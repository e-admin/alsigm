package ieci.tecdoc.core.centera;

import com.filepool.fplibrary.FPLibraryConstants;
import com.filepool.fplibrary.FPPool;

public final class CenteraConnection
{
   private FPPool m_centera;
   
   public CenteraConnection()
   {
      m_centera = null;
   }
   
   public void open(CenteraCnnCfg cfg ) throws Exception
   {
      String iPAddress;
      long    option;
      String user,pwd;
      
      if (cfg.isAvoidCollision())
         option = FPLibraryConstants.FP_OPTION_ENABLE_COLLISION_AVOIDANCE;
      else
         option = FPLibraryConstants.FP_OPTION_DISABLE_COLLISION_AVOIDANCE;
      
      user = cfg.getUser();
      pwd = cfg.getPassword();
      iPAddress = cfg.getServer() +  ":" + new Integer(cfg.getPort()).toString();
      
      if (user.length() > 0 && pwd.length() > 0)
      {
         iPAddress = iPAddress + "?name=" + user + ",secret=" + pwd;
      }
   
      m_centera = new FPPool(iPAddress);
      m_centera.setOption("collisionavoidance" ,(int)option);      
      
   }
   
   public void close() throws Exception
   {
      if (m_centera != null)
      {
         m_centera.Close();
         m_centera = null;
      }
   }
   
   public FPPool getPool() 
   {
      return m_centera;
   }
   
   
}
