package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCVLDTBL2"
 *     
*/
public class Idocvldtbl2 implements Serializable {

    /** identifier field */
    private int idocid;

    /** identifier field */
    private String idocval;

    /** full constructor */
    public Idocvldtbl2(int idocid, String idocval) {
        this.idocid = idocid;
        this.idocval = idocval;
    }

    /** default constructor */
    public Idocvldtbl2() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDOCID"
     *                 length="10"
     *             
     */
    public int getIdocid() {
        return this.idocid;
    }

    public void setIdocid(int idocid) {
        this.idocid = idocid;
    }

    /** 
     *                @hibernate.property
     *                 column="IDOCVAL"
     *                 length="2"
     *             
     */
    public String getIdocval() {
        return this.idocval;
    }

    public void setIdocval(String idocval) {
        this.idocval = idocval;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idocid", getIdocid())
            .append("idocval", getIdocval())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocvldtbl2) ) return false;
        Idocvldtbl2 castOther = (Idocvldtbl2) other;
        return new EqualsBuilder()
            .append(this.getIdocid(), castOther.getIdocid())
            .append(this.getIdocval(), castOther.getIdocval())
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
            .append(getIdocid())
            .append(getIdocval())
            .toHashCode();
    }

}
