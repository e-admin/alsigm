package ieci.tecdoc.sgm.certificacion.pdf.bean;

import java.util.ArrayList;

/**
 * Clase que almacena los datos correspondientes a la certificación del PDF
 * @author José Antonio Nogales
 */
public class DatosCertificacion {
	String titulo;
	ArrayList datosCertificacion;

	/**
	 * Constructor de la clase
	 * @param datosCertificacion Listado de datos de la certificación
	 */
	public DatosCertificacion(ArrayList datosCertificacion) {
		this.datosCertificacion = datosCertificacion;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public DatosCertificacion() {
		this.titulo = "";
		this.datosCertificacion = new ArrayList();
	}

	/**
	 * Métodos get y set de la clase
	 */
	public ArrayList getDatoCertificacion() {
		return datosCertificacion;
	}
	
	public int getSize(){
		if (datosCertificacion != null)
			return datosCertificacion.size();
		return 0;
	}

	public void setDatosCertificacion(ArrayList datosCertificacion) {
		this.datosCertificacion = datosCertificacion;
	}
	
	public DatoGenerico getDatoCertificacion(int index) {
		if (index < datosCertificacion.size())
			return (DatoGenerico)datosCertificacion.get(index);
		return null;
	}

	public void setDatoCertificacion(DatoGenerico datoCertificacion, int index) {
		if (index < this.datosCertificacion.size())
		this.datosCertificacion.set(index, datoCertificacion);
	}
	
	public void addDatoCertificacion(DatoGenerico datoCertificacion) {
		this.datosCertificacion.add(datoCertificacion);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
