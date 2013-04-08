package solicitudes.prestamos.db;

import java.util.Collection;
import java.util.List;

import solicitudes.prestamos.vos.MotivoPrestamoVO;

import common.db.IDBEntity;

/**
 * Interface para los motivos de prestamo <br>
 * Entidad: <b>ASGPMTVPRESTAMO</b>
 */
public interface IMotivoPrestamoDBEntity extends IDBEntity {

	public abstract void insertarMotivoPrestamo(final MotivoPrestamoVO motivoVO);

	public abstract MotivoPrestamoVO getMotivoPrestamo(
			final MotivoPrestamoVO motivoVO);

	public abstract List getMotivosPrestamo();

	public abstract MotivoPrestamoVO getMotivoPrestamoById(final String idMotivo);

	public abstract void deleteMotivoPrestamo(final MotivoPrestamoVO motivoVO);

	public abstract void actualizarMotivoPrestamo(
			final MotivoPrestamoVO motivoVO);

	public abstract Collection getMotivosByTipoUsuario(Integer tipoUsuario,
			Integer[] visibilidad);
}