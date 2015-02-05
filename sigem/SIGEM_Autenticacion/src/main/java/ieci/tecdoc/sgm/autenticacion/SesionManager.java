package ieci.tecdoc.sgm.autenticacion;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadCodigosError;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.database.SesionInfoDatos;
import ieci.tecdoc.sgm.catalogo_tramites.CatalogoTramites;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conector;
import ieci.tecdoc.sgm.autenticacion.Configuracion;
import ieci.tecdoc.sgm.rde.ContenedorDocumentosManager;
import ieci.tecdoc.sgm.rde.datatypes.DocumentoTemporal;
import ieci.tecdoc.sgm.rde.datatypes.DocumentosTemporales;


/**
 * Clase para la gestión de sesiones. Proporciona una fachada compuesta por métodos
 * estáticos con los operaciones necesarias para la administración de sesiones.
 * 
 * @author IECISA
 *
 */
public abstract class SesionManager {

	private static final Logger logger = Logger.getLogger(SesionManager.class);
			
	/**
	 * Añade una nueva entrada con la información de la sesión de un usuario
	 * en la base de datos.
	 * 
	 * @param sessionInfo Información sobre la sesión.
	 * @throws AutenticacionExcepcion En caso de producirse algún error.
	 */
   public static void add(SesionInfo sessionInfo, String entidad) throws AutenticacionExcepcion {
      SesionInfoDatos data = new SesionInfoDatos(sessionInfo); 
      try {
    	  data.insert(entidad);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al crear una sesion de usuario [add][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al crear una sesion de usuario [add][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_ADD_SESSION_INFO);
      }
   }

   /**
    * Elimina una determina sesión de la base de datos.
    * 
    * @param sessionId Identificador de session a eliminar.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static void delete(String sessionId, String entidad) throws AutenticacionExcepcion {
      SesionInfoDatos sessionInfo = new SesionInfoDatos();
      try {
    	  sessionInfo.setSessionId(sessionId);
    	  sessionInfo.delete(entidad);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al eliminar una sesion de usuario [delete][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al eliminar una sesion de usuario [delete][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_DELETE_SESSION_INFO);
      }
   }
   
   /**
    * Elimina todas las sesiones caducadas.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static void clean(String entidad) throws AutenticacionExcepcion {
      try {
         int timeout = new Integer(Configuracion.getTimeout()).intValue();
         ContenedorDocumentosManager cdm = new ContenedorDocumentosManager();
         DocumentosTemporales docsTemp = cdm.getDocumentTmp(timeout, entidad);
         SesionInfoDatos.clean(timeout, entidad);
         for(int i=0; i<docsTemp.count(); i++){
        	 DocumentoTemporal docTemp = (DocumentoTemporal)docsTemp.get(i);
        	 cdm.deleteDocumentTmp(docTemp.getSessionId(), entidad);
        	 cdm.deleteDocument(docTemp.getSessionId(), docTemp.getGuid(), entidad);
         }
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al eliminar sesiones caducadas [clean][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al eliminar sesiones caducadas [clean][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_DELETE_SESSION_INFO);
      }
   }
   
   
   /**
    * Elimina todas las sesiones caducadas.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static void clean(int timeout, String entidad) throws AutenticacionExcepcion {
      try {
         ContenedorDocumentosManager cdm = new ContenedorDocumentosManager();
         DocumentosTemporales docsTemp = cdm.getDocumentTmp(timeout, entidad);
         SesionInfoDatos.clean(timeout, entidad);
         for(int i=0; i<docsTemp.count(); i++){
        	 DocumentoTemporal docTemp = (DocumentoTemporal)docsTemp.get(i);
        	 cdm.deleteDocumentTmp(docTemp.getSessionId(), entidad);
        	 cdm.deleteDocument(docTemp.getSessionId(), docTemp.getGuid(), entidad);
         }
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al eliminar sesiones caducadas [clean][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al eliminar sesiones caducadas [clean][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_DELETE_SESSION_INFO);
      }
   }
   

   /**
    * Método que obtiene la información asociada a una determinada sesión.
    * @param sessionId Identificador de sesión de la que se quiere obtener la información.
    * @return SessionInfo Con la información de la sesión.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static SesionInfo get(String sessionId, String entidad) throws AutenticacionExcepcion {
      SesionInfoDatos sessionInfo = new SesionInfoDatos();
      try {
         sessionInfo.load(sessionId, entidad);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al recuperar sesion de usuario [get][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al recuperar sesion de usuario [get][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_GET_SESSION_INFO);
      }
      return sessionInfo;
   }

   /**
    * Método que devuelve los datos del usuario asociado al identificador de sesión
    * que llega como parámetro.
    * @param sessionId Cadena con el identificador de sesión.
    * @return Sender Objeto con los datos del usuario.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static Solicitante getSender(String sessionId, String entidad) throws AutenticacionExcepcion {
      return get(sessionId, entidad).getSender();
   }

   /**
    * Método que devuelve la cadena con el identificador de conector asociado
    * al identificador de sesión que llega como parámetro.
    * @param sessionId Cadena con el identificador de sesión.
    * @return String Identificador de conector.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static String getHookId(String sessionId, String entidad) throws AutenticacionExcepcion
   {
      String hookId = null;
      SesionInfoDatos sessionInfo = new SesionInfoDatos();
      
      try {
         sessionInfo.loadHook(sessionId, entidad);
         hookId = sessionInfo.getHookId();
      
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al recuperar id del conector de la sesion [getHookId][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al recuperar id del conector de la sesion [getHookId][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_GET_SESSION_INFO);
      }
      
      return hookId;
   }

   /**
    * Método que obtiene el conector asociado a al identificador de sesión
    * que llega como parámetro.
    * @param sessionId Cadena con el identificador de sesión.
    * @return Conector asociado al identificador de sesión.
    * @throws AutenticacionExcepcion En caso de producirse algún error.
    */
   public static Conector getHook(String sessionId, String entidad) throws AutenticacionExcepcion
   {
      String hookId;
      Conector hook = null;
      CatalogoTramites catalogoTramites = new CatalogoTramites();
      
      try {
         hookId = getHookId(sessionId, entidad);
         hook = catalogoTramites.getHook(hookId, entidad);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al recuperar conector de la sesion [getHook][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al recuperar conector de la sesion [getHook][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_GET_SESSION_INFO);
      }
      
      return hook;
   }

}