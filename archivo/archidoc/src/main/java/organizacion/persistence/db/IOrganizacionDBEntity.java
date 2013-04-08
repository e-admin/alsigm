package organizacion.persistence.db;

import java.util.List;

import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;

/**
 * DBEntity para acceder a la tabla ORGANIZACION.
 */
public interface IOrganizacionDBEntity {

	public static final String INSTITUCION = "1";
	public static final String ORGANO = "2";

	public static final String NIVEL_RAIZ = "0";

	public static final String BORRADOR = "1";
	public static final String VIGENTE = "2";
	public static final String HISTORICO = "3";

	/**
	 * 
	 * @param organizacionVO
	 * @return {@link OrganizacionVO}
	 */
	public OrganizacionVO insertOrganizacion(final OrganizacionVO organizacionVO);

	public void updateOrganizacion(final OrganizacionVO organizacionVO);

	public void deleteOrganizacion(final String idOrganizacion);

	/**
	 * 
	 * @param idOrganizacion
	 * @return {@link OrganizacionVO}
	 */
	public OrganizacionVO getOrganizacionById(final String idOrganizacion);

	/**
	 * 
	 * @param organizacionVO
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List searchOrganizaciones(final OrganizacionVO organizacionVO);

	/**
	 * 
	 * @param organizacionVO
	 * @param userOrganoVO
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List searchOrganizaciones(final OrganizacionVO organizacionVO,
			final UserOrganoVO userOrganoVO);

	/**
	 * 
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List getOrganizaciones();

	/**
	 * 
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List getInstituciones();

	/**
	 * 
	 * @param idPadre
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List getOrganizacionesByIdPadre(final String idPadre);

	/**
	 * 
	 * @param estado
	 * @return Lista de {@link OrganizacionVO}
	 */
	public List getOrganizacionesEstadoPadre(final String estado);

	/**
	 * Permite obtener los ancestros de una organización
	 * 
	 * @param idElemento
	 *            Id del elemento
	 * @return Lista de ancestros
	 */
	public List getAncestors(String idElemento);
}