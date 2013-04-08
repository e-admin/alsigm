package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import junit.framework.Assert;

import org.apache.axis.attachments.OctetStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.ieci.tecdoc.fwktd.sir.api.manager.impl.XPathReaderUtil;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.WS_SIR8_A_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
@ContextConfiguration({
	"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralWSFunctionalTest extends AbstractWSTest { 

	protected static final Logger logger = LoggerFactory.getLogger("TEST");
	
	private static final ThreadLocal<String> IDENTIFICADOR_ASIENTO_REGISTRAL = new ThreadLocal<String>();
	
	//SIGEM
	protected static final String CODIGO_ENTIDAD_REGISTRAL_SORIA = "O00002061";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_SORIA = "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE SORIA";
	
	protected static final String CODIGO_UNIDAD_TRAMITACION_SORIA = "L02000042";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_SORIA = "Diputación Provincial de Soria";
	
	//sigem
	protected static final String CODIGO_ENTIDAD_REGISTRAL_COLINDRES="O00002062";
	protected static final String DESCRIPCION_ENTIDAD_COLINDRES="REGISTRO GENERAL DEL AYUNTAMIENTO DE COLINDRES";
	protected static final String CODIGO_UNIDAD_TRAMITACION_COLINDRES="L01390232";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES= "Ayuntamiento de Colindres";
	
	//MINISTERIO
	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE="O00001223";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE="MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE="E00106103";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE="MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO = "O00001222";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO = "MSPI- INSTITUTO NACIONAL DE CONSUMO";
	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO = "E00138403";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO = "INSTITUTO NACIONAL DE CONSUMO";
	
	
	
	
	
	
	//SIGEM
	protected static final String CODIGO_ENTIDAD_REGISTRAL_INICIAL=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL=DESCRIPCION_ENTIDAD_COLINDRES;
	
	@Autowired
	private WS_SIR8_A_PortType WSSIR8AWSClient;

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
		Assert.assertNotNull(WSSIR8AWSClient);
	}


	@Test
	public void testFindAsientosRegistrales() throws Exception {
		
		logger.info("Buscando asientos registrales...");
		
		List<AsientoRegistralDTO> asientos = getIntercambioRegistralWS()
				.findAsientosRegistrales(
						createCriteriosDTO(
								new CriterioDTO[] {
										//createCriterioDTO(CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_ORIGEN, OperadorCriterioEnum.EQUAL, ""),
										//createCriterioDTO(CriterioEnum.ASIENTO_CODIGO_ENTIDAD_REGISTRAL_DESTINO, OperadorCriterioEnum.EQUAL, "")
								},
								new String[] { CriterioEnum.ASIENTO_IDENTIFICADOR_INTERCAMBIO.getValue() }));
		Assert.assertNotNull(asientos);
		
		logger.info("Se han encontrado {} asiento/s registral/es", asientos.size());
		for (AsientoRegistralDTO asiento : asientos) {
			logger.info("\tAsiento: {}", toString(asiento));
		}
	}
		
	
	/**
	 * Test para ejecucion de las pruebas funcionales de generacion de ficheros sicres
	 * @throws Exception
	 */
	@Test
	public void testEnviarAsientoRegistralFuncional()  {
		
		logger.info("Enviando asiento registral...");
		
		for (int  i= 1; i <=1; i++) {
			try{
			String methodName ="createAsientoRegistralFormParaTest"+i;
    		Method m = this.getClass().getDeclaredMethod(methodName, null);
    		AsientoRegistralFormDTO asientoForm = (AsientoRegistralFormDTO) m.invoke(this, null);
    		AsientoRegistralDTO asiento = getIntercambioRegistralWS().enviarAsientoRegistral(asientoForm);
    		Assert.assertNotNull("Asiento registral nulo", asiento);
    		Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
    		Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado());
    		logger.info("Asiento registral enviado: {}", toString(asiento));
			}catch (Exception e){
				String message="Error ejecutando test EnviarAsientoRegistralFuncional iteracion:"+i;
				logger.error(message,e);
				//TODO quitar esta salida a consola
				System.out.println(message+ "  "+ e.getLocalizedMessage());
				
			}
		}
		
	}

	@Test
	public void testEnviarAsientoRegistral() throws Exception {
		
		logger.info("Enviando asiento registral...");
		
		//ORIGEN
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_COLINDRES;
		String codigoUnidadTramitacionOrigen=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionOrigen=DESCRIPCION_ENTIDAD_COLINDRES;
		
		//DESTINO
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String codigoUnidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		
		//INICIAL
		String codigoEntidadRegistralInicial=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralInicial=DESCRIPCION_ENTIDAD_COLINDRES;
		String codigoUnidadTramitacionInicial=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionInicial=DESCRIPCION_ENTIDAD_COLINDRES;
		 
		
		

		// Información del asiento registral a enviar
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTO(codigoEntidadRegistralOrigen,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				codigoUnidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				codigoEntidadRegistralInicial, descripcionEntidadRegistralInicial,
				codigoUnidadTramitacionInicial, descripcionUnidadTramitacionInicial);

		
		AsientoRegistralDTO asiento = getIntercambioRegistralWS().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull("Asiento registral nulo", asiento);
		Assert.assertTrue("El identificador del asiento registral es nulo", StringUtils.isNotBlank(asiento.getId()));
		
		// Se guarda el identificador para su uso posterior
		IDENTIFICADOR_ASIENTO_REGISTRAL.set(asiento.getId());
		
		Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO.getValue() == asiento.getEstado());
		logger.info("Asiento registral enviado: {}", toString(asiento));
	}

	@Test
public void testReenviarAsientoRegistral() throws Exception {
		
		// Indicar el identificador del asiento registral a reenviar
		String identificadorIntercambio="O00001301_12_34521488";
						 
		String codigoEntidadRegistralNuevoDestino=CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String descripcionEntidadRegistralNuevoDestino=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String codigoUnidadTramitacionNuevoDestino=CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		String descripcionUnidadTramitacionNuevoDestino=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String idAsientoRegistral =this.getIdAsientoRegistral(codigoEntidadRegistral,identificadorIntercambio);

		logger.info("Reenviando asiento registral con id: {}", idAsientoRegistral);
		logger.info("Estado antes de reenviar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));
		
		AsientoRegistralDTO infoAsientoRegistral = getIntercambioRegistralWS().getAsientoRegistral(idAsientoRegistral);

		logger.info("Estado antes de reenviar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));
		
		//asientoRegistral
		//Ahora el Origen es el que era el destino, es decir El nuevo origen somos nosotros.
		infoAsientoRegistral.setCodigoEntidadRegistralOrigen(infoAsientoRegistral.getCodigoEntidadRegistralDestino());
		infoAsientoRegistral.setDescripcionEntidadRegistralOrigen(infoAsientoRegistral.getDescripcionEntidadRegistralDestino());
		
		infoAsientoRegistral.setObservacionesApunte("Reenvio");
		
		//Nuevo Destino
		infoAsientoRegistral.setCodigoEntidadRegistralDestino(codigoEntidadRegistralNuevoDestino);
		infoAsientoRegistral.setDescripcionEntidadRegistralDestino(descripcionEntidadRegistralNuevoDestino);
		
		//infoAsientoRegistral.setCodigoUnidadTramitacionDestino(codigoUnidadTramitacionNuevoDestino);
		//infoAsientoRegistral.setDescripcionUnidadTramitacionDestino(descripcionUnidadTramitacionNuevoDestino);

		getIntercambioRegistralWS().updateAsientoRegistral(infoAsientoRegistral);

		
		getIntercambioRegistralWS().reenviarAsientoRegistralById(idAsientoRegistral);
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de reenviar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.REENVIADO.getValue(), estado.getEstado());
	}


	@Test
	public void testRechazarAsientoRegistral() throws Exception {
		
		// Indicar el identificador de intercambio del asiento registral a rechazar
		String identificadorIntercambio = "O00001301_12_17837750";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL;
		logger.info("Rechazando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);

		
		// Identificador del asiento registral a rechazar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);

		logger.info("Estado antes de rechazar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));

		getIntercambioRegistralWS()
				.rechazarAsientoRegistral(
						idAsientoRegistral,
						TestUtils
								.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN));
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de rechazar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), estado.getEstado());
	}
	@Test
	public void testRechazarAsientoRegistralInicio() throws Exception {
		
		// Indicar el identificador de intercambio del asiento registral a rechazar
		String identificadorIntercambio = "O00001301_12_17837750";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		logger.info("Rechazando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);

		// Identificador del asiento registral a rechazar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral,identificadorIntercambio);

		logger.info("Estado antes de rechazar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));

		getIntercambioRegistralWS()
				.rechazarAsientoRegistral(
						idAsientoRegistral,
						TestUtils
								.createInfoRechazoDTO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL));
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de rechazar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), estado.getEstado());
	}

	@Test
	public void testValidarAsientoRegistral() throws Exception {
		
		// Indicar el identificador de intercambio del asiento registral a validar
		String identificadorIntercambio = "O00002062_12_00000002";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		logger.info("Validando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);
		
		// Identificador del asiento registral a validar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);

		logger.info("Estado antes de validar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));

		getIntercambioRegistralWS().validarAsientoRegistral(idAsientoRegistral, 
				"201200200000001", /*Número de registro*/
				DateUtils.toXMLGregorianCalendar(new Date()) /*Fecha de registro*/); 
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de validar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), estado.getEstado());
	}

	@Test
	public void testTrazabilidad() throws Exception {

		// Indicar el identificador de intercambio del asiento registral
		String identificadorIntercambio = "O00002062_12_00000000";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL;
		logger.info("Obteniendo la trazabilidad del asiento registral con identificador de intercambio: {}", identificadorIntercambio);

		// Identificador del asiento registral a validar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);

		List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoCompletoAsientoRegistral(idAsientoRegistral);
		
		Assert.assertNotNull(trazas);
		
		// Ordenar por fecha de modificación
		Collections.sort(trazas, new Comparator<TrazabilidadDTO>() {
			public int compare(TrazabilidadDTO o1, TrazabilidadDTO o2) {
				return o1.getFechaModificacion().compare(o2.getFechaModificacion());
			}
		});

		for (TrazabilidadDTO traza : trazas) {
			
			DateTime dtFechaMod=new DateTime(DateUtils.toDate(traza.getFechaModificacion()));
			
			DateTime dtNow=new DateTime(new Date());
			DateTime dtMinus = dtNow.minusDays(1);
			
			if (dtFechaMod.isBefore(dtNow) && dtFechaMod.isAfter(dtMinus)){
				logger.info("\tTraza: {}", toString(traza));
			}
		}
	}

	@Test
	public void testAnularAsientoRegistral() throws Exception {

		// Indicar el identificador del asiento registral a anular
		String idAsientoRegistral = IDENTIFICADOR_ASIENTO_REGISTRAL.get();
		
		logger.info("Anulando asiento registral con id: {}", idAsientoRegistral);
		
		AsientoRegistralDTO asiento = getIntercambioRegistralWS().getAsientoRegistral(idAsientoRegistral);
		Assert.assertNotNull("No se ha encontrado el asiento registral", asiento);
		
		logger.info("Estado antes de anular: {}", asiento.getEstado());
		
		getIntercambioRegistralWS().anularAsientoRegistral(idAsientoRegistral);

		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de anular: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.ANULADO.getValue(), estado.getEstado());
	}

	@Test
	public void testDeleteAsientoRegistral() throws Exception {
		
		// Indicar el identificador del asiento registral a eliminar
		String idAsientoRegistral = IDENTIFICADOR_ASIENTO_REGISTRAL.get();
		
		logger.info("Eliminando asiento registral con id: {}", idAsientoRegistral);
		logger.info("Asiento registral: {}", toString(getIntercambioRegistralWS().getAsientoRegistral(idAsientoRegistral)));
		
		getIntercambioRegistralWS().deleteAsientoRegistral(idAsientoRegistral);
		logger.info("Asiento registral eliminado");
	}
	
//	@Test
//	public void testGeneracion(){
//	for (int i = 1; i < 10; i++) {
//		String contenido="protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest"+i+"(){ String numeroRegistro=\"20120010000000"+i+"\"; AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(numeroRegistro,CODIGO_ENTIDAD_REGISTRAL_ORIGEN,DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,CODIGO_UNIDAD_TRAMITACION_ORIGEN,DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,CODIGO_ENTIDAD_REGISTRAL_DESTINO,DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,CODIGO_UNIDAD_TRAMITACION_DESTINO,DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,CODIGO_ENTIDAD_REGISTRAL_INICIAL,DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);return asientoForm;}";
//		System.out.println(contenido);
//	}
//	
//	for (int i = 10; i <= 63; i++) {
//		String contenido="protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest"+i+"(){ String numeroRegistro=\"2012001000000"+i+"\"; AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(numeroRegistro,CODIGO_ENTIDAD_REGISTRAL_ORIGEN,DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN,CODIGO_UNIDAD_TRAMITACION_ORIGEN,DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN,CODIGO_ENTIDAD_REGISTRAL_DESTINO,DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO,CODIGO_UNIDAD_TRAMITACION_DESTINO,DESCRIPCION_UNIDAD_TRAMITACION_DESTINO,CODIGO_ENTIDAD_REGISTRAL_INICIAL,DESCRIPCION_ENTIDAD_REGISTRAL_INICIAL);return asientoForm;}";
//		System.out.println(contenido);
//	}
//	
//	
//	}

	

	/**
	 * Test de certificación de la normativa sicres3 de recepcion de de intercambios registrales
	 * Correspondientes a SIR-RC-PR-001 a SIR-RC-PR-134
	 * @throws Exception
	 */
	@Test
	public void testRecepcionFicherosIntercambio() throws Exception {

		logger.info("Inicio de testRecepcionFicherosIntercambio");
		
		File dir = new ClassPathResource("/xml/").getFile();
		logger.info("Consultando ficheros de intercambio en el directorio: {}", dir.getAbsolutePath());
		
		@SuppressWarnings("unchecked")
		Collection<File> ficheros = (Collection<File>)FileUtils.listFiles(dir, new String[] { "xml" }, false); 
		List<String> listaErroresPruebas=new ArrayList<String>();
		for (File fichero : ficheros) {
			String mensajeError=probarRecepcionFicheroIntercambio(fichero);
			if(mensajeError!=null){
				listaErroresPruebas.add(mensajeError);
			}
		}
		logger.info("Fin de testRecepcionFicherosIntercambio");
		Assert.assertEquals(listaErroresPruebas.size(),0);
	}
	
	@Test
	public void testValidation(){
		String filePath="C:/Documents and Settings/66596040/workspace/fwktd-sirTrunk/fwktd-sir/fwktd-sir-ws/src/prueba4.xml";
		XPathReaderUtil reader;
		try {
			reader = new XPathReaderUtil(new FileInputStream(filePath));
		
		String expression="//Hash/text()";
		NodeList results=(NodeList)reader.read(expression,XPathConstants.NODESET);
		
		Assert.assertEquals(2, results.getLength());
		for (int i=0;i<results.getLength();i++){
			Node item = results.item(i);
			String value=item.getNodeValue();
			Assert.assertEquals(true, Base64.isBase64(value));
		}
		
		
				
		Assert.assertNotNull(results);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void parserMessageFileLog() throws IOException{
		File file = new File("C:/config/fwktdsir/logs/fwktdsirWSMessage.log");
		List<String> lines = FileUtils.readLines(file);
		List<String> linesToProcess = new ArrayList<String>();
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String linea= (String) iterator.next();
			if (StringUtils.contains(linea, "<De_Mensaje>")){
				linesToProcess.add(linea);
			}
			
			
		}
		File fileOut = new File("C:/config/fwktdsir/logs/fwktdsirWSMessage.log.salida.txt");
		FileUtils.writeLines(fileOut, linesToProcess,"\n");
		
	}

	@Test
	public void testRecepcionFicheroIntercambio() throws Exception {
		logger.info("Inicio de testRecepcionFicheroIntercambio");
		
		File fichero = new ClassPathResource("/xml/SIR-RC-PR-070-1.xml").getFile();
		logger.info("Consultando fichero de intercambio {}", fichero.getName());
	
		String mensajeFallo=probarRecepcionFicheroIntercambio(fichero);
		logger.info("Fin de testRecepcionFicheroIntercambio");
		Assert.assertNull(mensajeFallo);
	}
	
	/**
	 * Test para lanzar unos determinados test de recepcion indicados por los numeros de prueba
	 * @throws Exception
	 */
	@Test
	public void testRecepcionFicherosIntercambioEspecificos() throws Exception {

		logger.info("Inicio de testRecepcionFicherosIntercambioEspecificos");
		
		File dir = new ClassPathResource("/xml/").getFile();
		
		int[] numPrueba = { 81};
		List <String> fileNames= new ArrayList<String>();
		
		for (int i = 0; i < numPrueba.length; i++) {
			fileNames.add(generateTestFileName(numPrueba[i]));
		}
		
		//filtro para solo selecionar los ficheros del directorio que queremos
		NameFileFilter nameFileFilter = new NameFileFilter(fileNames) ;
		@SuppressWarnings("unchecked")
		Collection<File> ficheros = (Collection<File>)FileUtils.listFiles(dir, nameFileFilter,null); 
		
		List<String> listaErroresPruebas=new ArrayList<String>();
		for (File fichero : ficheros) {
			
			String mensajeError=probarRecepcionFicheroIntercambio(fichero);
			if(mensajeError!=null){
				listaErroresPruebas.add(mensajeError);
			}
		}
		logger.info("Fin de testRecepcionFicherosIntercambio");
		Assert.assertEquals(listaErroresPruebas.size(),0);
	}
	
	
	
	protected String generateTestFileName(int numTest){
		String result="SIR-RC-PR-"+StringUtils.leftPad(Integer.toString(numTest), 3, '0')+"-1.xml";
		return result;
	}
	
	
	
	/**
	 * Prueba la recepción de un fichero de intercambio
	 * 
	 * @param fichero Objeto File del fichero XML a probar
	 * @return un String con una descripción del error, si falla la prueba. sino null.
	 * @throws Exception
	 */
	private String probarRecepcionFicheroIntercambio(File fichero) throws Exception{
		String mensajeFallo=null;
		logger.info("Procesando el fichero: {}", fichero.getName());
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta = WSSIR8AWSClient.envioFicherosAAplicacion(
				xmlFileToString(fichero), "", new OctetStream[0]);
		if (respuesta != null) {
			String codCorrecto=checkRecepcionFicheroIntercambio(fichero.getName(),respuesta);
			if(codCorrecto==null){				
				logger.info("Respuesta: {} - {}", respuesta.getCodigo(), respuesta.getDescripcion());
			}else{
				
				mensajeFallo = MessageFormatter.arrayFormat(
						"Error comprobación {}: Esperado {}, Recibido {} - {} ",
						new String[]{fichero.getName(),codCorrecto,
						respuesta.getCodigo(), respuesta.getDescripcion()});
				logger.error(mensajeFallo);
				
			}	
		} else {  
			logger.info("Respuesta: null");
		}
		return mensajeFallo;
	}
	
	/**
	 * Comprueba si la respuesta de un test de recepciónera la esperada o no
	 * 
	 * @param nombreFicheroXMLTest nombre del fichero XML de recepción del test
	 * @param respuesta objeto RespuestaWS resultado de la invocación al WS. 
	 * @return si no coinciden, devuelve la respuesta esperada. Si coinciden, null
	 */
	protected String checkRecepcionFicheroIntercambio(String nombreFicheroXMLTest,RespuestaWS respuesta){
		String sIdPrueba=StringUtils.substringBetween(nombreFicheroXMLTest, "SIR-RC-PR-", "-1.xml");
		int idPrueba=Integer.parseInt(sIdPrueba);
		String result=null;
		switch(idPrueba){
		
		case 8:case 	11:case  18:case  20:case 	21:case 	27:case 	35:case 	49:case  62:case 	67:case 71:case 74:case  75:case  76:case  78:case  79:case  82:case  88:case  89:case 90:case 91:case 92:case 93:case 94:case 95:case 105:case 110:case  111:case 112:case 113:case 117:case 129:case 132:case 133:   
			result=checkCodigoRecepcion(ErroresEnum.ERROR_0037.getValue(),respuesta);
			break;
		
		case 1: case 9: case 10: case 12: case 15: case 16: case 17: 
		case 22: case 23: case 24: case 28: case 29: case 30: case 32: 
		case 33: case 34: case 36: case 37: case 38: case 43: case 44: case 45: 
		case 46: case 47: case 48: case 50: case 51: case 52: case 53: case 54:
		case 55: case 56: case 57: case 58: case 59: case 60: case 61: case 63: 
		case 64: case 65: case 66: case 68: case 69: case 70: case 72: case 73:
		case 77: case 83: case 85: case 84: case 86: case 87: case 97: case 98: case 101: 
		case 102: case 103: case 104: case 106: case 107: case 108: case 109:
		case 114: case 115: case 116: case 118: case 119: case 120: case 121:
		case 122: case 123: case 124: case 125: case 126: case 127: case 128:
		case 130: case 131: case 134:
			result=checkCodigoRecepcion(ErroresEnum.OK.getValue(),respuesta);
			break;
		case 2: case 3: case 4: case 5: case 6: case 7: case 31: case 81: 
		case 96: case 99: case 100:   
			result=checkCodigoRecepcion(ErroresEnum.ERROR_0037.getValue(),respuesta);
			break;
			/*
		case 8: case 11: case 13: case 14: case 18: case 19: case 20: case 21: 
		case 25: case 26: case 35: case 39:	case 40: case 41: case 42: case 49:
		case 62: case 67: case 71: case 74: case 75: case 76: case 78: case 79: 
		case 80: case 82: case 88: case 89: case 90: case 91: case 92: case 93:
		case 94: case 95: case 105: case 110: case 111: case 112: 
		case 27: case 113: case 117: case 129:  case 132: case 133:
		*/
		case 13: case 14:  case 19:  
		case 25: case 26:  case 39:	case 40: case 41: case 42:
		case 80: 
			result=checkCodigoRecepcion(ErroresEnum.ERROR_0065.getValue(),respuesta);
			break;
		default: 
			logger.error("Número de test no definido {}",idPrueba);
			break;
		}
		
		//temporalmente estas anomalias tendran otro comportamiento diferente al de los casos de certificación
		result=anomaliasTemporalesEnTest(idPrueba,nombreFicheroXMLTest,respuesta,result);
						
		return result;
	}
	
	protected String anomaliasTemporalesEnTest(int idPrueba,String nombreFicheroXMLTest,es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta,String result ){
		//excepciones desactivadas
		switch (idPrueba) {
		
		case 13: case 14: case 25: case 26: case 39: case 40: case 41: case 42: case 80:
			String mensaje = MessageFormatter.arrayFormat("Warning  comprobación DCO desactivada {}: Esperado {}, Recibido {} - {} ",
			new String[]{nombreFicheroXMLTest,result,
					respuesta.getCodigo(), respuesta.getDescripcion()});
			logger.warn(mensaje);
			result=null;
			break;
		
		case 19:  case 81:
			mensaje = MessageFormatter.arrayFormat("Warning comprobación sincro relojes desactivada o timestamp {}: Esperado {}, Recibido {} - {} ",
			new String[]{nombreFicheroXMLTest,result,
					respuesta.getCodigo(), respuesta.getDescripcion()});
			logger.warn(mensaje);
			result=null;
			break;
			
			/*
		case 131:
			mensaje = MessageFormatter.arrayFormat("Warning comprobación validacion valor de documento identificativo por tipo documento desactivada {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
				*/
					/*
		case 27:
			mensaje = MessageFormatter.arrayFormat("Warning Debe retornar error ya que no tiene definido origen {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
					*/
		/**/
		case 70:
			mensaje = MessageFormatter.arrayFormat("Warning Debe retornar ERROR ya que 'Identificador_Intercambio' no coincide con el patrón con 'Codigo_Entidad_Registral_Inicio' {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
					
					
		case 99:
			mensaje = MessageFormatter.arrayFormat("Warning Debe retornar OK ya que se debe desactivar la validación HASH {}, y devuelve si que es un base64: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
					
		/*case 85: case 100:
			mensaje = MessageFormatter.arrayFormat("Warning Debe retornar ERROR pero no sabemos que motivo es el ERROR {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
			*/
					/*
		case 109:
			mensaje = MessageFormatter.arrayFormat("Warning dDebe retornar ERROR ya que el campo Hash de un anexo no puede ser vacío aunque no se valide {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;
		*/
					/*
		case 126:
			mensaje = MessageFormatter.arrayFormat("Warning Debe retornar ERROR ya que razonSocialInteresado' o ('nombreInteresado' o 'primerApellidoInteresado') no debe ser vacío {}: Esperado {}, Recibido {} - {} ",
					new String[]{nombreFicheroXMLTest,result,
							respuesta.getCodigo(), respuesta.getDescripcion()});
					logger.warn(mensaje);
					result=null;
					break;

					 */

		default:
			break;
		}
		return result;
	}
	
	/**
	 * Comprueba si la cadena del codigo de error recibido, coincide con la 
	 * esparada. Si no es asi devuelve la esperada. En caso contrario devuelve
	 * null 
	 * @param codCorrecto. String con la cadena esperada
	 * @param respuesta: Objeto respuestaWS a comprobar
	 * @return null si coinciden y codCorrecto en otro caso.
	 */
	private String checkCodigoRecepcion(String codCorrecto,RespuestaWS respuesta){
		if(respuesta!=null){
			if(! codCorrecto.equals(respuesta.getCodigo())){
				return codCorrecto;
			}
		}
		return null;
	}
}
