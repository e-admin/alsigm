package es.ieci.tecdoc.fwktd.sir.ws.service;

import ieci.tecdoc.core.base64.Base64Util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;


import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
@ContextConfiguration( {
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralEnvioGeneracionWSFunctionalTest extends
		AbstractWSTest {

	protected static final Logger logger = LoggerFactory.getLogger("TEST");

	private static final ThreadLocal<String> IDENTIFICADOR_ASIENTO_REGISTRAL = new ThreadLocal<String>();

	// sigem
	protected static final String CODIGO_ENTIDAD_REGISTRAL_ORIGEN = "O00002062";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN = "REGISTRO GENERAL DEL AYUNTAMIENTO DE COLINDRES";
	protected static final String CODIGO_UNIDAD_TRAMITACION_ORIGEN = "L01390232";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN = "Ayuntamiento de Colindres";

	// MINISTERIO
	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO = "O00001223";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO = "MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO = "E00106103";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO = "MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";

	// SIGEM
	protected static final String CODIGO_ENTIDAD_REGISTRAL_INICIAL = CODIGO_ENTIDAD_REGISTRAL_ORIGEN;
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL = DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN;

	/**
	 * Test para ejecucion de las pruebas funcionales de generacion de ficheros
	 * sicres3
	 * Corresponde a las pruebas SIR-GE-PR-001 a SIR-GE-PR-063
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEnviarGenerarAsientosRegistralesFuncional() {
		logger.info("Enviando asiento registral...");

		for (int i = 1; i <= 63; i++) {
			try {
				String methodName = "createAsientoRegistralFormParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, null);
				AsientoRegistralFormDTO asientoForm = (AsientoRegistralFormDTO) m
						.invoke(this, null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
						.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert
						.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
			} catch (Exception e) {
				String message = "Error ejecutando test EnviarAsientoRegistralFuncional iteracion:"
						+ i;
				logger.error(message, e);
				// TODO quitar esta salida a consola
				System.out.println(message + "  " + e.getLocalizedMessage());

			}
		}
	}
	
		
	@Test
	public void testEnviarGenerarAsientosRegistralesFuncionalList() {
		logger.info("Enviando asiento registral...");
		
		int[] testToLanch = { 18, 21, 32, 46,54, 55 };
		
		for (int i = 0; i < testToLanch.length; i++) {
			try {
				String methodName = "createAsientoRegistralFormParaTest" + testToLanch[i];
				Method m = this.getClass().getDeclaredMethod(methodName, null);
				AsientoRegistralFormDTO asientoForm = (AsientoRegistralFormDTO) m
						.invoke(this, null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
						.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert
						.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
			} catch (Exception e) {
				String message = "Error ejecutando test EnviarAsientoRegistralFuncional test:"
						+ testToLanch[i];
				logger.error(message, e);

			}
		}

	}
	
	@Test 
	public void  generacionNombresTest(){
		int[] testToLanch = { 2, 3, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 21,
				23, 28, 32, 37, 43, 46, 47, 54, 55 };
		for (int i = 0; i < testToLanch.length; i++) {
			System.out.println(generateTestFileName(testToLanch[i]));
		}
		
		
		
	}
	
	protected String generateTestFileName(int numTest){
		String result="SIR-GE-PR-"+StringUtils.leftPad(Integer.toString(numTest), 3, '0')+"-1";
		return result;
	}
	
	@Test
	public void testEnviarGenerarAsientoRegistralFuncional() {
		logger.info("Enviando asiento registral...");
		
		int i = 9;
		try {
			String methodName = "createAsientoRegistralFormParaTest" + i;
			Method m = this.getClass().getDeclaredMethod(methodName, null);
			AsientoRegistralFormDTO asientoForm = (AsientoRegistralFormDTO) m
					.invoke(this, null);
			AsientoRegistralDTO asiento = getIntercambioRegistralWS()
					.enviarAsientoRegistral(asientoForm);
			Assert.assertNotNull("Asiento registral nulo", asiento);
			Assert.assertTrue("El identificador del asiento registral es nulo",
					StringUtils.isNotBlank(asiento.getId()));
			Assert
					.assertTrue(EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento
							.getEstado());
			logger.info("Asiento registral enviado: {}", toString(asiento));
		} catch (Exception e) {
			String message = "Error ejecutando test EnviarAsientoRegistralFuncional iteracion:"
					+ i;
			logger.error(message, e);
			// TODO quitar esta salida a consola
			System.out.println(message + "  " + e.getLocalizedMessage());

		}
	}
	
	@Test
	public void pruebaParser() throws IOException{
		File file = new File("C:/config/fwktdsir/logs/fwktdsirWSRegistro.log");
		List<String> lines = FileUtils.readLines(file);
		List<String> linesToProcess = new ArrayList<String>();
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String linea= (String) iterator.next();
			if (StringUtils.contains(linea, "<Fichero_Intercambio_SICRES_3>")){
				linesToProcess.add(linea);
			}
			
			
		}
		File fileOut = new File("C:/config/fwktdsir/logs/fwktdsirWSRegistro.salida.txt");
		FileUtils.writeLines(fileOut, linesToProcess,"\n");
		
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un
	 * registro con los campos mínimos requeridos por la aplicación de registro.
	 * El fichero generado debe cumplir:
	 * - Al menos todos los campos requeridos "R" deben estar informados.
	 * - Los campos cuya longitud esté limitada deben ajustarse a límites definidos en SICRES.
	 * - Los campos que contengan fechas deberán tener formato de una cadena de texto 
	 * 	 con el patrón "AAAAMMDDHHMMSS.
	 * - Los campos de tipo base64Binary deberán tener formato de cadena de texto en Base64.
	 * - El campo "Identificador de Intercambio" debe estar informado con una cadena de texto
	 *   con un formato válido "<Código Entidad Registral Origen>_<AA>_<Número Secuencial>.
	 * - El campo "Indicador de prueba" del segmento "De_Internos_Control" debe estar
	 *   informado con el valor "0" para el caso de entorno productivos.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 *  
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest1() {
		String numeroRegistro = "201200100000001";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				null,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				null,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				null);
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);

		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un
	 * registro con todos los campos soportados por la aplicación de registro.
	 * El fichero generado debe cumplir:
	 * 'Se genera correctamente el fichero de intercambio XML con los siguientes requisitos:
	 * - Al menos todos los campos requeridos "R" deben estar informados, e incluyendo
	 *   ocurrencias de los segmentos "De_Anexo", así como con datos en los campos de
	 *   al menos una ocurrencia del segmento "De_Interesado".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest2() {
		String numeroRegistro = "201200100000002";

		// nos serviria el global
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		setInteresadoFisicoConRepresentanteFisico(asientoForm);
		setDefaultAnexo(asientoForm);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con todos los campos soportados por la aplicación de registro, 
	 * introduciendo el tamaño máximo de los campos que sea posible a través de
	 * la aplicación de registro.
	 * El fichero generado debe cumplir:
	 * - Al menos todos los campos requeridos "R" deben estar informados, e 
	 *   incluyendo ocurrencias de los segmentos "De_Anexo", así como con datos
	 *   en los campos de al menos una ocurrencia del segmento "De_Interesado".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest3() {
		String numeroRegistro = "201200100000003";
		//nos sirve el global
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistroLongitudMaxima(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setCodigoAsunto(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con todos los campos soportados por la aplicación de registro, 
	 * introduciendo todo tipo de caracteres especiales permitidos por la 
	 * aplicación en cada uno de los campos.
	 * El fichero generado debe cumplir:
	 * - Al menos todos los campos requeridos "R" deben estar informados, e 
	 *   incluyendo ocurrencias de los segmentos "De_Anexo", así como con datos
	 *   en los campos de al menos una ocurrencia del segmento "De_Interesado",
	 *   en base a la funcionalidad de la aplicación.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest4() {
		String numeroRegistro = "201200100000004";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistroCaracteresEspeciales(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoConRepresentanteFisicoCaracteresEspeciales(asientoForm);
		setDefaultAnexoCaracteresEspeciales(asientoForm);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con código y/o denominación de unidad de tramitación origen.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Código de la Unidad de Tramitación de origen" del segmento
	 *  "De_Origen_o_Remitente" deberá estar informado.
	 * - El campo "Decodificación de la Unidad de Tramitación de origen" del
	 * segmento "De_Origen_o_Remitente" podrá estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest5() {
		String numeroRegistro = "201200100000005";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);

		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con denominación de unidad de tramitación destino.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Código de la Unidad de Tramitación de destino" del segmento
	 *   "De_Destino" deberá estar informado.
	 * - El campo "Decodificación de la Unidad de Tramitación de destino" del
	 *   segmento "De_Destino" podrá estar informado.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest6() {
		String numeroRegistro = "201200100000006";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con timestamp informado.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Timestamp de entrada" del segmento "De_Origen" deberá 
	 *   estar informado.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest7() {
		String numeroRegistro = "201200100000007";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(TestUtils.getTimestamp());
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con referencia externa informada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Referencia externa" del segmento "De_Asunto" deberá estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest8() {
		String numeroRegistro = "201200100000008";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna("1235");
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro con número de expediente informado.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Número de expediente" del segmento "De_Asunto" deberá estar
	 *   informado.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest9() {
		String numeroRegistro = "201200100000009";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente("12345965423");
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro que tiene informado el código de asunto en el destino.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Código de asunto según destino" del segmento "De_Asunto" 
	 *   deberá estar informado solamente si el origen conoce el catálogo de 
	 *   asuntos de destino, es necesario tenerlo presente en posibles reenvíos 
	 *   a otras oficinas.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest10() {
		String numeroRegistro = "201200100000010";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto("0126");   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación y envío de fichero de intercambio correspondiente a un 
	 * registro que no tiene informado el unidad de tramitación origen ni 
	 * interesado.
	 * 
	 * NO se genera el fichero de intercambio XML, ya que la aplicación de 
	 * registro no debe permitir dar de alta un registro con un segmento 
	 * "De_Interesado" que solamente contenga información de representante.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest11() {
		String numeroRegistro = "201200100000011";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		setSinInteresadoConRepresentanteFisico(asientoForm);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física sin representante y sin canal notificación de
	 * comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer apellido del Interesado"
	 *     deben estar informados.
	 *   - Opcionalmente, los campos "Tipo de Documento de Identificación del
	 *     Interesado" y "Documento de identificación del Interesado" podrán 
	 *     estar informados.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest12() {
		String numeroRegistro = "201200100000012";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con documento identificativo de tipo NIF, sin 
	 * representante y sin canal notificación de comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado"
	 *     deben estar informados  y opcionalmente el campo "Segundo apellido 
	 *     del Interesado".
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener el valor asociado al tipo NIF.
	 *   - El campo "Documento de identificación del Interesado" debe estar 
	 *     informado el valor válido de NIF.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest13() {
		String numeroRegistro = "201200100000013";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("00000000T");
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con documento identificativo de tipo NIE, sin
	 * representante y sin canal notificación de comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado"
	 *     deben estar informados  y opcionalmente el campo "Segundo apellido 
	 *     del Interesado".
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener el  valor asociado al tipo NIE.
	 *   - El campo "Documento de identificación del Interesado" debe estar 
	 *     informado el valor válido de NIE.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest14() {
		String numeroRegistro = "201200100000014";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIE.getValue());
		interesado.setDocumentoIdentificacionInteresado("Z0996302Q");
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con documento identificativo de tipo Pasaporte,
	 * sin representante y sin canal notificación de comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado"
	 *     deben estar informados  y opcionalmente el campo "Segundo apellido 
	 *     del Interesado".
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener el  valor asociado al tipo Pasaporte.
	 *   - El campo "Documento de identificación del Interesado" debe estar 
	 *     informado el valor del número de Pasaporte.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest15() {
		String numeroRegistro = "201200100000015";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.PASAPORTE.getValue());
		interesado.setDocumentoIdentificacionInteresado("AB123456");
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con documento identificativo de tipo Otros de 
	 * persona jurídica, sin representante y sin canal notificación de 
	 * comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado"
	 *     deben estar informados  y opcionalmente el campo "Segundo apellido 
	 *     del Interesado".
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener el valor asociado al tipo Otros.
	 *   - El campo "Documento de identificación del Interesado" debe estar 
	 *     informado el valor correspondiente.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest16() {
		String numeroRegistro = "201200100000016";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.OTROS_PERSONA_FISICA.getValue());
		interesado.setDocumentoIdentificacionInteresado("V123459729-O");
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica sin representante y sin canal notificación de
	 * comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - El campo "Razón social del Interesado" debe estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest17() {
		String numeroRegistro = "201200100000017";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setRazonSocialInteresado("IECISA");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica con documento identificativo de tipo CIF, 
	 * sin representante y sin canal notificación de comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - El campo "Razón Social del Interesado" debe estar informado.
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener el valor asociado al tipo CIF.
	 *   - El campo "Documento de identificación del Interesado" debe estar 
	 *     informado con el valor válido de CIF.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest18() {
		String numeroRegistro = "201200100000018";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		vaciarInteresado(asientoForm);
		vaciarRepresentantesInteresado(asientoForm);
		InteresadoFormDTO interesado=asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("F9037286C");
		interesado.setRazonSocialInteresado("IECISA");
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con documento identificativo, con representante
	 * como persona física y sin canal notificación de comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado"
	 *     deben estar informados  y opcionalmente el campo "Segundo apellido 
	 *     del Interesado".
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe 
	 *     estar informado y contener uno de los siguientes tips: NIE, NIF o 
	 *     Pasaporte u Otros de persona física.
	 *   - El campo "Documento de Identificación del Interesado" debe estar 
	 *     informado y en el caso de NIE y NIF debe contener un valor válido al
	 *     formato.
	 *   - Los campos "Nombre del Representante" y "Primer Apellido del 
	 *     Representante" deben estar informados  y opcionalmente el campo 
	 *     "Segundo apellido del Representante".
	 *   - El campo "Tipo de Documento de Identificación de Representante" debe
	 *     estar informado y contener uno de los siguientes tips: NIE, NIF o 
	 *     Pasaporte u Otros de persona física.
	 *   - El campo "Documento de Identificación del Representante" debe estar
	 *     informado y en el caso de NIE y NIF debe contener un valor válido al
	 *     formato.
	 *     
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest19() {
		String numeroRegistro = "201200100000019";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		setInteresadoFisicoConRepresentanteFisico(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica con documento identificativo, con 
	 * representante como persona jurídica y sin canal notificación de 
	 * comunicación.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - El campo "Razón Social del Interesado" debe estar informado.
	 *   - El campo "Tipo de Documento de Identificación del Interesado" debe
	 *     estar informado con el valor de CIF.
	 *   - El campo "Documento de Identificación del Interesado" debe estar
	 *     informado debe contener un valor válido al formato.
	 *   - El campo "Razón Social del Interesado" debe estar informado.
	 *   - El campo "Tipo de Documento de Identificación del Representante" debe
	 *     estar informado con el valor de CIF.
	 *   - El campo "Documento de Identificación del Representante" debe estar
	 *     informado con un valor válido al formato.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest20() {
		String numeroRegistro = "201200100000020";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);

		// Seteamos a null los innecesarios desde la aplicacion de registro
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
     * Generación de fichero de intercambio correspondiente a un registro con un
     * interesado persona física sin representante,  siendo el canal preferente 
     * de notificación la dirección postal del Interesado en España con código 
     * postal.
     * El fichero generado debe cumplir:
     * - Todos los campos requeridos "R" deben estar informados, además de una
     *   ocurrencia del segmento "De_Interesado" siguientes:
     *   - Los campos "Nombre del Interesado" y "Primer apellido del Interesado"
     *     deben estar informados.
     *   - El campo "Canal preferente de notificación del Interesado" debe tener
     *     el valor "01".
     *   - Los campos "País del Interesado" y "Dirección del Interesado" deben 
     *     estar informados.
     *   - El campo "País del Interesado" debe tener el valor del código "724".
     *   - El campo "Código postal del Interesado" debe estar informado.
     *   
     * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest21() {
		String numeroRegistro = "201200100000021";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		vaciarRepresentantesInteresado(asientoForm);
		setInteresadoFisicoSinRepresentante(asientoForm);
		
		asientoForm.setTimestampRegistro(null);
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física sin representante, siendo el canal preferente 
	 * de notificación la dirección postal en España con código de municipio y 
	 * provincia.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer apellido del Interesado"
	 *     deben estar informados.
	 *   - El campo "Canal Preferente de Notificación del Interesado" debe tener
	 *     el valor "01".
	 *   - Los campos "País del Interesado" y "Dirección del Interesado" deben
	 *     estar informados.
	 *   - El campo "País del Interesado" debe tener el valor del código "724".
	 *   - Los campos "Provincia del Interesado" y "Municipio del Interesado"  
	 *     deben estar informados con su correspondiente código del catálogo
	 *     correspondiente de Directorio Común.
	 *       
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest22() {
		String numeroRegistro = "201200100000022";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física sin representante, siendo el canal preferente 
	 * de notificación la dirección postal fuera de España.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer apellido del Interesado" 
	 *   deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Interesado" debe tener 
	 *   el valor "01".
	 * - Los campos "País del Interesado" y "Dirección del Interesado" deben 
	 *   estar informados.
	 * - El campo "País del Interesado" debe tener el valor del código del país 
	 *   correspondiente.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest23() {
		String numeroRegistro = "201200100000023";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);

		interesado.setCodigoPaisInteresado("320");
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física sin representante, siendo el canal preferente 
	 * de notificación la dirección electrónica habilitada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado" 
	 *   deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Interesado" debe tener 
	 *   el valor "02".
	 * - El campo "Dirección electrónica habilitada del Interesado" debe estar 
	 *   informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest24() {
		String numeroRegistro = "201200100000024";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setDireccionElectronicaHabilitadaInteresado("direccionElectronica@ieci.es");
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física sin representante, siendo el canal preferente 
	 * de notificación la comparecencia electrónica.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer apellido del Interesado" 
	 *   deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Interesado" debe tener 
	 *   el valor "03".
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest25() {
		String numeroRegistro = "201200100000025";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA.getValue());

		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambiocorrespondiente a un registro con un 
	 * interesado persona jurídica sin representante, siendo el canal preferente
	 * de notificación de comunicación la dirección postal en España con código
	 * postal.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Interesado" debe tener 
	 *   el valor "01".
	 * - Los campos "País del Interesado" y "Dirección del Interesado" deben 
	 *   estar informados.
	 * - El campo "País del Interesado" debe tener el valor del código "724".
	 * - El campo "Código Postal del Interesado" debe estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest26() {
		String numeroRegistro = "201200100000026";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica sin representante, siendo el canal preferente
	 * de notificación de comunicación la dirección postal en España con 
	 * provincia y municipio.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Interesado" debe tener 
	 *   el valor "01".
	 * - Los campos "País del Interesado" y "Dirección del Interesado" deben 
	 *   estar informados.
	 * - El campo "País del Interesado" debe tener el valor del código "724".
	 * - Los campos "Provincia del Interesado" y "Municipio del Interesado" debe
	 *   estar informados con su correspondiente código de Directorio Común.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest27() {
		String numeroRegistro = "201200100000027";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica sin representante, siendo el canal preferente
	 * de notificación la dirección postal fuera de España.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Canal preferente de notificación del Interesado" debe tener 
	 *   el valor "01".
	 * - Los campos "País del Interesado" y "Dirección del Interesado" deben 
	 *   estar informados.
	 * - El campo "País del Interesado" debe tener el valor del código del país
	 *   correspondiente.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest28() {
		String numeroRegistro = "201200100000028";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);

		interesado.setCodigoPaisInteresado("320");
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica sin representante, siendo el canal preferente
	 * de notificación la dirección electrónica habilitada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Canal preferente de notificación del Interesado" debe tener
	 *   el valor "02".
	 * - El campo "Dirección electrónica habilitada del Interesado" debe estar
	 *   informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest29() {
		String numeroRegistro = "201200100000029";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setDireccionElectronicaHabilitadaInteresado("direccionElectronica@ieci.es");
		return asientoForm;
	}


/**
 * Generación de fichero de intercambio correspondiente a un registro con un 
 * interesado persona jurídica sin representante, siendo el canal preferente de
 * notificación la comparecencia electrónica.
 * El fichero generado debe cumplir:
 * - Todos los campos requeridos "R" deben estar informados, además de una 
 *   ocurrencia del segmento "De_Interesado".
 * - El campo "Razón social del Interesado" debe estar informado.
 * - El campo "Canal preferente de notificación del Interesado" debe tener el
 *   valor "03".
 * 
 * @return un AsientoRegistralFormDTO con toda la información necesaria
 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest30() {
		String numeroRegistro = "201200100000030";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoSinRepresentante(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA.getValue());
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con representante persona física, siendo el 
	 * canal preferente de notificación de representante, la dirección postal en
	 * España con código de municipio y provincia.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado" siguientes:
	 *   - Los campos "Nombre del Interesado" y "Primer apellido del Interesado"
	 *     deben estar informados.
	 *   - Los campos "Nombre del Representante" y "Primer apellido del 
	 *     Representante" deben estar informados.
	 *   - El campo "Canal Preferente de Notificación del Representante" debe 
	 *     tener el valor "01".
	 *   - Los campos "País del Representante" y "Dirección del Representante"
	 *     deben estar informados.
	 *   - El campo "País del Representante" debe tener el valor del código
	 *     "724".
	 *   - Los campos "Provincia del Representante" y "Municipio del 
	 *     Representante"  deben estar informados con su correspondiente código
	 *     del catálogo correspondiente de Directorio Común.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest31() {
		String numeroRegistro = "201200100000031";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoConRepresentanteFisico(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}


	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con representante persona física, siendo el 
	 * canal preferente de notificación del representante, la dirección postal 
	 * del Interesado fuera de España.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer apellido del Interesado"
	 *   deben estar informados.
	 * - Los campos "Nombre del Representante" y "Primer apellido del 
	 *   Representante" deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "01".
	 * - Los campos "País del Interesado" y "Dirección del Representante" deben
	 *   estar informados.
	 * - El campo "País del Representante" debe tener el valor del código del
	 *   país correspondiente.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest32() {
		String numeroRegistro = "201200100000032";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoConRepresentanteFisico(asientoForm);
		//Con este tengo dudas, lo explica un poco raro...
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setTelefonoInteresado(null);
		
		interesado.setDireccionInteresado("direccion externa");
		interesado.setCodigoPaisInteresado("320");
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoMunicipioInteresado(null);
		

		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setCodigoPaisRepresentante("320");
		interesado.setDireccionRepresentante("direccion externa");
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con representante persona física, siendo el 
	 * canal preferente de notificación del representan, la dirección 
	 * electrónica habilitada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer Apellido del Interesado" 
	 *   deben estar informados.
	 * - Los campos "Nombre del Representante" y "Primer apellido del 
	 *   Representante" deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "02".
	 * - El campo "Dirección Electrónica habilitada del Representante" debe 
	 *   estar informado.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest33() {
		String numeroRegistro = "201200100000033";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoConRepresentanteFisico(asientoForm);

		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setDireccionElectronicaHabilitadaRepresentante("direccionElectronicaRepresentante@ieci.es");
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona física con representante, siendo el canal notificación
	 * de comunicación del representante la comparecencia electrónica.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - Los campos "Nombre del Interesado" y "Primer apellido del Interesado" 
	 *   deben estar informados.
	 * - Los campos "Nombre del Representante" y "Primer apellido del 
	 *   Representante" deben estar informados.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "03".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest34() {
		String numeroRegistro = "201200100000034";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoFisicoConRepresentanteFisico(asientoForm);

		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA.getValue());
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambiocorrespondiente a un registro con un
	 * interesado persona jurídica con representante, siendo el canal preferente
	 * de notificación de comunicación del representante, la dirección postal 
	 * del Interesado en España con código postal.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Razón social del Representante" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "01".
	 * - Los campos "País del Representante" y "Dirección del Representante" 
	 *   deben estar informados.
	 * - El campo "País del Representante" debe tener el valor del código "724".
	 * - El campo "Código Postal del Representante" debe estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest35() {
		String numeroRegistro = "201200100000035";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambiocorrespondiente a un registro con un
	 * interesado persona jurídica con representante, siendo el canal preferente
	 * de notificación de comunicación del representante, la dirección postal 
	 * del Interesado en España con provincia y municipio.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Razón social del Representante" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "01".
	 * - Los campos "País del Representante" y "Dirección del Representante" 
	 *   deben estar informados.
	 * - El campo "País del Representante" debe tener el valor del código "724".
	 * - Los campos "Provincia del Representante" y "Municipio del 
	 *   Representante" debe estar informados con su correspondiente código de 
	 *   Directorio Común.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest36() {
		String numeroRegistro = "201200100000036";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);

		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambiocorrespondiente a un registro con un
	 * interesado persona jurídica con representante, siendo el canal preferente
	 * de notificación del representante, la dirección postal del Interesado 
	 * fuera de España.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Razón social del Representante" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "01".
	 * - Los campos "País del Representante" y "Dirección del Representante" 
	 *   deben estar informados.
	 * - El campo "País del Representante" debe tener el valor del código del 
	 *   país correspondiente.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest37() {
		String numeroRegistro = "201200100000037";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);

		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCodigoPaisRepresentante("320");
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica con representante, siendo el canal preferente
	 * de notificación del representante la dirección electrónica habilitada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Razón social del Representante" debe estar informado.
	 * - El campo "Canal Preferente de Notificación del Representante" debe 
	 *   tener el valor "02".
	 * - El campo "Dirección Electrónica Habilitada del Representante" debe 
	 *   estar informado.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest38() {
		String numeroRegistro = "201200100000038";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);

		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setDireccionElectronicaHabilitadaRepresentante("direccionElectronicaRepresentanteJuridico@ieci.es");
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * interesado persona jurídica con representante persona jurídica, siendo el
	 * canal preferente de notificación del representante la comparecencia 
	 * electrónica.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Interesado".
	 * - El campo "Razón social del Interesado" debe estar informado.
	 * - El campo "Razón social del Representante" debe estar informado.
	 * - El campo "Canal preferente de notificación del Representante" debe 
	 *   tener el valor "03".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest39() {
		String numeroRegistro = "201200100000039";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		setInteresadoJuridicoConRepresentanteJuridico(asientoForm);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA.getValue());
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con 
	 * interesado pero con representante y ambos con canal preferentes de 
	 * notificación, cualquiera.
	 * El fichero generado debe cumplir:
	 * - Tanto el interesado como el representante deben respetar las lógicas de
	 *   validación de los casos de prueba: SIR-GE-PR-021 al SIR-GE-PR-039.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest40() {
		String numeroRegistro = "201200100000040";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA.getValue());
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setDireccionRepresentante(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro sin 
	 * interesado pero con representante.
	 * NO se genera el fichero de intercambio XML, ya que la aplicación de 
	 * registro no debe permitir dar de alta un registro con un segmento 
	 * "De_Interesado" que solamente contenga información de representante.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest41() {
		String numeroRegistro = "201200100000041";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(null);
		interesado.setTipoDocumentoIdentificacionInteresado(null);
		interesado.setDocumentoIdentificacionInteresado(null);
		interesado.setCodigoMunicipioInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro sin 
	 * ficheros anexos.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, sin incluir 
	 *   ninguna ocurrencia del segmento "De_Anexo".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest42() {
		String numeroRegistro = "201200100000042";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero de un documento adjunto al formulario trasmitido en el fichero de
	 * intercambio.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, además de una 
	 *   ocurrencia del segmento "De_Anexo".
	 * - El campo "Nombre del fichero anexado" debe estar informado con una 
	 *   cadena de texto.
	 * - El campo "Identificador de fichero" debe estar informado con una cadena
	 *   de texto con un formato válido "<Identificador de Intercambio>_<Código
	 *   de tipo de archivo>_<Número Secuencial>.<Extensión del fichero>". 
	 *   Siendo el código de tipo de archivo el valor "01".
	 * - El campo "Tipo de documento" debe estar informado con el valor "02".
	 * - El campo "Hash" debe estar informado con una cadena de texto en formato
	 *   Base64.
	 * - El campo "Anexo" debe estar informado con una cadena de texto en 
	 *   formato Base64.
	 * - El campo "Tipo MIME" podrá estar informado en dentro los tipos 
	 *   definidos en el estándar MIME. Limitación de tipo MIME a 20 caracteres.
	 *   En caso de ser superior el tipo MIME no se informará.
	 * - El resto de campos de este segmento estarán sin informar.
	 * - El campo "Documentación física" del segmento "De_Internos_Control" debe
	 *   estar informado con el valor "03".
	 *    
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest43() {
		String numeroRegistro = "201200100000043";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("FicheroIntercambio.txt");
		anexo.setTipoDocumento("02");
		anexo.setContenido("Contenido del fichero".getBytes());
		anexo.setTipoMIME("text/plain");
		asientoForm.getAnexos().add(anexo);
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA.getValue());
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero de tipo formulario con documentación física complementaria.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * - El campo "Identificador de Fichero" debe estar informado con una cadena
	 *   de texto con un formato válido 
	 *   "<Identificador de Intercambiol>_01_<Secuencia>.ext.
	 * - El campo "Tipo de documento" del segmento "De_Anexo" debe estar 
	 *   informado con el valor "01".
	 * - El campo "Documentación física" del segmento "De_Internos_Control" debe
	 *   estar informado con el valor "2". 
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest44() {
		String numeroRegistro = "201200100000044";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero de tipo documento adjunto con documentación física requerida.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * - El campo "Tipo de documento" del segmento "De_Anexo" debe estar 
	 *   informado con el valor "02".
	 * - El campo "Documentación física" del segmento "De_Internos_Control" debe
	 *   estar informado con el valor "1".
	 *    
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest45() {
		String numeroRegistro = "201200100000045";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con 
	 * dos ficheros, uno de tipo documento adjunto y el otro como firma 
	 * electrónica del anterior sin documentación física. El documento adjunto 
	 * con información de validez.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * Para el primer documento:
	 * - El campo "Tipo de documento" del segmento "De_Anexo" debe estar 
	 *   informado con el valor "02".
	 * - El campo "Identificador del documento firmado" debe estar vacío.
	 * - El campo "Validez del documento" debe contener algún valor '01', '02',
	 *   '03' y '04'.
	 * Para el segundo documento:
	 * - El campo "Tipo de documento" del segmento "De_Anexo" debe estar 
	 *   informado con el valor "03".
	 * - El campo "Identificador del documento firmado" debe contener el valor
	 *   del campo "Identificador de documento" del primer documento.
	 * - El campo "Documentación física" del segmento "De_Internos_Control" debe
	 *   estar informado con el valor "3".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest46() {
		String numeroRegistro = "201200100000046";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("FicheroIntercambio.txt");
		anexo.setTipoDocumento("02");
		anexo.setContenido("Contenido del fichero".getBytes());
		anexo.setValidezDocumento("01");
		anexo.setCodigoFichero("doc1");
		asientoForm.getAnexos().add(anexo);
		AnexoFormDTO anexoFirmado = new AnexoFormDTO();
		anexoFirmado.setNombreFichero("FicheroIntercambioFirma.txt");
		anexoFirmado.setTipoDocumento("03");
		anexoFirmado.setContenido("Firma del documento anterior".getBytes());
		anexoFirmado.setCodigoFichero("doc2");
		anexoFirmado.setCodigoFicheroFirmado("doc1");
		asientoForm.getAnexos().add(anexoFirmado);
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA.getValue());
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero con firma embebida.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * - El campo "Identificador del documento firmado" debe contener el mismo
	 *   valor del campo "Identificador de documento" para el mismo fichero.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria 
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest47() {
		String numeroRegistro = "201200100000047";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("FicheroIntercambio.txt");
		anexo.setTipoDocumento("02");
		anexo.setContenido("Contenido del fichero".getBytes());
		anexo.setValidezDocumento("01");
		anexo.setCodigoFichero("doc1");
		anexo.setCodigoFicheroFirmado("doc1");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero anexo con tipo MIME identificado.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * - El campo "Tipo MIME" del segmento "De_Anexo" debe estar informado con 
	 *   un valor correspondiente a un tipo que se corresponda con la extensión
	 *   del identificador contenido en el campo "Identificador de fichero" del
	 *   mismo segmento.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest48() {
		String numeroRegistro = "201200100000048";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("FicheroIntercambio.txt");
		anexo.setTipoDocumento("02");
		anexo.setContenido("Contenido del fichero".getBytes());
		anexo.setTipoMIME("text/plain");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro con un
	 * fichero que incluye su firma.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados, incluyendo dos
	 *   ocurrencias del segmento "De_Anexo".
	 * - El campo "Firma" del segmento "De_Anexo" debe contener la firma en 
	 *   codificada en Base64. No debe informarse por duplicado la firma en el
	 *   campo "Firma" o incorporada como un segmento "De_Anexo" adicional.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest49() {
		String numeroRegistro = "201200100000049";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("FicheroIntercambio.txt");
		anexo.setTipoDocumento("02");
		anexo.setContenido("Contenido del fichero".getBytes());
		anexo.setFirma("Firma del documento".getBytes());
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro 
	 * electrónico con datos de formulario genérico.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados,incluyendo el 
	 *   segmento "De_Formulario_Generico" con los campos rellenos de "Expone" 
	 *   y "Solicita"
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest50() {
		String numeroRegistro = "201200100000050";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		//El asiento por defecto ya crea Expone y Solicita
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de Registro" del segmento "De_Internos_Control" debe 
	 *   estar informado con el valor "0"
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest51() {
		String numeroRegistro = "201200100000051";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.setTipoRegistro("0");
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * salida.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de Registro" del segmento "De_Internos_Control" debe 
	 *   estar informado con el valor "1".
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest52() {
		String numeroRegistro = "201200100000052";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		asientoForm.setTipoRegistro("1");
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro 
	 * enviado desde la oficina origen inicial.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de Anotación" del segmento "De_Internos_Control" debe 
	 *   estar informado con el valor "02".
	 * - El campo "Código Entidad Registral de Inicio" del segmento 
	 *   "De_Internos_Control" debe estar informado con el mismo valor que el 
	 *   campo "Código Entidad Registral de Origen" del segmento "De_Origen".
	 * - El campo "Decodificación Código Entidad Registral de Inicio" del 
	 *   segmento "De_Internos_Control" debe estar informado con el mismo valor
	 *   que el campo "Decodificación Código Entidad Registral de Origen" del 
	 *   segmento "De_Origen" si la aplicación lo informa.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest53() {
		String numeroRegistro = "201200100000053";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro 
	 * reenviado.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de Anotación" del segmento "De_Internos_Control" debe 
	 *   estar informado con el valor "03".
	 * - El campo "Código Entidad Registral de inicio" debe estar informado con
	 *   el valor del "Código Entidad Registral de origen" de la entidad 
	 *   registral que realizó el primer envío.
	 * - Únicamente se modifica el campo "Código Entidad Registral de Destino"
	 *   y en su caso si la aplicación lo informa, los campos "Decodificación de
	 *   Entidad Registral de Destino", "Código Unidad de Tramitación de 
	 *   Destino" y la "Decodificación de Unidad de Tramitación Destino".
	 * - Los campos "Nombre de Usuario", "Contacto de Usuario" y "Aplicación y
	 *   Versión Emisora" deben corresponder con los datos de la aplicación que
	 *   realiza el reenvío.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	//TODO A priori no puede generarse 03
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest54() {
		String numeroRegistro = "201200100000054";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro 
	 * rechazado.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de Anotación" del segmento "De_Internos_Control" debe 
	 *   estar informado con el valor "04".
	 * - El campo "Código Entidad Registral de inicio" debe estar informado con
	 *   el valor del "Código Entidad Registral de origen" de la entidad 
	 *   registral que realizó el primer envío.
	 * - Únicamente se deben intercambiar los campos de código oficinas de 
	 *   registro origen y destino y en su caso la denominación. No se debe 
	 *   modificar el código y denominación de la unidad de tramitación destino.
	 * - Los campos "Nombre de Usuario", "Contacto de Usuario" y "Aplicación y
	 *   Versión Emisora" deben corresponder con los datos de la aplicación que
	 *   realiza el rechazo.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	//TODO A priori no puede generarse 04
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest55() {
		String numeroRegistro = "201200100000055";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNumeroTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Número de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con la referencia del
	 *   transporte.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest56() {
		String numeroRegistro = "201200100000056";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setTipoTransporte(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por servicio de mensajeros y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "01".
	 * - El campo "Número de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con la referencia del 
	 *   transporte.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest57() {
		String numeroRegistro = "201200100000057";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.SERVICIO_MENSAJEROS.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por correo postal y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "02".
	 * - El campo "Número de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con la referencia del 
	 *   transporte.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest58() {
		String numeroRegistro = "201200100000058";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.CORREO_POSTAL.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por correo postal certificado y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "03".
	 * - El campo "Número de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con la referencia del 
	 *   transporte.
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest59() {
		String numeroRegistro = "201200100000059";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.CORREO_POSTAL_CERTIFICADO.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por burofax y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "04".
	 * - El campo "Número de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con la referencia del
	 *   transporte.
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest60() {
		String numeroRegistro = "201200100000060";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.BUROFAX.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por entrega en mano y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "05".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest61() {
		String numeroRegistro = "201200100000061";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.EN_MANO.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por fax y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 *   "De_Internos_Control" debe estar informado con el valor "06".
	 *   
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest62() {
		String numeroRegistro = "201200100000062";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.FAX.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Generación de fichero de intercambio correspondiente a un registro de 
	 * entrada recibido por entrega de otro tipo y con número de transporte.
	 * El fichero generado debe cumplir:
	 * - Todos los campos requeridos "R" deben estar informados.
	 * - El campo "Tipo de transporte de entrada" del segmento 
	 * "De_Internos_Control" debe estar informado con el valor "07".
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest63() {
		String numeroRegistro = "201200100000063";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_ORIGEN,
				DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,
				CODIGO_UNIDAD_TRAMITACION_ORIGEN,
				DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,
				CODIGO_ENTIDAD_REGISTRAL_INICIAL,
				DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);
		
		asientoForm.setTipoTransporte(TipoTransporteEnum.OTROS.getValue());
		
		// Seteamos a null los innecesarios desde la aplicacion de registro
		asientoForm.setTimestampRegistro(null);
		asientoForm.getInteresados().clear();
		asientoForm.setCodigoAsunto(null);   
		asientoForm.setReferenciaExterna(null);
		asientoForm.setNumeroExpediente(null);
		asientoForm.setNombreUsuario(null);
		asientoForm.setContactoUsuario(null);
		asientoForm.setObservacionesApunte(null);
		asientoForm.setExpone(null);
		asientoForm.setSolicita(null);
		
		return asientoForm;
	}

	/**
	 * Crea un interesado físico sin representante
	 * Si la lista está vacia lo añade. Sino usa el primero.
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoFisicoSinRepresentante(
			AsientoRegistralFormDTO asientoForm) {
		InteresadoFormDTO interesado;
		if(asientoForm.getInteresados().size()==0){ 
			interesado=new InteresadoFormDTO();
			asientoForm.getInteresados().add(interesado);
		}
		else{ interesado=asientoForm.getInteresados().get(0); }

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("00000000T");
		interesado.setRazonSocialInteresado(null);
		interesado.setNombreInteresado("Isidoro");
		interesado.setPrimerApellidoInteresado("Álvarez");
		interesado.setSegundoApellidoInteresado("Álvarez");
		interesado.setCodigoPaisInteresado("724");
		interesado.setCodigoProvinciaInteresado("28");
		interesado.setCodigoMunicipioInteresado("0796");
		interesado.setDireccionInteresado("Hermosilla, 112");
		interesado.setCodigoPostalInteresado("28009");
		interesado.setCorreoElectronicoInteresado("servicio_clientes@elcorteingles.es");
		interesado.setTelefonoInteresado("901122122");
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());

		interesado.setTipoDocumentoIdentificacionRepresentante(null);
		interesado.setDocumentoIdentificacionRepresentante(null);
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante(null);
		interesado.setPrimerApellidoRepresentante(null);
		interesado.setSegundoApellidoRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
	}

	/**
	 * Cambia el primer interesado por un interesado judirico sin representante
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoJuridicoSinRepresentante(
			AsientoRegistralFormDTO asientoForm) {
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("INFORMÁTICA EL CORTE INGLÉS, S.A.");
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("724");
		interesado.setCodigoProvinciaInteresado("28");
		interesado.setCodigoMunicipioInteresado("0796");
		interesado.setDireccionInteresado("Travesía de Costa Brava nº4");
		interesado.setCodigoPostalInteresado("28034");
		interesado.setCorreoElectronicoInteresado("mkt@ieci.es");
		interesado.setTelefonoInteresado("913874700");
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());

		interesado.setTipoDocumentoIdentificacionRepresentante(null);
		interesado.setDocumentoIdentificacionRepresentante(null);
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante(null);
		interesado.setPrimerApellidoRepresentante(null);
		interesado.setSegundoApellidoRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
	}

	/**
	 * Cambia el primer interesado por un interesado fisico con representante
	 * fisico
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoFisicoConRepresentanteFisico(
			AsientoRegistralFormDTO asientoForm) {
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("11111111A");
		interesado.setRazonSocialInteresado(null);
		interesado.setNombreInteresado("Antonio");
		interesado.setPrimerApellidoInteresado("Alonso");
		interesado.setSegundoApellidoInteresado("Mendiburu");
		interesado.setCodigoPaisInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoMunicipioInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(null);

		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("Isidoro");
		interesado.setPrimerApellidoRepresentante("Álvarez");
		interesado.setSegundoApellidoRepresentante("Álvarez");
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante("Hermosilla, 112");
		interesado.setCodigoPostalRepresentante("28009");
		interesado.setCorreoElectronicoRepresentante("servicio_clientes@elcorteingles.es");
		interesado.setTelefonoRepresentante("901122122");
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
	}

	/**
	 * Cambia el primer interesado por un interesado judirico con representante
	 * judirico
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoJuridicoConRepresentanteJuridico(
			AsientoRegistralFormDTO asientoForm) {
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("INFORMÁTICA EL CORTE INGLÉS, S.A.");
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoMunicipioInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(null);

		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("A58818501");
		interesado.setRazonSocialRepresentante("Representaciones IECI");
		interesado.setNombreRepresentante(null);
		interesado.setPrimerApellidoRepresentante(null);
		interesado.setSegundoApellidoRepresentante(null);
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante("Caveda, 35");
		interesado.setCodigoPostalRepresentante("33005");
		interesado.setCorreoElectronicoRepresentante("representaciones@elcorteingles.es");
		interesado.setTelefonoRepresentante("901433221");
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
	}
	
	/**
	 * Vacia todos los campos del primer interesado
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void vaciarInteresado(AsientoRegistralFormDTO asientoForm){
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(null);
		interesado.setDocumentoIdentificacionInteresado(null);
		interesado.setRazonSocialInteresado(null);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado(null);
		interesado.setCodigoProvinciaInteresado(null);
		interesado.setCodigoMunicipioInteresado(null);
		interesado.setDireccionInteresado(null);
		interesado.setCodigoPostalInteresado(null);
		interesado.setCorreoElectronicoInteresado(null);
		interesado.setTelefonoInteresado(null);
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(null);
		
	}
	
	/**
	 * Vacia todos los campos del representante del primer interesado
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void vaciarRepresentantesInteresado(AsientoRegistralFormDTO asientoForm){
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setDocumentoIdentificacionRepresentante(null);
		interesado.setNombreRepresentante(null);
		interesado.setPrimerApellidoRepresentante(null);
		interesado.setRazonSocialRepresentante(null);
		interesado.setSegundoApellidoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setTipoDocumentoIdentificacionRepresentante(null);
	}
	
	/**
	 * Vacia los campos del interesado, y rellena los campos para un 
	 * representante fisico
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar 
	 */
	private void setSinInteresadoConRepresentanteFisico(
			AsientoRegistralFormDTO asientoForm) {
		
		
		if (asientoForm.getInteresados().isEmpty()){
			asientoForm.getInteresados().add((new InteresadoFormDTO()));
		}
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		vaciarInteresado(asientoForm);

		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("Isidoro");
		interesado.setPrimerApellidoRepresentante("Álvarez");
		interesado.setSegundoApellidoRepresentante("Álvarez");
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante("Hermosilla, 112");
		interesado.setCodigoPostalRepresentante("28009");
		interesado.setCorreoElectronicoRepresentante("servicio_clientes@elcorteingles.es");
		interesado.setTelefonoRepresentante("901122122");
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
	}
	
	/**
	 * Cambia el primer interesado por un interesado fisico con representante
	 * fisico, utilizando valores de longitud maxima para cada campo a rellenar.
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoFisicoConRepresentanteFisicoLongitudMaxima(
			AsientoRegistralFormDTO asientoForm) {
		
		if (asientoForm.getInteresados().isEmpty()){
			asientoForm.getInteresados().add(new InteresadoFormDTO());
		}
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("11111111A");
		interesado.setRazonSocialInteresado(TestUtils.generaCadena("",80));
		interesado.setNombreInteresado(TestUtils.generaCadena("Antonio",30));
		interesado.setPrimerApellidoInteresado(TestUtils.generaCadena("Alonso",30));
		interesado.setSegundoApellidoInteresado(TestUtils.generaCadena("Mendiburu",30));
		interesado.setCodigoPaisInteresado("724");
		interesado.setCodigoProvinciaInteresado("28");
		interesado.setCodigoMunicipioInteresado("0796");
		interesado.setDireccionInteresado(TestUtils.generaCadena("Hermosilla, 112",160));
		interesado.setCodigoPostalInteresado("28009");
		interesado.setCorreoElectronicoInteresado(TestUtils.generaCadena("pruebas@elcorteingles.es",160));
		interesado.setTelefonoInteresado(TestUtils.generaCadena("901122122",20));
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante(TestUtils.generaCadena("00000000T",17));
		interesado.setRazonSocialRepresentante(TestUtils.generaCadena("",80));
		interesado.setNombreRepresentante(TestUtils.generaCadena("Isidoro",30));
		interesado.setPrimerApellidoRepresentante(TestUtils.generaCadena("Álvarez",30));
		interesado.setSegundoApellidoRepresentante(TestUtils.generaCadena("Álvarez",30));
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante(TestUtils.generaCadena("Hermosilla, 112",160));
		interesado.setCodigoPostalRepresentante("28009");
		interesado.setCorreoElectronicoRepresentante(TestUtils.generaCadena("servicio_clientes@elcorteingles.es",160));
		interesado.setTelefonoRepresentante(TestUtils.generaCadena("901122122",20));
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
	}
	
	/**
	 * Cambia el primer interesado por un interesado fisico con representante
	 * fisico, utilizando valores con caracteres especiales para cada campo a
	 * rellenar.
	 * 
	 * @param un asientoForm del asiento con la lista de interesados a modificar
	 */
	private void setInteresadoFisicoConRepresentanteFisicoCaracteresEspeciales(
			AsientoRegistralFormDTO asientoForm) {
		
		if (asientoForm.getInteresados().isEmpty()){
			asientoForm.getInteresados().add(new InteresadoFormDTO());
		}
		
		InteresadoFormDTO interesado = asientoForm.getInteresados().get(0);
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("11111111A");
		//interesado.setRazonSocialInteresado(TestUtils.generaCadenaCaracteresEspeciales("",80));
		interesado.setNombreInteresado(TestUtils.generaCadenaCaracteresEspeciales("Antonio ",30));
		interesado.setPrimerApellidoInteresado(TestUtils.generaCadenaCaracteresEspeciales("Alonso ",30));
		interesado.setSegundoApellidoInteresado(TestUtils.generaCadenaCaracteresEspeciales("Mendiburu ",30));
		interesado.setCodigoPaisInteresado("724");
		interesado.setCodigoProvinciaInteresado("28");
		interesado.setCodigoMunicipioInteresado("0796");
		interesado.setDireccionInteresado(TestUtils.generaCadenaCaracteresEspeciales("Hermosilla, 112 ",160));
		interesado.setCodigoPostalInteresado("28009");
		interesado.setCorreoElectronicoInteresado("pruebas@elcorteingles.es");
		interesado.setTelefonoInteresado(TestUtils.generaCadena("901122122",20));
		interesado.setDireccionElectronicaHabilitadaInteresado(null);
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
	//	interesado.setRazonSocialRepresentante(TestUtils.generaCadenaCaracteresEspeciales("",80));
		interesado.setNombreRepresentante(TestUtils.generaCadenaCaracteresEspeciales("Isidoro ",30));
		interesado.setPrimerApellidoRepresentante(TestUtils.generaCadenaCaracteresEspeciales("Álvarez ",30));
		interesado.setSegundoApellidoRepresentante(TestUtils.generaCadenaCaracteresEspeciales("Álvarez ",30));
		interesado.setCodigoPaisRepresentante("724");
		interesado.setCodigoProvinciaRepresentante("28");
		interesado.setCodigoMunicipioRepresentante("0796");
		interesado.setDireccionRepresentante(TestUtils.generaCadenaCaracteresEspeciales("Hermosilla, 112 ",160));
		interesado.setCodigoPostalRepresentante("28009");
		interesado.setCorreoElectronicoRepresentante("servicio_clientes@elcorteingles.es");
		interesado.setTelefonoRepresentante(TestUtils.generaCadena("901122122",20));
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
	}
		
	/**
	 * Crea el asiento registral con los valores por defecto, rellenando algunos
	 * campos con los parametros recibidos.
	 * Utilza valores con la longitud maxima según el campo a rellenar.
	 * Los parametros corresponden a tres secciones:
	 * - De_Origen_o_Remitente: numeroRegistro, codEntidadRegistralOrigen,
	 *   descEntidadRegistralOrigen,codUnidadTramitacionOrigen,
	 *   descUnidadTramitacionOrigen.
	 * - De_Destino: codEntidadRegistralDestino,descEntidadRegistralDestino,
	 *   codUnidadTramitacionDestino,descEntidadRegistralDestino,
	 *   codUnidadTramitacionDestino,descUnidadTramitacionDestino
	 * - De_Internos_Control: codEntidadRegistralInicial,
	 *   descEntidadRegistralInicial 
	 * 
	 * @param numeroRegistro Numero de registro de entrada
	 * @param codEntidadRegistralOrigen Codigo de entidad registral del origen
	 * @param descEntidadRegistralOrigen Descripcion de entidad registral origen
	 * @param codUnidadTramitacionOrigen Codigo de la unidad de tramitacion 
	 *        origen
	 * @param descUnidadTramitacionOrigen Descripcion de unidad de tramitacion
	 *        origen
	 * @param codEntidadRegistralDestino Codigo entidad registral destino
	 * @param descEntidadRegistralDestino Descripcioin de la entidad registral 
	 *        destino 
	 * @param codUnidadTramitacionDestino Codigo de la unidad de tramitacion
	 *        destino
	 * @param descUnidadTramitacionDestino Descripcion de la unidad de 
	 *        tramiacion destino
	 * @param codEntidadRegistralInicial Codigo de la entidad registral inicial
	 * @param descEntidadRegistralInicial Descripcion de la entidad registral
	 *        inicial. 
	 * @return Un objeto AsientoRegistralFormDTO correspondiente al asiento 
	 * generado
	 */
	private AsientoRegistralFormDTO createAsientoRegistralFormDTONumeroRegistroLongitudMaxima(
			String numeroRegistro, 
			String codEntidadRegistralOrigen, String descEntidadRegistralOrigen,
			String codUnidadTramitacionOrigen, String descUnidadTramitacionOrigen,
			String codEntidadRegistralDestino, String descEntidadRegistralDestino,
			String codUnidadTramitacionDestino, String descUnidadTramitacionDestino,
			String codEntidadRegistralInicial, String descEntidadRegistralInicial){
		AsientoRegistralFormDTO asientoForm=new AsientoRegistralFormDTO();
		
		asientoForm.setCodigoEntidadRegistral("O00002062");
		
		//Origen
		asientoForm.setCodigoEntidadRegistralOrigen(codEntidadRegistralOrigen);
		asientoForm.setDescripcionEntidadRegistralOrigen(descEntidadRegistralOrigen);
		asientoForm.setNumeroRegistro(TestUtils.generaCadena(numeroRegistro,20));
		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
		asientoForm.setTimestampRegistro(TestUtils.getTimestamp());
		asientoForm.setCodigoUnidadTramitacionOrigen(codUnidadTramitacionOrigen);
		asientoForm.setDescripcionUnidadTramitacionOrigen(descUnidadTramitacionOrigen);
		
		//Destino
		asientoForm.setCodigoEntidadRegistralDestino(codEntidadRegistralDestino);
		asientoForm.setDescripcionEntidadRegistralDestino(descEntidadRegistralDestino);
		asientoForm.setCodigoUnidadTramitacionDestino(codUnidadTramitacionDestino);
		asientoForm.setDescripcionEntidadRegistralDestino(descUnidadTramitacionDestino);
		
		//Interesado
		setInteresadoFisicoConRepresentanteFisicoLongitudMaxima(asientoForm);
		
		//Asunto
		asientoForm.setResumen(TestUtils.generaCadena("Esto es un resumen de 240 caracteres ",240));
		asientoForm.setCodigoAsunto(TestUtils.generaCadena("",16));
		asientoForm.setReferenciaExterna(TestUtils.generaCadena("ReferenciaExterna",16));
		asientoForm.setNumeroExpediente(TestUtils.generaCadena("",80));
		
		setDefaultAnexoLongitudMaxima(asientoForm);
		
		//Internos Control
		asientoForm.setTipoTransporte(TestUtils.generaCadena("01",2));
		asientoForm.setNumeroTransporte(TestUtils.generaCadena("",20));
		asientoForm.setNombreUsuario(TestUtils.generaCadena("Usuario 80 char ",80));
		asientoForm.setContactoUsuario(TestUtils.generaCadena("Contacto 160 char",160));
		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA.getValue());
		asientoForm.setObservacionesApunte(TestUtils.generaCadena("Observaciones apuntes 50 char ",50));
		asientoForm.setIndicadorPrueba("1");
		asientoForm.setCodigoEntidadRegistralInicio(codEntidadRegistralInicial);
		asientoForm.setDescripcionEntidadRegistralInicio(descEntidadRegistralInicial);
		
	    //Formulario generico
		asientoForm.setExpone(TestUtils.generaCadena("Expone de 4000 chars ",4000));
		asientoForm.setSolicita(TestUtils.generaCadena("Solicita de 4000 chars",4000));
		return asientoForm; 
	}
	
	/**
	 * Crea el asiento registral con los valores por defecto, rellenando algunos
	 * campos con los parametros recibidos.
	 * Utilza valores con caracteres especiales.
	 * Los parametros corresponden a tres secciones:
	 * - De_Origen_o_Remitente: numeroRegistro, codEntidadRegistralOrigen,
	 *   descEntidadRegistralOrigen,codUnidadTramitacionOrigen,
	 *   descUnidadTramitacionOrigen.
	 * - De_Destino: codEntidadRegistralDestino,descEntidadRegistralDestino,
	 *   codUnidadTramitacionDestino,descEntidadRegistralDestino,
	 *   codUnidadTramitacionDestino,descUnidadTramitacionDestino
	 * - De_Internos_Control: codEntidadRegistralInicial,
	 *   descEntidadRegistralInicial 
	 * 
	 * @param numeroRegistro Numero de registro de entrada
	 * @param codEntidadRegistralOrigen Codigo de entidad registral del origen
	 * @param descEntidadRegistralOrigen Descripcion de entidad registral origen
	 * @param codUnidadTramitacionOrigen Codigo de la unidad de tramitacion 
	 *        origen
	 * @param descUnidadTramitacionOrigen Descripcion de unidad de tramitacion
	 *        origen
	 * @param codEntidadRegistralDestino Codigo entidad registral destino
	 * @param descEntidadRegistralDestino Descripcioin de la entidad registral 
	 *        destino 
	 * @param codUnidadTramitacionDestino Codigo de la unidad de tramitacion
	 *        destino
	 * @param descUnidadTramitacionDestino Descripcion de la unidad de 
	 *        tramiacion destino
	 * @param codEntidadRegistralInicial Codigo de la entidad registral inicial
	 * @param descEntidadRegistralInicial Descripcion de la entidad registral
	 *        inicial. 
	 * @return Un objeto AsientoRegistralFormDTO correspondiente al asiento 
	 * generado
	 */
	private AsientoRegistralFormDTO createAsientoRegistralFormDTONumeroRegistroCaracteresEspeciales(
			String numeroRegistro, 
			String codEntidadRegistralOrigen, String descEntidadRegistralOrigen,
			String codUnidadTramitacionOrigen, String descUnidadTramitacionOrigen,
			String codEntidadRegistralDestino, String descEntidadRegistralDestino,
			String codUnidadTramitacionDestino, String descUnidadTramitacionDestino,
			String codEntidadRegistralInicial, String descEntidadRegistralInicial){
		AsientoRegistralFormDTO asientoForm=new AsientoRegistralFormDTO();
		
		asientoForm.setCodigoEntidadRegistral("O00002062");
		
		//Origen
		asientoForm.setCodigoEntidadRegistralOrigen(codEntidadRegistralOrigen);
		asientoForm.setDescripcionEntidadRegistralOrigen(codEntidadRegistralOrigen);
		asientoForm.setNumeroRegistro(numeroRegistro);
		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
		asientoForm.setTimestampRegistro(TestUtils.getTimestamp());
		asientoForm.setCodigoUnidadTramitacionOrigen(codUnidadTramitacionOrigen);
		asientoForm.setDescripcionUnidadTramitacionOrigen(descUnidadTramitacionOrigen);
		
		//Destino
		asientoForm.setCodigoEntidadRegistralDestino(codEntidadRegistralDestino);
		asientoForm.setDescripcionEntidadRegistralDestino(descEntidadRegistralDestino);
		asientoForm.setCodigoUnidadTramitacionDestino(codUnidadTramitacionDestino);
		asientoForm.setDescripcionEntidadRegistralDestino(descUnidadTramitacionDestino);
		
		//Interesado
		setInteresadoFisicoConRepresentanteFisicoCaracteresEspeciales(asientoForm);
		
		//Asunto
		asientoForm.setResumen(TestUtils.generaCadena("Resumen de 240 chars",240));
		asientoForm.setCodigoAsunto(TestUtils.generaCadena("",16));
		asientoForm.setReferenciaExterna(TestUtils.generaCadena("ReferenciaExterna",16));
		asientoForm.setNumeroExpediente(TestUtils.generaCadena("",80));
		
		setDefaultAnexoCaracteresEspeciales(asientoForm);
		
		//Internos Control
		asientoForm.setTipoTransporte(TestUtils.generaCadena("02",2));
		asientoForm.setNumeroTransporte(TestUtils.generaCadena("1",20));
		asientoForm.setNombreUsuario(TestUtils.generaCadena("Usuario ",80));
		asientoForm.setContactoUsuario(TestUtils.generaCadena("Contacto ",160));
		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA.getValue());
		asientoForm.setObservacionesApunte(TestUtils.generaCadena("Observaciones apunte",50));
		asientoForm.setIndicadorPrueba("1");
		asientoForm.setCodigoEntidadRegistralInicio(codEntidadRegistralInicial);
		asientoForm.setDescripcionEntidadRegistralInicio(descEntidadRegistralInicial);
		
	    //Formulario generico
		asientoForm.setExpone(TestUtils.generaCadena("Expone ",4000));
		asientoForm.setSolicita(TestUtils.generaCadena("Solicita ",4000));
		return asientoForm; 
	}
	
	/**
	 * Crea el anexo del asiento registral con los valores por defecto.
	 * 
	 * @param un objeto AnexoFormDTO con la información del anexo generado. 
	 * @return
	 */
	private AnexoFormDTO setDefaultAnexo(AsientoRegistralFormDTO asientoForm){
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero("NOMBRE_FICHERO_1.txt");
		anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());
		anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
		anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
		anexo.setFirma(Base64.decodeBase64("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"));
		anexo.setTimestamp(TestUtils.getTimestamp());
		anexo.setValidacionOCSPCertificado(Base64.decodeBase64("MIISEwoBAKCCEgwwghIIBgkrBgEFBQcwAQEEghH5MIIR9TCB8KFuMGwxCzAJBgNVBAYTAkVTMSgwJgYDVQQKEx9ESVJFQ0NJT04gR0VORVJBTCBERSBMQSBQT0xJQ0lBMQ0wCwYDVQQLEwRETklFMQ0wCwYDVQQLEwRGTk1UMRUwEwYDVQQDEwxBViBETklFIEZOTVQYDzIwMTEwNDE0MDg0ODU3WjBUMFIwPTAJBgUrDgMCGgUABBQ5wWwjxfbPTEC81LUU9wGoH/2B+wQUjkX0n3PF/y8bBdsBR2AbA4qBt7oCBESZ5ZOAABgPMjAxMTA0MTQwODMwNDRaoRcwFTATBgkrBgEFBQcwAQIEBgEvUzK6azANBgkqhkiG9w0BAQUFAAOCAQEAGbEAtXfUj3W/PCX//2NiSaPFgevoyOfmi6/w9WNsJmb476buDImlUTGETCdgh2O++YGwHV2RWhNvFUm0ZbR64nzKP8UA6b2/BCVeKm7yt9W3XgsqQsgShRO9/JcaQ7Qmw6SJgg/IFnvwqQ1K198h6xbfhSEoQKU+nk+MOrZaTWw1RUHiVhHS0Ow4MP2r51zY59D2QMQxWOyBNQ85b13zWVroNXf78HUbXxQQ9zXglzaI/8cXN5TwwYbKD0Oq1njOupELl2olpeItfVKxqsfgq8AQtGjBuOwG2h+eFJ1oXvbTxJxbkADqdl8G7lLYZKVvtDEz4pUxiOPqhh/xyIXqY6CCD+owgg/mMIIEVjCCAz6gAwIBAgIQVI8Eu5/R6L1Nkxl1yhX4MjANBgkqhkiG9w0BAQUFADBcMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEUMBIGA1UEAwwLQUMgRE5JRSAwMDEwHhcNMTEwMzMwMTE1MjIwWhcNMTEwOTMwMTE1MjIwWjBsMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTENMAsGA1UECwwERk5NVDEVMBMGA1UEAwwMQVYgRE5JRSBGTk1UMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyNCUPY9jgsHxDuNbqPr7ZVX7zmqPJsxlnOnD4xiu4ozP6PTYAuqE7MJz/7uyNAPMW5VtEXme5hQE8+qeTxu8VH4xZ5fdA0CReIiCJSyEWwmlE9KhT+ahZzHG5jFYCiI6RGZvU9mZSGNmlREiOz12y9TO0dI6L7JFF2kgiIbNR/uqI6yBn6SY8B4tX9zdHsFROd7oC+KI2e1bXhNt4amDem0BORYXbW0Yi+kGWKouXkDuQXudSlUaEl1YtbZzThYUvO0TFl7wKtyqh96UtGyKYovL/4R+3Yfr/FZKuC4UrLEwoEk5E/ECggJY6ZbHqah51fWP12Ty9xPINnieAZMZIQIDAQABo4IBAjCB/zAOBgNVHQ8BAf8EBAMCA8gwHQYDVR0OBBYEFLjPC1q7T2x0m8wS5Yj3bErr34eAMB8GA1UdIwQYMBaAFBqJqMXuj3ZdVXGJ8zs1vaoFAJVvMD8GCCsGAQUFBwEBBDMwMTAvBggrBgEFBQcwAoYjaHR0cDovL3d3dy5kbmllLmVzL2NlcnRzL0FDUmFpei5jcnQwEwYDVR0lBAwwCgYIKwYBBQUHAwkwDwYJKwYBBQUHMAEFBAIFADA7BgNVHSAENDAyMDAGCGCFVAECAgIFMCQwIgYIKwYBBQUHAgEWFmh0dHA6Ly93d3cuZG5pZS5lcy9kcGMwCQYDVR0TBAIwADANBgkqhkiG9w0BAQUFAAOCAQEAQ+OaNXH0kmGfcqILEduvzQyv0GVo72psyxhWQ5qRnBhJboEFTyFrU9K00AE4ZTTzaN+oW9Y9NT8v6eYvMLXImA09w4XaOQ4fQ65W5kGSlMuMUr46tVYE8P7J0dCaux/x92E4bMVFlNOj9XkIBEvP6PGnkt0aaXThoNB3kbCN3x+eHpTPkrxWNQsvohpdsU0ldt42NpL2krT8DSZZGej/t2XrL+EyriGYVYeUp6TNAQ3LTW/EfP8RuIPMET/0h55irGr8pV2yup5w87NsLUdRuNIr4nNOpvqO5xKqS6Sw59GlSpjTV7LyskKEbaWgRplO+M4Na1J++LQEKklUlvD3aDCCBcUwggOtoAMCAQICEGQgZsmZe67hRALabqQi1kkwDQYJKoZIhvcNAQEFBQAwXTELMAkGA1UEBhMCRVMxKDAmBgNVBAoMH0RJUkVDQ0lPTiBHRU5FUkFMIERFIExBIFBPTElDSUExDTALBgNVBAsMBEROSUUxFTATBgNVBAMMDEFDIFJBSVogRE5JRTAeFw0wNjAyMjcxMDU0MzhaFw0yMTAyMjYyMjU5NTlaMFwxCzAJBgNVBAYTAkVTMSgwJgYDVQQKDB9ESVJFQ0NJT04gR0VORVJBTCBERSBMQSBQT0xJQ0lBMQ0wCwYDVQQLDARETklFMRQwEgYDVQQDDAtBQyBETklFIDAwMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKz+SpnS5Kf9jQ/ue4y51/uJNX/Omd+kxq78tzLgDVfn7fDWzTsWXKXBkj8OlmGvptMyn4UPrEBEx4TBzKOxm+p5YagpjU6U3Y9+StAZ+Zmi50xPBfP6f+qU/Qi6xD2FeXl+zmzQoqpNr57lkDHFQMEV9O+0GBijc1vfKfSsYquJmGUUdDm58WDXetVg3Mznbj+Qv3VCKxpNyXxSyDSxldHf/RGnh5e845MzmMgknBqvrZa5ClYFugUMCP4F8OF0WDYRYwgD82/HGi3r8UMRp80VGfHUzlDnqoDmdRV3zbooOyqHpOKpHckCqMGDaeEtzcHrrr27GzyWalcwqs8AqvcCAwEAAaOCAYAwggF8MBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFBqJqMXuj3ZdVXGJ8zs1vaoFAJVvMB8GA1UdIwQYMBaAFI5F9J9zxf8vGwXbAUdgGwOKgbe6MA4GA1UdDwEB/wQEAwIBBjA3BgNVHSAEMDAuMCwGBFUdIAAwJDAiBggrBgEFBQcCARYWaHR0cDovL3d3dy5kbmllLmVzL2RwYzCB3AYDVR0fBIHUMIHRMIHOoIHLoIHIhiBodHRwOi8vY3Jscy5kbmllLmVzL2NybHMvQVJMLmNybIaBo2xkYXA6Ly9sZGFwLmRuaWUuZXMvQ049Q1JMLENOPUFDJTIwUkFJWiUyMEROSUUsT1U9RE5JRSxPPURJUkVDQ0lPTiUyMEdFTkVSQUwlMjBERSUyMExBJTIwUE9MSUNJQSxDPUVTP2F1dGhvcml0eVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADggIBAGdY/2uyHzpnCf4HqyLlGJwRJOh25dOaY0k7Wjn97fAsUufeONInnRg3dm7gGx1aFJSt3eEPJ79KrLNFzJ+graO+QhZ9hIcsWXpOng425gVHCu1TxFpIzHUGVIwyMWmuJn28TBgPgcdkn39M43HbLsRJw82F5q/ZZT+36l/PPezEWqCj+SpO2GnLuPY67T1GEwrQB7p2Q8rbBkji7fE4RQ2ovJCLPB3/HYibwVBo8vRZUnqKn4rgL4HTybVZzXncbRHqOf6BnN/vS9NjxqqOKD4MbRQnCdjpbDL4eY8X/Flv6HJJ1bqTbXmyqtO9kO9qCyAsvb2kQrecRk/pVNKEWF0/jeNXnh2FuUhzElas23NrieKWMTBZY08eYj9DbCrDsDeoq/zspx7BzWwvYJY9dD4kkJDHVPKg8oG3/+DhWMM/+EaM4WjFMw9mF7YjqRfFOUcnohBw2dJQpCeqRre3Biz4S8s6mHfIs2E7VFVzZiRq4+eH9QBuFCosfSMPF89VsgI20ro/GZ/OBT8iK7ICLZX5LRkoDMRdfOW2s8ULR8qL+XgaLIvR/jwZfgj3kNtzsybQiuBbLHaZT3TAQHh2hiJ8S2ab7daoyZzSLNEGlysh4+j3KDdTA4GKOclgI9WTxeEocYlOkwRx/UscLbTZsZChZDWBcE95yqvY+uikdwtnMIIFvzCCA6egAwIBAgIQANKFcP2up9ZfEYQVxjG1yzANBgkqhkiG9w0BAQUFADBdMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEVMBMGA1UEAwwMQUMgUkFJWiBETklFMB4XDTA2MDIxNjEwMzcyNVoXDTM2MDIwODIyNTk1OVowXTELMAkGA1UEBhMCRVMxKDAmBgNVBAoMH0RJUkVDQ0lPTiBHRU5FUkFMIERFIExBIFBPTElDSUExDTALBgNVBAsMBEROSUUxFTATBgNVBAMMDEFDIFJBSVogRE5JRTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAIAArQzDoyAHo2P/9zSgze5qVAgXXbEBFafmuV+Kcf8Mwh3qN/Pek3/WBU2EstXXHAz0xJFwQA5ayJikgOgNM8AH87f1rKE4esBmVCT8UswwKvLDxKEsdr/BwL+C8ZvwaHoTQMiXvBwlBwgKt5bvzClU4OZlLeqyLrEJaRJOMNXY+LwAgC9Nkw/NLlcbM7ufME7Epct5p/viNBi2IJ4bn12nyTqtRWSzGM4REpxtHlVFKIScV2dN+cvii49YCdQ5/8g20jjiDGV/FQ59wQfdqSLfkQDEbHE0dNw56upPRGl/WNtYClJxK+ypHVB0M/kpavr+mfTnzEVFbcpaJaIS487XOAU58BoJ9XZZzmJvejQNLNG8BBLsPVPI+tACy849IbXF4DkzZc85U8mbRvmdM/NZgAhBvm9LoPpKzqR2HIXir68UnWWs93+X5DNJpq++zis38S7BcwWcnGBMnTANl1SegWK75+Av9xQHFKl3kenckZWO04iQM0dvccMUafqmLQEeG+rTLuJ/C9zP5yLw8UGjAZLlgNO+qWKoVYgLNDTs3CEVqu/WIl6J9VGSEypvgBbZsQ3ZLvgQuML+UkUznB04fNwVaTRzv6AsuxF7lM34Ny1vPe+DWsYem3RJj9nCjb4WdlDIWtElFvb2zIycWjCeZb7QmkiT1/poDXUxh/n3AgMBAAGjezB5MA8GA1UdEwEB/wQFMAMBAf8wDgYDVR0PAQH/BAQDAgEGMB0GA1UdDgQWBBSORfSfc8X/LxsF2wFHYBsDioG3ujA3BgNVHSAEMDAuMCwGBFUdIAAwJDAiBggrBgEFBQcCARYWaHR0cDovL3d3dy5kbmllLmVzL2RwYzANBgkqhkiG9w0BAQUFAAOCAgEAdeVzyVFRL4sZoIfp/642Nqb8QR/jHtdxYBnGb5oCML1ica1z/pEtTuQmQESprngmIzFp3Jpzlh5JUQvg78G4Q+9xnO5Bt8VQHzKEniKG8fcfj9mtK07alyiXu5aaGvix2XoE81SZEhmWFYBnOf8CX3r8VUJQWua5ov+4qGIeFM3ZP76jZUjFO9c3zg36KJDav/njUUclfUrTZ02HqmK8Xux6gER8958KvWVXlMryEWbWUn/kOnB1BM07l9Q2cvdRVr809dJB4bTaqEP+axJJErRdzyJClowIIyaMshBOXapT7gEvdeW5ohEzxNdq/fgOym6C2ee7WSNOtfkRHS9rI/V7ESDqQRKQMkbbMTupwVtzaDpGG4z+l7dWuWGZzE7wg/o38d4cnRxxiwOTw8Rzgi6omB1kopqM91QITc/qgcv1WwmZY691jJb4eTXV3OtBgXk4hF5v8W9idtuRzlqFYDkdW+IqL0Ml28J6JNMVsKLxjKB9a0gJE/+iTGaK7HBSCVOMMMy41bok3DCZPqFet9+BrOw3vk6bJ1jefqGbVH8Gti/kMlD95xC7qM3aGBvUY2Y96lFxOfScPt9a9NrHTCbti7UhujR5AnNhENqYMahgy34Hp9C3BUOJW82FJtmwUa/3jFKqEqdY35KbZ/Kd8ub0aTH0Fufed1se3ZoFAa0="));
		anexo.setTipoMIME("text/plain");
		anexo.setIdentificadorFicheroFirmado(null);
		anexo.setObservaciones("OBSERVACIONES 1");
		anexo.setContenido("Contenido del anexo".getBytes());
		anexo.setCodigoFichero("CODIGO_FICHERO_1");
		asientoForm.getAnexos().add(anexo);
		//anexoFormDTO.setCodigoFicheroFirmado(TestUtils.generaCadena("",50));
		return anexo;
	}
	
	/**
	 * Crea el anexo del asiento registral con los valores por defecto.
	 * Utiliza valores de longitud máxima, según el campo a rellenar. 
	 * 
	 * @param un objeto AnexoFormDTO con la información del anexo generado. 
	 * @return
	 */
	private AnexoFormDTO setDefaultAnexoLongitudMaxima(AsientoRegistralFormDTO asientoForm){
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero(TestUtils.generaCadena("NOMBRE_FICHERO_1",76)+".txt");
		anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());
		anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
		anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
		anexo.setFirma(Base64.decodeBase64("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"));
		anexo.setTimestamp(TestUtils.getTimestamp());
		anexo.setValidacionOCSPCertificado(Base64.decodeBase64("MIISEwoBAKCCEgwwghIIBgkrBgEFBQcwAQEEghH5MIIR9TCB8KFuMGwxCzAJBgNVBAYTAkVTMSgwJgYDVQQKEx9ESVJFQ0NJT04gR0VORVJBTCBERSBMQSBQT0xJQ0lBMQ0wCwYDVQQLEwRETklFMQ0wCwYDVQQLEwRGTk1UMRUwEwYDVQQDEwxBViBETklFIEZOTVQYDzIwMTEwNDE0MDg0ODU3WjBUMFIwPTAJBgUrDgMCGgUABBQ5wWwjxfbPTEC81LUU9wGoH/2B+wQUjkX0n3PF/y8bBdsBR2AbA4qBt7oCBESZ5ZOAABgPMjAxMTA0MTQwODMwNDRaoRcwFTATBgkrBgEFBQcwAQIEBgEvUzK6azANBgkqhkiG9w0BAQUFAAOCAQEAGbEAtXfUj3W/PCX//2NiSaPFgevoyOfmi6/w9WNsJmb476buDImlUTGETCdgh2O++YGwHV2RWhNvFUm0ZbR64nzKP8UA6b2/BCVeKm7yt9W3XgsqQsgShRO9/JcaQ7Qmw6SJgg/IFnvwqQ1K198h6xbfhSEoQKU+nk+MOrZaTWw1RUHiVhHS0Ow4MP2r51zY59D2QMQxWOyBNQ85b13zWVroNXf78HUbXxQQ9zXglzaI/8cXN5TwwYbKD0Oq1njOupELl2olpeItfVKxqsfgq8AQtGjBuOwG2h+eFJ1oXvbTxJxbkADqdl8G7lLYZKVvtDEz4pUxiOPqhh/xyIXqY6CCD+owgg/mMIIEVjCCAz6gAwIBAgIQVI8Eu5/R6L1Nkxl1yhX4MjANBgkqhkiG9w0BAQUFADBcMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEUMBIGA1UEAwwLQUMgRE5JRSAwMDEwHhcNMTEwMzMwMTE1MjIwWhcNMTEwOTMwMTE1MjIwWjBsMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTENMAsGA1UECwwERk5NVDEVMBMGA1UEAwwMQVYgRE5JRSBGTk1UMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyNCUPY9jgsHxDuNbqPr7ZVX7zmqPJsxlnOnD4xiu4ozP6PTYAuqE7MJz/7uyNAPMW5VtEXme5hQE8+qeTxu8VH4xZ5fdA0CReIiCJSyEWwmlE9KhT+ahZzHG5jFYCiI6RGZvU9mZSGNmlREiOz12y9TO0dI6L7JFF2kgiIbNR/uqI6yBn6SY8B4tX9zdHsFROd7oC+KI2e1bXhNt4amDem0BORYXbW0Yi+kGWKouXkDuQXudSlUaEl1YtbZzThYUvO0TFl7wKtyqh96UtGyKYovL/4R+3Yfr/FZKuC4UrLEwoEk5E/ECggJY6ZbHqah51fWP12Ty9xPINnieAZMZIQIDAQABo4IBAjCB/zAOBgNVHQ8BAf8EBAMCA8gwHQYDVR0OBBYEFLjPC1q7T2x0m8wS5Yj3bErr34eAMB8GA1UdIwQYMBaAFBqJqMXuj3ZdVXGJ8zs1vaoFAJVvMD8GCCsGAQUFBwEBBDMwMTAvBggrBgEFBQcwAoYjaHR0cDovL3d3dy5kbmllLmVzL2NlcnRzL0FDUmFpei5jcnQwEwYDVR0lBAwwCgYIKwYBBQUHAwkwDwYJKwYBBQUHMAEFBAIFADA7BgNVHSAENDAyMDAGCGCFVAECAgIFMCQwIgYIKwYBBQUHAgEWFmh0dHA6Ly93d3cuZG5pZS5lcy9kcGMwCQYDVR0TBAIwADANBgkqhkiG9w0BAQUFAAOCAQEAQ+OaNXH0kmGfcqILEduvzQyv0GVo72psyxhWQ5qRnBhJboEFTyFrU9K00AE4ZTTzaN+oW9Y9NT8v6eYvMLXImA09w4XaOQ4fQ65W5kGSlMuMUr46tVYE8P7J0dCaux/x92E4bMVFlNOj9XkIBEvP6PGnkt0aaXThoNB3kbCN3x+eHpTPkrxWNQsvohpdsU0ldt42NpL2krT8DSZZGej/t2XrL+EyriGYVYeUp6TNAQ3LTW/EfP8RuIPMET/0h55irGr8pV2yup5w87NsLUdRuNIr4nNOpvqO5xKqS6Sw59GlSpjTV7LyskKEbaWgRplO+M4Na1J++LQEKklUlvD3aDCCBcUwggOtoAMCAQICEGQgZsmZe67hRALabqQi1kkwDQYJKoZIhvcNAQEFBQAwXTELMAkGA1UEBhMCRVMxKDAmBgNVBAoMH0RJUkVDQ0lPTiBHRU5FUkFMIERFIExBIFBPTElDSUExDTALBgNVBAsMBEROSUUxFTATBgNVBAMMDEFDIFJBSVogRE5JRTAeFw0wNjAyMjcxMDU0MzhaFw0yMTAyMjYyMjU5NTlaMFwxCzAJBgNVBAYTAkVTMSgwJgYDVQQKDB9ESVJFQ0NJT04gR0VORVJBTCBERSBMQSBQT0xJQ0lBMQ0wCwYDVQQLDARETklFMRQwEgYDVQQDDAtBQyBETklFIDAwMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKz+SpnS5Kf9jQ/ue4y51/uJNX/Omd+kxq78tzLgDVfn7fDWzTsWXKXBkj8OlmGvptMyn4UPrEBEx4TBzKOxm+p5YagpjU6U3Y9+StAZ+Zmi50xPBfP6f+qU/Qi6xD2FeXl+zmzQoqpNr57lkDHFQMEV9O+0GBijc1vfKfSsYquJmGUUdDm58WDXetVg3Mznbj+Qv3VCKxpNyXxSyDSxldHf/RGnh5e845MzmMgknBqvrZa5ClYFugUMCP4F8OF0WDYRYwgD82/HGi3r8UMRp80VGfHUzlDnqoDmdRV3zbooOyqHpOKpHckCqMGDaeEtzcHrrr27GzyWalcwqs8AqvcCAwEAAaOCAYAwggF8MBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFBqJqMXuj3ZdVXGJ8zs1vaoFAJVvMB8GA1UdIwQYMBaAFI5F9J9zxf8vGwXbAUdgGwOKgbe6MA4GA1UdDwEB/wQEAwIBBjA3BgNVHSAEMDAuMCwGBFUdIAAwJDAiBggrBgEFBQcCARYWaHR0cDovL3d3dy5kbmllLmVzL2RwYzCB3AYDVR0fBIHUMIHRMIHOoIHLoIHIhiBodHRwOi8vY3Jscy5kbmllLmVzL2NybHMvQVJMLmNybIaBo2xkYXA6Ly9sZGFwLmRuaWUuZXMvQ049Q1JMLENOPUFDJTIwUkFJWiUyMEROSUUsT1U9RE5JRSxPPURJUkVDQ0lPTiUyMEdFTkVSQUwlMjBERSUyMExBJTIwUE9MSUNJQSxDPUVTP2F1dGhvcml0eVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADggIBAGdY/2uyHzpnCf4HqyLlGJwRJOh25dOaY0k7Wjn97fAsUufeONInnRg3dm7gGx1aFJSt3eEPJ79KrLNFzJ+graO+QhZ9hIcsWXpOng425gVHCu1TxFpIzHUGVIwyMWmuJn28TBgPgcdkn39M43HbLsRJw82F5q/ZZT+36l/PPezEWqCj+SpO2GnLuPY67T1GEwrQB7p2Q8rbBkji7fE4RQ2ovJCLPB3/HYibwVBo8vRZUnqKn4rgL4HTybVZzXncbRHqOf6BnN/vS9NjxqqOKD4MbRQnCdjpbDL4eY8X/Flv6HJJ1bqTbXmyqtO9kO9qCyAsvb2kQrecRk/pVNKEWF0/jeNXnh2FuUhzElas23NrieKWMTBZY08eYj9DbCrDsDeoq/zspx7BzWwvYJY9dD4kkJDHVPKg8oG3/+DhWMM/+EaM4WjFMw9mF7YjqRfFOUcnohBw2dJQpCeqRre3Biz4S8s6mHfIs2E7VFVzZiRq4+eH9QBuFCosfSMPF89VsgI20ro/GZ/OBT8iK7ICLZX5LRkoDMRdfOW2s8ULR8qL+XgaLIvR/jwZfgj3kNtzsybQiuBbLHaZT3TAQHh2hiJ8S2ab7daoyZzSLNEGlysh4+j3KDdTA4GKOclgI9WTxeEocYlOkwRx/UscLbTZsZChZDWBcE95yqvY+uikdwtnMIIFvzCCA6egAwIBAgIQANKFcP2up9ZfEYQVxjG1yzANBgkqhkiG9w0BAQUFADBdMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEVMBMGA1UEAwwMQUMgUkFJWiBETklFMB4XDTA2MDIxNjEwMzcyNVoXDTM2MDIwODIyNTk1OVowXTELMAkGA1UEBhMCRVMxKDAmBgNVBAoMH0RJUkVDQ0lPTiBHRU5FUkFMIERFIExBIFBPTElDSUExDTALBgNVBAsMBEROSUUxFTATBgNVBAMMDEFDIFJBSVogRE5JRTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAIAArQzDoyAHo2P/9zSgze5qVAgXXbEBFafmuV+Kcf8Mwh3qN/Pek3/WBU2EstXXHAz0xJFwQA5ayJikgOgNM8AH87f1rKE4esBmVCT8UswwKvLDxKEsdr/BwL+C8ZvwaHoTQMiXvBwlBwgKt5bvzClU4OZlLeqyLrEJaRJOMNXY+LwAgC9Nkw/NLlcbM7ufME7Epct5p/viNBi2IJ4bn12nyTqtRWSzGM4REpxtHlVFKIScV2dN+cvii49YCdQ5/8g20jjiDGV/FQ59wQfdqSLfkQDEbHE0dNw56upPRGl/WNtYClJxK+ypHVB0M/kpavr+mfTnzEVFbcpaJaIS487XOAU58BoJ9XZZzmJvejQNLNG8BBLsPVPI+tACy849IbXF4DkzZc85U8mbRvmdM/NZgAhBvm9LoPpKzqR2HIXir68UnWWs93+X5DNJpq++zis38S7BcwWcnGBMnTANl1SegWK75+Av9xQHFKl3kenckZWO04iQM0dvccMUafqmLQEeG+rTLuJ/C9zP5yLw8UGjAZLlgNO+qWKoVYgLNDTs3CEVqu/WIl6J9VGSEypvgBbZsQ3ZLvgQuML+UkUznB04fNwVaTRzv6AsuxF7lM34Ny1vPe+DWsYem3RJj9nCjb4WdlDIWtElFvb2zIycWjCeZb7QmkiT1/poDXUxh/n3AgMBAAGjezB5MA8GA1UdEwEB/wQFMAMBAf8wDgYDVR0PAQH/BAQDAgEGMB0GA1UdDgQWBBSORfSfc8X/LxsF2wFHYBsDioG3ujA3BgNVHSAEMDAuMCwGBFUdIAAwJDAiBggrBgEFBQcCARYWaHR0cDovL3d3dy5kbmllLmVzL2RwYzANBgkqhkiG9w0BAQUFAAOCAgEAdeVzyVFRL4sZoIfp/642Nqb8QR/jHtdxYBnGb5oCML1ica1z/pEtTuQmQESprngmIzFp3Jpzlh5JUQvg78G4Q+9xnO5Bt8VQHzKEniKG8fcfj9mtK07alyiXu5aaGvix2XoE81SZEhmWFYBnOf8CX3r8VUJQWua5ov+4qGIeFM3ZP76jZUjFO9c3zg36KJDav/njUUclfUrTZ02HqmK8Xux6gER8958KvWVXlMryEWbWUn/kOnB1BM07l9Q2cvdRVr809dJB4bTaqEP+axJJErRdzyJClowIIyaMshBOXapT7gEvdeW5ohEzxNdq/fgOym6C2ee7WSNOtfkRHS9rI/V7ESDqQRKQMkbbMTupwVtzaDpGG4z+l7dWuWGZzE7wg/o38d4cnRxxiwOTw8Rzgi6omB1kopqM91QITc/qgcv1WwmZY691jJb4eTXV3OtBgXk4hF5v8W9idtuRzlqFYDkdW+IqL0Ml28J6JNMVsKLxjKB9a0gJE/+iTGaK7HBSCVOMMMy41bok3DCZPqFet9+BrOw3vk6bJ1jefqGbVH8Gti/kMlD95xC7qM3aGBvUY2Y96lFxOfScPt9a9NrHTCbti7UhujR5AnNhENqYMahgy34Hp9C3BUOJW82FJtmwUa/3jFKqEqdY35KbZ/Kd8ub0aTH0Fufed1se3ZoFAa0="));
		anexo.setTipoMIME(TestUtils.generaCadena("text/plain",20));
		anexo.setIdentificadorFicheroFirmado(null);
		anexo.setObservaciones(TestUtils.generaCadena("OBSERVACIONES 1",50));
		anexo.setContenido("Contenido del anexo".getBytes());
		anexo.setCodigoFichero(TestUtils.generaCadena("CODIGO_FICHERO_1",50));
		asientoForm.getAnexos().add(anexo);
		//anexoFormDTO.setCodigoFicheroFirmado(TestUtils.generaCadena("",50));
		return anexo;
	}
	
	/**
	 * Crea el anexo del asiento registral con los valores por defecto.
	 * Utiliza valores con caracteres especiales, según el campo a rellenar. 
	 * 
	 * @param un objeto AnexoFormDTO con la información del anexo generado. 
	 * @return
	 */
	private AnexoFormDTO setDefaultAnexoCaracteresEspeciales(AsientoRegistralFormDTO asientoForm){
		AnexoFormDTO anexo = new AnexoFormDTO();
		anexo.setNombreFichero(TestUtils.generaCadenaCaracteresEspeciales("NOMBRE_FICHERO_1",76)+".txt");
		anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());
		anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
		anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
		anexo.setFirma(Base64.decodeBase64("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"));
		anexo.setTimestamp(TestUtils.getTimestamp());
		anexo.setValidacionOCSPCertificado(Base64.decodeBase64("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"));
		anexo.setTipoMIME("text/plain");
		anexo.setIdentificadorFicheroFirmado(null);
		anexo.setObservaciones(TestUtils.generaCadenaCaracteresEspeciales("OBSERVACIONES 1",50));
		anexo.setContenido(TestUtils.generaCadenaCaracteresEspeciales("Contenido del anexo",100).getBytes());
		anexo.setCodigoFichero(TestUtils.generaCadenaCaracteresEspeciales("CODIGO_FICHERO_1",50));
		asientoForm.getAnexos().add(anexo);
		//anexoFormDTO.setCodigoFicheroFirmado(TestUtils.generaCadena("",50));
		return anexo;
	}
}
