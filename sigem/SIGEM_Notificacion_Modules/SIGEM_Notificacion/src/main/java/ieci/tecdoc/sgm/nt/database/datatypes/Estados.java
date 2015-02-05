/*
 * Documentos.java
 *
 * Created on 23 de mayo de 2007, 15:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author Usuario
 */
public class Estados  implements java.io.Serializable {
	
	private java.util.ArrayList documentos;

	public Estados() {
		documentos = new java.util.ArrayList();
	}

	/**
	 * Devuelve el número de notificaciones de la colección.
	 * @return int Número de notificaciones de la colección.
	 */
	public int count() {
		return documentos.size();
	}

	/**
	 * Devuelve la notificacion de la posición indicada dentro de la colección
	 * @param index Posición la notificacion a recuperar.
	 * @return Expediente asociado a registro.
	 */
	public Estado get(int index) {
		return (Estado) documentos.get(index);
	}

	/**
	 * Añade una nueva notificacion a la colección.
	 * @param expediente Nueva notificacion a añadir.
	 */
	public void add(Estado nuevoValor_) {
		documentos.add(nuevoValor_);
	}
}
