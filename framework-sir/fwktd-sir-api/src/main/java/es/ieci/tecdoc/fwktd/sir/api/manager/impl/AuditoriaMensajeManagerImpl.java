package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;
import es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaMensajeManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;

/**
 * Implementación del manager de auditoría de ficheros de datos de control.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AuditoriaMensajeManagerImpl extends AuditoriaManagerBaseImpl
		implements AuditoriaMensajeManager {

	private static final Logger logger = LoggerFactory.getLogger(AuditoriaMensajeManagerImpl.class);

    private static final String ACTIVADO_PARAM_NAME = "auditoria.mensajes.activado";
    private static final String CONTENT_TYPE_ID_PARAM_NAME = "auditoria.mensajes.contentTypeId";

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
	 */
	public AuditoriaMensajeManagerImpl() {
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
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaMensajeManager#audita(es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO, es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum)
	 */
	public void audita(MensajeVO mensaje, BandejaEnum bandeja) {

		if (logger.isInfoEnabled()){
			logger.info("Auditando el mensaje [{}]: [{}]", bandeja, ToStringLoggerApiHelper.toStringLogger(mensaje));
		}

		if (isActivado()) {

			// Generando el XML a partir de la información del mensaje
			String xml = getSicresXMLManager().createXMLMensaje(mensaje);

			auditaMensaje(mensaje, xml, bandeja);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaMensajeManager#audita(es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO, java.lang.String, es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum)
	 */
	public void audita(MensajeVO mensaje, String xml, BandejaEnum bandeja) {

		if (logger.isInfoEnabled()){
			logger.info("Auditando el mensaje [{}]: [{}]", bandeja, ToStringLoggerApiHelper.toStringLogger(mensaje));
		}
		
		if (logger.isDebugEnabled()){
			logger.debug("XML del mensaje: {}", xml);
		}

		if (isActivado()) {
			auditaMensaje(mensaje, xml, bandeja);
		}
	}

	protected void auditaMensaje(MensajeVO mensaje, String xml, BandejaEnum bandeja) {

		Assert.notNull(mensaje, "'mensaje' must not be null");
		Assert.notNull(xml, "'xml' must not be null");
		Assert.notNull(bandeja, "'bandeja' must not be null");

		// Servicio de gestión documental
		GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

		try {

			gestionDocumentalService.createSesion();

			// Información del documento
			InfoDocumentoVO infoDocumento = getInfoDocumentoVO(mensaje, bandeja);

			// Crear el documento en el gestor documental
			infoDocumento = gestionDocumentalService.createDocumento(infoDocumento, new ByteArrayInputStream(xml.getBytes()));

			logger.info("Mensaje almacenado correctamente en el gestor documental con el id [{}]", infoDocumento.getId());

			// Eliminar el documento si se está lanzando un test
			if (isTest()) {
				gestionDocumentalService.deleteDocumento(infoDocumento.getId());
			}

		} catch (GestionDocumentalException e) {
			logger.error("Error al auditar el mensaje en el gestor documental", e);
			throw new SIRException("error.sir.auditoria.auditarMensaje", null,
					"Error al auditar el mensaje en el gestor documental");
		} finally {
			try {
				gestionDocumentalService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la conexión con el gestor documental", e);
			}
		}
	}

	protected InfoDocumentoVO getInfoDocumentoVO(MensajeVO mensaje, BandejaEnum bandeja) {

		InfoDocumentoVO infoDocumento = new InfoDocumentoVO();

		infoDocumento.setNombre("mensaje.xml");
		infoDocumento.setTipoMime("text/xml");

		List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();
		metadatos.add(new MetadatoVO("bandeja", bandeja.getValue()));
		metadatos.add(new MetadatoVO("fechaCreacion", getFechaManager().getFechaActual()));
		metadatos.add(new MetadatoVO("codigoEntidadRegistralOrigen", mensaje.getCodigoEntidadRegistralOrigen()));
		metadatos.add(new MetadatoVO("codigoEntidadRegistralDestino", mensaje.getCodigoEntidadRegistralDestino()));
		metadatos.add(new MetadatoVO("identificadorIntercambio", mensaje.getIdentificadorIntercambio()));
		metadatos.add(new MetadatoVO("tipoMensaje", mensaje.getTipoMensaje().getValue()));
		metadatos.add(new MetadatoVO("descripcionMensaje", mensaje.getDescripcionMensaje()));
		metadatos.add(new MetadatoVO("numeroRegistroEntradaDestino", mensaje.getNumeroRegistroEntradaDestino()));
		metadatos.add(new MetadatoVO("fechaEntradaDestino", mensaje.getFechaEntradaDestino()));
		metadatos.add(new MetadatoVO("indicadorPrueba", (mensaje.getIndicadorPrueba() != null
				? mensaje.getIndicadorPrueba().getValue() : null)));
		metadatos.add(new MetadatoVO("identificadoresFicheros", getCadenaIdentificadoresFichero(mensaje)));
		metadatos.add(new MetadatoVO("codigoError", mensaje.getCodigoError()));

		infoDocumento.setMetadatos(metadatos);

		return infoDocumento;
	}

	protected String getCadenaIdentificadoresFichero(MensajeVO mensaje) {

		String identificadores = null;

		if (CollectionUtils.isNotEmpty(mensaje.getIdentificadoresFicheros())) {

			identificadores = "<ids>";

			for (String idFichero : mensaje.getIdentificadoresFicheros()) {
				if (StringUtils.isNotBlank(idFichero)) {
					identificadores += "<id>" + idFichero + "</id>";
				}
			}
			identificadores += "</ids>";
		}

		return identificadores;
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
