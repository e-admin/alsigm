//
// FileName: AxXnid.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * 
 * 
 * @author lmvicente 
 * @version 
 * @since 
 * @creationDate 01-mar-2004
 */

public class AxXnid extends AbstractAx implements Serializable {
	
	private int type;
	private int parentId;
	private int id;
	
	public AxXnid() {
	}
	
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the parentId.
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] Type [");
		buffer.append(type);
		buffer.append("] ParentId [");
		buffer.append(parentId);
		buffer.append("]");
		return buffer.toString();
	}
	
}
