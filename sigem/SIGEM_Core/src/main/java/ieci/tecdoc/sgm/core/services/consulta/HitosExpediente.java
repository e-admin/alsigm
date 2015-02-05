package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;
import java.util.List;


public class HitosExpediente {

	private List hitosExpediente;
	/**
	 * Constructor de clase
	 */

	public HitosExpediente() {
		hitosExpediente = new ArrayList();
	}

	/**
	 * Devuelve el número de hitos de la colección.
	 * @return int Número de hitos de la colección.
	 */
	public int count() {
		return hitosExpediente.size();
	}

	/**
	 * Devuelve el hito de la posición indicada dentro de la colección
	 * @param index Posición del hito a recuperar.
	 * @return Hito asociado a registro.
	 */
	public HitoExpediente get(int index) {
		return (HitoExpediente)hitosExpediente.get(index);
	}

	/**
	 * Añade un nuevo hito a la colección.
	 * @param hito Nuevo expediente a añadir.
	 */
	public void add(HitoExpediente hito) {
		hitosExpediente.add(hito);
	}

	public List getHitosExpediente() {
		return hitosExpediente;
	}

	public void setHitosExpediente(List hitosExpediente) {
		this.hitosExpediente = hitosExpediente;
	}

}
