package ieci.tecdoc.sbo.idoc.folder.search;

import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.archive.base.*;

/**
 * Clase que encapusula los filtros de búsqueda de carpetas dentro de un
 * archivador y el conjunto de campos por los cuales se ordena los resultados
 * de una búsqueda
 */

public final class FolderSearchConditionFactory
{   
   
   private ArchiveToken m_arch;


   public FolderSearchConditionFactory(ArchiveToken arch)
   {   
      m_arch = arch;   
   }  
  
   public FolderSearchCondition createSearchCondition(int fldId, String opr, ArrayList vals)
                                throws Exception
   {
      ArchiveTokenFld       fld  = m_arch.getFlds().findById(fldId);
      FolderSearchCondition cond = null;

      cond = createSearchCondition(fld, opr, vals);

      return cond;
   }
     
   public FolderSearchCondition createSearchCondition(int fldId, String opr, Object val)
                                throws Exception
   {
      ArrayList             vals = new ArrayList();
      FolderSearchCondition cond = null;

      vals.add(val);

      cond = createSearchCondition(fldId, opr, vals);
      
      return cond;
   } 

   public FolderSearchCondition createSearchCondition(FolderSearchCondition cond1, 
                                                      FolderSearchCondition cond2,
                                                      String opr)
                                throws Exception
   {      
      FolderSearchCondition cond = null;

      cond = new FolderSearchCompCond(cond1, cond2, opr);

      return cond;
   }      
   
      
   private FolderSearchCondition createSearchCondition(ArchiveTokenFld fld, String opr,
                                                       ArrayList vals) throws Exception
   {
      FolderSearchCondition cond = null;

      if (fld.isExt())
      {
         cond = createExtSearchCondition(fld, opr, (String) vals.get(0));
      }
      else if (fld.isMult())
      {
         cond = createMultSearchCondition(fld, opr, vals);
      }
      else
      {
         cond = createRelSearchCondition(fld, opr, vals);
      }

      return cond;
   }

   private FolderSearchCondition createRelSearchCondition(ArchiveTokenFld fld, String opr,
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

      return item;

   }

   private FolderSearchCondition createMultSearchCondition(ArchiveTokenFld fld, String opr,
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

      return item;

   }

  
   private FolderSearchCondition createExtSearchCondition(ArchiveTokenFld fld, String opr,
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

      return item;
   }

   
   public FolderSearchCondition addFTSSearchCondition(String condition) throws Exception
   {
      FolderSearchCondition item = null;
      
      item = new FolderSearchFTSCond(m_arch.getId(), m_arch.getTblPrefix(), condition);

      return item;
   }

   

} // class
