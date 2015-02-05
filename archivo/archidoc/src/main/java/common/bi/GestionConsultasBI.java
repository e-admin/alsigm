package common.bi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.vos.BusquedaVO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.consultas.vos.MotivoConsultaVO;
import solicitudes.consultas.vos.TemaVO;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.vos.DetalleVO;
import solicitudes.vos.MotivoRechazoVO;

import common.exceptions.TooManyResultsException;

import deposito.vos.UDocEnUiDepositoVO;

/**
 * Bussines Interface con los métodos necesarios para el modulo de consultas.
 */
public interface GestionConsultasBI {
	/**
	 * Obtiene una consulta dada por su identificador para su
	 * visualizacion(Realiza auditoria)
    *
	 * @param codconsulta
	 *            Identificador de la consulta que deseamos recuperar
	 * @return consulta asociada al id
	 */
	public ConsultaVO verConsulta(String codconsulta);

	/**
	 * Obtiene una consulta dada por su identificador
    *
	 * @param codconsulta
	 *            Identificador de la consulta que deseamos recuperar
	 * @return consulta asociada al id
	 */
	public ConsultaVO getConsulta(String codconsulta);

	// /**
	// * Realiza la inserción de una consulta en la base de datos.
	// * @param vo Consulta con los datos que deseamos insertar en la bd.
	// */
	// public Collection insertConsulta(ConsultaVO vo) ;
	/**
	 * Realiza la inserción de una consulta en la base de datos por un
	 * determinado usuario.
    *
	 * @param vo
	 *            Consulta con los datos que deseamos insertar en la bd.
	 * @param user
	 *            Usuario que realiza la creacion de la consulta
	 */
	public void insertConsulta(ConsultaVO vo, ServiceClient user)
			throws ConsultaActionNotAllowedException;

	/**
	 * Realiza la actualizacion de una consulta.
    *
	 * @param consultaVO
	 *            Consulta que deseamos actualizar
	 */
	public void actualizarConsulta(ConsultaVO vo, ServiceClient sc)
			throws ConsultaActionNotAllowedException;

	/**
	 * Realiza el borrado de las consultas seleccionadas comprobando si es
	 * posible borrarlas, ya que solo se puede elimnar consultas en estado
	 * abierta o rechazada
    *
	 * @param consultasAEliminar
	 *            Listado de los identificadores de las consultas a eliminar.
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede borrar las consultas.
	 */
	public void eliminarConsultas(String[] consultasAEliminar)
			throws ConsultaActionNotAllowedException;

	/**
	 * Realiza el envío de la solicitud de los detalles de una consulta.
    *
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos enviar su solicitud.
	 * @throws ConsultaActionNotAllowedException. Si
	 *             no se puede realiar la operacion por - Por fecha de estado no
	 *             valida - Por estado abierta
	 */
	// public void enviarConsulta(String codigoConsulta) throws
	// ConsultaActionNotAllowedException;
	public void enviarConsulta(ConsultaVO consulta)
			throws ConsultaActionNotAllowedException;

	// /**
	// * Comprueba la disponibilidad de un detalle de una consulta dada.
	// * @param detalleConsulta Detalle de la consulta de la que se desea
	// comprobar su disponibilidad.
	// * @param finicioconsulta Fecha de inicio de la consulta.
	// * @param ffinalconsulta Fecha de fin de la consulta.
	// * @return Listado de los prestamos no disponibles con su motivo.
	// */
	// public Collection detalleDisponible(DetalleConsultaVO
	// detalleConsulta,Date finicioconsulta, Date ffinalconsulta) ;
	// /**
	// * Comprueba si un detalle de una consulta se encuentra disponible,
	// * devolviendo en su caso el que no esta y el motivo de que no este
	// * disponible. Nota: Este es el segundo caso de disponibilidad, donde se
	// * comprueba solo el estado.
	// * @param detalleConslta Detalle de la consulta que deseamos comprobar su
	// disponibilidad.
	// * @param finicio Fecha de inicio de la consulta.
	// * @param ffinal Fecha de finalizacion de la consulta.
	// * @return Listado de las consultas no disponibles con su motivo.
	// */
	// public Collection detalleDisponible2(DetalleConsultaVO
	// detalleConsulta,Date finicio, Date ffinal) ;
	/**
	 * Pasa la consulta a estado autorizado teniendo en cuenta el estado de las
	 * unidades documentales asociadas a el.
    *
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos autorizar.
	 * @param detalles
	 *            Listado de los detalles del préstamo.
	 * @return Listado de los detalles de la consulta que no se han podido
	 *         aceptar para la consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se pueder realizar la accion
	 */
	public Collection autorizardenegarConsulta(String codigoConsulta,
			String fentrega) throws ConsultaActionNotAllowedException;

	// /**
	// * Pasa la consulta a estado autorizado teniendo en cuenta el estado de
	// las unidades documentales asociadas a el.
	// * @param idConsulta Identificador de la consulta que deseamos autorizar.
	// * @param detalles Listado de los detalles del préstamo.
	// */
	// public void autorizardenegarConsulta2(String codigoConsulta) ;
	/**
	 * Realiza la entrega de una consulta(pasandola a entregada a ella y a sus
	 * u.docs autorizadas asociadas
    *
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos entregar
	 */
	public void entregarConsulta(String codigoConsulta)
			throws ConsultaActionNotAllowedException;

	/**
	 * Indica si un usuario tiene consultas en curso.
    *
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return true si el usuario tiene consultas en curso.
	 */
	public boolean hasConsultasEnCurso(String idUsuario);

	/**
	 * Obtiene un listado de consultas que se encuentran en alguno de los
	 * estados indicados.
    *
	 * @param estados
	 *            Listado de los posibles estados en que deseamos que se
	 *            encuentre las consultas buscadas
	 * @return Número de consultas encontradas que se encontraban en alguno de
	 *         los estados solicitados.
	 */
	public int getCountConsultasXEstados(String[] estados, String[] idsArchivo);

	/**
	 * Obtiene un listado de consultas que se encuentran en alguno de los
	 * estados indicados.
    *
	 * @param estados
	 *            Listado de los posibles estados en que deseamos que se
	 *            encuentre las consultas buscadas
	 * @param busqueda
	 *            Objeto con las restriccion para los resultados de la busqueda:
	 *            fecha de estado, entrega y usuario solicitante.
	 * @return Listado de las consultas encontradas que se encontraban en alguno
	 *         de los estados solicitados.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getConsultasXEstados(List estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException;

	/**
	 * Obtiene las consultas que pertenecen al usuario indicador(como
	 * solicitante) y se encuentra en unos de los estado pasados. En caso de que
	 * se pase el filtro de busqueda estos criterios deberan ser tambien
	 * cumplidos: fecha entrega,fescha estado.
    *
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 * @param estados
	 *            Listado de los estados por los que deseamo restringir
	 * @param busqueda
	 *            Objeto busqueda con las restricciones posibles, o null en caso
	 *            de no haber
	 * @return Listado de las consultas que cumplen el filtro
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getConsultasXEstadosYUsuario(String idUsuario,
			List estados, BusquedaVO busqueda) throws TooManyResultsException;

	/**
	 * Obtiene el número de consultas en las que el usuario figura como
	 * solicitante.
    *
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @return Número de consultas para el usuario solicitante
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioSolicitante);

	/**
	 * Obtiene el número de consultas en las que el usuario figura como
	 * solicitante.
    *
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @param idsArchvo
	 *            Identificadores de los archivos
	 * @return Número de consultas para el usuario solicitante
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioSolicitante,
			String[] idsArchivo);

	/**
	 * Obtiene el número de consultas abiertas en las que el usuario figura como
	 * solicitante.
    *
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @return Número de consultas abiertas para el usuario solicitante
	 */
	public int getCountConsultasAbiertasXUsuarioConsultor(
			String nUsuarioSolicitante);

	/**
	 * Obtiene un listado de las consultas en las que el usuario figura como
	 * solicitante.
    *
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @return Listado de las consultas para el usuario solicitante
	 */
	public List getConsultasXUsuarioConsultor(String idUsuario);

	/**
	 * Obtiene un listado de las consultas abiertas en las que el usuario figura
	 * como solicitante.
    *
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @return Listado de las consultas abiertas para el usuario solicitante
	 */
	public List getConsultasAbiertasXUsuarioConsultor(String idUsuario);

	/**
	 * Obtiene un listado de las consultas en las que el usuario figura usuario
	 * consultor y tiene el estado abierto.
    *
	 * @param nUsuario
	 *            Nombre del usuario consultor
	 * @return Listado de las consultas para el usuario
	 */
	public Collection getConsultasXUsuarioConsultorAbiertos(String nUsuario);

	/**
	 * Obtiene un listado de las consultas que tiene abiertas un usuario como
	 * usuario solicitante.
    *
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 */
	public Collection getConsultasXUsuarioGestorAbiertos(String idUsuario);

	/**
	 * Realiza la búsqueda de consultas.
    *
	 * @param busqueda
	 *            Objeto con los filtros para aplicar en la busqueda
	 * @return Listado de las consultas.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getConsultas(BusquedaVO busqueda, String[] idsArchivo)
			throws TooManyResultsException;

	/**
	 * Obtiene todos los temas existentes para un determinado tipo de entidad
    *
	 * @param tipo
	 *            Tipo de la entidad de la que deseamos obtener los temas: -1
	 *            Investigador -2 Ciudadano -3 Organo Externo
	 * @return Listado de los temas
	 */
	public List getTemasTipoEntidad(String tipo);

	/**
	 * Obtiene un listado de temas para un determinado usuario dado por su
	 * identificador
    *
	 * @param id
	 *            Identificador del usuario
	 * @return Listado de los temas del usuario
	 */
	public List getTemasUsuario(String id);

	/**
	 * Obtiene un listado de temas para un determinado usuario investigador por
	 * su identificador además de los temas comunes para los usuarios
	 * investigadores.
    *
	 * @param idUsuarioInvestigador
	 * @return Lista de {@link TemaVO}
	 */
	public List getTemasUsuarioInvestigador(String idUsuarioInvestigador);

	/**
	 * Obtiene el lista de temas para un determinado usuario de consulta en
	 * sala.
    *
	 * @param idUsrCSala
	 *            Identificador del usuario de consulta en Sala.
	 * @return Lista de {@link TemaVO}
	 */
	public List getTemasUsuarioSala(String idUsrCSala);

	/**
	 * Obtiene el lista de temas para los usuarios de tipo ciudadanos
    *
	 * @return Lista de {@link TemaVO}
	 */
	public List getTemasCiudadano();

	/**
	 * Obtiene la lista de temas para los usuarios de tipo Órgano
    *
	 * @return Lista de {@link TemaVO}
	 */
	public List getTemasOrgano();

	/**
	 * Obtiene los detalles(u.docs) de una consulta dada por su identificador.
    *
	 * @param codigoConsulta
	 *            Identificador de la consulta de la que deseamos obtener los
	 *            detalles.
	 * @return Listado de los detalles de la consulta.
	 */
	public Collection getDetallesConsulta(String codigoConsulta);

	/**
	 * Obtiene todas las unidadesd documentales que han sido devueltas para una
	 * consulta dada por su identificador.
    *
	 * @param codigoConsulta
	 *            Identificador de la consulta que deseamos obtener sus detalles
	 * @return Listado de las unidaddes documentales devueltas para la consulta
	 */
	public Collection getDetallesConsultaDevueltas(String codigoConsulta);

	/**
	 * Obtiene la ubicacion de una unidad documental identificada por su
	 * signatura y su identificador.
    *
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @param Signatura
	 *            Signatura de la unidad documental
	 * @return Ubicacion de la unidad documental indicada.
	 */
	public String getUbicacionDetalleConsulta(String idudoc, String signatura);

	/**
	 * Obtiene el detalle de identificador por su id y signatura de una consulta
	 * dada por su identificador
    *
	 * @param codigoConsulta
	 *            Identificador de la consulta al que pertenece el detalle.
	 * @param idudoc
	 *            Identificador de la unidad documental que deseamos obtener.
	 * @param signatura
	 *            Signatura de la unidad documental que deseamo obtener.
	 * @return El detalle de la consulta dada en caso de existir.
	 */
	public DetalleConsultaVO getDetalleConsulta(String codigoConsulta,
			String idudoc, String signatura) throws DetalleNotFoundException;

	/**
	 * Elimina los detalles(udocumentals) identificados por su id de la unidad
	 * documental y su signatura y que esta asociado a una consulta.
    *
	 * @param consulta
	 *            Consulta al que va asociado el detalle
	 * @param idudoc
	 *            Listado de los identificadores de los detalles(udocumental)
	 *            que deseamos eliminar.
	 * @param signaturasudocs
	 *            Listado de las signaturas de los detalles(udocumental) que
	 *            deseamos eliminar.
	 */
	public void eliminarDetallesConsulta(ConsultaVO consulta,
			String[] idsudocs, String[] signaturasudocs);

	/**
	 * Realiza la inserción de un nuevo detalle para una consulta
    *
	 * @param detalleConsulta
	 *            Detalle de la consulta a insertar en la bd.
	 */
	public void nuevoDetallesConsulta(DetalleConsultaVO detalleConsulta);

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
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	public void autorizarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc) throws ConsultaActionNotAllowedException;

	/**
	 * Realiza la denegación de una unidad documental de una determinada
	 * consulta.
    *
	 * @param consulta
	 *            Consulta a la que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea denegar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea denegar.
	 * @param motivo
	 *            Motivo de la denegación del préstamo.
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	public void denegarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo)
			throws ConsultaActionNotAllowedException;

	/**
	 * Obtiene un listado de los motivos de rechazo existente para las consultas
    *
	 * @return Listado de los motivos de rechazo {@link MotivoRechazoVO}
	 *         existentes para una consulta
	 */
	public Collection getMotivosRechazo();

	/**
	 * Realiza la devolución de un detalle(u.doc) para una consulta.
    *
	 * @param detalle
	 *            a devolver
	 */
	// public void devolverDetalleConsulta(ConsultaVO consulta,String idudoc,
	// String signaturaudoc) throws ConsultaActionNotAllowedException;
	public void devolverDetalleConsulta(DetalleConsultaVO detalle)
			throws ConsultaActionNotAllowedException;

	/**
	 * Obtiene todos los posibles estados en los que se puede encontrar una
	 * consulta
    *
	 * @return listado de los posibles estados de una consulta
	 **/
	public Collection getEstadosConsulta();

	// /**
	// * Comrpueba la disponibilidad de las unidades documentales aceptadas de
	// una determninada consulta.
	// * @param idConsulta Identificador de la consulta para el que se van a dar
	// de alta los solicitudes de unidades documentales
	// */
	// public Collection comprobarDisponibilidadConsulta(String idConsulta) ;
	// /**
	// * Comrpueba la disponibilidad de las unidades documentales aceptadas de
	// una determinada consulta que se van a entregar.
	// * @param idConsulta Identificador de la consulta para el que se van a dar
	// de alta los solicitudes de unidades documentales
	// */
	// public Collection comprobarDisponibilidadConsultaEntrega(String
	// idConsulta) ;
	// /**
	// * Encapsula el proceso de finalizacion de autorización y entrega de una
	// consulta.
	// * @param idConsulta Identificador de la consulta que deseamos entregar
	// * @return Listado de las u.docs que no se pudieron entregar por estar
	// ocupadas
	// */
	// public Collection finalizarAutorizacion(String idConsulta);
	// /**
	// * Encapsula el proceso de finalizacion de autorización y entrega de una
	// consulta.
	// * @param idConsulta Identificador de la consulta que deseamos entregar
	// * @return Listado de las u.docs que no se pudieron entregar por estar
	// ocupadas
	// */
	// public Collection finalizarAutorizacionReserva(String idConsulta);
	/**
	 * Obtiene un listado de los motivos existentes para el tipo de entidad
	 * indicado.
    *
	 * @param tipo
	 *            TIpo de entidad del que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de entidad
	 *         indicado.
	 */
	public abstract List getMotivosByTipoEntidad(int tipo);

	/**
	 * Obtiene los Motivos para el usuario investigador
    *
	 * @param idUsuarioConectado
	 *            Identificador del Usuario Conectado
	 * @param idUsuario
	 *            Identificador del Usuario seleccionado
	 * @return Lista de {@link MotivoConsultaVO}
	 */
	public abstract List getMotivosUsuarioInvestigador(AppUser appUser,
			String idUsuario);

	/**
	 * Obtiene los Motivos para los Usuarios de Consulta en Sala
    *
	 * @return Lista de {@link MotivoConsultaVO}
	 */
	public abstract List getMotivosUsuarioConsultaEnSala(AppUser appUser,
			String idUsuario);

	/**
	 * Obtiene un listado de los motivos existentes para el tipo de consulta.
    *
	 * @param tipo
	 *            TIpo de consulta de la que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de consulta
	 *         indicado.
	 */
	public abstract List getMotivosByTipoConsulta(int tipo);

	/**
	 * Obtiene el número de consultas gestionables por el usuario.
    *
	 * @param userVO
	 *            Usuario del que deseamos obtener las consultas
	 * @return Número de consultas gestionables por el usuario
	 */
	public int getCountListadoConsultasGestionar(ServiceClient userVO);

	/**
	 * Obtiene el número de consultas con reserva gestionables por el usuario.
    *
	 * @param userVO
	 *            Usuario del que deseamos obtener las consultas
	 * @return Número de consultas gestionables por el usuario
	 */
	public int getCountListadoConsultasGestionarReserva(ServiceClient userVO);

	/**
	 * Obtiene un listado de consultas gestionables por el usuario.
    *
	 * @param userVO
	 *            Usuario del que deseamos obtener las consultas
	 * @return Listado de consultas gestionables por el usuario
	 */
	public abstract List getListadoConsultasGestionar(ServiceClient userVO);

	/**
	 * Obtiene un listado de consultas con reserva gestionables por el usuario.
    *
	 * @param userVO
	 *            Usuario del que deseamos obtener las consultas
	 * @return Listado de consultas gestionables por el usuario
	 */
	public List getListadoConsultasGestionarReserva(ServiceClient userVO);

	/**
	 * Realiza la insercion de un nuevo tema en la base de datos para un usuario
	 * investigador
    *
	 * @param id
	 *            Identificador del usuario creador del nuevo tema
	 * @param tema
	 *            Tema creado
	 * @param consultaEnSala
	 *            Indicador de Consulta en Sala (Si este parametro es true,
	 */
	public TemaVO insertTema(String id, String tema, boolean consultaEnSala);

	/**
	 * Obtiene un listado de consultas a partir de sus identificadores.
    *
	 * @param codigos
	 *            Listado de los identificadores de las consultas que deseamo
	 *            recuperar
	 * @return Listado de las consultas
	 */
	public Collection getConsultas(String[] codigos);

	/**
	 * Obtiene la informacion adicional de un detalle(Descripcion del Fondo,
	 * Descripcion del Sistema Productor y Exepdiente correcto)
    *
	 * @param detalle
	 *            Detalle del que deseamos obtener la informacion adicional
	 * @return Detalle con los datos adicionales
	 */
	public DetalleConsultaVO tratarDetalleConsulta(DetalleConsultaVO detalle);

	/**
	 * Obtiene el número de detalles que están asociados a una determinada
	 * consulta.
    *
	 * @param idConsutla
	 *            Identificador de la consulta del que deseamos conocer su
	 *            numero de detalles asociados.
	 * @return Numero de detalless de asociados a la consulta.
	 */
	public int numeroDetallesConsulta(String idConsulta);

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * una determninada consulta, estableciendo su estado de disponiblidad.
    *
	 * @param Consulta
	 *            Consulta a la que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public boolean comprobarDisponibilidadDetallesConsulta(ConsultaVO consulta,
			Collection detalles);

	/**
	 * Realiza la entrega de una reserva de una consulta.
    *
	 * @param idConsulta
	 *            Identificador de la reserva de consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion por parte del usuario
	 */
	public Collection solicitarEntregaReserva(String idConsulta)
			throws ConsultaActionNotAllowedException;

	/**
	 * Obtiene el número de las preguntas que pueden ser entregadas
    *
	 * @param idsArchivo
	 * @return Número de las preguntas que pueden ser entregadas
	 */
	public int getCountListadoEntregar(String[] idsArchivo);

	/**
	 * Obtiene un listado de las preguntas que pueden ser entregadas
    *
	 * @return Listado de las preguntas que pueden ser entregadas
	 */
	public List obtenerListadoEntregar(String[] idsArchivo);

	/**
	 * Recupera los detalles de la consulta seleccionada para el usuario
	 * conectado
    *
	 * @param userVO
	 *            Usuario conectado
	 * @param consulta_VO
	 *            Consulta de la que deseamos obtener los detalles.
	 * @return Listado de los detalles de la consulta para el usuario conectado.
	 */
	public Collection obtenerDetallesConsultaXUsuario(ServiceClient user,
			ConsultaVO consulta_VO);

	/**
	 * Obtiene un listado de los detalles de una consulta para imprimir sus
	 * papeletas de salida
    *
	 * @param consulta
	 *            Consulta de la que se desean obtener los detalles
	 * @return Listado de los detalles de consulta
	 */
	public Collection obtenerDetallesSalida(ConsultaVO consulta);

	/**
	 * Realiza la devolucion de las unidades documentales seleccionadas.
    *
	 * @param udocs
	 *            Unidades documentales a devolver
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs)
			throws ConsultaActionNotAllowedException;

	/**
	 * Obtiene informacion adicional sobre la consulta indicada: - Nombre del
	 * archivo. - Datos del usuario creador.
    *
	 * @param consulta
	 *            Consulta de la que deseamos obtener la informacion
	 * @return ConsultaVO
	 */
	// public ConsultaVO getAditionalConsultaInformation(ConsultaVO consulta);

	/**
	 * Obtiene un listado de usuarios "visibles" en funcion de los permisos del
	 * usuario para filtrar en las busquedas por usuario
    *
	 * @return Listado de usuario
	 */
	public Collection getUsuariosBusqueda();

	// public Collection getDetallesConsultaEntregadas(String codigoConsulta)
	// throws Exception ;
	// public Collection getConsultas(String[] codigos) ;
	// public Collection getConsultasArchivo() ;
	// public Collection getConsultasXOrganoConsultor(String idOrganismo) ;
	// public Collection getConsultasXUsuarioConsultorReserva(String idUsuario)
	// ;
	// public Collection getDetallesConsultaAutorizadas(String codigoConsulta)
	// throws Exception ;
	// public void modificarEstadoConsulta(ConsultaVO consulta, int nuevoestado)
	// ;
	// public boolean unaConsultaBorrable(String[] consultasAEliminar) ;
	// public void eliminarDetallesConsulta(ConsultaVO consulta, String idudoc)
	// ;
	// public void eliminarDetalleConsulta(ConsultaVO consulta, String idudoc,
	// String signaturaudoc) ;
	// public void denegartodasConsulta(String codigoConsulta, String
	// motivorechazo) ;
	// public void modificarDetallesConsulta(ConsultaVO consulta,
	// DetalleConsultaVO infoDetalleConsulta) ;
	// public MotivoRechazoVO getMotivoRechazo(String motivoRechazo) ;
	// public void entregarDetalleConsulta(ConsultaVO consulta,String idudoc,
	// String signaturaudoc) ;

	/**
	 * Obtiene la lista de detalles de consultas que disponen de una unidad
	 * documental.
    *
	 * @param consulta
	 *            Información de la consulta.
	 * @param detalleConsulta
	 *            Udoc que deseamos comprobar
	 * @return Lista de detalles de consultas.
	 */
	public List getDetallesConsultasNoDisponibles(ConsultaVO consulta,
			DetalleConsultaVO detalleConsulta);

	/*
	 * (sin Javadoc)
    *
	 * @see common.bi.GestionConsultasBI#getNumDetalles(java.lang.String)
	 */
	public int getNumDetalles(String idSolicitud);

	void actualizarDetalleConsulta(DetalleVO detalle);

	/**
	 * Actualiza las observaciones
    *
	 * @param idSolicitud
	 */
	public void actualizarObservaciones(String idSolicitud, String observaciones);

	/* Motivos de Consulta */
	public void insertarMotivoConsulta(MotivoConsultaVO motivoVO);

	public MotivoConsultaVO getMotivoConsulta(MotivoConsultaVO motivoVO);

	public List getMotivosConsulta();

	public MotivoConsultaVO getMotivoConsultaById(String idMotivo);

	public int getCountConsultaByIdMotivo(String idMotivo);

	public void deleteMotivo(MotivoConsultaVO motivoVO);

	public void actualizarMotivo(MotivoConsultaVO motivoVO);

	public UDocEnUiDepositoVO getDescripcionUdocDeposito(String idudoc,
			String signatura);

	/**
	 * Obtiene las consultas a los que se pueden añadir unidades documentales
	 * desde la búsqueda.
    *
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @param idUsuario
	 *            Identificador del Usuario.
	 * @return lista de PrestamoVO que cumplen las condiciones.
	 */
	public List getConsultasAniadirUDocsFromBusqueda(String idArchivo,
			String idUsuario);

	/**
	 * Inserta los detalles a la consulta.
    *
	 * @param vo
	 * @param user
	 * @throws ConsultaActionNotAllowedException
	 */
	public void insertarDetallesAConsulta(ConsultaVO vo, ServiceClient user)
			throws ConsultaActionNotAllowedException;


	/**
    * Permite realizar el cambio de estado de una consulta, cuyo plazo de entrega ha caducado.
    * Se cambia el estado a Enviada, y todos los los detalles que tengan estado autorizados, se cambian
    * a pendientes, para que el usuario vuelva a realzar el proceso de autorización, donde se comprueba otra
    * vez la fecha de disponibilidad.
    * @param idConsulta
    */
	public void cambiarEstadoDeAutorizadaAEnviadaYDetallesAutorizadosAPendientes(String idConsulta);
}