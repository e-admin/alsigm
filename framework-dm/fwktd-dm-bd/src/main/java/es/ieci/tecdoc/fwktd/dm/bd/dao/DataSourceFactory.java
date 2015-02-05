package es.ieci.tecdoc.fwktd.dm.bd.dao;

import javax.sql.DataSource;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Interfaz para la factoría para obtener el origen de datos a partir de la
 * configuración de un tipo de contenido.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DataSourceFactory {

	/**
	 * Obtiene el origen de datos a partir de la configuración del
	 * tipo de contenido.
	 * @param contentType Tipo de contenido.
	 * @return Origen de datos.
	 */
	public DataSource getDataSource(ContentType contentType);

}
