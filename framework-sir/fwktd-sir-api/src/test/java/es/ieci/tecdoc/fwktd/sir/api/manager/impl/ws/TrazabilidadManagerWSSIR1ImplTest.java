package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.net.URL;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.TrazabilidadWS;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.WS_SIR1Service;
import es.ieci.tecdoc.fwktd.sir.api.service.wssir1.WS_SIR1_PortType;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class TrazabilidadManagerWSSIR1ImplTest extends
		AbstractJUnit4SpringContextTests {

	protected static final Logger logger = LoggerFactory.getLogger(TrazabilidadManagerWSSIR1ImplTest.class);
	
	private static final String CODIGO_ENTIDAD_REGISTRAL = "O00002061";
	private static final String CODIGO_INTERCAMBIO = "O00002061_12_10000001";
	private static final boolean IS_REGISTRO = true;
	private static final boolean SOLO_ESTADO_FINAL = false;
	
	private static final TrazabilidadWS[] TRAZAS = TestUtils.createTrazabilidadWSArray();

	@Autowired
	private TrazabilidadManagerWSSIR1Impl fwktd_sir_trazabilidadManagerWSSIR1Impl;

	public TrazabilidadManagerWSSIR1Impl getTrazabilidadManager() throws Exception {

		WS_SIR1_PortType service = EasyMock.createMock(WS_SIR1_PortType.class);

		EasyMock.expect(service.recuperarTrazabilidad((String) EasyMock.anyObject(),(String) EasyMock.anyObject(), EasyMock.anyBoolean()))
				.andReturn(TRAZAS);

		RespuestaWS respuesta = new RespuestaWS();
		respuesta.setCodigo(ErroresEnum.OK.getValue());
		respuesta.setDescripcion(ErroresEnum.OK.getName());

		EasyMock.expect(service.insertarTrazabilidad((String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(),
				(Calendar) EasyMock.anyObject(),
				(Calendar) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), (String) EasyMock.anyObject(),
				(String) EasyMock.anyObject(), EasyMock.anyLong(),
				(String) EasyMock.anyObject())).andReturn(respuesta);
		
		EasyMock.replay(service);
		
		WS_SIR1Service serviceLocator = EasyMock.createMock(WS_SIR1Service.class);
		EasyMock.expect(serviceLocator.getWS_SIR1((URL)EasyMock.anyObject())).andReturn(service);
		
		EasyMock.replay(serviceLocator);

		fwktd_sir_trazabilidadManagerWSSIR1Impl.setServiceLocator(serviceLocator);
		
		return fwktd_sir_trazabilidadManagerWSSIR1Impl;
	}

	@Test
	public void testManager() throws Exception {
		Assert.assertNotNull(getTrazabilidadManager());
	}

	@Test
	public void testGetTrazabilidad() throws Exception {
		List<TrazabilidadVO> trazas = getTrazabilidadManager().getTrazabilidad(
				CODIGO_ENTIDAD_REGISTRAL, CODIGO_INTERCAMBIO, IS_REGISTRO, SOLO_ESTADO_FINAL);

		Assert.assertNotNull(trazas);
		TestUtils.assertEqualsTrazas(TRAZAS, trazas);
		
		for (TrazabilidadVO traza : trazas) {
			logger.info("\tTraza: {}", toString(traza));
		}

	}

	@Test
	public void testInsertTrazabilidad() throws Exception {
		getTrazabilidadManager().insertarTrazabilidad(
				TestUtils.createTrazabilidadVO("1"));
	}

	
	protected static String toString(TrazabilidadVO traza) {
		
		if (traza == null) {
			return null;
		}

		return new StringBuffer()		
			.append("codigo=[").append(traza.getCodigo()).append("]")
			.append(", descripcion=[").append(traza.getDescripcion()).append("]")
			.append(", codigoEstado=[").append(traza.getCodigoEstado()).append("]")
			.append(", codigoError=[").append(traza.getCodigoError()).append("]")
			.append(", descripcionErrorAlternativa=[").append(traza.getDescripcionErrorAlternativa()).append("]")
			.append(", codigoErrorServicio=[").append(traza.getCodigoErrorServicio()).append("]")
			.append(", descripcionErrorServicio=[").append(traza.getDescripcionErrorServicio()).append("]")
			.append(", codigoIntercambio=[").append(traza.getCodigoIntercambio()).append("]")
			.append(", codigoEntidadRegistralDestino=[").append(traza.getCodigoEntidadRegistralDestino()).append("]")
			.append(", descripcionEntidadRegistralDestino=[").append(traza.getDescripcionEntidadRegistralDestino()).append("]")
			.append(", codigoEntidadRegistralOrigen=[").append(traza.getCodigoEntidadRegistralOrigen()).append("]")
			.append(", descripcionEntidadRegistralOrigen=[").append(traza.getDescripcionEntidadRegistralOrigen()).append("]")
			.append(", codigoUnidadTramitacionDestino=[").append(traza.getCodigoUnidadTramitacionDestino()).append("]")
			.append(", descripcionUnidadTramitacionDestino=[").append(traza.getDescripcionUnidadTramitacionDestino()).append("]")
			.append(", codigoUnidadTramitacionOrigen=[").append(traza.getCodigoUnidadTramitacionOrigen()).append("]")
			.append(", descripcionUnidadTramitacionOrigen=[").append(traza.getDescripcionUnidadTramitacionOrigen()).append("]")
			.append(", fechaAlta=[").append(traza.getFechaAlta()).append("]")
			.append(", fechaModificacion=[").append(traza.getFechaModificacion()).append("]")
			.append(", motivoRechazo=[").append(traza.getMotivoRechazo()).append("]")
			.append(", nombreFicheroIntercambio=[").append(traza.getNombreFicheroIntercambio()).append("]")
			.append(", registro=[").append(traza.isRegistro()).append("]")
			.append(", tamanyoDocs=[").append(traza.getTamanyoDocs()).append("]")
			.append(", codigoNodo=[").append(traza.getCodigoNodo()).append("]")
			.toString();
	}

}
