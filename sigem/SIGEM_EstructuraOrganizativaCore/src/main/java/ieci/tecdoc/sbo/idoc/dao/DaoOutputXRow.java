
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;


public interface DaoOutputXRow extends DbOutputRecord
{      
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
