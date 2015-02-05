package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: ConsultaExpedientesWebService.java,v 1.3.2.2 2008/03/28 14:03:35 jnogales Exp $
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

public class ConsultaExpedientesWebService {

	private static final Logger logger = Logger.getLogger(ConsultaExpedientesWebService.class);

	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_QUERY_EXPS;

	public Expedientes consultarExpedientesNIF(CriterioBusquedaExpedientes poNif, Entidad entidad){
		try {
			Expedientes oExps = getExpedientesWS(
						getServicioConsultaExpedientes().consultarExpedientesNIF(poNif.getNIF(), entidad)
						);
			return (Expedientes)ServiciosUtils.completeReturnOK(oExps);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en consulta de expedientes por NIF.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en consulta de expedientes por NIF.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado en consulta de expedientes por NIF.", e);
			return (Expedientes)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Expedientes()));
		}
	}

	public Expedientes consultarExpedientes(CriterioBusquedaExpedientes poCriterio, Entidad entidad){
		try {
			Expedientes oExps = getExpedientesWS(
						getServicioConsultaExpedientes().consultarExpedientes(
								getCriterioBusquedaExpedientesServicio(poCriterio), entidad)
						);
			return (Expedientes)ServiciosUtils.completeReturnOK(oExps);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en consulta de expedientes.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en consulta de expedientes.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado en consulta de expedientes.", e);
			return (Expedientes)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Expedientes()));
		}
	}

	public Url obtenerURLAportacionExpedientes(){
		try {
			Url oUrl = new Url();
			oUrl.setUrl(getServicioConsultaExpedientes().obtenerURLAportacionExpedientes());
			return (Url)ServiciosUtils.completeReturnOK(oUrl);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo URL de aportación a expediente.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo URL de aportación a expediente.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo URL de aportación a expediente.", e);
			return (Url)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Url()));
		}
	}

	public Url obtenerURLNotificacionExpedientes (){
		try {
			Url oUrl = new Url();
			oUrl.setUrl(getServicioConsultaExpedientes().obtenerURLNotificacionExpedientes());
			return (Url)ServiciosUtils.completeReturnOK(oUrl);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo URL de notificación a expediente.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo URL de notificación a expediente.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo URL de notificación a expediente.", e);
			return (Url)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Url()));
		}
	}

	public Url obtenerURLPagoTasas(){
		try {
			Url oUrl = new Url();
			oUrl.setUrl(getServicioConsultaExpedientes().obtenerURLPagoTasas());
			return (Url)ServiciosUtils.completeReturnOK(oUrl);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo URL de pago de tasas.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo URL de pago de tasas.", e);
			return (Url)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Url()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo URL de pago de tasas.", e);
			return (Url)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Url()));
		}
	}

	public Expediente obtenerDetalle(Expediente poNumExpediente, Entidad entidad){
		try {
			Expediente oExp = getExpedienteWS(
						getServicioConsultaExpedientes().obtenerDetalle(poNumExpediente.getNumero(), entidad)
						);
			return (Expediente)ServiciosUtils.completeReturnOK(oExp);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en obteniendo detalle de expediente.", e);
			return (Expediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expediente()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en obteniendo detalle de expediente.", e);
			return (Expediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expediente()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado en obteniendo detalle de expediente.", e);
			return (Expediente)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Expediente()));
		}
	}

	public HitosExpediente obtenerHistoricoExpediente(Expediente poNumExpediente, Entidad entidad){
		try {
			HitosExpediente oHitos = getHitosExpedienteWS(
						getServicioConsultaExpedientes()
							.obtenerHistoricoExpediente(poNumExpediente.getNumero(), entidad)
					);
			return (HitosExpediente)ServiciosUtils.completeReturnOK(oHitos);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo histótico de expediente.", e);
			return (HitosExpediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new HitosExpediente()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo histótico de expediente.", e);
			return (HitosExpediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new HitosExpediente()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo histótico de expediente.", e);
			return (HitosExpediente)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new HitosExpediente()));
		}
	}

	public HitoExpediente obtenerHitoEstado(Expediente poNumExpediente, Entidad entidad){
		try {
			HitoExpediente oHito = getHitoExpedienteWS(
							getServicioConsultaExpedientes().obtenerHitoEstado(poNumExpediente.getNumero(), entidad)
							);
			return (HitoExpediente)ServiciosUtils.completeReturnOK(oHito);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo histótico de expediente.", e);
			return (HitoExpediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new HitoExpediente()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo histótico de expediente.", e);
			return (HitoExpediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new HitoExpediente()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo histótico de expediente.", e);
			return (HitoExpediente)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new HitoExpediente()));
		}
	}

	public FicherosHito obtenerFicherosHito(HitoExpediente poGuidHito, Entidad entidad){
		try {
			FicherosHito oFichs = getFicherosHitoWS(
							getServicioConsultaExpedientes().obtenerFicherosHito(poGuidHito.getGuid(), entidad)
						);
			return (FicherosHito)ServiciosUtils.completeReturnOK(oFichs);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo ficheros de hito.", e);
			return (FicherosHito)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new FicherosHito()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo ficheros de hito.", e);
			return (FicherosHito)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new FicherosHito()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo ficheros de hito.", e);
			return (FicherosHito)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new FicherosHito()));
		}
	}

	public FicherosHitos obtenerFicherosHitos(HitosExpediente poHitos, Entidad entidad){
		try {
			FicherosHitos oFichs =
				getFicherosHitosWS(
					getServicioConsultaExpedientes().obtenerFicherosHitos(
						getHitosExpedienteServicio(poHitos), entidad
					)
				);
			return (FicherosHitos)ServiciosUtils.completeReturnOK(oFichs);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo ficheros de hitos.", e);
			return (FicherosHitos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new FicherosHitos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo ficheros de hitos.", e);
			return (FicherosHitos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new FicherosHitos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo ficheros de hitos.", e);
			return (FicherosHitos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new FicherosHitos()));
		}
	}

	public RetornoServicio nuevoExpediente(Expediente poExpediente, Interesado poInteresado, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.nuevoExpediente(getExpedienteServicio(poExpediente), getInteresadoServicio(poInteresado), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error creando nuevo expediente.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error creando nuevo expediente.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error creando nuevo expediente.", e);
			return ServiciosUtils.createReturnError();

		}
	}

	public RetornoServicio eliminarExpediente(Expediente poExpediente, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.eliminarExpediente(getExpedienteServicio(poExpediente), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error eliminando expediente.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error eliminando expediente.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado eliminando expediente.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio nuevoInteresado(Interesado poInteresado, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.nuevoInteresado(getInteresadoServicio(poInteresado), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error creando interesado.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error creando interesado.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado creando interesado.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarInteresado(Interesado poInteresado, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.eliminarInteresado(getInteresadoServicio(poInteresado), entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error eliminando interesado.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error eliminando interesado.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado eliminando interesado.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarInteresadoExpediente(String numeroExpediente, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.eliminarInteresadoExpediente(numeroExpediente, entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error eliminando interesado de expedeinte.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error eliminando interesado de expedeinte.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado eliminando interesado de expedeinte.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio establecerHitoActual(HitoExpediente poHito, FicherosHito poFicheros, Historico poHistorico, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.establecerHitoActual(
							getHitoExpedienteServicio(poHito),
							getFicherosHitoServicio(poFicheros),
							getHistoricoServicio(poHistorico),
							entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error estableciendo hito actual.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error estableciendo hito actual.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado estableciendo hito actual.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarHitoActual(Expediente poNumExpediente, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.eliminarHitoActual(poNumExpediente.getNumero(), entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error eliminando hito actual.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error eliminando hito actual.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado eliminando hito actual.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio nuevoHitoHistorico(HitoExpediente poHito, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.nuevoHitoHistorico(getHitoExpedienteServicio(poHito), entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error creando nuevo hito histórico.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error creando nuevo hito histórico.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado creando nuevo hito histórico.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarHitoHistorico(HitoExpediente poHito, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.eliminarHitoHistorico(poHito.getNumeroExpediente(), entidad);
				return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error eliminando hito histórico.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error eliminando hito histórico.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado eliminando hito histórico.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public InfoDocumento cargarDocumento(InfoDocumento poInfodoc, Entidad entidad){
		try {
			InfoDocumento oInfo = new InfoDocumento();
			oInfo.setContent(
					getServicioConsultaExpedientes().cargarDocumento(poInfodoc.getGuid(), entidad)
			);
			return (InfoDocumento)ServiciosUtils.completeReturnOK(oInfo);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error cargando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error cargando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado cargando documento.", e);
			return (InfoDocumento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new InfoDocumento()));
		}
	}

	public InfoDocumento recogerDocumento(InfoDocumento poInfodoc, Entidad entidad){
		try {
			InfoDocumento oInfo =
				getInfoDocumentoWS(
						getServicioConsultaExpedientes().recogerDocumento(poInfodoc.getGuid(), entidad)
				);
			return (InfoDocumento)ServiciosUtils.completeReturnOK(oInfo);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error recogiendo documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error recogiendo documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado recogiendo documento.", e);
			return (InfoDocumento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new InfoDocumento()));
		}
	}

	public Expedientes busquedaExpedientes(CriterioBusquedaExpedientes poCriterio, Entidad entidad){
		try {
			Expedientes oExps =
				getExpedientesWS(
						getServicioConsultaExpedientes()
							.busquedaExpedientes(getCriterioBusquedaExpedientesServicio(poCriterio), entidad)
				);
			return (Expedientes)ServiciosUtils.completeReturnOK(oExps);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error buscando expedientes.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error buscando expedientes.", e);
			return (Expedientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expedientes()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado buscando expedientes.", e);
			return (Expedientes)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Expedientes()));
		}
	}

	public Expediente busquedaExpediente(Expediente poExpediente, Entidad entidad){
		try {
			Expediente oExp = getExpedienteWS(
					getServicioConsultaExpedientes()
					.busquedaExpediente(poExpediente.getNif(),
							poExpediente.getNumero(), entidad)
						);

			if (oExp == null) {
				oExp = new Expediente();
			}
			return (Expediente)ServiciosUtils.completeReturnOK(oExp);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en obteniendo detalle de expediente.", e);
			return (Expediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expediente()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en obteniendo detalle de expediente.", e);
			return (Expediente)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Expediente()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado en obteniendo detalle de expediente.", e);
			return (Expediente)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Expediente()));
		}
	}

	public RetornoServicio anexarFicherosHitoActual(FicherosHito poFicheros, Entidad entidad){
		try {
			getServicioConsultaExpedientes().anexarFicherosHitoActual(getFicherosHitoServicio(poFicheros), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error anexando ficheros al hito actual.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error anexando ficheros al hito actual.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado anexando ficheros al hito actual.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	/**
	 * Método que utiliza el servicio de notificaciones de SIGEM para conocer si el expediente tiene alguna notificación en curso.
	 * @param NIF
	 * @param numeroExpediente
	 * @return
	 * @throws ConsultaExpedientesException
	 */
	public NotificacionesPendientes recogerNotificaciones(Expediente poExpediente, Entidad entidad){
		try {
			NotificacionesPendientes oNotificaciones =
				getNotificacionesPendientesWS(
						getServicioConsultaExpedientes()
							.recogerNotificaciones(poExpediente.getNif(),
									poExpediente.getNumero(), entidad)
				);
			return (NotificacionesPendientes)ServiciosUtils.completeReturnOK(oNotificaciones);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error recogiendo documento.", e);
			return (NotificacionesPendientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new NotificacionesPendientes()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error recogiendo documento.", e);
			return (NotificacionesPendientes)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new NotificacionesPendientes()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado recogiendo documento.", e);
			return (NotificacionesPendientes)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new NotificacionesPendientes()));
		}
	}

	/**
	 * Método que da de alta y asocia al hito actual un nueva solicitud de subsanación.
	 * Se utiliza desde la tramitación de expedientes para publicar en la consulta de expedientes
	 * del ciudadano la necesidad de aportar nueva documentación al expediente.
	 * La publicación de esta solicitud de subsanación suele ir acompañada de una notificación previa.
	 * @param poSubsanacion Objeto que encapsula los datos de la subsanación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public RetornoServicio altaSolicitudSubsanacion(Subsanacion poSubsanacion, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.altaSolicitudSubsanacion(getSubsanacionServicio(poSubsanacion), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error creando solicitud de subsanación.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error creando solicitud de subsanación.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado creando solicitud de subsanación.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Subsanaciones obtenerSubsanacionesHitoActual(Expediente poExpediente, Entidad entidad){
		try {
			Subsanaciones oSubs =
				getSubsanacionesWS(
						getServicioConsultaExpedientes()
							.obtenerSubsanacionesHitoActual(poExpediente.getNumero(), entidad)
				);
			return (Subsanaciones)ServiciosUtils.completeReturnOK(oSubs);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo subsanaciones del hito actual.", e);
			return (Subsanaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Subsanaciones()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo subsanaciones del hito actual.", e);
			return (Subsanaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Subsanaciones()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo subsanaciones del hito actual.", e);
			return (Subsanaciones)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Subsanaciones()));
		}
	}

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @param guidHito Identificador del hito del que se quiere recuperar la lista de solicitudes de subsanación
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Subsanaciones obtenerSubsanacionesHito(HitoExpediente poHitoExp, Entidad entidad){
		try {
			Subsanaciones oSubs =
				getSubsanacionesWS(
						getServicioConsultaExpedientes()
							.obtenerSubsanacionesHito(poHitoExp.getGuid(), entidad)
				);
			return (Subsanaciones)ServiciosUtils.completeReturnOK(oSubs);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo subsanaciones del hito.", e);
			return (Subsanaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Subsanaciones()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo subsanaciones del hito.", e);
			return (Subsanaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Subsanaciones()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo subsanaciones del hito.", e);
			return (Subsanaciones)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Subsanaciones()));
		}
	}

	/**
	 * Método que da de alta y asocia al hito actual del procedimiento un solicitud de realización de pago
	 * y aportación de documentación al expediente.
	 * @param poPago Objeto con los datos del pago.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public RetornoServicio altaSolicitudPago(Pago poPago, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.altaSolicitudPago(
						getPagoServicio(poPago), entidad
				);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en alta de solicitud de pago.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error en alta de solicitud de pago.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado en alta de solicitud de pago.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Pagos obtenerPagosHitoActual(Expediente poExpediente, Entidad entidad){
		try {
			Pagos oPagos =
				getPagosWS(
						getServicioConsultaExpedientes()
							.obtenerPagosHitoActual(poExpediente.getNumero(), entidad)
				);
				return (Pagos)ServiciosUtils.completeReturnOK(oPagos);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo pagos del hito actual.", e);
			return (Pagos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Pagos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo pagos del hito actual.", e);
			return (Pagos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Pagos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo pagos del hito actual.", e);
			return (Pagos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Pagos()));
		}
	}

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito que llega como parámetro.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Pagos obtenerPagosHito(HitoExpediente poHitoExp, Entidad entidad){
		try {
			Pagos oPagos =
				getPagosWS(
						getServicioConsultaExpedientes()
							.obtenerPagosHito(poHitoExp.getGuid(), entidad)
				);
				return (Pagos)ServiciosUtils.completeReturnOK(oPagos);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo pagos del hito.", e);
			return (Pagos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Pagos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo pagos del hito.", e);
			return (Pagos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Pagos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo pagos del hito.", e);
			return (Pagos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Pagos()));
		}
	}

	/**
	 * Método que da de alta y asocia al hito actual la información referente a una notificación
	 * en curso en el sistema de notificaciones de SIGEM.
	 * Este método es usado desde la tramitación para publicar que se ha realizado una notificación.
	 * Previa a su publicación la notificación se ha dado de alta en el servicio de notificaciones y
	 * el identificador devuelto por este sistema se pasa como atributo de la clase Notificacion.
	 * @param poNotificacion Datos de la notificación para su publicación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public RetornoServicio altaNotificacion(Notificacion poNotificacion, Entidad entidad){
		try {
			getServicioConsultaExpedientes()
				.altaNotificacion(
						getNotificacionServicio(poNotificacion), entidad
				);
			return ServiciosUtils.createReturnOK();
		} catch (ConsultaExpedientesException e) {
			logger.error("Error en alta de notificación.", e);
			return ServiciosUtils.createReturnError();

		} catch (SigemException e) {
			logger.error("Error en alta de notificación.", e);
			return ServiciosUtils.createReturnError();

		} catch (Throwable e) {
			logger.error("Error inesperado en alta de notificación.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para el hito actual.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Notificaciones obtenerNotificionesHitoActual(Expediente poExpediente, Entidad entidad){
		try {
			Notificaciones oNots =
				getNotificacionesWS(
					getServicioConsultaExpedientes()
						.obtenerNotificionesHitoActual(poExpediente.getNumero(), entidad)
				);
			return (Notificaciones)ServiciosUtils.completeReturnOK(oNots);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo notificaciones hito actual.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo notificaciones hito actual.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo notificaciones hito actual.", e);
			return (Notificaciones)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Notificaciones()));
		}
	}

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para cuyo identificador se pasa como parámetro.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Notificaciones obtenerNotificionesHito(HitoExpediente poHitoExp, Entidad entidad){
		try {
			Notificaciones oNots =
				getNotificacionesWS(
					getServicioConsultaExpedientes()
						.obtenerNotificionesHito(poHitoExp.getGuid(), entidad)
				);
			return (Notificaciones)ServiciosUtils.completeReturnOK(oNots);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error obteniendo notificaciones hito.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error obteniendo notificaciones hito.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo notificaciones hito.", e);
			return (Notificaciones)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Notificaciones()));
		}
	}

	public RetornoLogico existenNotificaciones(Expediente poExpediente, Entidad entidad){
		try {
			RetornoLogico oRetorno =
				getRetornoLogico(
						getServicioConsultaExpedientes()
							.existenNotificaciones(poExpediente.getNumero(), entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error consultando si existen notificaciones asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error consultando si existen notificaciones asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado consultando si existen notificaciones asociadas.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}

	public RetornoLogico existenPagos(Expediente poExpediente, Entidad entidad){
		try {
			RetornoLogico oRetorno =
				getRetornoLogico(
						getServicioConsultaExpedientes()
							.existenPagos(poExpediente.getNumero(), entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error consultando si existen pagos asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error consultando si existen pagos asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado consultando si existen pagos asociadas.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}

	public RetornoLogico existenSubsanaciones(Expediente poExpediente, Entidad entidad){
		try {
			RetornoLogico oRetorno =
				getRetornoLogico(
						getServicioConsultaExpedientes()
							.existenSubsanaciones(poExpediente.getNumero(), entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error consultando si existen subsanaciones asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error consultando si existen subsanaciones asociadas.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado consultando si existen subsanaciones asociadas.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}

	public RetornoLogico actualizarEstadoLocalGIS(String idExpediente, String estado, Entidad entidad){
		try {
			RetornoLogico oRetorno =
				getRetornoLogico(
						getServicioConsultaExpedientes()
							.actualizarEstadoLocalGIS(idExpediente, estado, entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error al actualizar el estado de un expediente de LocalGIS.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al actualizar el estado de un expediente de LocalGIS.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar el estado de un expediente de LocalGIS.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}

	public RetornoLogico publicarExpedienteLocalGIS(String idExpediente, String nif, String tipoExpediente, String estado, String fecha, Entidad entidad){
		try {
			RetornoLogico oRetorno =
				getRetornoLogico(
						getServicioConsultaExpedientes()
							.publicarExpedienteLocalGIS(idExpediente, nif, tipoExpediente, estado, DateTimeUtil.getDate(fecha, ConstantesServicios.DATE_PATTERN), entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (ConsultaExpedientesException e) {
			logger.error("Error al publicar un expediente de LocalGIS.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al publicar un expediente de LocalGIS.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al publicar un expediente de LocalGIS.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}

	private ServicioConsultaExpedientes getServicioConsultaExpedientes() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioConsultaExpedientes();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioConsultaExpedientes(sbImpl.toString());
		}
	}

	private Expedientes getExpedientesWS(ieci.tecdoc.sgm.core.services.consulta.Expedientes poExps){
		if(poExps == null){
			return null;
		}
		Expedientes oExps = new Expedientes();
		if(poExps.getExpedientes() != null){
			Expediente[] aExps = new Expediente[poExps.getExpedientes().size()];
			Iterator oIterador = poExps.getExpedientes().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				aExps[i] = getExpedienteWS((ieci.tecdoc.sgm.core.services.consulta.Expediente)oIterador.next());
				i++;
			}
			oExps.setExpedientes(aExps);
		}
		return oExps;
	}

	private Expediente getExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.Expediente poExp){
		if(poExp == null){
			return null;
		}
		Expediente oExp = new Expediente();
		oExp.setAportacion(poExp.getAportacion());
		oExp.setCodigoPresentacion(poExp.getCodigoPresentacion());
		oExp.setEstado(poExp.getEstado());
		oExp.setFechaInicio(DateTimeUtil.getDateTime(poExp.getFechaInicio(), ConstantesServicios.DATE_PATTERN));
		oExp.setFechaRegistro(DateTimeUtil.getDateTime(poExp.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
		oExp.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
		oExp.setNotificacion(poExp.getNotificacion());
		oExp.setNumero(poExp.getNumero());
		oExp.setNumeroRegistro(poExp.getNumeroRegistro());
		oExp.setProcedimiento(poExp.getProcedimiento());
		return oExp;
	}

	private ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes getCriterioBusquedaExpedientesServicio(CriterioBusquedaExpedientes poCriterio){
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes oCriterio = new ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes();
		oCriterio.setEstado(poCriterio.getEstado());
		oCriterio.setFechaDesde(poCriterio.getFechaDesde());
		oCriterio.setFechaHasta(poCriterio.getFechaHasta());
		oCriterio.setNIF(poCriterio.getNIF());
		oCriterio.setOperadorConsulta(poCriterio.getOperadorConsulta());

		oCriterio.setExpediente(poCriterio.getExpediente());
		oCriterio.setProcedimiento(poCriterio.getProcedimiento());
		oCriterio.setNumeroRegistroInicial(poCriterio.getNumeroRegistroInicial());
		oCriterio.setFechaRegistroInicialDesde(poCriterio.getFechaRegistroInicialDesde());
		oCriterio.setFechaRegistroInicialHasta(poCriterio.getFechaRegistroInicialHasta());
		oCriterio.setOperadorConsultaFechaInicial(poCriterio.getOperadorConsultaFechaInicial());
		return oCriterio;
	}

	private HitoExpediente getHitoExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.HitoExpediente poHito){
		if(poHito == null){
			return null;
		}
		HitoExpediente oHito = new HitoExpediente();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());
		oHito.setFecha(poHito.getFecha());
		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private HitosExpediente getHitosExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		HitosExpediente oHitos = new HitosExpediente();
		if(poHitos.getHitosExpediente() != null){
			HitoExpediente[] lHitos = new HitoExpediente[poHitos.getHitosExpediente().size()];
			Iterator oIterador = poHitos.getHitosExpediente().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lHitos[i] = getHitoExpedienteWS((ieci.tecdoc.sgm.core.services.consulta.HitoExpediente)oIterador.next());
				i++;
			}
			oHitos.setExpedientes(lHitos);
		}
		return oHitos;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Expediente getExpedienteServicio(Expediente poExp) throws Exception{
		if(poExp == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Expediente oExp = new ieci.tecdoc.sgm.core.services.consulta.Expediente();
		oExp.setAportacion(poExp.getAportacion());
		oExp.setCodigoPresentacion(poExp.getCodigoPresentacion());
		oExp.setEstado(poExp.getEstado());
		oExp.setFechaInicio(DateTimeUtil.getDate(poExp.getFechaInicio(), ConstantesServicios.DATE_PATTERN));
		oExp.setFechaRegistro(DateTimeUtil.getDate(poExp.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
		oExp.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
		oExp.setNotificacion(poExp.getNotificacion());
		oExp.setNumero(poExp.getNumero());
		oExp.setNumeroRegistro(poExp.getNumeroRegistro());
		oExp.setProcedimiento(poExp.getProcedimiento());
		return oExp;
	}

	private FicherosHito getFicherosHitoWS(ieci.tecdoc.sgm.core.services.consulta.FicherosHito poFichs){
		if(poFichs == null){
			return null;
		}
		FicherosHito oFichs = new FicherosHito();
		oFichs.setGuid(poFichs.getGuidHito());
		if(poFichs.getFicheros() != null){
			FicheroHito[] lFichs = new FicheroHito[poFichs.getFicheros().size()];
			Iterator oIterador = poFichs.getFicheros().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lFichs[i] = getFicheroHitoWS((ieci.tecdoc.sgm.core.services.consulta.FicheroHito)oIterador.next());
				i++;
			}
			oFichs.setFicherosHito(lFichs);
		}
		return oFichs;
	}

	private FicheroHito getFicheroHitoWS(ieci.tecdoc.sgm.core.services.consulta.FicheroHito pofich){
		if(pofich == null){
			return null;
		}
		FicheroHito ofich = new FicheroHito();
		ofich.setGuid(pofich.getGuid());
		ofich.setGuidHito(pofich.getGuidHito());
		ofich.setTitulo(pofich.getTitulo());
		return ofich;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicheroHito getFicheroHitoServicio(FicheroHito pofich){
		if(pofich == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicheroHito ofich = new ieci.tecdoc.sgm.core.services.consulta.FicheroHito();
		ofich.setGuid(pofich.getGuid());
		ofich.setGuidHito(pofich.getGuidHito());
		ofich.setTitulo(pofich.getTitulo());
		return ofich;
	}

	private ieci.tecdoc.sgm.core.services.consulta.HitoExpediente getHitoExpedienteServicio(HitoExpediente poHito){
		if(poHito == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.HitoExpediente oHito = new ieci.tecdoc.sgm.core.services.consulta.HitoExpediente();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());
		oHito.setFecha(poHito.getFecha());
		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private ieci.tecdoc.sgm.core.services.consulta.HitosExpediente getHitosExpedienteServicio(HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.HitosExpediente oHitos = new ieci.tecdoc.sgm.core.services.consulta.HitosExpediente();
		List lHitos = null;
		if(poHitos.getHitosExpedientes() != null){
			lHitos = new ArrayList(poHitos.getHitosExpedientes().length);
			for(int i=0; i < poHitos.getHitosExpedientes().length; i++){
				lHitos.add(getHitoExpedienteServicio(poHitos.getHitosExpedientes()[i]));
			}
		}else{
			lHitos = new ArrayList();
		}
		oHitos.setHitosExpediente(lHitos);
		return oHitos;
	}

	private FicherosHitos getFicherosHitosWS(ieci.tecdoc.sgm.core.services.consulta.FicherosHitos poFichs){
		if(poFichs == null){
			return null;
		}
		FicherosHitos oFichs = new FicherosHitos();
		if(poFichs.getFicherosHitos() != null){
			FicherosHito[] lfichs = new FicherosHito[poFichs.getFicherosHitos().size()];
			Iterator oIterador = poFichs.getFicherosHitos().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lfichs[i] = getFicherosHitoWS((ieci.tecdoc.sgm.core.services.consulta.FicherosHito)oIterador.next());
				i++;
			}
			oFichs.setFicherosHitos(lfichs);
		}
		return oFichs;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Interesado getInteresadoServicio(Interesado poInteresado) throws Exception{
		if(poInteresado == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Interesado oInteresado = new ieci.tecdoc.sgm.core.services.consulta.Interesado();
		oInteresado.setInformacionAuxiliar(poInteresado.getInformacionAuxiliar());
		oInteresado.setNIF(poInteresado.getNIF());
		oInteresado.setNombre(poInteresado.getNombre());
		oInteresado.setNumeroExpediente(poInteresado.getNumeroExpediente());
		oInteresado.setPrincipal(poInteresado.getPrincipal());
		oInteresado.setExpedientes(getExpedientesServicio(poInteresado.getExpedientes()));
		return oInteresado;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Expedientes getExpedientesServicio(Expedientes poExps) throws Exception{
		if(poExps == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Expedientes oExps = new ieci.tecdoc.sgm.core.services.consulta.Expedientes();
		if(poExps.getExpedientes() != null){
			List lExps = new ArrayList();
			for(int i=0; i<poExps.getExpedientes().length; i++){
				lExps.add(getExpedienteServicio(poExps.getExpedientes()[i]));
			}
			oExps.setExpedientes(lExps);
		}
		return oExps;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicherosHito getFicherosHitoServicio(FicherosHito poFichs){
		if(poFichs == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicherosHito oFichs = new ieci.tecdoc.sgm.core.services.consulta.FicherosHito();
		List lfichs = null;
		if(poFichs.getFicherosHito() != null){
			lfichs = new ArrayList(poFichs.getFicherosHito().length);
			for(int i = 0; i < poFichs.getFicherosHito().length; i++){
				lfichs.add(getFicheroHitoServicio((FicheroHito)poFichs.getFicherosHito()[i]));
			}
		}else{
			lfichs = new ArrayList();
		}
		oFichs.setFicheros(lfichs);
		return oFichs;
	}

	private boolean getHistoricoServicio(Historico poHistorico){
		if( (poHistorico != null)
			&& (poHistorico.getPasoHistoricoHitoActual() != null)
			&& (ConstantesServicios.LABEL_TRUE.equals(poHistorico.getPasoHistoricoHitoActual()))){
			return true;
		}else{
			return false;
		}
	}

	private InfoDocumento getInfoDocumentoWS(ieci.tecdoc.sgm.core.services.consulta.InfoDocumento poInfo){
		if(poInfo == null){
			return null;
		}
		InfoDocumento oInfo = new InfoDocumento();
		if(poInfo.getContent() != null){
			oInfo.setContent(Base64.encodeBytes(poInfo.getContent()));
		}
		oInfo.setExtension(poInfo.getExtension());
		oInfo.setMimeType(poInfo.getMimeType());
		oInfo.setGuid(poInfo.getGuid());
		return oInfo;
	}

	private NotificacionesPendientes getNotificacionesPendientesWS(boolean pbNotificaciones){
		NotificacionesPendientes oNotificaciones = new NotificacionesPendientes();
		if(pbNotificaciones){
			oNotificaciones.setNotificacionesPendientes(ConstantesServicios.LABEL_TRUE);
		}else{
			oNotificaciones.setNotificacionesPendientes(ConstantesServicios.LABEL_FALSE);
		}
		return oNotificaciones;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Subsanacion getSubsanacionServicio(Subsanacion poSub){
		if(poSub == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Subsanacion oSub = new ieci.tecdoc.sgm.core.services.consulta.Subsanacion();
		oSub.setFecha(poSub.getFecha());
		oSub.setIdDocumento(poSub.getIdDocumento());
		oSub.setIdentificador(poSub.getIdentificador());
		oSub.setIdentificadorHito(poSub.getIdentificadorHito());
		oSub.setMensajeParaElCiudadano(poSub.getMensajeParaElCiudadano());
		oSub.setNumeroExpediente(poSub.getNumeroExpediente());
		return oSub;
	}

	private Subsanacion getSubsanacionWS(ieci.tecdoc.sgm.core.services.consulta.Subsanacion poSub){
		if(poSub == null){
			return null;
		}
		Subsanacion oSub = new Subsanacion();
		oSub.setFecha(poSub.getFecha());
		oSub.setIdDocumento(poSub.getIdDocumento());
		oSub.setIdentificador(poSub.getIdentificador());
		oSub.setIdentificadorHito(poSub.getIdentificadorHito());
		oSub.setMensajeParaElCiudadano(poSub.getMensajeParaElCiudadano());
		oSub.setNumeroExpediente(poSub.getNumeroExpediente());
		return oSub;
	}

	private Subsanaciones getSubsanacionesWS(ieci.tecdoc.sgm.core.services.consulta.Subsanaciones poSubs){
		if(poSubs == null){
			return null;
		}
		Subsanaciones oRetorno = new Subsanaciones();
		Subsanacion[] oSubs = null;
		if(poSubs.getSubsanaciones() != null){
			oSubs = new Subsanacion[poSubs.getSubsanaciones().size()];
			Iterator oIterador = poSubs.getSubsanaciones().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				oSubs[i] = getSubsanacionWS((ieci.tecdoc.sgm.core.services.consulta.Subsanacion)oIterador.next());
				i++;
			}
			oRetorno.setSubsanaciones(oSubs);
		}
		return oRetorno;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Pago getPagoServicio(Pago poPago){
		if(poPago == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Pago oPago = new ieci.tecdoc.sgm.core.services.consulta.Pago();
		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setFecha(poPago.getFecha());
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setImporte(poPago.getImporte());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());
		return oPago;
	}

	private Pago getPagoWS(ieci.tecdoc.sgm.core.services.consulta.Pago poPago){
		if(poPago == null){
			return null;
		}
		Pago oPago = new Pago();
		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setFecha(poPago.getFecha());
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setImporte(poPago.getImporte());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());
		return oPago;
	}


	private Pagos getPagosWS(ieci.tecdoc.sgm.core.services.consulta.Pagos poPagos){
		if(poPagos == null){
			return null;
		}
		Pagos oPagos = new Pagos();
		if(poPagos.getPagos() != null){
			Pago[] lPagos = new Pago[poPagos.getPagos().size()];
			Iterator oIterador = poPagos.getPagos().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lPagos[i] = getPagoWS((ieci.tecdoc.sgm.core.services.consulta.Pago)oIterador.next());
				i++;
			}
			oPagos.setPagos(lPagos);
		}
		return oPagos;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Notificacion getNotificacionServicio(Notificacion poNot){
		if(poNot == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Notificacion oNot = new ieci.tecdoc.sgm.core.services.consulta.Notificacion();
		oNot.setDescripcion(poNot.getDescripcion());
		oNot.setDEU(poNot.getDeu());
		oNot.setExpediente(poNot.getExpediente());
		oNot.setFechaNotificacion(poNot.getFechaNotificacion());
		oNot.setHitoId(poNot.getHitoId());
		oNot.setNotificacionId(poNot.getNotificacionId());
		oNot.setServicioNotificionesId(poNot.getServicioNotificionesId());
		return oNot;
	}

	private Notificacion getNotificacionWS(ieci.tecdoc.sgm.core.services.consulta.Notificacion poNot){
		if(poNot == null){
			return null;
		}
		Notificacion oNot = new Notificacion();
		oNot.setDescripcion(poNot.getDescripcion());
		oNot.setDeu(poNot.getDEU());
		oNot.setExpediente(poNot.getExpediente());
		oNot.setFechaNotificacion(poNot.getFechaNotificacion());
		oNot.setHitoId(poNot.getHitoId());
		oNot.setNotificacionId(poNot.getNotificacionId());
		oNot.setServicioNotificionesId(poNot.getServicioNotificionesId());
		return oNot;
	}

	private Notificaciones getNotificacionesWS(ieci.tecdoc.sgm.core.services.consulta.Notificaciones poNots){
		if(poNots == null){
			return null;
		}
		Notificaciones oNots = new Notificaciones();
		if(poNots.getNotificaciones() != null){
			Notificacion[] lNots = new Notificacion[poNots.getNotificaciones().size()];
			Iterator oIterador = poNots.getNotificaciones().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lNots[i] = getNotificacionWS((ieci.tecdoc.sgm.core.services.consulta.Notificacion)oIterador.next());
				i++;
			}
			oNots.setNotificaciones(lNots);
		}
		return oNots;
	}

	private RetornoLogico getRetornoLogico(boolean pbValor){
		RetornoLogico oRetorno = new RetornoLogico();
		if(pbValor){
			oRetorno.setValor(ConstantesServicios.LABEL_TRUE);
		}else{
			oRetorno.setValor(ConstantesServicios.LABEL_FALSE);
		}
		return oRetorno;
	}
}
