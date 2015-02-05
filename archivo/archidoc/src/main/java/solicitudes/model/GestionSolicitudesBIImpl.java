package solicitudes.model;

import java.util.Date;
import java.util.List;

import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.DetalleVO;
import util.CollectionUtils;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;

/**
 * Clase de implementación del interface con los metodos de tratamiento de
 * solicitudes.
 */
public class GestionSolicitudesBIImpl extends ServiceBase implements
		GestionSolicitudesBI {

	private IDetalleDBEntity detalleDBEntity = null;

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public GestionSolicitudesBIImpl(IDetalleDBEntity detalleDBEntity) {
		this.detalleDBEntity = detalleDBEntity;
	}

	/**
	 * Obtiene el detalle de la solicitud entregada de una unidad documental.
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @return Detalle de la solicitud.
	 */
	public DetalleVO getDetalleEntregada(String idUdoc) {
		DetalleVO detalle = null;

		List detalles = detalleDBEntity.getDetalles(idUdoc,
				new int[] { PrestamosConstants.ESTADO_DETALLE_ENTREGADA });
		if (!CollectionUtils.isEmpty(detalles))
			detalle = (DetalleVO) detalles.get(0);

		return detalle;
	}

	public List getDetallesEntregadosByFechas(Date fechaIni, Date fechaFin) {
		return detalleDBEntity
				.getDetallesEntregadosByFechas(fechaIni, fechaFin);
	}

	/**
	 * Obtiene informacion adicional sobre la solicitud indicada: - Nombre del
	 * archivo. - Usuario Gestor de la solicitud
	 * 
	 * @param prestamo
	 *            Solicitud de la que deseamos obtener la informacion
	 * @return Object PrestamoVO o ConsultaVO
	 */
	public Object getAditionalSolicitudInformation(Object solicitud) {

		Object solicitudConInfo = null;

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		GestionControlUsuariosBI usuarioBI = services
				.lookupGestionControlUsuariosBI();
		if (solicitud instanceof PrestamoVO) {
			PrestamoVO prestamoVO = (PrestamoVO) solicitud;
			prestamoVO.setArchivo(sistemaBI.getArchivo(prestamoVO
					.getIdarchivo()));
			prestamoVO.setUsuarioGestor(usuarioBI.getUsuario(prestamoVO
					.getIdusrgestor()));
			solicitudConInfo = prestamoVO;
		} else if (solicitud instanceof ConsultaVO) {
			ConsultaVO consultaVO = (ConsultaVO) solicitud;
			consultaVO.setArchivo(sistemaBI.getArchivo(consultaVO
					.getIdarchivo()));
			consultaVO.setUsuarioGestor(usuarioBI.getUsuario(consultaVO
					.getIdusrsolicitante()));
			solicitudConInfo = consultaVO;
		}

		return solicitudConInfo;
	}

	/**
	 * Determina si la unidad documental está en algún préstamo/consulta en
	 * algún estado que no sea ni devuelta ni denegada
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @return Detalle de la solicitud.
	 */
	public boolean tieneDetallesNoDevueltos(String idUdoc) {

		List detalles = detalleDBEntity.getDetalles(idUdoc, new int[] {
				PrestamosConstants.ESTADO_DETALLE_AUTORIZADA,
				PrestamosConstants.ESTADO_DETALLE_ENTREGADA,
				PrestamosConstants.ESTADO_DETALLE_PENDIENTE,
				PrestamosConstants.ESTADO_DETALLE_RESERVADA, });
		if (!CollectionUtils.isEmpty(detalles))
			return true;

		return false;
	}
}
