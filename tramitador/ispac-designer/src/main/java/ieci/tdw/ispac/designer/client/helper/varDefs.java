package ieci.tdw.ispac.designer.client.helper;



public final class varDefs {


	//Tipos de datos manejados
	 
	   
	public static  int SHORTTEXT    = 0;
	public static  int LONGTEXT     = 1;   
	public static  int SHORTINTEGER = 2;   
    public static  int LONGINTEGER  = 3;   
	public static  int SHORTDECIMAL = 4;
	public static  int LONGDECIMAL  = 5;
	public static  int DATETIME     = 6;
	public static  int LONGBIN      = 7;

	
	
	//Tipos de objetos que tienen eventos
	public static final int EVENT_OBJ_PROCEDURE 		= 1;
	public static final int EVENT_OBJ_STAGE		 		= 2;
	public static final int EVENT_OBJ_TASK		 		= 3;
	public static final int EVENT_OBJ_TEMPLATE	 		= 4; //obsoleta
	public static final int EVENT_OBJ_ENTITY	 		= 5;
	public static final int EVENT_OBJ_FLOW		 		= 6;

	public static final int EVENT_OBJ_SUBPROCEDURE 		= 7;
	public static final int EVENT_OBJ_ACTIVITY		 	= 8;
	
	
	public static String pIdFlowTemp="idPFlowTemp";
	public static String fUp="img/flechaVerticalUp.gif";
	public static String fDown="img/flechaVerticalDown.gif";
	public static String fLeft="img/flechaHorizontalLeft.gif";
	public static String fRight="img/flechaHorizontalRight.gif";
	public static int UP=-1;
	public static int DOWN=-2;
	public static int LEFT=-3;
	public static int RIGHT=-4;
	public static int CATALOGO=1;
	public static int TRAMITADOR=2;
	public static int tramiteAbierto=1;
	public static int tramiteCerrado=3;
	public static int tramiteCancelado=2;
	public static int TIPONODOAND = 1;
	public static int TIPONODOOR = 2;
	public static String AND = "And";
	public static String OR = "Or";
	public static String SFASE = "F";
	public static String SAND = "Y";
	public static String SOR = "O";
	public static String NODO = "Nodo";
	public static int FASECATALOGO = 1;
	public static int FASENUEVA = 2;
	public static String FASE = "F";
	public static String ACTIVIDAD = "Actividad";
	public static String styleNode="example-connector-sinc";
	public static String stylePhase="example-connector";
	public static String styleToolboxNode="toolbox-node";
	public static String styleToolboxPhaseEspecifica="toolbox-fase-espe";
	public static String styleToolboxNodoSinc="nodo-sinc";
	public static String selConector="selected-connector";
	public static String toolboxnodeProxy="toolbox-node-proxy";
	public static String divDiagrama="diagrama";
	public static String divListado="listado";
	public static String divTramites="tramites";
	public static String divSubProceso="subProceso";
	public static String AnchoFase="auto";
	public static String LargoFase="auto";
	public static int AnchoNodo=37;
	public static int LargoNodo=20;
	public static int  ENODO=1;
	public static int EFASE=3;
	public static String seleccionado="seleccionado";
	public static int difTamanioLeft=16;
	public static int difTamanioTop=19;
	public static String transicionSel="transicionSel";
	public static String idTituloTramites="tituloTramites";
	public static String iplus="./img/plus3.gif";
	public static String iMinus="./img/minus.gif";
	public static String iNoIcon="./img/noIcon.gif";
	
	public static String iProc="./img/procedimiento.gif";
	public static String iProcRealizado="./img/procedimientoRealizado.gif";
	public static String iflecha="./img/flecha.gif";
	public static int borrador=1;
	public static int vigente=2;
	public static int historico=3;
	
	public static int NO_INICIADO=0;
	public static int INICIADO=1;
	public static int CERRADO=2;
	
	
	//Idiomas
	
	public static String es="es";
	
	
	
	//public static final int MILESTONE_EXPED_START 			= 1;
	public static final int MILESTONE_EXPED_END 			= 2;
	public static final int MILESTONE_STAGE_START 			= 3;
	public static final int MILESTONE_STAGE_END 			= 4;
	public static final int MILESTONE_STAGE_END_RELOCATED	= 20;
	public static final int MILESTONE_TASK_START 			= 5;
	public static final int MILESTONE_TASK_END 				= 6;
	/*public static final int MILESTONE_EXPED_RELOCATED		= 7;
	public static final int MILESTONE_INFORMATIVE			= 8;
	public static final int MILESTONE_EXPED_CANCELED		= 9;
	public static final int MILESTONE_EXPED_RESUMED			= 10;
	public static final int MILESTONE_EXPED_DELEGATED		= 11;
	public static final int MILESTONE_STAGE_ACTIVATED		= 12;*/
	public static final int MILESTONE_TASK_DELETE			= 13;
	/*public static final int MILESTONE_RESUME_DEADLINEPROCESS= 14;
	public static final int MILESTONE_PAUSE_DEADLINEPROCESS	= 15;
	public static final int MILESTONE_RESUME_DEADLINESTAGE 	= 16;
	public static final int MILESTONE_PAUSE_DEADLINESTAGE	= 17;
	public static final int MILESTONE_RESUME_DEADLINETASK 	= 18;
	public static final int MILESTONE_PAUSE_DEADLINETASK	= 19;*/


/*	public static final int MILESTONE_SUBPROCESS_START 		= 31;
	public static final int MILESTONE_SUBPROCESS_END 		= 32;*/
	public static final int MILESTONE_ACTIVITY_START 		= 33;
	public static final int MILESTONE_ACTIVITY_END 			= 34;
	public static final int MILESTONE_ACTIVITY_END_RELOCATED= 50;
	/*public static final int MILESTONE_SUBPROCESS_RELOCATED	= 37;
	public static final int MILESTONE_SUBPROCESS_CANCELED	= 39;
	public static final int MILESTONE_SUBPROCESS_RESUMED	= 40;
	public static final int MILESTONE_SUBPROCESS_DELEGATED	= 41;
	public static final int MILESTONE_ACTIVITY_ACTIVATED	= 42;
	public static final int MILESTONE_SUBPROCESS_DELETE		= 43;
	
	
	public static final int STATUS_OPEN						= 1;
	public static final int STATUS_CLOSED					= 2;
	public static final int STATUS_CANCELED					= 3;
	
	public static final int SYNCNODE_AND					= 1;
	public static final int SYNCNODE_OR						= 2;
	public static final int SYNCNODE_CUSTOM					= 3;*/

}
