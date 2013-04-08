package es.ieci.tecdoc.fwktd.csv.core.service;

import java.util.List;

import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;

/**
 * Interfaz del servicio de gestión de aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ServicioAplicaciones {

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
	 * @param aplicacion
	 *            Información de la nueva aplicación externa.
	 * @return Información de la aplicación externa creada.
	 */
	public AplicacionCSV saveAplicacion(AplicacionCSVForm aplicacion);

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
