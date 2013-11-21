package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="SCR_DISTREG"
 *
*/
public class ScrDistreg implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int idArch;

    /** persistent field */
    private int idFdr;

    /** persistent field */
    private Date distDate;

    /** persistent field */
    private int typeOrig;

    /** persistent field */
    private int idOrig;

    /** persistent field */
    private int typeDest;

    /** persistent field */
    private int idDest;

    /** persistent field */
    private int state;

    /** persistent field */
    private Date stateDate;

    /** nullable persistent field */
    private String message;

    /** persistent field */
    private Integer iddistfather;


    /** default constructor */
    public ScrDistreg() {
    }

    /** minimal constructor */
    public ScrDistreg(Integer id, int idArch, int idFdr, Date distDate, int typeOrig, int idOrig, int typeDest, int idDest, int state, Date stateDate) {
        this.id = id;
        this.idArch = idArch;
        this.idFdr = idFdr;
        this.distDate = distDate;
        this.typeOrig = typeOrig;
        this.idOrig = idOrig;
        this.typeDest = typeDest;
        this.idDest = idDest;
        this.state = state;
        this.stateDate = stateDate;
    }

	/** full constructor */
    public ScrDistreg(Integer id, int idArch, int idFdr, Date distDate,
			int typeOrig, int idOrig, int typeDest, int idDest, int state,
			Date stateDate, String message, Integer iddistfather) {
		this.id = id;
		this.idArch = idArch;
		this.idFdr = idFdr;
		this.distDate = distDate;
		this.typeOrig = typeOrig;
		this.idOrig = idOrig;
		this.typeDest = typeDest;
		this.idDest = idDest;
		this.state = state;
		this.stateDate = stateDate;
		this.message = message;
		this.iddistfather = iddistfather;
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
     *             column="ID_ARCH"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    /**
     *            @hibernate.property
     *             column="ID_FDR"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getIdFdr() {
        return this.idFdr;
    }

    public void setIdFdr(int idFdr) {
        this.idFdr = idFdr;
    }

    /**
     *            @hibernate.property
     *             column="DIST_DATE"
     *             length="7"
     *             not-null="true"
     *
     */
    public Date getDistDate() {
        return this.distDate;
    }

    public void setDistDate(Date distDate) {
        this.distDate = distDate;
    }

    /**
     *            @hibernate.property
     *             column="TYPE_ORIG"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getTypeOrig() {
        return this.typeOrig;
    }

    public void setTypeOrig(int typeOrig) {
        this.typeOrig = typeOrig;
    }

    /**
     *            @hibernate.property
     *             column="ID_ORIG"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getIdOrig() {
        return this.idOrig;
    }

    public void setIdOrig(int idOrig) {
        this.idOrig = idOrig;
    }

    /**
     *            @hibernate.property
     *             column="TYPE_DEST"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getTypeDest() {
        return this.typeDest;
    }

    public void setTypeDest(int typeDest) {
        this.typeDest = typeDest;
    }

    /**
     *            @hibernate.property
     *             column="ID_DEST"
     *             length="10"
     *             not-null="true"
     *
     */
    public int getIdDest() {
        return this.idDest;
    }

    public void setIdDest(int idDest) {
        this.idDest = idDest;
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
     *             column="MESSAGE"
     *             length="250"
     *
     */
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *            @hibernate.property
     *             column="iddistfather"
     *             type="java.lang.Integer"
     *
     */

    public Integer getIddistfather() {
		return iddistfather;
	}

	public void setIddistfather(Integer iddistfather) {
		this.iddistfather = iddistfather;
	}

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }


	public boolean equals(Object other) {
        if ( !(other instanceof ScrDistreg) ) return false;
        ScrDistreg castOther = (ScrDistreg) other;
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
