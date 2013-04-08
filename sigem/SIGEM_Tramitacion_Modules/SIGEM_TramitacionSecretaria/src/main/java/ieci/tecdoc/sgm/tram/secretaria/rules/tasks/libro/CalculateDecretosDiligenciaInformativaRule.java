package ieci.tecdoc.sgm.tram.secretaria.rules.tasks.libro;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Cuando se inicie un trámite de Información mensual (procedimiento Libro de Decretos)
 * se ha de calcular el rango de decretos que forman parte de ese periodo.
 *
 * Cuando se inicie el trámite se mira en la entidad Diligencia decretos el último
 * registro creado y se obtiene la fecha , se buscan nuevos decretos cuya fecha de firma
 * sea superior a la de la última diligencia e inferior o igual a la fecha actual.
 * Si no hay diligencia anterior entonces se buscarán los decretos desde el 1-Enero hasta
 * la fecha actual. Si no hay decreto no se permite crear el trámite
 *
 */

public class CalculateDecretosDiligenciaInformativaRule implements IRule{

    /**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(CalculateDecretosDiligenciaInformativaRule.class);


    private String numDecretoInic="";
    private String numDecretoFin="";
    private Date todayDate;

    public void cancel(IRuleContext rulectx) throws ISPACRuleException {

    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {

        IInvesflowAPI invesFlowAPI = rulectx.getClientContext().getAPI();
        String numexp=rulectx.getNumExp();
        int taskId=rulectx.getTaskId();


        if(logger.isDebugEnabled()){
            logger.debug("Ejecutando regla CalculateDecretosDiligenciaInformativaRule" +
                        " para el trámite con id: "+taskId +" del expediente: "+numexp);
        }
        try {
            IClientContext cct= rulectx.getClientContext();
            IEntitiesAPI entitiesAPI   = invesFlowAPI.getEntitiesAPI();



            IItem diligenciaInformativa=entitiesAPI.createEntity(SecretariaConstants.ENTITY_DILIGENCIAS_INFORMATIVAS, numexp);
            //Indicamos el identificador del trámite con el que está vinculado el registro de la entidad
            diligenciaInformativa.set(SecretariaConstants.FIELD_DILIGENCIAS_ID_TRAMITE, taskId);
            diligenciaInformativa.set(SecretariaConstants.FIELD_DILIGENCIAS_INFORMATIVAS_FECHA, todayDate);
            //Actualimos registro entidad
            diligenciaInformativa.set(SecretariaConstants.FIELD_DILIGENCIAS_NUM_DEC_INIC, numDecretoInic);
            diligenciaInformativa.set(SecretariaConstants.FIELD_DILIGENCIAS_NUM_DEC_FIN, numDecretoFin);

            if(logger.isDebugEnabled()){
                logger.debug("Ejecutando regla CalculateDecretosDiligenciaInformativaRule" +
                            " para el trámite con id: "+taskId +" del expediente: "+numexp +
                            "decreto inic: "+numDecretoInic+"decreto fin: "+numDecretoFin);
            }

            //Guardamos
            diligenciaInformativa.store(cct);

        } catch (ISPACException e) {
            logger.warn("Error en la regla CalculateDecretosDiligenciaInformativaRule:execute", e);
            throw new ISPACRuleException(e);
        }

        return null;
    }

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {
        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
        IInvesflowAPI invesFlowAPI = rulectx.getClientContext().getAPI();
        IEntitiesAPI entitiesAPI;
        DbCnt cnt=null;

        try {
        	cnt = rulectx.getClientContext().getConnection();
            entitiesAPI = invesFlowAPI.getEntitiesAPI();
            String where=" WHERE "+SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA +" ";
            //Obtenemos el anterior registro de la entidad diligencia informativa
            IItemCollection antDiligenciaInformativa=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_DILIGENCIAS_INFORMATIVAS, "WHERE NUMEXP='"+rulectx.getNumExp()+"' ORDER BY ID DESC");
            long timeNow = System.currentTimeMillis() ;
            todayDate=new Date(timeNow);
            Timestamp ts = new Timestamp( timeNow);
            //Calculamos el decreto inic y el decreto fin
            if(antDiligenciaInformativa.next()){
                IItem item= antDiligenciaInformativa.value();
                Date dateFirma=item.getDate(SecretariaConstants.FIELD_DILIGENCIAS_INFORMATIVAS_FECHA);
            	String initDay= DBUtil.getToTimestampByBD(cnt, new Timestamp(dateFirma.getTime()));
                where +=" > "+initDay+" ";
            }
            else{
                //Es la primera diligencia informativa
            	GregorianCalendar initDayGregorianCalendar = new GregorianCalendar();
                initDayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
                		initDayGregorianCalendar.getMinimum(GregorianCalendar.DAY_OF_MONTH));
                initDayGregorianCalendar.set(GregorianCalendar.MONTH,
                		initDayGregorianCalendar.getMinimum(GregorianCalendar.MONTH));
                initDayGregorianCalendar.set(GregorianCalendar.HOUR,
                		initDayGregorianCalendar.getMinimum(GregorianCalendar.HOUR));
                initDayGregorianCalendar.set(GregorianCalendar.MINUTE,
                		initDayGregorianCalendar.getMinimum(GregorianCalendar.MINUTE));
                initDayGregorianCalendar.set(GregorianCalendar.SECOND,
                		initDayGregorianCalendar.getMinimum(GregorianCalendar.SECOND));
                String initDay= DBUtil.getToDateByBD(cnt, initDayGregorianCalendar.getTime());
                where+=" >= "+initDay ;
            }
            	String today= DBUtil.getToTimestampByBD(cnt, ts);
                //Un decreto se considera decreto cuando es firmado por el alcalde, obtenemos los decretos del año actual
               IItemCollection decretos = entitiesAPI.queryEntities(SecretariaConstants.ENTITY_DECRETO,
            		   												where+" AND "+
            		   												SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA+" <= "+today+" ORDER BY "+SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA+" ASC");
                if(decretos.next()){
                    List <IItem> decretosList= decretos.toList();
                    IItem  item= (IItem) decretosList.get(0);
                    numDecretoInic=item.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO);
                    item=(IItem) decretosList.get(decretosList.size()-1);
                    numDecretoFin=item.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO);
                    return true;
                }
                else{
                    if(logger.isDebugEnabled()){
                        logger.debug("CalculateDecretosDiligenciaInformativaRule: " +
                                    "No hay decretos ");

                    }
                    rulectx.setInfoMessage(SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(), "diligenciaInformativa.noDecretos") );
                    return false;
                }

        } catch (ISPACException e) {
            logger.warn("Error en la regla CalculateDecretosDiligenciaInformativaRule:validate", e);
            throw new ISPACRuleException(e);
        }
        finally{
        	rulectx.getClientContext().releaseConnection(cnt);
        }

    }

}
