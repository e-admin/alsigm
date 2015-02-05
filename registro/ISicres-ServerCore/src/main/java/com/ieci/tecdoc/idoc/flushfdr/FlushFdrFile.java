package com.ieci.tecdoc.idoc.flushfdr;

import java.io.Serializable;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;

/*
 * @author JCEBRIEN
 * @creationDate 10-sep-2004 12:48:04
 * @version
 * @since
 */
public class FlushFdrFile implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int order = 0;
	private String fileNameFis = null;
	private String fileNameLog = null;
	private byte[] buffer = null;
	private String extension = null;

	// BookTypeConf
	private int code;
	private float resX;
	private float resY;

	// Extra
	private String fileID;
	private String pageID;

	// ///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return Returns the extension.
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            The extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return Returns the buffer.
	 */
	public byte[] getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer
	 *            The buffer to set.
	 */
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * @return Returns the fileNameFis.
	 */
	public String getFileNameFis() {
		return fileNameFis;
	}

	/**
	 * @param fileNameFis
	 *            The fileNameFis to set.
	 */
	public void setFileNameFis(String fileNameFis) {
		this.fileNameFis = fileNameFis;
	}

	/**
	 * @return Returns the fileNameLog.
	 */
	public String getFileNameLog() {
		return fileNameLog;
	}

	/**
	 * @param fileNameLog
	 *            The fileNameLog to set.
	 */
	public void setFileNameLog(String fileNameLog) {
		this.fileNameLog = fileNameLog;
	}

	/**
	 * @return Returns the order.
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            The order to set.
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return el fileID
	 */
	public String getFileID() {
		return fileID;
	}

	/**
	 * @param fileID
	 *            el fileID a fijar
	 */
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	/**
	 * @return el pageID
	 */
	public String getPageID() {
		return pageID;
	}

	/**
	 * @param pageID
	 *            el pageID a fijar
	 */
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

	/**
	 * toString methode: creates a String representation of the object
	 * 
	 * @return the String representation
	 * @author info.vancauwenberge.tostring plugin
	 * 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FlushFdrFile[");
		buffer.append("order = ").append(order);
		buffer.append(", fileNameFis = ").append(fileNameFis);
		buffer.append(", fileNameLog = ").append(fileNameLog);
		// buffer.append(", buffer = ").append(buffer);
		buffer.append(", fileID = ").append(fileID);
		buffer.append(", pageID = ").append(pageID);
		buffer.append("]");
		return buffer.toString();
	}

	public void loadFile() throws Exception {
		FileSeekableStream is = null;

		try {
			if (extension.toUpperCase().equals("TIF")
					|| extension.toUpperCase().equals("TIFF")) {
				is = new FileSeekableStream(fileNameFis);
				TIFFDirectory td = new TIFFDirectory(is, 0);

				// ver resolucion de imagen
				resX = td.getFieldAsFloat(TIFFImageDecoder.TIFF_X_RESOLUTION);
				resY = td.getFieldAsFloat(TIFFImageDecoder.TIFF_Y_RESOLUTION);

				// ver tipo de compresion
				code = (int) td
						.getFieldAsLong(TIFFImageDecoder.TIFF_COMPRESSION);

			}

		} finally {

			if (is != null) {
				is.close();
			}
		}

	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return Returns the resX.
	 */
	public float getResX() {
		return resX;
	}

	/**
	 * @param resX
	 *            The resX to set.
	 */
	public void setResX(float resX) {
		this.resX = resX;
	}

	/**
	 * @return Returns the resY.
	 */
	public float getResY() {
		return resY;
	}

	/**
	 * @param resY
	 *            The resY to set.
	 */
	public void setResY(float resY) {
		this.resY = resY;
	}
}
