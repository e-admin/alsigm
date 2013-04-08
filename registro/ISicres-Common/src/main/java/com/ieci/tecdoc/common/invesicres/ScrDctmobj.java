package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DCTMOBJ"
 *     
*/
public class ScrDctmobj implements Serializable {

    /** identifier field */
    private int bookid;

    /** identifier field */
    private int fdrid;

    /** identifier field */
    private String dctmObjguid;

    /** identifier field */
    private int docbaseid;

    /** full constructor */
    public ScrDctmobj(int bookid, int fdrid, String dctmObjguid, int docbaseid) {
        this.bookid = bookid;
        this.fdrid = fdrid;
        this.dctmObjguid = dctmObjguid;
        this.docbaseid = docbaseid;
    }

    /** default constructor */
    public ScrDctmobj() {
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
     *                 column="DOCBASEID"
     *                 length="10"
     *             
     */
    public int getDocbaseid() {
        return this.docbaseid;
    }

    public void setDocbaseid(int docbaseid) {
        this.docbaseid = docbaseid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("bookid", getBookid())
            .append("fdrid", getFdrid())
            .append("dctmObjguid", getDctmObjguid())
            .append("docbaseid", getDocbaseid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDctmobj) ) return false;
        ScrDctmobj castOther = (ScrDctmobj) other;
        return new EqualsBuilder()
            .append(this.getBookid(), castOther.getBookid())
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getDctmObjguid(), castOther.getDctmObjguid())
            .append(this.getDocbaseid(), castOther.getDocbaseid())
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
            .append(getBookid())
            .append(getFdrid())
            .append(getDctmObjguid())
            .append(getDocbaseid())
            .toHashCode();
    }

}
