package transferencias.model;

import ieci.core.base64.Base64Util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.db.IUDocRelacionDBEntity;
import transferencias.electronicas.common.SistemaTramitador;
import transferencias.electronicas.documentos.Almacenamiento;
import transferencias.electronicas.documentos.Binario;
import transferencias.electronicas.documentos.IRepositorio;
import transferencias.electronicas.documentos.Repositorio;
import transferencias.electronicas.documentos.Ubicacion;
import transferencias.electronicas.ficha.CampoDescriptor;
import transferencias.electronicas.ficha.CampoDocumentoElectronico;
import transferencias.electronicas.ficha.CampoFecha;
import transferencias.electronicas.ficha.CampoFilaTabla;
import transferencias.electronicas.ficha.CampoNumerico;
import transferencias.electronicas.ficha.CampoSistemaExterno;
import transferencias.electronicas.ficha.CampoTabla;
import transferencias.electronicas.ficha.CampoTexto;
import transferencias.electronicas.ficha.CamposFicha;
import transferencias.electronicas.ficha.ICampoFicha;
import transferencias.electronicas.serie.Productor;
import transferencias.electronicas.udoc.ContenidoUDocXML;
import transferencias.electronicas.udoc.IdentificacionUnidadDocumental;
import transferencias.electronicas.udoc.InformacionUnidadDocumentalElectronica;
import transferencias.vos.TransferenciaElectronicaInfo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.MultiEntityConstants;
import common.bi.GestionTransferenciasElectronicasBI;
import common.bi.ServiceBase;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.CheckedArchivoException;
import common.exceptions.TransferenciaElectronicaException;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.DateUtils;
import common.util.ListUtils;

import deposito.vos.DepositoElectronicoVO;
import descripcion.db.IFechaDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.ITextoCortoUdocREDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.exceptions.DescriptorDuplicadoException;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ICampoVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import docelectronicos.TipoObjeto;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.IRepositorioEcmVO;
import fondos.model.TipoProductor;

public class GestionTransferenciasElectronicasBIImpl extends ServiceBase
		implements GestionTransferenciasElectronicasBI {

	ITextoDBEntity _textoCortoUDocREDbEntity = null;
	ITextoDBEntity _textoLargoUDocREDbEntity = null;
	INumeroDBEntity _numeroUdocREDbEntity = null;
	IFechaDBEntity _fechaUDocREDbEntity = null;
	IReferenciaDBEntity _referenciaUDocREDbEntity = null;

	ITextoDBEntity _textoCortoDbEntity = null;
	ITextoDBEntity _textoLargoDbEntity = null;
	INumeroDBEntity _numeroDbEntity = null;
	IFechaDBEntity _fechaDbEntity = null;
	IReferenciaDBEntity _referenciaDbEntity = null;

	private IUDocRelacionDBEntity _udocRelacionDBEntity = null;

	private IDocDocumentoCFDBEntity _docDocumentoCFDBEntity = null;

	public GestionTransferenciasElectronicasBIImpl(
			ITextoCortoUdocREDBEntity textoCortoUDocREDBEntity,
			ITextoDBEntity textoLargoUDocREDBEntity,
			IFechaDBEntity fechaUDocREDBEntity,
			INumeroDBEntity numeroUDocREDBEntity,
			IReferenciaDBEntity referenciaUDocREDBEntity,
			ITextoDBEntity textoCortoDBEntity,
			ITextoDBEntity textoLargoDBEntity, IFechaDBEntity fechaDBEntity,
			INumeroDBEntity numeroDBEntity,
			IReferenciaDBEntity referenciaDBEntity,
			IDocDocumentoCFDBEntity docDocumentoCFDBEntity,
			IUDocRelacionDBEntity udocRelacionDBEntity

	) {
		this._textoCortoUDocREDbEntity = textoCortoUDocREDBEntity;
		this._textoLargoUDocREDbEntity = textoLargoUDocREDBEntity;
		this._numeroUdocREDbEntity = numeroUDocREDBEntity;
		this._fechaUDocREDbEntity = fechaUDocREDBEntity;
		this._referenciaUDocREDbEntity = referenciaUDocREDBEntity;

		this._textoCortoDbEntity = textoCortoDBEntity;
		this._textoLargoDbEntity = textoLargoDBEntity;
		this._numeroDbEntity = numeroDBEntity;
		this._fechaDbEntity = fechaDBEntity;
		this._referenciaDbEntity = referenciaDBEntity;
		this._docDocumentoCFDBEntity = docDocumentoCFDBEntity;

		this._udocRelacionDBEntity = udocRelacionDBEntity;
	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GestionTransferenciasElectronicasBIImpl.class);

	private TransferenciaElectronicaInfo info;

	public boolean transferirExpedienteElectronicoConDocumentos(
			TransferenciaElectronicaInfo transferenciaElectronicaInfo)
			throws TransferenciaElectronicaException {
		logger.info("Inicio transferirExpedienteElectronicoConDocumentos");

		this.info = transferenciaElectronicaInfo;
		completarInformacionTransferencia();

		iniciarTransaccion();

		transferenciaElectronicaInfo
				.setGestorOrganismos(getGestorOrganismos(getParams()));
		establecerRelacionEntregaElectronica();

		comprobarUnicidadExpediente();

		if (info.isInsertarFichaSerie()) {
			procesarCamposFicha(info.getSerieVO().getId(),
					ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO, info
							.getContenidoUDocXML().getInformacionSerie()
							.getCamposFicha(), null,
					getBreadCrumb("SERIE", "FICHA"));
		}

		getGestionRelacionesBI().addUDocElectronica(
				transferenciaElectronicaInfo);

		procesarCamposFicha(info.getIdUnidadDocumental(),
				ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE, info
						.getContenidoUDocXML()
						.getInfoUnidadDocumentalElectronica().getCamposFicha(),
				null, getBreadCrumb("UNIDAD_DOCUMENTAL_ELECTRONICA", "FICHA"));

		transferencias.vos.UnidadDocumentalVO udoc = getGestionRelacionesBI()
				.getUnidadDocumentalByRelacionAndId(
						info.getRelacionEntregaVO().getId(),
						info.getIdUnidadDocumental());

		try {

			Collection udocsRelacion = new ArrayList();
			udocsRelacion.add(udoc);

			getGestionRelacionesBI().validarRelacion(
					info.getRelacionEntregaVO(), info.getIdNivelDocumental(),
					false, udocsRelacion);
		} catch (ActionNotAllowedException e) {
			logger.error(e);
			throw new TransferenciaElectronicaException(
					e,
					TransferenciasElectronicasConstants.PARAMETRO_VALIDAR_RELACION);
		} catch (Exception e) {
			logger.error(e);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_VALIDANDO_RELACION_ENTREGA);
		}

		String idElementocf = udoc.getIdElementocf();

		info.setIdElementoCF(idElementocf);

		getGestionDocumentosElectronicosBI()
				.updateIdElementoCfDocumentosYClasificadores(
						info.getIdUnidadDocumental(), info.getIdElementoCF(),
						info.getIdsClasificadoresDocumentosCreados(),
						info.getIdsInternosDocumentos());

		commit();

		logger.info("Fin transferirExpedientesElectronicosConDocumentos");

		return true;
	}

	private String getBreadCrumb(String breadCrumb, String newBreadCrumb) {
		return new StringBuilder(breadCrumb).append(common.Constants.MAYOR)
				.append(newBreadCrumb).toString();
	}

	private Properties getParams() {
		Properties params = new Properties();

		String idEntidad = info.getAppUser().getEntity();
		if (StringUtils.isNotBlank(idEntidad)) {

			params.put(MultiEntityConstants.ENTITY_PARAM, idEntidad);
		}

		return params;
	}

	/**
	 * Obtiene la relación de entrega electrónica y si no existe la crea.
	 *
	 * @param transferenciaElectronicaInfo
	 * @return
	 * @throws TransferenciaElectronicaException
	 */
	private void establecerRelacionEntregaElectronica()
			throws TransferenciaElectronicaException {

		getGestionSeriesBI()
				.establecerSerieTransferenciaElectronica(info, true);

		getGestionPrevisionesBI().establecerPrevisionElectronica(info);

		getGestionPrevisionesBI().establecerDetallePrevisionElectronica(info);

		getGestionRelacionesBI().establecerRelacionEntregaElectronica(info);

	}

	private InfoOrgano getOrganoExternoByCodigo(String valor)
			throws TransferenciaElectronicaException {
		InfoOrgano infoOrgano = getOrganoSistemaExterno(
				TipoAtributo.CODIGO_ORGANO, valor);

		if (infoOrgano == null) {
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO,
					new Object[] { valor });
		}

		return infoOrgano;
	}

	private InfoOrgano getOrganoExternoById(String valor)
			throws TransferenciaElectronicaException {

		InfoOrgano infoOrgano = getOrganoSistemaExterno(
				TipoAtributo.IDENTIFICADOR_ORGANO, valor);

		if (infoOrgano == null) {
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO,
					new Object[] { valor });
		}

		return infoOrgano;
	}

	private InfoOrgano getOrganoSistemaExterno(short atributo, String valor)
			throws TransferenciaElectronicaException {
		try {
			InfoOrgano infoOrgano = getGestorOrganismos(getParams())
					.recuperarOrgano(atributo, valor);

			return infoOrgano;

		} catch (GestorOrganismosException e) {
			logger.error(e);
			throw new TransferenciaElectronicaException(e);
		} catch (NotAvailableException e) {
			logger.error(e);
			throw new TransferenciaElectronicaException(e);
		}
	}

	private GestorOrganismos getGestorOrganismos(Properties params)
			throws TransferenciaElectronicaException {
		try {
			return GestorOrganismosFactory.getConnectorById(info
					.getConfiguracionFondos().getIdSistGestorOrg(), params);
		} catch (GestorOrganismosException e) {
			throw new TransferenciaElectronicaException(e);
		}
	}

	/**
	 * Completa y valida la información de la transferencia electrónica
	 * (Identificadores de sistemas externos)
	 */
	private void completarInformacionTransferencia()
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio Completar Información de Trasferencia");
		}

		if (info != null) {
			validarParametrosEntrada();

			completarContenidoXML();
		} else {
			logger.error("TransferenciaElectronicaInfo es nula");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_OBJETO_INFO_TRANSFERENCIA)

			);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin Completar Información de Trasferencia");
		}
	}

	private void validarParametrosEntrada()
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio Validar Parámetros de Entrada");
		}


		//Validar el código de procedimiento
		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
		.getConfiguracionSistemaArchivo().getConfiguracionFondos()
		.getDelimitadorCodigoReferencia();

		if (Constants.hasForbidenChars(info.getCodigoProcedimiento(),
				delimitadorCodigoReferencia)) {

			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_VALOR_INCORRECTO,
					new Object[] {
							TransferenciasElectronicasConstants
									.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CODIGO_PROCEDIMIENTO),
							info.getCodigoProcedimiento()});
		}


		// Validar año
		String anio = info.getAnio();

		if (logger.isDebugEnabled()) {
			logger.debug("Año de la transferencia: " + anio);
		}

		if (StringUtils.isBlank(anio)) {
			logger.error("El año no puede ser nulo");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_ANIO_TRANSFERENCIA));
		}

		// Validar la información del sistema tramitador
		SistemaTramitador sistemaTramitador = info.getSistemaTramitador();
		if (sistemaTramitador != null) {

			String codigo = sistemaTramitador.getId();

			if (logger.isDebugEnabled()) {
				logger.debug("Código del sistema tramitador: " + codigo);
			}

			// Comprobar que el código no es nulo
			if (codigo != null) {

				// Comprobar la longitud del código
				int maxLenCodigo = getGestionRelacionesBI()
						.getLongitudCampoCodigoSistemaProductor();

				if (codigo.length() > maxLenCodigo) {

					logger.error("La longitud del parámetro código del sistema tramitador no puede tener más de "
							+ maxLenCodigo + " caracteres");
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_LONGITUD_INCORRECTA,
							TransferenciasElectronicasConstants
									.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CODIGO_SISTEMA_TRAMITADOR));
				}

				// Comprobar que el nombre no es nulo
				String nombre = sistemaTramitador.getNombre();
				if (nombre != null) {
					int maxLenNombre = getGestionRelacionesBI()
							.getLongitudCampoNombreSistemaProductor();

					// Comprobar la longitud del código
					if (nombre.length() > maxLenNombre) {
						logger.error("La longitud del parámetro nombre del sistema tramitador no puede tener más de "
								+ maxLenNombre + " caracteres.");
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_LONGITUD_INCORRECTA,
								TransferenciasElectronicasConstants
										.getParametro(TransferenciasElectronicasConstants.PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR));
					}
				} else {
					logger.error("El nombre del sistema tramitador es obligatorio");
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							TransferenciasElectronicasConstants
									.getParametro(TransferenciasElectronicasConstants.PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR));
				}
			} else {
				logger.error("El código del sistema tramitador es obligatorio");
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						TransferenciasElectronicasConstants
								.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CODIGO_SISTEMA_TRAMITADOR)

				);
			}
		} else {
			logger.error("No se ha definido el sistema tramitador");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_SISTEMA_TRAMITADOR));
		}

		// Comprobar el código de procedimiento
		String codigoProcedimiento = info.getCodigoProcedimiento();

		if (logger.isDebugEnabled()) {
			logger.debug("Código de procedimiento: " + codigoProcedimiento);
		}

		if (StringUtils.isBlank(codigoProcedimiento)) {

			logger.error("El parámetro código de procedimiento no puede ser nulo");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CODIGO_PROCEDIMIENTO));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin Validar Parámetros de Entrada");
		}

	}

	/**
	 * Valida y completa los datos del contenidoXML
	 *
	 * @param info
	 * @throws TransferenciaElectronicaException
	 */
	private void completarContenidoXML()
			throws TransferenciaElectronicaException {

		logger.info("Completando la información del contenido xml");

		ContenidoUDocXML contenidoXml = info.getContenidoUDocXML();
		if (contenidoXml != null) {

			InformacionUnidadDocumentalElectronica infoUdoc = contenidoXml
					.getInfoUnidadDocumentalElectronica();
			if (infoUdoc != null) {
				completarIdentificacionUdoc();
			} else {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						TransferenciasElectronicasConstants
								.getParametro(TransferenciasElectronicasConstants.PARAMETRO_INFO_UDOC));
			}
		} else {
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CONTENIDO_XML));
		}

		logger.info("Completada la información de expediente");
	}

	private void completarIdentificacionUdoc()
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Completando identificación de la unidad documental");
		}

		IdentificacionUnidadDocumental identificacionUdoc = info
				.getContenidoUDocXML().getInfoUnidadDocumentalElectronica()
				.getIdentificacionUnidadDocumental();
		if (identificacionUdoc != null) {

			Productor productorUdoc = identificacionUdoc.getProductor();

			// Obtener el sistema productor
			InfoOrgano organoProductor = getOrganoExterno(
					productorUdoc.getAtributo(), productorUdoc.getValor());

			if (organoProductor != null) {
				productorUdoc.setInfoOrgano(organoProductor);
			} else {
				logger.error("El productor de la unidad documental no existe");
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_NO_EXISTE_PRODUCTOR_UNIDAD_DOCUMENTAL);
			}

		} else {
			logger.error("La identificación de la unidad documental es obligatoria");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
					TransferenciasElectronicasConstants
							.getParametro(TransferenciasElectronicasConstants.PARAMETRO_IDENTIFICACION_UDOC));
		}
	}

	private InfoOrgano getOrganoExterno(short atributo, String valor)
			throws TransferenciaElectronicaException {

		if (atributo == TipoAtributo.IDENTIFICADOR_ORGANO) {
			return getOrganoExternoById(valor);
		} else {
			return getOrganoExternoByCodigo(valor);
		}
	}

	public void insertarCamposFicha(String idElemento, short tipoElemento,
			List<ICampoFicha> campos, String idTabla)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio Completar campos ficha");
		}

		if (campos != null) {

			for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
				ICampoFicha campoFicha = (ICampoFicha) iterator.next();

				if (campoFicha != null) {
					procesarCampoFicha(idElemento, tipoElemento, campoFicha,
							idTabla, 0 ,campoFicha.getBaseBreadCrumb());
				}
			}

		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No se han definido campos de ficha");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin completar campos ficha");
		}
	}

	/**
	 * Completa las filas de una tabla
	 *
	 * @param filas
	 * @throws TransferenciaElectronicaException
	 */
	private void procesarFilasTabla(String idElemento, short tipoElemento,
			CampoTabla campoTabla, String breadCrumb)
			throws TransferenciaElectronicaException {

		List<CampoFilaTabla> filas = campoTabla.getFilas();

		if (filas != null) {
			int posicion = 1;

			for (Iterator iterator = filas.iterator(); iterator.hasNext();) {
				CampoFilaTabla campoFilaTabla = (CampoFilaTabla) iterator
						.next();

				campoFilaTabla.setBaseBreadCrumb(breadCrumb);

				campoFilaTabla.setPosicion(posicion++);
				if (campoFilaTabla != null) {
					campoFilaTabla.setIdTabla(campoTabla.getCampoVO().getId());
					procesarCampoFicha(idElemento, tipoElemento,
							campoFilaTabla, campoFilaTabla.getIdTabla(),
							campoFilaTabla.getPosicion(),
							campoFilaTabla.getBaseBreadCrumb());
				}
			}
		}
	}

	private void procesarCampoFicha(String idElemento, short tipoElemento,
			ICampoFicha campoFicha, String idTabla, int posicion,
			String baseBreadCrumb) throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio Procesar Campo Ficha Etiqueta="
					+ campoFicha.getEtiqueta() + "  - IdTabla= " + idTabla);
			logger.debug("Valor: " + campoFicha.getValor());
		}

		if(posicion != 0){
			baseBreadCrumb+= " " + posicion;
		}

		campoFicha.setBaseBreadCrumb(baseBreadCrumb);

		if (campoFicha instanceof CampoTexto
				|| campoFicha instanceof CampoFecha
				|| campoFicha instanceof CampoNumerico
				|| campoFicha instanceof CampoDescriptor
				|| campoFicha instanceof CampoSistemaExterno
				|| campoFicha instanceof CampoDocumentoElectronico) {
			if (logger.isDebugEnabled()) {
				logger.debug("Campo de tipo Simple " + campoFicha.getEtiqueta());
			}

			ICampoVO campoVO = getCampoDatoByEtiqueta(campoFicha.getEtiqueta(),
					idTabla);

			campoFicha.setCampoVO(campoVO);

			if (StringUtils.isBlank(campoFicha.getEtiqueta())) {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						"etiqueta - " + campoFicha.toString());
			}

			// Procesar especifificos
			if (campoFicha instanceof CampoDescriptor) {
				CampoDescriptor campoDescriptor = (CampoDescriptor) campoFicha;

				// Comprobar que existe la lista descriptora
				ListaDescrVO lista = getGestionDescripcionBI().getListaDescriptora(
						campoDescriptor.getIdLista());

				if(lista == null){
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_VALOR_INCORRECTO,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_ID_LISTA),
									campoDescriptor.getIdLista()});
				}
			}

			procesarCampo(idElemento, campoFicha, tipoElemento, posicion);

		} else if (campoFicha instanceof CampoTabla) {
			if (logger.isDebugEnabled()) {
				logger.debug("Campo Tabla " + campoFicha.getEtiqueta());
			}
			ICampoVO campoVO = getCampoTablaByEtiqueta(campoFicha.getEtiqueta());
			campoFicha.setCampoVO(campoVO);

			CampoTabla campoTabla = (CampoTabla) campoFicha;

			// Procesar las filas
			procesarFilasTabla(idElemento, tipoElemento, campoTabla,
					campoFicha.getFullBreadCrumb());

		} else if (campoFicha instanceof CampoFilaTabla) {
			ICampoVO campoVO = getCampoTablaByEtiquetaFila(campoFicha
					.getEtiqueta());
			campoFicha.setCampoVO(campoVO);

			CampoFilaTabla campoFilaTabla = (CampoFilaTabla) campoFicha;

			if (campoFilaTabla != null) {
				procesarElementos(idElemento, tipoElemento,
						campoFilaTabla.getCampos(), idTabla,
						campoFilaTabla.getPosicion(),
						campoFicha.getFullBreadCrumb()
						);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin Procesar Campo Ficha Etiqueta="
					+ campoFicha.toString());
		}
	}

	private void procesarCampo(String idElemento, ICampoFicha campoFicha,
			int tipoElemento, int orden)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.info("Procesando campo " + campoFicha.toString());
		}

		String valor = campoFicha.getValor();

		if (campoFicha != null) {

			ICampoVO campoDato = campoFicha.getCampoVO();
			String idCampo = campoDato.getId();
			short tipo = (short) campoDato.getTipo();

			if (campoFicha instanceof CampoTexto
					|| campoFicha instanceof CampoDocumentoElectronico) {

				CampoTextoVO campoTextoVO = new CampoTextoVO(idElemento,
						idCampo, orden, tipo, valor, tipoElemento);

				if (campoFicha instanceof CampoDocumentoElectronico) {
					procesarDocumento(idElemento, info.getIdRepEcmUdoc(),
							(CampoDocumentoElectronico) campoFicha);
				} else {
					if (StringUtils.isBlank(campoFicha.getValor())) {
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
								new Object[] {
										TransferenciasElectronicasConstants
												.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
										campoFicha.toString() });
					}

				}

				campoTextoVO.setValor(campoFicha.getValor());

				if (campoDato.isTextoCorto()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Insertando el campo texto corto con valor:"
								+ valor + " -  idCampo: " + idCampo);
					}

					if (orden == 0) {
						orden = getTextoDBEntity(tipoElemento, tipo)
								.getMaxOrden(idElemento, idCampo);
						orden++;
					}

					campoTextoVO.setOrden(orden);
					getTextoDBEntity(tipoElemento, tipo).insertValue(
							campoTextoVO);
				} else if (campoDato.isTextoLargo()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Insertando el campo texto largo con valor:"
								+ valor + " -  idCampo: " + idCampo);
					}

					if (StringUtils.isBlank(campoFicha.getValor())) {
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
								new Object[] {
										TransferenciasElectronicasConstants
												.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
										campoFicha.toString() });
					}

					if (orden == 0) {
						orden = getTextoDBEntity(tipoElemento, tipo)
								.getMaxOrden(idElemento, idCampo);
						orden++;
					}
					campoTextoVO.setOrden(orden);

					getTextoDBEntity(tipoElemento, tipo).insertValue(
							campoTextoVO);
				}
			} else if (campoFicha instanceof CampoFecha) {
				CampoFecha campoFecha = (CampoFecha) campoFicha;

				if (StringUtils.isBlank(campoFecha.getValor())) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
									campoFecha.toString() });
				}

				if (StringUtils.isBlank(campoFecha.getFormato())) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_FORMATO_FECHA),
									campoFecha.toString() });
				}

				if (StringUtils.isBlank(campoFecha.getSeparador())) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_SEPARADOR_FECHA),
									campoFecha.toString() });
				}

				CustomDate cd = new CustomDate(campoFecha.getFormato(),
						campoFecha.getAnio(), campoFecha.getMes(),campoFecha.getDia(),
						campoFecha.getHoras(),campoFecha.getMinutos(), campoFecha.getSegundos(),
						campoFecha.getSiglo(),
						campoFecha.getSeparador(), campoFecha.getCalificador());

				if(!cd.validate()){
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_FECHA_INCORRECTA,
							campoFecha.toString()
					);
				}


				CampoFechaVO campoFechaVO = new CampoFechaVO(idElemento,
						idCampo, orden, valor, cd.getMinDate(),
						cd.getMaxDate(), campoFecha.getFormato(),
						campoFecha.getSeparador(), campoFecha.getCalificador(),
						tipoElemento);

				if (orden == 0) {
					orden = getFechaDBEntity(tipoElemento).getMaxOrden(
							idElemento, idCampo);
					orden++;
				}
				campoFechaVO.setOrden(orden);
				getFechaDBEntity(tipoElemento).insertValue(campoFechaVO);

			} else if (campoFicha instanceof CampoNumerico) {
				CampoNumerico campoNumerico = (CampoNumerico) campoFicha;

				if (StringUtils.isBlank(campoFicha.getValor())) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
									campoFicha.toString() });
				}

				if (orden == 0) {
					orden = getNumeroDBEntity(tipoElemento).getMaxOrden(
							idElemento, idCampo);
					orden++;
				}
				double valorD = Double.valueOf(valor);

				int tipoMedida = 0;

				if (campoNumerico.getTipoMedida() != null) {
					tipoMedida = Integer.valueOf(tipoMedida);
				}

				CampoNumericoVO campoNumericoVO = new CampoNumericoVO(
						idElemento, idCampo, orden, valorD, tipoMedida,
						campoNumerico.getUnidadMedida(), tipoElemento);

				getNumeroDBEntity(tipoElemento).insertValue(campoNumericoVO);
			} else if (campoFicha instanceof CampoDescriptor
					|| campoFicha instanceof CampoSistemaExterno

			) {

				CampoDescriptor campoDescriptor;

				String idLista = null;
				String nombre = null;

				if (StringUtils.isBlank(campoFicha.getValor())) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
							new Object[] {
									TransferenciasElectronicasConstants
											.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
									campoFicha.toString() });
				}

				if (campoFicha instanceof CampoSistemaExterno) {

					CampoSistemaExterno campoSistemaExterno = (CampoSistemaExterno) campoFicha;

					if (campoSistemaExterno.isTipoSistemaOrganizacion()) {

						if (StringUtils.isBlank(campoSistemaExterno
								.getTipoAtributo())) {
							throw new TransferenciaElectronicaException(
									TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
									new Object[] {
											TransferenciasElectronicasConstants
													.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
											campoFicha.toString() });
						}

						InfoOrgano infoOrgano = getOrganoExterno(
								campoSistemaExterno.getAtributo(), valor);

						idLista = info
								.getConfiguracionSistemaArchivo()
								.getConfiguracionGeneral()
								.getListaDescriptoresEntidad(
										TipoProductor.ORGANO.getIdentificador())
								.getId();
						nombre = infoOrgano.getNombre();
					} else {
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_TIPO_SISTEMA_EXTERNO_NO_SOPORTADO,
								new Object[] { campoSistemaExterno.toString() });
					}

				} else {
					campoDescriptor = (CampoDescriptor) campoFicha;
					idLista = campoDescriptor.getIdLista();
					nombre = campoDescriptor.getValor();

					if (StringUtils.isBlank(idLista)) {
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
								new Object[] {
										TransferenciasElectronicasConstants
												.getParametro(TransferenciasElectronicasConstants.PARAMETRO_VALOR),
										campoFicha.toString() });
					}
				}

				if(orden == 0){
					orden = getReferenciaDBEntity(tipoElemento).getMaxOrden(
							idElemento, idCampo);
					orden++;
				}

				DescriptorVO descriptor = getGestionDescripcionBI()
						.getDescriptorByNombreYIdLista(nombre, idLista);

				// Si no existe el descriptor se crea
				if (descriptor == null) {
					logger.info("El descriptor "
							+ campoFicha.toString()
							+ " no existe, se va proceder a crearlo en el sistema");

					try {
						descriptor = getGestionDescripcionBI().addDescriptor(
								idLista, nombre);
					} catch (DescriptorDuplicadoException e) {
						logger.error(e);
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_AL_CREAR_CAMPO_DESCRIPTOR,
								campoFicha.toString());
					} catch (CheckedArchivoException e) {
						logger.error(e);
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_AL_CREAR_CAMPO_DESCRIPTOR,
								campoFicha.toString());
					}
				}

				CampoReferenciaVO campoReferenciaVO = new CampoReferenciaVO(
						idElemento, idCampo, orden,
						CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR,
						descriptor.getId(), tipoElemento);

				getReferenciaDBEntity(tipoElemento).insertValue(
						campoReferenciaVO);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.info("Fin Procesado campo " + campoFicha.toString());
		}

	}

	private ICampoVO getCampoDatoByEtiqueta(String etiqueta, String idTabla)
			throws TransferenciaElectronicaException {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio obtener campo dato con etiqueta " + etiqueta);
		}

		if (StringUtils.isBlank(etiqueta)) {
			logger.error("Campo con etiqueta vacía ");
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_CAMPO_DATO_SIN_ETIQUETA);
		}

		CampoDatoVO campoDato = getGestionDescripcionBI()
				.getCampoDatoByEtiqueta(etiqueta, idTabla);

		if (campoDato == null) {
			logger.error("No existe el campo dato con etiqueta " + etiqueta);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_CAMPO_DATO_INEXISTENTE,
					etiqueta);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin obtener campo dato con etiqueta " + etiqueta);
		}

		return campoDato;
	}

	private ICampoVO getCampoTablaByEtiqueta(String etiqueta)
			throws TransferenciaElectronicaException {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio obtener campo tabla con etiqueta " + etiqueta);
		}
		CampoTablaVO campoTablaVO = getGestionDescripcionBI()
				.getCampoTablaByEtiqueta(etiqueta);
		if (campoTablaVO == null) {
			logger.error("No existe el campo tabla con etiquieta " + etiqueta);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_CAMPO_TABLA_INEXISTENTE,
					etiqueta);
		}
		logger.debug("Fin obtener campo tabla con etiqueta " + etiqueta);

		return campoTablaVO;
	}

	private ICampoVO getCampoTablaByEtiquetaFila(String etiqueta)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio obtener tabla por etiqueta fila");
		}

		CampoTablaVO campoTablaVO = getGestionDescripcionBI()
				.getCampoTablaByEtiquetaFila(etiqueta);
		if (campoTablaVO == null) {
			logger.error("No existe el campo fila tabla con etiqueta "
					+ etiqueta);
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_CAMPO_FILA_TABLA_INEXISTENTE,
					etiqueta);
		}

		logger.debug("Inicio obtener tabla por etiqueta fila");
		return campoTablaVO;
	}

	private ITextoDBEntity getTextoDBEntity(int tipoElemento, short tipo) {
		if (ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE == tipoElemento) {
			if (ValorCampoGenericoVO.TIPO_TEXTO_CORTO == tipo) {
				return _textoCortoUDocREDbEntity;
			} else if (ValorCampoGenericoVO.TIPO_TEXTO_LARGO == tipo) {
				return _textoLargoUDocREDbEntity;
			}
		} else if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoElemento) {
			if (ValorCampoGenericoVO.TIPO_TEXTO_CORTO == tipo) {
				return _textoCortoDbEntity;
			} else if (ValorCampoGenericoVO.TIPO_TEXTO_LARGO == tipo) {
				return _textoLargoDbEntity;
			}
		}
		return null;
	}

	private INumeroDBEntity getNumeroDBEntity(int tipoElemento) {
		if (ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE == tipoElemento) {
			return _numeroUdocREDbEntity;
		} else if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoElemento) {
			return _numeroDbEntity;
		}
		return null;
	}

	private IFechaDBEntity getFechaDBEntity(int tipoElemento) {
		if (ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE == tipoElemento) {
			return _fechaUDocREDbEntity;
		} else if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoElemento) {
			return _fechaDbEntity;
		}
		return null;
	}

	private IReferenciaDBEntity getReferenciaDBEntity(int tipoElemento) {
		if (ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_RE == tipoElemento) {
			return _referenciaUDocREDbEntity;
		} else if (ValorCampoGenericoVO.TIPO_ELEMENTO_INDEFINIDO == tipoElemento) {
			return _referenciaDbEntity;
		}
		return null;
	}

	/**
	 * Procesa los campos de una ficha
	 *
	 * @param idElemento
	 *            Identificador del elemento (IdElementoCF o IdUnidadDoc)
	 * @param tipoElemento
	 *            Tipo de Elemento {@link ValorCampoGenericoVOBase}
	 * @param camposFicha
	 * @throws TransferenciaElectronicaException
	 */
	private void procesarCamposFicha(String idElemento, short tipoElemento,
			CamposFicha camposFicha, String idTabla, String breadCrumbPadre)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio procesado de los campos ficha del elemento "
					+ idElemento + " tipo = " + tipoElemento);
		}

		if (camposFicha != null) {

			List<ICampoFicha> listaCampos = camposFicha.getCamposFicha();

			procesarElementos(idElemento, tipoElemento, listaCampos, idTabla, 0,
					breadCrumbPadre);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin procesado de los campos ficha del elemento "
					+ idElemento + " tipo = " + tipoElemento);
		}
	}

	private void procesarElementos(String idElemento, short tipoElemento,
			List<ICampoFicha> listaCampos, String idTabla, int posicion,
			String breadCrumbPadre)
			throws TransferenciaElectronicaException {
		if (ListUtils.isNotEmpty(listaCampos)) {

			for (int i = 0; i < listaCampos.size(); i++) {

				ICampoFicha campoFicha = (ICampoFicha) listaCampos.get(i);

				campoFicha.setPosicion(i);
				procesarCampoFicha(idElemento, tipoElemento, campoFicha,
						idTabla, posicion, breadCrumbPadre);

			}

		}
	}

	private String procesarDocumento(String idElementoCF,
			String idRepositorioEcmDefecto,
			CampoDocumentoElectronico campoDocumentoElectronico)
			throws TransferenciaElectronicaException {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio procesar documento"
					+ campoDocumentoElectronico);
		}

		comprobarUbicacion(campoDocumentoElectronico);
		comprobarAlmacenamiento(idRepositorioEcmDefecto,
				campoDocumentoElectronico);

		byte[] contenido = null;

		// Procesar el origen del fichero

		Ubicacion ubicacion = campoDocumentoElectronico.getUbicacion();

		String identificadorExterno = null;

		String idRepositorio = null;

		if (ubicacion.isBinario()) {

			if (logger.isDebugEnabled()) {
				logger.debug("Fichero binario en base64");
			}

			Binario binario = ubicacion.getBinario();

			if (binario != null) {
				try {
					contenido = Base64Util.decode(binario
							.getContenido().trim());
				} catch (Exception e) {
					logger.error("Error al decodificar el contenido del fichero en base64");
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_DOCUMENTO_BINARIO_INVALIDO,
							campoDocumentoElectronico.toString());
				}
			} else {
				logger.error("No se ha definido el contenido binario del documento");
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_DOCUMENTOS_ELECTRONICOS_BINARIO_INEXISTENTE,
						campoDocumentoElectronico.toString());
			}
		} else if (ubicacion.isRepositorio()) {
			if (logger.isDebugEnabled()) {
				logger.debug("La ubicación del fichero es un repositorio");
			}

			IRepositorio repositorio = ubicacion.getRepositorio();

			idRepositorio = repositorio.getIdRepositorio();
			identificadorExterno = repositorio.getLocalizador();

			try {
				if(ubicacion.isDepositoElectronico()){
					contenido = getGestionDocumentosElectronicosBI().getFile(
							null, ubicacion.getRepositorio().getIdRepositorio(), identificadorExterno);
				}
				else{
					contenido = getGestionDocumentosElectronicosBI().getFile(
							idRepositorio, null, identificadorExterno);
				}

			} catch (Exception e) {
				logger.error("Error al obtener el fichero del campo documento"
						+ campoDocumentoElectronico.toString());
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN,
						new Object[] { campoDocumentoElectronico.toString() }

				);
			}

			if(contenido == null){
				logger.error("Error al obtener el fichero del campo documento"
						+ campoDocumentoElectronico.toString());
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN,
						new Object[] { campoDocumentoElectronico.toString() }

				);
			}
		}

		// Procesar el almacenamiento del fichero
		Almacenamiento almacenamiento = campoDocumentoElectronico
				.getAlmacenamiento();

		String idClasificador = null;
		if (StringUtils.isNotBlank(almacenamiento.getClasificador())) {

			if (logger.isDebugEnabled()) {
				logger.info("Se ha definido un clasificador de documento");
			}

			idClasificador = getGestionDocumentosElectronicosBI()
					.establecerClasificador(TipoObjeto.ELEMENTO_CF,
							idElementoCF, almacenamiento.getClasificador(),
							info);
		}

		DocDocumentoExtVO docDocumentoExtVO = new DocDocumentoExtVO();

		docDocumentoExtVO.setContenido(contenido);
		docDocumentoExtVO.setNombre(almacenamiento.getNombre());
		docDocumentoExtVO.setIdClfPadre(idClasificador);
		docDocumentoExtVO.setEstado(almacenamiento.getEstado());
		docDocumentoExtVO.setDescripcion(almacenamiento.getDescripcion());
		docDocumentoExtVO.setIdObjeto(idElementoCF);
		docDocumentoExtVO.setTipoObjeto(TipoObjeto.ELEMENTO_CF);
		docDocumentoExtVO.setNombreOrgFich(almacenamiento.getNombre());
		docDocumentoExtVO.setExtFich(almacenamiento.getExtension());
		docDocumentoExtVO.setTamanoFich(contenido.length);

		DocDocumentoVO documentoInterno = null;


		if (almacenamiento.isRepositorio()) {

			if (logger.isDebugEnabled()) {
				logger.debug("El documento se va almacenar un repositorio");
			}

			Repositorio repositorio = almacenamiento.getRepositorio();
			idRepositorio = repositorio.getIdRepositorio();
			docDocumentoExtVO.setIdRepEcm(idRepositorio);
			try {
				documentoInterno = almacenarFichero(docDocumentoExtVO, true);
			} catch (Exception e) {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_ALMACENAR_FICHERO_REPOSITORIO,
						docDocumentoExtVO.toString(), e);
			}
		} else {

			if (logger.isDebugEnabled()) {
				logger.debug("El documento no se almacena en un repositorio");
			}

			if (ubicacion.isRepositorio()) {
				try {

					if (ubicacion.isDepositoElectronico())  {
						docDocumentoExtVO
								.setIdExtDeposito(ubicacion.getRepositorio().getIdRepositorio());
						//docDocumentoExtVO.setTamanoFich(-1);
						docDocumentoExtVO.setIdRepEcm(null);
						docDocumentoExtVO.setIdFich(ubicacion.getRepositorio().getLocalizador());
					}
					else{
						docDocumentoExtVO.setIdRepEcm(ubicacion.getRepositorio()
								.getIdRepositorio());
						docDocumentoExtVO.setIdFich(ubicacion.getRepositorio()
								.getLocalizador());
					}

					documentoInterno = almacenarFichero(docDocumentoExtVO,
							false);
				} catch (Exception e) {
					logger.error("No se ha definido el repositorio");
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_ALMACENAR_FICHERO_REPOSITORIO,
							docDocumentoExtVO.toString(), e);
				}
			} else {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_UBICACION_INCORRECTA_DOCUMENTO_ELECTRONICO,
						campoDocumentoElectronico.toString());
			}
		}

		if (StringUtils.isBlank(documentoInterno.getId())) {
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_OBTENER_IDENTIFICADOR_INTERNO_FICHERO,
					new Object[] { campoDocumentoElectronico.toString() });
		}

		campoDocumentoElectronico.setValor(documentoInterno.getId());

		info.addIdInternoDocumento(documentoInterno.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("Fin procesar documento. Id Interno = "
					+ docDocumentoExtVO.getId());
		}

		info.addIdInternoDocumento(documentoInterno.getId());

		return documentoInterno.getId();
	}

	private DocDocumentoVO almacenarFichero(DocDocumentoExtVO documentoExt,
			boolean almacenarFichero) {
		// Inserción de la información del documento

		if (almacenarFichero) {
			// Upload del fichero
			documentoExt.setIdFich(getGestionDocumentosElectronicosBI()
					.storeFile(documentoExt));
		}

		DocDocumentoVO documento = _docDocumentoCFDBEntity
				.insertDocumento(documentoExt);

		return documento;

	}

	private void comprobarRepositorio(
			CampoDocumentoElectronico campoDocumentoElectronico,
			IRepositorio repositorio) throws TransferenciaElectronicaException {
		if (repositorio != null) {

			if (repositorio.isDepositoElectronico()) {
				DepositoElectronicoVO depositoElectronicoVO = getGestorEstructuraDepositoBI()
						.getDepositoElectronicoByIdExt(
								repositorio.getIdRepositorio());

				if (depositoElectronicoVO == null) {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_DEPOSITO_ELECTRONICO_INEXISTENTE,
							new Object[] { repositorio,
									campoDocumentoElectronico });
				}
			} else {
				comprobarRepositorioExterno(campoDocumentoElectronico,
						(Repositorio) repositorio);
			}
		}
	}

	private void comprobarRepositorioExterno(
			CampoDocumentoElectronico campoDocumentoElectronico,
			Repositorio repositorio) throws TransferenciaElectronicaException {
		IRepositorioEcmVO repositorioEcmVO = getGestionDocumentosElectronicosBI()
				.getRepositorioEcm(repositorio.getIdRepositorio());

		if (repositorioEcmVO == null) {
			throw new TransferenciaElectronicaException(
					TransferenciasElectronicasConstants.ERROR_REPOSITORIO_NO_CONFIGURADO,
					new Object[] { repositorio, campoDocumentoElectronico });
		}
	}

	/**
	 * Comprueba que la definción del documento sea correcta<br/>
	 * ORIGEN: BINARIO -> DESTINO: REPECM ORIGEN: REPECM -> DESTINO: REPECM
	 * ORIGEN: REPECM -> DESTINO: SIN DESTINO ORIGEN: RDE -> DESTINO: SIN
	 * DESTINO
	 *
	 * @param campoDocumentoElectronico
	 * @throws TransferenciaElectronicaException
	 */
	private DocDocumentoExtVO getDocDocumentoExtVO(
			String idRepositorioEcmDefecto,
			CampoDocumentoElectronico campoDocumentoElectronico)
			throws TransferenciaElectronicaException {
		DocDocumentoExtVO doc = null;

		if (campoDocumentoElectronico != null) {
			comprobarUbicacion(campoDocumentoElectronico);
			comprobarAlmacenamiento(idRepositorioEcmDefecto,
					campoDocumentoElectronico);
		}
		return doc;
	}

	/**
	 * Comprueba que la información del documento electrónico sea correcta
	 *
	 * @param campoDocumentoElectronico
	 * @throws TransferenciaElectronicaException
	 */
	private void comprobarUbicacion(
			CampoDocumentoElectronico campoDocumentoElectronico)
			throws TransferenciaElectronicaException {

		Ubicacion ubicacion = campoDocumentoElectronico.getUbicacion();

		if (ubicacion.isRepositorio()) {

			IRepositorio repositorioOrigen = ubicacion.getRepositorio();
			if (repositorioOrigen != null) {
				comprobarRepositorio(campoDocumentoElectronico,
						repositorioOrigen);
			} else {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						new Object[] {
								TransferenciasElectronicasConstants.PARAMETRO_REPOSITORIO_UBICACION,
								campoDocumentoElectronico });
			}
		}
	}

	/**
	 * Comprueba el almacenamiento
	 *
	 * @param idRepositorioECMDefecto
	 * @param campoDocumentoElectronico
	 * @throws TransferenciaElectronicaException
	 */
	private void comprobarAlmacenamiento(String idRepositorioEcmDefecto,
			CampoDocumentoElectronico campoDocumentoElectronico)
			throws TransferenciaElectronicaException {
		Almacenamiento almacenamiento = campoDocumentoElectronico
				.getAlmacenamiento();

		if (campoDocumentoElectronico.getUbicacion().isBinario()
				|| almacenamiento.isRepositorio()) {

			Repositorio repositorioDestino = almacenamiento.getRepositorio();

			if (repositorioDestino == null
					|| StringUtils.isBlank(repositorioDestino
							.getIdRepositorio())) {
				if (logger.isDebugEnabled()) {
					logger.debug("No se ha definido repositorio de unidad documental, se utiliza el definido en la serie");
				}
				if (idRepositorioEcmDefecto != null) {
					repositorioDestino = new Repositorio();
					repositorioDestino
							.setIdRepositorio(idRepositorioEcmDefecto);

					almacenamiento.setRepositorio(repositorioDestino);

				} else {
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_SIN_REPOSITORIO_ALMACENAMIENTO_DEFECTO,
							new Object[] { repositorioDestino,
									campoDocumentoElectronico });
				}
			}

			if (StringUtils.isBlank(repositorioDestino.getIdRepositorio())) {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						new Object[] {
								TransferenciasElectronicasConstants.PARAMETRO_REPOSITORIO_ALMACENAMIENTO,
								campoDocumentoElectronico });
			}

			comprobarRepositorioExterno(campoDocumentoElectronico,
					(Repositorio) repositorioDestino);

		}

	}

	private void comprobarUnicidadExpediente()
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio Comprobar unicidad del expediente");
		}

		if (info.getVerificarUnicidad() != 0) {
			IdentificacionUnidadDocumental udoc = info.getContenidoUDocXML()
					.getInfoUnidadDocumentalElectronica()
					.getIdentificacionUnidadDocumental();

			String numeroExpediente = udoc.getNumExp();
			String titulo = udoc.getTitulo();

			Date fechaInicio;
			try {
				fechaInicio = udoc.getFechaInicio().getDate();
			} catch (ParseException e1) {
				logger.error(e1);
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_FECHA_INCORRECTA,
						TransferenciasElectronicasConstants
								.getParametro(TransferenciasElectronicasConstants.PARAMETRO_FECHA_INICIAL));

			}
			Date fechaFin;
			try {
				fechaFin = udoc.getFechaFin().getDate();
			} catch (ParseException e1) {
				logger.error(e1);
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_FECHA_INCORRECTA,
						TransferenciasElectronicasConstants
								.getParametro(TransferenciasElectronicasConstants.PARAMETRO_FECHA_FINAL));
			}

			int tipoVerificacion = info.getVerificarUnicidad();

			transferencias.vos.UnidadDocumentalVO udocFiltro = new transferencias.vos.UnidadDocumentalVO(
					info.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaInicial(), info
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaFinal(), ConfigConstants
							.getInstance().getSeparadorDefectoFechasRelacion());

			udocFiltro.setCodSistProductor(info.getCodSistemaProductor());

			if(tipoVerificacion > 10){
				udocFiltro.setIdRelEntrega(info.getRelacionEntregaVO().getId());
				tipoVerificacion = tipoVerificacion - 10;
			}



			String[] parametros = null;
			String[] valores = null;

			switch (tipoVerificacion) {
			case 1:
				udocFiltro.setNumeroExpediente(numeroExpediente);

				parametros = new String[] { TransferenciasElectronicasConstants.PARAMETRO_NUMERO_EXPEDIENTE };
				valores = new String[] { numeroExpediente };

				break;
			case 2:
				udocFiltro.setNumeroExpediente(numeroExpediente);
				udocFiltro.setAsunto(titulo);

				parametros = new String[] {
						TransferenciasElectronicasConstants.PARAMETRO_NUMERO_EXPEDIENTE,
						TransferenciasElectronicasConstants.PARAMETRO_TITULO };
				valores = new String[] { numeroExpediente, titulo };

				break;
			case 3:
				udocFiltro.setNumeroExpediente(numeroExpediente);
				udocFiltro.setAsunto(titulo);
				udocFiltro.setFechaInicio(fechaInicio);

				parametros = new String[] {
						TransferenciasElectronicasConstants.PARAMETRO_NUMERO_EXPEDIENTE,
						TransferenciasElectronicasConstants.PARAMETRO_TITULO,
						TransferenciasElectronicasConstants.PARAMETRO_FECHA_INICIAL };
				valores = new String[] { numeroExpediente, titulo,
						DateUtils.formatDate(fechaInicio) };

				break;
			case 4:
				udocFiltro.setNumeroExpediente(numeroExpediente);
				udocFiltro.setAsunto(titulo);
				udocFiltro.setFechaInicio(fechaInicio);
				udocFiltro.setFechaFin(fechaFin);

				parametros = new String[] {
						TransferenciasElectronicasConstants.PARAMETRO_NUMERO_EXPEDIENTE,
						TransferenciasElectronicasConstants.PARAMETRO_TITULO,
						TransferenciasElectronicasConstants.PARAMETRO_FECHA_INICIAL,
						TransferenciasElectronicasConstants.PARAMETRO_FECHA_FINAL };
				valores = new String[] { numeroExpediente, titulo,
						DateUtils.formatDate(fechaInicio),
						DateUtils.formatDate(fechaFin) };

				break;

			default:
				logger.error("La opcion de nivel de comprobación la unicidad de la unidad no es correcta");
				break;
			}

			transferencias.vos.UnidadDocumentalVO udocBD = _udocRelacionDBEntity
					.getUnidadDocumental(udocFiltro);

			if (udocBD != null) {

				if(StringUtils.isNotEmpty(udocFiltro.getIdRelEntrega())) {
					parametros = (String[]) ArrayUtils.add(parametros, TransferenciasElectronicasConstants.PARAMETRO_RELACION_ENTREGA);
					valores = (String[]) ArrayUtils.add(valores, info.getRelacionEntregaVO().getCodigoRelacion());
				}

				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_UNIDAD_DOCUMENTAL_ESTA_DUPLICADA, parametros,valores);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Fin Comprobar unicidad del expediente");
		}

	}

}
