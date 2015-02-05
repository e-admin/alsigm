package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;

/**
 * Contenedor de interesados asociados a un expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */
public class Notificaciones{
	
	private ArrayList notificaciones;

	/**
	 * Constructor de clase
	 *
	 */
	public Notificaciones() {
		notificaciones = new ArrayList();
	}

	public ArrayList getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(ArrayList notificaciones) {
		this.notificaciones = notificaciones;
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return notificaciones.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public Object get(int index) {
		return (Notificacion) notificaciones.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param fichero Nuevo interesado a añadir.
	 */
	public void add(Notificacion fichero) {
		notificaciones.add(fichero);
	}

}
