package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_ORGS_GL"
 *     
*/
public class ScrOrggl implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String code;

    /** nullable persistent field */
    private Integer idFather;

    /** persistent field */
    private String acron;

    /** persistent field */
    private String name;

    /** persistent field */
    private Date creationDate;

    /** nullable persistent field */
    private Date disableDate;

    /** persistent field */
    private int enabled;

    /** nullable persistent field */
    private String cif;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypeadmgl scrTypeadm;

    /** persistent field */
    private Set scrSendmsgsBySender;

    /** persistent field */
    private Set scrSendmsgsByDestination;

    /** persistent field */
    private Set scrRecvmsgsBySender;

    /** persistent field */
    private Set scrRecvmsgsByDestination;

    /** persistent field */
    private Set scrRecvdistregs;

    /** persistent field */
    private Set scrRegorigdocs;

    /** persistent field */
    private Set scrOfics;

    /** persistent field */
    private Set scrSendregs;

    /** persistent field */
    private Set scrRelations;

    /** full constructor */
    public ScrOrggl(Integer id, String code, Integer idFather, String acron, String name, Date creationDate, Date disableDate, int enabled, String cif, com.ieci.tecdoc.common.invesicres.ScrTypeadmgl scrTypeadm, Set scrSendmsgsBySender, Set scrSendmsgsByDestination, Set scrRecvmsgsBySender, Set scrRecvmsgsByDestination, Set scrRecvdistregs, Set scrRegorigdocs, Set scrOfics, Set scrSendregs, Set scrRelations) {
        this.id = id;
        this.code = code;
        this.idFather = idFather;
        this.acron = acron;
        this.name = name;
        this.creationDate = creationDate;
        this.disableDate = disableDate;
        this.enabled = enabled;
        this.cif = cif;
        this.scrTypeadm = scrTypeadm;
        this.scrSendmsgsBySender = scrSendmsgsBySender;
        this.scrSendmsgsByDestination = scrSendmsgsByDestination;
        this.scrRecvmsgsBySender = scrRecvmsgsBySender;
        this.scrRecvmsgsByDestination = scrRecvmsgsByDestination;
        this.scrRecvdistregs = scrRecvdistregs;
        this.scrRegorigdocs = scrRegorigdocs;
        this.scrOfics = scrOfics;
        this.scrSendregs = scrSendregs;
        this.scrRelations = scrRelations;
    }

    /** default constructor */
    public ScrOrggl() {
    }

    /** minimal constructor */
    public ScrOrggl(Integer id, String code, String acron, String name, Date creationDate, int enabled, com.ieci.tecdoc.common.invesicres.ScrTypeadmgl scrTypeadm, Set scrSendmsgsBySender, Set scrSendmsgsByDestination, Set scrRecvmsgsBySender, Set scrRecvmsgsByDestination, Set scrRecvdistregs, Set scrRegorigdocs, Set scrOfics, Set scrSendregs, Set scrRelations) {
        this.id = id;
        this.code = code;
        this.acron = acron;
        this.name = name;
        this.creationDate = creationDate;
        this.enabled = enabled;
        this.scrTypeadm = scrTypeadm;
        this.scrSendmsgsBySender = scrSendmsgsBySender;
        this.scrSendmsgsByDestination = scrSendmsgsByDestination;
        this.scrRecvmsgsBySender = scrRecvmsgsBySender;
        this.scrRecvmsgsByDestination = scrRecvmsgsByDestination;
        this.scrRecvdistregs = scrRecvdistregs;
        this.scrRegorigdocs = scrRegorigdocs;
        this.scrOfics = scrOfics;
        this.scrSendregs = scrSendregs;
        this.scrRelations = scrRelations;
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
     *             length="16"
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
     *             column="ID_FATHER"
     *             length="10"
     *         
     */
    public Integer getIdFather() {
        return this.idFather;
    }

    public void setIdFather(Integer idFather) {
        this.idFather = idFather;
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
     *             length="250"
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
     *             not-null="true"
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
     *             column="ENABLED"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    /** 
     *            @hibernate.property
     *             column="CIF"
     *             length="15"
     *         
     */
    public String getCif() {
        return this.cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TYPE"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrTypeadmgl getScrTypeadm() {
        return this.scrTypeadm;
    }

    public void setScrTypeadm(com.ieci.tecdoc.common.invesicres.ScrTypeadmgl scrTypeadm) {
        this.scrTypeadm = scrTypeadm;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="SENDER"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrSendmsg"
     *         
     */
    public Set getScrSendmsgsBySender() {
        return this.scrSendmsgsBySender;
    }

    public void setScrSendmsgsBySender(Set scrSendmsgsBySender) {
        this.scrSendmsgsBySender = scrSendmsgsBySender;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="DESTINATION"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrSendmsg"
     *         
     */
    public Set getScrSendmsgsByDestination() {
        return this.scrSendmsgsByDestination;
    }

    public void setScrSendmsgsByDestination(Set scrSendmsgsByDestination) {
        this.scrSendmsgsByDestination = scrSendmsgsByDestination;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="SENDER"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRecvmsg"
     *         
     */
    public Set getScrRecvmsgsBySender() {
        return this.scrRecvmsgsBySender;
    }

    public void setScrRecvmsgsBySender(Set scrRecvmsgsBySender) {
        this.scrRecvmsgsBySender = scrRecvmsgsBySender;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="DESTINATION"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRecvmsg"
     *         
     */
    public Set getScrRecvmsgsByDestination() {
        return this.scrRecvmsgsByDestination;
    }

    public void setScrRecvmsgsByDestination(Set scrRecvmsgsByDestination) {
        this.scrRecvmsgsByDestination = scrRecvmsgsByDestination;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDORGS"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrRecvdistreg"
     *         
     */
    public Set getScrRecvdistregs() {
        return this.scrRecvdistregs;
    }

    public void setScrRecvdistregs(Set scrRecvdistregs) {
        this.scrRecvdistregs = scrRecvdistregs;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="DESTINATION"
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
     * 	        column="ID_ORGS"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrOfic"
     *         
     */
    public Set getScrOfics() {
        return this.scrOfics;
    }

    public void setScrOfics(Set scrOfics) {
        this.scrOfics = scrOfics;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="ID_ENTREG_DEST"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrSendreg"
     *         
     */
    public Set getScrSendregs() {
        return this.scrSendregs;
    }

    public void setScrSendregs(Set scrSendregs) {
        this.scrSendregs = scrSendregs;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDUNIT"
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrOrggl) ) return false;
        ScrOrggl castOther = (ScrOrggl) other;
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
