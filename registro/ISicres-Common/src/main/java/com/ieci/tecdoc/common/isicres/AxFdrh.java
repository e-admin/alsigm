//
// FileName: AxFdrh.java
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

public class AxFdrh extends AbstractAx implements Serializable{
	
	private int id;
	private int verStat;
	private int refCount;
	private int accessType;
	private int acsId;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	private int accrId;
	private Date accDate;
	private int acccount;
	
	public AxFdrh(){
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
	 * @return Returns the verStat.
	 */
	public int getVerStat() {
		return verStat;
	}

	/**
	 * @param verStat The verStat to set.
	 */
	public void setVerStat(int verStat) {
		this.verStat = verStat;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] verStat [");
		buffer.append(verStat);
		buffer.append("] RefCount [");
		buffer.append(refCount);
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
