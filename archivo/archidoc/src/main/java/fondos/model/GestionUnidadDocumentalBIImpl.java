package fondos.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;

import util.CollectionUtils;
import valoracion.vos.IUnidadDocumentalEliminacionVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.MotivoEliminacionUnidadInstalacion;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.security.FondosSecurityManager;

import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.db.IReferenciaDBEntity;
import descripcion.model.xml.card.TipoFicha;
import docelectronicos.TipoObjeto;
import docelectronicos.db.IUnidadDocumentalElectronicaDBEntity;
import docelectronicos.vos.UnidadDocumentalElectronicaVO;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
import fondos.vos.TablaTemporalFondosVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.model.NivelAcceso;

public class GestionUnidadDocumentalBIImpl extends ServiceBase implements
		GestionUnidadDocumentalBI {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(GestionUnidadDocumentalBIImpl.class);

	private IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity;
	private IUnidadDocumentalDbEntity _unidadDocumentalDBEntity;
	protected INivelCFDBEntity _nivelDBEntity = null;
	private IUDocEnUiDepositoDbEntity _udocDepositoDBEntity = null;
	private IReferenciaDBEntity _referenciaDBEntity = null;
	// private IUdocElectronicaDBEntity _udocElectronicaDBEntity;
	private IUnidadDocumentalElectronicaDBEntity _unidadDocumentalElectroncaDBEntity;

	// private IOrganoProductorDbEntity _organoProductorDBEntity = null;
	// private IProductorSerieDbEntity _productorSerieDBEntity = null;
	// private IDescriptorDBEntity _descriptorDBEntity = null;

	public GestionUnidadDocumentalBIImpl(
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDBEntity,
			INivelCFDBEntity nivelDBEntity,
			IUDocEnUiDepositoDbEntity udocDepositoDBEntity,
			IReferenciaDBEntity referenciaDBEntity,
			/* IUdocElectronicaDBEntity udocElectronicaDBEntity, */
			IUnidadDocumentalElectronicaDBEntity unidadDocumentalElectroncaDBEntity
	/*
	 * , IProductorSerieDbEntity productorSerieDBEntity,
	 * IOrganoProductorDbEntity organoProductorDBEntity
	 */) {
		super();
		this._elementoCuadroClasificacionDBEntity = elementoCuadroClasificacionDBEntity;
		this._unidadDocumentalDBEntity = unidadDocumentalDBEntity;
		this._nivelDBEntity = nivelDBEntity;
		this._udocDepositoDBEntity = udocDepositoDBEntity;
		this._referenciaDBEntity = referenciaDBEntity;
		// this._udocElectronicaDBEntity = udocElectronicaDBEntity;
		this._unidadDocumentalElectroncaDBEntity = unidadDocumentalElectroncaDBEntity;
		/*
		 * this._productorSerieDBEntity = productorSerieDBEntity;
		 * this._organoProductorDBEntity = organoProductorDBEntity;
		 */
	}

	/**
	 * Obtiene el nivel de acceso de la unidad documental.
	 * 
	 * @param udoc
	 *            Información de la unidad documental.
	 * @param valoracion
	 *            Valoración dictaminada de la serie.
	 * @return Nivel de acceso ({@link NivelAcceso}).
	 */
	public int getNivelAcceso(transferencias.vos.UnidadDocumentalVO udoc,
			ValoracionSerieVO valoracion) {
		int nivelAcceso = NivelAcceso.ARCHIVO;

		if (valoracion != null) {
			switch (valoracion.getTipoRegimenAcceso()) {
			case ValoracionSerieVO.REGIMEN_ACCESO_LIBRE:
				nivelAcceso = NivelAcceso.PUBLICO;
				break;

			case ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL:
				Calendar fechaFin = Calendar.getInstance();
				fechaFin.setTime(udoc.getFechaFin());
				fechaFin.add(Calendar.YEAR, valoracion.getAnosRegimenAcceso());

				if (fechaFin.before(Calendar.getInstance()))
					nivelAcceso = NivelAcceso.PUBLICO;
				break;

			default: // ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_PERMANENTE
			}
		}

		return nivelAcceso;
	}

	public UnidadDocumentalVO crearUnidadDocumental(SerieVO serie,
			ValoracionSerieVO valoracion, INivelCFVO nivelUnidadDocumental,
			String codigoUdoc,
			transferencias.vos.UnidadDocumentalVO infoUnidadDocumental,
			Map.Entry sistemaProductor, String tipoDocumental,
			String idLCAPref, String idArchivo, boolean comprobarPermisos,
			boolean isUnidadElectronica) {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventAlta(this);
		Locale locale = getServiceClient().getLocale();

		if (comprobarPermisos)
			checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);
		ElementoCuadroClasificacionVO elementoCF = new ElementoCuadroClasificacion();
		elementoCF.setCodigo(codigoUdoc);
		elementoCF.setTitulo(infoUnidadDocumental.getAsunto());
		elementoCF.setIdPadre(serie.getId());
		elementoCF.setIdNivel(nivelUnidadDocumental.getId());
		elementoCF.setIdFichaDescr(nivelUnidadDocumental.getIdFichaDescrPref());
		elementoCF.setTipo(TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador());
		ConfiguracionFondos configuracionFondos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		StringBuffer codigoReferenciaUdoc = new StringBuffer(
				serie.getCodReferencia()).append(
				configuracionFondos.getDelimitadorCodigoReferencia()).append(
				codigoUdoc);
		elementoCF.setIdFondo(serie.getIdFondo());
		elementoCF.setCodRefFondo(serie.getCodRefFondo());
		elementoCF.setCodReferencia(codigoReferenciaUdoc.toString());
		elementoCF.setEstado(IElementoCuadroClasificacion.VIGENTE);
		elementoCF.setIdLCA(idLCAPref);
		elementoCF.setNivelAcceso(getNivelAcceso(infoUnidadDocumental,
				valoracion));
		elementoCF.setIdArchivo(idArchivo);
		String fichaDescripcionUdoc = nivelUnidadDocumental
				.getIdFichaDescrPref();
		String idRepEcmUdoc = nivelUnidadDocumental.getIdRepEcmPref();

		String fichaDescripcionPrefUDoc = serie
				.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());
		String idRepEcmPrefUDoc = serie
				.getIdRepEcmPrefUdoc(nivelUnidadDocumental.getId());

		// Si la serie tiene definidos listas de volúmenes y ficha preferente
		// para las u.docs., usar estas
		if (fichaDescripcionPrefUDoc != null)
			fichaDescripcionUdoc = fichaDescripcionPrefUDoc;

		if (idRepEcmPrefUDoc != null)
			idRepEcmUdoc = idRepEcmPrefUDoc;

		elementoCF.setIdFichaDescr(fichaDescripcionUdoc);
		elementoCF.setIdRepEcm(idRepEcmUdoc);
		elementoCF.setTienedescr(Constants.FALSE_STRING);
		elementoCF.setEditClfDocs(Constants.FALSE_STRING);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			elementoCF.setTitulo(elementoCF.getTitulo().toUpperCase());

		elementoCF.setSubtipo(nivelUnidadDocumental.getSubtipo());

		iniciarTransaccion();

		// Auditoria
		AuditFondos.addDataLogAlta(locale, logEvent, elementoCF,
				nivelUnidadDocumental.getNombre(), serie.getCodReferencia());
		elementoCF = _elementoCuadroClasificacionDBEntity
				.insertElementoCF(elementoCF);

		IUnidadDocumental unidadDocumental = new UnidadDocumental(elementoCF);
		unidadDocumental.setCodSistProductor(infoUnidadDocumental
				.getCodSistProductor());
		unidadDocumental.setNumExp(infoUnidadDocumental.getNumeroExpediente());
		unidadDocumental.setTipoDocumental(tipoDocumental);
		unidadDocumental.setMarcasBloqueo(infoUnidadDocumental
				.getMarcasBloqueo());
		unidadDocumental = (IUnidadDocumental) _unidadDocumentalDBEntity
				.insertUnidadDocumental(unidadDocumental);

		// Si es una unidad Electrónica

		if (isUnidadElectronica) {
			UnidadDocumentalElectronicaVO unidadDocumentalElectronica = new UnidadDocumentalElectronicaVO(
					elementoCF.getId());

			_unidadDocumentalElectroncaDBEntity
					.insertRow(unidadDocumentalElectronica);
		}

		commit();

		return unidadDocumental;
	}

	/**
	 * Obtiene la unidad documental que se corresponde con un número de
	 * expediente
	 * 
	 * @param numeroExpediente
	 *            Número de expediente
	 * @return Datos de unidad documental
	 */
	public UnidadDocumentalVO getUdocByNumeroExpediente(String numeroExpediente) {
		String[] numExpediente = { numeroExpediente };
		List udocs = _unidadDocumentalDBEntity
				.getUnidadDocumentalXNumeroExpediente(numExpediente);
		UnidadDocumentalVO udoc = null;
		if (udocs.size() > 0)
			udoc = (UnidadDocumentalVO) udocs.get(0);
		return udoc;
	}

	/**
	 * Obtiene las unidades documentales constituidas por los expedientes cuyos
	 * números se indican
	 * 
	 * @param numeroExpediente
	 *            Conjunto de números de expediente
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocsByNumeroExpediente(String[] numeroExpediente) {
		return _unidadDocumentalDBEntity
				.getUnidadDocumentalXNumeroExpediente(numeroExpediente);
	}

	/**
	 * Obtiene las números de expediente que están en el cuadro de clasificación
	 * de entre la lista que se pasa como parámetro
	 * 
	 * @param numerosExpedientes
	 *            Conjunto de números de expediente
	 * @return Lista de números de expediente
	 */
	public List findNumerosExpediente(String[] numerosExpediente) {
		return _unidadDocumentalDBEntity
				.findNumerosExpediente(numerosExpediente);
	}

	public void updateNumeroExpediente(String idUnidadDocumental,
			String numExpediente) {
		_unidadDocumentalDBEntity.updateNumeroExpediente(idUnidadDocumental,
				numExpediente);
	}

	public List getRangosUdoc(String idUDoc, String idCampoInicial,
			String idCampoFinal) {
		return _unidadDocumentalDBEntity.getRangosUDoc(idUDoc, idCampoInicial,
				idCampoFinal);
	}

	/**
	 * Obtiene la información de la unidad documental.
	 * 
	 * @param idElemento
	 *            Identificador de la unidad documental en el cuadro de
	 *            clasificación.
	 * @return Unidad documental.
	 */
	public UnidadDocumentalVO getUnidadDocumental(String idElemento) {
		return _unidadDocumentalDBEntity.getUnidadDocumental(idElemento);
	}

	public UnidadDocumentalVO getTuplaUnidadDocumental(String idElemento) {
		return _unidadDocumentalDBEntity.getTuplaUnidadDocumental(idElemento);
	}

	public HashMap getUINumPartesUdocs(TablaTemporalFondosVO tablaTemporal) {
		return _udocDepositoDBEntity.getUINumPartesUdocs(tablaTemporal);
	}

	public HashMap getUdocNumCambiosVolumenSerie(List idsUdocs) {
		return _unidadDocumentalDBEntity
				.getUdocNumCambiosVolumenSerie(idsUdocs);
	}

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 * 
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getSignaturasUdoc(String idUnidadDocumental) {
		return _udocDepositoDBEntity
				.getPartesUdocByIDElementoCF(idUnidadDocumental);
	}

	public List getUdocsEnUI(String idUnidadDocumental) {
		return _udocDepositoDBEntity
				.getPartesUdocByIDElementoCF(idUnidadDocumental);
	}

	public List getIdsUIPartesUdoc(String idUnidadDocumental) {
		return _udocDepositoDBEntity
				.getIdsUIsPartesUdocByIDElementoCF(idUnidadDocumental);
	}

	/**
	 * Elimina una unidad documental dada
	 * 
	 * @param udoc
	 *            Unidad documental
	 */
	public void eliminarUdoc(IUnidadDocumentalEliminacionVO udoc) {
		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent logEvent = getLogginEvent(ArchivoActions.FONDOS_MODULE_BAJA_UDOC);
		DataLoggingEvent logData = logEvent.getDataLoggingEvent(
				ArchivoObjects.OBJECT_UDOC, udoc.getIdudoc());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_UDOC,
				udoc.getCodigo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_UDOC,
				udoc.getExpedienteudoc());
		checkPermission(FondosSecurityManager.BAJAUDOC_ACTION);

		eliminarUdocBasico(udoc.getIdudoc());
	}

	private void eliminarUdocBasico(String idUdoc) {
		iniciarTransaccion();

		// Eliminamos la udoc
		_unidadDocumentalDBEntity.deleteUnidadDocumental(idUdoc);

		// Eliminamos el elemento del cuadro
		_elementoCuadroClasificacionDBEntity.deleteElementoCF(idUdoc);

		commit();
	}

	/**
	 * Obtiene la unidad documental en el cuadro de clasificacion en la que se
	 * ha integrado la unidad documental proveniente de relacion de entrega que
	 * se indica
	 * 
	 * @param id
	 * @return
	 */
	public UnidadDocumentalVO getUdocXUdocEnTransferencia(
			String idUdocEnRelacionEntrega) {
		List signaturasUdoc = _udocDepositoDBEntity
				.getPartesUdocByIDUdocEnRelacionEntrega(idUdocEnRelacionEntrega);
		if (CollectionUtils.isEmpty(signaturasUdoc))
			return null;
		UDocEnUiDepositoVO signaturaUdoc = (UDocEnUiDepositoVO) signaturasUdoc
				.get(0);
		return _unidadDocumentalDBEntity.getUnidadDocumental(signaturaUdoc
				.getIdunidaddoc());
	}

	public UnidadDocumentalVO getUdocXUdocEnDivisionFS(String idUdocEnDivisionFS) {
		UnidadDocumentalVO udoc = null;

		UDocEnUiDepositoVO udocEnUiDepositoVO = _udocDepositoDBEntity
				.getUdocByIDUdocEnDivisionFS(idUdocEnDivisionFS);
		if (udocEnUiDepositoVO != null)
			udoc = _unidadDocumentalDBEntity
					.getUnidadDocumental(udocEnUiDepositoVO.getIdunidaddoc());

		return udoc;
	}

	/**
	 * Desinstala una unidad documental de la ubicación que ocupa en el depósito
	 * físico
	 * 
	 * @param idUdoc
	 *            Identificador de unidad documental
	 */
	public void desinstalarUnidadDocumental(String idUdoc) {
		// Auditoria
		LoggingEvent logEvent = getLogginEvent(ArchivoActions.FONDOS_MODULE_BAJA_UDOCENUI);
		DataLoggingEvent logData = logEvent.getDataLoggingEvent(
				ArchivoObjects.OBJECT_UDOC, idUdoc);
		UnidadDocumentalVO udoc = getUnidadDocumental(idUdoc);
		Locale locale = getServiceClient().getLocale();

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_UDOC,
				udoc.getCodigo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_UDOC,
				udoc.getNumExp());

		checkPermission(FondosSecurityManager.BAJAUDOC_ENUI_ACTION);
		iniciarTransaccion();
		_udocDepositoDBEntity.deleteUdoc(idUdoc);
		commit();
	}

	/**
	 * Obtiene el número de unidades documentales que han sido producidas por un
	 * determinado productor
	 * 
	 * @param descriptorProductor
	 *            Descriptor del productor
	 * @return Número de unidades documentales
	 */
	public int getNumUdocByProductor(String descriptorProductor) {
		return _referenciaDBEntity.countReferences(1, descriptorProductor);
	}

	/**
	 * Obtiene un listado de las unidades de instalacion ocupadas para la unidad
	 * de instalacion dada por su identificador.
	 * 
	 * @param idUInstalacion
	 *            Identificador de la unidad de instalación
	 * @return Listado de {@link deposito.vos.UDocEnUiDepositoVO}
	 */
	public List getUdocsEnUnidadInstalacion(String idUInstalacion) {
		return _udocDepositoDBEntity.getUDocsVOXIdUInstalacion(idUInstalacion);
	}

	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent loggingEvent = new LoggingEvent(
				ArchivoModules.FONDOS_MODULE, action, getServiceClient(), false);
		loggingEvent.setServiceToAudit(this);
		getLogger().add(loggingEvent);
		return loggingEvent;
	}

	/**
	 * Publica las unidades documentales con acceso restringido que han
	 * sobrepasado la fecha de restricción.
	 */
	public void publicarUnidadesDocumentales() {
		// Auditoria
		LoggingEvent logEvent = getLogginEvent(ArchivoActions.FONDOS_MODULE_PUBLICACION_ELEMENTO);
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(FondosSecurityManager.PUBLICAR_ELEMENTO_ACTION);

		// Lista de unidades documentales a publicar
		List udocs = _unidadDocumentalDBEntity
				.getUnidadesDocumentalesPublicacion();

		UnidadDocumentalVO udoc;
		for (int i = 0; i < udocs.size(); i++) {
			udoc = (UnidadDocumentalVO) udocs.get(i);

			iniciarTransaccion();

			// Cambio de nivel de acceso de la unidad documental
			_elementoCuadroClasificacionDBEntity.updateControlAcceso(
					udoc.getIdElementocf(), NivelAcceso.PUBLICO,
					udoc.getIdLCA());

			// Auditoría
			AuditFondos.addDataLogUpdatePublicacionUdoc(locale, logEvent, udoc);

			if (logger.isDebugEnabled())
				logger.debug("Publicación de la unidad documental con id: "
						+ udoc.getId());

			commit();
		}
	}

	/**
	 * Elimina las unidades documentales prestadas que no han sido devueltas
	 * tras dos reclamaciones.
	 * 
	 * @throws Exception
	 */
	public void eliminarUnidadesDocumentalesPrestadasNoDevueltas()
			throws Exception {
		// Auditoria
		LoggingEvent logEvent = getLogginEvent(ArchivoActions.FONDOS_MODULE_BAJA_ELEMENTO);
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(FondosSecurityManager.ELIMINACION_ELEMENTO_ACTION);

		// Lista de unidades documentales a publicar
		List udocs = _unidadDocumentalDBEntity
				.getUnidadesDocumentalesPrestadasNoDevueltas();

		// Eliminar las unidades documentales
		UnidadDocumentalVO udoc;
		for (int i = 0; i < udocs.size(); i++) {
			udoc = (UnidadDocumentalVO) udocs.get(i);

			iniciarTransaccion();

			// Eliminar la unidad documental
			eliminarUnidadDocumental(
					udoc,
					MotivoEliminacionUnidadInstalacion.WS_PRESTADAS_NO_DEVUELTAS);

			// Auditoría
			AuditFondos.addDataLogRemove(locale, logEvent, udoc, null);

			if (logger.isDebugEnabled())
				logger.debug("Eliminación de la unidad documental con id: "
						+ udoc.getId());

			commit();
		}
	}

	private void eliminarUnidadDocumental(UnidadDocumentalVO udoc,
			Integer motivo) throws Exception {
		// Abrimos la transaccion
		iniciarTransaccion();

		// // TODO Incorporar la udoc al histórico
		// HistoricoUDOCVO historicoUDOC = new HistoricoUDOCVO();
		// historicoUDOC.setCodRefUdoc(udoc.getCodReferencia());
		// historicoUDOC.setIdEliminacion(null); // Error: NOT NULL
		// historicoUDOC.setIdUDOC(udoc.getId());
		// historicoUDOC.setSignaturaUdoc(udoc.getCodigo());
		// historicoUDOC.setTituloUdoc(udoc.getTitulo());
		// historicoUDOC.setNumExpUdoc(udoc.getNumExp());
		// getGestionEliminacionBI().crearHistoricoUDOC( historicoUDOC );

		// Eliminar la descripción
		if (udoc.tieneDescripcion())
			getGestionDescripcionBI().deleteFicha(udoc.getId(),
					TipoFicha.FICHA_ELEMENTO_CF);

		// Eliminar los documentos electrónicos
		GestionDocumentosElectronicosBI serviceDocumentos = getGestionDocumentosElectronicosBI();
		serviceDocumentos
				.deleteDocumentos(udoc.getId(), TipoObjeto.ELEMENTO_CF);

		// Eliminamos las referencias a documentos vitales
		getGestionDocumentosVitalesBI().eliminaVinculosExpediente(
				udoc.getNumExp(), udoc.getCodSistProductor());

		// Eliminar las tareas de captura relacionadas
		serviceDocumentos.eliminarTareas(udoc.getId(),
				ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL);

		// Desinstalar la unidad documental
		List partesUdoc = getUdocsEnUI(udoc.getId());
		CollectionUtils.transform(partesUdoc, new Transformer() {
			public Object transform(Object obj) {
				return ((UDocEnUiDepositoVO) obj).getIduinstalacion();
			}
		});
		desinstalarUnidadDocumental(udoc.getId());

		// Eliminar la unidad documental y el elemento del cuadro
		eliminarUdocBasico(udoc.getId());

		// Comprobar las unidades de instalación distintas
		// y si son vacías las eliminamos.
		int numUnidadesInstalacion = 0;
		// int volumenEliminado = 0;
		double volumenEliminado = 0;
		GestorEstructuraDepositoBI serviceEstructura = getGestorEstructuraDepositoBI();
		for (Iterator itUnidades = partesUdoc.iterator(); itUnidades.hasNext();) {
			String idUnidadInstalacion = (String) itUnidades.next();

			List udocsEnUI = getUdocsEnUnidadInstalacion(idUnidadInstalacion);
			if (CollectionUtils.isEmpty(udocsEnUI)) {
				UInsDepositoVO unidadInstalacion = serviceEstructura
						.getUinsEnDeposito(idUnidadInstalacion);

				FormatoHuecoVO formato = serviceEstructura
						.getFormatoHueco(unidadInstalacion.getIdformato());
				// volumenEliminado += formato.getLongitud().intValue();

				if (formato != null) {
					if (formato.getLongitud() == null
							|| formato.getLongitud().doubleValue() == Double.MIN_VALUE)
						volumenEliminado += serviceEstructura
								.getLongitudHuecosIrregularesXIdUInstalacion(idUnidadInstalacion);
					else
						volumenEliminado += formato.getLongitud().doubleValue();
				}

				if (unidadInstalacion == null) {
					unidadInstalacion = new UInsDepositoVO();
					unidadInstalacion.setId(idUnidadInstalacion);
				}

				// Obtener el idArchivo
				HuecoVO huecoVO = serviceEstructura
						.getHuecoUInstalacion(idUnidadInstalacion);

				String idArchivo = null;
				if (huecoVO != null) {
					DepositoVO deposito = serviceEstructura
							.getUbicacion(huecoVO.getIddeposito());

					if (deposito != null)
						idArchivo = deposito.getIdArchivo();
				}

				serviceEstructura.deleteUInstDeposito(idArchivo,
						unidadInstalacion, motivo);
				serviceEstructura.liberaUnidadInstalacion(idUnidadInstalacion);

				numUnidadesInstalacion++;
			}
		}

		// Actualizar los datos de la serie
		GestionSeriesBI serieBI = getGestionSeriesBI();
		serieBI.updateVolumenSerieNoTransaccional(udoc.getIdSerie());
		serieBI.updateContenidoSerieNoTransaccional(udoc.getIdSerie());

		// Cerramos la transaccion
		commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionUnidadDocumentalBI#getUnidadDocumental(java.lang.String
	 * [])
	 */
	public List getUnidadDocumental(String[] idsElemento) {
		return _unidadDocumentalDBEntity.getUnidadesDocumentales(idsElemento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionUnidadDocumentalBI#modificarSignaturaUDoc(java.lang.
	 * String, java.lang.String)
	 */
	public void modificarSignaturaUDoc(UnidadDocumentalVO unidadDocumentalVO,
			String nuevaSignatura) {
		// IElementoCuadroClasificacion elementoUDoc =
		// _elementoCuadroClasificacionDBEntity.getElementoCuadroClasificacion(idUDoc);
		// ElementoCuadroClasificacionVO elementoPadreUDoc =
		// elementoUDoc.getParentElement();
		// NivelCFVO nivelPadre =
		// _nivelDBEntity.getNivelCF(elementoPadreUDoc.getIdNivel());
		//
		// String nuevoCodigoReferencia =
		// _elementoCuadroClasificacionDBEntity.composeCodigoReferencia(nivelPadre,
		// elementoPadreUDoc
		// .getFinalCodRefPadre(), elementoPadreUDoc.getCodigo(),
		// elementoPadreUDoc
		// .getCodRefFondo(), nuevaSignatura);
		String antiguoCodigoReferencia = unidadDocumentalVO.getCodReferencia();
		String antiguaSignatura = unidadDocumentalVO.getCodigo();
		String codigoSinSignatura = antiguoCodigoReferencia.substring(0,
				antiguoCodigoReferencia.length() - antiguaSignatura.length());
		StringBuffer nuevoCodigo = new StringBuffer(codigoSinSignatura)
				.append(nuevaSignatura);

		Map colsToUPdate = new HashMap();
		colsToUPdate.put(ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
				nuevaSignatura);
		colsToUPdate
				.put(ElementoCuadroClasificacionDBEntityImpl.CODIGO_REFERENCIA_FIELD,
						nuevoCodigo);

		_elementoCuadroClasificacionDBEntity.updateFieldsECF(
				unidadDocumentalVO.getId(), colsToUPdate);

	}

	public int getNumUdocsUISerie(String idSerie, String idUnidadInstalacion) {
		return _unidadDocumentalDBEntity.getNumUdocsUISerie(idSerie,
				idUnidadInstalacion);
	}

	/**
	 * Actualiza los campos de la tabla de Unidades Documentales en el cuadro de
	 * clasificación que es necesario modificar durante la validación
	 * 
	 * @param idUnidadDocumental
	 * @param columnsToUpdate
	 */
	public void updateFieldsUDocCFVEA(UnidadDocumentalVO uDoc) {
		_unidadDocumentalDBEntity.updateFieldsUDocCFVEA(uDoc);
	}

	public int getMarcasBloqueo(String idElementoCF) {
		return _unidadDocumentalElectroncaDBEntity
				.getMarcasBloqueo(idElementoCF);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionUnidadDocumentalBI#eliminarMarcaConservadaUdocsSerie(java.lang.String[])
	 */
	public void eliminarMarcaConservadaUdocsSerie(String[] ids) {

		checkPermission(FondosSecurityManager.ELIMINAR_MARCA_CONSERVADA);

		LoggingEvent logEvent = AuditFondos
				.getLogginEventGestionUdocsSerie(this);
		Locale locale = getServiceClient().getLocale();

		AuditFondos.addDataLogEliminarMarcaConservada(locale, logEvent, ids);

		_elementoCuadroClasificacionDBEntity.removeIdEliminacionElementoCF(ids);

	}
}