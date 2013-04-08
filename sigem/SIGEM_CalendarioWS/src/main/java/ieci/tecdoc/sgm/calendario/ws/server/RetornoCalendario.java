package ieci.tecdoc.sgm.calendario.ws.server; 

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Clase que implementa la interfaz Retorno del Calendario
 * @author x53492no
 *
 */
public class RetornoCalendario extends RetornoServicio
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
	
  public String getProximo() {
	return proximo;
  }
	
  public void setProximo(String proximo) {
	this.proximo = proximo;
  }
	
  protected String proximo;      //si no es laborable aqui vendrá el próximo dia laborable
  protected boolean laborable; //si el dia pasado es laborable

}
