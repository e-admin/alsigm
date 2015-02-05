package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class CriterioBusquedaRegistroVO extends BaseCriterioBusquedaVO {

	private static final long serialVersionUID = -2311387648760071974L;

	protected String campo;

	protected String value;

	protected String operador;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

}
