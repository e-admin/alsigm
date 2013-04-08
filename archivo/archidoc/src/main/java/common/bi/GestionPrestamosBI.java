package common.bi;

import gcontrol.vos.UsuarioExtendedVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import se.usuarios.ServiceClient;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.MotivoPrestamoVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.ProrrogaVO;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.DetalleVO;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;

import deposito.vos.UDocEnUiDepositoVO;

/**
 * Bussines Interface con los metodos de negocio para el modulos de prestamos.
 */
public interface GestionPrestamosBI {
	/**
	 * 
	 * @param codigos
	 * @return Prestamos cuyo codigo sea alguno de los indicados
	 */
	public Collection getPrestamos(String[] codigos);

	/**
	 * Obtiene un prétamo a partir de su identificador en la base de datos para
	 * su visualizacion (edicion)
	 * 
	 * @param codprestamo
	 *            Identificador del préstamo en la base de datos
	 * @return Objeto {@link PrestamoVO} con los detalles del préstamo.
	 */
	public PrestamoVO verPrestamo(String codprestamo);

	/**
	 * Obtiene un prétamo a partir de su identificador en la base de datos
	 * 
	 * @param codprestamo
	 *            Identificador del préstamo en la base de datos
	 * @return Objeto {@link PrestamoVO} con los detalles del préstamo.
	 */
	public PrestamoVO getPrestamo(String codprestamo);

	/**
	 * Inserta un prestamo en la base de datos.
	 * 
	 * @param prestamoVO
	 *            Prestamo a insertar en la base de datos.
	 * @param user
	 *            Usuario que pretende realizar la inserción del préstamo
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la insercion por alguna de las
	 *             causas. - No tiene permisos. - El usuario tiene otro prestamo
	 *             abierto.
	 */
	public void insertPrestamo(PrestamoVO vo, ServiceClient user)
			throws PrestamoActionNotAllowedException;

	/**
	 * Realiza la actualizacion de un prestamo, actualizando su estado y la
	 * fecha de modificacion del estado, además de los datos que se modificaron
	 * en el formulario.
	 * 
	 * @param vo
	 *            Prestamo que se desea actualizar
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la actualizacion porque el préstamo
	 *             no es editable.
	 */
	public void actualizarPrestamo(PrestamoVO vo, ServiceClient sc)
			throws PrestamoActionNotAllowedException;

	/**
	 * Realiza el envío de los detalles de un prestamo
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo.
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar el envio por alguna de las causas. -
	 *             Fecha de reserva no válida. - Encontrarse en estado abierto.
	 */
	public void enviarPrestamo(String codigoPrestamo)
			throws PrestamoActionNotAllowedException;

	/**
	 * Encapsula la lógica de finalización del proceso de autorización de un
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo que deseamos finalizar.
	 * @throws PrestamoActionNotAllowedException
	 *             Si no es posible realizar la accion
	 */
	public Collection autorizardenegarPrestamo(String idPrestamo,
			String fentrega) throws PrestamoActionNotAllowedException;

	/**
	 * Pasa el prestamo a estado autorizado.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo que deseamos autorizar.
	 */
	// public void autorizardenegarPrestamo2(String codigoPrestamo) ;

	/**
	 * Realizar la entrega de un prestamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo que deseamos entregar.
	 * @throws PrestamoActionNotAllowedException
	 *             Si no es posible realizar la accion
	 */
	public void entregarPrestamo(String codigoPrestamo, Date fechaDevolucion)
			throws PrestamoActionNotAllowedException;

	/**
	 * Eliminar un conjunto de prestamos dados. Comprueba si los prestamos
	 * pueden ser borrados y en caso afirmativo procede a su borrado.
	 * 
	 * @param prestamosAEliminar
	 *            Listado de identificadores de los prestamos que se desea
	 *            eliminar.
	 * @exception Exception
	 *                Si se produce algún error durante el borrado de los
	 *                prestamos.
	 * @exception PrestamoActionNotAllowedException
	 *                Si no se puede realizar el borrado de los prestamos por el
	 *                estado en el que se encuentra alguno de ellos.
	 */
	public void eliminarPrestamos(String[] prestamosAEliminar)
			throws PrestamoActionNotAllowedException;

	/**
	 * Comprueba si un prestamo puede ser eliminado.
	 * 
	 * @param prestamosAEliminar
	 *            Identificador del préstamo que deseamos comprobar
	 * @return True si un prestamo se puede borrar o false en caso contrario.
	 */
	// public boolean unPrestamoBorrable(String[] prestamosAEliminar) ;

	/**
	 * Solicita la prorroga de un préstamo reclamado.
	 * 
	 * @param prorrogaVO
	 *            Datos de la prorroga del prestamo para el que vamos a
	 *            solicitar
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede solicitar la prorroga por: - Haberse
	 *             solicitado
	 */
	public void solicitarProrroga(ProrrogaVO prorrogaVO)
			throws PrestamoActionNotAllowedException;

	/**
	 * Solicita la prorroga de un préstamo reclamado y lo autoriza si tiene
	 * permisos
	 */
	public void solicitarProrroga(ProrrogaVO prorrogaVO, boolean autorizar)
			throws PrestamoActionNotAllowedException;

	/**
	 * Comprueba si el préstamo identificador por su id tiene prorrogas.</br>
	 * Comprobar si no tiene prorrogas solicitadas o enviada, q las fechas de
	 * reservar son correctas.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo del que deseamos comprobar si tiene
	 *            prorrogas.
	 * @return True si el préstamos ya dispone de prorrogas o falso en caso
	 *         contrario.
	 */
	public boolean yaTieneProrroga(String idPrestamo);

	/**
	 * Devuelve la fecha de una prorroga de un préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos obtener la fecha
	 *            de su prorroga.
	 * @return Fecha de la prorroga asociada al préstamo.
	 */
	public Date yaTieneProrrogaFecha(String idPrestamo);

	/**
	 * Realiza la autorización de una prorroga de un préstamo.
	 * 
	 * @param prestamoAutorizar
	 *            identificador del préstamo a autorizar sus prorrogas.
	 * @param fechaFinProrroga
	 *            Fecha del final de la prorroga
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	public void autorizarProrrogas(String idProrroga,
			String prestamoAAutorizar, Date fechaFinProrroga)
			throws PrestamoActionNotAllowedException;

	/**
	 * Realiza la denegación de una prorroga de un préstamo.
	 * 
	 * @param prestamosADenegar
	 *            Identificador del prestamos del que deseamos denegar su
	 *            prorroga.
	 * @param motivo
	 *            de denegarcion
	 * @throws PrestamoActionNotAllowedException
	 */
	public void denegarProrrogas(String prestamosADenegar, String motivo,
			String idMotivo) throws PrestamoActionNotAllowedException;

	/**
	 * Realiza la enesima reclamacion de un préstamo
	 * 
	 * @param veces
	 *            Número de la reclamación que se está realizando.
	 * @param idPrestamo
	 *            Identificador del prestamo sobre el que se realiza la
	 *            reclamación
	 */
	public void reclamar(int veces, String prestamosAReclamar)
			throws PrestamoActionNotAllowedException;

	/**
	 * Realiza cesión de un prétamo desde un usuario origen a un usuario
	 * destino.
	 * 
	 * @param idsPrestamos
	 *            Listado de los identificadores de los préstamos que desea
	 *            ceder.
	 * @param idUsuarioDestino
	 *            Usuario a quien se le va a ceder los prestamos indicados.
	 */
	// public void cederPrestamos(String[] prestamosACeder, String
	// idUsuarioDestino) ;

	/**
	 * Devuelve el número de prorrogas autorizadas de un préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador de bd de un préstamo del que deseamos obtener
	 *            las prórrogas.
	 * @return Número de prorrogas autorizadas.
	 */
	public int devolverProrrogas(String idPrestamo);

	/**
	 * Comprueba si el préstamo identificado por su id tiene prorrogas
	 * denegadas.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo del que deseamos comprobar si tiene
	 *            prorrogas denegadas.
	 * @return True si el préstamos ya dispone de prorrogas denegadas o falso en
	 *         caso contrario.
	 */
	public boolean yaTieneProrrogasDenegadas(String idPrestamo);

	/**
	 * Comprueba si un préstamo tiene prorrogas autorizadas.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo del que deseamos comprobar si tiene
	 *            prorrogas autorizadas
	 * @return True si el prestamo tiene prorrogas autorizad o false en caso
	 *         contrario
	 */
	public boolean yaTieneProrrogasAutorizadas(String idPrestamo);

	/**
	 * Obtiene los motivos de denegacion de una prorroga para un determinado
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo del que deseamos obtener los
	 *            motivos de prorroga.
	 * @return Motivos de denegación de la prorroga.
	 */
	public String motivoProrrogaDenegada(String idPrestamo);

	/**
	 * Obtiene la fecha de denegacion de una prorroga para un determinado
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del préstamo del que deseamos obtener los
	 *            motivos de prorroga.
	 * @return Fecha de denegación de la prorroga.
	 */
	public Date fechaProrrogaDenegada(String idPrestamo);

	/**
	 * Devuelve el número de préstamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos obtener sus
	 *            préstamos.
	 * @return Número de préstamos cuyo estado se encuentra en alguno de los
	 *         indicados.
	 */
	public int getCountPrestamosXEstados(String[] estados, String[] idsArchivo);

	/**
	 * Muestra todos los préstamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos mostrar sus
	 *            préstamos.
	 * @return Listado de los préstamos cuyo estado se encuentra en alguno de
	 *         los indicados.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getPrestamosXEstados(String[] estados, BusquedaVO busqueda,
			String[] idsArchivos) throws TooManyResultsException;

	// /**
	// * Obtiene los prestamos que pertenecen al usuario o que se encuentran en
	// * uno de los estados indicados.
	// *
	// * @param idUsuario
	// * Identificador del estado del que deseamos obtener sus
	// * prestamos.
	// * @param estados
	// * Estado en los que se pueden encontrar los prestamos.
	// * @return Listado de los prestamos encontrados que pertenecen al usuario
	// o
	// * estan en uno de los estados indicados.
	// */
	// public Collection getPrestamosXEstadosYUsuario(String idUsuario,String[]
	// estados, BusquedaVO busqueda) ;

	/**
	 * Obtiene un listado de los prestamos que pertenecen a un determinado
	 * organo
	 * 
	 * @param idOrgano
	 *            Identificador del organo del que deseamos obtener los
	 *            prestamos.
	 * @return Listado de los prestamos
	 */
	public Collection getPrestamosXOrganoSolicitante(String idOrganismo);

	/**
	 * Obtiene el número de prestamos que se encuentran en alguno de los estados
	 * que se indican y que han sido elaborador por un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Conjunto de posibles estados de préstamo. Puede ser nulo
	 * @return Número de prestamos del usuario en los que figura como usuario
	 *         gestor
	 */
	public int getCountPrestamosXUsuarioSolicitante(String idUsuario);

	/**
	 * Obtiene los prestamos para un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @return Un listado de los prestamos del usuario
	 */
	public List getPrestamosXUsuarioSolicitante(String idUsuario);

	/**
	 * Obtiene el número de prestamos de un usuario gestor dado por su
	 * identificador. Los del usuarios gestor son los suyos de su organismo y de
	 * organismos dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @param estados
	 *            Objeto con las restricciones de una busqueda
	 * @return Número de prestamos para el usuario
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuario,
			String[] estados);

	/**
	 * Obtiene los prestamos de un usuario gestor dado por su identificador. Los
	 * del usuarios gestor son los suyos de su organismo y de organismos
	 * dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @return Listado de prestamos para el usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getPrestamosXUsuarioGestor(String idUsuario, BusquedaVO busqueda)
			throws TooManyResultsException;

	/**
	 * Indica si un usuario tiene préstamos en curso, es decir, que no han sido
	 * rechazados y que todavia no ha devuelto.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return true si el usuario tiene préstamos en curso.
	 */
	public boolean hasPrestamosEnCurso(String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el nombre de
	 * usuario solicitante.
	 * 
	 * @param nombreUsuarioSolicitante
	 *            Nombre del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXUsuarioSolicitanteAbiertos(
			String nombreUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXIdUsuarioSolicitanteAbiertos(String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante y el id de usuario gestor
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public abstract Collection getPrestamosEnElaboracionXIdUsuario(
			String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos como gestor un
	 * determinado usuario.
	 * 
	 * @param idUsuario
	 *            Identificador en la base de datos del usuario.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXUsuarioGestorAbiertos(String idUsuario);

	/**
	 * Obtiene los prestamos para un usuario determinado.Utilizado para las
	 * busquedas
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @return Un listado de los prestamos del usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			BusquedaVO busqueda) throws TooManyResultsException;

	/**
	 * Obtiene el número de prestamos para un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Estados de los que se buscan préstamos
	 * @return Número de prestamos del usuario
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados);

	/**
	 * Obtiene el número de prestamos para un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Estados de los que se buscan préstamos
	 * @param idsArchivos
	 *            Identificadores de los archivos
	 * @return Número de prestamos del usuario
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados, String[] idsArchivo);

	/**
	 * Obtiene un listado de unidades documentales que pertenecen a un
	 * determinado expediente. Devuelve las partes si los expedientes están
	 * divididos
	 * 
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @param idArchivo
	 *            Identificador del archivo al que deben pertenecer las udocs
	 * @param like
	 *            Tipo de operador like
	 * @param pageInfo
	 *            Informacion sobre la paginacion de la tabla de resultados
	 * @return Unidades documentales que pertenecen al expediente.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	/*
	 * public Collection getUnidadesLikeNumExpWithParts(String num_exp, String
	 * idArchivo, String like, PageInfo pageInfo) throws TooManyResultsException
	 * ;
	 */
	/**
	 * Obtiene un listado de unidades documentales que pertenecen a un
	 * determinado fondo y con una determinada signatura.
	 * 
	 * @param fondo
	 *            Fondo al que pertenecen las unidades documentales que deseamos
	 *            obtener.
	 * @param signatura
	 *            Signatura de las unidades documentales a buscar. Busqueda tipo
	 *            LIKE signatura%
	 * @param idArchivo
	 *            Identificador del archivo al que deben pertenecer las udocs
	 * @param like
	 *            Tipo de operador like
	 * @param pageInfo
	 *            Informacion sobre la paginacion de la tabla de resultados
	 * @return Unidades documentales que pertenecen al expediente.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	/*
	 * public Collection getUnidadesLikeFondoSignatura(String fondo, String
	 * signatura, String idArchivo, String like, PageInfo pageInfo) throws
	 * TooManyResultsException;
	 */
	/**
	 * Obtiene un listado de unidades documentales entregadas que tienen una
	 * determinada signatura.
	 * 
	 * @param signatura
	 *            Signatura de la udoc.
	 * @param like
	 *            Tipo de operador like
	 * @return Unidades documentales que pertenecen al expediente.
	 */
	public Collection getUnidadesEntregadasLikeSignatura(String signatura,
			String like);

	/**
	 * Obtiene un listado de todas las unidades documentales para un determinado
	 * préstamo dado por su identificador.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamo del que deseamos obtener las
	 *            unidades documentales.
	 * @return Un listados de todas las unidades documentales para un
	 *         determinado préstamos
	 */
	public Collection getDetallesPrestamo(String codigoPrestamo);

	/**
	 * Obtiene las unidades documentales entregadas o autorizadas para un
	 * determinado préstamo que son lo que comprende los detalles de un
	 * prestamo.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamo del que deseamos obtener las
	 *            unidades documentales.
	 * @return Listado de unidades documentales autorizadas o entregadas para
	 *         ese prestamo.
	 */
	public Collection getDetallesPrestamoAutorizadasEntregadas(
			String codigoPrestamo);

	/**
	 * Obtiene un listado de las unidades documentales devueltas de un
	 * determinado prestamo
	 * 
	 * @param codigoPrestamo
	 *            Identificador del prestamo del que deseamos obtener los
	 *            detalles devueltos
	 * @return Listado de los detalles del prestamos
	 */
	public Collection getDetallesPrestamoDevueltas(String codigoPrestamo);

	/**
	 * Obtiene un listado de las unidades documentales entregadas de un
	 * determinado prestamo
	 * 
	 * @param codigoPrestamo
	 *            Identificador del prestamo del que deseamos obtener los
	 *            detalles entregados
	 * @return Listado de los detalles entregados del prestamos
	 */
	public Collection getDetallesPrestamoEntregadas(String codigoPrestamo);

	/**
	 * Obtiene la ubicacion de una unidad documental(detalle) de un préstamo.
	 * 
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @return Ubicacion de la unidad documental.
	 */
	public String getUbicacionDetallePrestamo(String idudoc, String signatura);

	/**
	 * Obtiene un detalle de prestamo(udoc) identificado por su id de prestamo y
	 * por los ids de la unidad documental.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamo.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param Signatura
	 *            Signatura del detalle
	 * @return Detalle del prestamo
	 * @throws DetalleNotFoundException
	 *             Si no existe el detalle en la BD.
	 */
	public DetallePrestamoVO getDetallePrestamo(String codigoPrestamo,
			String idudoc, String signatura) throws DetalleNotFoundException;

	/**
	 * Obtiene el detalle de un prestamo entregado a partir de su identificador
	 * de bd.
	 * 
	 * @param identificacion
	 *            Identificador del prestamo que deseamos recuperar.
	 * @return Detalle del préstamo
	 */
	public DetallePrestamoVO getDetallePrestamoEntregado(String identificacion);

	/**
	 * Elimina los detalles(udocumentals) identificados por su id de la unidad
	 * documental y su signatura y que esta asociado a una solicitud de
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo al que va asociado el detalle
	 * @param idudoc
	 *            Listado de los identificadores de los detalles(udocumental)
	 *            que deseamos eliminar.
	 * @param signaturasudocs
	 *            Listado de las signaturas de los detalles(udocumental) que
	 *            deseamos eliminar.
	 */
	public void eliminarDetallesPrestamo(String idPrestamo, String[] idsudocs,
			String[] signaturasudocs);

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
	 * @throws PrestamoActionNotAllowedException
	 *             si no se puede realizar la accion
	 */
	public void autorizarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc, ServiceClient sc)
			throws PrestamoActionNotAllowedException;

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
	 * @throws PrestamoActionNotAllowedException
	 *             si no se puede realizar la accion
	 */
	public void denegarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo)
			throws PrestamoActionNotAllowedException;

	/**
	 * Realiza la devolución de un detalle de un préstamo.
	 * 
	 * @param detalle
	 *            Detalle del préstamo que vamos a devolver
	 */
	public void devolverDetallePrestamo(DetallePrestamoVO detalle)
			throws PrestamoActionNotAllowedException;

	/**
	 * Añade un detalle(udoc) a un determinado préstamo.
	 * 
	 * @param detallePrestamo
	 *            Detalle a añadir al préstamo.
	 */
	public void nuevoDetallePrestamo(DetallePrestamoVO detallePrestamo);

	// /**
	// * Comprueba si un detalle de un préstamo se encuentra disponible,
	// * devolviendo en su caso el que no esta y el motivo de que no este
	// * disponible.
	// *
	// * @param detallePrestamo
	// * Detalle del prestamos que deseamos comprobar su
	// * disponibilidad.
	// * @param finicioprestamo
	// * Fecha de inicio del prestamo.
	// * @param ffinalprestamo
	// * Fecha de finalizacion del prestamo.
	// * @return Listado de los prestamos no disponibles con su motivo.
	// */
	// public Collection detalleDisponible(DetallePrestamoVO
	// detallePrestamo,Date finicioprestamo, Date ffinalprestamo) ;

	// /**
	// * Comprueba si un detalle de un préstamo se encuentra disponible,
	// * devolviendo en su caso el que no esta y el motivo de que no este
	// * disponible. Nota: Este es el segundo caso de disponibilidad, donde se
	// * comprueba solo el estado
	// *
	// * @param detallePrestamo
	// * Detalle del prestamos que deseamos comprobar su
	// * disponibilidad.
	// * @param finicioprestamo
	// * Fecha de inicio del prestamo.
	// * @param ffinalprestamo
	// * Fecha de finalizacion del prestamo.
	// * @return Listado de los prestamos no disponibles con su motivo.
	// */
	// public Collection detalleDisponible2(DetallePrestamoVO
	// detallePrestamo,Date finicioprestamo, Date ffinalprestamo) ;

	/**
	 * Obtiene los motivos de rechazo de para prestamos.
	 * 
	 * @return Listado con los motivos de rechazo.
	 */
	public Collection getMotivosRechazo();

	/**
	 * Obtiene un listado de los motivos de rechazo de las prorrogas
	 * 
	 * @return Listado con los motivos de rechazo de prorroga.
	 */
	public Collection getMotivosRechazoProrroga();

	/**
	 * Obtiene un listado de unidades documentales entregadas que pertenecen a
	 * un determinado expediente.
	 * 
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @param Like
	 *            para realizar la busqueda
	 * @return Unidades documentales entregadas que pertenecen al expediente.
	 */
	public Collection getUnidadesEntregadasLikeNumExp(String numexp, String like);

	/**
	 * Obtiene un listado de unidades documentales entregadas que pertenecen a
	 * un determinado fondo y tienen una determinada signatura.
	 * 
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @return Unidades documentales que pertenecen al expediente.
	 * @param Like
	 *            para realizar la busqueda
	 */
	// public Collection getUnidadesEntregadasLikeFondoSignatura(String fondo,
	// String signatura,String like) ;

	/**
	 * Obtiene una unidad documental entregada.
	 * 
	 * @param id
	 *            identificador de la solicitud.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param Signatura
	 *            Signatura del detalle
	 * @param type
	 *            Tipo de la solicitud(prestamo o consulta)
	 * @return Detalle de la solicitud entregada
	 */
	public BusquedaDetalleVO getUnidadEntregada(String id, String idUdoc,
			String signatura, String type);

	// /**
	// * Comprueba la disponibilidad de las unidades documentales aceptadas de
	// un determninado prestamo.
	// * @param idPrestamo Identificador del prestamo para el que se van a dar
	// de alta los solicitudes de unidades documentales
	// */
	// public Collection comprobarDisponibilidadPrestamo(String idPrestamo) ;

	// /**
	// * Comrpueba la disponibilidad de las unidades documentales aceptadas de
	// un determninado prestamo que se van a entregar.
	// * @param idPrestamo Identificador del prestamo para el que se van a dar
	// de alta los solicitudes de unidades documentales
	// */
	// public Collection comprobarDisponibilidadPrestamoEntrega(String
	// idPrestamo) ;
	public Collection getEstadosPrestamo();

	public void asignaEstadosAPrestamos(Collection prestamos);

	public Collection getNotas();

	/**
	 * Permite devolver varias unidades documentales de un préstamo o consulta
	 * 
	 * @param udocs
	 *            Lista de unidades a devolver
	 * @throws PrestamoActionNotAllowedException
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs)
			throws PrestamoActionNotAllowedException;

	/**
	 * Permite devolver varias unidades documentales de un préstamo o consulta
	 * 
	 * @param udocs
	 *            Lista de unidades a devolver
	 * @param unidadesDevolverRevDoc
	 *            unidades a devolver y para las que se debe crear una revisión
	 *            de documentación
	 * @throws PrestamoActionNotAllowedException
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs,
			Map unidadesDevolverRevDoc)
			throws PrestamoActionNotAllowedException;

	// /**
	// * Encapsula el proceso de finalizacion de autorización y entrega de un
	// prestamo.
	// * @param idPrestamo Identificador del prestamos que deseamos entrega
	// * @return Listado de las u.docs que no se pudieron entregar por estar
	// ocupadas
	// */
	// public Collection finalizarAutorizacionReserva(String idPrestamo)throws
	// Exception;
	// /**
	// * Encapsula el proceso de finalizacion de autorización y entrega de un
	// prestamo reservado.
	// * @param idPrestamo Identificador del prestamos que deseamos entrega
	// * @return Listado de las u.docs que no se pudieron entregar por estar
	// ocupadas
	// * @throws Exception Si se produce un error durante el proceso
	// */
	// public Collection finalizarAutorizacion(String idPrestamo)throws
	// Exception;
	/**
	 * Comprueba si una signatura de una unidad documental ha sido entregada.
	 * 
	 * @param signatura
	 *            Signatura de la unidad documental que se desea comprobar si ha
	 *            sido entregada.
	 * @return BusquedaDetallePrestamoVO si la u.doc con signatura fue entregada
	 *         o null si no ha sido entregada
	 */
	public BusquedaDetalleVO estaSignaturaEntregada(String signatura);

	/**
	 * Obtiene los detalles(sobre la solicitud) de las unidades documentales que
	 * se van a entregar
	 * 
	 * @param udocs
	 *            Listado de las unidades documentales que se desean entregar
	 * @return Listado de las unidadesd documentales a entregar con los detalles
	 *         asociados.
	 */
	public Collection getDetallesUDocs(List udocs);

	/**
	 * Realiza el filtrado de los prestamos
	 * 
	 * @param usuarioOrigen
	 * @param prestamos
	 * @return Listado de prestamos
	 */
	public Collection filtraPrestamosUsuario(String usuarioOrigen,
			Collection prestamos);

	/**
	 * Obtiene los detalles de un prestamo en funcion del tipo de usuario
	 * conectado.
	 * 
	 * @param prestamo_VO
	 *            Prestamo del que deseamos obtener sus detalles
	 * @return Listado de los detalles de un prestamos por su usuario
	 */
	public Collection obtenerDetallesPrestamoByUsuario(PrestamoVO prestamo);

	/**
	 * Asigna el fondo al detalle de una busqueda de unidad documental
	 * 
	 * @param detallesprestamos
	 *            Listado de las unidades documentales a las que se desea
	 *            asignar el fondo
	 * @param fondosService
	 *            Servicio de fondos
	 * @return Listado de los detalles con su fondo asociado
	 */
	public Collection asignarFondo(Collection detallesprestamos);

	/**
	 * Obtiene el número de prestamos a gestionar para el usuario del servicio
	 * en función de los permisos del mismo
	 * 
	 * @return Número de prestamos gestionables por el usuario
	 */
	public int getCountListadoGestionar(String[] idsArchivo);

	/**
	 * Obtiene el número de prestamos a gestionar reserva para el usuario del
	 * servicio en función de los permisos del mismo
	 * 
	 * @return Número de prestamos gestionables por el usuario
	 */
	public int getCountListadoGestionarReserva();

	/**
	 * Obtiene el listado de prestamos a gestionar para el usuario del servicio
	 * en función de los permisos del mismo
	 * 
	 * @return Listado de prestamos gestionables por el usuario
	 */
	public List obtenerListadoGestionar(String[] idsArchivo);

	/**
	 * Obtiene el listado de prestamos a gestionar reserva para el usuario del
	 * servicio en función de los permisos del mismo
	 * 
	 * @return Listado de prestamos gestionables por el usuario
	 */
	public List obtenerListadoGestionarReserva(String[] idsArchivo);

	/**
	 * Obtiene un listado de los organos "visibles" en función de los permisos
	 * del usuario para filtrar en las busqeuda por usuario
	 * 
	 * @return Listado de los organos.
	 */
	public Collection getOrganosBusqueda();

	/**
	 * Obtiene un listado de usuarios "visibles" en funcion de los permisos del
	 * usuario para filtrar en las busquedas por usuario
	 * 
	 * @return Listado de usuario
	 */
	public Collection getUsuariosBusqueda();

	/**
	 * Obtiene el número de detalles que están asociados a un determinado
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos conocer su numero
	 *            de detalles asociados.
	 * @return Numero de detaless de prestamo asociados al prestamo.
	 */
	public int numeroDetallesPrestamo(String idPrestamo);

	/**
	 * Obtiene la lista de detalles de préstamos que disponen de una unidad
	 * documental.
	 * 
	 * @param prestamo
	 *            Información del préstamo.
	 * @param detallePrestamo
	 *            Udoc que deseamos comprobar
	 * @return Lista de detalles de préstamos.
	 */
	public List getDetallesPrestamosNoDisponibles(PrestamoVO prestamo,
			DetallePrestamoVO detallePrestamo);

	/**
	 * Realiza la entrega de una reserva de un préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador de la reserva de prestamo
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la accion por parte del usuario
	 */
	public Collection solicitarEntregaReserva(String idPrestamo)
			throws PrestamoActionNotAllowedException;

	/**
	 * Obtiene las prorroga de un prestamo
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos obtener la
	 *            prorroga
	 * @return Prorroga asociada al prestamo
	 */
	public ProrrogaVO getProrrogaSolicitada(String idPrestamo);

	/**
	 * Obtiene el número de prorrogas entregables en funcion del usuario
	 * conectado
	 * 
	 * @return Número de prestamos entregables
	 */
	public int getCountListadoEntregar(String[] idsArchivo);

	/**
	 * Obtiene un listado de prorrogas entregables en funcion del usuario
	 * conectado
	 * 
	 * @return Listado de prestamos entregables
	 */
	public List obtenerListadoEntregar(String[] idsArchivo);

	/**
	 * Obtiene un listado de los detalles de un prestamos para imprimir sus
	 * papeletas de salida en funcion del usuario que realizar la impesion.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos imprimir sus
	 *            detalles
	 * @return Listado de detalles
	 */
	public Collection obtenerDetallesSalida(String idPrestamo);

	/**
	 * Comprueba la disponibilidad de una unidad documental(incluyendo reservas)
	 * 
	 * @param detallePrestamo
	 *            Udoc que deseamos comprobar
	 * @param finicioprestamo
	 *            fecha de inicio de uso de la udoc
	 * @param ffinalprestamo
	 *            fecha de fin de uso de la udoc
	 * @param isReserva
	 *            indica si el detalle es para una reserva
	 * @return Verdadero Si esta disponible o False en caso contrario
	 */

	public boolean isDetalleDisponible(DetallePrestamoVO detallePrestamo,
			Date finicioprestamo, Date ffinalprestamo, boolean isReserva);

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determinado préstamo, estableciendo su estado de disponiblidad.
	 * 
	 * @param Préstamo
	 *            al que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public boolean comprobarDisponibilidadDetallesPrestamo(PrestamoVO prestamo,
			Collection detalles);

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determninado prestamo que se va a prorrogar, estableciendo su estado
	 * de disponiblidad.
	 * 
	 * @param Prestamo
	 *            al que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public void comprobarDisponibilidadDetallesPrestamoProrroga(
			PrestamoVO prestamo, Collection detalles);

	/**
	 * Obtiene la informacion adicional de un detalle(Descripcion del Fondo,
	 * Descripcion del Sistema Productor y Exepdiente correcto)
	 * 
	 * @param detalle
	 *            Detalle del que deseamos obtener la informacion adicional
	 * @return Detalle con los datos adicionales
	 */
	public DetallePrestamoVO tratarDetallePrestamo(DetallePrestamoVO detalle);

	/**
	 * Obtiene un listado de usuarios cuando el prestamo no tiene solicitante
	 * interno
	 * 
	 * @param idArchivo
	 *            Identificador del archivo al que se le solicita el prestamo
	 * @return Listado de usuarios {@link UsuarioExtendedVO}
	 */
	public Collection getNuevosUsuariosGestoresXPermiso(String idArchivo);

	/**
	 * Obtiene un listado de usuarios cuando el prestamo tiene órgano
	 * solicitante interno
	 * 
	 * @param organo
	 *            Identificador del organo solicitante interno del préstamo
	 * @return Listado de usuarios{@link UsuarioExtendedVO}
	 */
	public Collection getNuevosUsuariosGestoresXOrgano(String organo);

	/**
	 * Obtiene informacion adicional sobre el prestamos indicado: - Nombre del
	 * archivo. - Datos del usuario creador.
	 * 
	 * @param prestamo
	 *            Prestamo del que deseamos obtener la informacion adicional
	 * @return PrestamoVO con la informacion adicional mas la básica
	 */
	// public PrestamoVO getAditionalPrestamoInformation(PrestamoVO prestamo);

	/**
	 * Obtiene los prestamos cedible por el usuario. Este método espera que en
	 * el cliente del servicio exista una propiedad denomindad
	 * PROPERTY_DEPENDENTORGANIZATIONLIST con la lista de organos dependientes.
	 * 
	 * @return Listado de los prestamos que puede ceder el usuario.
	 */
	public Collection getPrestamosCedibles();

	/**
	 * Obtiene un listado de los posibles usuarios que pueden gestionar un
	 * determinado prestamo.
	 * 
	 * @param prestamo
	 *            Prestamo del que deseamos obtener los posibles gestores.
	 * @return Listado de los posibles usuarios gestores.
	 */
	public Collection getUsuariosGestoresPosibles(PrestamoVO prestamo);

	/**
	 * Realiza el proceso de asignacion de un prestamo al gestor dado por su
	 * identificador
	 * 
	 * @param prestamo
	 *            Prestamo que deseamos ceder
	 * @param idGestor
	 *            Identificador del gestor al que vamos a ceder el prestamo
	 * @return Devuelve los datos del nuevo usuario gestor del prestamo
	 */
	public UsuarioExtendedVO asignarPrestamoAGestor(PrestamoVO prestamo,
			String idGestor) throws ActionNotAllowedException;

	// public void modificarEstadoPrestamo(PrestamoVO prestamo, int nuevoestado)
	// ;
	// public Collection getPrestamosXUsuarioSolicitanteReserva(String
	// idUsuario) ;
	// public Collection getPrestamosXUsuarioGestorNoDevueltosNoDenegados(String
	// idUsuario) ;
	// public Collection getProrrogasSolicitadas() ;

	// public Collection getDetallesPrestamoAutorizadas(String codigoPrestamo) ;
	// public void modificarDetallePrestamo(PrestamoVO
	// prestamo,DetallePrestamoVO infoDetallePrestamo) ;
	// public void eliminarDetallePrestamo(PrestamoVO prestamo,String idudoc) ;
	// public void eliminarDetallePrestamo(PrestamoVO prestamo,String idudoc,
	// String signaturaudoc) ;
	// public void entregarDetallePrestamo(PrestamoVO prestamo,String idudoc,
	// String signaturaudoc) ;

	/**
	 * Obtiene la lista de gestores con préstamos.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrestamos(String idOrgano);

	/**
	 * Obtiene la lista de préstamos que cumplan los criterios especificados.
	 * 
	 * @param busquedaVO
	 *            Criterios de búsqueda.
	 * @return Lista de préstamos ({@link PrestamoVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamos(BusquedaVO busquedaVO, String[] idsArchivo)
			throws TooManyResultsException;

	/**
	 * Devuelve el número de detalles del préstamo
	 * 
	 * @param idSolicitud
	 *            Id de solicitud
	 * @return Número de detalles del préstamo
	 */
	public int getNumDetalles(String idSolicitud);

	void actualizarDetallePrestamo(DetalleVO detalle);

	/**
	 * Actualiza las observaciones
	 * 
	 * @param idSolicitud
	 */
	public void actualizarObservaciones(String idSolicitud, String observaciones);

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determinado préstamo si se intenta establecer como fecha de devolución
	 * la indicada por parámetro
	 * 
	 * @param Préstamo
	 *            al que pertenecen las unidades
	 * @param fechaInicio
	 *            Fecha de inicio del préstamo
	 * @param fechaDevolucion
	 *            Fecha que se pretende establecer como de devolución del
	 *            préstamo
	 */
	public boolean comprobarDisponibilidadDetallesPrestamoXFecha(
			Collection detalles, Date fechaInicio, Date fechaDevolucion);

	/**
	 * Obtiene la lista de posibles gestores de revisiones de documentación
	 * 
	 * @param idArchivo
	 *            Identificador del archivo al que debe pertenecer el gestor
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getUsuariosGestoresRevDocPosibles(String idArchivo);

	/**
	 * Inserta la revisión de la Documentación
	 * 
	 * @param revDocVO
	 */
	public void insertRevisionDocumentacion(RevisionDocumentacionVO revDocVO);

	/**
	 * Obtiene todas revisiones de documentación existentes.
	 * 
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getAllRevisionDocumentacion();

	/**
	 * Obtiene todas revisiones de documentación que tengan el estado pasado por
	 * parametro.
	 * 
	 * @param idUserGestor
	 *            String con el usuario gestor
	 * @param estado
	 *            int con el identificador del estado
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getRevisionDocumentacionByEstado(String idUserGestor, int estado);

	/**
	 * Obtiene la revisión cuyo id concide con el que se le pasa
	 * 
	 * @param idRevDoc
	 *            Identificador de la revisión de la documentación
	 * @return {@link RevisionDocumentacionVO}
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc);

	/**
	 * Obtiene las revisiones cuyo idUdoc concide con el que se le pasa y que
	 * están en cierto estado
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param estados
	 *            Estados de revisiones
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getRevisionesDocumentacionByIdUdocYEstado(String idUdoc,
			int[] estados);

	/**
	 * Actualiza el registro
	 * 
	 * @param revDocVO
	 *            Objeto con los valores para actualizar
	 */
	public void actualizarRevisionDocumentacion(RevisionDocumentacionVO revDocVO);

	/**
	 * Obtiene el numero de unidades documentales con documentación a revisar.
	 * 
	 * @param idUser
	 *            String id del usuario gestor
	 * @return int
	 */
	public int getCountDocumentacionUDocsARevisar(String idUser);

	/**
	 * Se encarga de finalizar la revision de documentacion de las unidades
	 * documentales relacionadas.
	 * 
	 * @param revDocVO
	 *            {@link RevisionDocumentacionVO}
	 * @return
	 */
	public void finalizarRevisionDocumentacion(
			RevisionDocumentacionVO revDocVO, boolean copiarUdocsRelOrigen);

	List getGestoresConRevisionesDocumentacion();

	List getRevisionesDocumentacion(RevisionDocumentacionVO revDocVO)
			throws TooManyResultsException;

	List getUsuariosGestoresRevDocPosibles(List idsArchivos);

	UsuarioVO asignarRevisionDocAGestor(RevisionDocumentacionVO revDocVO,
			String idGestor) throws ActionNotAllowedException;

	/* Motivos de prestamo */
	public void insertarMotivoPrestamo(MotivoPrestamoVO motivoVO);

	public MotivoPrestamoVO getMotivoPrestamo(MotivoPrestamoVO motivoVO);

	public List getMotivosPrestamo();

	public MotivoPrestamoVO getMotivoPrestamoById(String idMotivo);

	public int getCountPrestamoByIdMotivo(String idMotivo);

	public void deleteMotivoPrestamo(MotivoPrestamoVO motivoVO);

	public void actualizarMotivoPrestamo(MotivoPrestamoVO motivoVO);

	public Collection getMotivosByTipoUsuario(Integer tipoUsuario,
			Integer[] visibilidad);

	public UDocEnUiDepositoVO getDescripcionUdocDeposito(String idudoc,
			String signatura);

	/**
	 * Obtiene los préstamos a los que se pueden añadir unidades documentales
	 * desde la búsqueda.
	 * 
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @param idUsuario
	 *            Identificador del Usuario.
	 * @return lista de PrestamoVO que cumplen las condiciones.
	 */
	public List getPrestamosAniadirUDocsFromBusqueda(String idArchivo,
			String idUsuario);

	/**
	 * Inserta los detalles a un préstamo existente
	 * 
	 * @param prestamoVO
	 *            Datos del préstamo
	 * @param user
	 * @throws PrestamoActionNotAllowedException
	 */
	public void insertarDetallesAPrestamo(PrestamoVO prestamoVO,
			ServiceClient user) throws PrestamoActionNotAllowedException;

	public int getCountPrestamosEnElaboracionXIdUsuario(String idUsuario);

	/**
	 * Obtiene las Prorrogas de un préstamo
	 * 
	 * @param idPrestamo
	 *            Cadena que contiene el identificador del préstamos
	 * @return Lista de {@link ProrrogaVO}
	 */
	public List getProrrogas(String idPrestamo);

	/**
	 * @param id
	 * @return
	 */
	public ProrrogaVO getProrrogaActiva(String idPrestamo);
}