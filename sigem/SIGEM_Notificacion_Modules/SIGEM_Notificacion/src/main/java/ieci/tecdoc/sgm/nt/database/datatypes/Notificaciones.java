/*
 * Notificaciones.java
 *
 * Created on 18 de mayo de 2007, 12:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * Contenedor / Manejador de una coleccion de Notificacion 
 */
public class Notificaciones implements java.io.Serializable {
	
	private java.util.ArrayList notificaciones;

	public Notificaciones() {
		notificaciones = new java.util.ArrayList();
	}

	/**
	 * Devuelve el número de notificaciones de la colección.
	 * @return int Número de notificaciones de la colección.
	 */
	public int count() {
		return notificaciones.size();
	}

	/**
	 * Devuelve la notificacion de la posición indicada dentro de la colección
	 * @param index Posición la notificacion a recuperar.
	 * @return Expediente asociado a registro.
	 */
	public Notificacion get(int index) {
		return (Notificacion) notificaciones.get(index);
	}

	/**
	 * Añade una nueva notificacion a la colección.
	 * @param expediente Nueva notificacion a añadir.
	 */
	public void add(Notificacion nuevoValor_) {
		notificaciones.add(nuevoValor_);
	}
	
	public void addAll(Notificaciones notificaciones){
		this.notificaciones.addAll(notificaciones.notificaciones);
	}

}