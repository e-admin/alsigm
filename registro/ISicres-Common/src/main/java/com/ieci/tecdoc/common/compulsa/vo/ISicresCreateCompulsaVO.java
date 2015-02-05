package com.ieci.tecdoc.common.compulsa.vo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class ISicresCreateCompulsaVO extends ISicresBaseCompulsaVO {

	protected float margen;
	protected float positionY;
	protected String font;
	protected String encoding;
	protected float fontSize;
	protected int band;
	protected float bandSize;

	protected List fileItems;

	protected String beginPath;
	protected String tempPath;
	protected String fondoPath;
	protected String datosPath;

	protected Date dateDataBaseServer;

	protected String folderNumber;

	protected String xadesFormat;
	protected String funcName;
	protected String certificate;
	protected String hash;

	/**
	 * InputStream del fichero a compulsar
	 */
	protected InputStream inputStream;
	
	/**
	 * Outputstream del fichero compulsado a partir del <code>inputStream</code>
	 */
	protected OutputStream outputStream;

	/**
	 * @return el margen
	 */
	public float getMargen() {
		return margen;
	}

	/**
	 * @param margen
	 *            el margen a fijar
	 */
	public void setMargen(float margen) {
		this.margen = margen;
	}

	/**
	 * @return el positionY
	 */
	public float getPositionY() {
		return positionY;
	}

	/**
	 * @param positionY
	 *            el positionY a fijar
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	/**
	 * @return el font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font
	 *            el font a fijar
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @return el encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 *            el encoding a fijar
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return el fontSize
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            el fontSize a fijar
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return el band
	 */
	public int getBand() {
		return band;
	}

	/**
	 * @param band
	 *            el band a fijar
	 */
	public void setBand(int band) {
		this.band = band;
	}

	/**
	 * @return el bandSize
	 */
	public float getBandSize() {
		return bandSize;
	}

	/**
	 * @param bandSize
	 *            el bandSize a fijar
	 */
	public void setBandSize(float bandSize) {
		this.bandSize = bandSize;
	}

	/**
	 * @return el fileItems
	 */
	public List getFileItems() {
		return fileItems;
	}

	/**
	 * @param fileItems
	 *            el fileItems a fijar
	 */
	public void setFileItems(List fileItems) {
		this.fileItems = fileItems;
	}

	/**
	 * @return el beginPath
	 */
	public String getBeginPath() {
		return beginPath;
	}

	/**
	 * @param beginPath
	 *            el beginPath a fijar
	 */
	public void setBeginPath(String beginPath) {
		this.beginPath = beginPath;
	}

	/**
	 * @return el tempPath
	 */
	public String getTempPath() {
		return tempPath;
	}

	/**
	 * @param tempPath
	 *            el tempPath a fijar
	 */
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	/**
	 * @return el fondoPath
	 */
	public String getFondoPath() {
		return fondoPath;
	}

	/**
	 * @param fondoPath
	 *            el fondoPath a fijar
	 */
	public void setFondoPath(String fondoPath) {
		this.fondoPath = fondoPath;
	}

	/**
	 * @return el datosPath
	 */
	public String getDatosPath() {
		return datosPath;
	}

	/**
	 * @param datosPath
	 *            el datosPath a fijar
	 */
	public void setDatosPath(String datosPath) {
		this.datosPath = datosPath;
	}

	/**
	 * @return el dateDataBaseServer
	 */
	public Date getDateDataBaseServer() {
		return dateDataBaseServer;
	}

	/**
	 * @param dateDataBaseServer
	 *            el dateDataBaseServer a fijar
	 */
	public void setDateDataBaseServer(Date dateDataBaseServer) {
		this.dateDataBaseServer = dateDataBaseServer;
	}

	/**
	 * @return el folderNumber
	 */
	public String getFolderNumber() {
		return folderNumber;
	}

	/**
	 * @param folderNumber
	 *            el folderNumber a fijar
	 */
	public void setFolderNumber(String folderNumber) {
		this.folderNumber = folderNumber;
	}

	/**
	 * @return el xadesFormat
	 */
	public String getXadesFormat() {
		return xadesFormat;
	}

	/**
	 * @param xadesFormat
	 *            el xadesFormat a fijar
	 */
	public void setXadesFormat(String xadesFormat) {
		this.xadesFormat = xadesFormat;
	}

	/**
	 * @return el funcName
	 */
	public String getFuncName() {
		return funcName;
	}

	/**
	 * @param funcName
	 *            el funcName a fijar
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	/**
	 * @return el certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate
	 *            el certificate a fijar
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @return el hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash
	 *            el hash a fijar
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return el inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            el inputStream a fijar
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return el outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream
	 *            el outputStream a fijar
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
