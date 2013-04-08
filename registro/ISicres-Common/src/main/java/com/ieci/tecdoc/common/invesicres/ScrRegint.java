package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGINT"
 *     
*/
public class ScrRegint implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int idArch;

    /** persistent field */
    private int idFdr;

    /** persistent field */
    private String name;

    /** persistent field */
    private int idPerson;

    /** persistent field */
    private int ord;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrAddress scrAddress;

    /** full constructor */
    public ScrRegint(Integer id, int idArch, int idFdr, String name, int idPerson, int ord, com.ieci.tecdoc.common.invesicres.ScrAddress scrAddress) {
        this.id = id;
        this.idArch = idArch;
        this.idFdr = idFdr;
        this.name = name;
        this.idPerson = idPerson;
        this.ord = ord;
        this.scrAddress = scrAddress;
    }

    /** default constructor */
    public ScrRegint() {
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
     *             column="ID_ARCH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    /** 
     *            @hibernate.property
     *             column="ID_FDR"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdFdr() {
        return this.idFdr;
    }

    public void setIdFdr(int idFdr) {
        this.idFdr = idFdr;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="80"
     *             not-null="true"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="ID_PERSON"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdPerson() {
        return this.idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /** 
     *            @hibernate.property
     *             column="ORD"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getOrd() {
        return this.ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ID_ADDRESS"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrAddress getScrAddress() {
        return this.scrAddress;
    }

    public void setScrAddress(com.ieci.tecdoc.common.invesicres.ScrAddress scrAddress) {
        this.scrAddress = scrAddress;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegint) ) return false;
        ScrRegint castOther = (ScrRegint) other;
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
