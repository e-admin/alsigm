package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCGLBDOCH"
 *     
*/
public class Idocglbdoch implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int fdrid;

    /** identifier field */
    private int archid;

    /** identifier field */
    private String name;

    /** identifier field */
    private int clfid;

    /** identifier field */
    private int type;

    /** identifier field */
    private String title;

    /** identifier field */
    private String author;

    /** identifier field */
    private String keywords;

    /** identifier field */
    private int stat;

    /** identifier field */
    private int refcount;

    /** identifier field */
    private String remarks;

    /** identifier field */
    private int accesstype;

    /** identifier field */
    private int acsid;

    /** identifier field */
    private int crtrid;

    /** identifier field */
    private Date crtndate;

    /** identifier field */
    private Integer updrid;

    /** identifier field */
    private Date upddate;

    /** identifier field */
    private int accrid;

    /** identifier field */
    private Date accdate;

    /** identifier field */
    private int acccount;

    /** identifier field */
    private Date timestamp;

    /** full constructor */
    public Idocglbdoch(int id, int fdrid, int archid, String name, int clfid, int type, String title, String author, String keywords, int stat, int refcount, String remarks, int accesstype, int acsid, int crtrid, Date crtndate, Integer updrid, Date upddate, int accrid, Date accdate, int acccount, Date timestamp) {
        this.id = id;
        this.fdrid = fdrid;
        this.archid = archid;
        this.name = name;
        this.clfid = clfid;
        this.type = type;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.stat = stat;
        this.refcount = refcount;
        this.remarks = remarks;
        this.accesstype = accesstype;
        this.acsid = acsid;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
        this.accrid = accrid;
        this.accdate = accdate;
        this.acccount = acccount;
        this.timestamp = timestamp;
    }

    /** default constructor */
    public Idocglbdoch() {
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
     *                 column="FDRID"
     *                 length="10"
     *             
     */
    public int getFdrid() {
        return this.fdrid;
    }

    public void setFdrid(int fdrid) {
        this.fdrid = fdrid;
    }

    /** 
     *                @hibernate.property
     *                 column="ARCHID"
     *                 length="10"
     *             
     */
    public int getArchid() {
        return this.archid;
    }

    public void setArchid(int archid) {
        this.archid = archid;
    }

    /** 
     *                @hibernate.property
     *                 column="NAME"
     *                 length="32"
     *             
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *                @hibernate.property
     *                 column="CLFID"
     *                 length="10"
     *             
     */
    public int getClfid() {
        return this.clfid;
    }

    public void setClfid(int clfid) {
        this.clfid = clfid;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE"
     *                 length="10"
     *             
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 
     *                @hibernate.property
     *                 column="TITLE"
     *                 length="128"
     *             
     */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** 
     *                @hibernate.property
     *                 column="AUTHOR"
     *                 length="64"
     *             
     */
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /** 
     *                @hibernate.property
     *                 column="KEYWORDS"
     *                 length="254"
     *             
     */
    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /** 
     *                @hibernate.property
     *                 column="STAT"
     *                 length="10"
     *             
     */
    public int getStat() {
        return this.stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    /** 
     *                @hibernate.property
     *                 column="REFCOUNT"
     *                 length="10"
     *             
     */
    public int getRefcount() {
        return this.refcount;
    }

    public void setRefcount(int refcount) {
        this.refcount = refcount;
    }

    /** 
     *                @hibernate.property
     *                 column="REMARKS"
     *                 length="254"
     *             
     */
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** 
     *                @hibernate.property
     *                 column="ACCESSTYPE"
     *                 length="10"
     *             
     */
    public int getAccesstype() {
        return this.accesstype;
    }

    public void setAccesstype(int accesstype) {
        this.accesstype = accesstype;
    }

    /** 
     *                @hibernate.property
     *                 column="ACSID"
     *                 length="10"
     *             
     */
    public int getAcsid() {
        return this.acsid;
    }

    public void setAcsid(int acsid) {
        this.acsid = acsid;
    }

    /** 
     *                @hibernate.property
     *                 column="CRTRID"
     *                 length="10"
     *             
     */
    public int getCrtrid() {
        return this.crtrid;
    }

    public void setCrtrid(int crtrid) {
        this.crtrid = crtrid;
    }

    /** 
     *                @hibernate.property
     *                 column="CRTNDATE"
     *                 length="7"
     *             
     */
    public Date getCrtndate() {
        return this.crtndate;
    }

    public void setCrtndate(Date crtndate) {
        this.crtndate = crtndate;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDRID"
     *                 length="10"
     *             
     */
    public Integer getUpdrid() {
        return this.updrid;
    }

    public void setUpdrid(Integer updrid) {
        this.updrid = updrid;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDDATE"
     *                 length="7"
     *             
     */
    public Date getUpddate() {
        return this.upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    /** 
     *                @hibernate.property
     *                 column="ACCRID"
     *                 length="10"
     *             
     */
    public int getAccrid() {
        return this.accrid;
    }

    public void setAccrid(int accrid) {
        this.accrid = accrid;
    }

    /** 
     *                @hibernate.property
     *                 column="ACCDATE"
     *                 length="7"
     *             
     */
    public Date getAccdate() {
        return this.accdate;
    }

    public void setAccdate(Date accdate) {
        this.accdate = accdate;
    }

    /** 
     *                @hibernate.property
     *                 column="ACCCOUNT"
     *                 length="10"
     *             
     */
    public int getAcccount() {
        return this.acccount;
    }

    public void setAcccount(int acccount) {
        this.acccount = acccount;
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
            .append("archid", getArchid())
            .append("name", getName())
            .append("clfid", getClfid())
            .append("type", getType())
            .append("title", getTitle())
            .append("author", getAuthor())
            .append("keywords", getKeywords())
            .append("stat", getStat())
            .append("refcount", getRefcount())
            .append("remarks", getRemarks())
            .append("accesstype", getAccesstype())
            .append("acsid", getAcsid())
            .append("crtrid", getCrtrid())
            .append("crtndate", getCrtndate())
            .append("updrid", getUpdrid())
            .append("upddate", getUpddate())
            .append("accrid", getAccrid())
            .append("accdate", getAccdate())
            .append("acccount", getAcccount())
            .append("timestamp", getTimestamp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocglbdoch) ) return false;
        Idocglbdoch castOther = (Idocglbdoch) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getArchid(), castOther.getArchid())
            .append(this.getName(), castOther.getName())
            .append(this.getClfid(), castOther.getClfid())
            .append(this.getType(), castOther.getType())
            .append(this.getTitle(), castOther.getTitle())
            .append(this.getAuthor(), castOther.getAuthor())
            .append(this.getKeywords(), castOther.getKeywords())
            .append(this.getStat(), castOther.getStat())
            .append(this.getRefcount(), castOther.getRefcount())
            .append(this.getRemarks(), castOther.getRemarks())
            .append(this.getAccesstype(), castOther.getAccesstype())
            .append(this.getAcsid(), castOther.getAcsid())
            .append(this.getCrtrid(), castOther.getCrtrid())
            .append(this.getCrtndate(), castOther.getCrtndate())
            .append(this.getUpdrid(), castOther.getUpdrid())
            .append(this.getUpddate(), castOther.getUpddate())
            .append(this.getAccrid(), castOther.getAccrid())
            .append(this.getAccdate(), castOther.getAccdate())
            .append(this.getAcccount(), castOther.getAcccount())
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
            .append(getArchid())
            .append(getName())
            .append(getClfid())
            .append(getType())
            .append(getTitle())
            .append(getAuthor())
            .append(getKeywords())
            .append(getStat())
            .append(getRefcount())
            .append(getRemarks())
            .append(getAccesstype())
            .append(getAcsid())
            .append(getCrtrid())
            .append(getCrtndate())
            .append(getUpdrid())
            .append(getUpddate())
            .append(getAccrid())
            .append(getAccdate())
            .append(getAcccount())
            .append(getTimestamp())
            .toHashCode();
    }

}
