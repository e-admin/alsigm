package se.geograficos;

/**
 * Información de un municipio.
 */
public class Municipio extends Provincia {

	/** Código de la provincia. */
	private String codigoProvincia = null;

	/**
	 * Constructor.
	 */
	public Municipio() {
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
	 * @param nombre
	 *            Nombre del municipio.
	 */
	public Municipio(String codigoPais, String codigoProvincia, String codigo,
			String nombre) {
		setCodigoPais(codigoPais);
		setCodigoProvincia(codigoProvincia);
		setCodigo(codigo);
		setNombre(nombre);
	}

	/**
	 * Obtiene el código de la provincia.
	 * 
	 * @return Código de la provincia.
	 */
	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	/**
	 * Establece el código de la provincia.
	 * 
	 * @param codigoProvincia
	 *            Código de la provincia.
	 */
	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}
}
