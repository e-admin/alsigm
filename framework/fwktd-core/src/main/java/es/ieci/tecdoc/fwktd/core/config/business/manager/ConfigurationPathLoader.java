package es.ieci.tecdoc.fwktd.core.config.business.manager;


/**
 * Interface a implementar por las clases que obtienen propiedades de un fichero properties
 */
public interface ConfigurationPathLoader {

	/**
	 * Permite obtener la ruta del fichero de propiedades
	 * @return ruta del fichero de propiedades
	 * @throws Exception Si hubo algun error
	 */
	public String getPathConfiguracionFile();
	
}
