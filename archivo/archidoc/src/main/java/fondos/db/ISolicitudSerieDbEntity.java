package fondos.db;

import java.util.List;
import java.util.Map;

import common.db.IDBEntity;

import fondos.vos.SolicitudSerieVO;

/**
 * Métodos de acceso a los datos almacenados en la base de datos referentes a
 * solicitudes de autorización de las acciones implicadas en la gestión de
 * series documentales. <br>
 * Entidad: <b>ASGFSOLICITUDSERIE</b>
 */
public interface ISolicitudSerieDbEntity extends IDBEntity {

	public SolicitudSerieVO insertSolicitud(SolicitudSerieVO solicitud);

	public int getCountSolicitudes(final String idUser, final int[] tipos,
			int[] estadosAMostrar);

	public List getSolicitudes(String idUser, int[] tipos, int[] estadosAMostrar);

	public abstract SolicitudSerieVO getSolicitudBySerie(String idSerie);

	public abstract SolicitudSerieVO getSolicitud(String idSolicitud);

	public void updateFields(String idSolicitud, final Map columnsToUpdate);

	public void saveAutorizacionSolicitud(String idSolicitud, String usuario);

	public void saveRechazoSolicitud(String idSolicitud, String usuario,
			String motivoRechazo);

	/**
	 * Elimina las entradas que se indican de la tabla en la que se almacenan
	 * las solicitudes de autorización de las acciones implicadas en la gestión
	 * de series documentales
	 * 
	 * @param idSolicitud
	 *            Identificadores de las solicitudes a eliminar
	 */
	public void eliminarSolicitudes(String[] idSolicitud);

	public void eliminarSolicitudesByIdSerie(String idSerie);

	// /**
	// *
	// * @param busquedaSolicitudes
	// * @return Collecton de @SolicitudSerieExtended
	// * @throws TooManyResultsException
	// */
	// public Collection findSolicitudes(BusquedaSolicitudesSerieVO
	// busquedaSolicitudes) throws TooManyResultsException;

}