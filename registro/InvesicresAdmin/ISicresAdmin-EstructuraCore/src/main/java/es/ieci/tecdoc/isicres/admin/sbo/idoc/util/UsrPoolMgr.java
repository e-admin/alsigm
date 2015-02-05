
package es.ieci.tecdoc.isicres.admin.sbo.idoc.util;


import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.datetime.DateTimeUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoUsrPoolTbl;

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
      	dbCon.open(DBSessionManager.getSession());
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
         	dbConn.open(DBSessionManager.getSession());
   
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
	   	dbConn.open(DBSessionManager.getSession());
   
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
