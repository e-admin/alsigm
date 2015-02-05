package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;

public class BandejaEntradaItemVO extends BaseIntercambioRegistralVO{

	protected Long id;
	protected Long idLibro;
	protected Long idRegistro;
	protected String idIntercambioRegistral;
	protected Long idIntercambioInterno;

	protected String numeroRegistro;
	protected String numeroRegistroOriginal;
	protected Integer tipoLibro;

	protected String origen;
	protected String origenName;
	
	protected String codigoUnidadTramitacion;
	protected String nombreUnidadTramitacion;
	
	protected String codigoEntidadTramitacion;
	protected String nombreEntidadTramitacion;
	
	protected Date fechaRegistro;
	protected EstadoIntercambioRegistralEntradaEnumVO estado;
	protected Date fechaEstado;
	protected Date fechaIntercambioRegistral;
	protected DocumentacionFisicaIntercambioRegistralEnum documentacionFisicaIntercambioRegistral;
	
	protected String username; 
	
	protected String contactoOrigen;
	
	protected String numeroRegistroOrigen;
	
	protected String comentarios;
	
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}
	public Long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String getIdIntercambioRegistral() {
		return idIntercambioRegistral;
	}
	public void setIdIntercambioRegistral(String idIntercambioRegistral) {
		this.idIntercambioRegistral = idIntercambioRegistral;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public Integer getTipoLibro() {
		return tipoLibro;
	}
	public void setTipoLibro(Integer tipoLibro) {
		this.tipoLibro = tipoLibro;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getOrigenName() {
		return origenName;
	}
	public void setOrigenName(String origenName) {
		this.origenName = origenName;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public EstadoIntercambioRegistralEntradaEnumVO getEstado() {
		return estado;
	}
	public void setEstado(EstadoIntercambioRegistralEntradaEnumVO estado) {
		this.estado = estado;
	}
	public Date getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public Date getFechaIntercambioRegistral() {
		return fechaIntercambioRegistral;
	}
	public void setFechaIntercambioRegistral(Date fechaIntercambioRegistral) {
		this.fechaIntercambioRegistral = fechaIntercambioRegistral;
	}
	public String getNumeroRegistroOriginal() {
		return numeroRegistroOriginal;
	}
	public void setNumeroRegistroOriginal(String numeroRegistroOriginal) {
		this.numeroRegistroOriginal = numeroRegistroOriginal;
	}
	public DocumentacionFisicaIntercambioRegistralEnum getDocumentacionFisicaIntercambioRegistral() {
		return documentacionFisicaIntercambioRegistral;
	}
	public void setDocumentacionFisicaIntercambioRegistral(
			DocumentacionFisicaIntercambioRegistralEnum documentacionFisicaIntercambioRegistral) {
		this.documentacionFisicaIntercambioRegistral = documentacionFisicaIntercambioRegistral;
	}
	public Long getIdIntercambioInterno() {
		return idIntercambioInterno;
	}
	public void setIdIntercambioInterno(Long idIntercambioInterno) {
		this.idIntercambioInterno = idIntercambioInterno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getCodigoUnidadTramitacion() {
		return codigoUnidadTramitacion;
	}
	public void setCodigoUnidadTramitacion(String codigoUnidadTramitacion) {
		this.codigoUnidadTramitacion = codigoUnidadTramitacion;
	}
	public String getNombreUnidadTramitacion() {
		return nombreUnidadTramitacion;
	}
	public void setNombreUnidadTramitacion(String nombreUnidadTramitacion) {
		this.nombreUnidadTramitacion = nombreUnidadTramitacion;
	}
	public String getCodigoEntidadTramitacion() {
		return codigoEntidadTramitacion;
	}
	public void setCodigoEntidadTramitacion(String codigoEntidadTramitacion) {
		this.codigoEntidadTramitacion = codigoEntidadTramitacion;
	}
	public String getNombreEntidadTramitacion() {
		return nombreEntidadTramitacion;
	}
	public void setNombreEntidadTramitacion(String nombreEntidadTramitacion) {
		this.nombreEntidadTramitacion = nombreEntidadTramitacion;
	}
	public String getContactoOrigen() {
		return contactoOrigen;
	}
	public void setContactoOrigen(String contactoOrigen) {
		this.contactoOrigen = contactoOrigen;
	}
	public String getNumeroRegistroOrigen() {
		return numeroRegistroOrigen;
	}
	public void setNumeroRegistroOrigen(String numeroRegistroOrigen) {
		this.numeroRegistroOrigen = numeroRegistroOrigen;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


}
