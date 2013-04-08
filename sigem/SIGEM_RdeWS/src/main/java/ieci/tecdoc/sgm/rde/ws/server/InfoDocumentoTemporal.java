package ieci.tecdoc.sgm.rde.ws.server;

public class InfoDocumentoTemporal extends InfoDocumento {

	private String sessionId; 
	private String timestamp;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
