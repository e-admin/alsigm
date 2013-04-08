package ieci.tecdoc.sgm.publicador;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tecdoc.sgm.core.services.publicador.PublicadorException;
import ieci.tecdoc.sgm.core.services.publicador.ServicioPublicador;
import ieci.tecdoc.sgm.core.services.publicador.dto.Error;
import ieci.tecdoc.sgm.core.services.publicador.dto.Hito;
import ieci.tecdoc.sgm.core.services.publicador.dto.Regla;

import java.util.Iterator;

import org.apache.log4j.Logger;


public class SigemPublicadorServiceAdapter implements ServicioPublicador {

	/** 
	 * Logger de la clase. 
	 */
	private static final Logger logger = Logger.getLogger(SigemPublicadorServiceAdapter.class);

	/**
	 * Inicializa los datos del usuario
	 * 
	 * @param entityId:dentificador
	 *            de la entidad contra la que se está trabajando
	 */
	private void setOrganizationUserInfo(String entityId) {
		OrganizationUserInfo info = new OrganizationUserInfo();
		info.setOrganizationId(entityId);
		OrganizationUser.setOrganizationUserInfo(info);
	}


	/**
	 * Devuelve el API del publicador
	 * 
	 * @param entityId
	 *            Identificador de la entidad contra la que se está trabajando
	 * @return
	 * @throws PublicadorException
	 */
	private IPublisherAPI getPublisherAPI(String entityId)
			throws PublicadorException {

		setOrganizationUserInfo(entityId);
		ClientContext context = new ClientContext();

		context.setAPI(new InvesflowAPI(context));
		IInvesflowAPI invesFlowAPI = context.getAPI();

		IPublisherAPI publisherAPI = null;
		try {
			publisherAPI = invesFlowAPI.getPublisherAPI();
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el api del publicador", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
		return publisherAPI;
	}

	/**
	 * Obtiene la lista de hitos de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todos los hitos
	 * @throws PublicadorException
	 */
	public Hito[] getListaHitos(String idEntidad) throws PublicadorException {
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			return getListHitos(publisherAPI.getMilestones());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener la lista de los hitos ", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Obtiene el hito que se correponde con el idHito que recibe como parámetro
	 * en la entidad contra la que estamos trabajando (parámetro idEntidad)
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a obtener
	 * @param idAplicacion:
	 *            Identificador de la aplicacion
	 * @param idSistema :
	 *            Identificador del sistema
	 * @return Un objeto Hito con la información del hito
	 * @throws PublicadorException
	 */
	public Hito getHito(String idEntidad, int idHito, int idAplicacion,
			String idSistema) throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			return getHito(publisherAPI.getMilestone(idHito, idAplicacion, idSistema));
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener la regla con id  ["
					+ idHito + "] en la entidad con id [" + idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade el hito que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param hito :
	 *            Objeto de tipo Hito con la información a guardar en bbdd
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws PublicadorException
	 */
	public Hito addHito(String idEntidad, Hito hito) throws PublicadorException {
		
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		
		try {
			
			if (hito == null) {
				return null;
			}
			
			IItem milestone = publisherAPI.addMilestone(
					hito.getIdHito(), hito.getIdPcd(), hito.getIdFase(), hito.getIdTramite(), 
					hito.getTipoDoc(), hito.getIdObjeto(), hito.getIdEvento(), hito.getIdInfo(), 
					hito.getInfoAux(), hito.getIdAplicacion(), hito.getIdSistema());
			
			return getHito(milestone);
			
		} catch (Throwable e) {
			logger.error("Error inesperado al añadir el hito en la entidad con id ["
							+ idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}

	}
	
	/**
	 * Actualiza el hito que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param hito :
	 *            Objeto de tipo Hito que se sobre-escribirá al existente
	 * @return Información del hito modificado.
	 * @throws PublicadorException
	 */
	public Hito updateHito(String idEntidad, Hito hito)
			throws PublicadorException {
		
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		
		try {
			
			if (hito == null) {
				return null;
			}
			
			IItem milestone = publisherAPI.updateMilestone(
					hito.getIdHito(), hito.getIdPcd(), hito.getIdFase(), hito.getIdTramite(), 
					hito.getTipoDoc(), hito.getIdObjeto(), hito.getIdEvento(), hito.getIdInfo(), 
					hito.getInfoAux(), hito.getIdAplicacion(), hito.getIdSistema(), hito.getEstado());
			
			return getHito(milestone);
			
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar el hito con id  ["
					+ hito.getIdHito() + "] en la entidad con id ["
					+ idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}

	}


	/**
	 * Borra el hito cuyo identificador se corresponde con el parámetro idHito,
	 * sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a borrar
	 * @param applicationId:
	 *            Identificador de la aplicacion
	 * @param systemId :
	 *            Identificador del sistema
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws PublicadorException
	 */
	public boolean deleteHito(String idEntidad, int idHito, int applicationId,
			String systemId) throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			publisherAPI.deleteMilestone(idHito, applicationId, systemId);
			return true;
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar el hito con id ["
					+ idHito + "] en la entidad con id [" + idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	/**
	 * Reactiva el hito erróneo eliminando su error
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a borrar
	 * @param applicationId:
	 *            Identificador de la aplicacion
	 * @param systemId :
	 *            Identificador del sistema
	 * @return Hito reactivado.
	 * @throws PublicadorException
	 */
	public Hito reactivateHito(String idEntidad, int idHito,
			int applicationId, String systemId) throws PublicadorException {
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			IItem milestone = publisherAPI.reactivateMilestone(idHito, applicationId, systemId);
			return getHito(milestone);
		} catch (Throwable e) {
			logger.error("Error inesperado al reactivar el hito con id  ["
					+ idHito + "] en la entidad con id [" + idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	/**
	 * Obtiene la lista de reglas de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todas las reglas
	 * @throws PublicadorException
	 */
	public Regla[] getListaReglas(String idEntidad) throws PublicadorException {
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			return getListReglas(publisherAPI.getRules());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener la lista de reglas ", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene la información de la regla cuyo identificador se corresponde con
	 * idRegla sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idRegla:
	 *            Identificador de la regla
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws PublicadorException
	 */
	public Regla getRegla(String idEntidad, int idRegla) throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		IItem regla;
		try {
			regla = publisherAPI.getRule(idRegla);
			return getRegla(regla);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener la regla con id  ["
					+ idRegla + "] en la entidad con id [" + idEntidad + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade la regla que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param regla:
	 *            Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Identificador de la regla creada.
	 * @throws PublicadorException
	 */
	public Regla addRegla(String idEntidad, Regla regla) throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		
		try {
			
			if (regla == null) {
				return null;
			}
			
			IItem rule = publisherAPI.addRule(regla.getIdPcd(), regla.getIdFase(), regla.getIdTramite(), 
					regla.getTipoDoc(), regla.getIdEvento(), regla.getIdAccion(),
					regla.getIdCondicion(), regla.getAtributos(), regla.getOrden(), 
					regla.getIdAplicacion(), regla.getIdInfo());
			
			return getRegla(rule); 

		} catch (Throwable e) {
			logger.error("Error inesperado al crear la regla", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza la regla que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param regla:
	 *            Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Información de la regla modificada.
	 * @throws PublicadorException
	 */
	public Regla updateRegla(String idEntidad, Regla regla)
			throws PublicadorException {
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);

		try {
			
			if (regla == null) {
				return null;
			}
			
			IItem rule = publisherAPI.updateRegla(regla.getId(), regla.getIdPcd(), 
					regla.getIdFase(), regla.getIdTramite(), regla.getTipoDoc(), 
					regla.getIdEvento(), regla.getIdAccion(), regla.getIdCondicion(), 
					regla.getAtributos(), regla.getOrden(), regla.getIdAplicacion(), 
					regla.getIdInfo());
			
			return getRegla(rule);

		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar la regla con identificador [" + regla.getId() + "]", e);
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Borra la regla cuyo identificador se corresponde con el parámetro
	 * idRegla, sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idRegla :
	 *            Identificador de la regla a borrar
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws PublicadorException
	 */
	public boolean deleteRegla(String idEntidad, int idRegla)
			throws PublicadorException {
		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			publisherAPI.deleteRule(idRegla);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al borrar la regla con identificador ["
							+ idRegla + "]", e);
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e);

		}

		return true;
	}

	/**
	 * Obtiene la lista de errores de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todos los errores
	 * @throws PublicadorException
	 */
	public Error[] getListaErrores(String idEntidad) throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			return getListErrores(publisherAPI.getErrors());
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al obtener la lista de los errores ", e);
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	
	/**
	 * Obtiene la información del error cuyo identificador se corresponde con
	 * idError sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito
	 * @param idAplicacion:
	 *            Identificador de la aplicacion
	 * @param idSistema :
	 *            Identificador del sistema
	 * @return Una lista con los errores
	 * @throws PublicadorException
	 */
	public Error getError(String idEntidad, int idHito, int idAplicacion, String idSistema) 
			throws PublicadorException {

		IPublisherAPI publisherAPI = getPublisherAPI(idEntidad);
		try {
			IItem error = publisherAPI.getError(idHito, idAplicacion, idSistema);
			return getError(error);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el error del hito ["
					+ idHito + "]", e);
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e);

		}
	}

	/**
	 * Se obtiene un lista de errores a partir de una coleccion de items
	 * 
	 * @param listErrores:
	 *            Collecion de errores
	 * @return Lista de objeto de tipo Error
	 * @throws Exception
	 */
	private Error[] getListErrores(IItemCollection listErrores) throws Exception {

		Error[] listaErrores = new Error[listErrores.toList().size()];
		int i = 0;
		if (listErrores.next()) {
			for (Iterator iter = listErrores.iterator(); iter.hasNext();) {
				IItem errorItem = (IItem) iter.next();
				listaErrores[i] = getError(errorItem);
				i++;
			}
		}
		return listaErrores;
	}

	/**
	 * Se obtiene un lista de errores a partir de una coleccion de items
	 * 
	 * @param listHitos:
	 *            Collecion de errores
	 * @return Lista de objeto de tipo Hito
	 * @throws Exception
	 */
	private Hito[] getListHitos(IItemCollection listHitos) throws Exception {

		Hito[] listaHitos = new Hito[listHitos.toList().size()];
		int i = 0;
		if (listHitos.next()) {
			for (Iterator iter = listHitos.iterator(); iter.hasNext();) {
				IItem hitoItem = (IItem) iter.next();
				listaHitos[i] = getHito(hitoItem);
				i++;
			}
		}
		return listaHitos;
	}

	/**
	 * Se obtiene un lista de errores a partir de una coleccion de items
	 * 
	 * @param listHitos:
	 *            Collecion de errores
	 * @return Lista de objeto de tipo Hito
	 * @throws Exception
	 */
	private Regla[] getListReglas(IItemCollection listReglas) throws Exception {

		Regla[] listaReglas = new Regla[listReglas.toList().size()];
		int i = 0;
		if (listReglas.next()) {
			for (Iterator iter = listReglas.iterator(); iter.hasNext();) {
				IItem reglaItem = (IItem) iter.next();
				listaReglas[i] = getRegla(reglaItem);
				i++;
			}
		}
		return listaReglas;
	}

	/**
	 * Se construye un objeto de tipo hito a partir de un objeto Iitem que
	 * contiene la informacion de un hito.
	 * 
	 * @param hito
	 *            Iitem con la información del hito obtenida de la base de datos
	 * @return Objeto de tipo Hito
	 * @throws ISPACException
	 */
	private Hito getHito(IItem hito) throws ISPACException {

		Hito hitoVar = null;
		
		if (hito != null) {
			hitoVar = new Hito();
			hitoVar.setIdHito(hito.getInt("ID_HITO"));
			hitoVar.setIdPcd(hito.getInt("ID_PCD"));
			hitoVar.setIdFase(hito.getInt("ID_FASE"));
			hitoVar.setIdTramite(hito.getInt("ID_TRAMITE"));
			hitoVar.setTipoDoc(hito.getInt("TIPO_DOC"));
			hitoVar.setIdObjeto(hito.getString("ID_OBJETO"));
			hitoVar.setIdEvento(hito.getInt("ID_EVENTO"));
			hitoVar.setEstado(hito.getInt("ESTADO"));
			hitoVar.setIdAplicacion(hito.getInt("ID_APLICACION"));
			hitoVar.setIpMaquina(hito.getString("IP_MAQUINA"));
			hitoVar.setFecha(hito.getDate("FECHA"));
			hitoVar.setIdSistema(hito.getString("ID_SISTEMA"));
			hitoVar.setInfoAux(hito.getString("INFO_AUX"));
			hitoVar.setIdInfo(hito.getInt("ID_INFO"));
		}

		return hitoVar;

	}

	/**
	 * Se obtiene un objeto Error mediante el objeto iitem que contiene la
	 * información del error
	 * 
	 * @param error
	 *            IItem de error
	 * @return Objeto de tipo error
	 * @throws Exception
	 */

	private Error getError(IItem error) throws Exception {

		Error errorVar = null;
		
		if (error != null) {
			errorVar = new Error();
			errorVar.setIdHito(error.getInt("ID_HITO"));
			errorVar.setIdAplicacion(error.getInt("ID_APLICACION"));
			errorVar.setIdSistema(error.getString("ID_SISTEMA"));
			errorVar.setIdObjeto(error.getString("ID_OBJETO"));
			errorVar.setDescripcion(error.getString("DESCRIPCION"));
			errorVar.setIdError(error.getInt("ID_ERROR"));
		}

		return errorVar;
	}

	/**
	 * * Se obtiene un objeto Regla mediante el objeto iitem que contiene la
	 * información de la regla
	 * 
	 * @param rule
	 *            IItem con la información de la regla
	 * @return Un objeto de tipo Regla
	 * @throws Exception
	 */
	private Regla getRegla(IItem rule) throws Exception {

		Regla regla = new Regla();

		if (rule != null) {
			regla = new Regla();
			regla.setId(rule.getInt("ID"));
			regla.setIdPcd(rule.getInt("ID_PCD"));
			regla.setIdFase(rule.getInt("ID_FASE"));
			regla.setIdTramite(rule.getInt("ID_TRAMITE"));
			regla.setTipoDoc(rule.getInt("TIPO_DOC"));
			regla.setIdEvento(rule.getInt("ID_EVENTO"));
			regla.setIdAccion(rule.getInt("ID_ACCION"));
			regla.setIdCondicion(rule.getInt("ID_CONDICION"));
			regla.setAtributos(rule.getString("ATRIBUTOS"));
			regla.setIdAplicacion(rule.getInt("ID_APLICACION"));
			regla.setOrden(rule.getInt("ORDEN"));
			regla.setIdInfo(rule.getInt("ID_INFO"));
		}

		return regla;
	}

}
