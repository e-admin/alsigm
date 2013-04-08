package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_TRANSLATEDFMT"
 *     
*/
public class ScrTranslatedfmt implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int langid;

    /** identifier field */
    private String spaText;

    /** identifier field */
    private String transText;

    /** full constructor */
    public ScrTranslatedfmt(int id, int langid, String spaText, String transText) {
        this.id = id;
        this.langid = langid;
        this.spaText = spaText;
        this.transText = transText;
    }

    /** default constructor */
    public ScrTranslatedfmt() {
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
     *                 column="LANGID"
     *                 length="10"
     *             
     */
    public int getLangid() {
        return this.langid;
    }

    public void setLangid(int langid) {
        this.langid = langid;
    }

    /** 
     *                @hibernate.property
     *                 column="SPA_TEXT"
     *                 length="254"
     *             
     */
    public String getSpaText() {
        return this.spaText;
    }

    public void setSpaText(String spaText) {
        this.spaText = spaText;
    }

    /** 
     *                @hibernate.property
     *                 column="TRANS_TEXT"
     *                 length="254"
     *             
     */
    public String getTransText() {
        return this.transText;
    }

    public void setTransText(String transText) {
        this.transText = transText;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("langid", getLangid())
            .append("spaText", getSpaText())
            .append("transText", getTransText())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrTranslatedfmt) ) return false;
        ScrTranslatedfmt castOther = (ScrTranslatedfmt) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getLangid(), castOther.getLangid())
            .append(this.getSpaText(), castOther.getSpaText())
            .append(this.getTransText(), castOther.getTransText())
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
            .append(getLangid())
            .append(getSpaText())
            .append(getTransText())
            .toHashCode();
    }

}
