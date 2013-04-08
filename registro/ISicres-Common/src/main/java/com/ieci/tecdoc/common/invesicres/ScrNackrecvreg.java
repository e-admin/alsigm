package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_NACKRECVREG"
 *     
*/
public class ScrNackrecvreg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idReg;

    /** identifier field */
    private int idSendmsg;

    /** identifier field */
    private String reason;

    /** full constructor */
    public ScrNackrecvreg(int id, int idReg, int idSendmsg, String reason) {
        this.id = id;
        this.idReg = idReg;
        this.idSendmsg = idSendmsg;
        this.reason = reason;
    }

    /** default constructor */
    public ScrNackrecvreg() {
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
     *                 column="ID_REG"
     *                 length="10"
     *             
     */
    public int getIdReg() {
        return this.idReg;
    }

    public void setIdReg(int idReg) {
        this.idReg = idReg;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_SENDMSG"
     *                 length="10"
     *             
     */
    public int getIdSendmsg() {
        return this.idSendmsg;
    }

    public void setIdSendmsg(int idSendmsg) {
        this.idSendmsg = idSendmsg;
    }

    /** 
     *                @hibernate.property
     *                 column="REASON"
     *                 length="254"
     *             
     */
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idReg", getIdReg())
            .append("idSendmsg", getIdSendmsg())
            .append("reason", getReason())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrNackrecvreg) ) return false;
        ScrNackrecvreg castOther = (ScrNackrecvreg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdReg(), castOther.getIdReg())
            .append(this.getIdSendmsg(), castOther.getIdSendmsg())
            .append(this.getReason(), castOther.getReason())
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
            .append(getIdReg())
            .append(getIdSendmsg())
            .append(getReason())
            .toHashCode();
    }

}
