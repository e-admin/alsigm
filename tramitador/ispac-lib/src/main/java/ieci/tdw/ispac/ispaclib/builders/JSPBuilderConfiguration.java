package ieci.tdw.ispac.ispaclib.builders;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.PropertiesConfiguration;

public class JSPBuilderConfiguration extends PropertiesConfiguration {

	private static JSPBuilderConfiguration mInstance = null;

	private static final String DEFAULT_CONFIG_FILENAME = "jspbuilder.properties";

	public static final String PROPERTY_READONLY = "PROPERTY_READONLY";
	public static final String STYLE_CLASS_LABEL = "STYLE_CLASS_LABEL";
	public static final String STYLE_CLASS_LABEL_CSS = "STYLE_CLASS_LABEL_CSS";
	public static final String STYLE_CLASS_INPUT = "STYLE_CLASS_INPUT";
	public static final String STYLE_CLASS_INPUT_CSS = "STYLE_CLASS_INPUT_CSS";
	public static final String STYLE_CLASS_READONLY = "STYLE_CLASS_READONLY";
	public static final String STYLE_CLASS_READONLY_CSS = "STYLE_CLASS_READONLY_CSS";
	public static final String STYLE_CLASS_DELETE_LINK = "STYLE_CLASS_DELETE_LINK";
	public static final String STYLE_CLASS_DELETE_LINK_CSS = "STYLE_CLASS_DELETE_LINK_CSS";
	public static final String STYLE_CLASS_SELECT_CSS = "STYLE_CLASS_SELECT_CSS";
	public static final String STYLE_CLASS_DATABLOCK_CSS = "STYLE_CLASS_DATABLOCK_CSS";
	public static final String STYLE_CLASS_BODY_CSS = "STYLE_CLASS_BODY_CSS";
	public static final String FRAME_WIDTH = "FRAME_WIDTH";
	public static final String FRAME_HEIGHT = "FRAME_HEIGHT";
	public static final String IMAGE_FRAME_HREF_IMAGE = "IMAGE_FRAME_HREF_IMAGE";
	public static final String IMAGE_FRAME_DELETE_IMAGE = "IMAGE_FRAME_DELETE_IMAGE";
	public static final String TITLE_KEY_LINK = "TITLE_KEY_LINK";
	public static final String TITLE_KEY_IMAGE_DELETE = "TITLE_KEY_IMAGE_DELETE";
	public static final String CONFIRM_DELETE_KEY = "CONFIRM_DELETE_KEY";
	public static final String PARAMETER_VALUE = "PARAMETER_VALUE";
	public static final String PARAMETER_SUBSTITUTE = "PARAMETER_SUBSTITUTE";	
	public static final String CALENDAR_HREF_IMG = "CALENDAR_HREF_IMG";
	public static final String CALENDAR_HREF_JS = "CALENDAR_HREF_JS";
	public static final String CALENDAR_HREF_BUTTON = "CALENDAR_HREF_BUTTON";
	public static final String CREATE_ENTITY_FORM_NAME = "CREATE_ENTITY_FORM_NAME";
	public static final String CREATE_ENTITY_FORM_DESCRIPTION = "CREATE_ENTITY_FORM_DESCRIPTION";
	public static final String CREATE_ENTITY_FORM_CLASS = "CREATE_ENTITY_FORM_CLASS";
	public static final String AUX_DATA_TABS_PROPERTIES_BY_TAB = "AUX_DATA_TABS_PROPERTIES_BY_TAB";
	public static final String AUX_DATA_TABS_ELEMENTS_ROW = "AUX_DATA_TABS_ELEMENTS_ROW";
	public static final String STYLE_CLASS_INPUT_RELATIVE = "STYLE_CLASS_INPUT_RELATIVE";
	public static final String STYLE_CLASS_LABEL_RELATIVE = "STYLE_CLASS_LABEL_RELATIVE";
	public static final String DIV_POSITION	= "DIV_POSITION";

	public static final String DIV_POSITION_RELATIVE_VALUE = "relative";
	public static final String DIV_POSITION_ABSOLUTE_VALUE = "absolute";	
	/**
	 * Constructor.
	 *
	 */
	private JSPBuilderConfiguration() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized JSPBuilderConfiguration getInstance() 
			throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new JSPBuilderConfiguration();
			mInstance.initiate(DEFAULT_CONFIG_FILENAME);
		}
		return mInstance;
	}
}