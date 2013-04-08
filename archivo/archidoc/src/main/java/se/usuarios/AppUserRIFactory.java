package se.usuarios;

/**
 * Factoría de interfaces remotos a la gestión de usuarios de la aplicación.
 */
public class AppUserRIFactory {
	/**
	 * Obtiene un interfaz remoto de gestión de usuarios.
	 * 
	 * @return Interfaz remoto de gestión de usuarios.
	 */
	public static AppUserRI createAppUserRI() {
		return new AppUserRIImpl();
	}
}
