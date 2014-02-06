package ieci.tecdoc.core.db;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbDataSourceMgr
{

   private static DbDataSourceMgr m_instance;
   private DataSource             m_dataSource;
   private Hashtable              m_hashtable = new Hashtable();

   static
   {
      synchronized (DbDataSourceMgr.class)
      {
         if (m_instance == null) m_instance = new DbDataSourceMgr();
      }
   }

   public static DbDataSourceMgr getInstance()
   {
      return m_instance;
   }

   private void setDataSource(String ctxName) throws Exception
   {      
      Context ctx;
      
      try
      {
         ctx = new InitialContext();
         m_dataSource = (DataSource) ctx.lookup(ctxName);
         m_hashtable.put(ctxName, m_dataSource);
      }
      catch (Exception e)
      {
         throw e;
      }
   }

   public DataSource getDataSource(String ctxName) throws Exception
   {
      if (!m_hashtable.containsKey(ctxName)) setDataSource(ctxName);
      
      return (DataSource) m_hashtable.get(ctxName);
   }

   private DbDataSourceMgr()
   {
   }
}