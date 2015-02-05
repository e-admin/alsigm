package se.procedimientos;

import java.util.List;

public class ProcedimientoImpl implements IProcedimiento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	InfoBProcedimiento infoProcedimiento = null;
	String objeto;
	String tramites;
	String normativa;
	String documentosBasicos;

	List productores = null;

	public ProcedimientoImpl() {
	}

	public ProcedimientoImpl(InfoBProcedimiento infoProcedimiento,
			String objeto, String tramites, String normativa, String docbasicos) {
		super();
		this.infoProcedimiento = infoProcedimiento;
		this.objeto = objeto;
		this.tramites = tramites;
		this.normativa = normativa;
		this.documentosBasicos = docbasicos;
	}

	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public void setTramites(String tramites) {
		this.tramites = tramites;
	}

	/**
	 * Devuelve la información básica de un procedimiento.
	 * 
	 * @return información básica de un procedimiento.
	 */
	public InfoBProcedimiento getInformacionBasica() {
		return this.infoProcedimiento;
	}

	/**
	 * Establece la información básica de un procedimiento.
	 * 
	 * @param info
	 *            Información básica de un procedimiento.
	 */
	public void setInformacionBasica(InfoBProcedimiento info) {
		this.infoProcedimiento = info;
	}

	/**
	 * Devuelve el objeto del procedimiento.
	 * 
	 * @return Objeto del procedimiento.
	 */
	public String getObjeto() {
		return this.objeto;
	}

	/**
	 * Devuelve los trámites de un procedimiento.
	 * 
	 * @return Trámites de un procedimiento.
	 */
	public String getTramites() {
		return this.tramites;
	}

	/**
	 * Devuelve la normativa de un procedimiento.
	 * 
	 * @return Normativa de un procedimiento.
	 */
	public String getNormativa() {
		return this.normativa;
	}

	/**
	 * Devuelve los documentos básicos de un procedimiento.
	 * 
	 * @return Documentos básicos de un procedimiento.
	 */
	public String getDocumentosBasicos() {
		return documentosBasicos;
	}

	/**
	 * Establece los documentos básicos de un procedimiento.
	 * 
	 * @param documentosBasicos
	 *            Documentos básicos de un procedimiento.
	 */
	public void setDocumentosBasicos(String documentosBasicos) {
		this.documentosBasicos = documentosBasicos;
	}

	/**
	 * Devuelve la lista de órganos productores del procedimiento.
	 * 
	 * @return Lista de órganos productores del procedimiento.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link IOrganoProductor}.
	 *         </p>
	 */
	public List getOrganosProductores() {
		return this.productores;
	}

	public void setOrganosProductores(List productores) {
		this.productores = productores;
	}
}