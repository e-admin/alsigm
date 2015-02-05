package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 16:45:56
 */
public class VldTblVInfoSust extends VldTblVInfo
{
   private String m_LogSustColName;
   private String m_sustColName;
   private int    m_sustFmt;
   private String m_sustOrderBy;

   public VldTblVInfoSust(String m_logColName, String m_docColName, int m_fmt, String m_sustLogColName, String m_sustDocColName, int m_sustFmt, String m_pkColName, String m_where, String m_docOrderBy, String m_sustOrderBy)
   {
      super(m_logColName,m_docColName ,m_fmt,m_pkColName,m_where,m_docOrderBy );
      this.m_LogSustColName = m_sustLogColName;
      this.m_sustColName = m_sustDocColName;
      this.m_sustFmt = m_sustFmt;
      this.m_sustOrderBy = m_sustOrderBy;
   }

   public String getSustLogColName()
   {
      return m_LogSustColName;
   }

   public void setSustLogColName(String m_sustLogColName)
   {
      this.m_LogSustColName = m_sustLogColName;
   }

   public String getSustDocColName()
   {
      return m_sustColName;
   }

   public void setSustDocColName(String m_sustDocColName)
   {
      this.m_sustColName = m_sustDocColName;
   }

   public int getSustFmt()
   {
      return m_sustFmt;
   }

   public void setSustFmt(int m_sustFmt)
   {
      this.m_sustFmt = m_sustFmt;
   }

   public String getSustOrderBy()
   {
      return m_sustOrderBy;
   }

   public void setSustOrderBy(String m_sustOrderBy)
   {
      this.m_sustOrderBy = m_sustOrderBy;
   }
}
