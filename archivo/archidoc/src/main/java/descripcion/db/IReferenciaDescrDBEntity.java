package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoReferenciaVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo
 * referencia. <br>
 * Entidad: <b>ADVCREFDESCR</b>
 */
public interface IReferenciaDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idDescr);

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
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
	public CampoReferenciaVO getValue(String idDescr, String idCampo, int orden);

	/**
	 * Inserta un valor de tipo referencia.
	 * 
	 * @param value
	 *            Valor de tipo referencia a insertar.
	 * @return Valor de tipo referencia insertado.
	 */
	public abstract CampoReferenciaVO insertValue(final CampoReferenciaVO value);

	/**
	 * Modifica un valor de tipo referencia.
	 * 
	 * @param value
	 *            Información del campo de tipo referencia.
	 */
	public abstract void updateValue(final CampoReferenciaVO value);

	/**
	 * Elimina un valor de tipo referencia.
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
	 * Elementos todos los valores de texto largo que pertenezcan al descriptor
	 * pasado como parametro
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public abstract void deleteValues(String idDescr);

	/**
	 * Obtiene el número de referencias a un objeto dado.
	 * 
	 * @param tipoObjRef
	 *            Tipo de objeto referenciado.
	 * @param idObjRef
	 *            Identificador del objeto referenciado.
	 * @return Número de referencias.
	 */
	public int countReferences(int tipoObjRef, String idObjRef);

	/**
	 * Obtiene el número de referencias a los descriptores de una lista dada.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesInList(String idListaDescriptora);

	/**
	 * Unifica el campo <b>ADVCREFDESCR.IDOBJREF</b> con el idDescriptor de
	 * todos los idsAReemplazar
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar);

	/**
	 * Elimina los elemento cuyo <b>ADVCREFDESCR.IDDESCR</b> sea igual a los
	 * idsDescriptores
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

}