package fondos.actions;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import solicitudes.SolicitudesConstants;
import util.ErrorsTag;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSeriesBI;
import common.util.ListUtils;

import fondos.FondosConstants;
import fondos.forms.ResultadoBusquedaFondosForm;
import fondos.model.TiposAccionBusqueda;
import fondos.utils.AccionesBusquedaHelper;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;

public class ResultadoBusquedaFondosAction extends BaseAction {

	public void ejecutarAccionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionErrors errors = getErrors(request, false);

		ResultadoBusquedaFondosForm frm = (ResultadoBusquedaFondosForm) form;

		String[] ids = frm.getIds();

		frm.validar(request, errors);

		if (errors != null && errors.size() != 0) {
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {

			GestionCuadroClasificacionBI cuadroClasificacionBI = getGestionCuadroClasificacionBI(request);

			try {
				errors = AccionesBusquedaHelper.check(
						getServiceClient(request), cuadroClasificacionBI, ids,
						frm.getAccion());
			} catch (FileNotFoundException flne) {
				logger.error(
						"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE ACCIONES",
						flne);
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			}

			catch (Exception e) {
				logger.error(
						"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE ACCIONES",
						e);
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_CONFIGURACION_FILE));
			}

			if (errors != null && errors.size() != 0) {
				ErrorsTag.saveErrors(request, errors);
				setInTemporalSession(request, "usarCache", Boolean.TRUE);
				goLastClientExecuteLogic(mappings, form, request, response);
			} else {
				// Guardar los valores es session

				// Valores para Préstamos y Consultas
				String idAccion = frm.getAccion();

				if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_MOVER_ELEMENTOS
						.equals(idAccion)) {
					setInTemporalSession(request,
							FondosConstants.ACCION_ELEMENTOS_KEY, frm.getIds());
					ElementoCuadroClasificacionVO elemento = cuadroClasificacionBI
							.getElementoPadre(ids[0]);
					GestionSeriesBI serieBI = getGestionSeriesBI(request);
					SerieVO serieOrigen = serieBI.getSerie(elemento.getId());
					setInTemporalSession(request,
							FondosConstants.ELEMENTO_CF_KEY, serieOrigen);
					removeInTemporalSession(request,
							FondosConstants.LISTA_SERIES_DESTINO);
				} else if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_BLOQUEAR
						.equals(idAccion)
						|| TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_DESBLOQUEAR
								.equals(idAccion)) {
					setInTemporalSession(request,
							FondosConstants.ACCION_ELEMENTOS_KEY, frm.getIds());
				} else if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_NO_CONSERVAR
						.equals(idAccion)) {
					setInTemporalSession(request,
							FondosConstants.ACCION_ELEMENTOS_KEY, frm.getIds());
				} else if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_CREAR_PRESTAMO
						.equals(idAccion)
						|| TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_CREAR_CONSULTA
								.equals(idAccion)
						|| TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO
								.equals(idAccion)
						|| TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA
								.equals(idAccion)) {

					ArchivoVO archivoVO = cuadroClasificacionBI
							.getArchivoByIdElemento(ids[0]);

					if (archivoVO != null) {
						if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO
								.equals(idAccion)) {
							GestionPrestamosBI prestamosBI = getGestionPrestamosBI(request);
							List listaPrestamos = prestamosBI
									.getPrestamosAniadirUDocsFromBusqueda(
											archivoVO.getId(),
											getAppUser(request).getId());

							if (ListUtils.isNotEmpty(listaPrestamos)) {
								setInTemporalSession(
										request,
										SolicitudesConstants.PRESTAMOS_PARA_ANIADIR_UDOCS_KEY,
										listaPrestamos);
							} else {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_SIN_PRESTAMOS_PARA_ANIADIR_UDOCS));
							}
						} else if (TiposAccionBusqueda.TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA
								.equals(idAccion)) {
							GestionConsultasBI consultasBI = getGestionConsultasBI(request);
							List listaPrestamos = consultasBI
									.getConsultasAniadirUDocsFromBusqueda(
											archivoVO.getId(),
											getAppUser(request).getId());

							if (ListUtils.isNotEmpty(listaPrestamos)) {
								setInTemporalSession(
										request,
										SolicitudesConstants.CONSULTAS_PARA_ANIADIR_UDOCS_KEY,
										listaPrestamos);
							} else {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_SIN_CONSULTAS_PARA_ANIADIR_UDOCS));
							}
						}

						setInTemporalSession(request,
								SolicitudesConstants.ARCHIVO_SOLICITUD_KEY,
								archivoVO);

						GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

						List listaUnidades = service.getElementosCFVOByIds(ids);
						setInTemporalSession(request,
								SolicitudesConstants.LISTA_UDOCS_SOLICITUD_KEY,
								listaUnidades);
					} else {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_AL_OBTENER_ARCHIVO_ELEMENTOS));
					}
				}

				// Obtener el forward correspondiente
				String forward;
				try {
					forward = AccionesBusquedaHelper.getForwardActionRedirect(
							cuadroClasificacionBI, ids, frm.getAccion(),
							request);
					// Crear un action redirect para ir a la pantalla
					// correspondiente
					ActionRedirect actionRedirect = new ActionRedirect(
							mappings.findForward(forward), true);
					actionRedirect.setContextRelative(true);

					// Establecer a nulos los valores del formulario
					frm.setAccion(null);
					frm.setIds(new String[0]);

					setReturnActionFordward(request, actionRedirect);

				} catch (FileNotFoundException flne) {
					logger.error(
							"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE ACCIONES",
							flne);
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
				}

				catch (Exception e) {
					logger.error(
							"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE ACCIONES",
							e);
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_CONFIGURACION_FILE));
				}

				if (errors != null && errors.size() != 0) {
					ErrorsTag.saveErrors(request, errors);
					setInTemporalSession(request, "usarCache", Boolean.TRUE);
					goLastClientExecuteLogic(mappings, form, request, response);
				}
			}
		}
	}

}
