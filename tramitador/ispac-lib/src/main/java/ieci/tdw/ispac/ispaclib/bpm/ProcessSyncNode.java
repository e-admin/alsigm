package ieci.tdw.ispac.ispaclib.bpm;

public class ProcessSyncNode extends ProcessNode {

	private int syncNodeType;
	
	/**
	 * Constructor.
	 *
	 */
	public ProcessSyncNode() {
		super();
		type = NODE_TYPE_SYNCNODE;
	}

	/**
	 * Constructor.
	 * @param name Nombre del nodo.
	 * @param type Tipo de nodo.
	 */
	public ProcessSyncNode(String name, int type) {
		super(name, type);
	}

	/**
	 * Constructor.
	 * @param type Tipo de nodo.
	 */
	public ProcessSyncNode(int type) {
		super(type);
	}    
    
	
	public int getSyncNodeType() {
		return syncNodeType;
	}

	public void setSyncNodeType(int syncNodeType) {
		this.syncNodeType = syncNodeType;
	}

	public String toString() {
		return new StringBuffer(super.toString()).append("syncNodetype=[").append(syncNodeType).append("]").toString();
	}
}