package se.procedimientos.archigest.stub;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import se.procedimientos.InfoBProcedimientoImpl;

public class InfoBProcedimientoVO extends InfoBProcedimientoImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(InfoBProcedimientoVO.class,
			true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "InfoBProcedimientoVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("codigo");
		elemField.setXmlName(new QName("", "codigo"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("id");
		elemField.setXmlName(new QName("", "id"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("nombre");
		elemField.setXmlName(new QName("", "nombre"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("codigoSistemaProductor");
		elemField.setXmlName(new QName("", "codigoSistemaProductor"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("nombreSistemaProductor");
		elemField.setXmlName(new QName("", "nombreSistemaProductor"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Constructor.
	 */
	public InfoBProcedimientoVO() {
		super();
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