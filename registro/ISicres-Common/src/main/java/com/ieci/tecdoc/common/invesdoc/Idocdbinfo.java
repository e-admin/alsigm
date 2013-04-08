package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCDBINFO"
 *     
*/
public class Idocdbinfo implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private String graldbname;

    /** identifier field */
    private String graldbuser;

    /** identifier field */
    private String graldbpassword;

    /** identifier field */
    private String misc;

    /** full constructor */
    public Idocdbinfo(int id, String name, String graldbname, String graldbuser, String graldbpassword, String misc) {
        this.id = id;
        this.name = name;
        this.graldbname = graldbname;
        this.graldbuser = graldbuser;
        this.graldbpassword = graldbpassword;
        this.misc = misc;
    }

    /** default constructor */
    public Idocdbinfo() {
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
     *                 column="NAME"
     *                 length="32"
     *             
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *                @hibernate.property
     *                 column="GRALDBNAME"
     *                 length="32"
     *             
     */
    public String getGraldbname() {
        return this.graldbname;
    }

    public void setGraldbname(String graldbname) {
        this.graldbname = graldbname;
    }

    /** 
     *                @hibernate.property
     *                 column="GRALDBUSER"
     *                 length="32"
     *             
     */
    public String getGraldbuser() {
        return this.graldbuser;
    }

    public void setGraldbuser(String graldbuser) {
        this.graldbuser = graldbuser;
    }

    /** 
     *                @hibernate.property
     *                 column="GRALDBPASSWORD"
     *                 length="34"
     *             
     */
    public String getGraldbpassword() {
        return this.graldbpassword;
    }

    public void setGraldbpassword(String graldbpassword) {
        this.graldbpassword = graldbpassword;
    }

    /** 
     *                @hibernate.property
     *                 column="MISC"
     *                 length="0"
     *             
     */
    public String getMisc() {
        return this.misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("graldbname", getGraldbname())
            .append("graldbuser", getGraldbuser())
            .append("graldbpassword", getGraldbpassword())
            .append("misc", getMisc())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocdbinfo) ) return false;
        Idocdbinfo castOther = (Idocdbinfo) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getGraldbname(), castOther.getGraldbname())
            .append(this.getGraldbuser(), castOther.getGraldbuser())
            .append(this.getGraldbpassword(), castOther.getGraldbpassword())
            .append(this.getMisc(), castOther.getMisc())
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
            .append(getName())
            .append(getGraldbname())
            .append(getGraldbuser())
            .append(getGraldbpassword())
            .append(getMisc())
            .toHashCode();
    }

}
