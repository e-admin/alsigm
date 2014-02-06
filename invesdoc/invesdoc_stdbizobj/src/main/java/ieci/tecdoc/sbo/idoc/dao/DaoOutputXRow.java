
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public interface DaoOutputXRow extends DbOutputRecord
{      
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
