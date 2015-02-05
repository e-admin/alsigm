package common.bi;

import java.util.Date;
import java.util.List;

import solicitudes.vos.DetalleVO;

public interface GestionSolicitudesBI {
	/**
	 * Obtiene el detalle de la solicitud entregada de una unidad documental.
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @return Detalle de la solicitud.
	 */
	public DetalleVO getDetalleEntregada(String idUdoc);

	public List getDetallesEntregadosByFechas(Date fechaIni, Date fechaFin);

	/**
	 * Obtiene informacion adicional sobre la solicitud indicada: - Nombre del
	 * archivo. - Usuario Gestor de la solicitud
	 * 
	 * @param prestamo
	 *            Solicitud de la que deseamos obtener la informacion
	 * @return Object PrestamoVO o ConsultaVO
	 */
	public Object getAditionalSolicitudInformation(Object solicitud);

	/**
	 * Determina si la unidad documental está en algún préstamo/consulta en
	 * algún estado que no sea ni devuelta ni denegada
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @return Detalle de la solicitud.
	 */
	public boolean tieneDetallesNoDevueltos(String idUdoc);

}