/**
 *
 */
package com.ieci.tecdoc.isicres.session.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrAddress;
import com.ieci.tecdoc.common.invesicres.ScrAddrtel;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrCaaux;
import com.ieci.tecdoc.common.invesicres.ScrDom;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrTypeaddress;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.PersonAddress;
import com.ieci.tecdoc.common.isicres.PersonAddressTel;
import com.ieci.tecdoc.common.isicres.PersonInfo;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.isicres.person.PersonValidationFactory;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class UtilsSessionEx extends UtilsSession implements ServerKeys, Keys,
		HibernateKeys {

	private static final Logger log = Logger.getLogger(UtilsSessionEx.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	public static ScrCaaux getScrCaaux(String sessionID, Integer bookID,
			String fldValue, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		ScrCaaux result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			result = ISicresQueries.getScrCaaux(session, fldValue);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrCaax bookID[" + bookID
					+ "] and fdlvalue [" + fldValue + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_FIND_ADDITIONAL_SUBJECT_INFO);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}


	/**
	 * Obtiene un tipo de asunto por el código y si está disponible para una oficina
	 * @param sessionID Cadena que contiene el identificador de Sesión
	 * @param matterCode Cadena que contiene el código de asunto
	 * @param officeCode Cadena que contiene el código de oficina
	 * @param entidad Cadena que contiene el identificador de la entidad
	 * @return {@link ScrCa} Objeto que contiene la información del tipo de asunto.
	 * 						Si no pertnece a la oficina se devuelve nulo.
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static ScrCa getScrCaByOfic(String sessionID, String matterCode, String officeCode,String entidad) throws
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ScrCa result = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);

			//Obtener la oficina
			ScrOfic scrOfic = ISicresQueries.getScrOficByCode(session, officeCode);

			//Si existe la oficina se comprueba el asunto
			if(scrOfic != null){
				result = ISSubjectsValidator.getSubjectForOfic(session, matterCode, scrOfic.getId());
			}
			else{
				if(log.isDebugEnabled()){
					log.debug("Office not exists [" + officeCode + "]");
				}
			}

			return result;
		}
		catch (Exception e) {
			log.error("Impossible to load ScrCaax code[" + matterCode
					+ "] and idOfic [" + officeCode + "] for the session ["
					+ sessionID + "]", e);
			throw new ValidationException(
					ValidationException.ERROR_GET_MATTER_FOR_OFFIC, new String[]{matterCode, officeCode});
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}



	/**
	 * Método que nos devuelve un organismo segun su codigo
	 *
	 * @param sessionID
	 * @param bookID
	 * @param code
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static ScrOrg getScrOrgByCode(String sessionID, Integer bookID,
			String code, String entidad) throws BookException,
			SessionException, ValidationException{

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		ScrOrg result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			result = ISUnitsValidator.getUnit(session, code, true, null);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrOrg bookID[" + bookID
					+ "] and code [" + code + "] for the session [" + sessionID
					+ "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_FIND_ADDITIONAL_SUBJECT_INFO);
		} finally {
			HibernateUtil.closeSession(entidad);
		}


	}

	/**
	 * @deprecated
	 * @param sessionID
	 * @param personId
	 * @param idAddress
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static List getScrAddress(String sessionID, int personId,
			int idAddress, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		List result = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = ISicresQueries.getScrAddress(session, personId, idAddress);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrAddress personId[" + personId
					+ "] and idAddress[" + idAddress + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ADDRESS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
     * @deprecated
     * @param sessionID
     * @param idAddress
     * @param entidad
     * @return
     * @throws BookException
     * @throws SessionException
     * @throws ValidationException
     */
	public static List getScrDom(String sessionID, int idAddress, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		List result = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = ISicresQueries.getScrDom(session, idAddress);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrDom idAddress[" + idAddress
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_DOM);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
     * @deprecated
     * @param sessionID
     * @param idAddress
     * @param entidad
     * @return
     * @throws BookException
     * @throws SessionException
     * @throws ValidationException
     */
	public static List getScrAddrtel(String sessionID, int idAddress,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		List result = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = ISicresQueries.getScrAddrtel(session, idAddress);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrAddrtel idAddress[" + idAddress
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ADDRTEL);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static int addScrPfiPjur(String sessionID, String strDoc,
			String strName, String strApe1, String strApe2, int strTipoDoc,
			String strDirecciones, String strDireccionesTel, int strTipoPer,
			Integer idPerson, String entidad) throws BookException,
			SessionException, ValidationException {
		String result = null;
		try {
			PersonInfo perInfo = new PersonInfo();
			perInfo.setSessionId(sessionID);
			perInfo.setId(idPerson.toString());
			perInfo.setType(Integer.toString(strTipoPer));
			perInfo.setName(strName);
			perInfo.setFirstName(strApe1);
			perInfo.setSecondName(strApe2);
			perInfo.setTypeDoc(Integer.toString(strTipoDoc));
			perInfo.setNif(strDoc);
			perInfo.setAddresses(getDoms(strDirecciones));
			perInfo.setAddressesTel(getDirTels(strDireccionesTel));

			String xmlPersonInfo = XMLPersons.createXMLSaveOrModifPersonInfo(
					perInfo, entidad);
			result = PersonValidationFactory.getCurrentPersonValidation()
					.create(xmlPersonInfo);

			return new Integer(result).intValue();
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			if (strTipoPer == 1) {
				log.error("Impossible to add a ScrPfis for the session ["
						+ sessionID + "]", e);
				throw new BookException(BookException.ERROR_CANNOT_ADD_PFIS);
			} else {
				log.error("Impossible to add a ScrPjur for the session ["
						+ sessionID + "]", e);
				throw new BookException(BookException.ERROR_CANNOT_ADD_PJUR);
			}
		}
	}

	public static int updateScrPfiPjur(String sessionID, String strDoc,
			String strName, String strApe1, String strApe2, int strTipoDoc,
			String strDirecciones, String strDireccionesTel, int strTipoPer,
			Integer idPerson, String entidad) throws BookException,
			SessionException, ValidationException {
		String result = null;
		try {
			PersonInfo perInfo = new PersonInfo();
			perInfo.setSessionId(sessionID);
			perInfo.setId(idPerson.toString());
			perInfo.setType(Integer.toString(strTipoPer));
			perInfo.setName(strName);
			perInfo.setFirstName(strApe1);
			perInfo.setSecondName(strApe2);
			perInfo.setTypeDoc(Integer.toString(strTipoDoc));
			perInfo.setNif(strDoc);
			perInfo.setAddresses(getDoms(strDirecciones));
			perInfo.setAddressesTel(getDirTels(strDireccionesTel));
			String xmlPersonInfo = XMLPersons.createXMLSaveOrModifPersonInfo(
					perInfo, entidad);
			result = PersonValidationFactory.getCurrentPersonValidation()
					.update(xmlPersonInfo);

			return new Integer(result).intValue();
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			if (strTipoPer == 1) {
				log.error("Impossible to update a ScrPfis for the session ["
						+ sessionID + "]", e);
				throw new BookException(BookException.ERROR_CANNOT_UPDATE_PFIS);
			} else {
				log.error("Impossible to update a ScrPjur for the session ["
						+ sessionID + "]", e);
				throw new BookException(BookException.ERROR_CANNOT_UPDATE_PJUR);
			}
		}
	}

	public static List getScrRegisterInter(Integer bookId, int fdrId,
			boolean orderByOrd, String entidad) throws BookException {
		List list = null;
		// Transaction tran = null;
		try {
			// Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			list = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getScrRegisterInter(bookId, fdrId, orderByOrd, entidad);
			// HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrRegInt bookID[" + bookId
					+ "] and fdlid[" + fdrId + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_FIND_INTEREST_LIST);
			// } finally {
			// HibernateUtil.closeSession(entidad);
		}
		return list;
	}

    public static ScrAddress getInterAddress(String sessionID, Integer personId,
			Integer idAddress, String entidad) throws AttributesException,
			Exception {
    	Document doc = null;

		String xmlParamId = XMLPersons.createXMLParamIdInfo(
				personId.toString(), sessionID, entidad);
		String interAddress = PersonValidationFactory
				.getCurrentPersonValidation().getInfo(xmlParamId);
		try {
			doc = XMLPersons.createFromStringText(interAddress);
		} catch (Exception e) {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}

		List nodeListDoms = doc.selectNodes(XPATH_PERSONA_DOMICILIO);
		if (!nodeListDoms.isEmpty()) {
			for (Iterator it = nodeListDoms.iterator(); it.hasNext();) {
				Element node = (Element) it.next();
				if (idAddress.intValue() == new Integer(((Element) node
						.selectObject(XML_ID_TEXT)).getText()).intValue()) {
					ScrAddress scrAddress = new ScrAddress();
					scrAddress.setId(idAddress);
					scrAddress.setType(0);
					scrAddress.setIdPerson(personId.intValue());
					return scrAddress;
				}
			}
		}

		List nodeListAddrTel = doc.selectNodes(XPATH_PERSONA_DIRECCION_TEL);
		if (!nodeListAddrTel.isEmpty()) {
			for (Iterator it = nodeListAddrTel.iterator(); it.hasNext();) {
				Element node = (Element) it.next();
				if (idAddress.intValue() == new Integer(((Element) node
						.selectObject(XML_ID_TEXT_TEL)).getText()).intValue()) {
					ScrAddress scrAddress = new ScrAddress();
					scrAddress.setId(idAddress);
					scrAddress.setType(1);
					scrAddress.setIdPerson(personId.intValue());
					return scrAddress;
				}
			}
		}

		return null;
    }

	public static ScrDom getInterDom(String sessionID, int personId, int domId,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		ScrDom dom = null;
		AuthenticationUser user = null;
		try {

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			user = (AuthenticationUser) cacheBag.get(HIBERNATE_Iuseruserhdr);

			dom = getDom(user.getId(), personId, domId, sessionID, entidad);
			return dom;

		} catch (Exception e) {
			log.error("Impossible getInterDoms for the session [" + sessionID
					+ "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ADDRESS);
		}
	}

	public static ScrAddrtel getInterAddrtel(String sessionID, int personId,
			int domId, String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		ScrAddrtel addrtel = null;
		AuthenticationUser user = null;
		try {

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			user = (AuthenticationUser) cacheBag.get(HIBERNATE_Iuseruserhdr);

			addrtel = getAddrtel(user.getId(), personId, domId, sessionID,
					entidad);
			return addrtel;

		} catch (Exception e) {
			log.error("Impossible getInterAddrtel for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ADDRESS);
		}
	}

	public static List getAllScrOficByUser(String sessionID, String entidad)
			throws TecDocException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		List result = new ArrayList();
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			List deptList = user.getDeptList();
			for (Iterator iterator = deptList.iterator(); iterator.hasNext();) {
				Integer deptId = (Integer) iterator.next();

				ScrOfic scrOfic = ISicresQueries.getScrOficByDeptId(session,
						deptId);
				result.add(scrOfic);
			}
			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			throw new BookException(BookException.ERROR_BOOK_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return result;
	}

	public static ScrOfic getScrOficByDeptId(String sessionID, Integer deptId,
			String entidad) throws TecDocException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		ScrOfic result = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			result = ISicresQueries.getScrOficByDeptId(session, deptId);

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			throw new BookException(BookException.ERROR_BOOK_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return result;
	}

	public static ScrOfic get (String sessionID, Integer deptId,
			String entidad) throws TecDocException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		ScrOfic result = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			result = ISicresQueries.getScrOficByDeptId(session, deptId);

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			throw new BookException(BookException.ERROR_BOOK_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return result;
	}



	/***************************************************************************
	 * PRIVATE METHOD
	 **************************************************************************/

	private static ScrDom getDom(Integer userId, int personId, int domId,
			String sessionID, String entidad) throws Exception {
		ScrDom dom = null;
		Document doc = null;
		List nodeList = null;

		String xmlParamId = XMLPersons.createXMLParamIdInfo(new Integer(
				personId).toString(), sessionID, entidad);
		String interAddress = PersonValidationFactory
				.getCurrentPersonValidation().getInfo(xmlParamId);
		try {
			doc = XMLPersons.createFromStringText(interAddress);
		} catch (Exception e) {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}
		nodeList = doc.selectNodes(XPATH_PERSONA_DOMICILIO);

		if (!nodeList.isEmpty()) {
			Element node = null;
			for (Iterator it = nodeList.iterator(); it.hasNext();) {
				node = (Element) it.next();
				if (domId == new Integer(((Element) node
						.selectObject(XML_ID_TEXT)).getText()).intValue()) {
					dom = new ScrDom();
					dom.setAddress(((Element) node
							.selectObject(XML_DIRECCION_TEXT)).getText());
					dom.setCity(((Element) node
							.selectObject(XML_POBLACION_TEXT)).getText());
					dom.setCountry(((Element) node
							.selectObject(XML_PROVINCIA_TEXT)).getText());
					dom.setId(new Integer(((Element) node
							.selectObject(XML_ID_TEXT)).getText()));
					dom.setPreference(new Integer(((Element) node
							.selectObject(XML_PREFERENCIA_TEXT)).getText())
							.intValue());
					dom
							.setZip(((Element) node
									.selectObject(XML_CODPOSTAL_TEXT))
									.getText());
					break;
				}
			}
		}
		return dom;
	}

	private static ScrAddrtel getAddrtel(Integer userId, int personId,
			int domId, String sessionID, String entidad) throws Exception {
		ScrAddrtel addrtel = null;
		Document doc = null;
		List nodeList = null;

		String xmlParamId = XMLPersons.createXMLParamIdInfo(new Integer(
				personId).toString(), sessionID, entidad);
		String interAddress = PersonValidationFactory
				.getCurrentPersonValidation().getInfo(xmlParamId);
		try {
			doc = XMLPersons.createFromStringText(interAddress);
		} catch (Exception e) {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}
		nodeList = doc.selectNodes(XPATH_PERSONA_DIRECCION_TEL);

		if (!nodeList.isEmpty()) {
			Element node = null;
			for (Iterator it = nodeList.iterator(); it.hasNext();) {
				node = (Element) it.next();
				if (domId == new Integer(((Element) node
						.selectObject(XML_ID_TEXT_TEL)).getText()).intValue()) {
					addrtel = new ScrAddrtel();
					addrtel.setAddress(((Element) node
							.selectObject(XML_DIRECCION_TEXT_TEL)).getText());
					ScrTypeaddress tipo = new ScrTypeaddress();
					tipo.setId(new Integer(((Element) node
							.selectObject(XML_TYPE_ADDRTEL_TEXT)).getText()));
					addrtel.setScrTypeaddress(tipo);
					addrtel.setId(new Integer(((Element) node
							.selectObject(XML_ID_TEXT_TEL)).getText())
							.intValue());
					addrtel.setPreference(new Integer(((Element) node
							.selectObject(XML_PREFERENCIA_TEXT_TEL)).getText())
							.intValue());
					break;
				}
			}
		}
		return addrtel;
	}

	private static List getDoms(String strDirecciones) {
		StringTokenizer tokenizer = new StringTokenizer(strDirecciones,
				AMPERSAN);
		String[] fields = null;
		PersonAddress perAddress = null;
		List addresses = new ArrayList();
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer1 = new StringTokenizer(tokenizer
					.nextToken(), BARRA, true);
			String aux = null;
			String anterior = null;
			int i = 0;
			fields = new String[7];
			aux = tokenizer1.nextToken();
			while (tokenizer1.hasMoreTokens()) {
				if (!aux.equals(BARRA)) {
					fields[i] = aux;
					anterior = aux;
					aux = tokenizer1.nextToken();
					i++;
				} else if (anterior.equals(BARRA)) {
					fields[i] = "";
					anterior = aux;
					aux = tokenizer1.nextToken();
					i++;
				} else {
					anterior = aux;
					aux = tokenizer1.nextToken();
				}
			}
			perAddress = new PersonAddress();
			fields[i] = aux;
			perAddress.setDom(fields[1]);
			perAddress.setZip(fields[2]);
			perAddress.setCity(fields[3]);
			perAddress.setProvince(fields[4]);
			perAddress.setId(fields[0]);
			perAddress.setPreference(fields[5]);
			perAddress.setToDelete(fields[6]);
			addresses.add(perAddress);

		}
		return addresses;
	}

	private static List getDirTels(String strDireccionesTel) {
		StringTokenizer tokenizer = new StringTokenizer(strDireccionesTel,
				AMPERSAN);
		String[] fields = null;
		PersonAddressTel perAddressTel = null;
		List addressesTel = new ArrayList();
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer1 = new StringTokenizer(tokenizer
					.nextToken(), BARRA, true);
			String aux = null;
			String anterior = null;
			int i = 0;
			fields = new String[5];
			aux = tokenizer1.nextToken();
			while (tokenizer1.hasMoreTokens()) {
				if (!aux.equals(BARRA)) {
					fields[i] = aux;
					anterior = aux;
					aux = tokenizer1.nextToken();
					i++;
				} else if (anterior.equals(BARRA)) {
					fields[i] = "";
					anterior = aux;
					aux = tokenizer1.nextToken();
					i++;
				} else {
					anterior = aux;
					aux = tokenizer1.nextToken();
				}
			}
			perAddressTel = new PersonAddressTel();
			fields[i] = aux;
			perAddressTel.setDirTel(fields[1]);
			perAddressTel.setType(fields[2]);
			perAddressTel.setId(fields[0]);
			perAddressTel.setPreference(fields[3]);
			perAddressTel.setToDelete(fields[4]);
			addressesTel.add(perAddressTel);

		}
		return addressesTel;
	}



}
