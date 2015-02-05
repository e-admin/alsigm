package es.ieci.tecdoc.isicres.admin.estructura.dao;

import java.util.ArrayList;

public interface GroupUserRel
{
   
   /**
    * Añade el usuario indicado al grupo indicado.
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  assignUserToGroup(int groupId, int userId, String entidad) throws Exception;

   /**
    * Elimina un usuario de un grupo
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  deleteGroupUser(int groupId, int userId, String entidad) throws Exception;
   
   public ArrayList loadUserGroupIds (int userId, String entidad) throws Exception;
   
}
