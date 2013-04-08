package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IUSERUSERHDR"
 *     
*/
public class Iuseruserhdr implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private String password;

    /** persistent field */
    private int deptid;

    /** persistent field */
    private int flags;

    /** persistent field */
    private int stat;

    /** persistent field */
    private int numbadcnts;

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

    /** nullable persistent field */
    private Integer pwdlastupdts;

    /** nullable persistent field */
    private String pwdmbc;

    /** nullable persistent field */
    private String pwdvpcheck;

    /** full constructor */
    public Iuseruserhdr(Integer id, String name, String password, int deptid, int flags, int stat, int numbadcnts, String remarks, int crtrid, Date crtndate, Integer updrid, Date upddate, Integer pwdlastupdts, String pwdmbc, String pwdvpcheck) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.deptid = deptid;
        this.flags = flags;
        this.stat = stat;
        this.numbadcnts = numbadcnts;
        this.remarks = remarks;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
        this.pwdlastupdts = pwdlastupdts;
        this.pwdmbc = pwdmbc;
        this.pwdvpcheck = pwdvpcheck;
    }

    /** default constructor */
    public Iuseruserhdr() {
    }

    /** minimal constructor */
    public Iuseruserhdr(Integer id, String name, String password, int deptid, int flags, int stat, int numbadcnts, int crtrid, Date crtndate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.deptid = deptid;
        this.flags = flags;
        this.stat = stat;
        this.numbadcnts = numbadcnts;
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
     *             column="PASSWORD"
     *             length="68"
     *             not-null="true"
     *         
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     *            @hibernate.property
     *             column="DEPTID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getDeptid() {
        return this.deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
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
     *             column="NUMBADCNTS"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getNumbadcnts() {
        return this.numbadcnts;
    }

    public void setNumbadcnts(int numbadcnts) {
        this.numbadcnts = numbadcnts;
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

    /** 
     *            @hibernate.property
     *             column="PWDLASTUPDTS"
     *             length="20"
     *         
     */
    public Integer getPwdlastupdts() {
        return this.pwdlastupdts;
    }

    public void setPwdlastupdts(Integer pwdlastupdts) {
        this.pwdlastupdts = pwdlastupdts;
    }

    /** 
     *            @hibernate.property
     *             column="PWDMBC"
     *             length="1"
     *         
     */
    public String getPwdmbc() {
        return this.pwdmbc;
    }

    public void setPwdmbc(String pwdmbc) {
        this.pwdmbc = pwdmbc;
    }

    /** 
     *            @hibernate.property
     *             column="PWDVPCHECK"
     *             length="1"
     *         
     */
    public String getPwdvpcheck() {
        return this.pwdvpcheck;
    }

    public void setPwdvpcheck(String pwdvpcheck) {
        this.pwdvpcheck = pwdvpcheck;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Iuseruserhdr) ) return false;
        Iuseruserhdr castOther = (Iuseruserhdr) other;
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
