
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.search.SearchConditionItem;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenFld;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveUtil;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFldType;
import ieci.tecdoc.sbo.idoc.dao.DaoMultFldTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import java.util.ArrayList;

/**
 * Representa una condición de búsqueda de carpetas dentro de un
 * archivador para un campo multivalor
 * 
 * @author
 * 
 * @see FolderSearchCondition
 */

public class FolderSearchMultFldCond implements FolderSearchCondition

{      
   /**
    * Identificador de un campo del archivador
    */
   int       m_fldId;
   
   /**
    * Tipo de campo
    */
   int       m_fldType;
   
   /**
    * Operador 
    */
   String    m_opr;
   
   /**
    * Valores 
    */
   ArrayList m_vals;
   
   /**
    * Prefijo de la tabla que contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
    */
   String    m_tblPrefix;
   
   /**
    * Constructor
    * @param tblPrefix prefijo de la tabla ue contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
    * @param fld referencia a un objeto de tipo ArchiveTokenFld que recoge información
    * de un campo del archivador
    * @param opr operador de búsqueda
    * @param vals valores de búsqueda
    * @throws Exception
    * @see ArchiveTokenFld
    */
   public FolderSearchMultFldCond(String tblPrefix, ArchiveTokenFld fld,
                                  String opr, ArrayList vals) throws Exception
   {
      
      m_fldId          = fld.getId(); 
      m_fldType        = fld.getType();
      m_opr            = opr;
      m_tblPrefix      = tblPrefix;
      m_vals           = vals;      
      
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
      
      if (m_opr.equals(FolderSearchOpr.IN_AND))
      {
         cond = getSqlConditionByInAnd(dbEngine);
      }
      else if (m_opr.equals(FolderSearchOpr.IN_OR))
      {
         cond = getSqlConditionByInOr(dbEngine);
      }
      else if (m_opr.equals(FolderSearchOpr.LIKE_AND))
      {
         cond = getSqlConditionByLikeAnd(dbEngine);
      }
      else if (m_opr.equals(FolderSearchOpr.LIKE_OR))
      {
         cond = getSqlConditionByLikeOr(dbEngine);
      }
      else if (m_opr.equals(FolderSearchOpr.EQUAL))
      {
         cond = getSqlConditionByEqual(dbEngine);
      }      
      
      return cond;
      
   }
   
   private String getBeginSelectSqlCondition(DaoMultFldTbl multTbl) throws Exception
   {
      
      StringBuffer         tbdr;       
      String               multTblFdrIdColName = multTbl.getFdrIdColName(true); 
      String               multTblFldIdColName = multTbl.getFldIdColName(true);      
      String               multTblName         = multTbl.getTblName();
      String               relTblName          = DaoUtil.getRelFldsTblName(m_tblPrefix);  
      String               relTblFdrIdColName;
      
      relTblFdrIdColName  = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();    
      
      tbdr = new StringBuffer();
      
      tbdr.append("SELECT DISTINCT(").append(multTblFdrIdColName).append(")");
      tbdr.append(" FROM ").append(multTblName).append(" WHERE ");  
      tbdr.append(multTblFldIdColName).append( " = " ).append(m_fldId).append(" AND "); 
      tbdr.append(multTblFdrIdColName).append( " = ").append(relTblFdrIdColName);
      
      return tbdr.toString();
      
   }
   
   private String getSqlConditionByInOr(int dbEngine) throws Exception
   {
      
      StringBuffer         tbdr; 
      int                  fldDbType           = ArchiveFldType.getDbDataType(m_fldType);
      String               multTblName         = ArchiveUtil.getMultFldTblName(m_tblPrefix, m_fldType);
      DaoMultFldTbl        multTbl             = null;      
      String               multTblValueColName;
      int                  i,numVals;
      SearchConditionItem  item                = null;
      int                  searchDataType;
      String               condition;
      String               beginSql;  
      
      multTbl = new DaoMultFldTbl(multTblName, fldDbType);
      
      multTblValueColName = multTbl.getValueColName(true);      
     
      searchDataType = FolderSearchUtil.convertFldTypeToSearchDataType(m_fldType);    
      
      tbdr = new StringBuffer();
      
      beginSql = getBeginSelectSqlCondition(multTbl);
      
      tbdr.append(" EXISTS( ").append(beginSql);
      tbdr.append(" AND (");
      
      item = new SearchConditionItem(multTblValueColName, FolderSearchOpr.OR, searchDataType,
                                     m_vals);
                                     
      condition = item.getSqlCondition(dbEngine); 
      
      tbdr.append(condition).append(") )"); 
      
      return tbdr.toString();
      
   }
   
   private String getSqlConditionByLikeOr(int dbEngine) throws Exception
   {
      
      StringBuffer         tbdr; 
      int                  fldDbType           = ArchiveFldType.getDbDataType(m_fldType);
      String               multTblName         = ArchiveUtil.getMultFldTblName(m_tblPrefix, m_fldType);
      DaoMultFldTbl        multTbl             = null;      
      int                  i,numVals;
      int                  searchDataType;
      String               beginSql;  
      String               existsCond;
      
      multTbl = new DaoMultFldTbl(multTblName, fldDbType);
               
      searchDataType = FolderSearchUtil.convertFldTypeToSearchDataType(m_fldType);    
      
      tbdr = new StringBuffer();
      
      beginSql = getBeginSelectSqlCondition(multTbl);
      
      tbdr.append(" EXISTS( ").append(beginSql);
      tbdr.append(" AND (");      
      
      numVals = m_vals.size();
      
      
      for(i = 0; i < numVals; i++)
      {
         existsCond = getExistsSqlCondition(dbEngine, multTbl, FolderSearchOpr.LIKE,
                                            searchDataType, m_vals.get(i));
         tbdr.append(existsCond);
                
         if (i < (numVals -1))
            tbdr.append(" OR ");        
      }     
      
      tbdr.append(") )"); 
      
      return tbdr.toString();
      
   }
   
   private String getExistsSqlCondition(int dbEngine, DaoMultFldTbl multTbl, String opr, 
                                        int searchDataType, Object val)
                  throws Exception
   {
      
      StringBuffer         tbdr;       
      String               multTblValueColName = multTbl.getValueColName(true);       
      String               beginSql;
      SearchConditionItem  item = null;      
      String               condition;
      ArrayList            vals = new ArrayList();
      
      beginSql = getBeginSelectSqlCondition(multTbl); 
      
      vals.add(val);   
      
      item = new SearchConditionItem(multTblValueColName, opr, searchDataType, vals);
      
      tbdr = new StringBuffer();      
      
      tbdr.append("EXISTS( ").append(beginSql).append(" AND ");     
      tbdr.append(item.getSqlCondition(dbEngine)).append(")");
      
      return tbdr.toString();
      
   }
   
   private String getSqlConditionByInAnd(int dbEngine) throws Exception
   {
      
      StringBuffer         tbdr; 
      int                  fldDbType    = ArchiveFldType.getDbDataType(m_fldType);
      String               multTblName  = ArchiveUtil.getMultFldTblName(m_tblPrefix, m_fldType);
      DaoMultFldTbl        multTbl      = null;      
      int                  i,numVals;     
      int                  searchDataType;
      String               existsCond;
      
      multTbl = new DaoMultFldTbl(multTblName, fldDbType);  
      
      searchDataType = FolderSearchUtil.convertFldTypeToSearchDataType(m_fldType);
      
      tbdr = new StringBuffer();
      
      numVals = m_vals.size();
      
      for(i = 0; i < numVals; i++)
      {
         existsCond = getExistsSqlCondition(dbEngine, multTbl, FolderSearchOpr.EQUAL,
                                            searchDataType, m_vals.get(i));
         tbdr.append(existsCond);
                
         if (i < (numVals -1))
            tbdr.append(" AND ");        
      }              
      
      return tbdr.toString();
      
   }
   
   private String getSqlConditionByLikeAnd(int dbEngine) throws Exception
   {
      
      StringBuffer         tbdr; 
      int                  fldDbType    = ArchiveFldType.getDbDataType(m_fldType);
      String               multTblName  = ArchiveUtil.getMultFldTblName(m_tblPrefix, m_fldType);
      DaoMultFldTbl        multTbl      = null; 
      int                  i,numVals;     
      int                  searchDataType;
      String               existsCond;
      
      multTbl = new DaoMultFldTbl(multTblName, fldDbType);  
      
      searchDataType = FolderSearchUtil.convertFldTypeToSearchDataType(m_fldType);
      
      tbdr = new StringBuffer();
      
      numVals = m_vals.size();
      
      for(i = 0; i < numVals; i++)
      {
         existsCond = getExistsSqlCondition(dbEngine, multTbl, FolderSearchOpr.LIKE,
                                            searchDataType, m_vals.get(i));
         tbdr.append(existsCond);
                
         if (i < (numVals -1))
            tbdr.append(" AND ");        
      }              
      
      return tbdr.toString();
      
   }
   
   private String getSqlConditionByEqual(int dbEngine) throws Exception
   {
      StringBuffer         tbdr; 
      int                  fldDbType    = ArchiveFldType.getDbDataType(m_fldType);
      String               multTblName  = ArchiveUtil.getMultFldTblName(m_tblPrefix, m_fldType);
      DaoMultFldTbl        multTbl      = null;     
      int                  searchDataType;
      String               existsCond, notExistsCond;
      
      multTbl = new DaoMultFldTbl(multTblName, fldDbType);  
      
      searchDataType = FolderSearchUtil.convertFldTypeToSearchDataType(m_fldType);
      
      tbdr = new StringBuffer();
      
      existsCond = getExistsSqlCondition(dbEngine, multTbl, FolderSearchOpr.EQUAL,
                                         searchDataType, m_vals.get(0));      
         
      notExistsCond = getExistsSqlCondition(dbEngine, multTbl, FolderSearchOpr.DISTINCT,
                                            searchDataType, m_vals.get(0));
                
      tbdr.append(existsCond).append(" AND NOT ").append(notExistsCond);             
      
      return tbdr.toString();
      
   }
   
} // class
