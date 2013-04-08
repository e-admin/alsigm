package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.OrderUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.BaseDispatchAction;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public class OrderPropuestaSesionAction extends BaseDispatchAction {

	/* Logger de la clase. */
    private static final Logger logger =
    	Logger.getLogger(OrderPropuestaSesionAction.class);
	public ActionForward subir(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("Vamos a subir");
		}
		return order(mapping,form,request, session,true);
	}

	public ActionForward bajar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("Vamos a bajar");
		}
		return order(mapping,form,request, session,false);

	}
private ActionForward order(ActionMapping mapping,ActionForm form, HttpServletRequest request, SessionAPI session, boolean subir) throws ISPACException {



		ClientContext cct = session.getClientContext();

		IManagerAPI managerAPI = ManagerAPIFactory.getInstance()
			.getManagerAPI(cct);
		ieci.tdw.ispac.ispacweb.api.IState currentstate = managerAPI.currentState(getStateticket(request));



		EntityForm defaultForm = (EntityForm) form;
		String numexp =defaultForm.getProperty(SecretariaConstants.ENTITY_PROPUESTA_SESION+":NUMEXP");


		if(logger.isDebugEnabled()){
			logger.debug("La ordenación afectará a los registros de la entidad SEC_PROPUESTA_SESION con numexp a: "+numexp);
		}
		IEntitiesAPI  entitiesAPI= session.getAPI().getEntitiesAPI();
		String[] seleccion = new String [1];
		seleccion[0]=defaultForm.getProperty(SecretariaConstants.ENTITY_PROPUESTA_SESION+":ID");

		// Ejecución en un contexto transaccional
		boolean bCommit = false;

		try {
	        // Abrir transacción
	        cct.beginTX();
	        /*Obtengo todos los valores de la entidad*/
			IItemCollection itemcol = entitiesAPI.queryEntities(
					SecretariaConstants.ENTITY_PROPUESTA_SESION, "WHERE NUMEXP='"+DBUtil.replaceQuotes(numexp)+"' ORDER BY  " +SecretariaConstants.FIELD_PROPUESTA_SESION_ORDEN	);

	        List <IItem> listPars = new ArrayList();
	        List <Boolean> checkChange=new ArrayList();
	        OrderUtil.ordenar(subir, "ORDEN", itemcol, seleccion, listPars, checkChange);

	        int i=0;


	        for (i=0; i<listPars.size();i++) {
	        	IItem item=(IItem) listPars.get(i);
	         //se puede utilizar la variable i xq se ha usuado el recorrido del iterador para inicializar esta estructura.
	        	if( checkChange.get(i).toString().equals("true") ){

				   item.store(session.getClientContext());
	        	}


			}
	        request.setAttribute("refresh", "true");

			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;

	        String param = currentstate.getQueryString();
	     // Mantener la ordenación de la lista si existe
			String displayTagOrderParams;
			try {
				displayTagOrderParams = getDisplayTagOrderParams(request);
			} catch (Exception e) {
				logger.error("Error al ordenar las propuestas"+e);
				throw new ISPACException(e);
			}
			if (!StringUtils.isEmpty(displayTagOrderParams)) {
				param = param + "&" + displayTagOrderParams;
			}

			// Mantener la pestaña de datos activa
	    	if (request.getParameter("block") != null){
	    		String block = "&block="+request.getParameter("block");
	    		param = param + block;
	    	}

	    	// Mantener la forma de presentar el formulario
	    	if (request.getParameter("form") != null){
	    		String paramForm = "&form="+request.getParameter("form");
	    		param = param + paramForm;
	    	}

	    	// Mantener no utilizar el formulario por defecto
	    	if (request.getParameter("nodefault") != null){
	    		String paramForm = "&nodefault="+request.getParameter("nodefault");
	    		param = param + paramForm;
	    	}
	    	ActionForward action = mapping.findForward(currentstate.getLabel());
			return new ActionForward(action.getName(), action.getPath() + param, action.getRedirect());
		}
		finally {
			cct.endTX(bCommit);
		}
	}

}
