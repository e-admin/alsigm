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

public class InfoTercero extends RetornoServicio implements Serializable {
	
    private Tercero tercero;

	public InfoTercero() {
		super();
	}

	public InfoTercero(String errorCode, String returnCode, Tercero tercero) {
		super();
		setErrorCode(errorCode);
		setReturnCode(returnCode);
		this.tercero = tercero;
	}

	/**
	 * Gets the tercero value for this InfoTercero.
	 * 
	 * @return tercero
	 */
	public Tercero getTercero() {
		return tercero;
	}

	/**
	 * Sets the tercero value for this InfoTercero.
	 * 
	 * @param tercero
	 */
	public void setTercero(Tercero tercero) {
		this.tercero = tercero;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof InfoTercero))
			return false;
		InfoTercero other = (InfoTercero) obj;
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
				&& ((this.tercero == null && other.getTercero() == null) || (this.tercero != null && this.tercero
						.equals(other.getTercero())));
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
		if (getTercero() != null) {
			_hashCode += getTercero().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(InfoTercero.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "InfoTercero"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("tercero");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "tercero"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "Tercero"));
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
