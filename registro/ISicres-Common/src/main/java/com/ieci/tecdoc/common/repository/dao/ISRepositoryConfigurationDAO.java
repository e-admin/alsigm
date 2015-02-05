/**
 * 
 */
package com.ieci.tecdoc.common.repository.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;

/**
 * Clase que gestiona la tabla SCR_RepositoryConf y la tabla SCR_RepoBookType
 * 
 * @author Iecisa
 * 
 */

public class ISRepositoryConfigurationDAO {

	protected static final Logger log = Logger
			.getLogger(ISRepositoryConfigurationDAO.class);

	/**
	 * Método por el que obtener la configuración del repositorio documental
	 * para un tipo de libro indicado
	 * 
	 * @param bookType
	 *            tipo de libro
	 * @param entidad
	 * @return
	 */
	public static String getRepositoryConfiguration(Integer bookType,
			String entidad) {
		String repositoryConf = null;

		try {
			if (bookType != null) {
				Integer repositoryConfId = DBEntityDAOFactory
						.getCurrentDBEntityDAO().getRepositoryByBookType(
								bookType, entidad);

				if (repositoryConfId != null) {
					repositoryConf = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getRepositoryConfiguration(repositoryConfId,
									entidad);
				}
			}
		} catch (SQLException e) {
			log
					.error(
							"Se ha producido un error al obtener la configuracion del repositorio documental para el tipo de libro ["
									+ bookType + "]", e);
		} catch (Exception e) {
			log
					.error(
							"Se ha producido un error al obtener la configuracion del repositorio documental para el tipo de libro ["
									+ bookType + "]", e);
		}

		return repositoryConf;

	}
}
