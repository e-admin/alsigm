package solicitudes.db;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import salas.vos.BusquedaUsuarioSalaConsultaVO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.DetalleVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

import fondos.vos.BusquedaElementosVO;

/**
 * Entidad: <b>ASGPSOLICITUDUDOC</b>
 *
 * @author IECISA
 *
 */
public interface IDetalleDBEntity extends IDBEntity {
	/**
	 * Elimina los detalles(udocumentals) identificado por su id de la unidad
	 * documental y su signatura y que esta asociado a una solicitud(de préstamo
	 * o consulta).
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud ala que va asociado el detalle
	 * @param idudoc
	 *            Identificador del detalle(udocumental) que deseamos eliminar.
	 * @param signatura
	 *            Signatura del detalle(udocumental) que deseamos eliminar.
	 */
	public abstract void deleteDetalle(String idSolicitud, String idUdoc,
			String signatura, String type);

	/**
	 * Obtiene un listado de los detalles con el identificador de udoc y la
	 * signatura pasados.
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param type
	 *            Tipo del detalle al que deseamos castear los resultado
	 *            obtenidos(detalle de prestamo o de consulta)
	 * @return Listado de los detalles
	 */
	public abstract Collection getDetalles(String idudoc, String signatura,
			String type);

	public List getDetalles(String idUdoc, int[] estados);

	/**
	 * Obtiene un listado de todas las unidades documentales para un determinado
	 * solicitud(préstamo o consulta) dada por su identificador.
    *
	 * @param codigo
	 *            Identificador de la solicitud de la que deseamos obtener las
	 *            unidades documentales.
	 * @param type
	 *            Tipo de la solicitud 1-Prestamos 2 Consultas
	 * @return Un listados de todas las unidades documentales para una
	 *         determinada solicitud
	 */
	public abstract Collection getDetallesXTipo(String idPrestamo, String type);

	/**
	 * Realiza la autorización de una unidad documental de un determinado
	 * préstamo.
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public abstract void autorizarDetallePrestamo(PrestamoVO prestamo,
			String idudoc, String signaturaudoc);

	/**
	 * Realiza la autorización de una unidad documental de una determinada
	 * consulta.
    *
	 * @param consulta
	 *            Consulta a la que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public void autorizarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc);

	/**
	 * Permite bloquear un detalle de préstamo para comprobar su disponibilidad
	 * (debe ser dentro de una transacción)
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public void bloquearDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc);

	/**
	 * Actualiza un detalle dado por su id solicitud,id udoc, signatura y tipo
    *
	 * @param detalle
	 *            Detalle a actualizad
	 */
	public abstract void actualizarDetalle(DetalleVO detalle);

	/**
	 * Elimina todos los detalles asociados a una solicitud de un determinado
	 * tipo
    *
	 * @param id
	 *            Identificadro de la solicitud de la que deseamos eliminar los
	 *            detalles asociados
	 * @param type
	 *            Tipo de la solicitud que deseamos eliminar
	 */
	public abstract void deleteDetalles(String id, String type);

	/**
	 * Obtiene el numero de detalles entregados para una solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles entregados para la solicitud
	 */
	public int getNumDetallesEntregados(final String idSolicitud);

	/**
	 * Obtiene un listado de las unidades documentales devueltas de una
	 * determinada solicitud(prestamo o consulta)
    *
	 * @param codigo
	 *            Identificador de la solicitud de la que deseamos obtener los
	 *            detalles devueltos
	 * @return Listado de los detalles de la solicitud
	 */
	public abstract Collection getUnidadesDevueltas(String codigo, String type);

	/**
	 * Obtiene la ubicacion de una unidad documental(detalle) de un préstamo.
	 * NOTA: select asgdhueco.path,asgdudocenui.identificacion from asgdhueco,
	 * asgdudocenui, asgfunidaddoc , asgfelementocf where
	 * asgdhueco.iduinstalacion=asgdudocenui.iduinstalacion and
	 * asgfunidaddoc.idelementocf = asgfelementocf.id and asgfelementocf.codigo
	 * = asgdudocenui.signaturaudoc --and asgdudocenui.signaturaudoc = 'xxx'
	 * --and asgdudocenui.idudoc
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @return Ubicacion de la unidad documental.
	 */
	public abstract String getUbicacionDetalle(String idUdoc, String Signatura);

	/**
	 * Obtiene un detalle entrega del tipo indicado
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud en la que está incluida la
	 *            unidad documental
	 * @param idUdoc
	 *            Identifcador de la unidad documental
	 * @param signatura
	 *            Signatura de la udoc
	 * @param type
	 *            Tipo del detalle(prestamo o consulta)
	 * @return Detalle buscado para el tipo (DetallePRestamoVO o
	 *         DetalleConsultaVO)
	 */
	public abstract DetalleVO getDetalleEntregado(String idSolicitud,
			String idUdoc, String signatura, String type);

	/**
	 * Obtiene el detalle entregado a partir de su identificador de bd.
    *
	 * @param identificacion
	 *            Identificador del detalle que deseamos recuperar.
	 * @param type
	 *            Tipo del detalla(prestamo o consulta)
	 * @return Detalle de la solicitud
	 */
	public abstract DetalleVO getDetalleEntregadoXID(String identificacion,
			String type);

	/**
	 * Obtiene el numero de detalles devueltos para una solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles devuelto para la solicitud
	 */
	public abstract int getNumDetallesDevueltos(final String idSolicitud);

	/**
	 * Obtiene el numero de detalles para un solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles
	 */
	public abstract int getNumDetalles(final String idSolicitud);

	/**
	 * Obtiene los detalles de una solicitud que se encuentran en un determinado
	 * estado.
    *
	 * @param estados
	 *            Estados en los que se debe encotrar el detalles
	 * @param identificacion
	 *            Identificador de la solicitud a la que pertenece los detalles
	 * @param type
	 *            Tipo del detalla(prestamo o consulta)
	 * @return Detalle de la solicitud
	 */
	public abstract Collection getDetallesSolicitudXEstado(List estados,
			String identificacion, String type);

	/**
	 * Obtiene un listado de unidades documentales entregadas que pertenecen a
	 * un determinado expediente.
    *
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @param Like
	 *            para realizar la busqueda
	 * @return Unidades documentales que pertenecen al expediente y están
	 *         entregadas.
	 */
	public abstract Collection busquedaEntregadaXNumExp(String num_exp,
			String like);

	/**
	 * Obtiene un listado de unidades documentales entregadas con una signatura.
    *
	 * @param signatura
	 *            Signatura de la u.documental
	 * @param Like
	 *            para realizar la busqueda
	 * @return Unidades documentales.
	 */
	public abstract Collection busquedaEntregadasXSignatura(String signatura,
			String like);

	/**
	 * Obtiene un detalle de una solicitud (prestamo o consulta) entregada.
    *
	 * @param id
	 *            identificador de la solicitud.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura del detalle
	 * @param type
	 *            Tipo de la solicitud(prestamo o consulta)
	 * @return Detalle de la solicitud entregada
	 */
	public BusquedaDetalleVO getUnidadEntregada(String id, String idUdoc,
			String signatura, String type);

	/**
	 * Realiza la inserción de un detalle de un préstamo en la base de datos.
    *
	 * @param detalle
	 *            Detalle de prestamos que deseamos insertar.
	 */
	public abstract void insertDetallePrestamo(final DetallePrestamoVO detalle);

	/**
	 * Realiza la inserción de un detalle de una consulta en la base de datos.
    *
	 * @param detalle
	 *            Detalle de la consulta que deseamos insertar.
	 */
	public abstract void insertDetalleConsulta(final DetalleConsultaVO detalle);

	/**
	 * Realiza la denegación de una unidad documental de un determinado
	 * préstamo.
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea denegar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea denegar.
	 * @param motivo
	 *            Motivo de la denegación del préstamo.
	 */
	public abstract void denegarDetallePrestamo(PrestamoVO prestamo,
			String idudoc, String signaturaudoc, String motivo,
			String idMotivoRechazo);

	/**
	 * Realiza la denegación de una unidad documental de una determinada
	 * consulta.
    *
	 * @param consulta
	 *            Consulta al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea denegar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea denegar.
	 * @param motivo
	 *            Motivo de la denegación del préstamo.
	 */
	public abstract void denegarDetalleConsulta(ConsultaVO consulta,
			String idudoc, String signaturaudoc, String motivo,
			String idMotivoRechazo);

	/**
	 * Obtiene un detalle de una solicitud(prestamo o consulta) identificado por
	 * su id y por los ids de la unidad documental.
    *
	 * @param id
	 *            identificador de la solicitud.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param Signatura
	 *            Signatura del detalle
	 * @param type
	 *            Tipo de la solicitud(prestamo o consulta)
	 * @return Detalle de la solicitud
	 * @throws DetalleNotFoundException
	 *             Si no se encuentra el elemento buscado.
	 */
	public abstract DetalleVO getDetalle(String id, String idUdoc,
			String signatura, String type) throws DetalleNotFoundException;

	public List getDetallesEntregadosByFechas(Date fechaIni, Date fechaFin);

	/**
	 * Obtiene todos los DetallePrestamoVO que tengan como identificador de la
	 * unidad documental alguno de la lista de idsUDocs pasada por parámetro y
	 * cuyo estado sea alguno de los pasados por parámetro
    *
	 * @param idsUDocs
	 *            , Lista de identificadores de unidades documentales
	 * @param estados
	 * @return una lista de {@link DetallePrestamoVO}
	 */
	public List getUDocsEnPrestamoByEstado(List idsUDocs, int[] estados);

	/**
	 * Obtiene las Unidades Documentales de una caja que están prestadas o en
	 * alguna solicitud, y por ello no se puede añadir a una relación de
	 * entrega.
    *
	 * @param idUinstalacion
	 * @return Lista de DetalleVO
	 * @author Lucas Alvarez
	 */
	public List getUDocsNoDispoblesParaRelacion(String idUinstalacion);

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

	/**
	 * Obtiene un listado de los detalles con el identificador de udoc y la
	 * signatura pasados y en los estados pasados
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param type
	 *            Tipo del detalle al que deseamos castear los resultado
	 *            obtenidos(detalle de prestamo o de consulta)
	 * @param estados
	 *            Estados de los detalles que queremos obtener
	 * @return Listado de los detalles
	 */
	public abstract Collection getDetalles(String idudoc, String signatura,
			String type, String[] estados);

	public int getCountSolicitudesByIdMotivo(String idMotivo, String type);

	/**
	 * Obtiene el número de detalles por su estado
    *
	 * @param estados
	 * @return
	 */
	public int getCountDetallesByEstado(String idUdoc, int[] estados);

	/**
	 * Comprueba si existe el detalle para una determinada solicitud.
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param type
	 *            Tipo del detalle (Préstamo o Consulta)
	 * @return true si el detalle existe para la solicitud.
	 */
	public boolean existeDetalle(String tipoSolicitud, String idSolicitud,
			String idudoc, String signatura);

	/**
	 * Obtiene la lista de expedientes entregados y devueltos de un usuario de
	 * consulta en sala en un determinado rango de fechas
    *
	 * @param busquedaUsuarioSala
	 * @return Lista de objetos {@link DetalleConsultaVO}
	 */
	public List getExpedientesUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
	 * Obtiene el numero de unidades consultadas
    *
	 * @param fechaIni
	 *            Fecha inicial
	 * @param fechaFin
	 *            Fecha final
	 * @return int numero de unidades consultadas
	 */
	public int getCountUnidadesConsultadas(Date fechaIni, Date fechaFin);

	/**
	 * Obtiene las unidades consultasdas más de N veces por usuarios de consulta
	 * en sala
    *
	 * @param busquedaUsuarioSala
	 * @return Lista de objetos de {@link DetalleConsultaVO}
	 */
	public List getUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
    * Actualiza el estado de los detalles de una solicitud
    *
    * @param idSolicitud
    *            Cadena que contiene el identificador de la solicitud
    * @param tipoSolicitud
    *            Entero que contiene el tipo de solicitud
    * @param estadosOrigen
    *            Array de enteros que contiene de los detalles a actualizar
    * @param estadoDestino
    *            Entero que contiene el nuevo estado.
    */
	public void cambiarEstadosASolicitadoDetallesAutorizados(
			String idSolicitud, int tipoSolicitud, int[] estadosOrigen,
			int estadoDestino);
}