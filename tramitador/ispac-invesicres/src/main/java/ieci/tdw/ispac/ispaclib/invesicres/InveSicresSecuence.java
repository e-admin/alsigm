package ieci.tdw.ispac.ispaclib.invesicres;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCallableStatement;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;

import java.sql.SQLException;
import java.sql.Types;

public class InveSicresSecuence {
	
	public static int getNexSecuence(DbCnt cnt) throws ISPACException {
		return getIdSequence(cnt, "SCR_SEQCNT");
	}
	
	protected static int getIdSequence(DbCnt cntorig, String sequence)
			throws ISPACException {
		
	    if (!cntorig.ongoingTX()) {
	        return nextVal(cntorig,sequence);
	    }

	    DbCnt cnt = new DbCnt(cntorig.getPoolName());
	    
		try  {
		    cnt.getConnection();
			return nextVal(cnt,sequence);
		} catch (ISPACException e) {
			throw new ISPACException("Error al obtener el valor de la secuencia ("+ sequence + ")", e);
		} finally {
		    cnt.closeConnection();
		}
	}

	protected static int nextVal(DbCnt cnt, String sequence) 
			throws ISPACException {
		
		String sql = null;
		
		if (cnt.isPostgreSQLDbEngine()) {
			sql = "SELECT NEXTVAL('" + sequence + "')";
		} else if (cnt.isOracleDbEngine()) {
			sql = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
		} else if (cnt.isDB2DbEngine()) {
			sql = "VALUES NEXTVAL FOR " + sequence;
		}

		if (sql != null) {
			DbResultSet rs = cnt.executeQuery(sql.toString());
			try {
				if (rs.getResultSet().next()){
					return rs.getResultSet().getBigDecimal(1).intValue();
				}else{
					throw new ISPACException("Nombre de secuencia no existente");
				}
			} catch (SQLException e) {
				throw new ISPACException(e);
			} finally {
				if (rs != null) {
					rs.close();
				}
			}
		} else {
			String callquery = "{ CALL SPAC_NEXTVAL( ? , ? ) }";
			DbCallableStatement callstmt = null;
			try {
			    callstmt=cnt.prepareCall(callquery);
			    callstmt.setString(1,sequence);
			    callstmt.registerOutputParameter(2,Types.INTEGER);
			    callstmt.execute();
			    return callstmt.getInt(2);
			} finally {
				if (callstmt != null) {
					callstmt.close();
				}
			}
		}
	}
}
