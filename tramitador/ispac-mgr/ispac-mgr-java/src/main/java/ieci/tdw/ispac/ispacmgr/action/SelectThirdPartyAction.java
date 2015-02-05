package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class SelectThirdPartyAction extends BaseAction
{
	public ActionForward executeAction( ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws Exception
	{

		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		IState currentstate = managerAPI.currentState(getStateticket(request));
		IInvesflowAPI invesflowAPI = session.getAPI();
		IThirdPartyAPI thirdPartyAPI = invesflowAPI.getThirdPartyAPI();
		
		int entity = currentstate.getEntityId();
		int key = currentstate.getEntityRegId();

		String nombre = request.getParameter("nombre");
		if (nombre == null)
		{
			nombre = "";
		}
		String apellidos = request.getParameter("apellidos");
		if (apellidos == null)
		{
			apellidos = "";
		}
		/*
		 * Nombre de la variable de sesión que mantiene los parámetros
		 * del tag de búsqueda.
		 */
		String parameters = request.getParameter("parameters");
		if (parameters == null)
		{
			parameters = "";
		}

		String thirdparty = request.getParameter("thirdparty");
		if (thirdparty == null)
		{
			thirdparty = "";
		}

		String option = request.getParameter("option");
		if (option == null)
		{
			option = "select";
		}

		if (thirdparty.length() != 0)
		{
			
			if (thirdPartyAPI == null) {
				throw new ISPACInfo("exception.thirdparty.notConfigured");
			}

			IThirdPartyAdapter tercero = thirdPartyAPI.lookupById(thirdparty);
			ActionForward actionFordward = null;
			String squeryString = "";

			if (tercero == null)
			{
				ActionMessages errors = new ActionMessages();
				errors.add("terceros", new ActionMessage("error.terceros"));
				saveErrors(request, errors);
				actionFordward = mapping.findForward("success");
			}
			else
			{
				request.setAttribute("ThirdParty", tercero);

				actionFordward = mapping.findForward("selectInterested");
				squeryString = "?option="
					         + option
					         + "&entity="
							 + entity
							 + "&key="
							 + key;
			}

//			// Obtiene los parámetros del tag de búsqueda y los salva en el request
//			if (parameters.length() != 0)
//			{
//				String sParameters = session.getClientContext().getSsVariable( parameters);
//				if (sParameters != null)
//				{
//					request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));
//				}
//			}

			return new ActionForward( actionFordward.getName(), actionFordward.getPath() + squeryString, false);
		}

		// obtenemos el decorador
		//FormatterManager.storeBFSelectThirdPartyAction(request,getPath());
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/searchformatter.xml"));
		request.setAttribute("Formatter", formatter);

		IThirdPartyAdapter [] terceros = null;
		if (nombre.length() != 0 || apellidos.length() != 0) {
			terceros = thirdPartyAPI.lookup(
					nombre,apellidos,"");
		}

		request.setAttribute("ParticipantList", terceros);
		request.setAttribute("nombre", nombre);
		request.setAttribute("apellidos", apellidos);

		/*
		 * Mantiene el nombre de la variable de sesión donde se guardan
		 * los parámetros del tag de la búsqueda.
		 */
		request.setAttribute("parameters", parameters);

		return mapping.findForward("success");
	}
}