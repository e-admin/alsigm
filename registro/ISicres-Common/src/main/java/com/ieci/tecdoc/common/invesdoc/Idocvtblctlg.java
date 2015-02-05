package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCVTBLCTLG"
 *     
*/
public class Idocvtblctlg implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private int btblid;

    /** persistent field */
    private int type;

    /** persistent field */
    private String info;

    /** persistent field */
    private int flags;

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
    public Idocvtblctlg(Integer id, String name, int btblid, int type, String info, int flags, String remarks, int crtrid, Date crtndate, Integer updrid, Date upddate) {
        this.id = id;
        this.name = name;
        this.btblid = btblid;
        this.type = type;
        this.info = info;
        this.flags = flags;
        this.remarks = remarks;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Idocvtblctlg() {
    }

    /** minimal constructor */
    public Idocvtblctlg(Integer id, String name, int btblid, int type, String info, int flags, int crtrid, Date crtndate) {
        this.id = id;
        this.name = name;
        this.btblid = btblid;
        this.type = type;
        this.info = info;
        this.flags = flags;
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
     *             length="64"
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
     *             column="BTBLID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getBtblid() {
        return this.btblid;
    }

    public void setBtblid(int btblid) {
        this.btblid = btblid;
    }

    /** 
     *            @hibernate.property
     *             column="TYPE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 
     *            @hibernate.property
     *             column="INFO"
     *             length="0"        
     */
    public String getInfo() {
        return this.info;
    }
     
    public void setInfo(String info) {
        this.info = info;
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
        if ( !(other instanceof Idocvtblctlg) ) return false;
        Idocvtblctlg castOther = (Idocvtblctlg) other;
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
