package com.ieci.tecdoc.isicres.usecase.validationlist;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.NodeComparator;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrCity;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrProv;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.isicres.ValidationResultScrOrg;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSessionEx;
import com.ieci.tecdoc.isicres.session.validation.ValidationSession;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionEx;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionInter;
import com.ieci.tecdoc.isicres.session.validation.invesdoctype.ProvTypeInvesdoc;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLFieldsInfo;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLBuscInter;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLDeptsGroupsUsers;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLDirection;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLRegisterBook;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLRegisterType;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeAddress;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeAdm;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeDoc;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeEntReg;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeInvesDoc;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeOffic;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeProc;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeSubject;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLTypeTransport;

/**
 * @author LMVICENTE
 * @creationDate 29-abr-2004 16:25:33
 * @version
 * @since
 */
public class ValidationListUseCase implements Keys {

	/*******************************************************************************************************************
	 * Attributes
	 ******************************************************************************************************************/

	private static Logger _logger = Logger
			.getLogger(ValidationListUseCase.class);
	private static final String AMPERSAN = "#";
	private static final String PAR_IZQ = "(";
	private static final String PAR_DER = ")";
	private static final String GUION = "-";
	private static final String APOS = "&apos;";
	private static final String APOSTROF = "'";

	private TransformerFactory factory = null;

	/*******************************************************************************************************************
	 * Constructors
	 ******************************************************************************************************************/

	public ValidationListUseCase() {
		super();

		if (factory == null) {
			factory = TransformerFactory.newInstance();
		}
	}

	/*******************************************************************************************************************
	 * Public methods
	 ******************************************************************************************************************/

	public String getValidateUnitActionForm(UseCaseConf useCaseConf,
			String action, int fldid, int initValue, String value)
			throws TransformerConfigurationException, TransformerException,
			AttributesException, ValidationException, SessionException,
			FileNotFoundException, UnsupportedEncodingException {
		ValidationResults results = ValidationSession
				.validateAndReturnUA(
						useCaseConf.getSessionID(),
						initValue,
						Configurator.getInstance()
								.getDefaultPageValidationListSize(),
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
						value, useCaseConf.getLocale(), useCaseConf
								.getEntidadId());

		Document xmlDocument = XMLTypeAdm.createXMLValidationListActionForm(
				results, action, initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), value, fldid,
				useCaseConf.getLocale());
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDocument);
		} catch (IOException e) {
			throw new ValidationException(
					ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	public String getOtherOfficesActionForm(UseCaseConf useCaseConf,
			String action, int fldid, int initValue)
			throws TransformerConfigurationException, TransformerException,
			AttributesException, ValidationException, SessionException,
			FileNotFoundException, UnsupportedEncodingException {

		List list = null;
		if (useCaseConf.getUseLdap().booleanValue()) {
			list = ValidationSessionEx.getOtherOfficesLDAP(
					useCaseConf.getSessionID(), useCaseConf.getEntidadId());
		} else {
			list = ValidationSessionEx.getOtherOffices(
					useCaseConf.getSessionID(), useCaseConf.getLocale(),
					useCaseConf.getEntidadId());
		}

		Document xmlDocument = XMLTypeAdm.createXMLOtherOfficesListActionForm(
				list, action, initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), fldid, useCaseConf
						.getLocale());
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDocument);
		} catch (IOException e) {
			throw new ValidationException(
					ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	@Deprecated
	public String getValidateIntActionForm(UseCaseConf useCaseConf,
			String action, int fldid, int initValue, String code)
			throws TransformerConfigurationException, TransformerException,
			AttributesException, ValidationException, SessionException,
			FileNotFoundException, UnsupportedEncodingException {
		Document doc = null;
		String xmlValidateInt = ValidationSessionInter
				.getValidateInt(
						useCaseConf.getSessionID(),
						initValue,
						code,
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE)),
						useCaseConf.getEntidadId());
		if (xmlValidateInt != null && !xmlValidateInt.equals("")) {
			try {
				doc = createFromStringText(xmlValidateInt);
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_INTER);
			}

		}

		Document xmlDocument = XMLTypeAdm.createXMLValidateIntListActionForm(
				doc, action, initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), code, fldid,
				useCaseConf.getLocale());

		/*
		 * 66575267 - Gabriel Saiz
		 */
		xmlDocument = addAddressValidateInt(xmlDocument,
				useCaseConf.getSessionID(), useCaseConf.getEntidadId());

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDocument);
		} catch (IOException e) {
			throw new ValidationException(
					ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	public String getValidateUnit(UseCaseConf useCaseConf, int fldid,
			int initValue, String value, String xslPath)
			throws TransformerConfigurationException, TransformerException,
			AttributesException, ValidationException, SessionException,
			FileNotFoundException, UnsupportedEncodingException, BookException {
		ValidationResults results = ValidationSession
				.validateAndReturnUA(
						useCaseConf.getSessionID(),
						initValue,
						Configurator.getInstance()
								.getDefaultPageValidationListSize(),
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
						value, useCaseConf.getLocale(), useCaseConf
								.getEntidadId());

		StringBuffer buffer = new StringBuffer();
		buffer.append(fldid);
		buffer.append(AMPERSAN);
		if (results.getTotalSize() == 0
				|| results.getTotalSize() > Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES))) {
			buffer.append(0);
			buffer.append(AMPERSAN);
			buffer.append(results.getTotalSize());
			buffer.append(AMPERSAN);
			buffer.append(AMPERSAN);
			buffer.append(AMPERSAN);
		} else if (results.getTotalSize() == 1) {
			ValidationResultScrOrg vr = (ValidationResultScrOrg) results
					.getResults().iterator().next();
			buffer.append(1);
			buffer.append(AMPERSAN);
			buffer.append(1);
			buffer.append(AMPERSAN);
			buffer.append(formatScrOrgDescription(vr));
			buffer.append(AMPERSAN);
			buffer.append(vr.getScrOrgCode());
			buffer.append(AMPERSAN);
		} else {
			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(),
							useCaseConf.getLocale(), useCaseConf.getEntidadId());
			Document xmlDocument = XMLTypeAdm.createXMLValidationList(results,
					initValue, Configurator.getInstance()
							.getDefaultPageValidationListSize(), value,
					sessionInformation.getCaseSensitive());

			StreamSource s = new StreamSource(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(xslPath))));

			Templates cachedXSLT = factory.newTemplates(s);
			Transformer transformer = cachedXSLT.newTransformer();
			DocumentSource source = new DocumentSource(xmlDocument);

			DocumentResult htmlResult = new DocumentResult();
			transformer.transform(source, htmlResult);

			String htmlStringResult = htmlResult.getDocument().asXML();
			htmlStringResult = htmlStringResult.substring(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>".length() + 1,
					htmlStringResult.length());
			htmlStringResult = htmlStringResult.replaceAll(APOS, APOSTROF);

			if (_logger.isDebugEnabled()) {
				_logger.debug(htmlStringResult);
			}

			buffer.append(1);
			buffer.append(AMPERSAN);
			buffer.append(results.getTotalSize());
			buffer.append(AMPERSAN);
			buffer.append(htmlStringResult);
			buffer.append(AMPERSAN);
			buffer.append(AMPERSAN);
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(buffer);
		}

		String result = URLEncoder.encode(buffer.toString(), "UTF-8");
		result = result.replaceAll("\\+", "%20");
		return result;
	}

	public static String formatScrOrgDescription(ValidationResultScrOrg vr) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(vr.getScrOrgName());
		if (vr.getScrOrgFatherName() != null) {
			buffer.append(PAR_IZQ);
			buffer.append(vr.getScrOrgFatherName());
			if (vr.getScrOrgParentAcron() != null) {
				buffer.append(GUION);
				buffer.append(vr.getScrOrgParentAcron());
			}
			buffer.append(PAR_DER);
		}

		return buffer.toString();
	}

	public String getScrOrgName(UseCaseConf useCaseConf, ScrOrg scr,
			String fld78Name) throws AttributesException, ValidationException,
			SessionException {
		if (scr == null) {
			return "";
		}
		String name = fld78Name;
		// Obtener el padre de nivel superior
		ScrOrg scrOrg1 = null;
		ScrOrgeu scrOrgeu1 = null;
		ScrOrggl scrOrggl1 = null;
		ScrOrgct scrOrgct1 = null;
		ScrOrg scrOrg2 = null;
		ScrOrgeu scrOrgeu2 = null;
		ScrOrggl scrOrggl2 = null;
		ScrOrgct scrOrgct2 = null;
		String aux = "";
		if (scr.getIdFather() != null) {
			Object father = ValidationSessionEx.getScrOrg(
					useCaseConf.getSessionID(), scr.getIdFather().intValue(),
					useCaseConf.getLocale().getLanguage(),
					useCaseConf.getEntidadId());

			Object parent = ValidationSessionEx.getTopLevelParentScrOrg(
					useCaseConf.getSessionID(), scr.getIdFather().intValue(),
					useCaseConf.getLocale().getLanguage(),
					useCaseConf.getEntidadId());

			if (father instanceof ScrOrg && parent instanceof ScrOrg) {
				scrOrg1 = (ScrOrg) father;
				scrOrg2 = (ScrOrg) parent;
				if (scrOrg1 != null && scrOrg2 != null) {
					if (scrOrg1.getId().equals(scrOrg2.getId())) {
						aux = PAR_IZQ + scrOrg2.getName() + PAR_DER;
					} else {
						aux = PAR_IZQ + scrOrg1.getName() + GUION
								+ scrOrg2.getAcron() + PAR_DER;
					}
				}
			} else if (father instanceof ScrOrgeu && parent instanceof ScrOrgeu) {
				scrOrgeu1 = (ScrOrgeu) father;
				scrOrgeu2 = (ScrOrgeu) parent;
				if (scrOrgeu1 != null && scrOrgeu2 != null) {
					if (scrOrgeu1.getId().equals(scrOrgeu2.getId())) {
						aux = PAR_IZQ + scrOrgeu2.getName() + PAR_DER;
					} else {
						aux = PAR_IZQ + scrOrgeu1.getName() + GUION
								+ scrOrgeu2.getAcron() + PAR_DER;
					}
				}
			} else if (father instanceof ScrOrggl && parent instanceof ScrOrggl) {
				scrOrggl1 = (ScrOrggl) father;
				scrOrggl2 = (ScrOrggl) parent;
				if (scrOrggl1 != null && scrOrggl2 != null) {
					if (scrOrggl1.getId().equals(scrOrggl2.getId())) {
						aux = PAR_IZQ + scrOrggl2.getName() + PAR_DER;
					} else {
						aux = PAR_IZQ + scrOrggl1.getName() + GUION
								+ scrOrggl2.getAcron() + PAR_DER;
					}
				}
			} else if (father instanceof ScrOrgct && parent instanceof ScrOrgct) {
				scrOrgct1 = (ScrOrgct) father;
				scrOrgct2 = (ScrOrgct) parent;
				if (scrOrgct1 != null && scrOrgct2 != null) {
					if (scrOrgct1.getId().equals(scrOrgct2.getId())) {
						aux = PAR_IZQ + scrOrgct2.getName() + PAR_DER;
					} else {
						aux = PAR_IZQ + scrOrgct1.getName() + GUION
								+ scrOrgct2.getAcron() + PAR_DER;
					}
				}
			}
		}
		return name + aux;
	}

	public Document getValidationListBooks(UseCaseConf useCaseConf,
			int initValue, int typeBook) throws BookException,
			SessionException, ValidationException {

		List bookList;
		// realizamos la busqueda de libros segun el tipo de libro que llegue
		// como parametro
		switch (typeBook) {
		case 1: {
			bookList = BookSession.getInBooksOpen(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			break;
		}
		case 2: {
			bookList = BookSession.getOutBooksOpen(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			break;
		}
		default: {
			bookList = BookSession.getInOutBooksOpen(
					useCaseConf.getSessionID(), useCaseConf.getLocale(),
					useCaseConf.getEntidadId());
			break;
		}
		}

		ValidationResults results = new ValidationResults();
		// obtenemos la lista de elementos a mostrar
		int end = initValue
				+ Configurator.getInstance().getDefaultPageValidationListSize();
		int cont = 0;

		if (!bookList.isEmpty()) {
			for (Iterator iterator = bookList.iterator(); iterator.hasNext();) {
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();
				if ((cont >= initValue) && (cont < end)) {
					results.getResults().add(book);
				}
				cont++;
			}
			results.setTotalSize(bookList.size());
		}

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(2, 1);
		return XMLRegisterBook.createXMLValidationList(results, initValue,
				useCaseConf.getLocale(), fieldsInfo,
				sessionInformation.getCaseSensitive());

	}

	public Document getValidationListRegisterType(UseCaseConf useCaseConf)
			throws BookException, SessionException, ValidationException {

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		return XMLRegisterType.createXMLValidationList(useCaseConf.getLocale(),
				sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeProc(UseCaseConf useCaseConf,
			int initValue, int enabled, String vldQuery, Locale locale)
			throws AttributesException, ValidationException, SessionException,
			BookException {
		ValidationResults results = ValidationSession.getScrTypeproc(
				useCaseConf.getSessionID(), initValue, Configurator
						.getInstance().getDefaultPageValidationListSize(),
				vldQuery, enabled, useCaseConf.getEntidadId());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(2, 2);
		return XMLTypeProc.createXMLValidationList(results, initValue, locale,
				fieldsInfo, sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeOffic(UseCaseConf useCaseConf,
			int initValue, int enabled, String vldQuery, Locale locale)
			throws AttributesException, ValidationException, SessionException,
			BookException {
		ValidationResults results = ValidationSession.getScrOfic(useCaseConf
				.getSessionID(), initValue, Configurator.getInstance()
				.getDefaultPageValidationListSize(), vldQuery, enabled,
				useCaseConf.getLocale(), useCaseConf.getEntidadId());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(3, 0);
		return XMLTypeOffic.createXMLValidationList(results, initValue,
				enabled, locale, fieldsInfo,
				sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeInvesDoc(UseCaseConf useCaseConf,
			int initValue, int typeId, Integer bookID, int fldid, int idCrl,
			int typeBusc, String vldQuery, Locale locale)
			throws AttributesException, BookException, ValidationException,
			SessionException {
		ValidationResults results = null;
		Document resultDoc = null;

		results = AttributesSession.getExtendedValidationFieldValues(
				useCaseConf.getSessionID(), bookID, fldid, initValue,
				Configurator.getInstance().getDefaultPageValidationListSize(),
				vldQuery, useCaseConf.getLocale(), useCaseConf.getEntidadId(),
				true);
		if (results.getAdditionalFieldName().equals("POBLACION")) {
			resultDoc = getValidationListTypeProv(useCaseConf, bookID,
					initValue, typeId, typeBusc, fldid, idCrl, vldQuery,
					results.getAdditionalFieldName(), results.getDocColName());
		} else {
			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(),
							useCaseConf.getLocale(), useCaseConf.getEntidadId());

			resultDoc = XMLTypeInvesDoc.createXMLValidationList(results,
					initValue, locale, sessionInformation.getCaseSensitive());
		}
		return resultDoc;
	}

	public Document getValidationListTypeTransport(UseCaseConf useCaseConf,
			int initValue, String vldQuery, Locale locale)
			throws AttributesException, ValidationException, SessionException,
			BookException {
		ValidationResults results = ValidationSession.getScrtt(useCaseConf
				.getSessionID(), initValue, Configurator.getInstance()
				.getDefaultPageValidationListSize(), vldQuery, useCaseConf
				.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(1, 0);

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		return XMLTypeTransport.createXMLValidationList(results, initValue,
				locale, fieldsInfo, sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeEntReg(UseCaseConf useCaseConf,
			int initValue, int enabled, String vldQuery, Locale locale)
			throws AttributesException, ValidationException, SessionException,
			BookException {
		ValidationResults results = ValidationSession.getScrOrgs(useCaseConf
				.getSessionID(), initValue, Configurator.getInstance()
				.getDefaultPageValidationListSize(), vldQuery, enabled,
				useCaseConf.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(3, 0);

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		return XMLTypeEntReg.createXMLValidationList(results, initValue,
				enabled, locale, Keys.I18N_VALIDATIONUSECASE_TYPEENTREG,
				fieldsInfo, sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeDistribution(UseCaseConf useCaseConf,
			Integer bookID, int initValue, int typeId, int typeBusc, int fldId,
			int idCrl, int enabled, String vldQuery)
			throws AttributesException, ValidationException, SessionException,
			BookException {
		ValidationResults results = null;
		String name = "";
		String ref = "";
		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}
		switch (typeBusc) {
		case 0: {
			results = ValidationSession.getScrOrgsForDistritution(useCaseConf
					.getSessionID(), initValue, Configurator.getInstance()
					.getDefaultPageValidationListSize(), vldQuery, enabled,
					locale, useCaseConf.getEntidadId());

			Object srcOrgAux = results.getResults().iterator().next();
			if (!results.getResults().isEmpty()
					&& (srcOrgAux instanceof ScrOrg
							|| srcOrgAux instanceof ScrOrgeu
							|| srcOrgAux instanceof ScrOrggl || srcOrgAux instanceof ScrOrgct)
					&& (vldQuery == null || vldQuery.equals(""))) {
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), 1, locale.getLanguage(),
						useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			}
			break;
		}
		case 1: {
			if (typeId == 0) {
				results = ValidationSession.getScrOrgsForAdminUnitsWithType(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			} else {
				results = ValidationSession.getScrOrgsForAdminUnitsWithFather(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getOrgNames(scrOrg);
				// Obtener el padre de nivel superior
				String aux = "";
				if (EntityByLanguage.getParentIds(scrOrg) != null) {
					scrOrg = ValidationSessionEx.getTopLevelParentScrOrg(
							useCaseConf.getSessionID(), EntityByLanguage
									.getParentIds(scrOrg).intValue(), locale
									.getLanguage(), useCaseConf.getEntidadId());
					if (scrOrg != null) {
						aux = EntityByLanguage.getOrgAcron(scrOrg);
					}
				}
				if (aux.length() > 0) {
					ref = name + GUION + aux;
				} else {
					ref = name;
				}
			}
			break;
		}
		case 2: {
			if (typeId == 0) {
				results = ValidationSession.getScrTypeAdm(useCaseConf
						.getSessionID(), initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), vldQuery, locale,
						useCaseConf.getEntidadId());
			} else {
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				if (EntityByLanguage.getParentIds(scrOrg) == null) {
					results = ValidationSession.getScrTypeAdm(useCaseConf
							.getSessionID(), initValue, Configurator
							.getInstance().getDefaultPageValidationListSize(),
							vldQuery, locale, useCaseConf.getEntidadId());
				} else {
					Object scrOrgVal = ValidationSessionEx.getScrOrg(
							useCaseConf.getSessionID(), EntityByLanguage
									.getParentIds(scrOrg).intValue(), locale
									.getLanguage(), useCaseConf.getEntidadId());
					if (EntityByLanguage.getParentIds(scrOrgVal) == null) {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithType(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery,
										enabled,
											EntityByLanguage
												.getScrTypeAdmIdFromOrg(
														scrOrgVal).intValue(),
										locale, useCaseConf.getEntidadId());
						Object scrAdm = ValidationSessionEx.getScrTypeAdm(

								useCaseConf.getSessionID(), EntityByLanguage
										.getScrTypeAdmIdFromOrg(scrOrg)
										.intValue(), locale.getLanguage(),
								useCaseConf.getEntidadId());
						name = EntityByLanguage.getScrTypeAdmName(scrAdm);
					} else {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithFather(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery, enabled, EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale,
										useCaseConf.getEntidadId());
						Object scrOrgFromFather = ValidationSessionEx
								.getScrOrg(useCaseConf.getSessionID(),
										EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale
												.getLanguage(), useCaseConf
												.getEntidadId());
						name = EntityByLanguage.getOrgNames(scrOrgFromFather);
						// Obtener el padre de nivel superior
						String aux = "";
						if (EntityByLanguage.getParentIds(scrOrgFromFather) != null) {
							Object scrOrgTopLevel = ValidationSessionEx
									.getTopLevelParentScrOrg(useCaseConf
											.getSessionID(), EntityByLanguage
											.getParentIds(scrOrgFromFather)
											.intValue(), locale.getLanguage(),
											useCaseConf.getEntidadId());
							if (EntityByLanguage.getOrgAcron(scrOrgTopLevel) != null) {
								aux = EntityByLanguage
										.getOrgAcron(scrOrgTopLevel);
							}
						}
						if (aux.length() > 0) {
							ref = name + GUION + aux;
						} else {
							ref = name;
						}
					}
				}
			}
			break;
		}
		default: {
			break;
		}
		}

		if (results != null) {
			Map parentNames = null;
			if (vldQuery != null && !vldQuery.equals("")) {
				parentNames = getParentNames(useCaseConf, results);
			}
			List fieldsInfo = createFieldsInfo(3, 0);

			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(), locale,
							useCaseConf.getEntidadId());

			return XMLTypeEntReg.createXMLDtrValidationList(results, initValue,
					enabled, typeBusc, locale, name, ref,
					Keys.I18N_VALIDATIONUSECASE_TYPEDISTRIBUTION, fieldsInfo,
					sessionInformation.getCaseSensitive());

		} else {
			return null;
		}
	}


	public Document getValidationListTypeSubject(UseCaseConf useCaseConf,
			Integer bookID, int initValue, int enabled, String vldQuery)
			throws AttributesException, BookException, ValidationException,
			SessionException {
		ValidationResults results = ValidationSession.getScrCa(useCaseConf
				.getSessionID(), bookID, initValue, Configurator.getInstance()
				.getDefaultPageValidationListSize(), vldQuery, enabled,
				useCaseConf.getLocale(), useCaseConf.getEntidadId());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(),
						useCaseConf.getLocale(), useCaseConf.getEntidadId());

		List fieldsInfo = createFieldsInfo(2, 1);
		return XMLTypeSubject.createXMLValidationList(results, initValue,
				enabled, useCaseConf.getLocale(), fieldsInfo,
				sessionInformation.getCaseSensitive());
	}

	public Document getValidationListTypeUA(UseCaseConf useCaseConf,
			Integer bookID, int initValue, int typeId, int typeBusc, int fldId,
			int idCrl, int enabled, int frmData, int frmDataBusc,
			String vldQuery) throws AttributesException, ValidationException,
			SessionException, BookException {

		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		if (frmData == 1) {
			ScrOrg selectedUnit = getLastSelectedUnit(useCaseConf, bookID,
					new Integer(fldId), locale);

			if (selectedUnit != null) {
				if (selectedUnit.getIdFather() != null
						&& selectedUnit.getIdFather().intValue() != 0) {
					idCrl = selectedUnit.getIdFather().intValue();
					typeId = selectedUnit.getScrTypeadm().getId().intValue();
					typeBusc = 1;
				} else {
					idCrl = selectedUnit.getScrTypeadm().getId().intValue();
					typeId = 0;
					typeBusc = 1;
				}
			}
		}

		ValidationResults results = null;
		String name = "";
		String ref = "";
		switch (typeBusc) {
		case 0: {
			results = ValidationSession.getInitialValidationTreeResults(
					useCaseConf.getSessionID(), bookID, initValue, Configurator
							.getInstance().getDefaultPageValidationListSize(),
					vldQuery, enabled, fldId, locale, useCaseConf
							.getEntidadId());
			Object srcOrgAux = null;
			if (results.getResults() != null
					&& results.getResults().iterator().hasNext()) {
				srcOrgAux = results.getResults().iterator().next();
			}
			if (!results.getResults().isEmpty()
					&& (srcOrgAux instanceof ScrOrg
							|| srcOrgAux instanceof ScrOrgeu
							|| srcOrgAux instanceof ScrOrggl || srcOrgAux instanceof ScrOrgct)
					&& (vldQuery == null || vldQuery.equals(""))) {
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), 1, locale.getLanguage(),
						useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			}
			break;
		}
		case 1: {
			if (typeId == 0) {
				results = ValidationSession.getScrOrgsForAdminUnitsWithType(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			} else {
				results = ValidationSession.getScrOrgsForAdminUnitsWithFather(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getOrgNames(scrOrg);
				// Obtener el padre de nivel superior
				String aux = "";
				if (EntityByLanguage.getParentIds(scrOrg) != null) {
					Object scrOrgTopLevel = ValidationSessionEx
							.getTopLevelParentScrOrg(
									useCaseConf.getSessionID(),
									EntityByLanguage.getParentIds(scrOrg)
											.intValue(), locale.getLanguage(),
									useCaseConf.getEntidadId());
					if (EntityByLanguage.getOrgAcron(scrOrgTopLevel) != null) {
						aux = EntityByLanguage.getOrgAcron(scrOrgTopLevel);
					}
				}
				if (aux.length() > 0) {
					ref = name + GUION + aux;
				} else {
					ref = name;
				}
			}
			break;
		}
		case 2: {
			if (typeId == 0) {
				results = ValidationSession.getScrTypeAdm(useCaseConf
						.getSessionID(), initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), vldQuery, locale,
						useCaseConf.getEntidadId());
			} else {
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				if (EntityByLanguage.getParentIds(scrOrg) == null) {
					results = ValidationSession.getScrTypeAdm(useCaseConf
							.getSessionID(), initValue, Configurator
							.getInstance().getDefaultPageValidationListSize(),
							vldQuery, locale, useCaseConf.getEntidadId());
				} else {
					Object scrOrgVal = ValidationSessionEx.getScrOrg(
							useCaseConf.getSessionID(), EntityByLanguage
									.getParentIds(scrOrg).intValue(), locale
									.getLanguage(), useCaseConf.getEntidadId());
					if (EntityByLanguage.getParentIds(scrOrgVal) == null) {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithType(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery,
										enabled,
										EntityByLanguage
												.getScrTypeAdmIdFromOrg(
														scrOrgVal).intValue(),
										locale, useCaseConf.getEntidadId());
						Object scrAdm = ValidationSessionEx.getScrTypeAdm(
								useCaseConf.getSessionID(), EntityByLanguage
										.getScrTypeAdmIdFromOrg(scrOrg)
										.intValue(), locale.getLanguage(),
								useCaseConf.getEntidadId());
						name = EntityByLanguage.getScrTypeAdmName(scrAdm);
					} else {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithFather(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery, enabled, EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale,
										useCaseConf.getEntidadId());
						Object scrOrgFromFather = ValidationSessionEx
								.getScrOrg(useCaseConf.getSessionID(),
										EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale
												.getLanguage(), useCaseConf
												.getEntidadId());
						name = EntityByLanguage.getOrgNames(scrOrgFromFather);
						// Obtener el padre de nivel superior
						String aux = "";
						if (EntityByLanguage.getParentIds(scrOrgFromFather) != null) {
							Object scrOrgTopLevel = ValidationSessionEx
									.getTopLevelParentScrOrg(useCaseConf
											.getSessionID(), EntityByLanguage
											.getParentIds(scrOrgFromFather)
											.intValue(), locale.getLanguage(),
											useCaseConf.getEntidadId());
							if (EntityByLanguage.getOrgAcron(scrOrgTopLevel) != null) {
								aux = EntityByLanguage
										.getOrgAcron(scrOrgTopLevel);
							}
						}
						if (aux.length() > 0) {
							ref = name + GUION + aux;
						} else {
							ref = name;
						}
					}
				}
			}
			break;
		}
		default: {
			break;
		}
		}

		if (results != null) {
			Map parentNames = null;
			if (vldQuery != null && !vldQuery.equals("")) {
				parentNames = getParentNames(useCaseConf, results);
			}
			List fieldsInfo = createFieldsInfo(3, 0);

			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(), locale,
							useCaseConf.getEntidadId());

			frmData = frmDataBusc;
			return XMLTypeAdm.createXMLValidationList(results, initValue,
					enabled, idCrl, typeId, typeBusc, frmData, fldId, locale, name, ref,
					parentNames, fieldsInfo,
					sessionInformation.getCaseSensitive());
		} else {
			return null;
		}
	}

	public Document getValidationListTypeUA(UseCaseConf useCaseConf,
			int initValue, int typeId, int typeBusc, int fldId, int idCrl,
			int enabled, int frmData, int frmDataBusc, String vldQuery)
			throws AttributesException, ValidationException, SessionException,
			BookException {

		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		ValidationResults results = null;
		String name = "";
		String ref = "";
		switch (typeBusc) {
		case 0: {
			results = ValidationSession.getInitialValidationTreeResults(
					useCaseConf.getSessionID(), initValue, Configurator
							.getInstance().getDefaultPageValidationListSize(),
					vldQuery, enabled, locale, useCaseConf.getEntidadId());
			Object srcOrgAux = null;
			if (results.getResults() != null
					&& results.getResults().iterator().hasNext()) {
				srcOrgAux = results.getResults().iterator().next();
			}
			if (!results.getResults().isEmpty()
					&& (srcOrgAux instanceof ScrOrg
							|| srcOrgAux instanceof ScrOrgeu
							|| srcOrgAux instanceof ScrOrggl || srcOrgAux instanceof ScrOrgct)
					&& (vldQuery == null || vldQuery.equals(""))) {
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), 1, locale.getLanguage(),
						useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			}
			break;
		}
		case 1: {
			if (typeId == 0) {
				results = ValidationSession.getScrOrgsForAdminUnitsWithType(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrTypeadm = ValidationSessionEx.getScrTypeAdm(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getScrTypeAdmName(scrTypeadm);
			} else {
				results = ValidationSession.getScrOrgsForAdminUnitsWithFather(
						useCaseConf.getSessionID(), initValue, Configurator
								.getInstance()
								.getDefaultPageValidationListSize(), vldQuery,
						enabled, idCrl, locale, useCaseConf.getEntidadId());
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				name = EntityByLanguage.getOrgNames(scrOrg);
				// Obtener el padre de nivel superior
				String aux = "";
				if (EntityByLanguage.getParentIds(scrOrg) != null) {
					Object scrOrgTopLevel = ValidationSessionEx
							.getTopLevelParentScrOrg(
									useCaseConf.getSessionID(),
									EntityByLanguage.getParentIds(scrOrg)
											.intValue(), locale.getLanguage(),
									useCaseConf.getEntidadId());
					if (EntityByLanguage.getOrgAcron(scrOrgTopLevel) != null) {
						aux = EntityByLanguage.getOrgAcron(scrOrgTopLevel);
					}
				}
				if (aux.length() > 0) {
					ref = name + GUION + aux;
				} else {
					ref = name;
				}
			}
			break;
		}
		case 2: {
			if (typeId == 0) {
				results = ValidationSession.getScrTypeAdm(useCaseConf
						.getSessionID(), initValue, Configurator.getInstance()
						.getDefaultPageValidationListSize(), vldQuery, locale,
						useCaseConf.getEntidadId());
			} else {
				Object scrOrg = ValidationSessionEx.getScrOrg(
						useCaseConf.getSessionID(), idCrl,
						locale.getLanguage(), useCaseConf.getEntidadId());
				if (EntityByLanguage.getParentIds(scrOrg) == null) {
					results = ValidationSession.getScrTypeAdm(useCaseConf
							.getSessionID(), initValue, Configurator
							.getInstance().getDefaultPageValidationListSize(),
							vldQuery, locale, useCaseConf.getEntidadId());
				} else {
					Object scrOrgVal = ValidationSessionEx.getScrOrg(
							useCaseConf.getSessionID(), EntityByLanguage
									.getParentIds(scrOrg).intValue(), locale
									.getLanguage(), useCaseConf.getEntidadId());
					if (EntityByLanguage.getParentIds(scrOrgVal) == null) {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithType(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery,
										enabled,
										EntityByLanguage
												.getScrTypeAdmIdFromOrg(
														scrOrgVal).intValue(),
										locale, useCaseConf.getEntidadId());
						Object scrAdm = ValidationSessionEx.getScrTypeAdm(
								useCaseConf.getSessionID(), EntityByLanguage
										.getScrTypeAdmIdFromOrg(scrOrg)
										.intValue(), locale.getLanguage(),
								useCaseConf.getEntidadId());
						name = EntityByLanguage.getScrTypeAdmName(scrAdm);
					} else {
						results = ValidationSession
								.getScrOrgsForAdminUnitsWithFather(
										useCaseConf.getSessionID(),
										initValue,
										Configurator
												.getInstance()
												.getDefaultPageValidationListSize(),
										vldQuery, enabled, EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale,
										useCaseConf.getEntidadId());
						Object scrOrgFromFather = ValidationSessionEx
								.getScrOrg(useCaseConf.getSessionID(),
										EntityByLanguage
												.getParentIds(scrOrgVal)
												.intValue(), locale
												.getLanguage(), useCaseConf
												.getEntidadId());
						name = EntityByLanguage.getOrgNames(scrOrgFromFather);
						// Obtener el padre de nivel superior
						String aux = "";
						if (EntityByLanguage.getParentIds(scrOrgFromFather) != null) {
							Object scrOrgTopLevel = ValidationSessionEx
									.getTopLevelParentScrOrg(useCaseConf
											.getSessionID(), EntityByLanguage
											.getParentIds(scrOrgFromFather)
											.intValue(), locale.getLanguage(),
											useCaseConf.getEntidadId());
							if (EntityByLanguage.getOrgAcron(scrOrgTopLevel) != null) {
								aux = EntityByLanguage
										.getOrgAcron(scrOrgTopLevel);
							}
						}
						if (aux.length() > 0) {
							ref = name + GUION + aux;
						} else {
							ref = name;
						}
					}
				}
			}
			break;
		}
		default: {
			break;
		}
		}

		if (results != null) {
			Map parentNames = null;
			if (vldQuery != null && !vldQuery.equals("")) {
				parentNames = getParentNames(useCaseConf, results);
			}
			List fieldsInfo = createFieldsInfo(3, 0);

			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(), locale,
							useCaseConf.getEntidadId());

			frmData = frmDataBusc;
			return XMLTypeAdm.createXMLValidationList(results, initValue,
					enabled,idCrl, typeId, typeBusc, frmData, fldId, locale, name, ref,
					parentNames, fieldsInfo,
					sessionInformation.getCaseSensitive());
		} else {
			return null;
		}
	}

	public Document getValidationListTypeProv(UseCaseConf useCaseConf,
			Integer bookID, int initValue, int typeId, int typeBusc, int fldId,
			int idCrl, String vldQuery, String additionalFieldName,
			String docColName) throws AttributesException, ValidationException,
			SessionException, BookException {
		ValidationResults results = null;
		String name = "";
		String ref = "";

		switch (typeBusc) {
		case 0: {
			results = ProvTypeInvesdoc
					.getInitialValidationTypeInvesdocProvResults(useCaseConf
							.getSessionID(), initValue, idCrl, Configurator
							.getInstance().getDefaultPageValidationListSize(),
							vldQuery, additionalFieldName, docColName,
							useCaseConf.getEntidadId());

			break;
		}
		case 1: {
			results = ProvTypeInvesdoc.getCitiesFromProv(useCaseConf
					.getSessionID(), initValue, idCrl, Configurator
					.getInstance().getDefaultPageValidationListSize(),
					vldQuery, additionalFieldName, docColName, useCaseConf
							.getEntidadId());
			if (typeId == 0) {
				ScrProv scrProv = ProvTypeInvesdoc.getScrProvById(
						useCaseConf.getSessionID(), idCrl,
						useCaseConf.getEntidadId());
				name = scrProv.getName();
			} else {
				ScrCity scrCity = ProvTypeInvesdoc.getScrCityById(
						useCaseConf.getSessionID(), idCrl,
						useCaseConf.getEntidadId());
				ScrProv scrProv = ProvTypeInvesdoc.getScrProvById(
						useCaseConf.getSessionID(), scrCity.getIdProv(),
						useCaseConf.getEntidadId());

				name = scrCity.getName() + GUION + scrProv.getName();
			}
			break;
		}
		case 2: {
			results = ProvTypeInvesdoc
					.getProvForCities(useCaseConf.getSessionID(), initValue,
							Configurator.getInstance()
									.getDefaultPageValidationListSize(),
							vldQuery, additionalFieldName, docColName,
							useCaseConf.getEntidadId());
			break;
		}
		default: {
			break;
		}
		}

		if (results != null) {

			SessionInformation sessionInformation = BookSession
					.getSessionInformation(useCaseConf.getSessionID(),
							useCaseConf.getLocale(), useCaseConf.getEntidadId());

			return XMLTypeInvesDoc.createXMLValidationList(results, initValue,
					typeBusc, useCaseConf.getLocale(), name, ref, null,
					sessionInformation.getCaseSensitive());
		} else {
			return null;
		}
	}

	public String getVldCiudad(UseCaseConf useCaseConf, Integer idProv)
			throws AttributesException, ValidationException, SessionException {
		String cities = null;
		Document doc = null;
		String result = null;
		cities = ValidationSessionEx.getScrCities(useCaseConf.getSessionID(),
				idProv, useCaseConf.getEntidadId());
		if (cities != null && !cities.equals("")) {
			try {
				doc = createFromStringText(cities);
				result = doc.asXML();
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_CITIES);
			}
		} else {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_CITIES);
		}
		return result;
	}

	public String getVldPoblacion(UseCaseConf useCaseConf)
			throws AttributesException, ValidationException, SessionException {
		String provinces = null;
		Document doc = null;
		String result = null;

		provinces = ValidationSessionEx.getScrProv(useCaseConf.getSessionID(),
				useCaseConf.getEntidadId());
		if (provinces != null && !provinces.equals("")) {
			try {
				doc = createFromStringText(provinces);
				result = doc.asXML();
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_PROV);
			}
		} else {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		}

		return result;
	}

	public Object getVldDirection(UseCaseConf useCaseConf, Integer idPerson,
			boolean isDirInter, int typeAddress) throws AttributesException,
			ValidationException, SessionException {
		String result = null;
		int size = 0;
		Document doc = null;

		if (isDirInter) {
			result = ValidationSessionInter
					.getDirInter(
							useCaseConf.getSessionID(),
							idPerson,
							Integer.parseInt(Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
							useCaseConf.getEntidadId(), typeAddress);
		} else {
			result = ValidationSessionEx
					.getDir(useCaseConf.getSessionID(),
							idPerson,
							Integer.parseInt(Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
							useCaseConf.getEntidadId(), 0);

		}
		if (result != null) {

			try {
				doc = createFromStringText(result);
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_INTER);
			}
			if (doc != null) {
				if (isDirInter) {
					if (typeAddress == 0) {
						List nodeList = doc
								.selectNodes(Keys.XPATH_PERSONA_DOMICILIO);
						size = nodeList.size();
					} else {
						List nodeList = doc
								.selectNodes(Keys.XPATH_PERSONA_TELEMATICA);
						size = nodeList.size();
					}
				} else {
					List nodeList = doc
							.selectNodes(Keys.XPATH_PERSONA_DOMICILIO);
					size = nodeList.size();
				}
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("getVldDirection [" + idPerson + "] size ["
						+ size + "]");
			}
		}
		if (result == null) {
			return Keys.I18N_EXCEPTION_CANNOT_MODIFY_PERSON_INFO;
		} else if (size > Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES))) {
			return Keys.I18N_EXCEPTION_TOOMANYROWS;
		} else {
			return XMLDirection.createXMLDirection(doc, size,
					useCaseConf.getLocale(), isDirInter, typeAddress);
		}
	}

	public String getBuscInter(UseCaseConf useCaseConf, Boolean searchPFis,
			Boolean searchPJur, Integer inicio, String wherePFis,
			String wherePJur, int viewDirInter) throws AttributesException,
			ValidationException, SessionException {
		String result = null;
		Integer rango = Integer.valueOf(Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_PERSON_SIZE));
		result = ValidationSessionInter
				.getBuscInter(
						useCaseConf.getSessionID(),
						searchPFis,
						searchPJur,
						inicio,
						rango,
						wherePFis,
						wherePJur,
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
						useCaseConf.getEntidadId());
		Document doc = null;
		if (result != null && !result.equals("")) {
			try {
				doc = createFromStringText(result);
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_INTER);
			}
			if (doc != null) {
				XMLBuscInter.parseXML(doc);
			}
		}
		if (result.equals("")) {
			return XMLBuscInter.createXMLValidationBuscInter(
					useCaseConf.getLocale()).asXML();
		} else {
			Document xmlDocument = XMLTypeAdm
					.createXMLValidateIntListActionForm(doc, "ValidateInt",
							inicio.intValue(), rango.intValue(), "",
							new Integer(9).intValue(), useCaseConf.getLocale());

			if (viewDirInter != 0) {
				xmlDocument = addAddressValidateInt(xmlDocument,
						useCaseConf.getSessionID(), useCaseConf.getEntidadId());
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			StringWriter writer = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(writer, format);
			try {
				xmlWriter.write(xmlDocument);
			} catch (IOException e) {
				throw new ValidationException(
						ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);
			}
			String xml = writer.toString();

			if (_logger.isDebugEnabled()) {
				_logger.debug(xml);
			}

			return xml;
		}
	}

	public Map getParentNames(UseCaseConf useCaseConf, ValidationResults results)
			throws AttributesException, ValidationException, SessionException {

		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		String parentName = "";
		Map result = new HashMap();
		for (Iterator it = results.getResults().iterator(); it.hasNext();) {
			Object object = it.next();
			Integer idFather = EntityByLanguage.getParentIds(object);
			parentName = getParentName(idFather, useCaseConf.getSessionID(),
					useCaseConf.getEntidadId(), useCaseConf.getLocale()
							.getLanguage());
			result.put(EntityByLanguage.getOrgIds(object), parentName);
		}

		return result;
	}

	/**
	 * Este metodo devuelve los tipos de documento que es posible asignar a una
	 * persona, en funcion del tipo que sea (fisica o juridica)
	 *
	 * @param useCaseConf
	 * @param personType
	 * @return
	 * @throws AttributesException
	 * @throws ValidationException
	 * @throws SessionException
	 */
	public String getVldTypeDocs(UseCaseConf useCaseConf, Integer personType)
			throws AttributesException, ValidationException, SessionException {
		String result = null;

		String typeDocs = ValidationSessionEx.getTypeDocs(
				useCaseConf.getSessionID(), personType,
				useCaseConf.getEntidadId());

		if (typeDocs != null && !typeDocs.equals("")) {
			try {
				Document doc = createFromStringText(typeDocs);
				doc = XMLTypeDoc.createXMLTypeDoc(doc, useCaseConf.getLocale());
				result = doc.asXML();
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_TYPE_DOCS);
			}
		} else {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_DOCS);
		}
		return result;

	}

	public String getVldTypeAddresses(UseCaseConf useCaseConf)
			throws AttributesException, ValidationException, SessionException {
		String result = null;

		String typeAddresses = ValidationSessionEx.getTypeAddresses(
				useCaseConf.getSessionID(), useCaseConf.getEntidadId());

		if (typeAddresses != null && !typeAddresses.equals("")) {
			try {
				Document doc = createFromStringText(typeAddresses);
				doc = XMLTypeAddress.createXMLTypeAddress(doc,
						useCaseConf.getLocale());
				result = doc.asXML();
			} catch (Exception e) {
				throw new AttributesException(
						AttributesException.ERROR_CANNOT_FIND_TYPE_ADDRESSES);
			}
		} else {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_ADDRESSES);
		}
		return result;

	}

	public static List createFieldsInfo(int fieldsNumber, int type) {
		List result = new ArrayList();
		XMLFieldsInfo xmlFieldsInfo = null;
		if (fieldsNumber == 3) {
			for (int i = 0; i < fieldsNumber; i++) {
				xmlFieldsInfo = new XMLFieldsInfo(i);
				result.add(xmlFieldsInfo);
			}
		}
		if (fieldsNumber == 1) {
			xmlFieldsInfo = new XMLFieldsInfo(3);
			result.add(xmlFieldsInfo);
		}
		if (fieldsNumber == 2) {
			for (int i = 0; i < fieldsNumber; i++) {
				if (i == 1) {
					if (type == 1) {
						xmlFieldsInfo = new XMLFieldsInfo(4);
					}
					if (type == 2) {
						xmlFieldsInfo = new XMLFieldsInfo(2);
					}
				} else {
					xmlFieldsInfo = new XMLFieldsInfo(i);
				}
				result.add(xmlFieldsInfo);
			}
		}

		return result;
	}

	public String getDeptsGroupsUsers(UseCaseConf useCaseConf,
			Integer userTypeId) throws ValidationException, SessionException,
			AttributesException {
		List deptsGroupsUsers = null;
		Document doc = null;
		String result = null;
		String deptsGroupsUsersXML = null;

		if (useCaseConf.getUseLdap().booleanValue()) {
			deptsGroupsUsers = ValidationSessionEx.getGroupsUsersLDAP(
					useCaseConf.getSessionID(), userTypeId,
					useCaseConf.getEntidadId());
		} else {
			deptsGroupsUsers = ValidationSessionEx.getDeptsGroupsUsers(
					useCaseConf.getSessionID(), userTypeId,
					useCaseConf.getEntidadId());
		}
		deptsGroupsUsersXML = XMLDeptsGroupsUsers
				.createXMLDeptsGroupsUser(deptsGroupsUsers);

		if (StringUtils.isNotEmpty(deptsGroupsUsersXML)) {
			try {
				doc = createFromStringText(deptsGroupsUsersXML);
				result = doc.asXML();
			} catch (Exception e) {
				throw new ValidationException(
						ValidationException.ERROR_USERS_LIST_NOT_FOUND);
			}
		} else {
			throw new ValidationException(
					ValidationException.ERROR_USERS_LIST_NOT_FOUND);
		}
		return result;
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/*******************************************************************************************************************
	 * Private methods
	 ******************************************************************************************************************/

	private static Document createFromStringText(String text) throws Exception {
		Document document = null;
		if (text != null) {
			document = DocumentHelper.parseText(text);
		}
		return document;

	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param document
	 * @param sessionID
	 * @param entidad
	 * @throws AttributesException
	 * @throws ValidationException
	 * @throws SessionException
	 */
	private static Document addAddressValidateInt(Document document,
			String sessionID, String entidad) throws AttributesException,
			ValidationException, SessionException {
		Element root = document.getRootElement();
		List list = root.selectNodes(XPATH_PERSONA_ROOT);

		if ((list != null) && (list.size() > 0)) {

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Element persona = (Element) iterator.next();

				if (persona != null) {
					persona = addAddressPersona(persona, sessionID, entidad);
				}
			}
		}

		return document;
	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param persona
	 * @param sessionID
	 * @param entidad
	 * @return
	 * @throws AttributesException
	 * @throws ValidationException
	 * @throws SessionException
	 */
	private static Element addAddressPersona(Element persona, String sessionID,
			String entidad) throws AttributesException, ValidationException,
			SessionException {
		for (Iterator iter = persona.elementIterator(); iter.hasNext();) {
			Element element = (Element) iter.next();

			if (XML_ID_TEXT.equalsIgnoreCase(element.getQName().getName())) {
				Element parent = element.getParent();

				if (parent != null) {
					NodeComparator nodeComparator = new NodeComparator();
					if (nodeComparator.compare(parent, persona) == 0) {
						String idValue = element.getText();
						String xmlAddress = getAddress(idValue, sessionID,
								entidad);
						String xmlAddressTelematica = getAddressTelematica(
								idValue, sessionID, entidad);

						Document docAddress = getAddressDocument(xmlAddress);

						if (docAddress != null) {
							persona = addDomicilioFromDocumentAddress(persona,
									docAddress);
						}

						Document docAddressTel = getAddressDocument(xmlAddressTelematica);

						if (docAddressTel != null) {
							persona = addTelematicaFromDocumentAddress(persona,
									docAddressTel);
						}
					}
				}
				break;
			}
		}

		return persona;
	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param xmlAddress
	 * @return
	 * @throws AttributesException
	 */
	private static Document getAddressDocument(String xmlAddress)
			throws AttributesException {
		Document docAddress = null;
		try {
			docAddress = createFromStringText(xmlAddress);
		} catch (Exception e) {
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		}

		return docAddress;
	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param persona
	 * @param docAddress
	 * @return
	 */
	private static Element addDomicilioFromDocumentAddress(Element persona,
			Document docAddress) {
		Element rootAddres = docAddress.getRootElement();
		List listAdrres = rootAddres.selectNodes(XPATH_DOMICILIO_ROOT);
		if ((listAdrres != null) && (listAdrres.size() > 0)) {
			for (Iterator it = listAdrres.iterator(); it.hasNext();) {
				Element direccion = (Element) it.next();

				if (direccion != null) {
					persona = getPreferredAdrres(persona, direccion,
							listAdrres.size());
				}
			}
		}

		return persona;
	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param idPersona
	 * @param sessionID
	 * @param entidad
	 * @return
	 * @throws AttributesException
	 * @throws ValidationException
	 * @throws SessionException
	 */
	private static String getAddress(String idPersona, String sessionID,
			String entidad) throws AttributesException, ValidationException,
			SessionException {
		if ((idPersona != null) && (idPersona.length() > 0)) {
			Integer personID = new Integer(idPersona);

			String direcciones = ValidationSessionInter
					.getDirInter(
							sessionID,
							personID,
							Integer.parseInt(Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
							entidad, 0);

			return direcciones;
		}

		return null;
	}

	/**
	 * 66575267 - Gabriel Saiz
	 *
	 * @param persona
	 * @param direccion
	 * @param size
	 * @return
	 */
	private static Element getPreferredAdrres(Element persona,
			Element direccion, int size) {
		for (Iterator iterat = direccion.elementIterator(); iterat.hasNext();) {
			Element elementAddres = (Element) iterat.next();
			if (XML_PREFERENCIA_TEXT.equalsIgnoreCase(elementAddres.getQName()
					.getName())) {
				Element parentAdrres = elementAddres.getParent();

				if (parentAdrres != null) {
					NodeComparator nodeComparatorAdrres = new NodeComparator();
					if (nodeComparatorAdrres.compare(parentAdrres, direccion) == 0) {
						String prefValue = elementAddres.getText();

						if (("1".equals(prefValue)) || (size == 1)) {
							List elementosPersona = persona.elements();
							elementosPersona.add(direccion.clone());
							persona.setContent(elementosPersona);
						}
					}
				}
				break;
			}
		}

		return persona;
	}

	private static String getAddressTelematica(String idPersona,
			String sessionID, String entidad) throws AttributesException,
			ValidationException, SessionException {
		if ((idPersona != null) && (idPersona.length() > 0)) {
			Integer personID = new Integer(idPersona);

			String direcciones = ValidationSessionInter
					.getDirInter(
							sessionID,
							personID,
							Integer.parseInt(Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES)),
							entidad, 1);

			return direcciones;
		}

		return null;
	}

	private static Element addTelematicaFromDocumentAddress(Element persona,
			Document docAddress) {
		Element rootAddres = docAddress.getRootElement();
		List listAdrres = rootAddres.selectNodes(XPATH_TELEMATICA_ROOT);
		if ((listAdrres != null) && (listAdrres.size() > 0)) {
			for (Iterator it = listAdrres.iterator(); it.hasNext();) {
				Element direccion = (Element) it.next();

				if (direccion != null) {
					persona = getPreferedTelematicaAddress(persona, direccion,
							listAdrres.size());
				}
			}
		}

		return persona;
	}

	private static Element getPreferedTelematicaAddress(Element persona,
			Element direccion, int size) {
		for (Iterator iterat = direccion.elementIterator(); iterat.hasNext();) {
			Element elementAddres = (Element) iterat.next();
			if (XML_PREFERENCIATEL_TEXT.equalsIgnoreCase(elementAddres
					.getQName().getName())) {
				Element parentAdrres = elementAddres.getParent();

				if (parentAdrres != null) {
					NodeComparator nodeComparatorAdrres = new NodeComparator();
					if (nodeComparatorAdrres.compare(parentAdrres, direccion) == 0) {
						String prefValue = elementAddres.getText();

						if (("1".equals(prefValue)) || (size == 1)) {
							List elementosPersona = persona.elements();
							elementosPersona.add(direccion.clone());
							persona.setContent(elementosPersona);
						}
					}
				}
				break;
			}
		}

		return persona;
	}

	private static String getParentName(Integer idFather, String sessionID,
			String entidad, String language) throws AttributesException,
			ValidationException, SessionException {
		String aux = "";
		String parentName = "";
		if (idFather != null) {
			Object object = ValidationSessionEx.getScrOrg(sessionID,
					idFather.intValue(), language, entidad);
			if (object instanceof ScrOrg) {
				parentName = ((ScrOrg) object).getName();
			} else if (object instanceof ScrOrgeu) {
				parentName = ((ScrOrgeu) object).getName();
			} else if (object instanceof ScrOrggl) {
				parentName = ((ScrOrggl) object).getName();
			} else if (object instanceof ScrOrgct) {
				parentName = ((ScrOrgct) object).getName();
			}
			// Obtener el padre de nivel superior
			if (object instanceof ScrOrg
					&& ((ScrOrg) object).getIdFather() != null) {
				ScrOrg scr = (ScrOrg) ValidationSessionEx
						.getTopLevelParentScrOrg(sessionID,
								idFather.intValue(), language, entidad);
				if (scr != null) {
					aux = scr.getAcron();
				}
			} else if (object instanceof ScrOrgeu
					&& ((ScrOrgeu) object).getIdFather() != null) {

				ScrOrgeu screu = (ScrOrgeu) ValidationSessionEx
						.getTopLevelParentScrOrg(sessionID,
								idFather.intValue(), language, entidad);
				if (screu != null) {
					aux = screu.getAcron();
				}
			} else if (object instanceof ScrOrggl
					&& ((ScrOrggl) object).getIdFather() != null) {

				ScrOrggl scrgl = (ScrOrggl) ValidationSessionEx
						.getTopLevelParentScrOrg(sessionID,
								idFather.intValue(), language, entidad);
				if (scrgl != null) {
					aux = scrgl.getAcron();
				}
			} else if (object instanceof ScrOrgct
					&& ((ScrOrgct) object).getIdFather() != null) {

				ScrOrgct scrct = (ScrOrgct) ValidationSessionEx
						.getTopLevelParentScrOrg(sessionID,
								idFather.intValue(), language, entidad);
				if (scrct != null) {
					aux = scrct.getAcron();
				}
			}
		}
		if (aux.length() > 0) {
			parentName = parentName + GUION + aux;
		}
		return parentName;

	}

	/**
	 * Método que nos devuelve la ultima unidad administrativa selecionada de
	 * origen o destino
	 *
	 * @param useCaseConf
	 * @param bookID
	 * @param typeUnitOrigOrDest
	 * @param locale
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	private static ScrOrg getLastSelectedUnit(UseCaseConf useCaseConf,
			Integer bookID, Integer typeUnitOrigOrDest, Locale locale)
			throws ValidationException, BookException, SessionException {
		Map selectUnitsMap = BookSession.getUserRememberUnitSelected(
				useCaseConf.getSessionID(), bookID, locale,
				useCaseConf.getEntidadId());
		if (selectUnitsMap == null || selectUnitsMap.isEmpty()) {
			return null;
		}

		String unitCode = (String) selectUnitsMap.get(typeUnitOrigOrDest);
		if (StringUtils.isBlank(unitCode)) {
			return null;
		}

		ScrOrg selectedUnit = UtilsSessionEx.getScrOrgByCode(
				useCaseConf.getSessionID(), bookID, unitCode,
				useCaseConf.getEntidadId());

		return selectedUnit;
	}

}