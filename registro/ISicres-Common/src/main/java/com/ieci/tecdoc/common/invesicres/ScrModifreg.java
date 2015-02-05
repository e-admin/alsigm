package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_MODIFREG"
 *     
*/
public class ScrModifreg implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String usr;

    /** persistent field */
    private Date modifDate;

    /** persistent field */
    private int idFld;

    /** persistent field */
    private String numReg;

    /** persistent field */
    private int idArch;

    /** persistent field */
    private int idOfic;

    /** persistent field */
    private int modifType;

    /** full constructor */
    public ScrModifreg(Integer id, String usr, Date modifDate, int idFld, String numReg, int idArch, int idOfic, int modifType) {
        this.id = id;
        this.usr = usr;
        this.modifDate = modifDate;
        this.idFld = idFld;
        this.numReg = numReg;
        this.idArch = idArch;
        this.idOfic = idOfic;
        this.modifType = modifType;
    }

    /** default constructor */
    public ScrModifreg() {
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
     *             column="USR"
     *             length="32"
     *             not-null="true"
     *         
     */
    public String getUsr() {
        return this.usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    /** 
     *            @hibernate.property
     *             column="MODIF_DATE"
     *             length="7"
     *             not-null="true"
     *         
     */
    public Date getModifDate() {
        return this.modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    /** 
     *            @hibernate.property
     *             column="ID_FLD"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdFld() {
        return this.idFld;
    }

    public void setIdFld(int idFld) {
        this.idFld = idFld;
    }

    /** 
     *            @hibernate.property
     *             column="NUM_REG"
     *             length="20"
     *             not-null="true"
     *         
     */
    public String getNumReg() {
        return this.numReg;
    }

    public void setNumReg(String numReg) {
        this.numReg = numReg;
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
     *             column="ID_OFIC"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getIdOfic() {
        return this.idOfic;
    }

    public void setIdOfic(int idOfic) {
        this.idOfic = idOfic;
    }

    /** 
     *            @hibernate.property
     *             column="MODIF_TYPE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getModifType() {
        return this.modifType;
    }

    public void setModifType(int modifType) {
        this.modifType = modifType;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrModifreg) ) return false;
        ScrModifreg castOther = (ScrModifreg) other;
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
