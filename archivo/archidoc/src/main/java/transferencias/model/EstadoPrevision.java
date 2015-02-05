/*
 * Created on 10-feb-2005
 *
 */
package transferencias.model;

import java.util.ArrayList;
import java.util.HashMap;

import transferencias.EstadoPrevisionConstants;

/**
 * Enumeracion con los distintos estados por los que puede pasar una prevision
 */
public class EstadoPrevision extends EstadoPrevisionConstants {

	public static int[] getEstadosPrevisionExcluyendo(
			EstadoPrevisionConstants[] estadosAExcluir) {
		HashMap hashEstadosAExcluir = new HashMap();
		for (int i = 0; i < estadosAExcluir.length; i++)
			hashEstadosAExcluir.put(estadosAExcluir[i].getNombre(),
					estadosAExcluir[i]);

		ArrayList listaEstadosADevolver = new ArrayList();
		for (int i = 0; i < estadosPrevision.length; i++) {
			if (!hashEstadosAExcluir.containsValue(estadosPrevision[i])) {
				listaEstadosADevolver.add(estadosPrevision[i]);
			}
		}
		int[] estadosADevolver = new int[listaEstadosADevolver.size()];
		for (int i = 0; i < estadosADevolver.length; i++)
			estadosADevolver[i] = ((EstadoPrevision) listaEstadosADevolver
					.get(i)).getIdentificador();

		return estadosADevolver;

	}
}
