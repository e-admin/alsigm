package ieci.tecdoc.sbo.idoc.report.std;

import java.util.Date;

import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoOutputRow;

/**
 * @author egonzalez
 */
public class FiAuxTblCtlgTokenRow implements DaoOutputRow
{

  public int            m_id;
  public String         m_name;
  public int            m_userId;
  public Date           m_timestamp;
  
  public String getColumnNames() throws Exception
  {
    StringBuffer tbdr;
    
    tbdr = new StringBuffer();

    tbdr.append(DaoFiauxTblCtlg.getIdColName(false));      
    tbdr.append(",").append(DaoFiauxTblCtlg.getNameColName(false));
    tbdr.append(",").append(DaoFiauxTblCtlg.getUserIdColName(false));
    tbdr.append(",").append(DaoFiauxTblCtlg.getTimestampColName(false));
    
    return tbdr.toString();
  }

  public void getStatementValues(DbOutputStatement stmt) throws Exception
  {
    int i = 1;
    
    m_id         = stmt.getLongInteger(i++);
    m_name       = stmt.getShortText(i++); 
    m_userId     = stmt.getLongInteger(i++);
    m_timestamp  = stmt.getDateTime(i++);
  }

  public String toString()
  {
     StringBuffer buffer = new StringBuffer();
     buffer.append("FiAuxTblCtlgTokenRow [");
     buffer.append("id = ").append(m_id);
     buffer.append(", name = ").append(m_name); 
     buffer.append(", userId = ").append(m_userId);  
     buffer.append(", timestamp = ").append(m_timestamp); 
     buffer.append("]");
     return buffer.toString();

  }
}
