package ieci.tecdoc.sbo.idoc.acs;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.acs.std.AcsMdoProfile;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.report.search.ReportSearchResult;
import ieci.tecdoc.sbo.idoc.report.search.ReportSearchResults;
import ieci.tecdoc.sbo.idoc.report.std.ReportDaoTokenRows;
import ieci.tecdoc.sbo.idoc.report.std.SearchReportDaoTokenRow;
import ieci.tecdoc.sbo.util.types.SboType;

//
// SELECT ID, NAME
// FROM IDOCRPT
// WHERE ARCHID = ? AND       
//      (ACSID = PUBLICO OR 
//       ACSID IN (SELECT OBJID FROM IUSEROBJPERM 
//                WHERE OBJID = IDOCRPT.ACSID AND
//                ((DSTTYPE = 1 AND DSTID = userId) OR
//                 (DSTTYPE = 2 AND DSTID = deptId) OR
//                 (DSTTYPE = 3 AND DSTID IN listGroupIds))                
//                )
//      )
//

/**
 * @author egonzalez
 */
public class ReportMdoSearch
{

  public ReportMdoSearch()
  {

  }
  
  public static ReportSearchResults executeQuery(AcsAccessToken accToken,
        int userId, ArchiveToken arch) throws Exception
  {
    StringBuffer qual = null;
    int deptId = -1;
    String groupIds = "";
    ReportSearchResults rs = null;
    
    qual = new StringBuffer ();
    qual.append(" WHERE ARCHID = ");
    qual.append(arch.getId());
    
    if (!AcsMdoProfile.hasiDocProfile(accToken, AcsProfile.IDOC_SUPERUSER)
           && !AcsMdoArchive.isArchManager(accToken, arch.getAcs().getAcsId()))
    {
      deptId = accToken.getDeptId();
      
      IeciTdLongIntegerArrayList aGroupIds = accToken.getGroupIds();
      for (int i=0; i<aGroupIds.count(); i++)
      {
        groupIds = aGroupIds.get(i) + "," + groupIds;
      }
    
      qual.append(" AND (ACSID = " + SboType.NULL_ID + " OR ");
      qual.append(" ACSID IN ( SELECT OBJID FROM IUSEROBJPERM ");
      qual.append(" WHERE OBJID = IDOCRPT.ACSID AND ");
      qual.append(" ((DSTTYPE = 1 AND DSTID = " + userId + ") OR ");
      qual.append(" (DSTTYPE = 2 AND DSTID = " + deptId + ") ");
      
      if (!groupIds.equals(""))
      {
        groupIds = groupIds.substring(0, groupIds.length()-1);
        qual.append(" OR (DSTTYPE = 3 AND DSTID IN ( " + groupIds + "))");
      }
      qual.append (")))");
      
    }
    ReportDaoTokenRows rows = new ReportDaoTokenRows ();
    DbSelectFns.select("IDOCRPT", rows.getColumnNames(), qual.toString(), false, rows);
    
    rs = new ReportSearchResults ();
    for (int i=0; i<rows.count(); i++)
    {
      SearchReportDaoTokenRow row = rows.getRecord(i);
      rs.addReport(new ReportSearchResult(row.m_id, row.m_name, row.m_reportType));
    }

    return rs;
  }
}
