package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CITIES"
 *     
*/
public class ScrCity implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private Date tmstamp;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private int idProv;

    /** full constructor */
    public ScrCity(Integer id, Date tmstamp, String code, String name, int idProv) {
        this.id = id;
        this.tmstamp = tmstamp;
        this.code = code;
        this.name = name;
        this.idProv = idProv;
    }

    /** default constructor */
    public ScrCity() {
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
     *             column="TMSTAMP"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getTmstamp() {
        return this.tmstamp;
    }

    public void setTmstamp(Date tmstamp) {
        this.tmstamp = tmstamp;
    }

    /** 
     *            @hibernate.property
     *             column="CODE"
     *             length="16"
     *             not-null="true"
     *         
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="40"
     *             not-null="true"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="ID_PROV"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdProv() {
        return this.idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCity) ) return false;
        ScrCity castOther = (ScrCity) other;
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
