package ieci.tecdoc.sgm.publicador.ws.client;


import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.publicador.PublicadorException;
import ieci.tecdoc.sgm.core.services.publicador.ServicioPublicador;
import ieci.tecdoc.sgm.core.services.publicador.dto.Error;
import ieci.tecdoc.sgm.core.services.publicador.dto.Hito;
import ieci.tecdoc.sgm.core.services.publicador.dto.Regla;

import java.rmi.RemoteException;

import org.apache.axis.utils.StringUtils;

public class ServicioPublicadorRemoteClient implements ServicioPublicador {

	private PublicadorWebService service = null;

	public void setService(PublicadorWebService service) {
		this.service = service;
	}

	private PublicadorException getPublicadorException(IRetornoServicio ret) {
		return new PublicadorException(Long.valueOf(ret.getErrorCode()).longValue());
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
		
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.ListaHitos oResult = service.getListaHitos(idEntidad);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getHitos(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
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
	 * @param idSystem :
	 *            Identificador del sistema
	 * @return Un objeto Hito con la información del hito
	 * @throws PublicadorException
	 */
	public Hito getHito(String idEntidad, int idHito, int idAplicacion, String idSystem) 
			throws PublicadorException {
		try {
			if (validateHito(idHito, idAplicacion, idSystem, idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito oResult = service
						.getHito(idEntidad, idHito, idAplicacion, idSystem);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getHitoCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
	 * @return Hito creado
	 * @throws PublicadorException
	 */
	public Hito addHito(String idEntidad, Hito hito) throws PublicadorException {
		try {

			if (validateHito(hito, idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito hitoObj = getHito(hito);
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito oResult = service.addHito(idEntidad, hitoObj);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getHitoCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, e .getMessage(), e);
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
	 * @return Información del hito modificado
	 * @throws PublicadorException
	 */
	public Hito updateHito(String idEntidad, Hito hito)
			throws PublicadorException {
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito hitoObj = getHito(hito);
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito oResult = service.updateHito(idEntidad, hitoObj);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getHitoCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {

				throw new PublicadorException(
						PublicadorException.PARAMETERS_INVALID);

			}
		} catch (RemoteException e) {
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e
							.getMessage(), e);
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
		try {
			if (validateHito(idHito, applicationId, systemId, idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Booleano oResult = service
						.deleteHito(idEntidad, idHito, applicationId, systemId);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return true;
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {

				throw new PublicadorException(
						PublicadorException.PARAMETERS_INVALID);

			}
		} catch (RemoteException e) {
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e
							.getMessage(), e);
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
	 * @return Información del hito reactivado
	 * @throws PublicadorException
	 */
	public Hito reactivateHito(String idEntidad, int idHito, int applicationId, String systemId)
			throws PublicadorException {
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Hito oResult = service
						.reactivateHito(idEntidad, idHito, applicationId, systemId);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getHitoCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {

				throw new PublicadorException(
						PublicadorException.PARAMETERS_INVALID);

			}
		} catch (RemoteException e) {
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e
							.getMessage(), e);
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
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.ListaReglas oResult = service
						.getListaReglas(idEntidad);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getReglas(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(
					PublicadorException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
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
	public Regla getRegla(String idEntidad, int idRegla)
			throws PublicadorException {
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Regla oResult = service
						.getRegla(idEntidad, idRegla);

				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getReglaCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws PublicadorException
	 */
	public Regla addRegla(String idEntidad, Regla regla)
			throws PublicadorException {
		try {
			if (validateRegla(regla, idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Regla reglaObj = getRegla(regla);
				ieci.tecdoc.sgm.publicador.ws.client.dto.Regla oResult = service
						.addRegla(idEntidad, reglaObj);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getReglaCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
				throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
						e.getMessage(), e);
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
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws PublicadorException
	 */
	public Regla updateRegla(String idEntidad, Regla regla)
			throws PublicadorException {
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Regla reglaObj = getRegla(regla);
				ieci.tecdoc.sgm.publicador.ws.client.dto.Regla oResult = service
						.updateRegla(idEntidad, reglaObj);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getReglaCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
		try {
			if (!StringUtils.isEmpty(idEntidad) && idRegla != 0) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.Booleano oResult = service
						.deleteRegla(idEntidad, idRegla);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return true;
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
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
		try {
			if (!StringUtils.isEmpty(idEntidad)) {
				ieci.tecdoc.sgm.publicador.ws.client.dto.ListaErrores oResult = service
						.getListaErrores(idEntidad);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getErrores(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
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
	 * @param applicationId:
	 *            Identificador de la aplicacion
	 * @param systemId :
	 *            Identificador del sistema
	 * @return Una lista con los errores
	 * @throws PublicadorException
	 */
	public Error getError(String idEntidad, int idHito, int applicationId, String systemId) 
			throws PublicadorException {
		try {
			if (!StringUtils.isEmpty(idEntidad)) {

				ieci.tecdoc.sgm.publicador.ws.client.dto.Error oResult = service
						.getError(idEntidad, idHito, applicationId, systemId);
				if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
					return getErrorCore(oResult);
				} else {
					throw getPublicadorException((IRetornoServicio)oResult);
				}
			} else {
				throw new PublicadorException(PublicadorException.PARAMETERS_INVALID);
			}
		} catch (RemoteException e) {
			throw new PublicadorException(PublicadorException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}


	
	/*
	 * ===================================================================
	 * Métodos de validación de parámetros
	 * ===================================================================
	 */
		
	/**
	 * Cierto si estan informados los campos mínimos de un hito , falso en caso
	 * contrario
	 * 
	 * @param hito
	 *            Objeto hito del core de sigem
	 * @return
	 */
	public boolean validateHito(Hito hito, String idEntidad) {

		if (hito == null || hito.getIdHito() == 0
				|| hito.getIdAplicacion() == 0
				|| StringUtils.isEmpty(hito.getIdSistema())
				|| StringUtils.isEmpty(idEntidad)) {
			return false;
		}
		return true;
	}

	public boolean validateHito(int idHito, int applicationId, String systemId,
			String idEntidad) {

		if (idHito == 0 || applicationId == 0 || StringUtils.isEmpty(systemId)
				|| StringUtils.isEmpty(idEntidad)) {
			return false;
		}
		return true;
	}

	/**
	 * Valida la información obligatoria de una regla
	 * 
	 * @param regla
	 * @param idEntidad
	 * @return
	 */
	public boolean validateRegla(Regla regla, String idEntidad) {

		if (regla == null || regla.getId() == 0
				|| StringUtils.isEmpty(idEntidad)) {
			return false;
		}
		return true;
	}

	/**
	 * Valida la información obligatoria de un error
	 * 
	 * @param idEntidad
	 * @param idHito
	 * @param applicationId
	 * @param systemId
	 * @param objectId
	 * @return
	 */
	public boolean validateError(String idEntidad, int idHito,
			int applicationId, String systemId, String objectId) {

		if (StringUtils.isEmpty(idEntidad) || idHito == 0 || applicationId == 0
				|| StringUtils.isEmpty(systemId)
				|| StringUtils.isEmpty(objectId)) {
			return false;
		}
		return true;
	}

	
	/*
	 * ===================================================================
	 * Métodos de conversión de tipos
	 * ===================================================================
	 */
	
	private void copyHito(ieci.tecdoc.sgm.publicador.ws.client.dto.Hito hito, Hito hitoObj) {
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

	private static void copyRegla(ieci.tecdoc.sgm.publicador.ws.client.dto.Regla regla, Regla reglaObj) {

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
	
	private Regla[] getReglas(ieci.tecdoc.sgm.publicador.ws.client.dto.ListaReglas reglas) {

		Regla[] reglasArray = null;

		if (reglas != null) {

			ieci.tecdoc.sgm.publicador.ws.client.dto.Regla[] aux = reglas.getReglas();
			reglasArray = new Regla[aux.length];
			for (int i = 0; i < aux.length; i++) {
				reglasArray[i] = getReglaCore((ieci.tecdoc.sgm.publicador.ws.client.dto.Regla) aux[i]);
			}
		}

		return reglasArray;
	}

	private Error[] getErrores(ieci.tecdoc.sgm.publicador.ws.client.dto.ListaErrores errores) {

		Error[] erroresArray = null;

		if (errores != null) {

			ieci.tecdoc.sgm.publicador.ws.client.dto.Error[] aux = errores.getErrores();
			erroresArray = new Error[aux.length];
			for (int i = 0; i < aux.length; i++) {
				erroresArray[i] = getErrorCore(aux[i]);
			}
		}

		return erroresArray;
	}

	private Hito[] getHitos(ieci.tecdoc.sgm.publicador.ws.client.dto.ListaHitos hitos) {

		Hito[] hitosArray = null;

		if (hitos != null) {

			ieci.tecdoc.sgm.publicador.ws.client.dto.Hito[] aux = hitos.getHitos();
			hitosArray = new Hito[aux.length];
			for (int i = 0; i < aux.length; i++) {
				hitosArray[i] = getHitoCore(aux[i]);
			}
		}

		return hitosArray;
	}

	private ieci.tecdoc.sgm.publicador.ws.client.dto.Hito getHito(Hito hito) {

		ieci.tecdoc.sgm.publicador.ws.client.dto.Hito hitoObj = null;
		
		if (hito != null) {
			hitoObj = new ieci.tecdoc.sgm.publicador.ws.client.dto.Hito();
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
		
		return hitoObj;

	}

	private Hito getHitoCore(ieci.tecdoc.sgm.publicador.ws.client.dto.Hito hito) {

		Hito hitoObj = null;
		
		if (hito != null) {
			hitoObj = new Hito();
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
		
		return hitoObj;
	}

	private ieci.tecdoc.sgm.publicador.ws.client.dto.Regla getRegla(Regla regla) {

		ieci.tecdoc.sgm.publicador.ws.client.dto.Regla reglaObj = null;
		
		if (regla != null) {
			reglaObj = new ieci.tecdoc.sgm.publicador.ws.client.dto.Regla();
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
		
		return reglaObj;

	}

	private Regla getReglaCore(ieci.tecdoc.sgm.publicador.ws.client.dto.Regla regla) {

		Regla reglaObj = null;
		
		if (regla != null) {
			reglaObj = new Regla();
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
		
		return reglaObj;
	}

	private Error getErrorCore(ieci.tecdoc.sgm.publicador.ws.client.dto.Error error) {
		
		Error errorObj = null;
		
		if (error != null) {
			errorObj = new Error();
			errorObj.setDescripcion(error.getDescripcion());
			errorObj.setIdAplicacion(error.getIdAplicacion());
			errorObj.setIdError(error.getIdError());
			errorObj.setIdHito(error.getIdHito());
			errorObj.setIdObjeto(error.getIdObjeto());
			errorObj.setIdSistema(error.getIdSistema());

		}

		return errorObj;
	}
}
