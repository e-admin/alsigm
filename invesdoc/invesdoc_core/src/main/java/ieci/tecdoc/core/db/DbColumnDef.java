
package ieci.tecdoc.core.db;

public final class DbColumnDef
{

   private String  m_name;
   private int     m_dataType;
   private int     m_maxLen;
   private boolean m_nullable;      

   public DbColumnDef(String name, int dataType, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = 0;
      m_nullable = nullable;
   }

   public DbColumnDef(String name, int dataType, int maxLen, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = maxLen;
      m_nullable = nullable;
   }

   public String getName()
   {
      return m_name;
   }
   
   public int getDataType()
   {
      return m_dataType;
   }
   
   public int getMaxLen()
   {
      return m_maxLen;
   }
   
   public boolean isNullable()
   {
      return m_nullable;
   }

} // class
