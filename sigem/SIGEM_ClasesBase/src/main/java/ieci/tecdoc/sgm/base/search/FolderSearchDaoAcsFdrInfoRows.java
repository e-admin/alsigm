
package ieci.tecdoc.sgm.base.search;

import ieci.tecdoc.sgm.base.dbex.*;
import java.util.ArrayList;

public final class FolderSearchDaoAcsFdrInfoRows implements DbOutputRecordSet
{
   private ArrayList  m_al;
   
   public FolderSearchDaoAcsFdrInfoRows()
   {    
      m_al = new ArrayList();  
   }
   
   public int count()
   {
      return m_al.size();
   }
    
   public FolderSearchDaoAcsFdrInfoRow getRecord(int index)
   {
      return (FolderSearchDaoAcsFdrInfoRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      FolderSearchDaoAcsFdrInfoRow rec = new FolderSearchDaoAcsFdrInfoRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
} // class
