/**
 *
 */
package com.ieci.tecdoc.common.adapter;

import com.ieci.tecdoc.common.repository.vo.ISRepositoryCreateDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositorySignDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISSignDocumentVO;

/**
 * @author 66575267
 *
 * Interfaz de gestion de repositorios
 */
public interface IRepositoryManager {

	/**
	 * Método que realiza las operaciones necesarias para recuperar un fichero
	 * de un repositorio
	 *
	 * @param retrieveVO
	 *            vo con todos datos necesarios para obtener un documento
	 *
	 * @return contenido del fichero que buscamos
	 * @throws Exception
	 */
	ISRepositoryRetrieveDocumentVO retrieveDocument(
			ISRepositoryRetrieveDocumentVO retrieveVO) throws Exception;

	/**
	 * Método que realiza las operaciones necesarias para añadir un fichero en
	 * un repositorio
	 *
	 * @param createVO
	 *            vo con todos los datos necesarios para crear un documento en
	 *            el gestor documental
	 *
	 * @return VO con el contenido y el UID del fichero en la aplicación
	 *         invesicres
	 * @throws Exception
	 */
	ISRepositoryCreateDocumentVO createDocument(
			ISRepositoryCreateDocumentVO createVO) throws Exception;

	/**
	 * Método que realiza las operaciones necesarias para guardar un documento
	 * ya existente en el gestor documental para asociarlo a la aplicacion
	 *
	 *
	 * @param saveExistVO
	 * @return
	 * @throws Exception
	 */
	ISRepositoryRetrieveDocumentVO saveExistDocument(
			ISRepositoryRetrieveDocumentVO saveExistVO) throws Exception;

	/**
	 * Método que realiza las operaciones necesarias para firmar un fichero que
	 * se encuentra en un repositorio
	 *
	 * @param signVO
	 *            vo con todos los datos necesarios para obtener un documento
	 *            del gestor documental, firmarlo y actualizarlo
	 * @return contenido del documento firmado
	 * @throws Exception
	 */
	ISRepositorySignDocumentVO signDocument(ISRepositorySignDocumentVO signVO)
			throws Exception;

	/**
	 * Método que realiza las operaciones necesarias para firmar un fichero que
	 * aun no se encuentra en un repositorio
	 *
	 * @param signVO
	 *            vo con todos los datos necesarios para firmar un documento
	 *            recibido como parametro
	 * @return contenido del documento firmado
	 * @throws Exception
	 */
	ISSignDocumentVO signDocument(ISSignDocumentVO signVO) throws Exception;

	/**
	 * Método que obtiene el DocUID de un documento.
	 *
	 * @param retrieveVO
	 * @return
	 * @throws Exception
	 */
	ISRepositoryRetrieveDocumentVO getDocUID(
			ISRepositoryRetrieveDocumentVO retrieveVO) throws Exception;

}
