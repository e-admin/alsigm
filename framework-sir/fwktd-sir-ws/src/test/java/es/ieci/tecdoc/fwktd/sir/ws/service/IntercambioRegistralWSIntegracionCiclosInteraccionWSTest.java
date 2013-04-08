package es.ieci.tecdoc.fwktd.sir.ws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.apache.axis.attachments.OctetStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.sir.api.types.EstadoTrazabilidadEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.WS_SIR8_A_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.WS_SIR9_PortType;
import es.ieci.tecdoc.fwktd.sir.ws.utils.IdentificadoresIntercambioTestHelper;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.ws.utils.TrazasUtils;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;
import es.ieci.tecdoc.fwktd.util.mime.MimeUtil;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
@ContextConfiguration( {
		"classpath*:/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-ws-test-beans.xml" })
public class IntercambioRegistralWSIntegracionCiclosInteraccionWSTest extends
		AbstractWSTest {
	
	protected static final Logger logger = LoggerFactory.getLogger("TEST");
			
	//SIGEM
	protected static final String CODIGO_ENTIDAD_REGISTRAL_SORIA = "O00002061";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_SORIA = "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE SORIA";
	protected static final String CODIGO_UNIDAD_TRAMITACION_SORIA = "L02000042";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_SORIA = "Diputación Provincial de Soria";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_SORIA="contacto soria";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_SORIA="usaurio soria";
	protected static final InfoEntidadRegistral INFO_SORIA=new InfoEntidadRegistral(
			CODIGO_ENTIDAD_REGISTRAL_SORIA,
			DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
			CONTACTO_ENTIDAD_REGISTRAL_SORIA,
			USUARIO_ENTIDAD_REGISTRAL_SORIA);
	
	//sigem
	protected static final String CODIGO_ENTIDAD_REGISTRAL_COLINDRES="O00002062";
	protected static final String DESCRIPCION_ENTIDAD_COLINDRES="REGISTRO GENERAL DEL AYUNTAMIENTO DE COLINDRES";
	protected static final String CODIGO_UNIDAD_TRAMITACION_COLINDRES="L01390232";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES= "Ayuntamiento de Colindres";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_COLINDRES="usercoli";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_COLINDRES="usercolin";
	
	//CAMBIAMOS COLINDRES POR CR	
//	protected static final String CODIGO_ENTIDAD_REGISTRAL_COLINDRES=CODIGO_ENTIDAD_REGISTRAL_4;
//	protected static final String DESCRIPCION_ENTIDAD_COLINDRES=DESCRIPCION_ENTIDAD_REGISTRAL_4;
//	protected static final String CODIGO_UNIDAD_TRAMITACION_COLINDRES=CODIGO_UNIDAD_TRAMITACION_4;
//	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES= DESCRIPCION_UNIDAD_TRAMITACION_4;
//	protected static final String CONTACTO_ENTIDAD_REGISTRAL_COLINDRES="ContaCTO REG AYTO CR";
//	protected static final String USUARIO_ENTIDAD_REGISTRAL_COLINDRES="USUARIO AYTO CRS";
	
	
	
	protected static final InfoEntidadRegistral INFO_COLINDRES=new InfoEntidadRegistral(
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
			DESCRIPCION_ENTIDAD_COLINDRES,
			CONTACTO_ENTIDAD_REGISTRAL_COLINDRES,
			USUARIO_ENTIDAD_REGISTRAL_COLINDRES);
	
	//MINISTERIO
	/* Comentado para pruebas entre nuestro CIR, similamos muface con soria*/
	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE="O00001223";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE="MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE="E00106103";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE="MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_DESTINO_MUFACE="";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_DESTINO_MUFACE="";
	
//	
//	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE=CODIGO_ENTIDAD_REGISTRAL_SORIA;
//	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE=DESCRIPCION_ENTIDAD_REGISTRAL_SORIA;
//	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE=CODIGO_UNIDAD_TRAMITACION_SORIA;
//	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE=DESCRIPCION_UNIDAD_TRAMITACION_SORIA;
//	protected static final String CONTACTO_ENTIDAD_REGISTRAL_DESTINO_MUFACE=CONTACTO_ENTIDAD_REGISTRAL_SORIA;
//	protected static final String USUARIO_ENTIDAD_REGISTRAL_DESTINO_MUFACE=USUARIO_ENTIDAD_REGISTRAL_SORIA;
//	
	
	
	
	protected static final InfoEntidadRegistral INFO_MUFACE=new InfoEntidadRegistral(
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			CONTACTO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			USUARIO_ENTIDAD_REGISTRAL_DESTINO_MUFACE);
	
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO = "O00001222";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO = "MSPI- INSTITUTO NACIONAL DE CONSUMO";
	protected static final String CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO = "E00138403";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO = "INSTITUTO NACIONAL DE CONSUMO";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO="";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO="";
//	protected static final InfoEntidadRegistral INFO_CONSUMO=new InfoEntidadRegistral(
//			CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
//			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
//			CONTACTO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
//			USUARIO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO);
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_REC="";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_REC="";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_REC="";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_REC="";
//	protected static final InfoEntidadRegistral INFO_REC=
//		new InfoEntidadRegistral(CODIGO_ENTIDAD_REGISTRAL_REC,
//				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
//				USUARIO_ENTIDAD_REGISTRAL_REC,CONTACTO_ENTIDAD_REGISTRAL_REC);
	
	
	
	
	protected static final String CODIGO_ENTIDAD_REGISTRAL_CIUDAD_REAL= "O00002101";
	protected static final String DESCRIPCION_ENTIDAD_REGISTRAL_CIUDAD_REAL= "REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE CIUDAD REAL";
	protected static final String CODIGO_UNIDAD_TRAMITACION_CIUDAD_REAL = "L02000013";
	protected static final String DESCRIPCION_UNIDAD_TRAMITACION_CIUDAD_REAL= "Diputación Provincial de Ciudad Real";
	protected static final String CONTACTO_ENTIDAD_REGISTRAL_CIUDAD_REAL="CONTACTO CR";
	protected static final String USUARIO_ENTIDAD_REGISTRAL_CIUDAD_REAL="USER CR";
	
		
	
	
	private static final String PREFIJO_CODIGO_PRUEBA_INTERCAMBIO="SIR-IN-PR-"; 
	
	private static final String APLICACION="5G3M";
	private static final String DESCRIPCION_RECHAZO_PARA_TEST="Rechazo para test ";
	private static final String DESCRIPCION_REENVIO_PARA_TEST="Reenvio para test ";
	
	@Autowired
	protected IntercambioRegistralWS intercambioRegistralWSClient;
	
	@Autowired
	private WS_SIR8_A_PortType WSSIR8AWSClient;
	
	protected IntercambioRegistralWS getIntercambioRegistralWS() {
		return intercambioRegistralWSClient;
	}
	
	@Autowired
	private WS_SIR9_PortType WSSIR9WSClient;
	
	
	
	public WS_SIR9_PortType getWSSIR9WSClient() {
		return WSSIR9WSClient;
	}

	public void setWSSIR9WSClient(WS_SIR9_PortType wSSIR9WSClient) {
		WSSIR9WSClient = wSSIR9WSClient;
	}

	@Test
	public void testWS() {
		Assert.assertNotNull(intercambioRegistralWSClient);
		Assert.assertNotNull(WSSIR8AWSClient);

		
	}
	
	@Test
	public void testDebug(){
		verificarEnviarAsientoRegistralesFuncional001();
	}
	
	/**
	 * @param codigoEntidadRegistral
	 * @param identificadorIntercambio
	 * @return
	 * @throws Exception
	 */
	protected List<TrazabilidadDTO> getTrazas(String codigoEntidadRegistral,String identificadorIntercambio) throws Exception {
		List<TrazabilidadDTO> result = new ArrayList<TrazabilidadDTO>();
		
		DateTime dtNow=new DateTime(new Date());
		DateTime dtLimiteInferior = dtNow.minusMinutes(320);
		
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
			
			if (dtFechaMod.isBefore(dtNow) && dtFechaMod.isAfter(dtLimiteInferior)){
				logger.info("\tTraza: {}", toString(traza));
				result.add(traza);
			}
		}
		return result;
	}
	
	
	protected InfoOrigenPruebaIntercambioRegistral getInfoEnvio(){
		//origen
		String codEROrigen=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descEROrigen=DESCRIPCION_ENTIDAD_COLINDRES;
		String codUTOrigen=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descUTOrigen= DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;

		//destino
		String codERDestino=CODIGO_ENTIDAD_REGISTRAL_SORIA;
		String descERDestino= DESCRIPCION_ENTIDAD_REGISTRAL_SORIA;
		String codUTDestino=CODIGO_UNIDAD_TRAMITACION_SORIA;
		String descUTDestino=DESCRIPCION_UNIDAD_TRAMITACION_SORIA;
		
		//String codERDestino=CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		//String descERDestino= DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		//String codUTDestino=CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		//String descUTDestino=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		
		//inicio
		String codERInicio=CODIGO_ENTIDAD_REGISTRAL_COLINDRES; 
		String descERInicio=DESCRIPCION_ENTIDAD_COLINDRES;
		
		String codERDestinoReenvio=CODIGO_ENTIDAD_REGISTRAL_CIUDAD_REAL;
		String descERDestinoReenvio= DESCRIPCION_ENTIDAD_REGISTRAL_CIUDAD_REAL;
		String codUTDestinoReenvio=CODIGO_UNIDAD_TRAMITACION_CIUDAD_REAL;
		String descUTDestinoReenvio=DESCRIPCION_UNIDAD_TRAMITACION_CIUDAD_REAL;
		
		InfoOrigenPruebaIntercambioRegistral result = new InfoOrigenPruebaIntercambioRegistral(
				codEROrigen, descEROrigen, codUTOrigen, descUTOrigen,
				codERDestino, descERDestino, codUTDestino, descUTDestino,
				codERInicio, descERInicio, codERDestinoReenvio,
				descERDestinoReenvio, codUTDestinoReenvio, descUTDestinoReenvio);
		
		return result;
	}
	
	
	
	/**
	 * metodo auxiliar para realizar pruebas de recuperacion de contenido de los anexos de un intecambio y salvarlos en hd
	 */
	@Test
	public void testSaveAnexos_00(){
		String codigoEntidadRegistral=getInfoEnvio().codEROrigen;//"O00002061";
		String identificadorIntercambio="O00002062_12_00000043";
		AsientoRegistralDTO asiento=getAsientoRegistral( codigoEntidadRegistral,  identificadorIntercambio);
		List <AnexoDTO> anexos =asiento.getAnexos();
		int i=0;
		for (Iterator iterator = anexos.iterator(); iterator.hasNext();) {
			i++;
			
			AnexoDTO anexoDTO = (AnexoDTO) iterator.next();
			logger.debug("recuperando contenido de anexo con id:"+anexoDTO.getId());
			byte[] contenido = getIntercambioRegistralWS().getContenidoAnexo(anexoDTO.getId());
			try {
				IOUtils.write(contenido,new FileOutputStream(new File("c:/salida."+anexoDTO.getId()+"t.xt")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	/**
	 * metodo auxiliar que elimina los anexos de un intercambio registral y añade los indicados en una carpeta
	 * @param asientoForm
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void addAnexosTest_00(AsientoRegistralFormDTO asientoForm) throws FileNotFoundException, IOException{
		List<AnexoFormDTO> anexos=asientoForm.getAnexos();
		anexos.clear();
		
		//aaa
		
		File dir = new File("C:/temp/ficheros/prueba3/");
		logger.info("Consultando ficheros de intercambio en el directorio: {}", dir.getAbsolutePath());

		int id=0;
		@SuppressWarnings("unchecked")
		Collection<File> ficheros = (Collection<File>)FileUtils.listFiles(dir, new String[] { "txt" }, false);
		for (Iterator iterator = ficheros.iterator(); iterator.hasNext();) {
			id++;
			File file = (File) iterator.next();
			AnexoFormDTO anexo= new AnexoFormDTO();
			anexo.setCertificado(null);
		    anexo.setCodigoFichero("Fichero_" + id);
		    anexo.setCodigoFicheroFirmado(null);
		    
		    anexo.setContenido(IOUtils.toByteArray(new FileInputStream(file)));
		    
		    anexo.setFirma(null);
		    anexo.setIdentificadorFicheroFirmado(null);
		    anexo.setNombreFichero(file.getName());
		    anexo.setObservaciones(null);
		    anexo.setTimestamp(null);
		    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
		    String mimetype=MimeUtil.getMimeType(file.getName());
		    anexo.setTipoMIME(mimetype);
		    anexo.setValidacionOCSPCertificado(null);
		    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());
			
			anexos.add(anexo);
			
		}
		
	}
	
	
	@Test
	public void tetGetTrazas_00(){
		List<TrazabilidadDTO> trazas = getTrazas(0, getInfoEnvio().codEROrigen);
		Assert.assertNotNull(trazas);
		logger.debug("Numero de trazas obtenidas:"+trazas.size());
		for (Iterator iterator = trazas.iterator(); iterator.hasNext();) {
			TrazabilidadDTO trazabilidadDTO = (TrazabilidadDTO) iterator.next();
			//logger.debug("Traza obtenida:"+ToStringBuilder.reflectionToString(trazabilidadDTO,ToStringStyle.MULTI_LINE_STYLE));
			logger.debug("Traza obtenida:"+ToStringBuilder.reflectionToString(trazabilidadDTO));
		}		
		
	}
	
	@Test
	public void testEnviarAsientoRegistral_00() {
		logger.info("Enviando asiento registral...");
		int i=0;
		String numeroRegistro = "2012050800000000";
		
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		
		try{
			AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
					numeroRegistro, infEnvio.codEROrigen, infEnvio.descEROrigen, infEnvio.codUTOrigen,
					infEnvio.descUTOrigen, infEnvio.codERDestino, infEnvio.descERDestino, infEnvio.codUTDestino,
					infEnvio.descUTDestino, infEnvio.codERInicio, infEnvio.descERInicio);
			
				addAnexosTest_00(asientoForm);
			
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
				setIdentificadorIntercambioRegistral(i,
						asiento.getIdentificadorIntercambio());
			} catch (Exception e) {
				String message = "Error ejecutando test EnviarAsientoRegistralFuncional iteracion:"
						+ i;
				logger.error(message, e);
				
			}
		saveIdentificadoresIntercambioRegistral();
	}
	
	@Test
	public void testConfirmar_00(){
		
			String numeroRegistro = "20120010000000A";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(0);
			
			//codigo de la entidad que confirma
			String codigoEntidad=getInfoEnvio().codERDestino;
			
			try{
				confirmarAsientoRegistral(codigoEntidad,
						identificadorIntercambio,numeroRegistro);
			}catch(Exception e){
				logger.error("Fallo en generación de confirmacion",e);
			}
	}


	@Test
	public void testRechazarOrigen_00(){
		
			String numeroRegistro = "20120010000000A";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(0);
			InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
			
			//codigo de la entidad que rechaza
			String codigoEntidad=infEnvio.codERDestino;
			String descripcionEntidad=infEnvio.codERDestino;
			
			try{
				InfoEntidadRegistral entidadRechaza= new InfoEntidadRegistral(codigoEntidad, descripcionEntidad, "usuario rechazo", "contactoRechazo");
				rechazarAsientoRegistral(0, TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(), entidadRechaza);
				
			}catch(Exception e){
				logger.error("Fallo en generación de rechazo",e);
			}
	}


	@Test
	public void testReenviar_00(){
		
		String identificadorIntercambio=getIdentificadorIntercambioRegistral(0);
		logger.info("Reenviando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);
		
		InfoOrigenPruebaIntercambioRegistral infoEnvio = getInfoEnvio();
		
		//codigo de la entidad que reenvia
		String codigoEntidad=infoEnvio.codERDestino;
		
		//datos a quien reenvia
		String codigoEntidadDestinoReenvio=infoEnvio.codERDestinoReenvio;
		String descripcionEntidadDestinoReenvio=infoEnvio.descERDestinoReenvio;
		
		
		// Identificador del asiento registral reenviar (se reenvía desde el destino)
		String idAsientoRegistral = getIdAsientoRegistral(infoEnvio.codERDestino, identificadorIntercambio);
	
		
		InfoReenvioDTO infoReenvio=new InfoReenvioDTO();
		infoReenvio.setCodigoEntidadRegistralDestino(codigoEntidadDestinoReenvio);
		infoReenvio.setDescripcionEntidadRegistralDestino(descripcionEntidadDestinoReenvio);
		infoReenvio.setAplicacion(APLICACION);
		infoReenvio.setDescripcion(DESCRIPCION_REENVIO_PARA_TEST+0);
		infoReenvio.setContacto("contacto reenvio");
		infoReenvio.setUsuario("user reenvio");
		
		getIntercambioRegistralWS().reenviarAsientoRegistral(idAsientoRegistral, infoReenvio); 
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de reenviar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.REENVIADO.getValue(), estado.getEstado());
		
	}


	@Test
	public void testConfirmarReenvio_00(){
		
			String numeroRegistro = "20120010000000R";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(0);	
			
			//codigoEntidad que confirma el reenvio
			String codigoEntidad=getInfoEnvio().codERDestinoReenvio;
			
			try{
				confirmarAsientoRegistral(codigoEntidad,
						identificadorIntercambio,numeroRegistro);
			}catch(Exception e){
				logger.error("Fallo en generación de confirmacion",e);
			}
	}


	@Test
	public void testRechazarOrigenReenvio_00(){
		
			String numeroRegistro = "20120010000000R";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(0);
			InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
			
			//codigoEntidad que rechaza a origen el reenvio
			String codigoEntidad=infEnvio.codERDestinoReenvio;
			String descripcionEntidad=infEnvio.descERDestinoReenvio;
			
			try{
				InfoEntidadRegistral entidadRechaza= new InfoEntidadRegistral(codigoEntidad, descripcionEntidad, "usuario rechazo renenvio origen", "contactoRechazo reenvio origen");
				rechazarAsientoRegistral(0, TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(), entidadRechaza);
				
			}catch(Exception e){
				logger.error("Fallo en generación de rechazo",e);
			}
	}


	@Test
	public void testRechazarReenvioInicio_00(){
		
			String numeroRegistro = "2012001000000RI";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(0);
			InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
			
			//codigoEntidad que rechaza a inicio el reenvio
			String codigoEntidad=infEnvio.codERDestinoReenvio;
			String descripcionEntidad=infEnvio.descERDestinoReenvio;
			
			try{
				InfoEntidadRegistral entidadRechaza= new InfoEntidadRegistral(codigoEntidad, descripcionEntidad, "usuario rechazo renenvio origen", "contactoRechazo reenvio origen");
				rechazarAsientoRegistral(0, TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(), entidadRechaza);
				
			}catch(Exception e){
				logger.error("Fallo en generación de rechazo",e);
			}
	}


	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 */
	@Test
	public void testVerificarEnviarAsientoRegistralesFuncional_00(){
		
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		
		
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		
		
		//registro pendiente de envio en el cir
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_PENDIENTE, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino);
		
		//registro enviado desde el cir
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.ENVIO, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino);
		
		
		//A partir de aqui gestion de estados desde el cir destinatario (El ack de recepcion del envio)
		
		//ACK desde el cir destinatario:
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PENDIENTE, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		//ACK desde el cir destinatario:
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_ENVIADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		
		//ACK recibido en cir originario del intercambio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_RECIBIDO_ENVIO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen);

		////ACK procesado en cir originario del intercambio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PROCESADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		//registro recibido en el cir destino, se intercambia el origen por el destino, porque el destino es el q lanza esta confirmacion
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
	}
	
	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 * CONFIRMACION: desde destino a destino reenvio
	 */
	
	@Test
	public void testVerificarConfirmar_00(){
		
		//verificamos el envio
		testVerificarEnviarAsientoRegistralesFuncional_00();
		
		//verificamos la confirmacion
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		
	}
	
	
	@Test
	public void testVerificarRechazo_00(){
		
		//verificamos el envio
		testVerificarEnviarAsientoRegistralesFuncional_00();
		
		//verificamos la confirmacion
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		//RECHAZO
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		//ACK rechazo desde el origen
		//ACK desde el cir ORIGEN:
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PENDIENTE, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		//ACK enviado desde el cir originario del intercambio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_ENVIADO, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		
		//ACK enviado desde el cir originario del intercambio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_RECIBIDO_ENVIO, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);

		//ACK enviado desde el cir originario del intercambio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PROCESADO, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		//ACK enviado desde el cir originario del intercambio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
	}
	
	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 * REENVIO: desde destino a destino reenvio
	 * ACK REENVIO: desde destino reenvio a destino
	 */
	@Test
	public void testVerificarReenvio_00(){
		
		//verificamos el envio y ACK de envio
		testVerificarEnviarAsientoRegistralesFuncional_00();
		
		//verificamos la recepcion correcta
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
		//REENVIO desde destino a destino de reenvio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				
				);
		
		//ACK desde destino reenvio a destino
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PENDIENTE, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		//ACK desde destino reenvio a destino
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_ENVIADO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		
		//ACK desde destino reenvio a destino 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_RECIBIDO_ENVIO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);

		//ACK desde destino reenvio a destino 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PROCESADO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		//ACK desde destino reenvio a destino
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
	}
	
	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 * REENVIO: desde destino a destino reenvio
	 * ACK REENVIO: desde destino reenvio a destino
	 * CONFIRMACION: desde destino reenvio a origen
	 */
	@Test
	public void testVerificarConfirmarReenvio_00(){
		
		/** ENVIO : desde origen a destino
		 * ACK ENVIO: desde destino a origen
		 * REENVIO: desde destino a destino reenvio
		 * ACK REENVIO: desde destino reenvio a destino
		 */ 
		testVerificarReenvio_00();
		
		//CONFIRMACION: desde destino reenvio a origen
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codEROrigen, infEnvio.descEROrigen,
				infEnvio.codUTOrigen, infEnvio.descUTOrigen
				);
		
	}
	
	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 * REENVIO: desde destino a destino reenvio
	 * ACK REENVIO: desde destino reenvio a destino
	 * RECHAZO A ORIGEN: desde destino reenvio a destino
	 * ACK desde destino a destino reenvio
	 * 
	 */
	@Test
	public void testVerificarRechazoReenvioOrigen_00(){
		
		/** ENVIO : desde origen a destino
		 * ACK ENVIO: desde destino a origen
		 * REENVIO: desde destino a destino reenvio
		 * ACK REENVIO: desde destino reenvio a destino
		 */ 
		testVerificarReenvio_00();
		
		//CONFIRMACION: desde destino reenvio a origen
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		
		
		/*
		* RECHAZO A ORIGEN: desde destino reenvio a destino
		 * ACK desde destino a destino reenvio
		*/
		
		//RECHAZO desde destino reenvio a destino
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino
				);
		
		//ACK rechazo desde destino a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PENDIENTE, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		//ACK rechazo desde destino a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_ENVIADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		
		//ACK rechazo desde destino a destino reenvio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_RECIBIDO_ENVIO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);

		//ACK rechazo desde destino a destino reenvio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PROCESADO, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		//ACK rechazo desde destino a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERDestino, infEnvio.descERDestino,
				infEnvio.codUTDestino, infEnvio.descUTDestino,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
	}
	
	
	/**
	 * Se debe verificar:
	 * ENVIO : desde origen a destino
	 * ACK ENVIO: desde destino a origen
	 * REENVIO: desde destino a destino reenvio
	 * ACK REENVIO: desde destino reenvio a destino
	 * RECHAZO A INICIO: desde destino reenvio al nodo inicial de los envios
	 * ACK desde el nodo inicial a destino reenvio
	 * 
	 */
	@Test
	public void testVerificarRechazoReenvioInicio_00(){
		
		/** ENVIO : desde origen a destino
		 * ACK ENVIO: desde destino a origen
		 * REENVIO: desde destino a destino reenvio
		 * ACK REENVIO: desde destino reenvio a destino
		 */ 
		testVerificarReenvio_00();
		
		//CONFIRMACION: desde destino reenvio a origen
		InfoOrigenPruebaIntercambioRegistral infEnvio = getInfoEnvio();
		List<TrazabilidadDTO> trazas=getTrazas(0,infEnvio.codEROrigen);
		
		
		/*
		* RECHAZO A ORIGEN: desde destino reenvio a inicio
		 * ACK desde destino a destino inicio
		*/
		
		//RECHAZO desde destino reenvio a inicio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null
				);
		
		//ACK rechazo desde inicio a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PENDIENTE, null,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		//ACK rechazo desde inicio a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_ENVIADO, null,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		
		//ACK rechazo desde inicio a destino reenvio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_RECIBIDO_ENVIO, null,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);

		//ACK rechazo desde inicio a destino reenvio 
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.MENSAJE_PROCESADO, null,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
		//ACK rechazo desde inicio a destino reenvio
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, null,
				infEnvio.codERInicio, infEnvio.descERInicio,
				null, null,
				infEnvio.codERDestinoReenvio, infEnvio.descERDestinoReenvio,
				infEnvio.codUTDestinoReenvio, infEnvio.descUTDestinoReenvio
				);
		
	}
	
	
	
	
	
	
	/**
	 * Test para ejecucion de las pruebas funcionales de envío de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-001 -SIR-IN-PR-008
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEnviarAsientoRegistralesFuncional_001_008() {
		logger.info("Enviando asiento registral...");
		for (int i = 1; i <= 8; i++) {
			try {
				String methodName = "createAsientoRegistralFormParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, (Class[])null);
				AsientoRegistralFormDTO asientoForm = (AsientoRegistralFormDTO) m
						.invoke(this, (Object[])null);
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
				setIdentificadorIntercambioRegistral(i,
						asiento.getIdentificadorIntercambio());
			} catch (Exception e) {
				String message = "Error ejecutando test EnviarAsientoRegistralFuncional iteracion:"
						+ i;
				logger.error(message, e);
				
			}
		}
		saveIdentificadoresIntercambioRegistral();
	}

	/*
	public void testGeneracionFicheroIdentificadoresIntercambio(){
		for (int i = 1; i < 66; i++) {
			String identificadorPre="O00002062_12_00000";//106·
			String suffix=StringUtils.leftPad(String.valueOf(i),3,'0');
			setIdentificadorIntercambioRegistral(i,identificadorPre+suffix);
		}
		saveIdentificadoresIntercambioRegistral();
	}
	*/
		
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * En oficina de origen:
	 * - Envío de fichero de intercambio de envío.
	 * - Recepción de mensaje ACK.
	 * - Recepción de mensaje de confirmación.
	 * - Consulta de trazabilidad de confirmación.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES Con 1 interesado sin representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			1 fichero  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 * 	
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest1() {
		String numeroRegistro = "201200100000001";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);	
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			Sin organismo origen. Con 1 interesado con representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			2 fichero  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest2() {
		String numeroRegistro = "201200100000002";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getAnexos().clear();
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("1"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("2"));	
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados con/sin representantes.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			3 fichero  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest3() {
		String numeroRegistro = "201200100000003";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		asientoForm.getAnexos().clear();
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("1"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("2"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("3"));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Sin interesado
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			4 fichero  +  firmas electr.
	 * Tipo de Registro:	Salida
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest4() {
		String numeroRegistro = "201200100000004";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getInteresados().clear();

		asientoForm.getAnexos().clear();
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("1"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("2"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("3"));
		asientoForm.getAnexos().add(TestUtils.createAnexoFormDTO("4"));
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest5() {
		String numeroRegistro = "201200100000005";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			Sin organismo origen. Con 1 interesado con representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest6() {
		String numeroRegistro = "201200100000006";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);

		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados con/sin representantes.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest7() {
		String numeroRegistro = "201200100000007";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,
				null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion:			Envío
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Salida
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralFormParaTest8() {
		String numeroRegistro = "201200100000008";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	protected void createConfirmacionAsientoRegistralParaTest1() {
		String numeroRegistro = "201200100000001";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(1);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest2() {
		String numeroRegistro = "201200100000002";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(2);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest3() {
		String numeroRegistro = "201200100000003";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(3);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest4() {
			String numeroRegistro = "201200100000004";
			String identificadorIntercambio= getIdentificadorIntercambioRegistral(4);	
			try{
				confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
						identificadorIntercambio,numeroRegistro);
			}catch(Exception e){
				logger.error("Fallo en generación de confirmacion",e);
			}
	
	}


	protected void createConfirmacionAsientoRegistralParaTest5() {
		String numeroRegistro = "201200100000005";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(5);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest6() {
		String numeroRegistro = "201200100000006";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(6);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest7() {
		String numeroRegistro = "201200100000007";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(7);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	protected void createConfirmacionAsientoRegistralParaTest8() {
		String numeroRegistro = "201200100000008";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(8);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}


	@Test
	public void testConfirmacionAsientosRegistralesFuncional_001_008() {
		logger.info("Enviando confirmacion asiento registral...");
		for(int i=2;i<=8;i++){	//casos de prueba
			try{
				String methodName = "createConfirmacionAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, (Class[])null);
				m.invoke(this, (Object[])null);
			}catch(Exception e){
				String mensaje="Error ejecutando test ConfirmacionAsientos"+
									"RegistralesFuncional iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
	}
	
	 /**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional001(){
		verificarEnvio(1,true);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional002(){
		verificarEnvio(2,false);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional003(){
		verificarEnvio(3,false);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional004(){
		verificarEnvio(4,true);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional005(){
		verificarEnvio(5,true);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional006(){
		verificarEnvio(6,false);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional007(){
		verificarEnvio(7,false);
	}
	
	/**
	 * Verificar en oficina de origen, usando el servicio de trazabilidad:
	 * - Envío de fichero de intercambio de envío.
	 * - Con posterioridad, recepción de mensaje ACK.
	 * - Con posterioridad, recepción de mensaje de confirmación.
	 */
	@Test
	public void verificarEnviarAsientoRegistralesFuncional008(){
		verificarEnvio(8,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * @param numPrueba
	 * 		Un entero con el numero del test a verificar
	 * @organismoOrigen
	 * 		Boolean para saber si los campos de la unidad de tramitacion
	 * 		deben estar vacíos en el test
	 */
	protected void verificarEnvio(int numPrueba,boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarEnvioConfirmacionAsientoRegistralFuncional(numPrueba,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				codigoUnidadTramitacion,descripcionUnidadTramitacion,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE);
	}

	/**
	 * Test para ejecucion de las pruebas funcionales de confirmacion de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-009 -SIR-IN-PR-014
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConfirmacionAsientosRegistralesFuncional_009_014() {
		logger.info("Enviando confirmacion asiento registral...");
		for(int i=9;i<=14;i++){	//casos de prueba
			try{
				String methodName = "createConfirmacionAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, (Class[])null);
				m.invoke(this, (Object[])null);
			}catch(Exception e){
				String mensaje="Error ejecutando test ConfirmacionAsientos"+
									"RegistralesFuncional iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Confirmación
	 * Adjuntos:			1 Fichero+Justificante+xml registro
	 * Tipo de Registro:	Entrada
	 */
	 public void createConfirmacionAsientoRegistralParaTest9() {
		String numeroRegistro = "201200100000009";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(9);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Confirmación
	 * Adjuntos:			2 Ficheros+Justificante+xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createConfirmacionAsientoRegistralParaTest10() {
		String numeroRegistro = "201200100000010";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(10);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Confirmación
	 * Adjuntos:			3 Ficheros+Justificante+xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createConfirmacionAsientoRegistralParaTest11() {
		String numeroRegistro = "201200100000011";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(11);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Confirmación
	 * Adjuntos:			4 Ficheros+Justificante+xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createConfirmacionAsientoRegistralParaTest12() {
		String numeroRegistro = "201200100000012";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(12);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Confirmación
	 * Adjuntos:			5 Ficheros+Justificante+xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createConfirmacionAsientoRegistralParaTest13() {
		String numeroRegistro = "201200100000013";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(13);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es confirmado.
	 * Generación de mensaje de confirmación
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operacion  1:		Reenvio
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion  2:		Confirmación
	 * Adjuntos:			5 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createConfirmacionAsientoRegistralParaTest14() {
		String numeroRegistro = "201200100000014";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(14);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
					identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}

	
	/**
	 * Metodo auxiliar para usarse en los los test unitarios referentes a confirmacion
	 * @param codigoEntidadRegistral
	 * @param identificadorIntercambio
	 * @param numeroRegistoNuevo
	 * @throws Exception
	 */
	protected void confirmarAsientoRegistral(String codigoEntidadRegistral,String identificadorIntercambio,String numeroRegistoNuevo) throws Exception {
 
		logger.info("Validando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);
		
		// Identificador del asiento registral a validar/confirmar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);

		logger.info("Estado antes de validar: {}", getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral));

		getIntercambioRegistralWS().validarAsientoRegistral(idAsientoRegistral, 
				numeroRegistoNuevo, /*Número de registro*/
				DateUtils.toXMLGregorianCalendar(new Date()) /*Fecha de registro*/); 
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de validar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.VALIDADO.getValue(), estado.getEstado());
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral009(){
		/* Por ejemplo para el caso de las confirmaciones
		 * Verificar que lo recibido concuerda con lo esperado (numero de ficheros,justicante, etc tc)
		 * se hará uso del metodo getTrazas de esto
		 * para verificar recepcion de ACK, confirmacion y ver que se encuentra en el estado confirmado 
		 */
		verificarConfirmacion1(9);
		
		String idAsientoRegistral=getIdAsientoRegistral(9,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,1);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral010(){
		verificarConfirmacion1(10);
		
		String idAsientoRegistral=getIdAsientoRegistral(10,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,2);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral011(){
		verificarConfirmacion1(11);

		String idAsientoRegistral=getIdAsientoRegistral(11,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,3);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral012(){
		verificarConfirmacion1(12);
		
		String idAsientoRegistral=getIdAsientoRegistral(12,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,4);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral013(){
		verificarConfirmacion1(13);

		String idAsientoRegistral=getIdAsientoRegistral(13,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 */
	protected void verificarConfirmacion1(int numPrueba){
		verificarEnvioConfirmacionAsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_REC,
			DESCRIPCION_ENTIDAD_REGISTRAL_REC,
			null,null,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
			DESCRIPCION_ENTIDAD_COLINDRES,
			CODIGO_UNIDAD_TRAMITACION_COLINDRES,
			DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio a la oficina destino.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarConfirmarAsientoRegistral014(){
		verificarReenvioConfirmacionAsientoRegistralFuncional(14,
				CODIGO_ENTIDAD_REGISTRAL_REC,
				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO, 
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
		
		
		String idAsientoRegistral=getIdAsientoRegistral(14,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Se encarga de realizar comprobaciones sobre los anexos de los test de
	 * de confirmacion
	 * @param idAsientoRegistral  
	 * 		Identificador del asiento registral enviado inicialmente
	 * @param numFicherosAdjuntos 
	 * 		Numero de ficheros adjuntados, sin contar justificante y xml de
	 * 		registro 
	 */
	protected void verificarAnexosAsientoRegistral(
			String idAsientoRegistral,int numFicherosAdjuntos){
		AsientoRegistralDTO asiento=getIntercambioRegistralWS()
			.getAsientoRegistral(idAsientoRegistral);
		Assert.assertEquals(numFicherosAdjuntos+1+1,asiento.getAnexos().size());
		Assert.assertEquals("application/pdf", asiento.getAnexos().get(numFicherosAdjuntos).tipoMIME);
		Assert.assertEquals("application/xml", asiento.getAnexos().get(numFicherosAdjuntos+1).tipoMIME);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion de origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Código de la unidad de tramitacion de destino
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de launidad de tramitacion de destino
	 */
	protected void verificarEnvioConfirmacionAsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){
		
		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		//Existe entrada para la confirmacion y es correcta?
		// se debe intercambiar origen por destino para el regitro confirmado
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO, null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);			
			
		//Existe entrada para el ACK y es correcta?
		// se debe intercambiar origen por destino para el regitro confirmado
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		//Existe entrada para la recepcion del registro y es correcta?
		verificarTraza(trazas,EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio a la oficina destino.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de confirmación.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion de origen
	 * @param codigoEntidadRegistralIntermedia
	 * 		Codigo de la entidad registral intermedia
	 * @param descripcionEntidadRegistralIntermedia
	 * 		Descripcion de la entidad registral intermedia
	 * @param unidadTramitacionIntermedia
	 * 		Código de la unidad de tramitacion intermedia
	 * @param descripcionUnidadTramitacionIntermedia
	 * 		Descripcion de launidad de tramitacion intermedia
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Código de la unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de launidad de tramitacion destino
	 */
	protected void verificarReenvioConfirmacionAsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralIntermedia,String descripcionEntidadRegistralIntermedia,
			String unidadTramitacionIntermedia,String descripcionUnidadTramitacionIntermedia,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		//Existe entrada para la confirmacion y es correcta?
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO, null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, 
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		//Existe entrada para el ACK y es correcta?
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				null, null);
	
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				null, null,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);	
	}
	
	/**
	 * Busca una traza anterior a la fecha pasada por parametro, a partir del 
	 * estado especificado, en la lista recibida.
	 * Con la traza devuelta comprueba que coincidan los codigos y descripciones
	 * de la entidad registrales y unidades de tramitacion, de origen y destino,
	 * con las suministradas en la llamada. 
	 * 
	 * @param trazas
	 * 		Lista de objetos TrazabilidadDTO con las trazas
	 * @param estado
	 * 		Un objeto EstadoTrazabilidadEnum con el estado a buscar 
	 * @param fechaHasta
	 * 		Límite superior para la fecha de la busqueda
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral origen
	 * @param unidadTramitacionOrigen
	 * 		Codigo de la unidad de tramitacion origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral origen
	 * @param unidadTramitacionDestino
	 * 		Codigo de la unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de la unidad de tramitacion destino
	 * @return un objeto TrazabilidadDTO con la traza encontrada
	 */
	protected TrazabilidadDTO verificarTraza(List<TrazabilidadDTO> trazas,
			EstadoTrazabilidadEnum estado,Date fechaHasta,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){
		TrazabilidadDTO trazaAVerificar=TrazasUtils.buscarTrazaAnterior(
				trazas, estado,fechaHasta,codigoEntidadRegistralOrigen,codigoEntidadRegistralDestino);
		Assert.assertNotNull(trazaAVerificar);
		
		
		
		//logger.debug("Traza a verificar:"+ToStringBuilder.reflectionToString(trazaAVerificar,ToStringStyle.MULTI_LINE_STYLE));
		logger.debug("Traza a verificar en estado "+estado.getName()+ ":" +ToStringBuilder.reflectionToString(trazaAVerificar));
		Assert.assertTrue(TrazasUtils.validarTraza(trazaAVerificar,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino));
		return trazaAVerificar;
	}
	
	/**
	 * Devuelva la lista de trazas asociada a una prueba
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral origen
	 * @return
	 */
	protected List<TrazabilidadDTO> getTrazas(int numPrueba,String codigoEntidadRegistralOrigen){
		String identificadorIntercambio=getIdentificadorIntercambioRegistral(numPrueba); 
		Assert.assertNotNull(identificadorIntercambio);
		List<TrazabilidadDTO> trazas=null;
		try{
			trazas=getTrazas(codigoEntidadRegistralOrigen,identificadorIntercambio);
		}catch(Exception e){
			logger.error("Error en la recuperacion de trazabilidad",e);
		}
		return trazas;
	}
	
	/**
	 * Test para ejecucion de las pruebas funcionales de rechazo a origen de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-015 - SIR-IN-PR-020
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRechazoOrigenAsiento_015_020() {
		logger.info("Enviando rechazo a origen asiento registral...");
		for(int i=15;i<=20;i++){	//casos de prueba
			try{
				String methodName = "createRechazoAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, (Class[])null);
				m.invoke(this, (Object[])null);
			}catch(Exception e){
				String mensaje="Error ejecutando test RechazoAsiento"+
					"OrigenAsiento iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		DIPUTACIÓN DE SORIA
	 * Operacion:			Rechazo a origen
	 * Adjuntos:			1 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest15(){
		//String numeroRegistro = "201200100000015";
		try{
			rechazarAsientoRegistral(15,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		DIPUTACIÓN DE SORIA
	 * Operacion:			Rechazo a origen
	 * Adjuntos:			2 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest16(){
		//String numeroRegistro = "201200100000016";
		try{
			rechazarAsientoRegistral(16,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		DIPUTACIÓN DE SORIA
	 * Operacion:			Rechazo a origen
	 * Adjuntos:			3 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest17(){
		//String numeroRegistro = "201200100000017";
		try{
			rechazarAsientoRegistral(17,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		DIPUTACIÓN DE SORIA
	 * Operacion:			Rechazo a origen
	 * Adjuntos:			4 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest18(){
		//String numeroRegistro = "201200100000018";
		try{
			rechazarAsientoRegistral(18,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario:		DIPUTACIÓN DE SORIA
	 * Operacion:			Rechazo a origen
	 * Adjuntos:			5 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest19(){
		//String numeroRegistro = "201200100000019";
		try{
			rechazarAsientoRegistral(19,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es rechazado a origen.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operacion 1:			Reenvio
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion 2:			Rechazo a origen
	 * Adjuntos:			5 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest20(){
		//String numeroRegistro = "201200100000020";	
		try{
			rechazarAsientoRegistral(20,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral015(){
		verificarRechazoOrig1(15);

		String idAsientoRegistral=getIdAsientoRegistral(15,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,1);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral016(){
		verificarRechazoOrig1(16);

		String idAsientoRegistral=getIdAsientoRegistral(16,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,2);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral017(){
		verificarRechazoOrig1(17);

		String idAsientoRegistral=getIdAsientoRegistral(17,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,3);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral018(){
		verificarRechazoOrig1(18);

		String idAsientoRegistral=getIdAsientoRegistral(18,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,4);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK .
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral019(){
		verificarRechazoOrig1(19);

		String idAsientoRegistral=getIdAsientoRegistral(19,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 */
	protected void verificarRechazoOrig1(int numPrueba){
		verificarRechazoAsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_REC,
			DESCRIPCION_ENTIDAD_REGISTRAL_REC,
			null,null,
			CODIGO_ENTIDAD_REGISTRAL_SORIA, 
			DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
			CODIGO_UNIDAD_TRAMITACION_SORIA,
			DESCRIPCION_UNIDAD_TRAMITACION_SORIA);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 */
	@Test
	public void verificarRechazoAsientoRegistral020(){
		verificarReenvioRechazoAsientoRegistralFuncional(20,
				CODIGO_ENTIDAD_REGISTRAL_REC,
				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
		
		String idAsientoRegistral=getIdAsientoRegistral(20,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral origen
	 * @param unidadTramitacionOrigen
	 * 		Codigo de la unidad de tramitacion origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Codigo de la unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 *		Descripcion de la unidad de tramitacion destino	 		
	 */
	protected void verificarRechazoAsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,EstadoTrazabilidadEnum.REGISTRO_ENVIADO ,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio de envío.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a origen.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo de la entidad registral origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral origen
	 * @param unidadTramitacionOrigen
	 * 		Codigo de la unidad de tramitacion origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralIntermedia
	 * 		Codigo de la entidad registral intermedia
	 * @param descripcionEntidadRegistralIntermedia
	 * 		Descripcion de la entidad registral intermedia
	 * @param unidadTramitacionIntermedia
	 * 		Codigo de la unidad de tramitacion intermedia
	 * @param descripcionUnidadTramitacionIntermedia
	 *		Descripcion de la unidad de tramitacion intermedia
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Codigo de la unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 *		Descripcion de la unidad de tramitacion destino	 		
	 */
	protected void verificarReenvioRechazoAsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralIntermedia,String descripcionEntidadRegistralIntermedia,
			String unidadTramitacionIntermedia,String descripcionUnidadTramitacionIntermedia,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				null,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
	}
	
	/**
	 * Test para ejecucion de las pruebas funcionales de rechazo a inicio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-021 - SIR-IN-PR-025 
	 * y SIR-IN-PR-027 - SIR-IN-PR-034
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRechazoInicioAsiento21_25() {
		logger.info("Enviando rechazo a inicio asiento registral...");
		for(int i=21;i<=25;i++){	//casos de prueba
			try{
				String methodName = "createRechazoAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, 
						(Class[])null);
				m.invoke(this, (Object[])null);
			}catch(Exception e){
				String mensaje="Error ejecutando test RechazoInicioAsiento "+
						"iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
		
		IdentificadoresIntercambioTestHelper.save();	
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral021(){
		verificarRechazoInicio1(21);
		
		String idAsientoRegistral=getIdAsientoRegistral(21,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,1);
	}


	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral022(){
		verificarRechazoInicio1(22);
		
		String idAsientoRegistral=getIdAsientoRegistral(22,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,2);
	}


	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral023(){
		verificarRechazoInicio1(23);
	
		String idAsientoRegistral=getIdAsientoRegistral(23,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,3);
	}


	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral024(){
		verificarRechazoInicio1(24);
		
		String idAsientoRegistral=getIdAsientoRegistral(24,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,4);
	}


	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarRechazoAsientoRegistral025(){
		verificarRechazoInicio1(25);
	
		String idAsientoRegistral=getIdAsientoRegistral(25,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de rechazo a inicio de ficheros
	 * sicres correspondientes al test SIR-IN-PR-026. Entidad AY-COLINDRES 
	 * Se encarga de generar las comunicaciones realizadas desde la primera
	 * entidad registral de SIGEM en el test:
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRechazoInicio2AsientoParaTest26Operacion1() {
		try{
			reenvioAsientoRegistral(26,INFO_SORIA);
		}catch(Exception e){
			logger.error("Fallo en generación de reenvio",e);
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad.
	 * Se encarga de comprobar la lógica realizada por la primera entidad 
	 * registral de SIGEM en el test, concretamente:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarRechazoAsientoRegistral026Operacion1(){
		verificarRechazoInicio2AsientoRegistralFuncionalOperacion1(26,
				CODIGO_ENTIDAD_REGISTRAL_REC,
				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA, 
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
	
		String idAsientoRegistral=getIdAsientoRegistral(26,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de rechazo a inicio de ficheros
	 * sicres correspondientes al test SIR-IN-PR-026. Entidad DP-SORIA 
	 * Se encarga de generar las comunicaciones realizadas desde la segunda
	 * entidad registral de SIGEM en el test:
	 * @throws Exception
	 */
	@Test
	public void testRechazoInicio2AsientoParaTest26Operacion2() {
		createRechazoAsientoRegistralParaTest26();
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarRechazoAsientoRegistral026Operacion2(){
		verificarRechazoInicio2AsientoRegistralFuncionalOperacion2(26,
				CODIGO_ENTIDAD_REGISTRAL_REC,
				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA, 
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
		
		String idAsientoRegistral=getIdAsientoRegistral(26,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	


	/**
	 * Test para ejecucion de las pruebas funcionales de rechazo a inicio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-027 - SIR-IN-PR-042
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRechazoInicioAsiento27_42() {
		logger.info("Enviando rechazo a inicio asiento registral...");
		
		
		for(int i=30;i<=30;i++){	//casos de prueba
			try{
				String methodName = "createAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, 
						(Class[])null);
				AsientoRegistralFormDTO asientoForm = 
					(AsientoRegistralFormDTO)m.invoke(this, (Object[])null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
					.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
				IdentificadoresIntercambioTestHelper.put(
						PREFIJO_CODIGO_PRUEBA_INTERCAMBIO+
							StringUtils.leftPad(String.valueOf(i),3,'0'),
						asiento.getIdentificadorIntercambio());
			}catch(Exception e){
				String mensaje="Error ejecutando test RechazoInicioAsiento "+
						"iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
		IdentificadoresIntercambioTestHelper.save();	
	}
	
	
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			1 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest21(){
		//String numeroRegistro = "201200100000021";
		try{
			rechazarAsientoRegistral(21,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			2 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest22(){
		//String numeroRegistro = "201200100000022";
		try{
			rechazarAsientoRegistral(22,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			3 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest23(){
		//String numeroRegistro = "201200100000023";
		try{
			rechazarAsientoRegistral(23,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			4 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest24(){	
		//String numeroRegistro = "201200100000024";
		try{
			rechazarAsientoRegistral(24,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Generación de mensaje de rechazo
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			5 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest25(){
		//String numeroRegistro = "201200100000025";	
		try{
			rechazarAsientoRegistral(25,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Se encarga de generar las comunicaciones realizadas desde la segunda
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin representante.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvío
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion:			Rechazo a inicio
	 * Adjuntos:			5 Ficheros + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	protected void createRechazoAsientoRegistralParaTest26(){
		//String numeroRegistro = "201200100000026";
		try{
			rechazarAsientoRegistral(26,
				TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL.getValue(),
				INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo",e);
		}
	}	
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin 
	 * 						representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			1 Fichero + Firmas
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest27() {
		String numeroRegistro = "201200100000027";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con 
	 * 						representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			2 Fichero + Firmas
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest28() {
		String numeroRegistro = "201200100000028";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			Sin organismo origen. Con dos o más interesados 
	 * 						con/sin representantes
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			3 Fichero + Firmas
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest29() {
		String numeroRegistro = "201200100000029";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Sin interesados.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			4 Fichero + Firmas
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest30(){
		String numeroRegistro = "201200100000030";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getInteresados().clear();
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin 
	 * 						representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest31(){
		String numeroRegistro = "201200100000031";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
			
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 * 
	 * @return un AsientoRegistralFormDTO con toda la información necesaria
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest32(){
		String numeroRegistro = "201200100000032";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
			
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest33(){
		String numeroRegistro = "201200100000033";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
			
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio.
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con 
	 * 						representante.
	 * Destinatario:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest34(){
		String numeroRegistro = "201200100000034";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			1 fichero  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest35(){
		String numeroRegistro = "201200100000035";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);		
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			2 ficheros  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest36(){
		String numeroRegistro = "201200100000036";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			3 ficheros  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest37(){
		String numeroRegistro = "201200100000037";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Sin interesado
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			4 ficheros  +  firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest38(){
		String numeroRegistro = "201200100000038";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getInteresados().clear();
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest39(){
		String numeroRegistro = "201200100000039";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest40(){
		String numeroRegistro = "201200100000040";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest41(){
		String numeroRegistro = "201200100000041";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * rechazado a inicio. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado 
	 * 						con representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Rechazo a inicio
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createAsientoRegistralParaTest42(){
		String numeroRegistro = "201200100000042";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	/**
	 * Verifica desde oficina de destino, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar 
	 */
	protected void verificarRechazoInicio1(int numPrueba){
		verificarRechazoInicio1y4AsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_REC,
			DESCRIPCION_ENTIDAD_REGISTRAL_REC,
			null,null,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
			DESCRIPCION_ENTIDAD_COLINDRES,
			CODIGO_UNIDAD_TRAMITACION_COLINDRES,
			DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral027(){
		verificarRechazoInicio3(27,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral028(){
		verificarRechazoInicio3(28,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral029(){
		verificarRechazoInicio3(29,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral030(){
		verificarRechazoInicio3(30,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral031(){
		verificarRechazoInicio3(31,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral032(){
		verificarRechazoInicio3(32,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral033(){
		verificarRechazoInicio3(33,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral034(){
		verificarRechazoInicio3(34,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 *		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarRechazoInicio3(int numPrueba,boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarRechazoInicio3AsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
			DESCRIPCION_ENTIDAD_COLINDRES,
			codigoUnidadTramitacion,descripcionUnidadTramitacion,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral035(){
		verificarRechazoInicio4(35,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral036(){
		verificarRechazoInicio4(36,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral037(){
		verificarRechazoInicio4(37,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral038(){
		verificarRechazoInicio4(38,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral039(){
		verificarRechazoInicio4(39,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral040(){
		verificarRechazoInicio4(40,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral041(){
		verificarRechazoInicio4(41,false);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 */
	@Test
	public void verificarRechazoAsientoRegistral042(){
		verificarRechazoInicio4(42,true);
	}
	
	/**
	 * Verifica desde oficina de origen, usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 *		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarRechazoInicio4(int numPrueba,boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarRechazoInicio1y4AsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
			DESCRIPCION_ENTIDAD_COLINDRES,
			codigoUnidadTramitacion,descripcionUnidadTramitacion,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de rechazo inico 1 y 4:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Código de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralIntermedia
	 * 		Código de la entidad registral intermedia
	 * @param descripcionEntidadRegistralIntermedia
	 * 		Descripcion de la unidad de tramitaciones intermedia
	 * @param unidadTramitacionIntermedia
	 * 		Código de la unidad de tramitacion intermedia
	 * @param descripcionUnidadTramitacionIntermedia
	 * 		Descripcion de la entidad registrar intermedia
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 *		Codigo de la unidad de tramitacion destino 
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de la unidad de tramitacion destino
	 */
	protected void verificarRechazoInicio1y4AsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralIntermedia,String descripcionEntidadRegistralIntermedia,
			String unidadTramitacionIntermedia,String descripcionUnidadTramitacionIntermedia,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				null,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,EstadoTrazabilidadEnum.REGISTRO_ENVIADO ,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de rechazo inico 2:
	 * Sólo engloba las comunicaciones realizadas por la primera entidad 
	 * registral de SIGEM en el caso de prueba. Concretamente:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Código de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralIntermedia
	 * 		Código de la entidad registral intermedia
	 * @param descripcionEntidadRegistralIntermedia
	 * 		Descripcion de la unidad de tramitaciones intermedia
	 * @param unidadTramitacionIntermedia
	 * 		Código de la unidad de tramitacion intermedia
	 * @param descripcionUnidadTramitacionIntermedia
	 * 		Descripcion de la entidad registrar intermedia
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 *		Codigo de la unidad de tramitacion destino 
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de la unidad de tramitacion destino
	 */
	protected void verificarRechazoInicio2AsientoRegistralFuncionalOperacion1(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralIntermedia,String descripcionEntidadRegistralIntermedia,
			String unidadTramitacionIntermedia,String descripcionUnidadTramitacionIntermedia,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO,
				null,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralDestino,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO ,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralDestino,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de rechazo inico 2:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Código de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralIntermedia
	 * 		Código de la entidad registral intermedia
	 * @param descripcionEntidadRegistralIntermedia
	 * 		Descripcion de la unidad de tramitaciones intermedia
	 * @param unidadTramitacionIntermedia
	 * 		Código de la unidad de tramitacion intermedia
	 * @param descripcionUnidadTramitacionIntermedia
	 * 		Descripcion de la entidad registrar intermedia
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 *		Codigo de la unidad de tramitacion destino 
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de la unidad de tramitacion destino
	 */
	protected void verificarRechazoInicio2AsientoRegistralFuncionalOperacion2(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralIntermedia,String descripcionEntidadRegistralIntermedia,
			String unidadTramitacionIntermedia,String descripcionUnidadTramitacionIntermedia,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				null,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO,
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,EstadoTrazabilidadEnum.REGISTRO_ENVIADO ,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia, descripcionEntidadRegistralIntermedia,
				unidadTramitacionIntermedia, descripcionUnidadTramitacionIntermedia);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de rechazo inico 2:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de rechazo a inicio.
	 * - Con posterioridad, la recepción de mensaje ACK del rechazo.
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Código de la entidad registral de origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion de la entidad registral de origen
	 * @param unidadTramitacionOrigen
	 * 		Código de la unidad de tramitacion de origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion de la unidad de tramitacion origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo de la entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion de la entidad registral destino
	 * @param unidadTramitacionDestino
	 *		Codigo de la unidad de tramitacion destino 
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion de la unidad de tramitacion destino
	 */
	protected void verificarRechazoInicio3AsientoRegistralFuncional(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,null,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Genera y envía el mensaje de rechazo a partir de los datos que recibidos,
	 * a la entidad registral correpondiente
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param tipoRechazo
	 * 		Tipo de rechazo a generar
	 * @param entidadSIGEM
	 * 		Objeto de tipo InfoEntidadRegistral con la información de la entidad
	 * 		registral de SIGEM, que recibio el mensaje a rechazar. 
	 */
	private void rechazarAsientoRegistral(int numPrueba,int tipoRechazo,
			InfoEntidadRegistral entidadSIGEM){
		String identificadorIntercambio=getIdentificadorIntercambioRegistral(numPrueba);
		logger.info("Rechazar el asiento registral con identificador de intercambio: {}", 
				identificadorIntercambio);
		
		// Identificador del asiento registral a validar/confirmar
		String idAsientoRegistral = getIdAsientoRegistral(
				entidadSIGEM.getCodigo(), identificadorIntercambio);

		InfoRechazoDTO infoRechazo=new InfoRechazoDTO();
		infoRechazo.setTipoRechazo(tipoRechazo);
		infoRechazo.setAplicacion(APLICACION);
		infoRechazo.setDescripcion(DESCRIPCION_RECHAZO_PARA_TEST+numPrueba);
		infoRechazo.setContacto(entidadSIGEM.getContacto());
		infoRechazo.setUsuario(entidadSIGEM.getUsuario());
		
		//infoReenvio.setDescripcionEntidadRegistralDestino(value);
		getIntercambioRegistralWS().rechazarAsientoRegistral(idAsientoRegistral,
				infoRechazo); 
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de rechazar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.RECHAZADO.getValue(), estado.getEstado());
	}
	
	
	/**
	 * Test para ejecucion de las pruebas funcionales de reenvio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-043 - SIR-IN-PR-050
	 * Envío del fichero de intercambio, realizado desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES
	 */
	@Test
	public void testReenvio1EnviarAsientoOperacion1_43_50() {
		logger.info("Enviando asiento registral (test reenvio)...");
		for(int i=44;i<=50;i++){	//casos de prueba
			try{
				String methodName = "createReenvioAsientoRegistralParaTest" 
					+ i+"Operacion1";
				Method m = this.getClass().getDeclaredMethod(methodName, 
						(Class[])null);
				AsientoRegistralFormDTO asientoForm = 
					(AsientoRegistralFormDTO)m.invoke(this, (Object[])null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
					.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
				IdentificadoresIntercambioTestHelper.put(
						PREFIJO_CODIGO_PRUEBA_INTERCAMBIO+
							StringUtils.leftPad(String.valueOf(i),3,'0'),
						asiento.getIdentificadorIntercambio());
			}catch(Exception e){
				String mensaje="Error ejecutando test ReenvioEnviarsiento "+
						"iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
		IdentificadoresIntercambioTestHelper.save();	
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral043Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(43,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral044ParcialOperacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(44,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral045Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(45,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral046Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(46,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral047Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(47,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral048Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(48,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral049Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(49,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral050Operacion1(){
		verificarReenvio1AsientoRegistralFuncionalOperacion1(50,true);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de reenvio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-043 - SIR-IN-PR-050
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA
	 */
	@Test
	public void testReenvio1EnviarAsientoOperacion2_43_50() {
		logger.info("Enviando asiento registral (test reenvio)...");
		for(int i=44;i<=50;i++){	//casos de prueba
			try{
				String numeroRegistro = "2012001000000"+i;
				String identificadorIntercambio=getIdentificadorIntercambioRegistral(i);
				confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_SORIA, 
						identificadorIntercambio, numeroRegistro);
			}catch(Exception e){
				String mensaje="Error ejecutando test ReenvioEnviarsiento "+
						"iteracion:"+i+". Error en generación de confirmacion";
				logger.error(mensaje,e);
			}
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral043Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(43,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral044Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(44,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral045Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(45,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral046Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(46,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral047Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(47,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral048Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(48,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral049Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(49,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral050Operacion2(){
		verificarReenvio1AsientoRegistralFuncionalOperacion2(50,true);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de reenvio de ficheros
	 * sicres correspondientes al test SIR-IN-PR-051
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReenvio2EnviarAsiento_51() {
		logger.info("Enviando asiento registral (test reenvio)...");
		try{
				reenvioAsientoRegistral(51, INFO_SORIA);
		}catch(Exception e){
			String mensaje="Error ejecutando test ReenvioEnviarsiento "+
					"iteracion:"+51;
			logger.error(mensaje,e);
		}	
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 2:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral051(){
		verificarReenvioConfirmacionAsientoRegistralFuncional(51,
				CODIGO_ENTIDAD_REGISTRAL_REC,
				DESCRIPCION_ENTIDAD_REGISTRAL_REC,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA, 
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE);
	
		String idAsientoRegistral=getIdAsientoRegistral(51,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de reenvio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-052 - SIR-IN-PR-059
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void testReenvio3EnviarAsientoOperacion1_52_59() {
		logger.info("Enviando asiento registral (test reenvio)...");
		for(int i=52;i<=59;i++){	//casos de prueba
			try{
				String methodName = "createReenvioAsientoRegistralParaTest"
					+ i+"Operacion1";
				Method m = this.getClass().getDeclaredMethod(methodName, 
						(Class[])null);
				AsientoRegistralFormDTO asientoForm = 
					(AsientoRegistralFormDTO)m.invoke(this, (Object[])null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
					.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
				IdentificadoresIntercambioTestHelper.put(
						PREFIJO_CODIGO_PRUEBA_INTERCAMBIO+
							StringUtils.leftPad(String.valueOf(i),3,'0'),
						asiento.getIdentificadorIntercambio());
			}catch(Exception e){
				String mensaje="Error ejecutando test ReenvioEnviarsiento "+
						"iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
		IdentificadoresIntercambioTestHelper.save();	
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral052Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(52,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral053Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(53,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral054Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(54,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral055Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(55,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral056Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(56,true);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral057Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(57,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral058Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(58,false);
	}


	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral059Operacion1(){
		verificarReenvio3AsientoRegistralFuncionalOperacion1(59,true);
	}


	/**
	 * Test para ejecucion de las pruebas funcionales de reenvio de ficheros
	 * sicres correspondientes a los test SIR-IN-PR-052 - SIR-IN-PR-059
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA
	 */
	@Test
	public void testReenvio3EnviarAsientoOperacion2_52_59() {
		logger.info("Enviando asiento registral prueba reenvio...");
		for(int i=52;i<=59;i++){	//casos de prueba
			try{
				//String numeroRegistro = "2012001000000"+i;	
				reenvioAsientoRegistral(i,INFO_SORIA);
			}catch(Exception e){
				String mensaje="Error ejecutando test ReenvioConfirmarAsiento "+
						"iteracion:"+i+". Fallo en generación de confirmacion";
				logger.error(mensaje,e);
			}
		}
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado 
	 * 						sin representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			1 fichero + firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest43Operacion1(){
		String numeroRegistro = "201200100000043";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			2 fichero + firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest44Operacion1(){
		String numeroRegistro = "201200100000044";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			3 fichero + firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest45Operacion1(){
		String numeroRegistro = "201200100000045";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio. 
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Sin interesado 
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			4 fichero + firmas electr.
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest46Operacion1(){
		String numeroRegistro = "201200100000046";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		asientoForm.getInteresados().clear();
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
		
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin
	 * 						representante 
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest47Operacion1(){
		String numeroRegistro = "201200100000047";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con
	 * 						representante 
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest48Operacion1(){
		String numeroRegistro = "201200100000048";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest49Operacion1(){
		String numeroRegistro = "201200100000049";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con 
	 * 						representante
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		DIPUTACIÓN DE SORIA
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest50Operacion1(){
		String numeroRegistro = "201200100000050";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());	
		return asientoForm;
	}

	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin 
	 * 						representante
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			1 fichero  +  firmas electr
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest52Operacion1(){
		String numeroRegistro = "201200100000052";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			2 fichero  +  firmas electr
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest53Operacion1(){
		String numeroRegistro = "201200100000053";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			3 fichero  +  firmas electr
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest54Operacion1(){
		String numeroRegistro = "201200100000054";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Sin interesado.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			4 fichero  +  firmas electr
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest55Operacion1(){
		String numeroRegistro = "201200100000055";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.getInteresados().clear();
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado sin
	 * 						representante.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest56Operacion1(){
		String numeroRegistro = "201200100000056";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		TestUtils.vaciarRepresentante(asientoForm.getInteresados().get(0));
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con
	 * 						representante.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest57Operacion1(){
		String numeroRegistro = "201200100000057";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			Sin organismo origen. Con 2 o más interesados 
	 * 						con/sin representantes.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest58Operacion1(){
		String numeroRegistro = "201200100000058";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				null,null,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		InteresadoFormDTO interesado=TestUtils.createInteresadoFormDTO("2");
		asientoForm.getInteresados().add(interesado);
		
		return asientoForm;
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		DIPUTACIÓN DE SORIA
	 * Operación 1:			Reenvio
	 * Destinatario 2:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 2:			Confirmacion
	 * Adjuntos:			N/A
	 * Tipo de Registro:	Salida
	 */
	protected AsientoRegistralFormDTO createReenvioAsientoRegistralParaTest59Operacion1(){
		String numeroRegistro = "201200100000059";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_SORIA,
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		asientoForm.setTipoRegistro(TipoRegistroEnum.SALIDA.getValue());
		
		return asientoForm;
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral052Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(52,true);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral053Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(53,false);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral054Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(54,false);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral055Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(55,true);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral056Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(56,true);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral057Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(57,false);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral058Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(58,false);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 */
	@Test
	public void verificarReenvioConfirmacionAsientoRegistral059Operacion2(){
		verificarReenvio3AsientoRegistralFuncionalOperacion2(59,true);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 *  
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 * 		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarReenvio1AsientoRegistralFuncionalOperacion1(int numPrueba,
			boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarReenvio1Operacion1(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
			DESCRIPCION_ENTIDAD_COLINDRES,
			codigoUnidadTramitacion,descripcionUnidadTramitacion,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 *  
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 * 		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarReenvio1AsientoRegistralFuncionalOperacion2(int numPrueba,
			boolean organismoOrigen){
		
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarReenvioConfirmacionAsientoRegistralFuncional(numPrueba,
			CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
			DESCRIPCION_ENTIDAD_COLINDRES,
			codigoUnidadTramitacion,descripcionUnidadTramitacion,
			CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO, 
			DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
			CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
			DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
			CODIGO_ENTIDAD_REGISTRAL_SORIA, 
			DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
			CODIGO_UNIDAD_TRAMITACION_SORIA,
			DESCRIPCION_UNIDAD_TRAMITACION_SORIA);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 *  
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 * 		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarReenvio3AsientoRegistralFuncionalOperacion1(int numPrueba,
			boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarReenvio3Operacion1(numPrueba,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				codigoUnidadTramitacion,descripcionUnidadTramitacion,
				CODIGO_ENTIDAD_REGISTRAL_SORIA, 
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA 
	 *  
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param organismoOrigen
	 * 		Indicador para saber si los campos correspondiente a la unidad de
	 *		tramitacion deben estar vacios.
	 */
	protected void verificarReenvio3AsientoRegistralFuncionalOperacion2(int numPrueba,
			boolean organismoOrigen){
		String codigoUnidadTramitacion=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacion=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		if(!organismoOrigen){
			codigoUnidadTramitacion=null;
			descripcionUnidadTramitacion=null;
		}
		
		verificarReenvioConfirmacionAsientoRegistralFuncional(numPrueba,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				codigoUnidadTramitacion,descripcionUnidadTramitacion,
				CODIGO_ENTIDAD_REGISTRAL_SORIA, 
				DESCRIPCION_ENTIDAD_REGISTRAL_SORIA,
				CODIGO_UNIDAD_TRAMITACION_SORIA,
				DESCRIPCION_UNIDAD_TRAMITACION_SORIA,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE, 
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 1:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 * 
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo entidad registral origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion entidad registral origen
	 * @param unidadTramitacionOrigen
	 * 		Codigo unidad de tramitacion origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion unidad de tramitacion origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Codigo unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion unidad de tramitacion destino
	 */
	protected void verificarReenvio1Operacion1(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
		
		//Existe entrada para el ACK y es correcta?
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				null, null);
	
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				null, null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);	
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad, Test de reenvio 3:
	 * - El envío de fichero de intercambio.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 *  
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param codigoEntidadRegistralOrigen
	 * 		Codigo entidad registral origen
	 * @param descripcionEntidadRegistralOrigen
	 * 		Descripcion entidad registral origen
	 * @param unidadTramitacionOrigen
	 * 		Codigo unidad de tramitacion origen
	 * @param descripcionUnidadTramitacionOrigen
	 * 		Descripcion unidad de tramitacion origen
	 * @param codigoEntidadRegistralDestino
	 * 		Codigo entidad registral destino
	 * @param descripcionEntidadRegistralDestino
	 * 		Descripcion entidad registral destino
	 * @param unidadTramitacionDestino
	 * 		Codigo unidad de tramitacion destino
	 * @param descripcionUnidadTramitacionDestino
	 * 		Descripcion unidad de tramitacion destino
	 */
	protected void verificarReenvio3Operacion1(int numPrueba,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){

		List<TrazabilidadDTO> trazas=getTrazas(numPrueba,codigoEntidadRegistralOrigen);
	
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				null,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				null, null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);	
	}
	
	
	/**
	 * Genera el reenvio del fichero intercambio a partir de los datos recibidos
	 * a la entidad registral correpondiente
	 * @param numPrueba
	 * 		Identificador numerico del test de intercambio a verificar
	 * @param entidadSIGEM
	 * 		Objeto de tipo InfoEntidadRegistral con la información de la entidad
	 * 		registral de SIGEM, que recibio el mensaje a rechazar. 
	 */
	protected void reenvioAsientoRegistral(int numPrueba,
			InfoEntidadRegistral entidadSIGEM){
		String identificadorIntercambio=getIdentificadorIntercambioRegistral(numPrueba);
		logger.info("Reenviando el asiento registral con identificador de intercambio: {}", identificadorIntercambio);
		
		// Identificador del asiento registral a validar/confirmar
		String idAsientoRegistral = getIdAsientoRegistral(
				entidadSIGEM.getCodigo(), identificadorIntercambio);
		

		InfoReenvioDTO infoReenvio=new InfoReenvioDTO();
		infoReenvio.setCodigoEntidadRegistralDestino(CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		infoReenvio.setDescripcionEntidadRegistralDestino(DESCRIPCION_ENTIDAD_COLINDRES);
		infoReenvio.setCodigoUnidadTramitacionDestino(CODIGO_UNIDAD_TRAMITACION_COLINDRES);
		infoReenvio.setDescripcionUnidadTramitacionDestino(DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES);
		
		infoReenvio.setAplicacion(APLICACION);
		infoReenvio.setDescripcion(DESCRIPCION_REENVIO_PARA_TEST+numPrueba);
		infoReenvio.setContacto(entidadSIGEM.getContacto());
		infoReenvio.setUsuario(entidadSIGEM.getUsuario());
		
		//infoReenvio.setDescripcionEntidadRegistralDestino(value);
		getIntercambioRegistralWS().reenviarAsientoRegistral(idAsientoRegistral, infoReenvio); 
		
		EstadoAsientoRegistralDTO estado = getIntercambioRegistralWS().getEstadoAsientoRegistral(idAsientoRegistral);
		logger.info("Estado después de reenviar: {}", estado.getEstado());
		Assert.assertEquals(EstadoAsientoRegistralEnum.REENVIADO.getValue(), estado.getEstado());
	}
	
	/**
	 * Intercambio de asiento registral entre cinco oficinas de registro. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion 2:			Rechazo a Origen
	 * Destinatario 3:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 3:			Reenvio
	 * Destinatario 4:		DIPUTACIÓN DE SORIA
	 * Operación 4:			Confirmacion
	 * Adjuntos:			5 ficheros  + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY_COLINDRES
	 */
	@Test
	public void testMultipleParaTest60Operacion1_60() {
		//String numeroRegistro = "201200100000060";
		try{
			rechazarAsientoRegistral(60,
					TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.getValue(),
					INFO_COLINDRES);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo (multiple)",e);
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de rechazo.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial
	 */
	@Test
	public void verificarMultipleAsientoRegistral060Operacion1(){
		verificarMultipleOperacion1();
	
		String idAsientoRegistral=getIdAsientoRegistral(60,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}


	/**
	 * Intercambio de asiento registral entre cinco oficinas de registro. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario 1:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación 1:			Reenvio
	 * Destinatario 2:		AYUNTAMIENTO DE COLINDRES
	 * Operacion 2:			Rechazo a Origen
	 * Destinatario 3:		MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)
	 * Operación 3:			Reenvio
	 * Destinatario 4:		DIPUTACIÓN DE SORIA
	 * Operación 4:			Confirmacion
	 * Adjuntos:			5 ficheros  + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA
	 */
	@Test
	public void testMultipleParaTest60Operacion2_60() {
		String numeroRegistro = "201200100000060";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL_SORIA;  
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(60);	
		try{
			confirmarAsientoRegistral(codigoEntidadRegistral, 
					identificadorIntercambio, numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion (multiple)",e);
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de rechazo.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarMultipleAsientoRegistral060Operacion2(){
		verificarMultipleAsientoRegistralFuncional();

		String idAsientoRegistral=getIdAsientoRegistral(60,CODIGO_ENTIDAD_REGISTRAL_SORIA);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de rechazo.
	 * Se encarga de las comunicaciones realizadas desde la primera entidad de 
	 * SIGEM, en el caso de prueba. AY-COLINDRES 
	 */
	protected void verificarMultipleOperacion1(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_REC;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_REGISTRAL_REC;
		String unidadTramitacionOrigen=null;
		String descripcionUnidadTramitacionOrigen=null;
		
		String codigoEntidadRegistralIntermedia1=CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String descripcionEntidadRegistralIntermedia1=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String unidadTramitacionIntermedia1=CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		String descripcionUnidadTramitacionIntermedia1=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		
		String codigoEntidadRegistralDestino1=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralDestino1=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionDestino1=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionDestino1=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
				
		List<TrazabilidadDTO> trazas=getTrazas(60,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				null,
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, 
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1,
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, 
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);	
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepción de mensaje de rechazo.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, el reenvio del fichero de intercambio.
	 * - Con posterioridad, la recepción de mensaje ACK del reenvio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Se encarga de las comunicaciones realizadas desde la segunda entidad de 
	 * SIGEM, en el caso de prueba. DP-SORIA
	 */
	protected void verificarMultipleAsientoRegistralFuncional(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_REC;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_REGISTRAL_REC;
		String unidadTramitacionOrigen=null;
		String descripcionUnidadTramitacionOrigen=null;
		
		String codigoEntidadRegistralIntermedia1=CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String descripcionEntidadRegistralIntermedia1=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String unidadTramitacionIntermedia1=CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		String descripcionUnidadTramitacionIntermedia1=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		
		String codigoEntidadRegistralDestino1=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralDestino1=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionDestino1=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionDestino1=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		
		String codigoEntidadRegistralIntermedia2=CODIGO_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String descripcionEntidadRegistralIntermedia2=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_MUFACE;
		String unidadTramitacionIntermedia2=CODIGO_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		String descripcionUnidadTramitacionIntermedia2=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_MUFACE;
		
		String codigoEntidadRegistralDestino2=CODIGO_ENTIDAD_REGISTRAL_SORIA;
		String descripcionEntidadRegistralDestino2=DESCRIPCION_ENTIDAD_REGISTRAL_SORIA;
		String unidadTramitacionDestino2=CODIGO_UNIDAD_TRAMITACION_SORIA;
		String descripcionUnidadTramitacionDestino2=DESCRIPCION_UNIDAD_TRAMITACION_SORIA;
				
		List<TrazabilidadDTO> trazas=getTrazas(60,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino2, descripcionEntidadRegistralDestino2,
				unidadTramitacionDestino2, descripcionUnidadTramitacionDestino2,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino2, descripcionEntidadRegistralDestino2,
				unidadTramitacionDestino2, descripcionUnidadTramitacionDestino2,
				codigoEntidadRegistralIntermedia2, descripcionEntidadRegistralIntermedia2,
				unidadTramitacionIntermedia2, descripcionUnidadTramitacionIntermedia2);
		
		TrazabilidadDTO trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia2, descripcionEntidadRegistralIntermedia2,
				unidadTramitacionIntermedia2, descripcionUnidadTramitacionIntermedia2,
				codigoEntidadRegistralDestino2, descripcionEntidadRegistralDestino2,
				unidadTramitacionDestino2, descripcionUnidadTramitacionDestino2);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia2, descripcionEntidadRegistralIntermedia2,
				unidadTramitacionIntermedia2, descripcionUnidadTramitacionIntermedia2,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);
		
		trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1,
				codigoEntidadRegistralIntermedia2, descripcionEntidadRegistralIntermedia2,
				unidadTramitacionIntermedia2, descripcionUnidadTramitacionIntermedia2);
		
		TrazabilidadDTO trazaRechazado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_RECHAZADO, 
				trazaReenviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, 
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);
		
		trazaReenviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_REENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1,
				codigoEntidadRegistralDestino1, descripcionEntidadRegistralDestino1,
				unidadTramitacionDestino1, descripcionUnidadTramitacionDestino1);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA, 
				trazaRechazado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralIntermedia1, descripcionEntidadRegistralIntermedia1,
				unidadTramitacionIntermedia1, descripcionUnidadTramitacionIntermedia1);	
	}
	
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * confirmado, pero con envío por duplicado. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado con 
	 * 						representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operación:			Confirmación y envío de ACK
	 * Adjuntos:			5 ficheros  + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	@Test
	public void testDuplicidadParaTest_61() {
		String numeroRegistro = "201200100000061";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(61);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
					identificadorIntercambio, numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo (multiple)",e);
		}
	}
	
	@Test
	public void testDuplicidadParaTest61_Operacion2(){
		
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta =null;
		
		try {
			
		//File fichero = new ClassPathResource("/xmlDupli/SIR-IN-PR-061-ENV1.xml").getFile();
		File fichero = new ClassPathResource("/xmlDupli/prueba61.xml").getFile();
		
		
			respuesta = WSSIR8AWSClient.envioFicherosAAplicacion(
					xmlFileToString(fichero), "", new OctetStream[0]);
		} catch (Exception e) {
			logger.error("Fallo test duplicidad1",e);
			
		}
		
		Assert.assertNotNull(respuesta);
		
	}
	
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el envío del mensaje ACK.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarDuplicidadAsientoRegistral061(){
		verificarDuplicidad1AsientoRegistralFuncional_061();
		
		String idAsientoRegistral=getIdAsientoRegistral(61,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el envío del mensaje ACK.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 */
	protected void verificarDuplicidad1AsientoRegistralFuncional_061(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_REC;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_REGISTRAL_REC;
		String unidadTramitacionOrigen=null;
		String descripcionUnidadTramitacionOrigen=null;
		
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
				
		List<TrazabilidadDTO> trazas=getTrazas(61,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaEnviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaEnviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaEnviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	
	@Test
	public void testDuplicidadParaTest62_Operacion1(){
		
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta =null;
		
		try {
			
		//File fichero = new ClassPathResource("/xmlDupli/SIR-IN-PR-061-ENV1.xml").getFile();
		File fichero = new ClassPathResource("/xmlDupli/prueba62.xml").getFile();
		
		
			respuesta = WSSIR8AWSClient.envioFicherosAAplicacion(
					xmlFileToString(fichero), "", new OctetStream[0]);
		} catch (Exception e) {
			logger.error("Fallo test duplicidad1",e);
			
		}
		
		Assert.assertNotNull(respuesta);
		
	}
	
	@Test
	public void testDuplicidadParaTest65_Operacion1(){
		
		es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS respuesta =null;
		
		try {
			
		//File fichero = new ClassPathResource("/xmlDupli/SIR-IN-PR-061-ENV1.xml").getFile();
		File fichero = new ClassPathResource("/xmlDupli/prueba65.xml").getFile();
		
		
			respuesta = WSSIR8AWSClient.envioFicherosAAplicacion(
					xmlFileToString(fichero), "", new OctetStream[0]);
		} catch (Exception e) {
			logger.error("Fallo test duplicidad1",e);
			
		}
		
		Assert.assertNotNull(respuesta);
		
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es 
	 * confirmado, pero con envío por duplicado.
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin 
	 * 						representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operación:			Confirmación
	 * Adjuntos:			5 ficheros  + Justificante + xml registro
	 * Tipo de Registro:	Entrada
	 */
	@Test
	public void testCreateDuplicidadParaTest62() {
		String numeroRegistro = "201200100000062";
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(62);	
		try{
			confirmarAsientoRegistral(CODIGO_ENTIDAD_REGISTRAL_COLINDRES, 
					identificadorIntercambio, numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de rechazo (multiple)",e);
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el envío del mensaje ACK.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados, 
	 * justificante y xml del envio inicial 
	 */
	@Test
	public void verificarDuplicidadAsientoRegistral062(){
		verificarDuplicidad2AsientoRegistralFuncional();
		String idAsientoRegistral=getIdAsientoRegistral(62,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
		verificarAnexosAsientoRegistral(idAsientoRegistral,5);
	}
	
	
	@Test
	public void testDuplicidadParaTest63_Operacion1(){
		
		RespuestaWS respuesta =null;
		
		try {
			
		//File fichero = new ClassPathResource("/xmlDupli/SIR-IN-PR-061-ENV1.xml").getFile();
		File fichero = new ClassPathResource("/xmlDupli/prueba63.xml").getFile();
		String mensaje=xmlFileToString(fichero);
		
			respuesta = WSSIR9WSClient.envioMensajeDatosControlAAplicacion(mensaje, "DDD");
					
		} catch (Exception e) {
			logger.error("Fallo test duplicidad1",e);
			
		}
		
		Assert.assertNotNull(respuesta);
		
	}
	
	
	@Test
	public void testDuplicidadParaTest64_Operacion1(){
		
		RespuestaWS respuesta =null;
		
		try {
			
		//File fichero = new ClassPathResource("/xmlDupli/SIR-IN-PR-061-ENV1.xml").getFile();
		File fichero = new ClassPathResource("/xmlDupli/prueba64.xml").getFile();
		String mensaje=xmlFileToString(fichero);
		
			respuesta = WSSIR9WSClient.envioMensajeDatosControlAAplicacion(mensaje, "DDD");
					
		} catch (Exception e) {
			logger.error("Fallo test duplicidad1",e);
			
		}
		
		Assert.assertNotNull(respuesta);
		
	}
	
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, el envío del mensaje ACK.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 */
	protected void verificarDuplicidad2AsientoRegistralFuncional(){
		String codigoEntidadRegistralREC=CODIGO_ENTIDAD_REGISTRAL_REC;
		String descripcionEntidadRegistralREC=DESCRIPCION_ENTIDAD_REGISTRAL_REC;
		
		String codigoEntidadRegistralOrigen=codigoEntidadRegistralREC;
		String descripcionEntidadRegistralOrigen=descripcionEntidadRegistralREC;
		String unidadTramitacionOrigen=null;
		String descripcionUnidadTramitacionOrigen=null;
		
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
				
		List<TrazabilidadDTO> trazas=getTrazas(61,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaEnviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		trazaEnviado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaEnviado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	
	@Test
	public void testDuplicidad34Asiento_63_64() {
		logger.info("Enviando asiento registral prueba duplicidad 3 y 4...");
		for(int i=63;i<=64;i++){	//casos de prueba
			try{
				
				String methodName = "createDuplicidadAsientoRegistralParaTest" + i;
				Method m = this.getClass().getDeclaredMethod(methodName, 
						(Class[])null);
				AsientoRegistralFormDTO asientoForm =(AsientoRegistralFormDTO)
						m.invoke(this,(Object[])null);
				AsientoRegistralDTO asiento = getIntercambioRegistralWS()
					.enviarAsientoRegistral(asientoForm);
				Assert.assertNotNull("Asiento registral nulo", asiento);
				Assert.assertTrue(
						"El identificador del asiento registral es nulo",
						StringUtils.isNotBlank(asiento.getId()));
				Assert.assertTrue(EstadoAsientoRegistralEnum.ENVIADO
								.getValue() == asiento.getEstado());
				logger.info("Asiento registral enviado: {}", toString(asiento));
				IdentificadoresIntercambioTestHelper.put(
						PREFIJO_CODIGO_PRUEBA_INTERCAMBIO+
							StringUtils.leftPad(String.valueOf(i),3,'0'),
						asiento.getIdentificadorIntercambio());			
			}catch(Exception e){
				String mensaje="Error ejecutando test ReenvioConfirmarAsiento "+
						"iteracion:"+i;
				logger.error(mensaje,e);
			}
		}
	}
	
	/**
	 * Intercambio de asiento registral entre tres oficinas de registro, que es 
	 * confirmado a inicio. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado 
	 * 						sin representante.
	 * Destinatario:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación:			Confirmacion
	 * Adjuntos:			4 fichero + firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createDuplicidadAsientoRegistralParaTest63(){
		String numeroRegistro = "201200100000063";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion. 
	 */
	@Test
	public void verificarDuplicidadAsientoRegistral063(){
		verificarDuplicidad3AsientoRegistralFuncional();
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion. 
	 */
	protected void verificarDuplicidad3AsientoRegistralFuncional(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionOrigen=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionOrigen=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String unidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
				
		List<TrazabilidadDTO> trazas=getTrazas(63,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es
	 * confirmado, pero con envío por duplicado de ACK. 
	 * Remitente: 			AYUNTAMIENTO DE COLINDRES. Con 1 interesado con
	 * 						representante.
	 * Destinatario:		INSTITUTO NACIONAL DE CONSUMO
	 * Operación:			Confirmacion
	 * Adjuntos:			4 fichero + firmas electr.
	 * Tipo de Registro:	Entrada
	 */
	protected AsientoRegistralFormDTO createDuplicidadAsientoRegistralParaTest64(){
		String numeroRegistro = "201200100000064";
		AsientoRegistralFormDTO asientoForm = createAsientoRegistralFormDTONumeroRegistro(
				numeroRegistro, CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES,
				CODIGO_UNIDAD_TRAMITACION_COLINDRES,
				DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES,
				CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO,
				CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO,
				CODIGO_ENTIDAD_REGISTRAL_COLINDRES,
				DESCRIPCION_ENTIDAD_COLINDRES);
		
		asientoForm.getAnexos().clear();
		AnexoFormDTO anexo=TestUtils.createAnexoFormDTO("1");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("2");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("3");
		asientoForm.getAnexos().add(anexo);
		anexo=TestUtils.createAnexoFormDTO("4");
		asientoForm.getAnexos().add(anexo);
		return asientoForm;
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion. 
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 */
	@Test
	public void verificarDuplicidadAsientoRegistral064(){
		verificarDuplicidad4AsientoRegistralFuncional();
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion. 
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 */
	protected void verificarDuplicidad4AsientoRegistralFuncional(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionOrigen=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionOrigen=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
		
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO_CONSUMO;
		String unidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_DESTINO_CONSUMO;
				
		List<TrazabilidadDTO> trazas=getTrazas(63,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO, 
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	/**
	 * Intercambio de asiento registral entre dos oficinas de registro, que es
	 * confirmado, pero con error en envío y posterior envío duplicado. 
	 * Remitente: 			Sin organismo origen. Con 1 interesado sin
	 * 						representante.
	 * Destinatario:		AYUNTAMIENTO DE COLINDRES
	 * Operación:			Confirmacion
	 * Adjuntos:			0 ficheros.
	 * Tipo de Registro:	Entrada
	 */
	protected void createErrorAsientoRegistralParaTest65() {
		String numeroRegistro = "201200100000065";
		String codigoEntidadRegistral=CODIGO_ENTIDAD_REGISTRAL_COLINDRES; 
		String identificadorIntercambio= getIdentificadorIntercambioRegistral(65);	
		try{
			confirmarAsientoRegistral(codigoEntidadRegistral,identificadorIntercambio,numeroRegistro);
		}catch(Exception e){
			logger.error("Fallo en generación de confirmacion",e);
		}
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje de error.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 * Tambien realiza comprobaciones sobre el numero de ficheros anexados 
	 */
	protected void verificarErrorAsientoRegistral065(){
		verificarErrorAsientoRegistralFuncional();
		verificarAsientoRegistralError(65,CODIGO_ENTIDAD_REGISTRAL_COLINDRES);
	}
	
	/**
	 * Se encarga de realizar comprobaciones sobre los anexos de los test de
	 * de confirmacion
	 * @param idAsientoRegistral  identificador del asiento registral enviado 
	 * 		  inicialmente
	 * @param numFicherosAdjuntos numero de ficheros adjuntados, sin contar 
	 * 		  justificante y xml de registro 
	 */
	protected void verificarAsientoRegistralError(int numPrueba,String codigoEntidadRegistral){
		String idAsientoRegistral=getIdAsientoRegistral(9,codigoEntidadRegistral);
		
		AsientoRegistralDTO asiento=getIntercambioRegistralWS()
			.getAsientoRegistral(idAsientoRegistral);
		Assert.assertEquals(0,asiento.getAnexos().size());
	}
	
	/**
	 * Verifica usando el servicio de trazabilidad:
	 * - El envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje de error.
	 * - Con posterioridad, el envío de fichero de intercambio.
	 * - Con posterioridad, la recepcion del mensaje ACK.
	 * - Con posterioridad, la recepcion del mensaje de confirmacion.
	 */
	protected void verificarErrorAsientoRegistralFuncional(){
		String codigoEntidadRegistralOrigen=CODIGO_ENTIDAD_REGISTRAL_REC;
		String descripcionEntidadRegistralOrigen=DESCRIPCION_ENTIDAD_REGISTRAL_REC;
		String unidadTramitacionOrigen=null;
		String descripcionUnidadTramitacionOrigen=null;
		
		String codigoEntidadRegistralDestino=CODIGO_ENTIDAD_REGISTRAL_COLINDRES;
		String descripcionEntidadRegistralDestino=DESCRIPCION_ENTIDAD_COLINDRES;
		String unidadTramitacionDestino=CODIGO_UNIDAD_TRAMITACION_COLINDRES;
		String descripcionUnidadTramitacionDestino=DESCRIPCION_UNIDAD_TRAMITACION_COLINDRES;
				
		List<TrazabilidadDTO> trazas=getTrazas(63,codigoEntidadRegistralOrigen);
		
		TrazabilidadDTO trazaConfirmado=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_CONFIRMADO,null,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaACK=verificarTraza(trazas,
				EstadoTrazabilidadEnum.RECEPCIÓN_CORRECTA,
				trazaConfirmado.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		TrazabilidadDTO trazaEnvio=verificarTraza(trazas,
				EstadoTrazabilidadEnum.REGISTRO_ENVIADO,
				trazaACK.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
		
		TrazabilidadDTO trazaError=verificarTraza(trazas,
				EstadoTrazabilidadEnum.ERROR,
				trazaEnvio.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino,
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen);
		
		verificarTraza(trazas,
				EstadoTrazabilidadEnum.ENVIO, 
				trazaError.getFechaModificacion().toGregorianCalendar().getTime(),
				codigoEntidadRegistralOrigen, descripcionEntidadRegistralOrigen,
				unidadTramitacionOrigen, descripcionUnidadTramitacionOrigen,
				codigoEntidadRegistralDestino, descripcionEntidadRegistralDestino,
				unidadTramitacionDestino, descripcionUnidadTramitacionDestino);
	}
	
	protected void errorAsientoRegistral(String codigoEntidadRegistral,
			String identificadorIntercambio, String numeroRegistro){
		//TODO: Determinar si hace falta este método. Si es así implementarlo.
	}
	
	
	
	@Test
	public void testTrazabilidad() throws Exception {

		// Indicar el identificador de intercambio del asiento registral
		String identificadorIntercambio = "O00002062_12_00000106";
		String codigoEntidadRegistral="";
		logger.info("Obteniendo la trazabilidad del asiento registral con identificador de intercambio: {}", identificadorIntercambio);

		// Identificador del asiento registral a validar
		String idAsientoRegistral = getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);

		List<TrazabilidadDTO> trazas = getIntercambioRegistralWS().getHistoricoAsientoRegistral(idAsientoRegistral);
		Assert.assertNotNull(trazas);
		
		// Ordenar por fecha de modificación
		Collections.sort(trazas, new Comparator<TrazabilidadDTO>() {
			public int compare(TrazabilidadDTO o1, TrazabilidadDTO o2) {
				return o1.getFechaModificacion().compare(o2.getFechaModificacion());
			}
		});
		
		for (TrazabilidadDTO traza : trazas) {
			logger.info("\tTraza: {}", toString(traza));
		}
	}
	
	protected String getIdentificadorIntercambioRegistral(int idPrueba){
		return IdentificadoresIntercambioTestHelper.get(generateKeyIdentificadorPrueba(idPrueba));
	}
	
	protected String getIdAsientoRegistral(int numPrueba,String codigoEntidadRegistral){
		String identificadorIntercambio=getIdentificadorIntercambioRegistral(numPrueba);
		return getIdAsientoRegistral(codigoEntidadRegistral, identificadorIntercambio);
	}
	
	/**
	 * @param idPrueba
	 * @param value
	 */
	protected void setIdentificadorIntercambioRegistral(int idPrueba,String value){
		IdentificadoresIntercambioTestHelper.put(generateKeyIdentificadorPrueba(idPrueba),value);
	}
	
	protected String generateKeyIdentificadorPrueba(int idPrueba){
		return PREFIJO_CODIGO_PRUEBA_INTERCAMBIO+	StringUtils.leftPad(String.valueOf(idPrueba),3,'0'); 
	}
	
	protected void loadIdentificadoresIntercambioRegistral(){
		IdentificadoresIntercambioTestHelper.save();
	}
	
	protected void saveIdentificadoresIntercambioRegistral(){
		IdentificadoresIntercambioTestHelper.save();		
	}
	
	public class InfoOrigenPruebaIntercambioRegistral{
		
		
		public String codEROrigen; 
		public String descEROrigen;
		public String codUTOrigen; 
		public String descUTOrigen; 
		
		public String codERDestino; 
		public String descERDestino; 
		public String codUTDestino; 
		public String descUTDestino;
		
		public String codERInicio;
		public String descERInicio;
		
		public String codERDestinoReenvio; 
		public String descERDestinoReenvio; 
		public String codUTDestinoReenvio; 
		public String descUTDestinoReenvio;
		
		
		
		public InfoOrigenPruebaIntercambioRegistral(
				String codEROrigen, String descEROrigen, String codUTOrigen,
				String descUTOrigen, String codERDestino, String descERDestino,
				String codUTDestino, String descUTDestino, String codERInicio,
				String descERInicio,String codERDestinoReenvio, String descERDestinoReenvio,String codUTDestinoReenvio,String descUTDestinoReenvio) {
			super();
			this.codEROrigen = codEROrigen;
			this.descEROrigen = descEROrigen;
			this.codUTOrigen = codUTOrigen;
			this.descUTOrigen = descUTOrigen;
			this.codERDestino = codERDestino;
			this.descERDestino = descERDestino;
			this.codUTDestino = codUTDestino;
			this.descUTDestino = descUTDestino;
			this.codERInicio = codERInicio;
			this.descERInicio = descERInicio;
			this.codERDestinoReenvio = codERDestinoReenvio; 
			this.descERDestinoReenvio = descERDestinoReenvio; 
			this.codUTDestinoReenvio = codUTDestinoReenvio; 
			this.descUTDestinoReenvio = descUTDestinoReenvio;
		}
		
	}
	
	public static class InfoEntidadRegistral{
		private String codigo;
		private String descripcion;
		private String usuario;
		private String contacto;
		
		public InfoEntidadRegistral(String codigo,String descripcion,
				String usuario,String contacto){
			this.codigo=codigo;
			this.descripcion=descripcion;
			this.usuario=usuario;
			this.contacto=contacto;	
		}
		
		public String getCodigo() { return codigo; }
		public String getDescripcion() { return descripcion; }
		public String getUsuario() { return usuario; }
		public String getContacto() { return contacto; }
	}
}
