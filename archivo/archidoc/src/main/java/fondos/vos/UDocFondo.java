/*
 * Created on 22-jul-2005
 *
 */
package fondos.vos;

import common.Constants;

/**
 * @author ABELRL
 * 
 */
public class UDocFondo {
	public static String getIdentificacion(String codigoFondo, String signatura) {
		return codigoFondo
				+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL
				+ signatura;
	}

}
