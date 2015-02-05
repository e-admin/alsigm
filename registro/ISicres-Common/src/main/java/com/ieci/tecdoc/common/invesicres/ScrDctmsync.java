package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DCTMSYNC"
 *     
*/
public class ScrDctmsync implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int bookid;

    /** identifier field */
    private int fdrid;

    /** identifier field */
    private String username;

    /** identifier field */
    private String dctmObjguid;

    /** identifier field */
    private String dctmPath;

    /** identifier field */
    private String javaMethod;

    /** identifier field */
    private int operationType;

    /** identifier field */
    private int state;

    /** identifier field */
    private Date stateDate;

    /** identifier field */
    private String errorinfo;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrDctmdocbase scrDctmdocbase;

    /** full constructor */
    public ScrDctmsync(int id, int bookid, int fdrid, String username, String dctmObjguid, String dctmPath, String javaMethod, int operationType, int state, Date stateDate, String errorinfo, com.ieci.tecdoc.common.invesicres.ScrDctmdocbase scrDctmdocbase) {
        this.id = id;
        this.bookid = bookid;
        this.fdrid = fdrid;
        this.username = username;
        this.dctmObjguid = dctmObjguid;
        this.dctmPath = dctmPath;
        this.javaMethod = javaMethod;
        this.operationType = operationType;
        this.state = state;
        this.stateDate = stateDate;
        this.errorinfo = errorinfo;
        this.scrDctmdocbase = scrDctmdocbase;
    }

    /** default constructor */
    public ScrDctmsync() {
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
     *                 column="BOOKID"
     *                 length="10"
     *             
     */
    public int getBookid() {
        return this.bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
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
     *                 column="USERNAME"
     *                 length="32"
     *             
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     *                @hibernate.property
     *                 column="DCTM_OBJGUID"
     *                 length="16"
     *             
     */
    public String getDctmObjguid() {
        return this.dctmObjguid;
    }

    public void setDctmObjguid(String dctmObjguid) {
        this.dctmObjguid = dctmObjguid;
    }

    /** 
     *                @hibernate.property
     *                 column="DCTM_PATH"
     *                 length="250"
     *             
     */
    public String getDctmPath() {
        return this.dctmPath;
    }

    public void setDctmPath(String dctmPath) {
        this.dctmPath = dctmPath;
    }

    /** 
     *                @hibernate.property
     *                 column="JAVA_METHOD"
     *                 length="250"
     *             
     */
    public String getJavaMethod() {
        return this.javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    /** 
     *                @hibernate.property
     *                 column="OPERATION_TYPE"
     *                 length="10"
     *             
     */
    public int getOperationType() {
        return this.operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
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
     *                 column="ERRORINFO"
     *                 length="32"
     *             
     */
    public String getErrorinfo() {
        return this.errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="DOCBASEID"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrDctmdocbase getScrDctmdocbase() {
        return this.scrDctmdocbase;
    }

    public void setScrDctmdocbase(com.ieci.tecdoc.common.invesicres.ScrDctmdocbase scrDctmdocbase) {
        this.scrDctmdocbase = scrDctmdocbase;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("bookid", getBookid())
            .append("fdrid", getFdrid())
            .append("username", getUsername())
            .append("dctmObjguid", getDctmObjguid())
            .append("dctmPath", getDctmPath())
            .append("javaMethod", getJavaMethod())
            .append("operationType", getOperationType())
            .append("state", getState())
            .append("stateDate", getStateDate())
            .append("errorinfo", getErrorinfo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDctmsync) ) return false;
        ScrDctmsync castOther = (ScrDctmsync) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getBookid(), castOther.getBookid())
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getUsername(), castOther.getUsername())
            .append(this.getDctmObjguid(), castOther.getDctmObjguid())
            .append(this.getDctmPath(), castOther.getDctmPath())
            .append(this.getJavaMethod(), castOther.getJavaMethod())
            .append(this.getOperationType(), castOther.getOperationType())
            .append(this.getState(), castOther.getState())
            .append(this.getStateDate(), castOther.getStateDate())
            .append(this.getErrorinfo(), castOther.getErrorinfo())
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
            .append(getBookid())
            .append(getFdrid())
            .append(getUsername())
            .append(getDctmObjguid())
            .append(getDctmPath())
            .append(getJavaMethod())
            .append(getOperationType())
            .append(getState())
            .append(getStateDate())
            .append(getErrorinfo())
            .toHashCode();
    }

}
