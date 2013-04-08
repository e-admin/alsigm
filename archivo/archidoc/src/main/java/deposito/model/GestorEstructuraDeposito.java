package deposito.model;

import ieci.core.exception.IeciTdException;
import ieci.core.guid.GuidManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessages;

import solicitudes.db.IDetalleDBEntity;
import transferencias.ReservaPrevision;
import transferencias.TipoTransferencia;
import transferencias.TipoUInstalacion;
import transferencias.actions.RelacionEntregaPO;
import transferencias.actions.RelacionToPO;
import transferencias.db.INSecUIDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.db.IUIReeaCRDBEntity;
import transferencias.db.IUdocElectronicaDBEntity;
import transferencias.db.IUdocEnUIDBEntity;
import transferencias.db.IUnidadInstalacionDBEntity;
import transferencias.db.IUnidadInstalacionReeaDBEntity;
import transferencias.db.UnidadInstalacionDBEntityImpl;
import transferencias.db.oracle.RelacionEntregaDBEntityImpl;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.model.EstadoREntrega;
import transferencias.model.TipoSignaturacion;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import transferencias.vos.UnidadInstalacionVO;
import util.CollectionUtils;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.MotivoEliminacionUnidadInstalacion;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.TooManyResultsException;
import common.exceptions.UniqueKeyException;
import common.security.DepositoSecurityManager;
import common.util.ArrayUtils;
import common.util.CustomDateRange;
import common.util.DateUtils;
import common.util.IntervalOptions;
import common.util.ListUtils;
import common.util.MarcaUtil;
import common.vos.ConsultaConnectBy;
import common.vos.ResultadoRegistrosVO;

import deposito.DepositoConstants;
import deposito.MarcaNumeracionConstants;
import deposito.SignaturaUtil;
import deposito.actions.ErrorKeys;
import deposito.actions.reubicacion.ReubicacionUinstVO;
import deposito.db.ConcurrentModificationException;
import deposito.db.IDepositoDbEntity;
import deposito.db.IDepositoElectronicoDBEntity;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.IFormatoDbEntity;
import deposito.db.IHistUInstalacionDepositoDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.INsecSigNumHuecoDBEntity;
import deposito.db.INumOrdenDBEntity;
import deposito.db.IOcupacionDBEntity;
import deposito.db.ITipoElementoDBEntity;
import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.db.common.HuecoDbEntityImplBase;
import deposito.exceptions.DepositoElectronicoAlreadyExistsException;
import deposito.exceptions.DepositoElectronicoEnUsoException;
import deposito.exceptions.DepositoEstadoHuecoCambiadoException;
import deposito.exceptions.DepositoHuecoCompactarNoLibreException;
import deposito.exceptions.DepositoHuecoReservarNoLibreException;
import deposito.exceptions.DepositoHuecoReubicarNoLibreException;
import deposito.exceptions.DepositoHuecoSignaturaOcupadoException;
import deposito.exceptions.DepositoHuecoUbicarNoLibreException;
import deposito.exceptions.NumeracionHuecoRepetidaException;
import deposito.exceptions.TipoElementoDepositoException;
import deposito.vos.BusquedaHistUInstDepositoVO;
import deposito.vos.BusquedaUIAnioSerieVO;
import deposito.vos.BusquedaUInsDepositoVO;
import deposito.vos.DepositoElectronicoVO;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HistUInstDepositoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;
import deposito.vos.OcupacionElementoDeposito;
import deposito.vos.ResumenOcupacionVO;
import deposito.vos.TipoElementoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.db.IUnidadDocumentalElectronicaDBEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.Serie;
import fondos.model.TipoNivelCF;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
import fondos.vos.UDocFondo;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.vos.ArchivoVO;

public class GestorEstructuraDeposito extends ServiceBase implements
		GestorEstructuraDepositoBI {
	/**
	 * Logger for this class
	 */
	// private static final Logger logger =
	// Logger.getLogger(GestorEstructuraDeposito.class);

	private static final Map tiposElemento = new HashMap();

	ITipoElementoDBEntity _tipoElementoDBEntity = null;
	IDepositoDbEntity _depositoDbEntity = null;
	IElementoNoAsignableDBEntity _noAsignableDBEntity = null;
	IElementoAsignableDBEntity _asignableDBEntity = null;
	IRelacionEntregaDBEntity _relacionDbEntity = null;
	IOcupacionDBEntity _ocupacionDBEntity = null;
	IHuecoDBEntity _huecoDBEntity = null;
	IUInstalacionDepositoDBEntity _unidadInstalacionDepositoDBEntity = null;
	IUnidadInstalacionDBEntity _unidadInstalacionDBEntity = null;
	IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity = null;
	IUdocEnUIDBEntity _udocEnUIDBEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUIDepositoDBEntity = null;
	IFormatoDbEntity _formatoDBEntity = null;
	INumOrdenDBEntity _numOrdenDBEntity = null;
	IDepositoElectronicoDBEntity _depositoElectronicoDBEntity = null;
	IDocDocumentoCFDBEntity _docDocumentoCFDBEntity = null;
	INSecUIDBEntity _nSecUIDBEntity = null;
	IUnidadDocumentalDbEntity _unidadDocumentalDBEntity = null;
	IDetalleDBEntity _detalleDBEntity = null;
	IElementoCuadroClasificacionDbEntity _elementoCuadro = null;
	IUdocElectronicaDBEntity _udocElectronicaDBEntity = null;
	IUnidadDocumentalElectronicaDBEntity _unidadDocumentalElectronicaDBEntity = null;
	IArchivoDbEntity _archivoDbEntity = null;
	INsecSigNumHuecoDBEntity _nsecSigNumHuecoDbEntity = null;
	IHistUInstalacionDepositoDBEntity _histUinstDepositoDBEntity = null;
	IUIReeaCRDBEntity _unidadInstalacionReeaCrDBEntity = null;

	public GestorEstructuraDeposito() {
	};

	public GestorEstructuraDeposito(
			IDepositoDbEntity depositoDbEntity,
			IElementoAsignableDBEntity asignableDBEntity,
			IElementoNoAsignableDBEntity noAsignableDBEntity,
			IRelacionEntregaDBEntity relacionDbEntity,
			IOcupacionDBEntity ocupacionDBEntity,
			IHuecoDBEntity huecoDBEntity,
			IUInstalacionDepositoDBEntity unidadInstalacionDepositoDBEntity,
			IUnidadInstalacionDBEntity unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity unidadInstalacionReeaDBEntity,
			IUdocEnUIDBEntity udocEnUIDBEntity,
			IUDocEnUiDepositoDbEntity udocEnUIDepositoDBEntity,
			ITipoElementoDBEntity tipoElementoDBEntity,
			IFormatoDbEntity formatoDBEntity,
			INumOrdenDBEntity numOrdenDBEntity,
			IDepositoElectronicoDBEntity depositoElectronicoDBEntity,
			IDocDocumentoCFDBEntity docDocumentoCFDBEntity,
			INSecUIDBEntity nSecUIDBEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDBEntity,
			IDetalleDBEntity detalleDBEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadro,
			IUdocElectronicaDBEntity udocElectronicaDBEntity,
			IUnidadDocumentalElectronicaDBEntity unidadDocumentalElectronicaDBEntity,
			IArchivoDbEntity archivoDbEntity,
			INsecSigNumHuecoDBEntity nsecSigNumHuecoDBEntity,
			IHistUInstalacionDepositoDBEntity histUinstDepositoDBEntity,
			IUIReeaCRDBEntity unidadInstalacionReeaCrDBEntity) {
		super();
		_depositoDbEntity = depositoDbEntity;
		_noAsignableDBEntity = noAsignableDBEntity;
		_asignableDBEntity = asignableDBEntity;
		_relacionDbEntity = relacionDbEntity;
		_ocupacionDBEntity = ocupacionDBEntity;
		_huecoDBEntity = huecoDBEntity;
		_unidadInstalacionDBEntity = unidadInstalacionDBEntity;
		_unidadInstalacionReeaDBEntity = unidadInstalacionReeaDBEntity;
		_unidadInstalacionDepositoDBEntity = unidadInstalacionDepositoDBEntity;
		_udocEnUIDBEntity = udocEnUIDBEntity;
		_udocEnUIDepositoDBEntity = udocEnUIDepositoDBEntity;
		_tipoElementoDBEntity = tipoElementoDBEntity;
		_formatoDBEntity = formatoDBEntity;
		_numOrdenDBEntity = numOrdenDBEntity;
		_depositoElectronicoDBEntity = depositoElectronicoDBEntity;
		_docDocumentoCFDBEntity = docDocumentoCFDBEntity;
		_nSecUIDBEntity = nSecUIDBEntity;
		_unidadDocumentalDBEntity = unidadDocumentalDBEntity;
		_detalleDBEntity = detalleDBEntity;
		_elementoCuadro = elementoCuadro;
		_udocElectronicaDBEntity = udocElectronicaDBEntity;
		_unidadDocumentalElectronicaDBEntity = unidadDocumentalElectronicaDBEntity;
		_archivoDbEntity = archivoDbEntity;
		_nsecSigNumHuecoDbEntity = nsecSigNumHuecoDBEntity;
		_histUinstDepositoDBEntity = histUinstDepositoDBEntity;
		_unidadInstalacionReeaCrDBEntity = unidadInstalacionReeaCrDBEntity;
	}

	public EstructuraDeposito getEstructuraDeposito() {
		GestorEstructuraDepositoBI wraperServiceDeposito = ServiceRepository
				.getInstance(getServiceSession())
				.lookupGestorEstructuraDepositoBI();
		return new EstructuraDeposito(wraperServiceDeposito, getServiceClient());
	}

	public EstructuraDeposito getEstructuraDeposito(String subtreeNode) {
		GestorEstructuraDepositoBI wraperServiceDeposito = ServiceRepository
				.getInstance(getServiceSession())
				.lookupGestorEstructuraDepositoBI();
		return new EstructuraDeposito(wraperServiceDeposito,
				getServiceClient(), subtreeNode);
	}

	// public static TipoElementoVO getTipoElemento2(String idTipoElemento) {
	// TipoElementoVO tipoElemento =
	// (TipoElementoVO)tiposElemento.get(idTipoElemento);
	// return tipoElemento;
	// }

	void validateCrearAsignableTipoRegular(int longitud, int longitudFormato)
			throws DepositoException {
		if (longitud < longitudFormato) {
			throw new DepositoException(
					DepositoException.LONGITUD_MENOR_Q_LONGITUD_FORMATO);
		}
	}

	private DepositoVO eliminarUbicacion(String idUbicacion)
			throws DepositoException {

		// //Auditoria
		// LoggingEvent event = AuditDeposito.getLogginEventBajaElemento(this);

		// Comprobar que no tenga elementos
		if (_noAsignableDBEntity.getNumeroElementosUbicacion(idUbicacion) > 0)
			throw new DepositoException(DepositoException.ELEMENT_NOT_EMPTY);

		DepositoVO ubicacion = _depositoDbEntity.loadDeposito(idUbicacion);

		// TODO : PREGUNTAR POR QU� HACE FALTA ESTO ????
		// Comprobar que sea el �ltimo elemento de la fila
		// List siblings = _depositoDbEntity.getDepositos();
		// if (ubicacion.getNumorden().intValue() < siblings.size())
		// throw new DepositoException(DepositoException.NOT_LAST_CHILD);

		// Eliminar la ubicaci�n
		iniciarTransaccion();
		_depositoDbEntity.delete(idUbicacion);
		_numOrdenDBEntity.incrementarNumeroOrden(null, null,
				ubicacion.getIdTipoElemento(), -1);

		// //Auditoria
		// AuditDeposito.addDataLogBajaElemento(event, ubicacion );

		commit();

		return ubicacion;
	}

	private ElementoNoAsignableVO eliminarNoAsignable(String idElemento)
			throws DepositoException {

		// //Auditoria
		// LoggingEvent event = AuditDeposito.getLogginEventBajaElemento(this);

		ElementoNoAsignableVO elementoAEliminar = _noAsignableDBEntity
				.loadElementoNoAsignable(idElemento);

		// Comprobar que sea el �ltimo elemento de la fila
		List siblings = _noAsignableDBEntity.getByIdPadreTipo(
				elementoAEliminar.getIdpadre(),
				elementoAEliminar.getIddeposito(),
				elementoAEliminar.getIdTipoElemento());
		if (elementoAEliminar.getNumorden().intValue() < siblings.size())
			throw new DepositoException(DepositoException.NOT_LAST_CHILD);

		// Comprobar que no contenga huecos ocupados o reservados
		if (_huecoDBEntity
				.checkHuecosByElementoNoAsignable(idElemento, new String[] {
						HuecoVO.OCUPADO_STATE, HuecoVO.RESERVADO_STATE }))
			throw new DepositoException(
					DepositoException.CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS);

		// Eliminar el elemento y sus descendientes
		iniciarTransaccion();

		// Eliminar descendientes
		List idsDescendientesNoAsignables = _noAsignableDBEntity
				.getIdsDescendientes(idElemento);
		idsDescendientesNoAsignables.add(idElemento);
		String[] idsDescendientesNoAsignablesArray = (String[]) idsDescendientesNoAsignables
				.toArray(new String[idsDescendientesNoAsignables.size()]);
		String[] idsElementosAsignables = _asignableDBEntity
				.getIdsElementosAsignables(idsDescendientesNoAsignablesArray);
		if (!ArrayUtils.isEmpty(idsElementosAsignables)) {
			_huecoDBEntity.deleteHuecos(idsElementosAsignables);
			_asignableDBEntity.delete(idsElementosAsignables);
		}
		_noAsignableDBEntity.delete(idsDescendientesNoAsignablesArray);

		if (StringUtils.isBlank(elementoAEliminar.getIdpadre()))
			_numOrdenDBEntity.incrementarNumeroOrden(
					elementoAEliminar.getIddeposito(),
					DepositoVO.ID_TIPO_ELEMENTO_UBICACION,
					elementoAEliminar.getIdTipoElemento(), -1);
		else {
			ElementoNoAsignableVO elementoPadre = _noAsignableDBEntity
					.loadElementoNoAsignable(elementoAEliminar.getIdpadre());
			_numOrdenDBEntity.incrementarNumeroOrden(elementoPadre.getId(),
					elementoPadre.getIdTipoElemento(),
					elementoAEliminar.getIdTipoElemento(), -1);
		}

		// //Auditoria
		// AuditDeposito.addDataLogBajaElemento(event, elementoAEliminar );

		commit();

		return elementoAEliminar;
	}

	private ElementoAsignableVO eliminarAsignable(String idElemento)
			throws DepositoException {

		// //Auditoria
		// LoggingEvent event = AuditDeposito.getLogginEventBajaElemento(this);

		ElementoAsignableVO elementoAEliminar = _asignableDBEntity
				.loadElementoAsignable(idElemento);

		// Comprobar que sea el �ltimo elemento de la fila
		List siblings = _asignableDBEntity
				.loadAsignablesElements(elementoAEliminar.getIdpadre());
		if (elementoAEliminar.getNumorden().intValue() < siblings.size())
			throw new DepositoException(DepositoException.NOT_LAST_CHILD);

		// Comprobar que no contenga huecos ocupados o reservados
		if (_huecoDBEntity
				.checkHuecosByElementoAsignable(idElemento, new String[] {
						HuecoVO.OCUPADO_STATE, HuecoVO.RESERVADO_STATE }))
			throw new DepositoException(
					DepositoException.CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS);

		// Eliminar elemento asignable y sus huecos
		iniciarTransaccion();
		_asignableDBEntity.delete(idElemento);
		ElementoNoAsignableVO elementoPadre = _noAsignableDBEntity
				.loadElementoNoAsignable(elementoAEliminar.getIdpadre());
		_numOrdenDBEntity.incrementarNumeroOrden(elementoPadre.getId(),
				elementoPadre.getIdTipoElemento(),
				elementoAEliminar.getIdTipoElemento(), -1);
		_huecoDBEntity.deleteHuecos(idElemento);

		// //Auditoria
		// AuditDeposito.addDataLogBajaElemento(event, elementoAEliminar );

		commit();

		return elementoAEliminar;
	}

	public ElementoVO eliminarElemento(String idElemento, String idTipoElemento)
			throws DepositoException {

		LoggingEvent event = AuditDeposito.getLogginEventBajaElemento(this);

		checkPermission(DepositoSecurityManager.ELIMINACION_ELEMENTO_ACTION);

		ElementoVO elementoAEliminar = null;
		iniciarTransaccion();
		if (StringUtils.equals(idTipoElemento,
				DepositoVO.ID_TIPO_ELEMENTO_UBICACION))
			elementoAEliminar = eliminarUbicacion(idElemento);
		else {
			TipoElementoVO tipoElemento = getTipoElementoSingleton(idTipoElemento);
			if (tipoElemento.isTipoAsignable())
				elementoAEliminar = eliminarAsignable(idElemento);
			else
				elementoAEliminar = eliminarNoAsignable(idElemento);
		}

		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogBajaElemento(locale, event, elementoAEliminar);

		commit();

		return elementoAEliminar;

	}

	public HuecoVO getInfoHueco(HuecoID idHueco) {
		return _huecoDBEntity.getHueco(idHueco);
	}

	public HuecoVO getHuecoUInstalacion(String unidadInstalacion) {
		return _huecoDBEntity.getHuecoUInstalacion(unidadInstalacion);
	}

	public Collection getEdificios() {
		return _depositoDbEntity.getDepositos();
	}

	// A�adido por Alicia para obtener la lista de dep�sitos f�sicos por
	// nombre
	public Collection getEdificios(String nombre) {
		return _depositoDbEntity.getDepositos(nombre);
	}

	public List getHijosElemento(String idElemento, String idTipoElemento) {
		TipoElementoVO tipoElemento = getTipoElementoSingleton(idTipoElemento);
		List childs = null;
		if (tipoElemento.esTipoUbicacion())
			childs = _noAsignableDBEntity.getByIdPadre(null, idElemento);
		else if (!tipoElemento.isTipoAsignable()) {
			ElementoNoAsignableVO elementoNoAsignable = _noAsignableDBEntity
					.loadElementoNoAsignable(idElemento);
			childs = _noAsignableDBEntity.getByIdPadre(idElemento,
					elementoNoAsignable.getIddeposito());
			childs.addAll(_asignableDBEntity.loadAsignablesElements(idElemento));
		}
		return childs;
	}

	public ElementoVO getElementoPadre(String idElemento, String idTipoElemento) {
		ElementoVO elementoPadre = null;
		ElementoVO elementoDeposito = getInfoElemento(idElemento,
				idTipoElemento);
		if (StringUtils.isNotBlank(elementoDeposito.getIdpadre()))
			elementoPadre = _noAsignableDBEntity
					.loadElementoNoAsignable(elementoDeposito.getIdpadre());
		else
			elementoPadre = _depositoDbEntity.loadDeposito(elementoDeposito
					.getIddeposito());
		return elementoPadre;
	}

	public DepositoVO getUbicacion(String idUbicacion) {
		return _depositoDbEntity.loadDeposito(idUbicacion);
	}

	public ElementoVO getInfoElemento(String idElemento, String idTipoElemento) {
		TipoElementoVO tipoElemento = getTipoElementoSingleton(idTipoElemento);
		ElementoVO elementoDeposito = null;
		if (tipoElemento.esTipoUbicacion()) {
			elementoDeposito = _depositoDbEntity.loadDeposito(idElemento);
			DepositoVO depositoVO = (DepositoVO) elementoDeposito;
			if (depositoVO != null) {
				ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(depositoVO
						.getIdArchivo());
				if (archivoVO != null) {
					depositoVO.setTipoSignaturacion(archivoVO
							.getTiposignaturacion());
				}
			}
		} else if (tipoElemento.isTipoAsignable())
			elementoDeposito = _asignableDBEntity
					.loadElementoAsignable(idElemento);
		else
			elementoDeposito = _noAsignableDBEntity
					.loadElementoNoAsignable(idElemento);
		return elementoDeposito;
	}

	public List getFormatos() {
		return _formatoDBEntity.loadFormatos();
	}

	public List getFormatosVigentes() {
		return _formatoDBEntity.loadFormatosVigentes();
	}

	public FormatoHuecoVO getFormatoByNombre(String nombre) {
		return _formatoDBEntity.getFormatoByName(nombre);
	}

	public List getHuecos(String idAsignable) {
		return _huecoDBEntity.getHuecosEnElemento(idAsignable);
	}

	public List getHuecos2(String idAsignable) {
		return _huecoDBEntity.getHuecosEnElemento2(idAsignable);
	}

	/**
	 * Devuelve la lista de huecos libres u ocupados pero no bloqueados del
	 * elementoAsignable
	 * 
	 * @param String
	 *            idElementoAsignable, id del elemento asignable al que
	 *            pertenecen los huecos devueltos
	 * @return Lista de huecos
	 */
	public List getHuecosNoBloqueados(String idElementoAsignable) {
		return _huecoDBEntity.getHuecosNoBloqueados(idElementoAsignable);
	}

	public List reubicarUnidadesInstalacion(HuecoID[] huecos,
			ElementoVO elementoDestino) throws Exception {
		List listaReubiacion = new ArrayList();
		LoggingEvent event = AuditDeposito.getLoggingEventReubicarUI(this);
		checkPermission(DepositoSecurityManager.REUBICACION_UNIDADES_INSTALACION);
		if (huecos != null && huecos.length > 0) {
			iniciarTransaccion();
			List listaHuecos = _huecoDBEntity.getHuecosYSignaturaXId(huecos);
			HuecoVO unHueco = (HuecoVO) listaHuecos.get(0);
			ElementoAsignableVO elementoOrigen = _asignableDBEntity
					.loadElementoAsignable(unHueco.getIdElemAPadre());
			List huecosDestino = searchNHuecosLibres(elementoDestino.getId(),
					elementoDestino.getIdTipoElemento(), listaHuecos.size(),
					elementoOrigen.getId(), elementoOrigen.getIdTipoElemento(),
					elementoOrigen.getIdFormato());
			HuecoVO destinoUI = null;
			int nRowsUpdated = 0;
			ArchivoVO archivoVO = getArchivoXIdElemento(elementoDestino);
			for (int i = 0; i < huecos.length; i++) {
				unHueco = (HuecoVO) listaHuecos.get(i);
				destinoUI = (HuecoVO) huecosDestino.get(i);

				if (archivoVO != null && archivoVO.isSignaturaAsociadaAHueco()) {
					resignaturarHueco(archivoVO, unHueco, destinoUI, null,
							false);
				}
				// Actualizar el hueco destino
				destinoUI.setIduinstalacion(unHueco.getIduinstalacion());
				ReubicacionUinstVO reubicacion = new ReubicacionUinstVO(
						unHueco, destinoUI);
				listaReubiacion.add(reubicacion);
				nRowsUpdated = _huecoDBEntity
						.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
								destinoUI.getHuecoID(), HuecoVO.OCUPADO_STATE,
								HuecoVO.LIBRE_STATE, unHueco.getIdRelEntrega(),
								unHueco.getIduinstalacion(),
								unHueco.getOrdenenrelacion(), false);
				if (nRowsUpdated == 0) {
					throw new DepositoHuecoReubicarNoLibreException();
				}
			}
			// Liberar los huecos origen
			_huecoDBEntity.setEstadoLibre(huecos);
			Locale locale = getServiceClient().getLocale();
			AuditDeposito.addDataLogReubicarUI(locale, event, listaHuecos,
					huecosDestino);
			commit();
		}
		return listaReubiacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#actualizarSignaturaHueco(gcontrol.vos.ArchivoVO,
	 *      deposito.vos.HuecoVO, java.lang.String)
	 */
	public void actualizarSignaturaHueco(ArchivoVO archivoVO, HuecoVO unHueco,
			String valorSignatura) throws Exception {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		iniciarTransaccion();

		if (archivoVO != null && archivoVO.isSignaturaAsociadaAHueco()) {
			// Comprobar que existe el hueco asociado a la signatura y esta
			// Libre
			HuecoVO destinoUI = depositoBI.getHuecoAsociadoNumeracion(
					archivoVO.getId(), valorSignatura);
			if (destinoUI != null
					&& HuecoVO.LIBRE_STATE.equals(destinoUI.getEstado())) {
				resignaturarHueco(archivoVO, unHueco, destinoUI, null, true);

				// Liberar el hueco origen
				_huecoDBEntity.setEstadoLibre(new HuecoID[] { unHueco
						.getHuecoID() });
			} else {
				throw new DepositoHuecoSignaturaOcupadoException();
			}
		} else {
			// Comprobar que no exista la signatura en el deposito
			boolean existeSignatura = SignaturaUtil.existeSignatura(
					valorSignatura, archivoVO.getId(),
					_unidadInstalacionDBEntity, _unidadInstalacionReeaDBEntity,
					_unidadInstalacionReeaCrDBEntity, _huecoDBEntity,
					ConfigConstants.getInstance().getSignaturacionPorArchivo());
			if (!existeSignatura) {
				resignaturarHueco(archivoVO, unHueco, null, valorSignatura,
						true);
			} else {
				throw new DepositoHuecoSignaturaOcupadoException();
			}
		}

		commit();
	}

	/**
	 * Resignaturar la unidad de instalaci�n y unidades documentales de la
	 * caja
	 * 
	 * @param archivoVO
	 * @param unHueco
	 * @param destinoUI
	 * @throws Exception
	 */
	private void resignaturarHueco(ArchivoVO archivoVO, HuecoVO unHueco,
			HuecoVO destinoUI, String valorSignatura, boolean actualizar)
			throws Exception {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		ConfiguracionFondos configuracionFondos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		// Obtener la caja a partir del iduinstalacion del hueco origen
		UInsDepositoVO unidadInstalacion = _unidadInstalacionDepositoDBEntity
				.getUInstDepositoVOXIdEnDeposito(unHueco.getIduinstalacion());

		int nRowsUpdated = 0;
		String signatura = null;
		String idFormato = null;
		if (archivoVO != null && archivoVO.isSignaturaAsociadaAHueco()) {
			signatura = destinoUI.getNumeracion();
			idFormato = destinoUI.getIdformato();
			historicoUnidadInstalacion(archivoVO.getId(), unidadInstalacion,
					MotivoEliminacionUnidadInstalacion.CAMBIO_SIGNATURA);
		} else {
			signatura = valorSignatura;
			idFormato = unHueco.getIdformato();
		}

		// La signatura de la caja es la signatura del hueco destino o el valor
		// de la nueva signatura
		unidadInstalacion.setSignaturaui(signatura);
		int pos = unidadInstalacion.getIdentificacion().lastIndexOf(
				Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION);
		unidadInstalacion.setIdentificacion(unidadInstalacion
				.getIdentificacion().substring(0, pos + 1) + signatura);
		_unidadInstalacionDepositoDBEntity
				.updateFieldsUInstDepositoVREA(unidadInstalacion);

		// Obtener las unidades documentales de la caja
		List udocs = _udocEnUIDepositoDBEntity
				.getUDocsVOValidadasXIdUInstalacion(unHueco.getIduinstalacion());

		// Recorrerlas y generar la nueva signatura e identificaci�n
		for (Iterator it = udocs.iterator(); it.hasNext();) {
			UDocEnUiDepositoVO udocDep = (UDocEnUiDepositoVO) it.next();
			String backupSignatura = udocDep.getSignaturaudoc();
			FormatoHuecoVO formatoHuecoVO = _formatoDBEntity
					.getFormatoById(idFormato);

			UnidadDocumentalVO udoc = _unidadDocumentalDBEntity
					.getUnidadDocumental(udocDep.getIdunidaddoc());
			SerieVO serie = seriesBI.getSerie(udoc.getIdSerie());

			String nuevaSignatura = SignaturaUtil.getSignaturaUdoc(
					unidadInstalacion.getSignaturaui(), formatoHuecoVO,
					udocDep.getPosudocenui());

			// Si el codigo de la unidad documental es unico
			// Aunque cambie la signatura de la UI no se modificaria ni el
			// codigo ni codigo de referencia de la udoc
			if (!ConfigConstants.getInstance().getCodigoUdocUnico()) {
				udoc.setCodigo(nuevaSignatura);
				StringBuffer codigoReferenciaUdoc = new StringBuffer(
						serie.getCodReferencia()).append(
						configuracionFondos.getDelimitadorCodigoReferencia())
						.append(udoc.getCodigo());

				udoc.setCodReferencia(codigoReferenciaUdoc.toString());
				cuadroBI.updateElementoCF(udoc);
			}
			String nuevaIdentificacionUdoc = Constants
					.getIdentificadorUnidadDocumental(udoc.getCodRefFondo(),
							nuevaSignatura);
			_udocEnUIDepositoDBEntity.updateSignaturaEIdentificacionYPosUdoc(
					unidadInstalacion.getId(), udocDep.getIdunidaddoc(),
					backupSignatura, nuevaSignatura, nuevaIdentificacionUdoc,
					udocDep.getPosudocenui());
		}
		if (actualizar && archivoVO != null
				&& archivoVO.isSignaturaAsociadaAHueco()) {
			// Actualizar el hueco destino
			destinoUI.setIduinstalacion(unHueco.getIduinstalacion());
			nRowsUpdated = _huecoDBEntity
					.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
							destinoUI.getHuecoID(), HuecoVO.OCUPADO_STATE,
							HuecoVO.LIBRE_STATE, unHueco.getIdRelEntrega(),
							unHueco.getIduinstalacion(),
							unHueco.getOrdenenrelacion(), false);
			if (nRowsUpdated == 0) {
				throw new DepositoHuecoReubicarNoLibreException();
			}
		}
	}

	public List getHuecosLibresAsignable(String idElemento) {
		return _huecoDBEntity.getHuecosEnElementoXEstado(idElemento,
				new String[] { HuecoVO.LIBRE_STATE });
	}

	public List searchNHuecosLibres(String idElemento, String idTipoElemento,
			int nHuecos, String idElementoOrigen, String idTipoElementoOrigen,
			String idFormatoOrigen) throws NoAvailableSpaceException {

		return searchNHuecosLibres(idElemento, idTipoElemento, nHuecos,
				idElementoOrigen, idTipoElementoOrigen, idFormatoOrigen, false);
	}

	public List searchNHuecosLibres(String idElemento, String idTipoElemento,
			int nHuecos, String idElementoOrigen, String idTipoElementoOrigen,
			String idFormatoOrigen, boolean recorrerDepositos)
			throws NoAvailableSpaceException {

		ElementoVO elementoVO = getInfoElemento(idElemento, idTipoElemento);
		Integer numHuecosABuscar = new Integer(nHuecos);
		List huecosLibres = searchHuecosLibres(elementoVO, numHuecosABuscar,
				idFormatoOrigen, recorrerDepositos);
		if (huecosLibres != null && (huecosLibres.size() >= nHuecos)) {
			List huecosRequeridos = huecosLibres.subList(0, nHuecos);
			Iterator it = huecosRequeridos.iterator();
			int i = 1;
			while (it.hasNext()) {
				HuecoVO hueco = (HuecoVO) it.next();
				hueco.setOrdenenrelacion(new Integer(i));
				i++;
			}
			return huecosRequeridos;
		}
		throw new NoAvailableSpaceException();
	}

	public void reservarHuecos(List huecos, RelacionEntregaVO relacionEntrega)
			throws ActionNotAllowedException {
		LoggingEvent event = AuditDeposito.getLoggingEventReservaHuecos(this);

		iniciarTransaccion();
		HuecoVO unHueco = null;
		int nRowsUpdated = 0;
		for (Iterator itHuecosVOS = huecos.iterator(); itHuecosVOS.hasNext();) {
			unHueco = (HuecoVO) itHuecosVOS.next();
			nRowsUpdated = _huecoDBEntity.updateEstadoHuecoIdRelacionEntrega(
					new HuecoID(unHueco.getIdElemAPadre(), unHueco
							.getNumorden().intValue()),
					HuecoVO.RESERVADO_STATE, HuecoVO.LIBRE_STATE,
					relacionEntrega.getId(), unHueco.getOrdenenrelacion());

			if (nRowsUpdated == 0) {
				throw new DepositoHuecoReservarNoLibreException();
			}
		}
		_relacionDbEntity.updateEstadoReserva(relacionEntrega.getId(),
				ReservaPrevision.RESERVADA.getIdentificador(),
				relacionEntrega.getIdelmtodreserva(),
				relacionEntrega.getIdtipoelmtodreserva());

		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogReservaHuecos(locale, event, relacionEntrega,
				huecos);
		commit();
	}

	public ElementoNoAsignableVO getNoAsignable(String idNoAsignable) {
		return _noAsignableDBEntity.loadElementoNoAsignable(idNoAsignable);
	}

	public ElementoAsignableVO getAsignable(String idAsignable) {
		return _asignableDBEntity.loadElementoAsignable(idAsignable);
	}

	public List getHuecosReservadosXIdRelacionEntrega(String idRelacionEntrega) {
		return _huecoDBEntity.getHuecosXRelacionEntregaYEstados(
				idRelacionEntrega, new String[] { HuecoVO.RESERVADO_STATE });
	}

	public List[] getHuecosUbicacionSinReservaSuficiente(
			String idAsignableDestino, String idTipoAsignableDestino,
			String idRelacion) throws NoAvailableSpaceException {

		List[] ret = new List[3];

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionRelacionesEntregaBI relacionService = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacion = relacionService
				.getRelacionXIdRelacion(idRelacion);
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacion);
		List huecosVOReservados = null;
		List detalleHuecosVOReservadosALiberar = null;
		List detalleHuecosVOReservados = null;
		String idUbicacionDelDestinoSeleccionado = getUbicacion(
				idAsignableDestino, idTipoAsignableDestino);
		boolean huboCambioDeUbicacion = !(idUbicacionDelDestinoSeleccionado
				.equalsIgnoreCase(relacion.getIddeposito()));

		List unidadesInstalacionRelacion = relacionService
				.getUnidadesInstalacion(relacion.getId(),
						relacion.getTipotransferencia(), true);

		if (relacion.getReservadeposito() == ReservaPrevision.RESERVADA
				.getIdentificador()) {
			// si no hubo cambio de deposito tengo en cuenta los huecos
			// reservados, sino habra de liberarlos
			huecosVOReservados = getHuecosReservadosXIdRelacionEntrega(relacion
					.getId());
			if (huboCambioDeUbicacion
					|| !idAsignableDestino
							.equals(relacion.getIdelmtodreserva())) {
				detalleHuecosVOReservadosALiberar = generarInformeLiberarTodosHuecosReservados(huecosVOReservados);
			} else {
				detalleHuecosVOReservados = generarDatosInformeHuecosReservados(
						huecosVOReservados, unidadesInstalacionRelacion, null,
						relacionPO.isSignaturacionAsociadaAHueco());
			}

		}

		// calculo de huecos necesarios a ocupar
		int huecosNecesarios = unidadesInstalacionRelacion.size();
		if (huecosVOReservados != null && !huboCambioDeUbicacion
				&& idAsignableDestino.equals(relacion.getIdelmtodreserva()))
			huecosNecesarios = huecosNecesarios - huecosVOReservados.size();

		List nuevosHuecosAOcupar = searchNHuecosLibres(idAsignableDestino,
				idTipoAsignableDestino, huecosNecesarios, null, null,
				relacion.getIdFormatoDestino(), true);

		List cajasAOcuparHuecosLibres = unidadesInstalacionRelacion.subList(
				unidadesInstalacionRelacion.size() - huecosNecesarios,
				unidadesInstalacionRelacion.size());

		List detalleHuecosVOAOcupar = generarDatosInformeHuecosLibresAOcupar(
				nuevosHuecosAOcupar, cajasAOcuparHuecosLibres);

		ret[0] = detalleHuecosVOReservados;
		ret[1] = detalleHuecosVOAOcupar;
		ret[2] = detalleHuecosVOReservadosALiberar;

		return ret;

	}

	private String getUbicacion(String idAsignableDestino,
			String idTipoAsignableDestino) {
		if (StringUtils.equals(idTipoAsignableDestino,
				DepositoVO.ID_TIPO_ELEMENTO_UBICACION))
			return idAsignableDestino;
		else {
			ElementoVO elementoVO = getInfoElemento(idAsignableDestino,
					idTipoAsignableDestino);
			return elementoVO.getIddeposito();
		}
	}

	/**
	 * Obtiene el Dep�sito de al que pertenece un elemento asignable.
	 * 
	 * @param idElemento
	 *            IdElemento
	 * @param idTipoElemento
	 *            IdElemento
	 * @return El depo�sito al que pertenece el elemento. null si es una
	 *         ubicaci�n
	 */
	public ElementoVO getDeposito(String idElemento, String idTipoElemento) {
		ElementoVO retorno = null;

		// Si no es una ubicaci�n
		if (!StringUtils.equals(idTipoElemento,
				DepositoVO.ID_TIPO_ELEMENTO_UBICACION)) {
			boolean depositoEncontrado = false;
			String idElementoActual = idElemento;
			String idTipoElementoActual = idTipoElemento;
			while (!depositoEncontrado) {
				ElementoVO padreVO = getElementoPadre(idElementoActual,
						idTipoElementoActual);

				if (padreVO != null) {
					if (DepositoVO.ID_TIPO_ELEMENTO_UBICACION.equals(padreVO
							.getIdTipoElemento())) {
						retorno = getInfoElemento(idElementoActual,
								idTipoElementoActual);
						depositoEncontrado = true;
					} else {
						idElementoActual = padreVO.getIdElemento();
						idTipoElementoActual = padreVO.getIdTipoElemento();
					}
				} else {
					// Para evitar un bucle infinito.
					depositoEncontrado = true;
				}

			}
		}
		return retorno;
	}

	private List generarDatosInformeHuecosLibresAOcupar(List huecosAOcupar,
			List cajasRelacionAOcuparlos) {

		List huecosLibresAOcupar = null;
		Iterator itCajas = cajasRelacionAOcuparlos.iterator();
		for (Iterator itHuecosAOcupar = huecosAOcupar.iterator(); itHuecosAOcupar
				.hasNext() && itCajas.hasNext();) {
			if (huecosLibresAOcupar == null)
				huecosLibresAOcupar = new ArrayList();
			HuecoVO huecoVO = (HuecoVO) itHuecosAOcupar.next();
			huecoVO.setEstado(HuecoVO.LIBRE_STATE);

			IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) itCajas
					.next();
			huecoVO.setIduinstalacion(unidadInstalacion.getId());

			DetalleUbicacion detalle = new DetalleUbicacion();
			detalle.setHueco(huecoVO);
			detalle.setUnidadInstalacion(unidadInstalacion);
			huecosLibresAOcupar.add(detalle);
		}

		return huecosLibresAOcupar;

	}

	public List[] getHuecosUbicacionConReservaSuficiente(
			String idRelacionEntrega) {
		List[] ret = new List[2];

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionRelacionesEntregaBI relacionService = services
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionEABI = services
				.lookupGestionRelacionesEACRBI();
		RelacionEntregaVO relacionVO = relacionService
				.getRelacionXIdRelacion(idRelacionEntrega);
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacionVO);

		List huecosReservados = getHuecosReservadosXIdRelacionEntrega(idRelacionEntrega);

		List unidadesInstalacion = null;
		if (relacionVO.isRelacionConReencajado()) {
			unidadesInstalacion = relacionEABI
					.getUIsReencajado(idRelacionEntrega);
		} else {
			unidadesInstalacion = relacionService
					.getUnidadesInstalacion(idRelacionEntrega);
		}
		List huecosReservadosALiberar = new ArrayList();
		List huecosReservadosAOcupar = generarDatosInformeHuecosReservados(
				huecosReservados, unidadesInstalacion,
				huecosReservadosALiberar,
				relacionPO.isSignaturacionAsociadaAHueco());

		ret[0] = huecosReservadosAOcupar;
		ret[1] = huecosReservadosALiberar;

		return ret;

	}

	private Map transformListUiMap(List listUis) {
		Map map = new LinkedHashMap();
		if (listUis != null) {
			for (Iterator it = listUis.iterator(); it.hasNext();) {
				IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) it
						.next();
				map.put(unidadInstalacion.getId(), unidadInstalacion);
			}
		}

		return map;
	}

	List generarDatosInformeHuecosReservados(List totalHuecosReservados,
			List unidadesInstalacion, List huecosALiberar,
			boolean isSignaturacionAsociadaAHueco) {

		Map mapUis = transformListUiMap(unidadesInstalacion);

		List huecosReservadosAOcupar = new ArrayList();

		if (totalHuecosReservados != null) {
			if (isSignaturacionAsociadaAHueco) {
				List huecosReservadosALiberar = new ArrayList();

				for (Iterator itHuecosReservados = totalHuecosReservados
						.iterator(); itHuecosReservados.hasNext();) {
					HuecoVO huecoVO = (HuecoVO) itHuecosReservados.next();
					Iterator it = mapUis.entrySet().iterator();
					boolean encontrado = false;
					while (it.hasNext()) {
						Entry ui = (Entry) it.next();
						IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) ui
								.getValue();
						if (unidadInstalacion.getSignaturaUI() == null
								|| unidadInstalacion.getSignaturaUI().equals(
										huecoVO.getNumeracion())) {
							huecosReservadosAOcupar
									.add(obtenerDetalleHuecoOcupado(huecoVO,
											unidadInstalacion));
							mapUis.remove(ui.getKey());
							encontrado = true;
							break;
						}
					}

					if (!encontrado)
						huecosReservadosALiberar.add(huecoVO);
				}
				huecosALiberar
						.addAll(generarInformeLiberarTodosHuecosReservados(huecosReservadosALiberar));
			} else {
				Iterator itHuecosReservados = totalHuecosReservados.iterator();
				for (Iterator itCajasRelacion = unidadesInstalacion.iterator(); itCajasRelacion
						.hasNext() && itHuecosReservados.hasNext();) {

					HuecoVO huecoVO = (HuecoVO) itHuecosReservados.next();
					IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) itCajasRelacion
							.next();
					huecosReservadosAOcupar.add(obtenerDetalleHuecoOcupado(
							huecoVO, unidadInstalacion));
				}

				// si sobran huecos => liberarlos
				for (; itHuecosReservados.hasNext();) {
					HuecoVO huecoVO = (HuecoVO) itHuecosReservados.next();
					huecoVO.setEstado(HuecoVO.LIBRE_STATE);

					DetalleUbicacion detalle = new DetalleUbicacion();
					detalle.setHueco(huecoVO);
					huecosALiberar.add(detalle);
				}
			}
		}
		return huecosReservadosAOcupar.size() > 0 ? huecosReservadosAOcupar
				: null;
	}

	private DetalleUbicacion obtenerDetalleHuecoOcupado(HuecoVO huecoVO,
			IUnidadInstalacionVO unidadInstalacion) {
		huecoVO.setIduinstalacion(unidadInstalacion.getId());
		huecoVO.setEstado(HuecoVO.OCUPADO_STATE);
		DetalleUbicacion detalle = new DetalleUbicacion();
		detalle.setHueco(huecoVO);
		detalle.setUnidadInstalacion(unidadInstalacion);
		return detalle;
	}

	private List generarInformeLiberarTodosHuecosReservados(
			List totalHuecosReservados) {
		List huecosALiberar = new ArrayList();
		Iterator itHuecosReservados = totalHuecosReservados.iterator();
		for (; itHuecosReservados.hasNext();) {
			HuecoVO huecoVO = (HuecoVO) itHuecosReservados.next();
			huecoVO.setEstado(HuecoVO.LIBRE_STATE);

			DetalleUbicacion detalle = new DetalleUbicacion();
			detalle.setHueco(huecoVO);
			huecosALiberar.add(detalle);
		}
		return huecosALiberar;
	}

	private int getMaxPosUDocEnUIDeposito(List udocsEnUIDeposito) {
		int max = 0;

		if (udocsEnUIDeposito != null && udocsEnUIDeposito.size() > 0) {
			Iterator it = udocsEnUIDeposito.iterator();
			while (it.hasNext()) {
				UDocEnUiDepositoVO udocEnUIDeposito = (UDocEnUiDepositoVO) it
						.next();
				if (udocEnUIDeposito.getPosudocenui() > max)
					max = udocEnUIDeposito.getPosudocenui();
			}
		}

		return max;
	}

	/**
	 * M�todo com�n para ubicar una Relaci�n, el m�todo que lo llama
	 * deber�a estar dentro de una transacci�n
	 * 
	 * @param relacionBI
	 *            Servicio de relaciones
	 * @param gestionFondos
	 *            Servicio de fondos
	 * @param relacionEntregaVO
	 *            Relaci�n de entrega
	 * @param huecosReservadosAOcupar
	 *            Huecos a ocupar
	 * @param huecosReservadorALiberar
	 *            Huecos a liberar
	 * @param huecosLibresAOcupar
	 *            Huecos libres a ocupar
	 * @param idElementoDestino
	 *            Id del elemento destino
	 * @param tipoElementoDestino
	 *            Tipo del elemento destino
	 * @throws ConcurrentModificationException
	 * @throws RelacionOperacionNoPermitidaException
	 */
	private void ubicarRelacionComun(GestionRelacionesEntregaBI relacionBI,
			GestionRelacionesEACRBI relacionEACRBi,
			GestionFondosBI gestionFondos, RelacionEntregaVO relacionEntregaVO,
			List huecosReservadosAOcupar, List huecosReservadorALiberar,
			List huecosLibresAOcupar, String idElementoDestino,
			String tipoElementoDestino) throws ConcurrentModificationException,
			RelacionOperacionNoPermitidaException,
			DepositoHuecoUbicarNoLibreException {

		List huecosAOcupar = new ArrayList();
		if (huecosReservadosAOcupar != null)
			huecosAOcupar.addAll(huecosReservadosAOcupar);

		if (huecosLibresAOcupar != null)
			huecosAOcupar.addAll(huecosLibresAOcupar);

		ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionEntregaVO
				.getIdarchivoreceptor());

		RelacionEntregaVO relacionEntregaVOBD;
		if (relacionEntregaVO.getIsIngresoDirecto()) {
			relacionEntregaVOBD = relacionBI
					.abrirIngresoDirecto(relacionEntregaVO.getId());
		} else {
			relacionEntregaVOBD = relacionBI
					.abrirRelacionEntrega(relacionEntregaVO.getId());
		}

		if (relacionEntregaVOBD != null
				&& relacionEntregaVOBD.isPuedeSerUbicada()) {

			for (Iterator itHuecosAOcupar = huecosAOcupar.iterator(); itHuecosAOcupar
					.hasNext();) {
				DetalleUbicacion detalleUbicacion = (DetalleUbicacion) itHuecosAOcupar
						.next();

				IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) detalleUbicacion
						.getUnidadInstalacion();
				String idUnidadInstalacion = unidadInstalacion != null ? unidadInstalacion
						.getId() : null;
				HuecoVO huecoVO = detalleUbicacion.getHueco();

				// Si tiene signaturaci�n asociada a hueco signaturar tanto la
				// caja como las unidades documentales
				if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
						.getIdentificador()) {
					if (relacionEntregaVO.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
						if (relacionEntregaVO.isRelacionConReencajado()) {
							relacionEACRBi.updateSignaturaUI(
									idUnidadInstalacion,
									huecoVO.getNumeracion());
							relacionEACRBi
									.updateSignaturaUdocsByIdUinstalacion(idUnidadInstalacion);
						} else {
							_unidadInstalacionReeaDBEntity
									.updateFieldSignatura(idUnidadInstalacion,
											huecoVO.getNumeracion());
						}
					} else {
						_unidadInstalacionDBEntity.updateFieldSignatura(
								idUnidadInstalacion, huecoVO.getNumeracion());
						updateSignaturaUdocsUnidadInstalacion(
								idUnidadInstalacion, huecoVO.getNumeracion(),
								relacionEntregaVO);
					}

					// Cambiar el estado a la relaci�n
					_relacionDbEntity.updateEstado(relacionEntregaVO.getId(),
							EstadoREntrega.SIGNATURIZADA.getIdentificador(),
							DateUtils.getFechaActual(), new Boolean(false));
				}

				String estadoHuecoEsperado = HuecoVO.LIBRE_STATE;
				if (!ListUtils.isEmpty(huecosReservadosAOcupar)
						&& (relacionEntregaVO.getIdelmtodreserva() != null || relacionEntregaVO
								.isConSignatura())) {
					estadoHuecoEsperado = HuecoVO.RESERVADO_STATE;
				}

				int numRowsUpdated = _huecoDBEntity
						.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
								new HuecoID(detalleUbicacion.getHueco()
										.getIdElemAPadre(), detalleUbicacion
										.getHueco().getNumorden().intValue()),
								HuecoVO.OCUPADO_STATE, estadoHuecoEsperado,
								relacionEntregaVO.getId(), idUnidadInstalacion,
								detalleUbicacion.getHueco()
										.getOrdenenrelacion(),
								relacionEntregaVO.isRelacionConReencajado());

				if (numRowsUpdated == 0) {
					throw new DepositoHuecoUbicarNoLibreException();
				}
			}

			if (huecosReservadorALiberar != null) {
				for (Iterator itHuecosALiberar = huecosReservadorALiberar
						.iterator(); itHuecosALiberar.hasNext();) {
					DetalleUbicacion detalleUbicacion = (DetalleUbicacion) itHuecosALiberar
							.next();

					_huecoDBEntity.updateEstadoHuecoIdRelacionEntrega(
							new HuecoID(detalleUbicacion.getHueco()
									.getIdElemAPadre(), detalleUbicacion
									.getHueco().getNumorden().intValue()),
							HuecoVO.LIBRE_STATE, null, relacionEntregaVO
									.getId(), detalleUbicacion.getHueco()
									.getOrdenenrelacion());
				}
			}

			// marca como ubicado y actualizar con la ubicacion del elemento
			// seleccionado
			String idUbicacion = getUbicacion(idElementoDestino,
					tipoElementoDestino);
			IRelacionEntregaDBEntity relacionDBEntity = new RelacionEntregaDBEntityImpl(
					getTransactionManager());
			relacionDBEntity.updateUbicacion(relacionEntregaVO.getId(), true,
					idUbicacion);

			// obtencion de datos del fondo => necesario para la identificacion
			// de
			// la udoc
			String idFondo = relacionEntregaVO.getIdfondodestino();

			FondoVO fondoVO = gestionFondos.getFondoXId(idFondo);
			String codigoFondo = fondoVO.getCodReferencia();
			// copiar unidades de instalacion y unidades documentales de la
			// relacion
			// en el deposito
			IUnidadInstalacionDBEntity uinstalacionDBEntity = new UnidadInstalacionDBEntityImpl(
					getTransactionManager());
			List unidadesDeInstalacion = uinstalacionDBEntity
					.fetchRowsByIdRelacion(relacionEntregaVO.getId(),
							TipoUInstalacion.ALL.getIdentificador());

			String[] idsUis = new String[unidadesDeInstalacion.size()];
			int j = 0;
			for (Iterator it = unidadesDeInstalacion.iterator(); it.hasNext();) {
				UnidadInstalacionVO unaUI = (UnidadInstalacionVO) it.next();
				idsUis[j] = unaUI.getId();
				j++;
			}
			Map mapPartesUdocEnUi = relacionBI.getUdocsEnUIs(idsUis);

			StringBuffer identificacionUdoc = new StringBuffer();
			for (Iterator itUnidadesInstalacion = unidadesDeInstalacion
					.iterator(); itUnidadesInstalacion.hasNext();) {
				identificacionUdoc.setLength(0);
				UnidadInstalacionVO uInstalacion = (UnidadInstalacionVO) itUnidadesInstalacion
						.next();
				UInsDepositoVO uInsDepositoVO = new UInsDepositoVO();

				uInsDepositoVO.setId(uInstalacion.getId());
				uInsDepositoVO.setIdformato(uInstalacion.getIdFormato());
				uInsDepositoVO.setSignaturaui(uInstalacion.getSignaturaUI());
				identificacionUdoc
						.append(codigoFondo)
						.append(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION)
						.append(uInstalacion.getSignaturaUI());
				uInsDepositoVO.setIdentificacion(identificacionUdoc.toString());
				uInsDepositoVO.setFcreacion(DateUtils.getFechaActual());

				int maxPosUDocEnUI = 0;
				// Lo inserto solamente si la unidad de instalacion no existe,
				// en otro caso, necesitamos saber cu�l es la m�xima
				// posici�n de las unidades
				// documentales que ya hay en la caja para poner a las nuevas la
				// posici�n correcta
				if (StringUtils.isEmpty(uInstalacion.getIduiubicada()))
					_unidadInstalacionDepositoDBEntity
							.insertUInstDepositoVO(uInsDepositoVO);
				else {
					List udocsEnUIDeposito = getUDocsEnUInstalacion(uInstalacion
							.getIduiubicada());
					if (udocsEnUIDeposito != null
							&& udocsEnUIDeposito.size() > 0) {
						maxPosUDocEnUI = getMaxPosUDocEnUIDeposito(udocsEnUIDeposito);
					}
				}

				// List partesUdocsDeUInstalacion =
				// relacionBI.getUdocsEnUI(uInstalacion.getId());
				List partesUdocsDeUInstalacion = (List) mapPartesUdocEnUi
						.get(uInstalacion.getId());
				for (Iterator itUDoc = partesUdocsDeUInstalacion.iterator(); itUDoc
						.hasNext();) {
					IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) itUDoc
							.next();
					UDocEnUiDepositoVO udocToDeposito = new UDocEnUiDepositoVO();
					udocToDeposito.setIdunidaddoc(null);// esta sera la q se
														// ponga
					// en el momento de la
					// validacion
					UnidadInstalacionVO caja = _unidadInstalacionDBEntity
							.fetchRowById(parteUDoc.getIdUinstalacionRe());
					if (StringUtils.isNotEmpty(caja.getIduiubicada())) {
						// Si es una unidad de instalaci�n asignada en lugar
						// de creada, no s�lo hay que asignarle el iduiubicada
						// sino que hay que calcular
						// la posici�n real de la unidad documental dentro de
						// la caja
						udocToDeposito.setIduinstalacion(caja.getIduiubicada());
						udocToDeposito.setPosudocenui(maxPosUDocEnUI
								+ parteUDoc.getPosUdocEnUI());
					} else {
						udocToDeposito.setIduinstalacion(parteUDoc
								.getIdUinstalacionRe());
						udocToDeposito.setPosudocenui(parteUDoc
								.getPosUdocEnUI());
					}
					// udocToDeposito.setIduinstalacion(parteUDoc.getIdUinstalacionRe());
					// udocToDeposito.setPosudocenui(parteUDoc.getPosUdocEnUI());

					udocToDeposito.setSignaturaudoc(parteUDoc
							.getSignaturaUdoc());

					udocToDeposito.setIdentificacion(UDocFondo
							.getIdentificacion(codigoFondo,
									parteUDoc.getSignaturaUdoc()));
					udocToDeposito.setIdudocre(parteUDoc.getIdUnidadDoc());
					udocToDeposito.setDescripcion(parteUDoc.getDescContenido());

					_udocEnUIDepositoDBEntity
							.insertUDocEnUiDeposito(udocToDeposito);
				}
			}

		} else {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.X_RELACION_ESTADO_UBICACION_INCORRECTO,
					Constants.BLANK);
		}
	}

	/**
	 * Modifica la signatura de las unidades documentales de una unidad de
	 * instalaci�n
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalaci�n.
	 * @param signaturaUI
	 *            Signatura de la unidad de instalaci�n.
	 * @param relacionEntrega
	 *            Relaci�n de entrega.
	 */
	private void updateSignaturaUdocsUnidadInstalacion(String idUI,
			String signaturaUI, RelacionEntregaVO relacionEntrega) {

		UnidadInstalacionVO ui = _unidadInstalacionDBEntity.fetchRowById(idUI);

		List udocsEnUI = _udocEnUIDBEntity.fetchRowsByUInstalacion(idUI);
		if (!util.CollectionUtils.isEmpty(udocsEnUI)) {
			ParteUnidadDocumentalVO parteUdoc;
			for (int j = 0; j < udocsEnUI.size(); j++) {
				parteUdoc = (ParteUnidadDocumentalVO) udocsEnUI.get(j);

				int udocsUI = 0;
				if (ui.getIduiubicada() != null) {
					udocsUI = _udocEnUIDepositoDBEntity
							.getUdocsXIdUinstalacion(ui.getIduiubicada())
							.size();
					parteUdoc.setPosUdocEnUI(parteUdoc.getPosUdocEnUI()
							+ udocsUI);
				}

				FormatoHuecoVO formatoHuecoVO = _formatoDBEntity
						.getFormatoById(relacionEntrega.getIdformatoui());

				String signaturaUdoc = SignaturaUtil.getSignaturaUdoc(
						ui.getSignaturaUI(), formatoHuecoVO, parteUdoc);

				_udocEnUIDBEntity.updateSignatura(parteUdoc.getIdUnidadDoc(),
						"" + parteUdoc.getNumParteUdoc(), signaturaUdoc);
			}
		}
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#ubicarRelacionConTransact(java
	 * .lang.String, java.util.List, java.util.List, java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void ubicarRelacionConTransact(String idRelacionEntrega,
			List huecosReservadosAOcupar, List huecosReservadorALiberar,
			List huecosLibresAOcupar, String idElementoDestino,
			String tipoElementoDestino) throws ConcurrentModificationException,
			ActionNotAllowedException {

		// Obtener los servicios necesarios
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionFondosBI gestionFondos = services.lookupGestionFondosBI();
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionEABI = services
				.lookupGestionRelacionesEACRBI();

		// Obtener la relaci�n de entrega
		RelacionEntregaVO relacionEntregaVO = relacionBI
				.getRelacionXIdRelacion(idRelacionEntrega);

		// Ubicar la relaci�n
		ubicarRelacionComun(relacionBI, relacionEABI, gestionFondos,
				relacionEntregaVO, huecosReservadosAOcupar,
				huecosReservadorALiberar, huecosLibresAOcupar,
				idElementoDestino, tipoElementoDestino);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#ubicarRelacion(java.lang.String
	 * , java.util.List, java.util.List, java.util.List, java.lang.String,
	 * java.lang.String)
	 */
	public void ubicarRelacion(String idRelacionEntrega,
			List huecosReservadosAOcupar, List huecosReservadorALiberar,
			List huecosLibresAOcupar, String idElementoDestino,
			String tipoElementoDestino) throws ConcurrentModificationException,
			ActionNotAllowedException {

		LoggingEvent event = AuditDeposito.getLoggingEventUbicarRelacion(this);

		// Obtener los servicios necesarios
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionFondosBI gestionFondos = services.lookupGestionFondosBI();
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionEABI = services
				.lookupGestionRelacionesEACRBI();

		// Obtener la relaci�n de entrega
		RelacionEntregaVO relacionEntregaVO = relacionBI
				.getRelacionXIdRelacion(idRelacionEntrega);

		// Iniciar la transacci�n
		iniciarTransaccion();

		// Unicar la relaci�n
		ubicarRelacionComun(relacionBI, relacionEABI, gestionFondos,
				relacionEntregaVO, huecosReservadosAOcupar,
				huecosReservadorALiberar, huecosLibresAOcupar,
				idElementoDestino, tipoElementoDestino);

		// Hacer commit
		commit();

		// Generar auditor�a
		Locale locale = getServiceClient().getLocale();
		AuditDeposito
				.addDataLogUbicarRelacion(locale, event, relacionEntregaVO,
						huecosReservadosAOcupar, huecosLibresAOcupar);
	}

	public ElementoAsignableVO getElementoAsignable(String idAsignable) {
		return _asignableDBEntity.loadElementoAsignable(idAsignable);
	}

	public ElementoNoAsignableVO getElementoNoAsignable(String idNoAsignable) {
		return _noAsignableDBEntity.loadElementoNoAsignable(idNoAsignable);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#getElementosNoAsignables(java.lang.String,
	 *      java.lang.String)
	 */
	public Collection getElementosNoAsignables(String idPadre,
			String idUbicacion) {
		return _noAsignableDBEntity.getByIdPadre(idPadre, idUbicacion);
	}

	/**
	 * Rechaza una solicitud de reserva para una relaci�n de entrega
	 * 
	 * @param relacionVO
	 *            relaci�n para la que se solicita la reserva
	 */
	public void rechazarReserva(RelacionEntregaVO relacionVO)
			throws ActionNotAllowedException {
		LoggingEvent event = AuditDeposito
				.getLoggingEventRechazarReservaHuecos(this);

		iniciarTransaccion();

		_relacionDbEntity.updateEstadoReserva(relacionVO.getId(),
				ReservaPrevision.NO_SE_HA_PODIDO.getIdentificador(),
				relacionVO.getIdelmtodreserva(),
				relacionVO.getIdtipoelmtodreserva());
		Locale locale = getServiceClient().getLocale();
		AuditDeposito
				.addDataLogRechazarReservaHuecos(locale, event, relacionVO);

		commit();
	}

	public int countHuecosEnDepositoXEstadoYFormato(String idDeposito,
			String idFormato, String[] estados) {
		List huecosXDeposito = _huecoDBEntity.getHuecosEnUbicacion(idFormato,
				idDeposito, estados);
		return huecosXDeposito != null ? huecosXDeposito.size() : 0;
	}

	public List getUDocsHueco(HuecoID idHueco) {
		HuecoVO huecoVO = _huecoDBEntity.getHueco(idHueco);
		String idUInstalacion = huecoVO.getIduinstalacion();

		// obtener las udocs del hueco
		return _udocEnUIDepositoDBEntity
				.getUDocsVOXIdUInstalacion(idUInstalacion);
	}

	public List getUDocsEnUInstalacion(String idUInstalacion) {
		return _udocEnUIDepositoDBEntity
				.getUDocsVOXIdUInstalacion(idUInstalacion);
	}

	// public Hueco getHuecoEntity(HuecoVO huecoVO) {
	// return new Hueco(huecoVO,getServiceSession());
	// }

	public UInsDepositoVO getUinsEnDeposito(String idUinsEnDeposito) {
		return _unidadInstalacionDepositoDBEntity
				.getUInstDepositoVOXIdEnDeposito(idUinsEnDeposito);
	}

	/**
	 * Realiza el borrado de una unidad de instalacion por su identificador.
	 * 
	 * @param idUInstaEnDeposito
	 *            Identificador de la unidad.
	 * @throws Exception
	 */
	public void deleteUInstDeposito(String idArchivo,
			UInsDepositoVO uInsDepositoVO, Integer motivo) throws Exception {
		iniciarTransaccion();
		LoggingEvent event = AuditDeposito.getLogginEventBajaUinstalacion(this);

		historicoUnidadInstalacion(idArchivo, uInsDepositoVO, motivo);

		_unidadInstalacionDepositoDBEntity.deleteUInstDeposito(uInsDepositoVO
				.getId());
		AuditDeposito.addDataLogInfoUInstalacion(event, uInsDepositoVO.getId());
		commit();
	}

	/**
	 * Realiza la liberacion de una unidad de instalacion poniendo su estado a
	 * libre 'L' y a nulo el campo IDUINSTALACION
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad a liberar
	 */
	public void liberaUnidadInstalacion(String idUnidadInstalacion) {
		checkPermission(DepositoSecurityManager.MODIFICAR_ELEMENTO_ACTION);
		LoggingEvent event = AuditDeposito
				.getLogginEventLiberaUinstalacion(this);
		iniciarTransaccion();
		_huecoDBEntity.liberaUnidadInstalacion(idUnidadInstalacion);
		AuditDeposito.addDataLogInfoUInstalacion(event, idUnidadInstalacion);
		commit();
	}

	// /**
	// * Obtiene los datos de ocupaci�n de un elemento asignable del fondo
	// f�sico
	// * @param idElementoAsignable Identificador del elemento asignable
	// * @return Datos de ocupaci�n del elemento
	// */
	// public InformeOcupacion getInformeOcupacionElementoAsignable(String
	// idElementoAsignable) {
	// return
	// _ocupacionDBEntity.getInformeOcupacionElementoAsignable(idElementoAsignable);
	// }

	/**
	 * Obtiene los datos de ocupaci�n de una de las ubicaciones del fondo
	 * f�sico
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicaci�n
	 * @return Datos de ocupaci�n de la ubicaci�n
	 */
	public InformeOcupacion getInformeOcupacionDeposito(String idUbicacion) {
		return _ocupacionDBEntity.getInformeOcupacionDeposito(idUbicacion);
	}

	/**
	 * Obtiene los datos de ocupaci�n de una de los elementos no asignables
	 * del fondo f�sico
	 * 
	 * @param idUbicacion
	 *            Identificador de elemento no asignable
	 * @return Datos de ocupaci�n de la ubicaci�n
	 */
	public InformeOcupacion getInformeOcupacionElementoNoAsignable(
			String idElementoNoAsignable) {
		return _ocupacionDBEntity
				.getInformeOcupacionElementoNoAsignable(idElementoNoAsignable);
	}

	/**
	 * Libera los huecos que han sido reservados para ubicar una relaci�n de
	 * entrega
	 * 
	 * @param idRelacion
	 *            Identificador de relaci�n de entrega
	 */
	public void liberarReserva(String idRelacion) {
		iniciarTransaccion();
		_huecoDBEntity.liberarHuecosReservados(idRelacion);
		commit();
	}

	/**
	 * Obtiene los datos de ocupaci�n en el formato indicado de un elemento
	 * del fondo f�sico
	 * 
	 * @param idElemento
	 *            Identificador de elemento del fondo f�sico
	 * @param tipoElemento
	 *            Tipo de elemento del fondo f�sico
	 * @param formato
	 *            Identificador de formato
	 * @return Datos de ocupaci�n con numero de huecos libres, ocupados y
	 *         porcentajes de ocupaci�n de huecos del formato indicado en el
	 *         elemento del fondo f�sico
	 */
	public OcupacionElementoDeposito getDatosOcupacion(String idElemento,
			String tipoElemento, String formato) {
		TipoElementoVO tipo = getTipoElementoSingleton(tipoElemento);
		if (tipo == null)
			throw new ArchivoModelException(getClass(), "getDatosOcupacion",
					"No se reconoce el tipo de elemento: " + tipoElemento);
		OcupacionElementoDeposito datosOcupacion = null;
		if (tipo.isTipoAsignable())
			datosOcupacion = _ocupacionDBEntity.getOcupacionElementoAsignable(
					idElemento, tipoElemento, formato);
		else if (tipoElemento.equals(DepositoVO.ID_TIPO_ELEMENTO_UBICACION))
			datosOcupacion = _ocupacionDBEntity.getOcupacionUbicacion(
					idElemento, formato);
		else
			datosOcupacion = _ocupacionDBEntity
					.getOcupacionElementoNoAsignable(idElemento, tipoElemento,
							formato);
		return datosOcupacion;
	}

	/**
	 * Obtiene los datos de ocupaci�n en el formato indicado de un elemento
	 * del fondo f�sico entre archivos
	 * 
	 * @param idElemento
	 *            Identificador de elemento del fondo f�sico
	 * @param tipoElemento
	 *            Tipo de elemento del fondo f�sico
	 * @param formato
	 *            Identificador de formato
	 * @return Datos de ocupaci�n con numero de huecos libres, ocupados y
	 *         porcentajes de ocupaci�n de huecos del formato indicado en el
	 *         elemento del fondo f�sico
	 */
	public OcupacionElementoDeposito getDatosOcupacionEntreArchivos(
			String idElemento, String tipoElemento, String formato) {
		TipoElementoVO tipo = getTipoElementoSingleton(tipoElemento);
		if (tipo == null)
			throw new ArchivoModelException(getClass(),
					"getDatosOcupacionEntreArchivos",
					"No se reconoce el tipo de elemento: " + tipoElemento);
		OcupacionElementoDeposito datosOcupacion = null;
		if (tipo.isTipoAsignable())
			datosOcupacion = _ocupacionDBEntity.getOcupacionElementoAsignable(
					idElemento, tipoElemento, formato);
		else if (tipoElemento.equals(DepositoVO.ID_TIPO_ELEMENTO_UBICACION))
			datosOcupacion = _ocupacionDBEntity.getOcupacionUbicacion(
					idElemento, formato);
		else
			datosOcupacion = _ocupacionDBEntity
					.getOcupacionElementoNoAsignable(idElemento, tipoElemento,
							formato);
		return datosOcupacion;
	}

	/**
	 * Obtiene el n�mero de huecos de un determinado formato disponibles en
	 * una ubicaci�n del fondo f�sico manejado por el sistema
	 * 
	 * @param idDeposito
	 *            Identificador de ubicaci�n
	 * @param idFormato
	 *            Formato de hueco
	 * @return Numero de huecos disponibles en la ubicaci�n
	 */
	public int getNumeroHuecosDisponibles(String idDeposito, String idFormato) {
		int numeroHuecosDisponibles = _huecoDBEntity.countHuecosEnUbicacion(
				idFormato, idDeposito, new String[] { HuecoVO.LIBRE_STATE });

		int numeroHuecosReservados = _huecoDBEntity
				.countHuecosEnUbicacion(idFormato, idDeposito,
						new String[] { HuecoVO.RESERVADO_STATE });

		numeroHuecosDisponibles = numeroHuecosDisponibles
				+ numeroHuecosReservados;

		int estados[] = { EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador(),
				EstadoREntrega.SIGNATURIZADA.getIdentificador() };

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();

		Collection relacionesPendientesUbicar = _relacionDbEntity
				.getRelacionXUbicacion(idDeposito, estados, idFormato, false);

		for (Iterator i = relacionesPendientesUbicar.iterator(); i.hasNext();) {
			RelacionEntregaVO relacion = (RelacionEntregaVO) i.next();
			numeroHuecosDisponibles = numeroHuecosDisponibles
					- relacionBI.getNUnidadesInstalacion(relacion.getId());
		}

		// Modificado para aqu�l caso en el que inicialmente hab�a huecos
		// disponibles del formato de la relaci�n de entrega,
		// pero que posteriormente fueron cambiados a otro tipo de formato
		// regular y ahora por tanto ya no hay hueco, pero s�
		// puede haber relaciones de entrega pendientes de ubicar. No hay
		// problema puesto que se puede seleccionar otra ubicaci�n,
		// pero no la balda inicial
		return numeroHuecosDisponibles < 0 ? 0 : numeroHuecosDisponibles;
	}

	/**
	 * Obtiene una unidad documental instalada en una determinada posici�n
	 * dentro de una unidad de instalacion
	 * 
	 * @param idUInstalacion
	 *            Identificador de unidad de instalaci�n instalada en un hueco
	 *            del dep�sito f�sico
	 * @param posUdoc
	 *            Posici�n de unidad documental dentro de la unidad de
	 *            instalaci�n
	 * @return Datos de unidad documental instalada en el dep�sito f�sico
	 */
	public UDocEnUiDepositoVO abrirUdocEnUI(String idUInstalacion, int posUdoc)
			throws ActionNotAllowedException {
		UDocEnUiDepositoVO udocEnUI = _udocEnUIDepositoDBEntity.getUdocEnUI(
				idUInstalacion, posUdoc);
		if (udocEnUI.isValidada()) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionUnidadDocumentalBI udocBI = services
					.lookupGestionUnidadDocumentalBI();
			UnidadDocumentalVO udoc = udocBI.getUnidadDocumental(udocEnUI
					.getIdunidaddoc());
			if (udoc.getEstado() == IElementoCuadroClasificacion.ESTADO_ELIMINADO)
				throw new ActionNotAllowedException(
						"Unidad documental en eliminacion", 5,
						ArchivoModules.DEPOSITOS_MODULE);
		}
		return udocEnUI;
	}

	public void guardarUbicacion(DepositoVO ubicacion)
			throws ActionNotAllowedException {
		boolean isAlta = (StringUtils.isBlank(ubicacion.getId()));

		// Auditoria
		LoggingEvent event = AuditDeposito.getLogginEventAltaElemento(this);
		if (!isAlta)
			event = AuditDeposito.getLogginEventModificarElemento(this);

		// comprobacion de permisos
		if (isAlta)
			checkPermission(DepositoSecurityManager.ALTA_ELEMENTO_ACTION);
		else
			checkPermission(DepositoSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		iniciarTransaccion();

		if (isAlta) {
			int orden = -1;

			orden = _numOrdenDBEntity.incrementarNumeroOrden(null, null,
					ubicacion.getIdTipoElemento(), 1);
			while (_depositoDbEntity.isNumOrdenEnUso(orden)) {
				orden = _numOrdenDBEntity.incrementarNumeroOrden(null, null,
						ubicacion.getIdTipoElemento(), 1);
			}

			ubicacion.setNumorden(orden);
			_depositoDbEntity.insertDeposito(ubicacion);

		} else {
			_depositoDbEntity.updateDeposito(ubicacion);
		}

		// Auditoria
		List elementosAAnadir = new ArrayList();
		elementosAAnadir.add(ubicacion);
		Locale locale = getServiceClient().getLocale();
		if (isAlta) {
			AuditDeposito.addDataLogAltaElemento(locale, event, null,
					ubicacion.getIdTipoElemento(), elementosAAnadir);
		} else {
			AuditDeposito.addDataLogModificarElemento(locale, event, null,
					ubicacion);
		}

		commit();
	}

	/**
	 * Obtiene los datos de uno de los formatos de hueco admitidos por el
	 * sistema
	 * 
	 * @param idFormatoHueco
	 *            Identificador del formato
	 * @return Datos de formato de hueco
	 */
	public FormatoHuecoVO getFormatoHueco(String idFormatoHueco) {
		return _formatoDBEntity.loadFormato(idFormatoHueco);
	}

	/**
	 * Crea o actualiza un elemento asignable del dep�sito f�sico gestionado
	 * por el sistema
	 * 
	 * @param infoAsignable
	 *            Datos del elemento asignable a almacenar
	 * @throws ActionNotAllowedException
	 *             Caso de que la creaci�n o actualizaci�n del elemento no
	 *             est� permitida
	 */
	public void guardarAsignable(ElementoAsignableVO infoAsignable,
			ElementoNoAsignableVO padre) throws ActionNotAllowedException {
		// Auditoria
		LoggingEvent event = AuditDeposito
				.getLogginEventModificarElemento(this);
		ElementoAsignableVO currentInfo = _asignableDBEntity
				.loadElementoAsignable(infoAsignable.getId());
		iniciarTransaccion();
		if (!StringUtils.equals(infoAsignable.getIdFormato(),
				currentInfo.getIdFormato())
				|| currentInfo.getNumhuecos() != infoAsignable.getNumhuecos()
				|| currentInfo.getLongitud() != infoAsignable.getLongitud()) {
			FormatoHuecoVO formato = _formatoDBEntity.loadFormato(infoAsignable
					.getIdFormato());
			if (formato.isRegular())
				infoAsignable
						.setNumhuecos((int) (infoAsignable.getLongitud() / formato
								.getLongitud().intValue()));
			_huecoDBEntity.deleteHuecos(infoAsignable.getId());
			HuecoVO unHueco = null;
			StringBuffer buff = new StringBuffer();
			for (int i = 1; i <= infoAsignable.getNumhuecos(); i++) {
				unHueco = new HuecoVO(infoAsignable.getId(), i,
						infoAsignable.getIddeposito());
				unHueco.setPath(buff.append(infoAsignable.getPathName())
						.append("/Hueco ").append(i).toString());
				unHueco.setIdformato(infoAsignable.getIdFormato());
				String codigoOrden;

				TipoElementoVO tipoElemento = _tipoElementoDBEntity
						.getTipoElemento(currentInfo.getIdTipoElemento());

				if (DepositoConstants.ORDENACION_ANCHURA.equals(tipoElemento
						.getTipoord())) {
					codigoOrden = Constants.getCodigoOrdenPorAnchura(unHueco
							.getNumorden().intValue(), Constants.LETRA_HUECO,
							currentInfo.getCodorden());
				} else {
					codigoOrden = Constants.getCodigoOrden(unHueco
							.getNumorden().intValue(), Constants.LETRA_HUECO,
							currentInfo.getCodorden());
				}

				unHueco.setCodorden(codigoOrden);
				unHueco.setTipoord(tipoElemento.getTipoord());

				_huecoDBEntity.insertHueco(unHueco);
				buff.setLength(0);

			}
		}
		_asignableDBEntity.updateAsignable(infoAsignable);
		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogModificarElemento(locale, event, padre,
				infoAsignable);
		commit();
	}

	/**
	 * Crea o actualiza un elemento no asignable del dep�sito f�sico
	 * gestionado por el sistema
	 * 
	 * @param infoNoAsignable
	 *            Datos del elemento no asignable a almacenar
	 * @throws ActionNotAllowedException
	 *             Caso de que la creaci�n o actualizaci�n del elemento no
	 *             est� permitida
	 */
	public List crearElementosAsignables(ElementoNoAsignableVO elementoPadre,
			TipoElementoVO tipoElementos, String idFormato, double longitud,
			int numeroHuecos, int numero) throws ActionNotAllowedException {
		return crearElementosAsignables(elementoPadre, tipoElementos,
				idFormato, longitud, numeroHuecos, numero, null);
	}

	public List crearElementosAsignables(ElementoNoAsignableVO elementoPadre,
			TipoElementoVO tipoElementos, String idFormato, double longitud,
			int numeroHuecos, int numero, HashMap numeracionPos)
			throws ActionNotAllowedException {
		// Auditoria
		LoggingEvent event = AuditDeposito.getLogginEventAltaElemento(this);

		checkPermission(DepositoSecurityManager.ALTA_ELEMENTO_ACTION);

		iniciarTransaccion();

		int orden = _numOrdenDBEntity.incrementarNumeroOrden(
				elementoPadre.getId(), elementoPadre.getIdTipoElemento(),
				tipoElementos.getId(), numero);
		while (_asignableDBEntity.isNumOrdenEnUso(elementoPadre.getId(), orden)) {
			orden = _numOrdenDBEntity.incrementarNumeroOrden(
					elementoPadre.getId(), elementoPadre.getIdTipoElemento(),
					tipoElementos.getId(), numero);
		}

		FormatoHuecoVO formato = _formatoDBEntity.loadFormato(idFormato);
		List elementosCreados = new ArrayList();
		ElementoAsignableVO asignable = null;
		StringBuffer buff = new StringBuffer();
		String idArchivo = null;
		if (numeracionPos != null && idArchivo == null) { // si es el primer
															// elemento y se
															// especificaron
															// numeraciones
			idArchivo = getArchivoXIdElemento(elementoPadre).getId();
		}

		_nsecSigNumHuecoDbEntity.bloquearNumeroSec(idArchivo);
		// long maxNumeracionNumerica=0;
		for (int i = 1; i <= numero; i++) {
			asignable = new ElementoAsignableVO();
			asignable.setIdTipoElemento(tipoElementos.getId());
			asignable.setIdpadre(elementoPadre.getId());
			asignable.setIddeposito(elementoPadre.getIddeposito());
			asignable.setNumorden(orden - numero + i);
			buff.append(tipoElementos.getNombre()).append(" ")
					.append(asignable.getNumorden());
			asignable.setNombre(buff.toString());
			asignable.setIdFormato(idFormato);
			asignable.setLongitud(longitud);
			asignable.setPathName(new StringBuffer(elementoPadre.getPathName())
					.append("/").append(asignable.getNombre()).toString());
			// El c�digo de Orden solo es para elementos que no sean
			// ubicaci�n.
			asignable.setCodorden(Constants.getCodigoOrden(asignable
					.getNumorden().intValue(),
					tipoElementos.getCaracterorden(), elementoPadre
							.getCodorden()));

			if (formato.isRegular())
				asignable.setNumhuecos((int) (longitud / formato.getLongitud()
						.doubleValue()));
			else
				asignable.setNumhuecos(numeroHuecos);
			_asignableDBEntity.insertElemento(asignable);

			buff.setLength(0);
			HuecoVO unHueco = null;
			for (int j = 1; j <= asignable.getNumhuecos(); j++) {
				unHueco = new HuecoVO(asignable.getId(), j,
						asignable.getIddeposito());
				unHueco.setPath(buff.append(asignable.getPathName())
						.append("/Hueco ").append(j).toString());
				unHueco.setIdformato(asignable.getIdFormato());
				// El c�digo de Orden solo es para elementos que no sean
				// ubicaci�n.
				String codigoOrden = null;

				if (DepositoConstants.ORDENACION_ANCHURA.equals(tipoElementos
						.getTipoord())) {
					codigoOrden = Constants.getCodigoOrdenPorAnchura(unHueco
							.getNumorden().intValue(), Constants.LETRA_HUECO,
							asignable.getCodorden());
				} else {
					codigoOrden = Constants.getCodigoOrden(unHueco
							.getNumorden().intValue(), Constants.LETRA_HUECO,
							asignable.getCodorden());
				}

				unHueco.setTipoord(tipoElementos.getTipoord());
				unHueco.setCodorden(codigoOrden);
				if (numeracionPos != null) {
					int indiceNumeracion = (i - 1) * asignable.getNumhuecos()
							+ j;
					String numeracion = (String) numeracionPos.get(""
							+ indiceNumeracion);

					// if(getHuecoAsociadoNumeracion(idArchivo,numeracion)!=null){
					// throw new NumeracionHuecoRepetidaException(numeracion);
					// }

					// unHueco.setMarcas(new Integer(0));
					unHueco.setNumeracion(numeracion);
					if (StringUtils.isNumeric(numeracion)) {
						// long num=Long. parseLong(numeracion);
						// if(maxNumeracionNumerica<num)
						// maxNumeracionNumerica=num;
						unHueco.setMarcas(new Integer(
								MarcaUtil
										.generarMarcas(new int[] { MarcaNumeracionConstants.POSICION_BIT_MARCA_TIPO_NUMERACION })));
					}
				}
				_huecoDBEntity.insertHueco(unHueco);
				if (numeracionPos != null) { // realizamos la compro
					int indiceNumeracion = (i - 1) * asignable.getNumhuecos()
							+ j;
					String numeracion = (String) numeracionPos.get(""
							+ indiceNumeracion);
					if (countHuecosAsociadoNumeracion(idArchivo, numeracion) > 1) {
						rollback();
						throw new NumeracionHuecoRepetidaException(numeracion);
					}
				}
				buff.setLength(0);
			}

			buff.append(elementoPadre.getItemPath()).append("/item")
					.append(asignable.getId());
			asignable.setItemPath(buff.toString());
			elementosCreados.add(asignable);
			buff.setLength(0);
		}
		// actualizar la secuencia al nuevo valor
		if (numeracionPos != null) {
			long numMax = getMaxNumeracionHuecoEnArchivo(idArchivo);
			if (numMax > 0)
				_nsecSigNumHuecoDbEntity.setNumeroSec(numMax + 1, idArchivo);
		}

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogAltaElemento(locale, event, elementoPadre,
				tipoElementos.getId(), elementosCreados);

		commit();

		return elementosCreados;
	}

	public List crearElementosNoAsignables(boolean padreIsUbicacion,
			ElementoNoAsignableVO elementoPadre, TipoElementoVO tipoElementos,
			int numero) throws ActionNotAllowedException {

		// Auditoria
		LoggingEvent event = AuditDeposito.getLogginEventAltaElemento(this);

		checkPermission(DepositoSecurityManager.ALTA_ELEMENTO_ACTION);

		iniciarTransaccion();

		int orden = _numOrdenDBEntity.incrementarNumeroOrden(
				elementoPadre.getId(), elementoPadre.getIdTipoElemento(),
				tipoElementos.getId(), numero);
		while (_noAsignableDBEntity.isNumOrdenEnUso(padreIsUbicacion,
				elementoPadre.getIddeposito(), elementoPadre.getId(),
				tipoElementos.getId(), orden)) {
			orden = _numOrdenDBEntity.incrementarNumeroOrden(
					elementoPadre.getId(), elementoPadre.getIdTipoElemento(),
					tipoElementos.getId(), numero);
		}

		List elementosCreados = new ArrayList();
		ElementoNoAsignableVO noAsignable = null;
		StringBuffer buffNombreElemento = new StringBuffer();
		StringBuffer buffPathElemento = new StringBuffer();
		for (int i = 1; i <= numero; i++) {
			noAsignable = new ElementoNoAsignableVO();
			noAsignable.setIdTipoElemento(tipoElementos.getId());
			noAsignable.setNumorden(orden - numero + i);

			if (!StringUtils.equals(elementoPadre.getIdTipoElemento(),
					DepositoVO.ID_TIPO_ELEMENTO_UBICACION)) {
				noAsignable.setIdpadre(elementoPadre.getId());
			}

			// El c�digo de Orden solo es para elementos que no sean
			// ubicaci�n.
			noAsignable.setCodorden(Constants.getCodigoOrden(noAsignable
					.getNumorden().intValue(),
					tipoElementos.getCaracterorden(), elementoPadre
							.getCodorden()));

			noAsignable.setIddeposito(elementoPadre.getIddeposito());

			buffNombreElemento.append(tipoElementos.getNombre()).append(" ")
					.append(noAsignable.getNumorden());
			noAsignable.setNombre(buffNombreElemento.toString());
			_noAsignableDBEntity.insertElemento(noAsignable);
			buffPathElemento.append(elementoPadre.getItemPath())
					.append("/item").append(noAsignable.getId());
			noAsignable.setItemPath(buffPathElemento.toString());
			elementosCreados.add(noAsignable);
			buffNombreElemento.setLength(0);
			buffPathElemento.setLength(0);
		}

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogAltaElemento(locale, event, elementoPadre,
				tipoElementos.getId(), elementosCreados);

		commit();

		return elementosCreados;
	}

	/**
	 * Obtiene un resumen de la ocupaci�n de un elemento del dep�sito
	 * f�sico gestionado por el sistema
	 * 
	 * @param elementoDeposito
	 *            Elemento del deposito para el que se calcula la ocupaci�n.
	 *            Caso de ser nulo se calcula la ocupaci�n del conjunto del
	 *            dep�sito
	 * @return Resumen de ocupaci�n en el que se indica la cantidad de huecos
	 *         libres, reservados, ocupados e inutilizados.
	 */
	public ResumenOcupacionVO getResumenOcupacion(ElementoVO elementoDeposito) {
		ResumenOcupacionVO resumenOcupacion = null;
		if (elementoDeposito != null) {
			if (StringUtils.equals(elementoDeposito.getIdTipoElemento(),
					DepositoVO.ID_TIPO_ELEMENTO_UBICACION))
				resumenOcupacion = _depositoDbEntity
						.getOcupacion(elementoDeposito.getId());
		} else
			resumenOcupacion = _depositoDbEntity.getOcupacion(null);
		return resumenOcupacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getResumenOcupacionByUbicaciones
	 * (java.util.List)
	 */
	public ResumenOcupacionVO getResumenOcupacionByUbicaciones(List listaIds) {
		return _depositoDbEntity.getOcupacionByIdsUbicacion(listaIds);
	}

	public List getSubtiposTipoElemento(String idTipoElemento, String[] exclude) {
		return _tipoElementoDBEntity.getTiposElemento(idTipoElemento, exclude);
	}

	public void atachUdocToElementoCF(String idUnidadDocumental,
			String idElmentoCF) {
		iniciarTransaccion();
		_udocEnUIDepositoDBEntity.setIDEnCF(idUnidadDocumental, idElmentoCF);
		commit();
	}

	public void setEstadoHuecos(String idAsignable, int[] numeroOrdenHueco,
			String estadoAEstablecer, String estadoHuecoEsperado)
			throws ActionNotAllowedException {

		LoggingEvent event = AuditDeposito.getLogginEventModificarHueco(this);
		Locale locale = getServiceClient().getLocale();

		if (numeroOrdenHueco != null && numeroOrdenHueco.length > 0) {
			int nHuecos = numeroOrdenHueco.length;
			HuecoID idHueco = new HuecoID(idAsignable, 0);

			iniciarTransaccion();
			HuecoVO huecoVO = null;
			int nRowsUpdated = 0;
			for (int i = 0; i < nHuecos; i++) {
				idHueco.setNumorden(numeroOrdenHueco[i]);
				huecoVO = _huecoDBEntity.getHueco(idHueco);
				nRowsUpdated = _huecoDBEntity.updateEstadoHueco(idHueco,
						estadoAEstablecer, estadoHuecoEsperado);

				if (nRowsUpdated == 0) {
					throw new DepositoEstadoHuecoCambiadoException();
				}

				AuditDeposito.addDataLogModificarHueco(locale, event,
						huecoVO.getPath(), huecoVO.getEstado(),
						estadoAEstablecer);

			}

			commit();
		}
	}

	/**
	 * Obtiene los huecos cuyos identificadores se suministran
	 * 
	 * @param idHuecosAMover
	 *            Lista de identificadores de hueco {@link HuecoID}
	 * @return Lista de huecos {@link HuecoVO}
	 */
	public List getHuecos(HuecoID[] idHuecosAMover) {
		return _huecoDBEntity.getHuecosXId(idHuecosAMover);
	}

	// =======================================================================
	// DEP�SITOS ELECTR�NICOS
	// =======================================================================

	/**
	 * Obtiene la lista de dep�sitos electr�nicos.
	 * 
	 * @return Lista de dep�sitos electr�nicos (
	 *         {@link DepositoElectronicoVO}).
	 */
	public List getDepositosElectronicos() {
		return _depositoElectronicoDBEntity.getDepositosElectronicos();
	}

	/**
	 * Obtiene la informaci�n de un dep�sito electr�nico.
	 * 
	 * @param id
	 *            Identificador del dep�sito electr�nico.
	 * @return Dep�sito electr�nico.
	 */
	public DepositoElectronicoVO getDepositoElectronico(String id) {
		return _depositoElectronicoDBEntity.getDepositoElectronico(id);
	}

	/**
	 * Obtiene la informaci�n de un dep�sito electr�nico.
	 * 
	 * @param idExt
	 *            Identificador externo del dep�sito electr�nico.
	 * @return Dep�sito electr�nico.
	 */
	public DepositoElectronicoVO getDepositoElectronicoByIdExt(String idExt) {
		return _depositoElectronicoDBEntity
				.getDepositoElectronicoByIdExt(idExt);
	}

	/**
	 * Crea un dep�sito electr�nico.
	 * 
	 * @param deposito
	 *            Informaci�n del dep�sito electr�nico.
	 * @return Dep�sito electr�nico creado.
	 * @throws DepositoElectronicoAlreadyExistsException
	 *             si ya existe un dep�sito electr�nico con el mismo
	 *             identificador externo.
	 */
	public DepositoElectronicoVO insertDepositoElectronico(
			DepositoElectronicoVO deposito)
			throws DepositoElectronicoAlreadyExistsException {
		Locale locale = getServiceClient().getLocale();
		try {
			LoggingEvent event = AuditDeposito.getLogginEventAltaDepElec(this);
			checkPermission(DepositoSecurityManager.ALTA_ELEMENTO_ACTION);

			iniciarTransaccion();
			deposito = _depositoElectronicoDBEntity
					.insertDepositoElectronico(deposito);
			AuditDeposito.addDataLogDepElec(locale, event, deposito);
			commit();

			return deposito;
		} catch (UniqueKeyException e) {
			throw new DepositoElectronicoAlreadyExistsException();
		}
	}

	/**
	 * Modifica la informaci�n de un dep�sito electr�nico.
	 * 
	 * @param deposito
	 *            Informaci�n del dep�sito electr�nico.
	 * @throws DepositoElectronicoAlreadyExistsException
	 *             si ya existe un dep�sito electr�nico con el mismo
	 *             identificador externo.
	 */
	public void updateDepositoElectronico(DepositoElectronicoVO deposito)
			throws DepositoElectronicoAlreadyExistsException {
		Locale locale = getServiceClient().getLocale();
		try {
			LoggingEvent event = AuditDeposito
					.getLogginEventModificacionDepElec(this);
			checkPermission(DepositoSecurityManager.MODIFICAR_ELEMENTO_ACTION);

			iniciarTransaccion();
			_depositoElectronicoDBEntity.updateDepositoElectronico(deposito);
			AuditDeposito.addDataLogDepElec(locale, event, deposito);
			commit();
		} catch (UniqueKeyException e) {
			throw new DepositoElectronicoAlreadyExistsException();
		}
	}

	/**
	 * Elimina un dep�sito electr�nico.
	 * 
	 * @param deposito
	 *            Dep�sito electr�nico.
	 * @throws DepositoElectronicoEnUsoException
	 *             si el dep�sito est� en uso.
	 */
	private void deleteDepositoElectronico(DepositoElectronicoVO deposito)
			throws DepositoElectronicoEnUsoException {
		Locale locale = getServiceClient().getLocale();
		if (deposito != null) {
			// Comprobar si est� en uso
			if (_docDocumentoCFDBEntity
					.getCountDocumentosByIdExtDeposito(deposito.getIdExt()) == 0) {
				LoggingEvent event = AuditDeposito
						.getLogginEventBajaDepElec(this);

				iniciarTransaccion();
				_depositoElectronicoDBEntity.deleteDepositoElectronico(deposito
						.getId());
				AuditDeposito.addDataLogDepElec(locale, event, deposito);
				commit();
			} else
				throw new DepositoElectronicoEnUsoException();
		}
	}

	/**
	 * Elimina un dep�sito electr�nico.
	 * 
	 * @param id
	 *            Identificador del dep�sito electr�nico.
	 * @throws DepositoElectronicoEnUsoException
	 *             si el dep�sito est� en uso.
	 */
	public void deleteDepositoElectronico(String id)
			throws DepositoElectronicoEnUsoException {
		checkPermission(DepositoSecurityManager.ELIMINACION_ELEMENTO_ACTION);

		// Eliminar el dep�sito electr�nico
		deleteDepositoElectronico(getDepositoElectronico(id));
	}

	/**
	 * Elimina una lista de dep�sitos electr�nicos.
	 * 
	 * @param ids
	 *            Lista de identificadores de dep�sitos electr�nicos.
	 * @return Informaci�n de la eliminaci�n.
	 */
	public ResultadoRegistrosVO deleteDepositosElectronicos(String[] ids) {
		checkPermission(DepositoSecurityManager.ELIMINACION_ELEMENTO_ACTION);

		ResultadoRegistrosVO res = new ResultadoRegistrosVO();

		if (!ArrayUtils.isEmpty(ids)) {
			res.setNumRegistros(ids.length);

			DepositoElectronicoVO deposito;

			for (int i = 0; i < ids.length; i++) {
				// Informaci�n del dep�sito electr�nico
				deposito = getDepositoElectronico(ids[i]);

				try {
					// Eliminar el dep�sito electr�nico
					deleteDepositoElectronico(deposito);

					res.setNumRegistrosTratados(res.getNumRegistrosTratados() + 1);
				} catch (DepositoElectronicoEnUsoException e) {
					res.getErrores()
							.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionError(
											ErrorKeys.DEPOSITO_ELECTRONICO_DELETE_ERROR_ENUSO,
											new Object[] { deposito.getNombre() }));
				}
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#getFormatosRegulares()
	 */
	public List getFormatosRegulares() {
		return _formatoDBEntity.loadFormatosRegulares();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#getFormatosIrregulares()
	 */
	public List getFormatosIrregulares() {
		return _formatoDBEntity.loadFormatosIrregulares();
	}

	public void validateHuecoDestinoParaReubicarUdoc(HuecoID huecoDestinoID,
			UDocEnUiDepositoVO primeraUDocEnUi,
			FormatoHuecoVO formatoHuecoDestino) throws DepositoException {

		HuecoVO huecoVO = _huecoDBEntity.getHueco(huecoDestinoID);
		UInsDepositoVO cajaDestino = _unidadInstalacionDepositoDBEntity
				.getUInstDepositoVOXIdEnDeposito(huecoVO.getIduinstalacion());

		validateHuecoDestinoParaReubicarUdoc(cajaDestino, primeraUDocEnUi,
				formatoHuecoDestino);

	}

	private void validateHuecoDestinoParaReubicarUdoc(
			UInsDepositoVO cajaDestino, UDocEnUiDepositoVO primeraUDocEnUi,
			FormatoHuecoVO formatoHuecoDestino) throws DepositoException {
		// comprobar q no sea el mismo hueco
		if (primeraUDocEnUi.getIduinstalacion().equals(cajaDestino.getId())) {
			throw new DepositoException(
					DepositoException.NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION);
		}
		// comprobar mismo fondo
		StringBuffer codRefFondoCajaDestino = new StringBuffer(cajaDestino
				.getIdentificacion().split("\\.")[0]);
		StringBuffer codRefFondoPrimeraUdoc = new StringBuffer(primeraUDocEnUi
				.getIdentificacion().split(
						Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL)[0]);
		if (!codRefFondoCajaDestino.toString().equals(
				codRefFondoPrimeraUdoc.toString()))
			throw new DepositoException(
					DepositoException.EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO);

		if (!formatoHuecoDestino.isMultidoc()) {
			throw new DepositoException(
					DepositoException.NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC);
		}
	}

	/*
	 * M�todo para actualizar la informaci�n de volumen de las series a las
	 * que pertenecen las unidades documentales reubicadas y la informaci�n de
	 * los huecos afectados por la misma
	 * 
	 * Condiciones de actualizaci�n de volumen de serie: 1. Si la caja destino
	 * existe Si (la caja origen se queda vac�a O la caja origen no contiene
	 * m�s udocs de la misma serie que la reubicada) volumen serie = volumen
	 * serie - volumen caja origen
	 * 
	 * Si la caja destino no contiene ninguna udoc de la misma serie volumen
	 * serie = volumen serie + volumen caja destino
	 * 
	 * 2. Si la caja destino es nueva Si (la caja origen se queda vac�a O la
	 * caja origen no contiene m�s udocs de la misma serie que la reubicada)
	 * volumen serie = volumen caja destino - volumen caja origen
	 * 
	 * Sino (Si la caja origen no se queda vac�a) volumen serie = volumen
	 * serie + volumen caja destino
	 */
	private void actualizacionVolumenesYHuecosReubicarUDocs(String idUIOrigen,
			String idFormatoOrigen, FormatoHuecoVO formatoHDestino,
			String idPadreHuecoOrigen, String idPadreHuecoDestino,
			ArrayList idsSeriesCajaDestino, ArrayList idsSeriesSeleccionadas,
			Locale locale, DataLoggingEvent dataLogEvent, Integer motivo,
			String idArchivo) throws Exception {

		// Calculamos el volumen de las cajas origen y destino
		double volumenCajaOrigen = 0, volumenCajaDestino = 0;

		// Ids de las Distintas Series de las Unidades de la Caja Origen
		ArrayList idsSeriesCajaOrigen = new ArrayList();

		FormatoHuecoVO formatoHOrigen = getFormatoHueco(idFormatoOrigen);
		// FormatoHuecoVO formatoHDestino = getFormatoHueco(idFormatoDestino);

		if (formatoHOrigen != null) {

			if (formatoHOrigen.getLongitud() == null
					|| formatoHOrigen.getLongitud().doubleValue() == Double.MIN_VALUE) {
				volumenCajaOrigen = getLongitudHuecosIrregularesXIdAsignable(idPadreHuecoOrigen);
			} else {
				volumenCajaOrigen = formatoHOrigen.getLongitud().doubleValue();
			}
		}

		if (formatoHDestino != null) {

			if (formatoHDestino.getLongitud() == null
					|| formatoHDestino.getLongitud().doubleValue() == Double.MIN_VALUE) {
				volumenCajaDestino = getLongitudHuecosIrregularesXIdAsignable(idPadreHuecoDestino);
			} else {
				volumenCajaDestino = formatoHDestino.getLongitud()
						.doubleValue();
			}
		}

		List udocsEnUIns = _udocEnUIDepositoDBEntity
				.getUDocsVOXIdUInstalacion(idUIOrigen);

		// Elimino la caja si queda vacia y el parametro de configuracion es N
		// Se supone que todas las udocs son de una misma caja por lo q obtengo
		// la caja de la primera udoc.
		if (CollectionUtils.isEmpty(udocsEnUIns)
				&& !ConfigConstants.getInstance()
						.getMantenerUInstalacionAlCompactar()) {

			// LA CAJA ORIGEN SE QUEDA VACIA Y SE BORRA

			AuditDeposito.addDetalleLogEliminarUInstalacion(locale,
					dataLogEvent, _unidadInstalacionDepositoDBEntity,
					idUIOrigen);
			// _unidadInstalacionDepositoDBEntity,
			// primeraUDocEnUi.getIduinstalacion());

			historicoUnidadInstalacion(idArchivo, idUIOrigen, motivo);
			// _unidadInstalacionDepositoDBEntity.deleteUInstDeposito(primeraUDocEnUi.getIduinstalacion());
			_unidadInstalacionDepositoDBEntity.deleteUInstDeposito(idUIOrigen);

			// actualizar numero de uins de la serie (decremento uins de la
			// serie)

			// Decremento com�n x eliminarse la caja origen
			double incrementoVolumen = (-1) * volumenCajaOrigen;
			int incrementoUIs = 0;

			// Si hab�a unidades en la caja de destino => NO es caja nueva
			if (!CollectionUtils.isEmpty(idsSeriesCajaDestino)) {

				Iterator it = idsSeriesSeleccionadas.iterator();
				while (it.hasNext()) {
					String idSerie = (String) it.next();

					// Si en en la caja de destino hay alguna unidad documental
					// con la misma serie
					if (contains(idSerie, idsSeriesCajaDestino)) {
						/*
						 * CONDICIONES PARA RESTAR UNA UNIDAD DE INSTALACI�N A
						 * LA SERIE - Caja ORIGEN vac�a - Caja DESTINO es una
						 * Caja EXISTENTE - Caja DESTINO tiene alguna UDOC con
						 * la SERIE igual a la que se va mover
						 */
						// getGestionSeriesBI().updateVolumenSerie(idSerie, 0,
						// -1, 0);
						incrementoUIs = -1;
					} else {
						incrementoVolumen = incrementoVolumen
								+ volumenCajaDestino;
					}

					if (incrementoVolumen != 0 || incrementoUIs != 0)
						getGestionSeriesBI().updateVolumenSerieNoTransaccional(
								idSerie);
				}
			} else { // La caja destino ES una caja nueva

				Iterator it = idsSeriesSeleccionadas.iterator();
				while (it.hasNext()) {
					String idSerie = (String) it.next();
					incrementoVolumen = incrementoVolumen + volumenCajaDestino;
					getGestionSeriesBI().updateVolumenSerieNoTransaccional(
							idSerie);
				}
			}

			// liberar el hueco
			// _huecoDBEntity.liberaUnidadInstalacion(primeraUDocEnUi.getIduinstalacion());
			_huecoDBEntity.liberaUnidadInstalacion(idUIOrigen);
		} else {

			// LA CAJA DE ORIGEN NO SE QUEDA VACIA
			double incrementoVolumen = 0;
			int incrementoUIs = 0;

			// Cargar la lista de Ids de Series que quedan en la caja de origen

			for (Iterator itUDocs = udocsEnUIns.iterator(); itUDocs.hasNext();) {
				UDocEnUiDepositoVO uDocEnUi = (UDocEnUiDepositoVO) itUDocs
						.next();
				UnidadDocumentalVO unidadDocumentalVO = _unidadDocumentalDBEntity
						.getUnidadDocumental(uDocEnUi.getIdunidaddoc());

				String idSerie = unidadDocumentalVO.getIdSerie();
				if (!contains(idSerie, idsSeriesCajaOrigen)) {
					idsSeriesCajaOrigen.add(idSerie);
				}
			}

			// Si no hab�a unidades en la caja de destino (Es caja Nueva)

			if (CollectionUtils.isEmpty(idsSeriesCajaDestino)) {
				// CAJA DESTINO NUEVA
				incrementoVolumen = volumenCajaDestino;

				Iterator it = idsSeriesSeleccionadas.iterator();
				while (it.hasNext()) {
					String idSerie = (String) it.next();

					// Si en en la caja de origen queda alguna unidad documental
					// con la misma serie
					if (contains(idSerie, idsSeriesCajaOrigen)) {
						/*
						 * CONDICIONES QUE SE DEBEN CUMPLIR PARA SUMAR UNA
						 * UNIDAD DE INSTALACI�N A LA SERIE - Caja DESTINO es
						 * una Caja NUEVA - En Caja ORIGEN queda alguna UDOC con
						 * la serie que se va a mover
						 */
						// getGestionSeriesBI().updateVolumenSerie(idSerie, 0,
						// 1, 0);
						incrementoUIs = 1;
					} else {
						/*
						 * CONDICIONES PARA RESTAR UNA UNIDAD DE INSTALACI�N A
						 * LA SERIE - Caja ORIGEN tiene UDOCS - Caja DESTINO es
						 * una Caja EXISTENTE - Caja ORIGEN NO tiene ningun UDOC
						 * con la SERIE igual a la que se va mover - Caja
						 * DESTINO tiene alguna UDOC con la SERIE igual a la que
						 * se va mover
						 */

						incrementoUIs = -1;
						incrementoVolumen = incrementoVolumen
								- volumenCajaOrigen;

						/*
						 * COMENTADA CONDICI�N PORQUE LA CAJA DESTINO ES NUEVA
						 * => Si la origen deja de contener udocs de esa serie,
						 * hay que decrementar el n�mero de cajas siempre, sin
						 * condiciones
						 * 
						 * if(contains(idSerie,idsSeriesCajaDestino)){
						 * //getGestionSeriesBI().updateVolumenSerie(idSerie, 0,
						 * -1, 0); incrementoUIs = -1; }
						 */
					}

					if (incrementoVolumen != 0 || incrementoUIs != 0)
						getGestionSeriesBI().updateVolumenSerieNoTransaccional(
								idSerie);
				}
			} else {
				// CAJA DESTINO EXISTENTE

				Iterator it = idsSeriesSeleccionadas.iterator();
				while (it.hasNext()) {
					String idSerie = (String) it.next();

					// Si la en la caja destino no hay ninguna unidad documental
					// con la serie seleccionada.
					if (!contains(idSerie, idsSeriesCajaDestino)) {
						/*
						 * CONDICIONES PARA SUMAR UNA UNIDAD DOCUMENTAL A LA
						 * SERIE - Caja DESTINO es una Caja EXISTENTE - Caja
						 * ORIGEN tiene alguna UDOC con la Serie de la UDOC a
						 * mover - Caja DESTINO NO tiene ninguna UDOC con la
						 * serie de la UDOC a mover
						 */

						incrementoUIs = 1;
						incrementoVolumen = volumenCajaDestino;

						// getGestionSeriesBI().updateVolumenSerie(idSerie, 0,
						// 1, 0);
					} else {
						/*
						 * CONDICIONES PARA RESTAR UNA UNIDAD DE INSTALACI�N A
						 * LA SERIE - Caja ORIGEN tiene UDOCS - Caja DESTINO es
						 * una Caja EXISTENTE - Caja DESTINO tiene alguna UDOC
						 * con la SERIE igual a la que se va mover - Caja ORIGEN
						 * NO tiene ninguna UDOC con la SERIE igual a la que se
						 * va mover
						 */
						if (!contains(idSerie, idsSeriesCajaOrigen)) {
							incrementoUIs = -1;
							incrementoVolumen = (-1) * volumenCajaOrigen;
							// getGestionSeriesBI().updateVolumenSerie(idSerie,
							// 0, -1, 0);
						}
					}

					if (incrementoVolumen != 0 || incrementoUIs != 0)
						getGestionSeriesBI().updateVolumenSerieNoTransaccional(
								idSerie);
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#reubicarUnidadesDocumentales
	 * (java.util.List, java.lang.String)
	 */
	public List reubicarUnidadesDocumentales(String idArchivo,
			List unidadesDocumentales, String idUInstalacionDestino,
			HuecoID huecoID, FormatoHuecoVO formatoHuecoDestino, Integer motivo)
			throws Exception {

		List listaUdocs = new ArrayList();
		GestionUnidadDocumentalBI unidadesService = getGestionUnidadDocumentalBI();

		Locale locale = getServiceClient().getLocale();

		// Ids de las Distintas Series de las Unidades de la Caja Destino
		ArrayList idsSeriesCajaDestino = new ArrayList();
		// Ids de las Distintas Series de las Unidades Seleccionadas
		ArrayList idsSeriesSeleccionadas = new ArrayList();
		String codigoFondo = null;

		if (!CollectionUtils.isEmpty(unidadesDocumentales)) {

			LoggingEvent event = AuditDeposito
					.getLoggingEventReubicarUDocs(this);

			// Comprobar que la caja puede ubicar las Udocs
			UInsDepositoVO cajaDestino = _unidadInstalacionDepositoDBEntity
					.getUInstDepositoVOXIdEnDeposito(idUInstalacionDestino);

			// Obtener la primera unidad documental
			UDocEnUiDepositoVO unaUDocEnUi = (UDocEnUiDepositoVO) unidadesDocumentales
					.get(0);

			UnidadDocumentalVO unaUnidadDocumentalVO = _unidadDocumentalDBEntity
					.getUnidadDocumental(unaUDocEnUi.getIdunidaddoc());
			HuecoVO huecoOrigenVO = getHuecoUInstalacion(unaUDocEnUi
					.getIduinstalacion());

			iniciarTransaccion();

			if (unaUnidadDocumentalVO != null) {
				codigoFondo = unaUnidadDocumentalVO.getCodRefFondo();
			}

			if (cajaDestino == null) {

				// if(unaUnidadDocumentalVO != null) {
				// codigoFondo = unaUnidadDocumentalVO.getCodRefFondo();
				// }

				DepositoVO depositoVO = _depositoDbEntity
						.loadDeposito(huecoOrigenVO.getIddeposito());

				long numeroSignatura = SignaturaUtil.obtenerSignaturaNumerica(
						depositoVO.getIdArchivo(), _nSecUIDBEntity,
						_unidadInstalacionDBEntity,
						_unidadInstalacionReeaDBEntity,
						_unidadInstalacionReeaCrDBEntity, _huecoDBEntity,
						_udocElectronicaDBEntity);
				String signatura = SignaturaUtil
						.formatearSignaturaNumerica(numeroSignatura);

				if (isSignaturacionAsociadaHueco(huecoOrigenVO
						.getIdElemAPadre())) {
					HuecoVO huecoDestino = _huecoDBEntity.getHueco(huecoID);
					if (StringUtils.isNumeric(huecoDestino.getNumeracion())) {
						long numeroSignaturaLong = Long.parseLong(huecoDestino
								.getNumeracion());
						signatura = SignaturaUtil
								.formatearSignaturaNumerica(numeroSignaturaLong);
					} else
						signatura = huecoDestino.getNumeracion();
				}

				String identificacion = Constants
						.getIdentificadorUnidadDeInstalacion(codigoFondo,
								signatura);

				cajaDestino = new UInsDepositoVO();
				cajaDestino.setSignaturaui(signatura);
				cajaDestino.setIdentificacion(identificacion);
				cajaDestino.setIdformato(formatoHuecoDestino.getId());
				cajaDestino.setFcreacion(DateUtils.getFechaActual());
				// cajaDestino.setIdformato(huecoOrigenVO.getIdformato());

				try {
					cajaDestino.setId(GuidManager
							.generateGUID(getServiceClient().getEngine()));
				} catch (Exception e) {
					rollback();
					throw new DepositoException(
							DepositoException.NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO);
				}

				_unidadInstalacionDepositoDBEntity
						.insertUInstDepositoVO(cajaDestino);

				int nRowsUpdated = _huecoDBEntity
						.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
								huecoID, HuecoVO.OCUPADO_STATE,
								HuecoVO.LIBRE_STATE, null, cajaDestino.getId(),
								null, false);

				if (nRowsUpdated == 0) {
					throw new DepositoHuecoCompactarNoLibreException();
				}

				idUInstalacionDestino = cajaDestino.getId();
			} else {
				validateHuecoDestinoParaReubicarUdoc(cajaDestino,
						(UDocEnUiDepositoVO) unidadesDocumentales.get(0),
						formatoHuecoDestino);
			}

			DataLoggingEvent dataLogEvent = AuditDeposito
					.addDataLogReubicarUDocs(locale, event, this,
							unidadesDocumentales, cajaDestino);

			// obtener numero udocs de la caja destino
			List udocsCajaDestino = _udocEnUIDepositoDBEntity
					.getUDocsVOXIdUInstalacion(idUInstalacionDestino);
			int numeroUdocsCajaDestino = 0;
			if (udocsCajaDestino != null)
				numeroUdocsCajaDestino = udocsCajaDestino.size();

			// Rellenar las series de las unidades documentales de la Caja
			// Destino
			if (!CollectionUtils.isEmpty(udocsCajaDestino)) {
				// Cargar la lista de Ids de Series que quedan en la caja de
				// origen
				for (Iterator itUDocs = udocsCajaDestino.iterator(); itUDocs
						.hasNext();) {
					UDocEnUiDepositoVO uDocEnUi = (UDocEnUiDepositoVO) itUDocs
							.next();

					UnidadDocumentalVO unidadDocumentalVO = _unidadDocumentalDBEntity
							.getUnidadDocumental(uDocEnUi.getIdunidaddoc());
					// UdocEnUIPO uDocEnUIPO = (UdocEnUIPO) itUDocs.next();
					String idSerie = unidadDocumentalVO.getIdSerie();
					if (!contains(idSerie, idsSeriesCajaDestino)) {
						idsSeriesCajaDestino.add(idSerie);
					}
				}
			}

			// obtencion de ids de las udocs
			String[] udocsIds = new String[unidadesDocumentales.size()];
			int i = 0;
			for (Iterator itUDocs = unidadesDocumentales.iterator(); itUDocs
					.hasNext();) {
				UDocEnUiDepositoVO uDocEnUi = (UDocEnUiDepositoVO) itUDocs
						.next();
				UnidadDocumentalVO unidadDocumentalVO = _unidadDocumentalDBEntity
						.getUnidadDocumental(uDocEnUi.getIdunidaddoc());
				udocsIds[i] = uDocEnUi.getIdunidaddoc();
				String idSerie = unidadDocumentalVO.getIdSerie();
				i++;

				if (!contains(idSerie, idsSeriesSeleccionadas)) {
					idsSeriesSeleccionadas.add(idSerie);
				}
			}

			// componer y actualizar signatura en cuadro y en deposito
			// int nuevaPosicionEnCaja = numeroUdocsCajaDestino+1;
			int nuevaPosicionEnCaja = 1;
			if (udocsCajaDestino != null && numeroUdocsCajaDestino > 0)
				nuevaPosicionEnCaja = ((UDocEnUiDepositoVO) udocsCajaDestino
						.get(numeroUdocsCajaDestino - 1)).getPosudocenui() + 1;

			int nuevaNumeroSignaturaEnCaja = 1;

			UDocEnUiDepositoVO ultimaUdoc = null;
			int posUltimaUdoc = numeroUdocsCajaDestino - 1;

			if (posUltimaUdoc >= 0) {
				ultimaUdoc = (UDocEnUiDepositoVO) udocsCajaDestino
						.get(numeroUdocsCajaDestino - 1);
			}

			if (ultimaUdoc != null) {
				String signaturaUltimaUdoc = ultimaUdoc.getSignaturaudoc();

				String[] partes = signaturaUltimaUdoc
						.split(Constants.SEPARADOR_PARTES_UNIDAD_INSTALACION);
				String numeroSignaturaUltimaCaja = null;
				if (partes.length == 2) {
					numeroSignaturaUltimaCaja = partes[1];
				}

				// String numeroSignaturaUltimaCaja =
				// signaturaUltimaUdoc.split("/")[1];
				if (numeroSignaturaUltimaCaja != null) {
					nuevaNumeroSignaturaEnCaja = new Integer(
							numeroSignaturaUltimaCaja).intValue() + 1;
				}
			}

			UDocEnUiDepositoVO primeraUDocEnUi = (UDocEnUiDepositoVO) unidadesDocumentales
					.get(unidadesDocumentales.size() - 1);
			String idUIOrigen = primeraUDocEnUi.getIduinstalacion();

			// actualizacion de cuadro
			int numeroDetalle = 0;

			for (Iterator it = unidadesDocumentales.iterator(); it.hasNext();) {

				UDocEnUiDepositoVO uDocEnUi = (UDocEnUiDepositoVO) it.next();
				String nuevaSignatura = null;

				// Si es un formato multidocumento, lleva el orden, sino
				// signatura caja = signatura udoc
				if (formatoHuecoDestino.isMultidoc()) {
					nuevaSignatura = Constants.getSignaturaUnidadDocumental(
							cajaDestino.getSignaturaui(), new Integer(
									nuevaNumeroSignaturaEnCaja++).toString());
				} else {
					nuevaSignatura = cajaDestino.getSignaturaui();
				}

				// StringBuffer nuevaSignatura =
				// composeSignaturaUDoc(cajaDestino.getSignaturaui(),nuevaNumeroSignaturaEnCaja++);
				UnidadDocumentalVO udocEnCuadro = unidadesService
						.getUnidadDocumental(uDocEnUi.getIdunidaddoc());
				String signaturaEnCuadro = udocEnCuadro.getCodigo();
				String signaturaAntigua = uDocEnUi.getSignaturaudoc();

				if (!ConfigConstants.getInstance().getCodigoUdocUnico()) {
					if (signaturaEnCuadro.equals(signaturaAntigua)) {
						unidadesService.modificarSignaturaUDoc(udocEnCuadro,
								nuevaSignatura);
					}
				}

				String nuevaIdentificacionUdoc = Constants
						.getIdentificadorUnidadDocumental(
								udocEnCuadro.getCodRefFondo(), nuevaSignatura);

				_udocEnUIDepositoDBEntity
						.updateSignaturaEIdentificacionYPosUdocYIdUIns(
								idUIOrigen, uDocEnUi.getIdunidaddoc(),
								signaturaAntigua, nuevaSignatura.toString(),
								nuevaIdentificacionUdoc.toString(),
								nuevaPosicionEnCaja, idUInstalacionDestino);

				AuditDeposito.addDetalleLogReubicarUDocs(locale, dataLogEvent,
						numeroDetalle++, signaturaAntigua, nuevaSignatura);

				nuevaPosicionEnCaja++;

				UDocEnUiDepositoVO uDocUbicada = new UDocEnUiDepositoVO();

				uDocUbicada.setSignaturaudoc(nuevaSignatura);
				uDocUbicada.setIdunidaddoc(uDocEnUi.getIdunidaddoc());

				listaUdocs.add(uDocUbicada);
			}

			// Actualizar informaci�n de volumenes de series y de huecos
			// implicados en la reubicaci�n
			actualizacionVolumenesYHuecosReubicarUDocs(idUIOrigen,
					huecoOrigenVO.getIdformato(), formatoHuecoDestino,
					huecoOrigenVO.getIdElemAPadre(), huecoID.getIdpadre(),
					idsSeriesCajaDestino, idsSeriesSeleccionadas, locale,
					dataLogEvent, motivo, idArchivo);

			commit();
		}

		return listaUdocs;
	}

	/**
	 * Comprueba si existe el valor de cadena en el array de cadenas.
	 * 
	 * @param String
	 *            cadena Cadena a buscar
	 * @param String
	 *            [] cadenas Arrya de Strings donde se va a buscar
	 * @return <b>true</b> Si existe cadena en el array <b>false</b> En caso
	 *         contrario.
	 */
	private boolean contains(String cadena, ArrayList listaCadenas) {

		if (StringUtils.isBlank(cadena)
				|| CollectionUtils.isEmpty(listaCadenas)) {
			return false;
		}

		Iterator it = listaCadenas.iterator();

		while (it.hasNext()) {
			String valor = (String) it.next();
			if (cadena.equals(valor)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUDocsValidadasEnUInstalacion
	 * (java.lang.String)
	 */
	public List getUDocsValidadasEnUInstalacion(String idUInstalacion) {
		return _udocEnUIDepositoDBEntity
				.getUDocsVOValidadasXIdUInstalacion(idUInstalacion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUDocsById(java.lang.String
	 * [])
	 */
	public List getUDocsById(String[] idUdocs) {
		return _udocEnUIDepositoDBEntity.getUDocsVOXId(idUdocs);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUDocsById(java.lang.String
	 * [], java.lang.String[])
	 */
	public List getUDocsById(String[] idUdocs, String[] signaturas) {
		return _udocEnUIDepositoDBEntity.getUDocsVOXId(idUdocs, signaturas);
	}

	public List getHuecosParaReubicarUdocs(
			String identificacionUnidadDocumental, String idAsignableDestino) {
		// coincide con el codReferenciaFondo
		// String primerTokenIdentificacionUDoc =
		// identificacionUnidadDocumental.split("-")[0];
		return _huecoDBEntity.getHuecosEnElementoXEstado(idAsignableDestino,
				new String[] { HuecoVO.OCUPADO_STATE, HuecoVO.LIBRE_STATE });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#insertarFormato(deposito.vos
	 * .FormatoHuecoVO)
	 */
	public FormatoHuecoVO insertarFormato(FormatoHuecoVO formato) {
		configFormato(formato);
		// AuditDeposito.

		return _formatoDBEntity.insertFormato(formato);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#modificarFormato(deposito.vos
	 * .FormatoHuecoVO)
	 */
	public void modificarFormato(FormatoHuecoVO formato) {
		configFormato(formato);
		_formatoDBEntity.updateFormato(formato);
	}

	private void configFormato(FormatoHuecoVO formato) {
		formato.setXinfo(null);
		formato.setTipo(1);
		if (!formato.isRegular()) {
			formato.setTipo(0);
			// formato.setMultidoc(false);
			formato.setRegular(false);
			formato.setLongitud(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#isFormatoHuecoEliminable(java
	 * .lang.String)
	 */
	public boolean isFormatoHuecoEliminable(String idFormatoHueco) {
		boolean formatoModificable = false;
		// no debe tener huecos y relaciones con este formato
		formatoModificable = _huecoDBEntity
				.countHuecosByFormato(idFormatoHueco) == 0;
		if (formatoModificable) {
			formatoModificable = _relacionDbEntity
					.countRelacionesXFormato(idFormatoHueco) == 0;
		}
		return formatoModificable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#isFormatoHuecoModificable(java
	 * .lang.String)
	 */
	public boolean isFormatoHuecoModificable(String idFormatoHueco) {
		boolean formatoModificable = false;
		// no debe tener huecos y relaciones con este formato
		formatoModificable = _huecoDBEntity
				.countHuecosByFormato(idFormatoHueco) == 0;
		if (formatoModificable) {
			int[] estados = new int[] { EstadoREntrega.VALIDADA
					.getIdentificador() };
			formatoModificable = _relacionDbEntity
					.countRelacionesNoValidadasXFormato(idFormatoHueco, estados) == 0;
		}
		return formatoModificable;
	}

	public void checkDeleteFormato(String idFormato) throws DepositoException {
		if (!isFormatoHuecoEliminable(idFormato)) {
			throw new DepositoException(
					DepositoException.NO_SE_PUEDE_ELIMINAR_EL_FORMATO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#deleteFormatos(java.lang.String
	 * [])
	 */
	public void deleteFormatos(String[] formatosSeleccionados)
			throws DepositoException {
		for (int i = 0; i < formatosSeleccionados.length; i++) {
			checkDeleteFormato(formatosSeleccionados[i]);
		}

		_formatoDBEntity.deleteFormatos(formatosSeleccionados);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#deleteFormato(java.lang.String)
	 */
	public void deleteFormato(String idFormato) throws DepositoException {
		deleteFormatos(new String[] { idFormato });
	}

	/**
	 * @param elementoVO
	 * @param tipoElementoVO
	 * @param numOrden
	 * @return una lista de los hijos de elementoVO que son de un orden superior
	 *         al numOrden pasado por par�metro
	 */
	public List getHijosElementoMayorQueOrden(ElementoVO elementoVO,
			TipoElementoVO tipoElementoVO, int numOrden) {

		List childs = null;
		if (tipoElementoVO.esTipoUbicacion())
			childs = _noAsignableDBEntity.getByIdPadreMayorQueOrden(null,
					elementoVO.getId(), numOrden);
		else if (!tipoElementoVO.isTipoAsignable()) {
			childs = _noAsignableDBEntity.getByIdPadreMayorQueOrden(
					elementoVO.getId(), elementoVO.getIddeposito(), numOrden);
			childs.addAll(_asignableDBEntity
					.loadAsignablesElementsMayorQueOrden(elementoVO.getId(),
							numOrden));
		}
		return childs;
	}

	/**
	 * @param elementoVO
	 * @param tipoElementoVO
	 * @param numOrden
	 * @return una lista de los hijos de elementoVO que son de un orden mayor o
	 *         igual al numOrden pasado por par�metro
	 */
	public List getHijosElementoMayorIgualQueOrden(ElementoVO elementoVO,
			TipoElementoVO tipoElementoVO, int numOrden) {

		List childs = null;
		if (tipoElementoVO.esTipoUbicacion())
			childs = _noAsignableDBEntity.getByIdPadreMayorIgualQueOrden(null,
					elementoVO.getId(), numOrden);
		else if (!tipoElementoVO.isTipoAsignable()) {
			childs = _noAsignableDBEntity.getByIdPadreMayorIgualQueOrden(
					elementoVO.getId(), elementoVO.getIddeposito(), numOrden);
			childs.addAll(_asignableDBEntity
					.loadAsignablesElementsMayorIgualQueOrden(
							elementoVO.getId(), numOrden));
		}
		return childs;
	}

	/**
	 * @param elementoVO
	 * @return true si el elemento es asignable, false en caso contrario
	 */
	private boolean isAsignable(ElementoVO elementoVO) {
		return (elementoVO instanceof ElementoAsignableVO);
	}

	/**
	 * @param elementoVO
	 * @return true si es el mayor tipo de elemento no asignable, es decir si
	 *         sus hijos son asignables, false en caso contrario
	 */
	private boolean isMayorTipoElementoNoAsignable(ElementoVO elementoVO) {
		List listaHijos = getHijosElemento(elementoVO.getId(),
				elementoVO.getIdTipoElemento());
		if (!ListUtils.isEmpty(listaHijos)) {
			ElementoVO hijoVO = (ElementoVO) listaHijos.get(0);
			return isAsignable(hijoVO);
		}
		return false;
	}

	/**
	 * @param elementoVO
	 * @param tipoElementoVO
	 * @return el hijo de elementoVO cuyo orden es numOrden
	 */
	public ElementoVO getPrimerHijoElemento(ElementoVO elementoVO,
			TipoElementoVO tipoElementoVO) {
		ElementoVO hijo = null;
		if (isMayorTipoElementoNoAsignable(elementoVO)) {
			List hijos = _asignableDBEntity.loadAsignablesElements(elementoVO
					.getId());
			if ((hijos != null) && (hijos.size() > 0)) {
				hijo = (ElementoVO) hijos.get(0);
			}
		} else if (tipoElementoVO.esTipoUbicacion()) {
			List hijos = _noAsignableDBEntity.getByIdPadre(null,
					elementoVO.getId());
			if ((hijos != null) && (hijos.size() > 0)) {
				hijo = (ElementoVO) hijos.get(0);
			}
		} else if (!tipoElementoVO.isTipoAsignable()) {
			List hijos = _noAsignableDBEntity.getByIdPadre(elementoVO.getId(),
					elementoVO.getIddeposito());
			if ((hijos != null) && (hijos.size() > 0)) {
				hijo = (ElementoVO) hijos.get(0);
			}
		}
		return hijo;
	}

	/**
	 * @param elementoVO
	 * @return true si elementoVO es ubicacion false en caso contrario
	 */
	private boolean isUbicacion(ElementoVO elementoVO) {
		return (elementoVO.getIdTipoElemento()
				.equalsIgnoreCase(DepositoConstants.ID_TIPO_ELEMENTO_UBICACION));
	}

	/**
	 * @param elementoVO
	 * @return true si elementoVO es un dep�sito, false en caso contrario
	 */
	private boolean isDeposito(ElementoVO elementoVO) {
		return (elementoVO.getIdTipoElemento()
				.equalsIgnoreCase(DepositoConstants.ID_TIPO_ELEMENTO_DEPOSITO));
	}

	/**
	 * @param elementoVO
	 * @param idFormato
	 * @return devuelve el primer elementoAsignables descendiente de elementoVO
	 *         que cumpla el formato idFormato
	 */
	private ElementoAsignableVO getPrimerElementoAsignable(
			ElementoVO elementoVO, String idFormato) {

		if (isAsignable(elementoVO))
			return (ElementoAsignableVO) elementoVO;

		if (isDeposito(elementoVO)) {
			TipoElementoVO tipoElementoVO = getTipoElementoSingleton(elementoVO
					.getIdTipoElemento());
			ElementoVO hijoDepositoVO = getPrimerHijoElemento(elementoVO,
					tipoElementoVO);
			if (hijoDepositoVO == null)
				return null;
			return _asignableDBEntity.getElementoByMinimoCodOrden(
					hijoDepositoVO.getIddeposito(),
					hijoDepositoVO.getCodorden(), idFormato);
		}
		return _asignableDBEntity
				.getElementoByMinimoCodOrden(elementoVO.getIddeposito(),
						elementoVO.getCodorden(), idFormato);
	}

	/**
	 * @param elementoAsignableVO
	 * @param tipoOrdenacion
	 * @return el c�digo de orden de un elementoAsignable, cuyo orden coincida
	 *         con tipoOrdenacion
	 */
	private String getCodOrden(ElementoAsignableVO elementoAsignableVO,
			Integer tipoOrdenacion) {
		String codOrden = elementoAsignableVO.getCodorden();
		if (DepositoConstants.ORDENACION_ANCHURA.equals(tipoOrdenacion)) {
			String strLastToken = common.util.StringUtils.getLastToken(
					codOrden, Constants.SEPARADOR_COD_ORDEN);
			int lastToken = common.util.StringUtils.numberOfTokens(codOrden,
					Constants.SEPARADOR_COD_ORDEN);
			codOrden = common.util.StringUtils.removeToken(codOrden,
					Constants.SEPARADOR_COD_ORDEN, lastToken);
			codOrden = common.util.StringUtils.insertToken(codOrden,
					strLastToken, Constants.SEPARADOR_COD_ORDEN, lastToken - 1);
		}
		return codOrden;
	}

	/**
	 * @param elementoVO
	 * @param idFormato
	 * @param hashMap
	 *            . El hashMap contiene la lista de huecos libres obtenidos. El
	 *            n�mero de huecos libres restantes que se necesitan
	 * @return los huecos libres existentes a partir de elementoVO
	 * @throws NoAvailableSpaceException
	 */
	private HashMap searchHuecosLibresEnDeposito(ElementoVO elementoVO,
			String idFormato, HashMap hashMap) throws NoAvailableSpaceException {
		List listaTotalHuecos = (List) hashMap
				.get(DepositoConstants.LISTA_HUECOS);
		Integer numHuecosABuscar = (Integer) hashMap
				.get(DepositoConstants.NUM_HUECOS_A_BUSCAR);

		while (elementoVO != null && numHuecosABuscar.intValue() > 0) {

			ElementoAsignableVO elementoAsignableVO = getPrimerElementoAsignable(
					elementoVO, idFormato);
			String codOrdenNextHijoDeposito = null;
			if (elementoAsignableVO != null) {
				TipoElementoVO tipoElementoAsignableVO = getTipoElementoSingleton(elementoAsignableVO
						.getIdTipoElemento());

				HuecoVO huecoOrigenVO = new HuecoVO();
				huecoOrigenVO.setIddeposito(elementoVO.getIddeposito());
				huecoOrigenVO.setIdformato(idFormato);
				huecoOrigenVO.setCodorden(getCodOrden(elementoAsignableVO,
						tipoElementoAsignableVO.getTipoord()));

				codOrdenNextHijoDeposito = getCodOrdenNextHijoDeposito(elementoAsignableVO
						.getCodorden());

				HuecoVO huecoDestinoVO = new HuecoVO();
				huecoDestinoVO.setCodorden(codOrdenNextHijoDeposito);

				List listaHuecos = getHuecosBetweenXEstadoByCodOrden(
						huecoOrigenVO, huecoDestinoVO, tipoElementoAsignableVO
								.getTipoord().intValue(),
						new String[] { HuecoVO.LIBRE_STATE });

				if (!ListUtils.isEmpty(listaHuecos)) {
					listaTotalHuecos.addAll(listaHuecos);
					numHuecosABuscar = new Integer(numHuecosABuscar.intValue()
							- listaHuecos.size());
				}
				elementoVO = getElementoByCodOrden(codOrdenNextHijoDeposito,
						elementoVO.getIddeposito());
			} else
				elementoVO = null;

		}
		hashMap.put(DepositoConstants.NUM_HUECOS_A_BUSCAR, numHuecosABuscar);
		hashMap.put(DepositoConstants.LISTA_HUECOS, listaTotalHuecos);
		return hashMap;
	}

	/**
	 * 
	 * @param elementoVO
	 * @param numHuecosABuscar
	 * @param idFormato
	 * @param recorrerDepositos
	 *            Indica si hay que recorrer todos lo dep�sitos o no.
	 * @return List lista de huecos libres
	 * @throws NoAvailableSpaceException
	 */
	public List searchHuecosLibres(ElementoVO elementoVO,
			Integer numHuecosABuscar, String idFormato,
			boolean recorrerDepositos) throws NoAvailableSpaceException {
		HashMap hashMap = new HashMap(2);
		hashMap.put(DepositoConstants.LISTA_HUECOS, new ArrayList());
		hashMap.put(DepositoConstants.NUM_HUECOS_A_BUSCAR, numHuecosABuscar);

		if (isUbicacion(elementoVO)) {
			TipoElementoVO tipoElementoVO = getTipoElementoSingleton(elementoVO
					.getIdTipoElemento());

			// Obtener el primer dep�sito
			ElementoVO depositoVO = getPrimerHijoElemento(elementoVO,
					tipoElementoVO);

			ElementoAsignableVO elementoAsignableVO = null;
			ElementoVO depositoInicioBusquedaVO = null;
			if (depositoVO != null)
				elementoAsignableVO = getPrimerElementoAsignable(depositoVO,
						idFormato);

			int numorden = -1;
			if (elementoAsignableVO != null) {
				depositoInicioBusquedaVO = getDeposito(
						elementoAsignableVO.getId(),
						elementoAsignableVO.getIdTipoElemento());
				if (depositoInicioBusquedaVO != null)
					numorden = depositoInicioBusquedaVO.getNumorden()
							.intValue() - 1;
			}

			List listaDepositos = getHijosElementoMayorQueOrden(elementoVO,
					tipoElementoVO, numorden);
			if (!ListUtils.isEmpty(listaDepositos)) {
				for (int i = 0; i < listaDepositos.size()
						&& ((Integer) hashMap
								.get(DepositoConstants.NUM_HUECOS_A_BUSCAR))
								.intValue() > 0; i++) {
					depositoVO = (ElementoVO) listaDepositos.get(i);
					hashMap = searchHuecosLibresEnDeposito(depositoVO,
							idFormato, hashMap);
				}
			}
		} else {
			hashMap = searchHuecosLibresEnDeposito(elementoVO, idFormato,
					hashMap);

			// Si se permite recorrer todos los dep�sitos
			if (recorrerDepositos) {
				// Obtener el c�digo de Dep�sito que anterior
				ElementoNoAsignableVO deposito = (ElementoNoAsignableVO) getDeposito(
						elementoVO.getId(), elementoVO.getIdTipoElemento());
				TipoElementoVO tipoElementoVO = getTipoElementoSingleton(DepositoConstants.ID_TIPO_ELEMENTO_UBICACION);
				if (deposito != null) {

					ElementoVO ubicacion = getUbicacion(deposito
							.getIddeposito());
					List listaDepositos = getHijosElementoMayorQueOrden(
							ubicacion, tipoElementoVO, deposito.getNumorden()
									.intValue());
					if (!ListUtils.isEmpty(listaDepositos)) {
						for (int i = 0; i < listaDepositos.size()
								&& ((Integer) hashMap
										.get(DepositoConstants.NUM_HUECOS_A_BUSCAR))
										.intValue() > 0; i++) {
							ElementoVO depositoVO = (ElementoVO) listaDepositos
									.get(i);
							hashMap = searchHuecosLibresEnDeposito(depositoVO,
									idFormato, hashMap);
						}
					}

				}
			}
		}

		List listaHuecos = (List) hashMap.get(DepositoConstants.LISTA_HUECOS);
		if (!ListUtils.isEmpty(listaHuecos)
				&& listaHuecos.size() > numHuecosABuscar.intValue())
			return listaHuecos.subList(0, numHuecosABuscar.intValue());
		return listaHuecos;

	}

	/**
	 * @param codOrden
	 * @return el codigo de orden del siguiente hijo del dep�sito
	 */
	private String getCodOrdenNextHijoDeposito(String codOrden) {
		String token = common.util.StringUtils.getToken(codOrden,
				Constants.SEPARADOR_COD_ORDEN, 2);
		String strNumber = token.substring(1);

		int number = Integer.parseInt(strNumber) + 1;

		codOrden = common.util.StringUtils.getToken(codOrden,
				Constants.SEPARADOR_COD_ORDEN, 1)
				+ Constants.SEPARADOR_COD_ORDEN
				+ token.substring(0, 1)
				+ common.util.StringUtils.addCharacterAtLeft(
						String.valueOf(number),
						DepositoConstants.LENGTH_CODIGO_ORDEN_ELEMENTO, "0");

		return codOrden;

	}

	/**
	 * 
	 * @param huecoOrigenVO
	 * @param huecoDestinoVO
	 * @param tipoOrdenacion
	 * @param estados
	 * @return Lista de huecos que tengan un codigo de orden mayor o igual que
	 *         el del huecoOrigen y menor que el del hueco destino y que cumplan
	 *         el tipo de ordenaci�n tipoOrdenacion
	 */
	public List getHuecosBetweenXEstadoByCodOrden(HuecoVO huecoOrigenVO,
			HuecoVO huecoDestinoVO, int tipoOrdenacion, String[] estados) {
		return _huecoDBEntity.getHuecosBetweenXEstadoByCodOrden(huecoOrigenVO,
				huecoDestinoVO, tipoOrdenacion, estados);
	}

	/**
	 * 
	 * @param codOrden
	 * @param idDeposito
	 * @return un elementoVO que contenga como codigo de orden codOrden y
	 *         pertenecza al idDeposito
	 */

	public ElementoVO getElementoByCodOrden(String codOrden, String idDeposito) {
		ElementoVO elementoVO = _asignableDBEntity.getElementoByCodOrden(
				codOrden, idDeposito);
		if (elementoVO == null)
			elementoVO = _noAsignableDBEntity.getElementoByCodOrden(codOrden,
					idDeposito);

		return elementoVO;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUbicacionesXIdsArchivo(java
	 * .lang.String[])
	 */
	public List getUbicacionesXIdsArchivo(String[] idsArchivo) {
		return _depositoDbEntity.getUbicacionesXIdsArchivo(idsArchivo);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUDocsEnPrestamoByEstado(
	 * java.util.List, int[])
	 */
	public List getUDocsEnPrestamoByEstado(List idsUDocs, int[] estados) {
		return _detalleDBEntity.getUDocsEnPrestamoByEstado(idsUDocs, estados);
	}

	public List getUdocsNoDisponiblesParaRelacion(String idUinstalacion) {
		return _detalleDBEntity.getUDocsNoDispoblesParaRelacion(idUinstalacion);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getNumHuecosNoLibres(java.lang
	 * .String)
	 */
	public int getNumHuecosNoLibres(String idDeposito) {
		return _huecoDBEntity.getNumHuecosNoLibres(idDeposito);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#organizarUDocsEnUInst(java.
	 * util.List)
	 */
	public void organizarUDocsEnUInst(List listaUDocs,
			List listaUDocsEliminadas, String signaturaCaja) {

		if (listaUDocs != null) {
			iniciarTransaccion();

			// Eliminar las Partes de Unidades Documentales
			if (!ListUtils.isEmpty(listaUDocsEliminadas)) {
				Iterator iterador = listaUDocsEliminadas.iterator();
				while (iterador.hasNext()) {
					UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) iterador
							.next();
					_udocEnUIDepositoDBEntity.deleteUdoc(
							udoc.getIduinstalacion(), udoc.getIdunidaddoc(),
							udoc.getSignaturaudoc());
				}
			}

			for (int pos = 0; pos < listaUDocs.size(); pos++) {
				UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) listaUDocs
						.get(pos);
				// Esto se hace porque la posici�n de la lista empieza en cero
				// y nuestras posiciones deben comenzar en 1
				int posReal = pos + 1;

				if (posReal != udoc.getPosudocenui()
						&& udoc.getPosudocenui() != -1) {
					udoc.setPosudocenui(posReal);
					_udocEnUIDepositoDBEntity.updatePosicionUDoc(udoc);
				}
			}

			// Una vez que se han modificado las posiciones, debemos modificar
			// las identificaciones
			// y signaturas
			// Recorrer la lista para ir actualizando la signatura y la
			// identificaci�n
			for (int pos = 0; pos < listaUDocs.size(); pos++) {
				UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) listaUDocs
						.get(pos);
				// Esto se hace porque la posici�n de la lista empieza en cero
				// y nuestras posiciones deben comenzar en 1
				int posReal = pos + 1;

				String signaturaUDoc = Constants.getSignaturaUnidadDocumental(
						signaturaCaja, (new Integer(posReal)).toString());

				if (udoc.getSignaturaudoc() == null
						|| !signaturaUDoc.equals(udoc.getSignaturaudoc())) {
					boolean actualizar = true;

					// Si el no hay signatura, hay que insertar, ya que es una
					// parte nueva.
					if (udoc.getSignaturaudoc() == null) {
						actualizar = false;
						udoc.setPosudocenui(posReal);
					}

					String signaturaAntigua = udoc.getSignaturaudoc();

					// Modificar la identificaci�n
					String identificacion = udoc.getIdentificacion();
					String[] partesIdentificacion = identificacion
							.split(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL);
					identificacion = partesIdentificacion[0]
							+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL
							+ signaturaUDoc;

					udoc.setIdentificacion(identificacion);
					// Obtener la nueva signatura
					udoc.setSignaturaudoc(signaturaUDoc);

					if (actualizar) {
						_udocEnUIDepositoDBEntity
								.updateIdentificacionYSignaturaUDoc(udoc);

						ElementoCuadroClasificacionVO elemento = _elementoCuadro
								.getElementoCuadroClasificacion(udoc
										.getIdunidaddoc());
						if (elemento != null) {
							// Solo se modifica si la signatura se correponde
							// con la signatura antigua ya que si la unidad
							// documental tiene partes, solo est� introducida
							// la signatura de la primera.
							if (signaturaAntigua.equals(elemento.getCodigo())
									&& !ConfigConstants.getInstance()
											.getCodigoUdocUnico()) {
								ElementoCuadroClasificacionVO elementoPadre = _elementoCuadro
										.getElementoCuadroClasificacion(elemento
												.getIdPadre());

								if (elementoPadre != null) {
									String codReferencia = elementoPadre
											.getCodReferencia();
									String separador = ConfiguracionSistemaArchivoFactory
											.getConfiguracionSistemaArchivo()
											.getConfiguracionFondos()
											.getDelimitadorCodigoReferencia();
									codReferencia += separador + signaturaUDoc;
									elemento.setCodReferencia(codReferencia);
								}
								elemento.setCodigo(signaturaUDoc);

								_elementoCuadro
										.updateElementoCuadroClasificacion(elemento);
							}
						}

					} else {
						_udocEnUIDepositoDBEntity.insertUDocEnUiDeposito(udoc);
					}

				}
			}

			commit();
		}
	}

	/**
	 * @param elementoVO
	 * @param idFormato
	 * @param hashMap
	 *            . El hashMap contiene la lista de huecos libres obtenidos. El
	 *            n�mero de huecos libres restantes que se necesitan
	 * @return los huecos libres existentes a partir de elementoVO
	 * @throws NoAvailableSpaceException
	 */
	/*
	 * private List searchIdsAsignablesEnElemNoAsigNoRaiz(String [] idsAmbito,
	 * String idFormato,int numMaxResults) throws TooManyResultsException{
	 * HashSet setTotalAsignables=new HashSet(); List listaTotalAsignables=new
	 * ArrayList(); boolean formatoVacio=StringUtils.isEmpty(idFormato);
	 * 
	 * for(int i=0;i<idsAmbito.length;i++){ ElementoVO
	 * elementoVO=getNoAsignable(idsAmbito[i]); if(elementoVO==null){
	 * elementoVO=getAsignable(idsAmbito[i]); if(elementoVO!=null){ //comprobar
	 * que el formato sea el adecuado sino es asi se pasa a la siguiente
	 * if(!formatoVacio &&
	 * !((ElementoAsignableVO)elementoVO).getIdFormato().equals(idFormato))
	 * continue;
	 * checkAndInsertsIfNotPresent(setTotalAsignables,listaTotalAsignables,
	 * elementoVO.getId(),numMaxResults); continue; } }
	 * 
	 * //recorrido en profundidad List
	 * listaHijosDirectos=getHijosElemento(elementoVO);
	 * 
	 * if(listaHijosDirectos==null) continue; ElementoVO elem=null;
	 * 
	 * while(listaHijosDirectos.size()>0){
	 * elem=(ElementoVO)listaHijosDirectos.remove(0); if(elem.isAsignable()){
	 * if(!formatoVacio &&
	 * !((ElementoAsignableVO)elem).getIdFormato().equals(idFormato)) continue;
	 * checkAndInsertsIfNotPresent(setTotalAsignables,listaTotalAsignables,
	 * elem.getId(),numMaxResults); }else{ List
	 * listaHijos=getHijosElemento(elem); if(listaHijos!=null ||
	 * listaHijos.size()>0) listaHijosDirectos.addAll(0,getHijosElemento(elem));
	 * } } } return new ArrayList(listaTotalAsignables); }
	 * 
	 * /*private void checkAndInsertsIfNotPresent(HashSet set,List lista, String
	 * id,int numMaxResults) throws TooManyResultsException{
	 * if(!set.contains(id)){ set.add(id); lista.add(id);
	 * if(lista.size()>numMaxResults){ throw new
	 * TooManyResultsException(lista.size(),numMaxResults); } } }
	 * 
	 * private List getHijosElemento(ElementoVO elementoVO) {
	 * if(elementoVO.isAsignable()) return new ArrayList(); List
	 * lista=_noAsignableDBEntity.getByIdPadre(elementoVO.getIdElemento(),
	 * elementoVO.getIddeposito()); if(lista==null || lista.size()==0){
	 * lista=_asignableDBEntity
	 * .loadAsignablesElements(elementoVO.getIdElemento()); } return lista; }
	 */

	private List getIds(List list) {

		List listIds = new ArrayList();
		if (!ListUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				BusquedaUInsDepositoVO busquedaUInsDepositoVO = (BusquedaUInsDepositoVO) list
						.get(i);
				listIds.add(busquedaUInsDepositoVO.getId());
			}
		}

		return listIds;
	}

	// Utilizado para obtener los objetos a partir del id (lista con paginacion
	// externa)
	public List getUnidsInstalacion(List listaIds,
			BusquedaElementosVO busquedaElementosVO) {
		return _unidadInstalacionDepositoDBEntity.getUnidsInstalacion(listaIds,
				busquedaElementosVO);
	}

	public List getIdsUnidsInstalacion(String idUbicacion,
			String[] codsOrdenAmbitos, String like_sig, String signatura,
			String idFondo, String idFormato, int numMaxResults,
			CustomDateRange rangoFechas) throws TooManyResultsException {
		// obtener la lista con los codigos de orden de los ambitos
		// (asignables/no asignables) seleccionados
		List listaCodsOrdenAmbitos = Arrays.asList(codsOrdenAmbitos);
		List list = _unidadInstalacionDepositoDBEntity.getIdsUnidsInstalacion(
				idUbicacion, listaCodsOrdenAmbitos, like_sig, signatura,
				idFondo, idFormato, numMaxResults, rangoFechas);
		// List
		// listaIdsAsignables=searchIdsAsignablesEnElemNoAsigNoRaiz(idsAmbitos,idFormato,numMaxResults);
		// List
		// list=_unidadInstalacionDepositoDBEntity.getIdsUnidsInstalacion(listaIdsAsignables,
		// like_sig, signatura, idFondo,numMaxResults);
		return getIds(list);
	}

	public ElementoVO getElemento(String id) {
		ElementoNoAsignableVO noAsignable = getNoAsignable(id);
		if (noAsignable == null)
			return getAsignable(id);
		return noAsignable;
	}

	public List getIdsUnidsInstalacion(String idUbicacion, String like_sig,
			String signatura, String idFondo, String idFormato,
			int numMaxResults, CustomDateRange rangoFechas)
			throws TooManyResultsException {
		List list = _unidadInstalacionDepositoDBEntity.getIdsUnidsInstalacion(
				idUbicacion, like_sig, signatura, idFondo, idFormato,
				numMaxResults, rangoFechas);
		return getIds(list);
	}

	public boolean isBloqueada(String idUnidadDocumental) {

		boolean isBloqueada = false;
		isBloqueada = _udocEnUIDepositoDBEntity.isBloqueada(idUnidadDocumental);
		if (!isBloqueada) {
			if (_unidadDocumentalElectronicaDBEntity
					.getMarcasBloqueo(idUnidadDocumental) == 0)
				isBloqueada = false;
			else
				isBloqueada = true;
		}
		return isBloqueada;
	}

	public double getVolumenHuecosIrregularesXIdRelEntrega(
			String idRelacionEntrega) {
		double longitud = 0;
		List elementos = _asignableDBEntity
				.getLongitudElementosXRelacionEntrega(idRelacionEntrega);
		for (Iterator iter = elementos.iterator(); iter.hasNext();) {
			ElementoAsignableVO elementoAsignableVO = (ElementoAsignableVO) iter
					.next();
			longitud += (elementoAsignableVO.getLongitud() / elementoAsignableVO
					.getNumhuecos()) * elementoAsignableVO.getNumHijos();
		}
		return longitud;
	}

	public double getVolumenHuecosIrregularesXIdRelEntregaEntreArchivos(
			String idRelacionEntrega) {
		double longitud = 0;
		List unidadesInstalacion = _unidadInstalacionReeaDBEntity
				.getUnidadesInstalacionXIdRelacionEntreArchivos(idRelacionEntrega);
		for (Iterator iter = unidadesInstalacion.iterator(); iter.hasNext();) {
			UnidadInstalacionReeaVO unidadInstalacionVO = (UnidadInstalacionReeaVO) iter
					.next();
			longitud += getLongitudHuecosIrregularesXIdAsignable(unidadInstalacionVO
					.getIdElemaPadreHuecoOrigen());
		}
		return longitud;
	}

	public double getLongitudHuecosIrregularesXIdUInstalacion(
			String idUInstalacion) {
		double longitud = 0;
		HuecoVO hueco = _huecoDBEntity.getHuecoUInstalacion(idUInstalacion);
		String idAsignablePadre = null;
		if (hueco != null && StringUtils.isNotEmpty(hueco.getIdElemAPadre()))
			idAsignablePadre = hueco.getIdElemAPadre();
		longitud = getLongitudHuecosIrregularesXIdAsignable(idAsignablePadre);
		return longitud;
	}

	public double getLongitudHuecosIrregularesXIdAsignable(String idAsignable) {
		double longitud = 0;
		if (StringUtils.isNotEmpty(idAsignable)) {
			ElementoAsignableVO elementoAsignableVO = _asignableDBEntity
					.loadElementoAsignable(idAsignable);
			if (elementoAsignableVO != null) {
				double longitudAsignable = elementoAsignableVO.getLongitud();
				int numHuecos = elementoAsignableVO.getNumhuecos();
				if (numHuecos >= 0)
					longitud = longitudAsignable / numHuecos;
			}
		}
		return longitud;
	}

	/**
	 * Actualiza el formato del elemento asignable del dep�sito f�sico
	 * gestionado por el sistema, el de los huecos en que se divida y el de las
	 * cajas que contengan esos huecos
	 * 
	 * @param infoAsignable
	 *            Datos del elemento asignable a almacenar
	 * @throws ActionNotAllowedException
	 *             Caso de que la creaci�n o actualizaci�n del elemento no
	 *             est� permitida
	 */

	public void actualizarFormato(ElementoAsignableVO infoAsignable)
			throws ActionNotAllowedException {

		LoggingEvent event = AuditDeposito
				.getLogginEventModificarElemento(this);

		iniciarTransaccion();

		// Actualizamos el formato en el asignable
		_asignableDBEntity.updateAsignable(infoAsignable);

		// Actualizamos el formato en los huecos de la balda y en las cajas de
		// cada hueco
		List huecos = this.getHuecos(infoAsignable.getId());
		if (huecos != null && huecos.size() > 0) {
			Iterator it = huecos.iterator();

			while (it.hasNext()) {
				HuecoVO hueco = (HuecoVO) it.next();
				Map colsHuecoToUpdate = new HashMap();
				colsHuecoToUpdate.put(HuecoDbEntityImplBase.CAMPO_FORMATO,
						infoAsignable.getIdFormato());
				this._huecoDBEntity.updateFieldsHueco(hueco.getIdElemAPadre(),
						hueco.getNumorden().intValue(), colsHuecoToUpdate);

				if (StringUtils.isNotEmpty(hueco.getIduinstalacion())) {
					Map colsUIToUpdate = new HashMap();
					colsUIToUpdate.put(
							UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
							infoAsignable.getIdFormato());
					this._unidadInstalacionDepositoDBEntity
							.updateFieldsUInstDeposito(
									hueco.getIduinstalacion(), colsUIToUpdate);
				}
			}
		}

		commit();

		Locale locale = getServiceClient().getLocale();
		AuditDeposito.addDataLogModificarElemento(locale, event, null,
				infoAsignable);
	}

	/**
	 * Permite obtener los padres de un hueco
	 * 
	 * @param hueco
	 *            Hueco del que se desea obtener los padres
	 * @return Lista de padres de un hueco comenzando desde el primer nivel y
	 *         finalizando en el hueco
	 */
	public LinkedList getListaPadresHueco(HuecoVO hueco) {
		LinkedList ltPadres = new LinkedList();
		ltPadres.addFirst(hueco);

		String idPadreHueco = hueco.getIdElemAPadre();

		ElementoVO elementoPadreHueco = _asignableDBEntity
				.loadElementoAsignable(idPadreHueco);

		if (elementoPadreHueco != null) {
			ltPadres.addFirst(elementoPadreHueco);

			ElementoVO elementoVO = elementoPadreHueco;
			while ((elementoVO != null) && (elementoVO.getIdpadre() != null)) {
				elementoVO = _noAsignableDBEntity
						.loadElementoNoAsignable(elementoVO.getIdpadre());
				ltPadres.addFirst(elementoVO);
			}
		}

		return ltPadres;
	}

	/**
	 * Devuelve el hueco en el que se encuentra la unidad de instalaci�n
	 * pasada como par�metro dentro de las ubicaciones del archivo indicado
	 * 
	 * @param idUInstalacion
	 * @param idArchivo
	 * @return HuecoVO
	 */
	public HuecoVO getHuecoUInstalacionXArchivo(String idUInstalacion,
			String idArchivo) {
		return _huecoDBEntity.getHuecoUInstalacionXArchivo(idUInstalacion,
				idArchivo);
	}

	/**
	 * Comprueba si existe alg�n hueco para alguno de los dep�sitos
	 * pertenecientes al archivo pasado como parametro en cuyo caso no ser�a
	 * posible editar el campoo tipo signaturaci�n.
	 * 
	 * @param idArchivo
	 * @return
	 */
	public boolean isEditableTipoSignaturacion(String idArchivo) {
		if (_huecoDBEntity.getNumHuecosXArchivo(idArchivo) > 0)
			return false;
		return true;
	}

	/**
	 * Obtiene el HuecoVO perteneciente a un deposito del archivo cuya
	 * numeracion coincide con la pasada como parametro. En caso de no ser as�
	 * se devolvera NULL.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public HuecoVO getHuecoAsociadoNumeracion(String idArchivo,
			String numeracion) {
		return _huecoDBEntity.getHuecoAsociadoNumeracion(idArchivo, numeracion);
	}

	public int countHuecosAsociadoNumeracion(String idArchivo, String numeracion) {
		return _huecoDBEntity.countHuecosAsociadoNumeracion(idArchivo,
				numeracion);
	}

	public ArchivoVO getArchivoXIdElemento(ElementoVO elemento) {
		if (elemento == null)
			return null;
		DepositoVO ubicacion = getUbicacion(getUbicacion(elemento.getId(),
				elemento.getIdTipoElemento()));
		if (ubicacion == null)
			return null;
		return _archivoDbEntity.getArchivoXId(ubicacion.getIdArchivo());
		// return
		// ServiceRepository.getInstance(getServiceClient()).lookupGestionArchivosBI().getArchivoXId(ubicacion.getIdArchivo());
	}

	public boolean isSignaturacionAsociadaHueco(String idElemento) {
		ElementoVO elemento = getElemento(idElemento);
		ArchivoVO archivo = getArchivoXIdElemento(elemento);
		if (archivo == null)
			return false;
		return archivo.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
				.getIdentificador();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#hasChildsAsignables(java.lang
	 * .String)
	 */
	public boolean hasChildsAsignables(String idTipoElemento) {
		return _tipoElementoDBEntity.hasChildsAsignables(idTipoElemento);
	}

	// public void updateNumeracion(List huecos) throws
	// NumeracionHuecoRepetidaException {
	// // a partir de cada hueco obtener el elemento asignable
	// // a partir del elemento asignable obtener el archivo
	//
	// // iniciar transaccion y recorrer la lista de huecos
	// // usar la numeracion de cada hueco y el archivo para comprobar que no
	// exista
	// // si existe se lanza la excepcion a capturar en la action para mostrar
	// un mensaje explicativo y rollback
	// // sino existe se inserta en la DB y se pasa a la siguiente.
	//
	// iniciarTransaccion();
	// boolean procesadoPrimerElemento=false;
	// String idArchivo=null;
	// for(Iterator it=huecos.iterator();it.hasNext();){
	// HuecoVO hueco=(HuecoVO)it.next();
	// if(!procesadoPrimerElemento){
	// ElementoAsignableVO asignable=getAsignable(hueco.getIdElemAPadre());
	// idArchivo=getArchivoXIdElemento(asignable).getId();
	// procesadoPrimerElemento=true;
	// }
	//
	// if(getHuecoAsociadoNumeracion(idArchivo,hueco.getNumeracion())!=null){
	// throw new NumeracionHuecoRepetidaException(hueco.getNumeracion());
	// }
	//
	// Map colsToUpdate = new HashMap();
	// colsToUpdate.put(HuecoDbEntityImplBase.CAMPO_NUMERACION,
	// hueco.getNumeracion());
	// _huecoDBEntity.updateFieldsHueco(hueco.getIdElemAPadre(),
	// hueco.getOrden().intValue(), colsToUpdate);
	// }
	// commit();
	// }

	public long getNsecSigNumericaHuecos(String idArchivo) {
		return _nsecSigNumHuecoDbEntity.getNumeroSecLong(0, idArchivo);
	}

	public long getMaxNumeracionHuecoEnArchivo(String idArchivo) {
		try {
			return _huecoDBEntity.getMaxNumeracionHueco(idArchivo);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Comprueba si el deposito en el nos encontramos permite editar la
	 * numeracion de sus huecos. Esto ocurre siempre que pertenecezca a un
	 * archivo cuyo tipo de signaturacion sea asociada a hueco.
	 * 
	 * @param idDeposito
	 * @return
	 */
	public boolean isEditableNumeracion(String idDeposito) {
		if (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.getIdentificador() == _depositoDbEntity
				.getTipoSignaturacionDeposito(idDeposito))
			return true;
		return false;
	}

	/**
	 * Obtiene la menor numeraci�n existente para el elemento padre pasado
	 * como parametro
	 * 
	 * @param idElemAPadre
	 * @return
	 */
	public long getMenorNumeracionHueco(String idElemAPadre) {
		return _huecoDBEntity.obtenerNumeracionHueco(idElemAPadre, DBUtils.MIN);
	}

	/**
	 * Obtiene la mayor numeraci�n existente para el elemento padre pasado
	 * como parametro
	 * 
	 * @param idElemAPadre
	 * @return
	 */
	public long getMayorNumeracionHueco(String idElemAPadre) {
		return _huecoDBEntity.obtenerNumeracionHueco(idElemAPadre, DBUtils.MAX);
	}

	/**
	 * Obtiene la menor numeraci�n existente para el elemento padre pasado
	 * como parametro
	 * 
	 * @param idElemAPadre
	 * @return
	 */
	public String getMenorNumeracionOrdenHueco(String idElemAPadre) {
		return _huecoDBEntity.obtenerNumeracionOrdenHueco(idElemAPadre,
				DBUtils.MIN);
	}

	/**
	 * Obtiene la mayor numeraci�n existente para el elemento padre pasado
	 * como parametro
	 * 
	 * @param idElemAPadre
	 * @return
	 */
	public String getMayorNumeracionOrdenHueco(String idElemAPadre) {
		return _huecoDBEntity.obtenerNumeracionOrdenHueco(idElemAPadre,
				DBUtils.MAX);
	}

	/**
	 * Se encarga de llevar a cabo la renumeraci�n de los huecos.
	 * 
	 * @param huecoID
	 * @param nuevaNumeracion
	 * @param renumerar
	 */
	public void renumerarHuecos(final HuecoID huecoID,
			final String nuevaNumeracion, final boolean renumerar)
			throws DepositoException {
		iniciarTransaccion();
		HuecoVO huecoVO = getInfoHueco(huecoID);
		String idArchivo = getUbicacion(huecoVO.getIddeposito()).getIdArchivo();
		if (renumerar) {
			/*
			 * if(_huecoDBEntity.comprobarRenumeracionHueco(huecoVO,
			 * nuevaNumeracion)){ _huecoDBEntity.renumerarHuecos(idArchivo,
			 * huecoVO, nuevaNumeracion, renumerar); }else throw new
			 * DepositoException
			 * (DepositoException.NO_SE_PUEDE_RENUMERAR_HUECOS);
			 */
			try {
				if (_huecoDBEntity.comprobarRenumeracionHueco(huecoVO,
						nuevaNumeracion, idArchivo))
					_huecoDBEntity.renumerarHuecos(idArchivo, huecoVO,
							nuevaNumeracion, renumerar);
			} catch (DepositoException e) {
				throw new DepositoException(e.getCodError());
			}
		} else {
			if (_huecoDBEntity.isNumeracionEnUso(idArchivo, nuevaNumeracion))
				throw new DepositoException(
						DepositoException.NUMERACION_HUECO_EN_USO);
			else
				_huecoDBEntity.updateNumeracionHueco(huecoID, nuevaNumeracion);
		}
		if (!StringUtils.isEmpty(nuevaNumeracion)) {
			long numMax = getMaxNumeracionHuecoEnArchivo(idArchivo);
			if (numMax > 0)
				_nsecSigNumHuecoDbEntity.setNumeroSec(numMax + 1, idArchivo);
		}
		commit();

	}

	public int getNumOrden(ElementoNoAsignableVO elementoPadre,
			String tipoAsignable) {
		return _numOrdenDBEntity.getNumeroOrden(elementoPadre.getId(),
				elementoPadre.getIdTipoElemento(), tipoAsignable);
	}

	/**
	 * Obtiene el numero total de huecos numericos que tiene un determinado
	 * elemento.
	 * 
	 * @param idElementoPadre
	 * @return
	 */
	public int getNumHuecosNumericos(String idElementoPadre) {
		return _huecoDBEntity.countNumHuecosNumericos(idElementoPadre);
	}

	/**
	 * Comprueba si es posible editar el archivo al que pertenece una ubicacion.
	 * 
	 * @param idArchivo
	 * @return
	 */
	public boolean isEditableArchivoUbicacion(String idUbicacion) {
		if (_huecoDBEntity.getNumHuecosUbicacion(idUbicacion) > 0)
			return false;
		return true;
	}

	public List buscarHistoricoUnidadesInstalacionDeposito(
			BusquedaHistUInstDepositoVO busquedaHistUInstDepositoVO) {
		return _histUinstDepositoDBEntity.find(busquedaHistUInstDepositoVO);
	}

	public HistUInstDepositoVO obtenerHistoricoUnidadInstalacionDeposito(
			String id) {
		return _histUinstDepositoDBEntity.getById(id);
	}

	private void historicoUnidadInstalacion(String idArchivo,
			String idUInstaEnDeposito, Integer motivo) throws Exception {
		UInsDepositoVO uInstDepositoVO = getUinsEnDeposito(idUInstaEnDeposito);

		if (uInstDepositoVO != null)
			historicoUnidadInstalacion(idArchivo, uInstDepositoVO, motivo);
	}

	/**
	 * Realiza el hist�rico de la unidad de instalaci�n
	 * 
	 * @param uInstDepositoVO
	 *            Unidad de Instalaci�n
	 * @param motivo
	 *            Motivo
	 * @throws Exception
	 *             Excepci�n
	 */
	private void historicoUnidadInstalacion(String idArchivo,
			UInsDepositoVO uInstDepositoVO, Integer motivo) throws Exception {
		String id = GuidManager.generateGUID(getServiceClient().getEngine());

		HistUInstDepositoVO histUnist = new HistUInstDepositoVO(id, idArchivo,
				uInstDepositoVO.getIdformato(),
				uInstDepositoVO.getSignaturaui(),
				uInstDepositoVO.getIdentificacion(),
				DateUtils.getFechaActual(), motivo);
		_histUinstDepositoDBEntity.insert(histUnist);
	}

	public void actualizarDescripcion(UDocEnUiDepositoVO udocEnUi) {
		_udocEnUIDepositoDBEntity.updateDescripcionUDoc(udocEnUi);
	}

	public List getPartesUdocEnUIDeposito(String idUnidadDocumental) {
		return _udocEnUIDepositoDBEntity
				.getPartesUdocByIDElementoCF(idUnidadDocumental);
	}

	public void eliminarCajaVacia(String idArchivo, String idUIOrigen,
			Integer motivo) throws Exception {
		LoggingEvent event = AuditDeposito.getLogginEventBajaElemento(this);
		Locale locale = getServiceClient().getLocale();

		ArchivoVO archivo = _archivoDbEntity.getArchivoXId(idArchivo);
		UInsDepositoVO uInstDepositoVO = getUinsEnDeposito(idUIOrigen);
		AuditDeposito.addDataLogEliminarUIVacia(locale, event, this,
				uInstDepositoVO, archivo.getNombre());

		historicoUnidadInstalacion(idArchivo, idUIOrigen, motivo);

		_unidadInstalacionDepositoDBEntity.deleteUInstDeposito(idUIOrigen);

		_huecoDBEntity.liberaUnidadInstalacion(idUIOrigen);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IeciTdException
	 * @see deposito.model.GestorEstructuraDepositoBI#getHuecosPorSignaturas(java.lang.String,
	 *      java.lang.String, java.lang.String, common.util.IntervalOptions)
	 */
	public List getHuecosPorSignaturas(String idElementoNoAsignable,
			String idUbicacion, String idFormato, IntervalOptions options)
			throws IeciTdException, TooManyResultsException {
		List huecos = null;
		huecos = _huecoDBEntity.getHuecosBySignaturas(idElementoNoAsignable,
				idUbicacion, idFormato, options);
		return huecos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IeciTdException
	 * @see deposito.model.GestorEstructuraDepositoBI#getUnidadesInstalacionPorAnioYSerie(deposito.vos.BusquedaUIAnioSerieVO)
	 */
	public List getUnidadesInstalacionPorAnioYSerie(
			BusquedaUIAnioSerieVO busquedaUIAnioSerieVO)
			throws IeciTdException, TooManyResultsException {

		busquedaUIAnioSerieVO
				.setIdsSerie(getIdsSeriesByAmbitos(busquedaUIAnioSerieVO
						.getIdsAmbitoSeries()));
		busquedaUIAnioSerieVO
				.setIdsElementosAsignables(getIdsAsignablesByAmbito(
						busquedaUIAnioSerieVO.getIdUbicacion(),
						busquedaUIAnioSerieVO.getIdsAmbitoDeposito()));

		int maxNumResults = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
				.getNumMaxResultados();
		ConfiguracionDescripcion cd = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		busquedaUIAnioSerieVO.setIdCampoFechaExtremaInicial(cd
				.getFechaExtremaInicial());
		busquedaUIAnioSerieVO.setIdCampoFechaExtremaFinal(cd
				.getFechaExtremaFinal());
		busquedaUIAnioSerieVO.setIdCampoProductor(cd.getProductor());

		ConsultaConnectBy consultaConnectBy = _unidadDocumentalDBEntity
				.getConsultaConnectByUnidadesInstalacionPorAniosYSerie(busquedaUIAnioSerieVO);

		int count = _unidadInstalacionDepositoDBEntity
				.getCountUnidadesInstalacion(consultaConnectBy);

		if (count > maxNumResults)
			throw new TooManyResultsException(count, maxNumResults);

		return _unidadDocumentalDBEntity
				.getUnidadesInstalacionPorAniosYSerie(consultaConnectBy);
	}

	private String[] getIdsSeriesByAmbitos(String[] idsAmbito) {
		String[] idsSerie = null;
		List listaSeries = new ArrayList();

		if (ArrayUtils.isNotEmptyOrBlank(idsAmbito)) {

			INivelCFVO nivelCFSerie = getGestionCuadroClasificacionBI()
					.getNivelCF(TipoNivelCF.SERIE.getIdentificador(), 0);

			if (nivelCFSerie != null) {
				for (int i = 0; i < idsAmbito.length; i++) {
					ElementoCuadroClasificacionVO elemento = getGestionCuadroClasificacionBI()
							.getElementoCuadroClasificacion(idsAmbito[i]);

					if (elemento != null) {
						if (elemento.isTipoSerie()) {
							listaSeries.add(elemento.getId());
						} else if (elemento.isTipoClasificadorSerie()) {
							// Obtener las Series
							List series = getGestionSeriesBI().getSeriesFondo(
									elemento.getIdFondo());

							if (ListUtils.isNotEmpty(series)) {
								Iterator it = series.iterator();
								while (it.hasNext()) {
									Serie serie = (Serie) it.next();
									listaSeries.add(serie.getId());
								}
							}
						} else if (elemento.isTipoFondo()) {
							// Obtener las Series
							List series = getGestionSeriesBI().getSeriesFondo(
									elemento.getId());

							if (ListUtils.isNotEmpty(series)) {
								Iterator it = series.iterator();
								while (it.hasNext()) {
									Serie serie = (Serie) it.next();
									listaSeries.add(serie.getId());
								}
							}
						}
					}
				}
			}
		}

		if (!ListUtils.isEmpty(listaSeries)) {
			idsSerie = (String[]) listaSeries.toArray(new String[] {});
		}

		return idsSerie;
	}

	private String[] getIdsAsignablesByAmbito(String idUbicacion,
			String[] idsAmbitoDeposito) throws TooManyResultsException,
			IeciTdException {
		String[] idsAsignables = null;

		if (StringUtils.isNotEmpty(idUbicacion)
				&& ArrayUtils.isNotEmptyOrBlank(idsAmbitoDeposito)) {

			List listaAsignables = _asignableDBEntity
					.getElementosAsignablesByAmbitosPadre(idsAmbitoDeposito);

			if (ListUtils.isNotEmpty(listaAsignables)) {
				int numSeries = listaAsignables.size();
				idsAsignables = new String[numSeries];

				for (int i = 0; i < numSeries; i++) {
					ElementoAsignableVO asignable = (ElementoAsignableVO) listaAsignables
							.get(i);
					if (asignable != null) {
						idsAsignables[i] = asignable.getId();
					}
				}

				return idsAsignables;
			}

		}

		return idsAsignables;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#getPathPadre(java.lang.String)
	 */
	public String getPathPadre(String idElemNoAsig) {
		String pathPadre = StringUtils.EMPTY;
		ElementoNoAsignableVO elemNoAsig = getElementoNoAsignable(idElemNoAsig);
		DepositoVO ubicacion = getUbicacion(elemNoAsig.getIddeposito());
		while (elemNoAsig != null
				&& StringUtils.isNotEmpty(elemNoAsig.getIdpadre())) {
			elemNoAsig = getElementoNoAsignable(elemNoAsig.getIdpadre());
			pathPadre = elemNoAsig.getNombre() + Constants.SLASH + pathPadre;
		}
		pathPadre = ubicacion.getNombre() + Constants.SLASH + pathPadre;
		return pathPadre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#guardarElementoNoAsignable(deposito.vos.ElementoNoAsignableVO)
	 */
	public void guardarElementoNoAsignable(
			ElementoNoAsignableVO elementoNoAsignable)
			throws ActionNotAllowedException {

		// comprobacion de permisos
		checkPermission(DepositoSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		iniciarTransaccion();

		// Obtengo el elemento NO asignable
		ElementoNoAsignableVO noAsignable = _noAsignableDBEntity
				.getById(elementoNoAsignable.getId());

		// Se actualiza el elemento no asignable
		_noAsignableDBEntity.updateNoAsignable(elementoNoAsignable);

		// Se actualizan los elementos asignables que dependen de dicho elemento
		// No Asignable
		// Tanto los huecos como las unidades de instalacion en las relaciones
		// entre archivos
		String pathPadre = getPathPadre(elementoNoAsignable.getId());
		String pathAntiguo = pathPadre + noAsignable.getNombre();
		String pathNuevo = pathPadre + elementoNoAsignable.getNombre();
		_huecoDBEntity.updateHuecosElementoNoAsignable(
				elementoNoAsignable.getId(), pathAntiguo, pathNuevo);
		_unidadInstalacionReeaDBEntity.updatePathUInstalacionReea(
				elementoNoAsignable.getId(), pathAntiguo, pathNuevo);

		commit();
	}

	// TIPOS ELEMENTO
	public TipoElementoVO getTipoElementoSingleton(String idTipoElemento) {
		TipoElementoVO tipoElemento = (TipoElementoVO) tiposElemento
				.get(idTipoElemento);
		if (tipoElemento == null) {
			tipoElemento = getTipoElemento(idTipoElemento);
			tiposElemento.put(idTipoElemento, tipoElemento);
		}
		return tipoElemento;
	}

	public TipoElementoVO getTipoElemento(String idTipoElemento) {
		TipoElementoVO tipoElemento = _tipoElementoDBEntity
				.getTipoElemento(idTipoElemento);
		return tipoElemento;
	}

	public List getTiposElemento(String idTipoElementoPadre, String[] exclude) {
		return _tipoElementoDBEntity.getTiposElemento(idTipoElementoPadre,
				exclude);
	}

	public void actualizarTipoElemento(TipoElementoVO tipoElementoVO)
			throws TipoElementoDepositoException {
		checkModificacionTipoElemento(tipoElementoVO);
		checkTipoElementoExistente(tipoElementoVO);
		checkCaracterOrden(tipoElementoVO);
		_tipoElementoDBEntity.updateTipoElemento(tipoElementoVO);
		tiposElemento.put(tipoElementoVO.getId(), tipoElementoVO);
	}

	public void insertarTipoElemento(TipoElementoVO tipoElementoVO)
			throws TipoElementoDepositoException {
		checkTipoElementoExistente(tipoElementoVO);
		checkCaracterOrden(tipoElementoVO);

		_tipoElementoDBEntity.insertTipoElemento(tipoElementoVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#tipoElementoReferenciado(java.lang.String,
	 *      boolean)
	 */
	public boolean isTipoElementoReferenciado(String idTipoElemento) {
		int num = 0;

		num = _noAsignableDBEntity.getCountByTipoElemento(idTipoElemento);

		if (num == 0) {
			num = _asignableDBEntity.getCountByTipoElemento(idTipoElemento);
		}

		if (num > 0) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#hasChilds(java.lang.String)
	 */
	public boolean hasChilds(String idTipoElemento) {
		return _tipoElementoDBEntity.hasChilds(idTipoElemento);
	}

	public void checkTipoElementoExistente(TipoElementoVO tipoElementoVO)
			throws TipoElementoDepositoException {
		TipoElementoVO tipoElemento = _tipoElementoDBEntity
				.getTipoElemento(tipoElementoVO);

		if (tipoElemento != null) {
			if (tipoElementoVO.getNombre().equalsIgnoreCase(
					tipoElemento.getNombre())) {
				throw new TipoElementoDepositoException(
						TipoElementoDepositoException.NOMBRE_DUPLICADO);
			} else if (tipoElementoVO.getNombreAbreviado().equalsIgnoreCase(
					tipoElemento.getNombreAbreviado())) {
				throw new TipoElementoDepositoException(
						TipoElementoDepositoException.NOMBRE_ABREVIADO_DUPLICADO);
			} else if (tipoElementoVO.getCaracterorden().equalsIgnoreCase(
					tipoElemento.getCaracterorden())) {
				throw new TipoElementoDepositoException(
						TipoElementoDepositoException.CARACTER_ORDENACION_HERMANO_DUPLICADO);
			}
		}
	}

	public void checkCaracterOrden(TipoElementoVO tipoElementoVO)
			throws TipoElementoDepositoException {
		TipoElementoVO tipoElementoPadre = _tipoElementoDBEntity
				.getTipoElemento(tipoElementoVO.getIdpadre());

		if (tipoElementoPadre != null
				&& tipoElementoPadre.getCaracterorden().equals(
						tipoElementoVO.getCaracterorden())) {
			throw new TipoElementoDepositoException(
					TipoElementoDepositoException.CARACTER_ORDENACION_PADRE_DUPLICADO);
		}
	}

	public void checkModificacionTipoElemento(TipoElementoVO tipoElementoVO)
			throws TipoElementoDepositoException {

		if (isTipoElementoReferenciado(tipoElementoVO.getId())) {
			TipoElementoVO tipoElemento = _tipoElementoDBEntity
					.getTipoElemento(tipoElementoVO.getId());

			if (tipoElemento != null) {
				if (!tipoElementoVO.getCaracterorden().equalsIgnoreCase(
						tipoElemento.getCaracterorden())) {
					throw new TipoElementoDepositoException(
							TipoElementoDepositoException.CARACTER_ORDENACION_NO_EDITABLE);
				} else if (tipoElementoVO.getAsignable() != tipoElemento
						.getAsignable()) {
					throw new TipoElementoDepositoException(
							TipoElementoDepositoException.ASIGNABLE_NO_EDITABLE);
				} else if (tipoElemento.isTipoAsignable()
						&& !tipoElementoVO.getTipoord().equals(
								tipoElemento.getTipoord())) {
					throw new TipoElementoDepositoException(
							TipoElementoDepositoException.TIPO_ORDENACION_NO_EDITABLE);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.model.GestorEstructuraDepositoBI#eliminarTipoElemento(java.lang.String)
	 */
	public void eliminarTipoElemento(String idTipoElemento)
			throws TipoElementoDepositoException {

		if (StringUtils.isNotBlank(idTipoElemento)) {
			// Comprobar que no tiene hijos
			if (hasChilds(idTipoElemento)) {
				throw new TipoElementoDepositoException(
						TipoElementoDepositoException.NO_ELIMINABLE_TIENE_DESCENDIENTES);
			} else if (isTipoElementoReferenciado(idTipoElemento)) {
				throw new TipoElementoDepositoException(
						TipoElementoDepositoException.NO_ELIMINABLE_TIPO_REFERENCIADO);
			}
			_tipoElementoDBEntity.deleteTipoElemento(idTipoElemento);
			tiposElemento.remove(idTipoElemento);
		}
	}

	// FIN TIPOS ELEMENTO
}