package ieci.tecdoc.core.search;

import java.util.ArrayList;
import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.exception.IeciTdException;

public class SearchConditionItem
{

    private String    m_qualifiedColName;
    private String    m_opr;
    private int       m_dataType;
    private ArrayList m_vals;

    public SearchConditionItem()
    {
        m_qualifiedColName = null;
        m_opr              = null;
        m_dataType         = 0;
        m_vals             = null;
    }

    public SearchConditionItem(String qualifiedColName, String opr, int dataType, ArrayList vals)
    {
        m_qualifiedColName = qualifiedColName;
        m_opr              = opr;
        m_dataType         = dataType;
        m_vals             = vals;
    }

    public String getSqlCondition(int dbEngine) throws Exception
    {

        String cond = null;

        if (m_opr.equals(SearchOpr.EQUAL))
        {
            cond = getSqlConditionByEqual(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.DISTINCT))
        {
            cond = getSqlConditionByDistinct(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.GREATER))
        {
            cond = getSqlConditionByGreater(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.GREATER_EQUAL))
        {
            cond = getSqlConditionByGreaterEqual(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.LOWER))
        {
            cond = getSqlConditionByLower(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.LOWER_EQUAL))
        {
            cond = getSqlConditionByLowerEqual(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.BETWEEN))
        {
            cond = getSqlConditionByBetween(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.LIKE))
        {
            cond = getSqlConditionByLike();
        }
        else if (m_opr.equals(SearchOpr.OR))
        {
            cond = getSqlConditionByOr(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.FULL_TEXT))
        {
            cond = getSqlConditionByFullText(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.FULL_TEXT_NOT))
        {
            cond = getSqlConditionByFullTextNot(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.IS_NULL))
        {
            cond = getSqlConditionIsNull(dbEngine);
        }
        else if (m_opr.equals(SearchOpr.IS_NOT_NULL))
        {
            cond = getSqlConditionIsNotNull(dbEngine);
        }

        return cond;

    }

    /**
    * @param dbEngine
    * @return
    */
   private String getSqlConditionIsNull(int dbEngine)
   {
      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(m_qualifiedColName).append(" IS NULL ");

      return tbdr.toString();
   }
   
   private String getSqlConditionIsNotNull(int dbEngine)
   {
      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(m_qualifiedColName).append(" IS NOT NULL ");

      return tbdr.toString();
   }

   private String getSqlConditionByEqual(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" = ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByDistinct(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" <> ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByGreater(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" > ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByGreaterEqual(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" >= ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByLower(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" < ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByLowerEqual(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" <= ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByBetween(int dbEngine) throws Exception
    {

        StringBuffer tbdr;

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(" BETWEEN ");
        tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(0)));
        tbdr.append(" AND ").append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(1)));

        return tbdr.toString();

    }

    private String getSqlConditionByLike() throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" LIKE ");

        tbdr = new StringBuffer();

        tbdr.append(m_qualifiedColName).append(sqlOpr);
        tbdr.append(SearchUtil.getTextSqlSyntaxValue((String) m_vals.get(0)));

        return tbdr.toString();

    }

    private String getSqlConditionByOr(int dbEngine) throws Exception
    {

        StringBuffer tbdr;
        String       sqlOpr = new String(" = ");
        int          i, numVals;

        tbdr = new StringBuffer();

        numVals = m_vals.size();

        if (numVals >= 1)
          tbdr.append ("(");
        
        for (i = 0; i < numVals; i++)
        {
            tbdr.append(m_qualifiedColName).append(sqlOpr);
            tbdr.append(SearchUtil.getSqlSyntaxValue(dbEngine, m_dataType, m_vals.get(i)));

            if (i < (numVals - 1))
                tbdr.append(" OR ");
        }

        if (numVals >= 1)
          tbdr.append (")");
        
        return tbdr.toString();

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
         throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                   SearchError.EM_INVALID_PARAM);    
      }

      return cond;
    }

    private String getOracleSqlConditionByFullText() throws Exception
    {
       /*
        * CONTAINS (Columna, expresion, 1) > 0         
        */
         
        StringBuffer cond = new StringBuffer();
        String       expr = (String)m_vals.get(0);
        
        cond.append(" CONTAINS (");
        cond.append(m_qualifiedColName);
        cond.append(", '" + expr + "', 1)>0 ");

        return cond.toString();
    }

    private String getSqlServerSqlConditionByFullText() throws Exception
    {

        /*
        * CONTAINS (Columna, expresion)          
        */
         
        StringBuffer cond = new StringBuffer();
        String       expr = (String)m_vals.get(0);
        
        cond.append(" CONTAINS (");
        cond.append(m_qualifiedColName);
        cond.append(", '" + expr + "') ");

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
         throw new IeciTdException(SearchError.EC_INVALID_PARAM, 
                                   SearchError.EM_INVALID_PARAM);    
      }

      return cond;
    }

    private String getOracleSqlConditionByFullTextNot() throws Exception
    {

        /*
        * CONTAINS (Columna, expresion, 1) = 0         
        */
         
        StringBuffer cond = new StringBuffer();
        String       expr = (String)m_vals.get(0);
        
        cond.append(" CONTAINS (");
        cond.append(m_qualifiedColName);
        cond.append(", '" + expr + "', 1)=0 ");

        return cond.toString();

    }

    private String getSqlServerSqlConditionByFullTextNot() throws Exception
    {

        /*
        * NOT CONTAINS (Columna, expresion)         
        */
         
        StringBuffer cond = new StringBuffer();
        String       expr = (String)m_vals.get(0);
        
        cond.append(" NOT CONTAINS (");
        cond.append(m_qualifiedColName);
        cond.append(", '" + expr + "') ");

        return cond.toString();

    }

    /**
     * @return Returns the m_dataType.
     */
    public int getDataType()
    {
        return m_dataType;
    }

    /**
     * @return Returns the m_opr.
     */
    public String getOpr()
    {
        return m_opr;
    }

    /**
     * @return Returns the m_qualifiedColName.
     */
    public String getQualifiedColName()
    {
        return m_qualifiedColName;
    }

    /**
     * @return Returns the m_vals.
     */
    public ArrayList getVals()
    {
        return m_vals;
    }
} // class
