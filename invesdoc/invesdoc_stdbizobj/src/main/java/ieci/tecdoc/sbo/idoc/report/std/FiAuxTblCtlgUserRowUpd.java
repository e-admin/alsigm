package ieci.tecdoc.sbo.idoc.report.std;

import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoInputRow;


/**
 * @author egonzalez
 */
public class FiAuxTblCtlgUserRowUpd implements DaoInputRow
{
  public int            m_userId;
  
  public FiAuxTblCtlgUserRowUpd (int userId)
  {
    m_userId = userId;
  }
  
  public String getColumnNames() throws Exception
  {
    return DaoFiauxTblCtlg.getUserIdColName(false);
  }

  public void setStatementValues(DbInputStatement stmt) throws Exception
  {
    int i=1;
    stmt.setLongInteger(i++, m_userId);  
  }
}
