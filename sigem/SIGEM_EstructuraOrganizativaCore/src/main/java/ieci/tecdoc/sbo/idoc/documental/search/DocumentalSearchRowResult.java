/*
 * Created on 26-oct-2005
 *
 */
package ieci.tecdoc.sbo.idoc.documental.search;


public class DocumentalSearchRowResult implements Comparable {
   
   private int m_archId;
   private int m_fdrId;
   private int m_docId;
   
   private String m_archName, m_fdrName, m_docName;
   
   DocumentalSearchRowResult (int archId, int fdrId, int docId) {
      setArchId(archId);
      setFdrId(fdrId);
      setDocId(docId);
   }
   
   
   public String toString()
   {
      return "ArchId=" + getArchId() + "::" + "FolderId=" + getFdrId() + "::" + "DocId=" + getDocId();
   }
   
   public int compareTo(Object o)
   {
      int compare = 0;
      if (o instanceof DocumentalSearchRowResult) {
         DocumentalSearchRowResult fdi = (DocumentalSearchRowResult) o;
         compare = getArchId() - fdi.getArchId();
         if (compare == 0) {
            compare = getFdrId() - fdi.getFdrId();
         }
         if (compare == 0) {
            compare = getDocId() - fdi.getDocId();
         }
      } else {
         compare = this.getClass().getName ().compareTo (o.getClass().getName());
      }
      return compare;
   }
   
   public boolean equals(Object obj)
   {
      boolean equals = true;
      if (obj instanceof DocumentalSearchRowResult) {
         DocumentalSearchRowResult fdi = (DocumentalSearchRowResult) obj;
         equals = (getArchId() == fdi.getArchId()) && (getFdrId() == fdi.getFdrId()) && (getDocId() == fdi.getDocId());
      } else {
         equals = false;
      }
      return equals;
   }


   void setDocId(int m_docId)
   {
      this.m_docId = m_docId;
   }


   public int getDocId()
   {
      return m_docId;
   }



   void setFdrId(int m_fdrId)
   {
      this.m_fdrId = m_fdrId;
   }



   public int getFdrId()
   {
      return m_fdrId;
   }


   void setArchId(int m_archId)
   {
      this.m_archId = m_archId;
   }



   public int getArchId()
   {
      return m_archId;
   }
   public String getArchName()
   {
      return m_archName;
   }
   public void setArchName(String archName)
   {
      this.m_archName = archName;
   }
   public String getDocName()
   {
      return m_docName;
   }
   public void setDocName(String docName)
   {
      this.m_docName = docName;
   }
   public String getFdrName()
   {
      return m_fdrName;
   }
   public void setFdrName(String fdrName)
   {
      this.m_fdrName = fdrName;
   }
}