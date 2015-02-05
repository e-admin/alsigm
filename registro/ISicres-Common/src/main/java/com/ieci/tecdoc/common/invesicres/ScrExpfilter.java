package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_EXPFILTERS"
 *     
*/
public class ScrExpfilter implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idTask;

    /** identifier field */
    private String filter;

    /** full constructor */
    public ScrExpfilter(int id, int idTask, String filter) {
        this.id = id;
        this.idTask = idTask;
        this.filter = filter;
    }

    /** default constructor */
    public ScrExpfilter() {
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
     *                 column="ID_TASK"
     *                 length="10"
     *             
     */
    public int getIdTask() {
        return this.idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    /** 
     *                @hibernate.property
     *                 column="FILTER"
     *                 length="0"
     *             
     */
    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idTask", getIdTask())
            .append("filter", getFilter())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrExpfilter) ) return false;
        ScrExpfilter castOther = (ScrExpfilter) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdTask(), castOther.getIdTask())
            .append(this.getFilter(), castOther.getFilter())
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
            .append(getIdTask())
            .append(getFilter())
            .toHashCode();
    }

}
