package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGASOC"
 *     
*/
public class ScrRegasoc implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int idArchprim;

    /** persistent field */
    private int idFdrprim;

    /** persistent field */
    private int idArchsec;

    /** persistent field */
    private int idFdrsec;

    /** full constructor */
    public ScrRegasoc(Integer id, int idArchprim, int idFdrprim, int idArchsec, int idFdrsec) {
        this.id = id;
        this.idArchprim = idArchprim;
        this.idFdrprim = idFdrprim;
        this.idArchsec = idArchsec;
        this.idFdrsec = idFdrsec;
    }

    /** default constructor */
    public ScrRegasoc() {
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
     *             column="ID_ARCHPRIM"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdArchprim() {
        return this.idArchprim;
    }

    public void setIdArchprim(int idArchprim) {
        this.idArchprim = idArchprim;
    }

    /** 
     *            @hibernate.property
     *             column="ID_FDRPRIM"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdFdrprim() {
        return this.idFdrprim;
    }

    public void setIdFdrprim(int idFdrprim) {
        this.idFdrprim = idFdrprim;
    }

    /** 
     *            @hibernate.property
     *             column="ID_ARCHSEC"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdArchsec() {
        return this.idArchsec;
    }

    public void setIdArchsec(int idArchsec) {
        this.idArchsec = idArchsec;
    }

    /** 
     *            @hibernate.property
     *             column="ID_FDRSEC"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdFdrsec() {
        return this.idFdrsec;
    }

    public void setIdFdrsec(int idFdrsec) {
        this.idFdrsec = idFdrsec;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegasoc) ) return false;
        ScrRegasoc castOther = (ScrRegasoc) other;
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
