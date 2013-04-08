package ieci.tecdoc.sgm.core.services.repositorio;

import java.util.Date;

/**
 * Clase que implementa documentos del repositorio
 *
 */
public class ContenedorDocumento
{
	/**
	 * Constructor de la clase ContenedorDocumentoImpl
	 *
	 */
   public ContenedorDocumento()
   {
     guid = null;
     content = null;
     hash = null;
     extension = null;
     timestamp = null;
     contentSize = 0;
   }
   
    /**
     * Devuelve el identificador único de documento.
     * @return String Identificador único de documento.
     */
   public String getGuid(){
     return guid;
   }
  
  /**
   * Devuelve el contenido del documento.
   * @return Blob Contenido del documento.
   */   
  public byte[] getContent(){
    return content;
  }
  
  /**
   * Devuelve el hash o resumen asociado a un documento.
   * @return String Hash o resumen asociado a un documento.
   */   
  public String getHash(){
    return hash;
  }
    
  /**
   * Devuelve la extensión del documento
   * @return String Extensión del documento.
   */   
  public String getExtension(){
    return extension;
  }
  
  /**
   * Devuelve el sello de tiempo del instante en el que
   * el documento fue almacenado
   * @return String Timestamp de almacenamiento del documento.
   */   
  public Date getTimestamp(){
    return timestamp;
  }
  
  /**
   * Devuelve tamaño del documento
   * @return int Tamaño del documento.
   */   
  public int getContentSize(){
    return contentSize;
  }
  
  /**
   * Establece identificador único de documento.
   * @param guid Identificador único de documento.
   */   
  public void setGuid(String guid){
    this.guid = guid;
  }
  
  /**
   * Establece el contenido del documento.
   * @param content Contenido del documento.
   */   
  public void setContent(byte[] content){
    this.content = content;
  }
  
  /**
   * Establece el hash o resumen del documento.
   * @param hash Hash o resumen del documento.
   */   
  public void setHash(String hash){
    this.hash = hash;
  }

  /**
   * Establece la extensión del documento.
   * @param extension Extensión del documento.
   */   
  public void setExtension(String extension){
    this.extension = extension;
  }
  
  /**
   * Establece el sello de tiempo del instante en el que
   * el documento fue almacenado
   * @param timestamp Timestamp de almacenamiento del documento.
   */   
   public void setTimestamp(Date timestamp){
     this.timestamp = timestamp;
   }
   
   /**
    * Establece el tamaño del documento
    * @param contentSize Tamaño del documento
    */   
    public void setContentSize(int contentSize){
      this.contentSize = contentSize;
    }

   protected String guid;
   protected byte[] content;
   protected String hash;
   protected String extension;
   protected Date timestamp;
   protected int contentSize;

   
}