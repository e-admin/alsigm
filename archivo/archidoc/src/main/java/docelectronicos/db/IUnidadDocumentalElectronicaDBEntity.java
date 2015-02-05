package docelectronicos.db;

import common.db.IDBEntity;

import docelectronicos.vos.UnidadDocumentalElectronicaVO;

/**
 * Entidad: <b>ASGFUDOCSDF</b>
 * 
 * @author IECISA
 * 
 */
public interface IUnidadDocumentalElectronicaDBEntity extends IDBEntity {

	/**
	 * Añade una unidad Documental Electrónca.
	 * 
	 * @param udoc
	 *            {@link UnidadDocumentalElectronicaVO} Datos de la Unidad
	 *            Documental
	 */
	public void insertRow(final UnidadDocumentalElectronicaVO udoc);

	/**
	 * Obtiene una unidad documental Electrónica a través de su id de unidad
	 * Documental Cruza con la tabla de IdElementoCF, para obtener el resto de
	 * campos.
	 * 
	 * @param idUnidadDoc
	 *            Identificador del unidad documental electrónica en el cuadro
	 *            de clasificación.
	 */
	public UnidadDocumentalElectronicaVO getUDocElectronica(String idElementoCF);

	/**
	 * Actualiza las Marcas de Bloqueo de la Unidad Documental Electrónica
	 * 
	 * @param idElementoCF
	 *            Identificador del Elemento
	 * @param marcasBlqueo
	 *            Marcas de Bloqueo a Establecer
	 */
	public void updateMarcasBloqueo(String idElementoCF, int marcasBlqueo);

	/**
	 * Obtiene las Marcas de Bloqueo de la Unidad Documental Electrónica.
	 * 
	 * @param idElementoCF
	 * @return
	 */
	public int getMarcasBloqueo(String idElementoCF);

	/**
	 * Desbloquea las Unidades Documentales seleccionadas
	 * 
	 * @param ids
	 *            Identificadores de las unidades a desbloquear.
	 */
	public void desbloqueaUDocsElectronicas(String ids[]);

}
