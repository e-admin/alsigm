package ieci.tecdoc.sbo.util.nextid;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;


public final class DaoNextIdTbl
{

   // **************************************************************************

   private String              m_tblName;

   private final DbColumnDef   CD_TYPE = new DbColumnDef("TYPE",
                                             DbDataType.LONG_INTEGER, false);

   private final DbColumnDef   CD_ID   = new DbColumnDef("ID",
                                             DbDataType.LONG_INTEGER, false);

   private final DbColumnDef[] ACD     =
                                       { CD_TYPE, CD_ID};

   private final String        ACN     = DbUtil.getColumnNames(ACD);

   // **************************************************************************

   public DaoNextIdTbl(String tblName)
   {
      m_tblName = tblName;
   }

   // **************************************************************************

   private String getDefaultQual(int type)
   {
      return "WHERE " + CD_TYPE.getName() + "=" + type;
   }

   public void incrementNextId(int type, String entidad) throws Exception
   {

      incrementNextId(type, (short) 1, entidad);

   }

   public void incrementNextId(int type, short incr, String entidad) throws Exception
   {

      String stmtText;

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
	      stmtText = "UPDATE " + m_tblName + " SET " + CD_ID.getName() + "="
	            + CD_ID.getName() + "+" + incr + " WHERE " + CD_TYPE.getName()
	            + "=" + type;
	
	      DbUtil.executeStatement(dbConn, stmtText);
      }catch (Exception e){
    		throw e;
    	}finally{
    		dbConn.close();
    	}

   }

   public int getNextId(int type, String entidad) throws Exception
   {

      int nextId;
      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
	      nextId = DbSelectFns.selectLongInteger(dbConn, m_tblName, CD_ID.getName(),
	            getDefaultQual(type));
      }catch (Exception e){
    		throw e;
    	}finally{
    		dbConn.close();
    	}
      return nextId;

   }

} // class
