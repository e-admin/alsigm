package se.geograficos;

/**
 * Información de una provincia.
 */
public class Provincia extends Pais {

	/** Código del país. */
	private String codigoPais = null;

	/**
	 * Constructor.
	 */
	public Provincia() {
	}

	/**
	 * Constructor.
	 * 
	 * @param codigoPais
	 *            Código del país.
	 * @param codigo
	 *            Código de la provincia.
	 * @param nombre
	 *            Nombre de la provincia.
	 */
	public Provincia(String codigoPais, String codigo, String nombre) {
		setCodigoPais(codigoPais);
		setCodigo(codigo);
		setNombre(nombre);
	}

	/**
	 * Obtiene el código del país.
	 * 
	 * @return Código del país.
	 */
	public String getCodigoPais() {
		return codigoPais;
	}

	/**
	 * Establece el código del país.
	 * 
	 * @param codigoPais
	 *            Código del país.
	 */
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
}
