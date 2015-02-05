package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.AjaxSuggestAction;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class GetFilteredSequencesAction extends AjaxSuggestAction {

	public void processFilter() {
		if((getFilter() != null)&&(!"".equals(getFilter().trim()))){
			setFilter(" WHERE SEQUENCE_NAME LIKE '" + DBUtil.replaceQuotes(getFilter()) + "%'");
		}
		else{
			setFilter(" WHERE SEQUENCE_NAME LIKE '%'");
		}
	}

	public List getLabelValuedBeansList(SessionAPI session) throws ISPACException {

		List aux = new ArrayList();
		IClientContext cct = session.getClientContext();
		
		DbResultSet resultado = null;
		if(cct.getConnection().isOracleDbEngine()){
			resultado = cct.getConnection().executeQuery("SELECT SEQUENCE_NAME FROM USER_SEQUENCES " + getFilter());
		}else{
			resultado = cct.getConnection().executeQuery("SELECT SEQUENCE_NAME FROM SPAC_SQ_SEQUENCES " + getFilter());
		}
	
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
						 aux.add(new LabelValueBean(rs.getString("SEQUENCE_NAME"), rs.getString("SEQUENCE_NAME")));
					 }
				}else{
					aux.add(new LabelValueBean(rs.getString("SEQUENCE_NAME"), rs.getString("SEQUENCE_NAME")));
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
			throw new ISPACException("Error recuperando secuencias de la base de datos.");
		}finally{
			if(resultado != null) resultado.close();
		}
		
		return aux;
	}
}
