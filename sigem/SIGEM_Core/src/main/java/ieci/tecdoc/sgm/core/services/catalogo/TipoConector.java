package ieci.tecdoc.sgm.core.services.catalogo;

/**
 * Calse que implementa la intefaz TipoConector
 * @author x53492no
 *
 */
public class TipoConector
{
	/**
	 * Constructor de la clase TipoConectorImpl
	 *
	 */
  public TipoConector()
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
