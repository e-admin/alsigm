package es.ieci.tecdoc.archidoc.organization.vo;


/**
 * Interfaz para los contenedores de informacion de organos.
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public interface OrganizationOrgano
{

	/**
	 * Obtiene el identificador del organo.
	 * @return identificador del organo.
	 */
	String getId();

	/**
	 * Obtiene el nombre del organo.
	 * @return nombre del organo.
	 */
	String getNombre();

	/**
	 * Obtiene el codigo del organo.
	 * @return codigo del organo.
	 */
	String getCodigo();

	/**
	 * Obtiene el nivel jerarquico al que pertenece el organo.
	 * @return nivel jerarquico.
	 */
	int getNivel();

	/**
	 * Obtiene el identificador del organo padre al que pertenece el organo.
	 * @return identificador del organo.
	 */
	String getIdPadre();
}
