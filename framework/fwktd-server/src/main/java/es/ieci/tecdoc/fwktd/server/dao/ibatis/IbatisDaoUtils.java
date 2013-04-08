package es.ieci.tecdoc.fwktd.server.dao.ibatis;

public class IbatisDaoUtils {
	/**
	 * 
	 * @param className
	 *            el nombre de la clase - devuelve "get" + className + "s"
	 * @return Devuelve el nombre de la query de select sobre una entidad.
	 */
	public static String getSelectQuery(String className) {
		return className + ".get" + className + "s";
	}

	/**
	 * 
	 * @param className
	 *            el nombre de la clase - devuelve "get" + className
	 * @return Devuelve el nombre de la consulta de búsqueda.
	 */
	public static String getFindQuery(String className) {
		return className + ".get" + className;
	}

	/**
	 * 
	 * @param className
	 *            el nombre de la clase - devuelve "add" + className
	 * @return Devuelve el nombre de la consulta insert.
	 */
	public static String getInsertQuery(String className) {
		return className + ".add" + className;
	}

	/**
	 * 
	 * @param className
	 *            el nombre de la clase - devuelve "update" + className
	 * @return Devuelve el nombre de la consulta de actualización.
	 */
	public static String getUpdateQuery(String className) {
		return className + ".update" + className;
	}

	/**
	 * 
	 * @param className
	 *            el nombre de la clase - devuelve "delete" + className
	 * @return Devuelve el nombre de la consulta de borrado.
	 */
	public static String getDeleteQuery(String className) {
		return className + ".delete" + className;
	}

	private IbatisDaoUtils() {

	}
}
