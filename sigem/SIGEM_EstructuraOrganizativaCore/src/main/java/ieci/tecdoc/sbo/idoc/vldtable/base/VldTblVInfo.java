package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 15:59:35
 */
public class VldTblVInfo
{
   private String m_logDocColName;
   private String m_docColName;
   private int    m_fmt;
   private String m_pkColName;
   private String m_where;
   private String m_ordeyBy;

   public VldTblVInfo(String logColName, String docColName, int fmt, String pkColName, String where, String orderBy)
   {
      this.m_logDocColName = logColName;
      this.m_docColName = docColName;
      this.m_fmt = fmt;
      this.m_pkColName = pkColName;
      this.m_where = where;
      this.m_ordeyBy = orderBy;
   }

   public String getLogColName()
   {
      return m_logDocColName;
   }

   public void setLogColName(String m_logColName)
   {
      this.m_logDocColName = m_logColName;
   }

   public String getDocColName()
   {
      return m_docColName;
   }

   public void setDocColName(String m_docColName)
   {
      this.m_docColName = m_docColName;
   }

   public int getFmt()
   {
      return m_fmt;
   }

   public void setFmt(int m_fmt)
   {
      this.m_fmt = m_fmt;
   }

   public String getPkColName()
   {
      return m_pkColName;
   }

   public void setPkColName(String m_pkColName)
   {
      this.m_pkColName = m_pkColName;
   }

   public String getWhere()
   {
      return m_where;
   }

   public void setWhere(String m_where)
   {
      this.m_where = m_where;
   }

   public String getOrdeyBy()
   {
      return m_ordeyBy;
   }

   public void setOrdeyBy(String m_ordeyBy)
   {
      this.m_ordeyBy = m_ordeyBy;
   }

   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblVInfo [");
      buffer.append("m_logDocColName = ").append(m_logDocColName);
      buffer.append(", m_docColName = ").append(m_docColName);
      buffer.append(", m_fmt = ").append(m_fmt);
      buffer.append(", m_pkColName = ").append(m_pkColName);
      buffer.append(", m_where = ").append(m_where);
      buffer.append(", m_orderBy = ").append(m_ordeyBy);
      buffer.append("]");
      return buffer.toString();

   }

}
