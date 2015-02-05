package solicitudes.db;

import java.util.Collection;
import java.util.List;

import solicitudes.vos.MotivoRechazoVO;

import common.db.IDBEntity;
import common.exceptions.DBException;

/**
 * Entidad: <b>ASGPMTVRECHAZO</b>
 * 
 * @author IECISA
 * 
 */
public interface IMotivoRechazoDBEntity extends IDBEntity {
	/**
	 * Obtiene los motivos de rechazo de un determinado tipo.
	 * 
	 * @param tipo
	 *            Tipo de los motivos de rechazo a obtener.
	 * @return Listado con los motivos de rechazo.
	 * @throws DBException
	 *             Si se produce algún problema durante el acceso a la base de
	 *             datos.
	 */
	public abstract Collection getMotivos(int tipo);

	public abstract void insertarMotivoRechazo(final MotivoRechazoVO motivoVO);

	public abstract MotivoRechazoVO getMotivoRechazo(
			final MotivoRechazoVO motivoVO);

	public abstract List getMotivosRechazo();

	public abstract MotivoRechazoVO getMotivoRechazoById(final String idMotivo);

	public abstract void deleteMotivoRechazo(final MotivoRechazoVO motivoVO);

	public abstract void actualizarMotivoRechazo(final MotivoRechazoVO motivoVO);
}