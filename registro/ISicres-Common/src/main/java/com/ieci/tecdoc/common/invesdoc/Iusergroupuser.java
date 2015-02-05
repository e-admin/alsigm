package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERGROUPUSER"
 *     
*/
public class Iusergroupuser implements Serializable {

    /** identifier field */
    private int groupid;

    /** identifier field */
    private int userid;

    /** full constructor */
    public Iusergroupuser(int groupid, int userid) {
        this.groupid = groupid;
        this.userid = userid;
    }

    /** default constructor */
    public Iusergroupuser() {
    }

    /** 
     *                @hibernate.property
     *                 column="GROUPID"
     *                 length="10"
     *             
     */
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("groupid", getGroupid())
            .append("userid", getUserid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iusergroupuser) ) return false;
        Iusergroupuser castOther = (Iusergroupuser) other;
        return new EqualsBuilder()
            .append(this.getGroupid(), castOther.getGroupid())
            .append(this.getUserid(), castOther.getUserid())
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
            .append(getGroupid())
            .append(getUserid())
            .toHashCode();
    }

}
