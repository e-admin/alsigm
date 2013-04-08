package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;
import es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaFicheroIntercambioManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Implementación del manager de auditoría de ficheros de datos de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AuditoriaFicheroIntercambioManagerImpl extends
		AuditoriaManagerBaseImpl implements AuditoriaFicheroIntercambioManager {

    private static final Logger logger = LoggerFactory
            .getLogger(AuditoriaFicheroIntercambioManagerImpl.class);
    
    private static final String ACTIVADO_PARAM_NAME = "auditoria.ficherosIntercambio.activado";
    private static final String CONTENT_TYPE_ID_PARAM_NAME = "auditoria.ficherosIntercambio.contentTypeId";

    /**
     * Factoría de servicios de gestión documental.
     */
    private GestionDocumentalServiceFactory gestionDocumentalServiceFactory = null;

    /**
     * Gestor de XML de SICRES.
     */
    private SicresXMLManager sicresXMLManager = null;

    /**
     * Gestor de fechas.
     */
    private FechaManager fechaManager = null;

    /**
     * Constructor.
     *
     * @param aGenericDao
     */
    public AuditoriaFicheroIntercambioManagerImpl() {
        super();
    }

    public GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
        return gestionDocumentalServiceFactory;
    }

    public void setGestionDocumentalServiceFactory(
            GestionDocumentalServiceFactory gestionDocumentalServiceFactory) {
        this.gestionDocumentalServiceFactory = gestionDocumentalServiceFactory;
    }

    public SicresXMLManager getSicresXMLManager() {
        return sicresXMLManager;
    }

    public void setSicresXMLManager(SicresXMLManager sicresXMLManager) {
        this.sicresXMLManager = sicresXMLManager;
    }

    public FechaManager getFechaManager() {
        return fechaManager;
    }

    public void setFechaManager(FechaManager fechaManager) {
        this.fechaManager = fechaManager;
    }

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaFicheroIntercambioManager#audita(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO,
	 *      es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum)
	 */
	public void audita(AsientoRegistralVO asientoRegistral, BandejaEnum bandeja) {

		if (logger.isInfoEnabled()){
			logger.info("Auditando el fichero de datos de intercambio [{}]: [{}]",
					bandeja, ToStringLoggerHelper.toStringLogger(asientoRegistral));
		}

        if (isActivado()) {

            Assert.notNull(asientoRegistral, "'asientoRegistral' must not be null");
            Assert.notNull(bandeja, "'bandeja' must not be null");

            // Construir el XML del fichero de intercambio
            String xml = getSicresXMLManager().createXMLFicheroIntercambio(asientoRegistral, true);

            // Auditar el XML
            auditaFicheroIntercambio(xml, getMetadatos(asientoRegistral, bandeja));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaFicheroIntercambioManager#audita(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO,
     *      java.lang.String, es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum)
     */
    public void audita(FicheroIntercambioVO ficheroIntercambio, String xml,
            BandejaEnum bandeja) {

    	if (logger.isInfoEnabled()){
	        logger.info("Auditando el fichero de datos de intercambio [{}]: [{}]",
	                bandeja, ToStringLoggerApiHelper.toStringLogger(ficheroIntercambio));
    	}
    	
    	if (logger.isDebugEnabled()){
    		logger.debug("XML del fichero de datos de intercambio: {}", xml);
    	}

        if (isActivado()) {

            Assert.notNull(ficheroIntercambio,
                    "'ficheroIntercambio' must not be null");
            Assert.notNull(bandeja, "'bandeja' must not be null");

            // Auditar el XML
            auditaFicheroIntercambio(xml, getMetadatos(ficheroIntercambio, bandeja));
        }
    }

    protected void auditaFicheroIntercambio(String xml, List<MetadatoVO> metadatos) {

        Assert.notNull(xml, "'xml' must not be null");

        // Servicio de gestión documental
        GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

        try {

            gestionDocumentalService.createSesion();

            // Información del documento
            InfoDocumentoVO infoDocumento = new InfoDocumentoVO();
            infoDocumento.setNombre("ficheroIntercambio.xml");
            infoDocumento.setTipoMime("text/xml");
            infoDocumento.setMetadatos(metadatos);

            // Crear el documento en el gestor documental
            infoDocumento = gestionDocumentalService.createDocumento(infoDocumento, new ByteArrayInputStream(xml.getBytes()));

            logger.info("Fichero de intercambio almacenado correctamente en el gestor documental con el id [{}]", infoDocumento.getId());

            // Eliminar el documento si se está lanzando un test
            if (isTest()) {
                gestionDocumentalService.deleteDocumento(infoDocumento.getId());
            }

        } catch (GestionDocumentalException e) {
            logger.error("Error al auditar el fichero de intercambio en el gestor documental", e);
            throw new SIRException("error.sir.auditoria.auditarFicheroIntercambio", null,
                    "Error al auditar el fichero de intercambio en el gestor documental");
        } finally {
            try {
                gestionDocumentalService.releaseSesion();
            } catch (GestionDocumentalException e) {
                logger.error("Error al finalizar la conexión con el gestor documental", e);
            }
        }

    }

    protected List<MetadatoVO> getMetadatos(AsientoRegistralVO asientoRegistral, BandejaEnum bandeja) {

        List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();

        metadatos.add(new MetadatoVO("bandeja", bandeja.getValue()));
        metadatos.add(new MetadatoVO("fechaCreacion", getFechaManager().getFechaActual()));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralOrigen", asientoRegistral.getCodigoEntidadRegistralOrigen()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralOrigen", asientoRegistral.getDescripcionEntidadRegistralOrigen()));
        metadatos.add(new MetadatoVO("codigoUnidadTramitacionOrigen", asientoRegistral.getCodigoUnidadTramitacionOrigen()));
        metadatos.add(new MetadatoVO("descripcionUnidadTramitacionOrigen", asientoRegistral.getDescripcionUnidadTramitacionOrigen()));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralDestino", asientoRegistral.getCodigoEntidadRegistralDestino()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralDestino", asientoRegistral.getDescripcionEntidadRegistralDestino()));
        metadatos.add(new MetadatoVO("codigoUnidadTramitacionDestino", asientoRegistral.getCodigoUnidadTramitacionDestino()));
        metadatos.add(new MetadatoVO("descripcionUnidadTramitacionDestino", asientoRegistral.getDescripcionUnidadTramitacionDestino()));
        metadatos.add(new MetadatoVO("numeroRegistro", asientoRegistral.getNumeroRegistro()));
        metadatos.add(new MetadatoVO("fechaRegistro", asientoRegistral.getFechaRegistro()));
        metadatos.add(new MetadatoVO("timestampRegistro", (asientoRegistral.getTimestampRegistro() != null
                ? Base64.encodeBase64String(asientoRegistral.getTimestampRegistro()) : null)));
        metadatos.add(new MetadatoVO("resumen", asientoRegistral.getResumen()));
        metadatos.add(new MetadatoVO("codigoAsunto", asientoRegistral.getCodigoAsunto()));
        metadatos.add(new MetadatoVO("referenciaExterna", asientoRegistral.getReferenciaExterna()));
        metadatos.add(new MetadatoVO("numeroExpediente", asientoRegistral.getNumeroExpediente()));
        metadatos.add(new MetadatoVO("tipoTransporte", (asientoRegistral.getTipoTransporte() != null
                ? asientoRegistral.getTipoTransporte().getValue() : null)));
        metadatos.add(new MetadatoVO("numeroTransporte", asientoRegistral.getNumeroTransporte()));
        metadatos.add(new MetadatoVO("nombreUsuario", asientoRegistral.getNombreUsuario()));
        metadatos.add(new MetadatoVO("contactoUsuario", asientoRegistral.getContactoUsuario()));
        metadatos.add(new MetadatoVO("identificadorIntercambio", asientoRegistral.getIdentificadorIntercambio()));
        metadatos.add(new MetadatoVO("aplicacionEmisora", asientoRegistral.getAplicacion()));
        metadatos.add(new MetadatoVO("tipoAnotacion", (asientoRegistral.getTipoAnotacion() != null ? asientoRegistral.getTipoAnotacion().getValue() : null)));
        metadatos.add(new MetadatoVO("descripcionTipoAnotacion", asientoRegistral.getDescripcionTipoAnotacion()));
        metadatos.add(new MetadatoVO("tipoRegistro", (asientoRegistral.getTipoRegistro() != null
        		? asientoRegistral.getTipoRegistro().getValue() : null)));
        metadatos.add(new MetadatoVO("documentacionFisica", (asientoRegistral.getDocumentacionFisica() != null
        		? asientoRegistral.getDocumentacionFisica().getValue() : null)));
        metadatos.add(new MetadatoVO("observacionesApunte", asientoRegistral.getObservacionesApunte()));
        metadatos.add(new MetadatoVO("indicadorPrueba", (asientoRegistral.getIndicadorPrueba() != null
        		? asientoRegistral.getIndicadorPrueba().getValue() : null)));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralInicio", asientoRegistral.getCodigoEntidadRegistralInicio()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralInicio", asientoRegistral.getDescripcionEntidadRegistralInicio()));
        metadatos.add(new MetadatoVO("expone", asientoRegistral.getExpone()));
        metadatos.add(new MetadatoVO("solicita", asientoRegistral.getSolicita()));

        return metadatos;
    }

    protected List<MetadatoVO> getMetadatos(FicheroIntercambioVO ficheroIntercambio, BandejaEnum bandeja) {

        List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();

        metadatos.add(new MetadatoVO("bandeja", bandeja.getValue()));
        metadatos.add(new MetadatoVO("fechaCreacion", getFechaManager().getFechaActual()));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralOrigen", ficheroIntercambio.getCodigoEntidadRegistralOrigen()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralOrigen", ficheroIntercambio.getDescripcionEntidadRegistralOrigen()));
        metadatos.add(new MetadatoVO("codigoUnidadTramitacionOrigen", ficheroIntercambio.getCodigoUnidadTramitacionOrigen()));
        metadatos.add(new MetadatoVO("descripcionUnidadTramitacionOrigen", ficheroIntercambio.getDescripcionUnidadTramitacionOrigen()));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralDestino", ficheroIntercambio.getCodigoEntidadRegistralDestino()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralDestino", ficheroIntercambio.getDescripcionEntidadRegistralDestino()));
        metadatos.add(new MetadatoVO("codigoUnidadTramitacionDestino", ficheroIntercambio.getCodigoUnidadTramitacionDestino()));
        metadatos.add(new MetadatoVO("descripcionUnidadTramitacionDestino", ficheroIntercambio.getDescripcionUnidadTramitacionDestino()));
        metadatos.add(new MetadatoVO("numeroRegistro", ficheroIntercambio.getNumeroRegistro()));
        metadatos.add(new MetadatoVO("fechaRegistro", ficheroIntercambio.getFechaRegistro()));
        metadatos.add(new MetadatoVO("timestampRegistro", (ficheroIntercambio.getTimestampRegistro() != null
                ? Base64.encodeBase64String(ficheroIntercambio.getTimestampRegistro()) : null)));
        metadatos.add(new MetadatoVO("resumen", ficheroIntercambio.getResumen()));
        metadatos.add(new MetadatoVO("codigoAsunto", ficheroIntercambio.getCodigoAsunto()));
        metadatos.add(new MetadatoVO("referenciaExterna", ficheroIntercambio.getReferenciaExterna()));
        metadatos.add(new MetadatoVO("numeroExpediente", ficheroIntercambio.getNumeroExpediente()));
        metadatos.add(new MetadatoVO("tipoTransporte", (ficheroIntercambio.getTipoTransporte() != null
                ? ficheroIntercambio.getTipoTransporte().getValue() : null)));
        metadatos.add(new MetadatoVO("numeroTransporte", ficheroIntercambio.getNumeroTransporte()));
        metadatos.add(new MetadatoVO("nombreUsuario", ficheroIntercambio.getNombreUsuario()));
        metadatos.add(new MetadatoVO("contactoUsuario", ficheroIntercambio.getContactoUsuario()));
        metadatos.add(new MetadatoVO("identificadorIntercambio", ficheroIntercambio.getIdentificadorIntercambio()));
        metadatos.add(new MetadatoVO("aplicacionEmisora", ficheroIntercambio.getAplicacionEmisora()));
        metadatos.add(new MetadatoVO("tipoAnotacion", (ficheroIntercambio.getTipoAnotacion() != null
        		? ficheroIntercambio.getTipoAnotacion().getValue() : null)));
        metadatos.add(new MetadatoVO("descripcionTipoAnotacion", (ficheroIntercambio.getTipoAnotacion() != null
        		? ficheroIntercambio.getTipoAnotacion().getName() : null)));
        metadatos.add(new MetadatoVO("tipoRegistro", (ficheroIntercambio.getTipoRegistro() != null
        		? ficheroIntercambio.getTipoRegistro().getValue() : null)));
        metadatos.add(new MetadatoVO("documentacionFisica", (ficheroIntercambio.getDocumentacionFisica() != null
        		? ficheroIntercambio.getDocumentacionFisica().getValue() : null)));
        metadatos.add(new MetadatoVO("observacionesApunte", ficheroIntercambio.getObservacionesApunte()));
        metadatos.add(new MetadatoVO("indicadorPrueba", (ficheroIntercambio.getIndicadorPrueba() != null
        		? ficheroIntercambio.getIndicadorPrueba().getValue() : null)));
        metadatos.add(new MetadatoVO("codigoEntidadRegistralInicio", ficheroIntercambio.getCodigoEntidadRegistralInicio()));
        metadatos.add(new MetadatoVO("descripcionEntidadRegistralInicio", ficheroIntercambio.getDescripcionEntidadRegistralInicio()));
        metadatos.add(new MetadatoVO("expone", ficheroIntercambio.getExpone()));
        metadatos.add(new MetadatoVO("solicita", ficheroIntercambio.getSolicita()));

        return metadatos;
    }

	@Override
	protected String getActivadoParamName() {
		return ACTIVADO_PARAM_NAME;
	}

	@Override
	protected String getContentTypeIdParamName() {
		return CONTENT_TYPE_ID_PARAM_NAME;
	}
}
