package solicitudes.vos;

import java.util.Date;
import java.util.List;

import solicitudes.SolicitudesConstants;

import common.CodigoTransferenciaUtils;

import fondos.model.ElementoCuadroClasificacion;

/**
 * Objeto con los detalles de las unidades documentales asociar a la solicitud
 */
public class BusquedaDetalleVO {

	private String identificacion = null;
	private String codigo = null;
	private String idudoc = null;
	private String titulo = null;
	private String expedienteudoc = null;
	private String signaturaudoc = null;
	private String idfondo = null;
	private String fondo = null;
	private String codsistproductor = null;
	private int tiposolicitud = 0;
	/**
	 * Indica si el tipo al que pertenece el detalle es una consulta(2) o un
	 * prestamo(1)
	 */
	private String descripcion = null;
	/** Descripcion de la udoc */
	private Date fechaFinal = null;
	/** Fecha de fin de uso de la udoc */
	private String codreferencia = null;

	// Otros datos para la impresion de las papeletas
	private Date fentrega = null;
	private Date fmaxfinprestamo = null;
	private String nusrsolicitante = null;
	private String norgsolicitante = null;
	private String ubicacion = null;
	private String iduinstalacion = null;
	private Date fechaini = null;
	private Date fechafin = null;
	private Date festado = null;

	// Datos de solicitud para no cargar todo el VO ni el VO de su Archivo
	private String idSolicitud = null;
	private String codigoArchivo = null;
	private String anioSolicitud = null;
	private Integer ordenSolicitud = null;

	// Datos para el caso de varios niveles de unidad documental como p.e.
	// fracción de serie
	protected int subtipo = 0;
	protected List rangos = null;

	/**
	 * @return Returns the codfondo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param codfondo
	 *            The codfondo to set.
	 */
	public void setTitulo(String codfondo) {
		this.titulo = codfondo;
	}

	/**
	 * @return Returns the expedienteudoc.
	 */
	public String getExpedienteudoc() {
		return expedienteudoc;
	}

	/**
	 * @param expedienteudoc
	 *            The expedienteudoc to set.
	 */
	public void setExpedienteudoc(String expedienteudoc) {
		this.expedienteudoc = expedienteudoc;
	}

	/**
	 * @return Returns the idudoc.
	 */
	public String getIdudoc() {
		return idudoc;
	}

	/**
	 * @param idudoc
	 *            The idudoc to set.
	 */
	public void setIdudoc(String idudoc) {
		this.idudoc = idudoc;
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return Returns the signaturaudoc.
	 */
	public String getSignaturaudoc() {
		return signaturaudoc;
	}

	/**
	 * @param signaturaudoc
	 *            The signaturaudoc to set.
	 */
	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	/**
	 * @return Returns the idFondo.
	 */
	public String getIdfondo() {
		return idfondo;
	}

	/**
	 * @param idFondo
	 *            The idFondo to set.
	 */
	public void setIdfondo(String idFondo) {
		this.idfondo = idFondo;
	}

	/**
	 * Redefinición del metodo equal. Son iguales si coincinde su
	 * identificacion,idudoc,expediente titulo, signatura e idMaestro.
	 */
	public boolean equals(Object arg) {
		boolean resultado = false;

		if (arg != null && (arg instanceof BusquedaDetalleVO)) {
			BusquedaDetalleVO d = (BusquedaDetalleVO) arg;

			// if ( d.idMaestro!=null && this.idMaestro!=null &&
			// d.idMaestro.equals(this.idMaestro) &&
			if (d.getIdSolicitud() != null && d.getIdSolicitud() != null
					&& d.getIdSolicitud().equals(this.getIdSolicitud())
					&& d.idudoc != null && this.idudoc != null
					&& d.idudoc.equals(this.idudoc) && d.signaturaudoc != null
					&& this.signaturaudoc != null
					&& d.signaturaudoc.equals(this.signaturaudoc)
					&& d.tiposolicitud == this.tiposolicitud)
				resultado = true;
			else
				resultado = false;
		} else
			resultado = false;

		return resultado;
	}

	// public String getCodigoMaestro() {
	// return codigoMaestro;
	// return this.ano+"/"+this.orden;

	// }
	// public void setCodigoMaestro(String codigoMaestro) {
	// this.codigoMaestro = codigoMaestro;
	// }
	// public String getIdMaestro() {
	// return idMaestro;
	// }

	public int getTiposolicitud() {
		return tiposolicitud;
	}

	public void setTiposolicitud(int tipo) {
		this.tiposolicitud = tipo;
	}

	public Date getFentrega() {
		return fentrega;
	}

	public void setFentrega(Date fentrega) {
		this.fentrega = fentrega;
	}

	public Date getFmaxfinprestamo() {
		return fmaxfinprestamo;
	}

	public void setFmaxfinprestamo(Date fmaxfinprestamo) {
		this.fmaxfinprestamo = fmaxfinprestamo;
	}

	public String getNorgsolicitante() {
		return norgsolicitante;
	}

	public void setNorgsolicitante(String norgsolicitante) {
		this.norgsolicitante = norgsolicitante;
	}

	public String getNusrsolicitante() {
		return nusrsolicitante;
	}

	public void setNusrsolicitante(String nusrsolicitante) {
		this.nusrsolicitante = nusrsolicitante;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// public void setId(String id) {
	// this.id = id;
	// this.idMaestro = id;
	// }

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getCodsistproductor() {
		return codsistproductor;
	}

	public void setCodsistproductor(String codsistproductor) {
		this.codsistproductor = codsistproductor;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getCodreferencia() {
		return codreferencia;
	}

	public void setCodreferencia(String codReferencia) {
		this.codreferencia = codReferencia;
	}

	public String getIduinstalacion() {
		return iduinstalacion;
	}

	public void setIduinstalacion(String iduinstalacion) {
		this.iduinstalacion = iduinstalacion;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechaini() {
		return fechaini;
	}

	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}

	public Date getFestado() {
		return festado;
	}

	public void setFestado(Date festado) {
		this.festado = festado;
	}

	/*
	 * public SolicitudVO getSolicitud() { return solicitud; }
	 * 
	 * public void setSolicitud(SolicitudVO solicitud) { this.solicitud =
	 * solicitud; }
	 */

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getAnioSolicitud() {
		return anioSolicitud;
	}

	public void setAnioSolicitud(String anioSolicitud) {
		this.anioSolicitud = anioSolicitud;
	}

	public String getCodigoArchivo() {
		return codigoArchivo;
	}

	public void setCodigoArchivo(String codigoArchivo) {
		this.codigoArchivo = codigoArchivo;
	}

	public int getOrdenSolicitud() {
		return ordenSolicitud.intValue();
	}

	public void setOrdenSolicitud(int ordenSolicitud) {
		this.ordenSolicitud = new Integer(ordenSolicitud);
	}

	public String getCodigoSolicitud() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(anioSolicitud,
				codigoArchivo, ordenSolicitud,
				SolicitudesConstants.FORMAT_ID_SOLICITUD);
	}

	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public boolean isSubtipoCaja() {
		return this.subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA;
	}

	public List getRangos() {
		return rangos;
	}

	public void setRangos(List rangos) {
		this.rangos = rangos;
	}
}
