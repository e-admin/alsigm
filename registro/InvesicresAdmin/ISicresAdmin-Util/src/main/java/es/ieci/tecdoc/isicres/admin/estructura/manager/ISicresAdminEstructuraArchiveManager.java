package es.ieci.tecdoc.isicres.admin.estructura.manager;


import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveAccess;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveIdxs;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminArchiveKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgDefs;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;

public class ISicresAdminEstructuraArchiveManager {

	public ISicresAdminEstructuraArchiveManager() {
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
			ArchiveAccess archiveAccess = ISicresAdminObjectFactory.createArchiveAccess();
			boolean userCanCreateArch = archiveAccess.userCanCreateArch(userId, parentId, entityId);
			if (!userCanCreateArch){
				ISicresAdminBasicException.throwException(ISicresAdminArchiveKeys.EC_ARCH_CANNT_PERM);
			}else{
				dbConn.open(DBSessionManager.getSession());
				CfgLdapConfig ldapCfg = null;
				CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();
				ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);
				if (ldapCfg.getEngine() != CfgDefs.LDAP_ENGINE_NONE)
					isLdap = true;
				arch = ISicresAdminObjectFactory.createArchive(userId,parentId,isLdap);
				arch.setName(name);
				arch.setType(typeId);
				arch.setRemarks(description);
				arch.setFldsDef(fldsDef);
				arch.setIdxsDef(idxsDef);

				arch.create(entityId);
				archiveId = arch.getId();
			}
		}catch(Exception e){
			throw new IeciTdException(String.valueOf(ISicresAdminArchiveKeys.EC_CREATE_ARCHIVE),e.getMessage(), e);
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
			ArchiveAccess archiveAccess = ISicresAdminObjectFactory.createArchiveAccess();
			boolean userCanCreateArch = archiveAccess.userCanDeleteArch(userId, archId, entityId);
			if (!userCanCreateArch){
				ISicresAdminBasicException.throwException(ISicresAdminArchiveKeys.EC_ARCH_CANNT_PERM);
			}else{
				dbConn.open(DBSessionManager.getSession());
				CfgLdapConfig ldapCfg = null;
				CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();
				ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);
				if (ldapCfg.getEngine() != CfgDefs.LDAP_ENGINE_NONE)
					isLdap = true;
				Archive archive = ISicresAdminObjectFactory.createArchive(userId, ISicresAdminDefsKeys.NULL_ID, isLdap);
				archive.delete(archId, entityId);
			}
		}catch(IeciTdException e){
			throw new IeciTdException(String.valueOf(ISicresAdminArchiveKeys.EC_CREATE_ARCHIVE),e.getMessage(), e);
		}finally{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}
	}





}
