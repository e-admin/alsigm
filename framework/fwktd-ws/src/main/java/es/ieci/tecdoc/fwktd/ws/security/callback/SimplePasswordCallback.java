package es.ieci.tecdoc.fwktd.ws.security.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Callback para obtener la clave del usuario.
 *
 * Esta clase devuelve la contraseña establecida.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class SimplePasswordCallback implements CallbackHandler {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SimplePasswordCallback.class);

	/**
	 * Clave del usuario.
	 */
	private String password = null;

	/**
	 * Constructor.
	 */
	public SimplePasswordCallback() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param password
	 *            Clave del usuario.
	 */
	public SimplePasswordCallback(String password) {
		this();
		setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		logger.info("Entrada en ClientPasswordCallback.handle");

		if ((callbacks != null) && (callbacks.length > 0)) {

			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			if (pc != null) {

				logger.info("Obteniendo la clave para el usuario [{}]",
						pc.getIdentifier());

				pc.setPassword(getPassword());
			}
		}
	}

}
