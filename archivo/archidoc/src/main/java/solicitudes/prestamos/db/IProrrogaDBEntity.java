package solicitudes.prestamos.db;

import java.util.Collection;
import java.util.List;

import solicitudes.prestamos.vos.ProrrogaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGPPRORROGA</b>
 * 
 * @author IECISA
 * 
 */
public interface IProrrogaDBEntity extends IDBEntity {
	/**
	 * Comprueba si un préstamos tiene prorroga solicitada.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamos del que deseamos comprobar si
	 *            tiene prorroga solicitada.
	 * @return Objeto que encapsula los datos de una prorroga de un préstamo.
	 */
	public abstract ProrrogaVO getProrrogaSolicitada(String codigoPrestamo);

	/**
	 * Obtiene un listado con las prorrogas de los préstamos solicitados y que
	 * vienen identificados por sus ids.
	 * 
	 * @param codigos
	 *            Listado de identificadores de los prestamos de los que
	 *            deseamos obtener sus prorrogas.
	 * @return Listado de las prorrogas para los préstamos solicitados.
	 */
	public abstract Collection getProrrogas(String[] codigos);

	/**
	 * Realiza la insercion de una prorroga de un préstamo reclamado.
	 * 
	 * @param prorrogaVO
	 *            Prorroga que deseamos insertar para el prestamo
	 * @return Prorroga insertada
	 */
	public abstract ProrrogaVO insertProrroga(final ProrrogaVO prorrogaVO);

	/**
	 * Actualiza la prorroga de un prestamo. Se puede utilzar dentro de un
	 * transaccion si la conexion pasada es null.
	 * 
	 * @param con
	 *            Conexion a la base de datos
	 * @param prorrogaVO
	 *            Prorroga que deseamos actualizar
	 */
	public abstract void updateProrroga(ProrrogaVO prorrogaVO);

	public int getCountProrrogasByIdMotivo(String idMotivo);

	/**
	 * Obtiene la lista de prorrogas de un prestamo
	 * 
	 * @param idPrestamo
	 *            Cadena que contiene el identificador del préstamo
	 * @return Lista de {@link ProrrogaVO}
	 */
	public List getProrrogasByIdPrestamo(String idPrestamo);

	/**
	 * @param idPrestamo
	 * @return
	 */
	public ProrrogaVO getProrrogaActiva(String idPrestamo);
}