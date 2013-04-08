package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.action.form.EntityBatchForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SelectMultiValueAction extends SelectValidationTableAction {

	public ActionForward executeAction(  
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

				//---------------------- A P I S ---------------------------------
			    IInvesflowAPI invesFlowAPI = session.getAPI();
				IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
				//----------------------------------------------------------------

				//Identificador de la entidad
				String strEntity = getParameterOrPropertyParameter(request, "entity");
				int entityId = Integer.parseInt(strEntity);

				//Nombre del campo de entrada (inputField)
				String field = getParameterOrPropertyParameter(request, "field");

			    //Obtenemos los multivalues
				String[] multiValues = getMultivalues(request, field);

				//Nombre de la variable de sesión que mantiene los parámetros del tag de búsqueda.
				String parameters = getParameterOrPropertyParameter(request, "parameters");
				
				//Introducimos en el EntityForm los siguientes parametros para luego mostrarlos en el siguiente .jsp
				EntityBatchForm nextForm = new EntityBatchForm();
				nextForm.setProperty("entity",""+entityId);
				nextForm.setProperty("field",""+field);
				nextForm.setProperty("parameters",""+parameters);
				nextForm.setMultibox(multiValues);
				request.setAttribute("entityBatchForm",nextForm);
				
				
				//Introduce en la request la lista de valores previa consulta
				setValueList(request, session, entitiesAPI, entityId);
				
				// Obtiene el decorador
				CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
				BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/multivalueformatter.xml"));		
				request.setAttribute("Formatter", formatter);

				return mapping.findForward("success");
			}

			/**
			 * Devuelve como un Array de String los valores ya sean
			 * porque vienen selecionados en un multibox o como un parámetro
			 * cuyo contenido es un String de valores separados por coma ','.
			 * 
			 * @param request
			 * @param field Nombre del campo 
			 * @return Array de String con los valores recibidos en la request
			 */
			private String[] getMultivalues(HttpServletRequest request, String field){
			    
			    //Valor del campo de entrada
				if (request.getParameter(field) != null){
				    String listValues = request.getParameter(field);
				    return listValues.split(",");
				}
				return request.getParameterValues("multibox");
			    
			}
			
		    /**
		     * Buscar <code>parameter</code> como parámetro de la request
		     * y en caso de que no lo encuentre, lo busca como parámetro 
		     * con el nombre property(<code>parameter</code>)
		     * @param request
		     * @param parameter Nombre del parámetro a buscar
		     * @return
		     */
		    private String getParameterOrPropertyParameter(HttpServletRequest request, String parameter) {
		        if (request.getParameter(parameter) != null )
		           return request.getParameter(parameter);
		         return request.getParameter("property("+parameter+")");
		    }

		    /**
		     * @param request
		     * @param entitiesAPI
		     * @throws ISPACException
		     */
		    protected void setValueList(HttpServletRequest request, SessionAPI session, IEntitiesAPI entitiesAPI, int entityId) throws ISPACException {

				IItemCollection collection = null;
				String sqlQuery = "";
		        //Si se recibe el parametro action=search, se añade a la consulta la restriccion recibida en el parametro description
				if (StringUtils.equals((String)request.getParameter("action"),"search")){
					// CONTROLAMOS EN LA QUERY QUE LOS VALORES MOSTRADOS SEAN VIGENTES si la tabla tiene el campo VIGENTE
					//1 Vigente 0 No Vigente
				    sqlQuery = "WHERE VALOR LIKE '%" + DBUtil.replaceQuotes(request.getParameter("description")) + "%'";
				}
				sqlQuery = composeSqlQuery(request,session, sqlQuery);
				collection = entitiesAPI.queryEntities(entityId, sqlQuery);	
	        	request.setAttribute(ActionsConstants.PARAM_VALUE_LIST, CollectionBean.getBeanList(collection));
		    }

}
