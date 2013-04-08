package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.ispaccatalog.action.AjaxSearchEntitySuggestAction;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class AjaxSearchProcedureSuggestAction extends AjaxSearchEntitySuggestAction {

	public void processFilter() {
		// Selecionamos la última versión de cada procedimiento.
		String whereClause =  " WHERE ID IN ( "
							+ " SELECT MAX(ID) FROM SPAC_P_PROCEDIMIENTOS "
							+ " GROUP BY ID_GROUP "
							+ " ) ";
		if((getFilter()!= null) && (!"".equals(getFilter().trim()))){
			whereClause += " AND " + getSearchProperty() + " LIKE '%" + DBUtil.replaceQuotes(getFilter()) + "%' ";
		}
		
		setFilter(whereClause);
	}
}
