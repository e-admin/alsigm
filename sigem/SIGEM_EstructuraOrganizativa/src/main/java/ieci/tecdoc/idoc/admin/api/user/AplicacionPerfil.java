package ieci.tecdoc.idoc.admin.api.user;

public class AplicacionPerfil {

	private static final int PERFIL_SIN_PERMISOS=0;
	private static final int PERFIL_USUARIO_STANDAR=1;
	private static final int PERFIL_USUARIO_ADMINISTRADOR=3	;
	
	private String idetificador;
	private String descripcion;
	private int  perfil;
	private boolean editableUsuarioEstandar;
	private boolean editableSinPermisos;
	private boolean editableUsuarioAdministrador;
	
	public AplicacionPerfil() {
		this.perfil = 0;
		this.idetificador = null;
		this.descripcion = null;
		this.editableSinPermisos = true;
		this.editableUsuarioEstandar = false;
		this.editableUsuarioAdministrador = false;		
	}
	
	public AplicacionPerfil(String idetificador, String descripcion,int perfil, boolean editableSinPermisos, boolean editableUsuarioEstandar, boolean editableUsuarioAdministrador) {
		this.perfil = perfil;
		this.idetificador = idetificador;
		this.descripcion = descripcion;
		this.editableUsuarioEstandar = editableUsuarioEstandar;
		this.editableSinPermisos = editableSinPermisos;
		this.editableUsuarioAdministrador = editableUsuarioAdministrador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEditableSinPermisos() {
		return editableSinPermisos;
	}

	public void setEditableSinPermisos(boolean editableSinPermisos) {
		this.editableSinPermisos = editableSinPermisos;
	}

	public boolean isEditableUsuarioAdministrador() {
		return editableUsuarioAdministrador;
	}

	public void setEditableUsuarioAdministrador(boolean editableUsuarioAdministrador) {
		this.editableUsuarioAdministrador = editableUsuarioAdministrador;
	}

	public boolean isEditableUsuarioEstandar() {
		return editableUsuarioEstandar;
	}

	public void setEditableUsuarioEstandar(boolean editableUsuarioEstandar) {
		this.editableUsuarioEstandar = editableUsuarioEstandar;
	}

	public String getIdetificador() {
		return idetificador;
	}

	public void setIdetificador(String idetificador) {
		this.idetificador = idetificador;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}
	
	
	
	
	
	
}
