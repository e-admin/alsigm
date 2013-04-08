package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;
import java.util.List;

public class FicherosHito {

	private String guidHito;
	
	private List ficheros;

	/**
	 * Constructor de clase
	 *
	 */
	public FicherosHito() {
		ficheros = new ArrayList();
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return ficheros.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public FicheroHito get(int index) {
		return (FicheroHito) ficheros.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param fichero Nuevo interesado a añadir.
	 */
	public void add(FicheroHito fichero) {
		ficheros.add(fichero);
	}

	public List getFicheros() {
		return ficheros;
	}

	public void setFicheros(List ficheros) {
		this.ficheros = ficheros;
	}

	public String getGuidHito() {
		return guidHito;
	}

	public void setGuidHito(String guidHito) {
		this.guidHito = guidHito;
	}

	
}
