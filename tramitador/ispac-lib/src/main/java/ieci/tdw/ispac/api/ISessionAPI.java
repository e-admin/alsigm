/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/api/ISessionAPI.java,v $
 * $Revision: 1.8 $
 * $Date: 2008/12/30 17:09:31 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.session.Session;

import java.util.Locale;

public interface ISessionAPI
{

	/**
	 * Valida un usuario
	 * @param user Nombre del usuario
	 * @param password Clave del usuario
	 * @return Entrada en el sistema de autenticación de usuarios
	 * @throws ISPACException
	 */
	public IDirectoryEntry validate(String user, String password) throws ISPACException;
	
	
    /**
     * Conecta a un usuario a una aplicación, creando una sesión
     * @param remoteHost Host de conexión
     * @param user Nombre del usuario
     * @param userEntry Entidad del usuario en el sistema de autenticación
     * @param aplicacion Nombre de la aplicación
     * @param locale Locale.
     * @throws ISPACException
     */
    public void login(String remoteHost, String user, IDirectoryEntry userEntry, String aplicacion, Locale locale) throws ISPACException;

    /**
     * Obtiene las sesiones activas del usuario '<code>user</code>' para la aplicación '<code>aplicacion</code>'
     * @param user Nombre del usuario
     * @param aplicacion Nombre de la aplicación
     * @return Colección de Sesiones activas del usuario para la aplicación en cuestión
     * @throws ISPACException
     */
    public IItemCollection getActiveSessions(String user, String aplicacion) throws ISPACException;
    
    /**
     * Elimina una sesión y todos los datos/bloqueos asociados a la misma
     * @param ticket Identificador de la sesion
     * @throws ISPACException
     */
    public void deleteSession(String ticket) throws ISPACException;
    
    
    /**
     * Inicializa una sesión, y valida el ticket de conexión. Debe ser invocada por un cliente ya conectado.    
     * @param ticket
     * @param locale
     * @throws ISPACException
     */
    public void init(String ticket, Locale locale) throws ISPACException;

    /**
     * Almacena en base de datos el objeto session
     * @throws ISPACException
     */
    public void release() throws ISPACException;

    /**
     * Actualiza el valor de la variable que indica que la sesión sigue viva
     * @param ticket Identificador de la sesión
     * @throws ISPACException
     */
    public void keepAlive(String ticket) throws ISPACException;

    /**
     * Desconecta la sesión
     * @throws ISPACException
     */
    public void logout() throws ISPACException;

    /**
     * Comprobación si la sesión está activa
     * @return true en caso de que haya una sesión activa, false en caso contrario
     */
    public boolean testLogged();

    /**
     * @return Cadena que identifica la sesión
     */
    public String getTicket();

    /**
     * @return Nombre de usuario conectado
     */
    public String getUserName();
    
    /**
     * @return Nombre del usuario conectado
     */
    public String getRespName();

    /**
     * @return sesión activa
     */
    public Session getSession();

    /**
     * @return Contexto del cliente
     */
    public ClientContext getClientContext();

    /**
     * @return Acceso al API de invesflow
     */
    public IInvesflowAPI getAPI();

}