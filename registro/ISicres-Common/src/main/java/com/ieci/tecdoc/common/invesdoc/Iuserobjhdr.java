package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSEROBJHDR"
 *     
*/
public class Iuserobjhdr implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int prodid;

    /** identifier field */
    private int type;

    /** identifier field */
    private int extid1;

    /** identifier field */
    private int extid2;

    /** identifier field */
    private int extid3;

    /** identifier field */
    private int ownertype;

    /** identifier field */
    private int ownerid;

    /** identifier field */
    private int crtrid;

    /** identifier field */
    private Date crtndate;

    /** identifier field */
    private Integer updrid;

    /** identifier field */
    private Date upddate;

    /** full constructor */
    public Iuserobjhdr(int id, int prodid, int type, int extid1, int extid2, int extid3, int ownertype, int ownerid, int crtrid, Date crtndate, Integer updrid, Date upddate) {
        this.id = id;
        this.prodid = prodid;
        this.type = type;
        this.extid1 = extid1;
        this.extid2 = extid2;
        this.extid3 = extid3;
        this.ownertype = ownertype;
        this.ownerid = ownerid;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Iuserobjhdr() {
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
     *                 column="EXTID1"
     *                 length="10"
     *             
     */
    public int getExtid1() {
        return this.extid1;
    }

    public void setExtid1(int extid1) {
        this.extid1 = extid1;
    }

    /** 
     *                @hibernate.property
     *                 column="EXTID2"
     *                 length="10"
     *             
     */
    public int getExtid2() {
        return this.extid2;
    }

    public void setExtid2(int extid2) {
        this.extid2 = extid2;
    }

    /** 
     *                @hibernate.property
     *                 column="EXTID3"
     *                 length="10"
     *             
     */
    public int getExtid3() {
        return this.extid3;
    }

    public void setExtid3(int extid3) {
        this.extid3 = extid3;
    }

    /** 
     *                @hibernate.property
     *                 column="OWNERTYPE"
     *                 length="10"
     *             
     */
    public int getOwnertype() {
        return this.ownertype;
    }

    public void setOwnertype(int ownertype) {
        this.ownertype = ownertype;
    }

    /** 
     *                @hibernate.property
     *                 column="OWNERID"
     *                 length="10"
     *             
     */
    public int getOwnerid() {
        return this.ownerid;
    }

    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("prodid", getProdid())
            .append("type", getType())
            .append("extid1", getExtid1())
            .append("extid2", getExtid2())
            .append("extid3", getExtid3())
            .append("ownertype", getOwnertype())
            .append("ownerid", getOwnerid())
            .append("crtrid", getCrtrid())
            .append("crtndate", getCrtndate())
            .append("updrid", getUpdrid())
            .append("upddate", getUpddate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserobjhdr) ) return false;
        Iuserobjhdr castOther = (Iuserobjhdr) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getProdid(), castOther.getProdid())
            .append(this.getType(), castOther.getType())
            .append(this.getExtid1(), castOther.getExtid1())
            .append(this.getExtid2(), castOther.getExtid2())
            .append(this.getExtid3(), castOther.getExtid3())
            .append(this.getOwnertype(), castOther.getOwnertype())
            .append(this.getOwnerid(), castOther.getOwnerid())
            .append(this.getCrtrid(), castOther.getCrtrid())
            .append(this.getCrtndate(), castOther.getCrtndate())
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
            .append(getId())
            .append(getProdid())
            .append(getType())
            .append(getExtid1())
            .append(getExtid2())
            .append(getExtid3())
            .append(getOwnertype())
            .append(getOwnerid())
            .append(getCrtrid())
            .append(getCrtndate())
            .append(getUpdrid())
            .append(getUpddate())
            .toHashCode();
    }

}
