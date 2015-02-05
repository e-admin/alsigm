package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderAsocSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSessionEx;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.manager.OficinaManager;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.AxSfToBaseRegistroVOMapper;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.ListOfScrRegisterInterToListOfInteresadoVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoTransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import gnu.trove.THashMap;

public class BaseRegistroVOBuilder {

	private static final String COMENTARIO_PROPERTY = "comentario";

	private static final Logger logger = Logger
			.getLogger(BaseRegistroVOBuilder.class);

	protected UnidadAdministrativaManager unidadAdministrativaManager;
	protected TipoAsuntoManager tipoAsuntoManager;
	protected OficinaManager oficinaManager;

	public UnidadAdministrativaManager getUnidadAdministrativaManager() {
		return unidadAdministrativaManager;
	}

	public void setUnidadAdministrativaManager(
			UnidadAdministrativaManager unidadAdministrativaManager) {
		this.unidadAdministrativaManager = unidadAdministrativaManager;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}

	public OficinaManager getOficinaManager() {
		return oficinaManager;
	}

	public void setOficinaManager(OficinaManager oficinaManager) {
		this.oficinaManager = oficinaManager;
	}

	/**
	 * Metodo que adapta la informacion que pasamos como parametro a un objeto
	 * {@link BaseRegistroVO}
	 *
	 * @param usuario
	 * @param id
	 *            - Objeto de tipo {@link IdentificadorRegistroVO}
	 * @param axSf
	 * @param camposAdicionales
	 *            - Listado de objetos {@link CampoAdicionalRegistroVO}, campos
	 *            adicionales de un registro
	 * @return {@link BaseRegistroVO}
	 */
	public BaseRegistroVO build(UsuarioVO usuario, AxSf axSf, String idLibro,
			BaseRegistroVO registerToPopulate) {
		Assert.notNull(registerToPopulate);

		BeanWrapper beanWrapper = new BeanWrapperImpl(registerToPopulate);
		boolean isOutputRegister = beanWrapper
				.isWritableProperty(COMENTARIO_PROPERTY);

		String idRegistro = String.valueOf(axSf
				.getAttributeValue(AxSf.FDRID_FIELD));

		try {
			String idAsunto = null;
			String numtransporte = null;
			String descripTransporte = null;

			// obtenemos los valores de los campos que son diferentes
			// dependiendo el libro
			if (axSf instanceof AxSfIn) {
				// para el libro de entrada
				idAsunto = axSf.getAttributeValueAsString(AxSf.FLD16_FIELD);
				numtransporte = axSf
						.getAttributeValueAsString(AxSf.FLD15_FIELD);
				descripTransporte = axSf
						.getAttributeValueAsString(AxSf.FLD14_FIELD);
			} else {
				// para el libro de salida
				idAsunto = axSf.getAttributeValueAsString(AxSf.FLD12_FIELD);
				numtransporte = axSf
						.getAttributeValueAsString(AxSf.FLD11_FIELD);
				descripTransporte = axSf
						.getAttributeValueAsString(AxSf.FLD10_FIELD);
			}

			// Campos Adicionales
			List camposAdicionales = populateCamposAdicionales(usuario, axSf,
					idLibro, beanWrapper, isOutputRegister);

			// Tipo Asunto
			TipoAsuntoVO tipoAsunto = null;
			if (StringUtils.isNotBlank(idAsunto)) {
				tipoAsunto = getTipoAsuntoManager().getTipoAsuntoById(usuario,
						idAsunto);
			}

			// Obtenemos el estado del registro
			String idEstado = axSf.getAttributeValueAsString(AxSf.FLD6_FIELD);
			EstadoRegistroEnum estado = EstadoRegistroEnum.getEnum(Integer
					.parseInt(idEstado));

			// Oficina de registro
			OficinaVO oficinaRegistro = null;
			String idOficina = axSf.getAttributeValueAsString(AxSf.FLD5_FIELD);
			if (StringUtils.isNotBlank(idOficina)) {
				oficinaRegistro = getOficinaManager().getOficinaById(
						usuario.getConfiguracionUsuario().getLocale(),
						idOficina);
			}

			// Unidad adminitrativa origen
			UnidadAdministrativaVO unidadAdministrativaOrigen = null;
			String idOrigen = axSf.getAttributeValueAsString(AxSf.FLD7_FIELD);
			if (StringUtils.isNotBlank(idOrigen)) {
				unidadAdministrativaOrigen = getUnidadAdministrativaManager()
						.findUnidadById(
								usuario.getConfiguracionUsuario().getLocale(),
								new Integer(idOrigen));
			}

			// Unidad adminitrativa destino
			UnidadAdministrativaVO unidadAdministrativaDestino = null;
			String idDestino = axSf.getAttributeValueAsString(AxSf.FLD8_FIELD);
			if (StringUtils.isNotBlank(idDestino)) {
				unidadAdministrativaDestino = getUnidadAdministrativaManager()
						.findUnidadById(
								usuario.getConfiguracionUsuario().getLocale(),
								new Integer(idDestino));
			}

			// Usuario del Registro
			UsuarioVO usuarioRegistro = new UsuarioVO();
			usuarioRegistro.setLoginName(axSf
					.getAttributeValueAsString(AxSf.FLD3_FIELD));

			// Transporte
			// TODO el transporte del registro debemos saber como obtenerlo
			// o insertar directamente lo que viene en el registro ya que puede
			// o no existir en BBDD
			TransporteVO transporte = new TransporteVO();
			transporte.setNumeroTransporte(numtransporte);

			TipoTransporteVO tipoTransporte = new TipoTransporteVO();
			tipoTransporte.setDescripcion(descripTransporte);

			transporte.setTipoTransporte(tipoTransporte);

			// Documentos del registro
			List docs = FolderFileSession.getBookFolderDocsWithPages(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(idLibro), Integer.valueOf(idRegistro).intValue(),
					usuario.getConfiguracionUsuario().getIdEntidad());

			List documentos = (new ListDocumentoRegistroVOBuilder()).build(
					docs, usuario.getConfiguracionUsuario().getIdEntidad());

			// Terceros
			// Devuelve un listado de objetos ScrRegisterInter
			List listScrRegisterInter = UtilsSessionEx.getScrRegisterInter(
					Integer.valueOf(idLibro), Integer.valueOf(idRegistro)
							.intValue(), false, usuario
							.getConfiguracionUsuario().getIdEntidad());

			// adaptamos el listado de interesados
			List terceros = (new ListOfScrRegisterInterToListOfInteresadoVO())
					.map(listScrRegisterInter);

			// Interesado Principal
			InteresadoVO interesadoPrincipal = null;
			for (Iterator it = terceros.iterator(); it.hasNext();) {
				InteresadoVO interesado = (InteresadoVO) it.next();
				if (interesado.isPrincipal()) {
					interesadoPrincipal = interesado;
				}
			}

			// Registros Relacionados
			List listScrRegasoc = FolderAsocSession.getAsocRegFdr(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(idLibro), Integer.valueOf(idRegistro).intValue(),
					usuario.getConfiguracionUsuario().getIdEntidad());

			List registrosRelacionados = fromListScrRegasocToListBaseLibroVO(listScrRegasoc);

			// Adaptamos los datos del registro para obtener un BaseRegistroVO
			AxSfToBaseRegistroVOMapper axSfToBaseRegistroVOMapper = new AxSfToBaseRegistroVOMapper(
					usuario, getTipoAsuntoManager(),
					getUnidadAdministrativaManager(), idLibro, idRegistro);

			registerToPopulate = axSfToBaseRegistroVOMapper.map(axSf,
					oficinaRegistro, usuarioRegistro, estado,
					unidadAdministrativaOrigen, unidadAdministrativaDestino,
					tipoAsunto, transporte, terceros, interesadoPrincipal,
					documentos, registrosRelacionados, camposAdicionales,
					registerToPopulate);

		} catch (Exception e) {
			logger.error("Error al obtener la informacion del registro ["
					+ idRegistro + "]", e);
			throw new RegistroException(
					"Error al obtener la informacion del registro ["
							+ idRegistro + "] ", e);
		}

		return registerToPopulate;

	}

	private List populateCamposAdicionales(UsuarioVO usuario, AxSf axSf,
			String idLibro, BeanWrapper beanWrapper, boolean isOutputRegister)
			throws BookException, SessionException, ValidationException {
		List camposAdicionales = new ArrayList();
		CampoAdicionalRegistroVO campoAdicionalRegistroVO = null;

		// Se recupera la definición del libro para extraer los campos
		// adicionales
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(usuario
				.getConfiguracionUsuario().getSessionID(), Integer
				.valueOf(idLibro));

		// Obtenemos los campos EXTENDIDOS
		List extendedFields = UtilsSession.getExtendedFields(idocarchdet,
				(axSf instanceof AxSfIn) ? Keys.EREG_FDR_MATTER
						: Keys.SREG_FDR_MATTER);
		Iterator iterator = extendedFields.iterator();
		while (iterator.hasNext()) {
			Integer fieldId = (Integer) iterator.next();
			String fieldName = AxSf.FLD + fieldId;
			// obtenemos el campo extendido
			AxXf extendField = (AxXf) axSf.getExtendedFields().get(fieldId);

			String valueExtendField = null;
			if (extendField != null) {
				valueExtendField = extendField.getText();
			}

			if ((((fieldId.intValue() == AxSf.FLD18_FIELD_ID) && (axSf instanceof AxSfIn)) || ((fieldId
					.intValue() == AxSf.FLD14_FIELD_ID) && (axSf instanceof AxSfOut)))
					&& isOutputRegister) {
				// obtenemos el comentario
				beanWrapper.setPropertyValue(COMENTARIO_PROPERTY,
						valueExtendField);
			} else {
				campoAdicionalRegistroVO = new CampoAdicionalRegistroVO();
				campoAdicionalRegistroVO.setName(String.valueOf(fieldId));
				campoAdicionalRegistroVO.setValue(valueExtendField);
				camposAdicionales.add(campoAdicionalRegistroVO);
			}
		}

		// Obtenemos los campos Adicionales
		List additionalFields = UtilsSession.getAdditionalFields(idocarchdet,
				(axSf instanceof AxSfIn) ? Keys.EREG_FDR_MATTER
						: Keys.SREG_FDR_MATTER);
		iterator = additionalFields.iterator();
		while (iterator.hasNext()) {
			Integer fieldId = (Integer) iterator.next();
			String fieldName = AxSf.FLD + fieldId;

			campoAdicionalRegistroVO = new CampoAdicionalRegistroVO();
			campoAdicionalRegistroVO.setName(String.valueOf(fieldId));
			campoAdicionalRegistroVO.setValue(axSf
					.getAttributeValueAsString(fieldName.toLowerCase()));
			camposAdicionales.add(campoAdicionalRegistroVO);
		}

		return camposAdicionales;
	}

	private List getExtendedFields(UsuarioVO usuario, String idLibro, AxSf axSf) {
		List fields = new ArrayList();
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					usuario.getConfiguracionUsuario().getSessionID());

			THashMap bookInformation = (THashMap) cacheBag.get(Integer
					.valueOf(idLibro));
			if (!cacheBag.containsKey(Integer.valueOf(idLibro))) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

			int limit = (axSf instanceof AxSfIn) ? 18 : 14;

			Iterator iterator = fieldFormat.getFlddefs().keySet().iterator();
			while (iterator.hasNext()) {
				FFldDef fFldDef = (FFldDef) fieldFormat.getFlddefs().get(
						iterator.next());

				if (fFldDef.getId() > limit) {
					fields.add(fFldDef);
				}
			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Se ha producido un error al obtener los campos adicionales del registro [")
					.append(axSf.getAttributeValue(AxSf.FDRID_FIELD))
					.append("] perteneciente al libro [").append(idLibro)
					.append("]");
			logger.error(sb.toString(), e);

			throw new RegistroException(sb.toString(), e);
		}

		return fields;
	}

	/**
	 * Metodo que adapta un listada de objeto {@link ScrRegasoc} a un listado de
	 * objetos {@link BaseLibroVO}
	 *
	 * @param listScrRegasoc
	 *            - listado de objetos {@link ScrRegasoc}
	 * @return listado de {@link BaseLibroVO}
	 */
	protected List fromListScrRegasocToListBaseLibroVO(List listScrRegasoc) {
		List result = new ArrayList();
		ScrRegasoc scrRegAsoc = null;
		BaseLibroVO baseLibroVO = null;

		for (Iterator it = listScrRegasoc.iterator(); it.hasNext();) {
			scrRegAsoc = (ScrRegasoc) it.next();

			baseLibroVO = new BaseLibroVO();
			baseLibroVO.setId(Integer.toString(scrRegAsoc.getIdFdrprim()));
			baseLibroVO.setIdArchivador(Integer.toString(scrRegAsoc
					.getIdArchprim()));

			result.add(baseLibroVO);
		}

		return result;
	}
}
