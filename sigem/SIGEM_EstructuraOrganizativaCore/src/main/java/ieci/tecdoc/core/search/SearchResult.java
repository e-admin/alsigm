
package ieci.tecdoc.core.search;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.dyndao.*;

public final class SearchResult
{
      
   private DynDaoRs  m_rows; 
   private int       m_currentIdx;  
  
   protected SearchResult(DynDaoRs rows)
   {   
      m_rows       = rows; 
      m_currentIdx = 0;  
   }
   
   public int count()
   {
      return m_rows.count();
   } 
   
   public DynDaoRec nextRow() throws Exception
   {
      DynDaoRec rec = null;
      
      if ( (m_currentIdx + 1) > m_rows.count() )
      {
         throw new IeciTdException(SearchError.EC_NO_MORE_ROWS, 
                                   SearchError.EM_NO_MORE_ROWS);
      
      }
      else
      {
         rec = m_rows.getRecord(m_currentIdx);
         
         m_currentIdx = m_currentIdx + 1;
         
         return rec;
      }
   }
   
   public DynDaoRec getRow(int idx) throws Exception
   {
      DynDaoRec rec = null;
      
      if ( (idx + 1) > m_rows.count() || (idx < 0) )
      {
         throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                   SearchError.EM_INVALID_PARAM);
      
      }
      else
      {
         rec = m_rows.getRecord(idx);
         
         return rec;
      }
   }
      
} // class
