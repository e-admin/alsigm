package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPubRulesGroupListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_RULES_READ,
				ISecurityAPI.FUNC_PUB_RULES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

        IItemCollection itemcol = publisherAPI.getRules();
        List pubrulesgrouplist=CollectionBean.getBeanList(itemcol);
        
        // Filtrar dejando una única regla por grupo
        // para que los colores salgan alternos en las filas del displaytag
        pubrulesgrouplist = filterPubRulesGroupList(pubrulesgrouplist);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/publisher/pubrulesgrouplistformatter.xml"));
		request.setAttribute("PubRulesGroupListFormatter", formatter);

   	 	request.setAttribute("PubRulesGroupList",pubrulesgrouplist);
   	 	
   	 	// Obtener los procedimientos para las descripciones en la vista
   	 	IItemCollection procedureCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_PROCEDURE, null);
   	 	request.setAttribute("ProceduresMap", procedureCollection.toMapStringKey());
   	 	
   	 	// Obtener las fases para las descripciones en la vista
   	 	IItemCollection stageCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_STAGE, null);
   	 	request.setAttribute("StagesMap", stageCollection.toMapStringKey());
   	 	
   	 	// Obtener los trámmites para las descripciones en la vista
   	 	IItemCollection taskCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_TASK, null);
   	 	request.setAttribute("TasksMap", taskCollection.toMapStringKey());
   	 	
   	 	// Obtener los tipos de documentos para las descripciones en la vista
   	 	IItemCollection typeDocCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TYPEDOC, null);
   	 	request.setAttribute("TypeDocsMap", typeDocCollection.toMapStringKey()); 

   	 	return mapping.findForward("success");
	}
 	
 	/**
 	 * Filtrar los grupos de la lista de reglas
 	 * para que los colores salgan alternos en las filas del displaytag.
 	 *  
 	 * @param pubrulesgrouplist Lista de reglas agrupadas.
 	 * @return Lista de reglas filtrada.
 	 * @throws ISPACException Si se produce algún error.
 	 */
 	private List filterPubRulesGroupList(List pubrulesgrouplist) throws ISPACException {
 		
        if ((pubrulesgrouplist != null) &&
            (!pubrulesgrouplist.isEmpty())) {
            	
        	int idPcd = 0;
        	int idFase = 0;
        	int idTramite = 0;
        	int idTipoDoc = 0;
        	
        	List pubrulesgrouplistAux = new ArrayList();
        	
        	Iterator it = pubrulesgrouplist.iterator();
        	while (it.hasNext()) {
        		
        		ItemBean itemBean = (ItemBean) it.next();
        		IItem rule = itemBean.getItem();
        		
        		if ((rule.getInt("ID_PCD") != idPcd) ||
        			(rule.getInt("ID_FASE") != idFase) ||
        			(rule.getInt("ID_TRAMITE") != idTramite) ||
        			(rule.getInt("TIPO_DOC") != idTipoDoc)) {
        			
        			pubrulesgrouplistAux.add(itemBean);
        			
                	idPcd = rule.getInt("ID_PCD");
                	idFase = rule.getInt("ID_FASE");
                	idTramite = rule.getInt("ID_TRAMITE");
                	idTipoDoc = rule.getInt("TIPO_DOC");
        		}
        	}
        	
        	return pubrulesgrouplistAux;
        }
        
        return pubrulesgrouplist;
 	}
 	
}