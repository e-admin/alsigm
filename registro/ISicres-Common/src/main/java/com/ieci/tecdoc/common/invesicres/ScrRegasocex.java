package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGASOCEX"
 *     
*/
public class ScrRegasocex implements Serializable {

    /** identifier field */
    private int idAsoc;

    /** identifier field */
    private int type;

    /** full constructor */
    public ScrRegasocex(int idAsoc, int type) {
        this.idAsoc = idAsoc;
        this.type = type;
    }

    /** default constructor */
    public ScrRegasocex() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ASOC"
     *                 length="10"
     *             
     */
    public int getIdAsoc() {
        return this.idAsoc;
    }

    public void setIdAsoc(int idAsoc) {
        this.idAsoc = idAsoc;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE"
     *                 length="10"
     *             
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idAsoc", getIdAsoc())
            .append("type", getType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegasocex) ) return false;
        ScrRegasocex castOther = (ScrRegasocex) other;
        return new EqualsBuilder()
            .append(this.getIdAsoc(), castOther.getIdAsoc())
            .append(this.getType(), castOther.getType())
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
            .append(getIdAsoc())
            .append(getType())
            .toHashCode();
    }

}
