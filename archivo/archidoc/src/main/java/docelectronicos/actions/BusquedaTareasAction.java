package docelectronicos.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;

import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import docelectronicos.DocumentosConstants;
import docelectronicos.forms.BusquedaTareaForm;
import docelectronicos.vos.DocTCapturaVO;

public class BusquedaTareasAction extends BaseAction {

	public void formBusquedaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener los estados de documentos vitales
		request.setAttribute(DocumentosConstants.ESTADOS_TAREAS_KEY,
				getGestionDocumentosElectronicosBI(request).getEstadosTareas());

		saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_FORM_BUSQUEDA, request);

		setReturnActionFordward(request, mapping.findForward("formulario"));
	}

	public class DocTCapturaPOTransformer implements Transformer {
		ServiceRepository rep = null;

		public DocTCapturaPOTransformer(ServiceRepository rep) {
			this.rep = rep;

		}

		public Object transform(Object arg0) {
			return new DocTCapturaPO(rep, (DocTCapturaVO) arg0);
		}
	}

	public void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaTareaForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DIGITALIZACION_LISTADO_BUSQUEDA,
					request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "fechaEstado");
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			BusquedaTareaForm documentosForm = (BusquedaTareaForm) form;
			documentosForm.setPageInfo(pageInfo);

			try {
				// Establecemos los elementos de la vista
				List tareasEncontradas = getGestionDocumentosElectronicosBI(
						request).busquedaTareas(documentosForm.getBusquedaVO());
				if (!CollectionUtils.isEmpty(tareasEncontradas)) {
					CollectionUtils.transform(tareasEncontradas,
							new DocTCapturaPOTransformer(
									getServiceRepository(request)));
				}
				request.setAttribute(DocumentosConstants.TAREAS_KEY,
						tareasEncontradas);

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request, mapping.findForward("listado"));
			} catch (TooManyResultsException e) {
				guardarError(request, e);
				goBackExecuteLogic(mapping, form, request, response);
			}
		} else {
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

}
