package es.ieci.tecdoc.isicres.api.compulsa.business.vo;

import java.io.Serializable;

public class DocumentoFisicoCompulsaVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4071364965375459010L;

	protected String name;

	protected String extension;

	private byte[] content;
	
	private String hashAlg;
	
	private String hash;

	/**
	 * cadena que identifica donde obtener el recurso fisico
	 * 
	 */
	private java.lang.String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public java.lang.String getLocation() {
		return location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

	public String getHashAlg() {
		return hashAlg;
	}

	public void setHashAlg(String hashAlg) {
		this.hashAlg = hashAlg;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}


}
