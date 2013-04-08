package ieci.tecdoc.sgm.core.services.consolidacion;

/**
 * Interfaz para el servicio de consolidación.
 * 
 */
public interface ServicioConsolidacion {

	/**
	 * Realiza la consolidación de registros telemáticos en todas las entidades definidas.
	 * @throws ConsolidacionException si ocurre algún error.
	 */
	public void consolida() throws ConsolidacionException;
	
	/**
	 * Realiza la consolidación de registros telemáticos en la entidad.
	 * @param idEntidad Identificador de la entidad.
	 * @throws ConsolidacionException si ocurre algún error.
	 */
	public void consolida(String idEntidad) throws ConsolidacionException;

}
