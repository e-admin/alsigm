
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUpdateFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

import java.util.Date;

public class DaoUsrPoolTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="UPIDOCUSRPOOL";

   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_STATUS = new DbColumnDef
   ("STAT", DbDataType.SHORT_INTEGER, false);  

   private static final DbColumnDef CD_TS = new DbColumnDef
   ("TS", DbDataType.DATE_TIME, true);   
   
   private static final DbColumnDef[] ACD = 
   {CD_USERID, CD_STATUS, CD_TS};  

   private static final String ACN = DbUtil.getColumnNames(ACD);

   private static final DbIndexDef ID_1 = new DbIndexDef
   (TN + "1", CD_USERID.getName(), true);  
   
   private static final DbIndexDef ID_2 = new DbIndexDef
   (TN + "2", CD_STATUS.getName(), false); 

   private static final DbIndexDef ID_3 = new DbIndexDef
   (TN + "3", CD_TS.getName(), false); 

   private static final DbIndexDef[] AID =
   {ID_1, ID_2, ID_3};

   private static final short STATUS_INUSE = 1;
   private static final short STATUS_FREE  = 0;  

   private static final String  UPDSTAT_CN  = DbUtil.getColumnNames
  (CD_STATUS, CD_TS); 
  

// **************************************************************************
   
   private DaoUsrPoolTbl()
   {
   }

   private static String getDefaultQual(int userId)
   {
      return "WHERE " + CD_USERID.getName() + "= " + userId;
   } 

   private static String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  TN + "." + colName;
      
      return colName;
   }
      
// **************************************************************************

   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN, ACD, AID);
   }

   public static void dropTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.dropIndices(dbConn, TN, AID);
      DbTableFns.dropTable(dbConn, TN);
   }    
   
   public static String getTblName()
   {
      return TN;
   }   
   
   public static String getUserIdColName(boolean qualified)
   {
      return getColName(CD_USERID, qualified);      
   }

   // **************************************************************************
   
   public static IeciTdLongIntegerArrayList selectFreeUserIds(DbConnection dbConn) throws Exception
   {

      IeciTdLongIntegerArrayList ids = new IeciTdLongIntegerArrayList();
      String                     qual;

      qual = "WHERE " + CD_STATUS.getName() + "= " + STATUS_FREE;
      
      DbSelectFns.select(dbConn, TN, CD_USERID.getName(), qual, true, ids);

      return ids;
   } 

   public static boolean isUserFree(DbConnection dbConn, int userId) throws Exception
   {

      short   status;
      boolean isFree = false;  
      
      status = DbSelectFns.selectShortInteger(dbConn, TN, CD_STATUS.getName(),
                                              getDefaultQual(userId));
      
      if (status == STATUS_FREE)
         isFree = true;
      else
         isFree = false;

      return isFree;
   } 
    
   // **************************************************************************
 
   private static void updateStatus(DbConnection dbConn, int userId, DaoUsrPoolStatusRowUpd row)
						  throws Exception
	{

      DbUpdateFns.update(dbConn, TN, UPDSTAT_CN, row, getDefaultQual(userId));
                                     
		
	}

   public static void setFreeStatus(DbConnection dbConn, int userId)
						  throws Exception
	{
      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();

      row.m_status = STATUS_FREE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();

      updateStatus(dbConn, userId, row);
		
	}

   public static void setInUseStatus(DbConnection dbConn, int userId)
						  throws Exception
	{

      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();

      row.m_status = STATUS_INUSE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();

      updateStatus(dbConn, userId, row);
		
	}

   public static void setFreeStatusByTimedOut(DbConnection dbConn, Date ts)
						  throws Exception
	{
      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();
      String                 qual, tsStr;

      row.m_status = STATUS_FREE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();
      
      tsStr = DbUtil.getNativeDateTimeSyntax(dbConn, ts, false);

      qual = "WHERE " + CD_TS.getName() + "<=" + tsStr + " AND "
             + CD_STATUS.getName() + "=" + STATUS_INUSE;

      DbUpdateFns.update(dbConn, TN, UPDSTAT_CN, row, qual);
		
	}

   public static void lockUserRow(DbConnection dbConn, int userId) throws Exception
	{
      
      DbUpdateFns.updateLongInteger(dbConn, TN, CD_USERID.getName(),
                                    userId, getDefaultQual(userId));
		
	}

}


