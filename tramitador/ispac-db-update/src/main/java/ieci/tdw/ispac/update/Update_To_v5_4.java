package ieci.tdw.ispac.update;

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
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Update_To_v5_4 {
	
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
			
			// Actualizar las entidades de SPAC_CT_ENTIDADES: XML de definición 
			IItemCollection catalogEntities = entitiesAPI.getCatalogEntities();
			updateCatalogEntities(context, catalogEntities);
			
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
											  IItemCollection catalogEntities) throws ISPACException {
		
		while (catalogEntities.next()) {
			
			IItem ctEntity = catalogEntities.value();
			
    		boolean updateEntityDefinition = false;
    					
			// En la definición de la entidad para los campos de tipo texto largo 
			// eliminar el tamaño asignado
        	String xmlEntityDefinition = ctEntity.getString("DEFINICION");
        	if (StringUtils.isNotBlank(xmlEntityDefinition)) {
        		
        		EntityDef entityDefinition = EntityDef.parseEntityDef(xmlEntityDefinition);
        		
        		List longTextFields = entityDefinition.getFields(InternalDataType.LONG_TEXT);
        		if (!longTextFields.isEmpty()) {
        			
        			Iterator it = longTextFields.iterator();
        			while (it.hasNext()) {
        				
        				EntityField longTextField = (EntityField) it.next();
        				
        				if (longTextField.getSize() > 0) {
        					
	        				longTextField.setSize(0);
	        				updateEntityDefinition = true;
        				}
        			}
        		}
        	
        		// Actualizar la entidad?
        		if (updateEntityDefinition) {
        			
        			// Actualizar la definición de la entidad
        			ctEntity.set("DEFINICION", entityDefinition.getXmlValues());
        			
            		ctEntity.store(context);
            		System.out.println("LOG: para la entidad con tabla BD '" + ctEntity.getString("NOMBRE") + "' se ha actualizado la definicion.");
        		}	
        	}
		}
	}
	
}