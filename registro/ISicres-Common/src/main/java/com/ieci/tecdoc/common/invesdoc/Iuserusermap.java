package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERUSERMAP"
 *     
*/
public class Iuserusermap implements Serializable {

    /** identifier field */
    private int srcdbid;

    /** identifier field */
    private int srcuserid;

    /** identifier field */
    private int dstdbid;

    /** identifier field */
    private int dstuserid;

    /** full constructor */
    public Iuserusermap(int srcdbid, int srcuserid, int dstdbid, int dstuserid) {
        this.srcdbid = srcdbid;
        this.srcuserid = srcuserid;
        this.dstdbid = dstdbid;
        this.dstuserid = dstuserid;
    }

    /** default constructor */
    public Iuserusermap() {
    }

    /** 
     *                @hibernate.property
     *                 column="SRCDBID"
     *                 length="10"
     *             
     */
    public int getSrcdbid() {
        return this.srcdbid;
    }

    public void setSrcdbid(int srcdbid) {
        this.srcdbid = srcdbid;
    }

    /** 
     *                @hibernate.property
     *                 column="SRCUSERID"
     *                 length="10"
     *             
     */
    public int getSrcuserid() {
        return this.srcuserid;
    }

    public void setSrcuserid(int srcuserid) {
        this.srcuserid = srcuserid;
    }

    /** 
     *                @hibernate.property
     *                 column="DSTDBID"
     *                 length="10"
     *             
     */
    public int getDstdbid() {
        return this.dstdbid;
    }

    public void setDstdbid(int dstdbid) {
        this.dstdbid = dstdbid;
    }

    /** 
     *                @hibernate.property
     *                 column="DSTUSERID"
     *                 length="10"
     *             
     */
    public int getDstuserid() {
        return this.dstuserid;
    }

    public void setDstuserid(int dstuserid) {
        this.dstuserid = dstuserid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("srcdbid", getSrcdbid())
            .append("srcuserid", getSrcuserid())
            .append("dstdbid", getDstdbid())
            .append("dstuserid", getDstuserid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuserusermap) ) return false;
        Iuserusermap castOther = (Iuserusermap) other;
        return new EqualsBuilder()
            .append(this.getSrcdbid(), castOther.getSrcdbid())
            .append(this.getSrcuserid(), castOther.getSrcuserid())
            .append(this.getDstdbid(), castOther.getDstdbid())
            .append(this.getDstuserid(), castOther.getDstuserid())
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
            .append(getSrcdbid())
            .append(getSrcuserid())
            .append(getDstdbid())
            .append(getDstuserid())
            .toHashCode();
    }

}
