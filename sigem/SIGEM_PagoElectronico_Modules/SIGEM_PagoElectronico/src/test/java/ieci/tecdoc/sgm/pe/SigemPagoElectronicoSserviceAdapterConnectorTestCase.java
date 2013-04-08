package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SigemPagoElectronicoSserviceAdapterConnectorTestCase extends SigemPagoElectronicoServiceAdapterBaseTestCase {
	private static final String EMISOR_REDES="000000";
	
	private LiquidacionImpl getLiquidacionComun(LiquidacionImpl liquidacion){
		liquidacion.setDatosEspecificos("Datos Especificos de la peticion");
		liquidacion.setImporte("000000003489");
		liquidacion.setNif("05261042E");
		liquidacion.setNombre("Prueba Pago");
		liquidacion.setNrc("");
		liquidacion.setRemesa("01");
		liquidacion.setVencimiento("20370101");
		liquidacion.setEstado("00");
		return liquidacion;
	}
	
	private LiquidacionImpl getLiquidacionC60M12(LiquidacionImpl liquidacion,String codTributo, String emisor){
		try{
			liquidacion=getLiquidacionComun(liquidacion);
			liquidacion.setDatosEspecificos("Datos Especificos de la peticion");
			liquidacion.setEjercicio("2010");
			liquidacion.setFinPeriodo(new SimpleDateFormat("yyyyMMdd").parse("20370101"));
			liquidacion.setInicioPeriodo(new SimpleDateFormat("yyyyMMdd").parse("20010401"));
			liquidacion.setDiscriminante("1");
			liquidacion.setIdEntidadEmisora(emisor);
			liquidacion.setIdTasa(codTributo);
		}catch(Exception e){
			fail("Error en el relleno de la liquidacion C60M12: "+e);
		}
		return liquidacion;
	}
	
	private LiquidacionImpl getLiquidacionC60M3(LiquidacionImpl liquidacion){
		final String TASA_C60M3_REDES="200";
		
		liquidacion=getLiquidacionComun(liquidacion);
		liquidacion.setDatosEspecificos("Datos Especificos de la peticion");
		liquidacion.setIdEntidadEmisora(EMISOR_REDES);
		liquidacion.setIdTasa(TASA_C60M3_REDES);
		
		return liquidacion;
	}
	
	private LiquidacionImpl getLiquidacionC57(LiquidacionImpl liquidacion){
		final String TASA_C57_REDES="100";
		final String EMISOR_EUSKADI_C57="00100000";
		
		liquidacion=getLiquidacionComun(liquidacion);
		liquidacion.setDatosEspecificos("Datos Especificos de la peticion");
		liquidacion.setIdEntidadEmisora(EMISOR_EUSKADI_C57);
		liquidacion.setIdTasa(TASA_C57_REDES);
		
		return liquidacion;
	}
	
	private PagoImpl rellenarDatosPagoRedes(PagoImpl datosPago,boolean pruebaPagoTarjeta){
		datosPago.setCcc("12360000400000000000");
		datosPago.setCccDomiciliacion("12360000400000000000");
		datosPago.setEntidadBancaria("1236");
		datosPago.setDomiciliacion("2");
		if(!pruebaPagoTarjeta){
			//datosPago.setAcreditacion("0");
			
			//datosPago.setCodDomiciliacion("2");
			//datosPago.setDomiciliacion("2");
			//datosPago.setExpediente(ccc);
			datosPago.setMedioPago("1");
			//datosPago.setNumeroTarjetaCredito(ccc);
		}else{
			//datosPago.setAcreditacion("0");
			//datosPago.setCodDomiciliacion("2");
			datosPago.setFechaCaducidadTarjetaCredito("1220");
			datosPago.setNumeroTarjetaCredito("6011000000000004");
			//datosPago.setExpediente(ccc);
			datosPago.setMedioPago("2");
			//datosPago.setNumeroTarjetaCredito(ccc);
		}
		return datosPago;
	}
	
	
	private void pagoRedesPorCuentaBancaria(LiquidacionImpl liquidacion,String ficheropago){
		pagoRedes(liquidacion,ficheropago,false);
	}
	
	private void pagoRedesPorTarjeta(LiquidacionImpl liquidacion,String ficheropago){
		pagoRedes(liquidacion,ficheropago,true);
	}
	
	private void pagoRedes(LiquidacionImpl liquidacion,String ficheropago,boolean pruebaPagoTarjeta){
		PagoImpl pago=null;
		try{ pago=realizarPago(liquidacion,ficheropago,pruebaPagoTarjeta); }
		catch(Exception e){ 
			fail("Este caso no deberia ocurrir:"+e);
			//throw new RuntimeException(e);
		}
		checkPagoRedes(pago);
	}
	
	private void checkPagoRedes(PagoImpl pago){
		assertNotNull(pago.getNrc());
		assertEquals(Liquidacion.ESTADO_PAGADO,pago.getEstado());
	}
	
	private PagoImpl realizarPago(LiquidacionImpl liquidacion,String ficheropago) throws PagoElectronicoExcepcion{
		return realizarPago(liquidacion,ficheropago,false);
	}
	
	private PagoImpl realizarPago(LiquidacionImpl liquidacion,String ficheropago,boolean pruebaPagoTarjeta) throws PagoElectronicoExcepcion{
		try{ 
			liquidacion=manager.altaLiquidacion(liquidacion, ENTIDAD);
			PagoImpl pago=null;
			ApplicationContext original=ConfiguracionComun.getConfiguracion().getApplicationContext();
			ApplicationContext contenedorConector = new ClassPathXmlApplicationContext(ficheropago);
			ConfiguracionComun.getConfiguracion().setApplicationContext(contenedorConector);
			pago=manager.detallePago(liquidacion.getReferencia(), ENTIDAD);
			if(liquidacion.getTasa().getTipo().equals(Cuaderno60Modalidad3.MODALIDAD_3) || pago==null){	//C60 M3
				//liquidacion.setTasa(manager.obtenerDatosTasa(liquidacion.getIdTasa(), liquidacion.getIdEntidadEmisora(), ENTIDAD));
				pago = new PagoImpl();
				pago.setEjercicio(liquidacion.getEjercicio());	
				//pago.setNumeroTarjetaCredito((String)poFormPago.get(CAMPO_NUM_TARJETA_CREDITO));
				pago.setFechaCaducidadTarjetaCredito(liquidacion.getVencimiento());
				pago.setFechaDevengo(Util.getFechaCuaderno60(
						liquidacion.getFechaPago()!=null?liquidacion.getFechaPago():Calendar.getInstance().getTime()));
				pago.setInformacionEspecifica(liquidacion.getDatosEspecificos());
				pago.setImporte(liquidacion.getImporte());
				pago.setAcreditacion(Pago.ACREDITACION_NO_TERCERO_AUTORIZADO);
				pago.setLiquidacion(liquidacion);
			}
			pago.setReferencia(liquidacion.getReferencia());
			pago.setIdTasa(pago.getLiquidacion().getIdTasa());
			pago.setIdEntidadEmisora(pago.getLiquidacion().getIdEntidadEmisora());
			pago.setNif(pago.getLiquidacion().getNif());
			pago=rellenarDatosPagoRedes(pago,pruebaPagoTarjeta);
			pago=manager.realizarPago(pago, ENTIDAD);
			ConfiguracionComun.getConfiguracion().setApplicationContext(original);
			return pago;
		}catch(Exception e){
			if(e instanceof PagoElectronicoExcepcion){
				if(liquidacion.getTasa().getTipo().equals("C57") 
					|| liquidacion.getTasa().getTipo().equals("AL3")){
					throw (PagoElectronicoExcepcion)e;
				}
			}
			fail("Error al realizar el pago de la liquidacion: "+e);		
		}finally{
			manager.bajaLiquidacion(liquidacion, ENTIDAD);	
		}
		return null;
	}
	
	public boolean checkPagoElectronicoExceptionCause(PagoElectronicoExcepcion e,long codError){
		PagoElectronicoExcepcion cause=e;
		do{
			if(cause.getErrorCode()==codError) return true;
			if(cause.getCause()!=null && cause.getCause() instanceof PagoElectronicoExcepcion){
				cause=(PagoElectronicoExcepcion)cause.getCause();
			}else cause=null;
		}while(cause!=null);
		return false;
	}
	
	private void pagoEuskadi(LiquidacionImpl liquidacion,String ficheropago){
		try{ realizarPago(liquidacion,"PagoEuskadi_Config.xml"); }
		catch(Exception e){ fail("Error en envio peticion pago a Euskadi.net: "+e); }
	}
	
	public boolean isInternetConnection() {
		final int HTTP_OK=200;
		
		final String host = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_HOST);
		final String port = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_PORT);
		final String user = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_USER);
		final String passwd = ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_PROXY_PASS);
		
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxyPort", port);
		System.setProperty("http.proxyUser", user);
		System.setProperty("http.proxyPassword", passwd);
																									
		Authenticator.setDefault(new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {  
		        return new PasswordAuthentication(user, passwd.toCharArray());  
		    }
		});
		
		try{
			URL currentUrl = new URL("http://www.google.com");
			HttpURLConnection connection = (HttpURLConnection) currentUrl.openConnection();
			//connection.connect();
			int responseCode=connection.getResponseCode();
			if(responseCode==HTTP_OK)	return true;
			//System.out.println("Respuesta HTTP: "+responseCode);
		}catch(Exception e){
			//e.printStackTrace();
			Logger.getLogger(this.getClass()).debug("no hay conexion a internet: Configurar en PagoElectronico.properties. "+e);
		}
		return false;
	}
	
	public void testRealizarPagoRedesC60M1PorCuentaBancaria() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M1_REDES="100";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M1_REDES, EMISOR_REDES);
		pagoRedesPorCuentaBancaria(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC60M2PorCuentaBancaria() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M2_REDES="000";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M2_REDES, EMISOR_REDES);
		pagoRedesPorCuentaBancaria(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC60M3PorCuentaBancaria() {
		if(!isInternetConnection()) return;
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M3(liquidacion);
		pagoRedesPorCuentaBancaria(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC60M1PorTarjeta() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M1_REDES="100";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M1_REDES, EMISOR_REDES);
		pagoRedesPorTarjeta(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC60M2PorTarjeta() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M2_REDES="000";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M2_REDES, EMISOR_REDES);
		pagoRedesPorTarjeta(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC60M3PorTarjeta() {
		if(!isInternetConnection()) return;
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M3(liquidacion);
		pagoRedesPorTarjeta(liquidacion,"PagoRedes_Config.xml");
	}
	
	public void testRealizarPagoRedesC57() {
		//esperada excepcion 
		if(!isInternetConnection()) return;
		try{
			LiquidacionImpl liquidacion=new LiquidacionImpl();
			liquidacion=getLiquidacionC57(liquidacion);
			realizarPago(liquidacion,"PagoRedes_Config.xml");
		}catch(PagoElectronicoExcepcion e){
			assertTrue(checkPagoElectronicoExceptionCause(e,PagoElectronicoExcepcion.EC_PAGO_C57_NOT_SUPPORTED));
			return;
		}catch(Exception e){
			fail();
		}
		//fail();
		assertTrue(true);
	}
	
	public void testRealizarPagoEuskadiNetC60M1_Peticion() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M1_EUSKADI="555";
		final String EMISOR_EUSKADI_C60M1="480447";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M1_EUSKADI, EMISOR_EUSKADI_C60M1);
		pagoEuskadi(liquidacion,"PagoEuskadi_Config.xml");
	}
	
	public void testRealizarPagoEuskadiNetC60M2_Peticion() {
		if(!isInternetConnection()) return;
		final String COD_TASA_C60M2_EUSKADI="100";
		final String EMISOR_EUSKADI_C60M2="010029";
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC60M12(liquidacion, COD_TASA_C60M2_EUSKADI, EMISOR_EUSKADI_C60M2);
		pagoEuskadi(liquidacion,"PagoEuskadi_Config.xml");
	}
	
	public void testRealizarPagoEuskadiNetC60M3_Peticion() {
		if(!isInternetConnection()) return;
		//esperada excepcion 
		try{
			LiquidacionImpl liquidacion=new LiquidacionImpl();
			liquidacion=getLiquidacionC60M3(liquidacion);
			realizarPago(liquidacion,"PagoEuskadi_Config.xml");
		}catch(PagoElectronicoExcepcion e){
			assertTrue(checkPagoElectronicoExceptionCause(e,PagoElectronicoExcepcion.EC_PAGO_AL3_NOT_SUPPORTED));
			return;
		}catch(Exception e){
			fail();
		}
		fail();
	}
	
	public void testRealizarPagoEuskadiNetC57_Peticion() {
		if(!isInternetConnection()) return;
		LiquidacionImpl liquidacion=new LiquidacionImpl();
		liquidacion=getLiquidacionC57(liquidacion);
		pagoEuskadi(liquidacion,"PagoEuskadi_Config.xml");
	}
	
	public void testCheckObtencionNRC_RetornoPasarela() {
		if(!isInternetConnection()) return;
		final String idPago="905224804474041864041909000110117200082091200200091282";
		//String idPago=null;
		//try{ idPago=PaymentRequestData.getObject(xmlPagoPasarela).peticionesPago.keySet().iterator().next().toString(); }
		//catch(Exception e){ fail("Error al obtener el idPAgo a partir del XML devuelto por Euskadi.net: "+e); return; }
		SistemaPasarelaPagoElectronico conectorPasarelaPago=(SistemaPasarelaPagoElectronico)SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico();
		String nrc=conectorPasarelaPago.getNRCByIdPago(idPago);
		assertTrue(StringUtils.isNotEmpty(nrc));
	}

}
