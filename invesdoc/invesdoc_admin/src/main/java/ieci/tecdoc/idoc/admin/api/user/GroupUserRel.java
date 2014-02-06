package ieci.tecdoc.idoc.admin.api.user;

import java.util.ArrayList;

public interface GroupUserRel
{
   
   /**
    * Añade el usuario indicado al grupo indicado.
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  assignUserToGroup(int groupId, int userId) throws Exception;

   /**
    * Elimina un usuario de un grupo
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  deleteGroupUser(int groupId, int userId) throws Exception;
   
   public ArrayList loadUserGroupIds (int userId) throws Exception;
   
}
