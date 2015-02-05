package solicitudes.prestamos.db;

import gcontrol.vos.UsuarioVO;

import java.util.Collection;
import java.util.List;

import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.PrestamoVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

/**
 * Interface con los metodos de bd para prestamos. <br>
 * Entidad: <b>ASGPPRESTAMO</b>
 */
public interface IPrestamoDBEntity extends IDBEntity {
	/**
	 * Inserta un prestamo en la base de datos creado por el usuario del
	 * servicio.
	 * 
	 * @param userVO
	 *            Usuario que está creando el préstamo.
	 * @param prestamoVO
	 *            Prestamo a insertar en la base de datos.
	 */
	public abstract void insertPrestamo(final PrestamoVO prestamo);

	/**
	 * Actualiza un prestamo.
	 * 
	 * @param prestamoVO
	 *            Prestamo que deseamos actualizar
	 */
	public abstract void updatePrestamo(final PrestamoVO prestamo);

	/**
	 * Obtiene un préstamo a partir de su identificador en la base de datos.
	 * 
	 * @param codprestamo
	 *            Identificador del préstamo en la base de datos
	 * @return Objeto {@link PrestamoVO} con los detalles del préstamo.
	 */
	public abstract PrestamoVO getPrestamo(String codigoPrestamo);

	/**
	 * Obtiene un listado de prestamos a partir de sus identificadores.
	 * 
	 * @param codigos
	 *            Listado de los identificadores de los prestamos que deseamo
	 *            recuperar
	 * @return Listado de los prestamos
	 */
	public abstract Collection getPrestamos(String[] codigos);

	/**
	 * Obtiene el número de prestamos que se encuentran en alguno de los estados
	 * que se indican y que han sido elaborador por un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Conjunto de posibles estados de préstamo. Puede ser nulo
	 * @return Número de los prestamos del usuario en los que figura como
	 *         usuario gestor
	 */
	public int getCountPrestamosXUsuarioSolicitante(String idUsuario,
			int[] estados);

	/**
	 * Obtiene los prestamos que se encuentran en alguno de los estados que se
	 * indican y que han sido elaborador por un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Conjunto de posibles estados de préstamo. Puede ser nulo
	 * @return Un listado de los prestamos del usuario en los que figura como
	 *         usuario gestor
	 */
	public List getPrestamosXUsuarioSolicitante(String idUsuario, int[] estados);

	/**
	 * Obtiene el número de prestamos para un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Estados de los que se buscan préstamos
	 * @return Número de prestamos del usuario
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados);

	/**
	 * Obtiene el número de prestamos para un usuario determinado.
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param estados
	 *            Estados de los que se buscan préstamos
	 * @param idsArchivo
	 *            Identificadores de los archivos
	 * @return Número de prestamos del usuario
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados, String[] idsArchivo);

	/**
	 * Obtiene los prestamos para un usuario determinado.Utilizado para las
	 * busquedas
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Un listado de los prestamos del usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public abstract List getPrestamosXUsuarioSolicitanteBuscar(
			String idUsuario, BusquedaVO busqueda)
			throws TooManyResultsException;

	/**
	 * Indica si un usuario tiene préstamos en los estados determinados.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario.
	 * @param estados
	 *            Estados de los préstamos.
	 * @return true si el usuario tiene préstamos en curso.
	 */
	public boolean hasPrestamos(String idUsuario, String[] estados);

	/**
	 * Devuelve el número de préstamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos obtener sus
	 *            préstamos.
	 * @return Número de préstamos cuyo estado se encuentra en alguno de los
	 *         indicados.
	 */
	public int getCountPrestamosXEstados(String[] estados, String[] idsArchivos);

	/**
	 * Muestra todos los préstamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos mostrar sus
	 *            préstamos.
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Listado de los préstamos cuyo estado se encuentra en alguno de
	 *         los indicados.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamosXEstados(String[] estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException;

	/**
	 * Obtiene el número de prestamos de un usuario gestor dado por su
	 * identificador, además de los de su organismo y de organismos
	 * dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @param descendientes
	 *            Lista de identificadores de los organismo
	 *            dependientes(incluido el suyo propio)
	 * @return Número de prestamos para el usuario
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuarioSolicitante,
			String[] descendientes, String[] estados);

	public int getCountPrestamosXUsuarioGestor(String idUsuarioSolicitante,
			String[] descendientes, String[] estados, String[] idsArchivo);

	/**
	 * Obtiene los prestamos de un usuario gestor dado por su identificador,
	 * además de los de su organismo y de organismos dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @param descendientes
	 *            Lista de identificadores de los organismo
	 *            dependientes(incluido el suyo propio)
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Listado de prestamos para el usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public abstract List getPrestamosXUsuarioGestor(
			String idUsuarioSolicitante, String[] descendientes,
			BusquedaVO busqueda) throws TooManyResultsException;

	/**
	 * Obtiene un listado de los usuarios solicitantes de prestamo cuyo estado
	 * es NO ABIERTO y, en caso que este presente,cuyo organo sea algún órgano
	 * de la lista(los que dependen del órgano del usuario conectado)
	 * 
	 * @param organos
	 *            Lista de órganos en los cuales debe estar presente el usuario
	 *            solicitante del prestamo
	 * @return Listado de los usuarios
	 */
	public abstract Collection getUsuariosBusquedaPrestamos(List organos);

	/**
	 * Realiza el envío de los detalles de un prestamo
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo.
	 * @param tipoUsuario
	 *            Tipo del usuario que va realiza el envio
	 */
	public abstract void enviarPrestamo(PrestamoVO prestamoVO,
			String tipoUsuario);

	/**
	 * Realiza el borrado de un prestamo dado por su identificador.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo.
	 */
	public abstract void deletePrestamo(String idPrestamo);

	/**
	 * Obtiene el número de detalles que están asociados a un determinado
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos conocer su numero
	 *            de detalles asociados.
	 * @return Numero de detalles de prestamo asociados al prestamo.
	 */
	public abstract int numeroDetallesPrestamo(final String idPrestamo);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el nombre de
	 * usuario solicitante.
	 * 
	 * @param nombreUsuarioSolicitante
	 *            Nombre del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public abstract Collection getPrestamosXUsuarioSolicitanteAbiertos(
			String nombreUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public abstract Collection getPrestamosXIdUsuarioSolicitanteAbiertos(
			String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que estan en elaboracion por el id de
	 * usuario solicitante o usuario gestor
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public abstract Collection getPrestamosEnElaboracionXIdUsuario(
			String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos como gestor un
	 * determinado usuario.
	 * 
	 * @param idUsuario
	 *            Identificador en la base de datos del usuario.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public abstract Collection getPrestamosXUsuarioGestorAbiertos(
			String idUsuario);

	/**
	 * Obtiene un listado de los prestamos que pertenecen a un determinado
	 * organo
	 * 
	 * @param idOrgano
	 *            Identificador del organo del que deseamos obtener los
	 *            prestamos.
	 * @return Listado de los prestamos
	 */
	public abstract Collection getPrestamosXOrganoSolicitante(String idOrgano);

	/**
	 * Obtiene un listado de los prestamos externos (que no tienen un
	 * identificador de organo solicitante establecido)
	 * 
	 * @return Listado de los prestamos
	 */
	public abstract Collection getPrestamosExternos();

	/**
	 * Obtiene la lista de gestores con préstamos.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrestamos(String idOrgano);

	/**
	 * Obtiene la lista de préstamos que cumplan los criterios especificados.
	 * 
	 * @param vo
	 *            Criterios de búsqueda.
	 * @return Lista de préstamos ({@link PrestamoVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamos(BusquedaVO vo, String[] idsArchivo)
			throws TooManyResultsException;

	/**
	 * Actualiza las observacioens de la Consulta.
	 */
	public void updateObservaciones(String id, String observaciones);

	public int getCountPrestamosByIdMotivo(String idMotivo);

	/**
	 * Obtiene los préstamos filtrados por idArchivo, idUsuarioGestor y estados
	 * 
	 * @param idArchivo
	 * @param idUsuarioGestor
	 * @param estados
	 * @return Lista de PrestamoVO
	 */
	public abstract List getPrestamosByIdarchivoAndIdUsuarioGestor(
			String idArchivo, String idUsuarioGestor, String[] estados);

	/**
	 * Cuenta los préstamos en elaboracion para usuario gestor o usuario
	 * solicitante.
	 * 
	 * @param idUsuario
	 * @return
	 */
	public abstract int getCountPrestamosEnElaboracionXIdUsuario(
			String idUsuario);
}