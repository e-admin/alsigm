
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;



import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.database.ArchivesTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.SystemCfg;
import es.ieci.tecdoc.isicres.admin.estructura.dao.User;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoGenPermTbl;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoObjHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoObjPermTbl;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoUserTypeTbl;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgDaoDbInfoTbl;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgDbInfoMisc;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgFtsConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoActDirTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoFileTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoFtsTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoListTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoListVolTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoRepTbl;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssDaoVolTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoAppEventTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchDetTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchListTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoAutoNumCtlgTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoCompUsgTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDATNodeTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDbCtlgTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDefFmtTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDirTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFdrLinkTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFdrStatTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFiauxTblCtlg;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFmtTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoGlbDocHTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoMacroTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoPageAnnsTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoPictTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoPrefWFmtTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoRptAuxTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoRptTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoSSNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoSrvStateDetTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoSrvStateHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoWMacroTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoXNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.std.VldDaoBTblCtlgTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.std.VldDaoVTblCtlgTbl;
import es.ieci.tecdoc.isicres.admin.sbo.sms.core.SmsDaoSessNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.sms.core.SmsDaoSessTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasDaoLdapGroupTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasDaoLdapUserTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoCurrCntTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoDeptTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoGURelTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoGroupTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoLicencesTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoRemUserTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoSysTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoUserMapTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoUserTbl;
/**
 * Representa Información del sistema
 */
public class SystemCfgImpl implements SystemCfg
{

   private String drvClsName;
   private String url;
   private String user;
   private String pwd;
   private boolean isDefaultBDConnection = true;

   private CfgDbInfoMisc infoMisc;

   public SystemCfgImpl(String drvClsName,String url, String user, String pwd)
   {
       this.drvClsName = drvClsName;
       this.url = url;
       this.user = user;
       this.pwd = pwd ;
       isDefaultBDConnection = false;

   }

   public SystemCfgImpl(){

   }

   /**
    * {@inheritDoc}
    * @see es.ieci.tecdoc.isicres.admin.estructura.dao.SystemCfg#ldapConnectionTest(java.lang.String)
    */
   public void ldapConnectionTest(CfgLdapConfig ldapConfig) throws Exception
   {
	   LdapConnection ldapConn = new LdapConnection();
	   LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(ldapConfig);
	   try {
		   ldapConn.open(connCfg);
	   } catch (Exception e) {
		   LdapConnection.ensureClose(ldapConn, e);
		   throw e;
	   }
   }

   public CfgLdapConfig getLdapConfig(String entidad)throws Exception
   {
       CfgLdapConfig ldapConfig = null;
       if (this.infoMisc == null ) // Aún no está inicializada
       {
    	   DbConnection dbConn=new DbConnection();
    	   try{
    	   	   dbConn.open(DBSessionManager.getSession());
               infoMisc = CfgMdoConfig.loadDbInfoMisc(dbConn);

               ldapConfig = infoMisc.getLdapConfig();
    	   }catch(Exception e){
    			throw e;
    		}finally{
    			dbConn.close();
    		}
       }
       else
           ldapConfig = infoMisc.getLdapConfig();
       return ldapConfig;

   }

   public void updateMiscLdapConfig(String entidad, CfgLdapConfig ldapConfig) throws Exception {
	   DbConnection dbConn = new DbConnection();

	   if (_logger.isDebugEnabled()) {
           _logger.debug("updateMiscLdapConfig - Inicio");
	   }

	   try {
		   if (this.infoMisc == null) {
	   	   	   dbConn.open(DBSessionManager.getSession());
	           infoMisc = CfgMdoConfig.loadDbInfoMisc(dbConn);
	       }

	       this.infoMisc.setLdapConfig(ldapConfig);
	       CfgMdoConfig.storeDbInfoMisc(infoMisc, dbConn);

	   } catch(Exception e) {
		   throw e;
	   } finally {
			dbConn.close();
	   }

	   if (_logger.isDebugEnabled()) {
           _logger.debug("updateMiscLdapConfig - Fin");
	   }
   }

   public void updateMisc(String entidad) throws Exception{
       if (infoMisc != null ){ // Se supone que se ha cargado la información antes.. pero bueno x si acaso.

           if (_logger.isDebugEnabled())
               _logger.debug("update");

           DbConnection dbConn=new DbConnection();
           try{
           	   dbConn.open(DBSessionManager.getSession());
               // infoMisc = CfgMdoConfig.loadDbInfoMisc();
               //cfgFtsConfig = infoMisc.getFtsConfig();
               CfgMdoConfig.storeDbInfoMisc(infoMisc, dbConn);
           }catch(Exception e){
        		throw e;
        	}finally{
        		dbConn.close();
        	}


       }
   }
   public CfgFtsConfig getFtsConfig(String entidad) throws Exception
   {
       CfgFtsConfig cfgFtsConfig = null;
       if (this.infoMisc == null ) // Aún no está inicializada
       {
    	   DbConnection dbConn=new DbConnection();
    	   try{
    	   	   dbConn.open(DBSessionManager.getSession());
               infoMisc = CfgMdoConfig.loadDbInfoMisc(dbConn);
               cfgFtsConfig = infoMisc.getFtsConfig();
    	   }catch(Exception e){
    			throw e;
    		}finally{
    			dbConn.close();
    		}
       }
       else
           cfgFtsConfig = infoMisc.getFtsConfig();

       return cfgFtsConfig;
   }

   /**
    * Abre la conexión a la base de datos, usando los siguientes parámetros
    * @param drvClsName Driver jdbc
    * @param url Cadena de conexión
    * @param user Nombre de usuario
    * @param pwd Contraseña
    * @throws Exception
    */
   /*
   public void connectDataBase(String entidad)throws Exception{
        try
        {
            if (isDefaultBDConnection)
            	DbConnection.open(DBSessionManager.getSession());
            else
            	DbConnection.open(DBSessionManager.getSession());
        }
        catch(Exception e)
        {
           _logger.error(e);
           throw e;
        }
   }*/
   /**
    * Cierra la conexión a la Base de datos
    * @throws Exception Si se produce algún error
    */
   /*
   public void closeDataBase()throws Exception{
       if (_logger.isDebugEnabled()){
           _logger.debug("close DataBase");
       }
       try{
           DbConnection.close();
       }
       catch (Exception e){
           _logger.error(e);
           throw e;
       }

   }*/

   /**
    * Determina si el SYSSUPERUSER está bloqueado o no
    * @return true si SYSSUPERUSER está bloqueado
    */
   public boolean isLockSyssuperuser()
   {
       boolean isLockSyssuperuser = false;
       try {
           String qual = "WHERE " + UasDaoUserTbl.CD_ID.getName() + " =0";
           int stat = DbSelectFns.selectLongInteger(null, "IUSERUSERHDR","STAT",qual);
           if (stat > 0)
               isLockSyssuperuser = true;
       }catch (Exception e){
           _logger.debug(e);
       }finally{
    	   System.out.println();
       }
       return isLockSyssuperuser;
   }

   /**
    *  Inicializa el sistema invesDoc
    *
    * @throws Exception Errores en la inicialización
    */
   public void initDataBase(String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("initDataBase");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession());
          else
        	  dbConn.open(DBSessionManager.getSession());

         setupDocDb(entidad);
         setupUserDb(entidad);
         setupVolDb(entidad);
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
    * Obtiene si existe o no configuración de motor documental
    *
    * @return  true / false
    * @throws Exception
    */
   public boolean hasFtsConfig(String entidad) throws Exception
   {
      boolean      has = false;
      CfgFtsConfig config;

      if (_logger.isDebugEnabled())
         _logger.debug("hasDbFtsConfig");

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession());
          else
        	  dbConn.open(DBSessionManager.getSession());

         config = CfgMdoConfig.loadDbFtsCfg(dbConn);

         //HAY que controlar que el motor documental corresponde con la BD
         if (config.m_engine > 0)
            has = true;
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

      return has;

   }

   /**
    * Obtiene el nombre del sistema
    * @return Dicho nombre
    */
   public String getRootNameArchs(String entidad) throws Exception
   {
      String rootName;

      if (_logger.isDebugEnabled())
         _logger.debug("getRootNameArchs");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession());
          else
        	  dbConn.open(DBSessionManager.getSession());

         rootName = DbSelectFns.selectShortText(dbConn, ArchivesTable.TN_IDOCDBINFO,
                                                ArchivesTable.CN_NAME,"");

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

      return rootName;
   }

   public void setRootNameArchs(String root, String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());
           if (isDefaultBDConnection)
        	   dbConn.open(DBSessionManager.getSession());
           else
        	   dbConn.open(DBSessionManager.getSession());
           String stmtText = "UPDATE " + ArchivesTable.TN_IDOCDBINFO +" SET " +ArchivesTable.CN_NAME +" = '" + root + "'";
           DbUtil.executeStatement(dbConn, stmtText);
          // rootName = DbSelectFns.selectShortText(ArchivesTable.TN_IDOCDBINFO,
                                                 //ArchivesTable.CN_NAME,"");

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
    * Desbloquea el SYSSUPERUSER.
    * Se necesita una Conexión a BD abierta
    * @throws Exception Si se produce algún error
    */
   public void unLockSyssuperuser(String entidad) throws Exception
   {
       /* Modificada para que no abra la bd con los valores de los ficheros de configuración */
      int    colValue;
      String qual;

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         colValue = 0;
         qual = "WHERE " + UasDaoUserTbl.CD_ID.getName() + " =0";

         DbUpdateFns.updateLongInteger(dbConn, UasDaoUserTbl.TN,UasDaoUserTbl.CD_STAT.getName(),colValue,qual);
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }finally{
    	  dbConn.close();
      }
   }

   //***************************************************************************


   /**
    * Inicializa las tablas principales del repositorio documental. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupDocDb(String entidad) throws Exception
   {
      boolean commit = false;
		boolean inTrans = false;

		if (_logger.isDebugEnabled())
			_logger.debug("setupDocDb");

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
	         /// crear tablas del sistema invesDoc
	         createDocTbls(entidad);
	         dbConn.beginTransaction();
	         inTrans = true;
	         // realizar inicialización de tablas
	         initializeDocTbls(entidad);
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
            dbConn.endTransaction(commit);
         dbConn.close();
      }
   }

   /**
    * Inicializa las tablas de usuarios del repositorio documental. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupUserDb(String entidad) throws Exception
   {
      boolean commit = false;
		boolean inTrans = false;

		if (_logger.isDebugEnabled())
			_logger.debug("setupUserDb");

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
	         //crear tablas de usuario
	         createUserTbls(entidad);
	         dbConn.beginTransaction();
	         inTrans = true;
	         // realizar inicialización de tablas
	         initializeUserTbls(entidad);
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
            dbConn.endTransaction(commit);
         dbConn.close();
      }
   }

   /**
    * Inicializa las tablas de volúmenes del repositorio documental. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupVolDb(String entidad) throws Exception
   {
      boolean commit = false;
		boolean inTrans = false;

		if (_logger.isDebugEnabled())
			_logger.debug("setupVolDb");

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
	         // crear tablas de volúmenes
	         createVolTbls(dbConn);
	         dbConn.beginTransaction();
	         inTrans = true;
	         // realizar inicialización de tablas de volúmenes
	         initializeVolTbls(dbConn);
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
            dbConn.endTransaction(commit);
         dbConn.close();
      }
   }


   private void initializeDocTbls(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	  dbConn.open(DBSessionManager.getSession());
	      DaoNextIdTbl.initTblContentsNextId(dbConn);
	      CfgDaoDbInfoTbl.initTblContentsDbInfo(dbConn);
	      DaoSSNextIdTbl.initTblContentsSSNextId(dbConn);
	      SmsDaoSessNextIdTbl.initTblContentsNextId(dbConn);
	   }catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}
   }


   private void createDocTbls(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());
	      DaoNextIdTbl.createTable(dbConn);
	      DaoXNextIdTbl.createTable(dbConn);
	      CfgDaoDbInfoTbl.createTable(dbConn);
	      DaoDbCtlgTbl.createTable(dbConn);
	      DaoDirTbl.createTable(dbConn);
	      DaoDATNodeTbl.createTable(dbConn);
	      DaoArchHdrTbl.createTable(dbConn);
	      DaoArchDetTbl.createTable(dbConn);
	      DaoFmtTbl.createTable(dbConn);
	      DaoDefFmtTbl.createTable(dbConn);
	      DaoPageAnnsTbl.createTable(dbConn);
	      DaoCompUsgTbl.createTable(dbConn);
	      VldDaoBTblCtlgTbl.createTable(dbConn);
	      VldDaoVTblCtlgTbl.createTable(dbConn);
	      DaoMacroTbl.createTable(dbConn);
	      DaoPictTbl.createTable(dbConn);
	      DaoFdrStatTbl.createTable(dbConn);
	      DaoRptTbl.createTable(dbConn);
	      DaoRptAuxTbl.createTable(dbConn);
	      DaoFdrLinkTbl.createTable(dbConn);
	      DaoFiauxTblCtlg.createTable(dbConn);
	      DaoGlbDocHTbl.createTable(dbConn);
	      DaoSSNextIdTbl.createTable(dbConn);
	      DaoSrvStateHdrTbl.createTable(dbConn);
	      DaoSrvStateDetTbl.createTable(dbConn);
	      DaoAppEventTbl.createTable(dbConn);
	      DaoAutoNumCtlgTbl.createTable(dbConn);
	      DaoWMacroTbl.createTable(dbConn);
	      DaoPrefWFmtTbl.createTable(dbConn);
	      SmsDaoSessTbl.createTable(dbConn);
	      SmsDaoSessNextIdTbl.createTable(dbConn);
	   }catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}
   }

   private void initializeUserTbls(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());
	   	User usr = ISicresAdminObjectFactory.createUser();

      UasDaoNextIdTbl.initTblContentsNextId(dbConn);
      UasDaoSysTbl.initContentsUserSysTbl(dbConn);
      usr.initSysSuperUser(entidad);
   }catch(Exception e){
		throw e;
	}finally{
		dbConn.close();
	}
   }

   private void createUserTbls(String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

	   UasDaoNextIdTbl.createTable(dbConn);
      UasDaoUserTbl.createTable(dbConn);
      AcsDaoUserTypeTbl.createTable(dbConn);
      UasDaoDeptTbl.createTable(dbConn);
      UasDaoGroupTbl.createTable(dbConn);
      UasDaoGURelTbl.createTable(dbConn);
      AcsDaoGenPermTbl.createTable(dbConn);
      AcsDaoObjHdrTbl.createTable(dbConn);
      AcsDaoObjPermTbl.createTable(dbConn);
      UasDaoCurrCntTbl.createTable(dbConn);
      UasDaoUserMapTbl.createTable(dbConn);
      UasDaoRemUserTbl.createTable(dbConn);
      UasDaoLdapUserTbl.createTable(dbConn);
      UasDaoLdapGroupTbl.createTable(dbConn);
      UasDaoLicencesTbl.createTable(dbConn);
      UasDaoSysTbl.createTable(dbConn);
   }catch(Exception e){
		throw e;
	}finally{
		dbConn.close();
	}
   }

   private void initializeVolTbls(DbConnection dbConn) throws Exception
   {

	   FssDaoNextIdTbl.initTblContentsNextId(dbConn);
   }

   private void createVolTbls(DbConnection dbConn) throws Exception
   {
	   FssDaoNextIdTbl.createTable(dbConn);
      FssDaoRepTbl.createTable(dbConn);
      FssDaoVolTbl.createTable(dbConn);
      FssDaoActDirTbl.createTable(dbConn);
      FssDaoListTbl.createTable(dbConn);
      FssDaoListVolTbl.createTable(dbConn);
      FssDaoFileTbl.createTable(dbConn);
      FssDaoFtsTbl.createTable(dbConn);
      DaoArchListTbl.createTable(dbConn);

   }

   private static final Logger _logger = Logger.getLogger(SystemCfgImpl.class);
}
