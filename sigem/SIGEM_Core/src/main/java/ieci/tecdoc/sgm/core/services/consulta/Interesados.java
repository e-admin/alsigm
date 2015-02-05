package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;

public class Interesados {

	private ArrayList interesados;

	/**
	 * Constructor de clase
	 */
	public Interesados() {
		interesados = new ArrayList();
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return interesados.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public Interesado get(int index) {
		return (Interesado) interesados.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param interesado Nuevo interesado a añadir.
	 */
	public void add(Interesado interesado) {
		interesados.add(interesado);
	}

	public ArrayList getInteresados() {
		return interesados;
	}

	public void setInteresados(ArrayList interesados) {
		this.interesados = interesados;
	}

}
