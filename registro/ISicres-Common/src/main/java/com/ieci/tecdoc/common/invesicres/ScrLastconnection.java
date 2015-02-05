package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_LASTCONNECTION"
 *     
*/
public class ScrLastconnection implements Serializable {

    /** identifier field */
    private int iduser;

    /** identifier field */
    private Date connectDate;

    /** full constructor */
    public ScrLastconnection(int iduser, Date connectDate) {
        this.iduser = iduser;
        this.connectDate = connectDate;
    }

    /** default constructor */
    public ScrLastconnection() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDUSER"
     *                 length="10"
     *             
     */
    public int getIduser() {
        return this.iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /** 
     *                @hibernate.property
     *                 column="CONNECT_DATE"
     *                 length="7"
     *             
     */
    public Date getConnectDate() {
        return this.connectDate;
    }

    public void setConnectDate(Date connectDate) {
        this.connectDate = connectDate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("iduser", getIduser())
            .append("connectDate", getConnectDate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrLastconnection) ) return false;
        ScrLastconnection castOther = (ScrLastconnection) other;
        return new EqualsBuilder()
            .append(this.getIduser(), castOther.getIduser())
            .append(this.getConnectDate(), castOther.getConnectDate())
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
            .append(getIduser())
            .append(getConnectDate())
            .toHashCode();
    }

}
