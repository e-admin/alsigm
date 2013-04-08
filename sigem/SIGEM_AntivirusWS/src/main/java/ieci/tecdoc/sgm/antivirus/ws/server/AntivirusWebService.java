package ieci.tecdoc.sgm.antivirus.ws.server;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

public class AntivirusWebService {

	private static final Logger logger = Logger.getLogger(AntivirusWebService.class);
			
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_ANTIVIRUS;
	
	public RetornoBooleano comprobarFicheroContenido(RetornoArrayByte contenidoFichero, boolean borrar){
		try {
			RetornoBooleano retorno = getRetornoBooleanoWS(
					getAntivirusService().comprobarFichero(
							getRetornoArrayByteServicio(contenidoFichero), borrar
						)
					);
			return (RetornoBooleano)ServiciosUtils.completeReturnOK(retorno);
		} catch(AntivirusException e){
			logger.error("Error al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()),
				e.getErrorCode());
		} catch(SigemException e){
			logger.error("Error al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()),
				e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()));
		}
	}
	
	public RetornoBooleano comprobarFicheroRuta(String rutaFichero, boolean borrar){
		try {
			RetornoBooleano retorno = getRetornoBooleanoWS(getAntivirusService().comprobarFichero(rutaFichero, borrar));
			return (RetornoBooleano)ServiciosUtils.completeReturnOK(retorno);
		} catch(AntivirusException e){
			logger.error("Error al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()),
				e.getErrorCode());
		} catch(SigemException e){
			logger.error("Error al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()),
				e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al comprobar con antivirus un fichero", e);
			return (RetornoBooleano)
			ServiciosUtils.completeReturnError(
				(RetornoServicio)(new RetornoBooleano()));
		}
	}
	
	
	private ServicioAntivirus getAntivirusService() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioAntivirus(DefaultConfiguration.getConfiguration());
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioAntivirus(DefaultConfiguration.getConfiguration(), sbImpl.toString());
		}
	}
	
	private RetornoBooleano getRetornoBooleanoWS(boolean oBooleano) {
		RetornoBooleano poBooleano = new RetornoBooleano();
		poBooleano.setValor(oBooleano);
		return poBooleano;
	}
	
	private byte[] getRetornoArrayByteServicio(RetornoArrayByte oArray){
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			return decoder.decodeBuffer(oArray.getContenido());
		}catch(Exception e){
			return null;
		}
	}
}
