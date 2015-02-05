package es.ieci.tecdoc.fwktd.dm.bd.manager;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Intefaz para la factoría que obtiene el manager de gestión documental
 * en base de datos configurado para un tipo documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface BaseDatosManagerFactory {

	/**
	 * Obtiene el gestor a partir de la configuración del
	 * tipo de contenido.
	 * @param contentType Tipo de contenido.
	 * @return Gestor.
	 */
	public BaseDatosManager getBaseDatosManager(ContentType contentType);

}
