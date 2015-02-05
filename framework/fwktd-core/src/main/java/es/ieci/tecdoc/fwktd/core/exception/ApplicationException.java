package es.ieci.tecdoc.fwktd.core.exception;

/*
 * 
 */

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.ObjectError;

import es.ieci.tecdoc.fwktd.core.model.RemoteMessageVO;
import es.ieci.tecdoc.fwktd.core.services.messages.MessagesService;

/**
 * Clase base para las excepciones producidas en las aplicaciones. Esta es la
 * clase que se deberá extender para disparar nuevas excepciones en las
 * aplicaciones.
 * 
 */
public abstract class ApplicationException extends RuntimeException implements
		ManageableException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -7551590813732051074L;

	/**
	 * Nivel de error de la excepción. Por defecto a ERROR.
	 */
	protected LevelEnum level = LevelEnum.ERROR;

	/**
	 * Los parametros para componer el mensaje de error
	 */
	protected String[] params = new String[] { StringUtils.EMPTY };

	/**
	 * La clave para localizar el mensaje de error
	 */
	protected String messageId = getDefaultMessageId();

	/**
	 * Constructor de la clase.
	 * 
	 * @param msg
	 *            ubicación en el código, operación o motivo que provocan el
	 *            disparo de la excepción
	 */
	public ApplicationException(String msg) {
		this(LevelEnum.ERROR, msg);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param level
	 *            Nivel de error del mensaje asociado.
	 * @param msg
	 *            ubicación en el código, operación o motivo que provocan el
	 *            disparo de la excepción
	 */
	public ApplicationException(LevelEnum level, String msg) {
		super(msg);
		this.params = new String[] { msg };
		if (level!=null){
			this.level = level;
		}
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param messageId
	 *            clave del mensaje en el fichero de recursos con los mensajes
	 *            internacionalizados para las excepciones
	 * @param params
	 *            parametros para componer el mensaje con el del fichero de
	 *            recursos
	 * @param defaultMessage
	 *            mensaje a mostar en caso de que no encuentre la clave del
	 *            mensaje
	 */
	public ApplicationException(String messageId, String[] params,
			String defaultMessage) {
		this(LevelEnum.ERROR, messageId, params, defaultMessage);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param level
	 *            Nivel de error del mensaje asociado
	 * @param messageId
	 *            clave del mensaje en el fichero de recursos con los mensajes
	 *            internacionalizados para las excepciones
	 * @param params
	 *            parametros para componer el mensaje con el del fichero de
	 *            recursos
	 * @param defaultMessage
	 *            mensaje a mostar en caso de que no encuentre la clave del
	 *            mensaje
	 */
	public ApplicationException(LevelEnum level, String messageId,
			String[] params, String defaultMessage) {

		this(level, defaultMessage);
		this.messageId = messageId;
		this.params = params;
	}

	/**
	 * Constructor de la clase. Utiliza la clave internacionalizada por defecto,
	 * es decir, la retornada por el metodo <code>getDefaultMessageId</code>.
	 * 
	 * @param params
	 *            parametros para componer el mensaje con el del fichero de
	 *            recursos
	 * @param defaultMessage
	 *            mensaje a mostar en caso de que no encuentre la clave del
	 *            mensaje
	 */
	public ApplicationException(String[] params, String defaultMessage) {
		this(LevelEnum.ERROR, params, defaultMessage);
	}

	/**
	 * Constructor de la clase. Utiliza la clave internacionalizada por defecto,
	 * es decir, la retornada por el metodo <code>getDefaultMessageId</code>.
	 * 
	 * @param level
	 *            Nivel de error del mensaje
	 * @param params
	 *            parametros para componer el mensaje con el del fichero de
	 *            recursos
	 * @param defaultMessage
	 *            mensaje a mostar en caso de que no encuentre la clave del
	 *            mensaje
	 */
	public ApplicationException(LevelEnum level, String[] params,
			String defaultMessage) {
		this(level, defaultMessage);
		this.params = params;
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param remoteError
	 *            VO utilizado para persistir excepciones remotas
	 */
	public ApplicationException(RemoteMessageVO remoteError) {
		this(LevelEnum.ERROR, remoteError.getMessageId(), remoteError
				.getParams(), remoteError.getDefaultMessage());
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param level
	 *            Tipo de error
	 * 
	 * @param remoteError
	 *            VO utilizado para persistir excepciones remotas
	 */
	public ApplicationException(LevelEnum level, RemoteMessageVO remoteError) {
		this(level, remoteError.getMessageId(), remoteError.getParams(),
				remoteError.getDefaultMessage());
	}

	/**
	 * Obtiene el id del mensaje por defecto. Se utiliza para indicar la clave,
	 * en un fichero de recursos, de la que obtener el mensaje
	 * internacionalizado de la excepcion. Las subclases deberán extender este
	 * metodo para definir sus propios mensajes.
	 * 
	 * @return el id del mensaje internacionalizado de la excepcion
	 */
	public abstract String getDefaultMessageId();

	/**
	 * Obtiene la clave para el mensaje de error
	 * 
	 * @return la clave para el mensaje de error
	 */
	public String getMessageId() {
		String result=this.messageId;
		if (StringUtils.isEmpty(result)){
			result=this.getDefaultMessageId();
		}
		return result;
	}

	/**
	 * Las subclases deben extender este metodo para definir un mensaje de error
	 * por defecto
	 * 
	 * @return el mensaje de error. En la clase base, devuelve <code>null</code>
	 */
	public String getDefaultMessage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ManageableException#getError
	 * ()
	 */
	public ObjectError getError() {
		String defaultMessage = getDefaultMessage();
		if (defaultMessage == null) {
			defaultMessage = getMessage();
		}
		ObjectError error = new ObjectError(this.getClass().getName(),
				new String[] { this.getMessageId() }, this.params,
				defaultMessage);
		return error;
	}

	/**
	 * Obtiene el {@link RemoteMessageVO} que define la informacion de error de
	 * la excepcion
	 * 
	 * @return el RemoteMessageVO asociado a la excepcion
	 * @see RemoteMessageVO
	 */
	public RemoteMessageVO getRemoteError() {
		return new RemoteMessageVO(getError());
	}
	
	protected MessagesService getMessagesService() {
		MessagesService result = null;
		result = MessagesService.getInstance();
		return result;
	}

	@Override
	public String getLocalizedMessage() {
		String result=null;
		MessagesService messages = getMessagesService();
		if (messages == null) {
			return super.getLocalizedMessage();
		} else {
			ObjectError error = getError();
			Assert.notNull(error);
			
			result= messages.getMessage(error.getCode(), error.getArguments(),
					error.getDefaultMessage());
			return result;
		}
	}

	public LevelEnum getLevel() {
		return level;
	}
}