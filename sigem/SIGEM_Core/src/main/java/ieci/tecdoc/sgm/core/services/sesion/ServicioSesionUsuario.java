package ieci.tecdoc.sgm.core.services.sesion;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.security.cert.X509Certificate;

public interface ServicioSesionUsuario {

		/**
		* Permite al usuario acceder al sistema si posee las credenciales adecuadas.
		* En este caso, la información debe ser previamente recuperada de la validación de 
	    * los datos de usuario contra un aplicativo externo.
	    *
	    * @param actSessionId Identificador de sesión actual.
	    * @param user Login de usuario.
	    * @param email Correo del usuario.
	    * @param senderId Identificador del remitente (NIF).
	    * @return Un identificador de sesión.
	    * @throws SesionUsuarioException 
	    */
	   public String login(String actSessionId, String nombre, String apellidos, String email, String senderId, Entidad entidad) throws SesionUsuarioException;
	   
	   /**
	    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
	    * En este caso debe poseer un certificado Además su
	    * certificado no debe estar revocado.
	    *
	    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
	    * @param authId Identificador de autenticación.
	    * @param certificate Certificado presentado (credencial).
	    * @return Un identificador de sesión.
	    * @throws SesionUsuarioException Si se produce algún error. 
	    */	   
	   public String login(String actSessionId, String authId, X509Certificate certificate, Entidad entidad) throws SesionUsuarioException; 	   

	   
	   /**
	    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
	    * En este caso debe poseer un certificado emitido por una CA de las que
	    * se encuentran en la lista de la política asociada al procedimiento que se pasa como parámetro.
	    * 
	    * @param procedureId Identificador del procedimiento.
	    * @param certificate Certificado presentado (credencial).
	    * @return Un identificador de sesión.
	    * @throws SesionUsuarioException Si se produce algún error. 
	    */
	   public String login(String procedureId, X509Certificate certificate, Entidad entidad) throws SesionUsuarioException; 

	   /**
	    * Desconecta a un usuario del sistema.
	    *
	    * @param sessionId Identificador de sesión.
	    * @throws SesionUsuarioException Si se produce algún error.
	    */
	   public void logout(String sessionId, Entidad entidad) throws SesionUsuarioException;

	   /**
	    * Crea una nueva sesión de usuario en el sistema. Esta sesión de usuario es compartida
	    * por las aplicaciones de tramitación de SIGEM.
	    * @param poSessionUsuario Datos de la sesión de usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public void crearSesion(SesionUsuario poSessionUsuario, Entidad entidad) throws SesionUsuarioException;
	   
	   /**
	    * Elimina una sesión de usuario del sistema. El usuario estará "deslogado" de todas las
	    * aplicaciones de tramitación de SIGEM.
	    * @param psIdSesion Identificador de sesión.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public void borrarSesion(String psIdSesion, Entidad entidad) throws SesionUsuarioException;	   
	   
	   /**
	    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
	    * máximo de existencia definido por configuración.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public void limpiarSesiones(Entidad entidad) throws SesionUsuarioException;	
	   
	   /**
	    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
	    * máximo que llega como parámetro.
	    * @param timeout Intervalo de tiempo hasta el momento actual máximo para mantener sesiones.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public void limpiarSesiones(int timeout, Entidad entidad) throws SesionUsuarioException;
	   
	   /**
	    * Obtiene todos los datos de la sesión de usuario.
	    * @param sessionId Identificador de la sesión de usuario.
	    * @return SesionUsuario Datos de la sesión de usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public SesionUsuario obtenerSesion(String sessionId, Entidad entidad) throws SesionUsuarioException;	
	   
	   /**
	    * Obtiene la información personal del usuario que inició la sesión en el sistema.
	    * @param sessionId Identificador de sesión.
	    * @return InfoUsuario Información personal del usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public InfoUsuario getInfoUsuario(String sessionId, Entidad entidad) throws SesionUsuarioException;
	   
	   /**
	    * Obtiene el identificador del método de autenticación utilizado para iniciar la sesión
	    * en el sistema.
	    * @param sessionId Identificador de sesión.
	    * @return Identificador del método de autenticación utilizado al crear la sesión.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public String getIdMetodoAutenticacion(String sessionId, Entidad entidad) throws SesionUsuarioException;
	   
	   /**
	    * Obtiene la información del método de autenticación utilizado para iniciar la sesión.
	    * @param sessionId Identificador de la sesión.
	    * @return MetodoAutenticacion Objeto con la información sobre el método de autenticación.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	   public MetodoAutenticacion getMetodoAutenticacion(String sessionId, Entidad entidad) throws SesionUsuarioException;
	   
	   
}
