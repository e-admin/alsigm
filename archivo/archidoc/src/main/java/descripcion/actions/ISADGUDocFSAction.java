package descripcion.actions;

import descripcion.model.xml.card.TipoFicha;

/**
 * Acción para la gestión de las fichas ISAD(G) de las unidades documentales en
 * división de fracción de serie
 */
public class ISADGUDocFSAction extends FichaBaseAction {

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha {@link TipoFicha}.
	 */
	protected int getTipoFicha() {
		return TipoFicha.FICHA_UDOCFS;
	}

}