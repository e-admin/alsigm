package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.OrganoDestinatarioDatos;

import org.apache.log4j.Logger;

/**
 * Gestor que implementa la funcionalidad para los órganos destinatarios 
 * (recuperación, almacenaje, eliminaciín y actualización de órganos destinatarios)
 *
 */
public class OrganoDestinatarioManager {
  
	private static final Logger logger = Logger.getLogger(OrganoDestinatarioManager.class);
	
  private static final boolean isDebugeable = true;
 
  protected OrganoDestinatarioManager() {
  }

  /**
   * Recupera la información de un órgano destinatario.
   * @param addresseeId Identificador del órgano destinatario.
   * @return Addressee El órgano destinatario.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static OrganoDestinatario get(String addresseeId, String entidad) throws CatalogoTramitesExcepcion {
     OrganoDestinatarioDatos addressee = new OrganoDestinatarioDatos();

     try {
    	 addressee.load(addresseeId, entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al obtener organo destinatario [get][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_ADDRESSEE);
     } catch (Exception exc) {
    	 logger.error("Error al obtener organo destinatario [get][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_ADDRESSEE);
     }
     
     return addressee;
  }


  /**
   * Añade un nuevo órgano destinatario al catálogo. 
   * @param adressee Información del órgano destinatario.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void add(OrganoDestinatario addressee, String entidad) throws CatalogoTramitesExcepcion {
     OrganoDestinatarioDatos data = new OrganoDestinatarioDatos(addressee);

     try {
    	 data.insert(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al añadir organo destinatario [add][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_ADDRESSEE);
     } catch (Exception exc) {
    	 logger.error("Error al añadir organo destinatario [add][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_ADDRESSEE);
     }
  }

  
  /**
   * Elimina un órgano destinatario.
   * @param addresseeId Identificador del órgano destinatario.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void delete(String addresseeId, String entidad) throws CatalogoTramitesExcepcion {
     OrganoDestinatarioDatos addressee = new OrganoDestinatarioDatos();

     try{
    	 //TODO Comprobar si el documento esta refenciado
        /*if (ProcedureManager.isDocumentReferenced(addresseeId)) {
          throw new ProcedureCatalogException(ProcedureCatalogErrorCodes.EC_ADDRESSEE_USED);
        }*/
        addressee.setId(addresseeId);
        addressee.delete(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al eliminar organo destinatario [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_ADDRESSEE);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar organo destinatario [delete][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_ADDRESSEE);
     }
  }

  
  /**
   * Actualiza el órgano destinatario que se pasa como parámetro. 
   * @param addressee Organo destinatario con los nuevos datos.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static void update(OrganoDestinatario addressee, String entidad) throws CatalogoTramitesExcepcion {
     OrganoDestinatarioDatos data = new OrganoDestinatarioDatos(addressee);

     try {
    	 data.update(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al actualizar organo destinatario [delete][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_ADDRESSEE);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar organo destinatario [delete][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_ADDRESSEE);
     }
  }

  
  /**
   * Recupera la lista de órganos destinatarios. 
   * @return La lista mencionada.
   * @throws CatalogoTramitesExcepcion Si se produce algún error.
   */
  public static OrganosDestinatarios getAddressees(String entidad) throws CatalogoTramitesExcepcion {
    OrganosDestinatarios addressees = null;

     try {
    	 addressees = OrganoDestinatarioDatos.getAddressees(entidad);
     } catch (CatalogoTramitesExcepcion exc) {
    	 logger.error("Error al obtener organos destinatarios [getAddressees][CatalogoTramitesExcepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_ADDRESSEES);
     } catch (Exception exc) {
    	 logger.error("Error al obtener organos destinatarios [getAddressees][Excepcion]", exc.fillInStackTrace());
    	 throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_ADDRESSEES);
     }
     
     return addressees;
  }
}
