package ieci.tecdoc.sbo.idoc.report.std;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoReport;
import ieci.tecdoc.sbo.idoc.api.ReportToken;

/**
 * @author egonzalez
 */
public class ReportManager
{
  
  public static ReportToken loadReport (AcsAccessToken accToken, int reportId) throws Exception
  {
    ReportToken  token; 
   
    token = ReportMdoToken.createReportToken(accToken, reportId);

    if (accToken != null)
      canLoadReport(accToken, token);
    
    return token;    
  }

  // NOTA: ESTE METODO NO TIENE MUCHO SENTIDO PUESTO QUE PARA VER SI SE PUEDE CARGAR O NO
  // EL REPORT PREVIAMENTE HAY QUE CARGARLO
  public static boolean canLoadReport (AcsAccessToken accToken, int reportId) throws Exception
  {
    ReportToken  token = null; 
    boolean can = false;       
    
    token = loadReport(accToken, reportId);
    can = AcsMdoReport.hasAuthView(accToken, token);
    
    return can;
  }
  
  private static void canLoadReport(AcsAccessToken accToken, ReportToken  token)
  throws Exception
  {
    
     if (accToken == null) return;
    
     if (!AcsMdoReport.hasAuthView(accToken, token))
       throw new IeciTdException(ReportError.EC_NOT_AUTH_LOAD_REPORT,
              ReportError.EM_NOT_AUTH_LOAD_REPORT);
        
  }
  
  public static String attachFdrIds (int[] folderIds, int userId) throws Exception
  {
    String auxTableName = null;
    
    try
    {
       DbConnection.beginTransaction(); 
       
       auxTableName = ReportMdoToken.getAndLockRptAuxTbl(userId);
       ReportMdoToken.insertFdrIds (auxTableName, folderIds);
    
       DbConnection.endTransaction(true);
    }
    catch (Exception e)
    {
       DbConnection.ensureEndTransaction(e);
    }
    return auxTableName;
  }
  
  public static void detachFdrIds (int userId, String tablename) throws Exception
  {
    ReportMdoToken.detachFdrIds (userId, tablename);
  }

}
