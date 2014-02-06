package ieci.tecdoc.sbo.idoc.report.std;


import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.sbo.idoc.dao.DaoOutputRow;
import ieci.tecdoc.sbo.idoc.dao.DaoRptTbl;


public class SearchReportDaoTokenRow implements DaoOutputRow
{  
  public int            m_id;
  public String         m_name;
  public int            m_reportType;
  
     
  public SearchReportDaoTokenRow()
  {      
  }   
  
  public String getColumnNames() throws Exception
  {
    StringBuffer tbdr;
    
    tbdr = new StringBuffer();

    tbdr.append(DaoRptTbl.getIdColName(false));      
    tbdr.append(",").append(DaoRptTbl.getNameColName(false));
    tbdr.append(",").append(DaoRptTbl.getEditTypeColName(false));
    
    return tbdr.toString();
  }

  public void getStatementValues(DbOutputStatement stmt) throws Exception
  {
    int i = 1;
    
    m_id         = stmt.getLongInteger(i++);
    m_name       = stmt.getShortText(i++); 
    m_reportType = stmt.getLongInteger(i++);
  }

  public String toString()
  {

     StringBuffer buffer = new StringBuffer();
     buffer.append("ReportDaoTokenRow [");
     buffer.append("id = ").append(m_id);
     buffer.append(", name = ").append(m_name);
     buffer.append(", reportType = ").append(m_reportType); 
     buffer.append("]");
     return buffer.toString();

  }
}
