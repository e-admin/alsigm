package ieci.tecdoc.sgm.autenticacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class MetodoAutenticacion extends RetornoServicio {

	  private String id;
	  private String description;
	  private int type;
	  
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
