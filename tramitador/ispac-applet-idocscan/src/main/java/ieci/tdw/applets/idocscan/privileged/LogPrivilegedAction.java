package ieci.tdw.applets.idocscan.privileged;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivilegedAction;

public class LogPrivilegedAction implements PrivilegedAction
{

   
   
   private static final String   LOG_FILE_DIR   =  System.getProperty("java.io.tmpdir");
   private static final String   LOG_FILE_NAME  =  "InvesdocAppletLog.txt";
   private static final String   LOG_FILE_PATH  =  LOG_FILE_DIR + LOG_FILE_NAME;
   private static       File     logFile        =  null;
   
   String   strDebugMessage   =  null;
   String   from              =  null;

   public LogPrivilegedAction(String strDebugMessage, String from)
   {
      this.strDebugMessage  =  strDebugMessage;
      this.from             =  from;
   }

   public Object run()
   {
      if(logFile == null)
      {
         if(!(new File(LOG_FILE_PATH)).exists())
         {    
            if(!(new File(LOG_FILE_DIR)).exists())
            {  
               if((new File(LOG_FILE_DIR)).mkdirs())
               {
                  File file = new File(LOG_FILE_PATH);
                  try
                  {
                     if(file.createNewFile())
                     {
                        logFile  =  file;
                     }
                  }
                  catch (IOException e)
                  {
                     e.printStackTrace();
                  }
               }

            }
            else
            {
               File file = new File(LOG_FILE_PATH);
               try
               {
                  if(file.createNewFile())
                  {
                     logFile  =  file;
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         else
         {
            logFile = new File(LOG_FILE_PATH);
         }

      }
      
      if(logFile != null && logFile.exists())
      {
         try
         {
            if(logFile.canWrite())
            {
               FileWriter fw =  new FileWriter(logFile.toString(), true);
               fw.write(System.getProperty("line.separator"));
               fw.write("message[ debugMessage: "+ strDebugMessage + " - from: " +from);
               fw.close();
            }
            else
            {
               System.out.println("Cannot write to logfile " + logFile.toString());
            }
         }
         catch (IOException e)
         {
            System.out.println("IOException " + e.getMessage());
            e.printStackTrace();
         }
         return null;
      }
       return null;
   }
}