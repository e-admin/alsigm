package ieci.tecdoc.sgm.core.services.publicador;

import ieci.tecdoc.sgm.core.services.publicador.dto.Error;
import ieci.tecdoc.sgm.core.services.publicador.dto.Hito;
import ieci.tecdoc.sgm.core.services.publicador.dto.Regla;

/**
 * Interfaz para el servicio de publicación.
 *
 */
public interface ServicioPublicador {

	/**
	 * Obtiene la lista de hitos de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todos los hitos
	 * @throws PublicadorException
	 */
	public Hito[] getListaHitos(String idEntidad) throws PublicadorException;

	/**
	 * Obtiene el hito que se correponde con el idHito que recibe como parámetro
	 * en la entidad contra la que estamos trabajando (parámetro idEntidad)
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a obtener
	 * @param idAplicacion:
	 *            Identificador de la aplicacion
	 * @param idSystem :
	 *            Identificador del sistema
	 * @return Un objeto Hito con la información del hito
	 * @throws PublicadorException
	 */
	public Hito getHito(String idEntidad, int idHito, int idAplicacion, String idSystem) 
			throws PublicadorException;

	/**
	 * Añade el hito que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param hito :
	 *            Objeto de tipo Hito con la información a guardar en bbdd
	 * @return Hito creado
	 * @throws PublicadorException
	 */
	public Hito addHito(String idEntidad, Hito hito)
			throws PublicadorException;

	/**
	 * Actualiza el hito que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param :
	 *            Objeto de tipo Hito que se sobre-escribirá al existente
	 * @return Información del hito modificado.
	 * @throws PublicadorException
	 */
	public Hito updateHito(String idEntidad, Hito hito)
			throws PublicadorException;

	/**
	 * Borra el hito cuyo identificador se corresponde con el parámetro idHito,
	 * sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a borrar
	 * @param idAplicacion:
	 *            Identificador de la aplicacion
	 * @param idSystem :
	 *            Identificador del sistema
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws PublicadorException
	 */
	public boolean deleteHito(String idEntidad, int idHito, int applicationId,
			String systemId) throws PublicadorException;

	/**
	 * Reactiva el hito erróneo eliminando su error
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a borrar
	 * @param idAplicacion:
	 *            Identificador de la aplicacion
	 * @param idSystem :
	 *            Identificador del sistema
	 * @para objectId: Identificador del objeto
	 * @return Hito reactivado
	 * @throws PublicadorException
	 */
	public Hito reactivateHito(String idEntidad, int idHito,
			int applicationId, String systemId) throws PublicadorException;

	/**
	 * Obtiene la lista de reglas de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todas las reglas
	 * @throws PublicadorException
	 */
	public Regla[] getListaReglas(String idEntidad) throws PublicadorException;

	/**
	 * Obtiene la información de la regla cuyo identificador se corresponde con
	 * idRegla sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idRegla:
	 *            Identificador de la regla
	 * @return Un objeto de tipo Regla con la información de la regla
	 * @throws PublicadorException
	 */
	public Regla getRegla(String idEntidad, int idRegla)
			throws PublicadorException;

	/**
	 * Añade la regla que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param regla:
	 *            Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Regla creada.
	 * @throws PublicadorException
	 */
	public Regla addRegla(String idEntidad, Regla regla)
			throws PublicadorException;

	/**
	 * Actualiza la regla que recibe como parámetro sobre la entidad cuyo
	 * identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param regla:
	 *            Objeto de tipo Regla con la información a guardar en bbdd
	 * @return Información de la regla modificada.
	 * @throws PublicadorException
	 */
	public Regla updateRegla(String idEntidad, Regla regla)
			throws PublicadorException;

	/**
	 * Borra la regla cuyo identificador se corresponde con el parámetro
	 * idRegla, sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito a borrar
	 * @return cierto si tuvo éxito , falso en caso contrario
	 * @throws PublicadorException
	 */
	public boolean deleteRegla(String idEntidad, int idRegla)
			throws PublicadorException;

	/**
	 * Obtiene la lista de errores de la entidad que recibe como parámetro
	 * 
	 * @param idEntidad:
	 *            Identificador de la entidad
	 * @return Lista de todos los errores
	 * @throws PublicadorException
	 */
	public Error[] getListaErrores(String idEntidad) throws PublicadorException;

	/**
	 * Obtiene la información del error cuyo identificador se corresponde con
	 * idError sobre la entidad cuyo identificador sea idEntidad
	 * 
	 * @param idEntidad :
	 *            Identificador de la entidad contra la que estamos trabajando
	 * @param idHito :
	 *            Identificador del hito
	 * @param applicationId:
	 *            Identificador de la aplicacion
	 * @param systemId :
	 *            Identificador del sistema
	 * @return
	 * @throws PublicadorException
	 */
	public Error getError(String idEntidad, int idHito, int applicationId,
			String systemId) throws PublicadorException;

}
