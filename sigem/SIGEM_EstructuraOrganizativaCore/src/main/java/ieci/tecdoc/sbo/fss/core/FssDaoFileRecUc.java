package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.sgm.base.dbex.DbInputRecord;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;

import java.util.Date;

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
