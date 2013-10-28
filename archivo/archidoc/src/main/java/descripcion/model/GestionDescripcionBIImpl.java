package descripcion.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.usuarios.AppPermissions;
import se.util.MapUtil;
import transferencias.db.IMapDescUDocDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.db.IUDocRelacionDBEntity;
import transferencias.vos.InteresadoUdocVO;
import transferencias.vos.MapDescUDocVO;
import transferencias.vos.RangoVO;
import util.CollectionUtils;
import util.StringOwnTokenizer;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.db.IDBEntityKeyValue;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoTables;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.CheckedArchivoException;
import common.exceptions.ConfigException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.security.ActionObject;
import common.security.DescripcionSecurityManager;
import common.security.SecurityManagerBase;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.DateQualifierHelper;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;
import common.vos.Describible;
import common.vos.ResultadoRegistrosVO;

import descripcion.ErrorKeys;
import descripcion.TipoListaDescriptora;
import descripcion.TipoObjetoUsado;
import descripcion.TipoObjetoUsuario;
import descripcion.db.IAreaDBEntity;
import descripcion.db.ICampoDatoDBEntity;
import descripcion.db.ICampoTablaDBEntity;
import descripcion.db.ICatalogoListaDescriptoresDBEntity;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.IFechaDBEntity;
import descripcion.db.IFechaDescrDBEntity;
import descripcion.db.IFichaDBEntity;
import descripcion.db.IFmtFichaDBEntity;
import descripcion.db.IFmtPrefDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.INumeroDescrDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.IReferenciaDescrDBEntity;
import descripcion.db.ITablaValidacionDBEntity;
import descripcion.db.ITextoCortoDescrDBEntity;
import descripcion.db.ITextoCortoUdocREDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.db.ITextoLargoDescrDBEntity;
import descripcion.db.IUsoObjetoDBEntity;
import descripcion.db.IValidacionDBEntity;
import descripcion.exceptions.DefFichaException;
import descripcion.exceptions.DescripcionSecurityException;
import descripcion.exceptions.DescriptorDuplicadoException;
import descripcion.exceptions.DescriptorRightsException;
import descripcion.exceptions.FichaException;
import descripcion.exceptions.ListaDescriptoraCerradaException;
import descripcion.model.automaticos.IADReglaGenDatos;
import descripcion.model.eventos.TipoEvento;
import descripcion.model.xml.card.ContenedorElementos;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.FichaFactory;
import descripcion.model.xml.card.TipoAccion;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.card.TiposElemento;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.definition.DefTipos;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.vos.AreaVO;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.CampoDatoBusquedaVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.FichaVO;
import descripcion.vos.FmtFichaVO;
import descripcion.vos.FmtPrefFichaVO;
import descripcion.vos.ICampoVO;
import descripcion.vos.InfoBFichaVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.TablaValidacionVO;
import descripcion.vos.TextoTablaValidacionVO;
import descripcion.vos.UsoObjetoVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import docelectronicos.TipoObjeto;
import docelectronicos.db.IDocClasifDescrDBEntity;
import docelectronicos.db.IDocDocumentoDescrDBEntity;
import docelectronicos.vos.IRepositorioEcmVO;
import es.archigest.framework.core.exceptions.ArchigestException;
import es.archigest.framework.core.vo.PropertyBean;
import fondos.actions.FondoPO;
import fondos.actions.FondoToPOTransformer;
import fondos.db.IDivisionFSDbEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IEntidadProductoraDBEntity;
import fondos.db.IFondoDbEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IUDocEnDivisionFSDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoNivelCF;
import fondos.utils.FondosUtils;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.UDocEnFraccionSerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.db.IListaControlAccesoDbEntity;
import gcontrol.model.NivelAcceso;
import gcontrol.model.TipoAcceso;

/**
 * Implementaciï¿½n del interfaz de negocio.
 */
public class GestionDescripcionBIImpl extends ServiceBase implements
		GestionDescripcionBI {
	private final static Logger logger = Logger
			.getLogger(GestionDescripcionBIImpl.class);

	private IFechaDBEntity campoFechaDBEntity = null,
			campoFechaUDocREDBEntity = null;
	private IFichaDBEntity fichaDBEntity = null;
	private INumeroDBEntity campoNumeroDBEntity = null,
			campoNumeroUDocREDBEntity = null;
	private IReferenciaDBEntity campoReferenciaDBEntity = null,
			campoReferenciaUDocREDBEntity = null;
	private ITextoDBEntity campoTextoCortoDBEntity = null;
	private ITextoCortoUdocREDBEntity campoTextoCortoUDocREDBEntity = null;
	private ITextoDBEntity campoTextoLargoDBEntity = null,
			campoTextoLargoUDocREDBEntity = null;
	private IValidacionDBEntity validacionDBEntity = null;
	private ICatalogoListaDescriptoresDBEntity catalogoListaDescriptoresDBEntity = null;
	private IDescriptorDBEntity descriptorDBEntity = null;
	private IFmtPrefDBEntity fmtPrefDBEntity = null;
	private IFmtFichaDBEntity fmtFichaDBEntity = null;
	private IFechaDescrDBEntity campoFechaDescrDBEntity = null;
	private INumeroDescrDBEntity campoNumeroDescrDBEntity = null;
	private IReferenciaDescrDBEntity campoReferenciaDescrDBEntity = null;
	private ITextoCortoDescrDBEntity campoTextoCortoDescrDBEntity = null;
	private ITextoLargoDescrDBEntity campoTextoLargoDescrDBEntity = null;
	private ITablaValidacionDBEntity tablaValidacionDBEntity = null;
	private IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity = null;
	private IFondoDbEntity fondoDbEntity = null;
	private IOrganoProductorDbEntity organoProductorDbEntity = null;
	private ICampoDatoDBEntity campoDatoDbEntity = null;
	private ICampoTablaDBEntity campoTablaDbEntity = null;
	private IUsoObjetoDBEntity usoObjetoDbEntity = null;
	private IAreaDBEntity areaDbEntity = null;
	private IUnidadDocumentalDbEntity unidadDocumentalDBEntity;
	private IUDocRelacionDBEntity unidadDocumentalREDBEntity;
	private IUDocEnDivisionFSDbEntity unidadDocumentalFSDBEntity;
	private IRelacionEntregaDBEntity relacionEntregaDBEntity;
	private IEntidadProductoraDBEntity entidadProductoraDBEntity;
	private IDocDocumentoDescrDBEntity docDocumentoDescrDBEntity;
	private IDocClasifDescrDBEntity docClasifDescrDBEntity;
	private IDivisionFSDbEntity divisionFSDbEntity;
	private IListaControlAccesoDbEntity listaControlAccesoDBEntity = null;
	private IMapDescUDocDBEntity mapDescUDocDBEntity = null;

	// private IVolumenSerieDBEntity volumenSerieDBEntity = null;
	// private ITextoDBEntity textoDBEntity = null;

	/**
	 * Constructor.
	 */
	public GestionDescripcionBIImpl(
			IFichaDBEntity fichaDBEntity,
			ITextoDBEntity campoTextoCortoDBEntity,
			ITextoDBEntity campoTextoLargoDBEntity,
			IFechaDBEntity campoFechaDBEntity,
			INumeroDBEntity campoNumeroDBEntity,
			IReferenciaDBEntity campoReferenciaDBEntity,
			IValidacionDBEntity validacionDBEntity,
			ICatalogoListaDescriptoresDBEntity catalogoListaDescriptoresDBEntity,
			IDescriptorDBEntity descriptorDBEntity,
			IFmtPrefDBEntity fmtPrefDBEntity,
			IFmtFichaDBEntity fmtFichaDBEntity,
			IFechaDescrDBEntity campoFechaDescrDBEntity,
			INumeroDescrDBEntity campoNumeroDescrDBEntity,
			IReferenciaDescrDBEntity campoReferenciaDescrDBEntity,
			ITextoCortoDescrDBEntity campoTextoCortoDescrDBEntity,
			ITextoLargoDescrDBEntity campoTextoLargoDescrDBEntity,
			ITablaValidacionDBEntity tablaValidacionDBEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity,
			IFondoDbEntity fondoDbEntity,
			IOrganoProductorDbEntity organoProductorDbEntity,
			ICampoDatoDBEntity campoDatoDbEntity,
			ICampoTablaDBEntity campoTablaDbEntity,
			IUsoObjetoDBEntity usoObjetoDbEntity, IAreaDBEntity areaDbEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDbEntity,
			ITextoCortoUdocREDBEntity campoTextoCortoUDocREDbEntity,
			ITextoDBEntity campoTextoLargoUDocREDbEntity,
			INumeroDBEntity campoNumeroUDocREDbEntity,
			IFechaDBEntity campoFechaUDocREDbEntity,
			IReferenciaDBEntity campoReferenciaUDocREDbEntity,
			IUDocRelacionDBEntity unidadDocumentalREDbEntity,
			IUDocEnDivisionFSDbEntity unidadDocumentalFSDBEntity,
			IRelacionEntregaDBEntity relacionEntregaDBEntity,
			IEntidadProductoraDBEntity entidadProductoraDBEntity,
			IDocDocumentoDescrDBEntity docDocumentoDescrDBEntity,
			IDocClasifDescrDBEntity docClasifDescrDBEntity,
			IDivisionFSDbEntity divisionFSDbEntity,
			IListaControlAccesoDbEntity listaControlAccesoDBEntity,
			IMapDescUDocDBEntity mapDescUDocDBEntity) {
		super();

		this.fichaDBEntity = fichaDBEntity;
		this.campoTextoCortoDBEntity = campoTextoCortoDBEntity;
		this.campoTextoLargoDBEntity = campoTextoLargoDBEntity;
		this.campoFechaDBEntity = campoFechaDBEntity;
		this.campoNumeroDBEntity = campoNumeroDBEntity;
		this.campoReferenciaDBEntity = campoReferenciaDBEntity;
		this.validacionDBEntity = validacionDBEntity;
		this.catalogoListaDescriptoresDBEntity = catalogoListaDescriptoresDBEntity;
		this.descriptorDBEntity = descriptorDBEntity;
		this.fmtPrefDBEntity = fmtPrefDBEntity;
		this.fmtFichaDBEntity = fmtFichaDBEntity;
		this.campoFechaDescrDBEntity = campoFechaDescrDBEntity;
		this.campoNumeroDescrDBEntity = campoNumeroDescrDBEntity;
		this.campoReferenciaDescrDBEntity = campoReferenciaDescrDBEntity;
		this.campoTextoCortoDescrDBEntity = campoTextoCortoDescrDBEntity;
		this.campoTextoLargoDescrDBEntity = campoTextoLargoDescrDBEntity;
		this.tablaValidacionDBEntity = tablaValidacionDBEntity;
		this.elementoCuadroClasificacionDBEntity = elementoCuadroClasificacionDBEntity;
		this.fondoDbEntity = fondoDbEntity;
		this.organoProductorDbEntity = organoProductorDbEntity;
		this.campoDatoDbEntity = campoDatoDbEntity;
		this.campoTablaDbEntity = campoTablaDbEntity;
		this.usoObjetoDbEntity = usoObjetoDbEntity;
		this.areaDbEntity = areaDbEntity;
		this.unidadDocumentalDBEntity = unidadDocumentalDbEntity;
		this.campoTextoCortoUDocREDBEntity = campoTextoCortoUDocREDbEntity;
		this.campoTextoLargoUDocREDBEntity = campoTextoLargoUDocREDbEntity;
		this.campoFechaUDocREDBEntity = campoFechaUDocREDbEntity;
		this.campoReferenciaUDocREDBEntity = campoReferenciaUDocREDbEntity;
		this.campoNumeroUDocREDBEntity = campoNumeroUDocREDbEntity;
		this.unidadDocumentalREDBEntity = unidadDocumentalREDbEntity;
		this.unidadDocumentalFSDBEntity = unidadDocumentalFSDBEntity;
		this.relacionEntregaDBEntity = relacionEntregaDBEntity;
		this.entidadProductoraDBEntity = entidadProductoraDBEntity;
		this.docDocumentoDescrDBEntity = docDocumentoDescrDBEntity;
		this.docClasifDescrDBEntity = docClasifDescrDBEntity;
		this.divisionFSDbEntity = divisionFSDbEntity;
		this.listaControlAccesoDBEntity = listaControlAccesoDBEntity;
		this.mapDescUDocDBEntity = mapDescUDocDBEntity;
	}

	/**
	 * Genera los valores automï¿½ticos de la ficha.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link TipoFicha}).
	 * @param parametros
	 *            Parï¿½metros para el objeto descrito.
	 */
	public void generarAutomaticos(String id, int tipoFicha, Map parametros) {
		if (StringUtils.isNotBlank(id)) {
			// Definiciï¿½n de la ficha de descripciï¿½n
			DefFicha defFicha = getDefFicha(id, tipoFicha);
			if ((defFicha != null)
					&& StringUtils.isNotBlank(defFicha
							.getClaseGenerarAutomaticos())) {
				try {
					iniciarTransaccion();

					IADReglaGenDatos regla = (IADReglaGenDatos) Class.forName(
							defFicha.getClaseGenerarAutomaticos())
							.newInstance();

					regla.inicializa(parametros);
					regla.generacionDatosAutomaticos(getServiceSession(), id);

					commit();
				} catch (Exception e) {
					logger.error(
							"Error en la generaci\u00F3n de valores autom\u00E1ticos",
							e);
					throw new DefFichaException(e, this.getClass().getName(),
							"Error en la generaci\u00F3n de valores autom\u00E1ticos");
				}
			}
		}
	}

	/**
	 * Actualiza el campo de la ficha.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Informaciï¿½n del campo.
	 */
	public void actualizaCampo(int tipoFicha, ValorCampoGenericoVO campo) {
		ValorCampoGenericoVO campoAnterior = retrieve(tipoFicha,
				campo.getTipo(), campo.getIdObjeto(), campo.getIdCampo(),
				campo.getOrden(), campo.getTipoElemento());

		if (campoAnterior == null)
			insert(tipoFicha, campo);
		else
			update(tipoFicha, campo, campoAnterior.getValorInfo());
	}

	/**
	 * Actualiza el campo de la ficha.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Informaciï¿½n del campo.
	 */
	public void insertaCampo(int tipoFicha, ValorCampoGenericoVO campo) {
		insert(tipoFicha, campo);
	}

	/**
	 * Elimina todos los valores de un campo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo ({@link ValorCampoGenericoVO}).
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 */
	public void vaciaCampo(int tipoFicha, int tipoCampo, String id,
			String idCampo) {
		remove(tipoFicha, tipoCampo, id, idCampo, null);
	}

	/**
	 * Obtiene la informaciï¿½n de un campo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	private ValorCampoGenericoVO retrieve(int tipoFicha, int tipoCampo,
			String id, String idCampo, int orden, int tipoElemento) {
		ValorCampoGenericoVO campo = null;

		switch (tipoCampo) {
		case ValorCampoGenericoVO.TIPO_FECHA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campo = campoFechaDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campo = campoFechaUDocREDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else
				campo = campoFechaDescrDBEntity.getValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campo = campoTextoCortoDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campo = campoTextoCortoUDocREDBEntity.getValue(id, idCampo,
						orden, tipoElemento);
			else
				campo = campoTextoCortoDescrDBEntity.getValue(id, idCampo,
						orden);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campo = campoTextoLargoDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campo = campoTextoLargoUDocREDBEntity.getValue(id, idCampo,
						orden, tipoElemento);
			else
				campo = campoTextoLargoDescrDBEntity.getValue(id, idCampo,
						orden);
			break;
		case ValorCampoGenericoVO.TIPO_NUMERICO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campo = campoNumeroDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campo = campoNumeroUDocREDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else
				campo = campoNumeroDescrDBEntity.getValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campo = campoReferenciaDBEntity.getValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campo = campoReferenciaUDocREDBEntity.getValue(id, idCampo,
						orden, tipoElemento);
			else
				campo = campoReferenciaDescrDBEntity.getValue(id, idCampo,
						orden);
			break;
		}

		return campo;
	}

	/**
	 * Crea un campo nuevo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Informaciï¿½n del campo.
	 */
	private void insert(int tipoFicha, ValorCampoGenericoVO campo) {

		Locale locale = getServiceClient().getLocale();

		if (campo != null) {
			// Auditorï¿½a de la inserciï¿½n
			AuditoriaDescripcion.auditaInsercionValorCampo(locale, this,
					tipoFicha, campo);

			// Inserciï¿½n del campo
			switch (campo.getTipo()) {
			case ValorCampoGenericoVO.TIPO_FECHA:
				if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
					campoFechaDBEntity.insertValue((CampoFechaVO) campo);
				else if (tipoFicha == TipoFicha.FICHA_UDOCRE
						|| tipoFicha == TipoFicha.FICHA_UDOCFS)
					campoFechaUDocREDBEntity.insertValue((CampoFechaVO) campo);
				else
					campoFechaDescrDBEntity.insertValue((CampoFechaVO) campo);
				break;
			case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
				if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
					if (!StringUtils.isEmpty(((CampoTextoVO) campo).getValor())) {
						campoTextoCortoDBEntity
								.insertValue((CampoTextoVO) campo);
					}
				} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
						|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
					if (!StringUtils.isEmpty(((CampoTextoVO) campo).getValor())) {
						campoTextoCortoUDocREDBEntity
								.insertValue((CampoTextoVO) campo);
					}
				} else {
					campoTextoCortoDescrDBEntity
							.insertValue((CampoTextoVO) campo);
				}
				break;
			case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
				if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
					if (!StringUtils.isEmpty(((CampoTextoVO) campo).getValor())) {
						campoTextoLargoDBEntity
								.insertValue((CampoTextoVO) campo);
					}
				} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
						|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
					if (!StringUtils.isEmpty(((CampoTextoVO) campo).getValor())) {
						campoTextoLargoUDocREDBEntity
								.insertValue((CampoTextoVO) campo);
					}
				} else {
					campoTextoLargoDescrDBEntity
							.insertValue((CampoTextoVO) campo);
				}
				break;
			case ValorCampoGenericoVO.TIPO_NUMERICO:
				if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
					campoNumeroDBEntity.insertValue((CampoNumericoVO) campo);
				else if (tipoFicha == TipoFicha.FICHA_UDOCRE
						|| tipoFicha == TipoFicha.FICHA_UDOCFS)
					campoNumeroUDocREDBEntity
							.insertValue((CampoNumericoVO) campo);
				else
					campoNumeroDescrDBEntity
							.insertValue((CampoNumericoVO) campo);
				break;
			case ValorCampoGenericoVO.TIPO_REFERENCIA:
				if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
					if (!StringUtils.isEmpty(((CampoReferenciaVO) campo)
							.getIdObjRef())) {
						campoReferenciaDBEntity
								.insertValue((CampoReferenciaVO) campo);
					}
				} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
						|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
					if (!StringUtils.isEmpty(((CampoReferenciaVO) campo)
							.getIdObjRef())) {
						campoReferenciaUDocREDBEntity
								.insertValue((CampoReferenciaVO) campo);
					}
				} else {
					if (!StringUtils.isEmpty(((CampoReferenciaVO) campo)
							.getIdObjRef())) {
						campoReferenciaDescrDBEntity
								.insertValue((CampoReferenciaVO) campo);
					}
				}
				break;
			}
		}
	}

	private void updateCampoEspecial(ValorCampoGenericoVO campo, int tipoFicha) {
		if (campo.getIdCampo().equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
			Valor valor = campo.getValorInfo();

			if (StringUtils.isNotEmpty(valor.getValor())
					&& !ConfigConstants.getInstance()
							.getDistinguirMayusculasMinusculas())
				valor.setValor(valor.getValor().toUpperCase());

			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				elementoCuadroClasificacionDBEntity.updateTitulo(
						campo.getIdObjeto(), valor.getValor());
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
				unidadDocumentalREDBEntity.updateAsunto(campo.getIdObjeto(),
						valor.getValor());
			else
				// tipoFicha == TipoFicha.FICHA_UDOCFS
				unidadDocumentalFSDBEntity.updateAsunto(campo.getIdObjeto(),
						valor.getValor());

		}

		if (campo.getIdCampo().equals(
				DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
			Valor valor = campo.getValorInfo();
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				unidadDocumentalDBEntity.updateNumeroExpediente(
						campo.getIdObjeto(), valor.getValor());
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
				unidadDocumentalREDBEntity.updateNumeroExpediente(
						campo.getIdObjeto(), valor.getValor());
			else
				// tipoFicha == TipoFicha.FICHA_UDOCFS
				unidadDocumentalFSDBEntity.updateNumeroExpediente(
						campo.getIdObjeto(), valor.getValor());
		}
	}

	private void updateFecha(int tipoFicha, ValorCampoGenericoVO campo) {

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			campoFechaDBEntity.updateValue((CampoFechaVO) campo);
		} else {
			if (tipoFicha == TipoFicha.FICHA_UDOCRE) {
				CampoFechaVO campoFecha = (CampoFechaVO) campo;
				campoFechaUDocREDBEntity.updateValue(campoFecha);
				// Actualizar tambiï¿½n la tabla de ASGTUDOCRE donde aparecen
				// estos campos
				if (campo.getIdCampo().equals(
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getFechaExtremaInicial())) {
					unidadDocumentalREDBEntity.updateFechaExtIni(
							campoFecha.getIdObjeto(), campoFecha.getFechaIni());
				} else {
					if (campo.getIdCampo().equals(
							ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getFechaExtremaFinal()))
						unidadDocumentalREDBEntity.updateFechaExtFin(
								campoFecha.getIdObjeto(),
								campoFecha.getFechaIni());
				}
			} else {
				if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
					CampoFechaVO campoFecha = (CampoFechaVO) campo;
					campoFechaUDocREDBEntity.updateValue((CampoFechaVO) campo);
					// Actualizar tambiï¿½n la tabla de ASGFUDOCENDIVISIONFS
					// donde aparecen estos campos
					if (campo.getIdCampo().equals(
							ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getFechaExtremaInicial())) {
						unidadDocumentalFSDBEntity.updateFechaExtIni(
								campoFecha.getIdObjeto(),
								campoFecha.getFechaIni());
					} else {
						if (campo.getIdCampo().equals(
								ConfiguracionSistemaArchivoFactory
										.getConfiguracionSistemaArchivo()
										.getConfiguracionDescripcion()
										.getFechaExtremaFinal()))
							unidadDocumentalFSDBEntity.updateFechaExtFin(
									campoFecha.getIdObjeto(),
									campoFecha.getFechaIni());
					}
				} else {
					campoFechaDescrDBEntity.updateValue((CampoFechaVO) campo);
				}
			}
		}
	}

	private void updateTextoCorto(int tipoFicha, ValorCampoGenericoVO campo) {
		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			if (!StringUtils.isEmpty(campo.getIdCampo())
					&& NumberUtils.isNumber(campo.getIdCampo())
					&& Integer.parseInt(campo.getIdCampo()) < 0) {
				updateCampoEspecial(campo, tipoFicha);
			} else {
				if (StringUtils.isEmpty(((CampoTextoVO) campo).getValor()))
					try {
						campoTextoCortoDBEntity.deleteValueXIdElemento(
								((CampoTextoVO) campo).getIdObjeto(),
								campo.getTipoElemento());
					} catch (Exception ex) {
						logger.warn("Error al eliminar elemento texto corto: " + campo, ex);
					}
				else
					campoTextoCortoDBEntity.updateValue((CampoTextoVO) campo);
			}
		} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
				|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
			if (!StringUtils.isEmpty(campo.getIdCampo())
					&& NumberUtils.isNumber(campo.getIdCampo())
					&& Integer.parseInt(campo.getIdCampo()) < 0) {
				updateCampoEspecial(campo, tipoFicha);
			} else {
				if (StringUtils.isEmpty(((CampoTextoVO) campo).getValor()))
					try {
						campoTextoCortoUDocREDBEntity.deleteValueXIdElemento(
								((CampoTextoVO) campo).getIdObjeto(),
								campo.getTipoElemento());
					} catch (Exception ex) {
						logger.warn("Error al eliminar elemento texto corto: " + campo, ex);
					}
				else
					campoTextoCortoUDocREDBEntity
							.updateValue((CampoTextoVO) campo);
			}
			// campoTextoCortoUDocREDBEntity.updateValue((CampoTextoVO)campo);
		} else
			campoTextoCortoDescrDBEntity.updateValue((CampoTextoVO) campo);
	}

	private void updateTextoLargo(int tipoFicha, ValorCampoGenericoVO campo) {
		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			if (!StringUtils.isEmpty(campo.getIdCampo())
					&& NumberUtils.isNumber(campo.getIdCampo())
					&& Integer.parseInt(campo.getIdCampo()) < 0) {
				updateCampoEspecial(campo, tipoFicha);
			} else {
				if (StringUtils.isEmpty(((CampoTextoVO) campo).getValor()))
					try {
						campoTextoLargoDBEntity.deleteValueXIdElemento(
								((CampoTextoVO) campo).getIdObjeto(),
								campo.getTipoElemento());
					} catch (Exception ex) {
						logger.warn("Error al eliminar elemento texto largo: " + campo, ex);
					}
				else
					campoTextoLargoDBEntity.updateValue((CampoTextoVO) campo);
			}
		} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
				|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
			if (!StringUtils.isEmpty(campo.getIdCampo())
					&& NumberUtils.isNumber(campo.getIdCampo())
					&& Integer.parseInt(campo.getIdCampo()) < 0) {
				updateCampoEspecial(campo, tipoFicha);
			} else {
				if (StringUtils.isEmpty(((CampoTextoVO) campo).getValor()))
					try {
						campoTextoLargoUDocREDBEntity.deleteValueXIdElemento(
								((CampoTextoVO) campo).getIdObjeto(),
								campo.getTipoElemento());
					} catch (Exception ex) {
						logger.warn("Error al eliminar elemento texto largo: " + campo, ex);
					}
				else
					campoTextoLargoUDocREDBEntity
							.updateValue((CampoTextoVO) campo);
			}
			// campoTextoLargoUDocREDBEntity.updateValue((CampoTextoVO)campo);
		} else
			campoTextoLargoDescrDBEntity.updateValue((CampoTextoVO) campo);
	}

	private void updateNumero(int tipoFicha, ValorCampoGenericoVO campo) {
		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
			campoNumeroDBEntity.updateValue((CampoNumericoVO) campo);
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE
				|| tipoFicha == TipoFicha.FICHA_UDOCFS)
			campoNumeroUDocREDBEntity.updateValue((CampoNumericoVO) campo);
		else
			campoNumeroDescrDBEntity.updateValue((CampoNumericoVO) campo);
	}

	private void updateReferencia(int tipoFicha, ValorCampoGenericoVO campo) {
		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			if (StringUtils.isEmpty(((CampoReferenciaVO) campo).getIdObjRef())) {
				try {
					campoReferenciaDBEntity.deleteValueXIdElemento(
							((CampoReferenciaVO) campo).getIdObjeto(),
							campo.getTipoElemento());
				} catch (Exception ex) {
					logger.warn("Error al borrar elemento referencia: " + campo, ex);
				}
			} else {
				campoReferenciaDBEntity.updateValue((CampoReferenciaVO) campo);
			}
		} else if (tipoFicha == TipoFicha.FICHA_UDOCRE
				|| tipoFicha == TipoFicha.FICHA_UDOCFS) {
			if (StringUtils.isEmpty(((CampoReferenciaVO) campo).getIdObjRef())) {
				try {
					campoReferenciaUDocREDBEntity.deleteValueXIdElemento(
							((CampoReferenciaVO) campo).getIdObjeto(),
							campo.getTipoElemento());
				} catch (Exception ex) {
					logger.warn("Error al borrar elemento referencia: " + campo, ex);
				}
			} else {
				campoReferenciaUDocREDBEntity
						.updateValue((CampoReferenciaVO) campo);
			}
			// campoReferenciaUDocREDBEntity.updateValue((CampoReferenciaVO)campo);
		} else{
			campoReferenciaDescrDBEntity.updateValue((CampoReferenciaVO) campo);
		}
	}

	/**
	 * Modificar un campo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Informaciï¿½n del campo.
	 */
	private void update(int tipoFicha, ValorCampoGenericoVO campo,
			Valor valorAnterior) {
		if (campo != null) {

			Locale locale = getServiceClient().getLocale();

			// Auditorï¿½a de la modificaciï¿½n
			AuditoriaDescripcion.auditaModificacionValorCampo(locale, this,
					tipoFicha, campo, valorAnterior);

			// Modificaciï¿½n del campo
			switch (campo.getTipo()) {
			case ValorCampoGenericoVO.TIPO_FECHA:
				updateFecha(tipoFicha, campo);
				break;
			case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
				updateTextoCorto(tipoFicha, campo);
				break;
			case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
				updateTextoLargo(tipoFicha, campo);
				break;
			case ValorCampoGenericoVO.TIPO_NUMERICO:
				updateNumero(tipoFicha, campo);
				break;
			case ValorCampoGenericoVO.TIPO_REFERENCIA:
				updateReferencia(tipoFicha, campo);
				break;
			}
		}
	}

	/**
	 * Elimina un campo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	private void remove(int tipoFicha, int tipoCampo, String id,
			String idCampo, String orden) {
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		// Auditorï¿½a de la eliminaciï¿½n
		Locale locale = getServiceClient().getLocale();
		AuditoriaDescripcion.auditaEliminacionValorCampo(locale, this,
				tipoFicha, id, idCampo, orden);

		// Eliminaciï¿½n del campo
		switch (tipoCampo) {
		case ValorCampoGenericoVO.TIPO_FECHA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campoFechaDBEntity
						.deleteValue(id, idCampo, orden, tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campoFechaUDocREDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else
				campoFechaDescrDBEntity.deleteValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campoTextoCortoDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campoTextoCortoUDocREDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else
				campoTextoCortoDescrDBEntity.deleteValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campoTextoLargoDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campoTextoLargoUDocREDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else
				campoTextoLargoDescrDBEntity.deleteValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_NUMERICO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campoNumeroDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campoNumeroUDocREDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else
				campoNumeroDescrDBEntity.deleteValue(id, idCampo, orden);
			break;
		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				campoReferenciaDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				campoReferenciaUDocREDBEntity.deleteValue(id, idCampo, orden,
						tipoElemento);
			else
				campoReferenciaDescrDBEntity.deleteValue(id, idCampo, orden);
			break;
		}
	}

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificaciï¿½n.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @return Lista de valores.
	 */
	public List getValues(int tipoFicha, int tipoCampo, String id) {
		List listaValores = null;
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		switch (tipoCampo) {
		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoTextoCortoDBEntity.getValues(id,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoTextoCortoUDocREDBEntity.getValues(id,
						tipoElemento);
			else
				listaValores = campoTextoCortoDescrDBEntity.getValues(id);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoTextoLargoDBEntity.getValues(id,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoTextoLargoUDocREDBEntity.getValues(id,
						tipoElemento);
			else
				listaValores = campoTextoLargoDescrDBEntity.getValues(id);
			break;
		case ValorCampoGenericoVO.TIPO_NUMERICO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoNumeroDBEntity.getValues(id, tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoNumeroUDocREDBEntity.getValues(id,
						tipoElemento);
			else
				listaValores = campoNumeroDescrDBEntity.getValues(id);
			break;
		case ValorCampoGenericoVO.TIPO_FECHA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoFechaDBEntity.getValues(id, tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoFechaUDocREDBEntity.getValues(id,
						tipoElemento);
			else
				listaValores = campoFechaDescrDBEntity.getValues(id);
			break;
		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoReferenciaDBEntity.getValues(id,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoReferenciaUDocREDBEntity.getValues(id,
						tipoElemento);
			else
				listaValores = campoReferenciaDescrDBEntity.getValues(id);
			break;
		}

		return listaValores;
	}

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificaciï¿½n.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(int tipoFicha, int tipoCampo, String id,
			String idCampo) {
		List listaValores = null;
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		switch (tipoCampo) {
		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoTextoCortoDBEntity.getValues(id, idCampo,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoTextoCortoUDocREDBEntity.getValues(id,
						idCampo, tipoElemento);
			else
				listaValores = campoTextoCortoDescrDBEntity.getValues(id,
						idCampo);
			break;
		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoTextoLargoDBEntity.getValues(id, idCampo,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoTextoLargoUDocREDBEntity.getValues(id,
						idCampo, tipoElemento);
			else
				listaValores = campoTextoLargoDescrDBEntity.getValues(id,
						idCampo);
			break;
		case ValorCampoGenericoVO.TIPO_NUMERICO:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoNumeroDBEntity.getValues(id, idCampo,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoNumeroUDocREDBEntity.getValues(id, idCampo,
						tipoElemento);
			else
				listaValores = campoNumeroDescrDBEntity.getValues(id, idCampo);
			break;
		case ValorCampoGenericoVO.TIPO_FECHA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoFechaDBEntity.getValues(id, idCampo,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoFechaUDocREDBEntity.getValues(id, idCampo,
						tipoElemento);
			else
				listaValores = campoFechaDescrDBEntity.getValues(id, idCampo);
			break;
		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				listaValores = campoReferenciaDBEntity.getValues(id, idCampo,
						tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				listaValores = campoReferenciaUDocREDBEntity.getValues(id,
						idCampo, tipoElemento);
			else
				listaValores = campoReferenciaDescrDBEntity.getValues(id,
						idCampo);
			break;
		}

		return listaValores;
	}

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificaciï¿½n.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return nï¿½mero de valores encontrados.
	 */
	public int countValues(int tipoFicha, int tipoCampo, String id,
			String idCampo) {
		// List listaValores = null;
		int numValores = 0;
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		switch (tipoCampo) {
		/*
		 * case ValorCampoGenericoVO.TIPO_TEXTO_CORTO: if (tipoFicha ==
		 * TipoFicha.FICHA_ELEMENTO_CF) listaValores = campoTextoCortoDBEntity.
		 * getValues(id, idCampo, tipoElemento); else if (tipoFicha ==
		 * TipoFicha.FICHA_UDOCRE || tipoFicha == TipoFicha.FICHA_UDOCFS)
		 * listaValores = campoTextoCortoUDocREDBEntity.getValues(id, idCampo,
		 * tipoElemento); else listaValores =
		 * campoTextoCortoDescrDBEntity.getValues(id, idCampo); break; case
		 * ValorCampoGenericoVO.TIPO_TEXTO_LARGO: if (tipoFicha ==
		 * TipoFicha.FICHA_ELEMENTO_CF) listaValores =
		 * campoTextoLargoDBEntity.getValues(id, idCampo, tipoElemento); else if
		 * (tipoFicha == TipoFicha.FICHA_UDOCRE || tipoFicha ==
		 * TipoFicha.FICHA_UDOCFS) listaValores =
		 * campoTextoLargoUDocREDBEntity.getValues(id, idCampo, tipoElemento);
		 * else listaValores = campoTextoLargoDescrDBEntity.getValues(id,
		 * idCampo); break; case ValorCampoGenericoVO.TIPO_NUMERICO: if
		 * (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) listaValores =
		 * campoNumeroDBEntity.getValues(id, idCampo, tipoElemento); else if
		 * (tipoFicha == TipoFicha.FICHA_UDOCRE || tipoFicha ==
		 * TipoFicha.FICHA_UDOCFS) listaValores =
		 * campoNumeroUDocREDBEntity.getValues(id, idCampo, tipoElemento); else
		 * listaValores = campoNumeroDescrDBEntity.getValues(id, idCampo);
		 * break; case ValorCampoGenericoVO.TIPO_FECHA: if (tipoFicha ==
		 * TipoFicha.FICHA_ELEMENTO_CF) listaValores =
		 * campoFechaDBEntity.getValues(id, idCampo, tipoElemento); else if
		 * (tipoFicha == TipoFicha.FICHA_UDOCRE || tipoFicha ==
		 * TipoFicha.FICHA_UDOCFS) listaValores =
		 * campoFechaUDocREDBEntity.getValues(id, idCampo, tipoElemento); else
		 * listaValores = campoFechaDescrDBEntity.getValues(id, idCampo); break;
		 */
		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
				numValores = campoReferenciaDBEntity.getCountValues(id,
						idCampo, tipoElemento);
			else if (tipoFicha == TipoFicha.FICHA_UDOCRE
					|| tipoFicha == TipoFicha.FICHA_UDOCFS)
				numValores = campoReferenciaUDocREDBEntity.getCountValues(id,
						idCampo, tipoElemento);
			break;
		}

		return numValores;
	}

	/**
	 * Obtiene la informaciï¿½n de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Informaciï¿½n de la ficha.
	 */
	public FichaVO getFicha(String id) {
		return fichaDBEntity.getFicha(id);
	}

	/**
	 * Obtiene la lista de fichas
	 *
	 * @return una lista de fichas
	 */
	public List getFichas() {
		List list = fichaDBEntity.getFichas();
		for (int i = 0; i < list.size(); i++) {
			FichaVO fichaVO = (FichaVO) list.get(i);
			fichaVO.setTipoNormaText(getTipoNormaText(fichaVO.getTipoNorma()));
		}
		return list;
	}

	/**
	 * Obtiene la informaciï¿½n bï¿½sica de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Informaciï¿½n bï¿½sica de la ficha.
	 */
	public InfoBFichaVO getInfoBFicha(String id) {
		return fichaDBEntity.getInfoBFicha(id);
	}

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 *
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas.
	 */
	public List getFichasByTiposNivel(int[] tiposNivel) {
		return fichaDBEntity.getFichasByTiposNivel(tiposNivel);
	}

	public List getFichasByTiposNivelIdFichaPref(List niveles) {
		return fichaDBEntity.getFichasByTiposNivelIdFichaPref(niveles);
	}

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNorma(int tipoNorma) {
		return fichaDBEntity.getFichasByTipoNorma(tipoNorma);
	}

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNormaYNiveles(int tipoNorma, int[] tiposNivel) {
		return fichaDBEntity
				.getFichasByTipoNormaYNiveles(tipoNorma, tiposNivel);
	}

	/**
	 * Obtiene la lista de campos de una ficha.
	 *
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @return Lista de campos ({@link CampoDatoVO}).
	 */
	public List getCamposBusquedaAvanzada(String idFicha) {
		// List campos = new ArrayList();
		Collection campos = new TreeSet(new Comparator() {

			public int compare(Object o1, Object o2) {
				return ((CampoDatoVO) o1).getNombre().compareTo(
						((CampoDatoVO) o2).getNombre());
			}
		});

		Locale locale = getServiceClient().getLocale();
		if (StringUtils.isNotBlank(idFicha)) {

			List camposFicha = getCamposFicha(idFicha);
			if (!CollectionUtils.isEmpty(camposFicha)) {

				List camposDato = campoDatoDbEntity
						.getCamposByTipoNorma(TipoNorma.NORMA_ISADG);

				CampoDatoBusquedaVO campo = null;
				CampoDatoVO campoDato = null;
				int index;
				for (int i = 0; i < camposFicha.size(); i++) {

					campo = (CampoDatoBusquedaVO) camposFicha.get(i);

					if (DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA
							.equals(campo.getId())) {
						campo.setNombre(Messages
								.getString(
										ArchivoDetails.DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA,
										locale));
						campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
						campos.add(campo);
					} else if (DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE
							.equals(campo.getId())) {
						campo.setNombre(Messages.getString(
								ArchivoDetails.DESCRIPCION_CAMPO_NUM_EXP,
								locale));
						campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
						campos.add(campo);
					} else if (DefTipos.CAMPO_ESPECIAL_ID_TITULO.equals(campo
							.getId())) {
						campo.setNombre(Messages.getString(
								ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO,
								locale));
						campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
						campos.add(campo);
					} else if (DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION
							.equals(campo.getId())) {
						campo.setNombre(Messages
								.getString(
										ArchivoDetails.DESCRIPCION_CAMPO_NIVEL_DESCRIPCION,
										locale));
						campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
						campos.add(campo);
					} else {
						index = Collections.binarySearch(camposDato,
								campo.getId(), new Comparator() {

									public int compare(Object o1, Object o2) {
										return ((CampoDatoVO) o1).getId()
												.compareTo((String) o2);
									}

								});
						if (index > -1) {
							campoDato = (CampoDatoVO) camposDato.get(index);
							campo.setNombre(campoDato.getNombre());
							campo.setTipo(campoDato.getTipo());
							campo.setIdTblPadre(campoDato.getIdTblPadre());
							campos.add(campo);
						}
					}
				}
			}
		} else {

			// Obtener todas la fichas
			List listaFichas = getFichas();

			if (!CollectionUtils.isEmpty(listaFichas)) {
				Iterator it = listaFichas.iterator();
				while (it.hasNext()) {
					FichaVO ficha = (FichaVO) it.next();

					List camposFicha = getCamposFicha(ficha.getId());
					if (!CollectionUtils.isEmpty(camposFicha)) {

						List camposDato = campoDatoDbEntity
								.getCamposByTipoNorma(TipoNorma.NORMA_ISADG);

						CampoDatoBusquedaVO campo = null;
						CampoDatoVO campoDato = null;
						int index;
						for (int i = 0; i < camposFicha.size(); i++) {

							campo = (CampoDatoBusquedaVO) camposFicha.get(i);

							if (DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA
									.equals(campo.getId())) {
								campo.setNombre(Messages
										.getString(
												ArchivoDetails.DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA,
												locale));
								campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
								campos.add(campo);
							} else if (DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE
									.equals(campo.getId())) {
								campo.setNombre(Messages
										.getString(
												ArchivoDetails.DESCRIPCION_CAMPO_NUM_EXP,
												locale));
								campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
								campos.add(campo);
							} else if (DefTipos.CAMPO_ESPECIAL_ID_TITULO
									.equals(campo.getId())) {
								campo.setNombre(Messages
										.getString(
												ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO,
												locale));
								campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
								campos.add(campo);
							} else if (DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION
									.equals(campo.getId())) {
								campo.setNombre(Messages
										.getString(
												ArchivoDetails.DESCRIPCION_CAMPO_NIVEL_DESCRIPCION,
												locale));
								campo.setTipo(DefTipos.TIPO_DATO_TEXTO_CORTO);
								campos.add(campo);
							} else {
								index = Collections.binarySearch(camposDato,
										campo.getId(), new Comparator() {

											public int compare(Object o1,
													Object o2) {
												return ((CampoDatoVO) o1)
														.getId().compareTo(
																(String) o2);
											}

										});
								if (index > -1) {
									campoDato = (CampoDatoVO) camposDato
											.get(index);
									campo.setNombre(campoDato.getNombre());
									campo.setTipo(campoDato.getTipo());
									campo.setIdTblPadre(campoDato
											.getIdTblPadre());
									campos.add(campo);
								}
							}
						}
					}
				}
			}
		}

		return new ArrayList(campos);
	}

	public List getCamposFicha(String idFicha) {
		List campos = new ArrayList();

		FichaVO ficha = getFicha(idFicha);
		if (ficha != null && StringUtils.isNotBlank(ficha.getDefinicion())) {
			XmlFacade xml = new XmlFacade(ficha.getDefinicion());
			NodeIterator nodeIt = xml
					.getNodeIterator("//Campo[@Tipo='Dato' or @Tipo='Especial']");
			CampoDatoBusquedaVO campo = null;
			for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
					.nextNode()) {
				campo = new CampoDatoBusquedaVO();
				campo.setId(XmlFacade.get(node, "Id/text()"));
				campo.setTipoReferencia(TypeConverter.toShort(XmlFacade.get(
						node, "Informacion_Especifica/Tipo_Referencia/text()"),
						DefTipos.TIPO_REFERENCIA_DESCONOCIDO));
				campo.setIdsListasDescriptoras(XmlFacade
						.get(node,
								"Informacion_Especifica/Validacion/Ids_Listas_Descriptoras/text()"));
				campos.add(campo);
			}
		}

		return campos;
	}

	// /**
	// * Compone una ficha de descripciï¿½n nueva
	// * @param id Identificador del objeto a describir.
	// * @param tipoFicha Tipo de ficha.
	// * @return ficha.
	// */
	// public Ficha createFichaNueva(String id, int tipoFicha)
	// {
	// Ficha ficha = null;
	//
	// if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF)
	// {
	// // Componer la ficha vacï¿½a
	// ficha = componerFichaElemento(
	// getElementoCuadroClasificacionVO(id), TipoAcceso.EDICION);
	// }
	// else // if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR)
	// {
	// // Componer la ficha vacï¿½a
	// ficha = componerFichaDescriptor(
	// getDescriptorVO(id), TipoAcceso.EDICION);
	// }
	//
	// return ficha;
	// }

	/**
	 * Compone una ficha de descripciï¿½n nueva
	 *
	 * @param id
	 *            Identificador del objeto a describir.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param defFicha
	 *            Definiciï¿½n de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @return ficha.
	 */
	public Ficha createFichaNueva(String id, int tipoFicha, DefFicha defFicha,
			DefFmtFicha defFmtFicha, String idFicha) {
		Ficha ficha = null;
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		ValoresFicha valoresFicha = ValoresFicha.getInstance(
				getServiceSession(), id, tipoFicha, false, tipoElemento);

		// Obtener los posibles formatos con los que mostrar la ficha
		List ltFormatos = DefFmtFichaFactory.getInstance(
				getServiceSession().getSessionOwner()).findFmtFichaAccesibles(
				idFicha, tipoFicha);

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			// Componer la ficha vacï¿½a
			ficha = componerFichaElemento(getElementoCuadroClasificacionVO(id),
					defFicha, defFmtFicha, valoresFicha, ltFormatos, false);
		} else {
			if (tipoFicha == TipoFicha.FICHA_UDOCRE) {
				ficha = componerFichaUDocRE(getUDocRE(id), defFicha,
						defFmtFicha, valoresFicha, ltFormatos, false);
			} else {
				if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
					ficha = componerFichaUDocFS(getUDocFS(id), defFicha,
							defFmtFicha, valoresFicha, ltFormatos, false);
				} else /* if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR) */{
					// Componer la ficha vacï¿½a
					ficha = componerFichaDescriptor(getDescriptorVO(id),
							defFicha, defFmtFicha, valoresFicha, ltFormatos,
							false);
				}
			}
		}

		return ficha;
	}

	/**
	 * Crea la ficha de descripciï¿½n y la inicializa con los valores por
	 * defecto.
	 *
	 * @param Ficha
	 *            Ficha de descripciï¿½n.
	 * @param setTieneDescripcion
	 *            Indica si se debe marcar como descrito el objeto al que se
	 *            refiere la descripciï¿½n que se va a generar
	 */
	public void createFicha(Ficha ficha, boolean setTieneDescripcion,
			Locale locale) {

		if (ficha == null)
			return;

		FichaFactory fichaFactory = FichaFactory
				.getInstance(getServiceSession());

		iniciarTransaccion();

		if (ficha.getTipo() == TipoFicha.FICHA_ELEMENTO_CF) {

			// Obtener el elemento del cuadro de clasificaciï¿½n
			ElementoCuadroClasificacionVO elementoCF = getElementoCuadroClasificacionVO(ficha
					.getId());

			// Comprobar si el elemento tiene una ficha asociada
			if (!elementoCF.tieneDescripcion()) {
				// Ejecutar el evento de antes de crear ficha
				ficha.ejecutarEvento(TipoEvento.ANTES_DE_CREAR_DESCRIPCION,
						ficha, locale);

				// Actualizar valores iniciales
				fichaFactory.actualizarValoresIniciales(ficha);

				// Ejecutar el evento de antes de salvar la descripcion
				ficha.ejecutarEvento(TipoEvento.ANTES_DE_SALVAR_DESCRIPCION,
						ficha, locale);

				// Salvar la ficha
				checkElements(ficha.getId(), ficha.getTipo(), ficha.getAreas(),
						null);

				// Ejecutar el evento de despuï¿½s de salvar la descripcion
				ficha.ejecutarEvento(TipoEvento.DESPUES_DE_SALVAR_DESCRIPCION,
						ficha, locale);

				if (setTieneDescripcion)
					// Marcar el elemento como descrito
					elementoCuadroClasificacionDBEntity
							.updateTieneDescrElementoCF(elementoCF.getId(),
									Constants.TRUE_STRING);
				// getServiceRepository().lookupGestionCuadroClasificacionBI().setTieneDescr(elementoCF.getId(),
				// true);
			}
		} else {
			if (ficha.getTipo() == TipoFicha.FICHA_UDOCRE) {
				// Obtener la unidad documental en relaciï¿½n de entrega
				transferencias.vos.UnidadDocumentalVO udoc = getUDocRE(ficha
						.getId());

				if (!udoc.getExtraInfo().getTieneDescripcion()) {
					// Ejecutar el evento de antes de crear ficha
					ficha.ejecutarEvento(TipoEvento.ANTES_DE_CREAR_DESCRIPCION,
							ficha, locale);

					// Actualizar valores iniciales
					fichaFactory.actualizarValoresIniciales(ficha);

					// Ejecutar el evento de antes de salvar la descripcion
					ficha.ejecutarEvento(
							TipoEvento.ANTES_DE_SALVAR_DESCRIPCION, ficha,
							locale);

					// Salvar la ficha
					checkElements(ficha.getId(), ficha.getTipo(),
							ficha.getAreas(), null);

					// Ejecutar el evento de despuï¿½s de salvar la descripcion
					ficha.ejecutarEvento(
							TipoEvento.DESPUES_DE_SALVAR_DESCRIPCION, ficha,
							locale);
				}
			} else {
				if (ficha.getTipo() == TipoFicha.FICHA_UDOCFS) {
					// Obtener la unidad documental en divisiï¿½n de fracciï¿½n
					// de serie
					UDocEnFraccionSerieVO udoc = getUDocFS(ficha.getId());

					if (!udoc.getXinfo().getTieneDescripcion()) {
						// Ejecutar el evento de antes de crear ficha
						ficha.ejecutarEvento(
								TipoEvento.ANTES_DE_CREAR_DESCRIPCION, ficha,
								locale);

						// Actualizar valores iniciales
						fichaFactory.actualizarValoresIniciales(ficha);

						// Ejecutar el evento de antes de salvar la descripcion
						ficha.ejecutarEvento(
								TipoEvento.ANTES_DE_SALVAR_DESCRIPCION, ficha,
								locale);

						// Salvar la ficha
						checkElements(ficha.getId(), ficha.getTipo(),
								ficha.getAreas(), null);

						// Ejecutar el evento de despuï¿½s de salvar la
						// descripcion
						ficha.ejecutarEvento(
								TipoEvento.DESPUES_DE_SALVAR_DESCRIPCION,
								ficha, locale);
					}
				} else // if (ficha.getTipo() == TipoFicha.FICHA_DESCRIPTOR)
				{
					// Obtener el descriptor
					DescriptorVO descriptor = getDescriptorVO(ficha.getId());

					// Comprobar si el elemento tiene una ficha asociada
					if (!Constants.TRUE_STRING.equals(descriptor
							.getTieneDescr())) {
						// Ejecutar el evento de antes de crear ficha
						ficha.ejecutarEvento(
								TipoEvento.ANTES_DE_CREAR_DESCRIPCION, ficha,
								locale);

						// Actualizar valores iniciales
						fichaFactory.actualizarValoresIniciales(ficha);

						// Salvar la ficha
						checkElements(ficha.getId(), ficha.getTipo(),
								ficha.getAreas(), null);

						if (setTieneDescripcion)
							// Marcar el elemento como descrito
							descriptorDBEntity.setTieneDescr(
									descriptor.getId(), Constants.TRUE_STRING);
						// setTieneDescrDescriptor(descriptor.getId(), true);
					}
				}
			}
		}

		// Auditorï¿½a
		AuditoriaDescripcion.auditaCreacionFichaElementoCuadro(locale, this,
				ficha);

		commit();
	}

	/**
	 * Inserta una nueva fichaVO y un nuevo formato FmtFichaVO. Actualiza la
	 * tabla de uso
	 *
	 * @param FichaVO
	 *            fichaVO
	 * @param FmtFichaVO
	 *            fmtFichaVO
	 * @param List
	 *            listaUsoObjeto
	 * @return FichaVO
	 */
	public FichaVO createFicha(FichaVO fichaVO, FmtFichaVO fmtFichaVO,
			List listaUsoObjeto) throws ActionNotAllowedException {

		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		if (fichaVO.getNombre() == null
				|| fichaVO.getNombre().trim().equals("")) {
			throw new ActionNotAllowedException(
					"El campo nombre no puede estar vacio",
					ArchivoErrorCodes.ERROR_CAMPO_FORMULARIO_VACIO,
					ArchivoModules.DESCRIPCION_MODULE);
		}

		if (fichaVO.getDefinicion() == null
				|| fichaVO.getDefinicion().trim().equals("")) {
			throw new ActionNotAllowedException(
					"El campo definicion no puede estar vacio",
					ArchivoErrorCodes.ERROR_CAMPO_FORMULARIO_VACIO,
					ArchivoModules.DESCRIPCION_MODULE);
		}

		// Auditoria
		AuditoriaDescripcion.auditaCreacionFicha(locale, this, fichaVO);

		fichaVO = fichaDBEntity.createFicha(fichaVO);
		getGestionRelacionesBI().resetMapDescrUDoc();

		fmtFichaVO.setIdFicha(fichaVO.getId());
		fmtFichaVO.setTipo(TipoFormato.CONSULTA_VALUE);
		fmtFichaVO.setNivelAcceso(NivelAcceso.PUBLICO);
		fmtFichaVO.setNombre(getNombreFormatoFicha(fichaVO.getNombre(), fmtFichaVO));
		fmtFichaDBEntity.createFmtFicha(fmtFichaVO);

		fmtFichaVO.setId(null);
		fmtFichaVO.setTipo(TipoFormato.CONSULTA_VALUE);
		fmtFichaVO.setNivelAcceso(NivelAcceso.ARCHIVO);
		fmtFichaVO.setNombre(getNombreFormatoFicha(fichaVO.getNombre(), fmtFichaVO));
		fmtFichaDBEntity.createFmtFicha(fmtFichaVO);

		fmtFichaVO.setId(null);
		fmtFichaVO.setIdFicha(fichaVO.getId());
		fmtFichaVO.setTipo(TipoFormato.EDICION_VALUE);
		fmtFichaVO.setNivelAcceso(NivelAcceso.PUBLICO);
		fmtFichaVO.setNombre(getNombreFormatoFicha(fichaVO.getNombre(), fmtFichaVO));
		fmtFichaDBEntity.createFmtFicha(fmtFichaVO);

		fmtFichaVO.setId(null);
		fmtFichaVO.setIdFicha(fichaVO.getId());
		fmtFichaVO.setTipo(TipoFormato.EDICION_VALUE);
		fmtFichaVO.setNivelAcceso(NivelAcceso.ARCHIVO);
		fmtFichaVO.setNombre(getNombreFormatoFicha(fichaVO.getNombre(), fmtFichaVO));
		fmtFichaDBEntity.createFmtFicha(fmtFichaVO);

		// Crear el mapeo de descripcion
		if (TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador() == fichaVO
				.getTipoNivel()) {
			try {
				MapDescUDocVO mapDescUDocVO;
				mapDescUDocVO = new MapDescUDocVO(fichaVO.getId(),
						getDefaultMapeo(fichaVO.getSubTipoNivel()));

				mapDescUDocDBEntity.insert(mapDescUDocVO);
			} catch (ConfigException e) {
				logger.error(e);
				throw new FichaException(
						"Error al obtener los mapeos de unidad documental / fracción de serie. Error de configuración");
			} catch (FileNotFoundException e) {
				logger.error(e);
				throw new FichaException(
						"Error al obtener los mapeos de unidad documental / fracción de serie. Fichero no encontrado");

			} catch (IOException e) {
				logger.error(e);
				throw new FichaException(
						"Error al obtener los mapeos de unidad documental / fracción de serie. Error al leer el fichero");

			}
		}

		if (listaUsoObjeto != null) {
			for (int i = 0; i < listaUsoObjeto.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) listaUsoObjeto.get(i);
				usoObjetoVO.setIdObjUsuario(fichaVO.getId());
			}
			usoObjetoDbEntity.create(listaUsoObjeto);
		}
		commit();
		return fichaVO;
	}

	public FichaVO duplicarFicha(String idFichaOrigen, FichaVO fichaVO) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Auditoria
		AuditoriaDescripcion.auditaCreacionFicha(locale, this, fichaVO);

		fichaVO = fichaDBEntity.createFicha(fichaVO);
		getGestionRelacionesBI().resetMapDescrUDoc();

		// Obtener los formatos
		List formatosFicha = fmtFichaDBEntity.getFmtsFicha(idFichaOrigen);

		if (formatosFicha != null) {
			for (Iterator iterator = formatosFicha.iterator(); iterator
					.hasNext();) {
				FmtFichaVO fmtFichaVO = (FmtFichaVO) iterator.next();

				if (fmtFichaVO != null) {
					fmtFichaVO.setIdFicha(fichaVO.getId());
					fmtFichaVO.setId(null);
					fmtFichaVO.setNombre(getNombreFormatoFicha(fichaVO.getNombre(), fmtFichaVO));
					fmtFichaDBEntity.createFmtFicha(fmtFichaVO);
				}
			}
		}

		// Insertar El Mapeo de Campos

		// Crear el mapeo de descripcion
		if (TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador() == fichaVO
				.getTipoNivel()) {

			MapDescUDocVO mapDescUDocVO = getMapeoDescripcion(idFichaOrigen);

			if (mapDescUDocVO != null) {
				mapDescUDocVO.setIdFicha(fichaVO.getId());
				mapDescUDocDBEntity.insert(mapDescUDocVO);
			}
		}
		commit();
		return fichaVO;
	}

	private String getNombreFormatoFicha(String nombreFicha, FmtFichaVO formatoFicha){
		StringBuilder str = new StringBuilder(nombreFicha);

		if(TipoFormato.CONSULTA_VALUE == formatoFicha.getTipo()){
			str.insert(0, "Consulta de ficha de ");
		}
		else if(TipoFormato.EDICION_VALUE == formatoFicha.getTipo()){
			str.insert(0, "Edicion de ficha de ");
		}

		if(NivelAcceso.ARCHIVO == formatoFicha.getNivelAcceso()){
			str.append(" - ")
			.append("Archivo");
		}else if(NivelAcceso.PUBLICO == formatoFicha.getNivelAcceso()){
			str.append(" - ")
			.append("Publico");
		}else if(NivelAcceso.RESTRINGIDO == formatoFicha.getNivelAcceso()){
			str.append(" - ")
			.append("Restringido");
		}

		return str.toString();
	}

	/**
	 * Actualiza la ficha pasada por parametro.
	 *
	 * @param FichaVO
	 *            fichaVO
	 * @param List
	 *            listaUsoObjeto
	 * @return FichaVO
	 */
	public FichaVO updateFicha(FichaVO fichaVO, List listaUsoObjeto)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		if (fichaVO.getNombre() == null
				|| fichaVO.getNombre().trim().equals("")) {
			throw new ActionNotAllowedException(
					"El campo nombre no puede estar vacï¿½o",
					ArchivoErrorCodes.ERROR_CAMPO_FORMULARIO_VACIO,
					ArchivoModules.DESCRIPCION_MODULE);
		}

		if (fichaVO.getDefinicion() == null
				|| fichaVO.getDefinicion().trim().equals("")) {
			throw new ActionNotAllowedException(
					"El campo definicion no puede estar vacï¿½o",
					ArchivoErrorCodes.ERROR_CAMPO_FORMULARIO_VACIO,
					ArchivoModules.DESCRIPCION_MODULE);
		}

		// try{
		// XmlFacade xmlFacade=new XmlFacade(fichaVO.getDefinicion());
		// }catch(Exception ex)
		// {
		// throw new ActionNotAllowedException(
		// "El xml de la definiciï¿½n de la ficha no estï¿½ bien formado",
		// ArchivoErrorCodes.ERROR_DEFINICION_FICHA_NO_BIEN_FORMADA,
		// ArchivoModules.DESCRIPCION_MODULE);
		// }

		// Auditorï¿½a
		Locale locale = getServiceClient().getLocale();
		AuditoriaDescripcion.auditaModificacionFicha(locale, this, fichaVO);

		fichaVO = fichaDBEntity.updateFicha(fichaVO);

		// Se elimina en aduso lo que estaba utilizando la ficha
		usoObjetoDbEntity.deleteByIdObjUsuario(fichaVO.getId());

		if (listaUsoObjeto != null) {
			for (int i = 0; i < listaUsoObjeto.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) listaUsoObjeto.get(i);
				usoObjetoVO.setIdObjUsuario(fichaVO.getId());
			}
			usoObjetoDbEntity.create(listaUsoObjeto);
		}
		commit();
		return fichaVO;
	}

	/**
	 * Crea la ficha de descripciï¿½n y la inicializa con los valores por
	 * defecto.
	 *
	 * @param id
	 *            Identificador del objeto a describir.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 */
	public void createFicha(String id, int tipoFicha) {
		if (logger.isDebugEnabled())
			logger.debug("createFicha(String id, int tipoFicha)");
		if (logger.isDebugEnabled())
			logger.debug("--id: " + id);
		if (logger.isDebugEnabled())
			logger.debug("--tipoFicha " + tipoFicha);

		Locale locale = getServiceClient().getLocale();

		Ficha ficha = null;

		if (logger.isDebugEnabled())
			logger.debug("antes de iniciarTransaccion()");
		iniciarTransaccion();
		if (logger.isDebugEnabled())
			logger.debug("despues de iniciarTransaccion()");

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			if (logger.isDebugEnabled())
				logger.debug("tipoFicha == TipoFicha.FICHA_ELEMENTO_CF");
			if (logger.isDebugEnabled())
				logger.debug("Obtener el elemento del cuadro de clasificacion");
			ElementoCuadroClasificacionVO elementoCF = getElementoCuadroClasificacionVO(id);
			if (logger.isDebugEnabled())
				logger.debug("elementoCF: " + elementoCF);

			if (logger.isDebugEnabled())
				logger.debug("Comprobar si el elemento tiene una ficha asociada");
			if (!elementoCF.tieneDescripcion()) {
				if (logger.isDebugEnabled())
					logger.debug("!elementoCF.tieneDescripcion()");
				if (logger.isDebugEnabled())
					logger.debug("Componer la ficha inicial");
				ficha = componerFichaElemento(elementoCF, TipoAcceso.EDICION,
						false);
				if (logger.isDebugEnabled())
					logger.debug("ficha Inicial:" + ficha);
			}
		} else {
			if (tipoFicha == TipoFicha.FICHA_UDOCRE) {
				transferencias.vos.UnidadDocumentalVO udoc = getUDocRE(id);
				if (!udoc.getExtraInfo().getTieneDescripcion()) {
					ficha = componerFichaUDocRE(udoc, TipoAcceso.EDICION, false);
				}
			} else {
				if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
					UDocEnFraccionSerieVO udocEnFSVO = getUDocFS(id);
					if (!udocEnFSVO.getXinfo().getTieneDescripcion()) {
						ficha = componerFichaUDocFS(udocEnFSVO,
								TipoAcceso.EDICION, false);
					}
				} else // if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR)
				{
					// Obtener el descriptor
					DescriptorVO descriptor = getDescriptorVO(id);

					// Comprobar si el elemento tiene una ficha asociada
					if (!Constants.TRUE_STRING.equals(descriptor
							.getTieneDescr())) {
						// Componer la ficha inicial
						ficha = componerFichaDescriptor(descriptor,
								TipoAcceso.EDICION, false);
					}
				}
			}
		}

		if (ficha != null) {
			createFicha(ficha, true, locale);
		}
		commit();
	}

	public String getDefaultMapeo(int tipo) throws ConfigException, IOException {
		if (ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE == tipo) {
			return ConfiguracionArchivoManager.getInstance()
					.getMapeoUnidadDocumental();
		} else if (ElementoCuadroClasificacion.SUBTIPO_CAJA == tipo) {
			return ConfiguracionArchivoManager.getInstance()
					.getMapeoFraccionSerie();
		}
		return null;
	}

	/**
	 * Guarda los cambios de la ficha en base de datos.
	 *
	 * @param ficha
	 *            Ficha a guardar.
	 */
	public void updateFicha(Ficha ficha) {

		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();

		if (ficha.getTipo() == TipoFicha.FICHA_ELEMENTO_CF) {
			// Comprobar permisos
			checkPermission(DescripcionSecurityManager.EDITAR_FICHA_ELEMENTO_ACTION);

			// Obtener el elemento del cuadro de clasificacion
			ElementoCuadroClasificacionVO elementoCF = getElementoCuadroClasificacionVO(ficha
					.getId());

			// Comprobar permisos sobre el elemento del cuadro de
			// clasificaciï¿½n
			if (!SecurityManagerBase.isAccessAllowed(getServiceClient(),
					TipoAcceso.EDICION, elementoCF.getNivelAcceso(),
					elementoCF.getIdArchivo(), elementoCF.getIdLCA(),
					AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION))
				throw new DescripcionSecurityException(
						"El usuario no puede modificar la ficha del elemento del cuadro de clasificaci\u00F3n");

			// Marcar el elemento como descrito
			getServiceRepository().lookupGestionCuadroClasificacionBI()
					.setTieneDescr(elementoCF.getId(), true);

		} else {
			if (ficha.getTipo() == TipoFicha.FICHA_UDOCRE
					|| ficha.getTipo() == TipoFicha.FICHA_UDOCFS) {

				// Comprobar permisos
				checkPermission(DescripcionSecurityManager.USO_FICHA_ALTA_TRANSFERENCIA_ACTION);
			} else // if (ficha.getTipo() == TipoFicha.FICHA_DESCRIPTOR)
			{
				// Comprobar permisos
				checkPermission(DescripcionSecurityManager.EDITAR_FICHA_DESCRIPTOR_ACTION);

				// Obtener el descriptor
				DescriptorVO descriptor = getDescriptorVO(ficha.getId());

				// Comprobar permisos sobre el elemento del cuadro de
				// clasificaciï¿½n
				if (!SecurityManagerBase.isAccessAllowed(getServiceClient(),
						TipoAcceso.EDICION, descriptor.getNivelAcceso(), null,
						descriptor.getIdLCA(),
						AppPermissions.EDICION_DESCRIPTOR))
					throw new DescripcionSecurityException(
							"El usuario no puede modificar la ficha del descriptor");

				// Marcar el elemento como descrito
				setTieneDescrDescriptor(descriptor.getId(), true);
			}
		}

		// Ejecutar el evento de antes de salvar ficha
		ficha.ejecutarEvento(TipoEvento.ANTES_DE_SALVAR_DESCRIPCION, ficha,
				locale);

		// Salvar la ficha
		checkElements(ficha.getId(), ficha.getTipo(), ficha.getAreas(), null);

		// Auditorï¿½a
		AuditoriaDescripcion.auditaModificacionFichaElementoCuadro(locale,
				this, ficha);

		// Ejecutar el evento de antes de salvar ficha
		ficha.ejecutarEvento(TipoEvento.DESPUES_DE_SALVAR_DESCRIPCION, ficha,
				locale);

		commit();
	}

	/**
	 * Comprueba si hay que salvar alguno de los elementos de la lista.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param elems
	 *            Lista de elementos.
	 */
	private void checkElements(String id, int tipoFicha, Elemento[] elems,
			Elemento tablaPadre) {

		for (int i = 0; i < elems.length; i++) {
			Elemento elem = elems[i];
			switch (elem.getTipo()) {
			case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
				checkElement(id, tipoFicha, (ElementoEtiquetaDato) elem,
						tablaPadre);
				break;

			case TiposElemento.TIPO_ELEMENTO_AREA:
			case TiposElemento.TIPO_ELEMENTO_CABECERA:
			case TiposElemento.TIPO_ELEMENTO_TABLA:
			case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:

				if (elem.getTipo() == TiposElemento.TIPO_ELEMENTO_TABLA
						|| elem.getTipo() == TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL) {

					checkElements(id, tipoFicha,
							((ContenedorElementos) elem).getElementos(), elem);
				}

				else {
					checkElements(id, tipoFicha,
							((ContenedorElementos) elem).getElementos(), null);
				}
				break;
			}
		}
	}

	/**
	 * Comprueba quï¿½ hay que hacer con el elemento.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param elem
	 *            Elemento.
	 */
	private void checkElement(String id, int tipoFicha,
			ElementoEtiquetaDato elem, Elemento tablaPadre) {
		Valor valor;
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

		if (tipoFicha == TipoFicha.FICHA_UDOCFS)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
		else if (tipoFicha == TipoFicha.FICHA_UDOCRE)
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;

		for (int contValores = elem.getTotalValores(); contValores > 0; contValores--) {
			valor = ((ElementoEtiquetaDato) elem).getValor(contValores - 1);
			// Establecemos el valor del tipo de elemento en funciï¿½n de la
			// ficha que tiene asociada
			valor.setTipoElemento(tipoElemento);

			if (valor.getAccion() != null) {
				if (logger.isDebugEnabled())
					logger.debug("Valor: " + valor.toString());

				if (TipoAccion.CREAR.equals(valor.getAccion())) {
					// Crear el valor
					insert(tipoFicha, createCampo(id, elem, valor));
					valor.setAccion(null);
				} else if (TipoAccion.MODIFICAR.equals(valor.getAccion())) {
					// Modificar el valor
					update(tipoFicha, createCampo(id, elem, valor),
							valor.getValorAnterior());
					valor.setAccion(null);
				} else if (TipoAccion.ELIMINAR.equals(valor.getAccion())) {
					// Eliminar el valor
					remove(tipoFicha, elem.getEdicion().getTipo(), id, elem
							.getEdicion().getId(), "" + valor.getOrden());

					String filasEliminadas = "";
					int fila;
					boolean eliminarValorDelXml = false;
					if (tablaPadre != null) {
						fila = valor.getOrden();
						filasEliminadas = ((ElementoTabla) tablaPadre)
								.getFilasABorrar();
						if (!StringUtils.isEmpty(filasEliminadas)
								&& (StringUtils.isTokenIn(filasEliminadas,
										String.valueOf(fila), ",") || fila > common.util.NumberUtils
										.getNumeroMayor(filasEliminadas)))

							eliminarValorDelXml = true;
					}

					if (tablaPadre == null || eliminarValorDelXml == true)
						((ElementoEtiquetaDato) elem)
								.removeValor(contValores - 1);
				}
			}
		}
	}

	/**
	 * Elimina la ficha de descripciï¿½n de un objeto.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 */
	public void deleteFicha(String id, int tipoFicha) {
		int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;
		iniciarTransaccion();

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			campoTextoCortoDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoTextoLargoDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoFechaDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoNumeroDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoReferenciaDBEntity.deleteValueXIdElemento(id, tipoElemento);
		} else if (tipoFicha == TipoFicha.FICHA_UDOCRE) {
			tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;
			campoTextoCortoUDocREDBEntity.deleteValueXIdElemento(id,
					tipoElemento);
			campoTextoLargoUDocREDBEntity.deleteValueXIdElemento(id,
					tipoElemento);
			campoFechaUDocREDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoNumeroUDocREDBEntity.deleteValueXIdElemento(id, tipoElemento);
			campoReferenciaUDocREDBEntity.deleteValueXIdElemento(id,
					tipoElemento);
		} else {
			if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
				tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS;
				campoTextoCortoUDocREDBEntity.deleteValueXIdElemento(id,
						tipoElemento);
				campoTextoLargoUDocREDBEntity.deleteValueXIdElemento(id,
						tipoElemento);
				campoFechaUDocREDBEntity.deleteValueXIdElemento(id,
						tipoElemento);
				campoNumeroUDocREDBEntity.deleteValueXIdElemento(id,
						tipoElemento);
				campoReferenciaUDocREDBEntity.deleteValueXIdElemento(id,
						tipoElemento);
			} else {
				campoTextoCortoDescrDBEntity.deleteValues(id);
				campoTextoLargoDescrDBEntity.deleteValues(id);
				campoFechaDescrDBEntity.deleteValues(id);
				campoNumeroDescrDBEntity.deleteValues(id);
				campoReferenciaDescrDBEntity.deleteValues(id);
			}
		}

		commit();
	}

	/**
	 * Elimina el conjunto de fichas indicado del sistema, junto a sus formatos
	 * de ficha correspondientes
	 *
	 * @param idsFichas
	 *            Conjunto de identificadores de ficha
	 */
	public void deleteFichas(String[] idsFichas) {
		if (isFichaEnUso(idsFichas))
			throw new FichaException("Alguna ficha en uso.");
		else {
			iniciarTransaccion();
			fichaDBEntity.deleteFichas(idsFichas);
			fmtFichaDBEntity.deleteFmtFichas(idsFichas);
			fmtPrefDBEntity.deleteFmtPrefFichas(idsFichas);
			usoObjetoDbEntity.deleteByIdObjUsuario(idsFichas);
			mapDescUDocDBEntity.delete(idsFichas);
			commit();
		}
	}

	/**
	 * Crea un campo con la informaciï¿½n del valor.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param elem
	 *            Elemento.
	 * @param valor
	 *            Valor del campo.
	 */
	private ValorCampoGenericoVO createCampo(String id,
			ElementoEtiquetaDato elem, Valor valor) {
		ValorCampoGenericoVO campo = null;

		switch (elem.getEdicion().getTipo()) {
		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			campo = new CampoTextoVO(id, elem.getEdicion().getId(),
					valor.getOrden(), elem.getEdicion().getTipo(),
					valor.getValor(), valor.getTipoElemento());
			break;

		case ValorCampoGenericoVO.TIPO_FECHA:
			CustomDate cd = new CustomDate(valor.getFormato(), valor.getAnio(),
					valor.getMes(), valor.getDia(),
					valor.getHoras(), valor.getMinutos(), valor.getSegundos(),
					valor.getSiglo(),
					valor.getSeparador(), valor.getCalificador());
			campo = new CampoFechaVO(id, elem.getEdicion().getId(),
					valor.getOrden(), valor.getValor(), cd.getMinDate(),
					cd.getMaxDate(), valor.getFormato(), valor.getSeparador(),
					valor.getCalificador(), valor.getTipoElemento());
			break;

		case ValorCampoGenericoVO.TIPO_NUMERICO:
			if (NumberUtils.isNumber(valor.getValor()))
				campo = new CampoNumericoVO(id, elem.getEdicion().getId(),
						valor.getOrden(), TypeConverter.toDouble(valor
								.getValor()), valor.getTipoMedida(),
						valor.getUnidadMedida(), valor.getTipoElemento());
			break;

		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			campo = new CampoReferenciaVO(
					id,
					elem.getEdicion().getId(),
					valor.getOrden(),
					valor.getTipoRef() == DefTipos.TIPO_REFERENCIA_ELEMENTO_CUADRO_CLASIFICACION ? CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF
							: CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR,
					valor.getIdRef(), valor.getTipoElemento());
			break;
		}

		return campo;
	}

	/**
	 * Obtiene un campo dato.
	 *
	 * @param id
	 *            Identificador del campo a obtener.
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDato(String id) {
		return campoDatoDbEntity.getCampoDato(id);
	}

	/**
	 * Obtiene un campo dato.
	 *
	 * @param nombre
	 *            del campo a obtener.
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoPorNombre(String nombre) {
		return campoDatoDbEntity.getCampoDatoPorNombre(nombre);
	}

	/**
	 * Obtiene un campo tabla.
	 *
	 * @param id
	 *            Identificador del campo a obtener.
	 * @return CampoDatoVO
	 */
	public CampoTablaVO getCampoTabla(String id) {
		return campoTablaDbEntity.getCampoTabla(id);
	}

	/**
	 * Obtiene un campo tabla.
	 *
	 * @param nombre
	 *            del campo a obtener.
	 * @return CampoTablaVO
	 */
	public CampoTablaVO getCampoTablaPorNombre(String nombre) {
		return campoTablaDbEntity.getCampoTablaPorNombre(nombre);
	}

	/**
	 * Obtiene el nombre de la lista de volumen.
	 *
	 * @param repositoriosEcm
	 *            Repositorios Ecm
	 * @param id
	 *            Identificador de la Repositorios Ecm
	 * @return Nombre de la lista de volumenes.
	 */
	private String getNombreRepositorioEcm(List repositoriosEcm, String id) {
		String nombre = null;

		if (ListUtils.isNotEmpty(repositoriosEcm)) {
			for (int i = 0; (nombre == null) && (i < repositoriosEcm.size()); i++) {
				IRepositorioEcmVO repositorioEcmVO = (IRepositorioEcmVO) repositoriosEcm
						.get(i);
				if (StringUtils.equals(id, repositorioEcmVO.getId()))
					nombre = repositorioEcmVO.getNombre();
			}
		} else {
			nombre = "--";
			logger.error("No están definidos los repositorios ecm");
		}

		return nombre;
	}

	/**
	 * Obtiene la definiciï¿½n del formato de ficha preferente del usuario.
	 *
	 * @param idFicha
	 *            Identificador de la definiciï¿½n de la ficha.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param tipo
	 *            Tipo de formato.
	 * @return Definiciï¿½n del formato de una ficha preferente.
	 */
	public FmtPrefFichaVO getFmtPrefFicha(String idFicha, String idUsuario,
			int tipo) {
		return fmtPrefDBEntity.getFmtPrefFicha(idFicha, idUsuario, tipo);
	}

	/**
	 * Obtiene la definiciï¿½n del formato de una ficha.
	 *
	 * @param id
	 *            Identificador de la definiciï¿½n del formato de la ficha.
	 * @return Definiciï¿½n del formato de una ficha.
	 */
	public FmtFichaVO getFmtFicha(String id) {
		return fmtFichaDBEntity.getFmtFicha(id);
	}

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas(String idFicha, int tipo) {
		return fmtFichaDBEntity.getFmtFichas(idFicha, tipo);
	}

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas() {
		return fmtFichaDBEntity.getFmtFichas();
	}

	/**
	 * Inserta un formato de ficha preferente para un usuario.
	 *
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void insertFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha) {
		fmtPrefDBEntity.insertFmtPrefFicha(fmtPrefFicha);
	}

	/**
	 * Modifica un formato de ficha preferente para un usuario.
	 *
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void updateFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha) {
		fmtPrefDBEntity.updateFmtPrefFicha(fmtPrefFicha);
	}

	// public List getElementos(String[] ids, BusquedaGeneralVO
	// busquedaGeneralVO)throws TooManyResultsException {
	public List getElementos(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException {

		// Hace falta? va aquï¿½?
		busquedaElementosVO.setServiceClient(getServiceClient());

		// return
		// elementoCuadroClasificacionDBEntity.getElementosCuadroClasificacionBRB(ids,busquedaElementosVO);
		return elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(ids, busquedaElementosVO,
						busqueda);
	}

	/**
	 * Obtiene el elemento del cuadro de clasificaciï¿½n.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificaciï¿½n.
	 * @return Elemento del cuadro de clasificaciï¿½n.
	 */
	protected ElementoCuadroClasificacionVO getElementoCuadroClasificacionVO(
			String idElementoCF) {
		if (StringUtils.isBlank(idElementoCF))
			throw new FichaException(
					"No se ha encontrado el identificador del elemento del cuadro de clasificaci\u00F3n");

		// Obtener la informaciï¿½n del elemento del cuadro de clasificaciï¿½n
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		ElementoCuadroClasificacionVO elementoCF = cuadroBI
				.getElementoCuadroClasificacion(idElementoCF);

		if (elementoCF == null) {
			logger.error("No se ha encontrado el elemento del cuadro de clasificaci\u00F3n con id "
					+ idElementoCF);
			throw new FichaException(
					"No se ha encontrado el elemento del cuadro de clasificaci\u00F3n");
		}

		return elementoCF;
	}

	/**
	 * Obtiene el descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	protected DescriptorVO getDescriptorVO(String idDescr) {
		if (StringUtils.isBlank(idDescr))
			throw new FichaException(
					"No se ha encontrado el identificador del descriptor");

		// Obtener la informaciï¿½n del descriptor
		DescriptorVO descriptor = descriptorDBEntity.getDescriptor(idDescr);

		if (descriptor == null) {
			logger.error("No se ha encontrado el descriptor con id " + idDescr);
			throw new FichaException(
					"No se ha encontrado el elemento del cuadro de clasificaci\u00F3n");
		}

		return descriptor;
	}

	protected void checkComposicionFicha(String id, int tipoAcceso,
			int tipoFicha, ActionObject accionSeguridadConsulta,
			ActionObject accionSeguridadEdicion, int nivelAcceso,
			String idArchivo, String idlca) {
		// Comprobar permisos y auditorï¿½a
		if (tipoAcceso == TipoAcceso.CONSULTA) {
			// Auditorï¿½a
			AuditoriaDescripcion.auditaVerDescripcion(this, tipoFicha, id);

			// Comprobar permisos
			checkPermission(accionSeguridadConsulta);

			if (tipoFicha != TipoFicha.FICHA_UDOCRE
					&& tipoFicha != TipoFicha.FICHA_UDOCFS) {
				// Comprobar permisos sobre el elemento
				if (!SecurityManagerBase.isAccessAllowed(getServiceClient(),
						tipoAcceso, nivelAcceso, idArchivo, idlca))
					throw new DescripcionSecurityException(
							"El usuario no puede consultar la ficha de descripci\u00F3n");
			}
		} else {
			// Comprobar permisos
			checkPermission(accionSeguridadEdicion);

			if (tipoFicha != TipoFicha.FICHA_UDOCRE
					&& tipoFicha != TipoFicha.FICHA_UDOCFS) {
				// Comprobar permisos sobre el elemento
				if (!SecurityManagerBase
						.isAccessAllowed(
								getServiceClient(),
								tipoAcceso,
								nivelAcceso,
								idArchivo,
								idlca,
								(tipoFicha == TipoFicha.FICHA_ELEMENTO_CF ? AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION
										: AppPermissions.EDICION_DESCRIPTOR)))
					throw new DescripcionSecurityException(
							"El usuario no puede editar la ficha de descripci\u00F3n");
			}
		}
	}

	/**
	 * Obtiene la definiciï¿½n de la ficha de descripciï¿½n.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link TipoFicha}).
	 * @return Definiciï¿½n de la ficha de descripciï¿½n.
	 */
	public DefFicha getDefFicha(String id, int tipoFicha) {
		DefFicha defFicha = null;
		Describible objeto = null;

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			// Obtener el elemento del cuadro de clasificaciï¿½n
			objeto = getElementoCuadroClasificacionVO(id);
		} else // if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR)
		{
			if (tipoFicha == TipoFicha.FICHA_UDOCRE) {
				objeto = getUDocRE(id);
			} else {
				if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
					objeto = getUDocFS(id);
				} else // if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR)
				{
					// Obtener el descriptor
					objeto = getDescriptorVO(id);
				}
			}
		}

		// Definiciï¿½n de la ficha de descripciï¿½n
		if ((objeto != null)
				&& StringUtils.isNotBlank(objeto.getIdFichaDescr()))
			defFicha = DefFichaFactory.getInstance(getServiceClient())
					.getDefFichaById(objeto.getIdFichaDescr());

		return defFicha;
	}

	/**
	 * Obtiene la definiciï¿½n de la ficha de descripciï¿½n.
	 *
	 * @param idFicha
	 *            Identificador de la ficha descriptiva
	 * @return Definiciï¿½n de la ficha de descripciï¿½n.
	 */
	public DefFicha getDefFichaById(String idFicha) {
		DefFicha defFicha = null;

		// Definiciï¿½n de la ficha de descripciï¿½n
		if (StringUtils.isNotEmpty(idFicha))
			defFicha = DefFichaFactory.getInstance(getServiceClient())
					.getDefFichaById(idFicha);

		return defFicha;
	}

	/**
	 * Compone una ficha de descripciï¿½n.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @param idFicha
	 *            Identificador de la ficha, se usarï¿½ para aquellos tipos de
	 *            ficha de objetos de los que no se puede extraer directamente
	 *            el idFicha a utilizar
	 * @return Ficha.
	 */
	// public Ficha componerFicha(String id, int tipoFicha, int tipoAcceso)
	public Ficha componerFicha(String id, int tipoFicha, int tipoAcceso,
			String idFicha, boolean exportacion) {
		Ficha ficha = null;

		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			// Obtener el elemento del cuadro de clasificaciï¿½n
			ElementoCuadroClasificacionVO elementoCF = getElementoCuadroClasificacionVO(id);

			// Auditorï¿½a y permisos
			checkComposicionFicha(id, tipoAcceso, tipoFicha,
					DescripcionSecurityManager.CONSULTAR_FICHA_ELEMENTO_ACTION,
					DescripcionSecurityManager.EDITAR_FICHA_ELEMENTO_ACTION,
					elementoCF.getNivelAcceso(), elementoCF.getIdArchivo(),
					elementoCF.getIdLCA());

			// Ficha de descripciï¿½n
			ficha = componerFichaElemento(elementoCF, tipoAcceso, exportacion);
		} else {
			if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR) {
				// Obtener el descriptor
				DescriptorVO descriptor = getDescriptorVO(id);

				// Auditorï¿½a y permisos
				checkComposicionFicha(
						id,
						tipoAcceso,
						tipoFicha,
						DescripcionSecurityManager.CONSULTAR_FICHA_DESCRIPTOR_ACTION,
						DescripcionSecurityManager.EDITAR_FICHA_DESCRIPTOR_ACTION,
						descriptor.getNivelAcceso(), null,
						descriptor.getIdLCA());

				// Ficha de descripciï¿½n
				ficha = componerFichaDescriptor(descriptor, tipoAcceso,
						exportacion);
			} else {
				if (tipoFicha == TipoFicha.FICHA_UDOCFS) {
					// Obtener la unidad documental en divisiï¿½n de fracciï¿½n
					// de serie
					UDocEnFraccionSerieVO udoc = getUDocFS(id);

					// Auditorï¿½a y permisos
					// Comprobar permisos, se necesita el permiso de uso de
					// fichas en alta para poder editar/ver la descripciï¿½n de
					// unidades en alta/relaciï¿½n
					checkPermission(DescripcionSecurityManager.USO_FICHA_ALTA_TRANSFERENCIA_ACTION);

					// Ficha de descripciï¿½n
					ficha = componerFichaUDocFS(udoc, tipoAcceso, exportacion);
				} else { // if (tipoFicha == TipoFicha.FICHA_UDOCRE)
							// Obtener la unidad documental en relaciï¿½n de
							// entrega
					transferencias.vos.UnidadDocumentalVO udoc = getUDocRE(id);

					// Auditorï¿½a y permisos
					// Comprobar permisos, se necesita el permiso de uso de
					// fichas en alta para poder editar/ver la descripciï¿½n de
					// unidades en alta/relaciï¿½n
					checkPermission(DescripcionSecurityManager.USO_FICHA_ALTA_TRANSFERENCIA_ACTION);
					/*
					 * checkComposicionFicha(id, tipoAcceso, tipoFicha,
					 * DescripcionSecurityManager
					 * .CONSULTAR_FICHA_DESCRIPTOR_ACTION,
					 * DescripcionSecurityManager
					 * .EDITAR_FICHA_DESCRIPTOR_ACTION, udoc.getNivelAcceso(),
					 * null, udoc.getIdLCA());
					 */

					// Ficha de descripciï¿½n
					ficha = componerFichaUDocRE(udoc, tipoAcceso, exportacion);
				}
			}
		}
		return ficha;
	}

	protected transferencias.vos.UnidadDocumentalVO getUDocRE(String id) {

		transferencias.vos.UnidadDocumentalVO unidadDocumental = null;

		// Si existe el id, estamos en la ediciï¿½n de una unidad documental en
		// relaciï¿½n existente, sino, es que se estï¿½ creando
		if (StringUtils.isNotEmpty(id)) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			unidadDocumental = relacionBI.getUnidadDocumentalConInfoDesc(id);
		} else {
			logger.error("No se ha encontrado la unidad documental en relaci\u00F3n de entrega con id "
					+ id);
			throw new FichaException(
					"No se ha encontrado la unidad documental en relaci\u00F3n de entrega");
		}

		return unidadDocumental;
	}

	protected UDocEnFraccionSerieVO getUDocFS(String id) {

		UDocEnFraccionSerieVO udocEnFSVO = null;

		// Si existe el id, estamos en la ediciï¿½n de una unidad documental en
		// divisiï¿½n de fracciï¿½n de serie existente, sino, es que se estï¿½
		// creando
		if (StringUtils.isNotEmpty(id)) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionFraccionSerieBI fraccionSerieBI = services
					.lookupGestionFraccionSerieBI();
			udocEnFSVO = fraccionSerieBI.getUDocEnDivisionFSConInfoDesc(id);
		} else {
			logger.error("No se ha encontrado la unidad documental en divisi\u00F3n de fracci\u00F3n de serie con id "
					+ id);
			throw new FichaException(
					"No se ha encontrado la unidad documental en divisi\u00F3n de fracci\u00F3n de serie");
		}

		return udocEnFSVO;
	}

	/**
	 * Compone la ficha de descripciï¿½n de una unidad documental en relaciï¿½n
	 * de entrega
	 *
	 * @param udoc
	 *            Unidad documental en relaciï¿½n de entrega
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @return Ficha.
	 */
	// public Ficha componerFichaUDocRE(transferencias.vos.UnidadDocumentalVO
	// udoc, String idFicha, int tipoAcceso)
	public Ficha componerFichaUDocRE(
			transferencias.vos.UnidadDocumentalVO udoc, int tipoAcceso,
			boolean exportacion) {
		// Comprobar si el elemento tiene una ficha asociada
		if (StringUtils.isEmpty(udoc.getIdFichaDescr())) {
			logger.error("La unidad documental en relaci\u00F3n o alta no tiene ninguna ficha asociada");
			throw new FichaException(
					"La unidad documental en relaci\u00F3n o alta no tiene ninguna ficha asociada");
		}

		// Componer la ficha
		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				udoc.getId(), udoc.getIdFichaDescr(), tipoAcceso,
				TipoFicha.FICHA_UDOCRE, getValoresEspecialesUDocRE(udoc), udoc,
				exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de una unidad documental en divisiï¿½n
	 * de fracciï¿½n de serie
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @param exportacion
	 * @return Ficha.
	 */
	// public Ficha componerFichaUDocRE(transferencias.vos.UnidadDocumentalVO
	// udoc, String idFicha, int tipoAcceso)
	public Ficha componerFichaUDocFS(UDocEnFraccionSerieVO udocEnFS,
			int tipoAcceso, boolean exportacion) {
		// Comprobar si el elemento tiene una ficha asociada
		if (StringUtils.isEmpty(udocEnFS.getIdFichaDescr())) {
			logger.error("La unidad documental en divisi\u00F3n no tiene ninguna ficha asociada");
			throw new FichaException(
					"La unidad documental en divisi\u00F3n no tiene ninguna ficha asociada");
		}

		// Componer la ficha
		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				udocEnFS.getIdUDoc(), udocEnFS.getIdFichaDescr(), tipoAcceso,
				TipoFicha.FICHA_UDOCFS, getValoresEspecialesUDocFS(udocEnFS),
				udocEnFS, exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un elemento del cuadro de
	 * clasificaciï¿½n.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @return Ficha.
	 */
	public Ficha componerFichaElemento(
			ElementoCuadroClasificacionVO elementoCF, int tipoAcceso,
			boolean exportacion) {
		if (logger.isDebugEnabled())
			logger.debug("componerFichaElemento(ElementoCuadroClasificacionVO elementoCF, int tipoAcceso)");

		// Comprobar si el elemento tiene una ficha asociada
		if (elementoCF.getIdFichaDescr() == null) {
			logger.error("El elemento del cuadro de clasificaci\u00F3n con id "
					+ elementoCF.getId() + " no tiene ninguna ficha asociada");
			throw new FichaException(
					"El elemento del cuadro de clasificaci\u00F3n no tiene ninguna ficha asociada");
		}

		// Componer la ficha
		Locale locale = getServiceClient().getLocale();

		return FichaFactory.getInstance(getServiceSession(), locale,
				elementoCF.getId()).componerFicha(elementoCF.getId(),
				elementoCF.getIdFichaDescr(), tipoAcceso,
				TipoFicha.FICHA_ELEMENTO_CF,
				getValoresEspecialesElemento(elementoCF), null, exportacion);
	}

	public Ficha componerFichaElemento(
			ElementoCuadroClasificacionVO elementoCF, int tipoAcceso,
			String idElementoCF, boolean exportacion) {
		// Comprobar si el elemento tiene una ficha asociada
		if (elementoCF.getIdFichaDescr() == null) {
			logger.error("El elemento del cuadro de clasificaci\u00F3n con id "
					+ elementoCF.getId() + " no tiene ninguna ficha asociada");
			throw new FichaException(
					"El elemento del cuadro de clasificaci\u00F3n no tiene ninguna ficha asociada");
		}

		// Componer la ficha
		Locale locale = getServiceClient().getLocale();
		return FichaFactory.getInstance(getServiceSession(), locale,
				idElementoCF).componerFicha(elementoCF.getId(),
				elementoCF.getIdFichaDescr(), tipoAcceso,
				TipoFicha.FICHA_ELEMENTO_CF,
				getValoresEspecialesElemento(elementoCF), null, exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un elemento del cuadro de
	 * clasificaciï¿½n.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @param defFicha
	 *            Definiciï¿½n de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param valoresFicha
	 *            Valores de la ficha.
	 * @param exportacion
	 * @return Ficha.
	 */
	public Ficha componerFichaElemento(
			ElementoCuadroClasificacionVO elementoCF, DefFicha defFicha,
			DefFmtFicha defFmtFicha, ValoresFicha valoresFicha,
			List ltFormatos, boolean exportacion) {
		// Aï¿½ade los valores especiales
		valoresFicha
				.addValoresEspeciales(getValoresEspecialesElemento(elementoCF));

		// Componer la ficha
		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				defFicha, defFmtFicha, valoresFicha, ltFormatos,
				defFmtFicha.isEditable(), exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @param exportacion
	 * @return Ficha.
	 */
	protected Ficha componerFichaDescriptor(DescriptorVO descriptor,
			int tipoAcceso, boolean exportacion) {
		// Comprobar si el elemento tiene una ficha asociada
		if (StringUtils.isEmpty(descriptor.getIdFichaDescr())) {
			ListaDescrVO listaDescriptora = null;
			if (StringUtils.isNotEmpty(descriptor.getIdLista()))
				listaDescriptora = getListaDescriptora(descriptor.getIdLista());

			if (listaDescriptora != null)
				descriptor.setIdFichaDescr(listaDescriptora
						.getIdFichaDescrPref());

			if (StringUtils.isEmpty(descriptor.getIdFichaDescr())) {
				logger.error("El descriptor con id " + descriptor.getId()
						+ " no tiene ninguna ficha asociada");
				throw new FichaException(
						"El descriptor no tiene ninguna ficha asociada");
			}
		}

		// Componer la ficha
		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				descriptor.getId(), descriptor.getIdFichaDescr(), tipoAcceso,
				TipoFicha.FICHA_DESCRIPTOR,
				getValoresEspecialesDescriptor(descriptor), null, exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @param defFicha
	 *            Definiciï¿½n de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param valoresFicha
	 *            Valores de la ficha.
	 * @param exportacion
	 * @return Ficha.
	 */
	protected Ficha componerFichaUDocRE(
			transferencias.vos.UnidadDocumentalVO udoc, DefFicha defFicha,
			DefFmtFicha defFmtFicha, ValoresFicha valoresFicha,
			List ltFormatos, boolean exportacion) {
		// Aï¿½ade los valores especiales
		valoresFicha.addValoresEspeciales(getValoresEspecialesUDocRE(udoc));

		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				defFicha, defFmtFicha, valoresFicha, ltFormatos,
				defFmtFicha.isEditable(), exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @param defFicha
	 *            Definiciï¿½n de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param valoresFicha
	 *            Valores de la ficha.
	 * @param exportacion
	 * @return Ficha.
	 */
	protected Ficha componerFichaUDocFS(UDocEnFraccionSerieVO udoc,
			DefFicha defFicha, DefFmtFicha defFmtFicha,
			ValoresFicha valoresFicha, List ltFormatos, boolean exportacion) {
		// Aï¿½ade los valores especiales
		valoresFicha.addValoresEspeciales(getValoresEspecialesUDocFS(udoc));

		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				defFicha, defFmtFicha, valoresFicha, ltFormatos,
				defFmtFicha.isEditable(), exportacion);
	}

	/**
	 * Compone la ficha de descripciï¿½n de un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @param defFicha
	 *            Definiciï¿½n de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param valoresFicha
	 *            Valores de la ficha.
	 * @param exportacion
	 * @return Ficha.
	 */
	protected Ficha componerFichaDescriptor(DescriptorVO descriptor,
			DefFicha defFicha, DefFmtFicha defFmtFicha,
			ValoresFicha valoresFicha, List ltFormatos, boolean exportacion) {
		// Aï¿½ade los valores especiales
		valoresFicha
				.addValoresEspeciales(getValoresEspecialesDescriptor(descriptor));

		return FichaFactory.getInstance(getServiceSession()).componerFicha(
				defFicha, defFmtFicha, valoresFicha, ltFormatos,
				defFmtFicha.isEditable(), exportacion);
	}

	/**
	 * Obtiene los valores especiales de un elemento del cuadro.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @return Valores especiales.
	 */
	private Map getValoresEspecialesUDocRE(
			transferencias.vos.UnidadDocumentalVO udoc) {
		// Valores especiales
		Map valoresEspeciales = new LinkedHashMap();

		// Cï¿½digo de referencia => no hay aï¿½n, porque no estï¿½ en el cuadro

		// Nivel de descripciï¿½n
		INivelCFVO nivel = null;
		if (StringUtils.isNotEmpty(udoc.getIdNivelDocumental())) {
			nivel = getGestionCuadroClasificacionBI().getNivelCF(
					udoc.getIdNivelDocumental());
			if (nivel != null)
				valoresEspeciales.put(
						DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION,
						nivel.getNombre());
		}

		// Nï¿½mero de Expediente
		if (nivel == null
				|| nivel.getSubtipo() != ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			if (StringUtils.isNotEmpty(udoc.getNumeroExpediente()))
				valoresEspeciales.put(
						DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE,
						udoc.getNumeroExpediente());
		}

		// Tï¿½tulo
		valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_TITULO,
				udoc.getAsunto());

		return valoresEspeciales;
	}

	/**
	 * Obtiene los valores especiales de un elemento del cuadro.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @return Valores especiales.
	 */
	private Map getValoresEspecialesUDocFS(UDocEnFraccionSerieVO udocEnFSVO) {
		// Valores especiales
		Map valoresEspeciales = new LinkedHashMap();

		// Cï¿½digo de referencia => no hay aï¿½n, porque no estï¿½ en el cuadro

		// Nivel de descripciï¿½n
		INivelCFVO nivel = null;
		if (StringUtils.isNotEmpty(udocEnFSVO.getIdNivelDocumental())) {
			nivel = getGestionCuadroClasificacionBI().getNivelCF(
					udocEnFSVO.getIdNivelDocumental());
			if (nivel != null)
				valoresEspeciales.put(
						DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION,
						nivel.getNombre());
		}

		// Nï¿½mero de Expediente
		if (nivel == null
				|| nivel.getSubtipo() != ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			if (StringUtils.isNotEmpty(udocEnFSVO.getNumExp()))
				valoresEspeciales.put(
						DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE,
						udocEnFSVO.getNumExp());
		}

		// Tï¿½tulo
		valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_TITULO,
				udocEnFSVO.getAsunto());

		return valoresEspeciales;
	}

	/**
	 * Obtiene los valores especiales de un elemento del cuadro.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @return Valores especiales.
	 */
	private Map getValoresEspecialesElemento(
			ElementoCuadroClasificacionVO elementoCF) {

		// Valores especiales
		Map valoresEspeciales = new LinkedHashMap();

		FondoVO fondo = fondoDbEntity.getFondoXId(elementoCF.getIdFondo());
		// a partir del FondoVO el FondoPO
		FondoPO fondoPO = (FondoPO) FondoToPOTransformer.getInstance(
				getServiceRepository()).transform(fondo);
		String codRefPersonalizado = FondosUtils
				.getCodigoReferenciaPorSecciones(elementoCF.getCodReferencia(),
						getServiceClient().isMostrarPaisProvinciaBackUp(),
						getServiceClient()
								.isMostrarArchivoCodigoClasificadoresBackUp(),
						fondoPO.getComunidad());

		// Cï¿½digo de referencia
		valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA,
				codRefPersonalizado);
		// elementoCF.getCodReferencia());

		// Nivel de descripciï¿½n
		INivelCFVO nivel = getGestionCuadroClasificacionBI().getNivelCF(
				elementoCF.getIdNivel());
		if (nivel != null)
			valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION,
					nivel.getNombre());

		// Tï¿½tulo
		valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_TITULO,
				elementoCF.getTitulo());

		// Nï¿½mero de Expediente
		if (elementoCF.getTipo() == ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL) {
			UnidadDocumentalVO udoc = getGestionUnidadDocumentalBI()
					.getUnidadDocumental(elementoCF.getId());
			if (udoc != null)
				valoresEspeciales.put(
						DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE,
						udoc.getNumExp());
		}

		return valoresEspeciales;
	}

	/**
	 * Obtiene los valores especiales de un elemento del cuadro.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @return Valores especiales.
	 */
	private Map getValoresEspecialesDescriptor(DescriptorVO descriptor) {
		// Valores especiales
		Map valoresEspeciales = new LinkedHashMap();

		// Nombre del descriptor
		valoresEspeciales.put(DefTipos.CAMPO_ESPECIAL_ID_NOMBRE_DESCRIPTOR,
				descriptor.getNombre());

		return valoresEspeciales;
	}

	/**
	 * Modifica los valores de la ficha.
	 *
	 * @param ficha
	 *            Ficha actual.
	 * @param parameters
	 *            Valores modificados.
	 * @param exportar
	 * @return Ficha modificada.
	 */
	public ActionErrors modificarValoresFicha(Ficha ficha, Map parameters) {
		Locale locale = getServiceClient().getLocale();

		// Modificar la ficha
		ActionErrors errores = FichaFactory.getInstance(getServiceSession())
				.updateValores(ficha, parameters, false);

		// Ejecutar el evento de despuï¿½s de validar la ficha
		if (errores.isEmpty())
			errores.add(ficha.ejecutarEvento(TipoEvento.AL_VALIDAR_DESCRIPCION,
					ficha, locale));

		return errores;
	}

	/*
	 * ============= Bï¿½SQUEDAS =============
	 */

	/**
	 * Obtiene la lista de elementos que cumplan los requisitos de la
	 * bï¿½squeda.
	 *
	 * @param busquedaGeneralVO
	 *            Informaciï¿½n del formulario de bï¿½squeda.
	 * @return Lista de elementos del cuadro de clasificaciï¿½n.
	 * @throws TooManyResultsException
	 *             si el nï¿½mero de resultados es excesivo.
	 */
	/*
	 * public List searchElementosCuadroClasificacion( BusquedaGeneralVO
	 * busquedaGeneralVO)
	 */
	public List searchElementosCuadroClasificacion(
			BusquedaElementosVO busquedaElementosVO)
			throws TooManyResultsException {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_FICHA_ELEMENTO_ACTION);

		Locale locale = getServiceClient().getLocale();

		// Auditorï¿½a
		// AuditoriaDescripcion.auditaBusquedaElementos(this,
		// busquedaGeneralVO);
		AuditoriaDescripcion.auditaBusquedaElementos(locale, this,
				busquedaElementosVO);

		// Si no estï¿½ activada la distinciï¿½n de mayï¿½sculas/minï¿½sculas,
		// no hacer el toUpper
		if (StringUtils.isNotEmpty(busquedaElementosVO.getTitulo())
				&& !ConfigConstants.getInstance()
						.getDistinguirMayusculasMinusculas())
			busquedaElementosVO.setTitulo(busquedaElementosVO.getTitulo()
					.toUpperCase());

		return elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(getServiceClient(),
						busquedaElementosVO);

		/*
		 * return elementoCuadroClasificacionDBEntity
		 * .getElementosCuadroClasificacion(getServiceClient(),
		 * busquedaGeneralVO);
		 */
	}

	/**
	 * Realiza la bï¿½squeda de autoridades en funciï¿½n de los parï¿½metros del
	 * formulario de bï¿½squedas.
	 *
	 * @param busquedaGeneralAutVO
	 *            Parï¿½metros del formulario de bï¿½squedas.
	 * @return Lista de autoridades.
	 * @throws TooManyResultsException
	 *             si el nï¿½mero de resultados es excesivo.
	 */
	public List searchAutoridades(BusquedaGeneralAutVO busquedaGeneralAutVO)
			throws TooManyResultsException {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_FICHA_DESCRIPTOR_ACTION);

		// Auditorï¿½a
		AuditoriaDescripcion.auditaBusquedaAutoridades(locale, this,
				busquedaGeneralAutVO);

		return descriptorDBEntity.getAutoridades(getServiceClient(),
				busquedaGeneralAutVO);
	}

	/*
	 * ====================== TABLAS DE VALIDACION ======================
	 */

	/**
	 * Obtiene la lista de tablas de validaciï¿½n.
	 *
	 * @return Lista de tablas de validaciï¿½n.
	 */
	public List getTablasValidacion() {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_TABLAS_VALIDACION_ACTION);

		return tablaValidacionDBEntity.getTablasValidacion();
	}

	/**
	 * Obtiene la tabla de validaciï¿½n.
	 *
	 * @param id
	 *            Identificador de la tabla de validaciï¿½n.
	 * @return Tabla de validaciï¿½n.
	 */
	public TablaValidacionVO getTablaValidacion(String id) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_TABLAS_VALIDACION_ACTION);

		return tablaValidacionDBEntity.getTablaValidacion(id);
	}

	public TablaValidacionVO getTablaValidacionByNombre(String nombre) {
		checkPermission(DescripcionSecurityManager.CONSULTAR_TABLAS_VALIDACION_ACTION);
		return tablaValidacionDBEntity.getTablaValidacionByNombre(nombre);
	}

	/**
	 * Crea una tabla de validaciï¿½n.
	 *
	 * @param tablaValidacion
	 *            Tabla de validaciï¿½n.
	 * @return Tabla de validaciï¿½n insertada.
	 */
	public TablaValidacionVO insertTablaValidacion(
			TablaValidacionVO tablaValidacion) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		// Auditorï¿½a
		LoggingEvent event = AuditoriaDescripcion.getLogginEvent(this,
				ArchivoActions.DESCRIPCION_MODULE_ALTA_LISTA_VALORES);

		iniciarTransaccion();

		// Crear la tabla de validaciï¿½n
		tablaValidacion = tablaValidacionDBEntity.insert(tablaValidacion);

		// Detalle de la auditorï¿½a
		AuditoriaDescripcion.auditaInsercionTablaValidacion(locale, event,
				tablaValidacion);

		commit();

		return tablaValidacion;
	}

	/**
	 * Modifica la tabla de validaciï¿½n.
	 *
	 * @param tablaValidacion
	 *            Tabla de validaciï¿½n.
	 */
	public void updateTablaValidacion(TablaValidacionVO tablaValidacion) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		iniciarTransaccion();

		// Auditorï¿½a
		AuditoriaDescripcion.auditaModificacionTablaValidacion(locale, this,
				tablaValidacion);

		tablaValidacionDBEntity.update(tablaValidacion);

		commit();
	}

	/**
	 * Elimina las tablas de validaciï¿½n.
	 *
	 * @param listaIds
	 *            Lista de identificadores de tablas de validaciï¿½n.
	 */
	public void deleteTablasValidacion(String[] listaIds) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		if (listaIds != null) {
			TablaValidacionVO tabla;
			for (int i = 0; i < listaIds.length; i++) {
				if (StringUtils.isNotBlank(listaIds[i])) {
					// Informaciï¿½n de la tabla
					tabla = getTablaValidacion(listaIds[i]);

					iniciarTransaccion();

					// Auditorï¿½a
					AuditoriaDescripcion.auditaEliminacionTablaValidacion(
							locale, this, tabla.getId(), tabla.getNombre());

					// Eliminar los valores de la tabla de validaciï¿½n
					validacionDBEntity.deleteByVldTblId(tabla.getId());

					// Eliminar la tabla de validaciï¿½n
					tablaValidacionDBEntity.delete(tabla.getId());

					commit();

				}
			}
		}
	}

	/*
	 * ================================= VALORES DE TABLAS DE VALIDACION
	 * =================================
	 */

	/**
	 * Obtiene la lista de valores de una tabla de validaciï¿½n.
	 *
	 * @param idTblVld
	 *            Identificador de la tabla de validaciï¿½n.
	 * @return Lista de valores.
	 */
	public List getValoresValidacion(String idTblVld) {
		return validacionDBEntity.getValoresTablaValidacion(idTblVld);
	}

	/**
	 * Obtiene el valor de la tabla de validaciï¿½n.
	 *
	 * @param id
	 *            Identificador del valor de la tabla de validaciï¿½n.
	 * @return Valor de la tabla de validaciï¿½n.
	 */
	public TextoTablaValidacionVO getValorTablaValidacion(String id) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_TABLAS_VALIDACION_ACTION);

		return validacionDBEntity.getValorTablaValidacion(id);
	}

	public TextoTablaValidacionVO getValorTablaValidacionByValor(String valor,
			String idTblvld) {

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_TABLAS_VALIDACION_ACTION);

		return validacionDBEntity.getValorTablaValidacionByValor(valor,
				idTblvld);
	}

	/**
	 * Crea un valor de la tabla de validaciï¿½n.
	 *
	 * @param valor
	 *            Valor de la tabla de validaciï¿½n.
	 * @return Valor insertado.
	 */
	public TextoTablaValidacionVO insertValorTablaValidacion(
			TextoTablaValidacionVO valor) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		// Auditorï¿½a
		LoggingEvent event = AuditoriaDescripcion.getLogginEvent(this,
				ArchivoActions.DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION);

		iniciarTransaccion();

		// Crear el descriptor
		valor = validacionDBEntity.insert(valor);

		// Detalle de la auditorï¿½a
		AuditoriaDescripcion.auditaInsercionValorTablaValidacion(locale, event,
				valor);

		commit();

		return valor;
	}

	/**
	 * Modifica el valor de la tabla de validaciï¿½n.
	 *
	 * @param valor
	 *            Valor de la tabla de validaciï¿½n.
	 * @param valor_antiguo
	 *            String que tenia antes de actualizarse.
	 */
	// public void updateValorTablaValidacion(TextoTablaValidacionVO valor,
	// String valor_antiguo)
	public void updateValorTablaValidacion(TextoTablaValidacionVO valor) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		iniciarTransaccion();

		// Auditorï¿½a
		AuditoriaDescripcion.auditaModificacionValorTablaValidacion(locale,
				this, valor);

		validacionDBEntity.update(valor);

		// Debemos actualizar el resto de tablas que utilizan dicho valor de
		// tabla de validacion
		// Lo haremos aqui para que este dentro de la misma transaccion por si
		// ocurre algun error que haga el rollback.
		// Solamente cuando lista de valores = FORMAS DOCUMENTALES
		/*
		 * String id_tblvld_formas_documentales =
		 * ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo().getConfiguracionTransferencias
		 * ().getIdTablaValidacionFormaDocumental();
		 * if(valor.getIdTblVld().equals(id_tblvld_formas_documentales)){
		 * relacionEntregaDBEntity.updateTipoDocumental(valor_antiguo,
		 * valor.getValor());
		 * unidadDocumentalDBEntity.updateTipoDocumental(valor_antiguo,
		 * valor.getValor());
		 * volumenSerieDBEntity.updateTipoDocumental(valor_antiguo,
		 * valor.getValor());
		 * ((TextoCortoDBEntityImpl)campoTextoCortoDBEntity).updateValor
		 * (valor_antiguo, valor.getValor());
		 * ((TextoCortoUDocREDBEntityImpl)campoTextoCortoUDocREDBEntity
		 * ).updateValor(valor_antiguo, valor.getValor()); }
		 */

		commit();
	}

	/**
	 * Elimina los valores de una tabla de validaciï¿½n.
	 *
	 * @param listaIds
	 *            Lista de identificadores de valores de una tabla de
	 *            validaciï¿½n.
	 */
	public void deleteValoresTablaValidacion(String[] listaIds) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_TABLAS_VALIDACION_ACTION);

		if (listaIds != null) {
			TextoTablaValidacionVO texto;
			for (int i = 0; i < listaIds.length; i++) {
				if (StringUtils.isNotBlank(listaIds[i])) {
					// Informaciï¿½n del valor
					texto = getValorTablaValidacion(listaIds[i]);

					iniciarTransaccion();

					// Auditorï¿½a
					AuditoriaDescripcion.auditaEliminacionValorTablaValidacion(
							locale, this, texto.getId(), texto.getValor(),
							texto.getNombreTblVld());

					// Eliminar el valor de validaciï¿½n
					validacionDBEntity.delete(texto.getId());

					commit();
				}
			}
		}
	}

	/*
	 * ===================== LISTAS DESCRIPTORAS =====================
	 */

	/**
	 * Obtiene la lista de listas descriptoras.
	 *
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras() {
		return catalogoListaDescriptoresDBEntity.getListasDescriptoras();
	}

	/**
	 * Obtiene la lista de listas descriptoras.
	 *
	 * @param ids
	 *            Array de identificadores de listas descriptoras.
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras(String[] ids) {
		return catalogoListaDescriptoresDBEntity.getListasDescriptoras(ids);
	}

	/**
	 * Obtiene la lista de listas descriptoras con informaciï¿½n extendida.
	 *
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasExt() {
		List listasDescriptoras = catalogoListaDescriptoresDBEntity
				.getListasDescriptorasExt();
		List repositoriosEcm = getServiceRepository()
				.lookupGestionDocumentosElectronicosBI().getRepositoriosEcm();

		for (int i = 0; i < listasDescriptoras.size(); i++) {
			ListaDescrVO listaDescriptora = (ListaDescrVO) listasDescriptoras
					.get(i);
			if (StringUtils.isNotEmpty(listaDescriptora.getIdRepEcmPref()))
				listaDescriptora.setNombreRepEcmPref(getNombreRepositorioEcm(
						repositoriosEcm, listaDescriptora.getIdRepEcmPref()));
		}

		return listasDescriptoras;
	}

	/**
	 * Obtiene las listas descriptoras en funciï¿½n del tipo de descriptores.
	 *
	 * @param tipoDescriptor
	 *            Tipo de descriptores ({@link descripcion.model.TipoDescriptor}
	 *            ).
	 * @return Listas descriptoras.
	 */
	public List getListasDescrByTipoDescriptor(int tipoDescriptor) {
		return catalogoListaDescriptoresDBEntity
				.getListasDescriptorasByTipoDescriptor(tipoDescriptor);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionDescripcionBI#getListasDescrByTipoDescrYFicha(int,
	 * java.lang.String)
	 */
	public List getListasDescrByTipoDescrOFicha(int tipoDescriptor,
			String idFicha) {

		return catalogoListaDescriptoresDBEntity
				.getListasDescrByTipoDescrOFicha(tipoDescriptor, idFicha);
	}

	/**
	 * Obtiene la lista descriptora.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptora(String id) {
		return catalogoListaDescriptoresDBEntity.getListaDescriptora(id);
	}

	/**
	 * Obtiene la lista descriptora con informaciï¿½n extendida.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptoraExt(String id) {
		ListaDescrVO listaDescriptora = catalogoListaDescriptoresDBEntity
				.getListaDescriptoraExt(id);
		if (StringUtils.isNotEmpty(listaDescriptora.getIdRepEcmPref()))
			listaDescriptora.setNombreRepEcmPref(getNombreRepositorioEcm(
					getServiceRepository()
							.lookupGestionDocumentosElectronicosBI()
							.getRepositoriosEcm(), listaDescriptora
							.getIdRepEcmPref()));

		return listaDescriptora;
	}

	/**
	 * Indica si una lista descriptora tiene descriptores.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Si tiene descriptores.
	 */
	public boolean tieneDescriptores(String id) {
		List descriptores = descriptorDBEntity.getDescriptores(
				getServiceClient(), id);
		return (descriptores.size() > 0);
	}

	/**
	 * Crea una lista descriptora.
	 *
	 * @param listaDescriptora
	 *            lista descriptora.
	 * @return Lista descriptora insertada.
	 */
	public ListaDescrVO insertListaDescriptora(ListaDescrVO listaDescriptora) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		iniciarTransaccion();

		// Auditorï¿½a
		LoggingEvent event = AuditoriaDescripcion.getLogginEvent(this,
				ArchivoActions.DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES);

		// Crear la lista descriptora
		listaDescriptora = catalogoListaDescriptoresDBEntity
				.insert(listaDescriptora);

		// Detalle de la auditorï¿½a
		AuditoriaDescripcion.auditaInsercionListaDescriptora(locale, event,
				listaDescriptora);

		commit();

		return listaDescriptora;
	}

	/**
	 * Modifica la lista descriptora.
	 *
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public void updateListaDescriptora(ListaDescrVO listaDescriptora) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		iniciarTransaccion();

		// Auditorï¿½a
		AuditoriaDescripcion.auditaModificacionListaDescriptora(locale, this,
				listaDescriptora);

		catalogoListaDescriptoresDBEntity.update(listaDescriptora);

		commit();
	}

	/**
	 * Elimina las listas descriptoras.
	 *
	 * @param listaIds
	 *            Lista de identificadores de listas descriptoras.
	 * @return Informaciï¿½n de la eliminaciï¿½n.
	 */
	public ResultadoRegistrosVO deleteListasDescriptoras(String[] listaIds) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		Locale locale = getServiceClient().getLocale();

		ResultadoRegistrosVO res = new ResultadoRegistrosVO();

		if (listaIds != null) {
			res.setNumRegistros(listaIds.length);

			StringOwnTokenizer tok = null;
			String id = null, nombre = null;
			List descriptores;
			DescriptorVO descriptor;

			for (int i = 0; i < listaIds.length; i++) {
				tok = new StringOwnTokenizer(listaIds[i], "#");
				if (tok.hasMoreTokens())
					id = tok.nextToken();
				if (tok.hasMoreTokens())
					nombre = tok.nextToken();

				// Comprobar que no estï¿½ referenciado algï¿½n descriptor
				if (!isDescriptorReferenciadoEnLista(id)) {
					iniciarTransaccion();

					// Auditorï¿½a
					AuditoriaDescripcion.auditaEliminacionListaDescriptora(
							locale, this, id, nombre);

					// Eliminar los descriptores de la lista descriptora
					descriptores = getDescriptores(id);
					for (int j = 0; j < descriptores.size(); j++) {
						descriptor = (DescriptorVO) descriptores.get(j);
						// String
						// nombreLista=getListaDescriptora(descriptor.getIdLista()).getNombre();
						deleteDescriptor(descriptor.getId(),
								descriptor.getNombre(), nombre);
					}

					// Baja de la lista
					catalogoListaDescriptoresDBEntity.delete(id);

					commit();

					res.setNumRegistrosTratados(res.getNumRegistrosTratados() + 1);
				} else
					res.getErrores()
							.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionError(
											ErrorKeys.ERROR_DESCRIPCION_LISTA_DESCRIPTORA_EN_USO,
											new Object[] { nombre }));
			}
		}

		return res;
	}

	/**
	 * Indica si la lista descriptora tiene algï¿½n descriptor referenciado
	 * desde una ficha de descripciï¿½n o desde el cuadro de clasificaciï¿½n.
	 *
	 * @param idLista
	 *            Identificador de la lista descriptora.
	 * @return true si la lista tiene descriptores referenciados.
	 */
	protected boolean isDescriptorReferenciadoEnLista(String idLista) {
		// Referencias en las fichas de descripciï¿½n de elementos
		if (campoReferenciaDBEntity.countReferencesInList(idLista) > 0)
			return true;

		// Referencias en las fichas de descripciï¿½n de descriptores
		if (campoReferenciaDescrDBEntity.countReferencesInList(idLista) > 0)
			return true;

		// Referencias en los productores de fondos
		if (fondoDbEntity.countReferencesEntProdInList(idLista) > 0)
			return true;

		// Referencias en los ï¿½rganos productores de series
		if (organoProductorDbEntity.countReferencesDescriptorInList(idLista) > 0)
			return true;

		return false;
	}

	/*
	 * ============== DESCRIPTORES ==============
	 */

	/**
	 * Obtiene los descriptores de una lista de descriptores.
	 *
	 * @param idListaDescriptores
	 *            Identificador de la lista de descriptores.
	 * @return Descriptores.
	 */
	public List getDescriptores(String idListaDescriptores) {
		return descriptorDBEntity.getDescriptores(getServiceClient(),
				idListaDescriptores);
	}

	/**
	 * Obtiene los descriptores de una lista de descriptores con informaciï¿½n
	 * extendida.
	 *
	 * @param idListaDescriptores
	 *            Identificador de la lista de descriptores.
	 * @param pageInfo
	 *            Informaciï¿½n de la paginaciï¿½n.
	 * @return Descriptores.
	 * @throws TooManyResultsException
	 *             si el nï¿½mero de resultados excede el mï¿½ximo.
	 */
	public List getDescriptoresExt(String idListaDescriptores, PageInfo pageInfo)
			throws TooManyResultsException {
		List descriptores = descriptorDBEntity.getDescriptoresExt(
				getServiceClient(), idListaDescriptores, pageInfo);
		List repositoriosEcm = getServiceRepository()
				.lookupGestionDocumentosElectronicosBI().getRepositoriosEcm();

		Iterator it = descriptores.iterator();
		while (it.hasNext()) {
			DescriptorVO descriptor = (DescriptorVO) it.next();
			if (StringUtils.isNotBlank(descriptor.getIdRepEcm()))
				descriptor.setNombreRepEcm(getNombreRepositorioEcm(
						repositoriosEcm, descriptor.getIdRepEcm()));
		}

		return descriptores;
	}

	/**
	 * Obtiene la informaciï¿½n de un descriptor con informaciï¿½n extendida.
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptorExt(String idDescriptor) {
		DescriptorVO descriptor = descriptorDBEntity.getDescriptorExt(
				getServiceClient(), idDescriptor);
		if (StringUtils.isNotEmpty(descriptor.getIdRepEcm()))
			descriptor.setNombreRepEcm(getNombreRepositorioEcm(
					getServiceRepository()
							.lookupGestionDocumentosElectronicosBI()
							.getRepositoriosEcm(), descriptor.getIdRepEcm()));

		return descriptor;
	}

	/**
	 * Obtiene la informaciï¿½n de un descriptor.
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idDescriptor) {
		return descriptorDBEntity.getDescriptor(idDescriptor);
	}

	// /**
	// * Obtiene los descriptores cuyo valor contiene el patrï¿½n de bï¿½squeda
	// indicado
	// * @param pattern Patrï¿½n que debe contener el valor de los descriptores
	// * @param idListaDescriptora Identificador de la lista descriptora
	// * a la que deben pertenecer los descriptores. Pude ser nulo.
	// * @param sistemaExterno Sistema externo que mantiene el valor del
	// descriptor. Puede ser nulo
	// * @return Conjunto de descriptores que verifican las condiciones {@link
	// DescriptorVO}
	// */
	// public List findDescriptores(String pattern, String idListaDescriptora,
	// String sistemaExterno) {
	// return descriptorDBEntity.findDescriptores(pattern, idListaDescriptora,
	// sistemaExterno);
	// }

	/**
	 * Obtiene una lista de descriptores a partir de su nombre.
	 *
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Lista de descriptores.
	 */
	public List getDescriptoresByNombre(String nombre) {
		return descriptorDBEntity.getDescriptoresXNombre(nombre);
	}

	/**
	 * Obtiene una lista de descriptores a partir de su nombre y el id de la
	 * lista.
	 *
	 * @param nombre
	 *            Nombre del descriptor. idLista, identificador de la lista
	 * @return Lista de descriptores.
	 */
	public DescriptorVO getDescriptorByNombreYIdLista(String nombre,
			String idLista) {
		return descriptorDBEntity.getDescriptorXNombreYIdLista(nombre, idLista);
	}

	/**
	 * Busca los descriptores en funciï¿½n de unos criterios.
	 *
	 * @param criterios
	 *            Criterios de bï¿½squeda.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el nï¿½mero de resultados es excesivo.
	 */
	public List getDescriptores(BusquedaDescriptoresVO criterios)
			throws TooManyResultsException {
		return descriptorDBEntity
				.getDescriptores(getServiceClient(), criterios);
	}

	/**
	 * Obtiene los descriptores de una lista de descriptores y en cuyo valor
	 * estï¿½ contenido el patrï¿½n que se suministra
	 *
	 * @param idListaDescriptores
	 *            Identificador de lista de descriptores
	 * @param pattern
	 *            Patrï¿½n a buscar
	 * @return Lista de descriptores {@link DescriptorVO}
	 */
	public List findDescriptores(String idListaDescriptores, String pattern) {
		return descriptorDBEntity
				.findDescriptores(pattern, idListaDescriptores);
	}

	/**
	 * Inserta un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @param exportacion
	 * @return Descriptor insertado.
	 */
	public DescriptorVO insertDescriptor(DescriptorVO descriptor) {

		// Comprobar permisos
		try {
			checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);
		} catch (Exception e) {
			throw new DescriptorRightsException(
					"El usuario no tiene permisos para realizar la operaciï¿½n");
		}

		Locale locale = getServiceClient().getLocale();

		// Inicializar valores
		descriptor.setTieneDescr(Constants.FALSE_STRING);
		descriptor.setTimestamp(new Date());

		iniciarTransaccion();

		// Auditorï¿½a
		LoggingEvent event = AuditoriaDescripcion.getLogginEvent(this,
				ArchivoActions.DESCRIPCION_MODULE_ALTA_DESCRIPTOR);

		// Crear el descriptor
		descriptor = descriptorDBEntity.insertDescriptorVO(descriptor);

		// Crear la ficha del descriptor
		if (StringUtils.isNotBlank(descriptor.getIdFichaDescr()))
			createFicha(descriptor.getId(), TipoFicha.FICHA_DESCRIPTOR);

		// Detalle de la auditorï¿½a
		AuditoriaDescripcion.auditaInsercionDescriptor(locale, event,
				descriptor);

		commit();

		return descriptor;

	}

	/**
	 * Modifica un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 */
	public void updateDescriptor(DescriptorVO descriptor) {
		Locale locale = getServiceClient().getLocale();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		// Actualizar valores
		descriptor.setTimestamp(new Date());

		iniciarTransaccion();

		// Auditorï¿½a
		AuditoriaDescripcion.auditaModificacionDescriptor(locale, this,
				descriptor);

		// Modificar el descriptor
		descriptorDBEntity.updateDescriptorVO(descriptor);

		// Crear la ficha del descriptor
		if (Constants.FALSE_STRING.equals(descriptor.getTieneDescr())
				&& StringUtils.isNotBlank(descriptor.getIdFichaDescr()))
			createFicha(descriptor.getId(), TipoFicha.FICHA_DESCRIPTOR);

		commit();
	}

	/**
	 * Establece si un descriptor tiene ficha de descripciï¿½n.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param tieneDescr
	 *            Si el descriptor tiene ficha de descripciï¿½n.
	 */
	public void setTieneDescrDescriptor(String idDescr, boolean tieneDescr) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		iniciarTransaccion();
		descriptorDBEntity.setTieneDescr(idDescr,
				DBUtils.getStringValue(tieneDescr));
		commit();
	}

	/**
	 * Establece si se han creado los clasificadores por defecto de un
	 * descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param editClfDocs
	 *            Si se han creado los clasificadores por defecto.
	 */
	public void setEditClfDocs(String idDescr, boolean editClfDocs) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		iniciarTransaccion();
		descriptorDBEntity.setEditClfDocs(idDescr,
				DBUtils.getStringValue(editClfDocs));
		commit();
	}

	/**
	 * Elimina los descriptores.
	 *
	 * @param listaIds
	 *            Lista de identificadores de descriptores.
	 * @return Informaciï¿½n de la eliminaciï¿½n.
	 */
	public ResultadoRegistrosVO deleteDescriptores(String[] listaIds) {
		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

		ResultadoRegistrosVO res = new ResultadoRegistrosVO();

		if (listaIds != null) {
			res.setNumRegistros(listaIds.length);

			DescriptorVO descriptor;

			for (int i = 0; i < listaIds.length; i++) {
				if (StringUtils.isNotBlank(listaIds[i])) {
					descriptor = getDescriptor(listaIds[i]);

					// Comprobar que no estï¿½ referenciado
					if (!isDescriptorReferenciado(descriptor.getId())) {
						String nombreLista = getListaDescriptora(
								descriptor.getIdLista()).getNombre();
						deleteDescriptor(descriptor.getId(),
								descriptor.getNombre(), nombreLista);
						res.setNumRegistrosTratados(res
								.getNumRegistrosTratados() + 1);
					} else
						res.getErrores()
								.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionError(
												ErrorKeys.ERROR_DESCRIPCION_DESCRIPTOR_EN_USO,
												new Object[] { descriptor
														.getNombre() }));
				}
			}
		}

		return res;
	}

	/**
	 * Elimina un descriptor.
	 *
	 * @param id
	 *            Identificador del descriptor.
	 * @param nombre
	 *            Nombre del descriptor.
	 * @param nombreLista
	 *            Nombre de la lista descriptora.
	 */
	protected void deleteDescriptor(String id, String nombre, String nombreLista) {

		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Auditorï¿½a
		AuditoriaDescripcion.auditaEliminacionDescriptor(locale, this, id,
				nombre, nombreLista);

		// Eliminar la informaciï¿½n de la ficha de descripciï¿½n
		deleteFicha(id, TipoFicha.FICHA_DESCRIPTOR);

		// Eliminar clasificadores y documentos
		GestionDocumentosElectronicosBI documentosBI = getGestionDocumentosElectronicosBI();
		documentosBI.deleteDocumentos(id, TipoObjeto.DESCRIPTOR);

		// Eliminar las tareas de captura relacionadas
		documentosBI.eliminarTareas(id,
				ElementoCuadroClasificacion.TIPO_DESCRIPTOR);

		// Eliminar descriptor
		descriptorDBEntity.deleteDescriptor(id);

		commit();
	}

	/**
	 * Indica si se estï¿½ referenciado un descriptor desde una ficha de
	 * descripciï¿½n o desde el cuadro de clasificaciï¿½n.
	 *
	 * @param id
	 *            Identificador del descriptor.
	 * @return true si el descriptor estï¿½ siendo referenciado.
	 */
	protected boolean isDescriptorReferenciado(String id) {
		// Referencias en las fichas de descripciï¿½n de elementos
		if (campoReferenciaDBEntity.countReferences(1, id) > 0)
			return true;

		// Referencias en las fichas de descripciï¿½n de descriptores
		if (campoReferenciaDescrDBEntity.countReferences(1, id) > 0)
			return true;

		// Referencias en los productores de fondos
		if (fondoDbEntity.countReferencesEntProd(id) > 0)
			return true;

		// Referencias en los ï¿½rganos productores de series
		if (organoProductorDbEntity.countReferencesDescriptor(id) > 0)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionDescripcionBI#getDescriptoresXListaAcceso(java.lang.
	 * String)
	 */
	public List getDescriptoresXListaAcceso(String idListaAcceso) {
		return descriptorDBEntity.getDescriptoresXListaAcceso(idListaAcceso);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionDescripcionBI#getFmtFichas(java.lang.String)
	 */
	public List getFmtsFichasXListaAcceso(String idListaAcceso) {
		return fmtFichaDBEntity.getFmtsFichasXListaAcceso(idListaAcceso);
	}

	/**
	 * Obtiene un area perteneciente al id pasado por parametro
	 *
	 * @param id
	 *            Identificador del descriptor.
	 * @return AreaVO area
	 */
	public AreaVO getArea(String id) {
		return areaDbEntity.getArea(id);
	}

	/**
	 * Obtiene un area perteneciente al nombre pasado por parametro
	 *
	 * @param nombre
	 *            Identificador del descriptor.
	 * @return AreaVO area
	 */
	public AreaVO getAreaPorNombre(String nombre) {
		return areaDbEntity.getAreaPorNombre(nombre);
	}

	/**
	 * Obtiene la lista de areas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma.
	 * @return Lista de areas.
	 */
	public List getAreasByTipoNorma(int tipoNorma) {
		return areaDbEntity.getAreasByTipoNorma(tipoNorma);
	}

	/**
	 * Obtiene la lista de areas
	 *
	 * @return una lista de areas
	 */
	public List getAreas() {
		return areaDbEntity.getAreas();
	}

	/**
	 * Inserta un nuevo area.
	 *
	 * @param AreaVO
	 * @return AreaVO
	 */
	public AreaVO createArea(AreaVO areaVO) {
		iniciarTransaccion();
		areaVO = areaDbEntity.createArea(areaVO);
		commit();
		return areaVO;
	}

	/**
	 * Elimina el conjunto de areas correspondiente a los id's pasados por
	 * parï¿½metro
	 *
	 * @param idsAreas
	 *            Conjunto de identificadores de area
	 */
	public void deleteAreas(String[] idsAreas) {
		iniciarTransaccion();
		areaDbEntity.deleteAreas(idsAreas);
		commit();
	}

	/**
	 * Actualiza el area pasada por parametro.
	 *
	 * @param areaVO
	 * @return AreaVO
	 */
	public AreaVO updateArea(AreaVO areaVO) {
		iniciarTransaccion();

		// Modificar la norma en los campos dato pertenecientes al area
		List list = getCamposDatoXArea(new String[] { areaVO.getId() });
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
				campoDatoVO.setTipoNorma(areaVO.getTipoNorma());
				campoDatoVO = campoDatoDbEntity.updateCampoDato(campoDatoVO);
			}
		}

		// Modificar la norma en los campo tabla pertenecientes al area

		list = getCamposTablaXArea(new String[] { areaVO.getId() });
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CampoTablaVO campoTablaVO = (CampoTablaVO) list.get(i);
				campoTablaVO.setTipoNorma(areaVO.getTipoNorma());
				campoTablaVO = campoTablaDbEntity
						.updateCampoTabla(campoTablaVO);
			}
		}

		areaVO = areaDbEntity.updateArea(areaVO);
		commit();
		return areaVO;
	}

	/**
	 * Obtiene el texto de un tipo para un id de tipo
	 *
	 * @param tipo
	 * @return String
	 */
	public String getTipoText(int tipo) {
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

	/**
	 * Obtiene el texto de un tipo de norma para un id de tipo de norma
	 *
	 * @param tipo
	 * @return String
	 */
	public String getTipoNormaText(int tipoNorma) {
		if (tipoNorma == TipoNorma.SIN_TIPO)
			return null;
		if (tipoNorma == TipoNorma.NORMA_ISAAR)
			return TipoNorma.ISAAR;
		if (tipoNorma == TipoNorma.NORMA_ISADG)
			return TipoNorma.ISADG;
		return null;
	}

	/**
	 * Obtiene la lista de CamposDatoVO que no pertenecen a ninguna tabla
	 *
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoSinTabla() {
		List list = campoDatoDbEntity.getCamposDatoSinTabla();
		for (int i = 0; i < list.size(); i++) {
			CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
			campoDatoVO.setTipoText(getTipoText(campoDatoVO.getTipo()));
			campoDatoVO.setTipoNormaText(getTipoNormaText(campoDatoVO
					.getTipoNorma()));
		}
		return list;
	}

	/**
	 * Obtiene la lista de CamposDatoVO que pertenecen a un Area con sus datos
	 *
	 * @param idArea
	 *            Cadena que contiene el identificador del área
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoByArea(String idArea, String nombreArea) {
		List list = campoDatoDbEntity.getCamposDatoXArea(
				new String[] { idArea }, false);
		transformListaCampos(list, idArea, nombreArea, false);
		return list;
	}

	public List getCamposTablaByArea(String idArea, String nombreArea) {
		List list = campoTablaDbEntity
				.getCamposTablaXArea(new String[] { idArea });
		transformListaCampos(list, idArea, nombreArea, true);
		return list;
	}

	private void transformListaCampos(List list, String idArea,
			String nombreArea, boolean isCampoTabla) {
		Map<String, CampoTablaVO> camposTabla = new HashMap<String, CampoTablaVO>();

		for (int i = 0; i < list.size(); i++) {
			ICampoVO campoDatoVO = (ICampoVO) list.get(i);

			if (!isCampoTabla) {
				campoDatoVO.setTipoText(getTipoText(campoDatoVO.getTipo()));
			} else {
				campoDatoVO.setTipoText(TipoCampo.TABLA_LABEL);
			}
			campoDatoVO.setTipoNormaText(getTipoNormaText(campoDatoVO
					.getTipoNorma()));
			campoDatoVO.setNombreArea(nombreArea);
			campoDatoVO.setIdArea(idArea);

			String idTabla = campoDatoVO.getIdTblPadre();
			if (StringUtils.isNotEmpty(idTabla)) {
				CampoTablaVO campoTabla = camposTabla.get(idTabla);

				if (campoTabla == null) {
					campoTabla = getCampoTabla(idTabla);
					camposTabla.put(idTabla, campoTabla);
				}

				if (campoTabla != null) {
					campoDatoVO
							.setEtiquetaXmlTabla(campoTabla.getEtiquetaXml());
					campoDatoVO.setEtiqXmlFila(campoTabla.getEtiqXmlFila());
					campoDatoVO.setNombreTabla(campoTabla.getNombre());
				}
			}

		}

	}

	/**
	 * Obtiene la lista de CamposDatoVO
	 *
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDato() {
		return campoDatoDbEntity.getCamposDato();

	}

	/**
	 * Obtiene la lista de CamposDatoVO
	 *
	 * @param String
	 *            idTabla
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoOrderByPosEnTbl(String idTabla) {
		List list = campoDatoDbEntity.getCamposDatoOrderByPosEnTbl(idTabla);
		for (int i = 0; i < list.size(); i++) {
			CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
			campoDatoVO.setTipoText(getTipoText(campoDatoVO.getTipo()));
		}

		return list;
	}

	/**
	 * Obtiene un CamposDatoVO
	 *
	 * @param String
	 *            idTabla
	 * @param int posicion
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByPosEnTbl(String idTabla, int pos) {
		return campoDatoDbEntity.getCampoDatoByPosEnTbl(idTabla, pos);
	}

	/**
	 * Obtiene una lista de CampoDatoVO perteneciente al id de la tabla que se
	 * pasa por parï¿½metro
	 *
	 * @param idTabla
	 * @return List
	 */
	public List getCamposDatoXIdTabla(String idTabla) {
		List list = campoDatoDbEntity.getCamposDatoXIdTabla(idTabla);
		for (int i = 0; i < list.size(); i++) {
			CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
			campoDatoVO.setTipoText(getTipoText(campoDatoVO.getTipo()));
		}

		return list;
	}

	/**
	 * Obtiene una lista de CampoTablaVO pertenecientes a los id de areas
	 * pasados por parï¿½metro
	 *
	 * @param idAreas
	 * @return List
	 */
	public List getCamposTablaXArea(String[] idAreas) {
		return campoTablaDbEntity.getCamposTablaXArea(idAreas);
	}

	/**
	 * Obtiene una lista de CampoDatoVO pertenecientes a los id de areas pasados
	 * por parï¿½metro
	 *
	 * @param idAreas
	 * @return List
	 */
	public List getCamposDatoXArea(String[] idAreas) {
		return campoDatoDbEntity.getCamposDatoXArea(idAreas, false);
	}

	/**
	 * Obtiene la lista de CamposTablaVO
	 *
	 * @return una lista de CamposTablaVO
	 */
	public List getCamposTabla() {
		List list = campoTablaDbEntity.getCamposTabla();
		for (int i = 0; i < list.size(); i++) {
			CampoTablaVO campoTablaVO = (CampoTablaVO) list.get(i);
			campoTablaVO.setTipoNormaText(getTipoNormaText(campoTablaVO
					.getTipoNorma()));
		}
		return list;
	}

	/**
	 * Inserta un nuevo CampoDatoVO.
	 *
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO createCampoDato(CampoDatoVO campoDatoVO) {

		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		if (campoDatoVO.getIdTblPadre() != null
				&& !campoDatoVO.getIdTblPadre().trim().equalsIgnoreCase("")) {
			List list = getCamposDatoXIdTabla(campoDatoVO.getIdTblPadre());
			if (list != null)
				campoDatoVO.setPosEnTbl(list.size() + 1);
		}

		campoDatoVO = campoDatoDbEntity.createCampoDato(campoDatoVO);

		AuditoriaDescripcion.auditaCreacionCampo(locale, this, campoDatoVO);

		// Insertamos el campo en la tabla ADUSO

		if (campoDatoVO.getIdArea() != null) {
			UsoObjetoVO usoOjetoVO = new UsoObjetoVO();
			usoOjetoVO.setIdObj(campoDatoVO.getIdArea());
			usoOjetoVO.setIdObjUsuario(campoDatoVO.getId());
			usoOjetoVO.setTipoObj(TipoObjetoUsado.AREA);
			usoOjetoVO.setTipoObjUsuario(TipoObjetoUsuario.CAMPO_DATO);

			usoObjetoDbEntity.create(usoOjetoVO);
		}

		commit();
		return campoDatoVO;
	}

	/**
	 * Inserta un nuevo CampoTablaVO.
	 *
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO createCampoTabla(CampoTablaVO campoTablaVO) {

		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		campoTablaVO = campoTablaDbEntity.createCampoTabla(campoTablaVO);

		AuditoriaDescripcion.auditaCreacionCampo(locale, this, campoTablaVO);

		// Insertamos el campo en la tabla ADUSO

		if (campoTablaVO.getIdArea() != null) {
			UsoObjetoVO usoOjetoVO = new UsoObjetoVO();
			usoOjetoVO.setIdObj(campoTablaVO.getIdArea());
			usoOjetoVO.setIdObjUsuario(campoTablaVO.getId());
			usoOjetoVO.setTipoObj(TipoObjetoUsado.AREA);
			usoOjetoVO.setTipoObjUsuario(TipoObjetoUsuario.CAMPO_TABLA);

			usoObjetoDbEntity.create(usoOjetoVO);
		}
		commit();
		return campoTablaVO;
	}

	/**
	 * Elimina el conjunto de campos indicado por parï¿½metro
	 *
	 * @param idsCamposDato
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposDato(String[] idsCamposDato) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		AuditoriaDescripcion.auditaEliminacionCampos(locale, this,
				idsCamposDato, false);
		campoDatoDbEntity.deleteCamposDato(idsCamposDato);

		// Se elimina el campo de la tabla ADUSO

		usoObjetoDbEntity.deleteByIdObjUsuario(idsCamposDato);

		commit();
	}

	/**
	 * Elimina el conjunto de campos indicado por parï¿½metro
	 *
	 * @param idsCamposTabla
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposTabla(String[] idsCamposTabla) {

		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		// Se eliminan todos sus campos simples de la tabla aduso
		AuditoriaDescripcion.auditaEliminacionCampos(locale, this,
				idsCamposTabla, true);
		for (int i = 0; i < idsCamposTabla.length; i++) {
			List list = campoDatoDbEntity
					.getCamposDatoXIdTabla(idsCamposTabla[i]);
			if (!ListUtils.isEmpty(list)) {
				Vector vector = new Vector();
				for (int j = 0; j < list.size(); j++) {
					CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(j);
					vector.add(campoDatoVO.getId());
				}
				usoObjetoDbEntity.deleteByIdObjUsuario(StringUtils
						.toString(vector.toArray()));
			}
		}

		// Se elimina el campo de la tabla ADUSO
		usoObjetoDbEntity.deleteByIdObjUsuario(idsCamposTabla);
		campoTablaDbEntity.deleteCamposTabla(idsCamposTabla);
		commit();
	}

	/**
	 * Actualiza el campo pasado por parametro.
	 *
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO updateCampoDato(CampoDatoVO campoDatoVO) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		campoDatoVO = campoDatoDbEntity.updateCampoDato(campoDatoVO);

		AuditoriaDescripcion.auditaModificacionCampo(locale, this, campoDatoVO);

		// actualizamos en la tabla aduso
		UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
		usoObjetoVO.setIdObj(campoDatoVO.getIdArea());
		usoObjetoVO.setIdObjUsuario(campoDatoVO.getId());
		usoObjetoVO.setTipoObj(TipoObjetoUsado.AREA);
		usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.CAMPO_DATO);

		UsoObjetoVO antiguoUsoObjetoVO = (UsoObjetoVO) usoObjetoDbEntity
				.getXIdObjUsuarioYTipoObj(usoObjetoVO.getIdObjUsuario(),
						usoObjetoVO.getTipoObj());
		if (antiguoUsoObjetoVO == null) {
			if (campoDatoVO.getIdArea() != null)
				usoObjetoDbEntity.create(usoObjetoVO);
		} else {
			if (campoDatoVO.getIdArea() != null)
				usoObjetoDbEntity.update(usoObjetoVO);
			else
				usoObjetoDbEntity.deleteByIdObjUsuario(campoDatoVO.getId());
		}

		commit();
		return campoDatoVO;
	}

	/**
	 * Actualiza el campo pasado por parametro.
	 *
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO updateCampoTabla(CampoTablaVO campoTablaVO) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		campoTablaVO = campoTablaDbEntity.updateCampoTabla(campoTablaVO);

		AuditoriaDescripcion
				.auditaModificacionCampo(locale, this, campoTablaVO);

		// actualizamos en la tabla aduso
		UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
		usoObjetoVO.setIdObj(campoTablaVO.getIdArea());
		usoObjetoVO.setIdObjUsuario(campoTablaVO.getId());
		usoObjetoVO.setTipoObj(TipoObjetoUsado.AREA);
		usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.CAMPO_TABLA);

		UsoObjetoVO antiguoUsoObjetoVO = (UsoObjetoVO) usoObjetoDbEntity
				.getXIdObjUsuarioYTipoObj(usoObjetoVO.getIdObj(),
						usoObjetoVO.getTipoObj());
		if (antiguoUsoObjetoVO == null) {
			if (campoTablaVO.getIdArea() != null)
				usoObjetoDbEntity.create(usoObjetoVO);
		} else if (campoTablaVO.getIdArea() != null)
			usoObjetoDbEntity.update(usoObjetoVO);
		else
			usoObjetoDbEntity.deleteByIdObjUsuario(campoTablaVO.getId());

		// Metemos sus campos simples en la tabla aduso

		List listaCamposDato = campoDatoDbEntity
				.getCamposDatoXIdTabla(campoTablaVO.getId());

		if (!ListUtils.isEmpty(listaCamposDato)) {
			if (campoTablaVO.getIdArea() != null) {
				for (int i = 0; i < listaCamposDato.size(); i++) {
					CampoDatoVO campoDatoVO = (CampoDatoVO) listaCamposDato
							.get(i);
					usoObjetoVO = new UsoObjetoVO();
					usoObjetoVO.setIdObj(campoDatoVO.getIdArea());
					usoObjetoVO.setIdObjUsuario(campoDatoVO.getId());
					usoObjetoVO.setTipoObj(TipoObjetoUsado.AREA);
					usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.CAMPO_DATO);

					antiguoUsoObjetoVO = usoObjetoDbEntity
							.getXIdObjUsuarioYTipoObj(usoObjetoVO.getIdObj(),
									usoObjetoVO.getTipoObj());
					if (antiguoUsoObjetoVO == null) {
						usoObjetoDbEntity.create(usoObjetoVO);
					} else {
						usoObjetoDbEntity.update(usoObjetoVO);
					}
				}
			} else {
				Vector vector = new Vector();
				for (int i = 0; i < listaCamposDato.size(); i++) {
					CampoDatoVO campoDatoVO = (CampoDatoVO) listaCamposDato
							.get(i);
					vector.add(campoDatoVO.getId());
				}
				usoObjetoDbEntity.deleteByIdObjUsuario(StringUtils
						.toString(vector.toArray()));
			}
		}
		commit();
		return campoTablaVO;
	}

	/**
	 * Elimina el campo de tabla perteneciente al id pasado por parï¿½metro
	 *
	 * @param String
	 *            id
	 */
	public void deleteCampoTabla(String id) {
		Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();
		AuditoriaDescripcion.auditaEliminacionCampo(locale, this, id, true);
		campoTablaDbEntity.deleteCampoTabla(id);
		commit();
	}

	/**
	 * Obtiene una lista de elementos en uso pertenecientes al id del objeto
	 * pasado por parï¿½metro
	 *
	 * @param String
	 *            idObj
	 * @return List lista de elementos en uso
	 */
	public List getElementosEnUsoXIdObj(String idObj) {
		return usoObjetoDbEntity.getXIdObj(idObj);
	}

	/**
	 * Obtiene una lista de elementos en uso pertenecientes al id del objeto
	 * pasado por parámetro y son del tipo especificado
	 *
	 * @param String
	 *            idObj
	 * @param String
	 *            Tipo de Objeto {@link TipoObjetoUsado}
	 * @return List lista de elementos en uso
	 */
	public List getElementosEnUsoXIdObjYTipo(String[] idsObjeto,
			int idTipoObjeto) {
		return usoObjetoDbEntity.getXIdObjYTipo(idsObjeto, idTipoObjeto);
	}

	/**
	 * Obtiene una lista de elementos en uso pertenecientes a los id's del
	 * objeto pasado por parï¿½metro
	 *
	 * @param String
	 *            [] idObjs
	 * @return List lista de elementos en uso
	 */
	public List getElementosEnUsoXIdsObj(String[] idObjs) {
		return usoObjetoDbEntity.getXIdsObj(idObjs);
	}

	/**
	 * Construye una lista con los tipos de normas
	 *
	 * @return lista de tipos de normas;
	 */
	public List makeListTipoNorma() {
		List listaTiposNorma = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoNorma.SIN_TIPO));
		propertyBean.setLabel(TipoNorma.NINGUNO);
		listaTiposNorma.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoNorma.NORMA_ISADG));
		propertyBean.setLabel(TipoNorma.ISADG);
		listaTiposNorma.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoNorma.NORMA_ISAAR));
		propertyBean.setLabel(TipoNorma.ISAAR);
		listaTiposNorma.add(propertyBean);

		return listaTiposNorma;
	}

	/**
	 * Construye una lista con los tipos de niveles para ninguna norma
	 *
	 * @return lista de tipos de niveles;
	 */
	public List makeListTipoNivelesNinguno() {
		List listaTiposNivelesNinguno = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoNiveles.TODOS_VALUE));
		propertyBean.setLabel(TipoNiveles.TODOS_LABEL);
		listaTiposNivelesNinguno.add(propertyBean);

		return listaTiposNivelesNinguno;
	}

	/**
	 * Construye una lista con los tipos de niveles para la norma isaar
	 *
	 * @return lista de tipos de niveles;
	 */
	public List makeListTipoNivelesIsaar() {
		List listaTiposNivelesIsaar = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoNiveles.AUTORIDAD_VALUE));
		propertyBean.setLabel(TipoNiveles.AUTORIDAD_LABEL);
		listaTiposNivelesIsaar.add(propertyBean);

		return listaTiposNivelesIsaar;
	}

	/**
	 * Construye una lista con los tipos de niveles para la norma isad
	 *
	 * @return lista de tipos de niveles;
	 */
	public List<PropertyBean> makeListTipoNivelesIsad() {
		List<PropertyBean> listaTiposNivelesIsad = new ArrayList<PropertyBean>();

		listaTiposNivelesIsad
				.add(getPropertyBean(TipoNiveles.CLASIFICADOR_DE_FONDOS_VALUE));
		listaTiposNivelesIsad.add(getPropertyBean(TipoNiveles.FONDOS_VALUE));
		listaTiposNivelesIsad
				.add(getPropertyBean(TipoNiveles.CLASIFICADOR_DE_SERIES_VALUE));
		listaTiposNivelesIsad.add(getPropertyBean(TipoNiveles.SERIE_VALUE));
		listaTiposNivelesIsad
				.add(getPropertyBean(TipoNiveles.CLASIFICADOR_UNIDAD_DOCUMENTAL_VALUE));
		listaTiposNivelesIsad
				.add(getPropertyBean(TipoNiveles.UNIDAD_DOCUMENTAL_VALUE));

		return listaTiposNivelesIsad;
	}

	public PropertyBean getPropertyBean(int tipoNivel) {
		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(tipoNivel));
		propertyBean.setLabel(Messages.getString(
				deposito.global.Constants.LABEL_PREFIJO_NIVEL_DOCUMENTAL
						+ tipoNivel, getLocale()));
		return propertyBean;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#makeListSubTipoNivelesIsad(java.lang.String)
	 */
	public List<PropertyBean> makeListSubTipoNivelesIsad(String tipoNivel) {
		List<PropertyBean> listaTiposSubNivelesIsad = new ArrayList<PropertyBean>();

		if (tipoNivel != null) {
			String tipoNivelUdoc = "" + TipoNiveles.UNIDAD_DOCUMENTAL_VALUE;

			if (tipoNivelUdoc.equals(tipoNivel)) {
				PropertyBean propertyBean = new PropertyBean();
				propertyBean.setValue(String.valueOf(SubtipoNivelCF.CAJA
						.getIdentificador()));
				propertyBean.setLabel(Messages.getString(
						deposito.global.Constants.LABEL_FRACCION_SERIE,
						getLocale()));
				listaTiposSubNivelesIsad.add(propertyBean);

				propertyBean = new PropertyBean();
				propertyBean.setValue(String.valueOf(SubtipoNivelCF.UDOCSIMPLE
						.getIdentificador()));
				propertyBean
						.setLabel(Messages
								.getString(
										deposito.global.Constants.LABEL_UNIDAD_DOCUMENTAL_SERIE,
										getLocale()));
				listaTiposSubNivelesIsad.add(propertyBean);
			}
		}

		return listaTiposSubNivelesIsad;

	}

	/**
	 * Construye una lista con los tipos campo
	 *
	 * @return lista con los tipos campo
	 */
	public List makeListTipoCampo() {
		List listaTipoCampo = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampo.TEXTO_CORTO_VALUE));
		propertyBean.setLabel(TipoCampo.TEXTO_CORTO_LABEL);
		listaTipoCampo.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampo.TEXTO_LARGO_VALUE));
		propertyBean.setLabel(TipoCampo.TEXTO_LARGO_LABEL);
		listaTipoCampo.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampo.FECHA_VALUE));
		propertyBean.setLabel(TipoCampo.FECHA_LABEL);
		listaTipoCampo.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampo.NUMERICO_VALUE));
		propertyBean.setLabel(TipoCampo.NUMERICO_LABEL);
		listaTipoCampo.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampo.REFERENCIA_VALUE));
		propertyBean.setLabel(TipoCampo.REFERENCIA_LABEL);
		listaTipoCampo.add(propertyBean);

		return listaTipoCampo;
	}

	/**
	 * Construye una lista con los tipos campo de entidad
	 *
	 * @return lista con los tipos campo
	 */
	public List makeListTipoCampoEntidad() {
		List listaTipoCampoEntidad = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampoEntidad.DATO_VALUE));
		propertyBean.setLabel(TipoCampoEntidad.DATO_LABEL);
		listaTipoCampoEntidad.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setValue(String.valueOf(TipoCampoEntidad.TABLA_VALUE));
		propertyBean.setLabel(TipoCampoEntidad.TABLA_LABEL);
		listaTipoCampoEntidad.add(propertyBean);

		return listaTipoCampoEntidad;

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getIdsElementos(fondos.vos.BusquedaElementosVO,
	 *      int, xml.config.Busqueda)
	 */
	public List getIdsElementos(BusquedaElementosVO vo, int numMaxResults,
			Busqueda busqueda) throws TooManyResultsException {

		// String[] idsRepsEcm = new String[0];
		Locale locale = getServiceClient().getLocale();

		List idsElementos = new ArrayList();

		// Comprobar permisos
		checkPermission(DescripcionSecurityManager.CONSULTAR_FICHA_ELEMENTO_ACTION);

		/*
		 * return elementoCuadroClasificacionDBEntity
		 * .getElementosCuadroClasificacion(getServiceClient(),
		 * busquedaGeneralVO);
		 */

		AuditoriaDescripcion.auditaBusquedaArchivista(locale, this, vo);
		vo.setServiceClient(getServiceClient());

		// Identificadores de los elementos del cuadro de clasificaciï¿½n que
		// cumplen las condiciones indicadas excepto
		// la bï¿½squeda en contenido de fichero
		idsElementos = elementoCuadroClasificacionDBEntity.getIdsElementos(vo,
				numMaxResults, busqueda);

		// Identificadores de los elementos del cuadro de clasificaciï¿½n que
		// cumplen con las condiciones anteriores y ademï¿½s
		// con las de contenido de fichero

		if (busqueda != null
				&& busqueda.getMapEntrada() != null
				&& busqueda
						.getMapEntrada()
						.containsKey(
								CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)
				&& idsElementos != null && idsElementos.size() > 0) {
			if (vo.getCamposRellenos().contains(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)
					&& StringUtils.isNotEmpty(vo.getContenidoFichero())) {
				idsElementos = getGestionDocumentosElectronicosBI()
						.buscarFicherosXContenido(vo.getContenidoFichero(),
								idsElementos);
			}
		}

		// return elementoCuadroClasificacionDBEntity.getIdsElementos(vo,
		// numMaxResults, busqueda);

		return idsElementos;
	}

	/**
	 * Obtiene en una lista de CampoValorVO el valor, formato y calificador de
	 * una fecha cuyo idCampo correspondiente se indica como parï¿½metro
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampo
	 *            Identificador del campo de fecha correspondiente
	 * @return Lista de rangos.
	 */
	public List getFechaElemento(String idElementocf, String idCampo) {
		return campoFechaDBEntity.getValues(idElementocf, idCampo,
				ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getFechaMaximaUDocs(java.lang.String,
	 *      java.lang.String)
	 */
	public Date getFechaMaximaUDocs(String idSerie, String idCampo) {
		return campoFechaDBEntity.getFechaMaximaUDocs(idSerie, idCampo);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getFechaMinimaUDocs(java.lang.String,
	 *      java.lang.String)
	 */
	public Date getFechaMinimaUDocs(String idSerie, String idCampo) {
		return campoFechaDBEntity.getFechaMinimaUDocs(idSerie, idCampo);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#createFmtFicha(descripcion.vos.FmtFichaVO)
	 */
	public FmtFichaVO createFmtFicha(FmtFichaVO fmtFichaVO)
			throws ActionNotAllowedException {
		return fmtFichaDBEntity.createFmtFicha(fmtFichaVO);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#deleteFormatos(java.lang.String[])
	 */
	public void deleteFormatos(String[] idsFormatos) {
		fmtFichaDBEntity.deleteFmtFichasByIds(idsFormatos);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#updateFmtFicha(descripcion.vos.FmtFichaVO)
	 */
	public FmtFichaVO updateFmtFicha(FmtFichaVO fmtFichaVO)
			throws ActionNotAllowedException {
		return fmtFichaDBEntity.updateFmtFicha(fmtFichaVO);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#findFmtFichas(java.lang.String,
	 *      java.lang.String)
	 */
	public List findFmtFichas(String nombre, String idFicha) {
		return fmtFichaDBEntity.findFmtFichas(nombre, idFicha);

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#copiarCamposUDocREaUDocCF(java.lang.String,
	 *      java.lang.String, int)
	 */
	public void copiarCamposUDocREaUDocCF(String idUDocRE, String idUDocCF,
			int tipoUDocOrigen) {

		List listaFechas = campoFechaUDocREDBEntity.getValues(idUDocRE,
				tipoUDocOrigen);
		List listaNumeros = campoNumeroUDocREDBEntity.getValues(idUDocRE,
				tipoUDocOrigen);
		List listaTextosCortos = campoTextoCortoUDocREDBEntity.getValues(
				idUDocRE, tipoUDocOrigen);
		List listaTextosLargos = campoTextoLargoUDocREDBEntity.getValues(
				idUDocRE, tipoUDocOrigen);
		List listaReferencias = campoReferenciaUDocREDBEntity.getValues(
				idUDocRE, tipoUDocOrigen);

		if (listaFechas != null && listaFechas.size() > 0) {
			Iterator itFechas = listaFechas.iterator();
			while (itFechas.hasNext()) {
				CampoFechaVO campoFecha = (CampoFechaVO) itFechas.next();
				campoFecha.setIdObjeto(idUDocCF);
				campoFechaDBEntity.insertValue(campoFecha);
			}
		}

		if (listaNumeros != null && listaNumeros.size() > 0) {
			Iterator itNumeros = listaNumeros.iterator();
			while (itNumeros.hasNext()) {
				CampoNumericoVO campoNumerico = (CampoNumericoVO) itNumeros
						.next();
				campoNumerico.setIdObjeto(idUDocCF);
				campoNumeroDBEntity.insertValue(campoNumerico);
			}
		}

		if (listaTextosCortos != null && listaTextosCortos.size() > 0) {
			Iterator itTextosCortos = listaTextosCortos.iterator();
			while (itTextosCortos.hasNext()) {
				CampoTextoVO campoTextoCorto = (CampoTextoVO) itTextosCortos
						.next();
				campoTextoCorto.setIdObjeto(idUDocCF);
				campoTextoCortoDBEntity.insertValue(campoTextoCorto);
			}
		}

		if (listaTextosLargos != null && listaTextosLargos.size() > 0) {
			Iterator itTextosLargos = listaTextosLargos.iterator();
			while (itTextosLargos.hasNext()) {
				CampoTextoVO campoTextoLargo = (CampoTextoVO) itTextosLargos
						.next();
				campoTextoLargo.setIdObjeto(idUDocCF);
				campoTextoLargoDBEntity.insertValue(campoTextoLargo);
			}
		}

		if (listaReferencias != null && listaReferencias.size() > 0) {
			Iterator itReferencias = listaReferencias.iterator();
			while (itReferencias.hasNext()) {
				CampoReferenciaVO campoReferencia = (CampoReferenciaVO) itReferencias
						.next();
				campoReferencia.setIdObjeto(idUDocCF);
				campoReferenciaDBEntity.insertValue(campoReferencia);
			}
		}
	}

	/**
	 * Permite eliminar de las tablas de descripciï¿½n de unidad documental en
	 * relaciï¿½n de entrega los valores de los campos de descripciï¿½n
	 *
	 * @param idUDocRE
	 *            Identificador de la unidad documental en la relaciï¿½n de
	 *            entrega
	 */
	public void eliminarValoresCamposUDocRE(String idUDocRE, int tipoElemento) {

		// Eliminar los valores de las tablas de descripciï¿½n de la unidad en
		// la relaciï¿½n
		campoTextoCortoUDocREDBEntity.deleteValueXIdElemento(idUDocRE,
				tipoElemento);
		campoTextoLargoUDocREDBEntity.deleteValueXIdElemento(idUDocRE,
				tipoElemento);
		campoNumeroUDocREDBEntity
				.deleteValueXIdElemento(idUDocRE, tipoElemento);
		campoFechaUDocREDBEntity.deleteValueXIdElemento(idUDocRE, tipoElemento);
		campoReferenciaUDocREDBEntity.deleteValueXIdElemento(idUDocRE,
				tipoElemento);
	}

	public void copiarCamposUDocREaUDocRE(String idUDocREOrigen,
			String idUDocREDestino, int tipoElemento) {

		copiarCamposUDocREaUDocRE(idUDocREOrigen, idUDocREDestino,
				tipoElemento, null);

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#copiarCamposUDocREaUDocRE(java.lang.String,
	 *      java.lang.String, int, java.util.Map)
	 */
	public void copiarCamposUDocREaUDocRE(String idUDocREOrigen,
			String idUDocREDestino, int tipoElemento, Map camposAIgnorar) {

		ArrayList camposAIgnorarTipoFecha = null, camposAIgnorarTipoTC = null, camposAIgnorarTipoTL = null, camposAIgnorarTipoRef = null;

		if (camposAIgnorar != null && camposAIgnorar.size() > 0) {
			Iterator it = camposAIgnorar.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				Integer key = new Integer((String) entry.getKey());
				switch (key.intValue()) {
				case TipoCampo.TEXTO_CORTO_VALUE: {
					if (camposAIgnorarTipoTC == null)
						camposAIgnorarTipoTC = new ArrayList();
					List values = (ArrayList) entry.getValue();
					camposAIgnorarTipoTC.addAll(values);
					break;
				}
				case TipoCampo.TEXTO_LARGO_VALUE: {
					if (camposAIgnorarTipoTL == null)
						camposAIgnorarTipoTL = new ArrayList();
					List values = (ArrayList) entry.getValue();
					camposAIgnorarTipoTL.addAll(values);
					break;
				}
				case TipoCampo.FECHA_VALUE: {
					if (camposAIgnorarTipoFecha == null)
						camposAIgnorarTipoFecha = new ArrayList();
					List values = (ArrayList) entry.getValue();
					camposAIgnorarTipoFecha.addAll(values);
					break;
				}
				case TipoCampo.REFERENCIA_VALUE: {
					if (camposAIgnorarTipoRef == null)
						camposAIgnorarTipoRef = new ArrayList();
					List values = (ArrayList) entry.getValue();
					camposAIgnorarTipoRef.addAll(values);
					break;
				}
				}

			}
		}

		List listaFechas = campoFechaUDocREDBEntity.getValues(idUDocREOrigen,
				tipoElemento, camposAIgnorarTipoFecha);
		List listaNumeros = campoNumeroUDocREDBEntity.getValues(idUDocREOrigen,
				tipoElemento);
		List listaTextosCortos = campoTextoCortoUDocREDBEntity.getValues(
				idUDocREOrigen, tipoElemento, camposAIgnorarTipoTC);
		List listaTextosLargos = campoTextoLargoUDocREDBEntity.getValues(
				idUDocREOrigen, tipoElemento, camposAIgnorarTipoTL);
		List listaReferencias = campoReferenciaUDocREDBEntity.getValues(
				idUDocREOrigen, tipoElemento, camposAIgnorarTipoRef);

		if (listaFechas != null && listaFechas.size() > 0) {
			Iterator itFechas = listaFechas.iterator();
			while (itFechas.hasNext()) {
				CampoFechaVO campoFecha = (CampoFechaVO) itFechas.next();
				campoFecha.setIdObjeto(idUDocREDestino);
				campoFechaUDocREDBEntity.insertValue(campoFecha);
			}
		}

		if (listaNumeros != null && listaNumeros.size() > 0) {
			Iterator itNumeros = listaNumeros.iterator();
			while (itNumeros.hasNext()) {
				CampoNumericoVO campoNumerico = (CampoNumericoVO) itNumeros
						.next();
				campoNumerico.setIdObjeto(idUDocREDestino);
				campoNumeroUDocREDBEntity.insertValue(campoNumerico);
			}
		}

		if (listaTextosCortos != null && listaTextosCortos.size() > 0) {
			Iterator itTextosCortos = listaTextosCortos.iterator();
			while (itTextosCortos.hasNext()) {
				CampoTextoVO campoTextoCorto = (CampoTextoVO) itTextosCortos
						.next();
				campoTextoCorto.setIdObjeto(idUDocREDestino);
				campoTextoCortoUDocREDBEntity.insertValue(campoTextoCorto);
			}
		}

		if (listaTextosLargos != null && listaTextosLargos.size() > 0) {
			Iterator itTextosLargos = listaTextosLargos.iterator();
			while (itTextosLargos.hasNext()) {
				CampoTextoVO campoTextoLargo = (CampoTextoVO) itTextosLargos
						.next();
				campoTextoLargo.setIdObjeto(idUDocREDestino);
				campoTextoLargoUDocREDBEntity.insertValue(campoTextoLargo);
			}
		}

		if (listaReferencias != null && listaReferencias.size() > 0) {
			Iterator itReferencias = listaReferencias.iterator();
			while (itReferencias.hasNext()) {
				CampoReferenciaVO campoReferencia = (CampoReferenciaVO) itReferencias
						.next();
				campoReferencia.setIdObjeto(idUDocREDestino);
				campoReferenciaUDocREDBEntity.insertValue(campoReferencia);
			}
		}

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getInteresadoPrincipal(java.lang.String,
	 *      int)
	 */
	public transferencias.vos.InteresadoVO getInteresadoPrincipal(
			String idUDoc, int tipoUDoc) {

		transferencias.vos.InteresadoVO interesado = null;

		ConfiguracionDescripcion configDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		String idCampoInteresadoIdentidad = configDesc.getInteresadoIdentidad();
		String idCampoInteresadoPrincipal = configDesc.getInteresadoPrincipal();
		String idCampoIdTerceroInteresado = configDesc.getIdTerceroInteresado();
		String idCampoInteresadoNumeroIdentidad = configDesc
				.getInteresadoNumeroIdentidad();
		String idCampoInteresadoRol = configDesc.getInteresadoRol();
		String idCampoInteresadoValidado = configDesc.getInteresadoValidado();
		boolean encontrado = false;

		List listaInteresados = campoReferenciaUDocREDBEntity.getValues(idUDoc,
				idCampoInteresadoIdentidad, tipoUDoc);

		if (!ListUtils.isEmpty(listaInteresados)) {
			Iterator it = listaInteresados.iterator();
			while (it.hasNext() && !encontrado) {
				CampoReferenciaVO campoRefInteresado = (CampoReferenciaVO) it
						.next();
				CampoTextoVO campoTextoPrincipal = campoTextoCortoUDocREDBEntity
						.getValue(idUDoc, idCampoInteresadoPrincipal,
								campoRefInteresado.getOrden(), tipoUDoc);

				if (campoTextoPrincipal != null
						&& StringUtils.isNotEmpty(campoTextoPrincipal
								.getValor())) {
					if (campoTextoPrincipal.getValor().equalsIgnoreCase(
							Constants.TRUE_FULL_STRING)) {
						interesado = new transferencias.vos.InteresadoVO();

						CampoTextoVO campoTextoTerceroInteresado = campoTextoCortoUDocREDBEntity
								.getValue(idUDoc, idCampoIdTerceroInteresado,
										campoRefInteresado.getOrden(), tipoUDoc);
						if (campoTextoTerceroInteresado != null)
							interesado
									.setIdEnTerceros(campoTextoTerceroInteresado
											.getValor());

						DescriptorVO descriptor = this
								.getDescriptor(campoRefInteresado.getIdObjRef());
						if (descriptor != null)
							interesado.setNombre(descriptor.getNombre());

						CampoTextoVO campoTextoNumeroIdentificacion = campoTextoCortoUDocREDBEntity
								.getValue(idUDoc,
										idCampoInteresadoNumeroIdentidad,
										campoRefInteresado.getOrden(), tipoUDoc);
						if (campoTextoNumeroIdentificacion != null)
							interesado
									.setNumeroIdentificacion(campoTextoNumeroIdentificacion
											.getValor());

						interesado.setPrincipal(Constants.TRUE_STRING);

						CampoTextoVO campoTextoRol = campoTextoCortoUDocREDBEntity
								.getValue(idUDoc, idCampoInteresadoRol,
										campoRefInteresado.getOrden(), tipoUDoc);
						if (campoTextoRol != null)
							interesado.setRol(campoTextoRol.getValor());

						CampoTextoVO campoTextoValidado = campoTextoCortoUDocREDBEntity
								.getValue(idUDoc, idCampoInteresadoValidado,
										campoRefInteresado.getOrden(), tipoUDoc);
						if (campoTextoValidado != null) {
							if (campoTextoValidado.getValor().equalsIgnoreCase(
									Constants.TRUE_FULL_STRING))
								interesado.setValidado(Constants.TRUE_STRING);
							else
								interesado.setValidado(Constants.FALSE_STRING);
						}

						encontrado = true;
					}
				}
			}
		}

		return interesado;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getInteresadosByIdsElementoCF(java.lang.String[])
	 */
	public List getInteresadosByIdsElementoCF(String[] idsElementos) {
		return campoReferenciaDBEntity
				.getInteresadosByIdsElementosCF(idsElementos);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getInteresadoPrincipal(java.lang.String[],
	 *      int)
	 */
	public Map getInteresadoPrincipal(String[] idsUDoc, int tipoUDoc) {

		List listaInteresados = campoTextoCortoUDocREDBEntity
				.getInteresadosPrincipales(idsUDoc, tipoUDoc);
		Map mapInteresados = new HashMap();

		if (listaInteresados != null) {
			Iterator itInteresados = listaInteresados.iterator();
			while (itInteresados.hasNext()) {
				InteresadoUdocVO interesado = (InteresadoUdocVO) itInteresados
						.next();
				if (mapInteresados.containsKey(interesado.getId())) {
					List ltInteresadosUdoc = (List) mapInteresados
							.get(interesado.getId());
					ltInteresadosUdoc.add(interesado);
				} else {
					List ltInteresados = new ArrayList();
					ltInteresados.add(interesado);
					mapInteresados.put(interesado.getId(), ltInteresados);
				}
			}
		}

		return mapInteresados;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#unificarDescriptores(descripcion.vos.DescriptorVO,
	 *      java.lang.String[], java.lang.String[])
	 */
	public void unificarDescriptores(DescriptorVO descriptorVO,
			String[] idsAReemplazar, String[] nombresAReemplazar)
			throws Exception {

		iniciarTransaccion();

		try {
			// DescriptorVO descriptorVO =
			// descriptorDBEntity.getDescriptor(descriptor.getId());

			// Eliminaciï¿½n de registros de tablas
			// ADVCTEXTDESCR.IDDESCR
			campoTextoCortoDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADVCTEXTLDESCR.IDDESCR
			campoTextoLargoDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADVCNUMDESCR.IDDESCR
			campoNumeroDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADVCFECHADESCR.IDDESCR
			campoFechaDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADVCREFDESCR.IDDESCR
			campoReferenciaDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADOCCLASIFDESCR.IDDESCR
			docClasifDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// ADOCDOCUMENTODESCR.IDDESCR
			docDocumentoDescrDBEntity.deleteDescriptores(idsAReemplazar);

			// Modificaciones en campos de tablas

			// ASGTRENTREGA.IDDESCRORGPRODUCTOR
			relacionEntregaDBEntity.unificarDescriptor(descriptorVO.getId(),
					idsAReemplazar);

			// ADVCREFCF.IDOBJREF
			campoReferenciaDBEntity.unificarDescriptor(descriptorVO.getId(),
					idsAReemplazar);

			// ADVCREFUDOCRE.IDOBJREF
			campoReferenciaUDocREDBEntity.unificarDescriptor(
					descriptorVO.getId(), idsAReemplazar);

			// ADVCREFDESCR.IDOBJREF
			campoReferenciaDescrDBEntity.unificarDescriptor(
					descriptorVO.getId(), idsAReemplazar);

			// ASGFENTPRODUCTORA.IDDESCR
			entidadProductoraDBEntity.unificarDescriptor(descriptorVO.getId(),
					idsAReemplazar);

			// ASGFORGPROD.IDDESCR
			organoProductorDbEntity.unificarDescriptor(descriptorVO.getId(),
					idsAReemplazar);

			// ASGFFONDO.IDENTPRODUCTORA
			fondoDbEntity.unificarDescriptor(descriptorVO.getId(),
					idsAReemplazar);

			// Modificaciones en XML

			// ASGTUNIDADDOCRE.INFO
			unificarDescriptorUnidadDocRe(descriptorVO, idsAReemplazar);

			// ASGFDIVISIONFS.INFO
			unificarDescriptorDivisionFS(descriptorVO, idsAReemplazar);

			// ASGFUDOCENDIVISIONFS.INFO
			unificarDescriptorUDocEnDivisionFS(descriptorVO, idsAReemplazar);

			// ELIMINAR LOS DESCRIPTORES A REEMPLAZAR
			descriptorDBEntity.deleteDescriptores(idsAReemplazar);

			commit();

			Locale locale = getServiceClient().getLocale();

			AuditoriaDescripcion.auditaUnificacionDescriptores(locale, this,
					descriptorVO.getNombre(), nombresAReemplazar);

		} catch (Exception ex) {
			logger.error("Error al Unificar los Descriptores", ex);
			rollback();
			throw ex;
		}
	}

	/**
	 * Unifica los descriptores en ASGTUNIDADDOCRE.INFO
	 *
	 * @param descriptor
	 *            Datos del Descriptor
	 * @param idsDescriptores
	 *            Ids de los descriptores a reemplazar.
	 */
	private void unificarDescriptorUnidadDocRe(DescriptorVO descriptor,
			String[] idsDescriptores) {
		// Obtener los Ids de Unidades Documentales que se deben reemplazar
		List listaUdocs = unidadDocumentalREDBEntity
				.getUdocsByInfoDescriptor(idsDescriptores);

		if (!ListUtils.isEmpty(listaUdocs)) {
			Iterator it = listaUdocs.listIterator();

			while (it.hasNext()) {
				transferencias.vos.UnidadDocumentalVO unidadDocumentalVO = (transferencias.vos.UnidadDocumentalVO) it
						.next();

				ValoresFicha valoresFicha = ValoresFicha.getInstance(
						getServiceSession(), unidadDocumentalVO.getId(),
						TipoFicha.FICHA_UDOCRE, unidadDocumentalVO);
				Map mapValoresDescripcion = valoresFicha.getListaValores();

				boolean conCambios = false;

				if (unidadDocumentalVO != null
						&& unidadDocumentalVO.getExtraInfo() != null) {
					// Comprobar si los productores de la unidad documental
					// tienen productores a modificar.
					if (unidadDocumentalVO.getExtraInfo().getProductor() != null
							&& unidadDocumentalVO.getExtraInfo().getProductor()
									.getId() != null) {

						String id = unidadDocumentalVO.getExtraInfo()
								.getProductor().getId();
						if (ArrayUtils.contains(idsDescriptores, id)) {
							conCambios = true;
							unidadDocumentalVO.getExtraInfo().getProductor()
									.setId(descriptor.getId());
							unidadDocumentalVO.getExtraInfo().getProductor()
									.setNombre(descriptor.getNombre());
						}
					}

					conCambios = modificarValores(conCambios,
							mapValoresDescripcion, descriptor, idsDescriptores);

					if (conCambios) {
						// Actualizar el campo info con el xml con los valores
						// actualizados
						unidadDocumentalREDBEntity.updateXmlInfo(
								unidadDocumentalVO, unidadDocumentalVO
										.asXMLWithValores(valoresFicha, null));
					}
				}
			}
		}
	}

	/**
	 * Unifica los descriptores en ASGFDIVISIONFS.INFO
	 *
	 * @param descriptor
	 *            Datos del Descriptor
	 * @param idsDescriptores
	 *            Ids de los descriptores a reemplazar.
	 */
	private void unificarDescriptorDivisionFS(DescriptorVO descriptor,
			String[] idsDescriptores) {
		List listaDivisiones = divisionFSDbEntity
				.getUdocsByInfoDescriptor(idsDescriptores);

		if (!ListUtils.isEmpty(listaDivisiones)) {
			Iterator it = listaDivisiones.listIterator();

			while (it.hasNext()) {
				DivisionFraccionSerieVO divisionFSVO = (DivisionFraccionSerieVO) it
						.next();

				// ValoresFicha valoresFicha =
				// ValoresFicha.getInstance(getServiceSession(),
				// divisionFSVO.getIdFS(), TipoFicha.FICHA_UDOCFS,
				// divisionFSVO);
				// Map mapValoresDescripcion = valoresFicha.getListaValores();

				boolean conCambios = false;

				if (divisionFSVO != null && divisionFSVO.getProductor() != null
						&& divisionFSVO.getProductor().getId() != null) {

					String id = divisionFSVO.getProductor().getId();
					if (ArrayUtils.contains(idsDescriptores, id)) {
						conCambios = true;
						divisionFSVO.getProductor().setId(descriptor.getId());
						divisionFSVO.getProductor().setNombre(
								descriptor.getNombre());
					}
				}

				if (conCambios) {
					divisionFSDbEntity.updateDivisionFS(divisionFSVO);
				}
			}
		}
	}

	/**
	 * Unifica los descriptores en ASGFUDOCENDIVISIONFS.INFO
	 *
	 * @param descriptor
	 *            Datos del Descriptor
	 * @param idsDescriptores
	 *            Ids de los descriptores a reemplazar.
	 */
	private void unificarDescriptorUDocEnDivisionFS(DescriptorVO descriptor,
			String[] idsDescriptores) {
		List listaUdocs = unidadDocumentalFSDBEntity
				.getUdocsByInfoDescriptor(idsDescriptores);

		if (!ListUtils.isEmpty(listaUdocs)) {
			Iterator it = listaUdocs.listIterator();

			while (it.hasNext()) {
				UDocEnFraccionSerieVO uDoc = (UDocEnFraccionSerieVO) it.next();

				ValoresFicha valoresFicha = ValoresFicha.getInstance(
						getServiceSession(), uDoc.getIdUDoc(),
						TipoFicha.FICHA_UDOCFS, uDoc);
				Map mapValoresDescripcion = valoresFicha.getListaValores();

				boolean conCambios = false;

				if (uDoc != null && uDoc.getXinfo() != null) {
					// Comprobar si los productores de la unidad documental
					// tienen productores a modificar.
					if (uDoc.getXinfo().getProductor() != null
							&& uDoc.getXinfo().getProductor().getId() != null) {

						String id = uDoc.getXinfo().getProductor().getId();
						if (ArrayUtils.contains(idsDescriptores, id)) {
							conCambios = true;
							uDoc.getXinfo().getProductor()
									.setId(descriptor.getId());
							uDoc.getXinfo().getProductor()
									.setNombre(descriptor.getNombre());
						}
					}

					conCambios = modificarValores(conCambios,
							mapValoresDescripcion, descriptor, idsDescriptores);

					if (conCambios) {
						// Actualizar el campo info con el xml con los valores
						// actualizados
						String xmlInfo = uDoc.asXMLWithValores(valoresFicha);

						unidadDocumentalFSDBEntity.updateXmlInfo(
								uDoc.getIdUDoc(), xmlInfo);
					}
				}
			}
		}

	}

	private boolean modificarValores(boolean conCambios,
			Map mapValoresDescripcion, DescriptorVO descriptor,
			String[] idsDescriptores) {
		if (mapValoresDescripcion != null) {
			Iterator itValores = mapValoresDescripcion.keySet().iterator();
			while (itValores.hasNext()) {
				String valorCampo = (String) itValores.next();

				List listaCampos = (List) mapValoresDescripcion.get(valorCampo);

				if (!ListUtils.isEmpty(listaCampos)) {
					Iterator itValoresCampo = listaCampos.iterator();

					while (itValoresCampo.hasNext()) {
						Object obj = itValoresCampo.next();

						if (obj instanceof CampoReferenciaVO) {
							CampoReferenciaVO campoReferencia = (CampoReferenciaVO) obj;

							if (campoReferencia.getTipoObjRef() == CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR) {
								String id = campoReferencia.getIdObjRef();
								if (ArrayUtils.contains(idsDescriptores, id)) {
									conCambios = true;
									campoReferencia.setIdObjRef(descriptor
											.getId());
									campoReferencia.setNombre(descriptor
											.getNombre());
								}
							}
						}
					}
				}
			}
		}

		return conCambios;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#isConDescripcion(java.lang.String)
	 */
	public boolean isConDescripcion(String idDescriptor) {
		boolean retorno = false;

		// Comprobar si hay textos cortos
		List listaCamposTextoCorto = campoTextoCortoDescrDBEntity
				.getValues(idDescriptor);
		if (!ListUtils.isEmpty(listaCamposTextoCorto))
			return true;

		// Comprobar si hay textos largos
		List listaCamposTextosLargos = campoTextoLargoDescrDBEntity
				.getValues(idDescriptor);
		if (!ListUtils.isEmpty(listaCamposTextosLargos))
			return true;

		// Comprobar si hay nï¿½meros
		List listaCamposNumericos = campoNumeroDescrDBEntity
				.getValues(idDescriptor);
		if (!ListUtils.isEmpty(listaCamposNumericos))
			return true;

		// Comprobar si hay Fechas
		List listaCamposFecha = campoFechaDescrDBEntity.getValues(idDescriptor);
		if (!ListUtils.isEmpty(listaCamposFecha))
			return true;

		// Comprobar si hay referencias
		List listaCamposReferencias = campoReferenciaDescrDBEntity
				.getValues(idDescriptor);
		if (!ListUtils.isEmpty(listaCamposReferencias))
			return true;

		return retorno;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getValoresFichaConDatos(int,
	 *      java.lang.String)
	 */
	public HashMap getValoresFichaConDatos(int tipoFicha, String id) {

		HashMap mapValoresConDatos = new HashMap();
		ArrayList idsTC = null, idsTL = null, idsFecha = null, idsNum = null, idsRef = null;

		List valoresTC = getValues(tipoFicha, TipoCampo.TEXTO_CORTO_VALUE, id);
		List valoresTL = getValues(tipoFicha, TipoCampo.TEXTO_LARGO_VALUE, id);
		List valoresFecha = getValues(tipoFicha, TipoCampo.FECHA_VALUE, id);
		List valoresNum = getValues(tipoFicha, TipoCampo.NUMERICO_VALUE, id);
		List valoresRef = getValues(tipoFicha, TipoCampo.REFERENCIA_VALUE, id);

		if (valoresTC != null && valoresTC.size() > 0) {
			Iterator it = valoresTC.iterator();
			while (it.hasNext()) {
				CampoTextoVO campoTC = (CampoTextoVO) it.next();
				if (idsTC == null)
					idsTC = new ArrayList();
				idsTC.add(campoTC.getIdCampo());
			}
			mapValoresConDatos.put(
					new Integer(TipoCampo.TEXTO_CORTO_VALUE).toString(), idsTC);
		}

		if (valoresTL != null && valoresTL.size() > 0) {
			Iterator it = valoresTL.iterator();
			while (it.hasNext()) {
				CampoTextoVO campoTL = (CampoTextoVO) it.next();
				if (idsTL == null)
					idsTL = new ArrayList();
				idsTL.add(campoTL.getIdCampo());
			}
			mapValoresConDatos.put(
					new Integer(TipoCampo.TEXTO_LARGO_VALUE).toString(), idsTL);
		}

		if (valoresFecha != null && valoresFecha.size() > 0) {
			Iterator it = valoresFecha.iterator();
			while (it.hasNext()) {
				CampoFechaVO campoFecha = (CampoFechaVO) it.next();
				if (idsFecha == null)
					idsFecha = new ArrayList();
				idsFecha.add(campoFecha.getIdCampo());
			}
			mapValoresConDatos.put(
					new Integer(TipoCampo.FECHA_VALUE).toString(), idsFecha);
		}

		if (valoresNum != null && valoresNum.size() > 0) {
			Iterator it = valoresNum.iterator();
			while (it.hasNext()) {
				CampoNumericoVO campoNumerico = (CampoNumericoVO) it.next();
				if (idsNum == null)
					idsNum = new ArrayList();
				idsNum.add(campoNumerico.getIdCampo());
			}
			mapValoresConDatos.put(
					new Integer(TipoCampo.NUMERICO_VALUE).toString(), idsNum);
		}

		if (valoresRef != null && valoresRef.size() > 0) {
			Iterator it = valoresRef.iterator();
			while (it.hasNext()) {
				CampoReferenciaVO campoReferencia = (CampoReferenciaVO) it
						.next();
				if (idsRef == null)
					idsRef = new ArrayList();
				idsRef.add(campoReferencia.getIdCampo());
			}
			mapValoresConDatos.put(
					new Integer(TipoCampo.REFERENCIA_VALUE).toString(), idsRef);
		}

		return mapValoresConDatos;

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getFechaFinal(int, java.lang.String)
	 */
	public Date getFechaFinal(int tipoFicha, String idElemento) {
		List listaFechas = getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_FECHA, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaFinal());

		if (!ListUtils.isEmpty(listaFechas)) {
			CampoFechaVO campoFecha = (CampoFechaVO) listaFechas.get(0);
			if (campoFecha != null
					&& !DateQualifierHelper.CALIFICADOR_SIN_FECHA
							.equals(campoFecha.getCalificador())) {
				CustomDate customDate = new CustomDate(campoFecha.getValor(),
						campoFecha.getFormato(), campoFecha.getSep(),
						campoFecha.getCalificador());
				return customDate.getDate();
			}
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getAsunto(java.lang.String)
	 */
	public String getAsunto(String idElementoCF) {
		ElementoCuadroClasificacionVO elemento = getElementoCuadroClasificacionVO(idElementoCF);

		if (elemento != null) {
			return elemento.getTitulo();
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getFechaInicial(int,
	 *      java.lang.String)
	 */
	public Date getFechaInicial(int tipoFicha, String idElemento) {
		List listaFechas = getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_FECHA, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaInicial());

		if (!ListUtils.isEmpty(listaFechas)) {
			CampoFechaVO campoFecha = (CampoFechaVO) listaFechas.get(0);
			if (campoFecha != null
					&& !DateQualifierHelper.CALIFICADOR_SIN_FECHA
							.equals(campoFecha.getCalificador())) {
				CustomDate customDate = new CustomDate(campoFecha.getValor(),
						campoFecha.getFormato(), campoFecha.getSep(),
						campoFecha.getCalificador());
				return customDate.getDate();
			}
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getProductor(int, java.lang.String)
	 */
	public CampoReferenciaVO getProductor(int tipoFicha, String idElemento) {
		List listaProductores = getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_REFERENCIA, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getProductor());

		if (!ListUtils.isEmpty(listaProductores)) {
			CampoReferenciaVO productor = (CampoReferenciaVO) listaProductores
					.get(0);
			return productor;
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getRangosFS(int, java.lang.String)
	 */
	public List getRangosFS(int tipoFicha, String idElemento) {
		List listaRangosIniciales = getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_TEXTO_CORTO, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getRangoInicial());

		List listaRangosFinales = getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_TEXTO_CORTO, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getRangoFinal());

		LinkedHashMap mapRangos = new LinkedHashMap();

		if (!ListUtils.isEmpty(listaRangosIniciales)) {
			for (int i = 0; i < listaRangosIniciales.size(); i++) {
				CampoTextoVO rangoInicial = (CampoTextoVO) listaRangosIniciales
						.get(i);

				if (rangoInicial != null) {

					// Integer numOrden = new Integer(rangoInicial.getOrden());

					String orden = new Integer(rangoInicial.getOrden())
							.toString();
					RangoVO rango = new RangoVO();

					rango.setDesde(rangoInicial.getValor());

					mapRangos.put(orden, rango);
				}
			}
		}

		if (!ListUtils.isEmpty(listaRangosFinales)) {
			for (int i = 0; i < listaRangosFinales.size(); i++) {
				CampoTextoVO rangoFinal = (CampoTextoVO) listaRangosFinales
						.get(i);

				if (rangoFinal != null) {

					// Integer numOrden = new Integer(rangoFinal.getOrden());

					String orden = new Integer(rangoFinal.getOrden())
							.toString();
					RangoVO rango = (RangoVO) mapRangos.get(orden);

					if (rango == null) {
						rango = new RangoVO();
					}
					rango.setHasta(rangoFinal.getValor());

					mapRangos.put(orden, rango);
				}
			}
		}

		List listaRangos = MapUtil.toList(mapRangos);

		return listaRangos;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getTituloDocumento(java.lang.String)
	 */
	public String getTituloDocumento(String idElemento) {
		UnidadDocumentalVO udoc = getGestionUnidadDocumentalBI()
				.getUnidadDocumental(idElemento);

		if (udoc != null) {
			return udoc.getNumExp();
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCondicionesAcceso(java.lang.String)
	 */
	public String getCondicionesAcceso(String idElemento) {
		List listaCondiciones = getValues(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_TEXTO_LARGO, idElemento,
				ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getCondicionesAcceso());

		if (!ListUtils.isEmpty(listaCondiciones)) {
			CampoTextoVO campo = (CampoTextoVO) listaCondiciones.get(0);
			if (campo != null) {
				return campo.getValor();
			}
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#isFichaEnUso(java.lang.String[])
	 */
	public boolean isFichaEnUso(String[] idsFichas) {
		if (fichaDBEntity.countElementosUsoFicha(idsFichas) > 0)
			return true;
		return false;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getListasControlAccesoProductoresSerie(java.lang.String)
	 */
	public List getListasControlAccesoProductoresSerie(String idSerie) {
		return listaControlAccesoDBEntity
				.getListasControlAccesoProductoresSerie(idSerie);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getDescriptoresProductoresRelacion(descripcion.vos.BusquedaDescriptoresVO,
	 *      java.lang.Integer[])
	 */
	public List getDescriptoresProductoresRelacion(
			BusquedaDescriptoresVO busquedaVO, Integer[] tiposRelacion)
			throws TooManyResultsException {
		return descriptorDBEntity.getDescriptoresProductoresRelacion(
				busquedaVO, tiposRelacion);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#validarDescriptores(java.lang.String[])
	 */
	public void validarDescriptores(String[] idsDescriptores) {
		descriptorDBEntity.updateEstado(EstadoDescriptor.VALIDADO,
				idsDescriptores);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getListasDescriptorasByTipos(java.lang.String[])
	 */
	public List getListasDescriptorasAbiertas() {
		int[] tipos = new int[] { TipoListaDescriptora.LISTA_ABIERTA };
		return catalogoListaDescriptoresDBEntity
				.getListasDescriptorasByTipos(tipos);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#addDescriptor(java.lang.String,
	 *      java.lang.String)
	 */
	public DescriptorVO addDescriptor(String idLista, String nombre)
			throws CheckedArchivoException {
		DescriptorVO descriptorVO = getGestionDescripcionBI()
				.getDescriptorByNombreYIdLista(nombre, idLista);

		// Comprobar que la lista estïa abierta
		ListaDescrVO listaDescriptora = getGestionDescripcionBI()
				.getListaDescriptora(idLista);


		if (descriptorVO == null) {
			DescriptorVO descriptor = new DescriptorVO(idLista, nombre);
			descriptor.setEstado(EstadoDescriptor.VALIDADO);

			try {
				checkPermission(DescripcionSecurityManager.ADMINISTRAR_DESCRIPTORES_ACTION);

			} catch (Exception e) {
				if (listaDescriptora == null || listaDescriptora.isCerrada()) {
					throw new ListaDescriptoraCerradaException();
				}

				descriptor.setEstado(EstadoDescriptor.NO_VALIDADO);

			}

			Locale locale = getServiceClient().getLocale();


			// Inicializar valores
			descriptor.setTieneDescr(Constants.FALSE_STRING);
			descriptor.setTimestamp(new Date());


			if(listaDescriptora != null){
				if(StringUtils.isNotEmpty(listaDescriptora.getIdFichaDescrPref())){
					descriptor.setIdFichaDescr(listaDescriptora.getIdFichaDescrPref());
					descriptor.setTieneDescr(Constants.TRUE_STRING);
				}

				descriptor.setTipo(listaDescriptora.getTipoDescriptor());

				descriptor.setIdRepEcm(listaDescriptora.getIdRepEcmPref());
			}

			// Auditoria
			LoggingEvent event = AuditoriaDescripcion.getLogginEvent(this,
					ArchivoActions.DESCRIPCION_MODULE_ALTA_DESCRIPTOR);

			// Crear el descriptor
			descriptor = descriptorDBEntity.insertDescriptorVO(descriptor);

			// Detalle de la auditoria
			AuditoriaDescripcion.auditaInsercionDescriptor(locale, event,
					descriptor);

			return descriptor;

		} else {
			throw new DescriptorDuplicadoException("El descriptor " + nombre
					+ " ya existe en la lista " + idLista);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#existeRegistroByKey(java.lang.String,
	 *      int)
	 */
	public boolean existeRegistroByKey(String key, int codigoTabla) {

		IDBEntityKeyValue dbEntity = getDbEntity(codigoTabla);

		if (dbEntity != null) {
			return dbEntity.existeByKey(key);
		} else {
			String nombreTabla = ArchivoTables.getTableName(codigoTabla);
			throw new ArchigestException(
					"No existe método getByKey para tabla " + nombreTabla);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#existeRegistroByValue(java.lang.String,
	 *      java.lang.String, int)
	 */
	public boolean existeRegistroByValue(String key, String value,
			int codigoTabla) {

		IDBEntityKeyValue dbEntity = getDbEntity(codigoTabla);

		if (dbEntity != null) {
			return dbEntity.existeByValue(key, value);
		} else {
			String nombreTabla = ArchivoTables.getTableName(codigoTabla);
			throw new ArchigestException(
					"No existe método getByValue para tabla " + nombreTabla);
		}
	}

	/**
	 *
	 * @param codigoTabla
	 * @return
	 */
	private IDBEntityKeyValue getDbEntity(int codigoTabla) {
		if (ArchivoTables.ADAREA_TABLE == codigoTabla) {
			return (IDBEntityKeyValue) areaDbEntity;
		} else if (ArchivoTables.ADCAMPODATO_TABLE == codigoTabla) {
			return (IDBEntityKeyValue) campoDatoDbEntity;
		} else if (ArchivoTables.ADCAMPOTBL_TABLE == codigoTabla) {
			return (ICampoTablaDBEntity) campoTablaDbEntity;
		} else if (ArchivoTables.ADFICHA_TABLE == codigoTabla) {
			return (IFichaDBEntity) fichaDBEntity;
		}

		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCampoTablaByEtiqueta(java.lang.String)
	 */
	public CampoTablaVO getCampoTablaByEtiqueta(String etiquetaXml) {
		return campoTablaDbEntity.getCampoTablaByEtiquetaXml(etiquetaXml);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCampoTablaByEtiquetaFila(java.lang.String)
	 */
	public CampoTablaVO getCampoTablaByEtiquetaFila(String etiquetaXml) {
		return campoTablaDbEntity.getCampoTablaByEtiquetaFilaXml(etiquetaXml);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCampoDatoByEtiqueta(java.lang.String)
	 */
	public CampoDatoVO getCampoDatoByEtiqueta(String etiquetaXml) {
		return campoDatoDbEntity.getCampoDatoByEtiquetaXml(etiquetaXml);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCampoDatoByEtiqueta(java.lang.String)
	 */
	public CampoDatoVO getCampoDatoByEtiqueta(String etiquetaXml, String idTabla) {
		return campoDatoDbEntity
				.getCampoDatoByEtiquetaXml(etiquetaXml, idTabla);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getCamposByArea(java.lang.String,
	 *      java.lang.String)
	 */
	public List getCamposByArea(String idArea, String nombreArea) {
		List list = new ArrayList();

		List listaCamposDato = getCamposDatoByArea(idArea, nombreArea);

		if (ListUtils.isNotEmpty(listaCamposDato)) {
			list.addAll(listaCamposDato);
		}

		List listaCampoTabla = getCamposTablaByArea(idArea, nombreArea);

		if (ListUtils.isNotEmpty(listaCampoTabla)) {
			list.addAll(listaCampoTabla);
		}

		return list;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#getMapeoDescripcion(java.lang.String)
	 */
	public MapDescUDocVO getMapeoDescripcion(String idFicha) {
		return mapDescUDocDBEntity.getMapDescUDoc(idFicha);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionDescripcionBI#updateMapeoDescripcion(transferencias.vos.MapDescUDocVO)
	 */
	public void updateMapeoDescripcion(MapDescUDocVO mapDescUDocVO) {
		mapDescUDocDBEntity.update(mapDescUDocVO);
	}
}