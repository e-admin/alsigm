package transferencias.model;

import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.model.EstadoSerie;
import fondos.vos.FondoVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.IProcedimiento;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.db.IDetallePrevisionDBEntity;
import transferencias.db.INSecTransferenciasDBEntity;
import transferencias.db.IPrevisionDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.exceptions.PrevisionOperacionNoPermitidaException;
import transferencias.model.AuditoriaTransferencias.PistaAuditoriaTransferencias;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.TransferenciaElectronicaInfo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.LoggingEvent;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.SecurityException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;
import common.exceptions.UncheckedArchivoException;
import common.security.TransferenciasSecurityManager;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.VO2IDTransformer;
import common.vos.TypeDescVO;

/**
 * Implementacion de los metodos del servicio de gestion de previsiones
 */
public final class GestionPrevisionesBIImpl extends ServiceBase implements
		GestionPrevisionesBI {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(GestionPrevisionesBIImpl.class);

	private class PrevisionAuthorizationHelper {
		int errorCode = -1;

		/**
		 * Establece que acciones estan permitidas y cuales nos para una
		 * prevision
		 *
		 * @param prevision
		 *            Prevision a configurar
		 */
		private boolean configurePrevision(PrevisionVO prevision) {
			/*
			 * Determinar si el fondo contra el que se hace la transferencia
			 * puede ser modificado por parte del usuario que esta elaborando la
			 * prevision
			 */
			prevision
					.setFondoCanBeChanged(permitidaModificacionFondo(prevision));

			/*
			 * Determinar si el fondo contra el que se hace la transferencia
			 * puede ser modificado por parte del usuario que esta elaborando la
			 * prevision
			 */
			prevision
					.setFondoCanBeChanged(permitidaModificacionFondo(prevision));

			/*
			 * Determinar si el archivo remitente contra el que se hace la
			 * transferencia puede ser modificado por parte del usuario que esta
			 * elaborando la prevision
			 */
			prevision
					.setArchivoRemitenteCanBeChanged(permitidaModificacionArchivoRemitente(prevision));

			/*
			 * Determinar si el archivo receptor contra el que se hace la
			 * transferencia puede ser modificado por parte del usuario que esta
			 * elaborando la prevision
			 */
			prevision
					.setArchivoReceptorCanBeChanged(permitidaModificacionArchivoReceptor(prevision));

			/*
			 * Verificar si la prevision puede ser enviada
			 */
			prevision.setPuedeSerEnviada(puedeSerEnviada(prevision));

			/*
			 * Verificar si la prevision puede ser eliminada
			 */
			prevision.setPuedeSerEliminada(puedeSerEliminada(prevision));

			/*
			 * Verificar si la prevision puede ser editada en el organo
			 * remitente
			 */
			prevision.setPuedeSerEditada(puedeSerEditada(prevision));

			/*
			 * Verificar si la prevision puede ser recibida
			 */
			prevision.setPuedeSerAceptada(puedeSerAceptada(prevision));

			/*
			 * Verificar si se permite la creacion de relaciones para la
			 * prevision
			 */
			prevision.setAceptaRelaciones(aceptaRelaciones(prevision));

			/*
			 * Verificar si se permite la modificacion de la prevision en el
			 * archivo receptor
			 */
			prevision.setPuedeSerModificada(puedeSerModificada(prevision));

			/*
			 * Verificar si se permite la incorporacion de lineas de detalle
			 * sobre cualquier procedimiento
			 */
			prevision
					.setPermitidaSeleccionProcedimiento(permitidaSeleccionProcedimiento(prevision));

			return errorCode != -1;
		}

		/**
		 * Comprueba si se pueden añadir a la previsión lineas de detalle para
		 * cualquier procedimiento
		 *
		 * @param prevision
		 *            Datos de la prevision de transferencia a comprobar
		 * @return true en caso de que sea posible elegir entre todos los
		 *         procedimientos y false en caso de que la prevision solo
		 *         admita lineas de detalle sobre un conjunto determinado de
		 *         procedimientos
		 */
		boolean permitidaSeleccionProcedimiento(PrevisionVO prevision) {
			boolean permitidaSeleccionProcedimiento = false;
			int tipoTransferencia = prevision.getTipotransferencia();
			if (tipoTransferencia != TipoTransferencia.ORDINARIA
					.getIdentificador()) {
				if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
						.getIdentificador() && prevision.isDetallada())
					permitidaSeleccionProcedimiento = getServiceClient()
							.hasPermission(
									AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)
							|| getServiceClient()
									.hasPermission(
											AppPermissions.ADMINISTRACION_TOTAL_SISTEMA);
				else
					permitidaSeleccionProcedimiento = true;
			}
			return permitidaSeleccionProcedimiento;
		}

		/**
		 * Comprueba si es posible la creacion de una prevision de transferencia
		 *
		 * @param prevision
		 *            Datos de la prevision de transferencia a comprobar
		 * @return true en caso de que la prevision pueda ser creada false si la
		 *         creacion de la prevision no se pueda llevar a cabo. En este
		 *         caso el valor de errorCode indicara el motivo por el que la
		 *         creacion de la prevision no esta permitida
		 */
		private boolean permitidaCreacionPrevision(PrevisionVO prevision) {
			boolean permitidaCreacionPrevision = true;
			int[] estados = { EstadoPrevision.ABIERTA.getIdentificador(),
					EstadoPrevision.ENVIADA.getIdentificador(),
					EstadoPrevision.RECHAZADA.getIdentificador() };
			int tipoTransferencia = prevision.getTipotransferencia();

			if (tipoTransferencia == TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador()) {
				// Comprobar que no haya más de una previsión entre archivos en
				// estado abierta para el mismo gestor
				Collection otrasPrevisionesUsuarioGestor = _previsionDbEntity
						.getPrevisionesXGestorYTipoTrans(prevision
								.getIdusrgestor(), tipoTransferencia,
								new int[] { EstadoPrevision.ABIERTA
										.getIdentificador() }, prevision
										.getAno());
				if (otrasPrevisionesUsuarioGestor != null
						&& otrasPrevisionesUsuarioGestor.size() > 0) {
					errorCode = ArchivoErrorCodes.PREVISION_ENTRE_ARCHIVOS_EN_CURSO_EN_USUARIO_REMITENTE;
					permitidaCreacionPrevision = false;
				}
			} else {
				// No puede haber más de una previsión del mismo tipo abierta si
				// no se dispone del permiso ampliado de transferencia
				// ordinaria / extraordinaria respectivamente
				String neededPermision = null;
				if (tipoTransferencia == TipoTransferencia.ORDINARIA
						.getIdentificador())
					neededPermision = AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS;
				else // Si es extraordinaria
				if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
						.getIdentificador() && prevision.isDetallada())
					neededPermision = AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS;

				if (neededPermision != null) {
					Collection otrasPrevisionesEnOrganoRemitente = _previsionDbEntity
							.getPrevisionesXIdOrgRemitenteYTTransfYTPrevYEstados(
									prevision.getIdorgremitente(),
									tipoTransferencia, estados);
					if (otrasPrevisionesEnOrganoRemitente != null
							&& otrasPrevisionesEnOrganoRemitente.size() > 0)
						if (!getServiceClient().hasPermission(neededPermision)) {
							errorCode = ArchivoErrorCodes.PREVISION_EN_CURSO_EN_ORGANO_REMITENTE;
							permitidaCreacionPrevision = false;
						}
				}
			}
			return permitidaCreacionPrevision;
		}

		/**
		 * Comprueba si el fondo asociado a una previsión puede ser modificado
		 * por el usuario
		 *
		 * @param prevision
		 *            Previsión de transferencia
		 * @return true si se permite cambiar el fondo y false en caso de que no
		 */
		private boolean permitidaModificacionFondo(PrevisionVO prevision) {
			boolean permitidaModificacionFondo = false;
			ServiceClient serviceClient = getServiceClient();
			if (serviceClient.getId().equals(prevision.getIdusrgestor())
					&& prevision.getTipotransferencia() != TipoTransferencia.ORDINARIA
							.getIdentificador()) {
				if (prevision.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
						.getIdentificador())
					permitidaModificacionFondo = true;
				else if (prevision.getTipoprevision() == PrevisionVO.PREVISION_DETALLADA) {
					if (prevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
						if (serviceClient
								.hasPermission(AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)) {
							Collection lineasDeDetalle = getDetallesPrevision(prevision
									.getId());
							if (lineasDeDetalle == null
									|| lineasDeDetalle.size() == 0)
								permitidaModificacionFondo = true;
						}
					} else {
						if (serviceClient
								.hasPermission(AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS)) {
							Collection lineasDeDetalle = getDetallesPrevision(prevision
									.getId());
							if (lineasDeDetalle == null
									|| lineasDeDetalle.size() == 0)
								permitidaModificacionFondo = true;
						}
					}
				} else
					permitidaModificacionFondo = true;
			}
			return permitidaModificacionFondo;
		}

		/**
		 * Comprueba si el archivo receptor asociado a una previsión puede ser
		 * modificado por el usuario
		 *
		 * @param prevision
		 *            Previsión de transferencia
		 * @return true si se permite cambiar el archivo receptor y false en
		 *         caso de que no
		 */
		private boolean permitidaModificacionArchivoReceptor(
				PrevisionVO prevision) {
			boolean permitidaModificacionArchivoReceptor = false;
			ServiceClient serviceClient = getServiceClient();
			if (serviceClient.getId().equals(prevision.getIdusrgestor())
					&& prevision.getTipotransferencia() != TipoTransferencia.ORDINARIA
							.getIdentificador()) {
				if (prevision.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
						.getIdentificador())
					permitidaModificacionArchivoReceptor = true;
				else if (prevision.getTipoprevision() == PrevisionVO.PREVISION_DETALLADA) {
					if (prevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
						if (serviceClient
								.hasPermission(AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)) {
							Collection lineasDeDetalle = getDetallesPrevision(prevision
									.getId());
							if (lineasDeDetalle == null
									|| lineasDeDetalle.size() == 0)
								permitidaModificacionArchivoReceptor = true;
						}
					} else {
						if (serviceClient
								.hasPermission(AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS)) {
							Collection lineasDeDetalle = getDetallesPrevision(prevision
									.getId());
							if (lineasDeDetalle == null
									|| lineasDeDetalle.size() == 0)
								permitidaModificacionArchivoReceptor = true;
						}
					}
				} else
					permitidaModificacionArchivoReceptor = true;
			}
			return permitidaModificacionArchivoReceptor;
		}

		/**
		 * Comprueba si el archivo remitente asociado a una previsión puede ser
		 * modificado por el usuario
		 *
		 * @param prevision
		 *            Previsión de transferencia
		 * @return true si se permite cambiar el archivo receptor y false en
		 *         caso de que no
		 */
		private boolean permitidaModificacionArchivoRemitente(
				PrevisionVO prevision) {
			boolean permitidaModificacionArchivoRemitente = false;
			ServiceClient serviceClient = getServiceClient();
			if (serviceClient.getId().equals(prevision.getIdusrgestor())
					&& prevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
				if (serviceClient
						.hasPermission(AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS)) {
					Collection lineasDeDetalle = getDetallesPrevision(prevision
							.getId());
					if (lineasDeDetalle == null || lineasDeDetalle.size() == 0)
						permitidaModificacionArchivoRemitente = true;
				}
			}
			return permitidaModificacionArchivoRemitente;
		}

		/**
		 * Comprueba si una prevision puede ser enviada
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si la prevision puede ser enviada y false en caso de que
		 *         no
		 */
		private boolean puedeSerEnviada(PrevisionVO prevision) {
			boolean puedeSerEnviada = false;
			if (prevision.getEstado() == EstadoPrevision.ABIERTA
					.getIdentificador()) {
				if (prevision.isDetallada()) {
					if (numeroDetallesPrevision(prevision.getId()) > 0)
						puedeSerEnviada = true;
					else
						errorCode = ArchivoErrorCodes.PREVISION_DETALLADA_SIN_DETALLES;
				} else
					puedeSerEnviada = true;
			} else
				errorCode = ArchivoErrorCodes.PREVISION_NO_ABIERTA;
			return puedeSerEnviada;
		}

		/**
		 * Comprueba si una prevision puede ser eliminada
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si la prevision puede ser eliminada y false en caso de
		 *         que no
		 */
		private boolean puedeSerEliminada(PrevisionVO prevision) {
			boolean puedeSerEliminada = false;

			if (prevision.getEstado() == (EstadoPrevision.ABIERTA
					.getIdentificador())
					|| prevision.getEstado() == (EstadoPrevision.RECHAZADA
							.getIdentificador()))
				puedeSerEliminada = true;
			else if (prevision.getEstado() == (EstadoPrevision.ACEPTADA
					.getIdentificador()))
				puedeSerEliminada = prevision.isCaducada();

			ServiceClient client = getServiceClient();
			if (!client.getId().equals(prevision.getIdusrgestor())) {
				puedeSerEliminada = false;
			}

			boolean tieneRelacionesAsociadas = _relacionDBEntity
					.previsionTieneRelaciones(prevision.getId());

			return puedeSerEliminada && !tieneRelacionesAsociadas;
		}

		/**
		 * Comprueba si una prevision puede ser editada
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si la prevision puede ser editada y false en caso de que
		 *         no
		 */
		private boolean puedeSerEditada(PrevisionVO prevision) {
			boolean puedeSerModificada = false;
			if (prevision.getEstado() == EstadoPrevision.ABIERTA
					.getIdentificador()
					|| prevision.getEstado() == EstadoPrevision.RECHAZADA
							.getIdentificador())
				if (prevision.getIdusrgestor().equals(
						getServiceClient().getId()))
					puedeSerModificada = true;
			return puedeSerModificada;
		}

		/**
		 * Comprueba si una prevision puede ser recibida
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si la prevision puede ser recibida y false en caso de
		 *         que no
		 */
		private boolean puedeSerAceptada(PrevisionVO prevision) {
			boolean puedeSerRecibida = false;
			if (prevision.getEstado() == EstadoPrevision.ENVIADA
					.getIdentificador())
				puedeSerRecibida = true;
			return puedeSerRecibida;
		}

		/**
		 * Comprueba si una prevision acepta la creacion de previsiones
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si se pueden crear relaciones sobre la prevision y false
		 *         en caso de que no
		 */
		private boolean aceptaRelaciones(PrevisionVO prevision) {
			boolean aceptaRelaciones = false;
			/*
			 * Para poder crear relaciones sobre una prevision la prevision debe
			 * estar aceptada y no puede haber caducado
			 */
			if (prevision.getEstado() == EstadoPrevision.ACEPTADA
					.getIdentificador() && !prevision.isCaducada())
				/*
				 * el usuario que pretende crear la relacion debe ser el gestor
				 * de la prevision
				 */
				if (prevision.getIdusrgestor().equals(
						getServiceClient().getId())) {
					Date currentDate = DateUtils.getFechaActualSinHora();
					/*
					 * la fecha actual debe estar comprendido dentro del rango
					 * de fechas del calendario asignado a la prevision
					 */

					/*
					 * if (!currentDate.before(prevision.getFechainitrans()) &&
					 * !currentDate.after(prevision.getFechafintrans()))
					 * aceptaRelaciones = true;
					 */

					if (DateUtils.isFechaMayorOIgual(currentDate,
							prevision.getFechainitrans())
							&& DateUtils.isFechaMenorOIgual(currentDate,
									prevision.getFechafintrans())) {
						aceptaRelaciones = true;

					}

				}
			return aceptaRelaciones;
		}

		/**
		 * Comprueba si una prevision puede ser modificada
		 *
		 * @param prevision
		 *            Prevision de transferencia
		 * @return true si la prevision puede ser editada y false en caso de que
		 *         no
		 */
		private boolean puedeSerModificada(PrevisionVO prevision) {
			boolean puedeSerModificada = false;
			if (prevision.getEstado() == EstadoPrevision.ACEPTADA
					.getIdentificador())
				puedeSerModificada = true;
			return puedeSerModificada;
		}

		private int getErrorCode() {
			return errorCode;
		}
	}

	private class DetallePrevisionAuthorizationHelper {
		int errorCode = -1;

		/**
		 * Determina las acciones que pueden ser llevadas a cabo sobre un
		 * detalle de prevision
		 *
		 * @param detallePrevision
		 *            Detalle de prevision a configurar
		 */
		void configureDetallePrevision(PrevisionVO prevision,
				DetallePrevisionVO detallePrevision) {
			detallePrevision.setPuedeSerEliminado(puedeSerEliminado(prevision,
					detallePrevision));
			detallePrevision.setPuedeSerEditado(puedeSerModificado(prevision,
					detallePrevision));
		}

		boolean puedeSerEliminado(PrevisionVO prevision,
				DetallePrevisionVO detallePrevision) {
			boolean puedeSerEliminado = false;
			if (prevision.getEstado() == EstadoPrevision.ABIERTA
					.getIdentificador()
					|| prevision.getEstado() == EstadoPrevision.RECHAZADA
							.getIdentificador())
				puedeSerEliminado = true;
			else
				errorCode = ArchivoErrorCodes.PREVISION_NO_EN_ESTADO_ADECUADO;
			return puedeSerEliminado;
		}

		boolean puedeSerModificado(PrevisionVO prevision,
				DetallePrevisionVO detallePrevision) {
			boolean puedeSerModificado = false;
			// TODO AQUI CREO QUE HABRIA QUE COMPROBAR TAMBIEN SI EL DETALLE
			// TIENE RELACIONES ASOCIADAS
			if (prevision.getEstado() == EstadoPrevision.ABIERTA
					.getIdentificador()
					|| prevision.getEstado() == EstadoPrevision.RECHAZADA
							.getIdentificador())
				puedeSerModificado = true;
			return puedeSerModificado;
		}

		int getErrorCode() {
			return errorCode;
		}
	}

	IPrevisionDBEntity _previsionDbEntity = null;
	IArchivoDbEntity _archivoDbEntity = null;
	IDetallePrevisionDBEntity _detallePrevisionDBEntity = null;
	INSecTransferenciasDBEntity _nSecDBEntity = null;
	IRelacionEntregaDBEntity _relacionDBEntity = null;
	IProductorSerieDbEntity _productorSerieDBEnitity = null;
	IOrganoProductorDbEntity _organoProductorDBEntity = null;
	ISerieDbEntity _serieDBEntity = null;

	public GestionPrevisionesBIImpl(IPrevisionDBEntity previsionDBEntity,
			IArchivoDbEntity archivoDBEntity,
			INSecTransferenciasDBEntity nSecDBEntity,
			IDetallePrevisionDBEntity detallePrevisionDBEntity,
			IRelacionEntregaDBEntity relacionDBEntity,
			IProductorSerieDbEntity productorSerieDBEnitity,
			IOrganoProductorDbEntity organoProductorDBEntity,
			ISerieDbEntity serieDBEntity) {
		this._previsionDbEntity = previsionDBEntity;
		this._archivoDbEntity = archivoDBEntity;
		this._nSecDBEntity = nSecDBEntity;
		this._detallePrevisionDBEntity = detallePrevisionDBEntity;
		this._relacionDBEntity = relacionDBEntity;
		this._productorSerieDBEnitity = productorSerieDBEnitity;
		this._organoProductorDBEntity = organoProductorDBEntity;
		this._serieDBEntity = serieDBEntity;
	}

	private PrevisionAuthorizationHelper getPrevisionAuthorizationHelper() {
		return new PrevisionAuthorizationHelper();
	}

	private DetallePrevisionAuthorizationHelper getDetallePrevisionAuthorizationHelper() {
		return new DetallePrevisionAuthorizationHelper();
	}

	/**
	 * Creación de prevision de transferencia
	 *
	 * @param previsionVO
	 *            Datos de prevision de transferencia
	 * @throws ActionNotAllowedException
	 *             Caso de que el alta de la prevision no esté permitida
	 */
	public void insertPrevision(PrevisionVO previsionVO)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA,
						this);
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA);
		int tipoTransferencia = previsionVO.getTipotransferencia();
		if (tipoTransferencia == TipoTransferencia.ORDINARIA.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_ORDINARIA);
		else if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador()
				|| tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
						.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_EXTRAORDINARIA);
		else if (tipoTransferencia == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_ENTRE_ARCHIVOS);
		else
			throw new ArchivoModelException(getClass(), "nuevaPrevision",
					"Tipo de transferencia desconocido: " + tipoTransferencia);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.permitidaCreacionPrevision(previsionVO)) {
			iniciarTransaccion();
			int numSecuencia = _nSecDBEntity.incrementarNumeroSecPrevision(
					previsionVO.getAno(), previsionVO.getIdarchivoreceptor());
			previsionVO.setOrden(numSecuencia);
			previsionVO.setFechaestado(DateUtils.getFechaActual());
			_previsionDbEntity.insertarPrevision(previsionVO);

			Locale locale = getServiceClient().getLocale();
			pistaAuditoria.auditaCreacionPrevision(locale, previsionVO, this);
			// DataLoggingEvent logData =
			// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
			// previsionVO.getId());
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
			// CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(previsionVO,getServiceSession()));
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_TRANSFERENCIA
			// ,
			// TipoTransferencia.getTipoTransferencia(tipoTransferencia).getNombre()
			// );
			commit();
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Pone una prevision a disposicion del usuario que solicita la apertura de
	 * manera que unicamente dicho usuario puede realizar acciones sobre la
	 * prevision
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO abrirPrevision(String idPrevision) {
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent logData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION, idPrevision);
		PrevisionVO prevision = _previsionDbEntity
				.getInfoPrevision(idPrevision);
		getPrevisionAuthorizationHelper().configurePrevision(prevision);
		return prevision;
	}

	/**
	 * Obtiene la informacion correspondiente a una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO getPrevision(String idPrevision) {
		PrevisionVO prevision = _previsionDbEntity
				.getInfoPrevision(idPrevision);
		if (prevision == null)
			throw new UncheckedArchivoException(
					"Prevision de transferencia no localizada en el sistema "
							+ idPrevision);
		return prevision;
	}

	/**
	 * Verifica que el usuario del servicio dispone de permiso para la
	 * elaboración de transferencias extraordinarias
	 *
	 * @throws SecurityException
	 *             Cuando el cliente del servicio no dispone de los permisos
	 */
	private void verificarPermisosTransferenciasExtraordinarias()
			throws SecurityException {
		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient
				.hasPermission(AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)
				&& !serviceClient
						.hasPermission(AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS))
			throw new SecurityException(
					"El usuario no dispone de permisos para la elaboración de transferencias extraordinarias");
	}

	/**
	 * Verifica que el usuario del servicio dispone de permiso para la
	 * elaboración de transferencias entre archivos
	 *
	 * @throws SecurityException
	 *             Cuando el cliente del servicio no dispone de los permisos
	 */
	private void verificarPermisosTransferenciasEntreArchivos()
			throws SecurityException {
		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient
				.hasPermission(AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS))
			throw new SecurityException(
					"El usuario no dispone de permisos para la elaboración de transferencias entre archivos");
	}

	private void verificarPermisosGeneralesTransferencia(PrevisionVO previsionVO)
			throws SecurityException {
		if (previsionVO != null) {
			if (previsionVO.isOrdinaria()) {

			} else if (previsionVO.isEntreArchivos()) {
				verificarPermisosTransferenciasEntreArchivos();
			} else {
				verificarPermisosTransferenciasExtraordinarias();
			}
		}
	}

	/**
	 * Actualizacion de una prevision
	 *
	 * @param prevision
	 *            Datos de prevision a actualizar
	 * @throws ActionNotAllowedException
	 *             En caso de que la prevision no pueda ser modificada
	 */
	public void actualizarPrevision(PrevisionVO prevision)
			throws ActionNotAllowedException {

		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA,
						this);
		pistaAuditoria
				.addDetalleBasico(locale, prevision,
						"auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.EDICION_CABECERA");
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
		// prevision.getId());
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
		// Messages.getString("auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.EDICION_CABECERA"));
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// prevision.getCodigoPrevision());

		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);
		PrevisionVO currentInfoPrevision = getPrevision(prevision.getId());
		// if ((currentInfoPrevision.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// && (currentInfoPrevision.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ENTRE_ARCHIVOS))
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(currentInfoPrevision);

		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();

		boolean auditarFondoPrevision = false;
		if (authorizationHelper.puedeSerEditada(prevision)) {
			if (!authorizationHelper.permitidaModificacionFondo(prevision)) {
				if (StringUtils.equals(
						currentInfoPrevision.getIdfondodestino(),
						prevision.getIdfondodestino())) {
					auditarFondoPrevision = true;
				}
				prevision.setIdfondodestino(currentInfoPrevision
						.getIdfondodestino());
			}
			// al modificarla cambiamos su estado y su fecha de estado
			prevision.setEstado(EstadoPrevision.ABIERTA.getIdentificador());
			prevision.setFechaestado(DateUtils.getFechaActual());

			_previsionDbEntity.updatePrevision(prevision);

			pistaAuditoria.auditaModificacionPrevision(locale, prevision,
					currentInfoPrevision, auditarFondoPrevision, this);
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
			// String.valueOf(prevision.getNumuinstalacion()));
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_OBSERVACIONES_PREVISION,
			// prevision.getObservaciones());
			//
			// if(auditarFondoPrevision){
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_FONDO_PREVISION_PREVIO,
			// currentInfoPrevision.getIdfondodestino());
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_FONDO_PREVISION,
			// prevision.getIdfondodestino());
			// }
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Incorpora una nueva linea de detalle a una prevision de transferencia
	 *
	 * @param prevision
	 *            Prevision a la que se desa incorporar la linea de detalle
	 * @param detallePrevision
	 *            Datos de la linea de detalle a incorporar
	 * @throws ActionNotAllowedException
	 *             En caso de que la prevision no pueda ser modificada
	 */
	public void nuevoDetallePrevision(PrevisionVO prevision,
			DetallePrevisionVO detallePrevision)
			throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA,
						this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						prevision,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ADICION_LINEA_DETALLE);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
		// prevision.getId());
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// prevision.getCodigoPrevision());
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
		// Messages.getString(TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ADICION_LINEA_DETALLE));
		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);

		// if (prevision.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(prevision);

		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerEditada(prevision)) {
			if (StringUtils.isEmpty(detallePrevision.getIdProcedimiento())
					&& StringUtils
							.isEmpty(detallePrevision.getIdSerieDestino()))
				throw new ActionNotAllowedException(
						ArchivoErrorCodes.DETALLE_SIN_PROCEDIMIENTO_Y_SERIE);
			else {
				Collection detallesPrevision = _detallePrevisionDBEntity
						.fetchRowsByCodigoPrevision(prevision.getId());
				if (StringUtils.isNotBlank(detallePrevision
						.getIdProcedimiento())) {
					for (Iterator i = detallesPrevision.iterator(); i.hasNext();)
						if (StringUtils.equals(detallePrevision
								.getIdProcedimiento(), ((DetallePrevisionVO) i
								.next()).getIdProcedimiento()))
							throw new PrevisionOperacionNoPermitidaException(
									ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO);
				} else
					for (Iterator i = detallesPrevision.iterator(); i.hasNext();)
						if (StringUtils.equals(detallePrevision
								.getIdSerieDestino(), ((DetallePrevisionVO) i
								.next()).getIdSerieDestino()))
							throw new PrevisionOperacionNoPermitidaException(
									ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMA_SERIE);

			}
			try {
				iniciarTransaccion();
				ServiceRepository services = ServiceRepository
						.getInstance(getServiceSession());
				GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
				SerieVO serie = null;
				InfoBProcedimiento infoProcedimiento = null;
				String strProcedimiento = null;
				if (!GenericValidator.isBlankOrNull(detallePrevision
						.getIdProcedimiento())) {
					// Obtener información de la entidad
					ServiceClient serviceClient = getServiceClient();

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if ((serviceClient != null)
							&& (StringUtils.isNotEmpty(serviceClient
									.getEntity()))) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM,
								serviceClient.getEntity());
					}

					GestorCatalogo catalogoProcedimientos = GestorCatalogoFactory
							.getConnector(params);
					List iProcedimientos = catalogoProcedimientos
							.recuperarInfoBProcedimientos(new String[] { detallePrevision
									.getIdProcedimiento() });
					IProcedimiento procedimiento = catalogoProcedimientos
							.recuperarProcedimiento(detallePrevision
									.getIdProcedimiento());
					if (procedimiento != null) {
						// InfoBProcedimiento infoProcedimiento =
						// procedimiento.getInformacionBasica();
						infoProcedimiento = (InfoBProcedimiento) iProcedimientos
								.get(0);
						strProcedimiento = infoProcedimiento.getNombre();
						serie = seriesBI
								.getSerieProcedimiento(infoProcedimiento
										.getId());
						detallePrevision.setIdSerieDestino(serie
								.getIdelementocf());
						detallePrevision.setCodSistProductor(infoProcedimiento
								.getCodSistProductor());
						detallePrevision
								.setNombreSistProductor(infoProcedimiento
										.getNombreSistProductor());
					}
				} else {
					serie = seriesBI.getSerie(detallePrevision
							.getIdSerieDestino());
				}

				_detallePrevisionDBEntity.insertRow(detallePrevision);

				int nUnidadesInstalacion = prevision.getNumuinstalacion()
						+ detallePrevision.getNumUInstalacion();
				_previsionDbEntity.updateNUnidadesInstalacion(
						prevision.getId(), nUnidadesInstalacion);
				_previsionDbEntity.updateEstadoAndFechaEstado(
						prevision.getId(),
						EstadoPrevision.ABIERTA.getIdentificador(), new Date());

				pistaAuditoria.auditaPrevisionNuevoDetalle(locale, prevision,
						detallePrevision, serie, strProcedimiento, this);

				// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION,
				// String.valueOf(detallePrevision.getNumeroOrden()+1));
				// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_SERIE_DETALLE_PREVISION,
				// serie.getTitulo());
				// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION,
				// detallePrevision.getAnoIniUdoc());
				// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION,
				// detallePrevision.getAnoFinUdoc());
				// loggingEventData.addDetalle(
				// ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_DETALLE_PREVISION,
				// String.valueOf(detallePrevision.getNumUInstalacion()));
				//
				// loggingEventData.addDetalleNoVacio(ArchivoDetails.TRANSFERENCIAS_PROCEDIMIENTO_DETALLE_PREVISION,
				// strProcedimiento);
				commit();
			} catch (GestorCatalogoException gce) {
				throw new UncheckedArchivoException(gce);
			} catch (NotAvailableException nae) {
				throw new UncheckedArchivoException(nae);
			}
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Actualiza una linea de detalle de una previsión
	 *
	 * @param prevision
	 *            Prevision en la que esta incluido el detalle a modificar
	 * @param infoDetallePrevision
	 *            Datos del detalle a modificar
	 *
	 */
	public void modificarDetallePrevision(PrevisionVO prevision,
			DetallePrevisionVO infoDetallePrevision)
			throws ActionNotAllowedException {

		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA,
						this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						prevision,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFCACION_LINEA_DETALLE);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
		// prevision.getId());
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
		// Messages.getString(TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFCACION_LINEA_DETALLE));
		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);
		// if (!prevision.isOrdinaria() && !prevision.isEntreArchivos())
		// verificarPermisosElaboracionTransferenciasExtraordinarias();
		//
		// if (prevision.isEntreArchivos())
		// verificarPermisosElaboracionTransferenciasEntreArchivos();

		verificarPermisosGeneralesTransferencia(prevision);

		DetallePrevisionAuthorizationHelper authorizationHelper = getDetallePrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerModificado(prevision,
				infoDetallePrevision)) {
			// Comprobacion de que no haya otro detalle igual en la prevision
			if (StringUtils.isEmpty(infoDetallePrevision.getIdProcedimiento())
					&& StringUtils.isEmpty(infoDetallePrevision
							.getIdSerieDestino()))
				throw new ActionNotAllowedException(
						ArchivoErrorCodes.DETALLE_SIN_PROCEDIMIENTO_Y_SERIE);
			else {
				Collection detallesPrevision = _detallePrevisionDBEntity
						.fetchRowsByCodigoPrevision(prevision.getId());
				if (StringUtils.isNotBlank(infoDetallePrevision
						.getIdProcedimiento())) {
					for (Iterator i = detallesPrevision.iterator(); i.hasNext();) {
						DetallePrevisionVO detallePrevision = ((DetallePrevisionVO) i
								.next());
						if (StringUtils.equals(
								infoDetallePrevision.getIdProcedimiento(),
								detallePrevision.getIdProcedimiento())
								&& !detallePrevision.getId().equals(
										infoDetallePrevision.getId()))
							throw new PrevisionOperacionNoPermitidaException(
									ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO);

					}
				} else
					for (Iterator i = detallesPrevision.iterator(); i.hasNext();) {
						DetallePrevisionVO detallePrevision = ((DetallePrevisionVO) i
								.next());
						if (StringUtils.equals(
								infoDetallePrevision.getIdSerieDestino(),
								detallePrevision.getIdSerieDestino())
								&& !detallePrevision.getId().equals(
										infoDetallePrevision.getId()))
							throw new PrevisionOperacionNoPermitidaException(
									ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMA_SERIE);

					}
			}
			//
			iniciarTransaccion();
			_detallePrevisionDBEntity.updateRow(infoDetallePrevision);
			_previsionDbEntity.updateNUnidadesInstalacion(prevision.getId(),
					prevision.getNumuinstalacion());

			_previsionDbEntity.updateEstadoAndFechaEstado(prevision.getId(),
					EstadoPrevision.ABIERTA.getIdentificador(), new Date());

			pistaAuditoria.auditaPrevisionModificarDetalle(locale, prevision,
					this);

			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
			// String.valueOf(prevision.getNumuinstalacion()));
			commit();
			prevision.setEstado(EstadoPrevision.ABIERTA.getIdentificador());
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Obtiene las lineas de detalle de una prevision
	 *
	 * @param idPrevision
	 *            Identificador de la prevision cuyas lineas de detalle se
	 *            quieren obtener
	 * @return Lista con las lineas de detalle de la prevision
	 *         {@link DetallePrevisionVO}
	 */
	public Collection getDetallesPrevision(String idPrevision) {
		return _detallePrevisionDBEntity
				.fetchRowsByCodigoPrevision(idPrevision);
	}

	/*
	 * public String getFechaUltimaTransferencia(String codigoProcedimiento,
	 * String codigoSerie) throws Exception { DetallePrevisionVO
	 * detallePrevision = null; PrevisionesDataPersister dataPersister =
	 * getDataPersister(); if (codigoProcedimiento != null) detallePrevision =
	 * dataPersister.findUltimoDetalleXProcedimiento(codigoProcedimiento); else
	 * if (codigoSerie != null) detallePrevision =
	 * dataPersister.findUltimoDetalleXSerie(codigoSerie);
	 *
	 * return detallePrevision != null ? detallePrevision.getAnoFinUdoc() :
	 * null; }
	 */

	// public Collection getPrevisiones(String idOrgRemitente, int[] estados) {
	// return
	// _previsionDbEntity.getPrevisionesXIdOrgRemitenteYEstados(idOrgRemitente,
	// estados);
	// }

	public DetallePrevisionVO getDetallePrevision(String idPrevision,
			int numOrdenDetalle) {
		return _detallePrevisionDBEntity
				.selectRow(idPrevision, numOrdenDetalle);
	}

	/**
	 * Elimina un conjunto de lineas de detalle de una prevision
	 *
	 * @param prevision
	 *            Prevision de la que se eliminan las lineas de detalle
	 * @param idDetallePrevision
	 *            Conjunto de identificadores de lineas de detalle
	 */
	public void eliminarDetallePrevision(PrevisionVO prevision,
			String[] idDetallePrevision) throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();

		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA,
						this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						prevision,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ELIMIACION_LINEA_DETALLE);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
		// prevision.getId());
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
		// Messages.getString(TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ELIMIACION_LINEA_DETALLE));

		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);
		// if (prevision.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(prevision);

		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerEditada(prevision)) {
			iniciarTransaccion();
			int nUInstalacion = prevision.getNumuinstalacion();
			int nDetallesAEliminar = idDetallePrevision.length;
			DetallePrevisionVO detallePrevision = null;
			int[] ordenDetallePrevision = new int[idDetallePrevision.length];
			for (int i = 0; i < nDetallesAEliminar; i++) {
				detallePrevision = _detallePrevisionDBEntity
						.selectRow(idDetallePrevision[i]);
				ordenDetallePrevision[i] = detallePrevision.getNumeroOrden();
				_detallePrevisionDBEntity.dropRow(prevision.getId(),
						idDetallePrevision[i]);
				nUInstalacion = nUInstalacion
						- detallePrevision.getNumUInstalacion();
			}
			Arrays.sort(ordenDetallePrevision);
			String strOrdenDetalles = ArrayUtils.join(ordenDetallePrevision,
					",");
			_previsionDbEntity.updateNUnidadesInstalacion(prevision.getId(),
					nUInstalacion);

			_previsionDbEntity.updateEstadoAndFechaEstado(prevision.getId(),
					EstadoPrevision.ABIERTA.getIdentificador(), new Date());
			// renumerar detalles ( se podria mejorar para solo hacerlo si no es
			// el ultimo)
			Collection detallesPrevision = _detallePrevisionDBEntity
					.fetchRowsByCodigoPrevision(prevision.getId());
			if (detallesPrevision != null) {
				int newIndex = 1;
				for (Iterator itDetallesPrevision = detallesPrevision
						.iterator(); itDetallesPrevision.hasNext();) {
					DetallePrevisionVO detalle = (DetallePrevisionVO) itDetallesPrevision
							.next();
					detalle.setNumeroOrden(newIndex++);
					_detallePrevisionDBEntity.updateRow(detalle);
				}
			}

			pistaAuditoria.auditaPrevisionEliminarDetalle(locale, prevision,
					strOrdenDetalles, nUInstalacion, this);
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
			// String.valueOf(nUInstalacion));
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION,strOrdenDetalles);
			commit();

			prevision.setNumuinstalacion(nUInstalacion);
		} else
			throw new ActionNotAllowedException(
					authorizationHelper.getErrorCode());
	}

	public void transfControlPrevisiones(String[] idsPrevisiones,
			String idNewUser) throws ActionNotAllowedException {
		iniciarTransaccion();
		/*
		 * Collection previsiones
		 * =_previsionDbEntity.getPrevisionesXIds(idsPrevisiones); for (Iterator
		 * itPrevisiones = previsiones.iterator(); itPrevisiones.hasNext();) {
		 * PrevisionVO previsionVO = (PrevisionVO) itPrevisiones.next(); }
		 */
		_previsionDbEntity.updateUsrgestor(idsPrevisiones, idNewUser);
		commit();
	}

	/**
	 * Obtiene el numero de detalles que se han definido para una prevision de
	 * transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 */
	public int numeroDetallesPrevision(String idPrevision) {
		int nDetallesPrevision = 0;
		if (idPrevision != null)
			nDetallesPrevision = _detallePrevisionDBEntity
					.numeroDetallesPrevision(idPrevision);
		return nDetallesPrevision;
	}

	/**
	 * Una vez que el Órgano remitente finaliza la Previsión de Transferencia,
	 * la envía al Archivo para su revisión.
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a enviar
	 * @throws ActionNotAllowedException
	 *             El envio de la prevision no esta permitido
	 */
	public void enviarPrevision(String idPrevision)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA,
						this);
		Locale locale = getServiceClient().getLocale();
		PrevisionVO previsionVO = getPrevision(idPrevision);
		pistaAuditoria.addDetalleBasico(locale, previsionVO);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION, idPrevision);
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// previsionVO.getCodigoPrevision(this.getServiceSession()));
		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);

		// if (previsionVO.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(previsionVO);

		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerEnviada(previsionVO)) {
			_previsionDbEntity.updateEstadoAndFechaEstado(idPrevision,
					EstadoPrevision.ENVIADA.getIdentificador(),
					DateUtils.getFechaActual());

			pistaAuditoria.auditaEnviarPrevision(previsionVO, this);
		} else
			throw new ActionNotAllowedException(
					authorizationHelper.getErrorCode());
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionPrevisionesBI#getCountPrevisionesAGestionar()
	 */
	public int getCountPrevisionesAGestionar()
			throws PrevisionOperacionNoPermitidaException {
		int previsiones = 0;

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR)) {
			final int[] estados = new int[] { EstadoPrevision.ENVIADA
					.getIdentificador() };

			final String[] archivosCustodia = (String[]) getServiceClient()
					.getCustodyArchiveList().toArray(
							ArrayUtils.EMPTY_STRING_ARRAY);

			if (!ArrayUtils.isEmpty(archivosCustodia))
				previsiones = _previsionDbEntity
						.getCountPrevisionesXArchivoReceptor(archivosCustodia,
								estados,
								String.valueOf(DateUtils.getAnoActual()));
		} else
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		return previsiones;
	}

	/**
	 * Obtiene las previsiones que pueden ser gestionadas por el usuario
	 * conectado.
	 *
	 * @return Listado de previsiones {@link PrevisionVO}
	 * @throws PrevisionOperacionNoPermitidaException
	 *             Caso de que el usuario no disponga de permisos para realizar
	 *             gestión de previsiones de transferencia
	 */
	public Collection getPrevisionesAGestionar()
			throws PrevisionOperacionNoPermitidaException {
		Collection previsiones = new ArrayList();

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR)) {
			final int[] estados = new int[] { EstadoPrevision.ENVIADA
					.getIdentificador() };

			final String[] archivosCustodia = (String[]) getServiceClient()
					.getCustodyArchiveList().toArray(
							ArrayUtils.EMPTY_STRING_ARRAY);

			if (!ArrayUtils.isEmpty(archivosCustodia))
				previsiones = _previsionDbEntity
						.getPrevisionesXArchivoReceptor(archivosCustodia,
								estados,
								String.valueOf(DateUtils.getAnoActual()));
		} else
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		return previsiones;
	}

	/**
	 * Acepta una prevision de transferencia enviada al archivo Una vez que el
	 * Archivo dispone de la Previsión de Transferencia enviada por el Órgano
	 * Remitente, éste puede o bien rechazarla o bien aceptarla. Una vez
	 * aceptada, el Órgano Remitente podrá generar Relaciones de Entrega para
	 * cualquier procedimiento de la Previsión de Transferencia.
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a aceptar
	 * @param fechaIniTrans
	 *            Fecha a partir de la cual pueden ser enviadas relaciones de
	 *            entrega realizadas sobre esta prevision de tranferencia
	 * @param fechaFinTrans
	 *            Fecha hasta la que se permite el envio de relaciones de
	 *            entrega realizadas sobre esta prevision de tranferencia
	 * @throws ActionNotAllowedException
	 *             La aceptacion de la relacion de entrega no esta permitida
	 */
	public void aceptarPrevision(String idPrevision, Date fechaIniTrans,
			Date fechaFinTrans) throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA,
						this);
		Locale locale = getServiceClient().getLocale();
		PrevisionVO previsionVO = getPrevision(idPrevision);
		pistaAuditoria.addDetalleBasico(locale, previsionVO);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent logData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION, idPrevision);
		// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// previsionVO.getCodigoPrevision(this.getServiceSession()));
		checkPermission(TransferenciasSecurityManager.ACEPTAR_PREVISION);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerAceptada(previsionVO)) {
			iniciarTransaccion();
			Date fechaEstado = DateUtils.getFechaActual();
			_previsionDbEntity
					.updateEstado_FechaEstado_FechasTransf_motivoRechazo(
							previsionVO.getId(),
							EstadoPrevision.ACEPTADA.getIdentificador(),
							fechaEstado, fechaIniTrans, fechaFinTrans, null);

			pistaAuditoria.auditaAceptarPrevision(locale, previsionVO,
					fechaIniTrans, fechaFinTrans, this);
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
			// DateUtils.formatDate(fechaIniTrans));
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
			// DateUtils.formatDate(fechaFinTrans));

			commit();
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Rechaza una prevision de transferencia enviada al archivo Si una vez
	 * enviada el archivo decide rechazar la previsión el usuario gestor que la
	 * elaboró podrá realizar modificaciones sobre la misma
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a aceptar
	 * @param motivoRechazo
	 *            Motivo por el que la prevision de transferencia es rechazada
	 * @throws ActionNotAllowedException
	 *             El rechazo de la relacion de entrega no está permitido
	 */
	public void rechazarPrevision(String idPrevision, String motivoRechazo)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA,
						this);
		Locale locale = getServiceClient().getLocale();
		PrevisionVO previsionVO = getPrevision(idPrevision);
		pistaAuditoria.addDetalleBasico(locale, previsionVO);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent logData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION, idPrevision);
		// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// previsionVO.getCodigoPrevision(this.getServiceSession()));
		checkPermission(TransferenciasSecurityManager.ACEPTAR_PREVISION);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerAceptada(previsionVO)) {

			Date fechaEstado = DateUtils.getFechaActual();
			iniciarTransaccion();
			_previsionDbEntity.updateEstado_FechaEstado_motivoRechazo(
					idPrevision, EstadoPrevision.RECHAZADA.getIdentificador(),
					fechaEstado, motivoRechazo);

			pistaAuditoria.auditaRechazarPrevision(locale, previsionVO,
					motivoRechazo, this);
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_MOTIVO_RECHAZO,
			// motivoRechazo);

			commit();
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionPrevisionesBI#getCountPrevisionesEnElaboracion(java.
	 * lang.String)
	 */
	public int getCountPrevisionesEnElaboracion(String idUser) {
		int[] estadosPrevision = { EstadoPrevision.ABIERTA.getIdentificador(),
				EstadoPrevision.ENVIADA.getIdentificador(),
		/*
		 * EstadoPrevision.ACEPTADA.getIdentificador(),
		 * EstadoPrevision.RECHAZADA.getIdentificador()
		 */};
		// ServiceClient client = getServiceClient();
		return _previsionDbEntity.getCountPrevisionesXGestor(idUser,
				estadosPrevision, String.valueOf(DateUtils.getAnoActual()));
	}

	/**
	 * Obtiene las previsiones que un usuario tiene en elaboracion.
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de previsiones de transferencia
	 */
	public List getPrevisionesEnElaboracion(String idUser) {
		int[] estadosPrevision = { EstadoPrevision.ABIERTA.getIdentificador(),
				EstadoPrevision.ENVIADA.getIdentificador(),
		/*
		 * EstadoPrevision.ACEPTADA.getIdentificador(),
		 * EstadoPrevision.RECHAZADA.getIdentificador()
		 */};
		// ServiceClient client = getServiceClient();
		return _previsionDbEntity.getPrevisionesXGestor(idUser,
				estadosPrevision, String.valueOf(DateUtils.getAnoActual()));
	}

	public int getCountPrevisionesAceptadasRechazadas(String idUser) {
		int[] estadosPrevision = { EstadoPrevision.ACEPTADA.getIdentificador(),
				EstadoPrevision.RECHAZADA.getIdentificador() };

		return _previsionDbEntity.getCountPrevisionesXGestor(idUser,
				estadosPrevision, String.valueOf(DateUtils.getAnoActual()));

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionPrevisionesBI#getPrevisionesFinalizadas(java.lang.String
	 * )
	 */
	public List getPrevisionesAceptadasRechazadas(String idUser) {
		int[] estadosPrevision = { EstadoPrevision.ACEPTADA.getIdentificador(),
				EstadoPrevision.RECHAZADA.getIdentificador() };

		return _previsionDbEntity.getPrevisionesXGestor(idUser,
				estadosPrevision, String.valueOf(DateUtils.getAnoActual()));

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionPrevisionesBI#getPrevisionesCalendarioPrevisiones(java
	 * .lang.String)
	 */
	public List getPrevisionesCalendarioPrevisiones(String anio,
			String[] idsArchivo) {
		int[] estadosPrevision = { EstadoPrevision.ACEPTADA.getIdentificador() };

		return _previsionDbEntity.getPrevisionesXAnioYArchivo(estadosPrevision,
				anio, idsArchivo);

	}

	// /**
	// * Obtiene el número de previsiones que un usuario tiene en elaboracion.
	// * @param idUser
	// * Identificador de usuario
	// * @return
	// * Número de previsiones que un uaurio tiene en elaboración
	// */
	// public int getCountPrevisionesEnElaboracion(String idUser) {
	// int[] estadosPrevision = {
	// EstadoPrevision.ABIERTA.getIdentificador(),
	// EstadoPrevision.ENVIADA.getIdentificador(),
	// EstadoPrevision.ACEPTADA.getIdentificador(),
	// EstadoPrevision.RECHAZADA.getIdentificador() };
	// return _previsionDbEntity.getCountPrevisionesXGestor(idUser,
	// estadosPrevision,
	// String.valueOf(DateUtils.getAnoActual()));
	// }

	/**
	 * Elimina del sistema las previsiones de transferencia indicadas
	 *
	 * @param previsionesAEliminar
	 *            Identificadores de las previsiones a eliminar
	 * @throws ActionNotAllowedException
	 *             La eliminacion de alguna de las previsiones indicadas no está
	 *             permitida
	 */
	public void eliminarPrevisiones(String[] previsionesAEliminar)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA,
						this);
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent logData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_LISTA_PREVISIONES,
		// null);

		checkPermission(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE);
		Collection previsionesVO = _previsionDbEntity
				.getPrevisionesXIds(previsionesAEliminar);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		boolean canBeDone = true;
		for (Iterator itPrevisionesVO = previsionesVO.iterator(); itPrevisionesVO
				.hasNext();) {
			PrevisionVO previsionVO = (PrevisionVO) itPrevisionesVO.next();
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
			// previsionVO.getCodigoPrevision(this.getServiceSession()));
			if (!authorizationHelper.puedeSerEliminada(previsionVO))
				canBeDone = false;
		}
		if (!canBeDone)
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		iniciarTransaccion();
		for (Iterator itPrevisionesVO = previsionesVO.iterator(); itPrevisionesVO
				.hasNext();) {
			PrevisionVO previsionVO = (PrevisionVO) itPrevisionesVO.next();
			_previsionDbEntity.deletePrevision(previsionVO.getId());
			_detallePrevisionDBEntity.dropRow(previsionVO.getId(), null);
		}

		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.auditaEliminarPrevisiones(locale, previsionesVO);
		commit();
	}

	/**
	 * Modificacion el rango de fechas en las que se permite el envio de
	 * relaciones de entrega elaboradas sobre una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @param fechaInicio
	 *            Nueva fecha de inicio del perio en el que se pueden enviar al
	 *            archivo relaciones de entrega elaboradas sobre la prevision de
	 *            transferencia
	 * @param fechaFin
	 *            Nueva fecha de finalización del perio en el que se pueden
	 *            enviar al archivo relaciones de entrega elaboradas sobre la
	 *            prevision de transferencia
	 * @throws ActionNotAllowedException
	 *             La modificacion del periodo en el que se pueden enviar al
	 *             archivo relaciones de entrega elaboradas sobre la prevision
	 *             de transferencia no está permitida
	 */
	public void modificarFechasCalendario(String idPrevision, Date fechaInicio,
			Date fechaFin) throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();

		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA,
						this);
		PrevisionVO previsionVO = getPrevision(idPrevision);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						previsionVO,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFICACION_FECHAS_TRANSFERENCIA);

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData =
		// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION, idPrevision);
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
		// Messages.getString(TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFICACION_FECHAS_TRANSFERENCIA));
		// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
		// prevision.getCodigoPrevision(this.getServiceSession()));

		checkPermission(TransferenciasSecurityManager.MODIFICAR_PREVISION);
		PrevisionVO prevision = getPrevision(idPrevision);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.puedeSerModificada(prevision)) {
			iniciarTransaccion();
			// loggingEventData.addDetalle(ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
			// DateUtils.formatDate(fechaFin));
			_previsionDbEntity.updateFechasTransf(idPrevision, fechaInicio,
					fechaFin);

			pistaAuditoria.auditaModificacionFechasPrevision(locale,
					fechaInicio, fechaFin);
			commit();
		} else
			throw new PrevisionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Obtiene la informacion referente a una linea de detalle de prevision
	 *
	 * @param idDetallePrevision
	 *            Identificador de la linea de detalle
	 * @return Linea de detalle de prevision {@link DetallePrevisionVO}
	 */
	public DetallePrevisionVO getDetallePrevision(String idDetallePrevision) {
		DetallePrevisionVO detallePrevision = _detallePrevisionDBEntity
				.selectRow(idDetallePrevision);
		if (detallePrevision == null)
			throw new UncheckedArchivoException(
					"La linea de detalle no ha sido encontrada en la base de datos: "
							+ idDetallePrevision);
		return detallePrevision;
	}

	/**
	 * Pone un detalle de prevision a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre el detalle
	 *
	 * @param idDetallePrevision
	 *            Identificador de detalle de prevision
	 * @return Detalle de prevision {@link DetallePrevisionVO}
	 */
	public DetallePrevisionVO abrirDetallePrevision(String idDetallePrevision) {
		DetallePrevisionVO detallePrevision = getDetallePrevision(idDetallePrevision);
		if (detallePrevision == null)
			throw new UncheckedArchivoException(
					"Detalle de prevision no encontrada en base de datos "
							+ idDetallePrevision);
		PrevisionVO prevision = _previsionDbEntity
				.getInfoPrevision(detallePrevision.getIdPrevision());
		if (prevision == null)
			throw new UncheckedArchivoException(
					"Prevision no encontrada en base de datos "
							+ detallePrevision.getIdPrevision());

		getDetallePrevisionAuthorizationHelper().configureDetallePrevision(
				prevision, detallePrevision);
		return detallePrevision;
	}

	/**
	 * Obtiene las previsiones de un gestor que aceptan que se creen relaciones
	 * de entrega a partir de ellas. Un usuario puede crear relaciones de
	 * entrega sobre aquellas previsiones de transferencia de las que es gestor,
	 * que hayan sido aceptadas por el archivo y cuyo rango de fechas de
	 * calendario asignadas no haya expirado
	 *
	 * @param idGestor
	 *            Identificador de gestor
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesAceptanRelaciones(String idGestor) {
		int[] estados = { EstadoPrevision.ACEPTADA.getIdentificador() };
		List previsiones = _previsionDbEntity.getPrevisionesXGestor(idGestor,
				estados, String.valueOf(DateUtils.getAnoActual()));
		final Date currentDate = DateUtils.getFechaActualSinHora();
		CollectionUtils.filter(previsiones, new Predicate() {
			public boolean evaluate(Object obj) {
				return DateUtils.isFechaMenorOIgual(currentDate,
						((PrevisionVO) obj).getFechafintrans());

				// currentDate.before(((PrevisionVO)obj).getFechafintrans());
			}
		});
		return previsiones;
	}

	/**
	 * Obtiene la informacion referente a una serie de previsiones de
	 * transferencia
	 *
	 * @param idsPrevisiones
	 *            Lista de identificadores de prevision de transferencia
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public Collection getPrevisionesXId(String[] idsPrevisiones) {
		return _previsionDbEntity.getPrevisionesXIds(idsPrevisiones);
	}

	/**
	 * Obtiene los procedimientos de los que un determinado organo remitente
	 * puede transferir documentacion al archivo
	 *
	 * @param idOrgano
	 *            Identificador del organo remitente
	 * @param tipoProcedimiento
	 *            Tipo de procedimiento: AUTOMATIZADO o NO AUTOMATIZADO
	 * @return Lista de procedimiento
	 *         {@link se.procedimientos.InfoBProcedimiento}
	 * @throws GestorCatalogoException
	 *             Error en invocacion al sistema de gestion del catalogo de
	 *             procedimientos
	 * @throws NotAvailableException
	 *             Funcionalidad no implementada por el sistema de gestion del
	 *             catalogo de procedimientos
	 */
	private List getProcedimientosOrgano(String idOrgano,
			final int tipoProcedimiento) throws GestorCatalogoException,
			NotAvailableException {
		if (logger.isDebugEnabled())
			logger.debug("Solicitados procedimientos de tipo: "
					+ tipoProcedimiento);
		String idProductor = null;
		if (idOrgano != null) {
			if (logger.isDebugEnabled())
				logger.debug("Solicitados procedimientos para organo "
						+ idOrgano);
			OrganoProductorVO productor = _organoProductorDBEntity
					.getOrgProductorXIdOrgano(idOrgano);
			if (productor == null)
				logger.warn("El órgano no ha sido incorporado como productor de ningúno de los procedimientos cuya documentación puede ser transferida al archivo");
			// throw new ArchivoModelException(getClass(),
			// "getProcedimientosOrgano",
			// "El órgano no ha sido incorporado como productor de ningúno de los procedimientos cuya documentación puede ser transferida al archivo");
			else
				idProductor = productor.getId();
		}
		List procedimientosOrgano = _productorSerieDBEnitity
				.getProcedimientosXProductor(idProductor,
						ProductorSerieVO.TIPO_ORG_DEP);
		if (procedimientosOrgano != null && procedimientosOrgano.size() > 0) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			/* GestionSeriesBI serieBI = */services.lookupGestionSeriesBI();
			final int[] estadosSerie = { EstadoSerie.VIGENTE,
					EstadoSerie.PENDIENTE_HISTORICA, EstadoSerie.HISTORICA };
			String[] procedimientoIDs = (String[]) procedimientosOrgano
					.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			if (logger.isDebugEnabled())
				logger.debug("Procedimientos tramitados por el organo: "
						+ ArrayUtils.join(procedimientoIDs, ","));
			Arrays.sort(estadosSerie);
			List seriesProcedimientos = _serieDBEntity
					.getSerieXProcedimientos(procedimientoIDs);

			final List result = new ArrayList();
			CollectionUtils.forAllDo(seriesProcedimientos, new Closure() {
				public void execute(Object obj) {
					SerieVO serie = (SerieVO) obj;
					if (logger.isDebugEnabled())
						logger.debug("Procedimiento "
								+ serie.getIdProcedimiento());
					if (!(Arrays.binarySearch(estadosSerie,
							serie.getEstadoserie()) < 0))
						if (serie.getTipoProcedimiento() == tipoProcedimiento)
							result.add(serie.getIdProcedimiento());
						else {
							if (logger.isDebugEnabled())
								logger.debug("El tipo del procedimiento no es el buscado. Tipo procedimiento: "
										+ serie.getTipoProcedimiento());
						}
					else if (logger.isDebugEnabled())
						logger.debug("El estado de la serie asociada al procedimiento no permite realizar transferencias sobre ella. Estado serie: "
								+ serie.getEstado());
				}
			});
			// Obtener información de la entidad
			ServiceClient serviceClient = getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorCatalogo catalogoProcedimientos = GestorCatalogoFactory
					.getConnector(params);
			String[] procedimientos = (String[]) result
					.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			if (logger.isDebugEnabled())
				logger.debug("Procedimientos: "
						+ ArrayUtils.join(procedimientos, ","));
			return catalogoProcedimientos
					.recuperarInfoBProcedimientos(procedimientos);
		} else {
			logger.info("No se han localizado procedimientos para el organo "
					+ idOrgano);
			return ArrayUtils.EMPTY_LIST;
		}
	}

	/**
	 * Obtiene los procedimientos que pueden ser transferidos en una prevision
	 * de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision
	 * @return Lista de procedimientos {@link IProcedimiento}
	 */
	public List getProcedimientosPrevision(String idPrevision) {
		PrevisionVO prevision = getPrevision(idPrevision);
		if (prevision == null)
			throw new UncheckedArchivoException(
					"Prevision no localizada en base de datos " + idPrevision);
		if (logger.isDebugEnabled())
			logger.debug("Obteniendo procedimientos para prevision "
					+ CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(
							prevision, getServiceSession()));
		try {
			List result = null;
			if (prevision.getTipotransferencia() == TipoTransferencia.ORDINARIA
					.getIdentificador()) {
				result = getProcedimientosOrgano(prevision.getIdorgremitente(),
						IProcedimiento.AUTOMATICO);
			} else if (prevision.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
					.getIdentificador()) {
				if (getServiceClient()
						.hasPermission(
								AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)) {
					result = getProcedimientosOrgano(null,
							IProcedimiento.AUTOMATICO);
				} else {
					result = getProcedimientosOrgano(
							prevision.getIdorgremitente(),
							IProcedimiento.NO_AUTOMATICO);
				}
			}
			return result;
		} catch (GestorCatalogoException gce) {
			throw new ArchivoModelException(getClass(),
					"getProcedimientosPrevision", gce.getMessage());
		} catch (NotAvailableException nae) {
			throw new ArchivoModelException(getClass(),
					"getProcedimientosPrevision", nae.getMessage());
		}
	}

	/**
	 * Inicializa una nueva prevision de transferencia
	 *
	 * @param idUser
	 *            Identificador del usuario que solicita nueva prevision
	 * @param idOrg
	 *            Organo remitente de la transferencia
	 * @param tipoTransferencia
	 *            Tipo de transferencia: Ordinario, Extraordinaria sin signatura
	 *            o Extraordinaria con signatura
	 * @param tipoPrevision
	 *            Tipo de prevision: Con detalle o sin detalle
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO nuevaPrevision(String idUser, String idOrganoRemitente,
			int tipoTransferencia, int tipoPrevision, String idArchivo,
			String codArchivo) throws ActionNotAllowedException {
		if (tipoTransferencia == TipoTransferencia.ORDINARIA.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_ORDINARIA);
		else if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador()
				|| tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
						.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_EXTRAORDINARIA);
		else if (tipoTransferencia == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador())
			checkPermission(TransferenciasSecurityManager.ALTA_PREVISION_ENTRE_ARCHIVOS);
		else
			throw new ArchivoModelException(getClass(), "nuevaPrevision",
					"Tipo de transferencia desconocido: " + tipoTransferencia);
		PrevisionVO prevision = new PrevisionVO();
		prevision.setTipoprevision(tipoPrevision);
		prevision.setTipotransferencia(tipoTransferencia);
		prevision.setIdorgremitente(idOrganoRemitente);
		prevision.setIdusrgestor(idUser);
		PrevisionAuthorizationHelper authorizationHelper = getPrevisionAuthorizationHelper();
		if (authorizationHelper.permitidaCreacionPrevision(prevision)) {
			prevision.setEstado(EstadoPrevision.ABIERTA.getIdentificador());
			int ano = DateUtils.getAnoActual();
			prevision.setAno(Integer.toString(ano));
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionFondosBI fondoBI = services.lookupGestionFondosBI();
			if (tipoTransferencia != TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador()) {
				FondoVO fondo = fondoBI.getFondoXOrganoRemitente(
						idOrganoRemitente, idArchivo, codArchivo);
				if (fondo == null) {
					if (!authorizationHelper
							.permitidaModificacionFondo(prevision)) {
						throw new ActionNotAllowedException(null,
								ArchivoErrorCodes.FONDO_DESTINO_NO_DEFINIDO,
								ArchivoModules.TRANSFERENCIAS_MODULE);
					}
				} else {
					prevision.setIdfondodestino(fondo.getId());
					// prevision.setIdarchivoreceptor(fondo.getIdArchivo());
				}
				// Obtener el archivo a partir del organo remitente
				GestionControlUsuariosBI controlUsuariosBI = services
						.lookupGestionControlUsuariosBI();
				CAOrganoVO organoVO = controlUsuariosBI
						.getCAOrgProductorVOXId(idOrganoRemitente);
				if (organoVO != null)
					prevision.setIdarchivoreceptor(organoVO
							.getIdArchivoReceptor());
				else
					throw new ActionNotAllowedException(null,
							ArchivoErrorCodes.ARCHIVO_DESTINO_NO_DEFINIDO,
							ArchivoModules.TRANSFERENCIAS_MODULE);
			}
			authorizationHelper.configurePrevision(prevision);
			return prevision;
		} else
			throw new ActionNotAllowedException(null,
					authorizationHelper.getErrorCode(),
					ArchivoModules.TRANSFERENCIAS_MODULE);
	}

	/**
	 * Obtiene la lista de previsiones activas que se encuentran en elaboracion
	 * en un organo remitente. La lista incluye previsiones del propio organo
	 * indicado y de sus organos dependientes
	 *
	 * @param idOrgano
	 *            Identificador de organo remitente
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesActivasEnOrgano(String idOrgano) {
		int[] estados = { EstadoPrevision.ABIERTA.getIdentificador(),
				EstadoPrevision.ENVIADA.getIdentificador(),
				EstadoPrevision.RECHAZADA.getIdentificador() };
		GestionControlUsuariosBI controlAccesoBI = ServiceRepository
				.getInstance(getServiceSession())
				.lookupGestionControlUsuariosBI();
		CAOrganoVO organo = controlAccesoBI.getCAOrgProductorVOXId(idOrgano);
		if (organo == null)
			throw new ArchivoModelException(getClass(),
					"getPrevisionesActivasEnOrgano",
					"Organo no encontrado en sistema: " + idOrgano);
		List listaOrganos = new ArrayList();
		listaOrganos.add(idOrgano);
		try {
			// Obtener información de la entidad
			ServiceClient serviceClient = getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(organo.getSistExtGestor(), params);
			List organosDependientes = gestorOrganismos
					.recuperarOrganosDependientes(idOrgano, 0);
			if (organosDependientes != null) {
				CollectionUtils.transform(organosDependientes,
						VO2IDTransformer.getInstance());
				List organosDependientesEnSistema = controlAccesoBI
						.getCAOrgProductorVOXId((String[]) organosDependientes
								.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
				for (Iterator i = organosDependientesEnSistema.iterator(); i
						.hasNext();)
					listaOrganos.add(((CAOrganoVO) i.next()).getIdOrg());
			}
			String[] listaOrganosIDs = (String[]) listaOrganos
					.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			return _previsionDbEntity.getPrevisionesXIdOrgRemitenteYEstados(
					listaOrganosIDs, estados);
		} catch (GestorOrganismosException goe) {
			throw new UncheckedArchivoException(goe);
		} catch (NotAvailableException nae) {
			throw new UncheckedArchivoException(nae);
		}
	}

	/**
	 * Obtiene las previsiones que tienen a un usuario por gestor
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Lista de previsiones de transferencia
	 */
	public List getPrevisionesGestor(String idGestor) {
		int[] estadosPrevision = { EstadoPrevision.ABIERTA.getIdentificador(),
				EstadoPrevision.ENVIADA.getIdentificador(),
				EstadoPrevision.ACEPTADA.getIdentificador(),
				EstadoPrevision.RECHAZADA.getIdentificador() };

		return _previsionDbEntity.getPrevisionesXGestor(idGestor,
				estadosPrevision, null);
	}

	protected LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(
				ArchivoModules.TRANSFERENCIAS_MODULE, action,
				getServiceClient(), false);

		// Lo añadimos a la pila
		getLogger().add(le);
		return le;
	}

	/**
	 * Obtiene los estados de las previsiones.
	 *
	 * @return Estados de las previsiones.
	 */
	public List getEstadosPrevisiones() {
		List estados = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		estados.add(new TypeDescVO(EstadoPrevision.ABIERTA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.ABIERTA.getIdentificador(), locale)));

		estados.add(new TypeDescVO(EstadoPrevision.ENVIADA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.ENVIADA.getIdentificador(), locale)));

		estados.add(new TypeDescVO(EstadoPrevision.ACEPTADA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.ACEPTADA.getIdentificador(), locale)));

		estados.add(new TypeDescVO(EstadoPrevision.RECHAZADA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.RECHAZADA.getIdentificador(), locale)));

		estados.add(new TypeDescVO(EstadoPrevision.CERRADA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.CERRADA.getIdentificador(), locale)));

		if(ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionWsTransferencias().isGestionarAutomatizadas()){
			estados.add(new TypeDescVO(EstadoPrevision.AUTOMATIZADA.getIdentificador(), Messages.getString(
				TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
						+ EstadoPrevision.AUTOMATIZADA.getIdentificador(), locale)));
		}

		return estados;
	}

	/**
	 * Obtiene los tipos de transferencias.
	 *
	 * @return Tipos de transferencias.
	 */
	public List getTiposTransferencias() {
		Locale locale = getServiceClient().getLocale();
		return TransferenciasConstants.getTiposTransferencias(locale);
	}

	/**
	 * Busca las previsiones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de previsiones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrevisiones(BusquedaPrevisionesVO vo)
			throws TooManyResultsException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA,
						this);
		pistaAuditoria.addDetalleVacio();

		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA);
		// DataLoggingEvent loggingEventData = new DataLoggingEvent();
		// le.getData().add(loggingEventData);

		// Comprobar permisos
		checkPermission(TransferenciasSecurityManager.GESTION_TRANSFERENCIAS);

		// Información del usuario conectado
		ServiceClient client = getServiceClient();

		// Previsiones de los archivos a los que pertenece el usuario
		if ((!client.hasAnyPermission(new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.CONSULTA_TOTAL_SISTEMA }))) {

			if (client
					.hasAnyPermission(new String[] { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR })) {
				// Búsqueda en todos los órganos existentes y pertenecientes
				// a los archivos del usuario
				vo.setArchivosReceptores((String[]) client
						.getCustodyArchiveList().toArray(
								new String[client.getCustodyArchiveList()
										.size()]));
			}
		}

		// Previsiones del órgano del usuario y descendientes
		if ((!client.hasAnyPermission(new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.CONSULTA_TOTAL_SISTEMA }))) {

			if (client
					.hasPermission(AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE)) {
				// Búsqueda en los órganos del usuario y sus descendientes
				List organos = new ArrayList(
						client.getDependentOrganizationList());
				if (client.getOrganization() != null)
					organos.add(0, client.getOrganization());

				String[] idsOrganos = new String[organos.size()];
				for (int i = 0; i < organos.size(); i++)
					idsOrganos[i] = ((CAOrganoVO) organos.get(i)).getIdOrg();

				vo.setOrganosUsuario(idsOrganos);
			}
		}

		// Previsiones del usuario
		vo.setIdGestor(client.getId());

		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.auditaBusquedaPrevisiones(locale, vo);

		return _previsionDbEntity.getPrevisiones(vo);
	}

	/**
	 * Obtiene la lista de gestores con previsiones.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrevision(String idOrgano,
			int[] tiposTransferencia) {
		return _previsionDbEntity.getGestoresConPrevision(idOrgano,
				tiposTransferencia);
	}

	/**
	 * Obtiene la lista de gestores.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tipoTransferencia
	 *            Tipo de transferencia.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestores(String idOrgano, int tipoTransferencia) {
		return new ArrayList();
	}

	/**
	 * Asigna una lista de previsiones a un gestor.
	 *
	 * @param idsPrevisiones
	 *            Lista de identificadores de previsiones.
	 * @param tipoTransferencia
	 *            Tipo de las previsiones ({@link TipoTransferencia}).
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws PrevisionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de la
	 *             previsión
	 */
	public UsuarioVO asignarPrevisionesAGestor(String[] idsPrevisiones,
			TipoTransferencia tipoTransferencia, String idGestor)
			throws PrevisionOperacionNoPermitidaException {
		// Auditoría
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA,
						this);
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA);

		// Comprobar permisos del usuario conectado
		checkPermission(TransferenciasSecurityManager.CESION_CONTROL_PREVISIONES);

		if (ArrayUtils.isEmpty(idsPrevisiones))
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_HAY_PREVISIONES_SELECCIONADAS);

		// Obtener la información del usuario
		GestionControlUsuariosBI gcu = getGestionControlUsusarios();
		UsuarioVO usuario = gcu.getUsuario(idGestor);
		if (usuario == null)
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_EXISTE_GESTOR_ESPECIFICADO);

		// Comprobar los permisos del usuario
		boolean tienePermisoAmpliado = false;
		if (TipoTransferencia.ORDINARIA.equals(tipoTransferencia))
			tienePermisoAmpliado = gcu
					.userHasPermission(
							idGestor,
							AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS);
		else
			tienePermisoAmpliado = gcu
					.userHasPermission(
							idGestor,
							AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS);

		// Si permiso Estándar, comprobar que no tenga previsiones asignadas
		if (!tienePermisoAmpliado) {
			final int[] estados = new int[] {
					EstadoPrevision.ABIERTA.getIdentificador(),
					EstadoPrevision.ENVIADA.getIdentificador(),
					EstadoPrevision.RECHAZADA.getIdentificador() };

			List previsiones = _previsionDbEntity.getPrevisionesXGestor(
					idGestor, estados, null);
			if (!util.CollectionUtils.isEmpty(previsiones))
				throw new PrevisionOperacionNoPermitidaException(
						ArchivoErrorCodes.ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR);
		}

		// Cesión del control de la previsión
		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();
		for (int i = 0; i < idsPrevisiones.length; i++) {
			PrevisionVO prevision = getPrevision(idsPrevisiones[i]);
			// loggingEventData =
			// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
			// idsPrevisiones[i]);
			// loggingEventData.addDetalle(
			// ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION ,
			// prevision.getCodigoPrevision(this.getServiceSession()));
			UsuarioVO usuarioAnt = gcu.getUsuario(prevision.getIdusrgestor());

			// loggingEventData.addDetalle(
			// ArchivoDetails.TRANSFERENCIAS_GESTOR_ANTERIOR,
			// usuarioAnt.getNombreCompleto() );
			// loggingEventData.addDetalle(
			// ArchivoDetails.TRANSFERENCIAS_GESTOR_NUEVO,
			// usuario.getNombreCompleto());
			_previsionDbEntity.updateUsrgestor(
					new String[] { idsPrevisiones[i] }, idGestor);

			// Auditoría
			pistaAuditoria.addNewDetalleBasico(locale, prevision);
			pistaAuditoria.auditaCesionControl(locale, usuarioAnt, usuario);
		}
		commit();

		return usuario;
	}

	private class SerieFondoSelector implements Predicate {

		String idSerie;

		SerieFondoSelector(String idSerie) {
			this.idSerie = idSerie;
		}

		public boolean evaluate(Object arg0) {
			return ((SerieVO) arg0).getId().equals(idSerie);
		}

	}

	public List findProcedimientosATransferir(String idFondo,
			int tipoProcedimiento, String titulo)
			throws GestorCatalogoException, NotAvailableException {
		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		GestorCatalogo gestorCatalogo = GestorCatalogoFactory
				.getConnector(params);
		List procedimientos = gestorCatalogo.recuperarInfoBProcedimientos(
				tipoProcedimiento, titulo);
		if ((procedimientos != null) && (!procedimientos.isEmpty())) {
			String[] procedimientoIDs = new String[procedimientos.size()];
			int count = 0;
			for (Iterator i = procedimientos.iterator(); i.hasNext(); count++)
				procedimientoIDs[count] = ((InfoBProcedimiento) i.next())
						.getId();
			Map series = _serieDBEntity.getSeriesAsociadas(procedimientoIDs);

			// obtener series del fondo
			List seriesDelFondo = _serieDBEntity.getSeriesXFondoYEstados(
					idFondo, new int[] { EstadoSerie.HISTORICA,
							EstadoSerie.VIGENTE });

			// filtrar series
			List procedimientosSinSerieEnFondo = new ArrayList();
			for (int i = 0; i < count; i++) {
				if (series.get(procedimientoIDs[i]) != null) {
					SerieVO serie = (SerieVO) CollectionUtils.find(
							seriesDelFondo, new SerieFondoSelector(
									(String) series.get(procedimientoIDs[i])));
					if (serie == null) {
						procedimientosSinSerieEnFondo
								.add(procedimientos.get(i));
					}
				} else {
					procedimientosSinSerieEnFondo.add(procedimientos.get(i));
				}
			}
			procedimientos.removeAll(procedimientosSinSerieEnFondo);
			//
			//
			// List procedimientosSinSerie = new ArrayList();
			// for(int i = 0; i < count; i++)
			// if (series.get(procedimientoIDs[i]) == null)
			// procedimientosSinSerie.add(procedimientos.get(i));
			// procedimientos.removeAll(procedimientosSinSerie);

			// comprobar que la series son solo las del fondo de la prevision

		}
		return procedimientos;
	}

	/**
	 * Cierra las previsiones que hayan caducado.
	 */
	public void cerrarPrevisiones() {
		// Auditoría
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA,
						this);
		// LoggingEvent le =
		// getLogginEvent(ArchivoActions.TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA);

		// Comprobar permisos
		checkPermission(TransferenciasSecurityManager.CERRAR_PREVISIONES);

		// Cerrar las previsiones caducadas
		Locale locale = getServiceClient().getLocale();
		List previsionesCaducadas = _previsionDbEntity
				.getPrevisionesCaducadas();
		PrevisionVO prevision;
		for (int i = 0; i < previsionesCaducadas.size(); i++) {
			prevision = (PrevisionVO) previsionesCaducadas.get(i);

			iniciarTransaccion();

			// Cerrar la previsión
			_previsionDbEntity.cerrarPrevision(prevision.getId());

			// Auditoría
			// DataLoggingEvent logData =
			// le.getDataLoggingEvent(ArchivoObjects.OBJECT_PREVISION,
			// prevision.getId());
			// logData.addDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
			// prevision.getCodigoPrevision(this.getServiceSession()));
			// logData.addDetalle(
			// ArchivoDetails.TRANSFERENCIAS_TIPO_TRANSFERENCIA,
			// TipoTransferencia.getTipoTransferencia(
			// prevision.getTipotransferencia()).getNombre() );
			pistaAuditoria.addNewDetalleBasico(locale, prevision);
			pistaAuditoria.auditaCerrarPrevision(locale, prevision);
			commit();
		}
	}

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idCampoFechaExtremaFinal
	 * @param idsUnidadesDocumentales
	 */
	public void getUInstConCondiciones(Date fechaInicial, Date fechaFinal,
			String idArchivoEmisor, String idPadre,
			String idCampoFechaExtremaFinal, List idsUnidadesDocumentales) {
		iniciarTransaccion();
		// _detallePrevisionDBEntity.getUInstXArchivoYSerieYFormatoConFechas(fechaInicial,
		// fechaFinal, idArchivoEmisor, idPadre, idCampoFechaExtremaFinal);
		_detallePrevisionDBEntity.getUInstXUdoc(idsUnidadesDocumentales);
		commit();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see common.bi.GestionPrevisionesBI#generarPrevisionElectronica(transferencias.vos.TransferenciaElectronicaInfo)
	 */
	public void establecerPrevisionElectronica(
			TransferenciaElectronicaInfo info) throws TransferenciaElectronicaException {

		if(logger.isDebugEnabled()){
			logger.debug("Inicio establecer prevision electrónica");
		}

		PrevisionVO prevision = new PrevisionVO();
		prevision.setTipotransferencia(TipoTransferencia.ORDINARIA.getIdentificador());
		prevision.setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
		prevision.setIdorgremitente(info.getIdOrganoRemitente());
		prevision.setAno(info.getAnio());
		prevision.setIdfondodestino(info.getIdFondoDestino());
		prevision.setIdarchivoreceptor(info.getIdArchivoReceptor());
		prevision.setIdusrgestor(info.getIdUsuarioGestor());
		prevision.setEstado(EstadoPrevision.AUTOMATIZADA.getIdentificador());

		PrevisionVO previsionBD = _previsionDbEntity.getPrevisionByVO(prevision);

		if(previsionBD == null){
			if(logger.isInfoEnabled()){
				logger.info("La prevision no existe, se va a proceder a crearla.");
			}

			prevision.setFechainitrans(DateUtils.getFirstDayOfYear(info.getAnioAsInt()));
			prevision.setFechafintrans(DateUtils.getLastDayOfYear(info.getAnioAsInt()));
			prevision.setTipotransferencia(TipoTransferencia.ORDINARIA.getIdentificador());
			prevision.setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
			prevision.setIdorgremitente(info.getIdOrganoRemitente());
			prevision.setAno(info.getAnio());
			prevision.setIdfondodestino(info.getIdFondoDestino());
			prevision.setIdarchivoreceptor(info.getIdArchivoReceptor());
			prevision.setIdusrgestor(info.getIdUsuarioGestor());
			prevision.setEstado(EstadoPrevision.AUTOMATIZADA.getIdentificador());


			try {
				insertPrevision(prevision);

				info.setPrevisionVO(prevision);
			} catch (ActionNotAllowedException e) {
				throw new TransferenciaElectronicaException(TransferenciasElectronicasConstants.ERROR_SIN_PERMISOS,
						TransferenciasElectronicasConstants.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CREAR_PREVISION),
						e);
			}
		}
		else{
			info.setPrevisionVO(previsionBD);
		}


		if(logger.isDebugEnabled()){
			logger.debug("Fin establecer prevision electrónica");
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see common.bi.GestionPrevisionesBI#generarDetallePrevisionElectronica(transferencias.vos.TransferenciaElectronicaInfo)
	 */
	public void establecerDetallePrevisionElectronica(
			TransferenciaElectronicaInfo info) throws TransferenciaElectronicaException {


		if(logger.isDebugEnabled()){
			logger.debug("Inicio establecer detalle prevision electrónica");
		}

		DetallePrevisionVO detallePrevision = new DetallePrevisionVO();

		detallePrevision.setIdPrevision(info.getIdPrevision());
		detallePrevision.setCodSistProductor(info.getCodSistemaProductor());
		detallePrevision.setNombreSistProductor(info.getNombreSistProductor());
		detallePrevision.setIdProcedimiento(info.getCodigoProcedimiento());
		detallePrevision.setIdSerieDestino(info.getSerieVO().getId());

		DetallePrevisionVO detalleBD = _detallePrevisionDBEntity.getDetallePrevisionVO(detallePrevision);

		if(detalleBD == null){

			if(logger.isInfoEnabled()){
				logger.info("No existe el detalle de la previsión se va proceder a crearlo");
			}

			detallePrevision.setIdPrevision(info.getIdPrevision());
			detallePrevision.setCodSistProductor(info.getCodSistemaProductor());
			detallePrevision.setNombreSistProductor(info.getNombreSistProductor());
			detallePrevision.setIdProcedimiento(info.getCodigoProcedimiento());
			detallePrevision.setIdSerieDestino(info.getSerieVO().getId());
			detallePrevision.setAnoFinUdoc(info.getAnio());
			detallePrevision.setAnoIniUdoc(info.getAnio());
			detallePrevision.setIdFormatoUI(Constants.FORMATO_UI_TRANSFERENCIA_ELECTRONICA);

			_detallePrevisionDBEntity.insertRow(detallePrevision);

			info.setDetallePrevisionVO(detallePrevision);

		}
		else{
			info.setDetallePrevisionVO(detalleBD);
		}

		if(logger.isDebugEnabled()){
			logger.debug("Fin establecer prevision electrónica");
		}
	}
}