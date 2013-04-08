package es.ieci.tecdoc.isicres.legacy.ws.manager;

import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistributionsResponse;

/**
 * 
 * @author IECISA
 * 
 */
public interface ISWebServiceDistributionsManager {

	/**
	 * 
	 * @param numReg
	 * @param security
	 */
	void acceptDistribution(String numReg, Security security);

	/**
	 * 
	 * @param parameters
	 * @param security
	 * @return
	 */
	WSAcceptDistributionExResponse acceptDistributionExResponse(WSAcceptDistributionEx parameters,
			Security security);

	/**
	 * 
	 * @param numReg
	 * @param security
	 */
	void archiveDistribution(String numReg, Security security);

	/**
	 * 
	 * @param state
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadInputDistributions(int state, int initValue,
			int size, Security security);

	/**
	 * 
	 * @param state
	 * @param userId
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadInputDistributionsByUserId(int state,
			int userId, int initValue, int size, Security security);

	/**
	 * 
	 * @param state
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadOutputDistributions(int state, int initValue,
			int size, Security security);

	/**
	 * 
	 * @param state
	 * @param userId
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadOutputDistributionsByUserId(int state, int userId, int initValue,
			int size, Security security);

	/**
	 * Permite obtener el contenido de la bandeja de distribución de entrada de un usuario para una
	 * condición SQL dada.
	 * 
	 * @param query	Consulta sql
	 * @param initValue	Inicio de la lista
	 * @param size	Tamaño de la lista
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadInputDistributionsByCondition(String query, int initValue, int size,
			Security security);
	
	/**
	 * Permite obtener el contenido de la bandeja de distribución de salida de un usuario para una
	 * condición SQL dada.
	 * 
	 * @param query	Consulta sql
	 * @param initValue	Inicio de la lista
	 * @param size	Tamaño de la lista
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadOutputDistributionsByCondition(String query, int initValue, int size,
			Security security);
	
	/**
	 * Permite obtener el contenido de la bandeja de distribución para una condición SQL dada.
	 * 
	 * No tiene en cuenta el usuario.
	 * 
	 * @param query	Consulta sql
	 * @param initValue	Inicio de la lista
	 * @param size	Tamaño de la lista
	 * @param security
	 * @return
	 */
	WSDistributionsResponse loadDistributionsByConditionEx(
			String query, int initValue, int size, Security security);

	/**
	 * 
	 * @param numReg
	 * @param codeDest
	 * @param security
	 */
	void redistributeInputDistribution(String numReg, String codeDest,
			Security security);

	/**
	 * 
	 * @param numReg
	 * @param codeDest
	 * @param security
	 */
	void redistributeOutputDistribution(String numReg, String codeDest,
			Security security);

	/**
	 * 
	 * @param numReg
	 * @param matter
	 * @param security
	 */
	void rejectDistribution(String numReg, String matter, Security security);

	/**
	 * Realiza una distribución manual a partir del origen y el destino
	 * 
	 * @param numReg
	 *            Número de registro
	 * @param bookId
	 *            Identificador del libro
	 * @param senderType
	 *            Tipo del origen (usuario, departamento o grupo)
	 * @param senderId
	 *            Identificador del usuario, grupo o departamento del origen
	 * @param senderName
	 *            Nombre del origen
	 * @param destinationType
	 *            Tipo del destino (usuario, departamento o grupo)
	 * @param destinationId
	 *            Identificador del destino
	 * @param destinationName
	 *            Nombre del destino
	 * @param matter
	 *            Asunto de la distribución
	 * @param security
	 *            Seguridad
	 * @return Devuelve la distribución creada
	 */
	WSDistribution registerDistribute(String numReg, int bookId, int senderType, int senderId,
			String senderName, int destinationType, int destinationId, String destinationName,
			String matter, Security security);

	/**
	 * Realiza una redistribución manual de una distribución que se encuentra en
	 * la bandeja de entrada. 
	 * 
	 * La redistribución se dirige hacia el destino indicando el id, tipo y nombre.
	 * 
	 * No modifica el destino del registro.
	 * 
	 * @param numReg
	 *            Número de registro
	 * @param destinationType
	 *            Tipo del destino (usuario, departamento o grupo)
	 * @param destinationId
	 *            Identificador del destino
	 * @param destinationName
	 *            Nombre del destino
	 * @param security
	 *            Seguridad. Usuario logado
	 */
	void redistributeInputDistributionManual(String numReg, int destinationType, int destinationId,
			String destinationName, String matter, Security security);

	/**
	 * Realiza una redistribución manual de una distribución que se encuentra en
	 * la bandeja de salida. 
	 * 
	 * La redistribución se dirige hacia el destino indicando el id, tipo y nombre.
	 * 
	 * No modifica el destino del registro.
	 * 
	 * @param numReg
	 *            Número de registro
	 * @param destinationType
	 *            Tipo del destino (usuario, departamento o grupo)
	 * @param destinationId
	 *            Identificador del destino
	 * @param destinationName
	 *            Nombre del destino
	 * @param security
	 *            Seguridad. Usuario logado
	 */
	void redistributeOutputDistributionManual(String numReg, int destinationType,
			int destinationId, String destinationName, String matter, Security security);

}
