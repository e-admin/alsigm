package se.instituciones.archigest.stub;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import se.instituciones.InfoOrganoImpl;

import common.Constants;

public class OrganoVO extends InfoOrganoImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(OrganoVO.class, true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "OrganoVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("codigo");
		elemField.setXmlName(new QName("", "codigo"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName(Constants.ID);
		elemField.setXmlName(new QName("", Constants.ID));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("idPadre");
		elemField.setXmlName(new QName("", "idPadre"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("nivel");
		elemField.setXmlName(new QName("", "nivel"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("nombre");
		elemField.setXmlName(new QName("", "nombre"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Constructor.
	 */
	public OrganoVO() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            Identificador del órgano.
	 * @param codigo
	 *            Código del órgano.
	 * @param nombre
	 *            Nombre del órgano.
	 * @param nivel
	 *            Nivel jerárquico al que pertenece el órgano.
	 * @param idPadre
	 *            Identificador del órgano padre.
	 */
	public OrganoVO(String id, String codigo, String nombre, int nivel,
			String idPadre) {
		super(id, codigo, nombre, nivel, idPadre);
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