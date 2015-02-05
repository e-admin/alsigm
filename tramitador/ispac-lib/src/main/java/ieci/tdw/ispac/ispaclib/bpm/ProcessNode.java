package ieci.tdw.ispac.ispaclib.bpm;

import java.io.Serializable;

/**
 * Clase para almacenar la información de un nodo.
 *
 */
public class ProcessNode implements Serializable {

	/*
	 * ======================================================================
	 * Constantes para los tipos de nodos 
	 * ======================================================================
	 */
	
	/** Nodo de tipo fase. */
	public static final int NODE_TYPE_STAGE		= 1;
	
	/** Nodo de tipo punto de sincronización. */
    public static final int NODE_TYPE_SYNCNODE	= 2;

	/* ====================================================================== */

    
	/** Identificador del nodo. */
	private String id = null;
	
	/** Identificador del nodo en SPAC. */
	private int spacNodeId = -1; 

	/** Nombre del nodo. */
	private String name = null;

	/** Identificador del responsable. */
	private String respId = null;

	/** 
	 * Tipo de nodo. 
	 * <ul>
	 * <li>NODE_TYPE_STAGE</li>
	 * <li>NODE_TYPE_SYNCNODE</li>
	 * </ul>
	 */
	protected int type = NODE_TYPE_STAGE;
	
	
	/**
	 * Constructor.
	 *
	 */
	public ProcessNode() {
		super();
	}

	/**
	 * Constructor.
	 * @param name Nombre del nodo.
	 * @param type Tipo de nodo.
	 */
	public ProcessNode(String name, int type) {
		this();
		setName(name);
		setType(type);
	}

	/**
	 * Constructor.
	 * @param type Tipo de nodo.
	 */
	public ProcessNode(int type) {
		this();
		setType(type);
	}

	/**
	 * Obtiene el identificador del nodo.
	 * @return Identificador del nodo.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del nodo.
	 * @param id Identificador del nodo.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del nodo.
	 * @return Nombre del nodo.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del nodo.
	 * @param name Nombre del nodo.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el identificador el responsable.
	 * @return Identificador el responsable.
	 */
	public String getRespId() {
		return respId;
	}

	/**
	 * Establece el identificador el responsable.
	 * @param respId Identificador el responsable.
	 */
	public void setRespId(String respId) {
		this.respId = respId;
	}

	/**
	 * Obtiene el identificador del nodo en SPAC.
	 * @return Identificador del nodo en SPAC.
	 */
	public int getSpacNodeId() {
		return spacNodeId;
	}

	/**
	 * Establece el identificador del nodo en SPAC.
	 * @param spacNodeId Identificador del nodo en SPAC.
	 */
	public void setSpacNodeId(int spacNodeId) {
		this.spacNodeId = spacNodeId;
	}

	/**
	 * Obtiene el tipo de nodo:
	 * <ul>
	 * <li>NODE_TYPE_STAGE</li>
	 * <li>NODE_TYPE_SYNCNODE</li>
	 * </ul>
	 * @return Tipo de nodo.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Establece el tipo de nodo:
	 * @param Tipo de nodo.
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		return new StringBuffer()
			.append("id=[").append(id).append("], ")
			.append("spacNodeId=[").append(spacNodeId).append("], ")
			.append("name=[").append(name).append("], ")
			.append("respId=[").append(respId).append("], ")
			.append("type=[").append(type).append("]")
			.toString();
	}
}
