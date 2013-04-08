package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_EXPCNTS"
 *     
*/
public class ScrExpcnt implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idTask;

    /** identifier field */
    private String username;

    /** identifier field */
    private String password;

    /** full constructor */
    public ScrExpcnt(int id, int idTask, String username, String password) {
        this.id = id;
        this.idTask = idTask;
        this.username = username;
        this.password = password;
    }

    /** default constructor */
    public ScrExpcnt() {
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
     *                 column="USERNAME"
     *                 length="32"
     *             
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     *                @hibernate.property
     *                 column="PASSWORD"
     *                 length="34"
     *             
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idTask", getIdTask())
            .append("username", getUsername())
            .append("password", getPassword())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrExpcnt) ) return false;
        ScrExpcnt castOther = (ScrExpcnt) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdTask(), castOther.getIdTask())
            .append(this.getUsername(), castOther.getUsername())
            .append(this.getPassword(), castOther.getPassword())
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
            .append(getUsername())
            .append(getPassword())
            .toHashCode();
    }

}
