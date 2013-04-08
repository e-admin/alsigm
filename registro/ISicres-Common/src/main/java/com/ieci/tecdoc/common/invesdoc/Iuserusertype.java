package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERUSERTYPE"
 *     
*/
public class Iuserusertype implements Serializable {

    /** identifier field */
    private int userid;

    /** identifier field */
    private int prodid;

    /** identifier field */
    private int type;

    /** full constructor */
    public Iuserusertype(int userid, int prodid, int type) {
        this.userid = userid;
        this.prodid = prodid;
        this.type = type;
    }

    /** default constructor */
    public Iuserusertype() {
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("userid", getUserid())
            .append("prodid", getProdid())
            .append("type", getType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserusertype) ) return false;
        Iuserusertype castOther = (Iuserusertype) other;
        return new EqualsBuilder()
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getProdid(), castOther.getProdid())
            .append(this.getType(), castOther.getType())
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
            .append(getUserid())
            .append(getProdid())
            .append(getType())
            .toHashCode();
    }

}
