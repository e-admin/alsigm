package se.tramites.archigest.stub;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import se.tramites.InteresadoImpl;

public class InteresadoVO extends InteresadoImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(InteresadoVO.class, true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "InteresadoVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("nombre");
		elemField.setXmlName(new QName("", "nombre"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("numIdentidad");
		elemField.setXmlName(new QName("", "numIdentidad"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("rol");
		elemField.setXmlName(new QName("", "rol"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("idEnTerceros");
		elemField.setXmlName(new QName("", "idEnTerceros"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("interesadoPrincipal");
		elemField.setXmlName(new QName("", "interesadoPrincipal"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"boolean"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Constructor.
	 */
	public InteresadoVO() {
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
