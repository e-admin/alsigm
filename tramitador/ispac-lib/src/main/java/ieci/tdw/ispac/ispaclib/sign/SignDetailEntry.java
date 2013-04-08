package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.ISignAPI;

/**
 * Detalle de una firma de un documento firmado.
 * 
 * @author antoniomaria_sanchez at ieci.es
 * @since 28/01/2009
 */

public class SignDetailEntry {

	
	String author;

	boolean isFirmado=false;

	String signDate;
	
	String integrity=ISignAPI.INTEGRIDAD_STRANGER;
	

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}



	public boolean isFirmado() {
		return isFirmado;
	}

	public void setFirmado(boolean isFirmado) {
		this.isFirmado = isFirmado;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getIntegrity() {
		return integrity;
	}

	public void setIntegrity(String integrity) {
		this.integrity = integrity;
	}

	public String toString() {
		return "author: " + author + ", state: " + isFirmado
				+ ", signDate: " + signDate;
	}

}
