package ieci.tdw.ispac.ispaclib.reports;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Locale;
import java.util.Properties;

public class JasperReportsProperties extends Properties {
	
	private static final long serialVersionUID = -6463561121552889486L;

	public JasperReportsProperties(String[] keys, String[] languagues, String[][] valores){
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < languagues.length; j++) {
				String[] localeValues = valores[j];
				String prefix = "";
				if (StringUtils.isNotBlank(languagues[j])){
					prefix = languagues[j] + ".";
				}
				this.setProperty(prefix + keys[i], localeValues[i]);
				
			}
		}
	}

	public String getProperty(Locale locale, String key){
		String localeKey = locale.toString() + "." + key;
		String value = getProperty(localeKey);
		if (value== null){
			localeKey = locale.getLanguage() + "." + key;
			value = getProperty(localeKey);
			if (value== null){
				localeKey = key;
				value = getProperty(localeKey);
				if (value == null){
					return "";
				}
			}
		}
		return value;
	}
}