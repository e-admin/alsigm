package se.tramites.archigest.stub;

import java.io.Serializable;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import se.procedimientos.archigest.stub.InfoBProcedimientoVO;

public class ExpedienteVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private InfoBExpedienteVO informacionBasica = null;
	private Date fechaInicio = null;
	private Date fechaFinalizacion = null;
	private String idOrgProductor = null;
	private String nombreOrgProductor = null;
	private String asunto = null;
	private DocFisicoVO[] documentosFisicos = null;
	private DocElectronicoVO[] documentosElectronicos = null;
	private InteresadoVO[] interesados = null;
	private EmplazamientoVO[] emplazamientos = null;

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(ExpedienteVO.class, true);
	static {
		typeDesc.setXmlType(new QName("urn:BeanService", "ExpedienteVO"));

		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("informacionBasica");
		elemField.setXmlName(new QName("", "informacionBasica"));
		elemField.setXmlType(new QName("urn:BeanService", "InfoBExpedienteVO"));
		elemField.setJavaType(InfoBProcedimientoVO.class);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("fechaInicio");
		elemField.setXmlName(new QName("", "fechaInicio"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"dateTime"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("fechaFinalizacion");
		elemField.setXmlName(new QName("", "fechaFinalizacion"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"dateTime"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("idOrgProductor");
		elemField.setXmlName(new QName("", "idOrgProductor"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("nombreOrgProductor");
		elemField.setXmlName(new QName("", "nombreOrgProductor"));
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

		elemField = new ElementDesc();
		elemField.setFieldName("documentosFisicos");
		elemField.setXmlName(new QName("", "documentosFisicos"));
		elemField.setXmlType(new QName("urn:BeanService", "DocFisicoVO"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("documentosElectronicos");
		elemField.setXmlName(new QName("", "documentosElectronicos"));
		elemField.setXmlType(new QName("urn:BeanService", "DocElectronicoVO"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("interesados");
		elemField.setXmlName(new QName("", "interesados"));
		elemField.setXmlType(new QName("urn:BeanService", "InteresadoVO"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new ElementDesc();
		elemField.setFieldName("emplazamientos");
		elemField.setXmlName(new QName("", "emplazamientos"));
		elemField.setXmlType(new QName("urn:BeanService", "EmplazamientoVO"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

	}

	/**
	 * Constructor.
	 */
	public ExpedienteVO() {
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
	 * @return Returns the asunto.
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto
	 *            The asunto to set.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * @return Returns the documentosElectronicos.
	 */
	public DocElectronicoVO[] getDocumentosElectronicos() {
		return documentosElectronicos;
	}

	/**
	 * @param documentosElectronicos
	 *            The documentosElectronicos to set.
	 */
	public void setDocumentosElectronicos(
			DocElectronicoVO[] documentosElectronicos) {
		this.documentosElectronicos = documentosElectronicos;
	}

	/**
	 * @return Returns the documentosFisicos.
	 */
	public DocFisicoVO[] getDocumentosFisicos() {
		return documentosFisicos;
	}

	/**
	 * @param documentosFisicos
	 *            The documentosFisicos to set.
	 */
	public void setDocumentosFisicos(DocFisicoVO[] documentosFisicos) {
		this.documentosFisicos = documentosFisicos;
	}

	/**
	 * @return Returns the emplazamientos.
	 */
	public EmplazamientoVO[] getEmplazamientos() {
		return emplazamientos;
	}

	/**
	 * @param emplazamientos
	 *            The emplazamientos to set.
	 */
	public void setEmplazamientos(EmplazamientoVO[] emplazamientos) {
		this.emplazamientos = emplazamientos;
	}

	/**
	 * @return Returns the fechaFinalizacion.
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * @param fechaFinalizacion
	 *            The fechaFinalizacion to set.
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	/**
	 * @return Returns the fechaInicio.
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            The fechaInicio to set.
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return Returns the idOrgProductor.
	 */
	public String getIdOrgProductor() {
		return idOrgProductor;
	}

	/**
	 * @param idOrgProductor
	 *            The idOrgProductor to set.
	 */
	public void setIdOrgProductor(String idOrgProductor) {
		this.idOrgProductor = idOrgProductor;
	}

	/**
	 * @return Returns the informacionBasica.
	 */
	public InfoBExpedienteVO getInformacionBasica() {
		return informacionBasica;
	}

	/**
	 * @param informacionBasica
	 *            The informacionBasica to set.
	 */
	public void setInformacionBasica(InfoBExpedienteVO informacionBasica) {
		this.informacionBasica = informacionBasica;
	}

	/**
	 * @return Returns the interesados.
	 */
	public InteresadoVO[] getInteresados() {
		return interesados;
	}

	/**
	 * @param interesados
	 *            The interesados to set.
	 */
	public void setInteresados(InteresadoVO[] interesados) {
		this.interesados = interesados;
	}

	/**
	 * @return Returns the nombreOrgProductor.
	 */
	public String getNombreOrgProductor() {
		return nombreOrgProductor;
	}

	/**
	 * @param nombreOrgProductor
	 *            The nombreOrgProductor to set.
	 */
	public void setNombreOrgProductor(String nombreOrgProductor) {
		this.nombreOrgProductor = nombreOrgProductor;
	}
}
