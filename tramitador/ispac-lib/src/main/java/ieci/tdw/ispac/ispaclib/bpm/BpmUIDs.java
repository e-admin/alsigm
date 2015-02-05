package ieci.tdw.ispac.ispaclib.bpm;

public class BpmUIDs {

	String processUID;
	String stageUID;
	String taskUID;
	String subProcessUID;
	String activityUID;
	public BpmUIDs(String processUID, String stageUID, String taskUID, String subProcessUID, String activityUID) {
		super();
		this.processUID = processUID;
		this.stageUID = stageUID;
		this.taskUID = taskUID;
		this.subProcessUID = subProcessUID;
		this.activityUID = activityUID;
	}
	public BpmUIDs() {
		super();
		this.processUID = null;
		this.stageUID =  null;
		this.taskUID =  null;
		this.subProcessUID =  null;
		this.activityUID =  null;		
	}
	public String getActivityUID() {
		return activityUID;
	}
	public void setActivityUID(String activityUID) {
		this.activityUID = activityUID;
	}
	public String getProcessUID() {
		return processUID;
	}
	public void setProcessUID(String processUID) {
		this.processUID = processUID;
	}
	public String getStageUID() {
		return stageUID;
	}
	public void setStageUID(String stageUID) {
		this.stageUID = stageUID;
	}
	public String getSubProcessUID() {
		return subProcessUID;
	}
	public void setSubProcessUID(String subProcessUID) {
		this.subProcessUID = subProcessUID;
	}
	public String getTaskUID() {
		return taskUID;
	}
	public void setTaskUID(String taskUID) {
		this.taskUID = taskUID;
	}
	
	
	
}
