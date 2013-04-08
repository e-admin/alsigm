package ieci.tecdoc.sgm.core.services.certificacion;

public class RetornoPdf {
	
	private byte[] contenido;
	private String identificador;
	
	public byte[] getContenido() {
		return contenido;
	}
	
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
}
