package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.ConectorDatos;

import org.apache.log4j.Logger;

/**
 * Gestor de conectores que implementa operaciones de recuperación, almacenaje,
 * actualización y eliminación de conectores
 *
 */
public class ConectorManager {
  
	private static final Logger logger = Logger.getLogger(ConectorManager.class);
  
  private static final boolean isDebugeable = true;
 
  protected ConectorManager() {
  }

  /**
   * Recupera la información de un conector
   * @param hookId Identificador del conector.
   * @return Hook El conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static Conector get(String hookId, String entidad) throws CatalogoTramitesExcepcion {
     ConectorDatos hook = new ConectorDatos();

     logger.debug("Get Hook <-- HookId: " + hookId);
     
     try {
    	 hook.load(hookId, entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al obtener un conector [get][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOK);
     } catch (Exception exc) {
    	 logger.error("Error al obtener un conector [get][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOK);
     }
     
     return hook;
  }


  /**
   * Añade un nuevo conector al catálogo. 
   * @param adressee Información del conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void add(Conector hook, String entidad) throws CatalogoTramitesExcepcion {
     ConectorDatos data = new ConectorDatos(hook);

     logger.debug("Add Hook <-- Hook: " + hook.toString());
     
     try {
    	 data.insert(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al insertar un conector [add][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOK);
     } catch (Exception exc) {
    	 logger.error("Error al insertar un conector [add][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOK);
     }
  }

  
  /**
   * Elimina un conector.
   * @param hookId Identificador del conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void delete(String hookId, String entidad) throws CatalogoTramitesExcepcion {
     ConectorDatos hook = new ConectorDatos();

     logger.debug("Delete Hook <-- HookId: " + hookId);
     
     try{
    	 //TODO Falta comprobar si esta referenciado el conector
        /*if (ProcedureManager.isDocumentReferenced(addresseeId)) {
          throw new ProcedureCatalogException(ProcedureCatalogErrorCodes.EC_ADDRESSEE_USED);
        }*/
    	 hook.setId(hookId);
    	 hook.delete(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al eliminar un conector [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOK);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar un conector [delete][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOK);
     }
  }

  
  /**
   * Actualiza el conector que se pasa como parámetro. 
   * @param hook Conector con los nuevos datos.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void update(Conector hook, String entidad) throws CatalogoTramitesExcepcion {
     ConectorDatos data = new ConectorDatos(hook);

     logger.debug("Update Hook <-- Hook: " + hook.toString());

     try {
    	 data.update(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al actualizar un conector [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOK);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar un conector [delete][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOK);
     }
  }

  
  /**
   * Recupera la lista de conectores.
   * @param hookType Tipo de conector (-1 si se quieren recoger todos)
   * @return La lista mencionada.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static Conectores getHooks(int hookType, String entidad) throws CatalogoTramitesExcepcion {
    Conectores hooks = null;

    logger.debug("getHooks");
     
    try {
    	hooks = ConectorDatos.getHooks(hookType, entidad);
    } catch (CatalogoTramitesExcepcion exc) {
    	logger.error("Error obtener conectores [getHooks][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
    } catch (Exception exc) {
    	logger.error("Error obtener conectores [getHooks][Excepcion]", exc.fillInStackTrace());
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
    }
     
    return hooks;
  }
  
  
  /**
   * Recupera la lista de conectores a partir de un conjunto de identificadores.
   * @param hookIds Listado de identificadores de conectores a recuperar
   * @return Lista de conectores.
   * @throws Exception Si se produce algún error.
   */
  public static Conectores getMultipleHooks(String[] hookIds, String entidad) throws CatalogoTramitesExcepcion {
    Conectores hooks = null;

	logger.debug("getMultipleHooks <-- HookIds: " + hookIds);
     
	try {
		hooks = ConectorDatos.getMultipleHooks(hookIds, entidad);
	} catch (CatalogoTramitesExcepcion exc) {
		logger.error("Error obtener conectores por id [getMultipleHooks][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
	} catch (Exception exc) {
		logger.error("Error obtener conectores por id [getMultipleHooks][Excepcion]", exc.fillInStackTrace());
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKS);
	}
     
	return hooks;
  }
}

