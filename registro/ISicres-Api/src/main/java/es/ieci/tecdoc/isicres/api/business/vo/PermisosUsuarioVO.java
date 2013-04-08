package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que contendrá los permisos efectivos del usuario logueado actualmente
 * 
 * @author Iecisa
 * 
 */
public class PermisosUsuarioVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8709872037420254795L;

	/**
	 * permisos de aplicación de usuario
	 */
	protected PermisosAplicacionVO permisosAplicacion;

	/**
	 * {@link PermisosLibroVO} map que contendrá los permisos efectivos para
	 * los libros .
	 */
	protected Map permisosLibros;

	/**
	 * {@link BaseFiltroRegistroVO} Map que contentrá los filtros efectivos
	 * para los librs por usuario
	 */
	protected Map filtrosRegistroLibros;

	public PermisosUsuarioVO() {
		super();
		permisosLibros = new HashMap();

	}

	public PermisosAplicacionVO getPermisosAplicacion() {
		return permisosAplicacion;
	}

	public void setPermisosAplicacion(PermisosAplicacionVO permisosAplicacion) {
		this.permisosAplicacion = permisosAplicacion;
	}

	public void setPermisoLibro(String idLibro, PermisosLibroVO permisoLibro) {
		this.permisosLibros.put(idLibro, permisoLibro);
	}

	public PermisosLibroVO getPermisoLibro(String idLibro) {
		PermisosLibroVO result = null;
		result = (PermisosLibroVO) this.permisosLibros.get(idLibro);
		return result;
	}

	public Map getPermisosLibros() {
		return permisosLibros;
	}

	public void setPermisosLibros(Map permisosLibro) {
		this.permisosLibros = permisosLibro;
	}

	public void setPermisoRegistro(IdentificadorRegistroVO idRegistro,
			PermisosRegistroVO permisoRegistro) {
		String key = generateKeyRegistro(idRegistro);
		this.permisosLibros.put(key, permisoRegistro);
	}

	public PermisosRegistroVO getPermisoRegistro(
			IdentificadorRegistroVO idRegistro) {

		String key = generateKeyRegistro(idRegistro);
		PermisosRegistroVO result = (PermisosRegistroVO) this.permisosLibros
				.get(key);
		return result;
	}

	protected String generateKeyRegistro(IdentificadorRegistroVO id) {
		String result = "";
		result = id.getIdLibro() + "-" + id.getIdRegistro();
		return result;
	}

	public void setFiltroRegistro(String idLibro, BaseFiltroRegistroVO filtro) {
		this.filtrosRegistroLibros.put(idLibro, filtro);
	}

	public BaseFiltroRegistroVO getFiltroRegistro(String idLibro) {
		FiltroRegistroWhereSqlVO result = (FiltroRegistroWhereSqlVO) this.filtrosRegistroLibros
				.get(idLibro);
		return result;
	}

	public Map getFiltrosRegistroLibros() {
		return filtrosRegistroLibros;
	}

	public void setFiltrosRegistroLibros(Map filtrosRegistroLibros) {
		this.filtrosRegistroLibros = filtrosRegistroLibros;
	}

}
