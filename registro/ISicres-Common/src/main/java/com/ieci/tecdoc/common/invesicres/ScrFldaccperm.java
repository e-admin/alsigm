package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_FLDACCPERMS"
 *     
*/
public class ScrFldaccperm implements Serializable {

    /** identifier field */
    private int archivetype;

    /** identifier field */
    private int idfld;

    /** identifier field */
    private int userperms;

    /** identifier field */
    private int adminperms;

    /** identifier field */
    private int suserperms;

    /** full constructor */
    public ScrFldaccperm(int archivetype, int idfld, int userperms, int adminperms, int suserperms) {
        this.archivetype = archivetype;
        this.idfld = idfld;
        this.userperms = userperms;
        this.adminperms = adminperms;
        this.suserperms = suserperms;
    }

    /** default constructor */
    public ScrFldaccperm() {
    }

    /** 
     *                @hibernate.property
     *                 column="ARCHIVETYPE"
     *                 length="10"
     *             
     */
    public int getArchivetype() {
        return this.archivetype;
    }

    public void setArchivetype(int archivetype) {
        this.archivetype = archivetype;
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
     *                 column="USERPERMS"
     *                 length="10"
     *             
     */
    public int getUserperms() {
        return this.userperms;
    }

    public void setUserperms(int userperms) {
        this.userperms = userperms;
    }

    /** 
     *                @hibernate.property
     *                 column="ADMINPERMS"
     *                 length="10"
     *             
     */
    public int getAdminperms() {
        return this.adminperms;
    }

    public void setAdminperms(int adminperms) {
        this.adminperms = adminperms;
    }

    /** 
     *                @hibernate.property
     *                 column="SUSERPERMS"
     *                 length="10"
     *             
     */
    public int getSuserperms() {
        return this.suserperms;
    }

    public void setSuserperms(int suserperms) {
        this.suserperms = suserperms;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("archivetype", getArchivetype())
            .append("idfld", getIdfld())
            .append("userperms", getUserperms())
            .append("adminperms", getAdminperms())
            .append("suserperms", getSuserperms())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrFldaccperm) ) return false;
        ScrFldaccperm castOther = (ScrFldaccperm) other;
        return new EqualsBuilder()
            .append(this.getArchivetype(), castOther.getArchivetype())
            .append(this.getIdfld(), castOther.getIdfld())
            .append(this.getUserperms(), castOther.getUserperms())
            .append(this.getAdminperms(), castOther.getAdminperms())
            .append(this.getSuserperms(), castOther.getSuserperms())
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
            .append(getArchivetype())
            .append(getIdfld())
            .append(getUserperms())
            .append(getAdminperms())
            .append(getSuserperms())
            .toHashCode();
    }

}
