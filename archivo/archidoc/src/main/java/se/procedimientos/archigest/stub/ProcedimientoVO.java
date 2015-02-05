package se.procedimientos.archigest.stub;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ProcedimientoVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String documentosBasicos;
	private InfoBProcedimientoVO informacionBasica;
	private String normativa;
	private String objeto;
	private OrganoProductorVO[] organosProductores;
	private String tramites;

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(ProcedimientoVO.class, true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "ProcedimientoVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("informacionBasica");
		elemField.setXmlName(new QName("", "informacionBasica"));
		elemField.setXmlType(new QName("urn:BeanService",
				"InfoBProcedimientoVO"));
		elemField.setJavaType(InfoBProcedimientoVO.class);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("objeto");
		elemField.setXmlName(new QName("", "objeto"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("tramites");
		elemField.setXmlName(new QName("", "tramites"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("normativa");
		elemField.setXmlName(new QName("", "normativa"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("documentosBasicos");
		elemField.setXmlName(new QName("", "documentosBasicos"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("organosProductores");
		elemField.setXmlName(new QName("", "organosProductores"));
		elemField.setXmlType(new QName("urn:BeanService", "OrganoProductorVO"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Constructor.
	 */
	public ProcedimientoVO() {
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

	/**
	 * @return Returns the documentosBasicos.
	 */
	public String getDocumentosBasicos() {
		return documentosBasicos;
	}

	/**
	 * @param documentosBasicos
	 *            The documentosBasicos to set.
	 */
	public void setDocumentosBasicos(String documentosBasicos) {
		this.documentosBasicos = documentosBasicos;
	}

	/**
	 * @return Returns the informacionBasica.
	 */
	public InfoBProcedimientoVO getInformacionBasica() {
		return informacionBasica;
	}

	/**
	 * @param informacionBasica
	 *            The informacionBasica to set.
	 */
	public void setInformacionBasica(InfoBProcedimientoVO informacionBasica) {
		this.informacionBasica = informacionBasica;
	}

	/**
	 * @return Returns the normativa.
	 */
	public String getNormativa() {
		return normativa;
	}

	/**
	 * @param normativa
	 *            The normativa to set.
	 */
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}

	/**
	 * @return Returns the objeto.
	 */
	public String getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto
	 *            The objeto to set.
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	/**
	 * @return Returns the organosProductores.
	 */
	public OrganoProductorVO[] getOrganosProductores() {
		return organosProductores;
	}

	/**
	 * @param organosProductores
	 *            The organosProductores to set.
	 */
	public void setOrganosProductores(OrganoProductorVO[] organosProductores) {
		this.organosProductores = organosProductores;
	}

	/**
	 * @return Returns the tramites.
	 */
	public String getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            The tramites to set.
	 */
	public void setTramites(String tramites) {
		this.tramites = tramites;
	}

	/**
	 * @param typeDesc
	 *            The typeDesc to set.
	 */
	public static void setTypeDesc(TypeDesc typeDesc) {
		ProcedimientoVO.typeDesc = typeDesc;
	}
}