package ieci.tecdoc.sgm.core.services.admsesion.backoffice;

public interface ServicioAdministracionSesionesBackOffice {

	public abstract String nuevaSesion(String usuario, String idEntidad);

	public abstract boolean validarSesion(String key) ;

	public abstract void caducarSesion(String key);

	public abstract Sesion obtenerSesion(String key);

	public abstract boolean modificarDatosSesion(String key,
			String datosEspecificos);

}