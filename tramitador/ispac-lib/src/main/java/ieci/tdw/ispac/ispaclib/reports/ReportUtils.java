package ieci.tdw.ispac.ispaclib.reports;

import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class ReportUtils {
	
	public static String generateFilter(String field, String[] values) {
    
		/*
		 * 	Ejemplo:
		 * 
		 *  (SPAC_EXPEDIENTES.NUMEXP='EXP2011/000002' OR SPAC_EXPEDIENTES.NUMEXP='EXP2011/000005' OR
		 *  SPAC_EXPEDIENTES.NUMEXP='EXP2011/000001' OR SPAC_EXPEDIENTES.NUMEXP='EXP2011/000003' OR
		 *  SPAC_EXPEDIENTES.NUMEXP='EXP2011/000006' OR SPAC_EXPEDIENTES.NUMEXP='EXP2011/000004')
		 */

		StringBuffer filter = new StringBuffer();
		
		if (!ArrayUtils.isEmpty(values)) {
			
			filter.append("(");
			
			filter.append(field).append("='").append(DBUtil.replaceQuotes(values[0])).append("'");
			for (int i = 1; i < values.length; i++) {
				filter.append(" OR ").append(field).append("='").append(DBUtil.replaceQuotes(values[i])).append("'");
			}
			
			filter.append(")");
		}
		
    
    	return filter.toString();
	}

}