package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

public class TransporteForm extends RPAdminBaseForm {

	private static final long serialVersionUID = 1L;
	
	private String[] attrsToUpper = new String[] { "transport" };
	
	private String id;
	private String transport;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}
	
	

}
