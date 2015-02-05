package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

public class AjaxSearchEntitySuggestAction extends AjaxSuggestAction {

	private int entityId;
	private String searchProperty;
	private String sqlQuery;
	
	public void getParameters(HttpServletRequest request) {
		
		super.getParameters(request);
		
		String sEntityId = getExtraParam();
		try {
			entityId = Integer.parseInt(sEntityId);
		}
		catch(NumberFormatException e){
			entityId = -1;
		}
		
		if(request.getParameter("searchField") != null) {
			searchProperty = request.getParameter("searchField");
		}
		else{
			searchProperty = "NOMBRE";
		}
		
		if(request.getParameter("sqlQuery") != null) {
			sqlQuery = "AND (" + request.getParameter("sqlQuery") + ")";
		}
		else {
			sqlQuery = "";
		}
	}

	public void processFilter() {
		
		if((getFilter()!=null)&&(!"".equals(getFilter().trim()))){
			
			setFilter(" WHERE " + searchProperty + " LIKE '%" + DBUtil.replaceQuotes(getFilter()) + "%' " + sqlQuery);
		}
		else{
			setFilter(null);
		}
	}

	public List getLabelValuedBeansList(SessionAPI session)	throws ISPACException {

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

        IItemCollection itemcol=catalogAPI.queryCTEntities(entityId, getFilter());
        List ctentlist=CollectionBean.getBeanList(itemcol);
        
        List aux = new ArrayList();
		int contador = 0;
		int restoFilas = 0;
		boolean sw = false;
		boolean empty = true;
		Iterator it = ctentlist.iterator();
			while(it.hasNext()){
				empty = false;
				ItemBean itemBean = (ItemBean)it.next();
				if(getNumRows() > -1){
					 if (++contador > getNumRows()){
						 sw = true;
						 restoFilas++;
					 }else{
						 aux.add(new LabelValueBean(itemBean.getString(searchProperty), itemBean.getString(searchProperty)));
					 }
				}else{
					aux.add(new LabelValueBean(itemBean.getString(searchProperty), itemBean.getString(searchProperty)));
				}
			}
			if(sw){
				aux.add(new LabelValueBean( "( + " + restoFilas + " )", "null"));
			}
			if(empty){
				aux.add(new LabelValueBean( "", "null"));
				aux.add(new LabelValueBean( "No existen conincidencias.", "null"));
			}
       
		return aux;
	}

	/**
	 * @return Returns the searchProperty.
	 */
	public String getSearchProperty() {
		return searchProperty;
	}
	/**
	 * @param searchProperty The searchProperty to set.
	 */
	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}

	/**
	 * @return Returns the entityId.
	 */
	public int getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId The entityId to set.
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
}