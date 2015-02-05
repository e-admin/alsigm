package solicitudes.consultas.db;

import java.util.Collection;
import java.util.List;

import solicitudes.consultas.vos.BusquedaVO;
import solicitudes.consultas.vos.ConsultaVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

/**
 * Inteface de la clase de acceso a la tabla consultas. <br>
 * Entidad: <b>ASGPCONSULTA</b>
 */
public interface IConsultaDBEntity extends IDBEntity {
	/**
	 * Obtiene un listado de los usuarios que realizan una consulta
	 * 
	 * @return Listado de los nombres de los usuarios consultores
	 */
	public abstract Collection getUsuariosBusqueda();

	/**
	 * Obtitene las consultas para el usuario gestor a partir del nombre del
	 * organismo consultor y de sus descendientes. Si ademas tiene filtros de
	 * busqueda, filtra los anteriores resultados por: fecha de entrega,usuario
	 * solicitante, fecha estado y estado.
	 * 
	 * @param busqueda
	 *            Objeto con los filtros para aplicar en la busqueda
	 * @return Listado de las consultas que se ajustan a los criterios indicados
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getConsultas(BusquedaVO busqueda, String[] idsArchivo)
			throws TooManyResultsException;

	/**
	 * Obtiene un consulta a partir de su identificador
	 * 
	 * @param codigoConsulta
	 *            Identificador de la consulta que deseamos recuperar
	 */
	public abstract ConsultaVO getConsulta(String codigoConsulta);

	/**
	 * Indica si un usuario tiene consultas en los estados determinados.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario.
	 * @param estados
	 *            Estados de las consultas.
	 * @return true si el usuario tiene consultas en curso.
	 */
	public boolean hasConsultas(String idUsuario, String[] estados);

	/**
	 * Obtiene el número de consultas en las que el usuario figura como
	 * solicitante y que se encuentran en alguno de los estados que se indican.
	 * 
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @param estados
	 *            Conjunto de posibles estados para una consulta
	 * @return Número de consultas para el usuario solicitante
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados);

	/**
	 * Obtiene el número de consultas en las que el usuario figura como
	 * solicitante y que se encuentran en alguno de los estados que se indican.
	 * 
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @param estados
	 *            Conjunto de posibles estados para una consulta
	 * @param idsArchivos
	 *            Identificadores de los Archivos para una consulta
	 * @return Número de consultas para el usuario solicitante
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados, String[] idsArchivo);

	/**
	 * Obtiene un listado de las consultas en las que el usuario figura como
	 * solicitante y que se encuentran en alguno de los estados que se indican.
	 * 
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @param estados
	 *            Conjunto de posibles estados para una consulta
	 * @return Listado de las consultas para el usuario solicitante
	 *         {@link ConsultaVO}.
	 */
	public List getConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados);

	public abstract Collection getConsultasXUsuarioConsultorAbiertos(
			String nUsuario);

	/**
	 * Obtiene el número de consultas que se encuentran en alguno de los estados
	 * indicados.
	 * 
	 * @param estados
	 *            Listado de los posibles estados en que deseamos que se
	 *            encuentre las consultas buscadas
	 * @return Número de consultas encontradas que se encontraban en alguno de
	 *         los estados solicitados.
	 */
	public int getCountConsultasXEstados(String[] estados, String[] idsArchivo);

	/**
	 * Obtiene un listado de consultas que se encuentran en alguno de los
	 * estados indicados.
	 * 
	 * @param estados
	 *            Listado de los posibles estados en que deseamos que se
	 *            encuentre las consultas buscadas
	 * @param busqueda
	 *            Objeto con las restriccion para los resultados de la busqueda:
	 *            fecha de estado, entrega y usuario solicitante.
	 * @return Listado de las consultas encontradas que se encontraban en alguno
	 *         de los estados solicitados.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public abstract List getConsultasXEstados(List estados,
			BusquedaVO busqueda, String[] idsArchivo)
			throws TooManyResultsException;

	/**
	 * Obtiene las consultas que pertenecen al usuario indicador(como
	 * solicitante) y se encuentra en unos de los estado pasados. En caso de que
	 * se pase el filtro de busqueda estos criterios deberan ser tambien
	 * cumplidos: fecha entrega,fescha estado.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 * @param estados
	 *            Listado de los estados por los que deseamo restringir
	 * @param busqueda
	 *            Objeto busqueda con las restricciones posibles, o null en caso
	 *            de no haber
	 * @return Listado de las consultas que cumplen el filtro
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public abstract Collection getConsultasXEstadosYUsuario(String idUsuario,
			List estados, BusquedaVO busqueda) throws TooManyResultsException;

	/**
	 * Obtiene un listado de las consultas que tiene abiertas un usuario como
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 */
	public abstract Collection getConsultasXUsuarioGestorAbiertos(
			String idUsuario);

	/**
	 * Realiza la actualizacion de una consulta.
	 * 
	 * @param consultaVO
	 *            Consulta que deseamos actualizar
	 */
	public abstract void updateConsulta(ConsultaVO consultaVO);

	/**
	 * Realiza la inserción de una consulta en la base de datos.
	 * 
	 * @param consulta
	 *            Consulta con los datos que deseamos insertar en la bd.
	 */
	public abstract void insertConsulta(final ConsultaVO consulta);

	/**
	 * Realiza el borrado de las consultas seleccionadas.
	 * 
	 * @param idConsultaonsulta
	 *            identificador de la consulta a eliminar.
	 */
	public abstract void deleteConsulta(String idConsulta);

	/**
	 * Obtiene un listado de consultas a partir de sus identificadores.
	 * 
	 * @param codigos
	 *            Listado de los identificadores de las consultas que deseamo
	 *            recuperar
	 * @return Listado de las consultas
	 */
	public abstract Collection getConsultas(String[] codigos);

	/**
	 * Obtiene el número de detalles que están asociados a un determinada
	 * consulta.
	 * 
	 * @param idConsulta
	 *            Identificador de la consulta del que deseamos conocer su
	 *            numero de detalles asociados.
	 * @return Numero de detalles de consulta asociados.
	 */
	public abstract int numeroDetallesConsulta(final String idConsulta);

	/**
	 * Realiza el envío de una consulta
	 * 
	 * @param consulta
	 *            Consulta que se va a enviar.
	 * @param tipoUsuario
	 *            Tipo del usuario que va realiza el envio
	 */
	public abstract void enviarConsulta(ConsultaVO consulta, String tipoUsuario);

	/**
	 * Actualiza las observaciones de la Consulta
	 * 
	 * @param id
	 *            Identificador de la Consulta
	 * @param observaciones
	 *            Observaciones
	 */
	public abstract void updateObservaciones(String id, String observaciones);

	public int getCountConsultasByIdMotivo(String idMotivo);

	/**
	 * Obtiene los préstamos filtrados por idArchivo, idUsuarioGestor y estados
	 * 
	 * @param idArchivo
	 * @param idUsuarioGestor
	 * @param estados
	 * @return Lista de ConsultaVO
	 */
	public List getConsultasByIdarchivoAndIdUsuarioSolicitante(
			String idArchivo, String idUsuarioSolicitante, String[] estados);

	/**
	 * Obtiene el número de consultas que no se encuentran en los estados
	 * especificados
	 * 
	 * @param idcsusrsala
	 *            Identificador del usuario en la sala.
	 * @param estadosNotIn
	 *            Estados
	 * @return numero de consultas.
	 */
	public int getCountConsultasByIdUsrSalaNotInEstados(String idcsusrsala,
			int[] estadosNotIn);

	/**
	 * Obtiene las consultas asociadas al archivo
	 * 
	 * @param idArchivo
	 *            identificador del archivo
	 * @return Lista de objetos {@link ConsultaVO}
	 */
	public List getConsultasByIdArchivo(String idArchivo);

	/**
	 * Obtiene las consultas asociadas al usuario de consulta en sala
	 * 
	 * @param idRegistro
	 *            Identificador del usuario de consulta en sala
	 * @param estados
	 * @return Lista de objetos {@link ConsultaVO}
	 */
	public List getConsultasByIdUsrCSala(String idUsrCSala, int[] estados);

	/**
	 * Obtiene las consultas con unidades documentales pendientes de devolver
	 * para el usuario de consulta en sala
	 * 
	 * @param idUsrCSala
	 *            Identificador del usuario de consulta en sala
	 * @return Lista de objetos {@link ConsultaVO}
	 */
	public List getConsultasPendientesByIdUsrCSala(String idUsrCSala);
}