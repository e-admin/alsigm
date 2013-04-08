package ieci.tecdoc.idoc.admin.internal;

import org.apache.log4j.Logger;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.database.DepartmentsTable;
import ieci.tecdoc.idoc.admin.database.GroupsTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
/**
 * Implementa el interfaz UserAccess.
 */

public class UserAccessImpl implements UserAccess
{
   public UserAccessImpl()
   {
      
   }
   
   /////////////////////////////////////////////////////////////////////////
   ///            permisos en departamentos
   ////////////////////////////////////////////////////////////////////////
   
   /**
    * Obtiene si el usuario conectado tiene permiso de consulta de departamento.
    * @param connectedUserId Identificador del usuario conectado.    
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewDept(int connectedUserId, String entidad) throws Exception
   {
      boolean        can = false;
      
      try
      {
  
         can = hasUserDeptAuth(connectedUserId, USER_ACTION_ID_VIEW, Defs.NULL_ID, Defs.NULL_ID,Defs.NULL_ID, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
   
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede crear departamentos.
    * @param connectedUserId Identificador del usuario conectado.
    * @param parentDeptId Identificador del departamento padre.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateDept(int connectedUserId, int parentDeptId, String entidad) throws Exception
   {
      boolean  can = false;
      int      parentDeptMgrId = Defs.NULL_ID;
      
      try
      {     
         
         if (parentDeptId == Defs.NULL_ID || parentDeptId == Defs.ROOT_DEPT_ID)
            parentDeptMgrId = 0;
         else
            parentDeptMgrId = this.getDeptMgrId(parentDeptId, entidad);
      
         can = hasUserDeptAuth(connectedUserId, USER_ACTION_ID_CREATE, Defs.NULL_ID, parentDeptMgrId,Defs.NULL_ID, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede eliminar el departamento indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param DeptId Identificador del departamento.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteDept(int connectedUserId, int DeptId, String entidad) throws Exception
   {
      boolean  can = false;      
      int      parentId = Defs.NULL_ID;
      int      parentDeptMgrId = Defs.NULL_ID;
      
      try
      {    
         
         parentId = getDeptParentId(DeptId, entidad);
         
         if (parentId == Defs.ROOT_DEPT_ID)
            parentDeptMgrId = 0;
         else                  
            parentDeptMgrId = getDeptMgrId(parentId, entidad);
      
         can = hasUserDeptAuth(connectedUserId, USER_ACTION_ID_DELETE, Defs.NULL_ID, parentDeptMgrId,Defs.NULL_ID, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   
   /**
    * Obtiene si el usuario conectado puede modificadr el departamento indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param DeptId Identifcador de departamento.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditDept(int connectedUserId, int DeptId, String entidad) throws Exception
   {
      boolean  can = false;
      int      deptMgrId = Defs.NULL_ID;
      
      try
      {
         
         deptMgrId = getDeptMgrId(DeptId, entidad);
      
         can = hasUserDeptAuth(connectedUserId, USER_ACTION_ID_EDIT, deptMgrId, Defs.NULL_ID, Defs.NULL_ID, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede mover el departamento.
    * @param connectedUserId Identificdor del usuario conectado.
    * @param DeptId Identificador del departamento.
    * @param dstDeptId Identificador del departamento destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveDept(int connectedUserId, int DeptId, int dstDeptId, String entidad) throws Exception
   {
      boolean  can = false;
      int      parentId = Defs.NULL_ID;
      int      parentDeptMgrId = Defs.NULL_ID;
      int      dstDeptMgrId = Defs.NULL_ID;
      

     
      try
      {     
         
         parentId = getDeptParentId(DeptId, entidad);
         
         if (parentId == Defs.ROOT_DEPT_ID)
            parentDeptMgrId = 0;
         else         
            parentDeptMgrId = getDeptMgrId(DeptId, entidad);
         
         if (dstDeptId == Defs.NULL_ID || dstDeptId == Defs.ROOT_DEPT_ID)
            dstDeptMgrId = 0;
         else
            dstDeptMgrId = getDeptMgrId(dstDeptId, entidad);         
      
         can = hasUserDeptAuth(connectedUserId, USER_ACTION_ID_EDIT, Defs.NULL_ID, 
               				parentDeptMgrId, dstDeptMgrId, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
      return can;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   ///               permisos en grupos
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * Obtiene si el usuario conectado puede consultar el grupo.
    * @param connectedUserId Identificador del usuario conectado.   
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewGroup(int connectedUserId, String entidad) throws Exception
   {
      boolean        can = false;
      
      try
      {      
         
         can = hasUserGroupAuth(connectedUserId, USER_ACTION_ID_VIEW, Defs.NULL_ID, entidad);
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede crear grupos.
    * @param connectedUserId Identificador del usuario conectado.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateGroup(int connectedUserId, String entidad) throws Exception
   {
      boolean  can = false;
      
      try{   
         
         can = hasUserGroupAuth(connectedUserId, USER_ACTION_ID_CREATE, Defs.NULL_ID, entidad);             
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede eliminar el grupo indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param GroupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteGroup(int connectedUserId, int GroupId, String entidad) throws Exception
   {
      boolean  can = false;      
      int      mgrGroup = Defs.NULL_ID;
      
      try
      {
      
         
         mgrGroup = this.getGroupMgrId(GroupId, entidad);
         
         can = hasUserGroupAuth(connectedUserId, USER_ACTION_ID_CREATE, mgrGroup, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede modificar el grupo indicado.
    * @param connectedUserId Identificador de usuario conectado.
    * @param GroupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditGroup(int connectedUserId, int GroupId, String entidad) throws Exception
   {
      boolean  can = false;      
      int      mgrGroup = Defs.NULL_ID;
       
      try
      {      
         
         mgrGroup = this.getGroupMgrId(GroupId, entidad);
         
         can = hasUserGroupAuth(connectedUserId, USER_ACTION_ID_EDIT, mgrGroup, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   ///               permisos en usuarios
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * Obtiene si el usuario conectado puede consultar usuarios
    * @param connectedUserId Identificador del usuario conectado.
    * @return true / false
    * @throws Exception Errores    
    * */
   public boolean userCanViewUser(int connectedUserId, String entidad) throws Exception
   {
      boolean can = false;
      
      try
      {
         
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_VIEW, Defs.NULL_ID, Defs.NULL_ID, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede crear usuarios
    * @param connectedUserId Identificador del usuario conectado.
    * @param deptId Identificador del departamento padre.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateUser(int connectedUserId, int deptId, String entidad) throws Exception
   {
      boolean can = false;
      int     deptMgrId = Defs.NULL_ID;      
      
      try
      {
        
        //HAY QUE MIRAR SI HAY LICENCIAS DISPONIBLES.
         
         deptMgrId = getDeptMgrId(deptId, entidad);
         
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_CREATE, deptMgrId, Defs.NULL_ID, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede eliminar el usuario indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteUser(int connectedUserId ,int UserId, String entidad) throws Exception
   {
      boolean can = false;      
      int     parentId = Defs.NULL_ID;
      int     deptMgrId = Defs.NULL_ID;
      
      try
      {         
         
         parentId = getUserParentId(UserId, entidad);
         deptMgrId = getDeptMgrId(parentId, entidad);
         
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_DELETE, deptMgrId, Defs.NULL_ID, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede modificar el usuario indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditUser(int connectedUserId ,int UserId, String entidad) throws Exception
   {
      boolean can = false;      
      int     parentId = Defs.NULL_ID;
      int     deptMgrId = Defs.NULL_ID;
       
      try
      {         
         
         parentId = getUserParentId(UserId, entidad);
         deptMgrId = getDeptMgrId(parentId, entidad);
         
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_EDIT, deptMgrId, Defs.NULL_ID, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede mover el usuario indicado al departamento
    * indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario.
    * @param dstDeptId Identificador del departamento destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveUser(int connectedUserId ,int UserId, int dstDeptId, String entidad) throws Exception
   {
      boolean can = false;      
      int     parentId = Defs.NULL_ID;
      int     deptMgrId = Defs.NULL_ID;
      int     dstMgrId = Defs.NULL_ID;
      
      try
      {        
         
         parentId = getUserParentId(UserId, entidad);
         deptMgrId = getDeptMgrId(parentId, entidad);
         dstMgrId = getDeptMgrId(dstDeptId, entidad);
         
         can = hasUserAuth(connectedUserId, USER_ACTION_ID_MOVE, deptMgrId, dstMgrId, entidad);         
         
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede asignar usuario a grupo.
    * @param connectedUserId Identificador del usuario conectado.
    * @param userId Identificador del usuario a asignar.
    * @param groupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores (usuario ya existe).
    */
   public boolean userCanAssingUserToGroup(int connectedUserId, int userId, int groupId, String entidad) throws Exception
   {
      boolean can = false;
      
      try
      {
         
         if (existsUserInGroup(userId, groupId, entidad))
            AdminException.throwException(UserErrorCodes.EC_USER_EXITS);
      
         can = this.userCanEditGroup(connectedUserId,groupId, entidad);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   /**
    * Obtiene si el usuario conectado puede elimnar usuario de grupo
    * @param connectedUserId Identificador del usuario conectado.
    * @param groupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteGroupUser(int connectedUserId, int groupId, String entidad) throws Exception
   {
      boolean can = false;
        
      try
      {
      
         can  = userCanEditGroup(connectedUserId, groupId, entidad);
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return can;
   }
   
   
   /**
    * Obtiene la existencia de la relación grupo-usuario.
    * @param userId Identificador de usuario.
    * @param groupId Identificador de grupo.
    * @return true / false
    * @throws Exception Errores
    */
   private boolean existsUserInGroup(int userId, int groupId, String entidad) throws Exception
   {
      boolean exists = false;
      UsersTable table = new UsersTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
	      exists = DbSelectFns.rowExists(dbConn, table.getGURelTableName(), 
	            								 table.getLoadGURelQual(userId, groupId));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
	      
      return exists;
   }
   
   
   /**
    * Obtiene el identificador del departamento del usuario indicado.
    * @param id Identificador de usuario.
    * @return Identificador del departamento.
    * @throws Exception Errores
    */
   private int getUserParentId(int id, String entidad) throws Exception
   {
      int        deptId = Defs.NULL_ID;
      UsersTable usrTable = new UsersTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
	      deptId = DbSelectFns.selectLongInteger(dbConn, usrTable.getBaseTableName(),
	            											usrTable.getDeptIdUserColumnName(),
	            											usrTable.getLoadUserIdQual(id));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
	      
      return deptId;
      
   }
   
   /**
    * Obtiene el identificador del padre del departamento indicado.
    * @param deptId Identificador del departamento.
    * @return Identificador del padre del departamento.
    * @throws Exception Errores
    */   
   private int getDeptParentId(int deptId, String entidad) throws Exception
   {
      int               parentId = Defs.NULL_ID;      
      DepartmentsTable  table = new DepartmentsTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
	      parentId = DbSelectFns.selectLongInteger(dbConn, table.getBaseTableName(),
						table.getParentIdDeptColumnName(),
						table.getLoadBaseQual(deptId));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
	      
      return parentId;

   }
   
   /**
    * Obtiene el administrador del directorio.
    * 
    * @param dirId Identificador del directorio.
    * @return El identificador.
    * @throws Exception Errores
    */   
   private int getDeptMgrId(int deptId, String entidad) throws Exception
   {     
      int              mgrId = Defs.NULL_ID;     
      DepartmentsTable  table = new DepartmentsTable();
      
      DbConnection dbConn=new DbConnection();
      
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");
    	  
	      mgrId = DbSelectFns.selectLongInteger(dbConn, table.getBaseTableName(),
						table.getMgrDeptColumnName(),
						table.getLoadBaseQual(deptId));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
      
      return mgrId;

   }

   /**
    * Obtiene el administrador del grupo.
    * 
    * @param dirId Identificador del grupo.
    * @return El identificador.
    * @throws Exception Errores
    */   
   private int getGroupMgrId(int groupId, String entidad) throws Exception
   {     
      int          mgrId = Defs.NULL_ID;     
      GroupsTable  table = new GroupsTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
	      mgrId = DbSelectFns.selectLongInteger(dbConn, table.getBaseTableName(),
						table.getMgrGroupColumnName(),
						table.getLoadBaseQual(groupId));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
	      
      return mgrId;

   }

   
   /**
    * Obtiene si el usuario está autorizado para realizar una acción sobre departamentos.
    * 
    * @param connectedUserId Identificador del usuario conectado.
    * @param actionId Identificador de la acción a realizar.
    * @param MgrId Identificador del administrador del objeto(departamento).
    * @param parentMgrId Identificador del administrador del padre del objeto (departamento).
    * @param dstDirMgrId Identificador del administrador del directorio destino. 
    * @return true / false
    * @throws Exception Errores
    */
   private boolean hasUserDeptAuth(int connectedUserId, int actionId, int mgrId, 
         									int parentMgrId, int dstDptMgrId, String entidad) 
   					throws Exception
   {
      boolean auth = false;
      String profile;
      
      profile = AcsMdoProfile.getiUserProfile(connectedUserId, entidad);
      
      if (AcsMdoProfile.isSysSuperuserProfile(connectedUserId, entidad) || profile == AcsProfile.IUSER_SUPERUSER)
      {
         auth = true;
      }
      else if ( (actionId == USER_ACTION_ID_VIEW) && 
               ( profile == AcsProfile.IUSER_STANDARD || profile == AcsProfile.IUSER_MANAGER ) )
      {   
         auth = true;
      }
      else if (profile != AcsProfile.IUSER_MANAGER)
      {
         auth = false;
      }
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
         	   if (mgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;         	   
         	}
         	case USER_ACTION_ID_MOVE:
         	{
         	   if (parentMgrId == connectedUserId  || dstDptMgrId == connectedUserId)
         	      auth = true;
         	   
         	   break;
         	}         	   
            
         }
      }         
     
      return auth;
   }
   
   /**
    * Obtiene si el usuario conectado tiene autorización sobre operaciónes en usuarios.
    * @param connectedUserId Identificador del usuario conectado.
    * @param actionId Identificador de la acción a realizar.
    * @param parentMgrId Identificador del administrador del departamento padre.
    * @param dstDptMgrId Identificador del acministrador del departamento destino.
    * @return true / false
    * @throws Exception Errores
    */
   private boolean hasUserAuth(int connectedUserId, int actionId, 
										 int parentMgrId, int dstDptMgrId, String entidad) 
   						throws Exception
  	{
      boolean auth = false;
      String profile;

      profile = AcsMdoProfile.getiUserProfile(connectedUserId, entidad);

      if (AcsMdoProfile.isSysSuperuserProfile(connectedUserId, entidad) || profile == AcsProfile.IUSER_SUPERUSER)
      {
         auth = true;
      }
      else if ( (actionId == USER_ACTION_ID_VIEW) && 
            	( profile == AcsProfile.IUSER_STANDARD || profile == AcsProfile.IUSER_MANAGER ) )
      {   
         auth = true;
      }
      else if (profile != AcsProfile.IUSER_MANAGER)
      {
         auth = false;
      }
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
					if (parentMgrId == connectedUserId)
					auth = true;
					
					break;         	   
				}
				case USER_ACTION_ID_MOVE:
				{
					if (parentMgrId == connectedUserId  || dstDptMgrId == connectedUserId)
					auth = true;
					
					break;
				}         	   
			
			}
      }


      return auth;
  	}

   /**
    * Obtiene si el usuario conectado puede realizar acciones sobre grupos.
    * @param connectedUserId Identificador del usuario conectado.
    * @param actionId Identificador de la acción a realizar.
    * @param MgrId Identificador del administrador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   private boolean hasUserGroupAuth(int connectedUserId, int actionId, int MgrId, String entidad) throws Exception
   {      
      boolean auth = false;
      String profile;
      
      profile = AcsMdoProfile.getiUserProfile(connectedUserId, entidad);
      
      if (AcsMdoProfile.isSysSuperuserProfile(connectedUserId, entidad) || 
          profile == AcsProfile.IUSER_SUPERUSER)
      {
         auth = true;
      }
      
      switch(actionId)
      {
      	case USER_ACTION_ID_VIEW:
      	{
      	   if (profile == AcsProfile.IUSER_STANDARD || profile == AcsProfile.IUSER_MANAGER )
      	      auth = true;
      	   
      	   break;
      	}
      	case USER_ACTION_ID_CREATE:
      	{
      	   if (profile == AcsProfile.IUSER_MANAGER)
      	      auth = true;
      	   break;
      	}
      	case USER_ACTION_ID_DELETE:
      	{
      	   if (profile == AcsProfile.IUSER_MANAGER && MgrId == connectedUserId)
      	      auth = true;
      	   
      	   break;
      	}
      	case USER_ACTION_ID_EDIT:
      	{
      	   if (profile == AcsProfile.IUSER_MANAGER && MgrId == connectedUserId)
      	      auth = true;
      	   
      	   break;
      	}
      }
            
      
      return auth;
   }
   
   private static final int  USER_ACTION_ID_VIEW   = 1;
   private static final int  USER_ACTION_ID_CREATE = 2;
   private static final int  USER_ACTION_ID_DELETE = 3;
   private static final int  USER_ACTION_ID_EDIT   = 4;
   private static final int  USER_ACTION_ID_MOVE   = 5;
   
  
   
   private static final Logger _logger = Logger.getLogger(UserAccessImpl.class);
   
}
