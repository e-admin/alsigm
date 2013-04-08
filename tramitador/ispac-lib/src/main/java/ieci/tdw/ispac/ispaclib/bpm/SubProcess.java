package ieci.tdw.ispac.ispaclib.bpm;

public class SubProcess extends Process {

	/** Identificador del proceso. */
	private String subProcessId = null;
	public SubProcess() {
		super();
		processType = TYPE_SUBPROCESS;
	}

	public String getSubProcessId() {
		return subProcessId;
	}

	public void setSubProcessId(String subProcessId) {
		this.subProcessId = subProcessId;
	}
	
	public String toString() {
		return new StringBuffer(super.toString()).append("subprocessId=[").append(subProcessId).append("]").toString();
	}	
}