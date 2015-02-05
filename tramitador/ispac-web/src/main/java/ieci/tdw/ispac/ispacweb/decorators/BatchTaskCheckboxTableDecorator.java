package ieci.tdw.ispac.ispacweb.decorators;

import ieci.tdw.ispac.api.ISPACEntities;

import org.apache.commons.lang.ObjectUtils;

public class BatchTaskCheckboxTableDecorator extends CheckboxTableDecorator {

	/**
    * Nombre del campo identificador para el trámite
    */
	protected String idTask = "idTask";

	/**
    * Constructor.
    */
	public BatchTaskCheckboxTableDecorator() {
		super();
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public String getCheckbox() {
		return CheckboxTableDecoratorHelper.getCheckbox(checkedIds, fieldName,
				ObjectUtils.toString(evaluate(id)) + ":" + ObjectUtils.toString(evaluate(idTask), String.valueOf(ISPACEntities.ENTITY_NULLREGKEYID)));
	}
}
