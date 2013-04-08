package ieci.tecdoc.sgm.pe.ws.client;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.pe.Util;
import junit.framework.TestCase;

public class ServicioPagoTelematicoRemoteClientTest extends TestCase {

	private ieci.tecdoc.sgm.core.services.dto.Entidad entidad = null;
	private ServicioPagoTelematico oServicio=null;
	private static String numReferenciaLiquidacion="";
	private static final String idEntidadEmisora="000000";
	private static final String idTasa="200";
	private static final String nif="12345678Z";
	private static final String nombre="Usuario de Pruebas";
	private static final String datosEspecificos="Test de Prueba";
	
	protected void setUp() throws Exception {
		entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("000");
		entidad.setNombre("Entidad 1");
		
		oServicio = LocalizadorServicios.getServicioPagoTelematico("SIGEM_ServicioPagoTelematico.SIGEM.WEBSERVICE");
	}

	public void testBuscarTasas() {
		ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa criterio=new ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa();
		criterio.setTipo("AL3");
		try{  
			List tasas=oServicio.buscarTasas(criterio, entidad);
			assertNotNull(tasas);
			assertTrue(tasas.size()==1);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testObtenerDatosTasa() {
		try{
			ieci.tecdoc.sgm.core.services.pago.Tasa tasa=oServicio.obtenerDatosTasa(idTasa, idEntidadEmisora, entidad);
			assertNotNull(tasa);
			assertEquals(tasa.getTipo(),"AL3");
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testAltaLiquidacion() {
		ieci.tecdoc.sgm.core.services.pago.Liquidacion liquidacion=new ieci.tecdoc.sgm.core.services.pago.Liquidacion();
		liquidacion.setIdEntidadEmisora(idEntidadEmisora);
		liquidacion.setIdTasa(idTasa);
		liquidacion.setNif(nif);
		liquidacion.setImporte("100");
		liquidacion.setNombre(nombre);
		liquidacion.setDatosEspecificos(datosEspecificos);
		try{  
			liquidacion=oServicio.altaLiquidacion(liquidacion, entidad);
			assertNotNull(liquidacion);
			assertNotNull(liquidacion.getReferencia());
			assertNotNull(liquidacion.getEstado());
			numReferenciaLiquidacion=liquidacion.getReferencia();
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testBuscarLiquidaciones() {
		try {
			ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion criterio=new ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion();
			criterio.setReferencia(numReferenciaLiquidacion);
			List listaLiquidaciones=oServicio.buscarLiquidaciones(criterio, entidad);
			assertNotNull(listaLiquidaciones);
			assertEquals(listaLiquidaciones.size(),1);
			assertEquals(((ieci.tecdoc.sgm.core.services.pago.Liquidacion)listaLiquidaciones.get(0)).getReferencia(),numReferenciaLiquidacion);
			return;
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail();
	}
	
	public void testModificarLiquidacion() {
		try {
			ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion criterio=new ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion();
			criterio.setReferencia(numReferenciaLiquidacion);
			List listaLiquidaciones=oServicio.buscarLiquidaciones(criterio, entidad);
			
			ieci.tecdoc.sgm.core.services.pago.Liquidacion liquidacion=(ieci.tecdoc.sgm.core.services.pago.Liquidacion)listaLiquidaciones.get(0);
			String nuevoImporte="10";
			liquidacion.setImporte(nuevoImporte);
			oServicio.modificarLiquidacion(liquidacion, entidad);
			criterio=new ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion();
			criterio.setReferencia(numReferenciaLiquidacion);
			listaLiquidaciones=oServicio.buscarLiquidaciones(criterio, entidad);
			assertNotNull(listaLiquidaciones);
			assertEquals(listaLiquidaciones.size(),1);
			assertEquals(((ieci.tecdoc.sgm.core.services.pago.Liquidacion)listaLiquidaciones.get(0)).getImporte(),formatearImporte(nuevoImporte,12));
			return;
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail();
	}

	public void testBajaLiquidacion() {
		ieci.tecdoc.sgm.core.services.pago.Liquidacion liquidacion=new ieci.tecdoc.sgm.core.services.pago.Liquidacion();
		liquidacion.setIdEntidadEmisora(idEntidadEmisora);
		liquidacion.setIdTasa(idTasa);
		liquidacion.setNif(nif);
		liquidacion.setImporte("100");
		liquidacion.setNombre(nombre);
		liquidacion.setDatosEspecificos(datosEspecificos);
		try{  
			liquidacion=oServicio.altaLiquidacion(liquidacion, entidad);
			assertNotNull(liquidacion);
			assertNotNull(liquidacion.getReferencia());
			oServicio.bajaLiquidacion(liquidacion, entidad);
			ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion criterio=new ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion();
			criterio.setReferencia(liquidacion.getReferencia());
			List listaLiquidaciones=oServicio.buscarLiquidaciones(criterio, entidad);
			assertNotNull(listaLiquidaciones);
			assertEquals(listaLiquidaciones.size(),0);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testRealizarPago() {
		ieci.tecdoc.sgm.core.services.pago.Pago pago=new ieci.tecdoc.sgm.core.services.pago.Pago();
		pago.setAcreditacion("1");
		pago.setCcc("12360000400000000000");
		//pago.setCccDomiciliacion(cccDomiciliacion);
		//pago.setDomiciliacion(domiciliacion);
		pago.setEjercicio("2011");
		pago.setEntidadBancaria("1236");
		//pago.setExpediente("201119084710948012");
		//pago.setFecha(Util.getFechaCuaderno60(Calendar.getInstance().getTime()));
		//pago.setFechaCaducidadTarjetaCredito(Util.getFechaCuaderno60(Calendar.getInstance().getTime()));
		pago.setFechaDevengo(Util.getFechaCuaderno60(Calendar.getInstance().getTime()));
		//pago.setHora(Util.getHoraCuaderno60(Calendar.getInstance()).substring(0,4));
		pago.setIdEntidadEmisora(idEntidadEmisora);
		pago.setIdTasa(idTasa);
		pago.setImporte("10");
		pago.setInformacionEspecifica(datosEspecificos);
		pago.setMedioPago("1");
		pago.setNif(nif);
		//pago.setNumeroTarjetaCredito(numeroTarjetaCredito);
		//pago.setPeticionPagoPasarelaExternaConRedireccion(PeticionPagoPasarelaExternaConRedireccion);
		pago.setReferencia(numReferenciaLiquidacion);
		//pago.setRemesa(remesa);
		
		try{  
			pago=oServicio.realizarPago(pago, entidad);
			assertNotNull(pago);
			assertNotNull(pago.getNrc());
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testDetallePago() {
		try{  	
			ieci.tecdoc.sgm.core.services.pago.Pago pago=oServicio.detallePago(numReferenciaLiquidacion, entidad);
			assertNotNull(pago);
			assertNotNull(pago.getNrc());
			assertNotNull(pago.getLiquidacion().getReferencia(),numReferenciaLiquidacion);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}

	public void testObtenerDocumentoPago() {
		try{  	
			ieci.tecdoc.sgm.core.services.pago.Pago pago=oServicio.detallePago(numReferenciaLiquidacion, entidad);
			assertNotNull(pago);
			assertNotNull(pago.getLiquidacion());
			assertNotNull(pago.getLiquidacion().getReferencia());
			assertNotNull(pago.getIdTasa());
			
			String documento=oServicio.obtenerDocumentoPago(pago, entidad);
			assertNotNull(documento);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}

	private String formatearImporte(String pcImporte, int peTamano){
		if((pcImporte == null) || ("".equals(pcImporte))){
			return null;
		}
		if(peTamano <= 0){
			return null;
		}
		String cAux = removeChar(pcImporte, '.');
		if(cAux.length() > peTamano){
			return null;
		}else{
			int eCeros = peTamano - cAux.length();
			StringBuffer sbAux = new StringBuffer();
			for(int eContador = 0; eContador < eCeros; eContador++){
				sbAux.append("0");
			}
			sbAux.append(cAux);
			return sbAux.toString();
		}	
	}
	
	public static String removeChar(String str, char c){
	      byte[] data = str.getBytes();
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      for (int i = 0; i < data.length; i++){
	         if (data[i] != c)
	            out.write(data[i]);
	      }
	      return(out.toString());
	}
}
