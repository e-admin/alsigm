package valoracion.db;

import java.util.List;

import valoracion.vos.EliminacionUDocConservadaVO;

import common.db.IDBEntity;

/**
 * Interface para la tabla de eliminaciones de serie <br>
 * Entidad: <b>ASGFELIMUDOCCONS</b>
 */
public interface IEliminacionUDOCConservadaDBEntity extends IDBEntity {
	/**
	 * Obtiene las unidades documentales a conservar de una eliminación
	 * 
	 * @param idEliminacion
	 *            Id de la eliminación de la que se quieren obtener las unidades
	 *            documentales.
	 */
	public List getUdocsEliminacionConservarXId(String idEliminacion);

	/**
	 * Obtiene los ids de las unidades documentales a conservar de una
	 * eliminación
	 * 
	 * @param idEliminacion
	 *            Id de la eliminación de la que se quieren obtener las unidades
	 *            documentales.
	 */
	public String[] getIdsUdocsEliminacionConservarXId(String idEliminacion);

	/**
	 * Obtiene la subconsulta para los ids de las unidades documentales a
	 * conservar de una eliminación
	 * 
	 * @param idEliminacion
	 *            Id de la eliminación de la que se quieren obtener las unidades
	 *            documentales.
	 */
	public String getSubConsultaIdsUdocsEliminacionConservarXId(
			String idEliminacion);

	/**
	 * Permite insertar una unidad documental a conservar en una eliminación
	 * 
	 * @param eliminacionUDocVO
	 *            Unidad documental a conservar
	 */
	public void insertUdocConservar(
			final EliminacionUDocConservadaVO eliminacionUDocVO);

	/**
	 * Permite eliminar una unidad documental a conservar
	 * 
	 * @param idEliminacion
	 *            Id de eliminacion
	 * @param idUdoc
	 *            Id de unidad documental
	 */
	public void deleteUdocXIdEliminacion(final String idEliminacion,
			final String idUdoc);

	/**
	 * Permite eliminar varias unidades documentales a conservar
	 * 
	 * @param idEliminacion
	 *            Id de eliminacion
	 * @param idUdoc
	 *            Id de unidad documental
	 */
	public void deleteUdocsXIdEliminacion(final String idEliminacion,
			final String[] idUdocs);

	/**
	 * Permite eliminar las unidades documentales a conservar de una eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion
	 */
	public void deleteUdocsXIdEliminacion(final String idEliminacion);
}