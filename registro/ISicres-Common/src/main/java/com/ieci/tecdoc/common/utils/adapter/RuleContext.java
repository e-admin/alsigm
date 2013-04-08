package com.ieci.tecdoc.common.utils.adapter;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.isicres.AxSf;

public class RuleContext {

	private String sessionId;
	private String usuario;
	private Integer id;
	private Integer oficina;
	private Integer libro;
	private Integer registro;
	private String eventId;
	private String entidad;
	private String message;
	private Integer typeDest;
	private Integer idDest;
	private AxSf axsf;
	private Idocarchdet idocarchdet;

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the libro
	 */
	public Integer getLibro() {
		return libro;
	}

	/**
	 * @param libro
	 *            the libro to set
	 */
	public void setLibro(Integer libro) {
		this.libro = libro;
	}

	/**
	 * @return the oficina
	 */
	public Integer getOficina() {
		return oficina;
	}

	/**
	 * @param oficina
	 *            the oficina to set
	 */
	public void setOficina(Integer oficina) {
		this.oficina = oficina;
	}

	/**
	 * @return the registro
	 */
	public Integer getRegistro() {
		return registro;
	}

	/**
	 * @param registro
	 *            the registro to set
	 */
	public void setRegistro(Integer registro) {
		this.registro = registro;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idDest
	 */
	public Integer getIdDest() {
		return idDest;
	}

	/**
	 * @param idDest
	 *            the idDest to set
	 */
	public void setIdDest(Integer idDest) {
		this.idDest = idDest;
	}

	/**
	 * @return the typeDest
	 */
	public Integer getTypeDest() {
		return typeDest;
	}

	/**
	 * @param typeDest
	 *            the typeDest to set
	 */
	public void setTypeDest(Integer typeDest) {
		this.typeDest = typeDest;
	}

	/**
	 * @return the axsf
	 */
	public AxSf getAxsf() {
		return axsf;
	}

	/**
	 * @param axsf
	 *            the axsf to set
	 */
	public void setAxsf(AxSf axsf) {
		this.axsf = axsf;
	}

	public Idocarchdet getIdocarchdet() {
		return idocarchdet;
	}

	public void setIdocarchdet(Idocarchdet idocarchdet) {
		this.idocarchdet = idocarchdet;
	}

}
