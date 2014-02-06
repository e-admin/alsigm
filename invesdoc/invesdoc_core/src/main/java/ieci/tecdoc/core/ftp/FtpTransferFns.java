package ieci.tecdoc.core.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import ieci.tecdoc.core.exception.IeciTdException;

public final class FtpTransferFns
{

   private FtpTransferFns()
   {
   }

   // **************************************************************************

   public static void storeFile(FtpConnectionInfo ftpConnInfo, String dstLoc,
         byte[] srcData) throws Exception
   {

      FtpConnection ftpConn = null;

      try
      {

         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

         storeFile(ftpConn, dstLoc, srcData);

         ftpConn.close();

      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
      }

   }

   public static byte[] retrieveFile(FtpConnectionInfo ftpConnInfo,
         String srcLoc) throws Exception
   {

      byte[] dstData = null;
      FtpConnection ftpConn = null;

      try
      {

         ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

         dstData = retrieveFile(ftpConn, srcLoc);

         ftpConn.close();

         return dstData;

      }
      catch (Exception e)
      {
         FtpConnection.ensureClose(ftpConn, e);
         return dstData;
      }

   }

   // **************************************************************************

   public static void storeFile(FtpConnection ftpConn, String dstLoc,
         byte[] srcData) throws Exception
   {

      ByteArrayInputStream in = null;

      try
      {

         in = new ByteArrayInputStream(srcData);

         storeFile(ftpConn, dstLoc, in);

         in.close();

      }
      catch (Exception e)
      {

         try
         {

            if (in != null) in.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   public static byte[] retrieveFile(FtpConnection ftpConn, String srcLoc)
         throws Exception
   {

      byte[] dstData;
      ByteArrayOutputStream out = null;

      try
      {

         out = new ByteArrayOutputStream();

         retrieveFile(ftpConn, srcLoc, out);

         dstData = out.toByteArray();

         out.close();

         return dstData;

      }
      catch (Exception e)
      {

         try
         {

            if (out != null) out.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   // **************************************************************************

   public static void storeFile(FtpConnection ftpConn, String dstLoc,
         InputStream srcData) throws Exception
   {

      Socket dSocket = null;

      setBinaryFileType(ftpConn);

      try
      {

         dSocket = createDataSocket(ftpConn, FtpCoreDefs.CMD_STOR, dstLoc);

         storeData(srcData, dSocket);

         dSocket.close();
         dSocket = null;

         completeDataCommand(ftpConn);

      }
      catch (Exception e)
      {

         try
         {

            if (dSocket != null) dSocket.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   public static void retrieveFile(FtpConnection ftpConn, String srcLoc,
         OutputStream dstData) throws Exception
   {

      Socket dSocket = null;

      setBinaryFileType(ftpConn);

      try
      {

         dSocket = createDataSocket(ftpConn, FtpCoreDefs.CMD_RETR, srcLoc);

         retrieveData(dstData, dSocket);

         dSocket.close();
         dSocket = null;

         completeDataCommand(ftpConn);

      }
      catch (Exception e)
      {

         try
         {

            if (dSocket != null) dSocket.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   // **************************************************************************

   // Por si hubiera que permitir activo y pasivo (configurado en ftpConn)

   private static Socket createDataSocket(FtpConnection ftpConn, String cmd,
         String args) throws Exception
   {
      return FtpAdsFns.createActiveDataSocket(ftpConn, cmd, args);
   }

   private static void storeData(InputStream in, Socket socket)
         throws Exception
   {

      OutputStream out = null;

      try
      {

         out = new BufferedOutputStream(socket.getOutputStream());

         transferData(in, out);

         out.close();

      }
      catch (Exception e)
      {

         try
         {

            if (out != null) out.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   private static void retrieveData(OutputStream out, Socket socket)
         throws Exception
   {

      InputStream in = null;

      try
      {

         in = new BufferedInputStream(socket.getInputStream());

         transferData(in, out);

         in.close();

      }
      catch (Exception e)
      {

         try
         {

            if (in != null) in.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   private static void transferData(InputStream in, OutputStream out)
         throws Exception
   {

      byte buf[];
      int nbr;

      buf = new byte[FtpCoreDefs.BLOCK_SIZE];

      while ((nbr = in.read(buf)) != -1)
      {
         out.write(buf, 0, nbr);
      }

   }

   private static void completeDataCommand(FtpConnection ftpConn)
         throws Exception
   {

      ftpConn.getResponse();

      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

   }

   private static void setBinaryFileType(FtpConnection ftpConn)
         throws Exception
   {

      ftpConn
            .executeCommand(FtpCoreDefs.CMD_TYPE, FtpCoreDefs.BINARY_FILE_TYPE);

      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

   }

   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static void retrieveFile(FtpConnectionInfo ftpConnInfo,
           String srcLoc, File destFile) throws Exception
     {

        byte[] dstData = null;
        FtpConnection ftpConn = null;

        try
        {

           ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

           retrieveFile(ftpConn, srcLoc, destFile);

           ftpConn.close();

        }
        catch (Exception e)
        {
           FtpConnection.ensureClose(ftpConn, e);
        }
     }
   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static void retrieveFile(FtpConnection ftpConn, String srcLoc, File destFile)
   throws Exception
   {
        FileOutputStream out = null;
        
        try
        {
           out = new FileOutputStream(destFile);
        
           retrieveFile(ftpConn, srcLoc, out);
        
           out.close();
        
        } catch (Exception e) {
           try {
              if (out != null) out.close();
           } catch (Exception e1){ // Se ignora
           }
           throw e;
        }
    
   }

   // PARCHE P9.0.2. Permite guardar el contenido del documento desde un File.
   public static void storeFile(FtpConnectionInfo ftpConnInfo, String dstLoc, InputStream inputStream) throws Exception
     {

        FtpConnection ftpConn = null;

        try
        {

           ftpConn = FtpConnection.createFtpConnection(ftpConnInfo);

           storeFile(ftpConn, dstLoc, inputStream);

           ftpConn.close();

        }
        catch (Exception e)
        {
           FtpConnection.ensureClose(ftpConn, e);
        }

     }
} // class
