package com.ieci.tecdoc.common.utils.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ieci.tecdoc.common.invesicres.ScrTypeaddress;
import com.ieci.tecdoc.common.invesicres.ScrTypedoc;
import com.ieci.tecdoc.common.isicres.PersonAddress;
import com.ieci.tecdoc.common.isicres.PersonAddressTel;
import com.ieci.tecdoc.common.isicres.PersonInfo;
import com.ieci.tecdoc.common.isicres.ProvCity;

public class XMLPersons implements PersonKeys {
	private static Logger log = Logger.getLogger(XMLPersons.class);
	private static final String BLANCO = " ";
	private static final String VACIO = "";

	public static String createXMLSearchPersons(String sessionId,
			Integer personType, Integer inicio, Integer rango, List criteria,
			String entidad) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_CRITERIOS_TEXT);
		root.addAttribute(XML_SESIONID_TEXT, sessionId);
		if (personType != null) {
			root.addAttribute(XML_PERSONTYPE_TEXT, personType.toString());
		} else {
			root.addAttribute(XML_PERSONTYPE_TEXT, new Integer(-1).toString());
		}
		if (inicio != null) {
			root.addAttribute(XML_INICIO_TEXT, inicio.toString());
		} else {
			root.addAttribute(XML_INICIO_TEXT, new Integer(0).toString());
		}
		if (rango != null) {
			root.addAttribute(XML_RANGO_TEXT, rango.toString());
		} else {
			root.addAttribute(XML_RANGO_TEXT, new Integer(0).toString());
		}
		if (entidad != null) {
			root.addAttribute(XML_ENTIDADID_TEXT, entidad);
		} else {
			root.addAttribute(XML_ENTIDADID_TEXT, "");
		}
		addCriteria(root, criteria);
		if (log.isDebugEnabled()) {
			log.debug("xmlSearchPersons" + document.asXML());
		}

		return document.asXML();
	}

	public static String createXMLPersonsInfo(List personData, int total,
			CriteriaAttributes criteriaAttrs) throws Exception {
		PersonInfo pInfo = null;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_PERSONAS_TEXT);
		root.addAttribute(XML_INICIO_TEXT, criteriaAttrs.getInicio());
		int fin = getFin(total, Integer.parseInt(criteriaAttrs.getInicio()),
				Integer.parseInt(criteriaAttrs.getRango()));
		root.addAttribute(XML_FIN_TEXT, Integer.toString(fin));
		root.addAttribute(XML_TOTAL_TEXT, Integer.toString(total));
		root.addAttribute(XML_RANGO_TEXT, criteriaAttrs.getRango());
		Element person = null;

		for (Iterator it = personData.iterator(); it.hasNext();) {
			person = root.addElement(XML_PERSONA_TEXT);
			pInfo = (PersonInfo) it.next();

			if (pInfo.getId().equals(VACIO))
				person.addElement(XML_ID_TEXT).addText(BLANCO);
			else
				person.addElement(XML_ID_TEXT).addText(pInfo.getId());

			if (pInfo.getType().equals(VACIO))
				person.addElement(XML_TIPO_TEXT).addText(BLANCO);
			else
				person.addElement(XML_TIPO_TEXT).addText(pInfo.getType());

			if (pInfo.getNif().equals(VACIO))
				person.addElement(XML_NIF_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				person.addElement(XML_NIF_TEXT).add(
						DocumentHelper.createCDATA(pInfo.getNif()));

			if (pInfo.getName().equals(VACIO))
				person.addElement(XML_NOMBRE_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				person.addElement(XML_NOMBRE_TEXT).add(
						DocumentHelper.createCDATA(pInfo.getName()));

			if (pInfo.getFirstName().equals(VACIO))
				person.addElement(XML_APELLIDO1_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				person.addElement(XML_APELLIDO1_TEXT).add(
						DocumentHelper.createCDATA(pInfo.getFirstName()));

			if (pInfo.getSecondName().equals(VACIO))
				person.addElement(XML_APELLIDO2_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				person.addElement(XML_APELLIDO2_TEXT).add(
						DocumentHelper.createCDATA(pInfo.getSecondName()));

			if (pInfo.getTypeDoc().equals(VACIO))
				person.addElement(XML_TIPODOC_TEXT).addText(BLANCO);
			else
				person.addElement(XML_TIPODOC_TEXT).addText(pInfo.getTypeDoc());
		}
		return document.asXML();

	}

	public static String createXMLPersonsAddresses(List addresses)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_DOMICILIOS_TEXT);
		if (!addresses.isEmpty()) {
			List aux = null;
			PersonAddress dom = null;
			aux = addresses;
			if (!aux.isEmpty()) {
				for (Iterator it2 = aux.iterator(); it2.hasNext();) {
					dom = (PersonAddress) it2.next();
					addDom(dom, root);
				}
			}
		}
		return document.asXML();

	}

	public static String createXMLPersonsAddressesTel(List addresses)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_TELEMATICAS_TEXT);
		if (!addresses.isEmpty()) {
			List aux = null;
			PersonAddressTel dirtel = null;
			aux = addresses;
			if (!aux.isEmpty()) {
				for (Iterator it2 = aux.iterator(); it2.hasNext();) {
					dirtel = (PersonAddressTel) it2.next();
					addDirtel(dirtel, root);
				}
			}
		}
		return document.asXML();

	}

	public static String createXMLProvCities(List provCities, boolean type)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = null;
		if (type) {
			root = document.addElement(XML_CIUDADES_TEXT);
		} else {
			root = document.addElement(XML_PROVINCIAS_TEXT);
		}

		ProvCity pC = null;
		Element cityProvElement = null;
		for (Iterator it = provCities.iterator(); it.hasNext();) {
			pC = (ProvCity) it.next();
			if (type) {
				cityProvElement = root.addElement(XML_CIUDAD_TEXT);
				cityProvElement.addElement(XML_ID_TEXT).setText(pC.getId());
				cityProvElement.addElement(XML_CODIGO_TEXT).setText(
						pC.getCode());
				cityProvElement.addElement(XML_NOMBRE_TEXT).add(
						DocumentHelper.createCDATA(pC.getName()));
				cityProvElement.addElement(XML_IDPROVINCIA_TEXT).setText(
						pC.getProvId());
			} else {
				cityProvElement = root.addElement(XML_PROVINCIA_TEXT);
				cityProvElement.addElement(XML_ID_TEXT).setText(pC.getId());
				cityProvElement.addElement(XML_CODIGO_TEXT).setText(
						pC.getCode());
				cityProvElement.addElement(XML_NOMBRE_TEXT).add(
						DocumentHelper.createCDATA(pC.getName()));
			}
		}
		return document.asXML();

	}

	public static String createXMLTypeDocs(List typeDocsData) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_TYPEDOCS_TEXT);

		Element typeDocElement = null;
		for (Iterator iterator = typeDocsData.iterator(); iterator.hasNext();) {
			ScrTypedoc scrTypedoc = (ScrTypedoc) iterator.next();
			typeDocElement = root.addElement(XML_TYPEDOC_TEXT);

			typeDocElement.addElement(XML_ID_TEXT).setText(
					scrTypedoc.getId().toString());
			typeDocElement.addElement(XML_DESCRIPCION_TEXT).add(
					DocumentHelper.createCDATA(scrTypedoc.getDescription()));
			typeDocElement.addElement(XML_TYPEPERSONA_TEXT).setText(
					String.valueOf(scrTypedoc.getTypePerson()));
			typeDocElement.addElement(XML_CODIGO_TEXT).setText(
					scrTypedoc.getCode());
		}

		return document.asXML();
	}

	public static String createXMLTypeAddresses(List typeAddressesData) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_TYPEADDRESSES_TEXT);

		Element typeDocElement = null;
		for (Iterator iterator = typeAddressesData.iterator(); iterator
				.hasNext();) {
			ScrTypeaddress scrTypeaddress = (ScrTypeaddress) iterator.next();
			typeDocElement = root.addElement(XML_TYPEADDRESS_TEXT);

			typeDocElement.addElement(XML_IDTEL_TEXT).setText(
					scrTypeaddress.getId().toString());
			typeDocElement.addElement(XML_DESCRIPCION_TEXT)
					.add(DocumentHelper.createCDATA(scrTypeaddress
							.getDescription()));
			typeDocElement.addElement(XML_CODIGO_TEXT).setText(
					scrTypeaddress.getCode());
		}

		return document.asXML();
	}

	public static String createXMLSaveOrModifPersonInfo(PersonInfo perInfo,
			String entidad) throws Exception {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement(XML_PERSONA_TEXT);
		root.addAttribute(XML_SESIONID_TEXT, perInfo.getSessionId());
		if (entidad != null) {
			root.addAttribute(XML_ENTIDADID_TEXT, entidad);
		} else {
			root.addAttribute(XML_ENTIDADID_TEXT, "");
		}
		addPersonData(perInfo, root);
		addDoms(perInfo.getAddresses(), root);
		addDirTels(perInfo.getAddressesTel(), root);
		return document.asXML();
	}

	public static String createXMLRecoverPersonInfo(PersonInfo perInfo)
			throws Exception {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement(XML_PERSONA_TEXT);

		if (perInfo != null) {
			addPersonData(perInfo, root);
			Element address = root.addElement(XML_DOMICILIOS_TEXT);
			Element addressTel = root.addElement(XML_TELEMATICAS_TEXT);
			if (!perInfo.getAddresses().isEmpty()) {
				List aux = null;
				PersonAddress dom = null;
				aux = perInfo.getAddresses();
				if (!aux.isEmpty()) {
					for (Iterator it2 = aux.iterator(); it2.hasNext();) {
						dom = (PersonAddress) it2.next();
						addDom(dom, address);
					}
				}
			}
			if (!perInfo.getAddressesTel().isEmpty()) {
				List aux = null;
				PersonAddressTel dirtel = null;
				aux = perInfo.getAddressesTel();
				if (!aux.isEmpty()) {
					for (Iterator it2 = aux.iterator(); it2.hasNext();) {
						dirtel = (PersonAddressTel) it2.next();
						addDirtel(dirtel, addressTel);
					}
				}
			}

		}
		return document.asXML();

	}

	public static String createXMLParamIdInfo(String id, String sessionId,
			String entidad) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_PARAMID_TEXT);
		root.addAttribute(XML_SESIONID_TEXT, sessionId);
		if (entidad != null) {
			root.addAttribute(XML_ENTIDADID_TEXT, entidad);
		} else {
			root.addAttribute(XML_ENTIDADID_TEXT, "");
		}
		if (id != null) {
			root.addElement(XML_ID_TEXT).addText(id);
		} else {
			root.addElement(XML_ID_TEXT).addText("");
		}
		return document.asXML();
	}

	public static PersonInfo getPersonInfoFromXMLText(String xmlText)
			throws Exception {
		Document document = createFromStringText(xmlText);
		PersonInfo pInfo = null;
		PersonAddress pAddress = null;
		PersonAddressTel pAddressTel = null;
		List nodeList = null;
		List telemList = null;

		Node node = null;
		if (document != null) {
			pInfo = new PersonInfo();
			node = document.selectSingleNode(XPATH_PERSONA_ID);
			pInfo.setId(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_TIPO);
			pInfo.setType(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_NOMBRE);
			pInfo.setName(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_APELLIDO1);
			pInfo.setFirstName(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_APELLIDO2);
			pInfo.setSecondName(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_TIPODOC);
			pInfo.setTypeDoc(node.getText());
			node = document.selectSingleNode(XPATH_PERSONA_NIF);
			pInfo.setNif(node.getText());

			nodeList = document.selectNodes(XPATH_PERSONA_DOMICILIO);
			Element address = null;
			for (int i = 0; i < nodeList.size(); i++) {
				pAddress = new PersonAddress();
				address = (Element) nodeList.get(i);
				pAddress.setToDelete(address.attributeValue(XML_ELIMINAR_TEXT));
				pAddress.setId(((Element) address.selectObject(XML_ID_TEXT))
						.getText());
				pAddress.setDom(((Element) address
						.selectObject(XML_DIRECCION_TEXT)).getText());
				pAddress.setCity(((Element) address
						.selectObject(XML_POBLACION_TEXT)).getText());
				pAddress.setZip(((Element) address
						.selectObject(XML_CODPOSTAL_TEXT)).getText());
				pAddress.setProvince(((Element) address
						.selectObject(XML_PROVINCIA_TEXT)).getText());
				pAddress.setPreference(((Element) address
						.selectObject(XML_PREFERENCIA_TEXT)).getText());
				pInfo.addAddress(pAddress);

			}
			telemList = document.selectNodes(XPATH_PERSONA_TELEMATICA);
			Element addressTel = null;
			for (int i = 0; i < telemList.size(); i++) {
				pAddressTel = new PersonAddressTel();
				addressTel = (Element) telemList.get(i);
				pAddressTel.setToDelete(addressTel
						.attributeValue(XML_ELIMINARTEL_TEXT));
				pAddressTel.setId(((Element) addressTel
						.selectObject(XML_IDTEL_TEXT)).getText());
				pAddressTel.setDirTel(((Element) addressTel
						.selectObject(XML_DIRECCIONTEL_TEXT)).getText());
				pAddressTel.setType(((Element) addressTel
						.selectObject(XML_TIPOTEL_TEXT)).getText());
				pAddressTel.setPreference(((Element) addressTel
						.selectObject(XML_PREFERENCIATEL_TEXT)).getText());
				pInfo.addAddressTel(pAddressTel);

			}
		}
		return pInfo;
	}

	public static List getCriteriaFromXMLText(String xmlText) throws Exception {
		Document document = createFromStringText(xmlText);
		PersonCriteria personCriteria = null;
		;
		List nodeList = null;

		List personCriteriaList = new ArrayList();
		if (document != null) {
			nodeList = document.selectNodes(XPATH_CRITERIOS_CRITERIO);
			Element criteria = null;
			for (int i = 0; i < nodeList.size(); i++) {
				criteria = (Element) nodeList.get(i);
				personCriteria = new PersonCriteria();
				personCriteria.setField(((Element) criteria
						.selectObject(XML_CAMPO_TEXT)).getText());
				personCriteria.setOperator(((Element) criteria
						.selectObject(XML_OPERADOR_TEXT)).getText());
				personCriteria.setValue(((Element) criteria
						.selectObject(XML_VALOR_TEXT)).getText());
				personCriteriaList.add(personCriteria);

			}
		}
		return personCriteriaList;
	}

	public static Document createFromStringText(String text) throws Exception {
		Document document = null;
		if (text != null) {
			document = DocumentHelper.parseText(text);
		}
		return document;

	}

	public static CriteriaAttributes getAttributesCriteria(String xmlText)
			throws Exception {
		CriteriaAttributes result = new CriteriaAttributes();

		Document document = createFromStringText(xmlText);
		Node node = document.selectSingleNode(XPATH_CRITERIOS);
		result.setSessionId(node.valueOf(XPATH_CRITERIOS_ATTRIBUTE_SESIONID));
		result.setEntidadId(node.valueOf(XPATH_CRITERIOS_ATTRIBUTE_ENTIDADID));
		result.setPersonType(node.valueOf(XPATH_CRITERIOS_ATTRIBUTE_PERSONTYPE));
		result.setInicio(node.valueOf(XPATH_CRITERIOS_ATTRIBUTE_INICIO));
		result.setRango(node.valueOf(XPATH_CRITERIOS_ATTRIBUTE_RANGO));

		return result;
	}

	public static String getSessionId(String xmlText) throws Exception {
		String result = null;
		List list = null;

		Document document = createFromStringText(xmlText);
		list = document.selectNodes(XPATH_PERSONA_ATTRIBUTE_SESIONID);
		if (list != null && !list.isEmpty()) {
			Attribute attribute = (Attribute) list.get(0);
			result = attribute.getValue();
		} else {
			list = document.selectNodes(XPATH_CRITERIOS_SESIONID);
			if (list != null && !list.isEmpty()) {
				Attribute attribute = (Attribute) list.get(0);
				result = attribute.getValue();
			}
		}
		return result;
	}

	public static String getEntidadId(String xmlText) throws Exception {
		String result = null;
		List list = null;

		Document document = createFromStringText(xmlText);
		list = document.selectNodes(XPATH_PERSONA_ATTRIBUTE_ENTIDADID);
		if (list != null && !list.isEmpty()) {
			Attribute attribute = (Attribute) list.get(0);
			result = attribute.getValue();
		} else {
			list = document.selectNodes(XPATH_CRITERIOS_ENTIDADID);
			if (list != null && !list.isEmpty()) {
				Attribute attribute = (Attribute) list.get(0);
				result = attribute.getValue();
			}
		}
		return result;
	}

	public static List getParamId(String xmlParamId) throws Exception {
		List list = new ArrayList();
		List attributeList = null;

		Document document = createFromStringText(xmlParamId);
		Node node = document.selectSingleNode(XPATH_PARAMID_ID);
		list.add(node.getText());
		attributeList = document.selectNodes(XPATH_PARAMID_ATTRIBUTE_SESIONID);
		if (attributeList != null && !attributeList.isEmpty()) {
			list.add(((Attribute) attributeList.get(0)).getValue());
		}
		attributeList = document.selectNodes(XPATH_PARAMID_ATTRIBUTE_ENTIDADID);
		if (attributeList != null && !attributeList.isEmpty()) {
			list.add(((Attribute) attributeList.get(0)).getValue());
		}
		return list;
	}

	private static void addCriteria(Element parent, List criterios) {
		Element criteria = null;
		PersonCriteria pCriteria = null;
		for (Iterator it = criterios.iterator(); it.hasNext();) {
			pCriteria = (PersonCriteria) it.next();
			criteria = parent.addElement(XML_CRITERIO_TEXT);
			criteria.addElement(XML_CAMPO_TEXT).addText(pCriteria.getField());
			criteria.addElement(XML_OPERADOR_TEXT).addText(
					pCriteria.getOperator());
			criteria.addElement(XML_VALOR_TEXT).addText(pCriteria.getValue());
		}
	}

	private static void addDom(PersonAddress dom, Element parent) {

		Element address = parent.addElement(XML_DOMICILIO_TEXT);

		if (dom.getDom() != null) {
			if (dom.getDom().equals(VACIO))
				address.addElement(XML_DIRECCION_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				address.addElement(XML_DIRECCION_TEXT).add(
						DocumentHelper.createCDATA(dom.getDom()));
		} else {
			address.addElement(XML_DIRECCION_TEXT).add(
					DocumentHelper.createCDATA(BLANCO));
		}

		if (dom.getZip() != null) {
			if (dom.getZip().equals(VACIO))
				address.addElement(XML_CODPOSTAL_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				address.addElement(XML_CODPOSTAL_TEXT).add(
						DocumentHelper.createCDATA(dom.getZip()));
		} else {
			address.addElement(XML_CODPOSTAL_TEXT).add(
					DocumentHelper.createCDATA(BLANCO));
		}

		if (dom.getCity() != null) {
			if (dom.getCity().equals(VACIO))
				address.addElement(XML_POBLACION_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				address.addElement(XML_POBLACION_TEXT).add(
						DocumentHelper.createCDATA(dom.getCity()));
		} else {
			address.addElement(XML_POBLACION_TEXT).add(
					DocumentHelper.createCDATA(BLANCO));
		}

		if (dom.getProvince() != null) {
			if (dom.getProvince().equals(VACIO))
				address.addElement(XML_PROVINCIA_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				address.addElement(XML_PROVINCIA_TEXT).add(
						DocumentHelper.createCDATA(dom.getProvince()));
		} else {
			address.addElement(XML_PROVINCIA_TEXT).add(
					DocumentHelper.createCDATA(BLANCO));
		}

		address.addElement(XML_ID_TEXT).addText(dom.getId().toString());
		address.addElement(XML_PREFERENCIA_TEXT).addText(dom.getPreference());
	}

	private static void addDirtel(PersonAddressTel dirtel, Element parent) {

		Element address = parent.addElement(XML_TELEMATICA_TEXT);

		if (dirtel.getDirTel() != null) {
			if (dirtel.getDirTel().equals(VACIO))
				address.addElement(XML_DIRECCIONTEL_TEXT).add(
						DocumentHelper.createCDATA(BLANCO));
			else
				address.addElement(XML_DIRECCIONTEL_TEXT).add(
						DocumentHelper.createCDATA(dirtel.getDirTel()));
		} else {
			address.addElement(XML_DIRECCIONTEL_TEXT).add(
					DocumentHelper.createCDATA(BLANCO));
		}

		address.addElement(XML_TIPOTEL_TEXT).addText(dirtel.getType());
		address.addElement(XML_IDTEL_TEXT).addText(dirtel.getId().toString());
		address.addElement(XML_PREFERENCIATEL_TEXT).addText(
				dirtel.getPreference());
	}

	private static void addPersonData(PersonInfo perInfo, Element parent) {

		parent.addElement(XML_ID_TEXT).addText(perInfo.getId());
		parent.addElement(XML_TIPO_TEXT).addText(perInfo.getType());
		parent.addElement(XML_NOMBRE_TEXT).add(
				DocumentHelper.createCDATA(perInfo.getName()));
		parent.addElement(XML_APELLIDO1_TEXT).add(
				DocumentHelper.createCDATA(perInfo.getFirstName()));
		parent.addElement(XML_APELLIDO2_TEXT).add(
				DocumentHelper.createCDATA(perInfo.getSecondName()));
		if (null == perInfo.getTypeDoc()) {
			parent.addElement(XML_TIPODOC_TEXT).addText(VACIO);
		} else {
			parent.addElement(XML_TIPODOC_TEXT).addText(perInfo.getTypeDoc());
		}
		if (perInfo.getNif() != null) {
			parent.addElement(XML_NIF_TEXT).add(
					DocumentHelper.createCDATA(perInfo.getNif()));
		} else {
			parent.addElement(XML_NIF_TEXT).add(DocumentHelper.createCDATA(""));
		}
	}

	private static int getFin(int total, int inicio, int rango) {
		int result = 0;
		if (inicio == 0) {
			if (total >= rango) {
				result = rango - 1;
			} else {
				result = total - 1;
			}
		} else {
			if (total >= inicio + rango) {
				result = inicio + rango - 1;
			} else {
				result = total - 1;
			}
		}
		return result;
	}

	private static void addDoms(List addrss, Element parent) {
		Element address = null;

		Element addresses = parent.addElement(XML_DOMICILIOS_TEXT);
		for (Iterator it = addrss.iterator(); it.hasNext();) {
			PersonAddress pAddress = (PersonAddress) it.next();
			address = addresses.addElement(XML_DOMICILIO_TEXT);
			address.addAttribute(XML_ELIMINAR_TEXT, pAddress.getToDelete());
			address.addElement(XML_DIRECCION_TEXT).add(
					DocumentHelper.createCDATA(pAddress.getDom()));
			address.addElement(XML_CODPOSTAL_TEXT).add(
					DocumentHelper.createCDATA(pAddress.getZip()));
			address.addElement(XML_POBLACION_TEXT).add(
					DocumentHelper.createCDATA(pAddress.getCity()));
			address.addElement(XML_PROVINCIA_TEXT).add(
					DocumentHelper.createCDATA(pAddress.getProvince()));
			address.addElement(XML_ID_TEXT).addText(pAddress.getId());
			address.addElement(XML_PREFERENCIA_TEXT).addText(
					pAddress.getPreference());
		}
	}

	private static void addDirTels(List addrss, Element parent) {
		Element addressTel = null;

		Element addresses = parent.addElement(XML_TELEMATICAS_TEXT);
		for (Iterator it = addrss.iterator(); it.hasNext();) {
			PersonAddressTel pAddressTel = (PersonAddressTel) it.next();
			addressTel = addresses.addElement(XML_TELEMATICA_TEXT);
			addressTel.addAttribute(XML_ELIMINARTEL_TEXT,
					pAddressTel.getToDelete());
			addressTel.addElement(XML_DIRECCIONTEL_TEXT).add(
					DocumentHelper.createCDATA(pAddressTel.getDirTel()));
			addressTel.addElement(XML_TIPOTEL_TEXT).add(
					DocumentHelper.createCDATA(pAddressTel.getType()));
			addressTel.addElement(XML_IDTEL_TEXT).addText(pAddressTel.getId());
			addressTel.addElement(XML_PREFERENCIATEL_TEXT).addText(
					pAddressTel.getPreference());
		}
	}
}
