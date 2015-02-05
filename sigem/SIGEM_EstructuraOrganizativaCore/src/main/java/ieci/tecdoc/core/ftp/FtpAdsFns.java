package ieci.tecdoc.core.ftp;

import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.ServerSocket;
import ieci.tecdoc.core.exception.IeciTdException;

// Ads: Active Data Socket

public final class FtpAdsFns
{

   private FtpAdsFns()
   {
   }

   public static Socket createActiveDataSocket(FtpConnection ftpConn,
         String cmd, String args) throws Exception
   {

      Socket adSocket = null;
      ServerSocket srvSocket = null;

      try
      {

         srvSocket = new ServerSocket(0);

         executePortCommand(ftpConn, FtpCoreFns.getLocalHostAddress(),
               srvSocket.getLocalPort());

         executeDataCommand(ftpConn, cmd, args);

         adSocket = srvSocket.accept();

         srvSocket.close();

         return adSocket;

      }
      catch (Exception e)
      {

         try
         {

            if (adSocket != null) adSocket.close();

            if (srvSocket != null) srvSocket.close();

            throw e;

         }
         catch (Exception e1)
         {
            throw e;
         }

      }

   }

   // **************************************************************************

   private static void executeDataCommand(FtpConnection ftpConn, String cmd,
         String args) throws Exception
   {

      ftpConn.executeCommand(cmd, args);

      if (!ftpConn.isPpResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

   }

   private static void executePortCommand(FtpConnection ftpConn, String host,
         int port) throws Exception
   {

      sendPortCommand(ftpConn.m_req, ftpConn.m_reqText, host, port);

      ftpConn.getResponse();

      if (!ftpConn.isPcResponse()) { throw new IeciTdException(
            FtpError.EC_NATIVE_ERR, ftpConn.m_respText.toString()); }

   }

   private static void sendPortCommand(OutputStreamWriter req,
         StringBuffer reqText, String host, int port) throws Exception
   {

      String args;

      args = getPortCommandArgs(reqText, host, port);

      FtpCoreFns.sendCommand(req, reqText, FtpCoreDefs.CMD_PORT, args);

   }

   private static String getPortCommandArgs(StringBuffer buf, String host,
         int port)
   {

      buf.setLength(0);

      buf.append(host.replace('.', ','));
      buf.append(',');

      buf.append(port >>> 8);
      buf.append(',');
      buf.append(port & 0xff);

      return buf.toString();

   }

} // Class
