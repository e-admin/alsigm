package ieci.tdw.applets.idocscan.helpers;

import ieci.tdw.applets.idocscan.Debug;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class EnvHelper {

	private static final Properties envVars;

	private static final Properties envVarsLowerCase;

	static {
		envVars = new Properties();
		envVarsLowerCase = new Properties();
		try {
			Process process = null;
			Runtime runtime = Runtime.getRuntime();
			
			String s = System.getProperty("os.name").toLowerCase();
			if (s.indexOf("windows 9") > -1) {
				process = runtime.exec("command.com /c set");
			} else if (s.indexOf("nt") > -1  
					//|| s.indexOf("windows 20") > -1
					//|| s.indexOf("windows xp") > -1
					//|| s.indexOf("windows vista") > -1
					|| s.indexOf("windows") > -1) {
				process = runtime.exec("cmd.exe /c set");
			} else {
				process = runtime.exec("env");
			}
			
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String s1;
			while ((s1 = bufferedreader.readLine()) != null) {
				int i = s1.indexOf('=');
				String s2 = s1.substring(0, i);
				String s3 = s1.substring(i + 1);
				envVars.setProperty(s2, s3);
				envVarsLowerCase.setProperty(s2.toLowerCase(), s3);
			}
		} catch (Exception e) {
			Debug.log("Error al obtener las propiedades del sistema: " + e.toString());
			e.printStackTrace();
		}
	}

	public static String getEnvVar(String s) {
		return (String) envVars.get(s);
	}

	public static String getEnvVarIgnoreCase(String s) {
		return (String) envVarsLowerCase.get(s.toLowerCase());
	}

}