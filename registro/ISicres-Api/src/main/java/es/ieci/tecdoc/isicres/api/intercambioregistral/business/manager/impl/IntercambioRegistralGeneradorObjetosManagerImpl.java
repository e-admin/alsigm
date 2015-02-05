package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.IRepositoryManager;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.ConfiguracionIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.InfoRegistroDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralExceptionCodes;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralGeneradorObjetosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.AsientoRegistralMapper;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.repository.RepositoryFactory;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionTelematicaInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDomicilioInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPageRepositoryVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPersonaFisicaOJuridicaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroRepresentanteVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoRegistroEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class IntercambioRegistralGeneradorObjetosManagerImpl implements
		IntercambioRegistralGeneradorObjetosManager {
	private static Logger logger = Logger.getLogger(IntercambioRegistralGeneradorObjetosManagerImpl.class);

	protected InfoRegistroDAO infoRegistroDAO;
	protected ConfiguracionIntercambioRegistralDAO configuracionIntercambioRegistralDAO;
	protected DocumentoElectronicoAnexoManager documentoElectronicoAnexoManager;
	protected TipoAsuntoManager tipoAsuntoManager;

	/**
	 * {@inheritDoc}
	 */
	public AsientoRegistralFormVO getAsientoRegistralIntercambioRegistralVO(
			IntercambioRegistralSalidaVO intercambioSalidaVO, String entidad) {

		AsientoRegistralMapper registroMapper = new AsientoRegistralMapper();

		EntidadRegistralVO entidadRegistralOrigen = getConfiguracionIntercambioRegistralDAO().getEntidadRegistralVOByIdScrOfic(String.valueOf(intercambioSalidaVO.getIdOfic()));

		if( entidadRegistralOrigen==null)
		{
			throw new IntercambioRegistralException("No hay configuración de intercambio registral para la entidad de origen", IntercambioRegistralExceptionCodes.ERROR_CODE_OFICINA_NO_MAPEADA);
		}


		InfoRegistroVO infoRegistro =  getInfoRegistroAxsf(intercambioSalidaVO);

		List<InfoRegistroPageRepositoryVO> documentos = getInfoRegistroDocumentos(intercambioSalidaVO);
		infoRegistro.setListadoDocumentos(documentos);
		if(StringUtils.isNotEmpty(infoRegistro.getUnidadOrigen()))
		{
			UnidadTramitacionIntercambioRegistralVO unidadTramitacionOrigen = getConfiguracionIntercambioRegistralDAO().getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(infoRegistro.getUnidadOrigen());
			infoRegistro.setUnidadTramitacionOrigen(unidadTramitacionOrigen);
		}

		infoRegistro.setEntidadRegistralOrigen(entidadRegistralOrigen);

		AsientoRegistralFormVO registro = registroMapper.toAsientoRegistralFormVO(infoRegistro);
		registro.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(intercambioSalidaVO.getTipoOrigen().toString()));
		return registro;
	}

	/**
	 * {@inheritDoc}
	 */
	public AsientoRegistralFormVO getAsientoRegistralIntercambioRegistralVO(
			IntercambioRegistralSalidaVO intercambioSalidaVO, UnidadTramitacionIntercambioRegistralVO unidadTramitacionDestino) {

		AsientoRegistralMapper registroMapper = new AsientoRegistralMapper();
		InfoRegistroVO infoRegistro =  getInfoRegistroAxsf(intercambioSalidaVO);
		List<InfoRegistroPageRepositoryVO> documentos = getInfoRegistroDocumentos(intercambioSalidaVO);
		infoRegistro.setListadoDocumentos(documentos);

		EntidadRegistralVO entidadRegistralOrigen = getConfiguracionIntercambioRegistralDAO().getEntidadRegistralVOByIdScrOfic(String.valueOf(intercambioSalidaVO.getIdOfic()));
		UnidadTramitacionIntercambioRegistralVO unidadTramitacionOrigen = getConfiguracionIntercambioRegistralDAO().getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(infoRegistro.getUnidadOrigen());


		if(entidadRegistralOrigen==null)
		{
			throw new IntercambioRegistralException("No hay configuración de intercambio registral para la oficina de origen.", IntercambioRegistralExceptionCodes.ERROR_CODE_OFICINA_NO_MAPEADA);
		}

		infoRegistro.setEntidadRegistralOrigen(entidadRegistralOrigen);
		infoRegistro.setUnidadTramitacionDestino(unidadTramitacionDestino);
		infoRegistro.setUnidadTramitacionOrigen(unidadTramitacionOrigen);
		AsientoRegistralFormVO registro = registroMapper.toAsientoRegistralFormVO(infoRegistro);
		registro.setTipoRegistro(TipoRegistroEnumVO.getTipoRegistroSIR(intercambioSalidaVO.getTipoOrigen()));
		return registro;
	}

	/**
	 * Obtiene los datos de los documentos adjuntos al registro
	 * @param intercambioSalidaVO
	 * @return
	 */
	private List<InfoRegistroPageRepositoryVO> getInfoRegistroDocumentos(
			IntercambioRegistralSalidaVO intercambioSalidaVO) {
		List<InfoRegistroPageRepositoryVO> listaDocumentos = getInfoRegistroDAO().getInfoRegistroPageRepositories(intercambioSalidaVO.getIdLibro(), intercambioSalidaVO.getIdRegistro());
		List<DocumentoElectronicoAnexoVO> listaDocumentosElectronicos = getDocumentoElectronicoAnexoManager().getDocumentosElectronicoAnexoByRegistro(intercambioSalidaVO.getIdLibro(), intercambioSalidaVO.getIdRegistro());

		try{
			IRepositoryManager repositoryManager = RepositoryFactory.getCurrentPolicy();
			//Obtenemos los ficheros físicos del repositorio correspondiente
			for (Iterator iterator = listaDocumentos.iterator(); iterator.hasNext();) {

				InfoRegistroPageRepositoryVO infoRegistroPageRepository = (InfoRegistroPageRepositoryVO) iterator.next();
				DocumentoElectronicoAnexoVO docElectronico = getDocumentoElectronicoAnexoVO(Long.parseLong(infoRegistroPageRepository.getIdPageh()), listaDocumentosElectronicos);
				if(docElectronico!=null)
				{
					infoRegistroPageRepository.setDatosFirma(docElectronico.getDatosFirma());
					infoRegistroPageRepository.setTipoDocumentoAnexo(docElectronico.getTipoDocumentoAnexo());
					infoRegistroPageRepository.setTipoValidez(docElectronico.getTipoValidez());
				}
				ISRepositoryRetrieveDocumentVO doc = new ISRepositoryRetrieveDocumentVO();
				doc.setDocumentUID(infoRegistroPageRepository.getDocUID());
				doc.setBookID(Integer.valueOf(infoRegistroPageRepository.getIdLibro()));
				doc.setFdrid(Integer.valueOf(infoRegistroPageRepository.getIdRegistro()));
				doc.setPageID(Integer.valueOf(infoRegistroPageRepository.getIdPageh()));
				doc.setEntidad(MultiEntityContextHolder.getEntity());
				doc = repositoryManager.retrieveDocument(doc);
				infoRegistroPageRepository.setContent(doc.getFileContent());
			}
		}
		catch (Exception e) {
			logger.error("Error al obtener los documentos del repositorio para anexarlos al intercambio registral", e);
		}
		return listaDocumentos;
	}

	/**
	 * Obtiene
	 * @param id
	 * @param listaDocumentosElectronicos
	 * @return
	 */
	private DocumentoElectronicoAnexoVO getDocumentoElectronicoAnexoVO(Long id,List<DocumentoElectronicoAnexoVO> listaDocumentosElectronicos){
		DocumentoElectronicoAnexoVO result = null;
		DocumentoElectronicoAnexoVO documento = null;
		for(int i=0;i<listaDocumentosElectronicos.size();i++)
		{
			documento = listaDocumentosElectronicos.get(i);
			if(id.equals(documento.getId().getIdPagina()))
			{
				result = documento;
			}
		}
		return result;
	}

	/**
	 * Obtiene la información del registro necesaria para enviar el intercambio (a partir de la tabla axsf)
	 * @param intercambioSalidaVO
	 * @return
	 */
	private InfoRegistroVO getInfoRegistroAxsf(IntercambioRegistralSalidaVO intercambioSalidaVO)
	{
		InfoRegistroVO infoRegistro = null;
		if(intercambioSalidaVO.getTipoOrigen().intValue()==IntercambioRegistralSalidaVO.TIPO_ENTRADA)
		{
			infoRegistro = getInfoRegistroDAO().getInfoRegistroTipoEntrada(intercambioSalidaVO);
		}
		else
		{
			infoRegistro = getInfoRegistroDAO().getInfoRegistroTipoSalida(intercambioSalidaVO);
		}

		infoRegistro.setCamposExtendidos(getInfoRegistroDAO().getInfoRegistroCamposExtendidos(infoRegistro));
		if(StringUtils.isEmpty(infoRegistro.getCodigoAsunto()) & StringUtils.isEmpty(infoRegistro.getResumen()))
		{
			throw new IntercambioRegistralException("El registro ha de tener Asunto o Resumen.", IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_ASUNTO_RESUMEN);
		}
		//Leemos el código de tipo Asunto, ya que en la tabla aNsf viene el ID
		if(StringUtils.isNotEmpty(infoRegistro.getCodigoAsunto()))
		{
			UsuarioVO usuario = new UsuarioVO();

			usuario.getConfiguracionUsuario().setLocale(new Locale("es", "ES"));
			TipoAsuntoVO asunto = getTipoAsuntoManager().getTipoAsuntoById(usuario, infoRegistro.getCodigoAsunto());
			if(asunto!=null)
			{
				infoRegistro.setCodigoAsunto(asunto.getCodigo());
				infoRegistro.setDescripcionAsunto(asunto.getDescripcion());
			}
		}
		List<InfoRegistroInteresadoVO> interesados = getInteresados(infoRegistro);
		infoRegistro.setInteresados(interesados);

		validateInfoRegistroForIR(infoRegistro);

		return infoRegistro;
	}

	/**
	 * Valida si los campos del registro son válidos para realizar un intercambio registral.
	 *
	 * Si algún campo no cumple con la validación se lanza una excepción de tipo {@code IntercambioRegistralException}
	 *
	 * @param infoRegistro El registro a validar
	 */
	private void validateInfoRegistroForIR(InfoRegistroVO infoRegistro) {
		String numeroTransporte = infoRegistro.getNumeroTransporte();
		if (numeroTransporte != null
				&& numeroTransporte.length() > IDocKeys.FIELD_NUM_TRANSPORTE_SIZE_IR_ENABLED) {
			throw new IntercambioRegistralException(
					"La longitud del campo número de transporte debe de ser menor de "
							+ IDocKeys.FIELD_NUM_TRANSPORTE_SIZE_IR_ENABLED
							+ " caracteres.",
					IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_LENGTH_NUMERO_TRANSPORTE);
		}

		String tipoTransporte = infoRegistro.getTipoTransporte();
		if (tipoTransporte != null) {
			TipoTransporteEnum tipoTransporteEnum = TipoTransporteEnum
					.getTipoTransporte(tipoTransporte);
			if (tipoTransporteEnum == null) {
				throw new IntercambioRegistralException(
						"El valor del campo Tipo de transporte debe de estar comprendido entre los valores definidos en la clase TipoTransporteEnum ",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_TIPO_TRANSPORTE);
			}
		}
	}

	/**
	 * Obtiene los interesados del registro
	 * @param infoRegistroAxsf
	 * @return
	 */
	private List<InfoRegistroInteresadoVO> getInteresados(
			InfoRegistroVO infoRegistroAxsf) {
		List<InfoRegistroInteresadoVO> listaInteresados = getInfoRegistroDAO()
				.getInfoRegistroInteresados(infoRegistroAxsf);
		for (InfoRegistroInteresadoVO interesado : listaInteresados) {
			InfoRegistroPersonaFisicaOJuridicaVO persona = null;
			// Obtenemos la informacion del interesado
			persona = getInfoPersonaORepresentante(interesado);

			if (persona != null) {
				interesado.setInfoPersona(persona);

				InfoRegistroDireccionVO direccion = getInfoRegistroDAO()
						.getInfoRegistroDireccion(interesado);
				// Si la dirección es nula lanzar una excepción
				if (direccion == null) {
					throw new IntercambioRegistralException(
							"El interesado debe de tener al menos una dirección registrada",
							IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_INTERESADOS_DIRECCION);
				}

				InfoRegistroDireccionTelematicaInteresadoVO direccionTelematica = getInfoRegistroDAO()
						.getInfoRegistroDireccionTelematicaInteresado(direccion);
				direccion.setDireccionTelematicaInteresado(direccionTelematica);
				try {
					InfoRegistroDomicilioInteresadoVO domicilio = getInfoRegistroDAO()
							.getInfoRegistroDomicilioInteresado(direccion);
					// Si el domicilio es nulo lanzar una excepción
					if (domicilio == null) {
						throw new IntercambioRegistralException(
								"La localidad o provincia de alguno de los interesados no tiene equivalente en el Directorio Comun.",
								IntercambioRegistralExceptionCodes.ERROR_CODE_PROVINCIA_LOCALIDAD_NO_MAPEADAS);
					}
					direccion.setDomicilioInteresado(domicilio);
				} catch (Exception e) {

					throw new IntercambioRegistralException(
							"La localidad o provincia de alguno de los interesados no tiene equivalente en el Directorio Comun.",
							IntercambioRegistralExceptionCodes.ERROR_CODE_PROVINCIA_LOCALIDAD_NO_MAPEADAS);
				}

				interesado.setDireccion(direccion);

				// Obtenemos la información del representante
				interesado.setInfoRepresentante(getRepresentanteByInteresado(interesado));

			}
		}
		return listaInteresados;
	}

	/**
	 * Método que obtiene la información del representante a partir del interesado
	 * @param interesado - Datos del interesado a buscar
	 * @return {@link InfoRegistroPersonaFisicaOJuridicaVO} - Información del representante
	 */
	private InfoRegistroPersonaFisicaOJuridicaVO getRepresentanteByInteresado(
			InfoRegistroInteresadoVO interesado) {

		InfoRegistroRepresentanteVO infoRepresentante = getInfoRegistroDAO()
				.getInfoRegistroRepresentante(interesado);

		InfoRegistroPersonaFisicaOJuridicaVO result = null;
		// Si el representante existe
		if (infoRepresentante != null) {
			// Generamos un objeto de tipo InfoRegistroInteresadoVO auxiliar,
			// para obtener la información del representante y la dirección del
			// mismo
			InfoRegistroInteresadoVO auxInfoRegistroInteresado = new InfoRegistroInteresadoVO();
			auxInfoRegistroInteresado.setIdPersona(infoRepresentante
					.getIdRepresentante());

			// obtenemos la información del representante
			result = getInfoPersonaORepresentante(auxInfoRegistroInteresado);

			// mapeamos la direccion del representante para obtener la
			// información completa
			auxInfoRegistroInteresado.setIdDireccion(infoRepresentante
					.getIdAddress());

			// obtenemos la información de la dirección
			InfoRegistroDireccionVO direccionRepresentante = getInfoRegistroDAO()
					.getInfoRegistroDireccion(auxInfoRegistroInteresado);
			// Si la dirección es nula lanzar una excepción
			if (direccionRepresentante == null) {
				throw new IntercambioRegistralException(
						"El representante debe de tener al menos una dirección registrada",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_REPRESENTANTES_DIRECCION);
			}

			InfoRegistroDireccionTelematicaInteresadoVO direccionTelematica = getInfoRegistroDAO()
					.getInfoRegistroDireccionTelematicaInteresado(
							direccionRepresentante);
			direccionRepresentante
					.setDireccionTelematicaInteresado(direccionTelematica);
			try {
				InfoRegistroDomicilioInteresadoVO domicilio = getInfoRegistroDAO()
						.getInfoRegistroDomicilioInteresado(
								direccionRepresentante);
				// Si el domicilio es nulo lanzar una excepción
				if (domicilio == null) {
					throw new IntercambioRegistralException(
							"La localidad o provincia de alguno del representante no tiene equivalente en el Directorio Comun.",
							IntercambioRegistralExceptionCodes.ERROR_CODE_PROVINCIA_LOCALIDAD_REPRESENTANTES_NO_MAPEADAS);
				}
				direccionRepresentante.setDomicilioInteresado(domicilio);
			} catch (Exception e) {

				throw new IntercambioRegistralException(
						"La localidad o provincia de alguno del representante no tiene equivalente en el Directorio Comun.",
						IntercambioRegistralExceptionCodes.ERROR_CODE_PROVINCIA_LOCALIDAD_REPRESENTANTES_NO_MAPEADAS);
			}

			//seteamos los datos de la dirección en los datos del interesado
			interesado.setDireccionRepresentante(direccionRepresentante);

		}

		return result;
	}

	/**
	 * Método que nos obtiene la información del interesado/representante a
	 * partir del objeto que pasamos como parámetro
	 *
	 * @param infoRegistroInteresado
	 *            - Objeto por el que se realiza la búsqueda en BBDD
	 * @return {@link InfoRegistroPersonaFisicaOJuridicaVO} - Información del
	 *         interesado/representante
	 */
	private InfoRegistroPersonaFisicaOJuridicaVO getInfoPersonaORepresentante(
			InfoRegistroInteresadoVO infoRegistroInteresado) {
		InfoRegistroPersonaFisicaOJuridicaVO result = null;

		result = getInfoRegistroDAO().getInfoRegistroPersonaFisica(
				infoRegistroInteresado);
		if (result == null) {
			result = getInfoRegistroDAO().getInfoRegistroPersonaJuridica(
					infoRegistroInteresado);

		}

		return result;
	}

	public InfoRegistroDAO getInfoRegistroDAO() {
		return infoRegistroDAO;
	}

	public void setInfoRegistroDAO(InfoRegistroDAO infoRegistroDAO) {
		this.infoRegistroDAO = infoRegistroDAO;
	}

	public ConfiguracionIntercambioRegistralDAO getConfiguracionIntercambioRegistralDAO() {
		return configuracionIntercambioRegistralDAO;
	}

	public void setConfiguracionIntercambioRegistralDAO(
			ConfiguracionIntercambioRegistralDAO configuracionIntercambioRegistralDAO) {
		this.configuracionIntercambioRegistralDAO = configuracionIntercambioRegistralDAO;
	}

	public DocumentoElectronicoAnexoManager getDocumentoElectronicoAnexoManager() {
		return documentoElectronicoAnexoManager;
	}

	public void setDocumentoElectronicoAnexoManager(
			DocumentoElectronicoAnexoManager documentoElectronicoAnexoManager) {
		this.documentoElectronicoAnexoManager = documentoElectronicoAnexoManager;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}


}
