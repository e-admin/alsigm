package com.ieci.tecdoc.common.repository.vo;

import java.io.InputStream;

import com.ieci.tecdoc.common.isicres.AxSf;

/**
 * VO que contiene todos los datos necesarios para crear un documento en el
 * gestor documental
 * 
 * @author Iecisa
 * 
 */
public class ISRepositoryCreateDocumentVO extends ISRepositoryBasicDocumentVO {

	/**
	 * Identidicador del Documento
	 */
	protected Integer docId;

	/**
	 * Nombre logico del fichero
	 */
	protected String fileName;

	/**
	 * Extension del fichero
	 */
	protected String fileExtension;

	/**
	 * Identificador del formato de invesdoc
	 */
	protected int miscFormatId;

	/**
	 * Datos del registro
	 */
	protected AxSf axsf;

	/**
	 * Contenido del fichero
	 */
	protected InputStream inputStream;

	/**
	 * Identificador del documento en la aplicacion ISicres. Este contenido es
	 * importante unicamente para el retorno del create
	 */
	protected String isicresDocUID;

	/**
	 * @return the docId
	 */
	public Integer getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension
	 *            the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the miscFormatId
	 */
	public int getMiscFormatId() {
		return miscFormatId;
	}

	/**
	 * @param miscFormatId
	 *            the miscFormatId to set
	 */
	public void setMiscFormatId(int miscFormatId) {
		this.miscFormatId = miscFormatId;
	}

	/**
	 * @return the axsf
	 */
	public AxSf getAxsf() {
		return axsf;
	}

	/**
	 * @param axsf
	 *            the axsf to set
	 */
	public void setAxsf(AxSf axsf) {
		this.axsf = axsf;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the isicresDocUID
	 */
	public String getIsicresDocUID() {
		return isicresDocUID;
	}

	/**
	 * @param isicresDocUID
	 *            the isicresDocUID to set
	 */
	public void setIsicresDocUID(String isicresDocUID) {
		this.isicresDocUID = isicresDocUID;
	}

}
