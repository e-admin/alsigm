package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.Locale;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class ConfiguracionUsuarioVO extends BaseConfiguracionUsuarioVO {

	private static final long serialVersionUID = 4908908212908502473L;

	/**
	 * locale con el que se conecta el usuario
	 */
	protected Locale locale;

	/**
	 * Oficina a la que se conecta el usuario.
	 */
	protected OficinaVO oficina;

	/**
	 * Perfil del usuario.
	 */
	protected String profile;

	/**
	 * identificador de la identidad con la que se conecta el usuario (soporte
	 * multiEntidad)
	 */
	protected String idEntidad;

	/**
	 * identificador de session
	 */
	protected String sessionID;

	public OficinaVO getOficina() {
		if (null == oficina) {
			oficina = new OficinaVO();
		}
		return oficina;
	}

	public void setOficina(OficinaVO oficina) {
		this.oficina = oficina;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

}
