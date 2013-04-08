package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="TGSS_CAPERMS"
 *     
*/
public class TgssCaperm implements Serializable {

    /** identifier field */
    private BigDecimal iduser;

    /** identifier field */
    private BigDecimal idca;

    /** full constructor */
    public TgssCaperm(BigDecimal iduser, BigDecimal idca) {
        this.iduser = iduser;
        this.idca = idca;
    }

    /** default constructor */
    public TgssCaperm() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDUSER"
     *                 length="22"
     *             
     */
    public BigDecimal getIduser() {
        return this.iduser;
    }

    public void setIduser(BigDecimal iduser) {
        this.iduser = iduser;
    }

    /** 
     *                @hibernate.property
     *                 column="IDCA"
     *                 length="22"
     *             
     */
    public BigDecimal getIdca() {
        return this.idca;
    }

    public void setIdca(BigDecimal idca) {
        this.idca = idca;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("iduser", getIduser())
            .append("idca", getIdca())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TgssCaperm) ) return false;
        TgssCaperm castOther = (TgssCaperm) other;
        return new EqualsBuilder()
            .append(this.getIduser(), castOther.getIduser())
            .append(this.getIdca(), castOther.getIdca())
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
            .append(getIdca())
            .toHashCode();
    }

}
