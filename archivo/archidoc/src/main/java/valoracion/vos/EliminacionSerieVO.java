package valoracion.vos;

import java.util.Date;

import valoracion.ValoracionConstants;

import common.vos.BaseVO;

/**
 * Clase que encapsula la información de una eliminacion de serie
 */
public class EliminacionSerieVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String idValoracion = null;
	private String tituloValoracion = null;
	private String idSerie = null;
	private String tituloSerie = null;
	private String titulo = null;
	private int estado = 0;
	private Date fechaEstado = null;
	private String motivoRechazo = null;
	private String notas = null;
	private int maxAnosVigencia = 0;
	private String condicionBusqueda = null;
	private int tipoEliminacion = 0;
	private String seleccionUdoc = null;
	private Date fechaEjecucion = null;
	private Date fechaAprobacion = null;
	private Date fechaDestruccion = null;
	private Date fechaFinalizacion = null;

	private boolean puedeSerEditada = false;
	private boolean puedeSerEliminada = false;
	private boolean permitidaSolicitudAprobacionEliminacion = false;
	private boolean permitidaAprobacionEliminacion = false;
	private boolean permitidaEjecucionEliminacion = false;
	private boolean permitidaDestruccionFisica = false;
	private boolean permitidaFinalizacion = false;
	private boolean permitidaAprobacionDirecta = false;

	private boolean uisCompletas = false;

	private ResumenUinstEliminacionVO resumenUInst = new ResumenUinstEliminacionVO();

	private String idArchivo = null;

	public String getCondicionBusqueda() {
		return condicionBusqueda;
	}

	public void setCondicionBusqueda(String condicionBusqueda) {
		this.condicionBusqueda = condicionBusqueda;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaDestruccion() {
		return fechaDestruccion;
	}

	public void setFechaDestruccion(Date fechaDestruccion) {
		this.fechaDestruccion = fechaDestruccion;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(String idValoracion) {
		this.idValoracion = idValoracion;
	}

	public int getMaxAnosVigencia() {
		return maxAnosVigencia;
	}

	public void setMaxAnosVigencia(int maxAnoVigencia) {
		this.maxAnosVigencia = maxAnoVigencia;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getSeleccionUdoc() {
		return seleccionUdoc;
	}

	public void setSeleccionUdoc(String seleccionUDOC) {
		this.seleccionUdoc = seleccionUDOC;
	}

	public int getTipoEliminacion() {
		return tipoEliminacion;
	}

	public void setTipoEliminacion(int tipoEliminacion) {
		this.tipoEliminacion = tipoEliminacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isAbierta() {
		return estado == ValoracionConstants.ESTADO_ELIMINACION_ABIERTA;
	}

	public boolean isRechazada() {
		return estado == ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA;
	}

	public boolean getPuedeSerEditada() {
		return puedeSerEditada;
	}

	public void setPuedeSerEditada(boolean puedeSerEditada) {
		this.puedeSerEditada = puedeSerEditada;
	}

	public boolean getPuedeSerEliminada() {
		return puedeSerEliminada;
	}

	public void setPuedeSerEliminada(boolean puedeSerEliminada) {
		this.puedeSerEliminada = puedeSerEliminada;
	}

	public boolean getTieneUdocs() {
		return seleccionUdoc.equalsIgnoreCase("N") ? true : false;
	}

	public boolean getPermitidaAprobacionEliminacion() {
		return permitidaAprobacionEliminacion;
	}

	public void setPermitidaAprobacionEliminacion(
			boolean permitidaAprobacionEliminacion) {
		this.permitidaAprobacionEliminacion = permitidaAprobacionEliminacion;
	}

	public boolean getPermitidaSolicitudAprobacionEliminacion() {
		return permitidaSolicitudAprobacionEliminacion;
	}

	public void setPermitidaSolicitudAprobacionEliminacion(
			boolean permitidaSolicitudAprobacionEliminacion) {
		this.permitidaSolicitudAprobacionEliminacion = permitidaSolicitudAprobacionEliminacion;
	}

	public boolean getPermitidaEjecucionEliminacion() {
		return permitidaEjecucionEliminacion;
	}

	public void setPermitidaEjecucionEliminacion(
			boolean permitidaEjecucionEliminacion) {
		this.permitidaEjecucionEliminacion = permitidaEjecucionEliminacion;
	}

	public boolean getPermitidaDestruccionFisica() {
		return permitidaDestruccionFisica;
	}

	public void setPermitidaDestruccionFisica(boolean permitidaDestruccionFisica) {
		this.permitidaDestruccionFisica = permitidaDestruccionFisica;
	}

	public boolean getPermitidaFinalizacion() {
		return permitidaFinalizacion;
	}

	public void setPermitidaFinalizacion(boolean permitidaFinalizacion) {
		this.permitidaFinalizacion = permitidaFinalizacion;
	}

	public boolean getRechazada() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA)
			resultado = true;

		return resultado;
	}

	public boolean getPendienteAprobar() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR)
			resultado = true;

		return resultado;
	}

	public boolean getAprobada() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_APROBADA)
			resultado = true;

		return resultado;
	}

	public boolean getPendienteDestruccion() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION)
			resultado = true;

		return resultado;
	}

	public boolean getDestruida() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA)
			resultado = true;

		return resultado;
	}

	public boolean getFinalizada() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA)
			resultado = true;

		return resultado;
	}

	public boolean getEnDestruccion() {
		boolean resultado = false;

		if (this.estado == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION)
			resultado = true;

		return resultado;
	}

	public boolean getPermitidaAprobacionDirecta() {
		return permitidaAprobacionDirecta;
	}

	public void setPermitidaAprobacionDirecta(boolean permitidaAprobacionDirecta) {
		this.permitidaAprobacionDirecta = permitidaAprobacionDirecta;
	}

	/**
	 * @return Returns the tituloSerie.
	 */
	public String getTituloSerie() {
		return tituloSerie;
	}

	/**
	 * @param tituloSerie
	 *            The tituloSerie to set.
	 */
	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	/**
	 * @return Returns the tituloValoracion.
	 */
	public String getTituloValoracion() {
		return tituloValoracion;
	}

	/**
	 * @param tituloValoracion
	 *            The tituloValoracion to set.
	 */
	public void setTituloValoracion(String tituloValoracion) {
		this.tituloValoracion = tituloValoracion;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setUisCompletas(boolean uisCompletas) {
		this.uisCompletas = uisCompletas;
	}

	public boolean isUisCompletas() {
		return uisCompletas;
	}

	/**
	 * Comprueba que el estado sea: APROBADA = 4; PENDIENTE DESTRUCCION = 5;
	 * DESTRUCCION REALIZADA = 6; PENDIENTE FINALIZACION = 7; ELIMINACION
	 * FINALIZADA = 8;
	 * 
	 * @return
	 */
	public boolean isAutorizada() {
		return (estado >= ValoracionConstants.ESTADO_ELIMINACION_APROBADA);
	}

	public void setResumenUInst(ResumenUinstEliminacionVO resumenUInst) {
		this.resumenUInst = resumenUInst;
	}

	public ResumenUinstEliminacionVO getResumenUInst() {
		return resumenUInst;
	}

}
