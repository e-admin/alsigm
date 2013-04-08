package descripcion.db;

import java.util.ArrayList;
import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.CampoTextoVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo texto
 * (corto y largo). <br>
 * Entidad: <b>ADVCTEXTCF</b> <br>
 * Entidad: <b>ADVCTEXTLCF</b> <br>
 * Entidad: <b>ADVCTEXTLUDOCRE</b> <br>
 * Entidad: <b>ADVCTEXTUDOCRE</b>
 */
public interface ITextoDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de valores de tipo texto de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public abstract List getValues(String idElementoCF, int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo texto de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento);

	/**
	 * Obtiene la lista de valores de tipo texto de la ficha.
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
	public CampoTextoVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento);

	/**
	 * Inserta un valor de tipo texto (corto o largo).
	 * 
	 * @param value
	 *            Valor de tipo texto corto a insertar.
	 * @return Valor de tipo texto corto insertado.
	 */
	public abstract CampoTextoVO insertValue(final CampoTextoVO value);

	/**
	 * Modifica un valor de tipo texto (corto o largo).
	 * 
	 * @param value
	 *            Información del campo de tipo texto (corto o largo).
	 */
	public abstract void updateValue(final CampoTextoVO value);

	/**
	 * Elimina un valor de tipo texto (corto o largo)
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
	 * Elementos todos los valores de texto que pertenezcan al elementoCF pasado
	 * como parametro
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
	public abstract CampoTextoVO getValue(String idElementoCF, String idCampo,
			String valor);

	/**
	 * Elimina el campo de la ficha
	 * 
	 * @param idElementoCF
	 * @param idCampo
	 * @param orden
	 */
	public void deleteValue(String idElementoCF, String idCampo, int orden);

	public int getMaxOrden(String idElemento, String idCampo);
}