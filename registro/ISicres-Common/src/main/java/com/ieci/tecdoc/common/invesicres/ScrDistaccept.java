package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DISTACCEPT"
 *     
*/
public class ScrDistaccept implements Serializable {

    /** identifier field */
    private int bookid;

    /** identifier field */
    private int regid;

    /** identifier field */
    private int officeid;

    /** identifier field */
    private Date distdate;

    /** identifier field */
    private Date accdate;

    /** identifier field */
    private String accuser;

    /** identifier field */
    private int state;

    /** identifier field */
    private String data;

    /** full constructor */
    public ScrDistaccept(int bookid, int regid, int officeid, Date distdate, Date accdate, String accuser, int state, String data) {
        this.bookid = bookid;
        this.regid = regid;
        this.officeid = officeid;
        this.distdate = distdate;
        this.accdate = accdate;
        this.accuser = accuser;
        this.state = state;
        this.data = data;
    }

    /** default constructor */
    public ScrDistaccept() {
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
     *                 column="OFFICEID"
     *                 length="10"
     *             
     */
    public int getOfficeid() {
        return this.officeid;
    }

    public void setOfficeid(int officeid) {
        this.officeid = officeid;
    }

    /** 
     *                @hibernate.property
     *                 column="DISTDATE"
     *                 length="7"
     *             
     */
    public Date getDistdate() {
        return this.distdate;
    }

    public void setDistdate(Date distdate) {
        this.distdate = distdate;
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
     *                 column="ACCUSER"
     *                 length="80"
     *             
     */
    public String getAccuser() {
        return this.accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
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
     *                 column="DATA"
     *                 length="0"
     *             
     */
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("bookid", getBookid())
            .append("regid", getRegid())
            .append("officeid", getOfficeid())
            .append("distdate", getDistdate())
            .append("accdate", getAccdate())
            .append("accuser", getAccuser())
            .append("state", getState())
            .append("data", getData())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDistaccept) ) return false;
        ScrDistaccept castOther = (ScrDistaccept) other;
        return new EqualsBuilder()
            .append(this.getBookid(), castOther.getBookid())
            .append(this.getRegid(), castOther.getRegid())
            .append(this.getOfficeid(), castOther.getOfficeid())
            .append(this.getDistdate(), castOther.getDistdate())
            .append(this.getAccdate(), castOther.getAccdate())
            .append(this.getAccuser(), castOther.getAccuser())
            .append(this.getState(), castOther.getState())
            .append(this.getData(), castOther.getData())
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
            .append(getOfficeid())
            .append(getDistdate())
            .append(getAccdate())
            .append(getAccuser())
            .append(getState())
            .append(getData())
            .toHashCode();
    }

}
