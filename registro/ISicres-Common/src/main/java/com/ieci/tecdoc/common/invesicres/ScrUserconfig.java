package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_USERCONFIG"
 *     
*/
public class ScrUserconfig implements Serializable {

    /** identifier field */
    private Integer userid;

    /** identifier field */
    private String data;
    
    //identificador de la oficina preferente del usuario
    private Integer idoficpref;

    /** full constructor */
    public ScrUserconfig(Integer userid, String data) {
        this.userid = userid;
        this.data = data;
    }

    /** default constructor */
    public ScrUserconfig() {
    }

    /** 
     *                @hibernate.property
     *                 column="USERID"
     *                 length="3"
     *             
     */
    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /** 
     *                @hibernate.property
     *                 column="DATA"
     *                 length="0"
     *             
     */
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userid", getUserid())
            .append("data", getData())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrUserconfig) ) return false;
        ScrUserconfig castOther = (ScrUserconfig) other;
        return new EqualsBuilder()
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getData(), castOther.getData())
            .isEquals();
    }

    /** 
     *     @hibernate.property
     *     column="IDOFICPREF"
     *     length="3"
     *             
     */
    public Integer getIdoficpref() {
    	return idoficpref;
    }

    public void setIdoficpref(Integer idoficpref) {
    	this.idoficpref = idoficpref;
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
            .append(getUserid())
            .append(getData())
            .toHashCode();
    }
}
