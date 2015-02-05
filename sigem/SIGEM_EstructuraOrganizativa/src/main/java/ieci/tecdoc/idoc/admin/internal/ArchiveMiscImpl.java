package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveMisc;


public class ArchiveMiscImpl implements ArchiveMisc
{
   private String m_fdrName;
   private int    m_volListType;
   private int    m_volListId;
   
   public ArchiveMiscImpl(String fdrName, int volListType, int volListId)
   {  
      m_fdrName     = fdrName;
      m_volListType = volListType;
      m_volListId   = volListId;  
   }
   
   public String getFdrName()
   {
      return m_fdrName;
   }
   
   public void setFdrName(String fdrName)
   {
      m_fdrName = fdrName;
   }
   
   public int getVolListId()
   {
      return m_volListId;
   }
   
   public void setVolListId(int volListId)
   {
      m_volListId = volListId; 
   }
   
   public int getVolListType()
   {
      return m_volListType;
   }
   
   public void setVolListType(int volListType)
   {
      m_volListType = volListType;
   }
   public void setMisc(String fdrName, int volListId, int volListType)
   {
      m_fdrName = fdrName;
      m_volListId = volListId;
      m_volListType = volListType;
   }
   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Miscellany";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("FoldersName", m_fdrName);
      bdr.addSimpleElement("VolListType", Integer.toString(m_volListType));
      bdr.addSimpleElement("VolListId", Integer.toString(m_volListId));
      
      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   public String toXML() 
   {
      return toXML(true);
   }
   
   public String toString()
   {
      return toXML(false);
   }
   
}

