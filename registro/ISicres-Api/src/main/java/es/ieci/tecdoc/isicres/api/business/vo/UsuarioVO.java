package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.List;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class UsuarioVO extends BaseUsuarioVO {

	private static final long serialVersionUID = -6272248632850674808L;

	/**
	 * Departamento al que pertenece el usuario
	 */
	protected DepartamentoUsuarioVO departamentoUsuario;

	/**
	 *
	 */
	protected ConfiguracionUsuarioVO configuracionUsuario;

	/**
	 * listado de grupos a los que pertenece el usuario
	 */
	protected List<GrupoUsuarioVO> gruposUsuario;

	/**
	 * Listado de oficinas a las que pertenece el usuario {@link OficinaVO}
	 */
	protected List<OficinaVO> oficinas;

	/**
	 * Información sobre los permisos efectivos que tiene el usuario
	 */
	protected PermisosUsuarioVO permisos;

	public ConfiguracionUsuarioVO getConfiguracionUsuario() {
		if (null == this.configuracionUsuario) {
			this.configuracionUsuario = new ConfiguracionUsuarioVO();
		}
		return configuracionUsuario;
	}

	public void setConfiguracionUsuario(
			ConfiguracionUsuarioVO configuracionUsuario) {
		this.configuracionUsuario = configuracionUsuario;
	}

	public PermisosUsuarioVO getPermisos() {
		return permisos;
	}

	public void setPermisos(PermisosUsuarioVO permisos) {
		this.permisos = permisos;
	}

	public List<GrupoUsuarioVO> getGruposUsuario() {
		return gruposUsuario;
	}

	public void setGruposUsuario(List<GrupoUsuarioVO> gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}

	public List<OficinaVO> getOficinas() {
		return oficinas;
	}

	public void setOficinas(List<OficinaVO> oficinas) {
		this.oficinas = oficinas;
	}

	public DepartamentoUsuarioVO getDepartamentoUsuario() {
		return departamentoUsuario;
	}

	public void setDepartamentoUsuario(DepartamentoUsuarioVO departamentoUsuario) {
		this.departamentoUsuario = departamentoUsuario;
	}





}
