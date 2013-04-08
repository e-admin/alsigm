package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;

/**
 * Contenedor de interesados asociados a un expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */
public class Pagos {
	
	private ArrayList pagos;

	/**
	 * Constructor de clase
	 *
	 */
	public Pagos() {
		pagos = new ArrayList();
	}

	public ArrayList getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList pagos) {
		this.pagos = pagos;
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return pagos.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public Object get(int index) {
		return (Pago) pagos.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param fichero Nuevo interesado a añadir.
	 */
	public void add(Pago fichero) {
		pagos.add(fichero);
	}

}
