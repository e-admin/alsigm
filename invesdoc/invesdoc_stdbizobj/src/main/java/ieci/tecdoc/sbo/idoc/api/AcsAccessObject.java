package ieci.tecdoc.sbo.idoc.api;

import java.io.Serializable;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;

/**
 * Esta clase contiene la información sobre el usuario que ha realizado 
 * un login en invesDoc. Se utiliza para chequear los permisos que el 
 * usuario posee sobre archivadores, carpetas, etc. 
 *  
 * 
 */
public final class AcsAccessObject implements Serializable
{
   /** 
    * Este atributo contiene la información relevante del usuario, tanto a 
    * nivel de usuario, departamentos y grupos
    */
   AcsAccessToken m_acsToken;
   
   /**
    * Constructor
    * @param acsToken
    */
   protected AcsAccessObject(AcsAccessToken acsToken)
   {
      m_acsToken = acsToken;
   }

   /**
    * Devuelve el AcsAccessToken con la información de acceso del 
    * usuario
    * 
    * @return el AcsAccessToken
    */
   public AcsAccessToken getAccessToken()
   {
      return m_acsToken;
   }
  
   /**
    * Devuelve el perfil del usuario
    * 
    * @return el perfil del usuario
    */
   public String getProfile()
   {      
      return m_acsToken.getProfile();
   }
   
   /**
    * Devuelve el identificador de usuario
    * 
    * @return el indentificador de usuario
    */
   public int getUserId()
   {
      return m_acsToken.getUserId();
   }
   
   /**
    * Devuelve el identificador de departamento al que pertenece el usuario
    * 
    * @return identificador del departamento
    */
   public int getDeptId()
   {
      return m_acsToken.getDeptId();
   }

   /**
    * Devuelve una lista con los identificadores de los grupos a los que 
    * pertenece el usuario
    * 
    * @return identificadores de los grupos del usuario
    */
   public IeciTdLongIntegerArrayList getGroupIds()
   {
      return m_acsToken.getGroupIds();
   }

} // class
