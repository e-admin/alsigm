package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.Comparator;

/**
 * Clase que compara dos objetos
 * <code>ieci.tdw.ispac.ispaclib.bean.ItemBean</code>
 * para saber si son iguales.
 * Se considera que son iguales, cuando:
 * - name es el mismo
 */
public class StateComparator implements Comparator {

    /**
     * Constructor de la clase OperationsComparator.java
     *
     *
     */
    public StateComparator() {
        super();
    }
	/**
	 * Compara los objetos enviados como parametro para ordenar según los patrones
	 * definidos en la declaracion de la clase.
	 *
	 * @param object1 Object <code>ieci.tdw.ispac.ispaclib.bean.ItemBean</code> a comparar
	 * @param object2 Object <code>ieci.tdw.ispac.ispaclib.bean.ItemBean</code> a comparar
	 * @return int negativo indica que el primer Objeto precede al segundo
	 * 			   cero indica que son iguales
	 *             positivo indica que el primer Objeto sigue al segundo ÁÉÍ
	 */
	public int compare(Object object1, Object object2)
		throws ClassCastException {
		int resultado = -1;
		if (!(object1
			instanceof ieci.tdw.ispac.ispaclib.bean.ItemBean)
			|| !(object2
				instanceof ieci.tdw.ispac.ispaclib.bean.ItemBean)) {
			throw new ClassCastException();
		}
		ItemBean c1 = (ItemBean) object1;
		ItemBean c2 = (ItemBean) object2;
		String estado1 = null;
		String estado2 = null;
		if (c1 != null && c2 != null) {
			try {
				estado1 = c1.getString("ESTADO");
				estado2 = c2.getString("ESTADO");
				return (estado1).compareTo(estado2);
			} catch (ISPACException e) {
				System.out.println("Se ha lanzado un ISPACException en StateComparator: " +
						"No se puede comparar los objetos");
			}
		}
		return resultado;
	}
	/**
	 * Funcion sobreescrita del interfaz java.util.Comparator
	 * Indica si un objeto es "equal to" este
	 * Comparator(ieci.tdw.ispac.ispaclib.bean.ItemBean).
	 *
	 * @param object 	Objeto a igualar
	 * @return boolean 	true - si el objeto es "equal to" <code>ieci.tdw.ispac.ispaclib.bean.ItemBean</code>
	 * 					false - si el objeto no es "equal to" <code>ieci.tdw.ispac.ispaclib.bean.ItemBean</code>
	 */
	public boolean equals(Object object) {
		return (
			object
				instanceof ieci.tdw.ispac.ispaclib.bean.ItemBean);
	}

}
