package ieci.tecdoc.sbo.idoc.report.std;

import java.util.Date;

import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.sbo.idoc.dao.DaoOutputRow;
import ieci.tecdoc.sbo.idoc.dao.DaoRptTbl;


public class ReportDaoTokenRow implements DaoOutputRow
{  
  public int            m_id;
  public String         m_name;
  public int 						m_editType;
  public int            m_archId;
  public String         m_version;
  public int            m_dataLength;
  public int            m_reportType;
  public byte[]         m_data;
  public String         m_remarks;
  public int            m_accessType;
  public int            m_acsId;
  public int            m_crtId;
  public Date           m_crtnDate;
  public int            m_updId;
  public Date           m_updDate;
     
  public ReportDaoTokenRow()
  {      
  }   
  
  public String getColumnNames() throws Exception
  {
    StringBuffer tbdr;
    
    tbdr = new StringBuffer();

    tbdr.append(DaoRptTbl.getIdColName(false));      
    tbdr.append(",").append(DaoRptTbl.getNameColName(false));
    tbdr.append(",").append(DaoRptTbl.getEditTypeColName(false));
    tbdr.append(",").append(DaoRptTbl.getArchIdColName(false));
    tbdr.append(",").append(DaoRptTbl.getDataColName(false));
    tbdr.append(",").append(DaoRptTbl.getRemarksColName(false));
    tbdr.append(",").append(DaoRptTbl.getAccessTypeColName(false));
    tbdr.append(",").append(DaoRptTbl.getAcsIdColName(false));
    tbdr.append(",").append(DaoRptTbl.getCrtIdColName(false));
    tbdr.append(",").append(DaoRptTbl.getCrtnDateColName(false));
    tbdr.append(",").append(DaoRptTbl.getUpdrIdColName(false));
    tbdr.append(",").append(DaoRptTbl.getUpdDateColName(false));
    
    return tbdr.toString();
  }

  public void getStatementValues(DbOutputStatement stmt) throws Exception
  {
    int i = 1;
   
    m_id         = stmt.getLongInteger(i++);
    m_name       = stmt.getShortText(i++);
    m_editType   = stmt.getLongInteger(i++);
    m_archId     = stmt.getLongInteger(i++); 
    m_data       = stmt.getBlob (i++);
    m_remarks    = stmt.getShortText(i++);
    m_accessType = stmt.getLongInteger(i++);
    m_acsId      = stmt.getLongInteger(i++);
    m_crtId      = stmt.getLongInteger(i++); 
    m_crtnDate   = stmt.getDateTime(i++);
    m_updId      = stmt.getLongInteger(i++); 
    m_updDate    = stmt.getDateTime(i++);     
  }

  public String toString()
  {

     StringBuffer buffer = new StringBuffer();
     buffer.append("ReportDaoTokenRow [");
     buffer.append("id = ").append(m_id);
     buffer.append(", name = ").append(m_name);
     buffer.append(", editType = ").append(m_editType); 
     buffer.append(", archId = ").append(m_archId);  
     buffer.append(", data = ").append(m_data); 
     buffer.append(", remarks = ").append(m_remarks); 
     buffer.append(", accessType = ").append(m_accessType);  
     buffer.append(", acsId = ").append(m_acsId); 
     buffer.append(", crtId = ").append(m_crtId);
     buffer.append(", crtnDate = ").append(m_crtnDate);
     buffer.append(", updId = ").append(m_updId);
     buffer.append(", m_updDate = ").append(m_updDate);
     buffer.append("]");
     return buffer.toString();

  }
}
