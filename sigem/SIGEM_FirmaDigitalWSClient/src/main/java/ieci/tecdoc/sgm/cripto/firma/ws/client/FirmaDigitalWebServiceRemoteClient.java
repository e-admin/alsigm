package ieci.tecdoc.sgm.cripto.firma.ws.client;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FirmaDigitalWebServiceRemoteClient implements ServicioFirmaDigital{

	private ServicioFirmaDigitalWebService service;
	
	public String firmar(byte[] pbaB64Source) throws FirmaDigitalException {
		try{
			Contenido oContenido = new Contenido();
			String idEntidad = MultiEntityContextHolder.getEntity();
			oContenido.setContenidoB64(Base64.encodeBytes(pbaB64Source));
			Firma oFirma = getService().firmarByEntidad(oContenido,idEntidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oFirma)){
				return oFirma.getFirmaB64();
			}else{
				throw getFirmaDigitalException((IRetornoServicio)oFirma);
			}
		} catch (RemoteException e) {
			throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma validarFirma(byte[] pabB64SourceSigned, byte [] signedContentB64 ) throws FirmaDigitalException {
		try{
			String idEntidad = MultiEntityContextHolder.getEntity();
			Contenido oSign = new Contenido();
			oSign.setContenidoB64(Base64.encodeBytes(pabB64SourceSigned));
			Contenido oSignedContent = new Contenido();
			oSignedContent.setContenidoB64(Base64.encodeBytes(signedContentB64));
			ResultadoValidacionFirma oResult = getService().validarFirmaByEntidad(oSign, oSignedContent,idEntidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oResult)){
				return getResultadoServicio(oResult);
			}else{
				throw getFirmaDigitalException((IRetornoServicio)oResult);
			}
		} catch (RemoteException e) {
			throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ServicioFirmaDigitalWebService getService() {
		return service;
	}

	public void setService(ServicioFirmaDigitalWebService service) {
		this.service = service;
	}

	private FirmaDigitalException getFirmaDigitalException(IRetornoServicio oReturn){
		return new FirmaDigitalException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma getResultadoServicio(ResultadoValidacionFirma poResult){
		if(poResult == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma oResult = new ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma();
		if(	(poResult.getResultadoValidacion() == null)
				||("".equals(poResult.getResultadoValidacion())
				||(ConstantesServicios.LABEL_FALSE.equals(poResult.getResultadoValidacion())))
		){
			oResult.setValidationResult(false);
		}else{
			oResult.setValidationResult(true);
		}
		oResult.setSigners(getFirmantesServicio(poResult.getFirmantes()));
		return oResult;
	}

	private List getFirmantesServicio(Firmante[] paFirmantes){
		List oLista = new ArrayList();
		if(paFirmantes != null){
			for(int i=0; i<paFirmantes.length; i++){
				oLista.add(getFirmanteServicio(paFirmantes[i]));
			}
		}
		return oLista;
	}
	
	private ieci.tecdoc.sgm.core.services.cripto.firma.Firmante getFirmanteServicio(Firmante poFirmante){
		if(poFirmante == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.cripto.firma.Firmante oFirmante = new ieci.tecdoc.sgm.core.services.cripto.firma.Firmante();
		oFirmante.setCertificate(getCertificadoServicio(poFirmante.getCertificate()));
		oFirmante.setName(poFirmante.getName());
		oFirmante.setNif(poFirmante.getName());
		return oFirmante;
	}
	
	private ieci.tecdoc.sgm.core.services.cripto.firma.Certificado getCertificadoServicio(Certificado poCertificado){
		if(poCertificado == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.cripto.firma.Certificado oCertificado = new ieci.tecdoc.sgm.core.services.cripto.firma.Certificado();
		oCertificado.setIssuer(poCertificado.getIssuer());
		oCertificado.setSerialNumber(poCertificado.getSerialNumber());
		oCertificado.setSubject(poCertificado.getSubject());
		return oCertificado;
	}

	public CertificadoX509Info getcertInfo() throws FirmaDigitalException {
		// TODO Auto-generated method stub
		return null;
	}

	public String registrarFirma(byte[] signatureValue,
			byte[] signCertificateValue, byte[] hash)
			throws FirmaDigitalException {
		try{
			String idEntidad = MultiEntityContextHolder.getEntity();
			Contenido oSignature = new Contenido();
			oSignature.setContenidoB64(Base64.encodeBytes(signatureValue));
			Contenido oCertificate = new Contenido();
			oCertificate.setContenidoB64(Base64.encodeBytes(signatureValue));
			Contenido oHash = new Contenido();
			oHash.setContenidoB64(Base64.encodeBytes(signatureValue));
			RegistroFirma oRegistroFirma= getService().registrarFirmaByEntidad(oSignature, oCertificate, oHash,idEntidad);
		
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRegistroFirma)){
				return oRegistroFirma.getIdRegistro();
			}else{
				throw getFirmaDigitalException((IRetornoServicio)oRegistroFirma);
			}
		} catch (RemoteException e) {
			throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
}
