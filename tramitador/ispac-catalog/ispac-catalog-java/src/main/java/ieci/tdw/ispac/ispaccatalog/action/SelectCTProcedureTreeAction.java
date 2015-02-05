package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectCTProcedureTreeAction extends ShowCTProceduresTreeAction {
	
 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		super.executeAction(mapping, form, request, response, session);
 		
 		SelectObjForm substform = (SelectObjForm) form;
 		
 		String caption = substform.getCaption();
 		String decorator = substform.getDecorator();
 		
	    // Establecer el título
	    if (StringUtils.isBlank(caption)) {
	        caption = "catalog.selectobj.caption.default";
	    }
        request.setAttribute("CaptionKey", caption);
         		
        // Establecer el formateador
        setFormatter(request, "CTProceduresListFormatter", decorator);
 		
 		return mapping.findForward("success");
 	}

}
