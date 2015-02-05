package ieci.tdw.ispac.update;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class UpdateDocumentarySearchForPostgres8_2_To_Postgres8_3  extends ScriptBase {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(UpdateDocumentarySearchForPostgres8_2_To_Postgres8_3.class); 
	
	
	public static void main(String[] args) throws Exception {
		
		checkArguments(args);
		ClientContext context = getClientContext(args);

		try {

		/*	try {

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
			
			ClientContext context = new ClientContext(dbCnt);*/
			
			// Se actualizará la búsqueda documental
			updateDocumentarySearch(context);

			

			logger.info("Proceso de actualización finalizado con éxito.");
		
		} catch (Throwable t) {
        	
			logger.error("Error en el proceso de actualización", t);
	
		} 
		/*finally {
			
			if (cnt != null) {
				cnt.close();
			}
		}*/
	}

	protected static void updateDocumentarySearch(ClientContext context) throws ISPACException {
		
		try {
			context.setAPI(new InvesflowAPI(context));
			IInvesflowAPI invesFlowAPI = context.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			StringBuffer condicion = new StringBuffer();
			condicion.append("WHERE TIPO = ")
					 .append(EntityType.PROCESS_ENTITY_TYPE.getId());
			condicion.append(" ORDER BY ID ");
			
			// Obtener las entidades
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, condicion.toString());
			
			//Recorro cada una de las entidades y obtengo la definición
			Iterator itr = itemcol.iterator();
			
			while(itr.hasNext()){
				
				IItem entity= (IItem) itr.next();
				EntityDef entidadDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
				//Recorro cada uno de los campos y si tiene activa la busqueda documental se añade
				List fields = new ArrayList(entidadDef.getFields());
				for(int i=0; i<fields.size(); i++){
					EntityField field= (EntityField) fields.get(i);
					if(field.getDocumentarySearch()){
						catalogAPI.activateDocumentarySearch(context.getConnection(), field.getPhysicalName() ,entity.getString("NOMBRE"));
						logger.info("Busqueda documental activada para el campo "+field.getPhysicalName() +"de la entidad "+ entity.getString("NOMBRE"));
					}
				}
			
			}
			
			
		} catch (ISPACException e) {
			context.endTX(false);
			logger.error("Error al añadir al actualizar la busqueda documental", e);
			throw e;
		} catch (Throwable e) {
			context.endTX(false);
			logger.error("Error al añadir al actualizar la busqueda documental", e);
			
			throw new ISPACException(e);
		}
		finally{
			if(context.ongoingTX()){
				context.endTX(false);
			}
			context.releaseTX();
			
		}
	}
}