package ieci.tecdoc.sgm.tiempos.impl;

import java.util.Calendar;
import java.util.Date;

import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;
import ieci.tecdoc.sgm.core.services.tiempos.TiemposException;

public class ServicioTiemposImpl implements ServicioTiempos {

	public Date getCurrentDate() throws TiemposException {
		return Calendar.getInstance().getTime();
	}

	public long getCurrentTime() throws TiemposException {
		return Calendar.getInstance().getTimeInMillis();
	}

}
