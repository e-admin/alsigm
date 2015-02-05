package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_DELTAS"
 *     
*/
public class ScrDelta implements Serializable {

    /** identifier field */
    private int idcambio;

    /** identifier field */
    private String tabla;

    /** identifier field */
    private int idregistro;

    /** identifier field */
    private int tipocambio;

    /** full constructor */
    public ScrDelta(int idcambio, String tabla, int idregistro, int tipocambio) {
        this.idcambio = idcambio;
        this.tabla = tabla;
        this.idregistro = idregistro;
        this.tipocambio = tipocambio;
    }

    /** default constructor */
    public ScrDelta() {
    }

    /** 
     *                @hibernate.property
     *                 column="IDCAMBIO"
     *                 length="10"
     *             
     */
    public int getIdcambio() {
        return this.idcambio;
    }

    public void setIdcambio(int idcambio) {
        this.idcambio = idcambio;
    }

    /** 
     *                @hibernate.property
     *                 column="TABLA"
     *                 length="20"
     *             
     */
    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /** 
     *                @hibernate.property
     *                 column="IDREGISTRO"
     *                 length="10"
     *             
     */
    public int getIdregistro() {
        return this.idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }

    /** 
     *                @hibernate.property
     *                 column="TIPOCAMBIO"
     *                 length="10"
     *             
     */
    public int getTipocambio() {
        return this.tipocambio;
    }

    public void setTipocambio(int tipocambio) {
        this.tipocambio = tipocambio;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idcambio", getIdcambio())
            .append("tabla", getTabla())
            .append("idregistro", getIdregistro())
            .append("tipocambio", getTipocambio())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrDelta) ) return false;
        ScrDelta castOther = (ScrDelta) other;
        return new EqualsBuilder()
            .append(this.getIdcambio(), castOther.getIdcambio())
            .append(this.getTabla(), castOther.getTabla())
            .append(this.getIdregistro(), castOther.getIdregistro())
            .append(this.getTipocambio(), castOther.getTipocambio())
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
            .append(getIdcambio())
            .append(getTabla())
            .append(getIdregistro())
            .append(getTipocambio())
            .toHashCode();
    }

}
