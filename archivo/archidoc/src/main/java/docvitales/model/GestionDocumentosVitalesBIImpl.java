package docvitales.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessages;

import se.terceros.GestorTercerosFactory;
import se.terceros.InfoTercero;
import se.usuarios.ServiceClient;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.logger.LoggingEvent;

import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.GestionDocumentosVitalesBI;
import common.bi.ServiceBase;
import common.definitions.ArchivoActions;
import common.exceptions.TooManyResultsException;
import common.exceptions.UniqueKeyException;
import common.security.DocumentosVitalesSecurityManager;
import common.util.ArrayUtils;
import common.util.NombresUtils;
import common.util.StringUtils;
import common.vos.ResultadoRegistrosVO;
import common.vos.TypeDescVO;

import docelectronicos.vos.DocDocumentoExtVO;
import docvitales.DocumentosVitalesConstants;
import docvitales.EstadoDocumentoVital;
import docvitales.db.IDocumentoVitalDBEntity;
import docvitales.db.ITipoDocVitProcedimientoDBEntity;
import docvitales.db.ITipoDocumentoVitalDBEntity;
import docvitales.db.IUsoDocumentoVitalDBEntity;
import docvitales.exceptions.DocumentoVitalException;
import docvitales.exceptions.TerceroNoEncontradoException;
import docvitales.exceptions.TipoDocumentoVitalEnUsoException;
import docvitales.exceptions.VinculoYaExistenteException;
import docvitales.vos.BusquedaDocumentosVitalesVO;
import docvitales.vos.DocumentoVitalExtVO;
import docvitales.vos.FormDocumentoVitalVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;
import docvitales.vos.TipoDocumentoVitalVO;
import docvitales.vos.UsoDocumentoVitalVO;
import fondos.db.ISerieDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.vos.DocumentoAntecedenteVO;
import fondos.vos.SerieDocAntVO;

/**
 * Implementación del interfaz de negocio de los documentos vitales.
 */
public class GestionDocumentosVitalesBIImpl extends ServiceBase implements
		GestionDocumentosVitalesBI {
	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(GestionDocumentosVitalesBIImpl.class);

	/** Interfaz para acceso a documentos vitales. */
	private IDocumentoVitalDBEntity documentoVitalDBEntity = null;

	/** Interfaz para acceso a tipos de documentos vitales. */
	private ITipoDocumentoVitalDBEntity tipoDocumentoVitalDBEntity = null;

	/** Interfaz para acceso a tipos de documentos vitales. */
	private ITipoDocVitProcedimientoDBEntity tipoDocVitProcedimientoDBEntity = null;

	/** Interfaz para acceso al uso de documentos vitales. */
	private IUsoDocumentoVitalDBEntity usoDocumentoVitalDBEntity = null;

	/** Interfaz para acceso a unidades documentales. */
	private IUnidadDocumentalDbEntity unidadDocumentalDbEntity = null;

	/** Interfaz para acceso a series. */
	private ISerieDbEntity serieDbEntity = null;

	/**
	 * Constructor.
	 * 
	 * @param tipoDocumentoVitalDBEntity
	 *            Interfaz para acceso a tipos de documentos vitales.
	 */
	public GestionDocumentosVitalesBIImpl(
			IDocumentoVitalDBEntity documentoVitalDBEntity,
			ITipoDocumentoVitalDBEntity tipoDocumentoVitalDBEntity,
			ITipoDocVitProcedimientoDBEntity tipoDocVitProcedimientoDBEntity,
			IUsoDocumentoVitalDBEntity usoDocumentoVitalDBEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDbEntity,
			ISerieDbEntity serieDbEntity) {
		super();

		this.documentoVitalDBEntity = documentoVitalDBEntity;
		this.tipoDocumentoVitalDBEntity = tipoDocumentoVitalDBEntity;
		this.tipoDocVitProcedimientoDBEntity = tipoDocVitProcedimientoDBEntity;
		this.usoDocumentoVitalDBEntity = usoDocumentoVitalDBEntity;
		this.unidadDocumentalDbEntity = unidadDocumentalDbEntity;
		this.serieDbEntity = serieDbEntity;
	}

	// ========================================================================
	// DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Realiza la búsqueda de documentos vitales.
	 * 
	 * @param busquedaVO
	 *            Criterios de búsqueda.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getDocumentosVitales(BusquedaDocumentosVitalesVO busquedaVO)
			throws TooManyResultsException {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);
		return documentoVitalDBEntity.getDocumentosVitales(busquedaVO);
	}

	/**
	 * Obtiene la lista de documentos vitales vigentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idProc
	 *            Identificador de procedimiento. Si es nulo, ignora este
	 *            criterio.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}.
	 */
	public List getDocumentosVitalesVigentes(String idTercero, String idProc) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);
		return documentoVitalDBEntity.getDocumentosVitalesVigentes(idTercero,
				idProc);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionDocumentosVitalesBI#getCountDocumentosVitalesAGestionar
	 * ()
	 */
	public int getCountDocumentosVitalesAGestionar() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		int[] estados = { EstadoDocumentoVital.PENDIENTE_VALIDACION };

		return documentoVitalDBEntity
				.getCountDocumentosVitalesXEstados(estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionDocumentosVitalesBI#getDocumentosVitalesAGestionar()
	 */
	public List getDocumentosVitalesAGestionar() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		try {
			BusquedaDocumentosVitalesVO busquedaVO = new BusquedaDocumentosVitalesVO();
			busquedaVO.setEstados(new String[] { new Integer(
					EstadoDocumentoVital.PENDIENTE_VALIDACION).toString() });

			return documentoVitalDBEntity.getDocumentosVitales(busquedaVO);
		} catch (TooManyResultsException e) {
			// No se va a dar nunca
			return new ArrayList();
		}
	}

	/**
	 * Obtiene el documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @return Documento vital.
	 */
	public DocumentoVitalExtVO getDocumentoVital(String id) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);

		DocumentoVitalExtVO documento = null;

		// Información de un documento vital
		InfoBDocumentoVitalExtVO infoB = documentoVitalDBEntity
				.getDocumentoVital(id);
		if (infoB != null) {
			documento = new DocumentoVitalExtVO(infoB);
			documento.setContenido(getGestionDocumentosElectronicosBI()
					.getFile(getIdRepEcmDocumentosVitales(infoB.getIdRepEcm()),
							null, infoB.getIdFich()));

			// Actualizar el nº de accesos y fecha ult. acceso
			if (infoB.getEstadoDocVit() != EstadoDocumentoVital.PENDIENTE_VALIDACION) {
				iniciarTransaccion();
				documento.setFechaUltAcceso(new Date());
				documento.setNumAccesos(infoB.getNumAccesos() + 1);
				documentoVitalDBEntity.updateDocumentoVital(documento);
				commit();
			}
		}

		return documento;
	}

	/**
	 * Obtiene un documento vital.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 * @param estado
	 *            Estado del documento vital.
	 * @return Documento vital.
	 */
	public InfoBDocumentoVitalExtVO getDocumentoVital(String idTercero,
			String idTipoDocVit, int estado) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);

		return documentoVitalDBEntity.getDocumentoVital(idTercero,
				idTipoDocVit, estado);
	}

	/**
	 * Obtiene la lista de estados de los documentos vitales.
	 * 
	 * @return Lista de estados ({@link TypeDescVO}).
	 */
	public List getEstadosDocumentosVitales() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);

		Locale locale = getServiceClient().getLocale();

		List estados = new ArrayList();
		estados.add(new TypeDescVO(0, Messages.getString(
				DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO + ".0",
				locale)));
		estados.add(new TypeDescVO(1, Messages.getString(
				DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO + ".1",
				locale)));
		estados.add(new TypeDescVO(2, Messages.getString(
				DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO + ".2",
				locale)));
		return estados;
	}

	/**
	 * Inserta un documento vital validado.
	 * 
	 * @param formDocVit
	 *            Información inicial del documento vital.
	 * @return Documento vital insertado y validado.
	 * @throws DocumentoVitalException
	 *             si hay un error en la creación.
	 */
	public InfoBDocumentoVitalExtVO insertDocumentoVitalValidado(
			FormDocumentoVitalVO formDocVit) throws DocumentoVitalException {
		InfoBDocumentoVitalExtVO docVit = null;

		iniciarTransaccion();

		// Comprobar que el tercero no tenga un documento vigente del mismo tipo
		checkDocumentoVitalCreable(formDocVit.getIdTercero(),
				formDocVit.getIdTipoDocVit());

		// Insertar el documento vital
		docVit = insertDocumentoVital(formDocVit);

		// Validar el documento vital
		docVit = validarDocumentoVital(docVit);

		commit();

		return docVit;
	}

	/**
	 * Inserta un documento vital.
	 * 
	 * @param formDocVit
	 *            Información inicial del documento vital.
	 * @return Documento vital insertado.
	 */
	public InfoBDocumentoVitalExtVO insertDocumentoVital(
			FormDocumentoVitalVO formDocVit) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		InfoBDocumentoVitalExtVO documentoVital = null;

		if (formDocVit != null) {
			InfoTercero tercero = null;
			try {

				// Obtener información de la entidad
				ServiceClient serviceClient = getServiceClient();

				// Obtener la entidad para el usuario conectado
				Properties params = null;

				if ((serviceClient != null)
						&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
					params = new Properties();
					params.put(MultiEntityConstants.ENTITY_PARAM,
							serviceClient.getEntity());
				}

				tercero = GestorTercerosFactory.getConnector(params)
						.recuperarTercero(formDocVit.getIdTercero());
			} catch (Exception e) {
				logger.error(
						"Error al recuperar un tercero (id: "
								+ formDocVit.getIdTercero() + ")", e);
			}

			if (tercero == null)
				throw new TerceroNoEncontradoException();

			documentoVital = new InfoBDocumentoVitalExtVO();
			documentoVital.setIdBdTerceros(formDocVit.getIdTercero());
			documentoVital.setNumIdentidad(tercero.getIdentificacion());
			documentoVital.setIdentidad(NombresUtils.getNombreCompleto(
					tercero.getNombre(), tercero.getPrimerApellido(),
					tercero.getSegundoApellido()));
			documentoVital.setIdTipoDocVit(formDocVit.getIdTipoDocVit());
			documentoVital.setFechaCad(formDocVit.getFechaCaducidad());
			documentoVital
					.setEstadoDocVit(EstadoDocumentoVital.PENDIENTE_VALIDACION);
			documentoVital.setFechaCr(new Date());
			documentoVital.setIdUsuarioCr(getServiceClient().getId());
			documentoVital.setTamanoFich(formDocVit.getContenidoFich().length);
			documentoVital.setNombreOrgFich(formDocVit.getNombreFich());
			documentoVital.setExtFich(formDocVit.getExtFich());
			documentoVital.setIdFich("0");
			documentoVital.setObservaciones(formDocVit.getObservaciones());

			// Auditoría
			LoggingEvent event = AuditoriaDocumentosVitales.getLogginEvent(
					this, ArchivoActions.DOCUMENTOS_VITALES_MODULE_ALTA);

			iniciarTransaccion();

			// Insertar el documento vital
			documentoVitalDBEntity.insertDocumentoVital(documentoVital);

			DocDocumentoExtVO docDocumentoExtVO = new DocDocumentoExtVO();
			docDocumentoExtVO.setContenido(formDocVit.getContenidoFich());
			docDocumentoExtVO.setNombre(formDocVit.getNombreFich());
			docDocumentoExtVO.setIdRepEcm(getIdRepEcmDocumentosVitales(null));
			docDocumentoExtVO.setExtFich(formDocVit.getExtFich());

			String idFichero = getGestionDocumentosElectronicosBI().storeFile(
					docDocumentoExtVO);

			documentoVital.setIdFich(idFichero);

			// Modificar identificador de fichero del documento vital
			documentoVitalDBEntity.updateIdFichDocumentoVital(
					documentoVital.getId(), documentoVital.getIdFich());

			// Auditoría
			Locale locale = getServiceClient().getLocale();
			AuditoriaDocumentosVitales.auditaInsercionDocumentoVital(locale,
					event, documentoVital);

			// Obtener la información actualizada del documentoVital
			documentoVital = getDocumentoVital(documentoVital.getId());

			commit();
		}

		return documentoVital;
	}

	/**
	 * Valida un documento vital.
	 * 
	 * @param documentoVital
	 *            Documento vital.
	 * @return Documento vital validado.
	 */
	public InfoBDocumentoVitalExtVO validarDocumentoVital(
			InfoBDocumentoVitalExtVO documentoVital) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		if ((documentoVital != null)
				&& (documentoVital.getEstadoDocVit() == EstadoDocumentoVital.PENDIENTE_VALIDACION)) {
			iniciarTransaccion();

			// Pasa a históricos los documentos vigentes anteriores
			documentoVitalDBEntity.pasaAHistoricoDocsVigentes(documentoVital
					.getIdBdTerceros());

			// Actualizar valores del documento vital
			documentoVital.setEstadoDocVit(EstadoDocumentoVital.VIGENTE);
			documentoVital.setIdUsuarioVig(getServiceClient().getId());
			documentoVital.setFechaVig(new Date());
			documentoVitalDBEntity.updateDocumentoVital(documentoVital);

			// Auditoría
			Locale locale = getServiceClient().getLocale();
			AuditoriaDocumentosVitales.auditaValidacionDocumentoVital(locale,
					this, documentoVital);

			commit();
		}

		return documentoVital;
	}

	/**
	 * Rechaza un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 */
	public void rechazarDocumentoVital(String id) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		iniciarTransaccion();

		// Obtener el documento vital
		InfoBDocumentoVitalExtVO documentoVital = getDocumentoVital(id);

		if ((documentoVital != null)
				&& (documentoVital.getEstadoDocVit() == EstadoDocumentoVital.PENDIENTE_VALIDACION)) {
			// Auditoría
			Locale locale = getServiceClient().getLocale();
			AuditoriaDocumentosVitales.auditaRechazoDocumentoVital(locale,
					this, documentoVital);

			// Eliminar el documento vital
			documentoVitalDBEntity.deleteDocumentoVital(id);

			try {
				// Eliminar el fichero del documento vital
				getGestionDocumentosElectronicosBI()
						.deleteFile(
								getIdRepEcmDocumentosVitales(documentoVital
										.getIdRepEcm()),
								null, documentoVital.getIdFich());
			} catch (Exception e) {
				logger.warn("Error al eliminar el fichero [id="
						+ documentoVital.getIdFich() + "] del documento vital",
						e);
			}
		}

		commit();
	}

	/**
	 * Pasa un documento vital a histórico.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 */
	public void pasarAHistoricoDocumentoVital(String id) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		// Obtener el documento vital
		InfoBDocumentoVitalExtVO documentoVital = getDocumentoVital(id);

		if ((documentoVital != null)
				&& (documentoVital.getEstadoDocVit() == EstadoDocumentoVital.VIGENTE)) {
			iniciarTransaccion();

			// Actualizar valores del documento vital
			documentoVital.setEstadoDocVit(EstadoDocumentoVital.HISTORICO);
			documentoVitalDBEntity.updateDocumentoVital(documentoVital);

			// Auditoría
			Locale locale = getServiceClient().getLocale();
			AuditoriaDocumentosVitales.auditaPasoAHistoricoDocumentoVital(
					locale, this, documentoVital);

			commit();
		}
	}

	// ========================================================================
	// TIPOS DE DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Obtiene la lista de tipos de documentos vitales.
	 * 
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);
		return tipoDocumentoVitalDBEntity.getTiposDocumentosVitales();
	}

	/**
	 * Obtiene la lista de tipos de documentos vitales de un procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales(String idProc) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);
		return tipoDocumentoVitalDBEntity.getTiposDocumentosVitales(idProc);
	}

	/**
	 * Obtiene el tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO getTipoDocumentoVital(String id) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.CONSULTA_DOCUMENTOS_VITALES_ACTION);
		return tipoDocumentoVitalDBEntity.getTipoDocumentoVital(id);
	}

	/**
	 * Inserta un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO insertTipoDocumentoVital(
			TipoDocumentoVitalVO tipo) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		// Auditoría
		LoggingEvent event = AuditoriaDocumentosVitales.getLogginEvent(this,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_ALTA_TIPO);

		iniciarTransaccion();

		// Alta del tipo de documento vital
		tipo = tipoDocumentoVitalDBEntity.insertTipoDocumentoVital(tipo);

		// Detalle de la auditoría
		Locale locale = getServiceClient().getLocale();
		AuditoriaDocumentosVitales.auditaAltaTipoDocumentoVital(locale, event,
				tipo);

		commit();

		return tipo;
	}

	/**
	 * Modifica un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 */
	public void updateTipoDocumentoVital(TipoDocumentoVitalVO tipo) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		if (tipo != null) {
			iniciarTransaccion();

			// Auditoría
			Locale locale = getServiceClient().getLocale();
			AuditoriaDocumentosVitales.auditaModificacionTipoDocumentoVital(
					locale, this, tipo, getTipoDocumentoVital(tipo.getId()));

			// Modificación del tipo de documento vital
			tipoDocumentoVitalDBEntity.updateTipoDocumentoVital(tipo);

			commit();
		}
	}

	/**
	 * Elimina un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 * @throws TipoDocumentoVitalEnUsoException
	 *             si el tipo está en uso.
	 */
	private void deleteTipoDocumentoVital(TipoDocumentoVitalVO tipo)
			throws TipoDocumentoVitalEnUsoException {
		if (tipo != null) {
			// Comprobar si está en uso
			if (documentoVitalDBEntity.getCountDocumentosVitalesByTipo(tipo
					.getId()) == 0) {
				iniciarTransaccion();

				// Auditoría
				Locale locale = getServiceClient().getLocale();
				AuditoriaDocumentosVitales.auditaEliminacionTipoDocumentoVital(
						locale, this, tipo);

				// Eliminar las relaciones del tipo de documento vital con
				// procedimientos
				tipoDocVitProcedimientoDBEntity.deleteByIdTipoDocVit(tipo
						.getId());

				// Eliminación del tipo de documento vital
				tipoDocumentoVitalDBEntity.deleteTipoDocumentoVital(tipo
						.getId());

				commit();
			} else
				throw new TipoDocumentoVitalEnUsoException();
		}
	}

	/**
	 * Elimina un tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @throws TipoDocumentoVitalEnUsoException
	 *             si el tipo está en uso.
	 */
	public void deleteTipoDocumentoVital(String id)
			throws TipoDocumentoVitalEnUsoException {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		// Elimina el tipo de documento vital
		deleteTipoDocumentoVital(getTipoDocumentoVital(id));
	}

	/**
	 * Elimina tipos de documentos vitales.
	 * 
	 * @param ids
	 *            Lista de identificadores de tipos de documentos vitales.
	 * @return Información de la eliminación.
	 */
	public ResultadoRegistrosVO deleteTiposDocumentosVitales(String[] ids) {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);

		ResultadoRegistrosVO res = new ResultadoRegistrosVO();

		if (!ArrayUtils.isEmpty(ids)) {
			res.setNumRegistros(ids.length);

			for (int i = 0; i < ids.length; i++) {
				TipoDocumentoVitalVO tipo = getTipoDocumentoVital(ids[i]);

				try {
					// Eliminar el tipo de documento vital
					deleteTipoDocumentoVital(tipo);

					res.setNumRegistrosTratados(res.getNumRegistrosTratados() + 1);
				} catch (TipoDocumentoVitalEnUsoException e) {
					res.getErrores()
							.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionError(
											DocumentosVitalesConstants.ERRORS_DOCVITALES_DELETE_TIPODOCVITAL_EN_USO,
											new Object[] { tipo.getNombre() }));
				}
			}
		}

		return res;
	}

	// ========================================================================
	// RELACIONES ENTRE TIPOS DE DOCUMENTOS VITALES Y PROCEDIMIENTOS
	// ========================================================================

	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void insertTipoDocVitProc(String idProc, String idTipoDocVit) {
		try {
			tipoDocVitProcedimientoDBEntity.insert(idProc, idTipoDocVit);
		} catch (UniqueKeyException e) {
			// No hacer nada
		}
	}

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteTipoDocVit(String idProc, String idTipoDocVit) {
		tipoDocVitProcedimientoDBEntity.delete(idProc, idTipoDocVit);
	}

	/**
	 * Elimina todas las relaciones de un tipo de documento vital con
	 * procedientos.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteTipoDocVitByIdTipoDocVit(String idTipoDocVit) {
		tipoDocVitProcedimientoDBEntity.deleteByIdTipoDocVit(idTipoDocVit);
	}

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void deleteTiposDocVitByIdProc(String idProc) {
		tipoDocVitProcedimientoDBEntity.deleteByIdProc(idProc);
	}

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void deleteTiposDocVit(String idProc, String[] idsTipoDocVit) {
		tipoDocVitProcedimientoDBEntity.delete(idProc, idsTipoDocVit);
	}

	// ========================================================================
	// USO DE DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Vincula un expediente a un documento vital.
	 * 
	 * @param idDocVit
	 *            Identificador del documento vital.
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 * @param usuario
	 *            Nombre completo del usuario de backoffice.
	 */
	public void vinculaExpediente(String idDocVit, String idExp, String idSist,
			String usuario) {
		if (usoDocumentoVitalDBEntity.existeUso(idDocVit, idExp, idSist))
			throw new VinculoYaExistenteException();

		UsoDocumentoVitalVO uso = new UsoDocumentoVitalVO();
		uso.setIdDocVit(idDocVit);
		uso.setIdExp(idExp);
		uso.setIdSist(idSist);
		uso.setUsuario(usuario);
		uso.setFechaUso(new Date());

		usoDocumentoVitalDBEntity.insertUsoDocumentoVital(uso);
	}

	/**
	 * Elimina los vínculos de un expediente.
	 * 
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 */
	public void eliminaVinculosExpediente(String idExp, String idSist) {
		usoDocumentoVitalDBEntity.deleteUsos(idExp, idSist);
	}

	// ========================================================================
	// DOCUMENTOS ANTECEDENTES
	// ========================================================================

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero) {
		return getDocumentosAntecedentes(idTercero, null);
	}

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero, String idSerie) {
		List documentos = unidadDocumentalDbEntity.getDocumentosAntecedentes(
				idTercero, idSerie);
		Locale locale = getServiceClient().getLocale();

		// Incluir la descripción del estado
		if (documentos != null) {
			DocumentoAntecedenteVO doc;
			for (int i = 0; i < documentos.size(); i++) {
				doc = (DocumentoAntecedenteVO) documentos.get(i);
				doc.setDescEstado(Messages.getString(
						Constants.ETIQUETA_ESTADO_ELEMENTO_CF + "."
								+ doc.getEstado(), locale));
			}
		}

		return documentos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionDocumentosVitalesBI#checkDocumentoVitalCreable(java.
	 * lang.String, java.lang.String)
	 */
	public void checkDocumentoVitalCreable(String idBdTerceros,
			String idTipoDocVit) throws DocumentoVitalException {
		InfoBDocumentoVitalExtVO documentoVigente = getDocumentoVital(
				idBdTerceros, idTipoDocVit, EstadoDocumentoVital.VIGENTE);
		if (documentoVigente != null)
			throw new DocumentoVitalException(
					DocumentoVitalException.EXISTE_DOCUMENTO_VITAL_VIGENTE);
	}

	public InfoBDocumentoVitalExtVO insertDocumentoVitalXAdministrador(
			FormDocumentoVitalVO formDocVit) throws DocumentoVitalException {

		checkDocumentoVitalCreable(formDocVit.getIdTercero(),
				formDocVit.getIdTercero());

		iniciarTransaccion();

		InfoBDocumentoVitalExtVO infoDoc = insertDocumentoVital(formDocVit);
		validarDocumentoVital(infoDoc);

		commit();

		return getDocumentoVital(infoDoc.getId());

	}

	/**
	 * Obtiene las series que contienen documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series ({@link SerieDocAntVO}).
	 */
	public List getSeriesDocumentosAntecedentes(String idTercero) {
		List series = serieDbEntity.getSeriesDocumentosAntecedentes(idTercero);
		Locale locale = getServiceClient().getLocale();

		// Incluir la descripción del estado
		if (series != null) {
			SerieDocAntVO serie;
			for (int i = 0; i < series.size(); i++) {
				serie = (SerieDocAntVO) series.get(i);
				serie.setDescEstado(Messages.getString(
						Constants.ETIQUETA_ESTADO_ELEMENTO_CF + "."
								+ serie.getEstado(), locale));
			}
		}

		return series;
	}

	/**
	 * Comprueba la validez de los documentos vitales vigentes y los pasa a
	 * histórico si han caducado.
	 */
	public void pasarAHistoricoDocumentosVitalesCaducados() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);
		Locale locale = getServiceClient().getLocale();

		// Paso a histórico de documentos vigentes que han caducado
		List docsCaducados = documentoVitalDBEntity
				.getDocumentosVigentesCaducados();
		InfoBDocumentoVitalExtVO docVit;
		for (int i = 0; i < docsCaducados.size(); i++) {
			docVit = (InfoBDocumentoVitalExtVO) docsCaducados.get(i);

			iniciarTransaccion();

			// Cambio de estado del documento vital a histórico
			documentoVitalDBEntity.updateEstadoDocumentoVital(docVit.getId(),
					EstadoDocumentoVital.HISTORICO);

			// Auditoría
			AuditoriaDocumentosVitales.auditaPasoAHistoricoDocumentoVital(
					locale, this, docVit);

			if (logger.isDebugEnabled())
				logger.debug("Paso a histórico por caducidad del documento vital con id: "
						+ docVit.getId());

			commit();
		}
	}

	/**
	 * Elimina los documentos vitales históricos que no están referenciados en
	 * ningún expediente.
	 */
	public void eliminarDocumentosVitalesObsoletos() {
		// Comprobar permisos
		checkPermission(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION);
		Locale locale = getServiceClient().getLocale();

		// Eliminar documentos caducados que no se están usando
		List docsEliminables = documentoVitalDBEntity
				.getDocumentosEliminables();
		InfoBDocumentoVitalExtVO docVit;
		for (int i = 0; i < docsEliminables.size(); i++) {
			docVit = (InfoBDocumentoVitalExtVO) docsEliminables.get(i);

			try {
				iniciarTransaccion();

				// Eliminar la información del documento vital
				documentoVitalDBEntity.deleteDocumentoVital(docVit.getId());

				// Auditoría
				AuditoriaDocumentosVitales.auditaEliminacionDocumentoVital(
						locale, this, docVit);

				// Eliminar el fichero del documento vital
				getGestionDocumentosElectronicosBI().deleteFile(
						getIdRepEcmDocumentosVitales(docVit.getIdRepEcm()),
						null, docVit.getIdFich());

				if (logger.isDebugEnabled())
					logger.debug("Eliminación del documento vital obsoleto con id: "
							+ docVit.getId());

				commit();
			} catch (Throwable t) {
				logger.error("Error al eliminar el documento vital con id "
						+ docVit.getId());
				rollback();
			}
		}
	}

	private String getIdRepEcmDocumentosVitales(String idRepEcm) {

		if (StringUtils.isBlank(idRepEcm)) {
			idRepEcm = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDocumentosVitales().getIdRepEcm();

			if (StringUtils.isEmpty(idRepEcm)) {
				logger.error("No se ha definido el idRepEcm, en el archivo-cfg.xml. Ej."
						+ "<Configuracion_Documentos_Vitales>"
						+ "<Id_Repositorio_ECM>1</Id_Lista_Volumenes>"
						+ "</Configuracion_Documentos_Vitales>");
			}
		}
		return idRepEcm;

	}
}