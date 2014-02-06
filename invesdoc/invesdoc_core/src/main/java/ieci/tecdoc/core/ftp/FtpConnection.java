package ieci.tecdoc.core.ftp;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import ieci.tecdoc.core.exception.IeciTdException;

public final class FtpConnection
{

   public Socket             m_socket;
   public OutputStreamWriter m_req;
   public BufferedReader     m_resp;
   public StringBuffer       m_reqText;
   public StringBuffer       m_respText;
   public int                m_respCode;
   public boolean            m_loggedIn;

   public FtpConnection()
   {
      m_socket = null;
      m_req = null;
      m_resp = null;
      m_reqText = new StringBuffer();
      m_respText = new StringBuffer();
      m_respCode = 0;
      m_loggedIn = false;
   }

   public void open(String host, int port, String user, String pwd)
      throws Exception
   {

      m_socket = new Socket(host, port);

      createStreams();

      getResponse();

      if (!isPcResponse()) { throw new IeciTdException(FtpError.EC_NATIVE_ERR,
            m_respText.toString()); }

      login(user, pwd);

   }

   public void close()
      throws Exception
   {

      logout();

      releaseStreams();

      if (m_socket != null)
      {
         m_socket.close();
         m_socket = null;
      }

   }

   public static FtpConnection createFtpConnection(FtpConnectionInfo info)
      throws Exception
   {

      FtpConnection conn = null;

      conn = new FtpConnection();
      conn.open(info.m_host, info.m_port, info.m_user, info.m_pwd);

      return conn;

   }

   public static void ensureClose(FtpConnection conn, Exception exc)
      throws Exception
   {

      try
      {
         if (conn != null) conn.close();
         throw exc;
      }
      catch (Exception e)
      {
         throw exc;
      }

   }

   public void executeCommand(String cmd)
      throws Exception
   {
      sendCommand(cmd);
      getResponse();
   }

   public void executeCommand(String cmd, String arg)
      throws Exception
   {
      sendCommand(cmd, arg);
      getResponse();
   }

   public void sendCommand(String cmd)
      throws Exception
   {
      FtpCoreFns.sendCommand(m_req, m_reqText, cmd);
   }

   public void sendCommand(String cmd, String arg)
      throws Exception
   {
      FtpCoreFns.sendCommand(m_req, m_reqText, cmd, arg);
   }

   public void getResponse()
      throws Exception
   {
      m_respCode = FtpCoreFns.getResponse(m_resp, m_respText);
   }

   public int getResponseCode()
   {
      return m_respCode;
   }

   public String getResponseText()
   {
      return m_respText.toString();
   }

   public boolean isPpResponse()
   {
      return FtpCoreFns.isPpResponse(m_respCode);
   }

   public boolean isPcResponse()
   {
      return FtpCoreFns.isPcResponse(m_respCode);
   }

   public boolean isPiResponse()
   {
      return FtpCoreFns.isPiResponse(m_respCode);
   }

   // **************************************************************************

   private void createStreams()
      throws Exception
   {

      OutputStream os;
      InputStream is;
      InputStreamReader isr;

      os = m_socket.getOutputStream();
      m_req = new OutputStreamWriter(os);

      is = m_socket.getInputStream();
      isr = new InputStreamReader(is);
      m_resp = new BufferedReader(isr);

   }

   private void releaseStreams()
      throws Exception
   {

      if (m_resp != null)
      {
         m_resp.close();
         m_resp = null;
      }

      if (m_req != null)
      {
         m_req.close();
         m_req = null;
      }

   }

   private void login(String user, String pwd)
      throws Exception
   {

      executeCommand(FtpCoreDefs.CMD_USER, user);

      if (!isPcResponse())
      {

         if (!isPiResponse()) { throw new IeciTdException(
               FtpError.EC_NATIVE_ERR, m_respText.toString()); }

         executeCommand(FtpCoreDefs.CMD_PASS, pwd);

         if (!isPcResponse()) { throw new IeciTdException(
               FtpError.EC_NATIVE_ERR, m_respText.toString()); }

      }

      m_loggedIn = true;

   }

   private void logout()
      throws Exception
   {

      if (!m_loggedIn) return;

      executeCommand(FtpCoreDefs.CMD_QUIT);

      if (!isPcResponse()) { throw new IeciTdException(FtpError.EC_NATIVE_ERR,
            m_respText.toString()); }

      m_loggedIn = false;

   }

} // class
