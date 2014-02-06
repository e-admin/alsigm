
package ieci.tecdoc.sbo.acs.base;

import java.io.Serializable;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sbo.util.types.SboType;

/**
 * Contiene la informacion de acceso del usuario, del departamento al que 
 * pertenece y de los grupos en los que se encuentra 
 * 
 * @author 
 *
 */

public final class AcsAccessToken implements Serializable
{
   /** Información sobre el usuario */
   private AcsAccessTokenUser   m_user;
   
   /** Información del departamento al que pertenece el usuario */
   private AcsAccessTokenDept   m_dept;
   
   /** Información sobre los grupos en los que esta incluido el usuario */
   private AcsAccessTokenGroups m_groups;
   
   /**
    * Constructor
    *
    */
   public AcsAccessToken()
   {
      m_user   = null;
      m_dept   = null;
      m_groups = new AcsAccessTokenGroups();
   }
   
   /**
    * Establece la informacion de usuario
    * 
    * @param id id de usuario
    * @param profile perfil de usuario
    * @param genPerms permisos genéricos de usuario
    */
   public void setUser(int id, String profile, int genPerms)
   {
      m_user = new AcsAccessTokenUser(id, profile, genPerms);
   }
   
   /**
    * Establece la informacion del departamento al que pertenece el usuario
    * 
    * @param id id de departamento 
    * @param genPerms permisos genéricos de departamento 
    */
   public void setDept(int id, int genPerms)
   {
      m_dept = new AcsAccessTokenDept(id, genPerms);
   }
   
   /**
    * Agrega un nuevo grupo a la lista de grupos a la que pertenece el usuario
    * 
    * @param id id de grupo
    * @param genPerms permisos genéricos de grupo
    */
   public void addGroup(int id, int genPerms)
   {
      m_groups.add(id, genPerms);
   }
   
   /**
    * Devuelve el perfil de usuario
    * 
    * @return el perfil de usuario
    */
   public String getProfile()
   {
      return m_user.getProfile();      
   }

   /**
    * Devuelve el id de usuario
    * 
    * @return el id de usuario
    */
   public int getUserId()
   {
      return m_user.getId();
   }
   
   /**
    * Devuelve el id de departamento
    * 
    * @return el id de departamento. <tt>(Integer.MAX_VALUE - 2)</tt> si el departamento
    * es nulo
    */
   public int getDeptId()
   {
      if (m_dept == null)
         return SboType.NULL_ID;
      else
         return m_dept.getId();
   }

   /**
    * Devuelve la lista de grupos a los que pertenece el usuario
    * 
    * @return lista de grupos a los que pertenece el usuario
    */
   public IeciTdLongIntegerArrayList getGroupIds()
   {
      return m_groups.getIds();
   }
   
   /**
    * Devuelve la informacion del usuario
    * 
    * @return la informacion del usuario
    */
   public AcsAccessTokenUser getUser()
   {
      return m_user;
   }
   
   /**
    * Devuelve la informacion del departamento al que pertenece el usuario
    *  
    * @return la informacion del departamento al que pertenece el usuario
    */
   public AcsAccessTokenDept getDept()
   {
      return m_dept;
   }
   
   /**
    * Devuelve la informacion de los grupos a los que pertenece el usuario 
    * 
    * @return la informacion de los grupos a los que pertenece el usuario
    */
   public AcsAccessTokenGroups getGroups()
   {
      return m_groups;
   }
   

   /**
    * @see java.lang.Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("AcsAccessToken[");
      buffer.append("m_user = ").append(m_user);
      buffer.append(", m_dept = ").append(m_dept);
      buffer.append(", m_groups = ").append(m_groups);
      buffer.append("]");
      
      return buffer.toString();
   
   }
} // class
