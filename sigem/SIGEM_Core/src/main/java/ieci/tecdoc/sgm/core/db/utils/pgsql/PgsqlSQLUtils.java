package ieci.tecdoc.sgm.core.db.utils.pgsql;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ieci.tecdoc.sgm.core.db.utils.SQLUtils;

public class PgsqlSQLUtils implements SQLUtils {

	private static final String SEPARADOR_FECHA = "/";
	private static final String SEPARADOR_HORA = ":";
	private static final String CERO = "0";
	private static final String SEPARADOR_FECHA_TIMESTAMP = "-";
	
	public String obtenerFecha(Date fecha) {
		if(fecha == null){
			return null;
		}
		Calendar oCal = GregorianCalendar.getInstance();
		oCal.setTime(fecha);
		StringBuffer sbFecha = new StringBuffer();
		int aux = oCal.get(Calendar.DAY_OF_MONTH);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_FECHA);
		aux = oCal.get(Calendar.MONTH) + 1;
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_FECHA);
		sbFecha.append(oCal.get(Calendar.YEAR));
		sbFecha.append(" ");

		aux = oCal.get(Calendar.HOUR_OF_DAY);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_HORA);

		aux = oCal.get(Calendar.MINUTE);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_HORA);

		aux = oCal.get(Calendar.SECOND);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		
		return sbFecha.toString();
	}

	public String obtenerTimeStamp(Date fecha) {
		if(fecha == null){
			return null;
		}
		Calendar oCal = GregorianCalendar.getInstance();
		oCal.setTime(fecha);
		StringBuffer sbFecha = new StringBuffer();
		//año
		int aux = oCal.get(Calendar.YEAR);
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_FECHA_TIMESTAMP);
		
		// mes
		aux = oCal.get(Calendar.MONTH) + 1;
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_FECHA_TIMESTAMP);
		
		// dia
		aux = oCal.get(Calendar.DAY_OF_MONTH);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);		
		sbFecha.append(" ");
		
		//hora
		aux = oCal.get(Calendar.HOUR_OF_DAY);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_HORA);

		// minutos
		aux = oCal.get(Calendar.MINUTE);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
		sbFecha.append(SEPARADOR_HORA);

		// segundos
		aux = oCal.get(Calendar.SECOND);
		if(aux < 10){
			sbFecha.append(CERO);
		}
		sbFecha.append(aux);
				
		return sbFecha.toString();
	}

}
