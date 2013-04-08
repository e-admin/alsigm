package common.forms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import common.Constants;
import common.Messages;
import common.util.CustomDateFormat;
import common.util.NumberUtils;
import common.util.StringUtils;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Clase abstracta para añadir funcionalidad a la clase
 * es.archigest.framework.web.action.ArchigestActionForm.
 */
public abstract class CustomForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Obtiene un mapa con la información del formulario.
	 *
	 * @return Mapa.
	 * @return
	 */

	HashMap values = new HashMap(); // Map para almacenar campos del formulario
									// dinamico. Campos en listados
	List sortedValues = new ArrayList();

	public Map getMap() {
		Map map = new HashMap();

		Field[] fields = getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			map.put(fields[i].getName(), getAttributeValue(fields[i].getName()));

		return map;
	}

	public Map getMapClassAndSuperClassObject(Object obj) {
		Map map = new HashMap();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			if (!StringUtils.isEmpty(fields[i].getName()))
				map.put(fields[i].getName(),
						getAttributeValue(fields[i].getName()));

		fields = obj.getClass().getSuperclass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			if (!StringUtils.isEmpty(fields[i].getName()))
				map.put(fields[i].getName(),
						getAttributeValue(fields[i].getName()));
		return map;
	}

	/**
	 * Devuelve el objeto atributo a partir del nombre del atributo.
	 *
	 * @param atributo
	 *            Nombre del atributo.
	 * @return Valor del atributo.
	 */
	private Object getAttributeValue(String attribute) {
		Object value = null;

		try {
			if ((attribute != null) && (attribute.length() > 0)) {
				Method method = findMethod("get".concat(attribute));
				if (method != null)
					value = method.invoke(this, null);
			}
		} catch (Exception e) {
			value = null;
		}

		return value;
	}

	/**
	 * Obtiene un método.
	 *
	 * @param name
	 *            Nombre del método.
	 * @return Método.
	 */
	private Method findMethod(String name) {
		Method[] methods = getClass().getMethods();
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equalsIgnoreCase(name))
				return methods[i];
		return null;
	}

	public String getMapFormValues(String key) {
		return (String) values.get(key);
	}

	public void setMapFormValues(String key, String value) {
		values.put(key, value.trim());
	}

	public HashMap getValues() {
		return values;
	}

	public void setValues(HashMap values) {
		this.values = values;
	}

	public void validateCamposFecha(String key, Locale locale,
			ActionErrors errors, String operador, String formato, String dia,
			String mes, String anio, String siglo, String formatoDesde,
			String diaDesde, String mesDesde, String anioDesde,
			String sigloDesde, String formatoHasta, String diaHasta,
			String mesHasta, String anioHasta, String sigloHasta) {

		if (errors == null)
			errors = new ActionErrors();

		String nombreFecha = Messages.getString(key, locale);

		String nombreRango = Messages.getString(Constants.LABEL_DESDE);

		String labelDesde = nombreFecha + Constants.STRING_SPACE + nombreRango;

		if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(operador)) {

			// DESDE

			if (isFormatoConDia(formatoDesde)
					&& StringUtils.isNotEmpty(diaDesde)
					&& !NumberUtils.isNumeroMayorCero(diaDesde)) {

				String nombreCampo = Messages.getString(Constants.LABEL_DIA);

				String label = getLabel(labelDesde, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConMes(formatoDesde)
					&& StringUtils.isNotBlank(mesDesde)
					&& !NumberUtils.isNumeroMayorCero(mesDesde)) {
				String nombreCampo = Messages.getString(Constants.LABEL_MES);

				String label = getLabel(labelDesde, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConAnio(formatoDesde)
					&& StringUtils.isNotBlank(anioDesde)
					&& !NumberUtils.isNumeroMayorCero(anioDesde)) {
				String nombreCampo = Messages.getString(Constants.LABEL_ANIO);

				String label = getLabel(labelDesde, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			// HASTA

			nombreRango = Messages.getString(Constants.LABEL_HASTA);

			String labelHasta = nombreFecha + Constants.STRING_SPACE
					+ nombreRango;

			if (isFormatoConDia(formatoHasta)
					&& StringUtils.isNotEmpty(diaHasta)
					&& !NumberUtils.isNumeroMayorCero(diaHasta)) {

				String nombreCampo = Messages.getString(Constants.LABEL_DIA);

				String label = getLabel(labelHasta, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConMes(formatoHasta)
					&& StringUtils.isNotBlank(mesHasta)
					&& !NumberUtils.isNumeroMayorCero(mesHasta)) {
				String nombreCampo = Messages.getString(Constants.LABEL_MES);

				String label = getLabel(labelHasta, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConAnio(formatoHasta)
					&& StringUtils.isNotBlank(anioHasta)
					&& !NumberUtils.isNumeroMayorCero(anioHasta)) {
				String nombreCampo = Messages.getString(Constants.LABEL_ANIO);

				String label = getLabel(labelHasta, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

		} else {
			if (isFormatoConDia(formato) && StringUtils.isNotEmpty(dia)
					&& !NumberUtils.isNumeroMayorCero(dia)) {

				String nombreCampo = Messages.getString(Constants.LABEL_DIA);

				String label = getLabel(nombreFecha, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConMes(formato) && StringUtils.isNotBlank(mes)
					&& !NumberUtils.isNumeroMayorCero(mes)) {
				String nombreCampo = Messages.getString(Constants.LABEL_MES);

				String label = getLabel(nombreFecha, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

			if (isFormatoConAnio(formato) && StringUtils.isNotBlank(anio)
					&& !NumberUtils.isNumeroMayorCero(anio)) {
				String nombreCampo = Messages.getString(Constants.LABEL_ANIO);

				String label = getLabel(nombreFecha, nombreCampo);

				errors.add(Constants.ERROR_INT, new ActionError(
						Constants.ERROR_INT, label));
			}

		}
	}

	private boolean isFormatoConDia(String formato) {
		if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(formato)) {
			return true;
		}

		return false;
	}

	private boolean isFormatoConMes(String formato) {
		if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_AAAAMM.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_MMAAAA.equals(formato)) {
			return true;
		}

		return false;
	}

	private boolean isFormatoConAnio(String formato) {
		if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_AAAAMM.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_MMAAAA.equals(formato)
				|| CustomDateFormat.DATE_FORMAT_AAAA.equals(formato)) {
			return true;
		}

		return false;
	}

	private String getLabel(String nombreFecha, String nombreCampo) {
		return nombreCampo + Constants.STRING_SPACE
				+ Constants.ABRIR_PARENTESIS + nombreFecha
				+ Constants.CERRAR_PARENTESIS;
	}

}
