
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;


public interface DaoOutputXRow extends DbOutputRecord
{      
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
