package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 19-nov-2004
 * Time: 10:09:52
 */
public class VldTblDataId extends VldTblData
{
   private Object m_idValue;
   private Object m_colValue;
   private Object m_userColValue;


   public VldTblDataId()
   {
   }

   public VldTblDataId( int m_idValue, String m_colValue) throws Exception
   {
      this.m_colValue = m_colValue;
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

   public Object getIdValue()
   {
      return m_idValue;
   }

   public void setIdValue(Object idValue)
   {
      this.m_idValue = idValue;
   }

   public int getPkValue() throws Exception
   {
      return Integer.parseInt( this.m_idValue.toString() );
   }

   public void setPkValue(int pkValue) throws Exception
   {
      this.m_idValue = Integer.toString( pkValue ) ;
   }

   public Object getUserColValue()
   {
      return m_userColValue;
   }

   public void setUserColValue(Object m_userColValue)
   {
      this.m_userColValue = m_userColValue;
   }

   public VldTblData getDataValue() throws Exception
   {
      return this;
   }
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblDataId [");
      buffer.append("m_idValue = ").append(m_idValue);
      buffer.append(", m_colValue = ").append(m_colValue);
      buffer.append(", m_userColValue = ").append(m_userColValue);
      buffer.append("]");
      return buffer.toString();

   }
  
   public String getJustData()
   {
      return m_userColValue.toString();
   }

}
