package ieci.tecdoc.idoc.admin.api.user;

/**
 * Maneja los accesos a la gestión de departamentos, grupos y usuarios.
 * 
 */
public interface UserAccess
{
   /**
    * Obtiene si el usuario conectado tiene permiso de consulta de departamento.
    * @param connectedUserId Identificador del usuario conectado.     
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewDept(int connectedUserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede crear departamentos.
    * @param connectedUserId Identificador del usuario conectado.
    * @param parentDeptId Identificador del departamento padre.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateDept(int connectedUserId, int parentDeptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede eliminar el departamento indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param DeptId Identificador del departamento.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteDept(int connectedUserId, int DeptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede modificadr el departamento indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param DeptId Identifcador de departamento.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditDept(int connectedUserId, int DeptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede mover el departamento.
    * @param connectedUserId Identificdor del usuario conectado.
    * @param DeptId Identificador del departamento.
    * @param dstDeptId Identificador del departamento destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveDept(int connectedUserId, int DeptId, int dstDeptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede consultar el grupo.
    * @param connectedUserId Identificador del usuario conectado.   
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewGroup(int connectedUserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede crear grupos.
    * @param connectedUserId Identificador del usuario conectado.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateGroup(int connectedUserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede eliminar el grupo indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param GroupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteGroup(int connectedUserId, int GroupId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede modificar el grupo indicado.
    * @param connectedUserId Identificador de usuario conectado.
    * @param GroupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditGroup(int connectedUserId, int GroupId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede consultar usuarios
    * @param connectedUserId Identificador del usuario conectado.
    * @return true / false
    * @throws Exception Errores    
    * */
   public boolean userCanViewUser(int connectedUserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede crear usuarios
    * @param connectedUserId Identificador del usuario conectado.
    * @param deptId Identificador del departamento padre.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateUser(int connectedUserId, int deptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede eliminar el usuario indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteUser(int connectedUserId ,int UserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede modificar el usuario indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditUser(int connectedUserId ,int UserId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede mover el usuario indicado al departamento
    * indicado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param UserId Identificador de usuario.
    * @param dstDeptId Identificador del departamento destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveUser(int connectedUserId ,int UserId, int dstDeptId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede asignar usuario a grupo.
    * @param connectedUserId Identificador del usuario conectado.
    * @param userId Identificador del usuario a asignar.
    * @param groupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores (usuario ya existe).
    */
   public boolean userCanAssingUserToGroup(int connectedUserId, int userId, int groupId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede elimnar usuario de grupo
    * @param connectedUserId Identificador del usuario conectado.
    * @param groupId Identificador del grupo.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteGroupUser(int connectedUserId, int groupId) throws Exception;
   
   
}
