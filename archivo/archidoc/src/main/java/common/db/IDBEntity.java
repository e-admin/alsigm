package common.db;

public interface IDBEntity {

	/**
	 * Obtiene el nombre de la entidad
	 */
	public String getTableName();

	/**
	 * Obtiene el count de la entidad
	 * 
	 * @return Entero que define el número de registros de la tabla
	 */
	public int getCount();

}
