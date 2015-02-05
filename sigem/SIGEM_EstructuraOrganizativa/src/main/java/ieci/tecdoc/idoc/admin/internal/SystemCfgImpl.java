
package ieci.tecdoc.idoc.admin.internal;


import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.system.SystemCfg;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.database.ArchivesTable;
import ieci.tecdoc.idoc.admin.database.DBSessionManager;
import ieci.tecdoc.sbo.acs.std.AcsDaoGenPermTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoObjHdrTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoObjPermTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoUserTypeTbl;
import ieci.tecdoc.sbo.config.CfgDaoDbInfoTbl;
import ieci.tecdoc.sbo.config.CfgDbInfoMisc;
import ieci.tecdoc.sbo.config.CfgFtsConfig;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.fss.core.FssDaoActDirTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoFileTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoFtsTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoListTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoListVolTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoNextIdTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoRepTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoAppEventTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchListTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoAutoNumCtlgTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoCompUsgTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDATNodeTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDbCtlgTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDefFmtTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDirTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrLinkTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrStatTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoFmtTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoGlbDocHTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoMacroTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoNextIdTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPageAnnsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPictTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPrefWFmtTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoRptAuxTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoRptTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoSSNextIdTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoSrvStateDetTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoSrvStateHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoWMacroTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoXNextIdTbl;
import ieci.tecdoc.sbo.idoc.vldtable.std.VldDaoBTblCtlgTbl;
import ieci.tecdoc.sbo.idoc.vldtable.std.VldDaoVTblCtlgTbl;
import ieci.tecdoc.sbo.sms.core.SmsDaoSessNextIdTbl;
import ieci.tecdoc.sbo.sms.core.SmsDaoSessTbl;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;
import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapGroupTbl;
import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapUserTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoCurrCntTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoDeptTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoGURelTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoGroupTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoLicencesTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoNextIdTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoRemUserTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoSysTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoUserMapTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoUserTbl;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUpdateFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

import org.apache.log4j.Logger;
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
   
   public void ldapConnectionTest() throws Exception
   {
       CfgLdapConfig ldapConfig = new CfgLdapConfig ();
       LdapConnection ldapConn = new LdapConnection();
   }

   public CfgLdapConfig getLdapConfig(String entidad)throws Exception
   {
       CfgLdapConfig ldapConfig = null;
       if (this.infoMisc == null ) // Aún no está inicializada
       {
    	   DbConnection dbConn=new DbConnection();
    	   try{
    	   	   dbConn.open(DBSessionManager.getSession(entidad));
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
   
   public void updateMisc(String entidad) throws Exception{
       if (infoMisc != null ){ // Se supone que se ha cargado la información antes.. pero bueno x si acaso.
           
           if (_logger.isDebugEnabled())
               _logger.debug("update");
           
           DbConnection dbConn=new DbConnection();
           try{
           	   dbConn.open(DBSessionManager.getSession(entidad));
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
    	   	   dbConn.open(DBSessionManager.getSession(entidad));
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
            	DbConnection.open(DBSessionManager.getSession(entidad));
            else
            	DbConnection.open(DBSessionManager.getSession(entidad));
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
      	dbConn.open(DBSessionManager.getSession(entidad));
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession(entidad));
          else
        	  dbConn.open(DBSessionManager.getSession(entidad));
         
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
      	  dbConn.open(DBSessionManager.getSession(entidad));     
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession(entidad));
          else
        	  dbConn.open(DBSessionManager.getSession(entidad));      
         
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
      	dbConn.open(DBSessionManager.getSession(entidad));   
          if (isDefaultBDConnection)
        	  dbConn.open(DBSessionManager.getSession(entidad));
          else
        	  dbConn.open(DBSessionManager.getSession(entidad));   
         
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
	   	dbConn.open(DBSessionManager.getSession(entidad));        
           if (isDefaultBDConnection)
        	   dbConn.open(DBSessionManager.getSession(entidad));
           else
        	   dbConn.open(DBSessionManager.getSession(entidad));   
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
      	 dbConn.open(DBSessionManager.getSession(entidad));
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
			dbConn.open(DBSessionManager.getSession(entidad));
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
			dbConn.open(DBSessionManager.getSession(entidad));
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
			dbConn.open(DBSessionManager.getSession(entidad));
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
	   	  dbConn.open(DBSessionManager.getSession(entidad));
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
	   	dbConn.open(DBSessionManager.getSession(entidad));
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
	   	dbConn.open(DBSessionManager.getSession(entidad));
	   	User usr = ObjFactory.createUser();
      
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
	   	dbConn.open(DBSessionManager.getSession(entidad));
	   	
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
