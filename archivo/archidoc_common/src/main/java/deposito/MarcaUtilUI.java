package deposito;

import common.util.MarcaUtil;

/**
 * Utilidades de marcas de unidad de instalaci&oacute;n.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MarcaUtilUI extends MarcaUtil{

	/**
	 * Permite obtener si una unidad de instalaci&oacute;n est&aacute; bloqueada
	 * @param marcas Marca en la que se comprueba
	 * @return si la unidad de instalaci&oacute;n est&aacute; bloqueada
	 * 		(si tiene activo el bit MARCA_BLOQUEADA_TRANSFERENCIA o el bit MARCA_BLOQUEADA_DIVISIONFS)
	 */
	public static boolean isUnidadInstalacionBloqueada (int marcas) {
		return isBitActivoInMarca(MarcaUInstalacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA, marcas)
				|| isBitActivoInMarca(MarcaUInstalacionConstants.POSICION_BIT_MARCA_BLOQUEADA_DIVISIONFS, marcas);
	}

}