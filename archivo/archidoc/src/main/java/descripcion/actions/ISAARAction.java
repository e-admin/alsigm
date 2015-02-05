package descripcion.actions;

import descripcion.model.xml.card.TipoFicha;

/**
 * Acción para la gestión de las fichas ISAAR de los descriptores.
 */
public class ISAARAction extends FichaBaseAction {

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha {@link TipoFicha}.
	 */
	protected int getTipoFicha() {
		return TipoFicha.FICHA_DESCRIPTOR;
	}

}