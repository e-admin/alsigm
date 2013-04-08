package ieci.tdw.ispac.services.ws.server.dto;

import java.io.Serializable;

/**
 * Información de un órgano.
 */
public class Organo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Identificador del órgano. */
	public String id = null;

	/** Nombre del órgano. */
	public String nombre = null;

	/** Código del órgano. */
	public String codigo = null;

	/** Nivel jerárquico al que pertenece el órgano. */
	public int nivel = 0;

	/** Identificador del órgano padre. */
	public String idPadre = null;

	/**
	 * Constructor.
	 */
	public Organo() {
		super();
	}

	/**
	 * Obtiene el identificador del órgano. 
	 * @return Identificador del órgano.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene el nombre del órgano. 
	 * @return Nombre del órgano.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene el código del órgano.
	 * @return Código del órgano.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Obtiene el nivel jerárquico al que pertenece el órgano. 
	 * @return Nivel jerárquico.
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Obtiene el identificador del órgano padre al que pertenece el órgano. 
	 * @return Identificador del órgano padre.
	 */
	public String getIdPadre() {
		return idPadre;
	}

	/**
	 * Establece el código del órgano.
	 * @param codigo Código del órgano.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Establece el identificador del órgano.
	 * @param id Identificador del órgano.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Establece el identificador del órgano padre.
	 * @param idPadre Identificador del órgano padre.
	 */
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * Establece el nivel jerárquico al que pertenece el órgano.
	 * @param nivel Nivel jerárquico al que pertenece el órgano.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * Establece el nombre del órgano.
	 * @param nombre Nombre del órgano.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
