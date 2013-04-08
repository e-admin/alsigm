package ieci.tecdoc.sgm.terceros.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;
import java.lang.reflect.Array;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ListaTerceros extends RetornoServicio implements Serializable {
	
    private Tercero[] terceros;

	public ListaTerceros() {
		super();
	}

	public ListaTerceros(String errorCode, String returnCode, Tercero[] terceros) {
		super();
        setErrorCode(errorCode);
        setReturnCode(returnCode);
		this.terceros = terceros;
	}

	/**
	 * Gets the terceros value for this ListaTerceros.
	 * 
	 * @return terceros
	 */
	public Tercero[] getTerceros() {
		return terceros;
	}

	/**
	 * Sets the terceros value for this ListaTerceros.
	 * 
	 * @param terceros
	 */
	public void setTerceros(Tercero[] terceros) {
		this.terceros = terceros;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof ListaTerceros))
			return false;
		ListaTerceros other = (ListaTerceros) obj;
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
				&& ((this.terceros == null && other.getTerceros() == null) || (this.terceros != null && java.util.Arrays
						.equals(this.terceros, other.getTerceros())));
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
		if (getTerceros() != null) {
			for (int i = 0; i < Array.getLength(getTerceros()); i++) {
				Object obj = Array.get(getTerceros(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(ListaTerceros.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaTerceros"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("terceros");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "terceros"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "Tercero"));
		elemField.setNillable(true);
		elemField.setItemQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "item"));
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static Serializer getSerializer(String mechType, Class _javaType,
			QName _xmlType) {
		return new BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static Deserializer getDeserializer(String mechType,
			Class _javaType, QName _xmlType) {
		return new BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
