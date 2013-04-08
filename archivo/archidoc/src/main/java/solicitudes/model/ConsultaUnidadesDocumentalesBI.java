package solicitudes.model;

import java.util.List;

import solicitudes.vos.DetalleVO;

import common.exceptions.TooManyResultsException;

import fondos.vos.BusquedaElementosVO;

public interface ConsultaUnidadesDocumentalesBI {

	/**
	 * @param idsToShow
	 *            . Listas de {@link DetalleVO} con los identificadores de las
	 *            unidades documentales el de las solicitudes y las signaturas
	 *            cuando se solicita una búsqueda de prestamos Y consultas
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos la
	 *         información que se va a mostrar en el displayTable
	 */
	public List getElementosEnPrestamosYConsultas(List idsToShow,
			BusquedaElementosVO busquedaElementosVO);

	/**
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos el
	 *         indentificador de la unidad documental, el de la solicitud y la
	 *         signatura cuando se solicita una búsqueda de prestamos Y
	 *         consultas
	 */
	public List getIdsElementosEnPrestamosYConsultas(
			BusquedaElementosVO busquedaElementosVO, int numMaxResults)
			throws TooManyResultsException;

	/**
	 * @param idsToShow
	 *            . Listas de {@link DetalleVO} con los identificadores de las
	 *            unidades documentales de las solicitudes y las signaturas que
	 *            se van a mostrar cuando se solicita una búsqueda de prestamos
	 *            O consultas
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @param tipoServicio
	 *            1 si es prestamos, 2 si es consultas
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos la
	 *         información que se va a mostrar en el displayTable
	 */
	public List getElementosEnPrestamosOrConsultas(List idsTiposToShow,
			BusquedaElementosVO busquedaElementosVO, String tipoServicio);

	/**
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @param tipoServicio
	 *            1 si es prestamos, 2 si es consultas
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos el
	 *         indentificador de la unidad documental, el de la solicitud y la
	 *         signatura cuando se solicita una búsqueda de prestamos Y
	 *         consultas
	 */
	public List getIdsElementosEnPrestamosOrConsultas(
			BusquedaElementosVO busquedaElementosVO, String tipoServicio,
			int numMaxResults) throws TooManyResultsException;
}
