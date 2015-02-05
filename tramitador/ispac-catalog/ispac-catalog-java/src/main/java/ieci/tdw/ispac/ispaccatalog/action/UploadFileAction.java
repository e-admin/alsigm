package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadFileAction extends BaseAction {
	
    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form, 
    								   HttpServletRequest request,
    								   HttpServletResponse response, 
    								   SessionAPI session) throws Exception {
    	
    	UploadForm uForm = (UploadForm) form;
        
    	FormFile ff = uForm.getUploadFile();
        
    	String idEntidad = uForm.getEntity();
        int idEntity = Integer.parseInt(idEntidad);
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(), idEntity);

        String property = null;
        String jspDest = null;
        
        // Formularios de búsqueda
        if (idEntity == ICatalogAPI.ENTITY_CT_SEARCHFORM) {
        
        	property = "FRM_BSQ";
        	jspDest= "/forms/catalog/ctfrmsearchform.jsp";
        	
    		// Para los formularios de búsqueda específicos
    		// obtener el listado de objetos de organizacion al que esta asociado el formulario
    		int tipo = Integer.parseInt(uForm.getProperty("TIPO"));
    		if (tipo == CTFrmBusquedaOrgDAO.SPECIFIC_TYPE) {
    			
    			ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();

    			// Responsables asignados al formulario de búsqueda
    			List permissionObjectOrganizactionList = catalogAPI.getSearchFormOrganization(Integer.parseInt(uForm.getKey()));
    	        
    			// Cargar el formateador de la lista de responsables
    	   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
    			BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctfrmsearchorglistformatter.xml"));
    			request.setAttribute("CTFrmSearchOrgListFormatter", formatter);

    	   	 	request.setAttribute("CTFrmSearchOrgList", permissionObjectOrganizactionList);			
    		}
        }
        // Informes
        else if(idEntity == ICatalogAPI.ENTITY_CT_INFORMES) {
        	
        	property = "XML";
        	jspDest = "/forms/catalog/ctreportform.jsp";
        	
        	IReportsAPI reportsAPI=	session.getAPI().getReportsAPI();
	   		// Responsables asignados al informe
			List permissionObjectOrganizactionList = reportsAPI.getReportOrganization(Integer.parseInt(uForm.getKey()));
	        
			// Cargar el formateador de la lista de responsables
	   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
			request.setAttribute("CTReportOrgListFormatter", formatter);

	   	 	request.setAttribute("CTReportOrgList", permissionObjectOrganizactionList);			
        	
        	
        	
        }
        else if(idEntity == ICatalogAPI.ENTITY_CT_HELPS){
        	property="CONTENIDO";
        	jspDest = "/forms/catalog/cthelpform.jsp";
        	// Idiomas soportados
        	request.setAttribute("languages", 
        						ConfigurationMgr.getLanguages(session.getClientContext(), getResources(request)));
        }
  
        try {
            uForm.setProperty(property, new String(ff.getFileData()));
        }
        catch (IOException e) {
        	
            throw new ISPACInfo("exception.uploadfile.error");
        }
        
        request.setAttribute("application", jspDest);
        
        return mapping.findForward("success");
    }
}
