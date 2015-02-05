package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoNumericoVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo
 * numérico. <br>
 * Entidad: <b>ADVCNUMDESCR</b>
 */
public interface INumeroDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idDescr);

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
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
	public CampoNumericoVO getValue(String idDescr, String idCampo, int orden);

	/**
	 * Inserta un valor de tipo numerico corto.
	 * 
	 * @param value
	 *            Valor de tipo numerico a insertar.
	 * @return Valor de tipo numerico insertado.
	 */
	public abstract CampoNumericoVO insertValue(final CampoNumericoVO value);

	/**
	 * Modifica un valor de tipo numérico.
	 * 
	 * @param value
	 *            Información del campo de tipo numérico.
	 */
	public abstract void updateValue(final CampoNumericoVO value);

	/**
	 * Elimina un valor de tipo numérico.
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
	 * Elementos todos los valores de numero que pertenezcan al elementoCF
	 * pasado como parametro
	 * 
	 * @param idDescr
	 */
	public abstract void deleteValues(String idDescr);

	/**
	 * Elimina los elemento cuyo <b>ADVCNUMDESCR.IDDESCR</b> sea igual a los
	 * idsDescriptores
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

}