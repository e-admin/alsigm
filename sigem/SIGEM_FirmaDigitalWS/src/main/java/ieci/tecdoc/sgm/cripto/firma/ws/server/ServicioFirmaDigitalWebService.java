package ieci.tecdoc.sgm.cripto.firma.ws.server;



import java.util.List;

import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.cripto.firma.Firmante;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;




import javax.xml.soap.SOAPException;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.JCEBlockCipher.IDEA;


public class ServicioFirmaDigitalWebService {
	
	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(ServicioFirmaDigitalWebService.class);

	/** Nombre del servicio. */
//	private static final java.lang.String SERVICE_NAME = ConstantesServicios.SERVICE_DIGITAL_SIGN;
	
	
	private static final java.lang.String SERVICE_NAME="SIGEM_ServicioFirmaElectronica.WEBSERVICE.IMPL";
	/**
	 * 
	 * @return El servicio de la firma digital
	 * @throws SOAPException
	 * @throws SigemException
	 */
	private ServicioFirmaDigital getServicioFirmaDigital() throws SOAPException,
			SigemException {
	/*	
		java.lang.String cImpl = UtilAxis.getImplementacion(MessageContext
				.getCurrentContext());

		if ((cImpl == null) || ("".equals(cImpl))) {
			return LocalizadorServicios.getServicioFirmaDigital();
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".")
					.append(cImpl);
			return LocalizadorServicios.getServicioFirmaDigital(sbImpl.toString());
		}
		*/
		
		return LocalizadorServicios.getServicioFirmaDigital(SERVICE_NAME);
	}
	
	/**
	 * 
	 * @param signature Firma
	 * @param certificte Certificado
	 * @param hash Hash
	 * @return
	 */
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma registrarFirma(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido hash)
	{
		ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma registro =  new ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma(); 
			try {
				ServicioFirmaDigital servicio = getServicioFirmaDigital();
				registro.setIdRegistro(servicio.registrarFirma(Base64Util.decode(signature.getContenidoB64()), Base64Util.decode(certificate.getContenidoB64()), Base64Util.decode(hash.getContenidoB64())));
			} catch (SOAPException e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma) ServiciosUtils.completeReturnError(registro);
			} catch (Exception e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma ) ServiciosUtils.completeReturnError(registro);
			}
			
			return (ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma) ServiciosUtils.completeReturnOK(registro);
			
	}
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.RegistroFirma registrarFirmaByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido hash, String idEntidad)
	{
		MultiEntityContextHolder.setEntity(idEntidad);
		return registrarFirma(signature, certificate, hash);
			
	}
	
	/**
	 * Devuelve la firma
	 * @param poContenido Contenido a firma
	 * @return
	 */
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.Firma firmar(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido poContenido)
	{
		ieci.tecdoc.sgm.cripto.firma.ws.server.Firma firma =  new ieci.tecdoc.sgm.cripto.firma.ws.server.Firma(); 
			try {
				ServicioFirmaDigital servicio = getServicioFirmaDigital();
				firma.setFirmaB64(servicio.firmar(Base64Util.decode(poContenido.getContenidoB64())));
			} catch (SOAPException e) {
				logger.error("Error al realizar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.Firma) ServiciosUtils.completeReturnError(firma);
			} catch (Exception e) {
				logger.error("Error al realizar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.Firma ) ServiciosUtils.completeReturnError(firma);
			}
			
			return (ieci.tecdoc.sgm.cripto.firma.ws.server.Firma) ServiciosUtils.completeReturnOK(firma);
			
	}
	
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.Firma firmarByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido poContenido, String idEntidad)
	{
		MultiEntityContextHolder.setEntity(idEntidad);
		return firmar(poContenido);
		
	}
	
	/**
	 * Información con el resultado de la validación, si ok o si hubo error.
	 * @param signature Firma a validar
	 * @param contenido Contenido firmado
	 * @return
	 */
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma validarFirma(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature,ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido contenido)
	{
		ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma validacionFirma =  new ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma(); 
			try {
				ServicioFirmaDigital servicio = getServicioFirmaDigital();
				ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma resVal= servicio.validarFirma(Base64Util.decode(signature.getContenidoB64()), Base64Util.decode(contenido.getContenidoB64()));
				setValidacionFirma(validacionFirma, resVal);
			} catch (SOAPException e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma) ServiciosUtils.completeReturnError(validacionFirma);
			} catch (Exception e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma) ServiciosUtils.completeReturnError(validacionFirma);
			}
			
			return (ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma) ServiciosUtils.completeReturnOK(validacionFirma);
			
	}
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma validarFirmaByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature,ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido contenido, String idEntidad)
	{
		 
		MultiEntityContextHolder.setEntity(idEntidad);
		return validarFirma(signature, contenido);
			
	}
	
	/**
	 * Obtiene la informacion del certificado
	 * @param signature
	 * @return
	 */
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo getCertInfo(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature)
	{
		ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo certInfoRemote =  new ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo(); 
			try {
				ServicioFirmaDigital servicio = getServicioFirmaDigital();
				ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info  cert= servicio.getcertInfo();
				setCertInfo(certInfoRemote, cert);
			} catch (SOAPException e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo) ServiciosUtils.completeReturnError(certInfoRemote);
			} catch (Exception e) {
				logger.error("Error al registrar la firma.", e);
				return (ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo) ServiciosUtils.completeReturnError(certInfoRemote);
			}
			
			return (ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo) ServiciosUtils.completeReturnOK(certInfoRemote);
			
	}
	
	public ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo getCertInfoByEntidad(ieci.tecdoc.sgm.cripto.firma.ws.server.Contenido signature, String idEntidad)
	{
		MultiEntityContextHolder.setEntity(idEntidad);
		return getCertInfo(signature);
	}
	
	/**
	 * Modifica el objeto remoto con la información del objeto del core
	 * @param validacionFirma Resultado de la validacion de la firma
	 * @param resVal
	 */
	public void setValidacionFirma(ieci.tecdoc.sgm.cripto.firma.ws.server.ResultadoValidacionFirma validacionFirma,ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma resVal){
		
		validacionFirma.setResultadoValidacion(Boolean.toString(resVal.getValidationResult()));
	
		ieci.tecdoc.sgm.cripto.firma.ws.server.Firmante[] firmantes = new ieci.tecdoc.sgm.cripto.firma.ws.server.Firmante[resVal.getSigners().size()];
		List signer= resVal.getSigners();
		for(int i=0; i<signer.size(); i++){
			ieci.tecdoc.sgm.core.services.cripto.firma.Firmante firmante = (ieci.tecdoc.sgm.core.services.cripto.firma.Firmante) signer.get(i);
			ieci.tecdoc.sgm.cripto.firma.ws.server.Firmante firmanteRemoto= new ieci.tecdoc.sgm.cripto.firma.ws.server.Firmante();
			setFirmante(firmanteRemoto, firmante);
			firmantes[i]=firmanteRemoto;
	
		}
		
		validacionFirma.setFirmantes(firmantes);
		
	}
	
	
	
	/**
	 * Modifica el objeto remoto con la información del objeto del core
	 * @param firmanteRemoto
	 * @param firmanteCore
	 */
	public void setFirmante(ieci.tecdoc.sgm.cripto.firma.ws.server.Firmante firmanteRemoto,ieci.tecdoc.sgm.core.services.cripto.firma.Firmante firmanteCore){
		
		firmanteRemoto.setName(firmanteCore.getName());
		firmanteRemoto.setNif(firmanteCore.getNif());
		ieci.tecdoc.sgm.cripto.firma.ws.server.Certificado cert = new ieci.tecdoc.sgm.cripto.firma.ws.server.Certificado();
		setCertificado(cert, firmanteCore.getCertificate());
	}
		
	/**
	 * Modifica el objeto remoto con la información del objeto del core
	 * @param certRemoto
	 * @param certCore
	 */
	public void setCertificado(ieci.tecdoc.sgm.cripto.firma.ws.server.Certificado certRemoto,ieci.tecdoc.sgm.core.services.cripto.firma.Certificado  certCore){
		
		if(certCore!=null){
		certRemoto.setIssuer(certCore.getIssuer());
		certRemoto.setSerialNumber(certCore.getSerialNumber());
		certRemoto.setSubject(certCore.getSubject());
		}
	}
	
	/**
	 * Modifica el objeto remoto con la información del objeto del core
	 * @param certInfoRemoto
	 * @param certInfoCore
	 */
	
	public void setCertInfo(ieci.tecdoc.sgm.cripto.firma.ws.server.X509CertificadoInfo certInfoRemoto,ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info  certInfoCore){
		
		certInfoRemoto.setCert(certInfoCore.getCert());
		certInfoRemoto.setCerts(certInfoCore.getCerts());
		certInfoRemoto.setCertStore(certInfoCore.getCertStore());
		certInfoRemoto.setPrivKey(certInfoCore.getPrivateKey());
	}
	
	

}
