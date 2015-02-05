//
// FileName: AxClfh.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author lmvicente 
 * @version 
 * @since 
 * @creationDate 01-mar-2004
 */

public class AxClfh extends AbstractAx implements Serializable {

	private int id;
	private int fdrId;
	private String name;
	private int type;
	private int parentId;
	private int info;
	private int stat;
	private int refCount;
	private String remarks;
	private int accessType;
	private int acsId;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	
	public AxClfh() {
	}
	
	/**
	 * @return Returns the accessType.
	 */
	public int getAccessType() {
		return accessType;
	}

	/**
	 * @param accessType The accessType to set.
	 */
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	/**
	 * @return Returns the acsId.
	 */
	public int getAcsId() {
		return acsId;
	}

	/**
	 * @param acsId The acsId to set.
	 */
	public void setAcsId(int acsId) {
		this.acsId = acsId;
	}

	/**
	 * @return Returns the crtnDate.
	 */
	public Date getCrtnDate() {
		return crtnDate;
	}

	/**
	 * @param crtnDate The crtnDate to set.
	 */
	public void setCrtnDate(Date crtnDate) {
		this.crtnDate = crtnDate;
	}

	/**
	 * @return Returns the crtrId.
	 */
	public int getCrtrId() {
		return crtrId;
	}

	/**
	 * @param crtrId The crtrId to set.
	 */
	public void setCrtrId(int crtrId) {
		this.crtrId = crtrId;
	}

	/**
	 * @return Returns the fdrId.
	 */
	public int getFdrId() {
		return fdrId;
	}

	/**
	 * @param fdrId The fdrId to set.
	 */
	public void setFdrId(int fdrId) {
		this.fdrId = fdrId;
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
	 * @return Returns the info.
	 */
	public int getInfo() {
		return info;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(int info) {
		this.info = info;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return Returns the refCount.
	 */
	public int getRefCount() {
		return refCount;
	}

	/**
	 * @param refCount The refCount to set.
	 */
	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the stat.
	 */
	public int getStat() {
		return stat;
	}

	/**
	 * @param stat The stat to set.
	 */
	public void setStat(int stat) {
		this.stat = stat;
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

	/**
	 * @return Returns the updDate.
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * @param updDate The updDate to set.
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * @return Returns the updrId.
	 */
	public int getUpdrId() {
		return updrId;
	}

	/**
	 * @param updrId The updrId to set.
	 */
	public void setUpdrId(int updrId) {
		this.updrId = updrId;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] FdrId [");
		buffer.append(fdrId);
		buffer.append("] Name [");
		buffer.append(name);
		buffer.append("] Type [");
		buffer.append(type);
		buffer.append("] ParentId [");
		buffer.append(parentId);
		buffer.append("] Info [");
		buffer.append(info);
		buffer.append("] Stat [");
		buffer.append(stat);
		buffer.append("] RefCount [");
		buffer.append(refCount);
		buffer.append("] Remarks [");
		buffer.append(remarks);
		buffer.append("] AccessType [");
		buffer.append(accessType);
		buffer.append("] AcsId [");
		buffer.append(acsId);
		buffer.append("] CrtrId [");
		buffer.append(crtrId);
		buffer.append("] CrtnDate [");
		buffer.append(crtnDate);
		buffer.append("] UpdrId [");
		buffer.append(updrId);
		buffer.append("] UpdDate [");
		buffer.append(updDate);
		buffer.append("]");
		return buffer.toString();
	}
	
}
