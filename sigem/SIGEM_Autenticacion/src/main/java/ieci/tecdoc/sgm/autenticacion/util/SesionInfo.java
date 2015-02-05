package ieci.tecdoc.sgm.autenticacion.util;


public interface SesionInfo {
   public abstract String getSessionId();

   public abstract String getHookId();

   public abstract String getLoginDate();

   public abstract Solicitante getSender();
   
   public abstract String getIdEntidad();

   public abstract void setSessionId(String sessionId);

   public abstract void setHookId(String hookId);

   public abstract void setLoginDate(String loginDate);

   public abstract void setSender(Solicitante sender);
   
   public abstract void setIdEntidad(String entidad);

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);
   
   /**
    * Devuelve una cadena formateada con la información para escribir en el log.
    * @param additionalInfo Información adicional a incluir en la cadena para el log.
    * @return Cadena de texto con la información a escribir en el log.
    */   
   public abstract String toLog(String additionalInfo);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();
}