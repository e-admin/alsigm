package fondos.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.actions.BusquedaBaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.vos.ElementoCFPO;
import descripcion.vos.ElementoCFVO;
import es.archigest.framework.web.action.ArchigestActionProcessException;
import export.ExportReport;
import fondos.FondosConstants;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.BusquedaElementosVO;

public class InformeBusquedaFondosReportAction extends BusquedaBaseAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = "informeResultadosBusqueda";

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
	private void createTableWithColumnTitles(ExportReport report,
			HttpServletRequest request) {
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		float[] witdhColumns = new float[0];
		String[] nameColumns = new String[0];

		int i = 0;
		Map camposSalida = busqueda.getMapSalida();
		for (Iterator it = camposSalida.entrySet().iterator(); it.hasNext(); i++) {
			Map.Entry entry = (Map.Entry) it.next();
			CampoBusqueda campo = (CampoBusqueda) entry.getValue();
			if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO)) {
				witdhColumns = addColumnWidth(witdhColumns, 15);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_CODIGO));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE)) {
				witdhColumns = addColumnWidth(witdhColumns, 15);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_NUM_EXPEDIENTE));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO)) {
				witdhColumns = addColumnWidth(witdhColumns, 60);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_TITULO));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO)) {
				witdhColumns = addColumnWidth(witdhColumns, 20);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_FONDO_JSP));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL)) {
				witdhColumns = addColumnWidth(witdhColumns, 20);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_NIVEL));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_ESTADO)) {
				witdhColumns = addColumnWidth(witdhColumns, 15);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_ESTADO));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA)) {
				witdhColumns = addColumnWidth(witdhColumns, 30);
				nameColumns = addColumnTitle(
						nameColumns,
						Messages.getString(Constants.ETIQUETA_CODIGO_REFERENCIA));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR)) {
				witdhColumns = addColumnWidth(witdhColumns, 25);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_PRODUCTOR));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL)) {
				witdhColumns = addColumnWidth(witdhColumns, 15);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_FECHA_INICIAL));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL)) {
				witdhColumns = addColumnWidth(witdhColumns, 15);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_FECHA_FINAL));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS)) {
				witdhColumns = addColumnWidth(witdhColumns, 30);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_RANGO));
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA)) {
				witdhColumns = addColumnWidth(witdhColumns, 20);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_SIGNATURA));
			} else if (campo
					.getNombre()
					.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE)) {
				witdhColumns = addColumnWidth(witdhColumns, 20);
				nameColumns = addColumnTitle(nameColumns,
						Messages.getString(Constants.ETIQUETA_RANGO));
			}
		}
		report.nuevaTabla(witdhColumns, nameColumns);
	}

	private float[] addColumnWidth(float[] witdhColumns, float width) {
		witdhColumns = (float[]) ArrayUtils.add(witdhColumns, width);
		return witdhColumns;
	}

	private String[] addColumnTitle(String[] nameColumns, String title) {
		nameColumns = (String[]) ArrayUtils.add(nameColumns, title);
		return nameColumns;
	}

	private void addRowFromVO(ElementoCFVO vo, ExportReport report,
			HttpServletRequest request) throws Exception {
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);
		Map camposSalida = busqueda.getMapSalida();
		for (Iterator it = camposSalida.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			CampoBusqueda campo = (CampoBusqueda) entry.getValue();
			if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO)) {
				report.addCelda(vo.getCodigo());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE)) {
				report.addCelda(vo.getNumexp());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO)) {
				report.addCelda(vo.getTitulo());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO)) {
				report.addCelda(vo.getNombreFondo());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL)) {
				report.addCelda(vo.getNombre()); // Nombre del nivel
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_ESTADO)) {
				report.addCelda("" + vo.getEstado()); // traduccion del id por
														// el nombre del estado
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA)) {
				report.addCelda(vo.getCodReferencia());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR)) {
				report.addCelda(vo.getNombreProductor());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL)) {
				report.addCelda(vo.getValorFInicial());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL)) {
				report.addCelda(vo.getValorFFinal());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS)) {
				report.addCelda(((ElementoCFPO) vo).getNombreRangos());
			} else if (campo.getNombre().equals(
					CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA)) {
				report.addCelda(vo.getSignaturaudoc());
			} else if (campo
					.getNombre()
					.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE)) {
				if (vo.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA)
					report.addCelda(((ElementoCFPO) vo).getNombreRangos());
				else
					report.addCelda(vo.getNumexp());
			}
		}
	}

	protected void executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Usuario conectado
		AppUser appUser = getAppUser(request);
		String tituloInforme = Messages
				.getString(Constants.TITULO_INFORME_BUSQUEDA_FONDOS);
		List idsToShow = null;
		List listaElementos = null;

		final GestionCuadroClasificacionBI serviceCF = getGestionCuadroClasificacionBI(request);

		BusquedaElementosVO busquedaElementosVO = (BusquedaElementosVO) getFromTemporalSession(
				request, FondosConstants.VO_BUSQUEDA_FONDOS);
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);
		final List listaIdsElementos = getListaIdsElementos(request);
		int numeroElementosPorConsulta = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
				.getNumMaxResultados();

		int totalPages = (int) Math.ceil(listaIdsElementos.size()
				/ (numeroElementosPorConsulta * 1.0));

		// obtener la listas de Ids de la session
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			ExportReport report = getExportReport(request);
			if (report == null)
				return;
			report.setInfoReport(tituloInforme, appUser.getNombreCompleto());
			createTableWithColumnTitles(report, request);

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
				listaElementos = descripcionBI.getElementos(
						StringUtils.toString(idsToShow.toArray()),
						busquedaElementosVO, busqueda);
				final ServiceRepository servicios = getServiceRepository(request);
				final Busqueda busqueda2 = busqueda;
				CollectionUtils.transform(listaElementos, new Transformer() {
					public Object transform(Object obj) {

						ElementoCFPO po = new ElementoCFPO(servicios,
								busqueda2, getProductores(busqueda2,
										listaIdsElementos, serviceCF), null);
						try {
							PropertyUtils.copyProperties(po, obj);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
						return po;
					}
				});

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
					Object obj = it.next();
					ElementoCFVO vo = (ElementoCFVO) obj;
					it.remove();
					addRowFromVO(vo, report, request);
				}
			}

			setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
					new Integer(100));
			// se escribe el pdf;
			report.enviaReport(request, response, REPORT_NAME);

			String uriRetorno = (String) getFromTemporalSession(request,
					FondosConstants.INFORME_BUSQUEDA_URI_RETORNO);
			setReturnActionFordward(request,
					new ActionForward(request.getContextPath() + uriRetorno));
		} catch (Exception e) {
			logger.error("No se ha podido generar informe " + REPORT_NAME, e);
			throw new ArchigestActionProcessException(e, "Generando informe "
					+ REPORT_NAME);
		}
	}
}
