package ieci.tecdoc.sbo.idoc.vldtable.base;

import java.util.ArrayList;

/**
 * User: RobertoBas
 * Date: 18-nov-2004
 * Time: 15:49:23
 */
public class VldTblSearchResults
{
   private ArrayList m_vldTblSearchResults= new ArrayList() ;

   public ArrayList getVldTblSearchResults()
   {
      return m_vldTblSearchResults;
   }

   public void setVldTblSearchResults(ArrayList m_vldTblSearchResult)
   {
      this.m_vldTblSearchResults = m_vldTblSearchResult;
   }

   public void addtVldTblSearchResult(Object data)
   {
      this.m_vldTblSearchResults.add(data) ;
   }

   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("VldTblSearchResults [");
      buffer.append("m_vldTblSearchResults = ").append(m_vldTblSearchResults);
      buffer.append("]");
      return buffer.toString();

   }
   
   public int count()
   {
       return m_vldTblSearchResults.size();
   }

}
