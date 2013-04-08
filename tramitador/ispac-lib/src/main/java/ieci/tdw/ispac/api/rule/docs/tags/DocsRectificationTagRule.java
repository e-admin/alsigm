package ieci.tdw.ispac.api.rule.docs.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.util.Iterator;
import java.util.List;

/**
 * Regla creada para ser llamada desde un tag en una plantilla.
 * Retorna una cadena que contiene un listado de documentos a subsanar.
 *
 */
public class DocsRectificationTagRule implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        try{
			//----------------------------------------------------------------------------------------------
	        ClientContext cct = (ClientContext) rulectx.getClientContext();
	        IInvesflowAPI invesFlowAPI = cct.getAPI();
	        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	        //----------------------------------------------------------------------------------------------
	
	        String xmlDocumentos = null; //Listado de documentos en formato XML
	        String listDocumentos = "";  //Listado de documentos a subsanar 
	        
	        //Obtener Identificador del Tramite de Solicitud de Subsanación
	        String idTaskCTStr = ConfigurationMgr.getVarGlobal(cct, ConfigurationMgr.ID_TASK_SOLICITUD_SUBSANACION);
	        
	        //Obtener entrada de InfoTramite con la lista de Documentos a subsanar
	        String strQuery = "WHERE ID = ( SELECT MAX(ID) FROM SPAC_INFOTRAMITE WHERE NUMEXP = '" + DBUtil.replaceQuotes(rulectx.getNumExp())
	        				+ "' AND ID_CT_TRAMITE = " + idTaskCTStr + " AND ID_PCD = " + rulectx.getProcedureId() + " )";
	        
	        //TODO: FALTA OBTENER LA LISTA DE DOCUMENTOS COMPROBANDO TAMBIEN EL IDENTIFICADOR DE LA FASE (ERROR: FASE = 0)

	        IItemCollection collection = entitiesAPI.queryEntities(SpacEntities.SPAC_INFOTRAMITE, strQuery);
	        Iterator it = collection.iterator();
	        if (it.hasNext())
	            xmlDocumentos = ((IItem)it.next()).getString("INFO");

	        //Si se han obtenido los documentos
	        if (xmlDocumentos != null) {
	            XmlFacade xmlFacade = new XmlFacade(xmlDocumentos);
	            List idDocs = xmlFacade.getList("/documentos/documento/@id");
	            Iterator itDoc = idDocs.iterator();
	            while (itDoc.hasNext()) {
	                String idDoc = (String)itDoc.next();
	                String xPath = "/documentos/documento[@id='" + idDoc + "']/";
	                String pendiente = xmlFacade.get(xPath+"pendiente");
	                if (pendiente.equals("SI")) {
	                    String documento = xmlFacade.get(xPath+"nombre");
	                    if (listDocumentos.length() > 0) listDocumentos += "\n\n";
	                    	listDocumentos += "- " + documento;
	                }
	            }
	        }// else {
	        //    throw new ISPACRuleException("No se ha encontrado la lista de Documentos en la información del Trámite.");
	        //}
	        
	        return listDocumentos;
	    } catch (Exception e) {
	        throw new ISPACRuleException("Error confecionando listado de documentos a subsanar.", e);
	    }     
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}
}
