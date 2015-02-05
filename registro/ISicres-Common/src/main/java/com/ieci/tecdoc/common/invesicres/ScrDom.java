package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DOM"
 *     
*/
public class ScrDom implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String address;

    /** nullable persistent field */
    private String city;

    /** nullable persistent field */
    private String zip;

    /** nullable persistent field */
    private String country;

    /** persistent field */
    private int preference;

    /** full constructor */
    public ScrDom(Integer id, String address, String city, String zip, String country, int preference) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.preference = preference;
    }

    /** default constructor */
    public ScrDom() {
    }

    /** minimal constructor */
    public ScrDom(Integer id, String address, int preference) {
        this.id = id;
        this.address = address;
        this.preference = preference;
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
     *             column="ADDRESS"
     *             length="100"
     *             not-null="true"
     *         
     */
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 
     *            @hibernate.property
     *             column="CITY"
     *             length="40"
     *         
     */
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /** 
     *            @hibernate.property
     *             column="ZIP"
     *             length="5"
     *         
     */
    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    /** 
     *            @hibernate.property
     *             column="COUNTRY"
     *             length="30"
     *         
     */
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /** 
     *            @hibernate.property
     *             column="PREFERENCE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getPreference() {
        return this.preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDom) ) return false;
        ScrDom castOther = (ScrDom) other;
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
