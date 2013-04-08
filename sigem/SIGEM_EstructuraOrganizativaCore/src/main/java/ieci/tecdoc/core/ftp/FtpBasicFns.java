package ieci.tecdoc.core.ftp;

import java.io.File;
import ieci.tecdoc.core.exception.IeciTdException;

public final class FtpBasicFns
{

   private FtpBasicFns()
   {
   }

   // **************************************************************************

   public static void createDirectory(FtpConnectionInfo ftpConnInfo, String loc)
      throws Exception
   {

      FtpConnection ftpConn = null;
      
      try
      {

         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);
         createDirectory(ftpConn, loc);

         ftpConn.close();

      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
      }

   }

   public static void createDirectoryX(FtpConnectionInfo ftpConnInfo,
         String loc, char sep)
      throws Exception
   {

      FtpConnection ftpConn = null;

      try
      {

         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

         createDirectoryX(ftpConn, loc, sep);

         ftpConn.close();

      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
      }

   }

   // **************************************************************************

   public static void createDirectory(FtpConnection ftpConn, String loc)
      throws Exception
   {

      boolean created;
      String respText = null;

      created = createDirectoryS(ftpConn, loc);

      if (!created)
      {
         respText = ftpConn.m_respText.toString();
         throw new IeciTdException(FtpError.EC_NATIVE_ERR, respText);
      }

   }

   public static void createDirectoryX(FtpConnection ftpConn, String loc,
         char sep)
      throws Exception
   {

      boolean created;
      String respText = null;

      created = createDirectoryS(ftpConn, loc);

      if (!created)
      {
         respText = ftpConn.m_respText.toString();
         created = createDirectoryM(ftpConn, loc, sep);
      }

      if (!created)
            throw new IeciTdException(FtpError.EC_NATIVE_ERR, respText);

   }

   // **************************************************************************

   private static boolean createDirectoryS(FtpConnection ftpConn, String loc)
      throws Exception
   {

      boolean created = true;

      ftpConn.executeCommand(FtpCoreDefs.CMD_MKD, loc);

      if (!ftpConn.isPcResponse()) created = false;

      return created;

   }

   private static boolean createDirectoryM(FtpConnection ftpConn, String loc,
                                           char sep)
      throws Exception
   {

      boolean created;
      File fileDir;
      String parentDir;

      fileDir = new File(loc);

      parentDir = fileDir.getParent();      

      if (parentDir != null)
      {
         parentDir = parentDir.replace(File.separatorChar, sep);
         
         created = createDirectoryS(ftpConn, parentDir);

         if (!created)
         {
            created = createDirectoryM(ftpConn, parentDir, sep);
            if (!created) return false;
         }

      }
      else
         created = false;
      

      return created;

   }

   // **************************************************************************

   public static void deleteFile(FtpConnectionInfo ftpConnInfo, String loc)
      throws Exception
   {

      FtpConnection ftpConn = null;

      try
      {

         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

         deleteFile(ftpConn, loc);

         ftpConn.close();

      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
      }

   }

   // **************************************************************************

   public static void deleteFile(FtpConnection ftpConn, String loc)
      throws Exception
   {

      ftpConn.executeCommand(FtpCoreDefs.CMD_DELE, loc);

      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

   }

   // **************************************************************************

   public static String getFileSize(FtpConnectionInfo ftpConnInfo, String loc)
      throws Exception
   {
      FtpConnection ftpConn = null;
      String size = null;

      try
      {
         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

         size = getFileSize(ftpConn, loc);

         ftpConn.close();
      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
      }

      return (size);
   }

   public static String getFileSize(FtpConnection ftpConn, String loc)
      throws Exception
   {

      String size;
      String response;

      ftpConn.executeCommand(FtpCoreDefs.CMD_SIZE, loc);

      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

      response = ftpConn.getResponseText();

      size = response.substring(4, response.length());

      return (size);

   }
   
   public static String getCurrentDirectory(FtpConnection ftpConn)
      throws Exception
   {
      
      String directory;
      String response;
      
      ftpConn.executeCommand(FtpCoreDefs.CMD_PWD);
      
      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }
      
      response = ftpConn.getResponseText();
      
      //response = '257"/currentdir" is current directory'
      directory = response.substring(6, response.lastIndexOf("\""));
      
      
      return (directory);
      
   }  
   
} // class
