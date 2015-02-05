package ieci.tecdoc.sgm.autenticacion.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;


// import org.apache.log4j.Logger;
/**
 * Clase que almacena información relacionada con un usario y su sesión, así
 * como la fecha de acceso.
 */
public class SesionInfoImpl implements SesionInfo, Serializable {

   protected String sessionId;
   protected String hookId;
   protected String loginDate;
   protected Solicitante sender;
   protected String idEntidad;
   
   public String getIdEntidad() {
	return idEntidad;
}

public void setIdEntidad(String idEntidad) {
	this.idEntidad = idEntidad;
}

/*
    * Constructor de la clase.
    */
   public SesionInfoImpl() {
      sender = new Solicitante();
      sessionId = "";
      hookId = "";
      loginDate = "";
   }

   /**
    * Obtienen el identificador de la sesión
    * @return String Identificador de sesión.
    */
   public String getSessionId() {
      return this.sessionId;
   }

   /**
    * Obtienen el identificador del conector
    * @return String Identificador de conector.
    */
   public String getHookId() {
      return this.hookId;
   }

   /**
    * Obtiene la fecha de acceso adel usuario al sistema
    * @return String Fecha de login.
    */
   public String getLoginDate() {
      return this.loginDate;
   }

   /**
    * Obtiene los datos del usuario
    * @return Emisor Datos del usuario.
    */
   public Solicitante getSender() {
      return this.sender;
   }

   /**
    * Establece el identificador de la sesión
    * @param sessionId Identificador de sesión.
    */
   public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
   }

   /**
    * Establece el identificador del conector
    * @param hookId Identificador de conector.
    */
   public void setHookId(String hookId) {
      this.hookId = hookId;
   }

   /**
    * Establece la fecha de aceso del usuario al sistema
    * @param loginDate Fecha de login.
    */
   public void setLoginDate(String loginDate) {
      this.loginDate = loginDate;
   }

   /**
    * Establece los datos del usuario
    * @param sender Datos de usuario.
    */
   public void setSender(Solicitante sender) {
      this.sender = sender;
   }

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Log de operación";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id_Sesión", sessionId);
      bdr.addSimpleElement("Id_Conector", hookId);
      bdr.addSimpleElement("Fecha_Conexión", loginDate.toString());
      bdr.addFragment(sender.toXML(false));

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve una cadena formateada con la información para escribir en el log.
    * @param additionalInfo Información adicional a incluir en la cadena para el log.
    * @return Cadena de texto con la información a escribir en el log.
    */
   public String toLog(String additionalInfo) {
      XmlTextBuilder bdr;
      String tagName = "Login";

      bdr = new XmlTextBuilder();

      bdr.addOpeningTag(tagName);

//      bdr.addSimpleElement("SessionId", sessionId);
      bdr.addSimpleElement("HookId", hookId);
//      bdr.addSimpleElement("Date", loginDate.toString());
//      bdr.addFragment(sender.toLog());
//      bdr.addSimpleElement("AdditionalInfo", additionalInfo);
      bdr.addFragment(sender.toXML(false));

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }


   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    * @return String Valores de la instancia
    */
   public String toString() {
      return toXML(false);
   }

   // private static final Logger logger = Logger.getLogger(SessionInfoImpl.class);

}