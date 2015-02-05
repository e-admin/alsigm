package auditoria.decorators;

import org.displaytag.decorator.TableDecorator;

import auditoria.vos.TrazaVO;

import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;

/**
 * Decorador de pistas de auditoria. Devuelve la salida adecuada para ciertas
 * columnas.
 */
public class ViewPistaDecorator_NOSEUSA extends TableDecorator {

	/**
	 * Obtiene el nombre del módulo a partir de su identificador.
	 * 
	 * @return String nombre del mópdulo
	 */
	public String getModulo() {
		TrazaVO traza = (TrazaVO) getCurrentRowObject();

		return ArchivoModules.getModuleName(traza.getModulo());
	}

	/**
	 * Obtiene el nombre de la accion mediante su identificador
	 * 
	 * @return Nombre de la accion
	 */
	public String getAccion() {
		TrazaVO traza = (TrazaVO) getCurrentRowObject();

		return generateIDLink(ArchivoActions.getActionName(traza.getAccion()),
				traza);
	}

	public String getIdUsuario() {
		TrazaVO traza = (TrazaVO) getCurrentRowObject();

		return traza.getIdUsuario();
	}

	/**
	 * Genera un enlace al detalle de una traza
	 * 
	 * @param texto
	 *            Texto a poner en el enlace
	 * @param traza
	 *            Traza a la que se va enlazar
	 * @return Enlace html generado
	 */
	private String generateIDLink(String texto, TrazaVO traza) {
		StringBuffer href = new StringBuffer();

		href.append("<a href=\"../../action/auditoriaBuscar?method=detail&id=");
		href.append(traza.getId());
		href.append("\" styleClass=\"etiquetaAzul12Bold\" >");
		href.append(texto);
		href.append("</a>");

		return href.toString();
	}
}
