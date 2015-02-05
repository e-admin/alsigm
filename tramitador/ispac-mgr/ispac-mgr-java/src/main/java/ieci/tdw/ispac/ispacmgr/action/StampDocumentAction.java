package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.gendoc.stamp.StampConnector;
import ieci.tdw.ispac.ispaclib.gendoc.stamp.StampConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StampDocumentAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		// Obtener el id del documento a sellar
		String documentId = request.getParameter("documentId");
		if (StringUtils.isNotBlank(documentId)) {

			File stampedDoc = null;
			
			try {
		  	    IInvesflowAPI invesFlowAPI = session.getAPI();
				IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	
		  		// Obtener el documento a sellar
		  		IItem document = entitiesAPI.getEntity(SpacEntities.SPAC_DT_DOCUMENTOS, TypeConverter.parseInt(documentId));
	
				// Sellar el documento
				StampConnector stampConnector = StampConnectorFactory.getInstance().getStampConnector();
				stampedDoc = stampConnector.stampDocument(session.getClientContext(),
						document, getResources(request));
	
	            // Descargar el documento sellado
	            response.setContentType(MimetypeMapping.getMimeType(document.getString("EXTENSION")));
		    	response.setHeader("Pragma", "public");
		    	response.setHeader("Cache-Control", "max-age=0");
	            response.setHeader("Content-Transfer-Encoding", "binary");
	            response.setHeader("Content-Disposition", "attachment; filename=\"" 
	            		+ document.getString("NOMBRE") + "." 
	            		+ document.getString("EXTENSION") + "\"");
				response.setContentLength((int) stampedDoc.length());
				
				ServletOutputStream ouputStream = response.getOutputStream();
				FileUtils.copy(stampedDoc, ouputStream);
				ouputStream.flush();
				ouputStream.close();
				
			} catch (ISPACInfo e) {
				logger.error("Error al sellar el documento", e);
				e.setRefresh(false);
				throw e;
			} catch (Exception e) {
				logger.error("Error al sellar el documento", e);
				throw new ISPACInfo(e,false);
			} finally {
				if (stampedDoc != null) {
					stampedDoc.delete();
				}
			}
		}

		return null;
	}
   	
}