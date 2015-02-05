package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoFechaVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a los campos de tipo
 * fecha. <br>
 * Entidad: <b>ADVCFECHADESCR</b>
 */
public interface IFechaDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idDescr);

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr, String idCampo);

	/**
	 * Obtiene el valor de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 * @return Valor de la ficha.
	 */
	public CampoFechaVO getValue(String idDescr, String idCampo, int orden);

	/**
	 * Inserta un valor de tipo fecha.
	 * 
	 * @param value
	 *            Valor de tipo fecha a insertar.
	 * @return Valor de tipo fecha insertado.
	 */
	public abstract CampoFechaVO insertValue(final CampoFechaVO value);

	/**
	 * Modifica un valor de tipo fecha.
	 * 
	 * @param value
	 *            Información del campo de tipo fecha.
	 */
	public abstract void updateValue(final CampoFechaVO value);

	/**
	 * Elimina un valor de tipo fecha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public abstract void deleteValue(String idDescr, String idCampo,
			String orden);

	/**
	 * Elementos todos los valores de fecha que pertenezcan al descriptor pasado
	 * como parametro
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public abstract void deleteValues(String idDescr);

	/**
	 * Elimina los elemento cuyo <b>ADVCFECHADESCR.IDDESCR</b> sea igual a los
	 * idsDescriptores
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

}