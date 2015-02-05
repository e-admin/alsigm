package se.tramites.archigest.stub;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import se.tramites.DocFisicoImpl;

public class DocFisicoVO extends DocFisicoImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(DocFisicoVO.class, true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "DocFisicoVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("tipoDocumento");
		elemField.setXmlName(new QName("", "tipoDocumento"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("asunto");
		elemField.setXmlName(new QName("", "asunto"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Constructor.
	 */
	public DocFisicoVO() {
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
