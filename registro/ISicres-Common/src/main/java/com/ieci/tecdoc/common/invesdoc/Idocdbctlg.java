package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCDBCTLG"
 *     
*/
public class Idocdbctlg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private String dbname;

    /** identifier field */
    private String dbuser;

    /** identifier field */
    private String dbpassword;

    /** identifier field */
    private String remarks;

    /** full constructor */
    public Idocdbctlg(int id, String name, String dbname, String dbuser, String dbpassword, String remarks) {
        this.id = id;
        this.name = name;
        this.dbname = dbname;
        this.dbuser = dbuser;
        this.dbpassword = dbpassword;
        this.remarks = remarks;
    }

    /** default constructor */
    public Idocdbctlg() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="NAME"
     *                 length="32"
     *             
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *                @hibernate.property
     *                 column="DBNAME"
     *                 length="32"
     *             
     */
    public String getDbname() {
        return this.dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /** 
     *                @hibernate.property
     *                 column="DBUSER"
     *                 length="32"
     *             
     */
    public String getDbuser() {
        return this.dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    /** 
     *                @hibernate.property
     *                 column="DBPASSWORD"
     *                 length="34"
     *             
     */
    public String getDbpassword() {
        return this.dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    /** 
     *                @hibernate.property
     *                 column="REMARKS"
     *                 length="254"
     *             
     */
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("name", getName())
            .append("dbname", getDbname())
            .append("dbuser", getDbuser())
            .append("dbpassword", getDbpassword())
            .append("remarks", getRemarks())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocdbctlg) ) return false;
        Idocdbctlg castOther = (Idocdbctlg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getDbname(), castOther.getDbname())
            .append(this.getDbuser(), castOther.getDbuser())
            .append(this.getDbpassword(), castOther.getDbpassword())
            .append(this.getRemarks(), castOther.getRemarks())
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
            .append(getName())
            .append(getDbname())
            .append(getDbuser())
            .append(getDbpassword())
            .append(getRemarks())
            .toHashCode();
    }

}
