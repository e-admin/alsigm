package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_USERFILTER"
 *     
*/
public class ScrUserfilter implements Serializable {

    /** identifier field */
    private int idarch;

    /** identifier field */
    private int iduser;

    /** identifier field */
    private String filterdef;

    /** identifier field */
    private int typeObj;

    /** full constructor */
    public ScrUserfilter(int idarch, int iduser, String filterdef, int typeObj) {
        this.idarch = idarch;
        this.iduser = iduser;
        this.filterdef = filterdef;
        this.typeObj = typeObj;
    }

    /** default constructor */
    public ScrUserfilter() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDARCH"
     *                 length="10"
     *             
     */
    public int getIdarch() {
        return this.idarch;
    }

    public void setIdarch(int idarch) {
        this.idarch = idarch;
    }

    /** 
     *                @hibernate.property
     *                 column="IDUSER"
     *                 length="10"
     *             
     */
    public int getIduser() {
        return this.iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /** 
     *                @hibernate.property
     *                 column="FILTERDEF"
     *                 length="0"
     *             
     */
    public String getFilterdef() {
        return this.filterdef;
    }

    public void setFilterdef(String filterdef) {
        this.filterdef = filterdef;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_OBJ"
     *                 length="10"
     *             
     */
    public int getTypeObj() {
        return this.typeObj;
    }

    public void setTypeObj(int typeObj) {
        this.typeObj = typeObj;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idarch", getIdarch())
            .append("iduser", getIduser())
            .append("filterdef", getFilterdef())
            .append("typeObj", getTypeObj())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrUserfilter) ) return false;
        ScrUserfilter castOther = (ScrUserfilter) other;
        return new EqualsBuilder()
            .append(this.getIdarch(), castOther.getIdarch())
            .append(this.getIduser(), castOther.getIduser())
            .append(this.getFilterdef(), castOther.getFilterdef())
            .append(this.getTypeObj(), castOther.getTypeObj())
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
            .append(getIdarch())
            .append(getIduser())
            .append(getFilterdef())
            .append(getTypeObj())
            .toHashCode();
    }

}
