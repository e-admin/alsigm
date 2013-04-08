
package ieci.tdw.ispac.ispaclib.db;

public final class DbIndexDefinition
{
   
   private String  m_name;
   private String  m_colNames;
   private boolean m_unique; 
   
   public DbIndexDefinition(String name, String colNames, boolean unique)
   {
      m_name      = name;
      m_colNames  = colNames;
      m_unique    = unique;
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
   

} 