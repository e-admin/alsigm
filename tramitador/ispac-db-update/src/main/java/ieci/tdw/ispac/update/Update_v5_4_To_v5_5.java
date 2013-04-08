package ieci.tdw.ispac.update;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Update_v5_4_To_v5_5 {

	public static void main(String[] args) throws Exception {
		
	
		if ((args == null) || (args.length != 4)) {

			System.out
					.println("ERROR: argumentos incorrectos (driverClassName url username password).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		} catch (ClassNotFoundException cnfe) {
			System.out
					.println("ERROR: driver 'org.postgresql.Driver' no encontrado - "
							+ cnfe.getMessage());
			cnfe.printStackTrace();
			System.exit(1);
		}

		Connection cnt = null;

		try {

			cnt = DriverManager.getConnection(args[1], args[2], args[3]);
		} catch (SQLException se) {
			System.out
					.println("ERROR: no se ha establecido la conexion con la BD - "
							+ se.getMessage());
			se.printStackTrace();
			System.exit(1);
		}
		
		DbCnt dbCnt = new DbCnt();
		dbCnt.setConnection(cnt);
		
		ClientContext context = new ClientContext(dbCnt);
		context.setAPI(new InvesflowAPI(context));
		IInvesflowAPI invesFlowAPI = context.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
		IItem entity = null;
		
		try {
			context.beginTX();

			//Obtengo las entidades que son tablas de validacion
			IItemCollection entidadesValidacion = catalogAPI.getValidationTables();

			//Preparo el campo ORDEN que va a ser insertados en todas las tablas de validacion que no esten en modo lectura 
			EntityField ESTADO_FIELD = new EntityField();
			ESTADO_FIELD = new EntityField();
			ESTADO_FIELD.setPhysicalName("ORDEN");
			ESTADO_FIELD.setSize(10);
			ESTADO_FIELD.setType(InternalDataType.SHORT_INTEGER);
			ESTADO_FIELD.setDescripcion("Indica el orden del valor");

			while (entidadesValidacion.next()) {
				
				entity = (IItem) entidadesValidacion.value();
				
				EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
				if ((!entidadDef.containsFieldByName("ORDEN"))&& (entidadDef.isEditable())) {
					
		
					List newFields = new ArrayList(entidadDef.getFields());
					ESTADO_FIELD.setId(newFields.size() + 1);
					//Añadir campo orden a la entidad de validacion y se actualiza la definicion de la entidad
					catalogAPI.addField(entity.getInt("ID"), ESTADO_FIELD, false);
					//Obtener el contenido de cada una de las tablas de validacion e igual el orden al id del valor de la tbl de validacion  
					IItemCollection valoresTablaValidacion = entitiesAPI.queryEntities(entity.getInt("ID"), null);

					while (valoresTablaValidacion.next()) {

						IItem tuplaValor = (IItem) valoresTablaValidacion.value();
						tuplaValor.set("ORDEN", tuplaValor.getString("ID"));
						tuplaValor.store(context);
					}
					
					System.out.println("LOG: en la entidad con tabla BD '" + entity.getString("NOMBRE") + "' se ha creado el campo ORDEN.");
				}

			}

			System.out.println("LOG: actualizacion del ORDEN ejecutada correctamente.");
			
			//Se actualiza la defincion de las entidades que no se correspondia su descripcion con su estructura
			updateCatalogEntities(context);
			
			// Añadir los campos id_entidad e id_reg_entidad a la tabla spac_dt_documentos
			entity = entitiesAPI.getCatalogEntity("SPAC_DT_DOCUMENTOS");
			EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
			int maxId = entidadDef.getMaxId();
			
			boolean idEntidad = false;
			boolean idRegEntidad = false;
			
			if (!entidadDef.containsFieldByName("id_entidad")) {
				
				idEntidad = true;
				
				EntityField IDENTIDAD_FIELD = new EntityField();
				IDENTIDAD_FIELD = new EntityField();
				IDENTIDAD_FIELD.setId(maxId + 1);
				IDENTIDAD_FIELD.setPhysicalName("id_entidad");
				IDENTIDAD_FIELD.setLogicalName("Id. Entidad");
				//IDENTIDAD_FIELD.setSize(10);
				//IDENTIDAD_FIELD.setType(InternalDataType.SHORT_INTEGER);
				IDENTIDAD_FIELD.setType(InternalDataType.LONG_INTEGER);
				IDENTIDAD_FIELD.setDescripcion("Indica la entidad a la que esta asociada el documento");
				
				catalogAPI.addField(entity.getInt("ID"), IDENTIDAD_FIELD, true);
				
				System.out.println("LOG: en la entidad con tabla BD 'SPAC_DT_DOCUMENTOS' se ha creado el campo ID_ENTIDAD.");
			}
			
			if (!entidadDef.containsFieldByName("id_reg_entidad")) {
				
			    idRegEntidad = true;
			    
				EntityField IDREGENTIDAD_FIELD = new EntityField();
				IDREGENTIDAD_FIELD = new EntityField();
				IDREGENTIDAD_FIELD.setId(maxId + 2);
				IDREGENTIDAD_FIELD.setPhysicalName("id_reg_entidad");
				IDREGENTIDAD_FIELD.setLogicalName("Id. Reg. Entidad");
				//IDREGENTIDAD_FIELD.setSize(10);
				//IDREGENTIDAD_FIELD.setType(InternalDataType.SHORT_INTEGER);
				IDREGENTIDAD_FIELD.setType(InternalDataType.LONG_INTEGER);
				IDREGENTIDAD_FIELD.setDescripcion("Indica el registro de la entidad");
			  
				catalogAPI.addField(entity.getInt("ID"), IDREGENTIDAD_FIELD, true);
				
				System.out.println("LOG: en la entidad con tabla BD 'SPAC_DT_DOCUMENTOS' se ha creado el campo ID_REG_ENTIDAD.");
			}
			
			IItemCollection valoresTabla = entitiesAPI.queryEntities(entity.getInt("ID"), null);
	
			while (valoresTabla.next() && (idRegEntidad || idEntidad)) {
	
				IItem tuplaValor = (IItem) valoresTabla.value();
				if(idRegEntidad){
					tuplaValor.set("ID_REG_ENTIDAD", 0);
				}
				if(idEntidad){
					tuplaValor.set("ID_ENTIDAD", 0);
				}
				tuplaValor.store(context);
			}
						
			// Commit de la transacción
			context.endTX(true);

			System.out.println("LOG: actualizacion de SPAC_DT_DOCUMENTOS ejecutada correctamente.");
		}
        catch (Exception e) {
			// Rollback de la transacción
			context.endTX(false);
        	
			System.out.println("ERROR: " + e.getMessage());
			
			if (entity != null) {
				System.out.println("en la entidad con tabla BD '" + entity.getString("NOMBRE") + "'.");
			}
			
			e.printStackTrace();
		}
		finally {
			
			if (cnt != null) {
				cnt.close();
			}
		}
	}
	private static void updateCatalogEntities(ClientContext context) throws ISPACException {
		IInvesflowAPI invesFlowAPI = context.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		// Actualizar las entidades de SPAC_CT_ENTIDADES: XML de definición 

		//=============
		//SPAC_EXPEDIENTES
		//=============
		IItem ctEntity = entitiesAPI.getCatalogEntity("SPAC_EXPEDIENTES");
    	String xmlEntityDefinition = ctEntity.getString("DEFINICION");
    	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
    	
    		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

			EntityField entityField = entityDefinition.getField("version");
			if (entityField != null){
				entityField.setPhysicalName("nversion");
			}

			//Se añade el campo iddireccionpostal
			entityField = entityDefinition.getField("iddireccionpostal");
			if (entityField == null){
				entityField = new EntityField();
				entityField.setPhysicalName("iddireccionpostal");
				entityField.setSize(32);
				entityField.setType(InternalDataType.SHORT_TEXT);
				entityDefinition.addField(entityField);
			}
			
			//Se cambia el tipo de idtitular
			entityField = entityDefinition.getField("idtitular");
			if (entityField != null){
				entityField.setSize(32);
				entityField.setType(InternalDataType.SHORT_TEXT);
			}
			
			// Actualizar la definición de la entidad
			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
			ctEntity.set("FECHA", new Date());
			ctEntity.store(context);
			System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se ha actualizado la definicion.");
		}
    	
		//=============
		//SPAC_DT_INTERVINIENTES
		//=============
		ctEntity = entitiesAPI.getCatalogEntity("SPAC_DT_INTERVINIENTES");
    	xmlEntityDefinition = ctEntity.getString("DEFINICION");
    	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
    	
    		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

			//Se añade el campo iddireccionpostal
    		EntityField entityField = entityDefinition.getField("iddireccionpostal"); 
			if (entityField == null){
	    		entityField = new EntityField();
				entityField.setPhysicalName("iddireccionpostal");
				entityField.setSize(32);
				entityField.setType(InternalDataType.SHORT_TEXT);
				entityDefinition.addField(entityField);
			}
    		
			//Se cambia el tipo de idtitular
			entityField = entityDefinition.getField("id_ext");
			if (entityField != null){
				entityField.setSize(32);
				entityField.setType(InternalDataType.SHORT_TEXT);
			}
			
			// Actualizar la definición de la entidad
			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
			ctEntity.set("FECHA", new Date());
			ctEntity.store(context);
			System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se ha actualizado la definicion.");
		}
    	
		//=============
		//SPAC_DT_DOCUMENTOS
		//=============
		ctEntity = entitiesAPI.getCatalogEntity("SPAC_DT_DOCUMENTOS");
    	xmlEntityDefinition = ctEntity.getString("DEFINICION");
    	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
    	
    		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);

			//Se elimina campo num_acto
    		EntityField entityField = entityDefinition.getField("num_acto");
			if (entityField != null){
				entityDefinition.removeField(entityField);				
			}
			
			//Se elimina campo ndoc
			entityField = entityDefinition.getField("ndoc");
			if (entityField != null){
				entityDefinition.removeField(entityField);				
			}			

			//Se cambia el tamaño del campo extension
			entityField = entityDefinition.getField("extension");
			if (entityField != null){
				entityField.setSize(4);				
			}
			
			//Se cambia el el tipo de dato del campo origen_id
			entityField = entityDefinition.getField("origen_id");
			if (entityField != null){
				entityField.setType(InternalDataType.SHORT_TEXT);
				entityField.setSize(20);				
			}
			
			//Se cambia el el tipo de dato del campo destino_id
			entityField = entityDefinition.getField("destino_id");
			if (entityField != null){
				entityField.setType(InternalDataType.SHORT_TEXT);
				entityField.setSize(20);				
			}
			
			//Se añade campo id_notificacion
			entityField = entityDefinition.getField("id_notificacion");
			if (entityField == null){
				entityField = new EntityField(entityDefinition.getMaxId()+1, null, "id_notificacion", "", InternalDataType.SHORT_TEXT, 64,  0 );
				entityDefinition.addField(entityField);
			}
			
			// Validaciones
			Map validations = entityDefinition.validationsToMapByIdField();
			
			entityField = entityDefinition.getField("estadonotificacion");
			if ((entityField != null) && (!validations.containsKey(new Integer(entityField.getId())))) {
				
				EntityValidation entityValidacion = new EntityValidation();
				entityValidacion.setId(validations.size() + 1);
				entityValidacion.setFieldId(entityField.getId());
				entityValidacion.setTable("SPAC_TBL_006");
				entityValidacion.setTableType(String.valueOf(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId()));
				entityValidacion.setMandatory(EntityValidation.FALSE);
				
				entityDefinition.addValidation(entityValidacion);
			}
			
			entityField = entityDefinition.getField("estadofirma");
			if ((entityField != null) && (!validations.containsKey(new Integer(entityField.getId())))) {
				
				EntityValidation entityValidacion = new EntityValidation();
				entityValidacion.setId(validations.size() + 2);
				entityValidacion.setFieldId(entityField.getId());
				entityValidacion.setTable("SPAC_TBL_008");
				entityValidacion.setTableType(String.valueOf(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId()));
				entityValidacion.setMandatory(EntityValidation.FALSE);
				
				entityDefinition.addValidation(entityValidacion);
			}
			
			// Actualizar la definición de la entidad
			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
			ctEntity.set("FECHA", new Date());
			ctEntity.store(context);
			System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se ha actualizado la definicion.");
		}
    	
		//=============
		//SPAC_DT_TRAMITES
		//=============
		ctEntity = entitiesAPI.getCatalogEntity("SPAC_DT_TRAMITES");
    	xmlEntityDefinition = ctEntity.getString("DEFINICION");
    	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
    	
    		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);
    	
			//Se añade campo id_subproceso
    		EntityField entityField = entityDefinition.getField("id_subproceso"); 
			if (entityField == null){
	    		entityField = new EntityField(entityDefinition.getMaxId()+1, null, "id_subproceso", "", InternalDataType.LONG_INTEGER, 0,  0 );
				entityDefinition.addField(entityField);
			}
    		
    		// Actualizar la definición de la entidad
			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
			ctEntity.set("FECHA", new Date());
    	
			ctEntity.store(context);
			System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se ha actualizado la definicion.");
		}
    }	
}