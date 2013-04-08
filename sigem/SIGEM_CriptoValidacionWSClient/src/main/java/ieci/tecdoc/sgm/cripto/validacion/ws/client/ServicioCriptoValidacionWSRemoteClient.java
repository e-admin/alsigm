package ieci.tecdoc.sgm.cripto.validacion.ws.client;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

import java.rmi.RemoteException;

public class ServicioCriptoValidacionWSRemoteClient implements
		ServicioCriptoValidacion {

	private CriptoValidacionWebService_PortType service;

	public CriptoValidacionWebService_PortType getService() {
		return service;
	}

	public void setService(CriptoValidacionWebService_PortType service) {
		this.service = service;
	}

	public String createHash(String psBase64Document)
			throws CriptoValidacionException {
		try{
			String idEntidad = MultiEntityContextHolder.getEntity();
			Hash oHash = getService().crearHashByEntidad(getContenidoWS(psBase64Document),idEntidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oHash)){
				return getHashServicio(oHash);
			}else{
				throw getCriptoValidacionException((IRetornoServicio)oHash);
			}
		} catch (RemoteException e) {
			throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ResultadoValidacion validateCertificate(String psB64Certificate)
			throws CriptoValidacionException {
		try{
			String idEntidad = MultiEntityContextHolder.getEntity();
			ResultadoValidacionCertificado oResult = getService().validarCertificadoByEntidad(getContenidoWS(psB64Certificate),idEntidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oResult)){
				return getResultadoValidacionServicio(oResult);
			}else{
				throw getCriptoValidacionException((IRetornoServicio)oResult);
			}
		} catch (RemoteException e) {
			throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public boolean validateHash(String psBase64Document, String psB64Hash)
			throws CriptoValidacionException {
		try{
			String idEntidad = MultiEntityContextHolder.getEntity();
			ResultadoValidacionHash oResult = getService().validaHashByEntidad(getContenidoWS(psBase64Document), getContenidoWS(psB64Hash),idEntidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oResult)){
				return getResultadoValidacionHashServicio(oResult);
			}else{
				throw getCriptoValidacionException((IRetornoServicio)oResult);
			}
		} catch (RemoteException e) {
			throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	private Contenido getContenidoWS(String psContenido){
		Contenido oContenido = new Contenido();
		oContenido.setContenidoB64(psContenido);
		return oContenido;
	}

	private String getHashServicio(Hash poHash){
		if(poHash == null){
			return null;
		}
		return poHash.getHash();
	}

	private CriptoValidacionException getCriptoValidacionException(IRetornoServicio oReturn){
		return new CriptoValidacionException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private ResultadoValidacion getResultadoValidacionServicio(ResultadoValidacionCertificado poResult){
		if(poResult == null){
			return null;
		}
		ResultadoValidacion oResult = new ResultadoValidacion();
		oResult.setResultadoValidacion(poResult.getResultadoValidacion());
		oResult.setMensajeValidacion(poResult.getMensajeValidacion());
		if(poResult.getCertificado() != null){
			oResult.setCertificado(getInfoCertificadoServicio(poResult.getCertificado()));
		}
		return oResult;
	}

	private ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado getInfoCertificadoServicio(InfoCertificado poInfo){
		if(poInfo == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado oInfo = new ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado();
		oInfo.setCif(poInfo.getCif());
		oInfo.setCorporateName(poInfo.getRazonSocial());
		oInfo.setIssuer(poInfo.getEmisor());
		oInfo.setFirstname(poInfo.getNombreSinApellidos());
		oInfo.setLastname1(poInfo.getApellido1());
		oInfo.setLastname2(poInfo.getApellido2());
		oInfo.setName(poInfo.getNombre());
		oInfo.setNif(poInfo.getNif());
		oInfo.setSerialNumber(poInfo.getNumeroSerie());
		oInfo.setSubject(poInfo.getAsunto());
		return oInfo;
	}

	private boolean getResultadoValidacionHashServicio(ResultadoValidacionHash poResult){
		if( (poResult == null)
			||(poResult.getResultadoValidacion() == null)
			||(!ConstantesServicios.LABEL_TRUE.equals(poResult.getResultadoValidacion()))
		){
			return false;
		}else{
			return true;
		}
	}
}
