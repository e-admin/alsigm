package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_PAGEINFO"
 *     
*/
public class ScrPageInfo implements Serializable {

    /** identifier field */
    private int bookid;

    /** identifier field */
    private int regid;

    /** identifier field */
    private int pageid;

    /** identifier field */
    private int hashVersion;

    /** identifier field */
    private String hash;

    /** full constructor */
    public ScrPageInfo(int bookid, int regid, int pageid, int hashVersion, String hash) {
        this.bookid = bookid;
        this.regid = regid;
        this.pageid = pageid;
        this.hashVersion = hashVersion;
        this.hash = hash;
    }

    /** default constructor */
    public ScrPageInfo() {
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
     *                 column="REGID"
     *                 length="10"
     *             
     */
    public int getRegid() {
        return this.regid;
    }

    public void setRegid(int regid) {
        this.regid = regid;
    }

    /** 
     *                @hibernate.property
     *                 column="PAGEID"
     *                 length="10"
     *             
     */
    public int getPageid() {
        return this.pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    /** 
     *                @hibernate.property
     *                 column="HASHVERSION"
     *                 length="10"
     *             
     */
    public int getHashVersion() {
        return this.hashVersion;
    }

    public void setHashVersion(int hashVersion) {
        this.hashVersion = hashVersion;
    }

    /** 
     *                @hibernate.property
     *                 column="HASH"
     *                 length="0"
     *             
     */
    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("bookid", getBookid())
            .append("regid", getRegid())
            .append("pageid", getPageid())
            .append("hashVersion", getHashVersion())
            .append("hash", getHash())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrPageInfo) ) return false;
        ScrPageInfo castOther = (ScrPageInfo) other;
        return new EqualsBuilder()
            .append(this.getBookid(), castOther.getBookid())
            .append(this.getRegid(), castOther.getRegid())
            .append(this.getPageid(), castOther.getPageid())
            .append(this.getHashVersion(), castOther.getHashVersion())
            .append(this.getHash(), castOther.getHash())
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
            .append(getRegid())
            .append(getPageid())
            .append(getHashVersion())
            .append(getHash())
            .toHashCode();
    }

}
