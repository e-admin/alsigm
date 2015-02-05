package organizacion.model.bi;

import java.util.List;

import organizacion.model.EstructuraOrganizacion;
import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;
import organizacion.persistence.db.IOrganizacionDBEntity;
import organizacion.persistence.db.IUserOrganoDBEntity;

import common.bi.ServiceBase;
import common.bi.ServiceRepository;

public class GestionOrganizacionBIImpl extends ServiceBase implements
		GestionOrganizacionBI {

	private IOrganizacionDBEntity _organizacionDBEntity = null;
	private IUserOrganoDBEntity _userOrganoDBEntity = null;

	public GestionOrganizacionBIImpl(
			IOrganizacionDBEntity _organizacionDBEntity,
			IUserOrganoDBEntity _userOrganoDBEntity) {
		this._organizacionDBEntity = _organizacionDBEntity;
		this._userOrganoDBEntity = _userOrganoDBEntity;
	}

	public OrganizacionVO insertarOrganizacion(OrganizacionVO organizacionVO) {
		return this._organizacionDBEntity.insertOrganizacion(organizacionVO);
	}

	public UserOrganoVO insertarUsuarioOrgano(UserOrganoVO userOrganoVO) {
		return this._userOrganoDBEntity.insertUsuarioOrgano(userOrganoVO);
	}

	public void actualizarOrganizacion(OrganizacionVO organizacionVO) {
		this._organizacionDBEntity.updateOrganizacion(organizacionVO);
	}

	public void actualizarUsuarioOrgano(UserOrganoVO userOrganoVO) {
		this._userOrganoDBEntity.updateUsuarioOrgano(userOrganoVO);
	}

	public void eliminarOrganizacion(String idOrganizacion) {
		this._organizacionDBEntity.deleteOrganizacion(idOrganizacion);
	}

	public void eliminarUsuarioOrgano(String idUsuario) {
		this._userOrganoDBEntity.deleteUsuarioOrgano(idUsuario);
	}

	public OrganizacionVO getOrganizacionById(String idOrganizacion) {
		return this._organizacionDBEntity.getOrganizacionById(idOrganizacion);
	}

	public UserOrganoVO getUsuarioById(String idUsuario) {
		return this._userOrganoDBEntity.getUsuarioOrganoById(idUsuario);
	}

	public UserOrganoVO getUsuario(String idUsuario, String idOrgano) {
		return this._userOrganoDBEntity.getUsuarioOrgano(idUsuario, idOrgano);
	}

	public List buscarOrganizaciones(OrganizacionVO organizacionVO) {
		return this._organizacionDBEntity.searchOrganizaciones(organizacionVO);
	}

	public List buscarUsuariosOrganos(UserOrganoVO userOrganoVO) {
		return this._userOrganoDBEntity.searchUsuariosOrganos(userOrganoVO);
	}

	public EstructuraOrganizacion getEstructuraOrganizacion() {
		GestionOrganizacionBI wraperServiceOrganizacion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionOrganizacionBI();
		return new EstructuraOrganizacion(wraperServiceOrganizacion,
				getServiceClient());
	}

	public EstructuraOrganizacion getEstructuraOrganizacion(String subtreeNode) {
		GestionOrganizacionBI wraperServiceOrganizacion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionOrganizacionBI();
		return new EstructuraOrganizacion(wraperServiceOrganizacion,
				getServiceClient(), subtreeNode);
	}

	public List getOrganizaciones() {
		return this._organizacionDBEntity.getOrganizaciones();
	}

	public List getUsuarios() {
		return this._userOrganoDBEntity.getUsuarios();
	}

	public List getUsuariosByOrgano(String idOrgano) {
		return this._userOrganoDBEntity.getUsuariosByOrgano(idOrgano);
	}

	public List getOrganizacionesByIdPadre(String idPadre) {
		return this._organizacionDBEntity.getOrganizacionesByIdPadre(idPadre);
	}

	public List getInstituciones() {
		return this._organizacionDBEntity.getInstituciones();
	}

	public void eliminarUsuariosOrgano(String idOrgano) {
		this._userOrganoDBEntity.deleteUsuariosOrgano(idOrgano);
	}

	public List buscarOrganizaciones(OrganizacionVO organizacionVO,
			UserOrganoVO userOrganoVO) {
		return this._organizacionDBEntity.searchOrganizaciones(organizacionVO,
				userOrganoVO);
	}

	public List getOrganizacionesEstadoPadre(String estado) {
		return this._organizacionDBEntity.getOrganizacionesEstadoPadre(estado);
	}

	public List getAncestors(String idElemento) {
		return this._organizacionDBEntity.getAncestors(idElemento);
	}
}