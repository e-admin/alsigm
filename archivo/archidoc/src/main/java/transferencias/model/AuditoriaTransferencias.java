/**
 *
 */
package transferencias.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.BusquedaRelacionesVO;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionVO;
import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.utils.AuditoriaUtils;

import common.Constants;
import common.Messages;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.IServiceBase;
import common.bi.ServiceSession;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.util.DateUtils;

import fondos.model.UnidadDocumental;
import fondos.vos.SerieVO;
import gcontrol.vos.UsuarioVO;

/**
 * @author davidm clase para encapsular y englobar todas las auditorias de
 *         transferencias Se trata de extraer de los metodos del servicio la
 *         parte de auditoria
 */
public class AuditoriaTransferencias {

	private static AuditoriaTransferencias instancia = new AuditoriaTransferencias();

	private AuditoriaTransferencias() {
	};

	public static PistaAuditoriaTransferencias crearPistaAuditoria(int action,
			IServiceBase service) {
		return instancia.new PistaAuditoriaTransferencias(action, service);
	}

	public static PistaAuditoriaIngreso crearPistaAuditoriaIngreso(int action,
			IServiceBase service) {
		return instancia.new PistaAuditoriaIngreso(action, service);
	}

	public static IAuditoriaTransferencias crearPistaAuditoria(
			RelacionEntregaVO relacionEntrega, int action, IServiceBase service) {
		if (relacionEntrega.getIsIngresoDirecto())
			return instancia.new PistaAuditoriaIngreso(action, service);
		else
			return instancia.new PistaAuditoriaTransferencias(action, service);
	}

	public static IAuditoriaTransferencias crearPistaAuditoria(
			int tipoTransferencia, int action, IServiceBase service) {
		if (tipoTransferencia == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador())
			return instancia.new PistaAuditoriaIngreso(action, service);
		else
			return instancia.new PistaAuditoriaTransferencias(action, service);
	}

	public class PistaAuditoriaTransferencias implements
			IAuditoriaTransferencias {
		private LoggingEvent le = null;
		private ServiceSession ss = null;
		private DataLoggingEvent logData = null;
		private GestionPrevisionesBI servicioPrevisiones = null;
		private GestionRelacionesEntregaBI servicioRelaciones = null;

		public int getActionAltaRelacionEntrega() {
			return ArchivoActions.TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA;
		}

		public PistaAuditoriaTransferencias(int action, IServiceBase service) {
			getLogginEvent(action, service);
		}

		private LoggingEvent getLogginEvent(int action, IServiceBase service) {
			// se rellenan variables static de la clase.
			// la clase es como un contenedor de variables (globales)
			// los atributos almacenan los datos de la ultima pista de auditoria
			// creada.
			// de esta forma es posible añadir más detalles/campos a los
			// detalles.

			ss = service.getServiceSession();
			if (service instanceof GestionPrevisionesBI)
				servicioPrevisiones = (GestionPrevisionesBI) service;
			if (service instanceof GestionRelacionesEntregaBI)
				servicioRelaciones = (GestionRelacionesEntregaBI) service;
			logData = null;
			le = new LoggingEvent(ArchivoModules.TRANSFERENCIAS_MODULE, action,
					service.getServiceClient(), false);

			// Lo añadimos a la pila
			service.getLogger().add(le);
			return le;
		}

		public void addDetalleBasico(Locale locale, PrevisionVO previsionVO) {
			// si ya esta relleno el detalle básico
			if (logData != null
					&& logData
							.getDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION) != null)
				return;
			if (logData == null) {
				logData = le.getDataLoggingEvent(
						ArchivoObjects.OBJECT_PREVISION, previsionVO.getId());
			}
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
					previsionVO.getCodigoPrevision(ss));
		}

		public void addDetalleBasico(Locale locale, RelacionEntregaVO relacionVO) {
			// si ya esta relleno el detalle básico
			if (logData != null
					&& logData
							.getDetalle(ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION) != null)
				return;
			if (logData == null) {
				logData = le.getDataLoggingEvent(
						ArchivoObjects.OBJECT_RELACION, relacionVO.getId());
			}
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
					relacionVO.getCodigoRelacion(ss));
		}

		public void addDetalleBasico(Locale locale, UnidadDocumental udoc) {
			if (logData != null
					&& logData
							.getDetalle(ArchivoDetails.TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL) != null)
				return;
			if (logData == null) {
				logData = le
						.getDataLoggingEvent(ArchivoObjects.OBJECT_RELACION,
								udoc.getCodReferencia());
			}
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL,
					udoc.getCodReferencia());

		}

		public void addDetalleVacio() {
			logData = new DataLoggingEvent();
			le.getData().add(logData);
		}

		public void addNewDetalleBasico(Locale locale, PrevisionVO previsionVO) {
			logData = null;
			addDetalleBasico(locale, previsionVO);
		}

		public void addNewDetalleBasico(Locale locale,
				RelacionEntregaVO relacionVO) {
			logData = null;
			addDetalleBasico(locale, relacionVO);
		}

		public void addDetalleBasico(Locale locale, PrevisionVO previsionVO,
				String propertyTipoEdicion) {
			addDetalleBasico(locale, previsionVO);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
					Messages.getString(propertyTipoEdicion, locale));
		}

		public void addDetalleBasico(Locale locale,
				RelacionEntregaVO relacionVO, String propertyTipoEdicion) {
			addDetalleBasico(locale, relacionVO);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
					Messages.getString(propertyTipoEdicion, locale));
		}

		public void auditaCreacionPrevision(Locale locale,
				PrevisionVO previsionVO, IServiceBase service) {
			addDetalleBasico(locale, previsionVO);

			int tipoTransferencia = previsionVO.getTipotransferencia();
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_TRANSFERENCIA,
					TipoTransferencia.getTipoTransferencia(tipoTransferencia)
							.getNombre());
		}

		public void auditaModificacionPrevision(Locale locale,
				PrevisionVO prevision, PrevisionVO previsionAnterior,
				boolean auditarFondoPrevision, IServiceBase service) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
					String.valueOf(prevision.getNumuinstalacion()));
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_OBSERVACIONES_PREVISION,
					prevision.getObservaciones());

			if (auditarFondoPrevision) {
				logData.addDetalle(locale,
						ArchivoDetails.TRANSFERENCIAS_FONDO_PREVISION_PREVIO,
						previsionAnterior.getIdfondodestino());
				logData.addDetalle(locale,
						ArchivoDetails.TRANSFERENCIAS_FONDO_PREVISION,
						prevision.getIdfondodestino());
			}
		}

		public void auditaPrevisionNuevoDetalle(Locale locale,
				PrevisionVO prevision, DetallePrevisionVO detallePrevision,
				SerieVO serie, String nombreProcedimiento, IServiceBase service) {
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION,
					String.valueOf(detallePrevision.getNumeroOrden() + 1));
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_SERIE_DETALLE_PREVISION,
					serie.getTitulo());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION,
					detallePrevision.getAnoIniUdoc());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION,
					detallePrevision.getAnoFinUdoc());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_DETALLE_PREVISION,
					String.valueOf(detallePrevision.getNumUInstalacion()));

			logData.addDetalleNoVacio(
					locale,
					ArchivoDetails.TRANSFERENCIAS_PROCEDIMIENTO_DETALLE_PREVISION,
					nombreProcedimiento);
		}

		public void auditaPrevisionModificarDetalle(Locale locale,
				PrevisionVO prevision, IServiceBase service) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
					String.valueOf(prevision.getNumuinstalacion()));
		}

		public void auditaPrevisionEliminarDetalle(Locale locale,
				PrevisionVO prevision, String ordenDetalles, int nUInstalacion,
				IServiceBase service) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UIS_PREVISION,
					String.valueOf(nUInstalacion));
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION,
					ordenDetalles);
		}

		public void auditaEnviarPrevision(PrevisionVO previsionVO,
				IServiceBase service) {

		}

		public void auditaAceptarPrevision(Locale locale,
				PrevisionVO previsionVO, Date fechaIniTrans,
				Date fechaFinTrans, IServiceBase service) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
					DateUtils.formatDate(fechaIniTrans));
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
					DateUtils.formatDate(fechaFinTrans));
		}

		public void auditaRechazarPrevision(Locale locale,
				PrevisionVO previsionVO, String motivoRechazo,
				IServiceBase service) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_MOTIVO_RECHAZO, motivoRechazo);
		}

		public void auditaEliminarPrevisiones(Locale locale,
				Collection previsionesVO) {
			for (Iterator itPrevisionesVO = previsionesVO.iterator(); itPrevisionesVO
					.hasNext();) {
				PrevisionVO previsionVO = (PrevisionVO) itPrevisionesVO.next();
				addNewDetalleBasico(locale, previsionVO);
			}
		}

		public void auditaModificacionFechasPrevision(Locale locale,
				Date fechaInicio, Date fechaFin) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
					DateUtils.formatDate(fechaFin));
		}

		public void auditaBusquedaPrevisiones(Locale locale,
				BusquedaPrevisionesVO vo) {
			String tipos = AuditoriaUtils
					.transformOptionsToString(vo.getTipos(),
							servicioPrevisiones.getTiposTransferencias());
			String estados = AuditoriaUtils.transformOptionsToString(
					vo.getEstados(),
					servicioPrevisiones.getEstadosPrevisiones());

			String tituloFondo = AuditoriaUtils.getDescripcionFondoById(
					vo.getFondo(), ss);

			// auditar el campo de la busqueda si no está vacío

			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
					vo.getCodigo());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ORGANO_BUSQUEDA_PREVISION,
					vo.getOrgano());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_USUARIO_BUSQUEDA_PREVISION,
					vo.getUsuario());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_FONDO_PREVISION, tituloFondo);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ANIO_BUSQUEDA_PREVISION,
					vo.getAnio());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
					DateUtils.formatDate(vo.getFechaInicio()));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
					DateUtils.formatDate(vo.getFechaFin()));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_BUSQUEDA_PREVISION,
					tipos);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ESTADOS_BUSQUEDA_PREVISION,
					estados);

			// posiblemente pendientes de auditar:
			// String idGestor = null;
			// String [] organosUsuario = null;
			// String [] archivosReceptores = null;
		}

		public void auditaCesionControl(Locale locale, UsuarioVO usuarioAnt,
				UsuarioVO usuario) {

			if(usuarioAnt == null){
				usuarioAnt = new UsuarioVO();
			}

			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_ANTERIOR,
					usuarioAnt.getNombreCompleto());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_NUEVO,
					usuario.getNombreCompleto());
		}

		/**
		 * Cierra las previsiones que hayan caducado.
		 */
		public void auditaCerrarPrevision(Locale locale, PrevisionVO prevision) {
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_TRANSFERENCIA,
					TipoTransferencia.getTipoTransferencia(
							prevision.getTipotransferencia()).getNombre());
		}

		public void auditaCreacionRelacion(Locale locale,
				RelacionEntregaVO relacionVO) {
			addDetalleBasico(locale, relacionVO);
		}

		public void auditaNuevaUnidadDocumentalConSignatura(Locale locale,
				UnidadDocumentalVO udoc) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL,
					udoc.getNumeroExpediente());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL,
					udoc.getAsunto());
		}

		public void auditaModificacionUnidadDocumental(Locale locale,
				UnidadDocumentalVO udoc) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL,
					udoc.getId());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL,
					udoc.getNumeroExpediente());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL,
					udoc.getAsunto());
		}

		public void auditaUnidadInstalacion(Locale locale,
				UnidadInstalacionVO uinst) {
			addDetalleVacio();
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UNIDAD_INSTALACION,
					new Integer(uinst.getOrden()).toString());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_SIGNATURA_UNIDAD_INSTALACION,
					uinst.getSignaturaUI());
		}

		public void auditaUnidadInstalacion(Locale locale, String id) {
			addDetalleVacio();
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ID_UNIDAD_INSTALACION, id);
		}

		public void auditaFinalizarCorreccionErroresRelacion(
				RelacionEntregaVO relacion) {

		}

		public void auditaRecibirRelacion(Locale locale,
				boolean auditarSignatura, long signatura, int nCajas,
				String nombreUbicacion, String regEntrada,
				String nombreDeposito, String usuario) {
			if (auditarSignatura) {
				logData.addDetalle(
						locale,
						ArchivoDetails.TRANSFERENCIAS_PRIMERA_SIGNATURA_RELACION,
						String.valueOf(signatura - nCajas + 1));
				logData.addDetalle(
						locale,
						ArchivoDetails.TRANSFERENCIAS_ULTIMA_SIGNATURA_RELACION,
						String.valueOf(signatura));
			}
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_UBICACION_RELACION,
					nombreUbicacion);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_REGISTRO_ENTRADA_RELACION,
					regEntrada);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_DEPOSITO_RESERVA_RELACION,
					nombreDeposito);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_RELACION_ARCHIVO,
					usuario);
		}

		public void auditaRecibirRelacionSinDocsFisicos(Locale locale,
				String regEntrada, String usuario) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_REGISTRO_ENTRADA_RELACION,
					regEntrada);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_RELACION_ARCHIVO,
					usuario);
		}

		public void auditaEliminaRelacion(RelacionEntregaVO relacion) {

		}

		public void auditaEnviarRelacion(Locale locale, PrevisionVO prevision,
				String nExpedientes) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
					prevision.getCodigoPrevision(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_EXPEDIENTES_RELACION,
					nExpedientes);
		}

		public void auditaGuardarCotejoUnidadInstalacion(Locale locale,
				UnidadInstalacionVO unidadInstalacion) {
			// guardamos el detalle principal del evento de auditoria

			String SI = Messages.getString(Constants.ETIQUETA_SI, locale);
			String NO = Messages.getString(Constants.ETIQUETA_NO, locale);

			DataLoggingEvent data = logData;
			addDetalleVacio();
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_UI_COTEJADA,
					unidadInstalacion.getSignaturaUI());
			if (StringUtils.isNotBlank(unidadInstalacion.getNotasCotejo()))
				logData.addDetalle(locale,
						ArchivoDetails.TRANSFERENCIAS_UI_NOTAS_COTEJO,
						unidadInstalacion.getNotasCotejo());
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_UI_DEVOLUCION_FISICA,
					DBUtils.getBooleanValue(unidadInstalacion.getDevolucion()) ? SI
							: NO);
			logData = data; // lo restauramos
		}

		public void auditaGuardarCotejoParteDocumental(Locale locale,
				ParteUnidadDocumentalVO parteDoc) {
			// guardamos el detalle principal del evento de auditoria
			DataLoggingEvent data = logData;
			addDetalleVacio();
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_UDOC_CON_ERRORES,
					parteDoc.getExpediente() + " ["
							+ parteDoc.getNumParteUdoc() + '/'
							+ parteDoc.getTotalPartes() + "] "
							+ parteDoc.getAsunto() + ":"
							+ parteDoc.getNotasCotejo());
			logData = data; // lo restauramos
		}

		public void auditaCotejoRelacion(Locale locale, boolean cotejoConErrores) {
			String SI = Messages.getString(Constants.ETIQUETA_SI, locale);
			String NO = Messages.getString(Constants.ETIQUETA_NO, locale);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_COTEJO_CON_ERRORES,
					cotejoConErrores ? SI : NO);
		}

		public void auditaEliminarUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ELIMINAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaBloquearUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_BLOQUEAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaDesbloquearUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_DESBLOQUEAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaModificacionRelacion(RelacionEntregaVO relacionVO) {

		}

		public void auditaValidacionRelacion(Locale locale,
				String uDocsValidadas) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UDOCS_VALIDADAS,
					uDocsValidadas);
		}

		public void auditaUInstValidacionRelacion(Locale locale,
				String idsUnidsInst) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UINST_VALIDADAS, idsUnidsInst);
		}

		public void auditaElectronicosValidacionRelacion(Locale locale,
				String idsDocsElectronicos) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UDOCS_ELECTRONICOS_VALIDADAS,
					idsDocsElectronicos);
		}

		public void auditaModificacionDestinoRelacion(Locale locale,
				String nombreDeposito) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UBICACION_RELACION,
					nombreDeposito);
		}

		public void auditaBusquedaRelaciones(Locale locale,
				BusquedaRelacionesVO vo) {
			addDetalleVacio();

			String tipos = AuditoriaUtils.transformOptionsToString(
					vo.getTipos(),
					TransferenciasConstants.getTiposTransferencias(locale));
			String estados = AuditoriaUtils.transformOptionsToString(
					vo.getEstados(), servicioRelaciones.getEstadosRelaciones());

			// auditar el campo de la busqueda si no está vacío
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
					vo.getCodigo());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ORGANO_BUSQUEDA_RELACION,
					vo.getOrgano());
			logData.addDetalleNoVacio(
					locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_OFICINA_BUSQUEDA_RELACION,
					vo.getGestorOficina());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_GESTOR_RELACION_ARCHIVO,
					vo.getGestorArchivo());
			logData.addDetalleNoVacio(
					locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_SERIE_BUSQUEDA_RELACION,
					vo.getCodigoSerie());
			logData.addDetalleNoVacio(
					locale,
					ArchivoDetails.TRANSFERENCIAS_DENOMINACION_SERIE_BUSQUEDA_RELACION,
					vo.getSerie());
			logData.addDetalleNoVacio(
					locale,
					ArchivoDetails.TRANSFERENCIAS_PROCEDIMIENTO_BUSQUEDA_RELACION,
					vo.getProcedimiento());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_BUSQUEDA_RELACION, tipos);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ANIO_BUSQUEDA_RELACION,
					vo.getAnio());
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_ESTADOS_BUSQUEDA_RELACION,
					estados);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO,
					DateUtils.formatDate(vo.getFechaInicio()));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_FECHA_FIN_CALENDARIO,
					DateUtils.formatDate(vo.getFechaFin()));

			// posiblemente pendientes de auditar:
			// String idGestor = null;
			// String idGestorArchivo = null;
			// String [] organosUsuario = null;
			// String [] archivosReceptores = null;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @see
		 * transferencias.model.IAuditoriaTransferencias#auditaEnviarRelacionEA
		 * (transferencias.vos.RelacionEntregaVO, java.lang.String,
		 * java.lang.String)
		 */
		public void auditaEnviarRelacionEA(Locale locale,
				RelacionEntregaVO relacionEntregaVO, String numUInst,
				String numUdocsElectronicos) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
					relacionEntregaVO.getCodigoRelacion(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_CAJAS_RELACION,
					numUInst);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UDOCS_ELECTRONICAS,
					numUdocsElectronicos);
		}

	}

	public class PistaAuditoriaIngreso implements IAuditoriaTransferencias {
		private LoggingEvent le = null;
		private ServiceSession ss = null;
		private DataLoggingEvent logData = null;
		protected GestionPrevisionesBI servicioPrevisiones = null;
		protected GestionRelacionesEntregaBI servicioRelaciones = null;

		public PistaAuditoriaIngreso(int action, IServiceBase service) {
			getLogginEvent(action, service);
		}

		private LoggingEvent getLogginEvent(int action, IServiceBase service) {
			// se rellenan variables static de la clase.
			// la clase es como un contenedor de variables (globales)
			// los atributos almacenan los datos de la ultima pista de auditoria
			// creada.
			// de esta forma es posible añadir más detalles/campos a los
			// detalles.

			ss = service.getServiceSession();
			if (service instanceof GestionPrevisionesBI)
				servicioPrevisiones = (GestionPrevisionesBI) service;
			if (service instanceof GestionRelacionesEntregaBI)
				servicioRelaciones = (GestionRelacionesEntregaBI) service;
			logData = null;
			le = new LoggingEvent(ArchivoModules.FONDOS_MODULE, action,
					service.getServiceClient(), false);

			// Lo añadimos a la pila
			service.getLogger().add(le);
			return le;
		}

		public void auditaCreacionRelacion(Locale locale,
				RelacionEntregaVO relacionVO) {
			addDetalleBasico(locale, relacionVO);
		}

		public void addDetalleBasico(Locale locale, RelacionEntregaVO relacionVO) {
			// si ya esta relleno el detalle básico
			if (logData != null
					&& logData.getDetalle(ArchivoDetails.FONDO_CODIGO_INGRESO) != null)
				return;
			if (logData == null) {
				logData = le.getDataLoggingEvent(ArchivoObjects.OBJECT_INGRESO,
						relacionVO.getId());
			}
			logData.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_INGRESO,
					relacionVO.getCodigoRelacion(ss));
		}

		public void addDetalleBasico(Locale locale,
				RelacionEntregaVO relacionVO, String propertyTipoEdicion) {
			addDetalleBasico(locale, relacionVO);
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_TIPO_EDICION,
					Messages.getString(propertyTipoEdicion, locale));
		}

		public void auditaNuevaUnidadDocumentalConSignatura(Locale locale,
				UnidadDocumentalVO udoc) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL,
					udoc.getNumeroExpediente());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL,
					udoc.getAsunto());
		}

		public void auditaModificacionUnidadDocumental(Locale locale,
				UnidadDocumentalVO udoc) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL,
					udoc.getId());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL,
					udoc.getNumeroExpediente());
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL,
					udoc.getAsunto());
		}

		public void addNewDetalleBasico(Locale locale,
				RelacionEntregaVO relacionVO) {
			logData = null;
			addDetalleBasico(locale, relacionVO);
		}

		public void auditaEliminarUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_ELIMINAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaBloquearUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_BLOQUEAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaDesbloquearUnidadDocumental(Locale locale,
				String udocsBorradas) {
			logData.addDetalle(
					locale,
					ArchivoDetails.TRANSFERENCIAS_DESBLOQUEAR_UNIDAD_DOCUMENTAL,
					udocsBorradas);
		}

		public void auditaModificacionDestinoRelacion(Locale locale,
				String nombreDeposito) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UBICACION_RELACION,
					nombreDeposito);
		}

		public void auditaEnviarRelacion(Locale locale, PrevisionVO prevision,
				String nExpedientes) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_PREVISION,
					prevision.getCodigoPrevision(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_EXPEDIENTES_RELACION,
					nExpedientes);
		}

		public void auditaEnviarSeleccionarUbicacionIngresoDirecto(
				Locale locale, RelacionEntregaVO ingreso, String nExpedientes) {
			logData.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_INGRESO,
					ingreso.getCodigoRelacion(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_EXPEDIENTES_RELACION,
					nExpedientes);
		}

		public void auditaSignaturarUbicarValidarIngresoDirecto(Locale locale,
				RelacionEntregaVO ingreso, String uDocsValidadas) {
			logData.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_INGRESO,
					ingreso.getCodigoRelacion(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_UDOCS_VALIDADAS,
					uDocsValidadas);
		}

		/*
		 * (sin Javadoc)
		 *
		 * @see
		 * transferencias.model.IAuditoriaTransferencias#auditaEnviarRelacionEA
		 * (transferencias.vos.RelacionEntregaVO, java.lang.String,
		 * java.lang.String)
		 */
		public void auditaEnviarRelacionEA(Locale locale,
				RelacionEntregaVO relacionEntregaVO, String numUInst,
				String numUdocsElectronicos) {
			logData.addDetalle(locale,
					ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
					relacionEntregaVO.getCodigoRelacion(ss));
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_CAJAS_RELACION,
					numUInst);
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.TRANSFERENCIAS_NUMERO_UDOCS_ELECTRONICAS,
					numUdocsElectronicos);
		}

		public void auditaUnidadInstalacion(Locale locale,
				UnidadInstalacionVO uinst) {
		}

		public void auditaUnidadInstalacion(Locale locale, String id) {
		}

		public void auditaUInstValidacionRelacion(Locale locale,
				String idsDocsValidados) {
		}

		public void auditaElectronicosValidacionRelacion(Locale locale,
				String idsDocsElectronicos) {
		}
	}

}
