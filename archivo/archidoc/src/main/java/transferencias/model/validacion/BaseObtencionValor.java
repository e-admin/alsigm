package transferencias.model.validacion;

import org.apache.commons.lang.StringUtils;

public abstract class BaseObtencionValor implements ObtencionValor {

	protected String createSimpleDataValue(String valorDato) {
		StringBuffer buff = new StringBuffer("<valor>").append(
				StringUtils.defaultString(valorDato)).append("</valor>");
		return buff.toString();
	}
}
