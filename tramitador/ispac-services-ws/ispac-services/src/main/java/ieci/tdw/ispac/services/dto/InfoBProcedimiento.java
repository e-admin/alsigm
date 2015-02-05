package ieci.tdw.ispac.services.dto;

import java.io.Serializable;

/**
 * Información básica de un procedimiento.
 */
public class InfoBProcedimiento implements Serializable {

	private static final long serialVersionUID = 8810130514599792310L;

	/*
	 *  Tipos de procedimientos
	 */
	
	public static final int ALL_PROCEDURES			= 1;
	public static final int ONLY_AUTOMATIZED 		= 2;
	public static final int ONLY_NOT_AUTOMATIZED	= 3;
	
	
	/** Identificador del procedimiento. */
	String id = null;

	/** Código del procedimiento. */
	String codigo = null;

	/** Nombre del procedimiento. */
	String nombre = null;

	/** Código del sistema productor. */
	String codSistProductor = null;

	/** Nombre del sistema productor. */
	String nombreSistProductor = null;

	/**
	 * Constructor.
	 */
	public InfoBProcedimiento() {
		super();
	}

	/**
	 * Obtiene el código del procedimiento.
	 * @return Código del procedimiento.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el código del procedimiento.
	 * @param codigo Código del procedimiento.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el código del sistema productor.
	 * @return Código del sistema productor.
	 */
	public String getCodSistProductor() {
		return codSistProductor;
	}

	/**
	 * Establece el código del sistema productor.
	 * @param codSistProductor Código del sistema productor.
	 */
	public void setCodSistProductor(String codSistProductor) {
		this.codSistProductor = codSistProductor;
	}

	/**
	 * Obtiene el identificador del procedimiento.
	 * @return Identificador del procedimiento.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del procedimiento.
	 * @param id Identificador del procedimiento.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del procedimiento.
	 * @return Nombre del procedimiento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del procedimiento.
	 * @param nombre Nombre del procedimiento.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el nombre del sistema productor.
	 * @return Nombre del sistema productor.
	 */
	public String getNombreSistProductor() {
		return nombreSistProductor;
	}

	/**
	 * Establece el nombre del sistema productor.
	 * @param nombreSistProductor Nombre del sistema productor.
	 */
	public void setNombreSistProductor(String nombreSistProductor) {
		this.nombreSistProductor = nombreSistProductor;
	}

}
