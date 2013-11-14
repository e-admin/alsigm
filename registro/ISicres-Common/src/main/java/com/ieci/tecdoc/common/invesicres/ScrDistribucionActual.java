package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="SCR_DISTRIBUCION_ACTUAL"
 *
*/
public class ScrDistribucionActual implements Serializable {

    /** identifier field */
    private Integer iddist;

    /** persistent field */
    private String dist_actual;


	public ScrDistribucionActual() {
		super();
	}


	public ScrDistribucionActual(Integer iddist, String dist_actual) {
		super();
		this.iddist = iddist;
		this.dist_actual = dist_actual;
	}

	/**
	 * @hibernate.id
	 * generator-class="assigned"
	 * type="java.lang.Integer"
	 * column="ID_DIST"
	 *
	 */
	public Integer getIddist() {
		return iddist;
	}


	public void setIddist(Integer iddist) {
		this.iddist = iddist;
	}

	/**
	 * @hibernate.property
	 * column="DIST_ACTUAL"
	 * length="0"
	 *
	 */
	public String getDist_actual() {
		return dist_actual;
	}


	public void setDist_actual(String dist_actual) {
		this.dist_actual = dist_actual;
	}


    public String toString() {
        return new ToStringBuilder(this)
            .append("idDist", getIddist())
            .toString();
    }

	public boolean equals(Object other) {
        if ( !(other instanceof ScrDistribucionActual) ) return false;
        ScrDistribucionActual castOther = (ScrDistribucionActual) other;
        return new EqualsBuilder()
            .append(this.getIddist(), castOther.getIddist())
            .isEquals();
    }

	public String toXML() {
	       String className = getClass().getName();
	       className = className.substring(className.lastIndexOf(".") + 1, className.length()).toUpperCase();
	       StringBuffer buffer = new StringBuffer();
	       buffer.append("<");
	       buffer.append(className);
	       buffer.append(">");
	       try {
	           java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
	           java.lang.reflect.Field field = null;
	           String name = null;
	           int size = fields.length;
	           for (int i = 0; i < size; i++) {
	               field = fields[i];
	               name = field.getName();
	               buffer.append("<");
	               buffer.append(name.toUpperCase());
	               buffer.append(">");
	               if (field.get(this) != null) {
	                   buffer.append(field.get(this));
	               }
	               buffer.append("</");
	               buffer.append(name.toUpperCase());
	               buffer.append(">");
	           }
	       } catch (Exception e) {
	           e.printStackTrace(System.err);
	       }
	       buffer.append("</");
	       buffer.append(className);
	       buffer.append(">");
	       return buffer.toString();
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getIddist())
            .toHashCode();
    }
}
