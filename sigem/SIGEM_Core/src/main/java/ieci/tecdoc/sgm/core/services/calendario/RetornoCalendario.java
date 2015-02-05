package ieci.tecdoc.sgm.core.services.calendario;

import java.util.Date;

/**
 * Clase que implementa la interfaz Retorno del Calendario
 * @author x53492no
 *
 */
public class RetornoCalendario
{
	/**
	 * Constructor de la clase RetornoCalendario
	 *
	 */
  public RetornoCalendario()
  {
     this.proximo = null;
     this.laborable = true;
  }
  

  public boolean isLaborable() {
	return laborable;
  }
	
  public void setLaborable(boolean laborable) {
	this.laborable = laborable;
  }
	
  public Date getProximo() {
	return proximo;
  }
	
  public void setProximo(Date proximo) {
	this.proximo = proximo;
  }
	
  protected Date proximo;      //si no es laborable aqui vendrá el próximo dia laborable
  protected boolean laborable; //si el dia pasado es laborable

}
