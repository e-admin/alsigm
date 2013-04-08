package se.geograficos;

import java.util.Comparator;

public class ElementoGeograficoComparator implements Comparator {

	private static ElementoGeograficoComparator instance;
	private static int tipoBusqueda;

	/**
	 * Método constructor.
	 */
	private ElementoGeograficoComparator() {

	}

	/**
	 * Devuelve una referencia a la única instancia del comparador.
	 * 
	 * @return Comparator
	 */
	public static ElementoGeograficoComparator getInstance(int tipoBusqueda) {
		if (instance == null)
			instance = new ElementoGeograficoComparator();
		ElementoGeograficoComparator.tipoBusqueda = tipoBusqueda;
		return instance;
	}

	/**
	 * Compara dos ElementoGeograficoVO.
	 * 
	 * @param a
	 *            ElementoGeograficoVO <code>a</code>.
	 * @param b
	 *            ElementoGeograficoVO <code>b</code>.
	 * @return Devolverá <code>1</code> si el valor del ElementoGeograficoVO
	 *         <code>a</code> es mayor que el valor del ElementoGeograficoVO
	 *         <code>b</code>, <code>-1</code> si el valor del
	 *         ElementoGeograficoVO <code>b</code> es mayor que el valor del
	 *         ElementoGeograficoVO <code>a</code> o <code>0</code> si ambos
	 *         ElementoGeograficoVO son iguales.
	 */
	public int compare(Object obj1, Object obj2) {
		int res = 0;
		ElementoGeograficoVO a = (ElementoGeograficoVO) obj1;
		ElementoGeograficoVO b = (ElementoGeograficoVO) obj2;
		switch (tipoBusqueda) {
		case GestorGeograficos.PAIS:
			res = a.getNamePais().compareTo(b.getNamePais());
			break;
		case GestorGeograficos.PROVINCIA:
			res = a.getNameProvincia().compareTo(b.getNameProvincia());
			break;
		case GestorGeograficos.MUNICIPIO:
			res = a.getNameMunicipio().compareTo(b.getNameMunicipio());
			break;
		case GestorGeograficos.POBLACION:
			res = a.getNamePoblacion().compareTo(b.getNamePoblacion());
			break;
		}
		return res;
	}
}