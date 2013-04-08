package solicitudes.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.prestamos.PrestamosConstants;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.DateUtils;

/**
 * Form base para el módulo de prestanos.
 */
public class SolicitudesBaseForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Logger de la clase */
	protected static Logger logger = Logger
			.getLogger(SolicitudesBaseForm.class);

	private String method = null;

	private String uricancel = null;

	/**
	 * @return Returns the uricancel.
	 */
	public String getUricancel() {
		return uricancel;
	}

	/**
	 * @param uricancel
	 *            The uricancel to set.
	 */
	public void setUricancel(String uricancel) {
		this.uricancel = uricancel;
	}

	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	protected ActionErrors mergeActionErrors(ActionErrors errors1,
			ActionErrors errors2) {
		ActionErrors ret = new ActionErrors();
		ActionErrors errorsA, errorsB;

		// si lo q me pasan son ambos null no hay nada q devolver
		if ((errors1 == null) && (errors2 == null))
			return null;

		// si solo alguno es null=>lo mezclo
		if (errors1 == null)
			errorsA = new ActionErrors();
		else
			errorsA = errors1;
		if (errors2 == null)
			errorsB = new ActionErrors();
		else
			errorsB = errors2;

		addErrors(ret, errorsA);
		addErrors(ret, errorsB);

		return ret;

	}

	private void addErrors(ActionErrors in, ActionErrors toAdd) {
		Iterator it = toAdd.get();
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg;
		while (it.hasNext()) {
			msg = (ActionMessage) it.next();
			msgs.add(msg.getKey(), msg);
		}
		in.add(msgs);
	}

	static public Date parseDate(String date) {
		return parseDate(date, Constants.FORMATO_FECHA);
	}

	public static Date parseDate(String date, String pattern) {
		Date fecha = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		try {
			fecha = format.parse(date);
		} catch (ParseException pe) {
			logger.error("Error transformando la fecha:" + date
					+ ". Se genera fecha nula");
		}

		return fecha;
	}

	public static String DateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.FORMATO_FECHA);

		return dateFormat.format(date);
	}

	public static String DateFormatString(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.FORMATO_FECHA);
		return dateFormat.format(dateFormat.parse(date));
	}

	public static int parseInt(String integer) {
		try {
			return Integer.parseInt(integer);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Realiza la validacion de las fechas de reserva. Devuelve errores si:<br/>
	 * - fini es fecha - fini es menor que hoy.
	 * 
	 * @param fechainireserva
	 *            Fecha de inicio de reserva
	 * @return {@link ActionErrors} con los errores de validación.
	 * @throws Exception
	 *             Si se produce algún problema durante la validación
	 */
	public ActionErrors validateFechasReserva(HttpServletRequest request,
			String fechainireserva, int tipoSolicitud) throws Exception {
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.FORMATO_FECHA);

		String messageKeyFechaInicio = PrestamosConstants.ERROR_PRESTAMOS_FECHA_INICIO_RESERVA;
		String messageKeyFechaInicioMayor = PrestamosConstants.ERROR_PRESTAMOS_FECHA_INICIO_MAYOR_ACTUAL;
		if (SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT == tipoSolicitud) {
			messageKeyFechaInicio = ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_FECHA_INICIO_RESERVA;
			messageKeyFechaInicioMayor = ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_FECHA_INICIO_MAYOR_ACTUAL;
		}

		if (!DateUtils.isDate(fechainireserva)) {
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(messageKeyFechaInicio,
									request.getLocale())));

			return errors;
		}

		Date fechaini = dateFormat.parse(fechainireserva);
		Date fechahoy = new Date();

		if (fechaini.compareTo(fechahoy) <= 0) {
			errors = new ActionErrors();
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(messageKeyFechaInicioMayor,
									request.getLocale())));
		}

		return errors;
	}

}
