
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbInputRecord;


public interface DaoInputXRow extends DbInputRecord
{      
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
