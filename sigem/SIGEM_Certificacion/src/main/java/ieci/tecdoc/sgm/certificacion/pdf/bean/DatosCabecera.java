package ieci.tecdoc.sgm.certificacion.pdf.bean;

import java.util.ArrayList;

/**
 * Clase que almacena los datos correspondientes a la cabecera del PDF
 * @author José Antonio Nogales
 */
public class DatosCabecera {
	String titulo;
	ArrayList datosCabecera;

	/**
	 * Constructor de la clase
	 * @param datosCabecera Listado de datos de la cabecera
	 */
	public DatosCabecera(ArrayList datosCabecera) {
		this.datosCabecera = datosCabecera;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public DatosCabecera() {
		this.titulo = "";
		this.datosCabecera = new ArrayList();
	}

	/**
	 * Métodos get y set de la clase
	 */
	public ArrayList getDatoCabecera() {
		return datosCabecera;
	}
	
	public int getSize(){
		if (datosCabecera != null)
			return datosCabecera.size();
		return 0;
	}

	public void setDatosCabecera(ArrayList datosCabecera) {
		this.datosCabecera = datosCabecera;
	}
	
	public DatoGenerico getDatoCabecera(int index) {
		if (index < datosCabecera.size())
			return (DatoGenerico)datosCabecera.get(index);
		return null;
	}

	public void setDatoCabecera(DatoGenerico datoCabecera, int index) {
		if (index < this.datosCabecera.size())
		this.datosCabecera.set(index, datoCabecera);
	}
	
	public void addDatoCabecera(DatoGenerico datoCabecera) {
		this.datosCabecera.add(datoCabecera);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
