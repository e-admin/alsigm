package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;

import java.sql.Connection;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class SigemPagoElectronicoServiceAdapterDBTestCase extends SigemPagoElectronicoServiceAdapterBaseTestCase {
	private static String numReferencia=null;
	
	private static final String ENTIDAD_EMISORA="000000";
	private static final String COD_TRIBUTO="100";
	
	private LiquidacionImpl getLiquidacionVO(){
		try{
			LiquidacionImpl liquidacion=new LiquidacionImpl();
			liquidacion.setDatosEspecificos("Datos Especificos de la peticion");
			liquidacion.setDiscriminante("1");
			liquidacion.setEjercicio("2010");
			liquidacion.setEstado("00");
			liquidacion.setFechaPago(new SimpleDateFormat("yyyyMMdd-hhmmss").parse("20100412-163425"));
			liquidacion.setFinPeriodo(new SimpleDateFormat("yyyyMMdd").parse("20370101"));
			liquidacion.setIdEntidadEmisora(ENTIDAD_EMISORA);
			liquidacion.setIdTasa(COD_TRIBUTO);
			liquidacion.setImporte("000000003489");
			liquidacion.setInicioPeriodo(new SimpleDateFormat("yyyyMMdd").parse("20010401"));
			liquidacion.setNif("71882675G");
			liquidacion.setNombre("Prueba Pago");
			liquidacion.setNrc("");
			liquidacion.setReferencia("001234123412");
			liquidacion.setRemesa("01");
			liquidacion.setSolicitud(new byte[]{0,0,0,0});
			liquidacion.setVencimiento("20370101");
			return liquidacion;
		}catch(Exception e){
			fail("Error en el relleno de la liquidacion: "+e);
		}
		return null;
	}
	
	private int getNumLiquidaciones(String mensajeError){
		CriterioBusquedaLiquidacion criterio=new CriterioBusquedaLiquidacion();
		try{
			LiquidacionImpl [] liquidaciones=
				SigemPagoElectronicoServiceAdapterDBTestCase.manager.buscarLiquidaciones(criterio, 
					SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
			return liquidaciones.length;
		}catch(Exception e){
			fail(mensajeError+": "+e);
		}
		return -1;
	}
	
	public boolean isDataBaseConnection(){
		try{
			Connection conn=DataSourceManagerMultientidad.getInstance().getConnection(
				DataSourceManagerMultientidad.DEFAULT_DATASOURCE_NAME,
				DefaultConfiguration.getConfiguration(), ENTIDAD);
			return !conn.isClosed();
		}catch(Exception e){
			Logger.getLogger(this.getClass()).debug("no hay conexion a Base de Datos: Configurar en jndi.xml. "+e);
		}
		return false;
	}
	
	public void testAltaLiquidacion() {
		if(!isDataBaseConnection()) return;
		int numLiquidacionesInicial=getNumLiquidaciones("Falló consulta de liquidaciones Alta1");
		
		LiquidacionImpl liquidacion=getLiquidacionVO();
		liquidacion.setReferencia(null);
		try{
			liquidacion=manager.altaLiquidacion(liquidacion, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Falló el alta de la liquidación: "+e);
		}
		numReferencia=liquidacion.getReferencia();
		
		int numLiquidacionesFinal=getNumLiquidaciones("Falló consulta de liquidaciones Alta2");
		assertEquals(1,numLiquidacionesFinal-numLiquidacionesInicial);
	}

	public void testModificarLiquidacion() {
		if(!isDataBaseConnection()) return;
		final String NUEVO_NIF="77889911G";
		LiquidacionImpl liquidacion=getLiquidacionVO();
		liquidacion.setNif(NUEVO_NIF);
		liquidacion.setReferencia(numReferencia);
		
		try{
			manager.modificarLiquidacion(liquidacion, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al modificar liquidacion: "+e);
		}
		CriterioBusquedaLiquidacion criterio=new CriterioBusquedaLiquidacion();
		criterio.setReferencia(numReferencia);
		
		LiquidacionImpl [] liquidaciones=null;
		try{
			liquidaciones=manager.buscarLiquidaciones(criterio, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Error al consultar liquidacion");
		}
		
		assertEquals(NUEVO_NIF,liquidaciones[0].getNif());
	}
	
	public void testBuscarLiquidaciones() {
		if(!isDataBaseConnection()) return;
		CriterioBusquedaLiquidacion criterio=new CriterioBusquedaLiquidacion();
		criterio.setReferencia(numReferencia);
		LiquidacionImpl [] liquidaciones=null;
		try{
			liquidaciones=manager.buscarLiquidaciones(criterio, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Error al consultar liquidacion");
		}
		assertEquals(1,liquidaciones.length);
	}

	public void testBuscarTasas() {
		if(!isDataBaseConnection()) return;
		CriterioBusquedaTasa criterio=new CriterioBusquedaTasa();
		criterio.setTipo("AL3");
		
		TasaImpl[] tasas=null;
		try{
			tasas=manager.buscarTasas(criterio, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr en consulta de tasas: "+e);
		}
		assertEquals(1,tasas.length);
	}

	public void testDetallePago() {
		if(!isDataBaseConnection()) return;
		CriterioBusquedaTasa criterio=new CriterioBusquedaTasa();
		criterio.setTipo("AL3");
		
		PagoImpl pago=null;
		try{
			pago=manager.detallePago(numReferencia, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al recuperar tasa: "+e);
		}
		assertNotNull(pago);
	}

	public void testObtenerDatosTasa() {
		if(!isDataBaseConnection()) return;
		CriterioBusquedaTasa criterio=new CriterioBusquedaTasa();
		criterio.setTipo("AL3");
		
		TasaImpl tasa=null;
		try{
			tasa=manager.obtenerDatosTasa(COD_TRIBUTO,ENTIDAD_EMISORA,SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al recuperar tasa: "+e);
		}
		assertNotNull(tasa);
	}

	public void testObtenerDocumentoPago() {
		if(!isDataBaseConnection()) return;
		CriterioBusquedaTasa criterio=new CriterioBusquedaTasa();
		criterio.setTipo("AL3");
		
		PagoImpl pago=null;
		try{
			pago=manager.detallePago(numReferencia, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al recuperar tasa: "+e);
		}
		
		String documentoPago=null;
		try{
			documentoPago=manager.obtenerDocumentoPago(pago,SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al recuperar el documento pago: "+e);
		}
		assertNotNull(documentoPago);
	}
	
	public void testBajaLiquidacion() {
		if(!isDataBaseConnection()) return;
		int numLiquidacionesInicial=getNumLiquidaciones("Falló consulta de liquidaciones Baja1");
		
		LiquidacionImpl liquidacion=getLiquidacionVO();
		liquidacion.setReferencia(numReferencia);
		
		try{
			manager.bajaLiquidacion(liquidacion, SigemPagoElectronicoServiceAdapterDBTestCase.ENTIDAD);
		}catch(Exception e){
			fail("Erorr al eliminar liquidacion: "+e);
		}
		int numLiquidacionesFinal=getNumLiquidaciones("Falló consulta de liquidaciones Baja2");
		assertEquals(1,numLiquidacionesInicial-numLiquidacionesFinal);
	}
}
