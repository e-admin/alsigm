package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REPORTS"
 *     
*/
public class ScrReport implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String report;

    /** persistent field */
    private int typeReport;

    /** persistent field */
    private int typeArch;

    /** persistent field */
    private int allArch;

    /** persistent field */
    private int allOfics;

    /** persistent field */
    private int allPerfs;

    /** persistent field */
    private String description;

    /** full constructor */
    public ScrReport(Integer id, String report, int typeReport, int typeArch, int allArch, int allOfics, int allPerfs, String description) {
        this.id = id;
        this.report = report;
        this.typeReport = typeReport;
        this.typeArch = typeArch;
        this.allArch = allArch;
        this.allOfics = allOfics;
        this.allPerfs = allPerfs;
        this.description = description;
    }

    /** default constructor */
    public ScrReport() {
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
     *             column="REPORT"
     *             length="250"
     *             not-null="true"
     *         
     */
    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    /** 
     *            @hibernate.property
     *             column="TYPE_REPORT"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getTypeReport() {
        return this.typeReport;
    }

    public void setTypeReport(int typeReport) {
        this.typeReport = typeReport;
    }

    /** 
     *            @hibernate.property
     *             column="TYPE_ARCH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getTypeArch() {
        return this.typeArch;
    }

    public void setTypeArch(int typeArch) {
        this.typeArch = typeArch;
    }

    /** 
     *            @hibernate.property
     *             column="ALL_ARCH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getAllArch() {
        return this.allArch;
    }

    public void setAllArch(int allArch) {
        this.allArch = allArch;
    }

    /** 
     *            @hibernate.property
     *             column="ALL_OFICS"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getAllOfics() {
        return this.allOfics;
    }

    public void setAllOfics(int allOfics) {
        this.allOfics = allOfics;
    }

    /** 
     *            @hibernate.property
     *             column="ALL_PERFS"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getAllPerfs() {
        return this.allPerfs;
    }

    public void setAllPerfs(int allPerfs) {
        this.allPerfs = allPerfs;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="250"
     *             not-null="true"
     *         
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrReport) ) return false;
        ScrReport castOther = (ScrReport) other;
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
