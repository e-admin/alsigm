package ieci.tdw.ispac.ispacweb.manager;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class ClosedTaskListFormatFactory extends TaskListFormatFactory {
	

	protected Logger logger = Logger.getLogger(ClosedTaskListFormatFactory.class);
	
    static final String DIR_TASK="CLOSED_TSK";
    static final String FILE_FMTNAME="closedtasklistformat.xml";
    static final String DIR_FMTPROCESSLIST="closedtasklistformat";
    
    static final String DIR_SUBPROCESS="CLOSED_SUBP";
    static final String FILE_FMTNAME_SUBPROCESS="closedsubprocesslistformat.xml";
    static final String DIR_FMTPROCESSLIST_SUBPROCESS="closedsubprocesslistformat";

    
    public ClosedTaskListFormatFactory(String basepath, int taskType) throws ISPACException {
    	super(basepath, getFileName(taskType), getSubDir(taskType));    	
	}
    

    
	// Obtener el formateador a partir del código del trámite
    public BeanFormatter getBeanFormatter(String taskCode, int taskType) throws ISPACException {
		InputStream istream = getTaskListFormat(taskCode, taskType);
		return getBeanFormatter(istream);
	}    
    
	// Obtener el formateador a partir del código del trámite
	public InputStream getTaskListFormat(String taskCode, int taskType) throws ISPACException {
		
		String taskdir = getMainDir(taskType) + "_" + StringUtils.escapeFile(taskCode);
		return findFormatResource(taskdir);
	}    
    
	private static String getMainDir(int taskType) {
    	if (taskType == ITask.SIMPLE_TASK_TYPE){
    		return DIR_TASK;
    	}
		return DIR_SUBPROCESS;
	}

	private static String getSubDir(int taskType) {
    	if (taskType == ITask.SIMPLE_TASK_TYPE){
    		return DIR_FMTPROCESSLIST;
    	}
		return DIR_FMTPROCESSLIST_SUBPROCESS;
	}

	private static String getFileName(int taskType) {
    	if (taskType == ITask.SIMPLE_TASK_TYPE){
    		return FILE_FMTNAME;
    	}
    	return FILE_FMTNAME_SUBPROCESS;
	}
	
}