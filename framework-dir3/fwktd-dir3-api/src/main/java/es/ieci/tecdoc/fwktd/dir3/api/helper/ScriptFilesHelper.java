package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class ScriptFilesHelper {

	public static final String OFICINAS = "oficicinas";
	public static final String INIT = "inicializacion";
	public static final String UPDATE = "actualizacion";
	public static final String UNIDADES = "unidadesOrganicas";
	public static final String RELACIONES_UNID_ORG_OFIC = "relacionesUnidOrgOfic";

	private static final String SCRIPT_DCO = "script_dco";
	private static final String SCRIPT_FILE_NAME_SEPARATOR = "_";

	public static String getScriptFileName(String dir, String tipoScript, String tipoEntidades){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		StringBuilder builder = new StringBuilder(dir);
		builder.append(SCRIPT_DCO);
		builder.append(SCRIPT_FILE_NAME_SEPARATOR);
		builder.append(tipoScript);
		builder.append(SCRIPT_FILE_NAME_SEPARATOR);
		builder.append(tipoEntidades);
		builder.append(SCRIPT_FILE_NAME_SEPARATOR);
		builder.append(dateFormat.format(GregorianCalendar.getInstance().getTime()));
		builder.append(".sql");
		return builder.toString();
	}

}
