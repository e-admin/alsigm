package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AjaxSuggestAction extends AjaxBaseAction {

	private String filter;
	private String extraParam;
	private int numRows;
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		//Se obtienen los parámetros de la request
		getParameters(request);
		
		//Se obtiene la lista de beans
		List aux = generateLabelValuedBeansList(session);
		
		//Se pasa a la jsp en la request
		request.setAttribute("labelValueBeanList", aux);
		
		return mapping.findForward("success");		
	}
	

	public void getParameters(HttpServletRequest request){
		extraParam = request.getParameter("extraParam");
		filter = request.getParameter("filter");
		
		try {
			filter = new String(filter.getBytes(), "UTF-8");
		}
		catch (UnsupportedEncodingException uee) {}
		
		String sNumRows = request.getParameter("numRows");
		try{
			numRows = Integer.parseInt(sNumRows);
		}catch(NumberFormatException e){
			numRows = -1;
		}
	}
	/**
	 * Método que controla los pasos a ejecutar en el procesado 
	 * de una petición de filtrado.
	 * @param session
	 * @param filter
	 * @param numRows
	 * @return
	 * @throws ISPACException
	 */
	public List generateLabelValuedBeansList(SessionAPI session) throws ISPACException{
		processFilter();
		return getLabelValuedBeansList(session);
	}
	/**
	 * Método que consigue la lista de LabelValueBean's para devolver a la vista
	 * @param filter
	 * @param numRows
	 * @return
	 */
	public abstract List getLabelValuedBeansList(SessionAPI session) throws ISPACException;
	
	/**
	 * Método que formatea la cadena que llega como parámetro 
	 * consiguiendo el filtro a utilizar en la consulta.
	 * @param filter
	 * @return
	 */
	public abstract void processFilter();


	/**
	 * @return Returns the extraParam.
	 */
	public String getExtraParam() {
		return extraParam;
	}


	/**
	 * @param extraParam The extraParam to set.
	 */
	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}


	/**
	 * @return Returns the filter.
	 */
	public String getFilter() {
		return filter;
	}


	/**
	 * @param filter The filter to set.
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}


	/**
	 * @return Returns the numRows.
	 */
	public int getNumRows() {
		return numRows;
	}


	/**
	 * @param numRows The numRows to set.
	 */
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

}
