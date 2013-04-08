package es.ieci.tecdoc.fwktd.csv.web.delegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.spring.context.AppContext;

/**
 * Factoría para la carga del delegate de gestión de captchas si se indica en el
 * fichero de configuración:
 * 
 * <pre>
 * fwktd-csv-webapp.useCaptcha = true
 * </pre>
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class CaptchaDelegateFactory {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CaptchaDelegateFactory.class);

	/**
	 * Constructor.
	 */
	public CaptchaDelegateFactory() {
		super();
	}

	/**
	 * Devuelve el delegate si se ha indicado el uso de captchas,
	 * 
	 * @param useCaptcha
	 *            Indicador de uso de captchas.
	 * @param captchaDelegateBeanName
	 *            Nombre del bean del delegate de captchas.
	 * @return Delegate de captchas.
	 */
	public CaptchaDelegate getCaptchaDelegate(String useCaptcha,
			String captchaDelegateBeanName) {

		CaptchaDelegate delegate = null;

		logger.info(
				"Obteniendo el CaptchaDelegate: useCaptcha=[{}], captchaDelegateBeanName=[{}]",
				useCaptcha, captchaDelegateBeanName);
		
		if (Boolean.toString(true).equalsIgnoreCase(useCaptcha)) {
			delegate = (CaptchaDelegate) AppContext
					.getBean(captchaDelegateBeanName);
		}
		
		logger.info("CaptchaDelegate: {}", delegate);

		return delegate;
	}

}
