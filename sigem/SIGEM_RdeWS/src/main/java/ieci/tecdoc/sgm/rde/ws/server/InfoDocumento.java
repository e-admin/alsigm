package ieci.tecdoc.sgm.rde.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoDocumento extends RetornoServicio{

	private String guid;
	private String contenidoB64;
	private String extension;
	private String mimeType;
	private String hash;
	
	public String getContenidoB64() {
		return contenidoB64;
	}
	public void setContenidoB64(String contenido) {
		this.contenidoB64 = contenido;
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
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
