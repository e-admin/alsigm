package descripcion.actions;

import descripcion.model.xml.card.TipoFicha;

/**
 * Acción para la gestión de las fichas ISAD(G) de los elementos del cuadro de
 * clasificación.
 */
public class ISADGAction extends FichaBaseAction {

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha {@link TipoFicha}.
	 */
	protected int getTipoFicha() {
		return TipoFicha.FICHA_ELEMENTO_CF;
	}

}