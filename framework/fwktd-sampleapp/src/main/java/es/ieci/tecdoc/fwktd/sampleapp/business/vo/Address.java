package es.ieci.tecdoc.fwktd.sampleapp.business.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class Address extends Entity {

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	// Members
	private static final long serialVersionUID = -6195631144172654057L;
	
	private String street;

}
