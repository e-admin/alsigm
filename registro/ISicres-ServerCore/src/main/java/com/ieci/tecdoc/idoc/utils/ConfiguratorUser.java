package com.ieci.tecdoc.idoc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.invesicres.ScrUserconfig;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.utils.HibernateUtil;

/**
 * @author 79426599
 *
 */
public class ConfiguratorUser {

	private static final String FLD = "fld";

	private static final Logger log = Logger.getLogger(ConfiguratorUser.class);

	public ConfiguratorUser() {

	}

	public UserConf getUsrConf(AuthenticationUser user, String entidad) {
		UserConf usrConf = new UserConf();

		try {
			Session session = HibernateUtil.currentSession(entidad);

			List userConfigList = ISicresQueries.getUserConfig(session, user.getId());

			if (userConfigList != null && !userConfigList.isEmpty()) {
				ScrUserconfig userConfig = (ScrUserconfig) userConfigList
						.get(0);
				usrConf.setDoc(createFromStringText(userConfig.getData()));
				if (userConfig.getIdoficpref() != null) {
					usrConf.setIdOficPref(userConfig.getIdoficpref().intValue());
				}
			} else {
				usrConf.setDoc(createFromStringText(null));
			}

		} catch (HibernateException e) {
			log.error("Impossible to load values for user configuration.", e);
		} catch (Exception e) {
			log.error("Impossible to load values for user configuration.", e);
		}
		return usrConf;
	}

	public Document createFromStringText(String text) throws Exception {
		Document document = null;

		if (text != null) {
			String xmlText = text.substring(text.indexOf("Configuration") - 1,
					text.length());
			document = DocumentHelper.parseText(xmlText);
		}

		return document;
	}

	public UserConf getUserConfig(UserConf userConf, Integer bookId, AxSf axsf,
			boolean updateOrIntroRegDate, TreeMap fldDefs, boolean isSave,
			boolean query, Locale locale) throws Exception {

		String checkFields = null;
		Document document = null;

		if (userConf != null) {
			document = userConf.getDoc();
		}

		if (document != null) {
			String xpath = ServerKeys.XPATH_CHECK_FIELDS_BOOKID + "'" + bookId
					+ "']";
			List list = document.selectNodes(xpath);
			if (list != null && !list.isEmpty()) {
				Element element = (Element) list.get(0);
				checkFields = element.getText();
			}
			Node node = document
					.selectSingleNode(ServerKeys.XPATH_PERSONVALIDATION);
			if (node != null) {
				userConf.setPersonValidation(new Integer(node.getText())
						.intValue());
			}
			node = document.selectSingleNode(ServerKeys.XPATH_SHOW_SCAN_DIALOG);
			if (node != null) {
				userConf.setShowScanDlg(new Integer(node.getText()).intValue());
			}
			node = document
					.selectSingleNode(ServerKeys.XPATH_REMEMBER_LAST_SELECTED_UNIT);
			if (node != null) {
				userConf
						.setRememberLastSelectedUnit(new Integer(node.getText())
								.intValue());
			}

			Map selectUnits = getSelectUnitMap(document, bookId);
			userConf.setSelectUnitInBook(selectUnits);
		} else {
			userConf.setPersonValidation(new Integer(0).intValue());
			userConf.setShowScanDlg(new Integer(0).intValue());
			userConf.setRememberLastSelectedUnit(new Integer(0).intValue());

		}

		List fieldsConf = parseFields(checkFields, axsf, updateOrIntroRegDate,
				fldDefs, isSave, query, locale);
		userConf.setFieldConf(fieldsConf);

		return userConf;
	}

	public Document createOrUpdateXMLDocument(UserConf userConf,
			Integer bookId, boolean saveRememberSelectedUnit) throws Exception {
		Document document = null;
		Document doc = userConf.getDoc();

		List checkFields = userConf.getFieldConf();
		StringBuffer buffer = new StringBuffer();
		if (!saveRememberSelectedUnit || doc == null) {
			for (int i = 0; i < checkFields.size(); i++) {
				FieldConf fieldConf = (FieldConf) checkFields.get(i);
				buffer.append(fieldConf.getFieldCheck());
				buffer.append(";");
			}
		}
		if (doc == null) {
			document = DocumentHelper.createDocument();
			Element root = document.addElement("Configuration");

			root.addElement("Fields").addAttribute("bookid", bookId.toString())
					.setText(buffer.toString());
			root.addElement("PersonValidation").setText(
					Integer.toString(userConf.getPersonValidation()));
			root.addElement("ShowScanDlg").setText(
					Integer.toString(userConf.getShowScanDlg()));
			root.addElement("RememberLastSelectedUnit").setText(
					Integer.toString(userConf.getRememberLastSelectedUnit()));

			if (saveRememberSelectedUnit) {
				getNewNodeRememberSelectedUnits(root, userConf, bookId);
			}
		} else {
			if (saveRememberSelectedUnit) {
				getUpdateNodeRememberSelectedUnits(doc, userConf, bookId);
			} else {
				getConfigurationUserElements(doc, userConf, bookId, buffer
						.toString());
			}

			document = doc;

		}

		return document;
	}

	public void getUsrConfFieldsChanged(UserConf usrConf, String fields)
			throws Exception {

		List fieldsToChange = new ArrayList();
		StringTokenizer tokens = new StringTokenizer(fields, ";");
		while (tokens.hasMoreTokens()) {
			String fielsToSave = tokens.nextToken();
			fieldsToChange.add(new Integer(fielsToSave));
		}

		List fieldCf = usrConf.getFieldConf();
		for (int i = 0; i < fieldCf.size(); i++) {
			FieldConf fieldConf = (FieldConf) fieldCf.get(i);
			int checkField = 0;
			for (Iterator it1 = fieldsToChange.iterator(); it1.hasNext();) {
				Integer fieldInt = (Integer) it1.next();
				if (fieldConf.getFieldId() == fieldInt.intValue()) {
					checkField = 1;
				}
			}
			fieldConf.setFieldCheck(checkField);
			fieldCf.set(i, fieldConf);
		}
		usrConf.setFieldConf(fieldCf);
	}

	public void getUsrConfParamsChanged(UserConf usrConf, String params)
			throws Exception {
		StringTokenizer tokens1 = new StringTokenizer(params, ";");

		usrConf.setPersonValidation(0);
		usrConf.setShowScanDlg(0);
		usrConf.setRememberLastSelectedUnit(0);

		while (tokens1.hasMoreTokens()) {
			String paramsToSave = tokens1.nextToken();
			if (paramsToSave.equals("1")) {
				usrConf.setPersonValidation(1);
			}
			if (paramsToSave.equals("2")) {
				usrConf.setShowScanDlg(1);
			}
			if (paramsToSave.equals("3")) {
				usrConf.setRememberLastSelectedUnit(1);
			}
		}
	}

	public void getUsrConfselectUnitInBookChanged(UserConf usrConf,
			String unitCode, Integer unitType) throws Exception {
		Map selectUnitInBook = usrConf.getSelectUnitInBook();

		if (selectUnitInBook != null && !selectUnitInBook.isEmpty()) {
			selectUnitInBook.remove(unitType);
		} else {
			selectUnitInBook = new HashMap();
		}

		selectUnitInBook.put(unitType, unitCode);
		usrConf.setSelectUnitInBook(selectUnitInBook);
	}

	private FieldConf getFieldConfToSave(String field, String checkField,
			TreeMap fldDefs, AxSf axsf, Locale locale) throws Exception {
		FieldConf fieldConf = null;

		field = field.replaceAll(FLD, "");
		Integer fldId = new Integer(field);
		if (fldDefs.containsKey(fldId)) {
			FFldDef fldDfs = (FFldDef) fldDefs.get(fldId);
			fieldConf = new FieldConf();
			fieldConf.setFieldId(fldId.intValue());
			fieldConf.setFieldCheck(new Integer(checkField).intValue());
			fieldConf.setFieldLabel(axsf.getLocaleAttributeNameForm(locale,
					fldDfs.getName()));
		}

		return fieldConf;
	}

	private List parseFields(String fields, AxSf axsf,
			boolean updateOrIntroRegDate, TreeMap fldDefs, boolean isSave,
			boolean query, Locale locale) throws Exception {
		FieldConf fieldConf = null;
		List fieldCf = new ArrayList();
		int i = 0;
		List attributeNames = axsf.getAttributesNames();
		int sizeAttributesNames = attributeNames.size();

		if (fields != null) {
			StringTokenizer tokens = new StringTokenizer(fields, ";");
			while (tokens.hasMoreTokens()) {
				String checkField = tokens.nextToken();
				String field = (String) attributeNames.get(i + 2);
				if (isSave) {
					fieldConf = getFieldConfToSave(field, checkField, fldDefs,
							axsf, locale);
				} else {
					if (query) {
						fieldConf = getFieldConf(field, fldDefs, axsf, locale);
					} else {
						fieldConf = getFieldConf(field, checkField,
								updateOrIntroRegDate, fldDefs, axsf, locale);
					}
				}
				if (fieldConf != null) {
					fieldCf.add(fieldConf);
				}
				i++;
			}
		} else {
			for (int j = 2; j < sizeAttributesNames; j++) {
				String field = (String) attributeNames.get(j);
				if (isSave) {
					fieldConf = getFieldConfToSave(field, "0", fldDefs, axsf,
							locale);
				} else {
					if (query) {
						fieldConf = getFieldConf(field, fldDefs, axsf, locale);
					} else {
						fieldConf = getFieldConf(field, "0",
								updateOrIntroRegDate, fldDefs, axsf, locale);
					}
				}
				if (fieldConf != null) {
					fieldCf.add(fieldConf);
				}
			}
		}
		return fieldCf;
	}

	private FieldConf getFieldConf(String field, String checkField,
			boolean updateOrIntroRegDate, TreeMap fldDefs, AxSf axsf,
			Locale locale) throws Exception {
		FieldConf fieldConf = null;

		field = field.replaceAll(FLD, "");
		Integer fldId = new Integer(field);

		if (fldId.intValue() != 1 && fldId.intValue() != 3
				&& fldId.intValue() != 4 && fldId.intValue() != 5
				&& fldId.intValue() != 6 && fldId.intValue() != 2) {

			if (fldDefs.containsKey(fldId)) {
				FFldDef fldDfs = (FFldDef) fldDefs.get(fldId);
				fieldConf = new FieldConf();
				fieldConf.setFieldId(fldId.intValue());
				fieldConf.setFieldCheck(new Integer(checkField).intValue());
				fieldConf.setFieldLabel(axsf.getLocaleAttributeNameForm(locale,
						fldDfs.getName()));
			}
		}

		if (new Integer(field).intValue() == 2 && updateOrIntroRegDate) {
			if (fldDefs.containsKey(fldId)) {
				FFldDef fldDfs = (FFldDef) fldDefs.get(fldId);
				fieldConf = new FieldConf();
				fieldConf.setFieldId(fldId.intValue());
				fieldConf.setFieldCheck(new Integer(checkField).intValue());
				fieldConf.setFieldLabel(axsf.getLocaleAttributeNameForm(locale,
						fldDfs.getName()));
			}
		}

		return fieldConf;
	}

	private FieldConf getFieldConf(String field, TreeMap fldDefs, AxSf axsf,
			Locale locale) throws Exception {
		FieldConf fieldConf = null;

		field = field.replaceAll(FLD, "");
		Integer fldId = new Integer(field);
		if (fldDefs.containsKey(fldId)) {
			FFldDef fldDfs = (FFldDef) fldDefs.get(fldId);
			fieldConf = new FieldConf();
			fieldConf.setFieldId(fldId.intValue());
			fieldConf.setFieldLabel(axsf.getLocaleAttributeNameForm(locale,
					fldDfs.getName()));
		}

		return fieldConf;
	}

	private Map getSelectUnitMap(Document document, Integer bookId) {

		String xpath = ServerKeys.XPATH_SELECTED_UNIT + "'" + bookId + "']";

		List selectedUnits = document.selectNodes(xpath);

		if (selectedUnits == null || selectedUnits.isEmpty()) {
			return null;
		}

		Node selectedUnit = (Node) selectedUnits.get(0);
		Map mapUnits = new HashMap();

		String xpathOrigen = xpath + ServerKeys.XPATH_SELECTED_UNIT_ORIGEN;
		Node origen = selectedUnit.selectSingleNode(xpathOrigen);
		if (origen != null && StringUtils.isNotBlank(origen.getText())) {
			mapUnits.put(ISicresKeys.REGISTRO_ORIGEN_FLD, origen.getText());
		}

		String xpathDestino = xpath + ServerKeys.XPATH_SELECTED_UNIT_DESTINO;
		Node destino = selectedUnit.selectSingleNode(xpathDestino);
		if (destino != null && StringUtils.isNotBlank(destino.getText())) {
			mapUnits.put(ISicresKeys.REGISTRO_DESTINO_FLD, destino.getText());
		}

		return mapUnits;
	}

	private void getConfigurationUserElements(Document doc, UserConf userConf,
			Integer bookId, String fieldsConf) {
		Node node = null;

		String xpath = ServerKeys.XPATH_CHECK_FIELDS_BOOKID + "'" + bookId
				+ "']";
		List list = doc.selectNodes(xpath);
		if (list != null && !list.isEmpty()) {
			Element element = (Element) list.get(0);
			element.setText(fieldsConf);
		} else {
			Element root = doc.getRootElement();
			root.addElement("Fields").addAttribute("bookid", bookId.toString())
					.setText(fieldsConf);
		}

		node = doc.selectSingleNode(ServerKeys.XPATH_PERSONVALIDATION);
		if (node != null) {
			node.setText(Integer.toString(userConf.getPersonValidation()));
		} else {
			Element root = doc.getRootElement();
			root.addElement("PersonValidation").setText(
					Integer.toString(userConf.getPersonValidation()));
		}

		node = doc.selectSingleNode(ServerKeys.XPATH_SHOW_SCAN_DIALOG);
		if (node != null) {
			node.setText(Integer.toString(userConf.getShowScanDlg()));
		} else {
			Element root = doc.getRootElement();
			root.addElement("ShowScanDlg").setText(
					Integer.toString(userConf.getShowScanDlg()));
		}

		node = doc
				.selectSingleNode(ServerKeys.XPATH_REMEMBER_LAST_SELECTED_UNIT);
		if (node != null) {
			node.setText(Integer.toString(userConf
					.getRememberLastSelectedUnit()));
		} else {
			Element root = doc.getRootElement();
			root.addElement("RememberLastSelectedUnit").setText(
					Integer.toString(userConf.getRememberLastSelectedUnit()));
		}
	}

	private Element getNewNodeRememberSelectedUnits(Element root,
			UserConf userConf, Integer bookId) {
		Element elementUnits = root.addElement("SelectedUnit").addAttribute(
				"bookId", bookId.toString());

		String codigoUnidadOrigen = (String) userConf.getSelectUnitInBook()
				.get(ISicresKeys.REGISTRO_ORIGEN_FLD);
		if (StringUtils.isNotBlank(codigoUnidadOrigen)) {
			elementUnits.addElement("codigoUnidadOrigen").addText(
					codigoUnidadOrigen);
		} else {
			elementUnits.addElement("codigoUnidadOrigen");
		}

		String codigoUnidadDestino = (String) userConf.getSelectUnitInBook()
				.get(ISicresKeys.REGISTRO_DESTINO_FLD);
		if (StringUtils.isNotBlank(codigoUnidadDestino)) {
			elementUnits.addElement("codigoUnidadDestino").addText(
					codigoUnidadDestino);
		} else {
			elementUnits.addElement("codigoUnidadDestino");
		}

		return elementUnits;
	}

	private void getUpdateNodeRememberSelectedUnits(Document doc,
			UserConf userConf, Integer bookId) {
		String xpath = ServerKeys.XPATH_SELECTED_UNIT + "'" + bookId + "']";

		List selectedUnits = doc.selectNodes(xpath);

		if (selectedUnits != null && !selectedUnits.isEmpty()) {
			Node selectedUnit = (Node) selectedUnits.get(0);

			String xpathOrigen = xpath + ServerKeys.XPATH_SELECTED_UNIT_ORIGEN;
			Node nodeOrig = selectedUnit.selectSingleNode(xpathOrigen);
			String codigoUnidadOrigen = (String) userConf.getSelectUnitInBook()
					.get(ISicresKeys.REGISTRO_ORIGEN_FLD);
			if (nodeOrig != null) {
				if (StringUtils.isNotBlank(codigoUnidadOrigen))
					nodeOrig.setText(codigoUnidadOrigen);
			} else {
				selectedUnit.getDocument().addElement("codigoUnidadOrigen")
						.addText(codigoUnidadOrigen);
			}

			String xpathDestino = xpath
					+ ServerKeys.XPATH_SELECTED_UNIT_DESTINO;
			Node nodeDest = selectedUnit.selectSingleNode(xpathDestino);
			String codigoUnidadDestino = (String) userConf
					.getSelectUnitInBook()
					.get(ISicresKeys.REGISTRO_DESTINO_FLD);
			if (nodeDest != null) {
				if (StringUtils.isNotBlank(codigoUnidadDestino))
					nodeDest.setText(codigoUnidadDestino);
			} else {
				selectedUnit.getDocument().addElement("codigoUnidadDestino")
						.addText(codigoUnidadDestino);
			}
		} else {
			Element root = doc.getRootElement();

			getNewNodeRememberSelectedUnits(root, userConf, bookId);
		}
	}
}
