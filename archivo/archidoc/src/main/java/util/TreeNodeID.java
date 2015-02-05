package util;

/**
 * Identificador de TreeNode
 * 
 * @see TreeNode
 * 
 */
public class TreeNodeID {
	String nodeId;

	public TreeNodeID(String id) {
		this.nodeId = id;
	}

	public boolean equals(Object oObject) {
		return this.nodeId.equals(((TreeNode) oObject).getNodeId());
	}
}
