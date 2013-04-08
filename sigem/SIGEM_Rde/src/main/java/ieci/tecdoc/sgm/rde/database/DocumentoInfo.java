package ieci.tecdoc.sgm.rde.database;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;

/**
 * Clase que almacena información relacionada con los documentos
 *
 */
public class DocumentoInfo implements Serializable {

  /**
   * Construye un objeto de la clase.
   */
  public DocumentoInfo() {

     super();
  }

  /**
   * Devuelve el identificador del documento.
   * @return String Identificador del documento.
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
 * Devuelve la extensión del documento
 * @return String Extensión del documento.
 */   
public String getExtension(){
  return extension;
}

/**
 * Devuelve el tipo de mime del documento
 * @return String Tipo de mime del documento
 */   
public String getMimeType(){
  return mimeType;
}

/**
 * Establece el identificador del documento.
 * @param guid Identificador del documento.
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
 * Establece la extensión del documento.
 * @param extension Extensión del documento.
 */   
public void setExtension(String extension){
  this.extension = extension;
}

/**
 * Establece el tipo de mime del documento
 * @param mimeType Tipo de mime del documento
 */   
 public void setMimeType(String mimeType){
   this.mimeType = mimeType;
 }

 /**
  * Recoge los valores de la instancia en una cadena xml
  * @param header Si se incluye la cabecera
  * @return los datos en formato xml
  */   
 public String toXML(boolean header) {
    XmlTextBuilder bdr;
    String tagName = "Document";

    bdr = new XmlTextBuilder();
    if (header)
       bdr.setStandardHeader();

    bdr.addOpeningTag(tagName);

    bdr.addSimpleElement("ContentSize", "" + content.length);
    bdr.addSimpleElement("Extension", extension);
    bdr.addSimpleElement("MimeType", mimeType);

    bdr.addClosingTag(tagName);

    return bdr.getText();
 }

 /**
  * Devuelve los valores de la instancia en una cadena de caracteres.
  */
 public String toString() {
    return toXML(false);
 }

 protected String guid;
 protected byte[] content;
 protected String extension;
 protected String mimeType;
}
