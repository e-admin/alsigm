package se.geograficos;

/**
 * Información de una población.
 */
public class Poblacion extends Municipio implements Comparable {

	/** Código del municipio. */
	private String codigoMunicipio = null;

	/**
	 * Constructor.
	 */
	public Poblacion() {
	}

	/**
	 * Constructor.
	 * 
	 * @param codigoPais
	 *            Código del país.
	 * @param codigo
	 *            Código de la provincia.
	 * @param codigo
	 *            Código del municipio.
	 * @param codigo
	 *            Código de la población.
	 * @param nombre
	 *            Nombre de la población.
	 */
	public Poblacion(String codigoPais, String codigoProvincia,
			String codigoMunicipio, String codigo, String nombre) {
		setCodigoPais(codigoPais);
		setCodigoProvincia(codigoProvincia);
		setCodigoMunicipio(codigoMunicipio);
		setCodigo(codigo);
		setNombre(nombre);
	}

	/**
	 * Obtiene el código del municipio.
	 * 
	 * @return Código del municipio.
	 */
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	/**
	 * Establece el código del municipio.
	 * 
	 * @param codigoMunicipio
	 *            Código del municipio.
	 */
	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	/**
	 * Compara este objeto con el objeto especificado.
	 * 
	 * @return Número negativo, cero o positivo si este objeto es menor, igual o
	 *         mayor que el especificado, respectivamente.
	 */
	public int compareTo(Object poblacion) {
		return getNombre().compareTo(((Poblacion) poblacion).getNombre());
	}
}
