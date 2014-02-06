package ieci.tecdoc.sbo.idoc.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @author egonzalez
 */

//
// TABLA IDOCRRRPT
//
// #  Nombre           Tipo        Longitud                   Nulo
// --  ---------------  ----------  -------------------------  -----
//
//  1  ID               LONG_INT                                FALSE
//  2  NAME             SHORT_STR   IDOC_RPT_NAME_LEN (32)      FALSE
//     EDITTYPE         LONG_INT                                FALSE
//  3  ARCHID           LONG_INT                                FALSE
//  4  DATA             LONG_BIN    IDOC_RPT_DATA_LEN_MAX (300K)FALSE
//  5  REMARKS          SHORT_STR   IDOC_REM_LEN (254)          TRUE
//  6  ACCESSTYPE       LONG_INT                                FALSE
//  7  ACSID            LONG_INT                                FALSE
//  8  CRTRID           LONG_INT                                FALSE
//  9  CRTNDATE         DATE_TIME                               FALSE
// 10  UPDRID           LONG_INT                                TRUE
// 11  UPDDATE          DATE_TIME                               TRUE

public class ReportToken implements Serializable
{
  
  private static final long serialVersionUID = 1L;
  
  private int id = -1;
  private String name = null;
  private int editType = -1;
  private int archId = -1;
  private String version = null;
  private int reportType = -1;
  private int dataLength = -1;
  private byte[] data = null;
  private String remarks = null;
  private int accessType = -1;
  private int acsId = -1;
  private int crtrId = -1;
  private Date crtrDate = null;
  private int updrId = -1;
  private Date updDate = null;
  
  public ReportToken ()
  {
    
  }
  
  public ReportToken (int id, String name, int editType, int archId, byte[] data, String remarks, int accessType,
        int acsId, int crtrId, Date crtrDate, int updrId, Date updDate)
  {
    this.id = id;
    this.name = name;
    this.editType = editType;
    this.archId = archId;
    this.data = data;
    this.remarks = remarks;
    this.accessType = accessType;
    this.acsId = acsId;
    this.crtrId = crtrId;
    this.crtrDate = crtrDate;
    this.updrId = updrId;
    this.updDate = updDate;
  }
    
  public int getAccessType()
  {
    return accessType;
  }
  
  public void setAccessType(int accessType)
  {
    this.accessType = accessType;
  }
  
  public int getAcsId()
  {
    return acsId;
  }
  
  public void setAcsId(int acsId)
  {
    this.acsId = acsId;
  }
  
  public int getArchId()
  {
    return archId;
  }
  
  public void setArchId(int archId)
  {
    this.archId = archId;
  }
  
  public int getCrtrId()
  {
    return crtrId;
  }
  
  public void setCrtrId(int crtrId)
  {
    this.crtrId = crtrId;
  }
  
  public Date getCrtrDate()
  {
    return crtrDate;
  }
  
  public void setCrtrDate(Date crtrTime)
  {
    this.crtrDate = crtrTime;
  }
  
  public byte[] getData()
  {
    return data;
  }
  
  public void setData(byte[] data)
  {
    this.data = data;
  }
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getRemarks()
  {
    return remarks;
  }
  
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  
  public Date getUpdDate()
  {
    return updDate;
  }
  
  public void setUpdDate(Date updDate)
  {
    this.updDate = updDate;
  }
  
  public int getUpdrId()
  {
    return updrId;
  }
  
  public void setUpdrId(int updrId)
  {
    this.updrId = updrId;
  }
  
  
  public int getDataLength()
  {
    return dataLength;
  }

  
  public void setDataLength(int dataLength)
  {
    this.dataLength = dataLength;
  }

  
  public int getReportType()
  {
    return reportType;
  }

  
  public void setReportType(int reportType)
  {
    this.reportType = reportType;
  }

  
  public String getVersion()
  {
    return version;
  }

  
  public void setVersion(String version)
  {
    this.version = version;
  }

  public int getEditType()
  {
     return editType;
  }

  public void setEditType(int editType)
  {
     this.editType = editType;
  }
  
  public String toString ()
  {
    StringBuffer buffer = new StringBuffer ();
    buffer.append("ReportToken[");
    buffer.append ("Id: " + getId() + ", ");
    buffer.append ("Name: " + getName() + ", ");
    buffer.append ("EditType: " + getEditType() + ", ");
    buffer.append ("ArchId: " + getArchId()+ ", ");
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
