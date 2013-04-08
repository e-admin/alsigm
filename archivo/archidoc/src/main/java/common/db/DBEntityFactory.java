package common.db;

import org.apache.commons.lang.StringUtils;

import common.exceptions.UncheckedArchivoException;
import common.util.DBFactoryConstants;

/**
 * Clase encargada de crear los objetos de acceso a datos
 */
public abstract class DBEntityFactory {

	private static IDBEntityFactory instance = null;

	/**
	 * Obtiene la factoría para una determinada base de datos
	 *
	 * @param dbFactoryClass
	 *            {@link DBFactoryConstants}
	 * @return
	 */
	public static IDBEntityFactory getInstance(String dbFactoryClass) {
		if (instance == null) {
			try {
				if(StringUtils.isNotEmpty(dbFactoryClass)){
					instance = (IDBEntityFactory) Class.forName(dbFactoryClass)
							.newInstance();
				}
				else{
					throw new UncheckedArchivoException("Error en la comunicación con la base de datos. Factoría no definida");
				}
			} catch (ClassNotFoundException cnfe) {
				throw new UncheckedArchivoException(cnfe);
			} catch (InstantiationException ie) {
				throw new UncheckedArchivoException(ie);
			} catch (IllegalAccessException iae) {
				throw new UncheckedArchivoException(iae);
			}
		}
		return instance;
	}

}