package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_VISDATE"
 *     
*/
public class ScrVisdate implements Serializable {

    /** identifier field */
    private BigDecimal idarch;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private Date visdate;

    /** full constructor */
    public ScrVisdate(BigDecimal idarch, Date timestamp, Date visdate) {
        this.idarch = idarch;
        this.timestamp = timestamp;
        this.visdate = visdate;
    }

    /** default constructor */
    public ScrVisdate() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDARCH"
     *                 length="22"
     *             
     */
    public BigDecimal getIdarch() {
        return this.idarch;
    }

    public void setIdarch(BigDecimal idarch) {
        this.idarch = idarch;
    }

    /** 
     *                @hibernate.property
     *                 column="TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /** 
     *                @hibernate.property
     *                 column="VISDATE"
     *                 length="7"
     *             
     */
    public Date getVisdate() {
        return this.visdate;
    }

    public void setVisdate(Date visdate) {
        this.visdate = visdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idarch", getIdarch())
            .append("timestamp", getTimestamp())
            .append("visdate", getVisdate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrVisdate) ) return false;
        ScrVisdate castOther = (ScrVisdate) other;
        return new EqualsBuilder()
            .append(this.getIdarch(), castOther.getIdarch())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getVisdate(), castOther.getVisdate())
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
            .append(getIdarch())
            .append(getTimestamp())
            .append(getVisdate())
            .toHashCode();
    }

}
