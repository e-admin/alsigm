package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_LOCKRELATIONS"
 *     
*/
public class ScrLockrelation implements Serializable {

    /** identifier field */
    private int typebook;

    /** identifier field */
    private int typerel;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic;

    /** full constructor */
    public ScrLockrelation(int typebook, int typerel, com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.typebook = typebook;
        this.typerel = typerel;
        this.scrOfic = scrOfic;
    }

    /** default constructor */
    public ScrLockrelation() {
    }

    /** 
     *                @hibernate.property
     *                 column="TYPEBOOK"
     *                 length="10"
     *             
     */
    public int getTypebook() {
        return this.typebook;
    }

    public void setTypebook(int typebook) {
        this.typebook = typebook;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPEREL"
     *                 length="10"
     *             
     */
    public int getTyperel() {
        return this.typerel;
    }

    public void setTyperel(int typerel) {
        this.typerel = typerel;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDOFIC"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOfic getScrOfic() {
        return this.scrOfic;
    }

    public void setScrOfic(com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.scrOfic = scrOfic;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typebook", getTypebook())
            .append("typerel", getTyperel())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrLockrelation) ) return false;
        ScrLockrelation castOther = (ScrLockrelation) other;
        return new EqualsBuilder()
            .append(this.getTypebook(), castOther.getTypebook())
            .append(this.getTyperel(), castOther.getTyperel())
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
            .append(getTypebook())
            .append(getTyperel())
            .toHashCode();
    }

}
