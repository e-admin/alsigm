package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLFILEHDR"
 *     
*/
public class Ivolfilehdr implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int volid;

    /** persistent field */
    private String loc;

    /** persistent field */
    private int extid1;

    /** persistent field */
    private int extid2;

    /** persistent field */
    private int extid3;

    /** persistent field */
    private int flags;

    /** persistent field */
    private int stat;

    /** nullable persistent field */
    private Date timestamp;

    /** persistent field */
    private int filesize;

    /** full constructor */
    public Ivolfilehdr(Integer id, int volid, String loc, int extid1, int extid2, int extid3, int flags, int stat, Date timestamp, int filesize) {
        this.id = id;
        this.volid = volid;
        this.loc = loc;
        this.extid1 = extid1;
        this.extid2 = extid2;
        this.extid3 = extid3;
        this.flags = flags;
        this.stat = stat;
        this.timestamp = timestamp;
        this.filesize = filesize;
    }

    /** default constructor */
    public Ivolfilehdr() {
    }

    /** minimal constructor */
    public Ivolfilehdr(Integer id, int volid, String loc, int extid1, int extid2, int extid3, int flags, int stat, int filesize) {
        this.id = id;
        this.volid = volid;
        this.loc = loc;
        this.extid1 = extid1;
        this.extid2 = extid2;
        this.extid3 = extid3;
        this.flags = flags;
        this.stat = stat;
        this.filesize = filesize;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="ID"
     *         
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     *            @hibernate.property
     *             column="VOLID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getVolid() {
        return this.volid;
    }

    public void setVolid(int volid) {
        this.volid = volid;
    }

    /** 
     *            @hibernate.property
     *             column="LOC"
     *             length="254"
     *             not-null="true"
     *         
     */
    public String getLoc() {
        return this.loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    /** 
     *            @hibernate.property
     *             column="EXTID1"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getExtid1() {
        return this.extid1;
    }

    public void setExtid1(int extid1) {
        this.extid1 = extid1;
    }

    /** 
     *            @hibernate.property
     *             column="EXTID2"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getExtid2() {
        return this.extid2;
    }

    public void setExtid2(int extid2) {
        this.extid2 = extid2;
    }

    /** 
     *            @hibernate.property
     *             column="EXTID3"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getExtid3() {
        return this.extid3;
    }

    public void setExtid3(int extid3) {
        this.extid3 = extid3;
    }

    /** 
     *            @hibernate.property
     *             column="FLAGS"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    /** 
     *            @hibernate.property
     *             column="STAT"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getStat() {
        return this.stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    /** 
     *            @hibernate.property
     *             column="TIMESTAMP"
     *             length="7"
     *         
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /** 
     *            @hibernate.property
     *             column="FILESIZE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getFilesize() {
        return this.filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolfilehdr) ) return false;
        Ivolfilehdr castOther = (Ivolfilehdr) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
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
            .toHashCode();
    }

}
