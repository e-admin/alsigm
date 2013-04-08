package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz con el comportamiento de un documento asociado a un trámite.
 * 
 * @author IECISA
 *
 */
public interface DocumentoTramite 
{
   public abstract void setProcedureId(String procedureId);
   public abstract void setDocumentId(String documentId);
   public abstract void setCode(String code);
   public abstract void setMandatory(boolean mandatory);

   public abstract String getProcedureId();
   public abstract String getDocumentId();
   public abstract String getCode();
   public abstract boolean isMandatory();

   /**
    * Recoge los valores de la instancia en una cadena xml
    * 
    * @param header
    *           Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();

}