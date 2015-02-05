package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoNumericoVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo
 * numérico. <br>
 * Entidad: <b>ADVCNUMCF</b> <br>
 * Entidad: <b>ADVCNUMUDOCRE</b>
 */
public interface INumeroDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idElementoCF, int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento);

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
	public CampoNumericoVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento);

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
	 * Elementos todos los valores de numero que pertenezcan al elementoCF
	 * pasado como parametro
	 * 
	 * @param idElementoCF
	 */
	public abstract void deleteValueXIdElemento(String idElementoCF,
			int tipoElemento);

	/**
	 * Obtiene el Campo de descripcion de la Ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param valor
	 *            Valor del campo.
	 * @return Campo de la Ficha.
	 */
	public abstract CampoNumericoVO getValue(String idElementoCF,
			String idCampo, String valor);

	/**
	 * Elimina un valor de tipo numérico.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idElementoCF, String idCampo, int orden);

	/**
	 * Obtiene el máximo ordens
	 * 
	 * @param idCampo
	 * @param idUdocRe
	 * @return
	 */
	public int getMaxOrden(String idElemento, String idCampo);
}