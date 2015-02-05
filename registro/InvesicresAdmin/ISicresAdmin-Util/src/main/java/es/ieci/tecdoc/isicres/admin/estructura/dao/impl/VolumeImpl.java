


package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.base.ftp.FtpBasicFns;
import es.ieci.tecdoc.isicres.admin.base.ftp.FtpConnectionInfo;
import es.ieci.tecdoc.isicres.admin.base.ftp.FtpTransferFns;
import es.ieci.tecdoc.isicres.admin.core.datetime.DatePattern;
import es.ieci.tecdoc.isicres.admin.core.datetime.DateTimeUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.file.FileManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.VolumesTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Volume;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminRepositoryKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminVolumeDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminVolumeKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoVolVolTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssMdoUtil;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssRepInfo;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssVolInfo;
import es.ieci.tecdoc.isicres.admin.sbo.util.nextid.NextId;

/**
 * Representa un volumen de invesDoc
 */
public class VolumeImpl implements Volume
{
	private String version;
	private int engine;

	/**
	 * Construye un objeto de la clase por el identificador del usuario y
	 * el identificador del repositorio al que pertenece.
	 *
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 * @param repId Identificador del repositorio del volumen al que pertenece.
	 */
	public VolumeImpl(int userConnected, int repId, String entidad)
	{
		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession());
			this.engine = dbConn.getEngine();
			this.version = dbConn.getEngineVersion();
		} catch(Exception e){

		} finally {
			try { dbConn.close(); }
			catch(Exception e) {}
		}
		init(userConnected, repId);
	}

	public VolumeImpl(int userConnected, int repId)
	{
		init(userConnected, repId);
	}

	/**
	 * Construye un objeto de la clase por defecto.
	 */
	public VolumeImpl(String entidad)
	{
		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession());
			this.engine = dbConn.getEngine();
			this.version = dbConn.getEngineVersion();
		} catch(Exception e){

		} finally {
			try { dbConn.close(); }
			catch(Exception e) {}
		}
		init(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID);
	}

	public VolumeImpl()
	{
		init(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID);
	}

	/**
	 * Carga un volumen de invesDoc.
	 *
	 * @param volumenId Identificador del volumen.
	 * @throws Exception Si se produce algún error al leer la información del
	 * volumen.
	 */
	public void load(int volumenId, String entidad) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger
					.debug("load: volumenId = " + Integer.toString(volumenId));

		try
		{
			_id = volumenId;
			loadBase(entidad);
			loadRepInfo(entidad);
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}

	/**
	 * Guarda el volumen. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 *
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, el
	 * volumen ya existe.
	 */
	public void store(String entidad) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("store");

		try
		{
			if (_id == ISicresAdminDefsKeys.NULL_ID)
			{
				loadRepInfo(entidad);
				if (_repInfo.m_type == FssMdoUtil.RT_CENTERA ||
				    _repInfo.m_type == FssMdoUtil.RT_DB  )
				   ISicresAdminBasicException
					.throwException(ISicresAdminVolumeKeys.EC_VOL_NO_PERMIT_CREATE_VOL);
			}
			getVolInfo();
			checkVolExists(entidad);
			checkRemarks();

			if (_id == ISicresAdminDefsKeys.NULL_ID)
			{
				insert(entidad);
			} else
			{
				checkMaxSize();
				update(entidad);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}

	}

	/**
	 * Elimina el volumen.
	 *
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void delete(String entidad) throws Exception
	{
		VolumesTable table = new VolumesTable();

		if (_logger.isDebugEnabled())
			_logger.debug("delete");

		DbConnection dbConn=new DbConnection();
		try
		{
		   if (_info.m_repType == FssMdoUtil.RT_CENTERA ||
		       _info.m_repType == FssMdoUtil.RT_DB  )
		   {
		      ISicresAdminBasicException

				.throwException(ISicresAdminVolumeKeys.EC_VOL_NO_PERMIT_DELETE_VOL);
		   }
		   dbConn.open(DBSessionManager.getSession());
		   checkVolHasFiles(entidad);

		   DbDeleteFns.delete(dbConn, table.getVolumeTableName(),
               table.getDeleteVolQual(_id));
		   DbDeleteFns.delete(dbConn, table.getListVolumeTableName(),
               table.getDeleteListVolQual(_id));
		   DbDeleteFns.delete(dbConn, table.getVolActDirTableName(),
               table.getDeleteActDirQual(_id));
		   if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_DB)
			   FssDaoVolVolTbl.dropTable(entidad);

			// Eliminar la estructura de ficheros y directorios del volumen
		   deleteVolLabel();

		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} finally
		{
			dbConn.close();
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
		if(_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_DB)
		{
		   _info.m_path = getDBLocation();
		   _info.m_maxSize="1073741824";
		   _info.m_repType=ISicresAdminVolumeDefsKeys.REP_TYPE_DB;
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
		statement.setLongInteger(index++, ISicresAdminDefsKeys.VOL_ACTDIR_INITIAL);
		statement.setLongInteger(index++, ISicresAdminDefsKeys.VOL_NUMFILES_INIT);
		return new Integer(index);
	}

	/**
	 * Obtiene los identificadores de las listas a las que está asociado un
	 * volumen,
	 * @param volId - Identificador del volumen
	 * @param ListIds -  Array de identificadores de lista
	 * @throws Exception
	 */
	public void loadListIds(int volId, ArrayList ListIds, String entidad) throws Exception
	{
	   IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      VolumesTable table = new VolumesTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadListIds");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());
         ids = new IeciTdLongIntegerArrayList();
         colName = table.getLoadListIdColumnName();
         qual = table.getDeleteListVolQual(volId);
         DbSelectFns.select(dbConn, table.getListVolumeTableName(),colName,qual,true,ids);

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
			dbConn.close();
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
      if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_DB)
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
		_info.m_flags = ISicresAdminDefsKeys.VOL_FLAG_NULL;
		_info.m_maxSize = _maxSize;
		_info.m_path = _path;
		_info.m_repType = _repType;

	}

	/**
	 * Comprueba que el volumen tiene distinto nombre a los que ya existen.
	 *
	 * @throws Exception Si el nombre del volumen ya existe.
	 */
	private void checkVolExists(String entidad) throws Exception
	{
		int count;
		VolumesTable table = new VolumesTable();

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			if (_id == ISicresAdminDefsKeys.NULL_ID)
				count = DbSelectFns.selectCount(dbConn, table.getVolumeTableName(), table
						.getCountVolNameQual(_name));
			else
				count = DbSelectFns.selectCount(dbConn, table.getVolumeTableName(), table
						.getCountVolNameIdQual(_id, _name));
			if (count > 0)
				ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_EXIST_NAME);
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}

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
				ISicresAdminBasicException
				.throwException(ISicresAdminVolumeKeys.EC_VOL_REMARKS_EXIST_QUOTES);
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

	/*
	 * @SF-SEVILLA Al usar Invesdoc windows, Al campo ACTSIZE de la tabla IVOLVOLHDR, se
	 * le añade una coma ',' al tamaño actual luego cuando intenta convertirlo a doble,
	 * pues salta una excepcion
	 * 02-may-2006 / antmaria
	 */
	   _actSize = _actSize.replaceAll(",",".");
	   if (Double.parseDouble(_maxSize) <= Double.parseDouble(_actSize))
			ISicresAdminBasicException
			.throwException(ISicresAdminVolumeKeys.EC_VOL_MAXSIZE_EXCEED);
	}

	/**
	 * Verifica si un volumen tiene ficheros.
	 * Se usa para la eliminación de volúmenes.
	 *
	 * @throws Exception Si el volumen tiene ficheros.
	 */
	private void checkVolHasFiles(String entidad) throws Exception
   {
      int count;
      VolumesTable table = new VolumesTable();

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      count = DbSelectFns.selectCount(dbConn, table.getFileTableName(),
	                                      table.getCountFileQual(_id));
	      if (count > 0)
	      {
	         ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_HAS_FILES);
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
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
		String fileName = ISicresAdminDefsKeys.VOL_FILE_NAME;
		if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_PFS)
			createPFSFile(fileName);
		if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_FTP)
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
								  + ISicresAdminDefsKeys.VOL_ACTDIR_INITIAL;
		String pathName = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os)
								+ fileName;
		if(FileManager.fileExists(pathName))
			ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_EXIST_FILE);
		try
		{
			FileManager.createDirectory(pathDir);
			FileManager.writeStringToFile(pathName, Integer.toString(_id));
			FileManager.createDirectory(pathActDir);
		} catch (Exception e)
		{
			// Si se produce algún error eliminar toda la estructura de ficheros y directorios
			// que pueda haber sido creada
			try
			{
				// Eliminar el directorio inicial
				FileManager.deleteFile(pathActDir);
			} catch (Exception de) {
			}
			try
			{
				// Eliminar el VOLLABEL
				FileManager.deleteFile(pathName);
			} catch (Exception de) {
			}
			try
			{
				// Eliminar el directorio del volumen
				FileManager.deleteFile(pathDir);
			} catch (Exception de) {
			}

			ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_NOT_EXIST_FILE);
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
								  + ISicresAdminDefsKeys.VOL_ACTDIR_INITIAL;
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
				// Si se produce algún error eliminar toda la estructura de ficheros y directorios
				// que pueda haber sido creada
				try
				{
					// Eliminar el directorio inicial
					FtpBasicFns.deleteDirectory(ftpConnInfo, pathActDir);
				} catch (Exception de) {
				}
				try
				{
					// Eliminar el VOLLABEL
					FtpBasicFns.deleteFile(ftpConnInfo, pathName);
				} catch (Exception de) {
				}
				try
				{
					// Eliminar el directorio del volumen
					FtpBasicFns.deleteDirectory(ftpConnInfo, pathDir);
				} catch (Exception de) {
				}

				ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_NOT_EXIST_FILE);
			}
		}
		if (data != null )
			ISicresAdminBasicException.throwException(ISicresAdminVolumeKeys.EC_VOL_EXIST_FILE);
	}

	/**
	 * Borra el fichero de etiqueta VOLLABEL del volumen.
	 *
	 * @throws Exception Si se produce algún error al eliminar
	 * el fichero.
	 */
	private void deleteVolLabel() throws Exception
	{
		String fileName = ISicresAdminDefsKeys.VOL_FILE_NAME;
		String pathDir = _repInfo.m_path + FssMdoUtil.getFileNameSeparator(_repInfo.m_os)
								+ _path;
		String pathName = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os)
								+ fileName;

		String pathActDir = pathDir + FssMdoUtil.getFileNameSeparator(_repInfo.m_os)
		  						+ ISicresAdminDefsKeys.VOL_ACTDIR_INITIAL;

		if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_PFS)
		{
			try {
				// Eliminar el directorio inicial
				FileManager.deleteFile(pathActDir);
			} catch (Exception de) {
			}
			try {
				// Eliminar el VOLLABEL
				FileManager.deleteFile(pathName);
			} catch (Exception de) {
			}
			try {
				// Eliminar el directorio del volumen
				FileManager.deleteFile(pathDir);
			} catch (Exception de) {
			}
		}

		if (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_FTP)
		{
			FtpConnectionInfo ftpConnInfo = new FtpConnectionInfo(_repInfo.m_srv,
					_repInfo.m_port, _repInfo.m_usr, _repInfo.m_pwd);

			try {
				// Eliminar el directorio inicial
				FtpBasicFns.deleteDirectory(ftpConnInfo, pathActDir);
			} catch (Exception de) {
			}
			try {
				// Eliminar el VOLLABEL
				FtpBasicFns.deleteFile(ftpConnInfo, pathName);
			} catch (Exception de) {
			}
			try {
				// Eliminar el directorio del volumen
				FtpBasicFns.deleteDirectory(ftpConnInfo, pathDir);
			} catch (Exception de) {
			}
		}
	}

	/**
	 * Lee el campo INFO del repositorio de invesDoc.
	 *
	 * @throws Exception Si se produce algún error en la lectura de la
	 * información mencionada.
	 */
	private void loadRepInfo(String entidad) throws Exception
	{
		//int counter;
		//RepositoryImpl repository;
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadRepInfo");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getRepositoryTableName");
			tableInfo.setColumnsMethod("getLoadRepInfoColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadRepInfoValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(dbConn, table.getLoadRepositoryQual(_repId), tableInfo,
					rowsInfo, false))
			{
				ISicresAdminBasicException
						.throwException(ISicresAdminRepositoryKeys.EC_REP_NOT_EXIST);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}

	/**
	 * Lee la información básica del volumen de invesDoc.
	 *
	 * @throws Exception Si se produce algún error en la lectura de la
	 * información mencionada.
	 */
	private void loadBase(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadBase");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getLoadVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadVolValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(dbConn, table.getLoadVolumenQual(_id), tableInfo,
					rowsInfo, false))
			{
				ISicresAdminBasicException
						.throwException(ISicresAdminVolumeKeys.EC_VOL_NOT_EXIST);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}

	protected void insertVolCentera(int volId, String entidad) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolCentera");
		try
		{
		   _id = volId;
		   _repType = FssMdoUtil.RT_CENTERA;
		   getVolInfo();
			insertVol(entidad);
			insertVolActDir(entidad);
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}

	protected void insertVolDB(int volId, String entidad) throws Exception
	{
	   if (_logger.isDebugEnabled())
	      _logger.debug("insertVolDB");
	   try
	   {
	      _id = volId;
	      _repType = FssMdoUtil.RT_DB;
			insertVol(entidad);
			insertVolActDir(entidad);
			createTblVol(entidad);
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
   }


	protected void deleteVolCentera(int volId, String entidad) throws Exception
	{
	   VolumesTable table = new VolumesTable();

	   if (_logger.isDebugEnabled())
	      _logger.debug("deleteVolCentera");

	   DbConnection dbConn=new DbConnection();
	   try
	   {
		   dbConn.open(DBSessionManager.getSession());
	      checkVolHasFiles(entidad);
	      DbDeleteFns.delete(dbConn, table.getVolumeTableName(),
               table.getDeleteVolQual(volId));
			DbDeleteFns.delete(dbConn, table.getListVolumeTableName(),
               table.getDeleteListVolQual(volId));
			DbDeleteFns.delete(dbConn, table.getVolActDirTableName(),
               table.getDeleteActDirQual(volId));
	   }
	   catch(Exception e)
	   {
	      _logger.error(e);
			throw e;
	   }
	}

	protected void deleteVolDB(int volId, String entidad) throws Exception
	{

	   VolumesTable table = new VolumesTable();

	   if (_logger.isDebugEnabled())
	      _logger.debug("deleteVolDB");

	   DbConnection dbConn=new DbConnection();
	   try
	   {
		   dbConn.open(DBSessionManager.getSession());
	      checkVolHasFiles(entidad);
	      DbDeleteFns.delete(dbConn, table.getVolumeTableName(),
               table.getDeleteVolQual(volId));
			DbDeleteFns.delete(dbConn, table.getListVolumeTableName(),
               table.getDeleteListVolQual(volId));
			DbDeleteFns.delete(dbConn, table.getVolActDirTableName(),
               table.getDeleteActDirQual(volId));
			FssDaoVolVolTbl.dropTable(entidad);

	   }
	   catch(Exception e)
	   {
	      _logger.error(e);
			throw e;
	   }
	   finally{
		   dbConn.close();
	   }

	}


	/**
	 * Inserta un nuevo volumen en invesDoc.
	 *
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen.
	 */
	private void insert(String entidad) throws Exception
	{
		boolean commit = false;
		boolean inTrans = false;

		if (_logger.isDebugEnabled())
			_logger.debug("insert");

		DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());
         if ( (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_DB) ||
			     (_repType == ISicresAdminVolumeDefsKeys.REP_TYPE_CENTERA) )
               ISicresAdminBasicException
					.throwException(ISicresAdminVolumeKeys.EC_VOL_NO_PERMIT_CREATE_VOL);

			_id = NextId.generateNextId(ISicresAdminDefsKeys.NEXT_ID_TBL_NAME,
					ISicresAdminDefsKeys.NEXT_ID_TYPE_VOL, entidad);

			dbConn.beginTransaction();
			inTrans = true;
			insertVol(dbConn);
			insertVolActDir(dbConn);
			createFile();
			commit = true;

		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} finally
		{
			if (inTrans)
				dbConn.endTransaction(commit);
			dbConn.close();
		}
	}

	/**
	 * Actualiza un volumen de invesDoc.
	 *
	 * @throws Exception Si se produce algún error en la actualización del
	 * volumen.
	 */
	private void update(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();

      if (_logger.isDebugEnabled())
			_logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getVolumeTableName");
         tableInfo.setColumnsMethod("getUpdateVolColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(VolumeImpl.class.getName());
         rowInfo.setValuesMethod("updateVolValues");
         rowsInfo.add(rowInfo);

         DynamicFns.update(dbConn, table.getLoadVolumenQual(_id), tableInfo, rowsInfo);

      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}finally{
			dbConn.close();
		}
	}

	/**
	 * Inserta un volumen en la tabla.
	 *
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen.
	 */
	private void insertVol(String entidad) throws Exception
	{
		/*
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		*/
		if (_logger.isDebugEnabled())
			_logger.debug("insertVol");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			/*
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getInsertVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			*/
			insertVol(dbConn);
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}

	/**
	 * Inserta un volumen en la tabla.
	 *
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen.
	 */
	private void insertVol(DbConnection dbConn) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVol");
		try{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getInsertVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
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
	private void insertVolActDir(String entidad) throws Exception
	{
		/*
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		*/
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolActDir");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			/*
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolActDirTableName");
			tableInfo.setColumnsMethod("getInsertVolActDirColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolActDirValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			*/
			insertVolActDir(dbConn);
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}

	/**
	 * Inserta un registro en la tabla IVOLACTDIR .
	 *
	 * @throws Exception Si se produce algún error en la inserción del
	 * registro.
	 */
	private void insertVolActDir(DbConnection dbConn) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertVolActDir");
		try{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getVolActDirTableName");
			tableInfo.setColumnsMethod("getInsertVolActDirColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("insertVolActDirValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
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
		_id = ISicresAdminDefsKeys.NULL_ID;
		_name = null;
		_repId = repId;
		_info = new FssVolInfo();
		_repType = ISicresAdminDefsKeys.REP_TYPE_NULL;
		_path = null;
		_maxSize = ISicresAdminDefsKeys.VOL_MAXSIZE_DEFAULT;
		_temp = ISicresAdminDefsKeys.VOL_NOT_TEMP;
		_actSize = ISicresAdminDefsKeys.VOL_ACTSIZE_INITIAL;
		_numFiles = ISicresAdminDefsKeys.VOL_NUMFILES_INIT;
		_state = ISicresAdminDefsKeys.STATE_DEF;
		_remarks = null;
		_creationDate = DbDataType.NULL_DATE_TIME;
      _creatorId = _userConnected;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _repInfo = new FssRepInfo();
	}

	private void createTblVol(String entidad) throws Exception
	{
	   FssDaoVolVolTbl.createTable(entidad);
	 /*
	 * @SF-SEVILLA  Modificacion esquema
	 * 02-may-2006 / antmaria
	 */
	   DbUtil.grantPermsOnTable(0xf,FssDaoVolVolTbl.TN);
	}

	private String getDBLocation() throws Exception
	{
	   String Loc;
	   String Col1,Col2;

	   try{
		   Col1 = DbDataType.getNativeDbType(DbDataType.SHORT_TEXT,engine,
		         									version);
		   Col2 = DbDataType.getNativeDbType(DbDataType.LONG_BINARY,engine,
		         									version);
	   }catch(Exception e){
			_logger.error(e);
			throw e;
		}

	   Loc = "IVOLVOLTBL" + ",LOCID," + Col1 + "(32),FILEVAL," + Col2;


	   return (Loc);
	}

	private static final Logger _logger = Logger.getLogger(VolumeImpl.class);

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
