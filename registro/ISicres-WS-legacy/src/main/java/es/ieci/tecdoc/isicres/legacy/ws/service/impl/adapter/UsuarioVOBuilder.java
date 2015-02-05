package es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter;

import java.util.Locale;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security;

public class UsuarioVOBuilder {

	/**
	 * Metodo que adapta un
	 * {@link es.ieci.tecdoc.isicres.ws.legacy.service.books.Security} a un
	 * objeto {@link UsuarioVO}
	 * 
	 * @param security
	 * @return {@link UsuarioVO}
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.books.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * Metodo que adapta un
	 * {@link es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security} a un
	 * objeto {@link UsuarioVO}
	 * 
	 * @param security
	 * @return {@link UsuarioVO}
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * Metodo que adapta un
	 * {@link es.ieci.tecdoc.isicres.ws.legacy.service.units.Security} a un
	 * objeto {@link UsuarioVO}
	 * 
	 * @param security
	 * @return {@link UsuarioVO}
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * Metodo que adapta un
	 * {@link es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security} a un
	 * objeto {@link UsuarioVO}
	 * 
	 * @param security
	 * @return {@link UsuarioVO}
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * 
	 * @param security
	 * @return
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * 
	 * @param security
	 * @return
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.reports.Security security) {

		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * 
	 * @param security
	 * @return
	 */
	public UsuarioVO fromSecurityToUsuarioVO(
			es.ieci.tecdoc.isicres.ws.legacy.service.session.Security security) {
		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

	/**
	 * Devuelve una instancia de <code>UsuarioVO</code> haciendo uso de los
	 * parámetros que recibe. El identificador de la entidad que se devuelve es
	 * <code>ISicresKeys.IS_INVESICRES</code>, el locale instanciado igual a
	 * <i>es</i>
	 * 
	 * @see ISicresKeys
	 * 
	 * @param anUsername
	 *            nombre de sesión del usuario
	 * @param aPassword
	 *            contraseña del usuario
	 * @param anOfficeCode
	 *            identificador de la oficina con la que trabaja el usuario en
	 *            la sesión activa
	 * @return
	 */
	private UsuarioVO mapToUsuarioVO(String anUsername, String aPassword,
			String anOfficeCode) {
		UsuarioVO result = new UsuarioVO();
		result.setLoginName(anUsername);
		result.setPassword(aPassword);

		ConfiguracionUsuarioVO configuracionUsuario = new ConfiguracionUsuarioVO();
		configuracionUsuario.getOficina().setCodigoOficina(anOfficeCode);

		String entidad = ISicresKeys.IS_INVESICRES;
		// TODO falta saber como obtener locale
		Locale locale = new Locale("es", "ES");

		PermisosUsuarioVO permisos = new PermisosUsuarioVO();
		result.setPermisos(permisos);

		configuracionUsuario.setIdEntidad(entidad);
		configuracionUsuario.setLocale(locale);

		result.setConfiguracionUsuario(configuracionUsuario);

		return result;
	}

	/**
	 * @param security
	 * @return
	 */
	public UsuarioVO fromSecurityToUsuarioVO(Security security) {
		// TODO Plantilla de método auto-generado
		return mapToUsuarioVO(security.getUsernameToken().getUsername(),
				security.getUsernameToken().getPassword(), security
						.getUsernameToken().getOfficeCode());
	}

}
