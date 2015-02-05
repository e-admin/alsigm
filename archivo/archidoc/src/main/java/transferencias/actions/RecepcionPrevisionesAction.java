package transferencias.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.vos.ArchivoVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.RecepcionPrevisionesForm;
import transferencias.vos.PrevisionVO;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.tags.calendar.CalendarCell;
import common.tags.calendar.CalendarLegend;
import common.tags.calendar.config.CalendarTagConfig;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;

/**
 * Action con la logica a ejecutar para el tratamiento de las previsiones
 * enviadas
 * 
 */
public class RecepcionPrevisionesAction extends BaseAction {

	static Logger logger = Logger.getLogger(RecepcionPrevisionesAction.class);

	public void aceptarprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_ACEPTACION_PREVISION,
				request);
		preparaDatosComunesVistaAceptarRechazar(request, form);
		RecepcionPrevisionesForm aceptacionForm = (RecepcionPrevisionesForm) form;
		aceptacionForm.setMethodToPerform("guardaraceptarprevision");
		setReturnActionFordward(request,
				mapping.findForward("aceptacion_prevision"));
	}

	public void preparaDatosComunesVistaAceptarRechazar(
			HttpServletRequest request, ActionForm form) {
		// colocar en session temporal datos de la prevision
		String idPrevision = ((RecepcionPrevisionesForm) form).getIdPrevision();
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		PrevisionVO previsionVO = previsionesService.getPrevision(idPrevision);
		PrevisionPO previsionPO = (PrevisionPO) PrevisionToPO.getInstance(
				services).transform(previsionVO);

		((RecepcionPrevisionesForm) form).setMotivorechazo(previsionPO
				.getMotivorechazo());

		setInTemporalSession(request, TransferenciasConstants.PREVISION_KEY,
				previsionPO);
	}

	public void guardarrechazoprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// actualizar fechas de transferencia
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		RecepcionPrevisionesForm frm = (RecepcionPrevisionesForm) form;
		if (GenericValidator.isBlankOrNull(frm.getMotivorechazo())) {
			obtenerErrores(request, true).add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOTAS,
									request.getLocale())));
		}
		if (obtenerErrores(request, false) == null) {
			try {
				previsionesService.rechazarPrevision(frm.getIdPrevision(),
						frm.getMotivorechazo());
				goBackExecuteLogic(mapping, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("aceptacion_prevision"));
			}
		} else {
			setReturnActionFordward(request,
					mapping.findForward("rechazo_prevision"));
		}
	}

	public void rechazarprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		preparaDatosComunesVistaAceptarRechazar(request, form);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_RECHAZO_PREVISION,
				request);
		RecepcionPrevisionesForm aceptacionForm = (RecepcionPrevisionesForm) form;
		aceptacionForm.setMethodToPerform("guardarrechazoprevision");
		setReturnActionFordward(request,
				mapping.findForward("rechazo_prevision"));
	}

	public void guardaraceptarprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// actualizar fechas de transferencia
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		RecepcionPrevisionesForm frm = (RecepcionPrevisionesForm) form;
		ActionErrors errors = validateFechasTrans(request,
				frm.getFechainitrans(), frm.getFechafintrans());
		if (errors == null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						Constants.FORMATO_FECHA);
				try {
					Date fechainit = dateFormat.parse(frm.getFechainitrans());
					Date fechafin = dateFormat.parse(frm.getFechafintrans());
					previsionesService.aceptarPrevision(frm.getIdPrevision(),
							fechainit, fechafin);
					// No puede darse porque se ha hecho la validacion del
					// formulario
				} catch (ParseException pe) {
				}
				goBackExecuteLogic(mapping, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("rechazo_prevision"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("rechazo_prevision"));
		}
	}

	public void mostrarMesExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CALENDARIO_PREVISIONES_MES,
				request);
		RecepcionPrevisionesForm recepcionPrevisionesForm = (RecepcionPrevisionesForm) form;
		String mesAttribute = recepcionPrevisionesForm.getMes();
		CalendarTagConfig calendarConfig = (CalendarTagConfig) getFromTemporalSession(
				request, TransferenciasConstants.CALENDAR_CONFIG_KEY);
		Map months = calendarConfig.getMonths();
		HashMap mes = (HashMap) months.get(mesAttribute);
		setInTemporalSession(request, TransferenciasConstants.CALENDAR_MES_KEY,
				mes);
		setReturnActionFordward(request,
				mapping.findForward("mostrar_calendario_previsiones_mes"));
	}

	public void mostrarCalendarioPrevisionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = getInvocationStack(request)
				.getLastClientInvocation();

		RecepcionPrevisionesForm recepcionPrevisionesForm = (RecepcionPrevisionesForm) form;
		invocation.addParameters(((RecepcionPrevisionesForm) form).getMap());
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CALENDARIO_PREVISIONES,
				request);

		String idArchivo = request.getParameter("idArchivo");
		recepcionPrevisionesForm.setIdArchivo(idArchivo);

		GestionArchivosBI archivoBI = getGestionArchivosBI(request);

		List custodyArchiveList = getServiceRepository(request)
				.getServiceClient().getCustodyArchiveList();

		String[] idsArchivo = new String[0];

		removeInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS);

		List archivos = archivoBI.getArchivosXId(custodyArchiveList.toArray());

		if (!ListUtils.isEmpty(archivos)) {
			// Si no hay idArchivo, se cargan todos.
			if (idArchivo == null) {
				for (Iterator iterator = custodyArchiveList.iterator(); iterator
						.hasNext();) {
					String id = (String) iterator.next();

					idsArchivo = (String[]) ArrayUtils.add(idsArchivo, id);
				}
			} else {
				idsArchivo = new String[] { idArchivo };
			}

			if (archivos.size() > 1) {

				// Añadir el Archivo Todos
				ArchivoVO archivo = new ArchivoVO();
				archivo.setId(Constants.BLANK);
				archivo.setNombre(Messages.getString(Constants.ETIQUETA_TODOS,
						request.getLocale()));

				archivos.add(0, archivo);

				setInTemporalSession(request,
						ControlAccesoConstants.LISTA_ARCHIVOS, archivos);
			}
		}

		cargarCalendarioPrevisiones(request, idsArchivo);
		setReturnActionFordward(request,
				mapping.findForward("mostrar_calendario_previsiones"));
	}

	public ActionErrors validateFechasTrans(HttpServletRequest request,
			String fechainitrans, String fechafintrans) {
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.FORMATO_FECHA);

		if (!DateUtils.isDate(fechainitrans)) {
			errors.add(
					common.Constants.ERROR_DATE,
					new ActionError(common.Constants.ERROR_DATE, Messages
							.getString(Constants.ETIQUETA_FECHA_INICIO,
									request.getLocale())));
		} else if (!DateUtils.isDate(fechafintrans)) {
			errors.add(
					common.Constants.ERROR_DATE,
					new ActionError(common.Constants.ERROR_DATE, Messages
							.getString(Constants.ETIQUETA_FECHA_FIN,
									request.getLocale())));
		} else {
			try {
				Date fechaini = dateFormat.parse(fechainitrans);
				Date fechafin = dateFormat.parse(fechafintrans);
				if (fechafin.compareTo(fechaini) < 0) {
					errors.add(
							Constants.ERROR_INITDATE_AFTER_ENDDATE,
							new ActionError(
									Constants.ERROR_INITDATE_AFTER_ENDDATE,
									Messages.getString(
											Constants.ETIQUETA_FECHA_FIN,
											request.getLocale())));
				}
				// no puede darse puesto que ya emos validado que sean fechas
				// con el formato correcto
			} catch (ParseException e) {
			}
		}
		return errors.size() > 0 ? errors : null;
	}

	/**
	 * Carga los días ocupados entre una fecha inicial y una final
	 * 
	 * @param fechaIni
	 *            fecha inicial
	 * @param fechaFin
	 *            fecha final
	 * @param mes
	 *            Hashmap con los datos del mes
	 */
	private void cargarDiasOcupados(Date fechaIni, Date fechaFin, HashMap mes) {
		// Comprobar que no haya eventos incompatibles en el intervalo

		Calendar fechaIniIt = GregorianCalendar.getInstance();
		fechaIniIt.setTime(fechaIni);

		Calendar fechaFinIt = GregorianCalendar.getInstance();
		fechaFinIt.setTime(fechaFin);

		while (DateUtils.isFechaMenorOIgual(fechaIniIt, fechaFinIt)) {

			String cellCode = CalendarTagConfig.getMapDateCode(fechaIniIt
					.getTime());
			CalendarCell cell = (CalendarCell) mes.get(cellCode);
			if (cell != null) {
				// La celda ya existe, cambiar el color
				cell.incNumPrevisiones();

			} else {
				// No existe, crearla
				cell = new CalendarCell(fechaIniIt.getTime(), null, null,
						false, 1);
			}

			// Guardar la celda
			mes.put(cellCode, cell);

			// Si se llega al último día del año salir del buble
			if ((fechaIniIt.get(GregorianCalendar.MONTH) == 11)
					&& (fechaIniIt.get(GregorianCalendar.DATE) == 31))
				break;

			fechaIniIt.roll(GregorianCalendar.DAY_OF_YEAR, true);
		}
	}

	/**
	 * Permite generar la leyenda del calendario
	 * 
	 * @param request
	 *            Petición actual
	 * @return Lista de objetos de leyenda
	 */
	private List generarLeyenda(HttpServletRequest request) {
		List leyenda = new ArrayList();

		// Obtener los colores y el intervalo de colores
		String[] colores = ConfigConstants.getInstance()
				.getColoresCalendarioPrevisiones();
		int coloresInterval = ConfigConstants.getInstance()
				.getIntervalCalendarioPrevisiones();

		for (int i = 0; i < colores.length; i++) {
			int inicio = (i * coloresInterval) + 1;
			int fin = (i + 1) * coloresInterval;

			CalendarLegend legend = null;
			if (inicio != fin)
				legend = new CalendarLegend(String.valueOf(inicio),
						String.valueOf(fin), colores[i]);
			else
				legend = new CalendarLegend(String.valueOf(inicio),
						Constants.STRING_EMPTY, colores[i]);

			if (i == (colores.length - 1))
				legend.setUltimo(true);

			leyenda.add(legend);
		}

		return leyenda;
	}

	/**
	 * Carga el calendario de previsiones
	 * 
	 * @param request
	 *            Petición actual
	 */
	private void cargarCalendarioPrevisiones(HttpServletRequest request,
			String[] idsArchivo) {

		// Obtener el año y mes actual
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		int actualYear = calendar.get(GregorianCalendar.YEAR);

		CalendarTagConfig config = new CalendarTagConfig();
		config.setYear(new Integer(actualYear));
		config.setFunction("selectDayCalendar");
		config.setId("CalendarioPrevisiones");
		config.setNumMonths(new Integer(6));

		// Obtener los nombres de los meses y de los días
		String monthNames = Messages.getString(Constants.CALENDAR_MONTH_NAMES,
				request.getLocale());
		String[] arrayMonthNames = monthNames.split(",");
		config.setMonthNames(arrayMonthNames);
		String dayNames = Messages.getString(Constants.CALENDAR_DAY_NAMES,
				request.getLocale());
		String[] arrayDayNames = dayNames.split(",");
		config.setDayNames(arrayDayNames);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();

		List previsiones = previsionesService
				.getPrevisionesCalendarioPrevisiones(
						String.valueOf(actualYear), idsArchivo);

		// Transformar las previsiones en una lista de PO
		CollectionUtils.transform(previsiones,
				PrevisionToPO.getInstance(services));

		HashMap meses = new HashMap();
		if ((previsiones != null) && (!previsiones.isEmpty())) {
			ListIterator it = previsiones.listIterator();
			while (it.hasNext()) {
				PrevisionPO prevision = (PrevisionPO) it.next();

				// Obtener el mes de inicio de prevision
				int mesIni = DateUtils.getDateField(
						prevision.getFechainitrans(), Calendar.MONTH);
				int mesFin = DateUtils.getDateField(
						prevision.getFechafintrans(), Calendar.MONTH);

				for (int mesit = mesIni; mesit <= mesFin; mesit++) {

					String mesKey = String.valueOf(mesit);
					// Obtener el mes actual del map de meses
					HashMap mes = (HashMap) meses.get(mesKey);
					if (mes == null) {
						mes = new HashMap();
						mes.put(TransferenciasConstants.NOMBRE_MES_KEY,
								arrayMonthNames[mesit]);
						mes.put(TransferenciasConstants.INDEX_MES_KEY,
								new Integer(mesit));
						mes.put(TransferenciasConstants.NUM_DIAS_OCUPADOS_MES_KEY,
								new Integer(0));
						mes.put(TransferenciasConstants.NUM_PREVISIONES_MES_KEY,
								new Integer(0));
						mes.put(TransferenciasConstants.NUM_UNIDADES_INSTALACION_MES_KEY,
								new Integer(0));
						mes.put(TransferenciasConstants.DIAS_OCUPADOS_MES_KEY,
								new HashMap());
						mes.put(TransferenciasConstants.PREVISIONES_MES_KEY,
								new ArrayList());
					}

					// Obtener el numero de previsiones
					Integer numPrevisiones = (Integer) mes
							.get(TransferenciasConstants.NUM_PREVISIONES_MES_KEY);
					numPrevisiones = new Integer(numPrevisiones.intValue() + 1);
					mes.put(TransferenciasConstants.NUM_PREVISIONES_MES_KEY,
							numPrevisiones);

					// Obtener el numero de unidades de instalacion
					Integer numUnidadesInstalacion = (Integer) mes
							.get(TransferenciasConstants.NUM_UNIDADES_INSTALACION_MES_KEY);
					numUnidadesInstalacion = new Integer(
							numUnidadesInstalacion.intValue()
									+ prevision.getNumuinstalacion());
					mes.put(TransferenciasConstants.NUM_UNIDADES_INSTALACION_MES_KEY,
							numUnidadesInstalacion);

					// Obtener el numero de posibles dias ocupados

					Date fechaIniMes = DateUtils.getFirstDateOfMonth(
							actualYear, mesit);
					Date fechaFinMes = DateUtils.getLastDateOfMonth(actualYear,
							mesit);

					HashMap diasOcupados = (HashMap) mes
							.get(TransferenciasConstants.DIAS_OCUPADOS_MES_KEY);
					if (DateUtils.isFechaMenor(prevision.getFechainitrans(),
							fechaIniMes)
							&& DateUtils.isFechaMayor(
									prevision.getFechafintrans(), fechaFinMes)) {
						// Los días posibles ocupados son todos los del mes
						cargarDiasOcupados(fechaIniMes, fechaFinMes,
								diasOcupados);
					} else if (DateUtils.isFechaMayorOIgual(
							prevision.getFechainitrans(), fechaIniMes)
							&& DateUtils.isFechaMenorOIgual(
									prevision.getFechafintrans(), fechaFinMes)) {
						// Los días posibles ocupados son desde el inicio de la
						// previsión hasta la fecha fin de la previsión
						cargarDiasOcupados(prevision.getFechainitrans(),
								prevision.getFechafintrans(), diasOcupados);
					} else if (DateUtils.isFechaMenor(
							prevision.getFechainitrans(), fechaIniMes)) {
						// Los días posibles ocupados son desde el inicio del
						// mes hasta la fecha fin de la previsión
						cargarDiasOcupados(fechaIniMes,
								prevision.getFechafintrans(), diasOcupados);
					} else if (DateUtils.isFechaMayor(
							prevision.getFechafintrans(), fechaFinMes)) {
						// Los días posibles ocupados son desde el inicio de la
						// previsión hasta el último día del mes
						cargarDiasOcupados(prevision.getFechainitrans(),
								fechaFinMes, diasOcupados);
					}

					// Obtener los días ocupados
					Integer numPosiblesDiasOcupados = new Integer(
							diasOcupados.size());
					mes.put(TransferenciasConstants.NUM_DIAS_OCUPADOS_MES_KEY,
							numPosiblesDiasOcupados);

					// Añadir a las previsiones del mes la previsión actual
					ArrayList previsionesMes = (ArrayList) mes
							.get(TransferenciasConstants.PREVISIONES_MES_KEY);
					previsionesMes.add(prevision);

					CollectionUtils.transform(previsionesMes,
							PrevisionToPO.getInstance(services));
					mes.put(TransferenciasConstants.PREVISIONES_MES_KEY,
							previsionesMes);

					meses.put(mesKey, mes);
				}

			}
		}

		config.setMonths(meses);

		// Guardar en sesión la configuración del calendario
		setInTemporalSession(request,
				TransferenciasConstants.CALENDAR_CONFIG_KEY, config);

		// Guardar los colores del calendario para mostrar la leyenda
		List leyenda = generarLeyenda(request);
		setInTemporalSession(request,
				TransferenciasConstants.CALENDAR_LEGEND_KEY, leyenda);
	}
}