package transferencias.actions;

import gcontrol.vos.UsuarioVO;

import common.util.StringUtils;

/**
 * Objeto de presentacion para gestor de transferencias
 */
public class GestorPO extends UsuarioVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String getNombreYApellidos() {
		StringBuffer nombreYAPellidos = new StringBuffer();

		if (StringUtils.isNotBlank(getNombre())) {
			nombreYAPellidos.append(getNombre());
		}

		if (StringUtils.isNotBlank(getApellidos())) {
			if (nombreYAPellidos.length() > 0) {
				nombreYAPellidos.append(" ");
			}
			nombreYAPellidos.append(getApellidos());
		}

		return nombreYAPellidos.toString();
	}
}