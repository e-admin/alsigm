/**
 * RegisterInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class RegisterInfo  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private java.lang.String bookId;

    private java.lang.String date;

    private java.lang.String folderId;

    private java.lang.String number;

    private java.lang.String office;

    private java.lang.String officeName;

    private java.lang.String state;

    private java.lang.String userName;

    private java.lang.String workDate;

    public RegisterInfo() {
    }

    public RegisterInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String bookId,
           java.lang.String date,
           java.lang.String folderId,
           java.lang.String number,
           java.lang.String office,
           java.lang.String officeName,
           java.lang.String state,
           java.lang.String userName,
           java.lang.String workDate) {
        super(
            errorCode,
            returnCode);
        this.bookId = bookId;
        this.date = date;
        this.folderId = folderId;
        this.number = number;
        this.office = office;
        this.officeName = officeName;
        this.state = state;
        this.userName = userName;
        this.workDate = workDate;
    }


    /**
     * Gets the bookId value for this RegisterInfo.
     * 
     * @return bookId
     */
    public java.lang.String getBookId() {
        return bookId;
    }


    /**
     * Sets the bookId value for this RegisterInfo.
     * 
     * @param bookId
     */
    public void setBookId(java.lang.String bookId) {
        this.bookId = bookId;
    }


    /**
     * Gets the date value for this RegisterInfo.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this RegisterInfo.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the folderId value for this RegisterInfo.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this RegisterInfo.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the number value for this RegisterInfo.
     * 
     * @return number
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this RegisterInfo.
     * 
     * @param number
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    /**
     * Gets the office value for this RegisterInfo.
     * 
     * @return office
     */
    public java.lang.String getOffice() {
        return office;
    }


    /**
     * Sets the office value for this RegisterInfo.
     * 
     * @param office
     */
    public void setOffice(java.lang.String office) {
        this.office = office;
    }


    /**
     * Gets the officeName value for this RegisterInfo.
     * 
     * @return officeName
     */
    public java.lang.String getOfficeName() {
        return officeName;
    }


    /**
     * Sets the officeName value for this RegisterInfo.
     * 
     * @param officeName
     */
    public void setOfficeName(java.lang.String officeName) {
        this.officeName = officeName;
    }


    /**
     * Gets the state value for this RegisterInfo.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this RegisterInfo.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the userName value for this RegisterInfo.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this RegisterInfo.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the workDate value for this RegisterInfo.
     * 
     * @return workDate
     */
    public java.lang.String getWorkDate() {
        return workDate;
    }


    /**
     * Sets the workDate value for this RegisterInfo.
     * 
     * @param workDate
     */
    public void setWorkDate(java.lang.String workDate) {
        this.workDate = workDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegisterInfo)) return false;
        RegisterInfo other = (RegisterInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bookId==null && other.getBookId()==null) || 
             (this.bookId!=null &&
              this.bookId.equals(other.getBookId()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.office==null && other.getOffice()==null) || 
             (this.office!=null &&
              this.office.equals(other.getOffice()))) &&
            ((this.officeName==null && other.getOfficeName()==null) || 
             (this.officeName!=null &&
              this.officeName.equals(other.getOfficeName()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.workDate==null && other.getWorkDate()==null) || 
             (this.workDate!=null &&
              this.workDate.equals(other.getWorkDate())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getBookId() != null) {
            _hashCode += getBookId().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getOffice() != null) {
            _hashCode += getOffice().hashCode();
        }
        if (getOfficeName() != null) {
            _hashCode += getOfficeName().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getWorkDate() != null) {
            _hashCode += getWorkDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "RegisterInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "bookId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("office");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "office"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "officeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "workDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
