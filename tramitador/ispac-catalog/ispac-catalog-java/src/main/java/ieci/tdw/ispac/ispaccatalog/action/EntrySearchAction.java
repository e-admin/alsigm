package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
		String mode=request.getParameter("mode");
		if(StringUtils.isBlank(mode)){
			mode="Simple";
		}
		
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
			String filter= defaultForm.getFilter();
			if(StringUtils.isBlank(filter)){
				throw new ISPACInfo("select.object.warning.filter.required");
			}
			// Obtener el número máximo de registros a mostrar 
			
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
        	BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctfrmsearchresplistformatter.xml"));
			request.setAttribute("CTFrmSearchOrgListFormatter", formatter);
         
			
		}
		else  {
		   
			EntityForm defaultForm= (EntityForm) form;
			defaultForm.setFilter("");
			
		}
		
			return mapping.findForward("success"+mode);
		
	}

}