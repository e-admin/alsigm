package auditoria.decorators;

import java.util.Locale;

import javax.servlet.ServletRequest;

import org.displaytag.decorator.TableDecorator;

import auditoria.ArchivoLogLevels;
import auditoria.vos.CritUsuarioVO;

/**
 * Decorador de grupos de auditoria. Devuelve la salida adecuada para ciertas
 * columnas.
 */
public class ViewGrupoDecorator extends TableDecorator {
	/**
	 * Obtiene el nombre del nivel a partir de su identificador
	 * 
	 * @return Nombre del nivel
	 */
	public String getNivel() {
		CritUsuarioVO usuario = (CritUsuarioVO) getCurrentRowObject();

		ServletRequest request = getPageContext().getRequest();
		Locale locale = null;
		if (request != null) {
			locale = request.getLocale();
		}
		return ArchivoLogLevels.getLogLevelName(locale, usuario.getNivel());
	}
}
