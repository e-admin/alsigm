package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DCTMDOCBASE"
 *     
*/
public class ScrDctmdocbase implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private Date timestamp;

    /** persistent field */
    private String docbase;

    /** persistent field */
    private String username;

    /** persistent field */
    private String password;

    /** persistent field */
    private Set scrDctmcas;

    /** persistent field */
    private Set scrDctmsyncs;

    /** full constructor */
    public ScrDctmdocbase(Integer id, Date timestamp, String docbase, String username, String password, Set scrDctmcas, Set scrDctmsyncs) {
        this.id = id;
        this.timestamp = timestamp;
        this.docbase = docbase;
        this.username = username;
        this.password = password;
        this.scrDctmcas = scrDctmcas;
        this.scrDctmsyncs = scrDctmsyncs;
    }

    /** default constructor */
    public ScrDctmdocbase() {
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
     *             column="TIMESTAMP"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /** 
     *            @hibernate.property
     *             column="DOCBASE"
     *             length="32"
     *             not-null="true"
     *         
     */
    public String getDocbase() {
        return this.docbase;
    }

    public void setDocbase(String docbase) {
        this.docbase = docbase;
    }

    /** 
     *            @hibernate.property
     *             column="USERNAME"
     *             length="32"
     *             not-null="true"
     *         
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     *            @hibernate.property
     *             column="PASSWORD"
     *             length="32"
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
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="DOCBASEID"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrDctmca"
     *         
     */
    public Set getScrDctmcas() {
        return this.scrDctmcas;
    }

    public void setScrDctmcas(Set scrDctmcas) {
        this.scrDctmcas = scrDctmcas;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     * 	       @hibernate.collection-key
     * 	        column="DOCBASEID"
     *            @hibernate.collection-one-to-many
     *             class="com.ieci.tecdoc.common.invesicres.ScrDctmsync"
     *         
     */
    public Set getScrDctmsyncs() {
        return this.scrDctmsyncs;
    }

    public void setScrDctmsyncs(Set scrDctmsyncs) {
        this.scrDctmsyncs = scrDctmsyncs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDctmdocbase) ) return false;
        ScrDctmdocbase castOther = (ScrDctmdocbase) other;
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
