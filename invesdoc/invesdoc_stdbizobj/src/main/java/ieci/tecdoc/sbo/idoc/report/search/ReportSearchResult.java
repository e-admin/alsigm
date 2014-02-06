package ieci.tecdoc.sbo.idoc.report.search;

import java.io.Serializable;


/**
 * @author egonzalez
 */
public class ReportSearchResult implements Serializable
{

  private static final long serialVersionUID = 1L;
  private String m_name = null;
  private int m_id = -1;
  private int m_reportType = -1;
  
  public ReportSearchResult(int id, String name, int reportType)
  {
    this.m_id = id;
    this.m_name = name;
    this.m_reportType = reportType;
  }

  public int getId()
  {
    return m_id;
  }
  
  public void setId(int id)
  {
    this.m_id = id;
  }
  
  public String getName()
  {
    return m_name;
  }
  
  public void setName(String name)
  {
    this.m_name = name;
  }
  
  public int getReportType()
  {
    return m_reportType;
  }
  
  public void setReportType(int reportType)
  {
    this.m_reportType = reportType;
  }
  
  public boolean equals (ReportSearchResult other)
  {
    return (getId() == other.m_id && getReportType() == other.m_reportType && getName().equals(other.getName()))? true:false;
  }
  
  public String toString ()
  {
    StringBuffer buffer = new StringBuffer ();
    buffer.append(" (ReportSearchResult: id");
    buffer.append(" = ").append(getId()).append("; name = ");
    buffer.append(getName());
    buffer.append("; reportType = ").append(getReportType());
    buffer.append(") ");
    return buffer.toString();
  }
}
