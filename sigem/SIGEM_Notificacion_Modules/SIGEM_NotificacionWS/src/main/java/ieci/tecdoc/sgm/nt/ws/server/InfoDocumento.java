package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoDocumento extends RetornoServicio {

    private String	content;
    private String  extension;
    private String  guid;
    private String  mimeType;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
