package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_NACKSENDREG"
 *     
*/
public class ScrNacksendreg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idReg;

    /** identifier field */
    private int idRcvmsg;

    /** identifier field */
    private String reason;

    /** full constructor */
    public ScrNacksendreg(int id, int idReg, int idRcvmsg, String reason) {
        this.id = id;
        this.idReg = idReg;
        this.idRcvmsg = idRcvmsg;
        this.reason = reason;
    }

    /** default constructor */
    public ScrNacksendreg() {
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
     *                 column="ID_RCVMSG"
     *                 length="10"
     *             
     */
    public int getIdRcvmsg() {
        return this.idRcvmsg;
    }

    public void setIdRcvmsg(int idRcvmsg) {
        this.idRcvmsg = idRcvmsg;
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
            .append("idRcvmsg", getIdRcvmsg())
            .append("reason", getReason())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrNacksendreg) ) return false;
        ScrNacksendreg castOther = (ScrNacksendreg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdReg(), castOther.getIdReg())
            .append(this.getIdRcvmsg(), castOther.getIdRcvmsg())
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
            .append(getIdRcvmsg())
            .append(getReason())
            .toHashCode();
    }

}
