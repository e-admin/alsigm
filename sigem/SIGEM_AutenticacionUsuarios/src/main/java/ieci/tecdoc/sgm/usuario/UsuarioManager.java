package ieci.tecdoc.sgm.usuario;

import java.util.List;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.usuario.exception.UsuarioCodigosError;
import ieci.tecdoc.sgm.usuario.exception.UsuarioExcepcion;
import ieci.tecdoc.sgm.usuario.database.UsuarioDatos;
import ieci.tecdoc.sgm.usuario.database.UsuarioTabla;
import ieci.tecdoc.sgm.usuario.datatypes.Usuario;



/**
 * EJB sin estado que proporciona las operaciones sobre usuarios
 * 
 * @author IECISA
 * 
 */
public class UsuarioManager{
	private static final Logger logger = Logger.getLogger(UsuarioManager.class);
	
	
   public UsuarioManager(){
     //DOMConfigurator.configure("ieci/tecdoc/sgm/catalogo_tramites/resources/log4j.xml");
   }

   /**
    * Recupera la información de un usuario
    * @param usuario Usuario de acceso.
    * @param password Contraseña.
    * @return Usuario Datos de usuario.
    * @throws UsuarioExcepcion Si se produce algún error.
    */
   public static Usuario get(String usuario, String password, String entidad) throws UsuarioExcepcion {
	   UsuarioDatos user = new UsuarioDatos();

	   logger.debug("Get Usuario <-- Usuario: " + usuario + " Contraseña: " + password);
	     
	   try {
	      user.load(usuario, password, entidad);
	   } catch (UsuarioExcepcion exc) {
	      throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
	   } catch (Exception exc) {
	      throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
	   }
	   return user;
   }

   /**
    * Recupera la información de un usuario
    * @param usuario Usuario de acceso.
    * @param password Contraseña.
    * @return Usuario Datos de usuario.
    * @throws UsuarioExcepcion Si se produce algún error.
    */
   public static Usuario get(String idUsuario,String entidad) throws UsuarioExcepcion {
	   UsuarioDatos user = new UsuarioDatos();
	   user.setUsuario(idUsuario);
	   try {
	      user.load(entidad);
	   } catch (UsuarioExcepcion exc) {
	      throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
	   } catch (Exception exc) {
	      throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
	   }
	   return user;
   }

   
   /**
    * Elimina la información de un usuario
    * @param usuario Usuario de acceso.
    * @param password Contraseña.
    * @return Usuario Datos de usuario.
    * @throws UsuarioExcepcion Si se produce algún error.
    */
   public static Usuario delete(String usuario, String entidad) throws UsuarioExcepcion {
	   UsuarioDatos user = new UsuarioDatos();
	     
	   try {
		   user.setUsuario(usuario);
		   user.delete(entidad);
	   } catch (UsuarioExcepcion exc) {
		   throw new UsuarioExcepcion(UsuarioCodigosError.EC_DELETE_USER);
	   } catch (Exception exc) {
		   throw new UsuarioExcepcion(UsuarioCodigosError.EC_DELETE_USER);
	   }
	   return user;
   }
   
   
   public static List findUsers(CriterioBusquedaUsuario poCriteria,String entidad) throws UsuarioExcepcion{
	   logger.debug("Find Usuarios ");
	   UsuarioTabla oTabla = new UsuarioTabla();
	   List oLista = null;
	   try {
		   oLista = oTabla.buscarUsuarios(poCriteria, entidad);
	   } catch (UsuarioExcepcion e) {
		   logger.equals(e.getMessage());
		   throw e;
	   }
	   return oLista;
   }
   
   public static void updateUser(Usuario pcUser, String entidad) throws UsuarioExcepcion{
	   UsuarioDatos oDatos = new UsuarioDatos();
	   oDatos.setApellidos(pcUser.getApellidos());
	   oDatos.setEmail(pcUser.getEmail());
	   oDatos.setId(pcUser.getId());
	   oDatos.setNombre(pcUser.getNombre());
	   oDatos.setPassword(pcUser.getPassword());
	   oDatos.setUsuario(pcUser.getUsuario());
	   try {
		   oDatos.update(entidad);
	   } catch (UsuarioExcepcion e) {
		   logger.equals(e.getMessage());
		   throw e;
	   } 
   }

   public static void createUser(Usuario pcUser, String entidad) throws UsuarioExcepcion{
	   UsuarioDatos oDatos = new UsuarioDatos();
	   oDatos.setApellidos(pcUser.getApellidos());
	   oDatos.setEmail(pcUser.getEmail());
	   oDatos.setId(pcUser.getId());
	   oDatos.setNombre(pcUser.getNombre());
	   oDatos.setPassword(pcUser.getPassword());
	   oDatos.setUsuario(pcUser.getUsuario());
	   try {
		   oDatos.add(entidad);
	   } catch (UsuarioExcepcion e) {
		   logger.equals(e.getMessage());
		   throw e;
	   } 
   }

}
