package ieci.tecdoc.sgm.certificacion.pdf.bean;

import java.util.ArrayList;

/**
 * Clase que almacena una entrada del fichero de propiedades de configuración del PDF
 * @author José Antonio Nogales
 */
public class DatoGenerico {
	String nombre;
	String etiqueta;
	ArrayList datos;
	int orden;
	int linea;
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public DatoGenerico() {
		this.etiqueta = null;
		this.datos = null;
		this.nombre = null;
		this.orden = -1;
		this.linea = -1;
	}
	
	/**
	 * Constructor de la clase
	 * @param etiqueta Etiqueta del dato
	 * @param nombre Nombre del dato
	 * @param datos Lista de valores (en el caso de rutas pueden encontrarse path.1, path.2, ...)
	 * @param orden Orden del elemento dentro del bloque del PDF
	 * @param linea Número de linea en la que mostrar el dato
	 */
	public DatoGenerico(String etiqueta, String nombre, ArrayList datos, int orden, int linea) {
		this.etiqueta = etiqueta;
		this.datos = datos;
		this.nombre = nombre;
		this.orden = orden;
		this.linea = linea;
	}

	/**
	 * Métodos get y set de la clase
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public ArrayList getDatos() {
		return datos;
	}

	public void setDatos(ArrayList datos) {
		this.datos = datos;
	}
	
	public String getDato(int index) {
		if (index < datos.size())
			return (String)datos.get(index);
		return null;
	}

	public void setDato(String dato, int index) {
		if (index < this.datos.size())
			this.datos.set(index, dato);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
