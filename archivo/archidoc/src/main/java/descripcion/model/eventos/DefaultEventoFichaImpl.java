package descripcion.model.eventos;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;

import common.Constants;

import descripcion.model.xml.card.Ficha;

/**
 * Implementación del interfaz para el comportamiento de los eventos generados
 * en una ficha.
 */
public class DefaultEventoFichaImpl implements IEventoFicha {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(DefaultEventoFichaImpl.class);

	/**
	 * Constructor.
	 */
	public DefaultEventoFichaImpl() {
	}

	/**
	 * Ejecuta la lógica del evento.
	 * 
	 * @param tipoEvento
	 *            Tipo de evento ({link TipoEvento}).
	 * @param ficha
	 *            Ficha de descripción.
	 * @return Errores producidos
	 * @throws EventoFichaException
	 *             si ocurre algún error.
	 */
	public ActionErrors executeEvent(int tipoEvento, Ficha ficha, Locale locale)
			throws EventoFichaException {
		if (logger.isInfoEnabled())
			logger.info("executeEvent: tipoEvento=[" + tipoEvento + "]");

		if (logger.isDebugEnabled())
			logger.debug("executeEvent: ficha=" + Constants.NEWLINE
					+ ficha.toString());

		return new ActionErrors();
	}
}
