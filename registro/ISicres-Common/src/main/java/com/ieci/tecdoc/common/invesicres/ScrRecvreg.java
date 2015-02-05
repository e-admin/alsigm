package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_RECVREG"
 *     
*/
public class ScrRecvreg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idMsg;

    /** identifier field */
    private int idArch;

    /** identifier field */
    private int idFdr;

    /** identifier field */
    private String regorigCode;

    /** identifier field */
    private String regorigNum;

    /** identifier field */
    private Date regorigDate;

    /** identifier field */
    private int regorigType;

    /** identifier field */
    private int attachPages;

    /** identifier field */
    private int attachDoc;

    /** identifier field */
    private Integer idAckmsg;

    /** identifier field */
    private int rcvState;

    /** identifier field */
    private String rcvNumreg;

    /** identifier field */
    private Date rcvDatereg;

    /** full constructor */
    public ScrRecvreg(int id, int idMsg, int idArch, int idFdr, String regorigCode, String regorigNum, Date regorigDate, int regorigType, int attachPages, int attachDoc, Integer idAckmsg, int rcvState, String rcvNumreg, Date rcvDatereg) {
        this.id = id;
        this.idMsg = idMsg;
        this.idArch = idArch;
        this.idFdr = idFdr;
        this.regorigCode = regorigCode;
        this.regorigNum = regorigNum;
        this.regorigDate = regorigDate;
        this.regorigType = regorigType;
        this.attachPages = attachPages;
        this.attachDoc = attachDoc;
        this.idAckmsg = idAckmsg;
        this.rcvState = rcvState;
        this.rcvNumreg = rcvNumreg;
        this.rcvDatereg = rcvDatereg;
    }

    /** default constructor */
    public ScrRecvreg() {
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
     *                 column="ID_ARCH"
     *                 length="10"
     *             
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_FDR"
     *                 length="10"
     *             
     */
    public int getIdFdr() {
        return this.idFdr;
    }

    public void setIdFdr(int idFdr) {
        this.idFdr = idFdr;
    }

    /** 
     *                @hibernate.property
     *                 column="REGORIG_CODE"
     *                 length="16"
     *             
     */
    public String getRegorigCode() {
        return this.regorigCode;
    }

    public void setRegorigCode(String regorigCode) {
        this.regorigCode = regorigCode;
    }

    /** 
     *                @hibernate.property
     *                 column="REGORIG_NUM"
     *                 length="20"
     *             
     */
    public String getRegorigNum() {
        return this.regorigNum;
    }

    public void setRegorigNum(String regorigNum) {
        this.regorigNum = regorigNum;
    }

    /** 
     *                @hibernate.property
     *                 column="REGORIG_DATE"
     *                 length="7"
     *             
     */
    public Date getRegorigDate() {
        return this.regorigDate;
    }

    public void setRegorigDate(Date regorigDate) {
        this.regorigDate = regorigDate;
    }

    /** 
     *                @hibernate.property
     *                 column="REGORIG_TYPE"
     *                 length="10"
     *             
     */
    public int getRegorigType() {
        return this.regorigType;
    }

    public void setRegorigType(int regorigType) {
        this.regorigType = regorigType;
    }

    /** 
     *                @hibernate.property
     *                 column="ATTACH_PAGES"
     *                 length="10"
     *             
     */
    public int getAttachPages() {
        return this.attachPages;
    }

    public void setAttachPages(int attachPages) {
        this.attachPages = attachPages;
    }

    /** 
     *                @hibernate.property
     *                 column="ATTACH_DOC"
     *                 length="10"
     *             
     */
    public int getAttachDoc() {
        return this.attachDoc;
    }

    public void setAttachDoc(int attachDoc) {
        this.attachDoc = attachDoc;
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
     *                 column="RCV_NUMREG"
     *                 length="20"
     *             
     */
    public String getRcvNumreg() {
        return this.rcvNumreg;
    }

    public void setRcvNumreg(String rcvNumreg) {
        this.rcvNumreg = rcvNumreg;
    }

    /** 
     *                @hibernate.property
     *                 column="RCV_DATEREG"
     *                 length="7"
     *             
     */
    public Date getRcvDatereg() {
        return this.rcvDatereg;
    }

    public void setRcvDatereg(Date rcvDatereg) {
        this.rcvDatereg = rcvDatereg;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idMsg", getIdMsg())
            .append("idArch", getIdArch())
            .append("idFdr", getIdFdr())
            .append("regorigCode", getRegorigCode())
            .append("regorigNum", getRegorigNum())
            .append("regorigDate", getRegorigDate())
            .append("regorigType", getRegorigType())
            .append("attachPages", getAttachPages())
            .append("attachDoc", getAttachDoc())
            .append("idAckmsg", getIdAckmsg())
            .append("rcvState", getRcvState())
            .append("rcvNumreg", getRcvNumreg())
            .append("rcvDatereg", getRcvDatereg())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRecvreg) ) return false;
        ScrRecvreg castOther = (ScrRecvreg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdMsg(), castOther.getIdMsg())
            .append(this.getIdArch(), castOther.getIdArch())
            .append(this.getIdFdr(), castOther.getIdFdr())
            .append(this.getRegorigCode(), castOther.getRegorigCode())
            .append(this.getRegorigNum(), castOther.getRegorigNum())
            .append(this.getRegorigDate(), castOther.getRegorigDate())
            .append(this.getRegorigType(), castOther.getRegorigType())
            .append(this.getAttachPages(), castOther.getAttachPages())
            .append(this.getAttachDoc(), castOther.getAttachDoc())
            .append(this.getIdAckmsg(), castOther.getIdAckmsg())
            .append(this.getRcvState(), castOther.getRcvState())
            .append(this.getRcvNumreg(), castOther.getRcvNumreg())
            .append(this.getRcvDatereg(), castOther.getRcvDatereg())
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
            .append(getIdArch())
            .append(getIdFdr())
            .append(getRegorigCode())
            .append(getRegorigNum())
            .append(getRegorigDate())
            .append(getRegorigType())
            .append(getAttachPages())
            .append(getAttachDoc())
            .append(getIdAckmsg())
            .append(getRcvState())
            .append(getRcvNumreg())
            .append(getRcvDatereg())
            .toHashCode();
    }

}
