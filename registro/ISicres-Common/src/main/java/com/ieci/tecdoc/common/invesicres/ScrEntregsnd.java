package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_ENTREGSND"
 *     
*/
public class ScrEntregsnd implements Serializable {

    /** identifier field */
    private int idEntreg;

    /** identifier field */
    private int transportType;

    /** identifier field */
    private int encodingMsg;

    /** identifier field */
    private String certificateIssuer;

    /** identifier field */
    private String certificateNserial;

    /** full constructor */
    public ScrEntregsnd(int idEntreg, int transportType, int encodingMsg, String certificateIssuer, String certificateNserial) {
        this.idEntreg = idEntreg;
        this.transportType = transportType;
        this.encodingMsg = encodingMsg;
        this.certificateIssuer = certificateIssuer;
        this.certificateNserial = certificateNserial;
    }

    /** default constructor */
    public ScrEntregsnd() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ENTREG"
     *                 length="10"
     *             
     */
    public int getIdEntreg() {
        return this.idEntreg;
    }

    public void setIdEntreg(int idEntreg) {
        this.idEntreg = idEntreg;
    }

    /** 
     *                @hibernate.property
     *                 column="TRANSPORT_TYPE"
     *                 length="10"
     *             
     */
    public int getTransportType() {
        return this.transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    /** 
     *                @hibernate.property
     *                 column="ENCODING_MSG"
     *                 length="10"
     *             
     */
    public int getEncodingMsg() {
        return this.encodingMsg;
    }

    public void setEncodingMsg(int encodingMsg) {
        this.encodingMsg = encodingMsg;
    }

    /** 
     *                @hibernate.property
     *                 column="CERTIFICATE_ISSUER"
     *                 length="255"
     *             
     */
    public String getCertificateIssuer() {
        return this.certificateIssuer;
    }

    public void setCertificateIssuer(String certificateIssuer) {
        this.certificateIssuer = certificateIssuer;
    }

    /** 
     *                @hibernate.property
     *                 column="CERTIFICATE_NSERIAL"
     *                 length="255"
     *             
     */
    public String getCertificateNserial() {
        return this.certificateNserial;
    }

    public void setCertificateNserial(String certificateNserial) {
        this.certificateNserial = certificateNserial;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idEntreg", getIdEntreg())
            .append("transportType", getTransportType())
            .append("encodingMsg", getEncodingMsg())
            .append("certificateIssuer", getCertificateIssuer())
            .append("certificateNserial", getCertificateNserial())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrEntregsnd) ) return false;
        ScrEntregsnd castOther = (ScrEntregsnd) other;
        return new EqualsBuilder()
            .append(this.getIdEntreg(), castOther.getIdEntreg())
            .append(this.getTransportType(), castOther.getTransportType())
            .append(this.getEncodingMsg(), castOther.getEncodingMsg())
            .append(this.getCertificateIssuer(), castOther.getCertificateIssuer())
            .append(this.getCertificateNserial(), castOther.getCertificateNserial())
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
            .append(getIdEntreg())
            .append(getTransportType())
            .append(getEncodingMsg())
            .append(getCertificateIssuer())
            .append(getCertificateNserial())
            .toHashCode();
    }

}
