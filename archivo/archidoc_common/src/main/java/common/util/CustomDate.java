package common.util;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import util.StringOwnTokenizer;

import common.Constants;
import common.exceptions.CustomDateParseException;


/**
 * Clase que almacena la información de una fecha.
 */
public class CustomDate {

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(CustomDate.class);

	public static final String SEPARADOR_HORA = ":";

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

	/**
    * Hora
    */
	private String hour = null;

	/**
    * Minutos
    */
	private String minutes = null;

	/**
    * Segundos
    */
	private String seconds = null;

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
		format = getFormat();

		setSeparator(separator);
		setQualifier(qualifier);

		try
		{
			if (StringUtils.isNotBlank(value))
			{

				if(isFormatoConHora()){
					String[] partes = value.split(Constants.STRING_SPACE);

					if(partes != null && partes.length==2){

						value= partes[0];

						String timestamp = partes[1];
						String[] partesHora = timestamp.split(SEPARADOR_HORA);

						if(partesHora != null && partesHora.length == 3){
							setHour(getValorFormateado(partesHora[0]));
							setMinutes(getValorFormateado(partesHora[1]));
							setSeconds(getValorFormateado(partesHora[2]));
						}
					}
				}

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
				}else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format) || CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format)){
					//SimpleDateFormat sdf = new SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy");
					//Date date = sdf.parse(value);

					StringOwnTokenizer tok = new StringOwnTokenizer(value, separator);
					if (tok.hasMoreTokens())
						setDay(tok.nextToken());
					if (tok.hasMoreTokens())
						setMonth(tok.nextToken());
					if (tok.hasMoreTokens())
						setYear(tok.nextToken());
				}else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format)){
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

	private String getValorFormateado(String valor){
		if(valor == null){
			valor = "00";
		}

		if(valor.length() == 1){
			valor = "0" + valor;
		}

		return valor;
	}

	/**
	 * Constructor.
	 * @param format Formato.
	 * @param year Años.
	 * @param month Mes.
	 * @param day Día del mes.
    *
	 * @param century Siglo.
	 */
	public CustomDate(String format, String year, String month, String day,String century)
	{
		this(format, year, month, day,null,null,null,century, null, null);
	}

	/**
    * Constructor.
    * @param format Formato.
    * @param year Años.
    * @param month Mes.
    * @param day Día del mes.
    * @param hour Hora
    * @param minutes Minutos
    * @param seconds Segundos
    * @param century Siglo.
    */
	public CustomDate(String format, String year, String month, String day,String hour, String minutes, String seconds ,String century)
	{
		this(format, year, month, day, hour, minutes, seconds ,century, null, null);
	}

	public Date getDate(){
		int dia, mes, anio, hora, minutos, segundos;

		dia = NumberUtils.toInt(this.day);
		mes = NumberUtils.toInt(this.month);
		anio = NumberUtils.toInt(this.year);

		if(isFormatoConHora()){
			hora = NumberUtils.toInt(this.hour);
			minutos = NumberUtils.toInt(this.minutes);
			segundos = NumberUtils.toInt(this.seconds);
			return DateUtils.getFechaConHora(dia, mes, anio, hora, minutos, segundos);
		}
		else{
			return DateUtils.getFechaSinHora(dia, mes, anio);
		}
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
	public CustomDate(String format, String year, String month, String day, String hour, String minutes, String seconds ,String century, String separator, String qualifier)
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
			else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format) || CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format))
			{
				setYear(year);
				setMonth(month);
				setDay(day);
				setSeparator(separator != null ? separator : "/");

				if(isFormatoConHora()){
					setHour(hour);
					setMinutes(minutes);
					setSeconds(seconds);
				}

			}
			else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format))
			{
				setYear(year);
				setMonth(month);
				setDay(day);
				setSeparator(separator != null ? separator : "/");

				if(isFormatoConHora()){
					setHour(hour);
					setMinutes(minutes);
					setSeconds(seconds);
				}
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

		if(isFormatoConHora()){
			if(StringUtils.isNotEmpty(this.getHour()))
				message += " Hora [" + this.getHour() + "]";
			if(StringUtils.isNotEmpty(this.getMinutes()))
				message += " Minutos [" + this.getMinutes() + "]";
			if(StringUtils.isNotEmpty(this.getSeconds()))
				message += " Segundos [" + this.getSeconds() + "]";
		}

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
		this.day = ((day != null) && (day.length() > 0) ? getValorFormateado(day) : null);
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
		this.month = ((month != null) && (month.length() > 0) ? getValorFormateado(month) : null);
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
		if(StringUtils.isNotEmpty(format)){
			this.format = format.replaceAll(Constants.STRING_SPACE, Constants.STRING_EMPTY);
		}
		else{
			this.format= null;
		}
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




	public String getHour() {
		if(hour == null && isFormatoConHora()){
			hour = "00";
		}
		return hour;
	}

	public void setHour(String hour) {
		this.hour = getValorFormateado(hour);
	}

	public String getMinutes() {
		if(minutes == null && isFormatoConHora()){
			minutes = "00";
		}

		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = getValorFormateado(minutes);
	}

	public String getSeconds() {
		if(seconds == null && isFormatoConHora()){
			seconds = "00";
		}
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = getValorFormateado(seconds);
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
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format) ||CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format) )
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
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format))
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


		if(isFormatoConHora()){
			dateValue += Constants.STRING_SPACE + getTime();
		}

		setValue(dateValue);
	}

	public String getTime(){
		return new StringBuffer(getHour())
			.append(SEPARADOR_HORA)
			.append(getMinutes())
			.append(SEPARADOR_HORA)
			.append(getSeconds())
		.toString();
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
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format) || CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMinDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format))
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
		else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA.equals(format) || CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format))
		{
			if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day))
				date = DateQualifierHelper.getMaxDate(qualifier, year, month, day);
		}
		else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD.equals(format) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format))
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

		if(!common.util.StringUtils.isEmpty(month) && !NumberUtils.isNumber(month)){
			return false;
		}

		if(!common.util.StringUtils.isEmpty(day) && !NumberUtils.isNumber(day))
			return false;

		if(!common.util.StringUtils.isEmpty(hour) && !NumberUtils.isNumber(hour))
			return false;

		if(!common.util.StringUtils.isEmpty(minutes) && !NumberUtils.isNumber(minutes))
			return false;

		if(!common.util.StringUtils.isEmpty(seconds) && !NumberUtils.isNumber(seconds))
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
			}else if (CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(format))	{
				if (StringUtils.isNotBlank(year) || StringUtils.isNotBlank(month) || StringUtils.isNotBlank(day)
					||	StringUtils.isNotBlank(hour) || StringUtils.isNotBlank(minutes) || StringUtils.isNotBlank(seconds)
				)
				{
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYYMMDD_HHMMSS.clone();
					df.setLenient(false);
					df.parse(year + "-" + month + "-" + day + " " + hour + SEPARADOR_HORA + minutes + SEPARADOR_HORA + seconds);
				}
				else if (StringUtils.isNotBlank(qualifier) && !DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
					isValid = false;
			}
			else if (CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(format))
			{
				if (StringUtils.isNotBlank(year) || StringUtils.isNotBlank(month) || StringUtils.isNotBlank(day)
						||	StringUtils.isNotBlank(hour) || StringUtils.isNotBlank(minutes) || StringUtils.isNotBlank(seconds)){
					if (DateQualifierHelper.CALIFICADOR_SIN_FECHA.equals(qualifier))
						return false;

					DateFormat df = (DateFormat) CustomDateFormat.SDF_YYYYMMDD_HHMMSS.clone();
					df.setLenient(false);
					df.parse(year + "-" + month + "-" + day + " " + hour + SEPARADOR_HORA + minutes + SEPARADOR_HORA + seconds);
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

	public boolean isFormatoConHora(){
		if(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS.equals(getFormat()) || CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS.equals(getFormat())){
			return true;
		}
		return false;
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
		xml.append("<hour>").append(hour != null ? hour : "").append("</hour>");
		xml.append("<minutes>").append(minutes != null ? minutes : "").append("</minutes>");
		xml.append("<seconds>").append(seconds != null ? seconds : "").append("</seconds>");
		xml.append("<century>").append(century != null ? century : "").append("</century>");
		xml.append("<qualifier>").append(qualifier != null ? qualifier : "").append("</qualifier>");
		xml.append("</CustomDateFormat>");

		return xml.toString();
	}

}
