package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_BOOKADMIN"
 *     
*/
public class ScrBookadmin implements Serializable {

    /** identifier field */
    private int idbook;

    /** identifier field */
    private int iduser;

    /** full constructor */
    public ScrBookadmin(int idbook, int iduser) {
        this.idbook = idbook;
        this.iduser = iduser;
    }

    /** default constructor */
    public ScrBookadmin() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDBOOK"
     *                 length="10"
     *             
     */
    public int getIdbook() {
        return this.idbook;
    }

    public void setIdbook(int idbook) {
        this.idbook = idbook;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("idbook", getIdbook())
            .append("iduser", getIduser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrBookadmin) ) return false;
        ScrBookadmin castOther = (ScrBookadmin) other;
        return new EqualsBuilder()
            .append(this.getIdbook(), castOther.getIdbook())
            .append(this.getIduser(), castOther.getIduser())
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
            .append(getIdbook())
            .append(getIduser())
            .toHashCode();
    }

}
