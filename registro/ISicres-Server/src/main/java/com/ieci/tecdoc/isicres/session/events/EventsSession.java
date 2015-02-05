//
//FileName: SendEmails.java
//
package com.ieci.tecdoc.isicres.session.events;

import gnu.trove.THashMap;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.adapter.RuleContext;
import com.ieci.tecdoc.isicres.events.EventsFactory;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author IECISA
 *
 *         Clase encarga de realizar las llamadas a los eventos correspondientes
 *
 */
public class EventsSession implements ServerKeys, HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(EventsSession.class);

	/**
	 * Llamada al evento que se lanza realizando la operación implementada
	 * cuando un resgistro es distribuido manualmente
	 *
	 * @param sessionID
	 * @param eventId
	 * @param bookId
	 * @param folderId
	 * @param axsf
	 * @param userType
	 * @param userId
	 * @param distId
	 * @param messageForUser
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws EventsException
	 */
	public static Object distributionEvent(String sessionID, String eventId,
			Integer bookId, Integer folderId, AxSf axsf, Integer userType,
			Integer userId, Integer distId, String messageForUser,
			String entidad) throws DistributionException, SessionException,
			ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx.setAxsf(axsf);
			ruleCtx.setIdocarchdet(idoc);
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setId(distId);
			ruleCtx.setLibro(bookId);
			ruleCtx.setRegistro(folderId);
			ruleCtx.setIdDest(userId);
			ruleCtx.setTypeDest(userType);
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			ruleCtx.setMessage(messageForUser);

			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de distribucion manual de la distribucion ["
							+ distId + "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		}

		return result;

	}

	/**
	 * Llamada al evento que se lanza cuando un registro distribuido pasa al
	 * estado aceptado
	 *
	 * @param sessionID
	 * @param eventId
	 * @param scrDistReg
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object distAceptEvent(String sessionID, String eventId,
			ScrDistreg scrDistReg, String entidad)
			throws DistributionException, SessionException, ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx = new RuleContext();
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(new Integer(scrDistReg.getIdArch()));
			ruleCtx.setRegistro(new Integer(scrDistReg.getIdFdr()));
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de aceptacion de la distribucion ["
							+ scrDistReg.getId() + "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}

		return result;

	}

	/**
	 * Llamada al evento que se lanza cuando un registro distribuido pasa al
	 * estado archivado
	 *
	 * @param sessionID
	 * @param eventId
	 * @param scrDistReg
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object distArchiveEvent(String sessionID, String eventId,
			ScrDistreg scrDistReg, String entidad)
			throws DistributionException, SessionException, ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx = new RuleContext();
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(new Integer(scrDistReg.getIdArch()));
			ruleCtx.setRegistro(new Integer(scrDistReg.getIdFdr()));
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de archivo de la distribucion ["
							+ scrDistReg.getId() + "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}

		return result;
	}

	/**
	 * Llamada al evento que se lanza cuando un registro distribuido pasa al
	 * estado rechazado.
	 *
	 * @param sessionID
	 * @param eventId
	 * @param scrDistReg
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object distRejectEvent(String sessionID, String eventId,
			ScrDistreg scrDistReg, String entidad)
			throws DistributionException, SessionException, ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx = new RuleContext();
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(new Integer(scrDistReg.getIdArch()));
			ruleCtx.setRegistro(new Integer(scrDistReg.getIdFdr()));
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de rechazo de la distribucion ["
							+ scrDistReg.getId() + "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}

		return result;
	}

	/**
	 * Llamada al evento que se lanza cuando un registro distribuido pasa al
	 * estado redistribuido
	 *
	 * @param sessionID
	 * @param eventId
	 * @param scrDistReg
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object distDestChangeEvent(String sessionID, String eventId,
			ScrDistreg scrDistReg, String entidad)
			throws DistributionException, SessionException, ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx = new RuleContext();
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(new Integer(scrDistReg.getIdArch()));
			ruleCtx.setRegistro(new Integer(scrDistReg.getIdFdr()));
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de cambio de destino de la distribucion ["
							+ scrDistReg.getId() + "]");
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}

		return result;
	}

	/**
	 * Llamada al evento que se lanza cuando se crea un registro de entrada
	 *
	 * @param sessionID
	 * @param eventId
	 * @param bookId
	 * @param axsfNew
	 * @param entidad
	 * @return Object
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object registerCreateEvent(String sessionID, String eventId,
			Integer bookId, AxSf axsfNew, String entidad) throws BookException,
			SessionException, ValidationException {

		RuleContext ruleCtx = null;
		Object result = null;
		CacheBag cacheBag = null;
		AuthenticationUser user = null;
		ScrOfic scrofic = null;

		if (log.isDebugEnabled()) {
			log.debug("Event eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			ruleCtx = new RuleContext();
			ruleCtx.setAxsf(axsfNew);
			ruleCtx.setIdocarchdet(idoc);
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(bookId);
			ruleCtx.setRegistro((Integer) axsfNew
					.getAttributeValue("fdrid"));
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);
			AxSf axsfAux = (AxSf) EventsFactory.getCurrentEvent(
					ruleCtx.getEventId()).execute(ruleCtx);
			axsfNew = axsfAux;
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de creacion del registro ["
							+ axsfNew
									.getAttributeValueAsString(AxSf.FDRID_FIELD)
							+ "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for create register for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER, e);
		}

		return result;
	}

	/**
	 * Llamada al evento que se lanza cuando se modifica un registro de entrada
	 *
	 * @param sessionID
	 * @param eventId
	 * @param bookId
	 * @param folderId
	 * @param entidad
	 * @return Object
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Object registerModifEvent(String sessionID, String eventId,
			Integer bookId, Integer folderId, String entidad)
			throws DistributionException, SessionException, ValidationException {
		Object result = null;

		if (log.isDebugEnabled()) {
			log.debug("distributionEx eventId [" + eventId + "]");
		}

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		// Recuperamos la sesión
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			RuleContext ruleCtx = new RuleContext();
			ruleCtx.setIdocarchdet(idoc);
			ruleCtx.setUsuario(user.getId().toString());
			ruleCtx.setSessionId(sessionID);
			ruleCtx.setEventId(eventId);
			ruleCtx.setLibro(bookId);
			ruleCtx.setRegistro(folderId);
			if (scrofic != null) {
				ruleCtx.setOficina(scrofic.getId());
			}
			ruleCtx.setEntidad(entidad);

			result = EventsFactory.getCurrentEvent(ruleCtx.getEventId())
					.execute(ruleCtx);
		} catch (EventException ee) {
			log
					.error("Se ha producido un error durante la ejecucion del evento de modificacion del registro ["
							+ folderId + "]");
			throw ee;
		} catch (SessionException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"Impossible to launch event for externe distribution for the session ["
							+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		}

		return result;

	}

}