package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.AsientoRegistralDao;
import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.FicheroIntercambioManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.MensajeManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Implementación del manager de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AsientoRegistralManagerImpl extends
		BaseManagerImpl<AsientoRegistralVO, String> implements
		AsientoRegistralManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(AsientoRegistralManagerImpl.class);

	private static final String TIME_OUT_PARAM_NAME = "envios.time-out";
	private static final long DEFAULT_TIME_OUT = 1000 * 60 * 60 * 24;

	private static final String NUMERO_REINTENTOS_PARAM_NAME = "envios.numeroReintentos";
	private static final int DEFAULT_NUMERO_REINTENTOS = 5;


    /**
     * Gestor de ficheros de intercambio.
     */
    private FicheroIntercambioManager ficheroIntercambioManager = null;

    /**
     * Gestor de ficheros de datos de control.
     */
    private MensajeManager mensajeManager = null;

    /**
     * Gestor de interesados de asientos registrales.
     */
    private InteresadoManager interesadoManager = null;

    /**
     * Gestor de anexos de asientos registrales.
     */
    private AnexoManager anexoManager = null;

	/**
	 * Gestor de fechas.
	 */
	private FechaManager fechaManager = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;

	/**
	 * Time-out por defecto para la recepción de mensajes (ACK o ERROR) en
	 * milisegundos
	 */
	private long defaultTimeOut = DEFAULT_TIME_OUT;

	/**
	 * Número por defecto de reintentos de envío de ficheros de intercambio si
	 * no se recibe respuesta.
	 */
	private int defaultNumeroReintentos = DEFAULT_NUMERO_REINTENTOS;


	/**
	 * Constructor.
	 * @param aGenericDao Dao.
	 */
	public AsientoRegistralManagerImpl(
			BaseDao<AsientoRegistralVO, String> aGenericDao) {
		super(aGenericDao);
	}

	public FicheroIntercambioManager getFicheroIntercambioManager() {
		return ficheroIntercambioManager;
	}

	public void setFicheroIntercambioManager(
			FicheroIntercambioManager ficheroIntercambioManager) {
		this.ficheroIntercambioManager = ficheroIntercambioManager;
	}

	public MensajeManager getMensajeManager() {
		return mensajeManager;
	}

	public void setMensajeManager(MensajeManager mensajeManager) {
		this.mensajeManager = mensajeManager;
	}

	public InteresadoManager getInteresadoManager() {
		return interesadoManager;
	}

	public void setInteresadoManager(InteresadoManager interesadoManager) {
		this.interesadoManager = interesadoManager;
	}

	public AnexoManager getAnexoManager() {
		return anexoManager;
	}

	public void setAnexoManager(AnexoManager anexoManager) {
		this.anexoManager = anexoManager;
	}

	public FechaManager getFechaManager() {
		return fechaManager;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	public void setFechaManager(FechaManager fechaManager) {
		this.fechaManager = fechaManager;
	}

	public long getDefaultTimeOut() {
		return defaultTimeOut;
	}

	public void setDefaultTimeOut(long defaultTimeOut) {
		this.defaultTimeOut = defaultTimeOut;
	}

	public int getDefaultNumeroReintentos() {
		return defaultNumeroReintentos;
	}

	public void setDefaultNumeroReintentos(int defaultNumeroReintentos) {
		this.defaultNumeroReintentos = defaultNumeroReintentos;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#countAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public int countAsientosRegistrales(CriteriosVO criterios) {

		logger.info("Obteniendo el número de asientos registrales. Criterios: {}", criterios);

		// Obtiene el número de asientos registrales en base a los criterios
		return ((AsientoRegistralDao)getDao()).countAsientosRegistrales(criterios);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#findAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
	 */
	public List<AsientoRegistralVO> findAsientosRegistrales(CriteriosVO criterios) {

		logger.info("Realizando búsqueda de asientos registrales. Criterios: {}", criterios);

		// Realiza la búsqueda de asientos registrales en base a los criterios
		return ((AsientoRegistralDao)getDao()).findAsientosRegistrales(criterios);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
	 */
	public AsientoRegistralVO saveAsientoRegistral(AsientoRegistralFormVO asientoForm) {

		AsientoRegistralVO asiento = new AsientoRegistralVO();
		BeanUtils.copyProperties(asientoForm, asiento, new String[] { "anexos", "interesados" });

//    	asiento.setCodigoEntidadRegistralOrigen(asientoForm.getCodigoEntidadRegistralOrigen());
//    	asiento.setDescripcionEntidadRegistralOrigen(asientoForm.getDescripcionEntidadRegistralOrigen());
//    	asiento.setCodigoUnidadTramitacionOrigen(asientoForm.getCodigoUnidadTramitacionOrigen());
//    	asiento.setDescripcionUnidadTramitacionOrigen(asientoForm.getDescripcionUnidadTramitacionOrigen());
//
//    	asiento.setCodigoEntidadRegistralDestino(asientoForm.getCodigoEntidadRegistralDestino());
//    	asiento.setDescripcionEntidadRegistralDestino(asientoForm.getDescripcionEntidadRegistralDestino());
//    	asiento.setCodigoUnidadTramitacionDestino(asientoForm.getCodigoUnidadTramitacionDestino());
//    	asiento.setDescripcionUnidadTramitacionDestino(asientoForm.getDescripcionUnidadTramitacionDestino());

    	asiento.setCodigoEntidadRegistralInicio(asiento.getCodigoEntidadRegistralInicio());
    	asiento.setDescripcionEntidadRegistralInicio(asiento.getDescripcionEntidadRegistralInicio());

//    	asiento.setTipoRegistro(asientoForm.getTipoRegistro());
//    	asiento.setNumeroRegistro(asientoForm.getNumeroRegistro());
//    	asiento.setFechaRegistro(asientoForm.getFechaRegistro());
//    	asiento.setTimestampRegistro(asientoForm.getTimestampRegistro());

        asiento.setNumeroRegistroInicial(asiento.getNumeroRegistro());
        asiento.setFechaRegistroInicial(asiento.getFechaRegistro());
        asiento.setTimestampRegistroInicial(asiento.getTimestampRegistro());

//    	asiento.setResumen(asientoForm.getResumen());
//    	asiento.setCodigoAsunto(asientoForm.getCodigoAsunto());
//    	asiento.setReferenciaExterna(asientoForm.getReferenciaExterna());
//    	asiento.setNumeroExpediente(asientoForm.getNumeroExpediente());
//    	asiento.setTipoTransporte(asientoForm.getTipoTransporte());
//    	asiento.setNumeroTransporte(asientoForm.getNumeroTransporte());
//    	asiento.setNombreUsuario(asientoForm.getNombreUsuario());
//    	asiento.setContactoUsuario(asientoForm.getContactoUsuario());

    	//asiento.setIdentificadorIntercambio(asientoForm.getidentificadorIntercambio);
    	//asiento.setAplicacion(asientoForm.getaplicacion);
        asiento.setTipoAnotacion(TipoAnotacionEnum.PENDIENTE);
        //asiento.setDescripcionTipoAnotacion(null);

//    	asiento.setDocumentacionFisica(asientoForm.getDocumentacionFisica());
//    	asiento.setObservacionesApunte(asientoForm.getObservacionesApunte());
//    	asiento.setIndicadorPrueba(asientoForm.getIndicadorPrueba());
//
//    	asiento.setExpone(asientoForm.getExpone());
//    	asiento.setSolicita(asientoForm.getSolicita());

        asiento.setEstado(EstadoAsientoRegistralEnum.PENDIENTE_ENVIO);
        asiento.setFechaEstado(getFechaManager().getFechaActual());

        if (logger.isDebugEnabled()){
        	logger.debug("Salvando asiento registral [{}]", ToStringLoggerHelper.toStringLogger(asiento));
        }


		// Guardar la información del asiento registral
		asiento = save(asiento);

		// Insertar la información de los interesados
		if (CollectionUtils.isNotEmpty(asientoForm.getInteresados())) {
			for (InteresadoFormVO interesadoForm : asientoForm.getInteresados()) {
				if (logger.isDebugEnabled()){
					logger.debug("Salvando interesado [{}]", ToStringLoggerHelper.toStringLogger(interesadoForm));
				}
				getInteresadoManager().saveInteresado(asiento.getId(), interesadoForm);
			}
		}

		// Insertar la información de los anexos
		if (CollectionUtils.isNotEmpty(asientoForm.getAnexos())) {

			List<AnexoVO> listaAnexoVO = new ArrayList<AnexoVO>();

			// Guardar la información de los anexos
			for (AnexoFormVO anexoForm : asientoForm.getAnexos()) {
				if (logger.isDebugEnabled()){
					logger.debug("Salvando anexo [{}]",ToStringLoggerHelper.toStringLogger(anexoForm));
				}
				AnexoVO anexo = getAnexoManager().saveAnexo(asiento.getId(), anexoForm);
				listaAnexoVO.add(anexo);
			}

			// Establecer el identificador del fichero firmado en los anexos
			for (int i = 0; i < asientoForm.getAnexos().size(); i++) {
				AnexoFormVO anexoForm = asientoForm.getAnexos().get(i);
				if (StringUtils.isBlank(anexoForm.getIdentificadorFicheroFirmado())
						&& StringUtils.isNotBlank(anexoForm.getCodigoFicheroFirmado())) {

					// Información del anexo guardado
					AnexoVO anexo = listaAnexoVO.get(i);

					// Obtener el identificador de fichero firmado
					String identificadorFicheroFirmado = null;
					if (StringUtils.equals(anexoForm.getCodigoFichero(), anexoForm.getCodigoFicheroFirmado())) {
						identificadorFicheroFirmado = anexo.getId();
					} else {
						// Obtener el índice del anexo con el código de fichero
						int orden = getOrdenCodigoFichero(anexoForm.getCodigoFicheroFirmado(), asientoForm.getAnexos());
						if (orden >= 0) {
							identificadorFicheroFirmado = listaAnexoVO.get(orden).getId();
						}
					}

					// Establecer el identificador de fichero firmado
					if (StringUtils.isNotBlank(identificadorFicheroFirmado)) {
						anexo.setIdentificadorFicheroFirmado(identificadorFicheroFirmado);
						getAnexoManager().update(anexo);
					}
				}
			}
		}

		return get(asiento.getId());
	}

	private int getOrdenCodigoFichero(String codigoFichero, List<AnexoFormVO> listaAnexoFormVO) {

		for (int orden = 0; orden < listaAnexoFormVO.size(); orden++) {
			AnexoFormVO anexoForm = listaAnexoFormVO.get(orden);
			if (StringUtils.equals(codigoFichero, anexoForm.getCodigoFichero())) {
				return orden;
			}
		}

		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#regenerateAsientoRegistral(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO )
	 */
	public AsientoRegistralVO regenerateAsientoRegistral(FicheroIntercambioVO ficheroIntercambio, String id) {
		AsientoRegistralVO result=null;
		//borramos el anterior pero guardamos con el nuevo con el identificador
		deleteAsientoRegistral(id);
		result = saveAsientoRegistral(ficheroIntercambio,id);
		
		return result;
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO )
	 */
	public AsientoRegistralVO saveAsientoRegistral(FicheroIntercambioVO ficheroIntercambio) {
		return saveAsientoRegistral(ficheroIntercambio,null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO, java.lang.String)
	 */
	public AsientoRegistralVO saveAsientoRegistral(FicheroIntercambioVO ficheroIntercambio, String idAsientoRegistralVO) {

		if (logger.isInfoEnabled()) {
			logger.info("Guardando la información del asiento registral del fichero de intercambio recibido [{}]",
					ficheroIntercambio.getIdentificadorIntercambio());
		}

		// Guardar la información del asiento registral
		AsientoRegistralVO asiento = ficheroIntercambio.getAsientoRegistralVO();
		asiento.setCodigoEntidadRegistral(ficheroIntercambio.getCodigoEntidadRegistralDestino());
        asiento.setNumeroRegistroInicial(asiento.getNumeroRegistro());
        asiento.setFechaRegistroInicial(asiento.getFechaRegistro());
        asiento.setTimestampRegistroInicial(asiento.getTimestampRegistro());
        asiento.setEstado(EstadoAsientoRegistralEnum.RECIBIDO);
        asiento.setFechaEstado(getFechaManager().getFechaActual());
        asiento.setFechaRecepcion(asiento.getFechaEstado());
        asiento.setId(idAsientoRegistralVO);

        if (logger.isDebugEnabled()){
        	logger.debug("Salvando asiento registral [{}]", ToStringLoggerHelper.toStringLogger(asiento));
        }

		// Guardar la información del asiento registral
		asiento = save(asiento);

		if (logger.isInfoEnabled()) {
			logger.info("Guardando la información de los interesados en el fichero de intercambio recibido [{}]",
					ficheroIntercambio.getIdentificadorIntercambio());
		}

		// Guardar la información de los interesados
        if (!CollectionUtils.isEmpty(asiento.getInteresados())) {
        	for (InteresadoVO interesado : asiento.getInteresados()) {

				if (logger.isDebugEnabled()){
					logger.debug("Salvando interesado [{}]",
							ToStringLoggerHelper.toStringLogger(interesado));
				}

            	// Establecer el identificador del asiento registral
        		interesado.setIdAsientoRegistral(asiento.getId());

        		// Guardar la información del interesado
        		getInteresadoManager().save(interesado);
        	}
        }

		// Guardar la información de los anexos
		if (CollectionUtils.isNotEmpty(asiento.getAnexos())) {

			List<AnexoVO> anexosAActualizar = new ArrayList<AnexoVO>();
			Map<String, String> idsMapping = new HashMap<String, String>();

			int cont = 0;
			for (AnexoVO anexo : asiento.getAnexos()) {

				if (logger.isDebugEnabled()){
					logger.debug("Salvando anexo #{}: [{}]", cont,
							ToStringLoggerHelper.toStringLogger(anexo));
				}

            	// Establecer el identificador del asiento registral
        		anexo.setIdAsientoRegistral(asiento.getId());

        		// Guardar la información del anexo
        		anexo = getAnexoManager().save(anexo);

        		// Almacenar temporalmente el mapeo identificadorFichero-id
        		if (StringUtils.isNotBlank(anexo.getIdentificadorFichero())) {
        			idsMapping.put(anexo.getIdentificadorFichero(), anexo.getId());
        		}

				// Si el anexo es una firma, añadirlo a la lista para actualizar
				// después el identificador del fichero firmado
        		if (StringUtils.isNotBlank(anexo.getIdentificadorDocumentoFirmado())) {
        			anexosAActualizar.add(anexo);
        		}

        		// Guardar el contenido del anexo
				getAnexoManager().setContenidoAnexo(anexo,
						ficheroIntercambio.getContenidoAnexo(cont));

        		cont++;
			}

			// Actualizar el id de fichero firmado
			if (!CollectionUtils.isEmpty(anexosAActualizar)) {
				for (AnexoVO anexo : anexosAActualizar) {
					String anexoId = idsMapping.get(anexo.getIdentificadorDocumentoFirmado());
					if (StringUtils.isNotBlank(anexoId)) {
						anexo.setIdentificadorFicheroFirmado(anexoId);
						getAnexoManager().update(anexo);
					}
				}
			}
		}

		return get(asiento.getId());
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#deleteAsientoRegistral(java.lang.String)
	 */
	public void deleteAsientoRegistral(String id) {

		logger.info("Eliminando asiento registral con identificador [{}]", id);

		logger.info("Eliminando anexos del asiento registral con identificador [{}]", id);
		getAnexoManager().deleteByIdAsientoRegistral(id);

		logger.info("Eliminando interesados del asiento registral con identificador [{}]", id);
		getInteresadoManager().deleteByIdAsientoRegistral(id);

		logger.info("Eliminando información del asiento registral con identificador [{}]", id);
		super.delete(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#getCodigoIntercambio(java.lang.String)
	 */
	public String getCodigoIntercambio(String id) {

		Assert.hasText(id, "'id' must not be empty");

		logger.info("Obteniendo el código de intercambio del asiento registral [{}]", id);

		return ((AsientoRegistralDao)getDao()).getCodigoIntercambio(id);
	}


	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#getDescripcionTipoAnotacion(java.lang.String)
	 */
	public String getDescripcionTipoAnotacion(String id){

		Assert.hasText(id, "'id' must not be empty");

		logger.info("Obteniendo  la  descripcion del tipo de anotacion de intercambio del asiento registral [{}]", id);

		return ((AsientoRegistralDao)getDao()).getDescripcionTipoAnotacion(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#getEstado(java.lang.String)
	 */
	public EstadoAsientoRegistraVO getEstado(String id) {

		logger.info("Obteniendo el estado del asiento registral [{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return ((AsientoRegistralDao)getDao()).getEstado(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#getAsientoRegistral(java.lang.String,java.lang.String)
	 */
	public AsientoRegistralVO getAsientoRegistral(
			String codigoEntidadRegistral, String identificadorIntercambio) {

		logger.info(
				"Obteniendo el asiento registral a partir del código de entidad registral [{}] e identificador de intercambio [{}]",
				codigoEntidadRegistral, identificadorIntercambio);

		Assert.hasText(codigoEntidadRegistral, "'codigoEntidadRegistral' must not be empty");
		Assert.hasText(identificadorIntercambio, "'identificadorIntercambio' must not be empty");

		return ((AsientoRegistralDao) getDao()).getAsientoRegistral(
				codigoEntidadRegistral, identificadorIntercambio);
	}

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#enviarAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
     */
    public AsientoRegistralVO enviarAsientoRegistral(AsientoRegistralFormVO asientoForm) {

    	if (logger.isInfoEnabled()){
    		logger.info("Enviando el asiento registral: [{}]", ToStringLoggerHelper.toStringLogger(asientoForm));
    	}

        Assert.notNull(asientoForm, "'asientoForm' must not be null");

        // Salvar el asiento registral
        AsientoRegistralVO asiento = saveAsientoRegistral(asientoForm);

        // Enviar el asiento registral
        enviarAsientoRegistral(asiento.getId());

        return get(asiento.getId());
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#enviarAsientoRegistral(java.lang.String)
     */
    public void enviarAsientoRegistral(String id) {

    	logger.info("Enviando el asiento registral con identificador: [{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {
        	enviarAsientoRegistral(asiento);
        } else {
            logger.error("No se ha encontrado el asiento registral a enviar con identificador [{}]", id);
            throw new SIRException("error.sir.enviarAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a enviar");
        }
    }

    protected void enviarAsientoRegistral(AsientoRegistralVO asiento) {

    	logger.debug("Enviando el asiento registral: [{}]", asiento);

    	Assert.notNull(asiento, "'asiento' must not be null");

        // Comprobar que el estado del asiento registral sea PENDIENTE_ENVIO
        if (!EstadoAsientoRegistralEnum.PENDIENTE_ENVIO.equals(asiento.getEstado())
        		&& !EstadoAsientoRegistralEnum.ENVIADO.equals(asiento.getEstado())
        		&& !EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR.equals(asiento.getEstado())) {
            logger.error("El asiento registral a enviar con identificador [{}] está en estado [{}]",
                    asiento.getId(), asiento.getEstado());
            throw new SIRException("error.sir.enviarAsientoRegistral.estadoNoValido", null,
                    "El asiento registral a enviar no está en estado PENDIENTE DE ENVIO, ENVIADO o ENVIADO con ERROR");
        }

        // Envío del fichero de datos de intercambio
        getFicheroIntercambioManager().enviarFicheroIntercambio(asiento);

        // Actualizar la información del asiento
        Date fechaActual = getFechaManager().getFechaActual();
        asiento.setEstado(EstadoAsientoRegistralEnum.ENVIADO);
        asiento.setFechaEstado(fechaActual);
        asiento.setFechaEnvio(fechaActual);
        asiento.setCodigoError(null);
        asiento.setDescripcionError(null);

        update(asiento);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#validarAsientoRegistral(java.lang.String, java.lang.String, java.util.Date)
     */
    public void validarAsientoRegistral(String id, String numeroRegistro, Date fechaRegistro) {

        logger.info("Validando el asiento registral: id=[{}], numeroRegistro=[{}], fechaRegistro=[{}]",
                new Object[] { id, numeroRegistro, fechaRegistro });

        /*
         * Desde detalle de pre-asiento en la bandeja de entrada:
         * 1	El usuario selecciona la opción de validar pre-asiento
         * 2	El sistema genera un nuevo número de asiento registral
         * 3	El sistema modifica:
         * 		•	El estado del pre-asiento a Completo
         * 		•	Asigna el número de asiento registral creado a este registro
         * 		•	Modifica la oficina de origen por la oficina propia que ha aceptado el pre-asiento
         * 		•	Modifica la fecha de alta y de entrada del asiento
         * 4	El sistema muestra la página de confirmación que se ha realizado el alta de asiento registral.
         */

        Assert.hasText(id, "'id' must not be empty");
        Assert.hasText(numeroRegistro, "'numeroRegistro' must not be empty");
        Assert.notNull(fechaRegistro, "'fechaRegistro' must not be null");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {

            // Comprobar que el estado del asiento registral sea RECIBIDO o DEVUELTO o REENVIADO
            if (!(EstadoAsientoRegistralEnum.RECIBIDO.equals(asiento.getEstado())|| EstadoAsientoRegistralEnum.DEVUELTO.equals(asiento.getEstado())|| EstadoAsientoRegistralEnum.REENVIADO.equals(asiento.getEstado()))) {
                logger.error("El asiento registral a validar con identificador [{}] está en estado [{}]",
                        id, asiento.getEstado());
                throw new SIRException("error.sir.validarAsientoRegistral.estadoNoValido", null,
                        "El asiento registral a validar no está en estado RECIBIDO");
            }

            Date fechaActual = getFechaManager().getFechaActual();

            // Actualizar la información del asiento registral
            asiento.setNumeroRegistro(numeroRegistro);
            asiento.setFechaRegistro(fechaRegistro);
            asiento.setEstado(EstadoAsientoRegistralEnum.VALIDADO);
            asiento.setFechaEstado(fechaActual);
            update(asiento);

            logger.info("Asiento validado, enviando mensaje de control");


			try {
				// Enviar mensaje de confirmación
				enviarMensajeConfirmacion(asiento);
				logger.info("Mensaje de confirmación enviado");
			} catch (SIRException e) {
				/*
				 * Si falla el mensaje al CIR entonces se pone el estado a
				 * REINTENTAR VALIDACION para que más adelante lo actualice un
				 * job.
				 */
				logger.error("Ha fallado el envio del mensaje de confirmacion para validar el asiento registral [{}]", id);

				asiento.setEstado(EstadoAsientoRegistralEnum.REINTENTAR_VALIDACION);
				logger.info("Debido al fallo anterior, al asiento registral con identificador [{}] se le establecera el estado [{}]",
                        id, asiento.getEstado());
				update(asiento);
			}


        } else {
            logger.error("No se ha encontrado el asiento registral a validar con identificador [{}]", id);
            throw new SIRException("error.sir.validarAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a validar");
        }
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#reenviarAsientoRegistral(java.lang.String)
     */
    public void reenviarAsientoRegistral(String id) {

        logger.info("Reenviando el asiento registral con identificador [{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {
        	reenviarAsientoRegistral(asiento);
        } else {
            logger.error("No se ha encontrado el asiento registral a reenviar con identificador [{}]", id);
            throw new SIRException("error.sir.reenviarAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a reenviar");
        }
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#reenviarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO)
     */
    public void reenviarAsientoRegistral(String id, InfoReenvioVO infoReenvio) {

		logger.info(
				"Reenviando el asiento registral con identificador [{}] e información de reenvío: {}",
				id, infoReenvio);

		Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(infoReenvio, "'infoReenvio' must not be null");
        Assert.hasText(infoReenvio.getCodigoEntidadRegistralDestino(), "'infoReenvio.codigoEntidadRegistralDestino' must not be empty");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {

        	// Actualizar el asiento registral con la información de reenvío
        	if (!StringUtils.equals(asiento.getCodigoEntidadRegistral(), asiento.getCodigoEntidadRegistralOrigen())) {
	            asiento.setCodigoEntidadRegistralOrigen(asiento.getCodigoEntidadRegistralDestino());
	            asiento.setDescripcionEntidadRegistralOrigen(asiento.getDescripcionEntidadRegistralDestino());
        	}
	        asiento.setCodigoEntidadRegistralDestino(infoReenvio.getCodigoEntidadRegistralDestino());
	        asiento.setDescripcionEntidadRegistralDestino(infoReenvio.getDescripcionEntidadRegistralDestino());

	        asiento.setCodigoUnidadTramitacionDestino(infoReenvio.getCodigoUnidadTramitacionDestino());
	        asiento.setDescripcionUnidadTramitacionDestino(infoReenvio.getDescripcionUnidadTramitacionDestino());

            asiento.setNombreUsuario(infoReenvio.getUsuario());
            asiento.setContactoUsuario(infoReenvio.getContacto());
            asiento.setAplicacion(infoReenvio.getAplicacion());
            asiento.setDescripcionTipoAnotacion(infoReenvio.getDescripcion());

        	reenviarAsientoRegistral(asiento);

        } else {
            logger.error("No se ha encontrado el asiento registral a reenviar con identificador [{}]", id);
            throw new SIRException("error.sir.reenviarAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a reenviar");
        }
    }

    protected void reenviarAsientoRegistral(AsientoRegistralVO asiento) {

    	if (logger.isDebugEnabled()){
    		logger.debug("Reenviando el asiento registral: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
    	}

        Assert.notNull(asiento, "'asiento' must not be null");

	    // Comprobar que el estado del asiento registral sea RECIBIDO o DEVUELTO
	    if (!EstadoAsientoRegistralEnum.RECIBIDO.equals(asiento.getEstado())
	            && !EstadoAsientoRegistralEnum.DEVUELTO.equals(asiento.getEstado())
	            && !EstadoAsientoRegistralEnum.REENVIADO.equals(asiento.getEstado())
	            && !EstadoAsientoRegistralEnum.REENVIADO_Y_ERROR.equals(asiento.getEstado())) {
	        logger.error("El asiento registral a reenviar con identificador [{}] está en estado [{}]",
	        		asiento.getId(), asiento.getEstado());
	        throw new SIRException("error.sir.reenviarAsientoRegistral.estadoNoValido", null,
	                "El asiento registral a reenviar no está en estado RECIBIDO, DEVUELTO, REENVIADO o REENVIADO con ERROR");
	    }

	    Assert.hasText(asiento.getCodigoEntidadRegistralDestino(), "'codigoEntidadRegistralDestino' must not be empty");

	    Date fechaActual = getFechaManager().getFechaActual();

	    // Actualizar la información del asiento registral
	    asiento.setEstado(EstadoAsientoRegistralEnum.REENVIADO);
	    asiento.setFechaEstado(fechaActual);
	    asiento.setFechaEnvio(fechaActual);
	    asiento.setCodigoError(null);
	    asiento.setDescripcionError(null);

	    update(asiento);

	    // Enviar el fichero de intercambio
	    getFicheroIntercambioManager().reenviarFicheroIntercambio(asiento);

	    // Actualizar la información del asiento
	    asiento.setFechaEnvio(getFechaManager().getFechaActual());
	    update(asiento);
    }

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#reintentarValidarAsientosRegistrales()
	 */
	public void reintentarValidarAsientosRegistrales() {

		CriteriosVO criterio = new CriteriosVO().addCriterioVO(new CriterioVO(
				CriterioEnum.ASIENTO_ESTADO, OperadorCriterioEnum.EQUAL,
				EstadoAsientoRegistralEnum.REINTENTAR_VALIDACION));
		List<AsientoRegistralVO> asientos = findAsientosRegistrales(criterio);
		if (CollectionUtils.isNotEmpty(asientos)) {
			logger.info("Intentando validar {} asiento/s registral/es",
					asientos.size());
			for (AsientoRegistralVO asiento : asientos) {
				reintentarValidarAsientoRegistral(asiento);
			}
		}
	}

	/**
	 * Reintenta validar un asiento registral
	 * @param asiento
	 */
	private void reintentarValidarAsientoRegistral(AsientoRegistralVO asiento) {
		logger.info("Validando el asiento con identificador [{}]",
				asiento.getId());
		try {
			enviarMensajeConfirmacion(asiento);
			logger.info("Mensaje de confirmación enviado");
			asiento.setEstado(EstadoAsientoRegistralEnum.VALIDADO);
			update(asiento);
			logger.info("Asiento con identificador [{}] validado",
					asiento.getId());
		} catch (Exception e) {
			logger.error(
					"Error al intentar validar el asiento con identificador [{}]",
					asiento.getId(), e);
		}
	}

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#rechazarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO)
     */
    public void rechazarAsientoRegistral(String id, InfoRechazoVO infoRechazo) {

		logger.info(
				"Rechazando el asiento registral: id=[{}], infoRechazo=[{}]",
				id, infoRechazo);

        /*
         * Desde detalle de pre-asiento en la bandeja de entrada:
         * 1 El usuario selecciona la acción de rechazar pre-asiento.
         * 2 El sistema solicita usuario:
         * 		1. El tipo de rechazo:
         * 			• Por Oficina destino errónea
         * 			• Por Pre-asiento erróneo
         * 		2. Observaciones de Rechazo
         * 3 El usuario ingresa los datos solicitados.
         * 4 El sistema genera un nuevo asiento de entrada:
         * 		• El sistema genera un nuevo número de registro de entrada.
         * 		• El sistema recupera los datos de la oficina de registro origen y destino
         * 		• El sistema ejecuta las siguientes actualizaciones en el registro del
         *        pre-asiento a reenviar:
         *        		o Nuevo Número de registro creado.
         *        		o Intercambia los valores de la Oficina de Registro Origen y Destino
         *        		  (La oficina origen inicial se convierte en la destino, y la oficina
         *        		  destino inicial se convierte en la origen)
         * 				o Observaciones ingresadas por el usuario.
         * 		• Terminado este proceso el flujo se re-direcciona al caso de uso
         * 		  AWR-CU-057 – Envío de Asientos Registrales intercambiados informando que
         * 		  se trata o de un rechazo por no corresponder, o un rechazo por erróneo.
         * 		• El sistema muestra la página de confirmación del alta del asiento registral.
         */

        Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(infoRechazo, "'infoRechazo.observaciones' must not be null");
        Assert.notNull(infoRechazo.getTipoRechazo(), "'infoRechazo.tipoRechazo' must not be null");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {

            if (!EstadoAsientoRegistralEnum.RECIBIDO.equals(asiento.getEstado())
            		&& !EstadoAsientoRegistralEnum.RECHAZADO.equals(asiento.getEstado())
            		&& !EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR.equals(asiento.getEstado())) {
                logger.error("El asiento registral a rechazar con identificador [{}] está en estado [{}]",
                        asiento.getId(), asiento.getEstado());
                throw new SIRException("error.sir.rechazarAsientoRegistral.estadoNoValido", null,
                        "El asiento registral a rechazar no está en estado RECIBIDO, RECHAZADO o RECHAZADO con ERROR");
            }

            String codigoEntidadRegistral = asiento.getCodigoEntidadRegistralOrigen();
            String descEntidadRegistral = asiento.getDescripcionEntidadRegistralOrigen();

            // Actualizar la información del asiento registral
            asiento.setCodigoEntidadRegistralOrigen(asiento.getCodigoEntidadRegistralDestino());
            asiento.setDescripcionEntidadRegistralOrigen(asiento.getDescripcionEntidadRegistralDestino());
            asiento.setNombreUsuario(infoRechazo.getUsuario());
            asiento.setContactoUsuario(infoRechazo.getContacto());
            asiento.setAplicacion(infoRechazo.getAplicacion());
            asiento.setDescripcionTipoAnotacion(infoRechazo.getDescripcion());

            if (TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN.equals(infoRechazo.getTipoRechazo())) {

                // Enviar el fichero de intercambio a la entidad registral de origen
                asiento.setCodigoEntidadRegistralDestino(codigoEntidadRegistral);
                asiento.setDescripcionEntidadRegistralDestino(descEntidadRegistral);

            } else { // TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL

                // Enviar el fichero de intercambio a la entidad registral de inicio
                asiento.setCodigoEntidadRegistralDestino(asiento.getCodigoEntidadRegistralInicio());
                asiento.setDescripcionEntidadRegistralDestino(asiento.getDescripcionEntidadRegistralInicio());
            }

            update(asiento);

            rechazarAsientoRegistral(asiento);

        } else {
            logger.error("No se ha encontrado el asiento registral a rechazar con identificador [{}]", id);
            throw new SIRException("error.sir.rechazarAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a rechazar");
        }
    }

    public void rechazarAsientoRegistral(AsientoRegistralVO asiento) {

    	if (logger.isDebugEnabled()){
    		logger.debug("Rechazando el asiento registral: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
    	}

        Assert.notNull(asiento, "'asiento' must not be null");

        // Comprobar que el estado del asiento registral sea RECIBIDO
        if (!EstadoAsientoRegistralEnum.RECIBIDO.equals(asiento.getEstado())
        		&& !EstadoAsientoRegistralEnum.RECHAZADO.equals(asiento.getEstado())
        		&& !EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR.equals(asiento.getEstado())) {
            logger.error("El asiento registral a rechazar con identificador [{}] está en estado [{}]",
                    asiento.getId(), asiento.getEstado());
            throw new SIRException("error.sir.rechazarAsientoRegistral.estadoNoValido", null,
                    "El asiento registral a rechazar no está en estado RECIBIDO, RECHAZADO o RECHAZADO con ERROR");
        }

        // Enviar el fichero de intercambio
        getFicheroIntercambioManager().rechazarFicheroIntercambio(asiento);

        Date fechaActual = getFechaManager().getFechaActual();

        // Actualizar la información del asiento
        asiento.setEstado(EstadoAsientoRegistralEnum.RECHAZADO);
        asiento.setFechaEstado(fechaActual);
        asiento.setFechaEnvio(fechaActual);
        asiento.setCodigoError(null);
        asiento.setDescripcionError(null);

        update(asiento);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#anularAsientoRegistral(java.lang.String)
     */
    public void anularAsientoRegistral(String id) {

        logger.info("Anulando el asiento registral con identificador [{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener la información del asiento registral
        AsientoRegistralVO asiento = get(id);
        if (asiento != null) {

            // Comprobar que el estado del asiento sea DEVUELTO.
            if (!EstadoAsientoRegistralEnum.DEVUELTO.equals(asiento.getEstado())
            		&& !EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR.equals(asiento.getEstado())
            		&& !EstadoAsientoRegistralEnum.REENVIADO_Y_ERROR.equals(asiento.getEstado())
            		&& !EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR.equals(asiento.getEstado())) {
                logger.error("El asiento registral a anular con identificador [{}] está en estado [{}]",
                        id, asiento.getEstado());
                throw new SIRException("error.sir.anularAsientoRegistral.estadoNoValido", null,
                        "El asiento registral a anular no está en estado DEVUELTO, ENVIADO con ERROR, REENVIADO con ERROR o RECHAZADO con ERROR");
            }

            // Actualizar la información del asiento registral
            asiento.setEstado(EstadoAsientoRegistralEnum.ANULADO);
            asiento.setFechaEstado(getFechaManager().getFechaActual());

            update(asiento);

        } else {
            logger.error("No se ha encontrado el asiento registral a anular con identificador [{}]", id);
            throw new SIRException("error.sir.anularAsientoRegistral.asientoNoEncontrado", null,
                    "No se ha encontrado el asiento registral a anular");
        }
    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager#comprobarTimeOutEnvios()
	 */
	public void comprobarTimeOutEnvios() {

		logger.info("Llamada a comprobarTimeOutEnvios");

		// Obtener la fecha que sobrepase el time-out
		Date fechaTimeOut = new Date(new Date().getTime() - getTimeOut());
		logger.info("Buscando ficheros de datos de intercambio enviados antes de [{}]", fechaTimeOut);

		// Número de reintentos
		int numeroReintentos = getNumeroReintentos();

		// Obtener los asientos registrales enviados que hayan sobrepasado el time-out
		CriteriosVO criterios = new CriteriosVO()
	    	.addCriterioVO(new CriterioVO(
	    			CriterioEnum.ASIENTO_ESTADO,
					OperadorCriterioEnum.IN,
					new EstadoAsientoRegistralEnum[] {
							EstadoAsientoRegistralEnum.ENVIADO,
							EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR,
							EstadoAsientoRegistralEnum.REENVIADO,
							EstadoAsientoRegistralEnum.REENVIADO_Y_ERROR,
							EstadoAsientoRegistralEnum.RECHAZADO,
							EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR}))
	    	.addCriterioVO(new CriterioVO(
	    			CriterioEnum.ASIENTO_FECHA_ENVIO,
					OperadorCriterioEnum.LESS_THAN,
					fechaTimeOut))
	    	.addOrderBy(CriterioEnum.ASIENTO_FECHA_ENVIO);

		if (numeroReintentos > 0) {
			criterios.addCriterioVO(new CriterioVO(
					CriterioEnum.ASIENTO_NUMERO_REINTENTOS,
					OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
					getNumeroReintentos()));
		}

        List<AsientoRegistralVO> asientos = findAsientosRegistrales(criterios);

        if (CollectionUtils.isNotEmpty(asientos)) {
	        for (AsientoRegistralVO asiento : asientos) {

	        	logger.info("Comprobando el asiento: id=[{}], identificadorIntercambio=[{}]",
	        			asiento.getId(), asiento.getIdentificadorIntercambio());

	        	// Realizar otra vez la comprobación de estado y fecha, por si se ha procesado ya en otro hilo.
	        	AsientoRegistralVO aux = get(asiento.getId());
	        	if ((aux.getFechaEnvio() != null)
	        			&& (aux.getFechaEnvio().compareTo(fechaTimeOut) < 0)) {

	        		// Comprobar que no se haya excedido del número máximo de reintentos
					if ((numeroReintentos == 0)
							|| (aux.getNumeroReintentos() <= numeroReintentos)) {

						// Incrementar el número de reintentos
						asiento.setNumeroReintentos(asiento.getNumeroReintentos() + 1);

					if (aux.getEstado().equals(EstadoAsientoRegistralEnum.ENVIADO)||aux.getEstado().equals(EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR)) {

		        			logger.info("Volviendo a enviar el asiento: id=[{}], identificadorIntercambio=[{}]",
		        					asiento.getId(), asiento.getIdentificadorIntercambio());

		        			// Enviar el asiento
		        			enviarAsientoRegistral(asiento);

					} else if (aux.getEstado().equals(EstadoAsientoRegistralEnum.REENVIADO)||aux.getEstado().equals(EstadoAsientoRegistralEnum.REENVIADO_Y_ERROR)) {

		        			logger.info("Volviendo a reenviar el asiento: id=[{}], identificadorIntercambio=[{}]",
		        					asiento.getId(), asiento.getIdentificadorIntercambio());

		        			// Reenviar el asiento
		        			reenviarAsientoRegistral(asiento);

					} else if (aux.getEstado().equals(EstadoAsientoRegistralEnum.RECHAZADO)||aux.getEstado().equals(EstadoAsientoRegistralEnum.RECHAZADO_Y_ERROR)) {

		        			logger.info("Volviendo a rechazar el asiento: id=[{}], identificadorIntercambio=[{}]",
		        					asiento.getId(), asiento.getIdentificadorIntercambio());

		        			// Rechazar el asiento
		        			rechazarAsientoRegistral(asiento);
		        		}
	        		} else {

	        			logger.error("El asiento con identificador [{}] ha excedido el número de reintentos [{}]",
	        					asiento.getId(), numeroReintentos);

						// Posible mejora: Cambiar el estado del asiento a
						// ENVIADO_TIME_OUT, REENVIADO_TIME_OUT o RECHAZADO_TIME_OUT
	        		}
	        	}
	        }
        }
	}



    /**
     * Envía un mensaje de control de confirmación.
     * @param asiento Información del asiento registral.
     */
    protected void enviarMensajeConfirmacion(AsientoRegistralVO asiento) {

        MensajeVO mensaje = new MensajeVO();
        mensaje.setCodigoEntidadRegistralOrigen(asiento.getCodigoEntidadRegistralDestino());
        mensaje.setCodigoEntidadRegistralDestino(asiento.getCodigoEntidadRegistralInicio());
        mensaje.setIdentificadorIntercambio(asiento.getIdentificadorIntercambio());
        mensaje.setTipoMensaje(TipoMensajeEnum.CONFIRMACION);
        mensaje.setDescripcionMensaje(TipoMensajeEnum.CONFIRMACION.getName());
        mensaje.setNumeroRegistroEntradaDestino(asiento.getNumeroRegistro());
        mensaje.setFechaEntradaDestino(asiento.getFechaRegistro());

        getMensajeManager().enviarMensaje(mensaje);
    }

    protected long getTimeOut() {

		long timeout = getDefaultTimeOut();

		if (getConfiguracionManager() != null) {

			// Obtener el time-out a partir de la configuración en base de
			// datos
			String strTimeOut = getConfiguracionManager()
					.getValorConfiguracion(TIME_OUT_PARAM_NAME);
			logger.info("Valor de {} en base de datos: [{}]", TIME_OUT_PARAM_NAME, strTimeOut);

			if (StringUtils.isNotBlank(strTimeOut)) {
				try {
					timeout = Long.parseLong(strTimeOut);
				} catch (NumberFormatException e) {
					logger.error(
							"Error al parsear el valor del parámetro de configuración "
									+ TIME_OUT_PARAM_NAME + ": [" + strTimeOut
									+ "]", e);
					timeout = getDefaultTimeOut();
				}
			}
		}

		logger.info("Time-out para envíos: [{}]", timeout);

		return timeout;
    }

    protected int getNumeroReintentos() {

		int numReintentos = getDefaultNumeroReintentos();

		if (getConfiguracionManager() != null) {

			// Obtener el número de reintentos de envío a partir de la
			// configuración en base de datos
			String strNumReintentos = getConfiguracionManager()
					.getValorConfiguracion(NUMERO_REINTENTOS_PARAM_NAME);
			logger.info("Valor de {} en base de datos: [{}]", NUMERO_REINTENTOS_PARAM_NAME, strNumReintentos);

			if (StringUtils.isNotBlank(strNumReintentos)) {
				try {
					numReintentos = Integer.parseInt(strNumReintentos);
				} catch (NumberFormatException e) {
					logger.error(
							"Error al parsear el valor del parámetro de configuración "
									+ NUMERO_REINTENTOS_PARAM_NAME + ": [" + strNumReintentos
									+ "]", e);
					numReintentos = getDefaultNumeroReintentos();
				}
			}
		}

		logger.info("Número de reintentos para envíos: [{}]", numReintentos);

		return numReintentos;
    }



}
