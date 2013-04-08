/**
 *
 */
package com.ieci.tecdoc.common.repository.helper;

import java.io.InputStream;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryBasicDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryCreateDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositorySignDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISSignDocumentVO;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.utils.HibernateUtil;

/**
 * Clase que complementa los VO que recibira la interfaz de Gestion de
 * Conectores para gestores documentales
 *
 * @author Iecisa
 *
 *
 */

public class ISRepositoryDocumentHelper {

	private static final Logger log = Logger
			.getLogger(ISRepositoryDocumentHelper.class);

	public static ISRepositoryCreateDocumentVO getRepositoryCreateDocumentVO(
			Integer bookID, Integer fdrid, Integer docId, String fileNameLog,
			String fileNameFis, String fileExtension, int miscFormatId,
			AxSf axsf, InputStream inputStream, String entidad,
			boolean closeSession) {
		ISRepositoryCreateDocumentVO createVO = new ISRepositoryCreateDocumentVO();
		createVO.setAxsf(axsf);
		createVO.setBookID(bookID);
		createVO.setDocId(docId);
		createVO.setEntidad(entidad);
		createVO.setFdrid(fdrid);
		createVO.setFileExtension(getFileExtension(fileNameFis, fileExtension));
		createVO.setFileName(getFileName(fileNameLog, fileNameFis));
		createVO.setInputStream(inputStream);
		createVO.setMiscFormatId(miscFormatId);

		createVO = (ISRepositoryCreateDocumentVO) getBookAttributes(createVO,
				closeSession);

		return createVO;
	}

	/**
	 * Método que construye el VO con los datos necesarios para recuperar un
	 * documento almacenado en el gestor documental
	 *
	 * @param bookID
	 * @param fdrid
	 * @param pageID
	 * @param entidad
	 * @return
	 */
	public static ISRepositoryRetrieveDocumentVO getRepositoryRetrieveDocumentVO(
			Integer bookID, Integer fdrid, Integer pageID, String entidad,
			boolean closeSession) {
		ISRepositoryRetrieveDocumentVO retrieveVO = new ISRepositoryRetrieveDocumentVO();
		retrieveVO.setBookID(bookID);
		retrieveVO.setEntidad(entidad);
		retrieveVO.setFdrid(fdrid);
		retrieveVO.setPageID(pageID);

		retrieveVO = (ISRepositoryRetrieveDocumentVO) getBookAttributes(
				retrieveVO, closeSession);

		return retrieveVO;
	}

	/**
	 * Método que construye el VO con los datos necesarios para firmar un
	 * documento almacenado en el gestor documental
	 *
	 * @param bookID
	 * @param fdrId
	 * @param docId
	 * @param pageID
	 * @param miscFormatId
	 * @param bookTypeConf
	 * @param entidad
	 * @return
	 */
	public static ISRepositorySignDocumentVO getRepositorySingDocumentVO(
			int bookID, int fdrId, int docId, int pageID, int miscFormatId,
			BookTypeConf bookTypeConf, BookConf bookConf, String entidad, boolean closeSession) {
		ISRepositorySignDocumentVO signVO = new ISRepositorySignDocumentVO();
		signVO.setBookID(new Integer(bookID));
		signVO.setBookTypeConf(bookTypeConf);
		signVO.setBookConf(bookConf);
		signVO.setDocId(new Integer(docId));
		signVO.setEntidad(entidad);
		signVO.setFdrid(new Integer(fdrId));
		signVO.setMiscFormatId(miscFormatId);
		signVO.setPageID(new Integer(pageID));

		signVO = (ISRepositorySignDocumentVO) getBookAttributes(signVO,
				closeSession);

		return signVO;
	}

	/**
	 * Metodo que construye el VO con los datos necesarios para firmar el
	 * contenido de un documento recibido como parametro
	 *
	 * @param inputStream
	 * @param bookTypeConf
	 * @return
	 */
	public static ISSignDocumentVO getSingDocumentVO(InputStream inputStream,
			BookTypeConf bookTypeConf,BookConf bookConf) {
		ISSignDocumentVO signVO = new ISSignDocumentVO();
		signVO.setBookTypeConf(bookTypeConf);
		signVO.setInputStream(inputStream);
		signVO.setBookConf(bookConf);

		return signVO;
	}

	/**
	 * Método que construye el VO con los datos necesarios para guardar en la
	 * aplicacion un documento que ya existe en el gestor documental
	 *
	 * @param bookID
	 * @param fileUID
	 * @param entidad
	 * @param closeSession
	 * @return
	 */
	public static ISRepositoryRetrieveDocumentVO getRepositorySaveExistDocument(
			Integer bookID, String fileUID, String entidad, boolean closeSession) {

		ISRepositoryRetrieveDocumentVO retrieveVO = new ISRepositoryRetrieveDocumentVO();
		retrieveVO.setBookID(bookID);
		retrieveVO.setEntidad(entidad);

		retrieveVO = (ISRepositoryRetrieveDocumentVO) getBookAttributes(
				retrieveVO, closeSession);

		retrieveVO.setDocumentUID(fileUID);

		return retrieveVO;
	}

	/**
	 * Método que recupera atributos referente al libro de registro
	 *
	 * @param basicVO
	 * @return
	 */
	protected static ISRepositoryBasicDocumentVO getBookAttributes(
			ISRepositoryBasicDocumentVO basicVO, boolean closeSession) {
		 Transaction tran = null;

		try {
			Session session = HibernateUtil
					.currentSession(basicVO.getEntidad());
			tran = session.beginTransaction();

			ScrRegstate regState = ISicresQueries.getScrRegstate(session,
					basicVO.getBookID());

			if (regState != null && regState.getIdocarchhdr() != null) {
				basicVO.setBookName(regState.getIdocarchhdr().getName());
				basicVO.setBookType(new Integer(regState.getIdocarchhdr()
						.getType()));
			}

			 HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Se ha producido un error al acceder al libro ["
					+ basicVO.getBookID() + "]", e);
		} finally {
			if (closeSession)
				HibernateUtil.closeSession(basicVO.getEntidad());
		}

		return basicVO;

	}

	/**
	 * Obtenemos el nombre del fichero
	 *
	 * @param fileNameLog
	 * @param fileNameFis
	 * @return
	 */
	private static String getFileName(String fileNameLog, String fileNameFis) {
		if (StringUtils.isEmpty(fileNameLog)) {
			fileNameLog = fileNameFis;
		}

		return fileNameLog;
	}

	/**
	 * Obtenemos la extension del fichero
	 *
	 * @param fileName
	 * @param extension
	 * @return
	 */
	private static String getFileExtension(String fileName, String extension) {
		if (StringUtils.isEmpty(extension)) {
			extension = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
		}

		return extension;
	}

}
