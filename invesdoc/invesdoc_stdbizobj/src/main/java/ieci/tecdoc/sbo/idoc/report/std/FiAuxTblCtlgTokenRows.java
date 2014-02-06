package ieci.tecdoc.sbo.idoc.report.std;

import java.util.ArrayList;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoOutputRows;


/**
 * @author egonzalez
 */
public class FiAuxTblCtlgTokenRows implements DaoOutputRows
{
  private ArrayList  m_al;
  
  public FiAuxTblCtlgTokenRows()
  {
     m_al = new ArrayList();       
  }
  
  public int count()
  {
     return m_al.size();
  }
  
  public FiAuxTblCtlgTokenRow getRecord(int index)
  {
     return (FiAuxTblCtlgTokenRow) m_al.get(index);
  }
  
  public DbOutputRecord newRecord()
  {
     
    FiAuxTblCtlgTokenRow rec = new FiAuxTblCtlgTokenRow();
     
     m_al.add(rec);
     
     return rec;
  }
     
     
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
}
