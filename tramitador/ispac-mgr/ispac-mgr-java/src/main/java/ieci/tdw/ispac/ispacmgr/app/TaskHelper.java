package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.deadline.Deadline;
import ieci.tdw.ispac.deadline.DeadlineFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;

public class TaskHelper {

	/**
	 * Establecer la fecha límite del trámite.
	 *
	 * @param context
	 * @param item
	 * @throws ISPACException
	 */
    public static void setLimitDate(ClientContext context,
    								IItem item) throws ISPACException {

	    IEntitiesAPI entapi = context.getAPI().getEntitiesAPI();

	    // TODO Si se crea un método finalize() a llamar despues del validate() y antes del store() mover esto alli
	    // Establecemos la fecha limite si se han introducido los datos correctos
	    String stFechaInicio = item.getString("SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO");
	    String stUPlazo = item.getString("SPAC_DT_TRAMITES:UPLAZO");
	    String stPlazo = item.getString("SPAC_DT_TRAMITES:PLAZO");

//	    if ((stFechaInicio != null) && (!stFechaInicio.equals("")) &&
//	    	(stUPlazo != null) && (!stUPlazo.equals("")) &&
//	    	(stPlazo!= null) && (!stPlazo.equals(""))) {
//
//	        Date fechaInicio = item.getDate("SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO");
//	        int uPlazo = Integer.parseInt(item.getString("SPAC_DT_TRAMITES:UPLAZO"));
//	        int plazo = item.getInt("SPAC_DT_TRAMITES:PLAZO");
//
//	        Date fechaLimite = calcLimitDate(context, fechaInicio, uPlazo, plazo);
//	        item.set("SPAC_DT_TRAMITES:FECHA_LIMITE", fechaLimite);
//
//	        // Establecemos la fecha limite en el registro de la tabla de tramites abiertos
//	        // asociado con el registro de la tabla que almacena los datos del tramite
//	        IItem task = entapi.getEntity(SpacEntities.SPAC_TRAMITES, item.getInt("SPAC_DT_TRAMITES:ID_TRAM_EXP"));
//	        task.set("FECHA_LIMITE", fechaLimite);
//	        task.store(context);
//	    }

	    Date fechaLimite = null;
	    if (StringUtils.isNotEmpty(stFechaInicio) && StringUtils.isNotEmpty(stUPlazo) && StringUtils.isNotEmpty(stPlazo)){
	        Date fechaInicio = item.getDate("SPAC_DT_TRAMITES:FECHA_INICIO_PLAZO");
	        int uPlazo = Integer.parseInt(item.getString("SPAC_DT_TRAMITES:UPLAZO"));
	        int plazo = item.getInt("SPAC_DT_TRAMITES:PLAZO");
	        String numExp = item.getString("SPAC_DT_TRAMITES:NUMEXP");


	        //el calendario debe ser el del tramite y si no el del procedimiento
	        ITask task = context.getAPI().getTask(item.getInt("SPAC_DT_TRAMITES:ID_TRAM_EXP"));
	        String idCalendario = task.getString("ID_CALENDARIO");
	        if (idCalendario==null||idCalendario.length()==0){
	        	IProcess process = context.getAPI().getProcess(numExp);
	        	idCalendario = process.getString("ID_CALENDARIO");
	        }
	        if (idCalendario!=null){
	        	fechaLimite = calcLimitDate(context, fechaInicio, uPlazo, plazo, Integer.parseInt(idCalendario));
	        }
	    }

        item.set("SPAC_DT_TRAMITES:FECHA_LIMITE", fechaLimite);

        // Establecemos la fecha limite en el registro de la tabla de tramites abiertos
        // asociado con el registro de la tabla que almacena los datos del tramite

        //Si la entidad SPAC_TRAMITES esta vinculada por composicion se establece directamente la fecha
        if (item.getProperty("SPAC_TRAMITES:FECHA_LIMITE") != null && item.get("SPAC_TRAMITES:FECHA_LIMITE") != null ){
        	item.set("SPAC_TRAMITES:FECHA_LIMITE", fechaLimite);
        }else{
	        IItem task = entapi.getEntity(SpacEntities.SPAC_TRAMITES, item.getInt("SPAC_DT_TRAMITES:ID_TRAM_EXP"));
	        task.set("FECHA_LIMITE", fechaLimite);
	        task.store(context);
        }

    }

    private static Date calcLimitDate(ClientContext context, Date fechaInicio, int uPlazo, int plazo, int idCalendar) throws ISPACException {

    	DeadlineFactory deadlinefactory = new DeadlineFactory(context, idCalendar);
    	Deadline deadline = deadlinefactory.createDeadline(uPlazo, plazo, fechaInicio);
    	Date fechaFin = deadline.getFinalDate();

    	return fechaFin;
    }

	/**
	 * Obtener el nombre del responsable del trámite.
	 *
	 * @param context
	 * @param item
	 * @return nombre del responsable
	 * @throws ISPACException
	 */
    public static String getNameResp(ClientContext context,
								     IItem item) throws ISPACException {

    	ITask task = null;

    	try {
        	task = context.getAPI().getTask(item.getInt("SPAC_DT_TRAMITES:ID_TRAM_EXP"));
        }
        catch(ISPACNullObject ino) {
        }

        if (task != null) {

	        String uid = task.getString("ID_RESP");
			if (uid != null) {

			    IResponsible responsible = context.getAPI().getRespManagerAPI().getResp(uid);
			    if (responsible != null) {

			    	return responsible.getString(IResponsible.PROPERTY_RESPNAME);
			    }
			}
        }

		return getNameResp(context);
	}

	/**
	 * Obtener el nombre del responsable del trámite.
	 *
	 * @param context
	 * @param task
	 * @return nombre del responsable
	 * @throws ISPACException
	 */
    public static String getNameResp(ClientContext context,
    								 ITask task) throws ISPACException {

    	if (task != null) {

    		String uid = task.getString("ID_RESP");
			if (uid != null) {

				IResponsible responsible = context.getAPI().getRespManagerAPI().getResp(uid);
				if (responsible != null) {

					return responsible.getString(IResponsible.PROPERTY_RESPNAME);
				}
			}
    	}

    	return getNameResp(context);
    }

	/**
	 * Obtener el nombre del responsable del trámite (usuario tramitador conectado).
	 *
	 * @param context
	 * @return nombre del responsable
	 * @throws ISPACException
	 */
   public static String getNameResp(ClientContext context) throws ISPACException {
	    IResponsible responsible = context.getResponsible();
	    return parseResponsibleName("" + responsible.get("NAME"));
   }


   /**
    * @param context
    * @return nombre del departamento responsable
    * @throws ISPACException
    */
   public static String getDptoResp(ClientContext context) throws ISPACException {
	    IResponsible responsible = context.getResponsible().getRespOrgUnit();
	    return parseResponsibleName("" + responsible.get("NAME"));
   }

    /**
     * Cuando la validación de usuarios se realiza contra LDAP 'name' es una
     * cadena del tipo CN=usuario,DC..., y solo nos interesa el contenido del atributo CN.
     *
     * @param name
     * @return nombre del usuario
     */
    public static String parseResponsibleName(String name) {

        String[] componentsName = name.split(",");
        for(int i = 0; i < componentsName.length; i++) {
            if (componentsName[i].startsWith("CN")) {
                return (componentsName[i].split("="))[1];
            }
        }

        return "" + name;
    }

}