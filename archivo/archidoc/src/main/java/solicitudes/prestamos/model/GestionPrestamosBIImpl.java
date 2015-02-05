package solicitudes.prestamos.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.db.ConsultaDBEntity;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.db.DetalleDBEntity;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.INSecSolicitudesDBEntity;
import solicitudes.db.IRevisionDocumentacionDBEntity;
import solicitudes.db.MotivoRechazoDBEntity;
import solicitudes.db.MotivoRechazoDBEntityImpl;
import solicitudes.db.NSecDBEntityImpl;
import solicitudes.db.RevisionDocumentacionDBEntityImpl;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.EstadoRevDoc;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.db.IMotivoPrestamoDBEntity;
import solicitudes.prestamos.db.IPrestamoDBEntity;
import solicitudes.prestamos.db.IProrrogaDBEntity;
import solicitudes.prestamos.db.MotivoPrestamoDBEntityImpl;
import solicitudes.prestamos.db.PrestamoDBEntityImpl;
import solicitudes.prestamos.db.ProrrogaDBEntityImpl;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.EstadoVO;
import solicitudes.prestamos.vos.MotivoPrestamoVO;
import solicitudes.prestamos.vos.NotaVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.ProrrogaVO;
import solicitudes.prestamos.vos.RevisionDocumentacionToPO;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import solicitudes.utils.PropertyHelper;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.DetalleUdocNoDisponibleVO;
import solicitudes.vos.DetalleVO;
import transferencias.db.IUdocEnUIDBEntity;
import util.CollectionUtils;
import util.StringOwnTokenizer;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Sistema;
import xml.config.Usuario;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.utils.AuditoriaUtils;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.bi.GestionConsultasBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.security.ActionObject;
import common.security.ServiciosSecurityManager;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.db.IReferenciaDBEntity;
import descripcion.vos.CampoFechaVO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.FondoVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.UsuarioExtendedVO;
import gcontrol.vos.UsuarioVO;

/**
 * Clase de implementacion del interface con los metodos de de tratamiento de
 * prestamos.
 */
public class GestionPrestamosBIImpl extends ServiceBase implements
		GestionPrestamosBI {

	private IMotivoRechazoDBEntity motivoRechazoDBEntity = null;
	private IProrrogaDBEntity prorrogaDBEntity = null;
	private INSecSolicitudesDBEntity nSecDBEntity = null;
	private IPrestamoDBEntity prestamoDBEntity = null;
	private IDetalleDBEntity detallePrestamoDBEntity = null;
	private IUDocEnUiDepositoDbEntity uDocEnUiDepositoDbEntity = null;
	private ConsultaDBEntity consultaDBEntity = null;
	private IUInstalacionDepositoDBEntity uInstalacionDepositoDBEntity = null;
	private IRevisionDocumentacionDBEntity revisionDocumentacionDBEntity = null;
	// private IReferenciaDBEntity campoReferenciaDBEntity = null;
	// private IUdocEnUIDBEntity udocEnUIDbEntity = null;
	private IMotivoPrestamoDBEntity motivoDBEntity = null;

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public GestionPrestamosBIImpl(ServiceSession ss) {
		this.motivoRechazoDBEntity = new MotivoRechazoDBEntityImpl(
				ss.getTransactionManager());
		this.prorrogaDBEntity = new ProrrogaDBEntityImpl(
				ss.getTransactionManager());
		this.nSecDBEntity = new NSecDBEntityImpl(ss.getTransactionManager());
		this.prestamoDBEntity = new PrestamoDBEntityImpl(
				ss.getTransactionManager());
		this.detallePrestamoDBEntity = new DetalleDBEntityImpl(
				ss.getTransactionManager());
		this.uDocEnUiDepositoDbEntity = new UDocEnUiDepositoDbEntityImpl(
				ss.getTransactionManager());
		this.consultaDBEntity = new ConsultaDBEntity(ss.getTransactionManager());
		this.uInstalacionDepositoDBEntity = new UInstalacionDepositoDBEntity(
				ss.getTransactionManager());
		this.revisionDocumentacionDBEntity = new RevisionDocumentacionDBEntityImpl(
				ss.getTransactionManager());
		// this.campoReferenciaDBEntity = new
		// ReferenciaDBEntityImpl(ss.getTransactionManager());
		// this.udocEnUIDbEntity = new
		// UdocEnUIDBEntityImpl(ss.getTransactionManager());
		this.motivoDBEntity = new MotivoPrestamoDBEntityImpl(
				ss.getTransactionManager());
	}

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public GestionPrestamosBIImpl(IMotivoRechazoDBEntity mrde,
			IProrrogaDBEntity pde, INSecSolicitudesDBEntity nsde,
			IDetalleDBEntity dpde, IUDocEnUiDepositoDbEntity udedde,
			IPrestamoDBEntity pdbe, ConsultaDBEntity cde,
			IUInstalacionDepositoDBEntity uInstalacionDepositoDBEntity,
			IRevisionDocumentacionDBEntity revisionDocumentacionDBEntity,
			IReferenciaDBEntity campoReferenciaDBEntity,
			IUdocEnUIDBEntity udocEnUIDbEntity, IMotivoPrestamoDBEntity mde) {
		this.motivoRechazoDBEntity = mrde;
		this.prorrogaDBEntity = pde;
		this.nSecDBEntity = nsde;
		this.detallePrestamoDBEntity = dpde;
		this.uDocEnUiDepositoDbEntity = udedde;
		this.prestamoDBEntity = pdbe;
		this.consultaDBEntity = cde;
		this.uInstalacionDepositoDBEntity = uInstalacionDepositoDBEntity;
		this.revisionDocumentacionDBEntity = revisionDocumentacionDBEntity;
		// this.campoReferenciaDBEntity = campoReferenciaDBEntity;
		// this.udocEnUIDbEntity = udocEnUIDbEntity;
		this.motivoDBEntity = mde;
	}

	public PrestamoVO verPrestamo(String codprestamo) {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CONSULTA_PRESTAMO);
		Locale locale = getServiceClient().getLocale();
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, codprestamo);

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.VER_PRESTAMO_ACTION);

		PrestamoVO ret = prestamoDBEntity.getPrestamo(codprestamo);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(ret,
						getServiceSession()));

		return ret;
	}

	public PrestamoVO getPrestamo(String codprestamo) {
		return prestamoDBEntity.getPrestamo(codprestamo);
	}

	public Collection getPrestamos(String[] codigos) {
		return prestamoDBEntity.getPrestamos(codigos);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXEstados(java.lang.String
	 * [])
	 */
	public int getCountPrestamosXEstados(String[] estados, String[] idsArchivo) {
		return prestamoDBEntity.getCountPrestamosXEstados(estados, idsArchivo);
	}

	/**
	 * Muestra todos los prestamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos mostrar sus
	 *            prestamos.
	 * @param Objeto
	 *            con las restricciones de una busqueda
	 * @return Listado de los prestamos cuyo estado se encuentra en alguno de
	 *         los indicados.
	 * @throws TooManyResultsException
	 *             si el n�mero de resultados excede el m�ximo.
	 */
	public List getPrestamosXEstados(String[] estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException {
		return prestamoDBEntity.getPrestamosXEstados(estados, busqueda,
				idsArchivo);
	}

	/**
	 * Indica si un usuario tiene prestamos en curso, es decir, que no han sido
	 * rechazados y que todavia no ha devuelto.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return true si el usuario tiene prestamos en curso.
	 */
	public boolean hasPrestamosEnCurso(String idUsuario) {
		String[] estados = {
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO),
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO),
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO),
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO),
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_RESERVADO),
				String.valueOf(PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO) };
		return prestamoDBEntity.hasPrestamos(idUsuario, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXUsuarioGestor(java.lang
	 * .String, java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuario,
			String[] estados) {
		// this.check(ServiciosSecurityManager.PRESTAMOS_X_USUARIOGESTOR_ACTION);

		return prestamoDBEntity.getCountPrestamosXUsuarioGestor(idUsuario,
				getServiceClient().getAllDependentOrganizationIds(), estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXUsuarioGestor(java.lang
	 * .String, java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuario,
			String[] estados, String[] idsArchivo) {
		// this.check(ServiciosSecurityManager.PRESTAMOS_X_USUARIOGESTOR_ACTION);

		return prestamoDBEntity.getCountPrestamosXUsuarioGestor(idUsuario,
				getServiceClient().getAllDependentOrganizationIds(), estados,
				idsArchivo);
	}

	/**
	 * Obtiene los prestamos de un usuario gestor dado por su identificador. Los
	 * del usuarios gestor son los suyos de su organismo y de organismos
	 * dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Listado de prestamos para el usuario
	 * @throws TooManyResultsException
	 *             si el n�mero de resultados excede el m�ximo.
	 */
	public List getPrestamosXUsuarioGestor(String idUsuario, BusquedaVO busqueda)
			throws TooManyResultsException {
		// this.check(ServiciosSecurityManager.PRESTAMOS_X_USUARIOGESTOR_ACTION);

		return prestamoDBEntity.getPrestamosXUsuarioGestor(idUsuario,
				getServiceClient().getAllDependentOrganizationIds(), busqueda);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXUsuarioSolicitante(java
	 * .lang.String)
	 */
	public int getCountPrestamosXUsuarioSolicitante(String idUsuario) {
		this.check(ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION);
		int[] estados = { PrestamosConstants.ESTADO_PRESTAMO_ABIERTO,
				PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO,
				PrestamosConstants.ESTADO_PRESTAMO_RESERVADO,
				PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO,
				PrestamosConstants.ESTADO_PRESTAMO_DENEGADO,
				PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO,
				PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO };

		return prestamoDBEntity.getCountPrestamosXUsuarioSolicitante(idUsuario,
				estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getPrestamosXUsuarioSolicitante(java.lang
	 * .String)
	 */
	public List getPrestamosXUsuarioSolicitante(String idUsuario) {
		this.check(ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION);
		int[] estados = { PrestamosConstants.ESTADO_PRESTAMO_ABIERTO,
				PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO,
				PrestamosConstants.ESTADO_PRESTAMO_RESERVADO,
				PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO,
				PrestamosConstants.ESTADO_PRESTAMO_DENEGADO,
				PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO,
				PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO };

		return prestamoDBEntity.getPrestamosXUsuarioSolicitante(idUsuario,
				estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXUsuarioSolicitanteBuscar
	 * (java.lang.String, java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados) {
		this.check(ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION);

		return prestamoDBEntity.getCountPrestamosXUsuarioSolicitanteBuscar(
				idUsuario, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getCountPrestamosXUsuarioSolicitanteBuscar
	 * (java.lang.String, java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados, String[] idsArchivo) {
		this.check(ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION);

		return prestamoDBEntity.getCountPrestamosXUsuarioSolicitanteBuscar(
				idUsuario, estados);
	}

	/**
	 * Obtiene los prestamos para un usuario determinado.Utilizado para las
	 * busquedas
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param Objeto
	 *            con las restricciones de una busqueda
	 * @return Un listado de los prestamos del usuario
	 * @throws TooManyResultsException
	 *             si el n�mero de resultados excede el m�ximo.
	 */
	public List getPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			BusquedaVO busqueda) throws TooManyResultsException {
		this.check(ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION);

		return prestamoDBEntity.getPrestamosXUsuarioSolicitanteBuscar(
				idUsuario, busqueda);
	}

	public Collection getPrestamosXUsuarioSolicitanteAbiertos(
			String nombreUsuario) {
		return prestamoDBEntity
				.getPrestamosXUsuarioSolicitanteAbiertos(nombreUsuario);
	}

	public Collection getPrestamosXIdUsuarioSolicitanteAbiertos(String idUsuario) {
		return prestamoDBEntity
				.getPrestamosXIdUsuarioSolicitanteAbiertos(idUsuario);
	}

	public Collection getPrestamosXUsuarioGestorAbiertos(String idUsuario) {
		return prestamoDBEntity.getPrestamosXUsuarioGestorAbiertos(idUsuario);
	}

	public Collection getPrestamosXOrganoSolicitante(String idOrgano) {
		return prestamoDBEntity.getPrestamosXOrganoSolicitante(idOrgano);
	}

	public void insertarDetallesAPrestamo(PrestamoVO vo, ServiceClient user)
			throws PrestamoActionNotAllowedException {
		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ANIADIR_UDOC_PRESTAMO_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnPrestamoEdit(vo, user, this);

		iniciarTransaccion();
		List detallesPrestamo = vo.getDetallesPrestamo();
		if (ListUtils.isNotEmpty(detallesPrestamo)) {

			for (Iterator iterator = detallesPrestamo.iterator(); iterator
					.hasNext();) {
				DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) iterator
						.next();

				detallePrestamo.setIdsolicitud(vo.getId());

				if (!detallePrestamoDBEntity.existeDetalle(
						DetalleDBEntity.TIPO_DETALLE_PRESTAMO,
						detallePrestamo.getIdsolicitud(),
						detallePrestamo.getIdudoc(),
						detallePrestamo.getSignaturaudoc())) {
					nuevoDetallePrestamo(detallePrestamo);
				}
			}
		}
		commit();
	}

	public void insertPrestamo(PrestamoVO vo, ServiceClient user)
			throws PrestamoActionNotAllowedException {
		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ALTA_PRESTAMO);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.INSERTAR_PRESTAMO_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnPrestamoInsert(user, this);

		iniciarTransaccion();

		int numSecuencia = nSecDBEntity.incrementarNumeroSecPrestamo(vo
				.getAno());
		vo.setOrden(numSecuencia);

		prestamoDBEntity.insertPrestamo(vo);

		// Insertar unidades Documentales

		List detallesPrestamo = vo.getDetallesPrestamo();
		if (ListUtils.isNotEmpty(detallesPrestamo)) {

			for (Iterator iterator = detallesPrestamo.iterator(); iterator
					.hasNext();) {
				DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) iterator
						.next();

				detallePrestamo.setIdsolicitud(vo.getId());
				nuevoDetallePrestamo(detallePrestamo);
			}
		}

		String codigoPrestamoArchivo = CodigoTransferenciaUtils
				.getCodigoTransferenciaFromVO(vo, getServiceSession());
		int pos = codigoPrestamoArchivo.lastIndexOf("/");
		if (pos != -1) {
			codigoPrestamoArchivo = codigoPrestamoArchivo.substring(0, pos + 1)
					+ numSecuencia;
		}

		// registro de la creacion del prestamo
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_PRESTAMO, vo.getId())
				.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
						codigoPrestamoArchivo)
				.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_RESERVA,
						vo.getFinicialreserva() != null ? "Reserva"
								: "No Reserva");

		commit();
	}

	/**
	 * Realiza la actualizacion de un prestamo, actualizando su estado y la
	 * fecha de modificacion del estado, adem�s de los datos que se
	 * modificaron en el formulario.
	 * 
	 * @param vo
	 *            Prestamo que se desea actualizar
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la actualizacion porque el prestamo
	 *             no es editable.
	 */
	public void actualizarPrestamo(PrestamoVO vo, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_EDICION_PRESTAMO);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.EDITAR_PRESTAMO_ACTION);
		// COmprobamos otras condiciones
		ConditionChecker.checkOnPrestamoEdit(vo, sc, this);

		prestamoDBEntity.updatePrestamo(vo);

		// registro de la modificacion del prestamo
		DataLoggingEvent data = event
				.getDataLoggingEvent(ArchivoObjects.OBJECT_PRESTAMO, vo.getId())
				// .addDetalle(ArchivoDetails.SOLICITUDES_PRESTAMO, vo.toXML());

				.addDetalleNoVacio(
						locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO,
						CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
								vo, getServiceSession()))
				.addDetalleNoVacio(locale,
						ArchivoDetails.SOLICITUDES_ORGANO_PRESTAMO,
						vo.getNorgsolicitante())
				.addDetalleNoVacio(locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_SOLICITANTE,
						vo.getNusrsolicitante())
				.addDetalleNoVacio(locale,
						ArchivoDetails.SOLICITUDES_ARCHIVO_PRESTAMO,
						vo.getArchivo().getNombre());
		if (vo.getFinicialreserva() == null) {
			data.addDetalleNoVacio(locale,
					ArchivoDetails.SOLICITUDES_FECHA_INICIAL_RESERVA,
					DateUtils.formatDate(vo.getFinicialreserva()));
		}
	}

	public Collection getDetallesPrestamo(String codigoPrestamo) {

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();
		// GestionCuadroClasificacionBI managerCuadroClf =
		// ServiceRepository.getInstance(getServiceSession()).lookupGestionCuadroClasificacionBI();

		Collection detallesPrestamos = detallePrestamoDBEntity
				.getDetallesXTipo(codigoPrestamo,
						DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

		Iterator it = detallesPrestamos.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO d = (DetallePrestamoVO) it.next();

			// Rellenar ubicaci�n
			d.setUbicacion(this.getUbicacionDetallePrestamo(d.getIdudoc(),
					d.getSignaturaudoc()));

			// Rellenar fechas de inicio y fin
			List listaFIni = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaInicial());
			List listaFFin = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaFinal());

			// Fecha de inicio
			if (listaFIni != null && listaFIni.size() > 0) {
				CampoFechaVO cfini = (CampoFechaVO) listaFIni.get(0);
				if (cfini != null)
					d.setFechaini(cfini.getValor());
			}

			// Fecha de fin
			if (listaFFin != null && listaFFin.size() > 0) {
				CampoFechaVO cffin = (CampoFechaVO) listaFFin.get(0);
				if (cffin != null)
					d.setFechafin(cffin.getValor());
			}

		}

		// return detallePrestamoDBEntity.getDetallesXTipo(codigoPrestamo,
		// DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

		return detallesPrestamos;

	}

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
	public Collection getUnidadesEntregadasLikeNumExp(String numexp, String like) {
		this.check(ServiciosSecurityManager.OBTENER_UDOCS_BY_NUMEXP_PRESTAMO_ACTION);

		return detallePrestamoDBEntity.busquedaEntregadaXNumExp(numexp, like);
	}

	/**
	 * Obtiene un listado de unidades documentales que pertenecen a un
	 * determinado fondo y tienen una determinada signatura.
	 * 
	 * @param fondo
	 *            Fondo del que deseamos obtener las unidades documentales.
	 * @param signatura
	 *            de la udoc
	 * @param idArchivo
	 *            Identificador del archivo del que deseamos obtener las
	 *            unidades documentales.
	 * @param like
	 *            Tipo de operador like
	 * @param pageInfo
	 *            Informacin sobre la paginacion de la tabla de resultados
	 * @return Unidades documentales que pertenecen al expediente.
	 * @throws TooManyResultsException
	 *             si el n�mero de resultados excede el m�ximo.
	 */
	/*
	 * public Collection getUnidadesLikeFondoSignatura(String fondo, String
	 * signatura, String idArchivo, String like, PageInfo pageInfo) throws
	 * TooManyResultsException {
	 * this.check(ServiciosSecurityManager.OBTENER_UDOCS_BY_NUMEXP_PRESTAMO_ACTION
	 * );
	 * 
	 * return detallePrestamoDBEntity.busquedaXFondoSignatura(fondo,signatura,
	 * idArchivo,like,pageInfo); }
	 */
	/**
	 * Obtiene un listado de unidades documentales entregadas que pertenecen a
	 * un determinado fondo y tienen una determinada signatura.
	 * 
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @param Like
	 *            para realizar la busqueda
	 * @return Unidades documentales que pertenecen al expediente.
	 */
	/*
	 * public Collection getUnidadesEntregadasLikeFondoSignatura(String fondo,
	 * String signatura, String like) { return
	 * detallePrestamoDBEntity.busquedaEntregadasXFondoSignatura
	 * (fondo,signatura,like); }
	 */
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
			String signatura, String type) {
		return detallePrestamoDBEntity.getUnidadEntregada(id, idUdoc,
				signatura, type);
	}

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
			String like) {
		return detallePrestamoDBEntity.busquedaEntregadasXSignatura(signatura,
				like);
	}

	public Collection getDetallesPrestamoAutorizadas(String codigoPrestamo) {
		ArrayList estados = new ArrayList();
		estados.add(new Integer(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA));

		return detallePrestamoDBEntity.getDetallesSolicitudXEstado(estados,
				codigoPrestamo, DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
	}

	public Collection getDetallesPrestamoAutorizadasEntregadas(
			String codigoPrestamo) {
		ArrayList estados = new ArrayList();
		estados.add(new Integer(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA));
		estados.add(new Integer(PrestamosConstants.ESTADO_DETALLE_ENTREGADA));

		return detallePrestamoDBEntity.getDetallesSolicitudXEstado(estados,
				codigoPrestamo, DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
	}

	/**
	 * Obtiene un listado de las unidades documentales devueltas de un
	 * determinado prestamo con su ubicacion en el deposito
	 * 
	 * @param codigoPrestamo
	 *            Identificador del prestamo del que deseamos obtener los
	 *            detalles devueltos
	 * @return Listado de los detalles del prestamos
	 */
	public Collection getDetallesPrestamoDevueltas(String codigoPrestamo) {
		this.check(ServiciosSecurityManager.OBTENER_UDOCS_DEVUELTAS_PRESTAMO_ACTION);

		Collection detallesPrestamos = detallePrestamoDBEntity
				.getUnidadesDevueltas(codigoPrestamo,
						DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

		Iterator it = detallesPrestamos.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO d = (DetallePrestamoVO) it.next();

			d.setUbicacion(this.getUbicacionDetallePrestamo(d.getIdudoc(),
					d.getSignaturaudoc()));
		}

		return detallesPrestamos;
	}

	/**
	 * Obtiene un listado de las unidades documentales entregadas de un
	 * determinado prestamo
	 * 
	 * @param codigoPrestamo
	 *            Identificador del prestamo del que deseamos obtener los
	 *            detalles entregados
	 * @return Listado de los detalles entregados del prestamos
	 * @throws Exception
	 *             Si se produce un error recuperando los detalles de la bd.
	 */
	public Collection getDetallesPrestamoEntregadas(String codigoPrestamo) {
		ArrayList estados = new ArrayList();

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();
		estados.add(new Integer(PrestamosConstants.ESTADO_DETALLE_ENTREGADA));

		Collection detallesPrestamos = detallePrestamoDBEntity
				.getDetallesSolicitudXEstado(estados, codigoPrestamo,
						DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
		// Para cada detalle , calculamos su ubicacion y rellenamos las fechas
		// extremas de sus unidades documentales
		Iterator it = detallesPrestamos.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO d = (DetallePrestamoVO) it.next();

			// Rellenar ubicaci�n
			d.setUbicacion(this.getUbicacionDetallePrestamo(d.getIdudoc(),
					d.getSignaturaudoc()));

			// Rellenar fechas de inicio y fin
			List listaFIni = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaInicial());
			List listaFFin = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaFinal());

			// Fecha de inicio
			if (listaFIni != null && listaFIni.size() > 0) {
				CampoFechaVO cfini = (CampoFechaVO) listaFIni.get(0);
				if (cfini != null)
					d.setFechaini(cfini.getValor());
			}

			// Fecha de fin
			if (listaFFin != null && listaFFin.size() > 0) {
				CampoFechaVO cffin = (CampoFechaVO) listaFFin.get(0);
				if (cffin != null)
					d.setFechafin(cffin.getValor());
			}
		}

		return detallesPrestamos;
		// return
		// detallePrestamoDBEntity.getDetallesSolicitudXEstado(estados,codigoPrestamo,DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
	}

	public void eliminarDetallesPrestamo(String idPrestamo, String[] idsudocs,
			String[] signaturasudocs) {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_BAJA_UDOC);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.DELETE_DETALLES_PRESTAMO_ACTION);

		iniciarTransaccion();

		PrestamoVO prestamo = getPrestamo(idPrestamo);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, idPrestamo);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		for (int i = 0; i < idsudocs.length; i++) {
			detallePrestamoDBEntity.deleteDetalle(idPrestamo, idsudocs[i],
					signaturasudocs[i], DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

			// Creamos el evento de Loggin de auditoria con sus datos asociados
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DETALLE_PRESTAMO, idsudocs[i]);
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					signaturasudocs[i]);
		}

		commit();
	}

	public void nuevoDetallePrestamo(DetallePrestamoVO detallePrestamo) {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ALTA_UDOC);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.ANIADIR_UDOC_PRESTAMO_ACTION);

		detallePrestamoDBEntity.insertDetallePrestamo(detallePrestamo);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
				detallePrestamo.getIdudoc());

		PrestamoVO prestamo = getPrestamo(detallePrestamo.getIdsolicitud());

		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
				detallePrestamo.getSignaturaudoc());
	}

	/**
	 * Eliminar un conjunto de prestamos dados. Comprueba si los prestamos
	 * pueden ser borrados y en caso afirmativo procede a su borrado.
	 * 
	 * @param prestamosAEliminar
	 *            Listado de identificadores de los prestamos que se desea
	 *            eliminar.
	 * @exception PrestamoActionNotAllowedException
	 *                Si no se puede realizar el borrado de los prestamos por el
	 *                estado en el que se encuentra alguno de ellos.
	 */
	public void eliminarPrestamos(String[] prestamosAEliminar)
			throws PrestamoActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_BAJA_PRESTAMO);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_LISTADO_PRESTAMOS, null);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.ELIMINAR_PRESTAMO_ACTION);
		// comprobar sin son prestamos borrables
		ConditionChecker.checkOnEliminarPrestamos(prestamosAEliminar, this);

		iniciarTransaccion();

		for (int i = 0; i < prestamosAEliminar.length; i++) {
			detallePrestamoDBEntity.deleteDetalles(prestamosAEliminar[i],
					DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

			PrestamoVO prestamo = getPrestamo(prestamosAEliminar[i]);
			prestamoDBEntity.deletePrestamo(prestamosAEliminar[i]);

			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
					CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
							prestamo, getServiceSession()));
		}

		commit();
	}

	public void autorizarProrrogas(String idProrroga,
			String prestamoAAutorizar, Date fechaFinProrroga)
			throws PrestamoActionNotAllowedException {

		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.AUTORIZAR_PRORROGA_ACTION);

		// Obtenemos el prestamo
		PrestamoVO prestamo = getPrestamo(prestamoAAutorizar);

		if (fechaFinProrroga == null) {
			// 1- Calculamos la fecha de fin de prestamo
			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_PRORROGA);

			Calendar fechafinal = new GregorianCalendar();
			// Si se ha superado la fecha de devolucion->Cogemos la fecha final
			if (!prestamo.getFmaxfinprestamo().after(DBUtils.getFechaActual()))
				fechafinal.setTime(DBUtils.getFechaActual());
			else
				fechafinal.setTime(prestamo.getFmaxfinprestamo());
			fechafinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
					* PrestamosConstants.HORAS_DIA);

			fechaFinProrroga = fechafinal.getTime();
		}

		// 2-Obtenemos los detalles
		Collection detalles = getDetallesPrestamo(prestamoAAutorizar);

		// Obtenemos las prorrogas del prestamo
		ProrrogaVO prorroga = prorrogaDBEntity
				.getProrrogaSolicitada(prestamoAAutorizar);
		prorroga.setEstado(PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA);
		prorroga.setFestado(DateUtils.getFechaActual());
		prorroga.setMotivoRechazo("");
		prorroga.setFechaFinProrroga(fechaFinProrroga);

		iniciarTransaccion();

		// Actualizamos la prorroga
		prorrogaDBEntity.updateProrroga(prorroga);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, null);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		// Actualizamos los detalles del prestamo
		Iterator itDetalles = detalles.iterator();
		while (itDetalles.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) itDetalles.next();
			detalle.setFfinaluso(fechaFinProrroga);

			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());
			data.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_PRORROGADO,
					"Udoc prorrogada");

			detallePrestamoDBEntity.actualizarDetalle(detalle);
		}

		prestamo.setFmaxfinprestamo(fechaFinProrroga);
		// 3- Actualizamos el detalle
		prestamoDBEntity.updatePrestamo(prestamo);

		commit();
	}

	public void denegarProrrogas(String prestamoADenegar, String motivo,
			String idMotivo) throws PrestamoActionNotAllowedException {
		this.check(ServiciosSecurityManager.AUTORIZAR_PRORROGA_ACTION);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos otras condiciones
		ConditionChecker.checkOnDenegarProrroga(prestamoADenegar, this);

		// Obtenemos las prorrogas a denegar
		ProrrogaVO prorroga = prorrogaDBEntity
				.getProrrogaSolicitada(prestamoADenegar);
		prorroga.setEstado(PrestamosConstants.ESTADO_PRORROGA_DENEGADA);
		prorroga.setFestado(DateUtils.getFechaActual());
		prorroga.setMotivoRechazo(motivo);
		prorroga.setIdMotivo(idMotivo);

		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRORROGA, prorroga.getId());

		PrestamoVO prestamo = getPrestamo(prorroga.getIdPrestamo());

		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_PRORROGA_DENEGADA,
				"Prorroga denegada");

		prorrogaDBEntity.updateProrroga(prorroga);

		// 2-Obtenemos los detalles
		Collection detalles = getDetallesPrestamo(prestamoADenegar);

		// Actualizamos los detalles del prestamo
		Iterator itDetalles = detalles.iterator();
		while (itDetalles.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) itDetalles.next();

			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());
		}
	}

	public void enviarPrestamo(String idPrestamo)
			throws PrestamoActionNotAllowedException {
		PrestamoVO prestamo = prestamoDBEntity.getPrestamo(idPrestamo);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ENVIAR_PRESTAMO_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnEnviarPrestamo(prestamo, this);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO);
		// registro del envio del prestamo
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, idPrestamo);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		prestamoDBEntity.enviarPrestamo(prestamo, getServiceClient()
				.getUserType());
	}

	public ProrrogaVO getProrrogaSolicitada(String idPrestamo) {
		return prorrogaDBEntity.getProrrogaSolicitada(idPrestamo);
	}

	public void solicitarProrroga(ProrrogaVO prorrogaVO)
			throws PrestamoActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS);
		Locale locale = getServiceClient().getLocale();

		String idPrestamo = prorrogaVO.getIdPrestamo();
		this.check(ServiciosSecurityManager.SOLICITAR_PRORROGA_ACTION);
		ConditionChecker.checkOnSolicitarProrroga(idPrestamo, this);

		// ProrrogaVO prorrogaVO = new ProrrogaVO();
		// prorrogaVO.setIdPrestamo(idPrestamo);
		prorrogaVO.setEstado(PrestamosConstants.ESTADO_PRORROGA_SOLICITADA);
		prorrogaVO.setFestado(DateUtils.getFechaActual());
		prorrogaVO.setMotivoRechazo("");

		prorrogaDBEntity.insertProrroga(prorrogaVO);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, idPrestamo);
		PrestamoVO prestamo = getPrestamo(idPrestamo);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		// faltan ids de unidades documentales
		Collection uDocsPrestamo = getDetallesPrestamo(idPrestamo);
		Iterator it = uDocsPrestamo.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) it.next();

			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());
		}

	}

	public boolean yaTieneProrroga(String idPrestamo) {
		boolean resultado = false;

		ProrrogaVO prorrogaVO = prorrogaDBEntity
				.getProrrogaSolicitada(idPrestamo);

		// Comprobamos si existe la prorroga
		if (prorrogaVO != null && prorrogaVO.getId() != null)
			resultado = true;
		else
			resultado = false;

		return resultado;
	}

	public Date yaTieneProrrogaFecha(String idPrestamo) {
		ProrrogaVO prorrogaVO = prorrogaDBEntity
				.getProrrogaSolicitada(idPrestamo);

		Date salida = null;
		if (prorrogaVO != null && prorrogaVO.getId() != null)
			salida = prorrogaVO.getFestado();

		return salida;
	}

	public int devolverProrrogas(String idPrestamo) {
		String idProrrogas[] = { idPrestamo };

		Collection prorrogas = prorrogaDBEntity.getProrrogas(idProrrogas);
		int numProrrogas = 0;

		if (prorrogas != null) {
			Iterator it = prorrogas.iterator();

			while (it.hasNext()) {
				ProrrogaVO prorrogaVO = (ProrrogaVO) it.next();
				if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA) {
					numProrrogas = numProrrogas + 1;
				}
			}
		}

		return numProrrogas;
	}

	public boolean yaTieneProrrogasDenegadas(String idPrestamo) {
		String idProrrogas[] = { idPrestamo };

		Collection prorrogas = prorrogaDBEntity.getProrrogas(idProrrogas);
		if (prorrogas != null) {
			Iterator it = prorrogas.iterator();
			while (it.hasNext()) {
				ProrrogaVO prorrogaVO = (ProrrogaVO) it.next();
				if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_DENEGADA) {
					return true;
				}
			}
		}

		return false;
	}

	public String motivoProrrogaDenegada(String idPrestamo) {
		String idProrrogas[] = { idPrestamo };

		Collection prorrogas = prorrogaDBEntity.getProrrogas(idProrrogas);
		if (prorrogas != null) {
			Iterator it = prorrogas.iterator();

			while (it.hasNext()) {
				ProrrogaVO prorrogaVO = (ProrrogaVO) it.next();
				if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_DENEGADA) {
					return prorrogaVO.getMotivoRechazo();
				}
			}
		}

		return "";
	}

	public Date fechaProrrogaDenegada(String idPrestamo) {
		String idProrrogas[] = { idPrestamo };

		Collection prorrogas = prorrogaDBEntity.getProrrogas(idProrrogas);
		if (prorrogas != null) {
			Iterator it = prorrogas.iterator();

			while (it.hasNext()) {
				ProrrogaVO prorrogaVO = (ProrrogaVO) it.next();
				if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_DENEGADA) {
					return prorrogaVO.getFestado();
				}
			}
		}

		return null;
	}

	public boolean yaTieneProrrogasAutorizadas(String idPrestamo) {
		String idProrrogas[] = { idPrestamo };

		Collection prorrogas = prorrogaDBEntity.getProrrogas(idProrrogas);
		if (prorrogas != null) {
			Iterator it = prorrogas.iterator();

			while (it.hasNext()) {
				ProrrogaVO prorrogaVO = (ProrrogaVO) it.next();
				if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA) {
					return true;
				}
			}
		}

		return false;
	}

	public void denegarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo)
			throws PrestamoActionNotAllowedException {
		this.check(ServiciosSecurityManager.DENEGAR_PRESTAMO_ACTION);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos otras condiciones
		ConditionChecker.checkOnAutorizarDetalle(
				prestamo,
				(List) getServiceClient().getProperties().get(
						PrestamosConstants.PROPERTY_ARCHIVOS_CUSTODIA),
				getServiceClient());

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO);

		detallePrestamoDBEntity.denegarDetallePrestamo(prestamo, idudoc,
				signaturaudoc, motivo, idMotivoRechazo);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DETALLE_PRESTAMO, idudoc);
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO,
				"Detalle Denegado");

		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
				signaturaudoc);
	}

	public void autorizarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		this.check(ServiciosSecurityManager.AUTORIZAR_PRESTAMO_ACTION);
		Locale locale = getServiceClient().getLocale();

		boolean disponible = true;
		PrestamoActionNotAllowedException prestamoActionNotAllowedException = null;

		try {
			iniciarTransaccion();

			// Comprobamos otras condiciones
			ConditionChecker.checkOnAutorizarDetalle(
					prestamo,
					(List) getServiceClient().getProperties().get(
							PrestamosConstants.PROPERTY_ARCHIVOS_CUSTODIA), sc);

			// Creamos el evento de Loggin de auditoria
			LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO);

			DetallePrestamoVO detalle = getDetallePrestamo(prestamo.getId(),
					idudoc, signaturaudoc);

			detallePrestamoDBEntity.bloquearDetallePrestamo(prestamo, idudoc,
					signaturaudoc);

			if (prestamo.tieneReserva()) {
				disponible = isDetalleDisponible(detalle,
						prestamo.getFinicialreserva(),
						prestamo.getFfinalreserva(), prestamo.tieneReserva());
			} else {
				final String dias_reserva = PropertyHelper
						.getProperty(PropertyHelper.PLAZO_PRESTAMO);

				Calendar fechafinal = new GregorianCalendar();
				fechafinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
						* PrestamosConstants.HORAS_DIA);

				disponible = isDetalleDisponible(detalle,
						DBUtils.getFechaActual(), fechafinal.getTime(),
						prestamo.tieneReserva());
			}

			if (disponible) {

				detallePrestamoDBEntity.autorizarDetallePrestamo(prestamo,
						idudoc, signaturaudoc);

				DataLoggingEvent data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_PRESTAMO, idudoc);
				data.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO,
						"Detalle Aceptado");

				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
						CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
								prestamo, getServiceSession()));
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						signaturaudoc);

				commit();
			} else {
				prestamoActionNotAllowedException = new PrestamoActionNotAllowedException(
						ArchivoErrorCodes.ERROR_AUTORIZACION_DETALLE_NO_DISPONIBLE,
						PrestamoActionNotAllowedException.XDETALLADA_DETALLE_NO_DISPONIBLE);
				rollback();
			}
		} catch (Throwable t) {
			rollback();
		}

		if (!disponible) {
			throw prestamoActionNotAllowedException;
		}
	}

	// public void devolverDetallePrestamo(PrestamoVO prestamo, String idudoc,
	// String signaturaudoc) throws PrestamoActionNotAllowedException{
	public void devolverDetallePrestamo(DetallePrestamoVO detalle)
			throws PrestamoActionNotAllowedException {

		if (detalle == null)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_DEVOLUCION_Y_NO_ENTREGADA,
					PrestamoActionNotAllowedException.XESTADO);

		detalle.setEstado(PrestamosConstants.ESTADO_SOLICITUD_DEVUELTA);
		detalle.setFestado(DateUtils.getFechaActual());

		detallePrestamoDBEntity.actualizarDetalle(detalle);
	}

	/**
	 * Realiza la devolucion de las unidades documentales seleccionadas.
	 * 
	 * @param udocs
	 *            Unidades documentales a devolver
	 * @param unidadesDevolverRevDoc
	 *            unidades documentales para las que se debe crear una
	 *            revisi�n de documentaci�n
	 */
	private void devolverUnidadesDocumentalesComun(ArrayList udocs,
			Map unidadesDevolverRevDoc)
			throws PrestamoActionNotAllowedException {

		this.check(ServiciosSecurityManager.DEVOLVER_UDOCS_PRESTAMO_ACTION);
		Locale locale = getServiceClient().getLocale();

		GestionConsultasBI serviceConsultas = ServiceRepository.getInstance(
				getServiceSession()).lookupGestionConsultasBI();

		// Iniciamos la transaccion
		iniciarTransaccion();

		for (int i = 0; i < udocs.size(); i++) {
			BusquedaDetalleVO bdp = (BusquedaDetalleVO) udocs.get(i);

			LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_DEVOLUCION_UDOCS);

			if (bdp.getTiposolicitud() == Integer
					.parseInt(DetalleDBEntity.TIPO_DETALLE_PRESTAMO)) {
				// Devolucion de los detalles de uno o varios prestamos
				// Obtenemos el detalle del prestamo mediante su identificador,
				// que ahora incluira el id de prestamo, ya que
				// las cajas pueden estar en mas de un prestamos compartiendo id
				// de unidad documental
				// DetallePrestamoVO detalle =
				// (DetallePrestamoVO)detallePrestamoDBEntity.getDetalleEntregado(bdp.getIdudoc(),bdp.getSignaturaudoc(),DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
				DetallePrestamoVO detalle = (DetallePrestamoVO) detallePrestamoDBEntity
						.getDetalleEntregado(bdp.getIdSolicitud(),
								bdp.getIdudoc(), bdp.getSignaturaudoc(),
								DetalleDBEntity.TIPO_DETALLE_PRESTAMO);

				// Obtenemos el prestamo
				PrestamoVO prestamo = this
						.getPrestamo(detalle.getIdsolicitud());
				// int numDetallesDevueltos =
				// detallePrestamoDBEntity.getNumDetallesDevueltos(prestamo.getId());

				DataLoggingEvent data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
						detalle.getIdudoc());
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
						CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
								prestamo, getServiceSession()));
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						detalle.getSignaturaudoc());

				if (detalle.isSubtipoCaja()
						&& StringUtils.isNotEmpty(detalle.getExpedienteudoc())) {
					data.addDetalle(locale,
							ArchivoDetails.SOLICITUDES_DETALLE_NUMEXP,
							detalle.getExpedienteudoc());
				}

				// Devolvemos el detalle y lo aniadimos a los que se han
				// procesado bien
				devolverDetallePrestamo(detalle);

				// Actualizamos la fecha de estado del objeto de busqueda que
				// identifica al detalle
				bdp.setFestado(DateUtils.getFechaActual());
				// devolverDetallePrestamo(prestamo, detalle.getIdudoc(),
				// detalle.getSignaturaudoc());
				// numDetallesDevueltos++;
				bdp.setDescripcion(detalle.getInformacion());

				int numDetallesEntregados = detallePrestamoDBEntity
						.getNumDetallesEntregados(prestamo.getId());

				boolean prestamoModificado = false;

				if (numDetallesEntregados == 0) {
					prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO);
					prestamo.setFestado(DateUtils.getFechaActual());
					prestamoModificado = true;
				} else if (numDetallesEntregados > 0) {
					prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO);
					prestamo.setFestado(DateUtils.getFechaActual());
					prestamoModificado = true;
				}

				if (prestamoModificado) {
					data = event.getDataLoggingEvent(
							ArchivoObjects.OBJECT_PRESTAMO, prestamo.getId());
					data.addDetalle(locale,
							ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
							CodigoTransferenciaUtils
									.getCodigoTransferenciaFromVO(prestamo,
											getServiceSession()));

					prestamoDBEntity.updatePrestamo(prestamo);
				}

				String idcompuesto = detalle.getIdudoc() + "|"
						+ detalle.getSignaturaudoc();

				// Comprobar si hay que crear una revisi�n de documentaci�n
				// y si es necesario crearla
				if (unidadesDevolverRevDoc != null) {
					RevisionDocumentacionVO revisionDocumentacionVO = (RevisionDocumentacionVO) unidadesDevolverRevDoc
							.get(idcompuesto);
					if (revisionDocumentacionVO != null) {
						revisionDocumentacionVO.setExpedienteUdoc(detalle
								.getExpedienteudoc());
						revisionDocumentacionVO.setIdUdoc(detalle.getIdudoc());
						revisionDocumentacionVO.setTitulo(detalle.getTitulo());
						revisionDocumentacionVO.setSignaturaUdoc(detalle
								.getSignaturaudoc());
						revisionDocumentacionVO.setEstado(EstadoRevDoc.ABIERTA
								.getIdentificador());
						revisionDocumentacionVO.setFEstado(DateUtils
								.getFechaActual());

						insertRevisionDocumentacion(revisionDocumentacionVO);
					}
				}

			} else {
				// Devoluci�n de una consulta
				// Obtenemos el detalle de la consulta mediante su id
				DetalleConsultaVO detalle = (DetalleConsultaVO) detallePrestamoDBEntity
						.getDetalleEntregado(bdp.getIdSolicitud(),
								bdp.getIdudoc(), bdp.getSignaturaudoc(),
								DetalleDBEntity.TIPO_DETALLE_CONSULTA);

				// Obtenemos la consulta
				ConsultaVO consulta = serviceConsultas.getConsulta(detalle
						.getIdsolicitud());

				// Actualizamos la fecha de estado del objeto de b�squeda que
				// identifica al detalle
				bdp.setFestado(DateUtils.getFechaActual());

				// int numDetallesDevueltos =
				// detallePrestamoDBEntity.getNumDetallesDevueltos(consulta.getId());

				DataLoggingEvent data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_CONSULTA,
						detalle.getIdudoc());
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
						CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
								consulta, getServiceSession()));
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						detalle.getSignaturaudoc());

				// Devolvemos el detalle
				try {
					// serviceConsultas.devolverDetalleConsulta(consulta,bdp.getIdudoc(),bdp.getSignaturaudoc());
					serviceConsultas.devolverDetalleConsulta(detalle);

				} catch (ConsultaActionNotAllowedException canae) {
					throw new PrestamoActionNotAllowedException(
							canae.getCodError(), canae.getMotivo());
				}
				// numDetallesDevueltos++;

				int numDetallesEntregados = detallePrestamoDBEntity
						.getNumDetallesEntregados(consulta.getId());

				boolean consultaModificada = false;

				if (numDetallesEntregados == 0) {
					consulta.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO);
					consulta.setFestado(DateUtils.getFechaActual());
					consultaModificada = true;
				} else if (numDetallesEntregados > 0) {
					consulta.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO);
					consulta.setFestado(DateUtils.getFechaActual());
					consultaModificada = true;
				}

				if (consultaModificada) {
					data = event.getDataLoggingEvent(
							ArchivoObjects.OBJECT_CONSULTA, consulta.getId());
					data.addDetalle(locale,
							ArchivoDetails.SOLICITUDES_CONSULTA_ID,
							CodigoTransferenciaUtils
									.getCodigoTransferenciaFromVO(consulta,
											getServiceSession()));

					consultaDBEntity.updateConsulta(consulta);
				}
			}
		}

		commit();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#devolverUnidadesDocumentales(java.util.ArrayList
	 * , java.util.Map)
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs,
			Map unidadesDevolver) throws PrestamoActionNotAllowedException {
		devolverUnidadesDocumentalesComun(udocs, unidadesDevolver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#devolverUnidadesDocumentales(java.util.ArrayList
	 * )
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs)
			throws PrestamoActionNotAllowedException {
		devolverUnidadesDocumentalesComun(udocs, null);
	}

	public void entregarPrestamo(String idPrestamo, Date fechaDevolucion)
			throws PrestamoActionNotAllowedException {
		Collection udocsEntregadas = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.ENTREGAR_PRESTAMO_ACTION);
		// Realizamos comprobaciones adicionales
		ConditionChecker.checkOnEntrega(idPrestamo, this, getServiceClient());

		// Iniciamos la transaction
		iniciarTransaccion();

		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ENTREGA_UDOCS_PRESTAMO);

		// Obtenemos el prestamo asoicado al id
		PrestamoVO prestamo = this.getPrestamo(idPrestamo);
		// Obtenemos las udocs del prestamo a entregar
		Collection detalles = this.getDetallesPrestamo(idPrestamo);

		// Se entregan solo las udocs autorizadas
		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) it.next();

			if (detalle.getEstado() == PrestamosConstants.ESTADO_SOLICITUD_AUTORIZADA) {
				detalle.setEstado(PrestamosConstants.ESTADO_SOLICITUD_ENTREGADA);
				detalle.setFestado(DateUtils.getFechaActual());

				DataLoggingEvent data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
						detalle.getIdudoc());
				// data.addDetalle(ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				// CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
				// getServiceSession()) );
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						detalle.getSignaturaudoc());
				if (fechaDevolucion != null) {
					detalle.setFfinaluso(fechaDevolucion);
				}

				// Actualizamos el detalle
				detallePrestamoDBEntity.actualizarDetalle(detalle);
				// A�adimos la udoc a las entregadas
				udocsEntregadas.add(detalle);
			}
		}

		prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO);
		prestamo.setFentrega(DBUtils.getFechaActual());
		prestamo.setFestado(DateUtils.getFechaActual());

		if (fechaDevolucion != null) {
			prestamo.setFmaxfinprestamo(fechaDevolucion);
		}

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, prestamo.getId());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		// Actualizamos el prestamos
		prestamoDBEntity.updatePrestamo(prestamo);

		commit();
	}

	public DetallePrestamoVO getDetallePrestamo(String codigoPrestamo,
			String idudoc, String signatura) throws DetalleNotFoundException {
		return (DetallePrestamoVO) detallePrestamoDBEntity.getDetalle(
				codigoPrestamo, idudoc, signatura,
				DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
	}

	public DetallePrestamoVO getDetallePrestamoEntregado(String identificacion) {
		return (DetallePrestamoVO) detallePrestamoDBEntity
				.getDetalleEntregadoXID(identificacion,
						DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
	}

	public String getUbicacionDetallePrestamo(String idudoc, String signatura) {
		return detallePrestamoDBEntity.getUbicacionDetalle(idudoc, signatura);
	}

	public UDocEnUiDepositoVO getDescripcionUdocDeposito(String idudoc,
			String signatura) {
		return uDocEnUiDepositoDbEntity.getUDocByIdAndSignatura(idudoc,
				signatura);
	}

	public Collection getMotivosRechazo() {
		return motivoRechazoDBEntity
				.getMotivos(MotivoRechazoDBEntity.TIPO_SOLICITUD_PRESTAMOS);
	}

	public Collection getMotivosRechazoProrroga() {
		return motivoRechazoDBEntity
				.getMotivos(MotivoRechazoDBEntity.TIPO_PRORROGA);
	}

	public void reclamar(int veces, String idPrestamo)
			throws PrestamoActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_RECLAMACION_UDOCS_PRESTAMO);
		Locale locale = getServiceClient().getLocale();

		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.RECLAMAR_ACTION);

		ConditionChecker.checkOnReclamar(idPrestamo, this);

		PrestamoVO prestamo = getPrestamo(idPrestamo);
		prestamo.setNumreclamaciones(veces);
		if (veces == 1)
			prestamo.setFreclamacion1(DBUtils.getFechaActual());
		if (veces == 2)
			prestamo.setFreclamacion2(DBUtils.getFechaActual());

		prestamoDBEntity.updatePrestamo(prestamo);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, idPrestamo);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_RECLAMACION_NUMBER, ""
						+ veces + " reclamacion");

		// Obtenemos las udocs del prestamo
		Collection detalles = this.getDetallesPrestamo(idPrestamo);

		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) it.next();

			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());
		}
	}

	/**
	 * Obtiene todos los posibles estados en los que se puede encontrar un
	 * prestamo
	 * 
	 * @return listado de los posibles estados de un prestamo
	 * @throws Si
	 *             se produce algun error durante la recuperacion
	 */
	public Collection getEstadosPrestamo() {
		ArrayList estados = new ArrayList();

		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_RESERVADO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_DENEGADO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO));
		estados.add(getEstadoVO(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO));

		return estados;
	}

	private EstadoVO getEstadoVO(int id) {
		Locale locale = getServiceClient().getLocale();
		String label;
		String key = PrestamosConstants.LABEL_ESTADO_PRESTAMOS_BASE + id;
		label = Messages.getString(key, locale);

		return new EstadoVO(label, id);
	}

	/**
	 * Asigna los estados de los prestamos a las notas de un prestamo de un
	 * listado de prestamos
	 * 
	 * @param prestamos
	 *            Listado de prestamos que deseamos modificar su estado
	 * @return Listado de prestamos con las notas de los estados.
	 * @throws Exception
	 *             Si se produce algun problema durante la generaci�n de las
	 *             norta
	 */
	public void asignaEstadosAPrestamos(Collection prestamos) {
		// ArrayList prestamosConNotas = new ArrayList();
		if (prestamos != null) {
			Iterator it = prestamos.iterator();

			while (it.hasNext()) {
				PrestamoVO prestamo = (PrestamoVO) it.next();
				String nota = "";

				if (prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO
						|| prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO) {
					if (prestamo.getFreclamacion2() != null)
						nota += PrestamosConstants.SITUACION_RECLAMADO_DOS_VECES;
					else if (prestamo.getFreclamacion1() != null)
						nota += PrestamosConstants.SITUACION_RECLAMADO_UNA_VEZ;
					else if (prestamo.getFmaxfinprestamo() != null
							&& prestamo.getFmaxfinprestamo().before(
									Calendar.getInstance().getTime()))
						nota += PrestamosConstants.SITUACION_CADUCADO;

					// Comprobamos si tiene prorrogas o prorrogas denegadas
					if (yaTieneProrroga(prestamo.getId()))
						nota = PrestamosConstants.SITUACION_PRORROGA_SOLICITADA
								+ "-" + nota;
					else if (yaTieneProrrogasDenegadas(prestamo.getId()))
						nota = PrestamosConstants.SITUACION_PRORROGA_DENEGADA
								+ "-" + nota;
				}

				prestamo.setNotas(nota);
				// prestamosConNotas.add(prestamo);
			}// while
		}// prestamos!=null

		// return prestamosConNotas;
	}

	/**
	 * Devuelve un listado de las posibles notas de un prestamo
	 * 
	 * @return Listado de las posibles notas de un prestamo
	 */
	public Collection getNotas() {
		ArrayList notas = new ArrayList();

		notas.add(getNotaVO(PrestamosConstants.SITUACION_CADUCADO));
		notas.add(getNotaVO(PrestamosConstants.SITUACION_RECLAMADO_UNA_VEZ));
		notas.add(getNotaVO(PrestamosConstants.SITUACION_RECLAMADO_DOS_VECES));
		notas.add(getNotaVO(PrestamosConstants.SITUACION_PRORROGA_SOLICITADA));
		notas.add(getNotaVO(PrestamosConstants.SITUACION_PRORROGA_DENEGADA));

		return notas;
	}

	private NotaVO getNotaVO(String id) {

		Locale locale = getServiceClient().getLocale();
		String label;
		String key = PrestamosConstants.LABEL_SITUACION_PRESTAMOS_BASE + id;
		label = Messages.getString(key, locale);

		return new NotaVO(label, id);
	}

	/**
	 * Comprueba si una signatura de una unidad documental ha sido entregada.
	 * 
	 * @param signatura
	 *            Signatura de la unidad documental que se desea comprobar si ha
	 *            sido entregada.
	 * @return BusquedaDetallePrestamoVO si la u.doc con signatura fue entregada
	 *         o null si no ha sido entregada
	 */
	public BusquedaDetalleVO estaSignaturaEntregada(String signatura) {
		Collection detallesPrestamos = null;
		boolean encontrado = false;
		BusquedaDetalleVO resultado = null;

		if (signatura != null && signatura.trim().length() > 0) {
			detallesPrestamos = this.getUnidadesEntregadasLikeSignatura(
					signatura, DBUtils.CONTIENE_LIKE);

			if (detallesPrestamos != null) {
				Iterator it = detallesPrestamos.iterator();

				while (!encontrado && it.hasNext()) {
					BusquedaDetalleVO infoDetallePrestamo = (BusquedaDetalleVO) it
							.next();

					if (infoDetallePrestamo.getSignaturaudoc()
							.equalsIgnoreCase(signatura)) {
						encontrado = true;

						resultado = infoDetallePrestamo;
					}
				}
			}
		}

		return resultado;
	}// estaSignatura

	/**
	 * Obtiene los detalles(sobre la solicitud) de las unidades documentales que
	 * se van a entregar
	 * 
	 * @param udocs
	 *            Listado de las unidades documentales que se desean entregar
	 * @return Listado de las unidadesd documentales a entregar con los detalles
	 *         asociados.
	 * @throws Exception
	 *             Si se produce un error recuperando los detalles de las
	 *             unidades documentales
	 */
	public Collection getDetallesUDocs(List udocs) {
		ServiceRepository sr = ServiceRepository
				.getInstance(getServiceSession());
		GestionConsultasBI serviceConsultas = sr.lookupGestionConsultasBI();

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();

		if (udocs != null) {
			for (int i = 0; i < udocs.size(); i++) {
				BusquedaDetalleVO bdp = (BusquedaDetalleVO) udocs.get(i);

				if (bdp.getTiposolicitud() == Integer
						.parseInt(DetalleDBEntity.TIPO_DETALLE_PRESTAMO)) {
					PrestamoVO prestamo = getPrestamo(bdp.getIdSolicitud());

					bdp.setFentrega(prestamo.getFentrega());
					bdp.setFmaxfinprestamo(prestamo.getFmaxfinprestamo());
					bdp.setNusrsolicitante(prestamo.getNusrsolicitante());
					bdp.setNorgsolicitante(prestamo.getNorgsolicitante());
					bdp.setUbicacion(this.getUbicacionDetallePrestamo(
							bdp.getIdudoc(), bdp.getSignaturaudoc()));

				} else {
					ConsultaVO consulta = serviceConsultas.getConsulta(bdp
							.getIdSolicitud());

					bdp.setFentrega(consulta.getFentrega());
					bdp.setFmaxfinprestamo(consulta.getFmaxfinconsulta());
					bdp.setNusrsolicitante(consulta.getNusrconsultor());
					bdp.setNorgsolicitante(consulta.getNorgconsultor());
					bdp.setUbicacion(this.getUbicacionDetallePrestamo(
							bdp.getIdudoc(), bdp.getSignaturaudoc()));
				}

				List listaFIni = managerDescripcion.getFechaElemento(bdp
						.getIdudoc(), csa.getConfiguracionDescripcion()
						.getFechaExtremaInicial());
				List listaFFin = managerDescripcion.getFechaElemento(bdp
						.getIdudoc(), csa.getConfiguracionDescripcion()
						.getFechaExtremaFinal());

				// Fecha de inicio
				if (listaFIni != null && listaFIni.size() > 0) {
					CampoFechaVO cfini = (CampoFechaVO) listaFIni.get(0);
					if (cfini != null)
						bdp.setFechaini(cfini.getFechaIni());
				}

				// Fecha de fin
				if (listaFFin != null && listaFFin.size() > 0) {
					CampoFechaVO cffin = (CampoFechaVO) listaFFin.get(0);
					if (cffin != null)
						bdp.setFechafin(cffin.getFechaFin());
				}
			}
		}

		return udocs;
	}

	public Collection filtraPrestamosUsuario(String usuarioOrigen,
			Collection prestamos) {
		ArrayList prestamosFinal = new ArrayList();

		if (prestamos != null) {
			realizaFiltrado(usuarioOrigen, prestamos, prestamosFinal);
		}

		return prestamosFinal;
	}

	/**
	 * Encapsula la logica que realizar el filtrado
	 * 
	 * @param usuarioOrigen
	 * @param prestamos
	 * @param prestamosFinal
	 */
	private void realizaFiltrado(String usuarioOrigen, Collection prestamos,
			ArrayList prestamosFinal) {
		// Quitamos de todos los prestamos los que no sean de este usuario.
		Iterator prestamos_it = prestamos.iterator();
		while (prestamos_it.hasNext()) {
			PrestamoVO prestamo = (PrestamoVO) prestamos_it.next();

			// Comprobamos el estado del prestamo
			if (!(prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DENEGADO || prestamo
					.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO)) {
				// Comprobamos el usuario del prestamo
				if (usuarioOrigen != null && usuarioOrigen.trim().length() > 0) {
					if (prestamo.getIdusrgestor().equals(usuarioOrigen))
						prestamosFinal.add(prestamo);
				} else
					prestamosFinal.add(prestamo);
			}
		}
	}

	/**
	 * Obtiene los detalles de un prestamo en funcion del tipo de usuario
	 * conectado.
	 * 
	 * @param prestamo_VO
	 *            Prestamo del que deseamos obtener sus detalles
	 * @return Listado de los detalles de un prestamos por su usuario
	 */
	public Collection obtenerDetallesPrestamoByUsuario(PrestamoVO prestamo) {
		Collection detallesPrestamos = this.getDetallesPrestamo(prestamo
				.getId());

		// // Si el usuario es de deposito solo vera las autorizadas, para
		// entregarlas. Ademas, se calculara su ubicacion
		// if (
		// getServiceClient().hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)
		// ) {
		//
		// //Actualizamos la ubicacion de cada detalle de prestamo(de cada
		// u.documental)
		// Iterator detalles_it = detallesPrestamos.iterator();
		// while (detalles_it.hasNext()) {
		// DetallePrestamoVO dp = (DetallePrestamoVO) detalles_it.next();
		//
		// String ubicacion = this.getUbicacionDetallePrestamo(dp.getIdudoc(),
		// dp.getSignaturaudoc());
		// dp.setUbicacion(ubicacion);
		// }
		// }

		return detallesPrestamos;
	}

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
	public Collection asignarFondo(Collection detallesprestamos) {
		GestionFondosBI fondosService = getGestionFondosBI();
		ArrayList detallesprestamosFinal = new ArrayList();

		// Establecemos el fondo al detalle(No nos importa el idFondo en los
		// detalles. Lo actualizamos al titulo del fondo)
		if (detallesprestamos != null) {
			Iterator it = detallesprestamos.iterator();
			while (it.hasNext()) {
				BusquedaDetalleVO bdp = (BusquedaDetalleVO) it.next();

				FondoVO fondoauxiliar = fondosService.getFondoXId(bdp
						.getIdfondo());
				bdp.setFondo(fondoauxiliar.getTitulo());

				detallesprestamosFinal.add(bdp);
			}
		}// detallesPrestamos!=null

		return detallesprestamosFinal;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#getCountListadoGestionar()
	 */
	public int getCountListadoGestionar(String[] idsArchivo) {
		int prestamos = 0;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| getServiceClient().hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			estados = new String[3];
			estados[0] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			prestamos = this.getCountPrestamosXEstados(estados, idsArchivo);
		} else if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
			// estados = new String[6];
			estados = new String[5];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ABIERTO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[3] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			// estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;
			// estados[5] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;
			estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			prestamos = this.getCountPrestamosXUsuarioGestor(getServiceClient()
					.getId(), estados, idsArchivo);
		} else if (getServiceClient().hasPermission(
				AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			estados = new String[3];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[2] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;

			prestamos = this.getCountPrestamosXEstados(estados, idsArchivo);
		} else if (getServiceClient().hasPermission(
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS)
				|| getServiceClient().hasPermission(
						AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)) {
			// estados = new String[6];
			estados = new String[5];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ABIERTO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[3] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;
			// estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;
			// estados[5] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			prestamos = this.getCountPrestamosXUsuarioSolicitanteBuscar(
					getServiceClient().getId(), estados, idsArchivo);
		}

		return prestamos;
	}

	/**
	 * Obtiene el listado de prestamos a gestionar para el usuario del servicio
	 * en funci�n de los permisos del mismo
	 * 
	 * @return Listado de prestamos gestionables por el usuario
	 */
	public List obtenerListadoGestionar(String[] idsArchivo) {
		List prestamos = null;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| getServiceClient().hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			estados = new String[3];
			estados[0] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			try {
				prestamos = this
						.getPrestamosXEstados(estados, null, idsArchivo);
			} catch (TooManyResultsException e) {
			}
		} else if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
			// estados = new String[6];
			estados = new String[5];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ABIERTO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[3] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			// estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;
			// estados[5] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;
			estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			try {
				BusquedaVO busqueda = new BusquedaVO();
				busqueda.setEstados(estados);
				prestamos = this.getPrestamosXUsuarioGestor(getServiceClient()
						.getId(), busqueda);
			} catch (TooManyResultsException e) {
			}
		} else if (getServiceClient().hasPermission(
				AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			estados = new String[3];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[2] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;

			try {
				prestamos = this
						.getPrestamosXEstados(estados, null, idsArchivo);
			} catch (TooManyResultsException e) {
			}
		} else if (getServiceClient().hasPermission(
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS)
				|| getServiceClient().hasPermission(
						AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)) {
			// estados = new String[6];
			estados = new String[5];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_ABIERTO;
			estados[1] = "" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
			estados[2] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
			estados[3] = ""
					+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
			estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;
			// estados[4] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;
			// estados[5] = "" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;

			try {
				BusquedaVO busqueda = new BusquedaVO();
				busqueda.setEstados(estados);
				prestamos = this.getPrestamosXUsuarioSolicitanteBuscar(
						getServiceClient().getId(), busqueda);
			} catch (TooManyResultsException e) {
			}
		}

		return prestamos;
	}

	public int getCountListadoGestionarReserva() {
		int prestamos = 0;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
			estados = new String[1];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;

			prestamos = this.getCountPrestamosXUsuarioGestor(getServiceClient()
					.getId(), estados);
		} else if (getServiceClient().hasPermission(
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS)
				|| getServiceClient().hasPermission(
						AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)) {
			// estados = new String[6];
			estados = new String[1];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;

			prestamos = this.getCountPrestamosXUsuarioSolicitanteBuscar(
					getServiceClient().getId(), estados);
		}

		return prestamos;
	}

	/**
	 * Obtiene el listado de prestamos a gestionar reserva para el usuario del
	 * servicio en funci�n de los permisos del mismo
	 * 
	 * @return Listado de prestamos gestionables por el usuario
	 */
	public List obtenerListadoGestionarReserva(String[] idsArchivo) {
		List prestamos = null;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
			estados = new String[1];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;

			try {
				BusquedaVO busqueda = new BusquedaVO();
				busqueda.setEstados(estados);
				prestamos = this.getPrestamosXUsuarioGestor(getServiceClient()
						.getId(), busqueda);
			} catch (TooManyResultsException e) {
			}

		} else if (getServiceClient().hasPermission(
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS)
				|| getServiceClient().hasPermission(
						AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)) {
			estados = new String[1];
			estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;

			try {
				BusquedaVO busqueda = new BusquedaVO();
				busqueda.setEstados(estados);
				prestamos = this.getPrestamosXUsuarioSolicitanteBuscar(
						getServiceClient().getId(), busqueda);
			} catch (TooManyResultsException e) {
			}
		}

		return prestamos;
	}

	public int getCountListadoEntregar(String[] idsArchivo) {
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ENTREGAR_PRESTAMO_ACTION);

		estados = new String[1];
		estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;

		int prestamos = 0;
		prestamos = this.getCountPrestamosXEstados(estados, idsArchivo);

		return prestamos;
	}

	public List obtenerListadoEntregar(String[] idsArchivo) {
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ENTREGAR_PRESTAMO_ACTION);

		estados = new String[1];
		estados[0] = "" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;

		List prestamos = null;
		try {

			prestamos = this.getPrestamosXEstados(estados, null, idsArchivo);
		} catch (TooManyResultsException e) {
		}

		return prestamos;
	}

	/**
	 * Obtiene un listado de los organos "visibles" en funci�n de los permisos
	 * del usuario para filtrar en las busqeuda por usuario
	 * 
	 * @return Listado de los organos.
	 */
	public Collection getOrganosBusqueda() {
		Collection organos = null;

		this.check(ServiciosSecurityManager.USUARIOS_PRESTAMOS_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ARCHIVO))
			organos = getGestionControlUsusarios().getCAOrgProductoresVOList(
					null);
		else if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE))
			organos = (List) getServiceClient().getProperties().get(
					PrestamosConstants.PROPERTY_DEPENDENTORGANIZATIONLIST);

		return organos;
	}

	/**
	 * Obtiene un listado de usuarios "visibles" en funcion de los permisos del
	 * usuario para filtrar en las busquedas por usuario
	 * 
	 * @return Listado de usuario
	 */
	public Collection getUsuariosBusqueda() {
		ServiceRepository sr = ServiceRepository
				.getInstance(getServiceSession());
		GestionControlUsuariosBI gcu = sr.lookupGestionControlUsuariosBI();
		Collection usuarios = new ArrayList();
		Collection prestamos = null;
		UsuarioVO usuario = null;

		this.check(ServiciosSecurityManager.USUARIOS_PRESTAMOS_ACTION);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| getServiceClient().hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			prestamos = prestamoDBEntity.getUsuariosBusquedaPrestamos(null);
		} else if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
			prestamos = prestamoDBEntity
					.getUsuariosBusquedaPrestamos((List) getServiceClient()
							.getProperties()
							.get(PrestamosConstants.PROPERTY_DEPENDENTORGANIZATIONLIST));
		}

		Iterator it = prestamos.iterator();
		while (it.hasNext()) {
			usuario = gcu.getUsuario(((PrestamoVO) it.next())
					.getIdusrsolicitante());

			if (usuario != null)
				usuarios.add(usuario);
		}

		return usuarios;
	}

	/**
	 * Obtiene el numero de detalles que est�n asociados a un determinado
	 * prestamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos conocer su numero
	 *            de detalles asociados.
	 * @return Numero de detaless de prestamo asociados al prestamo.
	 */
	public int numeroDetallesPrestamo(String idPrestamo) {
		return prestamoDBEntity.numeroDetallesPrestamo(idPrestamo);
	}

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determinado prestamo, estableciendo su estado de disponiblidad.
	 * 
	 * @param prestamo
	 *            al que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public boolean comprobarDisponibilidadDetallesPrestamo(PrestamoVO prestamo,
			Collection detalles) {
		boolean todosDisponibles = true;

		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) it.next();

			// Si el prestamo tiene reserva, se comprueban en esas fechas.
			// Si no tiene reserva se comprueban para la fecha actual + los
			// dias del properties configurados.
			if (prestamo.tieneReserva()) {
				detalle.setEstadoDisponibilidad(this
						.isDetalleDisponibleAllConditions(detalle,
								prestamo.getFinicialreserva(),
								prestamo.getFfinalreserva(),
								prestamo.tieneReserva()));
			} else {
				final String dias_reserva = PropertyHelper
						.getProperty(PropertyHelper.PLAZO_PRESTAMO);

				Calendar fechafinal = new GregorianCalendar();
				fechafinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
						* PrestamosConstants.HORAS_DIA);

				detalle.setEstadoDisponibilidad(this
						.isDetalleDisponibleAllConditions(detalle,
								DBUtils.getFechaActual(), fechafinal.getTime(),
								prestamo.tieneReserva()));
			}

			detalle.setDisponibilidad(detalle.getEstadoDisponibilidad() == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE
					|| detalle.getEstadoDisponibilidad() == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL);

			if (todosDisponibles && !detalle.isDisponibilidad())
				todosDisponibles = false;
		}

		return todosDisponibles;
	}

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determinado prestamo si se intenta establecer como fecha de devolucion
	 * la indicada por par�metro
	 * 
	 * @param prestamo
	 *            al que pertenecen las unidades
	 * @param fechaInicio
	 *            Fecha de inicio del prestamo
	 * @param fechaDevolucion
	 *            Fecha que se pretende establecer como de devolucion del
	 *            prestamo
	 */
	public boolean comprobarDisponibilidadDetallesPrestamoXFecha(
			Collection detalles, Date fechaInicio, Date fechaDevolucion) {
		boolean disponibles = true;
		Iterator it = detalles.iterator();
		while (it.hasNext()) {

			DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) it.next();

			String[] estados = new String[] {
					new Integer(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA)
							.toString(),
					new Integer(PrestamosConstants.ESTADO_DETALLE_RESERVADA)
							.toString() };

			// Obtenemos los posibles detalles en los que puede existir ya la
			// misma unidad documental y que sean relevantes para las
			// comprobaciones
			Collection detallesRelevantes = detallePrestamoDBEntity
					.getDetalles(detallePrestamo.getIdudoc(),
							detallePrestamo.getSignaturaudoc(),
							DetalleDBEntity.TIPO_DETALLE_PRESTAMO, estados);

			if (!CollectionUtils.isEmpty(detallesRelevantes)) {

				Iterator itRel = detallesRelevantes.iterator();
				while (itRel.hasNext()) {
					DetallePrestamoVO detalleEnOtroPrestamo = (DetallePrestamoVO) itRel
							.next();

					if (!detalleEnOtroPrestamo.getIdsolicitud().equals(
							detallePrestamo.getIdsolicitud())) {
						// Las fechas solo son relevantes para la disponibilidad
						// siempre y cuando el detalle no sea de tipo caja o
						// bien,
						// a�n siendo de tipo caja tiene relleno el campo de
						// numero de expediente y este coincide con alguno de
						// los de los detalles relevantes
						if (!detalleEnOtroPrestamo.isSubtipoCaja()
								|| (detalleEnOtroPrestamo.isSubtipoCaja()
										&& StringUtils
												.isNotEmpty(detalleEnOtroPrestamo
														.getExpedienteudoc())
										&& StringUtils
												.isNotEmpty(detallePrestamo
														.getExpedienteudoc()) && detalleEnOtroPrestamo
										.getExpedienteudoc().equalsIgnoreCase(
												detallePrestamo
														.getExpedienteudoc()))) {

							// Comprobar fechas de inicio y fin
							if ((detalleEnOtroPrestamo.getFinicialuso() != null)
									&& (detalleEnOtroPrestamo.getFfinaluso() != null)) {
								if ((DateUtils
										.getFechaSinHora(
												detalleEnOtroPrestamo
														.getFinicialuso())
										.compareTo(
												DateUtils
														.getFechaSinHora(fechaDevolucion)) <= 0 && DateUtils
										.getFechaSinHora(
												detalleEnOtroPrestamo
														.getFinicialuso())
										.compareTo(
												DateUtils
														.getFechaSinHora(fechaInicio)) >= 0)
										|| (DateUtils
												.getFechaSinHora(
														detalleEnOtroPrestamo
																.getFinicialuso())
												.compareTo(
														DateUtils
																.getFechaSinHora(fechaInicio)) >= 0 && DateUtils
												.getFechaSinHora(
														detalleEnOtroPrestamo
																.getFfinaluso())
												.compareTo(
														DateUtils
																.getFechaSinHora(fechaDevolucion)) <= 0)
										|| (DateUtils
												.getFechaSinHora(
														detalleEnOtroPrestamo
																.getFinicialuso())
												.compareTo(
														DateUtils
																.getFechaSinHora(fechaInicio)) <= 0 && DateUtils
												.getFechaSinHora(
														detalleEnOtroPrestamo
																.getFfinaluso())
												.compareTo(
														DateUtils
																.getFechaSinHora(fechaInicio)) >= 0)) {
									disponibles = false;
									break;
								}
							}
						}
					}
				}
			}
		}

		return disponibles;
	}

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * un determinado prestamo que se va a prorrogar, estableciendo su estado de
	 * disponiblidad.
	 * 
	 * @param prestamo
	 *            al que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public void comprobarDisponibilidadDetallesPrestamoProrroga(
			PrestamoVO prestamo, Collection detalles) {
		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) it.next();

			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_PRORROGA);

			Calendar fechaFinal = new GregorianCalendar();

			// Si la fecha maxima de devolucion es posterior al dia de hoy,
			// los dias de prorroga se suman a la fecha maxima de devolucion
			if ((prestamo.getFmaxfinprestamo() != null)
					&& (prestamo.getFmaxfinprestamo().after(DBUtils
							.getFechaActual())))
				fechaFinal.setTime(prestamo.getFmaxfinprestamo());

			fechaFinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
					* PrestamosConstants.HORAS_DIA);

			detalle.setDisponibilidad(this.isDetalleDisponible(detalle,
					DBUtils.getFechaActual(), fechaFinal.getTime(),
					prestamo.tieneReserva()));
		}
	}

	/**
	 * Encapsula la logica de finalizacion del proceso de autorizacion de un
	 * prestamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo que deseamos finalizar.
	 */
	public Collection autorizardenegarPrestamo(String idPrestamo,
			String fentrega) {
		boolean autorizadas = false;
		boolean reservadas = false;
		int denegadas = 0;
		ArrayList udocsNoDisponibles = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		// Realizar comprobaciones de seguridad
		this.check(ServiciosSecurityManager.FINALIZAR_AUTORIZACION_PRESTAMO_ACTION);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO);

		// Abrir transaction
		iniciarTransaccion();
		// Obtenemos el prestamo
		PrestamoVO prestamo = getPrestamo(idPrestamo);
		// Asignamos al prestamo el periodo de caducidad
		if (!prestamo.tieneReserva()) {
			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_PRESTAMO);

			Date hoy = Calendar.getInstance().getTime();
			Calendar fechafinal = new GregorianCalendar();
			fechafinal.setTime(hoy);
			fechafinal.add(Calendar.HOUR, (Integer.parseInt(dias_reserva))
					* PrestamosConstants.HORAS_DIA);

			prestamo.setFInicialPrestamo(DBUtils.getFechaActual());
			prestamo.setFFinalPrestamo(fechafinal.getTime());
			prestamo.setFmaxfinprestamo(fechafinal.getTime());
		} else {
			prestamo.setFInicialPrestamo(prestamo.getFinicialreserva());
			prestamo.setFFinalPrestamo(prestamo.getFfinalreserva());
			prestamo.setFmaxfinprestamo(prestamo.getFfinalreserva());
		}

		// Asignamos al prestamo la fecha de entrega si se ha decidido
		// rellenarla a la hora de autorizar
		if (!StringUtils.isEmpty(fentrega))
			prestamo.setFentrega(DateUtils.getDate(fentrega));

		// Obtener detalles prestamo
		Collection detalles = this.getDetallesPrestamo(idPrestamo);
		Iterator detallesIterator = detalles.iterator();
		while (detallesIterator.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) detallesIterator
					.next();

			// Bloquear detalle
			uDocEnUiDepositoDbEntity.bloquearUDoc(detalle.getIdudoc(),
					detalle.getSignaturaudoc());

			// Verificar disponibilidad del detalle
			int disponibilidad = isDetalleDisponibleAllConditions(detalle,
					prestamo.getFInicialPrestamo(),
					prestamo.getFFinalPrestamo(), prestamo.tieneReserva());

			// ahora se audita siempre
			// originalmente si la disponibilidad era igual a 0, solo se
			// auditaba en los estados autorizada, reservada, denegada
			DataLoggingEvent data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			// data.addDetalle(ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
			// CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
			// getServiceSession()) );
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());

			if (disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE
					|| disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL) {
				detalle.setFinicialuso(prestamo.getFInicialPrestamo());
				detalle.setFfinaluso(prestamo.getFFinalPrestamo());
				detalle.setFestado(DateUtils.getFechaActual());

				switch (detalle.getEstado()) {
				case PrestamosConstants.ESTADO_DETALLE_AUTORIZADA:
					autorizadas = true;
					detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA);
					data.addDetalle(
							locale,
							ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO,
							Messages.getString(
									"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
									locale)
									+ Messages
											.getString(
													"archigest.archivo.solicitudes.detalle.estado.3",
													locale));
					break;
				case PrestamosConstants.ESTADO_DETALLE_RESERVADA:
					reservadas = true;
					detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_RESERVADA);
					data.addDetalle(
							locale,
							ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_RESERVADO,
							Messages.getString(
									"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
									locale)
									+ Messages
											.getString(
													"archigest.archivo.solicitudes.detalle.estado.2",
													locale));
					break;
				case PrestamosConstants.ESTADO_DETALLE_DENEGADA:
					denegadas++;
					data.addDetalle(
							locale,
							ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO,
							Messages.getString(
									"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
									locale)
									+ Messages
											.getString(
													"archigest.archivo.solicitudes.detalle.estado.4",
													locale));
					break;
				/*
				 * case PrestamosConstants.ESTADO_DETALLE_AUTORIZADA_PARCIAL:
				 * autorizadas = true; detalle.setEstado(PrestamosConstants.
				 * ESTADO_DETALLE_AUTORIZADA_PARCIAL);
				 * data.addDetalle(ArchivoDetails
				 * .SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO , Messages.getString(
				 * "archigest.archivo.solicitudes.detalle.estado.infoUdoc") +
				 * Messages.getString(
				 * "archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
				 * )); break;
				 */
				}
			} else {
				detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_DENEGADA);
				detalle.setFestado(DateUtils.getFechaActual());

				switch (disponibilidad) {
				case SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA:
					detalle.setMotivorechazo(Messages
							.getString(
									"archigest.archivo.solicitudes.detalle.estado.nodisponible",
									locale));
					break;
				case SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_RESERVADA:
					detalle.setMotivorechazo(Messages.getString(
							"archigest.archivo.solicitudes.detalle.estado.2",
							locale));
					break;
				case SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_BLOQUEADA:
					detalle.setMotivorechazo(Messages
							.getString(
									"archigest.archivo.solicitudes.detalle.estado.bloqueada",
									locale));
					break;
				}

				/*
				 * if (disponibilidad==1)
				 * detalle.setMotivorechazo("No disponible"); if
				 * (disponibilidad==2) detalle.setMotivorechazo("Reservada"); if
				 * (disponibilidad==3) detalle.setMotivorechazo("Bloqueada");
				 */

				udocsNoDisponibles.add(detalle);
				denegadas++;

				data.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO,
						Messages.getString(
								"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
								locale)
								+ detalle.getMotivorechazo());
				// data.addDetalle(ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO,
				// "Udoc " + detalle.getMotivorechazo() );
			}

			// Actualizadmos los detalles
			detallePrestamoDBEntity.actualizarDetalle(detalle);
		}

		DataLoggingEvent dle = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, prestamo.getId());
		dle.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));
		if (denegadas == detalles.size()) {
			prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DENEGADO);

			// dle.addDetalle(ArchivoDetails.SOLICITUDES_PRESTAMO_DENEGADO,"prestamo Denegado");
			dle.addDetalle(
					locale,
					ArchivoDetails.SOLICITUDES_PRESTAMO_DENEGADO,
					Messages.getString("archigest.archivo.prestamos.prestamo",
							locale)
							+ Constants.STRING_SPACE
							+ Messages.getString(
									"archigest.archivo.solicitudes.estado.5",
									locale));
		} else {
			if (autorizadas) {
				prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO);

				dle.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_ACEPTADO,
						Messages.getString(
								"archigest.archivo.prestamos.prestamo", locale)
								+ Constants.STRING_SPACE
								+ Messages
										.getString(
												"archigest.archivo.solicitudes.estado.4",
												locale));
			}
			if (reservadas) {
				prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_RESERVADO);

				dle.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_RESERVADO,
						Messages.getString(
								"archigest.archivo.prestamos.prestamo", locale)
								+ Constants.STRING_SPACE
								+ Messages
										.getString(
												"archigest.archivo.solicitudes.estado.3",
												locale));
			}
		}
		prestamo.setFestado(DateUtils.getFechaActual());

		// Actualizamos el prestamo
		prestamoDBEntity.updatePrestamo(prestamo);

		// Terminamos la transaccion
		commit();

		return udocsNoDisponibles;
	}

	/**
	 * Comprueba la disponibilidad de una unidad documental(incluyendo las
	 * reservas).
	 * 
	 * @param detallePrestamo
	 *            Udoc que deseamos comprobar
	 * @param fechaInicial
	 *            fecha de inicio de uso de la udoc
	 * @param fechaFinal
	 *            fecha de fin de uso de la udoc
	 * @param isReserva
	 *            indica si el detalle es para una reserva
	 * @return Verdadero Si esta disponible o False en caso contrario
	 */

	public boolean isDetalleDisponible(DetallePrestamoVO detallePrestamo,
			Date fechaInicial, Date fechaFinal, boolean isReserva) {
		int disponible = this.isDetalleDisponibleAllConditions(detallePrestamo,
				fechaInicial, fechaFinal, isReserva);

		return (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE || disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL);
	}

	private int calcularDisponibilidad(
			DetallePrestamoVO detallePrestamoAComprobar,
			DetallePrestamoVO detalleEnOtroPrestamo, Date fechaInicioPrestamo,
			Date fechaFinalPrestamo, boolean isReserva) {

		int disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;

		boolean disponibleXFechas = true; // Variable local a cada detalle que
											// indica si cumple las condiciones
											// de disponibilidad por fechas
		boolean disponibleXReserva = true; // Variable local a cada detalle que
											// indica si cumple las condiciones
											// de disponibilidad por tener una
											// reserva

		// Explicaci�n de condiciones de fechas
		// El detalle est� disponible en fecha de nuestro prestamo siempre y
		// cuando se cumpla que:
		/*
		 * 1. La fecha de inicio de uso de la unidad no est� entre las fechas
		 * de inicio y fin de nuestro prestamo 2. Las fechas de nuestro prestamo
		 * no contengan por ambos lados a las fechas de uso de la unidad 3. Las
		 * fechas de uso de la unidad no contengan por ambos lados a las fechas
		 * de nuestro prestamo
		 */
		if ((detalleEnOtroPrestamo.getFinicialuso() != null)
				&& (detalleEnOtroPrestamo.getFfinaluso() != null)) {
			if ((DateUtils.getFechaSinHora(
					detalleEnOtroPrestamo.getFinicialuso()).compareTo(
					DateUtils.getFechaSinHora(fechaFinalPrestamo)) <= 0 && DateUtils
					.getFechaSinHora(detalleEnOtroPrestamo.getFinicialuso())
					.compareTo(DateUtils.getFechaSinHora(fechaInicioPrestamo)) >= 0)
					|| (DateUtils.getFechaSinHora(
							detalleEnOtroPrestamo.getFinicialuso()).compareTo(
							DateUtils.getFechaSinHora(fechaInicioPrestamo)) >= 0 && DateUtils
							.getFechaSinHora(
									detalleEnOtroPrestamo.getFfinaluso())
							.compareTo(
									DateUtils
											.getFechaSinHora(fechaFinalPrestamo)) <= 0)
					|| (DateUtils.getFechaSinHora(
							detalleEnOtroPrestamo.getFinicialuso()).compareTo(
							DateUtils.getFechaSinHora(fechaInicioPrestamo)) <= 0 && DateUtils
							.getFechaSinHora(
									detalleEnOtroPrestamo.getFfinaluso())
							.compareTo(
									DateUtils
											.getFechaSinHora(fechaInicioPrestamo)) >= 0)) {
				disponibleXFechas = false;
			} else {
				// Si a�n estando en los rangos de fechas esperados, resulta
				// que la unidad documental sigue entregada, no est�
				// disponible
				// Este caso no se contemplaba, que es el que alguien no
				// devuelve la unidad cuando deber�a haberlo hecho o bien, si
				// bien, hay
				// que tener en cuenta que esto solo se aplica si coincide que
				// la fecha de inicio del prestamo sea anterior o igual a hoy
				// (sino es que estamos en un prestamo con reserva que para la
				// fecha en que se vaya a entregar puede estar disponible ya
				if (detalleEnOtroPrestamo.getEstado() == PrestamosConstants.ESTADO_DETALLE_ENTREGADA
						&& DateUtils.getFechaActualSinHora().compareTo(
								DateUtils.getFechaSinHora(fechaInicioPrestamo)) >= 0)
					disponibleXFechas = false;
			}

			// Si el prestamo que queremos crear no es con reserva y los
			// detalles que referencian a la misma unidad
			// est�n autorizados o entregados, la unidad no esta disponible
			if (!isReserva) {
				if ((detalleEnOtroPrestamo.getEstado() == PrestamosConstants.ESTADO_DETALLE_AUTORIZADA || detalleEnOtroPrestamo
						.getEstado() == PrestamosConstants.ESTADO_DETALLE_ENTREGADA)) {
					disponibleXReserva = false;
				}
			}
		}

		if (detallePrestamoAComprobar.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			if (StringUtils.isNotEmpty(detallePrestamoAComprobar
					.getExpedienteudoc())) {
				// Si tiene relleno el numero de expediente concreto a prestar y
				// coincide con el de alg�n otro detalle
				// de su misma fracci�n de serie, estar� disponible siempre
				// y cuando lo esta por fechas o por tener reserva
				if (detallePrestamoAComprobar.getExpedienteudoc().equals(
						detalleEnOtroPrestamo.getExpedienteudoc())) {
					if (!disponibleXReserva || !disponibleXFechas) {
						disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA;
					}
				}
			} else {
				// Si no tiene relleno el numero de expediente concreto, estara
				// parcialmente disponible, puesto que se sabe
				// que existe alg�n otro detalle en el que aparece pero no si
				// el expediente concreto a prestar esta disponible o no
				if (!disponibleXFechas) {
					disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL;
				}
			}
		} else // Si es una unidad documental que no es de subtipo caja
				// (fraccion de serie)
		{
			// Si alguno de los detalles que existen ya para la unidad que
			// queremos tener en nuestro prestamo esta en alguno de los estados
			// siguientes y no se ha cumplido la condici�n anterior (prestamo
			// sin reserva autorizado o entregado), se supone que estamos
			// creando
			// un prestamo con reserva o bien que el detalle que manejamos puede
			// tener una reserva, por lo que hay que comprobar si por razones de
			// fechas
			// tenemos la unidad disponible a fecha de uso realmente o no
			if (!disponibleXReserva || !disponibleXFechas) {
				disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA;
			}
		}

		return disponible;
	}

	/**
	 * Comprueba la disponibilidad de una unidad documental
	 * 
	 * @param detallePrestamo
	 *            Udoc que deseamos comprobar
	 * @param fechaInicioPrestamo
	 *            Fecha de inicio del prestamo
	 * @param fechaFinalPrestamo
	 *            Fecha de fin del prestamo
	 * @param isReserva
	 *            Indica si el detalle es para una reserva
	 * @return 0 Si esta disponible 1. Si no esta disponible porque esta
	 *         autorizada o entregada 2. Si no esta disponible porque esta
	 *         reservada 3. Si esta bloqueada 4. Si esta disponible parcialmente
	 *         porque alg�n expediente esta prestado
	 */
	private int isDetalleDisponibleAllConditions(
			DetallePrestamoVO detallePrestamo, Date fechaInicioPrestamo,
			Date fechaFinalPrestamo, boolean isReserva) {
		int disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;

		// Comprobar que no esta bloqueada por transferencia entre archivos
		List udocs = uDocEnUiDepositoDbEntity
				.getUDocsVOXId(new String[] { detallePrestamo.getIdudoc() });
		if (!ListUtils.isEmpty(udocs)) {
			for (int i = 0; i < udocs.size(); i++) {
				UDocEnUiDepositoVO udocEnUiDepositoVO = (UDocEnUiDepositoVO) udocs
						.get(i);
				UInsDepositoVO uinsDepositoVO = uInstalacionDepositoDBEntity
						.getUInstDepositoVOXIdEnDeposito(udocEnUiDepositoVO
								.getIduinstalacion());
				if (uinsDepositoVO.getMarcasBloqueo() > 0) {
					disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_BLOQUEADA;
					return disponible;
				}
			}
		}

		String[] estados = new String[] {
				new Integer(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA)
						.toString(),
				new Integer(PrestamosConstants.ESTADO_DETALLE_ENTREGADA)
						.toString(),
				new Integer(PrestamosConstants.ESTADO_DETALLE_RESERVADA)
						.toString() };

		// Obtenemos los posibles detalles en los que puede existir ya la misma
		// unidad documental y que sean relevantes para las comprobaciones
		Collection detallesRelevantes = detallePrestamoDBEntity.getDetalles(
				detallePrestamo.getIdudoc(),
				detallePrestamo.getSignaturaudoc(),
				DetalleDBEntity.TIPO_DETALLE_PRESTAMO, estados);

		Iterator it = detallesRelevantes.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO d = (DetallePrestamoVO) it.next();

			if (!detallePrestamo.getIdsolicitud().equals(d.getIdsolicitud())) {
				disponible = calcularDisponibilidad(detallePrestamo, d,
						fechaInicioPrestamo, fechaFinalPrestamo, isReserva);

				if (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA
						|| (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL && StringUtils
								.isEmpty(detallePrestamo.getExpedienteudoc())))
					break; // Rompemos el bucle porque hemos detectado que no
							// esta disponible
			}
		}// while

		return disponible;
	}

	/**
	 * Obtiene la lista de detalles de prestamos que disponen de una unidad
	 * documental.
	 * 
	 * @param prestamo
	 *            Informaci�n del prestamo.
	 * @param detallePrestamo
	 *            Udoc que deseamos comprobar
	 * @return Lista de detalles de prestamos.
	 */
	public List getDetallesPrestamosNoDisponibles(PrestamoVO prestamo,
			DetallePrestamoVO detallePrestamo) {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSolicitudesBI solicitudesBI = services
				.lookupGestionSolicitudesBI();

		List detalles = new ArrayList();

		// Obtener las fechas inicial y final del per�odo del prestamo
		Date fechaInicial = null, fechaFinal = null;
		if (prestamo.tieneReserva()) {
			fechaInicial = prestamo.getFinicialreserva();
			fechaFinal = prestamo.getFfinalreserva();
		} else {
			Calendar fechafinal = new GregorianCalendar();
			fechafinal.add(
					Calendar.HOUR,
					Integer.parseInt(PropertyHelper
							.getProperty(PropertyHelper.PLAZO_PRESTAMO))
							* PrestamosConstants.HORAS_DIA);

			fechaInicial = DBUtils.getFechaActual();
			fechaFinal = fechafinal.getTime();
		}

		String[] estados = new String[] {
				new Integer(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA)
						.toString(),
				new Integer(PrestamosConstants.ESTADO_DETALLE_ENTREGADA)
						.toString(),
				new Integer(PrestamosConstants.ESTADO_DETALLE_RESERVADA)
						.toString() };

		Collection detallesRelevantes = detallePrestamoDBEntity.getDetalles(
				detallePrestamo.getIdudoc(),
				detallePrestamo.getSignaturaudoc(),
				DetalleDBEntity.TIPO_DETALLE_PRESTAMO, estados);

		solicitudes.vos.DetalleUdocNoDisponibleVO detalle;
		DetallePrestamoVO d;

		Iterator it = detallesRelevantes.iterator();
		while (it.hasNext()) {
			d = (DetallePrestamoVO) it.next();

			if (!detallePrestamo.getIdsolicitud().equals(d.getIdsolicitud())) {

				int disponibilidad = calcularDisponibilidad(detallePrestamo, d,
						fechaInicial, fechaFinal, prestamo.tieneReserva());

				boolean esDetalleNoDisponible = disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE ? false
						: true;

				if (esDetalleNoDisponible) {

					detalle = new DetalleUdocNoDisponibleVO();

					// Informacion de la unidad documental en el prestamo
					detalle.setIdUdoc(d.getIdudoc());
					detalle.setEstadoUdoc(d.getEstado());
					detalle.setFechaInicialUsoUdoc(d.getFinicialuso());
					detalle.setFechaFinalUsoUdoc(d.getFfinaluso());

					switch (d.getTiposolicitud()) {
					case ConsultasConstants.TIPO_SOLICITUD_CONSULTA:
						ConsultaVO c = getGestionConsultasBI().getConsulta(
								d.getIdsolicitud());
						if (c != null) {
							// Obtener info extra de la consulta
							c = (ConsultaVO) solicitudesBI
									.getAditionalSolicitudInformation(c);
							detalle.setSolicitud(c);
							detalle.setSolicitanteSolicitud(c
									.getNusrconsultor());
							detalle.setObservaciones(d.getObservaciones());
						}
						break;
					case PrestamosConstants.TIPO_SOLICITUD_PRESTAMO:
						PrestamoVO p = getPrestamo(d.getIdsolicitud());
						if (p != null) {
							// Necesitamos Informacion del archivo y usuario
							p = (PrestamoVO) solicitudesBI
									.getAditionalSolicitudInformation(p);
							detalle.setSolicitud(p);
							detalle.setSolicitanteSolicitud(p
									.getNusrsolicitante());
							detalle.setObservaciones(d.getObservaciones());

						}
						break;
					}

					detalles.add(detalle);
				} // if (esDetalleNoDisponible)
			} // While
		}

		return detalles;
	}

	/**
	 * Metodo para la creacion de un evento espec�fico para el modulo de
	 * prestamos
	 */
	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.SERVICIOS_MODULE,
				action, getServiceClient(), false);

		// Loaniadimos a la pila
		getLogger().add(le);

		return le;
	}

	/**
	 * Realiza la comprobacion de seguridad para la accion indicada de este
	 * modulo
	 * 
	 * @param action
	 *            Action que se desea comprobar
	 */
	protected void check(ActionObject action) {
		getSecurityManager().check(action, getServiceClient());
	}

	/**
	 * Realiza la entrega de una reserva de un prestamo.
	 * 
	 * @param idPrestamo
	 *            Identificador de la reserva de prestamo
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la accion por parte del usuario
	 */
	public Collection solicitarEntregaReserva(String idPrestamo)
			throws PrestamoActionNotAllowedException {
		int denegadas = 0;
		ArrayList udocsNoDisponibles = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		ConditionChecker.checkOnEntregaReserva(idPrestamo, this,
				getServiceClient());
		// Realizar comprobaciones de seguridad
		this.check(ServiciosSecurityManager.SOLICITAR_ENTREGA_RESERVA_PRESTAMO_ACTION);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO);

		// Abrir transaction
		iniciarTransaccion();
		// Obtenemos el prestamo
		PrestamoVO prestamo = getPrestamo(idPrestamo);

		// Obtener detalles prestamo
		Collection detalles = this.getDetallesPrestamo(idPrestamo);
		Iterator detallesIterator = detalles.iterator();
		while (detallesIterator.hasNext()) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) detallesIterator
					.next();

			// Bloquear detalle
			uDocEnUiDepositoDbEntity.bloquearUDoc(detalle.getIdudoc(),
					detalle.getSignaturaudoc());

			// Verificar disponibilidad del detalle
			boolean disponibilidad = isDetalleDisponible(detalle,
					DateUtils.getFechaActualSinHora(),
					prestamo.getFmaxfinprestamo(), prestamo.tieneReserva());

			DataLoggingEvent data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_PRESTAMO,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
					CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
							prestamo, getServiceSession()));
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());

			if (disponibilidad) {
				detalle.setFestado(DateUtils.getFechaActual());
				detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA);
				data.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO,
						"Udoc autorizada");
			} else {
				detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_DENEGADA);
				detalle.setFestado(DateUtils.getFechaActual());
				detalle.setMotivorechazo("No disponible");

				udocsNoDisponibles.add(detalle);
				denegadas++;
				data.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO,
						"Udoc no disponible");
			}

			// Actualizamos los detalles
			detallePrestamoDBEntity.actualizarDetalle(detalle);
		}

		DataLoggingEvent dle = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, prestamo.getId());
		if (denegadas == detalles.size()) {
			prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DENEGADO);

			dle.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_PRESTAMO_DENEGADO,
					"prestamo Denegado");
		} else {
			prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO);

			dle.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_PRESTAMO_ACEPTADO,
					"prestamo Aceptado");
		}

		prestamo.setFestado(DateUtils.getFechaActual());
		// Actualizamos el prestamo
		prestamoDBEntity.updatePrestamo(prestamo);

		// Terminamos la transaccion
		commit();

		return udocsNoDisponibles;
	}

	protected void devolverDetallesPrestamo(List identificadores) {
		PrestamoVO prestamo = null;

		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.DEVOLVER_UDOCS_PRESTAMO_ACTION);

		Iterator it = identificadores.iterator();

		// Abrimos la transaccion
		iniciarTransaccion();

		while (it.hasNext()) {
			String identificacion = (String) it.next();

			// Obtenemos el detalle del prestamo
			DetallePrestamoVO detalle = this
					.getDetallePrestamoEntregado(identificacion);

			// Obtenemos el prestamo al que va asociado el detalle
			prestamo = this.getPrestamo(detalle.getIdsolicitud());

			// Devolvemos el detalle y lo aniadimos a los que se han procesado
			// bien
			try {
				// this.devolverDetallePrestamo(prestamo, detalle.getIdudoc(),
				// detalle.getSignaturaudoc());
				this.devolverDetallePrestamo(detalle);

				// identificadores_exitosos.add(identificacion);
			} catch (PrestamoActionNotAllowedException panae) {
				// identificadores_fallidos.add(identificacion);
			}
		}// while

		int numDetalles = detallePrestamoDBEntity.getNumDetalles(prestamo
				.getId());
		int numDetallesDevueltos = detallePrestamoDBEntity
				.getNumDetallesDevueltos(prestamo.getId());
		boolean prestamoModificado = false;

		if (numDetalles == numDetallesDevueltos) {
			prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO);
			prestamo.setFestado(DateUtils.getFechaActual());
			prestamoModificado = true;
		} else if (numDetallesDevueltos > 0) {
			prestamo.setEstado(PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO);
			prestamo.setFestado(DateUtils.getFechaActual());
			prestamoModificado = true;
		}
		if (prestamoModificado)
			prestamoDBEntity.updatePrestamo(prestamo);
	}

	public Collection obtenerDetallesSalida(String idPrestamo) {
		Collection detallesPrestamos = null;

		this.check(ServiciosSecurityManager.DETALLES_IMPRIMIR_SALIDA_PRESTAMO);

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();

		// Si el usuario es de deposito solo vera las autorizadas, para
		// entregarlas.
		if (getServiceClient().hasPermission(
				AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
			detallesPrestamos = this
					.getDetallesPrestamoAutorizadasEntregadas(idPrestamo);
		else
			detallesPrestamos = this.getDetallesPrestamo(idPrestamo);

		// Para cada detalle , calculamos su ubicacion y rellenamos las fechas
		// extremas de sus unidades documentales
		Iterator it = detallesPrestamos.iterator();
		while (it.hasNext()) {
			DetallePrestamoVO d = (DetallePrestamoVO) it.next();

			// Rellenar ubicacion
			d.setUbicacion(this.getUbicacionDetallePrestamo(d.getIdudoc(),
					d.getSignaturaudoc()));

			// Rellenar la descripcion
			UDocEnUiDepositoVO udocEnDeposito = getDescripcionUdocDeposito(
					d.getIdudoc(), d.getSignaturaudoc());
			if (udocEnDeposito != null)
				d.setDescripcionUdocDeposito(udocEnDeposito.getDescripcion());

			// Rellenar fechas de inicio y fin
			List listaFIni = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaInicial());
			List listaFFin = managerDescripcion.getFechaElemento(d
					.getIdElementoCF(), csa.getConfiguracionDescripcion()
					.getFechaExtremaFinal());

			// Fecha de inicio
			if (listaFIni != null && listaFIni.size() > 0) {
				CampoFechaVO cfini = (CampoFechaVO) listaFIni.get(0);
				if (cfini != null)
					d.setFechaini(cfini.getValor());
			}

			// Fecha de fin
			if (listaFFin != null && listaFFin.size() > 0) {
				CampoFechaVO cffin = (CampoFechaVO) listaFFin.get(0);
				if (cffin != null)
					d.setFechafin(cffin.getValor());
			}
		}

		return detallesPrestamos;
	}

	public DetallePrestamoVO tratarDetallePrestamo(DetallePrestamoVO detalle) {
		String entidadProductora = "";
		String expediente = "";

		// Asingamos el nombre de fondo
		FondoVO fondo = getGestionFondosBI().getFondoXId(detalle.getIdFondo());

		if (fondo != null)
			detalle.setFondo(fondo.getCodReferenciaTitulo());

		// Asignamos el expediente y la entidad productor
		if (StringUtils.isNotEmpty(detalle.getExpedienteudoc())) {
			StringOwnTokenizer st = new StringOwnTokenizer(
					detalle.getExpedienteudoc(), "-");

			if (st != null) {
				if ((st.countTokens() > 1) && st.hasMoreTokens())
					entidadProductora = st.nextToken();
				if (st.hasMoreTokens())
					expediente = st.nextToken();
			}
		}

		detalle.setExpedienteudoc(expediente);
		detalle.setSistemaProductor(entidadProductora);

		return detalle;
	}

	public Collection getNuevosUsuariosGestoresXPermiso(String idArchivo) {
		List usuariosGestores = new ArrayList();
		String[] permisos = { AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS };

		// Obtener los usuarios que tienen el permiso generico 301
		Iterator users = getGestionControlUsusarios().getUsuariosConPermisos(
				permisos).iterator();
		// Buscar los grupos que tiene asociado el archivo al que se solicita el
		// prestamo
		List grupos = getGestionControlUsusarios().getGruposArchivo(idArchivo);

		// Comprobar que pertenece a algun grupo de los que tienen asociado el
		// archivo al que se solicita el prestamo y si pertenece formara parte
		// de la lista de posibles gestores
		while (users.hasNext()) {
			UsuarioExtendedVO usuario = (UsuarioExtendedVO) users.next();

			Iterator gruposUsuario = getGestionControlUsusarios()
					.getGruposUsuario(usuario.getId()).iterator();
			boolean encontrado = false;
			while (gruposUsuario.hasNext() && !encontrado) {
				GrupoVO grupo = (GrupoVO) gruposUsuario.next();

				if (grupos.contains(grupo)) {
					usuariosGestores.add(usuario);
					encontrado = true;
				}
			}
		}

		return usuariosGestores;
	}

	public Collection getNuevosUsuariosGestoresXOrgano(String organo) {
		ArrayList usuariosGestores = new ArrayList();
		ArrayList tipos = new ArrayList();
		// Obtener el sistema de organizacion del organo solicitante
		CAOrganoVO organoVO = getGestionControlUsusarios()
				.getCAOrgProductorVOXId(organo);

		// Buscar en el fichero de configuracion el tipo de usuario que tiene
		// como sistema de organizacion el mismo que el del organo solicitante o
		// el sistema interno.
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();

		Sistema sistemaInterno = csa.getSistemaInternoGestorOrganismos();

		Iterator usuarios = csa.getConfiguracionControlAcceso().getUsuarios()
				.iterator();
		while (usuarios.hasNext()) {
			Usuario usuario = (Usuario) usuarios.next();

			String organoSistGestor = usuario.getIdSistGestorOrg();
			if (organoSistGestor != null
					&& (organoSistGestor.equalsIgnoreCase(organoVO
							.getSistExtGestor()) || (sistemaInterno != null && organoSistGestor
							.equalsIgnoreCase(sistemaInterno.getId())))) {
				tipos.add(usuario.getTipo());
			}
		}

		// Obtener lista de usuarios que pueden solicitar un prestamo (ver punto
		// 1 del anexo) y que sean de alguno de los tipos obtenidos en el paso
		// anterior
		Iterator usuariosInternos = getGestionControlUsusarios()
				.getUsuariosExtRegistradosSolitantes().iterator();
		while (usuariosInternos.hasNext()) {
			UsuarioExtendedVO element = (UsuarioExtendedVO) usuariosInternos
					.next();

			if (tipos.contains(element.getTipo()))
				if (element.getIdOrgPertenece().equalsIgnoreCase(
						organoVO.getIdOrg()))
					usuariosGestores.add(element);
		}

		return usuariosGestores;
	}

	/**
	 * Obtiene los prestamos cedible por el usuario. Este metodo espera que en
	 * el cliente del servicio exista una propiedad denomindad
	 * PROPERTY_DEPENDENTORGANIZATIONLIST con la lista de organos dependientes.
	 * Codigo en el action: AppUser userVO = getAppUser(request); ServiceClient
	 * sc = ServiceClient.create(userVO); List orgList =
	 * userVO.getDependentOrganizationList(); orgList.add(
	 * userVO.getOrganization() ); sc.getProperties().put(
	 * ConsultasConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);
	 * 
	 * @return Listado de los prestamos que puede ceder el usuario.
	 */
	public Collection getPrestamosCedibles() {
		Collection prestamos = null;
		Collection prestamosEnEstado = new ArrayList();

		// //Realizamos las comprobaciones de seguridad
		// this.check(ServiciosSecurityManager.PRESTAMOS_X_USUARIOGESTOR_ACTION);

		try {
			// obtenemos prestamos q usuario de creacion es el mismo organo que
			// el usuario conectado, o de un organo dependiente
			prestamos = prestamoDBEntity.getPrestamosXUsuarioGestor(
					getServiceClient().getId(), getServiceClient()
							.getAllDependentOrganizationIds(), null);
		} catch (TooManyResultsException e) {
		}

		// Si el usuario tiene permiso de prestamo en archivo aniadimos los
		// prestamos que no tiene organo solicitante interno
		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| getServiceClient().hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			prestamos.addAll(prestamoDBEntity.getPrestamosExternos());
		}

		// Eliminamos los que estan denegados o devueltos
		for (Iterator it = prestamos.iterator(); it.hasNext();) {
			PrestamoVO prestamo = (PrestamoVO) it.next();

			if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_DENEGADO
					&& prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO)
				prestamosEnEstado.add(prestamo);
		}

		return prestamosEnEstado;
	}

	/**
	 * Obtiene un listado de los posibles usuarios que pueden gestionar un
	 * determinado prestamo.
	 * 
	 * @param prestamo
	 *            Prestamo del que deseamos obtener los posibles gestores.
	 * @return Listado de los posibles usuarios gestores.
	 */
	public Collection getUsuariosGestoresPosibles(PrestamoVO prestamo) {
		List usuariosPosibles = new ArrayList();

		// Si el prestamo no tiene organo solicitante
		if (prestamo.getIdorgsolicitante() == null) {
			// Permisos de los posibles gestores
			String[] permisos = new String[] {
					AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS,
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

			GestionControlUsuariosBI controlUsuariosBI = getGestionControlUsusarios();

			// * Obtener los usuarios que tienen el permiso generico 301
			// (Ampliado de solicitud de prestamos)
			List usuarios = controlUsuariosBI.getUsuariosConPermisos(permisos);
			Iterator it = usuarios.iterator();
			// Verificar que tiene organo asociado
			while (it.hasNext()) {
				UsuarioVO usuario = (UsuarioVO) it.next();

				if (StringUtils.isNotBlank(usuario.getIdUsrSistOrg())
						&& !usuario.getId().equalsIgnoreCase(
								prestamo.getIdusrgestor()))
					usuariosPosibles.add(usuario);
			}

			// Aniadir los superusuarios
			String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
			CollectionUtils
					.addList(usuariosPosibles, controlUsuariosBI
							.getUsuariosConPermisos(permisosSuperUser));
		} else {
			// Permisos de los posibles gestores
			String[] permisos = new String[] {
					AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS,
					AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS,
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

			String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

			GestionControlUsuariosBI controlUsuariosBI = getGestionControlUsusarios();
			usuariosPosibles = controlUsuariosBI.getUsuarios(
					prestamo.getIdorgsolicitante(), permisos);
			if (usuariosPosibles == null)
				usuariosPosibles = new ArrayList();
			CollectionUtils
					.addList(usuariosPosibles, controlUsuariosBI
							.getUsuariosConPermisos(permisosSuperUser));
		}

		return usuariosPosibles;
	}

	/**
	 * Realiza el proceso de asignacion de un prestamo al gestor dado por su
	 * identificador
	 * 
	 * @param prestamo
	 *            Prestamo que deseamos ceder
	 * @param idGestor
	 *            Identificador del gestor al que vamos a ceder el prestamo
	 * @return Los nuevos datos del usuario gestor del prestamo
	 */
	public UsuarioExtendedVO asignarPrestamoAGestor(PrestamoVO prestamo,
			String idGestor) throws ActionNotAllowedException {
		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.CEDER_CONTROL_ACTION);
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CESION_PRESTAMO);
		Locale locale = getServiceClient().getLocale();

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, prestamo.getId());
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_USUARIO_ANTERIOR,
				prestamo.getNusrsolicitante());

		UsuarioExtendedVO usuario = getGestionControlUsusarios()
				.getUsuarioExtendido(idGestor);
		if ((usuario == null) || (usuario.getIdOrgPertenece() == null)) {
			throw new ActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CESION_PRESTAMO_NO_ORGANIZACION_NAME,
					ArchivoErrorCodes.ERROR_CESION_PRESTAMO_NO_ORGANIZACION,
					ArchivoModules.SERVICIOS_MODULE);
		}

		if (prestamo.getIdusrsolicitante() != null
				&& prestamo.getIdusrsolicitante().equalsIgnoreCase(
						prestamo.getIdusrgestor())) {
			prestamo.setNusrsolicitante(usuario.getNombreCompleto());
			prestamo.setIdusrsolicitante(usuario.getId());
			prestamo.setNorgsolicitante(usuario.getNombreOrgPertenece());
			prestamo.setIdorgsolicitante(usuario.getIdOrgPertenece());
		}

		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_USUARIO_NUEVO,
				usuario.getNombreCompleto());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_PRESTAMO_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(prestamo,
						getServiceSession()));

		prestamo.setIdusrgestor(usuario.getId());

		prestamoDBEntity.updatePrestamo(prestamo);

		return usuario;
	}

	/**
	 * Obtiene la lista de gestores con prestamos.
	 * 
	 * @param idOrgano
	 *            Identificador del organo del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrestamos(String idOrgano) {
		return prestamoDBEntity.getGestoresConPrestamos(idOrgano);
	}

	/**
	 * Obtiene la lista de prestamos que cumplan los criterios especificados.
	 * 
	 * @param busquedaVO
	 *            Criterios de busqueda.
	 * @return Lista de prestamos ({@link PrestamoVO}).
	 * @throws TooManyResultsException
	 *             si el numero de resultados es excesivo.
	 */
	public List getPrestamos(BusquedaVO busquedaVO, String[] idsArchivo)
			throws TooManyResultsException {
		LoggingEvent le = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CONSULTA_PRESTAMO);
		Locale locale = getServiceClient().getLocale();

		DataLoggingEvent loggingEventData = new DataLoggingEvent();
		le.getData().add(loggingEventData);

		String estados = AuditoriaUtils.transformOptionsToString(
				busquedaVO.getEstados(), (ArrayList) getEstadosPrestamo());
		String tiposNotas = AuditoriaUtils.transformOptionsToString(
				busquedaVO.getNotas(), (ArrayList) getNotas());

		// auditar el campo de la busqueda si no esta vacio
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_CODIGO_PRESTAMO,
				busquedaVO.getCodigo());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_ORGANO_PRESTAMO,
				busquedaVO.getOrgano());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_SOLICITANTE_PRESTAMO,
				busquedaVO.getSolicitante());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_ESTADOS_PRESTAMO, estados);
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_TIPOSNOTAS_PRESTAMO, tiposNotas);
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
				DateUtils.formatDate(busquedaVO.getFechaInicioEntrega()));
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
				DateUtils.formatDate(busquedaVO.getFechaFinEntrega()));
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_AUTORIZADO,
				busquedaVO.getDatosautorizado());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_TIPO_ENTREGA,
				busquedaVO.getTipoentrega());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_NUMERO_EXPEDIENTE,
				busquedaVO.getExpedienteudoc());

		if (!getServiceClient().hasPermissionAdministracionTotal()) {
			busquedaVO.setIdAppUser(getServiceClient().getId());
			busquedaVO.setIdsArchivosUser(getServiceClient()
					.getIdsArchivosUser());
		}

		return prestamoDBEntity.getPrestamos(busquedaVO, idsArchivo);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#getNumDetalles(java.lang.String)
	 */
	public int getNumDetalles(String idSolicitud) {
		return detallePrestamoDBEntity.getNumDetalles(idSolicitud);
	}

	public void actualizarDetallePrestamo(DetalleVO detalle) {
		detallePrestamoDBEntity.actualizarDetalle(detalle);
	}

	public void actualizarObservaciones(String idSolicitud, String observaciones) {
		prestamoDBEntity.updateObservaciones(idSolicitud, observaciones);
	}

	/**
	 * Obtiene la lista de posibles gestores de revisiones de documentacion
	 * 
	 * @param idArchivo
	 *            Identificador del archivo al que debe pertenecer el gestor
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getUsuariosGestoresRevDocPosibles(String idArchivo) {

		GestionControlUsuariosBI controlAccesoBI = getGestionControlUsusarios();
		// List usuariosGestores = new ArrayList();
		String[] permisos = {
				AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION,
				AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES };

		// Obtener los usuarios que tienen los permisos de edicion de
		// descripcion y de ingreso directo de unidades
		List usuarios = new ArrayList();

		usuarios = controlAccesoBI.getUsuariosConTodosPermisosYArchivo(
				permisos, idArchivo);

		// Aniadir los superusuarios
		String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
		CollectionUtils.addList(usuarios,
				controlAccesoBI.getUsuariosConPermisos(permisosSuperUser));

		return usuarios;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#insertRevisionDocumentacion(solicitudes.
	 * prestamos.vos.RevisionDocumentacionVO)
	 */
	public void insertRevisionDocumentacion(RevisionDocumentacionVO revDocVO) {
		revisionDocumentacionDBEntity.insertRevisionDocumentacion(revDocVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#getAllRevisionDocumentacion()
	 */
	public List getAllRevisionDocumentacion() {
		return revisionDocumentacionDBEntity.getAllRevisionDocumentacion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getRevisionDocumentacionByEstado(java.lang
	 * .String, int)
	 */
	public List getRevisionDocumentacionByEstado(String idUserGestor, int estado) {
		return revisionDocumentacionDBEntity.getRevisionDocumentacionByEstado(
				idUserGestor, estado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getRevisionDocumentacionById(java.lang.String
	 * )
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc) {
		return revisionDocumentacionDBEntity
				.getRevisionDocumentacionById(idRevDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionPrestamosBI#getRevisionesDocumentacionByIdUdocYEstado
	 * (java.lang.String, int[])
	 */
	public List getRevisionesDocumentacionByIdUdocYEstado(String idUdoc,
			int[] estados) {
		return revisionDocumentacionDBEntity
				.getRevisionesDocumentacionByIdUdocYEstado(idUdoc, estados);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#update(solicitudes.prestamos.vos.
	 * RevisionDocumentacionVO)
	 */
	public void actualizarRevisionDocumentacion(RevisionDocumentacionVO revDocVO) {
		revisionDocumentacionDBEntity.update(revDocVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#updateIdAlta(java.lang.String,
	 * java.lang.String)
	 */
	public void updateIdAltaRevisionDocumentacion(String idRevDoc, String idAlta) {
		revisionDocumentacionDBEntity.updateIdAlta(idRevDoc, idAlta);
	}

	/**
	 * Obtiene el numero de unidades documentales con documentacion a revisar.
	 * 
	 * @param idUser
	 *            String id del usuario gestor
	 * @return int
	 */
	public int getCountDocumentacionUDocsARevisar(String idUser) {
		int[] estados = new int[] { EstadoRevDoc.ABIERTA.getIdentificador() };
		return revisionDocumentacionDBEntity.getCountRevisionDocXEstados(
				idUser, estados);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.bi.GestionPrestamosBI#finalizarRevisionDocumentacion(
	 * RevisionDocumentacionVO, boolean)
	 */
	public void finalizarRevisionDocumentacion(
			RevisionDocumentacionVO revDocVO, boolean copiarUdocsRelOrigen) {
		iniciarTransaccion();

		revDocVO.setEstado(EstadoRevDoc.FINALIZADA.getIdentificador());
		actualizarRevisionDocumentacion(revDocVO);
		commit();
	}

	/**
	 * Obtiene la lista de gestores con prestamos.
	 * 
	 * @param idOrgano
	 *            Identificador del organo del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConRevisionesDocumentacion() {
		return revisionDocumentacionDBEntity
				.getGestoresConRevisionesDocumentacion();
	}

	public List getRevisionesDocumentacion(RevisionDocumentacionVO revDocVO)
			throws TooManyResultsException {
		List revisionesDocumentacion = revisionDocumentacionDBEntity
				.getRevisionesDocumentacion(revDocVO);
		List revisionesDocumentacionPO = new ArrayList();

		RevisionDocumentacionToPO transformer = RevisionDocumentacionToPO
				.getInstance(getServiceClient().getLocale(),
						getServiceRepository());
		for (Iterator it = revisionesDocumentacion.iterator(); it.hasNext();) {
			RevisionDocumentacionVO vo = ((RevisionDocumentacionVO) it.next());
			revisionesDocumentacionPO.add(transformer.transform(vo));
		}
		return revisionesDocumentacionPO;
	}

	public List getUsuariosGestoresRevDocPosibles(List idsArchivos) {
		GestionControlUsuariosBI controlAccesoBI = getGestionControlUsusarios();
		// List usuariosGestores = new ArrayList();
		String[] permisos = {
				AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION,
				AppPermissions.ADMINISTRACION_DESCRIPCION };

		// Obtener los usuarios que tienen los permisos de edicion de
		// descripcion y de ingreso directo de unidades
		List usuarios = new ArrayList();

		String[] vIdsArchivos = new String[idsArchivos.size()];
		for (int i = 0; i < idsArchivos.size(); i++)
			vIdsArchivos[i] = (String) idsArchivos.get(i);
		usuarios = controlAccesoBI.getUsuariosConPermisosYArchivos(permisos,
				vIdsArchivos);

		// Aniadir los superusuarios
		String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
		CollectionUtils.addList(usuarios,
				controlAccesoBI.getUsuariosConPermisos(permisosSuperUser));

		return usuarios;
	}

	/**
	 * Realiza el proceso de asignacion de un prestamo al gestor dado por su
	 * identificador
	 * 
	 * @param prestamo
	 *            Prestamo que deseamos ceder
	 * @param idGestor
	 *            Identificador del gestor al que vamos a ceder el prestamo
	 * @return Los nuevos datos del usuario gestor del prestamo
	 */
	public UsuarioVO asignarRevisionDocAGestor(
			RevisionDocumentacionVO revDocVO, String idGestor)
			throws ActionNotAllowedException {
		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.CEDER_CONTROL_ACTION);
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC);
		Locale locale = getServiceClient().getLocale();

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionControlUsuariosBI usuariosBI = services
				.lookupGestionControlUsuariosBI();
		UsuarioVO usuarioAnterior = usuariosBI.getUsuario(revDocVO
				.getIdUsrGestor());
		UsuarioVO usuarioNuevo = usuariosBI.getUsuario(idGestor);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_PRESTAMO, revDocVO.getIdUsrGestor());
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_USUARIO_ANTERIOR,
				usuarioAnterior.getNombreCompleto());

		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_PRESTAMO_USUARIO_NUEVO,
				usuarioNuevo.getNombreCompleto());
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_SIGNATURA,
				revDocVO.getSignaturaUdoc());

		revDocVO.setIdUsrGestor(idGestor);

		revisionDocumentacionDBEntity.update(revDocVO);
		return usuarioNuevo;
	}

	/* Para los Motivos de prestamo */
	public void insertarMotivoPrestamo(MotivoPrestamoVO motivoVO) {
		motivoDBEntity.insertarMotivoPrestamo(motivoVO);
	}

	public MotivoPrestamoVO getMotivoPrestamo(MotivoPrestamoVO motivoVO) {
		return motivoDBEntity.getMotivoPrestamo(motivoVO);
	}

	public List getMotivosPrestamo() {
		return motivoDBEntity.getMotivosPrestamo();
	}

	public MotivoPrestamoVO getMotivoPrestamoById(String idMotivo) {
		return motivoDBEntity.getMotivoPrestamoById(idMotivo);
	}

	public int getCountPrestamoByIdMotivo(String idMotivo) {
		return prestamoDBEntity.getCountPrestamosByIdMotivo(idMotivo);
	}

	public void deleteMotivoPrestamo(MotivoPrestamoVO motivoVO) {
		motivoDBEntity.deleteMotivoPrestamo(motivoVO);
	}

	public void actualizarMotivoPrestamo(MotivoPrestamoVO motivoVO) {
		motivoDBEntity.actualizarMotivoPrestamo(motivoVO);
	}

	public Collection getMotivosByTipoUsuario(Integer tipoUsuario,
			Integer[] visibilidad) {
		return motivoDBEntity.getMotivosByTipoUsuario(tipoUsuario, visibilidad);
	}

	public List getPrestamosAniadirUDocsFromBusqueda(String idArchivo,
			String idUsuario) {

		String[] estados = new String[] { String
				.valueOf(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO) };
		return prestamoDBEntity.getPrestamosByIdarchivoAndIdUsuarioGestor(
				idArchivo, idUsuario, estados);
	}

	public int getCountPrestamosEnElaboracionXIdUsuario(String idUsuario) {
		return prestamoDBEntity
				.getCountPrestamosEnElaboracionXIdUsuario(idUsuario);
	}

	public Collection getPrestamosEnElaboracionXIdUsuario(String idUsuario) {
		return prestamoDBEntity.getPrestamosEnElaboracionXIdUsuario(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionPrestamosBI#solicitarProrroga(solicitudes.prestamos.vos.ProrrogaVO,
	 *      boolean)
	 */
	public void solicitarProrroga(ProrrogaVO prorrogaVO, boolean autorizar)
			throws PrestamoActionNotAllowedException {

		iniciarTransaccion();
		solicitarProrroga(prorrogaVO);

		if (autorizar) {
			autorizarProrrogas(prorrogaVO.getId(), prorrogaVO.getIdPrestamo(),
					prorrogaVO.getFechaFinProrroga());
		}
		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionPrestamosBI#getProrrogas(java.lang.String)
	 */
	public List getProrrogas(String idPrestamo) {
		return prorrogaDBEntity.getProrrogasByIdPrestamo(idPrestamo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionPrestamosBI#getProrrogaActiva(java.lang.String)
	 */
	public ProrrogaVO getProrrogaActiva(String idPrestamo) {
		return prorrogaDBEntity.getProrrogaActiva(idPrestamo);
	}
}