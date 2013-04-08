package ieci.tecdoc.sgm.pe;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;


public class PagoElectronicoManagerTest extends TestCase{
	
	String codEntidad=null;
	
	
	
	public void testAltaLiquidacionAL1(){
		
		PagoElectronicoManager oManager = new PagoElectronicoManager();
		LiquidacionImpl poLiquidacion = new LiquidacionImpl();
		poLiquidacion.setIdEntidadEmisora("000000");
		poLiquidacion.setIdTasa("100");
		poLiquidacion.setImporte("000000005500");
		poLiquidacion.setRemesa("01");
		poLiquidacion.setVencimiento("2008");
		poLiquidacion.setEjercicio("2007");
		poLiquidacion.setNif("05261042E");
		poLiquidacion.setDatosEspecificos("<DETALLE>IMPUESTO CIRCULACION: TURISMO 110CV</DETALLE>");
		poLiquidacion.setNombre("JOAQUIN REGODON HOLGUIN");
		try {
			poLiquidacion.setInicioPeriodo(DateTimeUtil.getDate("01/01/2007", "dd/MM/yyyy"));
			poLiquidacion.setFinPeriodo(DateTimeUtil.getDate("31/12/2007", "dd/MM/yyyy"));			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			poLiquidacion = oManager.altaLiquidacion(poLiquidacion, codEntidad);
		} catch (PagoElectronicoExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
		System.out.println("OK");
	}

//	
//	public void testAltaLiquidacionAL2(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Liquidacion poLiquidacion = new LiquidacionImpl();
//		poLiquidacion.setIdEntidadEmisora("000000");
//		poLiquidacion.setIdTasa("000");
//		poLiquidacion.setImporte("00000018020");
//		poLiquidacion.setRemesa("01");
//		poLiquidacion.setVencimiento("20091231");
//		poLiquidacion.setEjercicio("2007");
//		poLiquidacion.setNif("05261042E");
//		poLiquidacion.setDiscriminante(Liquidacion.DISCRIMINANTE_UN_PERIODO);
//		poLiquidacion.setDatosEspecificos("<DETALLE>IMPUESTO PROPIEDADES: CATASTRO</DETALLE>");
//		poLiquidacion.setNombre("JOAQUIN REGODON HOLGUIN");
//		try {
//			poLiquidacion.setInicioPeriodo(DateTimeUtil.getDate("01/01/2007", "dd/MM/yyyy"));
//			poLiquidacion.setFinPeriodo(DateTimeUtil.getDate("31/12/2007", "dd/MM/yyyy"));			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		try {
//			poLiquidacion = oManager.altaLiquidacion(poLiquidacion);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testBajaLiquidacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Liquidacion poLiquidacion = new LiquidacionImpl();
//		poLiquidacion.setReferencia("0010000000301");
//		try {
//			oManager.bajaLiquidacion(poLiquidacion);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testModificarLiquidacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Liquidacion poLiquidacion = new LiquidacionImpl();
//		poLiquidacion.setReferencia("0010000000421");
//		poLiquidacion.setIdEntidadEmisora("000000");
//		poLiquidacion.setIdTasa("200");
//		poLiquidacion.setImporte("000000000020");
//		poLiquidacion.setRemesa("01");
//		poLiquidacion.setVencimiento("20091231");
//		poLiquidacion.setEjercicio("2007");
//		poLiquidacion.setNif("05261042E");
//		poLiquidacion.setDiscriminante(Liquidacion.DISCRIMINANTE_UN_PERIODO);
//		poLiquidacion.setEstado(Liquidacion.ESTADO_PENDIENTE);
//		try {
//			oManager.modificarLiquidacion(poLiquidacion);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

	
//	
//	public void testBuscarLiquidaciones(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		CriterioBusquedaLiquidacion oCriterio = new CriterioBusquedaLiquidacion();
////		oCriterio.setReferencia("0010000000421");
//		oCriterio.setEjercicio("2007");
////		oCriterio.setEstado("00");
////		oCriterio.setIdEntidadEmisora("000000");
////		oCriterio.setIdTasa("200");
//		oCriterio.setNif("05261042E");
//		List oLista = null;
//		try {
//			oLista = oManager.buscarLiquidaciones(oCriterio);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}
//
//	
//	public void testDetallePago(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago oLista = null;
//		try {
//			oLista = oManager.detallePago("0010000000403");
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

	
//	
//	public void testRealizarPagoCuaderno60_1CuentaNoDomiciliacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("100");
//
//		poPago.setReferencia("000000004992");
//		
//		poPago.setCcc("12360000400000000000");
//		
//		poPago.setCccDomiciliacion("12360000400000000000");
//		poPago.setDomiciliacion(Pago.DOMICILIACION_NO);
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
//		poPago.setMedioPago(Pago.MEDIO_PAGO_CARGO_CUENTA);
////		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");		
//		poPago.setIdEntidadEmisora("000000");
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testRealizarPagoCuaderno60_1CuentaDomiciliacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("100");
//		poPago.setReferencia("000000003992");
//		poPago.setCcc("12360000400000000000");
//		poPago.setCccDomiciliacion("12360000400000000000");
//		poPago.setDomiciliacion(Pago.DOMICILIACION_SI);
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
//		poPago.setMedioPago(Pago.MEDIO_PAGO_CARGO_CUENTA);
//		poPago.setIdioma("0");
////		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");
////		poPago.setNumeroTarjetaCredito("6011000000000004");
////		poPago.setFechaCaducidadTarjetaCredito("0909");
//		
//		poPago.setIdEntidadEmisora("000000");
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}


//	
//	public void testRealizarPagoCuaderno60_1Tarjeta(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("100");
//		poPago.setReferencia("000000003592");
//		poPago.setCcc("60110000000000040505");
////		poPago.setCccDomiciliacion("12360000400000000000");
////		poPago.setDomiciliacion(Pago.DOMICILIACION_SI);
////		poPago.setEntidadBancaria("1236");
//		poPago.setFecha("20070510");
//		poPago.setHora("1657210000");
//		poPago.setMediaPago(Pago.MEDIO_PAGO_TARJETA);
//		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");
//		poPago.setNumeroTarjetaCredito("6011000000000004");
//		poPago.setFechaCaducidadTarjetaCredito("0505");
//		
//		poPago.setIdEntidadEmisora("000000");
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testPagoCuaderno60_1_2(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Cuaderno60Modalidad1_2 oDoc = new Cuaderno60Modalidad1_2();
//		oDoc.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_1);
//		oDoc.setReferencia("000000001004");
//		oDoc.setCcc("12360000400000000000");
//		oDoc.setCccDomiciliacion("12360000400000000000");
//		oDoc.setCodDomiciliacion("2");
//		oDoc.setCodEntidad("1236");
//		oDoc.setFecCaducidad("0909");
//		oDoc.setFecha("20070510");
//		oDoc.setHora("1657210000");
//		oDoc.setIdent1("100");
//		oDoc.setIdent2("1006714");
//		oDoc.setIdentificadorMedioPago("1");
//		oDoc.setIdioma("0");
//		oDoc.setImporte("000000008200");
//		oDoc.setNifCertificado("05261042E");
//		oDoc.setOrganismoEmisor("000000");
//		oDoc.setPan("6011000000000004");
//		oDoc.setReferencia("000004000110");
//
//		try {
//			oManager.pagoCuaderno60_1_2(oDoc);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

	
//	
//	public void testRealizarPagoCuaderno60_2CuentaNoDomiciliacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("000");
//
//		poPago.setReferencia("000000005005");
//		
//		poPago.setCcc("12360000400000000000");
//		
////		poPago.setCccDomiciliacion("12360000400000000000");
//		poPago.setDomiciliacion(Pago.DOMICILIACION_NO);
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
//		poPago.setMedioPago(Pago.MEDIO_PAGO_CARGO_CUENTA);
////		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");		
//		poPago.setIdEntidadEmisora("000000");
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testRealizarPagoCuaderno60_2CuentaDomiciliacion(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("000");
//
//		poPago.setReferencia("000000004642");
//		
//		poPago.setCcc("12360000400000000000");
//		
//		poPago.setCccDomiciliacion("12360000400000000000");
//		poPago.setDomiciliacion(Pago.DOMICILIACION_SI);
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
//		poPago.setMedioPago(Pago.MEDIO_PAGO_CARGO_CUENTA);
////		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");		
//		poPago.setIdEntidadEmisora("000000");
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testRealizarPagoCuaderno60_2PagoTarjeta(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("000");
//
//		poPago.setReferencia("000000004732");
//		
//		poPago.setCcc("12360000400000000000");
//		poPago.setCccDomiciliacion("12360000400000000000");
//		poPago.setDomiciliacion(Pago.DOMICILIACION_SI);
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
////		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");		
//		poPago.setIdEntidadEmisora("000000");
//		
//		poPago.setMedioPago(Pago.MEDIO_PAGO_TARJETA);
//		poPago.setNumeroTarjetaCredito("6011000000000004");
//		poPago.setFechaCaducidadTarjetaCredito("0505");
//		
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testPagoCuaderno60_3(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Cuaderno60Modalidad3 oDoc = new Cuaderno60Modalidad3();
//		oDoc.setCodTributo("200");
//		oDoc.setOrganismoEmisor("000000");		
//		
//		
////		oDoc.setFechaDevengo("20070101");   // opcional
//		
//		oDoc.setInformacionEspecifica("Informacion");
//		oDoc.setCcc("12360000400000000000");
//		oDoc.setCodEntidad("1236");
//		oDoc.setFecCaducidad("0909");
//		oDoc.setFecha("20070510");
//		oDoc.setHora("1657210000");
//		oDoc.setIdentificadorMedioPago("1");
//		oDoc.setIdioma("0");
//		oDoc.setImporte("000000008200");
//		oDoc.setNifCertificado("05261042E");
//		oDoc.setNifContribuyente("05261042E");
//		oDoc.setPan("6011000000000004");		
//		oDoc.setJustificante("9829207967633");
////		oDoc.setExpediente("294710022845");
//		oDoc.setAcreditacionPagos("0");
////		oDoc.setPasarela("01");
////		oDoc.setReferencia("169600703501");
////		oDoc.setReferencia("000000000100");
////		oDoc.setReservado("Pruebas organismo IECI1IECI_SA");
////		oDoc.setTipo("01");
////		oDoc.setUrlRetorno("https://spt.demo.red.es/SIMO/jsp/respuestaC60_12.jsp");
//
//		try {
//			oManager.pagoCuaderno60_3(oDoc);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testRealizarPagoCuaderno60_3Cuenta(){
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		Pago poPago = new PagoImpl();
//		
//		poPago.setIdTasa("200");
//		poPago.setCcc("12360000400000000000");
//		poPago.setEntidadBancaria("1236");
////		poPago.setFecha("20070510");
////		poPago.setHora("1657210000");
////		poPago.setIdioma("0");
//		poPago.setImporte("000000008200");
//		poPago.setNif("05261042E");		
//		poPago.setIdEntidadEmisora("000000");
//		poPago.setMedioPago(Pago.MEDIO_PAGO_CARGO_CUENTA);
//		poPago.setNumeroTarjetaCredito("6011000000000004");
//		poPago.setFechaCaducidadTarjetaCredito("0909");
//		poPago.setAcreditacion(Pago.ACREDITACION_NO_TERCERO_AUTORIZADO);
//		poPago.setInformacionEspecifica("Informacion");
//		poPago.setFechaDevengo("20070101");
//		
//		try {
//			poPago = oManager.realizarPago(poPago);
//		} catch (PagoElectronicoExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void consutaCuadernos60Modalidad1_2() {
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		CriterioBusquedaPago oCriterio = new CriterioBusquedaPago();
//		oCriterio.setReferencia("000000000003");
//		oCriterio.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_1);
////		oCriterio.setEstado("2");
//		Cuaderno60Modalidad1_2 odoc[] = null;
//		try {
//			odoc = oManager.consutaCuadernos60Modalidad1_2(oCriterio);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testConsutaCuadernos60Modalidad3() {
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		CriterioBusquedaPago oCriterio = new CriterioBusquedaPago();
//		oCriterio.setReferencia("0010000000771");
//		oCriterio.setTipo(Cuaderno60Modalidad3.MODALIDAD_3);
////		oCriterio.setEstado("2");
//		Cuaderno60Modalidad3 odoc[] = null;
//		try {
//			odoc = oManager.consutaCuadernos60Modalidad3(oCriterio);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

	
//	
//	public void testPagoAL12() {
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		DocumentoIngresoAutoliquidacionImpl oDoc = new DocumentoIngresoAutoliquidacionImpl();
//		oDoc.setClase("AL1");
//		oDoc.setFecha("200705161213");
//		oDoc.setFichero(new String("prueba").getBytes());
//		oDoc.setIdTasa("01");
//		oDoc.setNifInteresado("1r");
//		oDoc.setNombreInteresado("Fulano");
//		try {
//			oManager.pagoAutoLiquidacion(oDoc);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}

//	
//	public void testPagoAL3() {
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		DocumentoIngresoAutoliquidacionImpl oDoc = new DocumentoIngresoAutoliquidacionImpl();
//		oDoc.setClase("AL3");
//		oDoc.setFecha("200705161213");
//		oDoc.setFichero(new String("prueba").getBytes());
//		oDoc.setIdTasa("01");
//		oDoc.setNifInteresado("1r");
//		oDoc.setNombreInteresado("Fulano");
//		try {
//			oManager.pagoAutoLiquidacion(oDoc);
//		} catch (PagoElectronicoExcepcion e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(true);
//		System.out.println("OK");
//	}
	
//	
//	public void testPagoLiquidacion() {
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
//		DocumentoIngresoLiquidacionImpl oDoc = new DocumentoIngresoLiquidacionImpl();
//		oDoc.setClase("AL3");
//		oDoc.setFichero(new String("prueba").getBytes());
//		oDoc.setIdTasa("01");
//		oDoc.setDescripcion("Pago");
//		oManager.pagoLiquidacion(oDoc);
//		assertTrue(true);
//		System.out.println("OK");
//	}
	

}
