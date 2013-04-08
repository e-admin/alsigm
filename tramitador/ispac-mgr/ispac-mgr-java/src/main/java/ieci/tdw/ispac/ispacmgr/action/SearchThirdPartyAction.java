package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.ThirdPartyForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para realizar búsquedas en terceros.
 *
 */
public class SearchThirdPartyAction extends BaseAction {

	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(SearchThirdPartyAction.class);
	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		if(StringUtils.isNotEmpty(request.getParameter("search"))){
			if(StringUtils.isNotEmpty(request.getParameter("nombre"))){
				request.setAttribute("nombre", request.getParameter("nombre"));
			}
			else{
				request.setAttribute("nombre", "");
			}
			if(StringUtils.isNotEmpty(request.getParameter("apellido1"))){
				request.setAttribute("apellido1", request.getParameter("apellido1"));
			}
			else{
				request.setAttribute("apellido1", "");
			}
			if(StringUtils.isNotEmpty(request.getParameter("apellido2"))){
				request.setAttribute("apellido2", request.getParameter("apellido2"));
			}
			else{
				request.setAttribute("apellido2", "");
			}
			return mapping.findForward("search");
		}
		
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance()
			.getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		IInvesflowAPI invesflowAPI = session.getAPI();
		IThirdPartyAPI thirdPartyAPI = invesflowAPI.getThirdPartyAPI();
		
		int entity = currentstate.getEntityId();
		int key = currentstate.getEntityRegId();

		ThirdPartyForm thirdForm = (ThirdPartyForm) form;
		IThirdPartyAdapter [] terceros = null;
		String idThirdParty = (String)request.getParameter("id");
		//Si tenemos informado el id en la request quiere decir que venimos de la jsp en la que se selecciona un usuario 
		//cuando tenemnos varios , ej: con el mismo dni dos personas hay que decir con cual queremos trabajar
		//en ese caso debemos indicar que el botón volver ha de estar activo
		if(StringUtils.isNotEmpty(idThirdParty)){
			request.setAttribute("return", "1");
		}
		String paramDefaultValues = (String)request.getParameter("defaultValues");
		boolean defaultValues = (StringUtils.equalsIgnoreCase(paramDefaultValues, "TRUE") ? true : false);  
		if (StringUtils.isNotEmpty(idThirdParty)){
			IThirdPartyAdapter tercero = thirdPartyAPI.lookupById(idThirdParty, defaultValues);
			if (tercero == null){
				throw new ISPACInfo("exception.info.tercero.notFoundById", new Object[] {idThirdParty},false);
			}
			terceros = new IThirdPartyAdapter[]{tercero};
		}else{
			
			if (thirdPartyAPI == null) {
				throw new ISPACInfo("exception.thirdparty.notConfigured",false);
			}
			//Comprobamos si tenemos que buscar por nombre 
			String buscarPorNombre=(String)request.getParameter("searchByNombre");
			String nifcif="";
			if("1".equalsIgnoreCase(buscarPorNombre)){
				terceros=thirdPartyAPI.lookup(thirdForm.getNombre(), thirdForm.getApellido1(), thirdForm.getApellido2());
				request.setAttribute("searchByNombre", "1");
				
			}
			else{
				// Nombre del campo que contiene el valor de la búsqueda.
				String field = request.getParameter("field");
		
				// NIF/CIF del tercero a buscar
				nifcif = thirdForm.getProperty(field);
				
				if (StringUtils.isEmpty(nifcif)) {
					 if(StringUtils.isNotBlank(thirdForm.getNif())){ 
						  nifcif=thirdForm.getNif(); 
					 } 
					else{ 
						  throw new ISPACInfo("exception.info.emptyNifCif",false); 
					} 
				} 
				request.setAttribute("nif", nifcif); 
					
				// Buscar el tercero a partir del NIF/CIF
				if (defaultValues){
					terceros = thirdPartyAPI.lookup(nifcif);
				}else{
					terceros = thirdPartyAPI.lookup(nifcif, false);
				}
			}
			if (ArrayUtils.isEmpty(terceros)) {
				//...mostramos un mensaje informando del suceso
				if(StringUtils.isNotEmpty(nifcif)) {
				    if (logger.isInfoEnabled()) {
				    	
					    logger.info("No se han encontrado datos para el NIF/CIF '" + nifcif + "'");
				    }
					throw new ISPACInfo("exception.info.tercero.noDataNifCif", new Object[] {nifcif},false);
				}
				else{
					if (logger.isInfoEnabled()) {
					    logger.info("No se han encontrado datos para el Nobmre: '" +
					    			thirdForm.getNombre() + "' , Apellido1: '"+
					    			thirdForm.getApellido1()+"' Apellido2: '"+
					    			thirdForm.getApellido2()+"'");
				    }
					throw new ISPACInfo("exception.info.tercero.noDataNombreApellidos", new Object[] {thirdForm.getNombre(), thirdForm.getApellido1(),thirdForm.getApellido2()},false);
				}
			} 			
		}			
		if (terceros.length > 1) {
			request.setAttribute(ActionsConstants.THIRDPARTY_LIST, terceros);
			ActionForward action = mapping.findForward("thirdPartyList");
			String squeryString = "";
			if (defaultValues)
				squeryString = "?defaultValus=true";
			return new ActionForward(action.getName(), action.getPath() + squeryString , false);
		} else {
			IThirdPartyAdapter tercero = terceros[0];
			request.setAttribute(ActionsConstants.THIRDPARTY, tercero);
			
			//Si solo hay un tercero con (0 ó 1) ninguna direccion postal y una o nidireccion electronica se establece sin falta de confirmacion
			if (	   tercero.getDireccionesPostales()!= null 
					&& tercero.getDireccionesPostales().length <2 
					&& tercero.getDireccionesElectronicas()!= null 
					&& tercero.getDireccionesElectronicas().length <2
				){
				String sActions = "success";
				ActionForward action = mapping.findForward("setInterested");
				String squeryString = new StringBuffer()
					.append("?actions=").append(sActions)
					.append("&entity=").append(entity)
					.append("&key=").append(key)
					.toString();
				return new ActionForward(action.getName(), action.getPath() 
						+ squeryString, false);
			}
			else{
				return mapping.findForward("summaryInterested");
			}
		}

	}
}