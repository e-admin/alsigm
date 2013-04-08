package ieci.tecdoc.sgm.rde.datatypes;

import java.util.Date;


/**
 * Interfaz de comportamiento de un documento.
 * 
 * @author IECISA
 *
 */
public interface ContenedorDocumento 
{

	/**
	 * Devuelve el identificador único de documento.
	 * @return String Identificador único de documento.
	 */
   public abstract String getGuid();
   
   /**
    * Devuelve el contenido del documento.
    * @return Blob Contenido del documento.
    */   
   public abstract byte[] getContent();
   
   /**
    * Devuelve el hash o resumen asociado a un documento.
    * @return String Hash o resumen asociado a un documento.
    */   
   public abstract String getHash();
	
   /**
    * Devuelve la extensión del documento
    * @return String Extensión del documento.
    */   
   public abstract String getExtension();
   
   /**
    * Devuelve el sello de tiempo del instante en el que
    * el documento fue almacenado
    * @return Timestamp Timestamp de almacenamiento del documento.
    */   
   public abstract Date getTimestamp();
   
   /**
    * Devuelve el tamaño del documento
    * @return int Tamaño del documento.
    */   
   public abstract int getContentSize();
   
   /**
    * Establece identificador único de documento.
    * @param guid Identificador único de documento.
    */	
   public abstract void setGuid(String guid);
   
   /**
    * Establece el contenido del documento.
    * @param content Contenido del documento.
    */   
   public abstract void setContent(byte[] content);
   
   /**
    * Establece el hash o resumen del documento.
    * @param hash Hash o resumen del documento.
    */   
   public abstract void setHash(String hash);

   /**
    * Establece la extensión del documento.
    * @param extension Extensión del documento.
    */   
   public abstract void setExtension(String extension);
   
   /**
    * Establece el sello de tiempo del instante en el que
    * el documento fue almacenado
    * @param timestamp Timestamp de almacenamiento del documento.
    */   
   public abstract void setTimestamp(Date timestamp);
   
   /**
    * Establece el tamaño del documento
    * @param contentSize Tamaño del documento
    */   
   public abstract void setContentSize(int contentSize);
   
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