package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERUSERSYS"
 *     
*/
public class Iuserusersys implements Serializable {

    /** identifier field */
    private int maxbaccnt;

    /** identifier field */
    private int pwdvp;

    /** identifier field */
    private String pwdmbc;

    /** identifier field */
    private int pwdminlen;

    /** identifier field */
    private int pwexpiredap;

    /** identifier field */
    private int numpwdlock;

    /** identifier field */
    private Integer flags;

    /** identifier field */
    private Integer updrid;

    /** identifier field */
    private Date upddate;

    /** full constructor */
    public Iuserusersys(int maxbaccnt, int pwdvp, String pwdmbc, int pwdminlen, int pwexpiredap, int numpwdlock, Integer flags, Integer updrid, Date upddate) {
        this.maxbaccnt = maxbaccnt;
        this.pwdvp = pwdvp;
        this.pwdmbc = pwdmbc;
        this.pwdminlen = pwdminlen;
        this.pwexpiredap = pwexpiredap;
        this.numpwdlock = numpwdlock;
        this.flags = flags;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Iuserusersys() {
    }

    /** 
     *                @hibernate.property
     *                 column="MAXBACCNT"
     *                 length="10"
     *             
     */
    public int getMaxbaccnt() {
        return this.maxbaccnt;
    }

    public void setMaxbaccnt(int maxbaccnt) {
        this.maxbaccnt = maxbaccnt;
    }

    /** 
     *                @hibernate.property
     *                 column="PWDVP"
     *                 length="20"
     *             
     */
    public int getPwdvp() {
        return this.pwdvp;
    }

    public void setPwdvp(int pwdvp) {
        this.pwdvp = pwdvp;
    }

    /** 
     *                @hibernate.property
     *                 column="PWDMBC"
     *                 length="1"
     *             
     */
    public String getPwdmbc() {
        return this.pwdmbc;
    }

    public void setPwdmbc(String pwdmbc) {
        this.pwdmbc = pwdmbc;
    }

    /** 
     *                @hibernate.property
     *                 column="PWDMINLEN"
     *                 length="5"
     *             
     */
    public int getPwdminlen() {
        return this.pwdminlen;
    }

    public void setPwdminlen(int pwdminlen) {
        this.pwdminlen = pwdminlen;
    }

    /** 
     *                @hibernate.property
     *                 column="PWEXPIREDAP"
     *                 length="20"
     *             
     */
    public int getPwexpiredap() {
        return this.pwexpiredap;
    }

    public void setPwexpiredap(int pwexpiredap) {
        this.pwexpiredap = pwexpiredap;
    }

    /** 
     *                @hibernate.property
     *                 column="NUMPWDLOCK"
     *                 length="5"
     *             
     */
    public int getNumpwdlock() {
        return this.numpwdlock;
    }

    public void setNumpwdlock(int numpwdlock) {
        this.numpwdlock = numpwdlock;
    }

    /** 
     *                @hibernate.property
     *                 column="FLAGS"
     *                 length="10"
     *             
     */
    public Integer getFlags() {
        return this.flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("maxbaccnt", getMaxbaccnt())
            .append("pwdvp", getPwdvp())
            .append("pwdmbc", getPwdmbc())
            .append("pwdminlen", getPwdminlen())
            .append("pwexpiredap", getPwexpiredap())
            .append("numpwdlock", getNumpwdlock())
            .append("flags", getFlags())
            .append("updrid", getUpdrid())
            .append("upddate", getUpddate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserusersys) ) return false;
        Iuserusersys castOther = (Iuserusersys) other;
        return new EqualsBuilder()
            .append(this.getMaxbaccnt(), castOther.getMaxbaccnt())
            .append(this.getPwdvp(), castOther.getPwdvp())
            .append(this.getPwdmbc(), castOther.getPwdmbc())
            .append(this.getPwdminlen(), castOther.getPwdminlen())
            .append(this.getPwexpiredap(), castOther.getPwexpiredap())
            .append(this.getNumpwdlock(), castOther.getNumpwdlock())
            .append(this.getFlags(), castOther.getFlags())
            .append(this.getUpdrid(), castOther.getUpdrid())
            .append(this.getUpddate(), castOther.getUpddate())
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
            .append(getMaxbaccnt())
            .append(getPwdvp())
            .append(getPwdmbc())
            .append(getPwdminlen())
            .append(getPwexpiredap())
            .append(getNumpwdlock())
            .append(getFlags())
            .append(getUpdrid())
            .append(getUpddate())
            .toHashCode();
    }

}
