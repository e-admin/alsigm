package ieci.tecdoc.sgm.terceros.ws.client.dto;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class Tercero implements Serializable {

	/** Identificador del tercero en el sistema externo. */
    private String idExt = null;
    
	/** Número de identificación del tercero. */
    private String identificacion = null;
    
	/** Nombre del tercero. */
	private String nombre = null;

	/** Primer apellido del tercero. */
	private String primerApellido = null;
	
	/** Primer apellido del tercero. */
	private String segundoApellido = null;
	
	/** Indica si el tipo de dirección de notificación es telemática. */
    private boolean notificacionTelematica = false;
    
    /** Tipo de persona. */
    private String tipoPersona = null;
	
	/** Direcciones postales asociadas al tercero. */
	DireccionPostal [] direccionesPostales = null;
	
	/** Direcciones electronicas asociadas al tercero. */
	DireccionElectronica [] direccionesElectronicas = null;


	public Tercero() {
		super();
	}

	/**
	 * Gets the idExt value for this Tercero.
	 * 
	 * @return idExt
	 */
	public String getIdExt() {
		return idExt;
	}

	/**
	 * Sets the idExt value for this Tercero.
	 * 
	 * @param idExt
	 */
	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	/**
	 * Gets the identificacion value for this Tercero.
	 * 
	 * @return identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * Sets the identificacion value for this Tercero.
	 * 
	 * @param identificacion
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * Gets the nombre value for this Tercero.
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre value for this Tercero.
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the notificacionTelematica value for this Tercero.
	 * 
	 * @return notificacionTelematica
	 */
	public boolean isNotificacionTelematica() {
		return notificacionTelematica;
	}

	/**
	 * Sets the notificacionTelematica value for this Tercero.
	 * 
	 * @param notificacionTelematica
	 */
	public void setNotificacionTelematica(boolean notificacionTelematica) {
		this.notificacionTelematica = notificacionTelematica;
	}

	/**
	 * Gets the primerApellido value for this Tercero.
	 * 
	 * @return primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Sets the primerApellido value for this Tercero.
	 * 
	 * @param primerApellido
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Gets the segundoApellido value for this Tercero.
	 * 
	 * @return segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Sets the segundoApellido value for this Tercero.
	 * 
	 * @param segundoApellido
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}


	/**
	 * Gets the tipoPersona value for this Tercero.
	 * 
	 * @return tipoPersona
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Sets the tipoPersona value for this Tercero.
	 * 
	 * @param tipoPersona
	 */
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public DireccionPostal[] getDireccionesPostales() {
		return direccionesPostales;
	}

	public void setDireccionesPostales(DireccionPostal[] direccionesPostales) {
		this.direccionesPostales = direccionesPostales;
	}

	public DireccionElectronica[] getDireccionesElectronicas() {
		return direccionesElectronicas;
	}

	public void setDireccionesElectronicas(
			DireccionElectronica[] direccionesElectronicas) {
		this.direccionesElectronicas = direccionesElectronicas;
	}
	
	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof Tercero))
			return false;
		Tercero other = (Tercero) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.idExt == null && other.getIdExt() == null) || (this.idExt != null && this.idExt
						.equals(other.getIdExt())))
				&& ((this.identificacion == null && other.getIdentificacion() == null) || (this.identificacion != null && this.identificacion
						.equals(other.getIdentificacion())))
				&& ((this.nombre == null && other.getNombre() == null) || (this.nombre != null && this.nombre
						.equals(other.getNombre())))
				&& this.notificacionTelematica == other
						.isNotificacionTelematica()
				&& ((this.primerApellido == null && other.getPrimerApellido() == null) || (this.primerApellido != null && this.primerApellido
						.equals(other.getPrimerApellido())))
				&& ((this.segundoApellido == null && other.getSegundoApellido() == null) || (this.segundoApellido != null && this.segundoApellido
						.equals(other.getSegundoApellido())))
				&& ((this.direccionesPostales == null && other.getDireccionesPostales() == null) || (this.direccionesPostales != null && this.direccionesPostales
						.equals(other.getDireccionesPostales())))
				&& ((this.direccionesElectronicas == null && other.getDireccionesElectronicas() == null) || (this.direccionesElectronicas != null && this.direccionesElectronicas
						.equals(other.getDireccionesElectronicas())))
				&& ((this.tipoPersona == null && other.getTipoPersona() == null) || (this.tipoPersona != null && this.tipoPersona
						.equals(other.getTipoPersona())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getIdExt() != null) {
			_hashCode += getIdExt().hashCode();
		}
		if (getIdentificacion() != null) {
			_hashCode += getIdentificacion().hashCode();
		}
		if (getNombre() != null) {
			_hashCode += getNombre().hashCode();
		}
		_hashCode += (isNotificacionTelematica() ? Boolean.TRUE : Boolean.FALSE)
				.hashCode();
		if (getPrimerApellido() != null) {
			_hashCode += getPrimerApellido().hashCode();
		}
		if (getSegundoApellido() != null) {
			_hashCode += getSegundoApellido().hashCode();
		}
		if (getDireccionesPostales() != null) {
			_hashCode += getDireccionesPostales().hashCode();
		}
		if (getDireccionesElectronicas() != null) {
			_hashCode += getDireccionesElectronicas().hashCode();
		}
		if (getTipoPersona() != null) {
			_hashCode += getTipoPersona().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static TypeDesc typeDesc = new TypeDesc(Tercero.class, true);

	static {
		typeDesc.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "Tercero"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("idExt");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "idExt"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("identificacion");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "identificacion"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("nombre");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "nombre"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("notificacionTelematica");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci",
				"notificacionTelematica"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"boolean"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("primerApellido");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "primerApellido"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("segundoApellido");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "segundoApellido"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("direccionesPostales");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "direccionesPostales"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionPostal"));
		elemField.setItemQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "item"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("direccionesElectronicas");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "direccionesElectronicas"));
		elemField.setXmlType(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "DireccionElectronica"));
		elemField.setItemQName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "item"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("tipoPersona");
		elemField.setXmlName(new QName(
				"http://server.ws.terceros.sgm.tecdoc.ieci", "tipoPersona"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema",
				"string"));
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
