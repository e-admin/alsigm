package ieci.tdw.ispac.ispacweb.manager;

import ieci.tdw.ispac.api.ISPACEntities;
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

public class TaskListFormatFactory extends BeanFormatterFactory {

	private static final String SEPARATOR = System.getProperty("file.separator");
	protected Logger logger = Logger.getLogger(TaskListFormatFactory.class);
	static final String DIR_PCD="PCD";
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
			return getTaskListFormat(ISPACEntities.ENTITY_NULLREGKEYID, null, taskCode);
	}

	public InputStream getTaskListFormat(int procedureId, String procedureCode, String taskCode) throws ISPACException {

        String procedureDir = null;
        if (procedureId != ISPACEntities.ENTITY_NULLREGKEYID) {
			procedureDir = DIR_PCD + procedureId;
        }

        String procedureCodeDir = null;
        if (StringUtils.isNotBlank(procedureCode)) {
			procedureCodeDir = DIR_PCD + "_" + StringUtils.escapeFile(procedureCode);
        }

        String taskdir = DIR_TASK + "_" + StringUtils.escapeFile(taskCode);

		return findFormatResource(procedureDir, procedureCodeDir, taskdir);
    }

	// Obtener el formateador a partir del código del trámite
    public BeanFormatter getBeanFormatter(String taskCode) throws ISPACException {
		return getBeanFormatter(ISPACEntities.ENTITY_NULLREGKEYID, null, taskCode);
	}

    public BeanFormatter getBeanFormatter(int procedureId, String procedureCode, String taskCode) throws ISPACException {
		InputStream istream = getTaskListFormat(procedureId, procedureCode, taskCode);
		return getBeanFormatter(istream);
	}

    protected InputStream findFormatResource(String taskdir) throws ISPACException {
		return findFormatResource(null, null, taskdir);
    }

    /**
     * Obtener el recurso del formateador para el listado de trámites.
     *
     * Prioridades, primero teniendo en cuenta la multientidad:
     *
     * /PCD<id_procedimiento>/TSK_<código_trámite_transformado>/<formateador>
     * /PCD_<código_procedimiento_transformado>/TSK_<código_trámite_transformado>/<formateador>
     * /TSK_<código_trámite_transformado>/<formateador>
     * /<formateador>
     *
     * @param proceduredir Nombre del directorio para el procedimiento a partir de su identificador.
     * @param stagedir Nombre del directorio para la fase del procedimiento a partir de su identificador.
     * @param procedureCodeDir Nombre del directorio para el procedimiento a partir de su código.
     * @param stageCodeDir Nombre del directorio para la fase del procedimiento a partir de su código.
     * @return Recurso del formateador para el listado de expedientes.
     * @throws ISPACException Si se produce algún error.
     */
    protected InputStream findFormatResource(String proceduredir, String procedureCodeDir, String taskdir) throws ISPACException {

		File formatfile = null;
		String formatterPath = null;

		if (StringUtils.isNotEmpty(multiEntityDir)){
			// Existe el formato específico para el trámite teniendo en cuenta la multientidad
			// y el procedimiento

			// Formato por identificador de procedimiento
			if (StringUtils.isNotBlank(proceduredir)) {
				formatterPath = mbasepath + multiEntityDir + proceduredir + SEPARATOR + taskdir + SEPARATOR + fileFmtName;
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

			// Formato por código de procedimiento
			if (StringUtils.isNotBlank(procedureCodeDir)) {
				formatterPath = mbasepath + multiEntityDir + procedureCodeDir + SEPARATOR + taskdir + SEPARATOR + fileFmtName;
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

			// Existe el formato específico para el trámite sin tener en cuenta el procedimiento
			formatterPath = mbasepath + multiEntityDir + taskdir + SEPARATOR + fileFmtName;
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
                //return new FileInputStream(formatfile);
				return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
            }
		}

		// Sin multientidad
		// existe el formato específico para el trámite teniendo en cuenta el procedimiento

		// Formato por identificador de procedimiento
		if (StringUtils.isNotBlank(proceduredir)) {
			formatterPath = mbasepath + proceduredir + SEPARATOR + taskdir + SEPARATOR + fileFmtName;
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

		// Formato por código de procedimiento
		if (StringUtils.isNotBlank(procedureCodeDir)) {
			formatterPath = mbasepath + procedureCodeDir + SEPARATOR + taskdir + SEPARATOR + fileFmtName;
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

		// Existe el formato específico para el trámite sin tener en cuenta el procedimiento
		formatterPath = mbasepath + taskdir + SEPARATOR + fileFmtName;
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

        // El formato por defecto para las listas de trámites
        formatterPath = mbasepath + fileFmtName;
		if(logger.isDebugEnabled()){
			logger.debug("Localizando formateador en '" + formatterPath +"'");
		}
        formatfile = new File(formatterPath);
		if (!formatfile.exists()) {
			throw new ISPACException("No existe formato por defecto [" + mbasepath + fileFmtName + "] para mostrar la lista de tramites");
		}

		if(logger.isDebugEnabled()){
			logger.debug("Formateador localizado en '" + formatterPath+"'");
		}
        //return new FileInputStream(formatfile);
		return new ByteArrayInputStream(FileUtils.retrieveFile(formatfile));
    }
}