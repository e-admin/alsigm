package ieci.tecdoc.sgm.publicador.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.publicador.PublicadorException;
import ieci.tecdoc.sgm.core.services.publicador.ServicioPublicador;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;
import ieci.tecdoc.sgm.publicador.ws.server.dto.Booleano;
import ieci.tecdoc.sgm.publicador.ws.server.dto.Hito;

import javax.xml.soap.SOAPException;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

public class PublicadorWebService {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(PublicadorWebService.class);

	/** Nombre del servicio. */
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_PROCESSING;

	/**
	 * 
	 * @return El servicio del Publicador
	 * @throws SOAPException
	 * @throws SigemException
	 */
	private ServicioPublicador getServicioPublicador() throws SOAPException,
			SigemException {

		String cImpl = UtilAxis.getImplementacion(MessageContext
				.getCurrentContext());

		if ((cImpl == null) || ("".equals(cImpl))) {
			return LocalizadorServicios.getServicioPublicador();
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".")
					.append(cImpl);
			return LocalizadorServicios
					.getServicioPublicador(sbImpl.toString());
		}
	}

	/**
	 * Obtiene el hito que se correponde con el idHito que recibe como parámetro en 
	 * la entidad contra la que estamos trabajando (parámetro idEntidad)
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param idHito : Identificador del hito a obtener
	 * @param idAplicacion: Identificador de la aplicacion 
	 * @param idSystem : Identificador del sistema
	 * @return Un objeto Hito con la información del hito
	 */
	public Hito getHito(String idEntidad, int idHito, int idAplicacion,
			String idSystem) {

		Hito hitoObj = new Hito();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Hito hito = service
					.getHito(idEntidad, idHito, idAplicacion, idSystem);

			copyHito(hitoObj, hito);

		} catch (PublicadorException e) {
			logger.error("Error al obtener el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj);
		}

		return (Hito) ServiciosUtils.completeReturnOK(hitoObj);
	}

	/**
	 * Actualiza el hito que recibe como parámetro sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param hito : Objeto de tipo Hito que se sobre-escribirá al existente
	 * @return Un objeto Hito con la información del hito
	 */
	public Hito updateHito(String idEntidad,
			ieci.tecdoc.sgm.publicador.ws.server.dto.Hito hito) {

		Hito hitoObj = new Hito();

		try {
			ServicioPublicador service = getServicioPublicador();
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito ret = service
					.updateHito(idEntidad, getHito(hito));

			copyHito(hitoObj, ret);

		} catch (PublicadorException e) {
			logger.error("Error al actualizar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al actualizar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al actualizar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj);
		}

		return (Hito) ServiciosUtils.completeReturnOK(hitoObj);
	}

	/**
	 * Añade el hito que recibe como parámetro sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param hito : Objeto de tipo Hito con la información a guardar en bbdd
	 * @return Un objeto Hito con la información del hito
	 */
	public Hito addHito(String idEntidad,
			ieci.tecdoc.sgm.publicador.ws.server.dto.Hito hito) {

		Hito hitoObj = new Hito();

		try {
			ServicioPublicador service = getServicioPublicador();
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito ret = service
					.addHito(idEntidad, getHito(hito));
			copyHito(hitoObj, ret);

		} catch (PublicadorException e) {
			logger.error("Error al añadir el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al añadir el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al añadir el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj);
		}

		return (Hito) ServiciosUtils.completeReturnOK(hitoObj);
	}

	/**
	 * Borra el hito cuyo identificador se corresponde con el parámetro idHito, sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param idHito : Identificador del hito a borrar
	 * @param applicationId: Identificador de la aplicacion 
	 * @param systemId : Identificador del sistema
	 * @return cierto si tuvo éxito , falso en caso contrario
	 */
	public Booleano deleteHito(String idEntidad, int idHito, int applicationId,
			String systemId) {

		Booleano ret = new Booleano();

		try {
			ServicioPublicador service = getServicioPublicador();
			ret.setValor(service.deleteHito(idEntidad, idHito, applicationId,
					systemId));

		} catch (PublicadorException e) {
			logger.error("Error al borrar el hito.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al borrar el hito.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al borrar el hito.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}

		return (Booleano) ServiciosUtils.completeReturnOK(ret);
	}

	/**
	 * Reactiva el hito  erróneo eliminando su error
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param idHito : Identificador del hito a borrar
	 * @param applicationId: Identificador de la aplicacion 
	 * @param systemId : Identificador del sistema
	 * @return Un objeto Hito con la información del hito
	 */
	public Hito reactivateHito(String idEntidad, int idHito, int applicationId,
			String systemId) {

		Hito hitoObj = new Hito();

		try {
			ServicioPublicador service = getServicioPublicador();
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito ret = service
					.reactivateHito(idEntidad, idHito, applicationId, systemId);
			copyHito(hitoObj, ret);

		} catch (PublicadorException e) {
			logger.error("Error al reactivar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al reactivar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al reactivar el hito.", e);
			return (Hito) ServiciosUtils.completeReturnError(hitoObj);
		}

		return (Hito) ServiciosUtils.completeReturnOK(hitoObj);
	}

	/**
	 * Obtiene la información de la regla cuyo identificador se corresponde con idRegla sobre la entidad cuyo identificador sea idEntidad
	 * @param idEntidad : Identificador de la entidad contra la que estamos trabajando
	 * @param idRegla: Identificador de la regla
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.Regla getRegla(
			String idEntidad, int idRegla) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Regla reglaObj = new ieci.tecdoc.sgm.publicador.ws.server.dto.Regla();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Regla regla = service
					.getRegla(idEntidad, idRegla);

			copyRegla(reglaObj, regla);

		} catch (PublicadorException e) {
			logger.error("Error al obtener la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils
					.completeReturnError(reglaObj, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils
					.completeReturnError(reglaObj, e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils
					.completeReturnError(reglaObj);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils
				.completeReturnOK(reglaObj);
	}

	/**
	 * Actualiza la regla que recibe como parámetro sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad : Identificador de la entidad contra la que estamos trabajando
	 * @param regla: Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.Regla updateRegla(String idEntidad,
			ieci.tecdoc.sgm.publicador.ws.server.dto.Regla regla) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Regla reglaObj = new ieci.tecdoc.sgm.publicador.ws.server.dto.Regla();

		try {
			ServicioPublicador service = getServicioPublicador();
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla reglaCore = new ieci.tecdoc.sgm.core.services.publicador.dto.Regla();
			copyReglaCore(regla, reglaCore);

			ieci.tecdoc.sgm.core.services.publicador.dto.Regla ret = service.updateRegla(idEntidad, reglaCore);
			copyRegla(reglaObj, ret);

		} catch (PublicadorException e) {
			logger.error("Error al actualizar la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al actualizar la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al actualizar la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnOK(reglaObj);
	}

	/**
	 * Añade la regla que recibe como parámetro sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad : Identificador de la entidad contra la que estamos trabajando
	 * @param regla: Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.Regla addRegla(String idEntidad,
			ieci.tecdoc.sgm.publicador.ws.server.dto.Regla regla) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Regla reglaObj = new ieci.tecdoc.sgm.publicador.ws.server.dto.Regla();

		try {
			ServicioPublicador service = getServicioPublicador();
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla reglaCore = new ieci.tecdoc.sgm.core.services.publicador.dto.Regla();
			copyReglaCore(regla, reglaCore);
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla ret = service.addRegla(idEntidad, reglaCore);
			copyRegla(reglaObj, ret);

		} catch (PublicadorException e) {
			logger.error("Error al añadir la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al añadir la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al añadir la regla.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnError(reglaObj);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.Regla) ServiciosUtils.completeReturnOK(reglaObj);
	}

	/**
	 * Borra la regla cuyo identificador se corresponde con el parámetro idRegla, sobre la entidad cuyo identificador sea 
	 * idEntidad
	 * @param idEntidad: Identificador de la entidad contra la que estamos trabajando
	 * @param idRegla : Identificador de la regla a borrar
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public Booleano deleteRegla(String idEntidad, int idRegla) {

		Booleano ret = new Booleano();

		try {
			ServicioPublicador service = getServicioPublicador();
			ret.setValor(service.deleteRegla(idEntidad, idRegla));

		} catch (PublicadorException e) {
			logger.error("Error al borrar la regla.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret, e
					.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al borrar la regla.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret, e
					.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al borrar la regla.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}

		return (Booleano) ServiciosUtils.completeReturnOK(ret);
	}

	/**
	 * Obtiene la información del error cuyo identificador se corresponde con idError sobre la entidad cuyo identificador sea idEntidad
	 * @param idEntidad : Identificador de la entidad contra la que estamos trabajando
	 * @param idHito : Identificador del hito
	 * @param applicationId: Identificador de la aplicacion
	 * @param systemId : Identificador del sistema
	 * @param objectId : Identificador del objeto
	 * @return Una lista con los errores
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.Error getError(
			String idEntidad, int idHito, int applicationId, String systemId) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Error errorObj = new ieci.tecdoc.sgm.publicador.ws.server.dto.Error();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Error error = service
					.getError(idEntidad, idHito, applicationId, systemId);

			copyError(errorObj, error);

		} catch (PublicadorException e) {
			logger.error("Error al obtener el error.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Error) ServiciosUtils
					.completeReturnError(errorObj, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el error.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Error) ServiciosUtils
					.completeReturnError(errorObj, e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener el error.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.Error) ServiciosUtils
					.completeReturnError(errorObj);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.Error) ServiciosUtils
				.completeReturnOK(errorObj);
	}

	/**
	 * Obtiene la lista de errores de la entidad que recibe como parámetro
	 * @param idEntidad: Identificador de la entidad
	 * @return Lista de todos los errores 
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores getListaErrores(
			String idEntidad) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores listaErrores = new ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Error[] errores = service
					.getListaErrores(idEntidad);
			listaErrores.setErrores(getErrores(errores));

		} catch (PublicadorException e) {
			logger.error("Error al obtener la lista de errores.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores) ServiciosUtils
					.completeReturnError(listaErrores, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la lista de errores.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores) ServiciosUtils
					.completeReturnError(listaErrores, e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener la lista de errores.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores) ServiciosUtils
					.completeReturnError(listaErrores);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaErrores) ServiciosUtils
				.completeReturnOK(listaErrores);
	}

	/**
	 * Obtiene la lista de hitos de la entidad que recibe como parámetro
	 * @param idEntidad: Identificador de la entidad
	 * @return Lista de todos los hitos
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos getListaHitos(
			String idEntidad) {
		ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos listaHitos = new ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Hito[] hitos = service
					.getListaHitos(idEntidad);

			listaHitos.setHitos(getHitos(hitos));

		} catch (PublicadorException e) {
			logger.error("Error al obtener la lista de hitos.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos) ServiciosUtils
					.completeReturnError(listaHitos, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la lista de hitos.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos) ServiciosUtils
					.completeReturnError(listaHitos, e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener la lista de hitos.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos) ServiciosUtils
					.completeReturnError(listaHitos);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaHitos) ServiciosUtils
				.completeReturnOK(listaHitos);
	}

	/**
	 * Obtiene la lista de reglas de la entidad que recibe como parámetro
	 * @param idEntidad: Identificador de la entidad
	 * @return Lista de todas las reglas
	 * @throws SOAPException
	 * @throws SigemException
	 */
	public ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas getListaReglas(
			String idEntidad) {
		ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas listaReglas = new ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas();

		try {
			ServicioPublicador service = getServicioPublicador();

			ieci.tecdoc.sgm.core.services.publicador.dto.Regla[] reglas = service
					.getListaReglas(idEntidad);

			listaReglas.setReglas(getReglas(reglas));

		} catch (PublicadorException e) {
			logger.error("Error al obtener la lista de reglas.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas) ServiciosUtils
					.completeReturnError(listaReglas, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la lista de reglas.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas) ServiciosUtils
					.completeReturnError(listaReglas, e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener la lista de reglas.", e);
			return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas) ServiciosUtils
					.completeReturnError(listaReglas);
		}

		return (ieci.tecdoc.sgm.publicador.ws.server.dto.ListaReglas) ServiciosUtils
				.completeReturnOK(listaReglas);
	}

	/**
	 * Mapea los datos de objeto hito del Ws al del Core
	 * @param hitoObj Objeto que extiende del Retorno Servicio para ser utilizadas en las llamas internas del servicio web
	 * @param hito Objeto de core de sigem con la información del hito
	 */
	private void copyHito(Hito hitoObj,
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito hito) {
		if ((hitoObj != null) && (hito != null)) {
			hitoObj.setEstado(hito.getEstado());
			hitoObj.setFecha(hito.getFecha());
			hitoObj.setIdAplicacion(hito.getIdAplicacion());
			hitoObj.setIdEvento(hito.getIdEvento());
			hitoObj.setIdFase(hito.getIdFase());
			hitoObj.setIdHito(hito.getIdHito());
			hitoObj.setIdInfo(hito.getIdInfo());
			hitoObj.setIdObjeto(hito.getIdObjeto());
			hitoObj.setIdPcd(hito.getIdPcd());
			hitoObj.setIdSistema(hito.getIdSistema());
			hitoObj.setIdTramite(hito.getIdTramite());
			hitoObj.setInfoAux(hito.getInfoAux());
			hitoObj.setIpMaquina(hito.getIpMaquina());
			hitoObj.setTipoDoc(hito.getTipoDoc());
		}
	}

	/**
	 * Copia al objeto regla del core del sigem la información del objeto regla utilizado internamente en el servicio
	 * @param regla Objeto que extiende del Retorno Servicio para ser utilizadas en las llamas internas del servicio web
	 * @param reglaObj Objeto de core de sigem con la información de la regla
	 */
	private void copyReglaCore(
			ieci.tecdoc.sgm.publicador.ws.server.dto.Regla regla,
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla reglaObj) {

		if ((reglaObj != null) && (regla != null)) {
			reglaObj.setAtributos(regla.getAtributos());
			reglaObj.setId(regla.getId());
			reglaObj.setIdAccion(regla.getIdAccion());
			reglaObj.setIdAplicacion(regla.getIdAplicacion());
			reglaObj.setIdCondicion(regla.getIdCondicion());
			reglaObj.setIdEvento(regla.getIdEvento());
			reglaObj.setIdFase(regla.getIdFase());
			reglaObj.setIdInfo(regla.getIdInfo());
			reglaObj.setIdPcd(regla.getIdPcd());
			reglaObj.setIdTramite(regla.getIdTramite());
			reglaObj.setOrden(regla.getOrden());
			reglaObj.setTipoDoc(regla.getTipoDoc());
		}
	}

	/**
	 * Copia al objeto hito del core del sigem la información del objeto hito  utilizado internamente en el servicio
	 * @param hito Objeto que extiende del Retorno Servicio para ser utilizadas en las llamas internas del servicio web
	 * @param hitoObj Objeto de core de sigem con la información de la regla
	 */
	private void copyHitoCore(Hito hito,
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito hitoObj) {
		if ((hitoObj != null) && (hito != null)) {
			hitoObj.setEstado(hito.getEstado());
			hitoObj.setFecha(hito.getFecha());
			hitoObj.setIdAplicacion(hito.getIdAplicacion());
			hitoObj.setIdEvento(hito.getIdEvento());
			hitoObj.setIdFase(hito.getIdFase());
			hitoObj.setIdHito(hito.getIdHito());
			hitoObj.setIdInfo(hito.getIdInfo());
			hitoObj.setIdObjeto(hito.getIdObjeto());
			hitoObj.setIdPcd(hito.getIdPcd());
			hitoObj.setIdSistema(hito.getIdSistema());
			hitoObj.setIdTramite(hito.getIdTramite());
			hitoObj.setInfoAux(hito.getInfoAux());
			hitoObj.setIpMaquina(hito.getIpMaquina());
			hitoObj.setTipoDoc(hito.getTipoDoc());
		}
	}

	/**
	 * Mapea los datos de objeto hito del Ws al del Core
	 * @param reglaObj Objeto que extiende del Retorno Servicio para ser utilizadas en las llamas internas del servicio web
	 * @param regla Objeto de core de sigem con la información de la regla
	 */
	private void copyRegla(
			ieci.tecdoc.sgm.publicador.ws.server.dto.Regla reglaObj,
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla regla) {

		if ((reglaObj != null) && (regla != null)) {
			reglaObj.setAtributos(regla.getAtributos());
			reglaObj.setId(regla.getId());
			reglaObj.setIdAccion(regla.getIdAccion());
			reglaObj.setIdAplicacion(regla.getIdAplicacion());
			reglaObj.setIdCondicion(regla.getIdCondicion());
			reglaObj.setIdEvento(regla.getIdEvento());
			reglaObj.setIdFase(regla.getIdFase());
			reglaObj.setIdInfo(regla.getIdInfo());
			reglaObj.setIdPcd(regla.getIdPcd());
			reglaObj.setIdTramite(regla.getIdTramite());
			reglaObj.setOrden(regla.getOrden());
			reglaObj.setTipoDoc(regla.getTipoDoc());
		}
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Regla[] getReglas(
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla[] reglas) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Regla[] regs = null;

		if (reglas != null) {
			regs = new ieci.tecdoc.sgm.publicador.ws.server.dto.Regla[reglas.length];
			for (int i = 0; i < reglas.length; i++) {
				regs[i] = getRegla(reglas[i]);
			}
		}

		return regs;
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Hito[] getHitos(
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito[] hitos) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Hito[] regs = null;

		if (hitos != null) {
			regs = new ieci.tecdoc.sgm.publicador.ws.server.dto.Hito[hitos.length];
			for (int i = 0; i < hitos.length; i++) {
				regs[i] = getHito(hitos[i]);
			}
		}

		return regs;
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Error[] getErrores(
			ieci.tecdoc.sgm.core.services.publicador.dto.Error[] errores) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Error[] regs = null;

		if (errores != null) {
			regs = new ieci.tecdoc.sgm.publicador.ws.server.dto.Error[errores.length];
			for (int i = 0; i < errores.length; i++) {
				regs[i] = getError(errores[i]);
			}
		}

		return regs;
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Regla getRegla(
			ieci.tecdoc.sgm.core.services.publicador.dto.Regla reg) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Regla regla = new ieci.tecdoc.sgm.publicador.ws.server.dto.Regla();

		if (reg != null) {
			regla.setId(reg.getId());
			regla.setIdPcd(reg.getIdPcd());
			regla.setIdFase(reg.getIdFase());
			regla.setIdTramite(reg.getIdTramite());
			regla.setTipoDoc(reg.getTipoDoc());
			regla.setIdEvento(reg.getIdEvento());
			regla.setIdAccion(reg.getIdAccion());
			regla.setIdCondicion(reg.getIdCondicion());
			regla.setAtributos(reg.getAtributos());
			regla.setIdAplicacion(reg.getIdAplicacion());
			regla.setOrden(reg.getOrden());
			regla.setIdInfo(reg.getIdInfo());
		}

		return regla;
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Hito getHito(
			ieci.tecdoc.sgm.core.services.publicador.dto.Hito reg) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Hito hito = new ieci.tecdoc.sgm.publicador.ws.server.dto.Hito();

		if (reg != null) {
			hito.setEstado(reg.getEstado());
			hito.setFecha(reg.getFecha());
			hito.setIdAplicacion(reg.getIdAplicacion());
			hito.setIdEvento(reg.getIdEvento());
			hito.setIdFase(reg.getIdFase());
			hito.setIdHito(reg.getIdHito());
			hito.setIdInfo(reg.getIdInfo());
			hito.setIdObjeto(reg.getIdObjeto());
			hito.setIdPcd(reg.getIdPcd());
			hito.setIdSistema(reg.getIdSistema());
			hito.setIdTramite(reg.getIdTramite());
			hito.setInfoAux(reg.getInfoAux());
			hito.setIpMaquina(reg.getIpMaquina());
			hito.setTipoDoc(reg.getTipoDoc());
		}

		return hito;
	}

	private ieci.tecdoc.sgm.core.services.publicador.dto.Hito getHito(Hito reg) {

		ieci.tecdoc.sgm.core.services.publicador.dto.Hito hito = new ieci.tecdoc.sgm.core.services.publicador.dto.Hito();

		if (reg != null) {
			hito.setEstado(reg.getEstado());
			hito.setFecha(reg.getFecha());
			hito.setIdAplicacion(reg.getIdAplicacion());
			hito.setIdEvento(reg.getIdEvento());
			hito.setIdFase(reg.getIdFase());
			hito.setIdHito(reg.getIdHito());
			hito.setIdInfo(reg.getIdInfo());
			hito.setIdObjeto(reg.getIdObjeto());
			hito.setIdPcd(reg.getIdPcd());
			hito.setIdSistema(reg.getIdSistema());
			hito.setIdTramite(reg.getIdTramite());
			hito.setInfoAux(reg.getInfoAux());
			hito.setIpMaquina(reg.getIpMaquina());
			hito.setTipoDoc(reg.getTipoDoc());
		}

		return hito;
	}

	private ieci.tecdoc.sgm.publicador.ws.server.dto.Error getError(
			ieci.tecdoc.sgm.core.services.publicador.dto.Error reg) {

		ieci.tecdoc.sgm.publicador.ws.server.dto.Error error = new ieci.tecdoc.sgm.publicador.ws.server.dto.Error();

		if (reg != null) {
			error.setDescripcion(reg.getDescripcion());
			error.setIdAplicacion(reg.getIdAplicacion());
			error.setIdError(reg.getIdError());
			error.setIdHito(reg.getIdHito());
			error.setIdObjeto(reg.getIdObjeto());
			error.setIdSistema(reg.getIdSistema());
		}

		return error;
	}

	private void copyError(
			ieci.tecdoc.sgm.publicador.ws.server.dto.Error errorObj,
			ieci.tecdoc.sgm.core.services.publicador.dto.Error reg) {

		if ((errorObj != null) && (reg != null)) {
			errorObj.setDescripcion(reg.getDescripcion());
			errorObj.setIdAplicacion(reg.getIdAplicacion());
			errorObj.setIdError(reg.getIdError());
			errorObj.setIdHito(reg.getIdHito());
			errorObj.setIdObjeto(reg.getIdObjeto());
			errorObj.setIdSistema(reg.getIdSistema());
		}
	}

}
