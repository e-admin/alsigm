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

public class ListaDireccionesElectronicas extends RetornoServicio implements Serializable {
	
    private DireccionElectronica[] direccionesElectronicas;

	public ListaDireccionesElectronicas() {
		super();
	}

	public ListaDireccionesElectronicas(String errorCode, String returnCode, DireccionElectronica[] direccionesElectronicas) {
		super();
        setErrorCode(errorCode);
        setReturnCode(returnCode);
		this.direccionesElectronicas = direccionesElectronicas;
	}

	public DireccionElectronica[] getDireccionesElectronicas() {
		return direccionesElectronicas;
	}

	public void setDireccionesElectronicas(DireccionElectronica[] direccionesElectronicas) {
		this.direccionesElectronicas = direccionesElectronicas;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof ListaDireccionesElectronicas))
			return false;
		ListaDireccionesElectronicas other = (ListaDireccionesElectronicas) obj;
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
				&& ((this.direccionesElectronicas == null && other.getDireccionesElectronicas() == null) || (this.direccionesElectronicas != null && java.util.Arrays
						.equals(this.direccionesElectronicas, other.getDireccionesElectronicas())));
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
		if (getDireccionesElectronicas() != null) {
			for (int i = 0; i < Array.getLength(getDireccionesElectronicas()); i++) {
				Object obj = Array.get(getDireccionesElectronicas(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(ListaDireccionesElectronicas.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "ListaDireccionesElectronicas"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("direccionesElectronicas");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "direccionesElectronicas"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionElectronica"));
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
