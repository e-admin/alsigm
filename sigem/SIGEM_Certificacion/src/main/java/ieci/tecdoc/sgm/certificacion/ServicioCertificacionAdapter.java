package ieci.tecdoc.sgm.certificacion;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.manager.CertificacionPagoManager;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.certificacion.Certificacion;
import ieci.tecdoc.sgm.core.services.certificacion.CertificacionException;
import ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.certificacion.Usuario;
import ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.certificacion.pago.Pago;
import ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

public class ServicioCertificacionAdapter implements ServicioCertificacion{
	private static final Logger logger = Logger.getLogger(ServicioCertificacionAdapter.class);
			
	public RetornoPdf generarCertificacionPagos(Entidad entidad, Pago[] pagos, Usuario usuario)
		throws CertificacionException {
		try {
			return getRetornoPdfServicio(CertificacionPagoManager.GenerarCertificacion(
					entidad,
					getPagosImpl(pagos),
					getUsuarioImpl(usuario)));
		} catch (ieci.tecdoc.sgm.certificacion.exception.CertificacionException e) {
			logger.error("Error al crear una certificacion de pagos.", e);
			throw getCertificacionException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al crear una certificacion de pagos.", e);
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public void altaCertificacion(String idUsuario, String idFichero, Entidad entidad)
		throws CertificacionException {
		try {
			CertificacionManager.AltaCertificacion(idUsuario, idFichero, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.certificacion.exception.CertificacionException e) {
			logger.error("Error al dar de alta una certificacion.", e);
			throw getCertificacionException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al dar de alta una certificacion.", e);
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public void eliminarCertificacion(String idFichero, Entidad entidad)
		throws CertificacionException {
		try {
			CertificacionManager.EliminarCertificacion(idFichero, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.certificacion.exception.CertificacionException e) {
			logger.error("Error al eliminar una certificacion.", e);
			throw getCertificacionException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar una certificacion.", e);
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Certificacion obtenerCertificacion(String idUsuario, String idFichero, Entidad entidad)
		throws CertificacionException {
		try {
			return getCertificacionServicio(CertificacionManager.ObtenerCertificacion(idUsuario, idFichero, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.certificacion.exception.CertificacionException e) {
			logger.error("Error al obtener una certificacion.", e);
			throw getCertificacionException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener una certificacion.", e);
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	private CertificacionException getCertificacionException(ieci.tecdoc.sgm.certificacion.exception.CertificacionException poException){
		if(poException == null){
			return new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_CERTIFICATION_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new CertificacionException(Long.valueOf(cCodigo.toString()).longValue(), poException);
	}
	
	
	private ieci.tecdoc.sgm.certificacion.bean.pago.Pago getPagoImpl(Pago poPago){
		ieci.tecdoc.sgm.certificacion.bean.pago.Pago oPago = new ieci.tecdoc.sgm.certificacion.bean.pago.Pago();
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
		oPago.setLiquidacion(getLiquidacionImpl(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setTasa(getTasaImpl(poPago.getTasa()));
		
		return oPago;
	}
	
	private ieci.tecdoc.sgm.certificacion.bean.pago.Pago[] getPagosImpl(Pago[] poPagos){
		if (poPagos == null)
			return new ieci.tecdoc.sgm.certificacion.bean.pago.Pago[0];
		
		ieci.tecdoc.sgm.certificacion.bean.pago.Pago[] oPagos = new ieci.tecdoc.sgm.certificacion.bean.pago.Pago[poPagos.length];
		for(int i=0; i<poPagos.length; i++)
			oPagos[i] = getPagoImpl(poPagos[i]);
		
		return oPagos;
	}
	
	private ieci.tecdoc.sgm.certificacion.bean.pago.Liquidacion getLiquidacionImpl(Liquidacion poLiquidacion){
		ieci.tecdoc.sgm.certificacion.bean.pago.Liquidacion oLiquidacion = new ieci.tecdoc.sgm.certificacion.bean.pago.Liquidacion();
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
		oLiquidacion.setTasa(getTasaImpl(poLiquidacion.getTasa()));
		oLiquidacion.setVencimiento(poLiquidacion.getVencimiento());
		
		return oLiquidacion;
	}
	
	private ieci.tecdoc.sgm.certificacion.bean.pago.Tasa getTasaImpl(Tasa poTasa){
		ieci.tecdoc.sgm.certificacion.bean.pago.Tasa oTasa = new ieci.tecdoc.sgm.certificacion.bean.pago.Tasa();
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
	
	private ieci.tecdoc.sgm.certificacion.bean.Usuario getUsuarioImpl(Usuario poUsuario){
		ieci.tecdoc.sgm.certificacion.bean.Usuario oUsuario = new ieci.tecdoc.sgm.certificacion.bean.Usuario();
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
	
	private Certificacion getCertificacionServicio(ieci.tecdoc.sgm.certificacion.datatype.CertificacionImpl poCertificacion) {
		Certificacion oCertificacion = new Certificacion();
		if (poCertificacion == null)
			return oCertificacion;
		
		oCertificacion.setIdFichero(poCertificacion.getIdFichero());
		oCertificacion.setIdUsuario(poCertificacion.getIdUsuario());
		
		return oCertificacion;
	}
	
	private RetornoPdf getRetornoPdfServicio(ieci.tecdoc.sgm.certificacion.pdf.RetornoPdf poRetornoPdf) {
		RetornoPdf oRetornoPdf = new RetornoPdf();
		if (poRetornoPdf == null)
			return oRetornoPdf;
		
		oRetornoPdf.setContenido(poRetornoPdf.getContenido());
		oRetornoPdf.setIdentificador(poRetornoPdf.getIdentificador());
		
		return oRetornoPdf;
	}
}
