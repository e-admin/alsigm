/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.legacy.ws.constants.WSFieldTypeConstants;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class EsquemaLibroFieldVOTipoToWSFieldTypeMapper implements Mapper {

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		String tipoStr = (String) obj;
		Integer tipo = Integer.valueOf(tipoStr);
		String type = null;
		switch (tipo.intValue()) {
		case WSFieldTypeConstants.SHORT_TEXT:
			type = WSFieldTypeConstants.TYPE_CHAR;
			break;
		case WSFieldTypeConstants.LONG_TEXT:
			type = WSFieldTypeConstants.TYPE_CHAR;
			break;
		case WSFieldTypeConstants.DATE_TIME:
			type = WSFieldTypeConstants.TYPE_DATETIME;
			break;
		case WSFieldTypeConstants.DATE:			
			type = WSFieldTypeConstants.TYPE_DATE;
			break;
		case WSFieldTypeConstants.TIME:
			type=WSFieldTypeConstants.TYPE_DATETIME;
			break;
		case WSFieldTypeConstants.LONG_DECIMAL:
			type = WSFieldTypeConstants.TYPE_DECIMAL;
			break;
		case WSFieldTypeConstants.LONG_INTEGER:
			type = WSFieldTypeConstants.TYPE_NUMBER;
			break;
		case WSFieldTypeConstants.SHORT_DECIMAL:
			type = WSFieldTypeConstants.TYPE_DECIMAL;
			break;
		case WSFieldTypeConstants.SHORT_INTEGER:
			type = WSFieldTypeConstants.TYPE_NUMBER;
			break;
		default:
			
			break;
		}
		return type;
	}

}
