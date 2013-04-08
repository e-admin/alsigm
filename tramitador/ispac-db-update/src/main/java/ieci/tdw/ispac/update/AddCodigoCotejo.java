package ieci.tdw.ispac.update;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import org.apache.log4j.Logger;

/**
 * Clase para añadir el campo "Código de cotejo" a la entidad de documentos.
 *
 */
public class AddCodigoCotejo extends ScriptBase {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AddCodigoCotejo.class); 
	
	
	public static void main(String[] args) throws Exception {
		
		checkArguments(args);

		ClientContext context = getClientContext(args);
		
		try {
			
			context.beginTX();
			
			// Añadir el campo cod_cotejo
			addCodigoCotejo(context);

			// Commit de la transacción
			context.endTX(true);

			logger.info("Proceso de actualización finalizado con éxito.");
		
		} catch (Throwable t) {
        	
			logger.error("Error en el proceso de actualización", t);

			// Rollback de la transacción
			context.endTX(false);
			
		} finally {
			context.releaseTX();
		}
	}

	/**
	 * Añade la columna cod_cotejo a la entidad de documentos.
	 * @param context Contexto de cliente.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected static void addCodigoCotejo(ClientContext context) throws ISPACException {
		
		try {
			
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Información de la tabla Documentos del Expediente
			IItem entity = entitiesAPI.getCatalogEntity("SPAC_DT_DOCUMENTOS");
			EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
	
			if (!entidadDef.containsFieldByName("cod_cotejo")) {
				
				// Añadir el campo a la entidad
				EntityField codCotejoField = new EntityField();
				codCotejoField.setId(entidadDef.getMaxId() + 1);
				codCotejoField.setPhysicalName("cod_cotejo");
				codCotejoField.setLogicalName("Código de cotejo");
				codCotejoField.setDescripcion("Código de cotejo");
				codCotejoField.setType(InternalDataType.SHORT_TEXT);
				codCotejoField.setSize(50);
	
				catalogAPI.addField(entity.getInt("ID"), codCotejoField, true);
				logger.info("Campo COD_COTEJO añadido a la entidad de documentos (SPAC_DT_DOCUMENTOS)");
			} else {
				logger.info("El campo COD_COTEJO ya existe en la entidad de documentos (SPAC_DT_DOCUMENTOS)");
			}
			
		} catch (ISPACException e) {
			logger.error("Error al añadir el campo cod_cotejo a la tabla spac_dt_documentos", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al añadir el campo cod_cotejo a la tabla spac_dt_documentos", e);
			throw new ISPACException(e);
		}
	}
}