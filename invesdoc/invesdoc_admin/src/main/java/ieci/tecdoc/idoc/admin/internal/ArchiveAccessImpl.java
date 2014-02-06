package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.database.ArchivesTable;
import ieci.tecdoc.idoc.admin.database.DirsTable;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.dao.DaoDATNodeTbl;

import org.apache.log4j.Logger;

/**
 * Implementa el interfaz ArchiveAccess.
 */

public class ArchiveAccessImpl implements ArchiveAccess
{
   public ArchiveAccessImpl()
   {
      
   }
   
   /////////// Funciones para archivadores ///////////////////////////
   
   /**
    * Obtiene si el usuario conectador puede ver el archivador
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewArch (int connectedUserId, int archId) throws Exception
   {
      boolean        can = false;      
      int            acsId;
      int            archMgrId = Defs.NULL_ID;
      ArchivesTable  table = new ArchivesTable();
      LdapUsersTable usrTable = new LdapUsersTable();
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());	
         
         acsId = DbSelectFns.selectLongInteger(table.getArchHdrTableName(), 
            												table.getAcsIdArchHdrColName(),
              												table.getLoadArchIdQual(archId));
         
         archMgrId = DbSelectFns.selectLongInteger(usrTable.getOwnershipTableName(),
               												usrTable.getOwnerIdColumnName(),
               												usrTable.getLoadOwnerIdQual(acsId));
      
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_VIEW, archMgrId, Defs.NULL_ID,Defs.NULL_ID);
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
      
      return can;
   }
   
   
   /**
    * Obtiene si el usuario conectado puede crear un archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio padre
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateArch(int connectedUserId, int dirId) throws Exception
   {
      boolean        can = false;
      int            parentArchMgrId = Defs.NULL_ID;      
      
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (dirId == Defs.NULL_ID || dirId == Defs.ROOT_DIR_ID)
            parentArchMgrId = 0;
         else         
            parentArchMgrId = getDirMgrId(dirId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_CREATE,Defs.NULL_ID,parentArchMgrId, 
               				Defs.NULL_ID);
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede eliminar el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteArch(int connectedUserId, int archId) throws Exception
   {
      boolean        can = false;      
      int            parentId;        
      int            parentArchMgrId = Defs.NULL_ID;
      ArchivesTable  table           = new ArchivesTable();
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());         
         
         
         parentId = DbSelectFns.selectLongInteger(DaoDATNodeTbl.getTblName(),
               											DaoDATNodeTbl.getParentIdColName(true),
               											table.getLoadNodeArchIdQual(archId));
                              
         if (parentId == Defs.ROOT_DIR_ID)
            parentArchMgrId = 0;
         else
            parentArchMgrId = this.getDirMgrId(parentId);         
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_DELETE, Defs.NULL_ID, 
               					parentArchMgrId, Defs.NULL_ID);
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede editar el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archvador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditArch(int connectedUserId, int archId) throws Exception
   {
      boolean        can = false;      
      int            acsId, parentId;        
      int            archMgrId = Defs.NULL_ID;
      ArchivesTable  table     = new ArchivesTable();
      LdapUsersTable usrTable  = new LdapUsersTable();      
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());         
         
         
         acsId = DbSelectFns.selectLongInteger(table.getArchHdrTableName(), 
            												table.getAcsIdArchHdrColName(),
              												table.getLoadArchIdQual(archId));
         
         archMgrId = DbSelectFns.selectLongInteger(usrTable.getOwnershipTableName(),
               												usrTable.getOwnerIdColumnName(),
               												usrTable.getLoadOwnerIdQual(acsId));
      
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_EDIT, archMgrId, 
               				Defs.NULL_ID, Defs.NULL_ID);
         
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede mover el archivador.
    * 
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @param dstDirId Identificador del directorio destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveArch(int connectedUserId, int archId, int dstDirId) throws Exception
   {
      boolean        can = false;      
      int            parentId;        
      int            parentArchMgrId = Defs.NULL_ID;
      int            dstDirMgrId = Defs.NULL_ID;
      ArchivesTable  table       = new ArchivesTable();      
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());         
         
         
         parentId = DbSelectFns.selectLongInteger(DaoDATNodeTbl.getTblName(),
               											DaoDATNodeTbl.getParentIdColName(true),
               											table.getLoadNodeArchIdQual(archId));
         
         if (parentId == Defs.ROOT_DIR_ID)
            parentArchMgrId = 0;
         else
            parentArchMgrId = getDirMgrId(parentId);
         
         if (dstDirId == Defs.NULL_ID || dstDirId == Defs.ROOT_DIR_ID)
            dstDirMgrId = 0;
         else
            dstDirMgrId = getDirMgrId(dstDirId);
         
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_MOVE, Defs.NULL_ID, 
               		   	parentArchMgrId, dstDirMgrId);
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
      
      return can;
   }
   
   public boolean userCanAddPermOnArch(int connectedUserId, int archId) throws Exception
   {
      boolean can = false;
      
      can = userCanEditArch(connectedUserId, archId);
      
      return can;
   }
   
   
   
   /////////////////////////////// Directorios ///////////////////////////////////////////////////
   
   /**
    * Obtiene si el usuario conectador puede ver el directio
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del directorio
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewDir (int connectedUserId, int dirId) throws Exception
   {
      boolean        can = false;
      int            dirMgrId = Defs.NULL_ID;
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (dirId == Defs.NULL_ID || dirId == Defs.ROOT_DIR_ID)
            dirMgrId = 0;
         else
            dirMgrId = this.getDirMgrId(dirId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_VIEW, dirMgrId, Defs.NULL_ID,Defs.NULL_ID);
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede crear un directorio
    * @param connectedUserId Identificador del usuario conectador
    * @param dirId Identificador del directorio padre
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateDir (int connectedUserId, int dirId) throws Exception
   {
      boolean   can = false;      
      int       parentDirMgrId = Defs.NULL_ID;
      DirsTable table = new DirsTable();
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (dirId == Defs.NULL_ID || dirId == Defs.ROOT_DIR_ID)
            parentDirMgrId = 0;
         else
            parentDirMgrId = this.getDirMgrId(dirId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_CREATE, Defs.NULL_ID, parentDirMgrId, Defs.NULL_ID);
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
      
      return can;
   }
   
   
   /**
    * Obtiene si el usuario conectado puede eliminar el directorio
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteDir (int connectedUserId, int dirId) throws Exception
   {
      boolean   can = false;
      int       parentId;
      int       parentDirMgrId = Defs.NULL_ID;
      DirsTable table = new DirsTable();
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());	
         
         parentId = DbSelectFns.selectLongInteger(DaoDATNodeTbl.getTblName(),
																	DaoDATNodeTbl.getParentIdColName(true),
																	table.getLoadNodeDirQual(dirId));
         
         if (parentId == Defs.ROOT_DIR_ID)
            parentDirMgrId = 0;
         else
            parentDirMgrId = this.getDirMgrId(parentId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_DELETE, Defs.NULL_ID, parentDirMgrId, Defs.NULL_ID);
         
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede editar el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditDir(int connectedUserId, int dirId) throws Exception
   {
      boolean        can = false;      
      int            parentId;        
      int            dirMgrId = Defs.NULL_ID;
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());         
         
         if (dirId == Defs.NULL_ID || dirId == Defs.ROOT_DIR_ID)
            dirMgrId = 0;
         else
            dirMgrId = this.getDirMgrId(dirId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_EDIT, dirMgrId, 
               				Defs.NULL_ID, Defs.NULL_ID);
         
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
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede mover el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @param dstDirId Identificador del directorio destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveDir (int connectedUserId, int dirId, int dstDirId) throws Exception
   {
      boolean   can = false;
      int       parentId;
      int       parentDirMgrId = Defs.NULL_ID;
      int       dstDirMgrId = Defs.NULL_ID;
      DirsTable table = new DirsTable();
      
      try
      {
      
         DbConnection.open(CfgMdoConfig.getDbConfig());	
         
         parentId = DbSelectFns.selectLongInteger(DaoDATNodeTbl.getTblName(),
																	DaoDATNodeTbl.getParentIdColName(true),
																	table.getLoadNodeDirQual(dirId));
         
         if (parentId == Defs.ROOT_DIR_ID)
            parentDirMgrId = 0;
         else
            parentDirMgrId = this.getDirMgrId(parentId);
         
         if (dstDirId == Defs.NULL_ID || dstDirId == Defs.ROOT_DIR_ID)
            dstDirMgrId = 0;
         else
            dstDirMgrId = this.getDirMgrId(dstDirId);
      
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_MOVE, Defs.NULL_ID, parentDirMgrId, dstDirMgrId);
         
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
      
      return can;
   }
   
   
   
   /**
    * Obtiene el administrador del directorio.
    * 
    * @param dirId Identificador del directorio.
    * @return El identificador.
    * @throws Exception Errores
    */   
   private int getDirMgrId(int dirId) throws Exception
   {
      int            acsId;
      int            mgrId = Defs.NULL_ID;
      DirsTable      table = new DirsTable();
      LdapUsersTable usrTable = new LdapUsersTable();
      
      acsId = DbSelectFns.selectLongInteger(table.getDirTableName(), 
															table.getAcsIdDirColumnName(),
															table.getLoadDirQual(dirId));

      mgrId = DbSelectFns.selectLongInteger(usrTable.getOwnershipTableName(),
					usrTable.getOwnerIdColumnName(),
					usrTable.getLoadOwnerIdQual(acsId));
      
      return mgrId;

   }
   
   
   /**
    * Obtiene si el usuario está autorizado para realizar una acción.
    * 
    * @param connectedUserId Identificador del usuario conectado.
    * @param actionId Identificador de la acción a realizar.
    * @param MgrId Identificador del administrador del objeto(archivador ó directorio).
    * @param parentMgrId Identificador del administrador del padre del objeto (archivador ó directorio).
    * @param dstDirMgrId Identificador del administrador del directorio destino. 
    * @return true / false
    * @throws Exception Errores
    */
   private boolean hasUserAuth(int connectedUserId, int actionId, int MgrId, 
         									int parentMgrId, int dstDirMgrId) 
   					throws Exception
   {
      boolean auth = false;
      String profile;
      
      profile = AcsMdoProfile.getiDocProfile(connectedUserId);
      
      if (AcsMdoProfile.isSysSuperuserProfile(connectedUserId) || profile == AcsProfile.IDOC_SUPERUSER)         
         auth = true;
      else if (actionId == USER_ACTION_ID_VIEW)
         auth = true;
      else if (profile != AcsProfile.IDOC_MANAGER)
         auth = false;
      else
      {
         switch(actionId)
         {
         	case USER_ACTION_ID_CREATE:
         	{
         	   if (parentMgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;
         	}
         	case USER_ACTION_ID_DELETE:
         	{
         	   if (parentMgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;
         	}
         	case USER_ACTION_ID_EDIT:
         	{
         	   if (MgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;         	   
         	}
         	case USER_ACTION_ID_MOVE:
         	{
         	   if (parentMgrId == connectedUserId  || dstDirMgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;
         	}         	   
            
         }
      }
         
      
      return auth;
   }
   
   private static final int  USER_ACTION_ID_VIEW   = 1;
   private static final int  USER_ACTION_ID_CREATE = 2;
   private static final int  USER_ACTION_ID_DELETE = 3;
   private static final int  USER_ACTION_ID_EDIT   = 4;
   private static final int  USER_ACTION_ID_MOVE   = 5;
   
  
   
   private static final Logger _logger = Logger.getLogger(ArchiveAccessImpl.class);
}

