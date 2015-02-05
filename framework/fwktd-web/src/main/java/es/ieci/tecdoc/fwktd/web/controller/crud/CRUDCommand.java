package es.ieci.tecdoc.fwktd.web.controller.crud;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.web.controller.AbstractFormCommand;

/**
 * 
 * @author IECISA
 * 
 * @param <T>
 */
public class CRUDCommand<T extends Entity> extends AbstractFormCommand {

	/**
	 * Constructor por defecto
	 */
	public CRUDCommand() {
		super();
	}

	public CRUDCommand(T content) {
		this();
		setContent(content);
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public static final String NEW_METHOD = "new";
	public static final String RETRIEVE_METHOD = "retrieve";
	public static final String CREATE_METHOD = "create";
	public static final String DELETE_METHOD = "delete";
	public static final String UPDATE_METHOD = "update";
	public static final String ID = "content.id";

	protected T content;

	private static final long serialVersionUID = 6947855304080086384L;

}
