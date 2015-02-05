package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCARCHHDR"
 *     
*/
public class Idocarchhdr implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private String tblprefix;

    /** persistent field */
    private int type;

    /** persistent field */
    private int flags;

    /** nullable persistent field */
    private String remarks;

    /** persistent field */
    private int accesstype;

    /** persistent field */
    private int acsid;

    /** persistent field */
    private int crtrid;

    /** persistent field */
    private Date crtndate;

    /** nullable persistent field */
    private Integer updrid;

    /** nullable persistent field */
    private Date upddate;

    /** persistent field */
    private Set scrRegstates;

    /** full constructor */
    public Idocarchhdr(Integer id, String name, String tblprefix, int type, int flags, String remarks, int accesstype, int acsid, int crtrid, Date crtndate, Integer updrid, Date upddate, Set scrRegstates) {
        this.id = id;
        this.name = name;
        this.tblprefix = tblprefix;
        this.type = type;
        this.flags = flags;
        this.remarks = remarks;
        this.accesstype = accesstype;
        this.acsid = acsid;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
        this.scrRegstates = scrRegstates;
    }

    /** default constructor */
    public Idocarchhdr() {
    }

    /** minimal constructor */
    public Idocarchhdr(Integer id, String name, String tblprefix, int type, int flags, int accesstype, int acsid, int crtrid, Date crtndate, Set scrRegstates) {
        this.id = id;
        this.name = name;
        this.tblprefix = tblprefix;
        this.type = type;
        this.flags = flags;
        this.accesstype = accesstype;
        this.acsid = acsid;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.scrRegstates = scrRegstates;
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
     *             column="TBLPREFIX"
     *             length="16"
     *             not-null="true"
     *         
     */
    public String getTblprefix() {
        return this.tblprefix;
    }

    public void setTblprefix(String tblprefix) {
        this.tblprefix = tblprefix;
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
     *             column="ACCESSTYPE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getAccesstype() {
        return this.accesstype;
    }

    public void setAccesstype(int accesstype) {
        this.accesstype = accesstype;
    }

    /** 
     *            @hibernate.property
     *             column="ACSID"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getAcsid() {
        return this.acsid;
    }

    public void setAcsid(int acsid) {
        this.acsid = acsid;
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
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDARCHREG"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRegstate"
     *         
     */
    public Set getScrRegstates() {
        return this.scrRegstates;
    }

    public void setScrRegstates(Set scrRegstates) {
        this.scrRegstates = scrRegstates;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocarchhdr) ) return false;
        Idocarchhdr castOther = (Idocarchhdr) other;
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
