package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.PersonValidation;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.contextholder.IsicresContextHolder;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.CiudadFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.DireccionesFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.ProvinciaFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.TercerosFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.TipoDireccionFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory.TipoDocumentoFactory;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.CiudadesMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.CriteriosMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.DireccionesMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ParamIdMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.PersonaMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.PersonasMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ProvinciasMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.TipoDireccionesMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.TipoDocumentosMapper;

/**
 *
 * @author IECISA
 *
 */
public class TercerosLegacyDelegateImpl implements TercerosFacade {

	public TercerosLegacyDelegateImpl() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#count(es
	 * .ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO)
	 */
	public Integer count(CriteriaVO criteria) {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			return getPersonValidation().count(
					new CriteriosMapper().marshall(criteria, context));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#findByCriteria
	 * (es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO)
	 */
	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String searchResultsXML = getPersonValidation().search(
					new CriteriosMapper().marshall(criteria, context));

			return getTercerosFactory().createTerceros(
					new PersonasMapper().unmarshall(searchResultsXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#create(es
	 * .ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO)
	 */
	public TerceroValidadoVO create(TerceroValidadoVO tercero) {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String idXML = getPersonValidation().create(
					new PersonaMapper().marshall(tercero, context));

			return getTerceroManager().get(idXML);
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#update(es
	 * .ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO)
	 */
	public TerceroValidadoVO update(TerceroValidadoVO tercero) {
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String idXML = getPersonValidation().update(
					new PersonaMapper().marshall(tercero, context));

			return getTerceroManager().get(idXML);
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#get(java
	 * .lang.String)
	 */
	public TerceroValidadoVO get(String id) {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			Entity entity = new Entity();
			entity.setId(id);

			String terceroXML = getPersonValidation().getInfo(
					new ParamIdMapper().marshall(entity, context));

			return getTercerosFactory().createTercero(
					new PersonaMapper().unmarshall(terceroXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#
	 * getTiposDocumentosIdentificacion()
	 */
	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentosIdentificacion() {
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String tiposDocumentosXML = getPersonValidation().getDocsType(
					new ParamIdMapper().marshall(null, context));

			return getTipoDocumentoFactory().createTipoDocumentos(
					new TipoDocumentosMapper().unmarshall(tiposDocumentosXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	public List<TipoDireccionTelematicaVO> getTiposDireccion() {
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String tiposDireccionesXML = getPersonValidation()
					.getAddressesType(
							new ParamIdMapper().marshall(null, context));

			return getTipoDireccionFactory()
					.createTiposDireccion(
							new TipoDireccionesMapper()
									.unmarshall(tiposDireccionesXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#getDirecciones
	 * (es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO)
	 */
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero) {
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String direccionesXML = getPersonValidation().getAddresses(
					new ParamIdMapper().marshall(tercero, context));

			return getDireccionesFactory().createDirecciones(
					new DireccionesMapper().unmarshall(direccionesXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#getDirecciones
	 * (es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO,
	 * es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType)
	 */
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero,
			DireccionType tipoDireccion) {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String direccionesXML = getPersonValidation().getAddresses(
					new ParamIdMapper().marshall(tercero, context),
					tipoDireccion.getValue());

			return getDireccionesFactory().createDirecciones(
					new DireccionesMapper().unmarshall(direccionesXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#getProvincias
	 * ()
	 */
	public List<ProvinciaVO> getProvincias() {

		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String provinciasXML = getPersonValidation().getProvicies(
					new ParamIdMapper().marshall(null, context));

			return getProvinciaFactory().createProvincias(
					new ProvinciasMapper().unmarshall(provinciasXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate#getCiudades
	 * ()
	 */
	public List<CiudadVO> getCiudades(ProvinciaVO provincia) {
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			populateContext(context);

			String ciudadesXML = getPersonValidation().getCities(
					new ParamIdMapper().marshall(provincia, context));

			return getCiudadFactory().createCiudades(
					new CiudadesMapper().unmarshall(ciudadesXML));
		} catch (Exception e) {
			String message = new StringBuffer().toString();
			logger.error(message, e);

			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param context
	 */
	protected void populateContext(Map<String, Object> context) {
		ConfiguracionUsuarioVO configuracionUsuario = IsicresContextHolder
				.getContextoAplicacion().getUsuarioActual()
				.getConfiguracionUsuario();
		context.put("configuracionUsuario", configuracionUsuario);
	}

	public PersonValidation getPersonValidation() {
		return personValidation;
	}

	public void setPersonValidation(PersonValidation personValidation) {
		this.personValidation = personValidation;
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	public TercerosFactory getTercerosFactory() {
		return tercerosFactory;
	}

	public void setTercerosFactory(TercerosFactory tercerosFactory) {
		this.tercerosFactory = tercerosFactory;
	}

	public DireccionesFactory getDireccionesFactory() {
		return direccionesFactory;
	}

	public void setDireccionesFactory(DireccionesFactory direccionesFactory) {
		this.direccionesFactory = direccionesFactory;
	}

	public CiudadFactory getCiudadFactory() {
		return ciudadFactory;
	}

	public void setCiudadFactory(CiudadFactory ciudadFactory) {
		this.ciudadFactory = ciudadFactory;
	}

	public ProvinciaFactory getProvinciaFactory() {
		return provinciaFactory;
	}

	public void setProvinciaFactory(ProvinciaFactory provinciaFactory) {
		this.provinciaFactory = provinciaFactory;
	}

	public TipoDireccionFactory getTipoDireccionFactory() {
		return tipoDireccionFactory;
	}

	public void setTipoDireccionFactory(
			TipoDireccionFactory tipoDireccionFactory) {
		this.tipoDireccionFactory = tipoDireccionFactory;
	}

	public TipoDocumentoFactory getTipoDocumentoFactory() {
		return tipoDocumentoFactory;
	}

	public void setTipoDocumentoFactory(
			TipoDocumentoFactory tipoDocumentoFactory) {
		this.tipoDocumentoFactory = tipoDocumentoFactory;
	}

	// Members
	protected PersonValidation personValidation;

	protected TerceroManager terceroManager;

	protected TercerosFactory tercerosFactory;

	protected DireccionesFactory direccionesFactory;

	protected CiudadFactory ciudadFactory;

	protected ProvinciaFactory provinciaFactory;

	protected TipoDireccionFactory tipoDireccionFactory;

	protected TipoDocumentoFactory tipoDocumentoFactory;

	protected static final Logger logger = Logger
			.getLogger(TercerosLegacyDelegateImpl.class);

}
