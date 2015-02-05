package ieci.tdw.ispac.ispaclib.gendoc.config;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenEvaluator {
	
	public static String evaluateToken(Repository repository, Token token, String properties) throws Exception {
		
		if (StringUtils.equals(token.getValueType(), ConfigConstants.CONFIG_TOKEN_TYPE_CONSTANT)) {
			return token.value;
		} else if (StringUtils.equals(token.getValueType(), ConfigConstants.CONFIG_TOKEN_TYPE_XPATH)) {
			XmlFacade xml = new XmlFacade(properties);
			return xml.get(token.value);
		} else if (StringUtils.equals(token.getValueType(), ConfigConstants.CONFIG_TOKEN_TYPE_EXPRESION)) {
			return evaluateTokenString(repository, token.value, properties);
		}
		
		return "";
	}

	public static String evaluateDynamicToken(String token) {
		if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_YEAR)) {
			return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		} else if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_MONTH)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_DAY)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_HOUR)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_MINUTE)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, ConfigConstants.CONFIG_DYNAMIC_SECOND)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.SECOND)), 2, "0");
		}
		return "";
	}

	public static String evaluateTokenString(Repository repository, String expresion, String properties) 
			throws Exception {
		
		StringBuffer evaluated = new StringBuffer();

		Pattern pattern = Pattern.compile(ConfigConstants.CONFIG_TOKEN_REF_PATTERN);
		Matcher matcher = pattern.matcher(expresion);
		while (matcher.find()) {
			
			String var = matcher.group();
			String key = var.substring(2, var.length() - 1);
			String sust = null;
			
			//Si el valor empieza por un valor determinado se considera que es una valor dinamico a calcular
			if (StringUtils.startsWith(key, ConfigConstants.CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE)) {
				sust = TokenEvaluator.evaluateDynamicToken(key);
			} else {
				sust = TokenEvaluator.evaluateToken(repository, repository.getTokens().getToken(key), 
						properties);
			}
			sust = StringUtils.replace(sust, "/", "_");
			if (StringUtils.isEmpty(sust)) {
				return null;
			}
			matcher.appendReplacement(evaluated, sust);
		}
		matcher.appendTail(evaluated);
		return evaluated.toString();
	}

	public static String formatValue(String value, String sourceFormat, String destinationFormat, 
			String destinationType) throws ParseException {
		
		if ((StringUtils.isEmpty(sourceFormat)) || (StringUtils.isEmpty(destinationFormat))) {
			return value;
		}

		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_INTEGER)
				|| StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_FLOAT)
				|| StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_CURRENCY)) {
			NumberFormat formatterSource = new DecimalFormat(sourceFormat);
			Number number = formatterSource.parse(value);
			NumberFormat formatterDest = new DecimalFormat(destinationFormat);
			return formatterDest.format(number);
		} else if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_DATE)
				|| StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_DATETIME)) {
			DateFormat formatterSource = new SimpleDateFormat(sourceFormat);
			Date date = formatterSource.parse(value);
			DateFormat formatterDest = new SimpleDateFormat(destinationFormat);
			return formatterDest.format(date);
		}

		return null;
	}

}
