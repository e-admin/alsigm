
package ieci.tecdoc.sbo.idoc.util;

import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.dao.DaoUsrPoolTbl;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

import java.util.Date;

public final class UsrPoolMgr
{

   private UsrPoolMgr()
   {
   }  

   public static int getFreeUser(String entidad) throws Exception
   {
      
      IeciTdLongIntegerArrayList freeUserIds = null; 
      int                        numFreeUsers, i;  
      int                        freeUserId = -1, userId;
      boolean                    findFreeUser = false; 

      DbConnection dbCon=new DbConnection();
      try{
      	dbCon.open(DBSessionManager.getSession(entidad));
      freeUserIds = DaoUsrPoolTbl.selectFreeUserIds(dbCon);
      }
      catch (Exception e)
      {
     	 throw e;
      }finally{
     	 dbCon.close();
      }
      
      numFreeUsers = freeUserIds.count(); 

      for (i = 0; i < numFreeUsers; i++)
      {
         userId = freeUserIds.get(i);
         
         DbConnection dbConn=new DbConnection();
         try{
         	dbConn.open(DBSessionManager.getSession(entidad));
   
            dbConn.beginTransaction();

            DaoUsrPoolTbl.lockUserRow(dbConn, userId);

            //Se comprueba que nadie le ha cambiado el estado antes de bloquear
            // la fila

            if (DaoUsrPoolTbl.isUserFree(dbConn, userId))
            {
               DaoUsrPoolTbl.setInUseStatus(dbConn, userId);

               findFreeUser = true;
               freeUserId   = userId;               
            }   
   
            dbConn.endTransaction(true);

            if (findFreeUser)
               break;   
         }
         catch (Exception e)
         {
        	 throw e;
         }finally{
        	 dbConn.close();
         }

      }

      if (!findFreeUser)
      {
         throw new IeciTdException(UtilError.EC_NOT_EXISTS_POOL_FREE_USER,
                                   UtilError.EM_NOT_EXISTS_POOL_FREE_USER);
      }                     
     
      return freeUserId;
      
   }

   public static void freeUser(int userId, String entidad) throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession(entidad));
   
            dbConn.beginTransaction();

            DaoUsrPoolTbl.setFreeStatus(dbConn, userId);
   
            dbConn.endTransaction(true);
             
         }
         catch (Exception e)
         {
        	 throw e;
         }finally{
        	 dbConn.close();
         }
      
   }

   //TimedOut en minutos
   public static void freeTimedOutUsers(DbConnection dbConn, int toim) throws Exception
   {
      Date   cdt, ts;      
      
      
      cdt = DateTimeUtil.getCurrentDateTime();
      
      ts = DateTimeUtil.addMinutes(cdt, -toim);
      
      DaoUsrPoolTbl.setFreeStatusByTimedOut(dbConn, ts);    
      
   }
   
} // class
