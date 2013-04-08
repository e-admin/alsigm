package xml.config;

/**
 * Clase que almacena la información de un
 * Sistema Gestor de Repositorio Electrónico.
 */
public class SistemaGestorRepositorioElectronico extends Sistema
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor.
	 */
	public SistemaGestorRepositorioElectronico()
	{
		super();
	}


	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		return toXML(indent, "Sistema_Gestor_Repositorio_Electronico");
	}
}
