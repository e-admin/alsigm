//
// FileName: AxDochx.java
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

public class AxDochx extends AbstractAx implements Serializable {
	
	private int id;
	private int fdrId;
	private int archId;
	private String name;
	private int clfId;
	private int type;
	private String title;
	private String author;
	private String keyWords;
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
	private Date timeStamp;
	
	public AxDochx() {
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
	 * @return Returns the archId.
	 */
	public int getArchId() {
		return archId;
	}

	/**
	 * @param archId The archId to set.
	 */
	public void setArchId(int archId) {
		this.archId = archId;
	}

	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return Returns the clfId.
	 */
	public int getClfId() {
		return clfId;
	}

	/**
	 * @param clfId The clfId to set.
	 */
	public void setClfId(int clfId) {
		this.clfId = clfId;
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
	 * @return Returns the keyWords.
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * @param keyWords The keyWords to set.
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
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
	 * @return Returns the timeStamp.
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
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
		buffer.append("] ArchId [");
		buffer.append(archId);
		buffer.append("] Name [");
		buffer.append(name);
		buffer.append("] ClfId [");
		buffer.append(clfId);
		buffer.append("] Type [");
		buffer.append(type);
		buffer.append("] Title [");
		buffer.append(title);
		buffer.append("] Author [");
		buffer.append(author);
		buffer.append("] KeyWords [");
		buffer.append(keyWords);
		buffer.append("] Stat [");
		buffer.append(stat);
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
