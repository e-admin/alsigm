package docelectronicos.vos;

import util.TreeModelItem;

import common.vos.BaseVO;

public abstract class DocumentosTreeModelItemVO extends BaseVO implements
		TreeModelItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String itemId = null;
	protected String itemName = null;
	private DocumentosTreeModelItemVO parent = null;
	private int nodeType;

	/**
	 * Constructor.
	 */
	public DocumentosTreeModelItemVO(int nodeType) {
		this.nodeType = nodeType;
	}

	public abstract String getId();

	public abstract String getNombre();

	/**
	 * @return Returns the itemId.
	 */
	public Object getItemId() {
		return getId();
	}

	/**
	 * @return Returns the itemName.
	 */
	public String getItemName() {
		return getNombre();
	}

	/**
	 * @return Returns the parentElement.
	 */
	public TreeModelItem getParent() {
		return parent;
	}

	/**
	 * @return Returns the nodeType.
	 */
	public int getNodeType() {
		return nodeType;
	}

	/**
	 * @param parentElement
	 *            The parentElement to set.
	 */
	public void setParent(DocumentosTreeModelItemVO parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see util.TreeModelItem#getItemPath()
	 */
	public String getItemPath() {
		StringBuffer itemPath = new StringBuffer();
		if (parent != null)
			itemPath.append(((TreeModelItem) parent).getItemPath()).append("/");
		itemPath.append("item").append(this.getItemId());
		return itemPath.toString();
	}
}