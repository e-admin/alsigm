package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CA_EU"
 *     
*/
public class ScrCaeu implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String matter;

    /** nullable persistent field */
    private Integer forEreg;

    /** nullable persistent field */
    private Integer forSreg;

    /** nullable persistent field */
    private Integer allOfics;

    /** persistent field */
    private int idArch;

    /** persistent field */
    private Date creationDate;

    /** nullable persistent field */
    private Date disableDate;

    /** nullable persistent field */
    private Integer enabled;

    /** persistent field */
    private int idOrg;

    /** persistent field */
    private Set scrCaadms;

    /** full constructor */
    public ScrCaeu(Integer id, String code, String matter, Integer forEreg, Integer forSreg, Integer allOfics, int idArch, Date creationDate, Date disableDate, Integer enabled, int idOrg, Set scrCaadms) {
        this.id = id;
        this.code = code;
        this.matter = matter;
        this.forEreg = forEreg;
        this.forSreg = forSreg;
        this.allOfics = allOfics;
        this.idArch = idArch;
        this.creationDate = creationDate;
        this.disableDate = disableDate;
        this.enabled = enabled;
        this.idOrg = idOrg;
        this.scrCaadms = scrCaadms;
    }

    /** default constructor */
    public ScrCaeu() {
    }

    /** minimal constructor */
    public ScrCaeu(Integer id, String code, String matter, int idArch, Date creationDate, int idOrg, Set scrCaadms) {
        this.id = id;
        this.code = code;
        this.matter = matter;
        this.idArch = idArch;
        this.creationDate = creationDate;
        this.idOrg = idOrg;
        this.scrCaadms = scrCaadms;
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
     *             column="MATTER"
     *             length="250"
     *             not-null="true"
     *         
     */
    public String getMatter() {
        return this.matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    /** 
     *            @hibernate.property
     *             column="FOR_EREG"
     *             length="10"
     *         
     */
    public Integer getForEreg() {
        return this.forEreg;
    }

    public void setForEreg(Integer forEreg) {
        this.forEreg = forEreg;
    }

    /** 
     *            @hibernate.property
     *             column="FOR_SREG"
     *             length="10"
     *         
     */
    public Integer getForSreg() {
        return this.forSreg;
    }

    public void setForSreg(Integer forSreg) {
        this.forSreg = forSreg;
    }

    /** 
     *            @hibernate.property
     *             column="ALL_OFICS"
     *             length="10"
     *         
     */
    public Integer getAllOfics() {
        return this.allOfics;
    }

    public void setAllOfics(Integer allOfics) {
        this.allOfics = allOfics;
    }

    /** 
     *            @hibernate.property
     *             column="ID_ARCH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
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
     *         
     */
    public Integer getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /** 
     *            @hibernate.property
     *             column="ID_ORG"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdOrg() {
        return this.idOrg;
    }

    public void setIdOrg(int idOrg) {
        this.idOrg = idOrg;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="IDCA"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrCaadm"
     *         
     */
    public Set getScrCaadms() {
        return this.scrCaadms;
    }

    public void setScrCaadms(Set scrCaadms) {
        this.scrCaadms = scrCaadms;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCaeu) ) return false;
        ScrCaeu castOther = (ScrCaeu) other;
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
