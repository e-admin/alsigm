package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author RAULHC
 *
 */
public class RegistroBase {
	
	protected final static String NULL_INTEGER = "-2147483648";
	
	InvesDocConnection invesDocConnection = null;
	Registro registroApi = null;
	InveSicresConfiguration mConfig;
	
	public RegistroBase(InvesDocConnection invesDocConnection) throws ISPACException {
		this.invesDocConnection = invesDocConnection;
		registroApi = new Registro(invesDocConnection);
		mConfig = InveSicresConfiguration.getInstance();
	}

	protected final Integer getValueId(String tableId, String id, String code) throws ISPACException {
		Integer result = null;
		if (id != null && id.length() > 0) {
			 result = new Integer(id);
		} else {
			if (code != null && code.length() > 0) {
				id = registroApi.getScrIdFromCode(tableId, code);
				if (id != null && id.length() > 0) {
					result = new Integer(id);
				}
			}
		}
		if (result != null && result.intValue() == -1) result = null;
		return result;
	}

	protected final Integer getOrgIdFromCif(String cif) throws ISPACException {
		Integer result = null;
		if (cif != null && cif.length() > 0) {
			String qual = "WHERE CIF = '" + cif + "'";
			String id = invesDocConnection.DbSelect1C1R("ID", mConfig.get(InveSicresConfiguration.TBL_ORGS), qual);
			if (id != null && id.length() > 0) result = new Integer(id);
		}
		return result;
	}
	
	protected final String getOrgCifFromId(String id) throws ISPACException {
		String result = null;
		if (id != null && id.length() > 0) {
			String qual = "WHERE ID = " + id;
			result = invesDocConnection.DbSelect1C1R("CIF", mConfig.get(InveSicresConfiguration.TBL_ORGS), qual);
		}
		return result;
	}
	
	protected final String getDateTimeFormat(Date dateTime) {
		Format format = new SimpleDateFormat(mConfig.get(InveSicresConfiguration.DATE_TIME_FORMAT));
		return format.format(dateTime);
	}

	protected final String getDateFormat(Date date) {
		Format format = new SimpleDateFormat(mConfig.get(InveSicresConfiguration.DATE_FORMAT));
		return format.format(date);
	}
	
	protected final Date parseDate(String fechaStr) throws ISPACException {
		if (fechaStr == null || fechaStr.length() == 0) return null;
		try {
			return DateFormat.getInstance().parse(fechaStr);
		} catch (ParseException e) {
			throw new ISPACException("La fecha \"" + fechaStr + "\" no es correcta.", e);
		}
	}
	
}
