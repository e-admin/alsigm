/*
 * Created on 07-abr-2005
 */
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.fss.core.FssDaoFtsTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoRepTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;

/**
 * Representa una condición de búsqueda de carpetas dentro de un archivador,
 * pero la condición de búsqueda es documental (dentro de los documentos
 * asociados a una carpeta)
 * 
 * @author
 * 
 * @see FolderSearchCondition
 */

public class FolderSearchFTSCond implements FolderSearchCondition
{
   /**
    * Prefijo de la tabla que contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
    */
    private String    m_archTblPrefix  = null;
    
    /**
     * Identificador del archivador donde se buscan las carpetas
     */
    private int       m_archId         = -1;
    
    /**
     * Expresión de búsqueda documental
     */
    private String    m_searchExpr     = null;    
    
    /**
     * Constructor
     * @param archId identificador del archivador
     * @param archTblPrefix prefijo de la tabla que contiene las carpetas pertenecientes a un
    * archivador. El nombre de la tabla está formado por un prefijo ('A' + idArch) y un
    * sufijo ('SF'), por ejemplo, A32SF, que correspondería con el nombre de la tabla
    * que contiene las carpetas del archivador con identificador 32.
     * @param searchExpr expresión de busqueda documental
     */
    public FolderSearchFTSCond (int archId, String archTblPrefix, String searchExpr)
    {
        m_archId        = archId;
        m_archTblPrefix = archTblPrefix;
        m_searchExpr    = searchExpr;
    }
    
    /**
     * Devuelve la condición sql que representa la condición de búsqueda para un
     * determinado campo
     * @param dbEngine tipo de base de datos
     * @return  condición sql que representa la condición de búsqueda para un
     * determinado campo
     * @throws Exception si se intenta hace búsqueda documental en un gestor
     * documental que no sea OTEXT (Oracle Text)
     */    
    public String getSqlCondition(int dbEngine) throws Exception
    {
      int    engine;
      String cond = "";
     
      if (dbEngine == DbEngine.ORACLE)
         cond = getOracleSqlConditionByFTS();
      else if (dbEngine == DbEngine.SQLSERVER)
         cond = getSqlServerConditionByFTS();
      else
      {
         throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                                   FolderSearchError.EM_INVALID_PARAM);    
      }

      return cond;
    }
    
    private String getOracleSqlConditionByFTS() throws Exception
    {
        /*
         * 1 - SELECT FDRID FROM AxxSF
         * 2 - WHERE AxxSF.FLD1 = '----' AND
         * 3 - (EXISTS (SELECT EXTID2 FROM IVOLFILETFS
         * 4 -	WHERE CONTAINS (PATH, 'perro', 1) > 0 AND EXTID1 = xx 
         * 5 - 	AND AxxSF.FDRID = IVOLFILEFTS.EXTID2)
         * 6 - OR 
         * 7 -  EXISTS (SELECT EXTID2 FROM IVOLVOLTBL 
         * 8 -  WHERE CONTAINS (FILEVAL, 'carpeta', 1) > 0 AND EXTID1 = xx 
         * 9 -  AND AxxSF.FDRID = IVOLVOLTBL.EXTID2)
         * 10 - )
         * 
         * Las lineas 3,4 y 5 representa la condicion de busqueda documental en fichero
         * 'perro' es la expresión de busqueda y EXTID1 es el identificador del archivador 
         */
        StringBuffer  cond            = new StringBuffer();
        String        relTblName      = DaoUtil.getRelFldsTblName(m_archTblPrefix);
        String        relTblFdrIdColName;
        String        ftsTblName       = FssDaoFtsTbl.getTblName();
        String        ftsFdrIdColName  = FssDaoFtsTbl.getExtId2ColName(true);
        String        ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(true);
        String        ftsPathColName   = FssDaoFtsTbl.getPathColName(true);
      
        relTblFdrIdColName  = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();

        cond.append('(');
        
        cond.append("EXISTS(SELECT DISTINCT(").append(ftsFdrIdColName).append(")");
        cond.append(" FROM ").append(ftsTblName).append(" WHERE ");  
        cond.append(ftsArchIdColName).append( " = " ).append(m_archId).append(" AND "); 
        cond.append(ftsFdrIdColName).append( " = ").append(relTblFdrIdColName);
        cond.append(" AND CONTAINS (");
        cond.append(ftsPathColName).append(", '" + m_searchExpr + "',1)>0 ) ");
        
        if (FssDaoRepTbl.rowExists(FssMdoUtil.RT_DB))
        {
           cond.append(" OR ");
        
           ftsTblName 				= FssDaoVolVolTbl.TN;
           ftsFdrIdColName 	= ftsTblName + '.' + FssDaoVolVolTbl.CD_EXTID2.getName ();
           ftsArchIdColName 	= ftsTblName + '.' + FssDaoVolVolTbl.CD_EXTID1.getName ();
           String ftsFileValColName = ftsTblName + '.' + FssDaoVolVolTbl.CD_FILEVAL.getName ();
        
           cond.append("EXISTS(SELECT DISTINCT(").append(ftsFdrIdColName).append(")");
           cond.append(" FROM ").append(ftsTblName).append(" WHERE ");  
           cond.append(ftsArchIdColName).append( " = " ).append(m_archId).append(" AND "); 
           cond.append(ftsFdrIdColName).append( " = ").append(relTblFdrIdColName);
           cond.append(" AND CONTAINS (");
           cond.append(ftsFileValColName).append(", '" + m_searchExpr + "',1)>0 ) ");    
        }
        
        cond.append(')');
        return cond.toString();
    }
    
    
    private String getSqlServerConditionByFTS() throws Exception
    {
       
        StringBuffer  cond            = new StringBuffer();
        String        relTblName      = DaoUtil.getRelFldsTblName(m_archTblPrefix);
        String        relTblFdrIdColName;
        String        ftsTblName       = FssDaoFtsTbl.getTblName();
        String        ftsFdrIdColName  = FssDaoFtsTbl.getExtId2ColName(false);
        String        ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(false);
        String        ftsPathColName   = FssDaoFtsTbl.getPathColName(false);
      
        relTblFdrIdColName  = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();

        cond.append('(');
        cond.append("EXISTS (SELECT W.").append(ftsFdrIdColName);
        cond.append(" FROM OPENQUERY(FILESYSTEM,'SELECT PATH FROM SCOPE() WHERE CONTAINS (CONTENTS,''");
        cond.append(m_searchExpr);
        cond.append("'')') as Q, ").append(ftsTblName).append(" as W WHERE Q.PATH = W.").append(ftsPathColName);
        cond.append(" AND W.").append(ftsArchIdColName).append(" = ");
        cond.append(m_archId);
        cond.append(" AND ").append(relTblFdrIdColName).append(" = W.").append(ftsFdrIdColName).append(")");       
        
       
        if (FssDaoRepTbl.rowExists(FssMdoUtil.RT_DB))
        {
           ftsTblName 			= FssDaoVolVolTbl.TN;
           ftsFdrIdColName 	= FssDaoVolVolTbl.CD_EXTID2.getName ();
           ftsArchIdColName 	= FssDaoVolVolTbl.CD_EXTID1.getName ();
           String ftsFileValColName = FssDaoVolVolTbl.CD_FILEVAL.getName ();
                  
           cond.append(" OR EXISTS (SELECT X.").append(ftsFdrIdColName);
           cond.append(" FROM ").append(ftsTblName);
           cond.append(" as X WHERE CONTAINS(").append(ftsFileValColName).append(",'");
           cond.append(m_searchExpr);
           cond.append("') AND X.").append(ftsArchIdColName).append(" = ");
           cond.append(m_archId);
           cond.append(" AND ").append(relTblFdrIdColName).append(" = X.").append(ftsFdrIdColName).append(" )");
           
        }
        
        cond.append(')');
        
        return cond.toString();
        
    }
    
    /*
    private String getSqlServerConditionByFTS() throws Exception
    {
       
        StringBuffer  cond            = new StringBuffer();
        String        relTblName      = DaoUtil.getRelFldsTblName(m_archTblPrefix);
        String        relTblFdrIdColName;
        String        ftsTblName       = FssDaoFtsTbl.getTblName();
        String        ftsFdrIdColName  = FssDaoFtsTbl.getExtId2ColName(false);
        String        ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(false);
        String        ftsPathColName   = FssDaoFtsTbl.getPathColName(false);
      
        relTblFdrIdColName  = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();

        cond.append('(');
        cond.append("EXISTS (SELECT W.EXTID2 FROM OPENQUERY(FILESYSTEM,'SELECT PATH FROM SCOPE() WHERE CONTAINS (CONTENTS,''");
        cond.append(m_searchExpr);
        cond.append("'')') as Q, IVOLFILEFTS as W WHERE Q.PATH = W.PATH AND W.EXTID1 = ");
        cond.append(m_archId);
        cond.append("AND A").append(m_archId).append("SF.FDRID = W.EXTID2)");        
        
       
        if (FssDaoRepTbl.rowExists(FssMdoUtil.RT_DB))
        {
           ftsTblName 			= FssDaoVolVolTbl.TN;
           ftsFdrIdColName 	= FssDaoVolVolTbl.CD_EXTID2.getName ();
           ftsArchIdColName 	= FssDaoVolVolTbl.CD_EXTID1.getName ();
           String ftsFileValColName = FssDaoVolVolTbl.CD_FILEVAL.getName ();
                  
           cond.append(" OR EXISTS (SELECT X.EXTID2 FROM IVOLVOLTBL as X WHERE CONTAINS(FILEVAL,'");
           cond.append(m_searchExpr);
           cond.append("') AND X.EXTID1 = ");
           cond.append(m_archId);
           cond.append("AND A").append(m_archId).append("SF.FDRID = X.EXTID2 ))");
           
        }
        
        cond.append(')');
        return cond.toString();
    }
    */
}
