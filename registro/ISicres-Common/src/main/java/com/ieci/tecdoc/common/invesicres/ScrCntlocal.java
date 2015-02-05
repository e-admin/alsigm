package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CNTLOCAL"
 *     
*/
public class ScrCntlocal implements Serializable {

    /** identifier field */
    private int an;

    /** identifier field */
    private int puesto;

    /** identifier field */
    private int numReg;

    /** identifier field */
    private int idArch;

    /** full constructor */
    public ScrCntlocal(int an, int puesto, int numReg, int idArch) {
        this.an = an;
        this.puesto = puesto;
        this.numReg = numReg;
        this.idArch = idArch;
    }

    /** default constructor */
    public ScrCntlocal() {
    }

    /** 
     *                @hibernate.property
     *                 column="AN"
     *                 length="10"
     *             
     */
    public int getAn() {
        return this.an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    /** 
     *                @hibernate.property
     *                 column="PUESTO"
     *                 length="10"
     *             
     */
    public int getPuesto() {
        return this.puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    /** 
     *                @hibernate.property
     *                 column="NUM_REG"
     *                 length="10"
     *             
     */
    public int getNumReg() {
        return this.numReg;
    }

    public void setNumReg(int numReg) {
        this.numReg = numReg;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ARCH"
     *                 length="10"
     *             
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("an", getAn())
            .append("puesto", getPuesto())
            .append("numReg", getNumReg())
            .append("idArch", getIdArch())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCntlocal) ) return false;
        ScrCntlocal castOther = (ScrCntlocal) other;
        return new EqualsBuilder()
            .append(this.getAn(), castOther.getAn())
            .append(this.getPuesto(), castOther.getPuesto())
            .append(this.getNumReg(), castOther.getNumReg())
            .append(this.getIdArch(), castOther.getIdArch())
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
            .append(getAn())
            .append(getPuesto())
            .append(getNumReg())
            .append(getIdArch())
            .toHashCode();
    }

}
