package es.ieci.tecdoc.fwktd.core.services.messages;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.core.model.RemoteMessageVO;
import es.ieci.tecdoc.fwktd.core.services.i18n.I18nService;

/**
 * Servicio de internacionalización de mensajes (Singleton). Recibe el locale
 * por medio de un parámetro de configuración. En caso de no estar informado se
 * puede inyectar el locale y si no se utiliza el locale por defecto. Por
 * defecto utiliza BaseResourceBundleMessageSource para añadir los bundles con
 * los mensajes internacionalizados.
 * 
 * @see BaseResourceBundleMessageSource
 */
public class MessagesService {

	/**
	 * Message Sources que complementan los plugins con nuevos ficheros de
	 * resources
	 */
	private ResourceBundleMessageSource messageSource;

	/**
	 * Singleton de MessagesService
	 */
	private static MessagesService instance;

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MessagesService.class);

	/**
	 * Constructor.
	 */
	protected MessagesService() {
		messageSource = new ResourceBundleMessageSource();
	}

	/**
	 * Devuelve unica instancia de esta clase.
	 * 
	 * @return MessagesService
	 */
	public static MessagesService getInstance() {

		if (null == instance) {
			instance = new MessagesService();
		}

		return instance;
	}

	/**
	 * Mensaje internacionalizado, con el locale configurado, de
	 * {@link MessageSourceResolvable}
	 * 
	 * @param msr
	 *            MessageSourceResolvable
	 * @return Mensaje
	 * @throws NoSuchMessageException
	 */
	public String getMessage(MessageSourceResolvable msr)
			throws NoSuchMessageException {
		return messageSource.getMessage(msr, getCurrentLocale());
	}

	/**
	 * Mensaje internacionalizado con el locale configurado
	 * 
	 * @param code
	 *            Código del mensaje a buscar.
	 * @param args
	 *            Argumentos asociados al mensaje también internacionalizables.
	 * @return Mensaje internacionalizado.
	 * @throws NoSuchMessageException
	 *             Mensaje no encontrado en bundles.
	 */
	public String getMessage(String code, Object[] args)
			throws NoSuchMessageException {
		return getMessage(code, args, code);
	}

	/**
	 * Mensaje internacionalizado con el locale configurado
	 * 
	 * @param code
	 *            Código del mensaje a buscar.
	 * @param args
	 *            Argumentos asociados al mensaje también internacionalizables.
	 * @param messageDefault
	 *            Mensaje por defecto a mostrar si el código del mensaje no se
	 *            ha encontrado en los bundles.
	 * @return Mensaje internacionalizado.
	 * @throws NoSuchMessageException
	 */
	public String getMessage(String code, Object[] args, String messageDefault)
			throws NoSuchMessageException {
		try {
			return messageSource.getMessage(code, args, getCurrentLocale());
		} catch (NoSuchMessageException ex) {
			if (messageDefault != null) {
				return MessageFormat.format(messageDefault, args);
			}

			throw ex;
		}

	}

	/**
	 * Mensaje internacionalizado con el locale configurado a partir de
	 * RemoteMessageVO
	 * 
	 * @param message
	 *            Mensaje contenido normalmente en excepciones persisitidas o
	 *            remotas.
	 * @return mensaje internacionalizado.
	 */
	public String getMessage(RemoteMessageVO message) {
		return getMessage(message.getMessageId(), message.getParams(), message
				.getDefaultMessage());
	}

	/**
	 * Mensaje internacionalizado con el locale configurado. devuelve el propio
	 * código en caso de no encontrar el código en los bundles.
	 * 
	 * @param code
	 *            Código del mensaje.
	 * @return mensaje internacionalizado.
	 */
	public String getMessage(String code) {
		return getMessage(code, null, code);
	}

	/**
	 * Mensaje internacionalizado con el locale configurado.
	 * 
	 * @param source
	 *            Fuente de datos a utilizar para resolver el mensaje.primero
	 *            busca en el indicado y luego busca en todos.
	 * @param code
	 *            Código del mensaje
	 * @param args
	 *            Parámetros
	 * @param defaultMessage
	 *            Mensaje por defecto en caso de no encontrar el código del
	 *            mensaje en los bundles.
	 * @return mensaje internacionalizado.
	 * @throws NoSuchMessageException
	 *             Excepcion en caso mensaje internacionalizado null.
	 */
	public String getMessage(MessageSource source, String code, Object[] args,
			String defaultMessage) throws NoSuchMessageException {

		String message = null;
		try {
			if (source != null) {
				message = source.getMessage(code, args,getCurrentLocale());
			}
		} catch (NoSuchMessageException ex) {
			;
		}

		if (null == message) {
			try {
				message = MessagesService.getInstance().getMessage(code, args,
						defaultMessage);
			} catch (NoSuchMessageException ex) {
				logger.warn("Mensaje no encontrado para clave: {}", code, ex);
				message = defaultMessage;

			}
		}

		if (null == message) {
			throw new NoSuchMessageException(code, getCurrentLocale());
		}

		return message;

	}

	/**
	 * Mensaje internacionalizado con el locale configurado.
	 * 
	 * @param source
	 *            Fuente de datos a utilizar para resolver el mensaje. sólo
	 *            busca en el indicado.
	 * @param code
	 *            código
	 * @param args
	 *            argumentos
	 * @return mensaje
	 * @throws NoSuchMessageException
	 *             si mensaje no encontrado.
	 */
	public String getMessage(MessageSource source, String code, Object[] args)
			throws NoSuchMessageException {
		return getMessage(source, code, args, code);
	}

	/**
	 * Setter para fijar fichero de recursos.
	 * 
	 * @param basename
	 *            fichero de recursos
	 */
	public void setBasename(String basename) {
		messageSource.setBasename(basename);
	}

	/**
	 * Setter para fijar ficheros de recursos.
	 * 
	 * @param basenames
	 *            ficheros de recursos.
	 */
	public void setBasenames(String[] basenames) {
		List<String> basenamesList = Arrays.asList(basenames);
		messageSource.setBasenames((String[]) basenamesList
				.toArray(new String[basenamesList.size()]));
	}

	/**
	 * Setter para especificar un nuevo MessageSource y no utilizar el de por
	 * defecto.
	 * 
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		Assert.notNull(messageSource);
		this.messageSource = messageSource;
	}

	/**
	 * Añade un message source hijo al message source activo.
	 * 
	 * @param childSource
	 *            el source a asignar
	 */
	public void addMessageSource(ResourceBundleMessageSource childSource) {

		if (this.messageSource != null) {
			childSource.setParentMessageSource(this.messageSource);
		}

		setMessageSource(childSource);
	}
	
	 protected Locale getCurrentLocale()
	    {
	        Locale result = null;
	        result = I18nService.getService().getLocale();
	        return result;
	    }

}
