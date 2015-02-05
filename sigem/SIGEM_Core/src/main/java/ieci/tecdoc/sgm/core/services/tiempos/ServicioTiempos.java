package ieci.tecdoc.sgm.core.services.tiempos;

import java.util.Date;

public interface ServicioTiempos {
	
	/**
	 * 
	 * @return
	 * @throws TiemposException
	 */
	public Date getCurrentDate() throws TiemposException;
	
	/**
	 * 
	 * @return
	 * @throws TiemposException
	 */
	public long getCurrentTime() throws TiemposException;

}
