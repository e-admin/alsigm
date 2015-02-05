package ieci.tecdoc.core.centera;

public final class CenteraCnnCfg
{
   private String  m_server;
	private int     m_port;
	private String  m_user;
	private String  m_pwd;
	private boolean m_avoidCollision;

	//~ Constructors -----------------------------------------------------------

	public CenteraCnnCfg()
	{
		m_avoidCollision  = false;
	}
	
	public String getServer()
	{
	   return m_server;
	}
	
	public void setServer(String server)
	{
	   m_server = server; 
	}
	
	public int getPort()
	{
	   return m_port;
	}
	
	public void setPort(int port)
	{
	   m_port = port;
	}
	
	public String getUser()
	{
	   return m_user;
	}
	
	public void setUser(String user)
	{
	   m_user = user;
	}
	
	public String getPassword()
	{
	   return m_pwd;
	}
	
	public void setPassword(String pwd)
	{
	   m_pwd = pwd;
	}
	
	public boolean isAvoidCollision()
	{
	   return m_avoidCollision;
	}
	
	public void setAvoidCollision(boolean avoidCollision)
	{
	   m_avoidCollision = avoidCollision;
	}
}
