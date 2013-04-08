/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que contiene metodos tipicos a realizar con la informacion de
 *          usuario
 */
public class UsuarioHelper {

	/**
	 * Método que completa los datos minimos necesarios del usuario en el caso
	 * de que no existan
	 *
	 * @param usuario
	 * @return
	 */
	public static UsuarioVO validateUsusario(UsuarioVO usuario) {
		if (usuario != null && StringUtils.isNotEmpty(usuario.getLoginName())) {
			if (usuario.getConfiguracionUsuario() == null) {
				usuario.setConfiguracionUsuario(new ConfiguracionUsuarioVO());
			}

			if (StringUtils.isEmpty(usuario.getConfiguracionUsuario()
					.getIdEntidad())) {
				usuario.getConfiguracionUsuario().setIdEntidad(
						ConstantKeys.ENTIDAD_DEFAULT);
			}

			if (usuario.getConfiguracionUsuario().getLocale() == null) {
				Locale locale = new Locale(
						ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
						ConstantKeys.LOCALE_COUNTRY_DEFAULT);
				usuario.getConfiguracionUsuario().setLocale(locale);
			}
		}

		return usuario;

	}

}
