package ieci.tecdoc.sgm.core.services.catalogo;

/**
 * Clase que implementa la interfaz Conector
 * @author x53492no
 *
 */
public class Conector
{
	/**
	 * Constructor de la clase ConectorImpl
	 *
	 */
  public Conector()
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
  
  
  protected String id;
  protected String description;
  protected int type;
}
