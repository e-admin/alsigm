package ieci.tecdoc.sbo.idoc.report.std;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputRecordSet;

import java.util.ArrayList;


/**
 * @author egonzalez
 */
public class ReportDaoTokenRows implements DbOutputRecordSet
{
private ArrayList  m_al;
  
  public ReportDaoTokenRows()
  {
     m_al = new ArrayList();       
  }
  
  public int count()
  {
     return m_al.size();
  }
  
  public SearchReportDaoTokenRow getRecord(int index)
  {
     return (SearchReportDaoTokenRow) m_al.get(index);
  }
  
  public DbOutputRecord newRecord()
  {
     
    SearchReportDaoTokenRow rec = new SearchReportDaoTokenRow();
     
     m_al.add(rec);
     
     return rec;
  }
     
     
  public String getColumnNames() throws Exception
  {   
    return new SearchReportDaoTokenRow().getColumnNames();
     
  }
}
