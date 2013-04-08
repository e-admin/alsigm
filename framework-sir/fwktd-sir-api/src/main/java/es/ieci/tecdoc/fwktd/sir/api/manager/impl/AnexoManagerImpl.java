package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao;
import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.HashManager;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;

/**
 * Implementación del manager de anexos de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AnexoManagerImpl extends BaseManagerImpl<AnexoVO, String>
		implements AnexoManager {
	
	private static final String CONTENT_TYPE_ID_PARAM_NAME = "anexos.contentTypeId";
	private static final String DEFAULT_CONTENT_TYPE_ID = "fwktdsir";

	/**
	 * Gestor de códigos hash.
	 */
	private HashManager hashManager = null;

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;
	
	/**
	 * Factoría de servicios de gestión documental.
	 */
	private GestionDocumentalServiceFactory gestionDocumentalServiceFactory = null;

	/**
	 * Identificador del tipo de contenido en el módulo de gestión documental.
	 */
	private String defaultContentTypeId = DEFAULT_CONTENT_TYPE_ID;

	/**
	 * Constructor.
	 *
	 * @param aGenericDao
	 */
	public AnexoManagerImpl(BaseDao<AnexoVO, String> aGenericDao) {
		super(aGenericDao);
	}

	public HashManager getHashManager() {
		return hashManager;
	}

	public void setHashManager(HashManager hashManager) {
		this.hashManager = hashManager;
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	public GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
		return gestionDocumentalServiceFactory;
	}

	public void setGestionDocumentalServiceFactory(
			GestionDocumentalServiceFactory gestionDocumentalServiceFactory) {
		this.gestionDocumentalServiceFactory = gestionDocumentalServiceFactory;
	}

	public String getDefaultContentTypeId() {
		return defaultContentTypeId;
	}

	public void setDefaultContentTypeId(String defaultContentTypeId) {
		this.defaultContentTypeId = defaultContentTypeId;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#saveAnexo(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO)
	 */
	public AnexoVO saveAnexo(String idAsientoRegistral, AnexoFormVO anexoForm) {

        AnexoVO anexo = new AnexoVO();
        BeanUtils.copyProperties(anexoForm, anexo);
        anexo.setIdAsientoRegistral(idAsientoRegistral);

        // Guardar la información del anexo
        anexo = save(anexo);

        // Guardar el contenido
        if (ArrayUtils.isNotEmpty(anexoForm.getContenido())) {
        	setContenidoAnexo(anexo, anexoForm.getContenido());
        }

        return anexo;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#getContenidoAnexo(java.lang.String)
	 */
	public byte[] getContenidoAnexo(String id) {

		Assert.hasText(id, "'id' must not be empty");

		logger.info("Obteniendo el contenido del anexo [{}]", id);

		//return ((AnexoDao)getDao()).getContenidoAnexo(id);

		// UID del anexo en el gestor documental
		String uidGestorDocumental = ((AnexoDao)getDao()).getUIDGestorDocumental(id);
		if (StringUtils.isBlank(uidGestorDocumental)) {
			logger.warn("El anexo [{}] no tiene documento asociado en el gestor documental", id);
			return null;
		}

		GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

		try {

			gestionDocumentalService.createSesion();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Obtener el contenido en el gestor documental
			gestionDocumentalService.retrieveDocumento(uidGestorDocumental, baos);

			return baos.toByteArray();

		} catch (GestionDocumentalException e) {
			logger.error("Error al obtener el contenido del documento en el gestor documental", e);
			throw new SIRException("error.sir.dm.obtenerContenidoAnexo", new String[] { e.getMessage() },
					"Error al obtener el contenido del documento en el gestor documental");
		} finally {
			try {
				gestionDocumentalService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la conexión con el gestor documental", e);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#getInfoContenidoAnexo(java.lang.String)
	 */
	public InfoDocumentoVO getInfoContenidoAnexo(String id) {
		
		Assert.hasText(id, "'id' must not be empty");

		logger.info("Obteniendo la información del contenido del anexo [{}]", id);

		// UID del anexo en el gestor documental
		String uidGestorDocumental = ((AnexoDao)getDao()).getUIDGestorDocumental(id);
		if (StringUtils.isBlank(uidGestorDocumental)) {
			logger.warn("El anexo [{}] no tiene documento asociado en el gestor documental", id);
			return null;
		}

		GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

		try {

			gestionDocumentalService.createSesion();

			// Obtener la información del contenido en el gestor documental
			return gestionDocumentalService.getInfoDocumento(uidGestorDocumental);

		} catch (GestionDocumentalException e) {
			logger.error("Error al obtener la información del documento en el gestor documental", e);
			throw new SIRException("error.sir.dm.obtenerInfoContenidoAnexo", new String[] { e.getMessage() },
					"Error al obtener la información del documento en el gestor documental");
		} finally {
			try {
				gestionDocumentalService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la conexión con el gestor documental", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#setContenidoAnexo(java.lang.String, byte[])
	 */
	public void setContenidoAnexo(String id, byte[] contenido) {

		Assert.hasText(id, "'id' must not be empty");

		logger.info("Actualizando el contenido del anexo [{}]", id);

		// Información del anexo
		AnexoVO anexo = get(id);

		// Establecer el contenido del anexo
		setContenidoAnexo(anexo, contenido);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#setContenidoAnexo(es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO, byte[])
	 */
	public void setContenidoAnexo(AnexoVO anexo, byte[] contenido) {

		Assert.notNull(anexo, "'anexo' must not be null");

		logger.info("Actualizando el contenido del anexo [{}]", anexo.getId());

		//((AnexoDao)getDao()).setContenidoAnexo(id, contenido, hash);

		// Información del documento en el gestor documental
		InfoDocumentoVO infoDocumento = null;

		// Comprobar si ya existe un documento o si hay que crear uno nuevo
		String uidGestorDocumental = ((AnexoDao)getDao()).getUIDGestorDocumental(anexo.getId());

		// Servicio de gestión documental
		GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

		try {

			gestionDocumentalService.createSesion();

			// Información del documento
			infoDocumento = getInfoDocumentoVO(uidGestorDocumental, anexo);

			if (StringUtils.isBlank(uidGestorDocumental)) {

				// Crear el documento en el gestor documental
				infoDocumento = gestionDocumentalService.createDocumento(infoDocumento, new ByteArrayInputStream(contenido));

			} else {

				// Actualizar el documento en el gestor documental
				infoDocumento = gestionDocumentalService.updateDocumento(infoDocumento, new ByteArrayInputStream(contenido));
			}

		} catch (GestionDocumentalException e) {
			logger.error("Error al guardar el contenido del documento en el gestor documental", e);
			throw new SIRException("error.sir.dm.guardarContenidoAnexo", null,
					"Error al guardar el contenido del documento en el gestor documental");
		} finally {
			try {
				gestionDocumentalService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la conexión con el gestor documental", e);
			}
		}

		// Actualizar la información del anexo
		if (infoDocumento != null) {

			anexo.setTipoMIME(infoDocumento.getTipoMime());
			
			// Comprobar si el hash está informado
			if (anexo.getHash() == null) {
				anexo.setHash(getHashManager().generarHash(contenido));
			}

			update(anexo);

			if (StringUtils.isNotBlank(infoDocumento.getId())) {
				((AnexoDao)getDao()).updateUIDGestorDocumental(anexo.getId(), infoDocumento.getId());
			}
		}
	}

	protected InfoDocumentoVO getInfoDocumentoVO(String uidGestorDocumental, AnexoVO anexo) {

		InfoDocumentoVO infoDocumento = new InfoDocumentoVO();

		infoDocumento.setId(uidGestorDocumental);
		infoDocumento.setNombre(anexo.getNombreFichero());
		infoDocumento.setTipoMime(anexo.getTipoMIME());

		List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();
		metadatos.add(new MetadatoVO("nombreFichero", anexo.getNombreFichero()));
		metadatos.add(new MetadatoVO("hash", anexo.getHash() != null ? Base64.encodeBase64String(anexo.getHash()) : null));
		metadatos.add(new MetadatoVO("tipoDocumento", anexo.getTipoDocumento() != null ? anexo.getTipoDocumento().getValue() : null));
		metadatos.add(new MetadatoVO("validezDocumento", anexo.getValidezDocumento() != null ? anexo.getValidezDocumento().getValue() : null));
		metadatos.add(new MetadatoVO("timestamp", anexo.getTimestamp() != null ? Base64.encodeBase64String(anexo.getTimestamp()) : null));
		metadatos.add(new MetadatoVO("firma", anexo.getFirma() != null ? Base64.encodeBase64String(anexo.getFirma()) : null));
		metadatos.add(new MetadatoVO("certificado", anexo.getCertificado() != null ? Base64.encodeBase64String(anexo.getCertificado()) : null));
		metadatos.add(new MetadatoVO("validacionOCSPCertificado", anexo.getValidacionOCSPCertificado() != null ? Base64.encodeBase64String(anexo.getValidacionOCSPCertificado()) : null));
		metadatos.add(new MetadatoVO("observaciones", anexo.getObservaciones()));

		infoDocumento.setMetadatos(metadatos);

		return infoDocumento;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl#delete(java.io.Serializable)
	 */
	@Override
	public void delete(String id) {

		// Obtener el UID en el gestor documental
		String uidGestorDocumental = ((AnexoDao)getDao()).getUIDGestorDocumental(id);

		// Eliminar la información del anexo
		((AnexoDao)getDao()).delete(id);

		// Eliminar el documento en el gestor documental
		if (StringUtils.isNotBlank(uidGestorDocumental)) {

			// Servicio de gestión documental
			GestionDocumentalService gestionDocumentalService = getGestionDocumentalServiceFactory().getService(getContentTypeId());

			try {

				// Iniciar sesión con el gestor documental
				gestionDocumentalService.createSesion();

				// Eliminar el contenido
				gestionDocumentalService.deleteDocumento(uidGestorDocumental);

			} catch (GestionDocumentalException e) {
				logger.error("Error al eliminar el contenido del documento en el gestor documental", e);
				throw new SIRException("error.sir.dm.eliminarContenidoAnexo", null,
						"Error al eliminar el contenido del documento en el gestor documental");
			} finally {
				try {
					gestionDocumentalService.releaseSesion();
				} catch (GestionDocumentalException e) {
					logger.error("Error al finalizar la conexión con el gestor documental", e);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager#deleteByIdAsientoRegistral(java.lang.String)
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral) {

		Assert.hasText(idAsientoRegistral, "'idAsientoRegistral' must not be empty");

		logger.info("Eliminando anexos del asiento registral con identificador [{}]", idAsientoRegistral);

		// Obtener los identificadores de los anexos del asiento registral
		List<String> ids = ((AnexoDao)getDao()).getIdsByIdAsientoRegistral(idAsientoRegistral);
		if (ids != null) {
			for (String id : ids) {

				// Eliminar el anexo
				delete(id);
			}
		}
	}
	
	protected String getContentTypeId() {
		
		String contentTypeId = null;
		
		if (getConfiguracionManager() != null) {
			
			// Obtener el nombre del repositorio documental a partir de la
			// configuración en base de
			// datos
			contentTypeId = getConfiguracionManager().getValorConfiguracion(
					CONTENT_TYPE_ID_PARAM_NAME);
		}

		if (StringUtils.isBlank(contentTypeId)) {
			contentTypeId = getDefaultContentTypeId();
		}

		return contentTypeId;
	}
}
