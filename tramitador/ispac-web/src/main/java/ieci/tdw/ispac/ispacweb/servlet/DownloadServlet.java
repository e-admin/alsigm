package ieci.tdw.ispac.ispacweb.servlet;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownloadServlet extends ISPACBaseServlet {
  
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Logger de la clase. 
	 */
	protected static Logger logger = Logger.getLogger(DownloadServlet.class);

	
	/**
	 * Process a GET request for the specified resource.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet-specified error occurs
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			// Sesión de tramitación
	        SessionAPI session = getSession(request);
	
	        // Identificador del documento
	        int documentId = getDocumentId(request);
	        
	        // Descargar el documento
	        DocumentUtil.downloadDocument(response, session, documentId);
	        
		} catch (Exception e) {
			logger.error("Error al descargar el documento", e);
		}
	}
	
	protected int getDocumentId(HttpServletRequest request) {
		
		String aux = request.getPathInfo();
		
		int ix = aux.lastIndexOf("/");
		if (ix > 0) {
			aux = aux.substring(ix + 1);
		}
		
		ix = aux.lastIndexOf(".");
		if (ix > 0) {
			aux = aux.substring(0, ix);
		}
		
		return TypeConverter.parseInt(aux, 0);
	}
}
