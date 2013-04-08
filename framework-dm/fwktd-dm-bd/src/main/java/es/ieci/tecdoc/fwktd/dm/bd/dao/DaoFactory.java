package es.ieci.tecdoc.fwktd.dm.bd.dao;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Interfaz para la factoría para la creación de DAOs.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DaoFactory {


	/**
	 * Obtiene el DAO para la gestión de documentos.
	 *
	 * @param contentType
	 *            Información del tipo de contenido.
	 * @return DAO para la gestión de documentos.
	 */
	public DocumentoDao getDocumentoDao(ContentType contentType);

}
