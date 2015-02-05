//
// FileName: AxPagehx.java
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

public class AxPagehx extends AbstractAx implements Serializable {
	
	private int id;
	private int fdrId;
	private String name;
	private int sortOrder;
	private int docId;
	private int fileId;
	private int volId;
	private String loc;
	private int annId;
	private int stat;
	private int refCount;
	private String remarks;
	private int accessType;
	private int acsId;
	private int crtrId; 
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	private int accrId;
	private Date accDate;
	private int acccount;

	public AxPagehx(){
	}
	
	/**
	 * @return Returns the accDate.
	 */
	public Date getAccDate() {
		return accDate;
	}

	/**
	 * @param accDate The accDate to set.
	 */
	public void setAccDate(Date accDate) {
		this.accDate = accDate;
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
	 * @return Returns the account.
	 */
	public int getAcccount() {
		return acccount;
	}

	/**
	 * @param account The account to set.
	 */
	public void setAcccount(int acccount) {
		this.acccount = acccount;
	}

	/**
	 * @return Returns the accrId.
	 */
	public int getAccrId() {
		return accrId;
	}

	/**
	 * @param accrId The accrId to set.
	 */
	public void setAccrId(int accrId) {
		this.accrId = accrId;
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
	 * @return Returns the annId.
	 */
	public int getAnnId() {
		return annId;
	}

	/**
	 * @param annId The annId to set.
	 */
	public void setAnnId(int annId) {
		this.annId = annId;
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
	 * @return Returns the docId.
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId The docId to set.
	 */
	public void setDocId(int docId) {
		this.docId = docId;
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
	 * @return Returns the fileId.
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * @param fileId The fileId to set.
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
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
	 * @return Returns the loc.
	 */
	public String getLoc() {
		return loc;
	}

	/**
	 * @param loc The loc to set.
	 */
	public void setLoc(String loc) {
		this.loc = loc;
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
	 * @return Returns the sortOrder.
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder The sortOrder to set.
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

	/**
	 * @return Returns the volId.
	 */
	public int getVolId() {
		return volId;
	}

	/**
	 * @param volId The volId to set.
	 */
	public void setVolId(int volId) {
		this.volId = volId;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] FdrId [");
		buffer.append(fdrId);
		buffer.append("] Name [");
		buffer.append(name);
		buffer.append("] SortOrder [");
		buffer.append(sortOrder);
		buffer.append("] DocId [");
		buffer.append(docId);
		buffer.append("] FileId [");
		buffer.append(fileId);
		buffer.append("] VolId [");
		buffer.append(volId);
		buffer.append("] Loc [");
		buffer.append(loc);
		buffer.append("] AnnId [");
		buffer.append(annId);
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
		buffer.append("] AccrId [");
		buffer.append(accrId);
		buffer.append("] AccDate [");
		buffer.append(accDate);
		buffer.append("] Account [");
		buffer.append(acccount);
		buffer.append("]");
		return buffer.toString();
	}
	
}
