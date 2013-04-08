package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLACTDIR"
 *     
*/
public class Ivolactdir implements Serializable {

    /** identifier field */
    private int volid;

    /** identifier field */
    private int actdir;

    /** identifier field */
    private int numfiles;

    /** full constructor */
    public Ivolactdir(int volid, int actdir, int numfiles) {
        this.volid = volid;
        this.actdir = actdir;
        this.numfiles = numfiles;
    }

    /** default constructor */
    public Ivolactdir() {
    }

    /** 
     *                @hibernate.property
     *                 column="VOLID"
     *                 length="10"
     *             
     */
    public int getVolid() {
        return this.volid;
    }

    public void setVolid(int volid) {
        this.volid = volid;
    }

    /** 
     *                @hibernate.property
     *                 column="ACTDIR"
     *                 length="10"
     *             
     */
    public int getActdir() {
        return this.actdir;
    }

    public void setActdir(int actdir) {
        this.actdir = actdir;
    }

    /** 
     *                @hibernate.property
     *                 column="NUMFILES"
     *                 length="10"
     *             
     */
    public int getNumfiles() {
        return this.numfiles;
    }

    public void setNumfiles(int numfiles) {
        this.numfiles = numfiles;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("volid", getVolid())
            .append("actdir", getActdir())
            .append("numfiles", getNumfiles())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolactdir) ) return false;
        Ivolactdir castOther = (Ivolactdir) other;
        return new EqualsBuilder()
            .append(this.getVolid(), castOther.getVolid())
            .append(this.getActdir(), castOther.getActdir())
            .append(this.getNumfiles(), castOther.getNumfiles())
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
            .append(getVolid())
            .append(getActdir())
            .append(getNumfiles())
            .toHashCode();
    }

}
