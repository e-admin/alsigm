package ieci.tecdoc.sgm.core.services.catastro;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de parcelas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Parcelas {
	
	private List parcelas;

	/**
	 * Constructor de clase
	 *
	 */
	public Parcelas() {
		parcelas = new ArrayList();
	}

	public List getParcelas() {
		return parcelas;
	}

	public void setParcelas(List parcelas) {
		this.parcelas = parcelas;
	}

	/**
	 * Devuelve el número de parcelas de la colección.
	 * @return int Número de parcelas de la colección.
	 */
	public int count() {
		return parcelas.size();
	}

	/**
	 * Devuelve la parcela de la posición indicada dentro de la colección
	 * @param index Posición de la parcela a recuperar.
	 * @return Parcela.
	 */
	public Object get(int index) {
		return (Parcela) parcelas.get(index);
	}

	/**
	 * Añade una nueva parcelas a la colección.
	 * @param parcelas Nueva parcelas a añadir.
	 */
	public void add(Parcela parcela) {
		parcelas.add(parcela);
	}

}
