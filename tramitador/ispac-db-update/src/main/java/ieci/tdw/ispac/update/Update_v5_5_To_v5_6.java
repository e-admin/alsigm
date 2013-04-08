package ieci.tdw.ispac.update;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbColDef;
import ieci.tdw.ispac.ispaclib.db.DbTableFns;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import org.apache.log4j.Logger;

/**
 * Clase para la actualización de la base de datos de v5.5 a v5.6.
 *
 */
public class Update_v5_5_To_v5_6 extends ScriptBase {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Update_v5_5_To_v5_6.class); 
	
	
	public static void main(String[] args) throws Exception {
		
		checkArguments(args);

		ClientContext context = getClientContext(args);
		
		try {
			
			context.beginTX();
			
			// Añadir el campo extension_rde
			updateTablaDocumentos(context);

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

	protected static void updateTablaDocumentos(ClientContext context) throws ISPACException {
		
		try {
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Información de la tabla Documentos del Expediente
			IItem entity = entitiesAPI.getCatalogEntity("SPAC_DT_DOCUMENTOS");
			EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
	
			if (!entidadDef.containsFieldByName("extension_rde")) {
				
				// Añadir el campo a la entidad
				EntityField extRdeField = new EntityField();
				extRdeField.setId(entidadDef.getMaxId() + 1);
				extRdeField.setPhysicalName("extension_rde");
				extRdeField.setLogicalName("Extensión documento firmado");
				extRdeField.setType(InternalDataType.SHORT_TEXT);
				extRdeField.setSize(64);
				extRdeField.setDescripcion("Extensión del documento firmado");
	
			
				catalogAPI.addField(entity.getInt("ID"), extRdeField, true);
				logger.info("Campo EXTENSION_RDE añadido a la tabla SPAC_DT_DOCUMENTOS");
				
				// Actualizar el valor de la columna EXTENSION_RDE con el valor de EXTENSION
				context.getConnection().directExec("UPDATE SPAC_DT_DOCUMENTOS SET EXTENSION_RDE=EXTENSION WHERE INFOPAG_RDE IS NOT NULL");
				logger.info("Columna EXTENSION_RDE actualizada con el valore de EXTENSION");
			}
			
			//Hay que modificar el tamaño del campo extension de la tabla spac_dt_documentos antes era 4 ahora debera ser 64
			EntityField field_extension=entidadDef.getField("extension");
			field_extension.setSize(64);
			catalogAPI.saveField(entity.getInt("ID"),  field_extension);
			logger.info("Modificado tamaño campo extension de la tabla SPAC_DT_DOCUMENTOS en la definicion de la entidad");
			
			//Actualizamos el tipo de la columna
			DbColDef colDef= new DbColDef(field_extension.getPhysicalName(), field_extension.getType().getId(), field_extension.getSize(), field_extension.isNullable());
			DbTableFns.updateField(context.getConnection(), "SPAC_DT_DOCUMENTOS", colDef);
			logger.info("Modificado tamaño campo extension de la tabla SPAC_DT_DOCUMENTOS en la bbdd");
	
		} catch (ISPACException e) {
			logger.error("Error al añadir el campo extension_rde a la tabla spac_dt_documentos", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al añadir el campo extension_rde a la tabla spac_dt_documentos", e);
			throw new ISPACException(e);
		}
	}
}