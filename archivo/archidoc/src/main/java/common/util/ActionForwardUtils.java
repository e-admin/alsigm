package common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

public class ActionForwardUtils {

	public static ActionForward getActionForward(HttpServletRequest request,
			String methodName) {
		ActionForward ret = new ActionForward();
		String newPath = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		ret.setPath(UrlHelper.addUrlParameter(newPath, "method", methodName));
		ret.setRedirect(true);
		return ret;
	}

}
