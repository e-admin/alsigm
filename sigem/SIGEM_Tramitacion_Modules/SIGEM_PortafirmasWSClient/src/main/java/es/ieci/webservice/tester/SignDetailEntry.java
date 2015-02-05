package es.ieci.webservice.tester;

public class SignDetailEntry {

	
	String author;

	boolean isFirmado=false;

	String signDate;
	
	String integrity;
	

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
