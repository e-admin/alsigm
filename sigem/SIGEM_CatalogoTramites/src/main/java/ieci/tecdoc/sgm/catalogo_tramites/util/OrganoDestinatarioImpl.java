package ieci.tecdoc.sgm.catalogo_tramites.util;

import java.io.Serializable;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Clase que implementa la clase OrganoDestinatario
 * @author x53492no
 *
 */
public class OrganoDestinatarioImpl implements OrganoDestinatario, Serializable
{
	/**
	 * Constructor de la clase OrganoDestinatario
	 *
	 */
  public OrganoDestinatarioImpl()
  {
     this.id = null;
     this.description = null;
  }
  
  /**
   * Constructor de la clase OrganoDestinatario a partir de datos
   * @param id Identificador del órgano destinatario
   * @param description Descripción correspondiente al órgano destinatario
   */
  public OrganoDestinatarioImpl(String id, String description)
  {
     this.id = id;
     this.description = description;
  }
  
  /**
   * Establece el identificador del órgano destinatario.
   * @param String Identificador de órgano destinatario.
   */   
  public void setId(String id)
  {
     this.id = id;
  }
  
  /**
   * Establece la descripción del órgano destinatario.
   * @param description Descripción del órgano destinatario.
   */   
  public void setDescription(String description)
  {
     this.description = description;
  }
  
  /**
   * Recupera el identificador del órgano destinatario.
   * @return String identificador de órgano destinatario.
   */   
  public String getId()
  {
     return id;
  }
  
  /**
   * Recupera la descripción del órgano destinatario.
   * @return String descripción del órgano destinatario.
   */   
  public String getDescription()
  {
     return description;
  }
  
  /**
   * Devuelve una cadena XMl con la información asociada al órgano destinatario.
   * @param header Establece si el XML debe llevar cabecera.
   * @return String Cadena XML.
   */   
  public String toXML(boolean header) 
  {
     XmlTextBuilder bdr;
     String tagName = "Catalogo_Tramite";
     
     bdr = new XmlTextBuilder();
     if (header)
        bdr.setStandardHeader();

     bdr.addOpeningTag(tagName);
     
     bdr.addSimpleElement("Id", id);
     bdr.addSimpleElement("Description", description);

     bdr.addClosingTag(tagName);
     
     return bdr.getText();
  }

  /**
   * Devuelve una cadena con la información asociada a un órgano destinatario.
   * @return String Cadena XML con la información del documento.
   */
    public String toString()
    {
     return toXML(false);
  }
  
  protected String id;
  protected String description;

}
