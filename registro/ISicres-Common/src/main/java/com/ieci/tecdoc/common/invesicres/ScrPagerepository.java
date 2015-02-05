package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_PAGEREPOSITORY"
 *     
*/
public class ScrPagerepository implements Serializable {

    /** identifier field */
    private int bookid;

    /** identifier field */
    private int fdrid;

    /** identifier field */
    private int pageid;

    /** identifier field */
    private String docuid;

    /** full constructor */
    public ScrPagerepository(int bookid, int fdrid, int pageid, String docuid) {
        this.bookid = bookid;
        this.fdrid = fdrid;
        this.pageid = pageid;
        this.docuid = docuid;
    }

    /** default constructor */
    public ScrPagerepository() {
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
     *                 column="DOCUID"
     *                 length="32"
     *             
     */
    public String getDocuid() {
        return this.docuid;
    }

    public void setDocuid(String docuid) {
        this.docuid = docuid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("bookid", getBookid())
            .append("fdrid", getFdrid())
            .append("pageid", getPageid())
            .append("docuid", getDocuid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrPagerepository) ) return false;
        ScrPagerepository castOther = (ScrPagerepository) other;
        return new EqualsBuilder()
            .append(this.getBookid(), castOther.getBookid())
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getPageid(), castOther.getPageid())
            .append(this.getDocuid(), castOther.getDocuid())
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
            .append(getPageid())
            .append(getDocuid())
            .toHashCode();
    }

}
