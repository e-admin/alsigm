/**
 * 
 */
package es.ieci.tecdoc.fwktd.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @author IECISA
 * 
 */
public class Entity extends BaseValueObject {

	public String getId() {
		return id;
	}
	
	public Long getIdAsLong() {
		return Long.parseLong(id);
	}
	
	public Integer getIdAsInteger() {
		return Integer.parseInt(id);
	}

	public void setId(String anId) {
		this.id = anId;
	}

	/*
	 * Implementación comparando identificadores.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ((this == o))
			return true;
		if (!(o instanceof Entity))
			return false;

		Entity otherEntity = (Entity) o;

		return new EqualsBuilder().append(otherEntity.getId(), this.getId())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return "Entity con ID: " + getId();
	}

	// Members
	private static final long serialVersionUID = 5515426564073999982L;

	private String id;
}
