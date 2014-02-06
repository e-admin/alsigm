package ieci.tecdoc.sbo.idoc.report.std;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;


/**
 * @author egonzalez
 */
public class FiAuxTblRow implements DbInputRecord
{
  private int m_fdrId = -1;
  
  public FiAuxTblRow ()
  {
  }
  
  public FiAuxTblRow (int fdrId)
  {
    m_fdrId = fdrId;
  }
  
  public void setStatementValues(DbInputStatement stmt) throws Exception
  {
    int i = 1;
    stmt.setLongInteger(i++, m_fdrId);
  }
  
  
}
