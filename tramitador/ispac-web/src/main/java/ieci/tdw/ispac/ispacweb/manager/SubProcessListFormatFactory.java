package ieci.tdw.ispac.ispacweb.manager;

import java.io.InputStream;

import ieci.tdw.ispac.api.errors.ISPACException;

public class SubProcessListFormatFactory extends ProcessListFormatFactory {

    static final String DIR_SUBPCD="SUBPCD";
    static final String DIR_ACTIVITY="ACT";
	final static String FILE_FMTNAMESUBPROCESS="subpcdlistformat.xml";
	final static String DIR_FMTSUBPROCESSLIST="subprocesslistformat";
	
	
	public SubProcessListFormatFactory(String basepath) throws ISPACException {
        super(basepath, FILE_FMTNAMESUBPROCESS, DIR_FMTSUBPROCESSLIST);
	}

	public InputStream getProcessListFormat(int subProcedureId, String subProcedureCode, int activityPCDId, String activityCode) 
			throws ISPACException {
		
        String procedureDir = DIR_SUBPCD + subProcedureId;
		String stageDir = DIR_ACTIVITY + activityPCDId;

        return findFormatResource(procedureDir, null, stageDir, null);
    }

}
