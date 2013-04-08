/**
 * 
 */
package ieci.tecdoc.sgm.manager.ecologo;

/**
 * 
 * Manager para obtener el logo del MINETUR
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public interface LogoManager {

	/**
	 * Devuelve el HTML para mostrar el logo del MINETUR
	 * 
	 * @param param
	 * @return html con el logo
	 */
	public String render(String idApp, String serverName);

}
