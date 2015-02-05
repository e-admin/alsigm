package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IVOLVOLHDR"
 *     
*/
public class Ivolvolhdr implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private int repid;

    /** persistent field */
    private String info;

    /** persistent field */
    private int itemp;

    /** persistent field */
    private String actsize;

    /** persistent field */
    private int numfiles;

    /** persistent field */
    private int stat;

    /** nullable persistent field */
    private String remarks;

    /** persistent field */
    private int crtrid;

    /** persistent field */
    private Date crtndate;

    /** nullable persistent field */
    private Integer updrid;

    /** nullable persistent field */
    private Date upddate;

    /** full constructor */
    public Ivolvolhdr(Integer id, String name, int repid, String info, int itemp, String actsize, int numfiles, int stat, String remarks, int crtrid, Date crtndate, Integer updrid, Date upddate) {
        this.id = id;
        this.name = name;
        this.repid = repid;
        this.info = info;
        this.itemp = itemp;
        this.actsize = actsize;
        this.numfiles = numfiles;
        this.stat = stat;
        this.remarks = remarks;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Ivolvolhdr() {
    }

    /** minimal constructor */
    public Ivolvolhdr(Integer id, String name, int repid, String info, int itemp, String actsize, int numfiles, int stat, int crtrid, Date crtndate) {
        this.id = id;
        this.name = name;
        this.repid = repid;
        this.info = info;
        this.itemp = itemp;
        this.actsize = actsize;
        this.numfiles = numfiles;
        this.stat = stat;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
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
     *             column="NAME"
     *             length="32"
     *             not-null="true"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="REPID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getRepid() {
        return this.repid;
    }

    public void setRepid(int repid) {
        this.repid = repid;
    }

    /** 
     *            @hibernate.property
     *             column="INFO"
     *             not-null="true"
     *         
     */
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /** 
     *            @hibernate.property
     *             column="ITEMP"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getItemp() {
        return this.itemp;
    }

    public void setItemp(int itemp) {
        this.itemp = itemp;
    }

    /** 
     *            @hibernate.property
     *             column="ACTSIZE"
     *             length="32"
     *             not-null="true"
     *         
     */
    public String getActsize() {
        return this.actsize;
    }

    public void setActsize(String actsize) {
        this.actsize = actsize;
    }

    /** 
     *            @hibernate.property
     *             column="NUMFILES"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getNumfiles() {
        return this.numfiles;
    }

    public void setNumfiles(int numfiles) {
        this.numfiles = numfiles;
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
     *             column="REMARKS"
     *             length="254"
     *         
     */
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** 
     *            @hibernate.property
     *             column="CRTRID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getCrtrid() {
        return this.crtrid;
    }

    public void setCrtrid(int crtrid) {
        this.crtrid = crtrid;
    }

    /** 
     *            @hibernate.property
     *             column="CRTNDATE"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getCrtndate() {
        return this.crtndate;
    }

    public void setCrtndate(Date crtndate) {
        this.crtndate = crtndate;
    }

    /** 
     *            @hibernate.property
     *             column="UPDRID"
     *             length="10"
     *         
     */
    public Integer getUpdrid() {
        return this.updrid;
    }

    public void setUpdrid(Integer updrid) {
        this.updrid = updrid;
    }

    /** 
     *            @hibernate.property
     *             column="UPDDATE"
     *             length="7"
     *         
     */
    public Date getUpddate() {
        return this.upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Ivolvolhdr) ) return false;
        Ivolvolhdr castOther = (Ivolvolhdr) other;
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
