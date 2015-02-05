package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;

public class IntercambioRegistralEntradaVO extends BaseIntercambioRegistralVO {

	protected Long id;

	protected Long idLibro;

	protected Long idRegistro;
	
	protected Integer idOfic;

	public Integer getIdOfic() {
		return idOfic;
	}

	public void setIdOfic(Integer idOfic) {
		this.idOfic = idOfic;
	}

	protected Date fechaEstado;

	protected Date fechaIntercambio;

	protected EstadoIntercambioRegistralEntradaEnumVO estado;

	protected String idIntercambioRegistral;

	protected Long idIntercambioInterno;

	protected Integer tipoOrigen;

	protected String username;
	
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
	
	protected String contactoOrigen;
	
	protected String numeroRegistroOrigen;
	
	protected String comentarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public EstadoIntercambioRegistralEntradaEnumVO getEstado() {
		return estado;
	}

	public void setEstado(EstadoIntercambioRegistralEntradaEnumVO estado) {
		this.estado = estado;
	}



	public Integer getTipoOrigen() {
		return tipoOrigen;
	}

	public void setTipoOrigen(Integer tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public int getIdEstado()
	{
		int id = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE;
		if(estado!=null)
		{
			id=estado.getValue();
		}
		return id;
	}

	public Date getFechaIntercambio() {
		return fechaIntercambio;
	}

	public void setFechaIntercambio(Date fechaIntercambio) {
		this.fechaIntercambio = fechaIntercambio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public void setIdIntercambioInterno(String idIntercambioInterno) {
		this.idIntercambioInterno = Long.parseLong(idIntercambioInterno);
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

	public String getContactoOrigen() {
		return contactoOrigen;
	}

	public String getNumeroRegistroOrigen() {
		return numeroRegistroOrigen;
	}

	public void setContactoOrigen(String contactoOrigen) {
		this.contactoOrigen = contactoOrigen;
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

	public Long getIdLibro() {
		return idLibro;
	}

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}


}
