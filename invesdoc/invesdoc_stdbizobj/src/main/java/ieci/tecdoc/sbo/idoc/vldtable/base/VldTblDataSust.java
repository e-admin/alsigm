package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 19-nov-2004
 * Time: 10:08:34
 */
public class VldTblDataSust extends VldTblData
{
   private int m_idValue;
   private Object m_colValue;
   private Object m_sustColValue;


   public VldTblDataSust()
   {
   }

   public VldTblDataSust(int m_idValue, String m_colValue, String m_sustColValue) throws Exception
   {
      this.m_colValue = m_colValue;
      this.m_sustColValue = m_sustColValue;
      setPkValue(m_idValue );
   }

   public Object getColValue()
   {
      return m_colValue;
   }

   public void setColValue(Object colValue)
   {
      this.m_colValue = colValue;
   }

   public Object getSustColValue()
   {
      return m_sustColValue;
   }

   public void setSustColValue(Object sustColValue)
   {
      this.m_sustColValue = sustColValue;
   }

   public int getIdValue()
   {
      return m_idValue;
   }

   public void setIdValue(int idValue)
   {
      this.m_idValue = idValue;
   }


   public int getPkValue() throws Exception
   {
      return this.m_idValue ;
   }

   public void setPkValue(int pkValue) throws Exception
   {
      this.m_idValue = pkValue;
   }

   public VldTblData getDataValue() throws Exception
   {
      return this;

   }
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblDataSust [");
      buffer.append("m_idValue = ").append(m_idValue);
      buffer.append(", m_colValue = ").append(m_colValue);
      buffer.append(", m_sustColValue = ").append(m_sustColValue);
      buffer.append("]");
      return buffer.toString();

   }

   public String getJustData()
   {
      StringBuffer data = new StringBuffer();
      if (m_colValue != null)
         data.append(m_colValue.toString());
      if (m_sustColValue != null)
      {
         if (m_colValue != null)         
            data.append(" - ");
         data.append(m_sustColValue.toString());
      }
      return data.toString();
   }

}
