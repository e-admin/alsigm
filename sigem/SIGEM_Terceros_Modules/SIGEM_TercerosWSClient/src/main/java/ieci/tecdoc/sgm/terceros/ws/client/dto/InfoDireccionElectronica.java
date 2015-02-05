package ieci.tecdoc.sgm.terceros.ws.client.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class InfoDireccionElectronica extends RetornoServicio implements Serializable {
	
    private DireccionElectronica direccionElectronica;

	public InfoDireccionElectronica() {
		super();
	}

	public InfoDireccionElectronica(String errorCode, String returnCode, DireccionElectronica direccionElectronica) {
		super();
		setErrorCode(errorCode);
		setReturnCode(returnCode);
		this.direccionElectronica = direccionElectronica;
	}

	public DireccionElectronica getDireccionElectronica() {
		return direccionElectronica;
	}

	public void setDireccionElectronica(DireccionElectronica direccionElectronica) {
		this.direccionElectronica = direccionElectronica;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof InfoDireccionElectronica))
			return false;
		InfoDireccionElectronica other = (InfoDireccionElectronica) obj;
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
				&& ((this.direccionElectronica == null && other.getDireccionElectronica() == null) || (this.direccionElectronica != null && this.direccionElectronica
						.equals(other.getDireccionElectronica())));
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
		if (getDireccionElectronica() != null) {
			_hashCode += getDireccionElectronica().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(InfoDireccionElectronica.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoDireccionElectronica"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("direccionElectronica");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "direccionElectronica"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionElectronica"));
		elemField.setNillable(true);
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
