package es.ieci.tecdoc.fwktd.sir.api.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Anexo;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Asunto;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Destino;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Formulario_Generico;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Interesado;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Internos_Control;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Origen_o_Remitente;
import es.ieci.tecdoc.fwktd.sir.api.schema.Fichero_Intercambio_SICRES_3;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Documentacion_FisicaType;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Indicador_PruebaType;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Tipo_RegistroType;
import es.ieci.tecdoc.fwktd.sir.core.exception.ValidacionException;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Información del fichero de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FicheroIntercambioVO extends BaseValueObject {

	private static final long serialVersionUID = -14322899194618123L;

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * Información del fichero de intercambio
	 */
	private Fichero_Intercambio_SICRES_3 ficheroIntercambio = null;

	/**
	 * Constructor.
	 */
	public FicheroIntercambioVO() {
		super();
	}

	public Fichero_Intercambio_SICRES_3 getFicheroIntercambio() {
		return ficheroIntercambio;
	}

	public void setFicheroIntercambio(
			Fichero_Intercambio_SICRES_3 ficheroIntercambio) {
		this.ficheroIntercambio = ficheroIntercambio;
	}

	public String getCodigoEntidadRegistralOrigen() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getCodigo_Entidad_Registral_Origen();
		}

		return null;
	}

	public String getDescripcionEntidadRegistralOrigen() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getDecodificacion_Entidad_Registral_Origen();
		}

		return null;
	}

	public String getCodigoUnidadTramitacionOrigen() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getCodigo_Unidad_Tramitacion_Origen();
		}

		return null;
	}

	public String getDescripcionUnidadTramitacionOrigen() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getDecodificacion_Unidad_Tramitacion_Origen();
		}

		return null;
	}

	public String getCodigoEntidadRegistralDestino() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Destino() != null)){
			return getFicheroIntercambio().getDe_Destino().getCodigo_Entidad_Registral_Destino();
		}

		return null;
	}

	public String getDescripcionEntidadRegistralDestino() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Destino() != null)){
			return getFicheroIntercambio().getDe_Destino().getDecodificacion_Entidad_Registral_Destino();
		}

		return null;
	}

	public String getCodigoUnidadTramitacionDestino() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Destino() != null)){
			return getFicheroIntercambio().getDe_Destino().getCodigo_Unidad_Tramitacion_Destino();
		}

		return null;
	}

	public String getDescripcionUnidadTramitacionDestino() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Destino() != null)){
			return getFicheroIntercambio().getDe_Destino().getDecodificacion_Unidad_Tramitacion_Destino();
		}

		return null;
	}

	public String getNumeroRegistro() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getNumero_Registro_Entrada();
		}

		return null;
	}

	public String getFechaRegistroXML() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getFecha_Hora_Entrada();
		}

		return null;
	}

	public Date getFechaRegistro() {
		String fechaRegistro = getFechaRegistroXML();
		if (StringUtils.isNotBlank(fechaRegistro)) {
			try {
				return SDF.parse(fechaRegistro);
			} catch (ParseException e) {
				logger.error("Error al parsear la fecha de registro: [" + fechaRegistro + "]", e);
			}
		}

		return null;
	}

	public byte[] getTimestampRegistro() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Origen_o_Remitente() != null)){
			return getFicheroIntercambio().getDe_Origen_o_Remitente().getTimestamp_Entrada();
		}

		return null;
	}

	public String getCodigoAsunto() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Asunto() != null)){
			return getFicheroIntercambio().getDe_Asunto().getCodigo_Asunto_Segun_Destino();
		}

		return null;
	}

	public String getNumeroExpediente() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Asunto() != null)){
			return getFicheroIntercambio().getDe_Asunto().getNumero_Expediente();
		}

		return null;
	}

	public String getReferenciaExterna() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Asunto() != null)){
			return getFicheroIntercambio().getDe_Asunto().getReferencia_Externa();
		}

		return null;
	}

	public String getResumen() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Asunto() != null)){
			return getFicheroIntercambio().getDe_Asunto().getResumen();
		}

		return null;
	}

	public String getCodigoEntidadRegistralInicio() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getCodigo_Entidad_Registral_Inicio();
		}

		return null;
	}

	public String getDescripcionEntidadRegistralInicio() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getDecodificacion_Entidad_Registral_Inicio();
		}

		return null;
	}

	public String getNombreUsuario() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getNombre_Usuario();
		}

		return null;
	}

	public String getContactoUsuario() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getContacto_Usuario();
		}

		return null;
	}

	public String getTipoTransporteXML() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getTipo_Transporte_Entrada();
		}

		return null;
	}

	public TipoTransporteEnum getTipoTransporte() {
		String tipoTransporte = getTipoTransporteXML();
		if (StringUtils.isNotBlank(tipoTransporte)) {
			return TipoTransporteEnum.getTipoTransporte(tipoTransporte);
		}

		return null;
	}

	public String getNumeroTransporte() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getNumero_Transporte_Entrada();
		}

		return null;
	}

	public DocumentacionFisicaEnum getDocumentacionFisica() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){

	        Documentacion_FisicaType documentacion_Fisica = getFicheroIntercambio().getDe_Internos_Control().getDocumentacion_Fisica();
	        if ((documentacion_Fisica != null) && StringUtils.isNotBlank(documentacion_Fisica.value())) {
	        	return DocumentacionFisicaEnum.getDocumentacionFisica(documentacion_Fisica.value());
	        }
		}

		return null;
	}

	/**
	 * Obtiene el identificador de intercambio.
	 * @return Identificador de intercambio.
	 */
	public String getIdentificadorIntercambio() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getIdentificador_Intercambio();
		}

		return null;
	}

	/**
	 * Obtiene la información de la aplicación emisora.
	 * @return Aplicación emisora.
	 */
	public String getAplicacionEmisora() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getAplicacion_Version_Emisora();
		}

		return null;
	}

	public String getTipoAnotacionXML() {

		if (getFicheroIntercambio() != null) {
			De_Internos_Control de_Internos_Control = getFicheroIntercambio().getDe_Internos_Control();
			if (de_Internos_Control != null) {
				return de_Internos_Control.getTipo_Anotacion();
			}
		}

		return null;
	}

	/**
	 * Obtiene el tipo de anotación.
	 * @return Tipo de anotación.
	 */
	public TipoAnotacionEnum getTipoAnotacion() {

		String tipoAnotacion = getTipoAnotacionXML();
		if (StringUtils.isNotBlank(tipoAnotacion)) {
			return TipoAnotacionEnum.getTipoAnotacion(tipoAnotacion);
		}

		return null;
	}

	public String getDescripcionTipoAnotacion() {

		if (getFicheroIntercambio() != null) {
			De_Internos_Control de_Internos_Control = getFicheroIntercambio().getDe_Internos_Control();
			if (de_Internos_Control != null) {
				return de_Internos_Control.getDescripcion_Tipo_Anotacion();
			}
		}

		return null;
	}

	public TipoRegistroEnum getTipoRegistro() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){

	        Tipo_RegistroType tipoRegistro = getFicheroIntercambio().getDe_Internos_Control().getTipo_Registro();
	        if ((tipoRegistro != null) && StringUtils.isNotBlank(tipoRegistro.value())) {
	        	return TipoRegistroEnum.getTipoRegistro(tipoRegistro.value());
	        }
		}

		return null;
	}

	public String getObservacionesApunte() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){
			return getFicheroIntercambio().getDe_Internos_Control().getObservaciones_Apunte();
		}

		return null;
	}

	public IndicadorPruebaEnum getIndicadorPrueba() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Internos_Control() != null)){

	        Indicador_PruebaType indicadorPrueba = getFicheroIntercambio().getDe_Internos_Control().getIndicador_Prueba();
	        if ((indicadorPrueba != null) && StringUtils.isNotBlank(indicadorPrueba.value())) {
	        	return IndicadorPruebaEnum.getIndicadorPrueba(indicadorPrueba.value());
	        }
		}

		return null;
	}

	public String getExpone() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Formulario_Generico() != null)){
			return getFicheroIntercambio().getDe_Formulario_Generico().getExpone();
		}

		return null;
	}

	public String getSolicita() {
		if ((getFicheroIntercambio() != null)
				&& (getFicheroIntercambio().getDe_Formulario_Generico() != null)){
			return getFicheroIntercambio().getDe_Formulario_Generico().getSolicita();
		}

		return null;
	}

	/**
	 * Obtiene la información del asientoRegistral.
	 * 
	 * @return Información del asiento registral.
	 */
	public AsientoRegistralVO getAsientoRegistralVO() {

		AsientoRegistralVO asiento = null;

		if (getFicheroIntercambio() != null) {

			asiento = new AsientoRegistralVO();

			De_Origen_o_Remitente de_Origen_o_Remitente = getFicheroIntercambio().getDe_Origen_o_Remitente();
			if (de_Origen_o_Remitente != null) {

				asiento.setCodigoEntidadRegistralOrigen(de_Origen_o_Remitente.getCodigo_Entidad_Registral_Origen());
				asiento.setDescripcionEntidadRegistralOrigen(de_Origen_o_Remitente.getDecodificacion_Entidad_Registral_Origen());

				asiento.setCodigoUnidadTramitacionOrigen(de_Origen_o_Remitente.getCodigo_Unidad_Tramitacion_Origen());
				asiento.setDescripcionUnidadTramitacionOrigen(de_Origen_o_Remitente.getDecodificacion_Unidad_Tramitacion_Origen());

				asiento.setNumeroRegistro(de_Origen_o_Remitente.getNumero_Registro_Entrada());
				asiento.setTimestampRegistro(de_Origen_o_Remitente.getTimestamp_Entrada());

				String fechaRegistro = de_Origen_o_Remitente.getFecha_Hora_Entrada();
				if (StringUtils.isNotBlank(fechaRegistro)) {
					try {
						asiento.setFechaRegistro(SDF.parse(fechaRegistro));
					} catch (ParseException e) {
						logger.error("Error al parsear la fecha de registro: [" + fechaRegistro + "]", e);
						throw new ValidacionException(ErroresEnum.ERROR_0037, e);
					}
				}
			}

		    De_Destino de_Destino = getFicheroIntercambio().getDe_Destino();
		    if (de_Destino != null) {

				asiento.setCodigoEntidadRegistralDestino(de_Destino.getCodigo_Entidad_Registral_Destino());
				asiento.setDescripcionEntidadRegistralDestino(de_Destino.getDecodificacion_Entidad_Registral_Destino());

				asiento.setCodigoUnidadTramitacionDestino(de_Destino.getCodigo_Unidad_Tramitacion_Destino());
				asiento.setDescripcionUnidadTramitacionDestino(de_Destino.getDecodificacion_Unidad_Tramitacion_Destino());
		    }

		    De_Asunto de_Asunto = getFicheroIntercambio().getDe_Asunto();
		    if (de_Asunto != null) {
		    	asiento.setResumen(de_Asunto.getResumen());
		    	asiento.setCodigoAsunto(de_Asunto.getCodigo_Asunto_Segun_Destino());
		    	asiento.setReferenciaExterna(de_Asunto.getReferencia_Externa());
		    	asiento.setNumeroExpediente(de_Asunto.getNumero_Expediente());
		    }

		    De_Internos_Control de_Internos_Control = getFicheroIntercambio().getDe_Internos_Control();
		    if (de_Internos_Control != null) {

		    	asiento.setIdentificadorIntercambio(de_Internos_Control.getIdentificador_Intercambio());
		    	asiento.setAplicacion(de_Internos_Control.getAplicacion_Version_Emisora());
		        asiento.setTipoAnotacion(getTipoAnotacion());
		        asiento.setDescripcionTipoAnotacion(de_Internos_Control.getDescripcion_Tipo_Anotacion());
		        asiento.setNumeroTransporte(de_Internos_Control.getNumero_Transporte_Entrada());
		        asiento.setNombreUsuario(de_Internos_Control.getNombre_Usuario());
		        asiento.setContactoUsuario(de_Internos_Control.getContacto_Usuario());
		        asiento.setObservacionesApunte(de_Internos_Control.getObservaciones_Apunte());
		        asiento.setCodigoEntidadRegistralInicio(de_Internos_Control.getCodigo_Entidad_Registral_Inicio());
		        asiento.setDescripcionEntidadRegistralInicio(de_Internos_Control.getDecodificacion_Entidad_Registral_Inicio());

		        // Tipo de transporte
		    	String tipoTransporte = de_Internos_Control.getTipo_Transporte_Entrada();
		    	if (StringUtils.isNotBlank(tipoTransporte)) {
		    		asiento.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(tipoTransporte));
		    	}

		    	// Tipo de registro
		        Tipo_RegistroType tipo_Registro = de_Internos_Control.getTipo_Registro();
		        if ((tipo_Registro != null) && StringUtils.isNotBlank(tipo_Registro.value())) {
		        	asiento.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(tipo_Registro.value()));
		        }

		        // Documentación física
		        Documentacion_FisicaType documentacion_Fisica = de_Internos_Control.getDocumentacion_Fisica();
		        if ((documentacion_Fisica != null) && StringUtils.isNotBlank(documentacion_Fisica.value())) {
		        	asiento.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(documentacion_Fisica.value()));
		        }

                // Indicador de prueba
                Indicador_PruebaType indicadorPrueba = de_Internos_Control.getIndicador_Prueba();
                if ((indicadorPrueba != null) && StringUtils.isNotBlank(indicadorPrueba.value())){
                	asiento.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(indicadorPrueba.value()));
                }

		    }

		    De_Formulario_Generico de_Formulario_Generico = getFicheroIntercambio().getDe_Formulario_Generico();
		    if (de_Formulario_Generico != null) {
		    	asiento.setExpone(de_Formulario_Generico.getExpone());
		    	asiento.setSolicita(de_Formulario_Generico.getSolicita());
		    }

		    De_Interesado[] de_Interesados = getFicheroIntercambio().getDe_Interesado();
		    if (ArrayUtils.isNotEmpty(de_Interesados)) {
		    	for (De_Interesado de_Interesado : de_Interesados) {
		    		if (de_Interesado != null) {
		    			InteresadoVO interesado = new InteresadoVO();

		    			// Información del interesado
		    			interesado.setDocumentoIdentificacionInteresado(de_Interesado.getDocumento_Identificacion_Interesado());
		    			interesado.setRazonSocialInteresado(de_Interesado.getRazon_Social_Interesado());
		    			interesado.setNombreInteresado(de_Interesado.getNombre_Interesado());
		    			interesado.setPrimerApellidoInteresado(de_Interesado.getPrimer_Apellido_Interesado());
		    			interesado.setSegundoApellidoInteresado(de_Interesado.getSegundo_Apellido_Interesado());
		    			interesado.setCodigoPaisInteresado(de_Interesado.getPais_Interesado());
		    			interesado.setCodigoProvinciaInteresado(de_Interesado.getProvincia_Interesado());
		    			interesado.setCodigoMunicipioInteresado(de_Interesado.getMunicipio_Interesado());
		    			interesado.setDireccionInteresado(de_Interesado.getDireccion_Interesado());
		    			interesado.setCodigoPostalInteresado(de_Interesado.getCodigo_Postal_Interesado());
		    			interesado.setCorreoElectronicoInteresado(de_Interesado.getCorreo_Electronico_Interesado());
		    			interesado.setTelefonoInteresado(de_Interesado.getTelefono_Contacto_Interesado());
		    			interesado.setDireccionElectronicaHabilitadaInteresado(de_Interesado.getDireccion_Electronica_Habilitada_Interesado());

		    			String tipoDocumento = de_Interesado.getTipo_Documento_Identificacion_Interesado();
		    			if (StringUtils.isNotBlank(tipoDocumento)) {
		    				interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(tipoDocumento));
		    			}

		    			String canalPreferente = de_Interesado.getCanal_Preferente_Comunicacion_Interesado();
		    			if (StringUtils.isNotBlank(canalPreferente)) {
		    				interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.getCanalNotificacion(canalPreferente));
		    			}

		    			// Información del representante
		    			interesado.setDocumentoIdentificacionRepresentante(de_Interesado.getDocumento_Identificacion_Representante());
		    			interesado.setRazonSocialRepresentante(de_Interesado.getRazon_Social_Representante());
		    			interesado.setNombreRepresentante(de_Interesado.getNombre_Representante());
		    			interesado.setPrimerApellidoRepresentante(de_Interesado.getPrimer_Apellido_Representante());
		    			interesado.setSegundoApellidoRepresentante(de_Interesado.getSegundo_Apellido_Representante());
		    			interesado.setCodigoPaisRepresentante(de_Interesado.getPais_Representante());
		    			interesado.setCodigoProvinciaRepresentante(de_Interesado.getProvincia_Representante());
		    			interesado.setCodigoMunicipioRepresentante(de_Interesado.getMunicipio_Representante());
		    			interesado.setDireccionRepresentante(de_Interesado.getDireccion_Representante());
		    			interesado.setCodigoPostalRepresentante(de_Interesado.getCodigo_Postal_Representante());
		    			interesado.setCorreoElectronicoRepresentante(de_Interesado.getCorreo_Electronico_Representante());
		    			interesado.setTelefonoRepresentante(de_Interesado.getTelefono_Contacto_Representante());
		    			interesado.setDireccionElectronicaHabilitadaRepresentante(de_Interesado.getDireccion_Electronica_Habilitada_Representante());

		    			tipoDocumento = de_Interesado.getTipo_Documento_Identificacion_Representante();
		    			if (StringUtils.isNotBlank(tipoDocumento)) {
		    				interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(tipoDocumento));
		    			}

		    			canalPreferente = de_Interesado.getCanal_Preferente_Comunicacion_Representante();
		    			if (StringUtils.isNotBlank(canalPreferente)) {
		    				interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.getCanalNotificacion(canalPreferente));
		    			}

		    		    interesado.setObservaciones(de_Interesado.getObservaciones());

		    			asiento.getInteresados().add(interesado);
		    		}
		    	}
		    }

		    De_Anexo[] de_Anexos = getFicheroIntercambio().getDe_Anexo();
		    if (ArrayUtils.isNotEmpty(de_Anexos)) {
		    	for (De_Anexo de_Anexo : de_Anexos) {
		    		if (de_Anexo != null) {
		    			AnexoVO anexo = new AnexoVO();

		    		    anexo.setNombreFichero(de_Anexo.getNombre_Fichero_Anexado());
		    		    anexo.setIdentificadorFichero(de_Anexo.getIdentificador_Fichero());
		    		    anexo.setIdentificadorDocumentoFirmado(de_Anexo.getIdentificador_Documento_Firmado());
		    		    anexo.setCertificado(de_Anexo.getCertificado());
		    		    anexo.setFirma(de_Anexo.getFirma_Documento());
		    		    anexo.setTimestamp(de_Anexo.getTimeStamp());
		    		    anexo.setValidacionOCSPCertificado(de_Anexo.getValidacion_OCSP_Certificado());
		    		    anexo.setHash(de_Anexo.getHash());
		    		    anexo.setTipoMIME(de_Anexo.getTipo_MIME());
		    		    anexo.setObservaciones(de_Anexo.getObservaciones());

		    		    String validezDocumento = de_Anexo.getValidez_Documento();
		    		    if (StringUtils.isNotBlank(validezDocumento)) {
		    		    	anexo.setValidezDocumento(ValidezDocumentoEnum.getValidezDocumento(validezDocumento));
		    		    }

		    		    String tipoDocumento = de_Anexo.getTipo_Documento();
		    		    if (StringUtils.isNotBlank(tipoDocumento)) {
		    		    	anexo.setTipoDocumento(TipoDocumentoEnum.getTipoDocumento(tipoDocumento));
		    		    }

		    			asiento.getAnexos().add(anexo);
		    		}
		    	}
		    }
		}

		return asiento;
	}

//	/**
//	 * Obtiene la información del asientoRegistral.
//	 * @return Información del asiento registral.
//	 */
//	public AsientoRegistralFormVO getAsientoRegistralFormVO() {
//
//		AsientoRegistralFormVO asiento = null;
//
//		if (getFicheroIntercambio() != null) {
//
//			asiento = new AsientoRegistralFormVO();
//
//			De_Origen_o_Remitente de_Origen_o_Remitente = getFicheroIntercambio().getDe_Origen_o_Remitente();
//			if (de_Origen_o_Remitente != null) {
//
//				asiento.setCodigoEntidadRegistralOrigen(de_Origen_o_Remitente.getCodigo_Entidad_Registral_Origen());
//				asiento.setDescripcionEntidadRegistralOrigen(de_Origen_o_Remitente.getDecodificacion_Entidad_Registral_Origen());
//
//				asiento.setCodigoUnidadTramitacionOrigen(de_Origen_o_Remitente.getCodigo_Unidad_Tramitacion_Origen());
//				asiento.setDescripcionUnidadTramitacionOrigen(de_Origen_o_Remitente.getDecodificacion_Unidad_Tramitacion_Origen());
//
//				asiento.setNumeroRegistro(de_Origen_o_Remitente.getNumero_Registro_Entrada());
//				asiento.setTimestampRegistro(de_Origen_o_Remitente.getTimestamp_Entrada());
//
//				String fechaRegistro = de_Origen_o_Remitente.getFecha_Hora_Entrada();
//				if (StringUtils.isNotBlank(fechaRegistro)) {
//					try {
//						asiento.setFechaRegistro(SDF.parse(fechaRegistro));
//					} catch (ParseException e) {
//						logger.error("Error al parsear la fecha de registro: [" + fechaRegistro + "]", e);
//						throw new ValidacionException(ErroresEnum.ERROR_0037, e);
//					}
//				}
//			}
//
//		    De_Destino de_Destino = getFicheroIntercambio().getDe_Destino();
//		    if (de_Destino != null) {
//
//				asiento.setCodigoEntidadRegistralDestino(de_Destino.getCodigo_Entidad_Registral_Destino());
//				asiento.setDescripcionEntidadRegistralDestino(de_Destino.getDecodificacion_Entidad_Registral_Destino());
//
//				asiento.setCodigoUnidadTramitacionDestino(de_Destino.getCodigo_Unidad_Tramitacion_Destino());
//				asiento.setDescripcionUnidadTramitacionDestino(de_Destino.getDecodificacion_Unidad_Tramitacion_Destino());
//		    }
//
//		    De_Asunto de_Asunto = getFicheroIntercambio().getDe_Asunto();
//		    if (de_Asunto != null) {
//		    	asiento.setResumen(de_Asunto.getResumen());
//		    	asiento.setCodigoAsunto(de_Asunto.getCodigo_Asunto_Segun_Destino());
//		    	asiento.setReferenciaExterna(de_Asunto.getReferencia_Externa());
//		    	asiento.setNumeroExpediente(de_Asunto.getNumero_Expediente());
//		    }
//
//		    De_Internos_Control de_Internos_Control = getFicheroIntercambio().getDe_Internos_Control();
//		    if (de_Internos_Control != null) {
//
//		        asiento.setNumeroTransporte(de_Internos_Control.getNumero_Transporte_Entrada());
//		        asiento.setNombreUsuario(de_Internos_Control.getNombre_Usuario());
//		        asiento.setContactoUsuario(de_Internos_Control.getContacto_Usuario());
//		        asiento.setObservacionesApunte(de_Internos_Control.getObservaciones_Apunte());
//		        asiento.setCodigoEntidadRegistralInicio(de_Internos_Control.getCodigo_Entidad_Registral_Inicio());
//		        asiento.setDescripcionEntidadRegistralInicio(de_Internos_Control.getDecodificacion_Entidad_Registral_Inicio());
//
//		        // Tipo de transporte
//		    	String tipoTransporte = de_Internos_Control.getTipo_Transporte_Entrada();
//		    	if (StringUtils.isNotBlank(tipoTransporte)) {
//		    		asiento.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(tipoTransporte));
//		    	}
//
//		    	// Tipo de registro
//		        Tipo_RegistroType tipo_Registro = de_Internos_Control.getTipo_Registro();
//		        if ((tipo_Registro != null) && StringUtils.isNotBlank(tipo_Registro.value())) {
//		        	asiento.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(tipo_Registro.value()));
//		        }
//
//		        // Documentación física
//		        Documentacion_FisicaType documentacion_Fisica = de_Internos_Control.getDocumentacion_Fisica();
//		        if ((documentacion_Fisica != null) && StringUtils.isNotBlank(documentacion_Fisica.value())) {
//		        	asiento.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(documentacion_Fisica.value()));
//		        }
//
//               // Indicador de prueba
//               Indicador_PruebaType indicadorPrueba = de_Internos_Control.getIndicador_Prueba();
//               if ((indicadorPrueba != null) && StringUtils.isNotBlank(indicadorPrueba.value())){
//               	asiento.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(indicadorPrueba.value()));
//               }
//
//		    }
//
//		    De_Formulario_Generico de_Formulario_Generico = getFicheroIntercambio().getDe_Formulario_Generico();
//		    if (de_Formulario_Generico != null) {
//		    	asiento.setExpone(de_Formulario_Generico.getExpone());
//		    	asiento.setSolicita(de_Formulario_Generico.getSolicita());
//		    }
//
//		    De_Interesado[] de_Interesados = getFicheroIntercambio().getDe_Interesado();
//		    if (ArrayUtils.isNotEmpty(de_Interesados)) {
//		    	for (De_Interesado de_Interesado : de_Interesados) {
//		    		if (de_Interesado != null) {
//		    			InteresadoFormVO interesado = new InteresadoFormVO();
//
//		    			// Información del interesado
//		    			interesado.setDocumentoIdentificacionInteresado(de_Interesado.getDocumento_Identificacion_Interesado());
//		    			interesado.setRazonSocialInteresado(de_Interesado.getRazon_Social_Interesado());
//		    			interesado.setNombreInteresado(de_Interesado.getNombre_Interesado());
//		    			interesado.setPrimerApellidoInteresado(de_Interesado.getPrimer_Apellido_Interesado());
//		    			interesado.setSegundoApellidoInteresado(de_Interesado.getSegundo_Apellido_Interesado());
//		    			interesado.setCodigoPaisInteresado(de_Interesado.getPais_Interesado());
//		    			interesado.setCodigoProvinciaInteresado(de_Interesado.getProvincia_Interesado());
//		    			interesado.setCodigoMunicipioInteresado(de_Interesado.getMunicipio_Interesado());
//		    			interesado.setDireccionInteresado(de_Interesado.getDireccion_Interesado());
//		    			interesado.setCodigoPostalInteresado(de_Interesado.getCodigo_Postal_Interesado());
//		    			interesado.setCorreoElectronicoInteresado(de_Interesado.getCorreo_Electronico_Interesado());
//		    			interesado.setTelefonoInteresado(de_Interesado.getTelefono_Contacto_Interesado());
//		    			interesado.setDireccionElectronicaHabilitadaInteresado(de_Interesado.getDireccion_Electronica_Habilitada_Interesado());
//
//		    			String tipoDocumento = de_Interesado.getTipo_Documento_Identificacion_Interesado();
//		    			if (StringUtils.isNotBlank(tipoDocumento)) {
//		    				interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(tipoDocumento));
//		    			}
//
//		    			String canalPreferente = de_Interesado.getCanal_Preferente_Comunicacion_Interesado();
//		    			if (StringUtils.isNotBlank(canalPreferente)) {
//		    				interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.getCanalNotificacion(canalPreferente));
//		    			}
//
//		    			// Información del representante
//		    			interesado.setDocumentoIdentificacionRepresentante(de_Interesado.getDocumento_Identificacion_Representante());
//		    			interesado.setRazonSocialRepresentante(de_Interesado.getRazon_Social_Representante());
//		    			interesado.setNombreRepresentante(de_Interesado.getNombre_Representante());
//		    			interesado.setPrimerApellidoRepresentante(de_Interesado.getPrimer_Apellido_Representante());
//		    			interesado.setSegundoApellidoRepresentante(de_Interesado.getSegundo_Apellido_Representante());
//		    			interesado.setCodigoPaisRepresentante(de_Interesado.getPais_Representante());
//		    			interesado.setCodigoProvinciaRepresentante(de_Interesado.getProvincia_Representante());
//		    			interesado.setCodigoMunicipioRepresentante(de_Interesado.getMunicipio_Representante());
//		    			interesado.setDireccionRepresentante(de_Interesado.getDireccion_Representante());
//		    			interesado.setCodigoPostalRepresentante(de_Interesado.getCodigo_Postal_Representante());
//		    			interesado.setCorreoElectronicoRepresentante(de_Interesado.getCorreo_Electronico_Representante());
//		    			interesado.setTelefonoRepresentante(de_Interesado.getTelefono_Contacto_Representante());
//		    			interesado.setDireccionElectronicaHabilitadaRepresentante(de_Interesado.getDireccion_Electronica_Habilitada_Representante());
//
//		    			tipoDocumento = de_Interesado.getTipo_Documento_Identificacion_Representante();
//		    			if (StringUtils.isNotBlank(tipoDocumento)) {
//		    				interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(tipoDocumento));
//		    			}
//
//		    			canalPreferente = de_Interesado.getCanal_Preferente_Comunicacion_Representante();
//		    			if (StringUtils.isNotBlank(canalPreferente)) {
//		    				interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.getCanalNotificacion(canalPreferente));
//		    			}
//
//		    		    interesado.setObservaciones(de_Interesado.getObservaciones());
//
//		    			asiento.getInteresados().add(interesado);
//		    		}
//		    	}
//		    }
//
//		    De_Anexo[] de_Anexos = getFicheroIntercambio().getDe_Anexo();
//		    if (ArrayUtils.isNotEmpty(de_Anexos)) {
//		    	for (De_Anexo de_Anexo : de_Anexos) {
//		    		if (de_Anexo != null) {
//		    			AnexoFormVO anexo = new AnexoFormVO();
//
//		    		    anexo.setNombreFichero(de_Anexo.getNombre_Fichero_Anexado());
//		    		    anexo.setCertificado(de_Anexo.getCertificado());
//		    		    anexo.setFirma(de_Anexo.getFirma_Documento());
//		    		    anexo.setTimestamp(de_Anexo.getTimeStamp());
//		    		    anexo.setValidacionOCSPCertificado(de_Anexo.getValidacion_OCSP_Certificado());
//		    		    anexo.setHash(de_Anexo.getHash());
//		    		    anexo.setTipoMIME(de_Anexo.getTipo_MIME());
//		    		    anexo.setContenido(de_Anexo.getAnexo());
//		    		    anexo.setObservaciones(de_Anexo.getObservaciones());
//
//		    		    String validezDocumento = de_Anexo.getValidez_Documento();
//		    		    if (StringUtils.isNotBlank(validezDocumento)) {
//		    		    	anexo.setValidezDocumento(ValidezDocumentoEnum.getValidezDocumento(validezDocumento));
//		    		    }
//
//		    		    String tipoDocumento = de_Anexo.getTipo_Documento();
//		    		    if (StringUtils.isNotBlank(tipoDocumento)) {
//		    		    	anexo.setTipoDocumento(TipoDocumentoEnum.getTipoDocumento(tipoDocumento));
//		    		    }
//
//		    			anexo.setCodigoFichero(de_Anexo.getIdentificador_Fichero());
//		    			anexo.setCodigoFicheroFirmado(de_Anexo.getIdentificador_Documento_Firmado());
//
//		    			asiento.getAnexos().add(anexo);
//		    		}
//		    	}
//		    }
//		}
//
//		return asiento;
//	}

	/**
	 * Obtiene el número de anexos.
	 * @return Número de anexos
	 */
	public int countAnexos() {

		if (getFicheroIntercambio() != null) {
			return getFicheroIntercambio().getDe_AnexoCount();
		}

		return 0;
	}

	/**
	 * Establece el contenido del anexo.
	 * @param secuencia Ordinal del anexo
	 * @param contenido Contenido del anexo.
	 */
	public void setContenidoAnexo(int secuencia, byte[] contenido) {
		if (secuencia < getFicheroIntercambio().getDe_AnexoCount()) {
			getFicheroIntercambio().getDe_Anexo()[secuencia].setAnexo(contenido);
		}
	}
	
	/**
	 * Obtiene el contenido de un anexo.
	 * @param secuencia Ordinal del anexo.
	 * @return Contenido del anexo.
	 */
	public byte[] getContenidoAnexo(int secuencia) {
		if (secuencia < getFicheroIntercambio().getDe_AnexoCount()) {
			return getFicheroIntercambio().getDe_Anexo()[secuencia].getAnexo();
		} else {
			return null;
		}
	}

}
