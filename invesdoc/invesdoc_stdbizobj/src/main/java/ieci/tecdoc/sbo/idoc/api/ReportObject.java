package ieci.tecdoc.sbo.idoc.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @author egonzalez
 */
public class ReportObject implements Serializable
{

  private static final long serialVersionUID = 1L;

  private ReportToken token = null;
  
  protected ReportObject (ReportToken token)
  {
    this.token = token;    
  }
  
  public int getAccessType()
  {
    return token.getAccessType();
  }
  
  public void setAccessType(int accessType)
  {
    token.setAccessType(accessType);
  }
  
  public int getAcsId()
  {
    return token.getAcsId();
  }
  
  public void setAcsId(int acsId)
  {
    token.setAcsId(acsId);
  }
  
  public int getArchId()
  {
    return token.getArchId();
  }
  
  public void setArchId(int archId)
  {
    token.setArchId(archId);
  }
  
  public int getCrtrId()
  {
    return token.getCrtrId();
  }
  
  public void setCrtrId(int crtrId)
  {
    token.setCrtrId(crtrId);
  }
  
  public Date getCrtrDate()
  {
    return token.getCrtrDate();
  }
  
  public void setCrtrDate(Date crtrTime)
  {
    token.setCrtrDate(crtrTime);
  }
  
  public byte[] getData()
  {
    return token.getData();
  }
  
  public void setData(byte[] data)
  {
    token.setData(data);
  }
  
  public int getId()
  {
    return token.getId();
  }
  
  public void setId(int id)
  {
    token.setId(id);
  }
  
  public String getName()
  {
    return token.getName();
  }
  
  public void setName(String name)
  {
    token.setName(name);
  }
  
  public String getRemarks()
  {
    return token.getRemarks();
  }
  
  public void setRemarks(String remarks)
  {
    token.setRemarks(remarks);
  }
  
  public Date getUpdDate()
  {
    return token.getUpdDate();
  }
  
  public void setUpdDate(Date updDate)
  {
    token.setUpdDate(updDate);
  }
  
  public int getUpdrId()
  {
    return token.getUpdrId();
  }
  
  public void setUpdrId(int updrId)
  {
    token.setUpdrId(updrId);
  }
  
  public int getDataLength()
  {
    return token.getDataLength();
  }

  public int getReportType()
  {
    return token.getReportType();
  }

  public String getVersion()
  {
    return token.getVersion();
  }

  public void setDataLength(int dataLength)
  {
    token.setDataLength(dataLength);
  }

  public void setReportType(int reportType)
  {
    token.setReportType(reportType);
  }

  public void setVersion(String version)
  {
    token.setVersion(version);
  }

  public int getEditType()
  {
     return token.getEditType ();
  }

  public void setEditType(int editType)
  {
     token.setEditType(editType);
  }
  
  public String toString ()
  {
    StringBuffer buffer = new StringBuffer ();
    buffer.append("ReportToken[");
    buffer.append ("Id: " + getId() + ", ");
    buffer.append ("Name: " + getName() + ", ");
    buffer.append ("EditType: " + getEditType() + ", ");
    buffer.append ("ArchId: " + getArchId()+ ", ");
    buffer.append ("Version: " + getVersion()+ ", ");
    buffer.append ("ReportType: " + getReportType()+ ", ");
    buffer.append ("DataLength: " + getDataLength()+ ", ");
    buffer.append ("Remarks: " + getRemarks() + ", ");
    buffer.append ("AccessType: " + getAccessType() + ", ");
    buffer.append ("AcsId: " + getAcsId() + ", ");
    buffer.append ("CrtId: " + getCrtrId() + ", ");
    buffer.append ("CrtDate: " + getCrtrDate() + ", ");
    buffer.append ("UpdrId: " + getUpdrId() + ", ");
    buffer.append ("UpdDate: " + getUpdDate() + ", ");
    buffer.append("]");
    return buffer.toString();
  }  
}
