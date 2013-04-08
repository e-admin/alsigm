/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo;

/**
 * 
 * Clase para almacenar el contexto del usuario: IP, Host, UserName, etc.. para
 * utilizarlos en los métodos que necesitan auditar los eventos.
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class AuditContext {

	/**
	 * Usuario conectado
	 */
	private String user;

	/**
	 * Identificador del usuario conectado
	 */
	private String userId;

	/**
	 * Host del usuario conectado
	 */
	private String userHost;

	/**
	 * IP del usuario conectado
	 */
	private String userIP;

	/**
	 * @return el user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user el user a fijar
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return el userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId el userId a fijar
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
