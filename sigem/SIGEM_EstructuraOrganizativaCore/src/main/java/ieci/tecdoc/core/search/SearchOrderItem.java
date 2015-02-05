
package ieci.tecdoc.core.search;

public class SearchOrderItem
{
   
   private  String    m_qualifiedColName;
   private  boolean   m_desc;
      
   public SearchOrderItem()
   {
      m_qualifiedColName = null;
      m_desc             = false;              
   }
   
   public SearchOrderItem(String qualifiedColName, boolean desc)
   {
      m_qualifiedColName = qualifiedColName;
      m_desc             = desc;              
   }
   
   public String getSqlOrderItem() throws Exception
   {
      
      String order;
      
      order = m_qualifiedColName;
      
      if (m_desc)
         order = order + " DESC";
      else
         order = order + " ASC";
         
      return order;     
      
   }   
   
} // class
