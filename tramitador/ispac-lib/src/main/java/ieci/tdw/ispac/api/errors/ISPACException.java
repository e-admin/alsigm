package ieci.tdw.ispac.api.errors;

import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

public class ISPACException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/** Nombre del fichero de recursos. */
	private static String BUNDLE_NAME = "ieci.tdw.ispac.api.errors.ISPACExceptionMessages";
	
	/** Nombre del fichero de recursos personalizado. */
	private static String CUSTOM_BUNDLE_NAME = "ieci.tdw.ispac.api.errors.CustomExceptionMessages";
	
	/** Clave por defecto para los errores. */
	private static String DEFAULT_MESSAGE_KEY = "exception.default";
	
	
	/** Recursos. */
	//private static final Map RESOURCE_BUNDLES = new HashMap();
	
	/**
	 * Obtiene el ResourceBundle del idioma.
	 * @param language
	 * @return
	 */
	/*
	private static ResourceBundle getResourceBundle(String language) {
		
		ResourceBundle resourceBundle = (ResourceBundle) RESOURCE_BUNDLES.get(language);
		if (resourceBundle == null) {
			
			resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
			RESOURCE_BUNDLES.put(language, resourceBundle);
		}
		
		return resourceBundle;
	}
	*/
							
	private String extraInfo = null;
	
	private Object args[] = null;
	
	/** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(ISPACException.class);

	public ISPACException()
	{
	    logger.error(composeMessage("Error genérico."));
	}
	public ISPACException(boolean blog)
	{
	    if (blog)
	        logger.error(composeMessage("Error genérico."));
	}

	public ISPACException(String message)
	{
		super(message);
		logger.error(composeMessage(message));
	}
	
	public ISPACException(String message, String extraInfo)
	{
		super(message);
		setExtraInfo(extraInfo);
		logger.error(composeMessage(message));
	}	
	
	public ISPACException(String message,String extraInfo, boolean blog){
		super(message);
		setExtraInfo(extraInfo);
		if (blog)
		{
		    logger.error( composeMessage(message));
		}		
	}
	
	public ISPACException(String message, Object args[], boolean blog){
		super(message);
		setArgs(args);
		if (blog)
		{
		    logger.error( composeMessage(message));
		}		
	}

	public ISPACException(Throwable cause)
	{
		super(cause);
		logger.error(composeMessage(cause.getMessage()));
	}

	public ISPACException(String message,Throwable cause)
	{
		super(message,cause);
		logger.error(composeMessage(message+" - "+ cause.getMessage()));
	}

	public ISPACException(String message,boolean blog)
	{
		super(message);
		if (blog)
		{
		    logger.error( composeMessage(message));
		}
	}

	public ISPACException(Throwable cause,boolean blog)
	{
		super(cause);
		if (blog)
		{
		    logger.error(composeMessage(cause.getMessage()));
		}
	}

	public ISPACException(String message,Throwable cause,boolean blog)
	{
		super(message,cause);
		if (blog)
		{
		    logger.error(composeMessage(message+" - "+ cause.getMessage()));
		}
	}

	protected String composeMessage(String message)
	{
	    //TODO Revisar la sintaxis de la cadena y elegir una similar a las usadas
	    // por log4j
	    return	message
				+ "; "
				+ getStackTrace()[0].getFileName()
				+ "; "
				+ getStackTrace()[0].getMethodName()
				+ "; "
				+ getStackTrace()[0].getLineNumber();
	}
	

	public String getExtendedMessage(Locale locale) {
		return getResourceProperty(locale, getMessage());
	}
   
	public String getExtendedExtraInfo(Locale locale){
		return getResourceProperty(locale, getExtraInfo());
	}
	   
	/**
	 * Carga un mensaje de error del archivo de recursos en el idioma
	 * especificiado.
	 * 
	 * @param locale
	 *           Código de localización con el idioma especificado.
	 * @param property
	 *           Propiedad a buscar en el archivo de recursos
	 * @return El mensaje de error mencionado.
	 */
	private String getResourceProperty(Locale locale, String property) {
		
		String msg = null;
		ResourceBundle resBundle;
		
		if (property == null) {
			property = DEFAULT_MESSAGE_KEY;
		}
			
		// Fichero de recursos de ispaclib
        try {
            //resBundle = getResourceBundle(locale.getLanguage());
        	resBundle = ResourceBundle.getBundle(getMessagesFile(), locale);
            msg = resBundle.getString(property);
            return MessagesFormatter.format(msg, getArgs());
        }
        catch (Exception exc) {
        	/*
        	try {
        		resBundle = ResourceBundle.getBundle(getMessagesFile());
        		msg = resBundle.getString(property);
        		return msg;
        	}
        	catch(Exception exc2) {
        		
        		Logger.getLogger("EXCEPTIONS_i18n").error(property);
 	            //msg = getMessage();
        		msg = property;
	            //exc2.printStackTrace(System.err);
        	}
        	*/
        }
        
		// Fichero de recursos personalizado
        try {
        	resBundle = ResourceBundle.getBundle(getCustomMessagesFile(), locale);
            msg = resBundle.getString(property);
            return MessagesFormatter.format(msg, getArgs());
        }
        catch (Exception exc) {
        	/*
        	try {
        		resBundle = ResourceBundle.getBundle(getCustomMessagesFile());
        		msg = resBundle.getString(property);
        		return msg;
        	}
        	catch(Exception exc2) {
        	*/
        		
    		//Logger.getLogger("EXCEPTIONS_i18n").error(property);
            //msg = getMessage();
    		msg = property;
            //exc2.printStackTrace(System.err);
        	//}
        }
        
        return MessagesFormatter.format(msg, getArgs());
	}
	   
	/**
	 * Devuelve el nombre del archivo de recursos que contiene los mensajes para
	 * esta excepción.
	 * 
	 * @return El nombre del archivo de recursos mencionado.
	 */
	public String getMessagesFile() {
		return BUNDLE_NAME;
	}
	
	/**
	 * Devuelve el nombre del archivo de recursos personalizado que contiene los mensajes para
	 * esta excepción.
	 * 
	 * @return El nombre del archivo de recursos mencionado.
	 */
	public String getCustomMessagesFile() {
		return CUSTOM_BUNDLE_NAME;
	}
	   
	public void setExtraInfo(String extraInfo){
		this.extraInfo = extraInfo;
	}
	public String getExtraInfo(){
		return extraInfo;
	}
	
	/**
	 * @return Returns the args.
	 */
	public Object[] getArgs() {
		return args;
	}
	/**
	 * @param args The args to set.
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}