package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_OFIC_EU"
 *     
*/
public class ScrOficeu implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String acron;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private Date creationDate;

    /** nullable persistent field */
    private Date disableDate;

    /** nullable persistent field */
    private String stamp;

    /** persistent field */
    private int deptid;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypeofic scrTypeofic;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrgeu scrOrg;

    /** persistent field */
    private Set scrRegorigdocs;

    /** persistent field */
    private Set scrWs;

    /** persistent field */
    private Set scrRelations;

    /** persistent field */
    private Set scrOficadms;

    /** persistent field */
    private Set scrCaofics;

    /** persistent field */
    private Set scrReportofics;

    /** persistent field */
    private Set scrLockrelations;

    /** full constructor */
    public ScrOficeu(Integer id, String code, String acron, String name, Date creationDate, Date disableDate, String stamp, int deptid, com.ieci.tecdoc.common.invesicres.ScrTypeofic scrTypeofic, com.ieci.tecdoc.common.invesicres.ScrOrgeu scrOrg, Set scrRegorigdocs, Set scrWs, Set scrRelations, Set scrOficadms, Set scrCaofics, Set scrReportofics, Set scrLockrelations) {
        this.id = id;
        this.code = code;
        this.acron = acron;
        this.name = name;
        this.creationDate = creationDate;
        this.disableDate = disableDate;
        this.stamp = stamp;
        this.deptid = deptid;
        this.scrTypeofic = scrTypeofic;
        this.scrOrg = scrOrg;
        this.scrRegorigdocs = scrRegorigdocs;
        this.scrWs = scrWs;
        this.scrRelations = scrRelations;
        this.scrOficadms = scrOficadms;
        this.scrCaofics = scrCaofics;
        this.scrReportofics = scrReportofics;
        this.scrLockrelations = scrLockrelations;
    }

    /** default constructor */
    public ScrOficeu() {
    }

    /** minimal constructor */
    public ScrOficeu(Integer id, String code, String acron, String name, int deptid, com.ieci.tecdoc.common.invesicres.ScrTypeofic scrTypeofic, com.ieci.tecdoc.common.invesicres.ScrOrgeu scrOrg, Set scrRegorigdocs, Set scrWs, Set scrRelations, Set scrOficadms, Set scrCaofics, Set scrReportofics, Set scrLockrelations) {
        this.id = id;
        this.code = code;
        this.acron = acron;
        this.name = name;
        this.deptid = deptid;
        this.scrTypeofic = scrTypeofic;
        this.scrOrg = scrOrg;
        this.scrRegorigdocs = scrRegorigdocs;
        this.scrWs = scrWs;
        this.scrRelations = scrRelations;
        this.scrOficadms = scrOficadms;
        this.scrCaofics = scrCaofics;
        this.scrReportofics = scrReportofics;
        this.scrLockrelations = scrLockrelations;
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
     *             column="CODE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 
     *            @hibernate.property
     *             column="ACRON"
     *             length="12"
     *             not-null="true"
     *         
     */
    public String getAcron() {
        return this.acron;
    }

    public void setAcron(String acron) {
        this.acron = acron;
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
     *             column="CREATION_DATE"
     *             length="7"
     *         
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /** 
     *            @hibernate.property
     *             column="DISABLE_DATE"
     *             length="7"
     *         
     */
    public Date getDisableDate() {
        return this.disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    /** 
     *            @hibernate.property
     *             column="STAMP"
     *             length="240"
     *         
     */
    public String getStamp() {
        return this.stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
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
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TYPE"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrTypeofic getScrTypeofic() {
        return this.scrTypeofic;
    }

    public void setScrTypeofic(com.ieci.tecdoc.common.invesicres.ScrTypeofic scrTypeofic) {
        this.scrTypeofic = scrTypeofic;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ID_ORGS"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrgeu getScrOrg() {
        return this.scrOrg;
    }

    public void setScrOrg(com.ieci.tecdoc.common.invesicres.ScrOrgeu scrOrg) {
        this.scrOrg = scrOrg;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDOFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRegorigdoc"
     *         
     */
    public Set getScrRegorigdocs() {
        return this.scrRegorigdocs;
    }

    public void setScrRegorigdocs(Set scrRegorigdocs) {
        this.scrRegorigdocs = scrRegorigdocs;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDOFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrW"
     *         
     */
    public Set getScrWs() {
        return this.scrWs;
    }

    public void setScrWs(Set scrWs) {
        this.scrWs = scrWs;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDOFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRelation"
     *         
     */
    public Set getScrRelations() {
        return this.scrRelations;
    }

    public void setScrRelations(Set scrRelations) {
        this.scrRelations = scrRelations;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDOFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrOficadm"
     *         
     */
    public Set getScrOficadms() {
        return this.scrOficadms;
    }

    public void setScrOficadms(Set scrOficadms) {
        this.scrOficadms = scrOficadms;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="ID_OFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrCaofic"
     *         
     */
    public Set getScrCaofics() {
        return this.scrCaofics;
    }

    public void setScrCaofics(Set scrCaofics) {
        this.scrCaofics = scrCaofics;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="ID_OFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrReportofic"
     *         
     */
    public Set getScrReportofics() {
        return this.scrReportofics;
    }

    public void setScrReportofics(Set scrReportofics) {
        this.scrReportofics = scrReportofics;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDOFIC"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrLockrelation"
     *         
     */
    public Set getScrLockrelations() {
        return this.scrLockrelations;
    }

    public void setScrLockrelations(Set scrLockrelations) {
        this.scrLockrelations = scrLockrelations;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrOficeu) ) return false;
        ScrOficeu castOther = (ScrOficeu) other;
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
