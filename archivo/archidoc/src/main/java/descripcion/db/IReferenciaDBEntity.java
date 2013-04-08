package descripcion.db;

import java.util.ArrayList;
import java.util.List;

import transferencias.vos.InteresadoVO;

import common.db.IDBEntity;

import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.ProductorElementoCF;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo
 * referencia. <br>
 * Entidad: <b>ADVCREFCF</b> <br>
 * Entidad: <b>ADVCREFUDOCRE</b>
 */
public interface IReferenciaDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idElementoCF, int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificadores de elementos del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String[] idsElementoCF, String idCampo,
			int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param listaCamposIgnorar
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento,
			ArrayList listaCamposIgnorar);

	/**
	 * Obtiene el valor de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 * @return Valor de la ficha.
	 */
	public CampoReferenciaVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento);

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
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public abstract void deleteValue(String idElementoCF, String idCampo,
			String orden, int tipoElemento);

	/**
	 * Elementos todos los valores de texto largo que pertenezcan al elementoCF
	 * pasado como parametro
	 * 
	 * @param idElementoCF
	 */
	public abstract void deleteValueXIdElemento(String idElementoCF,
			int tipoElemento);

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
	 * Unifica el campo <b>ADVCREFUDOCRE.IDOBJREF</b> o el campo
	 * <b>ADVCREFCF.IDOBJREF</b> con el idDescriptor de todos los idsAReemplazar
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar);

	/**
	 * Obtiene la lista de valores de tipo texto de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param tipoElemento
	 *            tipo del elemento.
	 * @return número de valores encontrados
	 */
	public int getCountValues(String idElementoCF, String idCampo,
			int tipoElemento);

	/**
	 * Obtiene la lista de interesados pertenecientes a unas determinadas
	 * unidades documentales
	 * 
	 * @param idsElementosCF
	 *            Array de cadenas que contiene los identificadores de las
	 *            unidades documentales.
	 * @return Lista de {@link InteresadoVO}
	 */
	public List getInteresadosByIdsElementosCF(String[] idsElementosCF);

	/**
	 * Obtiene la lista de productores pertenecientes a unas determinadas
	 * unidades documentales
	 * 
	 * @param idsElementosCF
	 *            Array que contiene los identificadores de las unidades
	 *            documentales
	 * @return Lista de {@link ProductorElementoCF}
	 */
	public List getProductoresByIdsElementosCF(String[] idsElementosCF);

	/**
	 * Obtiene el productor asociado un elemento
	 * 
	 * @param idObjeto
	 *            Cadena que contiene el identificador del objeto
	 * @param idCampo
	 *            Cadena que contiene el identificador del campo
	 */
	public CampoReferenciaVO getProductor(String idObjeto, String idCampo);

	public int getMaxOrden(String idElemento, String idCampo);

}