package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.SearchResourceListFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.reports.xsl.ParameterUtils;
import ieci.tdw.ispac.ispaclib.search.PropertiesHelper;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispacmgr.action.form.ReportForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

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

public class GenerateReportAction extends BaseDispatchAction {

	public ActionForward generate(ActionMapping mapping,
								  ActionForm form,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  SessionAPI session) throws Exception {

		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		IState currentstate = managerAPI.currentState(getStateticket(request));
		IInvesflowAPI invesflowAPI = session.getAPI();
		IReportsAPI reportsAPI = invesflowAPI.getReportsAPI();
		
		// Número de expediente
		String numExp = currentstate.getNumexp();

		// Identificador del informe
		String strReportId = request.getParameter("id");
		int reportId = Integer.parseInt(strReportId);
		
		String strPosition = request.getParameter("position");
		
		// Información del informe
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
		IItem ctReport = catalogAPI.getCTReport(reportId);

		
		// Formulario con los parámetros del informe
		ReportForm reportForm = (ReportForm) form;
		
		// Validar los parámetros del informe
		reportsAPI.validatePDFReportParams(reportForm.getValuesMap(), reportForm.getTypesMap());
		
		
	

		// Generar el informe 
		String [] numExpsSearch=(String[]) request.getSession().getAttribute("numExpsSearch");
		FileTemporaryManager ftMgr = FileTemporaryManager.getInstance();
		// Crear un fichero temporal para el zip
		File pdfFile = ftMgr.newFile(".pdf");
	
		try{
			OutputStream pdfOutputStream = new FileOutputStream(pdfFile);
			reportsAPI.generatePDFReport(ctReport, 
										strPosition, 
										numExp, 
										session.getClientContext().getLocale(), 
										pdfOutputStream,
										currentstate.getStageId(), 
										currentstate.getTaskId(), 
										numExpsSearch,
										reportForm.getParams());
	
			pdfOutputStream.flush();
			pdfOutputStream.close();
			// Establecer el response y copiamos el pdf generado
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=0");
			response.setContentType("application/pdf");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + ctReport.getString("NOMBRE") + ".pdf\"");
			FileUtils.copy(pdfFile, response.getOutputStream());
		
		}catch(Exception e){
			throw new ISPACInfo("exception.report.generate",false);
			
		}finally{
			ftMgr.delete(pdfFile);
		}
		
		return null;
	}
	
	public ActionForward select(ActionMapping mapping, 
								ActionForm form,
								HttpServletRequest request, 
								HttpServletResponse response,
								SessionAPI session) throws Exception {


		// Identificador del informe
		String strReportId = request.getParameter("id");
		request.setAttribute("ID", strReportId);
		
		// Filas
		String strFilas = request.getParameter("filas");
		request.setAttribute("FILAS", strFilas);

		// Columnas
		String strColumnas = request.getParameter("columnas");
		request.setAttribute("COLUMNAS", strColumnas);

		return mapping.findForward("select");
	}
	/**
	 * Esta método solo sera invocado en aquellos casos que el informe tenga valor en el campo parámetros
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward params(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();

		IInvesflowAPI invesflowAPI = session.getAPI();

		Map params = request.getParameterMap();

//		Identificador del informe
		String strReportId = request.getParameter("id");
		request.setAttribute("ID", strReportId);
		int reportId = Integer.parseInt(strReportId);

//		Información del informe
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
		IItem ctReport = catalogAPI.getCTReport(reportId);

		String xml = ctReport.getString("FRM_PARAMS");
		
		int filasColumnas=0;
		int filas=ctReport.getInt("FILAS");
			
	    if(filas>0){
	    	filasColumnas=1;
	    	request.setAttribute("FILAS", filas+"");
	    	request.setAttribute("COLUMNAS", ctReport.getString("COLUMNAS"));
	    }

		ISPACRewrite ispacpath = new ISPACRewrite(getServlet().getServletContext());
		String xslurl = ispacpath.rewriteRealPath("xsl/ReportParamsForm.xsl");

		StringWriter sw = null;

		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xmlSource = new StreamSource(new StringReader(xml));
			Source xslSource = new StreamSource(new java.net.URL("file", "", xslurl).openStream());
			Transformer transformer = tFactory.newTransformer(xslSource);
			sw = new StringWriter();
			ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, cct.getLocale());
			transformer.setParameter("resourceBundle", resourceBundle);
			transformer.setParameter("mapParams", params);
			transformer.setParameter("filasColumnas", filasColumnas+"");

			Properties properties = SearchResourceListFactory.getSearchResourceProperties(SearchResourceListFactory.REPORT_RESOURCETYPE, xml);
			PropertiesHelper propertiesHelper = new PropertiesHelper(cct.getLocale(), properties, resourceBundle);
			transformer.setParameter("propertiesHelper", propertiesHelper);
			
			
			transformer.setParameter("request", request);
			transformer.setParameter("parameterUtils", new ParameterUtils());
			transformer.setParameter("userUID", session.getClientContext().getUser().getUID());
			transformer.setParameter("userName", session.getClientContext().getUser().getName());
			transformer.setParameter("deptUID", session.getClientContext().getUser().getOrgUnit().getUID());
			transformer.setParameter("deptName", session.getClientContext().getUser().getOrgUnit().getName());
			
			
			transformer.transform(xmlSource, new StreamResult(sw));
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en SearchMgr::buildHTMLSearchForm (" + xml + ","
					+ xslurl + ")", e);
		}

		request.setAttribute("Form", sw.getBuffer().toString());

		request.setAttribute("Caption", getMessage(request, cct.getLocale(), "select.reportParams.title", new String[] {ctReport.getString("NOMBRE")}));

		return mapping.findForward("params");
	}


}
