package ieci.tdw.ispac.ispaccatalog.action.templates;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.ispaccatalog.action.AjaxSearchEntitySuggestAction;

import javax.servlet.http.HttpServletRequest;

public class AjaxSearchTemplateSuggestAction extends
		AjaxSearchEntitySuggestAction {

	private int idDocType;
	
	public void getParameters(HttpServletRequest request){
		super.getParameters(request);
		String sIdTemplate = getExtraParam();
		try{
			idDocType = Integer.parseInt(sIdTemplate);
		}catch(NumberFormatException e){
			idDocType = -1;
		}
		if(request.getParameter("searchField") != null){
			setSearchProperty(request.getParameter("searchField"));
		}else{
			setSearchProperty( "NOMBRE");
		}
		setEntityId(ICatalogAPI.ENTITY_CT_TEMPLATE);
	}
	
	public void processFilter() {
		super.processFilter();
		
		if(getFilter() != null){
			setFilter(getFilter() + " AND ID_TPDOC = " + idDocType);
		}else{
			setFilter(" WHERE ID_TPDOC = " + idDocType);
		}

	}

}
