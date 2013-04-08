package es.ieci.tecdoc.fwktd.web.controller;

import java.io.Serializable;

/**
 * 
 * @author IECISA
 * 
 */
public abstract class AbstractFormCommand implements Serializable {

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequested() {
		return requested;
	}

	public void setRequested(String requested) {
		this.requested = requested;
	}

	private String method;

	private String requested;

	private static final long serialVersionUID = -5355057114396481449L;

}
