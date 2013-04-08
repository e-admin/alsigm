package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.webdav.util.URLEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EntrySearchAction extends BaseAction {
	
	protected static final String PARAM_ACTION		 		= "action";
	protected static final String PARAM_TYPE		 		= "type";
	protected static final String TYPE_ORGANIZATION		 	= "organization";
	protected static final String PARAM_UID		 			= "uid";
	protected static final String PARAM_VIEW		 		= "view";
	protected static final String PARAM_UIDINT		 		= "uidint";
	protected static final String PARAM_NO_VIEW_USERS 		= "noviewusers";
	protected static final String PARAM_NO_VIEW_GROUPS 		= "noviewgroups";
	protected static final String PARAM_ONLY_SELECT_USERS 	= "onlyselectusers";
	protected static final String PARAM_INVALID_SELECT 		= "invalidSelect";
	
	
	public ActionForward executeAction(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            SessionAPI session) throws Exception {
		
		String vista=request.getParameter("view");
		String[] typesEntriesToShow=null;
		if(StringUtils.isNotBlank(request.getParameter(PARAM_NO_VIEW_GROUPS))){
			typesEntriesToShow=new String [2];
			typesEntriesToShow[0]=IResponsible.PROPERTY_USER;
			typesEntriesToShow[1]=IResponsible.PROPERTY_ORGUNIT;
			
		}
		if(StringUtils.isNotBlank(request.getParameter(PARAM_ONLY_SELECT_USERS))){
			typesEntriesToShow=new String [1];
			typesEntriesToShow[0]=IResponsible.PROPERTY_USER;
			
		}
		 
		if(StringUtils.isNotBlank(vista) && "executeSearch".equalsIgnoreCase(vista)){
		
			EntityForm defaultForm= (EntityForm) form;

			//Obtenemos el filtro de la búsqueda
			String filter= defaultForm.getCriterio();
			
			if(StringUtils.isBlank(filter)){
			
				request.setAttribute("criterioVacio", "true");
				
			}
			else{
			
			IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
			Set set = directory.getEntryFromName(filter,typesEntriesToShow);
			Collection collection = RespFactory.createResponsibleSet(set);
			List lista = CollectionBean.getBeanList(new CustomItemCollection(collection));
			
		
		    Collections.sort(lista, Responsible.getRespComparator());
			// Elementos a mostrar
        	request.setAttribute("items", lista);
        	// Formateador 
        	// Cargar el formateador de la lista de responsables
	   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        	BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/frmsearchresplistformatter.xml"));
			request.setAttribute("FrmSearchOrgListFormatter", formatter);
			}
			
			
		}
		else  {
		   
			EntityForm defaultForm= (EntityForm) form;
			defaultForm.setCriterio("");
			
		}
		request.setAttribute("URLParams", buildURLParams(request.getParameterMap()));
		
			return mapping.findForward("success");
		
	}
	
	 protected String buildURLParams(Map parameters) {
	    	
	        StringBuffer url = new StringBuffer();
	        String key = null;
	        Map urlKeys = new HashMap();
	        URLEncoder encoder = new URLEncoder();

	        Iterator it = parameters.keySet().iterator();
	        while (it.hasNext()) {
	        	
	            key = (String) it.next();
	            if ((!urlKeys.containsKey(key)) && 
	            	(!key.equals("view") && !key.equals("criterio")  && (key.indexOf("property") == -1))) {
	            	
	                if (url.length() != 0) {
	                	url.append("&");
	                }
	            	url.append(key).append("=").append(encoder.encode(((String[])parameters.get(key))[0]));
	               	
	                urlKeys.put(key, null);
	            }
	        }

	        return url.toString();
	    }
	    

}