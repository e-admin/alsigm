package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_WS"
 *     
*/
public class ScrW implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private String nombre;

    /** identifier field */
    private String macaddr;

    /** identifier field */
    private String ipaddr;

    /** identifier field */
    private String code;

    /** identifier field */
    private String descripcion;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic;

    /** full constructor */
    public ScrW(int id, String nombre, String macaddr, String ipaddr, String code, String descripcion, com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.id = id;
        this.nombre = nombre;
        this.macaddr = macaddr;
        this.ipaddr = ipaddr;
        this.code = code;
        this.descripcion = descripcion;
        this.scrOfic = scrOfic;
    }

    /** default constructor */
    public ScrW() {
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
     *                 column="NOMBRE"
     *                 length="50"
     *             
     */
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** 
     *                @hibernate.property
     *                 column="MACADDR"
     *                 length="32"
     *             
     */
    public String getMacaddr() {
        return this.macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    /** 
     *                @hibernate.property
     *                 column="IPADDR"
     *                 length="20"
     *             
     */
    public String getIpaddr() {
        return this.ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    /** 
     *                @hibernate.property
     *                 column="CODE"
     *                 length="4"
     *             
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 
     *                @hibernate.property
     *                 column="DESCRIPCION"
     *                 length="250"
     *             
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
            .append("id", getId())
            .append("nombre", getNombre())
            .append("macaddr", getMacaddr())
            .append("ipaddr", getIpaddr())
            .append("code", getCode())
            .append("descripcion", getDescripcion())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrW) ) return false;
        ScrW castOther = (ScrW) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getNombre(), castOther.getNombre())
            .append(this.getMacaddr(), castOther.getMacaddr())
            .append(this.getIpaddr(), castOther.getIpaddr())
            .append(this.getCode(), castOther.getCode())
            .append(this.getDescripcion(), castOther.getDescripcion())
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
            .append(getNombre())
            .append(getMacaddr())
            .append(getIpaddr())
            .append(getCode())
            .append(getDescripcion())
            .toHashCode();
    }

}
