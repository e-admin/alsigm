/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * 
 * Clase base, abstracta, para agrupar los atributos básicos del usuario para los eventos de InvesFlow
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public abstract class IspacAuditAbstractBasicUserEventVO extends IspacAuditEventVO{

	private String user;

	private String idUser;

	private String userIp;

	private String userHostName;

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
	 * @return el idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser el idUser a fijar
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return el userIp
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * @param userIp el userIp a fijar
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	/**
	 * @return el userHostName
	 */
	public String getUserHostName() {
		return userHostName;
	}

	/**
	 * @param userHostName el userHostName a fijar
	 */
	public void setUserHostName(String userHostName) {
		this.userHostName = userHostName;
	}
	
	

}
