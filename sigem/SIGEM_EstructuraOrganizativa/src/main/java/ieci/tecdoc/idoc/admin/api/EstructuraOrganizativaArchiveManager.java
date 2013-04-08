package ieci.tecdoc.idoc.admin.api;


import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.database.DBSessionManager;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.sbo.config.CfgDefs;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public class EstructuraOrganizativaArchiveManager {

	public EstructuraOrganizativaArchiveManager() {
	}

	/**
	 * Crea un archivador en el repositorio documental
	 * @param userId Identificador de usuario del repositorio documental
	 * @param parentId Identificador del nodo padre
	 * @param fldsDef Definición de las carpetas del archivador
	 * @param idxsDef Definición de los índices del archivador
	 * @param typeId Identificador del tipo de archivador
	 * @param entityId Identificador de la entidad
	 * 
	 * @return Identificador del archivador creado
	 * 
	 * @throws Exception Si se produce algún error
	 */
	public int createArchive(int userId, int  parentId, String name, String description, ArchiveFlds fldsDef, ArchiveIdxs idxsDef, int typeId, String entityId) throws IeciTdException { 
		Archive arch = null;
		int archiveId = 0;
		boolean isLdap = false;
		DbConnection dbConn = new DbConnection();
		try{
			ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
			boolean userCanCreateArch = archiveAccess.userCanCreateArch(userId, parentId, entityId);
			if (!userCanCreateArch){
				AdminException.throwException(ArchiveErrorCodes.EC_ARCH_CANNT_PERM);
			}else{
				dbConn.open(DBSessionManager.getSession(entityId));
				CfgLdapConfig ldapCfg = null;
				CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();			      
				ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);
				if (ldapCfg.getEngine() != CfgDefs.LDAP_ENGINE_NONE)
					isLdap = true;
				arch = ObjFactory.createArchive(userId,parentId,isLdap);
				arch.setName(name);
				arch.setType(typeId);
				arch.setRemarks(description);
				arch.setFldsDef(fldsDef);
				arch.setIdxsDef(idxsDef);

				arch.create(entityId);
				archiveId = arch.getId();
			}
		}catch(Exception e){
			throw new IeciTdException(String.valueOf(ArchiveErrorCodes.EC_CREATE_ARCHIVE),e.getMessage(), e);
		}finally{
			try{
				if(dbConn.existConnection()){
					dbConn.close();
				}
			}catch(Exception e){

			}
		}
		return archiveId;
	}

	/**
	 * Elimina un archivador en el repositorio documental
	 * @param userId Identificador de usuario del repositorio documental
	 * @param archId Identificador del archivador
	 * @param entityId Identificador de la entidad
	 * 
	 * @throws Exception Si se produce algún error
	 */
	public void deleteArchive(int userId, int  archId, String entityId) throws Exception { 
		int archiveId = 0;
		boolean isLdap = false;
		DbConnection dbConn = new DbConnection();
		try{
			ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
			boolean userCanCreateArch = archiveAccess.userCanDeleteArch(userId, archId, entityId);
			if (!userCanCreateArch){
				AdminException.throwException(ArchiveErrorCodes.EC_ARCH_CANNT_PERM);
			}else{
				dbConn.open(DBSessionManager.getSession(entityId));
				CfgLdapConfig ldapCfg = null;
				CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();			      
				ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);
				if (ldapCfg.getEngine() != CfgDefs.LDAP_ENGINE_NONE)
					isLdap = true;
				Archive archive = ObjFactory.createArchive(userId, Defs.NULL_ID, isLdap);
				archive.delete(archId, entityId);
			}
		}catch(IeciTdException e){
			throw new IeciTdException(String.valueOf(ArchiveErrorCodes.EC_CREATE_ARCHIVE),e.getMessage(), e);
		}finally{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}
	}
	
	



}
