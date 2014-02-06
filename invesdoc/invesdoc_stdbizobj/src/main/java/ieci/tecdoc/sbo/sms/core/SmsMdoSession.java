
package ieci.tecdoc.sbo.sms.core;

import java.util.Date;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.core.crypto.CryptoUtil;
import ieci.tecdoc.core.textutil.Util;

public final class SmsMdoSession
{
   
   private SmsMdoSession()
   {
   }
   
   // NO poner transacción, ya que al insertar sesión se necesita obtener
   // un id y para ello se abre y cierra una transacción.

   public static String beginSession(String appId, int userId) throws Exception
   {
      
      String encSessId;
      int    decSessId;      
      
      deletePreviousSession(appId, userId);  

      decSessId = insertSession(appId, userId);
         
      encSessId = encodeSessionId(decSessId, appId, userId);
         
      return encSessId;
      
   }
   
   // NO poner transacción, ya que al insertar sesión se necesita obtener
   // un id y para ello se abre y cierra una transacción.
   // Permite conexión simultánea del mismo usuario

   public static String beginGenericSession(int userId) throws Exception
   {
      
      String encSessId;
      int    decSessId;
      String appId = SmsApp.GENERIC;

      decSessId = insertSession(appId, userId);
         
      encSessId = encodeSessionId(decSessId, appId, userId);
         
      return encSessId;
      
   }

   public static void endSession(String sessId)
                      throws Exception
   {
      
      int decSessId;
      
      decSessId = getDecodedSessionId(sessId);
      
      deleteSession(decSessId);
      
   }

   // Si aInfo es null, no se chequea.
   
   public static boolean isValidSession(String sessId)
                         throws Exception
   {
      
      boolean         valid = true;
      int             decSessId, userId;
      SmsDaoSessRecAc rec;
      
      decSessId = getDecodedSessionId(sessId);
      
      rec = selectSessionRow(decSessId);
      
      if (rec == null)      
         return false;         
      
      valid = validateSessionId(decSessId, rec.m_appId, rec.m_userId, sessId);
      
      return valid;
                                            
   }   
   
   // **************************************************************************
  
   // Fuera de transacción
   
   private static int insertSession(String appId, int userId)
                      throws Exception
   {
      
      int             sessId;
      Date            cdt;
      SmsDaoSessRecAc recAc;
      
      sessId = SmsNextId.generateNextId();
      cdt    = DateTimeUtil.getCurrentDateTime();
      
      recAc = new SmsDaoSessRecAc();
      
      recAc.m_id     = sessId;
      recAc.m_appId  = appId;
      recAc.m_userId = userId;      
      recAc.m_crtTs  = cdt;

      SmsDaoSessTbl.insertRow(recAc);

      return sessId;
      
   }
   
   private static void deletePreviousSession(String appId, int userId)
                       throws Exception
   {
      SmsDaoSessTbl.deleteRow(appId, userId);
   }
   
   private static void deleteSession(int id)
                       throws Exception
   {
      SmsDaoSessTbl.deleteRow(id);
   }
   
   private static SmsDaoSessRecAc selectSessionRow(int sessId)
                                 throws Exception
   {      
     
      try
      {         
         return SmsDaoSessTbl.selectRow(sessId);
      }
      catch (IeciTdException e)
      {
         if (e.getErrorCode() == DbError.EC_NOT_FOUND)
            return null;
         else
            throw e;
      }
      
   }
   
   // **************************************************************************

   private static String formatSessionId(int id) 
                         throws Exception
   {
      
      String       formatId;
      String       strId, aux;
      int          len;
           
      strId  = Util.formatLongInteger(id);
      len    = strId.length();
       
      aux = new String(strId);     
      
      while(len < 32)
      {
         aux = "0" + aux;
         len = len + 1;         
      }      
            
      formatId = new String(aux);      
      
      return formatId;
      
   }
   private static String encodeSessionId(int decId, String appId, int userId) 
                         throws Exception
   {
      
      String encId;
      byte[] hash;
      String encHash;
      String strDecId, strUserId;
      
      strDecId  = Util.formatLongInteger(decId);
      strUserId = Util.formatLongInteger(userId);
     
      hash    = CryptoUtil.generateSha1Hash(strDecId + appId + strUserId);
      encHash = Base64Util.encode(hash);
      encHash = encHash.replace('=', '_'); // Para que se pueda pasar por url.
      encHash = encHash.replace('+', '_'); // Para que se pueda pasar por url.
      encHash = encHash.replace('/', '_'); // Para que se pueda pasar por url.
      encId   = formatSessionId(decId) + encHash;
      
      return encId;
      
   }
   
   private static boolean validateSessionId(int decId, String appId, 
                                            int userId, String encId)
                          throws Exception
   {
      
      boolean valid = false;
      String  encId1;
      
      encId1 = encodeSessionId(decId, appId, userId);
      
      if (encId1.equals(encId))
         valid = true;
      
      return valid;
      
   }
   
   private static int getDecodedSessionId(String encId)
   {
      String strDecId;
      int    decSessId;
      
      strDecId = encId.substring(0, 32);
     
      decSessId = Integer.parseInt(strDecId);
      
      return decSessId;
   }
   
} // class
