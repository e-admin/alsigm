package organizacion.model.bi;

import java.util.List;

import organizacion.model.EstructuraOrganizacion;
import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;

public interface GestionOrganizacionBI {

	/*****************************/
	/** Metodos de ORGANIZACION **/
	/*****************************/
	public OrganizacionVO insertarOrganizacion(
			final OrganizacionVO organizacionVO);

	public void actualizarOrganizacion(final OrganizacionVO organizacionVO);

	public void eliminarOrganizacion(final String idOrganizacion);

	public OrganizacionVO getOrganizacionById(final String idOrganizacion);

	public List buscarOrganizaciones(final OrganizacionVO organizacionVO);

	public List buscarOrganizaciones(final OrganizacionVO organizacionVO,
			final UserOrganoVO userOrganoVO);

	public List getOrganizaciones();

	public List getInstituciones();

	public List getOrganizacionesByIdPadre(final String idPadre);

	public List getOrganizacionesEstadoPadre(final String estado);

	public EstructuraOrganizacion getEstructuraOrganizacion();

	public EstructuraOrganizacion getEstructuraOrganizacion(String subtreeNode);

	public List getAncestors(String idElemento);

	/*******************************/
	/** Metodos de USUARIO-ORGANO **/
	/*******************************/
	public UserOrganoVO insertarUsuarioOrgano(final UserOrganoVO userOrganoVO);

	public void actualizarUsuarioOrgano(final UserOrganoVO userOrganoVO);

	public void eliminarUsuarioOrgano(final String idUsuario);

	public UserOrganoVO getUsuarioById(final String idUsuario);

	public UserOrganoVO getUsuario(final String idUsuario, final String idOrgano);

	public List getUsuarios();

	public List getUsuariosByOrgano(final String idOrgano);

	public List buscarUsuariosOrganos(final UserOrganoVO userOrganoVO);

	public void eliminarUsuariosOrgano(final String idOrgano);
}