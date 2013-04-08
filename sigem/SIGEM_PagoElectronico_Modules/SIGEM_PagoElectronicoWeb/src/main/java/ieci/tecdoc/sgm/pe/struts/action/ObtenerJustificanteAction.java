package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: ObtenerJustificanteAction.java,v 1.4.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;
import ieci.tecdoc.sgm.pe.struts.cert.UserCertificateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Acción encargada de mostrar el justificante de pago al ciudadano. 
 */
public class ObtenerJustificanteAction extends Action {

	private static final Logger logger = Logger.getLogger(ObtenerJustificanteAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String JUSTIFICANTE_CONTENT_TYPE = "application/pdf";
	private static final int BUFFER_SIZE = 2048;
	
	
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		if(UserCertificateUtil.getUserData(request) == null){
			return mapping.findForward(ERROR_FORWARD);			
		}
		
		String cReferencia = (String)request.getParameter(ieci.tecdoc.sgm.pe.Constantes.REFERENCIA_KEY);
		
		File oFile = null;
        InputStream oInputStream = null;
        OutputStream oOutputStream = null;
        try{
    		Liquidacion oLiquidacion = PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, cReferencia);
    		if(!Liquidacion.ESTADO_PAGADO.equals(oLiquidacion.getEstado())){
    			return mapping.findForward(ERROR_FORWARD);
    		}
    		String cXML = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, cReferencia);
    		oFile = FormCreator.crearJustificantePago(oLiquidacion.getTasa(), cXML, request);
    		response.setContentType(JUSTIFICANTE_CONTENT_TYPE);
    		oInputStream = new FileInputStream(oFile);
    		oOutputStream = response.getOutputStream();

    		byte[] buffer = new byte[BUFFER_SIZE];
    		int count = 0;
    		int n = 0;
    		while (-1 != (n = oInputStream.read(buffer))) {
    			oOutputStream.write(buffer, 0, n);
    			count += n;
    		}
        }catch(Throwable e){
        	logger.error(e.getMessage(), e);
        	try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (IOException e1) {
				return null;
			}
        } finally {
        	if(oInputStream != null){
        		try {
					oInputStream.close();
				} catch (IOException e) {
	        		logger.error("Error cerrando input stream.");
				}
        	}
        	if(oFile != null){
        		if( (oFile.exists()) && (oFile.isFile()) && (!oFile.delete()) ){
            		StringBuffer sbError = new StringBuffer("Error borrando archivo temporal: ");
        		sbError.append(oFile.getAbsolutePath());
        		logger.error(sbError.toString());        		
        		}
        	}
        }
        return null;
	}
}