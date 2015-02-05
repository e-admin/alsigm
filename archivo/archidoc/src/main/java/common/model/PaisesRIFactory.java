/*
 * Created on 19-abr-2005
 *
 */
package common.model;

import java.util.Locale;

import configuracion.bi.GestionInfoSistemaBI;

/**
 * @author ABELRL
 * 
 */
public class PaisesRIFactory {
	public static PaisesRI createPaisesRI(GestionInfoSistemaBI infoSistema,
			Locale locale) {
		return PaisesRIImpl.getInstance(infoSistema, locale);
	}
}
