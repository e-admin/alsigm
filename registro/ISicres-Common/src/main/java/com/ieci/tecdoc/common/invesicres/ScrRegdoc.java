package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGDOC"
 *     
*/
public class ScrRegdoc implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idArchreg;

    /** identifier field */
    private int idFdrreg;

    /** identifier field */
    private int idArchdoc;

    /** identifier field */
    private int idFdrdoc;

    /** full constructor */
    public ScrRegdoc(int id, int idArchreg, int idFdrreg, int idArchdoc, int idFdrdoc) {
        this.id = id;
        this.idArchreg = idArchreg;
        this.idFdrreg = idFdrreg;
        this.idArchdoc = idArchdoc;
        this.idFdrdoc = idFdrdoc;
    }

    /** default constructor */
    public ScrRegdoc() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ARCHREG"
     *                 length="10"
     *             
     */
    public int getIdArchreg() {
        return this.idArchreg;
    }

    public void setIdArchreg(int idArchreg) {
        this.idArchreg = idArchreg;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_FDRREG"
     *                 length="10"
     *             
     */
    public int getIdFdrreg() {
        return this.idFdrreg;
    }

    public void setIdFdrreg(int idFdrreg) {
        this.idFdrreg = idFdrreg;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ARCHDOC"
     *                 length="10"
     *             
     */
    public int getIdArchdoc() {
        return this.idArchdoc;
    }

    public void setIdArchdoc(int idArchdoc) {
        this.idArchdoc = idArchdoc;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_FDRDOC"
     *                 length="10"
     *             
     */
    public int getIdFdrdoc() {
        return this.idFdrdoc;
    }

    public void setIdFdrdoc(int idFdrdoc) {
        this.idFdrdoc = idFdrdoc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idArchreg", getIdArchreg())
            .append("idFdrreg", getIdFdrreg())
            .append("idArchdoc", getIdArchdoc())
            .append("idFdrdoc", getIdFdrdoc())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegdoc) ) return false;
        ScrRegdoc castOther = (ScrRegdoc) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdArchreg(), castOther.getIdArchreg())
            .append(this.getIdFdrreg(), castOther.getIdFdrreg())
            .append(this.getIdArchdoc(), castOther.getIdArchdoc())
            .append(this.getIdFdrdoc(), castOther.getIdFdrdoc())
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
            .append(getIdArchreg())
            .append(getIdFdrreg())
            .append(getIdArchdoc())
            .append(getIdFdrdoc())
            .toHashCode();
    }

}
