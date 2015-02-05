package auditoria.vos;

import common.vos.BaseVO;

/**
 * VO que encapsula la información de los datos de una traza
 */
public class DatosTrazaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador de bd de los datos de la traza */
	private String id = null;
	/** Identificador de traza a la que van asociados los datos */
	private String idTraza = null;
	/** Contenido asociado a la informacion de los datos */
	private String contenido = null;
	/** Identificador del tipo de Objeto */
	private int tipoObjeto = 0;
	/** Nombre del objeto */
	private String objeto = null;
	/** Identificador en base de datos del objeto */
	private String idObjeto = null;

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getIdTraza() {
		return idTraza;
	}

	public void setIdTraza(String idPista) {
		this.idTraza = idPista;
	}

	public int getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(int idTipoObjeto) {
		this.tipoObjeto = idTipoObjeto;
	}
}
