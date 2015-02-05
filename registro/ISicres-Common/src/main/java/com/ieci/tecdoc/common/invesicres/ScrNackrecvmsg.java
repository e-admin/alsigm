package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_NACKRECVMSG"
 *     
*/
public class ScrNackrecvmsg implements Serializable {

    /** identifier field */
    private Integer id;

    /** identifier field */
    private int idMsg;

    /** identifier field */
    private int idSendmsg;

    /** identifier field */
    private String reason;

    /** full constructor */
    public ScrNackrecvmsg(Integer id, int idMsg, int idSendmsg, String reason) {
        this.id = id;
        this.idMsg = idMsg;
        this.idSendmsg = idSendmsg;
        this.reason = reason;
    }

    /** default constructor */
    public ScrNackrecvmsg() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_MSG"
     *                 length="10"
     *             
     */
    public int getIdMsg() {
        return this.idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
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
            .append("idMsg", getIdMsg())
            .append("idSendmsg", getIdSendmsg())
            .append("reason", getReason())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrNackrecvmsg) ) return false;
        ScrNackrecvmsg castOther = (ScrNackrecvmsg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdMsg(), castOther.getIdMsg())
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
            .append(getIdMsg())
            .append(getIdSendmsg())
            .append(getReason())
            .toHashCode();
    }

}
