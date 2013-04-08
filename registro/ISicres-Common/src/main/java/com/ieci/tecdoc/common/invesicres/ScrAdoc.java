package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_ADOC"
 *     
*/
public class ScrAdoc implements Serializable {

    /** identifier field */
    private BigDecimal id;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private BigDecimal idca;

    /** identifier field */
    private BigDecimal obligatorio;

    /** identifier field */
    private String documento;

    /** full constructor */
    public ScrAdoc(BigDecimal id, Date timestamp, BigDecimal idca, BigDecimal obligatorio, String documento) {
        this.id = id;
        this.timestamp = timestamp;
        this.idca = idca;
        this.obligatorio = obligatorio;
        this.documento = documento;
    }

    /** default constructor */
    public ScrAdoc() {
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

    /** 
     *                @hibernate.property
     *                 column="OBLIGATORIO"
     *                 length="22"
     *             
     */
    public BigDecimal getObligatorio() {
        return this.obligatorio;
    }

    public void setObligatorio(BigDecimal obligatorio) {
        this.obligatorio = obligatorio;
    }

    /** 
     *                @hibernate.property
     *                 column="DOCUMENTO"
     *                 length="250"
     *             
     */
    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("timestamp", getTimestamp())
            .append("idca", getIdca())
            .append("obligatorio", getObligatorio())
            .append("documento", getDocumento())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrAdoc) ) return false;
        ScrAdoc castOther = (ScrAdoc) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getIdca(), castOther.getIdca())
            .append(this.getObligatorio(), castOther.getObligatorio())
            .append(this.getDocumento(), castOther.getDocumento())
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
            .append(getTimestamp())
            .append(getIdca())
            .append(getObligatorio())
            .append(getDocumento())
            .toHashCode();
    }

}
