/*
 * Created on 17-feb-2005
 *
 */
package transferencias.model;

import java.util.ArrayList;
import java.util.HashMap;

import transferencias.EstadoREntregaConstants;

/**
 * Enumeracion con los diferentes estados por los que puede pasar una relacion
 * de entrega
 */
public class EstadoREntrega extends EstadoREntregaConstants {

	public static int[] getEstadosREntregaExcluyendo(
			EstadoREntregaConstants[] estadosAExcluir) {
		HashMap hashEstadosAExcluir = new HashMap();
		for (int i = 0; i < estadosAExcluir.length; i++)
			hashEstadosAExcluir.put(
					new Integer(estadosAExcluir[i].getIdentificador()),
					estadosAExcluir[i]);

		ArrayList listaEstadosADevolver = new ArrayList();
		for (int i = 0; i < estadosREntrega.length; i++) {
			if (!hashEstadosAExcluir.containsValue(estadosREntrega[i])) {
				listaEstadosADevolver.add(estadosREntrega[i]);
			}
		}
		int[] estadosADevolver = new int[listaEstadosADevolver.size()];
		for (int i = 0; i < estadosADevolver.length; i++)
			estadosADevolver[i] = ((EstadoREntregaConstants) listaEstadosADevolver
					.get(i)).getIdentificador();

		return estadosADevolver;

	}

}