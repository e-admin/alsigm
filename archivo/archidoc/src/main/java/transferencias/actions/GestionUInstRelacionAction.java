package transferencias.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.vos.DetalleVO;
import transferencias.EstadoCotejo;
import transferencias.TransferenciasConstants;
import transferencias.forms.UInstRelacionForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import util.CollectionUtils;

import common.ConfigConstants;
import common.Constants;
import common.Conversor;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;

import deposito.actions.hueco.UInsDepositoToPO;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.HuecoVO;
import deposito.vos.UInsDepositoVO;
import fondos.model.ElementoCuadroClasificacion;

/**
 * Gestiona las unidades de instalación de una relación entre Archivos
 * 
 * @author lucas
 * 
 */
public class GestionUInstRelacionAction extends BaseAction {
	private static String TIPO_ELECTRONICAS = "E";
	private static String TIPO_FISICAS = "F";

	/**
	 * Muestra las unidades de instalación disponibles.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void selUInstExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		removeInTemporalSession(
				request,
				TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY);

		removeInTemporalSession(
				request,
				TransferenciasConstants.LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS);

		String tipo = request.getParameter("tipo");

		UInstRelacionForm formulario = (UInstRelacionForm) form;

		ClientInvocation invocation;

		if (TIPO_ELECTRONICAS.equals(tipo)) {
			invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_ADD_UDOCS_ELECTRONICAS,
					request);
			formulario.setElectronicas(true);
		} else {
			invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_ADD_UINST_REEA,
					request);
			formulario.setElectronicas(false);

		}

		invocation.setAsReturnPoint(true);

		String idRelacionEntrega = request
				.getParameter("idrelacionseleccionada");

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		GestionPrevisionesBI previsionesBI = services
				.lookupGestionPrevisionesBI();

		// Obtener los datos de la relación de entrega
		RelacionEntregaVO relacionEntrega = relacionesBI
				.getRelacionXIdRelacion(idRelacionEntrega);

		if (relacionEntrega != null) {
			formulario.setIdRelacionEntrega(relacionEntrega.getId());

			DetallePrevisionVO detallePrevision = previsionesBI
					.getDetallePrevision(relacionEntrega.getIddetprevision());

			String anioDesde = detallePrevision.getAnoIniUdoc();
			String anioHasta = detallePrevision.getAnoFinUdoc();

			Date fechaFinalDesde = null;
			Date fechaFinalHasta = null;

			if (anioDesde != null) {
				try {
					fechaFinalDesde = DateUtils.getFirstDayOfYear(Conversor
							.toInt(anioDesde));
				} catch (Exception e) {
					logger.error("Error al convertir el año Inicial de las Unidades Documentales");
				}
			}

			if (anioHasta != null) {
				try {
					fechaFinalHasta = DateUtils.getFirstDayOfYear(Conversor
							.toInt(anioHasta) + 1);
				} catch (Exception e) {
					logger.error("Error al convertir el año Final de las Unidades Documentales");

				}
			}

			String idArchivoEmisor = relacionEntrega.getIdarchivoremitente();
			String idSerieOrigen = relacionEntrega.getIdserieorigen();

			List listaUUinst = null;

			List listaUDocsElectronicas = null;

			if (TIPO_FISICAS.equals(tipo)) {

				listaUUinst = relacionesBI.getUInstParaRelacionEntreArchivos(
						fechaFinalDesde, fechaFinalHasta, idArchivoEmisor,
						idSerieOrigen, relacionEntrega.getIdformatoui(),
						relacionEntrega.getIdNivelDocumental());

				CollectionUtils.transform(listaUUinst,
						UInsDepositoToPO.getInstance(services));

				setInTemporalSession(
						request,
						TransferenciasConstants.LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS,
						listaUUinst);
			}

			if (TIPO_ELECTRONICAS.equals(tipo)) {
				removeInTemporalSession(
						request,
						TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY);

				listaUDocsElectronicas = relacionesBI
						.getUDocsElectronicasParaRelacionEntreArchivos(
								fechaFinalDesde, fechaFinalHasta,
								idArchivoEmisor, idSerieOrigen);

				setInTemporalSession(
						request,
						TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY,
						listaUDocsElectronicas);

			}

			setReturnActionFordward(request,
					mapping.findForward("add_unidades_instalacion"));
		}
	}

	/**
	 * Almacena en Sesión los datos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void aceptarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UInstRelacionForm formulario = (UInstRelacionForm) form;
		int[] seleccionados = formulario.getElementosSeleccionados();
		int[] electronicos = formulario.getElementosElectronicosSel();

		ActionErrors errores = null;

		int orden = 1;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		if (seleccionados != null && seleccionados.length > 0) {
			List listaUinst = (List) getFromTemporalSession(
					request,
					TransferenciasConstants.LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS);

			// Lista del con objetos UnidadInstalacionReeaVO
			List listaUinstSeleccionadas = new ArrayList();

			List listaUinstBD = relacionesBI
					.getUnidadesInstalacionEntreArchivos(formulario
							.getIdRelacionEntrega());

			if (!CollectionUtils.isEmpty(listaUinstBD)) {
				orden = listaUinstBD.size() + 1;
			}

			for (int i = 0; i < seleccionados.length; i++) {

				int posicion = seleccionados[i];
				UInsDepositoVO uInsDepositoVO = (UInsDepositoVO) listaUinst
						.get(posicion);

				UnidadInstalacionReeaVO unidadInstalacionReeaVO = new UnidadInstalacionReeaVO();

				// Comprobar si alguna de las unidades está prestada.

				// Obtener las unidades documentales de la unidad de instalación
				List listaUdocsNoDisponibles = depositoBI
						.getUdocsNoDisponiblesParaRelacion(uInsDepositoVO
								.getId());

				if (ListUtils.isEmpty(listaUdocsNoDisponibles)) {

					// Establecer los valores de los VO
					unidadInstalacionReeaVO.setIduideposito(uInsDepositoVO
							.getId());
					unidadInstalacionReeaVO.setIdRelEntrega(formulario
							.getIdRelacionEntrega());
					unidadInstalacionReeaVO.setOrden(orden);
					unidadInstalacionReeaVO
							.setEstadoCotejo(EstadoCotejo.PENDIENTE
									.getIdentificador());
					unidadInstalacionReeaVO.setNotasCotejo(null);
					unidadInstalacionReeaVO
							.setDevolucion(Constants.FALSE_STRING);
					unidadInstalacionReeaVO.setIdFormato(uInsDepositoVO
							.getIdformato());

					// La signatura solo se asigna si es signaturación única
					if (!ConfigConstants.getInstance()
							.getSignaturacionPorArchivo()) {
						unidadInstalacionReeaVO.setSignaturaUI(uInsDepositoVO
								.getSignaturaui());
					}
					unidadInstalacionReeaVO.setSignaturaUIOrigen(uInsDepositoVO
							.getSignaturaui());

					// Información del hueco origen de la unidad de instalación
					HuecoVO hueco = depositoBI
							.getHuecoUInstalacion(uInsDepositoVO.getId());
					unidadInstalacionReeaVO.setPathdepositoorigen(hueco
							.getPath());
					unidadInstalacionReeaVO.setNumOrdenHuecoOrigen(hueco
							.getNumorden());
					unidadInstalacionReeaVO.setIdElemaPadreHuecoOrigen(hueco
							.getIdElemAPadre());
					listaUinstSeleccionadas.add(unidadInstalacionReeaVO);
					orden++;
				} else {
					if (errores == null) {
						errores = new ActionErrors();

						// Mensaje General
						errores.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										Constants.ERROR_NO_PERMITIR_ADD_UDOCS_PRESTADAS));
					}

					Iterator iterador = listaUdocsNoDisponibles.iterator();

					// Información de la Unidad de Instalación
					errores.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Constants.ERROR_CAJA_CON_UDOCS_PRESTADAS,
							uInsDepositoVO.getSignaturaui()));

					while (iterador.hasNext()) {
						DetalleVO detalle = (DetalleVO) iterador.next();

						String estado = Messages.getString(
								Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
										+ detalle.getEstado(),
								request.getLocale());
						// Información de la Unidad Documental
						errores.add(ActionErrors.GLOBAL_ERROR,
								new ActionError(
										Constants.ERROR_UDOC_EN_PRESTAMOS,
										detalle.getSignaturaudoc(), estado));
					}
				}
			}
			relacionesBI.insertarUinstReea(listaUinstSeleccionadas);
		}

		if (!ArrayUtils.isEmpty(electronicos)) {
			// Añadir las Unidades Documentales Electrónicas
			List listaUDocsElectronicas = (List) getFromTemporalSession(
					request,
					TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY);

			for (int i = 0; i < electronicos.length; i++) {
				int posicion = electronicos[i];

				ElementoCuadroClasificacion elemento = (ElementoCuadroClasificacion) listaUDocsElectronicas
						.get(posicion);

				// Crear la unidad Documental
				UDocElectronicaVO udocElectronicaVO = new UDocElectronicaVO(
						elemento.getId(), formulario.getIdRelacionEntrega(),
						orden, EstadoCotejo.PENDIENTE.getIdentificador());

				relacionesBI
						.addUDocElectronicaARelacionEntreArchivos(udocElectronicaVO);

				orden++;
			}

		}

		updateEstadoRelacionRechazadaInSession(request);

		if (errores != null) {

			obtenerErrores(request, true).add(errores);
		}

		ActionRedirect redirectAVistaRelacion = new ActionRedirect(
				mapping.findForward("ver_relacion_entre_archivos"));
		redirectAVistaRelacion.setRedirect(true);
		redirectAVistaRelacion.addParameter("idrelacionseleccionada",
				formulario.getIdRelacionEntrega());
		setReturnActionFordward(request, redirectAVistaRelacion);

	}

	private void updateEstadoRelacionRechazadaInSession(
			HttpServletRequest request) {
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacionEntrega.isRechazada())
			relacionEntrega
					.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
	}
}
