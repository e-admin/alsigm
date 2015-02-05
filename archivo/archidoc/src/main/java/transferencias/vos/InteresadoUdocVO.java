package transferencias.vos;

import common.Constants;

public class InteresadoUdocVO extends InteresadoVO {

	String id = null;
	int orden;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public void setValidado(String validado) {
		this.validado = Constants.TRUE_FULL_STRING.equals(validado);
	}

	public void setPrincipal(String principal) {
		this.principal = Constants.TRUE_FULL_STRING.equals(principal);
	}

}
