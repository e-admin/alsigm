package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbPreparedStatement;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.ClassLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseDAO {

	protected static Object getVO(DbCnt cnt, String sql, Class voClass) 
			throws ISPACException {
		return getVO(cnt, sql, null, voClass);
	}

	protected static Object getVO(DbCnt cnt, String sql, Object [] values, 
			Class voClass) throws ISPACException {
		
		DbInitializable vo = null;
		DbPreparedStatement ps = null;
		DbQuery dbq = null;
		
		try {
			ps = cnt.prepareDBStatement(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i+1, values[i]);
				}
			}

			dbq = ps.executeQuery();
			if (dbq.next()) {
				vo = (DbInitializable) ClassLoaderUtil.getInstance(voClass);
				vo.init(dbq);
		    }
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
		return vo;
	}

	protected static List getVOs(DbCnt cnt, String sql, Class voClass) 
			throws ISPACException {
		return getVOs(cnt, sql, null, voClass);
	}

	protected static List getVOs(DbCnt cnt, String sql, Object [] values, 
			Class voClass) throws ISPACException {
		
		List voList = new ArrayList();
		DbInitializable vo = null;
		DbPreparedStatement ps = null;
		DbQuery dbq = null;
		
		try {
			ps = cnt.prepareDBStatement(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i+1, values[i]);
				}
			}

			dbq = ps.executeQuery();
			while (dbq.next()) {
				vo = (DbInitializable) ClassLoaderUtil.getInstance(voClass);
				vo.init(dbq);
				voList.add(vo);
		    }
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
		return voList;
	}

	protected static int update(DbCnt cnt, String sql, Object [] values) 
			throws ISPACException {
		
		int res = 0;
		DbPreparedStatement ps = null;
		
		try {
			ps = cnt.prepareDBStatement(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i+1, values[i]);
				}
			}
			res = ps.executeUpdate();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
		return res;
	}
}
