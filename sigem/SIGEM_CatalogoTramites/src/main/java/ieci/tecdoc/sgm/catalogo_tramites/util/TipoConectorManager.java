package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.TipoConectorDatos;

import org.apache.log4j.Logger;

/**
 * Gestor que implementa la funcionalidad para los tipos de conectores (recuperación,
 * alamacenaje, eliminación y actualización de tipos de conectores)
 *
 */
public class TipoConectorManager {
  
	private static final Logger logger = Logger.getLogger(TipoConectorManager.class);
	
  private static final boolean isDebugeable = true;
 
  protected TipoConectorManager() {
  }

  /**
   * Recupera la información de un tipo de conector
   * @param hookId Identificador del tipo de conector.
   * @return Hook El tipo de conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static TipoConector get(int typeId, String entidad) throws CatalogoTramitesExcepcion {
     TipoConectorDatos hookType = new TipoConectorDatos();

     try {
    	 hookType.load(typeId, entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al obtener tipo de conector [get][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPE);
     } catch (Exception exc) {
    	 logger.error("Error al obtener tipo de conector [get][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPE);
     }
     
     return hookType;
  }


  /**
   * Añade un nuevo tipo de conector al catálogo. 
   * @param adressee Información del tipo de conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void add(TipoConector hookType, String entidad) throws CatalogoTramitesExcepcion {
     TipoConectorDatos data = new TipoConectorDatos(hookType);

     try {
    	 data.insert(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al añadir tipo de conector [add][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOKTYPE);
     } catch (Exception exc) {
    	 logger.error("Error al añadir tipo de conector [add][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_HOOKTYPE);
     }
  }

  
  /**
   * Elimina un tipo de conector.
   * @param hookId Identificador del tipo de conector.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void delete(int typeId, String entidad) throws CatalogoTramitesExcepcion {
     TipoConectorDatos hookType = new TipoConectorDatos();

     try{
    	 //TODO Comprobar referencias de documentos antes de eliminar
        /*if (ProcedureManager.isDocumentReferenced(addresseeId)) {
          throw new ProcedureCatalogException(ProcedureCatalogErrorCodes.EC_ADDRESSEE_USED);
        }*/
    	 hookType.setId(typeId);
    	 hookType.delete(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al eliminar tipo de conector [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOKTYPE);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar tipo de conector [delete][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_HOOKTYPE);
     }
  }

  
  /**
   * Actualiza el tipo de conector que se pasa como parámetro. 
   * @param hookType Tipo de conector con los nuevos datos.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void update(TipoConector hookType, String entidad) throws CatalogoTramitesExcepcion {
     TipoConectorDatos data = new TipoConectorDatos(hookType);

     try {
    	 data.update(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al actualizar tipo de conector [update][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOKTYPE);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar tipo de conector [update][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_HOOKTYPE);
     }
  }

  
  /**
   * Recupera la lista de tipos de conectores. 
   * @return La lista mencionada.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static TiposConectores getHookTypes(String entidad) throws CatalogoTramitesExcepcion {
    TiposConectores hookTypes = null;

     try {
    	 hookTypes = TipoConectorDatos.getHookTypes(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al obtener tipos de conectores [getHookTypes][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPES);
     } catch (Exception exc) {
    	 logger.error("Error al obtener tipos de conectores [getHookTypes][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_HOOKTYPES);
     }
     return hookTypes;
  }
}

