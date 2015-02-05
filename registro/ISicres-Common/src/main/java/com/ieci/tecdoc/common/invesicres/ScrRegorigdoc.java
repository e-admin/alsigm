package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGORIGDOC"
 *     
*/
public class ScrRegorigdoc implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int idarch;

    /** persistent field */
    private int idfdr;

    /** persistent field */
    private Date regdate;

    /** persistent field */
    private String summary;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrTypeproc scrTypeproc;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic;

    /** full constructor */
    public ScrRegorigdoc(Integer id, int idarch, int idfdr, Date regdate, String summary, com.ieci.tecdoc.common.invesicres.ScrTypeproc scrTypeproc, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg, com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.id = id;
        this.idarch = idarch;
        this.idfdr = idfdr;
        this.regdate = regdate;
        this.summary = summary;
        this.scrTypeproc = scrTypeproc;
        this.scrOrg = scrOrg;
        this.scrOfic = scrOfic;
    }

    /** default constructor */
    public ScrRegorigdoc() {
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
     *             column="IDARCH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdarch() {
        return this.idarch;
    }

    public void setIdarch(int idarch) {
        this.idarch = idarch;
    }

    /** 
     *            @hibernate.property
     *             column="IDFDR"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdfdr() {
        return this.idfdr;
    }

    public void setIdfdr(int idfdr) {
        this.idfdr = idfdr;
    }

    /** 
     *            @hibernate.property
     *             column="REGDATE"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getRegdate() {
        return this.regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    /** 
     *            @hibernate.property
     *             column="SUMMARY"
     *             length="100"
     *             not-null="true"
     *         
     */
    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDPROC"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrTypeproc getScrTypeproc() {
        return this.scrTypeproc;
    }

    public void setScrTypeproc(com.ieci.tecdoc.common.invesicres.ScrTypeproc scrTypeproc) {
        this.scrTypeproc = scrTypeproc;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="DESTINATION"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrg getScrOrg() {
        return this.scrOrg;
    }

    public void setScrOrg(com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg) {
        this.scrOrg = scrOrg;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDOFIC"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOfic getScrOfic() {
        return this.scrOfic;
    }

    public void setScrOfic(com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.scrOfic = scrOfic;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegorigdoc) ) return false;
        ScrRegorigdoc castOther = (ScrRegorigdoc) other;
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
