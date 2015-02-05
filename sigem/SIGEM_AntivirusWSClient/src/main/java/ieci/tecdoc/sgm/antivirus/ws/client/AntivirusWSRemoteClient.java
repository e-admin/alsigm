package ieci.tecdoc.sgm.antivirus.ws.client;

import java.rmi.RemoteException;

import sun.misc.BASE64Encoder;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class AntivirusWSRemoteClient implements ServicioAntivirus {

	private AntivirusWebService service;
	
	public AntivirusWebService getService() {
		return service;
	}

	public void setService(AntivirusWebService service) {
		this.service = service;
	}
	
	public boolean comprobarFichero(byte[] contenidoFichero, boolean borrar)
		throws AntivirusException {
			
			try{
				RetornoBooleano oRetorno =  getService().comprobarFicheroContenido(
						getRetornoArrayByteWS(contenidoFichero), borrar
					);
				if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
					return getRetornoBooleanoServicio(oRetorno);
				}else{
					throw getAntivirusException((IRetornoServicio)oRetorno);
				}
			}catch (RemoteException e) {
				throw new AntivirusException(AntivirusException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
			}
	}
	
	public boolean comprobarFichero(String rutaFichero, boolean borrar)
		throws AntivirusException {
		
		try{
			RetornoBooleano oRetorno =  getService().comprobarFicheroRuta(rutaFichero, borrar	);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoBooleanoServicio(oRetorno);
			}else{
				throw getAntivirusException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new AntivirusException(AntivirusException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	
	private AntivirusException getAntivirusException(IRetornoServicio oReturn){
		return new AntivirusException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private RetornoArrayByte getRetornoArrayByteWS(byte[] poStr){
		if(poStr == null){
			return null;
		}
		RetornoArrayByte oStr = new RetornoArrayByte();
		BASE64Encoder encoder = new BASE64Encoder();
		oStr.setContenido(encoder.encode(poStr));
		return oStr;
	}
	
	private boolean getRetornoBooleanoServicio(RetornoBooleano oRetorno) {
		return oRetorno.isValor();
	}

}
