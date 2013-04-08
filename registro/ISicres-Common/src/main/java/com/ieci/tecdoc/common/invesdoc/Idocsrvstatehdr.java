package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCSRVSTATEHDR"
 *     
*/
public class Idocsrvstatehdr implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int prodid;

    /** identifier field */
    private int appid;

    /** identifier field */
    private int userid;

    /** identifier field */
    private int aliasid;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private int random;

    /** identifier field */
    private String info;

    /** full constructor */
    public Idocsrvstatehdr(int id, int prodid, int appid, int userid, int aliasid, Date timestamp, int random, String info) {
        this.id = id;
        this.prodid = prodid;
        this.appid = appid;
        this.userid = userid;
        this.aliasid = aliasid;
        this.timestamp = timestamp;
        this.random = random;
        this.info = info;
    }

    /** default constructor */
    public Idocsrvstatehdr() {
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
     *                 column="PRODID"
     *                 length="10"
     *             
     */
    public int getProdid() {
        return this.prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    /** 
     *                @hibernate.property
     *                 column="APPID"
     *                 length="10"
     *             
     */
    public int getAppid() {
        return this.appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    /** 
     *                @hibernate.property
     *                 column="USERID"
     *                 length="10"
     *             
     */
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    /** 
     *                @hibernate.property
     *                 column="ALIASID"
     *                 length="10"
     *             
     */
    public int getAliasid() {
        return this.aliasid;
    }

    public void setAliasid(int aliasid) {
        this.aliasid = aliasid;
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
     *                 column="RANDOM"
     *                 length="10"
     *             
     */
    public int getRandom() {
        return this.random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    /** 
     *                @hibernate.property
     *                 column="INFO"
     *                 length="0"
     *             
     */
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("prodid", getProdid())
            .append("appid", getAppid())
            .append("userid", getUserid())
            .append("aliasid", getAliasid())
            .append("timestamp", getTimestamp())
            .append("random", getRandom())
            .append("info", getInfo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocsrvstatehdr) ) return false;
        Idocsrvstatehdr castOther = (Idocsrvstatehdr) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getProdid(), castOther.getProdid())
            .append(this.getAppid(), castOther.getAppid())
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getAliasid(), castOther.getAliasid())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getRandom(), castOther.getRandom())
            .append(this.getInfo(), castOther.getInfo())
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
            .append(getProdid())
            .append(getAppid())
            .append(getUserid())
            .append(getAliasid())
            .append(getTimestamp())
            .append(getRandom())
            .append(getInfo())
            .toHashCode();
    }

}
