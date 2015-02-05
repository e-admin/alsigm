package es.ieci.tecdoc.fwktd.applets.scan.vo;

public class ParametrosVO {	
	private String tipoDoc;
	private String resolucion;
	private String tamano;
	private String color;
	private String dummy;
	private String file;
	private String servlet;
	private boolean permitirGuardarComo;
	private boolean obligarPerfil;
	private boolean lanzadoComoApplet;
	
	public ParametrosVO(){	
		permitirGuardarComo=true;
		obligarPerfil=true;
		lanzadoComoApplet=false;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getDummy() {
		return dummy;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public void setServlet(String servlet) {
		this.servlet = servlet;
	}

	public String getServlet() {
		return servlet;
	}

	public boolean isPermitirGuardarComo() {
		return permitirGuardarComo;
	}

	public void setPermitirGuardarComo(boolean permitirGuardarComo) {
		this.permitirGuardarComo = permitirGuardarComo;
	}

	public boolean isLanzadoComoApplet() {
		return lanzadoComoApplet;
	}

	public void setLanzadoComoApplet(boolean lanzadoComoApplet) {
		this.lanzadoComoApplet = lanzadoComoApplet;
	}

	public boolean isObligarPerfil() {
		return obligarPerfil;
	}

	public void setObligarPerfil(boolean obligarPerfil) {
		this.obligarPerfil = obligarPerfil;
	}
}
