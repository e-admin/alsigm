/**
 *
 */
package com.ieci.tecdoc.isicres.servlets.intercambio.registral;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;

/**
 *
 * Servlet que devuelve un documento asociado a un intercambio registral
 *
 * @author IECISA
 * @version $Revision$
 *
 */
public class DescargaDocumentoIntercambioRegistral extends HttpServlet {

	private static Logger logger = Logger.getLogger(DescargaDocumentoIntercambioRegistral.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);

	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - start");
		}

		response.setDateHeader("Expires", 0);

		String idAnexo = request.getParameter("idAnexo");
		String idIntercambio = request.getParameter("idIntercambio");

		IntercambioRegistralManager intercambioManager = IsicresManagerProvider.getInstance()
				.getIntercambioRegistralManager();

		AsientoRegistralVO asientoRegistralVO = null;
		try {
			asientoRegistralVO = intercambioManager
					.getIntercambioRegistralByIdIntercambio(idIntercambio);
			List<AnexoVO> anexos = (List<AnexoVO>) asientoRegistralVO.getAnexos();
			for (AnexoVO anexo : anexos) {
				if (anexo.getId().equalsIgnoreCase(idAnexo)) {
					response.addHeader("Content-Disposition",
							"attachment; filename=" + anexo.getNombreFichero());
					byte[] contenido = intercambioManager.getContenidoAnexo(idAnexo);

					ServletOutputStream out = response.getOutputStream();
					out.write(contenido);
					out.flush();
				}
			}
		} catch (Exception e) {
			logger.error("ERROR obtener el documento del asiento registral con el identificador" + idIntercambio,
					e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("doWork(HttpServletRequest, HttpServletResponse) - end");
		}
	}

}
