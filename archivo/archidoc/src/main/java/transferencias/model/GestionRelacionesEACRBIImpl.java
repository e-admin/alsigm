/**
 *
 */
package transferencias.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import transferencias.EstadoCotejo;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.db.IUDocEnUIReeaCRDBEntity;
import transferencias.db.IUDocReeaCRDBEntity;
import transferencias.db.IUIReeaCRDBEntity;
import transferencias.db.IUnidadInstalacionReeaDBEntity;
import transferencias.exceptions.ParteUnidadDocumentalNoEliminable;
import transferencias.exceptions.RelacionEntregaConUDocsSinAsingarAUIException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasFSException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasUDocsException;
import transferencias.exceptions.UnidadDocumentalNoPermitidaDivisionException;
import transferencias.exceptions.UnidadInstalacionConUnidadesDocumentalesException;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocEnUIReeaCRVO;
import transferencias.vos.UDocReeaCRVO;
import transferencias.vos.UIReeaCRVO;
import transferencias.vos.UnidadInstalacionReeaVO;

import common.Constants;
import common.bi.GestionRelacionesEACRBI;
import common.bi.ServiceBase;
import common.util.ArrayUtils;
import common.util.IntervalOptions;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.MinMaxVO;

import deposito.SignaturaUtil;
import deposito.db.IFormatoDbEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import fondos.db.INivelCFDBEntity;
import fondos.model.SubtipoNivelCF;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GestionRelacionesEACRBIImpl extends ServiceBase implements
		GestionRelacionesEACRBI {

	IRelacionEntregaDBEntity _relacionEntregaDBEntity = null;
	IFormatoDbEntity _formatoDBEntity = null;
	IHuecoDBEntity _huecoDBEntity = null;
	IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUIDepositoDBEntity = null;
	IUIReeaCRDBEntity _uiReeaCRDBEntity = null;
	IUDocEnUIReeaCRDBEntity _udocEnUiReeaCRDBEntity = null;
	IUDocReeaCRDBEntity _udocReeaCRDBEntity = null;
	IUInstalacionDepositoDBEntity _uiInstalacionDepositoDBEntity = null;
	INivelCFDBEntity _nivelCFDBEntity = null;

	public GestionRelacionesEACRBIImpl(
			IRelacionEntregaDBEntity relacionEntregaDBEntity,
			IFormatoDbEntity formatoDBEntity, IHuecoDBEntity huecoDBEntity,
			IUnidadInstalacionReeaDBEntity unidadInstalacionReeaDBEntity,
			IUDocEnUiDepositoDbEntity udocEnUIDepositoDBEntity,
			IUIReeaCRDBEntity uiReeaCRDBEntity,
			IUDocEnUIReeaCRDBEntity udocEnUiReeaCRDBEntity,
			IUDocReeaCRDBEntity udocReeaCRDBEntity,
			IUInstalacionDepositoDBEntity uiInstalacionDepositoDBEntity,
			INivelCFDBEntity _nivelCFDBEntity) {
		super();
		this._relacionEntregaDBEntity = relacionEntregaDBEntity;
		this._formatoDBEntity = formatoDBEntity;
		this._huecoDBEntity = huecoDBEntity;
		this._unidadInstalacionReeaDBEntity = unidadInstalacionReeaDBEntity;
		this._udocEnUIDepositoDBEntity = udocEnUIDepositoDBEntity;
		this._uiReeaCRDBEntity = uiReeaCRDBEntity;
		this._udocEnUiReeaCRDBEntity = udocEnUiReeaCRDBEntity;
		this._udocReeaCRDBEntity = udocReeaCRDBEntity;
		this._uiInstalacionDepositoDBEntity = uiInstalacionDepositoDBEntity;
		this._nivelCFDBEntity = _nivelCFDBEntity;
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#activarReencajado(java.lang.String,
	 *      java.lang.String)
	 */
	public void activarReencajado(String idRelEntrega, String idFormatoRe) {

		// Comprobar que no está activo ya el reencajado.
		RelacionEntregaVO relacionEntregaVO = _relacionEntregaDBEntity
				.getRelacionXId(idRelEntrega);

		if (relacionEntregaVO != null
				&& !relacionEntregaVO.isRelacionConReencajado()) {
			iniciarTransaccion();

			// Obtener las unidades de instalación de la relación de entrega
			// entre
			// archivos.
			List listaUIs = _unidadInstalacionReeaDBEntity
					.getUnidadesInstalacionXIdRelacionEntreArchivos(idRelEntrega);
			if (ListUtils.isNotEmpty(listaUIs)) {
				crearUIsReeaConReencajadoNoTransaccional(idRelEntrega,
						listaUIs, idFormatoRe);
			}

			_relacionEntregaDBEntity.updateReencajado(idRelEntrega,
					Constants.TRUE_STRING, idFormatoRe);
			commit();
		}
	}

	public void addUIReencajadoNoTransaccional(String idRelEntrega,
			List listaUIs, String idFormatoRe) {
		if (ListUtils.isNotEmpty(listaUIs)) {
			crearUIsReeaConReencajadoNoTransaccional(idRelEntrega, listaUIs,
					idFormatoRe);
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#cancelarReencajado(java.lang.String)
	 */
	public void cancelarReencajadoTransaccional(String idRelEntrega) {
		iniciarTransaccion();
		cancelarReencajadoNoTransaccional(idRelEntrega);
		commit();

	}

	public void cancelarReencajadoNoTransaccional(String idRelEntrega) {
		_udocEnUiReeaCRDBEntity.deleteByIdRelacion(idRelEntrega);

		_udocReeaCRDBEntity.deleteByIdRelacion(idRelEntrega);
		_uiReeaCRDBEntity.deleteByIdRelacion(idRelEntrega);

		_relacionEntregaDBEntity.updateReencajado(idRelEntrega,
				Constants.FALSE_STRING, null);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#addUIReencajado(java.lang.String,
	 *      java.lang.String)
	 */
	public UIReeaCRVO addUIReencajado(String idRelEntrega, String idFormato) {
		UIReeaCRVO uiReeaCRVO = new UIReeaCRVO();

		uiReeaCRVO.setIdRelEntrega(idRelEntrega);
		uiReeaCRVO.setIdFormato(idFormato);
		uiReeaCRVO.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());

		_uiReeaCRDBEntity.insert(uiReeaCRVO);

		return uiReeaCRVO;
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#renumerarUnidadesDocumentalesCR(java.lang.String)
	 */
	private void renumerarUnidadesDocumentalesCRNoTransaccional(
			String idRelEntrega, String idUinstalacionReeaCR) {
		List listaUDocs = null;
		if (StringUtils.isNotEmpty(idUinstalacionReeaCR)) {
			listaUDocs = _udocEnUiReeaCRDBEntity
					.getByIdUI(idUinstalacionReeaCR);
		} else {
			listaUDocs = _udocEnUiReeaCRDBEntity
					.getUDocsSinUIOrderBySignatura(idRelEntrega);
		}

		int posicion = 1;
		if (ListUtils.isNotEmpty(listaUDocs)) {
			for (Iterator iterator = listaUDocs.iterator(); iterator.hasNext();) {
				UDocEnUIReeaCRVO udoc = (UDocEnUIReeaCRVO) iterator.next();

				if (udoc != null) {
					_udocEnUiReeaCRDBEntity.updatePosicion(udoc.getId(),
							posicion++);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#crearNuevaUICR(java.lang.String,
	 *      java.lang.String)
	 */
	public UIReeaCRVO crearNuevaUICR(String idRelEntrega, String idFormatoRe,
			String[] udocs) {
		UIReeaCRVO uiReeaCRVO = new UIReeaCRVO(idRelEntrega, idFormatoRe);
		uiReeaCRVO.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());
		iniciarTransaccion();
		_uiReeaCRDBEntity.insert(uiReeaCRVO);

		for (int i = 0; i < udocs.length; i++) {
			_udocEnUiReeaCRDBEntity.updateUIReaCRAndPosUdoc(udocs[i],
					uiReeaCRVO.getId());
		}

		renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, null);

		commit();
		return uiReeaCRVO;
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#eliminarUICRVacia(java.lang.String,
	 *      java.lang.String)
	 */
	public void eliminarUICRVacia(String idRelEntrega, String idUiReeaCR)
			throws UnidadInstalacionConUnidadesDocumentalesException {

		iniciarTransaccion();

		// Comprobar que la unidad de instalación esta vacia
		if (_udocEnUiReeaCRDBEntity.getCountUDocsUI(idUiReeaCR) != 0) {
			throw new UnidadInstalacionConUnidadesDocumentalesException();
		}

		_uiReeaCRDBEntity.delete(idRelEntrega, idUiReeaCR);

		// Reordenar las unidades de instalación
		List listaUnidades = _uiReeaCRDBEntity.getByIdRelacion(idRelEntrega);
		if (!ListUtils.isEmpty(listaUnidades)) {
			int orden = 1;
			for (Iterator iter = listaUnidades.iterator(); iter.hasNext();) {
				UIReeaCRVO uiReeaCr = (UIReeaCRVO) iter.next();
				_uiReeaCRDBEntity.updateOrden(uiReeaCr.getId(), new Integer(
						orden++));
			}
		}
		commit();
	}

	/**
    *
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#subirUdocsEnUIReeaCR(java.lang.String,
	 *      java.lang.String, java.lang.String[])
	 */
	public void subirUdocsEnUIReeaCR(String idRelEntrega, String idUI,
			String[] idsUdocs) {
		iniciarTransaccion();
		MinMaxVO minMaxVO = _udocEnUiReeaCRDBEntity.getPosicionesMinMax(idUI,
				idsUdocs);

		if (minMaxVO != null) {
			int posicionAnterior = minMaxVO.getMin() - 1;
			int posicionNueva = minMaxVO.getMax();

			_udocEnUiReeaCRDBEntity.updatePosiciones(idUI, idsUdocs, -1);

			_udocEnUiReeaCRDBEntity.updatePosicionElemento(idUI, idsUdocs,
					posicionAnterior, posicionNueva);

			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, idUI);
		}

		commit();
	}

	/**
    *
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#bajarUdocsEnUI(java.lang.String,
	 *      java.lang.String, java.lang.String[])
	 */
	public void bajarUdocsEnUI(String idRelEntrega, String idUI,
			String[] idsUdocs) {
		iniciarTransaccion();
		MinMaxVO minMaxVO = _udocEnUiReeaCRDBEntity.getPosicionesMinMax(idUI,
				idsUdocs);

		if (minMaxVO != null) {
			int posicionAnterior = minMaxVO.getMax() + 1;
			int posicionNueva = minMaxVO.getMin();

			_udocEnUiReeaCRDBEntity.updatePosiciones(idUI, idsUdocs, +1);

			_udocEnUiReeaCRDBEntity.updatePosicionElemento(idUI, idsUdocs,
					posicionAnterior, posicionNueva);

			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, idUI);
		}
		commit();

	}

	/**
    *
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#eliminarPartesUDocReeaCR(java.lang.String,
	 *      java.lang.String[])
	 */
	public void eliminarPartesUDocReeaCR(String idUI, String[] idsDoc)
			throws ParteUnidadDocumentalNoEliminable {
		if (ArrayUtils.isNotEmpty(idsDoc)) {

			// Obtener la primera unidad documental
			UDocEnUIReeaCRVO udocSeleccionada = _udocEnUiReeaCRDBEntity
					.getById(idsDoc[0]);

			if (udocSeleccionada != null) {

				iniciarTransaccion();

				_udocEnUiReeaCRDBEntity.deleteByIds(idsDoc);

				int numPartes = _udocEnUiReeaCRDBEntity
						.getTotalPartesRestantes(
								udocSeleccionada.getIdRelEntrega(),
								udocSeleccionada.getIdUnidadDoc(), idsDoc);

				if (numPartes >= 1) {
					Map mapUdocs = new HashMap();
					mapUdocs.put(udocSeleccionada.getIdUnidadDoc(),
							udocSeleccionada.getIdUnidadDoc());
					updateNumPartesNoTransaccional(
							udocSeleccionada.getIdRelEntrega(), mapUdocs);
					renumerarUnidadesDocumentalesCRNoTransaccional(
							udocSeleccionada.getIdRelEntrega(),
							udocSeleccionada.getIdUIReeaCR());
				} else {
					throw new ParteUnidadDocumentalNoEliminable();
				}

				commit();
			}
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#extraerUDocDeUIReeaCR(java.lang.String,
	 *      java.lang.String)
	 */
	public void extraerUDocsDeUIReeaCR(String idRelEntrega, String idUI,
			String[] udocs) {
		if (ArrayUtils.isNotEmptyOrBlank(udocs)) {
			iniciarTransaccion();

			for (int i = 0; i < udocs.length; i++) {
				_udocEnUiReeaCRDBEntity.updateUIReaCRAndPosUdoc(udocs[i], null);
			}
			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, idUI);

			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, null);

			commit();
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#getUDocsSinUIReeaCR(java.lang.String)
	 */
	public List getUDocsSinUIReeaCR(String idRelEntrega) {
		return _udocEnUiReeaCRDBEntity.getUDocsSinUI(idRelEntrega);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEntregaBI#incorporarUdocAUIReeaCR(java.lang.String,
	 *      java.lang.String[])
	 */
	public void incorporarUdocsAUIReeaCR(String idRelEntrega, String idUI,
			String[] udocs) {
		if (ArrayUtils.isNotEmptyOrBlank(udocs)) {
			iniciarTransaccion();

			for (int i = 0; i < udocs.length; i++) {
				_udocEnUiReeaCRDBEntity.updateUIReaCRAndPosUdoc(udocs[i], idUI);
			}
			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, null);

			commit();
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @throws UnidadDocumentalNoPermitidaDivisionException
    *
	 * @see common.bi.GestionRelacionesEntregaBI#dividirUDocReeaCR(java.lang.String,
	 *      java.lang.String)
	 */
	public void dividirUDocReeaCR(String idRelEntrega, String idUI,
			String idUDoc, String idFormatoRe)
			throws UnidadDocumentalNoPermitidaDivisionException {
		iniciarTransaccion();
		UDocEnUIReeaCRVO udoc = _udocEnUiReeaCRDBEntity.getById(idUDoc);
		if (udoc != null) {
			UIReeaCRVO uiReaCRVO = addUIReencajado(idRelEntrega, idFormatoRe);

			if (uiReaCRVO != null) {
				udoc.setIdUIReeaCR(uiReaCRVO.getId());
				udoc.setNumParte(udoc.getTotalPartes() + 1);
				udoc.setId(null);
				_udocEnUiReeaCRDBEntity.insert(udoc);
				Map mapUdocs = new HashMap();
				mapUdocs.put(udoc.getIdUnidadDoc(), udoc.getIdUnidadDoc());
				updateUdocCompletaNoTransaccional(idRelEntrega, mapUdocs);
			}
		}
		commit();
	}

	/**
	 * {@inheritDoc}
    *
	 * @throws RelacionEntregaConUDocsSinAsingarAUIException
	 * @see common.bi.GestionRelacionesEACRBI#permitirEnviarRelacionEACR(java.lang.String)
	 */
	public void checkPermtirEnviarRelacionEACR(String idRelEntrega)
			throws RelacionEntregaNoEnviableUIsConVariasFSException,
			RelacionEntregaNoEnviableUIsConVariasUDocsException,
			RelacionEntregaConUDocsSinAsingarAUIException {

		// Comprobar que todos las unidades documentales están asignadas a un
		// unidad de instalación
		int numUDocsSinAsignar = _udocEnUiReeaCRDBEntity
				.getCountUDocsSinUI(idRelEntrega);

		if (numUDocsSinAsignar != 0) {
			throw new RelacionEntregaConUDocsSinAsingarAUIException();
		}

		RelacionEntregaVO relacionEntregaVO = _relacionEntregaDBEntity
				.getRelacionXId(idRelEntrega);
		if (relacionEntregaVO != null) {
			FormatoHuecoVO formato = _formatoDBEntity
					.getFormatoById(relacionEntregaVO.getIdFormatoRe());
			if (formato != null && !formato.isMultidoc()) {
				if (_uiReeaCRDBEntity
						.getCountUIsWithUDocsCorrectas(idRelEntrega) > 0) {
					throw new RelacionEntregaNoEnviableUIsConVariasUDocsException();
				}
			}

			INivelCFVO nivelCF = _nivelCFDBEntity
					.getNivelCFById(relacionEntregaVO.getIdNivelDocumental());
			if (nivelCF != null
					&& SubtipoNivelCF.CAJA.getIdentificador() == nivelCF
							.getSubtipo()) {
				if (_uiReeaCRDBEntity
						.getCountUIsWithUDocsCorrectas(idRelEntrega) > 0) {
					throw new RelacionEntregaNoEnviableUIsConVariasFSException();
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#crearUIsReeaConReencajadoNoTransaccional(java.lang.String,
	 *      java.util.List, java.lang.String)
	 */
	public void crearUIsReeaConReencajadoNoTransaccional(String idRelEntrega,
			List listaUIs, String idFormato) {

		// Obtener el formato
		FormatoHuecoVO formato = _formatoDBEntity.getFormatoById(idFormato);

		if (formato != null) {
			Map mapUdocs = new HashMap();
			for (Iterator iter = listaUIs.iterator(); iter.hasNext();) {
				boolean uiCreada = false;
				UnidadInstalacionReeaVO unidadInstalacion = (UnidadInstalacionReeaVO) iter
						.next();

				if (unidadInstalacion != null) {
					String idUIDeposito = unidadInstalacion.getId();

					// Obtener las unidades documentales de la unidad de
					// instalacion
					List listaUnidades = _udocEnUIDepositoDBEntity
							.getUdocsEnUIWithAsuntoAndExpediente(idUIDeposito);

					if (ListUtils.isNotEmpty(listaUnidades)) {
						String idUIReeaCR = Constants.STRING_EMPTY;
						for (Iterator iterator = listaUnidades.iterator(); iterator
								.hasNext();) {
							UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) iterator
									.next();
							String idUnidadDoc = udoc.getIdunidaddoc();
							if ((formato.isMultidoc() && uiCreada == false)
									|| !formato.isMultidoc()) {
								// Crear unidad de Instalación
								UIReeaCRVO ui = new UIReeaCRVO(idRelEntrega,
										idFormato);
								_uiReeaCRDBEntity.insert(ui);

								uiCreada = true;
								idUIReeaCR = ui.getId();
							}

							// Crear el registro en IDUDOORIGEN
							UDocReeaCRVO uDocReeaCRVO = new UDocReeaCRVO(
									idRelEntrega, udoc.getIdunidaddoc(),
									udoc.getSignaturaudoc(),
									udoc.getIduinstalacion(),
									unidadInstalacion.getSignaturaUIOrigen());
							_udocReeaCRDBEntity.insert(uDocReeaCRVO);

							int numParte = _udocEnUiReeaCRDBEntity
									.getNumParteSiguiente(idRelEntrega,
											udoc.getIdunidaddoc());

							// Crear la uniad documental correspondiente
							UDocEnUIReeaCRVO udocEnUI = new UDocEnUIReeaCRVO();

							udocEnUI.setIdUDocOrigen(uDocReeaCRVO.getId());
							udocEnUI.setIdRelEntrega(idRelEntrega);
							udocEnUI.setIdUnidadDoc(udoc.getIdunidaddoc());
							udocEnUI.setIdUIReeaCR(idUIReeaCR);
							udocEnUI.setNumParte(numParte);

							if (numParte > 1) {
								udocEnUI.setCompleta(false);
							} else {
								udocEnUI.setCompleta(true);
							}
							udocEnUI.setDescripcion(udoc.getDescripcion());

							udocEnUI.setAsunto(udoc.getTitulo());
							udocEnUI.setNumExp(udoc.getNumExp());
							udocEnUI.setFechaIni(udoc.getFechaIni());
							udocEnUI.setFechaFin(udoc.getFechaFin());
							udocEnUI.setSignaturaUdocOrigen(udoc
									.getSignaturaudoc());

							mapUdocs.put(idUnidadDoc, idUnidadDoc);

							_udocEnUiReeaCRDBEntity.insert(udocEnUI);
						}
					}
				}
			}

			updateNumPartesNoTransaccional(idRelEntrega, mapUdocs);
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUIsReencajado(java.lang.String)
	 */
	public List getUIsReencajado(String idRelEntrega) {
		return _uiReeaCRDBEntity.getByIdRelacion(idRelEntrega);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUDocsByUIReencajado(java.lang.String)
	 */
	public List getUDocsByUIReencajado(String idUI) {
		return _udocEnUiReeaCRDBEntity.getByIdUI(idUI);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUDocsReencajadoByIdRelacion(java.lang.String)
	 */
	public List getUDocsSinAsignarByIdRelacion(String idRelEntrega) {
		return _udocEnUiReeaCRDBEntity
				.getUdocsSinAsignarByIdRelacion(idRelEntrega);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUIReencajadoById(java.lang.String)
	 */
	public UIReeaCRVO getUIReencajadoById(String idUIReeacr) {
		return _uiReeaCRDBEntity.getById(idUIReeacr);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getCountUIs(java.lang.String)
	 */
	public int getCountUIs(String idRelEntrega) {
		return _uiReeaCRDBEntity.getCountByIdRelacion(idRelEntrega);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getCountUIsEstadoPendiente(java.lang.String)
	 */
	public int getCountUIsEstadoPendiente(String idRelacion) {
		int[] estadosCotejo = new int[] { EstadoCotejo.PENDIENTE
				.getIdentificador() };

		return _uiReeaCRDBEntity.getCountByEstadosCotejo(idRelacion,
				estadosCotejo);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUdocEnUIById(java.lang.String)
	 */
	public UDocEnUIReeaCRVO getUdocEnUIById(String idUdocEnUI) {
		return _udocEnUiReeaCRDBEntity.getById(idUdocEnUI);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getNumUIsReeaCr(java.lang.String)
	 */
	public int getNumUIsReeaCr(String idRelEntrega) {
		return _uiReeaCRDBEntity.getCountByRelacion(idRelEntrega);
	}

	/**
	 * {@inheritDoc}
    *
	 * @param idRelEntrega
	 * @see common.bi.GestionRelacionesEACRBI#updateDescripcionUdoc(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateDescripcionUdoc(String idUdoc, String descripcion) {
		_udocEnUiReeaCRDBEntity.updateDescripcion(idUdoc, descripcion);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#updateDescripcionUI(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateDescripcionUI(String idUI, String descripcion) {
		_uiReeaCRDBEntity.updateDescripcion(idUI, descripcion);
	}

	private void updateUdocCompletaNoTransaccional(String idRelEntrega,
			Map mapUdocs) {
		for (Iterator iterator = mapUdocs.keySet().iterator(); iterator
				.hasNext();) {
			String idUnidadDoc = (String) iterator.next();

			// Obtener todas las partes de la unidad documental
			List listaUdocs = _udocEnUiReeaCRDBEntity.getByIdUDoc(idRelEntrega,
					idUnidadDoc);

			if (ListUtils.isNotEmpty(listaUdocs)) {
				int numPartes = listaUdocs.size();
				boolean udocCompleta = true;
				if (numPartes > 1) {
					udocCompleta = false;
				}
				_udocEnUiReeaCRDBEntity.updateIndicadorCompleta(idRelEntrega,
						idUnidadDoc, udocCompleta);
			}
		}
	}

	private void updateNumPartesNoTransaccional(String idRelEntrega,
			Map mapUdocs) {

		for (Iterator iterator = mapUdocs.keySet().iterator(); iterator
				.hasNext();) {
			String idUnidadDoc = (String) iterator.next();

			// Obtener todas las partes de la unidad documental
			List listaUdocs = _udocEnUiReeaCRDBEntity.getByIdUDoc(idRelEntrega,
					idUnidadDoc);

			if (ListUtils.isNotEmpty(listaUdocs)) {
				int numPartes = listaUdocs.size();
				int numParte = 1;
				for (Iterator it = listaUdocs.iterator(); it.hasNext();) {
					UDocEnUIReeaCRVO udoc = (UDocEnUIReeaCRVO) it.next();
					boolean udocCompleta = true;
					if (numPartes > 1) {
						udocCompleta = false;
					}
					_udocEnUiReeaCRDBEntity.updateNumParte(udoc.getId(),
							numParte, udocCompleta);
					numParte++;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#updateSignaturaUI(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateSignaturaUI(String idUI, String signaturaUI) {
		_uiReeaCRDBEntity.updateSignatura(idUI, signaturaUI);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#updateSignaturaUdocsByIdUinstalacion(java.lang.String)
	 */
	public void updateSignaturaUdocsByIdUinstalacion(String idUI) {

		UIReeaCRVO uIReeaCRVO = _uiReeaCRDBEntity.getById(idUI);
		List listaUnidades = _udocEnUiReeaCRDBEntity.getByIdUI(idUI);
		FormatoHuecoVO formatoHueco = _formatoDBEntity
				.getFormatoById(uIReeaCRVO.getIdFormato());

		for (Iterator it = listaUnidades.iterator(); it.hasNext();) {
			UDocEnUIReeaCRVO uDocEnUIReeaCRVO = (UDocEnUIReeaCRVO) it.next();

			String signaturaUdoc = SignaturaUtil.getSignaturaUdoc(
					uIReeaCRVO.getSignaturaUI(), formatoHueco,
					uDocEnUIReeaCRVO.getPosUdocEnUI());

			_udocEnUiReeaCRDBEntity.updateSignatura(uDocEnUIReeaCRVO.getId(),
					signaturaUdoc);
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#updateEstadoCotejo(java.lang.String,
	 *      int)
	 */
	public void updateEstadoCotejo(String idUI, int estado) {
		_uiReeaCRDBEntity.updateFieldEstado(idUI, estado);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#marcarUIsRevisadas(java.lang.String[])
	 */
	public void marcarUIsRevisadas(String[] ids) {
		_uiReeaCRDBEntity.updateFieldEstado(ids,
				EstadoCotejo.REVISADA.getIdentificador(), false);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUnidadesInstalacion(java.lang.String,
	 *      common.util.IntervalOptions)
	 */
	public List getUnidadesInstalacion(String idRelacionEntrega,
			IntervalOptions ordenes) {
		return _uiReeaCRDBEntity.getByIdRelacion(idRelacionEntrega, ordenes);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#eliminarUIsReencajadoNoTransaccional(java.lang.String,
	 *      java.lang.String[])
	 */
	public void eliminarUIsReencajadoNoTransaccional(String idRelEntrega,
			String[] idsUIDeposito) {

		// Obtener las partes de las unidades documentales afectadas
		List listaPartes = _udocEnUiReeaCRDBEntity
				.getPartesUDocByIdsUinstalacion(idRelEntrega, idsUIDeposito);

		if (ListUtils.isNotEmpty(listaPartes)) {

			Map mapUdocs = new HashMap();
			Map mapUIs = new HashMap();

			String[] idsDoc = ArrayUtils.getArrayIds(listaPartes);
			_udocEnUiReeaCRDBEntity.deleteByIds(idsDoc);
			_udocReeaCRDBEntity.deleteByIdsUIDeposito(idRelEntrega,
					idsUIDeposito);

			// Renumerar las unidades documentales que no están asignadas a
			// ninguna caja.
			renumerarUnidadesDocumentalesCRNoTransaccional(idRelEntrega, null);

			for (Iterator iterator = listaPartes.iterator(); iterator.hasNext();) {
				UDocEnUIReeaCRVO udoc = (UDocEnUIReeaCRVO) iterator.next();

				if (udoc != null) {
					mapUdocs.put(udoc.getIdUnidadDoc(), udoc.getIdUnidadDoc());
					mapUIs.put(udoc.getIdUIReeaCR(), udoc.getIdUIReeaCR());
				}
			}

			for (Iterator it = mapUIs.keySet().iterator(); it.hasNext();) {
				String idUI = (String) it.next();
				if (idUI != null) {
					renumerarUnidadesDocumentalesCRNoTransaccional(
							idRelEntrega, idUI);
				}
			}

			updateNumPartesNoTransaccional(idRelEntrega, mapUdocs);
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#crearDatosEnDeposito(java.lang.String)
	 */
	public void crearDatosEnDepositoNoTransaccional(String idRelEntrega) {

		// Obtener la Relación de Entrega
		RelacionEntregaVO relacionEntrega = getGestionRelacionesBI()
				.getRelacionXIdRelacion(idRelEntrega);

		if (relacionEntrega != null) {
			// Obtener el fondo destino
			FondoVO fondoVO = getGestionFondosBI().getFondoXId(
					relacionEntrega.getIdfondodestino());
			String codigoFondo = fondoVO.getCodReferencia();

			// Obtener las unidades de instalación de la relacón de entrega
			List listaUI = _uiReeaCRDBEntity.getByIdRelacion(idRelEntrega);

			if (ListUtils.isNotEmpty(listaUI)) {

				StringBuffer identificacionUI = new StringBuffer();
				for (Iterator iterator = listaUI.iterator(); iterator.hasNext();) {
					UIReeaCRVO uiReeaCRVO = (UIReeaCRVO) iterator.next();

					if (uiReeaCRVO != null) {
						identificacionUI.setLength(0);
						identificacionUI
								.append(codigoFondo)
								.append(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION)
								.append(uiReeaCRVO.getSignaturaUI());
						UInsDepositoVO uInsDepositoVO = new UInsDepositoVO(
								uiReeaCRVO.getIdFormato(),
								uiReeaCRVO.getSignaturaUI(),
								identificacionUI.toString());

						_uiInstalacionDepositoDBEntity
								.insertUInstDepositoVO(uInsDepositoVO);

						_huecoDBEntity.updateHuecoIdUInstalacionByIdUIReeaCR(
								idRelEntrega, uiReeaCRVO.getId(),
								uInsDepositoVO.getId());

						_uiReeaCRDBEntity.updateFieldIdUIDeposito(
								uiReeaCRVO.getId(), uInsDepositoVO.getId());

						// obtener las unidades documentales
						List listaUdocs = _udocEnUiReeaCRDBEntity
								.getByIdUI(uiReeaCRVO.getId());

						if (ListUtils.isNotEmpty(listaUdocs)) {
							for (Iterator itUdocs = listaUdocs.iterator(); itUdocs
									.hasNext();) {
								UDocEnUIReeaCRVO udoc = (UDocEnUIReeaCRVO) itUdocs
										.next();

								if (udoc != null) {
									String identificacionUDoc = new StringBuffer(
											identificacionUI.toString())
											.append(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL)
											.append(udoc.getPosUdocEnUI())
											.toString();

									UDocEnUiDepositoVO udocEnUIDepositoVO = new UDocEnUiDepositoVO(
											uInsDepositoVO.getId(),
											identificacionUDoc, udoc);

									_udocEnUIDepositoDBEntity
											.insertUDocEnUiDeposito(udocEnUIDepositoVO);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUIsReencajado(java.lang.String,
	 *      common.util.IntervalOptions)
	 */
	public List getUIsReencajado(String idRelEntrega, IntervalOptions ordenes) {
		return _uiReeaCRDBEntity.getByIdRelacion(idRelEntrega, ordenes);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see common.bi.GestionRelacionesEACRBI#getUDocsByIdRelacion(java.lang.String)
	 */
	public List getUDocsByIdRelacion(String idRelEntrega) {
		return _udocEnUiReeaCRDBEntity
				.getUDocsByIdRelacionOrderBySignatura(idRelEntrega);
	}

	public List getUDocsByIdRelacionGroupByUnidadDoc(String idRelacion) {
		return _udocEnUiReeaCRDBEntity
				.getUDocsByIdRelacionGroupByUnidadDoc(idRelacion);
	}

}