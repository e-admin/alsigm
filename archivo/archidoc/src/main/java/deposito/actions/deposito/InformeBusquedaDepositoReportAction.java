package deposito.actions.deposito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.BusquedaUInsDepositoVO;
import es.archigest.framework.web.action.ArchigestActionProcessException;
import export.ExportPDFReportImpl;
import export.ExportReport;
import fondos.vos.BusquedaElementosVO;

public class InformeBusquedaDepositoReportAction extends BaseAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = "informeResultadosBusquedaDeposito";

	/**
	 * Ejecuta la lógica de la acción.
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
	private void createTableWithColumnTitles(HttpServletRequest request,
			ExportReport report) {
		float[] witdhColumns = new float[] { 10, 20, 50, 40, 30, 20 };
		String[] nameColumns = new String[] {
				Messages.getString(Constants.ETIQUETA_ROWNUM,
						request.getLocale()),
				Messages.getString(Constants.ETIQUETA_SIGNATURA,
						request.getLocale()),
				Messages.getString(Constants.ETIQUETA_UBICACION,
						request.getLocale()),
				Messages.getString(Constants.ETIQUETA_FONDO,
						request.getLocale()),
				Messages.getString(Constants.ETIQUETA_FORMATO,
						request.getLocale()),
				Messages.getString(Constants.ETIQUETA_FECHA_CREACION,
						request.getLocale()) };
		report.nuevaTabla(witdhColumns, nameColumns);
	}

	private void addRowFromVO(BusquedaUInsDepositoVO vo, ExportReport report)
			throws Exception {
		report.addCelda(vo.getSignaturaUI());
		report.addCelda(vo.getPath());
		report.addCelda(vo.getTituloFondo());
		report.addCelda(vo.getNombreFormato());
		report.addCelda(vo.getFcreacionString());
	}

	protected void executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Usuario conectado
		AppUser appUser = getAppUser(request);
		String tituloInforme = Messages
				.getString(Constants.TITULO_INFORME_BUSQUEDA_DEPOSITO,
						request.getLocale());
		String numResultados = Messages.getString(
				Constants.NUM_RESULTADOS_INFORME_BUSQUEDA_DEPOSITO,
				request.getLocale());
		List idsToShow = null;
		List listaElementos = null;

		BusquedaElementosVO busquedaElementosVO = (BusquedaElementosVO) getFromTemporalSession(
				request, DepositoConstants.VO_BUSQUEDA_DEPOSITO);
		List listaIdsElementos = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_IDS_ELEMENTOS);
		if (listaIdsElementos == null)
			listaIdsElementos = new ArrayList();
		int numeroElementosPorConsulta = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
				.getNumMaxResultados();
		int totalPages = (int) Math.ceil(listaIdsElementos.size()
				/ (numeroElementosPorConsulta * 1.0));

		// obtener la listas de Ids de la session
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestorEstructuraDepositoBI estructuraBI = services
					.lookupGestorEstructuraDepositoBI();
			// GestionInfoSistemaBI infoSistema =
			// services.lookupInfoSistemaBI();
			ExportReport report = getExportReport(request);
			// PdfReport report = new
			// PdfReport(infoSistema.getManejadorIText());

			report.setInfoReport(tituloInforme, appUser.getNombreCompleto());
			report.setNumResultadosReport(numResultados,
					listaIdsElementos.size());
			createTableWithColumnTitles(request, report);

			int rowNum = 1;
			for (int actualPage = 1; actualPage <= totalPages; actualPage++) {
				Object boolCancelProgressBar = getFromTemporalSession(request,
						Constants.CANCEL_PROGRESSBAR_KEY);
				if (boolCancelProgressBar != null
						&& ((Boolean) boolCancelProgressBar).booleanValue()) {
					break;
				}
				idsToShow = ListUtils.getItems(actualPage,
						numeroElementosPorConsulta, listaIdsElementos);
				listaElementos = null;
				listaElementos = estructuraBI.getUnidsInstalacion(idsToShow,
						busquedaElementosVO);
				// listaIdsElementos.subList(0, idsToShow.size()).clear();
				idsToShow = null;

				int porcentaje = (int) Math.round(actualPage * 100
						/ (totalPages * 1.0));
				setInTemporalSession(request,
						Constants.PROGRESSBAR_PERCENT_KEY, new Integer(
								porcentaje));

				for (Iterator it = listaElementos.iterator(); it.hasNext();) {
					boolCancelProgressBar = getFromTemporalSession(request,
							Constants.CANCEL_PROGRESSBAR_KEY);
					if (boolCancelProgressBar != null
							&& ((Boolean) boolCancelProgressBar).booleanValue()) {
						break;
					}
					BusquedaUInsDepositoVO vo = (BusquedaUInsDepositoVO) it
							.next();
					it.remove();
					report.addCelda(String.valueOf(rowNum++));
					addRowFromVO(vo, report);
				}
			}
			if (!(report instanceof ExportPDFReportImpl))
				report.setNumResultadosReport(numResultados,
						listaIdsElementos.size());

			// se escribe el pdf;
			report.enviaReport(request, response, REPORT_NAME);

			// Cuando se llega al final del proceso de exportación, hay que
			// poner el porcentaje a 100%
			setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
					new Integer(105));

			// setReturnActionFordward(request, new
			// ActionForward(request.getContextPath()+"/action/gestionDepositoAction?method=nuevaBusquedaUI"));
			setReturnActionFordward(request, null);
		} catch (Exception e) {
			logger.error("No se ha podido generar informe " + REPORT_NAME, e);
			throw new ArchigestActionProcessException(e, "Generando informe "
					+ REPORT_NAME);
		}
	}
}
