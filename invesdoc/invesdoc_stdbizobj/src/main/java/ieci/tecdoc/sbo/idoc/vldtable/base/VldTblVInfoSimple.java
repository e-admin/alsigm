package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 15:59:35
 */
public class VldTblVInfoSimple extends VldTblVInfo
{


   public VldTblVInfoSimple(String logColName, String docColName, int fmt, String pkColName, String where, String orderBy)
   {
      super(logColName,docColName,fmt,pkColName,where, orderBy);
   }

   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblVInfoSimple [");
      buffer.append("_").append(super.toString() );
      buffer.append("]");
      return buffer.toString();

   }

}
