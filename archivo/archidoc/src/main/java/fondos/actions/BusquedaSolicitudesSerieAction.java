package fondos.actions;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import fondos.FondosConstants;
import fondos.forms.BusquedaSolicitudSerieForm;
import fondos.vos.SolicitudSerieExtended;

public class BusquedaSolicitudesSerieAction extends BaseAction {
	public void formBusquedaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// // Obtener los estados de documentos vitales
		// request.setAttribute(FondosConstants.estadosSolicitudesSerie,
		// getGestionDocumentosElectronicosBI(request)
		// .getEstadosTareas());

		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUD_SERIE_FORM_BUSQUEDA, request);

		setReturnActionFordward(request, mapping.findForward("formulario"));
	}

	public class SolicitudSerieExtendidaPOTransformer implements Transformer {
		ServiceRepository rep = null;

		public SolicitudSerieExtendidaPOTransformer(ServiceRepository rep) {
			this.rep = rep;

		}

		public Object transform(Object arg0) {
			return new SolicitudSerieExtendedPO(rep,
					(SolicitudSerieExtended) arg0);
		}
	}

	public void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaSolicitudSerieForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.SOLICITUD_SERIE_LISTADO_BUSQUEDA,
					request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "fechaEstado");
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			BusquedaSolicitudSerieForm busquedaSolicitudesForm = (BusquedaSolicitudSerieForm) form;
			busquedaSolicitudesForm.setPageInfo(pageInfo);

			try {
				// Establecemos los elementos de la vista
				Collection solicitudesEncontradas = getGestionSeriesBI(request)
						.busquedaSolicitudesSeries(
								busquedaSolicitudesForm.getBusquedaVO());
				if (!CollectionUtils.isEmpty(solicitudesEncontradas)) {
					CollectionUtils.transform(solicitudesEncontradas,
							new SolicitudSerieExtendidaPOTransformer(
									getServiceRepository(request)));
				}
				request.setAttribute(FondosConstants.SOLICITUDES_KEY,
						solicitudesEncontradas);

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request, mapping.findForward("listado"));
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				goBackExecuteLogic(mapping, form, request, response);
			}
		} else {
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

}
