
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbOutputRecordSet;



public interface DaoOutputXRows extends DbOutputRecordSet
{   
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
