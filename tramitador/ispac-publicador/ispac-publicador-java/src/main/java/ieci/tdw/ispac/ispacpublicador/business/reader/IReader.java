/*
 * Created on 04-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.reader;

import java.util.List;

/**
 * Lector que se encarga de obtener nuevos hitos aparecidos
 * en un sistema. 
 * @author Ildefonso Noreña
 *
 */
public interface IReader {

	/**
	 * Obtiene la lista de nuevos hitos, tomando como filtro (límite inferior) 
	 * el valor <code>'infLimitId'</code>
	 * @param infLimitId Límite inferior de identifcador de Hito.
	 * @return Lista de hitos. 
	 */
	public List getMilestoneList(int infLimitId);

	/**
	 * Obtiene el identificador del sistema externo sobre el que se realiza la
	 * lectura de nuevos hitos.
	 * @return Identificador del sistema externo.
	 */
	public String getIdSistemExterno();

}
