package descripcion.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.TipoObjetoUsuario;
import descripcion.forms.TablaValidacionForm;
import descripcion.forms.TextoTablaValidacionForm;
import descripcion.vos.FichaVO;
import descripcion.vos.TablaValidacionVO;
import descripcion.vos.TextoTablaValidacionVO;
import descripcion.vos.UsoObjetoVO;

/**
 * Acción para la gestión de las tablas de validación.
 */
public class TablasValidacionAction extends BaseAction {
	private static final String FICHA_USA_TABLA_VALIDACION = DescripcionConstants.DESCRIPCION_OBJETO_FORM_DELETE_FICHA;

	/**
	 * Muestra la lista de tablas de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void showExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de showExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_TABLAS_VALIDACION, request);

		// Tablas de validación
		request.setAttribute(DescripcionConstants.TABLAS_VALIDACION_KEY,
				getGestionDescripcionBI(request).getTablasValidacion());

		setReturnActionFordward(request,
				mapping.findForward("ver_tablas_validacion"));
	}

	/**
	 * Muestra el formulario para crear una tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de formExecuteLogic");

		// Identificador de la tabla
		String id = request.getParameter(Constants.ID);
		logger.info("Id tabla validaci\u00F3n: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_TABLAS_VALIDACION_EDIT,
					request);

			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Obtener la información de la tabla
			((TablaValidacionForm) form).set(descripcionBI
					.getTablaValidacion(id));
		} else {
			((TablaValidacionForm) form).reset();
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_TABLAS_VALIDACION_FORM,
					request);
		}

		setReturnActionFordward(request,
				mapping.findForward("editar_tabla_validacion"));
	}

	/**
	 * Muestra la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de retrieveExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_TABLAS_VALIDACION_VIEW,
				request);

		// Identificador de la tabla
		String id = request.getParameter(Constants.ID);
		logger.info("Id tabla validaci\u00F3n: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Obtener la información de la tabla
			((TablaValidacionForm) form).set(descripcionBI
					.getTablaValidacion(id));

			// Obtener los valores de la tabla de validación
			setInTemporalSession(request,
					DescripcionConstants.VALORES_VALIDACION_KEY,
					descripcionBI.getValoresValidacion(id));
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_tabla_validacion"));
	}

	/**
	 * Guarda la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void saveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de saveExecuteLogic");

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Validar el formulario
		ActionErrors errores = ((TablaValidacionForm) form).validateFrm(
				mapping, request, descripcionBI);

		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Recoger la información de la tabla
			TablaValidacionVO tabla = new TablaValidacionVO();
			((TablaValidacionForm) form).populate(tabla);

			if (StringUtils.isBlank(tabla.getId())) {
				// Crear la tabla de validación
				tabla = descripcionBI.insertTablaValidacion(tabla);
				((TablaValidacionForm) form).set(tabla);

				// Eliminar la invocación anterior
				popLastInvocation(request);
			} else {
				// Modificar la tabla de validación
				descripcionBI.updateTablaValidacion(tabla);
			}

			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "method", "retrieve&id="
							+ tabla.getId()));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mapping.findForward("editar_tabla_validacion"));
		}
	}

	/**
	 * Comprueba si una tabla de validación está en uso
	 * 
	 * @param request
	 * @param idTablaValidacion
	 * @return cierto si la tabla de validación está en uso, false en caso
	 *         contrario
	 */
	private boolean isTablaValidacionEnUso(HttpServletRequest request,
			String idTablaValidacion) {

		return isTablaValidacionEnUso(request,
				new String[] { idTablaValidacion });
	}

	/**
	 * Comprueba si un conjunto de tablas de validación está en uso
	 * 
	 * @param request
	 * @param idTablasValidacion
	 * @return cierto si la tabla de validación está en uso, false en caso
	 *         contrario
	 */
	private boolean isTablaValidacionEnUso(HttpServletRequest request,
			String[] idTablasValidacion) {
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		List list = descripcionService
				.getElementosEnUsoXIdsObj(idTablasValidacion);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(i);
				String idTablaValidacion = usoObjetoVO.getIdObj();
				TablaValidacionVO tablaValidacionVO = descripcionService
						.getTablaValidacion(idTablaValidacion);

				if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.FICHA) {
					FichaVO fichaVO = descripcionService.getFicha(usoObjetoVO
							.getIdObjUsuario());
					ActionErrors errors = new ActionErrors();
					errors.add(
							FICHA_USA_TABLA_VALIDACION,
							new ActionError(FICHA_USA_TABLA_VALIDACION,
									tablaValidacionVO.getNombre(), fichaVO
											.getNombre()));
					ErrorsTag.saveErrors(request, errors);

					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Elimina la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void removeTblExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de removeTblExecuteLogic");

		// Identificador de la tabla de validación
		String id = ((TablaValidacionForm) form).getId();
		if (StringUtils.isNotBlank(id)) {
			if (!isTablaValidacionEnUso(request, id)) {
				// Eliminar la tabla de validación
				getGestionDescripcionBI(request).deleteTablasValidacion(
						new String[] { id });
			} else {
				setReturnActionFordward(request,
						mapping.findForward("ver_tabla_validacion"));
				return;
			}
		}

		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Elimina las tablas de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void removeExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de removeExecuteLogic");

		// Identificadores de las tablas
		String[] listaIds = request.getParameterValues("eliminar");
		if (StringUtils.isNotEmpty(listaIds)) {
			if (!isTablaValidacionEnUso(request, listaIds)) {
				// Eliminar las tablas de validación
				getGestionDescripcionBI(request).deleteTablasValidacion(
						listaIds);
			}
		}

		setReturnActionFordward(request,
				redirectForwardMethod(request, "method", "show"));
	}

	/**
	 * Elimina los valores de la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void removeValuesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de removeValuesExecuteLogic");

		// Identificador de la tabla
		String id = request.getParameter(Constants.ID);
		logger.info("Id tabla validaci\u00F3n: " + id);

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Identificadores de los valores de la tabla
		String[] listaIds = request.getParameterValues("eliminar");
		if (StringUtils.isNotEmpty(listaIds)) {
			// SI pertenece a una lista de valores de uso interno no se puede
			// modificar.
			TextoTablaValidacionVO textoTblVld = descripcionBI
					.getValorTablaValidacion(listaIds[0]);
			TablaValidacionVO tblvld = descripcionBI
					.getTablaValidacion(textoTblVld.getIdTblVld());
			if (tblvld.isTablaDeSistema()) {
				ActionErrors errores = new ActionErrors();
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										DescripcionConstants.DESCRIPCION_TBLVLD_ERROR_MODIFICACION,
										request.getLocale())));
				ErrorsTag.saveErrors(request, errores);
				setReturnActionFordward(request,
						mapping.findForward("ver_texto_tabla_validacion"));
			} else {
				// Eliminar los valores de las tablas de validación
				descripcionBI.deleteValoresTablaValidacion(listaIds);
			}
		}

		setReturnActionFordward(
				request,
				redirectForwardMethod(request, "/tablasVld", "method",
						"retrieve&id=" + id));
	}

	/**
	 * Muestra el valor de una tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void retrieveValueExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de retrieveValueExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_TABLAS_VALIDACION_VALUE,
				request);

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Identificador de la tabla
		String id = request.getParameter(Constants.ID);
		logger.info("Id valor tabla validaci\u00F3n: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Obtener la información de la tabla
			((TextoTablaValidacionForm) form).set(descripcionBI
					.getValorTablaValidacion(id));
		} else {
			// Obtener el nombre de la lista descriptora
			String idTblVld = request.getParameter("idTblVld");
			TablaValidacionVO tablaValidacion = descripcionBI
					.getTablaValidacion(idTblVld);
			if (tablaValidacion != null)
				((TextoTablaValidacionForm) form)
						.setNombreTblVld(tablaValidacion.getNombre());
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_texto_tabla_validacion"));
	}

	/**
	 * Guarda el valor de la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void saveValueExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de saveValueExecuteLogic");

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Validar el formulario
		ActionErrors errores = ((TextoTablaValidacionForm) form).validate(
				mapping, request, descripcionBI);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Recoger la información del valor
			TextoTablaValidacionVO texto = new TextoTablaValidacionVO();
			((TextoTablaValidacionForm) form).populate(texto);

			if (StringUtils.isBlank(texto.getId())) {
				// Crear el texto de la tabla de validación
				texto = descripcionBI.insertValorTablaValidacion(texto);
				((TextoTablaValidacionForm) form).set(texto);
			} else {
				// Obtenemos el valor de tabla de validacion
				// TextoTablaValidacionVO textoTblVld =
				// descripcionBI.getValorTablaValidacion(texto.getId());

				// Modificar el texto de la tabla de validación
				// descripcionBI.updateValorTablaValidacion(texto,
				// textoTblVld.getValor());

				// SI pertenece a una lista de valores de uso interno no se
				// puede modificar.
				TablaValidacionVO tblvld = descripcionBI
						.getTablaValidacion(texto.getIdTblVld());
				if (tblvld.isTablaDeSistema()) {
					errores.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_GENERAL_MESSAGE,
									Messages.getString(
											DescripcionConstants.DESCRIPCION_TBLVLD_ERROR_MODIFICACION,
											request.getLocale())));
					ErrorsTag.saveErrors(request, errores);
					setReturnActionFordward(request,
							mapping.findForward("ver_texto_tabla_validacion"));
					return;
				} else
					descripcionBI.updateValorTablaValidacion(texto);
			}
			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "/tablasVld", "method",
							"retrieve&id=" + texto.getIdTblVld()));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mapping.findForward("ver_texto_tabla_validacion"));
		}
	}

	/**
	 * Elimina el valor de la tabla de validación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void removeValueExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de removeValueExecuteLogic");

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Identificador del valor
		String id = ((TextoTablaValidacionForm) form).getId();
		if (StringUtils.isNotBlank(id)) {
			// SI pertenece a una lista de valores de uso interno no se puede
			// modificar.
			TextoTablaValidacionVO textoTblVld = descripcionBI
					.getValorTablaValidacion(id);
			TablaValidacionVO tblvld = descripcionBI
					.getTablaValidacion(textoTblVld.getIdTblVld());
			if (tblvld.isTablaDeSistema()) {
				ActionErrors errores = new ActionErrors();
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										DescripcionConstants.DESCRIPCION_TBLVLD_ERROR_MODIFICACION,
										request.getLocale())));
				ErrorsTag.saveErrors(request, errores);
				((TextoTablaValidacionForm) form).set(textoTblVld);
				setReturnActionFordward(request,
						mapping.findForward("ver_texto_tabla_validacion"));
				return;
			} else {
				// Eliminar el texto de la tabla de validación
				getGestionDescripcionBI(request).deleteValoresTablaValidacion(
						new String[] { id });
			}
		}

		goBackExecuteLogic(mapping, form, request, response);
	}
}
