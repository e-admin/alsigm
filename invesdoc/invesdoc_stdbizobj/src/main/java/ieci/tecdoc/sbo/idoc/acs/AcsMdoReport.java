package ieci.tecdoc.sbo.idoc.acs;

import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.acs.base.AcsPermission;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoPerm;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sbo.idoc.api.ReportToken;

/**
 * @author egonzalez
 */
public class AcsMdoReport
{

  public static AcsInfo getAcsInfo(ReportToken token) throws Exception
  {
    AcsInfo acsInfo = null;
    acsInfo = new AcsInfo(token.getAccessType(), token.getAcsId());
    return acsInfo;
  }

  public static boolean hasAuthView(AcsAccessToken accToken, ReportToken token)
        throws Exception
  {

    AcsInfo reportAcs = null;
    int archAcsId = -1;
    int reportAcsType = -1;
    int reportAcsId = -1;
    
    reportAcs = getAcsInfo(token);
    archAcsId = token.getArchId();
    reportAcsType = reportAcs.getAcsType();
    reportAcsId = reportAcs.getAcsId();

    if (reportAcsType == AcsAccessType.ACS_PUBLIC)
      return true;

    if (AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER))
      return true;

    if (AcsMdoArchive.isArchManager(accToken, archAcsId)) 
      return true;

    /*
    if (AcsMdoObj.hasPermissions(reportAcsId, accToken.getUserId()))
      return true;
      */
    if (AcsMdoPerm.hasPermission(accToken, reportAcsId,
          AcsPermission.QUERY) )
       return true;

    return false;
  }
}
