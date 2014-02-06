package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 19-nov-2004
 * Time: 10:07:56
 */
public abstract class VldTblData
{
   protected int m_pkValue;
   protected Object m_dataValue;

   public abstract int getPkValue() throws Exception ;

   public abstract void setPkValue(int pkValue) throws Exception ;

   public abstract VldTblData getDataValue() throws Exception ;

   public abstract String toString();
   
   public abstract String getJustData (); 

}
