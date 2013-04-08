package ieci.tecdoc.sgm.catalogo_tramites.util;


public interface ConectorAutenticacion {
   public abstract String getTramiteId();

   public abstract String getConectorId();

   public abstract void setTramiteId(String tramiteId);

   public abstract void setConectorId(String conectorId);

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();
}
