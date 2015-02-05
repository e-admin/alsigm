/*
 * @author IECI S.A.
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package ieci.tecdoc.sgm.autenticacion.util;

/**
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * Clase que define los diferentes tipos de autenticación válidos para un usario
 */
public class TipoAutenticacion {

   private static final int webUser = 1;
   private static final int certificate = 2;

   /**
    * Constructor de la clase
    * @param authenticationTypeId Tipo de autenticación
    */
   public TipoAutenticacion(String authenticationTypeId) 
   {
   }
   
   /**
    * Obtiene un array con los diferentes tipos de autenticación
    * @param authenticationId Tipo de autenticación
    * @return
    */
   public Integer[] getAuthenticationType(String authenticationId) 
   {
  	 return null;
   }
}