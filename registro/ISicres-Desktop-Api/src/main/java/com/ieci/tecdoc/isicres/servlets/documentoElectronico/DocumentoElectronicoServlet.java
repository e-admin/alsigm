package com.ieci.tecdoc.isicres.servlets.documentoElectronico;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.IdentificadorDocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;

public class DocumentoElectronicoServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(DocumentoElectronicoServlet.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 8913948765379802495L;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req, resp);
	}

	protected void doWork(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {

		UseCaseConf useCaseConf = (UseCaseConf) req.getSession().getAttribute(Keys.J_USECASECONF);

		String idPagina=req.getParameter("idPagina");
		DocumentoElectronicoAnexoVO documentoElectronico=getDocumentoElectronicoAnexoVO(req,idPagina);
		if (documentoElectronico != null
				&& documentoElectronico.getDatosFirma() != null
				&& documentoElectronico.getDatosFirma()
						.getIdAttachmentFirmado() != null) {

			DocumentoElectronicoAnexoVO documentoFirmado = getDocumentoElectronicoAnexoManager()
					.getDocumentoFirmado(documentoElectronico.getId().getId());
			req.setAttribute("documentoFirmado", documentoFirmado);
		}
		req.setAttribute("documentoElectronico", documentoElectronico);

		getServletConfig().getServletContext().getRequestDispatcher("/infoDocElectronico.jsp").forward(req,resp);
		return;

	}

	/**
	 * Metodo que recibiendo un identificador de pagina referente al registro actual y libro actual obtiene los datos del documento electronico
	 * anexo, en caso de que se trate de un documento electronico, null en caso contrario.
	 * @param idPagina
	 * @return
	 */
	public DocumentoElectronicoAnexoVO getDocumentoElectronicoAnexoVO(HttpServletRequest req,String idPagina){
		DocumentoElectronicoAnexoVO result=null;
		result=getDocumentoElectronicoAnexoManager().retrieve(getIdentificadorDocumentoElectronicoAnexoVO(req,idPagina));
		return result;
	}

	/**
	 * Metodo que obtiene el manager de spring que contiene las operaciones de documento electronico
	 * @return
	 */
	protected DocumentoElectronicoAnexoManager getDocumentoElectronicoAnexoManager(){
		DocumentoElectronicoAnexoManager result=null;
		result=IsicresManagerProvider.getInstance().getDocumentoElectronicoAnexoManager();
		return result;
	}

	/**
	 * Metodo auxiliar encargado de obtener todos los datos necesarios que identificadn un documento electronico anexo
	 * @param idPagina
	 * @return
	 */
	protected IdentificadorDocumentoElectronicoAnexoVO getIdentificadorDocumentoElectronicoAnexoVO(HttpServletRequest req,String idPagina){
		IdentificadorDocumentoElectronicoAnexoVO result=null;

		CurrentUserSessionContextUtil currentUserSessionContextUtil= new CurrentUserSessionContextUtil();
		try {
			ContextoAplicacionVO contextoAplicacion = currentUserSessionContextUtil.getContextoAplicacionActual(req);
			String idLibro = contextoAplicacion.getLibroActual().getId();
			String idRegistro = contextoAplicacion.getRegistroActual().getIdRegistro();
			result= new IdentificadorDocumentoElectronicoAnexoVO();
			result.setIdLibro(Long.parseLong(idLibro));
			result.setIdRegistro(Long.parseLong(idRegistro));
			result.setIdPagina(Long.parseLong(idPagina));

		} catch (SessionException e) {
			logger.error(e.getMessage(),e);
		} catch (TecDocException e) {
			logger.error(e.getMessage(),e);
		}

		return result;

	}


}
