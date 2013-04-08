package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DISTREGTGSS"
 *     
*/
public class ScrDistregtgss implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idArch;

    /** identifier field */
    private int idFdr;

    /** identifier field */
    private Date distDate;

    /** identifier field */
    private int typeOrig;

    /** identifier field */
    private int idOrig;

    /** identifier field */
    private int typeDest;

    /** identifier field */
    private int idDest;

    /** identifier field */
    private int state;

    /** identifier field */
    private Date stateDate;

    /** identifier field */
    private String message;

    /** identifier field */
    private String regnum;

    /** identifier field */
    private int regdest;

    /** full constructor */
    public ScrDistregtgss(int id, int idArch, int idFdr, Date distDate, int typeOrig, int idOrig, int typeDest, int idDest, int state, Date stateDate, String message, String regnum, int regdest) {
        this.id = id;
        this.idArch = idArch;
        this.idFdr = idFdr;
        this.distDate = distDate;
        this.typeOrig = typeOrig;
        this.idOrig = idOrig;
        this.typeDest = typeDest;
        this.idDest = idDest;
        this.state = state;
        this.stateDate = stateDate;
        this.message = message;
        this.regnum = regnum;
        this.regdest = regdest;
    }

    /** default constructor */
    public ScrDistregtgss() {
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
     *                 column="DIST_DATE"
     *                 length="7"
     *             
     */
    public Date getDistDate() {
        return this.distDate;
    }

    public void setDistDate(Date distDate) {
        this.distDate = distDate;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_ORIG"
     *                 length="10"
     *             
     */
    public int getTypeOrig() {
        return this.typeOrig;
    }

    public void setTypeOrig(int typeOrig) {
        this.typeOrig = typeOrig;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ORIG"
     *                 length="10"
     *             
     */
    public int getIdOrig() {
        return this.idOrig;
    }

    public void setIdOrig(int idOrig) {
        this.idOrig = idOrig;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_DEST"
     *                 length="10"
     *             
     */
    public int getTypeDest() {
        return this.typeDest;
    }

    public void setTypeDest(int typeDest) {
        this.typeDest = typeDest;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_DEST"
     *                 length="10"
     *             
     */
    public int getIdDest() {
        return this.idDest;
    }

    public void setIdDest(int idDest) {
        this.idDest = idDest;
    }

    /** 
     *                @hibernate.property
     *                 column="STATE"
     *                 length="10"
     *             
     */
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /** 
     *                @hibernate.property
     *                 column="STATE_DATE"
     *                 length="7"
     *             
     */
    public Date getStateDate() {
        return this.stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /** 
     *                @hibernate.property
     *                 column="MESSAGE"
     *                 length="250"
     *             
     */
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** 
     *                @hibernate.property
     *                 column="REGNUM"
     *                 length="20"
     *             
     */
    public String getRegnum() {
        return this.regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    /** 
     *                @hibernate.property
     *                 column="REGDEST"
     *                 length="10"
     *             
     */
    public int getRegdest() {
        return this.regdest;
    }

    public void setRegdest(int regdest) {
        this.regdest = regdest;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idArch", getIdArch())
            .append("idFdr", getIdFdr())
            .append("distDate", getDistDate())
            .append("typeOrig", getTypeOrig())
            .append("idOrig", getIdOrig())
            .append("typeDest", getTypeDest())
            .append("idDest", getIdDest())
            .append("state", getState())
            .append("stateDate", getStateDate())
            .append("message", getMessage())
            .append("regnum", getRegnum())
            .append("regdest", getRegdest())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDistregtgss) ) return false;
        ScrDistregtgss castOther = (ScrDistregtgss) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdArch(), castOther.getIdArch())
            .append(this.getIdFdr(), castOther.getIdFdr())
            .append(this.getDistDate(), castOther.getDistDate())
            .append(this.getTypeOrig(), castOther.getTypeOrig())
            .append(this.getIdOrig(), castOther.getIdOrig())
            .append(this.getTypeDest(), castOther.getTypeDest())
            .append(this.getIdDest(), castOther.getIdDest())
            .append(this.getState(), castOther.getState())
            .append(this.getStateDate(), castOther.getStateDate())
            .append(this.getMessage(), castOther.getMessage())
            .append(this.getRegnum(), castOther.getRegnum())
            .append(this.getRegdest(), castOther.getRegdest())
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
            .append(getIdArch())
            .append(getIdFdr())
            .append(getDistDate())
            .append(getTypeOrig())
            .append(getIdOrig())
            .append(getTypeDest())
            .append(getIdDest())
            .append(getState())
            .append(getStateDate())
            .append(getMessage())
            .append(getRegnum())
            .append(getRegdest())
            .toHashCode();
    }

}
