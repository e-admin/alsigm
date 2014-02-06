package ieci.tecdoc.sbo.idoc.folder.search;

import java.util.ArrayList;
import ieci.tecdoc.core.search.SearchOrderItem;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.archive.base.*;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;

/**
 * Clase que encapusula los filtros de búsqueda de carpetas dentro de un
 * archivador y el conjunto de campos por los cuales se ordena los resultados
 * de una búsqueda
 */

public final class FolderSearchQuery
{

   /**
    * Colección de objetos de tipo FolderSearchCondition. Cada uno de estos
    * objetos representa una condición de búsqueda
    */
   private ArrayList    m_searchConditionItems;
   
   /**
    * Colección de objetos de tipo SearchOrderItem. Cada uno de estos objetos
    * representa un campo por el cual ordenar la búsqueda
    */
   private ArrayList    m_searchOrderItems;
   
   /**
    * Atributo que contiene información del archivador sobre el cual se va a realizar
    * una búsqueda
    */
   private ArchiveToken m_arch;

   /**
    * Contructor
    * @param arch - referencia a un objeto de tipo ArchiveToken que cotiene información
    * del archivador
    * @see ArchiveToken
    */
   public FolderSearchQuery(ArchiveToken arch)
   {
      m_searchConditionItems = new ArrayList();
      m_searchOrderItems = new ArrayList();
      m_arch = arch;
   }

   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda de carpetas
    * dentro de un archivador
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception - si se produce algún error en la obtención de la condición sql
    */
   public String getSqlWhere(int dbEngine) throws Exception
   {

      StringBuffer tbdr;
      int i, numConds;
      FolderSearchCondition item = null;

      tbdr = new StringBuffer();

      numConds = m_searchConditionItems.size();

      for (i = 0; i < numConds; i++)
      {
         item = (FolderSearchCondition) m_searchConditionItems.get(i);

         tbdr.append(item.getSqlCondition(dbEngine));

         if (i < (numConds - 1)) tbdr.append(" AND ");
      }

      return tbdr.toString();

   }

   /**
    * Devuelve la cláusula ORDER BY que define la ordenación de las carpetas resultado
    * de una búsqueda
    * @return cláusula ORDER BY
    * @throws Exception - si se produce algún error en la obtención de la cláusula
    */
   public String getSqlOrderBy() throws Exception
   {

      StringBuffer tbdr;
      int i, numItems;
      SearchOrderItem item = null;

      tbdr = new StringBuffer();

      numItems = m_searchOrderItems.size();

      for (i = 0; i < numItems; i++)
      {
         item = (SearchOrderItem) m_searchOrderItems.get(i);

         tbdr.append(item.getSqlOrderItem());

         if (i < (numItems - 1)) tbdr.append(" , ");
      }

      return tbdr.toString();

   }

   /**
    * Devuelve una cadena que representa las condiciones de búsqueda de carpetas dentro
    * de un archivador y la cláusula ORDER BY para la ordenación de los resultados de la
    * búsqueda  
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql y order by 
    * @throws Exception - si se produce algún error en la obtención de la condición y cláusula 
    * order by
    */   
   public String getSqlQual(int dbEngine) throws Exception
   {

      String qual = "";
      String where;
      String orderBy;

      where = getSqlWhere(dbEngine);
      orderBy = getSqlOrderBy();

      if (!where.equals("")) qual = qual + " WHERE (" + where + ") ";

      if (!orderBy.equals("")) qual = qual + " ORDER BY " + orderBy;

      return qual;

   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName - nombre del campo del archivador
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */
   public void addSearchCondition(String fldName, String opr, ArrayList vals)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findByName(fldName);

      addSearchCondition(fld, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId - identificador del campo del archivador
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */   
   public void addSearchCondition(int fldId, String opr, ArrayList vals)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findById(fldId);

      addSearchCondition(fld, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName - nombre del campo del archivador
    * @param opr - operador de búsqueda
    * @param val - valor del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */      
   public void addSearchCondition(String fldName, String opr, Object val)
            throws Exception
   {
      ArrayList vals = new ArrayList();

      vals.add(val);

      addSearchCondition(fldName, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId - identificador del campo del archivador
    * @param opr - operador de búsqueda
    * @param val - valor del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */         
   public void addSearchCondition(int fldId, String opr, Object val)
            throws Exception
   {
      ArrayList vals = new ArrayList();

      vals.add(val);

      addSearchCondition(fldId, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName - nombre del campo del archivador
    * @param opr - operador de búsqueda
    * @param val - valor del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */      
   public void addSearchCondition(String fldName, String opr, String val)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findByName(fldName);

      addSearchCondition(fld, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId - identificador del campo del archivador
    * @param opr - operador de búsqueda
    * @param val - valor del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */            
   public void addSearchCondition(int fldId, String opr, String val)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findById(fldId);

      addSearchCondition(fld, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName - nombre del campo del archivador
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */         
   public void addSearchCondition(String fldName, String opr, String[] vals)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findByName(fldName);

      addSearchCondition(fld, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId - identificador del campo del archivador
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */            
   public void addSearchCondition(int fldId, String opr, String[] vals)
            throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findById(fldId);

      addSearchCondition(fld, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param val - valor del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */               
   public void addSearchCondition(ArchiveTokenFld fld, String opr, String val)
            throws Exception
   {
      Object value;
      int idx = -1;
      ArchiveTokenFldVld vld = null;
      String fmtInfo = null;
      ArrayList vals = new ArrayList();

      idx = m_arch.getArchValidation().findFldValidationIndex(fld.getId());

      if (idx != -1)
      {
         vld = m_arch.getArchValidation().getFldValidation(idx);
         fmtInfo = vld.getFmtInfo();
      }

      value = FolderSearchUtil.parseSearchValue(fmtInfo, fld.getType(), val);

      vals.add(value);

      addSearchCondition(fld, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */      
   public void addSearchCondition(ArchiveTokenFld fld, String opr, String[] vals)
            throws Exception
   {
      ArrayList values;
      int idx = -1;
      ArchiveTokenFldVld vld = null;
      String fmtInfo = null;

      idx = m_arch.getArchValidation().findFldValidationIndex(fld.getId());

      if (idx != -1)
      {
         vld = m_arch.getArchValidation().getFldValidation(idx);
         fmtInfo = vld.getFmtInfo();
      }

      values = FolderSearchUtil.parseSearchValues(fmtInfo, fld.getType(), vals);

      addSearchCondition(fld, opr, values);

   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */      
   private void addSearchCondition(ArchiveTokenFld fld, String opr,
            ArrayList vals) throws Exception
   {
      FolderSearchCondition item = null;

      if (fld.isExt())
      {
         addExtSearchCondition(fld, opr, (String) vals.get(0));
      }
      else if (fld.isMult())
      {
         addMultSearchCondition(fld, opr, vals);
      }
      else
      {
         addRelSearchCondition(fld, opr, vals);
      }
   }

   /**
    * Añade una condición de búsqueda para un campo relacional del archivador
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */
   private void addRelSearchCondition(ArchiveTokenFld fld, String opr,
            ArrayList vals) throws Exception
   {
      FolderSearchCondition item = null;

      if (FolderSearchOpr.isMultOperator(opr))
      {

         throw new IeciTdException(FolderSearchError.EC_INVALID_SEARCH_OPR,
                  FolderSearchError.EM_INVALID_SEARCH_OPR);
      }
      else
      {
         if (!fld.isDoc() && FolderSearchOpr.isDocOperator(opr)) {

         throw new IeciTdException(FolderSearchError.EC_INVALID_SEARCH_OPR,
                  FolderSearchError.EM_INVALID_SEARCH_OPR); }

         item = new FolderSearchRelFldCond(m_arch.getTblPrefix(), fld, opr,
                  vals);
      }

      m_searchConditionItems.add(item);

   }

   /**
    * Añade una condición de búsqueda para un campo múltiple del archivador
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */   
   private void addMultSearchCondition(ArchiveTokenFld fld, String opr,
            ArrayList vals) throws Exception
   {
      FolderSearchCondition item = null;

      if (FolderSearchOpr.isMultOperator(opr))
      {

         item = new FolderSearchMultFldCond(m_arch.getTblPrefix(), fld, opr,
                  vals);

      }
      else
      {

         throw new IeciTdException(FolderSearchError.EC_INVALID_SEARCH_OPR,
                  FolderSearchError.EM_INVALID_SEARCH_OPR);
      }

      m_searchConditionItems.add(item);

   }

   /**
    * Añade una condición de búsqueda para un campo extendido del archivador
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que contiene la información
    * del campo sobre el cual añadir una condición
    * @param opr - operador de búsqueda
    * @param vals - valores del campo  
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    * @see ArchiveTokenFld
    */   
   private void addExtSearchCondition(ArchiveTokenFld fld, String opr,
            String val) throws Exception
   {
      FolderSearchCondition item = null;

      if (!fld.isDoc() || !FolderSearchOpr.isDocOperator(opr))
      {
         throw new IeciTdException(FolderSearchError.EC_INVALID_SEARCH_OPR,
                  FolderSearchError.EM_INVALID_SEARCH_OPR);
      }
      else
      {

         item = new FolderSearchExtFldCond(m_arch.getTblPrefix(), fld.getId(),
                  opr, val);
      }

      m_searchConditionItems.add(item);
   }

   /**
    * Añade una condición de búsqueda FULL TEXT SEARCH para un campo del archivador
    * @param condition - expresión de búsqueda documental   
    * @throws Exception - si se produce algún error al añadir la condicion de búsqueda
    */   
   public void addFTSSearchCondition(String condition) throws Exception
   {
      m_searchConditionItems.add(new FolderSearchFTSCond(m_arch.getId(),
               m_arch.getTblPrefix(), condition));
   }

   /**
    * Añade un campo dentro del conjunto de campo por los cuales se va a ordenar la
    * búsqueda 
    * @param fldName - nombre del campo
    * @param desc - la búsqueda es descendente
    * @throws Exception - si se produce algún error al añadir el campo
    */
   public void addSearchOrder(String fldName, boolean desc) throws Exception
   {
      ArchiveTokenFld fld = m_arch.getFlds().findByName(fldName);

      addSearchOrder(fld, desc);

   }

   /**
    * Añade un campo dentro del conjunto de campo por los cuales se va a ordenar la
    * búsqueda 
    * @param fldId - identificador del campo
    * @param desc - la búsqueda es descendente
    * @throws Exception - si se produce algún error al añadir el campo
    */   
   public void addSearchOrder(int fldId, boolean desc) throws Exception
   {

      ArchiveTokenFld fld = m_arch.getFlds().findById(fldId);

      addSearchOrder(fld, desc);

   }

   /**
    * Añade el campo identificador de la carpeta al conjunto de campo por los cuales
    * de va a ordenar la búsqueda
    * @param desc - la búsqueda es descendente
    * @throws Exception - si se produce algún error al añadir el campo identificador
    */
   public void addFdrIdSearchOrder(boolean desc) throws Exception
   {
      String tblPrefix, qualifiedColName;
      String relTblName;

      tblPrefix = m_arch.getTblPrefix();
      relTblName = DaoUtil.getRelFldsTblName(tblPrefix);

      qualifiedColName = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();

      SearchOrderItem item = new SearchOrderItem(qualifiedColName, desc);

      m_searchOrderItems.add(item);

   }

   /**
    * Añade un campo al conjunto de campos por los cuales se va a ordenar la 
    * búsqueda de carpetas
    * @param fld - referencia a un objeto de tipo ArchiveTokenFld que cotiene
    * información del campo 
    * @param desc - la búsqueda es descendente
    * @throws Exception - si se produce algún error al añadir el campo
    */
   private void addSearchOrder(ArchiveTokenFld fld, boolean desc)
            throws Exception
   {
      String tblName, qualifiedColName;

      if (fld.isMult() || fld.isExt()) { throw new IeciTdException(
               FolderSearchError.EC_INVALID_SEARCH_ORDER_FLD,
               FolderSearchError.EM_INVALID_SEARCH_ORDER_FLD); }

      tblName = DaoUtil.getRelFldsTblName(m_arch.getTblPrefix());
      qualifiedColName = tblName + "." + fld.getColName();

      SearchOrderItem item = new SearchOrderItem(qualifiedColName, desc);

      m_searchOrderItems.add(item);

   }

} // class
