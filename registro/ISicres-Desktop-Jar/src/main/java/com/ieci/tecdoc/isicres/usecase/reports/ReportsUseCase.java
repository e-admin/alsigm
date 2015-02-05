package com.ieci.tecdoc.isicres.usecase.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.ReportResult;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.reports.ReportsSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.reports.xml.XMLReportList;

/**
 * @author LMVICENTE
 * @creationDate 29-abr-2004 16:25:33
 * @version
 * @since
 */
public class ReportsUseCase {

    /***************************************************************************
     * Attributes
     **************************************************************************/

    private static final Logger _log = Logger.getLogger(ReportsUseCase.class);

    /***************************************************************************
     * Constructors
     **************************************************************************/

    public ReportsUseCase() {
        super();
    }

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public Document getReportsList(UseCaseConf useCaseConf, Integer bookId, Integer reportType) throws BookException, ReportException, ValidationException, SessionException {
        List reportList = ReportsSession.getScrReports(useCaseConf.getSessionID(), bookId,
                reportType.intValue(), useCaseConf.getLocale(), useCaseConf.getEntidadId());

        return XMLReportList.createXMLReportList(reportList);
    }
    /** Parámetro opcion dice si el tipo de informe es de relación o no**/
    public ReportResult getOptionAQuery(UseCaseConf useCaseConf, Integer bookID, List fdrid,
    		int opcion, Integer maxReportRegister) throws BookException, SessionException, ReportException, ValidationException {
        ReportResult reportResult = ReportsSession.getOptionAQuery(useCaseConf.getSessionID(), bookID,
                fdrid, opcion, maxReportRegister, useCaseConf.getEntidadId());

        //ReportsSession.linkReportResult(useCaseConf.getSessionID(), bookID, reportResult);
        
        if (_log.isDebugEnabled()) {
            _log.debug("getOptionAQuery result: " + reportResult);
        }

        return reportResult;
    }
    /** Método exclusivo de infomes de relación. Variable isTarget=true destino, false origen **/
    public ReportResult getOptionCQuery(UseCaseConf useCaseConf, Integer bookID, Date beginDate, Date endDate,
            String unit, boolean isTarget, Integer maxReportRegister) throws BookException, SessionException, ReportException,
            ValidationException {
        ReportResult reportResult = ReportsSession.getOptionCQuery(useCaseConf.getSessionID(), bookID,
                beginDate, endDate, unit, isTarget, maxReportRegister, useCaseConf.getEntidadId());

        //ReportsSession.linkReportResult(useCaseConf.getSessionID(), bookID, reportResult);
        
        if (_log.isDebugEnabled()) {
            _log.debug("getOptionCQuery result: " + reportResult);
        }

        return reportResult;
    }
    /** Método exclusivo de infomes de relación. Variable isTarget=true destino, false origen **/
    public ReportResult getOptionCMultipleQuery(UseCaseConf useCaseConf, Integer bookID, Date beginDate, Date endDate,
            String unit, boolean isTarget, boolean opened, Integer maxReportRegister) throws BookException, SessionException,
            ReportException, ValidationException {
        List bookIds = new ArrayList();
		// ScrRegstate scrregstate = null;

        if (BookSession.isInBook(useCaseConf.getSessionID(), bookID, useCaseConf.getEntidadId())) {
            List inList = BookSession.getInBooks(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
            for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opened) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				//                }
            	ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if ((opened)
						|| (scrRegStateByLanguage.getScrregstateState() == 0)) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
            }
        } else {
            List outList = BookSession.getOutBooks(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
            for (Iterator it = outList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opened) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				//                }
            	ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if ((opened)
						|| (scrRegStateByLanguage.getScrregstateState() == 0)) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
            }
        }

        if (_log.isDebugEnabled()) {
            _log.debug("getOptionCMultipleQuery bookIds: " + bookIds);
        }

        ReportResult reportResult = ReportsSession.getOptionCMultipleQuery(useCaseConf.getSessionID(),
                bookID, beginDate, endDate, unit, isTarget, bookIds, maxReportRegister, useCaseConf.getEntidadId());

        //ReportsSession.linkReportResult(useCaseConf.getSessionID(), bookID, reportResult);
        
        if (_log.isDebugEnabled()) {
            _log.debug("getOptionCMultipleQuery result: " + reportResult);
        }

        return reportResult;
    }
    /** Parámetro opcion dice si el tipo de informe es de relación o no **/
    public ReportResult getOptionAMultipleQuery(UseCaseConf useCaseConf, Integer bookID, boolean opened,
    		int opcion, Integer maxReportRegister)
            throws BookException, SessionException, ReportException, ValidationException {
        List bookIds = new ArrayList();
		// ScrRegstate scrregstate = null;

        if (BookSession.isInBook(useCaseConf.getSessionID(), bookID, useCaseConf.getEntidadId())) {
            List inList = BookSession.getInBooks(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
            for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opened) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				//                }
            	ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if ((opened)
						|| (scrRegStateByLanguage.getScrregstateState() == 0)) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
            }
        } else {
            List outList = BookSession.getOutBooks(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
            for (Iterator it = outList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opened) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				//                }
            	ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if ((opened)
						|| (scrRegStateByLanguage.getScrregstateState() == 0)) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
            }
        }

        if (_log.isDebugEnabled()) {
            _log.debug("getOptionAMultipleQuery bookIds: " + bookIds);
        }

        ReportResult reportResult = ReportsSession.getOptionAMultipleQuery(useCaseConf.getSessionID(),
                bookID, bookIds, opcion, maxReportRegister, useCaseConf.getEntidadId());

        //ReportsSession.linkReportResult(useCaseConf.getSessionID(), bookID, reportResult);
        
        if (_log.isDebugEnabled()) {
            _log.debug("getOptionAMultipleQuery result: " + reportResult);
        }

        return reportResult;
    }
    
    public void dropTable(UseCaseConf useCaseConf, ReportResult report) 
    	throws ReportException, ValidationException {
    	
    	ReportsSession.dropTable(useCaseConf.getSessionID(), report, useCaseConf.getEntidadId());
    }
    public void getTemplateReport(UseCaseConf useCaseConf, int reportId, String temporalDir) throws ReportException{
    	ReportsSession.getTemplateReport(useCaseConf.getSessionID(), reportId, temporalDir, useCaseConf.getEntidadId());
    }
    

    /***************************************************************************
     * Protected methods
     **************************************************************************/

    /***************************************************************************
     * Private methods
     **************************************************************************/

    /***************************************************************************
     * Inner classes
     **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}