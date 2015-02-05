package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CAADM"
 *     
*/
public class ScrCaadm implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int iduser;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrCa scrCa;

    /** full constructor */
    public ScrCaadm(int id, int iduser, com.ieci.tecdoc.common.invesicres.ScrCa scrCa) {
        this.id = id;
        this.iduser = iduser;
        this.scrCa = scrCa;
    }

    /** default constructor */
    public ScrCaadm() {
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
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDCA"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrCa getScrCa() {
        return this.scrCa;
    }

    public void setScrCa(com.ieci.tecdoc.common.invesicres.ScrCa scrCa) {
        this.scrCa = scrCa;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("iduser", getIduser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCaadm) ) return false;
        ScrCaadm castOther = (ScrCaadm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIduser(), castOther.getIduser())
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
            .append(getIduser())
            .toHashCode();
    }

}
