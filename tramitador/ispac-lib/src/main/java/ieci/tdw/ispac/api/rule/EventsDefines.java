/*
 * Created on 08-jun-2004
 *
 */
package ieci.tdw.ispac.api.rule;


/**
 * @author juanin
 *
 */
public class EventsDefines
{
	public static final int EVENT_OBJ_PROCEDURE 			= 1;
	public static final int EVENT_OBJ_STAGE		 			= 2;
	public static final int EVENT_OBJ_TASK		 			= 3;
	public static final int EVENT_OBJ_TEMPLATE	 			= 4; //obsoleta
	public static final int EVENT_OBJ_ENTITY	 			= 5;
	public static final int EVENT_OBJ_FLOW		 			= 6;

	public static final int EVENT_OBJ_SUBPROCEDURE 			= 7;
	public static final int EVENT_OBJ_ACTIVITY		 		= 8;

	public static final int EVENT_OBJ_SIGN_CIRCUIT			= 9;
	public static final int EVENT_OBJ_SIGN_CIRCUIT_STEP		= 10;
    public static final int EVENT_OBJ_SIGN_CIRCUIT_PORTAFIRMAS_NO_DEFAULT =11;

	public static final int EVENT_OBJ_SYSTEM		 		= 100;
	public static final int EVENT_OBJ_SYSTEM_SYSALL			= 0;

	public static final int EVENT_EXEC_START				= 1;
	public static final int EVENT_EXEC_START_AFTER			= 32;
	public static final int EVENT_EXEC_END 					= 2;
	public static final int EVENT_EXEC_END_REDEPLOY			= 29;
	public static final int EVENT_EXEC_END_AFTER			= 30;
	public static final int EVENT_EXEC_END_AFTER_REDEPLOY	= 31;
	public static final int EVENT_EXEC_CANCEL				= 3;
	public static final int EVENT_EXEC_REACTIVATE			= 4;
	public static final int EVENT_EXEC_REDEPLOY				= 5;
	public static final int EVENT_EXEC_OUTDATED				= 6;
	public static final int EVENT_EXEC_RESPCALC				= 7;
	public static final int EVENT_EXEC_NUMEXPCALC			= 8;
	public static final int EVENT_EXEC_DELETE				= 9;

	public static final int EVENT_ENTITY_CREATE				= 10;
	public static final int EVENT_ENTITY_DELETE				= 11;
	public static final int EVENT_ENTITY_MODIFY				= 12;
	public static final int EVENT_ENTITY_VIEW				= 13;

	public static final int EVENT_TEMPLATE_USE				= 20;
	public static final int EVENT_DOCUMENT_NEW				= 21;
	public static final int EVENT_DOCUMENT_SIGN				= 33;
	public static final int EVENT_TEMPLATE_EXTERNAL			= 22;

	public static final int EVENT_THIRDPARTY_ASOC			= 23;

	// A petición del usuario
	public static final int EVENT_ACTION					= 24;

	public static final int EVENT_INBOX_CREATE				= 25;
	public static final int EVENT_INBOX_ANNEX				= 26;

	public static final int EVENT_EXEC_DELEGATE				= 27;

	public static final int EVENT_EXEC_ARCHIVE				= 28;

	public static final int EVENT_EXEC_START_CIRCUIT_STEP	= 34;
	public static final int EVENT_EXEC_END_CIRCUIT_STEP		= 35;

	public static final int EVENT_OBJ_SIGN_CIRCUIT_STEP_SIGN_AFTER =	36;
	// Definidas arriba
	//public static final int EVENT_EXEC_END_REDEPLOY		= 29;
	//public static final int EVENT_EXEC_END_AFTER			= 30;
	//public static final int EVENT_EXEC_END_AFTER_REDEPLOY	= 31;
	//public static final int EVENT_EXEC_START_AFTER		= 32;
	//public static final int EVENT_DOCUMENT_SIGN			= 33;



	//Eventos para el envío papelera , restauración y eliminación definitiva de expedientes

	public static final int EVENT_EXEC_BEFORE_SEND_TRASH	=40;
	public static final int EVENT_EXEC_AFTER_SEND_TRASH		=41;
	public static final int EVENT_EXEC_BEFORE_RESTORE		=42;
	public static final int EVENT_EXEC_AFTER_RESTORE		=43;
	public static final int EVENT_EXEC_BEFORE_DELETE		=44;
	public static final int EVENT_EXEC_AFTER_DELETE			=45;

}
