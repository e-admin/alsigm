package es.ieci.tecdoc.fwktd.dm.business.config;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import es.ieci.tecdoc.fwktd.dm.business.util.ConvertUtils;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TokenEvaluator {

	private static final String TOKEN_TYPE_CONSTANT 		= "constant";
	private static final String TOKEN_TYPE_XPATH 			= "xpath";
	private static final String TOKEN_TYPE_EXPRESSION 		= "expression";

	private static final String OBLIGATORY_PREFIX_DYNAMIC_VALUE = "_";
	private static final String DYNAMIC_YEAR = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "YEAR";
	private static final String DYNAMIC_MONTH = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "MONTH";
	private static final String DYNAMIC_DAY = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "DAY";
	private static final String DYNAMIC_HOUR = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "HOUR";
	private static final String DYNAMIC_MINUTE = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "MINUTE";
	private static final String DYNAMIC_SECOND = OBLIGATORY_PREFIX_DYNAMIC_VALUE + "SECOND";
	private static final String TOKEN_REF_PATTERN = "\\$\\{[^}]*}";


	public static String evaluateToken(ContentType contentType, Token token,
			List<MetadatoVO> metadatos) throws Exception {

		if (token != null) {
			if (TOKEN_TYPE_CONSTANT.equalsIgnoreCase(token.getType())) {
				return token.getValue();
			} else if (TOKEN_TYPE_XPATH.equalsIgnoreCase(token.getType())) {
				Document document = getMetadatosDocument(metadatos);
				return document.valueOf(token.getValue());
			} else if (TOKEN_TYPE_EXPRESSION.equalsIgnoreCase(token.getType())) {
				return evaluateTokenExpression(contentType, token.getValue(), metadatos);
			} else if (metadatos != null) {
				for (MetadatoVO metadato : metadatos) {
					if (StringUtils.equalsIgnoreCase(token.getValue(), metadato.getNombre())) {
						return ConvertUtils.toString(metadato.getValor());
					}
				}
			}
		}

		return "";
	}

	public static String evaluateTokenExpression(ContentType contentType, String expresion, List<MetadatoVO> metadatos)
			throws Exception {

		StringBuffer evaluated = new StringBuffer();

		Pattern pattern = Pattern.compile(TOKEN_REF_PATTERN);
		Matcher matcher = pattern.matcher(expresion);
		while (matcher.find()) {

			String var = matcher.group();
			String key = var.substring(2, var.length() - 1);
			String sust = null;

			//Si el valor empieza por un valor determinado se considera que es una valor dinamico a calcular
			if (StringUtils.startsWith(key, OBLIGATORY_PREFIX_DYNAMIC_VALUE)) {
				sust = evaluateDynamicToken(key);
			} else {
				sust = evaluateToken(contentType, contentType.findToken(key), metadatos);
			}

			sust = StringUtils.replace(sust.toString(), "/", "_");
			if (StringUtils.isEmpty(sust.toString())) {
				return null;
			}

			matcher.appendReplacement(evaluated, sust.toString());
		}
		matcher.appendTail(evaluated);
		return evaluated.toString();
	}

	protected static String evaluateDynamicToken(String token) {
		if (StringUtils.equalsIgnoreCase(token, DYNAMIC_YEAR)) {
			return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		} else if (StringUtils.equalsIgnoreCase(token, DYNAMIC_MONTH)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, DYNAMIC_DAY)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, DYNAMIC_HOUR)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, DYNAMIC_MINUTE)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), 2, "0");
		} else if (StringUtils.equalsIgnoreCase(token, DYNAMIC_SECOND)) {
			return StringUtils.leftPad(String.valueOf(Calendar.getInstance().get(Calendar.SECOND)), 2, "0");
		}
		return "";
	}

	protected static Document getMetadatosDocument(List<MetadatoVO> metadatos)
			throws ParseException {

		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");

		Element root = doc.addElement("metadatos");

		if (metadatos != null) {
			for (MetadatoVO metadato : metadatos) {

				Element elemMetadato = root.addElement("metadato");

				Element elemNombre = elemMetadato.addElement("nombre");
				elemNombre.setText(metadato.getNombre());

				Element elemValor = elemMetadato.addElement("valor");
				elemValor.setText(ConvertUtils.toString(metadato.getValor()));
			}
		}

		return doc;
	}
}
