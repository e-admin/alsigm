package ieci.tecdoc.sgm.core.services.consulta;

import java.util.ArrayList;
import java.util.List;

public class Expedientes {

	private List expedientes;
	
	public Expedientes(){
		expedientes = new ArrayList();
	}
	
	public int count() {
		return expedientes.size();
	}

	/**
	 * Devuelve el expediente de la posición indicada dentro de la colección
	 * @param index Posición del expediente a recuperar.
	 * @return Expediente asociado a registro.
	 */
	public Expediente get(int index) {
		return (Expediente)expedientes.get(index);
	}

	/**
	 * Añade un nuevo expediente a la colección.
	 * @param expediente Nuevo expediente a añadir.
	 */
	public void add(Expediente expediente) {
		expedientes.add(expediente);
	}

	public List getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List expedientes) {
		this.expedientes = expedientes;
	}

}
