package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoTextoVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo texto
 * corto. <br>
 * Entidad: <b>ADVCTEXTDESCR</b>
 */
public interface ITextoCortoDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo texto corto de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idDescr);

	/**
	 * Obtiene la lista de valores de tipo texto corto de la ficha.
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
	public CampoTextoVO getValue(String idDescr, String idCampo, int orden);

	/**
	 * Inserta un valor de tipo texto corto.
	 * 
	 * @param value
	 *            Valor de tipo texto corto a insertar.
	 * @return Valor de tipo texto corto insertado.
	 */
	public abstract CampoTextoVO insertValue(final CampoTextoVO value);

	/**
	 * Modifica un valor de tipo texto corto.
	 * 
	 * @param value
	 *            Información del campo de tipo texto corto.
	 */
	public abstract void updateValue(final CampoTextoVO value);

	/**
	 * Elimina un valor de tipo texto corto.
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
	 * Elementos todos los valores de texto corto que pertenezcan al descriptor
	 * pasado como parametro.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public abstract void deleteValues(String idDescr);

	/**
	 * Elimina los elemento cuyo <b>ADVCTEXTDESCR.IDDESCR</b> sea igual a los
	 * idsDescriptores
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

}