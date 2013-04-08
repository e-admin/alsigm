package descripcion.actions;

import descripcion.model.xml.card.TipoFicha;

/**
 * Acción para la gestión de las fichas ISAD(G) de las unidades documentales en
 * relación de entrega
 */
public class ISADGUDocREAction extends FichaBaseAction {

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha {@link TipoFicha}.
	 */
	protected int getTipoFicha() {
		return TipoFicha.FICHA_UDOCRE;
	}

}