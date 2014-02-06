
package ieci.tecdoc.core.db;

public final class DbIndexDef
{
   
   private String  m_name;
   private String  m_colNames;
   private boolean m_unique; 
   private boolean m_clustered;
   
   public DbIndexDef(String name, String colNames, boolean unique, 
                     boolean clustered)
   {
      m_name      = name;
      m_colNames  = colNames;
      m_unique    = unique;
      m_clustered = clustered;
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public String getColumnNames()
   {
      return m_colNames;
   }      
   
   public boolean isUnique()
   {
      return m_unique;
   }
   
   public boolean isClustered()
   {
      return m_clustered;
   }
   
} // class
