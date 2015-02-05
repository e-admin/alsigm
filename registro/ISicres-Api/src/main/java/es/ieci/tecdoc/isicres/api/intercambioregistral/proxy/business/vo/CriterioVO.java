package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.vo;
/**
 *  mapea CriterioDTO
 * @author Iecisa
 * @version $Revision$ 
 *
 */
public class CriterioVO {
	
	protected String nombre;
    protected String operador;
    protected Object valor;
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}

}
