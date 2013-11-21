package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="SCR_DISTREGSTATE"
 *
*/
public class ScrDistregstate implements Serializable {

	private static final long serialVersionUID = 1758996812714734266L;

	/** identifier field */
    private Integer id;

    /** persistent field */
    private int idDist;

    /** persistent field */
    private int state;

    /** persistent field */
    private Date stateDate;

    /** persistent field */
    private String username;

    private String message;

    /** full constructor */
    public ScrDistregstate(Integer id, int idDist, int state, Date stateDate, String username, String message) {
        this.id = id;
        this.idDist = idDist;
        this.state = state;
        this.stateDate = stateDate;
        this.username = username;
        this.message = message;
    }

    /** default constructor */
    public ScrDistregstate() {
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="ID"
     *
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *            @hibernate.property
     *             column="ID_DIST"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getIdDist() {
        return this.idDist;
    }

    public void setIdDist(int idDist) {
        this.idDist = idDist;
    }

    /**
     *            @hibernate.property
     *             column="STATE"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     *            @hibernate.property
     *             column="STATE_DATE"
     *             length="7"
     *             not-null="true"
     *
     */
    public Date getStateDate() {
        return this.stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /**
     *            @hibernate.property
     *             column="USERNAME"
     *             length="32"
     *             not-null="true"
     *
     */
    public String getUsername() {
        return this.username;
    }



    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDistregstate) ) return false;
        ScrDistregstate castOther = (ScrDistregstate) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }




//************************************
// Incluido pos ISicres-Common Oracle 9i


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

//************************************

public int hashCode() {

        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
