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
 * Clase para la actualización de la base de datos de v6.3 a v6.4.
 *
 */
public class Update_v6_3_To_v6_4 extends ScriptBase {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Update_v6_3_To_v6_4.class); 
	
	
	public static void main(String[] args) throws Exception {
		
		checkArguments(args);

		ClientContext context = getClientContext(args);
		
		try {
			
			context.beginTX();
			
			// Modificaciones en la entidad de expedientes
			updateEntidadExpedientes(context);

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

	protected static void updateEntidadExpedientes(ClientContext context) throws ISPACException {
		
		try {
			
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Información de la tabla Documentos del Expediente
			IItem entity = entitiesAPI.getCatalogEntity("SPAC_EXPEDIENTES");
			EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
	
			// Modificación del tipo del campo IDUNIDADTRAMITADORA de entero a varchar(250)
			EntityField field=entidadDef.getField("idunidadtramitadora");
			if (field != null) {
			
				field.setType(InternalDataType.SHORT_TEXT);
				field.setSize(250);
				
				catalogAPI.saveField(entity.getInt("ID"),  field);
				logger.info("Modificado el tipo del campo IDUNIDADTRAMITADORA de la entidad SPAC_EXPEDIENTES en la definicion de la entidad");
				
			} else {
				logger.info("No se ha encontrado el campo IDUNIDADTRAMITADORA en la entidad SPAC_EXPEDIENTES");
			}
	
		} catch (ISPACException e) {
			logger.error("Error al modificar el campo IDUNIDADTRAMITADORA a la tabla SPAC_EXPEDIENTES", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al modificar el campo IDUNIDADTRAMITADORA a la tabla SPAC_EXPEDIENTES", e);
			throw new ISPACException(e);
		}
	}
}