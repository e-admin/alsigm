package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.InfoRegistroDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroCamposExtendidosVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInfoDocumentoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPageRepositoryVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroRepresentanteVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionTelematicaInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDomicilioInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPersonaFisicaOJuridicaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public class IbatisInfoRegistroDAOImpl implements
		InfoRegistroDAO {
	private static final Logger logger = Logger.getLogger(IbatisInfoRegistroDAOImpl.class);

	private static final String GET_INFO_REGISTRO_CAMPOS_EXTENDIDOS = "InfoRegistroCamposExtendidosVO.getInfoRegistroCamposExtendidos";
	private static final String GET_INFO_REGISTRO_INTERESADOS = "InfoRegistroInteresadoVO.getInfoRegistroInteresadosList";
	private static final String GET_INFO_REGISTRO_TIPO_ENTRADA = "InfoRegistroVO.getInfoRegistroTipoEntradaVO";
	private static final String GET_INFO_REGISTRO_TIPO_SALIDA = "InfoRegistroVO.getInfoRegistroTipoSalidaVO";
	private static final String GET_INFO_REGISTRO_PERSONA_JURIDICA = "InfoRegistroPersonaFisicaOJuridicaVO.getInfoRegistroPersonaJuridicaVO";
	private static final String GET_INFO_REGISTRO_PERSONA_FISICA = "InfoRegistroPersonaFisicaOJuridicaVO.getInfoRegistroPersonaFisicaVO";

	private static final String GET_INFO_REGISTRO_REPRESENTANTE ="InfoRegistroRepresentanteVO.getInfoRegistroRepresentanteVO";

	private static final String GET_INFO_REGISTRO_DIRECCIONES_INTERESADOS = "InfoRegistroDireccionVO.getInfoRegistroDireccionesList";
	private static final String GET_INFO_REGISTRO_DIRECCION_TELEMATICA_INTERESADO = "InfoRegistroDireccionTelematicaInteresadoVO.getInfoRegistroDireccionTelematicaInteresadoVO";
	private static final String GET_INFO_REGISTRO_DOMICILIO_INTERESADO = "InfoRegistroDomicilioInteresadoVO.getInfoRegistroDomicilioInteresadoVO";
	private static final String GET_INFO_REGISTRO_DOMICILIO_INTERNACIONAL_INTERESADO = "InfoRegistroDomicilioInteresadoVO.getInfoRegistroDomicilioInternacionalInteresadoVO";

	private static final String GET_INFO_REGISTRO_DIRECCION_INTERESADO = "InfoRegistroDireccionVO.getInfoRegistroDireccionInteresado";
	private static final String GET_INFO_REGISTRO_PAGE_REPOSITORIES = "InfoRegistroPageRepositoryVO.getInfoRegistroPageRepositories";
	private static final String GET_INFO_REGISTRO_INFO_DOCUMENTO = "InfoRegistroPageRepositoryVO.getInfoRegistroInfoDocumento";

	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();


	public InfoRegistroVO getInfoRegistroTipoEntrada(
			IntercambioRegistralSalidaVO intercambioRegistral) {
		try{

			InfoRegistroVO infoRegistro = (InfoRegistroVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_TIPO_ENTRADA, intercambioRegistral);
			infoRegistro.setIdLibro(intercambioRegistral.getIdLibro());
			infoRegistro.setIdRegistro(intercambioRegistral.getIdRegistro());
			return infoRegistro;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de información del registro", e);

			throw new RuntimeException(e);
		}
	}

	public InfoRegistroVO getInfoRegistroTipoSalida(
			IntercambioRegistralSalidaVO intercambioRegistral) {
		try{

			InfoRegistroVO infoRegistro = (InfoRegistroVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_TIPO_SALIDA, intercambioRegistral);
			infoRegistro.setIdLibro(intercambioRegistral.getIdLibro());
			infoRegistro.setIdRegistro(intercambioRegistral.getIdRegistro());
			return infoRegistro;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de información del registro", e);

			throw new RuntimeException(e);
		}
	}

	public List<InfoRegistroInteresadoVO> getInfoRegistroInteresados(
			InfoRegistroVO infoRegistro) {
		try{

			List<InfoRegistroInteresadoVO> interesados = (List<InfoRegistroInteresadoVO>)getSqlMapClientTemplate().queryForList(GET_INFO_REGISTRO_INTERESADOS, infoRegistro);
			return interesados;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de los interesados", e);

			throw new RuntimeException(e);
		}
	}

	public InfoRegistroPersonaFisicaOJuridicaVO getInfoRegistroPersonaFisica(
			InfoRegistroInteresadoVO infoRegistroInteresado) {
		try{

			InfoRegistroPersonaFisicaOJuridicaVO personaFisica = (InfoRegistroPersonaFisicaOJuridicaVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_PERSONA_FISICA, infoRegistroInteresado);
			return personaFisica;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de información de persona fisica", e);

			throw new RuntimeException(e);
		}
	}

	public InfoRegistroPersonaFisicaOJuridicaVO getInfoRegistroPersonaJuridica(
			InfoRegistroInteresadoVO infoRegistroInteresado) {
		try{

			InfoRegistroPersonaFisicaOJuridicaVO personaJuridica = (InfoRegistroPersonaFisicaOJuridicaVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_PERSONA_JURIDICA, infoRegistroInteresado);
			return personaJuridica;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de información de persona juridica", e);

			throw new RuntimeException(e);
		}
	}


	public InfoRegistroDireccionTelematicaInteresadoVO getInfoRegistroDireccionTelematicaInteresado(
			InfoRegistroDireccionVO direccion) {
		try{

			InfoRegistroDireccionTelematicaInteresadoVO direccionTelematica = (InfoRegistroDireccionTelematicaInteresadoVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_DIRECCION_TELEMATICA_INTERESADO, direccion);
			return direccionTelematica;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de información de persona juridica", e);

			throw new RuntimeException(e);
		}
	}

	public List<InfoRegistroDireccionVO> getInfoRegistroDireccionesInteresado(
			InfoRegistroInteresadoVO interesado) {
		try{

			List<InfoRegistroDireccionVO> direcciones = (List<InfoRegistroDireccionVO>)getSqlMapClientTemplate().queryForList(GET_INFO_REGISTRO_DIRECCIONES_INTERESADOS, interesado);
			return direcciones;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de las direcciones telematicas de un interesado", e);

			throw new RuntimeException(e);
		}
	}


	public InfoRegistroDireccionVO getInfoRegistroDireccion(InfoRegistroInteresadoVO infoRegistroInteresadoVO) {
	try{

			InfoRegistroDireccionVO direccion = (InfoRegistroDireccionVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_DIRECCION_INTERESADO, infoRegistroInteresadoVO);
			return direccion;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de las direccion de un registro", e);

			throw new RuntimeException(e);
		}
	}




	public InfoRegistroDomicilioInteresadoVO getInfoRegistroDomicilioInteresado(
			InfoRegistroDireccionVO direccion) {
		try{

			InfoRegistroDomicilioInteresadoVO domicilio = (InfoRegistroDomicilioInteresadoVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_DOMICILIO_INTERESADO, direccion);
			if (domicilio == null){
				domicilio = (InfoRegistroDomicilioInteresadoVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_DOMICILIO_INTERNACIONAL_INTERESADO, direccion);
			}
			return domicilio;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de un domicilio de un interesado", e);

			throw new RuntimeException(e);
		}
	}


	public List<InfoRegistroPageRepositoryVO> getInfoRegistroPageRepositories(
			Long idLibro, Long idRegistro) {
		try{
			HashMap<String, Long> params = new HashMap<String, Long>();
			params.put("idLibro", idLibro);
			params.put("idRegistro", idRegistro);
			List<InfoRegistroPageRepositoryVO> infoDocumentos = (List<InfoRegistroPageRepositoryVO>)getSqlMapClientTemplate().queryForList(GET_INFO_REGISTRO_PAGE_REPOSITORIES, params);
			return infoDocumentos;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de los documentos del registro", e);

			throw new RuntimeException(e);
		}
	}
	public List<InfoRegistroCamposExtendidosVO> getInfoRegistroCamposExtendidos(
			InfoRegistroVO infoRegistro) {
		try{
			HashMap<String, Long> params = new HashMap<String, Long>();
			params.put("idLibro", infoRegistro.getIdLibro());
			params.put("idRegistro", infoRegistro.getIdRegistro());
			List<InfoRegistroCamposExtendidosVO> camposExtendidos = (List<InfoRegistroCamposExtendidosVO>)getSqlMapClientTemplate().queryForList(GET_INFO_REGISTRO_CAMPOS_EXTENDIDOS, params);
			return camposExtendidos;
		}
		catch (DataAccessException e) {
			logger.error("Error en la busqueda de los documentos del registro", e);

			throw new RuntimeException(e);
		}
	}


	public InfoRegistroInfoDocumentoVO getInfoRegistroInfoDocumento(Integer idDocumento) {
		try{

			InfoRegistroInfoDocumentoVO infoDocumento = (InfoRegistroInfoDocumentoVO)getSqlMapClientTemplate().queryForObject(GET_INFO_REGISTRO_INFO_DOCUMENTO, idDocumento);
				return infoDocumento;
			}
			catch (DataAccessException e) {
				logger.error("Error en la busqueda del documento con id:"+idDocumento, e);

				throw new RuntimeException(e);
			}
		}

	public InfoRegistroRepresentanteVO getInfoRegistroRepresentante(
			InfoRegistroInteresadoVO infoRegistroInteresado) {
		try {

			InfoRegistroRepresentanteVO representante = (InfoRegistroRepresentanteVO) getSqlMapClientTemplate()
					.queryForObject(GET_INFO_REGISTRO_REPRESENTANTE,
							infoRegistroInteresado);
			return representante;
		} catch (DataAccessException e) {
			logger.error(
					"Error en la busqueda del representante para el interesado con id:"
							+ infoRegistroInteresado.getId(), e);

			throw new RuntimeException(e);
		}
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}




}
