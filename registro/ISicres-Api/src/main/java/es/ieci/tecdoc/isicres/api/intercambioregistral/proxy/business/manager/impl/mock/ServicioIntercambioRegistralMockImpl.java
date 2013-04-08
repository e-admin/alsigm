package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.manager.impl.mock;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

public class ServicioIntercambioRegistralMockImpl implements ServicioIntercambioRegistral {
	/**
	 * Logger for this class
	 */
	// Members
		protected static final Logger logger = LoggerFactory
				.getLogger(ServicioIntercambioRegistral.class);

	public AnexoVO addAnexo(String arg0, AnexoFormVO arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		return null;
	}

	public InteresadoVO addInteresado(String arg0, InteresadoFormVO arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("addInteresado(String, InteresadoFormVO) - end");
		}
		return null;
	}

	public void anularAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void comprobarTimeOutEnvios() {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public int countAsientosRegistrales(CriteriosVO arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("countAsientosRegistrales(CriteriosVO) - end");
		}
		return 0;
	}

	public void deleteAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public AsientoRegistralVO enviarAsientoRegistral(AsientoRegistralFormVO asiento) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		AsientoRegistralVO asientoResult = generateMockAsientoRegistral(asiento);

		if (logger.isDebugEnabled()) {
			logger.debug("enviarAsientoRegistral(AsientoRegistralFormVO) - end");
		}
		return asientoResult;
	}

	public void enviarAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	private AsientoRegistralVO generateMockAsientoRegistral(AsientoRegistralFormVO asientoForm){

		AsientoRegistralVO asiento1 = new AsientoRegistralVO();

		asiento1.setId("1");

		asiento1.setCodigoEntidadRegistralOrigen(asientoForm.getCodigoEntidadRegistralOrigen());
		asiento1.setDescripcionEntidadRegistralOrigen(asientoForm.getDescripcionEntidadRegistralOrigen());


		asiento1.setCodigoEntidadRegistralInicio(asientoForm.getCodigoEntidadRegistralInicio());
		asiento1.setDescripcionEntidadRegistralInicio(asientoForm.getDescripcionEntidadRegistralInicio());

		asiento1.setCodigoEntidadRegistral(asientoForm.getCodigoEntidadRegistral());

		asiento1.setCodigoEntidadRegistralDestino(asientoForm.getCodigoEntidadRegistralDestino());
		asiento1.setDescripcionEntidadRegistralDestino(asientoForm.getDescripcionEntidadRegistralDestino());


		asiento1.setFechaEstado(Calendar.getInstance().getTime());
		asiento1.setIdentificadorIntercambio("ER1_11_00000120");
		asiento1.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		Calendar.getInstance().set(2011, 2, 1);
		asiento1.setFechaRegistro(Calendar.getInstance().getTime());
		asiento1.setNumeroRegistro(asientoForm.getNumeroRegistro());

		asiento1.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
				Calendar.getInstance().set(2011, 1, 1);
		asiento1.setEstado(EstadoAsientoRegistralEnum.ENVIADO);

		return asiento1;
	}

	public List<AsientoRegistralVO> findAsientosRegistrales(CriteriosVO criterios) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		// DE momento retornamos siempre para bandeja de entrada
		List<AsientoRegistralVO> listaAsientos = new ArrayList<AsientoRegistralVO>();
		AsientoRegistralVO asiento1 = new AsientoRegistralVO();
		AsientoRegistralVO asiento2 = new AsientoRegistralVO();

		asiento1.setId("1");

		asiento1.setCodigoEntidadRegistralOrigen("2939");
		asiento1.setDescripcionEntidadRegistralOrigen("Ayuntamiento de Santander");

		asiento1.setFechaEstado(Calendar.getInstance().getTime());
		asiento1.setIdentificadorIntercambio("1");
		asiento1.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		Calendar.getInstance().set(2011, 2, 1);
		asiento1.setFechaRegistro(Calendar.getInstance().getTime());
		asiento1.setNumeroRegistro("20110099090000111");

		asiento2.setId("2");
		asiento1.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
		asiento2.setCodigoEntidadRegistralOrigen("2939");
		asiento2.setDescripcionEntidadRegistralOrigen("Comunidad Foral de Navarra");

		asiento2.setFechaEstado(Calendar.getInstance().getTime());
		asiento2.setIdentificadorIntercambio("2");
		asiento2.setTipoRegistro(TipoRegistroEnum.SALIDA);
		Calendar.getInstance().set(2011, 1, 1);
		asiento2.setFechaRegistro(Calendar.getInstance().getTime());
		asiento2.setNumeroRegistro("20110099090000112");
		asiento2.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_REQUERIDA);
		for (CriterioVO criterio : criterios.getCriterios()) {
			if (criterio.getNombre().equals(CriterioEnum.ASIENTO_ESTADO)) {
				Object valor = criterio.getValor();
				if (valor instanceof Integer) {
					int estado = ((Integer) criterio.getValor()).intValue();
					asiento1.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(estado));
					asiento2.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(estado));
				}
				if (valor instanceof Integer[]){
					int estado = ((Integer[]) criterio.getValor())[0].intValue();
					asiento1.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(estado));
					asiento2.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(estado));
				}

			}
		}

		listaAsientos.add(asiento1);
		listaAsientos.add(asiento2);

		if (logger.isDebugEnabled()) {
			logger.debug("findAsientosRegistrales(CriteriosVO) - end");
		}
		return listaAsientos;
	}

	public AsientoRegistralVO getAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		AsientoRegistralVO asiento1 = new AsientoRegistralVO();
		List<InteresadoVO> listaInteresados = new ArrayList<InteresadoVO>();
		InteresadoVO interesadoPrincipal = new InteresadoVO();

		asiento1.setId(arg0);
		asiento1.setCodigoAsunto("TLIC");
		asiento1.setContactoUsuario("Usuario contacto");
		asiento1.setExpone("Va a realizar la remodelacion parcial de un piso.");
		asiento1.setNombreUsuario("nombreUsuario");
		asiento1.setNumeroExpediente("5543");
		asiento1.setNumeroRegistro("201101010011");
		asiento1.setNumeroRegistroInicial("201200012121");

		asiento1.setFechaRegistro(Calendar.getInstance().getTime());
		asiento1.setNumeroTransporte("122321");
		asiento1.setObservacionesApunte("Documentación sellada y pagada.");
		asiento1.setResumen("Registro de licencia para obra menor");
		asiento1.setTipoTransporte(TipoTransporteEnum.CORREO_POSTAL);

		/*
		StringBuffer solicita = new StringBuffer();
		for (int i=0; i<1; i++){
			solicita.append("123456789");
		}
		asiento1.setSolicita(solicita.toString());
		*/
		asiento1.setSolicita("Permiso para la remodelacion interior de un piso.");
		asiento1.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		asiento1.setCodigoEntidadRegistralOrigen("A10");
		asiento1.setCodigoEntidadRegistralDestino("002");
		asiento1.setDescripcionEntidadRegistralOrigen("Generalitat valenciana");
		asiento1.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
		asiento1.setFechaEstado(Calendar.getInstance().getTime());
		asiento1.setIdentificadorIntercambio("1");
		asiento1.setAplicacion("Registro X.x");
		asiento1.setReferenciaExterna("2949403");
		asiento1.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
		asiento1.setCodigoEntidadRegistralInicio("000002684");
		asiento1.setDescripcionEntidadRegistralInicio("REGISTRO GENERAL DEL AYUNTAMIENTO DE DONOSTIA-SAN SEBASTIAN");


		interesadoPrincipal.setNombreInteresado("NombreInteresado12345678901234");
		interesadoPrincipal.setPrimerApellidoInteresado("PrimerApellidoInteresado123456");
		interesadoPrincipal.setSegundoApellidoInteresado("SegundoApellidoInteresado12345");
		interesadoPrincipal.setCodigoMunicipioInteresado("33498");
		interesadoPrincipal.setCodigoProvinciaInteresado("33");
		interesadoPrincipal.setCorreoElectronicoInteresado("correode160CaracteresParaverSiRompeLaAplicacionEsNecarioQueSeRealiceEstaPruebaEnSICRES30123456789012345678901234567890123456789012345678901234567890@hotmail.com");
		interesadoPrincipal.setDocumentoIdentificacionInteresado("32887953T");
		interesadoPrincipal
				.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion("D"));
		interesadoPrincipal
				.setDireccionInteresado("C/AsturconCon160CaracteresParaComprobarelcorrectofuncionamientodeSicresRespectoalavisualizaciondelinteresado1234567890123456789012345678901234567890123456789012");
		interesadoPrincipal
				.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
		interesadoPrincipal.setTelefonoInteresado("98510101001001201201");
		interesadoPrincipal.setDireccionElectronicaHabilitadaInteresado("Dir. Electrónica Habilitada Interesado");

		//Representante
		interesadoPrincipal.setNombreRepresentante("Antonio");
		interesadoPrincipal.setPrimerApellidoRepresentante("Perez");
		interesadoPrincipal.setSegundoApellidoRepresentante("Suarez");
		interesadoPrincipal.setCodigoMunicipioRepresentante("33498");
		interesadoPrincipal.setCodigoProvinciaRepresentante("33");
		interesadoPrincipal.setCodigoPaisRepresentante("34");
		interesadoPrincipal.setCodigoPostalRepresentante("33960");
		interesadoPrincipal.setCorreoElectronicoRepresentante("antonioperez@hotmail.com");
		interesadoPrincipal.setDocumentoIdentificacionRepresentante("00000000TT");
		interesadoPrincipal
				.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
		interesadoPrincipal.setDireccionRepresentante("C/RepurconCon160CaracteresParaComprobarelcorrectofuncionamientodeSicresRespectoalavisualizaciondelinteresado1234567890123456789012345678901234567890123456789012");
		interesadoPrincipal.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA);
		interesadoPrincipal.setRazonSocialRepresentante("Razón social Representante");
		interesadoPrincipal.setTelefonoRepresentante("985000000");
		interesadoPrincipal.setObservaciones("Observaciones del interesado principal");
		interesadoPrincipal.setDireccionElectronicaHabilitadaRepresentante("Dir. Electrónica Habilitada Representante");

		listaInteresados.add(interesadoPrincipal);
		asiento1.setInteresados(listaInteresados);
		AnexoVO anexo = new AnexoVO();
		anexo.setNombreFichero("prueba.pdf");
		anexo.setId("1");
		anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
		List<AnexoVO> listaAnexos = new ArrayList<AnexoVO>();
		listaAnexos.add(anexo);
		//asiento1.setAnexos(listaAnexos);

		if (logger.isDebugEnabled()) {
			logger.debug("getAsientoRegistral(String) - end");
		}
		return asiento1;
	}

	public byte[] getContenidoAnexo(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		try {
			File anexo = new File(this.getClass()
					.getResource("/intercambioRegistral/mock/anexo.pdf").getFile());
			byte[] anexoBytes = new byte[(int) anexo.length()];
			FileInputStream inputstream = new FileInputStream(anexo);
			inputstream.read(anexoBytes);

			if (logger.isDebugEnabled()) {
				logger.debug("getContenidoAnexo(String) - end");
			}
			return anexoBytes;
		} catch (Exception e) {
			logger.error("getContenidoAnexo(String)", e);

			e.toString();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoAnexo(String) - end");
		}
		return null;
	}

	public EstadoAsientoRegistraVO getEstadoAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoAsientoRegistral(String) - end");
		}
		return null;
	}

	public List<TrazabilidadVO> getHistoricoAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		TrazabilidadVO traza1 = new TrazabilidadVO();
		traza1.setCodigo("1");
		traza1.setCodigoEntidadRegistralDestino("002");
		traza1.setCodigoEntidadRegistralOrigen("001");
		traza1.setCodigoError("0000");
		traza1.setCodigoErrorServicio("00001");
		traza1.setCodigoEstado("000");
		traza1.setCodigoIntercambio(arg0);
		traza1.setCodigoNodo("00");
		traza1.setCodigoUnidadTramitacionDestino("");
		traza1.setDescripcion("Descripción");
		traza1.setDescripcionEntidadRegistralDestino("entidad registral destino");
		traza1.setDescripcionEntidadRegistralOrigen("Entidad Regitral Origen");
		traza1.setDescripcionErrorAlternativa("Error alternativa");
		traza1.setDescripcionErrorServicio("Error del servicio");
		traza1.setDescripcionUnidadTramitacionDestino("Unidad Tramitación Destino");
		traza1.setDescripcionUnidadTramitacionOrigen("Origen");
		traza1.setFechaAlta(new Date());
		traza1.setFechaModificacion(new Date());
		traza1.setMotivoRechazo("Motivo del rechazo");
		traza1.setNombreFicheroIntercambio("fichero.xml");
		traza1.setRegistro(true);


		List<TrazabilidadVO> list = new LinkedList<TrazabilidadVO>();
		list.add(traza1);

		if (logger.isDebugEnabled()) {
			logger.debug("getHistoricoAsientoRegistral(String) - end");
		}
		return list;
	}

	public void procesarFicherosRecibidos() {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void rechazarAsientoRegistral(String identificadorIntercambio, TipoRechazoEnum arg1,
			String motivo) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public AsientoRegistralVO recibirFicheroIntercambio(String arg0, List<byte[]> arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("recibirFicheroIntercambio(String, List<byte[]>) - end");
		}
		return null;
	}

	public void recibirMensaje(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void reenviarAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void removeAnexo(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void removeInteresado(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public AsientoRegistralVO saveAsientoRegistral(AsientoRegistralFormVO arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("saveAsientoRegistral(AsientoRegistralFormVO) - end");
		}
		return null;
	}

	public void setContenidoAnexo(String arg0, byte[] arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public AnexoVO updateAnexo(AnexoVO arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("updateAnexo(AnexoVO) - end");
		}
		return null;
	}

	public void updateAsientoRegistral(InfoBAsientoRegistralVO arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public InteresadoVO updateInteresado(InteresadoVO arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		if (logger.isDebugEnabled()) {
			logger.debug("updateInteresado(InteresadoVO) - end");
		}
		return null;
	}

	public void validarAsientoRegistral(String arg0, String arg1, Date arg2) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		//TODO: Completar este método

		List<TrazabilidadVO> list = new LinkedList<TrazabilidadVO>();
		list.addAll(getHistoricoMensajeIntercambioRegistral(arg0));
		list.addAll(this.getHistoricoAsientoRegistral(arg0));

		if (logger.isDebugEnabled()) {
			logger.debug("getHistoricoCompletoAsientoRegistral(String) - end");
		}
		return list;
	}

	public List<TrazabilidadVO> getHistoricoMensajeIntercambioRegistral(String arg0) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

		TrazabilidadVO traza1 = new TrazabilidadVO();
		traza1.setCodigo("2");
		traza1.setCodigoEntidadRegistralDestino("002");
		traza1.setCodigoEntidadRegistralOrigen("001");
		traza1.setCodigoError("0000");
		traza1.setCodigoErrorServicio("00001");
		traza1.setCodigoEstado("000");
		traza1.setCodigoIntercambio(arg0);
		traza1.setCodigoNodo("00");
		traza1.setCodigoUnidadTramitacionDestino("04");
		traza1.setDescripcion("Descripción de la traza del histórico");
		traza1.setDescripcionEntidadRegistralDestino("entidad registral destino");
		traza1.setDescripcionEntidadRegistralOrigen("Entidad Regitral Origen");
		traza1.setDescripcionErrorAlternativa("Error alternativa");
		traza1.setDescripcionErrorServicio("Error del servicio");
		traza1.setDescripcionUnidadTramitacionDestino("Unidad Tramitación Destino");
		traza1.setDescripcionUnidadTramitacionOrigen("Origen");
		traza1.setFechaAlta(new Date());
		traza1.setFechaModificacion(new Date());
		traza1.setMotivoRechazo("Motivo del rechazo");
		traza1.setNombreFicheroIntercambio("fichero2.xml");
		traza1.setRegistro(false);

		List<TrazabilidadVO> list = new LinkedList<TrazabilidadVO>();
		list.add(traza1);


		return list;
	}

	public void rechazarAsientoRegistral(String arg0, InfoRechazoVO arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

	public void reenviarAsientoRegistral(String arg0, InfoReenvioVO arg1) {
		logger.warn("ESTÁ UTILIZANDO UNA IMPLEMENTACIÓN MOCK DEL SERVICIO: ServicioIntercambioRegistral");

	}

}
