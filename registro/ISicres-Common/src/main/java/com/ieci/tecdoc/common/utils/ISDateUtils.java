/**
 *
 */
package com.ieci.tecdoc.common.utils;

import java.util.Date;

import com.ieci.tecdoc.common.invesicres.ScrTmzofic;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class ISDateUtils {

	public static final long HORA = 60 * 60 * 1000;

	/**
	 *
	 * @param date
	 * @param scrTmzofic
	 * @return
	 */
	public static Date calculateTmzOfic(Date date, ScrTmzofic scrTmzofic) {
		Date traslatedTime = date;
		if (scrTmzofic != null) {
			long lTraslatedTime = (scrTmzofic.getTmz() * HORA) + date.getTime();
			traslatedTime = new Date(lTraslatedTime);
		} else {
			traslatedTime = new Date(date.getTime());
		}
		return traslatedTime;
	}

}
