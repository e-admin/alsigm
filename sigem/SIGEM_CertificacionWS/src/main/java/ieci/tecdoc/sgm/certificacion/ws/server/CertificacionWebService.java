package ieci.tecdoc.sgm.certificacion.ws.server;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Liquidacion;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Pago;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Tasa;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.certificacion.CertificacionException;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

public class CertificacionWebService {
	private static final Logger logger = Logger.getLogger(CertificacionWebService.class);
			
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_CERTIFICATION;
	
	private ServicioCertificacion getServicioCertificacion() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioCertificacion();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioCertificacion(sbImpl.toString());
		}
	}
	
	public RetornoPdf generarCertificacionPagos(Pago[] pagos, Usuario usuario, Entidad entidad) {
		try {
			RetornoPdf certificacion = getRetornoPdfWS(
					getServicioCertificacion().generarCertificacionPagos(
							entidad,
							getPagosServicio(pagos),
							getUsuarioServicio(usuario)));
			return (RetornoPdf)ServiciosUtils.completeReturnOK(certificacion);
		}  catch (CertificacionException e) {
			logger.error("Error al crear una certificacion de pagos.", e);
			return (RetornoPdf)
					ServiciosUtils.completeReturnError(
							(RetornoServicio)(new RetornoPdf()),
							e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al crear una certificacion de pagos.", e);
			return (RetornoPdf)
			ServiciosUtils.completeReturnError(
					(RetornoServicio)(new RetornoPdf()),
					e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al crear una certificacion de pagos.", e);
			return (RetornoPdf)
			ServiciosUtils.completeReturnError(
					(RetornoServicio)(new RetornoPdf()));		
		}
	}
	
	public RetornoServicio altaCertificacion(String idUsuario, String idFichero, Entidad entidad) {
		try {
			getServicioCertificacion().altaCertificacion(idUsuario, idFichero, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (CertificacionException e) {
			logger.error("Error al dar de alta certificacion.", e);			
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al dar de alta certificacion.", e);			
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al dar de alta certificacion.", e);			
			return ServiciosUtils.createReturnError();			
		}
	}
	
	public RetornoServicio eliminarCertificacion(String idFichero, Entidad entidad) {
		try {
			getServicioCertificacion().eliminarCertificacion(idFichero, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (CertificacionException e) {
			logger.error("Error al eliminar certificacion.", e);			
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar certificacion.", e);			
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar certificacion.", e);			
			return ServiciosUtils.createReturnError();			
		}
	}
	
	public Certificacion obtenerCertificacion(String idUsuario, String idFichero, Entidad entidad) {
		try {
			Certificacion oCertificacion = getCertificacionWS(
					getServicioCertificacion().obtenerCertificacion(idUsuario, idFichero, entidad));
			return (Certificacion)ServiciosUtils.completeReturnOK(oCertificacion);
		} catch (CertificacionException e) {
			logger.error("Error al obtener certificacion.", e);			
			return (Certificacion)
				ServiciosUtils.completeReturnError(
					(RetornoServicio)(new Certificacion()),
					e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener certificacion.", e);			
			return (Certificacion)
				ServiciosUtils.completeReturnError(
					(RetornoServicio)(new Certificacion()),
					e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener certificacion.", e);			
			return (Certificacion)
				ServiciosUtils.completeReturnError(
					(RetornoServicio)(new Certificacion()));
		}
	}

	
	private ieci.tecdoc.sgm.core.services.certificacion.pago.Pago getPagoServicio(Pago poPago){
		ieci.tecdoc.sgm.core.services.certificacion.pago.Pago oPago = new ieci.tecdoc.sgm.core.services.certificacion.pago.Pago();
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
		oPago.setLiquidacion(getLiquidacionServicio(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setTasa(getTasaServicio(poPago.getTasa()));
		
		return oPago;
	}
	
	private ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] getPagosServicio(Pago[] poPagos){
		if (poPagos == null)
			return new ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[0];
		
		ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] oPagos = new ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[poPagos.length];
		for(int i=0; i<poPagos.length; i++)
			oPagos[i] = getPagoServicio(poPagos[i]);
		
		return oPagos;
	}
	
	private ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion getLiquidacionServicio(Liquidacion poLiquidacion){
		ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion oLiquidacion = new ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion();
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
		oLiquidacion.setTasa(getTasaServicio(poLiquidacion.getTasa()));
		oLiquidacion.setVencimiento(poLiquidacion.getVencimiento());
		
		return oLiquidacion;
	}
	
	private ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa getTasaServicio(Tasa poTasa){
		ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa oTasa = new ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa();
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
	
	private ieci.tecdoc.sgm.core.services.certificacion.Usuario getUsuarioServicio(Usuario poUsuario){
		ieci.tecdoc.sgm.core.services.certificacion.Usuario oUsuario = new ieci.tecdoc.sgm.core.services.certificacion.Usuario();
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
	
	private StringB64 getStringB64WS(byte[] poStr){
		if(poStr == null){
			return null;
		}
		StringB64 oStr = new StringB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oStr.setStringB64(encoder.encode(poStr));
		return oStr;
	}
	
	private RetornoPdf getRetornoPdfWS(ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf poRetornoPdf){
		if(poRetornoPdf == null){
			return null;
		}
		
		RetornoPdf oRetornoPdf = new RetornoPdf();
		
		oRetornoPdf.setContenido(poRetornoPdf.getContenido());
		oRetornoPdf.setIdentificador(poRetornoPdf.getIdentificador());
		
		return oRetornoPdf;
	}
	
	private Certificacion getCertificacionWS(ieci.tecdoc.sgm.core.services.certificacion.Certificacion poCertificacion){
		Certificacion oCertificacion = new Certificacion();
		if(poCertificacion == null)
			return oCertificacion;

		oCertificacion.setIdFichero(poCertificacion.getIdFichero());
		oCertificacion.setIdUsuario(poCertificacion.getIdUsuario());
		
		return oCertificacion;
	}
}
