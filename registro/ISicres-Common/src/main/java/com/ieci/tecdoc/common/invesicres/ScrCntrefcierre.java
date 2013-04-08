package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_CNTREFCIERRE"
 *     
*/
public class ScrCntrefcierre implements Serializable {

    /** identifier field */
    private String tipo;

    /** identifier field */
    private int contador;

    /** full constructor */
    public ScrCntrefcierre(String tipo, int contador) {
        this.tipo = tipo;
        this.contador = contador;
    }

    /** default constructor */
    public ScrCntrefcierre() {
    }

    /** 
     *                @hibernate.property
     *                 column="TIPO"
     *                 length="3"
     *             
     */
    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("tipo", getTipo())
            .append("contador", getContador())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrCntrefcierre) ) return false;
        ScrCntrefcierre castOther = (ScrCntrefcierre) other;
        return new EqualsBuilder()
            .append(this.getTipo(), castOther.getTipo())
            .append(this.getContador(), castOther.getContador())
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
            .append(getTipo())
            .append(getContador())
            .toHashCode();
    }

}
