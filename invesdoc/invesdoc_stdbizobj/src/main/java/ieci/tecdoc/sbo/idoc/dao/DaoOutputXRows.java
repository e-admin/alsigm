
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public interface DaoOutputXRows extends DbOutputRecordSet
{   
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
