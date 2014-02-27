/**
 * FolderWithPersonInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class FolderWithPersonInfo  extends ieci.tecdoc.sgm.registropresencial.ws.server.Folder  implements java.io.Serializable {
    private ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] persons;

    public FolderWithPersonInfo() {
    }

    public FolderWithPersonInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.registropresencial.ws.server.BookId bookId,
           ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage[] docWithPage,
           ieci.tecdoc.sgm.registropresencial.ws.server.Documents documentos,
           ieci.tecdoc.sgm.registropresencial.ws.server.Fields fields,
           java.lang.String folderId,
           java.lang.String folderNumber,
           ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] persons) {
        super(
            errorCode,
            returnCode,
            bookId,
            docWithPage,
            documentos,
            fields,
            folderId,
            folderNumber);
        this.persons = persons;
    }


    /**
     * Gets the persons value for this FolderWithPersonInfo.
     * 
     * @return persons
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] getPersons() {
        return persons;
    }


    /**
     * Sets the persons value for this FolderWithPersonInfo.
     * 
     * @param persons
     */
    public void setPersons(ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] persons) {
        this.persons = persons;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FolderWithPersonInfo)) return false;
        FolderWithPersonInfo other = (FolderWithPersonInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.persons==null && other.getPersons()==null) || 
             (this.persons!=null &&
              java.util.Arrays.equals(this.persons, other.getPersons())));
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
        if (getPersons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPersons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPersons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FolderWithPersonInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "FolderWithPersonInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("persons");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "persons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item"));
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
