package salas.actions;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.form.RegistroConsultaSalaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.EdificioVO;
import salas.vos.MesaVO;
import salas.vos.RegistroConsultaSalaPO;
import salas.vos.RegistroConsultaSalaToPO;
import salas.vos.RegistroConsultaSalaVO;
import salas.vos.SalaVO;
import salas.vos.UsuarioArchivoSalasConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import util.ErrorsTag;

import common.Constants;
import common.actions.ActionRedirect;
import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

public class GestionRegistroConsultaAction extends SalasConsultaBaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;
		registroForm.reset();

		colocarListaTipoDocumentos(request);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_REGISTRO_ENTRADA, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_REGISTRO_ENTRADA));
	}

	public void buscarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;

		String[] idsArchivo = getAppUser(request).getIdsArchivosUser();

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO = new BusquedaUsuarioSalaConsultaVO();
		registroForm.populateUsuarioConsultaSala(busquedaUsuarioSalaConsultaVO);
		busquedaUsuarioSalaConsultaVO.setIdsArchivo(idsArchivo);
		busquedaUsuarioSalaConsultaVO.setVigente(Constants.TRUE_STRING);

		colocarListaTipoDocumentos(request);

		List usuariosConsulta = salasBI
				.buscarUsuarios(busquedaUsuarioSalaConsultaVO);
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_CONSULTA_KEY,
				usuariosConsulta);

		saveCurrentInvocation(KeysClientsInvocations.SALAS_REGISTRO_ENTRADA,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_REGISTRO_ENTRADA));
	}

	public void seleccionarUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;

		removeInTemporalSession(request,
				SalasConsultaConstants.MOSTRAR_NAVEGACION_REGISTRO_KEY);
		removeInTemporalSession(request,
				SalasConsultaConstants.USUARIO_CONSULTA_KEY);
		removeInTemporalSession(request,
				SalasConsultaConstants.LISTA_ARCHIVOS_KEY);

		String idUsuarioSeleccionado = registroForm.getUsuarioSeleccionado();
		ActionErrors errors = new ActionErrors();
		if (StringUtils.isNotEmpty(idUsuarioSeleccionado)) {
			if (salasBI.isUsuarioRegistrado(idUsuarioSeleccionado)) {
				ActionErrors errores = new ActionErrors();
				errores.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						SalasConsultaConstants.ERROR_USUARIO_REGISTRADO));
				ErrorsTag.saveErrors(request, errores);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_REGISTRO_ENTRADA));
			} else {
				UsuarioSalasConsultaVO usuarioConsulta = salasBI
						.getUsuarioById(idUsuarioSeleccionado, false);
				String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
				usuarioConsulta.getListaArchivos();

				List archivos = salasBI
						.getArchivosByIdUsuarioSalaConsultaInArchivos(
								usuarioConsulta.getId(), idsArchivo);
				if (archivos == null || archivos.isEmpty()) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
							SalasConsultaConstants.ERROR_NO_ARCHIVOS_REGISTRO));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_REGISTRO_ENTRADA));
				} else {
					if (archivos != null && archivos.size() == 1) {
						registroForm
								.setIdArchivo(((UsuarioArchivoSalasConsultaVO) archivos
										.get(0)).getIdArchivo());
						setInTemporalSession(
								request,
								SalasConsultaConstants.MOSTRAR_NAVEGACION_REGISTRO_KEY,
								Boolean.TRUE);
					} else {
						setInTemporalSession(
								request,
								SalasConsultaConstants.MOSTRAR_NAVEGACION_REGISTRO_KEY,
								Boolean.FALSE);
					}

					setInTemporalSession(request,
							SalasConsultaConstants.LISTA_ARCHIVOS_KEY, archivos);
					setInTemporalSession(request,
							SalasConsultaConstants.USUARIO_CONSULTA_KEY,
							usuarioConsulta);

					// Añadir los parámetros del formulario al enlace
					ClientInvocation cli = getInvocationStack(request)
							.getLastClientInvocation();
					cli.addParameters(registroForm.getMap());
					cli.setAsReturnPoint(true);

					saveCurrentInvocation(
							KeysClientsInvocations.SALAS_CREAR_REGISTRO,
							request);
					setReturnActionFordward(
							request,
							mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_REGISTRO));
				}
			}
		}
	}

	public void reloadExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;

		String idArchivo = registroForm.getIdArchivo();
		if (StringUtils.isNotEmpty(idArchivo)) {
			setInTemporalSession(request,
					SalasConsultaConstants.MOSTRAR_NAVEGACION_REGISTRO_KEY,
					Boolean.TRUE);
		} else {
			setInTemporalSession(request,
					SalasConsultaConstants.MOSTRAR_NAVEGACION_REGISTRO_KEY,
					Boolean.FALSE);
		}

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_REGISTRO));
	}

	public void crearRegistroExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		GestionArchivosBI archivoBI = getGestionArchivosBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;
		UsuarioSalasConsultaVO usuarioConsultaVO = (UsuarioSalasConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.USUARIO_CONSULTA_KEY);

		removeInTemporalSession(request, SalasConsultaConstants.ARCHIVO_KEY);
		removeInTemporalSession(request,
				SalasConsultaConstants.REGISTRO_CONSULTA_KEY);
		removeInTemporalSession(request, SalasConsultaConstants.MESA_KEY);

		RegistroConsultaSalaVO registroConsultaVO = new RegistroConsultaSalaVO();
		registroForm.setUsuarioConsultaSala(usuarioConsultaVO);
		registroForm.populate(registroConsultaVO);

		List mesas = new ArrayList();
		MesaVO mesaVO = null;
		String idSeleccionado = registroForm.getElementoSeleccionado();
		String tipoSeleccionado = registroForm.getTipoSeleccionado();
		if (StringUtils.isNotEmpty(tipoSeleccionado)) {
			if (SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO
					.equals(tipoSeleccionado)) {
				mesas = salasBI.getMesasLibresByEdificio(idSeleccionado,
						registroForm.getIdArchivo(),
						registroForm.getEquipoInformatico());
			} else if (SalasConsultaConstants.TIPO_ELEMENTO_SALA
					.equals(tipoSeleccionado)) {
				mesas = salasBI.getMesasLibresBySala(idSeleccionado,
						registroForm.getIdArchivo(),
						registroForm.getEquipoInformatico());
			} else if (SalasConsultaConstants.TIPO_ELEMENTO_MESA
					.equals(tipoSeleccionado)) {
				mesaVO = salasBI.getMesaById(idSeleccionado);
			}

			if (mesas != null && !mesas.isEmpty()) {
				mesaVO = (MesaVO) mesas.get(0);
			}
			if (mesaVO != null) {
				SalaVO sala = salasBI.getSalaById(mesaVO.getIdSala());
				if (sala != null) {
					EdificioVO edificio = salasBI.getEdificioById(sala
							.getIdEdificio());
					if (edificio != null) {
						registroConsultaVO
								.setMesaAsignada(edificio.getNombre()
										+ Constants.SLASH
										+ sala.getNombre()
										+ Constants.SLASH
										+ SalasConsultaConstants.PATH_NAME_UBICACION_MESA
										+ mesaVO.getCodigo());

						ArchivoVO archivoVO = archivoBI.getArchivoXId(edificio
								.getIdArchivo());
						setInTemporalSession(request,
								SalasConsultaConstants.ARCHIVO_KEY, archivoVO);
						setInTemporalSession(request,
								SalasConsultaConstants.REGISTRO_CONSULTA_KEY,
								registroConsultaVO);
						setInTemporalSession(request,
								SalasConsultaConstants.MESA_KEY, mesaVO);
						saveCurrentInvocation(
								KeysClientsInvocations.SALAS_CONFIRMAR_REGISTRO_ENTRADA,
								request);
						setReturnActionFordward(
								request,
								mappings.findForward(SalasConsultaConstants.FORWARD_CONFIRMAR_REGISTRO));
					}
				}
			} else {
				ActionErrors errores = new ActionErrors();
				errores.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								SalasConsultaConstants.ERROR_NO_MESAS_LIBRES_A_SELECCIONAR));
				ErrorsTag.saveErrors(request, errores);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_REGISTRO));
			}
		}
	}

	public void verExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RegistroConsultaSalaVO registroConsultaVO = null;
		ActionErrors errors = new ActionErrors();

		String idRegistroConsulta = request
				.getParameter(SalasConsultaConstants.PARAM_REGISTRO_CONSULTA);

		try {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

			registroConsultaVO = salasBI
					.getRegistroConsultaSala(idRegistroConsulta);
			ServiceRepository serviceRepository = getServiceRepository(request);

			RegistroConsultaSalaPO registroConsultaSalaPO = (RegistroConsultaSalaPO) RegistroConsultaSalaToPO
					.getInstance(serviceRepository).transform(
							registroConsultaVO);
			setInTemporalSession(request,
					SalasConsultaConstants.REGISTRO_CONSULTA_KEY,
					registroConsultaSalaPO);
			if (registroConsultaSalaPO != null) {
				setInTemporalSession(request,
						SalasConsultaConstants.USUARIO_CONSULTA_KEY,
						registroConsultaSalaPO.getUsuarioSalasConsulta());
			}

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.SALAS_DATOS_REGISTRO_ENTRADA,
					request);
			invocation.setAsReturnPoint(true);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS_REGISTRO_ENTRADA));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			logger.error(
					"Se ha producido un error al ver el registro de consulta en sala con id: "
							+ idRegistroConsulta, e);
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	public void verByUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RegistroConsultaSalaVO registroConsultaVO = null;
		ActionErrors errors = new ActionErrors();

		String idUsuario = request
				.getParameter(SalasConsultaConstants.PARAM_ID_USUARIO);

		try {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

			registroConsultaVO = salasBI
					.getRegistroConsultaSalaByUsuario(idUsuario);
			ServiceRepository serviceRepository = getServiceRepository(request);

			RegistroConsultaSalaPO registroConsultaSalaPO = (RegistroConsultaSalaPO) RegistroConsultaSalaToPO
					.getInstance(serviceRepository).transform(
							registroConsultaVO);
			setInTemporalSession(request,
					SalasConsultaConstants.REGISTRO_CONSULTA_KEY,
					registroConsultaSalaPO);
			if (registroConsultaSalaPO != null) {
				setInTemporalSession(request,
						SalasConsultaConstants.USUARIO_CONSULTA_KEY,
						registroConsultaSalaPO.getUsuarioSalasConsulta());
			}

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.SALAS_DATOS_REGISTRO_ENTRADA,
					request);
			invocation.setAsReturnPoint(true);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS_REGISTRO_ENTRADA));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			logger.error(
					"Se ha producido un error al ver el registro de consulta en sala con id usuario: "
							+ idUsuario, e);
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RegistroConsultaSalaVO registroConsultaVO = null;
		ActionErrors errors = new ActionErrors();
		try {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

			registroConsultaVO = (RegistroConsultaSalaVO) getFromTemporalSession(
					request, SalasConsultaConstants.REGISTRO_CONSULTA_KEY);

			MesaVO mesaVO = (MesaVO) getFromTemporalSession(request,
					SalasConsultaConstants.MESA_KEY);
			salasBI.insertarRegistroConsultaSala(registroConsultaVO, mesaVO);

			getInvocationStack(request).goToLastReturnPoint(request);

			ActionRedirect vistaRegistroConsulta = new ActionRedirect(
					mappings.findForward(SalasConsultaConstants.FORWARD_REDIRECT_DATOS_REGISTRO_ENTRADA),
					true);
			vistaRegistroConsulta.addParameter(
					SalasConsultaConstants.PARAM_REGISTRO_CONSULTA,
					registroConsultaVO.getId());
			setReturnActionFordward(request, vistaRegistroConsulta);
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SalasConsultaException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			logger.error(
					"Se ha producido un error al guardar el registro de consulta en sala: "
							+ registroConsultaVO, e);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_REGISTRO));
		}
	}

	public void listadoUsuariosRegistradosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		List usuariosRegistrados = salasBI.getUsuariosRegistrados();
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY,
				usuariosRegistrados);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_USUARIOS_REGISTRADOS, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_USUARIOS_REGISTRADOS));
	}

	public void registrarSalidaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;
		String idRegistro = registroForm.getRegistrosSeleccionados()[0];
		RegistroConsultaSalaVO registroCSala = salasBI
				.getRegistroConsultaSala(idRegistro);
		String idUsuario = registroCSala.getIdUsrCSala();

		boolean isUsuarioConUDocsPendientes = salasBI
				.isUsuarioConUnidadesPendientes(idUsuario);
		if (isUsuarioConUDocsPendientes) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					SalasConsultaConstants.ERROR_USUARIO_CON_UDOCS_PENDIENTES));
			ErrorsTag.saveErrors(request, errors);
		} else {
			salasBI.registrarSalidaSala(idRegistro, idUsuario);
			List usuariosRegistrados = salasBI.getUsuariosRegistrados();
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY,
					usuariosRegistrados);
		}
		String datosRegistro = request
				.getParameter(SalasConsultaConstants.PARAM_DATOS_REGISTRO);
		if (StringUtils.isNotEmpty(datosRegistro)) {
			goLastClientExecuteLogic(mappings, registroForm, request, response);
		} else {
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_USUARIOS_REGISTRADOS));
		}
	}

	public void busquedaUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionArchivosBI archivoBI = getGestionArchivosBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;
		registroForm.reset();

		String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
		List archivos = archivoBI.getArchivosXId(idsArchivo);
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_ARCHIVOS_KEY, archivos);

		saveCurrentInvocation(KeysClientsInvocations.SALAS_BUSCAR_USUARIOS,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_USUARIOS));
	}

	public void listarBusquedaUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;

		BusquedaRegistroConsultaSalaVO registroConsultaSalaVO = new BusquedaRegistroConsultaSalaVO();
		registroForm.populateRegistroConsultaSala(registroConsultaSalaVO);
		if (StringUtils.isEmpty(registroForm.getSearchTokenArchivo())) {
			String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
			registroConsultaSalaVO.setIdsArchivo(idsArchivo);
		}

		getInvocationStack(request).goToLastReturnPoint(request);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_RESULTADO_BUSQUEDA_USUARIOS,
				request);
		invocation.setAsReturnPoint(true);

		List registrosConsulta = salasBI
				.buscarRegistros(registroConsultaSalaVO);
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY,
				registrosConsulta);

		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_USUARIOS_REGISTRADOS));
	}

	public void listadoRegistrosAbiertosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		List registrosAbiertos = salasBI.getRegistrosAbiertos();
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY,
				registrosAbiertos);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_USUARIOS_REGISTRADOS, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_USUARIOS_REGISTRADOS));
	}

	public void busquedaUsuariosAsistenciaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		colocarListaArchivos(request);

		setInTemporalSession(request,
				SalasConsultaConstants.INFORME_USUARIOS_KEY, Boolean.TRUE);
		saveCurrentInvocation(
				KeysClientsInvocations.SALAS_BUSCAR_ASISTENCIA_USUARIOS,
				request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_INVESTIGADORES));
	}

	public void imprimirBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		RegistroConsultaSalaForm registroForm = (RegistroConsultaSalaForm) form;

		BusquedaRegistroConsultaSalaVO registroConsultaSalaVO = new BusquedaRegistroConsultaSalaVO();
		registroForm.populateRegistroConsultaSala(registroConsultaSalaVO);
		if (StringUtils.isEmpty(registroForm.getSearchTokenArchivo())) {
			String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
			registroConsultaSalaVO.setIdsArchivo(idsArchivo);
		}

		List registrosConsulta = salasBI
				.buscarRegistros(registroConsultaSalaVO);

		if (registrosConsulta != null && !registrosConsulta.isEmpty()) {
			String numDocLike = registroForm.getNumeroDoc_like();
			String formatoEntrada = registroForm
					.getMapFormValues("fechaCompEntrada");
			String formatoSalida = registroForm
					.getMapFormValues("fechaCompSalida");
			setInTemporalSession(request,
					SalasConsultaConstants.NUM_DOC_LIKE_KEY, numDocLike);
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_ENTRADA_KEY,
					formatoEntrada);
			setInTemporalSession(request,
					SalasConsultaConstants.FECHA_COMP_SALIDA_KEY, formatoSalida);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY,
					registrosConsulta);
			setInTemporalSession(request,
					SalasConsultaConstants.BUSQUEDA_REGISTROS_KEY,
					registroConsultaSalaVO);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_INFORME_ASISTENCIA));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							SalasConsultaConstants.ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_BUSCAR_INVESTIGADORES));
		}
	}
}