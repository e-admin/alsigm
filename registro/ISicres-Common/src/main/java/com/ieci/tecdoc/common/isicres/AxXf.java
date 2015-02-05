//
// FileName: AxXf.java
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

public class AxXf extends AbstractAx implements Serializable{

	public static int DEFAULT_AXXF_IN_COMMENT_FIELD = 18;
	public static int DEFAULT_AXXF_OUT_COMMENT_FIELD = 14;
	
	private int id;
	private int fdrId;
	private int fldId;
	private String text;
	private Date timeStamp;
	
	public AxXf(){
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
	 * @return Returns the fldId.
	 */
	public int getFldId() {
		return fldId;
	}

	/**
	 * @param fldId The fldId to set.
	 */
	public void setFldId(int fldId) {
		this.fldId = fldId;
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
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
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

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] FdrId [");
		buffer.append(fdrId);
		buffer.append("] FdrId [");
		buffer.append(fdrId);
		buffer.append("] Text [");
		buffer.append(text);
		buffer.append("] TimeStamp [");
		buffer.append(timeStamp);
		buffer.append("]");
		return buffer.toString();
	}
	
}
