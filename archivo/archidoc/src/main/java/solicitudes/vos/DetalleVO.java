package solicitudes.vos;

import java.util.Date;
import java.util.List;

import solicitudes.SolicitudesConstants;
import solicitudes.prestamos.PrestamosConstants;

import common.vos.BaseVO;

import fondos.model.ElementoCuadroClasificacion;

/**
 * Value Object padre de los posibles detalles para el módulo de solicitudes
 * (detalles de prestamo y detalles de consulta).
 */
public class DetalleVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String idsolicitud = null;
	protected int tiposolicitud = 0;
	protected String idudoc = null;
	protected String titulo = null;
	protected String signaturaudoc = null;
	protected String expedienteudoc = null;
	protected int estado = PrestamosConstants.ESTADO_DETALLE_PENDIENTE;
	protected Date festado = null;
	protected Date finicialuso = null;
	protected Date ffinaluso = null;
	protected String motivorechazo = null;
	protected String informacion = null;
	protected String identificacion = null;
	protected String ubicacion = null;
	protected boolean disponibilidad = false;
	protected String idFondo = null;
	protected String fondo = null;
	protected String sistemaProductor = null;
	protected String idElementoCF = null;
	// Fecha de inicio del expediente
	protected String fechaini = null;
	// Fecha de finalización del expediente
	protected String fechafin = null;
	// Código de referencia
	protected String codReferencia = null;
	// Nombre de la serie
	protected String nombreSerie = null;
	// Fecha de entrega
	protected Date fentrega = null;
	protected int subtipo = 0;
	protected List rangos = null;
	protected int estadoDisponibilidad = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;
	protected String idMotivo = null;
	protected String descripcionUdocDeposito = null;
	protected String strNumSolicitud = null;
	protected String strFechaEntrega = null;
	protected String solicitante = null;

	public DetalleVO() {
		super();
	}

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
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
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
	 * @return Returns the festado.
	 */
	public Date getFestado() {
		return festado;
	}

	/**
	 * @param festado
	 *            The festado to set.
	 */
	public void setFestado(Date festado) {
		this.festado = festado;
	}

	/**
	 * @return Returns the ffinaluso.
	 */
	public Date getFfinaluso() {
		return ffinaluso;
	}

	/**
	 * @param ffinaluso
	 *            The ffinaluso to set.
	 */
	public void setFfinaluso(Date ffinaluso) {
		this.ffinaluso = ffinaluso;
	}

	/**
	 * @return Returns the finicialuso.
	 */
	public Date getFinicialuso() {
		return finicialuso;
	}

	/**
	 * @param finicialuso
	 *            The finicialuso to set.
	 */
	public void setFinicialuso(Date finicialuso) {
		this.finicialuso = finicialuso;
	}

	/**
	 * @return Returns the idsolicitud.
	 */
	public String getIdsolicitud() {
		return idsolicitud;
	}

	/**
	 * @param idsolicitud
	 *            The idsolicitud to set.
	 */
	public void setIdsolicitud(String idsolicitud) {
		this.idsolicitud = idsolicitud;
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
	 * @return Returns the informacion.
	 */
	public String getInformacion() {
		return informacion;
	}

	/**
	 * @param informacion
	 *            The informacion to set.
	 */
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	/**
	 * @return Returns the motivorechazo.
	 */
	public String getMotivorechazo() {
		return motivorechazo;
	}

	/**
	 * @param motivorechazo
	 *            The motivorechazo to set.
	 */
	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
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
	 * @return Returns the tiposolicitud.
	 */
	public int getTiposolicitud() {
		return tiposolicitud;
	}

	/**
	 * @param tiposolicitud
	 *            The tiposolicitud to set.
	 */
	public void setTiposolicitud(int tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}

	/**
	 * @return Returns the identificacion.
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion
	 *            The identificacion to set.
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return Returns the ubicacion.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion
	 *            The ubicacion to set.
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Está disponible el detalle del préstamo.
	 * 
	 * @return DEvuelve si el detalle del préstamo está disponible.
	 */
	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	/**
	 * Establece si el detalle del préstamo está o no disponible.
	 * 
	 * @param disponibilidad
	 */
	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getSistemaProductor() {
		return sistemaProductor;
	}

	public void setSistemaProductor(String sistemaProductor) {
		this.sistemaProductor = sistemaProductor;
	}

	public String getIdElementoCF() {
		return idElementoCF;
	}

	public void setIdElementoCF(String idElementoCF) {
		this.idElementoCF = idElementoCF;
	}

	/**
	 * @return the fechaini
	 */
	public String getFechaini() {
		return fechaini;
	}

	/**
	 * @param fechaini
	 *            the fechaini to set
	 */
	public void setFechaini(String fechaini) {
		this.fechaini = fechaini;
	}

	/**
	 * @return the fechafin
	 */
	public String getFechafin() {
		return fechafin;
	}

	/**
	 * @param fechafin
	 *            the fechafin to set
	 */
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public Date getFentrega() {
		return fentrega;
	}

	public void setFentrega(Date fentrega) {
		this.fentrega = fentrega;
	}

	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public List getRangos() {
		return rangos;
	}

	public void setRangos(List rangos) {
		this.rangos = rangos;
	}

	public boolean isSubtipoCaja() {
		return this.subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA;
	}

	public int getEstadoDisponibilidad() {
		return estadoDisponibilidad;
	}

	public void setEstadoDisponibilidad(int estadoDisponibilidad) {
		this.estadoDisponibilidad = estadoDisponibilidad;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public String getDescripcionUdocDeposito() {
		return descripcionUdocDeposito;
	}

	public void setDescripcionUdocDeposito(String descripcionUdocDeposito) {
		this.descripcionUdocDeposito = descripcionUdocDeposito;
	}

	public String getStrNumSolicitud() {
		return strNumSolicitud;
	}

	public void setStrNumSolicitud(String strNumSolicitud) {
		this.strNumSolicitud = strNumSolicitud;
	}

	public String getStrFechaEntrega() {
		return strFechaEntrega;
	}

	public void setStrFechaEntrega(String strFechaEntrega) {
		this.strFechaEntrega = strFechaEntrega;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public boolean isDenegado() {
		return this.estado == PrestamosConstants.ESTADO_DETALLE_DENEGADA;
	}
}