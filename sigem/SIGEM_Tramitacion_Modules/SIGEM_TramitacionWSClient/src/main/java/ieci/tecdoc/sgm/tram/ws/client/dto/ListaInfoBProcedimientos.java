package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;
import java.lang.reflect.Array;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaInfoBProcedimientos extends RetornoServicio implements
		Serializable {
	private InfoBProcedimiento[] procedimientos;

	public ListaInfoBProcedimientos() {
	}

	public ListaInfoBProcedimientos(java.lang.String errorCode,
			java.lang.String returnCode, InfoBProcedimiento[] procedimientos) {
		setErrorCode(errorCode);
		setReturnCode(returnCode);
		this.procedimientos = procedimientos;
	}

	/**
	 * Gets the procedimientos value for this ListaInfoBProcedimientos.
	 * 
	 * @return procedimientos
	 */
	public InfoBProcedimiento[] getProcedimientos() {
		return procedimientos;
	}

	/**
	 * Sets the procedimientos value for this ListaInfoBProcedimientos.
	 * 
	 * @param procedimientos
	 */
	public void setProcedimientos(InfoBProcedimiento[] procedimientos) {
		this.procedimientos = procedimientos;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof ListaInfoBProcedimientos))
			return false;
		ListaInfoBProcedimientos other = (ListaInfoBProcedimientos) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj)
				&& ((this.procedimientos == null && other.getProcedimientos() == null) || (this.procedimientos != null && java.util.Arrays
						.equals(this.procedimientos, other.getProcedimientos())));
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
		if (getProcedimientos() != null) {
			for (int i = 0; i < Array.getLength(getProcedimientos()); i++) {
				Object obj = Array.get(getProcedimientos(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			ListaInfoBProcedimientos.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci",
				"ListaInfoBProcedimientos"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("procedimientos");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "procedimientos"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "InfoBProcedimiento"));
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "item"));
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
			String mechType, Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			String mechType, Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

}
