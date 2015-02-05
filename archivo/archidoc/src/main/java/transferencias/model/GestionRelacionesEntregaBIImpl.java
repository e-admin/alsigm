package transferencias.model;

import ieci.core.guid.GuidManager;
import ieci.core.types.IeciTdType;

import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.tramites.DocElectronico;
import se.tramites.DocFisico;
import se.tramites.Emplazamiento;
import se.tramites.Expediente;
import se.tramites.InfoBExpediente;
import se.tramites.Interesado;
import se.tramites.SistemaTramitador;
import se.tramites.SistemaTramitadorFactory;
import se.tramites.exceptions.SistemaTramitadorException;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import solicitudes.db.IRevisionDocumentacionDBEntity;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import transferencias.CodigoUdocUtil;
import transferencias.EstadoCotejo;
import transferencias.MarcaUdocRelacionConstants;
import transferencias.ReservaPrevision;
import transferencias.TipoTransferencia;
import transferencias.TipoUInstalacion;
import transferencias.TransferenciasConstants;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.actions.RelacionEntregaPO;
import transferencias.db.IDetallePrevisionDBEntity;
import transferencias.db.IMapDescUDocDBEntity;
import transferencias.db.INSecTransferenciasDBEntity;
import transferencias.db.INSecUDocDBEntity;
import transferencias.db.INSecUIDBEntity;
import transferencias.db.IPrevisionDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.db.IUDocEnUIReeaCRDBEntity;
import transferencias.db.IUDocRelacionDBEntity;
import transferencias.db.IUIReeaCRDBEntity;
import transferencias.db.IUdocElectronicaDBEntity;
import transferencias.db.IUdocEnUIDBEntity;
import transferencias.db.IUnidadInstalacionDBEntity;
import transferencias.db.IUnidadInstalacionReeaDBEntity;
import transferencias.db.RelacionEntregaDBEntityBaseImpl;
import transferencias.electronicas.udoc.IdentificacionUnidadDocumental;
import transferencias.exceptions.PrevisionOperacionNoPermitidaException;
import transferencias.exceptions.RelacionEntregaConUDocsSinAsingarAUIException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasFSException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasUDocsException;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.model.AuditoriaTransferencias.PistaAuditoriaIngreso;
import transferencias.model.AuditoriaTransferencias.PistaAuditoriaTransferencias;
import transferencias.model.helper.CotejoHelper;
import transferencias.model.validacion.ComprobacionDatosDescripcion;
import transferencias.model.validacion.ObtencionValor;
import transferencias.vos.BusquedaRelacionesVO;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.DocumentoElectronicoVO;
import transferencias.vos.DocumentoVO;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.InteresadoVO;
import transferencias.vos.MapDescUDocVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.ProductorUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.TransferenciaElectronicaInfo;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UIReeaCRVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import transferencias.vos.UnidadInstalacionVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionGeneral;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionTransferencias;
import auditoria.ArchivoErrorCodes;

import common.ConfigConstants;
import common.Constants;
import common.Conversor;
import common.Messages;
import common.MotivoEliminacionUnidadInstalacion;
import common.MultiEntityConstants;
import common.bi.GestionArchivosBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.SecurityException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;
import common.exceptions.UncheckedArchivoException;
import common.pagination.PageInfo;
import common.security.TransferenciasSecurityManager;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.DateUtils;
import common.util.IntervalOptions;
import common.util.ListUtils;
import common.util.MarcaUtil;
import common.vos.Direccion;
import common.vos.TypeDescVO;

import deposito.MarcaUInstalacionConstants;
import deposito.MarcaUtilUI;
import deposito.SignaturaUtil;
import deposito.db.ConcurrentModificationException;
import deposito.db.IFormatoDbEntity;
import deposito.db.IHistUInstalacionDepositoDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.exceptions.DepositoHuecoReservarNoLibreSignaturaAsociadaHuecoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HistUInstDepositoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.IFechaDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.db.IValidacionDBEntity;
import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoDescriptor;
import descripcion.model.ValoresFicha;
import descripcion.model.automaticos.ADReglaGenDatosContants;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.TextoTablaValidacionVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import docelectronicos.db.IUnidadDocumentalElectronicaDBEntity;
import fondos.db.IDivisionFSDbEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.IUDocEnDivisionFSDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.TipoProductor;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.UDocEnFraccionSerieVO;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.IPermisosListaDbEntity;
import gcontrol.model.NombreOrganoFormat;
import gcontrol.model.TipoAcceso;
import gcontrol.model.TipoDestinatario;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.UsuarioVO;

/**
 * Implementacion de los metodos del servicio de gestion de relaciones de *ir
 * entrega y unidades documentales
 */
public class GestionRelacionesEntregaBIImpl extends ServiceBase implements
		GestionRelacionesEntregaBI {
	static final String SOPORTE_PAPEL = "Papel";
	static final String SOPORTE_ELECTRONICO = "Electrónico";
	static final String FORMATO_DOCUMENTO = "Documento/s";
	// Map de pares key=idFicha / valor=MapDescUDocVO
	private static Map mapDescUDocMap = null;

	private IOrganoProductorDbEntity _organoProductorDBEntity = null;
	private IProductorSerieDbEntity _productorSerieDBEntity = null;
	IRelacionEntregaDBEntity _relacionEntregaDBEntity = null;
	INSecTransferenciasDBEntity _nSecDBEntity = null;
	IPrevisionDBEntity _previsionDbEntity = null;
	IDetallePrevisionDBEntity _detallePrevisionDBEntity = null;
	IFormatoDbEntity _formatoDBEntity = null;
	IUDocRelacionDBEntity _unidadDocumentalDBEntity = null;
	IUdocEnUIDBEntity _udocEnUIDBEntity = null;
	IUnidadInstalacionDBEntity _unidadInstalacionDBEntity = null;
	INSecUIDBEntity _nSecUIDBEntity = null;
	INSecUDocDBEntity _nSecUDocBEntity = null;
	IDescriptorDBEntity _descriptorDBEntity = null;
	IValidacionDBEntity _validacionDBEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUIDepositoDBEntity = null;
	IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity = null;
	IUInstalacionDepositoDBEntity _uInstalacionDepositoDBEntity = null;
	IHuecoDBEntity _huecoDBEntity = null;
	IPermisosListaDbEntity _permisosListaDbEntity = null;
	IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDbEntity = null;
	IUdocElectronicaDBEntity _udocElectronicaDbEntity = null;
	IUnidadDocumentalElectronicaDBEntity _unidadDocumentalElectronicaDBEntity = null;
	IMapDescUDocDBEntity _mapDescUDocDbEntity = null;
	ITextoDBEntity _textoCortoUDocREDbEntity = null;
	ITextoDBEntity _textoLargoUDocREDbEntity = null;
	INumeroDBEntity _numeroUdocREDbEntity = null;
	IFechaDBEntity _fechaUDocREDbEntity = null;
	IReferenciaDBEntity _referenciaUDocREDbEntity = null;
	IUDocEnDivisionFSDbEntity _udocEnDivisionFSDbEntity = null;
	IDivisionFSDbEntity _divisionFSDbEntity = null;
	IArchivoDbEntity _archivoDbEntity = null;
	INivelCFDBEntity _nivelDbEntity = null;
	IRevisionDocumentacionDBEntity _revisionDocumentacionDBEntity = null;
	IReferenciaDBEntity _campoReferenciaDBEntity = null;
	IHistUInstalacionDepositoDBEntity _histUinstDepositoDBEntity = null;
	IUnidadDocumentalDbEntity _unidadDocDBEntity = null;
	IUIReeaCRDBEntity _uiReeaCRDBEntity = null;
	IUDocEnUIReeaCRDBEntity _udocEnUiReeaCRDBEntity = null;
	IUDocRelacionDBEntity _udocRelacionDBEntity = null;

	public GestionRelacionesEntregaBIImpl(
			IRelacionEntregaDBEntity relacionEntregaDBEntity,
			INSecTransferenciasDBEntity nSecDBEntity,
			IPrevisionDBEntity previsionDbEntity,
			IDetallePrevisionDBEntity detallePrevisionDBEntity,
			IFormatoDbEntity formatoDBEntity,
			IUDocRelacionDBEntity unidadDocumentalDBEntity,
			IUdocEnUIDBEntity udocEnUIDBEntity,
			IUnidadInstalacionDBEntity unidadInstalacionDBEntity,
			INSecUIDBEntity nSecUIDBEntity,
			IDescriptorDBEntity descriptorDBEntity,
			IValidacionDBEntity validacionDBEntity,
			IProductorSerieDbEntity productorSerieDBEntity,
			IOrganoProductorDbEntity organoProductorDBEntity,
			IUDocEnUiDepositoDbEntity udocEnUIDepositoDBEntity,
			IUnidadInstalacionReeaDBEntity unidadInstalacionReeaDBEntity,
			IUInstalacionDepositoDBEntity unidadInstalacionDepositoDBEntity,
			IHuecoDBEntity huecoDBEntity,
			INSecUDocDBEntity nSecUDocDBEntity,
			IPermisosListaDbEntity permisosListaDbEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDbEntity,
			IMapDescUDocDBEntity mapDescUDocDbEntity,
			IUdocElectronicaDBEntity uDocElectronicaDBEntity,
			IUnidadDocumentalElectronicaDBEntity unidadDocumentalElectronicaDBEntity,
			ITextoDBEntity textoCortoUDocREDbEntity,
			ITextoDBEntity textoLargoUDocREDbEntity,
			INumeroDBEntity numeroUdocREDbEntity,
			IFechaDBEntity fechaUDocREDbEntity,
			IReferenciaDBEntity referenciaUDocREDbEntity,
			IUDocEnDivisionFSDbEntity udocEnDivisionFSDbEntity,
			IDivisionFSDbEntity divisionFSDbEntity,
			IArchivoDbEntity archivoDbEntity, INivelCFDBEntity nivelDbEntity,
			IRevisionDocumentacionDBEntity revisionDocumentacionDBEntity,
			IReferenciaDBEntity campoReferenciaDBEntity,
			IHistUInstalacionDepositoDBEntity histUinstDepositoDBEntity,
			IUnidadDocumentalDbEntity unidadDocDBEntity,
			IUIReeaCRDBEntity uiReeaCRDBEntity,
			IUDocEnUIReeaCRDBEntity udocEnUiReeaCRDBEntity) {

		this._relacionEntregaDBEntity = relacionEntregaDBEntity;
		this._nSecDBEntity = nSecDBEntity;
		this._previsionDbEntity = previsionDbEntity;
		this._detallePrevisionDBEntity = detallePrevisionDBEntity;
		this._formatoDBEntity = formatoDBEntity;
		this._unidadDocumentalDBEntity = unidadDocumentalDBEntity;
		this._udocEnUIDBEntity = udocEnUIDBEntity;
		this._unidadInstalacionDBEntity = unidadInstalacionDBEntity;
		this._nSecUIDBEntity = nSecUIDBEntity;
		this._descriptorDBEntity = descriptorDBEntity;
		this._validacionDBEntity = validacionDBEntity;
		this._productorSerieDBEntity = productorSerieDBEntity;
		this._organoProductorDBEntity = organoProductorDBEntity;
		this._udocEnUIDepositoDBEntity = udocEnUIDepositoDBEntity;
		this._unidadInstalacionReeaDBEntity = unidadInstalacionReeaDBEntity;
		this._uInstalacionDepositoDBEntity = unidadInstalacionDepositoDBEntity;
		this._huecoDBEntity = huecoDBEntity;
		this._permisosListaDbEntity = permisosListaDbEntity;
		this._nSecUDocBEntity = nSecUDocDBEntity;
		this._elementoCuadroClasificacionDbEntity = elementoCuadroClasificacionDbEntity;
		this._mapDescUDocDbEntity = mapDescUDocDbEntity;
		this._udocElectronicaDbEntity = uDocElectronicaDBEntity;
		this._unidadDocumentalElectronicaDBEntity = unidadDocumentalElectronicaDBEntity;
		this._textoCortoUDocREDbEntity = textoCortoUDocREDbEntity;
		this._textoLargoUDocREDbEntity = textoLargoUDocREDbEntity;
		this._numeroUdocREDbEntity = numeroUdocREDbEntity;
		this._fechaUDocREDbEntity = fechaUDocREDbEntity;
		this._referenciaUDocREDbEntity = referenciaUDocREDbEntity;
		this._udocEnDivisionFSDbEntity = udocEnDivisionFSDbEntity;
		this._divisionFSDbEntity = divisionFSDbEntity;
		this._archivoDbEntity = archivoDbEntity;
		this._nivelDbEntity = nivelDbEntity;
		this._revisionDocumentacionDBEntity = revisionDocumentacionDBEntity;
		this._campoReferenciaDBEntity = campoReferenciaDBEntity;
		this._histUinstDepositoDBEntity = histUinstDepositoDBEntity;
		this._unidadDocDBEntity = unidadDocDBEntity;
		this._uiReeaCRDBEntity = uiReeaCRDBEntity;
		this._udocEnUiReeaCRDBEntity = udocEnUiReeaCRDBEntity;
	}

	private IRelacionAuthorizationHelper getAuthorizationHelper(
			RelacionEntregaVO relacionEntregaVO) {
		if (relacionEntregaVO.getIsIngresoDirecto())
			return new IngresoDirectoAuthorizationHelper();
		else
			return new RelacionAuthorizationHelper();
	}

	private IRelacionAuthorizationHelper getAuthorizationHelper(
			int tipoTransferencia) {
		if (tipoTransferencia == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador())
			return new IngresoDirectoAuthorizationHelper();
		else
			return new RelacionAuthorizationHelper();
	}

	/** Logger de la clase */
	static Logger logger = Logger
			.getLogger(GestionRelacionesEntregaBIImpl.class);

	/**
	 * Logica de comprobacion de las acciones que pueden ser llevadas a cabo
	 * sobre una relacion de entrega
	 */
	private class RelacionAuthorizationHelper implements
			IRelacionAuthorizationHelper {
		int errorCode = -1;

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #configureRelacionEntrega(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean configureRelacionEntrega(
				RelacionEntregaVO relacionEntrega) {

			if(relacionEntrega.isAutomatizada()){
				return false;
			}

			relacionEntrega
					.setPuedeSerEliminada(puedeSerEliminada(relacionEntrega));
			relacionEntrega
					.setPuedeSerEnviada(puedeSerEnviada(relacionEntrega));
			relacionEntrega
					.setPuedeSerModificada(puedeSerModificada(relacionEntrega));
			relacionEntrega
					.setPuedeSerRecibida(puedeSerRecibida(relacionEntrega));
			relacionEntrega
					.setPuedeSerCotejada(puedeSerCotejada(relacionEntrega));
			relacionEntrega
					.setPuedeSerSignaturada(puedeSerSignaturada(relacionEntrega));
			relacionEntrega
					.setPuedeSerUbicada(puedeSerUbicada(relacionEntrega));
			relacionEntrega
					.setPuedeSerValidada(puedeSerValidada(relacionEntrega));
			relacionEntrega
					.setPermitidaAdicionExpedientes(permitidaAdicionExpedientes(relacionEntrega));
			relacionEntrega
					.setPermitidaSustraccionExpedientes(permitidaSustraccionExpedientes(relacionEntrega));
			relacionEntrega
					.setPermitidaFinalizacionCorreccion(permitidaFinalizacionCorreccion(relacionEntrega));
			relacionEntrega
					.setPermitidaImpresionCartelasDefinitivas(permitidaImpresionCartelasDefinitivas(relacionEntrega));
			relacionEntrega
					.setPermitidaImpresionCartelasProvisionales(permitidaImpresionCartelasProvisionales(relacionEntrega));
			relacionEntrega
					.setPermitidaModificacionAsignacionCajas(permitidaModificacionAsignacionCajas(relacionEntrega));
			relacionEntrega
					.setPermitidaModificacionUbicacion(permitidaModificacionUbicacion(relacionEntrega));
			relacionEntrega
					.setPermitidoCorregirErrores(permitidoCorregirErrores(relacionEntrega));
			relacionEntrega
					.setPermitirEditarDescripcionContenido(permitirEditarDescripcionContenido(relacionEntrega));
			relacionEntrega
					.setPermitidaAdicionDocsElectronicos(permitidaAdicionUDocsElectronicas(relacionEntrega));
			relacionEntrega
					.setPermitidaSustraccionDocsElectronicos(permitidaSustraccionDocsElectronicos(relacionEntrega));
			relacionEntrega
					.setPermitidaImportacionExpedientes(puedeImportarExpedientes(relacionEntrega));
			relacionEntrega
					.setPermitidoBloqueoDesbloqueoExpedientes(permitidoBloqueoDesbloqueoExpedientes(relacionEntrega));
			relacionEntrega
					.setPermitidoMostrarBloqueoDesbloqueoExpedientes(permitidoMostrarBloqueoDesbloqueoExpedientes(relacionEntrega));
			relacionEntrega
					.setPuedeSerRechazada(puedeSerRechazada(relacionEntrega));
			relacionEntrega
					.setPermitidoActivarReencajado(permitidoActivarReencajado(relacionEntrega));
			relacionEntrega
					.setPermitidoAccionesReencajado(permitidoAccionesReencajado(relacionEntrega));
			relacionEntrega
					.setPermitidoMarcarRevisada(permitidoMarcarRevisada(relacionEntrega));
			return errorCode != -1;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#configureRelacionEntregaBusqueda(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean configureRelacionEntregaBusqueda(
				RelacionEntregaVO relacionEntrega) {
			relacionEntrega
					.setPuedeSerEliminada(puedeSerEliminada(relacionEntrega));
			return errorCode != -1;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEliminada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEliminada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerEliminada = false;
			if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
							.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()) {
				if (relacionEntrega.getIdusrgestorrem().equals(
						getServiceClient().getId()))
					puedeSerEliminada = true;
				else
					errorCode = ArchivoErrorCodes.SOLO_PERMITIDO_A_GESTOR;
			} else
				errorCode = ArchivoErrorCodes.RELACION_NO_ABIERTA;
			return puedeSerEliminada;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerModificada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerModificada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerModificada = false;
			// Por defecto el usuario es el del organo remitente.
			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

			// Si está con errores y tiene el indicador de corregir errores en
			// archivo
			if (relacionEntrega.isConErroresCotejo()
					&& Constants.TRUE_STRING.equals(relacionEntrega
							.getCorreccionenarchivo())) {
				// El usuario es del arhivo receptor
				usuarioRelacion = relacionEntrega.getIdusrgestorarchivorec();
			}

			INivelCFVO nivelCFVO = _nivelDbEntity
					.getNivelCFById(relacionEntrega.getIdNivelDocumental());

			int subtipo = nivelCFVO.getId() != null ? nivelCFVO.getSubtipo()
					: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
			if (usuarioRelacion.equals(getServiceClient().getId())
					&& (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
							.getIdentificador() || relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
							.getIdentificador())) {
				puedeSerModificada = true;
			} else if (usuarioRelacion.equals(getServiceClient().getId())
					&& relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()
					&& relacionEntrega.getTipotransferencia() == TipoTransferencia.ORDINARIA
							.getIdentificador()
					&& relacionEntrega.isSinDocsFisicos()) {
				puedeSerModificada = true;
			} else if (usuarioRelacion.equals(getServiceClient().getId())
					&& relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()
					&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				puedeSerModificada = true;
			} else if (relacionEntrega.isAutomatizada()) {
				puedeSerModificada = true;
			}

			return puedeSerModificada;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEnviada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerEnviada = false;
			if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador())
				if (relacionEntrega.getIdusrgestorrem().equals(
						getServiceClient().getId()))
					if (relacionEntrega.isEntreArchivos()) {
						if (_unidadInstalacionReeaDBEntity
								.countUIsRelacion(relacionEntrega.getId()) > 0
								|| _udocElectronicaDbEntity
										.countUdocsElectronicasByRelacion(relacionEntrega
												.getId()) > 0) {
							puedeSerEnviada = true;
						}
					} else {
						if (_unidadDocumentalDBEntity.countUdocsConEstado(
								relacionEntrega.getId(), null) > 0) {
							puedeSerEnviada = true;
						}
					}
			return puedeSerEnviada;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerRecibida(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerRecibida(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerRecibida = false;
			if (relacionEntrega.getEstado() == EstadoREntrega.ENVIADA
					.getIdentificador())
				puedeSerRecibida = true;
			return puedeSerRecibida;
		}

		boolean puedeSerCotejada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerCotejada = false;
			if (relacionEntrega.getEstado() == EstadoREntrega.RECIBIDA
					.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.CORREGIDA_ERRORES
							.getIdentificador())
				if (relacionEntrega.getIdusrgestorarchivorec().equals(
						getServiceClient().getId()))
					puedeSerCotejada = true;
			return puedeSerCotejada;
		}

		boolean puedeSerSignaturada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerSignaturada = true;

			if (relacionEntrega.isEntreArchivos()
					&& !ConfigConstants.getInstance()
							.getSignaturacionPorArchivo())
				puedeSerSignaturada = false;
			return puedeSerSignaturada;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerUbicada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerUbicada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerUbicada = false;

			// Si no hay documentos físicos.
			if (Constants.TRUE_STRING.equals(relacionEntrega
					.getSindocsfisicos()))
				return false;

			RelacionEntregaVO relacionEntregaVOBD = getRelacionXIdRelacion(relacionEntrega
					.getId());

			if (relacionEntregaVOBD != null) {

				boolean isUiEnDeposito = relacionEntregaVOBD.isUiendeposito();
				int estado = relacionEntregaVOBD.getEstado();

				if (logger.isDebugEnabled()) {
					if (relacionEntrega.getEstado() != estado) {
						logger.debug("Estado relacionEntrega:"
								+ relacionEntrega.getEstado());
						logger.debug("Estado relacionEntregaBD: " + estado);
					}

					if (isUiEnDeposito != relacionEntrega.isUiendeposito()) {
						logger.debug("isUiEnDeposito:"
								+ relacionEntrega.isUiendeposito());
						logger.debug("isUiEnDepositoBD:"
								+ relacionEntrega.isUiendeposito());
					}
				}

				if (((estado == EstadoREntrega.SIGNATURIZADA.getIdentificador()) || (estado == EstadoREntrega.COTEJADA
						.getIdentificador())) && !isUiEnDeposito) {
					puedeSerUbicada = true;
				}
			}

			return puedeSerUbicada;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerValidada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerValidada(RelacionEntregaVO relacionEntrega) {
			boolean puedeSerValidada = false;

			int estado = getEstadoRelacion(relacionEntrega.getId());

			if (logger.isDebugEnabled()
					&& relacionEntrega.getEstado() != estado) {
				logger.debug("Estado relacionEntrega:"
						+ relacionEntrega.getEstado());
				logger.debug("Estado relacionEntregaBD: " + estado);
			}

			if (estado == EstadoREntrega.SIGNATURIZADA.getIdentificador()
					&& (relacionEntrega.isUiendeposito() || Constants.TRUE_STRING
							.equals(relacionEntrega.getSindocsfisicos()))) {
				puedeSerValidada = true;
			}

			if (relacionEntrega.isAutomatizada()) {
				puedeSerValidada = true;
			}

			return puedeSerValidada;
		}

		boolean permitidaAdicionExpedientes(RelacionEntregaVO relacionEntrega) {
			boolean permitidaAdicionExpedientes = false;

			if (puedeSerModificada(relacionEntrega)) {
				permitidaAdicionExpedientes = true;
			}
			return permitidaAdicionExpedientes;
		}

		boolean permitidaAdicionUDocsElectronicas(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaAdicionUDocsElectronicas = false;

			if (puedeSerModificada(relacionEntrega)) {
				permitidaAdicionUDocsElectronicas = true;
			} else {
				if (relacionEntrega.isConErroresCotejo()
						&& relacionEntrega.isSinDocsFisicos()) {
					permitidaAdicionUDocsElectronicas = true;
				}
			}
			return permitidaAdicionUDocsElectronicas;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaSustraccionExpedientes(transferencias
		 * .vos.RelacionEntregaVO)
		 */
		public boolean permitidaSustraccionExpedientes(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaSustraccionExpedientes = false;

			// Por defecto el usuario es el del organo remitente.
			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

			// Si está con errores y tiene el indicador de corregir errores en
			// archivo
			if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()
					&& Constants.TRUE_STRING.equals(relacionEntrega
							.getCorreccionenarchivo())) {
				// El usuario es del arhivo receptor
				usuarioRelacion = relacionEntrega.getIdusrgestorarchivorec();
			}

			if (usuarioRelacion.equals(getServiceClient().getId())) {
				if (relacionEntrega.isAbierta()
						|| relacionEntrega.isRechazada()) {
					permitidaSustraccionExpedientes = true;
				} else if (relacionEntrega.isConErroresCotejo()) {
					if (!relacionEntrega.isSinDocsFisicos()) {
						if (hayCajasADevolver(getUnidadesInstalacion(relacionEntrega
								.getId()))) {
							permitidaSustraccionExpedientes = true;
						}
					}
					INivelCFVO nivelCFVO = _nivelDbEntity
							.getNivelCFById(relacionEntrega
									.getIdNivelDocumental());
					int subtipo = nivelCFVO.getId() != null ? nivelCFVO
							.getSubtipo()
							: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
					if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
						permitidaSustraccionExpedientes = true;
					}
				}
			}
			return permitidaSustraccionExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#permitidoBloqueoDesbloqueoExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidoBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidoBloqueoDesbloqueoExpedientes = false;

			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();
			ServiceClient serviceClient = getServiceClient();
			if (usuarioRelacion.equals(getServiceClient().getId())
					&& serviceClient
							.hasPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES)) {
				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == relacionEntrega
								.getTipotransferencia())) {
					permitidoBloqueoDesbloqueoExpedientes = true;
				}
			}
			return permitidoBloqueoDesbloqueoExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#permitidoMostrarBloqueoDesbloqueoExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidoMostrarBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidoMostrarBloqueoDesbloqueoExpedientes = false;

			if (relacionEntrega.isEntreArchivos()) {
				permitidoMostrarBloqueoDesbloqueoExpedientes = true;
			}
			return permitidoMostrarBloqueoDesbloqueoExpedientes;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaSustraccionExpedientes(transferencias
		 * .vos.RelacionEntregaVO)
		 */
		public boolean permitidaSustraccionDocsElectronicos(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaSustraccionDocsElectronicos = false;

			// Por defecto el usuario es el del organo remitente.
			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

			// Si está con errores y tiene el indicador de corregir errores en
			// archivo
			if (relacionEntrega.isConErroresCotejo()
					&& Constants.TRUE_STRING.equals(relacionEntrega
							.getCorreccionenarchivo())) {
				// El usuario es del arhivo receptor
				usuarioRelacion = relacionEntrega.getIdusrgestorarchivorec();
			}

			if (usuarioRelacion.equals(getServiceClient().getId())) {
				if (relacionEntrega.isAbierta()) {
					permitidaSustraccionDocsElectronicos = true;
				} else if (relacionEntrega.isConErroresCotejo()) {
					permitidaSustraccionDocsElectronicos = true;
				} else if (relacionEntrega.isRechazada()) {
					permitidaSustraccionDocsElectronicos = true;
				} else {
					permitidaSustraccionDocsElectronicos = false;
				}
			}

			return permitidaSustraccionDocsElectronicos;
		}

		public boolean permitirEditarDescripcionContenido(
				RelacionEntregaVO relacionEntrega) {

			boolean permitirEditarDescripcionContenido;

			// Por defecto el usuario es el del organo remitente.
			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

			// Si está con errores y tiene el indicador de corregir errores en
			// archivo
			if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()
					&& Constants.TRUE_STRING.equals(relacionEntrega
							.getCorreccionenarchivo())) {
				// El usuario es del arhivo receptor
				usuarioRelacion = relacionEntrega.getIdusrgestorarchivorec();
			}

			if (usuarioRelacion.equals(getServiceClient().getId())
					&& relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()) {
				permitirEditarDescripcionContenido = true;
			} else {
				permitirEditarDescripcionContenido = false;
			}

			return permitirEditarDescripcionContenido;
		}

		boolean permitidoCorregirErrores(RelacionEntregaVO relacionEntrega) {
			boolean permitidoCorregirErrores = false;

			// Si la relación está en estado con errores de cotejo
			if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()) {
				// Por defecto el usuario es el del organo remitente.
				String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

				if (Constants.TRUE_STRING.equals(relacionEntrega
						.getCorreccionenarchivo())) {
					usuarioRelacion = relacionEntrega
							.getIdusrgestorarchivorec();
				}

				if (usuarioRelacion.equals(getServiceClient().getId())) {
					permitidoCorregirErrores = true;
				}
			}
			return permitidoCorregirErrores;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaFinalizacionCotejo(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidaFinalizacionCotejo(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaFinalizacionCotejo = false;
			int estadoRelacion = relacionEntrega.getEstado();
			if (estadoRelacion == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()
					|| estadoRelacion == EstadoREntrega.CORREGIDA_ERRORES
							.getIdentificador()
					|| estadoRelacion == EstadoREntrega.RECIBIDA
							.getIdentificador()) {

				int[] estados = new int[] { EstadoCotejo.PENDIENTE
						.getIdentificador() };

				int udocsPendientes = -1;
				if (relacionEntrega.isRelacionConReencajado()) {
					udocsPendientes = _uiReeaCRDBEntity
							.getCountByEstadosCotejo(relacionEntrega.getId(),
									estados);
				} else {
					udocsPendientes = _unidadDocumentalDBEntity
							.countUdocsConEstado(relacionEntrega.getId(),
									estados);
				}
				int udocsElectronicasPendientes = _udocElectronicaDbEntity
						.countUdocsElectronicasConEstado(
								relacionEntrega.getId(), estados);

				if (udocsPendientes == 0 && udocsElectronicasPendientes == 0) {

					permitidaFinalizacionCotejo = true;

					// en transferencia ordinaria no puede finalizarse cotejo si
					// hay errores en cajas sin devolver
					if (relacionEntrega.getTipotransferencia() == TipoTransferencia.ORDINARIA
							.getIdentificador()) {
						if (estadoRelacion == EstadoREntrega.CORREGIDA_ERRORES
								.getIdentificador()
								|| estadoRelacion == EstadoREntrega.RECIBIDA
										.getIdentificador()) {
							List cajasRelacion = getUnidadesInstalacion(relacionEntrega
									.getId());
							if (cajasRelacion != null) {
								for (Iterator itCajasRelacion = cajasRelacion
										.iterator(); itCajasRelacion.hasNext();) {
									UnidadInstalacionVO aCaja = (UnidadInstalacionVO) itCajasRelacion
											.next();
									List udocsEnCaja = getUdocsEnUI(aCaja
											.getId());
									if (udocsEnCaja != null) {
										boolean hayErrores = false;
										for (Iterator itUdocEnCaja = udocsEnCaja
												.iterator(); itUdocEnCaja
												.hasNext();) {
											IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itUdocEnCaja
													.next();
											if (aPart.getEstadoCotejo() == EstadoCotejo.ERRORES
													.getIdentificador()) {
												// Comprobar si hay más partes
												// que no estén marcadas con
												// errores, ya que
												// en transferencias ordinarias,
												// si una parte está marcada
												// errores, el resto de partes
												// también se debe marcar con
												// errores
												List listaUDocsSinErrores = _udocEnUIDBEntity
														.getListaPartesByEstado(
																aPart.getIdUnidadDoc(),
																new int[] {
																		EstadoCotejo.PENDIENTE
																				.getIdentificador(),
																		EstadoCotejo.REVISADA
																				.getIdentificador() });
												if (ListUtils
														.isEmpty(listaUDocsSinErrores)) {
													hayErrores = true;
												} else {
													permitidaFinalizacionCotejo = false;
													errorCode = ArchivoErrorCodes.EN_ORDINARIAS_SI_PARTE_CON_ERRORES_TODAS_CON_ERRORES;
													return permitidaFinalizacionCotejo;
												}
											}
										}
										if (hayErrores) {
											if (!aCaja.isDevolver()) {
												permitidaFinalizacionCotejo = false;
												errorCode = ArchivoErrorCodes.EN_ORDINARIA_NO_PUEDE_FINALIZARSE_COTEJO_SI_EXISTE_ERRORES_EN_CAJAS_NO_DEVUELTAS;
											}
										}
									}

								}
							}

						}

					}

				} else
					errorCode = ArchivoErrorCodes.RELACION_TIENE_UDOCS_EN_ESTADO_PENDIENTE;
			} else
				errorCode = ArchivoErrorCodes.RELACION_EN_ESTADO_INCORRECTO;

			return permitidaFinalizacionCotejo;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaFinalizacionCorreccion(transferencias
		 * .vos.RelacionEntregaVO)
		 */
		public boolean permitidaFinalizacionCorreccion(
				RelacionEntregaVO relacionEntrega) {
			// TODO: lo que habría que comprobar es que el usuario es gestor de
			// la relación en el órgano remitente
			boolean permitidaFinalizacionCorreccion = false;
			int[] estadoCotejo = { EstadoCotejo.ERRORES.getIdentificador() };

			// Por defecto el usuario es el del organo remitente.
			String usuarioRelacion = relacionEntrega.getIdusrgestorrem();

			// Si está con errores y tiene el indicador de corregir errores en
			// archivo
			if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()
					&& Constants.TRUE_STRING.equals(relacionEntrega
							.getCorreccionenarchivo())) {
				// El usuario es del arhivo receptor
				usuarioRelacion = relacionEntrega.getIdusrgestorarchivorec();
			}

			int udocsElectronicasErrores = _udocElectronicaDbEntity
					.countUdocsElectronicasConEstado(relacionEntrega.getId(),
							estadoCotejo);

			if (usuarioRelacion.equals(getServiceClient().getId())
					&& relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()) {

				if (relacionEntrega.isEntreArchivos()) {
					// Si es entre archivos se mira que todas las unidades de
					// instalación estén sin errores.
					int numUIs = 0;
					if (relacionEntrega.isRelacionConReencajado()) {
						numUIs = _uiReeaCRDBEntity.getCountByEstadosCotejo(
								relacionEntrega.getId(), estadoCotejo);
					} else {
						numUIs = _unidadInstalacionReeaDBEntity
								.countUinstConEstado(relacionEntrega.getId(),
										estadoCotejo);
					}

					if (numUIs == 0 && udocsElectronicasErrores == 0) {
						permitidaFinalizacionCorreccion = true;
					}
				} else {
					int udocsErrores = _unidadDocumentalDBEntity
							.countUdocsConEstado(relacionEntrega.getId(),
									estadoCotejo);

					if (udocsErrores == 0 && udocsElectronicasErrores == 0) {

						permitidaFinalizacionCorreccion = true;
						// comprobar que todas las udocs estan en cajas
						if (!_unidadDocumentalDBEntity
								.isAllUdocsConDocFisicosEnCaja(relacionEntrega
										.getId())) {
							permitidaFinalizacionCorreccion = false;
							errorCode = ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA;
						}
					}
				}

				if (permitidaFinalizacionCorreccion) {
					int[] estados = new int[] {
							EstadoCotejo.PENDIENTE.getIdentificador(),
							EstadoCotejo.REVISADA.getIdentificador() };
					int numUdocs = 0;
					int numUInst = 0;

					int numUdocsElectronicas = _udocElectronicaDbEntity
							.countUdocsElectronicasConEstado(
									relacionEntrega.getId(), estados);

					if (relacionEntrega.isEntreArchivos()) {
						// Comprobar que la relacion tenga cajas o documentos
						// electrónicos.
						if (relacionEntrega.isRelacionConReencajado()) {
							numUInst = _uiReeaCRDBEntity
									.getCountByIdRelacion(relacionEntrega
											.getId());
							numUdocs = _udocEnUiReeaCRDBEntity
									.getCountUDocs(relacionEntrega.getId());
						} else {
							numUInst = _unidadInstalacionReeaDBEntity
									.countUIsRelacion(relacionEntrega.getId());
						}
						if (numUInst == 0 && numUdocsElectronicas == 0) {
							permitidaFinalizacionCorreccion = false;
						}
						if (relacionEntrega.isRelacionConReencajado()
								&& numUdocs == 0) {
							errorCode = ArchivoErrorCodes.REEA_REENCAJADO_NO_FINALIZA_SIN_UDOCS;
							permitidaFinalizacionCorreccion = false;
						}
					} else {
						// Comprobar que la relacion tenga expedientes
						numUdocs = _unidadDocumentalDBEntity
								.countUdocsConEstado(relacionEntrega.getId(),
										estados);
						if (numUdocs == 0 && numUdocsElectronicas == 0)
							permitidaFinalizacionCorreccion = false;
					}
				}
			}
			return permitidaFinalizacionCorreccion;
		}

		/**
		 * Comprueba si se pueden imprimir cartelas definitivas para una
		 * relacion de entrega
		 */
		boolean permitidaImpresionCartelasDefinitivas(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaImpresionCartelesDefinitivas = false;

			// Si no hay documentos físicos.
			if (Constants.TRUE_STRING.equals(relacionEntrega
					.getSindocsfisicos()))
				return false;

			if (relacionEntrega.getEstado() == EstadoREntrega.SIGNATURIZADA
					.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.VALIDADA
							.getIdentificador()) {
				permitidaImpresionCartelesDefinitivas = true;
			}

			return permitidaImpresionCartelesDefinitivas;
		}

		/**
		 * Comprueba si se pueden imprimir cartelas provisionales para una
		 * relacion de entrega
		 */
		boolean permitidaImpresionCartelasProvisionales(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaImpresionCartelesProvisionales = false;

			// Si no hay documentos físicos.
			if (Constants.TRUE_STRING.equals(relacionEntrega
					.getSindocsfisicos())) {
				return false;
			}

			if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()) {
				if (relacionEntrega.getIdusrgestorrem().equals(
						getServiceClient().getId())) {
					if (relacionEntrega.isEntreArchivos()) {
						if (relacionEntrega.isRelacionConReencajado()) {
							if (_uiReeaCRDBEntity
									.getCountByIdRelacion(relacionEntrega
											.getId()) > 0) {
								permitidaImpresionCartelesProvisionales = true;
							}
						} else {
							if (_unidadInstalacionReeaDBEntity
									.countUIsRelacion(relacionEntrega.getId()) > 0) {
								permitidaImpresionCartelesProvisionales = true;
							}
						}
					} else {
						if (_unidadInstalacionDBEntity
								.countUIsRelacion(relacionEntrega.getId()) > 0) {
							permitidaImpresionCartelesProvisionales = true;
						}
					}
				}
			}
			return permitidaImpresionCartelesProvisionales;
		}

		/**
		 * Comprueba si se puede modificar la asignacion de unidades
		 * documentales a unidades de instalacion de la relacion de entrega
		 */
		boolean permitidaModificacionAsignacionCajas(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaModificacionAsignacionCajas = false;
			FormatoHuecoVO formatoRelacion = _formatoDBEntity
					.loadFormato(relacionEntrega.getIdFormatoDestino());
			if ((formatoRelacion != null && formatoRelacion.isMultidoc())
					|| relacionEntrega.isRelacionConReencajado()) {
				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
								.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
								.getIdentificador()) {

					// Por defecto el usuario es el del organo remitente.
					String usuario = relacionEntrega.getIdusrgestorrem();

					// Si está con errores y tiene el indicador de corregir
					// errores en archivo
					if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()
							&& Constants.TRUE_STRING.equals(relacionEntrega
									.getCorreccionenarchivo())) {
						// El usuario es del arhivo receptor
						usuario = relacionEntrega.getIdusrgestorarchivorec();
					}

					if (usuario.equals(getServiceClient().getId()))
						permitidaModificacionAsignacionCajas = true;
				}
			}
			return permitidaModificacionAsignacionCajas;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaModificacionUbicacion(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidaModificacionUbicacion(
				RelacionEntregaVO relacionEntrega) {
			boolean permitidaModificacionUbicacion = false;

			// Si solo tiene documentos Físicos...
			if (Constants.TRUE_STRING.equals(relacionEntrega
					.getSindocsfisicos())) {
				return false;
			}

			if (relacionEntrega.getEstado() == EstadoREntrega.RECIBIDA
					.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()
					|| relacionEntrega.getEstado() == EstadoREntrega.CORREGIDA_ERRORES
							.getIdentificador()) {
				if (relacionEntrega.getReservadeposito() == ReservaPrevision.NO_RESERVADA
						.getIdentificador()
						|| relacionEntrega.getReservadeposito() == ReservaPrevision.NO_SE_HA_PODIDO
								.getIdentificador())
					permitidaModificacionUbicacion = true;
			} else if (((relacionEntrega.getEstado() == EstadoREntrega.SIGNATURIZADA
					.getIdentificador()) || (relacionEntrega.getEstado() == EstadoREntrega.COTEJADA
					.getIdentificador()))
					&& !relacionEntrega.isUiendeposito())
				permitidaModificacionUbicacion = true;

			return permitidaModificacionUbicacion;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #configureUnidadDocumental(transferencias.vos.RelacionEntregaVO,
		 * transferencias.vos.UnidadDocumentalVO)
		 */
		public boolean configureUnidadDocumental(
				RelacionEntregaVO relacionEntrega,
				UnidadDocumentalVO unidadDocumental) {
			unidadDocumental.setPermitidoRealizarCambios(puedeSerModificada(
					relacionEntrega, unidadDocumental));
			return errorCode == -1;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerModificada(transferencias.vos.RelacionEntregaVO,
		 * transferencias.vos.UnidadDocumentalVO)
		 */
		public boolean puedeSerModificada(RelacionEntregaVO relacionEntrega,
				UnidadDocumentalVO unidadDocumental) {

			boolean puedeSerModificada = false;
			if (relacionEntrega.getTipotransferencia() != TipoTransferencia.ORDINARIA
					.getIdentificador())
				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
								.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
								.getIdentificador()) {

					// Por defecto el usuario es el del organo remitente.
					String usuarioRelacion = relacionEntrega
							.getIdusrgestorrem();

					// Si está con errores y tiene el indicador de corregir
					// errores en archivo
					if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador()
							&& Constants.TRUE_STRING.equals(relacionEntrega
									.getCorreccionenarchivo())) {
						// El usuario es del arhivo receptor
						usuarioRelacion = relacionEntrega
								.getIdusrgestorarchivorec();
					}

					if (usuarioRelacion.equals(getServiceClient().getId()))
						puedeSerModificada = true;
					else
						errorCode = ArchivoErrorCodes.SOLO_PERMITIDO_A_GESTOR;
				} else
					errorCode = ArchivoErrorCodes.RELACION_EN_ESTADO_INCORRECTO;
			else
				errorCode = ArchivoErrorCodes.NO_PERMITIDO_EN_TRANSFERENCIAS_ORDINARIAS;
			return puedeSerModificada;

		}

		public int getErrorCode() {
			return errorCode;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviadoSeleccionarUbicacionIngreso
		 * (transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEnviadoSeleccionarUbicacionIngreso(
				RelacionEntregaVO ingreso) {
			return false;
		}

		public boolean puedeSerValidadaRelacion(
				RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#puedeImportarExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeImportarExpedientes(
				RelacionEntregaVO relacionEntrega) {
			boolean puedeImportarExpedientes = false;

			boolean importacionSinSignatura = ConfigConstants.getInstance()
					.getPermitirCargaXmlRelacionExtraordinariaSinSignatura();
			boolean importacionConSignatura = ConfigConstants.getInstance()
					.getPermitirCargaXmlRelacionExtraordinariaConSignatura();

			boolean relacionSinSignatura = (relacionEntrega
					.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
					.getIdentificador());
			boolean relacionConSignatura = (relacionEntrega
					.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
					.getIdentificador());

			if ((importacionSinSignatura && relacionSinSignatura)
					|| (importacionConSignatura && relacionConSignatura)) {
				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
								.getIdentificador()) {
					if ((_unidadDocumentalDBEntity
							.countRowsByCodigoRelacion(relacionEntrega.getId()) == 0)
							&& (_unidadInstalacionDBEntity
									.countUIsRelacion(relacionEntrega.getId()) == 0)
							&& (relacionEntrega.getIdFicha() != null)) {
						puedeImportarExpedientes = true;
					}
				}
			}

			return puedeImportarExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#puedeSerRechazada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerRechazada(RelacionEntregaVO relacionEntregaVO) {
			return puedeSerRecibida(relacionEntregaVO);
		}
	}

	private interface IRelacionAuthorizationHelper {

		/**
		 * Determina y configura las acciones que puden ser realizadas sobre una
		 * relacion de entrega
		 *
		 * @param relacionEntrega
		 *            Relacion de entrega a configurar
		 */
		public boolean configureRelacionEntrega(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Determina y configura las acciones que puden ser realizadas sobre una
		 * relacion de entrega proveniente de una búsqueda
		 *
		 * @param relacionEntrega
		 *            Relacion de entrega a configurar
		 */
		public boolean configureRelacionEntregaBusqueda(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Permite obtener el código de error
		 *
		 * @param Código
		 *            de error
		 */
		public int getErrorCode();

		/**
		 * Comprueba si la relacion de entrega puede ser enviada
		 *
		 * @param relacionEntrega
		 *            Relación a comprobar
		 * @return si la relación puede ser enviada
		 */
		public boolean puedeSerEnviada(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si la relacion de entrega puede ser recibida
		 *
		 * @param relacionEntrega
		 *            Relación a comprobar
		 * @return si la relación puede ser recibida
		 */
		public boolean puedeSerRecibida(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si la relacion de entrega puede ser eliminada
		 *
		 * @param relacionEntrega
		 *            Relación a comprobar
		 * @return si la relación puede ser eliminada
		 */
		public boolean puedeSerEliminada(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si la relacion de entrega puede ser validada
		 *
		 * @param relacionEntrega
		 *            Relación a comprobar
		 * @return si la relación puede ser validada
		 */
		boolean puedeSerValidada(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si la relacion de entrega puede ser ubicada
		 *
		 * @param relacionEntrega
		 *            Relación a comprobar
		 * @return si la relación puede ser ubicada
		 */
		boolean puedeSerUbicada(RelacionEntregaVO relacionEntrega);

		/**
		 * Permite obtener si puede ser modificada la relación
		 *
		 * @param relacion
		 *            Relación a comprobar
		 * @return si puede ser modificada
		 */
		public boolean puedeSerModificada(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si una relación de Entrega puede ser rechazada.
		 *
		 * @param relacionEntregaVO
		 * @return
		 */
		public boolean puedeSerRechazada(RelacionEntregaVO relacionEntregaVO);

		/**
		 * Comprueba si es posible finalizar el cotejo de una relacion de
		 * entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si se permite la finalización del cotejo
		 */
		public boolean permitidaFinalizacionCotejo(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Permite configurar una unidad documental
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @param unidadDocumental
		 *            Unidad documental a configurar
		 * @return
		 */
		public boolean configureUnidadDocumental(
				RelacionEntregaVO relacionEntrega,
				UnidadDocumentalVO unidadDocumental);

		/**
		 * Comprueba si una unidad documental incluida en una relacion de
		 * entrega puede ser modificada
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @param unidadDocumental
		 *            Unidad documental a comprobar
		 * @return si la unidad documental puede ser modificada
		 */
		boolean puedeSerModificada(RelacionEntregaVO relacionEntrega,
				UnidadDocumentalVO unidadDocumental);

		/**
		 * Comprueba si es posible dar por finalizada la correccion de los
		 * errores de cotejo de una relacion de entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si puede ser finalizada la correción de la relación de
		 *         entrega
		 */
		boolean permitidaFinalizacionCorreccion(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si se permite la sustraccion de expedientes a una relacion
		 * de entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si se permite eliminar expedientes de la relación de entrega
		 */
		boolean permitidaSustraccionExpedientes(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si se permite el bloqueo/desbloqueo de expedientes en una
		 * relacion de entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si se permite bloquear/desbloquear expedientes en una
		 *         relacion de entrega
		 */
		boolean permitidoBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si se permite el mostrar el indicador de bloqueo/desbloqueo
		 * de expedientes en una relacion de entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si se permite mostrar el indicador de bloqueao/desbloqueo
		 *         expedientes en una relacion de entrega
		 */
		boolean permitidoMostrarBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si se puede modificar la ubicacion que tiene asignada como
		 * destino una relacion de entrega
		 *
		 * @param relacionEntrega
		 *            Relación de entrega
		 * @return si se permite modificar la ubicación de la relación de
		 *         entrega
		 */
		boolean permitidaModificacionUbicacion(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si en el ingreso puede ser enviado parar seleccionar la
		 * ubicación
		 *
		 * @param ingreso
		 *            Ingreso a seleccionar ubicación
		 * @return si el ingreso puede ser enviado
		 */
		boolean puedeSerEnviadoSeleccionarUbicacionIngreso(
				RelacionEntregaVO ingreso);

		/**
		 * Comprueba si se puede validar la relacion de entrega cuando todas las
		 * unidades de instalación que contiene estan asociadas a cajas
		 * existentes y tiene estado abierto.
		 *
		 * @param relacionEntrega
		 * @return si el ingreso puede ser validado
		 */
		boolean puedeSerValidadaRelacion(RelacionEntregaVO relacionEntrega);

		/**
		 * Comprueba si se pueden importar expedientes desde un xml
		 *
		 * @param relacionEntrega
		 * @return si se pueden importar expedientes
		 */
		boolean puedeImportarExpedientes(RelacionEntregaVO relacionEntrega);
	}

	/**
	 * Logica de comprobacion de las acciones que pueden ser llevadas a cabo
	 * sobre un ingreso directo
	 */
	private class IngresoDirectoAuthorizationHelper implements
			IRelacionAuthorizationHelper {
		int errorCode = -1;

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #configureRelacionEntrega(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean configureRelacionEntrega(RelacionEntregaVO ingreso) {
			if(ingreso.isAutomatizada()){
				return false;
			}

			ingreso.setPuedeSerEliminada(puedeSerEliminada(ingreso));
			ingreso.setPuedeSerModificada(puedeSerModificada(ingreso));
			ingreso.setPuedeSerUbicada(puedeSerUbicada(ingreso));
			ingreso.setPermitidaAdicionExpedientes(permitidaAdicionExpedientes(ingreso));
			ingreso.setPermitidaSustraccionExpedientes(permitidaSustraccionExpedientes(ingreso));
			ingreso.setPermitidaImpresionCartelasDefinitivas(permitidaImpresionCartelasDefinitivas(ingreso));
			ingreso.setPermitidaModificacionAsignacionCajas(permitidaModificacionAsignacionCajas(ingreso));
			ingreso.setPermitidaModificacionUbicacion(permitidaModificacionUbicacion(ingreso));
			ingreso.setPuedeSerEnviadaSeleccionarUbicacionIngreso(puedeSerEnviadoSeleccionarUbicacionIngreso(ingreso));
			ingreso.setPuedeSerValidadoIngreso(puedeSerValidadoIngreso(ingreso));
			ingreso.setPermitidaImportacionExpedientes(puedeImportarExpedientes(ingreso));
			ingreso.setPermitidoBloqueoDesbloqueoExpedientes(permitidoBloqueoDesbloqueoExpedientes(ingreso));
			ingreso.setPermitidoMostrarBloqueoDesbloqueoExpedientes(permitidoMostrarBloqueoDesbloqueoExpedientes(ingreso));
			ingreso.setPuedeSerRechazada(puedeSerRechazada(ingreso));
			return errorCode != -1;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#configureRelacionEntregaBusqueda(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean configureRelacionEntregaBusqueda(
				RelacionEntregaVO ingreso) {
			ingreso.setPuedeSerEliminada(puedeSerEliminada(ingreso));
			return errorCode != -1;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEliminada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEliminada(RelacionEntregaVO ingreso) {
			boolean puedeSerEliminado = false;
			if (ingreso.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador()) {
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId()))
					puedeSerEliminado = true;
				else
					errorCode = ArchivoErrorCodes.SOLO_PERMITIDO_A_GESTOR;
			} else
				errorCode = ArchivoErrorCodes.RELACION_NO_ABIERTA;
			return puedeSerEliminado;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerModificada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerModificada(RelacionEntregaVO ingreso) {
			boolean puedeSerModificado = false;
			if (ingreso.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador()
					&& (StringUtils.isEmpty(ingreso.getIddeposito())))
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId()))
					puedeSerModificado = true;
			return puedeSerModificado;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviadoSeleccionarUbicacionIngreso
		 * (transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEnviadoSeleccionarUbicacionIngreso(
				RelacionEntregaVO ingreso) {
			boolean puedeSerEnviadoSeleccionarUbicacionIngreso = false;
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceClient());
			GestionArchivosBI archivoBI = services.lookupGestionArchivosBI();

			if (ingreso.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador()
					&& (StringUtils.isEmpty(ingreso.getIddeposito())))
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId())) {

					ArchivoVO archivo = archivoBI.getArchivoXId(ingreso
							.getIdarchivoreceptor());
					int numUIsAsignadas = _unidadInstalacionDBEntity
							.countUIsAsignadasRelacion(ingreso.getId());
					boolean signaturacionXHueco = (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
							.getIdentificador() == archivo
							.getTiposignaturacion());

					// Si no hay unidades asignadas en el ingreso, se puede
					// seleccionar ubicación, en caso contrario, no, porque ya
					// viene determinada
					// por la ubicación de las unidades asignadas que va a ser
					// la misma para todas
					if (!signaturacionXHueco
							|| (signaturacionXHueco && numUIsAsignadas == 0)) {
						if (_unidadDocumentalDBEntity.countUdocsConEstado(
								ingreso.getId(), null) > 0) {
							puedeSerEnviadoSeleccionarUbicacionIngreso = true;
						}
					}
				}
			return puedeSerEnviadoSeleccionarUbicacionIngreso;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviadoSeleccionarUbicacionIngreso
		 * (transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerValidadoIngreso(RelacionEntregaVO ingreso) {
			boolean puedeSerValidadoIngreso = false;

			int estado = getEstadoRelacion(ingreso.getId());

			if (estado == EstadoREntrega.ABIERTA.getIdentificador()
					&& (StringUtils.isEmpty(ingreso.getIddeposito())))
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId())) {
					if (_unidadInstalacionDBEntity.countUIsRelacion(ingreso
							.getId()) > 0
							&& _unidadInstalacionDBEntity
									.allUIsAsociadas(ingreso.getId())
							&& _unidadDocumentalDBEntity
									.isAllUdocsConDocFisicosEnCaja(ingreso
											.getId()))
						puedeSerValidadoIngreso = true;
					if (_unidadDocumentalDBEntity.countUdocsConEstado(
							ingreso.getId(), null) == 0)
						puedeSerValidadoIngreso = false;
				}
			return puedeSerValidadoIngreso;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviadoSeleccionarUbicacionIngreso
		 * (transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerValidadaRelacion(RelacionEntregaVO ingreso) {
			boolean puedeSerValidadaRelacion = false;
			int estado = getEstadoRelacion(ingreso.getId());

			if (logger.isDebugEnabled() && ingreso.getEstado() != estado) {
				logger.debug("Estado relacionEntrega:" + ingreso.getEstado());
				logger.debug("Estado relacionEntregaBD: " + estado);
			}

			if (estado == EstadoREntrega.ABIERTA.getIdentificador()
					&& (StringUtils.isEmpty(ingreso.getIddeposito())))
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId())) {
					if (estado == EstadoREntrega.ABIERTA.getIdentificador()
							&& _unidadInstalacionDBEntity
									.allUIsAsociadas(ingreso.getId()))
						puedeSerValidadaRelacion = true;
				}
			return puedeSerValidadaRelacion;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerUbicada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerUbicada(RelacionEntregaVO ingreso) {
			boolean puedeSerUbicada = false;
			ServiceClient serviceClient = getServiceClient();
			ServiceRepository services = ServiceRepository
					.getInstance(serviceClient);

			if (serviceClient
					.hasPermission(AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES)) {
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId())) {

					GestionArchivosBI archivoBI = services
							.lookupGestionArchivosBI();
					ArchivoVO archivo = archivoBI.getArchivoXId(ingreso
							.getIdarchivoreceptor());
					int countUIsAsignadas = _unidadInstalacionDBEntity
							.countUIsAsignadasRelacion(ingreso.getId());
					int countUIs = _unidadInstalacionDBEntity
							.countUIsRelacion(ingreso.getId());
					boolean ingresoMixto = (countUIs > 0
							&& countUIsAsignadas > 0 && (countUIs - countUIsAsignadas) > 0);
					boolean todasUDocsEnCajas = _unidadDocumentalDBEntity
							.isAllUdocsConDocFisicosEnCaja(ingreso.getId());
					boolean signaturacionXHueco = (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
							.getIdentificador() == archivo
							.getTiposignaturacion());

					int estado = getEstadoRelacion(ingreso.getId());

					if (logger.isDebugEnabled()
							&& ingreso.getEstado() != estado) {
						logger.debug("Estado relacionEntrega:"
								+ ingreso.getEstado());
						logger.debug("Estado relacionEntregaBD: " + estado);
					}

					if (estado == EstadoREntrega.ABIERTA.getIdentificador()
							&& (StringUtils.isNotEmpty(ingreso.getIddeposito()) || (signaturacionXHueco
									&& ingresoMixto && todasUDocsEnCajas))) {
						puedeSerUbicada = true;
					}
					/*
					 * if (_unidadDocumentalDBEntity
					 * .countUdocsConEstado(ingreso.getId(), null) == 0) {
					 * puedeSerUbicada = false; }
					 */
				}
			}
			return puedeSerUbicada;
		}

		boolean permitidaAdicionExpedientes(RelacionEntregaVO ingreso) {
			boolean permitidaAdicionExpedientes = false;
			if (ingreso.getIdusrgestorrem().equals(getServiceClient().getId())) {
				if (ingreso.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (StringUtils.isEmpty(ingreso.getIddeposito()))) {
					permitidaAdicionExpedientes = true;
				}

			}
			return permitidaAdicionExpedientes;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaSustraccionExpedientes(transferencias
		 * .vos.RelacionEntregaVO)
		 */
		public boolean permitidaSustraccionExpedientes(RelacionEntregaVO ingreso) {
			boolean permitidaSustraccionExpedientes = false;

			if (ingreso.getIdusrgestorrem().equals(getServiceClient().getId())) {
				if (ingreso.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (StringUtils.isEmpty(ingreso.getIddeposito()))) {
					permitidaSustraccionExpedientes = true;
				}
			}
			return permitidaSustraccionExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#permitidoBloqueoDesbloqueoExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidoBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO ingreso) {
			boolean permitidoBloqueoDesbloqueoExpedientes = false;

			ServiceClient serviceClient = getServiceClient();
			if (ingreso.getIdusrgestorrem().equals(getServiceClient().getId())
					&& serviceClient
							.hasPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES)) {
				if (ingreso.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (StringUtils.isEmpty(ingreso.getIddeposito()))) {
					permitidoBloqueoDesbloqueoExpedientes = true;
				}
			}
			return permitidoBloqueoDesbloqueoExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#permitidoMostrarBloqueoDesbloqueoExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidoMostrarBloqueoDesbloqueoExpedientes(
				RelacionEntregaVO ingreso) {
			boolean permitidoMostrarBloqueoDesbloqueoExpedientes = false;

			ServiceClient serviceClient = getServiceClient();
			if (serviceClient
					.hasPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES)) {
				permitidoMostrarBloqueoDesbloqueoExpedientes = true;
			}
			return permitidoMostrarBloqueoDesbloqueoExpedientes;
		}

		/**
		 * Comprueba si se pueden imprimir cartelas definitivas
		 */
		boolean permitidaImpresionCartelasDefinitivas(RelacionEntregaVO ingreso) {
			boolean permitidaImpresionCartelesDefinitivas = false;
			if (ingreso.getEstado() == EstadoREntrega.VALIDADA
					.getIdentificador())
				permitidaImpresionCartelesDefinitivas = true;
			return permitidaImpresionCartelesDefinitivas;
		}

		/**
		 * Comprueba si se puede modificar la asignacion de unidades
		 * documentales a unidades de instalacion
		 */
		boolean permitidaModificacionAsignacionCajas(RelacionEntregaVO ingreso) {
			boolean permitidaModificacionAsignacionCajas = false;
			FormatoHuecoVO formatoIngreso = _formatoDBEntity
					.loadFormato(ingreso.getIdFormatoDestino());
			if (formatoIngreso.isMultidoc())
				if (ingreso.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (StringUtils.isEmpty(ingreso.getIddeposito()))) {
					if (ingreso.getIdusrgestorrem().equals(
							getServiceClient().getId()))
						permitidaModificacionAsignacionCajas = true;
				}
			return permitidaModificacionAsignacionCajas;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaModificacionUbicacion(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidaModificacionUbicacion(RelacionEntregaVO ingreso) {
			boolean permitidaModificacionUbicacion = false;
			ServiceClient serviceClient = getServiceClient();
			ServiceRepository services = ServiceRepository
					.getInstance(serviceClient);
			GestionArchivosBI archivoBI = services.lookupGestionArchivosBI();

			if (serviceClient
					.hasPermission(AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES)) {
				if (ingreso.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						&& (StringUtils.isNotEmpty(ingreso.getIddeposito())))
					if (ingreso.getIdusrgestorrem().equals(
							getServiceClient().getId())) {
						ArchivoVO archivo = archivoBI.getArchivoXId(ingreso
								.getIdarchivoreceptor());
						int numUIsAsignadas = _unidadInstalacionDBEntity
								.countUIsAsignadasRelacion(ingreso.getId());
						boolean signaturacionXHueco = (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
								.getIdentificador() == archivo
								.getTiposignaturacion());
						// Si no hay unidades asignadas en el ingreso, se puede
						// seleccionar ubicación, en caso contrario, no, porque
						// ya viene determinada
						// por la ubicación de las unidades asignadas que va a
						// ser la misma para todas
						if (!signaturacionXHueco
								|| (signaturacionXHueco && numUIsAsignadas == 0)) {
							permitidaModificacionUbicacion = true;
						}
					}
			}
			return permitidaModificacionUbicacion;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerModificada(transferencias.vos.RelacionEntregaVO,
		 * transferencias.vos.UnidadDocumentalVO)
		 */
		public boolean puedeSerModificada(RelacionEntregaVO ingreso,
				UnidadDocumentalVO unidadDocumental) {
			boolean puedeSerModificada = false;
			if ((ingreso.getEstado() == EstadoREntrega.ABIERTA
					.getIdentificador())
					&& (StringUtils.isEmpty(ingreso.getIddeposito()))) {
				if (ingreso.getIdusrgestorrem().equals(
						getServiceClient().getId()))
					puedeSerModificada = true;
				else
					errorCode = ArchivoErrorCodes.SOLO_PERMITIDO_A_GESTOR;
			} else
				errorCode = ArchivoErrorCodes.RELACION_EN_ESTADO_INCORRECTO;
			return puedeSerModificada;

		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #configureUnidadDocumental(transferencias.vos.RelacionEntregaVO,
		 * transferencias.vos.UnidadDocumentalVO)
		 */
		public boolean configureUnidadDocumental(RelacionEntregaVO ingreso,
				UnidadDocumentalVO unidadDocumental) {
			unidadDocumental.setPermitidoRealizarCambios(puedeSerModificada(
					ingreso, unidadDocumental));
			return errorCode == -1;
		}

		public int getErrorCode() {
			return errorCode;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaFinalizacionCorreccion(transferencias
		 * .vos.RelacionEntregaVO)
		 */
		public boolean permitidaFinalizacionCorreccion(
				RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #permitidaFinalizacionCotejo(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean permitidaFinalizacionCotejo(
				RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerEnviada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerEnviada(RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerRecibida(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerRecibida(RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/*
		 * (sin Javadoc)
		 *
		 * @seetransferencias.model.GestionRelacionesEntregaBIImpl.
		 * IRelacionAuthorizationHelper
		 * #puedeSerValidada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerValidada(RelacionEntregaVO relacionEntrega) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#puedeImportarExpedientes(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeImportarExpedientes(
				RelacionEntregaVO relacionEntrega) {

			boolean puedeImportarExpedientes = false;

			if (ConfigConstants.getInstance()
					.getPermitirCargaXmlAltaUnidadesDocumentales()) {

				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
								.getIdentificador()) {
					if ((_unidadDocumentalDBEntity
							.countRowsByCodigoRelacion(relacionEntrega.getId()) == 0)
							&& (_unidadInstalacionDBEntity
									.countUIsRelacion(relacionEntrega.getId()) == 0)
							&& (relacionEntrega.getIdFicha() != null)) {
						puedeImportarExpedientes = true;
					}
				}
			}

			return puedeImportarExpedientes;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see transferencias.model.GestionRelacionesEntregaBIImpl.IRelacionAuthorizationHelper#puedeSerRechazada(transferencias.vos.RelacionEntregaVO)
		 */
		public boolean puedeSerRechazada(RelacionEntregaVO relacionEntregaVO) {
			return puedeSerRecibida(relacionEntregaVO);
		}
	}

	/**
	 * Obtiene la informacion referente a una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega {@link RelacionEntregaVO}
	 */
	public RelacionEntregaVO getRelacionXIdRelacion(String idRelacion) {
		RelacionEntregaVO relacionEntrega = _relacionEntregaDBEntity
				.getRelacionXId(idRelacion);
		if (relacionEntrega == null)
			throw new UncheckedArchivoException(
					"Relacion no encontrada en base de datos " + idRelacion);
		return relacionEntrega;
	}

	/**
	 * Pone una relacion de entrega a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la relacion
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega
	 */
	public RelacionEntregaVO abrirRelacionEntrega(String idRelacion) {
		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		getAuthorizationHelper(relacionEntrega).configureRelacionEntrega(
				relacionEntrega);
		return relacionEntrega;
	}

	/**
	 * Pone un ingreso directo a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la relacion
	 *
	 * @param idIngreso
	 *            Identificador del ingreso directo
	 * @return RelacionEntregaVO
	 */
	public RelacionEntregaVO abrirIngresoDirecto(String idIngreso) {
		RelacionEntregaVO ingreso = getRelacionXIdRelacion(idIngreso);
		getAuthorizationHelper(ingreso).configureRelacionEntrega(ingreso);
		return ingreso;
	}

	/**
	 * Obtiene el organo productor asociado a un id de descriptor
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor del productor
	 * @return Organo Productor
	 */
	public OrganoProductorVO getOrganoProductor(String idDescriptor) {
		return _organoProductorDBEntity.getOrgProductorXIdDescr(idDescriptor);
	}

	/**
	 * Pone una unidad documental a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la unidad documental
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega
	 */
	public UnidadDocumentalVO abrirUnidadDocumental(String idUnidadDocumental) {
		UnidadDocumentalVO unidadDocumental = _unidadDocumentalDBEntity
				.fetchRow(idUnidadDocumental);
		if (unidadDocumental == null)
			throw new UncheckedArchivoException(
					"La unidad documental no se ha encontrado en el sistema: "
							+ idUnidadDocumental);
		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(unidadDocumental
				.getIdRelEntrega());

		getAuthorizationHelper(relacionEntrega).configureUnidadDocumental(
				relacionEntrega, unidadDocumental);
		return unidadDocumental;
	}

	/**
	 * Obtiene la informacion referente a una unidad documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental
	 * @return Unidad documental {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO getUnidadDocumental(String idUnidadDocumental) {
		return _unidadDocumentalDBEntity.fetchRow(idUnidadDocumental);
	}

	private RelacionEntregaVO nuevaRelacion(PrevisionVO prevision,
			DetallePrevisionVO detallePrevision, String idSerie,
			String idGestorEnOrganoRemitente, String idFormato,
			String observaciones, String idDescrProductorRelacion,
			String idNivelDocumental, String idFicha) {
		RelacionEntregaVO relacionVO = new RelacionEntregaVO();
		relacionVO.setTipotransferencia(prevision.getTipotransferencia());
		relacionVO.setTipoprevision(prevision.getTipoprevision());
		relacionVO.setIdprevision(prevision.getId());
		relacionVO.setIdorganoremitente(prevision.getIdorgremitente());
		relacionVO.setIdarchivoreceptor(prevision.getIdarchivoreceptor());
		relacionVO.setIdfondo(prevision.getIdfondodestino());
		relacionVO.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
		relacionVO.setFechaestado(DateUtils.getFechaActual());
		relacionVO.setIdusrgestorrem(idGestorEnOrganoRemitente);
		relacionVO.setUiendeposito(false);
		relacionVO.setDevolucionui(false);
		relacionVO.setAno(Integer.toString(DateUtils.getAnoActual()));
		relacionVO.setIdarchivoreceptor(prevision.getIdarchivoreceptor());
		relacionVO.setObservaciones(observaciones);
		relacionVO.setIdformatoui(idFormato);
		relacionVO.setIddescrorgproductor(idDescrProductorRelacion);
		if (detallePrevision != null) {
			relacionVO.setIddetprevision(detallePrevision.getId());
			relacionVO.setCodsistproductor(detallePrevision
					.getCodSistProductor());
			relacionVO.setNombresistproductor(detallePrevision
					.getNombreSistProductor());
			relacionVO
					.setIdprocedimiento(detallePrevision.getIdProcedimiento());
			relacionVO.setIdseriedestino(detallePrevision.getIdSerieDestino());
		} else
			relacionVO.setIdseriedestino(idSerie);
		relacionVO.setIdNivelDocumental(idNivelDocumental);
		relacionVO.setIdFicha(idFicha);
		return relacionVO;
	}

	private RelacionEntregaVO nuevoIngresoDirecto(String idArchivo,
			String idFondo, String idSerie, String idFormato,
			String formaDocumental, String observaciones, String idUsrGestor,
			String idDescrProductorIngreso, String idGestorEnOrganoRemitente,
			String idArchivoRemitente, String idNivelDocumental, String idFicha) {

		RelacionEntregaVO ingresoVO = new RelacionEntregaVO();
		ingresoVO.setTipotransferencia(TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador());
		ingresoVO.setIdarchivoreceptor(idArchivo);
		ingresoVO.setIdfondo(idFondo);
		ingresoVO.setIdseriedestino(idSerie);
		ingresoVO.setIdformatoui(idFormato);
		ingresoVO.setTipoDocumental(formaDocumental);
		ingresoVO.setIdprevision(RelacionEntregaVO.SIN_PREVISION);
		ingresoVO.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
		ingresoVO.setFechaestado(DateUtils.getFechaActual());
		ingresoVO.setReservadeposito(ReservaPrevision.NO_RESERVADA
				.getIdentificador());
		ingresoVO.setIdusrgestorrem(idUsrGestor);
		ingresoVO.setIdusrgestorarchivorec(idUsrGestor);
		ingresoVO.setIdorganoremitente(idGestorEnOrganoRemitente);
		ingresoVO.setIdarchivoremitente(idArchivoRemitente);
		ingresoVO.setUiendeposito(false);
		ingresoVO.setDevolucionui(false);
		ingresoVO.setAno(DateUtils.getAnoActualStr());
		ingresoVO.setObservaciones(observaciones);
		ingresoVO.setIddescrorgproductor(idDescrProductorIngreso);
		ingresoVO.setIdNivelDocumental(idNivelDocumental);
		ingresoVO.setIdFicha(idFicha);

		return ingresoVO;
	}

	private RelacionEntregaVO nuevaRelacionEntreArchivos(PrevisionVO prevision,
			DetallePrevisionVO detallePrevision, String idSeriedestino,
			String idGestorEnOrganoRemitente, String idFormato,
			String observaciones, String idDescrProductorRelacion,
			String idFondoOrigen, String idNivelDocumental) {
		RelacionEntregaVO relacionVO = new RelacionEntregaVO();

		relacionVO.setTipotransferencia(prevision.getTipotransferencia());
		relacionVO.setIdprevision(prevision.getId());
		relacionVO.setTipoprevision(prevision.getTipoprevision());
		relacionVO.setIddetprevision(detallePrevision.getId());
		relacionVO.setIdorganoremitente(prevision.getIdorgremitente());
		relacionVO.setIdarchivoremitente(prevision.getIdarchivoremitente());
		relacionVO.setAno(Integer.toString(DateUtils.getAnoActual()));
		relacionVO.setIdformatoui(idFormato);
		relacionVO.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
		relacionVO.setFechaestado(DateUtils.getFechaActual());
		relacionVO.setIdarchivoreceptor(prevision.getIdarchivoreceptor());
		relacionVO.setIdusrgestorrem(idGestorEnOrganoRemitente);
		relacionVO.setUiendeposito(false);
		relacionVO.setDevolucionui(false);
		relacionVO.setObservaciones(observaciones);
		relacionVO.setIdseriedestino(detallePrevision.getIdSerieDestino());
		relacionVO.setIdfondodestino(prevision.getIdfondodestino());
		relacionVO.setIdserieorigen(detallePrevision.getIdSerieOrigen());
		relacionVO.setIdfondoorigen(idFondoOrigen);
		relacionVO.setIddescrorgproductor(idDescrProductorRelacion);
		relacionVO.setCodsistproductor(detallePrevision.getCodSistProductor());
		relacionVO.setNombresistproductor(detallePrevision
				.getNombreSistProductor());
		relacionVO.setIdprocedimiento(detallePrevision.getIdProcedimiento());
		relacionVO.setIdNivelDocumental(idNivelDocumental);
		return relacionVO;
	}

	/**
	 * Verifica que el usuario del servicio dispone de permiso para la
	 * elaboración de transferencias extraordinarias
	 *
	 * @throws SecurityException
	 *             Cuando el cliente del servicio no dispone de los permisos
	 */
	private void verificarPermisosTransferenciasExtraordinarias()
			throws SecurityException {
		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient
				.hasPermission(AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS)
				&& !serviceClient
						.hasPermission(AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS))
			throw new SecurityException(
					"El usuario no dispone de permisos para la elaboración de transferencias extraordinarias");
	}

	/**
	 * Verifica que el usuario del servicio dispone de permiso para la
	 * elaboración de ingresos directos
	 *
	 * @throws SecurityException
	 *             Cuando el cliente del servicio no dispone de los permisos
	 */
	private void verificarPermisosElaboracionIngresoDirecto()
			throws SecurityException {
		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient
				.hasPermission(AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES))
			throw new SecurityException(
					Messages.getString(
							TransferenciasConstants.ERROR_FONDOS_NO_PERMISOS_INGRESO_DIRECTO,
							serviceClient.getLocale()));
	}

	/**
	 * Verifica que el usuario del servicio dispone de permiso para la
	 * elaboración de transferencias entre archivos
	 *
	 * @throws SecurityException
	 *             Cuando el cliente del servicio no dispone de los permisos
	 */
	private void verificarPermisosTransferenciasEntreArchivos()
			throws SecurityException {
		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient
				.hasPermission(AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS))
			throw new SecurityException(
					"El usuario no dispone de permisos para la elaboración de transferencias entre archivos");
	}

	private void verificarPermisosGeneralesTransferencia(
			RelacionEntregaVO relacionVO) throws SecurityException {
		if (relacionVO != null) {
			if (relacionVO.isOrdinaria()) {

			} else if (relacionVO.isEntreArchivos()) {
				verificarPermisosTransferenciasEntreArchivos();
			} else {
				verificarPermisosTransferenciasExtraordinarias();
			}
		}
	}

	private void verificarPermisosGeneralesTransferencia(PrevisionVO previsionVO)
			throws SecurityException {
		if (previsionVO != null) {
			if (previsionVO.isOrdinaria()) {

			} else if (previsionVO.isEntreArchivos()) {
				verificarPermisosTransferenciasEntreArchivos();
			} else {
				verificarPermisosTransferenciasExtraordinarias();
			}
		}
	}

	/**
	 * Crea una nueva relacion de entrega en el sistema
	 *
	 * @param idPrevision
	 *            Identificador de al prevision sobre la que se hace la relacion
	 * @param idDetallePrevision
	 *            Detalle de prevision sobre el que se hace la relacion. En caso
	 *            de tratarse de una prevision no detallada sera null
	 * @param idSerie
	 *            Serie a la que iran a parar las unidades documentales que se
	 *            incluyan en la relacion de entrega. Su valor solo se tendra en
	 *            consideracion en caso de que no se especifique detalle de
	 *            prevision
	 * @param idFormato
	 *            Identificador del formato de la relacion
	 * @param formaDocumental
	 *            Forma documental de las unidades documentales de la relación
	 * @param observaciones
	 *            Observaciones que se quieran adjuntar a la relacion a crear
	 * @param idDescriptorProductorRelacion
	 *            Id del productor por defecto de la relacion
	 * @return La relacion de entrega creada {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             En caso de que la creacion de una relacion con la informacion
	 *             suministrada no este permitida para el usuario que lo
	 *             solicita
	 */
	public RelacionEntregaVO insertRelacion(String idPrevision,
			String idDetallePrevision, String idSerie, String idFormato,
			String formaDocumental, String observaciones,
			String idDescriptorProductorRelacion, String idFondoOrigen,
			String idNivelDocumental, String idFicha)
			throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						TipoTransferencia.ORDINARIA.getIdentificador(),
						ArchivoActions.TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA,
						this);
		PrevisionVO previsionVO = _previsionDbEntity
				.getInfoPrevision(idPrevision);

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);

		verificarPermisosGeneralesTransferencia(previsionVO);

		// if (previsionVO.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA
		// && previsionVO.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ENTRE_ARCHIVOS)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		// comprobar q prevision este aceptada

		if (previsionVO.getEstado() == EstadoPrevision.ACEPTADA
				.getIdentificador()) {
			if (!previsionVO.isCaducada()) {
				DetallePrevisionVO detalle = null;
				// comprobaciones en previsiones detalladas
				if (previsionVO.isDetallada()) {
					// comprobar q el detalle no este cerrado
					detalle = _detallePrevisionDBEntity
							.selectRow(idDetallePrevision);
					if (detalle.isCerrada()) {
						throw new ActionNotAllowedException(
								"No se puede crear la relacion: el detalle de prevision esta cerrado",
								ArchivoErrorCodes.DETALLE_PREVISION_CERRADO,
								ArchivoModules.TRANSFERENCIAS_MODULE);
					} else
					// si transferencia ordinaria el detalle no puede existir en
					// otra relacion con el mismo formato
					if (previsionVO.getTipotransferencia() == TipoTransferencia.ORDINARIA
							.getIdentificador()) {
						if (_relacionEntregaDBEntity
								.detallePrevisionTieneRelaciones(
										idDetallePrevision, formaDocumental)) {
							throw new ActionNotAllowedException(
									"No se puede crear la relacion: ya existe una relacion con igual forma documental sobre el mismo detalle",
									ArchivoErrorCodes.RELACION_DUPLICADA,
									ArchivoModules.TRANSFERENCIAS_MODULE);
						}
					}

				}

				// comprobacion de formato vigente
				FormatoHuecoVO formatoVO = _formatoDBEntity
						.loadFormato(idFormato);
				if (formatoVO.isVigente()) {
					iniciarTransaccion();

					RelacionEntregaVO vo;
					if (previsionVO.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
						vo = nuevaRelacionEntreArchivos(previsionVO, detalle,
								idSerie, getServiceClient().getId(), idFormato,
								observaciones, idDescriptorProductorRelacion,
								idFondoOrigen, idNivelDocumental);

					} else {
						vo = nuevaRelacion(previsionVO, detalle, idSerie,
								getServiceClient().getId(), idFormato,
								observaciones, idDescriptorProductorRelacion,
								idNivelDocumental, idFicha);

					}

					int numSecuencia = _nSecDBEntity
							.incrementarNumeroSecRelacion(vo.getAno(),
									vo.getIdarchivoreceptor());
					vo.setOrden(numSecuencia);
					vo.setReservadeposito(ReservaPrevision.NO_RESERVADA
							.getIdentificador());
					vo.setTipoDocumental(formaDocumental);

					if (previsionVO.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()) {
						// Comprobar que el usuario no tiene otra relación con
						// el mismo formato y serie.
						if (existeRelacionAbiertaXUsuarioYTipoYArchivo(vo)) {
							throw new ActionNotAllowedException(
									"No se puede crear la relacion: el usuario ya tiene una relación abierta con el mismo formato, serie y archivo receptor",
									ArchivoErrorCodes.RELACION_DUPLICADA,
									ArchivoModules.TRANSFERENCIAS_MODULE);
						}
					} else {
						// Comprobar que el usuario no tiene otra relación con
						// el mismo formato, serie y nivel documental.
						if (existeRelacionAbiertaXUsuario(vo)) {
							throw new ActionNotAllowedException(
									"No se puede crear la relacion: el usuario ya tiene una relación abierta con el mismo formato y serie",
									ArchivoErrorCodes.RELACION_DUPLICADA,
									ArchivoModules.TRANSFERENCIAS_MODULE);
						}
					}

					_relacionEntregaDBEntity.insertRelacion(vo);

					if (previsionVO.isDetallada()) {
						_detallePrevisionDBEntity
								.incNRelacionesAsociadasADetalle(vo
										.getIddetprevision());
					}
					commit();
					pistaAuditoria.auditaCreacionRelacion(locale, vo);
					return vo;
				} else
					throw new ActionNotAllowedException(
							"No se puede crear la relacion: formato no vigente",
							ArchivoErrorCodes.FORMATO_NO_VIGENTE,
							ArchivoModules.TRANSFERENCIAS_MODULE);

			} else
				throw new ActionNotAllowedException(
						"No se puede crear la relacion: prevision caducada",
						ArchivoErrorCodes.PREVISION_CADUCADA,
						ArchivoModules.TRANSFERENCIAS_MODULE);
		} else
			throw new ActionNotAllowedException(
					"No se puede crear la relacion: prevision no aceptada",
					ArchivoErrorCodes.PREVISION_NO_ACEPTADA,
					ArchivoModules.TRANSFERENCIAS_MODULE);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#insertIngresoDirecto(java.lang.String
	 * , java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public RelacionEntregaVO insertIngresoDirecto(String idArchivo,
			String idFondo, String idSerie, String idFormato,
			String formaDocumental, String observaciones,
			String idDescrProductor, String idNivelDocumental, String idFicha,
			String idRevDoc) throws ActionNotAllowedException {

		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						TipoTransferencia.INGRESO_DIRECTO.getIdentificador(),
						ArchivoActions.FONDOS_MODULE_ALTA_INGRESO_DIRECTO, this);
		Locale locale = getServiceClient().getLocale();

		verificarPermisosElaboracionIngresoDirecto();

		// comprobacion de formato vigente
		FormatoHuecoVO formatoVO = _formatoDBEntity.loadFormato(idFormato);
		if (formatoVO.isVigente()) {
			iniciarTransaccion();

			// Obtener el órgano del usuario
			CAOrganoVO organoVO = getServiceClient().getOrganization();

			if (organoVO != null) {

				RelacionEntregaVO vo = nuevoIngresoDirecto(idArchivo, idFondo,
						idSerie, idFormato, formaDocumental, observaciones,
						getServiceClient().getId(), idDescrProductor,
						organoVO.getIdOrg(), organoVO.getIdArchivoReceptor(),
						idNivelDocumental, idFicha);

				// Obtener el año actual
				String anoActual = DateUtils.getAnoActualStr();

				int numSecuencia = _nSecDBEntity
						.incrementarNumeroSecIngresoDirecto(anoActual,
								idArchivo);
				vo.setOrden(numSecuencia);

				_relacionEntregaDBEntity.insertRelacion(vo);

				if (StringUtils.isNotEmpty(idRevDoc)) {
					_revisionDocumentacionDBEntity.updateIdAlta(idRevDoc,
							vo.getId());
				}

				commit();
				pistaAuditoria.auditaCreacionRelacion(locale, vo);
				return vo;
			} else {
				throw new ActionNotAllowedException(
						Messages.getString(
								"archigest.archivo.fondos.ingreso.directo.usuario.sin.organo",
								locale), ArchivoErrorCodes.USUARIO_SIN_ORGANO,
						ArchivoModules.TRANSFERENCIAS_MODULE);
			}
		} else
			throw new ActionNotAllowedException(
					Messages.getString(
							"archigest.archivo.fondos.ingreso.directo.formato.no.vigente",
							locale), ArchivoErrorCodes.FORMATO_NO_VIGENTE,
					ArchivoModules.TRANSFERENCIAS_MODULE);

	}

	public void transfControlRelacionesGArchivo(String[] relacionIDs,
			String idNewUser) {
		_relacionEntregaDBEntity.updateUsrGestorArchivo(relacionIDs, idNewUser);
	}

	public Collection getRelacionesXIds(String[] codigos) {
		return _relacionEntregaDBEntity.getRelacionesXIds(codigos);
	}

	public Collection getRelacionesXUsrRemYEstados(String idusrgestorrem,
			int[] estados) {
		return _relacionEntregaDBEntity.getRelacionesXGestorEnOrganoRemitente(
				idusrgestorrem, estados, null, true);
	}

	public void transfControlRelacionesGOficina(String[] idsRelaciones,
			String idNewUserRem) throws ActionNotAllowedException {
		iniciarTransaccion();

		_relacionEntregaDBEntity
				.updateUsrGestorRem(idsRelaciones, idNewUserRem);
		commit();
	}

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion) {
		return _unidadDocumentalDBEntity
				.fetchRowsByCodigoRelacionOrderByOrden(codigoRelacion);
	}

	public List getUnidadesDocumentales(String codigoRelacion) {
		return _unidadDocumentalDBEntity
				.fetchRowsByCodigoRelacionOrderByOrden(codigoRelacion);
	}

	public List getUnidadesDocumentales(String[] idsUDocs) {
		return _unidadDocumentalDBEntity.fetchRowsByIds(idsUDocs);
	}

	public UnidadDocumentalVO getUnidadDocumentalByRelacionAndId(
			String idRelacion, String idUnidad){
		return _unidadDocumentalDBEntity.getUnidadDocumentalByRelacionAndId(idRelacion, idUnidad);
	}

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 * que incluyen documentos fisicos
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 * @throws TooManyResultsException
	 */
	public List getUdocsConDocumentosFisicos(String idRelacion,
			PageInfo pageInfo) throws TooManyResultsException {
		List udocsRelacion = null;
		if (pageInfo != null)
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion, pageInfo);
		else
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion);
		CollectionUtils.filter(udocsRelacion, new Predicate() {
			public boolean evaluate(Object obj) {
				return ((UnidadDocumentalVO) obj).tieneDocumentosFisicos();
			}
		});
		return udocsRelacion;
	}

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 * @throws TooManyResultsException
	 */
	public List getUdocs(String idRelacion, PageInfo pageInfo,
			boolean tieneDescripcion) throws TooManyResultsException {
		List udocsRelacion = null;
		if (pageInfo != null)
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion, pageInfo);
		else
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion);

		if (tieneDescripcion) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();

			// Obtener los ids de los elementos
			Iterator it = udocsRelacion.iterator();
			String[] idsElementos = new String[udocsRelacion.size()];
			int i = 0;
			while (it.hasNext()) {
				UnidadDocumentalVO udocREVO = (UnidadDocumentalVO) it.next();
				idsElementos[i] = udocREVO.getId();
				i++;
			}

			if (!ListUtils.isEmpty(udocsRelacion)) {
				Map interesados = descripcionBI.getInteresadoPrincipal(
						idsElementos,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE);

				it = udocsRelacion.iterator();

				while (it.hasNext()) {
					UnidadDocumentalVO udocREVO = (UnidadDocumentalVO) it
							.next();
					// Revisar si en la descripción de unidad documental se ha
					// establecido o modificado el interesado principal y
					// actualizarla
					List ltInteresados = (List) interesados.get(udocREVO
							.getId());
					if (!ListUtils.isEmpty(ltInteresados)) {
						InteresadoVO interesado = (InteresadoVO) ltInteresados
								.get(0);
						if (udocREVO.getExtraInfo() != null)
							udocREVO.getExtraInfo().setInteresadoPrincipal(
									interesado);
					}
				}
			}
		}

		return udocsRelacion;
	}

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 * que incluyen solo documentos electronicos
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 * @throws TooManyResultsException
	 */
	public List getUdocsConDocumentosElectronicos(String idRelacion,
			PageInfo pageInfo) throws TooManyResultsException {
		List udocsRelacion = null;
		if (pageInfo != null)
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion, pageInfo);
		else
			udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idRelacion);
		CollectionUtils.filter(udocsRelacion, new Predicate() {
			public boolean evaluate(Object obj) {
				return (!((UnidadDocumentalVO) obj).tieneDocumentosFisicos());
			}
		});
		return udocsRelacion;
	}

	boolean expedienteEnRelacion(String idRelacion, String numeroExpediente) {
		List udocs = _unidadDocumentalDBEntity.fetchRowsByNumExpediente(
				numeroExpediente, idRelacion);
		return (udocs != null) && (udocs.size() > 0);
	}

	public void nuevaUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signatura, int subtipo,
			int incrementoOrden) throws ActionNotAllowedException {

		nuevaUnidadDocumental(relacionEntrega, udoc, signatura, subtipo,
				incrementoOrden, false);

	}

	public void nuevaUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signatura, int subtipo,
			int incrementoOrden, boolean copiarUdocsRelacionadas)
			throws ActionNotAllowedException {

		ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionEntrega
				.getIdarchivoreceptor());

		String signaturaFormateada = signatura;

		if (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.getIdentificador() != archivoVO
				.getTiposignaturacion()) {

			if (StringUtils.isNumeric(signaturaFormateada))
				signaturaFormateada = SignaturaUtil
						.formatearSignaturaNumerica(Long
								.valueOf(signaturaFormateada));
		}

		nuevaUnidadDocumentalConSignatura(relacionEntrega, udoc,
				signaturaFormateada, subtipo, incrementoOrden,
				copiarUdocsRelacionadas);
	}

	/**
	 * Incorpora una unidad documental a una relacion de entrega
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega a la que se quiere incorporar la unidad
	 *            documental
	 * @param udoc
	 *            Datos de unidad documental
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporacion de unidades documentales a la
	 *             relación de entrega no esté permitida
	 */
	private void nuevaUnidadDocumentalConSignatura(
			RelacionEntregaVO relacionEntrega, UnidadDocumentalVO udoc,
			String signatura, int subtipo, int incrementoOrden,
			boolean copiarUdocsRelacionadas) throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionEntrega, action, this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionEntrega,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_INCORPORACION_UNIDAD_DOCUMENTAL);

		if (relacionEntrega.getIsIngresoDirecto())
			checkPermission(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS);
		else {
			checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
			verificarPermisosGeneralesTransferencia(relacionEntrega);
		}

		// if ((relacionEntrega.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// && (!relacionEntrega.getIsIngresoDirecto()))
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);

		if (authorizationHelper.puedeSerModificada(relacionEntrega)) {

			iniciarTransaccion();
			if (relacionEntrega.isRechazada()) {
				relacionEntrega.setEstado(EstadoREntrega.ABIERTA
						.getIdentificador());
				updateRelacion(relacionEntrega);
			}

			udoc.setIdRelEntrega(relacionEntrega.getId());
			udoc.setTipoTransferencia(relacionEntrega.getTipotransferencia());

			if (((ListUtils.isEmpty(udoc.getDocumentosFisicos())) && (!ListUtils
					.isEmpty(udoc.getDocumentosElectronicos())))
					|| (relacionEntrega.isAutomatizada() && !udoc
							.getTieneDocumentosFisicos()))
				udoc.setTieneDocumentosFisicos(false);
			else
				udoc.setTieneDocumentosFisicos(true);

			if (incrementoOrden != 0)
				_unidadDocumentalDBEntity.incrementOrdenUdoc(
						udoc.getIdRelEntrega(), udoc.getOrden(),
						incrementoOrden);

			udoc.setId(null);
			_unidadDocumentalDBEntity.insertRow(udoc);

			if (!udoc.getTieneDocumentosFisicos()) {
				UDocElectronicaVO udocElectronicaVO;
				udocElectronicaVO = new UDocElectronicaVO(udoc.getId(),
						udoc.getIdRelEntrega(), udoc.getOrden(),
						EstadoCotejo.PENDIENTE.getIdentificador());
				_udocElectronicaDbEntity.insertRow(udocElectronicaVO);
			}

			FormatoHuecoVO formatoRelacion = _formatoDBEntity
					.loadFormato(relacionEntrega.getIdformatoui());

			// La caja solo se crea si hay documentos físicos.
			if (!Constants.TRUE_STRING.equals(udoc.getSinDocsFisicos())) {
				if (!formatoRelacion.isMultidoc()
						|| subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
					if (relacionEntrega.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
							.getIdentificador()) {

						ArchivoVO archivoVO = _archivoDbEntity
								.getArchivoXId(relacionEntrega
										.getIdarchivoreceptor());
						String idUbicacion = relacionEntrega.getIddeposito();

						if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
								.getIdentificador()) {
							// Comprobar que el hueco asociado a la signatura
							// esté libre para el archivo indicado
							HuecoVO huecoVO = SignaturaUtil
									.getHuecoAsociadoASignatura(signatura,
											relacionEntrega
													.getIdarchivoreceptor(),
											_huecoDBEntity);
							if (huecoVO == null)
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO);
							if ((huecoVO != null)
									&& (!HuecoVO.LIBRE_STATE.equals(huecoVO
											.getEstado()))) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO);
							}
							if ((huecoVO != null)
									&& (!huecoVO.getIdformato().equals(
											relacionEntrega.getIdformatoui()))) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION);
							}
							if ((huecoVO != null)
									&& (StringUtils.isNotEmpty(idUbicacion))
									&& (!idUbicacion.equals(huecoVO
											.getIddeposito()))) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);
							}
							if (isSignaturaAsociadaHuecoUtilizadaRelacion(
									relacionEntrega.getId(), null, signatura)) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION);
							}

							// Actualizar el identificador de ubicacion
							_relacionEntregaDBEntity.updateUbicacion(
									relacionEntrega.getId(), false,
									huecoVO.getIddeposito());

						} else {
							// Comprobar que la signatura no está repetida
							if (SignaturaUtil.existeSignatura(signatura,
									relacionEntrega.getIdarchivoreceptor(),
									_unidadInstalacionDBEntity,
									_unidadInstalacionReeaDBEntity,
									_uiReeaCRDBEntity, _huecoDBEntity,
									ConfigConstants.getInstance()
											.getSignaturacionPorArchivo())) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA);
							}
						}
					}

					int numeroCaja = 0;
					if (incrementoOrden != 0) {
						// Se está insertando alguna unidad documental por el
						// medio
						numeroCaja = udoc.getOrden();

						_unidadInstalacionDBEntity.incrementOrdenUi(
								udoc.getIdRelEntrega(), udoc.getOrden(),
								incrementoOrden);
					} else {
						// Modificado por error en la generación de cartelas, si
						// el orden es 1 siempre, no sirven de nada
						numeroCaja = _unidadInstalacionDBEntity
								.countUIsRelacion(relacionEntrega.getId()) + 1;
					}

					crearUnidadInstalacionParaUdoc(udoc,
							relacionEntrega.getIdformatoui(), signatura,
							numeroCaja, false);
				}
			}

			// Si es una relación de entrega con ficha, rellenar la información
			// descriptiva que tenemos hasta el momento
			if (StringUtils.isNotEmpty(relacionEntrega.getIdFicha())) {

				// Definición de la ficha
				DefFicha defFicha = DefFichaFactory.getInstance(
						getServiceClient()).getDefFichaById(
						relacionEntrega.getIdFicha());

				// Definición del formato de la ficha
				DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
						getServiceClient()).getDefFmtFicha(
						relacionEntrega.getIdFicha(), TipoAcceso.EDICION);

				// Cargar la clase de mapeo de campos de unidad documental en
				// relación de entrega a descripción
				MapDescripcionUdoc descripcionUdoc = new MapDescripcionUdoc(
						descripcionBI, relacionEntrega.getIdFicha());

				// Insertar la descripción de la unidad documental
				descripcionUdoc.generateDescripcionUdoc(udoc.asXML(defFicha),
						udoc.getId(), defFicha, defFmtFicha,
						relacionEntrega.getIdFicha(), TipoFicha.FICHA_UDOCRE);

				// Eliminar la información del productor y fechas extremas de la
				// información extra de la unidad documental que es la que se
				// guarda
				// en el campo INFO y marcar la unidad como descrita para que no
				// se vuelva a crear la ficha al volver a entrar
				udoc.getExtraInfo().cleanFechasExtremas();
				udoc.getExtraInfo().setTieneDescripcion(Constants.TRUE_STRING);

				_unidadDocumentalDBEntity.updateRow(udoc);

				if (copiarUdocsRelacionadas) {
					addUdocsRelacionadas(relacionEntrega.getId(), udoc.getId(),
							ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE,
							copiarUdocsRelacionadas, false);
				}
			}

			checkModificaRelacionRechazada(relacionEntrega);
			commit();

			pistaAuditoria
					.auditaNuevaUnidadDocumentalConSignatura(locale, udoc);

			authorizationHelper
					.configureUnidadDocumental(relacionEntrega, udoc);

		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}
	}

	public void modificarInformacionUnidadDocumental(
			RelacionEntregaVO relacionEntrega, UnidadDocumentalVO udoc)
			throws ActionNotAllowedException {
		modificarUnidadDocumental(relacionEntrega, udoc, null, null, false);
	}

	public void modificarUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signaturaUI, String signaturaUDoc)
			throws ActionNotAllowedException {
		FormatoHuecoVO formatoHueco = _formatoDBEntity
				.loadFormato(relacionEntrega.getIdformatoui());

		INivelCFVO nivelCF = _nivelDbEntity.getNivelCF(relacionEntrega
				.getIdNivelDocumental());

		boolean transferenciaConSignaturaManual = relacionEntrega
				.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador()
				&& (!formatoHueco.isMultidoc() || (nivelCF.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA));
		if (transferenciaConSignaturaManual) {

			ArchivoVO archivoVO = _archivoDbEntity
					.getArchivoXId(relacionEntrega.getIdarchivoreceptor());

			String signaturaFormateadaUdoc = signaturaUDoc;
			String signaturaFormateadaUI = signaturaUI;

			if (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
					.getIdentificador() != archivoVO.getTiposignaturacion()) {

				if (StringUtils.isNumeric(signaturaFormateadaUdoc))
					signaturaFormateadaUdoc = SignaturaUtil
							.formatearSignaturaNumerica(Long
									.valueOf(signaturaFormateadaUdoc));

				if (StringUtils.isNumeric(signaturaFormateadaUI))
					signaturaFormateadaUI = SignaturaUtil
							.formatearSignaturaNumerica(Long
									.valueOf(signaturaFormateadaUI));

			}

			modificarUnidadDocumental(relacionEntrega, udoc,
					signaturaFormateadaUI, signaturaFormateadaUdoc, true);
		} else
			modificarUnidadDocumental(relacionEntrega, udoc, null, null, false);
	}

	/**
	 *
	 * @param relacionEntrega
	 * @param udoc
	 * @param signaturaUI
	 *            : signatura de la Udoc ya formateada, se guardara en BD como
	 *            venga
	 * @param signaturaUDoc
	 *            : signatura de la Udoc ya formateada, se guardara en BD como
	 *            venga
	 * @param actualizarSignaturas
	 * @throws ActionNotAllowedException
	 */
	private void modificarUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signaturaUI, String signaturaUDoc,
			boolean actualizarSignaturas) throws ActionNotAllowedException {

		Locale locale = getServiceClient().getLocale();
		int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionEntrega, action, this);

		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionEntrega,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFICACION_UNIDAD_DOCUMENTAL);
		pistaAuditoria.auditaModificacionUnidadDocumental(locale, udoc);

		if (relacionEntrega.getIsIngresoDirecto())
			checkPermission(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS);
		else
			checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);
		if (authorizationHelper.puedeSerModificada(relacionEntrega, udoc)) {
			iniciarTransaccion();
			_unidadDocumentalDBEntity.updateRow(udoc);

			// en caso de Extraordinario signaturada
			if (actualizarSignaturas) {

				// obtengo la parte de la udoc signaturada (solo tendra una)
				List partesUdoc = _udocEnUIDBEntity.fetchRowsByUdoc(udoc
						.getId());
				if (partesUdoc != null) {

					IParteUnidadDocumentalVO unicaParte = (IParteUnidadDocumentalVO) partesUdoc
							.iterator().next();

					String idUbicacion = relacionEntrega.getIddeposito();

					List unidadesInstalacion = _unidadInstalacionDBEntity
							.fetchRowsByIdRelacion(relacionEntrega.getId());

					// validacion signatura no repetida
					UnidadInstalacionVO unidadInstalacionVO = _unidadInstalacionDBEntity
							.fetchRowById(unicaParte.getIdUinstalacionRe());
					if (!signaturaUI.equals(unidadInstalacionVO
							.getSignaturaUI())) {
						// Validar que la signatura no esté usada

						ArchivoVO archivoVO = _archivoDbEntity
								.getArchivoXId(relacionEntrega
										.getIdarchivoreceptor());

						if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
								.getIdentificador()) {
							// Comprobar que el hueco asociado a la signatura
							// esté libre para el archivo indicado
							HuecoVO huecoVO = SignaturaUtil
									.getHuecoAsociadoASignatura(signaturaUI,
											relacionEntrega
													.getIdarchivoreceptor(),
											_huecoDBEntity);
							if (huecoVO == null)
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO);
							if ((huecoVO != null)
									&& (!HuecoVO.LIBRE_STATE.equals(huecoVO
											.getEstado()))) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO);
							}
							if ((huecoVO != null)
									&& (!huecoVO.getIdformato().equals(
											relacionEntrega.getIdformatoui()))) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION);
							}
							if (isSignaturaAsociadaHuecoUtilizadaRelacion(
									relacionEntrega.getId(),
									unidadInstalacionVO.getId(), signaturaUI)) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION);
							}
							if ((unidadesInstalacion != null)
									&& (unidadesInstalacion.size() > 1)) {
								if ((huecoVO != null)
										&& (StringUtils.isNotEmpty(idUbicacion))
										&& (!idUbicacion.equals(huecoVO
												.getIddeposito()))) {
									throw new RelacionOperacionNoPermitidaException(
											RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);
								}
							}

							_relacionEntregaDBEntity.updateUbicacion(
									relacionEntrega.getId(), false,
									huecoVO.getIddeposito());
						} else {
							// Comprobar que la signatura no está repetida
							if (SignaturaUtil.existeSignatura(signaturaUI,
									relacionEntrega.getIdarchivoreceptor(),
									_unidadInstalacionDBEntity,
									_unidadInstalacionReeaDBEntity,
									_uiReeaCRDBEntity, _huecoDBEntity,
									ConfigConstants.getInstance()
											.getSignaturacionPorArchivo())) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA);
							}
						}
					}

					_udocEnUIDBEntity.updateEstadoYSignatura(
							unicaParte.getIdUnidadDoc(),
							String.valueOf(unicaParte.getNumParteUdoc()),
							unicaParte.getEstadoCotejo(), signaturaUDoc);

					// cada udoc tiene una caja asignada
					UnidadInstalacionVO uinstalacionDeUdoc = _unidadInstalacionDBEntity
							.fetchRowById(unicaParte.getIdUinstalacionRe());

					// actualizar la caja
					_unidadInstalacionDBEntity.updateFieldSignatura(
							uinstalacionDeUdoc.getId(), signaturaUI);
				}
			}

			CotejoHelper
					.actualizarEstadoCotejoUdoc(relacionEntrega, udoc.getId(),
							_udocEnUIDBEntity, _unidadInstalacionDBEntity);
			checkModificaRelacionRechazada(relacionEntrega);
			commit();

		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}
	}

	/**
	 * Finaliza el proceso de correcion de errores de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @throws ActionNotAllowedException
	 *             Cuando la finalizacion de correccion de errores no esta
	 *             permitida para la relacion de entrega indicada
	 */
	public void finalizarCorreccionErrores(String idRelacion)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA,
						this);

		RelacionEntregaVO relacionActual = getRelacionXIdRelacion(idRelacion);
		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.addDetalleBasico(locale, relacionActual);

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionActual);
		if (authorizationHelper.permitidaFinalizacionCorreccion(relacionActual)) {
			// si la relacion es signaturada controlar que todas las cajas
			// tiene signatura(puede haberse creado una caja si signatura al
			// partir la udoc)
			if (relacionActual.isConSignatura()) {
				List unidadesInstalacion = getUnidadesInstalacion(relacionActual
						.getId());
				if (unidadesInstalacion != null) {
					for (Iterator itUnidadesInstalacion = unidadesInstalacion
							.iterator(); itUnidadesInstalacion.hasNext();) {
						UnidadInstalacionVO aCaja = (UnidadInstalacionVO) itUnidadesInstalacion
								.next();
						if (aCaja.getSignaturaUI() == null)
							throw new RelacionOperacionNoPermitidaException(
									RelacionOperacionNoPermitidaException.XLA_RELACION_SIGNATURADA_PARA_FINALIZAR_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS);
					}
				}
			}

			// comprobar que si es ordinaria no se puede finalizar con errores
			// en cajas sin devolver
			iniciarTransaccion();
			ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionActual
					.getIdarchivoreceptor());
			_unidadInstalacionDBEntity.setErroresCorregidos(idRelacion);
			_relacionEntregaDBEntity.updateEstado(idRelacion,
					EstadoREntrega.CORREGIDA_ERRORES.getIdentificador(),
					DateUtils.getFechaActual(), Boolean.FALSE);
			List unidadesInstalacionVacias = _unidadInstalacionDBEntity
					.getUnidadesInstalacionVacias(idRelacion);
			UnidadInstalacionVO unaUI = null;
			for (Iterator i = unidadesInstalacionVacias.iterator(); i.hasNext();) {
				unaUI = (UnidadInstalacionVO) i.next();
				// Si estamos en signaturacion asociada a hueco debemos liberar
				// la signatura
				if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
						.getIdentificador() && relacionActual.isConSignatura()) {
					HuecoVO huecoVO = _huecoDBEntity
							.getHuecoAsociadoNumeracion(
									relacionActual.getIdarchivoreceptor(),
									unaUI.getSignaturaUI());
					_huecoDBEntity
							.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
									new HuecoID(huecoVO.getIdElemAPadre(),
											huecoVO.getNumorden().intValue()),
									HuecoVO.LIBRE_STATE, null, null, unaUI
											.getId(),
									new Integer(unaUI.getOrden()), false);
				}
				_unidadInstalacionDBEntity.dropRow(unaUI);
			}

			List unidadesInstalacion = getUnidadesInstalacion(idRelacion);
			if (!archivoVO.isSignaturaAsociadaAHueco()) {
				long signaturaNumerica = -1;
				CollectionUtils.filter(unidadesInstalacion, new Predicate() {
					public boolean evaluate(Object obj) {
						return ((IUnidadInstalacionVO) obj).getSignaturaUI() == null;
					}
				});

				for (Iterator i = unidadesInstalacion.iterator(); i.hasNext();) {
					IUnidadInstalacionVO ui = (IUnidadInstalacionVO) i.next();
					signaturaNumerica = SignaturaUtil.obtenerSignaturaNumerica(
							relacionActual.getIdarchivoreceptor(),
							_nSecUIDBEntity, _unidadInstalacionDBEntity,
							_unidadInstalacionReeaDBEntity, _uiReeaCRDBEntity,
							_huecoDBEntity, _udocElectronicaDbEntity);

					String signatura = SignaturaUtil
							.formatearSignaturaNumerica(signaturaNumerica);
					if (relacionActual.isRelacionConReencajado()) {
						_uiReeaCRDBEntity
								.updateSignatura(ui.getId(), signatura);
						getGestionRelacionEACRBI()
								.updateSignaturaUdocsByIdUinstalacion(
										ui.getId());
					} else {
						_unidadInstalacionDBEntity.updateFieldSignatura(
								ui.getId(), signatura);
					}
				}
			} else {

				if (relacionActual.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
						.getIdentificador()) {
					reservarHuecosAsociadosRelacion(relacionActual.getId(),
							true);
				}
			}

			// Actualizar el indicador de Corregir en archivo.
			if (Constants.TRUE_STRING.equals(relacionActual
					.getCorreccionenarchivo())) {
				_relacionEntregaDBEntity.updateIndicadorCorreccionEnArchivo(
						relacionActual.getId(), Constants.FALSE_STRING);
			}
			commit();
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	public List fetchRowsByIdRelacion(String idRelacion, int idTipoTransferencia) {
		if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == idTipoTransferencia)
			return _unidadInstalacionReeaDBEntity
					.fetchRowsByIdRelacion(idRelacion);
		else
			return fetchRowsByIdRelacion(idRelacion, idTipoTransferencia,
					TipoUInstalacion.ALL.getIdentificador());
	}

	public List fetchRowsByIdRelacion(String idRelacion,
			int idTipoTransferencia, int tipoUInstalacion) {
		return _unidadInstalacionDBEntity.fetchRowsByIdRelacion(idRelacion,
				tipoUInstalacion);
	}

	/**
	 * Recepcion de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega a recibir
	 * @param idGestor
	 *            Identificador del usuario que es asignado como gestor de la
	 *            relación
	 * @param idFormato
	 *            Identificador del formato de las unidades de instalación en
	 *            las que viene organizada la documentación de la relación
	 * @param tipoDocumental
	 *            Tipo documental de las unidades documentales incluidas en la
	 *            relación
	 * @param idUbicacion
	 *            Ubicación del depósito físico que se asigna como destino donde
	 *            previsiblemente se realizará la ubicación de la relación
	 * @param idNoAsignableReserva
	 *            Identificador del elemento del depósito físico donde se
	 *            solicita reserva de espacio para ubicar la relación
	 * @throws ActionNotAllowedException
	 *             Caso de que la recepción de la relación de entrega no esté
	 *             permitida
	 */
	public void recibirRelacionEntrega(String idRelacion, String idGestor,
			String idFormato, String tipoDocumental, String idUbicacion,
			String idDepositoReserva, String idTipoDepositoReserva,
			String idElementoSeleccionadoReserva,
			String idTipoElementoSeleccionadoReserva)
			throws ActionNotAllowedException {
		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA,
						this);
		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		pistaAuditoria.addDetalleBasico(locale, relacionEntrega);
		checkPermission(TransferenciasSecurityManager.RECIBIR_RELACION);
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);

		boolean isAsignable = false;

		boolean isSinDocsFisicos = relacionEntrega.soloDocumentosElectronicos();

		if (authorizationHelper.puedeSerRecibida(relacionEntrega)) {
			List cajasDeRelacion = null;
			if (!isSinDocsFisicos) {
				cajasDeRelacion = getUnidadesInstalacion(idRelacion,
						relacionEntrega.getTipotransferencia());
			}

			int numUDocsElectronicas = _udocElectronicaDbEntity
					.countUdocsElectronicasByRelacion(relacionEntrega.getId());

			// Comprobar que existen cajas o Unidades Docuentales Electrónicas.
			if (!ListUtils.isEmpty(cajasDeRelacion) || numUDocsElectronicas > 0) {
				iniciarTransaccion();
				ServiceRepository services = ServiceRepository
						.getInstance(getServiceSession());
				GestorEstructuraDepositoBI serviceDeposito = services
						.lookupGestorEstructuraDepositoBI();

				if (!isSinDocsFisicos && idDepositoReserva != null) {
					try {

						ElementoNoAsignableVO elementoNoAsignableVO = serviceDeposito
								.getElementoNoAsignable(idElementoSeleccionadoReserva);

						if (elementoNoAsignableVO == null) {
							ElementoAsignableVO elementoAsignableVO = serviceDeposito
									.getElementoAsignable(idElementoSeleccionadoReserva);
							if (elementoAsignableVO != null) {
								isAsignable = true;
							}
						}

						if (idElementoSeleccionadoReserva
								.equals(idDepositoReserva)
								&& idTipoElementoSeleccionadoReserva
										.equals(idTipoDepositoReserva)) {

							serviceDeposito.searchNHuecosLibres(
									idElementoSeleccionadoReserva,
									idTipoElementoSeleccionadoReserva,
									cajasDeRelacion.size(), null, null,
									idFormato);
						}
					} catch (Exception e) {
						throw new ArchivoModelException(
								getClass(),
								"recibirRelacionEntregaConReservaEnNoAsignable",
								e.getMessage());
					}
				}

				// generar entrada para reg el registro
				String anoActual = Integer.toString(DateUtils.getAnoActual());
				int nsecreg = _nSecDBEntity.incrementarNumeroSecRegistro(
						anoActual, relacionEntrega.getIdarchivoreceptor());
				long signaturaNumerica = -1;
				boolean auditarSignatura = false;

				DepositoVO ubicacion = new DepositoVO();

				// Obtener el archivo
				ArchivoVO archivoReceptor = _archivoDbEntity
						.getArchivoXId(relacionEntrega.getIdarchivoreceptor());

				// Si la signaturación es asociada a hueco no asignar ninguna
				// signatura
				if (!archivoReceptor.isSignaturaAsociadaAHueco()) {
					if (!isSinDocsFisicos && !relacionEntrega.isConSignatura()) {
						for (Iterator i = cajasDeRelacion.iterator(); i
								.hasNext();) {
							String id = ((IUnidadInstalacionVO) i.next())
									.getId();
							if (!relacionEntrega.isEntreArchivos()) {
								signaturaNumerica = SignaturaUtil
										.obtenerSignaturaNumerica(
												relacionEntrega
														.getIdarchivoreceptor(),
												_nSecUIDBEntity,
												_unidadInstalacionDBEntity,
												_unidadInstalacionReeaDBEntity,
												_uiReeaCRDBEntity,
												_huecoDBEntity,
												_udocElectronicaDbEntity);

								String signatura = SignaturaUtil
										.formatearSignaturaNumerica(signaturaNumerica);
								_unidadInstalacionDBEntity
										.updateFieldSignatura(id, signatura);
							} else {
								if (ConfigConstants.getInstance()
										.getSignaturacionPorArchivo()
										|| relacionEntrega
												.isRelacionConReencajado()) {

									signaturaNumerica = SignaturaUtil
											.obtenerSignaturaNumerica(
													relacionEntrega
															.getIdarchivoreceptor(),
													_nSecUIDBEntity,
													_unidadInstalacionDBEntity,
													_unidadInstalacionReeaDBEntity,
													_uiReeaCRDBEntity,
													_huecoDBEntity,
													_udocElectronicaDbEntity);
									String signatura = SignaturaUtil
											.formatearSignaturaNumerica(signaturaNumerica);

									if (relacionEntrega
											.isRelacionConReencajado()) {
										_uiReeaCRDBEntity.updateSignatura(id,
												signatura);
										getGestionRelacionEACRBI()
												.updateSignaturaUdocsByIdUinstalacion(
														id);
									} else {
										_unidadInstalacionReeaDBEntity
												.updateFieldSignatura(id,
														signatura);
									}
								}
							}
						}

						auditarSignatura = true;
					}
				} else {

					if (relacionEntrega.getTipotransferencia() == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
							.getIdentificador()) {
						// Reservar los huecos asociados a la relación
						reservarHuecosAsociadosRelacion(idRelacion, false);
					}

					if (StringUtils.isNotEmpty(relacionEntrega.getIddeposito()))
						idUbicacion = relacionEntrega.getIddeposito();
				}

				// actualizar relacion de entrega
				String regEntrada = anoActual + "_" + Integer.toString(nsecreg);
				int estadoReserva = ReservaPrevision.NO_RESERVADA
						.getIdentificador();
				String nombreDeposito = null;
				if (!isSinDocsFisicos && idElementoSeleccionadoReserva != null) {

					ubicacion = serviceDeposito.getUbicacion(idUbicacion);

					estadoReserva = ReservaPrevision.PENDIENTE
							.getIdentificador();
					ElementoVO deposito;

					if (isAsignable) {
						deposito = serviceDeposito
								.getElementoAsignable(idElementoSeleccionadoReserva);
					} else {
						deposito = serviceDeposito
								.getElementoNoAsignable(idElementoSeleccionadoReserva);
					}

					nombreDeposito = deposito.getNombre();
				}
				Date currentDate = DateUtils.getFechaActual();
				relacionEntrega.setEstado(EstadoREntrega.RECIBIDA
						.getIdentificador());
				relacionEntrega.setFechaestado(currentDate);
				relacionEntrega.setFecharecepcion(currentDate);
				relacionEntrega.setIdusrgestorarchivorec(idGestor);
				relacionEntrega.setRegentrada(regEntrada);
				relacionEntrega.setIddeposito(idUbicacion);
				if (!relacionEntrega.isRelacionConReencajado()) {
					relacionEntrega.setIdformatoui(idFormato);
				}
				relacionEntrega.setTipoDocumental(tipoDocumental);
				relacionEntrega
						.setIdelmtodreserva(idElementoSeleccionadoReserva);
				relacionEntrega
						.setIdtipoelmtodreserva(idTipoElementoSeleccionadoReserva);
				relacionEntrega.setReservadeposito(estadoReserva);
				_relacionEntregaDBEntity.updateRelacionEntrega(relacionEntrega);
				commit();

				GestionControlUsuariosBI gcu = getGestionControlUsusarios();
				UsuarioVO usuario = gcu.getUsuario(idGestor);

				if (!isSinDocsFisicos) {
					pistaAuditoria.auditaRecibirRelacion(locale,
							auditarSignatura, signaturaNumerica,
							cajasDeRelacion.size(), ubicacion.getNombre(),
							regEntrada, nombreDeposito,
							usuario.getNombreCompleto());
				} else {
					pistaAuditoria.auditaRecibirRelacionSinDocsFisicos(locale,
							regEntrada, usuario.getNombreCompleto());
				}
			} else
				throw new RelacionOperacionNoPermitidaException(
						ArchivoErrorCodes.RELACION_SIN_UDOC_ASOCIADAS);
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	private void eliminarRelacionEntrega(RelacionEntregaVO relacionVO) {

		String idRelacionEntrega = relacionVO.getId();
		boolean tieneDescripcion = StringUtils.isNotEmpty(relacionVO
				.getIdFicha()) ? true : false;

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		iniciarTransaccion();

		if (relacionVO.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador()) {
			// elimino relaciones
			_udocEnUIDBEntity.deleteXIdRelacion(idRelacionEntrega);

			// borrar descripción de unidades documentales en relación si la
			// tuviesen
			if (tieneDescripcion) {
				List udocsEnRE = _unidadDocumentalDBEntity
						.getUdocsByIdRelacion(idRelacionEntrega);

				if (udocsEnRE != null && udocsEnRE.size() > 0) {
					Iterator it = udocsEnRE.iterator();
					while (it.hasNext()) {
						UnidadDocumentalVO udocEnRE = (UnidadDocumentalVO) it
								.next();
						// Eliminar los valores de las tablas de descripción de
						// la unidad en la división de fracción de serie
						descripcionBI
								.eliminarValoresCamposUDocRE(
										udocEnRE.getId(),
										ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE);
					}
				}
			}
			// borrar unidades de documentacion
			_unidadDocumentalDBEntity.deleteXIdRelacion(idRelacionEntrega);

			// borrar cajas
			_unidadInstalacionDBEntity.deleteXIdRelacion(idRelacionEntrega);
		} else {
			// Eliminar la marca de bloqueada para transferencia
			List ltUnidadInstalacionReeaVO = _unidadInstalacionReeaDBEntity
					.getUnidadesInstalacionXIdRelacionEntreArchivos(idRelacionEntrega);
			if (!ListUtils.isEmpty(ltUnidadInstalacionReeaVO)) {
				int marcas = MarcaUtil.generarMarcas(new int[] {});
				ListIterator it = ltUnidadInstalacionReeaVO.listIterator();
				while (it.hasNext()) {
					UnidadInstalacionReeaVO unidadInstalacionReeaVO = (UnidadInstalacionReeaVO) it
							.next();
					_uInstalacionDepositoDBEntity.updateMarcaUnidadInstalacion(
							unidadInstalacionReeaVO.getIduideposito(), marcas);
				}
			}

			// Eliminar la marca de bloquedad para transferencias de las
			// unidades documentales sin documentos físicos
			List udocsSinDocumentosFisicos = getUDocsElectronicasByIdRelacionEntreArchivos(relacionVO
					.getId());
			if (!ListUtils.isEmpty(udocsSinDocumentosFisicos)) {
				Iterator it = udocsSinDocumentosFisicos.iterator();
				while (it.hasNext()) {
					UDocElectronicaVO udocElectronicaVO = (UDocElectronicaVO) it
							.next();
					int marcasBloqueo = MarcaUtil.generarMarcas(new int[] {});
					_unidadDocumentalElectronicaDBEntity.updateMarcasBloqueo(
							udocElectronicaVO.getId(), marcasBloqueo);
				}
			}

			if (relacionVO.isRelacionConReencajado()) {
				getGestionRelacionEACRBI().cancelarReencajadoNoTransaccional(
						idRelacionEntrega);
			}

			// borrar cajas
			_unidadInstalacionReeaDBEntity.deleteXIdRelacion(idRelacionEntrega);

			// Borrar unidades electronicas en transferencias
			_udocElectronicaDbEntity.deleteXIdRelacion(idRelacionEntrega);
		}

		if (EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador() == relacionVO
				.getEstado()) {
			// Actualizar los huecos del deposito
			_huecoDBEntity.updateEstadoHuecoUInstalacion(idRelacionEntrega,
					HuecoVO.LIBRE_STATE);
		}

		_relacionEntregaDBEntity.deleteRelacion(idRelacionEntrega);

		if (relacionVO.getIddetprevision() != null)
			_detallePrevisionDBEntity
					.decNRelacionesAsociadasADetalle(relacionVO
							.getIddetprevision());

		// Poner a null el idAlta de la revisión de unidades documentales
		if (relacionVO.getTipotransferencia() == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador()) {
			_revisionDocumentacionDBEntity.removeIdAlta(relacionVO.getId());
		}

		commit();
	}

	public void eliminarRelaciones(String[] idsRelacionesEntrega)
			throws ActionNotAllowedException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		Collection relacionesVO = getRelacionesXIds(idsRelacionesEntrega);
		// comprobar si son previsiones borrables
		boolean canBeDone = true;
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(TipoTransferencia.ORDINARIA
				.getIdentificador());
		for (Iterator itRelacionesVO = relacionesVO.iterator(); itRelacionesVO
				.hasNext();) {
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) itRelacionesVO
					.next();
			pistaAuditoria.addNewDetalleBasico(locale, relacionVO);
			if (!authorizationHelper.puedeSerEliminada(relacionVO))
				canBeDone = false;
		}
		if (!canBeDone)
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());

		// borrar
		iniciarTransaccion();
		for (Iterator itRelacionesVO = relacionesVO.iterator(); itRelacionesVO
				.hasNext();) {
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) itRelacionesVO
					.next();
			eliminarRelacionEntrega(relacionVO);
		}
		commit();
	}

	public void eliminarIngresosDirectos(String[] idsIngresos)
			throws ActionNotAllowedException {
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						TipoTransferencia.INGRESO_DIRECTO.getIdentificador(),
						ArchivoActions.FONDOS_MODULE_BORRADO_INGRESO_DIRECTO,
						this);
		Locale locale = getServiceClient().getLocale();

		checkPermission(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS);
		Collection relacionesVO = getRelacionesXIds(idsIngresos);
		// comprobar si son previsiones borrables
		boolean canBeDone = true;
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador());
		for (Iterator itRelacionesVO = relacionesVO.iterator(); itRelacionesVO
				.hasNext();) {
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) itRelacionesVO
					.next();
			pistaAuditoria.addNewDetalleBasico(locale, relacionVO);
			if (!authorizationHelper.puedeSerEliminada(relacionVO))
				canBeDone = false;
		}
		if (!canBeDone)
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());

		// borrar
		iniciarTransaccion();
		for (Iterator itRelacionesVO = relacionesVO.iterator(); itRelacionesVO
				.hasNext();) {
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) itRelacionesVO
					.next();
			eliminarRelacionEntrega(relacionVO);
		}
		commit();
	}

	private void crearUnidadInstalacionParaUdoc(UnidadDocumentalVO udoc,
			String formato, String signatura, int orden, boolean isMultidoc) {
		UnidadInstalacionVO unidadInstalacion = new UnidadInstalacionVO();
		unidadInstalacion.setIdRelEntrega(udoc.getIdRelEntrega());
		unidadInstalacion.setIdFormato(formato);
		unidadInstalacion.setEstadoCotejo(EstadoCotejo.PENDIENTE
				.getIdentificador());
		unidadInstalacion.setOrden(orden);
		unidadInstalacion.setSignaturaUI(signatura);
		_unidadInstalacionDBEntity.insertRow(unidadInstalacion);
		// TODO hacer una subclase que se UnidadDocumentalSignaturada que
		// lleve la signatura aqui se pone
		ParteUnidadDocumentalVO parteUdoc = ParteUnidadDocumentalVO
				.generateParteUdoc(udoc, 1, null);

		if (!isMultidoc)
			parteUdoc.setPosUdocEnUI(1);
		else
			parteUdoc.setPosUdocEnUI(udoc.getOrden());

		parteUdoc.setIdUinstalacionRe(unidadInstalacion.getId());
		parteUdoc.setSignaturaUdoc(signatura);
		_udocEnUIDBEntity.addUdocToUI(parteUdoc);
	}

	public RelacionEntregaVO enviarRelacionEntrega(String idRelacion)
			throws ActionNotAllowedException,
			RelacionOperacionNoPermitidaException {
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						TipoTransferencia.ORDINARIA.getIdentificador(),
						ArchivoActions.TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		RelacionEntregaVO relacionActual = getRelacionXIdRelacion(idRelacion);
		pistaAuditoria.addDetalleBasico(locale, relacionActual);

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);

		// if (relacionActual.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(relacionActual);

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionActual);
		if (authorizationHelper.puedeSerEnviada(relacionActual)) {
			PrevisionVO previsionVO = _previsionDbEntity
					.getInfoPrevision(relacionActual.getIdprevision());
			if (!previsionVO.isCaducada()) {
				// si la relacion es signaturada controlar que todas las cajas
				// tiene signatura(puede haberse creado una caja si signatura al
				// partir la udoc)
				if (relacionActual.isConSignatura()) {
					List unidadesInstalacion = getUnidadesInstalacion(relacionActual
							.getId());
					if (unidadesInstalacion != null) {
						for (Iterator itUnidadesInstalacion = unidadesInstalacion
								.iterator(); itUnidadesInstalacion.hasNext();) {
							UnidadInstalacionVO aCaja = (UnidadInstalacionVO) itUnidadesInstalacion
									.next();
							if (aCaja.getSignaturaUI() == null)
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XLA_RELACION_SIGNATURADA_PARA_PODER_SER_ENVIADA_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS);
						}
					}
				}

				if (relacionActual.isEntreArchivos()) {

					if (relacionActual.isRelacionConReencajado()) {
						try {
							getGestionRelacionEACRBI()
									.checkPermtirEnviarRelacionEACR(
											relacionActual.getId());
						} catch (RelacionEntregaConUDocsSinAsingarAUIException e) {
							throw new RelacionOperacionNoPermitidaException(
									ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA);
						} catch (RelacionEntregaNoEnviableUIsConVariasUDocsException e) {
							throw new RelacionOperacionNoPermitidaException(
									ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_UDOCS);
						} catch (RelacionEntregaNoEnviableUIsConVariasFSException e) {
							throw new RelacionOperacionNoPermitidaException(
									ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_FS);
						}

						// Eliminar las unidades de instalación vacias
						eliminarUIsReeaCRVacias(relacionActual.getId());
					}

					int uInstRelacion = _unidadInstalacionReeaDBEntity
							.countUIsRelacion(idRelacion);
					int udocsElectronicas = _udocElectronicaDbEntity
							.countUdocsElectronicasByRelacion(idRelacion);

					int proximoEstadoRelacion;
					// Comprobar que existen Unidades de Instalación o
					// Documentos Electrónicos
					if (uInstRelacion > 0 || udocsElectronicas > 0) {
						proximoEstadoRelacion = EstadoREntrega.ENVIADA
								.getIdentificador();

						Boolean isRelacionElectronica = new Boolean(false);
						if (uInstRelacion == 0 && udocsElectronicas > 0) {
							isRelacionElectronica = new Boolean(true);
						}

						_relacionEntregaDBEntity.updateEstado(idRelacion,
								proximoEstadoRelacion,
								DateUtils.getFechaActual(), null,
								isRelacionElectronica);

						pistaAuditoria.auditaEnviarRelacionEA(locale,
								relacionActual, String.valueOf(uInstRelacion),
								String.valueOf(udocsElectronicas));

					} else
						throw new RelacionOperacionNoPermitidaException(
								ArchivoErrorCodes.RELACION_SIN_UDOC_ASOCIADAS,
								"Relacion sin unidades documentales");
				} else { // No es entre Archivos

					Collection unidadesDocDeLaRelacion = null;
					unidadesDocDeLaRelacion = _unidadDocumentalDBEntity
							.fetchRowsByCodigoRelacionOrderByOrden(idRelacion);

					int proximoEstadoRelacion;
					// comprobar que las unidades documentales tienen documentos
					// asociados
					if (unidadesDocDeLaRelacion != null
							&& unidadesDocDeLaRelacion.size() > 0) {
						String nExpedientes = String
								.valueOf(unidadesDocDeLaRelacion.size());
						iniciarTransaccion();
						List unidadesInstalacionVacias = _unidadInstalacionDBEntity
								.getUnidadesInstalacionVacias(idRelacion);
						UnidadInstalacionVO unaUI = null;
						for (Iterator i = unidadesInstalacionVacias.iterator(); i
								.hasNext();) {
							unaUI = (UnidadInstalacionVO) i.next();
							_unidadInstalacionDBEntity.dropRow(unaUI);
						}
						_formatoDBEntity.loadFormato(relacionActual
								.getIdformatoui());

						// actualiza estado de la relacion
						if (!_unidadDocumentalDBEntity
								.existUdocsConDocumentosFisicos(idRelacion)) {
							// checkeo q las unidades doc con doc fisicos esten
							// con caja
							if (_unidadDocumentalDBEntity
									.isAllUdocsConDocFisicosEnCaja(idRelacion))
								proximoEstadoRelacion = EstadoREntrega.ENVIADA
										.getIdentificador();
							else
								throw new RelacionOperacionNoPermitidaException(
										ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA,
										"Relacion con unidades documentales con documentos fisicos sin caja");
						} else {
							// todos las udosc son electronicos
							proximoEstadoRelacion = EstadoREntrega.ENVIADA
									.getIdentificador();
						}

						// Comprobar si solo tiene documentos físicos
						Boolean sindocsfisicos = new Boolean(
								_unidadDocumentalDBEntity
										.isOnlyDocumentosElectronicos(idRelacion));

						_relacionEntregaDBEntity.updateEstado(idRelacion,
								proximoEstadoRelacion,
								DateUtils.getFechaActual(), null,
								sindocsfisicos);

						commit();

						pistaAuditoria.auditaEnviarRelacion(locale,
								previsionVO, nExpedientes);
					} else
						throw new RelacionOperacionNoPermitidaException(
								ArchivoErrorCodes.RELACION_SIN_UDOC_ASOCIADAS,
								"Relacion sin unidades documentales");

				}

			} else
				throw new RelacionOperacionNoPermitidaException(
						ArchivoErrorCodes.PREVISION_CADUCADA,
						"Relacion de prevision caducada");
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());

		return getRelacionXIdRelacion(idRelacion);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#eliminarUIsReeaCRVacias(java.lang.String)
	 */
	public void eliminarUIsReeaCRVacias(String idRelEntrega) {
		List uisVacias = _uiReeaCRDBEntity.getUIsVacias(idRelEntrega);
		iniciarTransaccion();
		if (ListUtils.isNotEmpty(uisVacias)) {
			for (Iterator iterator = uisVacias.iterator(); iterator.hasNext();) {
				UIReeaCRVO uiReeaCRVO = (UIReeaCRVO) iterator.next();
				_uiReeaCRDBEntity.delete(idRelEntrega, uiReeaCRVO.getId());
			}
		}
		renumerarUIs(idRelEntrega);
		commit();
	}

	public void renumerarUIs(String idRelEntrega) {
		List listaUIs = _uiReeaCRDBEntity.getByIdRelacion(idRelEntrega);
		int orden = 1;
		if (ListUtils.isNotEmpty(listaUIs)) {
			for (Iterator iterator = listaUIs.iterator(); iterator.hasNext();) {
				UIReeaCRVO ui = (UIReeaCRVO) iterator.next();
				if (ui != null) {
					_uiReeaCRDBEntity.updateOrden(ui.getId(), new Integer(
							orden++));
				}
			}
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#enviarIngresoDirecto(java.lang.String
	 * )
	 */
	public RelacionEntregaVO enviarSeleccionarUbicacionIngresoDirecto(
			String idIngreso) throws ActionNotAllowedException {
		// Obtener el ingreso
		RelacionEntregaVO ingreso = getRelacionXIdRelacion(idIngreso);

		// Verificar los permisos
		verificarPermisosElaboracionIngresoDirecto();

		// Comprobar que pueda ser enviada
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador());
		if (authorizationHelper
				.puedeSerEnviadoSeleccionarUbicacionIngreso(ingreso)) {

			Collection unidadesDocDeLaRelacion = null;
			unidadesDocDeLaRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(idIngreso);

			// comprobar que las unidades documentales tienen documentos
			// asociados
			if (unidadesDocDeLaRelacion != null
					&& unidadesDocDeLaRelacion.size() > 0) {

				iniciarTransaccion();

				// Eliminar unidades de instalación vacías
				List unidadesInstalacionVacias = _unidadInstalacionDBEntity
						.getUnidadesInstalacionVacias(idIngreso);
				for (Iterator i = unidadesInstalacionVacias.iterator(); i
						.hasNext();) {
					UnidadInstalacionVO unaUI = (UnidadInstalacionVO) i.next();
					_unidadInstalacionDBEntity.dropRow(unaUI);
				}

				// checkeo q las unidades doc con doc fisicos esten con caja
				if (!_unidadDocumentalDBEntity
						.isAllUdocsConDocFisicosEnCaja(idIngreso))
					throw new RelacionOperacionNoPermitidaException(
							ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA,
							"Relacion con unidades documentales con documentos fisicos sin caja");

				commit();
			} else {
				throw new RelacionOperacionNoPermitidaException(
						ArchivoErrorCodes.RELACION_SIN_UDOC_ASOCIADAS,
						"Ingreso sin unidades documentales");
			}
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());

		return getRelacionXIdRelacion(idIngreso);
	}

	private int calculateEstadoFinalUInstalacion(
			boolean todasPartesUnidadesDocumentalesEnEstadoRevisado,
			boolean existeParteUnidadDocumentalConErrores) {
		int estadoFinalUInstalacion;
		if (todasPartesUnidadesDocumentalesEnEstadoRevisado)
			estadoFinalUInstalacion = EstadoCotejo.REVISADA.getIdentificador();
		else if (existeParteUnidadDocumentalConErrores)
			estadoFinalUInstalacion = EstadoCotejo.ERRORES.getIdentificador();
		else
			estadoFinalUInstalacion = EstadoCotejo.PENDIENTE.getIdentificador();
		return estadoFinalUInstalacion;
	}

	public void guardarCotejo(String codRelacionEntrega, Map unidadesInstalacion) {
		String idUDoc = null, signatura = null, numParteUdoc = null;
		boolean todasPartesUnidadesDocumentalesEnEstadoRevisado = true;
		boolean existeParteUnidadDocumentalConErrores = false;
		boolean existeParteEnEstadoPendiente = false;
		Iterator itPartesUDocs = null;
		EstadoCotejo estado = null;

		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA,
						this);

		iniciarTransaccion();
		RelacionEntregaVO relacionEntregaVO = getRelacionXIdRelacion(codRelacionEntrega);
		pistaAuditoria.addDetalleBasico(locale, relacionEntregaVO);

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		ParteUnidadDocumentalVO parteUnidadDocumental = null;
		Iterator itUnidadesInstalacion = unidadesInstalacion.keySet()
				.iterator();
		boolean cotejoConErrores = false;
		while (itUnidadesInstalacion.hasNext()) {
			todasPartesUnidadesDocumentalesEnEstadoRevisado = true;
			existeParteUnidadDocumentalConErrores = false;
			UnidadInstalacionVO unidadInstalacion = (UnidadInstalacionVO) itUnidadesInstalacion
					.next();
			pistaAuditoria.auditaGuardarCotejoUnidadInstalacion(locale,
					unidadInstalacion);
			itPartesUDocs = ((List) unidadesInstalacion.get(unidadInstalacion))
					.iterator();
			while (itPartesUDocs.hasNext()) {
				parteUnidadDocumental = (ParteUnidadDocumentalVO) itPartesUDocs
						.next();
				idUDoc = parteUnidadDocumental.getIdUnidadDoc();
				numParteUdoc = Integer.toString(parteUnidadDocumental
						.getNumParteUdoc());
				estado = EstadoCotejo.getEstadoCotejo(parteUnidadDocumental
						.getEstadoCotejo());

				if (estado.equals(EstadoCotejo.ERRORES)) {
					existeParteUnidadDocumentalConErrores = true;
					existeParteEnEstadoPendiente = false;
					todasPartesUnidadesDocumentalesEnEstadoRevisado = false;

					if (parteUnidadDocumental.isFlagChanged())
						_udocEnUIDBEntity.updateEstadoYObservacionesYSignatura(
								idUDoc, numParteUdoc,
								estado.getIdentificador(),
								parteUnidadDocumental.getNotasCotejo(), null);
					pistaAuditoria.auditaGuardarCotejoParteDocumental(locale,
							parteUnidadDocumental);

				} else if (estado.equals(EstadoCotejo.REVISADA)) {
					if (parteUnidadDocumental.isFlagChanged()) {
						GestionArchivosBI archivoBI = services
								.lookupGestionArchivosBI();
						ArchivoVO archivo = archivoBI
								.getArchivoXId(relacionEntregaVO
										.getIdarchivoreceptor());
						if (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
								.getIdentificador() == archivo
								.getTiposignaturacion())
							_udocEnUIDBEntity.updateEstado(idUDoc,
									numParteUdoc, estado.getIdentificador());
						else {
							signatura = SignaturaUtil.getSignaturaUdoc(
									unidadInstalacion.getSignaturaUI(),
									serviceDeposito
											.getFormatoHueco(relacionEntregaVO
													.getIdFormatoDestino()),
									parteUnidadDocumental);
							parteUnidadDocumental.setSignaturaUdoc(signatura);

							_udocEnUIDBEntity.updateEstadoYSignatura(idUDoc,
									numParteUdoc, estado.getIdentificador(),
									signatura);
						}
					}

				} else if (estado.equals(EstadoCotejo.PENDIENTE)) {
					if (parteUnidadDocumental.isFlagChanged()) {
						existeParteEnEstadoPendiente = true;
						parteUnidadDocumental.setSignaturaUdoc(null);
						_udocEnUIDBEntity.updateEstadoYSignatura(idUDoc,
								numParteUdoc, estado.getIdentificador(), null);
					}
					todasPartesUnidadesDocumentalesEnEstadoRevisado = false;
				} else
					throw new ArchivoModelException("ESTADO DESCONOCIDO!!!!");

				// indicar q la parte ha sido actualizada
				if (parteUnidadDocumental.isFlagChanged()) {
					parteUnidadDocumental.setFlagChanged(false);
				}
			}

			// obtener estado de la caja
			int estadoFinalUInstalacion = calculateEstadoFinalUInstalacion(
					todasPartesUnidadesDocumentalesEnEstadoRevisado,
					existeParteUnidadDocumentalConErrores);
			if (estadoFinalUInstalacion == EstadoCotejo.ERRORES
					.getIdentificador())
				cotejoConErrores = true;
			boolean cambioEstadoCaja = (unidadInstalacion.getEstadocotejo() != estadoFinalUInstalacion);

			if (unidadInstalacion.isDevolver()
					&& (existeParteEnEstadoPendiente)) {
				unidadInstalacion.setDevolver(false);
				unidadInstalacion.setChanged(true);
			}

			if (cambioEstadoCaja || unidadInstalacion.isChanged()) {
				unidadInstalacion.setEstadoCotejo(estadoFinalUInstalacion);
				_unidadInstalacionDBEntity
						.updateFieldEstadoYDevolverYNotasCotejo(
								unidadInstalacion.getId(),
								estadoFinalUInstalacion,
								(unidadInstalacion.isDevolver() == true ? Constants.TRUE_STRING
										: Constants.FALSE_STRING),
								unidadInstalacion.getNotasCotejo());
			}
		}
		pistaAuditoria.auditaCotejoRelacion(locale, cotejoConErrores);
		commit();
	}

	private static boolean hayCajasADevolver(Collection unidadesInstalacion) {
		Iterator itUnidadesInstalacion = unidadesInstalacion.iterator();
		boolean hayCajasADevolver = false;
		while (itUnidadesInstalacion.hasNext()) {
			IUnidadInstalacionVO unidadInstalacion = (IUnidadInstalacionVO) itUnidadesInstalacion
					.next();
			if (unidadInstalacion.isDevolver()) {
				hayCajasADevolver = true;
				break;
			}
		}
		return hayCajasADevolver;
	}

	/**
	 * Da por finalizada la fase de cotejo de una relación de entrega. Si la
	 * relación no tiene unidades documentales marcadas con errores de cotejo la
	 * relación se signatura. En caso contrario la relación se pasa a estado
	 * 'Con errores de cotejo' y pasa a estar disponible para que el gestor de
	 * la misma en el organo remitente realize las correcciones oportunas.
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega para la que se solicita la finalización
	 *            del cotejo
	 * @param unidadesInstalacion
	 *            Contenido de la relacion de entrega
	 * @throws ActionNotAllowedException
	 *             Caso de que la finalización del cotejo de la relacion de
	 *             entrega no esté permitida
	 */
	public void finalizarCotejo(RelacionEntregaVO relacionEntrega,
			Map unidadesInstalacion) throws ActionNotAllowedException {

		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.addDetalleBasico(locale, relacionEntrega);
		iniciarTransaccion();
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);
		if (authorizationHelper.permitidaFinalizacionCotejo(relacionEntrega)) {
			ArchivoVO archivoVO = _archivoDbEntity
					.getArchivoXId(relacionEntrega.getIdarchivoreceptor());
			int proximoEstadoRelacion = EstadoREntrega.SIGNATURIZADA
					.getIdentificador();
			if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
					.getIdentificador())
				proximoEstadoRelacion = EstadoREntrega.COTEJADA
						.getIdentificador();

			boolean cotejoConErrores = false;

			int[] estados = new int[] { EstadoCotejo.ERRORES.getIdentificador() };
			int udocsConErrores = _unidadDocumentalDBEntity
					.countUdocsConEstado(relacionEntrega.getId(), estados);
			int udocsElectronicasConErrores = _udocElectronicaDbEntity
					.countUdocsElectronicasConEstado(relacionEntrega.getId(),
							estados);

			if (relacionEntrega.isEntreArchivos()) {
				if (relacionEntrega.isRelacionConReencajado()) {
					if (_uiReeaCRDBEntity.getCountByEstadosCotejo(
							relacionEntrega.getId(), estados) != 0) {
						proximoEstadoRelacion = EstadoREntrega.CON_ERRORES_COTEJO
								.getIdentificador();
						cotejoConErrores = true;
					}
				} else {
					if (_unidadInstalacionReeaDBEntity.countUinstConEstado(
							relacionEntrega.getId(), estados) != 0
							|| udocsElectronicasConErrores != 0) {
						proximoEstadoRelacion = EstadoREntrega.CON_ERRORES_COTEJO
								.getIdentificador();
						cotejoConErrores = true;
					}
				}
			} else {
				if (udocsConErrores != 0 || udocsElectronicasConErrores != 0) {
					proximoEstadoRelacion = EstadoREntrega.CON_ERRORES_COTEJO
							.getIdentificador();
					cotejoConErrores = true;
				}
			}
			pistaAuditoria.auditaCotejoRelacion(locale, cotejoConErrores);

			_relacionEntregaDBEntity
					.updateEstado(
							relacionEntrega.getId(),
							proximoEstadoRelacion,
							DateUtils.getFechaActual(),
							new Boolean(hayCajasADevolver(unidadesInstalacion
									.keySet())));

			// Si el usuario ha marcado corrección en archivo
			if (Constants.TRUE_STRING.equals(relacionEntrega
					.getCorreccionenarchivo())) {
				_relacionEntregaDBEntity.updateIndicadorCorreccionEnArchivo(
						relacionEntrega.getId(),
						relacionEntrega.getCorreccionenarchivo());
			}
			relacionEntrega.setEstado(proximoEstadoRelacion);
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		commit();
	}

	public boolean existsUdocsEstado(String codigoRelacion, int estado) {
		return _udocEnUIDBEntity.checkEstadoCotejoUDocsRelacion(codigoRelacion,
				estado);
	}

	/**
	 * Recupera el número de relaciones a gestionar por un usuario. Un usuario
	 * puede realizar tareas de gestion sobre aquellas relaciones de entrega de
	 * las que sea gestor y que se encuentren en estado 'Recibida', 'Con Errores
	 * de Cotejo' y 'Errores Corregidos'. Aquellos usuario que tengan ademas
	 * permiso de 'Gestion de transferencias en Archivo Receptor' pueden llevar
	 * a cabo acciones sobre relaciones dirigidas a alguno de los archivos que
	 * tienen asociados y que se encuentren en estado 'Enviada' o 'Signaturada'
	 *
	 * @return Número de relaciones de entrega
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario no tenga permitido realizar gestión de
	 *             relaciones de entrega
	 */
	public int getCountRelacionesAGestionar() throws ActionNotAllowedException {
		if (!getServiceClient().hasPermission(
				AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR))
			throw new RelacionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		final String anoEnCurso = String.valueOf(DateUtils.getAnoActual());
		final int[] estadosRelacionesAGestionar = {
				EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };
		int numRelaciones = _relacionEntregaDBEntity
				.getCountRelacionesXGestorEnArchivo(getServiceClient().getId(),
						estadosRelacionesAGestionar, anoEnCurso);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR)) {
			int[] estados = { EstadoREntrega.ENVIADA.getIdentificador(),
					EstadoREntrega.SIGNATURIZADA.getIdentificador(),
					EstadoREntrega.COTEJADA.getIdentificador() };

			final String[] archivosCustodia = (String[]) getServiceClient()
					.getCustodyArchiveList().toArray(
							ArrayUtils.EMPTY_STRING_ARRAY);

			if (!ArrayUtils.isEmpty(archivosCustodia)) {
				numRelaciones += _relacionEntregaDBEntity
						.getCountRelacionesXArchivoReceptor(archivosCustodia,
								estados, anoEnCurso);
			}
		}
		return numRelaciones;
	}

	/**
	 * Recupera la lista de relaciones a gestionar por un usuario. Un usuario
	 * puede realizar tareas de gestion sobre aquellas relaciones de entrega de
	 * las que sea gestor y que se encuentren en estado 'Recibida', 'Con Errores
	 * de Cotejo' y 'Errores Corregidos'. Aquellos usuario que tengan ademas
	 * permiso de 'Gestion de transferencias en Archivo Receptor' pueden llevar
	 * a cabo acciones sobre relaciones dirigidas a alguno de los archivos que
	 * tienen asociados y que se encuentren en estado 'Enviada' o 'Signaturada'
	 *
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario no tenga permitido realizar gestión de
	 *             relaciones de entrega
	 */
	public Collection getRelacionesAGestionar()
			throws ActionNotAllowedException {
		if (!getServiceClient().hasPermission(
				AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR))
			throw new RelacionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		final String anoEnCurso = String.valueOf(DateUtils.getAnoActual());
		final int[] estadosRelacionesAGestionar = {
				EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };
		List listaRelaciones = _relacionEntregaDBEntity
				.getRelacionesXGestorEnArchivo(getServiceClient().getId(),
						estadosRelacionesAGestionar, anoEnCurso);

		if (getServiceClient().hasPermission(
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR)) {
			int[] estados = { EstadoREntrega.ENVIADA.getIdentificador(),
					EstadoREntrega.SIGNATURIZADA.getIdentificador(),
					EstadoREntrega.COTEJADA.getIdentificador() };

			final String[] archivosCustodia = (String[]) getServiceClient()
					.getCustodyArchiveList().toArray(
							ArrayUtils.EMPTY_STRING_ARRAY);

			if (!ArrayUtils.isEmpty(archivosCustodia))
				listaRelaciones.addAll(_relacionEntregaDBEntity
						.getRelacionesXArchivoReceptor(archivosCustodia,
								estados, anoEnCurso));
		}
		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.ORDINARIA
				.getIdentificador());
		for (Iterator i = listaRelaciones.iterator(); i.hasNext();) {
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		}
		return listaRelaciones;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getCountRelacionesEnElaboracionXUser
	 * (java.lang.String)
	 */
	public int getCountRelacionesEnElaboracionXUser(String idUser) {
		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador(),
				EstadoREntrega.ENVIADA.getIdentificador(),
				EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador() };
		int relacionesEnElaboracion = _relacionEntregaDBEntity
				.getCountRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true, true);
		return relacionesEnElaboracion;
	}

	/**
	 * Obtiene las relaciones de entrega del año en curso que estan siendo
	 * elaboradas por un determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesEnElaboracionXUser(String idUser) {
		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador(),
				EstadoREntrega.ENVIADA.getIdentificador(),
				EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador() };
		List relacionesEnElaboracion = _relacionEntregaDBEntity
				.getRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true, true);
		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.ORDINARIA
				.getIdentificador());
		for (Iterator i = relacionesEnElaboracion.iterator(); i.hasNext();)
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		return relacionesEnElaboracion;
	}

	public int getCountRelacionesRechazadasXUser(String idUser) {
		int[] estados = { EstadoREntrega.RECHAZADA.getIdentificador() };
		int relacionesRechazadas = _relacionEntregaDBEntity
				.getCountRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true, true);
		return relacionesRechazadas;
	}

	public Collection getRelacionesRechazadasXUser(String idUser) {
		int[] estados = { EstadoREntrega.RECHAZADA.getIdentificador() };
		List relacionesRechazadas = _relacionEntregaDBEntity
				.getRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true, true);
		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.ORDINARIA
				.getIdentificador());
		for (Iterator i = relacionesRechazadas.iterator(); i.hasNext();)
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		return relacionesRechazadas;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getCountIngresosEnElaboracionXUser
	 * (java.lang.String)
	 */
	public int getCountIngresosEnElaboracionXUser(String idUser) {
		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador() };
		int[] tipos = { TipoTransferencia.INGRESO_DIRECTO.getIdentificador() };
		int ingresosEnElaboracion = _relacionEntregaDBEntity
				.getCountRelacionesXGestorYTipoEnOrganoRemitente(idUser,
						estados, String.valueOf(DateUtils.getAnoActual()),
						tipos);
		return ingresosEnElaboracion;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getIngresosEnElaboracionXUser(java
	 * .lang.String)
	 */
	public Collection getIngresosEnElaboracionXUser(String idUser) {
		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador() };
		int[] tipos = { TipoTransferencia.INGRESO_DIRECTO.getIdentificador() };
		List ingresosEnElaboracion = _relacionEntregaDBEntity
				.getRelacionesXGestorYTipoEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), tipos);
		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador());
		for (Iterator i = ingresosEnElaboracion.iterator(); i.hasNext();)
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		return ingresosEnElaboracion;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getCountIngresosFinalizadosXUser
	 * (java.lang.String)
	 */
	public int getCountIngresosFinalizadosXUser(String idUser) {
		int[] estados = { EstadoREntrega.VALIDADA.getIdentificador() };
		int[] tipos = { TipoTransferencia.INGRESO_DIRECTO.getIdentificador() };
		int ingresosFinalizados = _relacionEntregaDBEntity
				.getCountRelacionesXGestorYTipoEnOrganoRemitente(idUser,
						estados, String.valueOf(DateUtils.getAnoActual()),
						tipos);
		return ingresosFinalizados;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getIngresosFinalizadosXUser(java
	 * .lang.String)
	 */
	public Collection getIngresosFinalizadosXUser(String idUser) {
		int[] estados = { EstadoREntrega.VALIDADA.getIdentificador() };
		int[] tipos = { TipoTransferencia.INGRESO_DIRECTO.getIdentificador() };
		List ingresosFinalizados = _relacionEntregaDBEntity
				.getRelacionesXGestorYTipoEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), tipos);
		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador());
		for (Iterator i = ingresosFinalizados.iterator(); i.hasNext();)
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		return ingresosFinalizados;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getCountRelacionesFinalizadasXUser
	 * (java.lang.String)
	 */
	public int getCountRelacionesFinalizadasXUser(String idUser) {
		int[] estados = { EstadoREntrega.VALIDADA.getIdentificador() };
		int relacionesFinalizadas = _relacionEntregaDBEntity
				.getCountRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true);

		return relacionesFinalizadas;
	}

	/**
	 * Obtiene las relaciones de entrega del año en curso que estan siendo
	 * elaboradas por un determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesFinalizadasXUser(String idUser) {
		int[] estados = { EstadoREntrega.VALIDADA.getIdentificador() };
		List relacionesFinalizadas = _relacionEntregaDBEntity
				.getRelacionesXGestorEnOrganoRemitente(idUser, estados,
						String.valueOf(DateUtils.getAnoActual()), true);

		IRelacionAuthorizationHelper autorizationHelper = getAuthorizationHelper(TipoTransferencia.ORDINARIA
				.getIdentificador());
		for (Iterator i = relacionesFinalizadas.iterator(); i.hasNext();)
			autorizationHelper.configureRelacionEntrega((RelacionEntregaVO) i
					.next());
		return relacionesFinalizadas;
	}

	public void moveUdocToUi(IParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID) {
		iniciarTransaccion();
		_udocEnUIDBEntity.updateUnidadInstalacionUdoc(
				parteUdoc.getIdUnidadDoc(), parteUdoc.getNumParteUdoc(),
				unidadInstalacionID, parteUdoc.getPosUdocEnUI());

		RelacionEntregaVO relacion = getRelacionXIdRelacion(parteUdoc
				.getIdRelentrega());
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void putUdocEnUi(ParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID) {
		iniciarTransaccion();
		parteUdoc.setIdUinstalacionRe(unidadInstalacionID);
		_udocEnUIDBEntity.addUdocToUI(parteUdoc);

		RelacionEntregaVO relacion = getRelacionXIdRelacion(parteUdoc
				.getIdRelentrega());
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void removeUdocFromUi(IParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID) {
		iniciarTransaccion();
		_udocEnUIDBEntity.deleteUdocFromUI(unidadInstalacionID,
				parteUdoc.getPosUdocEnUI());

		RelacionEntregaVO relacion = getRelacionXIdRelacion(parteUdoc
				.getIdRelentrega());
		CotejoHelper.actualizarEstadoCotejoUdoc(relacion,
				parteUdoc.getIdUnidadDoc(), _udocEnUIDBEntity,
				_unidadInstalacionDBEntity);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void removeParteUdoc(IParteUnidadDocumentalVO parteUdoc)
			throws RelacionOperacionNoPermitidaException {

		iniciarTransaccion();

		checkIsPartEliminable(parteUdoc);

		_unidadDocumentalDBEntity.incrementNumPartesUdoc(
				parteUdoc.getIdUnidadDoc(), -1);

		// esta pendiente el renumerar las posiciones en esa caja.
		// puede que la ultima parte de la unidad documenta no sea la ultima de
		// la caja
		actualizaPosicionesEnCaja(parteUdoc.getIdUinstalacionRe(),
				parteUdoc.getPosUdocEnUI());

		_udocEnUIDBEntity.deleteParteUdoc(parteUdoc.getIdUnidadDoc(),
				parteUdoc.getNumParteUdoc());
		RelacionEntregaVO relacion = getRelacionXIdRelacion(parteUdoc
				.getIdRelentrega());

		CotejoHelper.actualizarEstadoCotejoUdoc(relacion,
				parteUdoc.getIdUnidadDoc(), _udocEnUIDBEntity,
				_unidadInstalacionDBEntity);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void updateNumPartesUdoc(String udocID, int numPartes) {
		iniciarTransaccion();
		_unidadDocumentalDBEntity.updateNumPartesUdoc(udocID, numPartes);
		commit();
	}

	public void updateDescripcionUdoc(String idUdoc, int numParte,
			String descContenido) {
		iniciarTransaccion();
		_udocEnUIDBEntity.updateDescripcion(idUdoc, numParte, descContenido);

		RelacionEntregaVO relacion = getRelacionXIdRelacion(getUnidadDocumental(
				idUdoc).getIdRelEntrega());

		CotejoHelper.actualizarEstadoCotejoUdoc(relacion, idUdoc,
				_udocEnUIDBEntity, _unidadInstalacionDBEntity);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void checkUInstalacionRemovable(UnidadInstalacionVO unidadInstalacion)
			throws RelacionOperacionNoPermitidaException {
		List udocsEnCaja = _udocEnUIDBEntity
				.fetchRowsByUInstalacion(unidadInstalacion.getId());
		if (udocsEnCaja != null) {
			if (udocsEnCaja.size() > 0)
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_CAJAS_VACIAS);
		}

	}

	public void checkCorreccionHuecosAsociadosRelacionLibres(String idRelacion)
			throws RelacionOperacionNoPermitidaException {
		checkHuecosAsociadosRelacionLibres(idRelacion, true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#checkHuecosAsociadosRelacionLibres
	 * (java.lang.String)
	 */
	public void checkHuecosAsociadosRelacionLibres(String idRelacion,
			boolean finalizarCorreccion)
			throws RelacionOperacionNoPermitidaException {

		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);

		List unidadesInstalacion = _unidadInstalacionDBEntity
				.fetchRowsByIdRelacion(idRelacion);

		if (!ListUtils.isEmpty(unidadesInstalacion)) {
			ListIterator it = unidadesInstalacion.listIterator();
			HuecoVO huecoVO = null;
			UnidadInstalacionVO unidadInstalacionVO = null;
			String cadenaNumeraciones = Constants.STRING_EMPTY;
			while (it.hasNext()) {
				unidadInstalacionVO = (UnidadInstalacionVO) it.next();
				huecoVO = _huecoDBEntity.getHuecoAsociadoNumeracion(
						relacionEntrega.getIdarchivoreceptor(),
						unidadInstalacionVO.getSignaturaUI());

				if (huecoVO == null)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO);
				else {
					if (finalizarCorreccion) {
						// Si el hueco esta reservado por otra relacion o no
						// esta libre
						if ((HuecoVO.RESERVADO_STATE
								.equals(huecoVO.getEstado()) && !huecoVO
								.getIdRelEntrega().equals(idRelacion))) {
							cadenaNumeraciones += (Constants.STRING_EMPTY
									.equals(cadenaNumeraciones)) ? " "
									+ huecoVO.getNumeracion() : ", "
									+ huecoVO.getNumeracion();
						} else if ((!HuecoVO.LIBRE_STATE.equals(huecoVO
								.getEstado()))
								&& !(HuecoVO.RESERVADO_STATE.equals(huecoVO
										.getEstado()) && huecoVO
										.getIdRelEntrega().equals(idRelacion))) {
							cadenaNumeraciones += (Constants.STRING_EMPTY
									.equals(cadenaNumeraciones)) ? " "
									+ huecoVO.getNumeracion() : ", "
									+ huecoVO.getNumeracion();
						}
					} else {
						if (!HuecoVO.LIBRE_STATE.equals(huecoVO.getEstado()))
							cadenaNumeraciones += (Constants.STRING_EMPTY
									.equals(cadenaNumeraciones)) ? " "
									+ huecoVO.getNumeracion() : ", "
									+ huecoVO.getNumeracion();
					}
				}
			}

			if (!Constants.STRING_EMPTY.equals(cadenaNumeraciones)) {
				Locale locale = getServiceClient().getLocale();
				String causaError = Messages.getString(
						"errors.relaciones.huecos.relacion.ya.utilizados",
						locale)
						+ cadenaNumeraciones;
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XHUECOS_ASOCIADOS_RELACION_YA_UTILIZADOS,
						causaError);
			}
		}
	}

	/**
	 * Permite reservar los huecos en una relación con un archivo receptor con
	 * signaturación asociada al hueco
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param finalizarCorreccion
	 *            Indica si se está finalizando la corrección
	 */
	private void reservarHuecosAsociadosRelacion(String idRelacion,
			boolean finalizarCorreccion)
			throws RelacionOperacionNoPermitidaException,
			ActionNotAllowedException {

		checkHuecosAsociadosRelacionLibres(idRelacion, finalizarCorreccion);

		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);

		List unidadesInstalacion = _unidadInstalacionDBEntity
				.fetchRowsByIdRelacion(idRelacion);

		if (!ListUtils.isEmpty(unidadesInstalacion)) {
			ListIterator it = unidadesInstalacion.listIterator();
			HuecoVO huecoVO = null;
			IUnidadInstalacionVO unidadInstalacionVO = null;
			int nRowsUpdated = 0;

			while (it.hasNext()) {
				unidadInstalacionVO = (IUnidadInstalacionVO) it.next();
				huecoVO = _huecoDBEntity.getHuecoAsociadoNumeracion(
						relacionEntrega.getIdarchivoreceptor(),
						unidadInstalacionVO.getSignaturaUI());

				// Reservar el hueco
				nRowsUpdated = _huecoDBEntity
						.updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
								new HuecoID(huecoVO.getIdElemAPadre(), huecoVO
										.getNumorden().intValue()),
								HuecoVO.RESERVADO_STATE, null, relacionEntrega
										.getId(), unidadInstalacionVO.getId(),
								new Integer(unidadInstalacionVO.getOrden()),
								relacionEntrega.isRelacionConReencajado());

				if (nRowsUpdated == 0) {
					throw new DepositoHuecoReservarNoLibreSignaturaAsociadaHuecoException();
				}
			}
		}
	}

	public void removeUnidadInstalacion(RelacionEntregaVO relacionEntrega,
			UnidadInstalacionVO unidadInstalacion)
			throws RelacionOperacionNoPermitidaException {
		iniciarTransaccion();
		checkUInstalacionRemovable(unidadInstalacion);

		_unidadInstalacionDBEntity.dropRow(unidadInstalacion);
		checkModificaRelacionRechazada(relacionEntrega);
		commit();
	}

	/**
	 * Permite bloquear las unidades documentales seleccionadas
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega con las unidades a bloquear
	 * @param unidadDocumentalIDs
	 *            identificadores de las unidades a bloquear
	 * @throws ActionNotAllowedException
	 */
	public void lockUnidadesDocumentales(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs) throws ActionNotAllowedException {

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		checkPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES);
		// if (!relacionEntrega.isOrdinaria() &&
		// !relacionEntrega.getIsIngresoDirecto()){
		// verificarPermisosElaboracionTransferenciasExtraordinarias();
		// }

		verificarPermisosGeneralesTransferencia(relacionEntrega);

		Locale locale = getServiceClient().getLocale();
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);

		if (authorizationHelper
				.permitidoBloqueoDesbloqueoExpedientes(relacionEntrega)) {

			iniciarTransaccion();

			int nUdocs = unidadDocumentalIDs.length;
			StringBuffer udocsLocked = new StringBuffer();
			UnidadDocumentalVO unidadDocumentalVO = null;

			for (int i = 0; i < nUdocs; i++) {
				unidadDocumentalVO = getUnidadDocumental(unidadDocumentalIDs[i]);

				if (unidadDocumentalVO.getMarcasBloqueo() > 0) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_BLOQUEAR_UDOC_BLOQUEADAS);
				}

				// Bloquea la Unidad Documental
				int marcas = MarcaUtil
						.setBitActivoInMarca(
								MarcaUdocRelacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO,
								unidadDocumentalVO.getMarcasBloqueo());
				_unidadDocumentalDBEntity.updateMarcaBloqueo(
						unidadDocumentalVO.getId(), marcas);

				udocsLocked.append(unidadDocumentalVO.getNumeroExpediente())
						.append(":").append(unidadDocumentalVO.getAsunto())
						.append(" - ");
			}

			int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
					: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
			IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
					.crearPistaAuditoria(relacionEntrega, action, this);
			pistaAuditoria
					.addDetalleBasico(
							locale,
							relacionEntrega,
							TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_BLOQUEO_UNIDAD_DOCUMENTAL);
			pistaAuditoria.auditaBloquearUnidadDocumental(locale,
					udocsLocked.toString());

			checkModificaRelacionRechazada(relacionEntrega);
			commit();

		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}
	}

	/**
	 * Permite desbloquear las unidades documentales seleccionadas
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega con las unidades a desbloquear
	 * @param unidadDocumentalIDs
	 *            identificadores de las unidades a desbloquear
	 * @throws ActionNotAllowedException
	 */
	public void unlockUnidadesDocumentales(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs) throws ActionNotAllowedException {

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		checkPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES);
		// if (!relacionEntrega.isOrdinaria() &&
		// !relacionEntrega.getIsIngresoDirecto()){
		// verificarPermisosElaboracionTransferenciasExtraordinarias();
		// }

		verificarPermisosGeneralesTransferencia(relacionEntrega);

		Locale locale = getServiceClient().getLocale();
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);

		if (authorizationHelper
				.permitidoBloqueoDesbloqueoExpedientes(relacionEntrega)) {

			iniciarTransaccion();

			int nUdocs = unidadDocumentalIDs.length;
			StringBuffer udocsUnlocked = new StringBuffer();
			UnidadDocumentalVO unidadDocumentalVO = null;

			for (int i = 0; i < nUdocs; i++) {
				unidadDocumentalVO = getUnidadDocumental(unidadDocumentalIDs[i]);

				if (unidadDocumentalVO.getMarcasBloqueo() == 0) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DESBLOQUEAR_UDOC_DESBLOQUEADAS);
				}

				// Bloquea la Unidad Documental
				int marcas = MarcaUtil
						.setBitInactivoInMarca(
								MarcaUdocRelacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO,
								unidadDocumentalVO.getMarcasBloqueo());
				_unidadDocumentalDBEntity.updateMarcaBloqueo(
						unidadDocumentalVO.getId(), marcas);

				udocsUnlocked.append(unidadDocumentalVO.getNumeroExpediente())
						.append(":").append(unidadDocumentalVO.getAsunto())
						.append(" - ");
			}

			int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
					: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
			IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
					.crearPistaAuditoria(relacionEntrega, action, this);
			pistaAuditoria
					.addDetalleBasico(
							locale,
							relacionEntrega,
							TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_BLOQUEO_UNIDAD_DOCUMENTAL);
			pistaAuditoria.auditaDesbloquearUnidadDocumental(locale,
					udocsUnlocked.toString());

			checkModificaRelacionRechazada(relacionEntrega);
			commit();

		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}
	}

	public void eliminarUnidadDocumental(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs, int subtipo)
			throws ActionNotAllowedException {

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		// if (relacionEntrega.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(relacionEntrega);

		Locale locale = getServiceClient().getLocale();
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);
		int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionEntrega, action, this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionEntrega,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ELIMINACION_UNIDAD_DOCUMENTAL);
		if (authorizationHelper
				.permitidaSustraccionExpedientes(relacionEntrega)) {

			iniciarTransaccion();

			FormatoHuecoVO formatoUI = _formatoDBEntity
					.loadFormato(relacionEntrega.getIdformatoui());
			int nUdocs = unidadDocumentalIDs.length;
			List uisToRemove = new ArrayList();
			UnidadDocumentalVO udocLast = getUnidadDocumental(unidadDocumentalIDs[nUdocs - 1]);

			StringBuffer udocsBorradas = new StringBuffer();
			for (int i = 0; i < nUdocs; i++) {

				// comprobar que la unidad documental pueda ser borrada
				if (relacionEntrega.isConErroresCotejo()) {
					// solo se pueden borrar los expedientes que no tengan
					// partes validas
					List partesUdoc = getPartesUnidadDocumental(getUnidadDocumental(unidadDocumentalIDs[i]));
					if (partesUdoc != null) {
						for (Iterator itPartesUdoc = partesUdoc.iterator(); itPartesUdoc
								.hasNext();) {
							IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdoc
									.next();
							if (aPart.isRevisada()) {
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_UDOCS_CON_ERRORES);
							}
						}
					}
				}

				UnidadDocumentalVO udoc = getUnidadDocumental(unidadDocumentalIDs[i]);

				/*
				 * if (!formatoUI.isMultidoc() ||
				 * (relacionEntrega.getIdNivelDocumental() != null &&
				 * relacionEntrega.getIdNivelDocumental()
				 * .equals(ConfiguracionSistemaArchivoFactory
				 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
				 * .getIdNivelFraccionSerie()))) {
				 */
				if (!formatoUI.isMultidoc()
						|| subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
					List partesUdoc = _udocEnUIDBEntity
							.fetchRowsByUdoc(unidadDocumentalIDs[i]);
					for (Iterator j = partesUdoc.iterator(); j.hasNext();)
						uisToRemove.add(_unidadInstalacionDBEntity
								.fetchRowById(((IParteUnidadDocumentalVO) j
										.next()).getIdUinstalacionRe()));
				}

				// no es suficiente solo con borrar la unidad documental de la/s
				// caja/s y de la relacion
				// hay que actualizar el campo POSUDOCENUI.
				// Un ejemplo dos UDOC con pos 1 y 2 y se elimina la de la pos
				// 1. la 2 debe pasar a estar en la pos 1
				// tablas afectadas ASGUDOCENUI
				actualizarPosicionesEnRelacionActual(unidadDocumentalIDs[i]);

				_udocEnUIDBEntity.dropByUdoc(unidadDocumentalIDs[i]);
				_unidadDocumentalDBEntity.dropRow(unidadDocumentalIDs[i]);

				// Eliminar la unidad documental de la tabla de Documentos
				// Electrónicos si no es relación solo electrónica
				if (udoc.getSinDocsFisicos() != null
						&& udoc.getSinDocsFisicos().equals(
								Constants.TRUE_STRING)) {
					_udocElectronicaDbEntity.deleteXId(udoc.getId());
				}

				udocsBorradas.append(udoc.getNumeroExpediente()).append(":")
						.append(udoc.getAsunto()).append(" - ");

				for (Iterator j = uisToRemove.iterator(); j.hasNext();)
					_unidadInstalacionDBEntity.dropRow((UnidadInstalacionVO) j
							.next());

				uisToRemove.clear();

				// Comprobar si hay que eliminar los valores de las tablas de
				// descripción
				if (StringUtils.isNotEmpty(relacionEntrega.getIdFicha())) {
					ServiceRepository services = ServiceRepository
							.getInstance(getServiceSession());
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();
					// Eliminar los valores de las tablas de descripción de la
					// unidad en la relación
					descripcionBI.eliminarValoresCamposUDocRE(udoc.getId(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE);
				}
			}

			// Actualizar tambien la posicion en relacion de entrega al eliminar
			// elementos
			_unidadDocumentalDBEntity.incrementOrdenUdoc(
					udocLast.getIdRelEntrega(), udocLast.getOrden(), -nUdocs);

			pistaAuditoria.auditaEliminarUnidadDocumental(locale,
					udocsBorradas.toString());

			// después de borrar la udocs comprobar si sigue habiendo udoc en
			// estado errores, si no es asi cambiar estado de caja
			if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
					.getIdentificador()) {
				for (int i = 0; i < unidadDocumentalIDs.length; i++) {
					List cajasRelacion = getUnidadesInstalacion(relacionEntrega
							.getId());
					if (cajasRelacion != null) {
						for (Iterator itCajas = cajasRelacion.iterator(); itCajas
								.hasNext();) {
							UnidadInstalacionVO aCaja = (UnidadInstalacionVO) itCajas
									.next();
							if (aCaja.getEstadocotejo() == EstadoCotejo.ERRORES
									.getIdentificador()) {
								boolean hayErrores = false;
								List udocsCaja = getUdocsEnUI(aCaja.getId());
								if (udocsCaja != null) {
									for (Iterator itUdocsCaja = udocsCaja
											.iterator(); itUdocsCaja.hasNext();) {
										IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itUdocsCaja
												.next();
										if (aPart.getEstadoCotejo() == EstadoCotejo.ERRORES
												.getIdentificador()) {
											hayErrores = true;
										}
									}
								}
								if (!hayErrores)
									_unidadInstalacionDBEntity
											.updateFieldEstado(aCaja.getId(),
													EstadoCotejo.PENDIENTE
															.getIdentificador());
							}
						}
					}
				}
			}

			actualizarPosicionesUDocs(relacionEntrega.getId());

			checkModificaRelacionRechazada(relacionEntrega);
			commit();

		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}
	}

	public List findByPrevision(String idPrevision) {
		return _relacionEntregaDBEntity.getRelacionesXPrevision(idPrevision,
				null);
	}

	public List findByPrevision(String idPrevision, int[] estados) {
		return _relacionEntregaDBEntity.getRelacionesXPrevisionYEstado(
				idPrevision, null, estados);
	}

	public List findByDetallePrevision(String idPrevision, String idDetalle) {
		return _relacionEntregaDBEntity.getRelacionesXPrevision(idPrevision,
				idDetalle);
	}

	public boolean existsUInstalacionEstado(String codigoRelacion, int[] estados) {
		return _unidadInstalacionDBEntity
				.existUnidadesInstalacionEnEstadosXIdRelacion(codigoRelacion,
						estados);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getCountRelacionesAReservar()
	 */
	public int getCountRelacionesAReservar() throws ActionNotAllowedException {
		if (!getServiceClient().hasPermission(
				AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO))
			throw new RelacionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		final String[] archivosCustodia = (String[]) getServiceClient()
				.getCustodyArchiveList().toArray(ArrayUtils.EMPTY_STRING_ARRAY);

		if (ArrayUtils.isNotEmpty(archivosCustodia)) {
			return _relacionEntregaDBEntity
					.getCountRelacionesAReservar(archivosCustodia);
		} else {
			return 0;
		}
	}

	/**
	 * Obtiene las relaciones de entrega para las que esta solicitada una
	 * reserva de espacion en el deposito fisico
	 *
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario que solicita relaciones a reservar no
	 *             tenga permiso para realizar la gestión de reservas de espacio
	 */
	public Collection getRelacionesAReservar() throws ActionNotAllowedException {
		if (!getServiceClient().hasPermission(
				AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO))
			throw new RelacionOperacionNoPermitidaException(
					ArchivoErrorCodes.USER_CAN_NOT_VIEW_DATA);

		final String[] archivosCustodia = (String[]) getServiceClient()
				.getCustodyArchiveList().toArray(ArrayUtils.EMPTY_STRING_ARRAY);

		if (ArrayUtils.isNotEmpty(archivosCustodia)) {
			return _relacionEntregaDBEntity
					.getRelacionesAReservar(archivosCustodia);
		} else {
			return null;
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getCountRelacionesAUbicar()
	 */
	public int getCountRelacionesAUbicar(String[] archivosCustodia) {
		return _relacionEntregaDBEntity
				.getCountRelacionesAUbicar(archivosCustodia);
	}

	public Collection getRelacionesAUbicar(String[] archivosCustodia) {
		return _relacionEntregaDBEntity.getRelacionesAUbicar(archivosCustodia);
	}

	public void updateUnidadInstalacion(UnidadInstalacionVO unidadInstalacion) {
		iniciarTransaccion();
		_unidadInstalacionDBEntity.updateFieldSignatura(
				unidadInstalacion.getId(), unidadInstalacion.getSignaturaUI());

		RelacionEntregaVO relacion = getRelacionXIdRelacion(unidadInstalacion
				.getIdRelEntrega());
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void updateUnidadInstalacion(String idUnidadInstalacion,
			int estadoCotejo, String notasCotejo, String devolver,
			boolean isEntreArchivos, boolean isConReencajado) {
		if (isEntreArchivos) {
			if (isConReencajado) {
				_uiReeaCRDBEntity.updateCotejo(idUnidadInstalacion,
						new Integer(estadoCotejo), notasCotejo, devolver);
			} else {
				_unidadInstalacionReeaDBEntity
						.updateFieldEstadoYDevolverYNotasCotejo(
								idUnidadInstalacion, estadoCotejo, devolver,
								notasCotejo);
			}
		} else {
			_unidadInstalacionDBEntity.updateFieldEstadoYDevolverYNotasCotejo(
					idUnidadInstalacion, estadoCotejo, devolver, notasCotejo);
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#updateUnidadInstalacion(java.util
	 * .List, transferencias.actions.RelacionEntregaPO)
	 */
	public void updateUnidadInstalacion(List listaCajas,
			RelacionEntregaPO relacionPO) {
		iniciarTransaccion();
		if (!ListUtils.isEmpty(listaCajas)) {
			for (int i = 0; i < listaCajas.size(); i++) {
				IUnidadInstalacionVO caja = (IUnidadInstalacionVO) listaCajas
						.get(i);
				updateUnidadInstalacion(caja.getId(), caja.getEstadoCotejo(),
						caja.getNotasCotejo(), caja.getDevolucion(),
						relacionPO.isEntreArchivos(),
						relacionPO.isRelacionConReencajado());
			}
		}

		commit();

	}

	/**
	 * Modifica el estado de cotejo de las unidades de instalación.
	 *
	 * @param ids
	 *            Identificadores de las unidades de instalación.
	 * @param estado
	 *            Estado de cotejo.
	 * @param relacionEntrega
	 *            Relación de entrega.
	 */
	public void updateEstadoUnidadesInstalacion(String[] ids, int estado,
			RelacionEntregaVO relacionEntrega) {
		if (!ArrayUtils.isEmpty(ids)) {
			iniciarTransaccion();

			// Modificar el estado de cotejo de la unidad de instalación
			if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() != relacionEntrega
					.getTipotransferencia())
				if (relacionEntrega.getTipotransferencia() == TipoTransferencia.ORDINARIA
						.getIdentificador()
						&& estado == EstadoCotejo.ERRORES.getIdentificador()) {
					_unidadInstalacionDBEntity.updateFieldEstado(ids, estado,
							true);
				} else {
					_unidadInstalacionDBEntity.updateFieldEstado(ids, estado,
							false);
				}

			else
				_unidadInstalacionReeaDBEntity.updateFieldEstado(ids, estado,
						false);

			// Servicio de acceso al depósito
			GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI();

			// Obtener el archivo
			ArchivoVO archivoReceptor = _archivoDbEntity
					.getArchivoXId(relacionEntrega.getIdarchivoreceptor());

			for (int i = 0; i < ids.length; i++) {
				UnidadInstalacionVO ui = getUnidadInstalacion(ids[i]);

				List udocsEnUI = getUdocsEnUI(ids[i]);
				if (!util.CollectionUtils.isEmpty(udocsEnUI)) {
					ParteUnidadDocumentalVO parteUdoc;
					String signatura;
					for (int j = 0; j < udocsEnUI.size(); j++) {
						parteUdoc = (ParteUnidadDocumentalVO) udocsEnUI.get(j);

						int udocsUI = 0;
						if (ui.getIduiubicada() != null) {
							udocsUI = _udocEnUIDepositoDBEntity
									.getUdocsXIdUinstalacion(
											ui.getIduiubicada()).size();
							parteUdoc.setPosUdocEnUI(parteUdoc.getPosUdocEnUI()
									+ udocsUI);
						}

						// Si la signaturación no es asociada a hueco o estamos
						// en un alta, hay que actualizar la signatura
						if (TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
								.getIdentificador() != archivoReceptor
								.getTiposignaturacion()
								|| relacionEntrega.getIsIngresoDirecto()) {
							signatura = SignaturaUtil.getSignaturaUdoc(ui
									.getSignaturaUI(), serviceDeposito
									.getFormatoHueco(relacionEntrega
											.getIdformatoui()), parteUdoc);

							_udocEnUIDBEntity.updateEstadoYSignatura(
									parteUdoc.getIdUnidadDoc(),
									new String[] { ""
											+ parteUdoc.getNumParteUdoc() },
									estado, signatura);
						} else {
							_udocEnUIDBEntity.updateEstado(
									parteUdoc.getIdUnidadDoc(),
									"" + parteUdoc.getNumParteUdoc(), estado);
						}
					}
				}
			}

			commit();
		}
	}

	/**
	 * Actualizacion de relacion de entrega
	 *
	 * @param relacionVO
	 *            Datos de la relacion a actualizar
	 * @throws ActionNotAllowedException
	 *             Cuando la actualizacion de la relacion no esté permitida
	 */
	public void updateRelacion(RelacionEntregaVO relacionVO)
			throws ActionNotAllowedException {

		int action = relacionVO.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		Locale locale = getServiceClient().getLocale();
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionVO, action, this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionVO,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_EDICION_CABECERA);

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		// if (relacionVO.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(relacionVO);

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionVO);
		if (authorizationHelper.puedeSerModificada(relacionVO)) {
			iniciarTransaccion();
			_relacionEntregaDBEntity.updateRelacionEntrega(relacionVO);
			commit();
		} else {
			String causaError = Messages
					.getString(
							"errors.relaciones.rechazar.usuario.no.propietario",
							locale);
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode(), causaError);
		}
	}

	private String createUdocCode(List signaturasUdoc) {
		return signaturasUdoc != null ? (String) signaturasUdoc.get(0) : null;
	}


	public void resetMapDescrUDoc(){
		mapDescUDocMap = null;
	}

	private void cargarMapDescUDoc() {
		if (mapDescUDocMap == null) {
			List mapDescUDocVOList = _mapDescUDocDbEntity.getMapsDescUDoc();
			if (mapDescUDocVOList != null && mapDescUDocVOList.size() > 0) {
				Iterator it = mapDescUDocVOList.iterator();
				mapDescUDocMap = new HashMap();
				while (it.hasNext()) {
					MapDescUDocVO mapDescUDocVO = (MapDescUDocVO) it.next();
					mapDescUDocMap.put(mapDescUDocVO.getIdFicha(),
							mapDescUDocVO);
				}
			} else {
			} // TODO Lanzar excepción no existe fichero de mapeo
		}
	}

	private String cargarMapDescUDocFicha(String idFicha)
			throws ArchivoModelException {
		String ret = null;

		Object obj = mapDescUDocMap.get(idFicha);
		if (obj != null) {
			MapDescUDocVO mapDescUDocVO = (MapDescUDocVO) obj;
			ret = mapDescUDocVO.getInfo();
		} else {
			logger.error("No existe mapeo para la ficha con id:" + idFicha);
			throw new ArchivoModelException(
					"No existe mapeo para la ficha con id: " + idFicha);
		}

		return ret;
	}

	static final int VALUE_FROM_TAG = 1;

	static final int VALUE_LITERAL = 2;

	static final int VALUE_DINAMIC = 3;

	class MapDescripcionUdoc {
		SAXReader saxReader = null;
		Document mapUdocFields = null;
		GestionDescripcionBI descripcionBI = null;

		MapDescripcionUdoc(GestionDescripcionBI descripcionBI, String idFicha) {
			this.descripcionBI = descripcionBI;
			saxReader = new SAXReader();

			try {

				// Chequeamos la carga en memoria de los xml de mapeo de udocs
				// en transferencias a su descripción
				cargarMapDescUDoc();

				// Obtenemos el String xml del fichero de mapeo para la ficha
				// concreta que vamos a utilizar
				String mapDescUDocXML = cargarMapDescUDocFicha(idFicha);

				mapUdocFields = DocumentHelper.parseText(mapDescUDocXML);

			} catch (DocumentException e) {
				throw new ArchivoModelException(e, "MapDescripcionUdoc",
						"Error leyendo archivo de mapeo");
			}
		}

		void generateDescripcionUdoc(String xmlInfo, String udocID,
				DefFicha defFicha, DefFmtFicha defFmtFicha, String idFicha,
				int tipoFicha) {
			Ficha fichaDescripcionUdoc = descripcionBI.createFichaNueva(udocID,
					tipoFicha, defFicha, defFmtFicha, idFicha);

			List datosSimples = mapUdocFields
					.selectNodes("/MAP_UDOC_REL_A_DESCR/DATOS_SIMPLES/DATO");
			Document udocInfo = null;
			try {
				udocInfo = saxReader.read(new StringReader(xmlInfo));
			} catch (DocumentException e) {
				throw new ArchivoModelException(e, "generateDescripcionUdoc",
						"Error leyendo xinfo de unidad documental");
			}
			Node unDato = null;
			String idDato = null;
			for (Iterator iter = datosSimples.iterator(); iter.hasNext();) {
				unDato = (Node) iter.next();
				idDato = unDato.valueOf("@ID");
				fichaDescripcionUdoc.addValor(idDato,
						getValorCampo(udocID, 1, unDato, udocInfo));
			}

			List datosTabla = mapUdocFields
					.selectNodes("/MAP_UDOC_REL_A_DESCR/DATOS_TABLA/TABLA");
			for (Iterator iter = datosTabla.iterator(); iter.hasNext();) {
				List filasTabla = ((Node) iter.next()).selectNodes("FILA");
				int count = 1;
				for (Iterator iFilaTabla = filasTabla.iterator(); iFilaTabla
						.hasNext();) {
					Node filaTabla = (Node) iFilaTabla.next();
					String pathNodoValores = filaTabla.valueOf("@NODO");
					List datosFila = filaTabla.selectNodes("DATO");
					ComprobacionDatosDescripcion comprobacionDatos = getComprobacionDatos(filaTabla
							.valueOf("@VERIFICAR_DATOS"));
					if (comprobacionDatos == null
							|| comprobacionDatos.datosValidos(datosFila,
									udocInfo)) {
						if (StringUtils.isNotEmpty(pathNodoValores)) {
							List nodosValor = udocInfo
									.selectNodes(pathNodoValores);
							for (Iterator j = nodosValor.iterator(); j
									.hasNext(); count++) {
								Node udocNodeValue = (Node) j.next();
								for (Iterator k = datosFila.iterator(); k
										.hasNext();) {
									unDato = (Node) k.next();
									idDato = unDato.valueOf("@ID");
									fichaDescripcionUdoc.addValor(
											idDato,
											getValorCampo(udocID, count,
													unDato, udocNodeValue));
								}// for DATO
							}// for valor de @NODO
						} else {
							for (Iterator k = datosFila.iterator(); k.hasNext();) {
								unDato = (Node) k.next();
								idDato = unDato.valueOf("@ID");
								fichaDescripcionUdoc.addValor(
										idDato,
										getValorCampo(udocID, count, unDato,
												udocInfo));
							}
							count++;
						}
					}
				}
			}

			Locale locale = getServiceClient().getLocale();
			descripcionBI.createFicha(fichaDescripcionUdoc, true, locale);
		}

		private ComprobacionDatosDescripcion getComprobacionDatos(
				String className) {
			ComprobacionDatosDescripcion comprobacionDatos = null;
			if (!StringUtils.isEmpty(className))
				try {
					comprobacionDatos = (ComprobacionDatosDescripcion) Class
							.forName(className).newInstance();
				} catch (InstantiationException e) {
					logger.error("Error instanciando clase de comprobacion de datos de descripcion: "
							+ className);
					;
				} catch (IllegalAccessException e) {
					logger.error("Clase de comprobacion de datos de descripcion no accesible: "
							+ className);
					;
				} catch (ClassNotFoundException e) {
					logger.error("No se encuentra clase de comprobacion de datos de descripcion: "
							+ className);
					;
				}
			return comprobacionDatos;
		}

		private ObtencionValor getObtencionValor(String className) {
			ObtencionValor obtencionValor = null;
			if (className != null)
				try {
					obtencionValor = (ObtencionValor) Class.forName(className)
							.newInstance();
				} catch (InstantiationException e) {
					logger.error("Error instanciando clase de comprobacion de datos de descripcion: "
							+ className);
					;
				} catch (IllegalAccessException e) {
					logger.error("Clase de comprobacion de datos de descripcion no accesible: "
							+ className);
					;
				} catch (ClassNotFoundException e) {
					logger.error("No se encuentra clase de comprobacion de datos de descripcion: "
							+ className);
					;
				}
			return obtencionValor;
		}

		private Node getNodoValorDato(String xmlValorDato) {
			Node nodoValorDato = null;
			try {
				Document documentValorDato = saxReader.read(new StringReader(
						xmlValorDato));
				nodoValorDato = documentValorDato.selectSingleNode("valor");
			} catch (DocumentException e) {
				logger.error(
						"Error extrayendo valor de dato generado dinamicamente.",
						e);
				logger.debug("Resultado devuelto por la obtencion del dato: "
						+ xmlValorDato);
			}
			return nodoValorDato;
		}

		private DescriptorVO getDescriptor(Node infoDescriptor, Node value,
				Node contextData) {
			DescriptorVO descriptor = null;
			String valueDescriptor = value.getText();
			if (infoDescriptor != null) {
				String idListaDescriptor = infoDescriptor.selectSingleNode(
						"ID_LISTA").getText();
				descriptor = _descriptorDBEntity.getDescriptor(
						idListaDescriptor, valueDescriptor);
				if (descriptor == null
						&& StringUtils.isNotBlank(valueDescriptor)) {
					descriptor = new DescriptorVO(idListaDescriptor,
							valueDescriptor);

					// No guardar IdDescrSistExt ni IdSistExt para interesados,
					// porque se va a usar el mismo descriptor para todos los
					// interesados con el mismo nombre y apellidos.
					if (!"ID_LIST_INTERESADO".equals(idListaDescriptor)) {
						Node campoDescriptor = infoDescriptor
								.selectSingleNode("SIST_EXT");
						if (campoDescriptor != null) {
							switch (Integer.parseInt(campoDescriptor
									.valueOf("@TIPOPARAM"))) {
							case VALUE_FROM_TAG:
								descriptor.setIdSistExt(contextData
										.selectSingleNode(
												campoDescriptor.getText())
										.getText());
								break;
							case VALUE_LITERAL:
								descriptor.setIdSistExt(campoDescriptor
										.getText());
							}
							campoDescriptor = infoDescriptor
									.selectSingleNode("ID_SIST_EXT");
							switch (Integer.parseInt(campoDescriptor
									.valueOf("@TIPOPARAM"))) {
							case VALUE_FROM_TAG:
								descriptor.setIdDescrSistExt(contextData
										.selectSingleNode(
												campoDescriptor.getText())
										.getText());
								break;
							case VALUE_LITERAL:
								descriptor.setIdDescrSistExt(campoDescriptor
										.getText());
							}
						}
					}

					descriptor.setTieneDescr("N");
					descriptor.setEstado(EstadoDescriptor.VALIDADO);
					descriptor = descripcionBI.insertDescriptor(descriptor);
				}
			} else
				descriptor = descripcionBI.getDescriptor(valueDescriptor);
			return descriptor;
		}

		private Valor getValorDatoFecha(int numOrden, Node value) {
			Node infoFecha = value.selectSingleNode("FECHA");
			CustomDate fecha = new CustomDate(infoFecha.getText(),
					infoFecha.valueOf("@FMT"), infoFecha.valueOf("@SEP"),
					value.valueOf("@CALIF"));
			return new Valor(numOrden, fecha);
		}

		private Valor getValorDato(int numOrden, Node value) {
			Valor valorCampo = null;
			String strValorCampo = value.getText();
			if (!StringUtils.isEmpty(strValorCampo))
				valorCampo = new Valor(numOrden, strValorCampo);
			return valorCampo;
		}

		private Valor getValorDatoReferencia(int numOrden,
				DescriptorVO descriptor) {
			Valor valorCampo = null;
			if (descriptor != null)
				valorCampo = new Valor(numOrden, descriptor.getNombre(),
						descriptor.getId(),
						CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR);
			return valorCampo;
		}

		private Valor getValorCampo(String udocID, int numOrdenDato, Node dato,
				Node infoDato) {
			int tipoDato = Integer.parseInt(dato.valueOf("@TIPO"));
			int tipoParam = Integer.parseInt(dato.valueOf("@TIPOPARAM"));

			Node nodeParam = dato.selectSingleNode("PARAM");
			Node nValorDato = null;
			switch (tipoParam) {
			case VALUE_FROM_TAG:
				nValorDato = infoDato.selectSingleNode(nodeParam.getText());
				break;
			case VALUE_LITERAL:
				nValorDato = nodeParam;
				break;
			case VALUE_DINAMIC:
				ObtencionValor valueGetter = getObtencionValor(StringUtils
						.trim(nodeParam.getText()));
				nValorDato = getNodoValorDato(valueGetter.obtenerValor(
						getTransactionManager(), udocID));
				break;
			}
			Valor valorDato = null;
			if (nValorDato != null)
				switch (tipoDato) {
				case ValorCampoGenericoVO.TIPO_FECHA:
					valorDato = getValorDatoFecha(numOrdenDato, nValorDato);
					break;
				case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
				case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
				case ValorCampoGenericoVO.TIPO_NUMERICO:
					valorDato = getValorDato(numOrdenDato, nValorDato);
					break;
				case ValorCampoGenericoVO.TIPO_REFERENCIA:
					DescriptorVO descriptor = getDescriptor(
							dato.selectSingleNode("DESCRIPTOR"), nValorDato,
							infoDato);
					valorDato = getValorDatoReferencia(numOrdenDato, descriptor);
				}
			if (valorDato != null) {
				Node transformInfo = dato.selectSingleNode("TRANSFORMA_VALOR");
				if (transformInfo != null) {
					String strValorDato = nValorDato.getText().trim();
					StringBuffer buff = new StringBuffer();
					buff.setLength(0);
					buff.append("VALOR[@ORG='").append(strValorDato)
							.append("']");
					Node transformedValue = transformInfo.selectSingleNode(buff
							.toString());
					if (transformedValue != null)
						valorDato.setValor(transformedValue.getText());
				}
			}
			return valorDato;
		}
	}

	/**
	 * Método para generar la descripción de la unidad documental a partir de lo
	 * introducido durante la relación de entrega
	 *
	 * @param descripcionBI
	 * @param idUdocRE
	 * @param idUdocCF
	 */
	private void generateDescripcionUdoc(GestionDescripcionBI descripcionBI,
			String idUdocRE, String idUdocCF) {

		// Copiar los campos descriptivos de la unidad documental a las tablas
		// de fichas de unidades en el cuadro
		descripcionBI.copiarCamposUDocREaUDocCF(idUdocRE, idUdocCF,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE);

		// Eliminar los valores de las tablas de descripción de la unidad en la
		// relación
		descripcionBI.eliminarValoresCamposUDocRE(idUdocRE,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE);
	}

	interface ProductorIDGetter {
		void resolveIDProductor(ProductorUnidadDocumentalVO productorUdoc,
				Date fechaInicioUdoc);
	}

	private ProductorIDGetter getProductorIDGetter(
			RelacionEntregaVO relacionEntrega, ServiceClient serviceClient)
			throws GestorOrganismosException {
		if (StringUtils.isNotBlank(relacionEntrega.getCodsistproductor())
				&& !relacionEntrega.isAutomatizada()) {
			return new OrganoProductorIDGetter(
					relacionEntrega.getCodsistproductor(), serviceClient);
		} else
			return new ProductorIDGetter() {
				public void resolveIDProductor(
						ProductorUnidadDocumentalVO productorUdoc,
						Date fechaInicioUdoc) {
				}
				/*
				 * public void resolveIDProductor(ProductorUnidadDocumentalVO
				 * productorUdoc) { }
				 */
			};
	}

	private class OrganoProductorIDGetter implements ProductorIDGetter {
		Map organosResueltos = null;

		GestorOrganismos gestorOrganizacion = null;

		String listaDescriptoresOrgano = null;

		String codigoGestorOrganizacion = null;

		OrganoProductorIDGetter(String codigoSistemaProductor,
				ServiceClient serviceClient) throws GestorOrganismosException {
			ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			codigoGestorOrganizacion = config.findSistemaTramitadorById(
					codigoSistemaProductor).getIdSistGestorOrg();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			gestorOrganizacion = GestorOrganismosFactory.getConnectorById(
					codigoGestorOrganizacion, params);
			listaDescriptoresOrgano = config
					.getConfiguracionGeneral()
					.getListaDescriptoresEntidad(
							TipoProductor.BDORGANIZACION.getIdentificador())
					.getId();
			organosResueltos = new HashMap();
		}

		public void resolveIDProductor(
				ProductorUnidadDocumentalVO productorUdoc, Date fechaInicioUDoc) {
			try {

				if (StringUtils.isNotBlank(productorUdoc.getCodigoOrgano())) {
					String idProductor = (String) organosResueltos
							.get(productorUdoc.getNombre());
					if (idProductor == null) {
						DescriptorVO descriptor = null;
						InfoOrgano infoOrganoProductor = null;

						// Ñapa que se ha obligado a hacer porque a veces de
						// SPIGA no nos viene relleno el nombre del órgano
						// productor
						// Para poder buscar el id de descriptor en
						// ADDESCRIPTOR, por lo que, cuando esto ocurra
						// Habrá que pasar en el parámetro valorAtrib
						// codigoOrgano+_+nombreOrgano
						if (StringUtils.isBlank(productorUdoc.getNombre())) {
							String strBusq = productorUdoc.getCodigoOrgano()
									+ Constants.SEPARADOR_COD_FECHA
									+ DateUtils.formatDate(fechaInicioUDoc);

							infoOrganoProductor = gestorOrganizacion
									.recuperarOrgano(
											TipoAtributo.CODIGO_ORGANO, strBusq);

							if (infoOrganoProductor != null)
								descriptor = _descriptorDBEntity
										.getDescriptorXIdEnSistemaExterno(
												infoOrganoProductor.getId(),
												listaDescriptoresOrgano);
							// TODO: else ¿?
						} else {
							// Si tenemos relleno el nombre, buscamos en
							// ADDESCRIPTOR por nombre
							ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionGeneral();
							String idListaDescriptora = config
									.getListaDescriptoresEntidad(
											TipoProductor.BDORGANIZACION
													.getIdentificador())
									.getId();
							descriptor = _descriptorDBEntity.getDescriptor(
									idListaDescriptora,
									productorUdoc.getNombre());

							// Si aún teniendo el nombre, no está en la tabla de
							// descriptores, tenemos que ir al sistema de
							// organización con la fecha.
							// Pero sólo lo va a encontrar en el caso de que el
							// tramitador sea spiga.
							if (descriptor == null) {
								String strBusq = productorUdoc
										.getCodigoOrgano()
										+ Constants.SEPARADOR_COD_FECHA
										+ DateUtils.formatDate(fechaInicioUDoc);

								infoOrganoProductor = gestorOrganizacion
										.recuperarOrgano(
												TipoAtributo.CODIGO_ORGANO,
												strBusq);

								// Necesarios para sistemas Externos que no sean
								// SPIGA
								if (infoOrganoProductor == null) {
									infoOrganoProductor = gestorOrganizacion
											.recuperarOrgano(
													TipoAtributo.CODIGO_ORGANO,
													productorUdoc
															.getCodigoOrgano());

								}

								if (infoOrganoProductor == null) {
									infoOrganoProductor = gestorOrganizacion
											.recuperarOrgano(
													TipoAtributo.IDENTIFICADOR_ORGANO,
													productorUdoc
															.getCodigoOrgano());
								}

								if (infoOrganoProductor != null)
									descriptor = _descriptorDBEntity
											.getDescriptorXIdEnSistemaExterno(
													infoOrganoProductor.getId(),
													listaDescriptoresOrgano);

							}
						}

						if (descriptor == null) {
							descriptor = new DescriptorVO();
							descriptor.setIdSistExt(codigoGestorOrganizacion);

							if (infoOrganoProductor != null) {
								descriptor
										.setIdDescrSistExt(infoOrganoProductor
												.getId());
								descriptor
										.setNombre(NombreOrganoFormat
												.formatearNombreLargo(
														infoOrganoProductor,
														gestorOrganizacion
																.recuperarOrganosAntecesores(
																		infoOrganoProductor
																				.getId(),
																		0)));
							}
							descriptor.setIdLista(listaDescriptoresOrgano);
							descriptor.setTimestamp(DateUtils.getFechaActual());
							descriptor.setTipo(TipoDescriptor.ENTIDAD);
							descriptor.setEstado(EstadoDescriptor.VALIDADO);
							descriptor.setTieneDescr(Constants.FALSE_STRING);
							descriptor.setEditClfDocs(Constants.FALSE_STRING);
							_descriptorDBEntity.insertDescriptorVO(descriptor);
						}
						idProductor = descriptor.getId();
						organosResueltos.put(productorUdoc.getNombre(),
								idProductor);
					}
					productorUdoc.setId(idProductor);
				}
			} catch (GestorOrganismosException goe) {
				throw new UncheckedArchivoException(goe);
			} catch (NotAvailableException nae) {
				throw new UncheckedArchivoException(nae);
			}
		}
	}

	public void validarRelacion(RelacionEntregaVO relacion,
			String idNivelUnidadDocumental, boolean comprobarPermisos,
			Collection udocsRelacion) throws Exception {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA,
						this);
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacion);
		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.addDetalleBasico(locale, relacion);

		if (authorizationHelper.puedeSerValidada(relacion)) {

			try {
				// Si estamos en una transferencia que no es entre archivos
				if (relacion.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador()) {

					if (!relacion.isAutomatizada()) {
						udocsRelacion = _unidadDocumentalDBEntity
								.fetchRowsByCodigoRelacionOrderByOrden(relacion
										.getId());
					}
					validarRelacionOficinaArchivo(relacion,
							idNivelUnidadDocumental, pistaAuditoria,
							comprobarPermisos, udocsRelacion);
				} else {
					if (relacion.isRelacionConReencajado()) {
						validarRelacionEntreArchivosCR(relacion, pistaAuditoria);
					} else {
						validarRelacionEntreArchivos(relacion, pistaAuditoria);
					}
				}
			} catch (SecurityException se) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.X_EL_USUARIO_NO_TIENE_PERMISOS_REQUERIDOS_VALIDACION);
			}

		} else {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.X_RELACION_ESTADO_VALIDACION_INCORRECTO);
		}
	}

	private void validarRelacionEntreArchivos(RelacionEntregaVO relacion,
			PistaAuditoriaTransferencias pistaAuditoria) throws Exception {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionCuadroClasificacionBI managerCuadroClf = services
				.lookupGestionCuadroClasificacionBI();
		GestionUnidadDocumentalBI managerUdocs = services
				.lookupGestionUnidadDocumentalBI();
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		GestorEstructuraDepositoBI managerDeposito = services
				.lookupGestorEstructuraDepositoBI();
		GestionFondosBI managerFondos = services.lookupGestionFondosBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionControlUsuariosBI managerControlUsuarios = services
				.lookupGestionControlUsuariosBI();
		Locale locale = getServiceClient().getLocale();

		ConfiguracionFondos configuracionFondos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		SerieVO serieUdocs = null;

		// Obtenemos la Serie de destino
		serieUdocs = managerSeries.getSerie(relacion.getIdseriedestino());

		// Obtenemos el objeto que representa el fondo destino de la
		// relación en el cuadro de clasificación
		FondoVO fondoDestino = managerFondos.getFondoXId(relacion
				.getIdfondodestino());
		StringBuffer codReferenciaFondo = new StringBuffer()
				.append(fondoDestino.getCodReferencia());

		// Inicio de transacción
		iniciarTransaccion();

		/**** UNIDADES DE INSTALACIÓN ****/
		List ltUnidadInstalacionReeaVO = _unidadInstalacionReeaDBEntity
				.getUnidadesInstalacionXIdRelacionEntreArchivos(relacion
						.getId());
		List ltUnidadesElectronicas = getUDocsElectronicasByIdRelacionEntreArchivos(relacion
				.getId());
		List listaUdocsTratadas = new ArrayList();
		int numUDocs = 0;
		int numUInst = 0;

		if (!ListUtils.isEmpty(ltUnidadInstalacionReeaVO)) {
			numUInst = ltUnidadInstalacionReeaVO.size();
			int marcas = MarcaUtil.generarMarcas(new int[] {});
			ListIterator it = ltUnidadInstalacionReeaVO.listIterator();
			StringBuffer unidadesInstalacion = new StringBuffer("");

			// Obtener las Condiciones de acceso de la serie
			String condicionesAccesoSerie = descripcionBI
					.getCondicionesAcceso(relacion.getIdseriedestino());

			// Obtener el archivo de la relación
			ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacion
					.getIdarchivoreceptor());

			while (it.hasNext()) {
				UnidadInstalacionReeaVO unidadInstalacionReeaVO = (UnidadInstalacionReeaVO) it
						.next();

				// Actualizar datos de las unidades documentales
				List listaUdocsEnUInst = this
						.getUdocsEnUInst(unidadInstalacionReeaVO.getId());
				UnidadDocumentalVO unaUdoc = null;
				Map mapLCA = new HashMap();
				int posUDocsEnCaja = 0;

				for (Iterator i = listaUdocsEnUInst.iterator(); i.hasNext();) {
					posUDocsEnCaja++;
					unaUdoc = (UnidadDocumentalVO) i.next();

					if (!listaUdocsTratadas.contains(unaUdoc.getId())) {
						listaUdocsTratadas.add(unaUdoc.getId());

						// Actualizar los valores en el cuadro
						updateElementoCFValidacionEEA(unaUdoc, posUDocsEnCaja,
								managerUdocs, relacion,
								unidadInstalacionReeaVO, managerDeposito,
								serieUdocs, configuracionFondos,
								managerCuadroClf, managerControlUsuarios,
								condicionesAccesoSerie, descripcionBI, mapLCA);
					}

					if (ConfigConstants.getInstance()
							.getSignaturacionPorArchivo()
							|| TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
									.getIdentificador() == archivoVO
									.getTiposignaturacion()) {

						// Resignaturar en depósito la unidad documental
						String nuevaSignatura = SignaturaUtil.getSignaturaUdoc(
								unidadInstalacionReeaVO.getSignaturaUI(),
								managerDeposito.getFormatoHueco(relacion
										.getIdformatoui()), posUDocsEnCaja);

						UDocEnUiDepositoVO udocEnUiDepositoVO = _udocEnUIDepositoDBEntity
								.getUdocEnUIById(
										unidadInstalacionReeaVO.getId(),
										unaUdoc.getId());

						String nuevaIdentificacion = udocEnUiDepositoVO
								.getIdentificacion();
						String[] partesIdentificacion = nuevaIdentificacion
								.split(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL);
						nuevaIdentificacion = partesIdentificacion[0]
								+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL
								+ nuevaSignatura;

						udocEnUiDepositoVO.setSignaturaudoc(nuevaSignatura);
						udocEnUiDepositoVO
								.setIdentificacion(nuevaIdentificacion);

						_udocEnUIDepositoDBEntity
								.updateIdentificacionYSignaturaUDoc(udocEnUiDepositoVO);
					}
				}

				// Hacer histórico de la caja
				historicoUnidadInstalacion(
						archivoVO.getId(),
						unidadInstalacionReeaVO.getId(),
						MotivoEliminacionUnidadInstalacion.TRANSFERENCIA_ENTRE_ARCHIVOS);

				// Actualizar datos de la caja
				UInsDepositoVO uInstDepositoVO = new UInsDepositoVO();
				uInstDepositoVO.setId(unidadInstalacionReeaVO.getId());
				uInstDepositoVO.setSignaturaui(unidadInstalacionReeaVO
						.getSignaturaUI());

				String identificacionUIns = new StringBuffer(
						codReferenciaFondo.toString())
						.append(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION)
						.append(unidadInstalacionReeaVO.getSignaturaUI())
						.toString();

				unidadesInstalacion.append(identificacionUIns);
				if (it.hasNext())
					unidadesInstalacion.append(", ");

				uInstDepositoVO.setIdentificacion(identificacionUIns);
				uInstDepositoVO.setMarcasBloqueo(marcas);

				// Se actualiza la fecha de creación a la fecha actual.
				uInstDepositoVO.setFcreacion(DateUtils.getFechaActual());

				// Actualizar las unidades de instalación endepósito
				_uInstalacionDepositoDBEntity
						.updateFieldsUInstDepositoVREA(uInstDepositoVO);

				/*** Hueco que ocupaba la unidad de instalación ***/
				HuecoVO huecoVO = new HuecoVO();
				huecoVO.setIdElemAPadre(unidadInstalacionReeaVO
						.getIdElemaPadreHuecoOrigen());
				huecoVO.setNumorden(unidadInstalacionReeaVO
						.getNumOrdenHuecoOrigen());
				huecoVO.setEstado(HuecoVO.LIBRE_STATE);
				huecoVO.setIduinstalacion(StringUtils.EMPTY);
				huecoVO.setIdRelEntrega(StringUtils.EMPTY);
				huecoVO.setOrdenenrelacion(new Integer(0));

				// Actualizar el valor de relación de entrega de la que
				// provienen y del estado
				_huecoDBEntity.updateFieldsHuecoVREA(huecoVO);
			}
			pistaAuditoria.auditaUInstValidacionRelacion(locale,
					unidadesInstalacion.toString());
		}

		// Se actualiza el numero de udocs (teniendo en cuenta que puede haber
		// partes)
		numUDocs = listaUdocsTratadas.size();

		if (!ListUtils.isEmpty(ltUnidadesElectronicas)) {
			numUDocs += ltUnidadesElectronicas.size();

			validarUnidadesElectronicas(ltUnidadesElectronicas, relacion,
					serieUdocs, pistaAuditoria);
		}

		if (numUDocs != 0) {
			/***** SERIES *****/
			actualizarDatosSeriesVREA(relacion, numUDocs, numUInst, numUInst,
					managerSeries, descripcionBI, managerDeposito);

			/***** RELACIÓN *****/
			_relacionEntregaDBEntity.updateEstado(relacion.getId(),
					EstadoREntrega.VALIDADA.getIdentificador(), new Date(),
					null);
		}

		// Marcar en el objeto de la relación ésta como validada
		relacion.setEstado(EstadoREntrega.VALIDADA.getIdentificador());

		commit();
	}

	private void validarRelacionEntreArchivosCR(RelacionEntregaVO relacion,
			PistaAuditoriaTransferencias pistaAuditoria) throws Exception {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionCuadroClasificacionBI managerCuadroClf = services
				.lookupGestionCuadroClasificacionBI();
		GestionUnidadDocumentalBI managerUdocs = services
				.lookupGestionUnidadDocumentalBI();
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		GestorEstructuraDepositoBI managerDeposito = services
				.lookupGestorEstructuraDepositoBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionControlUsuariosBI managerControlUsuarios = services
				.lookupGestionControlUsuariosBI();
		GestionRelacionesEACRBI relacionEACRBI = services
				.lookupGestionRelacionesEACRBI();

		Locale locale = getServiceClient().getLocale();

		ConfiguracionFondos configuracionFondos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		SerieVO serieUdocs = null;

		// Obtenemos la Serie de destino
		serieUdocs = managerSeries.getSerie(relacion.getIdseriedestino());

		// Inicio de transacción
		iniciarTransaccion();

		/**** UNIDADES DE INSTALACIÓN ****/
		List ltUnidadInstalacionCr = relacionEACRBI.getUIsReencajado(relacion
				.getId());
		List ltUnidadesElectronicas = getUDocsElectronicasByIdRelacionEntreArchivos(relacion
				.getId());
		List listaUdocsTratadas = new ArrayList();
		int numUDocs = 0;
		int numUInstOrigen = 0;
		int numUInstDestino = 0;

		if (!ListUtils.isEmpty(ltUnidadInstalacionCr)) {
			numUInstDestino = ltUnidadInstalacionCr.size();
			ListIterator it = ltUnidadInstalacionCr.listIterator();
			StringBuffer unidadesInstalacion = new StringBuffer("");

			// Obtener las Condiciones de acceso de la serie
			String condicionesAccesoSerie = descripcionBI
					.getCondicionesAcceso(relacion.getIdseriedestino());

			while (it.hasNext()) {
				IUnidadInstalacionVO unidadInstalacionReeaVO = (IUnidadInstalacionVO) it
						.next();

				// Actualizar datos de las unidades documentales
				List listaUdocsEnUInstCr = this.getUdocsEnUInstCR(
						relacion.getId(), unidadInstalacionReeaVO.getId());
				UnidadDocumentalVO unaUdoc = null;
				Map mapLCA = new HashMap();
				int posUDocsEnCaja = 0;

				for (Iterator i = listaUdocsEnUInstCr.iterator(); i.hasNext();) {
					posUDocsEnCaja++;
					unaUdoc = (UnidadDocumentalVO) i.next();

					if (!listaUdocsTratadas.contains(unaUdoc.getId())) {
						listaUdocsTratadas.add(unaUdoc.getId());

						// Actualizar los valores en el cuadro
						updateElementoCFValidacionEEA(unaUdoc, posUDocsEnCaja,
								managerUdocs, relacion,
								unidadInstalacionReeaVO, managerDeposito,
								serieUdocs, configuracionFondos,
								managerCuadroClf, managerControlUsuarios,
								condicionesAccesoSerie, descripcionBI, mapLCA);
					}
				}
			}

			pistaAuditoria.auditaUInstValidacionRelacion(locale,
					unidadesInstalacion.toString());
		}

		// Liberar los huecos de las unidades de instalación originales y si hay
		// reencajado eliminar las unidades de instalación
		// y las unidades documentales
		List ltUnidadInstalacionReea = _unidadInstalacionReeaDBEntity
				.getUnidadesInstalacionXIdRelacionEntreArchivos(relacion
						.getId());
		if (!ListUtils.isEmpty(ltUnidadInstalacionReea)) {

			numUInstOrigen = ltUnidadInstalacionReea.size();

			// Obtener el archivo receptor
			ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacion
					.getIdarchivoreceptor());

			Iterator it = ltUnidadInstalacionReea.iterator();
			while (it.hasNext()) {
				UnidadInstalacionReeaVO unidadInstalacionReeaVO = (UnidadInstalacionReeaVO) it
						.next();

				/*** Hueco que ocupaba la unidad de instalación ***/
				HuecoVO huecoVO = new HuecoVO();
				huecoVO.setIdElemAPadre(unidadInstalacionReeaVO
						.getIdElemaPadreHuecoOrigen());
				huecoVO.setNumorden(unidadInstalacionReeaVO
						.getNumOrdenHuecoOrigen());
				huecoVO.setEstado(HuecoVO.LIBRE_STATE);
				huecoVO.setIduinstalacion(StringUtils.EMPTY);
				huecoVO.setIdRelEntrega(StringUtils.EMPTY);
				huecoVO.setOrdenenrelacion(new Integer(0));

				// Actualizar el valor de relación de entrega de la que
				// provienen y del estado
				_huecoDBEntity.updateFieldsHuecoVREA(huecoVO);

				// Eliminar las unidades de instalación y las unidades
				// documentales originales
				_uInstalacionDepositoDBEntity
						.deleteUInstDeposito(unidadInstalacionReeaVO
								.getIduideposito());
				_udocEnUIDepositoDBEntity.deleteByIdUi(unidadInstalacionReeaVO
						.getIduideposito());

				// Hacer histórico de la caja
				historicoUnidadInstalacion(
						archivoVO.getId(),
						unidadInstalacionReeaVO.getId(),
						MotivoEliminacionUnidadInstalacion.TRANSFERENCIA_ENTRE_ARCHIVOS);
			}
		}

		// Crear las nuevas unidades de instalación y las unidades documentales
		// procedentes del reencajado
		relacionEACRBI.crearDatosEnDepositoNoTransaccional(relacion.getId());

		// Se actualiza el numero de udocs (teniendo en cuenta que puede haber
		// partes)
		numUDocs = listaUdocsTratadas.size();

		// Validar las unidades electrónicas
		if (!ListUtils.isEmpty(ltUnidadesElectronicas)) {
			numUDocs += ltUnidadesElectronicas.size();

			validarUnidadesElectronicas(ltUnidadesElectronicas, relacion,
					serieUdocs, pistaAuditoria);
		}

		if (numUDocs != 0) {
			/***** SERIES *****/
			actualizarDatosSeriesVREA(relacion, numUDocs, numUInstOrigen,
					numUInstDestino, managerSeries, descripcionBI,
					managerDeposito);

			/***** RELACIÓN *****/
			_relacionEntregaDBEntity.updateEstado(relacion.getId(),
					EstadoREntrega.VALIDADA.getIdentificador(), new Date(),
					null);
		}

		// Marcar en el objeto de la relación ésta como validada
		relacion.setEstado(EstadoREntrega.VALIDADA.getIdentificador());

		commit();
	}

	/**
	 * @param udoc
	 * @param posUDocsEnCaja
	 * @param managerUdocs
	 * @param relacion
	 * @param unidadInstalacionReeaVO
	 * @param managerDeposito
	 * @param serieUdocs
	 * @param configuracionFondos
	 * @param managerCuadroClf
	 * @param managerControlUsuarios
	 * @param condicionesAccesoSerie
	 * @param descripcionBI
	 * @param mapLCA
	 */
	private void updateElementoCFValidacionEEA(UnidadDocumentalVO udoc,
			int posUDocsEnCaja, GestionUnidadDocumentalBI managerUdocs,
			RelacionEntregaVO relacion,
			IUnidadInstalacionVO unidadInstalacionReeaVO,
			GestorEstructuraDepositoBI managerDeposito, SerieVO serieUdocs,
			ConfiguracionFondos configuracionFondos,
			GestionCuadroClasificacionBI managerCuadroClf,
			GestionControlUsuariosBI managerControlUsuarios,
			String condicionesAccesoSerie, GestionDescripcionBI descripcionBI,
			Map mapLCA) {
		/*
		 * Actualizar: - id de archivo - id de padre - id de fondo - código de
		 * referencia de fondo - código de referencia final del padre - código
		 * de referencia de las unidades documentales en la tabla ASGFELEMENTOCF
		 * - id fondo e id serie en la tabla ASGFUNIDADDOC
		 */

		fondos.vos.UnidadDocumentalVO uDocEnCF = managerUdocs
				.getUnidadDocumental(udoc.getId());

		uDocEnCF.setIdArchivo(relacion.getIdarchivoreceptor());
		uDocEnCF.setIdFondo(serieUdocs.getIdFondo());
		uDocEnCF.setCodRefFondo(serieUdocs.getCodRefFondo());
		uDocEnCF.setIdPadre(serieUdocs.getId());

		ParteUnidadDocumentalVO parteUDoc = ParteUnidadDocumentalVO
				.createParteUdoc(udoc, unidadInstalacionReeaVO.getSignaturaUI());
		parteUDoc.setPosUdocEnUI(posUDocsEnCaja);

		if (!ConfigConstants.getInstance().getCodigoUdocUnico()) {
			uDocEnCF.setCodigo(SignaturaUtil.getSignaturaUdoc(
					unidadInstalacionReeaVO.getSignaturaUI(),
					managerDeposito.getFormatoHueco(relacion.getIdformatoui()),
					parteUDoc));

			StringBuffer codigoReferenciaUdoc = new StringBuffer(
					serieUdocs.getCodReferencia()).append(
					configuracionFondos.getDelimitadorCodigoReferencia())
					.append(uDocEnCF.getCodigo());

			uDocEnCF.setCodReferencia(codigoReferenciaUdoc.toString());
		}

		// Actualizar la unidad documental en la tabla de elementos del cuadro
		managerCuadroClf.updateElementoCFVEA(uDocEnCF);

		// Actualizar la unidad documental en la tabla de unidades
		// documentales en el cuadro
		managerUdocs.updateFieldsUDocCFVEA(uDocEnCF);

		// Almacenar los diferentes identificadores de listas de acceso
		saveIdsLCAEEA(uDocEnCF, managerControlUsuarios, relacion, mapLCA);

		// Actualizar el campo Condiciones de Acceso
		// Copiar los valores de condiciones de acceso de la serie
		if (StringUtils.isNotEmpty(condicionesAccesoSerie)
				&& ConfigConstants.getInstance().getHeredarCondicionesAcceso()) {
			addCondicionesAcceso(condicionesAccesoSerie, uDocEnCF.getId(),
					descripcionBI, true);
		}
	}

	/**
	 * Almacena los diferentes identificadores de listas de acceso
	 *
	 * @param uDocEnCF
	 *            Unidad en el cuadro
	 * @param managerControlUsuarios
	 *            manager de usuarios
	 * @param relacion
	 *            Relación de entrega
	 * @param mapLCA
	 *            map con las listas de acceso
	 */
	private void saveIdsLCAEEA(fondos.vos.UnidadDocumentalVO uDocEnCF,
			GestionControlUsuariosBI managerControlUsuarios,
			RelacionEntregaVO relacion, Map mapLCA) {
		// Almacenar los diferentes identificadores de listas de acceso
		String idLCA = uDocEnCF.getIdLCA();
		if (StringUtils.isNotBlank(idLCA) && !mapLCA.containsKey(idLCA)) {
			ListaAccesoVO lcaVO = managerControlUsuarios.getListaAcceso(idLCA);
			int[] permisos = new int[] {
					new Integer(
							AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION)
							.intValue(),
					new Integer(AppPermissions.CONSULTA_CUADRO_CLASIFICACION)
							.intValue() };
			if (lcaVO != null) {
				mapLCA.put(lcaVO.getId(), lcaVO);

				for (int k = 0; k < permisos.length; k++) {
					PermisosListaVO permisosDeLaLista = new PermisosListaVO();
					permisosDeLaLista.setIdListCA(idLCA);
					permisosDeLaLista.setTipoDest(TipoDestinatario.ORGANO);
					permisosDeLaLista
							.setIdDest(relacion.getIdorganoremitente());
					permisosDeLaLista.setPerm(permisos[k]);

					// Actualizar las listas de acceso añadiendo el
					// órgano remitente de la relación de entrega
					PermisosListaVO permisoVO = _permisosListaDbEntity
							.getPermisosListaVO(permisosDeLaLista);
					if (permisoVO == null)
						_permisosListaDbEntity
								.insertPermisosListaVO(permisosDeLaLista);
				}
			}
		}
	}

	/**
	 * Valida las unidades electrónicas pasadas como parámetro
	 *
	 * @param ltUnidadesElectronicas
	 *            Lista de unidades electrónicas
	 * @param relacion
	 *            Relación de entrega
	 * @param serieUdocs
	 *            Unidades documentales de la serie
	 * @param pistaAuditoria
	 *            Pista de auditoria
	 * @return número de unidades electrónicas validadas
	 */
	private int validarUnidadesElectronicas(List ltUnidadesElectronicas,
			RelacionEntregaVO relacion, SerieVO serieUdocs,
			PistaAuditoriaTransferencias pistaAuditoria) {

		int numUDocs = 0;

		if (!ListUtils.isEmpty(ltUnidadesElectronicas)) {

			ConfiguracionFondos configuracionFondos = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionFondos();

			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionCuadroClasificacionBI managerCuadroClf = services
					.lookupGestionCuadroClasificacionBI();
			GestionUnidadDocumentalBI managerUdocs = services
					.lookupGestionUnidadDocumentalBI();
			GestionControlUsuariosBI managerControlUsuarios = services
					.lookupGestionControlUsuariosBI();

			Locale locale = getServiceClient().getLocale();

			// Ahora validar las sólo electrónicas
			ArrayList idsUdocsElectronicas = new ArrayList();

			ListIterator itEl = ltUnidadesElectronicas.listIterator();
			numUDocs += ltUnidadesElectronicas.size();

			StringBuffer udocsElectronicas = new StringBuffer();
			while (itEl.hasNext()) {
				Map mapLCA = new HashMap();
				UDocElectronicaVO elementoVO = (UDocElectronicaVO) itEl.next();

				String idUdoc = elementoVO.getId();
				udocsElectronicas.append(idUdoc);
				if (itEl.hasNext())
					udocsElectronicas.append(", ");

				idsUdocsElectronicas.add(idUdoc);
				/*
				 * Actualizar: id de archivo id de padre id de fondo el código
				 * de referencia de fondo código de referencia final del padre
				 * código de referencia de las unidades documentales en la tabla
				 * ASGFELEMENTOCF id fondo e id serie en la tabla ASGFUNIDADDOC
				 */

				fondos.vos.UnidadDocumentalVO uDocElEnCF = managerUdocs
						.getUnidadDocumental(idUdoc);

				uDocElEnCF.setIdArchivo(relacion.getIdarchivoreceptor());
				uDocElEnCF.setIdFondo(serieUdocs.getIdFondo());
				uDocElEnCF.setCodRefFondo(serieUdocs.getCodRefFondo());
				uDocElEnCF.setIdPadre(serieUdocs.getId());

				String codigoUdoc = null;

				if (!ConfigConstants.getInstance().getCodigoUdocUnico()) {
					long signaturaNumerica = SignaturaUtil
							.obtenerSignaturaNumerica(
									relacion.getIdarchivoreceptor(),
									_nSecUIDBEntity,
									_unidadInstalacionDBEntity,
									_unidadInstalacionReeaDBEntity,
									_uiReeaCRDBEntity, _huecoDBEntity,
									_udocElectronicaDbEntity);

					codigoUdoc = SignaturaUtil
							.formatearSignaturaNumerica(signaturaNumerica);
					uDocElEnCF.setCodigo(codigoUdoc);
				}

				StringBuffer codigoReferenciaUdoc = new StringBuffer(
						serieUdocs.getCodReferencia()).append(
						configuracionFondos.getDelimitadorCodigoReferencia())
						.append(uDocElEnCF.getCodigo());

				uDocElEnCF.setCodReferencia(codigoReferenciaUdoc.toString());

				// Actualizar la unidad documental en la tabla de elementos del
				// cuadro
				managerCuadroClf.updateElementoCFVEA(uDocElEnCF);

				// Actualizar la unidad documental en la tabla de unidades
				// documentales en el cuadro
				managerUdocs.updateFieldsUDocCFVEA(uDocElEnCF);

				// Almacenar los diferentes identificadores de listas de acceso
				saveIdsLCAEEA(uDocElEnCF, managerControlUsuarios, relacion,
						mapLCA);
			}

			String[] idsADesbloquear = new String[idsUdocsElectronicas.size()];
			for (int i = 0; i < idsADesbloquear.length; i++) {
				idsADesbloquear[i] = (String) idsUdocsElectronicas.get(i);
			}

			_unidadDocumentalElectronicaDBEntity
					.desbloqueaUDocsElectronicas(idsADesbloquear);
			pistaAuditoria.auditaElectronicosValidacionRelacion(locale,
					udocsElectronicas.toString());
		}

		return numUDocs;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#signaturarUbicarValidarIngreso(
	 * transferencias.vos.RelacionEntregaVO, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.util.List, java.util.List,
	 * java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void signaturarUbicarValidarIngreso(RelacionEntregaVO ingreso,
			List huecosLibresAOcupar, String idElementoDestino,
			String tipoElementoDestino, String idNivelDocumental)
			throws ActionNotAllowedException, ConcurrentModificationException,
			ArchivoModelException {

		Locale locale = getServiceClient().getLocale();
		PistaAuditoriaIngreso pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoriaIngreso(
						ArchivoActions.FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO,
						this);
		pistaAuditoria.addDetalleBasico(locale, ingreso);

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionCuadroClasificacionBI managerCuadroClf = services
				.lookupGestionCuadroClasificacionBI();
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Verificar los permisos
		verificarPermisosElaboracionIngresoDirecto();

		// Comprobar si el ingreso puede ser ubicado
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(ingreso);
		if (authorizationHelper.puedeSerUbicada(ingreso)
				|| authorizationHelper.puedeSerValidadaRelacion(ingreso)) {

			// Obtener las cajas de la relación
			Collection cajasDeRelacion = fetchRowsByIdRelacion(ingreso.getId(),
					ingreso.getTipotransferencia(),
					TipoUInstalacion.CREADAS.getIdentificador());
			Collection cajasDeRelacionAsignadas = fetchRowsByIdRelacion(
					ingreso.getId(), ingreso.getTipotransferencia(),
					TipoUInstalacion.ASIGNADAS.getIdentificador());
			if ((cajasDeRelacion != null && cajasDeRelacion.size() > 0)
					|| (cajasDeRelacionAsignadas != null && cajasDeRelacionAsignadas
							.size() > 0)) {

				SerieVO serieUdocs = null;
				String fichaDescripcionUdoc = null;
				// Obtenemos la Serie de destino
				serieUdocs = managerSeries
						.getSerie(ingreso.getIdseriedestino());

				// Leer los datos necesarios para la validación
				INivelCFVO nivelDocumental = managerCuadroClf
						.getNivelCF(idNivelDocumental);

				String fichaDescripcionPrefUDoc = serieUdocs
						.getIdFichaDescrPrefUdoc(nivelDocumental.getId());

				// Si la serie tiene definidos listas de volúmenes y ficha
				// preferente para las u.docs., usar estas, sino, las del nivel
				if (fichaDescripcionPrefUDoc != null)
					fichaDescripcionUdoc = fichaDescripcionPrefUDoc;
				else
					fichaDescripcionUdoc = nivelDocumental
							.getIdFichaDescrPref();

				MapDescripcionUdoc descripcionUdoc = new MapDescripcionUdoc(
						descripcionBI, fichaDescripcionUdoc);

				ProductorIDGetter getterIDProductor = null;
				Date fechaInicial = null, fechaFinal = null;

				try {
					getterIDProductor = getProductorIDGetter(ingreso,
							getServiceClient());
				} catch (GestorOrganismosException goe) {
					throw new ArchivoModelException(
							getClass(),
							"validarRelacion",
							Messages.getString(
									Constants.ERROR_GESTOR_ORGANIZACION, locale));
					// "No se ha podido obtener el conector de comunicación con
					// sistema gestor de organización");
				}

				// Obtener fechas extremas actuales de la serie destino
				fechaInicial = serieUdocs.getFextremainicial();
				fechaFinal = serieUdocs.getFextremafinal();

				Collection udocsRelacion = _unidadDocumentalDBEntity
						.fetchRowsByCodigoRelacionOrderByOrden(ingreso.getId());

				// Obtener el sistema productor
				Map.Entry sistemaProductor = null;
				if (StringUtils.isNotBlank(ingreso.getCodsistproductor()))
					sistemaProductor = new DefaultMapEntry(
							ingreso.getCodsistproductor(),
							ingreso.getNombresistproductor());

				DefFicha defFicha = DefFichaFactory.getInstance(
						getServiceClient()).getDefFichaById(
						fichaDescripcionUdoc);

				// Definición del formato de la ficha
				DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
						getServiceClient()).getDefFmtFicha(
						fichaDescripcionUdoc, TipoAcceso.EDICION);

				// inicializar un string para añadir al log las unidades
				// validadas
				StringBuffer uDocsValidadas = new StringBuffer();

				// Obtener la última valoración que se ha dictaminado para la
				// serie a la que se están transfiriendo las U.Docs
				ValoracionSerieVO valoracion = valoracionBI
						.getValoracionDictaminada(ingreso.getIdseriedestino());

				// Obtener el descriptor del productor para tener su idLCAPref y
				// asignársela a las U. Documentales
				// OJO!! no necesariamente tiene que haber un productor, es
				// necesaria la comprobación de valores no nulos
				ProductorSerieVO productorSerieVO = resolverProductorSerieVO(
						ingreso, serieUdocs);

				// Iniciar una transacción
				iniciarTransaccion();

				// SIGNATURAR (SOLO cuando existan nuevas cajas creadas)
				if (cajasDeRelacion != null && cajasDeRelacion.size() > 0) {
					long signaturaNumerica = -1;
					for (Iterator i = cajasDeRelacion.iterator(); i.hasNext();) {
						UnidadInstalacionVO uInst = (UnidadInstalacionVO) i
								.next();
						String id = uInst.getId();
						signaturaNumerica = SignaturaUtil
								.obtenerSignaturaNumerica(
										ingreso.getIdarchivoreceptor(),
										_nSecUIDBEntity,
										_unidadInstalacionDBEntity,
										_unidadInstalacionReeaDBEntity,
										_uiReeaCRDBEntity, _huecoDBEntity,
										_udocElectronicaDbEntity);
						String signatura = SignaturaUtil
								.formatearSignaturaNumerica(signaturaNumerica);
						_unidadInstalacionDBEntity.updateFieldSignatura(id,
								signatura);
					}
				}

				// Actualizar el estado de las unidades de instalación tanto
				// asignadas como creadas
				Collection cajasRE = fetchRowsByIdRelacion(ingreso.getId(),
						ingreso.getTipotransferencia(),
						TipoUInstalacion.ALL.getIdentificador());
				String[] ids = new String[cajasRE.size()];
				Iterator it = cajasRE.iterator();
				int i = 0;
				while (it.hasNext()) {
					UnidadInstalacionVO unidadInstalacionVO = (UnidadInstalacionVO) it
							.next();
					ids[i] = unidadInstalacionVO.getId();
					i++;
				}

				updateEstadoUnidadesInstalacion(ids,
						EstadoCotejo.REVISADA.getIdentificador(), ingreso);

				// Establecer la ubicación en caso de que no se haya determinado
				// aún, esto es, si es un ingreso en un archivo con
				// signaturación
				// asociada al hueco y en el que existen cajas asignadas, por lo
				// que queda determinado por ellas la ubicación
				if (cajasDeRelacionAsignadas != null
						&& cajasDeRelacionAsignadas.size() > 0) {
					estableceUbicacion((List) cajasDeRelacionAsignadas,
							ingreso.getId(), depositoBI, true);
				}

				// UBICAR
				depositoBI.ubicarRelacionConTransact(ingreso.getId(),
						new ArrayList(), new ArrayList(), huecosLibresAOcupar,
						idElementoDestino, tipoElementoDestino);

				// VALIDAR
				validarRelacionOficinaArchivoConTransact(ingreso,
						udocsRelacion, getterIDProductor, productorSerieVO,
						serieUdocs, valoracion, nivelDocumental,
						sistemaProductor, defFicha, defFmtFicha,
						fichaDescripcionUdoc, fechaInicial, fechaFinal,
						uDocsValidadas, descripcionUdoc, false);

				// Hacer commit de la transacción
				commit();

				pistaAuditoria.auditaSignaturarUbicarValidarIngresoDirecto(
						locale, ingreso, uDocsValidadas.toString());
				// Marcar en el objeto de la relación ésta como validada
				ingreso.setEstado(EstadoREntrega.VALIDADA.getIdentificador());

			} else
				throw new RelacionOperacionNoPermitidaException(
						ArchivoErrorCodes.RELACION_SIN_UDOC_ASOCIADAS);
		} else
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
	}

	/**
	 * Método común para llamar con una transacción creada, el método que la
	 * llama debería tener una transacción abierta
	 *
	 * @param relacion
	 *            Relacion de entrega
	 * @param udocsRelacion
	 *            Unidades documentales de la relación
	 * @param getterIDProductor
	 *            Calcula el identificador del productor de cada unidad
	 *            documental
	 * @param productorSerieVO
	 *            Productor de la serie
	 * @param serieUdocs
	 *            Unidades documentales de la serie
	 * @param valoracion
	 *            Última valoración que se ha dictaminado para la serie a la que
	 *            se están transfiriendo las U.Docs
	 * @param nivelUnidadDocumental
	 *            Nivel unidad documental
	 * @param sistemaProductor
	 *            Sistema productor
	 * @param defFicha
	 *            Definición de la ficha
	 * @param defFmtFicha
	 *            Definición del formato de la ficha
	 * @param idFichaDescripcionUdoc
	 *            Identificador de la ficha
	 * @param fechaInicial
	 *            Fecha extrema inicial de la serie
	 * @param fechaFinal
	 *            Fecha extrema final de la serie
	 * @param uDocsValidadas
	 *            Buffer para devolver las unidades validadas
	 * @param descripcionUdoc
	 *            Objeto para generar la descripción de la unidad documental
	 */
	private void validarRelacionOficinaArchivoConTransact(
			RelacionEntregaVO relacion, Collection udocsRelacion,
			ProductorIDGetter getterIDProductor,
			ProductorSerieVO productorSerieVO, SerieVO serieUdocs,
			ValoracionSerieVO valoracion, INivelCFVO nivelUnidadDocumental,
			Map.Entry sistemaProductor, DefFicha defFicha,
			DefFmtFicha defFmtFicha, String idFichaDescripcionUdoc,
			Date fechaInicial, Date fechaFinal, StringBuffer uDocsValidadas,
			MapDescripcionUdoc descripcionUdoc, boolean comprobarPermisos) {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionUnidadDocumentalBI managerUdocs = services
				.lookupGestionUnidadDocumentalBI();
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		GestionDocumentosElectronicosBI documentosBI = services
				.lookupGestionDocumentosElectronicosBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		transferencias.vos.UnidadDocumentalVO aUdoc = null;
		fondos.vos.UnidadDocumentalVO aUdocEnCF = null;
		int numUDocs = 0;
		List udocsAArchivar = new ArrayList();

		// Obtener las Condiciones de acceso de la serie
		String condicionesAccesoSerie = descripcionBI
				.getCondicionesAcceso(relacion.getIdseriedestino());

		for (Iterator i = udocsRelacion.iterator(); i.hasNext();) {
			aUdoc = (UnidadDocumentalVO) i.next();
			boolean isDocFisico = false;
			// Comprobar si es una unidad física
			if (!Constants.TRUE_STRING.equals(aUdoc.getSinDocsFisicos())) {
				isDocFisico = true;
			}

			numUDocs++;

			getterIDProductor.resolveIDProductor(aUdoc.getProductor(),
					aUdoc.getFechaInicio());

			// Obtener el código para la unidad documental
			String codigoUdoc = Constants.STRING_EMPTY;

			if (ConfigConstants.getInstance().getCodigoUdocUnico()) {
				codigoUdoc = CodigoUdocUtil.obtenerCodigoUdocFormateado(
						_nSecUDocBEntity, _elementoCuadroClasificacionDbEntity);
			} else {
				if (isDocFisico) {
					codigoUdoc = createUdocCode(_udocEnUIDBEntity
							.getSignaturasUdoc(aUdoc.getId()));
				} else {
					long signaturaNumerica = SignaturaUtil
							.obtenerSignaturaNumerica(
									relacion.getIdarchivoreceptor(),
									_nSecUIDBEntity,
									_unidadInstalacionDBEntity,
									_unidadInstalacionReeaDBEntity,
									_uiReeaCRDBEntity, _huecoDBEntity,
									_udocElectronicaDbEntity);

					codigoUdoc = SignaturaUtil
							.formatearSignaturaNumerica(signaturaNumerica);
				}
			}

			// Obtener la lista de acceso para la unidad documental
			String idLCAPref = null;
			if (productorSerieVO != null)
				idLCAPref = productorSerieVO.getIdLCAPref();

			// Crear la unidad documental en el cuadro de clasificación
			aUdocEnCF = managerUdocs.crearUnidadDocumental(serieUdocs,
					valoracion, nivelUnidadDocumental, codigoUdoc, aUdoc,
					sistemaProductor, relacion.getTipoDocumental(), idLCAPref,
					relacion.getIdarchivoreceptor(), comprobarPermisos,
					!isDocFisico);

			if (StringUtils.isEmpty(relacion.getIdFicha())) {

				addUdocsRelacionadas(relacion.getId(), aUdocEnCF.getId(),
						ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO, true,
						true);

				// Insertar la descripción de la unidad documental
				descripcionUdoc.generateDescripcionUdoc(aUdoc.asXML(defFicha),
						aUdocEnCF.getId(), defFicha, defFmtFicha,
						idFichaDescripcionUdoc, TipoFicha.FICHA_ELEMENTO_CF);

			} else {
				addUdocsRelacionadas(relacion.getId(), aUdocEnCF.getId(),
						ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO, false,
						true);

				// Obtener los valores de la ficha de la descripción
				ValoresFicha valoresFicha = ValoresFicha.getInstance(
						getServiceSession(), aUdoc.getId(),
						TipoFicha.FICHA_UDOCRE, aUdoc);

				// Actualizar el campo info con el xml con los valores
				// actualizados
				_unidadDocumentalDBEntity.updateXmlInfo(aUdoc,
						aUdoc.asXMLWithValores(valoresFicha, defFicha));

				// Insertar la descripción de la unidad documental
				generateDescripcionUdoc(descripcionBI, aUdoc.getId(),
						aUdocEnCF.getId());


				aUdoc.setIdElementocf(aUdocEnCF.getId());

			}

			// Copiar los valores de condiciones de acceso de la serie
			if (StringUtils.isNotEmpty(condicionesAccesoSerie)
					&& ConfigConstants.getInstance()
							.getHeredarCondicionesAcceso()) {
				addCondicionesAcceso(condicionesAccesoSerie, aUdocEnCF.getId(),
						descripcionBI, false);
			}

			// Insertar los documentos electrónicos
			documentosBI.insertDocumentosDesdeValidacion(
					aUdocEnCF.getIdElementocf(),
					aUdoc.getDocumentosElectronicos());

			// Actualizar ASGDUDOCENUI con el id de elementoCF recién creado
			if (isDocFisico)
				depositoBI.atachUdocToElementoCF(aUdoc.getId(),
						aUdocEnCF.getId());

			// Actualizar fechas máximas temporales
			if (fechaInicial == null
					|| (aUdoc.getFechaInicio() != null && fechaInicial
							.after(aUdoc.getFechaInicio())))
				fechaInicial = aUdoc.getFechaInicio();
			if (fechaFinal == null
					|| (aUdoc.getFechaFin() != null && fechaFinal.before(aUdoc
							.getFechaFin())))
				fechaFinal = aUdoc.getFechaFin();

			uDocsValidadas.append(aUdoc.getNumeroExpediente()).append(":")
					.append(aUdoc.getAsunto()).append("- ");

			// Llamar al método del tramitador que archiva los expedientes si es
			// una relación ordinaria
			udocsAArchivar.add(aUdoc.getNumeroExpediente());

		}// for de creación de uDocs

		// Si la relación es ordinaria archivar los expedientes
		if (relacion.isOrdinaria() && (!ListUtils.isEmpty(udocsAArchivar))
				&& !relacion.isAutomatizada()) {

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

			String[] idsExp = new String[udocsAArchivar.size()];
			udocsAArchivar.toArray(idsExp);
			try {
				SistemaTramitador sistemaTramitacion = SistemaTramitadorFactory
						.getConnector(relacion.getCodsistproductor(), params);
				sistemaTramitacion.archivarExpedientes(idsExp);
			} catch (SistemaTramitadorException e) {
				logger.error("No se ha podido archivar los expedientes: "
						+ idsExp.toString());
			} catch (NotAvailableException e) {
				logger.error("No se ha podido archivar los expedientes: "
						+ idsExp.toString());
			}
		}


			_unidadDocumentalDBEntity.setEstadoValidacion(relacion.getId(),
					true);

		if (!relacion.isAutomatizada()) {
			_relacionEntregaDBEntity.updateEstado(relacion.getId(),
					EstadoREntrega.VALIDADA.getIdentificador(), new Date(),
					null);
		}

		// Actualizar datos de la serie
		actualizarDatosSerie(relacion, numUDocs, managerSeries, serieUdocs,
				descripcionBI, fechaInicial, fechaFinal, depositoBI);
	}

	public void validarRelacionOficinaArchivo(RelacionEntregaVO relacion,
			String idNivelUnidadDocumental,
			PistaAuditoriaTransferencias pistaAuditoria,
			boolean comprobarPermisos, Collection udocsRelacion) {

		Locale locale = getServiceClient().getLocale();
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionCuadroClasificacionBI managerCuadroClf = services
				.lookupGestionCuadroClasificacionBI();
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();

		ProductorIDGetter getterIDProductor = null;
		Date fechaInicial = null, fechaFinal = null;
		SerieVO serieUdocs = null;
		String fichaDescripcionUdoc = null;

		// Obtenemos la Serie de destino
		serieUdocs = managerSeries.getSerie(relacion.getIdseriedestino());

		// Obtenemos el nivel cuyo identificador es el pasado por parámetro y a
		// partir del mismo obtenemos el identificador de su ficha preferente
		INivelCFVO nivelUnidadDocumental = managerCuadroClf
				.getNivelCF(idNivelUnidadDocumental);

		String fichaDescripcionPrefUDoc = serieUdocs
				.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());

		// Si la serie tiene definidos listas de volúmenes y ficha preferente
		// para las u.docs., usar estas, sino, las del nivel
		if (fichaDescripcionPrefUDoc != null)
			fichaDescripcionUdoc = fichaDescripcionPrefUDoc;
		else
			fichaDescripcionUdoc = nivelUnidadDocumental.getIdFichaDescrPref();

		MapDescripcionUdoc descripcionUdoc = new MapDescripcionUdoc(
				descripcionBI, fichaDescripcionUdoc);

		try {
			getterIDProductor = getProductorIDGetter(relacion,
					getServiceClient());
		} catch (GestorOrganismosException goe) {
			throw new ArchivoModelException(getClass(), "validarRelacion",
					Messages.getString(Constants.ERROR_GESTOR_ORGANIZACION,
							locale));
		}

		// Obtener fechas extremas actuales de la serie destino
		fechaInicial = serieUdocs.getFextremainicial();
		fechaFinal = serieUdocs.getFextremafinal();

		// Obtener la lista de U.Docs en la relación de entrega

		// Obtener el sistema productor
		Map.Entry sistemaProductor = null;
		if (StringUtils.isNotBlank(relacion.getCodsistproductor()))
			sistemaProductor = new DefaultMapEntry(
					relacion.getCodsistproductor(),
					relacion.getNombresistproductor());

		// Definición de la ficha
		DefFicha defFicha = DefFichaFactory.getInstance(getServiceClient())
				.getDefFichaById(fichaDescripcionUdoc);

		// Definición del formato de la ficha
		DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
				getServiceClient()).getDefFmtFicha(fichaDescripcionUdoc,
				TipoAcceso.EDICION);

		// inicializar un string para añadir al log las unidades validadas
		StringBuffer uDocsValidadas = new StringBuffer();

		// Obtener la última valoración que se ha dictaminado para la serie a la
		// que se están transfiriendo las U.Docs
		ValoracionSerieVO valoracion = valoracionBI
				.getValoracionDictaminada(relacion.getIdseriedestino());

		// Obtener el descriptor del productor para tener su idLCAPref y
		// asignársela a las U. Documentales
		// OJO!! no necesariamente tiene que haber un productor, es necesaria la
		// comprobación de valores no nulos
		ProductorSerieVO productorSerieVO = resolverProductorSerieVO(relacion,
				serieUdocs);

		// Iniciamos la transacción aquí, donde empiezan las operaciones que
		// deben realizarse todas o ninguna
		iniciarTransaccion();

		// Llamar al método común para validar las unidades documentales
		validarRelacionOficinaArchivoConTransact(relacion, udocsRelacion,
				getterIDProductor, productorSerieVO, serieUdocs, valoracion,
				nivelUnidadDocumental, sistemaProductor, defFicha, defFmtFicha,
				fichaDescripcionUdoc, fechaInicial, fechaFinal, uDocsValidadas,
				descripcionUdoc, comprobarPermisos);

		pistaAuditoria.auditaValidacionRelacion(locale,
				uDocsValidadas.toString());

		// Marcar en el objeto de la relación ésta como validada
		relacion.setEstado(EstadoREntrega.VALIDADA.getIdentificador());

		// Finalizar transacción
		commit();
	}

	/**
	 * Actualizar los datos referidos a la serie cuyas unidades documentales
	 * acaban de darse de alta en el cuadro
	 * */

	public void actualizarDatosSerie(RelacionEntregaVO relacion, int numUDocs,
			GestionSeriesBI managerSeries, SerieVO serieUdocs,
			GestionDescripcionBI descripcionBI, Date fechaInicial,
			Date fechaFinal, GestorEstructuraDepositoBI depositoBI) {

		managerSeries.updateVolumenSerieNoTransaccional(serieUdocs.getId());
		managerSeries.updateContenidoSerieNoTransaccional(serieUdocs.getId());
		managerSeries.updateFechasExtremas(serieUdocs.getId(), fechaInicial,
				fechaFinal);

		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] {
						ADReglaGenDatosContants.ACTUALIZAR_VOLUMENES_SOPORTES,
						ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS });
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_INICIAL,
				fechaInicial);
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_FINAL,
				fechaFinal);
		descripcionBI.generarAutomaticos(serieUdocs.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);
	}

	/**
	 * Actualizar los datos referidos a la serie cuyas unidades documentales
	 * acaban de darse de alta en el cuadro
	 * */

	public void actualizarDatosSeriesVREA(RelacionEntregaVO relacion,
			int numUDocs, int nUnidadesInstalacionOrigen,
			int nUnidadesInstalacionDestino, GestionSeriesBI managerSeries,
			GestionDescripcionBI descripcionBI,
			GestorEstructuraDepositoBI depositoBI) {

		Date fMinInicialUDocsOrigen = null, fMaxFinalUDocsOrigen = null, fMinInicialUDocsDestino = null, fMaxFinalUDocsDestino = null;

		ConfiguracionDescripcion configuracionDescripcion = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		SerieVO serieOrigenVO = managerSeries.getSerie(relacion
				.getIdserieorigen());
		SerieVO serieDestinoVO = managerSeries.getSerie(relacion
				.getIdseriedestino());

		// Actualizamos la serie origen
		// Obtenemos las nuevas fechas extremas después de haber cambiado la
		// serie de las unidades documentales
		fMinInicialUDocsOrigen = descripcionBI.getFechaMinimaUDocs(
				serieOrigenVO.getId(),
				configuracionDescripcion.getFechaExtremaInicial());
		fMaxFinalUDocsOrigen = descripcionBI.getFechaMaximaUDocs(
				serieOrigenVO.getId(),
				configuracionDescripcion.getFechaExtremaFinal());

		managerSeries.updateVolumenSerieNoTransaccional(serieOrigenVO.getId());
		managerSeries
				.updateContenidoSerieNoTransaccional(serieOrigenVO.getId());
		managerSeries.updateFechasExtremas(serieOrigenVO.getId(),
				fMinInicialUDocsOrigen, fMaxFinalUDocsOrigen);

		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] {
						ADReglaGenDatosContants.ACTUALIZAR_VOLUMENES_SOPORTES,
						ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS });
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_INICIAL,
				fMinInicialUDocsOrigen);
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_FINAL,
				fMaxFinalUDocsOrigen);
		descripcionBI.generarAutomaticos(serieOrigenVO.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);

		// Actualizamos la serie destino
		fMinInicialUDocsDestino = descripcionBI.getFechaMinimaUDocs(
				serieDestinoVO.getId(),
				configuracionDescripcion.getFechaExtremaInicial());
		fMaxFinalUDocsDestino = descripcionBI.getFechaMaximaUDocs(
				serieDestinoVO.getId(),
				configuracionDescripcion.getFechaExtremaFinal());

		managerSeries.updateVolumenSerieNoTransaccional(serieDestinoVO.getId());
		managerSeries.updateContenidoSerieNoTransaccional(serieDestinoVO
				.getId());
		managerSeries.updateFechasExtremas(serieDestinoVO.getId(),
				fMinInicialUDocsDestino, fMaxFinalUDocsDestino);

		paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] {
						ADReglaGenDatosContants.ACTUALIZAR_VOLUMENES_SOPORTES,
						ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS });
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_INICIAL,
				fMinInicialUDocsDestino);
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_FINAL,
				fMaxFinalUDocsDestino);
		descripcionBI.generarAutomaticos(serieDestinoVO.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);
	}

	/**
	 * Obtener el productor de la serie
	 * */
	public ProductorSerieVO resolverProductorSerieVO(
			RelacionEntregaVO relacion, SerieVO serieUdocs) {
		ProductorSerieVO productorSerieVO = null;

		// Si es una transferencia extraordinaria, el id del descriptor del
		// productor viene en IdDescrOrgProductor
		if (relacion.getTipotransferencia() != TipoTransferencia.ORDINARIA
				.getIdentificador()) {

			productorSerieVO = _productorSerieDBEntity
					.getProductorXIdSerieEIdDescriptorOrgano(
							serieUdocs.getId(),
							relacion.getIddescrorgproductor());

			// Para las relaciones de entrega extraordinarias creadas antes de
			// haber añadido el campo Iddescrorgproductor, el objeto anterior
			// será null
			// por lo que habrá que intentar obtener la lista de órganos
			// productores vigentes de la serie, buscar si alguno coincide con
			// el órgano remitente,
			// y en caso de que ninguno coincida, dejar que sea null. Si alguno
			// coincide, también hay que actualizar el valor de
			// iddescrorgproductor en
			// la relación de entrega

			if (productorSerieVO == null) {
				// Obtenemos el objeto productor de donde sacar el id de órgano
				// a partir del id de organo remitente (descriptor)
				OrganoProductorVO organoRemitenteVO = _organoProductorDBEntity
						.getOrgProductorXIdOrgano(relacion
								.getIdorganoremitente());
				ProductorSerieVO productorRemitenteVO = null;
				if (organoRemitenteVO != null)
					productorRemitenteVO = _productorSerieDBEntity
							.getProductorXIdSerieEIdDescriptorOrgano(
									serieUdocs.getId(),
									organoRemitenteVO.getId());

				// Si hemos encontrado como productor de la serie el órgano
				// remitente, este es el que debemos asignar a productorSerieVO
				// y además,
				// sería necesario actualizar el campo idDescrorgproductor de
				// esta relación de entrega para que quede bien rellenado
				if (productorRemitenteVO != null) {
					productorSerieVO = productorRemitenteVO;
					relacion.setIddescrorgproductor(organoRemitenteVO.getId());
					_relacionEntregaDBEntity
							.updateIdDescrorgproductor(relacion.getId(),
									relacion.getIddescrorgproductor());
				}
			}
		} else // Si es una transferencia ordinaria, el id del productor viene
				// en IdOrganoRemitente => hay que sacar el id de descriptor del
				// productor
		{
			OrganoProductorVO organoProductorVO = _organoProductorDBEntity
					.getOrgProductorXIdOrgano(relacion.getIdorganoremitente());

			if (organoProductorVO != null)
				productorSerieVO = _productorSerieDBEntity
						.getProductorXIdSerieEIdDescriptorOrgano(
								serieUdocs.getId(), organoProductorVO.getId());
		}

		return productorSerieVO;
	}

	/**
	 * Obtiene el número de unidades de instalación que tiene una relación de
	 * entrega
	 *
	 * @param idRelacionEntrega
	 *            Identificador de relación de entrega
	 * @return Número de unidades de instalación de la relación de entrega
	 */
	public int getNUnidadesInstalacion(String idRelacionEntrega) {
		return _unidadInstalacionDBEntity.countUIsRelacion(idRelacionEntrega);
	}

	/**
	 * Obtiene el número de unidades de instalación que tiene una relación de
	 * entrega
	 *
	 * @param idRelacionEntrega
	 *            Identificador de relación de entrega
	 * @param idTipoTransferencia
	 *            id del tipo de transferencia
	 * @return Número de unidades de instalación de la relación de entrega
	 */
	public int getNUnidadesInstalacion(String idRelacionEntrega,
			int idTipoTransferencia) {
		RelacionEntregaVO relacion = _relacionEntregaDBEntity
				.getRelacionXId(idRelacionEntrega);
		if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == idTipoTransferencia) {
			if (relacion.isRelacionConReencajado()) {
				return _uiReeaCRDBEntity
						.getCountByIdRelacion(idRelacionEntrega);
			} else {
				return _unidadInstalacionReeaDBEntity
						.countUIsRelacion(idRelacionEntrega);
			}
		} else if (TipoTransferencia.INGRESO_DIRECTO.getIdentificador() == idTipoTransferencia) {
			return _unidadInstalacionDBEntity.countUIsRelacion(
					idRelacionEntrega, true);
		} else {
			return _unidadInstalacionDBEntity
					.countUIsRelacion(idRelacionEntrega);
		}
	}

	public int getNUnidadesInstalacionAsignadas(String idRelacionEntrega,
			int idTipoTransferencia) {
		return _unidadInstalacionDBEntity
				.countUIsAsignadasRelacion(idRelacionEntrega);
	}

	/**
	 * Obtiene las relaciones que han sido recibidas y que se encuentran
	 * pendientes de ser ubicadas en una ubicación
	 *
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param formato
	 *            Identificador de formato. Puede ser nulo
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public List getRelacionesPendientesDeUbicar(String idUbicacion,
			String formato) {
		int estadosRelacion[] = { EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador(),
				EstadoREntrega.SIGNATURIZADA.getIdentificador(),
				EstadoREntrega.COTEJADA.getIdentificador() };
		List relacionesPendientesUbicar = _relacionEntregaDBEntity
				.getRelacionXUbicacion(idUbicacion, estadosRelacion, formato,
						false);
		return relacionesPendientesUbicar;
	}

	/**
	 * Obtiene la lista de expedientes que es posible incorporar a una relacion
	 * de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de expedientes {@link se.tramites.InfoBExpediente}
	 * @throws SistemaTramitadorException
	 *             Caso de que se produzca un error obteniendo los expedientes a
	 *             partir del sistema de tramitación automatizado
	 * @throws NotAvailableException
	 *             Caso de que el sistema de tramitamitación del que es
	 *             necesario obtener los expedientes no soporte la funcionalidad
	 *             necesaria para suministrar la información requerida para la
	 *             incorporación de los expedientes al sistema de gestión de
	 *             archivo
	 */
	public List getExpedientesParaRelacion(String idRelacion)
			throws SistemaTramitadorException, NotAvailableException {
		RelacionEntregaVO relacion = _relacionEntregaDBEntity
				.getRelacionXId(idRelacion);
		if (relacion.getTipotransferencia() != TipoTransferencia.ORDINARIA
				.getIdentificador())
			throw new ArchivoModelException(
					getClass(),
					"getExpedientesParaRelacion",
					"Solo se pueden incorporar expedientes procedentes de sistemas de tramitacion a transferencias ordinarias");
		DetallePrevisionVO detallePrevision = _detallePrevisionDBEntity
				.selectRow(relacion.getIddetprevision());
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		SistemaTramitador sistemaTramitacion = null;
		String codigoSistemaTramitador = detallePrevision.getCodSistProductor();
		sistemaTramitacion = services
				.lookupSistemaTramitacion(codigoSistemaTramitador);
		List listaExpedientes = null;
		Calendar fechaInicioExpedientes = Calendar.getInstance();
		fechaInicioExpedientes.set(
				Integer.parseInt(detallePrevision.getAnoIniUdoc()), 0, 1);
		Calendar fechaFinExpedientes = Calendar.getInstance();
		fechaFinExpedientes.set(
				Integer.parseInt(detallePrevision.getAnoFinUdoc()), 11, 31);
		List expedientesIDList = sistemaTramitacion.recuperarIdsExpedientes(
				relacion.getIdprocedimiento(),
				fechaInicioExpedientes.getTime(),
				fechaFinExpedientes.getTime(),
				SistemaTramitador.ORDER_BY_NUM_EXP);
		if (expedientesIDList != null && expedientesIDList.size() > 0) {
			// No se presentan aquellos que ya hayan sido incluidos en la
			// relación de entrega
			if (!ListUtils.isEmpty(expedientesIDList)) {

				final List udocsEnRelacion = _unidadDocumentalDBEntity
						.fetchRowsByCodigoRelacionOrderByOrden(relacion.getId());

				// Obtener los ids
				final Collection idsEnRelacion = CollectionUtils.collect(
						udocsEnRelacion, new Transformer() {
							public Object transform(Object obj) {
								UnidadDocumentalVO vo = (UnidadDocumentalVO) obj;
								return vo.getNumeroExpediente();
							}
						});

				// Filtrar los expedientes por los que ya existan en la relación
				CollectionUtils.filter(expedientesIDList, new Predicate() {
					public boolean evaluate(Object obj) {
						String id = (String) obj;
						if (StringUtils.isNotEmpty(id)) {
							if ((idsEnRelacion != null)
									&& (idsEnRelacion.contains(id)))
								return false;
						}
						return true;
					}
				});
			}

			// Tampoco se incluyen los que ya hayan sido transferidos e
			// incorporados al cuadro de clasificación
			String[] expedienteIDs = (String[]) expedientesIDList
					.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			GestionUnidadDocumentalBI unidadDocumentalBI = services
					.lookupGestionUnidadDocumentalBI();

			List udocsExpedientes = unidadDocumentalBI
					.findNumerosExpediente(expedienteIDs);
			for (Iterator i = udocsExpedientes.iterator(); i.hasNext();)
				expedientesIDList.remove((String) i.next());

			listaExpedientes = sistemaTramitacion
					.recuperarInfoBExpedientes((String[]) expedientesIDList
							.toArray(ArrayUtils.EMPTY_STRING_ARRAY));

		} else if (logger.isDebugEnabled())
			logger.debug("El sistema tramitador no ha proporcionado ningun expediente para el procedimiento "
					+ relacion.getIdprocedimiento());
		return listaExpedientes;
	}

	/**
	 * Devuelve la lista de posibles gestores de una relacion
	 *
	 * @param relacionVO
	 *            Relacion para la que se buscaran posibles gestores
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getPosiblesGestores(RelacionEntregaVO relacionVO) {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionControlUsuariosBI controlAcceso = services
				.lookupGestionControlUsuariosBI();
		return controlAcceso.getGestoresTransferencia(relacionVO
				.getIdarchivoreceptor());
	}

	/**
	 * Incorpora a una relacion de entrega unidades documentales a partir de un
	 * conjunto de expedientes
	 *
	 * @param orden
	 *            Orden a partir del cual se van a insertar los diferentes
	 *            expedientes a la relación de entrega
	 * @param relacion
	 *            Relacion de entrega a la que se incorporarn las unidades
	 *            documentales
	 * @param expedientes
	 *            Lista de expedientes a incorporar {@link InfoBExpediente}.
	 *            Tras la finalización del proceso permanecen en esta lista
	 *            aquellos expedientes cuyo plazo de conservación ha vencido y
	 *            por tanto deben ser eliminados directamente en lugar de ser
	 *            incorporados en la transferencia
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporación de unidades documentales a la
	 *             relación de entrega no esté permitida
	 * @throws SistemaTramitadorException
	 *             Caso de error en la importación desde el sistema tramitador
	 *             de la información de los expedientes
	 * @throws NotAvailableException
	 *             Caso de que el sistema tramitador no ofrezca la funcionalidad
	 *             necesaria para la importación de la información necesaria
	 *             para la generación de las unidades documentales a partir de
	 *             los expedientes administrativos con los que se corresponden
	 */
	public int crearUnidadesDocumentales(Integer orden,
			RelacionEntregaVO relacion, List expedientes, int subtipo)
			throws ActionNotAllowedException, SistemaTramitadorException,
			NotAvailableException {

		int retorno = 0;

		if (relacion.getTipotransferencia() != TipoTransferencia.ORDINARIA
				.getIdentificador())
			throw new UncheckedArchivoException(
					"Solo es posible incorporar expedientes procedentes de sistemas automatizados a relaciones ordinarias");
		DetallePrevisionVO detallePrevision = _detallePrevisionDBEntity
				.selectRow(relacion.getIddetprevision());
		String codigoSistemaTramitador = detallePrevision.getCodSistProductor();

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

		SistemaTramitador sistemaTramitacion = SistemaTramitadorFactory
				.getConnector(codigoSistemaTramitador, params);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		ValoracionSerieVO valoracionSerie = valoracionBI
				.getValoracionDictaminada(relacion.getIdseriedestino());
		Date fechaLimiteConservacion = null;
		if (valoracionSerie != null) {
			int periodoConservacion = valoracionSerie.getAnosConservacion();
			Calendar limiteConservacion = Calendar.getInstance();
			limiteConservacion.setTime(DateUtils.getFechaActual());
			limiteConservacion.add(Calendar.YEAR, (-1) * periodoConservacion);
			fechaLimiteConservacion = limiteConservacion.getTime();
		}

		List expedientesFueraPeriodoConservacion = new ArrayList();
		InfoBExpediente infoExpediente = null;
		// InfoOrgano infoOrganoProductor = null;
		Expediente unExpediente = null;
		UnidadDocumentalVO udoc = null;

		// Establezco el orden, si es null los expedientes se insertaran al
		// final
		// Sino se insertaran de manera secuencial en la posicion seleccionada y
		// se desplazara el resto de unidades documentales.
		int nuevoOrden = 1;

		iniciarTransaccion();

		if (orden != null) {
			// Actualizar la posicion de las unidades documentales electronicas.
			_udocElectronicaDbEntity.incrementPosUdocSDF(relacion.getId(),
					orden.intValue(), expedientes.size());
			// Actualizar el orden de las unidades documentales seleccionadas y
			// posteriores.
			_unidadDocumentalDBEntity.incrementOrdenUdoc(relacion.getId(),
					orden.intValue(), expedientes.size());
			nuevoOrden = orden.intValue();
		} else {
			List udocsRelacion = _unidadDocumentalDBEntity
					.fetchRowsByCodigoRelacionOrderByOrden(relacion.getId());
			nuevoOrden = getNextOrden(udocsRelacion);
		}

		for (Iterator i = expedientes.iterator(); i.hasNext();) {
			infoExpediente = (InfoBExpediente) i.next();
			unExpediente = sistemaTramitacion
					.recuperarExpediente(infoExpediente.getId());
			if ((fechaLimiteConservacion != null)
					&& (unExpediente.getFechaFinalizacion() != null)
					&& (unExpediente.getFechaFinalizacion()
							.after(fechaLimiteConservacion)))
				expedientesFueraPeriodoConservacion.add(infoExpediente);
			udoc = new UnidadDocumentalVO(ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getFechaExtremaInicial(),
					ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaFinal(), ConfigConstants
							.getInstance().getSeparadorDefectoFechasRelacion());
			udoc.setNumeroExpediente(infoExpediente.getNumExp());
			udoc.setProductor(null, unExpediente.getNombreOrgProductor(),
					unExpediente.getIdOrgProductor());
			udoc.setFechaInicio(unExpediente.getFechaInicio());
			udoc.setFechaFin(unExpediente.getFechaFinalizacion());
			udoc.setAsunto(unExpediente.getAsunto());
			udoc.setSistemaProductor(detallePrevision.getCodSistProductor(),
					detallePrevision.getNombreSistProductor());
			udoc.setOrden(nuevoOrden++);
			List interesados = unExpediente.getInteresados();
			if (interesados != null)
				for (Iterator j = interesados.iterator(); j.hasNext();) {
					Interesado interesado = (Interesado) j.next();
					InteresadoVO vo = new InteresadoVO(interesado.getNombre(),
							interesado.getNumIdentidad(),
							interesado.getIdEnTerceros(), interesado.getRol());
					udoc.addInteresado(vo);
					if (interesado.esInteresadoPrincipal())
						udoc.setInteresadoPrincipal(vo);
				}
			List emplazamientos = unExpediente.getEmplazamientos();
			if (emplazamientos != null)
				for (Iterator j = emplazamientos.iterator(); j.hasNext();) {
					Emplazamiento emplazamiento = (Emplazamiento) j.next();
					udoc.addEmplazamiento(new Direccion(
							emplazamiento.getPais(), emplazamiento
									.getComunidad(),
							emplazamiento.getConcejo(), emplazamiento
									.getPoblacion(), emplazamiento
									.getLocalizacion(), emplazamiento
									.getValidado()));
				}
			List docsFisicos = unExpediente.getDocumentosFisicos();

			if (docsFisicos != null && docsFisicos.size() > 0) {
				for (Iterator j = docsFisicos.iterator(); j.hasNext();) {
					DocFisico documento = (DocFisico) j.next();
					udoc.addDocumento(new DocumentoVO(documento.getAsunto(),
							documento.getTipoDocumento()));
				}
				udoc.addSoporte(docsFisicos.size(), FORMATO_DOCUMENTO,
						SOPORTE_PAPEL);
			}
			List docsElectronicos = unExpediente.getDocumentosElectronicos();
			if (docsElectronicos != null && docsElectronicos.size() > 0) {
				for (Iterator j = docsElectronicos.iterator(); j.hasNext();) {
					DocElectronico documento = (DocElectronico) j.next();
					udoc.addDocumento(new DocumentoElectronicoVO(documento
							.getAsunto(), documento.getTipoDocumento(),
							documento.getRepositorio(), documento
									.getLocalizador(), documento.getExtension()));
				}
				udoc.addSoporte(docsElectronicos.size(), FORMATO_DOCUMENTO,
						SOPORTE_ELECTRONICO);

			}

			boolean insertar = false;

			if (relacion.isConErroresCotejo()) {
				if (relacion.isSinDocsFisicos()) {
					if (ListUtils.isEmpty(docsFisicos)
							&& (!ListUtils.isEmpty(docsElectronicos))) {
						insertar = true;
					} else {
						insertar = false;
					}
				}
			} else {
				insertar = true;
			}

			if (insertar) {
				nuevaUnidadDocumental(relacion, udoc, null, subtipo, 0);
			} else {
				retorno = ERROR_RELACION_SIN_DOCS_FISICOS;
			}
		}
		checkModificaRelacionRechazada(relacion);
		commit();
		expedientes.clear();
		expedientes.addAll(expedientesFueraPeriodoConservacion);
		return retorno;
	}

	public int getNextOrden(List udocsRelacion) {
		int orden = 1;
		if (udocsRelacion != null && udocsRelacion.size() > 0) {
			UnidadDocumentalVO unidadDocumentalVO = (UnidadDocumentalVO) udocsRelacion
					.get(udocsRelacion.size() - 1);
			orden = unidadDocumentalVO.getOrden();
			if (orden == IeciTdType.NULL_LONG_INTEGER)
				orden = 1;
			else
				orden++;
		}

		return orden;
	}

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List getUnidadesInstalacion(String idRelacion) {
		return getUnidadesInstalacion(idRelacion, false);
	}

	public List getUnidadesInstalacion(String idRelacion, boolean asignado) {
		RelacionEntregaVO relacion = _relacionEntregaDBEntity
				.getRelacionXId(idRelacion);
		if (relacion.isEntreArchivos()) {
			if (relacion.isRelacionConReencajado()) {
				return _uiReeaCRDBEntity.getByIdRelacion(idRelacion);
			} else {
				return _unidadInstalacionReeaDBEntity
						.fetchRowsByIdRelacion(idRelacion);
			}
		} else {
			if (asignado) {
				return _unidadInstalacionDBEntity
						.fetchRowsByIdRelacion(idRelacion,
								TipoUInstalacion.CREADAS.getIdentificador());
			} else {
				return _unidadInstalacionDBEntity.fetchRowsByIdRelacion(
						idRelacion, TipoUInstalacion.ALL.getIdentificador());
			}
		}
	}

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega.
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param ordenes
	 *            Órdenes.
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List getUnidadesInstalacion(String idRelacion,
			IntervalOptions ordenes) {
		return _unidadInstalacionDBEntity.fetchRowsByIdRelacion(idRelacion,
				ordenes);
	}

	/**
	 * Obtiene la unidades de instalación de una relacion de entrega
	 *
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalación
	 * @return Unidad de instalación
	 */
	public UnidadInstalacionVO getUnidadInstalacion(String idUnidadInstalacion) {
		return _unidadInstalacionDBEntity.fetchRowById(idUnidadInstalacion);
	}

	public UnidadInstalacionVO getUnidadInstalacion(String idUnidadInstalacion,
			int tipoTransferencia) {
		if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() != tipoTransferencia) {
			return _unidadInstalacionDBEntity.fetchRowById(idUnidadInstalacion);
		} else {
			return _unidadInstalacionReeaDBEntity
					.fetchRowById(idUnidadInstalacion);
		}
	}

	/**
	 * Establece la ubicación del fondo físico en la que será depositada la
	 * documentación incluída en una relación de entrega una vez finalizado el
	 * proceso de transferencia
	 *
	 * @param relacionEntrega
	 *            Relación de entrega
	 * @param idUbicacion
	 *            Identificador de ubicación del fondo físico manejado por el
	 *            sistema
	 */
	public void updateDestinoRelacion(RelacionEntregaVO relacionEntrega,
			String idUbicacion) throws ActionNotAllowedException {
		boolean permitidaModificacionUbicacion = false;
		int errorCode = -1;
		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionEntrega);
		if (relacionEntrega.getIsIngresoDirecto()) {
			// Si no hay seleccionado depósito comprobar que se puede
			// seleccionar una ubicación
			if (StringUtils.isEmpty(relacionEntrega.getIddeposito()))
				permitidaModificacionUbicacion = authorizationHelper
						.puedeSerEnviadoSeleccionarUbicacionIngreso(relacionEntrega);
			else
				// Comprobar que se puede modificar una ubicación
				permitidaModificacionUbicacion = authorizationHelper
						.permitidaModificacionUbicacion(relacionEntrega);
			errorCode = authorizationHelper.getErrorCode();
		} else {
			permitidaModificacionUbicacion = authorizationHelper
					.permitidaModificacionUbicacion(relacionEntrega);
			errorCode = authorizationHelper.getErrorCode();
		}
		if (permitidaModificacionUbicacion) {
			iniciarTransaccion();

			int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
					: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
			Locale locale = getServiceClient().getLocale();
			IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
					.crearPistaAuditoria(relacionEntrega, action, this);
			pistaAuditoria
					.addDetalleBasico(
							locale,
							relacionEntrega,
							TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_MODIFICAR_UBICACION);

			DepositoVO deposito = getServiceRepository()
					.lookupGestorEstructuraDepositoBI().getUbicacion(
							idUbicacion);

			pistaAuditoria.auditaModificacionDestinoRelacion(locale,
					deposito.getNombre());

			if (relacionEntrega.getReservadeposito() != ReservaPrevision.NO_RESERVADA
					.getIdentificador()
					&& relacionEntrega.getReservadeposito() != ReservaPrevision.NO_SE_HA_PODIDO
							.getIdentificador()) {
				if (relacionEntrega.getReservadeposito() == ReservaPrevision.RESERVADA
						.getIdentificador()) {
					ServiceRepository services = ServiceRepository
							.getInstance(getServiceSession());
					GestorEstructuraDepositoBI depositoBI = services
							.lookupGestorEstructuraDepositoBI();
					depositoBI.liberarReserva(relacionEntrega.getId());
				}
				_relacionEntregaDBEntity.updateEstadoReserva(
						relacionEntrega.getId(),
						ReservaPrevision.NO_RESERVADA.getIdentificador(), null,
						null);
			}
			_relacionEntregaDBEntity.updateUbicacion(relacionEntrega.getId(),
					false, idUbicacion);
			commit();
		} else
			throw new ActionNotAllowedException(errorCode);
	}

	/**
	 * Obtiene las unidades documentales incluidas en una unidad de instalcion
	 *
	 * @param id
	 *            Identificador de unidad de instalcion
	 * @return Lista de partes de unidad documental
	 *         {@link ParteUnidadDocumentalVO}
	 */
	public List getUdocsEnUI(String idUnidadInstalacion) {
		return _udocEnUIDBEntity.fetchRowsByUInstalacion(idUnidadInstalacion);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getUdocsEnUIs(java.lang.String[])
	 */
	public Map getUdocsEnUIs(String[] idsUnidadInstalacion) {

		Map mapUdocsEnUi = new HashMap();
		if ((idsUnidadInstalacion == null)
				|| (idsUnidadInstalacion.length == 0)) {
			return mapUdocsEnUi;
		}

		List ltUdocsEnUi = _udocEnUIDBEntity
				.fetchRowsByUInstalacion(idsUnidadInstalacion);
		if (!ListUtils.isEmpty(ltUdocsEnUi)) {
			Iterator it = ltUdocsEnUi.iterator();
			while (it.hasNext()) {
				IParteUnidadDocumentalVO parteUdoc = (IParteUnidadDocumentalVO) it
						.next();
				if (mapUdocsEnUi.containsKey(parteUdoc.getIdUinstalacionRe())) {
					List ltUdocs = (List) mapUdocsEnUi.get(parteUdoc
							.getIdUinstalacionRe());
					ltUdocs.add(parteUdoc);
				} else {
					List ltUdocs = new ArrayList();
					ltUdocs.add(parteUdoc);
					mapUdocsEnUi.put(parteUdoc.getIdUinstalacionRe(), ltUdocs);
				}
			}
		}
		return mapUdocsEnUi;
	}

	public List getPartesUnidadDocumental(UnidadDocumentalVO unidadDocumental) {
		List partesUdoc = ParteUnidadDocumentalVO
				.getPartesUdoc(unidadDocumental);
		List partesAsignadas = _udocEnUIDBEntity
				.fetchRowsByUdoc(unidadDocumental.getId());
		ParteUnidadDocumentalVO parteUdoc = null;
		for (Iterator i = partesAsignadas.iterator(); i.hasNext();) {
			parteUdoc = (ParteUnidadDocumentalVO) i.next();

			// Añadir la signatura de la unidad de instalación a la parte de
			// unidad documental
			UnidadInstalacionVO unidadInstalacionVO = _unidadInstalacionDBEntity
					.fetchRowById(parteUdoc.getIdUinstalacionRe());
			parteUdoc.setSignaturaUI(unidadInstalacionVO.getSignaturaUI());
			partesUdoc.set(parteUdoc.getNumParteUdoc() - 1, parteUdoc);
		}
		return partesUdoc;
	}

	public List getRelacionesAceptadas(String idFondo) {
		return (List) _relacionEntregaDBEntity.getRelacionXFondo(idFondo,
				new int[] { EstadoREntrega.RECIBIDA.getIdentificador(),
						EstadoREntrega.SIGNATURIZADA.getIdentificador(),
						EstadoREntrega.COTEJADA.getIdentificador(),
						EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
						EstadoREntrega.CORREGIDA_ERRORES.getIdentificador(),
						EstadoREntrega.VALIDADA.getIdentificador() });
	}

	/**
	 * Obtiene la lista de los posibles roles que un interesado puede jugar en
	 * un expediente
	 *
	 * @return Lista de String con la descripción de los roles
	 */
	public List getListaRolesInteresado() {
		ConfiguracionTransferencias config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionTransferencias();

		String tablaValidacionRoles = config
				.getIdTablaValidacionRolInteresado();
		if (tablaValidacionRoles != null) {
			List valoresTablaValidacion = _validacionDBEntity
					.getValoresTablaValidacion(tablaValidacionRoles);
			CollectionUtils.transform(valoresTablaValidacion,
					new Transformer() {
						public Object transform(Object obj) {
							return ((TextoTablaValidacionVO) obj).getValor();
						}
					});
			return valoresTablaValidacion;
		} else
			throw new ArchivoModelException(
					getClass(),
					"getListaRolesInteresado",
					"No se ha configurado identificador de tabla de validación donde se guardan los valores de rol de interesado");
	}

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega.
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacion(String idRelacion) {
		return _unidadInstalacionDBEntity.getUbicacionesRelacion(idRelacion);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getUbicacionesRelacionReencajado(java.lang.String)
	 */
	public List getUbicacionesRelacionReencajado(String idRelacion) {
		return _unidadInstalacionDBEntity
				.getUbicacionesRelacionReencajado(idRelacion);
	}

	public List getUbicacionesRelacionOuterJoin(String idRelacion,
			IntervalOptions ordenes, String idDepositoDestino) {
		return _unidadInstalacionDBEntity.getUbicacionesRelacionOuterJoin(
				idRelacion, ordenes, idDepositoDestino);
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro.
	 *
	 * @param idElemento
	 *            Identificador del elemento del cuadro.
	 * @param tipoElemento
	 *            Tipo de elemento del cuadro (
	 *            {@link ElementoCuadroClasificacion}).
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByElemento(String idElemento, int tipoElemento,
			PageInfo pageInfo) throws TooManyResultsException {
		List relaciones = null;

		switch (tipoElemento) {
		case ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL:
			relaciones = _relacionEntregaDBEntity.getRelacionesXUdoc(
					idElemento, pageInfo);
			break;

		case ElementoCuadroClasificacion.TIPO_SERIE:
			relaciones = _relacionEntregaDBEntity.getRelacionesXSerie(
					idElemento, pageInfo);
			break;

		case ElementoCuadroClasificacion.TIPO_CL_SERIES:
			relaciones = _relacionEntregaDBEntity
					.getRelacionesXClasificadorSeries(idElemento, pageInfo);
			break;

		case ElementoCuadroClasificacion.TIPO_FONDO:
			relaciones = _relacionEntregaDBEntity.getRelacionesXFondo(
					idElemento, pageInfo);
			break;

		default:
			relaciones = new ArrayList();
		}

		return relaciones;
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo unidad documental que es fruto de la división de una fracción de
	 * serie
	 *
	 * @param idUDoc
	 *            Identificador del elemento del cuadro.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByUDocFS(String idUDoc)
			throws TooManyResultsException {
		List relaciones = null;

		List udocsEnUIDeposito = _udocEnUIDepositoDBEntity
				.getPartesUdocByIDElementoCF(idUDoc);

		// Si aquí no se encuentra nada, es que se están sacando las relaciones
		// de entrega desde la relación o división de fracción de serie
		// en lugar de desde la ficha del cuadro, por lo que el idUDoc que llega
		// es el idUDoc en relación o en división de fracción de serie
		if (udocsEnUIDeposito == null || udocsEnUIDeposito.size() == 0)
			udocsEnUIDeposito = _udocEnUIDepositoDBEntity
					.getPartesUdocByIDUdocEnDivisionFS(idUDoc);

		if (udocsEnUIDeposito != null && udocsEnUIDeposito.size() > 0) {
			UDocEnUiDepositoVO udocEnUIDepositoVO = (UDocEnUiDepositoVO) udocsEnUIDeposito
					.get(0);
			UDocEnFraccionSerieVO udocEnFS = _udocEnDivisionFSDbEntity
					.getUDocEnDivisionFSXId(udocEnUIDepositoVO.getIdudocre());
			if (udocEnFS == null)
				return new ArrayList();
			DivisionFraccionSerieVO divisionFS = _divisionFSDbEntity
					.getDivisionFSXId(udocEnFS.getIdFS());
			relaciones = _relacionEntregaDBEntity.getRelacionesXIdFSEnREntrega(
					divisionFS.getIdFSEnREntrega(), null);
		}

		return relaciones;
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo unidad documental que provienen de una relación de entrega con ficha
	 * y a cuya ficha se está accediendo desde esa relación
	 *
	 * @param idUDoc
	 *            Identificador del elemento del cuadro.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByUDocRE(String idUDoc)
			throws TooManyResultsException {
		List relaciones = null;

		// Si aquí no se encuentra nada, es que se están sacando las relaciones
		// de entrega desde la relación o división de fracción de serie
		// en lugar de desde la ficha del cuadro, por lo que el idUDoc que llega
		// es el idUDoc en relación
		// Si aquí tampoco se encuentra nada, es buscamos en las unidades
		// documentales en división de fracción de serie

		relaciones = _relacionEntregaDBEntity.getRelacionesXUdoc(idUDoc, null);

		if (relaciones == null || relaciones.size() == 0) {
			List udocsEnUIDeposito = _udocEnUIDepositoDBEntity
					.getPartesUdocByIDUdocEnRelacionEntrega(idUDoc);

			if (udocsEnUIDeposito != null && udocsEnUIDeposito.size() > 0) {
				UDocEnUiDepositoVO udocEnUIDepositoVO = (UDocEnUiDepositoVO) udocsEnUIDeposito
						.get(0);
				relaciones = _relacionEntregaDBEntity.getRelacionesXUdoc(
						udocEnUIDepositoVO.getIdunidaddoc(), null);
			}
		}

		return relaciones;
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo fracción de serie , que puede estar dividida o no y a cuya ficha se
	 * está accediendo desde la relación de entrega
	 *
	 * @param idFS
	 *            Identificador de la fracción de serie dividida
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByFSDividida(String idFS)
			throws TooManyResultsException {
		List relaciones = null;

		relaciones = _relacionEntregaDBEntity.getRelacionesXIdFSEnREntrega(
				idFS, null);

		if (relaciones == null || relaciones.size() == 0) {
			DivisionFraccionSerieVO divisionFS = _divisionFSDbEntity
					.getDivisionFSXId(idFS);

			if (divisionFS != null)
				relaciones = _relacionEntregaDBEntity
						.getRelacionesXIdFSEnREntrega(
								divisionFS.getIdFSEnREntrega(), null);
		}

		return relaciones;
	}

	/**
	 * Obtiene los estados de las relaciones.
	 *
	 * @return Estados de las relaciones.
	 */
	public List getEstadosRelaciones() {
		List estados = new ArrayList();
		Locale locale = getServiceClient().getLocale();
		estados.add(new TypeDescVO(1, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".1",
				locale)));
		estados.add(new TypeDescVO(2, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".2",
				locale)));
		estados.add(new TypeDescVO(3, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".3",
				locale)));
		estados.add(new TypeDescVO(4, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".4",
				locale)));
		estados.add(new TypeDescVO(5, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".5",
				locale)));
		estados.add(new TypeDescVO(6, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".6",
				locale)));
		estados.add(new TypeDescVO(7, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".7",
				locale)));
		estados.add(new TypeDescVO(8, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".8",
				locale)));

		if (ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo()
				.getConfiguracionWsTransferencias().isGestionarAutomatizadas()) {
			estados.add(new TypeDescVO(EstadoREntrega.VALIDADA_AUTOMATIZADA
					.getIdentificador(), Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "."
							+ EstadoREntrega.VALIDADA_AUTOMATIZADA
									.getIdentificador(), locale)));
		}

		return estados;
	}

	/**
	 * Obtiene los estados de las relaciones.
	 *
	 * @return Estados de las relaciones.
	 */
	public List getEstadosIngresosDirectos() {
		List estados = new ArrayList();
		Locale locale = getServiceClient().getLocale();
		estados.add(new TypeDescVO(1, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".1",
				locale)));
		estados.add(new TypeDescVO(7, Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + ".7",
				locale)));
		return estados;
	}

	/**
	 * Busca las relaciones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de relaciones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getRelaciones(BusquedaRelacionesVO vo)
			throws TooManyResultsException {
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(TransferenciasSecurityManager.GESTION_TRANSFERENCIAS);

		// Información del usuario conectado
		ServiceClient client = getServiceClient();

		pistaAuditoria.auditaBusquedaRelaciones(locale, vo);

		if ((!client.hasAnyPermission(new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.CONSULTA_TOTAL_SISTEMA }))) {

			// Relaciones de los archivos a los que pertenece el usuario
			if (ArrayUtils.isEmpty(vo.getArchivosReceptores())
					&& client
							.hasAnyPermission(new String[] {
									AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
									AppPermissions.UBICACION_RELACIONES_ENTREGA,
									AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO })) {
				vo.setArchivosReceptores((String[]) client
						.getCustodyArchiveList().toArray(
								new String[client.getCustodyArchiveList()
										.size()]));
			}

			if (client
					.hasPermission(AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE)) {
				// Búsqueda en los órganos del usuario y sus descendientes
				List organos = new ArrayList(
						client.getDependentOrganizationList());
				if (client.getOrganization() != null)
					organos.add(0, client.getOrganization());

				String[] idsOrganos = new String[organos.size()];
				for (int i = 0; i < organos.size(); i++)
					idsOrganos[i] = ((CAOrganoVO) organos.get(i)).getIdOrg();

				vo.setOrganosUsuario(idsOrganos);
			}

			if (client
					.hasPermission(AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR)) {
				vo.setIdGestorArchivo(client.getId());
			}

		}

		// Relaciones del usuario
		vo.setIdGestor(client.getId());

		List relaciones = _relacionEntregaDBEntity.getRelaciones(vo);

		if (relaciones != null) {
			Iterator it = relaciones.iterator();
			IRelacionAuthorizationHelper helper = null;
			while (it.hasNext()) {
				RelacionEntregaVO relacion = (RelacionEntregaVO) it.next();
				helper = getAuthorizationHelper(relacion.getTipotransferencia());
				helper.configureRelacionEntregaBusqueda(relacion);
			}
		}

		return relaciones;
	}

	/**
	 * Obtiene las relaciones cedibles que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de relaciones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getRelacionesCedibles(BusquedaRelacionesVO vo)
			throws TooManyResultsException {
		// Comprobar permisos
		checkPermission(TransferenciasSecurityManager.GESTION_TRANSFERENCIAS);

		return _relacionEntregaDBEntity.getRelaciones(vo);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#getIngresosDirectosCedibles(
	 * transferencias.vos.BusquedaRelacionesVO)
	 */
	public List getIngresosDirectosCedibles(BusquedaRelacionesVO vo)
			throws TooManyResultsException {
		// Comprobar permisos
		checkPermission(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS);

		vo.setIngresoDirecto(true);
		return _relacionEntregaDBEntity.getRelaciones(vo);
	}

	/**
	 * Obtiene la lista de gestores en archivo con relaciones de entrega.
	 *
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresArchivoConRelaciones() {
		return _relacionEntregaDBEntity.getGestoresArchivoConRelaciones();
	}

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia) {
		return _relacionEntregaDBEntity.getGestoresOrgRemitenteConRelaciones(
				idOrgano, tiposTransferencia);
	}

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @param idsExcluir
	 *            Ids de gestores a excluir
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia, String[] idsExcluir) {
		return _relacionEntregaDBEntity.getGestoresOrgRemitenteConRelaciones(
				idOrgano, tiposTransferencia, idsExcluir);
	}

	/**
	 * Asigna una lista de relaciones de entrega a un gestor de archivo.
	 *
	 * @param idsRelaciones
	 *            Lista de identificadores de relaciones de entrega.
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws RelacionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de las
	 *             relaciones de entrega.
	 */
	public UsuarioVO asignarRelacionesAGestorArchivo(String[] idsRelaciones,
			String idGestor) throws PrevisionOperacionNoPermitidaException {

		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos del usuario conectado
		checkPermission(TransferenciasSecurityManager.CESION_CONTROL_RELACIONES_EN_ARCHIVO);

		if (ArrayUtils.isEmpty(idsRelaciones))
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_HAY_RELACIONES_SELECCIONADAS);

		// Obtener la información del usuario
		GestionControlUsuariosBI gcu = getGestionControlUsusarios();
		UsuarioVO usuario = gcu.getUsuario(idGestor);
		if (usuario == null)
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_EXISTE_GESTOR_ESPECIFICADO);

		// Cesión del control de la previsión
		iniciarTransaccion();
		for (int i = 0; i < idsRelaciones.length; i++) {
			RelacionEntregaVO relacion = getRelacionXIdRelacion(idsRelaciones[i]);

			// Auditoría
			pistaAuditoria.addDetalleVacio();
			pistaAuditoria.addDetalleBasico(locale, relacion);
			UsuarioVO usuarioAnt = gcu.getUsuario(relacion
					.getIdusrgestorarchivorec());

			pistaAuditoria.auditaCesionControl(locale, usuarioAnt, usuario);

			_relacionEntregaDBEntity.updateUsrGestorArchivo(
					new String[] { idsRelaciones[i] }, idGestor);
		}
		commit();

		return usuario;
	}

	/**
	 * Asigna una lista de relaciones de entrega a un gestor de órgano
	 * remitente.
	 *
	 * @param idsRelaciones
	 *            Lista de identificadores de relaciones de entrega.
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws RelacionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de las
	 *             relaciones de entrega.
	 */
	public UsuarioVO asignarRelacionesAGestorOrgRemitente(
			String[] idsRelaciones, String idGestor)
			throws PrevisionOperacionNoPermitidaException {
		// Auditoría
		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos del usuario conectado
		checkPermission(TransferenciasSecurityManager.CESION_CONTROL_RELACIONES_EN_ORG_REMITENTE);

		if (ArrayUtils.isEmpty(idsRelaciones))
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_HAY_RELACIONES_SELECCIONADAS);

		// Obtener la información del usuario
		GestionControlUsuariosBI gcu = getGestionControlUsusarios();
		UsuarioVO usuario = gcu.getUsuario(idGestor);
		if (usuario == null)
			throw new PrevisionOperacionNoPermitidaException(
					ArchivoErrorCodes.ERROR_NO_EXISTE_GESTOR_ESPECIFICADO);

		// Cesión del control de la previsión
		iniciarTransaccion();
		// DataLoggingEvent loggingEventData;
		for (int i = 0; i < idsRelaciones.length; i++) {
			RelacionEntregaVO relacion = getRelacionXIdRelacion(idsRelaciones[i]);

			// Auditoría
			pistaAuditoria.addDetalleVacio();
			pistaAuditoria.addDetalleBasico(locale, relacion);

			UsuarioVO usuarioAnt = gcu.getUsuario(relacion.getIdusrgestorrem());
			pistaAuditoria.auditaCesionControl(locale, usuarioAnt, usuario);

			_relacionEntregaDBEntity.updateUsrGestorRem(
					new String[] { idsRelaciones[i] }, idGestor);
		}
		commit();

		return usuario;
	}

	public List getExpedientesSinCaja(String idRelacion) {
		return _unidadDocumentalDBEntity.getUdocsSinCaja(idRelacion);
	}

	public UnidadInstalacionVO nuevaUnidadInstalacion(
			RelacionEntregaVO relacionEntrega, int numeroCaja,
			String signatura, String iduiubicada) {
		iniciarTransaccion();
		UnidadInstalacionVO unidadInstalacion = new UnidadInstalacionVO();
		unidadInstalacion.setSignaturaUI(signatura);
		unidadInstalacion.setEstadoCotejo(EstadoCotejo.PENDIENTE
				.getIdentificador());
		unidadInstalacion.setIdRelEntrega(relacionEntrega.getId());
		unidadInstalacion.setOrden(numeroCaja);
		unidadInstalacion.setIdFormato(relacionEntrega.getIdformatoui());
		unidadInstalacion.setIduiubicada(iduiubicada);

		_unidadInstalacionDBEntity.insertRow(unidadInstalacion);
		checkModificaRelacionRechazada(relacionEntrega);
		commit();
		return unidadInstalacion;
	}

	private boolean uDocsRETienenMismaUbicacionHueco(String idRelacion,
			String idUbicacion) {

		boolean mismaUbicacion = true;

		List uisRelacionAsignadas = _unidadInstalacionDBEntity
				.fetchRowsByIdRelacion(idRelacion,
						TipoUInstalacion.ASIGNADAS.getIdentificador());

		if (uisRelacionAsignadas != null && uisRelacionAsignadas.size() > 0) {
			Iterator it = uisRelacionAsignadas.iterator();
			while (it.hasNext()) {
				UnidadInstalacionVO uiAsignada = (UnidadInstalacionVO) it
						.next();
				HuecoVO hueco = _huecoDBEntity.getHuecoUInstalacion(uiAsignada
						.getIduiubicada());
				if (!idUbicacion.equals(hueco.getIddeposito())) {
					mismaUbicacion = false;
					break;
				}
			}
		}

		return mismaUbicacion;
	}

	/**
	 * Se crea una caja con signatura indicando el iduiubicada. Si iduiubicada
	 * == null se crea una caja nueva con signatura Sino se crea una caja
	 * (asignada) con signatura y el iduiubicada al que pertenece (en altas de
	 * unidades documentales).
	 */
	public UnidadInstalacionVO crearCajaSignaturada(String idRelacion,
			String idArchivoReceptor, String[] udocIDs, String signatura,
			boolean asignando, String formato)
			throws RelacionOperacionNoPermitidaException {

		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionEntrega
				.getIdarchivoreceptor());

		if (!asignando) {

			String idUbicacion = relacionEntrega.getIddeposito();

			if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
					.getIdentificador()) {
				// Comprobar que el hueco asociado a la signatura esté libre
				// para el archivo indicado
				HuecoVO huecoVO = SignaturaUtil.getHuecoAsociadoASignatura(
						signatura, idArchivoReceptor, _huecoDBEntity);
				if (huecoVO == null)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO);
				if ((huecoVO != null)
						&& (!HuecoVO.LIBRE_STATE.equals(huecoVO.getEstado()))) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO);
				}
				if ((huecoVO != null)
						&& (!huecoVO.getIdformato().equals(
								relacionEntrega.getIdformatoui()))) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION);
				}
				if ((huecoVO != null) && (StringUtils.isNotEmpty(idUbicacion))
						&& (!idUbicacion.equals(huecoVO.getIddeposito()))) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);
				}
				if (isSignaturaAsociadaHuecoUtilizadaRelacion(idRelacion, null,
						signatura)) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION);
				}
			} else {
				// Comprobar que la signatura no está repetida
				if (SignaturaUtil.existeSignatura(signatura, idArchivoReceptor,
						_unidadInstalacionDBEntity,
						_unidadInstalacionReeaDBEntity, _uiReeaCRDBEntity,
						_huecoDBEntity, ConfigConstants.getInstance()
								.getSignaturacionPorArchivo())) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA);
				}
			}
			return crearCaja(idRelacion, udocIDs, signatura);
		} else {
			// Obtengo el hueco, si es null significa que no existe ninguno con
			// esa signatura en todos los depositos pertenecientes al archivo
			// recector

			HuecoVO hueco = _huecoDBEntity.getHuecoBySignaturaInDeposito(
					signatura, idArchivoReceptor);
			if (hueco != null) {
				// Si el formato es multidocumento entonces lo insertamos, sino
				// error
				String idFormato = hueco.getIdformato();
				if (_formatoDBEntity.getFormatoById(idFormato).isMultidoc()) {
					if (idFormato.equals(formato)) {
						// Obtener la unidad de instalación asociada al hueco
						UInsDepositoVO uinsDepositoVO = _uInstalacionDepositoDBEntity
								.getUInstDepositoVOXIdEnDeposito(hueco
										.getIduinstalacion());
						if (!uinsDepositoVO.isBloqueada()) {
							if (checkUdocsEnUIEnMismoFondo(
									hueco.getIduinstalacion(),
									relacionEntrega.getIdfondodestino())) {
								boolean crearCaja = true;
								// Error al crear la caja si estamos en un alta
								// y vamos a asociar una caja de distinta
								// ubicación a la de
								// las unidades que ya tenemos asociadas o
								// creadas estando configurada en el archivo en
								// el que se está
								// realizando el alta la signaturación asociada
								// a hueco
								if (relacionEntrega.getIsIngresoDirecto()
										&& archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
												.getIdentificador())
									crearCaja = uDocsRETienenMismaUbicacionHueco(
											idRelacion, hueco.getIddeposito());
								if (crearCaja)
									return crearCaja(idRelacion, udocIDs,
											signatura,
											hueco.getIduinstalacion());
								else
									throw new RelacionOperacionNoPermitidaException(
											RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);
							} else
								throw new RelacionOperacionNoPermitidaException(
										RelacionOperacionNoPermitidaException.XLA_CAJA_A_ASIGNAR_CONTIENE_UNIDADES_CON_DISTINTO_FONDO);
						} else {
							Locale locale = getServiceClient().getLocale();
							int[] marcasCaja = MarcaUtil
									.obtenerMarcas(uinsDepositoVO
											.getMarcasBloqueo());
							String causaError = Messages
									.getTextoMessageBloqueoPorMarcas(
											locale,
											marcasCaja,
											MarcaUInstalacionConstants.marcas,
											Constants.ERRORS_RELACIONES_NO_ES_POSIBLE_ASIGNAR_CAJA_POR_ESTAR_BLOQUEADA,
											MarcaUInstalacionConstants.KEY_PREFIJO_MESSAGES_MARCAS_UINSTALACION);

							throw new RelacionOperacionNoPermitidaException(
									RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_ASIGNAR_CAJA_POR_ESTAR_BLOQUEADA,
									causaError);
						}
					} else
						throw new RelacionOperacionNoPermitidaException(
								RelacionOperacionNoPermitidaException.XEL_FORMATO_CAJA_NO_FORMATO_RELACION);
				} else
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XLA_UNIDAD_INSTALACION_NO_ES_MULTIDOCUMENTO);
			} else
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.X_SIGNATURAUI_NO_EXISTE);
		}
	}

	/**
	 * Permite comprobar si todas las unidades documentales de una unidad de
	 * instalación pertenecen al fondo pasado por parámetro
	 *
	 * @param idUinstalacion
	 *            Identificador de la unidad de instalación
	 * @param idFondo
	 *            Identificador del fondo
	 * @return <b>true</b> Si las unidades pertenecen al mismo fondo<br>
	 *         <b>false</b> Si las unidades pertenecen a distintos fondos
	 */
	private boolean checkUdocsEnUIEnMismoFondo(String idUinstalacion,
			String idFondo) {
		List ltUdocs = _udocEnUIDepositoDBEntity
				.getUDocsVOXIdUInstalacion(idUinstalacion);

		CollectionUtils.transform(ltUdocs, new Transformer() {

			public Object transform(Object obj) {
				UDocEnUiDepositoVO udocDepositoVO = (UDocEnUiDepositoVO) obj;
				return udocDepositoVO.getIdunidaddoc();
			}
		});

		return _elementoCuadroClasificacionDbEntity.checkUdocsEnMismoFondo(
				ltUdocs, idFondo);
	}

	/**
	 * Método para comprobar si una signatura ya está utilizada en la relacion
	 * de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relacion
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalación a excluir en la
	 *            comprobación
	 * @param signatura
	 *            Signatura a comprobar
	 *
	 * @return si la signatura asociada al hueco está utilizada en la relación
	 */
	private boolean isSignaturaAsociadaHuecoUtilizadaRelacion(
			String idRelacion, String idUnidadInstalacion, String signatura) {
		// Comprobar que la signatura no esté utilizada en la relación de
		// entrega
		List unidadesInstalacion = _unidadInstalacionDBEntity
				.fetchRowsByIdRelacion(idRelacion);

		boolean signaturaAsociadaHuecoUtilizadaRelacion = false;
		if (unidadesInstalacion != null) {
			ListIterator it = unidadesInstalacion.listIterator();
			while (it.hasNext()) {
				UnidadInstalacionVO unidadInstalacionVO = (UnidadInstalacionVO) it
						.next();
				if (signatura.equals(unidadInstalacionVO.getSignaturaUI())
						&& (!unidadInstalacionVO.getId().equals(
								idUnidadInstalacion)))
					signaturaAsociadaHuecoUtilizadaRelacion = true;
			}
		}

		return signaturaAsociadaHuecoUtilizadaRelacion;
	}

	public UnidadInstalacionVO crearCajaSinSignatura(String idRelacion,
			String[] udocIDs) {
		return crearCaja(idRelacion, udocIDs, null);
	}

	private UnidadInstalacionVO crearCaja(String idRelacion, String[] udocIDs,
			String signatura, String iduiubicada) {
		int maxPosUdocEnUI = 0;

		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionEntrega
				.getIdarchivoreceptor());
		UnidadInstalacionVO ui = null;

		iniciarTransaccion();

		if (signatura == null) {
			int numeroCaja = _unidadInstalacionDBEntity
					.maxOrdenUIsRelacion(idRelacion) + 1;
			ui = nuevaUnidadInstalacion(relacionEntrega, numeroCaja, signatura,
					iduiubicada);
		} else {
			// Obtengo la unidad de instalacion por la signatura
			ui = SignaturaUtil.obtenerUIREporSignatura(relacionEntrega.getId(),
					signatura, relacionEntrega.getIdarchivoreceptor(),
					_unidadInstalacionDBEntity, _unidadInstalacionReeaDBEntity);
			// Si existe entonces debemos asociar la unidad documental a la caja
			// existente y NO crear una nueva
			if (ui == null) {
				int numeroCaja = _unidadInstalacionDBEntity
						.maxOrdenUIsRelacion(idRelacion) + 1;
				ui = nuevaUnidadInstalacion(relacionEntrega, numeroCaja,
						signatura, iduiubicada);

				if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
						.getIdentificador()
						&& !relacionEntrega.getIsIngresoDirecto()) {
					// Marcar el hueco asociado a la signatura como reservado
					HuecoVO huecoVO = SignaturaUtil.getHuecoAsociadoASignatura(
							signatura, relacionEntrega.getIdarchivoreceptor(),
							_huecoDBEntity);

					_relacionEntregaDBEntity.updateUbicacion(idRelacion, false,
							huecoVO.getIddeposito());
				}
			} else {
				maxPosUdocEnUI = _udocEnUIDBEntity.maxPosUdocEnUI(ui.getId(),
						idRelacion);
			}
		}

		int nUdocsEnCaja = udocIDs.length;
		ParteUnidadDocumentalVO udocEnUI = null;
		for (int i = 0; i < nUdocsEnCaja; i++) {
			udocEnUI = new ParteUnidadDocumentalVO();
			udocEnUI.setIdRelentrega(idRelacion);
			udocEnUI.setIdUnidadDoc(udocIDs[i]);
			udocEnUI.setIdUinstalacionRe(ui.getId());
			// Si es una UI creada sigue igual, pero si es asociada tenemos que
			// coger la máxima posicion udoc en ui
			if (iduiubicada == null)
				udocEnUI.setPosUdocEnUI(i + 1);
			else {
				udocEnUI.setPosUdocEnUI(maxPosUdocEnUI + i + 1);
				// Si además estamos en un alta de unidades sobre un archivo con
				// signaturación asociada a hueco, tenemos que establecer ya
				// la signatura de la unidad documental
				if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
						.getIdentificador()
						&& relacionEntrega.getIsIngresoDirecto()) {
					FormatoHuecoVO formatoUI = _formatoDBEntity
							.loadFormato(relacionEntrega.getIdformatoui());
					udocEnUI.setSignaturaUdoc(SignaturaUtil.getSignaturaUdoc(
							signatura, formatoUI, udocEnUI));
				}
			}

			int numeroPartesUdoc = _udocEnUIDBEntity
					.countPartesUdoc(udocIDs[i]);
			udocEnUI.setNumParteUdoc(numeroPartesUdoc + 1);
			// actualizar numpartes de udoc
			_unidadDocumentalDBEntity.updateNumPartesUdoc(udocIDs[i],
					numeroPartesUdoc + 1);
			udocEnUI.setUdocCompleta(numeroPartesUdoc == 0 ? Constants.TRUE_STRING
					: Constants.FALSE_STRING);

			udocEnUI.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());
			_udocEnUIDBEntity.addUdocToUI(udocEnUI);
		}
		checkModificaRelacionRechazada(relacionEntrega);
		commit();
		return ui;
	}

	private UnidadInstalacionVO crearCaja(String idRelacion, String[] udocIDs,
			String signatura) {
		return crearCaja(idRelacion, udocIDs, signatura, null);
	}

	public List getUdocs(String idRelacion, boolean tieneDescripcion) {
		try {
			return getUdocs(idRelacion, null, tieneDescripcion);
		} catch (TooManyResultsException e) {
			logger.error(e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsConDocumentosFisicos(java
	 * .lang.String)
	 */
	public List getUdocsConDocumentosFisicos(String idRelacion) {
		try {
			return getUdocsConDocumentosFisicos(idRelacion, null);
		} catch (TooManyResultsException e) {
			logger.error(e);
			return null;
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsConDocumentosElectronicos
	 * (java.lang.String)
	 */
	public List getUdocsConDocumentosElectronicos(String idRelacion) {
		try {
			return getUdocsConDocumentosElectronicos(idRelacion, null);
		} catch (TooManyResultsException e) {
			logger.error(e);
			return null;
		}
	}

	public void removeUdocsFromUi(int[] posiciones, String unidadInstalacionID)
			throws RelacionOperacionNoPermitidaException {

		iniciarTransaccion();

		checkIsRemovableFromUI(unidadInstalacionID, posiciones);

		// borro todo
		for (int i = 0; i < posiciones.length; i++) {
			_udocEnUIDBEntity.deleteUdocFromUI(unidadInstalacionID,
					posiciones[i]);

		}

		RelacionEntregaVO relacion = null;
		relacion = getRelacionXIdRelacion(getUnidadInstalacion(
				unidadInstalacionID).getIdRelEntrega());

		// renumero ps dentro de la caja
		List partesUdocsEnUi = _udocEnUIDBEntity
				.fetchRowsByUInstalacion(unidadInstalacionID);
		if (partesUdocsEnUi != null) {
			int newPos = 1;
			for (Iterator itPartesUdocEnUi = partesUdocsEnUi.iterator(); itPartesUdocEnUi
					.hasNext();) {
				IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdocEnUi
						.next();
				if (newPos != aPart.getPosUdocEnUI()) {
					_udocEnUIDBEntity
							.updatePosicionEnCaja(aPart.getIdUnidadDoc(),
									unidadInstalacionID, newPos);
				}
				newPos++;
			}

			// si la caja era con errores miro a ver si la caja sigue teneidno
			// udocs con errores
			if (partesUdocsEnUi.size() > 0) {
				IParteUnidadDocumentalVO firstPart = (IParteUnidadDocumentalVO) partesUdocsEnUi
						.get(0);
				relacion = getRelacionXIdRelacion(firstPart.getIdRelentrega());
				if (relacion != null && relacion.isConErroresCotejo()) {
					boolean hayErrores = false;
					for (Iterator itPartesUdocEnUi = partesUdocsEnUi.iterator(); itPartesUdocEnUi
							.hasNext();) {
						IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdocEnUi
								.next();
						if (aPart.isConErrores()) {
							hayErrores = true;
							break;
						}
					}
					if (hayErrores)
						_unidadInstalacionDBEntity.updateFieldEstado(
								unidadInstalacionID,
								EstadoCotejo.ERRORES.getIdentificador());
					else
						_unidadInstalacionDBEntity.updateFieldEstado(
								unidadInstalacionID,
								EstadoCotejo.PENDIENTE.getIdentificador());
				}
			} else {
				// podria obtenerse la primera parte antes de eleminarla en
				// lugar de que siempre q quede vacia ponerla a pendiente
				_unidadInstalacionDBEntity.updateFieldEstado(
						unidadInstalacionID,
						EstadoCotejo.PENDIENTE.getIdentificador());
			}
		}

		checkModificaRelacionRechazada(relacion);
		commit();
	}

	/**
	 * @param posiciones
	 */
	private void checkIsRemovableFromUI(String idUInstalacion, int[] posiciones)
			throws RelacionOperacionNoPermitidaException {
		List partes = _udocEnUIDBEntity.fetchRowsByUInstalacion(idUInstalacion,
				posiciones);
		if (partes != null) {
			for (Iterator itPartes = partes.iterator(); itPartes.hasNext();) {
				ParteUnidadDocumentalVO aPart = (ParteUnidadDocumentalVO) itPartes
						.next();
				if (aPart.getTotalPartes() > 1)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_COMPLETOS);

				// comprobar que no este en una relacion de entrega con errores
				// y sea valida
				RelacionEntregaVO relacion = getRelacionXIdRelacion(aPart
						.getIdRelentrega());
				if (relacion.isConErroresCotejo()) {
					if (aPart.isRevisada())
						throw new RelacionOperacionNoPermitidaException(
								RelacionOperacionNoPermitidaException.XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_CON_ERRORES);
				}
			}
		}

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#removeUdocsFromUiConErrores(int[],
	 * java.lang.String)
	 */
	public void removeUdocsFromUiConErrores(int[] posiciones,
			String unidadInstalacionID)
			throws RelacionOperacionNoPermitidaException {
		iniciarTransaccion();

		// Se recorre al revés para ir eliminado las últimas posiciones
		for (int i = posiciones.length - 1; i >= 0; i--) {
			// Obtener la unidad documental
			IParteUnidadDocumentalVO parteUdocSel = _udocEnUIDBEntity
					.getRowByUdocPosicion(unidadInstalacionID, posiciones[i]);

			// Obtener todas las partes
			List listaPartes = _udocEnUIDBEntity.getListaPartes(parteUdocSel
					.getIdUnidadDoc());

			if (!util.CollectionUtils.isEmpty(listaPartes)) {
				// Recorrer la lista de Partes
				Iterator it = listaPartes.iterator();
				while (it.hasNext()) {
					IParteUnidadDocumentalVO parteUdoc = (IParteUnidadDocumentalVO) it
							.next();

					// Eliminar la Unidad Documental de la Unidad de Instalación
					_udocEnUIDBEntity.deleteUdocFromUI(
							parteUdoc.getIdUinstalacionRe(),
							parteUdoc.getPosUdocEnUI());

					// Actualizar el número de Partes
					_unidadDocumentalDBEntity.updateNumPartesUdoc(
							parteUdoc.getIdUnidadDoc(), 1);

					// renumero ps dentro de la caja
					List partesUdocsEnUi = _udocEnUIDBEntity
							.fetchRowsByUInstalacion(parteUdoc
									.getIdUinstalacionRe());
					if (partesUdocsEnUi != null) {
						int newPos = 1;
						for (Iterator itPartesUdocEnUi = partesUdocsEnUi
								.iterator(); itPartesUdocEnUi.hasNext();) {
							IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdocEnUi
									.next();
							if (newPos != aPart.getPosUdocEnUI()) {
								_udocEnUIDBEntity
										.updatePosicionEnCaja(
												aPart.getIdUnidadDoc(),
												parteUdoc.getIdUinstalacionRe(),
												newPos);
							}
							newPos++;
						}

						// si la caja era con errores miro a ver si la caja
						// sigue teniedno udocs con errores
						if (partesUdocsEnUi.size() > 0) {
							RelacionEntregaVO relacion = null;
							IParteUnidadDocumentalVO firstPart = (IParteUnidadDocumentalVO) partesUdocsEnUi
									.get(0);
							relacion = getRelacionXIdRelacion(firstPart
									.getIdRelentrega());
							if (relacion != null
									&& relacion.isConErroresCotejo()) {
								boolean hayErrores = false;
								for (Iterator itPartesUdocEnUi = partesUdocsEnUi
										.iterator(); itPartesUdocEnUi.hasNext();) {
									IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdocEnUi
											.next();
									if (aPart.isConErrores()) {
										hayErrores = true;
										break;
									}
								}
								if (hayErrores)
									_unidadInstalacionDBEntity
											.updateFieldEstado(parteUdoc
													.getIdUinstalacionRe(),
													EstadoCotejo.ERRORES
															.getIdentificador());
								else
									_unidadInstalacionDBEntity
											.updateFieldEstado(parteUdoc
													.getIdUinstalacionRe(),
													EstadoCotejo.PENDIENTE
															.getIdentificador());
							}
						} else {
							// podria obtenerse la primera parte antes de
							// eleminarla en lugar de que siempre q quede vacia
							// ponerla a pendiente
							_unidadInstalacionDBEntity.updateFieldEstado(
									parteUdoc.getIdUinstalacionRe(),
									EstadoCotejo.PENDIENTE.getIdentificador());
						}
					}
				}
			}
		}

		commit();

	}

	public void checkModificablePorExpedientes(
			UnidadInstalacionVO undidadInstalacionEnRelacion)
			throws RelacionOperacionNoPermitidaException {
		// comprobar que no se incorporan udocs a una caja con un parte de
		// expediente no primera ni final
		List partesDentroCaja = _udocEnUIDBEntity
				.fetchRowsByUInstalacion(undidadInstalacionEnRelacion.getId());
		if (partesDentroCaja != null) {
			for (Iterator itPartes = partesDentroCaja.iterator(); itPartes
					.hasNext();) {
				ParteUnidadDocumentalVO aPart = (ParteUnidadDocumentalVO) itPartes
						.next();
				if (aPart.getNumParteUdoc() > 1
						&& aPart.getNumParteUdoc() < aPart.getTotalPartes()) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_INCORPORAR_EXPEDIENTES_A_UNA_CAJA_CON_LA_PRIMERA_PARTE_O_LA_ULTIMA_DE_UNA_UNIDAD_DOC);
				}

			}
		}

	}

	public void incorporarACaja(
			UnidadInstalacionVO undidadInstalacionEnRelacion, String[] udocsIDS)
			throws RelacionOperacionNoPermitidaException {
		iniciarTransaccion();

		checkModificablePorExpedientes(undidadInstalacionEnRelacion);

		// obtener udocs actuales de la caja
		int nUdocsActualesEnCaja = 0;
		List udocsCaja = _udocEnUIDBEntity
				.fetchRowsByUInstalacion(undidadInstalacionEnRelacion.getId());
		if (udocsCaja != null)
			nUdocsActualesEnCaja = udocsCaja.size();

		RelacionEntregaVO relacion = getRelacionXIdRelacion(undidadInstalacionEnRelacion
				.getIdRelEntrega());
		for (int i = 0; i < udocsIDS.length; i++) {
			ParteUnidadDocumentalVO udocEnUI = null;
			// crear una parte de la udoc a insertar en la caja
			udocEnUI = new ParteUnidadDocumentalVO();
			udocEnUI.setIdRelentrega(undidadInstalacionEnRelacion
					.getIdRelEntrega());
			udocEnUI.setIdUnidadDoc(udocsIDS[i]);
			udocEnUI.setIdUinstalacionRe(undidadInstalacionEnRelacion.getId());
			udocEnUI.setPosUdocEnUI(nUdocsActualesEnCaja + i + 1);
			udocEnUI.setUdocCompleta(Constants.TRUE_STRING);
			udocEnUI.setNumParteUdoc(1);
			udocEnUI.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());
			_udocEnUIDBEntity.addUdocToUI(udocEnUI);

			CotejoHelper.actualizarEstadoCotejoUdoc(relacion, udocsIDS[i],
					_udocEnUIDBEntity, _unidadInstalacionDBEntity);

		}

		relacion = getRelacionXIdRelacion(undidadInstalacionEnRelacion
				.getIdRelEntrega());
		checkModificaRelacionRechazada(relacion);
		commit();

	}

	public void validarMovimientoUnidades(List partesAMover,
			String unidadInstalacionID, int offset)
			throws RelacionOperacionNoPermitidaException {
		boolean subir = offset < 0;
		int primeraPosicion = -1;
		int sigPos = -1;
		for (Iterator itPartesASubir = partesAMover.iterator(); itPartesASubir
				.hasNext();) {

			IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesASubir
					.next();
			if (subir && aPart.getPosUdocEnUI() == 1) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_PRIMERA_POSICION);
			} else {
				int size = _udocEnUIDBEntity
						.countUdocsEnUi(unidadInstalacionID);
				if (!subir && aPart.getPosUdocEnUI() == size)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_ULTIMA_POSICION);

			}
			if (primeraPosicion == -1) {
				primeraPosicion = aPart.getPosUdocEnUI();
				sigPos = primeraPosicion;
			} else {
				sigPos++;
				if (aPart.getPosUdocEnUI() != sigPos)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_POSICIONES_NO_CONSECUTIVAS);

			}

		}
	}

	/**
	 *
	 * @param partesAMover
	 *            : List de objetos ParteUnidadDocumental que se van a
	 *            mover(deben estar ordenados por posicion)
	 * @param unidadInstalacionID
	 * @param offset
	 */
	private void moverUdocsFromUi(List partesAMover,
			String unidadInstalacionID, int offset)
			throws RelacionOperacionNoPermitidaException {

		iniciarTransaccion();

		boolean subir = offset < 0;

		// validar
		// no subir la primera pos ni baja la ultima

		int primeraPosicion = -1;
		int sigPos = -1;
		for (Iterator itPartesASubir = partesAMover.iterator(); itPartesASubir
				.hasNext();) {

			IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesASubir
					.next();
			if (subir && aPart.getPosUdocEnUI() == 1) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_PRIMERA_POSICION);
			} else {
				int size = _udocEnUIDBEntity
						.countUdocsEnUi(unidadInstalacionID);
				if (!subir && aPart.getPosUdocEnUI() == size)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_ULTIMA_POSICION);

			}
			if (primeraPosicion == -1) {
				primeraPosicion = aPart.getPosUdocEnUI();
				sigPos = primeraPosicion;
			} else {
				sigPos++;
				if (aPart.getPosUdocEnUI() != sigPos)
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_POSICIONES_NO_CONSECUTIVAS);

			}

		}

		String[] idsObjetosAMover = new String[partesAMover.size()];

		// obtner posiciones actuales de los objetos a seleccionados para mover
		int[] posicionesAMover = new int[partesAMover.size()];
		int indexPos = 0;
		for (Iterator itPartesAMover = partesAMover.iterator(); itPartesAMover
				.hasNext();) {
			IParteUnidadDocumentalVO parte = (IParteUnidadDocumentalVO) itPartesAMover
					.next();
			posicionesAMover[indexPos] = parte.getPosUdocEnUI();
			idsObjetosAMover[indexPos] = parte.getIdUnidadDoc();
			indexPos++;
		}

		// actualizar posiciones de los desplazados (por posicion)
		if (offset < 0) {
			for (int i = 0; i < Math.abs(offset); i++) {
				_udocEnUIDBEntity.updatePosicionEnCaja2(posicionesAMover[i]
						+ offset, unidadInstalacionID, posicionesAMover[i]
						+ offset + posicionesAMover.length);
			}
		} else {

			if (posicionesAMover.length == 1) {
				_udocEnUIDBEntity.updatePosicionEnCaja2(posicionesAMover[0]
						+ offset, unidadInstalacionID, posicionesAMover[0]
						+ offset - posicionesAMover.length);
			} else {
				for (int i = posicionesAMover.length - 1; i >= Math.abs(offset); i--) {
					_udocEnUIDBEntity.updatePosicionEnCaja2(posicionesAMover[i]
							+ offset, unidadInstalacionID, posicionesAMover[i]
							+ offset - posicionesAMover.length);
				}
			}

		}

		RelacionEntregaVO relacion = getRelacionXIdRelacion(getUnidadInstalacion(
				unidadInstalacionID).getIdRelEntrega());

		// actualizar posiciones de los que decidimos mover(por IDS)
		for (int i = 0; i < partesAMover.size(); i++) {
			int nuevaPosicion = 0;
			nuevaPosicion = posicionesAMover[i] + offset;
			_udocEnUIDBEntity.updatePosicionEnCaja(idsObjetosAMover[i],
					unidadInstalacionID, nuevaPosicion);
			CotejoHelper.actualizarEstadoCotejoUdoc(relacion,
					idsObjetosAMover[i], _udocEnUIDBEntity,
					_unidadInstalacionDBEntity);
		}

		relacion = getRelacionXIdRelacion(getUnidadInstalacion(
				unidadInstalacionID).getIdRelEntrega());

		checkModificaRelacionRechazada(relacion);
		commit();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#subirExpediente(int[],
	 * java.lang.String)
	 */
	public void subirExpediente(List partesASubir, String idUnidadInstalacion)
			throws RelacionOperacionNoPermitidaException {
		moverUdocsFromUi(partesASubir, idUnidadInstalacion, -1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#bajarExpediente(int[],
	 * java.lang.String)
	 */
	public void bajarExpediente(List partesABajar, String idUnidadInstalacion)
			throws RelacionOperacionNoPermitidaException {
		moverUdocsFromUi(partesABajar, idUnidadInstalacion, 1);

	}

	public void checkIsPartDivisible(ParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException {
		// comprobar que sea la ultima parte de la caja
		int numUdocsEnCaja = _udocEnUIDBEntity.countUdocsEnUi(parteUDoc
				.getIdUinstalacionRe());
		if (numUdocsEnCaja != parteUDoc.getPosUdocEnUI()) {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_UDOC_DE_LA_CAJA);
		} else {
			if (parteUDoc.getTotalPartes() != parteUDoc.getNumParteUdoc()) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_PARTE_DE_UDOC);

			}
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#dividirParteUdoc(transferencias.
	 * vos.ParteUnidadDocumentalVO)
	 */
	public UnidadInstalacionVO dividirParteUdoc(RelacionEntregaVO relacion,
			ParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException {

		checkIsPartDivisible(parteUDoc);

		FormatoHuecoVO formatoRelacion = _formatoDBEntity.loadFormato(relacion
				.getIdformatoui());

		if (formatoRelacion.isMultidoc()) {
			// creamos caja sin signatura
			iniciarTransaccion();

			UnidadInstalacionVO caja = crearCajaSinSignatura(relacion.getId(),
					new String[] { parteUDoc.getIdUnidadDoc() });

			CotejoHelper.actualizarEstadoCotejoUdoc(relacion,
					parteUDoc.getIdUnidadDoc(), _udocEnUIDBEntity,
					_unidadInstalacionDBEntity);

			commit();
			return caja;

		} else {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DIVIDIR_LA_RELACION_NO_TIENE_FORMATO_MULTIDOCUMENTO);
		}

	}

	public UnidadInstalacionVO dividirParteUdocEnUIExistente(
			RelacionEntregaVO relacion, IParteUnidadDocumentalVO parteUDoc,
			String signatura) throws RelacionOperacionNoPermitidaException {

		FormatoHuecoVO formatoRelacion = _formatoDBEntity.loadFormato(relacion
				.getIdformatoui());

		// creamos caja con signatura
		if (formatoRelacion.isMultidoc()) {
			UnidadInstalacionVO caja = crearCajaSignaturada(relacion.getId(),
					relacion.getIdarchivoreceptor(),
					new String[] { parteUDoc.getIdUnidadDoc() }, signatura,
					true, relacion.getIdformatoui());
			return caja;
		} else {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DIVIDIR_LA_RELACION_NO_TIENE_FORMATO_MULTIDOCUMENTO);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#checkIsPartEliminable(transferencias
	 * .vos.ParteUnidadDocumentalVO)
	 */
	public void checkIsPartEliminable(IParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException {
		// comprobar que no este en una relacion de entrega con errores y sea
		// valida
		RelacionEntregaVO relacion = getRelacionXIdRelacion(parteUDoc
				.getIdRelentrega());
		if (relacion.isConErroresCotejo()) {
			if (parteUDoc.isRevisada())
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_ELIMINAR_PARTE_EXPEDIENTE_CON_ERRORES);
		}
		// comprobar que sea una parte y ademas la ultima parte
		int numPartes = _udocEnUIDBEntity.countPartesUdoc(parteUDoc
				.getIdUnidadDoc());
		if (numPartes == 1 || numPartes != parteUDoc.getNumParteUdoc()) {
			throw new RelacionOperacionNoPermitidaException(
					RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_LA_ULTIMA_PARTE);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#updateSignaturaCaja(transferencias
	 * .actions.UnidadInstalacionPO2, java.lang.String)
	 */
	public void updateSignaturaCaja(String idRelacion,
			UnidadInstalacionVO caja, String signaturaCaja,
			String idArchivoReceptor)
			throws RelacionOperacionNoPermitidaException {
		iniciarTransaccion();

		// Comprobar que la signatura no está repetida
		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		String idUbicacion = relacionEntrega.getIddeposito();

		ArchivoVO archivoVO = _archivoDbEntity.getArchivoXId(relacionEntrega
				.getIdarchivoreceptor());

		// Obtener las unidades de instalación de la relación
		List unidadesInstalacion = _unidadInstalacionDBEntity
				.fetchRowsByIdRelacion(idRelacion);

		if (archivoVO.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
				.getIdentificador()) {
			// Comprobar que el hueco asociado a la signatura esté libre para el
			// archivo indicado
			HuecoVO nuevoHuecoVO = SignaturaUtil.getHuecoAsociadoASignatura(
					signaturaCaja, idArchivoReceptor, _huecoDBEntity);
			if (nuevoHuecoVO == null)
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO);
			if ((nuevoHuecoVO != null)
					&& (!HuecoVO.LIBRE_STATE.equals(nuevoHuecoVO.getEstado()))) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO);
			}
			if ((nuevoHuecoVO != null)
					&& (!nuevoHuecoVO.getIdformato().equals(
							relacionEntrega.getIdformatoui()))) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION);
			}
			if (isSignaturaAsociadaHuecoUtilizadaRelacion(idRelacion,
					caja.getId(), signaturaCaja)) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION);
			}
			if ((unidadesInstalacion != null)
					&& (unidadesInstalacion.size() > 1)) {
				if ((nuevoHuecoVO != null)
						&& (StringUtils.isNotEmpty(idUbicacion))
						&& (!idUbicacion.equals(nuevoHuecoVO.getIddeposito()))) {
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);
				}
			}
			_unidadInstalacionDBEntity.updateFieldSignatura(caja.getId(),
					signaturaCaja);
			_relacionEntregaDBEntity.updateUbicacion(idRelacion, false,
					nuevoHuecoVO.getIddeposito());
		} else {
			if (SignaturaUtil.existeSignatura(signaturaCaja, idArchivoReceptor,
					_unidadInstalacionDBEntity, _unidadInstalacionReeaDBEntity,
					_uiReeaCRDBEntity, _huecoDBEntity, ConfigConstants
							.getInstance().getSignaturacionPorArchivo())) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA);
			}

			_unidadInstalacionDBEntity.updateFieldSignatura(caja.getId(),
					signaturaCaja);
		}

		checkModificaRelacionRechazada(relacionEntrega);

		commit();
	}

	public boolean existeRelacionAbiertaXUsuario(
			RelacionEntregaVO relacionEntregaVO) {

		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador() };
		List relaciones = _relacionEntregaDBEntity
				.getRelacionesXGestorEnOrganoRemitente(
						relacionEntregaVO.getIdusrgestorrem(), estados,
						relacionEntregaVO.getAno(), true);

		for (Iterator i = relaciones.iterator(); i.hasNext();) {
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) i.next();

			// Comprobar que la serie no sea la misma
			if (relacionEntrega != null
					&& relacionEntrega.getIdseriedestino() != null
					&& relacionEntrega.getIdformatoui() != null) {
				if (relacionEntrega.getIdseriedestino().equals(
						relacionEntregaVO.getIdseriedestino())
						&& relacionEntrega.getIdformatoui().equals(
								relacionEntregaVO.getIdformatoui())
						&& relacionEntrega.getIdNivelDocumental().equals(
								relacionEntregaVO.getIdNivelDocumental())) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean existeRelacionAbiertaXUsuarioYTipoYArchivo(
			RelacionEntregaVO relacionEntregaVO) {

		int[] estados = { EstadoREntrega.ABIERTA.getIdentificador() };
		List relaciones = _relacionEntregaDBEntity
				.getRelacionesXGestorYTipoYArchivoEnOrganoRemitente(
						relacionEntregaVO.getIdusrgestorrem(), estados,
						relacionEntregaVO.getAno(),
						relacionEntregaVO.getTipotransferencia(),
						relacionEntregaVO.getIdarchivoreceptor());

		for (Iterator i = relaciones.iterator(); i.hasNext();) {
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) i.next();

			// Comprobar que la serie no sea la misma
			if (relacionEntrega != null
					&& relacionEntrega.getIdseriedestino() != null
					&& relacionEntrega.getIdformatoui() != null) {
				if (relacionEntrega.getIdseriedestino().equals(
						relacionEntregaVO.getIdseriedestino())
						&& relacionEntrega.getIdformatoui().equals(
								relacionEntregaVO.getIdformatoui())) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idFormato
	 * @param idCampoFechaExtremaFinal
	 * @param idsUnidadesDocumentales
	 * @param idUnidadInstalacion
	 */
	/*
	 * public void getUInstConCondiciones(Date fechaInicial, Date fechaFinal,
	 * String idArchivoEmisor, String idPadre, String idFormato, String
	 * idCampoFechaExtremaFinal, List idsUnidadesDocumentales, String
	 * idUnidadInstalacion) { iniciarTransaccion();
	 * _relacionEntregaDBEntity.getUInstXArchivoYSerieConFechas(fechaInicial,
	 * fechaFinal, idArchivoEmisor, idPadre,
	 * idFormato,idCampoFechaExtremaFinal);
	 * _relacionEntregaDBEntity.getUInstUdocsXUdocsCompletasYNoCompletas
	 * (idsUnidadesDocumentales, idUnidadInstalacion); commit(); }
	 */

	/*
	 * public List getUdocsUInst(String idUnidadInstalacion) { return
	 * _udocEnUIDepositoDBEntity.getUdocsUInst(idUnidadInstalacion); }
	 */
	public List getUDocsXArchivoYSerieYFormatoConFechas(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idSerieOrigen) {
		return _relacionEntregaDBEntity
				.getUDocsXArchivoYSerieYFormatoConFechas(fechaInicial,
						fechaFinal, idArchivoEmisor, idSerieOrigen, null, null);
	}

	public List getUInstParaRelacionEntreArchivos(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idSerieOrigen,
			String idFormato) {

		return getUInstParaRelacionEntreArchivos(fechaInicial, fechaFinal,
				idArchivoEmisor, idSerieOrigen, idFormato, null, null);
	}

	public List getUInstParaRelacionEntreArchivos(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idSerieOrigen,
			String idFormato, String idNivelDocumental) {
		return getUInstParaRelacionEntreArchivos(fechaInicial, fechaFinal,
				idArchivoEmisor, idSerieOrigen, idFormato, idNivelDocumental,
				null);
	}

	public List getUInstParaRelacionEntreArchivos(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idSerieOrigen,
			String idFormato, String idNivelDocumental, List listaIdsAExcluir) {

		// Obtener las Unidades Documentales que cumplen los requisitos
		List listaUDocs = _relacionEntregaDBEntity
				.getUDocsXArchivoYSerieYFormatoConFechas(fechaInicial,
						fechaFinal, idArchivoEmisor, idSerieOrigen, idFormato,
						idNivelDocumental);

		List listaUInst = null;
		List listaIdsUDocs = new ArrayList();

		if (!ListUtils.isEmpty(listaUDocs)) {
			// Recorrer las Unidades Documentales para obtener las Unidades de
			// Instalación
			Iterator it = listaUDocs.iterator();
			while (it.hasNext()) {
				ElementoCuadroClasificacion elemento = (ElementoCuadroClasificacion) it
						.next();
				String idElemento = elemento.getId();
				listaIdsUDocs.add(idElemento);
			}

			if (!ListUtils.isEmpty(listaIdsUDocs)) {
				listaUInst = _detallePrevisionDBEntity.getUInstXUdoc(
						listaIdsUDocs, listaIdsAExcluir);
			}

			if (!ListUtils.isEmpty(listaUInst)) {
				it = listaUInst.iterator();
				List ltDelete = new ArrayList();
				while (it.hasNext()) {
					UInsDepositoVO uInsDepositoVO = (UInsDepositoVO) it.next();

					// Obtener la unidad de instalación en depósito
					uInsDepositoVO = _uInstalacionDepositoDBEntity
							.getUInstDepositoVOXIdEnDeposito(uInsDepositoVO
									.getId());

					// Comprobar que la unidad de instalación no esté bloqueada
					if (MarcaUtilUI.isUnidadInstalacionBloqueada(uInsDepositoVO
							.getMarcasBloqueo())) {
						ltDelete.add(uInsDepositoVO);
					}
				}

				listaUInst.removeAll(ltDelete);

				// Asociar Los ElementosCuadroClasificacion (Unidades
				// Documentales)
				if (!ListUtils.isEmpty(listaUInst)) {
					// Obtener la lista de Unidades de Instalación
					List listaUnidadesDoc = _relacionEntregaDBEntity
							.getUdocsCompletasYNoCompletas(listaIdsUDocs);

					Collections.sort(listaUnidadesDoc, new Comparator() {
						public int compare(Object o1, Object o2) {
							UDocEnUiDepositoVO udoc1 = (UDocEnUiDepositoVO) o1;
							UDocEnUiDepositoVO udoc2 = (UDocEnUiDepositoVO) o2;
							return new Integer(udoc1.getPosudocenui()).compareTo(
									new Integer(udoc2.getPosudocenui()));
						}
					});

					Map mapAsignacionUdocUinst = new HashMap();

					ListIterator itListaUnidadesDoc = listaUnidadesDoc
							.listIterator();
					while (itListaUnidadesDoc.hasNext()) {
						UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) itListaUnidadesDoc
								.next();

						List ltUnidadesMap = (List) mapAsignacionUdocUinst
								.get(udoc.getIduinstalacion());
						if (ListUtils.isEmpty(ltUnidadesMap)) {
							List ltUdoc = new ArrayList();
							ltUdoc.add(udoc);
							mapAsignacionUdocUinst.put(
									udoc.getIduinstalacion(), ltUdoc);
						} else {
							ltUnidadesMap.add(udoc);
							mapAsignacionUdocUinst.put(
									udoc.getIduinstalacion(), ltUnidadesMap);
						}

					}

					ListIterator itListaUInst = listaUInst.listIterator();
					while (itListaUInst.hasNext()) {
						UInsDepositoVO uIns = (UInsDepositoVO) itListaUInst
								.next();
						uIns.setListaUDocs((List) mapAsignacionUdocUinst
								.get(uIns.getId()));
					}

					/*
					 * for(int i=0;i<listaUInst.size();i++) { UInsDepositoVO
					 * uIns = (UInsDepositoVO) listaUInst.get(i);
					 *
					 * Iterator iterad = listaUnidadesDoc.iterator(); List
					 * lstUDocs = new ArrayList();
					 *
					 * while(iterad.hasNext()) { UDocEnUiDepositoVO udoc =
					 * (UDocEnUiDepositoVO) iterad.next();
					 *
					 * if(uIns.getId().equals(udoc.getIdentificacion())) {
					 * lstUDocs.add(udoc); } } uIns.setListaUDocs(lstUDocs);
					 * listaUInst.set(i,uIns); }
					 */
				}
			}
		}

		return listaUInst;
	}

	public void insertarUinstReea(List listaUnidadesInstalacion) {
		int marcas = MarcaUtil
				.generarMarcas(new int[] { MarcaUInstalacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA });
		if (listaUnidadesInstalacion == null
				|| listaUnidadesInstalacion.size() == 0)
			return;

		String idRelacion = ((UnidadInstalacionVO) listaUnidadesInstalacion
				.get(0)).getIdRelEntrega();
		RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
		int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		Locale locale = getServiceClient().getLocale();
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionEntrega, action, this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionEntrega,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_INCORPORACION_UNIDAD_INSTALACION);

		if (!ListUtils.isEmpty(listaUnidadesInstalacion)) {
			iniciarTransaccion();

			if (relacionEntrega.isRelacionConReencajado()) {
				getGestionRelacionEACRBI().addUIReencajadoNoTransaccional(
						idRelacion, listaUnidadesInstalacion,
						relacionEntrega.getIdFormatoRe());
			}

			Iterator it = listaUnidadesInstalacion.iterator();
			while (it.hasNext()) {
				UnidadInstalacionReeaVO unidadInstalacion = (UnidadInstalacionReeaVO) it
						.next();

				String id = unidadInstalacion.getIduideposito();

				pistaAuditoria.auditaUnidadInstalacion(locale,
						unidadInstalacion);
				_unidadInstalacionReeaDBEntity.insertRow(unidadInstalacion);
				_uInstalacionDepositoDBEntity.updateMarcaUnidadInstalacion(id,
						marcas);
			}

			checkModificaRelacionRechazada(relacionEntrega);
			commit();
		}
	}

	public void eliminarUinstReea(String idRelacion, String[] ids) {

		int marcas = MarcaUtil.generarMarcas(new int[] {});

		if (ids != null) {

			RelacionEntregaVO relacionEntrega = getRelacionXIdRelacion(idRelacion);
			int action = relacionEntrega.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
					: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
			Locale locale = getServiceClient().getLocale();
			IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
					.crearPistaAuditoria(relacionEntrega, action, this);
			pistaAuditoria
					.addDetalleBasico(
							locale,
							relacionEntrega,
							TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_ELIMINACION_UNIDAD_INSTALACION);

			iniciarTransaccion();
			for (int i = 0; i < ids.length; i++) {
				_unidadInstalacionReeaDBEntity.deleteRow(ids[i]);
				_uInstalacionDepositoDBEntity.updateMarcaUnidadInstalacion(
						ids[i], marcas);
				pistaAuditoria.auditaUnidadInstalacion(locale, ids[i]);
			}

			if (relacionEntrega.isRelacionConReencajado()) {
				getGestionRelacionEACRBI()
						.eliminarUIsReencajadoNoTransaccional(idRelacion, ids);
			}

			// Reordenar los números de orden
			List listaUnidades = _unidadInstalacionReeaDBEntity
					.fetchRowsByIdRelacion(idRelacion);
			if (!ListUtils.isEmpty(listaUnidades)) {
				Iterator it = listaUnidades.iterator();
				int orden = 1;
				while (it.hasNext()) {
					UnidadInstalacionReeaVO uInst = (UnidadInstalacionReeaVO) it
							.next();
					_unidadInstalacionReeaDBEntity.updateFieldOrden(
							uInst.getIdRelEntrega(), uInst.getId(), orden);
					orden++;
				}
			}
			checkModificaRelacionRechazada(relacionEntrega);
			commit();
		}

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUnidadesInstalacion(java.lang
	 * .String, int)
	 */
	public List getUnidadesInstalacion(String idRelacionEntrega,
			int tipoTransferencia) {
		return getUnidadesInstalacion(idRelacionEntrega, tipoTransferencia,
				false);
	}

	public List getUnidadesInstalacion(String idRelacionEntrega,
			int tipoTransferencia, boolean asignado) {
		RelacionEntregaVO relacion = _relacionEntregaDBEntity
				.getRelacionXId(idRelacionEntrega);
		if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == tipoTransferencia) {
			if (relacion.isRelacionConReencajado()) {
				return _uiReeaCRDBEntity.getByIdRelacion(idRelacionEntrega);
			} else {
				return getUnidadesInstalacionEntreArchivos(idRelacionEntrega);
			}
		} else {
			return getUnidadesInstalacion(idRelacionEntrega, asignado);
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUnidadesInstalacionEntreArchivos
	 * (java.lang.String)
	 */
	public List getUnidadesInstalacionEntreArchivos(String idRelacionEntrega) {
		return _unidadInstalacionReeaDBEntity
				.getUnidadesInstalacionXIdRelacionEntreArchivos(idRelacionEntrega);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUDocsXidRelacionEntreArchivos
	 * (java.lang.String)
	 */
	public List getUDocsXidRelacionEntreArchivos(String idRelacion) {
		return _udocEnUIDepositoDBEntity
				.getUdocsXidRelacionEntreArchivos(idRelacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsEnUInst(java.lang.String)
	 */
	public List getUdocsEnUInst(String idUnidadInstalacion) {
		return _udocEnUIDepositoDBEntity
				.getUdocsXIdUinstalacion(idUnidadInstalacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsEnUInst(java.lang.String)
	 */
	public List getUdocsEnUInstCR(String idRelEntrega,
			String idUnidadInstalacion) {
		return _udocEnUiReeaCRDBEntity
				.getUdocsXIdUinstalacion(idUnidadInstalacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#marcarUinstReeaRevisadas(java.lang
	 * .String[])
	 */
	public void marcarUinstReeaRevisadas(String[] ids) {
		_unidadInstalacionReeaDBEntity.updateFieldEstado(ids,
				EstadoCotejo.REVISADA.getIdentificador(), false);
	}

	private void actualizarPosicionesEnRelacionActual(
			String idUnidadDocumentalAEliminar) {
		// ¿Un expediente/unidad documental puede estar en varias cajas?
		// No, pero puede tener varias partes y una parte estara en una caja y
		// las otras en otras distintas.
		// ¿Un expediente con varias partes puede estar en varias relaciones?
		// No, Las unidades documentales se dividen en la propia relacion.
		// por tanto todas las partes van a estar siempre en la relacion actual.
		// Ademas, no puede haber varias partes en la misma caja
		// Al dividir una udoc,

		// obtener las cajas en la que esta esa unidad documental y todas sus
		// partes en la relacion
		List posiblesCajasAfectadas = _udocEnUIDBEntity
				.fetchRowsByUdoc(idUnidadDocumentalAEliminar);
		if (posiblesCajasAfectadas == null)
			return;
		for (Iterator it = posiblesCajasAfectadas.iterator(); it.hasNext();) {
			IParteUnidadDocumentalVO posibleCajaYParte = (IParteUnidadDocumentalVO) it
					.next();
			actualizaPosicionesEnCaja(posibleCajaYParte.getIdUinstalacionRe(),
					posibleCajaYParte.getPosUdocEnUI());
		}

		// actualizar posiciones en relación

	}

	private void actualizarPosicionesUDocs(String idRelacion) {
		List unidadesDocumentales = _unidadDocumentalDBEntity
				.fetchRowsByCodigoRelacionOrderByOrden(idRelacion);

		if (ListUtils.isNotEmpty(unidadesDocumentales)) {
			for (int i = 0; i < unidadesDocumentales.size(); i++) {
				UnidadDocumentalVO unidadDocumentalVO = (UnidadDocumentalVO) unidadesDocumentales
						.get(i);

				_unidadDocumentalDBEntity.updateOrden(
						unidadDocumentalVO.getId(), i + 1);
			}
		}
	}

	private void actualizaPosicionesEnCaja(String idUnidadInstalacion,
			int posicionUDocClaveEnUI) {
		// obtener la posicion de la parte/unidad documental dentro de la caja
		int nUdocEnCaja = _udocEnUIDBEntity.countUdocsEnUi(idUnidadInstalacion);
		int nUdocsAModificar = nUdocEnCaja - posicionUDocClaveEnUI;
		// si esa parte/unidad documental NO estaba en la ultima posicion de la
		// caja
		// actualizar las posiciones de los elementos de la lista por encima de
		// esa posicion
		// (decrementar una unidad)
		if (nUdocsAModificar > 0) {
			for (int i = 0; i < nUdocsAModificar; i++) {
				int posicionActualUdoc = posicionUDocClaveEnUI + i + 1;
				String uinstalacion = idUnidadInstalacion;
				int nuevaPosicion = posicionActualUdoc - 1;
				_udocEnUIDBEntity.updatePosicionEnCaja2(posicionActualUdoc,
						uinstalacion, nuevaPosicion);
			}
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUnidadesInstalacionEntreArchivos
	 * (java.lang.String, common.util.IntervalOptions)
	 */
	public List getUnidadesInstalacionEntreArchivos(String idRelacionEntrega,
			IntervalOptions ordenes) {
		return _unidadInstalacionReeaDBEntity.fetchRowsByIdRelacion(
				idRelacionEntrega, ordenes);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.model.GestorEstructuraDepositoBI#getUdocsIncompletasEnRelacion
	 * (java.lang.String, java.lang.String[], java.lang.String)
	 */
	public List getPartesUdocsNoIncluidasEnRelacion(String idRelacion,
			List idsUdocsRelacion, List idsUinstRelacion) {
		return _udocEnUIDepositoDBEntity.getPartesUdocsNoIncluidasEnRelacion(
				idRelacion, idsUdocsRelacion, idsUinstRelacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getPartesUdocsIncompletasEnRelacion
	 * (java.util.List, java.util.List)
	 */
	public List getPartesUdocsIncompletasEnRelacion(List idsUdocs, List idsUInst) {
		return _udocEnUIDepositoDBEntity.getPartesUdocsIncompletasEnRelacion(
				idsUdocs, idsUInst);
	}

	public List getPartesUdocsXIdRelacionOficinaArchivo(String idRelacion) {
		return _udocEnUIDBEntity.getUdocsByIdRelacionOficinaArchivo(idRelacion);
	}

	public List getPartesUdocsXIdRelacionEntreArchivos(String idRelacion) {
		return _udocEnUIDBEntity.getUdocsByIdRelacionEntreArchivos(idRelacion);
	}

	/*
	 * public void addUDocElectronica(UDocElectronicaVO uDocElectronicaVO) {
	 *
	 * }
	 */

	public boolean checkUdocsElectronicasConErrores(String idRelacion) {
		return _udocElectronicaDbEntity
				.checkUdocsElectronicasConErrores(idRelacion);
	}

	public void deleteUdocElectronica(String id) {
		iniciarTransaccion();
		String idRelacion = _udocElectronicaDbEntity.fetchRowById(id)
				.getIdRelEntrega();
		_unidadDocumentalDBEntity.dropRow(id);
		_udocElectronicaDbEntity.deleteXId(id);

		RelacionEntregaVO relacion = getRelacionXIdRelacion(idRelacion);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	public void deleteUDocElectronicas(String idRelacionEntrega) {
		_udocElectronicaDbEntity.deleteXIdRelacion(idRelacionEntrega);
	}

	public List getUDocsElectronicasByIdRelacion(String idRelacion) {
		return _udocElectronicaDbEntity.fetchRowsByIdRelacion(idRelacion);
	}

	public void guardarCotejoUDocsElectronicas(String codRelacionEntrega,
			Map udocsElectronicas, Map observacioneserror) {

		PistaAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(
						ArchivoActions.TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA,
						this);
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		RelacionEntregaVO relacionEntregaVO = getRelacionXIdRelacion(codRelacionEntrega);
		pistaAuditoria.addDetalleBasico(locale, relacionEntregaVO);

		/*
		 * ServiceRepository services =
		 * ServiceRepository.getInstance(getServiceSession());
		 * GestorEstructuraDepositoBI serviceDeposito =
		 * services.lookupGestorEstructuraDepositoBI();
		 */
		Iterator itUDocsElectronicas = udocsElectronicas.keySet().iterator();

		while (itUDocsElectronicas.hasNext()) {
			String idUDocElectronica = (String) itUDocsElectronicas.next();

			String estadoString = (String) udocsElectronicas
					.get(idUDocElectronica);
			int estado = EstadoCotejo.PENDIENTE.getIdentificador();

			if (estadoString != null) {
				estado = Integer.parseInt(estadoString);
			}
			String notasCotejo = null;

			if (estado == EstadoCotejo.ERRORES.getIdentificador()) {
				// Grabar las Observaciones
				notasCotejo = (String) observacioneserror
						.get(idUDocElectronica);

			}

			_udocElectronicaDbEntity.updateFieldEstadoYNotas(idUDocElectronica,
					estado, notasCotejo);
		}
		commit();
	}

	public void updateEstadoUDocsElectronicas(String idRelacionEntrega,
			String[] ids, int estado) {
		_udocElectronicaDbEntity.updateFieldEstado(idRelacionEntrega, ids,
				estado);
	}

	public boolean existenUDocsFisicas(String idRelacion) {
		return _unidadDocumentalDBEntity
				.existUdocsConDocumentosFisicos(idRelacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#
	 * getUDocsElectronicasParaRelacionEntreArchivos(java.util.Date,
	 * java.util.Date, java.lang.String, java.lang.String)
	 */
	public List getUDocsElectronicasParaRelacionEntreArchivos(
			Date fechaFinalDesde, Date fechaFinalHasta, String idArchivoEmisor,
			String idSerieOrigen) {
		return _relacionEntregaDBEntity
				.getUDocsElectronicasXArchivoYSerieConFechas(fechaFinalDesde,
						fechaFinalHasta, idArchivoEmisor, idSerieOrigen);

	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#
	 * getUDocsElectronicasParaRelacionEntreArchivos(java.util.Date,
	 * java.util.Date, java.lang.String, java.lang.String)
	 */
	public int getCountUDocsElectronicasParaRelacionEntreArchivos(
			Date fechaFinalDesde, Date fechaFinalHasta, String idArchivoEmisor,
			String idSerieOrigen) {
		return _relacionEntregaDBEntity
				.getCountUDocsElectronicasXArchivoYSerieConFechas(
						fechaFinalDesde, fechaFinalHasta, idArchivoEmisor,
						idSerieOrigen);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#addUDocElectronicaARelacionEntreArchivos
	 * (transferencias.vos.UDocElectronicaVO)
	 */
	public void addUDocElectronicaARelacionEntreArchivos(
			UDocElectronicaVO udocElectronicaVO) {
		iniciarTransaccion();

		_udocElectronicaDbEntity.insertRow(udocElectronicaVO);

		// Bloquea la Unidad Documental
		int marcasBloqueo = MarcaUtil
				.generarMarcas(new int[] { MarcaUInstalacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA });
		_unidadDocumentalElectronicaDBEntity.updateMarcasBloqueo(
				udocElectronicaVO.getId(), marcasBloqueo);

		String idRelacion = udocElectronicaVO.getIdRelEntrega();
		RelacionEntregaVO relacion = getRelacionXIdRelacion(idRelacion);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#
	 * getUDocsElectronicasByIdRelacionEntreArchivos(java.lang.String)
	 */
	public List getUDocsElectronicasByIdRelacionEntreArchivos(String idRelacion) {
		return _relacionEntregaDBEntity
				.getUDocsElectronicasByIdRelacionEntreArhivos(idRelacion);
	}

	public List getUDocsElectronicasByIdRelacionEntreArchivosConFechas(
			String idRelacion) {
		List udocsElectronicas = _relacionEntregaDBEntity
				.getUDocsElectronicasByIdRelacionEntreArhivos(idRelacion);
		GestionDescripcionBI managerDescripcion = ServiceRepository
				.getInstance(getServiceSession()).lookupGestionDescripcionBI();
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		for (Iterator it = udocsElectronicas.iterator(); it.hasNext();) {
			UDocElectronicaVO udoc = (UDocElectronicaVO) it.next();

			// Rellenar fechas de inicio y fin
			List listaFIni = managerDescripcion.getFechaElemento(udoc.getId(),
					csa.getConfiguracionDescripcion().getFechaExtremaInicial());
			List listaFFin = managerDescripcion.getFechaElemento(udoc.getId(),
					csa.getConfiguracionDescripcion().getFechaExtremaFinal());

			// Fecha de inicio
			if (listaFIni != null && listaFIni.size() > 0) {
				CampoFechaVO cfini = (CampoFechaVO) listaFIni.get(0);
				if (cfini != null)
					udoc.setFechaInicio(cfini.getFechaIni());
			}

			// Fecha de fin
			if (listaFFin != null && listaFFin.size() > 0) {
				CampoFechaVO cffin = (CampoFechaVO) listaFFin.get(0);
				if (cffin != null)
					udoc.setFechaFin(cffin.getFechaFin());
			}
		}
		return udocsElectronicas;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#eliminarUDocsElectronicasReea(java
	 * .lang.String, java.lang.String[])
	 */
	public void eliminarUDocsElectronicasReea(String idRelacion,
			String[] idsSeleccionados) {
		iniciarTransaccion();
		_udocElectronicaDbEntity.deleteXIds(idRelacion, idsSeleccionados);
		_unidadDocumentalElectronicaDBEntity
				.desbloqueaUDocsElectronicas(idsSeleccionados);
		RelacionEntregaVO relacion = getRelacionXIdRelacion(idRelacion);
		checkModificaRelacionRechazada(relacion);
		commit();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @seecommon.bi.GestionRelacionesEntregaBI#
	 * isRelacionEAConSoloDocumentosElectronicos(java.lang.String)
	 */
	public boolean isRelacionEAConSoloDocumentosElectronicos(String idRelacion) {
		int numUinst = _unidadInstalacionReeaDBEntity
				.countUIsRelacion(idRelacion);

		if (numUinst == 0) {
			// Comprobar si existen unidades documentales elctrónicas
			int numUdocElectronicas = _udocElectronicaDbEntity
					.countUdocsElectronicasByRelacion(idRelacion);
			if (numUdocElectronicas > 0)
				return true;
		}
		return false;
	}

	public boolean hayUDocsElectronicasParaRelacionEntreArchivos(
			RelacionEntregaVO relacionEntrega) {

		DetallePrevisionVO detallePrevision = _detallePrevisionDBEntity
				.selectRow(relacionEntrega.getIddetprevision());

		String anioDesde = detallePrevision.getAnoIniUdoc();
		String anioHasta = detallePrevision.getAnoFinUdoc();

		Date fechaFinalDesde = null;
		Date fechaFinalHasta = null;

		if (anioDesde != null) {
			try {
				fechaFinalDesde = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioDesde));
			} catch (Exception e) {
				logger.error("Error al convertir el año Inicial de las Unidades Documentales");
			}
		}

		if (anioHasta != null) {
			try {
				fechaFinalHasta = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioHasta) + 1);
			} catch (Exception e) {
				logger.error("Error al convertir el año Final de las Unidades Documentales");
			}
		}

		String idArchivoEmisor = relacionEntrega.getIdarchivoremitente();
		String idSerieOrigen = relacionEntrega.getIdserieorigen();

		int numUdocsElectronicas = getCountUDocsElectronicasParaRelacionEntreArchivos(
				fechaFinalDesde, fechaFinalHasta, idArchivoEmisor,
				idSerieOrigen);

		if (numUdocsElectronicas > 0)
			return true;

		return false;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUnidadesDocumentalesFisicas(java
	 * .lang.String)
	 */
	public List getUnidadesDocumentalesFisicas(String idRelacion) {
		return _unidadDocumentalDBEntity
				.fetchRowsFisicasByCodigoRelacionOrderByOrden(idRelacion);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUnidadesDocumentalesFisicas(java
	 * .lang.String)
	 */
	public List getUnidadesDocumentalesElectronicas(String idRelacion) {
		return _unidadDocumentalDBEntity
				.fetchRowsFisicasByCodigoRelacionOrderByOrden(idRelacion);
	}

	/**
	 * Obtiene una unidad documental en relación de entrega con su información
	 * de descripción rellena
	 *
	 * @param idUdocRE
	 * @return
	 */
	public UnidadDocumentalVO getUnidadDocumentalConInfoDesc(String idUdocRE) {

		// Obtenemos la unidad documental en alta/transferencia
		UnidadDocumentalVO udocRE = _unidadDocumentalDBEntity
				.fetchRow(idUdocRE);

		// Obtenemos la relación de entrega
		RelacionEntregaVO relacionVO = getRelacionXIdRelacion(udocRE
				.getIdRelEntrega());

		udocRE.setIdFichaDescr(relacionVO.getIdFicha()); // => De la relación de
															// entrega
		udocRE.setIdNivelDocumental(relacionVO.getIdNivelDocumental()); // => De
																		// la
																		// relación
																		// de
																		// entrega

		return udocRE;
	}

	public void conservarDescripcion(String idUDocOrigen, String idUDocDestino) {

		conservarDescripcion(idUDocOrigen, idUDocDestino, null);
	}

	public void conservarDescripcion(String idUDocOrigen, String idUDocDestino,
			Map mapCamposIgnorar) {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		descripcionBI.copiarCamposUDocREaUDocRE(idUDocOrigen, idUDocDestino,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
				mapCamposIgnorar);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getCountUnidadesDocRe(java.lang.String)
	 */
	public int getCountUnidadesDocRe(String codigoRelacion) {
		return _unidadDocumentalDBEntity
				.countRowsByCodigoRelacion(codigoRelacion);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getCountUnidadesInstalacion(java.lang.String)
	 */
	public int getCountUnidadesInstalacion(String codigoRelacion) {
		return _unidadInstalacionDBEntity.countUIsRelacion(codigoRelacion);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getCountUnidadesInstalacion(java.lang.String,
	 *      int)
	 */
	public int getCountUnidadesInstalacion(String idRelEntrega,
			int tipoTransferencia) {
		if (TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == tipoTransferencia) {
			return _unidadInstalacionReeaDBEntity
					.countUIsRelacion(idRelEntrega);
		} else {
			return _unidadInstalacionDBEntity.countUIsRelacion(idRelEntrega);
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsEnUIEA(java.lang.String)
	 * Devuelve una lista de ParteUnidadDocumentalVO
	 */
	public List getUdocsEnUIEA(String idUnidadInstalacion) {
		List listaPartes = new ArrayList();

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		List udocsEnUIDeposito = depositoBI
				.getUDocsEnUInstalacion(idUnidadInstalacion);

		if (udocsEnUIDeposito != null && udocsEnUIDeposito.size() > 0) {
			Iterator it = udocsEnUIDeposito.iterator();
			while (it.hasNext()) {
				UDocEnUiDepositoVO udocEnUIDeposito = (UDocEnUiDepositoVO) it
						.next();
				IParteUnidadDocumentalVO parte = ParteUnidadDocumentalVO
						.copyProperties(udocEnUIDeposito);
				listaPartes.add(parte);
			}
		}

		return listaPartes;
	}

	public void checkModificaRelacionRechazada(RelacionEntregaVO relacion) {
		if (relacion.isRechazada()) {
			relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
			try {
				updateRelacion(relacion);
			} catch (ActionNotAllowedException e) {
				logger.error(e);
			}
		}
	}

	private String estableceUbicacion(List uisAsignadas, String idIngreso,
			GestorEstructuraDepositoBI serviceDeposito, boolean actualizar) {

		String idUbicacion = StringUtils.EMPTY;

		if (uisAsignadas != null && uisAsignadas.size() > 0) {
			UnidadInstalacionVO uiAsignada = (UnidadInstalacionVO) uisAsignadas
					.get(0);
			String idUiUbicada = uiAsignada.getIduiubicada();
			HuecoVO hueco = serviceDeposito.getHuecoUInstalacion(idUiUbicada);
			idUbicacion = hueco.getIddeposito();

			// Actualizar la relación en bbdd
			if (actualizar)
				_relacionEntregaDBEntity
						.updateUbicacion(idIngreso, idUbicacion);
		}

		return idUbicacion;
	}

	public String estableceUbicacionIngresoSignaturacionAsociadaHueco(
			String idIngreso, GestorEstructuraDepositoBI serviceDeposito,
			boolean actualizar) {

		List uisAsignadas = _unidadInstalacionDBEntity.fetchRowsByIdRelacion(
				idIngreso, TipoUInstalacion.ASIGNADAS.getIdentificador());

		String idUbicacion = estableceUbicacion(uisAsignadas, idIngreso,
				serviceDeposito, actualizar);

		return idUbicacion;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getNextUnidadDocumental(java.lang
	 * .String, int)
	 */
	public UnidadDocumentalVO getNextUnidadDocumental(String idRelacion,
			int orden) {
		try {
			int nextOrden = _unidadDocumentalDBEntity
					.getNextOrdenUdocInRelacionEntrega(idRelacion, orden);
			return _unidadDocumentalDBEntity.getUdocInRelacionEntregaByOrden(
					idRelacion, nextOrden);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getPrevUnidadDocumental(java.lang
	 * .String, int)
	 */
	public UnidadDocumentalVO getPrevUnidadDocumental(String idRelacion,
			int orden) {
		try {
			int prevOrden = _unidadDocumentalDBEntity
					.getPrevOrdenUdocInRelacionEntrega(idRelacion, orden);
			return _unidadDocumentalDBEntity.getUdocInRelacionEntregaByOrden(
					idRelacion, prevOrden);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getRangosUDocRE(java.lang.String,
	 * int)
	 */
	public List getRangosUDocRE(String idUDoc, String idCampoInicial,
			String idCampoFinal) {
		return _unidadDocumentalDBEntity.getRangosUDocRE(idUDoc,
				idCampoInicial, idCampoFinal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#updateIdAltaRevisionDocumentacion
	 * (java.lang.String, java.lang.String)
	 */
	public void updateIdAltaRevisionDocumentacion(String idRevDoc, String idAlta) {
		_revisionDocumentacionDBEntity.updateIdAlta(idRevDoc, idAlta);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getIdUnidadDocumentalRevision(java
	 * .lang.String)
	 */
	public String getIdUnidadDocumentalRevision(String idRevDoc) {
		String id = null;
		RevisionDocumentacionVO revisionDocumentacionVO = _revisionDocumentacionDBEntity
				.getRevisionDocumentacionById(idRevDoc);

		if (revisionDocumentacionVO != null) {
			id = revisionDocumentacionVO.getIdUdoc();
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getRevisionDocumentacionById(java
	 * .lang.String)
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc) {
		return _revisionDocumentacionDBEntity
				.getRevisionDocumentacionById(idRevDoc);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getRevisionDocumentacionByIdAlta
	 * (java.lang.String)
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionByIdAlta(
			String idAlta) {
		return _revisionDocumentacionDBEntity
				.getRevisionDocumentacionByIdAlta(idAlta);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#existeRelacion(java.lang.String)
	 */
	public boolean existeRelacion(String idRelacion) {
		if (_relacionEntregaDBEntity.getRelacionXId(idRelacion) == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getIdUnidadDocumentalRevision(java
	 * .lang.String)
	 */
	public String getIdUnidadDocumentalRevisionByIdAlta(String idAlta) {
		String id = null;
		RevisionDocumentacionVO revisionDocumentacionVO = _revisionDocumentacionDBEntity
				.getRevisionDocumentacionByIdAlta(idAlta);

		if (revisionDocumentacionVO != null) {
			id = revisionDocumentacionVO.getIdUdoc();
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#addUdocsRelsOrigenToUdocRe(java.
	 * lang.String, java.lang.String)
	 */
	public void addUdocsRelacionadas(String idAlta, String idUdocDestino,
			int tipoElementoDestino, boolean copiarRelacionadas,
			boolean addToOrigen) {

		int tipoElementoOrigen = ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO;
		// int tipoObjetoOrigen = CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF;

		String idUdocOrigen = getIdUnidadDocumentalRevisionByIdAlta(idAlta);

		if (idUdocOrigen != null) {
			String idCampo = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getUdocsRel();
			List listaUdocsRelOrigen = _campoReferenciaDBEntity.getValues(
					idUdocOrigen, idCampo, tipoElementoOrigen);

			List listaUdocsRelDestino = null;
			if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoElementoDestino) {
				listaUdocsRelDestino = _campoReferenciaDBEntity.getValues(
						idUdocDestino, idCampo, tipoElementoDestino);
			} else {
				listaUdocsRelDestino = _referenciaUDocREDbEntity.getValues(
						idUdocDestino, idCampo, tipoElementoDestino);
			}

			int ordenOrigen = getNumeroOrden(listaUdocsRelOrigen);
			int ordenDestino = getNumeroOrden(listaUdocsRelDestino);

			if (copiarRelacionadas) {
				ListIterator it = listaUdocsRelOrigen.listIterator();
				while (it.hasNext()) {
					CampoReferenciaVO campoReferencia = (CampoReferenciaVO) it
							.next();

					relacionarCampo(idUdocDestino, tipoElementoDestino,
							campoReferencia.getIdObjRef(),
							CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF,
							idCampo, ordenDestino);
					ordenDestino++;
				}
				// Añadir la Origen como Relacionada
				ordenDestino = relacionarCampo(idUdocDestino,
						tipoElementoDestino, idUdocOrigen,
						CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF, idCampo,
						ordenDestino);
			}

			// Si el elemento destino es un ElementoCF copiar el elemento
			if (addToOrigen) {
				relacionarCampo(idUdocOrigen, tipoElementoOrigen,
						idUdocDestino,
						CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF, idCampo,
						ordenOrigen);
			}
		}
	}

	/**
	 * Añade las condiciones de acceso de la serie a la unidad documental.
	 *
	 * @param condicionesAccesoSerie
	 *            Condiciones de Acceso de la Serie
	 * @param idUdocEnCF
	 *            Identificador de la unidad documental
	 * @param descripcionBI
	 * @param sustituirExistente
	 *            Indica si se debe sustituir el valor existente en la unidad
	 *            documental.
	 */
	private void addCondicionesAcceso(String condicionesAccesoSerie,
			String idUdocEnCF, GestionDescripcionBI descripcionBI,
			boolean sustituirExistente) {

		String idCampoCondicionesAcceso = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getCondicionesAcceso();

		// Obtener las condiciones de acceso de la unidad documental
		String condicionesAccesoUDoc = descripcionBI
				.getCondicionesAcceso(idUdocEnCF);

		// Si la unidad documental no tiene condiciones de acceso, se copian las
		// de la serie.
		if (sustituirExistente || StringUtils.isEmpty(condicionesAccesoUDoc)) {
			CampoTextoVO campoTextoVO = new CampoTextoVO(idUdocEnCF,
					idCampoCondicionesAcceso, 0,
					ValorCampoGenericoVO.TIPO_TEXTO_LARGO,
					condicionesAccesoSerie,
					ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
			descripcionBI.actualizaCampo(TipoFicha.FICHA_ELEMENTO_CF,
					campoTextoVO);
		}
	}

	private int getNumeroOrden(List listaValores) {
		int orden = 0;

		// Si la unidad Documental origen tiene unidades relacionadas, copiarlas
		// como relacionadas del nuevo elemento
		if (ListUtils.isNotEmpty(listaValores)) {
			ListIterator it = listaValores.listIterator();
			while (it.hasNext()) {
				CampoReferenciaVO campoReferencia = (CampoReferenciaVO) it
						.next();

				if (campoReferencia != null) {
					int newOrden = campoReferencia.getOrden();
					if (newOrden > orden)
						orden = newOrden;
				}
			}
		}

		orden = orden + 1;
		return orden;
	}

	/**
	 * Relaciona dos elementos
	 *
	 * @param idObjeto
	 *            Identificador del elemento principipal
	 * @param tipoElemento
	 *            Tipo del Objeto Principal
	 * @param idObjRef
	 *            Identificador el Objeto Referenciado
	 * @param tipoObjRef
	 *            Tipo del Objeto Referenciado
	 * @param idCampo
	 *            Identificador del Campo
	 * @param orden
	 *            Orden que ocupa.
	 * @return Nuevo orden
	 */
	private int relacionarCampo(String idObjeto, int tipoObjeto,
			String idObjRef, int tipoObjRef, String idCampo, int orden) {
		CampoReferenciaVO campoReferenciaVO = new CampoReferenciaVO(idObjeto,
				idCampo, orden, tipoObjRef, idObjRef, tipoObjeto);

		if (ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE == tipoObjeto) {
			_referenciaUDocREDbEntity.insertValue(campoReferenciaVO);
			orden++;
		} else if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoObjeto) {
			_campoReferenciaDBEntity.insertValue(campoReferenciaVO);
		}
		return orden;

	}

	public int getCountRelacionesXUser(String idUser) {
		return _relacionEntregaDBEntity.getCountRelacionesByIdUser(idUser);
	}

	private void historicoUnidadInstalacion(String idArchivo,
			String idUInstaEnDeposito, Integer motivo) throws Exception {
		UInsDepositoVO uInstDepositoVO = _uInstalacionDepositoDBEntity
				.getUInstDepositoVOXIdEnDeposito(idUInstaEnDeposito);

		if (uInstDepositoVO != null)
			historicoUnidadInstalacion(idArchivo, uInstDepositoVO, motivo);
	}

	/**
	 * Realiza el histórico de la unidad de instalación
	 *
	 * @param uInstDepositoVO
	 *            Unidad de Instalación
	 * @param motivo
	 *            Motivo
	 * @throws Exception
	 *             Excepción
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

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getEstadoRelacion(java.lang.String)
	 */
	public int getEstadoRelacion(String idRelacion) {
		RelacionEntregaVO relacionEntregaVO = getRelacionXIdRelacion(idRelacion);

		if (relacionEntregaVO != null) {
			return relacionEntregaVO.getEstado();
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws RelacionOperacionNoPermitidaException
	 * @see common.bi.GestionRelacionesEntregaBI#rechazarRelacionEntrega(java.lang.String)
	 */
	public void rechazarRelacionEntrega(RelacionEntregaVO relacionVO)
			throws RelacionOperacionNoPermitidaException {

		int action = relacionVO.getIsIngresoDirecto() ? ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO
				: ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		Locale locale = getServiceClient().getLocale();
		IAuditoriaTransferencias pistaAuditoria = AuditoriaTransferencias
				.crearPistaAuditoria(relacionVO, action, this);
		pistaAuditoria
				.addDetalleBasico(
						locale,
						relacionVO,
						TransferenciasConstants.TRANSFERENCIAS_TIPO_EDICION_EDICION_CABECERA);

		checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);
		// if (relacionVO.getTipotransferencia() !=
		// PrevisionVO.TRANSFERENCIA_ORDINARIA)
		// verificarPermisosElaboracionTransferenciasExtraordinarias();

		verificarPermisosGeneralesTransferencia(relacionVO);

		IRelacionAuthorizationHelper authorizationHelper = getAuthorizationHelper(relacionVO);
		if (authorizationHelper.puedeSerRechazada(relacionVO)) {
			iniciarTransaccion();
			relacionVO.setEstado(EstadoREntrega.RECHAZADA.getIdentificador());
			_relacionEntregaDBEntity.updateRelacionEntrega(relacionVO);
			commit();
		} else {
			throw new RelacionOperacionNoPermitidaException(
					authorizationHelper.getErrorCode());
		}

	}

	public boolean permitidoActivarReencajado(RelacionEntregaVO relacionEntrega) {
		boolean permitidoActivarReencajado = false;
		if (relacionEntrega.isEntreArchivos()) {
			List listaUdocsRelacion = getUDocsXidRelacionEntreArchivos(relacionEntrega
					.getId());
			if (listaUdocsRelacion != null && !listaUdocsRelacion.isEmpty()) {
				if (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
						.getIdentificador()
						|| relacionEntrega.getEstado() == EstadoREntrega.RECHAZADA
								.getIdentificador()) {

					if (Constants.FALSE_STRING.equals(relacionEntrega
							.getConReencajado())) {
						permitidoActivarReencajado = true;
					}
				}
			}
		}
		return permitidoActivarReencajado;
	}

	public boolean permitidoAccionesReencajado(RelacionEntregaVO relacionEntrega) {
		boolean permitidoAccionesReencajado = false;
		ServiceClient sc = getServiceClient();
		if (Constants.TRUE_STRING.equals(relacionEntrega.getConReencajado())) {
			if (sc != null
					&& sc.getId().equals(relacionEntrega.getIdusrgestorrem())) {
				if (ArrayUtils.contains(sc.getIdsArchivosUser(),
						relacionEntrega.getIdarchivoremitente())) {
					if (relacionEntrega.isEntreArchivos()
							&& (relacionEntrega.getEstado() == EstadoREntrega.ABIERTA
									.getIdentificador() || relacionEntrega
									.getEstado() == EstadoREntrega.RECHAZADA
									.getIdentificador())) {
						permitidoAccionesReencajado = true;
					}
				}
			}
		}
		return permitidoAccionesReencajado;
	}

	public boolean permitidoMarcarRevisada(RelacionEntregaVO relacionEntrega) {
		boolean permitidoMarcarRevisada = false;
		List UIs = getUnidadesInstalacion(relacionEntrega.getId());
		for (Iterator iter = UIs.iterator(); iter.hasNext();) {
			IUnidadInstalacionVO uInstalacion = (IUnidadInstalacionVO) iter
					.next();
			if (uInstalacion.getEstadoCotejo() == EstadoCotejo.ERRORES
					.getIdentificador()) {
				permitidoMarcarRevisada = true;
				break;
			}
		}
		return permitidoMarcarRevisada;
	}

	public void addUDocElectronica(TransferenciaElectronicaInfo info)
			throws TransferenciaElectronicaException {

		getGestionDescripcionBI().getFicha(info.getIdFichaUdoc());

		// Crear la unidad documental
		UnidadDocumentalVO udoc = new UnidadDocumentalVO(
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaInicial(),
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaFinal(),
				ConfigConstants.getInstance()
						.getSeparadorDefectoFechasRelacion());

		IdentificacionUnidadDocumental identificacion = info
				.getContenidoUDocXML().getInfoUnidadDocumentalElectronica()
				.getIdentificacionUnidadDocumental();

		udoc.setIdRelEntrega(info.getRelacionEntregaVO().getId());
		udoc.setNumeroExpediente(identificacion.getNumExp());
		udoc.setAsunto(identificacion.getTitulo());
		udoc.setTieneDocumentosFisicos(false);

		Date fechaInicio;
		try {
			fechaInicio = identificacion.getFechaInicio().getDate();
		} catch (ParseException e1) {
			logger.error(e1);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_FECHA_INCORRECTA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_FECHA_INICIAL));

		}
		Date fechaFin;
		try {
			fechaFin = identificacion.getFechaFin().getDate();
		} catch (ParseException e1) {
			logger.error(e1);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_FECHA_INCORRECTA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_FECHA_FINAL));
		}

		udoc.setFechaInicio(fechaInicio);
		udoc.setFechaFin(fechaFin);
		udoc.setProductor(info.getDescriptorProductorSerieVO().getId(), info
				.getDescriptorProductorSerieVO().getNombre(), null);
		udoc.setPermitidoRealizarCambios(false);
		udoc.setCodSistProductor(info.getCodSistemaProductor());
		udoc.setTieneDocumentosFisicos(false);

		List udocsRelacion = fetchRowsByCodigoRelacionOrderByOrden(info
				.getRelacionEntregaVO().getId());
		udoc.setOrden(getNextOrden(udocsRelacion));

		int subtipo = ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;

		try {
			nuevaUnidadDocumental(info.getRelacionEntregaVO(), udoc, null,
					subtipo, 1, false);
			info.setIdUnidadDocumental(udoc.getId());

		} catch (ActionNotAllowedException e) {
			throw new TransferenciaElectronicaException(
					e,
					TransferenciasElectronicasConstants.PARAMETRO_CREAR_UNIDAD_DOCUMENTAL);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#establecerRelacionEntregaElectronica(transferencias.vos.TransferenciaElectronicaInfo)
	 */
	public void establecerRelacionEntregaElectronica(
			TransferenciaElectronicaInfo info)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio de establecer la relación de entrega electrónica");
		}

		RelacionEntregaVO relacion = new RelacionEntregaVO();

		relacion.setEstado(EstadoREntrega.VALIDADA_AUTOMATIZADA
				.getIdentificador());
		relacion.setIdorganoremitente(info.getIdOrganoRemitente());
		relacion.setCodsistproductor(info.getCodSistemaProductor());
		relacion.setNombresistproductor(info.getNombreSistProductor());
		relacion.setIdfondo(info.getFondo().getId());
		relacion.setIdprocedimiento(info.getCodigoProcedimiento());
		relacion.setIdarchivoreceptor(info.getIdArchivoReceptor());
		relacion.setAno(info.getAnio());
		relacion.setIdseriedestino(info.getIdSerie());
		relacion.setIddetprevision(info.getDetallePrevisionVO().getId());

		RelacionEntregaVO relacionBD = _relacionEntregaDBEntity
				.getRelacionVO(relacion);

		info.setRelacionEntregaVO(relacionBD);

		if (relacionBD == null) {

			if (logger.isInfoEnabled()) {
				logger.info("La relación de entrega no existe, se va a proceder a creala");
			}

			String idSerie = info.getSerieVO().getId();
			String idFormato = Constants.FORMATO_UI_TRANSFERENCIA_ELECTRONICA;
			String observaciones = null;
			String idDescriptorProductorRelacion = info
					.getDescriptorProductorSerieVO().getId();
			String idNivelDocumental = info.getIdNivelDocumental();
			String idFicha = info.getIdFichaUdoc();


			relacion = nuevaRelacion(info.getPrevisionVO(),
					info.getDetallePrevisionVO(), idSerie, info.getAppUser()
							.getId(), idFormato, observaciones,
					idDescriptorProductorRelacion, idNivelDocumental, idFicha);

			relacion.setEstado(EstadoREntrega.VALIDADA_AUTOMATIZADA
					.getIdentificador());
			relacion.setIdorganoremitente(info.getIdOrganoRemitente());
			relacion.setCodsistproductor(info.getCodSistemaProductor());
			relacion.setNombresistproductor(info.getNombreSistProductor());
			relacion.setIdfondo(info.getFondo().getId());
			relacion.setIdprocedimiento(info.getCodigoProcedimiento());
			relacion.setIdarchivoreceptor(info.getIdArchivoReceptor());
			relacion.setAno(info.getAnio());
			relacion.setSindocsfisicos(Constants.TRUE_STRING);
			relacion.setTipoDocumental(SOPORTE_ELECTRONICO);

			int numSecuencia = _nSecDBEntity.incrementarNumeroSecRelacion(
					relacion.getAno(), relacion.getIdarchivoreceptor());
			relacion.setOrden(numSecuencia);
			relacion.setFechaestado(DateUtils.getFechaActual());
			relacion.setIdusrgestorarchivorec(info.getAppUser().getId());
			relacion.setIdusrgestorrem(info.getAppUser().getId());

			checkPermission(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE);

			_relacionEntregaDBEntity.insertRelacion(relacion);
			info.setRelacionEntregaVO(relacion);

			_detallePrevisionDBEntity.incNRelacionesAsociadasADetalle(relacion.getIddetprevision());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin establecer la relación de entrega electrónica");
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getLongitudCampoCodigoSistemaProductor()
	 */
	public int getLongitudCampoCodigoSistemaProductor() {
		return RelacionEntregaDBEntityBaseImpl.CAMPO_CODSISTPRODUCTOR
				.getMaxLen();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionRelacionesEntregaBI#getLongitudCampoNombreSistemaProductor()
	 */
	public int getLongitudCampoNombreSistemaProductor() {
		return RelacionEntregaDBEntityBaseImpl.CAMPO_NOMBRESISTPRODUCTOR
				.getMaxLen();
	}

}