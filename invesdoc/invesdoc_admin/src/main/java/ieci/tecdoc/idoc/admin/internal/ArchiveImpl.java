
package ieci.tecdoc.idoc.admin.internal;

/**
 * Proporciona toda la funcionalidad necesaria para manejar un archivador. 
 */

import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.textutil.UtilX;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveDefs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveMisc;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveUpdInfo;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.database.ArchivesTable;
import ieci.tecdoc.idoc.admin.database.DirsTable;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgFtsConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFldType;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveVolListType;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetType;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoClfTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDATNodeTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDocTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoExtFldsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoMultFldTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPageTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoRelFldsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoXNIdTbl;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sbo.util.types.SboType;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;


public class ArchiveImpl implements Archive
{  
	
	public ArchiveImpl(int userConnected, int parentId, boolean isLdap)
	{
		init(userConnected,parentId,isLdap);
	
	}	
	
	public ArchiveImpl()
	{
		init(Defs.NULL_ID, Defs.NULL_ID,false);
	}
	
	/**
    * Establece información referente a la definición de campos
    * del archivador,
    *
    *  @param fldsArch Estructura con la información mencionada.
    */
	public void setFldsDef(ArchiveFlds fldsArch)
	{
		_flds = (ArchiveFldsImpl)fldsArch;
	}
	
	/**
	 * Obtiene la información referente a la definición de campos.
	 * @return Definición de campos.
	 */
	public ArchiveFlds getFldsDef()
	{
	   return _flds;	   
	}
	
	/**
	 * Establece información referente a la definición de índices 
	 * del archivador.
	 * @param idxArch Definición  de índices.
	 */
	public void setIdxsDef(ArchiveIdxs idxsArch)
	{
	   _idxs = (ArchiveIdxsImpl)idxsArch;
	}
	
	/**
	 * Obtiene la información referente a la definición de índices.
	 * @return Definición de índices.
	 */
	public ArchiveIdxs getIdxsDef()
	{
	   return (ArchiveIdxs)_idxs;
	}
	
	/** 
	 * Establece la información de título de carpetas y lista de volúmenes
	 * @param miscArch La información
	 */
	public void setMiscDef(ArchiveMisc miscArch)
	{
		_misc = (ArchiveMiscImpl)miscArch;
	}
	
	/**
	 * Obtiene la información de título de carpetas y lista de volúmenes
	 * @return La información mencionada. 
	 */
	public ArchiveMisc getMiscDef()
	{	   
	   return (ArchiveMisc)_misc;
	}
	
	/**
	 * Establece si estamos en un sistema de usuarios LDAP.
	 * @param isLdap true / false
	 */
	public void setLdap(boolean isLdap)
	{
	   _isLdap = isLdap;
	}
	
	/**
	 * Obtiene el nombre del archivador.
	 * @return Nombre del archivador.
	 */
	public String getName()
	{
	   return _name;
	}
	
	/**
	 * Establece el nombre del archivador.
	 * @param Nombre del archivador.
	 */
	public void setName(String name)
	{
		_name = name;
	}
	
	/**
	 * Obtiene la descripción del archivador.
	 * @return Descripción del archivador.
	 */
	public String getRemarks()
	{
	   return _remarks;
	}
	
	/**
	 * Establece la descripción del archivador.
	 * @param Descripción del archivador.
	 */
	public void setRemarks(String remarks)
	{
		_remarks = remarks;
	}
	
	/**
	 * Obtiene si existe búsqueda en contenido de fichero.
	 * @return true / false
	 */
	public boolean isFtsInContents()
	{
	   boolean ftsInContents = false;
	   
	   if ((_flags & ArchiveDefs.ARCH_FLAG_FTSINCONTENTS) != 0)
	      ftsInContents = true;
	   
	   return(ftsInContents);
	      
	}
	
	/**
	 * Establece la existencia de búsqueda en contenido de fichero.
	 * @param ftsInContents true / false
	 */
	public void setFtsInContents(boolean ftsInContents)
	{
	   if (ftsInContents)
	      _flags = _flags | ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
	   else
	      _flags = _flags & ~ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
	}
	
	public int getParentId()
	{
	   return (_parentId);
	}

	public int getParentId(int archId) throws Exception
	{	 
	   int oldId = Defs.NULL_ID;
	   int oldParentId = Defs.NULL_ID;
	   int parentId = Defs.NULL_ID;
	   
	   if (_logger.isDebugEnabled())
         _logger.debug("getParentId");
	   
	   try
	   {	  
	      DbConnection.open(CfgMdoConfig.getDbConfig());
	      oldId = _id;
	      oldParentId = _parentId;
	      _id = archId;
	      
	      loadParentId();
	      
	   }
	   catch(Exception e)
	   {
	      _logger.error(e);
	      throw e;
	   }
	   finally
	   {
	      _id = oldId;
	      parentId = _parentId;
	      _parentId = oldParentId;
	      DbConnection.close();
	   }
	   
	   
	   return (parentId);
	}

	/**
	 * Establece el identificador del padre del archivador.
	 * @param parentId Identificador del padre del archivador.
	 */
	public void setParentId (int parentId)
	{
		_parentId = parentId;
	}
	
	/**
	 * Establece el identificador del administrador del archivador.
	 * @param admId Identificador mencionado.
	 */
	public void setAdminUserId(int admId)
	{
	   _adminUserId = admId;
	}
	
	/**
	 * Obtiene el identificador del administrador del archivador.
	 * @return Identificador mencionado.
	 */
	public int getAdminUserId()
	{
	   return(_adminUserId);
	}
	
	/**
	 * Obtiene el identificador del archivador.
	 * @return Identificador del archivador.
	 */
	public int getId()
	{
	   return _id;
	}
	
	/**
	 * Establece el identificador del archivador.
	 * @param id Identificador del archivador.
	 */
	public void setId(int id)
	{
		_id = id;
		_tblPrefix = "A" + Integer.toString(_id);
	}
	
	/**
	 * Obtiene el tipo de acceso del archivador.
	 * @return Tipo de acceso
	 */
	public int getAccessType()
	{
	   return _accessType;
	}
	/**
	 * Establece el tipo de acceso del archivador.
	 * @param accessType Tipo de acceso.
	 */
	public void setAccessType(int accessType)
	{
	   _accessType = accessType;
	}
	
	/**
	 * Obtiene el identificador de acceso del archivador.
	 * @return Identificador mencionado.
	 */
	public int getAcsId()
	{
	   return _acsId;
	}
	
	/**
	 * Obtiene el identificador del creador del archivador.
	 * @return Identificador mencionado.
	 */
	public int getCreatorId()
	{
	   return _creatorId;	   
	}
	
	/**
	 * Obtiene la fecha de creación del archivador.
	 * @return Fecha de creación.
	 */
	public Date getCreationDate()
	{
	   return _creationDate;
	}
	
	/**
	 * Obtiene el identificador del último actualizador del archivador.
	 * @return Identificador mencionado.
	 */
	public int getUpdaterId()
   {
	   return _updaterId;
   }
	
	/**
	 * Obtiene la fecha de la última actualización del archivador.
	 * @return Fecha de actualización.
	 */
	public Date getUpdateDate()
   {
	   return _updateDate;
   }
	
	/**
    * Carga un archivador.
    * 
    * @param id Identificador del archivador.
    * @throws Exception Si se produce algún error al leer la información del 
    * archivador.
    */
   public void load(int id) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(id));
      
      try
      {
         _id = id;
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         loadArchiveHdr();
         loadDetailsArchive();
         loadParentId();
         loadAdminUserId();
         loadAdminUsersId();
         fillDetailValues();
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
    * Crea el archivador.
    * 
    * @throws Exception Si se produce algún error al crear. Por ejemplo, 
    * el archivador ya existe.
    */
   public void create() throws Exception   
	{
		if (_logger.isDebugEnabled())
			_logger.debug("store");
		
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());			
//			ver si son correctos ArchiveTokenFlds,ArchivTokenIdxs,ArchivTokenMisc
		   validateArchive(false); //false no se está actualizando
			createArchive();
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
    * Actualiza la información del archivador
    * 
    * @param archUpdInfo Estructura conteniendo la información del 
    * archivador tanto nueva como antigua
    * @throws Exception Errores
    */
   public void update(ArchiveUpdInfo archUpdInfo) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("update");
      
      _updInfo = (ArchiveUpdInfoImpl)archUpdInfo;
      
      try
      {         
         
         if (_id == Defs.NULL_ID)
            AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_ID);
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         load();        
       
         //Si existen carpetas 
         //general: cambiar nombre ,remarks y lista de volúmenes del archivador
         //campos: insertar, cambiar nombre, remarks de los existentes
         //índices: insertar, eliminar , cambiar el nombre del índice
         if (existsFdrs())
         {
            if (archUpdInfo.isModifyDefFlds())
               AdminException.throwException(ArchiveErrorCodes.EC_ARCH_WITH_FDRS);
            
            updateArchWithFdrs();
               
         }
         else // Si no existen carpetas se puede cambiar todo
         {
            updateArchWithoutFdrs();
         }
         //cargo la nueva estructura
         load();
         
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
   
   public void delete(int archiveId) throws Exception
   {
      int oldId = Defs.NULL_ID;
      String oldTblPfx = "";      
      
      if (_logger.isDebugEnabled())
         _logger.debug("delete");
      
      try
      {
         oldId = _id;
         oldTblPfx = _tblPrefix;
         
         _id = archiveId;
         _tblPrefix = "A" + Integer.toString(_id);
         
         delete();
         
      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }
      finally
      {
         _id = oldId;
         _tblPrefix = oldTblPfx;
      }     
    
      
   }
   
   /**
    * Elimina el archivador cargado
    * solo se puece eliminar un archivador si no existen carpetas y si no
    * hay creadas tablas de validación sobre alguno de sus campos.
    * 
    * @throws Exception Errores
    */
   public void delete() throws Exception 
   {
      
      boolean commit = false;
      boolean inTrans = false;
      ArchivesTable  table = new ArchivesTable();
      DirsTable      dirstable = new DirsTable();
      LdapUsersTable userTable = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

		try
		{
		   if ( _id == Defs.NULL_ID )
		      AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_ID);
		   
		   DbConnection.open(CfgMdoConfig.getDbConfig());
		   
		   if (_acsId == Defs.NULL_ID)
		      loadAcsIdFromArchId();	   
		   			
		   checkCanDeleteArchive();
		   
		   DbConnection.beginTransaction();
         inTrans = true;
         
         //formatos,report, componentes en uso
         deleteInfoExt();

         //tabla de detalle
         DbDeleteFns.delete(table.getArchDetTableName(), 
  				 					table.getLoadDetailsArchIdQual(_id));

         //tabla de relación archivador,directorio
         DbDeleteFns.delete(dirstable.getNodeTableName(),
               				 table.getLoadNodeArchIdQual(_id));
         
         //tabla idocxnextid
         DbDeleteFns.delete(table.getXNextIdTableName(),
               				table.getLoadNextIdArchIdQual(_id));
         
         //tabla idocpreffmt, idocprefwfmt
         DbDeleteFns.delete(table.getPrefFmtTableName(), 
               table.getLoadPrefFmtsArchIdQual(_id));
         
         DbDeleteFns.delete(table.getPrefWFmtTableName(), 
               table.getLoadPrefWFmtsArchIdQual(_id));
         
         
         //tabla idocarchhdr
         DbDeleteFns.delete(table.getArchHdrTableName(),
               				table.getLoadArchIdQual(_id));
         //tabla ivolarchlist
         DbDeleteFns.delete(table.getArchListTableName(),
               				table.getLoadArchListArchIdQual(_id));
         
         //Se eliminan permisos sobre el archivador iuserobjhdr,iuserobjperm
         DbDeleteFns.delete(userTable.getOwnershipTableName(), 
               userTable.getDeleteOwnershipById(_acsId));
         
         DbDeleteFns.delete(userTable.getObjPermsTableName(), 
               userTable.getDeleteObjPermsByObjIdQual(_acsId));
         //Se destruyen todas las tablas del archivador

         commit = true;
         
         deleteArchDoc();
         dropArchTbls();
		      
		}
		catch(Exception e)
		{
		   _logger.error(e);
			throw e;
		}
		finally
		{
			if (inTrans) 
	            DbConnection.endTransaction(commit);		
			
			DbConnection.close();
		}
		
   }
   /**
    * Actualiza el padre del un archivador en concreto no es necesario
    * que se carge el archivador previamente.
    * 
    * @param parentId Identificador del padre
    * @param archiveId Identificador del archivador
    * @throws Exception Errores
    */
   public void updateParentId(int parentId, int archiveId) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateParentId");
      
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (!isValidParentId(parentId))
            AdminException.throwException(ArchiveErrorCodes.EC_PARENT_NO_ID);
         if (!isValidArchId(archiveId))
            AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_ID);
                  
		   DbConnection.beginTransaction();         
         inTrans = true;
         
         updateArchParentId(parentId,archiveId);
         
         commit = true;
         
      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }
      finally
		{
			if (inTrans) 
	            DbConnection.endTransaction(commit);		
			
			DbConnection.close();
		}
   }
   
  /**
   * Obtiene información de los posibles administradores del archivador.
   * 
   * @return Estructura con dicha información
   */
   public BasicUsers getAdminUsers()
   {
      return _adminUsers;
   }
   
      
   /**
    * Obtiene si existen carpetas dadas de alta en el archivador
    * 
    * @return true / false
    * @throws Exception
    */
   public boolean existsFdrsInArch() throws Exception
   {
      boolean exists = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("existsFdrsInArch");
      
      try
      {
         if ( _id == Defs.NULL_ID )
		      AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_ID);
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         _tblPrefix = "A" + Integer.toString(_id);
         exists = existsFdrs();
         
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
      
      return exists;
   }
   
   /**
    * Obtiene la información del archivador en formato XML.
    * @param header true / false
    * @return La información mencionada.
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Archive";
      String text;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Name", _name);
      bdr.addSimpleElement("Description", _remarks);
      bdr.addSimpleElement("Type", Integer.toString(_type));
      bdr.addSimpleElement("AccessType", Integer.toString(_accessType));
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
      bdr.addSimpleElement("ParentId", Integer.toString(_parentId));
      bdr.addSimpleElement("AdminUserId", Integer.toString(_adminUserId));
      bdr.addSimpleElement("Flags", Integer.toString(_flags));
      bdr.addSimpleElement("FldsIdxsDef", _fldsIdxsDetail);
      bdr.addSimpleElement("MiscDetail", _miscDetail);

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }
   
   /**
    * Obtiene la información del campo en formato XML.
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
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertObjValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertObjValues");

      statement.setLongInteger(index++, _acsId);
      statement.setLongInteger(index++, UserDefs.PRODUCT_IDOC);
      statement.setLongInteger(index++, Defs.OBJECT_OWNER_TYPE_ARCHIVE);
      statement.setLongInteger(index++, _id);
      statement.setLongInteger(index++, Defs.ARCH_TYPE_STANDARD);
      statement.setLongInteger(index++, 0);
      statement.setLongInteger(index++, Defs.OBJECT_OWNER_TYPE_USER);
      statement.setLongInteger(index++, _userConnected);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());
      statement.setLongInteger(index++, DbDataType.NULL_LONG_INTEGER);
      statement.setDateTime(index++, DbDataType.NULL_DATE_TIME);
      
      return new Integer(index);
   }
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia 
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer updateObjValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateObjValues");
      
      statement.setLongInteger(index++, _updInfo.getAdminUserId());
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DateTimeUtil.getCurrentDateTime());
      
      return new Integer(index);
   }

   
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertArchHdrValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertArchHdrValues");

      statement.setLongInteger(index++, _id);
      statement.setShortText(index++, _name);
      statement.setShortText(index++, _tblPrefix);
      statement.setLongInteger(index++, _type);
      statement.setLongInteger(index++, _flags);
      statement.setShortText(index++, _remarks);
      statement.setLongInteger(index++, _accessType);
      statement.setLongInteger(index++, _acsId);
      statement.setLongInteger(index++, _creatorId);
      statement.setDateTime(index++, _creationDate);
      statement.setLongInteger(index++, _updaterId);
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
   public Integer updateArchHdrValues(DbInputStatement statement, Integer idx) 
   					throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateArchHdrValues");

      statement.setShortText(index++, _updInfo.getName());  
      statement.setShortText(index++, _updInfo.getRemarks());   
      statement.setLongInteger(index++, _updInfo.getFlags());
      statement.setLongInteger(index++, _updaterId);
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
   
   
 
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertNodeValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertNodeValues");

      statement.setLongInteger(index++, _id);
      statement.setLongInteger(index++, Defs.NODE_TYPE_ARCHIVE);
      statement.setLongInteger(index++, _parentId);
      
      return new Integer(index);
   }
   
     
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertFieldsDetValues(DbInputStatement statement, Integer idx) 
						throws Exception 
	{
		String value = getFieldsDetValue(false);
		int    index = idx.intValue();
		
		
		if (_logger.isDebugEnabled())
		_logger.debug("insertFieldDetValues");
		
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, DaoArchDetType.DET_TYPE_FLD_DEF);
		statement.setLongText(index++, value);
		
		
		return new Integer(index);
	}
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia	
    * @param idx Indice	
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertMiscDetValues(DbInputStatement statement, Integer idx)
                  throws Exception 
   {
      String value = getMiscDetValue(false);
      int    index = idx.intValue();
      
      if (_logger.isDebugEnabled())
   		_logger.debug("insertMiscDetValues");
   		
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, DaoArchDetType.DET_TYPE_MISC_DEF);
		statement.setLongText(index++, value);
		
		
		return new Integer(index);
   }
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia	
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertFtsDetValues(DbInputStatement statement, Integer idx) 
						throws Exception 
	{
		String value = getFtsDetValue(false);
		int    index = idx.intValue();
		
		
		if (_logger.isDebugEnabled())
		_logger.debug("insertFtsDetValues");
		
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, DaoArchDetType.DET_TYPE_FTS_DEF);
		statement.setLongText(index++, value);
		
		
		return new Integer(index);
	}
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer updateFtsDetValues(DbInputStatement statement, Integer idx)
						throws Exception
	{
      String value = getFtsDetValue(true);
      int index = idx.intValue();
	
      if (_logger.isDebugEnabled())
         _logger.debug("updateFtsDetValues");
	
      statement.setLongText(index++, value);
	
      return new Integer(index);
	}
   
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer updateFieldsDetValues(DbInputStatement statement, Integer idx)
   					throws Exception
   {
      String value = getFieldsDetValue(true);
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateFieldsDetValues");
      
      statement.setLongText(index++, value);
      
      return new Integer(index);
   }
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer updateMiscDetValues(DbInputStatement statement, Integer idx)
						throws Exception
	{
      String value = getMiscDetValue(true);
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateMiscDetValues");

      statement.setLongText(index++, value);

      return new Integer(index);
	}
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertXNextIdFolderValues(DbInputStatement statement, Integer idx)
   					throws Exception
   {
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertXNextIdFolderValues");
      
      statement.setLongInteger(index++, Defs.NEXT_ID_XNID_TABLE_TYPE_FOLDER);
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, 1);
      
      
      return new Integer(index);
   }
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertXNextIdXFValues(DbInputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertXNextIdXFValues");
      
      statement.setLongInteger(index++, Defs.NEXT_ID_XNID_TABLE_TYPE_EXT_FLD);
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, 1);
      
      
      return new Integer(index);
   }
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertXNextIdDocValues(DbInputStatement statement, Integer idx)
   					throws Exception
   {
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertXNextIdDocValues");
      
      statement.setLongInteger(index++, Defs.NEXT_ID_XNID_TABLE_TYPE_IDOC_DOC);
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, 1);
      
      
      return new Integer(index);
   }
   
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */
    
   public Integer insertArchListValues(DbInputStatement statement, Integer idx)
   					throws Exception
   {
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertArchListValues");
      
      statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, _misc.getVolListId());		
      
      
      return new Integer(index);
   }
   
   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */    
   public Integer updateArchListValues(DbInputStatement statement, Integer idx)
   					throws Exception
	{
      ArchiveMiscImpl misc = (ArchiveMiscImpl)_updInfo.getMiscDef();
      int index = idx.intValue();
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchListValues");
      
      statement.setLongInteger(index++ , misc.getVolListId());
      
      return new Integer(index);
	}

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement Sentencia
    * @param idx Indice
    * @return Indice
    * @throws java.lang.Exception
    */    
   public Integer loadAllArchHdrValues(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadAllArchHdrValues");

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);
      _tblPrefix = statement.getShortText(index++);
      _type = statement.getLongInteger(index++);
      _flags = statement.getLongInteger(index++);
      _remarks = statement.getShortText(index++);
      _accessType = statement.getLongInteger(index++);
      _acsId = statement.getLongInteger(index++);
      _creatorId = statement.getLongInteger(index++);
      _creationDate = statement.getDateTime(index++);
      _updaterId = statement.getLongInteger(index++);
      _updateDate = statement.getDateTime(index++);

      return new Integer(index);
   }
   
     
   /**
    * Obtiene toda la información referente al archivador sin
    * realizar una conexión.
    * 
    * @throws Exception Errores al obtener la información.
    */
   private void load() throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(_id));
      
      try
      {  
         loadArchiveHdr();
         loadDetailsArchive();
         loadParentId();
         loadAdminUsersId();
         fillDetailValues();
         loadAdminUserId();
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
   }
   
   
   /////////////////////////////////////////////////////////////////////////////////
   //                        privadas
   ////////////////////////////////////////////////////////////////////////////////

   /**
    * Actualiza el padre del archivador
    * @param parentId Identificador del padre del archivador
    * @param archiveId Identificador del archivador
    * @throws Errores de actualización
    */
   private void updateArchParentId(int parentId,int archiveId) throws Exception
   {
      
      String        qual  = null;
      ArchivesTable table = new ArchivesTable(); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchParentId");

      
      try
      {
         qual = table.getLoadNodeArchIdQual(archiveId);
         DbUpdateFns.updateLongInteger(DaoDATNodeTbl.getTblName(),
               								DaoDATNodeTbl.getParentIdColName(true),
               								parentId,qual);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
   }
   
   /**
    * Comprueba que el identificador del padre corresponde a un 
    * directorio existente
    * 
    * @param parentId - identificador del padre
    * @return true / false
    * @throws Exception Errores de consulta a base de datos
    */
   private boolean isValidParentId(int parentId) throws Exception
   {
      
      String        tblName, qual = null;
      boolean       valid = false;
      int           count = 0; 
      DirsTable table = new DirsTable();
      
      tblName = table.getDirTableName();
      qual = table.getLoadDirQual(parentId);
      
      if (parentId == 0)
         valid = true;
      else
      {
	      count = DbSelectFns.selectCount(tblName, qual);
	      if (count > 0)
	         valid = true;
      }
      
      return valid;
      
   }
   
   /**
    * Comprueba que el identificador del archivador corresponde
    * a un archivador existente.
    * 
    * @param archId Identificador del archivador
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isValidArchId(int archId) throws Exception
   {
      
      String        tblName, qual = null;
      boolean       valid = false;
      int           count = 0; 
      ArchivesTable table = new ArchivesTable();
      
      tblName = table.getArchHdrTableName();
      qual = table.getLoadArchIdQual(archId);
      
      
      count = DbSelectFns.selectCount(tblName, qual);
      if (count > 0)
         valid = true;
      
      return valid;
      
   }

   /**
    * Obtiene la información de la definición de un índice
    * @param iterator Información de índices
    * @return La información mencionada
    * @throws Exception Errores
    */
   private ArchiveIdxImpl createIdxDef(StringCharacterIterator iterator)
                         throws Exception
   {
      ArchiveIdxImpl idx = null;
      int            id, numFlds, unique, i, fldId;
      String         name;
      boolean        isUnique;
      char           sep = ',';
      char           sepNextIdx = '|';
      ArrayList      flds = new ArrayList();
      
      id = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      name = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      unique = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (unique == 0)
         isUnique = false;
      else
         isUnique = true;
      
      
      numFlds = UtilX.parseInteger(iterator, sep);      
      
      for(i = 0; i < numFlds; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1);
         if (i+1 < numFlds)
            fldId = UtilX.parseInteger(iterator, sep);
         else
            fldId = UtilX.parseInteger(iterator,sepNextIdx);
         
         flds.add(new Integer(fldId));
      }
      
      idx = new ArchiveIdxImpl(id, name, isUnique, flds);
      
      return idx;
   }
   
   /**
    * Obtiene la infomación de la definición de un campo
    * @param iterator Información de campos
    * @return Definición del campo
    * @throws Exception Errores
    */
   private ArchiveFldImpl createFldDef(StringCharacterIterator iterator) 
   							throws Exception
   {
      ArchiveFldImpl fld;
      int            id, type, len, nulls, doc, mult;
      String         name, colName, remarks;
      boolean        isNullable, isDoc, isMult;
      char           sep = ',';
      
      id = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      name = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      type = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      len = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      nulls = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (nulls == 0)
         isNullable = false;
      else
         isNullable = true;
      
      colName = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      doc = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (doc == 0)
         isDoc = false;
      else
         isDoc = true;
      
      mult = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (mult == 0)
         isMult = false;
      else
         isMult = true;
      
      remarks = UtilX.parseString(iterator);      
      
      fld = new ArchiveFldImpl(id, name, type, len, isNullable,
                              colName, isDoc, isMult, remarks);
      
      
      return fld;
   }
   
   /**
    * Obtiene una lista de identificadores de campos documentales.
    * @return Lista de identificadores de campos documentales.
    * @throws Exception Errores
    */
   private ArrayList getFldsIdFromFtsDetail() throws Exception
   {
      StringCharacterIterator iterator  = new StringCharacterIterator(_ftsDetail);
      char                    sep       = '|';
      char                    quoteSep  = ',';
      String                  vers,voidTblName;
      String                  noSepChars,thesName;
      int                     flags;
      int                     numFlds, i;
      int                     fldId,aux;
      ArrayList               ftsFlds = new ArrayList();
            
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //VoidTblName
      voidTblName = UtilX.parseString(iterator,quoteSep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //NoSepChars
      noSepChars = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Flags
      flags = UtilX.parseInteger(iterator,quoteSep);
      UtilX.iteratorIncrementIndex(iterator,1);
      
      //NumFlds
      numFlds = UtilX.parseInteger(iterator,sep);
      
      for(i = 0; i < numFlds; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1); 
         
         //FldId
         fldId = UtilX.parseInteger(iterator,quoteSep);
         UtilX.iteratorIncrementIndex(iterator,1);
         
         ftsFlds.add(new Integer(fldId));
    
         //ThesName
         thesName = UtilX.parseString(iterator,quoteSep);
         UtilX.iteratorIncrementIndex(iterator, 1);
         
         //idxType
         aux = UtilX.parseInteger(iterator,quoteSep);
         UtilX.iteratorIncrementIndex(iterator,1);
         
         //thesType
         aux = UtilX.parseInteger(iterator,sep);         
                  
      }
      
      return ftsFlds;
   }
   
   /**
    * Rellena las estructuras ArchFldsImpl e ArchIdxsImpl de la clase
    * con la información de campos e índices.
    * @throws Exception Errores
    */
   private void fillFldsDetail() throws Exception
   {
      StringCharacterIterator iterator  = new StringCharacterIterator(_fldsIdxsDetail);
      char                    sep       = '|';
      String                  vers;
      int                     numFlds, i, j;
      int                     numIdxs;
      ArchiveFldImpl          fld = null;
      ArchiveIdxImpl          idx = null;
      ArrayList               ftsFlds = new ArrayList();
      
      if (_ftsDetail != null)
         ftsFlds = getFldsIdFromFtsDetail();
      
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Número de campos
      numFlds = UtilX.parseInteger(iterator, sep);
      
      for(i = 0; i < numFlds; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1); 
         
         fld = createFldDef(iterator);
         
         for (j = 0; j < ftsFlds.size(); j++)
         {
            Integer id = (Integer)ftsFlds.get(j);
            if (fld.getId() == id.intValue())
               fld.setDoc(true);
         }
         
         _flds.addFld(fld);         
      }
      //Número de índices
      UtilX.iteratorIncrementIndex(iterator, 1); 
      numIdxs = UtilX.parseInteger(iterator, sep);
      
      for(i = 0; i < numIdxs; i++)
      {
         UtilX.iteratorIncrementIndex(iterator,1);
         
         idx = createIdxDef(iterator);
         
         _idxs.addIdx(idx);
      }
      
   }
   
   /**
    * Rellena la estructa ArchMiscImpl con la información del título de carpetas
    * y lista de volúmenes de esta clase.
    * @throws Exception Errores
    */
   private void fillMiscDetail() throws Exception
   {
      StringCharacterIterator iterator  = new StringCharacterIterator(_miscDetail);
      char                    sep       = ',';
      String                  vers;
      String                  fdrName;
      int                     volListType;
      int                     volListId = SboType.NULL_ID;
           
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Nombre de carpeta
      fdrName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);      
      
      volListType = UtilX.parseInteger(iterator, sep);
      
      if (volListType != ArchiveVolListType.NONE)
      {
         UtilX.iteratorIncrementIndex(iterator, 1);
         volListId = UtilX.parseInteger(iterator, sep);
      }
      
      _misc.setMisc(fdrName, volListId, volListType);

   }
   
   /**
    * Rellena la información de la definición del archivador
    * @throws Exception Errores
    */
   private void fillDetailValues() throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("fillDetailValues");
      
      _flds.clear();
      _idxs.clear();
      
      fillMiscDetail();
      fillFldsDetail(); 
   
   }
   
   /**
    * Obtiene el identificador del padre del archivador
    * @throws Exception Errores
    */
   private void loadParentId() throws Exception
   {
      String        qual;      
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadParentId");
      
      qual = table.getLoadNodeArchIdQual(_id);
      
      _parentId = DbSelectFns.selectLongInteger(DaoDATNodeTbl.getTblName(),
            											DaoDATNodeTbl.getParentIdColName(true),
            											qual);
                           
   }
   
   /**
    * Obtiene el identificador del administrador del archivador.
    * @throws Exception Errores
    */
   private void loadAdminUserId() throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();
      String         qual;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadAdminUserId");
      
      qual = table.getLoadOwnerIdQual(_acsId);
      
      _adminUserId = DbSelectFns.selectLongInteger(table.getOwnershipTableName(),
            													table.getOwnerIdColumnName(),
            													qual);
   }
   
   /**
    * Obtiene los identificadores de los usuarios suceptibles de ser
    * administradores del archivador
    * @throws Exception Errores
    */
   private void loadAdminUsersId() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      UsersTable     usrTbl = new UsersTable();
      int            counter;
      BasicUserImpl  user;
      String         qual;
      
      //    Cargamos posibles usuarios administradores

      try
      {
	      
	      if (_isLdap)
	      {
	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(LdapUsersTable.class.getName());
	         qual = table.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
	      }
	      else
	      {
	         tableInfo.setTableObject(usrTbl);
	         tableInfo.setClassName(UsersTable.class.getName());
	         qual = usrTbl.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
	      }
	         
	      tableInfo.setTablesMethod("getUserAdminTableNames");
	      tableInfo.setColumnsMethod("getAdminUserColumnNames");
	      
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();
	      rowInfo.setClassName(BasicUserImpl.class.getName());
	      rowInfo.setValuesMethod("loadValues");
	      rowsInfo.add(rowInfo);
	      
	      DynamicFns.selectMultiple(qual, true, tableInfo, rowsInfo);
	                                
	      for (counter = 0; counter < rowInfo.getRowCount(); counter++)
	      {
	         user = (BasicUserImpl)rowInfo.getRow(counter);
	         _adminUsers.add(user);
	      }
	            
	      
	   }
	   catch (Exception e)
		{
	      _logger.error(e);
	      throw e;
		}
   }
   
   /**
    * Carga la definición base del archivador.
    * @throws Exception Errores
    */
   private void loadArchiveHdr() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ArchivesTable table = new ArchivesTable();      
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadArchiveHdr");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchHdrTableName");         
         tableInfo.setColumnsMethod("getAllArchHdrColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());            
         rowInfo.setValuesMethod("loadAllArchHdrValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(table.getLoadArchIdQual(_id), tableInfo, rowsInfo))
         {
            AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NOT_EXISTS);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Carga la información de campos documentales del archivador.
    * @throws Exception Errores
    */
   private void loadFtsDetail() throws Exception
   {
      String        qual;
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadFtsDetail");
      
      try
      {
         qual = table.getLoadDetailArchiveQual(_id, DaoArchDetType.DET_TYPE_FTS_DEF);
      
         _ftsDetail = DbSelectFns.selectLongText(table.getArchDetTableName(),
               										DaoArchDetTbl.getDetValColName(true),qual);
      }
      catch(Exception e)
      { 
         String Err;
         
         Err = ((IeciTdException)e).getErrorCode();
         if ( Err.compareTo("IECI_TECDOC_CORE_DATABASE_NOT_FOUND") != 0)
         {
            _logger.error(e); 
            throw e;
         }  
      }
      
   }
   
   /** 
    * Carga la información de miscelánea del archivador.
    * @throws Exception Errores
    */
   private void loadMiscDetail() throws Exception
   {
      
      String        qual;
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadMiscDetail");
      
      qual = table.getLoadDetailArchiveQual(_id,DaoArchDetType.DET_TYPE_MISC_DEF);
      
      _miscDetail = DbSelectFns.selectLongText(table.getArchDetTableName(),
                 						DaoArchDetTbl.getDetValColName(true),qual);    
     
      

   }
   
   /**
    * Carga la información de campos e índices del archivador.
    * @throws Exception Errores
    */
   private void loadFldsDetail() throws Exception
   {
      String        qual;
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadFldsDetail");
      
      qual = table.getLoadDetailArchiveQual(_id,DaoArchDetType.DET_TYPE_FLD_DEF);
      
      _fldsIdxsDetail = DbSelectFns.selectLongText(table.getArchDetTableName(),DaoArchDetTbl.getDetValColName(true),qual);    
     
   }
   
   /**
    * Carga toda la definición del archivador.
    * @throws Exception Errores
    */
   private void loadDetailsArchive() throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadDetailsArchive");
     
      loadFldsDetail();
      loadMiscDetail();
      loadFtsDetail();
      
   }
   
   /**
    * Obtiene en formato texto la información miscelánea (título de carpeta, 
    * lista de volúmenes) del archivador.
    * @param isUpdate true / false
    * @return La información mencionada.
    * @throws Exception Errores
    */
   private String getMiscDetValue(boolean isUpdate) throws Exception
   {
      String          value;
      ArchiveMiscImpl misc; 
      String          ver = Defs.ARCH_MISC_DETAIL_VERSION;
      
      if (isUpdate)
         misc = (ArchiveMiscImpl)_updInfo.getMiscDef();
      else
         misc = _misc;
      
      value = "\"" + ver + "\"|";
      value = value + "\"" + misc.getFdrName() + "\"|";
      
      if (misc.getVolListType() == 0)
         value = value + Integer.toString(misc.getVolListType());
      else
         value = value + Integer.toString(misc.getVolListType()) + "," +
                 Integer.toString(misc.getVolListId());      
      
            
      return value;
      
   }
   
   /**
    * Modifica la estructura de índices con los identificador de los campos modificador
    * 
    * @param fldIdOld Antiguo identificador de campo
    * @param fldIdNew Nuevo identificador de campo
    * @param idxsDef  Estructura de índices
    */
   private void changeFldIdInIdxsDef(int fldIdOld, int fldIdNew, ArchiveIdxsImpl idxsDef)
   {
      boolean        find = false;
      
      for (int i = 0; i < idxsDef.count(); i++)
      {
         ArrayList      fldsId = null;
         ArchiveIdxImpl idxDef = (ArchiveIdxImpl)idxsDef.get(i);
         
         fldsId = idxDef.getFldsId();
         find = false;
         for (int j = 0; j < fldsId.size(); j++)
         {
            Integer id = (Integer)fldsId.get(j);
            if (id.intValue() == fldIdOld)
            {
               find = true;
               fldsId.remove(j);
               break;
            }            
         }
         if (find)
            fldsId.add(new Integer(fldIdNew));         
       }
   }
   
   /**
    * Obtiene las estructuras normalizadas de la definición de campos e índices.
    * 
    * @param flds Estructura de campos, es parámetro de vuelta tiene que estar instanciado
    * @param idxs Estructura de índices, es parámetro de vuelta tiene que estar instanciado
    * @throws Exception Errores
    */
   private void getUpdFldsIdxsDef(ArchiveFldsImpl flds, ArchiveIdxsImpl idxs) throws Exception
   {
      ArchiveFldsImpl updFlds = (ArchiveFldsImpl)_updInfo.getFldsDef();
      ArchiveIdxsImpl updIdxs = (ArchiveIdxsImpl)_updInfo.getIdxsDef();
      ArrayList       delFlds = _updInfo.getDeleteFlds();
      ArrayList       delIdxs = _updInfo.getDeleteIdxs();
      boolean         find = false;
      int             i,j,h;
      
      if (flds == null || idxs == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      flds.clear();
      idxs.clear();      
      
      //se tratan los índices eliminados y se comprueba que los campos que
      //forman parte del índice no han sido eliminados
      for (i = 0; i < updIdxs.count(); i++)
      {
         ArchiveIdxImpl updIdx = (ArchiveIdxImpl)updIdxs.get(i);
         find = false;
         for (j = 0; j < delIdxs.size(); j++)
         {
            if (delIdxs.get(j).equals(new Integer(updIdx.getId())))
            {
               find = true;
            }
         }
         if (!find)
         {
            ArrayList fldsId = updIdx.getFldsId();
            for (j = 0; j < fldsId.size(); j++)
            {
               Integer id = (Integer)fldsId.get(j);
               for (h = 0; h < delFlds.size(); h++)
               {
                  if (id.equals(delFlds.get(h)))
                     AdminException.throwException(ArchiveErrorCodes.EC_ARCH_IDXS_DEL_FIELDS);
               }
            }
               
            idxs.add(updIdx.getName(),updIdx.isUnique(),updIdx.getFldsId());            				
         }
      }
            
      // se tratan los campos eliminados
      for (i = 0; i < updFlds.count(); i++)
      {
         ArchiveFldImpl updFld = (ArchiveFldImpl)updFlds.get(i);
         find = false;
         for (j = 0; j < delFlds.size(); j++)
         {
            if (delFlds.get(j).equals(new Integer(updFld.getId())))
            {
               find = true;
            }
         }
         if (!find)
         {            
            int fldIdOld, fldIdNew;
            //Hay que comprobar si forma parte de un índice por si cambia el identificador
            //poner el correcto
            flds.add(updFld.getName(),updFld.getType(),updFld.getLen(),updFld.isNullable(),
                  	updFld.isDoc(),updFld.isMult(),updFld.getRemarks());
            
            fldIdOld = updFld.getId();
            fldIdNew = flds.getFldIdByName(updFld.getName());
            if (fldIdOld != fldIdNew)
               changeFldIdInIdxsDef(fldIdOld, fldIdNew, idxs);
         }
      }
      
   }
   
   
   
   /**
    * Obtiene la totalidad de los campos de archivador actualizado
    * teniendo en cuenta los campos eliminado e insertados
    * 
    * @return  Definición de campos del archivador     
    * @throws Exception Errores
    */
   private ArchiveFldsImpl getUpdFldsDef() throws Exception
   {
      ArchiveFldsImpl flds = new ArchiveFldsImpl();
      ArchiveFldsImpl updFlds = (ArchiveFldsImpl)_updInfo.getFldsDef();
      ArrayList       delFlds = _updInfo.getDeleteFlds();
      boolean         find = false; 
      
      for (int i = 0; i < updFlds.count(); i++)
      {
         ArchiveFldImpl updFld = (ArchiveFldImpl)updFlds.get(i);
         
         find = false;
         for (int j = 0; j < delFlds.size(); j++)
         {
            if (delFlds.get(j).equals(new Integer(updFld.getId())))
            {
               find = true;
            }
         }
         if (!find)
         {  
            flds.add(updFld.getName(),updFld.getType(),updFld.getLen(),updFld.isNullable(),
                  	updFld.isDoc(),updFld.isMult(),updFld.getRemarks());
         }
      }
      
      return (flds);
   }
   
   /**
    * Obtiene en formato texto la información de campos e índices del archivador.
    * @param isUpdate true / false
    * @return La información mencionada
    * @throws Exception Errores
    */
   private String getFieldsDetValue(boolean isUpdate) throws Exception
   {
      String          value;
      ArchiveFldsImpl flds = null;
      ArchiveIdxsImpl idxs = null;
      String          ver = Defs.ARCH_DETAIL_VERSION;
      
      if (isUpdate)
      {
         //Hay que tener en cuenta los eliminados y los nuevos
         flds = new ArchiveFldsImpl();
         idxs = new ArchiveIdxsImpl();
         getUpdFldsIdxsDef(flds, idxs);                  
      }
      else
      {
         flds = _flds;
         idxs = _idxs;
      }
      
      //campos
      value = "\"" + ver + "\"|" + Integer.toString(flds.count()) + "|";
      for (int i = 0; i < flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)flds.get(i);
         value = value + Integer.toString(fld.getId()) + ",";
         value = value + "\"" + fld.getName() + "\",";
         value = value + Integer.toString(fld.getType()) + ",";
         value = value + Integer.toString(fld.getLen()) + ",";
         
         if (fld.isNullable())
            value = value + "1,";
         else
            value = value + "0,";
         
         value = value + "\"" + fld.getColName() + "\",";
         
         if (fld.isDoc())
            value = value + "1,";
         else
            value = value + "0,";
         
         if (fld.isMult())
            value = value + "1,";
         else         
            value = value + "0,";
      
         
         value = value + "\"" + fld.getRemarks() + "\"" + "|";
      }
      //indices
      value = value + Integer.toString(idxs.count()) + "|";
      
      for (int j = 0; j < idxs.count(); j++)
      {
         ArchiveIdxImpl idx = (ArchiveIdxImpl)idxs.get(j);
         if (j > 0)
            value = value + "|";
         value = value + Integer.toString(idx.getId()) + ",";
         value = value + "\"" + idx.getName() + "\",";
         
         if (idx.isUnique())
            value = value + "1,";
         else
            value = value + "0,";
         
         value = value + Integer.toString(idx.getFldsId().size()) + ",";
         
         for (int h = 0; h < idx.getFldsId().size(); h++)
         {
            Integer fldId = (Integer)idx.getFldsId().get(h);
            value = value + fldId.toString();
            if (h + 1 < idx.getFldsId().size())
               value = value + ",";
         }
      }
   
      return value;
   }
   
   /**
    * Obtiene en formato texto la información de campos documentales del archivador.
    * @param isUpdate true / false
    * @return La información mencionada.
    * @throws Exception Errores
    */
   private String getFtsDetValue(boolean isUpdate) throws Exception
   {
      String          value;
      ArchiveFldsImpl flds = null;  
      ArchiveFldsImpl ftsFlds = null;
      String          ver = Defs.ARCH_DETAIL_VERSION;
      
      if (isUpdate)//Hay que tener en cuenta los eliminados y los nuevos
         flds =  getUpdFldsDef();                  
      else
         flds = _flds;         
      
      ftsFlds = flds.getFtsFlds();
      
      //campos
      value = "\"" + ver + "\"|\"\",\"\",0," +
             new Integer(ftsFlds.count()).toString();
      
      for (int i = 0; i < ftsFlds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)ftsFlds.get(i);         
         
         value = value + "|";
         value = value + new Integer(fld.getId()).toString();
         value = value + ",\"\",1,0";
      }
      
      return value;
   }
   
   /**
    * Verifica si un el archivador tiene carpetas y si no hay
    * creadas tablas de validación sobre alguno de sus campos.
    *  
    * @throws Exception Si ocurre alguno de los casos anteriores.
    */
    
   private void checkCanDeleteArchive() throws Exception
   {  
      int count;
      String tblName;
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("checkCanDeleteArchive");
      
      tblName = DaoUtil.getFdrHdrTblName(_tblPrefix);
      
      count = DbSelectFns.selectCount(tblName, null);
      if (count > 0)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_HAS_FOLDERS);
      }
      
      count = DbSelectFns.selectCount(table.getBTblCtlgTableName(), 
            								  table.getLoadBTblCtrlTableNameQual(tblName));
      if (count > 0)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_HAS_VALIDATION_TABLE);
      }
    
   }

	/**
    * Maneja la información de actualización de objetos.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * nodo.
    */
    
   private void insertObjInfo() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertObjInfo");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getOwnershipTableName");
         tableInfo.setColumnsMethod("getAllOwnershipColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("insertObjValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   /**
    * Inserta la parte base de las tablas de archivador (header).
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * archivador.
    */
    
   private void insertBase() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchHdrTableName");
         tableInfo.setColumnsMethod("getAllArchHdrColumnNames");
         
         _creationDate = DbUtil.getCurrentDateTime();
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("insertArchHdrValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Actualiza información sobre el propietario del archivador
    * @throws Exception Errores en la actualización.
    */
   private void updateObjInfo() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertObjInfo");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getOwnershipTableName");
         tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("updateObjValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getSelectOwnerQual(_acsId), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Actualiza información básica del archivador.
    * 
    * @throws Exception Errores en la actualización
    */
   private void updateArchHdr() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ArchivesTable table = new ArchivesTable();
      String        qual;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchHdr");

      try 
      {
         qual = table.getLoadArchIdQual(_id);  
         _updaterId = _userConnected;
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchHdrTableName");
         tableInfo.setColumnsMethod("getUpdateArchHdrColNames");
         
         _updateDate = DbUtil.getCurrentDateTime();
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("updateArchHdrValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(qual, tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Carga el identificador de acceso del archivador.
    * @throws Exception Errores en la carga
    */
   private void loadAcsIdFromArchId() throws Exception
   {
      String        qual;
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadAcsIdFromArchId");
      
      qual = table.getLoadArchIdQual(_id);
      
      _acsId = DbSelectFns.selectLongInteger(table.getArchHdrTableName(),
                         DaoArchHdrTbl.getAcsIdColName(true),qual);      
   }
   
   /**
    * Verifica si existe un archivador con ese nombre y el mismo padre antes
    * de insertarlo.
    *  
    * @throws Exception Si el archivador existe.
    */
    
   private void checkArchiveExists() throws Exception
   {
      int count;
      ArchivesTable table = new ArchivesTable();
      DirsTable tableNode = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("checkArchiveExists");
      
      count = DbSelectFns.selectCount(table.getArchHdrTableName(), 
                                      table.getCountArchIdQual(_id, _name));
      if (count > 0)
      {
         count = DbSelectFns.selectCount(tableNode.getNodeTableName(), 
                                 tableNode.getCountDirNodeQual(_parentId));
         if (count > 0)
            AdminException.throwException(ArchiveErrorCodes.EC_ARCH_EXITS);
      }
      
   }
   
      
   
   /**
    * Inserta el archivador en el nodo correspondiente.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * nodo.
    */
    
   private void insertNode() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertNode");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getNodeTableName");
         tableInfo.setColumnsMethod("getInsertNodeColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("insertNodeValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
    /**
    * Se inserta en la tabla IDOCARCHDET la definición del archivador
    * 
    * @throws Exception si se produce algún error en la insercción
    */
   private void insertDetails() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo1 = new DynamicRow();
      DynamicRow rowInfo2 = new DynamicRow();
      DynamicRow rowInfo3 = new DynamicRow();
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertDetails");

      
      try
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchDetTableName");
         tableInfo.setColumnsMethod("getAllArchDetColumnNames");
                  
         rowInfo1.addRow(this);
         rowInfo1.setClassName(ArchiveImpl.class.getName());
         rowInfo1.setValuesMethod("insertFieldsDetValues");
         rowsInfo.add(rowInfo1);
         
         
         rowInfo2.addRow(this);
         rowInfo2.setClassName(ArchiveImpl.class.getName());
         rowInfo2.setValuesMethod("insertMiscDetValues");
         rowsInfo.add(rowInfo2);

         rowInfo3.addRow(this);
         rowInfo3.setClassName(ArchiveImpl.class.getName());
         rowInfo3.setValuesMethod("insertFtsDetValues");
         rowsInfo.add(rowInfo3);
         

         DynamicFns.insert(tableInfo, rowsInfo);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
   }
   
   /**
    * Actualiza la información de miscelánea del archivador.
    * 
    * @throws Exception Errores en la actualización.
    */
   private void updateMiscDetail() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();      
      ArchivesTable table = new ArchivesTable();
      String        qual  = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateMiscDetail");

      
      try
      {
         qual = table.getLoadDetailArchiveQual(_id,DaoArchDetType.DET_TYPE_MISC_DEF);
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchDetTableName");
         tableInfo.setColumnsMethod("getupdateArchDetColumnNames");
                  
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("updateMiscDetValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(qual,tableInfo, rowsInfo);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
            
   }

   
   /**
    * Se actualiza en la tabla IDOCARCHDET la definición de campos e índices del archivador
    * 
    * @throws Exception si se produce algún error en la actualización
    */
   private void updateFldsDetail() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();      
      ArchivesTable table = new ArchivesTable();
      String        qual  = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateFldsDetail");

      
      try
      {
         qual = table.getLoadDetailArchiveQual(_id,DaoArchDetType.DET_TYPE_FLD_DEF);
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchDetTableName");
         tableInfo.setColumnsMethod("getupdateArchDetColumnNames");
                  
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("updateFieldsDetValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(qual,tableInfo, rowsInfo);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
   }
   
   /**
    * Se actualiza en la tabla IDOCARCHDET la definición de campos documentales
    * del archivador.
    * 
    * @throws Exception si se produce algún error en la actualización
    */
   private void updateFtsDetail() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();      
      ArchivesTable table = new ArchivesTable();
      String        qual  = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateFtsDetail");

      
      try
      {
         qual = table.getLoadDetailArchiveQual(_id,DaoArchDetType.DET_TYPE_FTS_DEF);
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getArchDetTableName");
         tableInfo.setColumnsMethod("getupdateArchDetColumnNames");
                  
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchiveImpl.class.getName());
         rowInfo.setValuesMethod("updateFtsDetValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(qual,tableInfo, rowsInfo);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
   }
   
   /**
    * Inicializa las tablas creadas del archivador.
    * 
    * @throws Exception Errores en la inicialización.
    */
   private void initArchTbls() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo1 = new DynamicRow();
      DynamicRow rowInfo2 = new DynamicRow();
      DynamicRow rowInfo3 = new DynamicRow();
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertArchTbls");

      
      try
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(ArchivesTable.class.getName());
         tableInfo.setTablesMethod("getXNextIdTableName");
         tableInfo.setColumnsMethod("getAllXNextIdColumnNames");
         
         rowInfo1.addRow(this);
         rowInfo1.setClassName(ArchiveImpl.class.getName());
         rowInfo1.setValuesMethod("insertXNextIdFolderValues");
         rowsInfo.add(rowInfo1);
         
         rowInfo2.addRow(this);
         rowInfo2.setClassName(ArchiveImpl.class.getName());
         rowInfo2.setValuesMethod("insertXNextIdXFValues");
         rowsInfo.add(rowInfo2);
         
         rowInfo3.addRow(this);
         rowInfo3.setClassName(ArchiveImpl.class.getName());
         rowInfo3.setValuesMethod("insertXNextIdDocValues");
         rowsInfo.add(rowInfo3);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }      
   }
   
   /**
    * Actualiza la lista de volúmenes asociada al archivador.
    * 
    * @throws Exception Errores en la actualización.
    */
   private void updateListVols() throws Exception
   {
      DynamicTable    tableInfo = new DynamicTable(); 
      DynamicRows     rowsInfo = new DynamicRows();
      DynamicRow      rowInfo = new DynamicRow();
      ArchivesTable   table = new ArchivesTable();
      String          qual;
      ArchiveMiscImpl misc = (ArchiveMiscImpl)_updInfo.getMiscDef();
      
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateListVols");

      
      try
      {
         if (misc.getVolListId() > 0)
         {
            qual = table.getLoadArchListArchIdQual(_id);
	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(ArchivesTable.class.getName());
	         tableInfo.setTablesMethod("getArchListTableName");
	         tableInfo.setColumnsMethod("getUpdateArchListColumnNames");
	         
	         rowInfo.addRow(this);
	         rowInfo.setClassName(ArchiveImpl.class.getName());
	         rowInfo.setValuesMethod("updateArchListValues");
	         rowsInfo.add(rowInfo);
	         
	         DynamicFns.update(qual,tableInfo, rowsInfo);
         }
         
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }   
   }
   
   /**
    * Inserta la relación Identificador de lista de volúmenes Identificador
    * de archivador.
    * 
    * @throws Exception Errores en la insercción.
    */
   private void insertListVols() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertListVols");

      
      try
      {
         if (_misc.getVolListId() > 0)
         {
	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(ArchivesTable.class.getName());
	         tableInfo.setTablesMethod("getArchListTableName");
	         tableInfo.setColumnsMethod("getAllArchListColumnNames");
	         
	         rowInfo.addRow(this);
	         rowInfo.setClassName(ArchiveImpl.class.getName());
	         rowInfo.setValuesMethod("insertArchListValues");
	         rowsInfo.add(rowInfo);
	         
	         DynamicFns.insert(tableInfo, rowsInfo);
         }
         
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }   
   }
   
   /**
    * Crea tabla AxNextId para un archivador en concreto y su índice.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   
   private void createANextIdTable(String tableName) throws Exception
   {      
      DaoXNIdTbl xNIdTbl; 
      
      if (_logger.isDebugEnabled())
         _logger.debug("createANextIdTable");
      
      xNIdTbl = new DaoXNIdTbl(tableName);
      xNIdTbl.createTable();
   }
   
   /**
    * Crea tabla AxFdrH para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createFdrHdrTable(String tableName) throws Exception
   {
      DaoFdrHdrTbl fdrHTbl;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createFdrHdrTable");
      
      fdrHTbl = new DaoFdrHdrTbl(tableName);
      fdrHTbl.createTable();
   }
   
   /**
    * Crea tabla AxSF para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createRelFldsTable(String tableName,ArchiveFldsImpl flds, ArchiveIdxsImpl idxs) 
   				throws Exception
   {
      DaoRelFldsTbl  relFldsTbl = null;
      DbColumnDef[]  colsDef = null;
      DbColumnDef    colDef = null;  
      DbIndexDef[]   idxsDef = null;
      DbIndexDef     idxDef = null;
      int            dataType;
      int            idxCol;
      String         nameIdx;
      String         colNamesIdx = "";
      ArrayList      colIdsIdx;
      
      if (flds == null || idxs == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      if (_logger.isDebugEnabled())
         _logger.debug("createRelFldsTable");
      
      colsDef = new DbColumnDef[flds.getRelFldsCount()];
      idxCol = 0;
      for(int i = 0; i < flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl) flds.get(i);
         if (fld.isRel())
         {            
            dataType = ArchiveFldType.getDbDataType(fld.getType());
            if ( fld.getType() == DbDataType.SHORT_TEXT )
            {
               colDef = new DbColumnDef(fld.getColName(), dataType, fld.getLen(), fld.isNullable());
            }
            else
            {
               colDef = new DbColumnDef(fld.getColName(), dataType, fld.isNullable());
            }
            
            colsDef[idxCol] = colDef;
            idxCol = idxCol + 1;
         }
                 
      }
      //REVISAR,REVISAR,REVISAR
      idxsDef = new DbIndexDef[idxs.count()];
      
      for (int i = 0; i < idxs.count(); i++)
      {
         ArchiveIdxImpl idx = (ArchiveIdxImpl) idxs.get(i);
         nameIdx = tableName + Integer.toString( i+1);
         colIdsIdx = idx.getFldsId();
         colNamesIdx = "";
         for (int j = 0; j < colIdsIdx.size(); j++)
         {
            Integer FldId = (Integer)colIdsIdx.get(j);
            if (colNamesIdx.length() == 0)
               colNamesIdx = "FLD" + FldId.toString();
            else
               colNamesIdx = colNamesIdx + ",FLD" + FldId.toString();
         }
         idxDef = new DbIndexDef(nameIdx,colNamesIdx,idx.isUnique(), false);
         
         idxsDef[i] = idxDef;
      
      }
      relFldsTbl = new DaoRelFldsTbl(tableName,colsDef);
      relFldsTbl.createTable(idxsDef);
      
   }
   
   /**
    * Crea tabla AxXF para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */   
   private void createExtFldTable(String tableName) throws Exception
   {
      DaoExtFldsTbl extFldsTbl;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createExtFldTable");
      
      extFldsTbl = new DaoExtFldsTbl(tableName);
      extFldsTbl.createTable();
   
   }
   
   /**
    * Crea tabla AxCLFH ó AxCLFHX para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   
   private void createDividerTable(String tableName) throws Exception
   {
      DaoClfTbl clfTbl;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createDividerTable");
      
      clfTbl = new DaoClfTbl(tableName);
      clfTbl.createTable();
      
   }
   
   /**
    * Crea tabla AxDOCH ó AxDOCHX para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createDocumentTable(String tableName) throws Exception
   {
      DaoDocTbl docTbl;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createDocumentTable");
      
      docTbl = new DaoDocTbl(tableName);
      docTbl.createTable();
   }
   
   /**
    * Crea tabla AxPAGEH ó AxPAGEHX para un archivador en concreto y sus índices.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createPageTable(String tableName) throws Exception
   {
      DaoPageTbl pageTbl;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createPageTable");
      
      pageTbl = new DaoPageTbl(tableName);
      pageTbl.createTable();
   }
   
   /**
    * Crea tabla multivalor e indices para un archivador y tipo de dato
    * en concreto.
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createMultTbl(String tableName,int DbDataType) throws Exception
   {
      DaoMultFldTbl MTbl;      
      
      if (_logger.isDebugEnabled())
         _logger.debug("createMultTbl");
      
      MTbl = new DaoMultFldTbl(tableName,DbDataType);
      MTbl.createTable();
   }
   
   /**
    * Crea tablas multivalor e índices para un archivador en concreto.
    *  
    * @throws Exception Si se produce algún error en la creación de la tabla. 
    * 
    */
   private void createMultivalTbls(ArrayList allTbls) throws Exception
   {
      boolean crtMTxtTbl = false;
      boolean crtMDecTbl = false;
      boolean crtMIntTbl = false;
      boolean crtMDateTbl = false;
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("createMultivalTbls");
      
      for(int i = 0; i < _flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)_flds.get(i);
         if (fld.isMult())
         {
            switch(fld.getType())
            {
         		case ArchiveFldType.SHORT_TEXT:    crtMTxtTbl  = true; break;
	         	case ArchiveFldType.DATE:
	         	case ArchiveFldType.DATE_TIME:
	         	case ArchiveFldType.TIME:          crtMDateTbl = true; break;
	         	case ArchiveFldType.SHORT_DECIMAL:
	         	case ArchiveFldType.LONG_DECIMAL:  crtMDecTbl  = true; break;
	         	case ArchiveFldType.SHORT_INTEGER:
	         	case ArchiveFldType.LONG_INTEGER:  crtMIntTbl  = true; break;
            }
         }
      }
         
      if (crtMTxtTbl)
      {            
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_TEXT);
         createMultTbl(tableName,DbDataType.SHORT_TEXT);   
         allTbls.add(tableName);
      }
      if (crtMDateTbl)
      {            
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.DATE_TIME);
         createMultTbl(tableName,DbDataType.DATE_TIME);
         allTbls.add(tableName);
      }
      if (crtMDecTbl)
      {            
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_DECIMAL);
         createMultTbl(tableName,DbDataType.SHORT_DECIMAL);
         allTbls.add(tableName);
      }
      if (crtMIntTbl)
      {            
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_INTEGER);
         createMultTbl(tableName,DbDataType.SHORT_INTEGER);
         allTbls.add(tableName);
      }
    
      
   }
   /**
    * Elimina tablas e índices creados para el archivador en concreto.
    *  
    * @throws Exception Si se produce algún error en la creación de tablas. 
    * 
    */
   
   private void dropArchTbls(ArrayList allTbls) throws Exception
   {
       if (_logger.isDebugEnabled())
         _logger.debug("dropArchTbls");
      
      for(int i = 0; i < allTbls.size(); i++)
      {
         String nameTbl = (String)allTbls.get(i);
         
         DbTableFns.dropTable(nameTbl);        
      }      
      
      
   }
   
   /**
    * Elimina tabla multivalor para campos de tipo texto.
    * 
    * @throws Exception Errores
    */
   private void dropTxtMultTbl() throws Exception
   {
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("dropTxtMultTbl");
      
      try
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_TEXT);
         DbTableFns.dropTable(tableName);
      }
      catch(Exception e)
      {
         _logger.error(e);
      }
   }
   
   /**
    * Elimina tabla multivalor para campos de tipo entero.
    * 
    * @throws Exception Errores
    */
   private void dropIntMultTbl() throws Exception
   {
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("dropIntMultTbl");
      
      try
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_INTEGER);
         DbTableFns.dropTable(tableName);
      }
      catch(Exception e)
      {
         _logger.error(e);
      }
   }
   
   /**
    * Elimina tabla multivalor para campos de tipo decimal.
    * 
    * @throws Exception Errores
    */
   private void dropDecMultTbl() throws Exception
   {
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("dropDecMultTbl");
      
      try
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_DECIMAL);
         DbTableFns.dropTable(tableName);
      }
      catch(Exception e)
      {
         _logger.error(e);
      }
   }
   
   /**
    * Elimina tabla multivalor para campos de tipo fecha-hora
    * 
    * @throws Exception Errores
    */
   private void dropDateMultTbl() throws Exception
   {
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("dropDateMultTbl");
      
      try
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.DATE_TIME);
         DbTableFns.dropTable(tableName);
      }
      catch(Exception e)
      {
         _logger.error(e);
      }
   }
   
   /**
    * Elimina todas las tablas del archivador.
    * 
    * @throws Exception Errores
    */
   private void dropArchTbls() throws Exception
   {
      String tableName;
      
      if (_logger.isDebugEnabled())
         _logger.debug("dropArchTbls");
      
      try
      {
         tableName = DaoUtil.getXNIdTblName(_tblPrefix); 
         DbTableFns.dropTable(tableName);
         
         tableName = DaoUtil.getFdrHdrTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         tableName = DaoUtil.getRelFldsTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         
         tableName = DaoUtil.getExtFldTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         tableName = DaoUtil.getDividerTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         
         tableName = tableName + "X";
         DbTableFns.dropTable(tableName);
         
         tableName = DaoUtil.getDocumentTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         tableName = tableName + "X";
         DbTableFns.dropTable(tableName);
         tableName = DaoUtil.getPageTblName(_tblPrefix);
         DbTableFns.dropTable(tableName);
         tableName = tableName + "X";
         DbTableFns.dropTable(tableName);
         
         dropTxtMultTbl();
         dropIntMultTbl();
         dropDecMultTbl();
         dropDateMultTbl();         
         
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }
   }
   /**
    * Crea tablas para el archivador (10) más tablas multivalor necesarias.
    *  
    * @throws Exception Si se produce algún error en la creación de tablas. 
    * 
    */   
   private void createArchTbls(ArrayList allTbls) throws Exception
   {   
      String tableName;      
      
      if (_logger.isDebugEnabled())
			_logger.debug("createArchTbls");
      
      try
      {        
      
         //Tablas e índice para la obtención de los nextId de 
         //clasificadores y páginas
         tableName = DaoUtil.getXNIdTblName(_tblPrefix);       
         createANextIdTable(tableName);
         allTbls.add(tableName);
         
         //Tablas de cabeceras de carpeta
         tableName = DaoUtil.getFdrHdrTblName(_tblPrefix);
         createFdrHdrTable(tableName);
         allTbls.add(tableName);
         
         //Crear tabla relacional
         tableName = DaoUtil.getRelFldsTblName(_tblPrefix);
         createRelFldsTable(tableName,_flds,_idxs);
         allTbls.add(tableName);
         
         //Crear tabla extendida
         tableName = DaoUtil.getExtFldTblName(_tblPrefix);
         createExtFldTable(tableName);
         allTbls.add(tableName);
         
         //Tablas de Clasificadores
         tableName = DaoUtil.getDividerTblName(_tblPrefix);
         createDividerTable(tableName);
         allTbls.add(tableName);
         
         tableName = tableName + "X";
         createDividerTable(tableName);
         allTbls.add(tableName);
         
         //Tablas de Documentos
         tableName = DaoUtil.getDocumentTblName(_tblPrefix);
         createDocumentTable(tableName);
         allTbls.add(tableName);
         
         tableName = tableName + "X";
         createDocumentTable(tableName);
         allTbls.add(tableName);
         
         //Tablas de páginas de documentos
         tableName = DaoUtil.getPageTblName(_tblPrefix);
         createPageTable(tableName);
         allTbls.add(tableName);
         
         tableName = tableName + "X";
         createPageTable(tableName);
         allTbls.add(tableName);
         
         //Tabla de campos multivalores si existen         
         if (_flds.getMultFldsCount() > 0)
         {
             createMultivalTbls(allTbls);
         }
         
         
      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }           
      
   }
   
   /**
    * Valida la información del archivador antes de las operaciones
    * de insercción y actualización.
    * 
    * @param update true / false
    * @throws Exception Errores en la validación.
    */
   private void validateArchive(boolean update) throws Exception
   {
      String          name, remarks;
      ArchiveFldsImpl flds;
      ArchiveIdxsImpl idxs;
      boolean         ftsInContents = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("validateArchive");
      
      if (update)
      {
         name = _updInfo.getName();
         remarks = _updInfo.getRemarks();
         flds = new ArchiveFldsImpl();
         idxs = new ArchiveIdxsImpl();
         getUpdFldsIdxsDef(flds, idxs);
         ftsInContents = _updInfo.isFtsInContents();
      }
      else
      {
         name = _name;
         remarks = _remarks;
         flds = _flds;
         idxs = _idxs;
         ftsInContents = this.isFtsInContents();
      }
      
      if (flds.count() <= 0 )
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_FLDS);
      }
      
      if (name.length() <= 0)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_NO_NAME);
      }
      
      if ((remarks != null && remarks.indexOf("\"") >= 0) || flds.areValidRemarks())
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_REMARKS_EXIST_QUOTES);
      }
      
      //se valida que los índice no hacen referencia a campos extendidos ó multivalores
      
      if (!isValidIdxsDef(flds,idxs))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_IDXS_BAD_FIELDS);
      }
      
      if (!isValidFTS(ftsInContents,flds))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_NO_CONFIG_FTS);
      }
      
      // se valida que los campos de tipo texto tengan la longitud correcta
      if (!isValidTxtFldsDef(flds))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_FLD_TXT_BAD_LEN);
      }
      
      //se valida que los campos documentales sean SHORT_TEXT ó LONG_TEXT
      if (!isValidFtsFldsDef(flds))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_FTS_BAD_FIELDS);
      }
      
      //se valida si estamos actualizando que los campos que queremos modificar ó
      //eliminar, no tengan una tabla de validación a partir de ellos
      
      //no se valida que los campos tengan una tabla de validación asociada
          //ESTO TENDRÍA QUE CONTROLARSE!!!!!
      if( update && _updInfo.isModifyDefFlds() && existsValidation() )
      {  
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_EXISTS_FLDS_WITH_VALIDATION);
      }
         
               
   }
   
   /**
    * Obtiene si existe alguna tabla de validación sobre los campos del archivador.
    * 
    * @return true / false
    * @throws Exception Errores
    */
   private boolean existsValidation() throws Exception
   {
      ArchivesTable table = new ArchivesTable();
      boolean       exists = false;
      int           count;
      String        tblName;
      
      tblName = DaoUtil.getFdrHdrTblName(_tblPrefix);
      
      count = DbSelectFns.selectCount(table.getBTblCtlgTableName(), 
				  table.getLoadBTblCtrlTableNameQual(tblName));
      if (count > 0)
         exists = true;
      
      return exists;
   }
   
   
   /**
    * Obtiene si se ha configurado algún motor documental en el sistema invesDoc
    * 
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isFtsConfig() throws Exception
   {
      
      boolean ftsConfig = false;
      CfgFtsConfig config;
      
      config = CfgMdoConfig.loadDbFtsCfg(); 
      
      //HAY que controlar que el motor documental corresponde con la BD
      if (config.m_engine > 0)
         ftsConfig = true;
      
      return ftsConfig;
      
   }
   
   /**
    * Obtiene si es válida la definición de campos documentales ( existe configuración
    * de un motor documental).
    * @param ftsInContents true / false
    * @param flds Definición de campos
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isValidFTS(boolean ftsInContents, ArchiveFldsImpl flds) throws Exception
   {
      boolean valid = true;
      
      if (flds == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      if (ftsInContents || flds.getFtsFldsCount() > 0)
      {
         if (!isFtsConfig())
            valid = false; 
      }
      
      return (valid);
      
   }
   
   /**
    * Valida la definición de campos tipo texto
    * (texto corto longitud entre 1-800)
    * (texto largo longitud > 254) 
    * @param flds Definición de campos
    * @return  true / falsse
    * @throws Exception
    */
   private boolean isValidTxtFldsDef(ArchiveFldsImpl flds) throws Exception
   {
      boolean valid = true;
      
      if (flds == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      for (int i = 0; i <flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)flds.get(i);
         if (fld.getType() == ArchiveFldType.SHORT_TEXT)
         {
            if (fld.getLen() <=0 || fld.getLen() > 8000)
            {
               valid = false;
               break;
            }
            
         }
            
         if (fld.getType() == ArchiveFldType.LONG_TEXT)
         {
            if (fld.getLen() <= 254)
            {
               valid = false;
               break;
            }
         }
         
         
      }
      
      return valid;
      
   }
   
   /**
    * Obtiene si es válida la definición de campos documentales ( solo pueden se
    * documentales campos de tipo texto).
    * @param flds Definición de campos.
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isValidFtsFldsDef(ArchiveFldsImpl flds) throws Exception
   {
      boolean valid = true;
      
      if (flds == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      for (int i = 0; i <flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)flds.get(i);
         if (fld.isDoc())
         {
            if (fld.getType() != ArchiveFldType.SHORT_TEXT &&
                fld.getType() != ArchiveFldType.LONG_TEXT)
            {
               valid = false;
               break;
            }
         }
         
      }
      
      return valid;
   }
   
   /**
    * Obtiene si es válida la definición de índices ( No puede formar parte de
    * los índices campos multivalores ni campos extendidos).
    * @param flds Definición de campos.
    * @param idxs Definición de índices
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isValidIdxsDef(ArchiveFldsImpl flds, ArchiveIdxsImpl idxs) throws Exception
   {
      boolean valid = true;
      //no se controla que dos índices tengan la misma definición de campos.
      if (flds == null || idxs == null)
         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_PARAM_NO_VALID);
      
      for (int i = 0; i < idxs.count(); i++)
      {
         ArrayList     fldsId;
         ArchiveIdxImpl idx = (ArchiveIdxImpl)idxs.get(i);
         
         fldsId = idx.getFldsId();
         for (int j = 0; j < fldsId.size(); j++)
         {
            ArchiveFldImpl fldDef;
            Integer       Id = (Integer)fldsId.get(j);
            
            fldDef = (ArchiveFldImpl)flds.getFldDefById(Id.intValue());
            
            if (fldDef.isExt() || fldDef.isMult())
            {
               valid = false;
               break;
            }           
            
         }
      }      
      
      return valid;
   }
   
   /**
    * Obtiene si existen carpetas en el archivador.
    * @return true / false
    * @throws Exception Errores
    */
   private boolean existsFdrs() throws Exception
   {
      ArchivesTable table = new ArchivesTable();
      boolean       exists = false;
      String        tblName;
      int           count;
      
      tblName = DaoUtil.getFdrHdrTblName(_tblPrefix);
      
      count = DbSelectFns.selectCount(tblName, null);
      if (count > 0)
         exists = true;
      
      return exists;
   }
   
   /**
    * Actualiza la definición del archivador.
    * 
    * @throws Exception Errores
    */
   private void updateDetails() throws Exception
   {
      updateFldsDetail();
      updateMiscDetail();
      updateFtsDetail();
   }
   
   /**
    * Actualiza la totalidad de la definición del archivador.
    * 
    * @throws Exception Errores
    */
   private void updateArchDef() throws Exception
   {  
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchDef");
      
      updateArchHdr();
      checkArchiveExists();  
      updateDetails();      
      //habría que mirar si se ha modificado la lista de volúmenes
      if (_updInfo.isModifyListVols())
         updateListVols();     
      
   }
   /**
    * Elimina tablas de archivador creadas(multivalor, relacional)
    * crea nuevas tablas (relacional,multivalor)
    * @throws Exception - errores
    */
   private void updateArchTbls() throws Exception
   {
      String tableName;
      ArchiveFldsImpl flds = new ArchiveFldsImpl();
      ArchiveIdxsImpl idxs = new ArchiveIdxsImpl();
      boolean         crtMTxtTbl = false;
      boolean         crtMDateTbl = false;
      boolean         crtMDecTbl = false;
      boolean         crtMIntTbl = false;
      
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchTbls");
      
      getUpdFldsIdxsDef(flds,idxs);
      //se eliminan tablas
      dropTxtMultTbl();
      dropIntMultTbl();
      dropDecMultTbl();
      dropDateMultTbl();     
      tableName = DaoUtil.getRelFldsTblName(_tblPrefix);
      DbTableFns.dropTable(tableName);
      
      //se crean tablas de nuevo
      //    Crear tabla relacional      
      createRelFldsTable(tableName, flds, idxs);
      //se crean las tablas multivalores que sean necesarias
      for (int i = 0; i < flds.count() ; i++)
      {         
         ArchiveFldImpl fldDef = (ArchiveFldImpl)flds.get(i);
                           
         if (fldDef.isMult())
         {
            switch(fldDef.getType())
            {
         		case ArchiveFldType.SHORT_TEXT:    crtMTxtTbl  = true; break;
	         	case ArchiveFldType.DATE:
	         	case ArchiveFldType.DATE_TIME:
	         	case ArchiveFldType.TIME:          crtMDateTbl = true; break;
	         	case ArchiveFldType.SHORT_DECIMAL:
	         	case ArchiveFldType.LONG_DECIMAL:  crtMDecTbl  = true; break;
	         	case ArchiveFldType.SHORT_INTEGER:
	         	case ArchiveFldType.LONG_INTEGER:  crtMIntTbl  = true; break;
            }
         }
      }
            
      if (crtMTxtTbl)
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_TEXT);
         createMultTbl(tableName,DbDataType.SHORT_TEXT);
      }
      if (crtMDateTbl)
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.DATE_TIME);
         createMultTbl(tableName,DbDataType.DATE_TIME);
      }
      if (crtMDecTbl)
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_DECIMAL);
         createMultTbl(tableName,DbDataType.SHORT_DECIMAL);
      }
      if (crtMIntTbl)
      {
         tableName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_INTEGER);
         createMultTbl(tableName,DbDataType.SHORT_INTEGER);
      }
                  
   }
   
   /**
    * Obtiene si la tabla multivalor para los campos de tipo texto está creada.
    * 
    * @return true / false
    */
   private boolean isTxtTblMultCreated()
   {
      boolean isMTxt = false;
      
      if (_flds.getMultFldsCount() > 0)
      {
         for (int i = 0; i < _flds.count(); i++)
         {
            ArchiveFldImpl fld = (ArchiveFldImpl)_flds.get(i);
            if (fld.isMult())
            {
               int type = fld.getType();
               
               if (type == ArchiveFldType.SHORT_TEXT)    
                  isMTxt  = true;               
            }
         }
         
      }
      
      return isMTxt;
   }
   
   /**
    * Obtiene si la tabla multivalor para los campos de tipo entero está creada.
    * 
    * @return true / false
    */
   private boolean isIntTblMultCreated()
   {
      boolean isMInt = false;
      
      if (_flds.getMultFldsCount() > 0)
      {
         for (int i = 0; i < _flds.count(); i++)
         {
            ArchiveFldImpl fld = (ArchiveFldImpl)_flds.get(i);
            if (fld.isMult())
            {
               int type = fld.getType();
               
               if (type == ArchiveFldType.SHORT_INTEGER || type == ArchiveFldType.LONG_INTEGER)
                  isMInt  = true;
               
            }
         }
         
      }
      
      return isMInt;
   }
   
   /**
    * Obtiene si la tabla multivalor para los campos de tipo decimal está creada.
    * 
    * @return true / false
    */
   private boolean isDecTblMultCreated()
   {
      boolean isMDec = false;
      
      if (_flds.getMultFldsCount() > 0)
      {
         for (int i = 0; i < _flds.count(); i++)
         {
            ArchiveFldImpl fld = (ArchiveFldImpl)_flds.get(i);
            if (fld.isMult())
            {
               int type = fld.getType();
               
               if (type == ArchiveFldType.SHORT_DECIMAL || type == ArchiveFldType.LONG_DECIMAL)
                  isMDec  = true;
               
            }
         }
      }
      
      return isMDec;
   }
   
   /**
    * Obtiene si la tabla multivalor para los campos de tipo fecha-hora está creada.
    * 
    * @return true / false
    */
   private boolean isDateTblMultCreated()
   {
      boolean isMDate = false;
      
      if (_flds.getMultFldsCount() > 0)
      {
         for (int i = 0; i < _flds.count(); i++)
         {
            ArchiveFldImpl fld = (ArchiveFldImpl)_flds.get(i);
            if (fld.isMult())
            {
               int type = fld.getType();
               if (type == ArchiveFldType.DATE || type == ArchiveFldType.DATE_TIME ||
                   type ==  ArchiveFldType.TIME)
               {
                  isMDate = true; 
               }
            }
         }
         
      }
      return isMDate;
   }
   
   /**
    * Actualiza la definición de las tablas del archivador.
    * 
    * @throws Exception Errores en la actualización.
    */
   private void alterTableAddArchFlds() throws Exception
   {
      ArrayList       newFlds = _updInfo.getNewFlds();
      ArchiveFldsImpl fldsDef = (ArchiveFldsImpl)_updInfo.getFldsDef();
      String          tblName = _tblPrefix + "SF";
      boolean         crtMTxtTbl = false;
      boolean         crtMIntTbl = false;
      boolean         crtMDecTbl = false;
      boolean         crtMDateTbl = false;
      boolean         isMTxt = false,isMInt = false;
      boolean         isMDec = false,isMDate = false;
            
      if (_logger.isDebugEnabled())
         _logger.debug("alterTableAddArchFlds");
      
      isMTxt = isTxtTblMultCreated();
      isMInt = isIntTblMultCreated();
      isMDec = isDecTblMultCreated();
      isMDate = isDateTblMultCreated();
      
      
      for (int i = 0; i < newFlds.size(); i++)
      {
         Integer fldId = (Integer)newFlds.get(i);
         ArchiveFldImpl fldDef = (ArchiveFldImpl)fldsDef.getFldDefById(fldId.intValue());
         
         if (fldDef.isRel())
         {
            DbColumnDef colsDef[] = new DbColumnDef[1];
            DbColumnDef colDef = new DbColumnDef(fldDef.getColName(),fldDef.getType(),
                                                 fldDef.getLen(),fldDef.isNullable());
            colsDef[0] = colDef;
            DbTableFns.altertable(tblName,colsDef);
         }
         
         if (fldDef.isMult())
         {
            switch(fldDef.getType())
            {
         		case ArchiveFldType.SHORT_TEXT:    crtMTxtTbl  = true; break;
	         	case ArchiveFldType.DATE:
	         	case ArchiveFldType.DATE_TIME:
	         	case ArchiveFldType.TIME:          crtMDateTbl = true; break;
	         	case ArchiveFldType.SHORT_DECIMAL:
	         	case ArchiveFldType.LONG_DECIMAL:  crtMDecTbl  = true; break;
	         	case ArchiveFldType.SHORT_INTEGER:
	         	case ArchiveFldType.LONG_INTEGER:  crtMIntTbl  = true; break;
            }             
         }                  
      }
      
      if (crtMTxtTbl && !isMTxt)
      {
         tblName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_TEXT);
         createMultTbl(tblName,DbDataType.SHORT_TEXT);
      }
      if (crtMDateTbl && !isMDate)
      {
         tblName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.DATE_TIME);
         createMultTbl(tblName,DbDataType.DATE_TIME);
      }
      if (crtMDecTbl && !isMDec)
      {
         tblName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_DECIMAL);
         createMultTbl(tblName,DbDataType.SHORT_DECIMAL);
      }
      if (crtMIntTbl && !isMInt)
      {
         tblName = DaoUtil.getMultFldTblName(_tblPrefix, DbDataType.SHORT_INTEGER);
         createMultTbl(tblName,DbDataType.SHORT_INTEGER);
      }
   }
   
   /**
    * Actualiza los índices de la tabla AxSF del archivador.
    * 
    * @throws Exception Errores 
    */
   private void updateArchIdxs() throws Exception
   {
      ArchiveIdxsImpl idxs = (ArchiveIdxsImpl)_updInfo.getIdxsDef();
      DbIndexDef[]    idxsDef = null;
      DbIndexDef      idxDef = null;
      ArrayList       newIdxs;
      boolean         find = false;
      String          tableName = _tblPrefix + "SF";
      String          indexName;
      ArchiveIdxsImpl nidxs = new ArchiveIdxsImpl();
      ArchiveFldsImpl nflds = new ArchiveFldsImpl();
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchIdxs");
           
      newIdxs = _updInfo.getNewIdx();    
    
     //se eliminan todos los índices
      for(int i = 0; i < idxs.count(); i++)
      {
         ArchiveIdxImpl idx = (ArchiveIdxImpl)idxs.get(i);
         find = false;
         for (int j = 0; j < newIdxs.size(); j++)
         {
            if ( newIdxs.get(j).equals(new Integer(idx.getId())) )
            {
               find = true;
               break;
            }
         }
         if (!find)
         {
            indexName = tableName + new Integer(i+1).toString();
            DbTableFns.dropIndex(tableName,indexName);
         }
      }         
          
      //se obtiene definición de índices teniendo en cuenta tanto campos como
      //indices eliminados y creados
      getUpdFldsIdxsDef(nflds, nidxs);
      
      //se crea la estructura de índices a crear
      
      idxsDef = new DbIndexDef[nidxs.count()];
      for (int i = 0; i < nidxs.count(); i++)
      {
         String         nameIdx, colNamesIdx;
         ArrayList      colIdsIdx;
         ArchiveIdxImpl idx = (ArchiveIdxImpl) nidxs.get(i);
         
         nameIdx = tableName + Integer.toString(i+1);
         colIdsIdx = idx.getFldsId();
         colNamesIdx = "";
         for (int j = 0; j < colIdsIdx.size(); j++)
         {
            Integer FldId = (Integer)colIdsIdx.get(j);
            if (colNamesIdx.length() == 0)
               colNamesIdx = "FLD" + FldId.toString();
            else
               colNamesIdx = colNamesIdx + ",FLD" + FldId.toString();
         }
         idxDef = new DbIndexDef(nameIdx,colNamesIdx,idx.isUnique(), false);
         
         idxsDef[i] = idxDef;      
      }
      
      DbTableFns.createIndices(tableName,idxsDef);
      
   }
   
   /**
    * Elimina información de formatos, reports u componentes en uso.
    * 
    * @throws Exception Errores
    */
   private void deleteInfoExt() throws Exception
   {
      ArchivesTable table = new ArchivesTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("deleteInfoExt");
      
	   //tabla de formatos
	   DbDeleteFns.delete(table.getFmtsTableName(), 
	         table.getLoadFmtsArchIdQual(_id));
		//tabla de reports
		DbDeleteFns.delete(table.getRptsTableName(), 
					 table.getLoadRptsArchIdQual(_id));
		
		//tabla de componentes en uso
		DbDeleteFns.delete(table.getCompsUsgTableName(), 
					 table.getLoadCompsUsgArchIdQual(_id));
		
		
   }

   /**
    * Actualiza la definición del archivador cuando no tiene carpetas.
    * 
    * @throws Exception Errores
    */
   private void updateArchWithoutFdrs() throws Exception
   {
      boolean        commit = false;
      boolean        inTrans = false;      
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchWithoutFdrs");
      
      try
      {
         //valida los datos del archivador
         validateArchive(true);
         DbConnection.beginTransaction();
         inTrans = true;  
         // se actualiza información general HDR,DETAIL,LISTVOL
         updateArchDef();
         
//       actualiza propietario del archivador
         updateObjInfo();
         
         //se eliminan los formatos,report y componentes en uso
         deleteInfoExt();         

         // se actualiza la información de tablas
         updateArchTbls();
         
         commit = true;
         
         if (inTrans) 
         {            
            DbConnection.endTransaction(commit);
            inTrans = false;
         }
         
//       Tiene que estar fuera de la transacción         
         updateArchDoc(true);
      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }
      finally
		{
			if (inTrans) 
	            DbConnection.endTransaction(commit);			
		}
   }
   
   /**
    * Actualiza la información del archivador cuando tiene carpertas.
    * 
    * @throws Exception Errores
    */
   private void updateArchWithFdrs() throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
     //solo se han añadido campos 
      if (_logger.isDebugEnabled())
         _logger.debug("updateArchWithFdrs");
      
      try
      {
         DbConnection.beginTransaction();
         inTrans = true;
         
         //se actualiza información general HDR,DETAIL,LISTVOL
         updateArchDef();
         //actualiza propietario del archivador
         updateObjInfo();
         //se añaden los campos ALTER TABLE Ó CREAR TABLAS MULTIVALOR
         alterTableAddArchFlds();
         //se actualizan los índices
         updateArchIdxs();
         
         commit = true;
         
         if (inTrans) 
         {
            DbConnection.endTransaction(commit);
            inTrans = false;
         }
         
//       Tiene que estar fuera de la transacción         
         updateArchDoc(true);
         
         
      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }
      finally
		{
			if (inTrans) 
	            DbConnection.endTransaction(commit);			
		}
            
      
   }
   
   /**
    * Crea un archivador.
    * 
    * @throws Exception Errores en la creación.
    */
	private void createArchive() throws Exception
	{
		boolean commit = false;
	   boolean inTrans = false;
	   ArrayList allTbls;
	      
	   allTbls = new ArrayList();
	   
		if (_logger.isDebugEnabled())
			_logger.debug("create");
		try
		{
		   //obtener identificador del archivador IDOCNEXTID		   
			_id = NextId.generateNextId(ArchivesTable.TN_NEXTID, 
                    Defs.NEXT_ID_TYPE_ARCHIVE);
			//	obtener identificador del objeto archivador IUSEROBJHDR  
         _acsId = NextId.generateNextId(LdapUsersTable.TN_NEXTID, 
                      Defs.NEXT_ID_USER_TABLE_TYPE_OBJ);
		
         //formatear _tblPrefix
         _tblPrefix = "A" + Integer.toString(_id);
            
         //Crear tablas de archivador 10 fijas, 4 posible si exists multivalores         
         createArchTbls(allTbls);
         
         DbConnection.beginTransaction();
         inTrans = true;
         
         //dar de alta el obj archivador poniendo como propietario al usuario conectado
         //en IUSEROBJHDR
		 
         insertObjInfo();
			
         //realizar el insert en la tabla IDOCARCHHDR (Se comprueba que no existe
         //otro hermano con el mismo nombre antes y después)
         insertBase();
         checkArchiveExists();         
         
         // se inserta la relación parentId ArchId en IDOCACTNODE
         insertNode();
         
         // se inserta en la tabla IDOCARCHDET la definición del archivador
			insertDetails();
			
			// se inicializan las tablas necesarias para el funcionamiento del archivador	
		   initArchTbls();
		   
		   //se asocia la lista de volúmenes al archivador		   
		   insertListVols();
		   
         commit = true;
         
         if (inTrans) 
         {
            DbConnection.endTransaction(commit);
            inTrans = false;
         }
         
         //Tiene que estar fuera de la transacción
         
         updateArchDoc(false);
		}
		catch(Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			if (inTrans) 
	            DbConnection.endTransaction(commit);
			if (!commit)
			{
			   dropArchTbls(allTbls);
			}
		}
		
	}
	
	private void updateArchDoc(boolean isUpdate) throws Exception
	{
	   String          value;
      ArchiveFldsImpl flds = null;  
      ArchiveFldsImpl ftsFlds = null;
      ArchiveFldsImpl oldftsFlds = null;
      String          tblName;
      
      if ( DbConnection.getEngine() == DbEngine.ORACLE ||
           DbConnection.getEngine() == DbEngine.SQLSERVER  )
      {
            
         if (isUpdate)//Hay que tener en cuenta los eliminados y los nuevos
         {
            flds =  getUpdFldsDef();
            oldftsFlds = _flds.getFtsFlds();
         }
         else
            flds = _flds;
         
      
         if (oldftsFlds != null && oldftsFlds.count() > 0)
         {
            if ( DbConnection.getEngine() == DbEngine.ORACLE )
               dropOracleDocumentalTbls(oldftsFlds);
            if ( DbConnection.getEngine() == DbEngine.SQLSERVER )
               dropSqlServerDocumentalTbls(oldftsFlds);
         }
      
         ftsFlds = flds.getFtsFlds();
      
         if (ftsFlds.count() > 0)
         {
            if ( DbConnection.getEngine() == DbEngine.ORACLE )         
               createOracleDocumentalTbls(ftsFlds);
            if ( DbConnection.getEngine() == DbEngine.SQLSERVER)
               createSqlServerDocumentalTbls(ftsFlds);
         }
      }
            
	}
	
	private void deleteArchDoc() throws Exception
	{	   
      ArchiveFldsImpl flds = null;  
      ArchiveFldsImpl ftsFlds = null;
      
      if ( DbConnection.getEngine() == DbEngine.ORACLE ||
           DbConnection.getEngine() == DbEngine.SQLSERVER  )
      {
         flds = _flds;               
         
         ftsFlds = flds.getFtsFlds();      
         if (ftsFlds.count() > 0)
         {
            if (DbConnection.getEngine() == DbEngine.ORACLE)
               dropOracleDocumentalTbls(ftsFlds);
            if (DbConnection.getEngine() == DbEngine.SQLSERVER)
               dropSqlServerDocumentalTbls(ftsFlds);
         }
      }
	}
	
	private void dropOracleDocumentalTbls(ArchiveFldsImpl ftsFlds) throws Exception
	{
	   String tblName;
	   String stmt;
	   
	   for (int i = 0; i < ftsFlds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)ftsFlds.get(i);
         
         if (fld.getType() == DbDataType.LONG_TEXT)
         {
            tblName = _tblPrefix + "XF";            
            stmt = "{Call InvesDoc.DropPolicy('" + tblName + "','TEXT','','-/')}";
   	      DbUtil.executeStatement(stmt);
         }
         if (fld.getType() == DbDataType.SHORT_TEXT)
         {
            tblName = _tblPrefix + "SF";
            stmt = "{Call InvesDoc.DropPolicy('" + tblName + "','" + fld.getColName() + "','','-/')}";
   	      DbUtil.executeStatement(stmt);
            
         }   
         
      }
	}
	
	private void dropSqlServerDocumentalTbls(ArchiveFldsImpl ftsFlds) throws Exception
	{
	   String tblName;
	   String stmt;
	   
	   for (int i = 0; i < ftsFlds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)ftsFlds.get(i);
         
         if (fld.getType() == DbDataType.LONG_TEXT)
         {
            tblName = _tblPrefix + "XF";            
            stmt = "{Call sp_fulltext_table('" + tblName + "','drop')}";
   	      DbUtil.executeStatement(stmt);
         }
         if (fld.getType() == DbDataType.SHORT_TEXT)
         {
            tblName = _tblPrefix + "SF";
            stmt = "{Call sp_fulltext_table('" + tblName + "','drop')}";
   	      DbUtil.executeStatement(stmt);
            
         }   
         
      }
	}
	
	private void createOracleDocumentalTbls(ArchiveFldsImpl ftsFlds) throws Exception
	{
	   String tblName;
	   String stmt;
	   
	   for (int i = 0; i < ftsFlds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)ftsFlds.get(i);
         
         if (fld.getType() == DbDataType.LONG_TEXT)
         {
            tblName = _tblPrefix + "XF";
            stmt = "{Call InvesDoc.CreateKey('" + tblName + "','FDRID,FLDID')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call InvesDoc.CreatePolicy('" + tblName + "','TEXT','FDRID,FLDID','','-/',0)}";
   	      DbUtil.executeStatement(stmt);
         }
         if (fld.getType() == DbDataType.SHORT_TEXT)
         {
            tblName = _tblPrefix + "SF";
            stmt = "{Call InvesDoc.CreateKey('" + tblName + "','FDRID')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call InvesDoc.CreatePolicy('" + tblName + "','" + fld.getColName() + "','FDRID','','-/',0)}";
   	      DbUtil.executeStatement(stmt);
            
         }   
         
      }
	   
	}
	
	private void createSqlServerDocumentalTbls(ArchiveFldsImpl ftsFlds) throws Exception
	{
	   String tblName;
	   String stmt;
	   
	   for (int i = 0; i < ftsFlds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)ftsFlds.get(i);
         
         if (fld.getType() == DbDataType.LONG_TEXT)
         {
            tblName = _tblPrefix + "XF";
            stmt = "{Call sp_fulltext_table('" + tblName + "','create','IDOCFTSCTLG','" + tblName + "1')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sq_fulltext_column('" + tblName + "','TEXT','add','3082')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sq_fulltext_table('" + tblName + "','start_change_tracking')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sq_fulltext_table('" + tblName + "','start_background_updateindex')}";
   	      DbUtil.executeStatement(stmt);
         }
         if (fld.getType() == DbDataType.SHORT_TEXT)
         {
            tblName = _tblPrefix + "SF";
            stmt = "{Call sp_fulltext_table('" + tblName + "','create''IDOCFTSCTLG','" + tblName + "0')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sp_fulltext_column('" + tblName + "','" + fld.getColName() + "','add','3082')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sq_fulltext_table('" + tblName + "','start_change_tracking')}";
   	      DbUtil.executeStatement(stmt);
   	      stmt = "{Call sq_fulltext_table('" + tblName + "','start_background_updateindex')}";
   	      DbUtil.executeStatement(stmt);
         }   
         
      }
	   
	}
	
	/**
	 * Inicializa los datos de esta clase.
	 * 
	 * @param userConnected Identificador de usuario conectado.
	 * @param parentId Identificador del padre del archivador.	  
	 * @param isLdap Indica si estamos en un sistema de usarios LDAP o no.
	 */
	private void init(int userConnected, int parentId, boolean isLdap)
	   {
	      _id = Defs.NULL_ID;
	      _parentId = parentId;
	      _isLdap = isLdap;
	      _userConnected = userConnected;
	      _name = "";
	      _tblPrefix = "";
	      _remarks = "";
	      _flags = ArchiveDefs.ARCH_FLAG_NONE;
	      _type = ArchiveDefs.ARCH_TYPE_STANDARD;
	      _accessType = ArchiveDefs.ACCESS_TYPE_PROTECTED;
	      _updaterId = DbDataType.NULL_LONG_INTEGER;
	      _creatorId = _userConnected;
	      _updateDate = DbDataType.NULL_DATE_TIME;
	      _acsId = Defs.NULL_ID;
	      _adminUserId = _userConnected;
	      _adminUsers = new BasicUsersImpl();
	      _flds = new ArchiveFldsImpl();
	      _idxs = new ArchiveIdxsImpl();
	      _misc = new ArchiveMiscImpl("",ArchiveVolListType.NONE,Defs.NULL_ID);
	   }
	   
	   
	   private int _id;
	   private int _userConnected;
	   private int _parentId;
	   private boolean _isLdap;
	   private String _name;
	   private String _tblPrefix;
	   private String _remarks;
	   private int _flags;
	   private int _acsId;
	   private int _type;
	   private int _accessType;
	   private Date _creationDate;
	   private int _creatorId;
	   private int _updaterId;
	   private Date _updateDate;
	   private ArchiveFldsImpl _flds;
	   private ArchiveIdxsImpl _idxs;
	   private ArchiveMiscImpl _misc;
	   private String         _fldsIdxsDetail;
	   private String         _miscDetail;
	   private String         _ftsDetail;
	   private int            _adminUserId;
	   private ArchiveUpdInfoImpl _updInfo;
	   private BasicUsersImpl _adminUsers;
	   
	   private static final Logger _logger = Logger.getLogger(ArchiveImpl.class);	

}
