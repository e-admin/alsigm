package ieci.tecdoc.sgm.certificacion.ws.client;

import java.rmi.RemoteException;

import sun.misc.BASE64Decoder;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.certificacion.ws.client.pago.Liquidacion;
import ieci.tecdoc.sgm.certificacion.ws.client.pago.Pago;
import ieci.tecdoc.sgm.certificacion.ws.client.pago.Tasa;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.certificacion.CertificacionException;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class CertificacionWSRemoteClient implements ServicioCertificacion{
	
	private CertificacionWebService service;
	
	
	public CertificacionWebService getService() {
		return service;
	}

	public void setService(CertificacionWebService service) {
		this.service = service;
	}
	
	public ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf generarCertificacionPagos (Entidad entidad,
		ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] pagos,
		ieci.tecdoc.sgm.core.services.certificacion.Usuario usuario)
		throws CertificacionException {
		
		try{
			RetornoPdf oRetorno =  getService().generarCertificacionPagos(
							getPagosWS(pagos),
							getUsuarioWS(usuario),
							getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoPdfServicio(oRetorno);
			}else{
				throw getCertificacionException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void altaCertificacion(String idUsuario, String idFichero, Entidad entidad)
		throws CertificacionException {
		try{
			RetornoServicio oRetorno =  getService().altaCertificacion(idUsuario, idFichero, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCertificacionException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void eliminarCertificacion(String idFichero, Entidad entidad) 
		throws CertificacionException {
		try{
			RetornoServicio oRetorno =  getService().eliminarCertificacion(idFichero, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCertificacionException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}	
	}
	
	public ieci.tecdoc.sgm.core.services.certificacion.Certificacion obtenerCertificacion(String idUsuario, String idFichero, Entidad entidad) 
		throws CertificacionException {
		try{
			Certificacion oRetorno =  getService().obtenerCertificacion(idUsuario, idFichero, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getCertificacionServicio(oRetorno);
			}else{
				throw getCertificacionException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	private CertificacionException getCertificacionException(IRetornoServicio oReturn){
		return new CertificacionException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private ieci.tecdoc.sgm.certificacion.ws.client.Entidad getEntidadWS(Entidad poEntidad) {
		ieci.tecdoc.sgm.certificacion.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.certificacion.ws.client.Entidad();
		if (poEntidad == null)
			return oEntidad;
		
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		
		return oEntidad;
	}
	
	private Pago getPagoWS(ieci.tecdoc.sgm.core.services.certificacion.pago.Pago poPago){
		Pago oPago = new Pago();
		if (poPago == null)
			return oPago;
		
		oPago.setAcreditacion(poPago.getAcreditacion());
		oPago.setCcc(poPago.getCcc());
		oPago.setCccDomiciliacion(poPago.getCccDomiciliacion());
		oPago.setDomiciliacion(poPago.getDomiciliacion());
		oPago.setEjercicio(poPago.getEjercicio());
		oPago.setEntidadBancaria(poPago.getEntidadBancaria());
		oPago.setEstado(poPago.getEstado());
		oPago.setExpediente(poPago.getExpediente());
		oPago.setFecha(poPago.getFecha());
		oPago.setFechaCaducidadTarjetaCredito(poPago.getFechaCaducidadTarjetaCredito());
		oPago.setFechaDevengo(poPago.getFechaDevengo());
		oPago.setHora(poPago.getHora());
		oPago.setIdEntidadEmisora(poPago.getIdEntidadEmisora());
		oPago.setIdioma(poPago.getIdioma());
		oPago.setIdTasa(poPago.getIdTasa());
		oPago.setImporte(poPago.getImporte());
		oPago.setInformacionEspecifica(poPago.getInformacionEspecifica());
		oPago.setLiquidacion(getLiquidacionWS(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setTasa(getTasaWS(poPago.getTasa()));
		
		return oPago;
	}
	
	private Pago[] getPagosWS(ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] poPagos){
		if (poPagos == null)
			return new Pago[0];
		
		Pago[] oPagos = new Pago[poPagos.length];
		for(int i=0; i<poPagos.length; i++)
			oPagos[i] = getPagoWS(poPagos[i]);
		
		return oPagos;
	}
	
	private Liquidacion getLiquidacionWS(ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion poLiquidacion){
		Liquidacion oLiquidacion = new Liquidacion();
		if (poLiquidacion == null)
			return oLiquidacion;
		
		oLiquidacion.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacion.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacion.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacion.setEstado(poLiquidacion.getEstado());
		oLiquidacion.setFechaPago(poLiquidacion.getFechaPago());
		oLiquidacion.setFinPeriodo(poLiquidacion.getFinPeriodo());
		oLiquidacion.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacion.setImporte(poLiquidacion.getImporte());
		oLiquidacion.setInicioPeriodo(poLiquidacion.getInicioPeriodo());
		oLiquidacion.setNif(poLiquidacion.getNif());
		oLiquidacion.setNombre(poLiquidacion.getNombre());
		oLiquidacion.setNrc(poLiquidacion.getNrc());
		oLiquidacion.setReferencia(poLiquidacion.getReferencia());
		oLiquidacion.setRemesa(poLiquidacion.getRemesa());
		oLiquidacion.setSolicitud(poLiquidacion.getSolicitud());
		oLiquidacion.setTasa(getTasaWS(poLiquidacion.getTasa()));
		oLiquidacion.setVencimiento(poLiquidacion.getVencimiento());
		
		return oLiquidacion;
	}
	
	private Tasa getTasaWS(ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa poTasa){
		Tasa oTasa = new Tasa();
		if (poTasa == null)
			return oTasa;
		
		oTasa.setCaptura(poTasa.getCaptura());
		oTasa.setCodigo(poTasa.getCodigo());
		oTasa.setDatosEspecificos(poTasa.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasa.getIdEntidadEmisora());
		oTasa.setModelo(poTasa.getModelo());
		oTasa.setNombre(poTasa.getNombre());
		oTasa.setTipo(poTasa.getTipo());
		
		return oTasa;
	}
	
	private Usuario getUsuarioWS(ieci.tecdoc.sgm.core.services.certificacion.Usuario poUsuario){
		Usuario oUsuario = new Usuario();
		if (poUsuario == null)
			return oUsuario;
		
		oUsuario.setApellidos(poUsuario.getApellidos());
		oUsuario.setCp(poUsuario.getCp());
		oUsuario.setDomicilio(poUsuario.getDomicilio());
		oUsuario.setEmail(poUsuario.getEmail());
		oUsuario.setLocalidad(poUsuario.getLocalidad());
		oUsuario.setNif(poUsuario.getNif());
		oUsuario.setNombre(poUsuario.getNombre());
		oUsuario.setProvincia(poUsuario.getProvincia());
		oUsuario.setTelefono(poUsuario.getTelefono());
		oUsuario.setIdioma(poUsuario.getIdioma());
		
		return oUsuario;
	}
	
	private byte[] getStringB64Servicio(StringB64 poStringB64){
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			return decoder.decodeBuffer(poStringB64.getStringB64());
		}catch(Exception e){
			return null;
		}
	}
	
	private ieci.tecdoc.sgm.core.services.certificacion.Certificacion getCertificacionServicio(Certificacion poCertificacion){
		ieci.tecdoc.sgm.core.services.certificacion.Certificacion oCertificacion = new ieci.tecdoc.sgm.core.services.certificacion.Certificacion();
		if (poCertificacion == null)
			return oCertificacion;
		
		oCertificacion.setIdFichero(poCertificacion.getIdFichero());
		oCertificacion.setIdUsuario(poCertificacion.getIdUsuario());
		
		return oCertificacion;
	}
	
	private ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf getRetornoPdfServicio(RetornoPdf poRetornoPdf){
		ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf oRetornoPdf = new ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf();
		if (poRetornoPdf == null)
			return oRetornoPdf;
		
		oRetornoPdf.setContenido(poRetornoPdf.getContenido());
		oRetornoPdf.setIdentificador(poRetornoPdf.getIdentificador());
		
		return oRetornoPdf;
	}
}
