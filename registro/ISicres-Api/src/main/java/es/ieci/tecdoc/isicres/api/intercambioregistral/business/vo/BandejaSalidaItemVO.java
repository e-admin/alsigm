package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;


/**
 * Clase que representa los elementos de la vista de los resultados de la bandeja de salida de distribucion
 *
 */
public class BandejaSalidaItemVO extends BaseIntercambioRegistralVO {

	protected Long id;
	protected Long idLibro;
	protected Long idRegistro;
	protected String idIntercambioRegistral;
	protected Long idIntercambioInterno;
	protected Integer idOfic;

	protected String numeroRegistro;
	protected Integer tipoLibro;

	/**
	 * Codigo de entidad al que ha sido enviado el intercambio
	 */
	protected String codeEntity;
	protected String nameEntity;

	/**
	 * Codigo de unidad de tramitación al que ha sido enviado el intercambio (si procede)
	 */
	protected String codeTramunit;
	protected String nameTramunit;
	protected Date fechaRegistro;
	protected EstadoIntercambioRegistralSalidaEnumVO estado;
	protected Date fechaEstado;
	protected Date fechaIntercambioRegistral;

	protected EstadoRegistroEnumVO estadoRegistro;

	protected String comentarios;

	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public EstadoIntercambioRegistralSalidaEnumVO getEstado() {
		return estado;
	}
	public void setEstado(EstadoIntercambioRegistralSalidaEnumVO estado) {
		this.estado = estado;
	}
	public Date getFechaIntercambioRegistral() {
		return fechaIntercambioRegistral;
	}
	public void setFechaIntercambioRegistral(Date fechaIntercambioRegistral) {
		this.fechaIntercambioRegistral = fechaIntercambioRegistral;
	}
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

	public Integer getTipoLibro() {
		return tipoLibro;
	}
	public void setTipoLibro(Integer tipoLibro) {
		this.tipoLibro = tipoLibro;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public EstadoRegistroEnumVO getEstadoRegistro() {
		return estadoRegistro;
	}
	public void setEstadoRegistro(EstadoRegistroEnumVO estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}


	public Integer getIdOfic() {
		return idOfic;
	}
	public void setIdOfic(Integer idOfic) {
		this.idOfic = idOfic;
	}
	public String getIdIntercambioRegistral() {
		return idIntercambioRegistral;
	}
	public void setIdIntercambioRegistral(String idIntercambioRegistral) {
		this.idIntercambioRegistral = idIntercambioRegistral;
	}
	public Long getIdIntercambioInterno() {
		return idIntercambioInterno;
	}
	public void setIdIntercambioInterno(Long idIntercambioInterno) {
		this.idIntercambioInterno = idIntercambioInterno;
	}
	public String getCodeEntity() {
		return codeEntity;
	}
	public void setCodeEntity(String codeEntity) {
		this.codeEntity = codeEntity;
	}
	public String getNameEntity() {
		return nameEntity;
	}
	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}
	public String getCodeTramunit() {
		return codeTramunit;
	}
	public void setCodeTramunit(String codeTramunit) {
		this.codeTramunit = codeTramunit;
	}
	public String getNameTramunit() {
		return nameTramunit;
	}
	public void setNameTramunit(String nameTramunit) {
		this.nameTramunit = nameTramunit;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
