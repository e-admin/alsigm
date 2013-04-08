package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz con el comportamiento de un trámite.
 * 
 * @author IECISA
 *
 */
public interface Tramite {
   public abstract void setId(String id);

   public abstract void setTopic(String topic);

   public abstract void setDescription(String description);

   public abstract void setAddressee(String addressee);
   
   public abstract void setOficina(String oficina);

   public abstract void setLimitDocs(boolean limit);
   
   public abstract void setFirma(boolean firma);

   public abstract void setDocuments(Documentos documents);
   
   public abstract void setIdProcedimiento(String idProcedimiento);
   
   public abstract String getId();

   public abstract String getTopic();

   public abstract String getDescription();

   public abstract String getAddressee();
   
   public abstract String getOficina();

   public abstract boolean getLimitDocs();
   
   public abstract boolean getFirma();
   
   public abstract Documentos getDocuments();
   
   public abstract String getIdProcedimiento();
   
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