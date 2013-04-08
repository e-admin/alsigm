/*
 * Created on 20-abr-2004
 *
 *
 */
package ieci.tdw.ispac.ispaclib.dao.idsequences;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCallableStatement;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.sql.Types;

/**
 * @author juanin
 *
 *
 */
public class IdSequenceGen
{
	static public int getIdSequence(DbCnt cnt,String sequence)
		throws ISPACException
	{
		String callquery = "CALL ISPAC_NEXTVAL('"+sequence+"',?)";
        DbCallableStatement callstmt=cnt.prepareCall(callquery);
        callstmt.registerOutputParameter(1,Types.INTEGER);
        callstmt.executeUpdate();
        return callstmt.getInt(1);
	}
}
