package transferencias.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.geograficos.ElementoGeograficoComparator;
import se.geograficos.GestorGeograficos;
import se.geograficos.GestorGeograficosFactory;
import se.geograficos.Municipio;
import se.geograficos.Pais;
import se.geograficos.Provincia;
import se.geograficos.exceptions.GestorGeograficosException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.EmplazamientoForm;
import transferencias.vos.UnidadDocumentalVO;

import common.ConfigConstantsDescripcionSistemasExternos;
import common.Constants;
import common.MultiEntityConstants;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;
import common.vos.DatosGeograficosVO;
import common.vos.Direccion;

import configuracion.bi.GestionInfoSistemaBI;

/**
 * Action que maneja las acciones que pueden ser invocadas para la incorporacion
 * de los emplazamientos asociados a una unidad documental
 * 
 */
public class ComposicionEmplazamientoAction extends BaseAction {

	static Logger logger = Logger
			.getLogger(ComposicionEmplazamientoAction.class);

	/**
	 * Permite guardar los valores necesarios para crear un geografico en la
	 * ficha
	 * 
	 * @param request
	 *            Petición actual
	 * @param form
	 *            Formulario de la petición
	 */
	private void leerReferenciasDescripcion(HttpServletRequest request,
			EmplazamientoForm form) {

		// Guardar los valores de los campos para seleccionar el geografico
		ConfigConstantsDescripcionSistemasExternos ccdse = ConfigConstantsDescripcionSistemasExternos
				.getInstance();
		Map mapIdRefLists = ConfigConstantsDescripcionSistemasExternos
				.getInstance().processRefLine(form.getRef());

		// Campo Pais
		String pais = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_PAIS);
		if (StringUtils.isNotEmpty(pais)) {
			Map map = (Map) mapIdRefLists.get(pais);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefPais(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasPais(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypePais(tipoReferencia);
				}
			}
		}

		// Campo Provincia
		String provincia = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_PROVINCIA);
		if (StringUtils.isNotEmpty(provincia)) {
			Map map = (Map) mapIdRefLists.get(provincia);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefProvincia(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasProvincia(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypeProvincia(tipoReferencia);
				}
			}
		}

		// Campo Concejo
		String concejo = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_MUNICIPIO);
		if (StringUtils.isNotEmpty(concejo)) {
			Map map = (Map) mapIdRefLists.get(concejo);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefConcejo(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasConcejo(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypeConcejo(tipoReferencia);
				}
			}
		}

		// Campo Poblacion
		String poblacion = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_POBLACION);
		if (StringUtils.isNotEmpty(poblacion)) {
			Map map = (Map) mapIdRefLists.get(poblacion);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefPoblacion(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasPoblacion(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypePoblacion(tipoReferencia);
				}
			}
		}

		// Campo Localizacion
		String localizacion = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_LOCALIZACION);
		if (StringUtils.isNotEmpty(localizacion)) {
			Map map = (Map) mapIdRefLists.get(localizacion);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefLocalizacion(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasLocalizacion(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypeLocalizacion(tipoReferencia);
				}
			}
		}

		// Campo Validado
		String validado = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_VALIDADO);
		if (StringUtils.isNotEmpty(validado)) {
			Map map = (Map) mapIdRefLists.get(validado);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefValidado(ref);
					String textSi = ccdse
							.getValorIndice(
									ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
									ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_VALIDADO_INDICE_SI);
					form.setValidadoTextSi(textSi);
					String textNo = ccdse
							.getValorIndice(
									ConfigConstantsDescripcionSistemasExternos.SISTEMA_GEOGRAFICOS,
									ConfigConstantsDescripcionSistemasExternos.CAMPO_GEOGRAFICOS_VALIDADO_INDICE_NO);
					form.setValidadoTextNo(textNo);
				}
			}
		}
	}

	public void nuevoEmplazamientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EmplazamientoForm frm = (EmplazamientoForm) form;
		removeInTemporalSession(request,
				TransferenciasConstants.SOPORTA_BUSQUEDA_EXTENDIDA);
		try {
			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			setInTemporalSession(request,
					TransferenciasConstants.SOPORTA_BUSQUEDA_EXTENDIDA,
					new Boolean(gestorGeograficos.soportaBusquedaExtendida()));
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionInfoSistemaBI infoSistemaBI = services.lookupInfoSistemaBI();
		DatosGeograficosVO datosGeograficos = infoSistemaBI
				.getDatosGeograficosDefecto();

		if (datosGeograficos != null) {
			frm.setPais(datosGeograficos.getNombrePais());
			frm.setCodigoPais(datosGeograficos.getCodigoPais());
			frm.setProvincia(datosGeograficos.getNombreProvincia());
			frm.setCodigoProvincia(datosGeograficos.getCodigoProvincia());

			// Establecer los Valores por Defecto
			frm.setPaisDefecto(datosGeograficos.getNombrePais());
			frm.setCodigoPaisDefecto(datosGeograficos.getCodigoPais());
			frm.setProvinciaDefecto(datosGeograficos.getNombreProvincia());
			frm.setCodigoProvinciaDefecto(datosGeograficos.getCodigoProvincia());
		}

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EMPLAZAMIENTO, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento"));
	}

	public void nuevoEmplazamientoDescripcionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmplazamientoForm frm = (EmplazamientoForm) form;
		removeInTemporalSession(request,
				TransferenciasConstants.SOPORTA_BUSQUEDA_EXTENDIDA);
		try {
			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			setInTemporalSession(request,
					TransferenciasConstants.SOPORTA_BUSQUEDA_EXTENDIDA,
					new Boolean(gestorGeograficos.soportaBusquedaExtendida()));
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionInfoSistemaBI infoSistemaBI = services.lookupInfoSistemaBI();
		DatosGeograficosVO datosGeograficos = infoSistemaBI
				.getDatosGeograficosDefecto();

		frm.setPais(datosGeograficos.getNombrePais());
		frm.setCodigoPais(datosGeograficos.getCodigoPais());
		frm.setProvincia(datosGeograficos.getNombreProvincia());
		frm.setCodigoProvincia(datosGeograficos.getCodigoProvincia());

		// Establecer los Valores por Defecto
		frm.setPaisDefecto(datosGeograficos.getNombrePais());
		frm.setCodigoPaisDefecto(datosGeograficos.getCodigoPais());
		frm.setProvinciaDefecto(datosGeograficos.getNombreProvincia());
		frm.setCodigoProvinciaDefecto(datosGeograficos.getCodigoProvincia());

		frm.setContextPath(request.getContextPath());

		// Obtener las referencias
		leerReferenciasDescripcion(request, frm);

		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento_descripcion"));
	}

	public void editarEmplazamientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmplazamientoForm direccionForm = (EmplazamientoForm) form;
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		String paramNumEmplazamiento = request.getParameter("numEmplazamiento");
		if (udoc != null && paramNumEmplazamiento != null) {
			List emplazamientosUdoc = udoc.getEmplazamientos();
			Direccion emplazamiento = (Direccion) emplazamientosUdoc
					.get(Integer.parseInt(paramNumEmplazamiento) - 1);
			if (emplazamiento != null) {
				direccionForm.setMunicipio(emplazamiento.concejo);
				direccionForm.setPoblacion(emplazamiento.poblacion);
				direccionForm.setDireccion(emplazamiento.localizacion);
				saveCurrentInvocation(
						KeysClientsInvocations.TRANSFERENCIAS_EMPLAZAMIENTO,
						request);
				setInTemporalSession(request,
						TransferenciasConstants.EMPLAZAMIENTO, emplazamiento);
				setReturnActionFordward(request,
						mappings.findForward("edicion_emplazamiento"));
			} else
				setReturnActionFordward(request,
						mappings.findForward("info_udoc"));
		} else
			setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	/*
	 * private Provincia getProvinciaEmplazamientos() {
	 * ConfiguracionTransferencias config =
	 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo
	 * ().getConfiguracionTransferencias(); Map.Entry infoPais =
	 * config.getPais(); Map.Entry infoProvincia = config.getProvincia();
	 * Provincia provincia = new Provincia( (String)infoPais.getKey(),
	 * (String)infoProvincia.getKey(), (String)infoProvincia.getValue()); return
	 * provincia; }
	 */

	private void buscarPaisesComunLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// EmplazamientoForm direccionForm = (EmplazamientoForm) form;

			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			List paises = null;
			if (gestorGeograficos.soportaBusquedaExtendida()) {
				paises = gestorGeograficos.busquedaElementos(
						GestorGeograficos.PAIS, new HashMap(),
						request.getParameter("patternNombrePais"));
				Collections.sort(paises, ElementoGeograficoComparator
						.getInstance(GestorGeograficos.PAIS));
			} else {
				paises = gestorGeograficos.recuperarPaises(request
						.getParameter("patternNombrePais"));
			}
			request.setAttribute(TransferenciasConstants.PAISES, paises);
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}
	}

	public void buscarPaisesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarPaisesComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento"));
	}

	public void buscarPaisesDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		buscarPaisesComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento_descripcion"));
	}

	private void buscarProvinciasComunLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			EmplazamientoForm direccionForm = (EmplazamientoForm) form;

			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			List provincias = null;
			if (gestorGeograficos.soportaBusquedaExtendida()) {
				Map ids = new HashMap();
				ids.put(new Integer(GestorGeograficos.PAIS),
						direccionForm.getCodigoPais());
				provincias = gestorGeograficos.busquedaElementos(
						GestorGeograficos.PROVINCIA, ids,
						request.getParameter("patternNombreProvincia"));
				Collections.sort(provincias, ElementoGeograficoComparator
						.getInstance(GestorGeograficos.PROVINCIA));
			} else {
				Pais pais = new Pais(direccionForm.getCodigoPais(),
						direccionForm.getPais());

				provincias = gestorGeograficos.recuperarProvincias(pais,
						request.getParameter("patternNombreProvincia"));
			}
			request.setAttribute(TransferenciasConstants.PROVINCIAS, provincias);
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}
	}

	public void buscarProvinciasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarProvinciasComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento"));
	}

	public void buscarProvinciasDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		buscarProvinciasComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento_descripcion"));
	}

	private void buscarConcejoComunLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			EmplazamientoForm direccionForm = (EmplazamientoForm) form;
			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			List concejos = null;
			if (gestorGeograficos.soportaBusquedaExtendida()) {
				Map ids = new HashMap();
				ids.put(new Integer(GestorGeograficos.PAIS),
						direccionForm.getCodigoPais());
				ids.put(new Integer(GestorGeograficos.PROVINCIA),
						direccionForm.getCodigoProvincia());
				concejos = gestorGeograficos.busquedaElementos(
						GestorGeograficos.MUNICIPIO, ids,
						request.getParameter("patternNombreMunicipio"));
				Collections.sort(concejos, ElementoGeograficoComparator
						.getInstance(GestorGeograficos.MUNICIPIO));
			} else {
				Provincia provincia = new Provincia(
						direccionForm.getCodigoPais(),
						direccionForm.getCodigoProvincia(),
						direccionForm.getProvincia());
				concejos = gestorGeograficos.recuperarMunicipios(provincia,
						request.getParameter("patternNombreMunicipio"));

			}
			request.setAttribute(TransferenciasConstants.CONCEJOS, concejos);
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}
	}

	public void buscarConcejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarConcejoComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento"));
	}

	public void buscarConcejoDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		buscarConcejoComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento_descripcion"));
	}

	// public void buscarParroquiaExecuteLogic(ActionMapping mappings,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	// Map.Entry[] concejos = { new DefaultMapEntry("1", "Concejo 1"),
	// new DefaultMapEntry("2", "Concejo 2"), new DefaultMapEntry("1",
	// "Concejo 1"),
	// new DefaultMapEntry("2", "Concejo 2"), new DefaultMapEntry("1",
	// "Concejo 1"),
	// new DefaultMapEntry("2", "Concejo 2") };
	// request.setAttribute(TransferenciasConstants.PARROQUIAS, concejos);
	// setReturnActionFordward(request,
	// mappings.findForward("edicion_emplazamiento"));
	// }

	private void buscarPoblacionComunLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			EmplazamientoForm direccionForm = (EmplazamientoForm) form;

			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			GestorGeograficos gestorGeograficos = GestorGeograficosFactory
					.getConnector(params);
			List poblaciones = null;
			if (gestorGeograficos.soportaBusquedaExtendida()) {
				Map ids = new HashMap();
				ids.put(new Integer(GestorGeograficos.PAIS),
						direccionForm.getCodigoPais());
				ids.put(new Integer(GestorGeograficos.PROVINCIA),
						direccionForm.getCodigoProvincia());
				ids.put(new Integer(GestorGeograficos.MUNICIPIO),
						direccionForm.getCodigoMunicipio());
				poblaciones = gestorGeograficos.busquedaElementos(
						GestorGeograficos.POBLACION, ids,
						request.getParameter("patternNombrePoblacion"));
				Collections.sort(poblaciones, ElementoGeograficoComparator
						.getInstance(GestorGeograficos.POBLACION));
			} else {
				Municipio municipio = null;
				if (direccionForm.getCodigoMunicipio() != null
						&& !StringUtils.isEmpty(direccionForm.getMunicipio())) {
					Provincia provincia = new Provincia(
							direccionForm.getCodigoPais(),
							direccionForm.getCodigoProvincia(),
							direccionForm.getProvincia());
					municipio = new Municipio(provincia.getCodigoPais(),
							provincia.getCodigo(),
							direccionForm.getCodigoMunicipio(),
							direccionForm.getMunicipio());
				}
				poblaciones = gestorGeograficos.recuperarPoblaciones(municipio,
						request.getParameter("patternNombrePoblacion"));
				request.setAttribute(TransferenciasConstants.MUNICIPIO,
						municipio);
			}
			request.setAttribute(TransferenciasConstants.POBLACIONES,
					poblaciones);
		} catch (GestorGeograficosException gge) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_GEOGRAFICOS));
		}
	}

	public void buscarPoblacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarPoblacionComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento"));
	}

	public void buscarPoblacionDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		buscarPoblacionComunLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_emplazamiento_descripcion"));
	}

	public void guardarEmplazamientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmplazamientoForm direccionForm = (EmplazamientoForm) form;
		Direccion emplazamiento = (Direccion) getFromTemporalSession(request,
				TransferenciasConstants.EMPLAZAMIENTO);
		if (emplazamiento == null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);

			emplazamiento = new Direccion(direccionForm.getPais(),
					direccionForm.getProvincia(), direccionForm.getMunicipio(),
					direccionForm.getPoblacion(), direccionForm.getDireccion(),
					direccionForm.getValidado());
			udoc.addEmplazamiento(emplazamiento);
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		}
		popLastInvocation(request);
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	public void eliminarEmplazamientosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmplazamientoForm direccionForm = (EmplazamientoForm) form;
		String[] emplazamientosSeleccionados = direccionForm
				.getSeleccionEmplazamiento();
		if (emplazamientosSeleccionados != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List emplazamientos = udoc.getEmplazamientos();
			List emplazamientosToRemove = new ArrayList();
			for (int i = 0; i < emplazamientosSeleccionados.length; i++)
				emplazamientosToRemove.add(emplazamientos.get(Integer
						.parseInt(emplazamientosSeleccionados[i]) - 1));
			for (Iterator i = emplazamientosToRemove.iterator(); i.hasNext();)
				emplazamientos.remove(i.next());
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

}