package ieci.tecdoc.sgm.rde.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ContenedorDocumento extends RetornoServicio {

	   private String guid;
	   private String contentB64;
	   private String hash;
	   private String extension;
	   private String timestamp;
	   private String contentSize;
	   
	public String getContentB64() {
		return contentB64;
	}
	public void setContentB64(String contentB64) {
		this.contentB64 = contentB64;
	}
	public String getContentSize() {
		return contentSize;
	}
	public void setContentSize(String contentSize) {
		this.contentSize = contentSize;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	   
}
