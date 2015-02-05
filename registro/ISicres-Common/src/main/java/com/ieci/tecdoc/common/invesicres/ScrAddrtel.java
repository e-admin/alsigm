package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_ADDRTEL"
 *     
*/
public class ScrAddrtel implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String address;

    /** identifier field */
    private int preference;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypeaddress scrTypeaddress;

    /** full constructor */
    public ScrAddrtel(int id, String address, int preference, com.ieci.tecdoc.common.invesicres.ScrTypeaddress scrTypeaddress) {
        this.id = id;
        this.address = address;
        this.preference = preference;
        this.scrTypeaddress = scrTypeaddress;
    }

    /** default constructor */
    public ScrAddrtel() {
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
     *                 column="ADDRESS"
     *                 length="160"
     *             
     */
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 
     *                @hibernate.property
     *                 column="PREFERENCE"
     *                 length="10"
     *             
     */
    public int getPreference() {
        return this.preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TYPE"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrTypeaddress getScrTypeaddress() {
        return this.scrTypeaddress;
    }

    public void setScrTypeaddress(com.ieci.tecdoc.common.invesicres.ScrTypeaddress scrTypeaddress) {
        this.scrTypeaddress = scrTypeaddress;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("address", getAddress())
            .append("preference", getPreference())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrAddrtel) ) return false;
        ScrAddrtel castOther = (ScrAddrtel) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getAddress(), castOther.getAddress())
            .append(this.getPreference(), castOther.getPreference())
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
            .append(getAddress())
            .append(getPreference())
            .toHashCode();
    }

}
