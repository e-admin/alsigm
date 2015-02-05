package es.ieci.tecdoc.fwktd.audit.integration.business.vo;

/**
 *
 * Clase para almacenar el contexto del usuario: IP y Host... para
 * utilizarlos en los métodos que necesitan auditar los eventos.
 *
 * @author IECISA
 * @version $Revision$
 *
 */
public class AuditContext {
	/**
	 * Host del usuario conectado
	 */
	private String userHost;

	/**
	 * IP del usuario conectado
	 */
	private String userIP;


	/**
	 * @return el userHost
	 */
	public String getUserHost() {
		return userHost;
	}

	/**
	 * @param userHost el userHost a fijar
	 */
	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}

	/**
	 * @return el userIP
	 */
	public String getUserIP() {
		return userIP;
	}

	/**
	 * @param userIP el userIP a fijar
	 */
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}


}
