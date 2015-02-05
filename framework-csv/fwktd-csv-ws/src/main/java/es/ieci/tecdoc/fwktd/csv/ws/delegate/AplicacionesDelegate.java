package es.ieci.tecdoc.fwktd.csv.ws.delegate;

import java.util.List;

import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;

/**
 * Interfaz del delegate para gestionar aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AplicacionesDelegate {

	/**
	 * Obtiene el listado de aplicaciones externas.
	 *
	 * @return Listado de aplicaciones.
	 */
	public List<AplicacionCSV> getAplicaciones();

	/**
	 * Obtiene la información de la aplicación externa.
	 *
	 * @param id
	 *            Identificador de la aplicación externa.
	 * @return Información de la aplicación externa.
	 */
	public AplicacionCSV getAplicacion(String id);

	/**
	 * Obtiene la información de la aplicación externa.
	 *
	 * @param codigo
	 *            Código de la aplicación externa.
	 * @return Información de la aplicación externa.
	 */
	public AplicacionCSV getAplicacionByCodigo(String codigo);

	/**
	 * Guarda la información de una aplicación externa.
	 *
	 * @param aplicacionForm
	 *            Información de la nueva aplicación externa.
	 * @return Información de la aplicación externa creada.
	 */
	public AplicacionCSV saveAplicacion(AplicacionCSVForm aplicacionForm);

	/**
	 * Actualiza la información de una aplicación externa existente.
	 *
	 * @param aplicacion
	 *            Información de la aplicación externa.
	 * @return Información de la aplicación externa modificada.
	 */
	public AplicacionCSV updateAplicacion(AplicacionCSV aplicacion);

	/**
	 * Elimina la información de la aplicación externa.
	 *
	 * @param id
	 *            Identificador de la aplicación externa.
	 */
	public void deleteAplicacion(String id);

}
