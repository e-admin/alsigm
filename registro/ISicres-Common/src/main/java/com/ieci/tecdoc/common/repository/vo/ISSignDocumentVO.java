/**
 * 
 */
package com.ieci.tecdoc.common.repository.vo;

import java.io.InputStream;

import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;

/**
 * VO que contiene todos los datos necesarios para que se realize la firma de un
 * domento. Ya sea un documento almacenado en el gestor documental o no
 * 
 * @author Iecisa
 * 
 * 
 */

public class ISSignDocumentVO {

	/**
	 * Configuración del tipo de libro
	 */
	protected BookTypeConf bookTypeConf;

	/**
	 * Configuración del libro
	 */
	protected BookConf bookConf;

	/**
	 * Contenido del documento que queremos firmar. Solo para firmar un
	 * documento que no está almacenado en el repositorio documental
	 */
	protected InputStream inputStream;

	/**
	 * Contenido del documento ya firmado
	 */
	protected byte[] fileContent;

	/**
	 * @return the bookTypeConf
	 */
	public BookTypeConf getBookTypeConf() {
		return bookTypeConf;
	}

	/**
	 * @param bookTypeConf
	 *            the bookTypeConf to set
	 */
	public void setBookTypeConf(BookTypeConf bookTypeConf) {
		this.bookTypeConf = bookTypeConf;
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
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 *            the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return el bookConf
	 */
	public BookConf getBookConf() {
		return bookConf;
	}

	/**
	 * @param bookConf el bookConf a fijar
	 */
	public void setBookConf(BookConf bookConf) {
		this.bookConf = bookConf;
	}
	
	

}
