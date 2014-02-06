package ieci.tecdoc.idoc.admin.internal;

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
import ieci.tecdoc.core.ftp.FtpConnection;
import ieci.tecdoc.core.ftp.FtpConnectionInfo;
import ieci.tecdoc.core.ftp.FtpTransferFns;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.RepositoryErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.VolumeErrorCodes;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.api.volume.Volumes;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.fss.core.FssDaoVolTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;
import ieci.tecdoc.sbo.fss.core.FssRepInfo;
import ieci.tecdoc.sbo.util.nextid.NextId;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Representa un repositorio de invesDoc
 */
public class RepositoryImpl implements Repository
{
	
	/**
	 * Construye un objeto de la clase por el identificador del usuario.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 */
	public RepositoryImpl(int userConnected)
	{
		init(userConnected);
	}

	/**
	 * Construye un objeto de la clase por defecto.
	 */
	public RepositoryImpl()
	{
		init(Defs.NULL_ID);
	}

	/**
	 * Carga un repositorio de invesDoc.
	 * 
	 * @param repositoryId Identificador del repositorio.	 
	 * @throws Exception Si se produce algún error al leer la información del
	 * repositorio.
	 */
	public void load(int repositoryId) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger
					.debug("load: repositoryId = " + Integer.toString(repositoryId));
		try
		{
			_id = repositoryId;
			DbConnection.open(CfgMdoConfig.getDbConfig());
			loadBase();
			
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
	 * Guarda el repositorio. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, el
	 * repositorio ya existe.
	 */
	public void store() throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("store");
		if (_id == Defs.NULL_ID)
			getRepInfo();
		
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			checkRepExists();
			checkRemarks();
			
			if (_id == Defs.NULL_ID)
			{
			   if (_type == VolumeDefs.REP_TYPE_DB)
			      checkExistsRepDB();
			   
				insert();
			} else
			{
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
	
	private void checkExistsRepDB() throws Exception
	{
	   int          count;
	   VolumesTable table = new VolumesTable();
	
	   try
	   {
	      count = DbSelectFns.selectCount(table.getRepositoryTableName(),
	            									table.getCountRepTypeQual(_type));
	      if (count > 0)
	         AdminException.throwException(RepositoryErrorCodes.EC_REP_DB_EXISTS);
	   }
	   catch(Exception e)
	   {
	      throw (e);
	   }
	}

	/**
	 * Elimina el repositorio.
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
		   	   
			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			if (_type == FssMdoUtil.RT_CENTERA || _type == FssMdoUtil.RT_DB)
			{
			   if (_type == FssMdoUtil.RT_CENTERA)
		         deleteVolCentera(_id);
			   else
			      deleteVolDB(_id);
			}
			else
			   checkRepHasVolumes();
			
			deleteRepLabel();
			DbDeleteFns.delete(table.getRepositoryTableName(), 
               table.getDeleteRepQual(_id));
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
	 * Obtiene el identificador del repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getId() 
   {
      return _id;
   }
	
	/**
    * Obtiene el nombre del repositorio.
    *
    * @return El nombre mencionado.
    */
   public String getName()
   {
      return _name;   
   }
	
	/**
	 * Establece el nombre del repositorio.
	 * 
	 * @param name El nombre del repositorio.
	 */
	public void setName(String name) 
	{
		_name = name;
	}
	
	/**
	 * Obtiene el identificador del tipo de repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getType() 
	{
		return _type;
	}
	/**
	 * Establece el tipo de repositorio.
	 * 
	 * @param type El tipo de repositorio.
	 */
	public void setType(int type)
	{
		_type = type;
	}
	
	/**
	 * Obtiene los comentarios del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getRemarks()
	{
		return _remarks;
	}
	
	/**
	 * Establece los comentarios del repositorio.
	 * 
	 * @param remarks Los comentarios del repositorio
	 */
	public void setRemarks(String remarks)
	{
		_remarks = remarks;
	}
	   
   /**
    * Obtiene el path completo del repositorio.
    * 
    * @return El nombre mencionado
    */
	public String getPath()
	{
		return _path;
	}
   
	/**
	 * Establece el path completo del repositorio.
	 * 
	 * @param path El path completo del repositorio.
	 */
	public void setPath(String path)
	{
		_path = path;
	}
	
	/**
	 * Obtiene el servidor ftp del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getServer() 
	{
		return _server;
	}
	
	/**
	 * Establece el servidor ftp del repositorio.
	 * 
	 * @param server El servidor ftp del repositorio
	 */
	public void setServer(String server)
	{
		_server = server;
	}
	
	/**
	 * Obtiene el puerto ftp del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public int getPort()
	{
		return _port;
	}
	
	/**
	 * Establece el puerto ftp del repositorio.
	 * 
	 * @param port El puerto ftp del repositorio.
	 */
	public void setPort(int port)
	{
		_port = port;
	}
	
	/**
	 * Obtiene el usuario para conectarse por ftp al repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getUser()
	{
		return _user;
	}
	
	/**
	 * Establece el usuario para conectarse por ftp al repositorio.
	 * 
	 * @param user el usuario para conectarse por ftp al repositorio.
	 */
	public void setUser(String user)
	{
		_user = user;
	}
	
	/**
	 * Obtiene el password para conectarse por ftp al repositorio.
	 * 
	 * @return Nombre mencionado.
	 */
	public String getPassword()
	{
		return _password;
	}
	
	/**
	 * Establece el password para conectarse por ftp al repositorio.
	 * 
	 * @param password El password para conectarse por ftp al repositorio.
	 */
	public void setPassword(String password)
	{
		_password = password;
	}
	
	/**
	 * Obtiene El identificador del sistema operativo del repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getOs()
	{
		return _os;
	}
	/**
	 * Establece el flag del repositorio
	 * @param flag
	 */
	public void setFlag(int flag)
	{
	   _info.m_flags = flag;
	}
	/**
	 * Obtiene el flag del repositorio
	 * @return
	 */
	public int getFlag()
	{
	   return _info.m_flags;
	}
	
	/**
	 * Obtiene el estado del repositorio 
	 * (ej:REP_STAT_READONLY de VolumeDefs)
	 * 
	 * @return El estado mencionado
	 */
	public int getState()
	{
	   return _state;
	}
	
	/**
	 * Establece el sistema operativo del repositorio.
	 * 
	 * @param os El sistema operativo del repositorio.
	 */
	public void setOs(int os)
	{
		_os = os;
	}
	
	/**
	 * Establece el estado del repositorio
	 * 
	 * @param state Estado del repositorio (VolumeDefs)
	 */
	public void setState(int state)
	{
	   _state = state;
	}
	
	/**
	 * Obtiene los volúmenes asociados al repositorio.
	 * No es necesario cargar el objeto antes.
	 * 
	 * @param id Identificador del repositorio
	 * @return Los datos mencionados.
	 */
	public Volumes getVolumes(int id) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("getVolumes");
		try
		{
			_id = id;	
			//la conexión se realiza en loadByRep
			_volumes.loadByRep(_id);
			
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} 
		
		return _volumes;
	}
	
	/**
	 * Obtiene el identificador del usuario que ha creado el repositorio. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId()
   {
      return _creatorId;   
   }
	
	/**
    * Obtiene la fecha de creación del repositorio.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate()
   {
      return _creationDate;   
   }
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el repositorio.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId()
   {
      return _updaterId;   
   }
   
   /**
    * Obtiene la fecha de actualización del repositorio.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate()
   {
      return _updateDate;   
   }
	
   /**
    * Obtiene el objeto FssRepInfo
    * 
    * @return El objeto mencionado.
    */
   public FssRepInfo getInfo()
	{
   	return _info;
   }
   
	/**
	 * Obtiene la información del repositorio en formato XML.
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
	 * Obtiene la información del repositorio en formato XML.
	 * 
	 * @param header Indica si hay que incluir la cabecera xml o no.
	 * @return La información mencionada.
	 */
	public String toXML(boolean header)
	{
		String text;
		XmlTextBuilder bdr;
		String tagName = "Repository";
		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		bdr.addSimpleElement("Id", Integer.toString(_id));
		bdr.addSimpleElement("Name", _name);
		bdr.addSimpleElement("State", Integer.toString(_state));
		bdr.addSimpleElement("Remarks", _remarks);
		bdr.addSimpleElement("Type", Integer.toString(_type));
		bdr.addSimpleElement("Server", _server);
		bdr.addSimpleElement("Port", Integer.toString(_port));
		bdr.addSimpleElement("Path", _path);
		bdr.addSimpleElement("User", _user);
		bdr.addSimpleElement("Password", _password);
		bdr.addSimpleElement("Os", Integer.toString(_os));
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
	 * Recupera de la base de datos información asociada al repositorio.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer loadRepValues(DbOutputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadRepValues");
		_name = statement.getShortText(index++);
		_info = FssMdoUtil.decodeRepInfo(statement.getLongText(index++));
		_path = _info.m_path;
		_server = _info.m_srv;
		_port = _info.m_port;
		_user = _info.m_usr;
		_password = _info.m_pwd;
		_os = _info.m_os;
		_type = _info.m_type;
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
		_updaterId = statement.getLongInteger(index++);
		_updateDate = statement.getDateTime(index++);
		return new Integer(index);
	}

	
	
	/**
	 * Inserta en base de datos información almacenada por esta clase.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer insertRepValues(DbInputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertRepValues");
		statement.setLongInteger(index++, _id);
		statement.setShortText(index++, _name);
		statement.setLongInteger(index++, _type);
		statement.setShortText(index++, FssMdoUtil.encodeRepInfo(_info));
		statement.setLongInteger(index++, _state);
		statement.setShortText(index++, _remarks);
		statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		return new Integer(index);
	}
	
	/**
	 * Actualiza en base de datos información almacenada por esta clase.
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer updateRepValues(DbInputStatement statement, Integer idx) 
   throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateRepValues");

      statement.setShortText(index++, _name);
      statement.setLongInteger(index++, _state);
      statement.setShortText(index++, _remarks);
      statement.setShortText(index++, FssMdoUtil.encodeRepInfo(_info));
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
      _updateDate = DbUtil.getCurrentDateTime();
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }

	/**
    * Recupera todos los datos del repositorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadRepAllValues(DbOutputStatement statement, Integer idx) 
   throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadRepAllValues");

      _id = statement.getShortInteger(index++);
      _name = statement.getShortText(index++);
		_info = FssMdoUtil.decodeRepInfo(statement.getLongText(index++));
		_path = _info.m_path;
		_server = _info.m_srv;
		_port = _info.m_port;
		_user = _info.m_usr;
		_password = _info.m_pwd;
		_os = _info.m_os;
		_type = _info.m_type;
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
		_updaterId = statement.getLongInteger(index++);
		_updateDate = statement.getDateTime(index++);

      return new Integer(index);
   }
	
	/**
	 * Lee la información básica del repositorio de invesDoc.
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
			tableInfo.setTablesMethod("getRepositoryTableName");
			tableInfo.setColumnsMethod("getLoadRepColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(RepositoryImpl.class.getName());
			rowInfo.setValuesMethod("loadRepValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(table.getLoadRepositoryQual(_id), tableInfo,
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
	 * Inserta un nuevo repositorio en invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * repositorio.
	 */
	private void insert() throws Exception
	{
		boolean commit = false;
		boolean inTrans = false;
		int     volId = Defs.NULL_ID;
		
		if (_logger.isDebugEnabled())
			_logger.debug("insert");
		try
		{
			_id = NextId.generateNextId(Defs.NEXT_ID_TBL_NAME,
					Defs.NEXT_ID_TYPE_REP);
			
			if (_type == VolumeDefs.REP_TYPE_CENTERA ||
			    _type == VolumeDefs.REP_TYPE_DB)
			   volId = NextId.generateNextId(Defs.NEXT_ID_TBL_NAME,
			         							Defs.NEXT_ID_TYPE_VOL);
			DbConnection.beginTransaction();
			inTrans = true;
			insertRep();
			createFile();
			if (_type == VolumeDefs.REP_TYPE_CENTERA || _type == VolumeDefs.REP_TYPE_DB)
			   insertVol(volId);
			commit = true;
			DbConnection.endTransaction(commit);
			inTrans = false;
			
			if (_type == VolumeDefs.REP_TYPE_DB)
			   FssDaoVolVolTbl.createVolPolicy();
			
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
	 * Actualiza un repositorio de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la actualización del
	 * repositorio.
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
         tableInfo.setTablesMethod("getRepositoryTableName");
         tableInfo.setColumnsMethod("getUpdateRepColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(RepositoryImpl.class.getName());
         rowInfo.setValuesMethod("updateRepValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getLoadRepositoryQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
	}
	
	/**
	 * Comprueba que el repositorio tiene distinto nombre a los que ya existen.
	 * 
	 * @throws Exception Si existe ya el nombre del repositorio.
	 */
	private void checkRepExists() throws Exception
	{
		int count;
		VolumesTable table = new VolumesTable();
		
		if (_id == Defs.NULL_ID)
			count = DbSelectFns.selectCount(table.getRepositoryTableName(), table
					.getCountRepNameQual(_name));
		else 
			count = DbSelectFns.selectCount(table.getRepositoryTableName(), table
					.getCountRepNameIdQual(_id, _name));
		if (count > 0)
			AdminException.throwException(RepositoryErrorCodes.EC_REP_EXIST_NAME);
	}
	
	/**
	 * Comprueba si el texto del campo Comentario tiene comillas dobles
	 * 
	 * @throws Exception Si el campo Comentario tiene comillas dobles.
	 */
	private void checkRemarks() throws Exception
	{
		int index ;
		if (_remarks != null) {
			index = _remarks.indexOf("\"");
			if (index > -1)
				AdminException
				.throwException(RepositoryErrorCodes.EC_REP_REMARKS_EXIST_QUOTES);
		}
	}

	/**
	 * Construye el objeto FssRepInfo, la información de un repositorio.
	 * 
	 * @throws Exception Si se produce algún error al construir 
	 * el objeto.
	 */
	private void getRepInfo() throws Exception
	{
		_info.m_type = _type;		
		_info.m_os = _os;
		if (_type == VolumeDefs.REP_TYPE_FTP)
		{
			_info.m_srv = _server;
			_info.m_port = _port;
			_info.m_usr = _user;
			_info.m_pwd = _password;
			FtpConnection conn = new FtpConnection();
			conn.open(_server, _port, _user, _password);
			_path = FssMdoUtil.getFileNameSeparator(_os) 
				+ FtpBasicFns.getCurrentDirectory(conn) 
				+ FssMdoUtil.getFileNameSeparator(_os) + _path;
		}
		else if(_type == VolumeDefs.REP_TYPE_CENTERA)
		{
		   _info.m_srv = _server;
		   _info.m_port = _port;
		   _info.m_usr = _user;
		   _info.m_pwd = _password;
		   _info.m_os = FssMdoUtil.OS_WINDOWS;
		   if (_path == null)
		      _path = "";
		}
		
		_info.m_path = _path;
		
	}
	
	/**
	 * Verifica si un repositorio tiene volúmenes. 
	 * Se usa en la eliminación de repositorios.
	 * 
	 * @throws Exception Si el repositorio tiene volúmenes.
	 */
	private void checkRepHasVolumes() throws Exception
   {
      int count;
      VolumesTable table = new VolumesTable();
      
      count = DbSelectFns.selectCount(table.getVolumeTableName(), 
                                      table.getCountVolQual(_id));
      if (count > 0)
      {
         AdminException.throwException(RepositoryErrorCodes.EC_REP_HAS_VOLUMES);
      }
      
   }
	
	/**
	 * Borra el fichero de etiqueta REPLABEL del repositorio.
	 * 
	 * @throws Exception Si se produce algún error al eliminar 
	 * el fichero.
	 */
	private void deleteRepLabel() throws Exception
	{
		String fileName = Defs.REP_FILE_NAME;
		String pathName = _path + FssMdoUtil.getFileNameSeparator(_os) + fileName;
		if (_type == VolumeDefs.REP_TYPE_PFS)
			FileManager.deleteFile(pathName);
		if (_type == VolumeDefs.REP_TYPE_FTP) 
		{
			FtpConnectionInfo ftpConnInfo = new FtpConnectionInfo(_server,
					_port, _user, _password);
			FtpBasicFns.deleteFile(ftpConnInfo, pathName);
		}
	}
	
	/**
	 * Inserta un repositorio en la tabla.
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * repositorio.
	 */
	private void insertRep() throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertRep");
		try
		{
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getRepositoryTableName");
			tableInfo.setColumnsMethod("getInsertRepColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(RepositoryImpl.class.getName());
			rowInfo.setValuesMethod("insertRepValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(tableInfo, rowsInfo);
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	private void insertVol(int volId) throws Exception
	{
	   VolumeImpl vol = new VolumeImpl(_userConnected, _id);
	   vol.setMaxSize("0");
	   vol.setName(_name);
	   vol.setPath("");	  
	   
	   if (_type == VolumeDefs.REP_TYPE_CENTERA)
	      vol.insertVolCentera(volId);
	   
	   if (_type == VolumeDefs.REP_TYPE_DB)
	   {
	      vol.setMaxSize("21073741824"); //1024Mb
	      vol.insertVolDB(volId);
	   }
	}
		
	/**
	 * Crea un fichero en la inserción del repositorio.
	 * 
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createFile() throws Exception
	{
		String fileName = Defs.REP_FILE_NAME;
		if (_type == VolumeDefs.REP_TYPE_PFS)
			createPFSFile(fileName);
		if (_type == VolumeDefs.REP_TYPE_FTP)
			createFTPFile(fileName);
	}

	/**
	 * Crea un fichero en la inserción del repositorio, cuando el tipo de
	 * repositorio es PFS.
	 * 
	 * @param fileName Nombre del fichero.
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createPFSFile(String fileName) throws Exception
	{
		String pathName = _path + FssMdoUtil.getFileNameSeparator(_os) + fileName;
		if(FileManager.fileExists(pathName))
			AdminException.throwException(RepositoryErrorCodes.EC_REP_EXIST_FILE);
		try
		{
			FileManager.createDirectory(_path);
			FileManager.writeStringToFile(pathName, Integer.toString(_id));
		} catch (Exception e)
		{
			AdminException.throwException(RepositoryErrorCodes.EC_REP_NOT_EXIST_FILE);
		}
	}

	/**
	 * Crea un fichero en la inserción de un repositorio, cuando el tipo de
	 * repositorio es FTP.
	 * 
	 * @param fileName Nombre del fichero.
	 * @throws Exception Si se produce algún error al
	 * crear el fichero.
	 */
	private void createFTPFile(String fileName) throws Exception
	{
		String pathName = _path + FssMdoUtil.getFileNameSeparator(_os) + fileName;
		FtpConnectionInfo ftpConnInfo = new FtpConnectionInfo(_server,
				_port, _user, _password);
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
				FtpBasicFns.createDirectory(ftpConnInfo, _path);
				FtpTransferFns.storeFile(ftpConnInfo, pathName, Integer.toString(_id)
						.getBytes());
			} catch (Exception e)
			{
				AdminException.throwException(RepositoryErrorCodes.EC_REP_NOT_EXIST_FILE);
			}
		}
		if (data != null )
			AdminException.throwException(RepositoryErrorCodes.EC_REP_EXIST_FILE);
	}
	
	private void deleteVolCentera(int repId) throws Exception
	{
	   int          volId = Defs.NULL_ID;
	   VolumesTable tbl = new VolumesTable();
	   VolumeImpl   vol = new VolumeImpl(_userConnected,repId);
	   
	   volId = DbSelectFns.selectLongInteger(tbl.getVolumeTableName(),FssDaoVolTbl.CD_REPID.getName(),
	         										tbl.getLoadVolumesByRepQual(repId));
	   
	   if (volId == Defs.NULL_ID)	      
	      AdminException.throwException(VolumeErrorCodes.EC_VOL_NOT_EXIST);
	   
	   vol.deleteVolCentera(volId);
	      
	}
	
	private void deleteVolDB(int repId) throws Exception
	{
	
	   int          volId = Defs.NULL_ID;
	   VolumesTable tbl = new VolumesTable();
	   VolumeImpl    vol = new VolumeImpl(_userConnected, repId);
	   
	   volId = DbSelectFns.selectLongInteger(tbl.getVolumeTableName(),FssDaoVolTbl.CD_REPID.getName(),
	         										tbl.getLoadVolumesByRepQual(repId));
	   
	   if (volId == Defs.NULL_ID)
	      AdminException.throwException(VolumeErrorCodes.EC_VOL_NOT_EXIST);
	   
	   vol.deleteVolDB(volId);	   
	   
	}
		
	/**
	 * Inicializa los valores del repositorio por defecto.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 */
	private void init(int userConnected)
	{
		_userConnected = userConnected;
		_id = Defs.NULL_ID;
		_name = null;
		_info = new FssRepInfo();
		_info.m_flags = Defs.REP_FLAG_NULL;
		_state = Defs.STATE_DEF;
		_remarks = null;
		_type = Defs.REP_TYPE_NULL;
	   _path = null;
	   _server = null;
	   _port = VolumeDefs.FTP_PORT_DEFAULT;
	   _user = null;
	   _password = null;
	   _os = Defs.OS_NULL;
	   _volumes = new Volumes();
	   _creationDate = DbDataType.NULL_DATE_TIME;
      _creatorId = userConnected;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
		
	}
	
	private static final Logger _logger = Logger.getLogger(RepositoryImpl.class);
	
	private int _userConnected;
	private int _id;
	private String _name;
	private FssRepInfo _info;
	private int _state;
	private String _remarks;
	private int _type;
   private String _path;
   private String _server;
   private int _port;
   private String _user;
   private String _password;
   private int _os;
   private Volumes _volumes;
   private Date _creationDate;
   private int _creatorId;
   private int _updaterId;
   private Date _updateDate;
}
