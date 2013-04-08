package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.AjaxSuggestAction;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class GetFilteredTablesAction extends AjaxSuggestAction {

	public void processFilter() {
		if((getFilter() != null)&&(!"".equals(getFilter().trim()))){
			setFilter(getFilter() + "%");
		}else{
			setFilter("%");
		}
	}

	public List getLabelValuedBeansList(SessionAPI session) throws ISPACException{

		List aux = new ArrayList();
		
		IClientContext cct = session.getClientContext();
		DbResultSet resultado = cct.getConnection().getTables(getFilter());
		int contador = 0;
		int restoFilas = 0;
		boolean sw = false;
		boolean empty = true;
		try {
			ResultSet rs = resultado.getResultSet();
			while(rs.next()){
				empty = false;
				if(getNumRows() > -1){
					 if (++contador > getNumRows()){
						 sw = true;
						 restoFilas++;
					 }else{
						 aux.add(new LabelValueBean(rs.getString("TABLE_NAME"), rs.getString("TABLE_NAME")));
					 }
				}else{
					aux.add(new LabelValueBean(rs.getString("TABLE_NAME"), rs.getString("TABLE_NAME")));
				}
			}
			if(sw){
				aux.add(new LabelValueBean( "y " + restoFilas + " más ...", "null"));
			}
			if(empty){
				aux.add(new LabelValueBean( "", "null"));
				aux.add(new LabelValueBean( "No existen conincidencias.", "null"));
			}
		} catch (SQLException e) {
			throw new ISPACException("Error recuperando tablas del esquema.");
		}finally{
			if(resultado != null) resultado.close();
		}	

		return aux;
	}

}
