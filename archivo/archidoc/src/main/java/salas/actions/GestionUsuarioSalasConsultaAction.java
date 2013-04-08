/**
 *
 */
package salas.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.form.UsuarioSalasConsultaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.ResumenServiciosVO;
import salas.vos.UsuarioArchivoSalasConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.usuarios.AppUser;
import se.usuarios.TipoUsuario;
import se.usuarios.exceptions.AppUserException;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Usuario;

import common.Messages;
import common.MultiEntityConstants;
import common.actions.ActionRedirect;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.CustomDateRange;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.KeyValueVO;

import deposito.global.Constants;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GestionUsuarioSalasConsultaAction extends SalasConsultaBaseAction {

	public void listadoUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_USUARIOS_CONSULTA, request);

		form.reset(mappings, request);
		invocation.setAsReturnPoint(true);

		List listaUsuarios = getGestionSalasBI(request).getUsuarios();

		request.setAttribute(SalasConsultaConstants.LISTA_USUARIOS_KEY,
				listaUsuarios);
		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO));
	}

	public void nuevoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_CREAR_USUARIO,
				request);
		form.reset(mappings, request);

		nuevoCodeLogic(mappings, form, request, response, null);
	}

	public void editarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_EDITAR_USUARIO,
				request);

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		try {
			colocarListaTipoDocumentos(request);
			colocarListaUsuarioExternos(request, form, true);
			UsuarioSalasConsultaVO usuario = getGestionSalasBI(request)
					.getUsuarioById(frm.getId(), true);
			if (salasBI.getRegistroConsultaSalaByUsuario(usuario.getId()) != null) {
				setInTemporalSession(request,
						SalasConsultaConstants.PERMITIR_MODIFICAR_USUARIO_KEY,
						Boolean.FALSE);
			}
			frm.set(usuario);
		} catch (SecurityException se) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
	}

	public void verExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		removeInTemporalSession(request,
				SalasConsultaConstants.USUARIO_ARCHIVO_KEY);
		try {
			UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

			String idUsuario = request
					.getParameter(SalasConsultaConstants.PARAM_ID_USUARIO);

			if (StringUtils.isEmpty(idUsuario)) {
				idUsuario = frm.getId();
			}

			if (StringUtils.isNotEmpty(idUsuario)) {

				GestionSalasConsultaBI bi = getGestionSalasBI(request);

				UsuarioSalasConsultaVO usuarioSalasConsultaVO = bi
						.getUsuarioById(idUsuario, true);

				if (usuarioSalasConsultaVO != null) {
					saveCurrentInvocation(
							KeysClientsInvocations.SALAS_VER_USUARIO, request);
					setInTemporalSession(request,
							SalasConsultaConstants.USUARIO_ARCHIVO_KEY,
							usuarioSalasConsultaVO);

					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("No se ha encontrado el objeto Usuario para el id:"
								+ idUsuario);
					}

					accionDatosElementoNoEncontrado(
							request,
							Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_USUARIO));

					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO));
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No se ha recibido el parámetro"
							+ SalasConsultaConstants.PARAM_ID_USUARIO);
				}

				accionDatosElementoNoEncontrado(
						request,
						Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_USUARIO));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		try {
			getGestionSalasBI(request).eliminarUsuario(frm.getId());
			List listaUsuarios = getGestionSalasBI(request).getUsuarios();
			request.setAttribute(SalasConsultaConstants.LISTA_USUARIOS_KEY,
					listaUsuarios);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SecurityException se) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
			UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

			ActionErrors errors = frm.validate(request);

			if (errors.isEmpty()) {
				UsuarioSalasConsultaVO usuarioSalaConsultaVO = new UsuarioSalasConsultaVO();
				frm.populate(usuarioSalaConsultaVO);

				if (StringUtils.isEmpty(usuarioSalaConsultaVO.getId())) {
					salasBI.insertarUsuario(usuarioSalaConsultaVO);
					popLastInvocation(request);

				} else {
					salasBI.actualizarUsuario(usuarioSalaConsultaVO);
				}
				removeInTemporalSession(request,
						SalasConsultaConstants.LISTA_ARCHIVOS_SELECCIONAR_KEY);
				forwardVerUsuario(mappings, request,
						usuarioSalaConsultaVO.getId());
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
			}
		} catch (SecurityException se) {
			accionSinPermisos(request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
		} catch (SalasConsultaException sce) {
			guardarError(request, sce);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
		}
	}

	public void desasociarArchivosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {

			UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

			getGestionSalasBI(request).desasociarArchivos(frm.getId(),
					frm.getIdsArchivo());

			forwardVerUsuario(mappings, request, frm.getId());
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SalasConsultaException ned) {
			guardarError(request, ned);
			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
		}

	}

	public void asociarArchivosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

			List listaArchivosSeleccionar = getListaAchivosUsuarioSeleccionar(
					request, frm.getId());

			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_ARCHIVOS_SELECCIONAR_KEY,
					listaArchivosSeleccionar);

			if (ListUtils.isEmpty(listaArchivosSeleccionar)) {
				ActionErrors errors = getErrors(request, true);
				errors.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionError(
								SalasConsultaConstants.ERROR_NO_HAY_ARCHIVOS_PARA_ANIADIR));
				ErrorsTag.saveErrors(request, errors);
			}

			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void aceptarAsociarArchivosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

			if (ArrayUtils.isNotEmpty(frm.getIdsArchivo())) {
				getGestionSalasBI(request).asociarArchivos(frm.getId(),
						frm.getIdsArchivo());
			}

			removeInTemporalSession(request,
					SalasConsultaConstants.LISTA_ARCHIVOS_SELECCIONAR_KEY);
			forwardVerUsuario(mappings, request, frm.getId());

		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void cancelarAsociarArchivosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		removeInTemporalSession(request,
				SalasConsultaConstants.LISTA_ARCHIVOS_SELECCIONAR_KEY);
		forwardVerUsuario(mappings, request, frm.getId());
	}

	public void cargarDatosUsuarioExternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		String idUsuarioExterno = frm.getIdscausr();

		if (!StringUtils.isEmpty(idUsuarioExterno)) {
			UsuarioVO usuarioVO = getGestionSalasBI(request).getDatosUsuario(
					idUsuarioExterno);

			if (usuarioVO != null) {
				frm.setNombre(usuarioVO.getNombre());
				frm.setApellidos(usuarioVO.getApellidos());
				frm.setDireccion(usuarioVO.getDireccion());
				frm.setEmail(usuarioVO.getEmail());

			}
		}

		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
	}

	public void cargarUsuariosExternosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		colocarListaUsuarioExternos(request, frm, true);

		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));

	}

	public void addUsuarioExternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveCurrentInvocation(
				KeysClientsInvocations.SALAS_ANIADIR_USUARIOS_EXTERNOS, request);

		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_ADD_USUARIO_EXTERNO));
	}

	public void realizarBusquedaUsuariosExternosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		ActionErrors errors = validateFormParaBuscar(frm);
		if (errors == null) {
			List tiposUsuario = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionControlAcceso().getUsuarios();
			Usuario tipoUsuarioSeleccionado = (Usuario) CollectionUtils.find(
					tiposUsuario, new FinderTipoUsuario(
							TipoUsuario.INVESTIGADOR));
			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS);
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

			AuthenticationConnector connector = AuthenticationConnectorFactory
					.getConnector(tipoUsuarioSeleccionado.getTipo(), params);
			List usuariosEncontrados = connector.findUserByName(frm
					.getNombreUsuarioABuscar());

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_USUARIOS_EXTERNOS,
					usuariosEncontrados);
		}

		if (errors != null)
			ErrorsTag.saveErrors(request, errors);

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_ADD_USUARIO_EXTERNO));
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_BUSCADOR_USUARIOS,
				request);

		colocarListaVacioSiNo(request);
		colocarListaTipoDocumentos(request);
		colocarListaArchivos(request);

		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCADOR));
	}

	public void buscarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		frm.populate(busquedaUsuarioSalaConsultaVO);
		if (StringUtils.isEmpty(frm.getIdArchivo())) {
			String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
			busquedaUsuarioSalaConsultaVO.setIdsArchivo(idsArchivo);
		}

		List listaUsuarios = getGestionSalasBI(request).buscarUsuarios(
				busquedaUsuarioSalaConsultaVO);
		request.setAttribute(SalasConsultaConstants.LISTA_USUARIOS_KEY,
				listaUsuarios);

		Boolean buscadorUsuarios = (Boolean) getFromTemporalSession(request,
				SalasConsultaConstants.BUSCADOR_USUARIOS_KEY);
		if (Boolean.TRUE.equals(buscadorUsuarios)) {
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO_USUARIOS));
		} else {
			popLastInvocation(request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO));
		}
	}

	public void seleccionarUsuarioExternoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		ActionErrors errors = validateCreacionUsuarioExterno(frm);
		if (errors.isEmpty()) {
			try {
				UsuarioVO usuarioVO = getGestionSalasBI(request)
						.crearUsuarioExterno(
								frm.getIdUsrSisExtGestorSeleccionado());
				frm.setIdscausr(usuarioVO.getId());
				frm.setAccesoAplicacion(Boolean.TRUE);

				List listaUsuariosExternos = (List) getFromTemporalSession(
						request,
						SalasConsultaConstants.LISTA_USUARIOS_EXTERNOS_KEY);
				if (listaUsuariosExternos == null) {
					listaUsuariosExternos = new ArrayList();
				}

				listaUsuariosExternos.add(usuarioVO);

				// colocarListaUsuarioExternos(request,form, true);
				popLastInvocation(request);
				frm.setIdUsrSisExtGestorSeleccionado(null);
				frm.setNombreUsuarioABuscar(null);

				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
			} catch (ActionNotAllowedException anae) {

				guardarError(request, anae);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_ADD_USUARIO_EXTERNO));

			} catch (AppUserException e) {
				errors = getErrors(request, true);
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						common.Constants.GLOBAL_ARCHIGEST_EXCEPTION, new Short(
								e.getErrorCode())));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_ADD_USUARIO_EXTERNO));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_ADD_USUARIO_EXTERNO));
		}
	}

	// METODOS PRIVADOS

	private void forwardVerUsuario(ActionMapping mappings,
			HttpServletRequest request, String idUsuario) {

		ActionRedirect vista = new ActionRedirect(
				mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_USUARIO_SALA_CONSULTA),
				true);
		vista.addParameter(SalasConsultaConstants.PARAM_ID_USUARIO, idUsuario);

		setReturnActionFordward(request, vista);
	}

	private List getListaAchivosUsuarioSeleccionar(HttpServletRequest request,
			String idUsuario) {

		List listaArchivosUsuarioConectado = getListaArchivosUsuarioConectado(request);

		List listaArchivosSeleccionar = new ArrayList();
		if (ListUtils.isNotEmpty(listaArchivosUsuarioConectado)) {
			for (Iterator iterator = listaArchivosUsuarioConectado.iterator(); iterator
					.hasNext();) {
				ArchivoVO archivoVO = (ArchivoVO) iterator.next();

				UsuarioArchivoSalasConsultaVO usuarioArchivo = new UsuarioArchivoSalasConsultaVO(
						idUsuario, archivoVO.getId(), archivoVO.getNombre());
				listaArchivosSeleccionar.add(usuarioArchivo);
			}
		}

		if (StringUtils.isNotEmpty(idUsuario)) {
			List listaArchivosUsuario = getGestionSalasBI(request)
					.getArchivosByIdUsuarioSalaConsultaInArchivos(idUsuario,
							null);
			listaArchivosSeleccionar.removeAll(listaArchivosUsuario);
		}

		return listaArchivosSeleccionar;
	}

	private void colocarListaUsuarioExternos(HttpServletRequest request,
			ActionForm form, boolean recargar) {

		List listaUsuariosExternos = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_EXTERNOS_KEY);

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		if (listaUsuariosExternos == null || recargar) {
			// listaUsuariosExternos = new ArrayList();

			List listaUsuarios = getGestionSalasBI(request)
					.getUsuariosExternos(frm.getFiltroNombreUsuarioExterno());

			// if(ListUtils.isNotEmpty(listaUsuarios)){
			// for (Iterator iterator = listaUsuarios.iterator(); iterator
			// .hasNext();) {
			// UsuarioVO usuario = (UsuarioVO) iterator.next();
			//
			// listaUsuariosExternos.add(new KeyValueVO(usuario.getId(),
			// usuario.getNombreCompleto()));
			// }
			// }
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_USUARIOS_EXTERNOS_KEY,
					listaUsuarios);
		}

	}

	private void colocarListaVacioSiNo(HttpServletRequest request) {
		List lista = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_VACIO_SI_NO);

		if (lista == null) {
			lista = new ArrayList();
			lista.add(new KeyValueVO(common.Constants.STRING_EMPTY,
					common.Constants.STRING_EMPTY));
			lista.add(new KeyValueVO(common.Constants.TRUE_STRING, Messages
					.getString(common.Constants.ETIQUETA_SI,
							request.getLocale())));
			lista.add(new KeyValueVO(common.Constants.FALSE_STRING, Messages
					.getString(common.Constants.ETIQUETA_NO,
							request.getLocale())));

			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_VACIO_SI_NO, lista);
		}

	}

	ActionErrors validateCreacionUsuarioExterno(UsuarioSalasConsultaForm frm) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(frm.getIdUsrSisExtGestorSeleccionado())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_SELECCIONAR_UN_USUARIO));
		}

		return errors;

	}

	ActionErrors validateFormParaBuscar(UsuarioSalasConsultaForm frm) {
		ActionErrors errors = null;
		if (StringUtils.isBlank(frm.getNombreUsuarioABuscar())) {
			errors = new ActionErrors();
			errors.add(
					ControlAccesoConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR,
					new ActionError(
							ControlAccesoConstants.ES_NECESARIO_INTRODUCIR_UN_VALOR_PARA_EL_NOMBRE_A_BUSCAR));
		}
		return errors;
	}

	private class FinderTipoUsuario implements Predicate {
		String tipoToFind = null;

		FinderTipoUsuario(String tipoToFind) {
			this.tipoToFind = tipoToFind;
		}

		public boolean evaluate(Object arg0) {
			return tipoToFind.equalsIgnoreCase(((Usuario) arg0).getTipo());
		}
	}

	private void nuevoCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			UsuarioVO usuarioVO) {

		List listaArchivosSeleccionar = getListaAchivosUsuarioSeleccionar(
				request, null);
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_ARCHIVOS_SELECCIONAR_KEY,
				listaArchivosSeleccionar);
		setInTemporalSession(request,
				SalasConsultaConstants.PERMITIR_MODIFICAR_USUARIO_KEY,
				Boolean.TRUE);

		colocarListaUsuarioExternos(request, form, true);
		colocarListaTipoDocumentos(request);
		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;

		frm.reset();
		frm.setVigente(Boolean.TRUE);
		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
	}

	public void busquedaUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		colocarListaVacioSiNo(request);
		colocarListaTipoDocumentos(request);
		colocarListaArchivos(request);

		setInTemporalSession(request,
				SalasConsultaConstants.INFORME_USUARIOS_KEY, Boolean.TRUE);
		saveCurrentInvocation(KeysClientsInvocations.SALAS_BUSCAR_USUARIOS,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_USUARIOS));
	}

	public void imprimirBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		frm.populate(busquedaUsuarioSalaConsultaVO);

		String idArchivo = frm.getIdArchivo();
		if (StringUtils.isEmpty(frm.getIdArchivo())) {
			String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
			busquedaUsuarioSalaConsultaVO.setIdsArchivo(idsArchivo);
		}

		List listaUsuarios = salasBI
				.buscarUsuariosPorArchivo(busquedaUsuarioSalaConsultaVO);

		if (StringUtils.isEmpty(idArchivo)) {
			busquedaUsuarioSalaConsultaVO.setIdsArchivo(null);
		}

		if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
			String formato = frm.getMapFormValues("fechaCompAlta");
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_KEY, formato);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_USUARIOS_CONSULTA_KEY,
					listaUsuarios);

			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY,
					busquedaUsuarioSalaConsultaVO);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_USUARIOS));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_USUARIOS));
		}
	}

	public void busquedaExpedientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_BUSCAR_EXPEDIENTES,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_EXPEDIENTES));
	}

	public void buscadorUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		colocarListaVacioSiNo(request);
		colocarListaTipoDocumentos(request);
		colocarListaArchivos(request);

		setInTemporalSession(request,
				SalasConsultaConstants.BUSCADOR_USUARIOS_KEY, Boolean.TRUE);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCADOR_USUARIOS));
	}

	public void imprimirBusquedaExpedientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		UsuarioSalasConsultaVO usuarioConsulta = null;
		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		if (StringUtils.isNotEmpty(frm.getId())) {
			busquedaUsuarioSalaConsultaVO.setId(frm.getId());
			busquedaUsuarioSalaConsultaVO.setNombre(frm.getNombre());
			usuarioConsulta = salasBI.getUsuarioById(frm.getId(), false);
		}

		CustomDateRange rangeExp = frm.getRangoFechaExp();
		if (rangeExp != null) {
			busquedaUsuarioSalaConsultaVO.setFechaIniExp(rangeExp
					.getInitialDate());
			busquedaUsuarioSalaConsultaVO.setFechaFinExp(rangeExp
					.getFinalDate());
		}

		List listaExpedientes = salasBI
				.getExpedientesUsuarioConsulta(busquedaUsuarioSalaConsultaVO);

		if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
			String formato = frm.getMapFormValues("fechaCompExp");
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_KEY, formato);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_EXPEDIENTES_KEY,
					listaExpedientes);
			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY,
					busquedaUsuarioSalaConsultaVO);
			setInTemporalSession(request,
					SalasConsultaConstants.USUARIO_CONSULTA_KEY,
					usuarioConsulta);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_EXPEDIENTES));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_EXPEDIENTES));
		}
	}

	public void resumenServiciosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_RESUMEN_SERVICIOS,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_RESUMEN_SERVICIOS));
	}

	public void imprimirResumenServiciosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		CustomDateRange rangeExp = frm.getRangoFechaExp();
		if (rangeExp != null) {
			busquedaUsuarioSalaConsultaVO.setFechaIniExp(rangeExp
					.getInitialDate());
			busquedaUsuarioSalaConsultaVO.setFechaFinExp(rangeExp
					.getFinalDate());
		}

		int numUsuarios = salasBI
				.getCountUsuariosConsulta(busquedaUsuarioSalaConsultaVO);
		int numRegistros = salasBI
				.getCountRegistros(busquedaUsuarioSalaConsultaVO);
		int numUnidades = salasBI
				.getCountUnidadesConsultadas(busquedaUsuarioSalaConsultaVO);

		ResumenServiciosVO resumenServicios = new ResumenServiciosVO(
				numUsuarios, numRegistros, numUnidades);

		if (resumenServicios != null) {
			String formato = frm.getMapFormValues("fechaCompExp");
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_KEY, formato);
			setInTemporalSession(request,
					SalasConsultaConstants.RESUMEN_SERVICIOS_KEY,
					resumenServicios);
			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY,
					busquedaUsuarioSalaConsultaVO);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_SERVICIOS));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_RESUMEN_SERVICIOS));
		}
	}

	public void temasInvestigacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.SALAS_TEMAS_INVESTIGACION,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_TEMAS_INVESTIGACION));
	}

	public void imprimirTemasInvestigacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		if (StringUtils.isNotEmpty(frm.getId())) {
			busquedaUsuarioSalaConsultaVO.setId(frm.getId());
			busquedaUsuarioSalaConsultaVO.setNombre(frm.getNombre());
		}

		List listaTemas = salasBI
				.getTemasUsuarioConsulta(busquedaUsuarioSalaConsultaVO);

		if (listaTemas != null && !listaTemas.isEmpty()) {
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_TEMAS_KEY, listaTemas);
			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY,
					busquedaUsuarioSalaConsultaVO);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_TEMAS));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_TEMAS_INVESTIGACION));
		}
	}

	public void unidadesConsultadasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.SALAS_UNIDADES_CONSULTADAS, request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_UNIDADES_CONSULTADAS));
	}

	public void imprimirUnidadesConsultadasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioSalasConsultaForm frm = (UsuarioSalasConsultaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		if (frm.getNumVeces() != null) {
			busquedaUsuarioSalaConsultaVO.setNumVeces(frm.getNumVeces());
		}
		CustomDateRange rangeExp = frm.getRangoFechaExp();
		if (rangeExp != null) {
			busquedaUsuarioSalaConsultaVO.setFechaIniExp(rangeExp
					.getInitialDate());
			busquedaUsuarioSalaConsultaVO.setFechaFinExp(rangeExp
					.getFinalDate());
		}

		List listaUnidades = salasBI
				.getUnidadesConsultadas(busquedaUsuarioSalaConsultaVO);

		if (listaUnidades != null && !listaUnidades.isEmpty()) {
			String formato = frm.getMapFormValues("fechaCompExp");
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_KEY, formato);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_UNIDADES_CONSULTADAS_KEY,
					listaUnidades);
			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY,
					busquedaUsuarioSalaConsultaVO);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_UNIDADES));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_UNIDADES_CONSULTADAS));
		}
	}
}