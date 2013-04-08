package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: InfoDocumento.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoDocumento extends RetornoServicio {

	 private String guid;
	 private String contentB64;
	 private String extension;
	 private String mimeType;
	 
	public String getContent() {
		return contentB64;
	}
	public void setContent(String content) {
		this.contentB64 = content;
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

	 
}
