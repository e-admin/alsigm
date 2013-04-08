package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;
import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Mapa;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Municipio;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoCoordenadas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoReferenciaCatastral;
import ieci.tecdoc.sgm.core.services.geolocalizacion.PeticionPlanoVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Provincia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.geolocalizacion.URLsPlano;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Vias;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GeoLocalizacionAction extends BaseDispatchAction {

	/* Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GeoLocalizacionAction.class);

	ServicioGeoLocalizacion servicioGeoLocalizacion = null;
	private static String VER_PLANO_REFERENCIA_CATASTRAL = "1";
	private static String VER_PLANO_COORDENADAS = "2";
	private static String VER_PLANO_VIA = "3";
	private static String COD_TP_DOC = "DOC_GIS";

	/*
	 * Este valor no debería estar prefijado ya que con los correos intercambiados con la empresa responsable de LocalGis se nos
	 * ha indicado que un entidad es una agrupación de municipios, por lo que una vez que el usuario seleccione el municipio
	 * lo que correspondería sería obtener el id_entidad al que pertenece y utilizar este valor y no uno prefijado.
	 *
	 * De momento para la entrega de SIGEM3.0 , por petición de MineTur , se establece este parámetro como fijo, con el valor que
	 * nos ha indicado la empresa responsable de LocalGis
	 */
	private static String ID_ENTIDAD ="3";

	public ActionForward getProvincias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio getProvincias ");
		}
		ArrayList lista = new ArrayList();
		Provincias provincias = new Provincias();
		provincias.setProvincias(new ArrayList());

		getServicioGeoLocalizacion();
		try {
			provincias = servicioGeoLocalizacion.obtenerProvincias();
			if (provincias == null) {
				provincias = new Provincias();
				provincias.setProvincias(new ArrayList());
			}
		} catch (GeoLocalizacionServicioException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("getVias-->No se ha encontrado ninguna provincia.Excepcion:"
								+ e);
			}
		}
		int i = 0;
		for (i = 0; i < provincias.getProvincias().size(); i++) {
			Provincia provincia = (Provincia) provincias.get(i);
			lista.add(generateDatosItem(provincia.getCodigoINE() + "",
					provincia.getNombreOficial()));
		}

		List list = CollectionBean.getBeanList(new ListCollection(lista));

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request
				.setAttribute(
						"Formatter",
						factory
								.getFormatter(getISPACPath("/digester/geolocalizacion/selectValorformatter.xml")));
		request.setAttribute("SubstituteList", list);
		request.setAttribute("setAction",
				"geoLocalizacion.do?method=setSubstitute");

		if (logger.isDebugEnabled()) {
			logger.debug("Fin getProvincias ");
		}

		return mapping.findForward("success");

	}

	public ActionForward getMunicipios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio getMunicipios ");
		}
		EntityForm entityForm = (EntityForm) form;
		// El formulario ya valida la existencia de este campo
		String idProvincia = entityForm
				.getProperty("SGL_OBRAS_MENORES:ID_PROVINCIA");
		ArrayList lista = new ArrayList();
		Municipios municipios = new Municipios();
		municipios.setMunicipios(new ArrayList());

		getServicioGeoLocalizacion();
		try {
			municipios = servicioGeoLocalizacion.obtenerMunicipios(Integer
					.parseInt(idProvincia));
			if (municipios == null) {
				municipios = new Municipios();
				municipios.setMunicipios(new ArrayList());
			}
		} catch (GeoLocalizacionServicioException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("getVias-->No se ha encontrado ningún municipio para el id provincia : "
								+ idProvincia + " Ecepcion:" + e);
			}

		}
		int i = 0;
		for (i = 0; i < municipios.getMunicipios().size(); i++) {
			Municipio municipio = (Municipio) municipios.get(i);
			lista.add(generateDatosItem(municipio.getCodigoINE() + "",
					municipio.getNombreOficial()));
		}

		List list = CollectionBean.getBeanList(new ListCollection(lista));
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request
				.setAttribute(
						"Formatter",
						factory
								.getFormatter(getISPACPath("/digester/geolocalizacion/selectValorformatter.xml")));
		request.setAttribute("SubstituteList", list);
		request.setAttribute("setAction",
				"geoLocalizacion.do?method=setSubstitute");

		if (logger.isDebugEnabled()) {
			logger.debug("Fin getMunicipios ");
		}

		return mapping.findForward("success");

	}

	public ActionForward getVias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio getVias ");
		}
		EntityForm entityForm = (EntityForm) form;
		String idMunicipio = entityForm
				.getProperty("SGL_OBRAS_MENORES:ID_MUNICIPIO");
		String filtro = entityForm.getProperty("SGL_OBRAS_MENORES:VIA");
		Vias vias = new Vias();
		vias.setVias(new ArrayList());
		ArrayList lista = new ArrayList();
		getServicioGeoLocalizacion();
		try {
			vias = servicioGeoLocalizacion.validarVia(filtro, Integer
					.parseInt(idMunicipio));
			if (vias == null) {
				vias = new Vias();
				vias.setVias(new ArrayList());
			}
		} catch (GeoLocalizacionServicioException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("getVias-->No se ha encontrado ningún valor para la vía : "
								+ filtro + " Ecepcion:" + e);
			}

		}

		int i = 0;
		for (i = 0; i < vias.getVias().size(); i++) {
			Via via = (Via) vias.get(i);

			lista
					.add(generateDatosItem(via.getIdVia() + "", via
							.getNombreVia()));
		}

		List list = CollectionBean.getBeanList(new ListCollection(lista));

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request
				.setAttribute(
						"Formatter",
						factory
								.getFormatter(getISPACPath("/digester/geolocalizacion/selectValorformatter.xml")));
		request.setAttribute("SubstituteList", list);
		request.setAttribute("setAction",
				"geoLocalizacion.do?method=setSubstitute");

		if (logger.isDebugEnabled()) {
			logger.debug("Fin getMunicipios ");
		}

		return mapping.findForward("success");

	}

	public ActionForward referenciaCatastral(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio referenciaCatastral ");
		}

		EntityForm entityForm = (EntityForm) form;
		String idMunicipio = entityForm
				.getProperty("SGL_OBRAS_MENORES:ID_MUNICIPIO");
		String referenciaCatastral = entityForm
				.getProperty("SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL");

		request.setAttribute("keySeccion", request.getParameter("keySeccion"));
		request.setAttribute("referenciaCatastral", "1");
		String idMapa = request.getParameter("idMapa");

		List list = getListaPlanos(idMunicipio);
		if (StringUtils.isBlank(idMapa) && list.size() > 0) {
			idMapa = ((ItemBean) list.get(0)).getItem().getString("ID");
		}

		PeticionPlanoReferenciaCatastral peticionPlanoReferenciaCatastral = new PeticionPlanoReferenciaCatastral();
		peticionPlanoReferenciaCatastral.setAlto(440);
		peticionPlanoReferenciaCatastral.setAncho(600);
		//peticionPlanoReferenciaCatastral.setCodigoINEMunicipio(Integer.parseInt(idMunicipio));
		peticionPlanoReferenciaCatastral.setCodigoINEMunicipio(Integer.parseInt(ID_ENTIDAD));
		peticionPlanoReferenciaCatastral.setEscala(1000);
		peticionPlanoReferenciaCatastral
				.setReferenciaCatastral(referenciaCatastral);
		peticionPlanoReferenciaCatastral.setIdMapa(Integer.parseInt(idMapa));


		 URLsPlano urlPlano = new URLsPlano();
		 try {
		 urlPlano =
		 servicioGeoLocalizacion.verPlanoPorReferenciaCatastral(peticionPlanoReferenciaCatastral);
		 } catch (GeoLocalizacionServicioException e) {
		 if (logger.isDebugEnabled()) {
		 logger
		 .debug("referenciaCatastral-->No se ha encontrado ningún mapa para la referencia catastral:"+referenciaCatastral+" e:"+e);
		 }
		 }
		 String urlMapa =urlPlano.getUrlMapServer();
		 if (logger.isDebugEnabled()) {
		 logger.debug("referenciaCatastral-->UrlMapa:" + urlMapa);
		 }
		 request.getSession().setAttribute("urlMapa", urlMapa);


		request.setAttribute("listadoPlanos", list);
		request.setAttribute("idMapa", idMapa);
		if (logger.isDebugEnabled()) {
			logger.debug("Fin referenciaCatastral");
		}

		return mapping.findForward("verPlano");

	}

	public ActionForward coordenadas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Inicio coordenadas ");
		}

		EntityForm entityForm = (EntityForm) form;
		String idMunicipio = entityForm
				.getProperty("SGL_OBRAS_MENORES:ID_MUNICIPIO");
		String coorx = entityForm.getProperty("SGL_OBRAS_MENORES:COOR_X");
		String coory = entityForm.getProperty("SGL_OBRAS_MENORES:COOR_Y");

		request.setAttribute("keySeccion", request.getParameter("keySeccion"));
		request.setAttribute("coordenadas", "1");
		String idMapa = request.getParameter("idMapa");

		List list = getListaPlanos(idMunicipio);
		if (StringUtils.isBlank(idMapa) && list.size() > 0) {
			idMapa = ((ItemBean) list.get(0)).getItem().getString("ID");
		}

		PeticionPlanoCoordenadas peticionPlanoCoordenadas = new PeticionPlanoCoordenadas();
		peticionPlanoCoordenadas.setAlto(440);
		peticionPlanoCoordenadas.setAncho(600);
		//peticionPlanoCoordenadas.setCodigoINEMunicipio(Integer.parseInt(idMunicipio));
		peticionPlanoCoordenadas.setCodigoINEMunicipio(Integer.parseInt(ID_ENTIDAD));
		peticionPlanoCoordenadas.setEscala(1000);
		double coorxD = 0;
		double cooryD = 0;
		try{
			cooryD=Double.parseDouble(coory);
			coorxD=Double.parseDouble(coorx);
		}catch (Exception e){
			throw new ISPACInfo(getResources(request).getMessage(
			"localgis.coordenadas.decimales"), false);
		}
		peticionPlanoCoordenadas.setCoordX(coorxD);
		peticionPlanoCoordenadas.setCoordY(cooryD);
		peticionPlanoCoordenadas.setIdMapa(Integer.parseInt(idMapa));
		 URLsPlano urlPlano = new URLsPlano();
		 try {
		 urlPlano =
		 servicioGeoLocalizacion.verPlanoPorCoordenadas(peticionPlanoCoordenadas);
		 } catch (GeoLocalizacionServicioException e) {
		 if (logger.isDebugEnabled()) {
		 logger
		 .debug("coordenadas-->No se ha encontrado ningún mapa para las coordenadas: x:"+peticionPlanoCoordenadas.getCoordX()
		 + " y: " + peticionPlanoCoordenadas.getCoordY());
		 }
		 }
		 String urlMapa = urlPlano.getUrlMapServer();
		 if (logger.isDebugEnabled()) {
		 logger.debug("coordenadas-->UrlMapa:" + urlMapa);
		 }
		 request.getSession().setAttribute("urlMapa", urlMapa);

		request.setAttribute("listadoPlanos", list);
		request.setAttribute("idMapa", idMapa);
		if (logger.isDebugEnabled()) {
			logger.debug("Fin coordenadas");
		}

		return mapping.findForward("verPlano");

	}

	public ActionForward via(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio via ");
		}

		EntityForm entityForm = (EntityForm) form;
		String idMunicipio = entityForm
				.getProperty("SGL_OBRAS_MENORES:ID_MUNICIPIO");
		String idVia = entityForm.getProperty("SGL_OBRAS_MENORES:ID_VIA");

		request.setAttribute("keySeccion", request.getParameter("keySeccion"));
		request.setAttribute("via", "1");
		String idMapa = request.getParameter("idMapa");

		List list = getListaPlanos(idMunicipio);
		if (StringUtils.isBlank(idMapa) && list.size() > 0) {
			idMapa = ((ItemBean) list.get(0)).getItem().getString("ID");
		}
		PeticionPlanoVia peticionPlanoVia = new PeticionPlanoVia();
		peticionPlanoVia.setAlto(440);
		peticionPlanoVia.setAncho(600);
		//peticionPlanoVia.setCodigoINEMunicipio(Integer.parseInt(idMunicipio));
		peticionPlanoVia.setCodigoINEMunicipio(Integer.parseInt(ID_ENTIDAD));
		peticionPlanoVia.setEscala(1000);
		peticionPlanoVia.setVia(Integer.parseInt(idVia));
		peticionPlanoVia.setIdMapa(Integer.parseInt(idMapa));
		 URLsPlano urlPlano = new URLsPlano();
		 try {
		 urlPlano = servicioGeoLocalizacion
		 .verPlanoPorIdVia(peticionPlanoVia);
		 } catch (GeoLocalizacionServicioException e) {
		 if (logger.isDebugEnabled()) {
		 logger
		 .debug("via-->No se ha encontrado ningún mapa para la via con  id:"
		 + idVia + " " + e);
		 }
		 }
		 String urlMapa = urlPlano.getUrlMapServer();
		 if (logger.isDebugEnabled()) {
		 logger.debug("via-->UrlMapa:" + urlMapa);
		 }
		 request.getSession().setAttribute("urlMapa", urlMapa);

		request.setAttribute("idMapa", idMapa);

		request.setAttribute("listadoPlanos", list);

		if (logger.isDebugEnabled()) {
			logger.debug("Fin via");
		}

		return mapping.findForward("verPlano");

	}

	public ActionForward validarReferenciaCatastral(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio validarReferenciaCatastral ");
		}
		EntityForm entityForm = (EntityForm) form;
		String referenciaCatastral = entityForm
				.getProperty("SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL");
		ServicioCatastro servicioCatastro = LocalizadorServicios
				.getServicioCatastro();
		Boolean referenciaValidada = servicioCatastro
				.validarReferenciaCatastral(referenciaCatastral);
		if (referenciaValidada) {
			request.setAttribute("refValidada", "referenciaCatastral.valida");
		} else {
			request.setAttribute("refValidada", "referenciaCatastral.invalida");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" validarReferenciaCatastral resultado"
					+ referenciaValidada);
		}
		return mapping.findForward("success");

	}

	public ActionForward storePlano(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio storePlano ");
		}
		// /////////////////////////////////////////////
		// Se obtiene el estado de tramitación
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(
				cct);
		IState currentState = managerAPI.currentState(getStateticket(request));
		IInvesflowAPI invesFlowAPI = session.getAPI();

		IProcedureAPI procedureAPI = cct.getAPI().getProcedureAPI();
		IItemCollection itemcol = procedureAPI.getStageTpDoc(currentState
				.getStagePcdId());
		IItem tpDoc = null;
		if (itemcol.next()) {
			while (itemcol.next() && tpDoc == null) {
				IItem item = itemcol.value();
				if (StringUtils.equals(COD_TP_DOC, item
						.getString("CT_TPDOC:COD_TPDOC"))) {
					tpDoc = item;
				}
			}
		}
		if (tpDoc == null) {
			throw new ISPACInfo(getResources(request).getMessage(
					"localgis.tpDoc.noExist"), false);
		}
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();
	   String urlMapa =(String) request.getSession().getAttribute("urlMapa");

	   	URL url = new URL(urlMapa);
		String urlPlano=FileTemporaryManager.getInstance().getFileTemporaryPath()+ "/plano.png";
		FileOutputStream fos = new FileOutputStream(urlPlano);
		FileUtils.copy(url.openStream(), fos);
		fos.flush();
		fos.close();
		String sMimeType = MimetypeMapping.getMimeType("png");
		InputStream in = new FileInputStream(urlPlano);
		File f =new File(urlMapa);

		Object connectorSession = genDocAPI.createConnectorSession();

		IItem document = genDocAPI.createStageDocument(currentState
				.getStageId(), tpDoc.getInt("CT_TPDOC:ID"), currentState
				.getEntityId(), currentState.getEntityRegId());

		genDocAPI.attachStageInputStream(connectorSession, currentState
				.getStageId(), document.getInt("ID"), in,
				(int) f.length(),sMimeType, "DOC-GIS");
		document.set("EXTENSION",sMimeType);
		document.set("DESCRIPCION", "DOC-GIS");
		document.store(cct);
		if (logger.isDebugEnabled()) {
			logger.debug("Fin storePlano ");
		}

		throw new ISPACInfo(getResources(request).getMessage(
		"localgis.plano.anexado"), false);

	}

	public ActionForward setSubstitute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		// Salva en la petición el bean del sustituto
		// Código del sustituto
		String valor = request.getParameter("value");
		// sustituto
		String sustituto = request.getParameter("sustitute");
		request.setAttribute("Substitute", new ItemBean(generateDatosItem(
				valor, sustituto)));
		return mapping.findForward("setSubstitute");
	}

	private List getListaPlanos(String idMunicipio) throws SigemException,
			ISPACException {
		ArrayList lista = new ArrayList();
		 Mapas mapas = new Mapas();
		 mapas.setMapas(new ArrayList());
		 getServicioGeoLocalizacion();
		 try {
		 //mapas = servicioGeoLocalizacion.verPlanosPublicados(Integer.parseInt(idMunicipio));
		   mapas =servicioGeoLocalizacion.verPlanosPublicados(Integer.parseInt(ID_ENTIDAD));
		 if (mapas == null) {
		 mapas = new Mapas();
		 mapas.setMapas(new ArrayList());
		 }
		 } catch (GeoLocalizacionServicioException e) {
		 if (logger.isDebugEnabled()) {
		 logger
		 .debug("getListaPlanos-->No se ha encontrado ningún mapa para el municipio con id:"
		 + idMunicipio);
		 }
		 }

		 int i = 0;
		 for (i = 0; i < mapas.getMapas().size(); i++) {
		 Mapa mapa = (Mapa) mapas.getMapas().get(i);
		 lista.add(generateDatosItem(mapa.getMapidgeopista() + "", mapa
		 .getName()));
		 }

		List list = CollectionBean.getBeanList(new ListCollection(lista));
		return list;
	}

	private IItem generateDatosItem(String valor, String sustituto)
			throws ISPACException {

		Properties properties = getProperties();
		IItem item = new GenericItem(properties, "ID");
		item.set("ID", valor);
		item.set("VALOR", valor);
		item.set("SUSTITUTO", sustituto);

		return item;
	}

	@SuppressWarnings("unchecked")
	private Properties getProperties() {

		int ordinal = 0;
		Properties properties = new Properties();
		properties.add(new Property(ordinal++, "ID", Types.VARCHAR));

		properties.add(new Property(ordinal++, "VALOR", Types.VARCHAR));
		properties.add(new Property(ordinal++, "SUSTITUTO", Types.VARCHAR));

		return properties;
	}

	private void getServicioGeoLocalizacion() throws SigemException {
		if (servicioGeoLocalizacion == null) {
			servicioGeoLocalizacion = LocalizadorServicios
					.getServicioGeoLocalizacion();
		}

	}

}
