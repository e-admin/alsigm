package fondos.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import se.usuarios.AppPermissions;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ActionForwardUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.MoverUdocsForm;
import fondos.model.IElementoCuadroClasificacion;
import fondos.view.SeriePO;
import fondos.vos.BusquedaUdocsVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.vos.ListaAccesoVO;

public class MoverUdocsAction extends BaseAction {

	protected void initFormularioBuscarUdocsAMoverExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		removeInTemporalSession(request,
				FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.setAsReturnPoint(true);

		// borrado del form
		MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;
		moverUdocsForm.resetInInit();

		ActionForward ret = ActionForwardUtils.getActionForward(request,
				"formularioBuscarUdocsAMover");
		setReturnActionFordward(request, ret);

	}

	protected void formularioBuscarUdocsAMoverExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveCurrentInvocation(KeysClientsInvocations.CUADRO_BUSQ_UDOCS, request);
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda_udocs"));

	}

	protected void buscarUdocsAMoverExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;

		removeInTemporalSession(request,
				FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((MoverUdocsForm) form).getMap());

		// Obtenemos el servicio de elementos del cuadro
		GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {

			try {
				// busqueda dentro de la serie
				SerieVO serie = (SerieVO) getFromTemporalSession(request,
						FondosConstants.ELEMENTO_CF_KEY);
				BusquedaUdocsVO busquedaUdocs = moverUdocsForm
						.getBusquedaUdocsVO();
				busquedaUdocs.setSerie(serie.getId());

				if (StringUtils.isNotEmpty(busquedaUdocs.getTitulo())
						&& !ConfigConstants.getInstance()
								.getDistinguirMayusculasMinusculas())
					busquedaUdocs.setTitulo(busquedaUdocs.getTitulo()
							.toUpperCase());

				// Solo se permiten mover unidades que estén vigentes
				busquedaUdocs.setEstados(new String[] { ""
						+ IElementoCuadroClasificacion.VIGENTE });

				request.setAttribute(FondosConstants.LISTA_UDOCS_AMOVER,
						service.getUnidadesDocumentales(busquedaUdocs));

				setReturnActionFordward(request,
						mapping.findForward("formulario_busqueda_udocs"));

			} catch (TooManyResultsException e) {
				guardarError(request, e);
				goBackExecuteLogic(mapping, form, request, response);
			}
		} else {

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void formBusquedaSerieDestinoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;
		formBusquedaSerieDestinoCodeLogic(mapping, form, request, response,
				moverUdocsForm.getUdocsSeleccionadas());
	}

	protected void accionFormBusquedaSerieDestinoExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (getServiceClient(request).hasPermission(
				AppPermissions.MODIFICACION_CUADRO_CLASIFICACION)) {
			String[] ids = (String[]) getFromTemporalSession(request,
					FondosConstants.ACCION_ELEMENTOS_KEY);
			formBusquedaSerieDestinoCodeLogic(mapping, form, request, response,
					ids);
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	private void formBusquedaSerieDestinoCodeLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String[] ids) {

		ActionErrors errors = validateBuscarUdocsAMover(request, ids);
		if (errors == null) {

			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_FORM_BUSQUEDA_SERIE_DESTINO_DE_UDOCS,
					request);

			MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;
			if (ids != null && ids.length > 0) {
				// Obtener la serie
				ServiceRepository services = getServiceRepository(request);
				GestionSeriesBI bi = services.lookupGestionSeriesBI();
				GestionCuadroClasificacionBI cuadroBI = services
						.lookupGestionCuadroClasificacionBI();
				ElementoCuadroClasificacionVO elementoCuadroClasificacionVO = cuadroBI
						.getElementoCuadroClasificacion(ids[0]);
				SerieVO serie = bi.getSerie(elementoCuadroClasificacionVO
						.getIdPadre());
				SeriePO seriePO = new SeriePO(serie, services);
				setInTemporalSession(request, FondosConstants.SERIE_KEY,
						seriePO);

				TablaTemporalFondosVO tablaTemporal = null;
				try {
					tablaTemporal = cuadroBI.getTablaTemporal(
							getAppUser(request).getId(), seriePO.getId(), ids);
					setTablaTemporales(request, tablaTemporal);

					moverUdocsForm.setFondoSerie(elementoCuadroClasificacionVO
							.getIdFondo());
					moverUdocsForm.setUdocsSeleccionadas(ids);

					// Establecemos los fondos vigentes
					setInTemporalSession(request,
							FondosConstants.LISTA_FONDOS_KEY,
							getGestionFondosBI(request).getFondosVigentes());

					// Redirigimos a la pagina adecuada
					setReturnActionFordward(
							request,
							mapping.findForward("formulario_busqueda_serieDestino"));

				} catch (Exception e) {
					errors = new ActionErrors();
					errors.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.ERROR_GENERAL_MESSAGE, e
									.getMessage()));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("formulario_busqueda_udocs"));
				}
			}
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void buscarSerieDestinoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((MoverUdocsForm) form).getMap());

		// Obtenemos el servicio de elementos del cuadro
		GestionSeriesBI service = getGestionSeriesBI(request);

		// Obtenemos el formulario de busqueda
		MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {

			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.SERIE_KEY);
			setInTemporalSession(
					request,
					FondosConstants.LISTA_SERIES_DESTINO,
					service.getSeriesDestinoMovimientoUDocs(
							moverUdocsForm.getFondoSerie(),
							moverUdocsForm.getCodigoSerie(),
							moverUdocsForm.getTituloSerie(), serie.getId()));

			// Redireccionamos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("formulario_busqueda_serieDestino"));
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void informeMoverUdocsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = validateBuscarSerieDestino(form);
		if (errors == null) {

			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_INFORME_MOVIMIENTO_UDOCS,
					request);

			MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;
			SerieVO serie = getGestionSeriesBI(request).getSerie(
					moverUdocsForm.getIdSerieDestino());
			setInTemporalSession(request, FondosConstants.SERIE_DESTINO, serie);

			List listasAccesoProductores = getGestionDescripcionBI(request)
					.getListasControlAccesoProductoresSerie(serie.getId());
			setInTemporalSession(request,
					FondosConstants.LISTAS_CONTROL_ACCESO_PRODUCTORES_KEY,
					listasAccesoProductores);

			//TablaTemporalFondosVO tablaTemporal = getTablaTemporalFondosVO(request);

			if (!listasAccesoProductores.isEmpty())
				moverUdocsForm
						.setIdLCA(((ListaAccesoVO) listasAccesoProductores
								.get(0)).getId());

			setReturnActionFordward(request,
					mapping.findForward("informe_mover_udocs"));

		} else {
			util.ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

	protected void aceptarMoverUdocsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// List udocsAMover = (List)getFromTemporalSession(request,
		// FondosConstants.UDOCS_A_MOVER);

		MoverUdocsForm moverUdocsForm = (MoverUdocsForm) form;

		Boolean movimientoFinalizado = (Boolean) getFromTemporalSession(
				request, FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		if (movimientoFinalizado == null) {
			movimientoFinalizado = new Boolean(false);
		}

		if (!movimientoFinalizado.booleanValue()) {
			try {
				String idLCA = null;
				if (moverUdocsForm.getAsignarNuevaLCA() != null
						&& moverUdocsForm.getAsignarNuevaLCA().booleanValue()) {
					idLCA = moverUdocsForm.getIdLCA();

					List listasAcceso = (List) getFromTemporalSession(
							request,
							FondosConstants.LISTAS_CONTROL_ACCESO_PRODUCTORES_KEY);

					if (listasAcceso != null) {
						for (Iterator iterator = listasAcceso.iterator(); iterator
								.hasNext();) {
							ListaAccesoVO listaAccesoVO = (ListaAccesoVO) iterator
									.next();
							if (listaAccesoVO.getId().equals(idLCA)) {
								setInTemporalSession(
										request,
										FondosConstants.NOMBRE_LISTA_ACCESO_KEY,
										listaAccesoVO.getNombre());
								break;
							}
						}
					}
				}

				SerieVO serieDestino = (SerieVO) getFromTemporalSession(
						request, FondosConstants.SERIE_DESTINO);
				TablaTemporalFondosVO tablaTemporal = getTablaTemporalFondosVO(request);

				getGestionCuadroClasificacionBI(request)
						.moverUnidadesDocumentales(tablaTemporal, serieDestino,
								idLCA);

				removeInTemporalSession(request, FondosConstants.TABLA_TEMPORAL);

				setInTemporalSession(request,
						FondosConstants.MOVIMIENTO_FINALIZADO_KEY, new Boolean(
								true));

			} catch (FondosOperacionNoPermitidaException e) {
				guardarError(request, e);
				if (e.getCodError() == FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_PROVOCA_FONDOS_DISTINTOS_EN_MISMA_CAJA) {
					request.setAttribute(Constants.LABEL_MENSAJE_MULTILINEA,
							Constants.MULTILINEA_ERROR_UIs_AFECTADAS);
					request.setAttribute(Constants.LINEAS_MENSAJE_MULTILINEA,
							e.getIdsProblematicos());
					setReturnActionFordward(request,
							mapping.findForward("back_and_error_multilinea"));
					return;
				}
			}
		}
		setReturnActionFordward(request,
				mapping.findForward("informe_mover_udocs"));
	}

	/**
	 * @param form
	 */
	private ActionErrors validateBuscarSerieDestino(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		MoverUdocsForm frm = (MoverUdocsForm) form;
		if (GenericValidator.isBlankOrNull(frm.getIdSerieDestino()))
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		return errors.size() > 0 ? errors : null;
	}

	private ActionErrors validateBuscarUdocsAMover(HttpServletRequest request,
			String[] ids) {
		ActionErrors errors = new ActionErrors();
		if (ids == null || ids.length == 0) {
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		} else {
			List elementos = getGestionCuadroClasificacionBI(request)
					.getElementos(ids);
			// Comprobar que todos los elementos están vigentes
			if (ListUtils.isNotEmpty(elementos)) {
				for (Iterator iterator = elementos.iterator(); iterator
						.hasNext();) {
					ElementoCuadroClasificacionVO elementoVO = (ElementoCuadroClasificacionVO) iterator
							.next();
					if (elementoVO != null
							&& elementoVO.getEstado() != IElementoCuadroClasificacion.VIGENTE) {
						String titulo = elementoVO.getTitulo();
						errors.add(Constants.ERROR_ELEMENTO_NO_VIGENTE,
								new ActionError(
										Constants.ERROR_ELEMENTO_NO_VIGENTE,
										titulo));
					}
				}
			}
		}
		return errors.size() > 0 ? errors : null;
	}

}
