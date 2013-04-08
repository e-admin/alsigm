package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERLDAPGRPHDR"
 *     
*/
public class Iuserldapgrphdr implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String ldapguid;

    /** identifier field */
    private String ldapfullname;

    /** identifier field */
    private int type;

    /** full constructor */
    public Iuserldapgrphdr(int id, String ldapguid, String ldapfullname, int type) {
        this.id = id;
        this.ldapguid = ldapguid;
        this.ldapfullname = ldapfullname;
        this.type = type;
    }

    /** default constructor */
    public Iuserldapgrphdr() {
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
     *                 column="LDAPGUID"
     *                 length="36"
     *             
     */
    public String getLdapguid() {
        return this.ldapguid;
    }

    public void setLdapguid(String ldapguid) {
        this.ldapguid = ldapguid;
    }

    /** 
     *                @hibernate.property
     *                 column="LDAPFULLNAME"
     *                 length="254"
     *             
     */
    public String getLdapfullname() {
        return this.ldapfullname;
    }

    public void setLdapfullname(String ldapfullname) {
        this.ldapfullname = ldapfullname;
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
            .append("id", getId())
            .append("ldapguid", getLdapguid())
            .append("ldapfullname", getLdapfullname())
            .append("type", getType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserldapgrphdr) ) return false;
        Iuserldapgrphdr castOther = (Iuserldapgrphdr) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getLdapguid(), castOther.getLdapguid())
            .append(this.getLdapfullname(), castOther.getLdapfullname())
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
            .append(getId())
            .append(getLdapguid())
            .append(getLdapfullname())
            .append(getType())
            .toHashCode();
    }

}
