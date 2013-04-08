
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;


public interface DaoInputXRow extends DbInputRecord
{      
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
