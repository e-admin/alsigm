package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CNTORDINAL"
 *     
*/
public class ScrCntordinal implements Serializable {

    /** identifier field */
    private int contador;

    /** identifier field */
    private String hora;

    /** full constructor */
    public ScrCntordinal(int contador, String hora) {
        this.contador = contador;
        this.hora = hora;
    }

    /** default constructor */
    public ScrCntordinal() {
    }

    /** 
     *                @hibernate.property
     *                 column="CONTADOR"
     *                 length="10"
     *             
     */
    public int getContador() {
        return this.contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    /** 
     *                @hibernate.property
     *                 column="HORA"
     *                 length="4"
     *             
     */
    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("contador", getContador())
            .append("hora", getHora())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCntordinal) ) return false;
        ScrCntordinal castOther = (ScrCntordinal) other;
        return new EqualsBuilder()
            .append(this.getContador(), castOther.getContador())
            .append(this.getHora(), castOther.getHora())
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
            .append(getContador())
            .append(getHora())
            .toHashCode();
    }

}
