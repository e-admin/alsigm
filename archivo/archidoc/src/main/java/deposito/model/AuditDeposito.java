package deposito.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import transferencias.vos.RelacionEntregaVO;
import util.CollectionUtils;
import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.Messages;
import common.bi.IServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;

import deposito.DepositoConstants;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.vos.DepositoElectronicoVO;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoVO;
import deposito.vos.TipoElementoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;

/**
 * Utilidad de generación de eventos y datos de auditoria del módulo de Depósito
 */
public class AuditDeposito {

	private static GestorEstructuraDeposito gestor = null;

	/**
	 * Genera un evento de auditoria para una determinada acción a auditar
	 * llevada a cabo por una instancia de servicio
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @param action
	 *            Acción a auditar {@link ArchivoActions}
	 * @return Evento de auditoria
	 */
	private static LoggingEvent getLogginEvent(IServiceBase serviceToAudit,
			int action) {

		LoggingEvent event = new LoggingEvent(ArchivoModules.DEPOSITOS_MODULE,
				action, serviceToAudit.getServiceClient(), false);
		if (serviceToAudit != null
				&& serviceToAudit instanceof GestorEstructuraDeposito) {
			gestor = (GestorEstructuraDeposito) serviceToAudit;
		}
		serviceToAudit.getLogger().add(event);

		return event;
	}

	/**
	 * Genera el evento de auditoria correspondiente a la eliminación de una
	 * unidad de instalación del deposito fisico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventBajaUinstalacion(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_BAJA_UI);
	}

	/**
	 * Genera el evento de auditoria correspondiente a la eliminación de una
	 * unidad de instalación del deposito fisico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventLiberaUinstalacion(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_BAJA_UI);
	}

	/**
	 * Incorpora el identificador unidad de instalación como dato de un evento
	 * de auditoria
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 */
	public static void addDataLogInfoUInstalacion(LoggingEvent event,
			String idUInstalacion) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_UNIDAD_INSTALACION,
				idUInstalacion);
	}

	/**
	 * Incorpora la información referente a un hueco del depósito físico como
	 * dato de un evento de auditoria
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param hueco
	 *            Datos de hueco
	 */
	public static void addDataLogInfoHueco(LoggingEvent event, HuecoVO hueco) {
		String huecoID = new StringBuffer(hueco.getHuecoID().getIdpadre())
				.append("/").append(hueco.getHuecoID().getNumorden())
				.toString();
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_HUECO, huecoID);
	}

	/**
	 * Genera el evento de auditoria correspondiente a la modificación de un
	 * elemento del deposito fisico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventModificarElemento(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * modificación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogModificarElemento(Locale locale,
			LoggingEvent event, ElementoNoAsignableVO elementoPadre,
			ElementoVO elemento) {
		addDataLogElemento(locale, event, elementoPadre, elemento);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * modificación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	private static void addDataLogElemento(Locale locale, LoggingEvent event,
			ElementoNoAsignableVO elementoPadre, ElementoVO elemento) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELEMENTO_CUADRO, elemento.getId());

		addDataLogElementoPadre(locale, data, elementoPadre);
		addDataLogElemento(locale, data, elemento);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * modificación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	private static void addDataLogElementoPadre(Locale locale,
			DataLoggingEvent data, ElementoNoAsignableVO elementoPadre) {
		// Detalles del padre
		if (elementoPadre != null) {
			if (elementoPadre.getId() != null)
				data.addDetalle(locale,
						ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_ID_PADRE,
						elementoPadre.getId());
			if (elementoPadre.getNombre() != null)
				data.addDetalle(locale,
						ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_NOMBRE_PADRE,
						elementoPadre.getNombre());
		}
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * modificación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	private static void addDataLogElemento(Locale locale,
			DataLoggingEvent data, ElementoVO elemento) {
		String idTipoElemento = elemento.getIdTipoElemento();

		if (elemento.getId() != null)
			data.addDetalle(locale, ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_ID,
					elemento.getId());
		if (StringUtils.isEmpty(elemento.getIdpadre())) { // UBICACION
			if (gestor != null) {
				TipoElementoVO tipo = gestor
						.getTipoElementoSingleton(idTipoElemento);
				if (tipo != null) {
					data.addDetalle(locale,
							ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_TIPO,
							tipo.getNombre());
				}
			}

		}

		if (elemento.getNombre() != null)
			data.addDetalle(locale,
					ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_NOMBRE,
					elemento.getNombre());
		if (elemento.getPathName() != null)
			data.addDetalle(locale,
					ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_UBICACION,
					elemento.getPathName());
		// if (elemento.getDeposito()!=null &&
		// elemento.getDeposito().getNombre()!=null)
		// data.addDetalle(ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_ID_DEPOSITO,elemento.getDeposito().getNombre());
		// if (elemento.getTipoElemento()!=null &&
		// elemento.getTipoElemento().getNombre()!=null)
		// data.addDetalle(ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_TIPO,elemento.getTipoElemento().getNombre());
		if (elemento.isAsignable()) { // Si es un elemento asignable
			ElementoAsignableVO asignable = (ElementoAsignableVO) elemento;
			String tipoFormato = null;
			if (gestor != null) {
				tipoFormato = gestor.getFormatoHueco(asignable.getIdFormato())
						.getNombre();
				data.addDetalle(locale,
						ArchivoDetails.DEPOSITO_ELEMENTO_TIPO_FORMATO,
						tipoFormato);
			}

			data.addDetalle(locale, ArchivoDetails.DEPOSITO_ELEMENTO_LONGITUD,
					"" + asignable.getLongitud());
			data.addDetalle(locale,
					ArchivoDetails.DEPOSITO_ELEMENTO_NUM_HUECOS,
					"" + asignable.getNumhuecos());
		}
	}

	/**
	 * Genera el evento de auditoria correspondiente a la eliminación de un
	 * elemento del deposito fisico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventBajaElemento(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * eliminación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogBajaElemento(Locale locale,
			LoggingEvent event, ElementoVO elemento) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELEMENTO_CUADRO, elemento.getId());

		addDataLogElemento(locale, data, elemento);
	}

	/**
	 * Genera el evento de auditoria correspondiente a la creación de un
	 * elemento del deposito fisico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventAltaElemento(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * creación de elemento del deposito
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogAltaElemento(Locale locale,
			LoggingEvent event, ElementoNoAsignableVO elementoPadre,
			String idTipoElemento, List elementos) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELEMENTO_CUADRO, null);

		// Detalles de cada elemento
		if (elementos != null) {
			for (Iterator it = elementos.iterator(); it.hasNext();) {
				ElementoVO elemento = (ElementoVO) it.next();
				addDataLogElementoPadre(locale, data, elementoPadre);
				addDataLogElemento(locale, data, elemento);
				if (it.hasNext())
					data = event.getDataLoggingEvent(
							ArchivoObjects.OBJECT_ELEMENTO_CUADRO, null);
			}
		}
	}

	/**
	 * Genera el evento de auditoria correspondiente a la reserva de un hueco en
	 * el depósito
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLoggingEventReservaHuecos(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_RESERVA_HUECO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * reserva de hueco
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogReservaHuecos(Locale locale,
			LoggingEvent event, RelacionEntregaVO relacionEntrega,
			List huecosIDAReservar) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_RELACION, relacionEntrega.getId());
		data.addDetalle(locale, ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
				getCodigoRelacion(relacionEntrega));
		data.addDetalle(locale, ArchivoDetails.TRANSFERENCIAS_ESTADO_RESERVA,
				Messages.getString(
						DepositoConstants.DEPOSITO_ESTADO_RESERVA_RESERVADA,
						locale));

		if (huecosIDAReservar != null) {
			data.addDetalle(locale, ArchivoDetails.DEPOSITO_HUECOS_RESERVADOS,
					"" + huecosIDAReservar.size());

			for (int i = 0; i < huecosIDAReservar.size(); i++) {
				HuecoVO hueco = (HuecoVO) huecosIDAReservar.get(i);
				if (hueco.getPath() != null)
					data.addDetalleNum(locale,
							ArchivoDetails.DEPOSITO_HUECO_RESERVADO, " "
									+ (i + 1), hueco.getPath());
			}
		}
	}

	public static String getCodigoRelacion(RelacionEntregaVO relacionEntrega) {
		String codigo = null;
		if (gestor != null)
			codigo = relacionEntrega.getCodigoRelacion(gestor
					.getServiceSession());
		else
			codigo = relacionEntrega.getCodigoRelacion();
		return codigo;
	}

	/**
	 * Genera el evento de auditoria correspondiente a la reserva de un hueco en
	 * el depósito
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLoggingEventRechazarReservaHuecos(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_RESERVA_HUECO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * reserva de hueco
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogRechazarReservaHuecos(Locale locale,
			LoggingEvent event, RelacionEntregaVO relacionEntrega) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_RELACION, relacionEntrega.getId());
		data.addDetalle(locale, ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
				getCodigoRelacion(relacionEntrega));
		data.addDetalle(
				locale,
				ArchivoDetails.TRANSFERENCIAS_ESTADO_RESERVA,
				Messages.getString(
						DepositoConstants.DEPOSITO_ESTADO_RESERVA_NO_SE_HA_PODIDO,
						locale));
	}

	/**
	 * Genera el evento de auditoria correspondiente a la modificación del
	 * estado de un hueco
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventModificarHueco(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * modificación del estado de un hueco
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogModificarHueco(Locale locale,
			LoggingEvent event, String huecoPath, String estadoAnterior,
			String estadoNuevo) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_HUECO, null);
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_ELEMENTO_CUADRO_NOMBRE,
				huecoPath);

		String estadoAnteriorNombre;
		String estadoNuevoNombre;

		if (HuecoVO.OCUPADO_STATE.equals(estadoAnterior))
			estadoAnteriorNombre = Messages.getString(
					DepositoConstants.DEPOSITO_OCUPADO, locale);
		else if (HuecoVO.RESERVADO_STATE.equals(estadoAnterior))
			estadoAnteriorNombre = Messages.getString(
					DepositoConstants.DEPOSITO_RESERVADO, locale);
		else if (HuecoVO.INUTILIZADO_STATE.equals(estadoAnterior))
			estadoAnteriorNombre = Messages.getString(
					DepositoConstants.DEPOSITO_INUTILIZADO, locale);
		else
			estadoAnteriorNombre = Messages.getString(
					DepositoConstants.DEPOSITO_LIBRE, locale);

		if (HuecoVO.OCUPADO_STATE.equals(estadoNuevo))
			estadoNuevoNombre = Messages.getString(
					DepositoConstants.DEPOSITO_OCUPADO, locale);
		else if (HuecoVO.RESERVADO_STATE.equals(estadoNuevo))
			estadoNuevoNombre = Messages.getString(
					DepositoConstants.DEPOSITO_RESERVADO, locale);
		else if (HuecoVO.INUTILIZADO_STATE.equals(estadoNuevo))
			estadoNuevoNombre = Messages.getString(
					DepositoConstants.DEPOSITO_INUTILIZADO, locale);
		else
			estadoNuevoNombre = Messages.getString(
					DepositoConstants.DEPOSITO_LIBRE, locale);

		data.addDetalle(locale, ArchivoDetails.DEPOSITO_HUECO_ESTADO_ANTERIOR,
				estadoAnteriorNombre);
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_HUECO_ESTADO,
				estadoNuevoNombre);
	}

	/**
	 * Genera el evento de auditoria correspondiente a la ubicación de una
	 * relación de entrega
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLoggingEventUbicarRelacion(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_UBICAR_RELACION);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * ubicación de una relación de entrega
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */

	public static void addDataLogUbicarRelacion(Locale locale,
			LoggingEvent event, RelacionEntregaVO relacionEntrega,
			List huecosReservadosAOcupar, List huecosLibresAOcupar) {

		List huecosAOcupar = new ArrayList();

		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_RELACION, relacionEntrega.getId());
		data.addDetalle(locale, ArchivoDetails.TRANSFERENCIAS_CODIGO_RELACION,
				getCodigoRelacion(relacionEntrega));

		if (huecosReservadosAOcupar != null)
			huecosAOcupar.addAll(huecosReservadosAOcupar);
		if (huecosLibresAOcupar != null)
			huecosAOcupar.addAll(huecosLibresAOcupar);

		data.addDetalle(locale, ArchivoDetails.DEPOSITO_HUECOS_OCUPADOS, ""
				+ huecosAOcupar.size());

		for (int i = 0; i < huecosAOcupar.size(); i++) {
			DetalleUbicacion detalleUbicacion = (DetalleUbicacion) huecosAOcupar
					.get(i);
			if (detalleUbicacion.getHueco() != null)
				data.addDetalleNum(locale,
						ArchivoDetails.DEPOSITO_HUECO_OCUPADO, " " + (i + 1),
						detalleUbicacion.getHueco().getPath());
		}
	}

	/**
	 * Genera el evento de auditoria correspondiente a la reubicación de
	 * unidades de instalacion dentro del depósito físico
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLoggingEventReubicarUI(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_REUBICAR_UI);
	}

	public static LoggingEvent getLoggingEventReubicarUDocs(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_REUBICAR_UDOCS);
	}

	/**
	 * Incorpora la información a registrar para el evento de auditoria de
	 * reubicación de unidades de instalacion dentro del depósito físico
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param elemento
	 *            Datos del elemento del deposito
	 */
	public static void addDataLogReubicarUI(Locale locale, LoggingEvent event,
			List huecosOrigen, List huecosDestino) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_NULL, null);
		if (huecosOrigen != null) {
			data.addDetalle(locale, ArchivoDetails.DEPOSITO_UIS_REUBICAR, ""
					+ huecosOrigen.size());

			for (int i = 0; i < huecosOrigen.size(); i++) {
				data = event.getDataLoggingEvent(ArchivoObjects.OBJECT_NULL,
						null);

				HuecoVO huecoOrigen = (HuecoVO) huecosOrigen.get(i);
				HuecoVO huecoDestino = (HuecoVO) huecosDestino.get(i);

				if (huecoOrigen.getPath() != null
						&& huecoDestino.getPath() != null) {
					data.addDetalleNum(locale,
							ArchivoDetails.DEPOSITO_UNIDAD_INSTALACION, " "
									+ (i + 1), "");
					data.addDetalle(
							locale,
							"<![CDATA[    ]]>"
									+ Messages.getString(
											ArchivoDetails.DEPOSITO_SIGNATURA,
											locale), huecoOrigen
									.getSignaturaUI());
					data.addDetalle(
							locale,
							"<![CDATA[    ]]>"
									+ Messages
											.getString(
													ArchivoDetails.DEPOSITO_ORIGEN_REHUBICACION,
													locale), huecoOrigen
									.getPath());
					data.addDetalle(
							locale,
							"<![CDATA[    ]]>"
									+ Messages
											.getString(
													ArchivoDetails.DEPOSITO_DESTINO_REHUBICACION,
													locale), huecoDestino
									.getPath());
				}
			}
		}

	}

	public static DataLoggingEvent addDataLogReubicarUDocs(Locale locale,
			LoggingEvent event, GestorEstructuraDeposito depositoService,
			List udocsOrigen, UInsDepositoVO cajaDestino) {

		if (event != null) {

			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_NULL, null);

			// obtener caja orgien para la auditoria
			UInsDepositoVO cajaOrigen = null;
			if (!CollectionUtils.isEmpty(udocsOrigen)) {
				// se supone q todas la udocs vienen de la misma caja
				UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) udocsOrigen
						.get(0);
				cajaOrigen = depositoService._unidadInstalacionDepositoDBEntity
						.getUInstDepositoVOXIdEnDeposito(udoc
								.getIduinstalacion());
			}

			if (udocsOrigen != null) {
				data.addDetalle(locale, ArchivoDetails.DEPOSITO_UDOCS_REUBICAR,
						"" + udocsOrigen.size());
				data.addDetalle(locale, ArchivoDetails.DEPOSITO_UI_ORIGEN, ""
						+ cajaOrigen.getSignaturaui());
				data.addDetalle(locale, ArchivoDetails.DEPOSITO_UI_DESTINO, ""
						+ cajaDestino.getSignaturaui());
			}
			return data;
		}

		return null;
	}

	public static void addDetalleLogReubicarUDocs(Locale locale,
			DataLoggingEvent data, int numeroDetalle, String signaturaOriginal,
			String nuevaSignatura) {
		data.addDetalleNum(locale, ArchivoDetails.DEPOSITO_SIGNATURA_ORIGINAL,
				" " + (numeroDetalle + 1), signaturaOriginal);
		data.addDetalleNum(locale, ArchivoDetails.DEPOSITO_SIGNATURA_NUEVA, " "
				+ (numeroDetalle + 1), nuevaSignatura);
	}

	public static void addDetalleLogEliminarUInstalacion(Locale locale,
			DataLoggingEvent data,
			IUInstalacionDepositoDBEntity _unidadInstalacionDBEntity,
			String idUinstalacionAEliminar) {
		UInsDepositoVO uInsAEliminar = _unidadInstalacionDBEntity
				.getUInstDepositoVOXIdEnDeposito(idUinstalacionAEliminar);
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_UINS_ELIMINADA,
				uInsAEliminar.getSignaturaui());
	}

	public static LoggingEvent getLoggingEventDeposito(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_CONSULTA_DEPOSITO);
	}

	public static void addDataLogConsultaDeposito(Locale locale,
			LoggingEvent event, DepositoVO deposito) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DEPOSITO, deposito.getId());

		data.addDetalle(locale, ArchivoDetails.DEPOSITO_DEPOSITO_NOMBRE,
				deposito.getNombre());
	}

	/**
	 * Genera el evento de auditoria correspondiente al alta de un depósito
	 * electrónico.
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventAltaDepElec(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO);
	}

	/**
	 * Incorpora el depósito electrónico como dato de un evento de auditoria
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param deposito
	 *            Información del depósito electrónico creado.
	 */
	public static void addDataLogDepElec(Locale locale, LoggingEvent event,
			DepositoElectronicoVO deposito) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DEPOSITO, deposito.getId());

		data.addDetalle(locale, ArchivoDetails.DEPOSITO_DEPOSITO_ID_EXT,
				deposito.getIdExt());
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_DEPOSITO_NOMBRE,
				deposito.getNombre());
		data.addDetalle(locale, ArchivoDetails.DEPOSITO_DEPOSITO_DESCRIPCION,
				deposito.getDescripcion());
	}

	/**
	 * Genera el evento de auditoria correspondiente a la modificación de un
	 * depósito electrónico.
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventModificacionDepElec(
			IServiceBase serviceToAudit) {
		return getLogginEvent(
				serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO);
	}

	/**
	 * Genera el evento de auditoria correspondiente a la baja de un depósito
	 * electrónico.
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEventBajaDepElec(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO);
	}

	public static LoggingEvent getLogginEventNuevoFormato(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_ALTA_FORMATO);
	}

	public static LoggingEvent getLogginEventModificarFormato(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_FORMATO);
	}

	public static LoggingEvent getLogginEventEliminarFormato(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.DEPOSITOS_MODULE_BAJA_FORMATO);
	}

	public static DataLoggingEvent addDataLogEliminarUIVacia(Locale locale,
			LoggingEvent event, GestorEstructuraDeposito depositoService,
			UInsDepositoVO cajaOrigen, String ubicacion) {

		if (event != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_NULL, null);
			data.addDetalle(
					locale,
					ArchivoDetails.DEPOSITO_TIPO_ELIMINACION,
					""
							+ Messages
									.getString(ArchivoDetails.DEPOSITO_UNIDAD_INSTALACION));
			data.addDetalle(locale, ArchivoDetails.DEPOSITO_UNIDAD_INSTALACION,
					"" + cajaOrigen.getSignaturaui());
			data.addDetalle(locale, ArchivoDetails.DEPOSITO_UI_ARCHIVO, ""
					+ ubicacion);
			return data;
		}
		return null;
	}
}
