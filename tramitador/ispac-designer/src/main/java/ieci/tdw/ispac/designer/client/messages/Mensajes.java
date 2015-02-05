package ieci.tdw.ispac.designer.client.messages;


import com.google.gwt.i18n.client.Messages;

public interface Mensajes extends Messages {
	
	
	//VALIDACIONES FLUJOS
	String flowExists();
	String flowIncomingExists();
	
	
	//TOOLTIPS
	String tooltipFase();
	String tooltipActividad();
	String tooltipOr();
	String tooltipAnd();
	String tooltipConector();
	String tooltipConectorSel();
	String tooltipPrint();
	
	
	
	
	//AYUDA
	String help();
	String helpEntry1();
	String helpEntry2();
	String helpEntry3();
	String helpEntrySelSubProc();
	
	//MENSAJES INFORMATIVOS
	String infoLoading();
	String infoDelete();
	String infoTramite();
	
	
	//PREGUNTAS AL USR DE CONFIRMACION
	String confirmDeleteNode();
	String confirmDeletePhase();
	String confirmDeleteFlow();
	
	//BOTONES
	String buttonReturn();
	String buttonSave();
	String buttonSelect();
	String buttonAccept();
	String buttonCancel();
	String buttonAdd();
	String buttonDelete();
	String buttonSubir();
	String buttonBajar();
	String buttonInsertar();
	
	//ESTADO PROCEDIMIENTO
	String stateOld();
	String stateCurrent();
	String stateDraft();
	
	
	//Instancias tramites
	
	String instanciasCreadas();
	String instanciasOpen(int open);
	String instanciasClose(int close);
	String fase(String fase);
	String tramite (String tramite);
	String stateOpen();
	String stateCancel();
	String stateClose();
	String usrCreate();
	
	//LABELS
	String elementName();
	String entidad();
	String campo();
	String instance();
	String selectElement();
	String registersFind();
	String delete();
	String addCondition();
	String nameStage();
	String operadorLogico();
	String listRules();
	String tipoCondicion();
	String condition();
	String tramites();
	String tituloTramites();
	String nombre();
	String fecha();
	String estado();
	String descripcion();
	String selectTramites();
	String associatedTramites();
	String disponiblesTramites();
	String designerG();
	String listadoCondiciones();
	String orden();
	String tipo();
	
	
	//Estados de las fases/Actividades
	String realizado();
	String enCurso();
	String pendiente();
	
	
	//Pantalla de condicion
	
	String nombreCondicion();
	String detalleCondicion();
	String andOr();
	String op1();
	String op2();
	String op3();
	String operando();
	String reglas();
	String between();
	String notNull();
	String greaterThan();
	String greaterEqual();
	String lessThan();
	String lessEqual();
	String equal();
	String distinct();
	String nulo();
	String like();
	String And();
	String valor();
	String Or();
	String seleccionObligatoria();
	String campoObligatorio(String campo);
	String sql();
	String java();

	
	
	
}
