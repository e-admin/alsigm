package auditoria.db;

import java.util.List;

import auditoria.vos.DatosTrazaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>AADATOSTRAZA</b>
 * 
 * @author IECISA
 * 
 */
public interface IDatosTrazaDBEntity extends IDBEntity {
	/**
	 * Inserta una nueva linea de datos traza de una traza de auditoria en la
	 * base de datos
	 * 
	 * @param datos
	 *            Linea con los datos de traza de una traza de auditoría a
	 *            insertar.
	 * @return {@link auditoria.vos.DatosTrazaVO} insertada
	 */
	public abstract DatosTrazaVO insertDatosTraza(final DatosTrazaVO datosTraza);

	/**
	 * Obtiene los detalles de una pista de auditoria a partir del identificador
	 * de la pista
	 * 
	 * @param id
	 *            Identificador de la pista de la que deseamos recuperar los
	 *            detalles.
	 * @return Listado de los detalles de la pista de auditoria
	 */
	public abstract List getDatosPista(String id);
}