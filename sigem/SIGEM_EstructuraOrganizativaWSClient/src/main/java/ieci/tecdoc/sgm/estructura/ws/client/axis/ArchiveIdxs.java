/**
 * ArchiveIdxs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public class ArchiveIdxs  extends ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio  implements java.io.Serializable {
    private ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdx[] archiveIndxsList;

    public ArchiveIdxs() {
    }

    public ArchiveIdxs(
           java.lang.String errorCode,
           java.lang.String returnCode,
           ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdx[] archiveIndxsList) {
        super(
            errorCode,
            returnCode);
        this.archiveIndxsList = archiveIndxsList;
    }


    /**
     * Gets the archiveIndxsList value for this ArchiveIdxs.
     * 
     * @return archiveIndxsList
     */
    public ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdx[] getArchiveIndxsList() {
        return archiveIndxsList;
    }


    /**
     * Sets the archiveIndxsList value for this ArchiveIdxs.
     * 
     * @param archiveIndxsList
     */
    public void setArchiveIndxsList(ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdx[] archiveIndxsList) {
        this.archiveIndxsList = archiveIndxsList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArchiveIdxs)) return false;
        ArchiveIdxs other = (ArchiveIdxs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.archiveIndxsList==null && other.getArchiveIndxsList()==null) || 
             (this.archiveIndxsList!=null &&
              java.util.Arrays.equals(this.archiveIndxsList, other.getArchiveIndxsList())));
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
        if (getArchiveIndxsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArchiveIndxsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArchiveIndxsList(), i);
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
        new org.apache.axis.description.TypeDesc(ArchiveIdxs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveIdxs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("archiveIndxsList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "archiveIndxsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "ArchiveIdx"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.estructura.sgm.tecdoc.ieci", "item"));
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
