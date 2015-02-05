package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.common.constants.SignCircuitStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitDetailDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitInstanceDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SignCircuitMgr {

	/**
	 * Secuencia para numerar la instacina de un circuito de firma, este id sera
	 * introducido en el campo ID_INSTANCIA_CIRCUITO de cada paso de un circuito
	 * instanciado
	 */
	private static final String IDSEQUENCE = "SPAC_SQ_ID_CTOS_FIRMA_INSTANCE";

	/**
	 * Contexto de cliente.
	 */
	private IClientContext mcontext = null;


	/**
	 * Constructor.
	 * @param clientContext Contexto de cliente.
	 */
	public SignCircuitMgr(IClientContext clientContext){
		this.mcontext = clientContext;
	}

	public int initCircuit(int circuitId, int documentId) throws ISPACException {

        DbCnt cnt = mcontext.getConnection();
        int instancedCircuitId;

		try {

			//Obtenemos la definicion del circuito (pasos que lo componen)
			IItemCollection itemcol = SignCircuitDetailDAO.getSteps(cnt, circuitId).disconnect();
			instancedCircuitId = IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE);

			//Instanciamos el circuito
			for (Iterator iter = itemcol.iterator(); iter.hasNext();) {
				IItem item = (IItem) iter.next();

				SignCircuitInstanceDAO instanceDAO = new SignCircuitInstanceDAO(cnt);
				instanceDAO.createNew(cnt);
				instanceDAO.set("ID_INSTANCIA_CIRCUITO", instancedCircuitId);
				instanceDAO.set("FECHA", new Date());
				instanceDAO.set("ID_DOCUMENTO", new Integer(documentId));
				instanceDAO.set("ID_CIRCUITO", circuitId);
				instanceDAO.set("ID_PASO", item.getString("ID_PASO"));
				instanceDAO.set("ID_FIRMANTE", item.getString("ID_FIRMANTE"));
				instanceDAO.set("NOMBRE_FIRMANTE", item.getString("NOMBRE_FIRMANTE"));
				instanceDAO.set("ESTADO", SignCircuitStates.SIN_INICIAR);
				instanceDAO.store(cnt);
			}

			// EVENTO: Iniciar circuito de firma
			processCircuitEvents(EventsDefines.EVENT_EXEC_START, circuitId, instancedCircuitId, documentId);

			instanceNextStep(cnt, circuitId, instancedCircuitId, documentId);

		} finally {
			mcontext.releaseConnection(cnt);
		}

		return instancedCircuitId;
	}

	public boolean signStep(SignDocument signDocument, int instancedStepId) throws ISPACException {

		// Se debe comprobar si es el ultimo paso del circuito de firma para activar el siguiente
		DbCnt cnt = mcontext.getConnection();

		try {

			// Finalizar el paso de firma
			SignCircuitInstanceDAO signCircuitInstanceDAO = new SignCircuitInstanceDAO(cnt, instancedStepId);
			signCircuitInstanceDAO.set("ESTADO", SignCircuitStates.FINALIZADO);
			signCircuitInstanceDAO.set("FECHA", new Date());

			// Comprobar si el firmante del paso de firma no es el mismo que el usuario conectado
			// (sustituto)
			if (!signCircuitInstanceDAO.getString("ID_FIRMANTE").equals(mcontext.getRespId())) {

				signCircuitInstanceDAO.set("ID_FIRMANTE", mcontext.getRespId());
				signCircuitInstanceDAO.set("NOMBRE_FIRMANTE", mcontext.getResponsible().getRespName());
			}

			signCircuitInstanceDAO.store(cnt);

			// EVENTO: Fin de paso de circuito de firmas
			processCircuitStepEvents(cnt, EventsDefines.EVENT_EXEC_END_CIRCUIT_STEP, signCircuitInstanceDAO);

			// Instanciar el paso siguiente
			instanceNextStep(cnt, signCircuitInstanceDAO.getInt("ID_CIRCUITO"),
					signCircuitInstanceDAO.getInt("ID_INSTANCIA_CIRCUITO"),
					signDocument.getItemDoc().getKeyInt());

		} finally {
			mcontext.releaseConnection(cnt);
		}

		return true;
	}

	protected void instanceNextStep(DbCnt cnt, int circuitId, int instancedCircuitId, int documentId)
			throws ISPACException {

		SignCircuitInstanceDAO circuitInstance = SignCircuitInstanceDAO.nexStep(cnt, instancedCircuitId);
		if (circuitInstance != null) { // Siguiente paso del circuito de firmas

			// Actualizar el estado del paso del circuito de firmas.
			circuitInstance.set("ESTADO", SignCircuitStates.PENDIENTE);
			circuitInstance.set("FECHA", new Date());
			circuitInstance.store(cnt);

			// EVENTO: Inicio de paso de circuito de firmas
			processCircuitStepEvents(cnt, EventsDefines.EVENT_EXEC_START_CIRCUIT_STEP, circuitInstance);

		} else { // No hay más pasos. Fin del circuito de firmas

			// Actualizar estado de firma del documento
			IItem document = mcontext.getAPI().getEntitiesAPI().getDocument(documentId);
			document.set("ESTADOFIRMA", SignStatesConstants.FIRMADO);
			document.store(mcontext);

			// EVENTO: Fin del circuito de firma
			processCircuitEvents(EventsDefines.EVENT_EXEC_END, circuitId, instancedCircuitId, documentId);
		}
	}

	/**
	 * Devuelve cierto si estamos en el ultimo paso de la firma
	 * @param instancedStepId Paso actual
	 * @return cierto si es el último , falso en caso contrario
	 * @throws ISPACException
	 */
	public boolean isLastStep(int instancedStepId) throws ISPACException {

		DbCnt cnt = mcontext.getConnection();
		try {
			return SignCircuitInstanceDAO.isLastStep(cnt, instancedStepId);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public void processCircuitEvents(int eventCode, int circuitId, int instancedCircuitId, int documentId)
			throws ISPACException {

		EventManager eventmgr = new EventManager(mcontext);
		ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
		.getInstance().getProcessSignConnector();
		int typeObj=processSignConnector.getTypeObject();

		// Inicializar contexto
		eventmgr.newContext();
		eventmgr.getRuleContextBuilder().addContext("ID_CIRCUITO", String.valueOf(circuitId));
		eventmgr.getRuleContextBuilder().addContext("ID_INSTANCIA_CIRCUITO", String.valueOf(instancedCircuitId));
		eventmgr.getRuleContextBuilder().addContext("ID_DOCUMENTO", String.valueOf(documentId));

		// Ejecutar evento de sistema al iniciar circuito de firmas.
		eventmgr.processSystemEvents(typeObj, eventCode);

		// Ejecutar evento al iniciar circuito de firmas.
		eventmgr.processEvents(typeObj, circuitId, eventCode);

	}

	public void processCircuitStepEvents(DbCnt cnt, int eventCode,SignCircuitInstanceDAO circuitInstance) throws ISPACException {

		this.processCircuitStepEvents(cnt, eventCode, circuitInstance, null);
	}

	public void processCircuitStepEvents(DbCnt cnt, int eventCode,SignCircuitInstanceDAO circuitInstance, Map paramsToAddContext)
			throws ISPACException {
		int instancedStepId = circuitInstance.getKeyInt();
		int circuitId = circuitInstance.getInt("ID_CIRCUITO");
		int instancedCircuitId = circuitInstance.getInt("ID_INSTANCIA_CIRCUITO");
		int stepId = circuitInstance.getInt("ID_PASO");
		int documentId = circuitInstance.getInt("ID_DOCUMENTO");

		ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
		.getInstance().getProcessSignConnector();

		// Información del detalle del paso
		SignCircuitDetailDAO detail = SignCircuitDetailDAO.getStep(cnt, circuitId, stepId);

		EventManager eventmgr = new EventManager(mcontext);

		// Inicializar contexto
		eventmgr.newContext();
		eventmgr.getRuleContextBuilder().addContext("ID_INSTANCIA_PASO", String.valueOf(instancedStepId));
		eventmgr.getRuleContextBuilder().addContext("ID_CIRCUITO", String.valueOf(circuitId));
		eventmgr.getRuleContextBuilder().addContext("ID_INSTANCIA_CIRCUITO", String.valueOf(instancedCircuitId));
		eventmgr.getRuleContextBuilder().addContext("ID_DOCUMENTO", String.valueOf(documentId));
		eventmgr.getRuleContextBuilder().addContext("ID_PASO", String.valueOf(stepId));

		eventmgr.getRuleContextBuilder().addContext("ID_FIRMANTE", circuitInstance.getString("ID_FIRMANTE"));
		eventmgr.getRuleContextBuilder().addContext("NOMBRE_FIRMANTE", circuitInstance.getString("NOMBRE_FIRMANTE"));
		eventmgr.getRuleContextBuilder().addContext("ESTADO", circuitInstance.getString("ESTADO"));
		eventmgr.getRuleContextBuilder().addContext("FECHA",
				TypeConverter.toString(circuitInstance.getDate("FECHA"), TypeConverter.TIMESTAMPFORMAT));
		eventmgr.getRuleContextBuilder().addContext("TIPO_NOTIF", detail.getString("TIPO_NOTIF"));
		eventmgr.getRuleContextBuilder().addContext("DIR_NOTIF", detail.getString("DIR_NOTIF"));

		if(paramsToAddContext!=null){
			Set keys= paramsToAddContext.keySet();
			Iterator itr = keys.iterator();
			while (itr.hasNext()){
				String key= (String) itr.next();
				eventmgr.getRuleContextBuilder().addContext(key, (String) paramsToAddContext.get(key));
			}

		}

		// Ejecutar evento de sistema al iniciar circuito de firmas.
		eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP, eventCode);

		// Ejecutar evento al iniciar cualquier paso en un circuito de firmas.
		eventmgr.processEvents(processSignConnector.getTypeObject(), circuitId, eventCode);

		// Ejecutar evento al iniciar paso en el circuito de firmas.
		eventmgr.processEvents(EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_STEP, detail.getKeyInt(), eventCode);

	}


}