package ieci.tecdoc.sgm.core.services.admsesion.administracion;

public interface ServicioAdministracionSesionesAdministrador {

	public abstract String nuevaSesion(String usuario, int tipoUsuario);

	public abstract String nuevaSesionEntidad(String key, String idEntidad);

	public abstract boolean validarSesion(String key);

	public abstract boolean validarSesionEntidad(String key_entidad,
			String idAplicacion);

	public abstract void caducarSesion(String key);

	public abstract void caducarSesionEntidad(String key_entidad);

	public abstract Sesion obtenerSesion(String key);

	public abstract Sesion obtenerSesionEntidad(String key_entidad);

	public abstract boolean modificarDatosSesion(String key,
			String datosEspecificos);

}