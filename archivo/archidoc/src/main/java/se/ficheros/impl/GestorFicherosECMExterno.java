package se.ficheros.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.IGestorFicheros;
import se.ficheros.MetaDatosUtils;
import se.repositoriosECM.EcmSprinUtils;

import common.MultiEntityConstants;
import common.util.DateUtils;

import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.IRepositorioEcmVO;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;
import es.ieci.tecdoc.fwktd.util.mime.MimeUtil;

/**
 * Gestor de fichero generico. Implementación por defecto
 *
 * @author IECISA
 *
 */
public class GestorFicherosECMExterno implements IGestorFicheros {

	protected static final Logger logger = Logger
			.getLogger(GestorFicherosECMExterno.class);

	private GestionDocumentalServiceFactory gestionDocumentalServiceFactory;

	private IRepositorioEcmVO repositorioEcmVO = null;

	private Properties params;

	public GestorFicherosECMExterno(IRepositorioEcmVO repositorioEcmVO) {
		this.repositorioEcmVO = repositorioEcmVO;
	}

	public void initialize(Properties params) {
		this.params = params;
	}

	public void establecerEntidad(){
		if(params != null){
			String entidad = (String)params.get(MultiEntityConstants.ENTITY_PARAM);
			MultiEntityContextHolder.setEntity(entidad);
		}
	}


	public byte[] retrieveFile(String idDocumento)
			throws GestionDocumentalException {

		establecerEntidad();

		GestionDocumentalService gdService = getGestionDocumentalServiceFactory()
				.getService(repositorioEcmVO.getId());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			gdService.createSesion();
		} catch (GestionDocumentalException e) {
			logger.error("Error al crear la sesion en el ECM", e);
			throw new GestionDocumentalException(
					"Error al crear la sesion en el ECM");
		}

		try {
			gdService.retrieveDocumento(idDocumento, out);
		} catch (GestionDocumentalException e) {
			logger.error("Error al obtener el documento en el ECM", e);
			throw new GestionDocumentalException(
					"Error al obtener el documento en el ECM");
		} finally {
			try {
				gdService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la sesion en el ECM", e);
			}
		}
		return out.toByteArray();
	}

	public String storeFile(DocDocumentoExtVO docDocumentoExtVO)
			throws GestionDocumentalException {

		establecerEntidad();
		InfoDocumentoVO documento = getInfoDocumentoVO(docDocumentoExtVO);

		GestionDocumentalService gdService = getGestionDocumentalServiceFactory()
				.getService(repositorioEcmVO.getId());
		ByteArrayInputStream in = new ByteArrayInputStream(
				docDocumentoExtVO.getContenido());
		documento.setTipoMime(MimeUtil.getMimeType(in));

		try {
			gdService.createSesion();
		} catch (GestionDocumentalException e) {
			logger.error("Error al crear la sesion en el ECM", e);
			throw new GestionDocumentalException(
					"Error al crear la sesion en el ECM");
		}

		try {
			documento = gdService.createDocumento(documento, in);
		} catch (GestionDocumentalException e) {
			logger.error("Error al crear el documento en el ECM", e);
			throw new GestionDocumentalException(
					"Error al crear el documento en el ECM");
		} finally {
			try {
				gdService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la sesion en el ECM", e);
			}
		}

		return documento.getId();
	}

	public void deleteFile(String idDocumento)
			throws GestionDocumentalException {

		establecerEntidad();
		GestionDocumentalService gdService = getGestionDocumentalServiceFactory()
				.getService(repositorioEcmVO.getId());
		try {
			gdService.createSesion();
		} catch (GestionDocumentalException e) {
			logger.error("Error al crear la sesion en el ECM", e);
			throw new GestionDocumentalException(
					"Error al crear la sesion en el ECM");
		}

		try {
			gdService.deleteDocumento(idDocumento);
		} catch (GestionDocumentalException e) {
			logger.error("Error al eliminar el documento en el ECM", e);
			throw new GestionDocumentalException(
					"Error al eliminar el documento en el ECM");
		} finally {
			try {
				gdService.releaseSesion();
			} catch (GestionDocumentalException e) {
				logger.error("Error al finalizar la sesion en el ECM", e);
			}
		}
	}

	public List<String> findFileByContent(String searchString, List docsIds)
			throws GestionDocumentalException {
		establecerEntidad();
		logger.error("Funcionalidad no implementada");
		return null;
	}

	public GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
		if (gestionDocumentalServiceFactory == null) {
			gestionDocumentalServiceFactory = EcmSprinUtils
					.getGestionDocumentalServiceFactory();
		}

		return gestionDocumentalServiceFactory;
	}

	private InfoDocumentoVO getInfoDocumentoVO(
			DocDocumentoExtVO docDocumentoExtVO) {
		InfoDocumentoVO infoDocumentoVO = new InfoDocumentoVO();

		Date fecha = DateUtils.getFechaActual();

		infoDocumentoVO.setFechaCreacion(fecha);
		// infoDocumentoVO.setFechaModificacion();
		infoDocumentoVO.setNombre(docDocumentoExtVO.getFileName());

		List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();

		metadatos.add(MetaDatosUtils.getMetaDatoId(docDocumentoExtVO.getId()));
		metadatos.add(MetaDatosUtils.getMetaDatoNombre(docDocumentoExtVO
				.getNombreOrgFich()));
		metadatos.add(MetaDatosUtils.getMetaDatoFecha(infoDocumentoVO
				.getFechaCreacion()));
		metadatos.add(MetaDatosUtils.getMetaDatoDescripcion(docDocumentoExtVO
				.getDescripcion()));
		metadatos.add(MetaDatosUtils.getMetaDatoTitulo(docDocumentoExtVO
				.getNombre()));

		infoDocumentoVO.setMetadatos(metadatos);

		return infoDocumentoVO;
	}

}
