package ieci.tecdoc.sgm.autenticacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class SesionUsuario extends RetornoServicio{

   private String sessionId;
   private String hookId;
   private String loginDate;
   private InfoUsuario sender;
	   
	public String getHookId() {
		return hookId;
	}
	public void setHookId(String hookId) {
		this.hookId = hookId;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public InfoUsuario getSender() {
		return sender;
	}
	public void setSender(InfoUsuario sender) {
		this.sender = sender;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	   
}
