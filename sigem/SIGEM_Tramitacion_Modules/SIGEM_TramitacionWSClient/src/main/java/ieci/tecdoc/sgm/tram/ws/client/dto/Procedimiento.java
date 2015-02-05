package ieci.tecdoc.sgm.tram.ws.client.dto;

import java.io.Serializable;
import java.lang.reflect.Array;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Procedimiento extends RetornoServicio implements Serializable {

	private String documentosBasicos;

	private InfoBProcedimiento informacionBasica;

	private String normativa;

	private String objeto;

	private OrganoProductor[] organosProductores;

	private String tramites;

	public Procedimiento() {
	}

	public Procedimiento(String errorCode, String returnCode,
			String documentosBasicos, InfoBProcedimiento informacionBasica,
			String normativa, String objeto,
			OrganoProductor[] organosProductores, String tramites) {
		setErrorCode(errorCode);
		setReturnCode(returnCode);
		this.documentosBasicos = documentosBasicos;
		this.informacionBasica = informacionBasica;
		this.normativa = normativa;
		this.objeto = objeto;
		this.organosProductores = organosProductores;
		this.tramites = tramites;
	}

	/**
	 * Gets the documentosBasicos value for this Procedimiento.
	 * 
	 * @return documentosBasicos
	 */
	public String getDocumentosBasicos() {
		return documentosBasicos;
	}

	/**
	 * Sets the documentosBasicos value for this Procedimiento.
	 * 
	 * @param documentosBasicos
	 */
	public void setDocumentosBasicos(String documentosBasicos) {
		this.documentosBasicos = documentosBasicos;
	}

	/**
	 * Gets the informacionBasica value for this Procedimiento.
	 * 
	 * @return informacionBasica
	 */
	public InfoBProcedimiento getInformacionBasica() {
		return informacionBasica;
	}

	/**
	 * Sets the informacionBasica value for this Procedimiento.
	 * 
	 * @param informacionBasica
	 */
	public void setInformacionBasica(InfoBProcedimiento informacionBasica) {
		this.informacionBasica = informacionBasica;
	}

	/**
	 * Gets the normativa value for this Procedimiento.
	 * 
	 * @return normativa
	 */
	public String getNormativa() {
		return normativa;
	}

	/**
	 * Sets the normativa value for this Procedimiento.
	 * 
	 * @param normativa
	 */
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}

	/**
	 * Gets the objeto value for this Procedimiento.
	 * 
	 * @return objeto
	 */
	public String getObjeto() {
		return objeto;
	}

	/**
	 * Sets the objeto value for this Procedimiento.
	 * 
	 * @param objeto
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	/**
	 * Gets the organosProductores value for this Procedimiento.
	 * 
	 * @return organosProductores
	 */
	public OrganoProductor[] getOrganosProductores() {
		return organosProductores;
	}

	/**
	 * Sets the organosProductores value for this Procedimiento.
	 * 
	 * @param organosProductores
	 */
	public void setOrganosProductores(OrganoProductor[] organosProductores) {
		this.organosProductores = organosProductores;
	}

	/**
	 * Gets the tramites value for this Procedimiento.
	 * 
	 * @return tramites
	 */
	public String getTramites() {
		return tramites;
	}

	/**
	 * Sets the tramites value for this Procedimiento.
	 * 
	 * @param tramites
	 */
	public void setTramites(String tramites) {
		this.tramites = tramites;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof Procedimiento))
			return false;
		Procedimiento other = (Procedimiento) obj;
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
				&& ((this.documentosBasicos == null && other
						.getDocumentosBasicos() == null) || (this.documentosBasicos != null && this.documentosBasicos
						.equals(other.getDocumentosBasicos())))
				&& ((this.informacionBasica == null && other
						.getInformacionBasica() == null) || (this.informacionBasica != null && this.informacionBasica
						.equals(other.getInformacionBasica())))
				&& ((this.normativa == null && other.getNormativa() == null) || (this.normativa != null && this.normativa
						.equals(other.getNormativa())))
				&& ((this.objeto == null && other.getObjeto() == null) || (this.objeto != null && this.objeto
						.equals(other.getObjeto())))
				&& ((this.organosProductores == null && other
						.getOrganosProductores() == null) || (this.organosProductores != null && java.util.Arrays
						.equals(this.organosProductores, other
								.getOrganosProductores())))
				&& ((this.tramites == null && other.getTramites() == null) || (this.tramites != null && this.tramites
						.equals(other.getTramites())));
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
		if (getDocumentosBasicos() != null) {
			_hashCode += getDocumentosBasicos().hashCode();
		}
		if (getInformacionBasica() != null) {
			_hashCode += getInformacionBasica().hashCode();
		}
		if (getNormativa() != null) {
			_hashCode += getNormativa().hashCode();
		}
		if (getObjeto() != null) {
			_hashCode += getObjeto().hashCode();
		}
		if (getOrganosProductores() != null) {
			for (int i = 0; i < Array.getLength(getOrganosProductores()); i++) {
				Object obj = Array.get(getOrganosProductores(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getTramites() != null) {
			_hashCode += getTramites().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Procedimiento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "Procedimiento"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("documentosBasicos");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "documentosBasicos"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("informacionBasica");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "informacionBasica"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "InfoBProcedimiento"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("normativa");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "normativa"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("objeto");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "objeto"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("organosProductores");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "organosProductores"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "OrganoProductor"));
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "item"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tramites");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://server.ws.tram.sgm.tecdoc.ieci", "tramites"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(
			String mechType, Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			String mechType, Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

}
