
package ieci.tecdoc.sbo.idoc.archive.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class ArchiveDaoArchHdrTokenRow implements DaoOutputRow
{
   public int    m_id;
   public String m_name;
   public String m_tblPrefix;
   public int    m_flags;
   public int    m_accessType;
   public int    m_acsId;
      
   public ArchiveDaoArchHdrTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id         = stmt.getLongInteger(i++);
      m_name       = stmt.getShortText(i++); 
      m_tblPrefix  = stmt.getShortText(i++); 
      m_flags      = stmt.getLongInteger(i++);
      m_accessType = stmt.getLongInteger(i++);
      m_acsId      = stmt.getLongInteger(i++);      
      
   }
   
   public String getColumnNames() throws Exception
   {   
      
      StringBuffer tbdr;
      
      tbdr = new StringBuffer();

      tbdr.append(DaoArchHdrTbl.getIdColName(false));      
      tbdr.append(",").append(DaoArchHdrTbl.getNameColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getTblPrefixColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getFlagsColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getAccessTypeColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getAcsIdColName(false));

      return tbdr.toString();
      
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("ArchiveDaoArchHdrTokenRow [");
      buffer.append("id = ").append(m_id);
      buffer.append(", name = ").append(m_name);  
      buffer.append(", tblPrefix = ").append(m_tblPrefix); 
      buffer.append(", flags = ").append(m_flags); 
      buffer.append(", accessType = ").append(m_accessType);  
      buffer.append(", acsId = ").append(m_acsId);      
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
