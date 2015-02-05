package es.ieci.tecdoc.isicres.api.audit.business.vo.events;

public abstract class IsicresAuditAbstractBasicUserEventVO extends IsicresAuditEventVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1667453792695576792L;
	
	private String user;
	
	private String idUser;
	
	private String userIp;
	
	private String userHostName;
	
	private String idOficina;
	
	private String oficina;

	public String getUser() {
		return user;
	}

	public String getUserIp() {
		return userIp;
	}

	public String getUserHostName() {
		return userHostName;
	}

	public String getOficina() {
		return oficina;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setUserHostName(String userHostName) {
		this.userHostName = userHostName;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getIdOficina() {
		return idOficina;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public void setIdOficina(String idOficina) {
		this.idOficina = idOficina;
	}
	
	
	
	
	
	

}
