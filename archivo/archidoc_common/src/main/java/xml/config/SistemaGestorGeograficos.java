package xml.config;

/**
 * Clase que almacena la información de un
 * Sistema Gestor de Geográficos.
 */
public class SistemaGestorGeograficos extends Sistema
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor.
	 */
	public SistemaGestorGeograficos()
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
		return toXML(indent, "Sistema_Gestor_Geograficos");
	}
}
