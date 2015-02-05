/**
 *
 */
package es.ieci.tecdoc.isicres.compulsa.connector.vo;

import java.util.Date;
import java.util.List;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase Abstracta que contiene la informacion necesaria basica para la
 *          compulsa
 */
public abstract class ISicresAbstractCompulsaVO extends
		ISicresAbsctractBaseCompulsaVO {

	/**
	 * Identificador del libro de registro al que pertenece el registro
	 */
	protected Integer bookId;

	/**
	 * Identificador del registro
	 */
	protected Integer folderId;

	/**
	 * Entidad con la que se está trabajando
	 */
	protected String entidadId;

	/**
	 * Localizador del documento
	 */
	protected String locator;

	/**
	 * Número de Registro
	 */
	protected String folderNumber;

	/**
	 * Fecha de compulsa
	 */
	protected Date fechaCompulsa;

	/**
	 * Lista de los documentos compulsados a almacenar
	 */
	protected List FilesInfo;

	/**
	 * Datos especificos para la implementacion de
	 */
	protected ISicresAbstractDatosEspecificosCompulsaVO datosEspecificos;

	/**
	 * @return el bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            el bookId a fijar
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return el folderId
	 */
	public Integer getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 *            el folderId a fijar
	 */
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return el entidadId
	 */
	public String getEntidadId() {
		return entidadId;
	}

	/**
	 * @param entidadId
	 *            el entidadId a fijar
	 */
	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	/**
	 * @return el locator
	 */
	public String getLocator() {
		return locator;
	}

	/**
	 * @param locator
	 *            el locator a fijar
	 */
	public void setLocator(String locator) {
		this.locator = locator;
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
	 * @return el filesInfo
	 */
	public List getFilesInfo() {
		return FilesInfo;
	}

	/**
	 * @return el fechaCompulsa
	 */
	public Date getFechaCompulsa() {
		return fechaCompulsa;
	}

	/**
	 * @param fechaCompulsa
	 *            el fechaCompulsa a fijar
	 */
	public void setFechaCompulsa(Date fechaCompulsa) {
		this.fechaCompulsa = fechaCompulsa;
	}

	/**
	 * @param filesInfo
	 *            el filesInfo a fijar
	 */
	public void setFilesInfo(List filesInfo) {
		FilesInfo = filesInfo;
	}

	/**
	 * @return el datosEspecificos
	 */
	public ISicresAbstractDatosEspecificosCompulsaVO getDatosEspecificos() {
		return datosEspecificos;
	}

	/**
	 * @param datosEspecificos
	 *            el datosEspecificos a fijar
	 */
	public void setDatosEspecificos(
			ISicresAbstractDatosEspecificosCompulsaVO datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}

}
