package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_SENDMSG"
 *     
*/
public class ScrSendmsg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String uidMsg;

    /** identifier field */
    private int typeMsg;

    /** identifier field */
    private String fileName;

    /** identifier field */
    private int sendType;

    /** identifier field */
    private Date genDate;

    /** identifier field */
    private Date sendDate;

    /** identifier field */
    private Date rcvDate;

    /** identifier field */
    private int sendState;

    /** identifier field */
    private Integer idAckmsg;

    /** identifier field */
    private int sendNum;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgBySender;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgByDestination;

    /** full constructor */
    public ScrSendmsg(int id, String uidMsg, int typeMsg, String fileName, int sendType, Date genDate, Date sendDate, Date rcvDate, int sendState, Integer idAckmsg, int sendNum, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgBySender, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgByDestination) {
        this.id = id;
        this.uidMsg = uidMsg;
        this.typeMsg = typeMsg;
        this.fileName = fileName;
        this.sendType = sendType;
        this.genDate = genDate;
        this.sendDate = sendDate;
        this.rcvDate = rcvDate;
        this.sendState = sendState;
        this.idAckmsg = idAckmsg;
        this.sendNum = sendNum;
        this.scrOrgBySender = scrOrgBySender;
        this.scrOrgByDestination = scrOrgByDestination;
    }

    /** default constructor */
    public ScrSendmsg() {
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
     *                 column="UID_MSG"
     *                 length="40"
     *             
     */
    public String getUidMsg() {
        return this.uidMsg;
    }

    public void setUidMsg(String uidMsg) {
        this.uidMsg = uidMsg;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_MSG"
     *                 length="10"
     *             
     */
    public int getTypeMsg() {
        return this.typeMsg;
    }

    public void setTypeMsg(int typeMsg) {
        this.typeMsg = typeMsg;
    }

    /** 
     *                @hibernate.property
     *                 column="FILE_NAME"
     *                 length="40"
     *             
     */
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /** 
     *                @hibernate.property
     *                 column="SEND_TYPE"
     *                 length="10"
     *             
     */
    public int getSendType() {
        return this.sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    /** 
     *                @hibernate.property
     *                 column="GEN_DATE"
     *                 length="7"
     *             
     */
    public Date getGenDate() {
        return this.genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    /** 
     *                @hibernate.property
     *                 column="SEND_DATE"
     *                 length="7"
     *             
     */
    public Date getSendDate() {
        return this.sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /** 
     *                @hibernate.property
     *                 column="RCV_DATE"
     *                 length="7"
     *             
     */
    public Date getRcvDate() {
        return this.rcvDate;
    }

    public void setRcvDate(Date rcvDate) {
        this.rcvDate = rcvDate;
    }

    /** 
     *                @hibernate.property
     *                 column="SEND_STATE"
     *                 length="10"
     *             
     */
    public int getSendState() {
        return this.sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ACKMSG"
     *                 length="10"
     *             
     */
    public Integer getIdAckmsg() {
        return this.idAckmsg;
    }

    public void setIdAckmsg(Integer idAckmsg) {
        this.idAckmsg = idAckmsg;
    }

    /** 
     *                @hibernate.property
     *                 column="SEND_NUM"
     *                 length="10"
     *             
     */
    public int getSendNum() {
        return this.sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="SENDER"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrg getScrOrgBySender() {
        return this.scrOrgBySender;
    }

    public void setScrOrgBySender(com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgBySender) {
        this.scrOrgBySender = scrOrgBySender;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="DESTINATION"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrg getScrOrgByDestination() {
        return this.scrOrgByDestination;
    }

    public void setScrOrgByDestination(com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgByDestination) {
        this.scrOrgByDestination = scrOrgByDestination;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("uidMsg", getUidMsg())
            .append("typeMsg", getTypeMsg())
            .append("fileName", getFileName())
            .append("sendType", getSendType())
            .append("genDate", getGenDate())
            .append("sendDate", getSendDate())
            .append("rcvDate", getRcvDate())
            .append("sendState", getSendState())
            .append("idAckmsg", getIdAckmsg())
            .append("sendNum", getSendNum())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrSendmsg) ) return false;
        ScrSendmsg castOther = (ScrSendmsg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getUidMsg(), castOther.getUidMsg())
            .append(this.getTypeMsg(), castOther.getTypeMsg())
            .append(this.getFileName(), castOther.getFileName())
            .append(this.getSendType(), castOther.getSendType())
            .append(this.getGenDate(), castOther.getGenDate())
            .append(this.getSendDate(), castOther.getSendDate())
            .append(this.getRcvDate(), castOther.getRcvDate())
            .append(this.getSendState(), castOther.getSendState())
            .append(this.getIdAckmsg(), castOther.getIdAckmsg())
            .append(this.getSendNum(), castOther.getSendNum())
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
            .append(getUidMsg())
            .append(getTypeMsg())
            .append(getFileName())
            .append(getSendType())
            .append(getGenDate())
            .append(getSendDate())
            .append(getRcvDate())
            .append(getSendState())
            .append(getIdAckmsg())
            .append(getSendNum())
            .toHashCode();
    }

}
