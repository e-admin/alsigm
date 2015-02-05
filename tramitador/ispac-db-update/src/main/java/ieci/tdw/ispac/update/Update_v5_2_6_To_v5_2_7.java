package ieci.tdw.ispac.update;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.EntityAppConstants;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ImportProcedureMgrVersions;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Update_v5_2_6_To_v5_2_7 {
	
	public static void main(String[] args) throws Exception {
		
		if ((args == null) ||
			(args.length != 4)) {
			
			System.out.println("ERROR: argumentos incorrectos (driverClassName url username password).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("ERROR: driver 'org.postgresql.Driver' no encontrado - " + cnfe.getMessage());
			cnfe.printStackTrace();
			System.exit(1);
		}
		
		Connection cnt = null;
		  
		try {
			cnt = DriverManager.getConnection(args[1], args[2], args[3]);
		}
		catch (SQLException se) {
			System.out.println("ERROR: no se ha establecido la conexion con la BD - " + se.getMessage());
			se.printStackTrace();
			System.exit(1);
		}
		
		DbCnt dbCnt = new DbCnt();
		dbCnt.setConnection(cnt);
		
		ClientContext context = new ClientContext(dbCnt);
		context.setAPI(new InvesflowAPI(context));
		
		try {
			context.beginTX();
			
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			
			// Actualizar las entidades de SPAC_CT_ENTIDADES: XML de definición y formulario JSP
			IItemCollection catalogEntities = entitiesAPI.getCatalogEntities();
			Map catalogEntitiesMapById = catalogEntities.toMapStringKey();
			catalogEntities.reset();
			updateCatalogEntities(context, catalogEntities, catalogEntitiesMapById);

			
			// Actualizar los formularios de SPAC_CT_APLICACIONES: XML de parámetros y formulario JSP
			IItemCollection catalogForms = entitiesAPI.queryEntities(SpacEntities.SPAC_CT_APLICACIONES, "");
			updateForms(context, catalogForms, catalogEntitiesMapById);
			
			// Commit de la transacción
			context.endTX(true);
			
			System.out.println("LOG: actualizacion ejecutada correctamente.");
		}
		catch (Exception e) {
			// Rollback de la transacción
			context.endTX(false);
			
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			
			if (cnt != null) {
				cnt.close();
			}
		}
	}
	
	private static void updateCatalogEntities(ClientContext context,
											  IItemCollection catalogEntities,
											  Map catalogEntitiesMapById) throws ISPACException {
		
		while (catalogEntities.next()) {
			
			IItem ctEntity = catalogEntities.value();
			
    		boolean updateEntityDefinition = false;
    		boolean updateEntityFrmJsp = false;
			
			// En las validaciones de la definición de la entidad
			// sustituir el Id de tabla por el nombre de la tabla
        	String xmlEntityDefinition = ctEntity.getString("DEFINICION");
        	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
        		
        		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);
        		
        		// Validaciones
        		List validations = entityDefinition.getValidations();
        		Iterator it = validations.iterator();
        		while (it.hasNext()) {
        			
        			EntityValidation entityValidation = (EntityValidation) it.next();
        			
        			// Validación con tabla?
        			String table = entityValidation.getTable();
        			if (StringUtils.isNotBlank(table)) {
        			
	        			// Id en la tabla?
	        			Integer tableId = null;
	        			try {
	        				tableId = new Integer(table);
	        			}
	        			catch (NumberFormatException nfe) {}
	        			
	        			if (tableId != null) {
	        				
	        				// Obtener el nombre de la tabla a partir del Id
	        				IItem ctEntityInValidation = (IItem) catalogEntitiesMapById.get(tableId.toString());
	        				if (ctEntityInValidation != null) {
	        					table = ctEntityInValidation.getString("NOMBRE");
	        				}
	        				else {
	        					table = "";
	        				}
	        				
	        				entityValidation.setTable(table);
	        				updateEntityDefinition = true;
	        			}
        			}
        		}
        		
        		if (updateEntityDefinition) {
        			
        			// Actualizar la definición de la entidad
        			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
        		}	
        	}
        	
        	// En el formulario JSP de la entidad
        	// sustituir en el action de las lupas entity=Id por entity=Nombre
        	String frmJsp = ctEntity.getString("FRM_JSP");
        	if (StringUtils.isNotBlank(frmJsp)) {
        		
        		String[] frmJspSplit = frmJsp.split(ImportProcedureMgrVersions.ENTITY_IN_FORM_JSP);
        		if (frmJspSplit.length > 1) {
        			
        			// Procesar el formulario JSP de la entidad
        			StringBuffer newFrmJsp = new StringBuffer(frmJspSplit[0]);
        			updateEntityFrmJsp = updateFrmJsp(newFrmJsp, frmJspSplit, catalogEntitiesMapById);
        			
        			if (updateEntityFrmJsp) {
        				
        				// Actualizar el formulario JSP de la entidad
        				ctEntity.set("FRM_JSP", newFrmJsp.toString());
        			}
        		}
        	}
        	
        	// Actualizar la entidad?
        	if (updateEntityDefinition || updateEntityFrmJsp) {
        		
        		ctEntity.store(context);
        		System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se han actualizado las validaciones de la definicion y el formulario JSP.");
        	}
		}
	}
	
	private static void updateForms(ClientContext context,
			  						IItemCollection catalogForms,
			  						Map catalogEntitiesMapById) throws ISPACException {
		
		while (catalogForms.next()) {
			
			IItem ctForm = catalogForms.value();
			
    		boolean updateFormParameters = false;
    		boolean updateFormFrmJsp = false;
    		
			// En los parámetros del formulario
			// sustituir
    		// el tag id por table
    		// el atributo primaryId por primaryTable
    		// y el Id de tabla por el nombre de la tabla
        	String xmlFormParameters = ctForm.getString("PARAMETROS");
        	if (StringUtils.isNotBlank(xmlFormParameters)) {
        		
        		// Reemplazar <id> por <table>
        		xmlFormParameters = StringUtils.replace(xmlFormParameters, ImportProcedureMgrVersions.XML_TAG_ID_OPEN, ImportProcedureMgrVersions.XML_TAG_TABLE_OPEN);
        		// Reemplazar </id> por </table>
        		xmlFormParameters = StringUtils.replace(xmlFormParameters, ImportProcedureMgrVersions.XML_TAG_ID_CLOSE, ImportProcedureMgrVersions.XML_TAG_TABLE_CLOSE);
        		
        		// Reemplazar primaryId por primaryTable (en COMPOSITE)
        		xmlFormParameters = StringUtils.replace(xmlFormParameters, ImportProcedureMgrVersions.XML_ATTRIBUTE_PRIMARY_ID, EntityAppConstants.ATTRIBUTE_PRIMARY_TABLE);

        		ParametersDef parametersDefinition = ParametersDef.parseParametersDef(xmlFormParameters);
        		
        		// Entidades del formulario
        		List entities = parametersDefinition.getEntities();
        		Iterator it = entities.iterator();
        		while (it.hasNext()) {
        			
        			EntityParameterDef entityParameterDefinition = (EntityParameterDef) it.next();
        			
        			// Validación con tabla primaria?
        			String table = entityParameterDefinition.getPrimaryTable();
        			if (StringUtils.isNotBlank(table)) {
        			
	        			// Id en la tabla?
	        			Integer tableId = null;
	        			try {
	        				tableId = new Integer(table);
	        			}
	        			catch (NumberFormatException nfe) {}
	        			
	        			if (tableId != null) {
	        				
	        				// Obtener el nombre de la tabla a partir del Id
	        				IItem ctEntityInValidation = (IItem) catalogEntitiesMapById.get(tableId.toString());
	        				if (ctEntityInValidation != null) {
	        					table = ctEntityInValidation.getString("NOMBRE");
	        				}
	        				else {
	        					table = "";
	        				}
	        				
	        				entityParameterDefinition.setPrimaryTable(table);
	        				updateFormParameters = true;
	        			}
        			}
        			
        			// Parámetro con tabla?
        			table = entityParameterDefinition.getTable();
        			if (StringUtils.isNotBlank(table)) {
        			
	        			// Id en la tabla?
	        			Integer tableId = null;
	        			try {
	        				tableId = new Integer(table);
	        			}
	        			catch (NumberFormatException nfe) {}
	        			
	        			if (tableId != null) {
	        				
	        				// Obtener el nombre de la tabla a partir del Id
	        				IItem ctEntityInValidation = (IItem) catalogEntitiesMapById.get(tableId.toString());
	        				if (ctEntityInValidation != null) {
	        					table = ctEntityInValidation.getString("NOMBRE");
	        				}
	        				else {
	        					table = "";
	        				}
	        				
	        				entityParameterDefinition.setTable(table);
	        				updateFormParameters = true;
	        			}
        			}
        		}
        		
        		if (updateFormParameters) {
        			
        			// Actualizar los parámetros del formulario
        			ctForm.set("PARAMETROS", parametersDefinition.toXml());
        		}	
        	}
        	
        	// En el formulario JSP
        	// sustituir en el action de las lupas entity=Id por entity=Nombre
        	String frmJsp = ctForm.getString("FRM_JSP");
        	if (StringUtils.isNotBlank(frmJsp)) {
        		
        		String[] frmJspSplit = frmJsp.split(ImportProcedureMgrVersions.ENTITY_IN_FORM_JSP);
        		if (frmJspSplit.length > 1) {
        			
        			// Procesar el formulario JSP
        			StringBuffer newFrmJsp = new StringBuffer(frmJspSplit[0]);
        			updateFormFrmJsp = updateFrmJsp(newFrmJsp, frmJspSplit, catalogEntitiesMapById);
        			
        			if (updateFormFrmJsp) {
        				
        				// Actualizar el formulario JSP
        				ctForm.set("FRM_JSP", newFrmJsp.toString());
        			}
        		}
        	}
        	
        	// Actualizar el formulario?
        	if (updateFormParameters || updateFormFrmJsp) {
        		
        		ctForm.store(context);
        		System.out.println("LOG: para el formulario '" + ctForm.getString("NOMBRE") + "' se han actualizado los parametros y el formulario JSP.");
        	}
		}
	}
	
	private static boolean updateFrmJsp(StringBuffer newFrmJsp,
									    String[] frmJspSplit,
									    Map catalogEntitiesMapById) throws ISPACException {
		
		boolean updateEntityFrmJsp = false;
		
		int i = 1;
		while (i < frmJspSplit.length) {
		
			String nextPart = frmJspSplit[i++];
			int quoteIndex = nextPart.indexOf("\"");
			
			String table = nextPart.substring(0, quoteIndex);
			
			// Id en la tabla?
			Integer tableId = null;
			try {
				tableId = new Integer(table);
			}
			catch (NumberFormatException nfe) {}
			
			if (tableId != null) {
				
				// Obtener el nombre de la tabla a partir del Id
				IItem ctEntityInValidation = (IItem) catalogEntitiesMapById.get(tableId.toString());
				if (ctEntityInValidation != null) {
					table = ctEntityInValidation.getString("NOMBRE");
				}
				else {
					table = "";
				}
				
				updateEntityFrmJsp = true;
			}
			
			newFrmJsp.append(ImportProcedureMgrVersions.ENTITY_IN_FORM_JSP)
					 .append(table)
					 .append(nextPart.substring(quoteIndex, nextPart.length()));
		}
		
		return updateEntityFrmJsp;
	}
	
}