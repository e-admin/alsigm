package util;

import common.util.StringUtils;

/**
 * Clase de utilidad para multientidad
 */
public class MultiEntityUtil {

	/**
	 * Permite obtener el nombre del datasource para una entidad específica
	 * 
	 * @param dsName
	 *            Nombre del datasource
	 * @param entity
	 *            Entidad
	 * @return Nombre del datasource incluyendo la entidad
	 */
	public static String composeDsName(String dsName, String entity) {

		String ds = dsName;

		if (StringUtils.isNotEmpty(dsName) && StringUtils.isNotEmpty(entity)) {
			ds = dsName.replaceFirst("\\{\\d\\}", entity);
		}

		return ds;
	}

}
