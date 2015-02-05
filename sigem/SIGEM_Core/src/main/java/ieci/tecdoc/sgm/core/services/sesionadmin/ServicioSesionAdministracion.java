package ieci.tecdoc.sgm.core.services.sesionadmin;

public interface ServicioSesionAdministracion {

	public SesionAdministracion iniciarSesion(SesionAdministracion poSesion) throws ServicioSesionAdministracionException;
	
	public SesionAdministracion obtenerSesion(String idSesion) throws ServicioSesionAdministracionException;
	
	public SesionAdministracion actualizarSesion(SesionAdministracion poSesion) throws ServicioSesionAdministracionException;
	
	public SesionAdministracion mantenerSesionActualizada(String idSesion) throws ServicioSesionAdministracionException;
	
	public void eliminarSesion(String idSesion) throws ServicioSesionAdministracionException;
	
	public void eliminarSesionesCaducadas(long timeout) throws ServicioSesionAdministracionException;
}
