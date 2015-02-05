package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportOrgDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskTpDocDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.FrmBusquedaReportDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.PcdTemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDataDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteCTEntityAction extends BaseAction {
	
	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(DeleteCTEntityAction.class);
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());

		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(), entityId);

		// Comprobar si se puede eliminar
		if (procedureAPI.isInUse( entityId, keyId)) {
			return mapping.findForward("use");
		}
	
		// Contexto del cliente
		IClientContext context = session.getClientContext();
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
			// Iniciar transacción
			context.beginTX();
			
			// Eliminar las entidades asociadas
			switch (entityId) {

				case ICatalogAPI.ENTITY_CT_STAGE: {
					
					// Bloquear las fases para actualizar los órdenes al eliminar
					catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_STAGE, "");

					// Eliminar asociaciones con trámites
					deleteStageTasks(context, keyId);
					
					break;
				}
				
				case ICatalogAPI.ENTITY_CT_TASK: {
					
					// Eliminar asociaciones con tipos de documentos
					deleteTaskDocTypes(context, keyId);
					
					break;
				}
				
				case ICatalogAPI.ENTITY_CT_TYPEDOC: {
					
					// Eliminar las plantilla del tipo de documento
					deleteDocTypeTemplates(context, keyId);
					
					break;
				}
				case ICatalogAPI.ENTITY_CT_SEARCHFORM:{
					
					// Eliminar los usuarios asociados al formulario
					deleteUserAsociated(context,keyId);
					
					// Eliminar las relaciones con informes
					deleteReportReferences(context, keyId);
					
					break;
				}
				case ICatalogAPI.ENTITY_CT_INFORMES:{
					deleteUserReportAsociated(context, keyId);	
					deleteSearchFormsAssociatedToReport(context, keyId);
					break;
				}

			}
	
			// Eliminar la entidad
			IItem item = catalogAPI.getCTEntity( entityId, keyId);
			item.delete(context);
			
			// Al eliminar una fase hay que reordenar el resto de fases cuyo orden es mayor que la eliminada
			if (entityId == ICatalogAPI.ENTITY_CT_STAGE) {
				
				IItemCollection collection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_STAGE, " WHERE ORDEN > " + item.getInt("ORDEN"));
				while(collection.next()) {
					
					item = collection.value();
					
					// Actualizar el orden
					int order = item.getInt("ORDEN");
					item.set("ORDEN", order - 1);
					
					item.store(context);
				}
			}
			
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (Exception e) {
						
			logger.error("Error al eliminar la entidad del catálogo: entityId=[" 
					+ entityId + "], keyId=[" + keyId + "]", e);
		}
		finally {
			context.endTX(bCommit);
		}
		
		return mapping.findForward("show" + entityId);
	}

    protected void deleteStageTasks(IClientContext context, int stageId)
			throws ISPACException {

		DbCnt cnt = context.getConnection();

		try {

			// Consulta SQL
			String sql = new StringBuffer("WHERE ID_FASE=").append(stageId)
					.toString();

			// Eliminar las relaciones fase <-> trámites
			CollectionDAO collection = new CollectionDAO(CTStageTaskDAO.class);
			collection.delete(cnt, sql);

		} catch (ISPACException e) {
			logger.error(
					"Error al eliminar los trámites asociados a la fase "
							+ stageId, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
	}


    protected void deleteTaskDocTypes(IClientContext context, int taskId) 
    		throws ISPACException {
    	
    	DbCnt cnt = context.getConnection();
    	
		try {
			
			// Consulta SQL
			String sql = new StringBuffer("WHERE ID_TRAMITE=")
				.append(taskId).toString();

			// Eliminar las relaciones trámite <-> tipos de documentos
			CollectionDAO collection = new CollectionDAO(CTTaskTpDocDAO.class);
			collection.delete(cnt, sql);

		} catch (ISPACException e) {
			logger.error("Error al eliminar los tipos de documentos asociados al trámite " + taskId, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
	}

    
    protected void deleteUserAsociated(IClientContext context, int idSearchForm) throws Exception{
    	DbCnt cnt = context.getConnection();
    	try {
			CollectionDAO collection = new CollectionDAO(
					CTFrmBusquedaOrgDAO.class);
			
			collection.delete(cnt, "WHERE ID_SEARCHFRM = "
					+ idSearchForm);
		} catch (Exception e) {
			logger.error(
					"Error al eliminar los usuario asociados al formulario de búsqueda específico"
							+ idSearchForm, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
    }
    
	protected void deleteReportReferences(IClientContext context,
			int idSearchForm) throws Exception {
		
		DbCnt cnt = context.getConnection();
		
		try {
			CollectionDAO collection = new CollectionDAO(
					FrmBusquedaReportDAO.class);

			collection.delete(cnt, "WHERE id_fmrbusqueda = " + idSearchForm);
			
		} catch (Exception e) {
			logger.error(
					"Error al eliminar las referencias a los informes del formulario de búsqueda "
							+ idSearchForm, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
	}
    
    protected void deleteDocTypeTemplates(IClientContext context, int docTypeId)
			throws ISPACException {

		DbCnt cnt = context.getConnection();

		try {

			// Eliminar el contenido de las plantillas del tipo de documento
			CollectionDAO collection = new CollectionDAO(TemplateDataDAO.class);
			collection.delete(cnt, new StringBuffer()
					.append("WHERE ID IN (SELECT ID FROM SPAC_P_PLANTDOC WHERE ID_TPDOC=")
					.append(docTypeId)
					.append(")")
					.toString());
			
			// Eliminar las plantillas específicas del tipo de documento
			collection = new CollectionDAO(PcdTemplateDAO.class);
			collection.delete(cnt, new StringBuffer()
					.append("WHERE ID_P_PLANTDOC IN (SELECT ID FROM SPAC_P_PLANTDOC WHERE ID_TPDOC=")
					.append(docTypeId)
					.append(")")
					.toString());

			// Eliminar las plantillas del tipo de documento
			collection = new CollectionDAO(TemplateDAO.class);
			collection.delete(cnt, new StringBuffer("WHERE ID_TPDOC=")
					.append(docTypeId)
					.toString());

		} catch (ISPACException e) {
			logger.error(
					"Error al eliminar las plantillas del tipo de documento "
							+ docTypeId, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
	}

    
    protected void deleteUserReportAsociated(IClientContext context, int idReport) 
    throws Exception{
    	DbCnt cnt = context.getConnection();
    	try {
			CollectionDAO collection = new CollectionDAO(
					CTReportOrgDAO.class);
			
			collection.delete(cnt, "WHERE ID_INFORME = "
					+ idReport);
		} catch (Exception e) {
			logger.error(
					"Error al eliminar los usuario asociados al informe"
							+ idReport, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
    }
    
    protected void deleteSearchFormsAssociatedToReport(IClientContext context, int idReport) 
    throws Exception{
    	DbCnt cnt = context.getConnection();
    	try {
			CollectionDAO collection = new CollectionDAO(
					FrmBusquedaReportDAO.class);
			
			collection.delete(cnt, "WHERE ID_INFORME = "
					+ idReport);
		} catch (Exception e) {
			logger.error(
					"Error al eliminar los formularios de búsqueda asociados al informe"
							+ idReport, e);
			throw e;
		} finally {
			context.releaseConnection(cnt);
		}
    }


}