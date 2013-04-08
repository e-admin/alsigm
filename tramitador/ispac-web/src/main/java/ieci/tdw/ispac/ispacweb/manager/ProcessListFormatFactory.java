package ieci.tdw.ispac.ispacweb.manager;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatterFactory;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class ProcessListFormatFactory extends BeanFormatterFactory {
	
	private static final String SEPARATOR = System.getProperty("file.separator");
	protected Logger logger = Logger.getLogger(ProcessListFormatFactory.class);
    static final String DIR_PCD="PCD";
    static final String DIR_STAGE="STG";
    static final String FILE_FMTNAME="pcdlistformat.xml";
    static final String DIR_FMTPROCESSLIST="processlistformat";

    String fileFmtName =null;
    String dirFmtformat=null;
    
    String mbasepath;
    String multiEntityDir;

    
    public ProcessListFormatFactory(String basepath, String fileFmt, String dirFmt) throws ISPACException {
    	
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
    
    public ProcessListFormatFactory(String basepath) throws ISPACException {
		this(basepath, FILE_FMTNAME, DIR_FMTPROCESSLIST);
	}
    
	public InputStream getProcessListFormat(int procedureId, int stagePCDId) throws ISPACException {
		return getProcessListFormat(procedureId, null, stagePCDId, null);
    }

	public InputStream getProcessListFormat(int procedureId, String procedureCode, int stagePCDId, String stageCode) throws ISPACException {
		
        String procedureDir = DIR_PCD + procedureId;
		String stageDir = DIR_STAGE + stagePCDId;

        String procedureCodeDir = null;
        if (StringUtils.isNotBlank(procedureCode)) {
        	procedureCodeDir = DIR_PCD + "_" + StringUtils.escapeFile(procedureCode);
        }
        
		String stageCodeDir = null;
		if (StringUtils.isNotBlank(stageCode)) {
			stageCodeDir = DIR_STAGE + "_" + StringUtils.escapeFile(stageCode);
		}

		return findFormatResource(procedureDir, stageDir, procedureCodeDir, stageCodeDir);
    }

    public BeanFormatter getBeanFormatter(int procedureId, int stagePCDId) throws ISPACException {
		return getBeanFormatter(procedureId, null, stagePCDId, null);
	}

    public BeanFormatter getBeanFormatter(int procedureId, String procedureCode, int stagePCDId, String stageCode) throws ISPACException {
		InputStream istream = getProcessListFormat(procedureId, procedureCode, stagePCDId, stageCode);
		return getBeanFormatter(istream);
	}

    protected InputStream findFormatResource(String proceduredir, String stagedir, String procedureCodeDir, String stageCodeDir) 
    		throws ISPACException {
    	
        	String formatterPath = null;
        	
        	File formatfile = null;
        	if (StringUtils.isNotEmpty(multiEntityDir)){
                // Existe el formato específico para la fase teniendo en cuenta la multientidad
        		formatterPath = mbasepath + multiEntityDir + proceduredir + SEPARATOR + stagedir + SEPARATOR + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
        		formatfile = new File(formatterPath);
				if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
					//return new FileInputStream(formatfile);
					return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
				}			
        		
				// Existe el formato específico para el procedimiento teniendo en cuenta la multientidad
				formatterPath = mbasepath + multiEntityDir + proceduredir + SEPARATOR + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
				formatfile = new File(formatterPath);
				if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
					//return new FileInputStream(formatfile);
	        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
				}

				// Formato por códigos de procedimiento y fase.
				if (StringUtils.isNotBlank(procedureCodeDir)) {
		            // Existe el formato específico para la fase.
					if (StringUtils.isNotBlank(stageCodeDir)) {
						// Existe el formato específico para la fase teniendo en cuenta la multientidad
						formatterPath = mbasepath + multiEntityDir + procedureCodeDir + SEPARATOR + stageCodeDir + SEPARATOR + fileFmtName;
		        		if(logger.isDebugEnabled()){
		        			logger.debug("Localizando formateador en '" + formatterPath +"'");
		        		}
			            formatfile = new File(formatterPath);
						if (formatfile.exists()) {
			        		if(logger.isDebugEnabled()){
								logger.debug("Formateador localizado en '" + formatterPath+"'");
			        		}
							//return new FileInputStream(formatfile);
			        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
						}
						
						// Existe el formato específico para el procedimiento teniendo en cuenta la multientidad
						formatterPath = mbasepath + multiEntityDir + procedureCodeDir + SEPARATOR + fileFmtName;
		        		if(logger.isDebugEnabled()){
		        			logger.debug("Localizando formateador en '" + formatterPath +"'");
		        		}
						formatfile = new File(formatterPath);
						if (formatfile.exists()) {
			        		if(logger.isDebugEnabled()){
								logger.debug("Formateador localizado en '" + formatterPath+"'");
			        		}
							//return new FileInputStream(formatfile);
			        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
						}
					}
				}

				
				// Existe el formato específico para la entidad 
				formatterPath = mbasepath + multiEntityDir + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
				formatfile = new File(formatterPath);
				if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
					//return new FileInputStream(formatfile);
	        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
				}
				
				
				
				
				
				
        	}
        	
        	
        	// Existe el formato específico para la fase.
        	formatterPath = mbasepath + proceduredir + SEPARATOR + stagedir + SEPARATOR + fileFmtName;
    		if(logger.isDebugEnabled()){
    			logger.debug("Localizando formateador en '" + formatterPath +"'");
    		}
            formatfile = new File(formatterPath);
			if (formatfile.exists()) {
        		if(logger.isDebugEnabled()){
					logger.debug("Formateador localizado en '" + formatterPath+"'");
        		}
				//return new FileInputStream(formatfile);
        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
			}

			// Existe el formato específico para el procedimiento.
			formatterPath = mbasepath + proceduredir + SEPARATOR + fileFmtName;
    		if(logger.isDebugEnabled()){
    			logger.debug("Localizando formateador en '" + formatterPath +"'");
    		}
			formatfile = new File(formatterPath);
			if (formatfile.exists()) {
        		if(logger.isDebugEnabled()){
					logger.debug("Formateador localizado en '" + formatterPath+"'");
        		}
				//return new FileInputStream(formatfile);
        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
			}


			// Formato por códigos de procedimiento y fase.
			if (StringUtils.isNotBlank(procedureCodeDir)) {
				
	            // Existe el formato específico para la fase.
				if (StringUtils.isNotBlank(stageCodeDir)) {
					formatterPath = mbasepath + procedureCodeDir + SEPARATOR + stageCodeDir + SEPARATOR + fileFmtName;
	        		if(logger.isDebugEnabled()){
	        			logger.debug("Localizando formateador en '" + formatterPath +"'");
	        		}
		            formatfile = new File(formatterPath);
					if (formatfile.exists()) {
		        		if(logger.isDebugEnabled()){
							logger.debug("Formateador localizado en '" + formatterPath+"'");
		        		}
						//return new FileInputStream(formatfile);
		        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
					}
				}
	
				// Existe el formato específico para el procedimiento.
				formatterPath = mbasepath + procedureCodeDir + SEPARATOR + fileFmtName;
        		if(logger.isDebugEnabled()){
        			logger.debug("Localizando formateador en '" + formatterPath +"'");
        		}
				formatfile = new File(formatterPath);
				if (formatfile.exists()) {
	        		if(logger.isDebugEnabled()){
						logger.debug("Formateador localizado en '" + formatterPath+"'");
	        		}
					//return new FileInputStream(formatfile);
	        		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
				}
				
			}
			
			// El formato por defecto para las listas de procesos.
			formatterPath = mbasepath + fileFmtName;
    		if(logger.isDebugEnabled()){
    			logger.debug("Localizando formateador en '" + formatterPath +"'");
    		}
			formatfile = new File(formatterPath);
			if (!formatfile.exists()) {
				throw new ISPACException("No existe formato por defecto [" + mbasepath + fileFmtName + "] para mostrar la lista de proceso");
			}

			//return new FileInputStream(formatfile);
    		if(logger.isDebugEnabled()){
				logger.debug("Formateador localizado en '" + formatterPath+"'");
    		}
			return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
			
    }
}
