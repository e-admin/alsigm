package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DCTMCA"
 *     
*/
public class ScrDctmca implements Serializable {

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private int bookType;

    /** identifier field */
    private String dctmPath;

    /** identifier field */
    private int modifiable;

    /** identifier field */
    private String javaMethod;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrDctmdocbase scrDctmdocbase;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrCa scrCa;

    /** full constructor */
    public ScrDctmca(Date timestamp, int bookType, String dctmPath, int modifiable, String javaMethod, com.ieci.tecdoc.common.invesicres.ScrDctmdocbase scrDctmdocbase, com.ieci.tecdoc.common.invesicres.ScrCa scrCa) {
        this.timestamp = timestamp;
        this.bookType = bookType;
        this.dctmPath = dctmPath;
        this.modifiable = modifiable;
        this.javaMethod = javaMethod;
        this.scrDctmdocbase = scrDctmdocbase;
        this.scrCa = scrCa;
    }

    /** default constructor */
    public ScrDctmca() {
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
     *                 column="BOOK_TYPE"
     *                 length="10"
     *             
     */
    public int getBookType() {
        return this.bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
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
     *                 column="MODIFIABLE"
     *                 length="10"
     *             
     */
    public int getModifiable() {
        return this.modifiable;
    }

    public void setModifiable(int modifiable) {
        this.modifiable = modifiable;
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDCA"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrCa getScrCa() {
        return this.scrCa;
    }

    public void setScrCa(com.ieci.tecdoc.common.invesicres.ScrCa scrCa) {
        this.scrCa = scrCa;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("timestamp", getTimestamp())
            .append("bookType", getBookType())
            .append("dctmPath", getDctmPath())
            .append("modifiable", getModifiable())
            .append("javaMethod", getJavaMethod())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDctmca) ) return false;
        ScrDctmca castOther = (ScrDctmca) other;
        return new EqualsBuilder()
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getBookType(), castOther.getBookType())
            .append(this.getDctmPath(), castOther.getDctmPath())
            .append(this.getModifiable(), castOther.getModifiable())
            .append(this.getJavaMethod(), castOther.getJavaMethod())
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
            .append(getTimestamp())
            .append(getBookType())
            .append(getDctmPath())
            .append(getModifiable())
            .append(getJavaMethod())
            .toHashCode();
    }

}
