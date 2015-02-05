package transferencias.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.SoporteForm;
import transferencias.vos.SoporteDocumentacionVO;
import transferencias.vos.UnidadDocumentalVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;

public class GestionSoportesAction extends BaseAction {

	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(GestionSoportesAction.class);

	public void altaSoporteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSistemaBI systemBI = services.lookupGestionSistemaBI();
		request.setAttribute(TransferenciasConstants.LISTA_FORMATOS_DOCUMENTO,
				systemBI.getListaFormatosDocumento());
		request.setAttribute(TransferenciasConstants.LISTA_SOPORTES,
				systemBI.getListaSoportes());
		saveCurrentInvocation(KeysClientsInvocations.SOPORTE, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_soporte"));
	}

	public void incorporarSoporteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SoporteForm soporteForm = (SoporteForm) form;

		ActionErrors errores = validarSoporteForm(request, soporteForm);

		if (errores.size() > 0) {
			ErrorsTag.saveErrors(request, errores);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			int numDocumentos = new Integer(soporteForm.getNumeroDocumentos())
					.intValue();
			udoc.addSoporte(numDocumentos, soporteForm.getFormato(),
					soporteForm.getSoporte());
			popLastInvocation(request);
			setReturnActionFordward(request, mappings.findForward("info_udoc"));
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
	}

	public void eliminarSoportesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SoporteForm soporteForm = (SoporteForm) form;
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		int[] soportesSeleccionados = soporteForm.getPosSoporteSeleccionado();
		if (soportesSeleccionados != null) {
			List soportesToRemove = new ArrayList();
			List soportes = udoc.getSoportes();
			for (int i = 0; i < soportesSeleccionados.length; i++)
				soportesToRemove.add((SoporteDocumentacionVO) soportes
						.get(soportesSeleccionados[i] - 1));
			soportes.removeAll(soportesToRemove);
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	public void edicionSoporteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SoporteForm soporteForm = (SoporteForm) form;
		// String soporte = soporteForm.getSoporte();
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		List soportes = udoc.getSoportes();
		int[] posSoporteSeleccionado = soporteForm.getPosSoporteSeleccionado();
		if (posSoporteSeleccionado != null && posSoporteSeleccionado.length > 0) {
			SoporteDocumentacionVO infoVolumen = (SoporteDocumentacionVO) soportes
					.get(posSoporteSeleccionado[0]);
			if (infoVolumen != null) {
				String volumen = new Integer(infoVolumen.getVolumen())
						.toString();
				soporteForm.setNumeroDocumentos(volumen);
				soporteForm.setFormato(infoVolumen.getFormato());
				soporteForm.setSoporte(infoVolumen.getSoporte());
			}
			saveCurrentInvocation(KeysClientsInvocations.SOPORTE, request);
			setReturnActionFordward(request,
					mappings.findForward("edicion_soporte"));
		}
	}

	public void guardarSoporteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SoporteForm soporteForm = (SoporteForm) form;
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		int numDocumentos = new Integer(soporteForm.getNumeroDocumentos())
				.intValue();
		udoc.addSoporte(numDocumentos, soporteForm.getFormato(),
				soporteForm.getSoporte());
		setInTemporalSession(request,
				TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		popLastInvocation(request);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	public void volverAvistaUnidadDocumentalExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		popLastInvocation(request);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	/**
	 * Realiza las validaciones del Interesado No Validado
	 * 
	 * @param interesadoForm
	 *            Formulario
	 * @return ActionErrors Errores detectados.
	 */
	protected ActionErrors validarSoporteForm(HttpServletRequest request,
			SoporteForm soporteForm) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(soporteForm.getNumeroDocumentos())
				|| !StringUtils.isNumeric(soporteForm.getNumeroDocumentos())) {
			errors.add(
					Constants.ERROR_INT,
					new ActionError(Constants.ERROR_INT, Messages.getString(
							Constants.ETIQUETA_VOLUMEN, request.getLocale())));
		} else {
			// Comrobar que no sea CERO.
			// Comprobar si cumple el formato Numérico > 0
			String numeroDocumentos = soporteForm.getNumeroDocumentos();

			Pattern mask = Pattern
					.compile(Constants.FORMATO_NUMERO_INT_MAYOR_CERO);
			Matcher matcher = mask.matcher(numeroDocumentos);

			if (!matcher.matches()) {
				errors.add(
						Constants.ERROR_INT_MAYOR_CERO,
						new ActionError(Constants.ERROR_INT_MAYOR_CERO,
								Messages.getString(Constants.ETIQUETA_VOLUMEN,
										request.getLocale())));
			}

		}

		return errors;
	}

}