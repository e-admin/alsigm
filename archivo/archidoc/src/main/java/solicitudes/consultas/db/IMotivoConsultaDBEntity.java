package solicitudes.consultas.db;

import java.util.List;

import solicitudes.consultas.vos.MotivoConsultaVO;

import common.db.IDBEntity;

/**
 * Interface para los motivos de consulta. Entidad: <b>ASGPMTVCONSULTA</b>
 */
public interface IMotivoConsultaDBEntity extends IDBEntity {
	/**
	 * Obtiene un listado de los motivos existentes para el tipo de entidad
	 * indicado.
	 * 
	 * @param tipo
	 *            TIpo de entidad del que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de entidad
	 *         indicado.
	 */
	public abstract List getMotivosByTipoEntidad(int tipo);

	/**
	 * Obtiene un listado de los motivos existentes para el tipo de consulta.
	 * 
	 * @param tipo
	 *            TIpo de consulta de la que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de consulta
	 *         indicado.
	 */
	public abstract List getMotivosByTipoConsulta(int tipo);

	public abstract void insertarMotivoConsulta(final MotivoConsultaVO motivoVO);

	public abstract MotivoConsultaVO getMotivoConsulta(
			final MotivoConsultaVO motivoVO);

	public abstract List getMotivosConsulta();

	public abstract MotivoConsultaVO getMotivoConsultaById(final String idMotivo);

	public abstract void deleteMotivoConsulta(final MotivoConsultaVO motivoVO);

	public abstract void actualizarMotivoConsulta(
			final MotivoConsultaVO motivoVO);

	public abstract List getMotivosConsulta(Integer tipoEntidad,
			Integer tipoConsulta, Integer[] visibilidad);
}