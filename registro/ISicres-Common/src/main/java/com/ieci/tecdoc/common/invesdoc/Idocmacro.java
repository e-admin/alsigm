package com.ieci.tecdoc.common.invesdoc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="IDOCMACRO"
 *     
*/
public class Idocmacro implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String name;

    /** identifier field */
    private int type;

    /** identifier field */
    private int language;

    /** identifier field */
    private String text;

    /** identifier field */
    private String remarks;

    /** identifier field */
    private int crtrid;

    /** identifier field */
    private Date crtndate;

    /** identifier field */
    private Integer updrid;

    /** identifier field */
    private Date upddate;

    /** full constructor */
    public Idocmacro(int id, String name, int type, int language, String text, String remarks, int crtrid, Date crtndate, Integer updrid, Date upddate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.language = language;
        this.text = text;
        this.remarks = remarks;
        this.crtrid = crtrid;
        this.crtndate = crtndate;
        this.updrid = updrid;
        this.upddate = upddate;
    }

    /** default constructor */
    public Idocmacro() {
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
     *                 column="TYPE"
     *                 length="10"
     *             
     */
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /** 
     *                @hibernate.property
     *                 column="LANGUAGE"
     *                 length="10"
     *             
     */
    public int getLanguage() {
        return this.language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    /** 
     *                @hibernate.property
     *                 column="TEXT"
     *                 length="0"
     *             
     */
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
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

    /** 
     *                @hibernate.property
     *                 column="CRTRID"
     *                 length="10"
     *             
     */
    public int getCrtrid() {
        return this.crtrid;
    }

    public void setCrtrid(int crtrid) {
        this.crtrid = crtrid;
    }

    /** 
     *                @hibernate.property
     *                 column="CRTNDATE"
     *                 length="7"
     *             
     */
    public Date getCrtndate() {
        return this.crtndate;
    }

    public void setCrtndate(Date crtndate) {
        this.crtndate = crtndate;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDRID"
     *                 length="10"
     *             
     */
    public Integer getUpdrid() {
        return this.updrid;
    }

    public void setUpdrid(Integer updrid) {
        this.updrid = updrid;
    }

    /** 
     *                @hibernate.property
     *                 column="UPDDATE"
     *                 length="7"
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
            .append("name", getName())
            .append("type", getType())
            .append("language", getLanguage())
            .append("text", getText())
            .append("remarks", getRemarks())
            .append("crtrid", getCrtrid())
            .append("crtndate", getCrtndate())
            .append("updrid", getUpdrid())
            .append("upddate", getUpddate())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idocmacro) ) return false;
        Idocmacro castOther = (Idocmacro) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getName(), castOther.getName())
            .append(this.getType(), castOther.getType())
            .append(this.getLanguage(), castOther.getLanguage())
            .append(this.getText(), castOther.getText())
            .append(this.getRemarks(), castOther.getRemarks())
            .append(this.getCrtrid(), castOther.getCrtrid())
            .append(this.getCrtndate(), castOther.getCrtndate())
            .append(this.getUpdrid(), castOther.getUpdrid())
            .append(this.getUpddate(), castOther.getUpddate())
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
            .append(getType())
            .append(getLanguage())
            .append(getText())
            .append(getRemarks())
            .append(getCrtrid())
            .append(getCrtndate())
            .append(getUpdrid())
            .append(getUpddate())
            .toHashCode();
    }

}
