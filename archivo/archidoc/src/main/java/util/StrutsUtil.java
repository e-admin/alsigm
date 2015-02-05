package util;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 *
 */
public class StrutsUtil {

	public static ActionForward findForward(ParamsSet params,
			ActionMapping mapping, String nameForward) {
		ActionForward forward = mapping.findForward(nameForward);
		String newPath = forward.getPath();
		boolean tieneInterrogante = forward.getPath().split("\\?").length > 1;
		boolean tieneParametros = tieneInterrogante;// de momento lo dejo
													// asi(supongo q si hay
													// interrognate hay param
		if (!tieneInterrogante) {
			newPath = newPath + "?";
		}
		if (!tieneParametros) {
			newPath = newPath + params.toString();
		} else
			newPath = newPath + "&" + params.toString();

		boolean redirect = forward.getRedirect();

		ActionForward newForward = new ActionForward();
		newForward.setPath(newPath);
		newForward.setRedirect(redirect);
		return newForward;
	}

	public static void setParamsToForward(ActionForward forward,
			ParamsSet params) {
		String newPath = forward.getPath();
		boolean tieneInterrogante = forward.getPath().split("\\?").length > 1;
		boolean tieneParametros = tieneInterrogante;// de momento lo dejo
													// asi(supongo q si hay
													// interrognate hay param
		if (!tieneInterrogante) {
			newPath = newPath + "?";
		}
		if (!tieneParametros) {
			newPath = newPath + params.toString();
		} else
			newPath = newPath + "&" + params.toString();

		/* boolean redirect = */forward.getRedirect();

		forward.setPath(newPath);
	}

}
