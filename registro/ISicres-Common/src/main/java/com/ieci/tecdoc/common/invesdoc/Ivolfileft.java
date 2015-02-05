package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLFILEFTS"
 *     
*/
public class Ivolfileft implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int extid1;

    /** identifier field */
    private int extid2;

    /** identifier field */
    private int extid3;

    /** identifier field */
    private int extid4;

    /** identifier field */
    private String path;

    /** identifier field */
    private Date timestamp;

    /** full constructor */
    public Ivolfileft(int id, int extid1, int extid2, int extid3, int extid4, String path, Date timestamp) {
        this.id = id;
        this.extid1 = extid1;
        this.extid2 = extid2;
        this.extid3 = extid3;
        this.extid4 = extid4;
        this.path = path;
        this.timestamp = timestamp;
    }

    /** default constructor */
    public Ivolfileft() {
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
     *                 column="PATH"
     *                 length="254"
     *             
     */
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /** 
     *                @hibernate.property
     *                 column="TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("extid1", getExtid1())
            .append("extid2", getExtid2())
            .append("extid3", getExtid3())
            .append("extid4", getExtid4())
            .append("path", getPath())
            .append("timestamp", getTimestamp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolfileft) ) return false;
        Ivolfileft castOther = (Ivolfileft) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getExtid1(), castOther.getExtid1())
            .append(this.getExtid2(), castOther.getExtid2())
            .append(this.getExtid3(), castOther.getExtid3())
            .append(this.getExtid4(), castOther.getExtid4())
            .append(this.getPath(), castOther.getPath())
            .append(this.getTimestamp(), castOther.getTimestamp())
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
            .append(getExtid1())
            .append(getExtid2())
            .append(getExtid3())
            .append(getExtid4())
            .append(getPath())
            .append(getTimestamp())
            .toHashCode();
    }

}
