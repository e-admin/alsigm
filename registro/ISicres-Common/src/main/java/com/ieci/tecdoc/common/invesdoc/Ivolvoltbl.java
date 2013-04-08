package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.sql.Blob;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLVOLTBL"
 *     
*/
public class Ivolvoltbl implements Serializable {

    /** identifier field */
    private int locid;

    /** identifier field */
    private int extid1;

    /** identifier field */
    private int extid2;

    /** identifier field */
    private int extid3;

    /** identifier field */
    private int extid4;

    /** identifier field */
    private String fileext;

    /** identifier field */
    private Blob fileval;

    /** full constructor */
    public Ivolvoltbl(int locid, int extid1, int extid2, int extid3, int extid4, String fileext, Blob fileval) {
        this.locid = locid;
        this.extid1 = extid1;
        this.extid2 = extid2;
        this.extid3 = extid3;
        this.extid4 = extid4;
        this.fileext = fileext;
        this.fileval = fileval;
    }

    /** default constructor */
    public Ivolvoltbl() {
    }

    /** 
     *                @hibernate.property
     *                 column="LOCID"
     *                 length="10"
     *             
     */
    public int getLocid() {
        return this.locid;
    }

    public void setLocid(int locid) {
        this.locid = locid;
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
     *                 column="EXTID4"
     *                 length="10"
     *             
     */
    public int getExtid4() {
        return this.extid4;
    }

    public void setExtid4(int extid4) {
        this.extid4 = extid4;
    }

    /** 
     *                @hibernate.property
     *                 column="FILEEXT"
     *                 length="10"
     *             
     */
    public String getFileext() {
        return this.fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    /** 
     *                @hibernate.property
     *                 column="FILEVAL"
     *                 length="0"
     *             
     */
    public Blob getFileval() {
        return this.fileval;
    }

    public void setFileval(Blob fileval) {
        this.fileval = fileval;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("locid", getLocid())
            .append("extid1", getExtid1())
            .append("extid2", getExtid2())
            .append("extid3", getExtid3())
            .append("extid4", getExtid4())
            .append("fileext", getFileext())
            .append("fileval", getFileval())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolvoltbl) ) return false;
        Ivolvoltbl castOther = (Ivolvoltbl) other;
        return new EqualsBuilder()
            .append(this.getLocid(), castOther.getLocid())
            .append(this.getExtid1(), castOther.getExtid1())
            .append(this.getExtid2(), castOther.getExtid2())
            .append(this.getExtid3(), castOther.getExtid3())
            .append(this.getExtid4(), castOther.getExtid4())
            .append(this.getFileext(), castOther.getFileext())
            .append(this.getFileval(), castOther.getFileval())
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
            .append(getLocid())
            .append(getExtid1())
            .append(getExtid2())
            .append(getExtid3())
            .append(getExtid4())
            .append(getFileext())
            .append(getFileval())
            .toHashCode();
    }

}
