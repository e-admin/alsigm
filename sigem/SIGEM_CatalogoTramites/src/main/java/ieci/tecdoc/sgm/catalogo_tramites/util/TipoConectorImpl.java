package ieci.tecdoc.sgm.catalogo_tramites.util;

import java.io.Serializable;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Calse que implementa la intefaz TipoConector
 * @author x53492no
 *
 */
public class TipoConectorImpl implements TipoConector, Serializable
{
	/**
	 * Constructor de la clase TipoConectorImpl
	 *
	 */
  public TipoConectorImpl()
  {
     this.id = -1;
     this.description = null;
  }
  
  /**
   * Establece el identificador del tipo de conector.
   * @param int Identificador de tipo de conector.
   */   
  public void setId(int id)
  {
     this.id = id;
  }
  
  /**
   * Establece la descripción del tipo de conector.
   * @param description Descripción del tipo de conector.
   */   
  public void setDescription(String description)
  {
     this.description = description;
  }
  
  /**
   * Recupera el identificador del tipo de conector.
   * @return int identificador de tipo de conector.
   */   
  public int getId()
  {
     return id;
  }
  
  /**
   * Recupera la descripción del tipo de conector.
   * @return String descripción del tipo de conector.
   */   
  public String getDescription()
  {
     return description;
  }
  
  /**
   * Devuelve una cadena XMl con la información asociada al tipo de conector.
   * @param header Establece si el XML debe llevar cabecera.
   * @return String Cadena XML.
   */   
  public String toXML(boolean header) 
  {
     XmlTextBuilder bdr;
     String tagName = "Catalogo_TipoConector";
     
     bdr = new XmlTextBuilder();
     if (header)
        bdr.setStandardHeader();

     bdr.addOpeningTag(tagName);
     
     bdr.addSimpleElement("Id", ""+id);
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
  
  
    /**
     * Autenticación con certificado digital.
     */
    public final static int CERTIFICATE_AUTH = 1;
    
    /**
     * Autenticación con usuario Web de nivel 2.
     */
    public final static int WEB_USER_AUTH = 2;
    
    /**
     * Creación de firma.
     */
    public final static int SIGNATURE_CREATE = 3;
    
    /**
     * Verificación de firma.
     */
    public final static int SIGNATURE_VERIFY = 4;
    
    /**
     * Validación del contenido de un documento.
     */
    public final static int DOCUMENT_VERIFY = 5;

    /**
     * Obtención del certiricado.
     */
    public final static int CERTIFICATE_GET = 6;
    
    /**
     * Autenticación con certificado digital vía web.
     */
    public final static int CERTIFICATE_WEB_AUTH = 7;
   
    
  protected int id;
  protected String description;
}
