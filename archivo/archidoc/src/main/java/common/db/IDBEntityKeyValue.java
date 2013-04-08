package common.db;

import ieci.core.db.DbColumnDef;

import common.vos.IKeyValue;

/**
 * Gestión de Entitys que tienen método genérico de getById
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IDBEntityKeyValue extends IDBEntity {

	/**
	 * Obtiene el registro por su id
	 * 
	 * @param id
	 *            Cadena que contiene el identificador del objeto
	 * @return Objeto con los datos
	 */
	public boolean existeByKey(String id);

	/**
	 * Obtiene la columna ID
	 * 
	 * @return
	 */
	public DbColumnDef getKeyField();

	/**
	 * Obtiene el registro por su key
	 * 
	 * @param key
	 *            Cadena que contiene la key
	 * @return
	 */
	public IKeyValue getByKey(String key);

	/**
	 * Obtiene el registro por su valor
	 * 
	 * @param id
	 *            Cadena que contiene el identificador del objeto
	 * @return Objeto con los datos
	 */
	public boolean existeByValue(String key, String value);

	/**
	 * Obtiene la columna ID
	 * 
	 * @return
	 */
	public DbColumnDef getValueField();

	/**
	 * Obtiene el registro por su key
	 * 
	 * @param key
	 *            Cadena que contiene la key
	 * @return
	 */
	public IKeyValue getByValue(String value);

	/**
	 * Obtiene la clausula where por el campo id
	 * 
	 * @param id
	 *            Cadena que contiene el valor del ID
	 * @return WHERE ID='<ID>'
	 */
	public String getWhereClauseByValue(String value);

}
