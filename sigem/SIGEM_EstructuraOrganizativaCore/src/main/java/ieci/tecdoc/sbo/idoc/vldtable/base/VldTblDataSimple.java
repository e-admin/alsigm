package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 19-nov-2004
 * Time: 10:08:27
 */
public class VldTblDataSimple extends VldTblData
{
   private Object m_colValue;
   private int m_idValue;

   public VldTblDataSimple()
   {
   }

   public VldTblDataSimple(int m_idValue, String m_colValue) throws Exception
   {
      this.m_colValue = m_colValue;
      setPkValue( m_idValue );
   }

   public Object getColValue()
   {
      return m_colValue;
   }

   public void setColValue(Object colValue)
   {
      this.m_colValue = colValue;
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

   public VldTblData getDataValue()throws Exception
   {
      return this;

   }

   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblDataSimple [");
      buffer.append("m_idValue = ").append(m_idValue);
      buffer.append(", m_colValue = ").append(m_colValue);
      buffer.append("]");
      return buffer.toString();

   }
   
   public String getJustData()
   {
      return m_colValue.toString();
   }
   
}
