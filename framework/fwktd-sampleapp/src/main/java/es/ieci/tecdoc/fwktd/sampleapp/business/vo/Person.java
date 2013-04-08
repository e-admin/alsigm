/*
 * 
 */
package es.ieci.tecdoc.fwktd.sampleapp.business.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.core.model.NamedEntity;

/**
 * 
 * @author IECISA
 *
 */
public class Person extends NamedEntity {
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	// Members
	private static final long serialVersionUID = 3220137494950564160L;

	private String email;
	
	private List<Address> addresses = new ArrayList<Address>();
}
