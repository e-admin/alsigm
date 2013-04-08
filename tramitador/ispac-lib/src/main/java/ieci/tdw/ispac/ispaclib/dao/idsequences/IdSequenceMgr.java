/*
 * Created on 20-abr-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.idsequences;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCallableStatement;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author juanin
 *
 */
public class IdSequenceMgr
{
	
	public final static String SEQUENCE_NAME_PREFIX = "SPAC_SQ_";
	
	static public List getSequences(DbCnt cnt)
	throws ISPACException
	{
	    return getSequences(cnt,"SPAC_SQ_SEQUENCES");
	}

	static private List getSequences(DbCnt cnt,String seqtable)
	throws ISPACException
	{
	    String sQuery = "SELECT SEQUENCE_NAME FROM "+seqtable;
	    DbResultSet dbrs=null;
	    ArrayList sequencelist=new ArrayList();
		try
		{
	        dbrs = cnt.executeQuery(sQuery);
	        ResultSet rs=dbrs.getResultSet();
	        while (rs.next())
	        {
	            sequencelist.add(rs.getString("SEQUENCE_NAME"));
	        }
	        return sequencelist;

		}catch(SQLException e)
		{
			throw new ISPACException(e);
		}
		finally
		{
		    if (dbrs!=null)
		        dbrs.close();
		}
	}

	static public int getIdSequence(DbCnt cntorig,String sequence)
	throws ISPACException
	{
	    if (!cntorig.ongoingTX())
	        return IdSequenceMgr.nextVal(cntorig,sequence);

	    DbCnt cnt = new DbCnt();
		try
		{
		    cnt.getConnection();
			return IdSequenceMgr.nextVal(cnt,sequence);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IdSequenceMgr:getIdSequence("+ sequence + ")", e);
		}
		finally
		{
		    cnt.closeConnection();
		}
	}

	static public int nextVal(DbCnt cnt,String sequence)
	throws ISPACException
	{
		//si la base de datos es postgree la invocacion al procedimiento almacenado se hace mediante sentencia SELECT
		if (cnt.isPostgreSQLDbEngine()){
			StringBuffer sql = new StringBuffer("SELECT SPAC_NEXTVAL('");
			sql.append(sequence);
			sql.append("')");
			DbResultSet rs = null;
			try {
				rs = cnt.executeQuery(sql.toString());
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
			
		}else{
			String callquery = "{ CALL SPAC_NEXTVAL( ? , ? ) }";
			DbCallableStatement callstmt=null;
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

/*
	static public int getIdSequenceOracle(DbCnt cnt,String sequence)
		throws ISPACException
	{
		String sQuery = "SELECT "+sequence+".NEXTVAL ID FROM DUAL";
		try
		{
	        DbResultSet dbrs = cnt.executeQuery(sQuery);
	        ResultSet rs=dbrs.getResultSet();
	        rs.next();
	        int id = rs.getInt("ID");
	        dbrs.close();
	        return id;
		}catch(SQLException e)
		{
			throw new ISPACException(e);
		}
	}
*/
}
