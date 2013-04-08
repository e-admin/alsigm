package valoracion.db;

import java.util.List;

import valoracion.vos.PlazoValoracionVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGFPZTRVALSERIE</b>
 * 
 * @author IECISA
 * 
 */
public interface IPlazosValoracionDBEntity extends IDBEntity {
	List getPlazosValoracion(String idValoracion);

	int getCountPlazosValoracion(String idValoracion);

	List getValoracionesPorPlazos(String idNivelOrigen, String idNivelDestino);

	List getValoracionesPorIdNivelOrigenDestino(String idNivel);

	PlazoValoracionVO getPlazoValoracion(String idValoracion, int orden);

	void deletePlazoValoracion(final String idValoracion, final int orden);

	void deletePlazosValoracionFromOrden(final String idValoracion,
			final int orden);

	void deletePlazosValoracion(final String idValoracion);

	void insertPlazoValoracion(final PlazoValoracionVO plazoVO);

	void updatePlazoValoracion(final PlazoValoracionVO plazoVO);
}