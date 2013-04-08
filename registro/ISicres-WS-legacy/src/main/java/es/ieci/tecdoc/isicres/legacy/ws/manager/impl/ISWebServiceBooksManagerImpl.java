package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.PermisosManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceBooksManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfEsquemaLibroFieldVOToArrayOfWSFieldMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.MapOfBaseLibroVOWithPermisosLibroVOToArrayOfWSBookMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSField;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSBook;

/**
 *
 * @author IECISA
 *
 */
public class ISWebServiceBooksManagerImpl implements ISWebServiceBooksManager {

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceBooksManager#
	 * getBookSchema(int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	public ArrayOfWSField getBookSchema(int bookIdentification,
			Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// creamos el objeto BaseLibroVO mediante su id
		BaseLibroVO libro = new BaseLibroVO(Integer
				.toString(bookIdentification));

		// obtenemos el esquema del libro
		EsquemaLibroVO esquemaLibro = getLibroManager().getEsquemaLibro(
				usuario, libro);

		ArrayOfWSField result = (ArrayOfWSField) new ListOfEsquemaLibroFieldVOToArrayOfWSFieldMapper()
		.map(esquemaLibro.getCamposLibro());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceBooksManager#
	 * getInputBooks(es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	@SuppressWarnings("unchecked")
	public ArrayOfWSBook getInputBooks(Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos listado de libros de Salida
		List<BaseLibroVO> libros = getLibroManager().findLibrosEntradaByUser(
				usuario);

		Map<BaseLibroVO, PermisosLibroVO> permisosByLibro = getPermisosByLibroAndUser(
				libros, usuario);

		ArrayOfWSBook result = (ArrayOfWSBook) new MapOfBaseLibroVOWithPermisosLibroVOToArrayOfWSBookMapper(
				permisosByLibro).map(libros);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceBooksManager#
	 * getOutputBooks(es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	@SuppressWarnings("unchecked")
	public ArrayOfWSBook getOutputBooks(Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		// Logeamos al usuario
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// obtenemos listado de libros de Salida
		List<BaseLibroVO> libros = getLibroManager().findLibrosSalidaByUser(
				usuario);

		// generamos la informacion referente a libros WSBook mediante listado
		// de libros (BaseLibroVO) y el usuario
		Map<BaseLibroVO, PermisosLibroVO> permisosByLibro = getPermisosByLibroAndUser(
				libros, usuario);

		ArrayOfWSBook result = (ArrayOfWSBook) new MapOfBaseLibroVOWithPermisosLibroVOToArrayOfWSBookMapper(
				permisosByLibro).map(libros);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	public LibroManager getLibroManager() {
		return libroManager;
	}

	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
	}

	public PermisosManager getPermisosManager() {
		return permisosManager;
	}

	public void setPermisosManager(PermisosManager permisosManager) {
		this.permisosManager = permisosManager;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioAdapter) {
		this.usuarioBuilder = usuarioAdapter;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	/**
	 * Metodo que obtiene un listado de objetos {@link WSBook} a partir de un
	 * listado de objetos {@link BaseLibroVO} y un {@link UsuarioVO}
	 *
	 * @param listadoLibros
	 * @param usuario
	 * @return listado de objetos {@link WSBook}
	 */
	protected Map<BaseLibroVO, PermisosLibroVO> getPermisosByLibroAndUser(
			List<BaseLibroVO> libros, UsuarioVO usuario) {
		Map<BaseLibroVO, PermisosLibroVO> result = new HashMap<BaseLibroVO, PermisosLibroVO>();

		for (BaseLibroVO libro : libros) {
			// obtenemos los permisos del usuario sobre el libro
			PermisosLibroVO permisosLibro = getPermisosManager()
					.getPermisosLibro(libro, usuario);

			result.put(libro, permisosLibro);
		}

		return result;
	}

	// Members
	protected LibroManager libroManager;
	protected LoginManager loginManager;
	protected PermisosManager permisosManager;

	protected UsuarioVOBuilder usuarioBuilder;
}
