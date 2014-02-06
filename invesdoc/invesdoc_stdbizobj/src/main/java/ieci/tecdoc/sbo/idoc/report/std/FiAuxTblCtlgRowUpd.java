package ieci.tecdoc.sbo.idoc.report.std;

import java.util.Date;

import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoInputRow;


/**
 * @author egonzalez
 */
public class FiAuxTblCtlgRowUpd implements DaoInputRow
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

  public void setStatementValues(DbInputStatement stmt) throws Exception
  {
    int i = 1;
    
    stmt.setLongInteger (i++, m_id);
    stmt.setShortText(i++, m_name); 
    stmt.setLongInteger(i++, m_userId);  
    stmt.setDateTime(i++, m_timestamp); 
  }
  
}
