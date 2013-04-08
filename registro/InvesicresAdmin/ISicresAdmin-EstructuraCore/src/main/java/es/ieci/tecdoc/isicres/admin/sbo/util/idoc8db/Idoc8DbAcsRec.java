package es.ieci.tecdoc.isicres.admin.sbo.util.idoc8db;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;


public final class Idoc8DbAcsRec implements DbOutputRecord
{
   public int m_acsType;
   public int m_acsId;
   
   public Idoc8DbAcsRec()
   {
     
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_acsType  = stmt.getLongInteger(i++);
      m_acsId    = stmt.getLongInteger(i++);       
      
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("Idoc8DbAcsRec [");
      buffer.append("acsType = ").append(m_acsType);
      buffer.append(", acsId = ").append(m_acsId);      
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
