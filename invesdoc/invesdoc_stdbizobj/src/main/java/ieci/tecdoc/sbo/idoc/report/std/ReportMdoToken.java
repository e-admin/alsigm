package ieci.tecdoc.sbo.idoc.report.std;

import java.text.StringCharacterIterator;

import ieci.tecdoc.core.textutil.UtilX;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.idoc.api.ReportToken;
import ieci.tecdoc.sbo.idoc.dao.DaoFiAuxTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFiauxTblCtlg;
import ieci.tecdoc.sbo.idoc.dao.DaoRptTbl;

/**
 * @author egonzalez
 */

public class ReportMdoToken
{
  
  public static ReportToken createReportToken(AcsAccessToken accToken, 
                                                int reportId)
                             throws Exception
  {
     ReportToken                token     = null; 
     ReportDaoTokenRow          row       = null;   
      
     row = new ReportDaoTokenRow ();
     DaoRptTbl.selectRow(reportId, row);
     
     token = new ReportToken (row.m_id, row.m_name, row.m_editType, row.m_archId, row.m_data, row.m_remarks, row.m_accessType,       
           row.m_acsId, row.m_crtId, row.m_crtnDate, row.m_updId, row.m_updDate);
     
     parseData (token);
     
     return token;         
  }
  
  public static String getAndLockRptAuxTbl (int userId) throws Exception
  {
    String tableName = null;
    tableName =  ReportMdoToken.loadFirstReleased(userId);
    if (tableName ==  null)
      tableName = createNewFiAuxTbl (userId);
    return tableName;
  }
  
  public static void insertFdrIds (String tableName, int[] folderIds) throws Exception
  {
    DaoFiAuxTbl fiAuxTbl = null;
    
    fiAuxTbl = new DaoFiAuxTbl (tableName);
    fiAuxTbl.clear();
    for (int i=0; i<folderIds.length; i++)
    {
      fiAuxTbl.insertRow (folderIds[i]);
    }
  }
  
  public static void detachFdrIds (int userId, String tablename) throws Exception
  {
    DaoFiauxTblCtlg.releaseFiAuxTbl (userId, tablename);
  }
  
  private static String loadFirstReleased(int userId)
  throws Exception
  {
    String tableName = null;
    FiAuxTblCtlgTokenRow row = null;
    FiAuxTblCtlgTokenRows rows = null;
    boolean locked = false;

    rows = DaoFiauxTblCtlg.getReleasedFiAuxTblInfo ();
    
    if (rows.count() > 0)
    {
      int i = 0;
      for (; i<rows.count() && !locked; i++)
      {
        row = rows.getRecord(i);
        locked = DaoFiauxTblCtlg.lockFiAuxTbl (row, userId);      
        if (locked)
          tableName = row.m_name;
      }
      
      if (i != rows.count())
        tableName = row.m_name;
    }
  
    return tableName;
  }
  
  private static String createNewFiAuxTbl (int userId) throws Exception
  {
    return DaoFiauxTblCtlg.createAndLockFiAuxTbl (userId);
  }
  
  private static void parseData (ReportToken token) throws Exception
  {
    StringCharacterIterator iterator = null;
    byte[] data = token.getData();
    String sData = new String(data);
    if (data != null)
    {
      iterator  = new StringCharacterIterator(sData);
      token.setVersion(UtilX.parseString(iterator,  '|'));
      UtilX.iteratorIncrementIndex(iterator, 1);
      token.setReportType(UtilX.parseInteger(iterator, ','));
      UtilX.iteratorIncrementIndex(iterator, 1);
      token.setDataLength( UtilX.parseInteger(iterator, ','));
      UtilX.iteratorIncrementIndex(iterator, 1);
    }
    
    //sData = sData.substring(iterator.getIndex(), iterator.getIndex() + token.getDataLength());
    //token.setData(sData.getBytes());
    byte [] reportData = new byte [token.getDataLength ()];
    System.arraycopy (data, data.length - token.getDataLength (), reportData, 0, reportData.length);
    token.setData(reportData);
  }
}
