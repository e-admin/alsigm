package ieci.tecdoc.sgm.core.services.consulta;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de interesados asociados a un expediente.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Subsanaciones  {
	
	private ArrayList subsanaciones;

	/**
	 * Constructor de clase
	 *
	 */
	public Subsanaciones() {
		subsanaciones = new ArrayList();
	}

	public ArrayList getSubsanaciones() {
		return subsanaciones;
	}

	public void setSubsanaciones(ArrayList subsanaciones) {
		this.subsanaciones = subsanaciones;
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return subsanaciones.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public Object get(int index) {
		return (Subsanacion) subsanaciones.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param fichero Nuevo interesado a añadir.
	 */
	public void add(Subsanacion fichero) {
		subsanaciones.add(fichero);
	}

}
