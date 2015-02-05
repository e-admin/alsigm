package ieci.tecdoc.core.ftp;

public final class FtpConnectionInfo
{

   public String m_host;
   public int    m_port;
   public String m_user;
   public String m_pwd;

   public FtpConnectionInfo()
   {
      m_host = null;
      m_port = 21;
      m_user = null;
      m_pwd  = null;
   }

   public FtpConnectionInfo(String host, int port, String user, String pwd)
   {
      m_host = host;
      m_port = port;
      m_user = user;
      m_pwd  = pwd;
   }

   public String toString()
   {
      StringBuffer stb = new StringBuffer("FtpConnectionInfo Object [ m_host: ");
      stb.append(m_host);
      stb.append(", m_port: ");
      stb.append(m_port);
      stb.append(", m_user: ");
      stb.append(m_user);
      stb.append(", m_pwd: ");
      stb.append(m_pwd);
      stb.append(" ]");
      return stb.toString();
   }
} // class
