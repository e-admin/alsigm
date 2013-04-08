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

public class ListaDireccionesPostales extends RetornoServicio implements Serializable {
	
    private DireccionPostal[] direccionesPostales;

	public ListaDireccionesPostales() {
		super();
	}

	public ListaDireccionesPostales(String errorCode, String returnCode, DireccionPostal[] direccionesPostales) {
		super();
        setErrorCode(errorCode);
        setReturnCode(returnCode);
		this.direccionesPostales = direccionesPostales;
	}

	public DireccionPostal[] getDireccionesPostales() {
		return direccionesPostales;
	}

	public void setDireccionesPostales(DireccionPostal[] direccionesPostales) {
		this.direccionesPostales = direccionesPostales;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof ListaDireccionesPostales))
			return false;
		ListaDireccionesPostales other = (ListaDireccionesPostales) obj;
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
				&& ((this.direccionesPostales == null && other.getDireccionesPostales() == null) || (this.direccionesPostales != null && java.util.Arrays
						.equals(this.direccionesPostales, other.getDireccionesPostales())));
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
		if (getDireccionesPostales() != null) {
			for (int i = 0; i < Array.getLength(getDireccionesPostales()); i++) {
				Object obj = Array.get(getDireccionesPostales(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(ListaDireccionesPostales.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaDireccionesPostales"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("direccionesPostales");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "direccionesPostales"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionPostal"));
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
