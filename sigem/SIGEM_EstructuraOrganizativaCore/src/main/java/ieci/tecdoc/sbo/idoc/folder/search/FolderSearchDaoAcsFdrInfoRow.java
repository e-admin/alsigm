
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.db.*;

public final class FolderSearchDaoAcsFdrInfoRow implements DbOutputRecord
{
   private  int   m_fdrId;
   private  int   m_acsId;
   private  int   m_acsType;
  
   public FolderSearchDaoAcsFdrInfoRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_fdrId   = stmt.getLongInteger(i++);     
      m_acsId   = stmt.getLongInteger(i++);   
      m_acsType = stmt.getLongInteger(i++);       
   }  
   
   public int getFdrId()
   {
      return m_fdrId;
   }
   
   public int getAcsId()
   {
      return m_acsId;
   }

   public int getAcsType()
   {
      return m_acsType;
   }  
   
} // class
