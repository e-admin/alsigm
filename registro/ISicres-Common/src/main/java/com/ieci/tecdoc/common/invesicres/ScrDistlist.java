package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DISTLIST"
 *     
*/
public class ScrDistlist implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idOrgs;

    /** identifier field */
    private int typeDest;

    /** identifier field */
    private int idDest;

    /** full constructor */
    public ScrDistlist(int id, int idOrgs, int typeDest, int idDest) {
        this.id = id;
        this.idOrgs = idOrgs;
        this.typeDest = typeDest;
        this.idDest = idDest;
    }

    /** default constructor */
    public ScrDistlist() {
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
     *                 column="ID_ORGS"
     *                 length="10"
     *             
     */
    public int getIdOrgs() {
        return this.idOrgs;
    }

    public void setIdOrgs(int idOrgs) {
        this.idOrgs = idOrgs;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_DEST"
     *                 length="10"
     *             
     */
    public int getTypeDest() {
        return this.typeDest;
    }

    public void setTypeDest(int typeDest) {
        this.typeDest = typeDest;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_DEST"
     *                 length="10"
     *             
     */
    public int getIdDest() {
        return this.idDest;
    }

    public void setIdDest(int idDest) {
        this.idDest = idDest;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idOrgs", getIdOrgs())
            .append("typeDest", getTypeDest())
            .append("idDest", getIdDest())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDistlist) ) return false;
        ScrDistlist castOther = (ScrDistlist) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdOrgs(), castOther.getIdOrgs())
            .append(this.getTypeDest(), castOther.getTypeDest())
            .append(this.getIdDest(), castOther.getIdDest())
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
            .append(getIdOrgs())
            .append(getTypeDest())
            .append(getIdDest())
            .toHashCode();
    }

}
