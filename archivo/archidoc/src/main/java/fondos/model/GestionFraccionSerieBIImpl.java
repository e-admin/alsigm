package fondos.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import transferencias.CodigoUdocUtil;
import transferencias.db.IMapDescUDocDBEntity;
import transferencias.db.INSecUDocDBEntity;
import transferencias.db.IUdocEnUIDBEntity;
import transferencias.model.validacion.ComprobacionDatosDescripcion;
import transferencias.model.validacion.ObtencionValor;
import transferencias.vos.MapDescUDocVO;
import transferencias.vos.RangoVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.CampoDescriptivoConfigMapFSUDoc;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionMapeoFSUDoc;
import xml.config.ConfiguracionMapeoFSUDocFactory;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.security.FondosSecurityManager;
import common.util.CustomDate;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.MarcaUtil;

import deposito.MarcaUInstalacionConstants;
import deposito.db.IFormatoDbEntity;
import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.IFechaDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.model.EstadoDescriptor;
import descripcion.model.ValoresFicha;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import fondos.actions.DivisionFSToPOTransformer;
import fondos.actions.DivisionFraccionSeriePO;
import fondos.actions.UnidadDocumentalPO;
import fondos.db.IDivisionFSDbEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IUDocEnDivisionFSDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.DivisionFraccionSerieForm;
import fondos.model.AuditFondos.PistaAuditoriaFraccionSerie;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.SerieVO;
import fondos.vos.UDocEnFraccionSerieVO;
import fondos.vos.UDocFondo;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.model.NivelAcceso;
import gcontrol.model.TipoAcceso;

public class GestionFraccionSerieBIImpl extends ServiceBase implements
		GestionFraccionSerieBI {
	IFormatoDbEntity _formatoDBEntity = null;
	IUnidadDocumentalDbEntity _unidadDocumentalDBEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUIDepositoDBEntity = null;
	IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity = null;
	IUInstalacionDepositoDBEntity _unidadInstalacionDepositoDBEntity = null;
	IDescriptorDBEntity _descriptorDBEntity = null;
	IReferenciaDBEntity _referenciaDBEntity, _referenciaUDocDBEntity = null;
	IFechaDBEntity _fechaDBEntity, _fechaUDocDBEntity = null;
	ITextoDBEntity _textoCortoDBEntity, _textoCortoUDocDBEntity = null;
	ITextoDBEntity _textoLargoDBEntity, _textoLargoUDocDBEntity = null;
	INumeroDBEntity _numeroDBEntity, _numeroUDocDBEntity = null;
	IOrganoProductorDbEntity _organoProductorDBEntity = null;
	INSecUDocDBEntity _nSecUDocBEntity = null;
	IUdocEnUIDBEntity _udocEnUIDBEntity = null;
	IMapDescUDocDBEntity _mapDescUDocDbEntity = null;
	IDivisionFSDbEntity _divisionFSDbEntity = null;
	IUDocEnDivisionFSDbEntity _udocEnDivisionFSDbEntity = null;

	static final int VALUE_FROM_TAG = 1;
	static final int VALUE_LITERAL = 2;
	static final int VALUE_DINAMIC = 3;

	// Map de pares key=idFicha / valor=MapDescUDocVO
	private static Map mapDescUDocMap = null;

	/** Logger de la clase */
	static Logger logger = Logger.getLogger(GestionFraccionSerieBIImpl.class);

	private void loadMapDescUDoc() {
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

	private String loadMapDescUDocFicha(String idFicha) {
		String ret = null;

		Object obj = mapDescUDocMap.get(idFicha);
		if (obj != null) {
			MapDescUDocVO mapDescUDocVO = (MapDescUDocVO) obj;
			ret = mapDescUDocVO.getInfo();
		} else {
		} // TODO Lanzar excepción de que no hay definición para esa ficha

		return ret;
	}

	class MapDescripcionUdoc {
		SAXReader saxReader = null;

		Document mapUdocFields = null;

		GestionDescripcionBI descripcionBI = null;

		MapDescripcionUdoc(GestionDescripcionBI descripcionBI, String idFicha) {
			this.descripcionBI = descripcionBI;
			saxReader = new SAXReader();

			/*
			 * ConfiguracionTransferencias config =
			 * ConfiguracionSistemaArchivoFactory
			 * .getConfiguracionSistemaArchivo
			 * ().getConfiguracionTransferencias();
			 */

			try {

				// Chequeamos la carga en memoria de los xml de mapeo de udocs
				// en transferencias a su descripción
				loadMapDescUDoc();

				// Obtenemos el String xml del fichero de mapeo para la ficha
				// concreta que vamos a utilizar
				String mapDescUDocXML = loadMapDescUDocFicha(idFicha);

				// mapUdocFields =
				// saxReader.read(getClass().getResourceAsStream(mapDescUDocXML));

				mapUdocFields = DocumentHelper.parseText(mapDescUDocXML);

				/*
				 * mapUdocFields =
				 * saxReader.read(getClass().getResourceAsStream(
				 * config.getUdocFieldsMappingFile()));
				 */
			} catch (DocumentException e) {
				throw new ArchivoModelException(e, "MapDescripcionUdoc",
						"Error leyendo archivo de mapeo");
			}
		}

		void generateDescripcionUdoc(String xmlInfo, String udocID,
				DefFicha defFicha, DefFmtFicha defFmtFicha, String idFicha,
				int tipoFicha, int tipoElemento) {
			Ficha fichaDescripcionUdoc = descripcionBI.createFichaNueva(udocID,
					tipoFicha, defFicha, defFmtFicha, idFicha);
			// TipoFicha.FICHA_ELEMENTO_CF, defFicha, defFmtFicha, idFicha);

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

	public GestionFraccionSerieBIImpl(
			IUnidadDocumentalDbEntity unidadDocumentalDBEntity,
			IUDocEnUiDepositoDbEntity udocEnUIDepositoDBEntity,
			IUInstalacionDepositoDBEntity unidadInstalacionDepositoDBEntity,
			IFormatoDbEntity formatoDBEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity,
			IDescriptorDBEntity descriptorDBEntity,
			IReferenciaDBEntity referenciaDBEntity,
			IFechaDBEntity fechaDBEntity, ITextoDBEntity textoCortoDBEntity,
			ITextoDBEntity textoLargoDBEntity, INumeroDBEntity numeroDBEntity,
			IOrganoProductorDbEntity organoProductorDBEntity,
			IMapDescUDocDBEntity mapDescUDocDBEntity,
			INSecUDocDBEntity nSecUdocDBEntity,
			IDivisionFSDbEntity divisionFSDbEntity,
			IUDocEnDivisionFSDbEntity udocEnDivisionFSDbEntity,
			IReferenciaDBEntity referenciaUDocDBEntity,
			IFechaDBEntity fechaUDocDBEntity,
			ITextoDBEntity textoCortoUDocDBEntity,
			ITextoDBEntity textoLartoUDocDBEntity,
			INumeroDBEntity numeroUDocDBEntity) {
		this._formatoDBEntity = formatoDBEntity;
		this._unidadDocumentalDBEntity = unidadDocumentalDBEntity;
		this._udocEnUIDepositoDBEntity = udocEnUIDepositoDBEntity;
		this._elementoCuadroClasificacionDBEntity = elementoCuadroClasificacionDBEntity;
		this._unidadInstalacionDepositoDBEntity = unidadInstalacionDepositoDBEntity;
		this._descriptorDBEntity = descriptorDBEntity;
		this._referenciaDBEntity = referenciaDBEntity;
		this._fechaDBEntity = fechaDBEntity;
		this._textoCortoDBEntity = textoCortoDBEntity;
		this._textoLargoDBEntity = textoLargoDBEntity;
		this._numeroDBEntity = numeroDBEntity;
		this._organoProductorDBEntity = organoProductorDBEntity;
		this._mapDescUDocDbEntity = mapDescUDocDBEntity;
		this._nSecUDocBEntity = nSecUdocDBEntity;
		this._divisionFSDbEntity = divisionFSDbEntity;
		this._udocEnDivisionFSDbEntity = udocEnDivisionFSDbEntity;
		this._referenciaUDocDBEntity = referenciaUDocDBEntity;
		this._fechaUDocDBEntity = fechaUDocDBEntity;
		this._textoCortoUDocDBEntity = textoCortoUDocDBEntity;
		this._textoLargoUDocDBEntity = textoLartoUDocDBEntity;
		this._numeroUDocDBEntity = numeroUDocDBEntity;
	}

	public HashMap getMapCamposFSUDoc(String idFS) {

		HashMap mapCamposFSUDoc = new HashMap();

		ConfiguracionMapeoFSUDoc configMapeo = ConfiguracionMapeoFSUDocFactory
				.getConfiguracionMapeoFSUDoc();

		if (configMapeo != null && configMapeo.getCamposDescriptivos() != null
				&& configMapeo.getCamposDescriptivos().entrySet() != null) {
			Iterator itCampos = configMapeo.getCamposDescriptivos().entrySet()
					.iterator();

			while (itCampos.hasNext()) {
				Entry entry = (Entry) itCampos.next();
				CampoDescriptivoConfigMapFSUDoc cdMap = (CampoDescriptivoConfigMapFSUDoc) entry
						.getValue();
				Integer tipoI = new Integer(cdMap.getTipo());
				List listaValores = new ArrayList();
				switch (tipoI.intValue()) {
				case ValorCampoGenericoVO.TIPO_REFERENCIA:
					listaValores = _referenciaDBEntity.getValues(idFS,
							cdMap.getValorOrigen(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
					break;
				case ValorCampoGenericoVO.TIPO_NUMERICO:
					listaValores = _numeroDBEntity.getValues(idFS,
							cdMap.getValorOrigen(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
					break;
				case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
					listaValores = _textoCortoDBEntity.getValues(idFS,
							cdMap.getValorOrigen(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
					break;
				case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
					listaValores = _textoLargoDBEntity.getValues(idFS,
							cdMap.getValorOrigen(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
					break;
				case ValorCampoGenericoVO.TIPO_FECHA:
					listaValores = _fechaDBEntity.getValues(idFS,
							cdMap.getValorOrigen(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
				}

				if (listaValores != null && listaValores.size() > 0) {
					Map mapCampoTipo = (Map) mapCamposFSUDoc.get(cdMap
							.getTipo());

					if (mapCampoTipo == null)
						mapCampoTipo = new HashMap();

					mapCampoTipo.put(cdMap.getValorDestino(), listaValores);
					mapCamposFSUDoc.put(cdMap.getTipo(), mapCampoTipo);
				}
			}
		}

		return mapCamposFSUDoc;
	}

	public void createUDocsFraccionSerie(DivisionFraccionSerieVO divisionFS,
			UnidadDocumental udoc, List udocsEnFraccionSerie) {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI managerCuadroClf = services
				.lookupGestionCuadroClasificacionBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		GestionFondosBI gestionFondos = services.lookupGestionFondosBI();

		String fichaDescripcionUdoc = null;
		INivelCFVO nivelUnidadDocumental = null;

		PistaAuditoriaFraccionSerie pistaAuditoria = AuditFondos
				.crearPistaAuditoria(
						ArchivoActions.FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE,
						this);
		// pistaAuditoria.addDetalleBasico(udoc);
		Locale locale = getServiceClient().getLocale();
		pistaAuditoria.addDetalleBasico(locale, divisionFS);

		SerieVO serieUdocs = managerSeries.getSerie(udoc.getIdSerie());

		// Obtener el identificador de la fracción de serie en relación de
		// entrega original antes
		// de que se modifique ningún dato del depósito
		List fsEnUIDeposito = depositoBI.getUDocsById(new String[] { udoc
				.getId() });
		String idFSEnREntrega = ((UDocEnUiDepositoVO) fsEnUIDeposito.get(0))
				.getIdudocre();

		// El nivel y la ficha nos viene en la división de fracción de serie
		nivelUnidadDocumental = managerCuadroClf.getNivelCF(divisionFS
				.getIdNivelDocumental());
		String fichaDescripcionPrefUDoc = serieUdocs
				.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());

		// Si la serie tiene definidos listas de volúmenes y ficha preferente
		// para las u.docs., usar estas, sino, las del nivel
		if (StringUtils.isNotEmpty(fichaDescripcionPrefUDoc))
			fichaDescripcionUdoc = fichaDescripcionPrefUDoc;
		else
			fichaDescripcionUdoc = nivelUnidadDocumental.getIdFichaDescrPref();

		MapDescripcionUdoc descripcionUdoc = new MapDescripcionUdoc(
				descripcionBI, fichaDescripcionUdoc);

		ValoracionSerieVO valoracion = valoracionBI
				.getValoracionDictaminada(udoc.getIdSerie());

		Date fechaInicial = serieUdocs.getFextremainicial();
		Date fechaFinal = serieUdocs.getFextremafinal();
		Map.Entry sistemaProductor = null;

		// Definición de la ficha
		DefFicha defFicha = DefFichaFactory.getInstance(getServiceClient())
				.getDefFichaById(fichaDescripcionUdoc);

		// Definición del formato de la ficha
		DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
				getServiceClient()).getDefFmtFicha(fichaDescripcionUdoc,
				TipoAcceso.EDICION);

		// Obtener información de la caja en la que estaba la fracción de serie
		// original
		String idUnidadInstalacion = new String();
		List udocsEnUI = _udocEnUIDepositoDBEntity
				.getPartesUdocByIDElementoCF(udoc.getId());
		int posIni = 1;
		String signaturaUDocOriginal = Constants.STRING_EMPTY;
		if (udocsEnUI != null && udocsEnUI.size() > 0) {
			UDocEnUiDepositoVO udocEnUIVO = (UDocEnUiDepositoVO) udocsEnUI
					.get(0);
			idUnidadInstalacion = udocEnUIVO.getIduinstalacion();
			posIni = udocEnUIVO.getPosudocenui();
			signaturaUDocOriginal = udocEnUIVO.getSignaturaudoc();
		}

		// Obtener información del fondo
		FondoVO fondoVO = gestionFondos.getFondoXId(serieUdocs.getIdFondo());

		// Esto sólo deberá hacerse ahora si la división de fracción de serie no
		// se hace con ficha, si se hace con ficha, estos campos
		// ya aparecen prerrellenados en ella y no hay que hacer la conservación
		// aquí
		/*
		 * Mantenimiento de información de fracción de serie a unidad
		 * documental: Rellenamos un map que se utilizará más adelante por cada
		 * unidad documental en la que se va a dividir la fracción de serie para
		 * saber qué campos descriptivos mantener
		 */
		Map mapCamposFSUDoc = null;
		if (StringUtils.isEmpty(divisionFS.getIdFicha()))
			mapCamposFSUDoc = getMapCamposFSUDoc(udoc.getId());

		// Obtenemos la caja en la que se encuentra la fracción de serie
		UInsDepositoVO uInsDepositoVO = depositoBI
				.getUinsEnDeposito(idUnidadInstalacion);

		// Obtenemos toda la lista de unidades documentales que había en la caja
		// originalmente para actualizar sus datos al final
		List todasUdocsEnUI = depositoBI
				.getUDocsEnUInstalacion(idUnidadInstalacion);

		// Posición a partir de la que se va a insertar la fracción de serie en
		// la caja
		int posActual = posIni == 0 ? 1 : posIni;

		// Hasta este punto no era necesario iniciar la transacción, a partir de
		// aquí ya se van a producir actualizaciones e inserciones
		// que deben realizarse todas o ninguna
		iniciarTransaccion();

		StringBuffer uDocsDeFraccionSerie = new StringBuffer();
		for (Iterator i = udocsEnFraccionSerie.iterator(); i.hasNext();) {
			UDocEnFraccionSerieVO udocEnFraccionSerie = (UDocEnFraccionSerieVO) i
					.next();

			// Obtener la nueva signatura para la unidad documental
			String nuevaSignatura = udoc.getCodigo();

			// Calcular nueva signatura
			nuevaSignatura = Constants.getSignaturaUnidadDocumental(
					uInsDepositoVO.getSignaturaui(),
					(new Integer(posActual)).toString());
			udocEnFraccionSerie.setSignatura(nuevaSignatura);

			// Obtener el código para la unidad documental
			String codigoUdoc = Constants.STRING_EMPTY;
			if (ConfigConstants.getInstance().getCodigoUdocUnico()) {
				codigoUdoc = CodigoUdocUtil.obtenerCodigoUdocFormateado(
						_nSecUDocBEntity, _elementoCuadroClasificacionDBEntity);
			} else {
				codigoUdoc = udocEnFraccionSerie.getSignatura();
			}

			// Crear la unidad documental
			fondos.vos.UnidadDocumentalVO aUdocEnCF = createUnidadDocumental(
					serieUdocs, valoracion, nivelUnidadDocumental
					// , aUdoc.getSignatura()
					, udoc, udocEnFraccionSerie, sistemaProductor
					// , udoc.getTipoDocumental()
					// , udoc.getIdLCA()
					, udocsEnUI, fondoVO, codigoUdoc, posActual);

			// Generar la descripción para la unidad documental
			if (StringUtils.isEmpty(divisionFS.getIdFicha())) {
				descripcionUdoc.generateDescripcionUdoc(
						udocEnFraccionSerie.asXML(), aUdocEnCF.getId(),
						defFicha, defFmtFicha, fichaDescripcionUdoc,
						TipoFicha.FICHA_ELEMENTO_CF,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
			} else {

				// Obtener los valores de la ficha de la descripción
				ValoresFicha valoresFicha = ValoresFicha.getInstance(
						getServiceSession(), udocEnFraccionSerie.getIdUDoc(),
						TipoFicha.FICHA_UDOCFS, udocEnFraccionSerie);

				// Actualizar el campo info con el xml con los valores
				// actualizados
				_udocEnDivisionFSDbEntity.updateXmlInfo(
						udocEnFraccionSerie.getIdUDoc(),
						udocEnFraccionSerie.asXMLWithValores(valoresFicha));

				// Insertar la descripción de la unidad documental
				generateDescripcionUdoc(descripcionBI,
						udocEnFraccionSerie.getIdUDoc(), aUdocEnCF.getId());
			}

			if (StringUtils.isEmpty(divisionFS.getIdFicha())
					&& mapCamposFSUDoc != null && mapCamposFSUDoc.size() > 0) {
				// Añadir los campos descriptivos que se han indicado que se
				// conservan de la fracción de serie por fichero de mapeo
				generateDescripcionMapeo(mapCamposFSUDoc, aUdocEnCF.getId(),
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
			}

			// Esto ya no se hace aquí, se hace dentro del método de
			// crearUnidadDocumental, asociar la udoc con la caja
			// depositoBI.atachUdocToElementoCF(aUdoc.getId(),
			// aUdocEnCF.getId());
			if (fechaInicial == null
					|| fechaInicial.after(udocEnFraccionSerie.getFechaExtIni()))
				fechaInicial = udocEnFraccionSerie.getFechaExtIni();
			if (fechaFinal == null
					|| fechaFinal.before(udocEnFraccionSerie.getFechaExtFin()))
				fechaFinal = udocEnFraccionSerie.getFechaExtFin();

			// Insertar fechas de unidad documental
			uDocsDeFraccionSerie.append(udocEnFraccionSerie.getNumExp())
					.append(":").append(udocEnFraccionSerie.getAsunto())
					.append("- ");

			// Incrementamos en 1 la posición de la Unidad documental
			posActual = posActual + 1;
		}

		pistaAuditoria.auditaFraccionSerieDividida(locale,
				uDocsDeFraccionSerie.toString());

		// Actualizar las posiciones, signaturas (y código si hace falta) del
		// resto de Unidades Documentales que ya había en la caja
		if (todasUdocsEnUI != null) {
			Iterator it = todasUdocsEnUI.iterator();
			while (it.hasNext()) {
				UDocEnUiDepositoVO udui = (UDocEnUiDepositoVO) it.next();
				if (!udui.getIdunidaddoc().equals(udoc.getId())) {
					// Calcular nueva signatura
					String nuevaSignatura = Constants
							.getSignaturaUnidadDocumental(
									uInsDepositoVO.getSignaturaui(),
									(new Integer(posActual)).toString());

					// Calcular nueva identificación
					String nuevaIdentificacion = udui.getIdentificacion();
					String[] partesIdentificacion = nuevaIdentificacion
							.split(Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL);
					nuevaIdentificacion = partesIdentificacion[0]
							+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL
							+ nuevaSignatura;

					_udocEnUIDepositoDBEntity
							.updateSignaturaEIdentificacionYPosUdoc(
									idUnidadInstalacion, udui.getIdunidaddoc(),
									udui.getSignaturaudoc(), nuevaSignatura,
									nuevaIdentificacion, posActual);

					if (!ConfigConstants.getInstance().getCodigoUdocUnico()) {
						ElementoCuadroClasificacionVO elemento = managerCuadroClf
								.getElementoCuadroClasificacion(udui
										.getIdunidaddoc());
						elemento.setCodigo(nuevaSignatura);
						_elementoCuadroClasificacionDBEntity
								.updateElementoCuadroClasificacion(elemento);

					}
					// Incrementamos la posición actual de la udoc en la caja
					posActual = posActual + 1;
				}
			}
		}

		// Actualizar información de serie
		// Volumen y contenido
		managerSeries.updateVolumenSerieNoTransaccional(serieUdocs.getId());
		managerSeries.updateContenidoSerieNoTransaccional(serieUdocs.getId());

		// Fechas extremas
		managerSeries.actualizarFechasSerie(serieUdocs.getId(), fechaInicial,
				fechaFinal, descripcionBI);
		pistaAuditoria.auditaActualizadaFechasExtremas(locale,
				serieUdocs.getCodReferencia());

		// Eliminar toda la información referente a la fracción de serie
		String codRefFS = divisionFS.getCodReferencia();
		deleteFraccionSerie(uInsDepositoVO.getId(), udoc.getId(),
				signaturaUDocOriginal);
		pistaAuditoria.auditaEliminarFraccionSerie(locale, codRefFS);

		// Actualiza el estado de validación de todas las unidades documentales
		// en la división de fracción de serie a validadas
		_udocEnDivisionFSDbEntity.setEstadoValidacion(divisionFS.getIdFS(),
				true);

		// Actualizar el estado de la división de fracción de serie a validada
		divisionFS.setEstado(EstadoDivisionFS.VALIDADA);
		divisionFS.setFechaEstado(DateUtils.getFechaActual());
		divisionFS.setInfo(udoc, idFSEnREntrega);
		_divisionFSDbEntity.updateDivisionFS(divisionFS);

		// Eliminar las marcas de bloqueo por estar siendo dividida de la caja
		// que contenía la fracción de serie
		int marcas = MarcaUtil.generarMarcas(new int[] {});
		_unidadInstalacionDepositoDBEntity.updateMarcaUnidadInstalacion(
				divisionFS.getIdUIDeposito(), marcas);

		commit();
	}

	/**
	 * Método para guardar la descripción de las unidades documentales que se
	 * quiere mantener de la fracción de serie
	 * 
	 * @param mapCamposFSUDoc
	 * @param idUDoc
	 */

	public void generateDescripcionMapeo(Map mapCamposFSUDoc, String idUDoc,
			int tipoElemento) {
		if (mapCamposFSUDoc != null && mapCamposFSUDoc.entrySet() != null) {
			Iterator it = mapCamposFSUDoc.entrySet().iterator();

			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				if (entry != null) {
					String keyValorCampo = (String) entry.getKey();
					Integer keyValorCampoInt = keyValorCampo == null ? new Integer(
							0) : new Integer(keyValorCampo);
					Map mapCampoTipo = (Map) entry.getValue();
					if (mapCampoTipo != null) {
						Iterator itCampo = mapCampoTipo.entrySet().iterator();
						while (itCampo.hasNext()) {
							Entry idCampo = (Entry) itCampo.next();
							String keyValorIdCampo = (String) idCampo.getKey();
							// Integer keyValorIdCampoInt = keyValorIdCampo ==
							// null ? new Integer(0) : new
							// Integer(keyValorIdCampo);
							List listaValores = (List) idCampo.getValue();
							Iterator itListaValores = listaValores.iterator();
							while (itListaValores.hasNext()) {
								switch (keyValorCampoInt.intValue()) {
								case ValorCampoGenericoVO.TIPO_REFERENCIA: {
									CampoReferenciaVO campoReferenciaVO = (CampoReferenciaVO) itListaValores
											.next();
									campoReferenciaVO
											.setIdCampo(keyValorIdCampo);
									campoReferenciaVO.setIdObjeto(idUDoc);
									campoReferenciaVO
											.setTipoElemento(tipoElemento);
									if (tipoElemento == ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
										_referenciaDBEntity
												.insertValue(campoReferenciaVO);
									else
										// tipoElemento ==
										// ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCRE
										// o
										// ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCFS
										_referenciaUDocDBEntity
												.insertValue(campoReferenciaVO);
									break;
								}
								case ValorCampoGenericoVO.TIPO_NUMERICO: {
									CampoNumericoVO campoNumericoVO = (CampoNumericoVO) itListaValores
											.next();
									campoNumericoVO.setIdCampo(keyValorIdCampo);
									campoNumericoVO.setIdObjeto(idUDoc);
									campoNumericoVO
											.setTipoElemento(tipoElemento);
									if (tipoElemento == ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
										_numeroDBEntity
												.insertValue(campoNumericoVO);
									else
										// tipoElemento ==
										// ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCRE
										// o
										// ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCFS
										_numeroUDocDBEntity
												.insertValue(campoNumericoVO);
									break;
								}
								case ValorCampoGenericoVO.TIPO_TEXTO_CORTO: {
									CampoTextoVO campoTextoVO = (CampoTextoVO) itListaValores
											.next();
									campoTextoVO.setIdCampo(keyValorIdCampo);
									campoTextoVO.setIdObjeto(idUDoc);
									campoTextoVO.setTipoElemento(tipoElemento);
									if (tipoElemento == ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
										_textoCortoDBEntity
												.insertValue(campoTextoVO);
									else
										_textoCortoUDocDBEntity
												.insertValue(campoTextoVO);
									break;
								}
								case ValorCampoGenericoVO.TIPO_TEXTO_LARGO: {
									CampoTextoVO campoTextoVO = (CampoTextoVO) itListaValores
											.next();
									campoTextoVO.setIdCampo(keyValorIdCampo);
									campoTextoVO.setIdObjeto(idUDoc);
									campoTextoVO.setTipoElemento(tipoElemento);
									if (tipoElemento == ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
										_textoLargoDBEntity
												.insertValue(campoTextoVO);
									else
										_textoLargoUDocDBEntity
												.insertValue(campoTextoVO);
									break;
								}
								case ValorCampoGenericoVO.TIPO_FECHA: {
									CampoFechaVO campoFechaVO = (CampoFechaVO) itListaValores
											.next();
									campoFechaVO.setIdCampo(keyValorIdCampo);
									campoFechaVO.setIdObjeto(idUDoc);
									campoFechaVO.setTipoElemento(tipoElemento);
									if (tipoElemento == ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
										_fechaDBEntity
												.insertValue(campoFechaVO);
									else
										_fechaUDocDBEntity
												.insertValue(campoFechaVO);
									break;
								}
								}
							}
						}
					}
				}
			}
		}
	}

	public void deleteFraccionSerie(String idUI, String idFraccionSerie,
			String signaturaUDoc) {
		_textoLargoDBEntity.deleteValueXIdElemento(idFraccionSerie,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		_textoCortoDBEntity.deleteValueXIdElemento(idFraccionSerie,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		_fechaDBEntity.deleteValueXIdElemento(idFraccionSerie,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		_numeroDBEntity.deleteValueXIdElemento(idFraccionSerie,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		_referenciaDBEntity.deleteValueXIdElemento(idFraccionSerie,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		_udocEnUIDepositoDBEntity.deleteUdoc(idUI, idFraccionSerie,
				signaturaUDoc);
		_unidadDocumentalDBEntity.deleteUnidadDocumental(idFraccionSerie);
		_elementoCuadroClasificacionDBEntity.deleteElementoCF(idFraccionSerie);

	}

	public UnidadDocumentalVO createUnidadDocumental(SerieVO serie,
			ValoracionSerieVO valoracion, INivelCFVO nivelUnidadDocumental,
			UnidadDocumental udoc, UDocEnFraccionSerieVO infoUnidadDocumental,
			Map.Entry sistemaProductor, List udocsEnCaja, FondoVO fondo,
			String codigoUDoc, int posUDocEnUI) {
		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventAlta(this);
		checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);

		ElementoCuadroClasificacionVO elementoCF = new ElementoCuadroClasificacion();
		elementoCF.setCodigo(codigoUDoc);
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
				codigoUDoc);
		// .append(infoUnidadDocumental.getSignatura());
		elementoCF.setIdFondo(serie.getIdFondo());
		elementoCF.setCodRefFondo(serie.getCodRefFondo());
		elementoCF.setCodReferencia(codigoReferenciaUdoc.toString());
		elementoCF.setEstado(IElementoCuadroClasificacion.VIGENTE);
		elementoCF.setIdLCA(udoc.getIdLCA());
		elementoCF.setNivelAcceso(getNivelAcceso(infoUnidadDocumental,
				valoracion));

		// Rellenar con el idArchivo de la unidad documental que se está
		// dividiendo
		elementoCF.setIdArchivo(udoc.getIdArchivo());
		String fichaDescripcionUdoc = nivelUnidadDocumental
				.getIdFichaDescrPref();
		String idRepEcmUdoc = nivelUnidadDocumental.getIdRepEcmPref();

		String fichaDescripcionPrefUDoc = serie
				.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());
		String repEcmPrefUDoc = serie.getIdRepEcmPrefUdoc(nivelUnidadDocumental
				.getId());

		// Si la serie tiene definidos listas de volúmenes y ficha preferente
		// para las u.docs., usar estas
		if (fichaDescripcionPrefUDoc != null)
			fichaDescripcionUdoc = fichaDescripcionPrefUDoc;

		if (repEcmPrefUDoc != null)
			idRepEcmUdoc = repEcmPrefUDoc;

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
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAlta(locale, logEvent, elementoCF,
				nivelUnidadDocumental.getNombre(), serie.getCodReferencia());
		elementoCF = _elementoCuadroClasificacionDBEntity
				.insertElementoCF(elementoCF);
		IUnidadDocumental unidadDocumental = new UnidadDocumental(elementoCF);
		unidadDocumental.setNumExp(infoUnidadDocumental.getNumExp());
		unidadDocumental.setTipoDocumental(udoc.getTipoDocumental());
		unidadDocumental = (IUnidadDocumental) _unidadDocumentalDBEntity
				.insertUnidadDocumental(unidadDocumental);

		// Añadido => meter en udocEnUI
		if (udocsEnCaja != null && udocsEnCaja.size() > 0) {
			UDocEnUiDepositoVO udocEnUIDeposito = (UDocEnUiDepositoVO) udocsEnCaja
					.get(0);
			if (udocEnUIDeposito != null) {
				UDocEnUiDepositoVO nuevaUdocEnUiDeposito = new UDocEnUiDepositoVO();
				nuevaUdocEnUiDeposito.setIduinstalacion(udocEnUIDeposito
						.getIduinstalacion());
				nuevaUdocEnUiDeposito.setIdunidaddoc(unidadDocumental.getId());
				nuevaUdocEnUiDeposito.setSignaturaudoc(infoUnidadDocumental
						.getSignatura());
				nuevaUdocEnUiDeposito.setIdentificacion(UDocFondo
						.getIdentificacion(fondo.getCodReferencia(),
								infoUnidadDocumental.getSignatura()));
				nuevaUdocEnUiDeposito.setPosudocenui(posUDocEnUI);
				// nuevaUdocEnUiDeposito.setIdudocre(udocEnUIDeposito.getIdudocre());
				nuevaUdocEnUiDeposito.setIdudocre(infoUnidadDocumental
						.getIdUDoc());

				_udocEnUIDepositoDBEntity
						.insertUDocEnUiDeposito(nuevaUdocEnUiDeposito);
			}
		}

		commit();

		return unidadDocumental;
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
	private int getNivelAcceso(UDocEnFraccionSerieVO udoc,
			ValoracionSerieVO valoracion) {
		int nivelAcceso = NivelAcceso.ARCHIVO;

		if (valoracion != null) {
			switch (valoracion.getTipoRegimenAcceso()) {
			case ValoracionSerieVO.REGIMEN_ACCESO_LIBRE:
				nivelAcceso = NivelAcceso.PUBLICO;
				break;

			case ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL:
				Calendar fechaFin = Calendar.getInstance();
				fechaFin.setTime(udoc.getFechaExtFin());
				fechaFin.add(Calendar.YEAR, valoracion.getAnosRegimenAcceso());

				if (fechaFin.before(Calendar.getInstance()))
					nivelAcceso = NivelAcceso.PUBLICO;
				break;

			default: // ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_PERMANENTE
			}
		}

		return nivelAcceso;
	}

	// public DivisionFraccionSerieVO createDivisionFS(String idFicha, String
	// idNivelDocumental, String idFSEnREntrega, UnidadDocumental udoc) {
	public List createDivisionFS(DivisionFraccionSerieVO divisionFSVO,
			UnidadDocumentalPO udoc, DivisionFraccionSerieForm udocForm,
			GestionFraccionSerieBI fraccionSerieBI, List rangos) {

		// Marcamos como activado el bloqueo por estar siendo dividida
		// int marcas = MarcaUtil.generarMarcas(new int [] {1,0});
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();

		iniciarTransaccion();

		// Insertamos la división de fracción de serie
		divisionFSVO.setEstado(EstadoDivisionFS.ABIERTA);
		divisionFSVO.setIdUsrGestor(getServiceClient().getId());
		divisionFSVO.setFechaEstado(DateUtils.getFechaActual());

		// Rellenar la información del productor que van a tener las unidades
		// documentales
		// en las que se divida la fracción de serie que por defecto será el
		// mismo que el suyo
		// ProductorUnidadDocumentalVO productorUDoc = null;
		OrganoProductorVO organoProductorVO = null;
		List listaProductoresUDoc = _referenciaDBEntity.getValues(divisionFSVO
				.getIdFS(), csa.getConfiguracionDescripcion().getProductor(),
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);
		if (listaProductoresUDoc != null && listaProductoresUDoc.size() > 0) {
			Iterator it = listaProductoresUDoc.iterator();
			if (it.hasNext()) {
				CampoReferenciaVO referenciaProductor = (CampoReferenciaVO) it
						.next();
				// Obtenemos el objeto productor de donde sacar el id de órgano
				// a partir del id de organo remitente (descriptor)
				organoProductorVO = _organoProductorDBEntity
						.getOrgProductorXIdDescr(referenciaProductor
								.getIdObjRef());
			}
		}

		divisionFSVO.setProductor(organoProductorVO);

		// Insertamos la división de fracción de serie
		divisionFSVO = _divisionFSDbEntity.insertDivisionFS(divisionFSVO);

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		DivisionFSToPOTransformer transformer = new DivisionFSToPOTransformer(
				services);
		DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) transformer
				.transform(divisionFSVO);
		divisionFSPO.setFraccionSeriePO(udoc);

		// Inicializar las unidades documentales en división de fracción de
		// serie
		Integer nofUdocs = new Integer(udocForm.getNofUDocs());
		List udocsEnFraccionSerie = initListaUDocsFraccionSerie(divisionFSPO,
				udocForm, nofUdocs.intValue(), fraccionSerieBI, rangos);

		// Marcamos la caja que contiene la fracción de serie como bloqueada
		// para que no pueda ser organizada ni prestada o consultada
		_unidadInstalacionDepositoDBEntity
				.updateMarcaUnidadInstalacion(
						divisionFSVO.getIdUIDeposito(),
						MarcaUtil
								.generarMarcas(new int[] { MarcaUInstalacionConstants.POSICION_BIT_MARCA_BLOQUEADA_DIVISIONFS }));

		commit();

		return udocsEnFraccionSerie;
	}

	private List initListaUDocsFraccionSerie(
			DivisionFraccionSeriePO divisionFSPO,
			DivisionFraccionSerieForm udocForm, int nofUdocs,
			GestionFraccionSerieBI fraccionSerieBI, List rangos) {

		List ret = new ArrayList();
		int posIni = 1;
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String[] listaUnIdUDoc = new String[] { divisionFSPO.getFraccionSerie()
				.getId() };
		String signaturaCaja = Constants.STRING_EMPTY;
		// List rangos = null;

		List udocsEnDeposito = depositoBI.getUDocsById(listaUnIdUDoc);
		if (udocsEnDeposito != null && udocsEnDeposito.size() > 0) {
			UDocEnUiDepositoVO udocEnUIVO = (UDocEnUiDepositoVO) udocsEnDeposito
					.get(0);
			posIni = udocEnUIVO.getPosudocenui();
			UInsDepositoVO uInsDepositoVO = depositoBI
					.getUinsEnDeposito(udocEnUIVO.getIduinstalacion());
			signaturaCaja = uInsDepositoVO.getSignaturaui();
		}

		posIni = posIni == 0 ? 1 : posIni;

		if (nofUdocs > 0) {
			for (int i = 0; i < nofUdocs; i++) {
				UDocEnFraccionSerieVO udocEnFraccionSerieVO = new UDocEnFraccionSerieVO();
				String nuevaSignatura = Constants.STRING_EMPTY;

				// udocEnFraccionSerieVO.setIdUDoc(new Integer(i+1).toString());

				udocEnFraccionSerieVO.setOrden(i + 1);
				udocEnFraccionSerieVO.setIdFS(divisionFSPO.getFraccionSerie()
						.getId());
				udocEnFraccionSerieVO.setAsunto(udocForm.getAsunto());
				udocEnFraccionSerieVO.setFechaExtIni(DateUtils.getDate(udocForm
						.getFechaInicio()));
				udocEnFraccionSerieVO.setFechaExtFin(DateUtils.getDate(udocForm
						.getFechaFin()));

				if (divisionFSPO.getNivelDocumental() != null
						&& divisionFSPO.getNivelDocumental().getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
					if (rangos != null && rangos.size() > 0) {
						String numExpRangos = null;
						Iterator it = rangos.iterator();
						while (it.hasNext()) {
							RangoVO rango = (RangoVO) it.next();
							numExpRangos = (numExpRangos == null ? ""
									: numExpRangos
											+ Constants.DELIMITADOR_RANGOS)
									+ rango.getDesde()
									+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
									+ rango.getHasta();

							udocEnFraccionSerieVO.addRango(rango);
						}
						udocEnFraccionSerieVO.setNumExp(numExpRangos);
					}
				} else {
					udocEnFraccionSerieVO.setNumExp(udocForm.getNumExp());
				}

				nuevaSignatura = Constants.getSignaturaUnidadDocumental(
						signaturaCaja, (new Integer(posIni)).toString());

				udocEnFraccionSerieVO.setSignatura(nuevaSignatura);

				fraccionSerieBI.addUDocToDivisionFS(udocEnFraccionSerieVO,
						divisionFSPO, false);
				ret.add(udocEnFraccionSerieVO);

				posIni = posIni + 1;

			} // For
		}

		return ret;
	}

	public DivisionFraccionSerieVO searchDivisionFS(String idFS) {
		return _divisionFSDbEntity.getDivisionFSXId(idFS);
	}

	public List getUDocsEnDivisionFS(String idFS, boolean tieneDescripcion) {

		List udocsEnDivisionFS = _udocEnDivisionFSDbEntity
				.getUDocsEnDivisionFSVO(idFS);

		if (!ListUtils.isEmpty(udocsEnDivisionFS)) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			DivisionFraccionSerieVO divisionFSVO = searchDivisionFS(idFS);

			Iterator it = udocsEnDivisionFS.iterator();

			while (it.hasNext()) {
				UDocEnFraccionSerieVO udocEnFSVO = (UDocEnFraccionSerieVO) it
						.next();
				if (tieneDescripcion) {
					// Revisar si en la descripción de unidad documental se ha
					// establecido o modificado el interesado principal y
					// actualizarla
					transferencias.vos.InteresadoVO interesado = descripcionBI
							.getInteresadoPrincipal(
									udocEnFSVO.getIdUDoc(),
									ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
					if (udocEnFSVO.getXinfo() != null)
						udocEnFSVO.getXinfo()
								.setInteresadoPrincipal(interesado);
				}

				// Establecemos el nivel documental y en su caso la ficha
				// seleccionada a partir de los datos de la
				// división de fracción de serie
				if (divisionFSVO != null) {
					udocEnFSVO.setIdFichaDescr(divisionFSVO.getIdFicha());
					udocEnFSVO.setIdNivelDocumental(divisionFSVO
							.getIdNivelDocumental());
				}
			}
		}

		return udocsEnDivisionFS;

	}

	public void deleteUDocEnDivisionFS(String idUDoc,
			boolean eliminarDescripcion) {

		// Comprobar si hay que eliminar los valores de las tablas de
		// descripción
		if (eliminarDescripcion) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			// Eliminar los valores de las tablas de descripción de la unidad en
			// la división de fracción de serie
			descripcionBI.eliminarValoresCamposUDocRE(idUDoc,
					ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
		}

		_udocEnDivisionFSDbEntity.deleteUDocEnDivisionFS(idUDoc);
	}

	public void deleteUDocsEnDivisionFS(String[] idsUDoc,
			boolean eliminarDescripcion) {

		if (idsUDoc != null) {

			iniciarTransaccion();

			for (int i = 0; i < idsUDoc.length; i++) {
				String idUDoc = idsUDoc[i];
				deleteUDocEnDivisionFS(idUDoc, eliminarDescripcion);
			}

			commit();
		}
	}

	public UDocEnFraccionSerieVO addUDocToDivisionFS(
			UDocEnFraccionSerieVO udocEnFS, DivisionFraccionSerieVO divisionFS,
			boolean mantenerInfo) {
		// UDocEnFraccionSerieVO udocEnFsVO = null;
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		iniciarTransaccion();

		// udocEnFsVO
		// =_udocEnDivisionFSDbEntity.insertUDocEnDivisionFS(udocEnFS);
		_udocEnDivisionFSDbEntity.insertUDocEnDivisionFS(udocEnFS);

		if (StringUtils.isNotEmpty(divisionFS.getIdFicha())) {

			// Definición de la ficha
			DefFicha defFicha = DefFichaFactory.getInstance(getServiceClient())
					.getDefFichaById(divisionFS.getIdFicha());

			// Definición del formato de la ficha
			DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
					getServiceClient()).getDefFmtFicha(divisionFS.getIdFicha(),
					TipoAcceso.EDICION);

			// Cargar la clase de mapeo de campos de unidad documental en
			// relación de entrega a descripción
			MapDescripcionUdoc descripcionUdoc = new MapDescripcionUdoc(
					descripcionBI, divisionFS.getIdFicha());

			// Insertar la descripción de la unidad documental
			descripcionUdoc.generateDescripcionUdoc(udocEnFS.asXML(),
					udocEnFS.getIdUDoc(), defFicha, defFmtFicha,
					divisionFS.getIdFicha(), TipoFicha.FICHA_UDOCFS,
					ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);

			// Prerrellenar la ficha con los campos que se ha decidido mapear
			// por fichero de configuración siempre que no se haya elegido
			// duplicar
			// una unidad documental anterior
			if (!mantenerInfo) {
				HashMap mapCamposFSUDoc = getMapCamposFSUDoc(divisionFS
						.getIdFS());
				generateDescripcionMapeo(mapCamposFSUDoc, udocEnFS.getIdUDoc(),
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
			}

			// Eliminar la información del productor y fechas extremas de la
			// información extra de la unidad documental que es la que se guarda
			// en el campo INFO y marcar la unidad como descrita para que no se
			// vuelva a crear la ficha al volver a entrar
			// udoc.getExtraInfo().cleanProductor();

			// Revisar si en la descripción de unidad documental se ha
			// establecido o modificado el interesado principal y actualizarla
			transferencias.vos.InteresadoVO interesado = descripcionBI
					.getInteresadoPrincipal(udocEnFS.getIdUDoc(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
			if (udocEnFS.getXinfo() != null)
				udocEnFS.getXinfo().setInteresadoPrincipal(interesado);

			udocEnFS.getXinfo().cleanFechasExtremas();
			udocEnFS.getXinfo().setTieneDescripcion(Constants.TRUE_STRING);

			_udocEnDivisionFSDbEntity.updateUDocEnDivisionFS(udocEnFS);

		}

		commit();

		return udocEnFS;
	}

	public int getCountDivisionesFSEnElaboracion() {
		int[] estados = new int[] { EstadoDivisionFS.ABIERTA,
				EstadoDivisionFS.PENDIENTE_VALIDACION };
		return _divisionFSDbEntity.getCountDivisionFSXUsr(getServiceClient()
				.getId(), estados);
	}

	public int getCountDivisionesFSFinalizadas() {
		int[] estados = new int[] { EstadoDivisionFS.VALIDADA };
		return _divisionFSDbEntity.getCountDivisionFSXUsr(getServiceClient()
				.getId(), estados);
	}

	public List getDivisionesFSEnElaboracion() {
		int[] estados = new int[] { EstadoDivisionFS.ABIERTA,
				EstadoDivisionFS.PENDIENTE_VALIDACION };
		return _divisionFSDbEntity.getDivisionesFSXUsr(getServiceClient()
				.getId(), estados);
	}

	public List getDivisionesFSFinalizadas() {
		int[] estados = new int[] { EstadoDivisionFS.VALIDADA };
		return _divisionFSDbEntity.getDivisionesFSXUsr(getServiceClient()
				.getId(), estados);
	}

	public void deleteFraccionesSerie(String[] idsDivisionFS)
			throws ActionNotAllowedException {
		PistaAuditoriaFraccionSerie pistaAuditoria = AuditFondos
				.crearPistaAuditoria(
						ArchivoActions.FONDOS_MODULE_BORRADO_DIVISIONFS, this);

		// Chequeamos que tiene el permiso adecuado para manejar divisiones de
		// fracción de serie
		checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);
		List divisionesFS = _divisionFSDbEntity
				.getDivisionesFSXId(idsDivisionFS);

		// comprobar si son divisiones de fracción de serie borrables
		boolean canBeDone = true;

		for (Iterator itDivisionesFSVO = divisionesFS.iterator(); itDivisionesFSVO
				.hasNext();) {
			DivisionFraccionSerieVO divisionFSVO = (DivisionFraccionSerieVO) itDivisionesFSVO
					.next();
			if (divisionFSVO.getEstado() != EstadoDivisionFS.ABIERTA) {
				canBeDone = false;
				break;
			}
		}

		if (!canBeDone)
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XDIVISION_FS_ESTADO_INCORRECTO_BORRADO);

		int marcas = MarcaUtil.generarMarcas(new int[] {});

		// borrar las divisiones de fracción de serie y sus unidades
		// documentales
		// si las unidades documentales eran con descripción, eliminar también
		// la posible información que se hubiese rellenado
		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();
		for (Iterator itDivisionesFSVO = divisionesFS.iterator(); itDivisionesFSVO
				.hasNext();) {
			DivisionFraccionSerieVO divisionFSVO = (DivisionFraccionSerieVO) itDivisionesFSVO
					.next();
			pistaAuditoria.addDetalleBasico(locale, divisionFSVO);
			deleteDivisionFS(divisionFSVO);

			// Actualizar las marcas de bloqueo de la caja
			_unidadInstalacionDepositoDBEntity.updateMarcaUnidadInstalacion(
					divisionFSVO.getIdUIDeposito(), marcas);
		}
		commit();
	}

	private void deleteDivisionFS(DivisionFraccionSerieVO divisionFSVO) {

		String idFS = divisionFSVO.getIdFS();
		iniciarTransaccion();

		// borrar la información descriptiva de las unidades documentales en
		// division f.s.
		// Comprobar si hay que eliminar los valores de las tablas de
		// descripción
		if (StringUtils.isNotEmpty(divisionFSVO.getIdFicha())) {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			List udocsEnDivisionFS = _udocEnDivisionFSDbEntity
					.getUDocsEnDivisionFSVO(idFS);

			if (udocsEnDivisionFS != null && udocsEnDivisionFS.size() > 0) {
				Iterator it = udocsEnDivisionFS.iterator();
				while (it.hasNext()) {
					UDocEnFraccionSerieVO udocEnFS = (UDocEnFraccionSerieVO) it
							.next();
					// Eliminar los valores de las tablas de descripción de la
					// unidad en la división de fracción de serie
					descripcionBI.eliminarValoresCamposUDocRE(
							udocEnFS.getIdUDoc(),
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
				}
			}
		}

		// borrar las unidades documentales en la división de f.s.
		_udocEnDivisionFSDbEntity.deleteUDocsEnDivisionFS(idFS);

		// borrar la división de fracción de serie
		_divisionFSDbEntity.deleteDivisionFS(idFS);

		commit();

	}

	public UDocEnFraccionSerieVO getUDocEnDivisionFS(String idUDoc) {

		UDocEnFraccionSerieVO udocEnFSVO = null;
		// ServiceRepository services =
		// ServiceRepository.getInstance(getServiceSession());
		// GestionDescripcionBI descripcionBI =
		// services.lookupGestionDescripcionBI();

		udocEnFSVO = _udocEnDivisionFSDbEntity.getUDocEnDivisionFSXId(idUDoc);

		/*
		 * if (tieneDescripcion) { // Revisar si en la descripción de unidad
		 * documental se ha establecido o modificado el interesado principal y
		 * actualizarla transferencias.vos.InteresadoVO interesado =
		 * descripcionBI.getInteresadoPrincipal(udocEnFSVO.getIdUDoc(),
		 * ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCFS);
		 * udocEnFSVO.getXinfo().setInteresadoPrincipal(interesado); }
		 */

		return udocEnFSVO;
	}

	public void updateUDocEnDivisionFS(
			UDocEnFraccionSerieVO udocEnFraccionSerieVO) {

		/*
		 * if (tieneDescripcion) { ServiceRepository services =
		 * ServiceRepository.getInstance(getServiceSession());
		 * GestionDescripcionBI descripcionBI =
		 * services.lookupGestionDescripcionBI();
		 * 
		 * // Revisar si en la descripción de unidad documental se ha
		 * establecido o modificado el interesado principal y actualizarla
		 * transferencias.vos.InteresadoVO interesado =
		 * descripcionBI.getInteresadoPrincipal
		 * (udocEnFraccionSerieVO.getIdUDoc(),
		 * ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCFS); if
		 * (udocEnFraccionSerieVO.getXinfo() != null)
		 * udocEnFraccionSerieVO.getXinfo().setInteresadoPrincipal(interesado);
		 * }
		 */

		_udocEnDivisionFSDbEntity.updateUDocEnDivisionFS(udocEnFraccionSerieVO);
	}

	public UDocEnFraccionSerieVO getUDocEnDivisionFSConInfoDesc(String idUDoc) {

		UDocEnFraccionSerieVO udocEnFSVO = null;
		// ServiceRepository services =
		// ServiceRepository.getInstance(getServiceSession());
		// GestionDescripcionBI descripcionBI =
		// services.lookupGestionDescripcionBI();

		// udocEnFSVO = getUDocEnDivisionFS(idUDoc, tieneDescripcion);
		udocEnFSVO = getUDocEnDivisionFS(idUDoc);

		DivisionFraccionSerieVO divisionFSVO = searchDivisionFS(udocEnFSVO
				.getIdFS());

		// Establecemos el nivel documental y en su caso la ficha seleccionada a
		// partir de los datos de la
		// división de fracción de serie
		if (divisionFSVO != null) {
			udocEnFSVO.setIdFichaDescr(divisionFSVO.getIdFicha());
			udocEnFSVO
					.setIdNivelDocumental(divisionFSVO.getIdNivelDocumental());

			/*
			 * if (StringUtils.isNotEmpty(divisionFSVO.getIdFicha())) { //
			 * Revisar si en la descripción de unidad documental se ha
			 * establecido o modificado el interesado principal y actualizarla
			 * transferencias.vos.InteresadoVO interesado =
			 * descripcionBI.getInteresadoPrincipal(udocEnFSVO.getIdUDoc(),
			 * ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCFS);
			 * udocEnFSVO.getXinfo().setInteresadoPrincipal(interesado); }
			 */
		}

		return udocEnFSVO;
	}

	public void conservarDescripcion(String idUDocOrigen, String idUDocDestino) {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		descripcionBI.copiarCamposUDocREaUDocRE(idUDocOrigen, idUDocDestino,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
	}

	/**
	 * Método para generar la descripción de la unidad documental a partir de lo
	 * introducido durante la división de la fracción de serie
	 * 
	 * @param descripcionBI
	 * @param idUdocFS
	 * @param idUdocCF
	 */
	private void generateDescripcionUdoc(GestionDescripcionBI descripcionBI,
			String idUdocFS, String idUdocCF) {

		// Copiar los campos descriptivos de la unidad documental en división de
		// fracción de serie a las tablas de fichas de unidades en el cuadro
		descripcionBI.copiarCamposUDocREaUDocCF(idUdocFS, idUdocCF,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);

		// Eliminar los valores de las tablas de descripción de la unidad en la
		// relación
		descripcionBI.eliminarValoresCamposUDocRE(idUdocFS,
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_FS);
	}

	/**
	 * Obtiene la lista de rangos de una fracción de serie en división de
	 * fracción de serie
	 * 
	 * @param idUDoc
	 *            Identificador de la fracción de serie en la división de
	 *            fracción de serie
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal) {
		return _udocEnDivisionFSDbEntity.getRangosUDoc(idUDoc, idCampoInicial,
				idCampoFinal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionFraccionSerieBI#getNextUdocEnDivisionFs(java.lang.String
	 * , int)
	 */
	public UDocEnFraccionSerieVO getNextUdocEnDivisionFs(String idDivFs,
			int orden) {
		try {
			int nextOrden = _udocEnDivisionFSDbEntity
					.getNextOrdenUdocInDivisionFs(idDivFs, orden);
			return _udocEnDivisionFSDbEntity.getUdocInDivisionFsByOrden(
					idDivFs, nextOrden);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * common.bi.GestionFraccionSerieBI#getPrevUdocEnDivisionFs(java.lang.String
	 * , int)
	 */
	public UDocEnFraccionSerieVO getPrevUdocEnDivisionFs(String idDivFs,
			int orden) {
		try {
			int prevOrden = _udocEnDivisionFSDbEntity
					.getPrevOrdenUdocInDivisionFs(idDivFs, orden);
			return _udocEnDivisionFSDbEntity.getUdocInDivisionFsByOrden(
					idDivFs, prevOrden);
		} catch (Exception e) {
			return null;
		}
	}

}