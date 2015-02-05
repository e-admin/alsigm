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

	public InputStream getProcessListFormat(int subProcedureId, int activityPCDId) throws ISPACException {
		return getProcessListFormat(subProcedureId, null, activityPCDId, null);
	}

	/**
     * Obtener el recurso del formateador para el listado de subprocesos.
     *
     * Prioridades, primero teniendo en cuenta la multientidad:
     *
     * /SUBPCD<id_subproceso>/ACT<id_actividad>/<formateador>
     * /SUBPCD<id_subproceso>/<formateador>
     * /<formateador>
     *
    * {@inheritDoc}
    * @see ieci.tdw.ispac.ispacweb.manager.ProcessListFormatFactory#getProcessListFormat(int, java.lang.String, int, java.lang.String)
    */
	public InputStream getProcessListFormat(int subProcedureId, String subProcedureCode, int activityPCDId, String activityCode) throws ISPACException {

        String subProcedureDir = DIR_SUBPCD + subProcedureId;
		String activityDir = DIR_ACTIVITY + activityPCDId;

        return findFormatResource(subProcedureDir, activityDir, null, null);
    }

}
