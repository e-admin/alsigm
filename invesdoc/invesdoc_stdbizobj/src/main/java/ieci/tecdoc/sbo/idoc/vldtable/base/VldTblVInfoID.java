package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 16:45:56
 */
public class VldTblVInfoID extends VldTblVInfo
{
   private String m_logUserColName;
   private String m_userColName;
   private int    m_userFmt;

   public VldTblVInfoID(String m_logColName, String m_docColName, int m_fmt, String m_idLogColName, String m_idDocColName, int m_idFmt, String m_pkColName, String m_where, String m_orderBy)
   {
      super(m_logColName,m_docColName,m_fmt,m_pkColName,m_where, m_orderBy);
      this.m_logUserColName = m_idLogColName;
      this.m_userColName = m_idDocColName;
      this.m_userFmt = m_idFmt;
   }

   public String getLogUserColName()
   {
      return m_logUserColName;
   }

   public void setLogUserColName(String m_logUserColName)
   {
      this.m_logUserColName = m_logUserColName;
   }

   public String getUserColName()
   {
      return m_userColName;
   }

   public void setUserColName(String m_userColName)
   {
      this.m_userColName = m_userColName;
   }

   public int getUserFmt()
   {
      return m_userFmt;
   }

   public void setUserFmt(int m_idFmt)
   {
      this.m_userFmt = m_idFmt;
   }
}
