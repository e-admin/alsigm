package ieci.tecdoc.sgm.certificacion.ws.server.test;

import java.io.File;
import java.io.FileOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import ieci.tecdoc.sgm.certificacion.ws.server.CertificacionWebService;
import ieci.tecdoc.sgm.certificacion.ws.server.RetornoPdf;
import ieci.tecdoc.sgm.certificacion.ws.server.StringB64;
import ieci.tecdoc.sgm.certificacion.ws.server.Usuario;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Liquidacion;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Pago;
import ieci.tecdoc.sgm.certificacion.ws.server.pago.Tasa;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;


public class Demo1 {

	public static RetornoPdf obtenerCertificacion(String idioma) {
			
			/***************************** GENERACION XML ***************************************/
			Pago pago = new Pago();
			pago.setIdTasa("12"); 
			pago.setIdEntidadEmisora("000001");
			pago.setImporte("101.90");
			pago.setFecha("12-09-2007");
			pago.setHora("10:12:32");
			Pago pago1 = new Pago();
			pago1.setIdTasa("14"); 
			pago1.setIdEntidadEmisora("000002");
			pago1.setImporte("221.30");
			pago1.setFecha("14-09-2007");
			pago1.setHora("13:07:12");			
			Pago pago2 = new Pago();
			pago2.setIdTasa("22"); 
			pago2.setIdEntidadEmisora("000001");
			pago2.setImporte("17.85");
			pago2.setFecha("21-10-2007");
			pago2.setHora("17:10:07");
			Pago pago3 = new Pago();
			pago3.setIdTasa("34"); 
			pago3.setIdEntidadEmisora("000001");
			pago3.setImporte("21.15");
			pago3.setFecha("23-10-2007");
			pago3.setHora("13:46:23");
			
			Liquidacion liq1 = new Liquidacion();
			liq1.setInicioPeriodo("12-08-2007 10:00:00");
			liq1.setFinPeriodo("16-09-2007 14:00:00");
			Liquidacion liq2 = new Liquidacion();
			liq2.setInicioPeriodo("09-10-2007 8:00:00");
			liq2.setFinPeriodo("21-10-2007 15:00:00");
			Liquidacion liq3 = new Liquidacion();
			liq3.setInicioPeriodo("22-10-2007 10:00:00");
			liq3.setFinPeriodo("28-10-2007 14:00:00");
			Liquidacion liq4 = new Liquidacion();
			liq4.setInicioPeriodo("01-12-2007 10:00:00");
			liq4.setFinPeriodo("22-12-2007 14:00:00");
			pago.setLiquidacion(liq1);
			pago1.setLiquidacion(liq2);
			pago2.setLiquidacion(liq3);
			pago3.setLiquidacion(liq4);
			Pago[] pagos = new Pago[4];
			pagos[0] = pago;
			pagos[1] = pago1;
			pagos[2] = pago2;
			pagos[3] = pago3;
			Entidad entidad = new Entidad();
			entidad.setIdentificador("00002");
			entidad.setNombre("Ayuntamiento de Villanueva del Pardillo");
			
			Usuario usuario = new Usuario("Juan", "Romero López", "53243698P", "918372987", "", "Calle Bravo Murillo, 4, 4B", "Colmenar Viejo", "Madrid", "28134", idioma); 
			RetornoPdf retorno = null;
			StringB64 bytesB64 = null;
			
			try {
				ServicioCertificacion oServicio = LocalizadorServicios.getServicioCertificacion();
				retorno = getRetornoPdfWS(oServicio.generarCertificacionPagos(entidad, getPagosServicio(pagos), getUsuarioServicio(usuario)));
				return retorno;
			} catch(Exception e) {
				return new RetornoPdf();
			}
	}
	
	private static byte[] getStringB64Servicio(StringB64 poStringB64){
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			return decoder.decodeBuffer(poStringB64.getStringB64());
		}catch(Exception e){
			return null;
		}
	}

	
	private static ieci.tecdoc.sgm.core.services.certificacion.pago.Pago getPagoServicio(Pago poPago){
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
	
	private static ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] getPagosServicio(Pago[] poPagos){
		if (poPagos == null)
			return new ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[0];
		
		ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[] oPagos = new ieci.tecdoc.sgm.core.services.certificacion.pago.Pago[poPagos.length];
		for(int i=0; i<poPagos.length; i++)
			oPagos[i] = getPagoServicio(poPagos[i]);
		
		return oPagos;
	}
	
	private static ieci.tecdoc.sgm.core.services.certificacion.pago.Liquidacion getLiquidacionServicio(Liquidacion poLiquidacion){
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
	
	private static ieci.tecdoc.sgm.core.services.certificacion.pago.Tasa getTasaServicio(Tasa poTasa){
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
	
	private static ieci.tecdoc.sgm.core.services.certificacion.Usuario getUsuarioServicio(Usuario poUsuario){
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
	
	private static StringB64 getStringB64WS(byte[] poStr){
		if(poStr == null){
			return null;
		}
		StringB64 oStr = new StringB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oStr.setStringB64(encoder.encode(poStr));
		return oStr;
	}
	
	private static RetornoPdf getRetornoPdfWS(ieci.tecdoc.sgm.core.services.certificacion.RetornoPdf poRetornoPdf){
		if(poRetornoPdf== null){
			return null;
		}
		
		RetornoPdf oRetornoPdf = new RetornoPdf();
		
		oRetornoPdf.setContenido(poRetornoPdf.getContenido());
		oRetornoPdf.setIdentificador(poRetornoPdf.getIdentificador());
		
		return oRetornoPdf;
	}

}
