/**
 * 
 */
package ieci.tecdoc.sgm.admin;

import ieci.tecdoc.sgm.admin.beans.UsuarioImpl;
import ieci.tecdoc.sgm.admin.beans.Usuarios;
import ieci.tecdoc.sgm.admin.database.UsuarioDatos;
import ieci.tecdoc.sgm.admin.interfaces.Usuario;


import org.apache.log4j.Logger;

/**
 * @author 66018987
 *
 */
public class AdministracionUsuarioManager {

	private static final Logger logger = Logger.getLogger(AdministracionUsuarioManager.class);

	/**
	 * 
	 */
	public AdministracionUsuarioManager() {
	}
	public void altaUsuario(UsuarioImpl poUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Creando Usuario...");
		}
		if(poUsuario == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos(poUsuario);
		try {
			oDatos.add();
			if(logger.isDebugEnabled()) {
				logger.debug("Usuario creado.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error creando nuevo Usuario.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error creando nuevo Usuario.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
	}
	
	public void bajaUsuario(UsuarioImpl poUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Eliminando Usuario...");
		}		
		if(poUsuario== null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}			
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos=new UsuarioDatos(poUsuario);
		try {
			oDatos.delete();
			if(logger.isDebugEnabled()) {
				logger.debug("Usuario eliminado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error eliminando nuevo Usuario.", e);
			}
		}
		catch(Exception e) {
			logger.error("Error eliminando nuevo Usuario.", e);
		}
	}

	public Usuario actualizarUsuario(UsuarioImpl poUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Actualizando Usuario...");
		}				
		if(poUsuario== null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos(poUsuario);
		try {
			oDatos.update();
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil actualizado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error actualizando Perfil.", e);
			}
			throw e;
		}
		catch(Exception e) {
			logger.error("Error actualizando Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return poUsuario;
	}

	
	public Usuario obtenerUsuario(String usuario) throws AdministracionException {
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Perfil...");
		}				
		if(usuario == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos();
		try{
			oDatos.load(usuario);
			if(logger.isDebugEnabled()){
				logger.debug("Perfil obtenido.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error obteniendo Perfil.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error obteniendo Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return oDatos;
	}
	
	public Usuarios obtenerUsuarios(String idEntidad) throws AdministracionException {
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo usuarios...");
		}				
		if(idEntidad == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos();
		Usuarios usuarios = null;
		try{
			usuarios = oDatos.loadEntityUsers(idEntidad);
			
			if(logger.isDebugEnabled()){
				logger.debug("Usuarios obtenidos.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error obteniendo usuarios.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error obteniendo usuarios.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return usuarios;
	}
	
	public boolean autenticaUsuario(String usuario, String password) throws AdministracionException {
		if(logger.isDebugEnabled()){
			logger.debug("Autenticando usuario...");
		}				
		if(usuario == null || password == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos();
		boolean autenticado = false;
		try{
			autenticado = oDatos.authUser(usuario, password);
			
			if(logger.isDebugEnabled()){
				logger.debug("Usuario autenticado.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error autenticando usuario.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error autenticando usuario.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return autenticado;
	}
	
	public void actualizaPassword(Usuario poUsuario, String newPassword) throws AdministracionException {
		if(logger.isDebugEnabled()){
			logger.debug("Actualizando contraseña...");
		}				
		if(poUsuario == null || newPassword == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		UsuarioDatos oDatos = new UsuarioDatos(poUsuario);
		try{
			oDatos.updatePassword(newPassword);
			
			if(logger.isDebugEnabled()){
				logger.debug("Contraseña actualizada.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error actualizando contraseña.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error actualizando contraseña.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}

	}
	
	


}
