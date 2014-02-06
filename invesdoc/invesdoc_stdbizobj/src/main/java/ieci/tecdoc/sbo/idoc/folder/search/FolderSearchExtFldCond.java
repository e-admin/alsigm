/*
 * Created on 06-abr-2005
 *
 */
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoExtFldsTbl;
import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.search.SearchOpr;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Representa una condición de búsqueda de carpetas dentro de un archivador
 * para un campo de tipo extendido, es decir, los de texto largo
 * 
 * @author 
 */

public class FolderSearchExtFldCond implements FolderSearchCondition
{
   /**
    * Prefijo de la tabla que contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
    */
    String    m_tblPrefix;
    
    /**
     * Identificador del archivador donde se buscan las carpetas
     */
    int       m_fldId;
    
    /**
     * Operador
     */
    String    m_opr;
    
    /**
     * Valor
     */
    String    m_val;   

    /**
     * Constructor
     * @param tblPrefix prefijo de la tabla que contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
     * @param fldId identificador del campo
     * @param opr operador de búsqueda
     * @param val valorde búsqueda
     */
    public FolderSearchExtFldCond(String tblPrefix, int fldId,
                                  String opr, String val)
    {
       m_tblPrefix  = tblPrefix;
       m_fldId      = fldId; 
       m_opr        = opr;
       m_val        = val;      
    } 

    /**
     * Devuelve la condición sql que representa la condición de búsqueda para un
     * determinado campo
     * @param dbEngine tipo de base de datos
     * @return  condición sql que representa la condición de búsqueda para un
     * determinado campo
     * @throws Exception 
     */    
    public String getSqlCondition(int dbEngine) throws Exception
    {
        String cond = null;

        if (m_opr.equals(SearchOpr.FULL_TEXT))
        {
            cond = getSqlConditionByFullText(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.FULL_TEXT_NOT))
        {
            cond = getSqlConditionByFullTextNot(dbEngine);
        }

        return cond;
    }

   
    private String getSqlConditionByFullText(int dbEngine) throws Exception
    { 
      int    engine;
      String cond = "";

      if (dbEngine == DbEngine.SQLSERVER)
         cond = getSqlServerSqlConditionByFullText();
      else if (dbEngine == DbEngine.ORACLE)
         cond = getOracleSqlConditionByFullText();
      else
      {
         throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                                   FolderSearchError.EM_INVALID_PARAM);    
      }

      return cond;
    }

    private String getOracleSqlConditionByFullText() throws Exception
    {
        /*
         * 1 - EXISTS (SELECT DISTINCT(AxxXF.FDRID) FROM AxxXF WHERE 
         *     AxxXF.FDRID = AxxSF.FDRID AND A31XF.FLDID = fldId AND
         *     CONTAINS (A31XF.TEXT, expresion, 1)>0)          
         */
        StringBuffer  cond = new StringBuffer();
        String        beginSql;
        String        extTblName         = DaoUtil.getExtFldTblName(m_tblPrefix);  
        DaoExtFldsTbl extTbl             = new DaoExtFldsTbl(extTblName);   
        String        extTblTextColName  = extTbl.getTextColName(true); 
       
        beginSql = getBeginSelectSqlCondition();

        cond.append(" EXISTS( ").append(beginSql);
        cond.append(" AND CONTAINS (" + extTblTextColName);
        cond.append(", '" + m_val + "', 1)>0 )");
        
        return cond.toString();
    }

    private String getSqlServerSqlConditionByFullText() throws Exception
    {

        /*
         * 1 - EXISTS (SELECT DISTINCT(AxxXF.FDRID) FROM AxxXF WHERE 
         *     AxxXF.FDRID = AxxSF.FDRID AND A31XF.FLDID = fldId AND
         *     CONTAINS (A31XF.TEXT, expresion)>0)          
         */
        StringBuffer  cond = new StringBuffer();
        String        beginSql;
        String        extTblName         = DaoUtil.getExtFldTblName(m_tblPrefix);  
        DaoExtFldsTbl extTbl             = new DaoExtFldsTbl(extTblName);   
        String        extTblTextColName  = extTbl.getTextColName(true); 
       
        beginSql = getBeginSelectSqlCondition();

        cond.append(" EXISTS( ").append(beginSql);
        cond.append(" AND CONTAINS (" + extTblTextColName);
        cond.append(", '" + m_val + "'))");
        
        return cond.toString();

    }

    private String getSqlConditionByFullTextNot(int dbEngine) throws Exception
    { 
      int    engine;
      String cond = "";

      if (dbEngine == DbEngine.SQLSERVER)
         cond = getSqlServerSqlConditionByFullTextNot();
      else if (dbEngine == DbEngine.ORACLE)
         cond = getOracleSqlConditionByFullTextNot();
      else
      {
         throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                                   FolderSearchError.EM_INVALID_PARAM);    
      }

      return cond;
    }

    private String getOracleSqlConditionByFullTextNot() throws Exception
    {

        /*
         * 1 - EXISTS (SELECT DISTINCT(AxxXF.FDRID) FROM AxxXF WHERE 
         *     AxxXF.FDRID = AxxSF.FDRID AND A31XF.FLDID = fldId AND
         *     CONTAINS (A31XF.TEXT, expresion, 1)=0)          
         */
        StringBuffer  cond = new StringBuffer();
        String        beginSql;
        String        extTblName         = DaoUtil.getExtFldTblName(m_tblPrefix);  
        DaoExtFldsTbl extTbl             = new DaoExtFldsTbl(extTblName);   
        String        extTblTextColName  = extTbl.getTextColName(true); 
       
        beginSql = getBeginSelectSqlCondition();

        cond.append(" EXISTS( ").append(beginSql);
        cond.append(" AND CONTAINS (" + extTblTextColName);
        cond.append(", '" + m_val + "', 1)=0 )");
        
        return cond.toString();

    }

    private String getSqlServerSqlConditionByFullTextNot() throws Exception
    {

        /*
         * 1 - EXISTS (SELECT DISTINCT(AxxXF.FDRID) FROM AxxXF WHERE 
         *     AxxXF.FDRID = AxxSF.FDRID AND A31XF.FLDID = fldId AND
         *     NOT CONTAINS (A31XF.TEXT, expresion))          
         */
        StringBuffer  cond = new StringBuffer();
        String        beginSql;
        String        extTblName         = DaoUtil.getExtFldTblName(m_tblPrefix);  
        DaoExtFldsTbl extTbl             = new DaoExtFldsTbl(extTblName);   
        String        extTblTextColName  = extTbl.getTextColName(true); 
       
        beginSql = getBeginSelectSqlCondition();

        cond.append(" EXISTS( ").append(beginSql);
        cond.append(" AND NOT CONTAINS (").append(extTblTextColName);
        cond.append(", '" + m_val + "'))");
        
        return cond.toString();

    }

    private String getBeginSelectSqlCondition() throws Exception
    {
      
      StringBuffer  tbdr               = new StringBuffer();
      String        extTblName         = DaoUtil.getExtFldTblName(m_tblPrefix);  
      DaoExtFldsTbl extTbl             = new DaoExtFldsTbl(extTblName);   
      String        extTblFdrIdColName = extTbl.getFdrIdColName(true); 
      String        extTblFldIdColName = extTbl.getFldIdColName(true); 
      String        relTblName         = DaoUtil.getRelFldsTblName(m_tblPrefix);  
      String        relTblFdrIdColName;
      
      relTblFdrIdColName  = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();  
      
      tbdr.append("SELECT DISTINCT(").append(extTblFdrIdColName).append(")");
      tbdr.append(" FROM ").append(extTblName).append(" WHERE ");  
      tbdr.append(extTblFldIdColName).append( " = " ).append(m_fldId).append(" AND "); 
      tbdr.append(extTblFdrIdColName).append( " = ").append(relTblFdrIdColName);
      
      return tbdr.toString();
      
   }

}