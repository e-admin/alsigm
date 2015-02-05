package deposito.actions.asignable;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.model.TipoSignaturacion;
import util.CollectionUtils;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.IntervalOptions;
import common.util.TypeConverter;

import deposito.CartelasDepositoHelper;
import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.actions.hueco.HuecoToPO;
import deposito.exceptions.NumeracionHuecoRepetidaException;
import deposito.forms.ElementoAsignableForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoVO;
import deposito.vos.TipoElementoVO;

public class GestionTipoAsignableAction extends BaseAction {

	private void setListaFormatosEnSession(List listaFormatos,
			HttpServletRequest request) {
		List formatosRegulares = new ArrayList();
		List formatosIrregulares = new ArrayList();
		removeInTemporalSession(request,
				DepositoConstants.LISTA_FORMATOS_REGULARES_KEY);
		removeInTemporalSession(request,
				DepositoConstants.LISTA_FORMATOS_IRREGULARES_KEY);
		FormatoHuecoVO unFormato = null;
		for (Iterator i = listaFormatos.iterator(); i.hasNext();) {
			unFormato = (FormatoHuecoVO) i.next();
			if (unFormato.isRegular())
				formatosRegulares.add(unFormato);
			else
				formatosIrregulares.add(unFormato);
		}
		setInTemporalSession(request,
				DepositoConstants.LISTA_FORMATOS_REGULARES_KEY,
				formatosRegulares);
		setInTemporalSession(request,
				DepositoConstants.LISTA_FORMATOS_IRREGULARES_KEY,
				formatosIrregulares);
	}

	public void altaAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		List listaFormatos = depositoBI.getFormatosVigentes();
		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO, request);

		// Nombre del tipo de elemento
		TipoElementoVO tipoElemento = depositoBI
				.getTipoElementoSingleton(asignableForm.getTipoElemento());
		if (tipoElemento != null)
			asignableForm.setNombreTipoElemento(tipoElemento.getNombre());

		setListaFormatosEnSession(listaFormatos, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_asignable"));
	}

	ActionErrors validarFormularioEdicionAsignable(ElementoAsignableForm form) {
		ActionErrors errors = new ActionErrors();
		if (!GenericValidator.isDouble(form.getLongitud()))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.LONGITUD_ASIGNABLE_INCORRECTA));
		if (!form.isFormatoRegular())
			if (!GenericValidator.isInt(form.getNumeroHuecos()))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.NUMERO_HUECOS_INCORRECTO));
		return errors.size() > 0 ? errors : null;
	}

	public void guardarAsignablesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		ActionErrors errors = validarFormularioEdicionAsignable(asignableForm);
		if (errors == null) {
			ServiceRepository services = getServiceRepository(request);
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			String idFormato = asignableForm.getIdFormato();
			FormatoHuecoVO formato = depositoBI.getFormatoHueco(idFormato);
			double longitudElemento = TypeConverter.toDouble(asignableForm
					.getLongitud());
			int numeroHuecos = 0;
			if (!formato.isRegular())
				numeroHuecos = TypeConverter.toInt(asignableForm
						.getNumeroHuecos());
			else if (formato.getLongitud().compareTo(
					new Double(TypeConverter.toDouble(asignableForm
							.getLongitud()))) > 0) { // mayor longitud del
														// formato que la del
														// formulario
				errors = new ActionErrors();
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								DepositoConstants.ERROR_NUMERACION_ASIGNABLE_SIN_HUECOS));
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mappings.findForward("edicion_asignable"));
				return;
			}

			try {
				ElementoNoAsignableVO elementoPadre = depositoBI
						.getElementoNoAsignable(asignableForm.getIdPadre());
				if (StringUtils.isBlank(asignableForm.getId())) {
					TipoElementoVO tipoElemento = depositoBI
							.getTipoElementoSingleton(asignableForm
									.getTipoElemento());

					ArchivoVO archivo = depositoBI
							.getArchivoXIdElemento(elementoPadre);
					if (archivo.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
							.getIdentificador()) {
						asignableForm.setNombreTipoElemento(tipoElemento
								.getNombre());

						// obtener el numero de asignables a crear
						int asignablesACrear = TypeConverter
								.toInt(asignableForm.getNumACrear());

						// obtener el numero de huecos a crear (diferenciar
						// regular e irregular)
						int numHuecosACrearPorAsignable = numeroHuecos;
						if (formato.isRegular()) {
							numHuecosACrearPorAsignable = ((int) (longitudElemento / formato
									.getLongitud().doubleValue()));
						}

						// obtener el Path del no asignable padre
						String pathPadre = elementoPadre.getPathName();
						List pathHuecos = new ArrayList();
						// int inicioNumeracion=(int)(Math.random()*40);
						long inicioNumeracion = depositoBI
								.getNsecSigNumericaHuecos(archivo.getId());
						if ((inicioNumeracion + "").length() > 16) {
							// error
						}

						// int
						// incioNumeracionAsignables=(depositoBI.getHijosElemento(elementoPadre.getIdElemento(),
						// elementoPadre.getIdTipoElemento())).size();
						int inicioNumeracionAsignables = (depositoBI
								.getNumOrden(elementoPadre,
										asignableForm.getTipoElemento()));

						for (int i = 1; i <= asignablesACrear; i++) {
							// a partir de este añadir la cadena correspondiente
							// para los elementos asignables
							String pathAsignable = pathPadre + "/"
									+ tipoElemento.getNombre() + " "
									+ (inicioNumeracionAsignables + i);
							// por ultimo para cada uno de los huecos a generar
							// generar su path y añadirlo a la lista para la jsp
							for (int j = 1; j <= numHuecosACrearPorAsignable; j++) {
								String pathHueco = pathAsignable + "/Hueco" + j;
								pathHuecos.add(pathHueco);

								// la numeracion se devuelve en el campo
								// correpondiente del form.
								// la numeracion correspondiente para esa fila
								asignableForm
										.getValues()
										.put(""
												+ ((i - 1)
														* numHuecosACrearPorAsignable + j),
												asignableForm
														.isFormatoRegular() ? ""
														+ inicioNumeracion
														: "");
								inicioNumeracion++;
							}
						}

						popLastInvocation(request);
						setInTemporalSession(request,
								DepositoConstants.ELEMENTO_DEPOSITO_KEY,
								elementoPadre);
						setInTemporalSession(request,
								DepositoConstants.FORMATO_KEY, formato);
						setInTemporalSession(request,
								DepositoConstants.LISTA_PATHS_HUECOS_KEY,
								pathHuecos);
						setInTemporalSession(request,
								DepositoConstants.EDITANDO_KEY, Boolean.FALSE);
						saveCurrentInvocation(
								KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO,
								request);
						setReturnActionFordward(
								request,
								mappings.findForward("cambiar_numeracion_asignables"));
						return;
					} else {
						List elementosCreados = depositoBI
								.crearElementosAsignables(elementoPadre,
										tipoElemento, formato.getId(),
										longitudElemento, numeroHuecos,
										TypeConverter.toInt(asignableForm
												.getNumACrear()));
						TreeView depositoTreeView = (TreeView) getFromTemporalSession(
								request, DepositoConstants.DEPOSITO_VIEW_NAME);
						if (depositoTreeView != null) {
							TreeNode parentNode = depositoTreeView
									.findNode(elementoPadre);
							for (Iterator i = elementosCreados.iterator(); i
									.hasNext();)
								depositoTreeView.insertNode(parentNode,
										(ElementoVO) i.next());
						}
					}

					popLastInvocation(request);
					ActionRedirect view = new ActionRedirect(
							mappings.findForward("ver_elemento"));
					view.addParameter("refreshView", "true");
					view.addParameter("node", elementoPadre.getItemPath(),
							false);
					setReturnActionFordward(request, view);
				} else {
					ElementoAsignableVO asignable = depositoBI
							.getElementoAsignable(asignableForm.getId());
					asignable.setLongitud(longitudElemento);
					asignable.setIdFormato(asignableForm.getIdFormato());
					asignable.setNumhuecos(numeroHuecos);
					depositoBI.guardarAsignable(asignable, elementoPadre);
					goBackExecuteLogic(mappings, form, request, response);
				}
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("edicion_asignable"));
			}
		} else {
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_asignable"));
		}
	}

	public void editarNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		// comprobar que se haya seleccionado alguna fila
		if (StringUtils.isEmpty(asignableForm.getSelectedHueco())) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									DepositoConstants.ERROR_EDITAR_NUMERACION_SIN_SELECCION)); // error
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		// añadir el parametro con la fila seleccionada a la url de la
		// invocacion.
		ClientInvocation client = getInvocationStack(request)
				.getLastClientInvocation();
		client.addParameter("selectedHueco", asignableForm.getSelectedHueco());

		asignableForm.setNumeracionAnteriorAEdionRegular(asignableForm
				.getMapFormValues(asignableForm.getSelectedHueco()));

		popLastInvocation(request);
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO,
				request);
		setInTemporalSession(request, DepositoConstants.EDITANDO_KEY,
				Boolean.TRUE);
		setReturnActionFordward(request,
				mappings.findForward("cambiar_numeracion_asignables"));
	}

	public void cancelarCambioNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		popLastInvocation(request);
		setInTemporalSession(request, DepositoConstants.EDITANDO_KEY,
				Boolean.FALSE);
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO,
				request);

		asignableForm.setMapFormValues(asignableForm.getSelectedHueco(),
				asignableForm.getNumeracionAnteriorAEdionRegular());
		asignableForm.setNumeracionAnteriorAEdionRegular("-1");
		setReturnActionFordward(request,
				mappings.findForward("cambiar_numeracion_asignables"));
	}

	public void confirmarCambioNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// solo entraran aqui con formato regular
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;

		ActionErrors errors = asignableForm.checkNumeracionRegular();

		if (errors != null) {
			obtenerErrores(request, true).add(errors); // error
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		setInTemporalSession(request, DepositoConstants.EDITANDO_KEY,
				Boolean.FALSE);

		List listaPathsHuecos = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_PATHS_HUECOS_KEY);
		if (StringUtils.isNumeric(asignableForm.getMapFormValues(asignableForm
				.getSelectedHueco()))) {
			boolean recalculando = false;
			long reenumeracion = 0;
			long numeracion = 0;
			for (int i = 1; i <= listaPathsHuecos.size(); i++) {
				numeracion = Long.parseLong(asignableForm.getMapFormValues(""
						+ i));
				if (i == asignableForm.getFilaSelectedHueco()) {
					recalculando = true;
					reenumeracion = numeracion;
				}

				// la misma tablaHash del CustomForm, almacena la numeracion con
				// y sin recalcular
				// para la numeracion recalculada se utilizan la misma key pero
				// con una 'R' como prefijo.
				asignableForm.setMapFormValues(Constants.PREFIX_R + i,
						(!recalculando) ? "" + numeracion : ""
								+ (reenumeracion++));
			}
		} else {
			popLastInvocation(request);
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO, request);
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		// ahora el formulario tiene los dos tipos de numeraciones
		// nuevo campo en el formulario para el tipo de numeracion seleccionada.
		asignableForm
				.setTipoCambioNumeracion(DepositoConstants.RECALCULAR_NUMERACION);

		// añadir el parametro con la fila seleccionada a la url de la
		// invocacion.
		// ClientInvocation
		// client=getInvocationStack(request).getLastClientInvocation();
		// client.addParameter("selectedHueco",
		// asignableForm.getSelectedHueco());

		// redireccionar a la nueva página
		popLastInvocation(request);
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO,
				request);
		// setInTemporalSession(request,DepositoConstants.EDITANDO_KEY,Boolean.FALSE);
		setReturnActionFordward(
				request,
				mappings.findForward("confirmar_numeracion_asignables_regulares"));
	}

	public void aceptarNumeracionIrregularExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// que ninguno de los valores este vacio, y que no este usado.
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		ActionErrors errors = asignableForm.checkNumeracionIrregulares();
		if (errors != null) {
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		// guardar en la request la hashmap de las numeraciones
		request.setAttribute(DepositoConstants.MAP_NUMERACION_KEY,
				asignableForm.getValues());
		setReturnActionFordward(request,
				mappings.findForward("crear_asignables_con_numeracion"));
	}

	public void modificarNumeracionRegularExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;

		ActionErrors errors = asignableForm
				.checkNumeracionRegularConTipoCambio();
		if (errors != null) {
			obtenerErrores(request, true).add(errors); // error
			popLastInvocation(request);
			setInTemporalSession(request, DepositoConstants.EDITANDO_KEY,
					Boolean.TRUE);
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO, request);
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		// modificar la hashmap del formulario exclusivamente con la numeracion
		// seleccionada

		String prefijo = "";
		if ((!StringUtils.isEmpty(asignableForm.getTipoCambioNumeracion()))
				&& asignableForm.getTipoCambioNumeracion().equals(
						Constants.PREFIX_R))
			prefijo = Constants.PREFIX_R;

		HashMap numeracionEscogida = new HashMap();
		if (StringUtils.isNumeric(asignableForm.getMapFormValues(asignableForm
				.getSelectedHueco()))) {
			for (int i = 1; i <= (int) (asignableForm.getValues().size() / 2); i++) {
				numeracionEscogida.put("" + i,
						asignableForm.getMapFormValues(prefijo + i));
			}
		} else
			numeracionEscogida = asignableForm.getValues();

		asignableForm.setValues(numeracionEscogida);

		popLastInvocation(request);
		setInTemporalSession(request, DepositoConstants.EDITANDO_KEY,
				Boolean.FALSE);
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO,
				request);
		setReturnActionFordward(request,
				mappings.findForward("cambiar_numeracion_asignables"));
	}

	public void aceptarNumeracionRegularExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		ActionErrors errors = asignableForm.checkNumeraciones();
		if (errors != null) {
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("cambiar_numeracion_asignables"));
			return;
		}

		// si llega aqui la nueva numeracion es numerica y ademas no vacia
		// solo falta quedarse con una numeracion u otra segun se haya
		// seleccionado
		// poner la numeracion seleccionada en la request y redireccionar a la
		// action encargada de realizar la creacion
		// de elementos asignables y huecos con numeracion.

		request.setAttribute(DepositoConstants.MAP_NUMERACION_KEY,
				asignableForm.getValues());
		setReturnActionFordward(request,
				mappings.findForward("crear_asignables_con_numeracion"));
	}

	public void crearAsignablesYHuecosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		HashMap numeracionPos = (HashMap) request
				.getAttribute(DepositoConstants.MAP_NUMERACION_KEY);

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		ElementoNoAsignableVO elementoPadre = depositoBI
				.getElementoNoAsignable(asignableForm.getIdPadre());
		TipoElementoVO tipoElemento = depositoBI
				.getTipoElementoSingleton(asignableForm.getTipoElemento());
		FormatoHuecoVO formato = depositoBI.getFormatoHueco(asignableForm
				.getIdFormato());
		double longitudElemento = TypeConverter.toDouble(asignableForm
				.getLongitud());
		int numeroHuecos = 0;
		if (!formato.isRegular())
			numeroHuecos = TypeConverter.toInt(asignableForm.getNumeroHuecos());

		List elementosCreados = null;
		try {
			elementosCreados = depositoBI.crearElementosAsignables(
					elementoPadre, tipoElemento, formato.getId(),
					longitudElemento, numeroHuecos,
					TypeConverter.toInt(asignableForm.getNumACrear()),
					numeracionPos);
		} catch (NumeracionHuecoRepetidaException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							DepositoConstants.ERROR_DEPOSITO_NUMERACION_BD_REPETIDA,
							e.getNumeracionRepetida()));
			obtenerErrores(request, true).add(errors);
			if (formato.isRegular())
				if (asignableForm.getTipoCambioNumeracion() == null)
					setReturnActionFordward(
							request,
							mappings.findForward("cambiar_numeracion_asignables"));
				else
					setReturnActionFordward(
							request,
							mappings.findForward("confirmar_numeracion_asignables_regulares"));
			else
				setReturnActionFordward(request,
						mappings.findForward("cambiar_numeracion_asignables"));
			return;
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_asignable"));
			return;
		}

		TreeView depositoTreeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		if (depositoTreeView != null) {
			TreeNode parentNode = depositoTreeView.findNode(elementoPadre);
			for (Iterator i = elementosCreados.iterator(); i.hasNext();)
				depositoTreeView.insertNode(parentNode, (ElementoVO) i.next());
		}

		popLastInvocation(request);

		ActionRedirect view = new ActionRedirect(
				mappings.findForward("ver_elemento"));
		view.addParameter("refreshView", "true");
		view.addParameter("node", elementoPadre.getItemPath(), false);
		setReturnActionFordward(request, view);
	}

	public void verAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_VER_ELEMENTO, request,
				treeView, viewAction, viewName);

		removeInTemporalSession(request, DepositoConstants.LISTA_HUECOS_KEY);
		removeInTemporalSession(request,
				DepositoConstants.ELEMENTO_ASIGNABLE_KEY);
		removeInTemporalSession(request,
				DepositoConstants.LISTA_FMT_REG_IGUAL_LONG);

		String pIdAsignable = request.getParameter("idAsignable");
		ElementoAsignableVO asignable = depositoBI
				.getElementoAsignable(pIdAsignable);
		List listaHuecos = depositoBI.getHuecos2(pIdAsignable);

		CollectionUtils.transform(listaHuecos, HuecoToPO.getInstance(services));

		List listaFormatos = depositoBI.getFormatosRegulares();
		if (listaFormatos != null && listaFormatos.size() > 0) {
			// Eliminar de la lista de formatos aquellos cuya longitud no
			// coincida con la del formato del asignable
			FormatoHuecoVO formatoAsignable = depositoBI
					.getFormatoHueco(asignable.getIdFormato());
			Iterator it = listaFormatos.iterator();
			while (it.hasNext()) {
				FormatoHuecoVO formatoRegular = (FormatoHuecoVO) it.next();
				if (formatoRegular.getLongitud().doubleValue() != formatoAsignable
						.getLongitud().doubleValue())
					it.remove();
			}
		}

		setInTemporalSession(request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY,
				new ElementoAsignablePO(asignable, services));

		setInTemporalSession(request, DepositoConstants.LISTA_HUECOS_KEY,
				listaHuecos);

		setInTemporalSession(request,
				DepositoConstants.LISTA_FMT_REG_IGUAL_LONG, listaFormatos);

		// Comprobar si el elemento es editable
		boolean signaturaAsociadaHueco = depositoBI
				.isEditableNumeracion(asignable.getIddeposito());
		boolean editable = true;
		if (signaturaAsociadaHueco) {
			editable = false;
		} else {
			for (int i = 0; editable && i < listaHuecos.size(); i++)
				editable = HuecoVO.LIBRE_STATE.equals(((HuecoVO) listaHuecos
						.get(i)).getEstado());
		}

		request.setAttribute(DepositoConstants.EDITABLE_KEY, new Boolean(
				editable));
		request.setAttribute(DepositoConstants.EDITABLE_NUMERACION_KEY,
				new Boolean(signaturaAsociadaHueco));

		invocation
				.setTitleNavigationToolBar(TitlesToolBar.DEPOSITO_VER_ELEMENTO
						+ asignable.getIdTipoElemento());
		setReturnActionFordward(request, mappings.findForward("info_asignable"));
	}

	public void editarAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ElementoAsignableForm asignableForm = (ElementoAsignableForm) form;
		String pIdAsignable = request.getParameter("idAsignable");

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		ElementoAsignableVO asignable = depositoBI
				.getElementoAsignable(pIdAsignable);
		asignableForm.setId(asignable.getId());
		asignableForm.setNombre(asignable.getNombre());
		asignableForm.setPathName(asignable.getPathName());
		asignableForm.setIdPadre(asignable.getIdpadre());
		asignableForm.setTipoElemento(asignable.getIdTipoElemento());
		FormatoHuecoVO formatoHueco = depositoBI.getFormatoHueco(asignable
				.getIdFormato());
		asignableForm.setLongitud(String.valueOf(asignable.getLongitud()));
		asignableForm.setIdFormato(formatoHueco.getId());
		asignableForm.setFormatoRegular(formatoHueco.isRegular());
		if (!formatoHueco.isRegular()) {
			asignableForm.setNumeroHuecos(String.valueOf(asignable
					.getNumhuecos()));
		}
		List listaFormatos = depositoBI.getFormatosVigentes();
		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_EDITAR_ELEMENTO, request);
		setListaFormatosEnSession(listaFormatos, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_asignable"));
	}

	public void eliminarAsignableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String pIdAsignable = request.getParameter("idAsignable");
		String pIdTipoElemento = request.getParameter("idTipoElemento");
		try {
			ElementoVO elementoEliminado = depositoBI.eliminarElemento(
					pIdAsignable, pIdTipoElemento);

			ActionRedirect view = new ActionRedirect(
					mappings.findForward("ver_elemento"));
			view.addParameter("refreshView", "true");
			TreeView depositoTreeView = (TreeView) getFromTemporalSession(
					request, DepositoConstants.DEPOSITO_VIEW_NAME);
			if (depositoTreeView != null) {
				TreeNode nodoEliminado = depositoTreeView
						.findNode(elementoEliminado);
				TreeNode parentNode = nodoEliminado.getParent();
				view.addParameter("node", parentNode.getNodePath(), false);
				depositoTreeView.setSelectedNode(parentNode);
				depositoTreeView.removeNode(nodoEliminado);
			}
			popLastInvocation(request);
			setReturnActionFordward(request, view);
		} catch (DepositoException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void selCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_SELECCION_CARTELAS, request);

		// Lista de huecos
		List huecos = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_HUECOS_KEY);

		// Número de huecos ocupados
		int numHuecosOcupados = 0;
		if (!CollectionUtils.isEmpty(huecos)) {
			HuecoVO hueco;
			for (int i = 0; i < huecos.size(); i++) {
				hueco = (HuecoVO) huecos.get(i);
				if (HuecoVO.OCUPADO_STATE.equals(hueco.getEstado()))
					numHuecosOcupados++;
			}
		}
		request.setAttribute(DepositoConstants.NUM_HUECOS_OCUPADOS_KEY,
				new Integer(numHuecosOcupados));

		setReturnActionFordward(request,
				mapping.findForward("seleccionar_cartelas"));
	}

	public void verCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String strHuecos = (String) request.getParameter("huecos");
		// cajas admite el siguiente formato 1,2,3,4,5-7,8, 9, 10, 12-3, ...
		// usar una expresion regular
		if (((ElementoAsignableForm) form).getSelHuecos().equals("2")
				&& !(Pattern
						.matches(Constants.EXPRESION_REGULAR_VAL_SEL_CARTELAS,
								strHuecos))) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CAMPO_NO_NUMERICO, Messages
							.getString(DepositoConstants.TEXTO_HUECOS,
									request.getLocale())));
			setReturnActionFordward(request,
					mapping.findForward("seleccionar_cartelas"));
			return;
		}

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_IMPRESION_CARTELAS, request);

		// Lista de huecos
		List huecos = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_HUECOS_KEY);

		// Huecos seleccionados
		IntervalOptions options = IntervalOptions.parse(request
				.getParameter("huecos"));

		removeInTemporalSession(request, DepositoConstants.LISTA_CARTELAS_KEY);
		removeInTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_CARTELAS_KEY);

		List cartelas = new ArrayList();
		HashMap mapUDocsHueco = new HashMap();
		if (!CollectionUtils.isEmpty(huecos)) {
			HuecoVO hueco = null;
			ServiceRepository services = getServiceRepository(request);
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();

			for (int i = 0; i < huecos.size(); i++) {
				hueco = (HuecoVO) huecos.get(i);
				if (HuecoVO.OCUPADO_STATE.equals(hueco.getEstado())
						&& options.isInOptions(hueco.getNumorden().intValue())) {

					// Obtener su lista de padres y generar los valores
					// necesarios para el informe
					LinkedList ltPadres = depositoBI.getListaPadresHueco(hueco);
					HashMap mapElementosDepositoNombresElemento = CartelasDepositoHelper
							.getMapElementosDepositoNombresElemento(ltPadres,
									depositoBI);
					hueco.setMapElementosDepositoNombresElemento(mapElementosDepositoNombresElemento);

					// Añadir el hueco
					cartelas.add(hueco);

					// Obtener la lista de unidades documentales del hueco
					List udocsHueco = depositoBI.getUDocsHueco(hueco
							.getHuecoID());
					// CollectionUtils.transform(udocsHueco,
					// UdocEnUIToPO.getInstance(services));
					mapUDocsHueco.put(hueco.getHuecoID().getIdpadre() + "_"
							+ hueco.getHuecoID().getNumorden(), udocsHueco);
				}
			}

			if (hueco != null) {
				String idArchivo = depositoBI.getUbicacion(
						hueco.getIddeposito()).getIdArchivo();
				setInTemporalSession(request, DepositoConstants.ID_ARCHIVO_KEY,
						idArchivo);
			}
		}

		setInTemporalSession(request, DepositoConstants.LISTA_CARTELAS_KEY,
				cartelas);
		setInTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_CARTELAS_KEY, mapUDocsHueco);

		setReturnActionFordward(request, mapping.findForward("ver_cartelas"));
	}

	public void actualizarCampoFormatoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Obtenemos los parámetros de la request y de la sesión
		ElementoAsignableVO asignable = (ElementoAsignableVO) getFromTemporalSession(
				request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY);
		String pIdFormato = request.getParameter("valorCampoFormato");

		// Actualizamos el formato del elemento al nuevo seleccionado siempre y
		// cuando se haya modificado
		if (pIdFormato != null && !pIdFormato.equals(asignable.getIdFormato())) {
			asignable.setIdFormato(pIdFormato);

			try {

				depositoBI.actualizarFormato(asignable);

			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

}
