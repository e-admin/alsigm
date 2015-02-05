package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StateContext implements Serializable {

    static final String TICKET_SEPARATOR = "|";
    
    public static final int READONLYREASON_LOCK = 1;
    public static final int READONLYREASON_NOT_RESPONSABILITY = 2;
    public static final int READONLYREASON_FOLLOW_SUPERVISION = 3;
    public static final int READONLYREASON_FINISHED_EXPEDIENT = 4;
    public static final int READONLYREASON_FORM = 5;
    public static final int READONLYREASON_BLOCKED_EXPEDIENT = 6;
    public static final int READONLYREASON_ARCHIVED_EXPEDIENT = 7;
    public static final int READONLYREASON_DELETED_EXPEDIENT = 8;
    
	private int pcdId;
	private int stagePcdId;
	private int stageId;
	private int taskCtlId;
	private int taskPcdId;
	private int taskId;
	private int processId;
	private String numexp;
	private int state;
	private int entity;
	private int key;
	private boolean responsible;
	private boolean readonly;
	private int batchTaskId;
	private int readonlyReason;
	
	private int subPcdId;
	private int activityPcdId;
	private int activityId;	
	private int subProcessId;
		

	/*
	 * Constructor
	 */
	public StateContext(String stateticket) throws ISPACException {
		int i=0;
		String values[] = stateticket.split("\\"+StateContext.TICKET_SEPARATOR);
		this.setPcdId(Integer.parseInt(values[i++]));
		this.setStagePcdId(Integer.parseInt(values[i++]));
		this.setStageId(Integer.parseInt(values[i++]));
		this.setTaskCtlId(Integer.parseInt(values[i++]));
		this.setTaskPcdId(Integer.parseInt(values[i++]));
		this.setTaskId(Integer.parseInt(values[i++]));
		this.setProcessId(Integer.parseInt(values[i++]));
		this.setNumexpDecoder(values[i++]);
		this.setState(Integer.parseInt(values[i++]));
		this.setEntity(Integer.parseInt(values[i++]));
		this.setKey(Integer.parseInt(values[i++]));
		boolean isResponsible = (new Boolean(values[i++])).booleanValue();
		this.setResponsible(isResponsible);
		boolean readonly = (new Boolean(values[i++])).booleanValue();
		this.setReadonly(readonly);
		this.setReadonlyReason(Integer.parseInt(values[i++]));
		this.setBatchTaskId(Integer.parseInt(values[i++]));
		
		this.setSubPcdId(Integer.parseInt(values[i++]));
		this.setActivityPcdId(Integer.parseInt(values[i++]));
		this.setActivityId(Integer.parseInt(values[i++]));
		this.setSubProcessId(Integer.parseInt(values[i++]));
	}

	public StateContext() 
	{
		this.pcdId = 0;
		this.stagePcdId = 0;
		this.stageId = 0;
		this.taskCtlId = 0;
		this.taskPcdId = 0;
		this.taskId = 0;
		this.processId = 0;
		this.numexp = null;
		this.state = 0;
		this.entity = 0;
		this.key = 0;
		this.responsible = false;
		this.readonly= false;
		this.batchTaskId = 0;
		this.readonlyReason = 0;
		
		this.subPcdId=0;
		this.activityPcdId=0;
		this.activityId=0;
		this.subProcessId=0;		
	}

	/*
	 * método que devuelve el ticket del estado
	 */
	public String getStateTicket() throws ISPACException
	{
		String stateticket =
			getPcdId()+StateContext.TICKET_SEPARATOR+
			getStagePcdId()+StateContext.TICKET_SEPARATOR+
			getStageId()+StateContext.TICKET_SEPARATOR+
			getTaskCtlId()+StateContext.TICKET_SEPARATOR+
			getTaskPcdId()+StateContext.TICKET_SEPARATOR+
			getTaskId()+StateContext.TICKET_SEPARATOR+
			getProcessId()+StateContext.TICKET_SEPARATOR+
			getNumexpEncoder()+StateContext.TICKET_SEPARATOR+
			getState()+StateContext.TICKET_SEPARATOR+
			getEntity()+StateContext.TICKET_SEPARATOR+
			getKey()+StateContext.TICKET_SEPARATOR+
			getResponsible()+StateContext.TICKET_SEPARATOR+
			getReadonly()+StateContext.TICKET_SEPARATOR+
			getReadonlyReason()+StateContext.TICKET_SEPARATOR+
			getBatchTaskId()+StateContext.TICKET_SEPARATOR+
			getSubPcdId()+StateContext.TICKET_SEPARATOR+
			getActivityPcdId()+StateContext.TICKET_SEPARATOR+
			getActivityId()+StateContext.TICKET_SEPARATOR+
			getSubProcessId()
			;
		return stateticket;
	}

	/**
	 * @return Returns the pcdId.
	 */
	public int getPcdId() {
		return pcdId;
	}
	/**
	 * @param pcdId The pcdId to set.
	 */
	public void setPcdId(int pcdId) {
		this.pcdId = pcdId;
	}
	/**
	 * @return Returns the processNum.
	 */
	public int getStageId() {
		return stageId;
	}
	/**
	 * @param processNum The processNum to set.
	 */
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	/**
	 * @return Returns the stageId.
	 */
	public int getStagePcdId() {
		return stagePcdId;
	}
	/**
	 * @param stageId The stageId to set.
	 */
	public void setStagePcdId(int stagePcdId) {
		this.stagePcdId = stagePcdId;
	}
	/**
	 * @return Returns the state.
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return Returns the taskId.
	 */
	public int getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	/**
	 * @return Returns the taskCtlId.
	 */
	public int getTaskCtlId() {
		return taskCtlId;
	}

	public void setBatchTaskId(int batchTaskId) {
		this.batchTaskId = batchTaskId;
	}

	public int getBatchTaskId() {
		return this.batchTaskId;
	}

	/**
	 * @param taskCtlId The taskCtlId to set.
	 */
	public void setTaskCtlId(int taskCtlId) {
		this.taskCtlId = taskCtlId;
	}
	/**
	 * @return Returns the taskPcdId.
	 */
	public int getTaskPcdId() {
		return taskPcdId;
	}
	/**
	 * @param taskCtlId The taskPcdId to set.
	 */
	public void setTaskPcdId(int taskPcdId) {
		this.taskPcdId = taskPcdId;
	}
	/**
	 * @return Returns the processId.
	 */
	public int getProcessId() {
		return processId;
	}
	/**
	 * @param processId The processId to set.
	 */
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	/**
	 * @return Returns the numexp.
	 */
	public String getNumexpEncoder() throws ISPACException
	{
	    if (this.numexp == null)
		    return numexp;

		try
		{
			return URLEncoder.encode(this.numexp,"ISO-8859-1");
		} catch (UnsupportedEncodingException e)
		{
			throw new ISPACException("Error en StateContext:getNumexpEncoder()",e);
		}
	}
	/**
	 * @param numexp The numexp to set.
	 */
	public void setNumexpDecoder(String numexp) throws ISPACException
	{
		if (numexp != null)
		{
			try
			{
				this.numexp = URLDecoder.decode(numexp,"ISO-8859-1");
			} catch (UnsupportedEncodingException e)
			{
				throw new ISPACException("Error en StateContext:setNumexpDecoder("+numexp+")",e);
			}
		} else
			this.numexp = numexp;
	}

	/**
	 * @return Returns the numexp.
	 */
	public String getNumexp() {
		return numexp;
	}
	/**
	 * @param numexp The numexp to set.
	 */
	public void setNumexp(String numexp) {
		this.numexp = numexp;
	}

	/**
	 * @return Returns the entity.
	 */
	public int getEntity() {
		return entity;
	}
	/**
	 * @param entity The entity to set.
	 */
	public void setEntity(int entity) {
		this.entity = entity;
	}
	/**
	 * @return Returns the key.
	 */
	public int getKey() {
		return key;
	}
	/**
	 * @param key The key to set.
	 */
	public void setKey(int key) {
		this.key = key;
	}
	/**
	 * @return Returns the isResponsible.
	 */
	public boolean getResponsible() {
		return responsible;
	}

	/**
	 * @param isResponsible The isResponsible to set.
	 */
	public void setResponsible(boolean isResponsible) {
		this.responsible = isResponsible;
	}

	/**
	 * @param readonly The readonly to set.
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return Returns the readonly.
	 */
	public boolean getReadonly() {
		return readonly;
	}


	public int getReadonlyReason() {
		return readonlyReason;
	}


	public void setReadonlyReason(int readonlyReason) {
		this.readonlyReason = readonlyReason;
	}


	public int getActivityId() {
		return activityId;
	}


	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}


	public int getActivityPcdId() {
		return activityPcdId;
	}


	public void setActivityPcdId(int activityPcdId) {
		this.activityPcdId = activityPcdId;
	}


	public int getSubPcdId() {
		return subPcdId;
	}


	public void setSubPcdId(int subPcdId) {
		this.subPcdId = subPcdId;
	}


	public int getSubProcessId() {
		return subProcessId;
	}


	public void setSubProcessId(int subProcessId) {
		this.subProcessId = subProcessId;
	}
}
