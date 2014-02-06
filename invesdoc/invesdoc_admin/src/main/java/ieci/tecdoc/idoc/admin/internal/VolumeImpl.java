


package ieci.tecdoc.idoc.admin.internal;

import java.util.ArrayList;
import java.util.Date;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.ftp.FtpBasicFns;
import ieci.tecdoc.core.ftp.FtpConnectionInfo;
import ieci.tecdoc.core.ftp.FtpTransferFns;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.RepositoryErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.VolumeErrorCodes;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;
import ieci.tecdoc.sbo.fss.core.FssRepInfo;
import ieci.tecdoc.sbo.fss.core.FssVolInfo;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.core.db.DbConnectionConfig;

import org.apache.log4j.Logger;

/**
 * Representa un volumen de invesDoc
 */
public class VolumeImpl implements Volume
{
	
	/**
	 * Construye un objeto de la clase por el identificador del usuario y
	 * el identificador del repositorio al que pertenece.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 * @param repId Identificador del repositorio del volumen al que pertenece.
	 */
	public VolumeImpl(int userConnected, int repId)
	{
		init(userConnected, repId);
	}

	/**
	 * Construye un objeto de la clase por defecto.
	 */
	public VolumeImpl()
	{
		init(Defs.NULL_ID, Defs.NULL_ID);
	}
	
	/**
    * Establece la configuración de la conexión de base de datos
    * 
    * @param dbConnConfig Configuración de la conexión de base de datos
    * @throws Exception
    */
   public void setConnectionConfig(DbConnectionConfig dbConnConfig)
               throws Exception
   {
      _dbCntConfig = dbConnConfig;
   }
	
	/**
	 * Carga un volumen de invesDoc.
	 * 
	 * @param volumenId Identificador del volumen.
	 * @param full <code>true</code> cargo todas las listas de objetos
	 *           asociados a un volumen; <code>false</code> sólo se carga
	 *           los datos de un volumen.
	 * @throws Exception Si se produce algún error al leer la información del
	 * volumen.
	 */
	public void load(int volumenId) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger
					.debug("load: volumenId = " + Integer.toString(volumenId));
		try
		{
			_id = volumenId;
			DbConnection.open(getDbConfig());
			loadBase();
			loadRepInfo();
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			DbConnection.close();
		}
	}

	/**
	 * Guarda el volumen. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, el
	 * volumen ya existe.
	 */
	public void store() throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("store");
		
		try
		{
			DbConnection.open(getDbConfig());
			if (_id == Defs.NULL_ID)
			{
				loadRepInfo();
				if (_repInfo.m_type == FssMdoUtil.RT_CENTERA ||
				    _repInfo.m_type == FssMdoUtil.RT_DB  )
				   AdminException
					.throwException(VolumeErrorCodes.EC_VOL_NO_PERMIT_CREATE_VOL);				
			}
			getVolInfo();
			checkVolExists();
			checkRemarks();
			
			if (_id == Defs.NULL_ID)
			{
				insert();
			} else
			{
				checkMaxSize();
				update();
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} finally
		{
			DbConnection.close();
		}
		
	}

	/**
	 * Elimina el volumen.
	 * 
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void delete() throws Exception
	{
		VolumesTable table = new VolumesTable();
		
		if (_logger.isDebugEnabled())
			_logger.debug("delete");
		
		try
		{
		   if (_info.m_repType == FssMdoUtil.RT_CENTERA || 
		       _info.m_repType == FssMdoUtil.RT_DB  )
		   {
		      AdminException
				.throwException(VolumeErrorCodes.EC_VOL_NO_PERMIT_DELETE_VOL);
		   }
			DbConnection.open(getDbConfig());
			checkVolHasFiles();
			deleteVolLabel();
			DbDeleteFns.delete(table.getVolumeTableName(), 
               table.getDeleteVolQual(_id));
			DbDeleteFns.delete(table.getListVolumeTableName(), 
               table.getDeleteListVolQual(_id));
			DbDeleteFns.delete(table.getVolActDirTableName(), 
               table.getDeleteActDirQual(_id));
			if (_repType == VolumeDefs.REP_TYPE_DB)
			   FssDaoVolVolTbl.dropTable();
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} finally
		{
			DbConnection.close();
		}
		
	}
	
	/**
	 * Obtiene el identifiacdor del volumen.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * Establece el identificador del repositorio.
	 * 
	 * @param repId El identificador del repositorio.
	 */
	public void setRepId(int repId)
	{
		_repId = repId;
	}
	
	/**
	 * Obtiene el identificador del repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getRepId()
	{
		return _repId;
	}
	
	/**
	 * Obtiene el nombre del volumen.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Establece el nombre del volumen.
	 * 
	 * @param name El nombre del volumen.
	 */
	public void setName(String name)
	{
		_name = name;
	}
	
	/**
	 * Obtiene el identificador del tipo de repositorio donde está 
	 * almacenado el volumen, puede ser PFS, valor 3 o FTP, valor 1.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getRepType()
	{
		return _repType;
	}
	
	/**
	 * Obtiene la ruta del volumen relativo a su repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getPath()
	{
		return _path;
	}
	
	/**
	 * Establece la ruta del volumen relativo a su repositorio.
	 * 
	 * @param path La ruta del volumen.
	 */
	public void setPath(String path)
	{
		_path = path;
	}
	
	/**
	 * Obtiene el tamaño máximo del volumen en bytes.
	 * 
	 * @return El dato mencionado.
	 */
	public String getMaxSize()
	{
		return _maxSize;
	}
	
	/**
	 * Establece el tamaño máximo del volumen en bytes.
	 * 
	 * @param maxSize El tamaño máximo mencionado en bytes.
	 */
	public void setMaxSize(String maxSize)
	{
		_maxSize = maxSize;
	}
	
	/**
	 * Obtiene la información de si el volumen es temporal(1) o no(0).
	 * 
	 * @return El valor mencionado.
	 */
	public int getTemp()
	{
		return _temp;
	}
	
	/**
	 * Obtiene la ocupación en curso en bytes.
	 * 
	 * @return El valor mencionado.
	 */
	public String getActSize()
	{
		return _actSize;
	}
	
	/**
	 * Obtiene el número de ficheros en curso del volumen.
	 * 
	 * @return El valor mencionado.
	 */
	public int getNumFiles()
	{
		return _numFiles;
	}
	
	/**
	 * Obtiene el estado del volumen.
	 * 
	 * @return El valor mencionado.
	 */
	public int getState()
	{
		return _state;
	}
	
	/**
	 * Establece el estado del volumen.
	 * 
	 * @param state El estado del volumen.
	 */
	public void setState(int state)
	{
		_state = state;
	}
	
	/**
	 * Obtiene los comentarios del volumen.
	 * 
	 * @return El dato mencionado.
	 */
	public String getRemarks()
	{
		return _remarks;
	}
	
	/**
	 * Establece los comentarios del volumen.
	 * 
	 * @param remarks Los comentarios del volumen.
	 */
	public void setRemarks(String remarks)
	{
		_remarks = remarks;
	}
	
	/**
	 * Obtiene el identificador del usuario que ha creado el volumen. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId()
   {
      return _creatorId;   
   }
	
	/**
    * Obtiene la fecha de creación del volumen.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate()
   {
      return _creationDate;   
   }
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el volumen.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId()
   {
      return _updaterId;   
   }
   
   /**
    * Obtiene la fecha de actualización del volumen.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate()
   {
      return _updateDate;   
   }
   
   /**
	 * Obtiene la información del volumen en formato XML.
	 * 
	 * @return La información mencionada.
	 */
	public String toXML()
	{
		return toXML(true);
	}

	/**
	 * Muestra una representación de los valores de la clase en formato XML.
	 * 
	 * @return La representación mencionada.
	 */
	public String toString()
	{
		return toXML(false);
	}

	/**
	 * Obtiene la información del volumen en formato XML.
	 * 
	 * @param header Indica si hay que incluir la cabecera xml o no.
	 * @return La información mencionada.
	 */
	
	public String toXML(boolean header)
	{
		String text;
		XmlTextBuilder bdr;
		String tagName = "Volumen";
		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		bdr.addSimpleElement("Id", Integer.toString(_id));
		bdr.addSimpleElement("Name", _name);
		bdr.addSimpleElement("RepId", Integer.toString(_repId));
		bdr.addSimpleElement("RepType", Integer.toString(_repType));
		bdr.addSimpleElement("Path", _path);
		bdr.addSimpleElement("MaxSize", _maxSize);
		bdr.addSimpleElement("Temp", Integer.toString(_temp));
		bdr.addSimpleElement("ActSize", _actSize);
		bdr.addSimpleElement("NumFiles", Integer.toString(_numFiles));
		bdr.addSimpleElement("State", Integer.toString(_state));
		bdr.addSimpleElement("Remarks", _remarks);
		bdr.addSimpleElement("CreatorId", Integer.toString(_creatorId));
      bdr.addSimpleElement("CreationDate", DateTimeUtil.getDateTime(
                     _creationDate, DatePattern.XML_TIMESTAMP_PATTERN));
      if (DbDataType.isNullLongInteger(_updaterId))
      {
         text = "";
      }
      else
      {
         text = Integer.toString(_updaterId);
      }
      bdr.addSimpleElement("UpdaterId", text);
      if (DbDataType.isNullDateTime(_updateDate))
      {
         text = "";
      }
      else
      {
         text = DateTimeUtil.getDateTime(_updateDate, 
                                        DatePattern.XML_TIMESTAMP_PATTERN);
      }
      
      bdr.addSimpleElement("UpdateDate", text);
		bdr.addClosingTag(tagName);
		return bdr.getText();
	}
   
	/**
	 * Recupera de la base de datos el campo INFO de la tabla de repositorio.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer loadRepInfoValues(DbOutputStatement statement, Integer idx)
	throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadRepInfoValues");
		_repInfo = FssMdoUtil.decodeRepInfo(statement.getLongText(index++));
		_repType = _repInfo.m_type;
		return new Integer(index);
	}
	
	/**
	 * Inserta en base de datos información almacenada por esta clase.
	 * 
	 * @param statement
	 * @param idx
	 * @return @throws java.lang.Exception
	 */
	public Integer insertVolValues(DbInputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolValues");
		statement.setLongInteger(index++, _id);
		statement.setShortText(index++, _name);
		statement.setLongInteger(index++, _repId);
		if(_repType == VolumeDefs.REP_TYPE_DB)
		{
		   _info.m_path = getDBLocation();
		   _info.m_maxSize="1073741824";
		   _info.m_repType=VolumeDefs.REP_TYPE_DB;
		}
		statement.setShortText(index++, FssMdoUtil.encodeVolInfo(_info));
		statement.setLongInteger(index++, _temp);
		statement.setShortText(index++, _actSize);
		statement.setLongInteger(index++, _numFiles);
		statement.setLongInteger(index++, _state);
		statement.setShortText(index++, _remarks);
		_creatorId = _userConnected;
		statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		
		return new Integer(index);
	}
	
	/**
	 * Inserta en base de datos información almacenada por esta clase
	 * en la tabla IVOLACTDIR.
	 * 
	 * @param statement
	 * @param idx
	 * @return @throws java.lang.Exception
	 */
	public Integer insertVolActDirValues(DbInputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolActDirValues");
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, Defs.VOL_ACTDIR_INITIAL);
		statement.setLongInteger(index++, Defs.VOL_NUMFILES_INIT);
		return new Integer(index);
	}
	
	/**
	 * Obtiene los identificadores de las listas a las que está asociado un
	 * volumen,
	 * @param volId - Identificador del volumen
	 * @param groupIds -  Array de identificadores de lista
	 * @throws Exception
	 */
	public void loadListIds(int volId, ArrayList ListIds) throws Exception
	{
	   IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      VolumesTable table = new VolumesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadListIds");
      
      try
      {
         DbConnection.open(getDbConfig());
         ids = new IeciTdLongIntegerArrayList();
         colName = table.getLoadListIdColumnName();
         qual = table.getDeleteListVolQual(volId);
         DbSelectFns.select(table.getListVolumeTableName(),colName,qual,true,ids);
      
         for (int i=0; i <ids.count(); i++)
         {
            Integer val = new Integer(ids.get(i));
            ListIds.add(val);
         }
      }
      catch(Exception e)
      {
      	_logger.error(e);
			throw e;
		}
		finally
		{
			DbConnection.close();
		}
	}
	
	/**
	 * Recupera de la base de datos información asociada al volumen.
	 * 
	 * @param statement
	 * @param idx
	 * @return @throws java.lang.Exception
	 */
	public Integer loadVolValues(DbOutputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadVolValues");
		_name = statement.getShortText(index++);
		_repId = statement.getLongInteger(index++);
		_info = FssMdoUtil.decodeVolInfo(statement.getLongText(index++));
		_path = _info.m_path;
		_maxSize = _info.m_maxSize;
		_repType = _info.m_repType;
		_temp = statement.getLongInteger(index++);
		_actSize = statement.getShortText(index++);
		_numFiles = statement.getLongInteger(index++);
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
	   _updaterId = statement.getLongInteger(index++);
	   _updateDate = statement.getDateTime(index++);
		return new Integer(index);
	}
	
	/**
	 * Recupera de la base de datos información asociada al volumen.
	 * 
	 * @param statement
	 * @param idx
	 * @return @throws java.lang.Exception
	 */
	public Integer loadVolIdValue(DbOutputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadVolIdValue");
		_id = statement.getLongInteger(index++);
		
		return new Integer(index);
	}
	
	/**
	 * Recupera el identificador del volumen.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer loadVolumesByRepValues(DbOutputStatement statement, Integer idx)
	throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadVolumesByRepValues");
		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);
		_info = FssMdoUtil.decodeVolInfo(statement.getLongText(index++));
		_path = _info.m_path;
		_maxSize = _info.m_maxSize;
		_repType = _info.m_repType;
		_temp = statement.getLongInteger(index++);
		_actSize = statement.getShortText(index++);
		_numFiles = statement.getLongInteger(index++);
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
	   _updaterId = statement.getLongInteger(index++);
	   _updateDate = statement.getDateTime(index++);
		
		return new Integer(index);
	}
	
	/**
	 * Actualiza en base de datos información almacenada por esta clase.
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer updateVolValues(DbInputStatement statement, Integer idx) 
   throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateVolValues");

      statement.setShortText(index++, _name);
      if (_repType == VolumeDefs.REP_TYPE_DB)
         _path = getDBLocation();
      statement.setShortText(index++, FssMdoUtil.encodeVolInfo(_info));
      statement.setLongInteger(index++, _state);
      statement.setShortText(index++, _remarks);
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
      _updateDate = DbUtil.getCurrentDateTime();
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
	
   /**
	 * Construye el objeto FssVolInfo, la información de un volumen.
	 */
	private void getVolInfo()
	{
		_info.m_flags = Defs.VOL_FLAG_NULL;
		_info.m_maxSize = _maxSize;
		_info.m_path = _path;
		_info.m_repType = _repType;
		
	}
	
	/**
	 * Comprueba que el volumen tiene distinto nombre a los que ya existen.
	 * 
	 * @throws Exception Si el nombre del volumen ya existe.
	 */
	private void checkVolExists() throws Exception
	{
		int count;
		VolumesTable table = new VolumesTable();
		
		if (_id == Defs.NULL_ID)
			count = DbSelectFns.selectCount(table.getVolumeTableName(), table
					.getCountVolNameQual(_name));
		else 
			count = DbSelectFns.selectCount(table.getVolumeTableName(), table
					.getCountVolNameIdQual(_id, _name));
		if (count > 0)
			AdminException.throwException(VolumeErrorCodes.EC_VOL_EXIST_NAME);
	}
	
	/**
	 * Comprueba si el texto del campo Comentarios tiene comillas dobles
	 * 
	 * @throws Exception Si el campo Comentario tiene comillas dobles.
	 */
	private void checkRemarks() throws Exception
	{
		int index;
		if (_remarks != null) {
			index = _remarks.indexOf("\"");
			if (index > -1)
				AdminException
				.throwException(VolumeErrorCodes.EC_VOL_REMARKS_EXIST_QUOTES);
		}
	}
	
	/**
	 * Comprueba si el tamaño máximo del volumen es mayor que 
	 * la ocupación actual del volumen.
	 * 
	 * @throws Exception Si el tamaño máximo del volumen excede 
	 * de la ocupación actual del volumen.
	 */
	private void checkMaxSize() throws Exception
	{
	   if (Double.parseDouble(_maxSize) <= Double.parseDouble(_actSize))		
			AdminException
			.throwException(VolumeErrorCodes.EC_VOL_MAXSIZE_EXCEED);
	}
	
	/**
	 * Verifica si un volumen tiene ficheros. 
	 * Se usa para la eliminación de volúmenes.
	 * 
	 * @throws Exception Si el volumen tiene ficheros.
	 */
	private void checkVolHasFiles() throws Exception
   {
      int count;
      VolumesTable table = new VolumesTable();
      
      count = DbSelectFns.selectCount(table.getFileTableName(), 
                                      table.getCountFileQual(_id));
      if (count > 0)
      {
         AdminException.throwException(VolumeErrorCodes.EC_VOL_HAS_FILES);
      }
      
   }
	
	/**
	 * Crea un fichero en la inserción del volumen y 
	 * un directorio con el valor del directorio actual.
	 * 
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createFile() throws Exception
	{
		String fileName = Defs.VOL_FILE_NAME;
		if (_repType == VolumeDefs.REP_TYPE_PFS)
			createPFSFile(fileName);
		if (_repType == VolumeDefs.REP_TYPE_FTP)
			createFTPFile(fileName);
		
	}
	
	/**
	 * Crea un fichero en la inserción del volumen y un directorio 
	 * con el valor del directorio actual, cuando el tipo de
	 * repositorio es PFS.
	 * 
	 * @param fileName nombre del fichero.
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createPFSFile(String fileName) throws Exception
	{
		String pathDir = _repInfo.m_path + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ _path;
		String pathActDir = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								  + Defs.VOL_ACTDIR_INITIAL;
		String pathName = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ fileName;
		if(FileManager.fileExists(pathName))
			AdminException.throwException(VolumeErrorCodes.EC_VOL_EXIST_FILE);
		try
		{
			FileManager.createDirectory(pathDir);
			FileManager.writeStringToFile(pathName, Integer.toString(_id));
			FileManager.createDirectory(pathActDir);
		} catch (Exception e)
		{
			AdminException.throwException(VolumeErrorCodes.EC_VOL_NOT_EXIST_FILE);
		}
	}

	/**
	 * Crea un fichero en la inserción del volumen y un directorio 
	 * con el valor del directorio actual, cuando el tipo de
	 * repositorio es FTP.
	 * 
	 * @param fileName nombre del fichero.
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createFTPFile(String fileName) throws Exception
	{
		String pathDir = _repInfo.m_path + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ _path;
		String pathActDir = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								  + Defs.VOL_ACTDIR_INITIAL;
		String pathName = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ fileName;
		FtpConnectionInfo ftpConnInfo = new FtpConnectionInfo(_repInfo.m_srv,
				_repInfo.m_port, _repInfo.m_usr, _repInfo.m_pwd);
		String data = null;
		try
		{
			// veamos si existe ya el fichero, obtendremos el tamaño del fichero, 
			// en caso contrario, lanzará una excepcion. 
			data = FtpBasicFns.getFileSize(ftpConnInfo,pathName);
		} catch (Exception ie)
		{
			try
			{
				FtpBasicFns.createDirectory(ftpConnInfo, pathDir);
				FtpTransferFns.storeFile(ftpConnInfo, pathName, Integer.toString(_id)
						.getBytes());
				FtpBasicFns.createDirectory(ftpConnInfo, pathActDir);
			} catch (Exception e)
			{
				AdminException.throwException(VolumeErrorCodes.EC_VOL_NOT_EXIST_FILE);
			}
		}
		if (data != null )
			AdminException.throwException(VolumeErrorCodes.EC_VOL_EXIST_FILE);
	}
	
	/**
	 * Borra el fichero de etiqueta VOLLABEL del volumen.
	 * 
	 * @throws Exception Si se produce algún error al eliminar 
	 * el fichero.
	 */
	private void deleteVolLabel() throws Exception
	{
		String fileName = Defs.VOL_FILE_NAME;
		String pathDir = _repInfo.m_path + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ _path;
		String pathName = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os) 
								+ fileName;
		if (_repType == VolumeDefs.REP_TYPE_PFS)
			FileManager.deleteFile(pathName);
		if (_repType == VolumeDefs.REP_TYPE_FTP) 
		{
			FtpConnectionInfo ftpConnInfo = new FtpConnectionInfo(_repInfo.m_srv,
					_repInfo.m_port, _repInfo.m_usr, _repInfo.m_pwd);
			FtpBasicFns.deleteFile(ftpConnInfo, pathName);
		}
	}
	
	/**
	 * Lee el campo INFO del repositorio de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la lectura de la
	 * información mencionada.
	 */
	private void loadRepInfo() throws Exception
	{
		int counter;
		RepositoryImpl repository;
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadRepInfo");
		try
		{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getRepositoryTableName");
			tableInfo.setColumnsMethod("getLoadRepInfoColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadRepInfoValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(table.getLoadRepositoryQual(_repId), tableInfo,
					rowsInfo))
			{
				AdminException
						.throwException(RepositoryErrorCodes.EC_REP_NOT_EXIST);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	/**
	 * Lee la información básica del volumen de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la lectura de la
	 * información mencionada.
	 */
	private void loadBase() throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadBase");
		try
		{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getLoadVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadVolValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(table.getLoadVolumenQual(_id), tableInfo,
					rowsInfo))
			{
				AdminException
						.throwException(VolumeErrorCodes.EC_VOL_NOT_EXIST);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	protected void insertVolCentera(int volId) throws Exception
	{		
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolCentera");
		try
		{	
		   _id = volId;
		   _repType = FssMdoUtil.RT_CENTERA;
		   getVolInfo();
			insertVol();
			insertVolActDir();			
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	protected void insertVolDB(int volId) throws Exception
	{
	   if (_logger.isDebugEnabled())
	      _logger.debug("insertVolDB");
	   try
	   {
	      _id = volId;
	      _repType = FssMdoUtil.RT_DB;	      
			insertVol();
			insertVolActDir();
			createTblVol();
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
   }

	
	protected void deleteVolCentera(int volId) throws Exception
	{
	   VolumesTable table = new VolumesTable();
	   
	   if (_logger.isDebugEnabled())
	      _logger.debug("deleteVolCentera");
	   
	   try
	   {
	      checkVolHasFiles();
	      DbDeleteFns.delete(table.getVolumeTableName(), 
               table.getDeleteVolQual(volId));
			DbDeleteFns.delete(table.getListVolumeTableName(), 
               table.getDeleteListVolQual(volId));
			DbDeleteFns.delete(table.getVolActDirTableName(), 
               table.getDeleteActDirQual(volId));
	   }
	   catch(Exception e)
	   {
	      _logger.error(e);
			throw e;
	   }
	}
	
	protected void deleteVolDB(int volId) throws Exception
	{
	   
	   VolumesTable table = new VolumesTable();
	   
	   if (_logger.isDebugEnabled())
	      _logger.debug("deleteVolDB");
	   
	   try
	   {
	      
	      checkVolHasFiles();
	      DbDeleteFns.delete(table.getVolumeTableName(), 
               table.getDeleteVolQual(volId));
			DbDeleteFns.delete(table.getListVolumeTableName(), 
               table.getDeleteListVolQual(volId));
			DbDeleteFns.delete(table.getVolActDirTableName(), 
               table.getDeleteActDirQual(volId));
			FssDaoVolVolTbl.dropTable();			
			
	   }
	   catch(Exception e)
	   {
	      _logger.error(e);
			throw e;
	   }
	   
	}
	
	
	/**
	 * Inserta un nuevo volumen en invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen.
	 */
	private void insert() throws Exception
	{
		boolean commit = false;
		boolean inTrans = false;
		
		if (_logger.isDebugEnabled())
			_logger.debug("insert");
		try
		{
         if ( (_repType == VolumeDefs.REP_TYPE_DB) ||
			     (_repType == VolumeDefs.REP_TYPE_CENTERA) )
               AdminException
					.throwException(VolumeErrorCodes.EC_VOL_NO_PERMIT_CREATE_VOL);	

			_id = NextId.generateNextId(Defs.NEXT_ID_TBL_NAME,
					Defs.NEXT_ID_TYPE_VOL);
			
			DbConnection.beginTransaction();
			inTrans = true;
			insertVol();
			insertVolActDir();			
			createFile();
			commit = true;				
			
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} finally
		{
			if (inTrans)
				DbConnection.endTransaction(commit);
		}
	}
	
	/**
	 * Actualiza un volumen de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la actualización del
	 * volumen.
	 */
	private void update() throws Exception
	{
		DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      
      if (_logger.isDebugEnabled())
			_logger.debug("update");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getVolumeTableName");
         tableInfo.setColumnsMethod("getUpdateVolColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(VolumeImpl.class.getName());
         rowInfo.setValuesMethod("updateVolValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getLoadVolumenQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
	}
	
	/**
	 * Inserta un volumen en la tabla.
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen.
	 */
	private void insertVol() throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVol");
		try
		{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getInsertVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(tableInfo, rowsInfo);
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	/**
	 * Inserta un registro en la tabla IVOLACTDIR .
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * registro.
	 */
	private void insertVolActDir() throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolActDir");
		try
		{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolActDirTableName");
			tableInfo.setColumnsMethod("getInsertVolActDirColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolActDirValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(tableInfo, rowsInfo);
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	/**
	 * Inicializa los valores del volumen por defecto.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 * @param repId Identificador del repositorio al que pertenece el volumen.
	 */
	private void init(int userConnected, int repId)
	{
		_userConnected = userConnected;
		_id = Defs.NULL_ID;
		_name = null;
		_repId = repId;
		_info = new FssVolInfo();
		_repType = Defs.REP_TYPE_NULL;
		_path = null;
		_maxSize = Defs.VOL_MAXSIZE_DEFAULT;
		_temp = Defs.VOL_NOT_TEMP;
		_actSize = Defs.VOL_ACTSIZE_INITIAL;
		_numFiles = Defs.VOL_NUMFILES_INIT;
		_state = Defs.STATE_DEF;
		_remarks = null;
		_creationDate = DbDataType.NULL_DATE_TIME;
      _creatorId = _userConnected;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _repInfo = new FssRepInfo();
	}
	
	private void createTblVol() throws Exception
	{
	   FssDaoVolVolTbl.createTable();
	}
	
	private String getDBLocation() throws Exception
	{
	   String Loc;
	   String Col1,Col2;
	   
	   Col1 = DbDataType.getNativeDbType(DbDataType.SHORT_TEXT,DbConnection.getEngine(),
	         									DbConnection.getEngineVersion());
	   Col2 = DbDataType.getNativeDbType(DbDataType.LONG_BIN,DbConnection.getEngine(),
	         									DbConnection.getEngineVersion());
	   
	   Loc = "IVOLVOLTBL" + ",LOCID," + Col1 + "(32),FILEVAL," + Col2;
	   
	   return (Loc);
	}
	
	
	 /**
    * Devuelve la conexión a base de datos. Si la conexión es nula la crea 
    * a través del fichero de configuración de base de datos. 
    * 
    * @return Conexión con la base de datos
    * @throws Exception
    */
   private DbConnectionConfig getDbConfig() throws Exception
   {
      if (_dbCntConfig == null)
      {
         _dbCntConfig = CfgMdoConfig.getDbConfig();  
      }
      
      return  _dbCntConfig;
   }
   
	
	private static final Logger _logger = Logger.getLogger(VolumeImpl.class);
	
	private DbConnectionConfig _dbCntConfig;
	private int _userConnected;
	private int _id;
	private String _name;
	private int _repId;
	private FssVolInfo _info;
	private int _repType;
	private String _path;
	private String _maxSize;
	private int _temp;
	private String _actSize;
	private int _numFiles;
	private int _state;
	private String _remarks;
	private Date _creationDate;
   private int _creatorId;
   private int _updaterId;
   private Date _updateDate;
   private FssRepInfo _repInfo;
	
}
