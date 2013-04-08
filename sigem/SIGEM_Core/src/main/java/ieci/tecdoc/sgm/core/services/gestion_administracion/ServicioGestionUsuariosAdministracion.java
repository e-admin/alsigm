package ieci.tecdoc.sgm.core.services.gestion_administracion;

public interface ServicioGestionUsuariosAdministracion {

	/**
	 * Método que obtiene la URL de login para una aplicacion de back office
	 * @throws GestionUsuariosAdministracionException En caso de producirse algún error
	 */
	public String obtenerDireccionLogado()  throws GestionUsuariosAdministracionException;
		
	/**
	 * Método que obtiene la URL de desconexión para una aplicacion de back office
	 * @throws GestionUsuariosAdministracionException En caso de producirse algún error
	 */
	public String obtenerDireccionDeslogado()  throws GestionUsuariosAdministracionException;
}
