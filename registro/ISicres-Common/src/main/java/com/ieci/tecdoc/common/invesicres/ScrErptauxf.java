package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_ERPTAUXF"
 *     
*/
public class ScrErptauxf implements Serializable {

    /** identifier field */
    private BigDecimal id;

    /** identifier field */
    private BigDecimal fdrid;

    /** identifier field */
    private BigDecimal fldid;

    /** identifier field */
    private String text;

    /** identifier field */
    private Date timestamp;

    /** full constructor */
    public ScrErptauxf(BigDecimal id, BigDecimal fdrid, BigDecimal fldid, String text, Date timestamp) {
        this.id = id;
        this.fdrid = fdrid;
        this.fldid = fldid;
        this.text = text;
        this.timestamp = timestamp;
    }

    /** default constructor */
    public ScrErptauxf() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="22"
     *             
     */
    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="FDRID"
     *                 length="22"
     *             
     */
    public BigDecimal getFdrid() {
        return this.fdrid;
    }

    public void setFdrid(BigDecimal fdrid) {
        this.fdrid = fdrid;
    }

    /** 
     *                @hibernate.property
     *                 column="FLDID"
     *                 length="22"
     *             
     */
    public BigDecimal getFldid() {
        return this.fldid;
    }

    public void setFldid(BigDecimal fldid) {
        this.fldid = fldid;
    }

    /** 
     *                @hibernate.property
     *                 column="TEXT"
     *                 length="0"
     *             
     */
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("fdrid", getFdrid())
            .append("fldid", getFldid())
            .append("text", getText())
            .append("timestamp", getTimestamp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrErptauxf) ) return false;
        ScrErptauxf castOther = (ScrErptauxf) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getFldid(), castOther.getFldid())
            .append(this.getText(), castOther.getText())
            .append(this.getTimestamp(), castOther.getTimestamp())
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
            .append(getFdrid())
            .append(getFldid())
            .append(getText())
            .append(getTimestamp())
            .toHashCode();
    }

}
