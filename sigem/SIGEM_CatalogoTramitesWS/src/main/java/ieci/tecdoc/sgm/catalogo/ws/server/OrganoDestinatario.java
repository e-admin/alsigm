package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: OrganoDestinatario.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


/**
 * Clase que implementa la clase OrganoDestinatario
 * @author x53492no
 *
 */
public class OrganoDestinatario extends RetornoServicio
{
	/**
	 * Constructor de la clase OrganoDestinatario
	 *
	 */
  public OrganoDestinatario()
  {
     this.id = null;
     this.description = null;
  }
  
  /**
   * Constructor de la clase OrganoDestinatario a partir de datos
   * @param id Identificador del órgano destinatario
   * @param description Descripción correspondiente al órgano destinatario
   */
  public OrganoDestinatario(String id, String description)
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
  
  protected String id;
  protected String description;

}
