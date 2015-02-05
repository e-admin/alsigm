package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

public class DbDAOUtils {
	
	protected static int executeCreate(DbCnt cnt, String sql) throws ISPACException {
		
		int res = 0;
		DbPreparedStatement ps = null;

		try {
			ps = cnt.prepareDBStatement(sql);
			res = ps.executeUpdate();
		}
		catch (Exception e) {
			throw new ISPACException(e.getCause().getMessage(), e);
		}
		finally {
			if (ps != null) {
				ps.close();
			}
		}
		
		return res;
	}
	
	protected static DbResultSet executeQuery(DbCnt cnt, String sql) throws ISPACException {
		
		DbResultSet dbrs = null;
		
		try {
			dbrs = cnt.executeQuery(sql);
		}
		catch (Exception e) {
			
			if (dbrs!=null)
				dbrs.close();
			
			throw new ISPACException(e.getCause().getMessage(), e);
		}
		finally {
		}
		
		return dbrs;
	}

}