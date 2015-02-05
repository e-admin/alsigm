package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_FLDPERMUSER"
 *     
*/
public class ScrFldpermuser implements Serializable {

    /** identifier field */
    private int idarchive;

    /** identifier field */
    private int idfld;

    /** identifier field */
    private int iduser;

    /** identifier field */
    private int perms;

    /** full constructor */
    public ScrFldpermuser(int idarchive, int idfld, int iduser, int perms) {
        this.idarchive = idarchive;
        this.idfld = idfld;
        this.iduser = iduser;
        this.perms = perms;
    }

    /** default constructor */
    public ScrFldpermuser() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDARCHIVE"
     *                 length="10"
     *             
     */
    public int getIdarchive() {
        return this.idarchive;
    }

    public void setIdarchive(int idarchive) {
        this.idarchive = idarchive;
    }

    /** 
     *                @hibernate.property
     *                 column="IDFLD"
     *                 length="10"
     *             
     */
    public int getIdfld() {
        return this.idfld;
    }

    public void setIdfld(int idfld) {
        this.idfld = idfld;
    }

    /** 
     *                @hibernate.property
     *                 column="IDUSER"
     *                 length="10"
     *             
     */
    public int getIduser() {
        return this.iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /** 
     *                @hibernate.property
     *                 column="PERMS"
     *                 length="10"
     *             
     */
    public int getPerms() {
        return this.perms;
    }

    public void setPerms(int perms) {
        this.perms = perms;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idarchive", getIdarchive())
            .append("idfld", getIdfld())
            .append("iduser", getIduser())
            .append("perms", getPerms())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrFldpermuser) ) return false;
        ScrFldpermuser castOther = (ScrFldpermuser) other;
        return new EqualsBuilder()
            .append(this.getIdarchive(), castOther.getIdarchive())
            .append(this.getIdfld(), castOther.getIdfld())
            .append(this.getIduser(), castOther.getIduser())
            .append(this.getPerms(), castOther.getPerms())
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
            .append(getIdarchive())
            .append(getIdfld())
            .append(getIduser())
            .append(getPerms())
            .toHashCode();
    }

}
