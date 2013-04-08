package ieci.tdw.ispac.services.dto;

import java.io.Serializable;


/**
 * Información de un procedimiento.
 */
public class Procedimiento implements Serializable {
	
	private static final long serialVersionUID = 1387167663361324735L;

	/** Información básica del procedimiento. */
	private InfoBProcedimiento informacionBasica = new InfoBProcedimiento();
	
	/** Objeto del procedimiento. */
	private String objeto = null;
	
	/** Trámites del procedimiento. */
	private String tramites = null;
	
	/** Normativa del procedimiento. */
	private String normativa = null;
	
	/** Documentos básicos del procedimiento. */
	private String documentosBasicos = null;
	
	/** Órganos productores del procedimiento. */
	private OrganoProductor [] organosProductores = new OrganoProductor[0];
	
	
	/**
	 * Constructor.
	 */
	public Procedimiento() {
		super();
	}
	
	
	
	/**
	 * Obtiene los documentos básicos del procedimiento.
	 * @return Documentos básicos del procedimiento.
	 */
	public String getDocumentosBasicos() {
		return documentosBasicos;
	}
	
	
	/**
	 * Establece los documentos básicos del procedimiento.
	 * @param documentosBasicos Documentos básicos del procedimiento.
	 */
	public void setDocumentosBasicos(String documentosBasicos) {
		this.documentosBasicos = documentosBasicos;
	}
	
	
	/**
	 * Obtiene la información básica del procedimiento.
	 * @return Información básica del procedimiento.
	 */
	public InfoBProcedimiento getInformacionBasica() {
		return informacionBasica;
	}
	
	
	/**
	 * Establece la información básica del procedimiento.
	 * @param informacionBasica Información básica del procedimiento.
	 */
	public void setInformacionBasica(InfoBProcedimiento informacionBasica) {
		this.informacionBasica = informacionBasica;
	}
	
	
	/**
	 * Obtiene la normativa del procedimiento.
	 * @return Normativa del procedimiento.
	 */
	public String getNormativa() {
		return normativa;
	}
	
	
	/**
	 * Establece la normativa del procedimiento.
	 * @param normativa Normativa del procedimiento.
	 */
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}
	
	
	/**
	 * Obtiene el objeto del procedimiento.
	 * @return Objeto del procedimiento.
	 */
	public String getObjeto() {
		return objeto;
	}
	
	
	/**
	 * Establece el objeto del procedimiento.
	 * @param objeto Objeto del procedimiento.
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	
	
	/**
	 * Obtiene los órganos productores del procedimiento.
	 * @return Órganos productores del procedimiento.
	 */
	public OrganoProductor [] getOrganosProductores() {
		return organosProductores;
	}
	
	
	/**
	 * Establece los órganos productores del procedimiento.
	 * @param organosProductores Órganos productores del procedimiento.
	 */
	public void setOrganosProductores(OrganoProductor [] organosProductores) {
		this.organosProductores = organosProductores;
	}
	
	
	/**
	 * Obtiene los trámites del procedimiento.
	 * @return Trámites del procedimiento.
	 */
	public String getTramites() {
		return tramites;
	}
	
	
	/**
	 * Establece los trámites del procedimiento.
	 * @param tramites Trámites del procedimiento.
	 */
	public void setTramites(String tramites) {
		this.tramites = tramites;
	}

}