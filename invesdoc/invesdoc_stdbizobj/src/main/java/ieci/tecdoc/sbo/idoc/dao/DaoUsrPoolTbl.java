
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import java.util.Date;

public class DaoUsrPoolTbl
{
   private static final String TN = "UPIDOCUSRPOOL";

   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_STATUS = new DbColumnDef
   ("STAT", DbDataType.SHORT_INTEGER, false);  

   private static final DbColumnDef CD_TS = new DbColumnDef
   ("TS", DbDataType.DATE_TIME, true);
   
   private static final DbColumnDef CD_GROUPID = new DbColumnDef
   ("GROUPID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = 
   {CD_USERID, CD_STATUS, CD_TS, CD_GROUPID};  

   private static final DbIndexDef ID_1 = new DbIndexDef
   (TN + "1", CD_USERID.getName(), false, false);  
   
   private static final DbIndexDef ID_2 = new DbIndexDef
   (TN + "2", CD_STATUS.getName(), false, false); 

   private static final DbIndexDef ID_3 = new DbIndexDef
   (TN + "3", CD_TS.getName(), false, false); 

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
   
   private static String getDefaultGroupQual(int userId, int groupId)
   {
      String qual;
      
      qual = "WHERE " + CD_USERID.getName() + "= " + userId;
      qual = qual + " AND " + CD_GROUPID.getName() + "= " + groupId;
      
      return qual;
      
   } 

   private static String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  TN + "." + colName;
      
      return colName;
   }
      
// **************************************************************************

   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN, ACD, AID);
   }

   public static void dropTable() throws Exception
   {
      DbTableFns.dropIndices(TN, AID);
      DbTableFns.dropTable(TN);
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
   
   public static IeciTdLongIntegerArrayList selectFreeUserIds(int groupId) throws Exception
   {

      IeciTdLongIntegerArrayList ids = new IeciTdLongIntegerArrayList();
      String                     qual;

      qual = "WHERE " + CD_STATUS.getName() + "= " + STATUS_FREE;
      
      if (groupId > 0)
         qual = qual + " AND " + CD_GROUPID.getName() + "= " + groupId;
      
      DbSelectFns.select(TN, CD_USERID.getName(), qual, true, ids);

      return ids;
   } 
   
   public static IeciTdLongIntegerArrayList selectFreeUserIds() throws Exception
   {

      IeciTdLongIntegerArrayList ids = new IeciTdLongIntegerArrayList();
      String                     qual;

      qual = "WHERE " + CD_STATUS.getName() + "= " + STATUS_FREE;      
           
      DbSelectFns.select(TN, CD_USERID.getName(), qual, true, ids);

      return ids;
   } 

   public static boolean isUserFree(int userId, int groupId) throws Exception
   {

      short   status;
      boolean isFree = false;  
      String  qual;
      
      if (groupId > 0)
         qual = getDefaultGroupQual(userId,groupId);
      else
         qual = getDefaultQual(userId);
      
      status = DbSelectFns.selectShortInteger(TN, CD_STATUS.getName(),
                                              qual);
      
      if (status == STATUS_FREE)
         isFree = true;
      else
         isFree = false;

      return isFree;
   } 
   
   public static boolean isUserFree(int userId) throws Exception
   {

      short   status;
      boolean isFree = false;  
      String  qual;
      
      qual = getDefaultQual(userId);
      
      status = DbSelectFns.selectShortInteger(TN, CD_STATUS.getName(),
                                              qual);
      
      if (status == STATUS_FREE)
         isFree = true;
      else
         isFree = false;

      return isFree;
   } 
    
   // **************************************************************************
 
   private static void updateStatus(int userId, DaoUsrPoolStatusRowUpd row)
						  throws Exception
	{
      
      String qual;
      
      qual = getDefaultQual(userId);
      
      DbUpdateFns.update(TN, UPDSTAT_CN, row, qual);                                           
		
	}

   public static void setFreeStatus(int userId)
						  throws Exception
	{
      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();

      row.m_status = STATUS_FREE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();

      updateStatus(userId, row);
		
	}

   public static void setInUseStatus(int userId)
						  throws Exception
	{

      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();

      row.m_status = STATUS_INUSE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();

      updateStatus(userId, row);
		
	}

   public static void setFreeStatusByTimedOut(Date ts)
						  throws Exception
	{
      DaoUsrPoolStatusRowUpd row = new DaoUsrPoolStatusRowUpd();
      String                 qual, tsStr;

      row.m_status = STATUS_FREE;
      row.m_ts     = DateTimeUtil.getCurrentDateTime();
      
      tsStr = DbUtil.getNativeDateTimeSyntax(ts, false);

      qual = "WHERE " + CD_TS.getName() + "<=" + tsStr + " AND "
             + CD_STATUS.getName() + "=" + STATUS_INUSE;

      DbUpdateFns.update(TN, UPDSTAT_CN, row, qual);
		
	}

   public static void lockUserRow(int userId, int groupId) throws Exception
	{
      String qual;
      
      if (groupId > 0)
         qual = getDefaultGroupQual(userId, groupId);
      else
         qual = getDefaultQual(userId);
      
      DbUpdateFns.updateLongInteger(TN, CD_USERID.getName(),
                                    userId, qual);
		
	}

}


