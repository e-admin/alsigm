package com.ieci.tecdoc.isicres.servlets.compulsa;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;

import es.ieci.tecdoc.isicres.api.IsicresSpringAppContext;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.compulsa.business.manager.CompulsaManager;
import es.ieci.tecdoc.isicres.api.compulsa.business.vo.ConfiguracionCompulsaVO;
import es.ieci.tecdoc.isicres.api.compulsa.business.vo.DatosEspecificosCompulsaDefaultVO;
import es.ieci.tecdoc.isicres.api.compulsa.business.vo.DocumentoCompulsarVO;
import es.ieci.tecdoc.isicres.api.compulsa.business.vo.DocumentoFisicoCompulsaVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;


public class CompulsaServlet  extends HttpServlet{
	
	private static Logger logger = Logger.getLogger(CompulsaServlet.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 998057626409585119L;
	
	private static Logger _logger = Logger.getLogger(CompulsaServlet.class);
	
	protected static final String ID_REGISTRO_KEY="idRegistro";
	protected static final String ID_LIBRO_KEY="idLibro";
	protected static final String ID_SESSION_KEY="sessionPId";
	protected static final String FIRMA_KEY="firma";
	protected static final String CERTIFICATE_KEY="certificate";
	protected static final String USER_KEY="user";
	protected static final String FILE_SCAN_HASH_KEY="fileScanHash";
	protected static final String FILE_FIRMA_HASH_KEY="fileFirmaHash";
	
	protected static final String FILE_SCAN_KEY="fileScan";
	
	protected static final String FILE_FIRMA_KEY="fileFirma";
	
	
	
	
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req,resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doWork(req,resp);
	}
	
	/**
	 * Los parametros que espera tratar en la request son:
	 * 
	 * 	idRegistro ( deprecado, lo obtiene del contexto del usuario)
		idLibro ( deprecado, lo obtiene del contexto del usuario)
		sessionPId (deprecado, lo obtiene del contexto del usuario)
		
		firma
		certificate
		user
		fileScanHash
		fileScan
		fileFirmaHash
		fileFirma
	 * 
	 * 
	 * 
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doWork(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		
		
		ContextoAplicacionVO contextoAplicacion=null;

		
		try {
			CurrentUserSessionContextUtil currentUserSessionContextUtil= new CurrentUserSessionContextUtil();
			contextoAplicacion=currentUserSessionContextUtil.getContextoAplicacionActual(req);
		
			CompulsaManager compulsaManager=(CompulsaManager) IsicresSpringAppContext.getApplicationContext().getBean("isicresCompulsaManager");

			//seteamos lso valores de DocumentoCompulsarVO 
			DocumentoCompulsarVO documentoCompulsar= new DocumentoCompulsarVO();
			populateDocumentoCompulsarVO(req, documentoCompulsar);		
			
			//seteamos lso valores de ConfiguracionCompulsaVO
			ConfiguracionCompulsaVO configuracion=null;
			populateConfiguracionCompulsaVO(req, configuracion);
			
			//finalemente llamamos a la logica de compulsar
			compulsaManager.compulsar(documentoCompulsar, configuracion);
		}catch (Exception e){
			logger.fatal("Error compulsando ficheros", e);
			ResponseUtils.generateJavaScriptCompulsa(resp.getWriter(), RBUtil
					.getInstance(contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_COMPUL_UPLOAD_OBJ));
		}
		
	}
	
	protected void populateConfiguracionCompulsaVO(HttpServletRequest req,ConfiguracionCompulsaVO configuracion){
		return ;
	}
	
	protected void populateDocumentoCompulsarVO(HttpServletRequest req,DocumentoCompulsarVO documentoCompulsar) throws SessionException, TecDocException, IOException{
		
		Map<String, FileItem> params= parseMultiPart(req);
		
		ContextoAplicacionVO contextoAplicacion=null;
		
		CurrentUserSessionContextUtil currentUserSessionContextUtil= new CurrentUserSessionContextUtil();
		contextoAplicacion=currentUserSessionContextUtil.getContextoAplicacionActual(req);
		
		//TODO enganchar con compulsa del nuevo api
//		String idRegistro=params.get(ID_REGISTRO_KEY).getString();
//		String idLibro=params.get(ID_LIBRO_KEY).getString();
//		String sessionPId=params.get(ID_SESSION_KEY).getString();
		
		String idLibro=contextoAplicacion.getLibroActual().getId();
		String idRegistro=contextoAplicacion.getRegistroActual().getIdRegistro();
		String sessionPId=contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getSessionID();
		
		String firma=params.get(FIRMA_KEY).getString();
		String certificate=params.get(CERTIFICATE_KEY).getString();
		String user=params.get(USER_KEY).getString();
		String fileScanHash=params.get(FILE_SCAN_HASH_KEY).getString();
		String fileFirmaHash=params.get(FILE_FIRMA_HASH_KEY).getString();
		
		FileItem fileScanFileItem=params.get(FILE_SCAN_KEY);
		InputStream fileScan = fileScanFileItem.getInputStream();
		String fileScanName=fileScanFileItem.getName();
		
		
		FileItem fileFirmaFileItem=params.get(FILE_FIRMA_KEY);
		InputStream fileFirma = fileFirmaFileItem.getInputStream();
		String fileFirmaName=fileFirmaFileItem.getName();
		
		DocumentoFisicoCompulsaVO documentoOriginal= new DocumentoFisicoCompulsaVO();
		documentoOriginal.setContent(fileScanFileItem.get());
		documentoOriginal.setExtension(FilenameUtils.getExtension(fileScanName));
		documentoOriginal.setName(fileScanName);
		//TODO setear hashAlg
		documentoOriginal.setHashAlg("01");
		documentoOriginal.setHash(fileScanHash);
		
		documentoCompulsar.setDocumentoOriginal(documentoOriginal);
		
		DocumentoFisicoCompulsaVO documentoFirma= new DocumentoFisicoCompulsaVO();
		documentoFirma.setContent(fileFirmaFileItem.get());
		documentoFirma.setExtension(FilenameUtils.getExtension(fileFirmaName));
		documentoFirma.setName(fileFirmaName);
		//TODO setear algoritmo hash
		documentoFirma.setHashAlg("02");
		documentoFirma.setHash(fileFirmaHash);
		documentoCompulsar.setFirma(documentoFirma);
		
		IdentificadorRegistroVO identificadorRegistro=contextoAplicacion.getRegistroActual().getId();
		documentoCompulsar.setIdentificadorRegistro(identificadorRegistro);
		
		
		DatosEspecificosCompulsaDefaultVO datosEspecificos= new DatosEspecificosCompulsaDefaultVO();
		//TODO setear algoritmo firma
		datosEspecificos.setAlgoritmoFirma("03");
		datosEspecificos.setCertificado(certificate);
		datosEspecificos.setFirma(firma);
		//TODO setear formato de firma
		datosEspecificos.setFormatoFirma("04");
		
		datosEspecificos.setUsuario(user);
		documentoCompulsar.setDatosEspecificos(datosEspecificos);
		
	}
	
	
	protected Map<String, FileItem> parseMultiPart(HttpServletRequest request) {
		Map result = new HashMap<String, FileItem>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List items = upload.parseRequest(request);
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					String fieldName = item.getFieldName();
					result.put(fieldName, item);
				}
			} catch (FileUploadException e) {
				log("Error encountered while parsing the request",e);
			} catch (Exception e) {
				log("Error encountered while parsing the request",e);
			}
		}

		return result;
	}
	
	

}
