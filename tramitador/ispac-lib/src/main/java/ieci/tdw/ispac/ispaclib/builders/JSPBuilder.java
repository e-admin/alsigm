package ieci.tdw.ispac.ispaclib.builders;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSPBuilder {
	
	public static final String RETORNO = "\r\n";
	public static final String TAG_LIB_OPEN = "<%@ taglib ";
	public static final String TAG_LIB_CLOSE = "%>" + RETORNO;
	public static final String BEAN_WRITE_TAG_OPEN = "<bean:write ";
	public static final String HTML_FORM_TAG_OPEN = "<html:form action=\"storeEntity.do\">";
	public static final String HTML_FORM_TAG_CLOSE = "</html:form>" + RETORNO;
	public static final String HTML_HIDDEN_TAG_OPEN = "<html:hidden ";
	public static final String ISPAC_REWRITE_TAG_OPEN = "<ispac:rewrite ";
	public static final String ISPAC_PARAMETER_TAG_OPEN = "<ispac:parameter ";
	public static final String ISPAC_PARAMETER_MULTIVALUE_TAG_OPEN = "<ispac:parameterMultivalue ";
	public static final String ISPAC_CALENDAR_CONFIG_TAG_OPEN = "<ispac:calendar-config ";
	public static final String ISPAC_HTML_TEXT_TAG_OPEN = "<ispac:htmlText ";
	public static final String ISPAC_HTML_TEXT_TAG_CLOSE = "</ispac:htmlText>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_TAG_OPEN = "<ispac:htmlTextMultivalue ";
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_TAG_CLOSE = "</ispac:htmlTextMultivalue>" + RETORNO;
	public static final String ISPAC_HTML_TEXTAREA_TAG_OPEN = "<ispac:htmlTextarea ";
	public static final String ISPAC_HTML_TEXTAREA_TAG_CLOSE = "</ispac:htmlTextarea>" + RETORNO;
	public static final String ISPAC_HTML_TEXTAREA_MULTIVALUE_TAG_OPEN = "<ispac:htmlTextareaMultivalue ";
	public static final String ISPAC_HTML_TEXTAREA_MULTIVALUE_TAG_CLOSE = "</ispac:htmlTextareaMultivalue>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_CALENDAR_TAG_OPEN = "<ispac:htmlTextCalendar ";
	public static final String ISPAC_HTML_TEXT_CALENDAR_TAG_CLOSE = "</ispac:htmlTextCalendar>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_CALENDAR_TAG_OPEN = "<ispac:htmlTextMultivalueCalendar ";
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_CALENDAR_TAG_CLOSE = "</ispac:htmlTextMultivalueCalendar>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_OPEN = "<ispac:htmlTextImageFrame ";
	public static final String ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_CLOSE = "</ispac:htmlTextImageFrame>" + RETORNO;
	public static final String ISPAC_HTML_TEXTAREA_IMAGE_FRAME_TAG_OPEN = "<ispac:htmlTextareaImageFrame ";
	public static final String ISPAC_HTML_TEXTAREA_IMAGE_FRAME_TAG_CLOSE = "</ispac:htmlTextareaImageFrame>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_IMAGE_FRAME_TAG_OPEN = "<ispac:htmlTextMultivalueImageFrame ";
	public static final String ISPAC_HTML_TEXT_MULTIVALUE_IMAGE_FRAME_TAG_CLOSE = "</ispac:htmlTextMultivalueImageFrame>" + RETORNO;
	public static final String ISPAC_HTML_TEXTAREA_MULTIVALUE_IMAGE_FRAME_TAG_OPEN = "<ispac:htmlTextareaMultivalueImageFrame ";
	public static final String ISPAC_HTML_TEXTAREA_MULTIVALUE_IMAGE_FRAME_TAG_CLOSE = "</ispac:htmlTextareaMultivalueImageFrame>" + RETORNO;
	public static final String ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_ID = "SEARCH_";
	public static final String ISPAC_TAG_TABS_OPEN="<ispac:tabs>";
	public static final String ISPAC_TAG_TABS_CLOSE="</ispac:tabs>";
	public static final String ISPAC_TAG_TAB_OPEN="<ispac:tab ";
	public static final String ISPAC_TAG_TAB_CLOSE="</ispac:tab>";
	public static final String NOBR_TAG_OPEN = "<nobr>" + RETORNO;
	public static final String NOBR_TAG_CLOSE = "</nobr>" + RETORNO;
	public static final String ATTRIBUTE_URI = "uri";
	public static final String ATTRIBUTE_PREFIX = "prefix";
	public static final String ATTRIBUTE_PROPERTY = "property";
	public static final String ATTRIBUTE_PROPERTY_DESTINATION = "propertyDestination";	
	public static final String ATTRIBUTE_READONLY = "readonly";
	public static final String ATTRIBUTE_READONLY_TAG = "readonlyTag";
	public static final String ATTRIBUTE_PROPERTY_READONLY ="propertyReadonly";
	public static final String ATTRIBUTE_STYLE_CLASS = "styleClass";
	public static final String ATTRIBUTE_STYLE_CLASS_READONLY = "styleClassReadonly";
	public static final String ATTRIBUTE_STYLE_CLASS_DELETE_LINK = "styleClassDeleteLink";
	public static final String ATTRIBUTE_SIZE = "size";
	public static final String ATTRIBUTE_MAXLENGTH = "maxlength";
	public static final String ATTRIBUTE_ROWS = "rows";
	public static final String ATTRIBUTE_COLS = "cols";
	public static final String ATTRIBUTE_FORMAT = "format";
	public static final String ATTRIBUTE_ENABLE_PAST = "enablePast";
	public static final String ATTRIBUTE_ID = "id";
	public static final String ATTRIBUTE_HREF = "href";
	public static final String ATTRIBUTE_IMAGE = "image";
	public static final String ATTRIBUTE_IMAGE_DELETE = "imageDelete";
	public static final String ATTRIBUTE_IMG_DIR = "imgDir";
	public static final String ATTRIBUTE_SCRIPT_FILE = "scriptFile";
	public static final String ATTRIBUTE_TARGET = "target";
	public static final String ATTRIBUTE_ACTION = "action";
	public static final String ATTRIBUTE_TITLE_KEY_LINK = "titleKeyLink";
	public static final String ATTRIBUTE_TITLE_KEY_IMAGE_DELETE = "titleKeyImageDelete";
	public static final String ATTRIBUTE_CONFIRM_DELETE_KEY = "confirmDeleteKey";
	public static final String ATTRIBUTE_SHOW_FRAME = "showFrame";
	public static final String ATTRIBUTE_SHOW_DELETE = "showDelete";
	public static final String ATTRIBUTE_WIDTH = "width";
	public static final String ATTRIBUTE_HEIGHT = "height";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_TABLE_VALIDATION_TYPE = "tableValidationType";
	public static final String ATTRIBUTE_IMAGE_TAB_INDEX = "imageTabIndex";

	public static final String ATTIBUTE_DIV_WIDTH= "divWidth";
	public static final String ATTIBUTE_SET_METHOD= "setMethod";
	public static final String VALUE_ATTIBUTE_SET_METHOD = "id";
	public static final String OPEN_EXPRESSION_VALUE = "='<%= ";
	public static final String CLOSE_EXPRESSION_VALUE = " %>' ";
	public static final String OPEN_ATTRIBUTE_VALUE = "=\"";
	public static final String CLOSE_ATTRIBUTE_VALUE = "\" ";
	public static final String CLOSE_TAG = "/>";
	public static final String CLOSE_TAG_OPEN = ">" + RETORNO;
	public static final String VALUE_PROPERTY_OPEN = "property(";
	public static final String VALUE_PROPERTY_MULTIVALUE_OPEN = "propertyMultivalue(";
	public static final String VALUE_ENTITY_APP_LABEL_OPEN = "entityApp.label(";
	public static final String VALUE_CLOSE = ")";
	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";
	public static final String VALUE_IMGCALENDAR = "imgcalendar";
	public static final String VALUE_JSCALENDAR = "jscalendar";
	public static final String VALUE_BUTTONCALENDAR = "buttoncalendar";
	public static final String VALUE_WORKFRAME = "workframe";
	public static final String VALUE_DEFAULT_FORM = "defaultForm";
	public static final String ACTION_SELECT_SUBSTITUTE_ENTITY = "selectSubstitute.do?entity=";
	public static final String ACTION_SELECT_VALUE_ENTITY = "selectValue.do?entity=";
	public static final String URI_STRUTS_TILES_TLD = "/WEB-INF/struts-tiles.tld";
	public static final String PREFIX_TAGS_STRUTS_TILES = "tiles";
	public static final String URI_STRUTS_BEAN_TLD = "/WEB-INF/struts-bean.tld";
	public static final String PREFIX_TAGS_STRUTS_BEAN = "bean";
	public static final String URI_STRUTS_HTML_TLD = "/WEB-INF/struts-html.tld";
	public static final String PREFIX_TAGS_STRUTS_HTML = "html";
	public static final String URI_STRUTS_LOGIC_TLD = "/WEB-INF/struts-logic.tld";
	public static final String PREFIX_TAGS_STRUTS_LOGIC = "logic";
	public static final String URI_ISPAC_UTIL_TLD = "/WEB-INF/ispac-util.tld";
	public static final String PREFIX_TAGS_ISPAC_UTIL = "ispac";
	public static final String URI_STRUTS_C_TLD = "/WEB-INF/c.tld";
	public static final String PREFIX_TAGS_STRUTS_C = "c1";
	
	public static final String CSS="estilos.css";
	public static final String JS="utils.js";
	
	private static final int DEFAULT_DIV_WITDH = 700; 
	private static final int DEFAULT_CALENDAR_DIV_WIDTH = 150;

	private static final int DEFAULT_TEXTAREA_ROWS = 2;
	private static final int DEFAULT_TEXTAREA_COLS = 80;
	
	public static final String TABLE_VALIDATION_SUBSTITUTE = "substitute";
	
	/*
	public static final String AUX_DATA_TABS_PROPERTIES_BY_TAB_DEFAULT_VALUE = "20";
	public static final String AUX_DATA_TABS_ELEMENTS_ROW_DEFAULT_VALUE = "2";
	*/
	
	public static String generateIspacHtmlTextTag(String property,
			  int size,
			  int maxlength) throws ISPACException {
		return generateIspacHtmlTextTag(property, size, maxlength, null);
	}
	
	/**
	 * 
	 * @param property
	 * @param size
	 * @param maxlength
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacHtmlTextTag(String property,
												  int size,
												  int maxlength,
												  String format) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(ISPAC_HTML_TEXT_TAG_OPEN);
		generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_SIZE, size);
		if (maxlength > 0) {
			generateAttribute(jspCode, ATTRIBUTE_MAXLENGTH, maxlength);
		}
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXT_TAG_CLOSE);
		if(StringUtils.isNotBlank(format)) {
			jspCode.append(format);
		}
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param property
	 * @param size
	 * @param maxlength
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacHtmlTextMultivalueTag(String property,
												  int size,
												  int maxlength) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(ISPAC_HTML_TEXT_MULTIVALUE_TAG_OPEN);
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_SIZE, size);
		generateAttribute(jspCode, ATTIBUTE_DIV_WIDTH, DEFAULT_DIV_WITDH);
		if (maxlength > 0) {
			generateAttribute(jspCode, ATTRIBUTE_MAXLENGTH, maxlength);
		}
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXT_MULTIVALUE_TAG_CLOSE);
		
		return jspCode.toString();
	}
	
	
	
	
	/**
	 * 
	 * @param property
	 * @param rows
	 * @param cols
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacHtmlTextareaTag(String property,
													  int rows,
													  int cols) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(ISPAC_HTML_TEXTAREA_TAG_OPEN);
		generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_ROWS, rows);
		generateAttribute(jspCode, ATTRIBUTE_COLS, cols);
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXTAREA_TAG_CLOSE);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param property
	 * @param rows
	 * @param cols
	 * @return
	 * @throws ISPACException
	 */
	private static String generateIspacHtmlTextareaMultivalueTag(String property,
			  int rows,
			  int cols) throws ISPACException {
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(ISPAC_HTML_TEXTAREA_MULTIVALUE_TAG_OPEN);
		
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_ROWS, rows);
		generateAttribute(jspCode, ATTRIBUTE_COLS, cols);
		generateAttribute(jspCode, ATTIBUTE_DIV_WIDTH, DEFAULT_DIV_WITDH);
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXTAREA_MULTIVALUE_TAG_CLOSE);
		
		return jspCode.toString();
	}	
	
	
	
	/**
	 * 
	 * @param property
	 * @param size
	 * @param maxlength
	 * @param format
	 * @param enablePast
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacHtmlTextCalendar(String property,
													   int size,
													   int maxlength,
													   String format,
													   boolean enablePast) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(NOBR_TAG_OPEN);
		
		jspCode.append(ISPAC_HTML_TEXT_CALENDAR_TAG_OPEN);
		generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_SIZE, size);
		generateAttribute(jspCode, ATTRIBUTE_MAXLENGTH, maxlength);
		generateAttributeWithExpression(jspCode, ATTRIBUTE_IMAGE, VALUE_BUTTONCALENDAR);
		generateAttribute(jspCode, ATTRIBUTE_FORMAT, format);
		String strEnablePast = VALUE_FALSE;
		if (enablePast) {
			strEnablePast = VALUE_TRUE;
		}
		generateAttribute(jspCode, ATTRIBUTE_ENABLE_PAST, strEnablePast);		
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXT_CALENDAR_TAG_CLOSE);
		
		jspCode.append(NOBR_TAG_CLOSE);
		
		return jspCode.toString();
	}
	
	
	public static String generateIspacHtmlTextMultivalueCalendar(String property,
																   int size,
																   int maxlength,
																   String format,
																   boolean enablePast) throws ISPACException {
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer(NOBR_TAG_OPEN);
		
		jspCode.append(ISPAC_HTML_TEXT_MULTIVALUE_CALENDAR_TAG_OPEN);
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_SIZE, size);
		generateAttribute(jspCode, ATTIBUTE_DIV_WIDTH, DEFAULT_CALENDAR_DIV_WIDTH);
		generateAttribute(jspCode, ATTRIBUTE_MAXLENGTH, maxlength);
		generateAttributeWithExpression(jspCode, ATTRIBUTE_IMAGE, VALUE_BUTTONCALENDAR);
		generateAttribute(jspCode, ATTRIBUTE_FORMAT, format);
		String strEnablePast = VALUE_FALSE;
		if (enablePast) {
			strEnablePast = VALUE_TRUE;
		}
		generateAttribute(jspCode, ATTRIBUTE_ENABLE_PAST, strEnablePast);		
		jspCode.append(CLOSE_TAG_OPEN);
		jspCode.append(ISPAC_HTML_TEXT_MULTIVALUE_CALENDAR_TAG_CLOSE);
		
		jspCode.append(NOBR_TAG_CLOSE);
		
		return jspCode.toString();	
	}	
	
	
	public static String generateIspacHtmlTextMultivalueTimestamp(String property) throws ISPACException {
		return generateIspacHtmlTextMultivalueTag(property, 25, 19);
	}

	public static String generateIspacHtmlTextTimestamp(String property) throws ISPACException {
		return generateIspacHtmlTextTag(property, 25, 19 , "("+generateBeanMessage("format.timestamp")+")");
	}
	
	/**
	 * 
	 * @param id
	 * @param href
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacRewrite(String id,
											  String href) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(ISPAC_REWRITE_TAG_OPEN);
		generateAttribute(jspCode, ATTRIBUTE_ID, id);
		generateAttribute(jspCode, ATTRIBUTE_HREF, href);
		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacCalendarConfig() throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer();
		jspCode.append(generateIspacRewrite(VALUE_IMGCALENDAR, jspBuilderConfiguration.get(JSPBuilderConfiguration.CALENDAR_HREF_IMG)));
		jspCode.append(generateIspacRewrite(VALUE_JSCALENDAR, jspBuilderConfiguration.get(JSPBuilderConfiguration.CALENDAR_HREF_JS)));
		jspCode.append(generateIspacRewrite(VALUE_BUTTONCALENDAR, jspBuilderConfiguration.get(JSPBuilderConfiguration.CALENDAR_HREF_BUTTON)));
		jspCode.append(ISPAC_CALENDAR_CONFIG_TAG_OPEN);
		generateAttributeWithExpression(jspCode, ATTRIBUTE_IMG_DIR, VALUE_IMGCALENDAR);
		generateAttributeWithExpression(jspCode, ATTRIBUTE_SCRIPT_FILE, VALUE_JSCALENDAR);
		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param property
	 * @return
	 * @throws ISPACException
	 */
	public static String generateHtmlHidden(String property) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(HTML_HIDDEN_TAG_OPEN);
		generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, property);
		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param property
	 * @return
	 * @throws ISPACException
	 */
	public static String generateHtmlMultivalueHidden(String property) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(HTML_HIDDEN_TAG_OPEN);
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, property);
		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param name
	 * @param id
	 * @param property
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacParameter(String name,
												String id,
												String property) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(ISPAC_PARAMETER_TAG_OPEN);
		generateAttribute(jspCode, ATTRIBUTE_NAME, name);
		generateAttributeWithProperty(jspCode, ATTRIBUTE_ID, id);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY, property);
		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}

	/**
	 * 
	 * @param name
	 * @param id
	 * @param property
	 * @param setMethod 
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacParameterMultivalue(String name,
												String id,
												String property,
												String propertyDestination, boolean setMethod) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(ISPAC_PARAMETER_MULTIVALUE_TAG_OPEN);
		generateAttribute(jspCode, ATTRIBUTE_NAME, name);
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_ID, id);
		if (setMethod)
			generateAttribute(jspCode, ATTIBUTE_SET_METHOD, VALUE_ATTIBUTE_SET_METHOD);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY, property);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_DESTINATION, propertyDestination);

		jspCode.append(CLOSE_TAG)
			   .append(RETORNO);
		
		return jspCode.toString();
	}	
	
	/**
	 * 
	 * @param propertyValue
	 * @param propertySubstitute
	 * @param idEntity
	 * @param entityType
	 * @param size
	 * @return
	 * @throws ISPACException
	 */
	public static String generateIspacHtmlTextImageFrame(String propertyValue,
														 String propertySubstitute,
														 String entity,
														 EntityType entityType,
														 int size,
														 boolean textarea,
														 int rows,
														 int cols) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer();
		jspCode.append(NOBR_TAG_OPEN);
		
		// Si la tabla es de sustituto el valor se guarda en un campo oculto
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
			jspCode.append(generateHtmlHidden(propertyValue));
		}
		
		if (textarea) {
			// Tag ispac:htmlTextareaImageFrame
			jspCode.append(ISPAC_HTML_TEXTAREA_IMAGE_FRAME_TAG_OPEN);			
		}
		else {
			// Tag ispac:htmlTextImageFrame
			jspCode.append(ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_OPEN);
		}
		
		// Si la tabla es de sustituto el sustituto se muestra en el campo de sólo lectura
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
			generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, propertySubstitute);
		}
		// Si la tabla es simple el valor se muestra en el campo de sólo lectura
		else {
			generateAttributeWithProperty(jspCode, ATTRIBUTE_PROPERTY, propertyValue);
		}
		
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_READONLY_TAG, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		
		if (textarea) {
			// Rows y cols para el textarea
			generateAttribute(jspCode, ATTRIBUTE_ROWS, rows);
			generateAttribute(jspCode, ATTRIBUTE_COLS, cols);
		}
		else {
			// Size para el input
			generateAttribute(jspCode, ATTRIBUTE_SIZE, size);

			// Índice de tabulación
			generateAttribute(jspCode, ATTRIBUTE_IMAGE_TAB_INDEX, VALUE_TRUE);
		}
		
		String idIspacHtmlTextImageFrame = ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_ID + propertyValue.replace(':', '_');
		generateAttribute(jspCode, ATTRIBUTE_ID, idIspacHtmlTextImageFrame);
		generateAttribute(jspCode, ATTRIBUTE_TARGET, VALUE_WORKFRAME);
		// Acción para seleccionar un sustituto o un valor
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
			generateAttribute(jspCode, ATTRIBUTE_ACTION, ACTION_SELECT_SUBSTITUTE_ENTITY + entity);
		}
		else {
			generateAttribute(jspCode, ATTRIBUTE_ACTION, ACTION_SELECT_VALUE_ENTITY + entity);
		}
		generateAttribute(jspCode, ATTRIBUTE_IMAGE, jspBuilderConfiguration.get(JSPBuilderConfiguration.IMAGE_FRAME_HREF_IMAGE));
		generateAttribute(jspCode, ATTRIBUTE_TITLE_KEY_LINK, jspBuilderConfiguration.get(JSPBuilderConfiguration.TITLE_KEY_LINK));
		generateAttribute(jspCode, ATTRIBUTE_IMAGE_DELETE, jspBuilderConfiguration.get(JSPBuilderConfiguration.IMAGE_FRAME_DELETE_IMAGE));
		generateAttribute(jspCode, ATTRIBUTE_TITLE_KEY_IMAGE_DELETE, jspBuilderConfiguration.get(JSPBuilderConfiguration.TITLE_KEY_IMAGE_DELETE));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_DELETE_LINK, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_DELETE_LINK));
		generateAttribute(jspCode, ATTRIBUTE_CONFIRM_DELETE_KEY, jspBuilderConfiguration.get(JSPBuilderConfiguration.CONFIRM_DELETE_KEY));
		generateAttribute(jspCode, ATTRIBUTE_SHOW_DELETE, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_SHOW_FRAME, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_WIDTH, jspBuilderConfiguration.get(JSPBuilderConfiguration.FRAME_WIDTH));
		generateAttribute(jspCode, ATTRIBUTE_HEIGHT, jspBuilderConfiguration.get(JSPBuilderConfiguration.FRAME_HEIGHT));
		jspCode.append(CLOSE_TAG_OPEN);
		
		// Generación de parámetros
		jspCode.append(generateIspacParameter(idIspacHtmlTextImageFrame, propertyValue, jspBuilderConfiguration.get(JSPBuilderConfiguration.PARAMETER_VALUE)));
		// Si la tabla es de sustituto el sustituto se pasa como parámetro
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
		jspCode.append(generateIspacParameter(idIspacHtmlTextImageFrame, propertySubstitute, jspBuilderConfiguration.get(JSPBuilderConfiguration.PARAMETER_SUBSTITUTE)));
		}
		
		jspCode.append(ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_CLOSE);
		jspCode.append(NOBR_TAG_CLOSE);
		
		return jspCode.toString();
	}
	
	private static String generateIspacHtmlTextMultivalueImageFrame(String propertyValue,
																	 String propertySubstitute,
																	 String entity,
																	 EntityType entityType,
																	 int size,
																	 boolean textarea,
																	 int rows,
																	 int cols) throws ISPACException {

		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer();
		jspCode.append(NOBR_TAG_OPEN);
		
		// Si la tabla es de sustituto el valor se guarda en un campo oculto
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
//No se genera campo solo lectura, se deja para que el tag lo interprete y genera los n campos en solo lectura correspondientes			
//jspCode.append(generateHtmlMultivalueHidden(propertyValue));
		
		}
		
		if (textarea) {
			// Tag ispac:htmlTextareaImageFrame
    		jspCode.append(ISPAC_HTML_TEXTAREA_MULTIVALUE_IMAGE_FRAME_TAG_OPEN);
		}
		else {
			// Tag ispac:htmlTextImageFrame
			jspCode.append(ISPAC_HTML_TEXT_MULTIVALUE_IMAGE_FRAME_TAG_OPEN);
		}
		
		// Si la tabla es de sustituto el sustituto se muestra en el campo de sólo lectura
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
			generateAttribute(jspCode, ATTRIBUTE_TABLE_VALIDATION_TYPE, TABLE_VALIDATION_SUBSTITUTE);
			generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, propertySubstitute);
		}
		// Si la tabla es simple el valor se muestra en el campo de sólo lectura
		else {
			generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY, propertyValue);
		}
		generateAttributeWithPropertyMultivalue(jspCode, ATTRIBUTE_PROPERTY_DESTINATION, propertyValue);		

		
		generateAttribute(jspCode, ATTRIBUTE_READONLY, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_READONLY_TAG, VALUE_FALSE);
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.PROPERTY_READONLY));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_READONLY, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY));
		
		if (textarea) {
			// Rows y cols para el textarea
			generateAttribute(jspCode, ATTRIBUTE_ROWS, rows);
			generateAttribute(jspCode, ATTRIBUTE_COLS, cols);
		}
		else {
			// Size para el input
			generateAttribute(jspCode, ATTRIBUTE_SIZE, size);

			// Índice de tabulación
			generateAttribute(jspCode, ATTRIBUTE_IMAGE_TAB_INDEX, VALUE_TRUE);
		}
		
		String idIspacHtmlTextImageFrame = ISPAC_HTML_TEXT_IMAGE_FRAME_TAG_ID + propertyValue.replace(':', '_');
		generateAttribute(jspCode, ATTRIBUTE_ID, idIspacHtmlTextImageFrame);
		generateAttribute(jspCode, ATTRIBUTE_TARGET, VALUE_WORKFRAME);
		// Acción para seleccionar un sustituto o un valor
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
			
			generateAttribute(jspCode, ATTRIBUTE_ACTION, ACTION_SELECT_SUBSTITUTE_ENTITY + entity);
		}
		else {
			generateAttribute(jspCode, ATTRIBUTE_ACTION, ACTION_SELECT_VALUE_ENTITY + entity);
		}
		generateAttribute(jspCode, ATTRIBUTE_IMAGE, jspBuilderConfiguration.get(JSPBuilderConfiguration.IMAGE_FRAME_HREF_IMAGE));
		generateAttribute(jspCode, ATTRIBUTE_TITLE_KEY_LINK, jspBuilderConfiguration.get(JSPBuilderConfiguration.TITLE_KEY_LINK));
		generateAttribute(jspCode, ATTRIBUTE_IMAGE_DELETE, jspBuilderConfiguration.get(JSPBuilderConfiguration.IMAGE_FRAME_DELETE_IMAGE));
		generateAttribute(jspCode, ATTRIBUTE_TITLE_KEY_IMAGE_DELETE, jspBuilderConfiguration.get(JSPBuilderConfiguration.TITLE_KEY_IMAGE_DELETE));
		generateAttribute(jspCode, ATTRIBUTE_STYLE_CLASS_DELETE_LINK, jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_DELETE_LINK));
		generateAttribute(jspCode, ATTRIBUTE_CONFIRM_DELETE_KEY, jspBuilderConfiguration.get(JSPBuilderConfiguration.CONFIRM_DELETE_KEY));
		generateAttribute(jspCode, ATTRIBUTE_SHOW_DELETE, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_SHOW_FRAME, VALUE_TRUE);
		generateAttribute(jspCode, ATTRIBUTE_WIDTH, jspBuilderConfiguration.get(JSPBuilderConfiguration.FRAME_WIDTH));
		generateAttribute(jspCode, ATTRIBUTE_HEIGHT, jspBuilderConfiguration.get(JSPBuilderConfiguration.FRAME_HEIGHT));
		generateAttribute(jspCode, ATTIBUTE_DIV_WIDTH, DEFAULT_DIV_WITDH);

		jspCode.append(CLOSE_TAG_OPEN);
		
		// Generación de parámetros
		
		String attribute = VALUE_PROPERTY_MULTIVALUE_OPEN + propertyValue + VALUE_CLOSE;

		jspCode.append(generateIspacParameterMultivalue(idIspacHtmlTextImageFrame, propertyValue, jspBuilderConfiguration.get(JSPBuilderConfiguration.PARAMETER_VALUE), attribute, true));
		// Si la tabla es de sustituto el sustituto se pasa como parámetro
		if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
			(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {


			jspCode.append(generateIspacParameterMultivalue(idIspacHtmlTextImageFrame, propertySubstitute, jspBuilderConfiguration.get(JSPBuilderConfiguration.PARAMETER_SUBSTITUTE), attribute, true));
		}
		
		jspCode.append(ISPAC_HTML_TEXT_MULTIVALUE_IMAGE_FRAME_TAG_CLOSE);
		jspCode.append(NOBR_TAG_CLOSE);
		
		return jspCode.toString();
	}		
	
	
	/**
	 * 
	 * @param uri
	 * @param prefix
	 * @return
	 * @throws ISPACException
	 */
	public static String generateTagLib(String uri, String prefix) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(TAG_LIB_OPEN);
		generateAttribute(jspCode, ATTRIBUTE_URI, uri);
		generateAttribute(jspCode, ATTRIBUTE_PREFIX, prefix);
		jspCode.append(TAG_LIB_CLOSE);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @return
	 * @throws ISPACException
	 */
	public static String generateTagLibs() throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer();
		jspCode.append(generateTagLib(URI_STRUTS_TILES_TLD, PREFIX_TAGS_STRUTS_TILES));
		jspCode.append(generateTagLib(URI_STRUTS_BEAN_TLD, PREFIX_TAGS_STRUTS_BEAN));
		jspCode.append(generateTagLib(URI_STRUTS_HTML_TLD, PREFIX_TAGS_STRUTS_HTML));
		jspCode.append(generateTagLib(URI_STRUTS_LOGIC_TLD, PREFIX_TAGS_STRUTS_LOGIC));
		jspCode.append(generateTagLib(URI_ISPAC_UTIL_TLD, PREFIX_TAGS_ISPAC_UTIL));
		jspCode.append(generateTagLib(URI_STRUTS_C_TLD, PREFIX_TAGS_STRUTS_C));
		return jspCode.toString();
	}
	

	/**
	 * 
	 * @param property
	 * @return
	 * @throws ISPACException
	 */
	public static String generateLabel(String property) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer(BEAN_WRITE_TAG_OPEN);
		generateAttribute(jspCode, ATTRIBUTE_NAME, VALUE_DEFAULT_FORM);
		String value = VALUE_ENTITY_APP_LABEL_OPEN
		 			 + property
		 			 + VALUE_CLOSE;
		generateAttribute(jspCode, ATTRIBUTE_PROPERTY, value);
		jspCode.append(CLOSE_TAG);
		
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param entities
	 * @return
	 * @throws ISPACException
	 
	private static String generateTabs(List entities) throws ISPACException {
		
		//JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer();
			
		jspCode.append("\r\n<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->\r\n")
			   .append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n")
			   .append("<tr>\r\n");
		
		Iterator it = entities.iterator();
		String entity = (String) it.next();
		entity = entity + ":" + entity;
		int index = 1;
		
		if (entities.size() == 1) {
			
			jspCode.append("<td class=\"select\" id=\"tdlink1\" align=\"center\">\r\n")
				   .append(NOBR_TAG_OPEN)
				   .append(generateLabel(entity))
				   .append(RETORNO)
				   .append(NOBR_TAG_CLOSE)
				   .append("</td>\r\n");
		}
		else {
			jspCode.append("<td class=\"select\" id=\"tdlink1\" align=\"center\" onclick=\"showTab(1)\">\r\n")
			   	   .append(NOBR_TAG_OPEN)
			   	   .append(generateLabel(entity))
			   	   .append(RETORNO)
			   	   .append(NOBR_TAG_CLOSE)
			   	   .append("</td>\r\n");
			
			while (it.hasNext()) {
				
				entity = (String) it.next();
				entity = entity + ":" + entity;
				index = index + 1;
				String strIndex = String.valueOf(index);
				
				jspCode.append("<td width=\"5px\"><img height=\"1\" width=\"5px\" src='<ispac:rewrite href=\"img/pixel.gif\"/>'/></td>\r\n")
					   .append("<td class=\"unselect\" id=\"tdlink")
					   .append(strIndex)
					   .append("\" align=\"center\" onclick=\"showTab(")
					   .append(strIndex)
					   .append(")\">\r\n")
				   	   .append(NOBR_TAG_OPEN)
				   	   .append(generateLabel(entity))
				   	   .append(RETORNO)
				   	   .append(NOBR_TAG_CLOSE)
				   	   .append("</td>\r\n");
			}
		}
		
		
		// Pestañas para los datos auxiliares
		//String propertiesByTag = jspBuilderConfiguration.get(JSPBuilderConfiguration.AUX_DATA_TABS_PROPERTIES_BY_TAB);
		//if (StringUtils.isEmpty(propertiesByTag)) {
		//	propertiesByTag = AUX_DATA_TABS_PROPERTIES_BY_TAB_DEFAULT_VALUE;
		//}
		//jspCode.append("<!-- ANCLAS PARA LOS BLOQUES DE DATOS AUXILIARES -->\r\n")
		//	   .append("<tiles:insert template=\"/forms/common/auxDataTabs.jsp\">\r\n")
		//	   .append("<tiles:put name=\"propertiesByTab\" direct=\"true\">")
		//	   .append(propertiesByTag)
		//	   .append("</tiles:put>\r\n")
		//	   .append("<tiles:put name=\"iniTab\" direct=\"true\">100</tiles:put>\r\n")
		//	   .append("</tiles:insert>\r\n");
		
		
		jspCode.append("</tr>\r\n")
		       .append("</table>\r\n\r\n");
		
		return jspCode.toString();
	}
*/
	

	
	/**
	 * 
	 * @param dataBlocks
	 * @return
	 * @throws ISPACException
	 
	private static String generateDataBlocks(List dataBlocks) throws ISPACException {
		
		//JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer jspCode = new StringBuffer();
					
		if ((dataBlocks != null) &&
			(!dataBlocks.isEmpty())) {
			
			jspCode.append("<table width=\"100%\" border=\"0\" class=\"formtable\" cellspacing=\"0\" cellpadding=\"0\">\r\n")
				   .append("<tr>\r\n")
				   .append("<td><img height=\"8\" src='<ispac:rewrite href=\"img/pixel.gif\"/>'/></td>\r\n")
				   .append("</tr>\r\n")
				   .append("<tr>\r\n")
				   .append("<td>\r\n");
		
			Iterator it = dataBlocks.iterator();
			int index = 1;
			
			while (it.hasNext()) {
				
				String dataBlock = (String) it.next();
				String strIndex = String.valueOf(index);
				
				jspCode.append("\r\n<!-- BLOQUE")
					   .append(index)
					   .append(" DE CAMPOS -->\r\n")
					   .append("<div style=\"display:");
				
				if (index == 1) {
					jspCode.append("block");
				}
				else {
					jspCode.append("none");
				}
				jspCode.append("\" id=\"block")
					   .append(strIndex)
					   .append("\">\r\n")
					   .append(dataBlock)
					   .append("</div>\r\n");
				
				index = index + 1;
			}
			
			
			// Divs para los datos auxiliares
			//String elementsRow = jspBuilderConfiguration.get(JSPBuilderConfiguration.AUX_DATA_TABS_ELEMENTS_ROW);
			//if (StringUtils.isEmpty(elementsRow)) {
				//elementsRow = AUX_DATA_TABS_ELEMENTS_ROW_DEFAULT_VALUE;
			//}
			//jspCode.append("\r\n<!-- BLOQUES DE DATOS AUXILIARES -->\r\n")
				 //  .append("<tiles:insert template=\"/forms/common/auxData.jsp\">\r\n")
				 //.append("<tiles:put name=\"elementsRow\" direct=\"true\">")
				 //  .append(elementsRow)
				 //  .append("</tiles:put>\r\n")
				 //  .append("<tiles:put name=\"iniBlock\" direct=\"true\">100</tiles:put>\r\n")
				  // .append("</tiles:insert>\r\n");
		


			jspCode.append("</td>\r\n")
				   .append("</tr>\r\n")
				   .append("<tr>\r\n")
				   .append("<td height=\"15\"><img src='<ispac:rewrite href=\"img/pixel.gif\"/>'/></td>\r\n")
				   .append("</tr>\r\n")
				   .append("</table>\r\n");
		}
		
		return jspCode.toString();
	}
	*/
	
	
	/**
	 * 
	 * @param js
	 * @param css
	 * @return
	 */
	public static String generateImports(){

		StringBuffer jspCode=new StringBuffer("<script type=\"text/javascript\" src='<ispac:rewrite href=\"../scripts/"+JS+"\"/>'></script>");
		jspCode.append("<link rel=\"stylesheet\" href='<ispac:rewrite href=\"css/"+CSS+"\"/>'/>");
		return jspCode.toString();
	}
	
	/**
	 * 
	 * @param entities
	 * @param dataBlocks
	 * @return
	 * @throws ISPACException
	 */
	public static String generateTabsDataBlocks(List entities, List dataBlocks,boolean generateTabDocumentos ) throws ISPACException {
		
		StringBuffer jspCode = new StringBuffer();
		
		if ((entities != null) &&
			(!entities.isEmpty())) {
			
			jspCode.append(generateTagLibs())
				   //.append(generateImports())
				   .append(generateTagTabs(entities, dataBlocks , generateTabDocumentos));
				  
		}
		
		return jspCode.toString();
	}
	/**
	 * 
	 * @param entities
	 * @param dataBlocks
	 * @return
	 * @throws ISPACException
	 */
	public static String generateTagTabs( List entities, List dataBlocks, boolean generateTabDocumentos) throws ISPACException{
		
		StringBuffer jspCode = new StringBuffer();
		StringBuffer jspLabels= new StringBuffer();
		jspCode=new StringBuffer(ISPAC_TAG_TABS_OPEN);

		Iterator it = entities.iterator();
		int i=0;
		while(it.hasNext()){
			String entity = (String) it.next();
			entity = entity + ":" + entity;
			jspLabels.append("<"+PREFIX_TAGS_STRUTS_C+":set var=\"aux"+i+"\">"+generateLabel(entity)+"</"+PREFIX_TAGS_STRUTS_C+":set>");
			jspLabels.append("<jsp:useBean id=\"aux"+i+"\" type=\"java.lang.String\"/>");
			jspCode.append(ISPAC_TAG_TAB_OPEN + " title='<%=aux"+i+"%>'>");
			jspCode.append(dataBlocks.get(i));
			jspCode.append(ISPAC_TAG_TAB_CLOSE);
			i++;
		}
		if(generateTabDocumentos){
			jspCode.append("<"+PREFIX_TAGS_STRUTS_C+":if test=\"${defaultForm.key != ENTITY_NULLREGKEYID}\">");
			jspCode.append(ISPAC_TAG_TAB_OPEN + " titleKey=\"entity.documents.title\">");
			jspCode.append(dataBlocks.get(i));
			jspCode.append(ISPAC_TAG_TAB_CLOSE);
			jspCode.append("</"+PREFIX_TAGS_STRUTS_C+":if>");
		}
		
		jspCode.append(ISPAC_TAG_TABS_CLOSE);
		return jspLabels.toString()+" "+jspCode.toString();
	}
	
	/**
	 * 
	 * @param cnt
	 * @param entityDef
	 * @param entityName
	 * @return
	 * @throws ISPACException
	 */
	public static String generateDataBlock(DbCnt cnt, EntityDef entityDef, String entityName) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
	
		StringBuffer jspCode = new StringBuffer();
		int top = 10;
		
		if (entityDef != null) {
			
			List fields = entityDef.getFields();
			
			if ((fields != null) &&
				(!fields.isEmpty())) {
				
				Map validations = entityDef.validationsToMapByIdField();
				
				//jspCode.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\">\r\n");

				if (StringUtils.equalsIgnoreCase(jspBuilderConfiguration.get(JSPBuilderConfiguration.DIV_POSITION), JSPBuilderConfiguration.DIV_POSITION_RELATIVE_VALUE)){
					jspCode.append("<style type=\"text/css\">")
					.append(".")
					.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL_RELATIVE))
					.append("_").append(entityName).append(" {position:relative; left: 10px;  top: 15px;} ")
					.append(".")
					.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT_RELATIVE))
					.append("_").append(entityName).append(" {position:relative; left: 180px;}")
					.append("</style>");
				}
				
				
				Iterator it = fields.iterator();
				while (it.hasNext()) {
					
					EntityField entityField = (EntityField) it.next();
					String physicalName = entityField.getPhysicalName();
					
					if ((!physicalName.equals(ICatalogAPI.ID_FIELD_NAME)) &&
						(!physicalName.equals(ICatalogAPI.NUMEXP_FIELD_NAME)) &&
						(!entityField.getType().equals(InternalDataType.LONG_BIN))) {
					
						String property = entityName + ":" + physicalName.toUpperCase();
						
						/*
						jspCode.append("<tr>\r\n")
						   	   .append("<td><img src='<ispac:rewrite href=\"img/pixel.gif\"/>' border=\"0\" height=\"8\"/></td>\r\n")
						   	   .append("</tr>\r\n")
						   	   .append("<tr>\r\n")
						   	   .append("<td>\r\n")
						   	   .append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n")
							   .append("<tr>\r\n")
							   .append("<td width=\"15\"><img height=\"1\" width=\"15\" src='<ispac:rewrite href=\"img/pixel.gif\"/>'/></td>\r\n")
							   .append("<td height=\"20\" width=\"200\" class=\"formsTitleB\" valign=\"top\">\r\n")
							   .append(NOBR_TAG_OPEN)
							   .append(generateLabel(property))
							   .append(":")
							   .append(NOBR_TAG_CLOSE)
							   .append("</td>\r\n");
						*/
						jspCode.append("<div id=\"label_")
							   .append(property);
						if (StringUtils.equalsIgnoreCase(jspBuilderConfiguration.get(JSPBuilderConfiguration.DIV_POSITION), JSPBuilderConfiguration.DIV_POSITION_RELATIVE_VALUE)){
							jspCode.append("\" class=\"")
							.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL))
							.append(" ")
							.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL_RELATIVE))
							.append("_").append(entityName);
						}else{
							jspCode.append("\" style=\"position: absolute; top: ")
							   .append(top)
							   .append("px; left: 10px; width: 110px;\" class=\"")
							   .append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL));
						}
							   //.append("px; left: 10px;\" class=\"")
								
						jspCode.append("\">\r\n")
							   //.append(NOBR_TAG_OPEN)
							   .append(generateLabel(property))
							   .append(":")
							   //.append(NOBR_TAG_CLOSE)
							   .append("</div>\r\n");
						
						//jspCode.append("<td height=\"20\">\r\n");
						jspCode.append("<div id=\"data_")
							   .append(property);
						
						if (StringUtils.equalsIgnoreCase(jspBuilderConfiguration.get(JSPBuilderConfiguration.DIV_POSITION), JSPBuilderConfiguration.DIV_POSITION_RELATIVE_VALUE)){
							   jspCode.append("\" class=\"") 
							   .append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT_RELATIVE))
							   .append("_").append(entityName)
							   .append("\">\r\n");
							
						}else{
							   jspCode.append("\" style=\"position: absolute; top: ")
						   	   .append(top)
						   	   .append("px; left: 130px; width:100% ;\" >\r\n");
						}
					
						
						top += 25;
						if (entityField.isMultivalue()){
							top += 100;
						}
							
						InternalDataType dataType = entityField.getType();
						if (dataType.equals(InternalDataType.LONG_TEXT)) {
							if (entityField.isMultivalue()){
								jspCode.append(generateIspacHtmlTextareaMultivalueTag(property, DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLS));
							}else{
								jspCode.append(generateIspacHtmlTextareaTag(property, DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLS));
							}
							top += 25;
						}
						else if (dataType.equals(InternalDataType.DATE)) {
							if (entityField.isMultivalue()){
								jspCode.append(generateIspacHtmlTextMultivalueCalendar(property, 14, 10, "dd/mm/yyyy", true));
							}else{
								jspCode.append(generateIspacHtmlTextCalendar(property, 14, 10, "dd/mm/yyyy", true));
							}
						}
						else if (dataType.equals(InternalDataType.TIMESTAMP)) {
							if (entityField.isMultivalue()){
								jspCode.append(generateIspacHtmlTextMultivalueTimestamp(property));
							}else{
								jspCode.append(generateIspacHtmlTextTimestamp(property));
							}
						}
						else {
							
							EntityValidation entityValidation = (EntityValidation) validations.get(new Integer(entityField.getId()));
							
							if ((entityValidation != null) &&
								(!StringUtils.isEmpty(entityValidation.getTable()))) {
								
								// Validación de tabla
								String propertySustitute = null;
								
								boolean textarea = false;
//No se permite validacion de tablas de texto largo								
//								if (dataType.equals(InternalDataType.LONG_TEXT)) {
//									textarea = true;
//								}
								if (entityField.isMultivalue()){
									propertySustitute = entityDef.findField(entityValidation.getFieldId()).getPhysicalName().toUpperCase() + "_" + entityValidation.getTable() + ":SUSTITUTO";
									jspCode.append(generateIspacHtmlTextMultivalueImageFrame(property, propertySustitute, entityValidation.getTable(), EntityType.getInstance(Integer.parseInt(entityValidation.getTableType())), 80, textarea, 2, 80));
								}else{
									propertySustitute = entityDef.findField(entityValidation.getFieldId()).getPhysicalName().toUpperCase() + "_" + entityValidation.getTable() + ":SUSTITUTO";
									jspCode.append(generateIspacHtmlTextImageFrame(property, propertySustitute, entityValidation.getTable(), EntityType.getInstance(Integer.parseInt(entityValidation.getTableType())), 80, textarea, 2, 80));
								}
							}
							else {
								// Establecer la longitud máxima del campo
								int maxlength = entityField.getSize();
								
								if (dataType.equals(InternalDataType.LONG_INTEGER)) {
									maxlength = 10;
								}
								else if (dataType.equals(InternalDataType.LONG_DECIMAL)) {
									maxlength = 0;
								}
								else if (dataType.equals(InternalDataType.SHORT_DECIMAL)) {
									
									// Número posiciones para separadores de miles
									int intSize3 = (maxlength - entityField.getPrecision()) / 3;
									if ((intSize3 > 0) && (((maxlength - entityField.getPrecision()) % 3) == 0)) {
										intSize3 = intSize3 - 1;
									}
									maxlength = maxlength + intSize3;
									
									// Y una posición para el separador decimal
									if (entityField.getPrecision() > 0) {
										maxlength = maxlength + 1;
									}
								}
								if (entityField.isMultivalue()){
									jspCode.append(generateIspacHtmlTextMultivalueTag(property, 80, maxlength));
								}else{
									jspCode.append(generateIspacHtmlTextTag(property, 80, maxlength));
								}
							}
						}
						/*
						jspCode.append("</td>\r\n")
							   .append("</tr>\r\n")
							   .append("</table>\r\n")
							   .append("</td>\r\n")
							   .append("</tr>\r\n");
						*/
						jspCode.append("</div>\r\n");
					}
				}
				//jspCode.append("</table>\r\n");
			}
		}
		
		StringBuffer divDataBlock = new StringBuffer();
		
		divDataBlock.append("<div id=\"dataBlock_")
				    .append(entityName)
				    .append("\" style=\"position: relative; height: ")
		   			.append(top + 25)
		   			.append("px; width: 600px\">\r\n")
		   			.append(jspCode)
		   			.append("</div>\r\n");
		   
		return divDataBlock.toString();
	}
	
	/**
	 * 
	 * @param jspCode
	 * @param attribute
	 * @param property
	 */
	private static void generateAttributeWithProperty(StringBuffer jspCode,
													  String attribute,
													  String property) {
		
		if (!StringUtils.isEmpty(property)) {
			
			String value = VALUE_PROPERTY_OPEN
						 + property
						 + VALUE_CLOSE;
			
			generateAttribute(jspCode, attribute, value);
		}
	}
	
	/**
	 * 
	 * @param jspCode
	 * @param attribute
	 * @param property
	 */
	private static void generateAttributeWithPropertyMultivalue(StringBuffer jspCode,
													  String attribute,
													  String property) {
		
		if (!StringUtils.isEmpty(property)) {
			
			String value = VALUE_PROPERTY_MULTIVALUE_OPEN
						 + property
						 + VALUE_CLOSE;
			
			generateAttribute(jspCode, attribute, value);
		}
	}
	
	
	
	/**
	 * 
	 * @param jspCode
	 * @param attribute
	 * @param expression
	 */
	private static void generateAttributeWithExpression(StringBuffer jspCode,
														String attribute,
														String expression) {
		
		if (!StringUtils.isEmpty(expression)) {
		
			jspCode.append(attribute)
				   .append(OPEN_EXPRESSION_VALUE)
			   	   .append(expression)
			   	   .append(CLOSE_EXPRESSION_VALUE);
		}
	}
	
	/**
	 * 
	 * @param jspCode
	 * @param attribute
	 * @param value
	 */
	private static void generateAttribute(StringBuffer jspCode,
										  String attribute,
										  String value) {
		
		// Evitar un null en el valor de la propiedad del tag jsp
		// que provoca una excepción al procesar la página
		if (value == null) {
			value = "";
		}
		
		jspCode.append(attribute)
		   	   .append(OPEN_ATTRIBUTE_VALUE)
		   	   .append(value)
		   	   .append(CLOSE_ATTRIBUTE_VALUE);
	}
	
	/**
	 * 
	 * @param jspCode
	 * @param attribute
	 * @param value
	 */
	private static void generateAttribute(StringBuffer jspCode,
										  String attribute,
										  int value) {
		
		generateAttribute(jspCode, attribute, String.valueOf(value));
	}
	
	/**
	 * 
	 * @return Devuelve el código correspondiente al contenido de la pesta de documentos.
	 */
	public static String getBlockDocuments(int i){
		
		StringBuffer jspCode = new StringBuffer();
		jspCode.append("<tiles:insert template=\"/forms/common/entityDocumentsForm.jsp\" flush=\"false\" ignore=\"true\">" )
		.append("<tiles:put name=\"blockdocs\" direct=\"true\">"+i+"</tiles:put></tiles:insert>");
		return jspCode.toString();
	}
	
	private static String generateBeanMessage(String key){
		return "<bean:message key='"+key+"' />";
	}

}