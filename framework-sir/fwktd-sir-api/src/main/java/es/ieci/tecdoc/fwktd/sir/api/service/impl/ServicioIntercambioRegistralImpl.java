package es.ieci.tecdoc.fwktd.sir.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.RecepcionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.TrazabilidadManager;
import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

/**
 * Implementación del servicio de intercambio registral en formato SICRES 3.0.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioIntercambioRegistralImpl implements ServicioIntercambioRegistral {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(ServicioIntercambioRegistralImpl.class);


    /**
     * Gestor de asientos registrales.
     */
    private AsientoRegistralManager asientoRegistralManager = null;

    /**
     * Gestor de anexos de asientos registrales.
     */
    private AnexoManager anexoManager = null;

    /**
     * Gestor de interesados de asientos registrales.
     */
    private InteresadoManager interesadoManager = null;

    /**
     * Gestor de trazabilidad.
     */
    private TrazabilidadManager trazabilidadManager = null;

//    /**
//     * Gestor de ficheros de intercambio.
//     */
//    private FicheroIntercambioManager ficheroIntercambioManager = null;

//    /**
//     * Gestor de ficheros de datos de control.
//     */
//    private MensajeManager mensajeManager = null;

	/**
	 * Gestor de recepción de ficheros de datos de intercambio y de control.
	 */
	private RecepcionManager recepcionManager = null;

//	/**
//	 * Gestor que comprueba la validez de los documentos de asientos registrales.
//	 */
//	private ValidacionAnexosManager validacionAnexosManager = null;

    /**
     * Constructor.
     */
    public ServicioIntercambioRegistralImpl() {
        super();
    }

    public AsientoRegistralManager getAsientoRegistralManager() {
        return asientoRegistralManager;
    }

    public void setAsientoRegistralManager(
            AsientoRegistralManager asientoRegistralManager) {
        this.asientoRegistralManager = asientoRegistralManager;
    }

    public AnexoManager getAnexoManager() {
        return anexoManager;
    }

    public void setAnexoManager(AnexoManager anexoManager) {
        this.anexoManager = anexoManager;
    }

    public InteresadoManager getInteresadoManager() {
        return interesadoManager;
    }

    public void setInteresadoManager(InteresadoManager interesadoManager) {
        this.interesadoManager = interesadoManager;
    }

    public TrazabilidadManager getTrazabilidadManager() {
        return trazabilidadManager;
    }

    public void setTrazabilidadManager(TrazabilidadManager trazabilidadManager) {
        this.trazabilidadManager = trazabilidadManager;
    }

	public RecepcionManager getRecepcionManager() {
		return recepcionManager;
	}

	public void setRecepcionManager(RecepcionManager recepcionManager) {
		this.recepcionManager = recepcionManager;
	}

//	public ValidacionAnexosManager getValidacionAnexosManager() {
//		return validacionAnexosManager;
//	}
//
//	public void setValidacionAnexosManager(ValidacionAnexosManager validacionAnexosManager) {
//		this.validacionAnexosManager = validacionAnexosManager;
//	}

	/**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#countAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
     */
    public int countAsientosRegistrales(CriteriosVO criterios) {

        logger.info("Llamada a countAsientosRegistrales: criterios=[{}]", criterios);

        // Obtener el número de asientos registrales
        return getAsientoRegistralManager().countAsientosRegistrales(criterios);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#findAsientosRegistrales(es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO)
     */
    public List<AsientoRegistralVO> findAsientosRegistrales(CriteriosVO criterios) {

        logger.info("Llamada a findAsientosRegistrales: criterios=[{}]", criterios);

        // Realizar la búsqueda de asientos registrales
        return getAsientoRegistralManager().findAsientosRegistrales(criterios);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getEstadoAsientoRegistral(java.lang.String)
     */
    public EstadoAsientoRegistraVO getEstadoAsientoRegistral(String id) {

    	EstadoAsientoRegistraVO result = null;
        logger.info("Llamada a getEstadoAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener el estado del asiento registral
        result = getAsientoRegistralManager().getEstado(id);

        return result;
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getAsientoRegistral(java.lang.String)
     */
    public AsientoRegistralVO getAsientoRegistral(String id) {

        logger.info("Llamada a getAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener la información del asiento registral
        return getAsientoRegistralManager().get(id);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#saveAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
     */
    public AsientoRegistralVO saveAsientoRegistral(AsientoRegistralFormVO asientoForm) {

    	if (logger.isInfoEnabled()){
    		logger.info("Llamada a saveAsientoRegistral: [{}]", ToStringLoggerHelper.toStringLogger(asientoForm));
    	}

        Assert.notNull(asientoForm, "'asientoForm' must not be null");

        // Salvar el asiento registral
        return getAsientoRegistralManager().saveAsientoRegistral(asientoForm);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO)
     */
    public void updateAsientoRegistral(InfoBAsientoRegistralVO infoBAsiento) {

    	if (logger.isInfoEnabled()){
    		logger.info("Llamada a updateAsientoRegistral: [{}]", ToStringLoggerHelper.toStringLogger(infoBAsiento));
    	}

        Assert.notNull(infoBAsiento, "'infoBAsiento' must not be null");
        Assert.hasText(infoBAsiento.getId(), "'infoBAsiento.id' must not be empty");

        // Construir el objeto del asiento registral
        AsientoRegistralVO asiento = new AsientoRegistralVO();
        BeanUtils.copyProperties(infoBAsiento, asiento);

        // Salvar el asiento registral
        getAsientoRegistralManager().update(asiento);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#deleteAsientoRegistral(java.lang.String)
     */
    public void deleteAsientoRegistral(String id) {

        logger.info("Llamada a deleteAsientoRegistral: [{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Eliminar el asiento registral
        getAsientoRegistralManager().deleteAsientoRegistral(id);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#addAnexo(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO)
     */
    public AnexoVO addAnexo(String idAsientoRegistral, AnexoFormVO anexoForm) {

    	if (logger.isInfoEnabled()){
    		logger.info("Llamada a addAnexo: [{}]", ToStringLoggerHelper.toStringLogger(anexoForm));
    	}

        Assert.notNull(anexoForm, "'anexo' must not be null");
        Assert.hasText(idAsientoRegistral, "'idAsientoRegistral' must not be empty");

        // Salvar el anexo
        return getAnexoManager().saveAnexo(idAsientoRegistral, anexoForm);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateAnexo(es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO)
     */
    public AnexoVO updateAnexo(AnexoVO anexo) {

    	if (logger.isInfoEnabled()){
    		logger.info("Llamada a updateAnexo: [{}]", ToStringLoggerHelper.toStringLogger(anexo));
    	}

        Assert.notNull(anexo, "'anexo' must not be null");
        Assert.hasText(anexo.getId(), "'anexo.id' must not be empty");
        Assert.hasText(anexo.getIdAsientoRegistral(), "'anexo.idAsientoRegistral' must not be empty");

        // Modificar el anexo
        return getAnexoManager().update(anexo);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#removeAnexo(java.lang.String)
     */
    public void removeAnexo(String idAnexo) {

        logger.info("Llamada a removeAnexo: [{}]", idAnexo);

        Assert.hasText(idAnexo, "'idAnexo' must not be empty");

        // Eliminar el anexo
        getAnexoManager().delete(idAnexo);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#addInteresado(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO)
     */
    public InteresadoVO addInteresado(String idAsientoRegistral,
			InteresadoFormVO interesadoForm) {

    	if(logger.isInfoEnabled()){
    		logger.info("Llamada a addInteresado: [{}]", ToStringLoggerHelper.toStringLogger(interesadoForm));
    	}

        Assert.hasText(idAsientoRegistral, "'idAsientoRegistral' must not be empty");
        Assert.notNull(interesadoForm, "'interesadoForm' must not be null");

        // Salvar el interesado
        return getInteresadoManager().saveInteresado(idAsientoRegistral, interesadoForm);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#updateInteresado(es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO)
     */
    public InteresadoVO updateInteresado(InteresadoVO interesado) {

    	if(logger.isInfoEnabled()){
    		logger.info("Llamada a updateInteresado: [{}]", ToStringLoggerHelper.toStringLogger(interesado));
    	}

        Assert.notNull(interesado, "'interesado' must not be null");
        Assert.hasText(interesado.getId(), "'interesado.id' must not be empty");
        Assert.hasText(interesado.getIdAsientoRegistral(), "'interesado.idAsientoRegistral' must not be empty");

        // Modificar el interesado
        return getInteresadoManager().update(interesado);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#removeInteresado(java.lang.String)
     */
    public void removeInteresado(String idInteresado) {

        logger.info("Llamada a removeInteresado: [{}]", idInteresado);

        Assert.hasText(idInteresado, "'idInteresado' must not be empty");

        // Eliminar el interesado
        getInteresadoManager().delete(idInteresado);
    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getContenidoAnexo(java.lang.String)
	 */
	public byte[] getContenidoAnexo(String id) {

        logger.info("Llamada a getContenidoAnexo: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Obtener el contenido del anexo
        return getAnexoManager().getContenidoAnexo(id);
    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#setContenidoAnexo(java.lang.String, byte[])
	 */
	public void setContenidoAnexo(String id, byte[] contenido) {

        logger.info("Llamada a updateContenidoAnexo: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Actualziar el contenido del anexo
        getAnexoManager().setContenidoAnexo(id, contenido);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoAsientoRegistral(java.lang.String)
     */
    public List<TrazabilidadVO> getHistoricoAsientoRegistral(String id) {

        logger.info("Llamada a getHistoricoAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        List<TrazabilidadVO> trazas = null;

        // Obtener el código de intercambio del asiento registral
        AsientoRegistralVO asiento = getAsientoRegistralManager().get(id);
        logger.debug("Asiento registral: id=[{}], codigoEntidadRegistral=[{}], codigoIntercambio=[{}]",
        		new Object[] { asiento.getId(), asiento.getCodigoEntidadRegistral(), asiento.getIdentificadorIntercambio()});

        if (StringUtils.isNotBlank(asiento.getIdentificadorIntercambio())) {

            // Obtener las trazas de intercambio
			trazas = getTrazabilidadManager().getTrazabilidad(
					asiento.getCodigoEntidadRegistral(),
					asiento.getIdentificadorIntercambio(), true, false);
        }

        return trazas;
    }

    /* (non-Javadoc)
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoCompletoAsientoRegistral(java.lang.String)
     */
    public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String id){
    	logger.info("Llamada a getHistoricoCompletoAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        List<TrazabilidadVO> trazas = new ArrayList<TrazabilidadVO>();
        //historico de estados
        trazas.addAll(this.getHistoricoAsientoRegistral(id));
        //historio de mensajes
        trazas.addAll(this.getHistoricoMensajeIntercambioRegistral(id));

        return trazas;


    }

    /* (non-Javadoc)
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#getHistoricoMensajeIntercambioRegistral(java.lang.String)
     */
    public List<TrazabilidadVO> getHistoricoMensajeIntercambioRegistral(String id){

        logger.info("Llamada a getHistoricoMensajeIntercambioRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        List<TrazabilidadVO> trazas = null;

        // Obtener el código de intercambio del asiento registral
        AsientoRegistralVO asiento = getAsientoRegistralManager().get(id);
        logger.debug("Asiento registral: id=[{}], codigoEntidadRegistral=[{}], codigoIntercambio=[{}]",
        		new Object[] { asiento.getId(), asiento.getCodigoEntidadRegistral(), asiento.getIdentificadorIntercambio()});

        if (StringUtils.isNotBlank(asiento.getIdentificadorIntercambio())) {

            // Obtener las trazas de intercambio
			trazas = getTrazabilidadManager().getTrazabilidad(
					asiento.getCodigoEntidadRegistral(),
					asiento.getIdentificadorIntercambio(), false, false);
        }

        return trazas;
    }


    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#enviarAsientoRegistral(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO)
     */
    public AsientoRegistralVO enviarAsientoRegistral(AsientoRegistralFormVO asientoForm) {

    	if(logger.isInfoEnabled()){
    		logger.info("Llamada a enviarAsientoRegistral: [{}]", ToStringLoggerHelper.toStringLogger(asientoForm));
    	}

        Assert.notNull(asientoForm, "'asientoForm' must not be null");

        // Enviar el asiento registral
        return getAsientoRegistralManager().enviarAsientoRegistral(asientoForm);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#enviarAsientoRegistral(java.lang.String)
     */
    public void enviarAsientoRegistral(String id) {

        logger.info("Llamada a enviarAsientoRegistral: [{}]", id);

        Assert.hasText(id, "'id' must not be null");

        // Enviar el asiento registral
        getAsientoRegistralManager().enviarAsientoRegistral(id);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#validarAsientoRegistral(java.lang.String, java.lang.String, java.util.Date)
     */
    public void validarAsientoRegistral(String id, String numeroRegistro, Date fechaRegistro) {

        logger.info("Llamada a validarAsientoRegistral: id=[{}], numeroRegistro=[{}], fechaRegistro=[{}]",
                new Object[] { id, numeroRegistro, fechaRegistro });

        Assert.hasText(id, "'id' must not be empty");
        Assert.hasText(numeroRegistro, "'numeroRegistro' must not be empty");
        Assert.notNull(fechaRegistro, "'fechaRegistro' must not be null");

        // Validar el asiento registral
        getAsientoRegistralManager().validarAsientoRegistral(id, numeroRegistro, fechaRegistro);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#reenviarAsientoRegistral(java.lang.String)
     */
    public void reenviarAsientoRegistral(String id) {

        logger.info("Llamada a reenviarAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Reenviar el asiento registral
        getAsientoRegistralManager().reenviarAsientoRegistral(id);
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#reintentarValidarAsientosRegistrales()
	 */
	public void reintentarValidarAsientosRegistrales() {
		logger.info("Llamada a reintentarValidarAsientosRegistrales()");
		getAsientoRegistralManager().reintentarValidarAsientosRegistrales();
	}

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#reenviarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO)
     */
    public void reenviarAsientoRegistral(String id, InfoReenvioVO infoReenvio) {

		logger.info(
				"Llamada a reenviarAsientoRegistral: id=[{}], infoReenvio=[{}]",
				id, infoReenvio);

		Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(infoReenvio, "'infoReenvio' must not be null");

        // Reenviar el asiento registral
        getAsientoRegistralManager().reenviarAsientoRegistral(id, infoReenvio);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#rechazarAsientoRegistral(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO)
     */
    public void rechazarAsientoRegistral(String id, InfoRechazoVO infoRechazo) {

		logger.info(
				"Llamada a rechazarAsientoRegistral: id=[{}], infoRechazo=[{}]",
				id, infoRechazo);

        Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(infoRechazo, "'infoRechazo' must not be null");

        // Rechazar el asiento registral
        getAsientoRegistralManager().rechazarAsientoRegistral(id, infoRechazo);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#anularAsientoRegistral(java.lang.String)
     */
    public void anularAsientoRegistral(String id) {

        logger.info("Llamada a anularAsientoRegistral: id=[{}]", id);

        Assert.hasText(id, "'id' must not be empty");

        // Anular el asiento registral
        getAsientoRegistralManager().anularAsientoRegistral(id);
    }

//    /**
//     * {@inheritDoc}
//     * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#validarAnexos(java.lang.String)
//     */
//    public List<ValidacionAnexoVO> validarAnexos(String id) {
//
//    	logger.info("Llamada a validarAnexos: id=[{}]", id);
//
//    	Assert.hasText(id, "'id' must not be empty");
//
//        // Validar los anexos del asiento registral
//        return getValidacionAnexosManager().validarAnexos(id);
//    }

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#recibirFicheroIntercambio(java.lang.String,
	 *      java.util.List)
	 */
	public AsientoRegistralVO recibirFicheroIntercambio(
			String xmlFicheroIntercambio, List<byte[]> documentos) {

		logger.info("Llamada a recibirFicheroIntercambio");

		Assert.hasText(xmlFicheroIntercambio, "'xmlFicheroIntercambio' must not be empty");

		// Cargar la información del asiento registral
		return getRecepcionManager().recibirFicheroIntercambio( xmlFicheroIntercambio, documentos);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#recibirMensaje(java.lang.String)
	 */
	public void recibirMensaje(String xmlMensaje) {

		logger.info("Llamada a recibirMensaje: [{}]", xmlMensaje);

		Assert.hasText(xmlMensaje, "'xmlMensaje' must not be empty");

		// Recibir el mensaje
		getRecepcionManager().recibirMensaje(xmlMensaje);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#procesarFicherosRecibidos()
	 */
	public void procesarFicherosRecibidos() {

		logger.info("Llamada a procesarFicherosRecibidos");

		// Procesar los ficheros recibidos
		getRecepcionManager().procesarFicherosRecibidos();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral#comprobarTimeOutEnvios()
	 */
	public void comprobarTimeOutEnvios() {

		logger.info("Llamada a comprobarTimeOutEnvios");

		// Comprobar el time-out de los ficheros de intercambio enviados
		getAsientoRegistralManager().comprobarTimeOutEnvios();
	}


}
