package gcontrol.model;

import java.util.List;

import org.apache.log4j.Logger;

import se.instituciones.InfoOrgano;

import common.Constants;

/**
 * Utilidad para la obtención de datos de órgano
 */
public class NombreOrganoFormat {

	private static final Logger logger = Logger
			.getLogger(NombreOrganoFormat.class);

	/**
	 * Obtener el nombre largo de un órgano.
	 * 
	 * @param organoUsuario
	 *            Órgano.
	 * @param organosAntecesores
	 *            Lista de órganos antecesores.
	 * @return Nombre largo del órgano.
	 */
	public static final String formatearNombreLargo(InfoOrgano organoUsuario,
			List organosAntecesores) {
		StringBuffer nombreLargo = new StringBuffer();

		// Añadir el nombre del órgano del usuario
		if (organoUsuario != null) {
			nombreLargo.append(organoUsuario.getNombre());
		}

		// Añadir el nombre de los órganos antecesores
		if (organosAntecesores != null) {
			InfoOrgano antecesor;
			for (int i = organosAntecesores.size() - 1; i >= 0; i--) {
				antecesor = (InfoOrgano) organosAntecesores.get(i);
				nombreLargo.append(Constants.SEPARADOR_ANTECESORES_ORGANO);
				nombreLargo.append(antecesor.getNombre());
			}
		}
		if (logger.isDebugEnabled())
			logger.debug("Nombre largo del \u00F3rgano: "
					+ nombreLargo.toString());
		return nombreLargo.toString();

	}
}