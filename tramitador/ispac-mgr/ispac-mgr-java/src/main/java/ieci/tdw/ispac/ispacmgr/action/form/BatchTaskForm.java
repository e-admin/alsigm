package ieci.tdw.ispac.ispacmgr.action.form;



/**
 * Formulario para la información de una tramitación agrupada.
 *
 */
public class BatchTaskForm extends BatchForm  {

	private static final long serialVersionUID = 1L;
	
	private String taskPcdId;
	private String batchTaskId = null;
	private String tpDocId = null;
	private String template = null;
	private String generarDocumento = null;
	private String tipoAccion  = null;
	private String file  = null;
	private String save = null;
	private String print = null;
	private String[] taskIds = null;
	private String multiboxString = null;
	private String taskIdsString = null;
	
//	public String getMultiboxString(){
//		return ArrayUtils.join(getMultibox(), ",");
//	}
//	public String getTaskIdsString(){
//		return ArrayUtils.join(getTaskIds(), ",");
//	}
	/**
	 * Constructor.
	 *
	 */
	public BatchTaskForm() {
		super();
	}

	
	public String getPrint() {
		return print;
	}


	public void setPrint(String print) {
		this.print = print;
	}


	public String getSave() {
		return save;
	}


	public void setSave(String save) {
		this.save = save;
	}


	public String getTipoAccion() {
		return tipoAccion;
	}

	
	public void setTipoAccion(String action) {
		this.tipoAccion = action;
	}



	public String getFile() {
		return file;
	}



	public void setFile(String file) {
		this.file = file;
	}



	public String getGenerarDocumento() {
		return generarDocumento;
	}

	public void setGenerarDocumento(String generarDocumento) {
		this.generarDocumento = generarDocumento;
	}

	public String getBatchTaskId() {
		return batchTaskId;
	}

	public void setBatchTaskId(String batchTaskId) {
		this.batchTaskId = batchTaskId;
	}

	public String getTaskPcdId() {
		return taskPcdId;
	}

	public void setTaskPcdId(String taskPcdId) {
		this.taskPcdId = taskPcdId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String templateId) {
		this.template = templateId;
	}

	public String getTpDocId() {
		return tpDocId;
	}

	public void setTpDocId(String tpDocId) {
		this.tpDocId = tpDocId;
	}


	public String[] getTaskIds() {
		return taskIds;
	}


	public void setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
	}


	public String getMultiboxString() {
		return multiboxString;
	}


	public void setMultiboxString(String multiboxString) {
		this.multiboxString = multiboxString;
	}


	public String getTaskIdsString() {
		return taskIdsString;
	}


	public void setTaskIdsString(String taskIdsString) {
		this.taskIdsString = taskIdsString;
	}

}
