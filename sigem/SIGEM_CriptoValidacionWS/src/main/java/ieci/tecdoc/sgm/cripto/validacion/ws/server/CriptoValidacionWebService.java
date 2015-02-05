package ieci.tecdoc.sgm.cripto.validacion.ws.server;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class CriptoValidacionWebService {

	private static final Logger logger = Logger.getLogger(CriptoValidacionWebService.class);

	private static final String SERVICE_NAME = "SIGEM_ServicioValidacion.WEBSERVICE.IMPL";

	public Hash crearHashByEntidad(Contenido poBase64Document, String idEntidad)
	{
		MultiEntityContextHolder.setEntity(idEntidad);
		return crearHash(poBase64Document);
	}

	public Hash crearHash(Contenido poBase64Document){
		try {
			Hash oHash = new Hash();
			oHash.setHash(getServicioCriptoValidacion().createHash(poBase64Document.getContenidoB64()));
			return (Hash)ServiciosUtils.completeReturnOK((RetornoServicio) oHash);
		} catch (CriptoValidacionException e) {
			logger.error("Error creando Hash.", e);
			return (Hash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Hash()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error creando Hash.", e);
			return (Hash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Hash()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error creando Hash.", e);
			return (Hash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Hash()));
		}
	}

	public ResultadoValidacionHash validaHashByEntidad(Contenido poBase64Document, Contenido poB64Hash, String idEntidad)
	{
		MultiEntityContextHolder.setEntity(idEntidad);
		return validarHash(poBase64Document, poB64Hash);
	}

    public ResultadoValidacionHash validarHash(Contenido poBase64Document, Contenido poB64Hash){
    	try {
			ResultadoValidacionHash oResultado = getResultadoValidacionHashWS(
						getServicioCriptoValidacion().validateHash(poBase64Document.getContenidoB64(), poB64Hash.getContenidoB64())
						);
			return (ResultadoValidacionHash)ServiciosUtils.completeReturnOK((RetornoServicio) oResultado);
		} catch (CriptoValidacionException e) {
			logger.error("Error validando Hash.", e);
			return (ResultadoValidacionHash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionHash()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error validando Hash.", e);
			return (ResultadoValidacionHash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionHash()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error creando Hash.", e);
			return (ResultadoValidacionHash)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionHash()));
		}
    }

    public ResultadoValidacionCertificado validarCertificadoByEntidad(Contenido poB64Certificate,String idEntidad)
    {
    	MultiEntityContextHolder.setEntity(idEntidad);
    	return validarCertificado(poB64Certificate);
    }

    public ResultadoValidacionCertificado validarCertificado(Contenido poB64Certificate){

    	try {
			ResultadoValidacionCertificado oInfo = getInfoCertificadoWS(
							getServicioCriptoValidacion().validateCertificate(poB64Certificate.getContenidoB64())
							);
			return (ResultadoValidacionCertificado)ServiciosUtils.completeReturnOK((RetornoServicio) oInfo);
		} catch (CriptoValidacionException e) {
			logger.error("Error validando certificado.", e);
			return (ResultadoValidacionCertificado)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionCertificado()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error validando certificado.", e);
			return (ResultadoValidacionCertificado)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionCertificado()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error creando Hash.", e);
			return (ResultadoValidacionCertificado)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ResultadoValidacionCertificado()));
		}
    }


	private ServicioCriptoValidacion getServicioCriptoValidacion() throws SOAPException, SigemException{
	/*	String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioCriptoValidacion();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioCriptoValidacion(sbImpl.toString());
		}
		*/
	return LocalizadorServicios.getServicioCriptoValidacion(SERVICE_NAME);
	}

	private ResultadoValidacionHash getResultadoValidacionHashWS(boolean pbResultado){
		ResultadoValidacionHash oResultado = new ResultadoValidacionHash();
		if(pbResultado){
			oResultado.setResultadoValidacion(ConstantesServicios.LABEL_TRUE);
		}else{
			oResultado.setResultadoValidacion(ConstantesServicios.LABEL_FALSE);
		}
		return oResultado;
	}

	private ResultadoValidacionCertificado getInfoCertificadoWS(ResultadoValidacion poCertificado){
		if(poCertificado == null){
			return null;
		}
		ResultadoValidacionCertificado oResult = new ResultadoValidacionCertificado();
		oResult.setResultadoValidacion(poCertificado.getResultadoValidacion());
		oResult.setMensajeValidacion(poCertificado.getMensajeValidacion());
		if(poCertificado.getCertificado() != null){
			InfoCertificado oCertificado = new InfoCertificado();
			oCertificado.setNombre(poCertificado.getCertificado().getName());
			oCertificado.setNombreSinApellidos(poCertificado.getCertificado().getFirstname());
			oCertificado.setApellido1(poCertificado.getCertificado().getLastname1());
			oCertificado.setApellido2(poCertificado.getCertificado().getLastname2());
			oCertificado.setAsunto(poCertificado.getCertificado().getSubject());
			oCertificado.setCif(poCertificado.getCertificado().getCif());
			oCertificado.setEmisor(poCertificado.getCertificado().getIssuer());
			oCertificado.setNif(poCertificado.getCertificado().getNif());
			oCertificado.setNumeroSerie(poCertificado.getCertificado().getSerialNumber());
			oCertificado.setRazonSocial(poCertificado.getCertificado().getCorporateName());
			oResult.setCertificado(oCertificado);
		}

		return oResult;
	}
}
