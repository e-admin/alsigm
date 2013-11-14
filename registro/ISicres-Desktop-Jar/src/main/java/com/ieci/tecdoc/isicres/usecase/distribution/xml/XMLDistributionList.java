package com.ieci.tecdoc.isicres.usecase.distribution.xml;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrct;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdreu;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrgl;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.ScrDistRegResults;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLUtils;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLValidationListAbstract;

/*
 * @author LMVICENTE @creationDate 26-oct-2004 12:59:29
 *
 * @version @since
 */
public class XMLDistributionList extends XMLValidationListAbstract implements Keys {

	private static Logger _logger = Logger.getLogger(XMLDistributionList.class);
    private static SimpleDateFormat dateFormat = null;

	private static final String FLD = "Fld";

    public static Document getXMLDistributionList(int init, int paso,
			int total, Locale locale, List listScrDistRegResults, int typeDist,
			Integer timeout, Integer[] distPerms, Date actualDate,
			SessionInformation sessionInformation) {

	//obtenemos el formato de fecha con el que se trabajará
	dateFormat = XMLUtils.getDateFormatView(locale);

		Document doc = createDocument(init, paso, total, distPerms,
				sessionInformation.getCaseSensitive());
		Element root = doc.getRootElement();

		addHeadMinuta(locale, root, typeDist);
		Element bodyMinuta = root.addElement(XML_BODYMINUTA_TEXT);

		if (total != 0 && listScrDistRegResults != null
				&& !listScrDistRegResults.isEmpty()) {

			for (Iterator iterator = listScrDistRegResults.iterator(); iterator
					.hasNext();) {
				ScrDistRegResults scrDistRegResutl = (ScrDistRegResults) iterator
						.next();
				addBodyMinuta(scrDistRegResutl, locale, bodyMinuta, paso,
						typeDist, timeout, sessionInformation
								.getCaseSensitive(), actualDate);
			}

		} else {
			addNoBobyMinuta(locale, bodyMinuta);
		}
		return doc;
	}

    public static Document getXMLSaveRemarks(int id, String remarks, Locale locale) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(XML_DISTRIBUTION_ROOT);

        root.addAttribute(XML_ID_TEXT, Integer.toString(id));
        root.addElement(XML_MESSAGE_TEXT).add(DocumentHelper.createCDATA(remarks));

        return document;
    }

    public static Document getXMLDistributionClausuleWhere(Object distWhere, Object regWhere, Locale locale) {

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_VALIDATION_TEXT);
        if (distWhere instanceof List){
        	root.addElement(XML_ERROR_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR)));
    		root.addElement(XML_FLDNAME_TEXT).add(DocumentHelper.createCDATA((String) ((List) distWhere).get(0)));
        } else if (regWhere instanceof List){
        	root.addElement(XML_ERROR_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR)));
    		root.addElement(XML_FLDNAME_TEXT).add(DocumentHelper.createCDATA((String) ((List) regWhere).get(0)));
        } else {
        	root.addElement(XML_DISTWHERE_TEXT).add(DocumentHelper.createCDATA((String) distWhere));
    		root.addElement(XML_REGWHERE_TEXT).add(DocumentHelper.createCDATA((String) regWhere));
        }

        return document;
    }

    public static Document getXMLDistributionVldBooks( Map createPermBooks) {

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addBooks(root, createPermBooks);

        return document;
    }

    public static Document getXMLDistributionSearchList( int typeDist, Locale locale, String dataBaseType, String caseSensitive) {

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addSearchTitle(root, locale);
        addDistRegFields(root, typeDist, locale, 1, dataBaseType, caseSensitive);
        addDistRegFields(root, typeDist, locale, 2, dataBaseType, caseSensitive);

        return document;
    }

    private static void addBooks(Element parent, Map createPermBooks){

    	Element book = null;
    	Integer bookId = null;
    	String bookName = null;
    	for (Iterator it = createPermBooks.keySet().iterator(); it.hasNext();) {

        	bookId = (Integer) it.next();
        	bookName = (String) createPermBooks.get(bookId);
        	book = parent.addElement(XML_BOOK_TEXT);
        	Element id = book.addElement(XML_ID_TEXT);
        	id.addText(bookId.toString());
        	Element name = book.addElement(XML_NAME_UPPER_TEXT);
        	name.addText(bookName);

    	}
    }

    private static void addSearchTitle(Element parent, Locale locale){

		Element title = parent.addElement(XML_TITLE_TEXT);
		title.addText(RBUtil.getInstance(locale).getProperty(BOOKUSECASE_DISTRIBUTIONSEARCH_TITLE));

    }

    private static void addDistRegFields(Element parent, int typeDist, Locale locale, int typeSearch, String dataBaseType, String caseSensitive){

    	DistributionSearchFields distributionSearchFields = null;
    	Element distRegFields = null;

    	if (typeSearch == 1){
    		distRegFields = parent.addElement(XML_DISTFIELDS_TEXT);
        	distRegFields.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(BOOKUSECASE_DISTRIBUTIONSEARCH_BY_DISTRIBUTION)));
    	} else {
    		distRegFields = parent.addElement(XML_REGFIELDS_TEXT);
        	distRegFields.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(BOOKUSECASE_DISTRIBUTIONSEARCH_BY_FLD)));
    	}

		distributionSearchFields = new DistributionSearchFields(new Integer(typeSearch), new Integer(typeDist), locale, dataBaseType);
		List fieldSearch = distributionSearchFields.getResult();
		Element fields = null;
    	for (int i = 0; i < fieldSearch.size(); i++){
    		DistributionFields distributionFields = (DistributionFields) fieldSearch.get(i);
    		fields = distRegFields.addElement(XML_FIELD_TEXT);
        	if (typeSearch == 1){
        		fields.addAttribute(XML_ID_TEXT,XML_DISTFIELD_TEXT);
        	} else {
        		fields.addAttribute(XML_ID_TEXT,XML_REGFIELD_TEXT);
        	}
    		fields.addAttribute(XML_DATATYPE_TEXT,distributionFields.getFieldType().toString());
    		fields.addAttribute(XML_CASESENSITIVE_TEXT,caseSensitive);
    		int validation = distributionFields.getFieldValidation().intValue();
    		if (validation != 0){
        		fields.addAttribute(XML_VALIDATION_TEXT,"1");
        		if (validation == 9 || validation == 10){
            		fields.addAttribute(XML_FLDID_TEXT,"5");
            		fields.addAttribute(XML_TVALID_TEXT,"4");
        		}
        		if (validation == 1){
        			int fldid;
        			if (distributionFields.getFieldName().equals("@FLD7")){
        				fldid=7;
        			}else{
        				fldid=8;
        			}
            		fields.addAttribute(XML_FLDID_TEXT,Integer.toString(fldid));
            		fields.addAttribute(XML_TVALID_TEXT,"8");
        		}

        		if (validation == 6){
            		fields.addAttribute(XML_FLDID_TEXT,"16");
            		fields.addAttribute(XML_TVALID_TEXT,"3");
        		}

    		}
    		fields.addElement(XML_FIELDLABEL_TEXT).add(DocumentHelper.createCDATA(distributionFields.getFieldLabel()));
    		fields.addElement(XML_FIELDNAME_TEXT).add(DocumentHelper.createCDATA(distributionFields.getFieldName()));
    		Element fieldOperator = fields.addElement(XML_OPERATORS_TEXT);
    		Map operators = distributionFields.getOperators();
            for (Iterator it = operators.keySet().iterator(); it.hasNext();) {
                String operator = (String) it.next();
                operator = operator.substring(1, operator.length());
        		fieldOperator.addElement(XML_OPERATOR_TEXT).add(DocumentHelper.createCDATA(operator));
        	}
    		String key= null;
			String state = null;
        	if (distributionFields.getFieldType().intValue() == 5){
        		Element values = fields.addElement(XML_VALUES_TEXT);
				key = I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE;
				Element value = null;

				int limit = (typeDist == 1)?6:7;
        		for (int k = 0; k < limit; k++){
					state = key + k;
	        		value = values.addElement(XML_VALUE_TEXT);
	        		value.addAttribute(XML_VALUEID_TEXT, new Integer (k).toString());
	        		if (k > 0){
		        		value.add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(state)));
	        		} else {
	            		value.add(DocumentHelper.createCDATA("-----"));

	        		}
        		}
        	}

    	}
    }

    private static int getOutOfDate(Date distDate, Date actualDate, Integer timeout) {
    	int result = 0;
	    GregorianCalendar gc = new GregorianCalendar();
	    Date aux = new Date();
	    Date aux1 = new Date();
	    gc.setTime(distDate);
	    aux1.setTime(gc.getTimeInMillis());
	    gc.add(Calendar.HOUR_OF_DAY, timeout.intValue());
	    aux.setTime(gc.getTimeInMillis());
	    if (aux.before(actualDate)){
	    	result = 1;
	    }
    	return result;
    }

    private static void addHeadMinuta(Locale locale, Element parent,
			int typeDist) {
		Element headMinuta = parent.addElement(XML_HEADMINUTA_TEXT);

		// Columna 1 Libro de registro
		String width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL1);
		String cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL1);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "BOOKNAME")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 2 Numero de registro
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL2);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL2);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "FLD1")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 3 Fecha de Registro
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL3);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL3);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "FLD2")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 4 Destino
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL4);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL4);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "FLD8_TEXT")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 5 Tipo de Asunto
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL5);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL5);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "ASUNTO_TEXT")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 6 Resumen
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL6);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL6);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "RESUMEN")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 7 - Origen de la Distribucion (Distribucion de Entrada)
		// Columna 7b - Destino de la Distribucion (Distribucion de Entrada)
		// Columna 7 - Destino de la Distribucion (Distribucion de Salida)
		// Columna 7b - Origen de la Distribucion (Distribucion de Salida)
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL7);
		if (typeDist == 1) {
			cdata = RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL12);
			headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT,
					width).add(DocumentHelper.createCDATA(cdata));

			cdata = RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL13);
			headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT,
					width).add(DocumentHelper.createCDATA(cdata));
		} else {
			cdata = RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL13);
			headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT,
					width).add(DocumentHelper.createCDATA(cdata));
			cdata = RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL12);
			headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT,
					width).add(DocumentHelper.createCDATA(cdata));
		}

		// Columna Distribución actual
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL16);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width)
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 8 Estado
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL8);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL8);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "DIST_STATE")
				.add(DocumentHelper.createCDATA(cdata));

		// Columna 9 Fecha de Estado
		width = RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL9);
		cdata = RBUtil.getInstance(locale).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL9);
		headMinuta.addElement(XML_COL_TEXT).addAttribute(XML_WIDTH_TEXT, width).addAttribute(FLD, "DIST_STATE_DATE")
				.add(DocumentHelper.createCDATA(cdata));
	}

    private static void addBodyMinuta(ScrDistRegResults scr,
			Locale locale, Element parent, int paso, int typeDist,
			Integer timeout,
			String caseSensitive, Date actualDate) {

		if (_logger.isDebugEnabled()) {
			_logger.debug("distReg.getIdArch() distReg.getIdFdr(): "
					+ (scr.getScrDistReg().getIdArch() + "_" + scr
							.getScrDistReg().getIdFdr()));
		}

		Element row = parent.addElement(XML_ROW_UPPER_TEXT);
		String remarks = scr.getScrDistReg().getMessage();
		String text = getRegisterDate(scr.getAxsf());
		String textRemarks = "";
		int outOfDate = 0;

		if (remarks != null) {
			if (remarks.indexOf("\"") != -1) {
				textRemarks = remarks.replace('\"', '\'');
			} else {
				textRemarks = remarks;
			}
		}
		if (scr.getScrDistReg().getState() == 1 && timeout.intValue() != 0) {
			outOfDate = getOutOfDate(scr.getScrDistReg().getDistDate(),
					actualDate, timeout);
		}

		int idocarchType = 0;
		String idocarchName = null;
		Object idocarch = scr.getIdocarch();
		if (idocarch instanceof Idocarchhdr){
			idocarchType = ((Idocarchhdr) idocarch).getType();
			idocarchName = ((Idocarchhdr) idocarch).getName();
		} else if (idocarch instanceof Idocarchhdreu){
			idocarchType = ((Idocarchhdreu) idocarch).getType();
			idocarchName = ((Idocarchhdreu) idocarch).getName();
		} else if (idocarch instanceof Idocarchhdrgl){
			idocarchType = ((Idocarchhdrgl) idocarch).getType();
			idocarchName = ((Idocarchhdrgl) idocarch).getName();
		} else if (idocarch instanceof Idocarchhdrct){
			idocarchType = ((Idocarchhdrct) idocarch).getType();
			idocarchName = ((Idocarchhdrct) idocarch).getName();
		}


		row.addAttribute(XML_ID_TEXT, scr.getScrDistReg().getId().toString());
		row.addAttribute(XML_IDARCH_TEXT, String.valueOf(scr.getScrDistReg()
				.getIdArch()));
		row.addAttribute(XML_IDFDR_TEXT, String.valueOf(scr.getScrDistReg()
				.getIdFdr()));
		row.addAttribute(XML_STATE_TEXT, String.valueOf(scr.getScrDistReg()
				.getState()));
		row.addAttribute(XML_OUTOFDATE_TEXT, String.valueOf(outOfDate));
		// row.addAttribute(XML_BOOKTYPE_TEXT, String.valueOf(scr.getIdocarch()
		// .getType()));
		row.addAttribute(XML_BOOKTYPE_TEXT, String.valueOf(idocarchType));
		row.addAttribute(XML_DISTTYPE_TEXT, scr.getDistType().toString());

		// row.addElement(XML_COLDATA_TEXT).add(
		// DocumentHelper.createCDATA(scr.getIdocarch().getName()));
		row.addElement(XML_COLDATA_TEXT).add(
				DocumentHelper.createCDATA(idocarchName));
		row.addElement(XML_COLDATA_TEXT).add(
				DocumentHelper.createCDATA(scr.getAxsf()
						.getAttributeValueAsString("fld1")));

		row.addElement(XML_COLDATA_TEXT).add(DocumentHelper.createCDATA(text));
		row.addElement(XML_COLDATA_TEXT).add(
				DocumentHelper
						.createCDATA(getAdminUnit(scr.getAxsf().getFld8())));
		if (scr.getAxsf() instanceof AxSfIn) {
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(getAsuntType(((AxSfIn) scr
							.getAxsf()).getFld16())));
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getAxsf()
							.getAttributeValueAsString("fld17")));

		} else {
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(getAsuntType(((AxSfOut) scr
							.getAxsf()).getFld12())));
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getAxsf()
							.getAttributeValueAsString("fld13")));

		}

		if (typeDist == 1) {
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getSourceDescription()));
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getTargetDescription()));
		} else {
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getTargetDescription()));
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getSourceDescription()));
		}

		//Destino actual de la distribución
		if (scr.getScrDistReg().getState() == ISDistribution.STATE_REDISTRIBUIDO) {
			//mostramos el destino actual para la redistribución
			row.addElement(XML_COLDATA_TEXT).add(DocumentHelper.createText
					(scr.getTargetActualDescription().replace("\n", ", ")));
		}else{
			//mostramos el destino de la distribución
			row.addElement(XML_COLDATA_TEXT).add(
					DocumentHelper.createCDATA(scr.getTargetDescription()));
		}

		row.addElement(XML_COLDATA_TEXT).add(
				DocumentHelper.createCDATA(RBUtil.getInstance(locale)
						.getProperty(
								I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE
										+ scr.getScrDistReg().getState())));
		row.addElement(XML_COLDATA_TEXT).addText(
				dateFormat.format(scr.getScrDistReg().getStateDate()));

		row.addElement(XML_NAMEARCH_TEXT).addText(idocarchName);
		row.addElement(XML_REMARKS_TEXT).add(
				DocumentHelper.createCDATA(textRemarks));
		row.addElement(XML_CASESENSITIVE_TEXT).add(
				DocumentHelper.createCDATA(caseSensitive));
	}

    private static void addNoBobyMinuta(Locale locale, Element parent) {

        Element message = parent.addElement(XML_MESSAGE_TEXT);
        message.add(DocumentHelper.createCDATA(
                RBUtil.getInstance(locale).getProperty(
                		I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_NOBODYMINUTA )));
    }

    private static String getAdminUnit(ScrOrg org) {
        String text = "";
        if (org != null) {
            if (Configurator.getInstance().getPropertyBoolean(
                    ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_CODE)) {
                text = org.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_ABBV)) {
                text = org.getAcron();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_NAME)) {
                text = org.getName();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static String getAsuntType(ScrCa ca) {
        String text = "";
        if (ca != null) {
            if (Configurator.getInstance().getPropertyBoolean(
			ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_CODE)) {
                text = ca.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
			ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_NAME)) {
                text = ca.getMatter();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static String getRegisterDate(AxSf axsf){
    	String text = "";
    	if (axsf.getAttributeValue("fld2") != null
				&& axsf.getAttributeClass("fld2") != null) {
			if (axsf.getAttributeClass("fld2").equals(Date.class)) {
				text = dateFormat.format((Date) axsf
						.getAttributeValue("fld2"));
			}
		} else if (axsf.getAttributeClass("fld2") == null) {
			if (axsf.getAttributeValue("fld2") instanceof Date) {
				text = dateFormat.format((Date) axsf
						.getAttributeValue("fld2"));
			}
			if (axsf.getAttributeValue("fld2") instanceof java.sql.Date) {
				text = dateFormat.format(new Date(((java.sql.Date) axsf
						.getAttributeValue("fld2")).getTime()));
			}
			if (axsf.getAttributeValue("fld2") instanceof Timestamp) {
				text = dateFormat.format(new Date(((Timestamp) axsf
						.getAttributeValue("fld2")).getTime()));
			}
		}

    	return text;
    }

    public static class DateComparator implements Comparator {
        public boolean equals(Object object) {
            return false;
        }

        public int compare(Object o1, Object o2) {
            int result = ((Date) o1).compareTo((Date)o2);
            return result;
        }
    }

    public static class ScrDistregComparator implements Comparator {
        public boolean equals(Object object) {
            return false;
        }

        public int compare(Object o1, Object o2) {
            return (new Integer(((ScrDistreg) o1).getIdFdr())).compareTo(new Integer(((ScrDistreg) o2).getIdFdr()));
        }
    }

    public static class IntegerComparator implements Comparator {
        public boolean equals(Object object) {
            return false;
        }

        public int compare(Object o1, Object o2) {
             return ((Integer) o1).compareTo((Integer)o2);
        }
    }

    public static class StringComparator implements Comparator {
        public boolean equals(Object object) {
            return false;
        }

        public int compare(Object o1, Object o2) {
             return ((String) o1).compareTo((String)o2);
        }
    }
}