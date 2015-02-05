package ieci.tdw.ispac.ispaclib.events;

import ieci.tdw.ispac.api.rule.EventsDefines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DescriptionsPEvents {

    //Todos los eventos
    private static final Map DESCEVENT_MAP = new LinkedHashMap();
    private static final Map DESCEVENT_BEAN_MAP=new LinkedHashMap();

    //Contenedor de listas con los eventos accesibles para cada uno de los objetos de sistema
    private static final Map DESCEVENT_BEAN_TPOBJSYSTEM_MAP = new LinkedHashMap();
    //Contenedor de listas con los eventos accesibles para cada uno de los tipos de objeto
    private static final Map DESCEVENT_BEAN_TPOBJ_MAP = new LinkedHashMap();

    //Contenedor de las descripciones de los diferentes tipos de objetos
    private static final Map DESC_OBJ_MAP = new LinkedHashMap();

    static
    {
        //Carga del mapa con las descripciones de los objetos existentes
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_PROCEDURE), "events.obj.procedure");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SUBPROCEDURE), "events.obj.subprocedure");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_STAGE), "events.obj.stage");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_ACTIVITY), "events.obj.activity");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_TASK), "events.obj.task");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_ENTITY), "events.obj.entity");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_FLOW), "events.obj.flow");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT), "events.obj.sign.circuit");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP), "events.obj.sign.circuit.step");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SYSTEM_SYSALL), "events.obj.system.sysall");
        DESC_OBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SYSTEM), "events.obj.system");

        //Carga del mapa con las descripciones de todos los eventos existentes
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_START), "events.exec.start");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_START_AFTER), "events.exec.start.after");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_END), "events.exec.end");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_END_AFTER), "events.exec.end.after");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_END_REDEPLOY), "events.exec.redeploy");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_END_AFTER_REDEPLOY), "events.exec.redeploy.after");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_ARCHIVE), "events.exec.archive");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_CANCEL), "events.exec.cancel");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_REACTIVATE), "events.exec.reactivate");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_REDEPLOY), "events.exec.redeploy");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_OUTDATED), "events.exec.outdated");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_RESPCALC), "events.exec.respcalc");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_NUMEXPCALC), "events.exec.numexpcalc");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_DELETE), "events.exec.delete");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_START_CIRCUIT_STEP), "events.exec.start.sign.circuit.step");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_END_CIRCUIT_STEP), "events.exec.end.sign.circuit.step");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP_SIGN_AFTER), "events.exec.document.signAfter");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_ENTITY_CREATE), "events.exec.create");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_ENTITY_DELETE), "events.exec.entity.delete");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_ENTITY_MODIFY), "events.exec.modify");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_ENTITY_VIEW), "events.exec.view");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_TEMPLATE_USE), "events.exec.template.use");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_DOCUMENT_NEW), "events.exec.document.new");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_DOCUMENT_SIGN), "events.exec.document.sign");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_TEMPLATE_EXTERNAL), "events.exec.template.external");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_THIRDPARTY_ASOC), "events.exec.thirdparty.asoc");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_ACTION), "events.exec.action");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_INBOX_CREATE), "events.exec.inbox.create");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_INBOX_ANNEX), "events.exec.inbox.annex");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_DELEGATE), "events.exec.delegate");

        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_BEFORE_SEND_TRASH), "events.exec.before.send.trash");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_AFTER_SEND_TRASH), "events.exec.after.send.trash");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_BEFORE_RESTORE), "events.exec.before.restore");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_AFTER_RESTORE), "events.exec.after.restore");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_BEFORE_DELETE), "events.exec.before.delete");
        DESCEVENT_MAP.put(new Integer(EventsDefines.EVENT_EXEC_AFTER_DELETE), "events.exec.after.delete");

        //Carga del mapa con todos los eventos existentes
        DescriptionsPEvents.buildEventBeanList();

        //Carga del mapa con las listas de los eventos accesibles para los objetos de sistema
        DescriptionsPEvents.buildSystemTPObjectBeanMap();

        //Carga del mapa con las listas de los eventos accesibles para los tipos de objeto
        DescriptionsPEvents.buildTPObjectBeanMap();
    }

    static private void buildTPObjectBeanMap()
    {
        //Carga del mapa con las listas de los eventos accesibles para los tipos de objeto
        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SYSTEM_SYSALL),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_CANCEL,
		                EventsDefines.EVENT_EXEC_REACTIVATE,
		                EventsDefines.EVENT_EXEC_REDEPLOY,
		                EventsDefines.EVENT_EXEC_DELEGATE
                		}));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_PROCEDURE),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_START_AFTER,
		                EventsDefines.EVENT_INBOX_CREATE,
		                EventsDefines.EVENT_INBOX_ANNEX,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_END_AFTER,
		                EventsDefines.EVENT_EXEC_ARCHIVE,
		                EventsDefines.EVENT_EXEC_CANCEL,
		                EventsDefines.EVENT_EXEC_REACTIVATE,
		                EventsDefines.EVENT_EXEC_REDEPLOY,
		                EventsDefines.EVENT_EXEC_OUTDATED,
		                EventsDefines.EVENT_EXEC_RESPCALC,
		                EventsDefines.EVENT_EXEC_NUMEXPCALC,
		                EventsDefines.EVENT_EXEC_DELEGATE,
		                EventsDefines.EVENT_EXEC_BEFORE_SEND_TRASH,
		                EventsDefines.EVENT_EXEC_AFTER_SEND_TRASH,
		                EventsDefines.EVENT_EXEC_BEFORE_RESTORE,
		                EventsDefines.EVENT_EXEC_AFTER_RESTORE,
		                EventsDefines.EVENT_EXEC_BEFORE_DELETE,
		                EventsDefines.EVENT_EXEC_AFTER_DELETE
            			}));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SUBPROCEDURE),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_END_AFTER,
		                EventsDefines.EVENT_EXEC_CANCEL,
		                EventsDefines.EVENT_EXEC_REACTIVATE,
		                EventsDefines.EVENT_EXEC_REDEPLOY,
		                EventsDefines.EVENT_EXEC_OUTDATED,
		                EventsDefines.EVENT_EXEC_RESPCALC,
		                EventsDefines.EVENT_EXEC_NUMEXPCALC,
		                EventsDefines.EVENT_EXEC_DELEGATE
		    			}));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_STAGE),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_END_AFTER,
		                EventsDefines.EVENT_EXEC_END_REDEPLOY,
		                EventsDefines.EVENT_EXEC_END_AFTER_REDEPLOY,
		                EventsDefines.EVENT_EXEC_OUTDATED,
		                EventsDefines.EVENT_EXEC_RESPCALC,
		                EventsDefines.EVENT_EXEC_DELEGATE,
		                EventsDefines.EVENT_TEMPLATE_USE,
		                EventsDefines.EVENT_DOCUMENT_NEW,
		                EventsDefines.EVENT_DOCUMENT_SIGN,
		                EventsDefines.EVENT_TEMPLATE_EXTERNAL
                        }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_ACTIVITY),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_END_AFTER,
		                EventsDefines.EVENT_EXEC_END_REDEPLOY,
		                EventsDefines.EVENT_EXEC_END_AFTER_REDEPLOY,
		                EventsDefines.EVENT_EXEC_OUTDATED,
		                EventsDefines.EVENT_EXEC_RESPCALC,
		                EventsDefines.EVENT_EXEC_DELEGATE,
		                EventsDefines.EVENT_TEMPLATE_USE,
		                EventsDefines.EVENT_DOCUMENT_NEW,
		                EventsDefines.EVENT_DOCUMENT_SIGN,
		                EventsDefines.EVENT_TEMPLATE_EXTERNAL
		                }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_TASK),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_END_AFTER,
		                EventsDefines.EVENT_EXEC_OUTDATED,
		                EventsDefines.EVENT_EXEC_RESPCALC,
		                EventsDefines.EVENT_EXEC_DELETE,
		                EventsDefines.EVENT_EXEC_DELEGATE,
		                EventsDefines.EVENT_TEMPLATE_USE,
		                EventsDefines.EVENT_DOCUMENT_NEW,
		                EventsDefines.EVENT_DOCUMENT_SIGN,
		                EventsDefines.EVENT_TEMPLATE_EXTERNAL
                        }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_ENTITY),buildListDescription(new int[]{
		                EventsDefines.EVENT_ENTITY_CREATE,
		                EventsDefines.EVENT_ENTITY_DELETE,
		                EventsDefines.EVENT_ENTITY_MODIFY,
		                EventsDefines.EVENT_ENTITY_VIEW,
                        }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_FLOW),buildListDescription(new int[]{
                        EventsDefines.EVENT_EXEC_START
                        }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_START_CIRCUIT_STEP,
		                EventsDefines.EVENT_EXEC_END_CIRCUIT_STEP,
		                EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP_SIGN_AFTER
		                }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_PORTAFIRMAS_NO_DEFAULT),buildListDescription(new int[]{
                EventsDefines.EVENT_EXEC_START,
                EventsDefines.EVENT_EXEC_END
                }));

        DESCEVENT_BEAN_TPOBJ_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START_CIRCUIT_STEP,
		                EventsDefines.EVENT_EXEC_END_CIRCUIT_STEP,
		                EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP_SIGN_AFTER
		                }));
    }

    static private void buildSystemTPObjectBeanMap()
    {
        //Carga del mapa con las listas de los eventos accesibles para los objetos de sistema
        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SYSTEM_SYSALL),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_PROCEDURE),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_START_AFTER,
		                EventsDefines.EVENT_EXEC_END,
		                EventsDefines.EVENT_EXEC_ARCHIVE
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_STAGE),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_TASK),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_ENTITY),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_FLOW),buildListDescription(new int[]{
                        EventsDefines.EVENT_EXEC_START
                        }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));

        DESCEVENT_BEAN_TPOBJSYSTEM_MAP.put(new Integer(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP),buildListDescription(new int[]{
		                EventsDefines.EVENT_EXEC_START,
		                EventsDefines.EVENT_EXEC_END
		                }));
    }

    static private List buildListDescription(int[] ele)
    {
        ArrayList list = new ArrayList();
        for (int ind=0; ind<ele.length; ind++)
        {
           list.add(DESCEVENT_BEAN_MAP.get(new Integer(ele[ind])));
        }
        return list;
    }

    static private void buildEventBeanList()
    {
        Iterator it=DESCEVENT_MAP.keySet().iterator();
        while (it.hasNext())
        {
            Integer id=(Integer)it.next();
            DESCEVENT_BEAN_MAP.put(id,new DescEventsBean(id));
        }
    }

    static public String getDescripcionObject(int id)
    {
        String desc=(String)DESC_OBJ_MAP.get(new Integer(id));
        if (desc!=null)
            return desc;

        return "## Objeto desconocido";
    }

    static public String getDescripcionEvents(int id)
    {
        String desc=(String)DESCEVENT_MAP.get(new Integer(id));
        if (desc!=null)
            return desc;

        return "## Evento desconocido";
    }

    static public List getDescEventsList(int TPObject,int idObj)
    {
        if (TPObject==EventsDefines.EVENT_OBJ_SYSTEM)
            return DescriptionsPEvents.getSystemDescEventsList(idObj);

        return DescriptionsPEvents.getDescEventsList(TPObject);
    }

    static public List getDescEventsList(int TPObject)
    {
        return (List)DESCEVENT_BEAN_TPOBJ_MAP.get(new Integer(TPObject));
    }

    static public List getSystemDescEventsList(int TPObject)
    {
        return (List)DESCEVENT_BEAN_TPOBJSYSTEM_MAP.get(new Integer(TPObject));
    }

    static public Map getDescEventsMap()
    {
        return DESCEVENT_MAP;
    }

}
