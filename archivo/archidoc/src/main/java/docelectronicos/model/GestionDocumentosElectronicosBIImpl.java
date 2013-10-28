package docelectronicos.model;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbError;
import ieci.core.exception.IeciTdException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import se.ficheros.GestorFicherosFactory;
import se.ficheros.IGestorFicheros;
import se.repositorios.GestorRepositorioFactory;
import se.repositorios.IGestorRepositorio;
import se.repositorios.archigest.InfoFicheroVO;
import se.repositoriosECM.GestorRepositoriosECMFactory;
import se.repositoriosECM.IGestorRepositoriosECM;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.AppUserRIFactory;
import se.usuarios.ServiceClient;
import se.usuarios.exceptions.AppUserException;
import transferencias.vos.DocumentoElectronicoVO;
import transferencias.vos.TransferenciaElectronicaInfo;
import util.CollectionUtils;
import util.DefaultTreeModel;
import util.TreeModel;
import util.TreeModelItem;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.security.ActionObject;
import common.security.DocumentosElectronicosSecurityManager;
import common.security.SecurityManagerBase;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.view.POUtils;
import common.vos.Restringible;
import common.vos.TypeDescVO;

import deposito.vos.DepositoElectronicoVO;
import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.ICampoDatoDBEntity;
import descripcion.db.ICampoTablaDBEntity;
import descripcion.db.ICatalogoListaDescriptoresDBEntity;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.model.TipoCampo;
import descripcion.model.TipoDescriptor;
import descripcion.model.documentos.CargarCamposDocumento;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import docelectronicos.DocumentosConstants;
import docelectronicos.EstadoClasificador;
import docelectronicos.EstadoDocumento;
import docelectronicos.EstadoTareaCaptura;
import docelectronicos.MarcasClasificador;
import docelectronicos.TipoObjeto;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.db.IDocDocumentoDescrDBEntity;
import docelectronicos.db.IDocFichaDBEntity;
import docelectronicos.db.IDocTCapturaDBEntity;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.exceptions.DocElectronicosSecurityException;
import docelectronicos.exceptions.FileException;
import docelectronicos.model.helpers.ClasificadoresHelper;
import docelectronicos.vos.BusquedaTareasVO;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;
import docelectronicos.vos.DocumentosTreeModelItemVO;
import docelectronicos.vos.FichaClfVO;
import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.InfoBFichaClfVO;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.model.TipoAcceso;
import gcontrol.vos.UsuarioVO;

/**
 * Implementación del interfaz de negocio de los documentos electrónicos.
 */
public class GestionDocumentosElectronicosBIImpl extends ServiceBase implements
		GestionDocumentosElectronicosBI {

	private final static Logger logger = Logger
			.getLogger(GestionDocumentosElectronicosBIImpl.class);

	private IDocFichaDBEntity docFichaDBEntity = null;
	private IDocClasifCFDBEntity docClasifCFDBEntity = null;
	private IDocClasifDescrDBEntity docClasifDescrDBEntity = null;
	private IDocDocumentoCFDBEntity docDocumentoCFDBEntity = null;
	private IDocDocumentoDescrDBEntity docDocumentoDescrDBEntity = null;
	private IDocTCapturaDBEntity docTCapturaDBEntity = null;
	protected INivelCFDBEntity nivelCFDbEntity = null;
	private IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDbEntity = null;
	private IDescriptorDBEntity descriptorDBEntity = null;
	private IUnidadDocumentalDbEntity unidadDocumentalDbEntity = null;
	private ISerieDbEntity serieDbEntity = null;
	private ICatalogoListaDescriptoresDBEntity catalogoListaDescriptoresDBEntity = null;
	private ICampoDatoDBEntity campoDatoDbEntity = null;
	private ICampoTablaDBEntity campoTablaDbEntity = null;
	private INumeroDBEntity campoNumeroDBEntity = null;
	private ITextoDBEntity campoTextoCortoDBEntity = null;
	private ITextoDBEntity campoTextoLargoDBEntity = null;

	/**
	 * Constructor.
	 */
	public GestionDocumentosElectronicosBIImpl(
			IDocFichaDBEntity docFichaDBEntity,
			IDocClasifCFDBEntity docClasifCFDBEntity,
			IDocClasifDescrDBEntity docClasifDescrDBEntity,
			IDocDocumentoCFDBEntity docDocumentoCFDBEntity,
			IDocDocumentoDescrDBEntity docDocumentoDescrDBEntity,
			IDocTCapturaDBEntity docTCapturaDBEntity,
			INivelCFDBEntity nivelCFDbEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDbEntity,
			IDescriptorDBEntity descriptorDBEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDbEntity,
			ISerieDbEntity serieDbEntity,
			ICatalogoListaDescriptoresDBEntity catalogoListaDescriptoresDBEntity,
			ICampoDatoDBEntity campoDatoDbEntity,
			ICampoTablaDBEntity campoTablaDbEntity,
			INumeroDBEntity campoNumeroDBEntity,
			ITextoDBEntity campoTextoCortoDBEntity,
			ITextoDBEntity campoTextoLargoDBEntity) {
		super();

		this.docFichaDBEntity = docFichaDBEntity;
		this.docClasifCFDBEntity = docClasifCFDBEntity;
		this.docClasifDescrDBEntity = docClasifDescrDBEntity;
		this.docDocumentoCFDBEntity = docDocumentoCFDBEntity;
		this.docDocumentoDescrDBEntity = docDocumentoDescrDBEntity;
		this.docTCapturaDBEntity = docTCapturaDBEntity;
		this.nivelCFDbEntity = nivelCFDbEntity;
		this.elementoCuadroClasificacionDbEntity = elementoCuadroClasificacionDbEntity;
		this.descriptorDBEntity = descriptorDBEntity;
		this.unidadDocumentalDbEntity = unidadDocumentalDbEntity;
		this.serieDbEntity = serieDbEntity;
		this.catalogoListaDescriptoresDBEntity = catalogoListaDescriptoresDBEntity;
		this.campoDatoDbEntity = campoDatoDbEntity;
		this.campoTablaDbEntity = campoTablaDbEntity;
		this.campoNumeroDBEntity = campoNumeroDBEntity;
		this.campoTextoCortoDBEntity = campoTextoCortoDBEntity;
		this.campoTextoLargoDBEntity = campoTextoLargoDBEntity;
	}

	// ========================================================================
	// VOLÚMENES
	// ========================================================================

	/**
	 * Obtiene las listas de volúmenes.
	 *
	 * @return Listas de volúmenes.
	 */
	public List<IRepositorioEcmVO> getRepositoriosEcm() {
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

		List<IRepositorioEcmVO> repositoriosEcm = null;

		try {

			// Obtener el gestor de ficheros
			// GestorFicheros gestorFicherosInvesdoc =
			// GestorFicherosFactory.getConnector(params);
			IGestorRepositoriosECM gestorRepositoriosECM = se.repositoriosECM.GestorRepositoriosECMFactory
					.getGestorRepositoriosECM(params);
			repositoriosEcm = gestorRepositoriosECM.getRepositoriosEcm();

			logger.debug("Repositorios ECM:" + Constants.NEWLINE
					+ repositoriosEcm.toString());

		} catch (Exception e) {
			logger.error("Error al leer los repositorios ECM", e);
		}

		return repositoriosEcm;
	}

	/**
	 * Obtiene las lista de volúmenes asociada al identificador pasado como
	 * parámetro
	 *
	 * @param idRepEcm
	 *            Identificador del repositorio ECM
	 * @return {@link IRepositorioEcmVO}.
	 */
	public IRepositorioEcmVO getRepositorioEcm(String idRepEcm) {
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

		IRepositorioEcmVO repositorioEcmVO = null;

		try {

			// Obtener el gestor de ficheros
			// GestorFicheros gestorFicherosInvesdoc =
			// GestorFicherosFactory.getConnector(params);
			IGestorRepositoriosECM gestorRepositoriosECM = GestorRepositoriosECMFactory
					.getGestorRepositoriosECM(params);
			repositorioEcmVO = gestorRepositoriosECM
					.getRepositorioEcmById(idRepEcm);

			if (logger.isDebugEnabled() && repositorioEcmVO != null)
				logger.debug("Lista de vol\u00FAmenes:" + Constants.NEWLINE
						+ repositorioEcmVO.toString());

		} catch (Exception e) {
			logger.error("Error al leer la lista de vol\u00FAmenes", e);
		}

		return repositorioEcmVO;
	}

	// ========================================================================
	// FICHAS
	// ========================================================================

	/**
	 * Obtiene la lista de fichas de clasificadores documentales.
	 *
	 * @return Listas fichas de clasificadores documentales.
	 */
	public List getFichas() {
		return docFichaDBEntity.getFichas();
	}

	/**
	 * Obtiene la fichas de clasificadores documentales.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public FichaClfVO getFicha(String id) {
		return docFichaDBEntity.getFicha(id);
	}

	/**
	 * Obtiene la información básica de la ficha de clasificadores documentales.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public InfoBFichaClfVO getInfoBFicha(String id) {
		return docFichaDBEntity.getInfoBFicha(id);
	}

	// ========================================================================
	// CLASIFICADORES DE DOCUMENTOS
	// ========================================================================

	/**
	 * Obtiene la lista de clasificadores publicados de un objeto visibles por
	 * el usuario.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Lista de clasificadores.
	 */
	public List getClasificadoresVisiblesDesdeCuadro(int tipoObjeto, String id,
			String idClfPadre) {
		int[] estados = null;

		if (!getServiceClient().hasPermission(
				AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION))
			estados = new int[] { EstadoDocumento.PUBLICADO };

		return getClasificadores(tipoObjeto, id, idClfPadre, estados);
	}

	public List getClasificadoresVisiblesDesdeTarea(int tipoObjeto, String id,
			String idClfPadre) {
		return getClasificadores(tipoObjeto, id, idClfPadre, null);
	}

	/**
	 * Obtiene la lista de clasificadores publicados de un objeto.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Lista de clasificadores.
	 */
	public List getClasificadores(int tipoObjeto, String id, String idClfPadre,
			int[] estados) {
		// Comprobar permisos de consulta
		// checkAcceso(tipoObjeto, id, TipoAcceso.CONSULTA,
		// DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docClasifCFDBEntity.getClasificadores(id, idClfPadre,
					estados);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docClasifDescrDBEntity.getClasificadores(id, idClfPadre,
					estados);
	}

	/**
	 * Obtiene el clasificador de documentos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @parma idObjeto Identificador del objeto.
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador de documentos.
	 */
	public DocClasificadorVO getClasificador(int tipoObjeto, String idObjeto,
			String id) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
			return docClasifCFDBEntity.getClasificador(id);
		} else {
			return docClasifDescrDBEntity.getClasificador(id);
		}
	}

	public DocClasificadorVO getClasificadorByNombre(int tipoObjeto,
			String idObjeto, String nombre, String idClfPadre) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
			return docClasifCFDBEntity.getClasificadorByNombre(idObjeto,
					nombre, idClfPadre);
		} else {
			return docClasifDescrDBEntity.getClasificadorByNombre(idObjeto,
					nombre, idClfPadre);
		}
	}

	protected int calculateEstadoClasificador() {
		if (getServiceClient().hasAnyPermission(
				new String[] { AppPermissions.ADMINISTRACION_TAREAS_CAPTURA,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA }))
			return EstadoClasificador.PUBLICADO;
		return EstadoClasificador.SIN_VALIDAR;
	}

	protected int calculateEstadoDocumento() {
		if (getServiceClient().hasAnyPermission(
				new String[] { AppPermissions.ADMINISTRACION_TAREAS_CAPTURA,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA }))
			return EstadoDocumento.PUBLICADO;
		return EstadoDocumento.SIN_VALIDAR;
	}

	public DocClasificadorVO insertClasificadorXDefecto(
			DocClasificadorVO clasificador) {
		return insertClasificador(clasificador, EstadoClasificador.PUBLICADO);
	}

	public DocClasificadorVO insertClasificadorDesdeCuadro(
			DocClasificadorVO clasificador) {
		// Comprobar permisos de edición
		checkAcceso(
				clasificador.getTipoObjeto(),
				clasificador.getIdObjeto(),
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		return insertClasificador(clasificador, EstadoClasificador.PUBLICADO);
	}

	public DocClasificadorVO insertClasificadorDesdeTarea(
			DocClasificadorVO clasificador) {
		int estado = 0;
		if (getServiceClient().hasAnyPermission(
				new String[] { AppPermissions.ADMINISTRACION_TAREAS_CAPTURA,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA }))
			estado = EstadoClasificador.PUBLICADO;
		else
			estado = EstadoClasificador.SIN_VALIDAR;

		return insertClasificador(clasificador, estado);
	}

	/**
	 * Crea un clasificador de documentos electrónicos.
	 *
	 * @param clasificador
	 *            Clasificador.
	 * @return Clasificador.
	 */
	private DocClasificadorVO insertClasificador(
			DocClasificadorVO clasificador, int estado) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Auditoría
		LoggingEvent event = AuditoriaDocumentos
				.getLogginEvent(
						this,
						ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR);

		clasificador.setEstado(estado);

		// Inserción de la información del clasificador
		if (clasificador.getTipoObjeto() == TipoObjeto.ELEMENTO_CF)
			clasificador = docClasifCFDBEntity.insertClasificador(clasificador);
		else
			// if (clasificador.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
			clasificador = docClasifDescrDBEntity
					.insertClasificador(clasificador);

		// Marcar el objeto como editado
		updateEditClfDocs(clasificador.getIdObjeto(),
				clasificador.getTipoObjeto());

		// Detalle de la auditoría
		AuditoriaDocumentos.auditaInsercionClasificador(locale, event,
				clasificador);

		commit();

		return clasificador;
	}

	public void updateClasificadorDesdeTarea(DocClasificadorVO clasificador) {
		if (clasificador.getEstado() == EstadoClasificador.NO_VALIDO)
			clasificador.setEstado(EstadoClasificador.SIN_VALIDAR);
		updateClasificador(clasificador);
	}

	public void updateClasificadorDesdeCuadro(DocClasificadorVO clasificador) {
		// Comprobar permisos de edición
		checkAcceso(
				clasificador.getTipoObjeto(),
				clasificador.getIdObjeto(),
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		updateClasificador(clasificador);
	}

	/**
	 * Modifica un clasificador de documentos electrónicos.
	 *
	 * @param clasificador
	 *            Clasificador.
	 */
	private void updateClasificador(DocClasificadorVO clasificador) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Auditoría
		AuditoriaDocumentos.auditaModificacionClasificador(locale, this,
				clasificador);

		// Actualización de la información del documento
		if (clasificador.getTipoObjeto() == TipoObjeto.ELEMENTO_CF)
			docClasifCFDBEntity.updateClasificador(clasificador);
		else
			// if (clasificador.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
			docClasifDescrDBEntity.updateClasificador(clasificador);

		commit();
	}

	/**
	 * Elimina un clasificador de documentos electrónicos.
	 *
	 * @param clasificador
	 *            Clasificador.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeClasificador(DocClasificadorVO clasificador)
			throws Exception {
		Locale locale = getServiceClient().getLocale();

		if (clasificador.getMarcas() != MarcasClasificador.FIJO) {
			iniciarTransaccion();

			// Comprobar permisos de edición
			checkAcceso(
					clasificador.getTipoObjeto(),
					clasificador.getIdObjeto(),
					TipoAcceso.EDICION,
					DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

			// Eliminar clasificadores hijos
			removeClasificadoresHijos(clasificador.getTipoObjeto(),
					clasificador.getIdObjeto(), clasificador.getId());

			// Eliminar documentos hijos
			removeDocumentosHijos(clasificador.getTipoObjeto(),
					clasificador.getIdObjeto(), clasificador.getId());

			// Auditoría
			AuditoriaDocumentos.auditaEliminacionClasificador(locale, this,
					clasificador.getId(), clasificador.getNombre());

			// Eliminar el clasificador
			if (clasificador.getTipoObjeto() == TipoObjeto.ELEMENTO_CF)
				docClasifCFDBEntity.deleteClasificador(clasificador.getId());
			else
				// if (documento.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
				docClasifDescrDBEntity.deleteClasificador(clasificador.getId());

			commit();
		}
	}

	/**
	 * Elimina el contenido seleccionado del clasificador de documentos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param listaIdsClasificadores
	 *            Lista de identificadores de clasificadores.
	 * @param listaIdsDocumentos
	 *            Lista de identificadores de documentos.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeContenidoClasificador(int tipoObjeto, String idObjeto,
			String[] listaIdsClasificadores, String[] listaIdsDocumentos)
			throws Exception {
		// Comprobar permisos de edición
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		iniciarTransaccion();
		removeClasificadores(tipoObjeto, idObjeto, listaIdsClasificadores);
		removeDocumentos(tipoObjeto, idObjeto, listaIdsDocumentos);
		commit();
	}

	/**
	 * Elimina una lista de clasificadores de documentos electrónicos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param listaIds
	 *            Lista de identificadores de documentos.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected void removeClasificadores(int tipoObjeto, String idObjeto,
			String[] listaIds) throws Exception {
		Locale locale = getServiceClient().getLocale();

		if (listaIds != null) {
			// StringOwnTokenizer tok = null;
			String id = null, nombre = null;

			for (int i = 0; i < listaIds.length; i++) {
				// tok = new StringOwnTokenizer(listaIds[i], "#");
				// if (tok.hasMoreTokens())
				// id = tok.nextToken();
				// if (tok.hasMoreTokens())
				// nombre = tok.nextToken();

				iniciarTransaccion();
				// anadido por abel
				id = listaIds[i];
				// *********
				// Eliminar clasificadores hijos
				removeClasificadoresHijos(tipoObjeto, idObjeto, id);

				// Eliminar documentos hijos
				removeDocumentosHijos(tipoObjeto, idObjeto, id);

				// Auditoría
				// anadido por abel
				DocClasificadorVO cla = getClasificador(tipoObjeto, idObjeto,
						id);
				nombre = cla.getNombre();
				// *********
				AuditoriaDocumentos.auditaEliminacionClasificador(locale, this,
						id, nombre);

				// Eliminar el clasificador
				if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
					docClasifCFDBEntity.deleteClasificador(id);
				else
					// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
					docClasifDescrDBEntity.deleteClasificador(id);

				commit();
			}
		}
	}

	protected void removeClasificadoresHijos(int tipoObjeto, String idObjeto,
			String id) throws Exception {
		Locale locale = getServiceClient().getLocale();

		// Obtener la lista de clasificadores hijos
		List clasificadoresHijos = getClasificadores(tipoObjeto, idObjeto, id,
				null);
		DocClasificadorVO clasificadorHijo;
		for (int i = 0; i < clasificadoresHijos.size(); i++) {
			clasificadorHijo = (DocClasificadorVO) clasificadoresHijos.get(i);

			// Eliminar clasificadores hijos
			removeClasificadoresHijos(tipoObjeto, idObjeto,
					clasificadorHijo.getId());

			// Eliminar documentos hijos
			removeDocumentosHijos(tipoObjeto, idObjeto,
					clasificadorHijo.getId());

			// Auditoría
			AuditoriaDocumentos.auditaEliminacionClasificador(locale, this,
					clasificadorHijo.getId(), clasificadorHijo.getNombre());

			// Eliminar el clasificador
			if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
				docClasifCFDBEntity
						.deleteClasificador(clasificadorHijo.getId());
			else
				// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
				docClasifDescrDBEntity.deleteClasificador(clasificadorHijo
						.getId());
		}
	}

	protected void removeDocumentosHijos(int tipoObjeto, String idObjeto,
			String id) {
		Locale locale = getServiceClient().getLocale();

		// Obtener la lista de documentos hijos
		List documentosHijos = getDocumentos(tipoObjeto, idObjeto, id, null);
		DocDocumentoVO documentoHijo;
		for (int i = 0; i < documentosHijos.size(); i++) {
			documentoHijo = (DocDocumentoVO) documentosHijos.get(i);

			// Auditoría
			AuditoriaDocumentos.auditaEliminacionDocumento(locale, this,
					documentoHijo.getId(), documentoHijo.getNombre(),
					documentoHijo.getIdFich());

			// Eliminar el documento
			if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
				docDocumentoCFDBEntity.deleteDocumento(documentoHijo.getId());
			else
				// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
				docDocumentoDescrDBEntity
						.deleteDocumento(documentoHijo.getId());
		}
	}

	/**
	 * Indica si el clasificador es de tipo fijo.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @return True si el padre del clasificador es de tipo fijo.
	 */
	public boolean esFijo(String id, int tipoObjeto, String idObjeto) {
		DocClasificadorVO clasificador = getClasificador(tipoObjeto, idObjeto,
				id);
		return (clasificador == null)
				|| (clasificador.getMarcas() == MarcasClasificador.FIJO);
	}

	/**
	 * Indica si el clasificador tiene algún hijo de tipo fijo.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @return True si el clasificador tiene algún hijo de tipo fijo.
	 */
	public boolean tieneHijoFijo(String id, int tipoObjeto) {
		boolean tieneHijoFijo = false;

		List hijos = getClasificadores(tipoObjeto, null, id, null);
		DocClasificadorVO hijo;
		for (int i = 0; !tieneHijoFijo && (i < hijos.size()); i++) {
			hijo = (DocClasificadorVO) hijos.get(i);
			tieneHijoFijo = (hijo.getMarcas() == MarcasClasificador.FIJO);
		}

		return tieneHijoFijo;
	}

	// ========================================================================
	// DOCUMENTOS ELECTRÓNICOS
	// ========================================================================

	/**
	 * Obtiene la lista de documentos de un objeto visibles por el usuario.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Lista de documentos.
	 */
	public List getDocumentosVisiblesDesdeCuadro(int tipoObjeto, String id,
			String idClfPadre) {
		int[] estados = null;

		if (!getServiceClient().hasPermission(
				AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION))
			estados = new int[] { EstadoDocumento.PUBLICADO };

		return getDocumentos(tipoObjeto, id, idClfPadre, estados);
	}

	public List getDocumentosVisiblesDesdeTarea(int tipoObjeto, String id,
			String idClfPadre) {
		return getDocumentos(tipoObjeto, id, idClfPadre, null);
	}

	/**
	 * Obtiene la lista de documentos de un objeto.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Lista de documentos.
	 */
	public List getDocumentos(int tipoObjeto, String id, String idClfPadre,
			int[] estados) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				id,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docDocumentoCFDBEntity
					.getDocumentos(id, idClfPadre, estados);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docDocumentoDescrDBEntity.getDocumentos(id, idClfPadre,
					estados);
	}

	/**
	 * Obtiene la lista de documentos de un objeto.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Lista de documentos.
	 */
	public List getDocumentos(int tipoObjeto, String id, int[] estados) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				id,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docDocumentoCFDBEntity.getDocumentos(id, estados);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docDocumentoDescrDBEntity.getDocumentos(id, estados);
	}

	/**
	 * Comprueba si un objeto tiene documentos asociados
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @return <b>true</b> si el objeto tiene docuementos asociados y
	 *         <b>false</b> en caso contrario
	 */
	public boolean tieneDocumentosAsociados(int tipoObjeto, String id) {
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docDocumentoCFDBEntity.countNumDocumentos(id) > 0;
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docDocumentoDescrDBEntity.countNumDocumentos(id) > 0;

	}

	/**
	 * Obtiene el documento electrónico.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Documento electrónico.
	 */
	public DocDocumentoVO getDocumento(int tipoObjeto, String idObjeto,
			String id) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docDocumentoCFDBEntity.getDocumento(id);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docDocumentoDescrDBEntity.getDocumento(id);
	}

	/**
	 * Obtiene la información del fichero del documento electrónico.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Información del fichero.
	 */
	public DocDocumentoExtVO getDocumentoExt(int tipoObjeto, String idObjeto,
			String id) throws Exception {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		DocDocumentoVO documento = null;
		DocDocumentoExtVO documentoExt = null;

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			documento = docDocumentoCFDBEntity.getDocumento(id);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			documento = docDocumentoDescrDBEntity.getDocumento(id);

		if (documento != null) {
			documentoExt = new DocDocumentoExtVO();
			POUtils.copyVOProperties(documentoExt, documento);
			documentoExt.setContenido(getFile(documentoExt.getIdRepEcm(),
					documentoExt.getIdExtDeposito(), documentoExt.getIdFich()));
		}

		return documentoExt;
	}

	/**
	 * Añade los documentos transferidos desde el sistema tramitador.
	 *
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @param documentos
	 *            Lista de documentos electrónicos (
	 *            {@link DocumentoElectronicoVO}).
	 */
	public void insertDocumentosDesdeValidacion(String idUdoc, List documentos) {
		Locale locale = getServiceClient().getLocale();

		if (StringUtils.isNotBlank(idUdoc)
				&& !CollectionUtils.isEmpty(documentos)) {
			iniciarTransaccion();

			DocumentoElectronicoVO documento;
			DocDocumentoVO doc;
			LoggingEvent event;

			for (int i = 0; i < documentos.size(); i++) {
				documento = (DocumentoElectronicoVO) documentos.get(i);

				// Crea el documento electrónico
				doc = new DocDocumentoVO();
				doc.setNombre(documento.getNombre());
				doc.setIdClfPadre(null);
				doc.setIdExtDeposito(documento.getRepositorio());
				doc.setIdFich(documento.getLocalizador());
				doc.setEstado(EstadoDocumento.PUBLICADO);
				doc.setDescripcion(documento.getDescripcion());
				doc.setIdObjeto(idUdoc);
				doc.setTipoObjeto(TipoObjeto.ELEMENTO_CF);
				doc.setTamanoFich(-1);
				doc.setNombreOrgFich(documento.getNombre());

				// Extensión del fichero
				if (StringUtils.isNotBlank(documento.getExtension()))
					doc.setExtFich(documento.getExtension().toLowerCase());
				else
					doc.setExtFich("___");

				// Auditoría
				event = AuditoriaDocumentos
						.getLogginEvent(
								this,
								ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO);

				// Inserción de la información del documento
				docDocumentoCFDBEntity.insertDocumento(doc);

				// Detalle de la auditoría
				AuditoriaDocumentos
						.auditaInsercionDocumento(locale, event, doc);
			}

			commit();
		}
	}

	public DocDocumentoVO insertDocumentoDesdeTarea(
			DocDocumentoExtVO documentoExt) throws Exception {

		int estado = 0;
		if (getServiceClient().hasAnyPermission(
				new String[] { AppPermissions.ADMINISTRACION_TAREAS_CAPTURA,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA }))
			estado = EstadoDocumento.PUBLICADO;
		else
			estado = EstadoDocumento.SIN_VALIDAR;

		return insertDocumento(documentoExt, estado, true);
	}

	public DocDocumentoVO insertDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt) throws Exception {
		return insertDocumento(documentoExt, EstadoDocumento.PUBLICADO, true);
	}

	public DocDocumentoVO insertDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt, boolean almacenarFichero)
			throws Exception {
		// Comprobar permisos de edición
		checkAcceso(
				documentoExt.getTipoObjeto(),
				documentoExt.getIdObjeto(),
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		return insertDocumento(documentoExt, EstadoDocumento.PUBLICADO,
				almacenarFichero);
	}

	/**
	 * Crea un documento electrónico.
	 *
	 * @param documentoExt
	 *            Documento electrónico.
	 * @return Documento.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	private DocDocumentoVO insertDocumento(DocDocumentoExtVO documentoExt,
			int estado, boolean almacenarFichero) throws Exception {
		Locale locale = getServiceClient().getLocale();

		// Inicializar el Id de Fichero

		if (almacenarFichero) {
			documentoExt.setIdFich("0");
		}

		iniciarTransaccion();

		documentoExt.setEstado(estado);
		if (StringUtils.isNotBlank(documentoExt.getExtFich())) {
			documentoExt.setExtFich(documentoExt.getExtFich().toLowerCase());
		}

		if (StringUtils.isBlank(documentoExt.getIdRepEcm())) {
			String idRepEcm = getIdRepEcmElementoCf(
					documentoExt.getTipoObjeto(), documentoExt.getIdObjeto());

			if (idRepEcm != null) {
				documentoExt.setIdRepEcm(idRepEcm);
			} else {
				logger.error("Error al obtener el identificador del repositorio documental");
			}
		}

		if (documentoExt.getTipoObjeto() == TipoObjeto.ELEMENTO_CF) {
			// Elemento del cuadro
			ElementoCuadroClasificacionVO elementoCF = getServiceRepository()
					.lookupGestionCuadroClasificacionBI()
					.getElementoCuadroClasificacion(documentoExt.getIdObjeto());
			if (elementoCF != null) {
				// Auditoría
				LoggingEvent event = AuditoriaDocumentos
						.getLogginEvent(
								this,
								ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO);

				// Inserción de la información del documento
				docDocumentoCFDBEntity.insertDocumento(documentoExt);

				if (almacenarFichero) {
					// Upload del fichero
					documentoExt.setIdFich(storeFile(documentoExt));

					// Actualización del id de fichero
					docDocumentoCFDBEntity.updateDocumento(documentoExt);

					// Comprobamos si tenemos que añadir la descripcion del
					// documento
					if (ConfigConstants.getInstance()
							.getAgregarDescripcionDocumentos()) {
						agregarCamposDocumento(documentoExt);
					}
				}

				// Detalle de la auditoría
				AuditoriaDocumentos.auditaInsercionDocumento(locale, event,
						documentoExt);
			}
		} else // if (documento.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
		{
			// Descriptor
			DescriptorVO descriptor = getServiceRepository()
					.lookupGestionDescripcionBI().getDescriptor(
							documentoExt.getIdObjeto());
			if (descriptor != null) {
				// Auditoría
				LoggingEvent event = AuditoriaDocumentos
						.getLogginEvent(
								this,
								ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO);

				// Inserción de la información del documento
				docDocumentoDescrDBEntity.insertDocumento(documentoExt);

				if (almacenarFichero) {
					// Upload del fichero
					documentoExt.setIdFich(storeFile(documentoExt));

					// Actualización del id de fichero
					docDocumentoDescrDBEntity.updateDocumento(documentoExt);
				}

				// Detalle de la auditoría
				AuditoriaDocumentos.auditaInsercionDocumento(locale, event,
						documentoExt);
			}
		}

		// Marcar el objeto como editado
		updateEditClfDocs(documentoExt.getIdObjeto(),
				documentoExt.getTipoObjeto());

		commit();

		return documentoExt;
	}

	public DocDocumentoVO updateDocumentoDesdeTarea(
			DocDocumentoExtVO documentoExt) throws Exception {
		if (documentoExt.getEstado() == EstadoDocumento.NO_VALIDO)
			documentoExt.setEstado(EstadoDocumento.SIN_VALIDAR);
		return updateDocumento(documentoExt);
	}

	public DocDocumentoVO updateDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt) throws Exception {

		// Comprobar permisos de edición
		checkAcceso(
				documentoExt.getTipoObjeto(),
				documentoExt.getIdObjeto(),
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		return updateDocumento(documentoExt);
	}

	/**
	 * Modifica un documento electrónico.
	 *
	 * @param documentoExt
	 *            Documento electrónico.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	private DocDocumentoVO updateDocumento(DocDocumentoExtVO documentoExt)
			throws Exception {
		Locale locale = getServiceClient().getLocale();

		DocDocumentoVO documento = null;

		iniciarTransaccion();

		if (StringUtils.isNotBlank(documentoExt.getExtFich()))
			documentoExt.setExtFich(documentoExt.getExtFich().toLowerCase());

		// Auditoría
		AuditoriaDocumentos.auditaModificacionDocumento(locale, this,
				documentoExt);

		if (documentoExt.getTipoObjeto() == TipoObjeto.ELEMENTO_CF) {
			String idFich = null;
			if (documentoExt.getContenido() != null) {
				// Elemento del cuadro
				ElementoCuadroClasificacionVO elementoCF = getServiceRepository()
						.lookupGestionCuadroClasificacionBI()
						.getElementoCuadroClasificacion(
								documentoExt.getIdObjeto());
				if (elementoCF != null) {
					// Eliminar el fichero anterior
					deleteFile(documentoExt.getIdRepEcm(),
							documentoExt.getIdExtDeposito(),
							documentoExt.getIdFich());

					// Guardamos el idFich anterior para actualizar los campos
					idFich = documentoExt.getIdFich();

					// Upload del fichero
					documentoExt.setIdFich(storeFile(documentoExt));
				}
			}

			// Actualización de la información del documento
			docDocumentoCFDBEntity.updateDocumento(documentoExt);

			// Comprobamos si tenemos que añadir la descripcion del documeto
			if (ConfigConstants.getInstance().getAgregarDescripcionDocumentos()) {
				actualizarCamposDocumento(documentoExt, idFich);
			}
		} else // if (documento.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
		{
			if (documentoExt.getContenido() != null) {
				// Descriptor
				DescriptorVO descriptor = getServiceRepository()
						.lookupGestionDescripcionBI().getDescriptor(
								documentoExt.getIdObjeto());
				if (descriptor != null) {
					// Eliminar el fichero anterior
					deleteFile(documentoExt.getIdRepEcm(),
							documentoExt.getIdExtDeposito(),
							documentoExt.getIdFich());

					// Upload del fichero
					documentoExt.setIdFich(storeFile(documentoExt));
				}
			}

			// Actualización de la información del documento
			docDocumentoDescrDBEntity.updateDocumento(documentoExt);
		}

		commit();

		return documento;
	}

	/**
	 * Elimina un documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeDocumento(DocDocumentoVO documento) throws Exception {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Comprobar permisos de edición
		checkAcceso(
				documento.getTipoObjeto(),
				documento.getIdObjeto(),
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		// Auditoría
		AuditoriaDocumentos
				.auditaEliminacionDocumento(locale, this, documento.getId(),
						documento.getNombre(), documento.getIdFich());

		// Eliminar el documento
		if (documento.getTipoObjeto() == TipoObjeto.ELEMENTO_CF)
			docDocumentoCFDBEntity.deleteDocumento(documento.getId());
		else
			// if (documento.getTipoObjeto() == TipoObjeto.DESCRIPTOR)
			docDocumentoDescrDBEntity.deleteDocumento(documento.getId());

		// Eliminar el fichero
		if (documento.getIdFich() != null)
			deleteFile(documento.getIdRepEcm(), documento.getIdExtDeposito(),
					documento.getIdFich());

		// Comprobamos si tenemos que eliminar la descripcion del documento
		if (ConfigConstants.getInstance().getAgregarDescripcionDocumentos()) {
			eliminarCamposDocumento(documento);
		}

		commit();
	}

	/**
	 * Elimina una lista de documentos electrónicos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param listaIds
	 *            Lista de identificadores de documentos.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected void removeDocumentos(int tipoObjeto, String idObjeto,
			String[] listaIds) throws Exception {
		Locale locale = getServiceClient().getLocale();

		if (listaIds != null) {
			// StringOwnTokenizer tok = null;
			String id = null, idFich = null, nombre = null;

			for (int i = 0; i < listaIds.length; i++) {
				// tok = new StringOwnTokenizer(listaIds[i], "#");
				// if (tok.hasMoreTokens())
				// id = tok.nextToken();
				// if (tok.hasMoreTokens())
				// idFich = tok.nextToken();
				// if (tok.hasMoreTokens())
				// nombre = tok.nextToken();

				iniciarTransaccion();
				// abel
				id = listaIds[i];
				DocDocumentoVO doc = getDocumento(tipoObjeto, idObjeto, id);
				nombre = doc.getNombre();
				idFich = doc.getIdFich();
				// *********

				// Auditoría
				AuditoriaDocumentos.auditaEliminacionDocumento(locale, this,
						id, nombre, idFich);

				// Eliminar el documento
				if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
					docDocumentoCFDBEntity.deleteDocumento(id);
				else
					// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
					docDocumentoDescrDBEntity.deleteDocumento(id);

				// Eliminar el fichero
				if (idFich != null)
					deleteFile(doc.getIdRepEcm(), doc.getIdExtDeposito(),
							idFich);

				// Comprobamos si tenemos que eliminar la descripcion del
				// documento
				if (ConfigConstants.getInstance()
						.getAgregarDescripcionDocumentos()) {
					eliminarCamposDocumento(doc);
				}

				commit();
			}
		}
	}

	// ========================================================================
	// GENERALES
	// ========================================================================

	/**
	 * Inicializa los clasificadores por defecto del objeto.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param isTarea
	 *            Booleano que indica si se está realizando la operación para
	 *            una tarea de digitalización o no
	 * @throws DocElectronicosException
	 */
	public void inicializaClasificadoresYRepEcm(String idObjeto,
			int tipoObjeto, boolean isTarea) throws DocElectronicosException {
		String idFichaClfDoc = null;
		DataClfDocYRepEcm dataClfDocYRepEcm = null;
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		// Obtener el identificador de la ficha de los clasificadores por
		// defecto
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			dataClfDocYRepEcm = getIdFichaClfDocYRepEcmElementoCF(idObjeto);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			dataClfDocYRepEcm = getIdFichaClfDocYRepEcmDescriptor(idObjeto);

		List listaDocumentos = this.getDocumentosVisiblesDesdeCuadro(
				tipoObjeto, idObjeto);

		// si el objeto ya fue editado no se hace nada
		if (dataClfDocYRepEcm.isUpdateClAndIdVol()) {

			// no se permiten crear tarea sin lista de volúmenes
			// Nuevo: si no hay lista de volúmenes asociada, pero sí hay
			// documentos a los que el usuario tenga acceso, se ignora toda la
			// lógica de creación
			// de clasificadores pero no se lanza el error al intentar ver los
			// documentos, siempre y cuando, se disponga del permiso de verlos,
			// que se chequea
			// en la llamada de getDocumentosVisiblesDesdeCuadro (para filtrar
			// estados del documento) y en la jsp
			if (dataClfDocYRepEcm.getIdRepEcm() == null
					&& (listaDocumentos == null || listaDocumentos.size() == 0)) {

				if (isTarea) {
					if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
						throw new DocElectronicosException(
								DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_FALTA_REPOSITORIO_ECM_NIVEL_CUADRO);
					else
						throw new DocElectronicosException(
								DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_FALTA_REPOSITORIO_ECM_DESCRIPTOR);
				} else {
					if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
						throw new DocElectronicosException(
								DocElectronicosException.XNO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_ELEMENTO_CUADRO);
					else
						throw new DocElectronicosException(
								DocElectronicosException.XNO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_DESCRIPTOR);
				}
			} else {
				iniciarTransaccion();

				// columnas a actualizar
				Map<DbColumnDef, String> colsToUpdate = new HashMap<DbColumnDef, String>();
				if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
					colsToUpdate
							.put(ElementoCuadroClasificacionDBEntityImpl.REPOSITORIO_ECM_FIELD,
									dataClfDocYRepEcm.getIdRepEcm());
				} else {
					colsToUpdate.put(DescriptorDBEntityImpl.CAMPO_ID_REP_ECM,
							dataClfDocYRepEcm.getIdRepEcm());
				}

				// actualizacion de columnas en el elemento
				if (colsToUpdate.size() > 0) {
					if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
						elementoCuadroClasificacionDbEntity.updateFieldsECF(
								idObjeto, colsToUpdate);
					} else {
						descriptorDBEntity.updateDescriptorVOXId(idObjeto,
								colsToUpdate);
					}
				}

				// creacion de clasificadores si hay ficha asociada
				if (dataClfDocYRepEcm.getIdFichaCla() != null) {
					if (logger.isInfoEnabled())
						logger.info("Creando los clasificadores por defecto del "
								+ (tipoObjeto == TipoObjeto.ELEMENTO_CF ? "elemento"
										: "descriptor")
								+ ": ["
								+ idObjeto
								+ "] a partir de la ficha: ["
								+ idFichaClfDoc
								+ "]");

					// Obtener la ficha de los clasificadores por defecto
					FichaClfVO fichaClf = getFicha(dataClfDocYRepEcm
							.getIdFichaCla());
					if (logger.isDebugEnabled())
						logger.debug("FichaClfVO: "
								+ dataClfDocYRepEcm.getIdFichaCla());

					if (fichaClf != null) {
						// Crear los clasificadores por defecto
						ClasificadoresHelper.getInstance(idObjeto, tipoObjeto,
								this).createClasificadoresPorDefecto(
								fichaClf.getDefinicion());
					}
				}

				// Marcar el objeto como editado
				updateEditClfDocs(idObjeto, tipoObjeto);

				commit();
			}
		}

		return;

	}

	public class DataClfDocYRepEcm {
		String idRepEcm;
		String idFichaCla;
		boolean updateClAndIdVol = false;

		public DataClfDocYRepEcm(String idFichaClfDoc, String idRepEcm,
				boolean updateClAndIdVol) {
			this.idFichaCla = idFichaClfDoc;
			this.idRepEcm = idRepEcm;
			this.updateClAndIdVol = updateClAndIdVol;
		}

		public String getIdFichaCla() {
			return idFichaCla;
		}

		public void setIdFichaCla(String idFichaCla) {
			this.idFichaCla = idFichaCla;
		}

		public String getIdRepEcm() {
			return idRepEcm;
		}

		public void setIdRepEcm(String idRepEcm) {
			this.idRepEcm = idRepEcm;
		}

		public boolean isUpdateClAndIdVol() {
			return updateClAndIdVol;
		}

		public void setUpdateClAndIdVol(boolean updateClAndIdVol) {
			this.updateClAndIdVol = updateClAndIdVol;
		}
	}

	/**
	 * Obtiene el identificador de la ficha de los clasificadores por defecto un
	 * elemento del cuadro de clasificación.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public DataClfDocYRepEcm getIdFichaClfDocYRepEcmElementoCF(String idObjeto)
			throws DocElectronicosException {
		String idFichaClfDoc = null;
		String idRepEcm = null;

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		// Obtener la información del elemento del cuadro
		ElementoCuadroClasificacionVO elementoCF = cuadroBI
				.getElementoCuadroClasificacion(idObjeto);
		if (elementoCF != null) {

			INivelCFVO nivel = cuadroBI.getNivelCF(elementoCF.getIdNivel());
			idRepEcm = elementoCF.getIdRepEcm();

			// asignar ficha de clasificador por el nivel
			if (nivel != null)
				idFichaClfDoc = nivel.getIdFichaClfDocPref();

			// clasficadores no editados
			if (!Constants.TRUE_STRING.equals(elementoCF.getEditClfDocs())) {
				if (nivel.getTipoNivel().equals(TipoNivelCF.UNIDAD_DOCUMENTAL)) {
					// si es unidad documental obtener ficha de clasificadores y
					// lista vol asociados al nivel del elemento
					UnidadDocumentalVO unidadVO = unidadDocumentalDbEntity
							.getUnidadDocumental(idObjeto);
					SerieVO serie = serieDbEntity.getSerie(unidadVO
							.getIdSerie());
					idRepEcm = serie.getIdRepEcmPrefUdoc(nivel.getId());

					// Esto no estaba antes de 2.6
					// Si hay definido un clasificador esplícito para el nivel,
					// se coge ese, sino, se deja
					// el que se le hubiese asignado al nivel
					String fichaClfDoc = serie.getIdFichaClfDocPrefUdoc(nivel
							.getId());
					if (StringUtils.isNotEmpty(fichaClfDoc))
						idFichaClfDoc = fichaClfDoc;
					//
				}

				if (idRepEcm == null) {
					if (nivel != null)
						idRepEcm = nivel.getIdRepEcmPref();
				}

				return new DataClfDocYRepEcm(idFichaClfDoc, idRepEcm, true);

			} else {
				return new DataClfDocYRepEcm(idFichaClfDoc,
						elementoCF.getIdRepEcm(), false);
			}

		} else {
			// return null;
			throw new DocElectronicosException(
					DocElectronicosException.XNO_PERMITIDO_PARA_UNIDADES_SIN_VALIDAR);
		}

	}

	/**
	 * Obtiene el identificador de la ficha de los clasificadores por defecto un
	 * descriptor.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public DataClfDocYRepEcm getIdFichaClfDocYRepEcmDescriptor(String idObjeto) {
		String idFichaClfDoc = null;
		String idRepEcm = null;

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		// Obtener la información del descriptor
		DescriptorVO descriptor = descripcionBI.getDescriptor(idObjeto);
		if (descriptor != null) {
			idRepEcm = descriptor.getIdRepEcm();
			// Obtener la información de la lista descriptora
			ListaDescrVO listaDescriptora = descripcionBI
					.getListaDescriptora(descriptor.getIdLista());
			if (listaDescriptora != null) {
				idFichaClfDoc = listaDescriptora.getIdFichaClfDocPref();
				if (!Constants.TRUE_STRING.equals(descriptor.getEditClfDocs())) {
					if (idRepEcm == null)
						idRepEcm = listaDescriptora.getIdRepEcmPref();
					return new DataClfDocYRepEcm(idFichaClfDoc, idRepEcm, true);
				} else
					return new DataClfDocYRepEcm(idFichaClfDoc, idRepEcm, false);
			}

		}
		return null;
	}

	/**
	 * Marca el objeto como editado.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto});
	 */
	protected void updateEditClfDocs(String idObjeto, int tipoObjeto) {
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
			ServiceRepository.getInstance(getServiceSession())
					.lookupGestionCuadroClasificacionBI()
					.setEditClfDocs(idObjeto, true);
		} else // if (tipoObjeto == TipoObjeto.DESCRIPTOR)
		{
			descriptorDBEntity.setEditClfDocs(idObjeto,
					DBUtils.getStringValue(true));
		}
	}

	/**
	 * Elimina los clasificadores y documentos del objeto.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public void deleteDocumentos(String idObjeto, int tipoObjeto) {
		iniciarTransaccion();

		// Comprobar permisos de edición
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.EDICION,
				DocumentosElectronicosSecurityManager.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			deleteDocumentosElementoCF(idObjeto);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			deleteDocumentosDescriptor(idObjeto);

		commit();
	}

	/**
	 * Elimina los clasificadores y documentos del elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 */
	protected void deleteDocumentosElementoCF(String idElementoCF) {
		// Leer la lista de documentos asociados al elemento
		List documentos = docDocumentoCFDBEntity.getDocumentos(idElementoCF);

		// Eliminar los documentos del elemento
		docDocumentoCFDBEntity.deleteDocumentos(idElementoCF);

		// Eliminar los clasificadores del elemento
		docClasifCFDBEntity.deleteClasificadores(idElementoCF);

		// Eliminar los documentos físicos del elemento
		deleteDocumentos(documentos);
	}

	/**
	 * Elimina los clasificadores y documentos del descriptor.
	 *
	 * @param idElementoCF
	 *            Identificador del descriptor.
	 */
	protected void deleteDocumentosDescriptor(String idDescr) {
		// Leer la lista de documentos asociados al descriptor
		List documentos = docDocumentoDescrDBEntity.getDocumentos(idDescr);

		// Eliminar los documentos del descriptor
		docDocumentoDescrDBEntity.deleteDocumentos(idDescr);

		// Eliminar los clasificadores del descriptor
		docClasifDescrDBEntity.deleteClasificadores(idDescr);

		// Eliminar los documentos físicos del descriptor
		deleteDocumentos(documentos);
	}

	/**
	 * Elimina los fichero de la lista de documentos.
	 *
	 * @param documentos
	 *            Lista de documentos.
	 */
	protected void deleteDocumentos(List documentos) {
		if (documentos != null) {
			DocDocumentoVO documento;
			// int idFichero;

			for (int i = 0; i < documentos.size(); i++) {
				documento = (DocDocumentoVO) documentos.get(i);

				try {
					// Eliminar el fichero del documento
					deleteFile(documento.getIdRepEcm(),
							documento.getIdExtDeposito(), documento.getIdFich());
				} catch (Exception e) {
					logger.warn("Error al eliminar el fichero con id "
							+ documento.getIdFich(), e);
				}
			}
		}
	}

	class TreeModelClasificadoresDocumentos extends DefaultTreeModel {

		String idObjeto = null;
		int tipoObjeto;
		GestionDocumentosElectronicosBI documentosService = null;

		TreeModelClasificadoresDocumentos(int tipo, String idObjeto,
				GestionDocumentosElectronicosBI documentosService) {
			this.tipoObjeto = tipo;
			this.idObjeto = idObjeto;
			this.documentosService = documentosService;
		}

		public Collection getChilds(TreeModelItem item) {
			DocumentosTreeModelItemVO modelItem = (DocumentosTreeModelItemVO) item;
			if (modelItem != null) {
				DocClasificadorVO clasificadorVO = getClasificador(tipoObjeto,
						idObjeto, modelItem.getId());
				if (clasificadorVO != null) {
					clasificadorVO
							.setParent((DocumentosTreeModelItemVO) modelItem
									.getParent());
					addChildren(clasificadorVO, clasificadorVO.getIdObjeto(),
							tipoObjeto);
					return clasificadorVO.childItems();
				}
			}
			return null;
			// DocumentosTreeModelItemVO modelItem =
			// (DocumentosTreeModelItemVO)item;
			// if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			// return docClasifCFDBEntity.getClasificadores(idObjeto,
			// modelItem.getId(), null);
			// else //if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			// return docClasifDescrDBEntity.getClasificadores(idObjeto,
			// modelItem.getId(), null);
		}

		public TreeModelItem getParentItem(TreeModelItem item) {
			DocumentosTreeModelItemVO modelItem = (DocumentosTreeModelItemVO) item;
			return (TreeModelItem) getClasificadorPadre(tipoObjeto,
					modelItem.getId());
			// if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			// return
			// docClasifCFDBEntity.getClasificadorPadre(modelItem.getId());
			// else //if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			// return
			// docClasifDescrDBEntity.getClasificadorPadre(modelItem.getId());
		}
	}

	public DocClasificadorVO getClasificadorPadre(int tipoObjeto,
			String idObject) {
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docClasifCFDBEntity.getClasificadorPadre(idObject);
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docClasifDescrDBEntity.getClasificadorPadre(idObject);
	}

	/**
	 * Obtiene la raíz del árbol de documentos.
	 *
	 * @param id
	 *            Identificador del objeto que contiene los documentos.
	 * @param tipo
	 *            Tipo de objeto que contiene los documentos.
	 * @return Árbol de documentos.
	 */
	public TreeModel getTreeModel(String id, int tipo) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipo,
				id,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		// creacion de un proxy al servicio
		GestionDocumentosElectronicosBI wraperDocumentosService = ServiceRepository
				.getInstance(getServiceSession())
				.lookupGestionDocumentosElectronicosBI();
		DefaultTreeModel treeModel = new TreeModelClasificadoresDocumentos(
				tipo, id, wraperDocumentosService);

		// Añadir clasificadores hijos
		List clasificadores = getClasificadores(tipo, id, null, null);
		DocClasificadorVO clasificador;
		for (int i = 0; i < clasificadores.size(); i++) {
			clasificador = (DocClasificadorVO) clasificadores.get(i);
			treeModel.addItem(clasificador);
			addChildren(clasificador, id, tipo);
		}

		/*
		 * // Añadir documentos hijos List documentos = getDocumentos(tipo, id,
		 * null, null); for (int i = 0; i < documentos.size(); i++)
		 * treeModel.addItem((DocDocumentoVO) documentos.get(i));
		 */

		return treeModel;
	}

	protected void addChildren(DocClasificadorVO clasificador, String idObjeto,
			int tipoObjeto) {
		// Añadir clasificadores hijos
		List clasificadores = getClasificadores(tipoObjeto, idObjeto,
				(String) clasificador.getItemId(), null);
		DocClasificadorVO clasificadorHijo;
		for (int i = 0; i < clasificadores.size(); i++) {
			clasificadorHijo = (DocClasificadorVO) clasificadores.get(i);
			clasificador.addChildItem(clasificadorHijo);
			addChildren(clasificadorHijo, idObjeto, tipoObjeto);
		}

		/*
		 * // Añadir documentos hijos List documentos =
		 * getDocumentos(tipoObjeto, idObjeto, (String)
		 * clasificador.getItemId(), null); for (int i = 0; i <
		 * documentos.size(); i++) clasificador.addChildItem((DocDocumentoVO)
		 * documentos.get(i));
		 */
	}

	/**
	 * Obtiene el contenido de un fichero del repositorio de ficheros.
	 *
	 * @param idExtDep
	 *            Identificador externo del depósito electrónico.
	 * @param idFich
	 *            Identificador del fichero en el gestor documental.
	 * @return Contenido del fichero.
	 */
	public byte[] getFile(String idRepEcm, String idExtDep, String idFich) {
		byte[] contenido = null;

		try {
			if (StringUtils.isBlank(idExtDep)) {

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

				// Obtener el gestor de ficheros
				IGestorFicheros gestorFicherosInvesdoc = GestorFicherosFactory
						.getConnector(idRepEcm, params);

				contenido = gestorFicherosInvesdoc.retrieveFile(idFich);

			} else {
				// Información del depósito
				DepositoElectronicoVO deposito = getGestorEstructuraDepositoBI()
						.getDepositoElectronicoByIdExt(idExtDep);

				if (deposito != null) {
					// Obtener información de la entidad
					ServiceClient serviceClient = getServiceClient();

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if ((serviceClient != null)
							&& (StringUtils.isNotEmpty(serviceClient
									.getEntity()))) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM,
								serviceClient.getEntity());
					}

					IGestorRepositorio gestorRepositorio = GestorRepositorioFactory
							.getConnector(params);
					gestorRepositorio.setWsdlLocation(deposito.getUri());
					gestorRepositorio.setUser(deposito.getUsuario());
					gestorRepositorio.setPassword(deposito.getClave());
					contenido = gestorRepositorio.getFichero(idFich);

					/*
					 * if(TipoRepositorio.getTipoRepositorio(ArchigestAction.class
					 * ).equalsIgnoreCase("prin")) { WSRepositorioProxy proxy =
					 * new WSRepositorioProxy(deposito.getUri());
					 * proxy.setUsername(deposito.getUsuario());
					 * proxy.setPassword(deposito.getClave());
					 *
					 * contenido = ArrayUtils.toPrimitive(
					 * proxy.getFichero(idFich)); } else {
					 * contenido=WSCustodiaServiceLocator
					 * .getCustodiaService(deposito
					 * .getUri()).getFichero(idFich);
					 *
					 * }
					 */
				}
			}
		} catch (Exception e) {
			logger.error("Error al obtener el fichero con id " + idFich, e);
			throw new FileException("Error al obtener el fichero", e);
		}

		return contenido;
	}

	/**
	 * Guarda el fichero en el repositorio de ficheros.
	 *
	 * @param idRepEcm
	 *            Identificador de la lista de volúmenes
	 * @param docDocumentoExtVO
	 *            Extensión del fichero.
	 * @param content
	 *            Contenido del fichero.
	 */
	public String storeFile(DocDocumentoExtVO docDocumentoExtVO) {
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

			// Obtener el gestor de ficheros
			IGestorFicheros gestorFicheros = GestorFicherosFactory
					.getConnector(docDocumentoExtVO.getIdRepEcm(), params);

			String fileId = gestorFicheros.storeFile(docDocumentoExtVO);

			return fileId;
		} catch (Exception e) {
			logger.error(
					"Error al almacenar el fichero en el repositorio con id "
							+ docDocumentoExtVO.getIdRepEcm(), e);
			throw new FileException("Error al almacenar el fichero", e);
		}
	}

	/**
	 * Elimina el fichero del repositorio de ficheros.
	 *
	 * @param idDeposito
	 *            Identificador del depósito (nulo si es interno).
	 * @param idFich
	 *            Identificador del fichero en el gestor documental.
	 */
	public void deleteFile(String idRepEcm, String idDeposito, String idFich) {
		if (StringUtils.isNotBlank(idDeposito)) {
			try {
				// Información del depósito
				DepositoElectronicoVO deposito = getGestorEstructuraDepositoBI()
						.getDepositoElectronico(idDeposito);

				if (deposito != null) {

					// Obtener información de la entidad
					ServiceClient serviceClient = getServiceClient();

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if ((serviceClient != null)
							&& (StringUtils.isNotEmpty(serviceClient
									.getEntity()))) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM,
								serviceClient.getEntity());
					}

					IGestorRepositorio gestorRepositorio = GestorRepositorioFactory
							.getConnector(params);
					gestorRepositorio.setWsdlLocation(deposito.getUri());
					gestorRepositorio.setUser(deposito.getUsuario());
					gestorRepositorio.setPassword(deposito.getClave());
					gestorRepositorio.eliminaFicheros(new String[] { idFich });

					/*
					 * if(TipoRepositorio.getTipoRepositorio(ArchigestAction.class
					 * ).equalsIgnoreCase("prin")) { WSRepositorioProxy proxy =
					 * new WSRepositorioProxy(deposito.getUri());
					 * proxy.setUsername(deposito.getUsuario());
					 * proxy.setPassword(deposito.getClave());
					 * proxy.eliminaFicheros(new String [] { idFich }); } else {
					 * WSCustodiaServiceLocator
					 * .getCustodiaService(deposito.getUri
					 * ()).eliminaFicheros(new String [] { idFich }); }
					 */

				}
			} catch (Exception e) {
				logger.error("Error al eliminar el fichero con id " + idFich
						+ " en el depósito " + idDeposito, e);
				throw new FileException("Error al eliminar el fichero", e);
			}
		} else
			deleteInternalFile(idRepEcm, idFich);
	}

	/**
	 * Elimina el fichero del repositorio de ficheros.
	 *
	 * @param idFich
	 *            Identificador del fichero en el gestor documental.
	 */
	protected void deleteInternalFile(String idRepEcm, String idFich) {
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

			// Obtener el gestor de ficheros
			IGestorFicheros gestorFicherosInvesdoc = GestorFicherosFactory
					.getConnector(idRepEcm, params);

			gestorFicherosInvesdoc.deleteFile(idFich);
		} catch (IeciTdException e) {
			if (!DbError.EC_NOT_FOUND.equals(e.getErrorCode())) {
				logger.warn("Error al eliminar el fichero con id " + idFich, e);
				throw new FileException("Error al eliminar el fichero", e);
			}
		} catch (Exception e) {
			logger.warn("Error al eliminar el fichero con id " + idFich, e);
			throw new FileException("Error al eliminar el fichero", e);
		}
	}

	protected void checkAcceso(int tipoObjeto, String idObjeto, int tipoAcceso,
			ActionObject accion) {
		Restringible objeto;

		// Obtener la información del objeto
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			objeto = getGestionCuadroClasificacionBI()
					.getElementoCuadroClasificacion(idObjeto);
		else
			objeto = getGestionDescripcionBI().getDescriptor(idObjeto);

		if (objeto != null) {

			// Comprobar permisos genéricos
			checkPermission(accion);

			// Comprobar permisos y auditoría
			if (tipoAcceso == TipoAcceso.CONSULTA) {
				// Comprobar permisos sobre el elemento
				if (!SecurityManagerBase.isAccessAllowed(getServiceClient(),
						tipoAcceso, objeto.getNivelAcceso(),
						objeto.getIdArchivo(), objeto.getIdLCA()))
					throw new DocElectronicosSecurityException(
							"El usuario no puede consultar los documentos electrónicos");
			} else {
				// Comprobar permisos sobre el elemento
				if (!SecurityManagerBase.isAccessAllowed(getServiceClient(),
						tipoAcceso, objeto.getNivelAcceso(),
						objeto.getIdArchivo(), objeto.getIdLCA(),
						AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION))
					throw new DocElectronicosSecurityException(
							"El usuario no puede editar los documentos electrónicos");
			}
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getCountTareasAGestionar()
	 */
	public int getCountTareasAGestionar() {

		// sino tiene permiso de gestion de tareas retorno nulo
		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_TAREAS_CAPTURA))

			return 0;

		return docTCapturaDBEntity
				.fetchCountTareasXEstados(new EstadoTareaCaptura[] { EstadoTareaCaptura.FINALIZADA });
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getTareasAGestionar()
	 */
	public List getTareasAGestionar() {

		// sino tiene permiso de gestion de tareas retorno nulo
		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_TAREAS_CAPTURA))

			return null;

		return docTCapturaDBEntity
				.fetchTareasXEstados(new EstadoTareaCaptura[] { EstadoTareaCaptura.FINALIZADA });
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getCountTareasPendientes()
	 */
	public int getCountTareasPendientes() {
		if (getServiceClient().hasPermission(AppPermissions.CAPTURA_DOCUMENTOS))
			return docTCapturaDBEntity.fetchCountTareasXEstadosYUsuario(
					new EstadoTareaCaptura[] { EstadoTareaCaptura.PENDIENTE,
							EstadoTareaCaptura.CON_ERRORES },
					getServiceClient().getId());
		else
			return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getTareasPendientes()
	 */
	public List getTareasPendientes() {
		if (getServiceClient().hasPermission(AppPermissions.CAPTURA_DOCUMENTOS))
			return docTCapturaDBEntity.fetchTareasXEstadosYUsuario(
					new EstadoTareaCaptura[] { EstadoTareaCaptura.PENDIENTE,
							EstadoTareaCaptura.CON_ERRORES },
					getServiceClient().getId());
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getTarea(java.lang.String)
	 */
	public DocTCapturaVO getTarea(String idTarea) {
		return docTCapturaDBEntity.fetchTareaXId(idTarea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#updateTarea(docelectronicos
	 * .vos.DocTCapturaVO)
	 */
	public DocTCapturaVO updateTarea(DocTCapturaVO tareaVO) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#insertTarea(docelectronicos
	 * .vos.DocTCapturaVO)
	 */
	public DocTCapturaVO insertTarea(DocTCapturaVO newTarea)
			throws DocElectronicosException {
		boolean editadoClasificadoresDocumentales = false;
		boolean objetoEsDescriptor = newTarea.getTipoObj() == TipoObjeto.DESCRIPTOR;
		boolean objetoEsElementoCF = newTarea.getTipoObj() == TipoObjeto.ELEMENTO_CF;
		Locale locale = getServiceClient().getLocale();
		IElementoCuadroClasificacion elementoCuadro = null;

		// Auditoría
		LoggingEvent event = AuditoriaDocumentos.getLogginEvent(this,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA);

		if (objetoEsElementoCF) {

			elementoCuadro = elementoCuadroClasificacionDbEntity
					.getElementoCuadroClasificacion(newTarea.getIdObj());

			// comprobacion
			checkTareaCreableParaUsuario((Restringible) elementoCuadro,
					newTarea.getIdUsrCaptura());

			editadoClasificadoresDocumentales = DBUtils
					.getBooleanValue(elementoCuadro.getEditClfDocs());
			/* idRepEcm = */elementoCuadro.getIdRepEcm();

			newTarea.setTipoObj(elementoCuadro.getTipo());
			newTarea.setCodRefObj(elementoCuadro.getCodReferencia());
			newTarea.setTituloObj(elementoCuadro.getTitulo());

		} else if (objetoEsDescriptor) {
			DescriptorVO descriptor = descriptorDBEntity.getDescriptor(newTarea
					.getIdObj());

			// comprobacion
			checkTareaCreableParaUsuario((Restringible) descriptor,
					newTarea.getIdUsrCaptura());

			editadoClasificadoresDocumentales = DBUtils
					.getBooleanValue(descriptor.getEditClfDocs());
			/* idRepEcm = */descriptor.getIdRepEcm();

			newTarea.setTipoObj(TipoObjeto.DESCRIPTOR_EN_BD);
			newTarea.setCodRefObj(null);

			ListaDescrVO lista = catalogoListaDescriptoresDBEntity
					.getListaDescriptora(descriptor.getIdLista());
			String nombreLista = null;
			if (lista != null)
				nombreLista = lista.getNombre();
			newTarea.setTituloObj(nombreLista + "-" + descriptor.getNombre());

		}

		newTarea.setEstado(EstadoTareaCaptura.PENDIENTE.getValue());
		newTarea.setFechaEstado(DateUtils.getFechaActual());

		iniciarTransaccion();

		// si no se han editado aun no tienen ficha de clas y lista volumenes
		// asociados
		if (!editadoClasificadoresDocumentales) {
			inicializaClasificadoresYRepEcm(newTarea.getIdObj(),
					objetoEsDescriptor ? TipoObjeto.DESCRIPTOR
							: TipoObjeto.ELEMENTO_CF, true);
		}

		docTCapturaDBEntity.insert(newTarea);

		// Detalle de la auditoría
		AuditoriaDocumentos.auditaDetalleInfoTarea(locale, event, newTarea,
				getGestionControlUsusarios());

		commit();

		return newTarea;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#getListasDescriptorasDigitalizables
	 * ()
	 */
	public List getListasDescriptorasDigitalizables() {
		return getServiceRepository().lookupGestionDescripcionBI()
				.getListasDescrByTipoDescriptor(TipoDescriptor.ENTIDAD);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getFondosDigitalizables()
	 */
	public List getFondosDigitalizables() {
		return getServiceRepository().lookupGestionFondosBI()
				.getFondosVigentes();
	}

	public List getElementosDigitalizables(String idFondo, String titulo,
			String codigo, PageInfo pageInfo) throws TooManyResultsException {

		BusquedaElementosVO parametrosBusqueda = new BusquedaElementosVO();
		parametrosBusqueda.setFondo(idFondo);
		parametrosBusqueda.setTitulo(titulo);
		parametrosBusqueda.setCodigo(codigo);
		parametrosBusqueda.setPageInfo(pageInfo);

		GestionCuadroClasificacionBI cuadroBI = getServiceRepository()
				.lookupGestionCuadroClasificacionBI();
		// nivel de unidad documental=>nivels hijos de la serie
		List listaNivelCFVO = cuadroBI.getNivelesByTipo(
				TipoNivelCF.UNIDAD_DOCUMENTAL, null);
		String[] niveles = new String[listaNivelCFVO.size()];
		int i = 0;
		for (Iterator it = listaNivelCFVO.iterator(); it.hasNext();) {
			INivelCFVO nivel = (INivelCFVO) it.next();
			niveles[i] = nivel.getId();
			i++;
		}
		parametrosBusqueda.setNiveles(niveles);

		return getServiceRepository().lookupGestionCuadroClasificacionBI()
				.getElementos(parametrosBusqueda);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#getDescriptoresDigitalizables
	 * (java.lang.String, java.lang.String)
	 */
	public List getDescriptoresDigitalizables(String idLista, String titulo,
			PageInfo pageInfo) throws TooManyResultsException {

		BusquedaGeneralAutVO parametrosBusqueda = new BusquedaGeneralAutVO();
		if (idLista != null)
			parametrosBusqueda.setListasDescriptoras(new String[] { idLista });
		// else{
		// List listasEntidad =
		// catalogoListaDescriptoresDBEntity.getListasDescriptorasByTipoDescriptor(TipoDescriptor.ENTIDAD);
		// if (!CollectionUtils.isEmpty(listasEntidad)){
		// String[]idsListas = new String[listasEntidad.size()];
		// int i=0;
		// for (Iterator itListas = listasEntidad.iterator();
		// itListas.hasNext();) {
		// ListaDescrVO unaLista = (ListaDescrVO) itListas.next();
		// idsListas[i] = unaLista.getId();
		// i++;
		// }
		// parametrosBusqueda.setListasDescriptoras(idsListas);
		// }
		// }
		parametrosBusqueda.setNombre(titulo);
		parametrosBusqueda.setPageInfo(pageInfo);

		return getServiceRepository().lookupGestionDescripcionBI()
				.searchAutoridades(parametrosBusqueda);
	}

	public void checkCapturaFinalizable(String idObjeto, int tipoObjeto,
			int estado) throws DocElectronicosException {

		if (!(estado == EstadoTareaCaptura.PENDIENTE.getValue() || estado == EstadoTareaCaptura.CON_ERRORES
				.getValue()))
			throw new DocElectronicosException(
					DocElectronicosException.XSOLO_ES_POSIBLE_FINALIZAR_TAREAS_EN_ESTADO_PENDIENTE_O_CON_ERRORES);

		if (estado == EstadoTareaCaptura.CON_ERRORES.getValue()) {
			if (TipoObjeto.valueOf(tipoObjeto) == TipoObjeto.DESCRIPTOR) {
				List docs = docDocumentoDescrDBEntity.getDocumentos(idObjeto,
						new int[] { EstadoDocumento.NO_VALIDO });
				if (docs != null && docs.size() > 0)
					throw new DocElectronicosException(
							DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_DOC_EN_ESTADO_NO_VALIDO);
				List clfs = docClasifDescrDBEntity.getClasificadores(idObjeto,
						new int[] { EstadoClasificador.NO_VALIDO });
				if (clfs != null && clfs.size() > 0)
					throw new DocElectronicosException(
							DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_CL_EN_ESTADO_NO_VALIDO);
			} else {
				List docs = docDocumentoCFDBEntity.getDocumentos(idObjeto,
						new int[] { EstadoDocumento.NO_VALIDO });
				if (docs != null && docs.size() > 0)
					throw new DocElectronicosException(
							DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_DOC_EN_ESTADO_NO_VALIDO);
				List clfs = docClasifCFDBEntity.getClasificadores(idObjeto,
						new int[] { EstadoClasificador.NO_VALIDO });
				if (clfs != null && clfs.size() > 0)
					throw new DocElectronicosException(
							DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_CL_EN_ESTADO_NO_VALIDO);
			}
		}

	}

	public void checkPermisosParaFinalizarTarea(DocTCapturaVO tarea)
			throws DocElectronicosException {
		checkPermission(DocumentosElectronicosSecurityManager.CAPTURA_DOCUMENTOS_ACTION);
		if (!tarea.getIdUsrCaptura().equalsIgnoreCase(
				getServiceClient().getId()))
			throw new DocElectronicosException(
					DocElectronicosException.XNO_TENER_PERMISOS_SOBRE_TAREA);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#finalizarCaptura(java.lang.
	 * String)
	 */
	public void finalizarCaptura(String idTarea, String observaciones)
			throws DocElectronicosException {
		DocTCapturaVO tarea = docTCapturaDBEntity.fetchTareaXId(idTarea);
		Locale locale = getServiceClient().getLocale();

		checkPermisosParaFinalizarTarea(tarea);

		checkCapturaFinalizable(tarea.getIdObj(), tarea.getTipoObj(),
				tarea.getEstado());

		// Auditoría
		LoggingEvent event = AuditoriaDocumentos
				.getLogginEvent(
						this,
						ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA);

		int estadoAnterior = tarea.getEstado();

		if (getServiceClient().hasAnyPermission(
				new String[] { AppPermissions.ADMINISTRACION_TAREAS_CAPTURA,
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA }))
			tarea.setEstado(EstadoTareaCaptura.VALIDADA.getValue());
		else
			tarea.setEstado(EstadoTareaCaptura.FINALIZADA.getValue());

		tarea.setFechaEstado(DateUtils.getFechaActual());
		tarea.setObservaciones(observaciones);
		tarea.setMotivoError(null);

		docTCapturaDBEntity.update(tarea);

		// Detalle de la auditoría
		AuditoriaDocumentos.auditaDetalleCambioEstadoTarea(locale, event,
				tarea, estadoAnterior, tarea.getEstado());

	}

	public void checkValidacionCapturaFinalizable(DocTCapturaVO tarea)
			throws DocElectronicosException {
		checkTodosElementosNoEstadoSinValidar(tarea);
		if (tarea.getEstado() != EstadoTareaCaptura.FINALIZADA.getValue())
			throw new DocElectronicosException(
					DocElectronicosException.XSOLO_ES_POSIBLE_FINALIZAR_VALIDACION_TAREA_FINALIZADA);
	}

	/**
	 * @param tarea
	 * @throws DocElectronicosException
	 */
	private void checkTodosElementosNoEstadoSinValidar(DocTCapturaVO tarea)
			throws DocElectronicosException {
		// comprobar que no exista doc o clas con estado no sin validar
		if (TipoObjeto.DESCRIPTOR == TipoObjeto.valueOf(tarea.getTipoObj())) {
			List docsSinValidar = docDocumentoDescrDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(docsSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsSinValidar = docClasifDescrDBEntity
					.getClasificadores(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(clsSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

		} else {
			List docsSinValidar = docDocumentoCFDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(docsSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsSinValidar = docClasifCFDBEntity
					.getClasificadores(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(clsSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

		}
	}

	protected void checkTodosElementosEstadoSinValidar(DocTCapturaVO tarea)
			throws DocElectronicosException {
		// comprobar que no exista doc o clas con estado no sin validar
		if (TipoObjeto.DESCRIPTOR == TipoObjeto.valueOf(tarea.getTipoObj())) {
			List docsNoEstanSinValidar = docDocumentoDescrDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.NO_VALIDO,
									EstadoDocumento.PUBLICADO,
									EstadoDocumento.VALIDADO });
			if (!CollectionUtils.isEmpty(docsNoEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsNoEstanSinValidar = docClasifDescrDBEntity
					.getClasificadores(tarea.getIdObj(), new int[] {
							EstadoClasificador.NO_VALIDO,
							EstadoClasificador.PUBLICADO,
							EstadoClasificador.VALIDADO });
			if (!CollectionUtils.isEmpty(clsNoEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

		} else {
			List docsNoEstanSinValidar = docDocumentoCFDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.NO_VALIDO,
									EstadoDocumento.PUBLICADO,
									EstadoDocumento.VALIDADO });
			if (!CollectionUtils.isEmpty(docsNoEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsNoEstanSinValidar = docClasifCFDBEntity.getClasificadores(
					tarea.getIdObj(), new int[] { EstadoClasificador.NO_VALIDO,
							EstadoClasificador.PUBLICADO,
							EstadoClasificador.VALIDADO });
			if (!CollectionUtils.isEmpty(clsNoEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

		}
	}

	private void checkNoElementosEstadoSinValidar(DocTCapturaVO tarea)
			throws DocElectronicosException {
		// comprobar que no existan doc o clas con estado 'sin validar'
		if (TipoObjeto.DESCRIPTOR == TipoObjeto.valueOf(tarea.getTipoObj())) {
			List docsEstanSinValidar = docDocumentoDescrDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(docsEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsEstanSinValidar = docClasifDescrDBEntity.getClasificadores(
					tarea.getIdObj(),
					new int[] { EstadoClasificador.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(clsEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);
		} else {
			List docsEstanSinValidar = docDocumentoCFDBEntity
					.getDocumentos(tarea.getIdObj(),
							new int[] { EstadoDocumento.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(docsEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

			List clsEstanSinValidar = docClasifCFDBEntity.getClasificadores(
					tarea.getIdObj(),
					new int[] { EstadoClasificador.SIN_VALIDAR });
			if (!CollectionUtils.isEmpty(clsEstanSinValidar))
				throw new DocElectronicosException(
						DocElectronicosException.XELEMENTOS_SIN_VALIDAR);

		}
	}

	public void finalizarValidacionTarea(String idTarea, String motivoError)
			throws DocElectronicosException {

		DocTCapturaVO tarea = docTCapturaDBEntity.fetchTareaXId(idTarea);
		boolean isObjetoDescriptor = TipoObjeto.DESCRIPTOR == TipoObjeto
				.valueOf(tarea.getTipoObj());

		checkPermisosValidarTarea();

		checkValidacionCapturaFinalizable(tarea);

		// Auditoría
		LoggingEvent event = AuditoriaDocumentos
				.getLogginEvent(
						this,
						ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR);

		// existe algun elemento no valido (clasificadores o documentos)

		// publicar elementos validos
		iniciarTransaccion();

		int estadoAnterior = tarea.getEstado();

		// si hay elementos no validos guardo tarea con errores
		if (tareaTieneElementosNoValidos(tarea.getIdObj(), tarea.getTipoObj())) {
			tarea.setEstado(EstadoTareaCaptura.CON_ERRORES.getValue());
			tarea.setMotivoError(motivoError);
		} else {
			tarea.setEstado(EstadoTareaCaptura.VALIDADA.getValue());
			// publicar elementos con estado valido
			if (isObjetoDescriptor) {
				docDocumentoDescrDBEntity.update(tarea.getIdObj(),
						new int[] { EstadoDocumento.VALIDADO },
						EstadoDocumento.PUBLICADO);
				docClasifDescrDBEntity.update(tarea.getIdObj(),
						new int[] { EstadoClasificador.VALIDADO },
						EstadoDocumento.PUBLICADO);

			} else {
				docDocumentoCFDBEntity.update(tarea.getIdObj(),
						new int[] { EstadoDocumento.VALIDADO },
						EstadoDocumento.PUBLICADO);

				docClasifCFDBEntity.update(tarea.getIdObj(),
						new int[] { EstadoClasificador.VALIDADO },
						EstadoDocumento.PUBLICADO);
			}
		}

		tarea.setFechaEstado(DateUtils.getFechaActual());

		docTCapturaDBEntity.update(tarea);

		// Detalle de la auditoría
		Locale locale = getServiceClient().getLocale();
		AuditoriaDocumentos.auditaDetalleCambioEstadoTarea(locale, event,
				tarea, estadoAnterior, tarea.getEstado());

		commit();

	}

	public void checkPermisosEliminarTarea(DocTCapturaVO tarea) {
		checkPermission(DocumentosElectronicosSecurityManager.ADMINISTRACION_TAREAS_CAPTURA_ACTION);
	}

	public void checkPermisosValidarTarea() {
		checkPermission(DocumentosElectronicosSecurityManager.ADMINISTRACION_TAREAS_CAPTURA_ACTION);
	}

	public void checkTareaEliminable(DocTCapturaVO tarea)
			throws DocElectronicosException {
		// tarea en estado pendiente
		if (tarea.getEstado() != EstadoTareaCaptura.PENDIENTE.getValue())
			throw new DocElectronicosException(
					DocElectronicosException.XSOLO_ES_POSIBLE_ELIMINAR_TAREAS_ES_ESTADO_PENDIENTE);

		// tarea sin cls ni doc sin validar
		// checkTodosElementosEstadoSinValidar(tarea);
		checkNoElementosEstadoSinValidar(tarea);
	}

	public void eliminarTareaCaptura(String idTarea)
			throws DocElectronicosException {
		DocTCapturaVO tarea = docTCapturaDBEntity.fetchTareaXId(idTarea);

		// Auditoría
		LoggingEvent event = AuditoriaDocumentos.getLogginEvent(this,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA);

		checkPermisosEliminarTarea(tarea);

		checkTareaEliminable(tarea);

		docTCapturaDBEntity.removeXId(tarea.getId());

		// Detalle de la auditoría
		Locale locale = getServiceClient().getLocale();
		AuditoriaDocumentos.auditaDetalleInfoTarea(locale, event, tarea,
				getGestionControlUsusarios());

	}

	private void checkPermisosModificarCaptura(DocTCapturaVO tarea)
			throws DocElectronicosException {
		checkPermission(DocumentosElectronicosSecurityManager.CAPTURA_DOCUMENTOS_ACTION);
		if (!tarea.getIdUsrCaptura().equalsIgnoreCase(
				getServiceClient().getId()))
			throw new DocElectronicosException(
					DocElectronicosException.XNO_TENER_PERMISOS_SOBRE_TAREA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#modificarEstadoClasificador
	 * (java.lang.String, docelectronicos.model.EstadoClasificador)
	 */
	public void validarClasificador(DocTCapturaVO tarea, String[] idClasificador)
			throws DocElectronicosException {
		checkPermisosValidarTarea();
		checkCapturaValidable(tarea);
		iniciarTransaccion();
		int tipoObjeto = tarea.getTipoObj();
		for (int i = 0; i < idClasificador.length; i++) {
			cambiarEstadoClasificador(tipoObjeto, idClasificador[i],
					EstadoClasificador.VALIDADO);
		}
		commit();
	}

	public void invalidarClasificador(DocTCapturaVO tarea,
			String[] idClasificador) throws DocElectronicosException {
		checkPermisosValidarTarea();
		checkCapturaValidable(tarea);
		iniciarTransaccion();
		int tipoObjeto = tarea.getTipoObj();
		for (int i = 0; i < idClasificador.length; i++) {
			cambiarEstadoClasificador(tipoObjeto, idClasificador[i],
					EstadoClasificador.NO_VALIDO);
		}
		commit();
	}

	private void cambiarEstadoClasificador(int tipoObjeto,
			String idClasificador, int newEstado)
			throws DocElectronicosException {
		boolean isDescriptor = TipoObjeto.valueOf(tipoObjeto) == TipoObjeto.DESCRIPTOR;
		if (isDescriptor) {
			DocClasificadorVO clas = docClasifDescrDBEntity
					.getClasificador(idClasificador);
			clas.setEstado(newEstado);
			docClasifDescrDBEntity.updateClasificador(clas);
		} else {
			DocClasificadorVO clas = docClasifCFDBEntity
					.getClasificador(idClasificador);
			clas.setEstado(newEstado);
			docClasifCFDBEntity.updateClasificador(clas);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#modificarEstadoDocumento(java
	 * .lang.String, docelectronicos.model.EstadoDocumento)
	 */
	public void validarDocumento(DocTCapturaVO tarea, String[] idDocumento)
			throws DocElectronicosException {
		checkPermisosValidarTarea();
		checkCapturaValidable(tarea);
		int tipoObjeto = tarea.getTipoObj();
		for (int i = 0; i < idDocumento.length; i++) {
			cambiarEstadoDocumento(tipoObjeto, idDocumento[i],
					EstadoDocumento.VALIDADO);
		}
	}

	public void invalidarDocumento(DocTCapturaVO tarea, String[] idDocumento)
			throws DocElectronicosException {
		checkPermisosValidarTarea();
		checkCapturaValidable(tarea);
		int tipoObjeto = tarea.getTipoObj();
		for (int i = 0; i < idDocumento.length; i++) {
			cambiarEstadoDocumento(tipoObjeto, idDocumento[i],
					EstadoDocumento.NO_VALIDO);
		}
	}

	private void cambiarEstadoDocumento(int tipoObjeto, String idDocumento,
			int newEstado) throws DocElectronicosException {

		boolean isDescriptor = (TipoObjeto.valueOf(tipoObjeto) == TipoObjeto.DESCRIPTOR);
		if (isDescriptor) {
			DocDocumentoVO doc = docDocumentoDescrDBEntity
					.getDocumento(idDocumento);

			checkCambiarEstadoValidacion(doc.getEstado(), newEstado);

			doc.setEstado(newEstado);
			docDocumentoDescrDBEntity.updateDocumento(doc);
		} else {
			DocDocumentoVO doc = docDocumentoCFDBEntity
					.getDocumento(idDocumento);

			checkCambiarEstadoValidacion(doc.getEstado(), newEstado);

			doc.setEstado(newEstado);
			docDocumentoCFDBEntity.updateDocumento(doc);
		}
	}

	/**
	 * @param estado
	 * @param newEstado
	 */
	public void checkCambiarEstadoValidacion(int oldEstado, int newEstado)
			throws DocElectronicosException {
		if (newEstado == EstadoClasificador.PUBLICADO
				|| newEstado == EstadoDocumento.PUBLICADO)
			throw new DocElectronicosException(
					DocElectronicosException.XNO_ES_POSIBLE_CAMBIAR_ESTADO_A_ELEMENTOS_PUBLICADOS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#tareaTieneElementosNoValidos
	 * (java.lang.String)
	 */
	public boolean tareaTieneElementosNoValidos(String idObjeto, int tipoObjeto) {
		List docsNoValidos = null;
		boolean isObjetoDescriptor = (TipoObjeto.valueOf(tipoObjeto) == TipoObjeto.DESCRIPTOR);
		if (isObjetoDescriptor) {
			docsNoValidos = docDocumentoDescrDBEntity.getDocumentos(idObjeto,
					new int[] { EstadoDocumento.NO_VALIDO });
		} else {
			docsNoValidos = docDocumentoCFDBEntity.getDocumentos(idObjeto,
					new int[] { EstadoDocumento.NO_VALIDO });
		}
		List clasNoValidos = null;
		if (isObjetoDescriptor) {
			clasNoValidos = docClasifDescrDBEntity.getClasificadores(idObjeto,
					new int[] { EstadoClasificador.NO_VALIDO });
		} else {
			clasNoValidos = docClasifCFDBEntity.getClasificadores(idObjeto,
					new int[] { EstadoClasificador.NO_VALIDO });
		}
		if (!CollectionUtils.isEmpty(docsNoValidos)
				|| !CollectionUtils.isEmpty(clasNoValidos))
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#checkCapturaValidable(
	 * docelectronicos.vos.DocTCapturaVO)
	 */
	public void checkCapturaValidable(DocTCapturaVO tarea)
			throws DocElectronicosException {
		if (tarea.getEstado() != EstadoTareaCaptura.FINALIZADA.getValue())
			throw new DocElectronicosException(
					DocElectronicosException.XPARA_VALIDAR_LA_TAREA_DEBE_ESTAR_EN_ESTADO_FINALIZADA_CAPTURA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#checkTareaModificablePorElementos
	 * (docelectronicos.actions.DocTCapturaPO)
	 */
	public void checkTareaModificablePorElementos(DocTCapturaVO capturaVO)
			throws DocElectronicosException {
		checkPermisosModificarCaptura(capturaVO);
		if (capturaVO.getEstado() == EstadoTareaCaptura.FINALIZADA.getValue()
				|| capturaVO.getEstado() == EstadoTareaCaptura.VALIDADA
						.getValue())
			throw new DocElectronicosException(
					DocElectronicosException.XNO_ES_POSIBLE_MODIFICAR_LA_TAREA_EN_ESTADO_FINALIZADA);
	}

	public void eliminarTareas(String idElemento, int tipoElemento) {
		docTCapturaDBEntity.removeTareasXIdElementoYTipoObj(idElemento,
				tipoElemento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#busquedaTareas(docelectronicos
	 * .vos.BusquedaTareasVO)
	 */
	public List busquedaTareas(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException {
		// Comprobar permisos
		checkPermission(DocumentosElectronicosSecurityManager.ADMINISTRACION_TAREAS_CAPTURA_ACTION);
		return docTCapturaDBEntity.getTareas(busquedaVO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getEstadosTareas()
	 */
	public List<TypeDescVO> getEstadosTareas() {
		// Comprobar permisos
		checkPermission(DocumentosElectronicosSecurityManager.ADMINISTRACION_TAREAS_CAPTURA_ACTION);
		Locale locale = getServiceClient().getLocale();

		List<TypeDescVO> estados = new ArrayList<TypeDescVO>();
		estados.add(new TypeDescVO(
				EstadoTareaCaptura.PENDIENTE.getValue(),
				Messages.getString(
						DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO
								+ ".1", locale)));
		estados.add(new TypeDescVO(
				EstadoTareaCaptura.FINALIZADA.getValue(),
				Messages.getString(
						DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO
								+ ".2", locale)));
		estados.add(new TypeDescVO(
				EstadoTareaCaptura.CON_ERRORES.getValue(),
				Messages.getString(
						DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO
								+ ".3", locale)));
		estados.add(new TypeDescVO(
				EstadoTareaCaptura.VALIDADA.getValue(),
				Messages.getString(
						DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO
								+ ".4", locale)));

		return estados;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#getTareasCedibles(docelectronicos
	 * .vos.BusquedaTareasVO)
	 */
	public List getTareasCedibles(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException {
		return docTCapturaDBEntity.getTareas(busquedaVO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#asignarTareaACapturador(java
	 * .lang.String[], java.lang.String)
	 */
	public void asignarTareaACapturador(String[] idTareas, String idCapturador)
			throws DocElectronicosException {
		iniciarTransaccion();
		docTCapturaDBEntity.updateGestor(idTareas, idCapturador);
		commit();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#getTareasXIds(java.lang.String
	 * [])
	 */
	public List getTareasXIds(String[] idsTareas) {
		return docTCapturaDBEntity.fetchTareasXIds(idsTareas);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#checkTareaCreable(java.lang
	 * .String, int)
	 */
	public void checkTareaCreableSobreElemento(String id, int tipoObjeto)
			throws DocElectronicosException {
		// comprobar que no exista una tarea sobre este elemento
		List tareas = docTCapturaDBEntity.fetchXIdElementoYTipoObj(id,
				tipoObjeto);
		if (!CollectionUtils.isEmpty(tareas)) {
			for (Iterator itTareas = tareas.iterator(); itTareas.hasNext();) {
				DocTCapturaVO aTarea = (DocTCapturaVO) itTareas.next();
				if (aTarea.getEstado() != EstadoTareaCaptura.VALIDADA
						.getValue())
					throw new DocElectronicosException(
							DocElectronicosException.XESTE_ELEMENTO_AUN_TIENE_UNA_CAPTURA_CON_VALIDACION_NO_FINALIZADA);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDocumentosElectronicosBI#checkTareaParaUsuario(java.
	 * lang.String, int)
	 */
	public void checkTareaCreableParaUsuario(Restringible restringibleObj,
			String idUsuario) throws DocElectronicosException {

		// Datos de usuario
		UsuarioVO usuario = getGestionControlUsusarios().getUsuario(idUsuario);
		AppUser user = null;
		ServiceClient serviceClient = getServiceClient();
		String entity = null;
		if (serviceClient != null)
			entity = serviceClient.getEntity();

		try {
			user = AppUserRIFactory.createAppUserRI().getAppUser(
					usuario.getIdUsrsExtGestor(), usuario.getTipo(),
					Constants.STRING_EMPTY, Constants.STRING_EMPTY, entity,
					null);
			serviceClient = ServiceClient.create(user);

		} catch (AppUserException e) {
			throw new DocElectronicosException(
					DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_EL_USUARIO_NO_TIENE_ACCESO_AL_ELEMENTO_SOBRE_EL_Q_SE_REALIZA_LA_TAREA);
		}

		// comprobar acceso de edicion sobre el elemento
		boolean acesoPermitido = SecurityManagerBase.isAccessAllowed(
				serviceClient, TipoAcceso.EDICION,
				restringibleObj.getNivelAcceso(),
				restringibleObj.getIdArchivo(), restringibleObj.getIdLCA(),
				AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION);

		if (!acesoPermitido)
			throw new DocElectronicosException(
					DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_EL_USUARIO_NO_TIENE_ACCESO_AL_ELEMENTO_SOBRE_EL_Q_SE_REALIZA_LA_TAREA);

	}

	/**
	 * Obtiene la información del fichero del documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
	 * @return Información del fichero del documento electrónico.
	 */
	public InfoFicheroVO getInfoFichero(DocDocumentoVO documento) {

		InfoFicheroVO infoFichero = null;

		if (StringUtils.isNotBlank(documento.getIdExtDeposito())) {

			// Obtener la información del depósito electrónico
			DepositoElectronicoVO deposito = getGestorEstructuraDepositoBI()
					.getDepositoElectronicoByIdExt(documento.getIdExtDeposito());

			if (deposito != null && StringUtils.isNotBlank(deposito.getUri())) {
				try {
					// Obtener información de la entidad
					ServiceClient serviceClient = getServiceClient();

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if ((serviceClient != null)
							&& (StringUtils.isNotEmpty(serviceClient
									.getEntity()))) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM,
								serviceClient.getEntity());
					}

					IGestorRepositorio gestorRepositorio = GestorRepositorioFactory
							.getConnector(params);
					gestorRepositorio.setWsdlLocation(deposito.getUri());
					gestorRepositorio.setUser(deposito.getUsuario());
					gestorRepositorio.setPassword(deposito.getClave());
					infoFichero = gestorRepositorio.getInfoFichero(documento
							.getIdFich());

					// Obtener la información del fichero
					// if(TipoRepositorio.getTipoRepositorio().equalsIgnoreCase("archigest"))
					/*
					 * if(TipoRepositorio.getTipoRepositorio(ArchigestAction.class
					 * ).equalsIgnoreCase("prin")) { WSRepositorioProxy proxy =
					 * new WSRepositorioProxy(deposito.getUri());
					 * proxy.setUsername(deposito.getUsuario());
					 * proxy.setPassword(deposito.getClave()); infoFichero =
					 * proxy.getInfoFichero(documento.getIdFich()); } else {
					 * se.repositorios.InfoFicheroVO
					 * infoFicheroAux=WSCustodiaServiceLocator
					 * .getCustodiaService
					 * (deposito.getUri()).getInfoFichero(documento
					 * .getIdFich()); se.repositorios.FirmaVO[]
					 * firmasAux=infoFicheroAux.getFirmas();
					 * se.repositorios.archigest.FirmaVO[] firmas=null;
					 * if(firmasAux!=null) { firmas=new
					 * se.repositorios.archigest.FirmaVO[firmasAux.length];
					 * for(int i=0;i<firmasAux.length;i++) {
					 * se.repositorios.FirmaVO firmaAux=firmasAux[i];
					 * se.repositorios.archigest.FirmaVO firma=new
					 * se.repositorios.archigest.FirmaVO();
					 * firma.setAutenticada(new
					 * Boolean(firmaAux.isAutenticada()));
					 * firma.setAutor(firmaAux.getAutor()); firmas[i]=firma; } }
					 * infoFichero=new
					 * se.repositorios.archigest.InfoFicheroVO(infoFicheroAux
					 * .getFechaAlta(),firmas,infoFicheroAux.getNombre()); }
					 */
				} catch (Exception e) {
					logger.error("Error al leer la información del documento electrónico '"
							+ documento.getNombre()
							+ "' ["
							+ documento.getId()
							+ "]");
				}
			}
		}

		return infoFichero;
	}

	/**
	 * Obtiene la lista de documentos de un objeto visibles por el usuario.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @return Lista de documentos.
	 */
	public List getDocumentosVisiblesDesdeCuadro(int tipoObjeto, String id) {
		int[] estados = null;

		if (!getServiceClient().hasPermission(
				AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION))
			estados = new int[] { EstadoDocumento.PUBLICADO };

		return getDocumentos(tipoObjeto, id, estados);
	}

	/**
	 * Comprueba si un objeto tiene documentos asociados
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre
	 * @return <b>true</b> si el objeto tiene docuementos hijos y <b>false</b>
	 *         en caso contrario
	 */
	public boolean tieneDocumentosAsociados(int tipoObjeto, String id,
			String idClfPadre) {
		if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			return docDocumentoCFDBEntity.getCountDocumentosByIdClfPadre(id,
					idClfPadre) > 0;
		else
			// if (tipoObjeto == TipoObjeto.DESCRIPTOR)
			return docDocumentoDescrDBEntity.countNumDocumentos(id) > 0;

	}

	/**
	 * Comprueba si un objeto tiene documentos asociados
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre
	 * @return <b>true</b> si el objeto tiene docuementos hijos y <b>false</b>
	 *         en caso contrario
	 */
	public boolean tieneDescendientes(int tipoObjeto, String idObjeto,
			String idClfPadre) {

		int nofDocs = 0, nofClasif = 0;

		// comprobar si existe doc o clas descendiente del padre indicado
		if (TipoObjeto.DESCRIPTOR == tipoObjeto) {
			nofDocs = docDocumentoDescrDBEntity.getCountDocumentosByIdClfPadre(
					idObjeto, idClfPadre);
			nofClasif = docClasifDescrDBEntity
					.getCountClasificadoresByIdClfPadre(idObjeto, idClfPadre);
		} else {
			nofDocs = docDocumentoCFDBEntity.getCountDocumentosByIdClfPadre(
					idObjeto, idClfPadre);
			nofClasif = docClasifCFDBEntity.getCountClasificadoresByIdClfPadre(
					idObjeto, idClfPadre);
		}

		if (nofDocs + nofClasif > 0)
			return true;
		else
			return false;
	}

	// ========================================================================
	// BÚSQUEDA EN CONTENIDOS DE FICHEROS
	// ========================================================================

	/**
	 * Obtiene la lista de identificadores de fichero en el sistema de
	 * almacenamiento que contienen la cadena pasada como parámetro y que están
	 * dentro de la lista de ficheros asociados los ids de elementos que se
	 * pasan como parámetro
	 *
	 * @return lista de identificadores de fichero
	 */
	public List<String> buscarFicherosXContenido(String cadenaBusqueda,
			List idsElementos) {
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

		List<String> idsList = null, idsDocsConds = null;
		Map mapDocsElems = null, mapElemsDocs = null;

		IGestorRepositoriosECM gestorRepositoriosECM = GestorRepositoriosECMFactory
				.getGestorRepositoriosECM(params);
		List<IRepositorioEcmVO> idsRepositorios = gestorRepositoriosECM
				.getRepositoriosEcmInternos();

		if (ListUtils.isNotEmpty(idsRepositorios)) {
			try {

				// Obtener la lista de ids de ficheros electrónicos asociados a
				// los elementos del cuadro
				mapElemsDocs = docDocumentoCFDBEntity
						.getDocumentos(idsElementos);

				// Transformar el map idElemento: {lista idsDocumentos} en un
				// map idDocumento:idElemento para después manejarlo mejor
				if (mapElemsDocs != null && mapElemsDocs.size() > 0) {
					mapDocsElems = new HashMap();
					idsDocsConds = new ArrayList<String>();
					Iterator it = mapElemsDocs.entrySet().iterator();
					while (it.hasNext()) {
						Entry entry = (Entry) it.next();
						ArrayList entryList = (ArrayList) entry.getValue();
						Iterator itList = entryList.iterator();
						while (itList.hasNext()) {
							String idDoc = (String) itList.next();
							mapDocsElems.put(idDoc, entry.getKey());
							idsDocsConds.add(idDoc);
						}
					}
				}

				for (Iterator iterator = idsRepositorios.iterator(); iterator
						.hasNext();) {
					IRepositorioEcmVO iRepositorioEcmVO = (IRepositorioEcmVO) iterator
							.next();

					// Obtener el gestor de ficheros
					IGestorFicheros gestorFicheros = GestorFicherosFactory
							.getConnector(iRepositorioEcmVO.getId(), params);
					idsDocsConds = gestorFicheros.findFileByContent(
							cadenaBusqueda, idsDocsConds);

					if (idsDocsConds != null && idsDocsConds.size() > 0) {
						idsList = new ArrayList<String>();
						Iterator<String> it = idsDocsConds.iterator();
						while (it.hasNext()) {
							String idDoc = it.next();
							String idElem = (String) mapDocsElems.get(idDoc);
							idsList.add(idElem);
						}
					}
				}

			} catch (Exception e) {
				logger.error(
						"Error al buscar en contenido de ficheros la cadena: "
								+ cadenaBusqueda, e);
				throw new FileException(e);
			}
		}

		return idsList;
	}

	/**
	 * Se encarga de cargar el properties con los campos que debe rellenar para
	 * que aparezca en los documentos de la ficha a la hora de añadir y eliminar
	 * documentos a la unidad documental.
	 *
	 * @param documento
	 * @param request
	 * @throws Exception
	 */
	private void agregarCamposDocumento(DocDocumentoExtVO documento)
			throws Exception {
		Properties campos = CargarCamposDocumento.getInstance().cargarCampos();
		if (campos.containsKey(DocDocumentoVO.campoIdTablaDesc)) {
			String idTablaDesc = campos
					.getProperty(DocDocumentoVO.campoIdTablaDesc);
			List camposTabla = getCamposDatoXIdTabla(idTablaDesc);
			if (!camposTabla.isEmpty()) {
				String idsCampos[] = new String[camposTabla.size()];
				int j = 0;
				for (Iterator iter = camposTabla.iterator(); iter.hasNext();) {
					idsCampos[j++] = ((CampoDatoVO) iter.next()).getId();
				}
				int maxOrden = campoTablaDbEntity.getMaxOrdenTablaDescripcion(
						documento.getIdObjeto(), idsCampos);
				int orden = maxOrden + 1;
				for (int i = 0; i < DocDocumentoVO.camposDocumento.length; i++) {
					String key = DocDocumentoVO.camposDocumento[i];
					if (campos.containsKey(key)) {
						String idCampo = campos.getProperty(key);
						PropertyDescriptor propiedad = BeanUtils
								.getPropertyDescriptor(DocDocumentoExtVO.class,
										key);
						if (propiedad != null) {
							Method metodo = propiedad.getReadMethod();
							if (metodo != null) {
								String value = (String) metodo.invoke(
										documento, null);
								insertarCampoDatoDocumento(
										documento.getIdObjeto(), idCampo,
										value, orden);
							} else {
								throw new Exception(
										"Error: No se encontro ningun metodo con ese nombre en la clase:"
												+ documento.getClass());
							}
						} else {
							throw new Exception(
									"Error: No se encontro ninguna propiedad en DocDocumentoVO que coincida con la clave:"
											+ key);
						}
					} else {
						throw new Exception(
								"Error: No se encontro ningun campo en el properties que coincida con la clave:"
										+ key);
					}
				}
			}
		} else {
			throw new Exception(
					"Error: No se encontro el Id de tabla descripcion dentro del fichero de propiedades.");
		}
	}

	/**
	 * Se encarga de eliminar la descripcion de los campos descritos en
	 * DocDocumentoVO del documento asociado que se va a borrar de los
	 * documentos de la unidad documental.
	 *
	 * @param documento
	 * @param request
	 * @throws Exception
	 */
	private void eliminarCamposDocumento(DocDocumentoVO documento)
			throws Exception {
		Properties campos = CargarCamposDocumento.getInstance().cargarCampos();
		if (campos.containsKey(DocDocumentoVO.campoIdInterno)) {
			String idCampoInterno = campos
					.getProperty(DocDocumentoVO.campoIdInterno);

			// Obtenemos el orden del campo definido por el idInterno
			int orden = getOrdenCampoTabla(documento.getIdObjeto(),
					idCampoInterno, documento.getIdFich());

			// Obtenemos todos los campos de la tabla de descripcion definida en
			// el fichero properties
			String idTablaDesc = campos
					.getProperty(DocDocumentoVO.campoIdTablaDesc);
			List camposTabla = getCamposDatoXIdTabla(idTablaDesc);
			// Eliminamos de las tablas de campos todos los valores que
			// coincidan con el IdElementoCF, IdCampo y el ORDEN
			eliminarCampoDatoDocumento(camposTabla, documento.getIdObjeto(),
					orden);
		} else {
			throw new Exception(
					"Error: No se encontro el Id Interno dentro del fichero de propiedades.");
		}
	}

	/**
	 * Se encarga de actualizar la descripcion de los campos descritos en
	 * DocDocumentoVO del documento que se va actualizar
	 *
	 * @param documento
	 * @param idFich
	 * @throws Exception
	 */
	private void actualizarCamposDocumento(DocDocumentoVO documento,
			String idFich) throws Exception {
		Properties campos = CargarCamposDocumento.getInstance().cargarCampos();
		if (campos.containsKey(DocDocumentoVO.campoIdInterno)) {
			String idCampoInterno = campos
					.getProperty(DocDocumentoVO.campoIdInterno);

			// Obtenemos el orden del campo definido por el idInterno
			int orden = 0;
			if (StringUtils.isNotEmpty(idFich)) {
				orden = getOrdenCampoTabla(documento.getIdObjeto(),
						idCampoInterno, idFich);
			} else {
				orden = getOrdenCampoTabla(documento.getIdObjeto(),
						idCampoInterno, documento.getIdFich());
			}

			for (int i = 0; i < DocDocumentoVO.camposDocumento.length; i++) {
				String key = DocDocumentoVO.camposDocumento[i];
				if (campos.containsKey(key)) {
					String idCampo = campos.getProperty(key);
					PropertyDescriptor propiedad = BeanUtils
							.getPropertyDescriptor(DocDocumentoExtVO.class, key);
					if (propiedad != null) {
						Method metodo = propiedad.getReadMethod();
						if (metodo != null) {
							String value = (String) metodo.invoke(documento,
									null);
							actualizarCampoDatoDocumento(
									documento.getIdObjeto(), idCampo, value,
									orden);
						} else {
							throw new Exception(
									"Error: No se encontro ningun metodo con ese nombre en la clase:"
											+ documento.getClass());
						}
					} else {
						throw new Exception(
								"Error: No se encontro ninguna propiedad en DocDocumentoVO que coincida con la clave:"
										+ key);
					}
				} else {
					throw new Exception(
							"Error: No se encontro ningun campo en el properties que coincida con la clave:"
									+ key);
				}
			}
		} else {
			throw new Exception(
					"Error: No se encontro el Id Interno dentro del fichero de propiedades.");
		}
	}

	private List getCamposDatoXIdTabla(String idTabla) {
		List list = campoDatoDbEntity.getCamposDatoXIdTabla(idTabla);
		for (int i = 0; i < list.size(); i++) {
			CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
			campoDatoVO.setTipoText(getTipoText(campoDatoVO.getTipo()));
		}
		return list;
	}

	private String getTipoText(int tipo) {
		if (tipo == TipoCampo.FECHA_VALUE)
			return TipoCampo.FECHA_LABEL;
		if (tipo == TipoCampo.NUMERICO_VALUE)
			return TipoCampo.NUMERICO_LABEL;
		if (tipo == TipoCampo.REFERENCIA_VALUE)
			return TipoCampo.REFERENCIA_LABEL;
		if (tipo == TipoCampo.TEXTO_CORTO_VALUE)
			return TipoCampo.TEXTO_CORTO_LABEL;
		if (tipo == TipoCampo.TEXTO_LARGO_VALUE)
			return TipoCampo.TEXTO_LARGO_LABEL;
		return null;
	}

	private int getOrdenCampoTabla(String idElementoCF, String idCampo,
			String valor) {
		ValorCampoGenericoVOBase value = null;
		if (StringUtils.isNotEmpty(idCampo)) {
			CampoDatoVO campo = campoDatoDbEntity.getCampoDato(idCampo);
			switch (campo.getTipo()) {
			case TipoCampo.TEXTO_CORTO_VALUE:
				value = campoTextoCortoDBEntity.getValue(idElementoCF, idCampo,
						valor);
				break;
			case TipoCampo.TEXTO_LARGO_VALUE:
				value = campoTextoLargoDBEntity.getValue(idElementoCF, idCampo,
						valor);
				break;
			case TipoCampo.NUMERICO_VALUE:
				value = campoNumeroDBEntity.getValue(idElementoCF, idCampo,
						valor);
				break;
			}
		}
		if (value != null)
			return value.getOrden();
		return 0;
	}

	private void insertarCampoDatoDocumento(String idElementoCF,
			String idCampo, String value, int orden) {
		if (StringUtils.isNotEmpty(idCampo)) {
			CampoDatoVO campo = campoDatoDbEntity.getCampoDato(idCampo);
			if (campo != null) {
				switch (campo.getTipo()) {
				case TipoCampo.TEXTO_CORTO_VALUE:
					CampoTextoVO texto = new CampoTextoVO();
					texto.setIdObjeto(idElementoCF);
					texto.setIdCampo(idCampo);
					texto.setOrden(orden);
					texto.setTipo(TipoCampo.TEXTO_CORTO_VALUE);
					texto.setValue(value);
					texto.setTimestamp(DateUtils.getFechaActual());
					campoTextoCortoDBEntity.insertValue(texto);
					break;
				case TipoCampo.TEXTO_LARGO_VALUE:
					CampoTextoVO textol = new CampoTextoVO();
					textol.setIdObjeto(idElementoCF);
					textol.setIdCampo(idCampo);
					textol.setOrden(orden);
					textol.setTipo(TipoCampo.TEXTO_LARGO_VALUE);
					textol.setValue(value);
					textol.setTimestamp(DateUtils.getFechaActual());
					campoTextoLargoDBEntity.insertValue(textol);
					break;
				case TipoCampo.NUMERICO_VALUE:
					CampoNumericoVO num = new CampoNumericoVO();
					num.setIdObjeto(idElementoCF);
					num.setIdCampo(idCampo);
					num.setOrden(orden);
					num.setTipo(TipoCampo.NUMERICO_VALUE);
					num.setValue(value);
					num.setTimestamp(DateUtils.getFechaActual());
					campoNumeroDBEntity.insertValue(num);
					break;
				}
			}else{
				logger.error("No se ha encontrado el campo con ID:" + idCampo);
			}
		}
	}

	private void eliminarCampoDatoDocumento(List camposTabla,
			String idElementoCF, int orden) {
		if (!camposTabla.isEmpty()) {
			for (Iterator iter = camposTabla.iterator(); iter.hasNext();) {
				CampoDatoVO campo = ((CampoDatoVO) iter.next());
				switch (campo.getTipo()) {
				case TipoCampo.TEXTO_CORTO_VALUE:
					campoTextoCortoDBEntity.deleteValue(idElementoCF,
							campo.getId(), orden);
					break;
				case TipoCampo.TEXTO_LARGO_VALUE:
					campoTextoLargoDBEntity.deleteValue(idElementoCF,
							campo.getId(), orden);
					break;
				case TipoCampo.NUMERICO_VALUE:
					campoNumeroDBEntity.deleteValue(idElementoCF,
							campo.getId(), orden);
					break;
				}
			}
		}
	}

	private void actualizarCampoDatoDocumento(String idElementoCF,
			String idCampo, String value, int orden) {
		if (StringUtils.isNotEmpty(idCampo)) {
			CampoDatoVO campo = campoDatoDbEntity.getCampoDato(idCampo);
			switch (campo.getTipo()) {
			case TipoCampo.TEXTO_CORTO_VALUE:
				CampoTextoVO texto = new CampoTextoVO();
				texto.setIdObjeto(idElementoCF);
				texto.setIdCampo(idCampo);
				texto.setOrden(orden);
				texto.setValue(value);
				campoTextoCortoDBEntity.updateValue(texto);
				break;
			case TipoCampo.TEXTO_LARGO_VALUE:
				CampoTextoVO textol = new CampoTextoVO();
				textol.setIdObjeto(idElementoCF);
				textol.setIdCampo(idCampo);
				textol.setOrden(orden);
				textol.setValue(value);
				campoTextoLargoDBEntity.updateValue(textol);
				break;
			case TipoCampo.NUMERICO_VALUE:
				CampoNumericoVO num = new CampoNumericoVO();
				num.setIdObjeto(idElementoCF);
				num.setIdCampo(idCampo);
				num.setOrden(orden);
				num.setValue(value);
				campoNumeroDBEntity.updateValue(num);
				break;
			}
		}
	}

	public DocDocumentoVO getDocumentoByIdInterno(int tipoObjeto,
			String idObjeto, String idInterno) {
		// Comprobar permisos de consulta
		checkAcceso(
				tipoObjeto,
				idObjeto,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		if (tipoObjeto == TipoObjeto.ELEMENTO_CF) {
			DocDocumentoVO documento = docDocumentoCFDBEntity
					.getDocumentoByIdInterno(idInterno);

			if (documento == null) {
				documento = getDocumento(tipoObjeto, idObjeto, idInterno);
				documento = docDocumentoCFDBEntity.getDocumento(idInterno);
			}

			return documento;

		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDocumentosElectronicosBI#getDocumentosElementoCuadro(java.lang.String)
	 */
	public List getDocumentosElementoCuadro(String id) {
		// Comprobar permisos de consulta
		checkAcceso(
				TipoObjeto.ELEMENTO_CF,
				id,
				TipoAcceso.CONSULTA,
				DocumentosElectronicosSecurityManager.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION);

		return docDocumentoCFDBEntity.getDocumentos(id);
	}

	private String getIdRepEcmElementoCf(int tipo, String idElemento) {
		String idRepEcm = null;

		switch (tipo) {
		case TipoObjeto.ELEMENTO_CF:
			IElementoCuadroClasificacion elementoCuadro = elementoCuadroClasificacionDbEntity
					.getElementoCuadroClasificacion(idElemento);

			if (elementoCuadro != null) {
				idRepEcm = elementoCuadro.getIdRepEcm();
			}

			break;
		case TipoObjeto.DESCRIPTOR:
			DescriptorVO descriptor = descriptorDBEntity
					.getDescriptor(idElemento);

			if (descriptor != null) {
				idRepEcm = descriptor.getIdRepEcm();
			}
			break;
		default:
			break;
		}
		return idRepEcm;
	}

	public String establecerClasificador(int tipoObjeto, String idObjeto,
			String nombreCompleto, TransferenciaElectronicaInfo info) {

		String idClfPadre = null;

		if (StringUtils.isNotEmpty(nombreCompleto)) {

			String[] partes = nombreCompleto.split(Constants.SLASH);

			for (int i = 0; i < partes.length; i++) {
				String nombre = partes[i];

				if (StringUtils.isNotBlank(nombre)) {
					DocClasificadorVO docClasificador = getClasificadorByNombre(
							tipoObjeto, idObjeto, nombre, idClfPadre);

					if (docClasificador == null) {
						docClasificador = new DocClasificadorVO();
						docClasificador.setNombre(nombre);
						docClasificador.setIdClfPadre(idClfPadre);

						docClasificador.setIdObjeto(idObjeto);
						docClasificador.setTipoObjeto(tipoObjeto);
						docClasificador
								.setMarcas(MarcasClasificador.SIN_MARCAS);
						docClasificador.setEstado(EstadoClasificador.VALIDADO);
						// vo.setDescripcion();

						// docClasificador.set
						docClasificador = insertClasificadorDesdeCuadro(docClasificador);

						idClfPadre = docClasificador.getId();

						info.addIdClasificadoresDocumentosCreados(docClasificador
								.getId());
					} else {
						idClfPadre = docClasificador.getId();
					}
				}
			}
		}

		return idClfPadre;
	}

	public void updateIdElementoCfDocumentosYClasificadores(
			String idElementocfAntiguo, String idElementoCfNuevo,
			String[] idsClasificadores, String[] idsInternosDocumentos) {

		if (ArrayUtils.isNotEmptyOrBlank(idsClasificadores)) {
			docClasifCFDBEntity.updateIdElementocf(idElementocfAntiguo,
					idElementoCfNuevo, idsClasificadores);
		}

		if (ArrayUtils.isNotEmptyOrBlank(idsInternosDocumentos)) {
			docDocumentoCFDBEntity.updateIdElementocf(idElementocfAntiguo,
					idElementoCfNuevo, idsInternosDocumentos);
		}
	}

}
