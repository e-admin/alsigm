package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_RECVDISTREG"
 *     
*/
public class ScrRecvdistreg implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String numreg;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg;

    /** full constructor */
    public ScrRecvdistreg(int id, String numreg, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg) {
        this.id = id;
        this.numreg = numreg;
        this.scrOrg = scrOrg;
    }

    /** default constructor */
    public ScrRecvdistreg() {
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
     *                 column="NUMREG"
     *                 length="20"
     *             
     */
    public String getNumreg() {
        return this.numreg;
    }

    public void setNumreg(String numreg) {
        this.numreg = numreg;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDORGS"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrg getScrOrg() {
        return this.scrOrg;
    }

    public void setScrOrg(com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg) {
        this.scrOrg = scrOrg;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("numreg", getNumreg())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRecvdistreg) ) return false;
        ScrRecvdistreg castOther = (ScrRecvdistreg) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getNumreg(), castOther.getNumreg())
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
            .append(getNumreg())
            .toHashCode();
    }

}
