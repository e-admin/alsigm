package ieci.tecdoc.sgm.autenticacion.util;

import ieci.tecdoc.sgm.autenticacion.util.Solicitante;

import java.util.Date;

/**
 * Clas que representa una sesión de un usario solicitante
 * @author x53492no
 *
 */
public class SesionSolicitante {

   private String sessionId;
   private String hookId;
   private String loginDate;
   private Solicitante sender;

   /**
    * Obtiene el identificador de sesión
    * @return String Identificador de sesión
    */
   public String getSessionId() {
      return this.sessionId;
   }
   
   /**
    * Obtiene el identificador de conector
    * @return String Identificador del conector de sesión
    */
   public String getHookId(){
      return this.hookId;
   }
   
   /**
    * Obtiene la fecha de inicio de sesión del usuario
    * @return String Fecha de inicio de desión
    */
   public String getLoginDate(){
      return this.loginDate;
   }
   
   /**
    * Obtiene los datos del solicitante al que pertenece la sesión
    * @return Solicitante Datos del solicitante
    */
   public Solicitante getSender(){
      return this.sender;
   }

   /**
    * Establece el identificador de sesión
    * @param sessionId Identificador de sesión
    */
   public void setSessionId(String sessionId){
      this.sessionId = sessionId;
   }
   
   /**
    * Etablece el identificador de conector de sesión
    * @param hookId Identificador del conector de sesión
    */
   public void setHookId(String hookId){
   	  this.hookId = hookId;
   }
   
   /**
    * Establece la fecha de inicio de sesión
    * @param loginDate Fecha de inicio
    */
   public void setLoginDate(String loginDate){
   	  this.loginDate = loginDate;
   }
   
   /**
    * Establece los datos del solicitante
    * @param sender Datos del solicitante
    */
   public void setSender(Solicitante sender){
   	  this.sender = sender;
   }
}