
package ieci.tecdoc.sbo.idoc.util;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.idoc.dao.DaoUsrPoolTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoAuxUsrPoolTbl;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import java.util.Date;
import ieci.tecdoc.core.datetime.DateTimeUtil;

public final class UsrPoolMgr
{

   private UsrPoolMgr()
   {
   }  
   
   public static int getFreeUser() throws Exception
   {
      
	   IeciTdLongIntegerArrayList freeUserIds = null; 
	      int                        numFreeUsers, i;  
	      int                        freeUserId = -1, userId;
	      boolean                    findFreeUser = false; 

	      freeUserIds = DaoUsrPoolTbl.selectFreeUserIds();

	      numFreeUsers = freeUserIds.count(); 

	      for (i = 0; i < numFreeUsers; i++)
	      {
	         userId = freeUserIds.get(i);
	         
	         try
	         {
	   
	            DbConnection.beginTransaction();
	            
	            DaoAuxUsrPoolTbl.lockUser(userId);           

	            //Se comprueba que nadie le ha cambiado el estado antes de bloquear
	            // la fila

	            if (DaoUsrPoolTbl.isUserFree(userId))
	            {
	               DaoUsrPoolTbl.setInUseStatus(userId);

	               findFreeUser = true;
	               freeUserId   = userId;               
	            }   
	   
	            DbConnection.endTransaction(true);

	            if (findFreeUser)
	               break;   
	         }
	         catch (Exception e)
	         {
	   
	            DbConnection.ensureEndTransaction(e);
	   
	         }

	      }

	      if (!findFreeUser)
	      {
	         throw new IeciTdException(UtilError.EC_NOT_EXISTS_POOL_FREE_USER,
	                                   UtilError.EM_NOT_EXISTS_POOL_FREE_USER);
	      }                     
	     
	      return freeUserId;
      
   }

   public static int getFreeUser(int groupId) throws Exception
   {
      
      IeciTdLongIntegerArrayList freeUserIds = null; 
      int                        numFreeUsers, i;  
      int                        freeUserId = -1, userId;
      boolean                    findFreeUser = false; 

      freeUserIds = DaoUsrPoolTbl.selectFreeUserIds(groupId);

      numFreeUsers = freeUserIds.count(); 

      for (i = 0; i < numFreeUsers; i++)
      {
         userId = freeUserIds.get(i);
         
         try
         {
   
            DbConnection.beginTransaction();
            
            DaoAuxUsrPoolTbl.lockUser(userId);           

            //Se comprueba que nadie le ha cambiado el estado antes de bloquear
            // la fila

            if (DaoUsrPoolTbl.isUserFree(userId, groupId))
            {
               DaoUsrPoolTbl.setInUseStatus(userId);

               findFreeUser = true;
               freeUserId   = userId;               
            }   
   
            DbConnection.endTransaction(true);

            if (findFreeUser)
               break;   
         }
         catch (Exception e)
         {
   
            DbConnection.ensureEndTransaction(e);
   
         }

      }

      if (!findFreeUser)
      {
         throw new IeciTdException(UtilError.EC_NOT_EXISTS_POOL_FREE_USER,
                                   UtilError.EM_NOT_EXISTS_POOL_FREE_USER);
      }                     
     
      return freeUserId;
      
   }
   
   public static void freeUser(int userId) throws Exception
   {
      int groupId = 0;     
      
      freeUser(userId, groupId);
      
   }

   public static void freeUser(int userId, int groupId) throws Exception
   {

         try
         {
   
            DbConnection.beginTransaction();

            DaoUsrPoolTbl.setFreeStatus(userId);
   
            DbConnection.endTransaction(true);
             
         }
         catch (Exception e)
         {
   
            DbConnection.ensureEndTransaction(e);
   
         }      
      
   }

   //TimedOut en minutos
   public static void freeTimedOutUsers(int toim) throws Exception
   {
      Date   cdt, ts;      
      
      
      cdt = DateTimeUtil.getCurrentDateTime();
      
      ts = DateTimeUtil.addMinutes(cdt, -toim);
      
      DaoUsrPoolTbl.setFreeStatusByTimedOut(ts);    
      
   }
   
} // class
