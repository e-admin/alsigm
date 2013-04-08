package common.util;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import util.StringOwnTokenizer;

import common.exceptions.CustomDateParseException;


/**
 * Clase que almacena la información de una fecha.
 */
public class CustomDate {

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(CustomDate.class);

	/** Valor de la fecha. */
	private String value = null;

	/** Año. */
	private String year = null;

	/** Mes. */
	private String month = null;

	/** Día del mes. */
	private String day = null;

	/** Siglo. */
	private String century = null;

	/** Formato de la fecha. */
	private String format = null;

	/** Separador de los componentes de la fecha. */
	private String separator = null;

	/** Calificador de la fecha. */
	private String qualifier = null;

	/**
	 * Constructor.
	 */
	public CustomDate()
	{
	}

	/**
	 * Constructor.
	 */
	public CustomDate(String value, String format, String separator, String qualifier)
	{
		this();

		setValue(value);
		setFormat(format);
		setSeparator(separator);
		setQualifier(qualifier);

		try
		{
			if (StringUtils.isNotBlank(value))
			{
				if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format)){
					//Date date = CustomDateFormat.SDF_YYYY.parse(value);
					setYear(value);
				}else if (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format)){
					//SimpleDateFormat sdf = new SimpleDateFormat("MM" + separator + "yyyy");
					//Date date = sdf.parse(value);

					StringOwnTokenizer tok = new StringOwnTokenizer(value, separator);
					if (tok.hasMoreTokens())
						setMonth(tok.nextToken());
					if (tok.hasMoreTokens())
						setYear(tok.nextToken());
				}else if (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)){
						//SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + separator + "MM");
						//Date date = sdf.parse(value);

						StringOwnTokenizer tok = new StringOwnTokenizer(value, separator);
						if (tok.hasMoreTokens())
							setYear(tok.nextToken());
						if (tok.hasMoreTokens())
							setMonth(tok.nextToken());
				}else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format)){
					//SimpleDateFormat sdf = new SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy");
					//Date date = sdf.parse(value);

					StringOwnTokenizer tok = new StringOwnTokenizer(value, separator);
					if (tok.hasMoreTokens())
						setDay(tok.nextToken());
					if (tok.hasMoreTokens())
						setMonth(tok.nextToken());
					if (tok.hasMoreTokens())
						setYear(tok.nextToken());
				}else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format)){
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
					//Date date = sdf.parse(value);

					StringOwnTokenizer tok = new StringOwnTokenizer(value, separator);
					if (tok.hasMoreTokens())
						setYear(tok.nextToken());
					if (tok.hasMoreTokens())
						setMonth(tok.nextToken());
					if (tok.hasMoreTokens())
						setDay(tok.nextToken());
				}else if (CustomDateFormat.DATE_FORMAT_S.equals(format)){
					RomanNumeral roman = new RomanNumeral(value);
					setCentury(roman.toString());
				}else{
					logger.error("Formato no v\u00E1lido: " + format);
					throw new CustomDateParseException("Formato no v\u00E1lido: " + format);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error en el parseo de la fecha [" + value + "] con formato [" + format + "] y separador [" + separator + "]", e);
			throw new CustomDateParseException("Error en el parseo de la fecha [" + value + "] con formato [" + format + "] y separador [" + separator + "]", e);
		}
	}


	/**
	 * Constructor.
	 * @param format Formato.
	 * @param year Años.
	 * @param month Mes.
	 * @param day Día del mes.
	 * @param century Siglo.
	 */
	public CustomDate(String format, String year, String month, String day, String century)
	{
		this(format, year, month, day, century, null, null);
	}

	public Date getDate(){
		int dia, mes, anio;

		dia = NumberUtils.toInt(this.day);
		mes = NumberUtils.toInt(this.month);
		anio = NumberUtils.toInt(this.year);

		return DateUtils.getFechaSinHora(dia, mes, anio);
	}

	/**
	 * Constructor.
	 * @param format Formato.
	 * @param year Años.
	 * @param month Mes.
	 * @param day Día del mes.
	 * @param century Siglo.
	 * @param qualifier Calificador.
	 */
	public CustomDate(String format, String year, String month, String day, String century, String separator, String qualifier)
	{
		this();

		setFormat(format);
		setQualifier(qualifier);

		try{
			if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format))
			{
				setYear(year);
			}
			else if( (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format))
						|| (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)) ){
				setYear(year);
				setMonth(month);
				setSeparator(separator != null ? separator : "/");
			}
			else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format))
			{
				setYear(year);
				setMonth(month);
				setDay(day);
				setSeparator(separator != null ? separator : "/");
			}
			else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format))
			{
				setYear(year);
				setMonth(month);
				setDay(day);
				setSeparator(separator != null ? separator : "/");
			}
			else if (CustomDateFormat.DATE_FORMAT_S.equals(format))
			{
				if(StringUtils.isNotBlank(century)){
					RomanNumeral roman = new RomanNumeral(century);
					setCentury(roman.toString());
				}
			}

			formatValue();
		}catch (NumberFormatException nfe){
			logger.error(getMessageError(), nfe);
			throw new CustomDateParseException("Error en el parseo de la fecha. El valor del Siglo debe especificarse en números romanos", nfe);
		}catch (Exception e){
			logger.error(getMessageError(), e);
			throw new CustomDateParseException(getMessageError(), e);
		}
	}

	private String getMessageError(){
		String message = "Error en el parseo de la fecha con: ";
		if(StringUtils.isNotEmpty(this.getFormat()))
			message += " Formato [" + this.getFormat() + "]";
		if(StringUtils.isNotEmpty(this.getDay()))
			message += " Dia [" + this.getDay() + "]";
		if(StringUtils.isNotEmpty(this.getMonth()))
			message += " Mes [" + this.getMonth() + "]";
		if(StringUtils.isNotEmpty(this.getYear()))
			message += " Anio [" + this.getYear() + "]";
		if(StringUtils.isNotEmpty(this.getCentury()))
			message += " Siglo [" + this.getCentury() + "]";
		if(StringUtils.isNotEmpty(this.getSeparator()))
			message += " Separador [" + this.getSeparator() + "]";
		if(StringUtils.isNotEmpty(this.getValue()))
			message += " Valor [" + this.getValue() + "]";
		return message;
	}

	/**
	 * Obtiene el siglo.
	 * @return Siglo.
	 */
	public String getCentury()
	{
		return century;
	}


	/**
	 * Establece el siglo.
	 * @param century Siglo.
	 */
	public void setCentury(String century)
	{
		this.century = ((century != null) && (century.length() > 0) ? century : null);
	}


	/**
	 * Obtiene el día del mes.
	 * @return Día del mes.
	 */
	public String getDay()
	{
		return day;
	}


	/**
	 * Establece el día del mes.
	 * @param day Día del mes.
	 */
	public void setDay(String day)
	{
		this.day = ((day != null) && (day.length() > 0) ? day : null);
	}


	/**
	 * Obtiene el mes.
	 * @return Mes.
	 */
	public String getMonth()
	{
		return month;
	}


	/**
	 * Establece el mes.
	 * @param month Mes.
	 */
	public void setMonth(String month)
	{
		this.month = ((month != null) && (month.length() > 0) ? month : null);
	}


	/**
	 * Obtiene el año.
	 * @return Año.
	 */
	public String getYear()
	{
		return year;
	}


	/**
	 * Establece el año.
	 * @param year Año.
	 */
	public void setYear(String year)
	{
		this.year = ((year != null) && (year.length() > 0) ? year : null);
	}


	/**
	 * Obtiene el formato de la fecha.
	 * @return Formato de la fecha.
	 */
	public String getFormat()
	{
		return format;
	}


	/**
	 * Establece el formato de la fecha.
	 * @param format Formato de la fecha.
	 */
	public void setFormat(String format)
	{
		this.format = ((format != null) && (format.length() > 0) ? format : null);
	}


	/**
	 * Obtiene el separador de los componentes de la fecha.
	 * @return Separador de los componentes de la fecha.
	 */
	public String getSeparator()
	{
		return separator;
	}


	/**
	 * Establece el separador de los componentes de la fecha.
	 * @param separator Separador de los componentes de la fecha.
	 */
	public void setSeparator(String separator)
	{
		this.separator = ((separator != null) && (separator.length() > 0) ? separator : null);
	}


	/**
	 * Obtener el calificador de la fecha.
	 * @return Calificador de la fecha.
	 */
	public String getQualifier()
	{
		return qualifier;
	}


	/**
	 * Establecer el calificador de la fecha.
	 * @param qualifier Calificador de la fecha.
	 */
	public void setQualifier(String qualifier)
	{
		this.qualifier = ((qualifier != null) && (qualifier.length() > 0) ? qualifier : null);
	}


	/**
	 * Obtiene el valor de la fecha.
	 * @return Valor de la fecha.
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * Establece el valor de la fecha.
	 * @param value Valor de la fecha.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}


	/**
	 * Formatea el valor de la fecha en función del formato y sus componentes.
	 */
	private void formatValue()
	{
		String dateValue = null;

		if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format))
		{
			dateValue = year;
		}else if (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format)){
			if (StringUtils.isNotEmpty(month)
					&& StringUtils.isNotEmpty(year)) {
				dateValue = new StringBuffer()
					.append(month)
					.append(separator)
					.append(year)
					.toString();
			}
		}else if (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)){
			if (StringUtils.isNotEmpty(month)
					&& StringUtils.isNotEmpty(year))
			{
				dateValue = new StringBuffer()
					.append(year)
					.append(separator)
					.append(month)
					.toString();
			}
		}
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format))
		{
			if (StringUtils.isNotEmpty(day)
					&& StringUtils.isNotEmpty(month)
					&& StringUtils.isNotEmpty(year))
			{
				dateValue = new StringBuffer()
					.append(day)
					.append(separator)
					.append(month)
					.append(separator)
					.append(year)
					.toString();
			}
		}
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format))
		{
			if (StringUtils.isNotEmpty(day)
					&& StringUtils.isNotEmpty(month)
					&& StringUtils.isNotEmpty(year))
			{
				dateValue = new StringBuffer()
					.append(year)
					.append(separator)
					.append(month)
					.append(separator)
					.append(day)
					.toString();
			}
		}
		else if (CustomDateFormat.DATE_FORMAT_S.equals(format))
		{
			dateValue = century;
		}

		setValue(dateValue);
	}


	/**
	 * Obtiene la fecha mínima con la información actual.
	 * @return Fecha mínima.
	 */
	public Date getMinDate()
	{
		Date date = null;

		if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format))
		{
			if (StringUtils.isNotBlank(year))
				date = DateQualifierHelper.getMinDate(qualifier, year);
		}
		else if( (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format))
					|| (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)) ){
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month))
				date = DateQualifierHelper.getMinDate(qualifier, year, month);
		}
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMinDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMinDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_S.equals(format))
		{
			if (StringUtils.isNotBlank(century))
				date = DateQualifierHelper.getMinDate(qualifier, RomanNumeral.toInt(century));
		}

		return date;
	}


	/**
	 * Obtiene la fecha máxima con la información actual.
	 * @return Fecha máxima.
	 */
	public Date getMaxDate()
	{
		Date date = null;

		if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format))
		{
			if (StringUtils.isNotBlank(year))
				date = DateQualifierHelper.getMaxDate(qualifier, year);
		}
		else if( (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format))
				||  (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)) ){
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month))
				date = DateQualifierHelper.getMaxDate(qualifier, year, month);
		}
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMaxDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMaxDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_S.equals(format))
		{
			if (StringUtils.isNotBlank(century))
				date = DateQualifierHelper.getMaxDate(qualifier, RomanNumeral.toInt(century));
		}

		return date;
	}


	/**
	 * Valida la fecha.
	 * @return true si la fecha es correcta, false en caso contrario.
	 */
	public boolean validate()
	{
		boolean isValid = true;

		if(!common.util.StringUtils.isEmpty(year) && !NumberUtils.isNumber(year))
			return false;

		if(!common.util.StringUtils.isEmpty(month) && !NumberUtils.isNumber(month))
			return false;

		if(!common.util.StringUtils.isEmpty(day) && !NumberUtils.isNumber(day))
			return false;

		try
		{
			if (DateQualifierHelper.CALIFICADOR_TAL_Y_COMO_SE_HA_ESCRITO.equals(qualifier))
				return true;

			if (CustomDateFormat.DATE_FORMAT_AAAA.equals(format))
			{
				if (StringUtils.isNotBlank(year))
				{
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYY.clone();
					df.setLenient(false);
					df.parse(year);
				}
				else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}
			else if( (CustomDateFormat.DATE_FORMAT_MMAAAA.equals(format))
				 ||  (CustomDateFormat.DATE_FORMAT_AAAAMM.equals(format)) )	{
				if (StringUtils.isNotBlank(year) || StringUtils.isNotBlank(month)) {
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYYMM.clone();
					df.setLenient(false);
					df.parse(year + "-" + month);
				}else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format))	{
				if (StringUtils.isNotBlank(year) || StringUtils.isNotBlank(month) || StringUtils.isNotBlank(day))
				{
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYYMMDD.clone();
					df.setLenient(false);
					df.parse(year + "-" + month + "-" + day);
				}
				else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}
			else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format))
			{
				if (StringUtils.isNotBlank(year) || StringUtils.isNotBlank(month) || StringUtils.isNotBlank(day))
				{
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYYMMDD.clone();
					df.setLenient(false);
					df.parse(year + "-" + month + "-" + day);
				}
				else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}
			else if (CustomDateFormat.DATE_FORMAT_S.equals(format))
			{
				if (StringUtils.isNotBlank(century))
				{
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					if(!StringUtils.isEmpty(century)) {
						RomanNumeral.toInt(century);
					}
				}
				else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}
			else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
				isValid = false;
		}
		catch (Exception e)
		{
			isValid = false;
		}

		return isValid;
	}


	/**
	 * Obtiene una representación del objeto.
	 * @return Representación del objeto.
	 */
	public String toString()
	{
		StringBuffer xml = new StringBuffer();

		xml.append("<CustomDateFormat>");
		xml.append("<value>").append(value != null ? value : "").append("</value>");
		xml.append("<format>").append(format != null ? format : "").append("</format>");
		xml.append("<separator>").append(separator != null ? separator : "").append("</separator>");
		xml.append("<year>").append(year != null ? year : "").append("</year>");
		xml.append("<month>").append(month != null ? month : "").append("</month>");
		xml.append("<day>").append(day != null ? day : "").append("</day>");
		xml.append("<century>").append(century != null ? century : "").append("</century>");
		xml.append("<qualifier>").append(qualifier != null ? qualifier : "").append("</qualifier>");
		xml.append("</CustomDateFormat>");

		return xml.toString();
	}
}
