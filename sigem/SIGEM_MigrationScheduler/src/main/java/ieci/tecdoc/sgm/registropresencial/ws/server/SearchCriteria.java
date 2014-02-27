/**
 * SearchCriteria.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class SearchCriteria  implements java.io.Serializable {
    private java.lang.String firstRow;

    private java.lang.String maxResults;

    private java.lang.String oficAsoc;

    private java.lang.String onlyOpenBooks;

    private java.lang.String state;

    private java.lang.String typeBookRegister;

    public SearchCriteria() {
    }

    public SearchCriteria(
           java.lang.String firstRow,
           java.lang.String maxResults,
           java.lang.String oficAsoc,
           java.lang.String onlyOpenBooks,
           java.lang.String state,
           java.lang.String typeBookRegister) {
           this.firstRow = firstRow;
           this.maxResults = maxResults;
           this.oficAsoc = oficAsoc;
           this.onlyOpenBooks = onlyOpenBooks;
           this.state = state;
           this.typeBookRegister = typeBookRegister;
    }


    /**
     * Gets the firstRow value for this SearchCriteria.
     * 
     * @return firstRow
     */
    public java.lang.String getFirstRow() {
        return firstRow;
    }


    /**
     * Sets the firstRow value for this SearchCriteria.
     * 
     * @param firstRow
     */
    public void setFirstRow(java.lang.String firstRow) {
        this.firstRow = firstRow;
    }


    /**
     * Gets the maxResults value for this SearchCriteria.
     * 
     * @return maxResults
     */
    public java.lang.String getMaxResults() {
        return maxResults;
    }


    /**
     * Sets the maxResults value for this SearchCriteria.
     * 
     * @param maxResults
     */
    public void setMaxResults(java.lang.String maxResults) {
        this.maxResults = maxResults;
    }


    /**
     * Gets the oficAsoc value for this SearchCriteria.
     * 
     * @return oficAsoc
     */
    public java.lang.String getOficAsoc() {
        return oficAsoc;
    }


    /**
     * Sets the oficAsoc value for this SearchCriteria.
     * 
     * @param oficAsoc
     */
    public void setOficAsoc(java.lang.String oficAsoc) {
        this.oficAsoc = oficAsoc;
    }


    /**
     * Gets the onlyOpenBooks value for this SearchCriteria.
     * 
     * @return onlyOpenBooks
     */
    public java.lang.String getOnlyOpenBooks() {
        return onlyOpenBooks;
    }


    /**
     * Sets the onlyOpenBooks value for this SearchCriteria.
     * 
     * @param onlyOpenBooks
     */
    public void setOnlyOpenBooks(java.lang.String onlyOpenBooks) {
        this.onlyOpenBooks = onlyOpenBooks;
    }


    /**
     * Gets the state value for this SearchCriteria.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this SearchCriteria.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the typeBookRegister value for this SearchCriteria.
     * 
     * @return typeBookRegister
     */
    public java.lang.String getTypeBookRegister() {
        return typeBookRegister;
    }


    /**
     * Sets the typeBookRegister value for this SearchCriteria.
     * 
     * @param typeBookRegister
     */
    public void setTypeBookRegister(java.lang.String typeBookRegister) {
        this.typeBookRegister = typeBookRegister;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchCriteria)) return false;
        SearchCriteria other = (SearchCriteria) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firstRow==null && other.getFirstRow()==null) || 
             (this.firstRow!=null &&
              this.firstRow.equals(other.getFirstRow()))) &&
            ((this.maxResults==null && other.getMaxResults()==null) || 
             (this.maxResults!=null &&
              this.maxResults.equals(other.getMaxResults()))) &&
            ((this.oficAsoc==null && other.getOficAsoc()==null) || 
             (this.oficAsoc!=null &&
              this.oficAsoc.equals(other.getOficAsoc()))) &&
            ((this.onlyOpenBooks==null && other.getOnlyOpenBooks()==null) || 
             (this.onlyOpenBooks!=null &&
              this.onlyOpenBooks.equals(other.getOnlyOpenBooks()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.typeBookRegister==null && other.getTypeBookRegister()==null) || 
             (this.typeBookRegister!=null &&
              this.typeBookRegister.equals(other.getTypeBookRegister())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFirstRow() != null) {
            _hashCode += getFirstRow().hashCode();
        }
        if (getMaxResults() != null) {
            _hashCode += getMaxResults().hashCode();
        }
        if (getOficAsoc() != null) {
            _hashCode += getOficAsoc().hashCode();
        }
        if (getOnlyOpenBooks() != null) {
            _hashCode += getOnlyOpenBooks().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getTypeBookRegister() != null) {
            _hashCode += getTypeBookRegister().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchCriteria.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "SearchCriteria"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstRow");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "firstRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "maxResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficAsoc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "oficAsoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlyOpenBooks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "onlyOpenBooks"));
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
        elemField.setFieldName("typeBookRegister");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "typeBookRegister"));
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
