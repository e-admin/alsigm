package solicitudes.consultas.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import salas.vos.UsuarioSalasConsultaVO;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.db.ConsultaDBEntity;
import solicitudes.consultas.db.IConsultaDBEntity;
import solicitudes.consultas.db.IMotivoConsultaDBEntity;
import solicitudes.consultas.db.ITemaDBEntity;
import solicitudes.consultas.db.MotivoConsultaDBEntityImpl;
import solicitudes.consultas.db.TemaDBEntity;
import solicitudes.consultas.db.TemaDBEntityImpl;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.vos.BusquedaVO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.consultas.vos.EstadoVO;
import solicitudes.consultas.vos.MotivoConsultaVO;
import solicitudes.consultas.vos.TemaVO;
import solicitudes.db.DetalleDBEntity;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.INSecSolicitudesDBEntity;
import solicitudes.db.MotivoRechazoDBEntity;
import solicitudes.db.MotivoRechazoDBEntityImpl;
import solicitudes.db.NSecDBEntityImpl;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.utils.PropertyHelper;
import solicitudes.vos.DetalleUdocNoDisponibleVO;
import solicitudes.vos.DetalleVO;
import util.StringOwnTokenizer;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.utils.AuditoriaUtils;

import common.CodigoTransferenciaUtils;
import common.Messages;
import common.bi.GestionConsultasBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
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
import descripcion.vos.CampoFechaVO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.FondoVO;

/**
 * Implementacion del Bussiness Interface para el modulo de consultas.
 * 
 */
public class GestionConsultasBIImpl extends ServiceBase implements
		GestionConsultasBI {

	private ITemaDBEntity temaDBEntity = null;
	private IMotivoRechazoDBEntity motivoRechazoDBEntity = null;
	private IMotivoConsultaDBEntity motivoDBEntity = null;
	private INSecSolicitudesDBEntity nSecDBEntity = null;
	private IConsultaDBEntity consultaDBEntity = null;
	private IDetalleDBEntity detalleConsultaDBEntity = null;
	private IUDocEnUiDepositoDbEntity uDocEnUiDepositoDbEntity = null;
	private IUInstalacionDepositoDBEntity uInstalacionDepositoDBEntity = null;

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public GestionConsultasBIImpl(ServiceSession ss) {
		this.temaDBEntity = new TemaDBEntityImpl(ss.getTransactionManager());
		this.motivoRechazoDBEntity = new MotivoRechazoDBEntityImpl(
				ss.getTransactionManager());
		this.motivoDBEntity = new MotivoConsultaDBEntityImpl(
				ss.getTransactionManager());
		this.nSecDBEntity = new NSecDBEntityImpl(ss.getTransactionManager());
		this.consultaDBEntity = new ConsultaDBEntity(ss.getTransactionManager());
		this.detalleConsultaDBEntity = new DetalleDBEntityImpl(
				ss.getTransactionManager());
		this.uDocEnUiDepositoDbEntity = new UDocEnUiDepositoDbEntityImpl(
				ss.getTransactionManager());
		this.uInstalacionDepositoDBEntity = new UInstalacionDepositoDBEntity(
				ss.getTransactionManager());
	}

	/**
	 * Constructor de entidades
	 * 
	 * @param tde
	 *            Entidad para acceso a temas
	 */
	public GestionConsultasBIImpl(ITemaDBEntity tde,
			IMotivoRechazoDBEntity mrde, IMotivoConsultaDBEntity mde,
			INSecSolicitudesDBEntity nsde, IDetalleDBEntity dcde,
			IUDocEnUiDepositoDbEntity udedde, IConsultaDBEntity cde,
			IUInstalacionDepositoDBEntity uidde) {
		this.temaDBEntity = tde;
		this.motivoRechazoDBEntity = mrde;
		this.motivoDBEntity = mde;
		this.nSecDBEntity = nsde;
		this.detalleConsultaDBEntity = dcde;
		this.uDocEnUiDepositoDbEntity = udedde;
		this.consultaDBEntity = cde;
		this.uInstalacionDepositoDBEntity = uidde;
	}

	public ConsultaVO verConsulta(String codconsulta) {
		Locale locale = getServiceClient().getLocale();

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CONSULTA_CONSULTA);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, codconsulta);

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.VER_CONSULTA_ACTION);

		ConsultaVO ret = consultaDBEntity.getConsulta(codconsulta);

		// Creamos el evento de Loggin de auditoria con sus datos asociados

		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(ret,
						getServiceSession()));

		return ret;
	}

	public ConsultaVO getConsulta(String codconsulta) {
		return consultaDBEntity.getConsulta(codconsulta);
	}

	/**
	 * Indica si un usuario tiene consultas en curso.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return true si el usuario tiene pr�stamos en curso.
	 */
	public boolean hasConsultasEnCurso(String idUsuario) {
		String[] estados = {
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_ABIERTA),
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA),
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA),
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_ENTREGADA),
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_RESERVADA),
				String.valueOf(ConsultasConstants.ESTADO_CONSULTA_SOLICITADA) };
		return consultaDBEntity.hasConsultas(idUsuario, estados);
	}

	public int getCountConsultasXEstados(String[] estados, String[] idsArchivo) {
		return consultaDBEntity.getCountConsultasXEstados(estados, idsArchivo);
	}

	public List getConsultasXEstados(List estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException {
		return consultaDBEntity.getConsultasXEstados(estados, busqueda,
				idsArchivo);
	}

	public Collection getConsultasXEstadosYUsuario(String idUsuario,
			List estados, BusquedaVO busqueda) throws TooManyResultsException {
		return consultaDBEntity.getConsultasXEstadosYUsuario(idUsuario,
				estados, busqueda);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionConsultasBI#getCountConsultasXUsuarioConsultor(java.
	 * lang.String)
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioSolicitante) {
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_SOLICITADA,
				ConsultasConstants.ESTADO_CONSULTA_RESERVADA,
				ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA,
				ConsultasConstants.ESTADO_CONSULTA_DENEGADA,
				ConsultasConstants.ESTADO_CONSULTA_ENTREGADA };
		return consultaDBEntity.getCountConsultasXUsuarioConsultor(
				nUsuarioSolicitante, estados);
	}

	public int getCountConsultasXUsuarioConsultor(String nUsuarioSolicitante,
			String[] idsArchivo) {
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_SOLICITADA,
				ConsultasConstants.ESTADO_CONSULTA_RESERVADA,
				ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA,
				ConsultasConstants.ESTADO_CONSULTA_DENEGADA,
				ConsultasConstants.ESTADO_CONSULTA_ENTREGADA };
		return consultaDBEntity.getCountConsultasXUsuarioConsultor(
				nUsuarioSolicitante, estados, idsArchivo);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionConsultasBI#getCountConsultasAbiertasXUsuarioConsultor
	 * (java.lang.String)
	 */
	public int getCountConsultasAbiertasXUsuarioConsultor(
			String nUsuarioSolicitante) {
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_ABIERTA };
		return consultaDBEntity.getCountConsultasXUsuarioConsultor(
				nUsuarioSolicitante, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionConsultasBI#getConsultasXUsuarioConsultor(java.lang.
	 * String)
	 */
	public List getConsultasAbiertasXUsuarioConsultor(String nUsuarioSolicitante) {
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_ABIERTA };
		return consultaDBEntity.getConsultasXUsuarioConsultor(
				nUsuarioSolicitante, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionConsultasBI#getConsultasXUsuarioConsultor(java.lang.
	 * String)
	 */
	public List getConsultasXUsuarioConsultor(String nUsuarioSolicitante) {
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_SOLICITADA,
				ConsultasConstants.ESTADO_CONSULTA_RESERVADA,
				ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA,
				ConsultasConstants.ESTADO_CONSULTA_DENEGADA,
				ConsultasConstants.ESTADO_CONSULTA_ENTREGADA };
		return consultaDBEntity.getConsultasXUsuarioConsultor(
				nUsuarioSolicitante, estados);
	}

	public Collection getConsultasXUsuarioGestorAbiertos(String idUsuario) {
		return consultaDBEntity.getConsultasXUsuarioGestorAbiertos(idUsuario);
	}

	public Collection getConsultasXUsuarioConsultorAbiertos(String nUsuario) {
		return consultaDBEntity.getConsultasXUsuarioConsultorAbiertos(nUsuario);
	}

	public TemaVO insertTema(String id, String tema, boolean consultaEnSala) {
		boolean encontrado = false;
		TemaVO temaVO = new TemaVO();

		if (consultaEnSala) {
			temaVO.setIdUsrCSala(id);
		} else {
			temaVO.setIdUsuario(id);
		}

		temaVO.setTema(tema);
		temaVO.setTipoEntidad(Integer
				.parseInt(ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR));

		for (Iterator it = getTemasUsuario(id).iterator(); it.hasNext();) {
			TemaVO temaIt = (TemaVO) it.next();
			if (temaIt.getIdUsuario().equals(id)
					&& temaIt.getTema().equalsIgnoreCase(tema))
				encontrado = true;
		}

		if (!encontrado)
			temaDBEntity.insertTema(temaVO);

		return temaVO;
	}

	public void insertConsulta(ConsultaVO vo, ServiceClient user)
			throws ConsultaActionNotAllowedException {
		// Comprobamos los permisos
		Locale locale = getServiceClient().getLocale();
		this.check(ServiciosSecurityManager.INSERTAR_CONSULTA_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnConsultaInsert(user, this);

		iniciarTransaccion();

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ALTA_CONSULTA);

		int numSecuencia = nSecDBEntity.incrementarNumeroSecConsulta(vo
				.getAno());
		vo.setId(vo.getAno() + "/" + numSecuencia);
		vo.setOrden(numSecuencia);

		// Obtener el motivo
		String idMotivo = vo.getIdMotivo();
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoConsultaVO motivo = motivoDBEntity
					.getMotivoConsultaById(idMotivo);
			if (motivo != null) {
				vo.setMotivo(motivo.getMotivo());
			}
		}

		consultaDBEntity.insertConsulta(vo);

		List detallesConsulta = vo.getDetallesConsulta();
		if (ListUtils.isNotEmpty(detallesConsulta)) {

			for (Iterator iterator = detallesConsulta.iterator(); iterator
					.hasNext();) {
				DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) iterator
						.next();

				detalleConsulta.setIdsolicitud(vo.getId());
				nuevoDetallesConsulta(detalleConsulta);
			}
		}

		// auditoria de la creacion

		String codigoConsultaArchivo = CodigoTransferenciaUtils
				.getCodigoTransferenciaFromVO(vo, getServiceSession());
		int pos = codigoConsultaArchivo.lastIndexOf("/");
		if (pos != -1) {
			codigoConsultaArchivo = codigoConsultaArchivo.substring(0, pos + 1)
					+ numSecuencia;
		}

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, vo.getId());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				codigoConsultaArchivo);

		commit();
	}

	public void actualizarConsulta(ConsultaVO vo, ServiceClient sc)
			throws ConsultaActionNotAllowedException {
		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_EDICION_CONSULTA);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.EDITAR_CONSULTA_ACTION);
		ConditionChecker.checkOnConsultaEdit(vo, sc);

		// Obtener el motivo
		String idMotivo = vo.getIdMotivo();
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoConsultaVO motivo = motivoDBEntity
					.getMotivoConsultaById(idMotivo);
			if (motivo != null) {
				vo.setMotivo(motivo.getMotivo());
			}
		}

		consultaDBEntity.updateConsulta(vo);

		// registro de la modificacion del prestamo
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_CONSULTA, vo.getId())
				// .addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA, vo.toXML());
				.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA,
						vo.getAno() + '/' + vo.getOrden())
				.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_CONSULTA_SOLICITANTE,
						vo.getNusrconsultor());
	}

	public Collection getDetallesConsulta(String codigoConsulta) {

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();

		Collection detallesConsulta = detalleConsultaDBEntity.getDetallesXTipo(
				codigoConsulta, DetalleDBEntity.TIPO_DETALLE_CONSULTA);

		Iterator it = detallesConsulta.iterator();
		while (it.hasNext()) {
			DetalleConsultaVO d = (DetalleConsultaVO) it.next();

			// Rellenar ubicaci�n
			d.setUbicacion(this.getUbicacionDetalleConsulta(d.getIdudoc(),
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

		// return detalleConsultaDBEntity.getDetallesXTipo(codigoConsulta,
		// DetalleDBEntity.TIPO_DETALLE_CONSULTA);

		return detallesConsulta;
	}

	public Collection getDetallesConsultaAutorizadasEntregadas(
			String codigoConsulta) {
		ArrayList estados = new ArrayList();
		estados.add(new Integer(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA));
		estados.add(new Integer(ConsultasConstants.ESTADO_DETALLE_ENTREGADA));

		return detalleConsultaDBEntity.getDetallesSolicitudXEstado(estados,
				codigoConsulta, DetalleDBEntity.TIPO_DETALLE_CONSULTA);
	}

	public Collection getDetallesConsultaDevueltas(String codigoConsulta) {
		this.check(ServiciosSecurityManager.OBTENER_UDOCS_DEVUELTAS_CONSULTA_ACTION);

		Collection detalles = detalleConsultaDBEntity.getUnidadesDevueltas(
				codigoConsulta, DetalleDBEntity.TIPO_DETALLE_CONSULTA);

		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetalleConsultaVO d = (DetalleConsultaVO) it.next();

			d.setUbicacion(this.getUbicacionDetalleConsulta(d.getIdudoc(),
					d.getSignaturaudoc()));
		}

		return detalles;
	}

	/**
	 * Obtiene un listado de consultas a partir de sus identificadores.
	 * 
	 * @param codigos
	 *            Listado de los identificadores de las consultas que deseamo
	 *            recuperar
	 * @return Listado de las consultas
	 */
	public Collection getConsultas(String[] codigos) {
		return consultaDBEntity.getConsultas(codigos);
	}

	public void eliminarConsultas(String[] consultasAEliminar)
			throws ConsultaActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_BAJA_CONSULTA);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_LISTADO_CONSULTAS, null);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.ELIMINAR_CONSULTA_ACTION);
		// comprobar sin son prestamos borrables
		ConditionChecker.checkOnEliminarConsultas(consultasAEliminar, this,
				getServiceClient());

		iniciarTransaccion();

		for (int i = 0; i < consultasAEliminar.length; i++) {
			detalleConsultaDBEntity.deleteDetalles(consultasAEliminar[i],
					DetalleDBEntity.TIPO_DETALLE_CONSULTA);

			consultaDBEntity.deleteConsulta(consultasAEliminar[i]);

			ConsultaVO consulta = getConsulta(consultasAEliminar[i]);

			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
					CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
							consulta, getServiceSession()));
		}

		commit();
	}

	/**
	 * Obtiene el n�mero de detalles que est�n asociados a una determinada
	 * consulta.
	 * 
	 * @param idConsutla
	 *            Identificador de la consulta del que deseamos conocer su
	 *            numero de detalles asociados.
	 * @return Numero de detalless de asociados a la consulta.
	 */
	public int numeroDetallesConsulta(String idConsulta) {
		return consultaDBEntity.numeroDetallesConsulta(idConsulta);
	}

	// public void enviarConsulta(String idConsulta) throws
	// ConsultaActionNotAllowedException {
	public void enviarConsulta(ConsultaVO consulta)
			throws ConsultaActionNotAllowedException {
		// ConsultaVO consulta = getConsulta(idConsulta);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ENVIAR_CONSULTA_ACTION);

		ConditionChecker.checkOnEnviarConsulta(consulta, this,
				getServiceClient());

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA);

		// registro del envio del prestamo
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, consulta.getId());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));

		consultaDBEntity.enviarConsulta(consulta, getServiceClient()
				.getUserType());
	}

	public void entregarConsulta(String idConsulta)
			throws ConsultaActionNotAllowedException {
		Collection udocsEntregadas = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		// Realizamos las comprobaciones de seguridad
		this.check(ServiciosSecurityManager.ENTREGAR_CONSULTA_ACTION);

		// Realizamos comprobaciones adicionales
		ConditionChecker.checkOnEntrega(idConsulta, this, getServiceClient());

		// Iniciamos la transaction
		iniciarTransaccion();

		// Obtenemos la consultaautorizardenegarConsultaautorizardenegarConsulta
		// asociada al id
		ConsultaVO consulta = this.getConsulta(idConsulta);

		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, idConsulta);
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));

		// Obtenemos las udocs de la consulta a entregar
		Collection detalles = this.getDetallesConsulta(idConsulta);

		// Se entregan solo las udocs autorizadas
		Iterator it = detalles.iterator();
		while (it.hasNext()) {
			DetalleConsultaVO detalle = (DetalleConsultaVO) it.next();

			if (detalle.getEstado() == ConsultasConstants.ESTADO_SOLICITUD_AUTORIZADA) {
				detalle.setEstado(ConsultasConstants.ESTADO_SOLICITUD_ENTREGADA);
				detalle.setFestado(DateUtils.getFechaActual());

				data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_CONSULTA,
						detalle.getIdudoc());
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						detalle.getSignaturaudoc());

				// Actualizamos el detalle
				detalleConsultaDBEntity.actualizarDetalle(detalle);
				// A�adimos la udoc a las entregadas
				udocsEntregadas.add(detalle);
			}
		}

		consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_ENTREGADA);
		consulta.setFentrega(DBUtils.getFechaActual());
		consulta.setFestado(DateUtils.getFechaActual());

		// data =
		// event.getDataLoggingEvent(ArchivoObjects.OBJECT_CONSULTA,consulta.getId());
		// data.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_ID,
		// CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,getServiceSession())
		// );

		// Actualizamos el prestamos
		consultaDBEntity.updateConsulta(consulta);

		commit();
	}

	public void nuevoDetallesConsulta(DetalleConsultaVO detalleConsulta) {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_ALTA_UDOC);
		Locale locale = getServiceClient().getLocale();

		this.check(ServiciosSecurityManager.ANIADIR_UDOC_CONSULTA_ACTION);

		detalleConsultaDBEntity.insertDetalleConsulta(detalleConsulta);

		ConsultaVO consulta = getConsulta(detalleConsulta.getIdsolicitud());

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DETALLE_CONSULTA,
				detalleConsulta.getIdudoc());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
				detalleConsulta.getSignaturaudoc());
	}

	public void denegarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo)
			throws ConsultaActionNotAllowedException {
		this.check(ServiciosSecurityManager.DENEGAR_CONSULTA_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnAutorizarDetalle(consulta, getServiceClient());
		Locale locale = getServiceClient().getLocale();

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA);

		detalleConsultaDBEntity.denegarDetalleConsulta(consulta, idudoc,
				signaturaudoc, motivo, idMotivoRechazo);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DETALLE_CONSULTA, idudoc);
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_DENEGADO,
				"Detalle Denegado");
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
				signaturaudoc);
	}

	public void autorizarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc) throws ConsultaActionNotAllowedException {
		this.check(ServiciosSecurityManager.AUTORIZAR_CONSULTA_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnAutorizarDetalle(consulta, getServiceClient());
		Locale locale = getServiceClient().getLocale();

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA);

		detalleConsultaDBEntity.autorizarDetalleConsulta(consulta, idudoc,
				signaturaudoc);

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DETALLE_CONSULTA, idudoc);
		data.addDetalle(locale,
				ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_ACEPTADO,
				"Detalle Aceptado");
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
				signaturaudoc);
	}

	/**
	 * Realiza la devolucion de las unidades documentales seleccionadas.
	 * 
	 * @param udocs
	 *            Unidades documentales a devolver
	 */
	public void devolverUnidadesDocumentales(ArrayList udocs)
			throws ConsultaActionNotAllowedException {
		try {
			getGestionPrestamosBI().devolverUnidadesDocumentales(udocs);
		} catch (PrestamoActionNotAllowedException panae) {
			throw new ConsultaActionNotAllowedException(panae.getCodError(),
					panae.getMotivo());
		}
	}

	// public void devolverDetalleConsulta(ConsultaVO consulta, String
	// idudoc,String signaturaudoc) throws ConsultaActionNotAllowedException{
	public void devolverDetalleConsulta(DetalleConsultaVO detalle)
			throws ConsultaActionNotAllowedException {

		// DetalleConsultaVO detalle =
		// (DetalleConsultaVO)detalleConsultaDBEntity.getDetalleEntregado(idudoc,signaturaudoc,DetalleDBEntity.TIPO_DETALLE_CONSULTA);
		Locale locale = getServiceClient().getLocale();
		if (detalle == null)
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_DEVOLUCION_Y_NO_ENTREGADA,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO, locale));
		// ConsultaActionNotAllowedException.XESTADO);

		detalle.setEstado(ConsultasConstants.ESTADO_SOLICITUD_DEVUELTA);
		detalle.setFestado(DateUtils.getFechaActual());

		detalleConsultaDBEntity.actualizarDetalle(detalle);
	}

	public DetalleConsultaVO getDetalleConsulta(String codigoConsulta,
			String idudoc, String signatura) throws DetalleNotFoundException {
		return (DetalleConsultaVO) detalleConsultaDBEntity.getDetalle(
				codigoConsulta, idudoc, signatura,
				DetalleDBEntity.TIPO_DETALLE_CONSULTA);
	}

	public String getUbicacionDetalleConsulta(String idudoc, String signatura) {
		return detalleConsultaDBEntity.getUbicacionDetalle(idudoc, signatura);
	}

	public Collection getMotivosRechazo() {
		return motivoRechazoDBEntity
				.getMotivos(MotivoRechazoDBEntity.TIPO_SOLICITUD_CONSULTAS);
	}

	/**
	 * Realiza la b�squeda de consultas.
	 * 
	 * @param busqueda
	 *            Objeto con los filtros para aplicar en la busqueda
	 * @return Listado de las consultas.
	 * @throws TooManyResultsException
	 *             si el n�mero de resultados es excesivo.
	 */
	public List getConsultas(BusquedaVO busqueda, String[] idsArchivo)
			throws TooManyResultsException {
		LoggingEvent le = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_CONSULTA_CONSULTA);
		Locale locale = getServiceClient().getLocale();

		DataLoggingEvent loggingEventData = new DataLoggingEvent();
		le.getData().add(loggingEventData);

		String estados = AuditoriaUtils.transformOptionsToString(
				busqueda.getEstados(), (ArrayList) getEstadosConsulta());

		// auditar el campo de la busqueda si no est� vac�o
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_CODIGO_CONSULTA,
				busqueda.getCodigo());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_ORGANO_CONSULTA,
				busqueda.getOrgano());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_SOLICITANTE_CONSULTA,
				busqueda.getSolicitante());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_ESTADOS_CONSULTA, estados);
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
				DateUtils.formatDate(busqueda.getFechaInicioEntrega()));
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
				DateUtils.formatDate(busqueda.getFechaFinEntrega()));
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_AUTORIZADO,
				busqueda.getDatosautorizado());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_TIPO_ENTREGA,
				busqueda.getTipoentrega());
		loggingEventData.addDetalleNoVacio(locale,
				ArchivoDetails.SOLICITUDES_NUMERO_EXPEDIENTE,
				busqueda.getExpedienteudoc());

		// posiblemente pendiente de auditar:
		// String idSolicitante = null;

		if (!getServiceClient().hasPermissionAdministracionTotal()) {
			busqueda.setIdAppUser(getServiceClient().getId());
			busqueda.setIdsArchivosUser(getServiceClient().getIdsArchivosUser());
		}

		return consultaDBEntity.getConsultas(busqueda, idsArchivo);
	}

	public void eliminarDetallesConsulta(ConsultaVO consulta,
			String[] idsudocs, String[] signaturasudocs) {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_BAJA_UDOC);
		Locale locale = getServiceClient().getLocale();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.DELETE_DETALLES_CONSULTA_ACTION);

		iniciarTransaccion();

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, consulta.getId());
		data.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));

		for (int i = 0; i < idsudocs.length; i++) {
			detalleConsultaDBEntity.deleteDetalle(consulta.getId(),
					idsudocs[i], signaturasudocs[i],
					DetalleDBEntity.TIPO_DETALLE_CONSULTA);

			// Creamos el evento de Loggin de auditoria con sus datos asociados
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DETALLE_CONSULTA, idsudocs[i]);
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					signaturasudocs[i]);
		}

		commit();
	}

	public Collection getEstadosConsulta() {
		ArrayList estados = new ArrayList();

		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_ABIERTA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_SOLICITADA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_RESERVADA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_DENEGADA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_ENTREGADA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA));
		estados.add(getEstadoVO(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA));

		return estados;
	}

	private EstadoVO getEstadoVO(int id) {
		Locale locale = getServiceClient().getLocale();
		String label;
		String key = ConsultasConstants.LABEL_ESTADO_CONSULTAS_BASE + id;
		label = Messages.getString(key, locale);

		return new EstadoVO(label, id);
	}

	public List getTemasTipoEntidad(String tipo) {
		return temaDBEntity.getTemasTipoEntidad(tipo);
	}

	public List getTemasUsuario(String id) {
		return temaDBEntity.getTemasUsuario(id);
	}

	public List getTemasUsuarioInvestigador(String idUsuarioInvestigador) {
		return temaDBEntity.getTemasUsuarioInvestigador(idUsuarioInvestigador);
	}

	public List getTemasUsuarioSala(String idUsrCSala) {
		return temaDBEntity.getTemasUsuarioSala(idUsrCSala);
	}

	public List getMotivosByTipoEntidad(int tipo) {
		return motivoDBEntity.getMotivosByTipoEntidad(tipo);
	}

	public List getMotivosByTipoConsulta(int tipo) {
		return motivoDBEntity.getMotivosByTipoConsulta(tipo);
	}

	/**
	 * Metodo para la creacion de un evento espec�fico para el modulo de
	 * consultas
	 */
	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.SERVICIOS_MODULE,
				action, getServiceClient(), false);

		// Lo a�adimos a la pila
		getLogger().add(le);

		return le;
	}

	/**
	 * Realiza la comprobacion de seguridad para la accion indicada de este
	 * m�dulo
	 * 
	 * @param action
	 *            Action que se desea comprobar
	 */
	protected void check(ActionObject action) {
		getSecurityManager().check(action, getServiceClient());
	}

	public int getCountListadoConsultasGestionar(ServiceClient userVO) {
		int consultas = 0;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_GESTION_ACTION);

		if (userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DENEGADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_RESERVADA));
			estados = new String[3];
			estados[0] = ""
					+ ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA;
			estados[1] = "" + ConsultasConstants.ESTADO_CONSULTA_ENTREGADA;
			estados[2] = "" + ConsultasConstants.ESTADO_CONSULTA_SOLICITADA;

			consultas = this.getCountConsultasXEstados(estados,
					userVO.getIdsArchivosUser());

		} else if (userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
			// las que tengan estado �Abierta� y haya solicitado
			int[] estadosGestion = new int[1];
			estadosGestion[0] = ConsultasConstants.ESTADO_CONSULTA_ABIERTA;
			consultas = consultaDBEntity
					.getCountConsultasXUsuarioConsultor(userVO.getId(),
							estadosGestion, userVO.getIdsArchivosUser());

			// no tengan estado �Abierta�
			// estados = new String [5];
			estados = new String[4];
			estados[0] = "" + ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA;
			estados[1] = ""
					+ ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA;
			estados[2] = "" + ConsultasConstants.ESTADO_CONSULTA_ENTREGADA;
			estados[3] = "" + ConsultasConstants.ESTADO_CONSULTA_SOLICITADA;
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DENEGADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA));
			/*
			 * estados[1] = "" + ConsultasConstants.ESTADO_CONSULTA_RESERVADA;
			 * estados[2] = "" +
			 * ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA;
			 * estados[3] = "" + ConsultasConstants.ESTADO_CONSULTA_ENTREGADA;
			 * estados[4] = "" + ConsultasConstants.ESTADO_CONSULTA_SOLICITADA;
			 */

			consultas += this.getCountConsultasXEstados(estados,
					userVO.getIdsArchivosUser());

		} else if (userVO
				.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			estados = new String[3];
			estados[0] = "" + ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA;
			estados[1] = "" + ConsultasConstants.ESTADO_CONSULTA_ENTREGADA;
			estados[2] = ""
					+ ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA;

			consultas = this.getCountConsultasXEstados(estados,
					userVO.getIdsArchivosUser());

		} else if (userVO
				.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS)) {
			consultas = this.getCountConsultasXUsuarioConsultor(userVO.getId());
		}

		return consultas;
	}

	public int getCountListadoConsultasGestionarReserva(ServiceClient userVO) {
		int consultas = 0;
		String[] estados = null;

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_GESTION_ACTION);

		if (userVO.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
			// las que tengan estado �Abierta� y haya solicitado
			estados = new String[1];

			estados[0] = "" + ConsultasConstants.ESTADO_CONSULTA_RESERVADA;

			consultas = this.getCountConsultasXEstados(estados,
					userVO.getIdsArchivosUser());

		}

		return consultas;
	}

	public List getListadoConsultasGestionar(ServiceClient userVO) {
		List consultas = null;
		ArrayList estados = new ArrayList();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_GESTION_ACTION);

		if (userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DENEGADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_RESERVADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_ENTREGADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_SOLICITADA));

			try {
				consultas = this.getConsultasXEstados(estados, null,
						userVO.getIdsArchivosUser());
			} catch (TooManyResultsException e) {
				// Nunca se va a dar esta excepci�n
			}
		} else if (userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
			// las que tengan estado �Abierta� y haya solicitado
			try {
				BusquedaVO busqueda = new BusquedaVO();
				String[] estadosConsulta = new String[1];
				estadosConsulta[0] = ""
						+ ConsultasConstants.ESTADO_CONSULTA_ABIERTA;
				busqueda.setEstados(estadosConsulta);
				busqueda.setIdSolicitante(userVO.getId());
				consultas = this.getConsultas(busqueda,
						userVO.getIdsArchivosUser());
			} catch (TooManyResultsException e) {
				// Nunca se va a dar esta excepci�n
			}

			// no tengan estado �Abierta�
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DENEGADA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_DEVUELTA));
			// estados.add(new
			// Integer(ConsultasConstants.ESTADO_CONSULTA_RESERVADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_ENTREGADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_SOLICITADA));

			try {
				consultas.addAll(this.getConsultasXEstados(estados, null,
						userVO.getIdsArchivosUser()));
			} catch (TooManyResultsException e1) {
				// Nunca se va a dar esta excepci�n
			}
		} else if (userVO
				.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_ENTREGADA));
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA));

			try {
				consultas = this.getConsultasXEstados(estados, null,
						userVO.getIdsArchivosUser());
			} catch (TooManyResultsException e) {
				// Nunca se va a dar esta excepci�n
			}
		} else if (userVO
				.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS)) {
			consultas = this.getConsultasXUsuarioConsultor(userVO.getId());
		}

		return consultas;
	}

	public List getListadoConsultasGestionarReserva(ServiceClient userVO) {
		List consultas = null;
		ArrayList estados = new ArrayList();

		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_GESTION_ACTION);

		if (userVO.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
			estados.add(new Integer(
					ConsultasConstants.ESTADO_CONSULTA_RESERVADA));

			try {
				consultas = this.getConsultasXEstados(estados, null,
						userVO.getIdsArchivosUser());
			} catch (TooManyResultsException e) {
				// Nunca se va a dar esta excepci�n
			}
		}
		return consultas;
	}

	public DetalleConsultaVO tratarDetalleConsulta(DetalleConsultaVO detalle) {
		String entidadProductora = "";
		String expediente = "";

		// Asingamos el nombre de fondo
		FondoVO fondo = getGestionFondosBI().getFondoXId(detalle.getIdFondo());

		if (fondo != null)
			detalle.setFondo(fondo.getTitulo());

		if (StringUtils.isNotEmpty(detalle.getExpedienteudoc())) {
			// Asignamos el expediente y la entidad productor
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

	private int calcularDisponibilidad(
			DetalleConsultaVO detalleConsultaAComprobar,
			DetalleConsultaVO detalleEnOtraConsulta, Date fechaInicioConsulta,
			Date fechaFinalConsulta, boolean isReserva) {

		int disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;

		boolean disponibleXFechas = true; // Variable local a cada detalle que
											// indica si cumple las condiciones
											// de disponibilidad por fechas
		boolean disponibleXReserva = true; // Variable local a cada detalle que
											// indica si cumple las condiciones
											// de disponibilidad por tener una
											// reserva

		// Explicaci�n de condiciones de fechas
		// El detalle est� disponible en fecha de nuestro pr�stamo siempre y
		// cuando se cumpla que:
		/*
		 * 1. La fecha de inicio de uso de la unidad no est� entre las fechas
		 * de inicio y fin de nuestro pr�stamo 2. Las fechas de nuestro
		 * pr�stamo no contengan por ambos lados a las fechas de uso de la
		 * unidad 3. Las fechas de uso de la unidad no contengan por ambos lados
		 * a las fechas de nuestro pr�stamo
		 */
		if ((detalleEnOtraConsulta.getFinicialuso() != null)
				&& (detalleEnOtraConsulta.getFfinaluso() != null)) {
			if ((DateUtils.getFechaSinHora(
					detalleEnOtraConsulta.getFinicialuso()).compareTo(
					DateUtils.getFechaSinHora(fechaFinalConsulta)) <= 0 && DateUtils
					.getFechaSinHora(detalleEnOtraConsulta.getFinicialuso())
					.compareTo(DateUtils.getFechaSinHora(fechaInicioConsulta)) >= 0)
					|| (DateUtils.getFechaSinHora(
							detalleEnOtraConsulta.getFinicialuso()).compareTo(
							DateUtils.getFechaSinHora(fechaInicioConsulta)) >= 0 && DateUtils
							.getFechaSinHora(
									detalleEnOtraConsulta.getFfinaluso())
							.compareTo(
									DateUtils
											.getFechaSinHora(fechaFinalConsulta)) <= 0)
					|| (DateUtils.getFechaSinHora(
							detalleEnOtraConsulta.getFinicialuso()).compareTo(
							DateUtils.getFechaSinHora(fechaInicioConsulta)) <= 0 && DateUtils
							.getFechaSinHora(
									detalleEnOtraConsulta.getFfinaluso())
							.compareTo(
									DateUtils
											.getFechaSinHora(fechaInicioConsulta)) >= 0)) {
				disponibleXFechas = false;
			} else {
				// Si a�n estando en los rangos de fechas esperados, resulta
				// que la unidad documental sigue entregada, no est�
				// disponible
				// Este caso no se contemplaba, que es el que alguien no
				// devuelve la unidad cuando deber�a haberlo hecho o bien, si
				// bien, hay
				// que tener en cuenta que esto s�lo se aplica si coincide que
				// la fecha de inicio del pr�stamo sea anterior o igual a hoy
				// (sino es que estamos en un pr�stamo con reserva que para la
				// fecha en que se vaya a entregar puede estar disponible ya
				if (detalleEnOtraConsulta.getEstado() == ConsultasConstants.ESTADO_DETALLE_ENTREGADA
						&& DateUtils.getFechaActualSinHora().compareTo(
								DateUtils.getFechaSinHora(fechaInicioConsulta)) >= 0)
					disponibleXFechas = false;
			}

			// Si el pr�stamo que queremos crear no es con reserva y los
			// detalles que referencian a la misma unidad
			// est�n autorizados o entregados, la unidad no est� disponible
			if (!isReserva) {
				if ((detalleEnOtraConsulta.getEstado() == ConsultasConstants.ESTADO_DETALLE_AUTORIZADA || detalleEnOtraConsulta
						.getEstado() == ConsultasConstants.ESTADO_DETALLE_ENTREGADA)) {
					disponibleXReserva = false;
				}
			}
		}

		if (detalleConsultaAComprobar.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			if (StringUtils.isNotEmpty(detalleConsultaAComprobar
					.getExpedienteudoc())) {
				// Si tiene relleno el n�mero de expediente concreto a prestar
				// y coincide con el de alg�n otro detalle
				// de su misma fracci�n de serie, estar� disponible siempre
				// y cuando lo est� por fechas o por tener reserva
				if (detalleConsultaAComprobar.getExpedienteudoc().equals(
						detalleEnOtraConsulta.getExpedienteudoc())) {
					if (!disponibleXReserva || !disponibleXFechas) {
						disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA;
					}
				}
			} else {
				// Si no tiene relleno el n�mero de expediente concreto,
				// estar� parcialmente disponible, puesto que se sabe
				// que existe alg�n otro detalle en el que aparece pero no si
				// el expediente concreto a prestar est� disponible o no
				if (!disponibleXFechas) {
					disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL;
				}
			}
		} else // Si es una unidad documental que no es de subtipo caja
				// (fracci�n de serie)
		{
			// Si alguno de los detalles que existen ya para la unidad que
			// queremos tener en nuestro pr�stamo est� en alguno de los
			// estados
			// siguientes y no se ha cumplido la condici�n anterior
			// (pr�stamo sin reserva autorizado o entregado), se supone que
			// estamos creando
			// un pr�stamo con reserva o bien que el detalle que manejamos
			// puede tener una reserva, por lo que hay que comprobar si por
			// razones de fechas
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
	 * @param detalleConsulta
	 *            Udoc que deseamos comprobar
	 * @param fechaInicial
	 *            Fecha inicial de la consulta.
	 * @param fechaFinal
	 *            Fecha final de la consulta.
	 * @return 0 Si esta disponible 1. Si no esta disponible porque esta
	 *         autorizada o entregada 2. Si no esta disponible porque esta
	 *         reservada 3. Si est� bloqueada 4. Si est� disponible
	 *         parcialmente porque alg�n expediente est� prestado
	 */
	private int isDetalleDisponibleAllConditions(
			DetalleConsultaVO detalleConsulta, Date fechaInicial,
			Date fechaFinal, boolean isReserva) {
		int disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;

		// Comprobar que no est� bloqueada por transferencia entre archivos

		List udocs = uDocEnUiDepositoDbEntity
				.getUDocsVOXId(new String[] { detalleConsulta.getIdudoc() });
		if (!ListUtils.isEmpty(udocs)) {
			for (int i = 0; i < udocs.size(); i++) {
				UDocEnUiDepositoVO udocEnUiDepositoVO = (UDocEnUiDepositoVO) udocs
						.get(i);
				UInsDepositoVO uinsDepositoVO = uInstalacionDepositoDBEntity
						.getUInstDepositoVOXIdEnDeposito(udocEnUiDepositoVO
								.getIduinstalacion());
				if (uinsDepositoVO.getMarcasBloqueo() > 0) {
					// disponible = 3;
					disponible = SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_BLOQUEADA;
					return disponible;
				}
			}
		}

		String[] estados = new String[] {
				new Integer(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA)
						.toString(),
				new Integer(ConsultasConstants.ESTADO_DETALLE_ENTREGADA)
						.toString(),
				new Integer(ConsultasConstants.ESTADO_DETALLE_RESERVADA)
						.toString() };

		Collection detallesRelevantes = detalleConsultaDBEntity.getDetalles(
				detalleConsulta.getIdudoc(),
				detalleConsulta.getSignaturaudoc(),
				DetalleDBEntity.TIPO_DETALLE_CONSULTA, estados);

		Iterator it = detallesRelevantes.iterator();
		while (it.hasNext()) {
			DetalleConsultaVO d = (DetalleConsultaVO) it.next();

			if (!detalleConsulta.getIdsolicitud().equals(d.getIdsolicitud())) {
				disponible = calcularDisponibilidad(detalleConsulta, d,
						fechaInicial, fechaFinal, isReserva);

				if (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA
						|| (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL && StringUtils
								.isEmpty(detalleConsulta.getExpedienteudoc())))
					break; // Rompemos el bucle porque hemos detectado que no
							// est� disponible
			}// detalle!=d
		}// while

		return disponible;
	}

	/*
	 * private int isDetalleDisponibleAllConditions(DetalleConsultaVO
	 * detalleConsulta, Date fechaInicial, Date fechaFinal, boolean isReserva) {
	 * int disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE;
	 * 
	 * // Comprobar que no est� bloqueada por transferencia entre archivos
	 * List udocs = uDocEnUiDepositoDbEntity.getUDocsVOXId(new
	 * String[]{detalleConsulta.getIdudoc()}); if(!ListUtils.isEmpty(udocs)) {
	 * for(int i=0;i<udocs.size();i++) { UDocEnUiDepositoVO
	 * udocEnUiDepositoVO=(UDocEnUiDepositoVO)udocs.get(i); UInsDepositoVO
	 * uinsDepositoVO
	 * =uInstalacionDepositoDBEntity.getUInstDepositoVOXIdEnDeposito
	 * (udocEnUiDepositoVO.getIduinstalacion()); if
	 * (uinsDepositoVO.getMarcasBloqueo()>0) { //disponible = 3; disponible =
	 * SolicitudesConstants
	 * .ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_BLOQUEADA; return
	 * disponible; } } }
	 * 
	 * String [] estados = new String [] {new
	 * Integer(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA).toString() , new
	 * Integer(ConsultasConstants.ESTADO_DETALLE_ENTREGADA).toString() , new
	 * Integer(ConsultasConstants.ESTADO_DETALLE_RESERVADA).toString() };
	 * 
	 * Collection detallesRelevantes = detalleConsultaDBEntity.getDetalles(
	 * detalleConsulta.getIdudoc(), detalleConsulta.getSignaturaudoc(),
	 * DetalleDBEntity.TIPO_DETALLE_CONSULTA, estados);
	 * 
	 * Iterator it = detallesRelevantes.iterator(); while (it.hasNext()) {
	 * DetalleConsultaVO d = (DetalleConsultaVO) it.next();
	 * 
	 * if ( !detalleConsulta.getIdsolicitud().equals(d.getIdsolicitud()) ) { if
	 * (detalleConsulta.getSubtipo() ==
	 * ElementoCuadroClasificacion.SUBTIPO_CAJA) { boolean disponibleXFechas =
	 * true;
	 * 
	 * if (d.getFinicialuso() != null && d.getFfinaluso() != null) {
	 * 
	 * if ( (
	 * DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo(fechaFinal)<=0 &&
	 * DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo(fechaInicial)>=0)
	 * || ( DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo(
	 * fechaInicial)>=0 &&
	 * DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo( fechaFinal )<=0)
	 * || ( DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo(
	 * fechaInicial)<=0 &&
	 * DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo( fechaInicial)>=0)
	 * ) { disponibleXFechas = false; } else { // Si a�n estando en los rangos
	 * de fechas esperados, resulta que la unidad documental sigue entregada, no
	 * est� disponible // Este caso no se contemplaba, que es el que alguien
	 * no devuelve la unidad cuando deber�a haberlo hecho o bien, si bien, hay
	 * // que tener en cuenta que esto s�lo se aplica si coincide que la fecha
	 * de inicio del pr�stamo sea anterior o igual a hoy if
	 * (d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA &&
	 * DateUtils.getFechaActualSinHora
	 * ().compareTo(DateUtils.getFechaSinHora(fechaInicial))>=0)
	 * disponibleXFechas = false; }
	 * 
	 * if (StringUtils.isNotEmpty(detalleConsulta.getExpedienteudoc())) { // Si
	 * tiene relleno el n�mero de expediente concreto a prestar y coincide con
	 * el de alg�n otro detalle // de su misma fracci�n de serie, estar�
	 * disponible siempre y cuando lo est� por fechas if
	 * (detalleConsulta.getExpedienteudoc().equals(d.getExpedienteudoc())) { //
	 * Si la consulta que queremos crear no es con reserva y los detalles que
	 * referencian a la misma unidad // est�n autorizados o entregados, la
	 * unidad no est� disponible if (!isReserva){ if
	 * ((d.getEstado()==ConsultasConstants.ESTADO_DETALLE_AUTORIZADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA) &&
	 * (d.getFinicialuso() != null) && (d.getFfinaluso() != null)) {
	 * //disponible = 1; disponible = SolicitudesConstants.
	 * ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA;
	 * break; } }
	 * 
	 * if (disponibleXFechas) disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE; else
	 * disponible = SolicitudesConstants.
	 * ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA; }
	 * else { // Si tiene relleno el n�mero de expediente concreto a prestar y
	 * no coincide con el de ning�n otro detalle // de su misma fracci�n de
	 * serie, estar� disponible independientemente de las fechas disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE; } } else {
	 * // Si no tiene relleno el n�mero de expediente concreto, estar�
	 * parcialmente disponible, puesto que se sabe // que existe alg�n otro
	 * detalle en el que aparece pero no si el expediente concreto a prestar
	 * est� disponible o no if (!disponibleXFechas) disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL; }
	 * } } else { // Si el pr�stamo que queremos crear no es con reserva y los
	 * detalles que referencian a la misma unidad // est�n autorizados o
	 * entregados, la unidad no est� disponible if (!isReserva){ if
	 * ((d.getEstado()==ConsultasConstants.ESTADO_DETALLE_AUTORIZADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA) &&
	 * (d.getFinicialuso() != null) && (d.getFfinaluso() != null)) {
	 * //disponible = 1; disponible = SolicitudesConstants.
	 * ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA;
	 * break; } }
	 * 
	 * // Si alguno de los detalles que existen ya para la unidad que queremos
	 * tener en nuestro pr�stamo est� en alguno de los estados // siguientes
	 * y no se ha cumplido la condici�n anterior (pr�stamo sin reserva
	 * autorizado o entregado), se supone que estamos creando // un pr�stamo
	 * con reserva o bien que el detalle que manejamos puede tener una reserva,
	 * por lo que hay que comprobar si por razones de fechas // tenemos la
	 * unidad disponible a fecha de uso realmente o no if (
	 * (d.getEstado()==ConsultasConstants.ESTADO_DETALLE_RESERVADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_AUTORIZADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA) &&
	 * (d.getFinicialuso() != null) && (d.getFfinaluso() != null)) { //
	 * Explicaci�n de condiciones: // El detalle est� disponible en fecha de
	 * nuestro pr�stamo siempre y cuando se cumpla que: /* 1. La fecha de
	 * inicio de uso de la unidad no est� entre las fechas de inicio y fin de
	 * nuestro pr�stamo 2. Las fechas de nuestro pr�stamo no contengan por
	 * ambos lados a las fechas de uso de la unidad 3. Las fechas de uso de la
	 * unidad no contengan por ambos lados a las fechas de nuestro pr�stamo
	 * 
	 * if ( (DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo(DateUtils.
	 * getFechaSinHora(fechaFinal))<=0 &&
	 * DateUtils.getFechaSinHora(d.getFinicialuso
	 * ()).compareTo(DateUtils.getFechaSinHora(fechaInicial))>=0 ) ||
	 * (DateUtils.
	 * getFechaSinHora(d.getFinicialuso()).compareTo(DateUtils.getFechaSinHora
	 * (fechaInicial))>=0 &&
	 * DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo
	 * (DateUtils.getFechaSinHora(fechaFinal))<=0 ) ||
	 * (DateUtils.getFechaSinHora
	 * (d.getFinicialuso()).compareTo(DateUtils.getFechaSinHora
	 * (fechaInicial))<=0 &&
	 * DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo
	 * (DateUtils.getFechaSinHora(fechaInicial))>=0) ) { //disponible = 2;
	 * disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_RESERVADA
	 * ; } else { // Si a�n estando en los rangos de fechas esperados, resulta
	 * que la unidad documental sigue entregada, no est� disponible // Este
	 * caso no se contemplaba, que es el que alguien no devuelve la unidad
	 * cuando deber�a haberlo hecho o bien, si bien, hay // que tener en
	 * cuenta que esto s�lo se aplica si coincide que la fecha de inicio del
	 * pr�stamo sea anterior o igual a hoy if
	 * (d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA &&
	 * DateUtils.getFechaActualSinHora
	 * ().compareTo(DateUtils.getFechaSinHora(fechaInicial))>=0)
	 * 
	 * disponible =
	 * SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_RESERVADA
	 * ; } } } }//detalle!=d }//while
	 * 
	 * return disponible; }
	 */

	/**
	 * Comprueba la disponibilidad de una unidad documental
	 * 
	 * @param detalleConsulta
	 *            Udoc que deseamos comprobar
	 * @param fechaInicial
	 *            fecha de inicio de uso de la udoc
	 * @param fechaFinal
	 *            fecha de fin de uso de la udoc
	 * @return true Si esta disponible o false en caso contrario
	 */
	private boolean isDetalleDisponible(DetalleConsultaVO detalleConsulta,
			Date fechaInicial, Date fechaFinal, boolean isReserva) {
		int disponible = isDetalleDisponibleAllConditions(detalleConsulta,
				fechaInicial, fechaFinal, isReserva);

		return (disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE || disponible == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL);
	}

	/**
	 * Compueba la disponibilidad de las unidades documentales seleccionadas de
	 * una determinada consulta, estableciendo su estado de disponiblidad.
	 * 
	 * @param Consulta
	 *            Consulta a la que pertenecen las unidades
	 * @param detalles
	 *            Listado de las unidades documentales a comprobar
	 */
	public boolean comprobarDisponibilidadDetallesConsulta(ConsultaVO consulta,
			Collection detalles) {
		boolean todosDisponibles = true;

		Iterator it = detalles.iterator();

		while (it.hasNext()) {
			DetalleConsultaVO detalle = (DetalleConsultaVO) it.next();

			// Es la entrega de una reserva se comprueban en esas fechas.
			if (consulta.tieneReserva()) {
				// consulta.setFInicialConsulta( consulta.getFinicialreserva()
				// );
				// consulta.setFFinalConsulta( consulta.getFinicialreserva() );

				detalle.setEstadoDisponibilidad(this
						.isDetalleDisponibleAllConditions(detalle,
								consulta.getFinicialreserva(),
								consulta.getFfinalreserva(),
								consulta.tieneReserva()));

				/*
				 * detalle.setDisponibilidad( isDetalleDisponible( detalle,
				 * consulta.getFinicialreserva(), consulta.getFinicialreserva())
				 * );
				 */
			} else {
				Calendar fechafinal = new GregorianCalendar();
				fechafinal.add(
						Calendar.HOUR,
						Integer.parseInt(PropertyHelper
								.getProperty(PropertyHelper.PLAZO_CONSULTA))
								* ConsultasConstants.HORAS_DIA);

				// consulta.setFInicialConsulta( DBUtils.getFechaActual() );
				// consulta.setFFinalConsulta( fechafinal.getTime() );

				detalle.setEstadoDisponibilidad(this
						.isDetalleDisponibleAllConditions(detalle,
								DBUtils.getFechaActual(), fechafinal.getTime(),
								consulta.tieneReserva()));

				/*
				 * detalle.setDisponibilidad( isDetalleDisponible( detalle,
				 * DBUtils.getFechaActual(), fechafinal.getTime()) );
				 */
			}

			detalle.setDisponibilidad(detalle.getEstadoDisponibilidad() == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE
					|| detalle.getEstadoDisponibilidad() == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL);

			if (todosDisponibles && !detalle.isDisponibilidad())
				todosDisponibles = false;

		}

		return todosDisponibles;
	}

	/**
	 * Encapsula la l�gica de finalizaci�n del proceso de autorizaci�n de
	 * una consulta.
	 * 
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos finalizar.
	 */
	public Collection autorizardenegarConsulta(String idConsulta,
			String fentrega) {
		boolean autorizadas = false;
		boolean reservadas = false;
		int denegadas = 0;
		ArrayList udocsNoDisponibles = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		// Realizar comprobaciones de seguridad
		this.check(ServiciosSecurityManager.FINALIZAR_AUTORIZACION_CONSULTA_ACTION);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO);

		// Abrir transaction
		iniciarTransaccion();
		// Obtenemos la consulta
		ConsultaVO consulta = getConsulta(idConsulta);
		if (!consulta.tieneReserva()) {
			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_CONSULTA);

			Date hoy = Calendar.getInstance().getTime();
			Calendar fechafinal = new GregorianCalendar();
			fechafinal.setTime(hoy);
			fechafinal.add(Calendar.HOUR, (Integer.parseInt(dias_reserva))
					* ConsultasConstants.HORAS_DIA);

			consulta.setFInicialConsulta(DateUtils.getFechaActual());
			consulta.setFFinalConsulta(fechafinal.getTime());
			consulta.setFmaxfinconsulta(fechafinal.getTime());
		} else {
			consulta.setFInicialConsulta(consulta.getFinicialreserva());
			consulta.setFFinalConsulta(consulta.getFfinalreserva());
			consulta.setFmaxfinconsulta(consulta.getFfinalreserva());
		}

		// Asignamos a la consulta la fecha de entrega si se ha decidido
		// rellenarla a la hora de autorizar
		if (!StringUtils.isEmpty(fentrega))
			consulta.setFentrega(DateUtils.getDate(fentrega));

		// Obtener detalles prestamo
		Collection detalles = this.getDetallesConsulta(idConsulta);
		Iterator detallesIterator = detalles.iterator();
		while (detallesIterator.hasNext()) {
			DetalleConsultaVO detalle = (DetalleConsultaVO) detallesIterator
					.next();

			// Bloquear detalle
			uDocEnUiDepositoDbEntity.bloquearUDoc(detalle.getIdudoc(),
					detalle.getSignaturaudoc());

			if (detalle.isDenegado()) {
				denegadas++;
			} else {
				// Verificar disponibilidad del detalle
				int disponibilidad = isDetalleDisponibleAllConditions(detalle,
						consulta.getFInicialConsulta(),
						consulta.getFFinalConsulta(), consulta.tieneReserva());

				DataLoggingEvent data = event.getDataLoggingEvent(
						ArchivoObjects.OBJECT_DETALLE_CONSULTA,
						detalle.getIdudoc());
				// data.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				// CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,getServiceSession())
				// );
				data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
						detalle.getSignaturaudoc());

				if (disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE
						|| disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL) {
					detalle.setFinicialuso(consulta.getFInicialConsulta());
					detalle.setFfinaluso(consulta.getFFinalConsulta());
					detalle.setFestado(DateUtils.getFechaActual());

					switch (detalle.getEstado()) {
					case ConsultasConstants.ESTADO_DETALLE_AUTORIZADA:
						autorizadas = true;
						detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA);
						data.addDetalle(
								locale,
								ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_ACEPTADO,
								Messages.getString(
										"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
										locale)
										+ Messages
												.getString(
														"archigest.archivo.solicitudes.detalle.estado.3",
														locale));
						break;
					case ConsultasConstants.ESTADO_DETALLE_RESERVADA:
						reservadas = true;
						detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_RESERVADA);
						data.addDetalle(
								locale,
								ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_RESERVADO,
								Messages.getString(
										"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
										locale)
										+ Messages
												.getString(
														"archigest.archivo.solicitudes.detalle.estado.2",
														locale));
						break;
					case ConsultasConstants.ESTADO_DETALLE_DENEGADA:
						denegadas++;
						data.addDetalle(
								locale,
								ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_DENEGADO,
								Messages.getString(
										"archigest.archivo.solicitudes.detalle.estado.infoUdoc",
										locale)
										+ Messages
												.getString(
														"archigest.archivo.solicitudes.detalle.estado.4",
														locale));
						break;
					/*
					 * case
					 * PrestamosConstants.ESTADO_DETALLE_AUTORIZADA_PARCIAL:
					 * autorizadas = true; detalle.setEstado(PrestamosConstants.
					 * ESTADO_DETALLE_AUTORIZADA_PARCIAL);
					 * data.addDetalle(ArchivoDetails
					 * .SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO ,
					 * Messages.getString
					 * ("archigest.archivo.solicitudes.detalle.estado.infoUdoc")
					 * + Messages.getString(
					 * "archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
					 * )); break;
					 */
					}
				} else {
					detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_DENEGADA);
					detalle.setFestado(DateUtils.getFechaActual());

					switch (disponibilidad) {
					case SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA:
						detalle.setMotivorechazo(Messages
								.getString(
										"archigest.archivo.solicitudes.detalle.estado.nodisponible",
										locale));
						break;
					case SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_RESERVADA:
						detalle.setMotivorechazo(Messages
								.getString(
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
					 * (disponibilidad==2)
					 * detalle.setMotivorechazo("Reservada");
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
					// data.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_DENEGADO,
					// "Udoc " + detalle.getMotivorechazo() );
				}

				// Actualizadmos los detalles
				detalleConsultaDBEntity.actualizarDetalle(detalle);
			}// Fin !detalleConsulta.isDenegado()
		}

		DataLoggingEvent dle = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, consulta.getId());
		dle.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));
		if (denegadas == detalles.size()) {
			consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_DENEGADA);

			// dle.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_DENEGADA,"Consulta Denegada");
			dle.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_CONSULTA_DENEGADA,
					Messages.getString(
							"auditoria.detalles.SOLICITUDES_CONSULTA_DENEGADO",
							locale));
		} else {
			if (autorizadas) {
				consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA);

				// dle.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_ACEPTADA,"Consulta Aceptada");
				dle.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_CONSULTA_ACEPTADA,
						Messages.getString(
								"auditoria.detalles.SOLICITUDES_CONSULTA_ACEPTADO",
								locale));
			}
			if (reservadas) {
				consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_RESERVADA);

				dle.addDetalle(
						locale,
						ArchivoDetails.SOLICITUDES_CONSULTA_RESERVADA,
						Messages.getString(
								"auditoria.detalles.SOLICITUDES_CONSULTA_RESERVADO",
								locale));
				// dle.addDetalle(ArchivoDetails.SOLICITUDES_CONSULTA_RESERVADA,"Consulta Reservada");
			}
		}
		consulta.setFestado(DateUtils.getFechaActual());
		// Actualizamos el prestamo
		consultaDBEntity.updateConsulta(consulta);

		// Terminamos la transaccion
		commit();

		return udocsNoDisponibles;
	}

	/**
	 * Realiza la entrega de una reserva de una consulta.
	 * 
	 * @param idConsulta
	 *            Identificador de la reserva de consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion por parte del usuario
	 */
	public Collection solicitarEntregaReserva(String idConsulta)
			throws ConsultaActionNotAllowedException {
		int denegadas = 0;
		ArrayList udocsNoDisponibles = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		ConditionChecker.checkOnEntregaReserva(idConsulta, this,
				getServiceClient());
		// Realizar comprobaciones de seguridad
		this.check(ServiciosSecurityManager.SOLICITAR_ENTREGA_RESERVA_CONSULTA_ACTION);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA);

		// Abrir transaction
		iniciarTransaccion();
		// Obtenemos el prestamo
		ConsultaVO consulta = getConsulta(idConsulta);

		// Obtener detalles consulta
		Collection detalles = this.getDetallesConsulta(idConsulta);
		Iterator detallesIterator = detalles.iterator();
		while (detallesIterator.hasNext()) {
			DetalleConsultaVO detalle = (DetalleConsultaVO) detallesIterator
					.next();

			// Bloquear detalle
			uDocEnUiDepositoDbEntity.bloquearUDoc(detalle.getIdudoc(),
					detalle.getSignaturaudoc());

			// Verificar disponibilidad del detalle
			// boolean disponibilidad = isDetalleDisponible(
			// detalle,
			// consulta.getFInicialConsulta(),
			// consulta.getFFinalConsulta());

			// Verificar disponibilidad del detalle
			boolean disponibilidad = isDetalleDisponible(detalle,
					DateUtils.getFechaActualSinHora(),
					consulta.getFmaxfinconsulta(), consulta.tieneReserva());

			DataLoggingEvent data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DETALLE_CONSULTA,
							detalle.getIdudoc());
			data.addDetalle(locale, ArchivoDetails.SOLICITUDES_DETALLE_ID,
					detalle.getSignaturaudoc());

			if (disponibilidad) {
				detalle.setFestado(DateUtils.getFechaActual());
				detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA);

				data.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_ACEPTADO,
						"Udoc autorizada");
			} else {
				detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_DENEGADA);
				detalle.setFestado(DateUtils.getFechaActual());
				detalle.setMotivorechazo("No disponible");

				udocsNoDisponibles.add(detalle);
				denegadas++;

				data.addDetalle(locale,
						ArchivoDetails.SOLICITUDES_CONSULTA_DETALLE_DENEGADO,
						"Udoc no disponible");
			}

			// Actualizamos los detalles
			detalleConsultaDBEntity.actualizarDetalle(detalle);
		}

		DataLoggingEvent dle = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CONSULTA, consulta.getId());
		dle.addDetalle(locale, ArchivoDetails.SOLICITUDES_CONSULTA_ID,
				CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(consulta,
						getServiceSession()));

		if (denegadas == detalles.size()) {
			consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_DENEGADA);

			dle.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_CONSULTA_DENEGADA,
					"Consulta Denegada");
		} else {
			consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA);

			dle.addDetalle(locale,
					ArchivoDetails.SOLICITUDES_CONSULTA_ACEPTADA,
					"Consulta Aceptado");
		}

		consulta.setFestado(DateUtils.getFechaActual());
		// Actualizamos la consulta
		consultaDBEntity.updateConsulta(consulta);

		// Terminamos la transaccion
		commit();

		return udocsNoDisponibles;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionConsultasBI#getCountListadoEntregar()
	 */
	public int getCountListadoEntregar(String[] idsArchivo) {
		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_ENTREGAR_ACTION);

		String[] estados = new String[1];

		estados[0] = "" + ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA;

		int consultas = 0;

		consultas = this.getCountConsultasXEstados(estados, idsArchivo);

		return consultas;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionConsultasBI#obtenerListadoEntregar()
	 */
	public List obtenerListadoEntregar(String[] idsArchivo) {
		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.CONSULTAS_ENTREGAR_ACTION);

		List estados = new ArrayList();
		estados.add("" + ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA);

		List consultas = null;

		try {
			consultas = this.getConsultasXEstados(estados, null, idsArchivo);
		} catch (TooManyResultsException e) {
			// Nunca se va a dar esta excepci�n
		}

		return consultas;
	}

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
			ConsultaVO consulta_VO) {
		Collection detallesConsultas = this.getDetallesConsulta(consulta_VO
				.getId());

		// //Si el usuario es de deposito solo vera las autorizadas o
		// entregadas, para entregarlas.
		// if ( user.hasPermission(
		// AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
		//
		// //Ahora, a cada una de ellas le actualizamos la ubicacion
		// Iterator detalles_it = detallesConsultas.iterator() ;
		// DetalleConsultaVO dc;
		// while (detalles_it.hasNext()) {
		// dc = (DetalleConsultaVO) detalles_it.next() ;
		// dc.setUbicacion(this.getUbicacionDetalleConsulta(
		// dc.getIdudoc(), dc.getSignaturaudoc()));
		// }
		// }

		return detallesConsultas;
	}

	public Collection obtenerDetallesSalida(ConsultaVO consulta) {
		Collection detallesConsultas = null;

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();

		// Si el usuario es de deposito solo vera las autorizadas, para
		// entregarlas.
		if (getServiceClient().hasPermission(
				AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
			detallesConsultas = this
					.getDetallesConsultaAutorizadasEntregadas(consulta.getId());
		else
			detallesConsultas = this.getDetallesConsulta(consulta.getId());

		// Para cada detalle , calculamos su ubicacion
		ArrayList detallesFinal = new ArrayList();
		Iterator it = detallesConsultas.iterator();
		while (it.hasNext()) {
			DetalleConsultaVO d = (DetalleConsultaVO) it.next();
			d.setUbicacion(this.getUbicacionDetalleConsulta(d.getIdudoc(),
					d.getSignaturaudoc()));

			// Rellenar la descripci�n
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

			detallesFinal.add(d);
		}

		return detallesConsultas;
	}

	/**
	 * Obtiene informacion adicional sobre la consulta indicada: - Nombre del
	 * archivo. - Datos del usuario creador.
	 * 
	 * @param consulta
	 *            Consulta de la que deseamos obtener la informacion
	 * @return ConsultaVO
	 */
	/*
	 * public ConsultaVO getAditionalConsultaInformation(ConsultaVO consulta) {
	 * ServiceRepository services =
	 * ServiceRepository.getInstance(getServiceSession()); GestionSistemaBI
	 * sistemaBI = services.lookupGestionSistemaBI(); GestionControlUsuariosBI
	 * usuarioBI = services.lookupGestionControlUsuariosBI();
	 * consulta.setArchivo ( sistemaBI.getArchivo( consulta.getIdarchivo() ) );
	 * consulta.setUsuarioGestor( usuarioBI.getUsuario(
	 * consulta.getIdusrsolicitante() ) );
	 * 
	 * return consulta; }
	 */
	/**
	 * Obtiene un listado de usuarios "visibles" en funcion de los permisos del
	 * usuario para filtrar en las busquedas por usuario
	 * 
	 * @return Listado de usuario
	 */
	public Collection getUsuariosBusqueda() {
		return consultaDBEntity.getUsuariosBusqueda();
	}

	/**
	 * Obtiene la lista de detalles de consultas que disponen de una unidad
	 * documental.
	 * 
	 * @param consulta
	 *            Informaci�n de la consulta.
	 * @param detalleConsulta
	 *            Udoc que deseamos comprobar
	 * @return Lista de detalles de consultas.
	 */
	public List getDetallesConsultasNoDisponibles(ConsultaVO consulta,
			DetalleConsultaVO detalleConsulta) {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSolicitudesBI solicitudesBI = services
				.lookupGestionSolicitudesBI();
		List detalles = new ArrayList();

		// Obtener las fechas inicial y final del per�odo del pr�stamo
		Date fechaInicial = null, fechaFinal = null;
		if (consulta.tieneReserva()) {
			fechaInicial = consulta.getFinicialreserva();
			fechaFinal = consulta.getFfinalreserva();
		} else {
			Calendar fechafinal = new GregorianCalendar();
			fechafinal.add(
					Calendar.HOUR,
					Integer.parseInt(PropertyHelper
							.getProperty(PropertyHelper.PLAZO_CONSULTA))
							* ConsultasConstants.HORAS_DIA);

			fechaInicial = DBUtils.getFechaActual();
			fechaFinal = fechafinal.getTime();
		}

		String[] estados = new String[] {
				new Integer(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA)
						.toString(),
				new Integer(ConsultasConstants.ESTADO_DETALLE_ENTREGADA)
						.toString(),
				new Integer(ConsultasConstants.ESTADO_DETALLE_RESERVADA)
						.toString() };

		Collection detallesRelevantes = detalleConsultaDBEntity.getDetalles(
				detalleConsulta.getIdudoc(),
				detalleConsulta.getSignaturaudoc(),
				DetalleDBEntity.TIPO_DETALLE_CONSULTA, estados);

		DetalleUdocNoDisponibleVO detalle;
		DetalleConsultaVO d;

		Iterator it = detallesRelevantes.iterator();
		while (it.hasNext()) {
			d = (DetalleConsultaVO) it.next();

			int disponibilidad = calcularDisponibilidad(detalleConsulta, d,
					fechaInicial, fechaFinal, consulta.tieneReserva());

			boolean esDetalleNoDisponible = disponibilidad == SolicitudesConstants.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE ? false
					: true;

			if (esDetalleNoDisponible) {

				detalle = new DetalleUdocNoDisponibleVO();

				// Informaci�n de la unidad documental en el pr�stamo
				detalle.setIdUdoc(d.getIdudoc());
				detalle.setEstadoUdoc(d.getEstado());
				detalle.setFechaInicialUsoUdoc(d.getFinicialuso());
				detalle.setFechaFinalUsoUdoc(d.getFfinaluso());

				switch (d.getTiposolicitud()) {
				case ConsultasConstants.TIPO_SOLICITUD_CONSULTA:
					ConsultaVO c = getConsulta(d.getIdsolicitud());
					if (c != null) {
						// Obtener info extra de la consulta
						c = (ConsultaVO) solicitudesBI
								.getAditionalSolicitudInformation(c);
						detalle.setSolicitud(c);
						detalle.setSolicitanteSolicitud(c.getNusrconsultor());
						detalle.setObservaciones(d.getObservaciones());
					}
					break;
				case PrestamosConstants.TIPO_SOLICITUD_PRESTAMO:
					PrestamoVO p = getGestionPrestamosBI().getPrestamo(
							d.getIdsolicitud());
					if (p != null) {
						// Obtener info extra de la consulta
						p = (PrestamoVO) solicitudesBI
								.getAditionalSolicitudInformation(p);
						detalle.setSolicitud(p);
						detalle.setSolicitanteSolicitud(p.getNusrsolicitante());
						detalle.setObservaciones(d.getObservaciones());
					}
					break;
				}

				detalles.add(detalle);
			}
		} // while

		return detalles;
	}

	/*
	 * public List getDetallesConsultasNoDisponibles(ConsultaVO consulta,
	 * DetalleConsultaVO detalleConsulta) {
	 * 
	 * ServiceRepository services =
	 * ServiceRepository.getInstance(getServiceSession()); GestionSolicitudesBI
	 * solicitudesBI = services.lookupGestionSolicitudesBI(); List detalles =
	 * new ArrayList();
	 * 
	 * // Obtener las fechas inicial y final del per�odo del pr�stamo Date
	 * fechaInicial = null, fechaFinal = null; if ( consulta.tieneReserva() ) {
	 * fechaInicial = consulta.getFinicialreserva(); fechaFinal =
	 * consulta.getFfinalreserva(); } else { Calendar fechafinal = new
	 * GregorianCalendar(); fechafinal.add(Calendar.HOUR,
	 * Integer.parseInt(PropertyHelper.getProperty( //
	 * PropertyHelper.PLAZO_PRESTAMO)) PropertyHelper.PLAZO_CONSULTA))
	 * ConsultasConstants.HORAS_DIA);
	 * 
	 * fechaInicial = DBUtils.getFechaActual(); fechaFinal =
	 * fechafinal.getTime(); }
	 * 
	 * Collection todosDetalles = detalleConsultaDBEntity.getDetalles(
	 * detalleConsulta.getIdudoc(), detalleConsulta.getSignaturaudoc(),
	 * DetalleDBEntity.TIPO_DETALLE_CONSULTA); DetalleUdocNoDisponibleVO
	 * detalle; DetalleConsultaVO d;
	 * 
	 * Iterator it = todosDetalles.iterator(); while (it.hasNext()) { boolean
	 * esDetalleNoDisponible = false;
	 * 
	 * d = (DetalleConsultaVO) it.next();
	 * 
	 * if ( !detalleConsulta.getIdsolicitud().equals(d.getIdsolicitud()) &&
	 * (d.getEstado()==ConsultasConstants.ESTADO_DETALLE_RESERVADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_AUTORIZADA ||
	 * d.getEstado()==ConsultasConstants.ESTADO_DETALLE_ENTREGADA) &&
	 * (d.getFinicialuso() != null && d.getFfinaluso() != null)) { if (
	 * (DateUtils
	 * .getFechaSinHora(d.getFinicialuso()).compareTo(DateUtils.getFechaSinHora
	 * (fechaFinal))<=0 &&
	 * DateUtils.getFechaSinHora(d.getFinicialuso()).compareTo
	 * (DateUtils.getFechaSinHora(fechaInicial))>=0) ||
	 * (DateUtils.getFechaSinHora
	 * (d.getFinicialuso()).compareTo(DateUtils.getFechaSinHora
	 * (fechaInicial))>=0 &&
	 * DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo
	 * (DateUtils.getFechaSinHora(fechaFinal))<=0) ||
	 * (DateUtils.getFechaSinHora(
	 * d.getFinicialuso()).compareTo(DateUtils.getFechaSinHora(fechaInicial))<=0
	 * && DateUtils.getFechaSinHora(d.getFfinaluso()).compareTo(DateUtils.
	 * getFechaSinHora(fechaInicial))>=0) ) { esDetalleNoDisponible = true; }
	 * else if (d.getEstado() == ConsultasConstants.ESTADO_DETALLE_ENTREGADA &&
	 * DateUtils
	 * .getFechaActualSinHora().compareTo(DateUtils.getFechaSinHora(fechaInicial
	 * ))>=0) esDetalleNoDisponible = true; }
	 * 
	 * if (esDetalleNoDisponible) {
	 * 
	 * detalle = new DetalleUdocNoDisponibleVO();
	 * 
	 * // Informaci�n de la unidad documental en el pr�stamo
	 * detalle.setIdUdoc(d.getIdudoc()); detalle.setEstadoUdoc(d.getEstado());
	 * detalle.setFechaInicialUsoUdoc(d.getFinicialuso());
	 * detalle.setFechaFinalUsoUdoc(d.getFfinaluso());
	 * 
	 * switch (d.getTiposolicitud()) { case
	 * ConsultasConstants.TIPO_SOLICITUD_CONSULTA: ConsultaVO c =
	 * getConsulta(d.getIdsolicitud()); if (c != null) { // Obtener info extra
	 * de la consulta c =
	 * (ConsultaVO)solicitudesBI.getAditionalSolicitudInformation(c);
	 * detalle.setSolicitud(c);
	 * detalle.setSolicitanteSolicitud(c.getNusrconsultor());
	 * detalle.setObservaciones(d.getObservaciones()); } break; case
	 * PrestamosConstants.TIPO_SOLICITUD_PRESTAMO: PrestamoVO p =
	 * getGestionPrestamosBI() .getPrestamo(d.getIdsolicitud()); if (p != null)
	 * { // Obtener info extra de la consulta p =
	 * (PrestamoVO)solicitudesBI.getAditionalSolicitudInformation(p);
	 * detalle.setSolicitud(p);
	 * detalle.setSolicitanteSolicitud(p.getNusrsolicitante());
	 * detalle.setObservaciones(d.getObservaciones()); } break; }
	 * 
	 * detalles.add(detalle); } } // while
	 * 
	 * return detalles; }
	 */
	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionConsultasBI#getNumDetalles(java.lang.String)
	 */
	public int getNumDetalles(String idSolicitud) {
		return detalleConsultaDBEntity.getNumDetalles(idSolicitud);
	}

	public void actualizarDetalleConsulta(DetalleVO detalle) {
		detalleConsultaDBEntity.actualizarDetalle(detalle);
	}

	public void actualizarObservaciones(String idSolicitud, String observaciones) {
		consultaDBEntity.updateObservaciones(idSolicitud, observaciones);
	}

	/* Para los Motivos de consulta */
	public void insertarMotivoConsulta(MotivoConsultaVO motivoVO) {
		motivoDBEntity.insertarMotivoConsulta(motivoVO);
	}

	public MotivoConsultaVO getMotivoConsulta(MotivoConsultaVO motivoVO) {
		return motivoDBEntity.getMotivoConsulta(motivoVO);
	}

	public List getMotivosConsulta() {
		return motivoDBEntity.getMotivosConsulta();
	}

	public MotivoConsultaVO getMotivoConsultaById(String idMotivo) {
		return motivoDBEntity.getMotivoConsultaById(idMotivo);
	}

	public int getCountConsultaByIdMotivo(String idMotivo) {
		return consultaDBEntity.getCountConsultasByIdMotivo(idMotivo);
	}

	public void deleteMotivo(MotivoConsultaVO motivoVO) {
		motivoDBEntity.deleteMotivoConsulta(motivoVO);
	}

	public void actualizarMotivo(MotivoConsultaVO motivoVO) {
		motivoDBEntity.actualizarMotivoConsulta(motivoVO);
	}

	public UDocEnUiDepositoVO getDescripcionUdocDeposito(String idudoc,
			String signatura) {
		return uDocEnUiDepositoDbEntity.getUDocByIdAndSignatura(idudoc,
				signatura);
	}

	public List getConsultasAniadirUDocsFromBusqueda(String idArchivo,
			String idUsuario) {

		String[] estados = new String[] { String
				.valueOf(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO) };
		return consultaDBEntity.getConsultasByIdarchivoAndIdUsuarioSolicitante(
				idArchivo, idUsuario, estados);
	}

	public void insertarDetallesAConsulta(ConsultaVO vo, ServiceClient user)
			throws ConsultaActionNotAllowedException {
		// Comprobamos los permisos
		this.check(ServiciosSecurityManager.ANIADIR_UDOC_PRESTAMO_ACTION);
		// Comprobamos otras condiciones
		ConditionChecker.checkOnConsultaEdit(vo, user);

		iniciarTransaccion();
		List detallesConsulta = vo.getDetallesConsulta();
		if (ListUtils.isNotEmpty(detallesConsulta)) {

			for (Iterator iterator = detallesConsulta.iterator(); iterator
					.hasNext();) {
				DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) iterator
						.next();

				detalleConsulta.setIdsolicitud(vo.getId());

				if (!detalleConsultaDBEntity.existeDetalle(
						DetalleDBEntity.TIPO_DETALLE_CONSULTA,
						detalleConsulta.getIdsolicitud(),
						detalleConsulta.getIdudoc(),
						detalleConsulta.getSignaturaudoc())) {
					nuevoDetallesConsulta(detalleConsulta);
				}
			}
		}
		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionConsultasBI#getTemasCiudadano()
	 */
	public List getTemasCiudadano() {
		return temaDBEntity
				.getTemasTipoEntidad(TemaDBEntity.TIPO_ENTIDAD_CIUDADANO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionConsultasBI#getTemasOrgano()
	 */
	public List getTemasOrgano() {
		return temaDBEntity
				.getTemasTipoEntidad(TemaDBEntity.TIPO_ENTIDAD_ORGANO_EXTERNO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionConsultasBI#getMotivosUsuarioConsultaEnSala()
	 */
	public List getMotivosUsuarioConsultaEnSala(AppUser appUser,
			String idUsuario) {
		List listaMotivos = new ArrayList();

		if (StringUtils.isNotEmpty(idUsuario)) {

			Integer tipoConsulta = new Integer(
					ConsultasConstants.TIPO_CONSULTA_INDIRECTA);

			Integer visibilidad = new Integer(
					SolicitudesConstants.VISIBILIDAD_NO_ARCHIVO);

			// Comprobar si es directa o indirecta
			UsuarioSalasConsultaVO usuarioSalaConsulta = appUser
					.getUsuarioSalasConsultaVO();
			if (usuarioSalaConsulta != null && idUsuario != null
					&& idUsuario.equals(usuarioSalaConsulta.getId())) {

				tipoConsulta = new Integer(
						ConsultasConstants.TIPO_CONSULTA_DIRECTA);
			}

			// Obtener los archivos a los que pertenece el usuario
			if (appUser.isPersonalArchivo()) {
				visibilidad = new Integer(
						SolicitudesConstants.VISIBILIDAD_ARCHIVO);
			}

			Integer[] visibilidades = new Integer[] { visibilidad,
					new Integer(SolicitudesConstants.VISIBILIDAD_AMBOS) };

			listaMotivos = motivoDBEntity
					.getMotivosConsulta(
							new Integer(
									ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT),
							tipoConsulta, visibilidades);

		}

		return listaMotivos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionConsultasBI#getMotivosUsuarioInvestigador(java.lang.String,
	 *      java.lang.String)
	 */
	public List getMotivosUsuarioInvestigador(AppUser appUser, String idUsuario) {

		List listaMotivos = new ArrayList();

		if (StringUtils.isNotEmpty(idUsuario)) {

			Integer tipoConsulta = new Integer(
					ConsultasConstants.TIPO_CONSULTA_INDIRECTA);

			Integer visibilidad = new Integer(
					SolicitudesConstants.VISIBILIDAD_NO_ARCHIVO);

			// Comprobar si es directa o indirecta
			if (idUsuario.equals(appUser.getId())) {
				tipoConsulta = new Integer(
						ConsultasConstants.TIPO_CONSULTA_DIRECTA);
			}

			// Obtener los archivos a los que pertenece el usuario
			if (appUser.isPersonalArchivo()) {
				visibilidad = new Integer(
						SolicitudesConstants.VISIBILIDAD_ARCHIVO);
			}

			Integer[] visibilidades = new Integer[] { visibilidad,
					new Integer(SolicitudesConstants.VISIBILIDAD_AMBOS) };

			listaMotivos = motivoDBEntity
					.getMotivosConsulta(
							new Integer(
									ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT),
							tipoConsulta, visibilidades);

		}

		return listaMotivos;
	}
}