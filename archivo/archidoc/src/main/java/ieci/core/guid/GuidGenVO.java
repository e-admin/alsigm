package ieci.core.guid;

public class GuidGenVO {

	/**
	 *
	 */
	private static final long serialVersionUID = -1153170940841035601L;
	private String macNode = "000000000000";
	private int lastProcessId = 0;

	public GuidGenVO(String macNode, int lastProcessId) {
		super();
		this.macNode = macNode;
		this.lastProcessId = lastProcessId;
	}

	public GuidGenVO() {
		macNode = "000000000000";
		lastProcessId = 0;
	}

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public String getMacNode() {
		return macNode;
	}

	/**
	 * @param macNode
	 *            the macNode to set
	 */
	public void setMacNode(String macNode) {
		this.macNode = macNode;
	}

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public int getLastProcessId() {
		return lastProcessId;
	}

	/**
	 * @param lastProcessId
	 *            the lastProcessId to set
	 */
	public void setLastProcessId(int lastProcessId) {
		this.lastProcessId = lastProcessId;
	}
}
