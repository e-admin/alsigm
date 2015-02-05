/*
 * Created on 25-ene-2005
 *
 */
package ieci.tdw.ispac.api.rule;

/**
 * @author JUANIV
 *
 * Nombres estándar de propiedades de contexto de IRuleContext utilizadas por ispac
 */
public class RuleProperties
{

    public static final String RCTX_PROCEDURE 			= "procedureid";
	public static final String RCTX_PROCESS	 			= "processid";
	public static final String RCTX_SUBPROCESS			= "subprocessid";
	public static final String RCTX_NUMEXP				= "numexp";
	public static final String RCTX_TASK				= "taskid";
	public static final String RCTX_STAGE				= "stageid";
	public static final String RCTX_TASKPCD				= "taskprocedureid";
	public static final String RCTX_STAGEPCD 			= "stageprocedureid";

	public static final String RCTX_PROCEDURENAME		= "procedurename";
	public static final String RCTX_TASKNAME			= "taskname";
	public static final String RCTX_STAGENAME 			= "stagename";


	public static final String RCTX_ENTITYID			= "_entityid";
	public static final String RCTX_REGENTITYID			= "_regentityid";

	//Delegación
	public static final String RCTX_RESPDELEGATEID		= "_respdelegateid";
	public static final String RCTX_RESPDELEGATENAME	= "_respdelegatename";


	//Generación de documentos
	public static final String RCTX_DOCUMENTID	 		= "_documentid";
	public static final String RCTX_DOCUMENTTYPE 		= "_documenttype";
	public static final String RCTX_DOCUMENTNAME 		= "_documentname";
	public static final String RCTX_DOCUMENTAUTHOR 		= "_documentauthor";
	public static final String RCTX_DOCUMENTAUTHORNAME  = "_documentauthorname";
	public static final String RCTX_DOCUMENTREF 		= "_documentref";
	public static final String RCTX_DOCUMENTMIMETYPE	= "_documentmimetype";

	public static final String RCTX_TEMPLATEID	 		= "_templateid";
	public static final String RCTX_TEMPLATENAME 		= "_templatename";

	public static final String RCTX_REGISTERID	 		= "_registerid";
	
	//Información del evento
	public static final String RCTX_OBJECTID			= "_idobj";
	public static final String RCTX_OBJECTTYPE			= "_typeobj";
	public static final String RCTX_EVENT				= "_event";

}
