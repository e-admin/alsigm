
package ieci.tecdoc.sbo.idoc.archive.std;

import ieci.tecdoc.sbo.idoc.archive.base.*;
import ieci.tecdoc.core.textutil.UtilX;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import java.text.StringCharacterIterator;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetRows;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetRow;
import ieci.tecdoc.sbo.idoc.dao.DaoArchDetType;
import ieci.tecdoc.sbo.idoc.vldtable.base.VldTblToken;
import ieci.tecdoc.sbo.idoc.vldtable.base.VldTblUtil;
import ieci.tecdoc.sbo.idoc.vldtable.base.VldTblVInfo;
import ieci.tecdoc.sbo.idoc.vldtable.base.VldTblVInfoHierarchical;
import ieci.tecdoc.sbo.idoc.vldtable.std.VldMdoVldTable;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoArchive;
import ieci.tecdoc.sbo.idoc.acs.AcsTokenArchive;

public class ArchiveMdoToken
{
   
   private static final String MISC_VERS1_0_0    = "01.00";  
   private static final String MISC_CURRENT_VERS = "01.01";   
   
   private ArchiveMdoToken()
   {      
   }
   
   public static ArchiveToken createArchiveToken(AcsAccessToken accToken, 
                                                 int archId)
                              throws Exception
   {
      
      ArchiveToken               token; 
      ArchiveDaoArchHdrTokenRow  row       = null;
      DaoArchDetRows             detRows   = null;
      DaoArchDetRow              detRow    = null;        
      int                        i, numRows, count;      
      AcsTokenArchive            acsToken  = null;

      token = new ArchiveToken(); 
      
      row = new ArchiveDaoArchHdrTokenRow();
      
      DaoArchHdrTbl.selectRow(archId, row);
      
      token.setArchHdr(row.m_id, row.m_name, row.m_tblPrefix, 
                       row.m_flags, row.m_accessType, row.m_acsId);
           
      detRows = DaoArchDetTbl.selectAllColumnRows(archId);
      
      numRows = detRows.count();
      
      for (i = 0; i < numRows; i++)
      {
         detRow = detRows.getRecord(i);
         
         switch(detRow.m_detType)
         {
            case DaoArchDetType.DET_TYPE_FLD_DEF:
               
               fillArchiveTokenFlds(token, detRow.m_detVal);              
               break; 
               
            case DaoArchDetType.DET_TYPE_CLF_DEF:
               
               fillArchiveTokenDividers(token, detRow.m_detVal);              
               break; 
               
            case DaoArchDetType.DET_TYPE_VLD_DEF:
               
               fillArchiveTokenValidation(token, detRow.m_detVal);              
               break;
               
            case DaoArchDetType.DET_TYPE_MISC_DEF:
               
               fillArchiveTokenMisc(token, detRow.m_detVal);              
               break; 
         }         
         
      }
      
      adjustHierarchicalVldTokens (token);
      
      if ( (accToken != null) && 
           (row.m_accessType == AcsAccessType.ACS_PROTECTED) )
      {
         acsToken = AcsMdoArchive.createAcsToken(accToken, row.m_acsId);                  
         
         token.setAcs(acsToken);         
      }
      
      return token;    
      
   }
   
   /**
    * Se reajustan los token de tipo validación jerárquica, de manera que queden 
    * estas validaciones contenidas en la validación del propio campo.
    * 
    * @param archiveToken
    * @throws Exception
    */
   private static void adjustHierarchicalVldTokens (ArchiveToken archiveToken) throws Exception {
      //VldMdoVldTable.loadValidationTable(tableId) ;
      ArchiveTokenValidation archiveTokenValidation = archiveToken.getArchValidation ();
      ArchiveTokenFldVld archiveTokenFldVld,  childArchiveTokenFldVld;
      VldTblToken vldTblToken; 
      VldTblVInfoHierarchical vldTblVInfoHierarchical;
      VldTblVInfo vldTblVInfo;
      
      for (int i = 0; i < archiveTokenValidation.numFldsValidation(); i++) {
         archiveTokenFldVld = archiveTokenValidation.getFldValidation (i);
         
         if (archiveTokenFldVld.getVldType() == VldTblUtil.VLD_TBL_JERAR) {
            // Obtenemos la información sobre la validación jerárquica
            vldTblToken = VldMdoVldTable.loadValidationTable (archiveTokenFldVld.getVldParam1 ());
            
            vldTblVInfoHierarchical = (VldTblVInfoHierarchical) vldTblToken.getVInfo().restoreInfo();
            
            // Obtenemos la información sobre la validación "hija" de la jerarquica
            vldTblToken = VldMdoVldTable.loadValidationTable (vldTblVInfoHierarchical.getChildVTblId());
            
            vldTblVInfo = vldTblToken.getVInfo().restoreInfo();
            
            
            childArchiveTokenFldVld = new ArchiveTokenFldVld (
                     	archiveTokenFldVld.getId(), 
                     	archiveTokenFldVld.getFmtInfo(), 
                      vldTblToken.getVInfo().getType(),
                      vldTblVInfoHierarchical.getChildVTblId(),
                      0, 
                      archiveTokenFldVld.isRequired (), 
                      archiveTokenFldVld);
            
            archiveTokenValidation.setFldValidation (i, childArchiveTokenFldVld);
         }
      }
   }
   
   
   public static ArchiveTokenFlds createArchiveTokenFlds(int archId)
                                  throws Exception
   {
      
      ArchiveToken     token; 
      DaoArchDetRow    detRow = null; 
      String           detVal;       
           
      token = new ArchiveToken();     
      
      detVal = DaoArchDetTbl.selectDetVal(archId, DaoArchDetType.DET_TYPE_FLD_DEF);
      
      fillArchiveTokenFlds(token, detVal);       
      
      return token.getFlds();    
      
   }
   
   private static void fillArchiveTokenFlds(ArchiveToken token,
                                            String detVal)
                       throws Exception
   { 
      StringCharacterIterator iterator  = new StringCharacterIterator(detVal);
      char                    sep       = '|';
      String                  vers;
      int                     numFlds, i;
      ArchiveTokenFld         fld = null;
      
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Número de campos
      numFlds = UtilX.parseInteger(iterator, sep);
      
      for(i = 0; i < numFlds; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1); 
         
         fld = createTokenFld(iterator);
         
         token.addFld(fld);         
      }

   }

   private static ArchiveTokenFld createTokenFld(StringCharacterIterator iterator)
                                  throws Exception
   { 
      
      ArchiveTokenFld fld = null;
      int             id, type, len, nulls, doc, mult;
      String          name, colName, remarks;
      boolean         isNullable, isDoc, isMult;
      char            sep = ',';
      
      id = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      name = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      type = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      len = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      nulls = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (nulls == 0)
         isNullable = false;
      else
         isNullable = true;
      
      colName = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      doc = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (doc == 0)
         isDoc = false;
      else
         isDoc = true;
      
      mult = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (mult == 0)
         isMult = false;
      else
         isMult = true;
      
      remarks = UtilX.parseString(iterator);
      
      fld = new ArchiveTokenFld(id, name, type, len, isNullable,
                                colName, isDoc, isMult, remarks);
      
      return fld;
      
   }
   
   private static void fillArchiveTokenDividers(ArchiveToken token,
                                                String detVal)
                       throws Exception
   { 
      StringCharacterIterator iterator  = new StringCharacterIterator(detVal);
      char                    sep       = '|';
      String                  vers;
      int                     numDivs, i;
      ArchiveTokenDivider     div = null;
      
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Número de clasificadores
      numDivs = UtilX.parseInteger(iterator, sep);
      
      for(i = 0; i < numDivs; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1); 
         
         div = createTokenDivider(iterator);
         
         token.addDivider(div);         
      }

   }
   
   private static ArchiveTokenDivider createTokenDivider(StringCharacterIterator iterator)
                                      throws Exception
   { 
      
      ArchiveTokenDivider div = null;
      int                 id, type, parentId, info;
      String              name, remarks;
      char                sep = ',';
      
      id = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      name = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      parentId = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      type = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      info = UtilX.parseInteger(iterator, sep);       
      UtilX.iteratorIncrementIndex(iterator, 1);      
      
      remarks = UtilX.parseString(iterator);
      
      div = new ArchiveTokenDivider(id, name, parentId, type);
      
      return div;
      
   }
   
   private static void fillArchiveTokenValidation(ArchiveToken token,
                                                  String detVal)
                       throws Exception
   { 
      StringCharacterIterator     iterator  = new StringCharacterIterator(detVal);
      char                        sep       = '|';
      char                        reqSep;
      String                      vers;
      int                         fdrVldMacroId;
      int                         numReqFlds, i, reqFldId;
      int                         numFldsVld;
      ArchiveTokenFldVld          fldVld  = null;
      IeciTdLongIntegerArrayList  reqFlds = new IeciTdLongIntegerArrayList();
      
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      fdrVldMacroId = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Número de campos obligatorios
      numReqFlds = UtilX.parseInteger(iterator, sep);
      
      UtilX.iteratorIncrementIndex(iterator, 1); 
      
      for(i = 0; i < numReqFlds; i++)
      {    
         if ((i +1) < numReqFlds)
            reqSep = ',';
         else
            reqSep = '|';
         
         reqFldId = UtilX.parseInteger(iterator, reqSep);
         UtilX.iteratorIncrementIndex(iterator, 1);
         
         reqFlds.add(reqFldId);
      }
      
      //    Número de campos con validación
      numFldsVld = UtilX.parseInteger(iterator, sep);
            
      for(i = 0; i < numFldsVld; i++)
      {
         UtilX.iteratorIncrementIndex(iterator, 1); 
         
         fldVld = createTokenFldValidation(iterator, reqFlds);
         
         token.addFldValidation(fldVld);         
      }

   }
   
   private static ArchiveTokenFldVld createTokenFldValidation(StringCharacterIterator iterator,
                                                              IeciTdLongIntegerArrayList reqFlds)
                                     throws Exception
   { 
      
      ArchiveTokenFldVld  fldVld = null;
      int                 id, vldType, vldParam1, vldParam2;
      String              fmtInfo;
      boolean             isRequired = false;      
      char                sep    = '|';
      char                vldSep = ',';
      
      id = UtilX.parseInteger(iterator, sep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      fmtInfo = UtilX.parseString(iterator, sep);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      vldType = UtilX.parseInteger(iterator, vldSep);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      if (vldType == ArchiveValidationType.TBL_HIERARCHIC)
      {
      
         vldParam1 = UtilX.parseInteger(iterator, vldSep);
         UtilX.iteratorIncrementIndex(iterator, 1); 
      
         vldParam2 = UtilX.parseInteger(iterator, sep);          
      }
      else
      {
         vldParam1 = UtilX.parseInteger(iterator, sep);
         vldParam2 = 0;  
      }
         
      if (reqFlds.indexOf(id) != -1)
         isRequired = true;
      else
         isRequired = false;   
      
      fldVld = new ArchiveTokenFldVld(id, fmtInfo, vldType, vldParam1, 
                                      vldParam2, isRequired);
      
      return fldVld;
      
   }
   
   private static void fillArchiveTokenMisc(ArchiveToken token,
                                            String detVal)
                       throws Exception
   { 
      StringCharacterIterator iterator  = new StringCharacterIterator(detVal);
      char                    sep       = ',';
      String                  vers;
      String                  fdrName;
      int                     volListType;
      int                     volListId = SboType.NULL_ID;
      
           
      //versión
      vers = UtilX.parseString(iterator);       
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      //Nombre de carpeta
      fdrName = UtilX.parseString(iterator);
      
      if (vers.equals(MISC_VERS1_0_0))
         volListType = ArchiveVolListType.NONE;
      else
      {      
         UtilX.iteratorIncrementIndex(iterator, 1); 
         volListType = UtilX.parseInteger(iterator, sep);
      
         if (volListType != ArchiveVolListType.NONE)
         {
            UtilX.iteratorIncrementIndex(iterator, 1);
            volListId = UtilX.parseInteger(iterator, sep);
         }
      }
      
      token.setMisc(fdrName, volListType, volListId);

   }
   
} // class
