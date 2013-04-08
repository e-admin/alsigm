package descripcion.model.eventos;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import descripcion.model.xml.card.Ficha;

/**
 * Interfaz para el comportamiento de los eventos generados en una ficha.
 */
public interface IEventoFicha {

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
			throws EventoFichaException;

}
