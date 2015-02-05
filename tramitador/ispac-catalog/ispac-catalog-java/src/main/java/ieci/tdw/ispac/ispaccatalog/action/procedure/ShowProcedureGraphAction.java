package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.AjaxBaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.StringReader;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowProcedureGraphAction extends AjaxBaseAction {
	
 	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        String spcdId = request.getParameter("pcdId");

        if (spcdId == null) {
            return null;
        }

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IProcedureAPI pcdAPI= invesFlowAPI.getProcedureAPI();

        String pcdxml=pcdAPI.getProcedureXml(Integer.parseInt(spcdId));

        ISPACRewrite ispacpath = new ISPACRewrite(getServlet().getServletContext());
        String xslurl = ispacpath.rewriteRealPath("xsl/ispacsvg.xsl");

        ServletOutputStream out = response.getOutputStream();
    	response.setHeader("Pragma", "public");
    	response.setHeader("Cache-Control", "max-age=0");
        response.setContentType("image/svg+xml");
//      response.setHeader("Content-Transfer-Encoding", "binary");

        try {
        	
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xmlSource = new StreamSource(new StringReader(pcdxml));
            Source xslSource = new StreamSource(new URL("file", "", xslurl).openStream());
            
            Transformer transformer = tFactory.newTransformer(xslSource);
            transformer.setParameter("staticContext", 
            		StaticContext.getInstance().getBaseUrl(request.getContextPath()));
            transformer.setParameter("dynamicContext", request.getContextPath());
            transformer.transform(xmlSource, new StreamResult(out));
            
        } catch (Exception e) {
            throw new ISPACException("Error en ShowProcedureGraphAction (" 
            		+ pcdxml + "," + xslurl + ")", e);
        }

        return null;
	}
}