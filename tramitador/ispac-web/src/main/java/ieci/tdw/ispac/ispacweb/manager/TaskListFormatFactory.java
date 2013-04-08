package ieci.tdw.ispac.ispacweb.manager;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatterFactory;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class TaskListFormatFactory extends BeanFormatterFactory {
	
	private static final String SEPARATOR = System.getProperty("file.separator");
	protected Logger logger = Logger.getLogger(TaskListFormatFactory.class);
	
    static final String DIR_TASK="TSK";
    static final String FILE_FMTNAME="tasklistformat.xml";
    static final String DIR_FMTPROCESSLIST="tasklistformat";

    String fileFmtName =null;
    String dirFmtformat=null;
    
    
    String mbasepath;
    String multiEntityDir;
    

    public TaskListFormatFactory(String basepath, String fileFmt, String dirFmt) throws ISPACException{
    	
        fileFmtName = fileFmt;
        dirFmtformat = dirFmt;

        // Comprobar si el entorno es de multientidad
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null){
			String organizationId = info.getOrganizationId();
			if (StringUtils.isNotBlank(organizationId)) {
				multiEntityDir = organizationId + SEPARATOR;
			}
		}        
                
        if (!basepath.endsWith(SEPARATOR)) {
			mbasepath = basepath + SEPARATOR + dirFmtformat + SEPARATOR;
		} else {
			mbasepath = basepath + dirFmtformat + SEPARATOR;
		}
	}
    
    public TaskListFormatFactory(String basepath) throws ISPACException {
		this(basepath, FILE_FMTNAME, DIR_FMTPROCESSLIST);
	}
    
	// Obtener el formateador a partir del código del trámite
	public InputStream getTaskListFormat(String taskCode) throws ISPACException {
		String taskdir = DIR_TASK + "_" + StringUtils.escapeFile(taskCode);
		return findFormatResource(taskdir);
	}
	
	// Obtener el formateador a partir del código del trámite
    public BeanFormatter getBeanFormatter(String taskCode) throws ISPACException {
		InputStream istream = getTaskListFormat(taskCode);
		return getBeanFormatter(istream);
	}

    protected InputStream findFormatResource(String taskdir) throws ISPACException {
    	
        try {
        	
        	File formatfile = null;
        	String formatterPath = null;
        	if (StringUtils.isNotEmpty(multiEntityDir)){
            	// Existe el formato específico para el trámite teniendo en cuenta la multientidad
        		formatterPath = mbasepath + multiEntityDir + taskdir + SEPARATOR + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
                formatfile = new File(formatterPath);
                if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
                    return new FileInputStream(formatfile);
                }

                // El formato por defecto para las listas de trámites teniendo en cuenta la multientidad
                formatterPath = mbasepath + multiEntityDir + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
                formatfile = new File(formatterPath);
                if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
                    return new FileInputStream(formatfile);
                }
        	}

        	// Existe el formato específico para el trámite.
        	formatterPath = mbasepath + taskdir + SEPARATOR + fileFmtName;
    		if(logger.isDebugEnabled()){
    			logger.debug("Localizando formateador en '" + formatterPath +"'");
    		}
            formatfile = new File(formatterPath);
            if (formatfile.exists()) {
        		if(logger.isDebugEnabled()){
					logger.debug("Formateador localizado en '" + formatterPath+"'");
        		}
                return new FileInputStream(formatfile);
            }

            // El formato por defecto para las listas de trámites.
            formatterPath = mbasepath + fileFmtName;
    		if(logger.isDebugEnabled()){
    			logger.debug("Localizando formateador en '" + formatterPath +"'");
    		}
            formatfile = new File(formatterPath);
			if (!formatfile.exists()) {
				throw new ISPACException("No existe formato por defecto [" + mbasepath + fileFmtName + "] para mostrar la lista de proceso");
			}

    		if(logger.isDebugEnabled()){
				logger.debug("Formateador localizado en '" + formatterPath+"'");
    		}
            return new FileInputStream(formatfile);
            
        } catch (FileNotFoundException e) {
            throw new ISPACException(e);
        }
    }
    
}