package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.UsersImpl;


/**
 * Maneja la lista de usuarios invesDoc.
 */
public class Users
{
	/**
    * Construye un objeto de la clase.
    *  
    */

   public Users()
   {
      _usersImpl = new UsersImpl();
   }

   /**
    * Devuelve el número de usuarios.
    * 
    * @return El número de usuarios mencionado.
    */

   public int count() 
   {
      return _usersImpl.count();
   }

   /**
    * Carga la lista de usuarios con su información básica, según un departamento.
    * 
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadByDept(int deptId) throws Exception 
   {
      _usersImpl.loadByDept(deptId);
   }
   
   /**
    * Carga la lista de usuarios con su información básica, según un grupo.
    * 
    * @param groupId Identificador del grupo.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadByGroup(int groupId) throws Exception 
   {
      _usersImpl.loadByGroup(groupId);
   }

   /**
    * Carga la lista de usuarios con su información básica, cuyo nombre 
    * contenga el texto del parámetro subName.
    * 
    * @param subName texto que tiene que contener el nombre de usuario.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadBySubName(String subName) throws Exception 
   {
      _usersImpl.loadBySubName(subName);
   }

   /**
    * Devuelve un usuario de la lista.
    * 
    * @param index Indice del usuario que se desea recuperar.
    * 
    * @return El usuario mencionado.
    */

   public User getUser(int index) 
   {
      return _usersImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de usuarios en formato XML.
    *  
    * @return La lista de usuarios mencionada.
    */

   /**
    * mueve el usuario al departamento indicado.
    * @param userId Identificador de usuario.
    * @param dstDeptId Identificador del departamento destino.
    */
   public void moveUser(int userId, int dstDeptId) throws Exception
   {
      _usersImpl.moveUser(userId,dstDeptId);
   }
   
   public String toXML()
   {
      return _usersImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _usersImpl.toString();
   }


   private UsersImpl _usersImpl;
}
