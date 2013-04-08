package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_RECVMSG"
 *     
*/
public class ScrRecvmsg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String uidMsg;

    /** identifier field */
    private int typeMsg;

    /** identifier field */
    private String fileName;

    /** identifier field */
    private int rcvType;

    /** identifier field */
    private Date rcvDate;

    /** identifier field */
    private int rcvState;

    /** identifier field */
    private Integer idAckmsg;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgBySender;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgByDestination;

    /** full constructor */
    public ScrRecvmsg(int id, String uidMsg, int typeMsg, String fileName, int rcvType, Date rcvDate, int rcvState, Integer idAckmsg, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgBySender, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrgByDestination) {
        this.id = id;
        this.uidMsg = uidMsg;
        this.typeMsg = typeMsg;
        this.fileName = fileName;
        this.rcvType = rcvType;
        this.rcvDate = rcvDate;
        this.rcvState = rcvState;
        this.idAckmsg = idAckmsg;
        this.scrOrgBySender = scrOrgBySender;
        this.scrOrgByDestination = scrOrgByDestination;
    }

    /** default constructor */
    public ScrRecvmsg() {
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
     *                 column="RCV_TYPE"
     *                 length="10"
     *             
     */
    public int getRcvType() {
        return this.rcvType;
    }

    public void setRcvType(int rcvType) {
        this.rcvType = rcvType;
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
     *                 column="RCV_STATE"
     *                 length="10"
     *             
     */
    public int getRcvState() {
        return this.rcvState;
    }

    public void setRcvState(int rcvState) {
        this.rcvState = rcvState;
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
            .append("rcvType", getRcvType())
            .append("rcvDate", getRcvDate())
            .append("rcvState", getRcvState())
            .append("idAckmsg", getIdAckmsg())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRecvmsg) ) return false;
        ScrRecvmsg castOther = (ScrRecvmsg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getUidMsg(), castOther.getUidMsg())
            .append(this.getTypeMsg(), castOther.getTypeMsg())
            .append(this.getFileName(), castOther.getFileName())
            .append(this.getRcvType(), castOther.getRcvType())
            .append(this.getRcvDate(), castOther.getRcvDate())
            .append(this.getRcvState(), castOther.getRcvState())
            .append(this.getIdAckmsg(), castOther.getIdAckmsg())
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
            .append(getRcvType())
            .append(getRcvDate())
            .append(getRcvState())
            .append(getIdAckmsg())
            .toHashCode();
    }

}
