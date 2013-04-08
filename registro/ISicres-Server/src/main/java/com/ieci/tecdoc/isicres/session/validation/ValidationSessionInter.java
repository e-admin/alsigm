/**
 *
 */
package com.ieci.tecdoc.isicres.session.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.adapter.PersonCriteria;
import com.ieci.tecdoc.common.utils.adapter.PersonKeys;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.isicres.person.PersonValidationFactory;
import com.ieci.tecdoc.utils.Validator;

/**
 * @author 66575267
 *
 */
public class ValidationSessionInter extends ValidationSessionUtil implements
		ServerKeys, HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger
			.getLogger(ValidationSessionInter.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	/**
	 * Obtiene los interesados que cumplen con el criterio
	 *
	 * @param sessionID
	 * @param firstRow
	 * @param code - Valor por el que se busca
	 * @param maxResults
	 * @param rangoDefault
	 * @param entidad
	 * @return String - Documento XML con el resultado de la busqueda
	 *
	 * @throws AttributesException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static String getValidateInt(String sessionID, int firstRow,
			String code, int maxResults, int rangoDefault, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		String result = null;

		try {
			//Busca interesados por NIF/CIF IGUAL el codigo
			int numPFIS = getNumPerson(sessionID, "nif", 0, code, 1,
					new Integer(firstRow), new Integer(0), null, false, entidad);

			int numPJUR = getNumPerson(sessionID, "cif", 0, code, 2,
					new Integer(firstRow), new Integer(0), null, false, entidad);

			int flag = 1;
			if (numPFIS + numPJUR == 0) {
				//Busca interados por NIF/CIF EMPIEZA por el codigo
				numPFIS = getNumPerson(sessionID, "nif", 1, code, 1,
						new Integer(firstRow), new Integer(0), null, false,
						entidad);

				numPJUR = getNumPerson(sessionID, "cif", 1, code, 2,
						new Integer(firstRow), new Integer(0), null, false,
						entidad);
				flag = 2;
			}
			if (numPFIS + numPJUR == 0) {
				// Busca los interesados por NOMBRE EMPIEZA por el parametro
				numPFIS = getNumPerson(sessionID, "first_name", 1, code, 1,
						new Integer(firstRow), new Integer(0), null, false,
						entidad);

				numPJUR = getNumPerson(sessionID, "name", 1, code, 2,
						new Integer(firstRow), new Integer(0), null, false,
						entidad);
				flag = 3;
			}

			String xmlPfis = null;
			if (numPFIS + numPJUR == 0) {
				result = "";
			} else if (numPFIS + numPJUR > maxResults) {
				/*
				 * Para el caso de que el numero de resultado sea mayor del
				 * rango deseado.
				 */
				result = creteXMLTotalCount(firstRow, numPFIS, numPJUR,
						rangoDefault, maxResults);
			} else if (numPFIS + numPJUR <= maxResults) {
				// criteria = new ArrayList();
				if (numPFIS != 0 && firstRow < numPFIS) {
					if (flag == 1) {

						xmlPfis = getXmlPerson(sessionID, "nif", 0, code, 1,
								new Integer(firstRow),
								new Integer(rangoDefault), null, false, entidad);
					}
					if (flag == 2) {

						xmlPfis = getXmlPerson(sessionID, "nif", 1, code, 1,
								new Integer(firstRow),
								new Integer(rangoDefault), null, false, entidad);
					}
					if (flag == 3) {

						xmlPfis = getXmlPerson(sessionID, "first_name", 1,
								code, 1, new Integer(firstRow), new Integer(
										rangoDefault), null, false, entidad);
					}

				}

				String xmlPjur = null;
				if (numPJUR != 0 && (firstRow + rangoDefault) > numPFIS) {

					Integer inicio = new Integer((firstRow < numPFIS) ? 0
							: firstRow - numPFIS);
					Integer rango = new Integer(
							(firstRow < numPFIS) ? (firstRow + rangoDefault)
									- numPFIS : rangoDefault);
					if (flag == 1) {

						xmlPjur = getXmlPerson(sessionID, "cif", 0, code, 2,
								inicio, rango, null, false, entidad);
					}
					if (flag == 2) {

						xmlPjur = getXmlPerson(sessionID, "cif", 1, code, 2,
								inicio, rango, null, false, entidad);
					}
					if (flag == 3) {

						xmlPjur = getXmlPerson(sessionID, "name", 1, code, 2,
								inicio, rango, null, false, entidad);
					}

				}
				result = createXMLMix(xmlPfis, xmlPjur, firstRow, numPFIS,
						numPJUR, rangoDefault, maxResults);
			}

			return result;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load ValidateInt for session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}
	}

	public static String getDirInter(String sessionID, Integer personID,
			int maxResults, String entidad, int typeAddress)
			throws AttributesException, SessionException, ValidationException {
		return getDirTemp(sessionID, personID, maxResults, true, entidad,
				typeAddress);
	}

	public static String getBuscInter(String sessionID, Boolean searchPFis,
			Boolean searchPJur, Integer inicio, Integer rango,
			String wherePFis, String wherePJur, int maxResults, String entidad)
			throws AttributesException, SessionException, ValidationException {
		String result = "";

		try {
			Integer personType = getPersonType(searchPFis, searchPJur);

			if (personType.intValue() == 1) {

				int numPFIS = getNumPerson(sessionID, null, 0, null, 1, inicio,
						rango, wherePFis, true, entidad);
				if (numPFIS == 0) {
					result = createXMLEmpty(inicio.intValue(),
							rango.intValue(), maxResults);
				} else if (numPFIS <= maxResults) {
					if (inicio.intValue() < numPFIS) {

						String xmlPFis = getXmlPerson(sessionID, null, 0, null,
								1, inicio, rango, wherePFis, true, entidad);
						result = createXMLMix(xmlPFis, null, inicio.intValue(),
								numPFIS, 0, rango.intValue(), maxResults);
					}
				} else {
					result = "";
				}
			}
			if (personType.intValue() == 2) {

				int numPJUR = getNumPerson(sessionID, null, 0, null, 2, inicio,
						rango, wherePJur, true, entidad);
				if (numPJUR == 0) {
					result = createXMLEmpty(inicio.intValue(),
							rango.intValue(), maxResults);
				} else if (numPJUR <= maxResults) {
					if (inicio.intValue() < numPJUR) {

						String xmlPJur = getXmlPerson(sessionID, null, 0, null,
								2, inicio, rango, wherePJur, true, entidad);
						result = createXMLMix(null, xmlPJur, inicio.intValue(),
								0, numPJUR, rango.intValue(), maxResults);

					}
				} else {
					result = "";
				}
			}
			if (personType.intValue() == -1) {

				int numPFIS = getNumPerson(sessionID, null, 0, null, 1, inicio,
						rango, wherePFis, true, entidad);

				int numPJUR = getNumPerson(sessionID, null, 0, null, 2, inicio,
						rango, wherePJur, true, entidad);
				if (numPFIS + numPJUR == 0) {
					result = createXMLEmpty(inicio.intValue(),
							rango.intValue(), maxResults);
				} else if (numPFIS + numPJUR <= maxResults) {
					String xmlPFis = null;
					String xmlPJur = null;
					if (numPFIS != 0 && inicio.intValue() < numPFIS) {

						xmlPFis = getXmlPerson(sessionID, null, 0, null, 1,
								inicio, rango, wherePFis, true, entidad);
					}
					if (numPJUR != 0
							&& (inicio.intValue() + rango.intValue()) > numPFIS) {
						Integer inicio1 = new Integer(
								(inicio.intValue() < numPFIS) ? 0 : inicio
										.intValue() - numPFIS);
						Integer rango1 = new Integer(
								(inicio.intValue() < numPFIS) ? (inicio
										.intValue() + rango.intValue())
										- numPFIS : rango.intValue());

						xmlPJur = getXmlPerson(sessionID, null, 0, null, 2,
								inicio1, rango1, wherePJur, true, entidad);
					}
					result = createXMLMix(xmlPFis, xmlPJur, inicio.intValue(),
							numPFIS, numPJUR, rango.intValue(), maxResults);
				}
			}
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find inter", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}
		return result;

	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/**
	 * Author: 66575267 - Gabriel Saiz Metodo para crear un xml que indique el
	 * numero total de resultados encontrados.
	 *
	 * @param firstRow
	 * @param numPFIS
	 * @param numPJUR
	 * @param rDefault
	 * @param maxResults
	 * @return
	 */
	private static String creteXMLTotalCount(int firstRow, int numPFIS,
			int numPJUR, int rDefault, int maxResults) {
		String result = null;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(PersonKeys.XML_PERSONAS_TEXT);
		root.addAttribute(PersonKeys.XML_INICIO_TEXT,
				Integer.toString(firstRow));
		if ((firstRow + rDefault) > (numPFIS + numPJUR)) {
			root.addAttribute(PersonKeys.XML_FIN_TEXT,
					Integer.toString(numPFIS + numPJUR));
		} else {
			root.addAttribute(PersonKeys.XML_FIN_TEXT,
					Integer.toString(firstRow + rDefault));
		}
		root.addAttribute(PersonKeys.XML_TOTAL_TEXT,
				Integer.toString(numPFIS + numPJUR));
		root.addAttribute(PersonKeys.XML_RANGO_TEXT, Integer.toString(rDefault));
		root.addAttribute(PersonKeys.XML_RESULT_MAX_TEXT,
				Integer.toString(maxResults));
		result = document.asXML();

		return result;
	}

	/**
	 * Author: 66575267 - Gabriel Saiz Modificacion: Se le pasa como parametro
	 * el numero maximo de resultados.
	 *
	 * @param xmlPfis
	 * @param xmlPjur
	 * @param firstRow
	 * @param numPFIS
	 * @param numPJUR
	 * @param rDefault
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	private static String createXMLMix(String xmlPfis, String xmlPjur,
			int firstRow, int numPFIS, int numPJUR, int rDefault, int maxResults)
			throws Exception {
		String result = null;
		if (xmlPfis != null || xmlPjur != null) {
			List nodes = null;
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement(PersonKeys.XML_PERSONAS_TEXT);
			root.addAttribute(PersonKeys.XML_INICIO_TEXT,
					Integer.toString(firstRow));
			if ((firstRow + rDefault) > (numPFIS + numPJUR)) {
				root.addAttribute(PersonKeys.XML_FIN_TEXT,
						Integer.toString(numPFIS + numPJUR));
			} else {
				root.addAttribute(PersonKeys.XML_FIN_TEXT,
						Integer.toString(firstRow + rDefault));
			}
			root.addAttribute(PersonKeys.XML_TOTAL_TEXT,
					Integer.toString(numPFIS + numPJUR));
			root.addAttribute(PersonKeys.XML_RANGO_TEXT,
					Integer.toString(rDefault));
			root.addAttribute(PersonKeys.XML_RESULT_MAX_TEXT,
					Integer.toString(maxResults));
			if (xmlPfis != null) {
				Document docPfis = XMLPersons.createFromStringText(xmlPfis);
				nodes = docPfis.selectNodes(PersonKeys.XPATH_PERSONAS_PERSONA);
				for (Iterator it = nodes.iterator(); it.hasNext();) {
					Node node = (Node) it.next();
					node.setParent(null);
					root.add(node);
				}
			}

			if (xmlPjur != null) {
				Document docPJur = XMLPersons.createFromStringText(xmlPjur);
				nodes = docPJur.selectNodes(PersonKeys.XPATH_PERSONAS_PERSONA);
				for (Iterator it = nodes.iterator(); it.hasNext();) {
					Node node = (Node) it.next();
					node.setParent(null);
					root.add(node);
				}
			}
			result = document.asXML();
		}

		return result;
	}

	/**
	 * Author: 66575267 - Gabriel Saiz Modificacion: Se le pasa como parametro
	 * el numero maximo de resultados.
	 *
	 * @param firstRow
	 * @param rDefault
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	private static String createXMLEmpty(int firstRow, int rDefault,
			int maxResults) throws Exception {
		String result = null;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(PersonKeys.XML_PERSONAS_TEXT);
		root.addAttribute(PersonKeys.XML_INICIO_TEXT,
				Integer.toString(firstRow));
		root.addAttribute(PersonKeys.XML_FIN_TEXT,
				Integer.toString(firstRow + rDefault));
		root.addAttribute(PersonKeys.XML_TOTAL_TEXT, Integer.toString(0));
		root.addAttribute(PersonKeys.XML_RANGO_TEXT, Integer.toString(rDefault));
		root.addAttribute(PersonKeys.XML_RESULT_MAX_TEXT,
				Integer.toString(maxResults));
		result = document.asXML();

		return result;
	}

	private static int getNumPerson(String sessionID, String field,
			int operartor, String code, int personType, Integer inicio,
			Integer rango, String where, boolean parseWhere, String entidad)
			throws Exception {
		List criteria = null;

		if (parseWhere) {
			criteria = parseCriteriaPFisPJur(where, personType);
		} else {
			PersonCriteria pCriteria = new PersonCriteria();
			pCriteria.setField(field);
			pCriteria.setOperator(getOperatorBuscInter(operartor));
			pCriteria.setValue(code);

			criteria = new ArrayList();
			criteria.add(pCriteria);
		}

		String xmlSearchParameters = XMLPersons.createXMLSearchPersons(
				sessionID, new Integer(personType), inicio, rango, criteria,
				entidad);

		int numPerson = PersonValidationFactory.getCurrentPersonValidation()
				.count(xmlSearchParameters).intValue();

		return numPerson;
	}

	private static String getXmlPerson(String sessionID, String field,
			int operartor, String code, int personType, Integer inicio,
			Integer rango, String where, boolean parseWhere, String entidad)
			throws Exception {
		List criteria = null;

		if (parseWhere) {
			criteria = parseCriteriaPFisPJur(where, personType);
		} else {
			PersonCriteria pCriteria = new PersonCriteria();
			pCriteria.setField(field);
			pCriteria.setOperator(getOperatorBuscInter(operartor));
			pCriteria.setValue(code);

			criteria = new ArrayList();
			criteria.add(pCriteria);
		}

		String xmlSearchParameters = XMLPersons.createXMLSearchPersons(
				sessionID, new Integer(personType), inicio, rango, criteria,
				entidad);
		String xmlPerson = PersonValidationFactory.getCurrentPersonValidation()
				.search(xmlSearchParameters);

		return xmlPerson;

	}

	private static Integer getPersonType(Boolean searchPFis, Boolean searchPJur) {
		if (searchPFis.booleanValue() && searchPJur.booleanValue()) {
			return new Integer(-1);
		} else if (searchPFis.booleanValue()) {
			return new Integer(1);
		} else if (searchPJur.booleanValue()) {
			return new Integer(2);
		}
		return new Integer(-1);
	}

	private static List parseCriteriaPFisPJur(String wherePFisPJur,
			int typePerson) {
		List listPCriteria = new ArrayList();

		if (!wherePFisPJur.equals("")) {
			StringTokenizer tokens1 = new StringTokenizer(wherePFisPJur, "#");
			while (tokens1.hasMoreTokens()) {
				String token1 = tokens1.nextToken();
				StringTokenizer tokens = new StringTokenizer(token1, "|", true);
				while (tokens.hasMoreTokens()) {
					String token = tokens.nextToken();
					if (!(token.equals("|"))) {
						PersonCriteria pCriteria = getPersonCriteria(tokens,
								token, typePerson);
						listPCriteria.add(pCriteria);
					} else {
						token = tokens.nextToken();
					}
				}
			}
		}
		return listPCriteria;
	}

	private static PersonCriteria getPersonCriteria(StringTokenizer tokens,
			String token, int typePerson) {
		PersonCriteria pCriteria = new PersonCriteria();
		if (token.equals("1")) {
			if (typePerson == 1) {
				pCriteria.setField("nif");
			} else {
				pCriteria.setField("cif");
			}
		}
		if (token.equals("2")) {
			pCriteria.setField("first_name");
		}
		if (token.equals("3")) {
			pCriteria.setField("second_name");
		}
		if (token.equals("4")) {
			pCriteria.setField("surname");
		}
		if (token.equals("5")) {
			pCriteria.setField("name");
		}
		token = tokens.nextToken();
		token = tokens.nextToken();
		pCriteria.setOperator(getOperatorBuscInter(new Integer(token)
				.intValue()));
		token = tokens.nextToken();
		token = tokens.nextToken();
		pCriteria.setValue(token);

		return pCriteria;
	}

	private static String getOperatorBuscInter(int operador) {
		String operator = null;
		switch (operador) {
		case 0: {
			operator = EQUAL;
			break;
		}
		case 1: {
			operator = BEGIN_BY;
			break;
		}
		case 2: {
			operator = CONTAIN;
			break;
		}
		default: {
			operator = BEGIN_BY;
			break;
		}
		}
		return operator;
	}

}
