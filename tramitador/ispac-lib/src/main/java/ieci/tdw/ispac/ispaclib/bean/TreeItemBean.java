package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;

import java.util.ArrayList;
import java.util.List;

public class TreeItemBean extends ItemBean {
	
	protected List children = new ArrayList();
	
	public TreeItemBean() {
		super();
	}

	public TreeItemBean(IItem item) throws ISPACException {
		super(item);
	}

	/**
	 * @return Returns the children.
	 */
	public List getChildren() {
		return children;
	}

	/**
	 * @param children The children to set.
	 */
	public void setChildren(List children) {
		this.children = children;
	}
	
	public void addChild(ItemBean item){
		children.add(item);
	}
	public boolean hasChildren(){
		return !children.isEmpty();
	}
	
}
