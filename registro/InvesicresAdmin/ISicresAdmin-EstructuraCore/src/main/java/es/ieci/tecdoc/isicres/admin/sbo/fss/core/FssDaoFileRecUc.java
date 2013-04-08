package es.ieci.tecdoc.isicres.admin.sbo.fss.core;


import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;

public final class FssDaoFileRecUc implements DbInputRecord
{

   public int  m_stat;
   public Date m_ts;

   public FssDaoFileRecUc()
   {
   }

   public void setStatementValues(DbInputStatement stmt) throws Exception
   {

      int i = 1;

      stmt.setLongInteger(i++, m_stat);
      stmt.setDateTime(i++, m_ts);

   }

} // class
