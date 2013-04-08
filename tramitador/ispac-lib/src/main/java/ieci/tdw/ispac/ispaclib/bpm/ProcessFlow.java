package ieci.tdw.ispac.ispaclib.bpm;

import java.io.Serializable;

/**
 * Clase para almacenar la información de un flujo.
 *
 */
public class ProcessFlow implements Serializable {

	/** Identificador del flujo. */
	private String id = null;

	/** Identificador del flujo en SPAC. */
	private int spacFlowId = -1; 

	/** Identificador del nodo origen. */
	private String startNodeId = null;
	
	/** Identificador del nodo destino. */
	private String endNodeId = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public ProcessFlow() {
		super();
	}

	/**
	 * Constructor.
	 *
	 */
	public ProcessFlow(String processModelId, String startNodeId, 
			String endNodeId) {
		this();
		setStartNodeId(startNodeId);
		setEndNodeId(endNodeId);
	}

	/**
	 * Obtiene el identificador del flujo.
	 * @return Identificador del flujo.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del flujo.
	 * @param id Identificador del flujo.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del flujo en SPAC.
	 * @return Identificador del flujo en SPAC.
	 */
	public int getSpacFlowId() {
		return spacFlowId;
	}

	/**
	 * Establece el identificador del flujo en SPAC.
	 * @param spacFlowId Identificador del flujo en SPAC.
	 */
	public void setSpacFlowId(int spacFlowId) {
		this.spacFlowId = spacFlowId;
	}

	/**
	 * Obtiene el identificador del nodo de origen del flujo.
	 * @return identificador del nodo de origen del flujo.
	 */
	public String getStartNodeId() {
		return startNodeId;
	}

	/**
	 * Establece el identificador del nodo de origen del flujo.
	 * @return nodeId identificador del nodo de origen del flujo.
	 */
	public void setStartNodeId(String nodeId) {
		this.startNodeId = nodeId;
	}

	/**
	 * Obtiene el identificador del nodo de destino del flujo.
	 * @return identificador del nodo de destino del flujo.
	 */
	public String getEndNodeId() {
		return endNodeId;
	}
	
	/**
	 * Establece el identificador del nodo de destino del flujo.
	 * @return nodeId identificador del nodo de destino del flujo.
	 */
	public void setEndNodeId(String nodeId) {
		this.endNodeId = nodeId;
	}

	public String toString() {
		return new StringBuffer()
			.append("id=[").append(id).append("], ")
			.append("startNodeId=[").append(startNodeId).append("], ")
			.append("endNodeId=[").append(endNodeId).append("]")
			.toString();
	}

}
