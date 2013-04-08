package ieci.tecdoc.sgm.certificacion.pdf.bean;

import java.util.ArrayList;

/**
 * Clase que almacena los datos correspondientes a la parte centrar del PDF
 * @author José Antonio Nogales
 */
public class DatosCentrales {
	String titulo;
	ArrayList datosCentrales;

	/**
	 * Constructor de la clase
	 * @param datosCentrales Listados de datos correspondientes a la parte central
	 */
	public DatosCentrales(ArrayList datosCentrales) {
		this.datosCentrales = datosCentrales;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public DatosCentrales() {
		this.titulo = "";
		this.datosCentrales = new ArrayList();
	}

	/**
	 * Métodos get y set de la clase
	 */
	public ArrayList getDatosCentrales() {
		return datosCentrales;
	}
	
	public int getSize(){
		if (datosCentrales != null)
			return datosCentrales.size();
		return 0;
	}

	public void setDatosCentrales(ArrayList datosCentrales) {
		this.datosCentrales = datosCentrales;
	}
	
	public DatoGenerico getDatoCentral(int index) {
		if (index < datosCentrales.size())
			return (DatoGenerico)datosCentrales.get(index);
		return null;
	}

	public void setDatoCentral(DatoGenerico datoCentral, int index) {
		if (index < this.datosCentrales.size())
		this.datosCentrales.set(index, datoCentral);
	}
	
	public void addDatoCentral(DatoGenerico datoCentral) {
		this.datosCentrales.add(datoCentral);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
