
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecordSet;



public interface DaoOutputXRows extends DbOutputRecordSet
{   
   public String getColumnNames(String tblName) throws Exception;   
   
} // class
