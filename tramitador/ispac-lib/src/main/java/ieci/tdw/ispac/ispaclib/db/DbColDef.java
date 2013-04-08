
package ieci.tdw.ispac.ispaclib.db;

public final class DbColDef
{
   private String  m_name;
   private int     m_dataType;
   private int     m_maxLen;
   private boolean m_nullable;     
   private int m_precision;

   public DbColDef(String name, int dataType, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = 0;
      m_nullable = nullable;
   }

   public DbColDef(String name, int dataType, int maxLen, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = maxLen;
      m_nullable = nullable;
   }

   public DbColDef(String name, int dataType, int maxLen, int precision, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = maxLen;
      m_nullable = nullable;
      m_precision = precision;
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
   
   public int getPrecision(){
	   return m_precision;
   }

} 
