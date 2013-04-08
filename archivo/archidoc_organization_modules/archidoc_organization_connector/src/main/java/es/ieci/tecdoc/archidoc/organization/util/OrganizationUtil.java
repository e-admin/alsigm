package es.ieci.tecdoc.archidoc.organization.util;


/**
 * Clase de utilidad para organizacion
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public final class OrganizationUtil {

	/**
	 * Permite obtener el nombre del datasource para una entidad especifica
	 * @param dsName Nombre del datasource
	 * @param entity Entidad
	 * @return Nombre del datasource incluyendo la entidad
	 */
	public static String composeDsName(final String dsName, final String entity) {

		String datasourceName = dsName;

		if ((dsName!=null)&&(entity!=null)&&!"".equals(dsName)&&!"".equals(entity)){
			if (dsName.indexOf('{')>-1) {
				datasourceName = dsName.replaceFirst("\\{\\d\\}", entity);
			} else {
				datasourceName += entity;
			}
		}

		return datasourceName;
	}

	/**
	 * Constructor privado
	 */
	private OrganizationUtil(){

	}
}
