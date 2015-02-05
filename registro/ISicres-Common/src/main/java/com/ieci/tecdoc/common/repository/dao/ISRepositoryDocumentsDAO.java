package com.ieci.tecdoc.common.repository.dao;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;

/**
 * Clase que gestiona la tabla SCR_RepositoryDocs. Esta tabla relaciona el
 * UID del documento en el gestor documental con el UID del documento en la
 * aplicacion de Invesicres
 * 
 * @author Iecisa
 * @version Invesicres 7.0.1
 * 
 */
public class ISRepositoryDocumentsDAO {

	protected static final Logger log = Logger
			.getLogger(ISRepositoryDocumentsDAO.class);

	/**
	 * Método por el que obtenemos el identificador de documento unico en el
	 * repositorio documental
	 * 
	 * @param isicresDocUID
	 *            identificador del documento en invesicres - invesdoc
	 * @param entidad
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static String getDocumentRepositoryUID(String isicresDocUID,
			String entidad) {
		String docRepoUID = null;
		if (StringUtils.isNotEmpty(isicresDocUID)
				&& StringUtils.isNumeric(isicresDocUID)) {
			try {
				docRepoUID = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDocumentRepositoryUID(isicresDocUID, entidad);
			} catch (SQLException e) {
				log.error(
						"Se ha producido un error al obtener el UID del documento ISicres nº ["
								+ isicresDocUID + "]", e);
			} catch (Exception e) {
				log.error(
						"Se ha producido un error al obtener el UID del documento ISicres nº ["
								+ isicresDocUID + "]", e);
			}
		}
		return docRepoUID;
	}

	public static String insertDocumentRepositoryUID(
			String repositoryDocumentUID, String entidad) {
		String isicresDocUID = null;
		if (StringUtils.isNotEmpty(repositoryDocumentUID)) {
			try {
				String result = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.insertScrDocumentRepository(repositoryDocumentUID,
								entidad);
				if (StringUtils.isNotEmpty(result)
						&& StringUtils.isNotBlank(result)) {
					isicresDocUID = result;
				}
			} catch (SQLException e) {
				log.error(
						"Se ha producido un error al almacenar el UID del documento ["
								+ repositoryDocumentUID + "]", e);
			} catch (Exception e) {
				log.error(
						"Se ha producido un error al almacenar el UID del documento ["
								+ repositoryDocumentUID + "]", e);
			}
		}
		return isicresDocUID;
	}

}
