package ieci.tdw.applets.idocscan;

import java.applet.Applet;
import java.util.Properties;

public class Configurator {

	public static String appName;

	public static String appVersion;

	public static String strBanner;

	public static String strUserAgent;

	private static Properties defaultprops;

	private static Properties props;

	private static String settings[][] = {
			{ "debug", ConfigMessages.getString("Configurator.debug") },
			{ "cookieName", ConfigMessages.getString("Configurator.cookieName") },
			{ "uploadActionPath",
					ConfigMessages.getString("Configurator.uploadActionPath") },
			{ "connectionTimeout",
					ConfigMessages.getString("Configurator.connectionTimeout") },
			{ "activexName", ConfigMessages.getString("Configurator.activex.name") } };
	static {
		appName = ConfigMessages.getString("ieci.tecdoc.app.name");
		appVersion = ConfigMessages.getString("ieci.tecdoc.app.version");
		strBanner = appName + " v" + appVersion;
		strUserAgent = appName + "/" + appVersion;
	}

	public Configurator() {
	}

	public static void readConfiguration(Applet t_applet) {
		props = new Properties();
		defaultprops = new Properties();

		debug("Configurator() there are " + settings.length
				+ " known parameters.");

		for (int i = 0; i < settings.length; i++) {
			String strKey = settings[i][0];
			String strDefaultValue = settings[i][1];
			String strUserValue = null;

			if (t_applet != null)
				strUserValue = t_applet.getParameter(strKey);

			debug("Configurator() key=[" + strKey + "] default=["
					+ strDefaultValue + "] uservalue=[" + strUserValue + "]");

			defaultprops.put(strKey, strDefaultValue);

			if (null == strUserValue)
				props.put(strKey, strDefaultValue);
			else
				props.put(strKey, strUserValue);
		}

	}

	public static boolean getDebug() {
		if (props == null)
			return false;

		String debug = (String) props.get("debug");

		return "true".equals(debug);
	}

	public static String getDebugLogFileDir() {
		return getProperty("debugLogFileDir");
	}

	public static String getDebugLogFileName() {
		return getProperty("debugLogFileName");
	}

	public static String getUploadActionPath() {
		return getProperty("uploadActionPath");
	}

	private static void debug(String s) {
		System.out.println(s);
	}

	public static String getProperty(String property) {
		if (props == null)
			return null;

		String prop = (String) props.get(property);

		if (prop != null && !prop.equals(""))
			return prop;

		return null;
	}
}