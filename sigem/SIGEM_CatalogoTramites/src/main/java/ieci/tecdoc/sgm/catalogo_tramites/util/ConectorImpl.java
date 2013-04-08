package ieci.tecdoc.sgm.catalogo_tramites.util;

import java.io.Serializable;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Clase que implementa la interfaz Conector
 * @author x53492no
 *
 */
public class ConectorImpl implements Conector, Serializable
{
	/**
	 * Constructor de la clase ConectorImpl
	 *
	 */
  public ConectorImpl()
  {
     this.id = null;
     this.description = null;
     this.type = -1;
  }
  
  /**
   * Establece el identificador del conector.
   * @param String Identificador de conector.
   */   
  public void setId(String id)
  {
     this.id = id;
  }
  
  /**
   * Establece la descripción del conector.
   * @param description Descripción del conector.
   */   
  public void setDescription(String description)
  {
     this.description = description;
  }
  
  /**
   * Establece el tipo del conector.
   * @param type Tipo de conector.
   */   
  public void setType(int type)
  {
     this.type = type;
  }
  
  /**
   * Recupera el identificador del conector.
   * @return String identificador de conector.
   */   
  public String getId()
  {
     return id;
  }
  
  /**
   * Recupera la descripción del conector.
   * @return String descripción del conector.
   */   
  public String getDescription()
  {
     return description;
  }
  
  /**
   * Recupera el tipo del conector.
   * @return int tipo del conector.
   */   
  public int getType()
  {
     return type;
  }
  
  /**
   * Devuelve una cadena XMl con la información asociada al órgano destinatario.
   * @param header Establece si el XML debe llevar cabecera.
   * @return String Cadena XML.
   */   
  public String toXML(boolean header) 
  {
     XmlTextBuilder bdr;
     String tagName = "Catalogo_Conector";
     
     bdr = new XmlTextBuilder();
     if (header)
        bdr.setStandardHeader();

     bdr.addOpeningTag(tagName);
     
     bdr.addSimpleElement("Id", id);
     bdr.addSimpleElement("Description", description);
     bdr.addSimpleElement("Type", ""+type);

     bdr.addClosingTag(tagName);
     
     return bdr.getText();
  }

  /**
   * Devuelve una cadena con la información asociada a un conector.
   * @return String Cadena XML con la información del documento.
   */
    public String toString()
    {
     return toXML(false);
  }
  
  protected String id;
  protected String description;
  protected int type;
}
