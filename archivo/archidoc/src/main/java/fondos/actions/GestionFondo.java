package fondos.actions;

import common.ConfigConstants;
import fondos.model.ElementoCuadroClasificacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.TreeModelItem;
import util.TreeNode;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ListaProductores;

import common.Constants;
import common.MultiEntityConstants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ConfigException;
import common.model.PaisesRI;
import common.model.PaisesRIFactory;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.view.ErrorKeys;
import common.vos.PaisVO;

import descripcion.vos.DescriptorVO;
import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.FondoForm;
import fondos.model.Fondo;
import fondos.model.TipoEntidad;
import fondos.model.TipoNivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.ArchivoVO;

public class GestionFondo extends BaseAction {

	public void pantallanuevofondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			removeInTemporalSession(request, FondosConstants.FONDO_KEY);

			FondoForm frm = (FondoForm) form;
			AppUser appUser = getAppUser(request);
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(appUser));
			GestionCuadroClasificacionBI cuadroService = services
					.lookupGestionCuadroClasificacionBI();

			/*
			 * List listaPosiblesNiveles = cuadroService.getNivelesCF(
			 * frm.getIdnivelpadre(), TipoNivelCF.FONDO.getIdentificador());
			 */

			List listaPosiblesNiveles = cuadroService.getNivelesByTipo(
					TipoNivelCF.FONDO, frm.getIdnivelpadre());

			if ((listaPosiblesNiveles != null)
					&& (listaPosiblesNiveles.size() > 0)) {
				INivelCFVO primerFondo = (INivelCFVO) listaPosiblesNiveles
						.get(0);
				frm.setIdnivelacrear(primerFondo.getId());
			}

			GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
			List listaArchivos = sistemaBI.getArchivos();

			// Lista de paises
			Locale locale = request.getLocale();
			PaisesRI paisesRI = PaisesRIFactory.createPaisesRI(
					services.lookupInfoSistemaBI(), locale);
			List listaPaises = paisesRI.getAllPaises();

			List listaComunidades = null;
			if (listaPaises != null && listaPaises.size() > 0) {
				PaisVO unPais = (PaisVO) listaPaises.get(0);
				frm.setCodigopais(unPais.getCodigo());
				// if (listaPaises.size() == 1)
				listaComunidades = unPais.getComunidades();
			}
			// tipo productora (institucion por defecto)
			if (frm.getTipoEntidad() == 0) {
				frm.setTipoEntidad(TipoEntidad.INSTITUCION.getIdentificador());
				frm.setInstitucionEnSistemaExterno(false);
			}

			ElementoCuadroClasificacionVO clasificadorFondos = cuadroService
					.getElementoCuadroClasificacion(frm.getIdclpadre());

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_CREAR_FONDO, request);
			invocation.setAsReturnPoint(true);

			setInTemporalSession(request,
					FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
					listaPosiblesNiveles);
			setInTemporalSession(request, FondosConstants.LISTA_PAISES_KEY,
					listaPaises);
			setInTemporalSession(request,
					FondosConstants.LISTA_COMUNIDADES_KEY, listaComunidades);
			setInTemporalSession(request, FondosConstants.LISTA_ARCHIVOS_KEY,
					listaArchivos);

			// padre del fondo para mostrar el contexto
			setInTemporalSession(request, FondosConstants.PADRE_ELEMENTO_CF,
					clasificadorFondos);

			setReturnActionFordward(request,
					mappings.findForward("edicion_fondo"));
		} catch (ConfigException cfgex) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GENERAL_MESSAGE, cfgex.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	public void seleccionarPaisExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		Locale locale = request.getLocale();
		PaisesRI paisesRI = PaisesRIFactory.createPaisesRI(
				services.lookupInfoSistemaBI(), locale);
		FondoForm frm = (FondoForm) form;

		PaisVO unPais = paisesRI.getPaisXId(frm.getCodigopais());
		List listaComunidades = unPais.getComunidades();
		setInTemporalSession(request, FondosConstants.LISTA_COMUNIDADES_KEY,
				listaComunidades);
		setReturnActionFordward(request, mappings.findForward("edicion_fondo"));
	}

	public void buscarEntidadProductoraExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FondoForm frm = (FondoForm) form;
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		if (frm.isInstitucionEnSistemaExterno()) {
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

				GestorOrganismos gestorOrganismos = GestorOrganismosFactory
						.getConnectorById(config.getConfiguracionFondos()
								.getIdSistGestorOrg(), params);

				List listaInstituciones = gestorOrganismos
						.recuperarInstitucionesProductoras();
				CollectionUtils.transform(listaInstituciones,
						new Transformer() {
							public Object transform(Object obj) {
								InfoOrgano institucion = (InfoOrgano) obj;
								return new DefaultMapEntry(institucion.getId(),
										institucion.getNombre());
							}
						});
				request.setAttribute(FondosConstants.LISTA_ENTIDADES,
						listaInstituciones);
			} catch (GestorOrganismosException goe) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			} catch (NotAvailableException nae) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			}

		} else {
			int tipoEntidad = frm.getTipoEntidad();
			ListaProductores listaDescriptora = config
					.getConfiguracionGeneral().getListaDescriptoresEntidad(
							tipoEntidad);
			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			List listaPosiblesEntidades = descripcionBI.findDescriptores(
					listaDescriptora.getId(), frm.getTokenBusquedaEntidad());
			CollectionUtils.transform(listaPosiblesEntidades,
					new Transformer() {
						public Object transform(Object obj) {
							DescriptorVO descriptor = (DescriptorVO) obj;
							return new DefaultMapEntry(descriptor.getId(),
									descriptor.getNombre());
						}
					});
			request.setAttribute(FondosConstants.LISTA_ENTIDADES,
					listaPosiblesEntidades);
		}

		setReturnActionFordward(request, mappings.findForward("edicion_fondo"));
	}

	public void guardarFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			ServiceRepository services = getServiceRepository(request);
			GestionFondosBI fondosService = services.lookupGestionFondosBI();
			GestionArchivosBI archivosService = services
					.lookupGestionArchivosBI();

			FondoForm frm = (FondoForm) form;

			ActionErrors errors = frm.validate(request.getLocale());
			if (errors == null) {
				FondoVO fondo = null;
				ActionRedirect vistaFondo = null;
				CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
						request, FondosConstants.CUADRO_CLF_VIEW_NAME);
				TreeModelItem elementoPadre = null;

				if(treeView != null){
					TreeNode nodoSeleccionado = treeView.getSelectedNode();

					if(nodoSeleccionado != null){
						elementoPadre = nodoSeleccionado.getModelItem();
					}
					else{
						elementoPadre = new ElementoCuadroClasificacion();
					}
				}

				String idArchivo = null;
				ArchivoVO archivo = null;
				if (StringUtils.isNotBlank(frm.getCodigoarchivo())) {
					archivo = archivosService.getArchivosXCodArchivo(frm
							.getCodigoarchivo());
					if (archivo != null)
						idArchivo = archivo.getId();
				}

				if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
					if (StringUtils.isEmpty(frm.getCodOrdenacion())) {
						frm.setCodOrdenacion(frm.getCodigo());
					}
				}

				if (StringUtils.isBlank(frm.getIdFondo())) {
					fondo = new Fondo();
					fondo.setCodigo(frm.getCodigo());
					fondo.setDenominacion(frm.getDenominacion());
					fondo.setCodPais(frm.getCodigopais());
					fondo.setCodComunidad(frm.getCodigoautonomia());
					fondo.setCodArchivo(frm.getCodigoarchivo());
					fondo.setIdNivel(frm.getIdnivelacrear());
					fondo.setIdPadre(frm.getIdclpadre());
					fondo.setIdArchivo(idArchivo);
					fondo.setOrdPos(frm.getCodOrdenacion());

					if (frm.isInstitucionEnSistemaExterno()){
						fondo = fondosService.guardarFondo(fondo,
								frm.getIdInstitucionEnSistemaExterno());
					}
					else{
						fondo = fondosService.guardarFondo(fondo,
								frm.getTipoEntidad(),
								frm.getIdDescriptorEntidadProductora());
					}

					TreeNode addedNode = treeView.itemAdded(elementoPadre,
							(TreeModelItem) fondo);
					treeView.setSelectedNode(addedNode);

					vistaFondo = new ActionRedirect(
							mappings.findForward("forwardVistaFondo"));
					vistaFondo.addParameter("idelementocf", fondo.getId());
					vistaFondo.addParameter("refreshView", "true");
					vistaFondo.setRedirect(true);
					setReturnActionFordward(request, vistaFondo);
				} else {
					fondo = (FondoVO) getFromTemporalSession(request,
							FondosConstants.FONDO_KEY);
					fondo.setCodigo(frm.getCodigo());
					// Añadido porque no mostraba el nombre
					// fondo.setCodArchivo(frm.getCodigoarchivo());
					fondo.setDenominacion(frm.getDenominacion());
					fondo.setCodArchivo(frm.getCodigoarchivo());
					fondo.setIdArchivo(idArchivo);
					fondo.setOrdPos(frm.getCodOrdenacion());
					if (frm.isInstitucionEnSistemaExterno())
						fondo = fondosService.guardarFondo(fondo,
								frm.getIdInstitucionEnSistemaExterno());
					else
						fondo = fondosService.guardarFondo(fondo,
								frm.getTipoEntidad(),
								frm.getIdDescriptorEntidadProductora());
					TreeNode nodoSeleccionado = treeView.getSelectedNode();
					ElementoCuadroClasificacionVO fondoEnTreeView = (ElementoCuadroClasificacionVO) nodoSeleccionado
							.getModelItem();
					fondoEnTreeView.setCodigo(fondo.getCodigo());
					fondoEnTreeView.setTitulo(fondo.getDenominacion());
					fondoEnTreeView.setCodReferencia(fondo.getCodReferencia());
					fondoEnTreeView.setCodRefFondo(fondo.getCodRefFondo());
					// fondoEnTreeView.setNombreArchivo(fondo.getNombreArchivo());
					fondoEnTreeView.setIdArchivo(idArchivo);
					vistaFondo = new ActionRedirect(
							mappings.findForward("refreshView"), true);
					vistaFondo.addParameter("node",
							nodoSeleccionado.getNodePath(), false);
					vistaFondo.addParameter("refreshView", "true");
					setReturnActionFordward(request, vistaFondo);
				}
			} else {
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mappings.findForward("edicion_fondo"));
			}

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_fondo"));
		} catch (GestorOrganismosException goe) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
		}
	}

	public void guardarInfoFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionFondosBI fondosService = services.lookupGestionFondosBI();

		FondoForm fondoForm = (FondoForm) form;

		FondoPO fondo = (FondoPO) getFromTemporalSession(request,
				FondosConstants.FONDO_KEY);

		if (fondo != null) {
			fondo.setNivelAcceso(fondoForm.getNivelAcceso());
			fondo.setIdLCA(fondoForm.getIdLCA());

			fondosService.updateInfoAccesoElemento(fondo.getId(),
					fondo.getNivelAcceso(), fondo.getIdLCA());
		}

		goBackExecuteLogic(mappings, form, request, response);
	}

	public void verClasificadorFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
		String idClasificadorFondos = request.getParameter("idelementocf");
		ElementoCuadroClasificacionVO elementoCF = bi
				.getElementoCuadroClasificacion(idClasificadorFondos);

		request.setAttribute(FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
				bi.getTiposSubniveles(elementoCF.getIdNivel()));

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		setInTemporalSession(request, FondosConstants.LISTA_SUBNIVELES_KEY,
				bi.getNivelesCF(elementoCF.getIdNivel()));

		setReturnActionFordward(request,
				mappings.findForward("view_clasificadorFondos"));

		List listaDescendientes = bi
				.getHijosElementoCuadroClasificacion(elementoCF.getId());
		request.setAttribute(FondosConstants.LISTA_ELEMENTOS_CF,
				listaDescendientes);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request)
				.setTitleNavigationToolBar(
						TitlesToolBar.CUADRO_VER_CLASIFICADOR_FONDO);

		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
				elementoCF);
	}

	public void verfondoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FondoForm frm = (FondoForm) form;

		if (!puedeAccederUsuarioAElemento(request, frm.getIdelementocf())) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		ServiceRepository services = getServiceRepository(request);
		GestionFondosBI fondosService = services.lookupGestionFondosBI();
		GestionCuadroClasificacionBI cuadroService = services
				.lookupGestionCuadroClasificacionBI();
		FondoVO fondo = fondosService.abrirFondo(frm.getIdelementocf());

		try {
			FondoPO fondoPO = (FondoPO) FondoToPOTransformer.getInstance(
					services).transform(fondo, getAppUser(request));

			request.setAttribute(FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
					cuadroService.getTiposSubniveles(fondo.getIdNivel()));

			if (Boolean.valueOf(request.getParameter("refreshView"))
					.booleanValue())
				request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);

			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request)
					.setTitleNavigationToolBar(TitlesToolBar.CUADRO_VER_FONDO);

			setInTemporalSession(request, FondosConstants.FONDO_KEY, fondoPO);
			setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
					fondo);
			setInTemporalSession(request, FondosConstants.LISTA_SUBNIVELES_KEY,
					cuadroService.getNivelesCF(fondo.getIdNivel()));

			ElementoCuadroClasificacionVO padreClasificador = cuadroService
					.getElementoCuadroClasificacion(fondo.getIdPadre());

			List jerarquiaElementoCF = null;
			if (padreClasificador != null)
				jerarquiaElementoCF = cuadroService.getAncestors(fondo.getId());
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					jerarquiaElementoCF);

			setReturnActionFordward(request, mappings.findForward("ver_fondo"));
		} catch (ConfigException cfgex) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_GENERAL_MESSAGE, cfgex.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// FondoForm frm = (FondoForm) form;

		FondoVO fondo = (FondoVO) getFromTemporalSession(request,
				FondosConstants.FONDO_KEY);

		GestionFondosBI fondosService = getGestionFondosBI(request);
		TreeNode nodoSeleccionado = null;
		CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
				request, FondosConstants.CUADRO_CLF_VIEW_NAME);

		try {
			fondosService.removeFondo(fondo.getId());

			List list = (List) getFromTemporalSession(request,
					FondosConstants.LISTA_ELEMENTOS_CF);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					ElementoCuadroClasificacionVO elemento = (ElementoCuadroClasificacionVO) list
							.get(i);
					if (elemento.getIdFondo().equalsIgnoreCase(fondo.getId())) {
						list.remove(i);
						break;
					}
				}
			}
			// borrado del nodo del arbol
			nodoSeleccionado = treeView.getSelectedNode();
			treeView.setSelectedNode(nodoSeleccionado.getParent());
			treeView.removeNode(nodoSeleccionado);

			ActionRedirect result = new ActionRedirect(
					mappings.findForward("refreshView"), true);
			result.addParameter("refreshView", "true");
			nodoSeleccionado = treeView.getSelectedNode();
			if (nodoSeleccionado != null)
				result.addParameter("node", nodoSeleccionado.getNodePath(),
						false);

			setReturnActionFordward(request, result);

		} catch (FondosOperacionNoPermitidaException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);

		FondoPO fondo = (FondoPO) getFromTemporalSession(request,
				FondosConstants.FONDO_KEY);

		if (!checkPermisosSobreElemento(request, fondo.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		FondoForm frm = (FondoForm) form;
		frm.fillForm(fondo, fondo.getEntidadProductora());

		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		List listaArchivos = null;
		if (fondo.getPermitidoModificarArchivo()) {
			listaArchivos = sistemaBI.getArchivos();
		} else {
			listaArchivos = new ArrayList();
			listaArchivos
					.add(sistemaBI.getArchivoXCodigo(fondo.getCodArchivo()));
		}

		Locale locale = request.getLocale();
		PaisesRI paisesRI = PaisesRIFactory.createPaisesRI(
				services.lookupInfoSistemaBI(), locale);
		List listaPaises = paisesRI.getAllPaises();

		List listaComunidades = null;
		if (listaPaises != null && listaPaises.size() > 0) {
			PaisVO unPais = (PaisVO) listaPaises.get(0);
			frm.setCodigopais(unPais.getCodigo());
			if (listaPaises.size() == 1)
				listaComunidades = unPais.getComunidades();
		}

		// lista de tipos de fondos
		GestionCuadroClasificacionBI cuadroService = services
				.lookupGestionCuadroClasificacionBI();

		ElementoCuadroClasificacionVO elementoPadre = cuadroService.getElementoCuadroClasificacion(
				fondo.getIdPadre());

		String nivelPadre = null;
		if(elementoPadre != null){
			 nivelPadre = elementoPadre.getIdNivel();
		}

		Collection nivelesFondo = cuadroService.getNivelesByTipo(
				TipoNivelCF.FONDO, nivelPadre);

		if ((nivelesFondo != null) && (nivelesFondo.size() > 0)) {
			INivelCFVO primerFondo = (INivelCFVO) nivelesFondo.iterator()
					.next();
			frm.setIdnivelacrear(primerFondo.getId());
		}

		ElementoCuadroClasificacionVO clasificadorFondos = cuadroService
				.getElementoCuadroClasificacion(fondo.getIdPadre());

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_EDITAR_FONDO, request);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request, FondosConstants.FONDO_KEY, fondo);
		setInTemporalSession(request, FondosConstants.LISTA_ARCHIVOS_KEY,
				listaArchivos);
		setInTemporalSession(request, FondosConstants.LISTA_PAISES_KEY,
				listaPaises);
		setInTemporalSession(request, FondosConstants.LISTA_COMUNIDADES_KEY,
				listaComunidades);
		setInTemporalSession(request, FondosConstants.LISTA_FONDOS_KEY,
				nivelesFondo);
		// padre del fondo para mostrar el contexto
		setInTemporalSession(request, FondosConstants.PADRE_ELEMENTO_CF,
				clasificadorFondos);

		setReturnActionFordward(request, mappings.findForward("edicion_fondo"));
	}

	public void editarInfoFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuariosBI = services
				.lookupGestionControlUsuariosBI();

		FondoPO fondo = (FondoPO) getFromTemporalSession(request,
				FondosConstants.FONDO_KEY);

		if (!checkPermisosSobreElemento(request, fondo.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		if (fondo != null) {

			FondoForm fondoForm = (FondoForm) form;
			fondoForm.setNivelAcceso(fondo.getNivelAcceso());
			fondoForm.setIdLCA(fondo.getIdLCA());

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_EDITAR_FONDO, request);
			invocation.setAsReturnPoint(true);

			request.setAttribute(
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
					usuariosBI
							.getListasControlAccesoByTipo(TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

			request.setAttribute(FondosConstants.FONDO_KEY, fondo);

			setReturnActionFordward(request,
					mappings.findForward("edicion_info_fondo"));

		} else
			goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void cambiarEstadoVigenciaFondoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			FondoVO fondo = (FondoVO) getFromTemporalSession(request,
					FondosConstants.FONDO_KEY);
			ServiceRepository services = getServiceRepository(request);
			GestionFondosBI fondoBI = services.lookupGestionFondosBI();
			fondoBI.setEstadoVigencia(fondo, !fondo.isVigente());

		} catch (ActionNotAllowedException cnve) {
			guardarError(request, cnve);

		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void seleccionarNuevoPadreExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String pIdFondo = request.getParameter("idFondo");
		seleccionarNuevoPadreComunLogic(mappings, form, request, response,
				pIdFondo);

	}

	private void seleccionarNuevoPadreComunLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String idFondo) {

		removeInTemporalSession(request,
				FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		String pIdFondo = idFondo;
		ServiceRepository services = getServiceRepository(request);
		GestionFondosBI fondosBI = services.lookupGestionFondosBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		final FondoVO fondo = (FondoPO) FondoToPOTransformer.getInstance(
				services).transform(fondosBI.getFondoXId(pIdFondo));
		INivelCFVO nivelFondo = cuadroBI.getNivelCF(fondo.getIdNivel());
		GestionCuadroClasificacionBI cfBI = services
				.lookupGestionCuadroClasificacionBI();
		List posiblesNivelesPadre = cfBI.getNivelesPadre(nivelFondo.getId(),
				nivelFondo.getTipo());
		CollectionUtils.transform(posiblesNivelesPadre, new Transformer() {
			public Object transform(Object arg0) {
				return ((INivelCFVO) arg0).getId();
			}
		});

		String[] nivelesID = (String[]) posiblesNivelesPadre
				.toArray(new String[0]);
		List clasificadoresFondo = cfBI.getElementosCuadroClasificacionXNivel(
				nivelesID, null);
		CollectionUtils.filter(clasificadoresFondo, new Predicate() {
			public boolean evaluate(Object arg0) {
				return !((ElementoCuadroClasificacionVO) arg0).getId().equals(
						fondo.getIdPadre());
			}
		});

		request.setAttribute(FondosConstants.FONDO_KEY, fondo);
		request.setAttribute(FondosConstants.ENT_PRODUCTORA_KEY, fondosBI
				.getEntidadProductoraXIdDescr(fondo.getIdEntProductora()));
		request.setAttribute(FondosConstants.LISTA_ELEMENTOS_CF,
				clasificadoresFondo);
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_MOVER_FONDO,
				request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_clasificador_fondos"));
	}

	public void accionSeleccionarNuevoPadreExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (getServiceClient(request).hasPermission(
				AppPermissions.MODIFICACION_CUADRO_CLASIFICACION)) {
			String[] ids = (String[]) getFromTemporalSession(request,
					FondosConstants.ACCION_ELEMENTOS_KEY);
			seleccionarNuevoPadreComunLogic(mappings, form, request, response,
					ids[0]);
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void moverFondoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pIdFondo = request.getParameter("idFondo");
		String pNuevoPadre = request.getParameter("clasificadorSeleccionado");
		GestionFondosBI fondosBI = getGestionFondosBI(request);

		Boolean movimientoFinalizado = (Boolean) getFromTemporalSession(
				request, FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		if (movimientoFinalizado == null) {
			movimientoFinalizado = new Boolean(false);
		}

		if (!movimientoFinalizado.booleanValue()) {
			ActionErrors errors = null;
			try {
				errors = validateMoverFondo(pIdFondo, pNuevoPadre);

				if (errors == null) {

					FondoVO fondo = fondosBI.getFondoXId(pIdFondo);
					FondoPO fondoPO = new FondoPO(fondo,
							getServiceRepository(request));
					setInTemporalSession(request,
							FondosConstants.FONDO_ORIGEN_KEY, fondoPO);

					fondosBI.moverFondo(fondo, pNuevoPadre);

					setInTemporalSession(request,
							FondosConstants.MOVIMIENTO_FINALIZADO_KEY,
							new Boolean(true));

					FondoVO fondoDestino = fondosBI.getFondoXId(pIdFondo);
					FondoPO fondoDestinoPO = new FondoPO(fondoDestino,
							getServiceRepository(request));
					setInTemporalSession(request,
							FondosConstants.FONDO_DESTINO_KEY, fondoDestinoPO);

					CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
							request, FondosConstants.CUADRO_CLF_VIEW_NAME);

					if (treeView != null) {
						GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);

						TreeModelItem nuevoPadre = (TreeModelItem) cfBI
								.getElementoCuadroClasificacion(pNuevoPadre);

						// Corregido - Alicia
						/*********************************/
						// TreeNode nodeNuevoPadre =
						// treeView.findNode(nuevoPadre);
						TreeNode nodeNuevoPadre = treeView.getNode(treeView
								.getTreeModel().calculateItemPath(nuevoPadre));
						/*********************************/

						TreeNode nodeFondo = treeView.getSelectedNode();
						treeView.removeNode(nodeFondo);
						nodeFondo = treeView.insertNode(nodeNuevoPadre,
								(TreeModelItem) fondo);
						treeView.setSelectedNode(nodeFondo);
					}
					request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					setReturnActionFordward(request,
							mappings.findForward("informe_mover"));
					// setReturnActionFordward(request, result);

				} else {
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				}

			} catch (FondosOperacionNoPermitidaException re) {
				guardarError(request, re);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			setReturnActionFordward(request,
					mappings.findForward("informe_mover"));
		}
	}

	private ActionErrors validateMoverFondo(String idFondo, String nuevoPadre) {
		ActionErrors errors = new ActionErrors();
		if (nuevoPadre == null)
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.SELECCIONAR_NUEVO_PADRE_PARA_EL_FONDO));

		return errors.size() > 0 ? errors : null;
	}

}