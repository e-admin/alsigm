package deposito.db;

import deposito.vos.InformeOcupacion;
import deposito.vos.OcupacionElementoDeposito;

/**
 * Interface con los métodos de acceso a datos referentes a la ocupación de los
 * fondos físicos
 */
public interface IOcupacionDBEntity {
	/**
	 * Recupera los datos de ocupación de una de las ubicaciones del fondo
	 * físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador de la ubicación
	 * @return Datos de ocupación del elemento
	 */
	public InformeOcupacion getInformeOcupacionDeposito(String idDeposito);

	/**
	 * Recupera los datos de ocupación de un elemento no asignable del fondo
	 * físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador del elemento no asignable
	 * @return Datos de ocupación del elemento
	 */
	public InformeOcupacion getInformeOcupacionElementoNoAsignable(
			String idNoAsignable);

	// /**
	// * Recupera los datos de ocupación de un elemento asignable del fondo
	// físico
	// * @param idElementoAsignable Identificador del elemento asignable
	// * @return Datos de ocupación del elemento
	// */
	// public InformeOcupacion getInformeOcupacionElementoAsignable(String
	// idAsignable);

	/**
	 * Recupera la ocupación de los huecos del formato indicado para una
	 * ubicación del fondo físico
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación de la ubicación
	 */
	public OcupacionElementoDeposito getOcupacionUbicacion(String idUbicacion,
			String idFormato);

	/**
	 * Recupera la ocupación de los huecos del formato indicado para un elemento
	 * no asignable del fondo físico
	 * 
	 * @param idUbicacion
	 *            Identificador de elemento no asignable
	 * @param tipoElemento
	 *            Tipo de elemento no asignable
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación del elemento no asignable
	 */
	public OcupacionElementoDeposito getOcupacionElementoNoAsignable(
			String idElemento, String tipoElemento, String idFormato);

	/**
	 * Recupera la ocupación de los huecos del formato indicado para un elemento
	 * asignable del fondo físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador de elemento asignable
	 * @param tipoElemento
	 *            Tipo de elemento asignable
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación del elemento asignable
	 */
	public OcupacionElementoDeposito getOcupacionElementoAsignable(
			String idElementoAsignable, String tipoElemento, String idFormato);

}