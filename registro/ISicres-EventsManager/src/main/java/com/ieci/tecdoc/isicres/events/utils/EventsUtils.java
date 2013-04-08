/**
 * 
 */
package com.ieci.tecdoc.isicres.events.utils;

import java.util.Iterator;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;

/**
 * @author 66575267
 * 
 */
public class EventsUtils {

	public static String getDistributeField(Idocarchdet idoc, AxSf axsf) {
		String field = "";
		int extendedFieldsId = Keys.SREG_FDR_MATTER;

		if (axsf instanceof AxSfIn) {
			extendedFieldsId = Keys.EREG_FDR_MATTER;
		}

		FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());
		for (Iterator iterator = fieldFormat.getFlddefs().values().iterator(); iterator
				.hasNext();) {
			FFldDef fldDef = (FFldDef) iterator.next();

			if ((fldDef.getId() > extendedFieldsId)
					&& (fldDef.getLen() == EventsKeys.DISTRIBUIDO_COLUMN_LENGTH)
					&& (fldDef.getType() == EventsKeys.DISTRIBUIDO_COLUMN_TYPE)) {
				if (EventsKeys.DISTRIBUIDO_COLUMN_NAME.equalsIgnoreCase(fldDef
						.getName())) {
					field = fldDef.getColname().toLowerCase();
					break;
				} else {
					field = fldDef.getColname().toLowerCase();
				}
			}			
		}

		return field;
	}

}
