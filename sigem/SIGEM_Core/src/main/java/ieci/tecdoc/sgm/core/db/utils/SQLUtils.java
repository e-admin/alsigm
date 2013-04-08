package ieci.tecdoc.sgm.core.db.utils;

import java.util.Date;

public interface SQLUtils {

	public static final String SQL_UTILS_IMPL = "SQL_UTILS_IMPL";
	
	public String obtenerFecha(Date fecha);
	
	public String obtenerTimeStamp(Date fecha);
}
